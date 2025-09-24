if exists (select * from ValidatorCatalog where id = 211192)
begin
insert ValidatorCatalog (id,className,description,name,scope,tenantId) values (211192
,'com.asopagos.pila.validadores.bloque2.ValidadorTipoAportanteSubtipoCotizante',
'Validación de Tipo de aportante vs Subtipo de cotizante '
,'Tipo - Subtipo Cotizante validator','LINE',null)
end
-----------------------------------------------------------------------------------
if exists (select * from ValidatorDefinition where id = 2110384)
begin
insert into ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state) 
values (2110384, 0, 0, 108, null, null, 4, 211192, 2, 1)
end
---------------------------------------------------------------------------------
if exists (select * from ValidatorParameter where id = 211395)
begin
insert into ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id)
values (211395, 'STRING', 'Campo Tipo Aportante', null, 'campoTipoAportante', 211192);
end
-----------------------------------------------------------------------------------
if exists (select * from ValidatorParameter where id = 211396)
begin
insert into ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) 
values (211396, 'STRING', 'Nombre del campo a validar', null, 'nombreCampo', 211192);
end
------------------------------------------------------------------------------------------
if exists (select * from ValidatorParameter where id = 211397)
begin
insert into ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id)
values (211397, 'STRING', 'Campo Subtipo Cotizante', null, 'campoSubtipoCotizante', 211192);
end
-----------------------------------------------------------------------------------------
if exists (select * from ValidatorParamValue where id = 2111787)
begin
insert into ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) 
values (2111787, 'Archivo Tipo I - Registro 2 - Campo 6: Subtipo de cotizante', 2110384, 211396);
end
----------------------------------------------------------------------------------------
if exists (select * from ValidatorParamValue where id = 2111788)
begin
insert into ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) 
values (2111788, 'archivoIregistro2campo6', 2110384, 211397);
end
-----------------------------------------------------------------------------------------------
if exists (select * from ValidatorParamValue where id = 2111789)
begin
insert into ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id)
values (2111789, 'tipoAportante', 2110384, 211395);
end