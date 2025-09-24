-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/01/21
-- Update : 
-- Description:	Procedimiento almacenado que valida archivo de retiro tercero pagador
-- =============================================
create PROCEDURE USP_PG_InsertRestuladosValidacionCargaManualRetiroTerceroPag
@parametros varchar(max)
/*
@nIdConvenio bigint,
@sFileLoadedName VARCHAR(500),
@sUsuario VARCHAR(500),
@nidArchivoTerceroPagadorEfectivo BIGINT,
@nIdentificacionECM VARCHAR(500),
@nmNombreArchivo VARCHAR(500)
*/
AS
	SET NOCOUNT ON;

	DECLARE @registrosCargados int,
	@registrosFallidos INT,
	@idLoaded BIGINT,
	@iRevision BIGINT

	-- SE SEPARA EL PARAMETROS EN PARAMETROS SEPARADOS POR COMA
	declare @nIdConvenio bigint,
			@sFileLoadedName VARCHAR(500),
			@sUsuario VARCHAR(500),
			@nidArchivoTerceroPagadorEfectivo BIGINT,
			@nIdentificacionECM VARCHAR(500),
			@nmNombreArchivo VARCHAR(500)

				declare	@pos int,
			@name varchar(500),
			@count int = 1

	WHILE CHARINDEX(',', @parametros) > 0
	BEGIN
		SELECT @pos  = CHARINDEX(',', @parametros)
		SELECT @name = SUBSTRING(@parametros, 1, @pos-1)

		IF @count = 1
			SELECT @nIdConvenio = cast(@name as bigint);
		IF @count = 2
			SELECT @sFileLoadedName = @name;
		IF @count = 3
			SELECT @sUsuario = @name;
		IF @count = 4
			SELECT @nidArchivoTerceroPagadorEfectivo = cast(@name as bigint);
		IF @count = 5
			SELECT @nIdentificacionECM = @name;
		IF @count = 6
			SELECT @nmNombreArchivo = @name;

		SET @count = @count + 1;
		SELECT @parametros = SUBSTRING(@parametros, @pos+1, LEN(@parametros)-@pos)
	END

	SET @idLoaded = (SELECT ISNULL(MAX(id),0)+1 FROM FileLoaded)

	SELECT @registrosCargados = COUNT(*)
	FROM TempArchivoRetiroTerceroPagadorEfectivo tat
	WHERE tat.tatIdConvenio = @nIdConvenio
	  AND tat.tatArchivoRetiroTerceroPagadorEfectivo = @nidArchivoTerceroPagadorEfectivo

	SELECT @registrosFallidos = COUNT(*)
	FROM TempArchivoRetiroTerceroPagadorEfectivo tat
	WHERE tat.tatIdConvenio = @nIdConvenio
	  AND tat.tatResultado = 'NO_EXITOSO'
	  AND tat.tatArchivoRetiroTerceroPagadorEfectivo = @nidArchivoTerceroPagadorEfectivo

	/*INSERT FileLoaded
	(id, finalDate, initialDate, fileLoadedName, format, loadedRegisters, state, fileDefinition_id, validatorprofile)
	SELECT @idLoaded,--  id	bigint
			dbo.GetLocalDate(),-- finalDate	datetime
			dbo.GetLocalDate(),--initialDate	datetime
			@sFileLoadedName,-- fileLoadedName	varchar
			'DELIMITED_TEXT_PLAIN',--format	varchar
			@registrosCargados,-- loadedRegisters	bigint
			CASE WHEN @registrosFallidos>0 THEN 'LOADED_WITH_ERRORS' ELSE 'SUCCESFUL' END,-- state	varchar
			1233,-- fileDefinition_id	bigint
			null --validatorprofile	bigint
	*/		
	DECLARE @TempConsolaEstadoCargueMasivo table(
	cecId bigint,
	cecCcf varchar(5) NULL,
	cecTipoProcesoMasivo varchar(40) NOT NULL,
	cecUsuario varchar(255) NULL,
	cecFechaInicio datetime2(7) NULL,
	cecFechaFin datetime2(7) NULL,
	cecNumRegistroObjetivo bigint NULL,
	cecNumRegistroProcesado bigint NULL,
	cecNumRegistroConErrores bigint NULL,
	cecNumRegistroValidos bigint NULL,
	cecEstadoCargueMasivo varchar(15) NOT NULL,
	cecCargueId bigint NULL,
	cecFileLoadedId bigint NULL,
	cecIdentificacionECM varchar(255) NOT NULL,
	cecGradoAvance numeric(6,3) NULL,
	cecNombreArchivo varchar(255) NULL);

	
	INSERT ConsolaEstadoCargueMasivo (cecCcf,
	cecTipoProcesoMasivo,cecUsuario,cecFechaInicio,cecFechaFin,
	cecNumRegistroObjetivo,cecNumRegistroProcesado,cecNumRegistroConErrores,cecNumRegistroValidos,
	cecEstadoCargueMasivo,cecCargueId,cecFileLoadedId,cecIdentificacionECM,cecGradoAvance,cecNombreArchivo)
	OUTPUT INSERTED.cecId,INSERTED.cecCcf,INSERTED.cecTipoProcesoMasivo,INSERTED.cecUsuario,INSERTED.cecFechaInicio,INSERTED.cecFechaFin,INSERTED.cecNumRegistroObjetivo,INSERTED.cecNumRegistroProcesado,INSERTED.cecNumRegistroConErrores,INSERTED.cecNumRegistroValidos,INSERTED.cecEstadoCargueMasivo,INSERTED.	cecCargueId,INSERTED.cecFileLoadedId,INSERTED.cecIdentificacionECM,INSERTED.cecGradoAvance,INSERTED.cecNombreArchivo
	INTO @TempConsolaEstadoCargueMasivo
	SELECT (SELECT SUBSTRING(cns.cnsValor,1,150) FROM dbo.Constante cns WHERE cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO'),
	'CARGUE_MANUAL_DE_ARCH_TERCEROS_PAG',@sUsuario,dbo.GetLocalDate(),dbo.GetLocalDate(),
	@registrosCargados,	@registrosCargados,	@registrosFallidos,	@registrosCargados-@registrosFallidos,
	'FINALIZADO',@nidArchivoTerceroPagadorEfectivo,@idLoaded,@nIdentificacionECM,100.000,@nmNombreArchivo

	EXEC [dbo].[USP_UTIL_GET_CrearRevision] 'com.asopagos.entidades.transversal.core.ConsolaEstadoCargueMasivo',1,'',@sUsuario,@iRevision OUTPUT

	INSERT INTO aud.ConsolaEstadoCargueMasivo_aud (cecId,cecCcf,
	cecTipoProcesoMasivo,cecUsuario,cecFechaInicio,cecFechaFin,
	cecNumRegistroObjetivo,cecNumRegistroProcesado,cecNumRegistroConErrores,cecNumRegistroValidos,
	cecEstadoCargueMasivo,cecCargueId,cecFileLoadedId,cecIdentificacionECM,cecGradoAvance,cecNombreArchivo,REV,REVTYPE)
	SELECT cecId,cecCcf,
	cecTipoProcesoMasivo,cecUsuario,cecFechaInicio,cecFechaFin,
	cecNumRegistroObjetivo,cecNumRegistroProcesado,cecNumRegistroConErrores,cecNumRegistroValidos,
	cecEstadoCargueMasivo,cecCargueId,cecFileLoadedId,cecIdentificacionECM,cecGradoAvance,cecNombreArchivo,@iRevision,0
	FROM @TempConsolaEstadoCargueMasivo

	DECLARE @tempResultadoHallazgoValidacionArchivo table (
		rhvId bigint NOT NULL,
		rhvIdConsolaEstadoCargueMasivo bigint NOT NULL,
		rhvNumeroLinea bigint NULL,
		rhvNombreCampo varchar(255) NULL,
		rhvError varchar(MAX) NULL
	)

	DECLARE @iNewId BIGINT	
	EXEC USP_GET_GestorValorSecuencia @registrosFallidos, 'SEC_consecutivoRHV', @iNewId OUTPUT

	INSERT ResultadoHallazgoValidacionArchivo (rhvId,rhvIdConsolaEstadoCargueMasivo,rhvNumeroLinea,rhvNombreCampo,rhvError)
	OUTPUT INSERTED.rhvId,INSERTED.rhvIdConsolaEstadoCargueMasivo,INSERTED.rhvNumeroLinea,INSERTED.rhvNombreCampo,INSERTED.rhvError
	INTO @tempResultadoHallazgoValidacionArchivo	

	SELECT ROW_NUMBER() OVER (Order by tat.tatId) + @iNewId -1 ,
	(SELECT min(cecId) FROM @TempConsolaEstadoCargueMasivo),
	tat.tatLinea,
	tat.tatNombreCampo,
	tat.tatMotivo
	FROM TempArchivoRetiroTerceroPagadorEfectivo tat
	WHERE tat.tatIdConvenio = @nIdConvenio
	  AND tat.tatResultado = 'NO_EXITOSO'
	  AND tat.tatArchivoRetiroTerceroPagadorEfectivo = @nidArchivoTerceroPagadorEfectivo

	EXEC [dbo].[USP_UTIL_GET_CrearRevision] 'com.asopagos.entidades.transversal.core.ResultadoHallazgoValidacionArchivo',@registrosFallidos,'',@sUsuario,@iRevision OUTPUT

	INSERT aud.ResultadoHallazgoValidacionArchivo_aud(rhvId,rhvIdConsolaEstadoCargueMasivo,rhvNumeroLinea,rhvNombreCampo,rhvError,REV,REVTYPE)
	SELECT rhvId,rhvIdConsolaEstadoCargueMasivo,rhvNumeroLinea,rhvNombreCampo,rhvError,@iRevision,0
	FROM @tempResultadoHallazgoValidacionArchivo
;



