--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Inserción de Constantes y parámetros


-- Constantes
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('CAJA_COMPENSACION_ID', '1');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('CAJA_COMPENSACION_MEDIO_PAGO_PREFERENTE', '');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('NOMBRE_CCF','Caja de Compensación Familiar de Caldas');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('LOGO_DE_LA_CCF','####');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('DEPARTAMENTO_CCF','Caldas');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('CIUDAD_CCF','Manizales');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('DIRECCION','Cra 25 Calle 50');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('TELEFONO','57(6)8783111');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('TIPO_ID_CCF','NIT');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('NUMERO_ID_CCF','890806490');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('CAJA_COMPENSACION_SITE','CONFAHBT');
INSERT [dbo].[Constante] ([cnsNombre], [cnsValor]) VALUES ('KEY','euNuWPuienBrq2t/lEnpCw3f3IpFuQRA');

-- Parámetros
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('BPMS_BUSINESS_CENTRAL_ENDPOINT', 'http://10.77.187.42/business-central');
--INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('BPMS_BUSINESS_CENTRAL_ENDPOINT', '$(BPMS_ENDPONIT)');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('BPMS_BUSINESS_CENTRAL_USERNAME', 'bpmsAdmin');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('BPMS_BUSINESS_CENTRAL_PASSWORD', 'Asdf1234$');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('BPMS_PROCESS_DEPLOYMENT_ID', 'com.asopagos.coreaas.bpm.afiliacion_empresas_presencial:Afiliacion_empresas_presencial:0.0.1-SNAPSHOT');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('SERVICIOS_ENDPOINT', 'http://localhost');
--INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('SERVICIOS_ENDPOINT', '$(SERVICES_ENDPOINT)');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO', 'NO');

INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('ECM_USERNAME', 'Admin');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('ECM_PASSWORD', 'Asdf1234$');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('ECM_HOST', 'http://localhost:8282/alfresco');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.from', null);
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.user', 'jocampo@heinsohn.com.co');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.sendpartial', 'TRUE');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.host', 'smtp.office365.com');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.port', '587');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.auth', 'TRUE');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.starttls.enable', 'TRUE');  
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('mail.smtp.password', 'D+ZrTqSAeL6YB9p5XPyZLg==');
INSERT [dbo].[Parametro] ([prmNombre], [prmValor]) VALUES ('USERS_SERVICES', 'http://10.77.187.38/UsuariosService/rest/usuarios/activos/cajaCompensacion?rol={nombreRol}&sede={idSedeCajaCompensacion}');
GO
