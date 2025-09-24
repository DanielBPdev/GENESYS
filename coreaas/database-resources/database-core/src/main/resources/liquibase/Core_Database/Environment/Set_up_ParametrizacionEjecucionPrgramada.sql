--liquibase formatted sql

--changeset heinsohn:01 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Anuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepMes='01', pepFrecuencia = 'ANUAL' WHERE pepProceso = 'INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010';
IF @@ROWCOUNT = 0
BEGIN
	INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepMes, pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010', '00', '00', '00', '01', '01', 'ANUAL')
END

--changeset heinsohn:02 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:03 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'RETIRO_AUTOMATICO_POR_MORA';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('RETIRO_AUTOMATICO_POR_MORA', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:04 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:05 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'VENCIMIENTO_AUTOMATICO_CERTIFICADOS';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('VENCIMIENTO_AUTOMATICO_CERTIFICADOS', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:06 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'VENCIMIENTO_AUTOMATICO_INCAPACIDADES';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('VENCIMIENTO_AUTOMATICO_INCAPACIDADES', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:07 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:08 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrización Procesos Mensuales
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepDiaMes='01', pepFrecuencia = 'MENSUAL' WHERE pepProceso = 'REGISTRO_NOVEDAD_MULTIPLE_NO_FINALIZADA';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaMes, pepFrecuencia) VALUES ('REGISTRO_NOVEDAD_MULTIPLE_NO_FINALIZADA', '00', '00', '00', '01', 'MENSUAL')
END

--changeset heinsohn:09 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:10 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'INACTIVAR_ACTIVAR_USUARIOS_TEMPORALES';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('INACTIVAR_ACTIVAR_USUARIOS_TEMPORALES', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:11 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'BLOQUEAR_CUENTAS_USUARIO_CCF';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('BLOQUEAR_CUENTAS_USUARIO_CCF', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:12 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'GENERAR_AVISO_VENCIMIENTO_CLAVE_USUARIO';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('GENERAR_AVISO_VENCIMIENTO_CLAVE_USUARIO', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:13 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'PILA_CARGA_DESCARGA_AUTOMATICA_ARCHIVOS_OI';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('PILA_CARGA_DESCARGA_AUTOMATICA_ARCHIVOS_OI', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:15 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_CERO_TRABAJADORES';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_CERO_TRABAJADORES', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:16 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:17 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD', '00', '00', '00', 'DIARIO')
END

--changeset heinsohn:18 runOnChange:true
--comment: Insercion y actualizacion de registros de la tabla ParametrizacionEjecucionProgramada
--Parametrizacion Procesos Diarios
UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD', '00', '00', '00', 'DIARIO')
END

UPDATE ParametrizacionEjecucionProgramada SET pepHoras='00', pepMinutos='00', pepSegundos='00', pepFrecuencia = 'DIARIO' WHERE pepProceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA';
IF @@ROWCOUNT = 0
BEGIN
  INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) VALUES ('CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA', '00', '00', '00', 'DIARIO')
END