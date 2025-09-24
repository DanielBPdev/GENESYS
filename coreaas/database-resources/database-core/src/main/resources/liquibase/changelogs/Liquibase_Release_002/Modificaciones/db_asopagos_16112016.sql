--liquibase formatted sql

--changeset  mgiradlo:01
--comment: Creación campo CódigoPila en AFP

ALTER TABLE AFP ADD afpCodigoPila VARCHAR(10);

--changeset  mgiradlo:02
--comment: Eliminación campo código
ALTER TABLE AFP DROP COLUMN afpCodigo;

--changeset  mgiradlo:03
--comment: Adición de llave Unique
ALTER TABLE AFP ADD CONSTRAINT UK_AFP_afpCodigoPila UNIQUE(afpCodigoPila);


--changeset  mgiradlo:04
--comment: Eliminación del campo empEspecialRevision de empleador
ALTER TABLE Empleador DROP COLUMN empEspecialRevision;