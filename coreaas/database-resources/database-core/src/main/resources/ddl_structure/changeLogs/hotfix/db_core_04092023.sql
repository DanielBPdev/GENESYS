IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('REGISTRO_APORTES_FUTURO'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('REGISTRO_APORTES_FUTURO',
           'NO'
           ,0
           ,'CAJA_COMPENSACION'
           ,'PERMITE REGISTRAR O RELACIONAR EL APORTE FUTURO, SI SE ELIGE LA OPCIÓN SI SE VA A REGISTRAR LOS APORTE Y SI TIENE MARCADO NO SE RELACIONAN'
           ,'BOOLEAN')


IF EXISTS (SELECT 1 FROM ParametrizacionGaps param WHERE  param.prgNombre IN ('APORTES_FUTUROS'))
    UPDATE ParametrizacionGaps set prgDescripcion = N'PERMITE REGISTRAR O RELACIONAR EL APORTE FUTURO, SI SE ELIGE LA OPCIÓN ACTIVO SE ACTIVA EL PARÁMETRO (Registrar_Aportes_Futuro)' WHERE prgNombre = 'APORTES_FUTUROS'
    UPDATE ParametrizacionGaps set prgNombre = 'APORTES_FUTURO' WHERE prgNombre = 'APORTES_FUTUROS'