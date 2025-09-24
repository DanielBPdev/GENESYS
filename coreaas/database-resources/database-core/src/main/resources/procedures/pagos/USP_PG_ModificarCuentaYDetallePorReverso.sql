


-- =============================================
-- Author: Diego Morales Amaya
-- Create date: 2023/10/11
-- Update : 2023/10/27
-- Description:	Realiza la actualización del valor real de la transacción de retiro y cambia los estados de los abonos asociados al retiro. Este SP será utilizado por un servicio de reverso
-- =============================================

CREATE   PROCEDURE [dbo].[USP_PG_ModificarCuentaYDetallePorReverso]
    @idTransaccionTerceroPagador VARCHAR(200),
    @nombreTerceroPagador VARCHAR(200),
	@Resultado INT OUTPUT,
	@mensajeError VARCHAR(200) OUTPUT
AS

    SET NOCOUNT ON;
	SET @Resultado = 0;
	SET @mensajeError = '';

	BEGIN TRY
		
		--DECLARE @nombreTerceroPagador VARCHAR(200);
		DECLARE @cantAbonosRelacionadosRetiro INT;
		DECLARE @cantUsuariosTercero INT;

		SET @cantAbonosRelacionadosRetiro = 0;

		-- Se consulta el usuario del convenio tercero pagador registrado en Genesys por medio del idTerceroPagador
		SELECT @cantUsuariosTercero = COUNT(*)
		FROM ConvenioTerceroPagador 
		WHERE conUsuarioGenesys = @nombreTerceroPagador;

		IF @cantUsuariosTercero = 0
		BEGIN
			SET @mensajeError = 'No existe un convenio tercero pagador con el identificador ingresado';
			SET @Resultado = 0;
		END
		ELSE
		BEGIN
			-- Se consulta la cantidad de abonos que están relacionados a la transacción de retiro que corresponde al idTransaccionTerceroPagador y usuarioTerceroPagador
			SELECT @cantAbonosRelacionadosRetiro = COUNT(*) 
			FROM CuentaAdministradorSubsidio
			WHERE casIdCuentaAdmonSubsidioRelacionado IN (
				SELECT casId 
				FROM CuentaAdministradorSubsidio
				WHERE casIdTransaccionTerceroPagador = @idTransaccionTerceroPagador
					AND casNombreTerceroPagado = @nombreTerceroPagador
			);

			IF @cantAbonosRelacionadosRetiro = 0
			BEGIN
				SET @mensajeError = 'No se le puede aplicar el reverso a la transacción de retiro, no hay abonos asociados';
				SET @Resultado = 0;
			END
			ELSE
			BEGIN
				-- Actualizar casValorRealTransaccion a 0
				UPDATE CuentaAdministradorSubsidio
				SET casValorRealTransaccion = 0 
				WHERE casIdTransaccionTerceroPagador = @idTransaccionTerceroPagador
					AND casNombreTerceroPagado = @nombreTerceroPagador

				-- Actualizar casEstadoTRansaccionSubsidio a 'APLICADO'
				UPDATE CuentaAdministradorSubsidio
				SET casEstadoTRansaccionSubsidio = 'APLICADO', casIdCuentaAdmonSubsidioRelacionado = NULL
				WHERE casIdCuentaAdmonSubsidioRelacionado IN (
					SELECT casId 
					FROM CuentaAdministradorSubsidio
					WHERE casIdTransaccionTerceroPagador = @idTransaccionTerceroPagador
						AND casNombreTerceroPagado = @nombreTerceroPagador
				);

				-- Actualizar dsaUsuarioTransaccionRetiro y dsaFechaTransaccionRetiro a NULL
				UPDATE DetalleSubsidioAsignado
				SET dsaUsuarioTransaccionRetiro = NULL,
					dsaFechaTransaccionRetiro = NULL
				WHERE dsaCuentaAdministradorSubsidio IN (
					SELECT casId 
					FROM CuentaAdministradorSubsidio
					WHERE casIdCuentaAdmonSubsidioRelacionado IN (
						SELECT casId 
						FROM CuentaAdministradorSubsidio
						WHERE casIdTransaccionTerceroPagador = @idTransaccionTerceroPagador
							AND casNombreTerceroPagado = @nombreTerceroPagador
					)
				);  
				SET @Resultado = 1;
			END
		END		
	END TRY
	BEGIN CATCH
		SET @mensajeError = 'Se produjo un error al realizar el reverso';
		SET @Resultado = 0;
	END CATCH



