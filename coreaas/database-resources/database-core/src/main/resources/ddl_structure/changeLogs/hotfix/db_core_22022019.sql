--liquibase formatted sql

--changeset abaquero:01
--comment: se eliminan los constraint para las tablas Cartera y CarteraDependiente
ALTER TABLE CarteraDependiente DROP CONSTRAINT FK_CarteraDependiente_cadCartera
ALTER TABLE DocumentoCartera DROP CONSTRAINT FK_DocumentoCartera_dcaCartera
ALTER TABLE MotivoNoGestionCobro DROP CONSTRAINT FK_MotivoNoGestionCobro_mgcCartera
ALTER TABLE NotificacionPersonal DROP CONSTRAINT FK_NotificacionPersonal_ntpCartera
ALTER TABLE Cartera DROP CONSTRAINT PK_Cartera_carId
ALTER TABLE CarteraDependiente DROP CONSTRAINT PK_CarteraDependiente_cadId

--changeset abaquero:02
--comment: Se agrega una columna temporal para la copia de los valores actuales de la llave a la que se le quitará el IDENTITY
ALTER TABLE Cartera ADD carId_new BIGINT
ALTER TABLE CarteraDependiente ADD cadId_new BIGINT

--changeset abaquero:03
--comment: Se copian los valores de las llaves a la columna temporal
UPDATE Cartera SET carId_new = carId
UPDATE CarteraDependiente SET cadId_new = cadId

--changeset abaquero:04
--comment: Se elima la columna de llave con IDENTITY
ALTER TABLE Cartera DROP COLUMN carId
ALTER TABLE CarteraDependiente DROP COLUMN cadId

--changeset abaquero:05
--comment: Se crean nuevamente las columnas de llave sin IDENTITY ni restricción de NOT NULL
ALTER TABLE Cartera ADD carId BIGINT
ALTER TABLE CarteraDependiente ADD cadId BIGINT

--changeset abaquero:06
--comment: Se copian de nuevo los valores de las llaves al campo respectivo
UPDATE Cartera SET carId = carId_new
UPDATE CarteraDependiente SET cadId = cadId_new

--changeset abaquero:07
--comment: Se agrega la restricción de NOT NULL a los campos de llave
ALTER TABLE Cartera ALTER COLUMN carId BIGINT NOT NULL
ALTER TABLE CarteraDependiente ALTER COLUMN cadId BIGINT NOT NULL

--changeset abaquero:08
--comment: Se creas de nuevo los constraint
ALTER TABLE Cartera ADD CONSTRAINT PK_Cartera_carId PRIMARY KEY (carId)
ALTER TABLE CarteraDependiente ADD CONSTRAINT PK_CarteraDependiente_cadId PRIMARY KEY (cadId)
ALTER TABLE CarteraDependiente ADD CONSTRAINT FK_CarteraDependiente_cadCartera FOREIGN KEY(cadCartera) REFERENCES Cartera (carId)
ALTER TABLE DocumentoCartera ADD CONSTRAINT FK_DocumentoCartera_dcaCartera FOREIGN KEY(dcaCartera) REFERENCES Cartera (carId)
ALTER TABLE MotivoNoGestionCobro ADD CONSTRAINT FK_MotivoNoGestionCobro_mgcCartera FOREIGN KEY(mgcCartera) REFERENCES Cartera (carId)
ALTER TABLE NotificacionPersonal ADD CONSTRAINT FK_NotificacionPersonal_ntpCartera FOREIGN KEY(ntpCartera) REFERENCES Cartera (carId)

--changeset abaquero:09
--comment: Se eliminan las columnas temporales
ALTER TABLE Cartera DROP COLUMN carId_new
ALTER TABLE CarteraDependiente DROP COLUMN cadId_new

--changeset abaquero:10
--comment: Se crea la secuencia para el manejo de los valores de PK
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(carId) + 1 FROM Cartera
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_Cartera AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1')
SELECT @inicioSeq = max(cadId) + 1 FROM CarteraDependiente
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_CarteraDependiente AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1')