--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Creación de Parametrizacion Medio De Pago

INSERT ParametrizacionMedioDePago  (pmpMedioPago) VALUES ('Tarjeta multiservicio');
INSERT ParametrizacionMedioDePago  (pmpMedioPago) VALUES ('Transferencia bancaria');
INSERT ParametrizacionMedioDePago  (pmpMedioPago) VALUES ('Cheque');
INSERT ParametrizacionMedioDePago  (pmpMedioPago) VALUES ('Pago en ventanilla');
INSERT ParametrizacionMedioDePago  (pmpMedioPago) VALUES ('Pago Banco Agrario');
GO