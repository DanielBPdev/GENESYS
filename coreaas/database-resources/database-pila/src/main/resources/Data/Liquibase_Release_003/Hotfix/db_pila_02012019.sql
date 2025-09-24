--liquibase formatted sql

--changeset abaquero:01
--comment: se elimina el constraint de PK para la tabla PilaErrorValidacionLog
ALTER TABLE PilaErrorValidacionLog DROP CONSTRAINT PK_PilaErrorValidacionLog_pevId

--changeset abaquero:02
--comment: Se agrega una columna temporal para la copia de los valores actuales de la llave a la que se le quietará el IDENTITY
ALTER TABLE PilaErrorValidacionLog ADD pevId_new BIGINT

--changeset abaquero:03
--comment: Se copian los valores de las llaves a la columna temporal
UPDATE PilaErrorValidacionLog SET pevId_new = pevId

--changeset abaquero:04
--comment: Se elima la columna de llave con IDENTITY
ALTER TABLE PilaErrorValidacionLog DROP COLUMN pevID

--changeset abaquero:05
--comment: Se crea nuevamente la columna de llave sin IDENTITY ni restricción de NOT NULL
ALTER TABLE PilaErrorValidacionLog ADD pevId BIGINT

--changeset abaquero:06
--comment: Se copian de nuevo los valores de las llaves al campo respectivo
UPDATE PilaErrorValidacionLog SET pevId = pevId_new

--changeset abaquero:07
--comment: Se agrega la restricción de NOT NULL al campo de llaves
ALTER TABLE PilaErrorValidacionLog ALTER COLUMN pevID BIGINT NOT NULL

--changeset abaquero:08
--comment: Se crea de nuevo el constraint como PK
ALTER TABLE PilaErrorValidacionLog ADD CONSTRAINT PK_PilaErrorValidacionLog_pevId PRIMARY KEY (pevId)

--changeset abaquero:09
--comment: Se elimina la columna temporal
ALTER TABLE PilaErrorValidacionLog DROP COLUMN pevID_new

--changeset abaquero:10
--comment: Se crea la secuencia para el manejo de los valores de PK
DECLARE @inicioSeq BIGINT
SELECT @inicioSeq = max(pevId) + 1 FROM PilaErrorValidacionLog
SELECT @inicioSeq = ISNULL(@inicioSeq, 1)
EXEC('CREATE SEQUENCE Sec_PilaErrorValidacionLog AS BIGINT START WITH ' + @inicioSeq + ' INCREMENT BY 1;')