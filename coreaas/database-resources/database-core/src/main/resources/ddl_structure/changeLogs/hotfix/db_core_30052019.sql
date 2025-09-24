--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('GENERAR_FORMULARIO_PRESENCIAL', 'TRUE', 1, 'VALOR_GLOBAL_NEGOCIO', 'Indica si se debe generar el pdf con el detalle de la solcitud, cuando esta es del tipo presencial', 'BOOLEAN');
