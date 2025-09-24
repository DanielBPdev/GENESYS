--liquibase formatted sql

--changeset flopez:01
--comment: Inserciones en la tabla ParametrizacionEjecucionProgramada HU 035-134-424, 135-448, 136-454
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepDiaMes,pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR','00','00','01','MENSUAL')
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepDiaMes,pepFrecuencia) VALUES ('VENCIMIENTO_AUTOMATICO_CERTIFICADOS','00','00','01','MENSUAL')
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepDiaMes,pepFrecuencia) VALUES ('VENCIMIENTO_AUTOMATICO_INCAPACIDADES','00','00','01','MENSUAL')
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepDiaMes,pepFrecuencia) VALUES ('ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE','00','00','01','MENSUAL')

--changeset flopez:02
--comment: Inserciones en la tabla Novedad HU 035-134-424, 135-448, 136-454
--Novedades Persona
INSERT Novedad (novPuntoResolucion,novTipoTransaccion,novTipoNovedad,novRutaCualificada) VALUES ('SISTEMA_AUTOMATICO','INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR','AUTOMATICA','com.asopagos.convertidores.persona.ActualizarInactivarCuentaWebPersona');
INSERT Novedad (novPuntoResolucion,novTipoTransaccion,novTipoNovedad,novRutaCualificada) VALUES ('SISTEMA_AUTOMATICO','VENCIMIENTO_AUTOMATICO_CERTIFICADOS','AUTOMATICA','com.asopagos.convertidores.persona.ActualizarVenciCertificadoEscolaridad');
INSERT Novedad (novPuntoResolucion,novTipoTransaccion,novTipoNovedad,novRutaCualificada) VALUES ('SISTEMA_AUTOMATICO','VENCIMIENTO_AUTOMATICO_INCAPACIDADES','AUTOMATICA','com.asopagos.convertidores.persona.ActualizarVenciIncapacidades');
INSERT Novedad (novPuntoResolucion,novTipoTransaccion,novTipoNovedad,novRutaCualificada) VALUES ('SISTEMA_AUTOMATICO','ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE','AUTOMATICA','com.asopagos.convertidores.persona.ActualizarVenciIncapacidades');
