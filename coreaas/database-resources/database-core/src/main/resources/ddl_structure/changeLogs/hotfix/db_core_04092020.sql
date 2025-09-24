--liquibase formatted sql

--changeset amarin:01
--comment: Se parametriza como no obligatorio el registro tipo 2 del archivo de encontrados en la carga de supervivencia
UPDATE LineDefinitionLoad
SET required = 0
FROM Constante con
JOIN FileDefinitionLoad fdl ON fdl.id = (CASE
  WHEN
    (isnumeric(con.cnsValor) = 1) 
  THEN
    CAST(con.cnsValor AS bigint)
  ELSE
    0
  END)
JOIN FileDefinitionLoadType fdlt ON fdl.fileDefinitionType_id = fdlt.id
JOIN LineDefinitionLoad ldl ON ldl.fileDefinition_id = fdl.id
JOIN LineLoadCatalog llc ON llc.id = ldl.lineLoadCatalog_id
where con.cnsNombre = 'FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC' AND ldl.identifier = 2;