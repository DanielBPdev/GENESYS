--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de código de operador de información en la tablad e paso de valores de Pila M1
ALTER TABLE PilaPasoValores ADD ppvCodOperadorInf VARCHAR(2)

--changeset abaquero:02
--comment: Adición de de campo de agrupamiento de registros tipo 2 en planillas de corrección
ALTER TABLE staging.RegistroDetallado ADD redOUTGrupoAC INT