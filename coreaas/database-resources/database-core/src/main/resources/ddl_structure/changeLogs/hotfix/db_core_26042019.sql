--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TABLE HistoricoSolicitudLiquidacionSubsidio (
	hslId bigint IDENTITY(1,1) NOT NULL,
	hslEstadoLiquidacion varchar(25) NULL,
	hslSolicitudLiquidacionSubsidio bigint NOT NULL,
	hslTimeStamp bigint,
	hslNombreUsuario varchar(255),
	hslObservacionesSegundoNivel varchar(250),
	hslRazonRechazoLiquidacion varchar(250),
	CONSTRAINT PK_HistoricoSolicitudLiquidacionSubsidio_hslId PRIMARY KEY (hslId)
);

--changeset jroa:01
--comment: 
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion], [cnsTipoDato]) VALUES ( 'IDM_CLIENT_WEB_CLIENT_ADMIN_SECRET', '422c847c-e202-400a-b233-8cd50f3a43d3', 'Contraseña del cliente administrador de keycloak usado para obtener tokens de acceso para los usuarios anonimos en el dominio "app_web"', 'PASSWORD')
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion], [cnsTipoDato]) VALUES ( 'IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ADMIN_ID', 'realm-management', 'Nombre del cliente administrador, en el dominio "app_web" usado para generar tokens anonimos', 'TEXT')

