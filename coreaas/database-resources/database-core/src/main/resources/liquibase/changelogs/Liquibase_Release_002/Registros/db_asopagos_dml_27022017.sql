--liquibase formatted sql

--changeset halzate:01 
--comment: cambio en valores Constantes
DELETE FROM Constante 
WHERE cnsNombre IN ('FECHA_29_DICIEMBRE_ANIO_2010', 'FECHA_31_DICIEMBRE_ANIO_2014', 'FECHA_10_JULIO_ANIO_2000');


INSERT INTO Constante (cnsNombre, cnsValor) values ('FECHA_INICIO_LEY_1429','1293580800000');
INSERT INTO Constante (cnsNombre, cnsValor) values ('FECHA_FIN_LEY_1429','1419984000000');
INSERT INTO Constante (cnsNombre, cnsValor) values ('FECHA_FIN_LEY_590','963187200000');