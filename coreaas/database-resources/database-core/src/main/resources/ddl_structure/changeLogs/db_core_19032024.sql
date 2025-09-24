------------Cargo Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('CARGO_GERENTE_FINANCIERA'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('CARGO_GERENTE_FINANCIERA',
           'Gerente Financiera'
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene la gerente financiera de  fovis'
           ,'TEXT')
------------Firma Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_GERENTE_FINANCIERA'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('FIRMA_GERENTE_FINANCIERA',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al id en el ECM de la firma del responsable de la caja de compensación actual. Utilizado en comunicados'
           ,'FILE')
------------Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('GERENTE_FINANCIERA'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('GERENTE_FINANCIERA',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene la gerente financiera de  fovis'
           ,'TEXT')

------------Cargo Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('CARGO_GERENTE_COMERCIAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('CARGO_GERENTE_COMERCIAL',
           'Gerente Comercial'
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa'
           ,'TEXT')
------------Firma Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_GERENTE_COMERCIAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('FIRMA_GERENTE_COMERCIAL',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al id en el ECM de la firma del responsable de la caja de compensación actual. Utilizado en comunicados'
           ,'FILE')
------------Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('GERENTE_COMERCIAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('GERENTE_COMERCIAL',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa'
           ,'TEXT')

------------Cargo Secretaria General-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('CARGO_SECRETARIA_GENERAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('CARGO_SECRETARIA_GENERAL',
           'Secretaria General'
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene la secretaria general en cartera'
           ,'TEXT')
------------Firma Secretaria General-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_SECRETARIA_GENERAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('FIRMA_SECRETARIA_GENERAL',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al id en el ECM de la firma del responsable de la caja de compensación actual. Utilizado en comunicados'
           ,'FILE')
------------Secretaria General-----------------
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('SECRETARIA_GENERAL'))
 INSERT INTO [dbo].[Parametro]
           ([prmNombre]
           ,[prmValor]
           ,[prmCargaInicio]
           ,[prmSubCategoriaParametro]
           ,[prmDescripcion]
           ,[prmTipoDato])
     VALUES
           ('SECRETARIA_GENERAL',
           ''
           ,0
           ,'CAJA_COMPENSACION'
           ,'Corresponde al cargo que tiene la secretaria general en cartera'
           ,'TEXT')