-- CREAR PARAMETRO PARA CARGO, FIRMA Y NOMBRE DE NUEVAS CLAVES
-- GLPI 74927

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
IF NOT EXISTS (SELECT 1 FROM Parametro param WHERE  param.prmNombre IN ('FIRMA_GERENTE_FINANCIERA'))
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


------------Cargo Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${cargogerentefinanciera}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${cargogerentefinanciera}'
       ,'Corresponde al cargo que tiene la gerente financiera de  fovis'
       ,'Cargo Gerente Financiera'
       ,158
       ,'CARGO_GERENTE_FINANCIERA'
       ,'CONSTANTE'
       ,'')
------------Firma Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${firmagerentefinanciera}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${firmagerentefinanciera}'
       ,'Firma del gerente financiero'
       ,'Firma Gerente Financiera'
       ,158
       ,'FIRMA_GERENTE_FINANCIERA'
       ,'CONSTANTE'
       ,'')
------------Gerente Financiera-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${gerentefinanciera}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${gerentefinanciera}'
       ,'Gerente financiero'
       ,'Gerente Financiera'
       ,158
       ,'GERENTE_FINANCIERA'
       ,'CONSTANTE'
       ,'')

------------Cargo Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${cargogerentecomercial}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${cargogerentecomercial}'
       ,'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa'
       ,'Cargo Gerente Comercial'
       ,4
       ,'CARGO_GERENTE_COMERCIAL'
       ,'CONSTANTE'
       ,'')
------------Firma Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${firmagerentecomercial}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${firmagerentecomercial}'
       ,'Firma de gerente comercial'
       ,'Firma Gerente Comercial'
       ,4
       ,'FIRMA_GERENTE_COMERCIAL'
       ,'CONSTANTE'
       ,'')
------------Gerente Comercial-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${gerentecomercial}'))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${gerentecomercial}'
       ,'Gerente Comercial'
       ,'Gerente Comercial'
       ,4
       ,'GERENTE_COMERCIAL'
       ,'CONSTANTE'
       ,'')

------------Cargo Secretaria General Empresa-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${cargosecretariageneral}') AND param.vcoPlantillaComunicado IN (328))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${cargosecretariageneral}'
       ,'Corresponde al cargo que tiene la secretaria general en cartera'
       ,'Cargo Secretaria General'
       ,328
       ,'CARGO_SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')
------------Firma Secretaria General Empresa-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${firmasecretariageneral}') AND param.vcoPlantillaComunicado IN (328))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${firmasecretariageneral}'
       ,'Firma secretaria general'
       ,'Firma Secretaria General'
       ,328
       ,'FIRMA_SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')
------------Secretaria General Empresa-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${secretariageneral}') AND param.vcoPlantillaComunicado IN (328))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${secretariageneral}'
       ,'Secretaria General'
       ,'Secretaria General'
       ,328
       ,'SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')

------------Cargo Secretaria General Persona-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${cargosecretariageneral}') AND param.vcoPlantillaComunicado IN (330))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${cargosecretariageneral}'
       ,'Corresponde al cargo que tiene la secretaria general en cartera'
       ,'Cargo Secretaria General'
       ,330
       ,'CARGO_SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')
------------Firma Secretaria General Persona-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${firmasecretariageneral}') AND param.vcoPlantillaComunicado IN (330))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${firmasecretariageneral}'
       ,'Firma secretaria general'
       ,'Firma Secretaria General'
       ,330
       ,'FIRMA_SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')
------------Secretaria General Persona-----------------
IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  param.vcoClave IN ('${secretariageneral}') AND param.vcoPlantillaComunicado IN (330))
 INSERT INTO [dbo].[VariableComunicado]
       ([vcoClave]
       ,[vcoDescripcion]
       ,[vcoNombre]
       ,[vcoPlantillaComunicado]
       ,[vcoNombreConstante]
       ,[vcoTipoVariableComunicado]
       ,[vcoOrden])
VALUES
       ('${secretariageneral}'
       ,'Secretaria General'
       ,'Secretaria General'
       ,330
       ,'SECRETARIA_GENERAL'
       ,'CONSTANTE'
       ,'')