--liquibase formatted sql

--changeset
--comment: Agrega campo carPrescribir a tabl cartera
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='Cartera' AND column_name='carPrescribir')
BEGIN
    ALTER TABLE Cartera ADD carPrescribir varchar(32);
END
IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'carPrescribir' AND TABLE_NAME = 'Cartera_aud' and TABLE_SCHEMA = 'aud'
)
BEGIN
    ALTER TABLE aud.cartera_aud ADD carPrescribir VARCHAR(32)
END
