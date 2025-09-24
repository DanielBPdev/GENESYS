--liquibase formatted sql

--changeset kvides:21012025
if not exists (select 1 from sys.types where is_table_type = 1 and name = 'TablaAfiliadoIdType')

create type [dbo].[TablaAfiliadoIdType] as table([afiId] [bigint] null)