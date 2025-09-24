--liquibase formatted sql

--changeset sbrinez:01
--comment:Se modifica tamaño de campo para la tabla Municipio
ALTER TABLE Municipio ALTER COLUMN munCodigo VARCHAR(5) NOT NULL;

--changeset jocorrea:02
--comment:Se actualiza registro en la tabla PlantillaComunicado 
UPDATE PlantillaComunicado SET pcoAsunto = 'Carta entidad pagadora', pcoEncabezado = 'Encabezado', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_ENT_PAG';
