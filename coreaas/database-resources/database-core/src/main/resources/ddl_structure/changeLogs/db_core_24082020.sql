--liquibase formatted sql
--changeset silopez:01
--comment: actualizacion validador 

UPDATE ValidatorParamValue SET value = '00,01,02,03' WHERE id = 2111415;