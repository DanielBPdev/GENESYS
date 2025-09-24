--liquibase formatted sql

--changeset ecastano:01
--comment: Se modifica tamaño de campos en la tabla PostulacionFOVIS
ALTER TABLE PostulacionFOVIS ALTER COLUMN pofEstadoHogar VARCHAR(58);

--changeset ecastano:02
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MESES_VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA',12,0,'VALOR_GLOBAL_NEGOCIO', 'Numero de meses a sumar para el calculo de la fecha de vencimiento de la asignacion sin prorroga');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MESES_VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA',24,0,'VALOR_GLOBAL_NEGOCIO', 'Numero de meses a sumar para el calculo de la fecha de vencimiento de la asignacion sin segunda prorroga');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('MESES_VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA',36,0,'VALOR_GLOBAL_NEGOCIO', 'Numero de meses a sumar para el calculo de la fecha de vencimiento de la asignacion con segunda prorroga caducada');

--changeset borozco:03
--comment:Se modifican tamaño de campos en las tablas SolicitudDesafiliacion y DocumentoSoporte
ALTER TABLE SolicitudDesafiliacion ALTER COLUMN sodComentarioCoordinador VARCHAR(500);
ALTER TABLE DocumentoSoporte ALTER COLUMN dosTipoDocumento VARCHAR(23);

--changeset flopez:04
--comment:Se adicionan campos a la tabla PostulacionFovis
ALTER TABLE PostulacionFOVIS ADD pofMotivoDesistimiento VARCHAR(29) NULL;
ALTER TABLE PostulacionFOVIS ADD pofMotivoRechazo VARCHAR(51) NULL;
ALTER TABLE PostulacionFOVIS ADD pofMotivoHabilitacion VARCHAR(38) NULL;
ALTER TABLE PostulacionFOVIS ADD pofRestituidoConSancion BIT NULL;
ALTER TABLE PostulacionFOVIS ADD pofTiempoSancion VARCHAR(10) NULL;
ALTER TABLE PostulacionFOVIS ADD pofMotivoRestitucion VARCHAR(45) NULL;
ALTER TABLE PostulacionFOVIS ADD pofMotivoEnajenacion VARCHAR(40) NULL;
ALTER TABLE PostulacionFOVIS ADD pofValorAjusteIPCSFV NUMERIC(19,5) NULL;

--changeset fvasquez:05
--comment:Se adiciona campo en la tabla Cartera
ALTER TABLE Cartera ADD carFechaAsignacionAccion DATETIME NULL;

--changeset fvasquez:06
--comment:Insercion de registros en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1A','00','05','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1B','00','10','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1C','00','15','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1D','00','20','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1E','00','25','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_1F','00','30','00','DIARIO','ACTIVO');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepFrecuencia,pepEstado) VALUES ('ASIGNACION_ACCION_CIERRE','00','35','00','DIARIO','ACTIVO');

--changeset flopez:07
--comment:Se modifica tamaño de campo de la tabla DocumentoSoporte
ALTER TABLE DocumentoSoporte ALTER COLUMN dosTipoDocumento VARCHAR(24) NULL;
