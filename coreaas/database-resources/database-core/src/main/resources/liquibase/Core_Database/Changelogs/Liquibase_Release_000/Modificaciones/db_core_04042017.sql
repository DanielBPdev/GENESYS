--liquibase formatted sql

--changeset atoro:01
--comment: Eliminacion de registros en la tabla RequisitoCajaClasificacion
DELETE FROM RequisitoCajaClasificacion WHERE rtsTipotransaccion='CAMBIO_ENTIDAD_PAGADORA_INDEPENDIENTE_PRESENCIAL';
DELETE FROM RequisitoCajaClasificacion WHERE rtsTipotransaccion='CAMBIO_ENTIDAD_PAGADORA_INDEPENDIENTE_WEB';