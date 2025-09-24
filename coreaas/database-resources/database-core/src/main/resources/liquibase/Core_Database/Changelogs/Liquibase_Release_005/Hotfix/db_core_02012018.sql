--liquibase formatted sql

--changeset abaquero:01
--comment: Se actualiza la parametrización de lectura de los archivos PILA
UPDATE lineloadcatalog SET name = 'Detalle de Dependiente o Independiente - Información Aportante' WHERE id = 3;
UPDATE lineloadcatalog SET name = 'Detalle de Dependiente o Independiente - Detalle Cotizante' WHERE id = 4;
UPDATE lineloadcatalog SET name = 'Detalle de Dependiente o Independiente - Subtotal aportes planilla' WHERE id = 5;
UPDATE lineloadcatalog SET name = 'Detalle de Dependiente o Independiente - Subtotal morosidad planilla' WHERE id = 6;
UPDATE lineloadcatalog SET name = 'Detalle de Dependiente o Independiente - Valor total planilla' WHERE id = 7;
UPDATE lineloadcatalog SET name = 'Detalle de Pensionado - Información Aportante' WHERE id = 8;
UPDATE lineloadcatalog SET name = 'Detalle de Pensionado - Detalle Cotizante' WHERE id = 9;
UPDATE lineloadcatalog SET name = 'Detalle de Pensionado - Valor total planilla' WHERE id = 10;