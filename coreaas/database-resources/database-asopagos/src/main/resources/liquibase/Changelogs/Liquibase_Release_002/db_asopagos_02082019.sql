--liquibase formatted sql

--changeset jroa:01
--comment: Creacion de campo lerCajaCompensacionCodigo
ALTER TABLE [dbo].[ListaEspecialRevision] ADD lerCajaCompensacionCodigo VARCHAR (20)