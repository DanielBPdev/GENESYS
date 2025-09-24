--liquibase formatted sql

--changeset jvelandia:01
--comment: 	Configuraciones VariableComunicado
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('RESPONSABLE_FOVIS_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Username del responsable del proceso de fovis');
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('CARGO_RESPONSABLE_FOVIS_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Cargo del responsable del proceso de fovis');

DELETE VariableComunicado where vcoClave in('${responsableAportesCcf}','${cargoResponsableAportesCcf}') 
AND vcoPlantillaComunicado=(SELECT pcoId from PlantillaComunicado where pcoEtiqueta='SUS_NTF_NO_PAG');
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante) 
VALUES ('${responsableCarteraCcf}',null,'Responsable de cartera de la caja','Responsable de aportes de la caja','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'),'RESPONSABLE_CARTERA_CCF');
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante) 
VALUES ('${cargoResponsableCarteraCcf}',null,'Cargo responsable de cartera de la caja','Cargo responsable de aportes de la caja','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'),'CARGO_RESPONSABLE_CARTERA_CCF');
UPDATE PlantillaComunicado SET pcoCuerpo = REPLACE(pcoCuerpo,'${responsableAportesCcf}','${responsableCarteraCcf}') 
WHERE pcoEtiqueta='SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo = REPLACE(pcoCuerpo,'${cargoResponsableAportesCcf}','${cargoResponsableCarteraCcf}') 
WHERE pcoEtiqueta='SUS_NTF_NO_PAG';

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${responsableFovisCcf}',null,'Responsable de fovis de la caja','Responsable de aportes de la caja','CONSTANTE',
pl.pcoId, 'RESPONSABLE_FOVIS_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación rechazo de solicitud de corrección de aportes',
'Recordatorio plazo límite pago aportes',
'Recordatorio plazo límite pago aportes personas',
'Notificación de intento de postulación',
'Notificación de radicación  solicitud postulación FOVIS presencial',
'Rechazo de solicitud de postulación FOVIS por escalamiento',
'Notificación de radicación de solicitud de legalización y desembolso',
'Notificación de intento de legalizacion y desembolso',
'Rechazo de solicitud de postulación FOVIS',
'Rechazo de solicitud de legalización y desembolso FOVIS por subsanación no exitosa',
'Desembolso FOVIS autorizado',
'Notificación de desembolso FOVIS exitoso',
'Desembolso FOVIS no autorizado',
'Notificación de subsanación de postulación FOVIS exitosa',
'Notificaciónde radicación de solicitud de postulación  FOVIS web',
'Rechazo de solicitud de legalización y desembolso FOVIS por escalamiento',
'Notificación de validaciones no exitosas para radicar postulación FOVIS web',
'Notificación de radicación de solicitud de novedad FOVIS',
'Notificación de resultados de solicitud de novedad FOVIS',
'Rechazo de solicitud de novedad FOVIS por escalamiento',
'Rechazo de solicitud de novedad FOVIS por producto no conforme no subsanable',
'Acta de asignación FOVIS',
'Carta asignación FOVIS');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${cargoResponsableFovisCcf}',null,'Cargo responsable proceso de fovis','Cargo responsable proceso de fovis','CONSTANTE',
pl.pcoId, 'CARGO_RESPONSABLE_FOVIS_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación rechazo de solicitud de corrección de aportes',
'Recordatorio plazo límite pago aportes',
'Recordatorio plazo límite pago aportes personas',
'Notificación de intento de postulación',
'Notificación de radicación  solicitud postulación FOVIS presencial',
'Rechazo de solicitud de postulación FOVIS por escalamiento',
'Notificación de radicación de solicitud de legalización y desembolso',
'Notificación de intento de legalizacion y desembolso',
'Rechazo de solicitud de postulación FOVIS',
'Rechazo de solicitud de legalización y desembolso FOVIS por subsanación no exitosa',
'Desembolso FOVIS autorizado',
'Notificación de desembolso FOVIS exitoso',
'Desembolso FOVIS no autorizado',
'Notificación de subsanación de postulación FOVIS exitosa',
'Notificaciónde radicación de solicitud de postulación  FOVIS web',
'Rechazo de solicitud de legalización y desembolso FOVIS por escalamiento',
'Notificación de validaciones no exitosas para radicar postulación FOVIS web',
'Notificación de radicación de solicitud de novedad FOVIS',
'Notificación de resultados de solicitud de novedad FOVIS',
'Rechazo de solicitud de novedad FOVIS por escalamiento',
'Rechazo de solicitud de novedad FOVIS por producto no conforme no subsanable',
'Acta de asignación FOVIS',
'Carta asignación FOVIS');

----###########################APORTES###################################
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${responsableAportesCcf}',null,'Responsable del proceso de aportes','Responsable del proceso de aportes','CONSTANTE',
pl.pcoId, 'RESPONSABLE_APORTESS_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación de gestión de información faltante para devolución de aportes',
'Notificación de rechazo de solicitud de devolución de aportes',
'Notificación de aprobación de solicitud de devolución de aportes',
'Notificación de pago de solicitud de devolución de aportes',
'Notificación de pago de aportes - pagador por sí mismo',
'Notificación aprobación de solicitud de corrección de aportes');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${cargoResponsableAportesCcf}',null,'Cargo responsable del proceso de aportes','Cargo responsable del proceso de aportes','CONSTANTE',
pl.pcoId, 'CARGO_RESPONSABLE_APORTES_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación de gestión de información faltante para devolución de aportes',
'Notificación de rechazo de solicitud de devolución de aportes',
'Notificación de aprobación de solicitud de devolución de aportes',
'Notificación de pago de solicitud de devolución de aportes',
'Notificación de pago de aportes - pagador por sí mismo',
'Notificación aprobación de solicitud de corrección de aportes');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${responsableCarteraCcf}',null,'Responsable del proceso de cartera','Responsable del proceso de cartera','CONSTANTE',
pl.pcoId, 'RESPONSABLE_CARTERA_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Cierre satisfactorio convenio de pago',
'Notificación de no recaudo de aportes',
'Suspensión automática de servicios por mora y notificación de no recaudo',
'Notificación de no recaudo de aportes personas',
'Notificación interna No envío comunicado cobro',
'Aviso afiliación empresas presencial tiempo de proceso solicitud',
'Aviso afiliación empresas presencial asignar solicitud empleador',
'Aviso afiliación empresas presencial tiempo de gestión de la solicitud back',
'Aviso de incumplimiento',
'Liquidación de aportes en mora',
'Citación para notificación personal',
'Primer aviso cobro persuasivo',
'Notificación por aviso',
'Segundo aviso cobro persuasivo',
'Carta a empresa expulsada',
'Carta persona expulsada');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${cargoResponsableCarteraCcf}',null,'Cargo responsable del proceso de cartera','Cargo responsable del proceso de cartera','CONSTANTE',
pl.pcoId, 'CARGO_RESPONSABLE_CARTERA_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Cierre satisfactorio convenio de pago',
'Notificación de no recaudo de aportes',
'Suspensión automática de servicios por mora y notificación de no recaudo',
'Notificación de no recaudo de aportes personas',
'Notificación interna No envío comunicado cobro',
'Aviso afiliación empresas presencial tiempo de proceso solicitud',
'Aviso afiliación empresas presencial asignar solicitud empleador',
'Aviso afiliación empresas presencial tiempo de gestión de la solicitud back',
'Aviso de incumplimiento',
'Liquidación de aportes en mora',
'Citación para notificación personal',
'Primer aviso cobro persuasivo',
'Notificación por aviso',
'Segundo aviso cobro persuasivo',
'Carta a empresa expulsada',
'Carta persona expulsada');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${responsableSubsidioCcf}',null,'Responsable del proceso de subsidio','Responsable del proceso de subsidio','CONSTANTE',
pl.pcoId, 'RESPONSABLE_SUBSIDIO_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación por cancelación de publicación en edicto para gestión de cobro de aportantes',
'Comprobante de retiro por ventanilla',
'Noficación de inconsistencias en procesamiento de archivo de consumo ANIBOL',
'Notificación de dispersión de pagos al empleador',
'Notificación de dispersión de pagos al trabajador',
'Notificación de dispersión de pagos al administrador del subsidio',
'Notificación de programación de dispersión de pagos al trabajador – liquidación específica de subsidio por fallecimiento',
'Notificación de programación de dispersión de pagos al administrador del subsidio – liquidación específica de subsidio por fallecimiento',
'Notificación de dispersión de pagos al trabajador – liquidación específica de subsidio por fallecimiento',
'Notificación de dispersión de pagos al administrador del subsidio – liquidación específica de subsidio por fallecimiento',
'Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio',
'Notificación rechazo liquidación específica por fallecimiento - Trabajador');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante)
SELECT '${cargoResponsableSubsidioCcf}',null,'Cargo responsable del proceso de subsidio','Cargo responsable del proceso de subsidio','CONSTANTE',
pl.pcoId, 'CARGO_RESPONSABLE_SUBSIDIO_CCF' FROM PlantillaComunicado Pl
where pl.pcoNombre IN ('Notificación por cancelación de publicación en edicto para gestión de cobro de aportantes',
'Comprobante de retiro por ventanilla',
'Noficación de inconsistencias en procesamiento de archivo de consumo ANIBOL',
'Notificación de dispersión de pagos al empleador',
'Notificación de dispersión de pagos al trabajador',
'Notificación de dispersión de pagos al administrador del subsidio',
'Notificación de programación de dispersión de pagos al trabajador – liquidación específica de subsidio por fallecimiento',
'Notificación de programación de dispersión de pagos al administrador del subsidio – liquidación específica de subsidio por fallecimiento',
'Notificación de dispersión de pagos al trabajador – liquidación específica de subsidio por fallecimiento',
'Notificación de dispersión de pagos al administrador del subsidio – liquidación específica de subsidio por fallecimiento',
'Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio',
'Notificación rechazo liquidación específica por fallecimiento - Trabajador');

--changeset jvelandia:02
--comment: 	Configuraciones VariableComunicado
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado)
SELECT '${cotizantes}',null,'Tipo, numero y nombre del cotizante como lista','Tipo, numero y nombre del cotizante como lista','VARIABLE',
pl.pcoId FROM PlantillaComunicado Pl
WHERE pcoEtiqueta 
IN ('NTF_REC_APT_PLA_DEP1','NTF_REC_APT_PLA_DEP3','NTF_REC_APT_PLA_DEP5','NTF_REC_APT_PLA_DEP6','NTF_REC_APT_PLA_DEP7','NTF_REC_APT_PLA_DEP8','NTF_REC_APT_PLA_DEP9',
'NTF_REC_APT_PLA_DEP10','NTF_REC_APT_PLA_DEP11','NTF_REC_APT_PLA_DEP13','NTF_REC_APT_PLA_DEP14','NTF_REC_APT_PLA_DEP15','NTF_REC_APT_PLA_DEP16',
'NTF_REC_APT_PLA_DEP17','NTF_REC_APT_PLA_DEP18','NTF_REC_APT_PLA_DEP19','NTF_REC_APT_PLA_DEP20','NTF_REC_APT_PLA_DEP21','NTF_REC_APT_PLA_DEP22',
'NTF_REC_APT_PLA_DEP23','NTF_REC_APT_PLA_DEP24','NTF_REC_APT_PLA_DEP25','NTF_REC_APT_PLA_DEP26','NTF_REC_APT_PLA_DEP27','NTF_REC_APT_PLA_DEP28',
'NTF_REC_APT_PLA_DEP29','NTF_REC_APT_PLA_DEP30','NTF_REC_APT_PLA_DEP31','NTF_REC_APT_PLA_DEP32','NTF_REC_APT_PLA_IDPE1','NTF_REC_APT_PLA_IDPE2',
'NTF_REC_APT_PLA_IDPE3','NTF_REC_APT_PLA_IDPE4','NTF_REC_APT_PLA_IDPE5','NTF_REC_APT_PLA_IDPE6','NTF_REC_APT_PLA_IDPE7','NTF_REC_APT_PLA_IDPE8',
'NTF_REC_APT_PLA_IDPE9','NTF_REC_APT_PLA_PNS1','NTF_REC_APT_PLA_PNS2','NTF_REC_APT_PLA_PNS3','NTF_REC_APT_PLA_PNS4','NTF_REC_APT_PLA_PNS5',
'NTF_REC_APT_PLA_PNS6','NTF_REC_APT_PLA_PNS7','NTF_REC_APT_PLA_PNS8','NTF_REC_APT_PLA_PNS9');

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cotizantes}</p>') WHERE pcoEtiqueta 
IN ('NTF_REC_APT_PLA_DEP1','NTF_REC_APT_PLA_DEP3','NTF_REC_APT_PLA_DEP5','NTF_REC_APT_PLA_DEP6','NTF_REC_APT_PLA_DEP7','NTF_REC_APT_PLA_DEP8','NTF_REC_APT_PLA_DEP9',
'NTF_REC_APT_PLA_DEP10','NTF_REC_APT_PLA_DEP11','NTF_REC_APT_PLA_DEP13','NTF_REC_APT_PLA_DEP14','NTF_REC_APT_PLA_DEP15','NTF_REC_APT_PLA_DEP16',
'NTF_REC_APT_PLA_DEP17','NTF_REC_APT_PLA_DEP18','NTF_REC_APT_PLA_DEP19','NTF_REC_APT_PLA_DEP20','NTF_REC_APT_PLA_DEP21','NTF_REC_APT_PLA_DEP22',
'NTF_REC_APT_PLA_DEP23','NTF_REC_APT_PLA_DEP24','NTF_REC_APT_PLA_DEP25','NTF_REC_APT_PLA_DEP26','NTF_REC_APT_PLA_DEP27','NTF_REC_APT_PLA_DEP28',
'NTF_REC_APT_PLA_DEP29','NTF_REC_APT_PLA_DEP30','NTF_REC_APT_PLA_DEP31','NTF_REC_APT_PLA_DEP32','NTF_REC_APT_PLA_IDPE1','NTF_REC_APT_PLA_IDPE2',
'NTF_REC_APT_PLA_IDPE3','NTF_REC_APT_PLA_IDPE4','NTF_REC_APT_PLA_IDPE5','NTF_REC_APT_PLA_IDPE6','NTF_REC_APT_PLA_IDPE7','NTF_REC_APT_PLA_IDPE8',
'NTF_REC_APT_PLA_IDPE9','NTF_REC_APT_PLA_PNS1','NTF_REC_APT_PLA_PNS2','NTF_REC_APT_PLA_PNS3','NTF_REC_APT_PLA_PNS4','NTF_REC_APT_PLA_PNS5',
'NTF_REC_APT_PLA_PNS6','NTF_REC_APT_PLA_PNS7','NTF_REC_APT_PLA_PNS8','NTF_REC_APT_PLA_PNS9');

--changeset jvelandia:03
--comment: 	
ALTER TABLE Comunicado ADD comCertificado BIGINT;
ALTER TABLE aud.Comunicado_aud ADD comCertificado BIGINT;
