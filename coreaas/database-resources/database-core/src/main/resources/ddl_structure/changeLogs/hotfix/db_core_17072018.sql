--liquibase formatted sql

--changeset abaquero:01
--comment: Se crea campo excludeForValidations y se le asigna datos
ALTER TABLE LineDefinitionLoad ADD excludeForValidations bit; 
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=1;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=2;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=3;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=4;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=5;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=6;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=7;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=8;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=9;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=10;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=11;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=12;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=13;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=14;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=15;
UPDATE LineDefinitionLoad SET excludeForValidations=0 WHERE id=16;

--changeset abaquero:02
--comment: Inserciones tabla ValidatorParameter
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211379, 'STRING', 'Campo Tipo Cotizante', null, 'campoTipoCotizante', 211071);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111732, 'archivoIregistro2campo5', 2110315, 211379);

--changeset jvelandia:03
--comment: Borrado -  Insercion tablas PrioridadDestinatario y DestinatarioComunicado
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado in (SELECT dcoId from destinatarioComunicado WHERE dcoEtiquetaPlantilla='NTF_SBC_AFL_PNS')
INSERT PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='NTF_SBC_AFL_PNS'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('AFILIACION_INDEPENDIENTE_WEB', 'NTF_ACPT_AFL_IDPE_DSP_SUB');
INSERT DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('AFILIACION_INDEPENDIENTE_WEB', 'NTF_ACPT_AFL_PNS_DSP_SUB');
INSERT PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='NTF_ACPT_AFL_IDPE_DSP_SUB'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='NTF_ACPT_AFL_PNS_DSP_SUB'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);