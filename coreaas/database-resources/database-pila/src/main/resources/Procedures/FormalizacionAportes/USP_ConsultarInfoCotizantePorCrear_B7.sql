/****** Object:  StoredProcedure [dbo].[USP_ConsultarInfoCotizantePorCrear_B7]    Script Date: 2024-07-05 10:54:28 AM ******/
 
-- =============================================
-- Author: Robinson Castillo Capera
-- Create date: 2022-06-22
-- Description:	Consulta si el cotizante no existe en la tabla persona, lo crea, solo crea la persona. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ConsultarInfoCotizantePorCrear_B7]
	@idRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;	
BEGIN TRY
	
    EXEC sp_execute_remote CoreReferenceData,
		N'	CREATE TABLE #TemCotizanteTMP_B7 (
			perNumeroIdentificacion varchar(20),
			perTipoIdentificacion varchar(50),
			perPrimerNombre	varchar(30), 
			perSegundoNombre	varchar(30), 
			perPrimerApellido	varchar(30), 
			perSegundoApellido varchar(30), 
			origen varchar(250)
	    )
	    INSERT INTO #TemCotizanteTMP_B7 (perNumeroIdentificacion,perTipoIdentificacion,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,origen)
	    EXEC sp_execute_remote PilaReferenceData,
			N''select redNumeroIdentificacionCotizante,redTipoIdentificacionCotizante,redPrimerNombre,redSegundoNombre,redPrimerApellido,redSegundoApellido
				from staging.RegistroDetallado with (nolock)
				where redRegistroGeneral = @idRegistroGeneral
				group by redNumeroIdentificacionCotizante,redTipoIdentificacionCotizante,redPrimerNombre,redSegundoNombre,redPrimerApellido,redSegundoApellido'',
		  	N''@idRegistroGeneral bigint'',
	 		@idRegistroGeneral=@idRegistroGeneral;

				CREATE TABLE #Persona_aud_tmp_B7(
				[perId] [bigint] NOT NULL,
				[perDigitoVerificacion] [smallint] NULL,
				[perNumeroIdentificacion] [varchar](16) NULL,
				[perRazonSocial] [varchar](250) NULL,
				[perTipoIdentificacion] [varchar](20) NULL,
				[perUbicacionPrincipal] [bigint] NULL,
				[perPrimerNombre] [varchar](50) NULL,
				[perSegundoNombre] [varchar](50) NULL,
				[perPrimerApellido] [varchar](50) NULL,
				[perSegundoApellido] [varchar](50) NULL,
				[perCreadoPorPila] [bit] NULL)

			insert persona (perDigitoVerificacion,perNumeroIdentificacion,perRazonSocial,perTipoIdentificacion,perUbicacionPrincipal,perPrimerNombre,
							perSegundoNombre,perPrimerApellido,perSegundoApellido,perCreadoPorPila)
			output inserted.perId, inserted.perDigitoVerificacion,inserted.perNumeroIdentificacion,inserted.perRazonSocial,inserted.perTipoIdentificacion,inserted.perUbicacionPrincipal,inserted.perPrimerNombre
					,inserted.perSegundoNombre,inserted.perPrimerApellido,inserted.perSegundoApellido,inserted.perCreadoPorPila	into #Persona_aud_tmp_B7
			SELECT null as perDigitoVerificacion,tmp.perNumeroIdentificacion,
			concat(tmp.perPrimerNombre,N'' '',tmp.perSegundoNombre,N'' '',tmp.perPrimerApellido,N'' '',tmp.perSegundoApellido) as perRazonSocial,
			tmp.perTipoIdentificacion,null as perUbicacionPrincipal,tmp.perPrimerNombre,tmp.perSegundoNombre,tmp.perPrimerApellido,tmp.perSegundoApellido,1 as perCreadoPorPila
			FROM #TemCotizanteTMP_B7 tmp
			LEFT JOIN Persona per ON per.perTipoIdentificacion = tmp.perTipoIdentificacion AND per.perNumeroIdentificacion = tmp.perNumeroIdentificacion
			where per.perId IS NULL
			
			
			
			IF (@@ROWCOUNT)>0
		 
			BEGIN 
					DECLARE @iRevision BIGINT;
					EXEC USP_UTIL_GET_CrearRevision ''com.asopagos.entidades.ccf.personas.Persona'', NULL, '''', ''USUARIO_SISTEMA'', @iRevision OUTPUT

					insert aud.Persona_aud (perId,REV,REVTYPE,perDigitoVerificacion,perNumeroIdentificacion,perRazonSocial,perTipoIdentificacion,perUbicacionPrincipal,perPrimerNombre
					,perSegundoNombre,perPrimerApellido,perSegundoApellido,perCreadoPorPila)
					select perId,@iRevision,0,perDigitoVerificacion,perNumeroIdentificacion,perRazonSocial,perTipoIdentificacion,perUbicacionPrincipal,
					perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,perCreadoPorPila
					from #Persona_aud_tmp_B7

			END 
			',
	  	N'@idRegistroGeneral bigint',
 		@idRegistroGeneral=@idRegistroGeneral;
       
END TRY
BEGIN CATCH

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate()
		,'USP_ConsultarInfoCotizantePorCrear_B7 | @idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,ERROR_MESSAGE());
	
	THROW
END CATCH;
END;