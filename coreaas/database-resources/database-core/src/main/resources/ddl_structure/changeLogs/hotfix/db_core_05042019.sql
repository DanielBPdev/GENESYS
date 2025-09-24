--liquibase formatted sql

--changeset jroa:01
--comment: Se inserta parametros para reportes kpi
INSERT INTO Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MICROSOFT_CLIENT_ID', 'xxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx', 0, 'REPORTES', 'Client ID para obtener token de Microsoft');
INSERT INTO Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MICROSOFT_USERNAME', 'usuario@onmicrosoft.com', 0, 'REPORTES', 'Nombre de usuario de cuenta de microsoft');
INSERT INTO Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MICROSOFT_PASSWORD', 'password', 0, 'REPORTES', 'Clave para cuenta de Microsoft');