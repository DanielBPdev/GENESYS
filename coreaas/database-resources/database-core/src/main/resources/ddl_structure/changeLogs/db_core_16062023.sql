if not exists (select * from FieldDefinitionLoad where id = 32133157)
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133157,NULL,NULL,NULL,'Fecha recepción de documentos del trabajador o cabeza de familia',0,0,1,32133157,12124);
else 
update FieldDefinitionLoad 
set label = 'Fecha de recepcion de documentos del trabajador o cabeza de familia'
where id = 32133157



if  exists (select * from fieldloadcatalog where id = 32133157)
update fieldloadcatalog
set name = 'fechaRecepcionDocumentos',description = 'Fecha recepción de documentos del trabajador o cabeza de familia'
where id = 32133157
