--liquibase formatted sql

--changeset jusanchez:01
--comment: Cambio de nombre de la tabla CargueMultiple
EXEC sp_rename 'dbo.CargueAfiliacionMultiple', 'CargueMultiple';