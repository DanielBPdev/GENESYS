--liquibase formatted sql

--changeset lzarate:01
--comment: Insercion en las tablas Parametro y ParametrizacionEjecucionProgramada
INSERT Parametro (prmNombre,prmValor) VALUES ('DIAS_NOTIFICACION_EXPIRACION_CONTRASENA','2');
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia) VALUES ('BLOQUEAR_CUENTAS_USUARIO_CCF','00','00',null,null,null,null,null,null,null,'DIARIO');