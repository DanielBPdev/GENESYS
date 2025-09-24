--liquibase formatted sql

--changeset abaquero:01
--comment:Adición de campo temPresentaNovedad tabla TemAporte 
ALTER TABLE TemAporte ADD temPresentaNovedad bit;

--changeset squintero:02
--comment:Adición de campo temPresentaNovedad tabla TemAporte 
ALTER TABLE TemAporteProcesado ADD tprPresentaNovedades bit;