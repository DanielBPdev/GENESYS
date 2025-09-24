--liquibase formatted sql

--changeset dsuesca:01
--comment: 
INSERT INTO DestinatarioGrupo(dgrGrupoPrioridad, dgrRolContacto) VALUES((select gprid from grupoprioridad where gprNombre='USUARIO_FRONT'), 'USUARIO_FRONT');