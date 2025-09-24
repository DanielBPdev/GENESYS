--liquibase formatted sql

--changeset flopez:01 
--comment:Se actualiza registro en la tabla Parametro
UPDATE Parametro SET prmValor = 'puntaje*(1+(1-SFVCalculado/SFVTope))' WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_8_PF';