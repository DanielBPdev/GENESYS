--liquibase formatted sql

--changeset abaquero:01
--comment: Insercion de registros en la tabla Parametro y Constante
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('TIEMPO_MINIMO_PLANILLA','30',0,'VALOR_GLOBAL_NEGOCIO','Tiempo mínimo para la presentación de la planilla respecto a la fecha actual');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('REINTENTOS_PERSISTENCIA_CONTENIDO','2',0,'FILE_DEFINITION','Cantidad de reintentos automáticos para realizar la persistencia del contenido de los archivos');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA','0.05',0,'VALOR_GLOBAL_NEGOCIO','Margen de tolerancia en la comparación del valor de mora');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MULTIPLICADOR_VALOR_MINIMO_SALARIO_INTEGRAL','13',0,'VALOR_GLOBAL_NEGOCIO','Multiplicador para determinar el valor mínimo para un salario integral');

INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CAJA_COMPENSACION_MUNI_ID','001','Código DANE del municipio de la CCF');

--changeset abaquero:02 context:integracion
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CAJA_COMPENSACION_DEPTO_ID','15','Código DANE del departamento de la CCF');

--changeset abaquero:03 context:pruebas
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CAJA_COMPENSACION_DEPTO_ID','19','Código DANE del departamento de la CCF');

--changeset abaquero:04 context:asopagos_confa
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CAJA_COMPENSACION_DEPTO_ID','17','Código DANE del departamento de la CCF');

--changeset abaquero:05 context:asopagos_funcional
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CAJA_COMPENSACION_DEPTO_ID','17','Código DANE del departamento de la CCF');

--changeset mosanchez:06
--comment: Eliminacion registro de la tabla Constante
DELETE FROM Constante WHERE cnsNombre = 'CIUDAD_CCF' AND cnsId = 9;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'UK_Constante_cnsNombre')) ALTER TABLE Constante DROP CONSTRAINT UK_Constante_cnsNombre;
ALTER TABLE Constante ADD CONSTRAINT UK_Constante_cnsNombre UNIQUE (cnsNombre);
