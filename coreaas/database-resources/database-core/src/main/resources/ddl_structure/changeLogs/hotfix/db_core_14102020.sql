--liquibase formatted sql

--changeset flopez:01
--comment:Agrega campo pofOferente a PostulacionFOVIS 
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='PostulacionFOVIS' AND column_name='pofOferente')
ALTER TABLE PostulacionFOVIS ADD pofOferente BIGINT NULL;

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_PostulacionFOVIS_pofOferente')
ALTER TABLE PostulacionFOVIS ADD CONSTRAINT FK_PostulacionFOVIS_pofOferente FOREIGN KEY(pofOferente) REFERENCES Oferente (ofeId);

IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='PostulacionFOVIS_aud' AND column_name='pofOferente')
ALTER TABLE aud.PostulacionFOVIS_aud ADD pofOferente BIGINT NULL;