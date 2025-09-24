--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de SedeCajaCompensacion
INSERT SedeCajaCompensacion (sccfNombre,sccfVirtual,sccCodigo) VALUES ('Sede Principal',0,'02');
INSERT SedeCajaCompensacion (sccfNombre,sccfVirtual,sccCodigo) VALUES ('Sede Virtual',1,'01');