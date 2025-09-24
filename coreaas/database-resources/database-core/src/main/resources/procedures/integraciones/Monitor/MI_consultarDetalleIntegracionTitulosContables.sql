-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosContables]
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@integracion varchar(200),
@estado varchar(200),
@operacion varchar(200),
@referencia varchar(200), 
@tipoMovimiento varchar(200), 
@fechaDocumento varchar(200), 
@fechaContabilizacion varchar(200), 
@documentoContable varchar(200)
AS
BEGIN

	declare @tabla varchar(400);  -- CAMBIO BORIS SOLUCION VISUALIZACION N DE REGISTROS  CONTABLE CUOTA 

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'consecutivo,tipoMovimiento
	  ,estado
	  ,fecIng
      ,horaIng
      ,fechaDocumento
      ,fechaContabilizacion
      ,periodo
      ,referencia
      ,observacion
      ,moneda
      ,documentoContable
      ,sociedad
      ,ejercicio
      ,nroIntentos
      ,fecProceso
      ,horaProceso
      ,usuario
	    ,Componente';
	end;
	IF @integracion = 'Aportes'
	begin
		set @tabla = 'consecutivo,[tipoMovimiento]
	  ,estado
	  ,fecIng
      ,horaIng
      ,fechaDocumento
      ,fechaContabilizacion
      ,periodo
      ,referencia
      ,[observacion
      ,moneda
      ,documentoContable
      ,sociedad
      ,ejercicio
      ,nroIntentos
      ,fecProceso
      ,horaProceso
      ,usuario';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'consecutivo,tipoMovimiento
	  ,estado
	  ,fecIng
      ,horaIng
      ,fechaDocumento
      ,fechaContabilizacion
      ,periodo
      ,periodoAnioLiquidacion
      ,referencia
      ,observacion
      ,moneda
      ,documentoContable
      ,sociedad
      ,ejercicio
      ,nroIntentos
      ,fecProceso
      ,horaProceso
      ,usuario';
	end;

	
	select @tabla

	

END