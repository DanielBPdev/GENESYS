--liquibase formatted sql

--changeset atoro:01
--comment: Se agrega UK en la tabla PersonaDetalle
ALTER TABLE PersonaDetalle ADD CONSTRAINT UK_PersonaDetalle_pedPersona UNIQUE (pedPersona); 
