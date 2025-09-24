if not exists (select * from FileDefinitionLoadType where id = 12126)
begin

--1. Insertamos en FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12126,'Archivo confirmacion abonos bancarios','Plantilla archivos para confirmacion abonos bancarios');

--2. Insertamos en Parametro

INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOVEDAD_CONFIRMACION_ABONO_BANCARIO','12126','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para confirmacion abonos bancarios');

--3. Insertamos en Constante

INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (N'FILE_DEFINITION_ID_NOVEDAD_CONFIRMACION_ABONO_BANCARIO', N'12126', N'Identificador de definición de archivo de componente FileProcessing para confirmacion abonos bancarios')

--4. Insertamos en FileDefinitionLoad

INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12126,'.','Archivo confirmacion abonos bancarios',NULL,NULL,0,NULL,NULL,0,12126);

--5. Insertamos en LineLoadCatalog

INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12126,'com.asopagos.novedades.personas.web.load.ConfirmacionAbonosBancariosPersistLine','Estructura del archivo de confirmacion abonos bancarios','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--6. Insertamos en LineDefinitionLoad

INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12126,NULL,NULL,1,NULL,1,12126,12126);

--7. Insertamos en ValidatorCatalog

INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12126,'com.asopagos.novedades.personas.web.load.validator.ConfirmacionAbonosBancariosLineValidator','Validador de línea de carga confirmacion abonos bancarios','Validador de línea de carga confirmacion abonos bancarios','LINE',NULL);

--8. Insertamos en ValidatorDefinition

INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12126,0,0,1,NULL,NULL,12126,12126);

--9. Insertamos en FieldLoadCatalog

--12126
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133164,'STRING','Tipo identificación del administrador de subsidio',NULL,NULL,'tipoIdenAdminSubsidio',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133165,'STRING','Número de identificación del administrador de subsidio',NULL,NULL,'numeroIdenAdminSubsidio',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133166,'STRING','Identificador transacción en cuenta de admin subsidio monetario',NULL,NULL,'casId',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133167,'STRING','Tipo de cuenta',NULL,NULL,'tipoCuenta',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133168,'STRING','Número de cuenta',NULL,NULL,'numeroCuenta',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133169,'STRING','Valor transferencia',NULL,NULL,'valorTransferencia',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133170,'STRING','Resultado Abono',NULL,NULL,'resultadoAbono',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133171,'STRING','Motivo',NULL,NULL,'motivoRechazoAbono',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133172,'STRING','Fecha y hora de la confirmación',NULL,NULL,'fechaConfirmacionAbono',9,0);

--10. Insertamos en FieldDefinitionLoad

--12126
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133164,NULL,NULL,NULL,'Tipo identificación del administrador de subsidio',0,0,0,32133164,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133165,NULL,NULL,NULL,'Número de identificación del administrador de subsidio',0,0,1,32133165,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133166,NULL,NULL,NULL,'Identificador transacción en cuenta de admin subsidio monetario',0,0,1,32133166,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133167,NULL,NULL,NULL,'Tipo de cuenta',0,0,0,32133167,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133168,NULL,NULL,NULL,'Número de cuenta',0,0,1,32133168,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133169,NULL,NULL,NULL,'Valor transferencia',0,0,1,32133169,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133170,NULL,NULL,NULL,'Resultado Abono',0,0,1,32133170,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133171,NULL,NULL,NULL,'Motivo',0,0,0,32133171,12126);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133172,NULL,NULL,NULL,'Fecha y hora de la confirmación',0,0,1,32133172,12126);

--11. Alter table ConsolaEstadoCargueMasivo
--ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo
--ALTER TABLE ConsolaEstadoCargueMasivo ADD CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo check (cecTipoProcesoMasivo in ('CARGUE_DE_AFILIACION_MULTIPLE_122','CARGUE_DE_PILA','CARGUE_DE_PILA_OF','CARGUE_DE_NOVEDAD_MULTIPLE_135','CARGUE_SUPERVIVENCIA','CARGUE_CERTIFICADOS_ESCOLARES','CARGUE_PENSIONADOS_BENEFICIARIOS','CARGUE_DE_NOVEDAD_ACTUALIZA_INFO','CARGUE_DE_ARCHIVO_DESCUENTOS','CARGUE_DE_ARCHIVO_RETIRO_TERCER_PAGADOR','CARGUE_CRUCE_FOVIS','CARGUE_MANUAL_DE_ARCH_TERCEROS_PAG','CARGUE_DE_NOVEDAD_RETIRO_TRABAJADORES','CARGUE_DE_NOVEDAD_REINTEGRO_TRABAJADORES','CARGUE_DE_NOVEDAD_ACTUALIZACION_SUCURSAL','CARGUE_DE_NOVEDAD_CONFIRMACION_AB'));

--12. se crear parametro para el control del cambio de medio de pago
insert into [Parametro] 
values('CONTROL_EJECUCION_ABONOS_AUTOMATICOS',1,0,'VALOR_GLOBAL_TECNICO','Variable que identifica si la caja de compesacion permite realizar el registro de la novedad de cambio de pago',null)

--13. se crea parametro para el sitio de pago 
insert into parametro 
values ('SITIO_DE_PAGO_CONFIRMACIÓN_CARGUE_MASIVO','SEVILLA (VALLE)',0,'CAJA_COMPENSACION','Indica el sitio de pago para la confirmación de abonos automaticos.','TEXT')
end;

--14. Actualización de valores para GLPI 67367

-- Eliminar obligatoriedad para campo Tipo Identificacion
update FieldDefinitionLoad set required=0 where id=32133164

-- Eliminar obligatoriedad para campo Tipo Cuenta
update FieldDefinitionLoad set required=0 where id=32133167