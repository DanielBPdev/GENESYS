--liquibase formatted sql

--changeset dsuesca:01
--comment: Delete tabla RevisionEntidad por ajuste de sp que envía al pasado las revisiones
DELETE FROM RevisionEntidad WHERE reeEntityClassName = 'com.asopagos.entidades.ccf.personas.MedioDePago';
