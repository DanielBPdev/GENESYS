--liquibase formatted sql

--changeset clmarin:01
--comment: Insercion en la tabla Constante
INSERT Constante (cnsNombre, cnsValor) VALUES ('TIEMPO_EXPIRACION_ENLACE', '1d');