-- CREAR PARAMETRO PARA MEMBRETES DE CAJAS DE COMPENZACIÓN Y AGREGAR VARIABLE EN LOS COMUNICADOS
-- GLPI 64467

IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('MEMBRETE_PIE_DE_PAGINA_LA_CCF'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('MEMBRETE_PIE_DE_PAGINA_DE_LA_CCF',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al id en el ECM del membrete de píe de pagina de la caja de la caja de compensación actual. Utilizado en los comunicados'
           ,'FILE')
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('MEMBRETE_ENCABEZADO_DE_LA_CCF'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('MEMBRETE_ENCABEZADO_DE_LA_CCF',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al id en el ECM del membrete de encabezado de la caja de la caja de compensación actual. Utilizado en los comunicados'
           ,'FILE')

IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${membretePieDePaginaDeLaCcf}'))
 INSERT INTO [dbo].[VariableComunicado]
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
	    from PlantillaComunicado;

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
	   from PlantillaComunicado;