--liquibase formatted sql

--changeset hhernandez:01
--comment: Se adicionan campos a la tabla AporteDetallado_aud
ALTER TABLE AporteDetallado_aud ADD apdCodSucursal VARCHAR(10);
ALTER TABLE AporteDetallado_aud ADD apdNomSucursal VARCHAR(100);

--changeset hhernandez:02
--comment: Se elimina la tabla ActualizacionDatosEmpleador 
DROP TABLE ActualizacionDatosEmpleador_aud;