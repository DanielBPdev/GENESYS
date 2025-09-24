--liquibase formatted sql

--changeset jusanchez:01
--comment: Modificaciones en tablas de auditoria	

--Eliminación de la columna dgrDestinatario de la tabla GrupoPrioridad_aud
ALTER TABLE DestinatarioGrupo_aud DROP COLUMN dgrDestinatario;

--Eliminación de la tabla destinatario_aud
DROP TABLE Destinatario_aud;

-- Agregar la columna dgrRolContacto de la tabla DestinatarioGrupo_aud
ALTER TABLE DestinatarioGrupo_aud ADD dgrRolContacto varchar (60) NOT NULL;