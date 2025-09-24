--liquibase formatted sql

--changeset  eamaya:01 
--comment: cambio tipo de dato solFechaCreacion a datetime2
SET ANSI_PADDING ON 
ALTER TABLE SOLICITUD ALTER COLUMN solFechaCreacion datetime2;
SET ANSI_PADDING OFF
--changeset  abaquero:02
--comment: Adición campo pipEnLista en la tabla PilaIndicePlanilla de tipo bit
ALTER TABLE PilaIndicePlanilla ADD pipEnLista bit;

--changeset  mgiraldo:03
--comment: tamaño campo roaTipoContrato varchar(20)
alter table ROLAFILIADO alter column roaTipoContrato VARCHAR(20);


