--------------- MASIVO TRANSFERENCIA
--begin transaction-- rollback
IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_NOVEDAD_MASIVA_CAMBIO_MEDIODEPAGO_TRANSFERENCIA')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) 
    VALUES ('FILE_DEFINITION_ID_NOVEDAD_MASIVA_CAMBIO_MEDIODEPAGO_TRANSFERENCIA',12131,'identificador archivo de componente FileProcessing para cargue cambio medio pago transferencia');
 END
--delete FileDefinitionLoadType where id>=12130
--delete FileDefinitionLoad where id>=12130
--delete LineDefinitionLoad where id>=12130
--delete LineLoadCatalog where id>=12130
--delete FileDefinitionLoad where id>=12130
--delete FieldDefinitionLoad where id>=400000445
--delete FieldLoadCatalog where id>=400000445
--delete ValidatorCatalog where id>=211079
--delete ValidatorParameter where id>=211382
--delete ValidatorDefinition where id>=2110361
--delete ValidatorParamValue where id>=2111775

--select top 10 * from FieldDefinitionLoad order by 1 desc
--Tabla FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,description,name) VALUES (12131,'Carga de archivo novedad masiva de cambio de medio de pago transferencia ','Plantilla cambio medio pago transferencia por ccf');
--Tabla FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalseparator,nombre,tenantid,thousandsseparator,excludelines,registersread,sheetnumberxls,usecharacters,filedefinitiontype_id) VALUES (12131,'.','Estructura del archivo  cambio medio pago transferencia',null,null,0,null,null,0,12131);
--PARAMETROS DE LA LINEA
--Tabla LineLoadCatalog
INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) VALUES (12131,'com.asopagos.novedades.personas.web.load.CambioMedioPagoTransferenciaPersistLine','Validador de archivo novedad masiva de cambio de medio de pago transferencia','Validador de archivo',null,1,'|',null);
--Tabla LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id) VALUES (12131,null,null,1,null,1,12131,12131);
--PARAMETROS DE LOS CAMPOS--
--Tabla FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000450,'STRING','Tipo de identificación del Afiliado principal',NULL,NULL,'tipoIdentificacionAfiliadoPrincipal',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000451,'STRING','Número de identificación del Afiliado principal',NULL,NULL,'numeroIdentificacionAfiliadoPrincipal',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000452,'STRING','Banco',NULL,NULL,'codigoBanco',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000453,'STRING','Tipo de cuenta',NULL,NULL,'tipoCuenta',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000454,'STRING','Número de cuenta',NULL,NULL,'numeroCuenta',5,0);

--Tabla FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000450,NULL,NULL,NULL,'Tipo de identificación del Afiliado principal',0,0,1,400000450,12131);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000451,NULL,NULL,NULL,'Número de identificación del Afiliado principal',0,0,1,400000451,12131);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000452,NULL,NULL,NULL,'Banco',0,0,1,400000452,12131);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000453,NULL,NULL,NULL,'Tipo de cuenta',0,0,1,400000453,12131);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000454,NULL,NULL,NULL,'Número de cuenta',0,0,1,400000454,12131);
--Validador De Nombre Archivo
INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) VALUES (211081,'com.asopagos.novedades.personas.web.load.validator.NovedadMasivaCambioMedioDePagoTransferenciaLineValidator','Validador de Linea archivo de transferencia','Validador de archivo','LINE',null);
--Tabla ValidatorParameter
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) VALUES (211383,'STRING','Expresión regular',NULL,'fileNamePattern',211081);
--Tabla ValidadorDefinition
INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id,validatorprofile,state) VALUES (2110363,1,NULL,1,NULL,NULL,12131,211081,1,1);
--Tabla ValidatorParamValue
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111776,'TRANSFERENCIA_CCF[0-9]{2}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',2110363,211383);

--commit
-- select * from FileDefinitionLoadType fl 
-- inner join FileDefinitionLoad fd on fl.id=fd.fileDefinitionType_id
-- inner join LineDefinitionLoad ld on ld.fileDefinition_id=fl.id
-- inner join LineLoadCatalog lg on lg.id=ld.lineLoadCatalog_id
-- inner join FieldDefinitionLoad fdl on fdl.lineDefinition_id=ld.id
-- inner join FieldLoadCatalog fcl on fcl.id=fdl.fieldLoadCatalog_id
-- inner join ValidatorDefinition vd on vd.lineDefinition_id=ld.id
-- --inner join ValidatorDefinition vd2 on vd2.fileDefinitionLoad_id=fd.id
-- inner join ValidatorCatalog vc on vc.id=vd.validatorCatalog_id
-- inner join ValidatorParameter vp on vp.validatorCatalog_id=vc.id
-- inner join ValidatorParamValue vpv on vpv.validatorParameter_id=vp.id
-- inner join ValidatorParamValue vpv2 on vpv2.validatorDefinition_id=vd.id
-- --where fd.id=12129
-- where fd.id= 12131 --12129


-- ========================================== 

insert into ParametrizacionNovedad(novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles)values
('CAMBIO_MEDIO_DE_PAGO_MASIVO_TRANSFERENCIA','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona','GENERAL','NOVEDADES_PERSONAS_PRESENCIAL',NULL)

CREATE TABLE CargueTrasladoMedioPagoTranferencia(
	ctmId bigint NOT NULL IDENTITY,
	ctmArchivoCargueTrasladoMedioPagoTranferencia bigint,
	ctmTipoIdentificacionAfiliadoPrincipal varchar(30) NOT NULL ,
	ctmNumeroIdentificacionAfiliadoPrincipal varchar(90) NOT NULL,    
	ctmCodigoBanco bigint NOT NULL,
	ctmTipoCuenta bigint NOT NULL,
	ctmNumeroCuenta bigint NOT NULL,
	ctmNumeroRadicado varchar(40),
	ctmResultado varchar(40),
	ctmOrigenRegistro varchar(40),
	ctmFechaHoraRegistro datetime2(7) NOT NULL,
	ctmCodigoIdentificacionECM varchar(200),

CONSTRAINT PK_CargueTrasladoMedioPagoTranferencia_ctmId PRIMARY KEY (ctmId));