--liquibase formatted sql

--changeset flopez:01
--comment: Ajuste eliminar campos repetidos tabla MediosPagoCCF
DELETE FROM MediosPagoCCF WHERE mpcId IN(
SELECT MAX(mpcId) FROM MediosPagoCCF
WHERE mpcMedioPago IN (
SELECT  mpcMedioPago FROM MediosPagoCCF
GROUP BY mpcMedioPago
HAVING count (*) = 2)
GROUP BY mpcMedioPago);

--changeset flopez:02
--comment: Se agrega constraint para no permitir valores duplicados en la columna mpcMedioPago
ALTER TABLE MediosPagoCCF ADD CONSTRAINT UK_MediosPagoCCF_mpcMedioPago UNIQUE (mpcMedioPago);