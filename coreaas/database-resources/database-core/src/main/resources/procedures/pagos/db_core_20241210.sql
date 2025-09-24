--liquibase formatted sql
--changeset rcastillo

if not exists (select * from information_schema.tables where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'transaccionTP')
	begin
		create table dbo.transaccionTP(idAdministrador bigint,id bigint,unique nonclustered (idAdministrador asc))
	end

if not exists (select * from information_schema.tables where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'transaccionTP2')
	begin
		create table transaccionTP2(idAdministrador bigint not null,id bigint,operacion varchar(1),unique nonclustered(idAdministrador asc))
	end

if not exists (select * from information_schema.tables where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'transaccionTP_log')
	begin
		create table dbo.transaccionTP_log(id bigint identity(1,1) not null,idAdministrador bigint,fecha datetime,datosEntrada varchar(500),error varchar(700))
	end