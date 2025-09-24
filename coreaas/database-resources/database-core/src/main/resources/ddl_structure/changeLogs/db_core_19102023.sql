

if not exists (select * from FieldCatalog where id = 85)
begin
insert into FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(85, 'STRING', 'Medio de pago', null, null, 'medioDePago', 'Medio de pago', null, null, null, null, 5);
update FieldDefinition 
set fieldOrder = fieldOrder + 1
where lineDefinition_id = 5 and fieldOrder > 24
end


if not exists (select * from FieldDefinition where id = 85)
insert into FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(85, null, null, null, 'medio de pago', 25, 'NONE', 0, 85, 5);
