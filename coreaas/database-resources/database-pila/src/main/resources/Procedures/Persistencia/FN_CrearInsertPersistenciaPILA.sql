-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2021/03/10
-- Description:	Funcion que genera la sentencia INSERT
-- para persistencia sobre la tabla dada
-- Se agrega control para los registros 4 
-- =============================================
CREATE OR ALTER FUNCTION [dbo].[FN_CrearInsertPersistenciaPILA]
(
	@Tabla VARCHAR(50)
)
RETURNS VARCHAR(MAX)
BEGIN
	DECLARE @SQLClause VARCHAR(MAX)
	DECLARE @Columnas VARCHAR(MAX) = ''
	DECLARE @SelectClause VARCHAR(MAX) = ''
	DECLARE @TipoArchivoGeneral VARCHAR(1)
	DECLARE @TipoArchivo VARCHAR(2)
	DECLARE @TipoRegistroSubStr VARCHAR(255)
	DECLARE @CampoConSecuencia VARCHAR(50)
	DECLARE @CampoIndicePlanilla VARCHAR(50)
	DECLARE @SecuenciaAplicar VARCHAR(50)
	DECLARE @ppcId INT
		
	SELECT @TipoRegistroSubStr = pppSubStr
	FROM PilaPersistenciaParametrizacion
	WHERE pppTabla = @Tabla
	AND pppCampo = 'TipoRegistro'

	SELECT @ppcId = ppcId
	FROM dbo.PilaPersistenciaClausulasInsert
	WHERE ppcTabla = @Tabla

	SELECT 
		@Columnas = ppcCampoIndicePlanilla + CASE WHEN ppcCampoConSecuencia IS NOT NULL THEN ', ' + ppcCampoConSecuencia ELSE '' END + @Columnas, 
		@TipoArchivo = ppcTipoArchivo, 
		@TipoArchivoGeneral = LEFT(ppcTipoArchivo,1),
		@CampoConSecuencia = ppcCampoConSecuencia, 
		@SecuenciaAplicar = ppcSecuenciaAplicar,
		@CampoIndicePlanilla = ppcCampoIndicePlanilla
	FROM PilaPersistenciaClausulasInsert
	WHERE ppcTabla = @Tabla

	SET @SQLClause = '
	IF NOT EXISTS (SELECT 1 FROM ' + @Tabla + ' WHERE ' + @CampoIndicePlanilla + ' =  @IndicePlanilla)
	BEGIN
	'  
	IF (@ppcId = 4) -- 'PilaArchivoIRegistro4'
	BEGIN
		SET @SQLClause = @SQLClause + '
	INSERT INTO dbo.' + @Tabla

		SELECT	@Columnas = COALESCE(@Columnas + ',
			', '') + pppCampo, 
				@SelectClause = COALESCE(@SelectClause + ',
			', '') + CASE WHEN pppEsRequerido = 0 THEN 'CASE WHEN LTRIM(RTRIM(' + pppSubStr + ')) = '''' THEN NULL ELSE ' + pppSubStr + ' END' ELSE pppSubStr END 
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppCampo <> 'TipoRegistro'
		ORDER BY pppOrden

		SET @SQLClause = @SQLClause + '
		(' + @Columnas + ')' + '
		SELECT @IndicePlanilla' + CASE WHEN @CampoConSecuencia IS NOT NULL THEN ',
		NEXT VALUE FOR ' + @SecuenciaAplicar ELSE '' END +
		@SelectClause + '
		FROM staging.PreliminarArchivoPila 
		INNER JOIN dbo.PilaIndicePlanilla ON papIndicePlanilla = pipId
		WHERE papIndicePlanilla = @IndicePlanilla
		AND (CASE WHEN ''' +  @TipoArchivo + ''' IN (''AP'', ''IP'') THEN (CASE WHEN pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) ELSE (CASE WHEN NOT pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) END) = 1
		AND papTipoArchivo = ''' + @TipoArchivoGeneral + '''' +
		CASE WHEN @TipoArchivoGeneral = 'I' 
			THEN
		'
		AND ' + @TipoRegistroSubStr + ' = ''' + RIGHT(@Tabla, 1) + '''
		'
			ELSE '' END	
	END

	IF (@ppcId in (1,2,5,6,7,8,9)) -- 'PilaArchivoIRegistro3'
	BEGIN
		SET @SQLClause = @SQLClause + '
	INSERT INTO dbo.' + @Tabla

		SELECT	@Columnas = COALESCE(@Columnas + ',
			', '') + pppCampo, 
				@SelectClause = COALESCE(@SelectClause + ',
			', '') + CASE WHEN pppEsRequerido = 0 THEN 'CASE WHEN LTRIM(RTRIM(' + pppSubStr + ')) = '''' THEN NULL ELSE ' + pppSubStr + ' END' ELSE pppSubStr END 
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppCampo <> 'TipoRegistro'
		ORDER BY pppOrden

		SET @SQLClause = @SQLClause + '
		(' + @Columnas + ')' + '
		SELECT @IndicePlanilla' + CASE WHEN @CampoConSecuencia IS NOT NULL THEN ',
		NEXT VALUE FOR ' + @SecuenciaAplicar ELSE '' END +
		@SelectClause + '
		/*FROM staging.PreliminarArchivoPila  Comentario de prueba realizado por Robinson Castillo */
		FROM (select * from staging.PreliminarArchivoPila /*where RTRIM(LTRIM(SUBSTRING(papTextoRegistro,1, 1))) <> ''4'' Se deja comentario por error en el nombre del aportante que inicia por numero 4 */) as t
		INNER JOIN dbo.PilaIndicePlanilla ON papIndicePlanilla = pipId
		WHERE papIndicePlanilla = @IndicePlanilla
		AND (CASE WHEN ''' +  @TipoArchivo + ''' IN (''AP'', ''IP'') THEN (CASE WHEN pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) ELSE (CASE WHEN NOT pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) END) = 1 
		AND papTipoArchivo = ''' + @TipoArchivoGeneral + '''' +
		CASE WHEN @TipoArchivoGeneral = 'I' 
			THEN
		'
		AND ' + @TipoRegistroSubStr + ' = ''' + RIGHT(@Tabla, 1) + '''
		and RTRIM(LTRIM(SUBSTRING(papTextoRegistro,1, 1))) <> 4 /*Se agrega control para evitar el registro tipo 4 ya que por ser numerico en la posición 6 puede dar 1 y genera doble registro. */
		'
			ELSE '' END	
	END
	if (@ppcId = 3)
	BEGIN
		DECLARE @SizeI31 INT, @SizeI32 INT, @SizeI33 INT

		SELECT @SizeI31 = MAX(pppPosicionFinal)
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppSubId = 5

		SELECT @SizeI32 = MAX(pppPosicionFinal)
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppSubId = 6		

		SELECT @SizeI33 = MAX(pppPosicionFinal)
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppSubId = 7

		SELECT	@Columnas = COALESCE(@Columnas + ',
			', '') + pppCampo, 
				@SelectClause = COALESCE(@SelectClause + ',
			', '') + pppSubStr + ' AS ' + pppCampo
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppCampo <> 'TipoRegistro'
		AND pppSubId = 5
		ORDER BY pppSubId

		SET @SQLClause = @SQLClause + '
	INSERT INTO dbo.' + @Tabla + '
		(' + @Columnas + ')' + '
		SELECT @IndicePlanilla' + CASE WHEN @CampoConSecuencia IS NOT NULL THEN ',
		NEXT VALUE FOR ' + @SecuenciaAplicar ELSE '' END +
		@SelectClause + '
		FROM staging.PreliminarArchivoPila
		INNER JOIN dbo.PilaIndicePlanilla ON papIndicePlanilla = pipId
		WHERE papIndicePlanilla = @IndicePlanilla
		AND (CASE WHEN ''' +  @TipoArchivo + ''' IN (''AP'', ''IP'') THEN (CASE WHEN pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) ELSE (CASE WHEN NOT pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) END) = 1
		AND papTipoArchivo = ''' + @TipoArchivoGeneral + '''		
		AND LEN(papTextoRegistro) = ' + CAST(@SizeI31 AS varchar(10)) + '
		AND TRY_CONVERT(int,' + @TipoRegistroSubStr + ') = 3
		'

		SET @Columnas = ''
		SELECT	@Columnas = COALESCE(@Columnas + ',
			', '') + pppCampo + ' = ' + pppSubStr
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppCampo <> 'TipoRegistro'
		AND pppSubId = 6
		ORDER BY pppSubId

		SET @Columnas = RIGHT(@Columnas, LEN(@Columnas) - 1)

		SET @SQLClause = @SQLClause + '
		UPDATE dbo.' + @Tabla + '
		SET ' + @Columnas + '
		FROM dbo.' + @Tabla + '
		INNER JOIN staging.PreliminarArchivoPila ON papIndicePlanilla = pi3IndicePlanilla
		INNER JOIN dbo.PilaIndicePlanilla ON papIndicePlanilla = pipId
		WHERE papIndicePlanilla = @IndicePlanilla
		AND (CASE WHEN ''' +  @TipoArchivo + ''' IN (''AP'', ''IP'') THEN (CASE WHEN pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) ELSE (CASE WHEN NOT pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) END) = 1
		AND papTipoArchivo = ''' + @TipoArchivoGeneral + '''		
		AND TRY_CONVERT(int,' + @TipoRegistroSubStr + ') = 3
		AND LEN(papTextoRegistro) = ' + CAST(@SizeI32 AS varchar(10)) + '
		'

		SET @Columnas = ''
		SELECT	@Columnas = COALESCE(@Columnas + ',
			', '') + pppCampo + ' = ' + pppSubStr
		FROM PilaPersistenciaParametrizacion
		WHERE pppTabla = @Tabla
		AND pppCampo <> 'TipoRegistro'
		AND pppSubId = 7
		ORDER BY pppSubId

		SET @Columnas = RIGHT(@Columnas, LEN(@Columnas) - 1)

		SET @SQLClause = @SQLClause + '
		UPDATE dbo.' + @Tabla + '
		SET ' + @Columnas + '
		FROM dbo.' + @Tabla + '
		INNER JOIN staging.PreliminarArchivoPila ON papIndicePlanilla = pi3IndicePlanilla
		INNER JOIN dbo.PilaIndicePlanilla ON papIndicePlanilla = pipId
		WHERE papIndicePlanilla = @IndicePlanilla
		AND (CASE WHEN ''' +  @TipoArchivo + ''' IN (''AP'', ''IP'') THEN (CASE WHEN pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) ELSE (CASE WHEN NOT pipTipoArchivo LIKE (''%P%'') THEN 1 ELSE 0 END) END) = 1
		AND papTipoArchivo = ''' + @TipoArchivoGeneral + '''		
		AND TRY_CONVERT(int,' + @TipoRegistroSubStr + ') = 3
		AND LEN(papTextoRegistro) = ' + CAST(@SizeI33 AS varchar(10)) + '
		'
	END
	
	SET @SQLClause = @SQLClause + '
	END'

	RETURN @SQLClause
END