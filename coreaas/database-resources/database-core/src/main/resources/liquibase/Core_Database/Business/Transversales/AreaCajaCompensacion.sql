--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de AreaCajaCompensacion
INSERT AreaCajaCompensacion (arcNombre) VALUES ('Subsidio');
INSERT AreaCajaCompensacion (arcNombre) VALUES ('Afiliación');
INSERT AreaCajaCompensacion (arcNombre) VALUES ('Aporte');