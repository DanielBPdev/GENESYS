--liquibase formatted sql

--changeset squintero:01
--comment: 
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion, cnsTipoDato)
VALUES ('ANIBOL_CCF_ID', '1', 'Identificador de la caja frente a ANIBOL', 'NUMBER');
