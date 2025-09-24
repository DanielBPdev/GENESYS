-- =============================================
-- Author:      Boris Leon
-- Create Date: 20/03/2024
-- Description: SP para traer los headers de la tabla para la exportacion de los detalles de integracion
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContablesExport]
@integracion varchar(200)
AS
BEGIN

	declare @tabla varchar(1500);

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'fecIng,
      horaIng,
      fechaDocumento,
      fechaContabilizacion,
      periodo,
      referencia,
      tipoMovimiento,
      consecutivo,
      observacion,
      moneda,
      documentoContable,
      sociedad,
      nroIntentos,
      fecProceso,
      horaProceso,
      estadoReg,
      usuario,
      componente,
      codigoSap,
      claveCont,
      importeDocumento,
      ref1,
      tipoDocumento,
      ref3,
      tipoSector,
      anulacion,
      idProyecto,
      nombreProyecto,
      proyectoPropio,
      rendimientoFinanciero,
      asignacion,
      clavePaisBanco,
      claveBanco,
      nroCuentaBancaria,
      titularCuenta,
      claveControlBanco,
      tipoBancoInter,
      referenciaBancoCuenta,
      id,
      codigoGenesys,
      tipo,
      claseCuenta';
	end;

	IF @integracion = 'Aportes'
	begin
		set @tabla = 'fecIng,
      horaIng,
      fechaDocumento,
      fechaContabilizacion,
      periodo,
      referencia,
      tipoMovimiento,
      consecutivo,
      observacion,
      moneda,
      documentoContable,
      sociedad,
      nroIntentos,
      fecProceso,
      horaProceso,
      estadoReg,
      usuario,
      codigoSap,
      importeDocumento,
      operador,
      claveCont,
      asignacion,
      textoPosicion,
      ref1,
      ref2,
      tipoDocumento,
      ref3,
      noIdentificado,
      adelantado,
      identificadorDelBanco,
      codigoBanco,
      transitoria,
      claseDeAporte,
      claseDeAportePrescripcion,
      tieneIntereses,
      tipoInteres,
      correccion,
      tipoMora,
      id,
      codigoGenesys,
      tipo,
      claseCuenta';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'fecIng,
      horaIng,
      fechaDocumento,
      fechaContabilizacion,
      periodo,
      referencia,
      tipoMovimiento,
      consecutivo,
      observacion,
      moneda,
      documentoContable,
      sociedad,
      radicadoLiquidacion,
      nroIntentos,
      fecProceso,
      horaProceso,
      estadoReg,
      usuario,
      codigoSap,
      importeDocumento,
      claveCont,
      asignacion,
      periodoLiquidado,
      tipoDocumento,
      ref1,
      ref2,
      ref3,
      tipoCuotaMonetaria,
      idTipoCm,
      formaPago,
      codigoEntidadDescuento,
      bancoDispersionDevolucion,
      id,
      codigoGenesys,
      tipo,
      claseCuenta';
	end;

	
	select @tabla

END