--liquibase formatted sql

--changeset mamonroy:01
--comment:
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion,prmTipoDato)
VALUES ('TAMANO_PAGINADOR','1000',0,'VALOR_GLOBAL_TECNICO','Cantidad de registros por pagina para el tratamiento de consultas que requieren paginacion','NUMBER');

--changeset mamonroy:02
--comment:
ALTER TABLE BeneficioEmpleador ADD bemPerteneceDepartamento bit;
ALTER TABLE aud.BeneficioEmpleador_aud ADD bemPerteneceDepartamento bit;