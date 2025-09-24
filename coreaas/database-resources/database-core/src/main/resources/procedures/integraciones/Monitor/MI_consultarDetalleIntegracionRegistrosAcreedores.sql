-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosAcreedores]
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@identificacion varchar(200), 
@estado varchar(200), 
@codigoSap varchar(200), 
@operacion varchar(200), 
@codigoGenesys varchar(200), 
@procesoOrigen varchar(200), 
@observacionesContiene varchar(max)
AS
BEGIN

	declare @scriptSQL varchar(max)
	declare @tabla varchar(200) = 'Acreedores'
	declare @filtros varchar(2000)
	declare @nombreEstado varchar(200) = 'estadoreg'
	declare @nombreidentificacion varchar(200) = 'nroDocumento'
	
	set @scriptSQL = CONCAT('select consecutivo as consecutivoInicial, CASE ',@nombreEstado,' 
			WHEN ''E'' THEN ''Error''
		WHEN ''V'' THEN ''Enviado''
		WHEN ''S'' THEN ''Satisfactorio''
		WHEN ''P'' THEN ''Pendiente''
		END	as estadoInicial,[consecutivo],[fecIng],[horaIng]
		,case [operacion] when ''R'' then ''Retiro'' when ''I'' then ''Ingreso'' when ''M'' then ''Modificado'' end as operacion,[codigoSap]
		,[codigoGenesys],[tipo],[procesoOrigen],[sociedad],[grupoCuenta],[tratamiento],[nroDocumento],[tipoDocumento],[nombre1],[nombre2],[nombre3]
		,[nombre4],[direccion],[municipio],[pais],[departamento],[telefono],[celular],[fax],[email],[email2],[claseImpuesto],[personaFisica]
		,[clavePaisBanco],[claveBanco],[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[cuentaAsociada]
      ,[claveClasificacion],[grupoTesoreria],[nroPersonal],[condicionPago],[grupoTolerancia],[verificacionFacturaDoble],[viasPago],[extractoCuenta]
      ,[actividadEconomica],[paisRetencion],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[observacion],[usuario] from sap.',@tabla)

	if @fechaInicial != '' and @fechaFinal != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' fecing between ''',@fechaInicial,''' and ''', @fechaFinal ,'''') 
	end;

	if @identificacion != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, @nombreidentificacion,' =''',@identificacion,'''') 
	end

	if @estado != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, @nombreEstado, ' =''',@estado,'''') 
	end;

	if @codigoSap != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' codigosap =''',@codigoSap,'''') 
	end;
	
	if @operacion != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' operacion =''',@operacion,'''') 
	end;

	if @codigoGenesys != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' codigoGenesys =''',@codigoGenesys,'''') 
	end;

	if @procesoOrigen != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' procesoOrigen =''',@procesoOrigen,'''') 
	end;

	if @observacionesContiene != ''
	begin
		if @filtros != ''
		begin
			set @filtros =CONCAT (@filtros , ' and ');
		end
		set @filtros = CONCAT (@filtros, ' REPLACE(observacion,'','','''') like ''%',@observacionesContiene,'%''') 
	end;

	
	if @filtros != ''
	begin
		set @scriptSQL = CONCAT(@scriptSQL,' where ',@filtros)
	End

	print @scriptSQL 

	EXEC (@scriptSQL)

END