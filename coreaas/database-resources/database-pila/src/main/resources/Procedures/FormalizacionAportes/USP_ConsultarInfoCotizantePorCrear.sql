/****** Object:  StoredProcedure [dbo].[USP_ConsultarInfoCotizantePorCrear]    Script Date: 2024-07-05 2:30:42 PM ******/
 
-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2021-04-20
-- Description:	consulta datos el cotizante de la planilla para formalizar   
-- Ajuste 2022-06-16
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ConsultarInfoCotizantePorCrear]
	@idRegistroGeneral Bigint
AS
BEGIN
SET NOCOUNT ON;	
BEGIN TRY
	
    EXEC sp_execute_remote CoreReferenceData,
		N'	CREATE TABLE #TemCotizanteTMP (
			perNumeroIdentificacion varchar(20),
			perTipoIdentificacion varchar(50),
			perPrimerNombre	varchar(30), 
			perSegundoNombre	varchar(30), 
			perPrimerApellido	varchar(30), 
			perSegundoApellido varchar(30), 
			origen varchar(250)
	    )
	    INSERT INTO #TemCotizanteTMP (perNumeroIdentificacion,perTipoIdentificacion,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,origen)
	    EXEC sp_execute_remote PilaReferenceData,
			N''SELECT 
				tct.tctIdCotizante,
	            tct.tctTipoIdCotizante,
				tct.tctPrimerNombre,
				tct.tctSegundoNombre,
				tct.tctPrimerApellido,
				tct.tctSegundoApellido
	        FROM TemCotizante tct
	        INNER JOIN TemAporte ON tctIdTransaccion = temIdTransaccion
	        WHERE temRegistroGeneral = @idRegistroGeneral'',
		  	N''@idRegistroGeneral bigint'',
	 		@idRegistroGeneral=@idRegistroGeneral;

				CREATE TABLE #Persona_aud_tmp(
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
					,inserted.perSegundoNombre,inserted.perPrimerApellido,inserted.perSegundoApellido,inserted.perCreadoPorPila	into #Persona_aud_tmp
			SELECT null as perDigitoVerificacion,tmp.perNumeroIdentificacion,
			concat(tmp.perPrimerNombre,N'' '',tmp.perSegundoNombre,N'' '',tmp.perPrimerApellido,N'' '',tmp.perSegundoApellido) as perRazonSocial,
			tmp.perTipoIdentificacion,null as perUbicacionPrincipal,tmp.perPrimerNombre,tmp.perSegundoNombre,tmp.perPrimerApellido,tmp.perSegundoApellido,1 as perCreadoPorPila
			FROM #TemCotizanteTMP tmp
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
				from #Persona_aud_tmp
			end
			-- execute [dbo].[USP_CopiarAportesDesdeTemporalPila] @idRegistroGeneral

			',
	  	N'@idRegistroGeneral bigint',
 		@idRegistroGeneral=@idRegistroGeneral;
       
	   /* 
	SELECT 
		tct.*
	FROM TemCotizante tct
	INNER JOIN TemAporte tem ON tct.tctIdTransaccion = tem.temIdTransaccion
	INNER JOIN #PersonaCotizantePorCrear pcp ON pcp.perTipoIdentificacion = tct.tctTipoIdCotizante AND pcp.perNumeroIdentificacion = tct.tctIdCotizante
	WHERE tem.temRegistroGeneral = @idRegistroGeneral
	*/

END TRY
BEGIN CATCH

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate()
		,'USP_ConsultarInfoAportanteProcesar | @idRegistroGeneral=' + CAST(@idRegistroGeneral AS VARCHAR(20)) 
		,ERROR_MESSAGE());
	
	THROW
END CATCH;
END;