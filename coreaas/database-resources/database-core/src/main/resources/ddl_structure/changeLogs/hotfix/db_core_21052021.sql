--liquibase formatted sql

--changeset jocampo:01
--comment: 
UPDATE prm
SET prmValor = '3600',
    prmNombre = 'PILA_SEGUNDOS_REINTENTO_PROCESAMIENTO'
FROM Parametro prm
WHERE prmNombre = 'PILA_DIAS_REINTENTO_PROCESAMIENTO'