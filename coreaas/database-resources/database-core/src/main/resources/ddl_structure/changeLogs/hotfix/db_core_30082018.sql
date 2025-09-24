--liquibase formatted sql

--changeset jocorrea:01
--comment: actualizacion tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsTipoRequisito = 'ESTANDAR' WHERE rtsTipoRequisito IS NULL;