--liquibase formatted sql

--changeset rarboleda:01
--comment: Se agregan campos tabla 
ALTER TABLE MedioTransferencia_aud ADD metCobroJudicial bit;
ALTER TABLE MedioTransferencia_aud ADD metInfoRelacionadaCobroJudicial varchar(50);