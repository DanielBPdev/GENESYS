--liquibase formatted sql

--changeset abaquero:01
--comment: Se adicionan campos en la tabla staging.RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redOUTRegDetOriginal bigint;
ALTER TABLE staging.RegistroDetallado ADD redOUTEstadoRegistroRelacionAporte varchar(50);
ALTER TABLE staging.RegistroDetallado ADD redOUTEstadoEvaluacionAporte varchar(22);
