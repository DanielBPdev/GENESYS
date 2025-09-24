--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizaciones tabla VariableComunicado
UPDATE VariableComunicado SET vcoOrden='5' WHERE vcoClave='${fechaYHoraDeSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta IN ('HU_PROCESO_214_106','HU_PROCESO_214_125','HU_PROCESO_214_129','HU_PROCESO_214_134','HU_PROCESO_214_112'));
UPDATE VariableComunicado SET vcoOrden='6' WHERE vcoClave='${numeroDeSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta IN ('HU_PROCESO_214_106','HU_PROCESO_214_125','HU_PROCESO_214_129','HU_PROCESO_214_134','HU_PROCESO_214_112'));
UPDATE VariableComunicado SET vcoOrden='7' WHERE vcoClave='${estadoDeLaSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta IN ('HU_PROCESO_214_106','HU_PROCESO_214_125','HU_PROCESO_214_129','HU_PROCESO_214_134','HU_PROCESO_214_112'));
UPDATE VariableComunicado SET vcoOrden='8' WHERE vcoClave='${estadoDeAfiliacion}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta IN ('HU_PROCESO_214_106','HU_PROCESO_214_125','HU_PROCESO_214_129','HU_PROCESO_214_134','HU_PROCESO_214_112'));
