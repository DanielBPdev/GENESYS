--------Alteral el constraint--------
ALTER TABLE dbo.VariableComunicado
DROP CONSTRAINT CK_VariableComunicado_vcoTipoVariableComunicado;

--------Agregar el nuevo valor al constraint--------
ALTER TABLE dbo.VariableComunicado
ADD CONSTRAINT CK_VariableComunicado_vcoTipoVariableComunicado
CHECK (
 [vcoTipoVariableComunicado]='REPORTE_VARIABLE' OR
 [vcoTipoVariableComunicado]='LISTA_VARIABLE' OR
 [vcoTipoVariableComunicado]='CONSTANTE' OR
 [vcoTipoVariableComunicado]='VARIABLE' OR
 [vcoTipoVariableComunicado]='USUARIO_VARIABLE' OR
 [vcoTipoVariableComunicado]='USUARIO_CONSTANTE' OR
 [vcoTipoVariableComunicado]='FECHA_LARGA' OR
 [vcoTipoVariableComunicado]='LUGAR_MAYUS'
);

--------Editar las claves de municipio y departamento con el nuevo valor--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'LUGAR_MAYUS'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${municipio}'

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'LUGAR_MAYUS'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${departamento}'

--------Cambio Formato Fechas Comunicados--------
--------Comunicado Acta de asignación FOVIS--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaApertura}'
AND pcoEtiqueta = 'ACT_ASIG_FOVIS';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCierre}'
AND pcoEtiqueta = 'ACT_ASIG_FOVIS';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaOficio}'
AND pcoEtiqueta = 'ACT_ASIG_FOVIS';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaResolucion}'
AND pcoEtiqueta = 'ACT_ASIG_FOVIS';

--------Comunicado Aviso de incumplimiento--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'AVI_INC';

--------Comunicado Aviso de incumplimiento persona--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'AVI_INC_PER';

--------Comunicado Citación para notificación personal--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'CIT_NTF_PER';

--------Comunicado Certificado de Afiliación--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaGeneracion}'
AND pcoEtiqueta = 'COM_GEN_CER_AFI';

--------Comunicado Certificado de Aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaGeneracion}'
AND pcoEtiqueta = 'COM_GEN_CER_APO';

--------Comunicado Certificado histórico de afiliaciones--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaGeneracion}'
AND pcoEtiqueta = 'COM_GEN_CER_HIS_AFI';

--------Comunicado Certificado Paz y Salvo Persona--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaGeneracion}'
AND pcoEtiqueta = 'COM_GEN_CER_PYS';

--------Comunicado Certificado Paz y Salvo Empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaGeneracion}'
AND pcoEtiqueta = 'COM_GEN_CER_PYS_EMP';

--------Comunicado Noficación de inconsistencias en procesamiento de archivo de consumo ANIBOL--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaHoraCargue}'
AND pcoEtiqueta = 'COM_PAG_SUB_INC_ARC_CON';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaHoraProcesamiento}'
AND pcoEtiqueta = 'COM_PAG_SUB_INC_ARC_CON';

--------Comunicado Notificación de dispersión de pagos subsidio fallecimiento al admin subsidio--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_ADM_SUB';

--------Comunicado Notificación de dispersión de pagos subsidio fallecimiento al trabajador ó conyuge--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_TRA';

--------Comunicado Carta de aceptación de empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'CRT_ACP_EMP';

--------Comunicado Carta asignación FOVIS--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaInicioVigencia}'
AND pcoEtiqueta = 'CRT_ASIG_FOVIS';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'CRT_ASIG_FOVIS';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaResolucion}'
AND pcoEtiqueta = 'CRT_ASIG_FOVIS';

--------Comunicado Carta de bienvenida para empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'CRT_BVD_EMP';

--------Comunicado Carta entidad pagadora--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fecha}'
AND pcoEtiqueta = 'CRT_ENT_PAG';

--------Comunicado Desembolso FOVIS autorizado--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'DES_FOVIS_AUTO';

--------Comunicado Desembolso FOVIS no autorizado--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'DES_FOVIS_NAUTO';

--------Comunicado Liquidación de aportes en mora manual--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaComunicado}'
AND pcoEtiqueta = 'LIQ_APO_MAN';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'LIQ_APO_MAN';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaNotificacionPersonal}'
AND pcoEtiqueta = 'LIQ_APO_MAN';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaSuspencionAutomatica}'
AND pcoEtiqueta = 'LIQ_APO_MAN';

--------Comunicado Liquidación de aportes en mora--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaComunicado}'
AND pcoEtiqueta = 'LIQ_APO_MOR';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'LIQ_APO_MOR';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaNotificacionPersonal}'
AND pcoEtiqueta = 'LIQ_APO_MOR';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaSuspencionAutomatica}'
AND pcoEtiqueta = 'LIQ_APO_MOR';

--------Comunicado Notificación aprobación de solicitud de corrección de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRegistroSolicitud}'
AND pcoEtiqueta = 'NTF_APR_COR_APT';

--------Comunicado Notificación de aprobación de solicitud de devolución de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRegistroSolicitud}'
AND pcoEtiqueta = 'NTF_APR_DVL_APT';

--------Comunicado Notificación por aviso--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaLiquidacion}'
AND pcoEtiqueta = 'NTF_AVI';

UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaNotificacionAviso}'
AND pcoEtiqueta = 'NTF_AVI';

--------Comunicado Notificación de desembolso FOVIS exitoso--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_DES_FOVIS_EXT';

--------Comunicado Notificación para empleador de resultados de afiliación múltiple trabajadores web--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_EMP_AFL_MLT_TRBW';

--------Comunicado Notificación de intento de afiliación--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_INT_AFL';

--------Comunicado Notificación de intento de legalizacion y desembolso--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_INT_LEG_DES';

--------Comunicado Notificación de intento de postulación--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_INT_POS_FOVIS';

--------Comunicado Notificación individual de resultado de afiliación trabajador web (trabajador)--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_INVL_AFL_TRBW';

--------Comunicado Notificación individual de resultados de afiliación de trabajadores web (empleador)--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_INVL_AFL_TRBW_EMP';

--------Comunicado Notificación de no recaudo de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'NTF_NO_REC_APO';

--------Comunicado Notificación de no recaudo de aportes personas--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'NTF_NO_REC_APO_PER';

--------Comunicado Notificación de pago de solicitud de devolución de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRegistroSolicitud}'
AND pcoEtiqueta = 'NTF_PAG_DVL_APT';

--------Comunicado Notificación de radicación  solicitud postulación FOVIS presencial--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';

--------Comunicado Notificaciónde radicación de solicitud de postulación  FOVIS web--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';

--------Comunicado Notificación de radicación de solicitud de legalización y desembolso--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';

--------Comunicado Notificación de radicación de solicitud de novedad FOVIS--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';

--------Comunicado Notificación rechazo de solicitud de corrección de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRegistroSolicitud}'
AND pcoEtiqueta = 'NTF_RCHZ_COR_APT';

--------Comunicado Notificación de rechazo de solicitud de devolución de aportes--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRegistroSolicitud}'
AND pcoEtiqueta = 'NTF_RCHZ_DVL_APT';

--------Comunicado Notificación de resultados del registro de beneficiarios en la solicitud web de una trabajador dirigida al empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';

--------Comunicado Notificación de resultados del registro de beneficiarios en la solicitud web de una trabajador dirigida al trabajador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';

--------Comunicado Notificación de subsanación de postulación FOVIS exitosa--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';

--------Comunicado Notificación de resultados de solicitud de novedad--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';

--------Comunicado Notificación para requerir subsanación de solicitud afiliación de trabajador dependiente--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'NTF_SUB_AFL_TRB_DPT';

--------Comunicado Notificación de validaciones no exitosas para radicar postulación FOVIS web--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente inconsistencia validación caso empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_EMP';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente INconsistencia validación caso trabajador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_TRAB';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanado dirigida a empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_EMP';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanado dirigida a trabajador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_TRB';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanable dirigida a empleador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_EMP';

--------Comunicado Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanable dirigida a trabajador--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_TRB';

--------Comunicado Rechazo de solicitud de afiliación de empresa--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaDelSistema}'
AND pcoEtiqueta = 'RCHZ_AFL_EMP';

--------Comunicado Rechazo de solicitud de legalización y desembolso FOVIS--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';

--------Comunicado Rechazo de solicitud de legalización y desembolso FOVIS por escalamiento--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';

--------Comunicado Rechazo de solicitud de legalización y desembolso FOVIS por subsanación no exitosa--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';

--------Comunicado Rechazo de solicitud de novedad FOVIS por escalamiento--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';

--------Comunicado Rechazo de solicitud de novedad FOVIS por producto no conforme no subsanable--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';

--------Comunicado Rechazo de solicitud de postulación FOVIS--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';

--------Comunicado Rechazo de solicitud de postulación FOVIS por escalamiento--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaRadicacionSolicitud}'
AND pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';

--------Comunicado Suspensión automática de servicios por mora y notificación de no recaudo--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'SUS_NTF_NO_PAG';

--------Comunicado Suspensión automática de servicios por mora y notificación de no recaudo personas--------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'FECHA_LARGA'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave = '${fechaCorte}'
AND pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
