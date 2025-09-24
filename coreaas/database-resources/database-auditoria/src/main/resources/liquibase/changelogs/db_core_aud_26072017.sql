--liquibase formatted sql

--changeset rarboleda:01
--comment:  Se adicionan campos a la tabla empleador
ALTER TABLE dbo.Empleador_aud ADD empCantIngresoBandejaCeroTrabajadores smallint;
ALTER TABLE dbo.Empleador_aud ADD empFechaRetiroTotalTrabajadores date;
ALTER TABLE dbo.Empleador_aud ADD empFechaGestionDesafiliacion date;
