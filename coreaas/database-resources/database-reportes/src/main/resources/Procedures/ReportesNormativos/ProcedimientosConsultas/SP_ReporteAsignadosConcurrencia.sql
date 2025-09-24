/****** Object:  StoredProcedure [dbo].[reporteAsignadosConcurrencia]    Script Date: 11/10/2022 2:06:49 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAsignadosConcurrencia]    Script Date: 05/02/2022 9:00 a.m. ******/

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 5 Febrero 2022
-- Description: Procedimiento almacenado encargado de ejecutar la consulta Asignados Concurrencia con sus rangos de fechas
-- Reporte Normativo 30A
---execute reporteAsignadosConcurrencia '2022-06-01','2022-06-30'
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[reporteAsignadosConcurrencia]
(
    @fechaInicio DATE,
	@fechaFin DATE
)
AS

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

BEGIN
	-- (REPORTE 30A) Asignados Concurrencia
	-- Descripción: El reporte de Asignados Concurrencia de la circular 0013 del 03 de octubre del 2019 debe 
	-- reportar la información correspondiente a hogares que presenten estado asignado.

	-- Se asignan todas las variables a utilizar
	DECLARE @idHogar VARCHAR(20);
	DECLARE @tipoDocumento SMALLINT;
	DECLARE @numDocumento VARCHAR(20);
	DECLARE @nombres VARCHAR(50);
	DECLARE @apellidos VARCHAR(50);
	DECLARE @parentesco SMALLINT;
	DECLARE @valorSubsidio BIGINT;
	DECLARE @nitEntidadOtorgante BIGINT;
	DECLARE @rangoIngresos SMALLINT;
	DECLARE @fechaNacimiento VARCHAR(10);
	DECLARE @fechaAsignacion VARCHAR(10);
	DECLARE @tipoDocJefeHogar SMALLINT;
	DECLARE @numDocJefeHogar varchar(20);
	DECLARE @nombresJefeHogar VARCHAR(50);
	DECLARE @apellidosJefeHogar VARCHAR(50);
	DECLARE @parentescoJefeHogar SMALLINT;
	DECLARE @fechaNacJefeHogar VARCHAR(10);
	DECLARE @ingresosJefeHogarJson VARCHAR(MAX);
	DECLARE @dataJsonIntegrantesHogar VARCHAR(MAX);
	DECLARE @jefeHogarId INT;

	DECLARE @sumNivelIngreso BIGINT;
	DECLARE @numJefeHogarActual BIGINT;
	DECLARE @consultarDataAsignados AS CURSOR;
	DECLARE @consultarDataAsignadosSinIntegrantes AS CURSOR;

	-- Tabla temporal que contiene la información de cada postulación asignada
	DROP TABLE IF EXISTS #DataAsignados;
	CREATE TABLE #DataAsignados (numDocumento VARCHAR(20), idHogar VARCHAR(20), tipoDocumento SMALLINT, nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT, valorSubsidio NUMERIC(19,0),
		nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10), tipoDocJefeHogar SMALLINT, numDocJefeHogar VARCHAR(20), nombresJefeHogar  VARCHAR(50),
		apellidosJefeHogar VARCHAR(50), parentescoJefeHogar SMALLINT, fechaNacJefeHogar VARCHAR(10), ingresosJefeHogarJson VARCHAR(MAX), dataJsonIntegrantesHogar VARCHAR(MAX), jefeHogarId INT)

	-- Tabla temporal que resulta de haber pasado por el cursor modificando cada registro con respecto a los JSON para los ingresos mensuales y los integrantes del hogar
	DROP TABLE IF EXISTS #DataAsignadosModificado;
	CREATE TABLE #DataAsignadosModificado (idHogar VARCHAR(20), tipoDocumento SMALLINT, numDocumento VARCHAR(20), nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT, valorSubsidio BIGINT,
		nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10))

	-- Tabla temporal que resulta contiene la información de los asignados sin integrantes de hogar
	DROP TABLE IF EXISTS #DataAsignadosSinIntegrantes;
	CREATE TABLE #DataAsignadosSinIntegrantes (idHogar VARCHAR(20), tipoDocumento SMALLINT, numDocumento VARCHAR(20), nombres VARCHAR(50), apellidos VARCHAR(50), parentesco SMALLINT,
		valorSubsidio BIGINT, nitEntidadOtorgante BIGINT, rangoIngresos SMALLINT, fechaNacimiento VARCHAR(10), fechaAsignacion VARCHAR(10))

	-- Ingresa la informacion de asignados sin integrantes de hogar a la tabla temporal
	INSERT INTO #DataAsignadosSinIntegrantes (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion)
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
				/ (SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV')) BETWEEN 0.00 AND 1.00 THEN 1
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
				/ (SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV')) BETWEEN 1.00 AND 2.00 THEN 2
			WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, ISNULL(JSON_VALUE(pof.pofJsonPostulacion, '$.postulacion.jefeHogar.ingresosMensuales'), j.jehIngresoMensual)) 
			/ (SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV')) BETWEEN 2.00 AND 100 THEN 3
		END [RANGO DE INGRESOS],
		--	Fecha de Nacimiento
		CONVERT(VARCHAR, (SELECT perDet.pedFechaNacimiento FROM PersonaDetalle perDet 
			WHERE perDet.pedPersona = pj.perId), 103) [FECHA DE NACIMIENTO],
		-- Fecha de Asignación
		CONVERT(VARCHAR, sAsig.safFechaAceptacion, 103) [FECHA DE LA ASIGNACION]

		FROM SolicitudAsignacion sAsig (NOLOCK)
		INNER JOIN PostulacionFOVIS pof ON pof.pofSolicitudAsignacion = sAsig.safId
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN ActaAsignacionFovis actAsig (NOLOCK) on sAsig.safId = actAsig.aafSolicitudAsignacion
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
			-- Aplica para los hogares que tengan ingresos de hasta 2 SMMLV, 'Esta validacion se aplica dentro del cursor'
			AND pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND (SELECT TOP 1 intHogar.inhId FROM IntegranteHogar intHogar WHERE intHogar.inhPostulacionFovis = pof.pofId AND intHogar.inhestadoHogar = 'ACTIVO') IS NULL
			AND sAsig.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin

	-- Llena la tabla temporal con las asignaciones
	INSERT INTO #DataAsignados (numDocumento, idHogar, tipoDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos,
		fechaNacimiento, fechaAsignacion, tipoDocJefeHogar, numDocJefeHogar, nombresJefeHogar, apellidosJefeHogar,
		parentescoJefeHogar, fechaNacJefeHogar, ingresosJefeHogarJson, dataJsonIntegrantesHogar, jefeHogarId)
		SELECT  
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
		pof.pofJefeHogar [jefeHogarId]
		-- intHogar.inhId [integranteHogarId]

		FROM SolicitudAsignacion sAsig (NOLOCK)
		INNER JOIN PostulacionFOVIS pof ON pof.pofSolicitudAsignacion = sAsig.safId
		INNER JOIN CicloAsignacion ca (NOLOCK) ON pof.pofCicloAsignacion = ca.ciaId
		INNER JOIN ActaAsignacionFovis actAsig (NOLOCK) on sAsig.safId = actAsig.aafSolicitudAsignacion
		INNER JOIN SolicitudPostulacion solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN Solicitud sol (NOLOCK) ON solPost.spoSolicitudGlobal = sol.solId -- Se ata a la solicitud de postulacion para conseguir el radicado
		INNER JOIN JefeHogar j WITH(NOLOCK) ON pof.pofJefeHogar = j.jehId
		INNER JOIN Afiliado a WITH(NOLOCK) ON j.jehAfiliado = a.afiId
		INNER JOIN Persona pj WITH(NOLOCK) ON a.afipersona = pj.perid
		INNER JOIN IntegranteHogar intHogar WITH(NOLOCK) ON intHogar.inhPostulacionFovis = pof.pofId
		INNER JOIN Persona perIntHogar WITH(NOLOCK) ON intHogar.inhPersona = perIntHogar.perid
		WHERE pof.pofResultadoAsignacion = 'ESTADO_ASIGNADO'
			-- Aplica para los hogares que tengan ingresos de hasta 2 SMMLV, 'Esta validacion se aplica dentro del cursor'
			AND inhestadoHogar = 'ACTIVO'
			AND pof.pofModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
			AND sAsig.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin

	-- Verifica si encontró información de asignados sin integrantes para agregarlos de primeras
	IF EXISTS (SELECT 1 FROM #DataAsignadosSinIntegrantes)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataAsignadosSinIntegrantes = CURSOR FAST_FORWARD FOR
		SELECT idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion
		FROM #DataAsignadosSinIntegrantes
			
		OPEN @consultarDataAsignadosSinIntegrantes
		FETCH NEXT FROM @consultarDataAsignadosSinIntegrantes INTO
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
		@fechaAsignacion

		WHILE @@FETCH_STATUS = 0
			
		-- Por cada asignación que no tenga integrantes de hogar lo agrega a la tabla de Asignados
		BEGIN
			-- Solo agrega los que el rango de ingreso sea 1 y 2
			IF @rangoIngresos = 1 OR @rangoIngresos = 2
			BEGIN
				INSERT INTO #DataAsignadosModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
					nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion)
					SELECT @idHogar, @tipoDocumento, @numDocumento, @nombres, @apellidos, @parentesco, @valorSubsidio,
						@nitEntidadOtorgante, @rangoIngresos, @fechaNacimiento, @fechaAsignacion
			END
				
			FETCH NEXT FROM @consultarDataAsignadosSinIntegrantes INTO
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
			@fechaAsignacion
		END
		CLOSE @consultarDataAsignadosSinIntegrantes;
		DEALLOCATE @consultarDataAsignadosSinIntegrantes
	END

	-- Verifica si encontró información de asignados
	IF EXISTS (SELECT 1 FROM #DataAsignados)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataAsignados = CURSOR FAST_FORWARD FOR
		SELECT idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio, nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion, tipoDocJefeHogar,
			numDocJefeHogar, nombresJefeHogar, apellidosJefeHogar, parentescoJefeHogar, fechaNacJefeHogar, ingresosJefeHogarJson, dataJsonIntegrantesHogar, jefeHogarId
		FROM #DataAsignados
			
		OPEN @consultarDataAsignados
		FETCH NEXT FROM @consultarDataAsignados INTO
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
		@tipoDocJefeHogar,
		@numDocJefeHogar,
		@nombresJefeHogar,
		@apellidosJefeHogar,
		@parentescoJefeHogar,
		@fechaNacJefeHogar,
		@ingresosJefeHogarJson,
		@dataJsonIntegrantesHogar,
		@jefeHogarId

		SET @numJefeHogarActual = 0
		WHILE @@FETCH_STATUS = 0
			
		-- Por cada item en el cursor entonces obtiene el JSON de los integrantes y suma los ingresos para el total así homologarlo al nivel de ingreso
		BEGIN
			DECLARE @ingresosJefeHogar NUMERIC(19,0);
			DECLARE @sumIngresosMensualesIH NUMERIC(19,0);
			DECLARE @sumIngresosMensuales NUMERIC(19,0);
			DECLARE @smmlv NUMERIC(19,0);

			IF @ingresosJefeHogarJson IS NOT NULL
			BEGIN
 

				SET @ingresosJefeHogar = @ingresosJefeHogarJson
				-- Abre el json de integrantes para hacerle un sum a ingresos mensuales y asignarlo a la variable
 
				SET @sumIngresosMensualesIH = (SELECT SUM(cast([ingresosMensuales] as bigint)) [sumIngresosMensualesIH] FROM OPENJSON(@dataJsonIntegrantesHogar, '$.integrantesHogar')
					WITH ([idPersona] INT '$.idPersona', [ingresosMensuales] numeric(19,2) '$.ingresosMensuales', [estadoHogar] NVARCHAR(20) '$.estadoHogar')
					WHERE [estadoHogar] = 'ACTIVO');
			END
			ELSE
			BEGIN
				SET @ingresosJefeHogar = (SELECT jehIngresoMensual AS ingreso FROM jefehogar jh
					WHERE jh.jehid = @jefeHogarId AND jehEstadoHogar = 'ACTIVO')
				SET @sumIngresosMensualesIH = (SELECT SUM(inhSalarioMensual) FROM jefehogar jh INNER JOIN [dbo].[IntegranteHogar] ih ON jh.jehId = ih.inhJefeHogar 
					WHERE jh.jehid = @jefeHogarId AND inhestadoHogar = 'ACTIVO')
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
			SET @smmlv = (SELECT pR.prmValor FROM Parametro pR WHERE pR.prmNombre = 'SMMLV');
			-- Rango de ingresos
			SET @sumNivelIngreso = CASE 
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 0.00 AND 1.00 THEN 1
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 1.00 AND 2.00 THEN 2
				WHEN CONVERT(DECIMAL(12,2), CONVERT(FLOAT, @sumIngresosMensuales) / @smmlv) BETWEEN 2.00 AND 100 THEN 3
			END;

			-- Solo agrega los que el rango de ingreso sea 1 y 2
			IF @sumNivelIngreso = 1 OR @sumNivelIngreso = 2
			BEGIN
				IF @numJefeHogarActual <> @numDocJefeHogar
				BEGIN
					-- Agrega el jefe de hogar de primeras
					INSERT INTO #DataAsignadosModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
						nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion)
						SELECT @idHogar, @tipoDocJefeHogar, @numDocJefeHogar, @nombresJefeHogar, @apellidosJefeHogar, @parentescoJefeHogar, @valorSubsidio,
							@nitEntidadOtorgante, @sumNivelIngreso, @fechaNacJefeHogar, @fechaAsignacion
				END

				-- Agrega el integrante del hogar
				INSERT INTO #DataAsignadosModificado (idHogar, tipoDocumento, numDocumento, nombres, apellidos, parentesco, valorSubsidio,
					nitEntidadOtorgante, rangoIngresos, fechaNacimiento, fechaAsignacion)
					SELECT @idHogar, @tipoDocumento, @numDocumento, @nombres, @apellidos, @parentesco, 0,
						@nitEntidadOtorgante, @sumNivelIngreso, @fechaNacimiento, @fechaAsignacion

				SET @numJefeHogarActual = @numDocJefeHogar
			END
				
			FETCH NEXT FROM @consultarDataAsignados INTO
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
		CLOSE @consultarDataAsignados;
		DEALLOCATE @consultarDataAsignados;
	END

	SELECT 
		idHogar [ID_HOGAR],
		tipoDocumento [TIPO DE DOCUMENTO],
		numDocumento [NUMERO DE DOCUMENTO],
		nombres [NOMBRES],
		apellidos [APELLIDOS],
		parentesco [PARENTESCO],
		valorSubsidio [VALOR DEL SUBSIDIO],
		nitEntidadOtorgante [NIT ENTIDAD OTORGANTE],
		rangoIngresos [RANGO DE INGRESOS],
		fechaNacimiento [FECHA DE NACIMIENTO],
		fechaAsignacion [FECHA DE LA ASIGNACION]
	FROM #DataAsignadosModificado
END