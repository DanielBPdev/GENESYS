--liquibase formatted sql

--changeset ecastano:01
--comment:Se adicionan campos en la tabla ProyectoSolucionVivienda_aud
ALTER TABLE ProyectoSolucionVivienda_aud ADD psvDisponeCuentaBancaria bit NULL;
ALTER TABLE ProyectoSolucionVivienda_aud ADD psvComparteCuentaOferente bit NULL;
