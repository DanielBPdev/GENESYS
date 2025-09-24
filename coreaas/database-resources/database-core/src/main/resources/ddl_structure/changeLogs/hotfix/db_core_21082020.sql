--liquibase formatted sql

--changeset squintero:01
--comment: creación de constante para controlar la Cantidad de planillas a procesar en cada ciclo de la HU 410 automática
INSERT INTO Constante
(cnsNombre, cnsValor, cnsDescripcion, cnsTipoDato)
VALUES('CANTIDAD_PLANILLAS_MANUALES_POR_CICLO', '10', 'Cantidad de planillas a procesar en cada ciclo de gestión de planillas manuales en pila mundo 2 (410 automática)', 'NUMBER');

--changeset squintero:02
--comment: update del valor para la constante CANTIDAD_PLANILLAS_MANUALES_POR_CICLO 
UPDATE Constante SET cnsValor = '25' WHERE cnsNombre = 'CANTIDAD_PLANILLAS_MANUALES_POR_CICLO'; 