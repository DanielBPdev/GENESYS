--liquibase formatted sql

--changeset  sbriñez:01
--comment: Eliminación entidades
drop table ItemChequeoAfiliEmpleador; 
drop table RequisitoAfiliaciEmpleador;  

--changeset  alopez:02
--comment: Cambio de tipo de dato
ALTER TABLE Persona ALTER COLUMN perDigitoVerificacion SMALLINT;

