/****** Object:  StoredProcedure [dbo].[reporteNovedadesConcurrencia]    Script Date: 21/04/2023 8:26:06 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesConcurrencia]    Script Date: 17/04/2023 8:55:03 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesConcurrencia]    Script Date: 16/09/2022 12:36:47 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesConcurrencia]    Script Date: 05/02/2022 9:00 a.m. ******/
-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 5 Febrero 2022
-- Description: Procedimiento almacenado encargado de ejecutar la consulta Novedades Concurrencia con sus rangos de fechas
-- Reporte Normativo 30B
--exec [reporteNovedadesConcurrencia] '2023-03-01','2023-03-31'
-- =============================================
CREATE OR ALTER      PROCEDURE [dbo].[reporteNovedadesConcurrencia]
(
    @fechaInicio DATE,
	@fechaFin DATE
)
AS
BEGIN
	-- (REPORTE 30B) Novedades Concurrencia
	-- Descripción: El reporte de “Novedades Concurrencia” de la circular 0013 del 03 de octubre del 2019 debe reportar 
	-- la información correspondiente a hogares que presentan novedad de renuncia, SFV Aplicado y SFV Vencido.

	-- Se asignan todas las variables a utilizar
	DECLARE @idHogar VARCHAR(30);
	DECLARE @tipoDocumento SMALLINT;
	DECLARE @numDocumento VARCHAR(30);
	DECLARE @nombres VARCHAR(50);
	DECLARE @apellidos VARCHAR(50);
	DECLARE @parentesco SMALLINT;
	DECLARE @valorSubsidio BIGINT;
	DECLARE @nitEntidadOtorgante BIGINT;
	DECLARE @rangoIngresos SMALLINT;
	DECLARE @fechaNacimiento VARCHAR(10);
	DECLARE @fechaAsignacion VARCHAR(10);
	DECLARE @novedad SMALLINT;
	DECLARE @tipoDocJefeHogar SMALLINT;
	DECLARE @numDocJefeHogar VARCHAR(30);
	DECLARE @nombresJefeHogar VARCHAR(50);
	DECLARE @apellidosJefeHogar VARCHAR(50);
	DECLARE @parentescoJefeHogar SMALLINT;
	DECLARE @fechaNacJefeHogar VARCHAR(10);
	DECLARE @ingresosJefeHogarJson VARCHAR(MAX);
	DECLARE @dataJsonIntegrantesHogar VARCHAR(MAX);
	DECLARE @jefeHogarId INT;
	DECLARE @anioFechaAsig BIGINT;

	DECLARE @sumNivelIngreso BIGINT;
	DECLARE @consultarDataNovedades AS CURSOR;
	DECLARE @consultarDataNovedadesSinIntegrantes AS CURSOR;

	-- Tabla temporal que contiene la información de cada postulación con novedad
	DROP TABLE IF EXISTS #DataNovedades;
	CREATE TABLE #DataNovedades (numDocumento VARCHAR(30), idHogar VARCHAR(30), tipoDocumento SMALLINT, nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT, valorSubsidio BIGINT,
		nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10), novedad SMALLINT, tipoDocJefeHogar SMALLINT, numDocJefeHogar BIGINT, nombresJefeHogar  VARCHAR(50),
		apellidosJefeHogar VARCHAR(50), parentescoJefeHogar SMALLINT, fechaNacJefeHogar VARCHAR(10), ingresosJefeHogarJson VARCHAR(MAX), dataJsonIntegrantesHogar VARCHAR(MAX), jefeHogarId INT, anioFechaAsig BIGINT)

	-- Tabla temporal que resulta de haber pasado por el cursor modificando cada registro con respecto a los JSON para los ingresos mensuales y los integrantes del hogar
	DROP TABLE IF EXISTS #DataNovedadesModificado;
	CREATE TABLE #DataNovedadesModificado (idHogar VARCHAR(30), tipoDocumento SMALLINT, numDocumento VARCHAR(30), nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT, valorSubsidio BIGINT,
		nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10), novedad SMALLINT)

	-- Tabla temporal que resulta contiene la información de las novedades y legalizaciones sin integrantes de hogar
	DROP TABLE IF EXISTS #DataNovedadesSinIntegrantes;
	CREATE TABLE #DataNovedadesSinIntegrantes (idHogar VARCHAR(30), tipoDocumento SMALLINT, numDocumento VARCHAR(30), nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT,
		valorSubsidio BIGINT, nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10), novedad SMALLINT)

	-- Crea y llena la tabla temporal de solicitudes de novedad de auditoria con el fin de obtener la fecha de cambio de estado y así hacer el filtro
	DROP TABLE IF EXISTS #solNovAud;
	CREATE TABLE #solNovAud (snfId BIGINT, snfSolicitudGlobal BIGINT, snfEstadoSolicitud VARCHAR(75), fechaCambioEstado DATETIME, origen VARCHAR(250))
	INSERT #solNovAud
		EXECUTE sp_execute_remote N'coreAudReferenceData',N'
		SELECT s.snfId, s.snfSolicitudGlobal, s.snfEstadoSolicitud, dateadd(second, r.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' as fechaCambioEstado
		FROM SolicitudNovedadFovis_aud as s INNER JOIN revision as r on s.rev = r.revId'

	-- Ingresa la informacion de novedades de renuncia y vencimiento a la tabla temporal sin integrantes de hogar
	INSERT INTO #DataNovedadesSinIntegrantes (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad)
		SELECT 
		-- Id_hogar
		sol.solNumeroRadicacion [ID_HOGAR],
		-- Tipo de documento
		CASE pj.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [TIPO DE DOCUMENTO],
		-- Número de documento
		REPLACE(pj.perNumeroIdentificacion, '-', '') [NUMERO DE DOCUMENTO],
		-- Nombres
		RTRIM(CONCAT(pj.perPrimerNombre, ' ', ISNULL(pj.perSegundoNombre, ''))) [NOMBRES],
		-- Apellidos
		RTRIM(CONCAT(pj.perPrimerApellido, ' ', ISNULL(pj.perSegundoApellido, ''))) [APELLIDOS],
		-- Parentesco
		1 [PARENTESCO],
		-- Valor del subsidio
		pof.pofValorAsignadoSFV [VALOR DEL SUBSIDIO],
		-- Nit entidad otorgante
		REPLACE((SELECT par.prmValor FROM Parametro par WHERE par.prmNombre = 'NUMERO_ID_CCF'),
			'-', '') [NIT ENTIDAD OTORGANTE],
		-- Rango ingresos
		CASE 
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
				/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
					WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
						(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 0.00 AND 1.00 THEN 1
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
				/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
					WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
						(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 1.00 AND 2.00 THEN 2
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
			/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
				WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
					(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 2.00 AND 100 THEN 3
		END [RANGO DE INGRESOS],
		--	Fecha de Nacimiento
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = pj.perId), 103) [FECHA DE NACIMIENTO],
		-- Fecha de Asignación
		CONVERT(VARCHAR, sAsig.safFechaAceptacion, 103) [FECHA DE LA ASIGNACION],
		-- Novedad
		CASE
			WHEN pof.pofEstadoHogar = 'RENUNCIO_A_SUBSIDIO_ASIGNADO' THEN 1
			WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA')
				THEN 3
		END [NOVEDAD]

		FROM [dbo].[SolicitudNovedadFovis] postNov (NOLOCK)
		INNER JOIN ParametrizacionNovedad paramNov WITH(NOLOCK) ON paramNov.novId = postNov.snfParametrizacionNovedad
		INNER JOIN SolicitudNovedad solNov WITH(NOLOCK) ON solNov.snoId = paramNov.novId
		INNER JOIN [dbo].[SolicitudNovedadPersonaFovis] solNovPer (NOLOCK) ON solNovPer.spfSolicitudNovedadFovis = postNov.snfId
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = solNovPer.spfPostulacionFovis
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN Solicitud solDesdeNov (NOLOCK) ON postNov.snfSolicitudGlobal = solDesdeNov.solId
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		LEFT JOIN SolicitudLegalizacionDesembolso sa (NOLOCK) on sa.sldPostulacionFOVIS = pof.pofId
		LEFT JOIN SolicitudAsignacion sAsig ON pof.pofSolicitudAsignacion = sAsig.safId
		WHERE pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND pof.pofEstadoHogar IN ('RENUNCIO_A_SUBSIDIO_ASIGNADO', 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA')
			AND (SELECT TOP 1 intHogar.inhId FROM IntegranteHogar intHogar WHERE intHogar.inhPostulacionFovis = pof.pofId AND intHogar.inhestadoHogar = 'ACTIVO') IS NULL
			AND ISNULL((SELECT TOP 1 FORMAT(solNovAudTemp.fechaCambioEstado, 'MM-dd-yyyy') FROM #solNovAud solNovAudTemp
				WHERE solNovAudTemp.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA' AND solNovAudTemp.snfId = postNov.snfId), 
				FORMAT(solDesdeNov.solFechaRadicacion, 'MM-dd-yyyy')) BETWEEN @fechaInicio AND @fechaFin

	-- Ingresa la informacion de legalizaciones y desembolsos a la tabla temporal sin integrantes de hogar
	INSERT INTO #DataNovedadesSinIntegrantes (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad)
		SELECT 
		-- Id_hogar
		sol.solNumeroRadicacion [ID_HOGAR],
		-- Tipo de documento
		CASE pj.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [TIPO DE DOCUMENTO],
		-- Número de documento
		REPLACE(pj.perNumeroIdentificacion, '-', '') [NUMERO DE DOCUMENTO],
		-- Nombres
		RTRIM(CONCAT(pj.perPrimerNombre, ' ', ISNULL(pj.perSegundoNombre, ''))) [NOMBRES],
		-- Apellidos
		RTRIM(CONCAT(pj.perPrimerApellido, ' ', ISNULL(pj.perSegundoApellido, ''))) [APELLIDOS],
		-- Parentesco
		1 [PARENTESCO],
		-- Valor del subsidio
		pof.pofValorAsignadoSFV [VALOR DEL SUBSIDIO],
		-- Nit entidad otorgante
		REPLACE((SELECT par.prmValor FROM Parametro par WHERE par.prmNombre = 'NUMERO_ID_CCF'),
			'-', '') [NIT ENTIDAD OTORGANTE],
		-- Rango ingresos
		CASE 
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
				/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
					WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
						(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 0.00 AND 1.00 THEN 1
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
				/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
					WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
						(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 1.00 AND 2.00 THEN 2
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
			/ ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
				WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = FORMAT(sAsig.safFechaAceptacion, 'yyyy')), 
					(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'))) BETWEEN 2.00 AND 100 THEN 3
		END [RANGO DE INGRESOS],
		--	Fecha de Nacimiento
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = pj.perId), 103) [FECHA DE NACIMIENTO],
		-- Fecha de Asignación
		CONVERT(VARCHAR, sAsig.safFechaAceptacion, 103) [FECHA DE LA ASIGNACION],
		-- Novedad
		2 [NOVEDAD]

		FROM [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK)
		INNER JOIN legalizaciondesembolso legDes WITH(NOLOCK) on sa.[sldLegalizacionDesembolso] = legDes.lgdid
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = sa.sldPostulacionFOVIS
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		LEFT JOIN SolicitudAsignacion sAsig ON pof.pofSolicitudAsignacion = sAsig.safId
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
			-- Aplica para los hogares que tengan ingresos de hasta 2 SMMLV, 'Esta validacion se aplica dentro del cursor'
			AND pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND sa.sldEstadoSolicitud IN ('LEGALIZACION_Y_DESEMBOLSO_CONFIRMADO', 'DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA', 'LEGALIZACION_Y_DESEMBOLSO_CERRADO')
			AND (SELECT TOP 1 intHogar.inhId FROM IntegranteHogar intHogar WHERE intHogar.inhPostulacionFovis = pof.pofId AND intHogar.inhestadoHogar = 'ACTIVO') IS NULL
			AND FORMAT(sa.sldFechaOperacion, 'MM-dd-yyyy') BETWEEN @fechaInicio AND @fechaFin
		    AND (pofValorAsignadoSFV-lgdMontoDesembolsado) = 0---CAMBIO 20230417 POR OLGA VEGA GLPI 61445



	-- Llena la tabla temporal con las novedades de renuncia y vencimiento
	INSERT INTO #DataNovedades (numDocumento, idHogar, tipoDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos,
		fechaNacimiento, fechaAsignacion, novedad, tipoDocJefeHogar, numDocJefeHogar, nombresJefeHogar, apellidosJefeHogar,
		parentescoJefeHogar, fechaNacJefeHogar, ingresosJefeHogarJson, dataJsonIntegrantesHogar, jefeHogarId, anioFechaAsig)
		SELECT DISTINCT
		-- Número de documento
		REPLACE(perIntHogar.perNumeroIdentificacion, '-', '') [NUMERO DE DOCUMENTO],
		-- Id_hogar
		sol.solNumeroRadicacion [ID_HOGAR],
		-- Tipo de documento
		CASE perIntHogar.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [TIPO DE DOCUMENTO],
		-- Nombres
		CONCAT(perIntHogar.perPrimerNombre, ' ', ISNULL(perIntHogar.perSegundoNombre, '')) [NOMBRES],
		-- Apellidos
		CONCAT(perIntHogar.perPrimerApellido, ' ', ISNULL(perIntHogar.perSegundoApellido, '')) [APELLIDOS],
		-- Parentesco
		CASE intHogar.inhTipoIntegrante
			WHEN 'CONYUGE' THEN 2
			WHEN 'CONYUGE_HOGAR' THEN 2
			WHEN 'HIJO_BIOLOGICO' THEN 3
			WHEN 'HIJO_BIOLOGICO_HOGAR' THEN 3
			WHEN 'HERMANO_HOGAR' THEN 4
			WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN 4
			WHEN 'PADRE' THEN 5
			WHEN 'PADRE_HOGAR' THEN 5
			WHEN 'PADRE_MADRE_ADOPTANTE_HOGAR' THEN 5
			WHEN 'MADRE' THEN 5
			WHEN 'MADRE_HOGAR' THEN 5
			WHEN 'ABUELO_HOGAR' THEN 6
			WHEN 'NIETO_HOGAR' THEN 6
			WHEN 'TIO_HOGAR' THEN 15
			WHEN 'SOBRINO_HOGAR' THEN 15
			WHEN 'SUEGRO_HOGAR' THEN 8
			WHEN 'PADRE_MADRE_ADOPTANTE_HOGAR' THEN 13
			WHEN 'HIJO_ADOPTIVO' THEN 14
			WHEN 'HIJO_ADOPTIVO_HOGAR' THEN 14
			WHEN 'YERNO_HOGAR' THEN 10
			WHEN 'BISABUELO_HOGAR' THEN 12
			WHEN 'BISNIETO_HOGAR' THEN 12
		END [PARENTESCO],
		-- Valor del subsidio
		pof.pofValorAsignadoSFV [VALOR DEL SUBSIDIO],
		-- Nit entidad otorgante
		REPLACE((SELECT par.prmValor FROM Parametro par WHERE par.prmNombre = 'NUMERO_ID_CCF'),
			'-', '') [NIT ENTIDAD OTORGANTE],
		-- Rango ingresos
		0 [RANGO DE INGRESOS],
		--	Fecha de Nacimiento
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = perIntHogar.perId), 103) [FECHA DE NACIMIENTO],
		-- Fecha de Asignación
		CONVERT(VARCHAR, sAsig.safFechaAceptacion, 103) [FECHA DE LA ASIGNACION],
		-- Novedad
		CASE
			WHEN pof.pofEstadoHogar = 'RENUNCIO_A_SUBSIDIO_ASIGNADO' THEN 1
			WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA')
				THEN 3
		END [NOVEDAD],

		-- Columnas del Jefe de hogar para ser agregados más adelante
		CASE pj.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [tipoDocJefeHogar],
		pj.perNumeroIdentificacion [numDocJefeHogar],
		CONCAT(pj.perPrimerNombre, ' ', ISNULL(pj.perSegundoNombre, '')) [nombresJefeHogar],
		CONCAT(pj.perPrimerApellido, ' ', ISNULL(pj.perSegundoApellido, '')) [apellidosJefeHogar],
		1 [parentescoJefeHogar],
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = pj.perId), 103) [fechaNacJefeHogar],

		-- Columnas del json para el rango de ingresos
		CAST(REPLACE(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), '.00', '') as bigint) [ingresosJefeHogarJson],
		pof.pofJsonPostulacion [dataJsonIntegrantesHogar],
		pof.pofJefeHogar [jefeHogarId],
		FORMAT(sAsig.safFechaAceptacion, 'yyyy') [anioFechaAsig]

		FROM [dbo].[SolicitudNovedadFovis] postNov (NOLOCK)
		INNER JOIN ParametrizacionNovedad paramNov WITH(NOLOCK) ON paramNov.novId = postNov.snfParametrizacionNovedad
		INNER JOIN SolicitudNovedad solNov WITH(NOLOCK) ON solNov.snoId = paramNov.novId
		INNER JOIN [dbo].[SolicitudNovedadPersonaFovis] solNovPer (NOLOCK) ON solNovPer.spfSolicitudNovedadFovis = postNov.snfId
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = solNovPer.spfPostulacionFovis
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN Solicitud solDesdeNov (NOLOCK) ON postNov.snfSolicitudGlobal = solDesdeNov.solId
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		INNER JOIN IntegranteHogar intHogar WITH(NOLOCK) ON intHogar.inhPostulacionFovis = pof.pofId
		INNER JOIN Persona perIntHogar WITH(NOLOCK) ON intHogar.inhPersona = perIntHogar.perid
		LEFT JOIN SolicitudLegalizacionDesembolso sa (NOLOCK) on sa.sldPostulacionFOVIS = pof.pofId
		LEFT JOIN SolicitudAsignacion sAsig ON pof.pofSolicitudAsignacion = sAsig.safId
		WHERE inhestadoHogar = 'ACTIVO'
			AND pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND pof.pofEstadoHogar IN ('RENUNCIO_A_SUBSIDIO_ASIGNADO', 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA')
			AND ISNULL((SELECT TOP 1 FORMAT(solNovAudTemp.fechaCambioEstado, 'MM-dd-yyyy') FROM #solNovAud solNovAudTemp
				WHERE solNovAudTemp.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA' AND solNovAudTemp.snfId = postNov.snfId), 
				FORMAT(solDesdeNov.solFechaRadicacion, 'MM-dd-yyyy')) BETWEEN @fechaInicio AND @fechaFin
----	AND (pofValorAsignadoSFV-lgdMontoDesembolsado) = 0---CAMBIO 20230417 POR OLGA VEGA GLPI 61445


	-- Llena la tabla temporal con las legalizaciones y desembolsos
	INSERT INTO #DataNovedades (numDocumento, idHogar, tipoDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos,
		fechaNacimiento, fechaAsignacion, novedad, tipoDocJefeHogar, numDocJefeHogar, nombresJefeHogar, apellidosJefeHogar,
		parentescoJefeHogar, fechaNacJefeHogar, ingresosJefeHogarJson, dataJsonIntegrantesHogar, jefeHogarId, anioFechaAsig)
		SELECT DISTINCT
		-- Número de documento
		REPLACE(perIntHogar.perNumeroIdentificacion, '-', '') [NUMERO DE DOCUMENTO],
		-- Id_hogar
		sol.solNumeroRadicacion [ID_HOGAR],
		-- Tipo de documento
		CASE perIntHogar.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [TIPO DE DOCUMENTO],
		-- Nombres
		RTRIM(CONCAT(perIntHogar.perPrimerNombre, ' ', ISNULL(perIntHogar.perSegundoNombre, ''))) [NOMBRES],
		-- Apellidos
		RTRIM(CONCAT(perIntHogar.perPrimerApellido, ' ', ISNULL(perIntHogar.perSegundoApellido, ''))) [APELLIDOS],
		-- Parentesco
		CASE intHogar.inhTipoIntegrante
			WHEN 'CONYUGE' THEN 2
			WHEN 'CONYUGE_HOGAR' THEN 2
			WHEN 'HIJO_BIOLOGICO' THEN 3
			WHEN 'HIJO_BIOLOGICO_HOGAR' THEN 3
			WHEN 'HERMANO_HOGAR' THEN 4
			WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN 4
			WHEN 'PADRE' THEN 5
			WHEN 'PADRE_HOGAR' THEN 5
			WHEN 'PADRE_MADRE_ADOPTANTE_HOGAR' THEN 5
			WHEN 'MADRE' THEN 5
			WHEN 'MADRE_HOGAR' THEN 5
			WHEN 'ABUELO_HOGAR' THEN 6
			WHEN 'NIETO_HOGAR' THEN 6
			WHEN 'TIO_HOGAR' THEN 15
			WHEN 'SOBRINO_HOGAR' THEN 15
			WHEN 'SUEGRO_HOGAR' THEN 8
			WHEN 'PADRE_MADRE_ADOPTANTE_HOGAR' THEN 13
			WHEN 'HIJO_ADOPTIVO' THEN 14
			WHEN 'HIJO_ADOPTIVO_HOGAR' THEN 14
			WHEN 'YERNO_HOGAR' THEN 10
			WHEN 'BISABUELO_HOGAR' THEN 12
			WHEN 'BISNIETO_HOGAR' THEN 12
		END [PARENTESCO],
		-- Valor del subsidio
		pof.pofValorAsignadoSFV AS [VALOR DEL SUBSIDIO],
		-- Nit entidad otorgante
		REPLACE((SELECT par.prmValor FROM Parametro par WHERE par.prmNombre = 'NUMERO_ID_CCF'),
			'-', '') [NIT ENTIDAD OTORGANTE],
		-- Rango ingresos
		0 [RANGO DE INGRESOS],
		--	Fecha de Nacimiento
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = perIntHogar.perId), 103) [FECHA DE NACIMIENTO],
		-- Fecha de Asignación
		CONVERT(VARCHAR, sAsig.safFechaAceptacion, 103) [FECHA DE LA ASIGNACION],
		-- Novedad
		2 [NOVEDAD],

		-- Columnas del Jefe de hogar para ser agregados más adelante
		CASE pj.pertipoidentificacion 
			WHEN 'CEDULA_CIUDADANIA' THEN 1
			WHEN 'CEDULA_EXTRANJERIA' THEN 2
			WHEN 'REGISTRO_CIVIL' THEN 6
			WHEN 'TARJETA_IDENTIDAD' THEN 7
		END [tipoDocJefeHogar],
		pj.perNumeroIdentificacion [numDocJefeHogar],
		RTRIM(CONCAT(pj.perPrimerNombre, ' ', ISNULL(pj.perSegundoNombre, ''))) [nombresJefeHogar],
		RTRIM(CONCAT(pj.perPrimerApellido, ' ', ISNULL(pj.perSegundoApellido, ''))) [apellidosJefeHogar],
		1 [parentescoJefeHogar],
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = pj.perId), 103) [fechaNacJefeHogar],

		-- Columnas del json para el rango de ingresos
		CAST(REPLACE(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), '.00', '') as bigint) [ingresosJefeHogarJson],
		pof.pofJsonPostulacion [dataJsonIntegrantesHogar],
		pof.pofJefeHogar [jefeHogarId],
		FORMAT(sAsig.safFechaAceptacion, 'yyyy') [anioFechaAsig]

		FROM [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK)
		INNER JOIN legalizaciondesembolso legDes WITH(NOLOCK) on sa.[sldLegalizacionDesembolso] = legDes.lgdid
		INNER JOIN [dbo].[PostulacionFOVIS] pof (NOLOCK) on pof.pofId = sa.sldPostulacionFOVIS
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		INNER JOIN IntegranteHogar intHogar WITH(NOLOCK) ON intHogar.inhPostulacionFovis = pof.pofId
		INNER JOIN Persona perIntHogar WITH(NOLOCK) ON intHogar.inhPersona = perIntHogar.perid
		LEFT JOIN SolicitudAsignacion sAsig ON pof.pofSolicitudAsignacion = sAsig.safId
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
			-- Aplica para los hogares que tengan ingresos de hasta 2 SMMLV, 'Esta validacion se aplica dentro del cursor'
			AND inhestadoHogar = 'ACTIVO'
			AND pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND sa.sldEstadoSolicitud IN ('LEGALIZACION_Y_DESEMBOLSO_CONFIRMADO', 'DESEMBOLSO_EXITOSO_REPORTADO_POR_AREA_FINANCIERA', 'LEGALIZACION_Y_DESEMBOLSO_CERRADO')
			AND FORMAT(sa.sldFechaOperacion, 'MM-dd-yyyy') BETWEEN @fechaInicio AND @fechaFin
			AND (pofValorAsignadoSFV-lgdMontoDesembolsado) = 0---CAMBIO 20230417 POR OLGA VEGA GLPI 61445

	-- Verifica si encontró información de novedades o legalizados sin integrantes para agregarlos de primeras
	IF EXISTS (SELECT 1 FROM #DataNovedadesSinIntegrantes)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataNovedadesSinIntegrantes = CURSOR FAST_FORWARD FOR
		SELECT idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad
		FROM #DataNovedadesSinIntegrantes
			
		OPEN @consultarDataNovedadesSinIntegrantes
		FETCH NEXT FROM @consultarDataNovedadesSinIntegrantes INTO
		@idHogar,
		@tipoDocumento,
		@numDocumento,
		@nombres,
		@apellidos,
		@parentesco,
		@valorSubsidio,
		@nitEntidadOtorgante,
		@rangoIngresos,
		@fechaNacimiento,
		@fechaAsignacion,
		@novedad

		WHILE @@FETCH_STATUS = 0
			
		-- Por cada novedad que no tenga integrantes de hogar lo agrega a la tabla de Novedades
		BEGIN
			-- Solo agrega los que el rango de ingreso sea 1 y 2
			IF @rangoIngresos = 1 OR @rangoIngresos = 2
			BEGIN
				INSERT INTO #DataNovedadesModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
					nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad)
					SELECT @idHogar, @tipoDocumento, @numDocumento, @nombres, @apellidos, @parentesco, @valorSubsidio,
						@nitEntidadOtorgante, @rangoIngresos, @fechaNacimiento, @fechaAsignacion, @novedad
			END
				
			FETCH NEXT FROM @consultarDataNovedadesSinIntegrantes INTO
			@idHogar,
			@tipoDocumento,
			@numDocumento,
			@nombres,
			@apellidos,
			@parentesco,
			@valorSubsidio,
			@nitEntidadOtorgante,
			@rangoIngresos,
			@fechaNacimiento,
			@fechaAsignacion,
			@novedad
		END
		CLOSE @consultarDataNovedadesSinIntegrantes;
		DEALLOCATE @consultarDataNovedadesSinIntegrantes
	END

	-- Verifica si encontró información de Novedades
	IF EXISTS (SELECT 1 FROM #DataNovedades)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataNovedades = CURSOR FAST_FORWARD FOR
		SELECT idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad, tipoDocJefeHogar,
			numDocJefeHogar, nombresJefeHogar, apellidosJefeHogar, parentescoJefeHogar, fechaNacJefeHogar, ingresosJefeHogarJson, dataJsonIntegrantesHogar, jefeHogarId
		FROM #DataNovedades
			
		OPEN @consultarDataNovedades
		FETCH NEXT FROM @consultarDataNovedades INTO
		@idHogar,
		@tipoDocumento,
		@numDocumento,
		@nombres,
		@apellidos,
		@parentesco,
		@valorSubsidio,
		@nitEntidadOtorgante,
		@rangoIngresos,
		@fechaNacimiento,
		@fechaAsignacion,
		@novedad,
		@tipoDocJefeHogar,
		@numDocJefeHogar,
		@nombresJefeHogar,
		@apellidosJefeHogar,
		@parentescoJefeHogar,
		@fechaNacJefeHogar,
		@ingresosJefeHogarJson,
		@dataJsonIntegrantesHogar,
		@jefeHogarId

		WHILE @@FETCH_STATUS = 0
			
		-- Por cada item en el cursor entonces obtiene el JSON de los integrantes y suma los ingresos para el total así homologarlo al nivel de ingreso
		BEGIN
			DECLARE @ingresosJefeHogar BIGINT;
			DECLARE @sumIngresosMensualesIH BIGINT;
			DECLARE @sumIngresosMensuales BIGINT;
			DECLARE @smmlv BIGINT;

			IF @ingresosJefeHogarJson IS NOT NULL
			BEGIN
					SET @ingresosJefeHogar = convert(bigint ,replace(@ingresosJefeHogarJson,'.00',''))---modificación olga vega 20220916 por error de conversion
				-- Abre el json de integrantes para hacerle un sum a ingresos mensuales y asignarlo a la variable
				
		SET @sumIngresosMensualesIH = (SELECT SUM(cast([ingresosMensuales] as bigint)) [sumIngresosMensualesIH] FROM OPENJSON(@dataJsonIntegrantesHogar, '$.integrantesHogar')
					WITH ([idPersona] INT '$.idPersona', [ingresosMensuales] numeric(19,2) '$.ingresosMensuales', [estadoHogar] NVARCHAR(20) '$.estadoHogar')
					WHERE [estadoHogar] = 'ACTIVO');
			END
			ELSE
			BEGIN
				SET @ingresosJefeHogar = (SELECT jehIngresoMensual AS ingreso FROM jefehogar jh
					WHERE jh.jehid = @jefeHogarId AND jehEstadoHogar = 'ACTIVO')
  				
				select @sumIngresosMensualesIH = x.suma
				from
					(select jh.jehId as id, SUM(IH.inhSalarioMensual) as suma
					from jefehogar as jh
						inner join IntegranteHogar as ih on jh.jehId = ih.inhJefeHogar
					where inhestadoHogar = 'ACTIVO'
						and jh.jehid = @jefeHogarId
					group by jh.jehId) as x
			END

			-- Valida si los ingresos del jefe de hogar da null para entonces poner 0
			IF ISNUMERIC(@ingresosJefeHogar) = 0
			BEGIN
				SET @ingresosJefeHogar = 0
			END

			-- Valida si la suma de los integrantes dió null para dejarlo 0
			IF ISNUMERIC(@sumIngresosMensualesIH) = 0
			BEGIN
				SET @sumIngresosMensualesIH = 0
			END

			-- Suma los ingresos del jefe de hogar y de los integrantes del json
			SET @sumIngresosMensuales = @ingresosJefeHogar + @sumIngresosMensualesIH;

			-- Setea el salario minimo mensual legal vigente
			SET @smmlv = ISNULL((SELECT TOP 1 pLiquiSubsidio.plsSMLMV FROM ParametrizacionLiquidacionSubsidio pLiquiSubsidio 
					WHERE pLiquiSubsidio.plsAnioVigenciaParametrizacion = @anioFechaAsig), 
						(SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV'));
			-- Rango de ingresos
			SET @sumNivelIngreso = CASE 
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 0.00 AND 1.00 THEN 1
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 1.00 AND 2.00 THEN 2
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 2.00 AND 100 THEN 3
			END;

			-- Solo agrega los que el rango de ingreso sea 1 y 2
			IF @sumNivelIngreso = 1 OR @sumNivelIngreso = 2
			BEGIN
				IF (SELECT TOP 1 dNovMTemp.idHogar FROM #DataNovedadesModificado dNovMTemp WHERE dNovMTemp.numDocumento = @numDocJefeHogar) IS NULL
				BEGIN
					-- Agrega el jefe de hogar de primeras
					INSERT INTO #DataNovedadesModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
						nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad)
						SELECT @idHogar, @tipoDocJefeHogar, @numDocJefeHogar, @nombresJefeHogar, @apellidosJefeHogar, @parentescoJefeHogar, @valorSubsidio,
							@nitEntidadOtorgante, @sumNivelIngreso, @fechaNacJefeHogar, @fechaAsignacion, @novedad
				END

				-- Agrega el integrante del hogar
				INSERT INTO #DataNovedadesModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
					nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, novedad)
					SELECT @idHogar, @tipoDocumento, @numDocumento, @nombres, @apellidos, @parentesco, 0,
						@nitEntidadOtorgante, @sumNivelIngreso, @fechaNacimiento, @fechaAsignacion, @novedad
			END
				
			FETCH NEXT FROM @consultarDataNovedades INTO
			@idHogar,
			@tipoDocumento,
			@numDocumento,
			@nombres,
			@apellidos,
			@parentesco,
			@valorSubsidio,
			@nitEntidadOtorgante,
			@rangoIngresos,
			@fechaNacimiento,
			@fechaAsignacion,
			@novedad,
			@tipoDocJefeHogar,
			@numDocJefeHogar,
			@nombresJefeHogar,
			@apellidosJefeHogar,
			@parentescoJefeHogar,
			@fechaNacJefeHogar,
			@ingresosJefeHogarJson,
			@dataJsonIntegrantesHogar,
			@jefeHogarId
		END
		CLOSE @consultarDataNovedades;
		DEALLOCATE @consultarDataNovedades;
	END

	SELECT  
		idHogar [ID_HOGAR],
		tipoDocumento [TIPO DE DOCUMENTO],
		numDocumento [NÚMERO DE DOCUMENTO],
		nombres [NOMBRES],
		apellidos [APELLIDOS],
		parentesco [PARENTESCO],
		valorSubsidio [VALOR DEL SUBSIDIO],
		nitEntidadOtorgante [NIT ENTIDAD OTORGANTE],
		rangoIngresos [RANGO INGRESOS],
		fechaNacimiento [FECHA DE NACIMIENTO],
		fechaAsignacion [FECHA DE ASIGNACIÓN],
		novedad [NOVEDAD]
	FROM #DataNovedadesModificado
	group by idHogar ,
		tipoDocumento  ,
		numDocumento ,
		nombres ,
		apellidos ,
		parentesco  ,
		valorSubsidio  ,
		nitEntidadOtorgante  ,
		rangoIngresos  ,
		fechaNacimiento  ,
		fechaAsignacion  ,
		novedad  
END