--liquibase formatted sql

--changeset jusanchez:01
--comment: Insercion de registro en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122','91de0193-281a-4d14-a592-f105efe82eea_1.0',0,'VALOR_GLOBAL_NEGOCIO','Identificador de la plantilla de Excel para el cargue múltiple del proceso de afiliación masiva 1.2.2');

--changeset jroa:02
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_DEVOLUCION_APORTES_DEPLOYMENTID','com.asopagos.coreaas.bpm.devolucion_aportes:devolucion_aportes:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Identificador de versión de proceso BPM para Devolución de Aportes');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_CORRECCION_APORTES_DEPLOYMENTID','com.asopagos.coreaas.bpm.correcciones_aportes:correcciones_aportes:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Identificador de versión de proceso BPM para Corrección de Aportes');