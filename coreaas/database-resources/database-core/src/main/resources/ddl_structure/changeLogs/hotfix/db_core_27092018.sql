--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizaciones tabla PlantillaComunicado
UPDATE PlantillaComunicado set pcoCuerpo='Cuerpo<br  /> <p>${ciudadSolicitud}</p><br  /> <p>${fechaDelSistema}</p><br  /> <p>${direccionResidencia}</p><br  /> <p>${municipio}</p><br  /> <p>${departamento}</p><br  /> <p>${telefono}</p><br  /> <p>${fechaRadicacionSolicitud}</p><br  /> <p>${numeroSolicitud}</p><br  /> <p>${modalidad}</p><br  /> <p>${cicloAsignacion}</p><br  /> <p>${tipoIdentificacion}</p><br  /> <p>${numeroIdentificacion}</p><br  /> <p>${nombresYApellidosDelJefeDelHogar}</p><br  /> <p>${responsableCcf}</p><br  /> <p>${cargoResponsableCcf}</p><br  /> <p>${direccionCcf}</p><br  /> <p>${telefonoCcf}</p> <br /> <p>${responsableCcf}</p><br /> <p>${firmaResponsableCcf}</p>',
pcoEncabezado='<p>${logoDeLaCcf}</p>'
WHERE pcoEtiqueta='NTF_RAD_SOL_NVD_FOVIS';

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}',null,'Logo SuperServicios','Logo de la caja de Compensación','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}',null,'Responsable CCF','Responsable de la caja de Compensación','USUARIO_CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}',null,'Firma de la caja de Compensación','Firma Responsable CCF','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS') ) ;

