--liquibase formatted sql

--changeset mosanchez:01 
--comment: Se eliminan registros de la tabla parametro
DELETE FROM Parametro WHERE prmNombre IN('FECHA_INICIO_LEY_1429','FECHA_FIN_LEY_1429','FECHA_FIN_LEY_590');