--liquibase formatted sql

--changeset Heinsohn:LineDefinition runOnChange:true failOnError:false
-- TABLA LineDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla LineDefinition que representa las
 características administrables que tiene una línea de una definición de
 archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla LineDefinition que representa las
 características administrables que tiene una línea de una definición de
 archivo.<br/>(<i> Componente transversal HBT </i>)',
 @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
END CATCH;

--changeset Heinsohn:LineDefinition.lineOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al orden de la línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'lineOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al orden de la línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'lineOrder'
END CATCH;

--changeset Heinsohn:LineDefinition.generateLineFooter runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al indicador de generación de línea de footer.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'generateLineFooter'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al indicador de generación de línea de footer.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'generateLineFooter'
END CATCH;

--changeset Heinsohn:LineDefinition.generaterHeaderLine runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al indicador de generación de encabezado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'generaterHeaderLine'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al indicador de generación de encabezado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'generaterHeaderLine'
END CATCH;

--changeset Heinsohn:LineDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:LineDefinition.required runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la linea es requerida o no.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'required'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica si la linea es requerida o no.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'required'
END CATCH;

--changeset Heinsohn:LineDefinition.fileDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición de archivo asociada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la definición de archivo asociada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END CATCH;

--changeset Heinsohn:LineDefinition.lineCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al catálogo de línea asociado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'lineCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al catálogo de línea asociado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'lineCatalog_id'
END CATCH;

--changeset Heinsohn:LineDefinition.numberGroup runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al número de grupo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'numberGroup'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al número de grupo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'numberGroup'
END CATCH;

--changeset Heinsohn:LineDefinition.query runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'query'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'query'
END CATCH;

--changeset Heinsohn:LineDefinition.masterField runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del campo en la linea padre (maestro) a evaluar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterField'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre del campo en la linea padre (maestro) a evaluar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterField'
END CATCH;

--changeset Heinsohn:LineDefinition.masterFieldReference runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del campo en la linea hija (detalle) a evaluar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterFieldReference'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre del campo en la linea hija (detalle) a evaluar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterFieldReference'
END CATCH;

--changeset Heinsohn:LineDefinition.masterLine runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si es una linea maestra',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterLine'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica si es una linea maestra',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'masterLine'
END CATCH;

--changeset Heinsohn:LineDefinition.alternateDetail runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si las lineas hijas (detalle) se debe alternar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'alternateDetail'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica si las lineas hijas (detalle) se debe alternar',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'alternateDetail'
END CATCH;

--changeset Heinsohn:LineDefinition.parentLine runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Definición de linea padre en relación maestro detalle',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'parentLine'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Definición de linea padre en relación maestro detalle',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinition'
,@level2type=N'COLUMN', @level2name=N'parentLine'
END CATCH;


--changeset Heinsohn:LineCatalog runOnChange:true failOnError:false
-- TABLA LineCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla LineCatalog que representa las
 características que tiene una línea y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla LineCatalog que representa las
 características que tiene una línea y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
END CATCH;

--changeset Heinsohn:LineCatalog.queryType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'queryType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al tipo de consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'queryType'
END CATCH;

--changeset Heinsohn:LineCatalog.query runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'query'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'query'
END CATCH;

--changeset Heinsohn:LineCatalog.paginated runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al indicador de paginación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'paginated'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al indicador de paginación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'paginated'
END CATCH;

--changeset Heinsohn:LineCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:LineCatalog.className runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre de clase.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'className'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al nombre de clase.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'className'
END CATCH;

--changeset Heinsohn:LineCatalog.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la descripción.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END CATCH;

--changeset Heinsohn:LineCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al nombre',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END CATCH;

--changeset Heinsohn:LineCatalog.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador de la entidad propietaria',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END CATCH;

--changeset Heinsohn:GroupFieldCatalogs runOnChange:true failOnError:false
-- TABLA GroupFieldCatalogs
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de crear la tabla GroupFieldCatalogs que representa los grupos de catalogos disponibles.<br/>(<i> Componente transversal HBT </i>) ',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de crear la tabla GroupFieldCatalogs que representa los grupos de catalogos disponibles.<br/>(<i> Componente transversal HBT </i>) ',
 @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
END CATCH;

--changeset Heinsohn:GroupFieldCatalogs.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:GroupFieldCatalogs.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica el nombre de la agrupacion funcional de campos genericos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que indica el nombre de la agrupacion funcional de campos genericos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'name'
END CATCH;

--changeset Heinsohn:GroupFieldCatalogs.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que almacena la descricion de la agrupacion funcional de campos genericos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que almacena la descricion de la agrupacion funcional de campos genericos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFieldCatalogs'
,@level2type=N'COLUMN', @level2name=N'description'
END CATCH;


--changeset Heinsohn:FileGeneratedLog runOnChange:true failOnError:false
-- TABLA FileGeneratedLog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileGeneratedLog que representa las características del log de la generación de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileGeneratedLog que representa las características del log de la generación de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
END CATCH;

--changeset Heinsohn:FileGeneratedLog.fileGenerated_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al resultado de la generación asociado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'fileGenerated_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al resultado de la generación asociado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'fileGenerated_id'
END CATCH;

--changeset Heinsohn:FileGeneratedLog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FileGeneratedLog.lineNumber runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al número de línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'lineNumber'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al número de línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'lineNumber'
END CATCH;

--changeset Heinsohn:FileGeneratedLog.message runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al mensaje de log.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'message'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al mensaje de log.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGeneratedLog'
,@level2type=N'COLUMN', @level2name=N'message'
END CATCH;


--changeset Heinsohn:FileGenerated runOnChange:true failOnError:false
-- TABLA FileGenerated
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileGenerated que representa las características resultantes de la generación de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileGenerated que representa las características resultantes de la generación de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
END CATCH;

--changeset Heinsohn:FileGenerated.registersGenerated runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al número de registros generados generados.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'registersGenerated'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al número de registros generados generados.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'registersGenerated'
END CATCH;

--changeset Heinsohn:FileGenerated.formats runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a los formatos de archivo generados.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'formats'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a los formatos de archivo generados.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'formats'
END CATCH;

--changeset Heinsohn:FileGenerated.state runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al estado resultado de la generación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'state'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al estado resultado de la generación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'state'
END CATCH;

--changeset Heinsohn:FileGenerated.fileDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END CATCH;

--changeset Heinsohn:FileGenerated.userName runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre del usuario que generó el archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'userName'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al nombre del usuario que generó el archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'userName'
END CATCH;

--changeset Heinsohn:FileGenerated.finalDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha final de la generación del archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'finalDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la fecha final de la generación del archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'finalDate'
END CATCH;

--changeset Heinsohn:FileGenerated.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FileGenerated.initialDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha inicial de la generación del archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'initialDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la fecha inicial de la generación del archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileGenerated'
,@level2type=N'COLUMN', @level2name=N'initialDate'
END CATCH;


--changeset Heinsohn:FileDefinitionType runOnChange:true failOnError:false
-- TABLA FileDefinitionType
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileDefinitionType que representa las características administrables que tiene la definición de tipo de archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileDefinitionType que representa las características administrables que tiene la definición de tipo de archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
END CATCH;

--changeset Heinsohn:FileDefinitionType.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción de tipo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la descripción de tipo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'description'
END CATCH;

--changeset Heinsohn:FileDefinitionType.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FileDefinitionType.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre del tipo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al nombre del tipo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionType'
,@level2type=N'COLUMN', @level2name=N'name'
END CATCH;


--changeset Heinsohn:FileDefinition runOnChange:true failOnError:false
-- TABLA FileDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileDefinition que representa las
 características administrables que tiene un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileDefinition que representa las
 características administrables que tiene un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
END CATCH;

--changeset Heinsohn:FileDefinition.compressEachFile runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si se debe comprimir cada archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'compressEachFile'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica si se debe comprimir cada archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'compressEachFile'
END CATCH;

--changeset Heinsohn:FileDefinition.compressAll runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si se deben comprimir todos los archivos juntos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'compressAll'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica si se deben comprimir todos los archivos juntos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'compressAll'
END CATCH;

--changeset Heinsohn:FileDefinition.signerClass runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre cualificado de la clase que firma archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'signerClass'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre cualificado de la clase que firma archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'signerClass'
END CATCH;

--changeset Heinsohn:FileDefinition.signedFileExtension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Extensión con la cual se va a generar firmado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'signedFileExtension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Extensión con la cual se va a generar firmado.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'signedFileExtension'
END CATCH;

--changeset Heinsohn:FileDefinition.encrypterClass runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre cualificado de la clase que encripta archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'encrypterClass'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre cualificado de la clase que encripta archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'encrypterClass'
END CATCH;

--changeset Heinsohn:FileDefinition.encryptedFileExtension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre cualificado de la clase que encripta archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'encryptedFileExtension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre cualificado de la clase que encripta archivos.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'encryptedFileExtension'
END CATCH;

--changeset Heinsohn:FileDefinition.finalConditionsClass runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre cualificado de la clase que ejecuta las condiciones finales del proceso de generacion.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'finalConditionsClass'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre cualificado de la clase que ejecuta las condiciones finales del proceso de generacion.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'finalConditionsClass'
END CATCH;

--changeset Heinsohn:FileDefinition.nextLineSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Cadena que indica el separador o salto siguiente linea en archivos de texto',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'nextLineSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Cadena que indica el separador o salto siguiente linea en archivos de texto',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'nextLineSeparator'
END CATCH;

--changeset Heinsohn:FileDefinition.activeHistoric runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que se guardara un historico para la definicion de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'activeHistoric'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica que se guardara un historico para la definicion de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'activeHistoric'
END CATCH;

--changeset Heinsohn:FileDefinition.fileDefinitionType_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinitionType_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al tipo de definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinitionType_id'
END CATCH;

--changeset Heinsohn:FileDefinition.fileLocation_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'fileLocation_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al tipo de definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'fileLocation_id'
END CATCH;

--changeset Heinsohn:FileDefinition.registersBlockSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica cada cuantos registros se va a realizar la escritura del archivo en el disco para liberar memoria.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'registersBlockSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica cada cuantos registros se va a realizar la escritura del archivo en el disco para liberar memoria.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'registersBlockSize'
END CATCH;

--changeset Heinsohn:FileDefinition.decimalSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador decimal.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'decimalSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al caracter usado como separador decimal.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'decimalSeparator'
END CATCH;

--changeset Heinsohn:FileDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FileDefinition.nombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de una definición de Archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'nombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Indica el nombre de una definición de Archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'nombre'
END CATCH;

--changeset Heinsohn:FileDefinition.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador de la entidad propietaria.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END CATCH;

--changeset Heinsohn:FileDefinition.thousandsSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador de las unidades de mil.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'thousandsSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al caracter usado como separador de las unidades de mil.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinition'
,@level2type=N'COLUMN', @level2name=N'thousandsSeparator'
END CATCH;


--changeset Heinsohn:FieldGenericCatalog runOnChange:true failOnError:false
-- TABLA FieldGenericCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de crear la tabla FieldGenericCatalog que representa los catalogos genericos de campo disponibles.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de crear la tabla FieldGenericCatalog que representa los catalogos genericos de campo disponibles.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.Description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'Description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Descripcion del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'Description'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.label runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre a mostrar del FielGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'label'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Nombre a mostrar del FielGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'label'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tipo de dato del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Tipo de dato del FieldGenericCatalog.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'dataType'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.maxDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud máxima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'maxDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la longitud máxima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'maxDecimalSize'
END CATCH;

--changeset Heinsohn:FieldGenericCatalog.minDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud minima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'minDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la longitud minima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldGenericCatalog'
,@level2type=N'COLUMN', @level2name=N'minDecimalSize'
END CATCH;


--changeset Heinsohn:FieldDefinition runOnChange:true failOnError:false
-- TABLA FieldDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldDefinition que representa las características administrables que tiene un campo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FieldDefinition que representa las características administrables que tiene un campo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
END CATCH;

--changeset Heinsohn:FieldDefinition.footerOperation runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la operación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'footerOperation'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la operación.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'footerOperation'
END CATCH;

--changeset Heinsohn:FieldDefinition.generateEvidence runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al indicador para generar una evidencia.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'generateEvidence'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al indicador para generar una evidencia.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'generateEvidence'
END CATCH;

--changeset Heinsohn:FieldDefinition.fieldOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al orden de ubicación del campo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al orden de ubicación del campo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldOrder'
END CATCH;

--changeset Heinsohn:FieldDefinition.lineDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición de línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la definición de línea.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinition_id'
END CATCH;

--changeset Heinsohn:FieldDefinition.fieldCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica no administrable del campo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la característica no administrable del campo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldCatalog_id'
END CATCH;

--changeset Heinsohn:FieldDefinition.finalPosition runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición final.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'finalPosition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la posición final.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'finalPosition'
END CATCH;

--changeset Heinsohn:FieldDefinition.formatDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al formato de la fecha.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'formatDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al formato de la fecha.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'formatDate'
END CATCH;

--changeset Heinsohn:FieldDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FieldDefinition.initialPosition runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición inicial.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'initialPosition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la posición inicial.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'initialPosition'
END CATCH;

--changeset Heinsohn:FieldDefinition.label runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la etiqueta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'label'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la etiqueta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinition'
,@level2type=N'COLUMN', @level2name=N'label'
END CATCH;


--changeset Heinsohn:FieldCatalog runOnChange:true failOnError:false
-- TABLA FieldCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldCatalog que representa las características que tiene un campo y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FieldCatalog que representa las características que tiene un campo y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
END CATCH;

--changeset Heinsohn:FieldCatalog.roundMode runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al modo de redondear loa valores decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'roundMode'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al modo de redondear loa valores decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'roundMode'
END CATCH;

--changeset Heinsohn:FieldCatalog.paginated runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al indicador si las características son o no paginada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'paginated'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al indicador si las características son o no paginada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'paginated'
END CATCH;

--changeset Heinsohn:FieldCatalog.query runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'query'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la consulta.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'query'
END CATCH;

--changeset Heinsohn:FieldCatalog.idLineCatalog runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica de línea asociada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'idLineCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la característica de línea asociada.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'idLineCatalog'
END CATCH;

--changeset Heinsohn:FieldCatalog.label runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que se usa para sugerir el nombre del campo en el reporte.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'label'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que se usa para sugerir el nombre del campo en el reporte.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'label'
END CATCH;

--changeset Heinsohn:FieldCatalog.fieldGCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'FieldGeneticCatalog asociado a el FieldCatalog',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'fieldGCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'FieldGeneticCatalog asociado a el FieldCatalog',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'fieldGCatalog_id'
END CATCH;

--changeset Heinsohn:FieldCatalog.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de dato.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al tipo de dato.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'dataType'
END CATCH;

--changeset Heinsohn:FieldCatalog.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la descripción.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END CATCH;

--changeset Heinsohn:FieldCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde al identificador (llave primaria).',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END CATCH;

--changeset Heinsohn:FieldCatalog.maxDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud máxima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'maxDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la longitud máxima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'maxDecimalSize'
END CATCH;

--changeset Heinsohn:FieldCatalog.minDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud minima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'minDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde a la longitud minima de números decimales.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'minDecimalSize'
END CATCH;

--changeset Heinsohn:FieldCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde el nombre de la característica.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Atributo que corresponde el nombre de la característica.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END CATCH;


--changeset Heinsohn:ValidatorParamValue runOnChange:true failOnError:false
-- TABLA ValidatorParamValue
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ValidatorParamValue que representa las características que tiene un valor de parametro de un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ValidatorParamValue que representa las características que tiene un valor de parametro de un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
END CATCH;


--changeset Heinsohn:ValidatorParamValue.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador de la entidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador de la entidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ValidatorParamValue.value runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Valor del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN', @level2name=N'value'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Valor del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN',@level2name=N'value'
END CATCH;

--changeset Heinsohn:ValidatorParamValue.validatorParameter_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN', @level2name=N'validatorParameter_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN',@level2name=N'validatorParameter_id'
END CATCH;

--changeset Heinsohn:ValidatorParamValue.validatorDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN', @level2name=N'validatorDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParamValue'
,@level2type=N'COLUMN',@level2name=N'validatorDefinition_id'
END CATCH;


--changeset Heinsohn:ValidatorParameter runOnChange:true failOnError:false
-- TABLA ValidatorParameter
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ValidatorParameter que representa las características que tiene un parametro de un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla ValidatorParameter que representa las características que tiene un parametro de un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
END CATCH;

--changeset Heinsohn:ValidatorParameter.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador de la entidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador de la entidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ValidatorParameter.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:ValidatorParameter.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:ValidatorParameter.mask runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Mascara para el paraemtro.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'mask'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Mascara para el paraemtro.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'mask'
END CATCH;

--changeset Heinsohn:ValidatorParameter.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tipo de dato del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Tipo de dato del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'dataType'
END CATCH;

--changeset Heinsohn:ValidatorParameter.validatorCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el catalago.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN', @level2name=N'validatorCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Asociacion con el catalago.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorParameter'
,@level2type=N'COLUMN',@level2name=N'validatorCatalog_id'
END CATCH;


--changeset Heinsohn:ValidatorDefinition runOnChange:true failOnError:false
-- TABLA ValidatorDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ValidatorDefinition que representa las características administrables que tiene un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla ValidatorDefinition que representa las características administrables que tiene un validador.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.fileDefinitionLoad_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'file definition asociado al validador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinitionLoad_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'file definition asociado al validador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'fileDefinitionLoad_id'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.validatorOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que representa el orden del validador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'validatorOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que representa el orden del validador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'validatorOrder'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.stopProcess runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si el validador detiene o no el proceso de caraga.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'stopProcess'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si el validador detiene o no el proceso de caraga.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'stopProcess'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.fieldDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que rerpresenta la asociacion con definicion de campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que rerpresenta la asociacion con definicion de campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'fieldDefinition_id'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.lineDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que representa la asociacionc con definicion de linea' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que representa la asociacionc con definicion de linea' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'lineDefinition_id'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.validatorCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que represnta la realicion con el catalogo del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'validatorCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que represnta la realicion con el catalogo del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'validatorCatalog_id'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.excludeLine runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'excludeLine'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'excludeLine'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.validatorProfile runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'representa el identificador del perfil que agrupa los validadores que
 deben ejecutarse, valor por defecto null ejecuta todos los validadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'validatorProfile'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'representa el identificador del perfil que agrupa los validadores que
 deben ejecutarse, valor por defecto null ejecuta todos los validadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'validatorProfile'
END CATCH;

--changeset Heinsohn:ValidatorDefinition.state runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'indica si el validador se debe ejecutar (activo/inactivo)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN', @level2name=N'state'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'indica si el validador se debe ejecutar (activo/inactivo)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorDefinition'
,@level2type=N'COLUMN',@level2name=N'state'
END CATCH;


--changeset Heinsohn:ValidatorCatalog runOnChange:true failOnError:false
-- TABLA ValidatorCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ValidatorCatalog que representa las características que tiene un validador y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla ValidatorCatalog que representa las características que tiene un validador y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.className runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre de la clase que contiene la logica del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'className'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre de la clase que contiene la logica del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'className'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del validador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.scope runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'scope'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'scope'
END CATCH;

--changeset Heinsohn:ValidatorCatalog.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ValidatorCatalog'
,@level2type=N'COLUMN',@level2name=N'tenantId'
END CATCH;


--changeset Heinsohn:LineLoadCatalog runOnChange:true failOnError:false
-- TABLA LineLoadCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla LineCatalog que representa las características que tiene una línea y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla LineCatalog que representa las caracterí­sticas que tiene una línea y que no son administrables.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.lineOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Orden de linea' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'lineOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Orden de linea' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'lineOrder'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.lineSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Separador de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'lineSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Separador de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'lineSeparator'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.targetEntity runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad objetivo del cargue.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'targetEntity'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad objetivo del cargue.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'targetEntity'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.className runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre de clase.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'className'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre de clase.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'className'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:LineLoadCatalog.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'tenantId'
END CATCH;



--changeset Heinsohn:LineDefinitionLoad runOnChange:true failOnError:false
-- TABLA LineDefinitionLoad
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla LineDefinition que representa las características administrables que tiene una línea de una definición de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla LineDefinition que representa las características administrables que tiene una línea de una definición de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.identifier runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Caracter identificador de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'identifier'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Caracter identificador de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'identifier'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.fileDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'fileDefinition_id'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.lineLoadCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Catalogo de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'lineLoadCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Catalogo de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'lineLoadCatalog_id'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.required runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la linea es requerida o no.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'required'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si la linea es requerida o no.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'required'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.numberGroup runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el numero de grupo el cual pertenece el registro de Definición de
 Linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'numberGroup'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el numero de grupo el cual pertenece el registro de Definición de
 Linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'numberGroup'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.rollbackOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Representa el orden de ejecucion de los rollback en caso de usarse clases
 para ello' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'rollbackOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Representa el orden de ejecucion de los rollback en caso de usarse clases
 para ello' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'rollbackOrder'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.requiredInGroup runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la linea es requerida o no en cada iteración del grupo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'requiredInGroup'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si la linea es requerida o no en cada iteración del grupo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'requiredInGroup'
END CATCH;

--changeset Heinsohn:LineDefinitionLoad.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'LineDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;



--changeset Heinsohn:FileLoadedLog runOnChange:true failOnError:false
-- TABLA FileLoadedLog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada que representa las características del log de la carga de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada que representa las características del log de la carga de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
END CATCH;


--changeset Heinsohn:FileLoadedLog.fileLoaded_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Registro de carga de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN', @level2name=N'fileLoaded_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Registro de carga de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN',@level2name=N'fileLoaded_id'
END CATCH;

--changeset Heinsohn:FileLoadedLog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FileLoadedLog.lineNumber runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al número de línea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN', @level2name=N'lineNumber'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al número de línea..' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN',@level2name=N'lineNumber'
END CATCH;

--changeset Heinsohn:FileLoadedLog.message runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al mensaje de log.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN', @level2name=N'message'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al mensaje de log.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoadedLog'
,@level2type=N'COLUMN',@level2name=N'message'
END CATCH;



--changeset Heinsohn:FileLoaded runOnChange:true failOnError:false
-- TABLA FileLoaded
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada que representa las características del log de la generación o carga de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada que representa las características del log de la generación o carga de un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
END CATCH;

--changeset Heinsohn:FileLoaded.fileLoadedName runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica el nombre del archivo utilizado para realizar la carga.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'fileLoadedName'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica el nombre del archivo utilizado para realizar la carga.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'fileLoadedName'
END CATCH;

--changeset Heinsohn:FileLoaded.format runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Formato de archivo cargado.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'format'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Formato de archivo cargado.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'format'
END CATCH;

--changeset Heinsohn:FileLoaded.loadedRegisters runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Registros cargados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'loadedRegisters'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Registros cargados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'loadedRegisters'
END CATCH;

--changeset Heinsohn:FileLoaded.state runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'state'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'state'
END CATCH;

--changeset Heinsohn:FileLoaded.fileDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Definicion de linea asociada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Definicion de linea asociada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'fileDefinition_id'
END CATCH;

--changeset Heinsohn:FileLoaded.validatorProfile runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'representa el identificador del perfil que agrupa los validadores que deben ejecutarse, valor por defecto null ejecuta todos los validadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'validatorProfile'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'representa el identificador del perfil que agrupa los validadores que deben ejecutarse, valor por defecto null ejecuta todos los validadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'validatorProfile'
END CATCH;

--changeset Heinsohn:FileLoaded.finalDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha final de la generación del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'finalDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha final de la generación del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'finalDate'
END CATCH;

--changeset Heinsohn:FileLoaded.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FileLoaded.initialDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha inicial de la generación del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN', @level2name=N'initialDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la fecha inicial de la generación del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLoaded'
,@level2type=N'COLUMN',@level2name=N'initialDate'
END CATCH;



--changeset Heinsohn:FileDefinitionLoadType runOnChange:true failOnError:false
-- TABLA FileDefinitionLoadType
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileDefinitionType que representa las características administrables que tiene la definición de tipo de archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileDefinitionType que representa las características administrables que tiene la definición de tipo de archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
END CATCH;

--changeset Heinsohn:FileDefinitionLoadType.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción de tipo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción de tipo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:FileDefinitionLoadType.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FileDefinitionLoadType.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre del tipo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre del tipo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoadType'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;



--changeset Heinsohn:FileDefinitionLoad runOnChange:true failOnError:false
-- TABLA FileDefinitionLoad
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileDefinition que representa las características administrables que tiene un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileDefinition que representa las características administrables que tiene un archivo.<br/>(<i> Componente transversal HBT </i>)',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.registersRead runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica el valor de la cantidad de registros que se van a procesar, por lote.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'registersRead'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica el valor de la cantidad de registros que se van a procesar, por lote.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'registersRead'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.useCharacters runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica si se usaran caracteres identificadores de linea para
 el procesamiento y cargue' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'useCharacters'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que indica si se usaran caracteres identificadores de linea para
 el procesamiento y cargue' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'useCharacters'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.fileDefinitionType_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Instancia de la definicion de tipo de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'fileDefinitionType_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Instancia de la definicion de tipo de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'fileDefinitionType_id'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.sheetNumberXls runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el numero de la hoja donde se encuentran alojados los registros que representan a la parametrización de lina de un Archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'sheetNumberXls'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el numero de la hoja donde se encuentran alojados los registros que representan a la parametrización de lina de un Archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'sheetNumberXls'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.excludeLines runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si se debe cargar un archivo aunque tenga errores de lineas en las validaciones de estructura.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'excludeLines'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si se debe cargar un archivo aunque tenga errores de lineas en las validaciones de estructura.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'excludeLines'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.decimalSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador decimal.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'decimalSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador decimal.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'decimalSeparator'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.nombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de una definición de Archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'nombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de una definición de Archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'nombre'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'tenantId'
END CATCH;

--changeset Heinsohn:FileDefinitionLoad.thousandsSeparator runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador de las unidades de mil.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'thousandsSeparator'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al caracter usado como separador de las unidades de mil.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'thousandsSeparator'
END CATCH;



--changeset Heinsohn:FieldLoadCatalog runOnChange:true failOnError:false
-- TABLA FieldLoadCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldCatalog que representa las características que tiene un campo y que no son administrables.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldCatalog que representa las características que tiene un campo y que no son administrables.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.fieldOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Orden del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'fieldOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Orden del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'fieldOrder'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'tenantId'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.Description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'Description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripcion del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'Description'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tipo de dato del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Tipo de dato del FieldGenericCatalog.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'dataType'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.maxDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud máxima de números decimales.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'maxDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud máxima de números decimales.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'maxDecimalSize'
END CATCH;

--changeset Heinsohn:FieldLoadCatalog.minDecimalSize runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud minima de números decimales.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN', @level2name=N'minDecimalSize'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la longitud minima de números decimales.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldLoadCatalog'
,@level2type=N'COLUMN',@level2name=N'minDecimalSize'
END CATCH;



--changeset Heinsohn:FieldDefinitionLoad runOnChange:true failOnError:false
-- TABLA FieldDefinitionLoad
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldDefinition que representa las características administrables que tiene un campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FieldDefinition que representa las características administrables que tiene un campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.removeFormat runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Permite indicar si remover formato o no del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'removeFormat'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Permite indicar si remover formato o no del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'removeFormat'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.required runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si el campo es requerido o no.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'required'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si el campo es requerido o no.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'required'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.lineDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definicion de línea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'lineDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definicion de línea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'lineDefinition_id'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.fieldLoadCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica no administrable del campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'fieldLoadCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica no administrable del campo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'fieldLoadCatalog_id'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.identifierLine runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si el campo representa un identificador de línea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'identifierLine'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si el campo representa un identificador de línea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'identifierLine'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.finalPosition runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición final.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'finalPosition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición final.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'finalPosition'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.formatDate runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al formato de la fecha.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'formatDate'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al formato de la fecha.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'formatDate'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.initialPosition runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición inicial.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'initialPosition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la posición inicial.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'initialPosition'
END CATCH;

--changeset Heinsohn:FieldDefinitionLoad.label runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la etiqueta.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN', @level2name=N'label'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la etiqueta.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FieldDefinitionLoad'
,@level2type=N'COLUMN',@level2name=N'label'
END CATCH;



--changeset Heinsohn:GraphicFeature runOnChange:true failOnError:false
-- TABLA GraphicFeature
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla GraphicFeature que representa las características gráficas de una definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla GraphicFeature que representa las características gráficas de una definición de archivo.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
END CATCH;

--changeset Heinsohn:GraphicFeature.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción de la característica gráfica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción de la característica gráfica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:GraphicFeature.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:GraphicFeature.restrictions runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a las restricciones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'restrictions'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a las restricciones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'restrictions'
END CATCH;

--changeset Heinsohn:GraphicFeature.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de dato.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de dato.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'dataType'
END CATCH;

--changeset Heinsohn:GraphicFeature.defaultValue runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor por defecto de la característica gráfica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'defaultValue'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor por defecto de la característica gráfica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'defaultValue'
END CATCH;

--changeset Heinsohn:GraphicFeature.fileFormat runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al formato del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'fileFormat'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al formato del archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'fileFormat'
END CATCH;

--changeset Heinsohn:GraphicFeature.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeature'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;


--changeset Heinsohn:GraphicFeatureDefinition runOnChange:true failOnError:false
-- TABLA GraphicFeatureDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla GraphicFeatureDefinition que representa las características gráficas asociadas a una definición de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla GraphicFeatureDefinition que representa las características gráficas asociadas a una definición de archivo.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.value runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'value'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'value'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.fileDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición de archivo asociada.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'fileDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición de archivo asociada.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'fileDefinition_id'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.lineDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la definición de linea a la cual le va asociar la caracteristica grafica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la definición de linea a la cual le va asociar la caracteristica grafica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'lineDefinition_id'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.fieldDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la definición de Campo a la cual le va asociar la caracteristica grafica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la definición de Campo a la cual le va asociar la caracteristica grafica.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'fieldDefinition_id'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.graphicFeature_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica gráfica asociada.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'graphicFeature_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la característica gráfica asociada.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'graphicFeature_id'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.header runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al encabezado cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'header'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al encabezado cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'header'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.footer runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al pie de página cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'footer'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al pie de página cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'footer'
END CATCH;

--changeset Heinsohn:GraphicFeatureDefinition.detail runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al detalle cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN', @level2name=N'detail'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si la caracteristica grafica se le aplica al detalle cuando dicha caracteristica se aplica a una definición de linea.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GraphicFeatureDefinition'
,@level2type=N'COLUMN',@level2name=N'detail'
END CATCH;

--changeset Heinsohn:FileLocationCommon runOnChange:true failOnError:false
-- TABLA FileLocationCommon
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla FileLocationCommon que representa las características que tiene un campo y que no son administrables.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla FileLocationCommon que representa las características que tiene un campo y que no son administrables.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
END CATCH;

--changeset Heinsohn:FileLocationCommon.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:FileLocationCommon.protocolo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tipo de protoloco a usar.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'protocolo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Tipo de protoloco a usar.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'protocolo'
END CATCH;

--changeset Heinsohn:FileLocationCommon.host runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Dirección Ip ó nombre del host' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'host'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Dirección Ip ó nombre del host' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'host'
END CATCH;

--changeset Heinsohn:FileLocationCommon.puerto runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Puerto de conexion con el servidor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'puerto'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Puerto de conexion con el servidor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'puerto'
END CATCH;

--changeset Heinsohn:FileLocationCommon.remotePath runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo dentro del servidor.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'remotePath'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo dentro del servidor.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'remotePath'
END CATCH;

--changeset Heinsohn:FileLocationCommon.localPath runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo localmente (en caso de download)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'localPath'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo localmente (en caso de download)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'localPath'
END CATCH;

--changeset Heinsohn:FileLocationCommon.usuario runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Usuario en el servidor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'usuario'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Usuario en el servidor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'usuario'
END CATCH;

--changeset Heinsohn:FileLocationCommon.contrasena runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contrasena para ingresar al servidor (si es necesario)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'contrasena'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contrasena para ingresar al servidor (si es necesario)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'contrasena'
END CATCH;

--changeset Heinsohn:FileLocationCommon.overwriteFile runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contrasena para ingresar al servidor (si es necesario)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'overwriteFile'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contrasena para ingresar al servidor (si es necesario)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'overwriteFile'
END CATCH;

--changeset Heinsohn:FileLocationCommon.multiplesFiles runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica si se generan o no multiples archivos temporales ( xls )' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'multiplesFiles'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica si se generan o no multiples archivos temporales ( xls )' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'multiplesFiles'
END CATCH;

--changeset Heinsohn:FileLocationCommon.tempLocalPath runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo temporal localmente (para el caso de escritura parcial)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN', @level2name=N'tempLocalPath'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Ubicacion del archivo temporal localmente (para el caso de escritura parcial)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'FileLocationCommon'
,@level2type=N'COLUMN',@level2name=N'tempLocalPath'
END CATCH;


--changeset Heinsohn:ProcessorCatalog runOnChange:true failOnError:false
-- TABLA ProcessorCatalog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorCatalog que representa las características que tiene un procesador y que no son administrables.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description', 
@value=N'Entidad encargada de generar la tabla ProcessorCatalog que representa las características que tiene un procesador y que no son administrables.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.className runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre de clase.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'className'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre de clase.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'className'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.scope runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al alcance del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'scope'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al alcance del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'scope'
END CATCH;

--changeset Heinsohn:ProcessorCatalog.tenantId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN', @level2name=N'tenantId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la entidad propietaria.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorCatalog'
,@level2type=N'COLUMN',@level2name=N'tenantId'
END CATCH;



--changeset Heinsohn:ProcessorDefinition runOnChange:true failOnError:false
-- TABLA ProcessorDefinition
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorDefinition que representa las características administrables que tiene un procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorDefinition que representa las características administrables que tiene un procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.processorOrder runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al orden del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'processorOrder'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al orden del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'processorOrder'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.fieldDefinitionId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del fieldDefinition' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldDefinitionId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del fieldDefinition' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'fieldDefinitionId'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.fieldDefinitionLoadId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del fieldDefinitionLoad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'fieldDefinitionLoadId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del fieldDefinitionLoad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'fieldDefinitionLoadId'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.lineDefinitionId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del lineDefinition' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinitionId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del lineDefinition' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'lineDefinitionId'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.lineDefinitionLoadId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del lineDefinitionLoad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'lineDefinitionLoadId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del lineDefinitionLoad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'lineDefinitionLoadId'
END CATCH;

--changeset Heinsohn:ProcessorDefinition.processorCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al catálogo del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN', @level2name=N'processorCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al catálogo del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorDefinition'
,@level2type=N'COLUMN',@level2name=N'processorCatalog_id'
END CATCH;



--changeset Heinsohn:ProcessorParameter runOnChange:true failOnError:false
-- TABLA ProcessorParameter
BEGIN TRY
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorParameter que representa las características que tiene un parametro de un procesador.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
END TRY
BEGIN CATCH
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorParameter que representa las características que tiene un parametro de un procesador.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
END CATCH;

--changeset Heinsohn:ProcessorParameter.dataType runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de dato.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'dataType'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al tipo de dato..' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'dataType'
END CATCH;

--changeset Heinsohn:ProcessorParameter.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la descripción.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:ProcessorParameter.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:ProcessorParameter.mask runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la máscara de formato de dato.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'mask'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la máscara de formato de dato.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'mask'
END CATCH;

--changeset Heinsohn:ProcessorParameter.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:ProcessorParameter.processorCatalog_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al catálogo del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN', @level2name=N'processorCatalog_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al catálogo del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParameter'
,@level2type=N'COLUMN',@level2name=N'processorCatalog_id'
END CATCH;



--changeset Heinsohn:ProcessorParamValue runOnChange:true failOnError:false
-- TABLA ProcessorParamValue
BEGIN TRY
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorParamValue que representa las características que tiene un valor de parametro de un procesador.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
END TRY
BEGIN CATCH
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad encargada de generar la tabla ProcessorParamValue que representa las características que tiene un valor de parametro de un procesador.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
END CATCH;

--changeset Heinsohn:ProcessorParamValue.value runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor del parametro del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN', @level2name=N'value'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al valor del parametro del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN',@level2name=N'value'
END CATCH;

--changeset Heinsohn:ProcessorParamValue.processorDefinition_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN', @level2name=N'processorDefinition_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde a la definición del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN',@level2name=N'processorDefinition_id'
END CATCH;

--changeset Heinsohn:ProcessorParamValue.processorParameter_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al parametro del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN', @level2name=N'processorParameter_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al parametro del procesador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ProcessorParamValue'
,@level2type=N'COLUMN',@level2name=N'processorParameter_id'
END CATCH;


--changeset Heinsohn:GroupFC_FieldGC runOnChange:true failOnError:false
-- TABLA GroupFC_FieldGC
BEGIN TRY
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Tabla relacional entre las tablas FieldGenericCatalog y GroupFieldCatalogs.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
END TRY
BEGIN CATCH
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tabla relacional entre las tablas FieldGenericCatalog y GroupFieldCatalogs.',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
END CATCH;

--changeset Heinsohn:GroupFC_FieldGC.fieldGenericCatalogs_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la tabla FieldGenericCatalog (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
,@level2type=N'COLUMN', @level2name=N'fieldGenericCatalogs_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la tabla FieldGenericCatalog (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
,@level2type=N'COLUMN',@level2name=N'fieldGenericCatalogs_id'
END CATCH;

--changeset Heinsohn:GroupFC_FieldGC.groupFieldCatalogs_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la tabla GroupFieldCatalogs (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
,@level2type=N'COLUMN', @level2name=N'groupFieldCatalogs_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Atributo que corresponde al identificador de la tabla GroupFieldCatalogs (llave primaria).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'GroupFC_FieldGC'
,@level2type=N'COLUMN',@level2name=N'groupFieldCatalogs_id'
END CATCH;



--changeset Heinsohn:ParamValue runOnChange:true failOnError:false
-- TABLA ParamValue
BEGIN TRY
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Representa el valor del parametro',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
END TRY
BEGIN CATCH
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Representa el valor del parametro',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
END CATCH;

--changeset Heinsohn:ParamValue.groupFieldCatalogs_id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN', @level2name=N'parameter_id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN',@level2name=N'parameter_id'
END CATCH;

--changeset Heinsohn:ParamValue.entityEnterpriseId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de la empresa' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN', @level2name=N'entityEnterpriseId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de la empresa' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN',@level2name=N'entityEnterpriseId'
END CATCH;

--changeset Heinsohn:ParamValue.paramValue runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN', @level2name=N'paramValue'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del parametro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN',@level2name=N'paramValue'
END CATCH;

--changeset Heinsohn:ParamValue.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Llave primaria' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Llave primaria' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;



--changeset Heinsohn:Parameter runOnChange:true failOnError:false
-- TABLA Parameter
BEGIN TRY
EXEC sys.sp_updateextendedproperty 
@name=N'MS_Description',
@value=N'Representa un parametro',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
END TRY
BEGIN CATCH
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Representa un parametro',
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
END CATCH;

--changeset Heinsohn:Parameter.id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN', @level2name=N'id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'ParamValue'
,@level2type=N'COLUMN',@level2name=N'id'
END CATCH;

--changeset Heinsohn:Parameter.name runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN', @level2name=N'name'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN',@level2name=N'name'
END CATCH;

--changeset Heinsohn:Parameter.description runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Valor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN', @level2name=N'description'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Valor' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN',@level2name=N'description'
END CATCH;

--changeset Heinsohn:Parameter.type runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripcion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN', @level2name=N'type'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripcion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'Parameter'
,@level2type=N'COLUMN',@level2name=N'type'
END CATCH;
