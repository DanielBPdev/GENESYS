--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla PlantillaComunicado
UPDATE PlantillaComunicado set pcoCuerpo='Cuerpo<br  /> <p>${ciudadSolicitud}</p><br  /> <p>${fechaDelSistema}</p><br  /> <p>${direccionResidencia}</p><br  /> <p>${municipio}</p><br  /> <p>${departamento}</p><br  /> <p>${telefono}</p><br  /> <p>${fechaRadicacionSolicitud}</p><br  /> <p>${numeroSolicitud}</p><br  /> <p>${modalidad}</p><br  /> <p>${cicloAsignacion}</p><br  /> <p>${tipoIdentificacion}</p><br  /> <p>${numeroIdentificacion}</p><br  /> <p>${nombresYApellidosDelJefeDelHogar}</p><br  /> <p>${contenido}</p> <br  /> <p>${responsableCcf}</p><br  /> <p>${cargoResponsableCcf}</p><br  /> <p>${direccionCcf}</p><br  /> <p>${telefonoCcf}</p> <br /> <p>${firmaResponsableCcf}</p>'
WHERE pcoEtiqueta='NTF_RAD_SOL_NVD_FOVIS';

--changeset abaquero:02
--comment: Actualizacion tablas LineDefinitionLoad, ValidatorCatalog ValidatorDefinition
UPDATE LineDefinitionLoad SET numberGroup=9 WHERE id=11;
UPDATE LineDefinitionLoad SET numberGroup=10 WHERE id=12;
UPDATE LineDefinitionLoad SET numberGroup=10 WHERE id=13;
UPDATE LineDefinitionLoad SET numberGroup=10 WHERE id=14;
UPDATE LineDefinitionLoad SET numberGroup=11 WHERE id=15;
UPDATE ValidatorCatalog SET [scope]='FIELD' WHERE id=211073;
UPDATE ValidatorDefinition SET state=0 WHERE id=2110317;
UPDATE ValidatorDefinition SET lineDefinition_id=NULL, fieldDefinition_id=2110214, state=0 WHERE id=2110318;
UPDATE ValidatorDefinition SET lineDefinition_id=NULL, fieldDefinition_id=2110227, state=0 WHERE id=2110319;

--changeset clmarin:03
--comment: Actualizacion tablas LineDefinitionLoad, ValidatorCatalog ValidatorDefinition
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad) VALUES ((select dcoid from DestinatarioComunicado where dcoEtiquetaPlantilla='NTF_CCL_PUB_EDC' ),
(select gprId from GrupoPrioridad where gprNombre='SUPERVISOR_CARTERA' ), 1);