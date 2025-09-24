--liquibase formatted sql

--changeset abaquero:01
--comment:Se agrega marca de archivo en proceso para excluir de bandeja de gestión 399
UPDATE TemAporte set temModalidadPlanilla = 'ELECTRONICA' where temModalidadPlanilla = 'UNICA';