--liquibase formatted sql

--changeset clmarin:01
--comment: Se agregan registro en la tabla GrupoPrioridad
INSERT INTO GrupoPrioridad (gprNombre) VALUES( 'GRUPO 9');
INSERT INTO DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) VALUES(9, 'AFILIADO_PRINCIPAL');