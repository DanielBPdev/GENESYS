--liquibase formatted sql

--changeset abaquero:01
--comment:Creación de tabla de control para actualización de estado de aportes por validación integral HU-211-480
CREATE TABLE dbo.TemAporteActualizado (
	taaId bigint identity NOT NULL,
	taaEstadoRegistroAporte varchar(50),
	taaRegistroDetallado bigint
)

ALTER TABLE dbo.TemAporteActualizado ADD CONSTRAINT PK_TemAporteActualizado PRIMARY KEY (taaId)

--changeset abaquero:02
--comment:Adición de la cantidad de registros tipo 2 en Registro General
ALTER TABLE staging.RegistroGeneral ADD regCantidadReg2 int