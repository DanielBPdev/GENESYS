IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS_SALIDA')
BEGIN
    INSERT INTO  [Parametro] ( [prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla])
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS_SALIDA', N'/home/confa/CRUCES_APORTES/OUT', 0, N'FTP_ARCHIVOS_CRUCES', N'Ruta de los archivos de cruces Aportes en el servidor FTP de salida','ROUTE',1)
END

if not exists(select * from ValidatorCatalog where id = 211078)
BEGIN
    INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
    VALUES (211078,'com.asopagos.aportes.masivos.service.load.validator.ArchivoCrucesLineValidator','Validador de línea archivo ftp cruce aportes','Validador de línea de ftp cruce aportes','LINE',NULL);
END

if not exists(select * from ValidatorDefinition where id = 2110360)
BEGIN
    INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) 
    VALUES (2110360,0,0,1,NULL,NULL,12129,211078);
END


update ValidatorParamValue set value='CRUCE_[0-9]{2}_CCF[0-9]{2}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)' where id= 2111774
update FieldCatalog set name = 'tipoIdentificacion' where id = 86
update FieldCatalog set name =  'numeroIdentificacion'where id = 87
update FieldCatalog set name =  'nombre'where id =88
update FieldCatalog set name =  'tipoAportante'where id =89
update FieldCatalog set name =  'estadoCCF'where id =90
update FieldCatalog set name =  'tipoAfiliacionCCF'where id =91
update FieldCatalog set name =   'fechaRetiro'where id =92
update FieldCatalog set dataType =   'STRING'where id =92
