INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           (
		   'CUOTA_MONETARIA_APLICAR_LIQUIDACION2'
           ,'VERDADERO'
           ,0
           ,'CAJA_COMPENSACION'
           ,'Controla si en la liquidación se tienen en cuenta las novedades LMA, IGE, IRL, reportadas en PILA.'
           ,'BOOLEAN'
		   );