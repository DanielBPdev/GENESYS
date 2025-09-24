--liquibase formatted sql

--changeset jroa:01
--comment: Creacion table constante
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Constante' AND TABLE_SCHEMA = 'dbo')
CREATE TABLE Constante (
  cnsId int NOT NULL,
  cnsNombre varchar(100),
  cnsValor varchar(150),
  cnsDescripcion varchar(250) NOT NULL,
  CONSTRAINT PK_Constante_cnsId PRIMARY KEY (cnsId)
);