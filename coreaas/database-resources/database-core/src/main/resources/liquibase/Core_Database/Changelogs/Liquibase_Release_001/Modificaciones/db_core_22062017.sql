--liquibase formatted sql

--changeset anbuitrago:01 
--comment: Se adiciona el campo picComentarios 
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla ADD picComentarios varchar(500) NULL;

--changeset rarboleda:02
--comment: Creacion de la tabla Banco
CREATE TABLE dbo.Banco (
	banId bigint NOT NULL IDENTITY,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit,
	CONSTRAINT PK_Banco_banId PRIMARY KEY (banId)
); 