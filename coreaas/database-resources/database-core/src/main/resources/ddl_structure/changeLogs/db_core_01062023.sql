IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${membreteEncabezadoDeLaCcf}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
select
       '${membreteEncabezadoDeLaCcf}'
       ,'Membrete encabezado de la Caja de Compensación'
       ,'Membrete encabezado de la CCF'
       ,pcoId 
       ,'MEMBRETE_ENCABEZADO_DE_LA_CCF'
       ,'CONSTANTE'
       ,''
	   from PlantillaComunicado
	   where pcoEtiqueta not like '%HU_PROCESO%';