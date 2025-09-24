--liquibase formatted sql

--changeset squintero:01
--comment: 
UPDATE TipoInfraestructura 
SET tifMedidaCapacidad = 'PERSONAS' 
WHERE tifNombre='Infraestructura para programas o convenios especiales';