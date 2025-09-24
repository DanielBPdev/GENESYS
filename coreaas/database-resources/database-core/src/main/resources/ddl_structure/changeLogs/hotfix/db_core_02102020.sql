--liquibase formatted sql

--changeset squintero:01
--comment: adicion de campo hraMotivoDesafiliacion (varchar 50) en la tabla HistoricoRolAfiliado 
ALTER TABLE [dbo].[HistoricoRolAfiliado] ADD hraMotivoDesafiliacion VARCHAR(50) NULL;