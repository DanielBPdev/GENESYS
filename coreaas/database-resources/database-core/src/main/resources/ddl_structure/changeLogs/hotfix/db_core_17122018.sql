--liquibase formatted sql

--changeset clmarin:01
--comment: Creacion de constante
DELETE FROM Parametro WHERE prmNombre = '112_CORREGIR_INFORMACION_TIMER';