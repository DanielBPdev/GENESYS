
CREATE OR ALTER   PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContablesDetalle]
@integracion varchar(200),
@idEncabezado varchar(200)
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON
	declare @scriptSQL varchar(max)
	declare @tabla varchar(max)

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'SELECT [consecutivo]
      ,[codigoSap]
      ,[claveCont]
      ,replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as [importeDocumento]
      ,[ref1]
      ,[tipoDocumento]
      ,[ref3]
      ,[tipoSector]
      ,convert(numeric,anulacion) as [anulacion]
      ,case idProyecto when ''0'' then '''' else idProyecto end as [idProyecto]
      ,[nombreProyecto]
      ,convert(numeric,proyectoPropio) as [proyectoPropio]
      ,convert(numeric,rendimientoFinanciero) as [rendimientoFinanciero]
      ,[asignacion]
      ,[clavePaisBanco]
      ,[claveBanco]
      ,[nroCuentaBancaria]
      ,[titularCuenta]
      ,[claveControlBanco]
      ,[tipoBancoInter]
      ,[referenciaBancoCuenta]
      ,[id]
      ,[codigoGenesys]
      ,[tipo]
      ,[claseCuenta]
      ,[fechaEjecucion]
  FROM [sap].[IC_FOVIS_Det]';
	end;
	IF @integracion = 'Aportes'
	begin
		set @tabla = 'SELECT [consecutivo]
      ,[codigoSap]
      ,replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as [importeDocumento]
      ,[operador]
      ,[claveCont]
      ,[asignacion]
	  ,[textoposicion]
      ,[ref1]
	  ,[ref2]
      ,[tipoDocumento]
      ,[ref3]
      ,[noIdentificado]
      ,[adelantado]
      ,[identificadorDelBanco]
      ,[codigoBanco]
      ,[transitoria]
      ,[claseDeAporte]
      ,[claseDeAportePrescripcion]
      ,convert(numeric,tieneIntereses) as [tieneIntereses]
      ,[tipoInteres]
      ,convert(numeric,correccion) as [correccion]
      ,[tipoMora]
      ,[id]
      ,[codigoGenesys]
      ,[tipo]
      ,[claseCuenta]
      ,[usuario]
      ,[fechaEjecucion]

  FROM [sap].[IC_Aportes_Det]';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'SELECT [consecutivo]
      ,[codigoSap]
      ,replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as [importeDocumento]
      ,[claveCont]
      ,[asignacion]
	   ,[ref1]
      ,[ref2]
      ,[tipoDocumento]
      ,[ref3]
      ,[tipoCuotaMonetaria]
      ,[idTipoCm]
      ,[formaPago]
      ,[codigoEntidadDescuento]
      ,[bancoDispersionDevolucion]
      ,[cajaPago]
      ,[id]
      ,[codigoGenesys]
      ,[tipo]
      ,[claseCuenta]
      ,[usuario]
      ,[fechaEjecucion]
	  ,[periodoliquidado]
  FROM [sap].[IC_CM_Det]';
	end;

	set @scriptSQL = @tabla;

	--if @filtros != ''
	--begin
		set @scriptSQL = CONCAT(@scriptSQL,' where consecutivo = ', @idEncabezado)
	--End

	print @scriptSQL 

	EXEC (@scriptSQL)

END