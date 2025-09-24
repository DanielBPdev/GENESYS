if not exists (select * from VariableComunicado where vcoClave = '${membretePieDePaginaDeLaCcf}') INSERT INTO [dbo].[VariableComunicado]
    ([vcoClave]
    ,[vcoDescripcion]
    ,[vcoNombre]
    ,[vcoPlantillaComunicado]
    ,[vcoNombreConstante]
    ,[vcoTipoVariableComunicado]
    ,[vcoOrden])
select
    '${membretePieDePaginaDeLaCcf}'
    ,'Membrete pie de pagina de la Caja de Compensación'
    ,'Membrete pie de pagina de la CCF'
    ,pcoId
    ,'MEMBRETE_PIE_DE_PAGINA_DE_LA_CCF'
    ,'CONSTANTE'
    ,''
    from PlantillaComunicado
    where pcoAsunto  ='INCONSISTENCIAS RECAUDO APORTE';


if not exists (select * from VariableComunicado where vcoClave = '${membreteEncabezadoDeLaCcf}') INSERT INTO [dbo].[VariableComunicado]
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
    where pcoAsunto  ='INCONSISTENCIAS RECAUDO APORTE';