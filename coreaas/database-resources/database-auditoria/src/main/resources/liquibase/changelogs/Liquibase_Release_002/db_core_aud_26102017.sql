--liquibase formatted sql

--changeset dasanchez:01
--comment: Eliminacion y creacion de la tabla ParametrizacionTablaAuditable
DROP TABLE ParametrizacionTablaAuditable;
-- Creación de tabla ParametrizacionTablaAuditable   
CREATE TABLE ParametrizacionTablaAuditable(
	ptaId int IDENTITY(1,1) NOT NULL,
	ptaActualizar bit NULL,
	ptaConsultar bit NULL,
	ptaCrear bit NULL,
	ptaEntityClassName varchar(255) NULL,
	ptaNombreTabla varchar(255) NULL,
CONSTRAINT PK_ParametrizacionTablaAuditable_ptaId PRIMARY KEY (ptaId)
);
