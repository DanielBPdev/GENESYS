-- =============================================
-- Author: Robinson Castillo Capera
-- Create date: 2023-11-19
-- Description:	Encargado de crear las personas faltantes en el proceso de masivos. 
-- =============================================
CREATE  OR ALTER  PROCEDURE [masivos].[crearPersonas]
AS
BEGIN
SET NOCOUNT ON;	
BEGIN TRY
	
    EXEC sp_execute_remote CoreReferenceData,
		N'	
		
		SET XACT_ABORT ON;  

		begin try 

			begin transaction	

					CREATE TABLE #TemCotizanteTMP_B7 (
					perNumeroIdentificacion varchar(20),
					perTipoIdentificacion varchar(50),
					perRazonSocial varchar(300),
					PrimerNombre varchar(300),
					SegundoNombre varchar(300),
					PrimerApellido varchar(300), 
					SegundoApellido varchar(300),
					magTipoAportante varchar(50),
					origen varchar(250)
					)
					INSERT INTO #TemCotizanteTMP_B7 (perNumeroIdentificacion,perTipoIdentificacion,perRazonSocial,PrimerNombre,SegundoNombre,PrimerApellido,SegundoApellido, magTipoAportante, origen)
					EXEC sp_execute_remote PilaReferenceData,
					N''select perNumeroIdentificacion,perTipoIdentificacion,max(perRazonSocial) as perRazonSocial,max(perPrimerNombre) as PrimerNombre,max(perSegundoNombre) as SegundoNombre,max(perPrimerApellido) as PrimerApellido,max(perSegundoApellido) as SegundoApellido, magTipoAportante
					from masivos.crearPersonaAporCot
					where procesado = 0
					group by perNumeroIdentificacion,perTipoIdentificacion, magTipoAportante''

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
					SELECT null as perDigitoVerificacion,tmp.perNumeroIdentificacion,tmp.perRazonSocial,tmp.perTipoIdentificacion,null as perUbicacionPrincipal,
					tmp.PrimerNombre,tmp.SegundoNombre,tmp.PrimerApellido,tmp.SegundoApellido,1 as perCreadoPorPila
					FROM #TemCotizanteTMP_B7 tmp
					LEFT JOIN Persona per ON per.perTipoIdentificacion = tmp.perTipoIdentificacion AND per.perNumeroIdentificacion = tmp.perNumeroIdentificacion
					where per.perId IS NULL

					create table #empresa_aud_tempB7(
					empId bigInt, empPersona bigInt, empCreadoPorPila bit
					)

					insert dbo.empresa (empPersona, empCreadoPorPila)
					output inserted.empId, inserted.empPersona, inserted.empCreadoPorPila into #empresa_aud_tempB7
					select a.perId, a.perCreadoPorPila
					from #Persona_aud_tmp_B7 as a
					inner join #TemCotizanteTMP_B7 per ON per.perTipoIdentificacion = a.perTipoIdentificacion AND per.perNumeroIdentificacion = a.perNumeroIdentificacion
					where per.magTipoAportante = ''EMPLEADOR''



					DECLARE @iRevision BIGINT;
					EXEC USP_UTIL_GET_CrearRevision ''com.asopagos.entidades.ccf.personas.Persona'', NULL, '''', ''USUARIO_SISTEMA'', @iRevision OUTPUT

					insert aud.Persona_aud (perId,REV,REVTYPE,perDigitoVerificacion,perNumeroIdentificacion,perRazonSocial,perTipoIdentificacion,perUbicacionPrincipal,perPrimerNombre
					,perSegundoNombre,perPrimerApellido,perSegundoApellido,perCreadoPorPila)
					select perId,@iRevision,0,perDigitoVerificacion,perNumeroIdentificacion,perRazonSocial,perTipoIdentificacion,perUbicacionPrincipal,
					perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,perCreadoPorPila
					from #Persona_aud_tmp_B7

					insert aud.Empresa_aud (empId,REV,REVTYPE,empPersona,empNombreComercial,empFechaConstitucion,empNaturalezaJuridica,empCodigoCIIU,empArl,empUltimaCajaCompensacion,empPaginaWeb,empRepresentanteLegal,empRepresentanteLegalSuplente,empEspecialRevision
					,empUbicacionRepresentanteLegal,empUbicacionRepresentanteLegalSuplente,empCreadoPorPila,empEnviadoAFiscalizacion,empMotivoFiscalizacion,empFechaFiscalizacion)
					select empId, @iRevision, 0, empPersona, null as empNombreComercial,null as empFechaConstitucion,null as empNaturalezaJuridica,null as empCodigoCIIU,null as empArl,null as empUltimaCajaCompensacion, null as empPaginaWeb,null as empRepresentanteLegal,
					null as empRepresentanteLegalSuplente,null as empEspecialRevision,null as empUbicacionRepresentanteLegal,null as empUbicacionRepresentanteLegalSuplente,empCreadoPorPila,null as empEnviadoAFiscalizacion,null as empMotivoFiscalizacion,null as empFechaFiscalizacion
					from #empresa_aud_tempB7

				commit transaction

			end try
			
			begin catch
			
			    IF (XACT_STATE()) = -1  
			    BEGIN  
			        ROLLBACK TRANSACTION;  
			    END;  
			  
			    IF (XACT_STATE()) = 1  
			    BEGIN  
			        COMMIT TRANSACTION;     
			    END;  
			end catch
			'
       
END TRY
BEGIN CATCH

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate()
		,'[masivos].[crearPersonas]'  
		,ERROR_MESSAGE());
	
	THROW
END CATCH;
END;