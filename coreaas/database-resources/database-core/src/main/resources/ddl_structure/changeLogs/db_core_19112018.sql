--liquibase formatted sql

--changeset dsuesca:01
--comment: Creacion tabla DashboardConsulta
CREATE TABLE DashboardConsulta( 
	dbcId bigint NOT NULL IDENTITY(1,1),
	dbcPermiso varchar(11) NOT NULL,
	dbcGroupsID varchar(40) NOT NULL,
	dbcReportsID varchar(40) NOT NULL,
	dbcLabelUsuario varchar(100) NOT NULL,
	dbcDescripcion varchar(255) NOT NULL,	
	CONSTRAINT PK_DashboardConsulta_dbcId PRIMARY KEY (dbcId),
	CONSTRAINT UK_DashboardConsulta_dbcPermiso UNIQUE (dbcPermiso),
	CONSTRAINT UK_DashboardConsulta_dbcGroupsID_dbcReportsID UNIQUE (dbcGroupsID,dbcReportsID)
);

--changeset dsuesca:02
--comment: Creacion tabla DashboardConsulta
INSERT DashboardConsulta (dbcPermiso,dbcGroupsID,dbcReportsID,dbcLabelUsuario,dbcDescripcion) VALUES
('CON_EMP','Grupo1','Reporte1','Consultas empleador','Consultas empleador'),
('CON_PER','Grupo2','Reporte1','Consultas persona','Consultas persona'),
('CON_RECAU','Grupo3','Reporte1','Consultas recaudos','Consultas recaudos'),
('CON_CA_APO','Grupo4','Reporte1','Consultas cartera de aportes','Consultas cartera de aportes'),
('CON_SUB_MON','Grupo5','Reporte1','Consultas subsidio monetario','Consultas subsidio monetario'),
('CON_FOVIS','Grupo6','Reporte1','Consultas FOVIS','Consultas FOVIS'),
('CON_REPAGO','Grupo7','Reporte1','Consultas reservadas medios de pago','Consultas reservadas medios de pago'),
('CON_GENER','Grupo8','Reporte1','Consultas generales','Consultas generales'),
('CON_RES_CL','Grupo9','Reporte1','Consultas servicio al cliente','Consultas servicio al cliente')


