--liquibase formatted sql

--changeset halzate:01 stripComments:false 
ALTER TABLE DocumentoEntidadPagadora ADD dpgNombreDocumento VARCHAR(60);
--changeset halzate:03 stripComments:false 
ALTER TABLE DocumentoEntidadPagadora ADD dpgVersionDocumento smallint;

--changeset jocampo:04 stripComments:false 
ALTER TABLE SolicitudAfiliacionPersona ADD perObservacion VARCHAR (500);



