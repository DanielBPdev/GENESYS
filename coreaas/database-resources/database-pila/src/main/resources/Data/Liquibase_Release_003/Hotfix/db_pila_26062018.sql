--liquibase formatted sql

--changeset alquintero:01
--comment:Actualizacion tabla TemAporte por cambio de enumerado
UPDATE TemAporte set temModalidadPlanilla = 'ELECTRONICA' where temModalidadPlanilla = 'UNICA';
