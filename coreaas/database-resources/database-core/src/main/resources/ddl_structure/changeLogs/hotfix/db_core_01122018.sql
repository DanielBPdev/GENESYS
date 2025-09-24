--liquibase formatted sql

--changeset jevlandia:01
--comment: 
UPDATE variablecomunicado SET vcoTipoVariableComunicado='CONSTANTE' WHERE vcoClave in ('${responsableCcf}','${cargoResponsableCcf}');

--changeset fvasquez:01
--comment: 
UPDATE bitacoracartera set bcaresultado='OTRO' where bcaresultado in('EXCLUIDA','CERRADA','APROBADA','FINALIZADO','ASIGNADA');
UPDATE bitacoracartera set bcaresultado='NO_EXITOSO' where bcaresultado in('NO_EXITOSA');
UPDATE bitacoracartera set bcaresultado='EXITOSO' where bcaresultado in('EXITOSA');
UPDATE bitacoracartera set bcaresultado='NO_ENVIADA' where bcaresultado in('NO_ENVIADO');
UPDATE bitacoracartera set bcaactividad='REGISTRO_NOTIFICACION_PERSONAL' where bcaactividad in('NOTIFICACION_PERSONAL');
UPDATE bitacoracartera set bcaactividad='OTRO' where bcaactividad in('LC2B','G1','LC4B','ACCION_MANUAL','LC5B','I2','LC3B');
UPDATE bitacoracartera set bcaactividad='GENERAR_LIQUIDACION' where bcaactividad in('LIQUIDACION_APORTES');


--changeset fvasquez:02
--comment: 
UPDATE bitacoracartera set bcaresultado='OTRO' where bcaresultado in('EXCLUIDA','CERRADA','APROBADA','FINALIZADO','ASIGNADA');
UPDATE bitacoracartera set bcaresultado='NO_EXITOSO' where bcaresultado in('NO_EXITOSA');
UPDATE bitacoracartera set bcaresultado='EXITOSO' where bcaresultado in('EXITOSA');
UPDATE bitacoracartera set bcaresultado='NO_ENVIADA' where bcaresultado in('NO_ENVIADO');
UPDATE bitacoracartera set bcaactividad='REGISTRO_NOTIFICACION_PERSONAL' where bcaactividad in('NOTIFICACION_PERSONAL');
UPDATE bitacoracartera set bcaactividad='OTRO' where bcaactividad in('LC2B','G1','LC4B','ACCION_MANUAL','LC5B','I2','LC3B');
UPDATE bitacoracartera set bcaactividad='GENERAR_LIQUIDACION' where bcaactividad in('LIQUIDACION_APORTES');