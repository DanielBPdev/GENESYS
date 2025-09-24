-- =============================================
-- Author:      Boris Leon
-- Create Date: 20/03/2024
-- Description: SP para traer los registros de la tabla para la exportacion de los detalles de integracion
-- =============================================
CREATE OR ALTER   PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContablesExport]
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
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON
	declare @scriptSQL varchar(max)
	declare @tabla varchar(max)
	declare @filtros varchar(2000)
	declare @nombreEstado varchar(200) = 'estadoReg'

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'SELECT  
				fecIng,
				horaIng,
				fechaDocumento,
				fechaContabilizacion,
				periodo,
				referencia,
				tipoMovimiento,
				e.consecutivo,
				REPLACE(observacion, ''|'',''''),
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
				replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as importeDocumento,
				ref1,
				tipoDocumento,
				ref3,
				tipoSector,
				convert(numeric,anulacion) as [anulacion],
				idProyecto,
				nombreProyecto,
				convert(numeric,proyectoPropio) as [proyectoPropio],
				convert(numeric,rendimientoFinanciero) as [rendimientoFinanciero],
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
				claseCuenta
			FROM sap.IC_FOVIS_Enc e
			INNER JOIN sap.IC_fovis_Det d  ON e.consecutivo = d.consecutivo ';
	end;

	IF @integracion = 'Aportes'
	begin
		set @tabla = 'SELECT 
			fecIng,
			horaIng,
			fechaDocumento,
			fechaContabilizacion,
			periodo,
			referencia,
			tipoMovimiento,
			e.consecutivo,
			REPLACE(observacion, ''|'',''''),
			moneda,
			documentoContable,
			sociedad,
			nroIntentos,
			fecProceso,
			horaProceso,
			estadoReg,
			e.usuario,
			codigoSap,
			replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as importeDocumento,
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
			claseCuenta
		FROM sap.IC_Aportes_Enc e
		INNER JOIN sap.IC_aportes_Det d  ON e.consecutivo = d.consecutivo ';
	end;

	IF @integracion = 'Cuota'
	begin
		set @tabla = 'SELECT
			fecIng,
			horaIng,
			fechaDocumento,
			fechaContabilizacion,
			periodo,
			referencia,
			tipoMovimiento,
			e.consecutivo,
			REPLACE(observacion, ''|'',''''),
			moneda,
			documentoContable,
			sociedad,
			radicadoLiquidacion,
			nroIntentos,
			fecProceso,
			horaProceso,
			estadoReg,
			e.usuario,
			codigoSap,
			replace(convert(varchar,FORMAT(convert(numeric,importeDocumento),''$ ###,###'')),'','',''.'') as importeDocumento,
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
			claseCuenta
		FROM sap.IC_CM_Enc e
		INNER JOIN sap.IC_CM_Det d ON e.consecutivo = d.consecutivo ';
	end;

	set @scriptSQL = @tabla

	if @fechaInicial != '' and @fechaFinal != ''
	begin
		set @filtros = CONCAT (@filtros, ' and fecing between ''',@fechaInicial,''' and ''', @fechaFinal ,'''') 
	end;

	if @estado != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', @nombreEstado, ' =''',@estado,'''') 
	end;

	if @referencia != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', ' referencia =''',@referencia,'''') 
	end

	if @tipoMovimiento != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', ' tipoMovimiento =''',@tipoMovimiento,'''') 
	end

	if @fechaDocumento != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', ' fechaDocumento =''',@fechaDocumento,'''') 
	end
	
	if @fechaContabilizacion != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', ' fechaContabilizacion =''',@fechaContabilizacion,'''') 
	end

	if @documentoContable != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', ' documentoContable =''',@documentoContable,'''') 
	end
	
	
	--if @filtros != ''
	--begin
		set @scriptSQL = CONCAT(@scriptSQL,' where ', @nombreEstado, ' in (''S'',''P'',''V'',''E'') ' ,@filtros)
	--End

	print @scriptSQL 

	EXEC (@scriptSQL)

END