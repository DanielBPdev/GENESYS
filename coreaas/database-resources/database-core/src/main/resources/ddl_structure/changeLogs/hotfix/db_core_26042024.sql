
--72393 Alcance adicional actualización información masiva (  GENESYS)
--Se eliminan los campos que ya no se requieren (responsable de contacto de caja 1 y 2)
DELETE FROM FieldDefinitionLoad WHERE id = 400000040;
DELETE FROM FieldLoadCatalog WHERE id = 400000040;
DELETE FROM FieldDefinitionLoad WHERE id = 400000041;
DELETE FROM FieldLoadCatalog WHERE id = 400000041;

--Se actualiza el orden de los campos
update FieldLoadCatalog
set fieldOrder = 25
where id = 400000042