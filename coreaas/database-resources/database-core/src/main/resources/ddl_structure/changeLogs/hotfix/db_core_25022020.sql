--liquibase formatted sql

--changeset jocampo:01
--comment: Ajuste Mantis 259212
INSERT INTO Constante(cnsNombre,cnsValor,cnsDescripcion,cnsTipoDato) VALUES('FECHA_INICIO_CORTE_APORTES_PRIMERA_LIQUIDACION_SUBSIDIO', '2019-09-01 00:00:00', 'Fecha del inicio del coret de aportes en caso de tratarse de la primera liquiedación de subsidio monetario masiva.', 'TIME')