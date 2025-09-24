--liquibase formatted sql
--changeset rcastillo:01
--comment: Se cambia el tipo de dato de la tabla, para permitir mas caracteres, por guardar información tipo html
alter table dbo.plantillacomunicado alter column pcoEncabezado nvarchar(max);
alter table dbo.plantillacomunicado alter column pcoCuerpo nvarchar(max);
alter table dbo.plantillacomunicado alter column pcoPie nvarchar(max);