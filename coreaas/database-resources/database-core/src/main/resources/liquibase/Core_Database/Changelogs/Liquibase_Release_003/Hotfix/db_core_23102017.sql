--liquibase formatted sql

--changeset ogiral:01
--comment:Se modifican registro en la tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsTipoRequisito = 'COMPORTAMIENTO_TIPO_A' WHERE rtsTipoRequisito = 'SECUNDARIO_AFILIACION';

--changeset jzambrano:02
--comment:Se eliminan registros con la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion='VALIDACION_PERSONA_MISMO_EMPLEADOR' AND vapBloque='122-369-1';