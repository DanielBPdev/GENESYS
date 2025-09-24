-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarReferenciasContables]
@tipoIntegracion varchar(200)
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON

	if(@tipoIntegracion = 'Fovis')
	begin
		select id,clase,rangoInicial,rangoFinal,valorActual,case estado when 'A' then 'Activo' when 'I' then 'Inactivo' when 'E' then 'Agotado' end estado,case comentario when 'PT' then 'Pagos y traslados' when 'AVR' then 'Asignados, vencidos y renuncias'  end comentario , case estado when 'I' then '' else 'disabled' end as act ,case estado when 'I' then 'display:block' else 'display:none' end as bot from sap.IC_Referencia where comentario in ('PT','AVR') 
		union 
		select max(id)+1,'','','','','','','' as act,'display:block' as bot from sap.IC_Referencia
	end
	if(@tipoIntegracion = 'Cuota')
	begin
		select id,clase,rangoInicial,rangoFinal,valorActual,case estado when 'A' then 'Activo' when 'I' then 'Inactivo' when 'E' then 'Agotado' end estado,case comentario when 'LP' then 'Liquidación y prescripción de subsidios' when 'CT' then 'Causación y traslado de pagos'  
		when 'AC' then 'Anulación CM Genesys doc.DP,SK' 
		end comentario, case estado when 'I' then '' else 'disabled' end as act ,case estado when 'I' then 'display:block' else 'display:none' end as bot from sap.IC_Referencia where comentario in ('LP','CT', 'AC') 
		union
		select max(id)+1,'','','','','','','' as act,'display:block' as bot from sap.IC_Referencia
	end
		if(@tipoIntegracion = 'Aportes')
	begin
		select id,clase,rangoInicial,rangoFinal,valorActual,case estado when 'A' then 'Activo' when 'I' then 'Inactivo' when 'E' then 'Agotado' end estado,case comentario when 'A' then 'Aportes' when 'M' then 'Morosidad' when 'P' then 'Prescripcion' end comentario ,case estado when 'I' then '' else 'disabled' end as act ,case estado when 'I' then 'display:block' else 'display:none' end as bot from sap.IC_Referencia where comentario in ('A','M','P') 
		union
		select max(id)+1,'','','','','','','' as act,'display:block' as bot from sap.IC_Referencia
		

	end
END