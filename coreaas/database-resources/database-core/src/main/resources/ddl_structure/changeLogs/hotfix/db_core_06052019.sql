--liquibase formatted sql

--changeset jocampo:01
--comment: 
CREATE TABLE NumeroRadicado (
	nraId BIGINT IDENTITY,
	nraAnio VARCHAR(4)
)

--changeset jocampo:02
--comment:
CREATE SEQUENCE Sec_NumeroRadicado START WITH 0 INCREMENT BY 1

--changeset jocampo:03
--comment:
INSERT INTO NumeroRadicado(nraAnio) VALUES('2019')

--changeset jocampo:04
--comment:
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(solId) + 1 FROM Solicitud
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('ALTER SEQUENCE Sec_NumeroRadicado RESTART WITH ' + @inicioSeq + ' INCREMENT BY 1')