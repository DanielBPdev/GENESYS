--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE CuentaCCF ADD cccBanco BIGINT NOT NULL;
ALTER TABLE CuentaCCF ADD CONSTRAINT FK_CuentaCCF_cccBanco FOREIGN KEY (cccBanco)
REFERENCES Banco(banId);

--changeset abaquero:02
--comment: Se agregan el nuevo tipo de aportante y de cotizante
UPDATE ValidatorParamValue SET value = '01,02,03,04,05,06,07,08,09,10,11' WHERE id IN (2110052, 2110789)
UPDATE ValidatorParamValue SET value = '1,2,3,4,12,15,16,18,19,20,21,22,23,30,31,32,33,34,35,36,40,42,43,44,45,47,51,52,53,54,55,56,57,58,59,60,61,62' WHERE id IN (2110211)