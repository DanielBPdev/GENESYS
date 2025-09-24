
-- =================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 15 DE 2021
-- Description: EXTRAE LA INFORMACION DE LA ENTIDAD EMPRESAS DESDE GENESYS AL ESQUEMA DE DATOS.
----exec sap.USP_GetEmpresas_DocumentoId '903111999','NIT'
-- =================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetEmpresas_DocumentoId] 
	@documentoId nvarchar(20), 
	@tipoDocumentoId nvarchar(20),
	@solNumeroRadicacion varchar(30) = null

	-- Calcular el valor del tipo de documento de identificacion CUANDO VIENE DEL MONITOR DE INTEGRACION

AS
BEGIN
SET NOCOUNT ON;

Declare @descentralizada nvarchar(20)
Declare @numerodoc nvarchar(20)

	--SELECT @tipoDocumentoId = perTipoIdentificacion
	--FROM Persona
	--WHERE perNumeroIdentificacion = @documentoId;

	-----Cuando los documentos no estaban bien parametrizados en Genesys



CREATE TABLE #Empresas_G2E(
	[fecIng] [date] NULL,
	[horaIng] [time] NULL,
	[operacion] [varchar](1) NULL,
	[codigoSAP] [varchar](10) NULL,
	[tipoDoc] [varchar](3) NULL,
	[numeroDoc] [varchar](16) NULL,
	[digVerif] [varchar](1) NULL,
	[nombreEmpresa] [varchar](250) NULL,
	[direcc] [varchar](300) NULL,
	[ciudad] [varchar](5) NULL,
	[region] [varchar](3) NULL,
	[telefono] [varchar](10) NULL,
	[celular] [varchar](10) NULL,
	[email] [varchar](255) NULL,
	[tipoAfil] [varchar](3) NULL,
	[afiliadoA] [varchar](2) NULL,
	[tarifaServ] [varchar](2) NULL,
	[estado] [varchar](1) NULL,
	[grupoCta] [varchar](4) NULL,
	[celCorresp] [varchar](10) NULL,
	[dptoCorresp] [varchar](3) NULL,
	[dirCorresp] [varchar](300) NULL,
	[munCorresp] [varchar](5) NULL,
	[telCorresp] [varchar](10) NULL,
	[morosa] [varchar](1) NULL,
	[nroTrabInsc] [int] NULL,
	[solPazySalvo] [varchar](1) NULL,
	[tipoSector] [varchar](1) NULL,
	[tipoCliente] [varchar](1) NULL,
	[trasladoCaja] [varchar](1) NULL,
	[ultimaCCFProcedencia] [varchar](100) NULL,
	[vlrAporte] NUMERIC(10,0) NULL,
	[autEnvio] [varchar](1) NULL,
	[fecConstitucion] [date] NULL,
	[fecAfil] [date] NULL,
	[fecRetiro] [date] NULL,
	[fecFormulario] [date] NULL,
	[actEco] [varchar](4) NULL,
	[nucleo] [varchar](3) NULL,
	[fecIngreso] [date] NULL,
	[arl] [varchar](3) NULL,
	[paginaWeb] [varchar](256) NULL,
	[tipoPersona] [varchar](1) NULL,
	[telefono2] [varchar](10) NULL,
	[autCorreo] [varchar](1) NULL,
	[codPostal] [varchar](10) NULL,
	[nroIntentos] [int]  NULL,
	[fecProceso] [date] NULL,
	[horaProceso] [time] NULL,
	[estadoReg] [varchar](1) NULL,
	[observacion] [varchar](2000) NULL,
	[usuario] [varchar](50) NULL,
	[codigoGenesys] [bigint] NULL
)

-- VALIDA PARA EL MONITOR DE INTEGRACI0NES QUE VIENE SIN TIPO DE IDENTIFICACION

IF @tipoDocumentoId = 'INT' BEGIN
	SET  @tipoDocumentoId = 'NIT'
END --IF
 
-- ELIMINACION DE REGISTROS CON ERROR Y QUE TIENEN EL MISMO DOCUMENTO DE IDENTIDAD.

DELETE sap.Empresas_G2E
FROM sap.Empresas_G2E WITH (NOLOCK)
INNER JOIN Persona WITH(NOLOCK) ON codigoGenesys = perId
WHERE perNumeroIdentificacion = @documentoId
AND perTipoIdentificacion = @tipoDocumentoId
AND estadoreg IN ('E', 'P')

-- Consulta de delta de afiliaciones en Genesys

INSERT INTO #Empresas_G2E
([fecing],[horaing],[operacion],[codigosap],[tipodoc],[numerodoc],[digverif],[nombreempresa],[direcc],[ciudad],[region],
		   [telefono],[celular],[email],[afiliadoa],[tarifaserv],[estado],[grupocta],[celcorresp],[dptocorresp],[dircorresp],
		   [muncorresp],[telcorresp],[morosa],[nrotrabinsc],[solpazysalvo],[tiposector],[tipocliente],[trasladocaja],[ultimaCCFProcedencia],
		   [autenvio],[fecconstitucion],[fecafil],[fecretiro],[fecformulario],[acteco],[nucleo],[fecingreso],[arl],[paginaweb],
		   [tipopersona],[telefono2],[autcorreo],[codpostal],[codigoGenesys])	    
SELECT 
	CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
	CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			 CASE WHEN codigoSAP IS NULL THEN 'I' ELSE 'U' END   AS Operacion, --- cambio 20220127 por olga vega 
		 CASE WHEN codigoSAP IS NULL THEN '' ELSE codigoSAP END  AS codigoSAP,--- cambio 20220127 por olga vega 
	SUBSTRING (TipoDocumentoCaja,1,3) AS tipoDoc, 
	per.perNumeroIdentificacion  AS NumeroDoc, 
	CONVERT(VARCHAR, per.perDigitoVerificacion) AS digVerif,
	per.perRazonSocial AS nombreEmpresa, 
	REPLACE(UPPER(REPLACE(UPPER(ubi1.ubiDireccionFisica),'  ', ' ')),'#', '') AS direcc, 
	CONVERT(VARCHAR, mun.munCodigo) AS ciudad, 
	CONVERT(VARCHAR, dep.depCodigo) AS Region,
	CONVERT(VARCHAR, ubi1.ubiTelefonoFijo) AS Telefono, 
	ubi1.ubiTelefonoCelular  AS celular,
	LOWER(ubi1.ubiEmail) AS email, 
	CASE WHEN eor.empEstadoEmpleador = 'ACTIVO' THEN '01'
		ELSE '17' END AS afiliadoA,
	CASE WHEN eor.empEstadoEmpleador = 'ACTIVO' THEN '08'
		ELSE '09' END AS tarifaServ,
	CASE WHEN eor.empMotivodesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO' THEN 'R'
		WHEN eor.empEstadoEmpleador = 'ACTIVO' THEN 'A'
		ELSE 'A' END AS estado,
	CASE WHEN sue.sueSucursalPrincipal = 1 THEN 'DCIA'
		ELSE 'DSUC' END AS grupoCta,
	  ubi2.ubiTelefonoCelular  AS celCorresp, 
	CONVERT(VARCHAR, dep2.depCodigo) AS dptoCorresp, -- revisar datos correspondencia
	REPLACE(UPPER(REPLACE(UPPER(ubi2.ubiDireccionFisica),'  ', ' ')),'#', '') AS dirCorresp, -- revisar datos correspondencia
	CONVERT(VARCHAR, mun2.munCodigo) AS munCorresp,  -- revisar datos correspondencia
	ubi2.ubiTelefonoFijo AS telCorresp, -- revisar datos correspondencia
	CASE WHEN car.carEstadoCartera = 'MOROSO' THEN 'S'
	WHEN car.carEstadoCartera = 'AL_DIA' THEN 'N' END AS morosa,
	(select isnull(count(roaid ),0) from rolafiliado WITH(NOLOCK)
	where roaempleador = eor.empid and roaEstadoAfiliado = 'activo'
	 )
	--eor.empNumeroTotalTrabajadores 
	AS nrotrabinsc,
	
	CASE WHEN car.carEstadoCartera = 'MOROSO' THEN 'N' -- Por revisar por condicion de retiro
	WHEN car.carEstadoCartera = 'AL_DIA' THEN 'S' END AS solpazysalvo,
	CASE WHEN emr.empNaturalezaJuridica = 'MIXTA' THEN 'M'
	WHEN emr.empNaturalezaJuridica = 'PUBLICA' THEN 'P' 
	WHEN emr.empNaturalezaJuridica = 'PRIVADA' THEN 'R'
	WHEN emr.empNaturalezaJuridica = 'ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS' THEN 'R' 
	WHEN emr.empNaturalezaJuridica = 'ORGANISMOS_MULTILATERALES' THEN 'R' END AS tipoSector,
		'E' AS tipoCliente,  -- Revisar GAP
	CASE WHEN emr.empUltimaCajaCompensacion is null THEN 'N' -- No se esta cargando en migracion
	ELSE 'S' END AS trasladoCaja,
	CASE WHEN emr.empUltimaCajaCompensacion = '2' THEN CONVERT(VARCHAR, '07') -- CAMACOL
		WHEN emr.empUltimaCajaCompensacion = '4' THEN CONVERT(VARCHAR, '20') -- COMFAMA ANTIOQUIA
		ELSE '' END AS ultimaCCFProcedencia,
		--WHEN emr.empUltimaCajaCompensacion is null THEN CONVERT(VARCHAR, '')
	CASE WHEN ubi1.ubiAutorizacionEnvioEmail = 1 THEN 'A' 
	WHEN ubi1.ubiAutorizacionEnvioEmail = 0 THEN 'N'
	WHEN ubi1.ubiAutorizacionEnvioEmail IS NULL THEN 'Z'
	END AS autEnvio,
	emr.empFechaConstitucion AS fecConstitucion,
	eor.empFechaCambioEstadoAfiliacion AS fecAfil,
	eor.empFechaRetiro AS fecRetiro,
 
 
	ISNULL((SELECT DISTINCT ich.ichFechaRecepcionDocumento 
	   from  ItemChequeo ich WITH (NOLOCK) 
	inner join Solicitud WITH (NOLOCK) on solid = ichSolicitud
	   where solNumeroRadicacion = @solNumeroRadicacion 
	   and  ich.ichPersona = per.perid and ichRequisito = 87),eor.empFechaCambioEstadoAfiliacion) AS fecFormulario,---CAMBIO OLGA VEGA 20220215 porque necesitan es la fecha de recepcion del documento
	CONVERT (VARCHAR, ciiu.ciiCodigo) AS actEco,
	'' AS nucleo, -- Revisar GAP
	MAX(sol.solFechaCreacion) AS fecIngreso,
	CONVERT(VARCHAR, ma.CodigoCCF) AS arl,
	LOWER(emr.empPaginaWeb) AS paginaWeb,
	'E' AS tipoPersona,
	'' AS Telefono2, -- Validar el campo de telefono 2: En reunion 21 octubre se cambia Rodrigo y Juan Felipe a vacio.
	CASE WHEN ubi1.ubiAutorizacionEnvioEmail = 1 THEN 'S' 
	WHEN ubi1.ubiAutorizacionEnvioEmail = 0 THEN 'N'
	WHEN ubi1.ubiAutorizacionEnvioEmail IS NULL THEN 'N'	END AS autCorreo, -- Validar el campo
		ubi1.ubiCodigoPostal AS codPostal,
	per.perId AS codigoGenesys
	      FROM Persona per WITH (NOLOCK)
		INNER JOIN sap.MaestraIdentificacion h WITH (NOLOCK) ON  per.perTipoIdentificacion = h.TipoIdGenesys
		INNER JOIN Empresa emr WITH (NOLOCK) ON  per.perId = emr.empPersona
		LEFT JOIN sap.MaestraArl ma WITH(NOLOCK) ON  emr.empArl = ma.codigoGenesys
		LEFT JOIN CodigoCIIU ciiu WITH (NOLOCK) ON emr.empCodigoCIIU = ciiu.ciiId
		INNER JOIN Empleador eor WITH (NOLOCK) ON  emr.empID = eor.empEmpresa
		INNER JOIN SucursalEmpresa sue WITH (NOLOCK) ON emr.empid = sue.sueEmpresa AND sue.sueSucursalPrincipal = 1
		LEFT JOIN UbicacionEmpresa ube  WITH (NOLOCK) ON  emr.empId = ube.ubeEmpresa AND  ube.ubeTipoUbicacion = 'UBICACION_PRINCIPAL' 
		LEFT JOIN Ubicacion ubi1 WITH (NOLOCK) ON  ube.ubeUbicacion = ubi1.ubiID
		LEFT JOIN UbicacionEmpresa ube2  WITH (NOLOCK) ON  emr.empId = ube2.ubeEmpresa AND  ube2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'  
		LEFT JOIN Ubicacion ubi2 WITH (NOLOCK) ON  ube2.ubeUbicacion = ubi2.ubiID
		LEFT JOIN Municipio mun WITH (NOLOCK) ON ubi1.ubiMunicipio = mun.munID
		LEFT JOIN departamento dep ON  mun.munDepartamento = dep.depId -- POR SI NO HAY DATOS SE PASA A UN LEFT
		LEFT JOIN Municipio mun2 WITH (NOLOCK) ON ubi2.ubiMunicipio = mun2.munID
		INNER JOIN departamento dep2 ON  mun2.munDepartamento = dep2.depId
		LEFT JOIN Cartera car WITH (NOLOCK) ON  per.perID = car.carPersona
		LEFT JOIN SolicitudAfiliaciEmpleador sae WITH (NOLOCK) ON  eor.empid = sae.saeEmpleador
		LEFT JOIN Solicitud sol WITH (NOLOCK) ON  sae.saeSolicitudGlobal = sol.solId 
		
		LEFT JOIN sap.CodSAPGenesysDeudor   WITH (NOLOCK) ON per.perid = sap.CodSAPGenesysDeudor.codigoGenesys and tipo = 'E' --- cambio 20220127 por olga vega 
		   WHERE eor.empEstadoEmpleador  IS NOT NULL
		   AND per.perNumeroIdentificacion = @documentoId
		   AND per.perTipoIdentificacion = @tipoDocumentoId
		   -- AND sue.sueEstadoSucursal = 'ACTIVO'
		   --AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		   --AND ube2.ubeTipoUbicacion='ENVIO_CORRESPONDENCIA'
		   GROUP BY TipoDocumentoCaja, per.perNumeroIdentificacion, per.perDigitoVerificacion, per.perRazonSocial, ubi1.ubiDireccionFisica, mun.munCodigo, dep.depCodigo, 
		   ubi1.ubiTelefonoFijo, ubi1.ubiTelefonoCelular, ubi1.ubiEmail, eor.empEstadoEmpleador, eor.empMotivoDesafiliacion, sue.sueSucursalPrincipal, ubi2.ubiTelefonoCelular,
		   dep2.depcodigo, ubi2.ubiDireccionFisica, mun2.munCodigo, ubi2.ubiTelefonoFijo, car.carEstadoCartera, eor.empNumeroTotalTrabajadores, emr.empNaturalezaJuridica,
		   emr.empUltimaCajaCompensacion, ubi1.ubiAutorizacionEnvioEmail, emr.empFechaConstitucion, eor.empFechaCambioEstadoAfiliacion,
		   eor.empFechaRetiro, ciiu.ciiCodigo, ma.codigoCCF, emr.empPaginaWeb, ubi1.ubiCodigoPostal, per.perId
		  , sap.CodSAPGenesysDeudor.codigoSAP, eor.empid

	--select * from #Empresas_G2E
	
		-- Cálculo del tipo de afiliacion
		SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion,		
		CASE WHEN eor.empEstadoEmpleador = 'ACTIVO' THEN 'EDN'
		WHEN eor.empEstadoEmpleador = 'INACTIVO' THEN 'E02'
		ELSE '' END AS tipoAfil
		INTO #tmp_TipoAfiliacion
		FROM Persona per WITH (NOLOCK)
		INNER JOIN sap.MaestraIdentificacion h WITH (NOLOCK) ON  per.perTipoIdentificacion = h.TipoIdGenesys
		INNER JOIN Empresa emr WITH (NOLOCK) ON  per.perId = emr.empPersona
		INNER JOIN Empleador eor WITH (NOLOCK) ON  emr.empID = eor.empEmpresa
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumentoId
		
	UNION
		SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion,		
		CASE WHEN bem.bemBeneficioactivo = 1 THEN 'EEA'
		WHEN bem.bemBeneficioactivo = 0 THEN 'EEX'
		ELSE '' END AS tipoAfil
		FROM Persona per WITH (NOLOCK)
		INNER JOIN sap.MaestraIdentificacion h WITH (NOLOCK) ON  per.perTipoIdentificacion = h.TipoIdGenesys
		INNER JOIN Empresa emr WITH (NOLOCK) ON  per.perId = emr.empPersona
		INNER JOIN Empleador eor WITH (NOLOCK) ON  emr.empID = eor.empEmpresa
		INNER JOIN BeneficioEmpleador bem WITH (NOLOCK) ON  eor.empID = bem.bemEmpleador
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumentoId
	UNION
		SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion,		
		CASE WHEN epa.epaTipoAfiliacion = 'ENTIDADES_PENSIONALES' THEN 'EPE'
		WHEN epa.epaTipoAfiliacion = 'ENTIDADES_INDEPENDIENTES' THEN 'EIN'
		ELSE '' END AS tipoAfil
		FROM Persona per WITH (NOLOCK)
		INNER JOIN sap.MaestraIdentificacion h WITH (NOLOCK) ON  per.perTipoIdentificacion = h.TipoIdGenesys
		INNER JOIN Empresa emr WITH (NOLOCK) ON  per.perId = emr.empPersona
		INNER JOIN Empleador eor WITH (NOLOCK) ON  emr.empID = eor.empEmpresa
		INNER JOIN EntidadPagadora epa WITH (NOLOCK) ON  epa.epaEmpresa = emr.empid
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumentoId
	 


		UPDATE #Empresas_G2E
		SET tipoAfil = tmp.tipoAfil
		FROM #Empresas_G2E emp WITH (NOLOCK)
		JOIN #tmp_TipoAfiliacion tmp WITH (NOLOCK) ON emp.numerodoc = tmp.perNumeroIdentificacion
		JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  emp.tipodoc = ide.TipoIdHomologado
		AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	 --select * from #Empresas_G2E
	 --select * from #tmp_TipoAfiliacion

		-- Cálculo del valor del aporte vlrAporte
		SELECT perNumeroIdentificacion, perTipoIdentificacion, isnull(tmp.TotalApoObligatorio,0) AS vlrAporte, PeriodoAporte
		INTO #tmp_vlrAporte
		FROM ( SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion,
			CASE WHEN apg.apgPeriodoAporte = MAX(apg.apgPeriodoAporte) 
			OVER (PARTITION BY perNumeroIdentificacion, perTipoIdentificacion) THEN apg.apgPeriodoAporte ELSE NULL END AS [PeriodoAporte],
			SUM(apgValTotalApoObligatorio) AS TotalApoObligatorio
			FROM AporteGeneral apg WITH (NOLOCK)
			INNER JOIN Empresa emp WITH (NOLOCK) ON apg.apgEmpresa = emp.empId
			INNER JOIN Persona per WITH (NOLOCK) ON emp.empPersona = per.perId
			WHERE per.perNumeroIdentificacion = @documentoId
			AND per.perTipoIdentificacion = @tipoDocumentoId
			AND apg.apgPeriodoAporte IS NOT NULL  
			GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, apg.apgPeriodoAporte
			) tmp 
		WHERE tmp.PeriodoAporte IS NOT NULL

		UPDATE #Empresas_G2E
		SET vlrAporte = tmp.vlrAporte
		FROM #Empresas_G2E emp WITH (NOLOCK)
		JOIN #tmp_vlrAporte tmp WITH (NOLOCK) ON emp.numerodoc = tmp.perNumeroIdentificacion
		JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  emp.tipodoc = ide.TipoIdHomologado
		AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

		---------------------------------- Validar que la descentralizada si este en la tabla y tenga codigo sap. --------------------------------------
		select @numerodoc = (select DISTINCT numeroDoc from #Empresas_G2E )

		select @descentralizada = (select DISTINCT desce.prdNumeroIdentificacionSerial from #Empresas_G2E emp
		inner join PreRegistroEmpresaDescentralizada desce on emp.numeroDoc = desce.prdNumeroIdentificacionSerial where emp.numeroDoc = desce.prdNumeroIdentificacionSerial)

	


		IF (((@numeroDoc = @descentralizada) and ((select codigoSAP from #Empresas_G2E )<>''))or(@descentralizada is null))
		
		begin 


		-------- Actualizacion numeroDoc en empresas descentralizadas con el nit de la centralizada sin alterar codigo sap
		--select * from #Empresas_G2E

		update #Empresas_G2E
		set numeroDoc = desce.prdNumeroIdentificacion
		from persona per 
		inner join PreRegistroEmpresaDescentralizada desce on per.perNumeroIdentificacion = desce.prdNumeroIdentificacionSerial
		where numeroDoc = per.perNumeroIdentificacion

		
		--print 'SI DUNCIONA'

		

	INSERT INTO [SAP].[Empresas_G2E]
	([fecing],[horaing],[operacion],[codigosap],[tipodoc],[numerodoc],[digverif],[nombreempresa],[direcc],[ciudad],[region],
		   [telefono],[celular],[email],[tipoafil],[afiliadoa],[tarifaserv],[estado],[grupocta],[celcorresp],[dptocorresp],[dircorresp],
		   [muncorresp],[telcorresp],[morosa],[nrotrabinsc],[solpazysalvo],[tiposector],[tipocliente],[trasladocaja],[ultimaCCFProcedencia],
		   [vlraporte],[autenvio],[fecconstitucion],[fecafil],[fecretiro],[fecformulario],[acteco],[nucleo],[fecingreso],[arl],[paginaweb],
		   [tipopersona],[telefono2],[autcorreo],[codpostal],[codigoGenesys],[FechaEjecucion])

	SELECT DISTINCT [fecing],[horaing],[operacion],[codigosap],[tipodoc],[numerodoc],[digverif],[nombreempresa],[direcc],[ciudad],[region],
		   [telefono],[celular],[email],[tipoafil],[afiliadoa],[tarifaserv],[estado],[grupocta],[celcorresp],[dptocorresp],[dircorresp],
		   [muncorresp],[telcorresp],[morosa],
		   [nrotrabinsc],
		   [solpazysalvo],[tiposector],[tipocliente],[trasladocaja],[ultimaCCFProcedencia],
		   isnull([vlraporte],0),[autenvio],[fecconstitucion],[fecafil],[fecretiro],[fecformulario],[acteco],[nucleo],[fecingreso],[arl],[paginaweb],
		   [tipopersona],[telefono2],[autcorreo],[codpostal],[codigoGenesys], GETDATE() -'05:00:00'
	FROM #Empresas_G2E WITH(NOLOCK)


		End

select * from #Empresas_G2E

	TRUNCATE TABLE #tmp_TipoAfiliacion;
	TRUNCATE TABLE #Empresas_G2E;
	TRUNCATE TABLE #tmp_vlrAporte;
END