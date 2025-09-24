--liquibase formatted sql

--changeset jroa:01
--comment: Creacion de valores para constante
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'CAJA_COMPENSACION_SITE', N'ASOPAGOS', N'Nombre del SITE en el ECM para la caja de compensación')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'CAJA_COMPENSACION_ID', N'0', N'Es el id en la solución genesys para la caja de compensación actual')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'CAJA_COMPENSACION_CODIGO', N'ASOPAGOS', N'Es el código en pila para la caja de compensación actual')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'KEYCLOAK_ENDPOINT', N'http://35.196.134.25:8080/auth/realms/{realm}', N'Endpoint donde se encuentran disponibles los servicios de keycloak')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'IDM_SERVER_URL', N'http://35.196.134.25:8080/auth/', N'Endpoint donde se encuentran disponibles los servicios de keycloak')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'KEY', N'euNuWPuienBrq2t/lEnpCw3f3IpFuQRA', N'Corresponde  SALT para adicionar en las preguntas de los usuarios')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'IDM_CLIENT_WEB_CLIENT_SECRET', N'cd5f3ee7-39ad-4275-9e8c-cfeeae478280', N'Contraseña del cliente de keycloak usado para obtener tokens de acceso para los usuarios anonimos en el dominio "app_web"')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'SEC_INITIAL_CHARACTERS_PASSWORD', N'8', N'Número de caracteres  que tiene una contraseña generada por el sistema')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'IDM_INTEGRATION_WEB_DOMAIN_NAME', N'Asopagos', N'Dominio en keycloak donde se encuentran registrados los usuarios del sistema')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) VALUES (N'IDM_INTEGRATION_WEB_CLIENT_ID', N'realm-management', N'Nombre del cliente en el dominio de integración usado para generar tokens para los usuarios anonimos')


--changeset dsuesca:03
--comment: 

IF NOT EXISTS (
  SELECT * 
  FROM   sys.columns 
  WHERE  object_id = OBJECT_ID(N'[dbo].[Constante]') 
         AND name = 'cnsTipoDato'
)
ALTER TABLE Constante ADD cnsTipoDato varchar(17);

--changeset dsuesca:04
--comment: 

IF NOT EXISTS (
  SELECT * 
  FROM   sys.columns 
  WHERE  object_id = OBJECT_ID(N'[dbo].[Parametro]') 
         AND name = 'prmTipoDato'
)
ALTER TABLE Parametro ADD prmTipoDato varchar(17);