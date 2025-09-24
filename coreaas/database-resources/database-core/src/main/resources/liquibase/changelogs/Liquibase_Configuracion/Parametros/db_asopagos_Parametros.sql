--liquibase formatted sql
--changeset juagonzalez:01
--comment: se inserta Parametro para el salario minimo legal vigente
insert into Parametro (prmNombre,prmValor) values ('SMMLV', '689454'); 