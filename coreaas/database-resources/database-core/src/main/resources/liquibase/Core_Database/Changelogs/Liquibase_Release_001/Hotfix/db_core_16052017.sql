--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve mantis 0223726	
UPDATE Novedad SET novPuntoResolucion='BACK' WHERE novTipoTransaccion='CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB';