-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosContables]
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
	declare @scriptSQL varchar(MAX)
	declare @tabla varchar(MAX)
	declare @filtros varchar(2000)
	declare @nombreEstado varchar(200) = 'estadoReg'
	declare @nombreidentificacion varchar(200) = 'numerodoc'

    IF @integracion = 'Fovis'
	begin
		set @tabla = 'select [consecutivo],DESCRIPCION AS [tipoMovimiento]
	  ,case [estadoReg] WHEN ''E'' THEN ''Error''
		WHEN ''V'' THEN ''Enviado''
		WHEN ''S'' THEN ''Satisfactorio''
		WHEN ''P'' THEN ''Pendiente''
		END
		as estado
	  ,[fecIng]
      ,[horaIng]
      ,[fechaDocumento]
      ,[fechaContabilizacion]
      ,[periodo]
      ,[referencia]
      ,[observacion]
      ,[moneda]
      ,[documentoContable]
      ,[sociedad]
      ,[ejercicio]
      ,[nroIntentos]
      ,[fecProceso]
      ,[horaProceso]
      ,[usuario]
	  ,case [Componente] when ''1'' then ''1-Recurso por apropiación'' 
						 when ''2'' then ''2-Recurso Segunda Prioridad''
						 when ''3'' then ''3-Recurso Tercera Prioridad''	
		end as Componente
       from sap.IC_FOVIS_Enc  LEFT JOIN SAP.MAESTRO_MOVIMEINTOS_CONTABLES ON tipoMovimiento=MOVIMIENTO';
	end;
	IF @integracion = 'Aportes'
	begin
		set @tabla = 'select [consecutivo],DESCRIPCION AS [tipoMovimiento]
	  ,case [estadoReg] WHEN ''E'' THEN ''Error''
		WHEN ''V'' THEN ''Enviado''
		WHEN ''S'' THEN ''Satisfactorio''
		WHEN ''P'' THEN ''Pendiente''
		END
		as estado
	  ,[fecIng]
      ,[horaIng]
      ,[fechaDocumento]
      ,[fechaContabilizacion]
      ,[periodo]
      ,[referencia]
      ,[observacion]
      ,[moneda]
      ,[documentoContable]
      ,[sociedad]
      ,[ejercicio]
      ,[nroIntentos]
      ,[fecProceso]
      ,[horaProceso]
      ,[usuario]
       from sap.IC_Aportes_Enc  LEFT JOIN SAP.MAESTRO_MOVIMEINTOS_CONTABLES ON tipoMovimiento=MOVIMIENTO';
	end;
	IF @integracion = 'Cuota'
	begin
		set @tabla = 'select [consecutivo],DESCRIPCION AS [tipoMovimiento]
		,case [estadoReg] WHEN ''E'' THEN ''Error''
		WHEN ''V'' THEN ''Enviado''
		WHEN ''S'' THEN ''Satisfactorio''
		WHEN ''P'' THEN ''Pendiente''
		END
		as estado
	  ,[fecIng]
      ,[horaIng]
      ,[fechaDocumento]
      ,[fechaContabilizacion]
      ,[periodo]
      ,[periodoAnioLiquidacion]
      ,[referencia]
      ,[observacion]
      ,[moneda]
      ,[documentoContable]
      ,[sociedad]
      ,[ejercicio]
      ,[nroIntentos]
      ,[fecProceso]
      ,[horaProceso]
      ,[usuario]
  FROM [sap].[IC_CM_Enc] LEFT JOIN SAP.MAESTRO_MOVIMEINTOS_CONTABLES ON tipoMovimiento=MOVIMIENTO';
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

	-- Operación no está dentro del alcance del GLPI 18/01/2022
	--if @operacion != ''
	--begin
	--	set @filtros = CONCAT (@filtros, ' and operacion =''',@operacion,'''') 
	--end;
	
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