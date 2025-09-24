--liquibase formatted sql

--changeset jvelandia:01
--comment: 
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${refCertificado}',null,'Referencia Certificado','Referencia Certificado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_HIS_AFI') ) ;

--changeset jvelandia:02
--comment: 
DELETE FROM PrioridadDestinatario where prdDestinatarioComunicado = (select dcoId from destinatarioComunicado WHERE dcoProceso='NOVEDADES_EMPRESAS_WEB' AND dcoEtiquetaPlantilla='CNFR_RET_APRT')
DELETE FROM DestinatarioComunicado WHERE dcoProceso='NOVEDADES_EMPRESAS_WEB' AND dcoEtiquetaPlantilla='CNFR_RET_APRT'