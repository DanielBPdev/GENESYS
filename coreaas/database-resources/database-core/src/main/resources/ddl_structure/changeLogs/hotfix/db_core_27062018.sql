--liquibase formatted sql

--changeset jvelandia:01
--comment: 
DELETE VariableComunicado WHERE vcoClave='${tipoDeSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta in ('HU_PROCESO_214_106','HU_PROCESO_214_125','HU_PROCESO_214_129','HU_PROCESO_214_134','HU_PROCESO_214_112'));

--changeset alquintero:02
--comment: Ajustes mantis -0239530
UPDATE AporteGeneral SET apgModalidadPlanilla = 'ELECTRONICA' WHERE apgModalidadPlanilla = 'UNICA';
UPDATE datotemporalsolicitud SET dtsJsonPayload = REPLACE ( CAST(dtsJsonPayload AS nvarchar(max)) , '"modalidadPlanilla":"UNICA"' , '"modalidadPlanilla":"ELECTRONICA"' ) WHERE dtsJsonPayload LIKE '%"modalidadPlanilla":"UNICA"%';


