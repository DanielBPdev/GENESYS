-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/09/13
-- Description:	Procedimiento almacenado encargado de consultar los aportantes que aplican para gestión de fiscalización
-- HU-222-204
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarGestionFiscalizacion] 
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
	@estadoMoroso BIT,
	@estadoAlDia BIT,
	@personas BIT,
	@valorAportes NUMERIC(19,5),
	@cantidadTrabajadoresActivos BIGINT,
	@corteEntidades BIGINT,
	@preventiva BIT,
	@validacionesPILA BIT,
	@fechaInicialEmpleadorP DATE,
	@fechaFinalEmpleadorP DATE,
	@fechaInicialP DATE,
	@fechaFinalP DATE,
	@motivoFiscalizacionEnum VARCHAR(250),
	@estadoAfiliacionCartera VARCHAR(20),
	@incluirLC2 BIT,
    @incluirLC3 BIT
AS	
SET NOCOUNT ON

DECLARE @estadoAfiliado VARCHAR(6)='ACTIVO'
DECLARE @estadoOperacion VARCHAR(7)='VIGENTE'
DECLARE @estadoCarteraMoroso VARCHAR(6) = 'MOROSO'


DECLARE @tipoAfiliados TABLE (tipoAfiliado VARCHAR(24))
INSERT INTO @tipoAfiliados (tipoAfiliado)
	SELECT Data FROM dbo.Split(@tipoAfiliadoEnum,',') 

DECLARE @tipoSolicitantes TABLE (tipoSolicitante VARCHAR(24))
INSERT INTO @tipoSolicitantes (tipoSolicitante)
	SELECT Data FROM dbo.Split(@tipoSolicitanteEnum,',') 	
	
DECLARE @motivosFiscalizacion TABLE (motivoFiscalizacion VARCHAR(30))
INSERT INTO @motivosFiscalizacion (motivoFiscalizacion)
	SELECT Data FROM dbo.Split(@motivoFiscalizacionEnum,',') 		
	
DECLARE @resultado TABLE (
	tipoIdentificacion VARCHAR(20),
	numeroIdentificacion VARCHAR (16),
	razonSocial VARCHAR(250),
	estadoAfiliacion VARCHAR(41), 
	estadoCartera VARCHAR(6),
	valorAportes NUMERIC(19,5) ,
	vecesMoroso INT,
	cantidadTrabajadores INT,
	tipoAportante VARCHAR(13),
	tipoLineaCobro VARCHAR(13)
)

declare @minValorAportes Numeric(19,5)
declare @maxValorAportes Numeric(19,5)

set @minValorAportes = case when @valorAportes > 0 then @valorAportes else 0 end;
set @maxValorAportes = case when @valorAPortes > 0 then 99999.99 else @valorAportes end;


declare @filtrosLineaCobro table(carTipoLineaCobro VARCHAR(10))
insert into @filtrosLineaCobro select distinct top (@corteEntidades) carTipoLineaCobro from Cartera where carTipoLineaCobro not in ('LC2', 'LC3');

if @incluirLC2 = 1 OR @incluirLC3 = 1 begin delete from @filtrosLineaCobro end;
if @incluirLC2 = 1 begin insert into @filtrosLineaCobro values ('LC2') end;
if @incluirLC3 = 1 begin insert into @filtrosLineaCobro values ('LC3') end;

declare @filtroEstado table( estadoAfiliacion VARCHAR(50))
declare @noFormalizados table( estado varchar(100),empId bigint,perId BIGINT);


if @estadoAfiliacionCartera = 'TODOS' begin delete from @filtroEstado end;
if @estadoAfiliacionCartera = 'ACTIVO' or @estadoAfiliacionCartera = 'TODOS'  begin insert into @filtroEstado values ('ACTIVO') end; 
if @estadoAfiliacionCartera = 'INACTIVO' or @estadoAfiliacionCartera = 'TODOS'  begin insert into @filtroEstado values ('INACTIVO') end;
if @estadoAfiliacionCartera ! = 'OTROS ESTADOS'



BEGIN 
		----INICIA FILTROS DIFERENTES A OTROS FILTROS
		IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 1
		BEGIN
			--Caso empleador y personas con estado cartera 'MOROSO'
			--INSERT INTO @resultado (
				--tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
			--) 
			print '1'
			SELECT DISTINCT TOP (@corteEntidades) * FROM ( 
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
			  --      CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera carNico where carTipoLineaCobro like '%LC2%' and carNico.carId=car.carId) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3 = 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
	            AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
				AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
				AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
					OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
		   --        CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				JOIN Cartera car on car.carpersona=per.perid
			   WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
			   AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
				AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
				AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    			WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    			GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio) between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			    AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
				AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
					OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante,car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				JOIN Cartera car on car.carpersona=per.perid
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
				AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
						(SELECT tipoSolicitante from @tipoSolicitantes)))
					OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
		END
		ELSE

		IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 0
		BEGIN
			--Caso Empleador con estado cartera 'MOROSO'	
			--INSERT INTO @resultado (
			--	tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
			--)
			print '2'
		  SELECT  DISTINCT TOP (@corteEntidades)*  FROM(
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
			AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
			AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
			AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
				OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
				OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
			UNION
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
		   --    CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
			AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
			AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
			AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
					BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
				OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro )AS B
		END 
		ELSE

		IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 1
		BEGIN
			--Caso empleador y personas con estado cartera 'AL_DIA'
			--INSERT INTO @resultado (
			--	tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
			--) 
			print '3'
			SELECT DISTINCT TOP (@corteEntidades)  * FROM (
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
					OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Cartera car on car.carpersona=per.perid
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select   * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    			WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    			GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
					OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante,car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Cartera car on car.carpersona=per.perid
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
						(SELECT tipoSolicitante from @tipoSolicitantes)))
					OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
		END
		ELSE 

		IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 0
		BEGIN
			--Caso Empleador con estado cartera 'AL_DIA'	
			--INSERT INTO @resultado (
			--	tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
			--)
			print '4'
			SELECT DISTINCT TOP (@corteEntidades) * FROM ( 
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
				--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				--    ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
				--	( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			 --    	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro		
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			 AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
			 AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
			 AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
				OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
				OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)	
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
			UNION
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
		   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
			AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
			AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
					BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
				OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro) AS E
		END

		IF @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas = 0
		BEGIN
			--Caso empleador y personas con estado cartera 'MOROSO Y AL DÍA'
			----INSERT INTO @resultado (
			--	tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
			--)
			print '5'
		--****** Al_Dia, persona = 0
		SELECT DISTINCT TOP (@corteEntidades) * FROM ( 
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
		   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro		
			 FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
		   WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
		   	AND car.carTipoLineaCobro in (select   * from @filtrosLineaCobro)
			 AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
			AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
				OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
				OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)	
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
			UNION
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
		   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
			AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
			AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
					BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
				OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
	
		--****** Moroso, persona = 0
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
			AND  (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
			AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
			AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
				OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
				OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
			UNION
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
				CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
				(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
					AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
				(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
					AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
				(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
				'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
			FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
			JOIN Persona per on emp.emppersona = per.perid
			JOIN Cartera car on car.carpersona=per.perid
			WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
			AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
			AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
			AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
					BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
				OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
			group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro)R
		END

		IF @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas= 1 
		--select * from VW_EstadoAfiliacionEmpleadorCaja vwc WHERE vwc.perNumeroIdentificacion ='900787411'
		BEGIN
		--**** Moroso, Persona = 1
		print '6'
		 SELECT DISTINCT TOP (@corteEntidades)*  FROM(
		 SELECT *  FROM (
		 SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		   			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
				AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
				AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
					OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				JOIN Cartera car on car.carpersona=per.perid
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
				AND  roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
				AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
				AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    			WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    			GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
				AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
					OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				JOIN Cartera car on car.carpersona=per.perid
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
				AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
						(SELECT tipoSolicitante from @tipoSolicitantes)))
					OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
				UNION 
		--**** AL_DIA, Personas = 1
		SELECT  TOP 1* FROM (
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					 (SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
					OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
		
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Cartera car on car.carpersona=per.perid
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado) 
				AND car.carTipoLineaCobro in (select* from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    			WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    			GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
					OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, empl.empestadoEmpleador,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
						AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
					(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
					'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
				JOIN Persona per on emp.emppersona = per.perid
				JOIN Cartera car on car.carpersona=per.perid
				WHERE empl.empestadoEmpleador in (select * from @filtroEstado)
			    AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
					OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
						AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,empl.empestadoEmpleador,car.carTipoLineaCobro
				UNION
				SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, roa.roaestadoAfiliado,
					CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       			(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
					(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
						AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
					0 as cantidadTrabajadores,
					(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
						AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
						AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    			CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
					--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
				 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
					--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
			  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
				FROM Persona per
				JOIN Cartera car on car.carpersona=per.perid
				JOIN Afiliado afi on afi.afiPersona = per.perId
	   			JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
				WHERE roa.roaEstadoAfiliado in (select * from @filtroEstado)
				AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
				AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
				AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
				AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
						BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
						(SELECT tipoSolicitante from @tipoSolicitantes)))
					OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
					AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
				group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,roa.roaestadoAfiliado,car.carTipoLineaCobro)aportantes
				)AS C
		 end
		----FIN FILTROS DIFERENTES A OTROS FILTROS
END
ELSE -- ELSE´PRIMER IF if @estadoAfiliacionCartera != 'OTROS ESTADOS' MEDIOXL
BEGIN
			--insert into @filtroEstado (estadoAfiliacion) (select empEstadoEmpleador from VW_EstadoAfiliacionEmpleadorCaja vwc where vwc.empEstadoEmpleador not in ('ACTIVO', 'INACTIVO') group by empEstadoEmpleador)SELECT  'Paso por aquí.... OTROS ESTADO'
			insert into @filtroEstado (estadoAfiliacion) values 
				('NO_FORMALIZADO_RETIRADO_CON_APORTES'),
					('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'),
					('NO_FORMALIZADO_CON_INFORMACION');

		/*	insert into @noFormalizados
				select case when apg.apgEmpresa is null then 'NO_FORMALIZADO_CON_INFORMACION' else 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' end,
				e.empId,p.perId
				from Persona p 
				inner join Empresa e on e.empPersona=p.perId
				left join dbo.AporteGeneral as apg on apg.apgEmpresa = e.empId
				where not exists (select 1 from dbo.Empleador where empEmpresa = e.empId)
				group by e.empId, apg.apgEmpresa,p.perId*/

IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 1
BEGIN
	--Caso empleador y personas con estado cartera 'MOROSO'
	/*comentado 15/11/2023
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
	) */
	SELECT DISTINCT TOP (@corteEntidades)  * FROM ( 
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else empl.empestadoEmpleador  end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
	  --      CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera carNico where carTipoLineaCobro like '%LC2%' and carNico.carId=car.carId) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3 = 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
        WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
		AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		------------------------------------------------------------
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, -- (case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		   isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
   --        CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		JOIN Cartera car on car.carpersona=per.perid
	   -- LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio) between @minValorAportes and @maxValorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro
		UNION
		--------------------------------------------------------------
		  SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
		AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
		AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
			OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		------------------------------------------------------------------------------
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		    isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante,car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		JOIN Cartera car on car.carpersona=per.perid
	--	LEFT JOIN @noFormalizados nof on nof.perId=per.perId
        WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
				(SELECT tipoSolicitante from @tipoSolicitantes)))
			OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
END
ELSE

IF @estadoMoroso = 1 AND @estadoAlDia = 0 AND @personas = 0
BEGIN
	--Caso Empleador con estado cartera 'MOROSO'	
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
	)
	SELECT DISTINCT TOP (@corteEntidades)  * FROM (
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE-- nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	--------------------------------------
	UNION
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, -- (case when nof.estado is not null then nof.estado else empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
   --    CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE nof.estado in(select * from @filtroEstado )
	AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
			BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
		OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro)B
END
-------------------------------------------------------------------------------------------
ELSE

IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 1
BEGIN
	--Caso empleador y personas con estado cartera 'AL_DIA'
	/*comentado 15/11/2023
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
	)*/
	SELECT DISTINCT TOP (@corteEntidades) * FROM (
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE  --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		UNION
		-----------------------------------------------------------------------------------------------------------------------------------------------------------------
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		    isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
		JOIN Cartera car on car.carpersona=per.perid
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
		AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro
		---------------------------------------------------------------------------------------------------------------------------------
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else empl.empestadoEmpleador  end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
		WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
			OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		   isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante,car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
		JOIN Cartera car on car.carpersona=per.perid
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE nof.estado in(select * from @filtroEstado )
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
		AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
				(SELECT tipoSolicitante from @tipoSolicitantes)))
			OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
	--------------------------------------------------------------------------------------------------------------
END
ELSE 

IF @estadoMoroso = 0 AND @estadoAlDia = 1 AND @personas = 0
BEGIN
	--Caso Empleador con estado cartera 'AL_DIA'	
		/*comentado 15/11/2023
	INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
	)*/
	 SELECT DISTINCT TOP (@corteEntidades)*  FROM(
			SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
		--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		--    ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
		--	( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	 --    	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro		
    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE --nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	 AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)	
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	UNION
	-------------------------------------------------------------------------------------------------------------------
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE  --nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
			BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
		OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	)as c
END

IF @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas = 0
BEGIN
	--Caso empleador y personas con estado cartera 'MOROSO Y AL DÍA'
		/*comentado 15/11/2023
		INSERT INTO @resultado (
		tipoIdentificacion, numeroIdentificacion, razonSocial, estadoAfiliacion, estadoCartera, valorAportes, vecesMoroso, cantidadTrabajadores, tipoAportante,tipoLineaCobro
	)*/
--****** Al_Dia, persona = 0
 SELECT DISTINCT TOP (@corteEntidades)*  FROM(
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro		
     FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
   WHERE --nof.estado in(select * from @filtroEstado )
    (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
   AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	 AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)	
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	UNION
	-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
   --     CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE --nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
			BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
		OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	
--****** Moroso, persona = 0
UNION
SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE --nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	AND  (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
		OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	UNION
	--------------------------------------------------------------------------------------------------------------------------
  SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
		isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
	    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
		(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
			AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
		(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
			AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
		(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
	    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
	JOIN Persona per on emp.emppersona = per.perid
	JOIN Cartera car on car.carpersona=per.perid
	--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	WHERE --nof.estado in(select * from @filtroEstado )
	 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
	AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
			BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
		OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
	group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
	)AS C
END

IF @estadoMoroso = 1 AND @estadoAlDia = 1 AND @personas= 1 
--select * from VW_EstadoAfiliacionEmpleadorCaja vwc WHERE vwc.perNumeroIdentificacion ='900787411'
BEGIN
--**** Moroso, Persona = 1
 SELECT DISTINCT TOP (@corteEntidades)*  FROM(
 SELECT *  FROM (
  SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		   	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
		WHERE --nof.estado in(select top(@corteEntidades) * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
		AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		UNION
		----------------------------------------------------------------------------------------------
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		    isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
        WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND  roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
			OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro
		UNION
		---------------------------------------------------------------------------
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, -- (case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
			'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
        WHERE  --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion and car.carTipoSolicitante='EMPLEADOR') in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion = @estadoOperacion and car.carTipoSolicitante='EMPLEADOR'
		AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
			OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		UNION
		----------------------------------------------------
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		   isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
		WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
	   AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) in (@estadoCarteraMoroso)
	    AND car.carestadoOperacion =@estadoOperacion and car.carTipoSolicitante in (SELECT tipoSolicitante from @tipoSolicitantes)
	    AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
				(SELECT tipoSolicitante from @tipoSolicitantes)))
			OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro) aportantes
		UNION 
--**** AL_DIA, Personas = 1
SELECT DISTINCT TOP (@corteEntidades) * FROM (
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			 (SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		    'EMPLEADOR' as tipoAportante, car.carTipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
	    WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
        AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND (EXISTS(SELECT AVG(ap.apgValTotalApoObligatorio),ap.apgEmpresa FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
		    	AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador  GROUP BY ap.apgEmpresa HAVING AVG(ap.apgValTotalApoObligatorio)between @minValorAportes and @maxValorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador))
			OR (SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado)>=@cantidadTrabajadoresActivos)
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		
		UNION
	
				--*******************************************************************--
		----AJUSTES FILTROS NO FORMALIZADOS 72837
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, -- (case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		   isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
		JOIN Cartera car on car.carpersona=per.perid
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
        WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select  * from @filtrosLineaCobro)
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
	    AND (EXISTS(SELECT AVG(apd.apdAporteObligatorio),apd.apdPersona FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId  
		    	WHERE apd.apdPersona=per.perid AND apd.apdTipoCotizante in (SELECT tipoAfiliado FROM @tipoAfiliados) AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal
		    	GROUP BY apd.apdPersona HAVING AVG(apd.apdAporteObligatorio)between @minValorAportes and @maxValorAportes)
		    OR (@noAplicarCriterioCantVecesMoroso = 1 OR EXISTS(SELECT car.carid FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora)))
	    group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro

			--*******************************************************************--
			----FIN AJUSTES FILTROS NO FORMALIZADOS 72837
		UNION
	SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial, -- (case when nof.estado is not null then nof.estado else  empl.empestadoEmpleador end ) AS empestadoEmpleador,
			isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		    	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(ap.apgValTotalApoObligatorio) FROM AporteGeneral ap WHERE ap.apgEmpresa=emp.empId 
				AND ap.apgperiodoAporte BETWEEN @periodoInicialEmpleador AND @periodoFinalEmpleador) as valorAportes,
			(SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
				AND car.carperiodoDeuda BETWEEN @periodoInicialMoraEmpleador AND @periodoFinalMoraEmpleador) as vecesMoroso,
			(SELECT COUNT(roa.roaid) FROM RolAfiliado roa WHERE roa.roaempleador=empl.empid AND roa.roaEstadoAfiliado=@estadoAfiliado) as cantidadTrabajadores,
		    'EMPLEADOR' as tipoAportante,car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Empleador empl JOIN Empresa emp ON empl.empempresa=emp.empid 
		JOIN Persona per on emp.emppersona = per.perid
		JOIN Cartera car on car.carpersona=per.perid
		--LEFT JOIN @noFormalizados nof on nof.perId=per.perId
		 WHERE --nof.estado in(select * from @filtroEstado )
		  (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		 AND car.carTipoLineaCobro in (select   * from @filtrosLineaCobro)
        AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN ('EMPLEADOR')))
			OR (@validacionesPILA=1 AND (emp.empEnviadoAFiscalizacion = 1 AND emp.empMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
				AND emp.empFechaFiscalizacion BETWEEN @fechaInicialEmpleadorP AND @fechaFinalEmpleadorP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,emp.empid,empl.empid,per.perid,/*nof.estado,*/empl.empestadoEmpleador,car.carTipoLineaCobro
		------------------------------------------------------------------------------------------
		UNION
		SELECT per.perTipoIdentificacion as tipoIdentificacion,per.pernumeroIdentificacion as numeroIdentificacion,per.perrazonsocial as razonSocial,  --(case when nof.estado is not null then nof.estado else roa.roaEstadoAfiliado end ) AS roaestadoAfiliado,
		    isnull((select vistaemp.empestadoempleador 
		from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
		where vistaemp.perid=per.perid ),'')  as empestadoEmpleador,
		CASE WHEN EXISTS (select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) THEN
		       	(select max(car.carEStadoCArtera) FROM Cartera car WHERE car.carpersona=per.perid and car.carEstadoOperacion=@estadoOperacion) ELSE 'AL_DIA' END AS estadoCartera,
			(SELECT AVG(apd.apdAporteObligatorio) FROM AporteDetallado apd JOIN AporteGeneral apg on apd.apdAporteGeneral =apg.apgId WHERE apd.apdPersona=per.perid 
		        AND apd.apdTipoCotizante = roa.roaTipoAfiliado AND apg.apgperiodoAporte BETWEEN @periodoInicial AND @periodoFinal) as valorAportes,
		    0 as cantidadTrabajadores,
		    (SELECT COUNT(car.carid) FROM Cartera car WHERE car.carpersona=per.perid 
		        AND car.carperiodoDeuda BETWEEN @periodoInicialMora AND @periodoFinalMora 
		        AND car.carTipoSolicitante = (CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END)) as vecesMoroso,
	    	CASE WHEN roa.roaTipoAfiliado='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' ELSE roa.roaTipoAfiliado END as tipoAportante, car.carTipoLineaCobro
			--CASE WHEN @incluirLC2 = 1 and  @incluirLC3 = 0 THEN
		 --   ( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC2%' and car.carpersona=per.perid ) WHEN @incluirLC2 = 0 and @incluirLC3 = 1  THEN
			--( SELECT top 1 carTipoLineaCobro FROM Cartera car where carTipoLineaCobro like '%LC3%' and car.carpersona=per.perid )WHEN @incluirLC2= 1 and  @incluirLC3= 1  THEN
	  --   	( SELECT top 1 carTipoLineaCobro from Cartera car where carTipoLineaCobro like '%LC2%' or  carTipoLineaCobro like '%LC3%'and  car.carpersona=per.perid ) END AS tipoLineaCobro
	    FROM Persona per
		JOIN Cartera car on car.carpersona=per.perid
	    JOIN Afiliado afi on afi.afiPersona = per.perId
	   	JOIN RolAfiliado roa ON roaAfiliado = afi.afiId
	   -- LEFT JOIN @noFormalizados nof on nof.perId=per.perId

		WHERE --nof.estado in(select * from @filtroEstado )
		 (select vistaemp.empestadoempleador 
								from [dbo].[VW_EstadoAfiliacionempleadorcaja] vistaemp
								where vistaemp.perid=per.perid) LIKE 'NO_FORMALIZADO%'
		AND car.carTipoLineaCobro in (select * from @filtrosLineaCobro)
	    AND per.perId not in (select car.carPersona FROM Cartera car WHERE car.carEstadoOperacion=@estadoOperacion and car.carEStadoCArtera=@estadoCarteraMoroso)
	    AND roa.roaTipoAfiliado in (SELECT tipoAfiliado FROM @tipoAfiliados)
		AND ((@preventiva=1 AND EXISTS(SELECT spr.sprId from SolicitudPreventiva spr where spr.sprPersona=per.perId AND spr.sprFechaFiscalizacion 
				BETWEEN @fechaInicialP AND @fechaFinalP AND spr.sprRequiereFiscalizacion =1 AND spr.sprTipoSolicitanteMovimientoAporte IN 
				(SELECT tipoSolicitante from @tipoSolicitantes)))
			OR (@validacionesPILA=1 AND (roa.roaEnviadoAFiscalizacion = 1 AND roa.roaMotivoFiscalizacion in (SELECT motivoFiscalizacion FROM @motivosFiscalizacion) 
			AND roa.roaFechaFiscalizacion BETWEEN @fechaInicialP AND @fechaFinalP)))
		group by per.perTipoIdentificacion,per.pernumeroIdentificacion,per.perrazonsocial,per.perid,roa.roaTipoAfiliado,/*nof.estado,*/roa.roaestadoAfiliado,car.carTipoLineaCobro)aportantes
		)AS C
 end
  
--EXEC [dbo].[USP_ExecuteCARTERAConsultarGestionFiscalizacion]'20220410','20230410','20220411','20230411','20220111','20230111','20220111','20230111','PENSIONADO','','0','1','0','1','-5000.0','11','50','0','0','20220410','20230410','20220411','20230411',''
--,'OTROS ESTADOS','0','0'

END

