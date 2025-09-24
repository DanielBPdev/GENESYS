
if not exists (select * from FileDefinitionLoadType where id = 1238)
begin

	begin
	insert into FileDefinitionType (id,description,name)
	values(1238,'Archivo servicios cobrados por efectivo','Estructura del archivo servicios cobrados por efectivo')
	end
	begin
	insert into FileDefinition (id,decimalSeparator,nombre,compressAll,compressEachFile,fileDefinitionType_id)values
	(1238,'|','Estructura del archivo de salida de descuentos NUEVO',0,0,1238)
	end
	begin
	insert into LineCatalog (id,className,description,name,paginated) values(8,'com.asopagos.subsidiomonetario.pagos.load.validator.PagosSubsidioMonetarioLineValidator',
	'Validacion Información de la solicitud del convenio del tercero pagador','Información conciliacion servicios cobrados por efectivo',0)
	end
	begin
	insert into LineDefinition (id,generateLineFooter,generaterHeaderLine,lineOrder,required,fileDefinition_id,lineCatalog_id)values
	(8,0,0,1,1,1234,8)
	end
	begin
	insert into LineLoadCatalog (id,className,description,name,lineOrder,lineSeparator)
	values (1238,'com.asopagos.subsidiomonetario.pagos.load.validator.PagosSubsidioMonetarioLineValidator','Estructura del archivo servicios cobrados por efectivo','Archivo servicios cobrados por efectivo',1,'|')
	end
	begin
	insert into FileDefinitionLoadType (id,description,name) values 
	(1238,'Archivo servicios cobrados por efectivo','Estructura del archivo servicios cobrados por efectivo')
	end
	begin
	insert into FileDefinitionLoad(id,decimalSeparator,nombre,excludeLines,useCharacters,fileDefinitionType_id)values
	(1238,'|','Estructura del archivo servicios cobrados por efectivo',0,0,1238)
	end
	begin
	insert into LineDefinitionLoad (id,required,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) values
	(12128,1,1,1238,1238)
	end
	begin
	update Constante set cnsValor=1238 where cnsNombre='FILE_DEFINITION_ID_ARCHIVO_RETIRO_PAGOS_SUBSIDIO_MONETARIO'
	end
	begin
	update LineLoadCatalog set className= 'com.asopagos.subsidiomonetario.pagos.persist.PagosSubsidioMonetarioPersistLine' 
	where className='com.asopagos.subsidiomonetario.pagos.load.validator.PagosSubsidioMonetarioLineValidator'
	end
	begin
	update FieldDefinitionLoad set lineDefinition_id=12128 where lineDefinition_id=1234
	end

end;