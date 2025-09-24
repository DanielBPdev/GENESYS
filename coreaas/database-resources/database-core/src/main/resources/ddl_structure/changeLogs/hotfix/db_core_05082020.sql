--liquibase formatted sql

--changeset flopez:01
--comment: 
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(casId) + 1 FROM CuentaAdministradorSubsidio 
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_CuentaAdministradorSubsidio AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1');