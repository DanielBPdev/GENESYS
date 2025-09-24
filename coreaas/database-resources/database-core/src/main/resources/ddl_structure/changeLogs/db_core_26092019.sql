--liquibase formatted sql

--changeset squintero:01
--comment:
insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
values ('MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PAGOS_MANUALES', '0.05', 0, 'VALOR_GLOBAL_NEGOCIO', 'Margen de tolerancia en la comparación del valor de mora para pagos manuales', 'VALOR_PESOS');

UPDATE PARAMETRO SET prmDescripcion = 'Margen de tolerancia en la comparación del valor de mora para PILA', prmTipoDato = 'VALOR_PESOS' WHERE prmNombre = 'MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA';

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
values ('ASOCIAR_APORTE_PILA', 'FALSE', 0, 'VALOR_GLOBAL_NEGOCIO', 'Permite activar una validación adicional en pila, para asociar el aporte que llega en una planilla a una entidad diferente a la que está diligenciada, bajo unas condiciones específicas.', 'BOOLEAN');