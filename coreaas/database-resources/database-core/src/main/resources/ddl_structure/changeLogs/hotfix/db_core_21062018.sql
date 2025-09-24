--liquibase formatted sql

--changeset jvelandia:01
--comment: Insersion tabla DestinatarioComunicado
INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('GESTION_PREVENTIVA_CARTERA','REC_PLZ_LMT_PAG_PER');

--changeset jocorrea:02
--comment: Insersion tabla DestinatarioComunicado
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_MADRE' AND vapBloque = 'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD_MADRE';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_HIJO_ADOPTIVO' AND vapBloque = 'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD_ADOPTIVO';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_PADRE' AND vapBloque = 'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD_PADRE';



