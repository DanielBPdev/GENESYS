CREATE OR ALTER PROCEDURE [sap].[MI_guardarreferencia]
@id varchar(200),@clase varchar(200),@rangoInicial varchar(200),@rangoFinal varchar(200),@valorActual varchar(200),@estado varchar(200),@comentario varchar(200),
@resultado varchar(200)OUTPUT,
@mensaje varchar(200)OUTPUT
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON

	IF NOT EXISTS (select * from sap.IC_Referencia where id = @id )
	begin
	INSERT INTO sap.IC_Referencia VALUES(@CLASE,@rangoInicial,@rangoFinal,@valorActual,
	case @estado when 'Activo' then 'A' when 'Inactivo' then 'I' when 'Agotado' then 'E' end,case @comentario when 'Pagos y traslados' then 'PT' when 'Asignados, vencidos y renuncias' then 'AVR'  
	when 'Liquidación y prescripción de subsidios' then 'LP' when 'Causación y traslado de pagos' then 'CT' when 'Anulación CM Genesys doc.DP,SK' then 'AC'
	when 'Aportes' then 'A' when 'Morosidad' then 'M' when 'Prescripcion' then 'P' end)
	end
	else
	begin
	update sap.IC_Referencia
	set clase =@clase, rangoInicial = @rangoInicial, rangoFinal = @rangoFinal, valorActual = @valorActual,
	estado = case @estado when 'Activo' then 'A' when 'Inactivo' then 'I' when 'Agotado' then 'E' end,
	comentario = case @comentario when 'Pagos y traslados' then 'PT' when 'Asignados, vencidos y renuncias' then 'AVR'  
	when 'Liquidación y prescripción de subsidios' then 'LP' when 'Causación y traslado de pagos' then 'CT'   when 'Anulación CM Genesys doc.DP,SK' then 'AC'
	when 'Aportes' then 'A' when 'Morosidad' then 'M' when 'Prescripcion' then 'P' end
	where id = @id 
	
	set @resultado = 'Ok';
	set @mensaje = 'Funcionando correctamente';
	end
	
END