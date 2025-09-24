--liquibase formatted sql

--changeset squintero:01
--comment: creación de constante para controlar la cantidad de registros consultados en cada iteración del proceso automático de la HU410
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion, cnsTipoDato) VALUES ('CANTIDAD_PLANILLAS_MANUALES_POR_CICLO', '10', 'Cantidad de planillas a procesar en cada ciclo de gestión de planillas manuales en pila mundo 2 (410 automática)', 'NUMBER');
