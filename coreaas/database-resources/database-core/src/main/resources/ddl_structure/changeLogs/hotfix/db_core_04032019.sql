--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TYPE TablaIdsType AS TABLE   
( perIdAfiliado BIGINT, perIdEmpresa BIGINT);  