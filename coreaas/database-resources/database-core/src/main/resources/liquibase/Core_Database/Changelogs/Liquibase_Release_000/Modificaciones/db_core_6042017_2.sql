--liquibase formatted sql

--changeset clmarin:04 failOnError:false
--comment: Actualizacion de registros de Persona para el campo perOcupacionProfesion y perMedioPago
UPDATE Persona SET perOcupacionProfesion = NULL
UPDATE Persona SET perMedioPago = NULL 

--changeset clmarin:05 failOnError:false
--comment: Eliminar Constraints de Persona
ALTER TABLE Persona DROP CONSTRAINT FK_Persona_perOcupacionProfesion;
ALTER TABLE Persona DROP CONSTRAINT FK_Persona_perMedioPago;

--changeset clmarin:06 failOnError:false
--comment: Eliminar Constraints de Persona
UPDATE Persona SET perGenero = NULL
UPDATE Persona SET perNivelEducativo = NULL 
UPDATE Persona SET perEstadoCivil = NULL

--changeset clmarin:07 
--comment: Eliminar check Constraints de Persona
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perGenero')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perGenero;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perNivelEducativo')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perNivelEducativo;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Persona_perEstadoCivil')) ALTER TABLE Persona DROP CONSTRAINT CK_Persona_perEstadoCivil;

--changeset clmarin:08 failOnError:false
--comment: eliminacion de constraints de la tabla persona que permita eliminar las siguiente colunmas
ALTER TABLE Persona DROP COLUMN perFechaNacimiento;
ALTER TABLE Persona DROP COLUMN perFechaExpedicionDocumento;
ALTER TABLE Persona DROP COLUMN perGenero;
ALTER TABLE Persona DROP COLUMN perOcupacionProfesion;
ALTER TABLE Persona DROP COLUMN perNivelEducativo;
ALTER TABLE Persona DROP COLUMN perCabezaHogar;
ALTER TABLE Persona DROP COLUMN perAutorizaUsoDatosPersonales;
ALTER TABLE Persona DROP COLUMN perResideSectorRural;
ALTER TABLE Persona DROP COLUMN perMedioPago;
ALTER TABLE Persona DROP COLUMN perEstadoCivil;
ALTER TABLE Persona DROP COLUMN perHabitaCasaPropia;
ALTER TABLE Persona DROP COLUMN perFallecido;

--changeset atoro:09 failOnError:false
--comment: Se agrega campo en la tabla PersonaDetalle
ALTER TABLE PersonaDetalle ADD pedFechaFallecido date NULL

