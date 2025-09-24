--liquibase formatted sql

--changeset arocha:01
--comment: Se adicionan dos campos a la tabla staging.RegistroDetallado 
ALTER TABLE staging.RegistroDetallado ADD redUsuarioAprobadorAporte VARCHAR (50);
ALTER TABLE staging.RegistroDetallado ADD redNumeroOperacionAprobacion VARCHAR (12);
 