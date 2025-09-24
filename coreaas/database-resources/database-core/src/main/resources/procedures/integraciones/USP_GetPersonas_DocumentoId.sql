-- =================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: EXTRAE LA INFORMACIoN DE LA ENTIDAD PERSONAS DESDE GENESYS AL ESQUEMA DE DATOS.
-- =================================================================================================
CREATE OR ALTER   PROCEDURE [sap].[USP_GetPersonas_DocumentoId] @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @solNumeroRadicacion varchar (30) = NULL 

AS


BEGIN

SET NOCOUNT ON;
 
DECLARE @SMMLV BIGINT;
DECLARE @BENEFICIARIO INT; 

-- Consultar parametro de valor de salario minimo
SET @SMMLV = (SELECT CONVERT(BIGINT, prmValor)
FROM parametro WITH(NOLOCK)
WHERE prmNombre = 'SMMLV');

-- Calcular el valor del tipo de documento de identificacion CUANDO VIENE DEL MONITOR DE INTEGRACIoN
IF (@tipoDocumento = 'INT') BEGIN
	SELECT @tipoDocumento = perTipoIdentificacion
	FROM Persona WITH(NOLOCK)
	WHERE perNumeroIdentificacion = @documentoId;
END

drop table if exists #Personas_G2E
create table #Personas_G2E
(
	fecing date null, horaing time null, operacion varchar(1) null, codigosap varchar(10) null, codigogenesys bigint null, tipodoc varchar(2) null, numerodoc varchar(16) null,nombre1 varchar(35) null,nombre2 varchar(35) null,
	apellido1 varchar(35) null,apellido2 varchar(35) null,fecnac varchar(40) null,estcivil varchar(2) null,direcc varchar(60) null,ciudad varchar(5) null,region varchar(3) null,telefono varchar(10) null,celular varchar(10) null,
	email varchar(241) null,tipoafil varchar(2) null,afiliadoa varchar(2) null,categoria varchar(2) null,estado varchar(1) null,sexo varchar(1) null,autenvio varchar(1) null,discapacidad varchar(1) null,fecafil date null,
	fecretiro date null,fecformulario date null,nucleo varchar(5) null,fecexped date null,fecingreso date null,gradoesc varchar(2) null,grupofam smallint null,indsub varchar(1) null,niveledu varchar(2) null,ccostos varchar(10) null,
	nrotarjeta varchar(16) null,ocupacion varchar(200) null,porcdisca smallint null,profesion int null,rangosalario varchar(3) null,sectorresidencia varchar(10) null,tipocontrato varchar(1) null,tiposalario varchar(1) null,
	tipopersona varchar(1) null,codpostal varchar(10) null,nomempresa varchar(40) null,nitempresa varchar(40) null,morosa varchar(1) null,nrointentos smallint null,fecproceso date null,horaproceso time null,estadoreg varchar(1) null,
	observacion varchar(2000) null,usuario varchar(50) null,perfil varchar(50) null
)

-- TRABAJADOR ACTIVO y RETIRADO, BENEFICIARIO ACTIVO y RETIRADO
-- Calcula los datos de contacto de la persona para los casos
 
INSERT INTO #Personas_G2E ([fecing],[horaing],[operacion],[codigosap],[codigoGenesys],[tipodoc],[numerodoc],[nombre1],[nombre2],[apellido1],[apellido2],
[fecnac],[estcivil],[direcc],[ciudad],[region],[telefono],[celular],[email],[estado],[sexo],[autenvio],[FecExpeD],[GradoEsc],[NivelEdu],[Nucleo],
[PorcDisca],[profesion],[SectorResidencia],[CodPostal],[estadoreg]  )
SELECT	DISTINCT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
		CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				 CASE WHEN codigoSAP IS NULL THEN 'I' ELSE 'U' END AS Operacion,  ---cambio 20220127 por olga vega 
		 CASE WHEN codigoSAP IS NULL THEN '' ELSE codigoSAP END AS codigoSAP,---cambio 20220127 por olga vega 
		per.perId AS codigoGenesys,
		ide.TipoIdHomologado AS tipoDoc,
		per.perNumeroIdentificacion AS NumeroDoc, 
		per.perPrimerNombre AS nombre1, 
		per.perSegundoNombre AS nombre2, 
		per.perPrimerApellido AS apellido1, 
		per.perSegundoApellido AS apellido2, 
		ISNULL(ped.pedFechaNacimiento ,'20220101') AS fecNac, -- condicion de nulo, John Sotelo 23062022 - por pruebas AS fecNac,
		CASE WHEN mec.EstadocivilCCF IS NULL 
		AND b.benTipoBeneficiario like '%HIJ%'
			 THEN 'S' 
			ELSE CASE WHEN mec.EstadocivilCCF IS NULL THEN '' 
			ELSE mec.EstadocivilCCF END END AS estcivil,----cambio olga vega 20220204 
		left(ISNULL(REPLACE(UPPER(REPLACE(UPPER(ubi.ubiDireccionFisica),'  ', ' ')),'#', ''),''),60) AS direcc, -- Por SAT no recibe # ni espacios adicionales -- se añade isnull, John Sotelo 290022
		ISNULL(mun.muncodigo,'') AS ciudad,
		ISNULL(SUBSTRING(mun.munCodigo, 1, 2), '') AS Region, 
		ubi.ubiTelefonoFijo AS Telefono, 
		ubi.ubiTelefonoCelular AS Celular, 
		ubi.ubiEmail AS email,
		CASE WHEN ped.pedFallecido = 1 THEN 'R' 
			ELSE 'A' END AS Estado,
			isnull(mg.cod,'') AS Sexo,
		--CASE WHEN ubi.ubiAutorizacionEnvioEmail = 1 THEN 'A' 
		--	WHEN ubi.ubiAutorizacionEnvioEmail = 0 THEN 'N'
		--	WHEN ubi.ubiAutorizacionEnvioEmail IS NULL THEN 'Z' END AS autenvio,   validacion al dia '06102022
			CASE WHEN not exists (select * from Solicitud where solTipoTransaccion like '%web%' and solNumeroRadicacion = @solNumeroRadicacion) 
			THEN CASE WHEN ped.pedAutorizaUsoDatosPersonales in (1,0) AND afi.afiId is not null THEN 'A' 
			ELSE 'Z' END WHEN afi.afiId is null then 'Z' ELSE 'Z' END AS autenvio, 	
			----------------------------------- Agregado para validar que el campo si este trayendo informacion de manera correcta.
		ped.pedFechaExpedicionDocumento AS FecExpeD,
		ISNULL(ME.cod,'') AS GradoEsc,	--Se calcula para todos los tipos de personas y en cada uno se deja el campo vacio cuando no aplica
		CASE WHEN ped.pedNivelEducativo = 'NINGUNO'	THEN 'O'
			WHEN ped.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 'E'
			WHEN ped.pedNivelEducativo = 'SUPERIOR_PREGRADO' THEN 'U'
			WHEN ped.pedNivelEducativo = 'MEDIA_COMPLETA_ADULTOS' THEN 'B'
			WHEN ped.pedNivelEducativo = 'MEDIA_INCOMPLETA_ADULTOS' THEN 'C'
			WHEN ped.pedNivelEducativo = 'BASICA_SECUNDARIA_COMPLETA_ADULTOS' THEN 'B'
			WHEN ped.pedNivelEducativo = 'BASICA_SECUNDARIA_INCOMPLETA_ADULTOS' THEN 'C'
			WHEN ped.pedNivelEducativo = 'BASICA_PRIMARIA_COMPLETA_ADULTOS' THEN 'P'
			WHEN ped.pedNivelEducativo = 'BASICA_PRIMARIA_INCOMPLETA_ADULTOS' THEN 'I'
			WHEN ped.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 'O'
			WHEN ped.pedNivelEducativo = 'SUPERIOR' THEN 'U'
			WHEN ped.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 'T'
			WHEN ped.pedNivelEducativo = 'MEDIA_COMPLETA' THEN 'B'
			WHEN ped.pedNivelEducativo = 'MEDIA_INCOMPLETA' THEN 'C'
			WHEN ped.pedNivelEducativo = 'BASICA_SECUNDARIA_COMPLETA' THEN 'B'
			WHEN ped.pedNivelEducativo = 'BASICA_SECUNDARIA_INCOMPLETA' THEN 'C'
			WHEN ped.pedNivelEducativo = 'BASICA_PRIMARIA_COMPLETA' THEN 'P'
			WHEN ped.pedNivelEducativo = 'BASICA_PRIMARIA_INCOMPLETA' THEN 'I'
			WHEN ped.pedNivelEducativo = 'PREESCOLAR' THEN 'O' 
			ELSE '' END AS NivelEdu,
		'' AS Nucleo,  -- GAP
		0 AS PorcDisca, -- GENESYS NO TIENE PORCENTAJE DE DISCAPACIDAD, SE ACUERDA ENVIAR 0
		NULL AS profesion, -- SE ENVIA NULO AL NO TENERLO EN GENESYS
		CASE WHEN ped.pedResideSectorRural = 1 THEN 'R'
			WHEN ped.pedResideSectorRural = 0 THEN 'U' 
			ELSE 'U' END AS SectorResidencia,
		CASE WHEN ubi.ubiCodigoPostal IS NULL THEN ''
			ELSE ubi.ubiCodigoPostal END AS CodPostal,
		'P' as estadoreg  
	-- DISCAPACIDAD: se calcula posterior a esta consulta
	 FROM Persona per WITH (NOLOCK)
		LEFT JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK) ON  per.perID = ped.pedPersona
		LEFT JOIN core.dbo.Beneficiario b WITH (NOLOCK) ON  per.perID = b.benPersona
		LEFT JOIN core.dbo.GradoAcademico gra WITH (NOLOCK) ON ped.pedGradoAcademico = gra.graId
		LEFT JOIN core.dbo.Ubicacion ubi WITH (NOLOCK) ON  per.perUbicacionPrincipal = ubi.ubiID
		LEFT JOIN core.dbo.Municipio mun WITH (NOLOCK) ON ubi.ubiMunicipio = mun.munID
		LEFT JOIN core.[sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK)  ON  per.perTipoIdentificacion = ide.TipoIdGenesys
		LEFT JOIN core.[sap].[MaestraEstadoCivil] mec WITH (NOLOCK)  ON  ped.pedEstadoCivil = mec.EstadoCivilG
		LEFT JOIN core.sap.CodSAPGenesysDeudor  WITH (NOLOCK) ON per.perid = codigoGenesys and tipo = 'P'---cambio 20220127 por olga vega 
		LEFT JOIN core.dbo.Afiliado afi WITH(NOLOCK) on per.perId = afi.afiPersona -------  agregado 20221006 por camilo gomez para validacion de campo autenvio.
		LEFT JOIN core.sap.maestra_generos mg WITH(NOLOCK) on ped.pedGenero = mg.descripcion
		LEFT JOIN core.sap.maestra_escolaridad ME WITH(NOLOCK) on gra.graNombre = me.descripcion 

		WHERE per.perNumeroIdentificacion = @documentoId
		  AND per.perTipoIdentificacion = @tipoDocumento

--Calcular item de chequeo
  	drop table if exists #item
	select ichPersona,ichSolicitud,ichFechaRecepcionDocumento,ichRequisito,ichEstadoRequisito
	into #item
	from core.dbo.Persona with (nolock) 
	inner join ItemChequeo with (nolock) on perid = ichPersona 
	where ichRequisito in (89,90,91) 
	and perNumeroIdentificacion = @documentoId
	and perTipoIdentificacion = @tipoDocumento

	create nonclustered index [IX_ichEstadoRequisito]on #item ([ichEstadoRequisito],[ichRequisito]) include ([ichSolicitud],[ichFechaRecepcionDocumento])


--Calcular la condicion de invalidez DISCAPACIDAD
	drop table if exists #tmp_coinvalidez
	SELECT  per.perNumeroIdentificacion, per.perTipoIdentificacion, CASE WHEN coi.coiInvalidez = 1 THEN 'S' 
		ELSE 'N' END AS discapacidad
	INTO #tmp_coinvalidez
	FROM Persona per WITH (NOLOCK) 
	JOIN CondicionInvalidez coi WITH (NOLOCK) ON coi.coiPersona = per.perID
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento


-- Actualiza valores en caso contrario coloca vacio
	UPDATE #Personas_G2E
	SET discapacidad = tmp.discapacidad
	FROM #Personas_G2E per WITH (NOLOCK) 
	INNER JOIN #tmp_coinvalidez tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON ide.TipoIdHomologado = per.tipodoc
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.discapacidad IS NULL

	UPDATE #Personas_G2E
	SET discapacidad = ''
	FROM #Personas_G2E WITH(NOLOCK)
	WHERE discapacidad IS NULL


--Calcular la morosidad
	drop table if exists #tmp_cartera
	SELECT  per.perNumeroIdentificacion, perTipoIdentificacion, 
	CASE WHEN car.carEstadoCartera = 'MOROSO' THEN 'S' 
										WHEN car.carEstadoCartera = 'AL_DIA' THEN 'N' -- Validar el calculo
										ELSE 'N' END AS Morosa --car.carEstadoOperacion + '/' + car.carTipoLineaCobro AS Morosa,  ---Validar calculo
		INTO #tmp_cartera
			FROM dbo.Persona per WITH (NOLOCK) 
			LEFT JOIN dbo.cartera car WITH (NOLOCK) ON per.perID = car.carPersona
			WHERE per.perNumeroIdentificacion = @documentoId
			AND per.perTipoIdentificacion = @tipoDocumento	

	UPDATE #Personas_G2E
	SET morosa = tmp.morosa
	FROM #Personas_G2E per WITH (NOLOCK) 
	INNER JOIN #tmp_cartera tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON per.tipodoc =  ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.morosa IS NULL
	
--CALCULO DE TIPO DE PERSONA
----TRABAJADOR ACTIVO
	drop table if exists #tmp_TipoPersona
	SELECT  pertipoIdentificacion, perNumeroIdentificacion, roaEstadoAfiliado, Tipo
	INTO #tmp_TipoPersona
           FROM (
				SELECT per.pertipoIdentificacion,per.perNumeroIdentificacion, roa.roaEstadoAfiliado, roa.roaid,per.perid,
					   CASE WHEN roa.roaFechaAfiliacion = MIN(roa.roaFechaAfiliacion) over (partition by roa.roaafiliado) then  roa.roaFechaAfiliacion else NULL end as [FechaAfiliacion], 'Trabajador' AS 'Tipo'
				FROM dbo.Persona per WITH (NOLOCK)
				INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON per.perID = afi.afiPersona
				INNER JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
				 WHERE per.perNumeroIdentificacion = @documentoId
				 AND per.perTipoIdentificacion = @tipoDocumento
				  AND roa.roaEstadoAfiliado IN ('ACTIVO' )
				) ti
		WHERE ti.FechaAfiliacion IS NOT NULL
  UNION
-- BENEFICIARIO ACTIVO ----aqui verificar porque no coloca la fecha de afiliacion 


  SELECT  pertipoIdentificacion, perNumeroIdentificacion, benEstadoBeneficiarioAfiliado, Tipo
           FROM (
					SELECT  per.pertipoIdentificacion,per.perNumeroIdentificacion, ben.benEstadoBeneficiarioAfiliado, ben.benid,per.perid,
							CASE WHEN ben.benFechaAfiliacion = MIN(ben.benFechaAfiliacion) over (partition by ben.benpersona) 
							then  ben.benFechaAfiliacion else null end as [FechaAfiliacion], 
							'Beneficiario' AS 'Tipo'
					FROM core.dbo.Persona per WITH (NOLOCK)
					INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perId = ben.benPersona
					WHERE per.perNumeroIdentificacion = @documentoId 
					AND per.perTipoIdentificacion = @tipoDocumento
					AND ben.benEstadoBeneficiarioAfiliado IN ('ACTIVO' )
  				) ti
		WHERE ti.FechaAfiliacion IS NOT NULL
 UNION
  -- TRABAJADOR INACTIVO
SELECT pertipoIdentificacion, perNumeroIdentificacion, roaEstadoAfiliado, Tipo
       FROM (
				SELECT per.pertipoIdentificacion,per.perNumeroIdentificacion, roa.roaEstadoAfiliado, roa.roaid,per.perid,
					   CASE WHEN roa.roaFechaRetiro = MAX(roa.roaFechaRetiro) over (partition by roa.roaafiliado) then  roa.roaFechaAfiliacion else null end as [FechaRetiro], 'Trabajador' AS 'Tipo'
				FROM core.dbo.Persona per WITH (NOLOCK)
				INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON per.perID = afi.afiPersona
				INNER JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
				 WHERE per.perNumeroIdentificacion = @documentoId
				 AND per.perTipoIdentificacion = @tipoDocumento
				 AND roa.roaEstadoAfiliado IN ('INACTIVO')
				) ti
UNION
-- BENEFICIARIO INACTIVO 
  SELECT pertipoIdentificacion, perNumeroIdentificacion, benEstadoBeneficiarioAfiliado, Tipo
           FROM (
					SELECT  per.pertipoIdentificacion,per.perNumeroIdentificacion, ben.benEstadoBeneficiarioAfiliado, ben.benid,per.perid,
							CASE WHEN ben.benFechaRetiro = MAX(ben.benFechaRetiro) over (partition by ben.benpersona) then  ben.benFechaAfiliacion else null end as [FechaAfiliacion], 'Beneficiario' AS 'Tipo'
					FROM core.dbo.Persona per WITH (NOLOCK)
					INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perId = ben.benPersona
					WHERE per.perNumeroIdentificacion = @documentoId
					AND per.perTipoIdentificacion = @tipoDocumento
					AND ben.benEstadoBeneficiarioAfiliado IN ('INACTIVO' )
					) bi
		WHERE bi.FechaAfiliacion IS NOT NULL
	
  	
-- CALCULO DE ACUERDO AL TIPO DE PERSONA
IF EXISTS (SELECT perNumeroIdentificacion, perTipoIdentificacion 
             FROM #tmp_TipoPersona WITH (NOLOCK) 
			 WHERE Tipo = 'Trabajador' 
			   AND roaEstadoAfiliado = 'ACTIVO') -- trabajador activo
BEGIN
	-- Calcular tipos de afiliacion TIPOAFIL y USUARIO destino de la solicitud
	SELECT DISTINCT per.perNumeroIdentificacion, per.perTipoIdentificacion,
	CASE WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN 'MC'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN 'SD'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaEstadoAfiliado = 'INACTIVO' THEN 'P1'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND sol.solClasificacion = 'PENSION_FAMILIAR' THEN 'P1'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'REGULAR' THEN 'DN'
		WHEN roa.roaclaseIndependiente = 'TAXISTA' THEN 'TX'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaEmpleador IN (
			SELECT emp.empId 
			FROM core.dbo.BENEFICIOEMPLEADOR bee 
			INNER JOIN core.dbo.BENEFICIO ben ON bee.bemBeneficio = ben.befId 
			INNER JOIN core.dbo.Empleador emp ON bee.bemEmpleador = emp.empId 
			WHERE ben.befTipoBeneficio = 'LEY_1429') THEN 'EA'
		WHEN sol.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN 'IC'
		WHEN sol.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN 'ID'
		WHEN sol.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' THEN 'PC' 
		WHEN sol.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' THEN 'PK'
		WHEN sol.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO' THEN 'PL'
		WHEN sol.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO' THEN 'PM'
		WHEN sol.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' THEN 'PQ'
		WHEN sol.solClasificacion = 'FIDELIDAD_25_ANIOS' THEN 'PV'
		WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' THEN 'DN'
		ELSE NULL END AS TipoAfil,
		MAX(sol.solDestinatario) AS Usuario
		INTO #tmp_TipoAfil
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON per.perId = afi.afiPersona
	LEFT JOIN core.dbo.rolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
	LEFT JOIN core.dbo.SolicitudAfiliacionPersona sap WITH (NOLOCK) ON  roa.roaId = sap.sapRolAfiliado
	LEFT JOIN core.dbo.Solicitud sol WITH (NOLOCK) ON  sap.sapSolicitudGlobal = sol.solId
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND roa.roaEstadoAfiliado = 'ACTIVO'
	GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, sol.solClasificacion, roa.roaClaseTrabajador, roaClaseIndependiente, roaEmpleador, roaEstadoAfiliado, roa.roaTipoAfiliado

	
	-- Actualiza los valores en caso de ser nulo posterior a la actualizacion se deja como no afiliado (inactivo)
	
	SELECT perNumeroIdentificacion, perTipoIdentificacion, MAX(TipoAfil) AS TipoAfil, MAX(Usuario) AS Usuario
	INTO #tmp_TipoAfil_Alt
	FROM #tmp_TipoAfil WITH(NOLOCK)
	GROUP BY perNumeroIdentificacion, perTipoIdentificacion

	UPDATE #Personas_G2E
	SET tipoafil = CASE WHEN tmp.TipoAfil IS NULL THEN 'P1' ELSE tmp.TipoAfil END, 
	usuario = tmp.Usuario
	FROM #Personas_G2E per WITH (NOLOCK) 
	INNER JOIN #tmp_TipoAfil tmp WITH (NOLOCK)  ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK)  ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.tipoafil IS NULL

	UPDATE #Personas_G2E
	SET afiliadoA = '01',
	tipopersona = 'T'
	FROM #Personas_G2E per WITH(NOLOCK)
	INNER JOIN #tmp_TipoPersona tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK)  ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND tmp.Tipo = 'Trabajador' 
	AND tmp.roaEstadoAfiliado = 'ACTIVO'
	AND per.afiliadoA IS NULL;
		
	-- Calcular nombre de empresa, NIT y Centro de Costos, Ocupacion para afiliados cuando esta activo y con la afiliacion a la empresa mas antigua y vacio cuando no es afiliado
	
	SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion, CASE WHEN emp.empNombreComercial IS NULL THEN ''
		ELSE SUBSTRING(emp.empNombreComercial,1,40) END AS NomEmpresa,
		CASE WHEN per2.perNumeroIdentificacion IS NULL THEN ''
		ELSE per2.perNumeroIdentificacion END AS NITEmpresa, 
		MIN(roa.roaFechaIngreso) AS FechaIngreso
		INTO #tmp_Empresa
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perID = afi.afiPersona 
	LEFT JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
	LEFT JOIN core.dbo.Empleador eml WITH (NOLOCK) ON roa.roaEmpleador =  eml.empId 
	LEFT JOIN core.dbo.Empresa emp WITH (NOLOCK) ON  eml.empEmpresa = emp.empId
	LEFT JOIN core.dbo.persona per2 WITH (NOLOCK) ON emp.empPersona = per2.perId 
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND roa.roaEstadoAfiliado = 'ACTIVO'
	GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, emp.empNombreComercial, per2.perNumeroIdentificacion
	

	UPDATE #Personas_G2E
	SET nomempresa = tmp.NomEmpresa, 
	nitempresa = tmp.NITEmpresa,
	CCostos = '',  -- GAP
	Ocupacion = '',
	gradoesc = ''
	FROM #Personas_G2E per WITH (NOLOCK) 
	INNER JOIN #tmp_Empresa tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.tipoafil <> 'P1'
	AND per.nomempresa IS NULL;
	
	
	
		-- RangoSalario, TipoContrato, TipoSalario
	SELECT DISTINCT per.perNumeroIdentificacion, per.pertipoidentificacion,
	CASE WHEN roa.roaValorSalarioMesadaIngresos <= @SMMLV*2 THEN '001'
		WHEN roa.roaValorSalarioMesadaIngresos <= @SMMLV*4 THEN '002' 
		ELSE '003' END AS RangoSalario,
	CASE WHEN roa.roaTipoContrato = 'TERMINO_INDEFINIDO' THEN 'I'
		WHEN roa.roaTipoContrato = 'TERMINO_FIJO' THEN 'F'
		WHEN roa.roaTipoContrato = 'OBRA_LABOR' THEN 'F'
		ELSE '' END AS TipoContrato,
	CASE WHEN roa.roaTipoSalario = 'INTEGRAL' THEN 'I'
		WHEN roa.roaTipoSalario = 'VARIABLE' THEN 'V'
		WHEN roa.roaTipoSalario = 'FIJO' THEN 'F'
		ELSE '' END AS TipoSalario
	INTO #tmp_rolafiliado
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
	LEFT JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
	AND roa.roaEstadoAfiliado = 'ACTIVO'
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento

	

	UPDATE #Personas_G2E
	SET RangoSalario= tmp.RangoSalario,
	TipoContrato = tmp.TipoContrato,
	TipoSalario = tmp.TipoSalario
	FROM #Personas_G2E per WITH (NOLOCK) 
	INNER JOIN #tmp_rolafiliado tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK)  ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	
	drop table if exists #tmp_fechas1
	create table #tmp_fechas1 (perNumeroIdentificacion varchar(30),	perTipoIdentificacion varchar(30),	roaEstadoAfiliado varchar(30),FecAfil date, FecFormulario date, FecIngreso date)
	-- Fecha de afiliacion, fecha de retiro, fecha de formulario, fecha de ingreso
	insert #tmp_fechas1
	SELECT  per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado,
	MIN(roa.roaFechaAfiliacion) AS FecAfil,
	--NULL AS FecRetiro,
	ISNULL((SELECT DISTINCT ichFechaRecepcionDocumento 
	  FROM Solicitud  WITH (NOLOCK)  
	INNER JOIN #item  WITH (NOLOCK) ON solId = ichSolicitud 
	      WHERE  ichRequisito in (89,90,91)   AND ichEstadoRequisito = 'OBLIGATORIO'
		  AND  solNumeroRadicacion = @solNumeroRadicacion ) ,MIN(roa.roaFechaAfiliacion))
	  AS FecFormulario,---cambio OLGA vega 20220208
	MIN(roa.roaFechaIngreso) AS FecIngreso
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
	LEFT JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND roa.roaEstadoAfiliado = 'ACTIVO'
	GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado 

	
	
	UPDATE #Personas_G2E
	SET FecAfil = tmp.FecAfil,
	--FecRetiro = tmp.FecRetiro,
	FecFormulario = tmp.FecFormulario,
	-- para el caso de los pensionados e independientes la fechaIngreso = fechaAfiliacion
	FecIngreso = CASE WHEN (SELECT COUNT(0) FROM #tmp_TipoAfil WITH (NOLOCK) WHERE tipoAFil IN ('IC','ID','PC','PK','PL','PM','PQ','PV')) > 0 THEN tmp.FecAfil ELSE tmp.FecIngreso END
	FROM #Personas_G2E per WITH (NOLOCK) 
	JOIN #tmp_fechas1 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

	drop table if exists #GrupoSubsidio
	select distinct afiid,dsaValorSubsidioMonetario,dsaGrupoFamiliar,perNumeroIdentificacion,perTipoIdentificacion
	into #GrupoSubsidio
	FROM core.dbo.Persona per with (nolock) 
	inner join core.dbo.Afiliado afi with (nolock) on  per.perid = afi.afiPersona
	left join core.dbo.DetalleSubsidioAsignado dsa with (nolock) on  afi.afiid = dsa.dsaAfiliadoPrincipal
	where per.perNumeroIdentificacion = @documentoId
	and per.perTipoIdentificacion = @tipoDocumento

	create nonclustered index [IX_dsaGrupoFamiliar] on #GrupoSubsidio ([dsaGrupoFamiliar]) include ([dsaValorSubsidioMonetario])

	-- TRABAJADOR: Calcular el grupo familiar, beneficiario recibe subsidio
	SELECT grsub.perNumeroIdentificacion, grsub.perTipoIdentificacion,
	CASE WHEN grf.grfNumero IS NULL THEN '' ELSE grf.grfNumero END AS GrupoFam, 
		CASE WHEN grsub.dsaValorSubsidioMonetario IS NULL THEN '' 
		WHEN grsub.dsaValorSubsidioMonetario > 0 THEN 'S' 
		ELSE 'N' END AS IndSub -- Calcular
	INTO #tmp_grupoFamiliar
	FROM #GrupoSubsidio grsub WITH (NOLOCK) 
	LEFT JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON  grsub.afiId = grf.grfAfiliado
	
	
	
 
	print @documentoId
	print @tipoDocumento

	UPDATE #Personas_G2E
	SET grupofam = tmp.GrupoFam, indsub = tmp.indsub
	FROM #Personas_G2E per WITH (NOLOCK) 
	JOIN #tmp_grupoFamiliar tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK)  ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.grupofam IS NULL

	/*
	-- TRABAJADOR: Calcular el Numero Tarjeta
	SELECT DISTINCT per.perNumeroIdentificacion, perTipoIdentificacion,
	mtr.mtrNumeroTarjeta AS NroTarjeta
	INTO #tmp_numeroTarjeta
	FROM core.dbo.Persona per WITH (NOLOCK) 
	INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
	LEFT JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON  afi.afiId = grf.grfAfiliado
	LEFT JOIN core.dbo.AdminSubsidioGrupo asg WITH (NOLOCK) ON  grf.grfID = asg.asgGrupoFamiliar
	LEFT JOIN core.dbo.MedioDePago mdp WITH (NOLOCK) ON  asg.asgMedioDePago = mdp.mdpID AND mdp.mdptipo = 'TARJETA'
	LEFT JOIN core.dbo.MedioTarjeta mtr WITH (NOLOCK) ON mdp.mdpID = mtr.mdpId
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	
	
	UPDATE #Personas_G2E
	SET nrotarjeta = tmp.nroTarjeta
	FROM #Personas_G2E per WITH (NOLOCK) 
	JOIN #tmp_numeroTarjeta tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.nrotarjeta IS NULL
	*/
	
	UPDATE #Personas_G2E
	SET nrotarjeta = ''


	-- TRABAJADOR: Calcula la categoria
	SELECT top 1 per.pernumeroidentificacion, per.perTipoIdentificacion,
	cta.ctaFechaCambioCategoria AS ctaFechaCambioCategoria,
	CASE WHEN cta.ctaCategoria IS NULL OR cta.ctaCategoria = 'SIN_CATEGORIA' THEN 'D'
		ELSE cta.ctaCategoria END AS ctaCategoria
	INTO #tmp_categoria
	FROM core.dbo.Persona per WITH (NOLOCK)
	INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON per.perid = afi.afiPersona
	INNER JOIN core.dbo.categoriaAfiliado cta WITH (NOLOCK) ON afiid = cta.ctaAfiliado  
	WHERE perNumeroIdentificacion = @documentoId
	AND perTipoIdentificacion = @tipoDocumento
	AND cta.ctaEstadoAfiliacion = 'ACTIVO'
	Order by 3 desc    ----------------- cambio 19/10/2022  camolo gomez
	

	UPDATE #Personas_G2E
	SET categoria = tmp.ctaCategoria
	FROM #Personas_G2E per WITH (NOLOCK) 
	JOIN #tmp_categoria tmp WITH (NOLOCK)  ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.Categoria IS NULL



	DROP TABLE #tmp_Empresa;
	DROP TABLE #tmp_TipoAfil_Alt;
	DROP TABLE #tmp_TipoAfil;
	DROP TABLE #tmp_fechas1;
	DROP TABLE #tmp_rolafiliado;
	DROP TABLE #tmp_grupoFamiliar;
	--DROP TABLE #tmp_numeroTarjeta;
	DROP TABLE #tmp_categoria;

END
ELSE IF EXISTS (SELECT perNumeroIdentificacion, perTipoIdentificacion 
                 FROM #tmp_TipoPersona WITH(NOLOCK)
				WHERE Tipo = 'Beneficiario' 
				  AND roaEstadoAfiliado = 'ACTIVO') -- beneficiario activo
	BEGIN
	IF 	(SELECT COUNT(1)
                 FROM #tmp_TipoPersona WITH(NOLOCK)
				WHERE Tipo = 'Trabajador' 
				  AND roaEstadoAfiliado = 'ACTIVO') = 0

				BEGIN					

					-- Calcular tipos de afiliacion TIPOAFIL, USUARIO destino de la solicitud, calculo de 
					SELECT DISTINCT per.perNumeroIdentificacion, per.perTipoIdentificacion,
					CASE WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN 'MC'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN 'SD'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaEstadoAfiliado = 'INACTIVO' THEN 'P1'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND sol.solClasificacion = 'PENSION_FAMILIAR' THEN 'P1'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaClaseTrabajador = 'REGULAR' THEN 'DN'
						WHEN roa.roaclaseIndependiente = 'TAXISTA' THEN 'TX'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' AND roa.roaEmpleador IN (
							SELECT bee.bemEmpleador 
							FROM BENEFICIOEMPLEADOR bee  WITH (NOLOCK) 
							INNER JOIN BENEFICIO ben WITH (NOLOCK) ON bee.bemBeneficio = ben.befId 
							WHERE ben.befTipoBeneficio = 'LEY_1429') THEN 'EA'
						WHEN sol.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN 'IC'
						WHEN sol.solClasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN 'ID'
						WHEN sol.solClasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' THEN 'PC' 
						WHEN sol.solClasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO' THEN 'PK'
						WHEN sol.solClasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO' THEN 'PL'
						WHEN sol.solClasificacion = 'MAS_1_5_SM_2_POR_CIENTO' THEN 'PM'
						WHEN sol.solClasificacion = 'MENOS_1_5_SM_2_POR_CIENTO' THEN 'PQ'
						WHEN sol.solClasificacion = 'FIDELIDAD_25_ANIOS' THEN 'PV'
						WHEN sol.solClasificacion = 'TRABAJADOR_DEPENDIENTE' THEN 'DN'
						WHEN roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 'DN'
						WHEN roa.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' THEN 'IC'
						WHEN roa.roaTipoAfiliado = 'PENSIONADO' THEN 'PM'
						ELSE NULL END AS TipoAfil,
						MAX(sol.solDestinatario) AS Usuario,
						MIN(roa.roaFechaIngreso) AS fechaIngreso,
						MAX(ben.benTipoBeneficiario) AS benTipoBeneficiario,
						CASE WHEN mec.EstadocivilCCF IS NULL THEN '' 
							ELSE mec.EstadocivilCCF END AS estcivil
						INTO #tmp_TipoAfil2
					FROM core.dbo.Persona per WITH (NOLOCK) 
					LEFT JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perId = ben.benPersona 
					LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  ben.benAfiliado = afi.afiid
					LEFT JOIN core.dbo.rolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
					LEFT JOIN core.dbo.SolicitudAfiliacionPersona sap WITH (NOLOCK) ON  roa.roaId = sap.sapRolAfiliado
					LEFT JOIN core.dbo.Solicitud sol WITH (NOLOCK) ON  sap.sapSolicitudGlobal = sol.solId
					LEFT JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK) ON  per.perID = ped.pedPersona
					LEFT JOIN [sap].[MaestraEstadoCivil] mec WITH(NOLOCK) ON  ped.pedEstadoCivil = mec.EstadoCivilG
					WHERE per.perNumeroIdentificacion = @documentoId
					AND per.perTipoIdentificacion = @tipoDocumento
					AND ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
					AND roa.roaEstadoAfiliado = 'ACTIVO'
					GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, sol.solClasificacion, roa.roaClaseTrabajador, roa.roaClaseIndependiente, roa.roaEmpleador, roa.roaEstadoAfiliado, roa.roaTipoAfiliado, mec.EstadocivilCCF
					
					
					-- Actualiza los valores en caso de ser nulo posterior a la actualizacion se deja como no afiliado (inactivo)
		
					SELECT DISTINCT perNumeroIdentificacion, perTipoIdentificacion, MAX(TipoAfil) AS TipoAfil, MAX(Usuario) AS Usuario, 
					MIN(fechaIngreso) AS fechaIngreso, MAX(benTipoBeneficiario) AS benTipoBeneficiario
					INTO #tmp_TipoAfil_Alt2
					FROM #tmp_TipoAfil2 WITH (NOLOCK) 
					GROUP BY perNumeroIdentificacion, perTipoIdentificacion

					UPDATE #Personas_G2E
					SET tipoafil = CASE WHEN tmp.TipoAfil IS NULL THEN 'P1' ELSE tmp.TipoAfil END, 
					usuario = tmp.Usuario,
					tiposalario = '',
					tipocontrato = ''
					FROM #Personas_G2E per WITH (NOLOCK) 
					JOIN #tmp_TipoAfil2 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
					JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
					AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
					AND per.tipoafil IS NULL

					-- ACTUALIZAR los datos de ubicacion en el caso de que el tipo de beneficiario sea diferente a conyugue
					-- ya que tomaria los datos del grupo familiar al que pertenence y los sobreescribe en este bloque de codigo
					IF exists (SELECT DISTINCT benTipoBeneficiario FROM #tmp_TipoAfil_Alt2 WITH (NOLOCK) where benTipoBeneficiario <> 'CONYUGE' )  BEGIN
						SELECT per.perNumeroIdentificacion, per.perTipoIdentificacion, left(REPLACE(UPPER(REPLACE(UPPER(ubi.ubiDireccionFisica),'  ', ' ')),'#', ''),60) AS direcc, -- Por SAT no recibe # ni espacios adicionales
						mun.muncodigo AS ciudad, 
						SUBSTRING(mun.munCodigo, 1, 2) AS Region, 
						ubi.ubiTelefonoFijo AS Telefono, 
						ubi.ubiTelefonoCelular AS Celular, 
						ubi.ubiEmail AS email,
						CASE WHEN ubi.ubiCodigoPostal IS NULL THEN ''
							ELSE ubi.ubiCodigoPostal END AS CodPostal
						INTO #tmp_DireccionBeneficiario
						FROM core.dbo.Persona per WITH (NOLOCK)
						INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perID = ben.benPersona
						INNER JOIN core.dbo.GrupoFamiliar grf WITH (NOLOCK) ON ben.benGrupoFamiliar = grf.grfId 
						LEFT JOIN core.dbo.Ubicacion ubi WITH (NOLOCK) ON  grf.grfUbicacion = ubi.ubiID
						LEFT JOIN core.dbo.Municipio mun WITH (NOLOCK) ON ubi.ubiMunicipio = mun.munID
						LEFT JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.perTipoIdentificacion = ide.TipoIdGenesys
						WHERE per.perNumeroIdentificacion = @documentoId
						AND per.perTipoIdentificacion = @tipoDocumento

						UPDATE #Personas_G2E
						SET direcc = tmp.direcc,
						ciudad = tmp.ciudad,
						region = tmp.Region,
						telefono = tmp.Telefono,
						celular = tmp.Celular,
						email = tmp.email
						FROM #Personas_G2E per WITH (NOLOCK)
						INNER JOIN #tmp_DireccionBeneficiario tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
						INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
						AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

						DROP TABLE #tmp_DireccionBeneficiario;
					END
		END
	-- CONTINUACION: Actualiza los valores en caso de ser nulo posterior a la actualizacion se deja como no afiliado (inactivo)
  
	DROP TABLE #tmp_TipoAfil_Alt2;
	DROP TABLE #tmp_TipoAfil2; 
	
	UPDATE #Personas_G2E
	SET afiliadoA = '01',
	tipopersona = 'B'
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_TipoPersona tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON ide.TipoIdHomologado = per.tipodoc
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND tmp.Tipo = 'Beneficiario' 
	AND tmp.roaEstadoAfiliado = 'ACTIVO'
	AND per.afiliadoA IS NULL;

	-- Actualizacion del campo ocupacion
	UPDATE #Personas_G2E
	SET Ocupacion= ''
	
	-- Fecha de afiliacion, fecha de retiro, fecha de formulario, fecha de ingreso
	SELECT DISTINCT per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado,
	MAX(roa.roaFechaAfiliacion) AS FecAfil,
	MAX(roa.roaFechaRetiro) AS FecRetiro,
	MAX(roa.roaFechaAfiliacion) AS FecFormulario,
	MAX(roa.roaFechaIngreso) AS FecIngreso
	INTO #tmp_fechas2
	FROM Persona per WITH (NOLOCK) 
	LEFT JOIN Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona 
	LEFT JOIN RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado 
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND roa.roaEstadoAfiliado = 'ACTIVO'
	GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado
	
	UPDATE #Personas_G2E
	SET FecAfil = tmp.FecAfil,
	FecRetiro = tmp.FecRetiro,
	FecFormulario = tmp.FecFormulario,
	FecIngreso = tmp.FecIngreso
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_fechas2 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

	-- BENEFICIARIO: Calcular el grupo familiar, beneficiario recibe subsidio
	SELECT DISTINCT per.perNumeroIdentificacion, perTipoIdentificacion,
	CASE WHEN grf.grfNumero IS NULL THEN '' ELSE grf.grfNumero END AS GrupoFam,
	CASE WHEN dsa.dsaValorSubsidioMonetario IS NULL THEN '' 
		WHEN dsa.dsaValorSubsidioMonetario > 0 THEN 'S' 
		ELSE 'N' END AS IndSub -- Calcular
	INTO #tmp_grupoFamiliar2
	FROM core.dbo.Persona per WITH (NOLOCK) 
	INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perid = ben.benPersona
	INNER JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON ben.benGrupoFamiliar = grf.grfId
	LEFT JOIN core.dbo.DetalleSubsidioAsignado dsa WITH (NOLOCK) ON grf.grfID = dsa.dsaGrupoFamiliar
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	
	UPDATE #Personas_G2E
	SET grupofam = tmp.GrupoFam, indsub = tmp.indsub
	FROM #Personas_G2E per WITH (NOLOCK)
	JOIN #tmp_grupoFamiliar2 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.grupofam IS NULL

	/*
		-- BENEFICIARIO: Calcular el Numero Tarjeta
	SELECT DISTINCT per.perNumeroIdentificacion, perTipoIdentificacion,
	mtr.mtrNumeroTarjeta AS NroTarjeta
	INTO #tmp_NumeroTarjeta2
	FROM core.dbo.Persona per WITH (NOLOCK) 
	INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perid = ben.benPersona
	INNER JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON  ben.benGrupoFamiliar = grf.grfId
	LEFT JOIN core.dbo.AdminSubsidioGrupo asg WITH (NOLOCK) ON  grf.grfID = asg.asgGrupoFamiliar
	LEFT JOIN core.dbo.MedioDePago mdp WITH (NOLOCK) ON  asg.asgMedioDePago = mdp.mdpID AND mdp.mdptipo = 'TARJETA'
	LEFT JOIN core.dbo.MedioTarjeta mtr WITH (NOLOCK) ON mdp.mdpID = mtr.mdpId 
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion =  @tipoDocumento
	AND ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	
	UPDATE #Personas_G2E
	SET nrotarjeta = tmp.NroTarjeta
	FROM #Personas_G2E per WITH (NOLOCK)
	JOIN #tmp_NumeroTarjeta2 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.grupofam IS NULL

	*/

	UPDATE #Personas_G2E
	SET nrotarjeta = ''

	-- BENEFICIARIO: Calcula la categoria 
	
	SELECT top 1 per.pernumeroidentificacion, per.perTipoIdentificacion,    --------- Agregado 19/10/2022 segun solicitud de rogrigo (Camilo Gomez) para cambio de categoria
		cta.ctaFechaCambioCategoria AS ctaFechaCambioCategoria,
	CASE WHEN cta.ctaCategoria IS NULL OR cta.ctaCategoria = 'SIN_CATEGORIA' THEN 'D'
		ELSE cta.ctaCategoria END AS ctaCategoria
	INTO #tmp_categoria2
	FROM core.dbo.Persona per WITH (NOLOCK)
	INNER JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON  per.perid = ben.benPersona
	INNER JOIN core.dbo.categoriaAfiliado cta WITH (NOLOCK) ON ben.benAfiliado = cta.ctaAfiliado----cambiar o adicionar tabla categoria
	WHERE perNumeroIdentificacion = @documentoId
	AND perTipoIdentificacion = @tipoDocumento
	AND cta.ctaEstadoAfiliacion = 'ACTIVO'
	AND ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
	order by 3 desc

	UPDATE #Personas_G2E
	SET categoria = tmp.ctaCategoria
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_categoria2 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.Categoria IS NULL

	-- BENEFICIARIO: Calculo de autEnvio autorizacion de envio para el beneficiario cuando el campo es NULL hereda del trabajador principal.
	SELECT per.pernumeroidentificacion, per.perTipoIdentificacion,
	CASE WHEN not exists (select * from Solicitud WITH(NOLOCK) where solTipoTransaccion like '%web%' and solNumeroRadicacion = @solNumeroRadicacion) THEN CASE ped.pedAutorizaUsoDatosPersonales WHEN 1 THEN 'A'
			WHEN 0 THEN 'A' 
			ELSE 'Z' END WHEN afi.afiId is null then 'Z' ELSE 'Z' END AS autenvio   --- agregado
	INTO #tmp_autEnvio
	FROM core.dbo.Persona per WITH (NOLOCK)
	LEFT JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON per.perid = ben.benPersona
	LEFT JOIN core.dbo.Ubicacion ubi2 WITH (NOLOCK) ON  per.perUbicacionPrincipal = ubi2.ubiID
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  ben.benAfiliado = afi.afiId
	LEFT JOIN core.dbo.Persona per2 WITH (NOLOCK) ON  afi.afiPersona = per2.perId
	LEFT JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK) on per2.perId = ped.pedPersona
	--LEFT JOIN GrupoFamiliar grf WITH (NOLOCK) ON grf.grfId = ben.benGrupoFamiliar
	LEFT JOIN Ubicacion ubi WITH (NOLOCK) ON  per2.perUbicacionPrincipal = ubi.ubiID
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND ubi2.ubiAutorizacionEnvioEmail IS NULL
	AND ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'

	UPDATE #Personas_G2E
	SET autEnvio = tmp.autenvio
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_autEnvio tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.autenvio IS NULL

	DROP TABLE #tmp_fechas2;
	DROP TABLE #tmp_grupoFamiliar2;
	--DROP TABLE #tmp_NumeroTarjeta2;
	DROP TABLE #tmp_categoria2;
	DROP TABLE #tmp_autEnvio;

END

ELSE IF EXISTS (SELECT perNumeroIdentificacion, perTipoIdentificacion 
                 FROM #tmp_TipoPersona WITH (NOLOCK)
				WHERE Tipo = 'Trabajador' 
				  AND roaEstadoAfiliado = 'INACTIVO') -- trabajador inactivo
BEGIN
	IF (SELECT COUNT(*)
                 FROM #tmp_TipoPersona WITH (NOLOCK)
				WHERE Tipo = 'Beneficiario' 
				  AND roaEstadoAfiliado = 'ACTIVO') = 0
	
				BEGIN
					--PRINT 'trabajador retirado'

					UPDATE #Personas_G2E
					SET afiliadoA = '17',
					tipopersona = '',
					gradoesc = '',
					tipoafil = 'P1',
					categoria = 'D',
					indsub = 'N',
					tiposalario = '',
					tipocontrato = ''
					FROM #Personas_G2E per WITH (NOLOCK)
					INNER JOIN #tmp_TipoPersona tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
					INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
					AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
					AND tmp.Tipo = 'Trabajador' AND tmp.roaEstadoAfiliado = 'INACTIVO'
					AND per.afiliadoA IS NULL;

				-- Fecha de afiliacion, fecha de retiro, fecha de formulario, fecha de ingreso
					SELECT DISTINCT per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado,
					MAX(roa.roaFechaAfiliacion) AS FecAfil,
					MAX(roa.roaFechaRetiro) AS FecRetiro,
					MAX(roa.roaFechaAfiliacion) AS FecFormulario,
					MAX(roa.roaFechaIngreso) AS FecIngreso
					INTO #tmp_fechas3
					FROM core.dbo.Persona per WITH (NOLOCK) 
					LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
					LEFT JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON afi.afiId =  roa.roaAfiliado
					WHERE per.perNumeroIdentificacion = @documentoId
					AND per.perTipoIdentificacion = @tipoDocumento
					AND roa.roaEstadoAfiliado = 'INACTIVO'
					GROUP BY per.perNumeroIdentificacion, roa.roaEstadoAfiliado, per.perTipoIdentificacion
	
					UPDATE #Personas_G2E
					SET FecAfil = tmp.FecAfil,
					FecRetiro = tmp.FecRetiro,
					FecFormulario = tmp.FecFormulario,
					FecIngreso = tmp.FecIngreso
					FROM #Personas_G2E per WITH (NOLOCK)
					INNER JOIN #tmp_fechas3 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
					INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
					AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

					DROP TABLE #tmp_fechas3;

					-- TRABAJADOR: Calcular el grupo familiar
					SELECT DISTINCT per.perNumeroIdentificacion, perTipoIdentificacion,
					CASE WHEN grf.grfNumero IS NULL THEN '' ELSE grf.grfNumero END AS GrupoFam
					INTO #tmp_grupoFamiliar3
					FROM core.dbo.Persona per WITH (NOLOCK) 
					LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
					LEFT JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON  afi.afiId  = grf.grfAfiliado
					WHERE per.perNumeroIdentificacion = @documentoId
					AND per.perTipoIdentificacion = @tipoDocumento

					UPDATE #Personas_G2E
					SET grupofam = tmp.GrupoFam
					FROM #Personas_G2E per WITH (NOLOCK)
					JOIN #tmp_grupoFamiliar3 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
					JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
					AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
					AND per.grupofam IS NULL

					DROP TABLE #tmp_grupoFamiliar3;
				END
		END


ELSE IF EXISTS (SELECT perNumeroIdentificacion, perTipoIdentificacion FROM #tmp_TipoPersona WITH (NOLOCK) WHERE Tipo = 'Beneficiario' AND roaEstadoAfiliado = 'INACTIVO') -- beneficiario inactivo
BEGIN	

	--VALIDAR CONSULTA

	UPDATE #Personas_G2E
	SET afiliadoA = '17',
	tipopersona = '',
	tipoafil = 'P1',
	categoria = CASE WHEN (		SELECT COUNT(*)
	             FROM core.dbo.Solicitud WITH (NOLOCK)
				 INNER JOIN core.dbo.solicitudnovedad WITH (NOLOCK) on solid = snosolicitudglobal
				 INNER JOIN core.dbo.solicitudnovedadpersona WITH (NOLOCK) on snoid = snpsolicitudnovedad
				 WHERE snpPersona IN ( SELECT snpPersona
											 FROM core.dbo.Solicitud WITH (NOLOCK)
											 INNER JOIN core.dbo.solicitudnovedad WITH (NOLOCK) on solid = snosolicitudglobal
											 INNER JOIN core.dbo.solicitudnovedadpersona WITH (NOLOCK) on snoid = snpsolicitudnovedad
											 WHERE solnumeroradicacion = CASE WHEN CHARINDEX( '_',@solNumeroRadicacion )>0 THEN 
											 SUBSTRING (@solNumeroRadicacion ,1,CHARINDEX( '_',@solNumeroRadicacion )-1) ELSE @solNumeroRadicacion END
											 AND snpBeneficiario IS NULL
											 )
	            AND solTipoTransaccion = 'REPORTE_FALLECIMIENTO_PERSONAS')>0
		 THEN ( SELECT DISTINCT ctaCategoria 
		          FROM (
				  SELECT   CASE WHEN c.ctaId = MAX(c.ctaId) 
								OVER (PARTITION by ctaafiliado) 
								THEN c.ctaId ELSE NULL END  AS  ctaid, ctaCategoria
					FROM core.dbo.Beneficiario WITH (NOLOCK)
			  INNER JOIN core.dbo.persona pb WITH (NOLOCK) on  benpersona = pb.perid
			  INNER JOIN core.dbo.CategoriaAfiliado c WITH (NOLOCK) on   benAfiliado = c.ctaAfiliado
			  INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  pb.perTipoIdentificacion = ide.TipoIdGenesys  ---- Agregado para comparar el tipo de documento con la temporal y que no traiga mas de un valor agragado por camilo gomez 28/11/2023
				   WHERE pb.perNumeroIdentificacion = per.numerodoc
					  AND IDE.TipoIdHomologado = per.tipodoc  ---- Agregado para comparar el tipo de documento con la temporal y que no traiga mas de un valor agragado por camilo gomez 28/11/2023
				      AND ctaCategoria <> 'SIN_CATEGORIA'
				   ) a 
				   WHERE a.ctaid is not null 
						  ) ELSE 'D' END  ,----para fallecimiento olga vega 20220222 CAMBIO DE CATEGORIA PARA QUE EL BENF NO PIERDA LA CATEGORIA 
						  ---PARA FALLECIDO DEL TRABAJADOR PRINCIPAL 20220330
	--categoria = 'D',
	indsub = 'N',
	tiposalario = '',
	tipocontrato = ''
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_TipoPersona tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND tmp.Tipo = 'Beneficiario' 
	AND tmp.roaEstadoAfiliado = 'INACTIVO'
	AND per.afiliadoA IS NULL;

	-- Fecha de afiliacion, fecha de retiro, fecha de formulario, fecha de ingreso
	SELECT DISTINCT per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado,
	MAX(roa.roaFechaAfiliacion) AS FecAfil,
	MAX(roa.roaFechaRetiro) AS FecRetiro,
	MAX(roa.roaFechaAfiliacion) AS FecFormulario,
	MAX(roa.roaFechaIngreso) AS FecIngreso
	INTO #tmp_fechas4
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON  per.perid = afi.afiPersona
	LEFT JOIN core.dbo.RolAfiliado roa WITH (NOLOCK) ON  afi.afiId = roa.roaAfiliado
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND roa.roaEstadoAfiliado = 'INACTIVO'
	GROUP BY per.perNumeroIdentificacion, per.perTipoIdentificacion, roa.roaEstadoAfiliado
	
	UPDATE #Personas_G2E
	SET FecAfil = tmp.FecAfil,
	FecRetiro = tmp.FecRetiro,
	FecFormulario = tmp.FecFormulario,
	FecIngreso = tmp.FecIngreso
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_fechas4 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys

	DROP TABLE #tmp_fechas4;

	-- BENEFICIARIO: Calcular el grupo familiar
	SELECT DISTINCT per.perNumeroIdentificacion, perTipoIdentificacion,
	CASE WHEN grf.grfNumero IS NULL THEN '' ELSE grf.grfNumero END AS GrupoFam
	INTO #tmp_grupoFamiliar4
	FROM core.dbo.Persona per WITH (NOLOCK) 
	LEFT JOIN core.dbo.Beneficiario ben WITH (NOLOCK) ON per.perid = ben.benPersona
	LEFT JOIN core.dbo.grupoFamiliar grf WITH (NOLOCK) ON ben.benGrupoFamiliar = grf.grfId 
	WHERE per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND ben.benEstadoBeneficiarioAfiliado = 'INACTIVO'

	UPDATE #Personas_G2E
	SET grupofam = tmp.GrupoFam
	FROM #Personas_G2E per WITH (NOLOCK)
	INNER JOIN #tmp_grupoFamiliar4 tmp WITH (NOLOCK) ON per.numerodoc = tmp.perNumeroIdentificacion
	INNER JOIN [sap].[MaestraTiposIdentificacion] ide WITH (NOLOCK) ON  per.tipodoc = ide.TipoIdHomologado
	AND tmp.perTipoIdentificacion = ide.TipoIdGenesys
	AND per.grupofam IS NULL

	DROP TABLE #tmp_grupoFamiliar4;

------ADICIONO FECHA AFIL PARA BENEFICIARIOS
			    UPDATE #Personas_G2E SET fecafil = b.benFechaAfiliacion  , 
				                            fecformulario = ISNULL(ichFechaRecepcionDocumento ,b.benFechaAfiliacion), 
											fecretiro = benFechaRetiro
		 	    FROM #Personas_G2E WITH (NOLOCK)
			INNER JOIN core.dbo.Persona WITH (NOLOCK) ON codigoGenesys = perId
			INNER JOIN core.dbo.Beneficiario b WITH (NOLOCK) ON  perid = benPersona
			LEFT JOIN (SELECT snpBeneficiario, ichFechaRecepcionDocumento
						   FROM core.dbo.SolicitudNovedadPersona  WITH (NOLOCK)
						INNER JOIN core.dbo.SolicitudNovedad WITH (NOLOCK) ON  snpSolicitudNovedad = snoId
						INNER JOIN core.dbo.Solicitud WITH (NOLOCK) ON  snoSolicitudGlobal = solid
						INNER JOIN #item WITH (NOLOCK) on  solid = ichSolicitud) docnov
			ON snpBeneficiario = benid
		 
			WHERE perNumeroIdentificacion = @documentoId
			 AND perTipoIdentificacion = @tipoDocumento
			 AND benEstadoBeneficiarioAfiliado = 'INACTIVO'

END
ELSE 
BEGIN
	PRINT 'No clasifica'
END

-- REVISION DE CAMPOS EN NULL AL NO CLASIFICARLOS COMO PERSONAS. CASO DE NO FORMALIZADO CON INFORMACIoN

UPDATE #Personas_G2E
	SET tipoafil = 'XX'
	FROM #Personas_G2E per 
	WHERE tipoafil IS NULL

-- ELIMINACIoN DE REGISTROS CON ERROR Y QUE TIENEN EL MISMO DOCUMENTO DE IDENTIDAD EN LA TABLA DE INTEGRACION.

    DELETE sap.Personas_G2E
     FROM sap.Personas_G2E WITH (NOLOCK)
	 INNER JOIN sap.MaestraIdentificacion WITH (NOLOCK) on tipodoc=tipoDocumentoCaja
     WHERE numerodoc = @documentoId
       AND tipoIdGenesys = @tipoDocumento
       AND estadoreg IN ('E', 'P')

	   
----calcula la categoria para los que tuvieon un cambio de categoria por aporte cambio 20220518 por olga vega 
		UPDATE per SET categoria = cambiocatxsalario.ctaCategoria
		FROM 
		(
			SELECT DISTINCT CASE WHEN CategoriaAfiliado.ctaId = max(CategoriaAfiliado.ctaId) over (partition by ctaAfiliado) then CategoriaAfiliado.ctaCategoria else null end as ctaCategoria, perNumeroIdentificacion
			  FROM core.dbo.CategoriaAfiliado WITH (NOLOCK)
		INNER JOIN core.dbo.afiliado WITH (NOLOCK) ON  ctaAfiliado = afiid
		INNER JOIN core.dbo.persona WITH (NOLOCK) ON  afipersona = perid
		INNER JOIN core.dbo.rolAfiliado WITH (NOLOCK) ON  afiId = roaAfiliado AND roaEstadoAfiliado = 'ACTIVO'
		INNER JOIN (SELECT CASE WHEN ctaId = max(ctaId) OVER (partition by ctaAfiliado) THEN ctaId ELSE null end as ctaId,ctaCategoria
						,afiid 
						FROM CategoriaAfiliado WITH (NOLOCK)
					INNER JOIN afiliado WITH (NOLOCK) ON  ctaAfiliado = afiid
					INNER JOIN persona WITH (NOLOCK) ON  afipersona = perid
					 WHERE perNumeroIdentificacion = @documentoId AND 
					       perTipoIdentificacion = @tipoDocumento AND 
			      ctaMotivoCambioCategoria <> 'APORTE_RECIBIDO_AFILIADO_CAJA') catant
				ON catant.afiid = ctaAfiliado and  catant.ctaCategoria <> CategoriaAfiliado.ctaCategoria 
			 WHERE perNumeroIdentificacion = @documentoId AND 
				   perTipoIdentificacion = @tipoDocumento AND 
			       ctaMotivoCambioCategoria = 'APORTE_RECIBIDO_AFILIADO_CAJA'
			   AND ctaFechaCambioCategoria >= GETDATE()-1
		)cambiocatxsalario
	 INNER JOIN #Personas_G2E per WITH(NOLOCK) ON per.numerodoc = cambiocatxsalario.perNumeroIdentificacion

 
 	----calcula la categoria para los beneficiarios ya que sus apfiliados princ lleva mas de un año muertos cambio 20220519 por olga vega  
	UPDATE per SET categoria = 'D'  
		FROM 
			(	 SELECT DISTINCT pb.perNumeroIdentificacion  
				   FROM core.dbo.persona pa WITH (NOLOCK)
			 INNER JOIN core.dbo.personadetalle pda WITH (NOLOCK) on pa.perId = pda.pedPersona
			 INNER JOIN core.dbo.afiliado WITH (NOLOCK) on  perid = afipersona
			 INNER JOIN core.dbo.RolAfiliado WITH (NOLOCK) on  afiid  = roaAfiliado
			 INNER JOIN core.dbo.Beneficiario WITH (NOLOCK) on  roaAfiliado = benAfiliado
			 INNER JOIN core.dbo.personadetalle pbd WITH (NOLOCK) on benpersona = pbd.pedPersona
			 INNER JOIN core.dbo.persona pb WITH (NOLOCK) on  benPersona = pb.perid
			 INNER JOIN core.dbo.SolicitudNovedadPersona WITH (NOLOCK) ON snpPersona = pa.perid 
			 INNER JOIN core.dbo.solicitudnovedad WITH (NOLOCK) ON snpSolicitudNovedad = snoId
			 INNER JOIN core.dbo.Solicitud WITH (NOLOCK) ON  snoSolicitudGlobal = solid
				  WHERE roaEstadoAfiliado = 'INACTIVO'
					AND benEstadoBeneficiarioAfiliado = 'INACTIVO'
					AND solTipoTransaccion = 'REPORTE_FALLECIMIENTO_PERSONAS'
					AND solClasificacion = 'TRABAJADOR_DEPENDIENTE'
					AND solResultadoProceso = 'APROBADA'
					AND snpBeneficiario IS NULL
					AND pda.pedFechaFallecido IS NOT NULL 
					AND DATEDIFF(DAY, pda.pedFechaDefuncion,GETDATE())= 365
					AND pb.perNumeroIdentificacion = @documentoId   --cambiado a identificacion de beneficiario, John Sotelo 21062022
				    AND pb.perTipoIdentificacion = @tipoDocumento   
					AND solFechaRadicacion>= '2021-05-01'
					) as finalserv
		INNER JOIN #Personas_G2E per WITH(NOLOCK) ON per.numerodoc = finalserv.perNumeroIdentificacion

-- Actualiza los datos en la tabla de la integracion
 
 ------PARA LOS BENEFICIARIOS DE LA NOVEDAD INTERNA QUE CUMPLEN 19 AÑOS por olga vega 20220719

	UPDATE per SET categoria = 'C'  
		FROM 
			(	 SELECT DISTINCT pb.perNumeroIdentificacion  
				   FROM core.dbo.persona pb WITH (NOLOCK)
			 INNER JOIN core.dbo.Beneficiario WITH (NOLOCK) on benPersona = pb.perid 		  
			 INNER JOIN core.dbo.SolicitudNovedadPersona WITH (NOLOCK) ON snpPersona = pb.perid and snpBeneficiario = benid 
			 INNER JOIN core.dbo.solicitudnovedad WITH (NOLOCK) ON snpSolicitudNovedad = snoId
			 INNER JOIN core.dbo.Solicitud WITH (NOLOCK) ON  snoSolicitudGlobal = solid
				  WHERE solTipoTransaccion = 'CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD'
					AND solResultadoProceso = 'APROBADA'
					AND pb.perNumeroIdentificacion = @documentoId  
				    AND pb.perTipoIdentificacion = @tipoDocumento   
					AND solFechaRadicacion>= GETDATE()-1
			    EXCEPT
		------TEMPORAL MIENTRAS GENESYS NO LO GENERE CON ESTA CONDICION
				SELECT DISTINCT perNumeroIdentificacion 
				      FROM core.dbo.persona p WITH (NOLOCK)
				INNER JOIN core.dbo.Beneficiario WITH (NOLOCK) ON  p.perid = benPersona
				INNER JOIN core.dbo.PersonaDetalle WITH (NOLOCK) ON  p.perid = pedPersona
				INNER JOIN core.dbo.CertificadoEscolarBeneficiario WITH (NOLOCK) ON  benBeneficiarioDetalle = cebBeneficiarioDetalle
				WHERE   cebFechaVencimiento >= GETDATE()
					) as catmayor19
		INNER JOIN #Personas_G2E per WITH(NOLOCK) ON  catmayor19.perNumeroIdentificacion = per.numerodoc

INSERT INTO [SAP].[Personas_G2E]
([fecing],[horaing],[operacion],[codigosap],[codigoGenesys],[tipodoc],[numerodoc],[nombre1],[nombre2],[apellido1],[apellido2],[fecnac],[estcivil]
,[direcc],[ciudad],[region],[telefono],[celular],[email],[tipoafil],[afiliadoa],[categoria],[estado],[sexo],[autenvio],[discapacidad],[fecafil]
,[fecretiro],[fecformulario],[nucleo],[fecexped],[fecingreso],[gradoesc],[grupofam],[indsub],[niveledu],[ccostos],[nrotarjeta],[ocupacion]
,[porcdisca],[profesion],[rangosalario],[sectorresidencia],[tipocontrato],[tiposalario],[tipopersona],[codpostal],[nomempresa],[nitempresa]
,[morosa], [estadoreg],[FechaEjecucion])

SELECT DISTINCT [fecing],[horaing],[operacion],[codigosap],[codigoGenesys],[tipodoc],[numerodoc],[nombre1],[nombre2],[apellido1],[apellido2],[fecnac],[estcivil]
,[direcc],[ciudad],[region],[telefono],[celular],[email],[tipoafil],[afiliadoa],ISNULL([categoria],'D'),[estado],[sexo],[autenvio],[discapacidad],[fecafil]
,[fecretiro],[fecformulario],[nucleo],[fecexped],[fecingreso],[gradoesc],[grupofam],[indsub],[niveledu],[ccostos],[nrotarjeta],[ocupacion]
,[porcdisca],[profesion],[rangosalario],[sectorresidencia],[tipocontrato],[tiposalario],[tipopersona],[codpostal],[nomempresa],[nitempresa]
,[morosa], [estadoreg], GETDATE() -'05:00:00'
FROM #Personas_G2E WITH (NOLOCK)
WHERE afiliadoa is not null


 IF (SELECT COUNT(*) FROM #Personas_G2E WITH(NOLOCK) WHERE numerodoc = @documentoId  AND fecafil IS NULL)>0
BEGIN		 
------ADICIONO FECHA AFIL PARA BENEFICIARIOS
			    UPDATE sap.Personas_G2E SET fecafil = b.benFechaAfiliacion  , 
				                            fecformulario = ISNULL(ichFechaRecepcionDocumento ,b.benFechaAfiliacion), 
											fecretiro = benFechaRetiro
		 	    FROM sap.Personas_G2E WITH (NOLOCK)
			INNER JOIN core.dbo.Persona WITH (NOLOCK) ON codigoGenesys = perId
			INNER JOIN core.dbo.Beneficiario b WITH (NOLOCK) ON  perid = benPersona
			LEFT JOIN (SELECT snpBeneficiario, ichFechaRecepcionDocumento
						   FROM core.dbo.SolicitudNovedadPersona WITH (NOLOCK)
						INNER JOIN core.dbo.SolicitudNovedad WITH (NOLOCK) ON  snpSolicitudNovedad = snoId
						INNER JOIN core.dbo.Solicitud WITH (NOLOCK) ON  snoSolicitudGlobal = solid
						INNER JOIN #item WITH (NOLOCK) on  solid = ichSolicitud) docnov
			ON snpBeneficiario = benid
		 
			WHERE perNumeroIdentificacion = @documentoId
			 AND perTipoIdentificacion = @tipoDocumento
			 AND benEstadoBeneficiarioAfiliado = 'INACTIVO'
		 

------ADICIONO FECHA AFIL PARA BENEFICIARIOS
			    UPDATE sap.Personas_G2E SET fecafil = B.benFechaAfiliacion  , fecformulario =  ichFechaRecepcionDocumento 
		 	    FROM sap.Personas_G2E WITH (NOLOCK)
			INNER JOIN core.dbo.Persona WITH (NOLOCK) ON codigoGenesys = perId
			INNER JOIN (SELECT CASE WHEN benAfiliado = MIN(benAfiliado) OVER (PARTITION BY benpersona) THEN benAfiliado ELSE NULL END AS benAfiliado,
							   benEstadoBeneficiarioAfiliado, benFechaAfiliacion, benpersona  ,benid 
						  FROM beneficiario WITH (NOLOCK)
						WHERE  benEstadoBeneficiarioAfiliado = 'ACTIVO' AND benTipoBeneficiario <>'CONYUGE'
						) b ON perid = b.benPersona
			LEFT JOIN (SELECT snpBeneficiario, ichFechaRecepcionDocumento
						   FROM core.dbo.SolicitudNovedadPersona WITH (NOLOCK)
						INNER JOIN core.dbo.SolicitudNovedad WITH (NOLOCK) ON  snpSolicitudNovedad = snoId
						INNER JOIN core.dbo.Solicitud WITH (NOLOCK) ON  snoSolicitudGlobal = solid
						INNER JOIN #item WITH (NOLOCK) on  solid = ichSolicitud ) docnov
			ON snpBeneficiario = benid
		 
			WHERE perNumeroIdentificacion = @documentoId
			AND perTipoIdentificacion = @tipoDocumento
			AND fecafil IS NULL
			
					   
			 UPDATE sap.Personas_G2E SET fecafil = B.benFechaAfiliacion  , fecformulario =  ISNULL(ichFechaRecepcionDocumento ,B.benFechaAfiliacion)---CAMBIO OLGA VEGA 20220209 CUANDO VIENEN MIGRADOS 
		FROM sap.Personas_G2E 
			INNER JOIN core.dbo.Persona WITH (NOLOCK) ON codigoGenesys = perId
			INNER JOIN (SELECT CASE WHEN benAfiliado = MIN(benAfiliado) OVER (PARTITION BY benpersona) THEN benAfiliado ELSE NULL END AS benAfiliado,
							   benEstadoBeneficiarioAfiliado, benFechaAfiliacion, benpersona  ,benid , roaId
						  FROM core.dbo.beneficiario WITH (NOLOCK)
					INNER JOIN core.dbo.Afiliado WITH (NOLOCK) ON  benAfiliado = afiid 
					INNER JOIN core.dbo.RolAfiliado WITH (NOLOCK) ON afiid = roaAfiliado
						 WHERE benEstadoBeneficiarioAfiliado = 'ACTIVO' and benTipoBeneficiario <>'CONYUGE'
						) b ON perid = b.benPersona
			LEFT JOIN ( SELECT DISTINCT  ichFechaRecepcionDocumento, sapRolAfiliado, ichPersona
						  FROM core.dbo.Solicitud WITH (NOLOCK)
					INNER JOIN #item WITH (NOLOCK) on solid = ichSolicitud
					INNER JOIN core.dbo.SolicitudAfiliacionPersona WITH (NOLOCK) on sapSolicitudGlobal = solid 
					INNER JOIN core.dbo.Requisito WITH (NOLOCK) ON reqId = ichRequisito
						 WHERE ichEstadoRequisito = 'OBLIGATORIO' 
						    AND ichRequisito NOT IN (89,90,91)
						    AND solNumeroRadicacion = @solNumeroRadicacion
						  ) docnov 
			ON   sapRolAfiliado = b.roaid and ichpersona = b.benpersona
			WHERE perNumeroIdentificacion = @documentoId
			AND perTipoIdentificacion = @tipoDocumento
			AND fecformulario IS NULL

			-----*ACTUALIZACIoN CUANDO UN BENEFICIARIO ES AFILIADO PRINCIPAL ACTIVO**---
END
			 
DROP TABLE #Personas_G2E;
DROP TABLE #tmp_TipoPersona;
DROP TABLE #tmp_coinvalidez;
DROP TABLE #tmp_cartera;

-- FIN Consulta de delta de afiliaciones en Genesys
END;