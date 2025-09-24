--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla VariableComunicado
UPDATE VariableComunicado set vcoNombreConstante='LOGO_DE_LA_CCF', vcoNombre='Logo de la CCF' WHERE vcoClave='${logoDeLaCcf}' AND vcoPlantillaComunicado=(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS');
UPDATE VariableComunicado set vcoNombreConstante='RESPONSABLE_CCF' WHERE vcoClave='${responsableCcf}' AND vcoPlantillaComunicado=(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS');
UPDATE VariableComunicado set vcoNombreConstante='FIRMA_RESPONSABLE_CCF' WHERE vcoClave='${firmaResponsableCcf}' AND vcoPlantillaComunicado=(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS');

--changeset abaquero:01
--comment: Actualización de configuración de lectura de archivos FileProcessing PILA M1
UPDATE dbo.LineDefinitionLoad SET numberGroup=9 WHERE id=11
UPDATE dbo.LineDefinitionLoad SET numberGroup=10 WHERE id=12
UPDATE dbo.LineDefinitionLoad SET numberGroup=10 WHERE id=13 
UPDATE dbo.LineDefinitionLoad SET numberGroup=10 WHERE id=14 
UPDATE dbo.LineDefinitionLoad SET numberGroup=11 WHERE id=15

UPDATE dbo.ValidatorCatalog SET [scope]='FIELD' WHERE id=211073

UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110317
UPDATE dbo.ValidatorDefinition SET lineDefinition_id=NULL, fieldDefinition_id=2110214, state=0 WHERE id=2110318
UPDATE dbo.ValidatorDefinition SET lineDefinition_id=NULL, fieldDefinition_id=2110227, state=0 WHERE id=2110319
