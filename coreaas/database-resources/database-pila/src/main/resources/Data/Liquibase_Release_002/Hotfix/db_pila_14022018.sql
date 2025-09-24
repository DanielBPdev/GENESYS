--liquibase formatted sql

--changeset abaquero:01
--comment:Adicion de campo en la tabla TemAportante 
-- 0230846
ALTER TABLE TemAportante ADD tapEsEmpleadorReintegrable bit;
