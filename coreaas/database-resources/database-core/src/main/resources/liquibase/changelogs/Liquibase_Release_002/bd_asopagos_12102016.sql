--liquibase formatted sql

--changeset mgiraldo:01 stripComments:false  
alter table requisito drop column reqTipoTransaccion;

--changeset mgiraldo:02 stripComments:false  
CREATE TABLE RequisitoTipoTransaccion(
	rttId [bigint] IDENTITY(1,1) NOT NULL,
	rttRequisito [bigint] NULL,
	rttTipoTransaccion varchar(100) NULL,
	
	
	CONSTRAINT PK_RequisitoTipoTransaccion PRIMARY KEY (rttId) 
);

ALTER TABLE RequisitoTipoTransaccion ADD CONSTRAINT FK_RequisitoTipoTransaccion_rttRequisito FOREIGN KEY (rttRequisito) REFERENCES Requisito (reqId);

--changeset atoro:03 stripComments:false  
ALTER TABLE HistoriaResultadoValidacion ALTER COLUMN hrvValidacion VARCHAR(60); 

--changeset jcamrgo:04 stripComments:false  
DROP TABLE  InformacionContacto;

ALTER TABLE RolContactoEmpleador ADD rceCargo VARCHAR(100);
ALTER TABLE RolContactoEmpleador ADD rcetoken VARBINARY(MAX);
ALTER TABLE RolContactoEmpleador ADD rceCorreoEnviado VARCHAR(100);

