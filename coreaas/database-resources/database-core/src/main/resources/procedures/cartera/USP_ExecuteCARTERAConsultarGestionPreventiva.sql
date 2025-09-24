-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/09/11
-- Description:	Procedimiento almacenado encargado de consultar los aportantes que aplican para gestión preventiva
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2021/05/28
-- HU-221-159
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarGestionPreventiva] 
	@periodoInicialEmpleador VARCHAR(7), 
	@periodoFinalEmpleador VARCHAR(7), 
	@periodoInicial VARCHAR(7),
	@periodoFinal VARCHAR(7),
	@periodoInicialMoraEmpleador DATE, 
	@periodoFinalMoraEmpleador DATE, 
	@periodoInicialMora DATE,
	@periodoFinalMora DATE,
	@tipoAfiliadoEnum VARCHAR(40),
	@tipoSolicitanteEnum VARCHAR(40),
	@noAplicarCriterioCantVecesMoroso BIT,
	@esAutomatico BIT,
	@estadoMoroso BIT,
	@estadoAlDia BIT,
	@personas BIT,
	@valorAportes NUMERIC(19,5),
	@cantidadTrabajadoresActivos BIGINT,
	@cantidadDiaHabilEjecucioAutomatica SMALLINT,
	@diaHabilActual SMALLINT
AS
SET NOCOUNT ON

DECLARE @estadoAfiliado VARCHAR(6)='ACTIVO'
DECLARE @estadoOperacion VARCHAR(7)='VIGENTE'
DECLARE @tipoRolContacto VARCHAR(28)='ROL_RESPONSABLE_APORTES'
DECLARE @tipoUbicacion VARCHAR(21)='UBICACION_PRINCIPAL'
DECLARE @estadoCarteraMoroso VARCHAR(6) = 'MOROSO'

DECLARE @tipoAfiliados TABLE (tipoAfiliado VARCHAR(24))
INSERT INTO @tipoAfiliados (tipoAfiliado)
	SELECT Data FROM dbo.Split(@tipoAfiliadoEnum,',') 

DECLARE @tipoSolicitantes TABLE (tipoSolicitante VARCHAR(24))
INSERT INTO @tipoSolicitantes (tipoSolicitante)
	SELECT Data FROM dbo.Split(@tipoSolicitanteEnum,',') 	
	
DECLARE @resultado TABLE (
	tipoIdentificacion VARCHAR(20),
	numeroIdentificacion VARCHAR (16),
	razonSocial VARCHAR(250),
	email VARCHAR(250), 
	estadoCartera VARCHAR(6),
	valorAportes NUMERIC(19,5) ,
	vecesMoroso INT,
	cantidadTrabajadores INT,
	autorizacion BIT,
	tipoAportante VARCHAR(13)
)
	
IF @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas = 1 	
BEGIN	
	--Caso empleador y personas con estado de cartera AL_DIA y MOROSO
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT * FROM (
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
			CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
		    'EMPLEADOR' as tipoAportante
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	    JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
		JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
		JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
		JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
		JOIN Persona per on emp.emppersona = per.perid
		LEFT JOIN Cartera car on car.carpersona=per.perid AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	    WHERE empl.empestadoEmpleador = @estadoAfiliado
	    AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	    AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
		    	AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))	
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos
			)
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email,
		    CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,        	
			CASE WHEN ubi.ubiAutorizacionEnvioEmail=1 OR ubi.ubiEmail IS NOT NULL THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante
		FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
	   	JOIN Ubicacion ubi ON ubi.ubiId= per.perUbicacionPrincipal
		LEFT JOIN Cartera car on car.carpersona=per.perid AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    WHERE roa.roaestadoAfiliado = @estadoAfiliado
	    AND (@esAutomatico=0 OR (roa.roaDiaHabilVencimientoAporte = @diaHabilActual))
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
		AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)>=@valorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))    	
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,ubi.ubiEmail,ubi.ubiAutorizacionEnvioEmail,roa.roaTipoAfiliado) aportantes
END
ELSE 
IF  @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas = 0 
BEGIN
	-- Caso empleador con estado de cartera AL_DIA y MOROSO	    
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    'EMPLEADOR' as tipoAportante
	FROM  Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
	JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
	JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
	JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
	LEFT JOIN Cartera car on car.carpersona=per.perid and car.carEstadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	WHERE empl.empestadoEmpleador = @estadoAfiliado
	AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))	
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
END
ELSE 
IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 1
BEGIN
	--Caso empleador y personas con estado cartera 'MOROSO'
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT * FROM (
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
			CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
		    'EMPLEADOR' as tipoAportante
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
		JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
		JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
		JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
	    WHERE empl.empestadoEmpleador = @estadoAfiliado
	    AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	    AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos) 		
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email,
		    CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,        	
			CASE WHEN ubi.ubiAutorizacionEnvioEmail=1 OR ubi.ubiEmail IS NOT NULL THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante 
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		JOIN Cartera car on car.carpersona=per.perid
		JOIN Ubicacion ubi ON ubi.ubiId= per.perUbicacionPrincipal 
		WHERE roa.roaestadoAfiliado = @estadoAfiliado
		AND (@esAutomatico=0 OR (roa.roaDiaHabilVencimientoAporte = @diaHabilActual))
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)>=@valorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora))) 
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,ubi.ubiEmail,ubi.ubiAutorizacionEnvioEmail,roa.roaTipoAfiliado) aportantes
END
ELSE
IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 0
BEGIN
	--Caso Empleador con estado cartera 'MOROSO'	
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    'EMPLEADOR' as tipoAportante
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
	JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
	JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
	JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
	WHERE empl.empestadoEmpleador = @estadoAfiliado
	AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
END
ELSE
IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 1
BEGIN
	--Caso empleador y personas con estado cartera 'AL_DIA'
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT * FROM (
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
			CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
		    'EMPLEADOR' as tipoAportante
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
		JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
		JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
		JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
	    WHERE empl.empestadoEmpleador = @estadoAfiliado
	    AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera='MOROSO')
	    AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
		    	AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email,
		    CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,        	
			CASE WHEN ubi.ubiAutorizacionEnvioEmail=1 OR ubi.ubiEmail IS NOT NULL THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
	   	JOIN Ubicacion ubi ON ubi.ubiId= per.perUbicacionPrincipal
	    WHERE roa.roaestadoAfiliado = @estadoAfiliado
	    AND (@esAutomatico=0 OR (roa.roaDiaHabilVencimientoAporte = @diaHabilActual))
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera='MOROSO')
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)>=@valorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,ubi.ubiEmail,ubi.ubiAutorizacionEnvioEmail,roa.roaTipoAfiliado) aportantes
END
ELSE 
IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 0
BEGIN
	--Caso Empleador con estado cartera 'AL_DIA'	
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
	)
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, ubi.ubiEmail as email, 
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		CASE WHEN (ubiAuto.ubiAutorizacionEnvioEmail=1 AND ubi.ubiEmail IS NOT NULL)THEN cast(1 as bit) ELSE cast(0 as bit) END as autorizacion,
	    'EMPLEADOR' as tipoAportante 
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion=@tipoUbicacion
	JOIN Ubicacion ubiAuto ON ube.ubeUbicacion = ubiAuto.ubiId
	JOIN RolContactoEmpleador rol ON rol.rceEmpleador = empl.empId AND rol.rceTipoRolContactoEmpleador =@tipoRolContacto
	JOIN Ubicacion ubi ON ubi.ubiId= rol.rceUbicacion
	WHERE empl.empestadoEmpleador = @estadoAfiliado
	AND (@esAutomatico=0 OR (empl.empDiaHabilVencimientoAporte = @diaHabilActual))
	AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera='MOROSO')
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)>=@valorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)		
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,ubi.ubiEmail,ubiAuto.ubiAutorizacionEnvioEmail
END

SELECT  
	tipoIdentificacion, numeroIdentificacion, razonSocial, email, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, autorizacion, tipoAportante
FROM @resultado