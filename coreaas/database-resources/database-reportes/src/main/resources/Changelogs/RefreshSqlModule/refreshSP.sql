--liquibase formatted sql

--changeset dsuesca:01
--comment: Actualización metadata de sp USP_REP_MERGE_RegistroDetallado
EXEC sys.sp_refreshsqlmodule 'USP_REP_MERGE_RegistroDetallado';