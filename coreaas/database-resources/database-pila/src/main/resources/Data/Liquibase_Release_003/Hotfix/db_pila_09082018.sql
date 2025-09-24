--liquibase formatted sql

--changeset abaquero:01
--comment:Se añade el tipo contizante 59 en el parámetro de tipos de cotizante independientes
UPDATE staging.StagingParametros SET stpValorParametro='3,4,16,34,35,36,53,57,59,60,61' WHERE stpId=2