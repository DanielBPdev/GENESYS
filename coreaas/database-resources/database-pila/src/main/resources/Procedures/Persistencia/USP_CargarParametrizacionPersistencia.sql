-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2021/03/10
-- Description:	Procedimiento que carga las sentencias INSERT de tablas de PILA
-- sobre la tabla dbo.PilaPersistenciaClausulasInsert. Se debe ejecutar sobre una
-- tarea programada o en cada ejecución de liquibase de PILA
-- =============================================
CREATE PROCEDURE USP_CargarParametrizacionPersistencia (
	@CadenaTest VARCHAR(4) = ''
)

AS

	WITH cteCatalogParam
	AS
	(
		SELECT 
		CAST(cnsValor AS BIGINT) Valor,
		REPLACE(cnsNombre, 'FILE_DEFINITION_ID_PILA_', '') Nombre
		FROM core.Constante
		WHERE cnsNombre IN (
			'FILE_DEFINITION_ID_PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE',
			'FILE_DEFINITION_ID_PILA_INFORMACION_PENSIONADO',
			'FILE_DEFINITION_ID_PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE',
			'FILE_DEFINITION_ID_PILA_DETALLE_PENSIONADO'
		)
	),
	ctePrelim
	AS
	(
		SELECT REPLACE(llc.className,'com.asopagos.pila.persistencia.PersistirArchivo','PilaArchivo') AS Tabla, flld.dataType, flld.fieldOrder AS Orden, fldl.initialPosition, fldl.finalPosition AS finalPosition, flld.description, fldl.required, llc.id AS Sub
		FROM core.FileDefinitionLoad fdl
		INNER JOIN cteCatalogParam cteCp ON fdl.id = cteCp.valor
		INNER JOIN core.lineDefinitionLoad ldl ON fdl.id = ldl.fileDefinition_id
		INNER JOIN core.LineLoadCatalog llc ON ldl.lineLoadCatalog_id = llc.id
		INNER JOIN core.FieldDefinitionLoad fldl ON ldl.id = fldl.lineDefinition_id
		INNER JOIN core.FieldLoadCatalog flld ON fldl.FieldLoadCatalog_id = flld.id
		WHERE fdl.id IN (SELECT Valor FROM cteCatalogParam)	
	)
	UPDATE ppp
	SET
		pppTipoDato = cp.dataType,
		pppPosicionInicial = cp.initialPosition,
		pppPosicionFinal = cp.finalPosition,
		pppDescripcion = cp.description,
		pppEsRequerido = cp.required,
		pppSubStr = 'RTRIM(LTRIM(SUBSTRING(papTextoRegistro,' + CAST(cp.initialPosition+1 AS varchar(10)) + ', ' + CAST((cp.finalPosition - cp.initialPosition) AS varchar(10)) + ')))'	
	FROM ctePrelim cp
	INNER JOIN PilaPersistenciaParametrizacion ppp ON cp.Tabla = ppp.pppTabla AND cp.Orden = ppp.pppOrden AND cp.Sub = ppp.pppSubId 


	UPDATE dbo.PilaPersistenciaClausulasInsert
	SET ppcInsertClause = dbo.FN_CrearInsertPersistenciaPILA(ppcTabla)
;
