-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/08/06
-- Description:	Procedimiento almacenado encargado de validar el cumplimiento 
-- de las condiciones de sucursal para un aporte
-- =============================================
CREATE PROCEDURE [dbo].[USP_VerificarCumplimientoSucursal]
	@IdRegistroGeneral BIGINT,
	@codigoSucursalPILA VARCHAR(10),
	@codigoSucursalPrincipal VARCHAR(40),
	@cumpleSucursal BIT OUTPUT
AS

BEGIN
SET NOCOUNT ON;
--print 'Inicia USP_VerificarCumplimientoSucursal'

--print '@codigoSucursalPILA'
--print @codigoSucursalPILA
--print '@codigoSucursalPrincipal'
--print @codigoSucursalPrincipal

-- se consulta el nombre de la sucursal en PILA y sucursal principal
DECLARE @nombreSucursalPILA VARCHAR(40),
	@nombreSucursalPrincipal VARCHAR(40)
SELECT @nombreSucursalPILA = regNomSucursal, @nombreSucursalPrincipal = regOUTNomSucursalPrincipal FROM staging.RegistroGeneral WHERE regId = @IdRegistroGeneral

IF @codigoSucursalPILA IS NULL 
BEGIN
	-- la sucursal pila es NULL, s�lo puede pasar cuando la forma de presentacion es U
	DECLARE @formaPrestacion VARCHAR(1)

	SELECT @formaPrestacion = ISNULL(reg.regFormaPresentacion, 'U')
	FROM staging.RegistroGeneral reg with (nolock)
	WHERE reg.regId = @IdRegistroGeneral

	IF ISNULL(@formaPrestacion, '') = 'U'
	BEGIN
		-- con forma de presentaci�n U, la sucursal PILA se iguala a la sucursal principal a efectos de la validaci�n
		SET @codigoSucursalPILA = @codigoSucursalPrincipal
		SET @nombreSucursalPILA = @nombreSucursalPrincipal

		-- se actualiza en el RegistroGeneral con el fin de que se procese correctamente el cambio de sucursal que aplique
		UPDATE reg
		SET reg.regCodSucursal = @codigoSucursalPILA,
			reg.regNomSucursal = @nombreSucursalPILA,
			reg.regDateTimeUpdate = dbo.getLocalDate()
		FROM staging.RegistroGeneral reg with (nolock)
		WHERE reg.regId = @IdRegistroGeneral
	END
END	

IF	NOT EXISTS (SELECT TOP 1 1 
		FROM staging.SucursalEmpresa sue with (nolock)
		INNER JOIN staging.RegistroGeneral reg with (nolock) ON
				sueTipoIdentificacion = regTipoIdentificacionAportante AND
				sueNumeroIdentificacion = regNumeroIdentificacionAportante AND
				sueCodigoSucursal = @codigoSucursalPILA AND
				sueNombreSucursal = @nombreSucursalPILA AND
				sueTransaccion = regTransaccion
		WHERE regId = @IdRegistroGeneral)
	OR (@codigoSucursalPILA IS NULL AND @codigoSucursalPrincipal IS NULL)
BEGIN
	--print 'Pasar a bandeja'

	SET @cumpleSucursal = 0;
END
ELSE
BEGIN
	SET @cumpleSucursal = 1;
END;

--print 'Finaliza USP_VerificarCumplimientoSucursal'
END;