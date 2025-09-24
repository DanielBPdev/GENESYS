--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla VariableComunicado
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoSolicitante}','0','Tipo Solicitante','Tipo Solicitante','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') ) ;