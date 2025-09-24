--liquibase formatted sql

--changeset jzambrano:01
--comment: Actualizacion en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapObjetoValidacion ='HERMANO_HUERFANO_DE_PADRES' WHERE vapValidacion='VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE'
 