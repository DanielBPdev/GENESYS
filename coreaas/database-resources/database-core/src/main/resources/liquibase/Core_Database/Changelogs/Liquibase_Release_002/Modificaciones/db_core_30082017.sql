--liquibase formatted sql

--changeset jocorrea:01
--comment: Creacion de la tabla ExpulsionSubsanada
CREATE TABLE ExpulsionSubsanada(
	exsId bigint IDENTITY(1,1) NOT NULL,
	exsExpulsionSubsanada bit NULL,
	exsFechaSubsanacionExpulsion date NULL,
	exsMotivoSubsanacionExpulsion varchar (200) NOT NULL,
	exsEmpleador bigint NULL,
	exsRolAfiliado bigint NULL,
	CONSTRAINT PK_ExpulsionSubsanada_exsId PRIMARY KEY (exsId)
);
ALTER TABLE ExpulsionSubsanada ADD CONSTRAINT FK_ExpulsionSubsanada_exsEmpleador FOREIGN KEY (exsEmpleador) REFERENCES Empleador (empId);
ALTER TABLE ExpulsionSubsanada ADD CONSTRAINT FK_ExpulsionSubsanada_exsRolAfiliado FOREIGN KEY (exsRolAfiliado) REFERENCES RolAfiliado (roaId);