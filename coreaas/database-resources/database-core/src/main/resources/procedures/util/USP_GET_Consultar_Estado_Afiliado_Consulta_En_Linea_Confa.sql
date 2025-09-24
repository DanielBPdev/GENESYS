-- =============================================
-- Author:      Juan Montaña
-- Create date: 24/6/2022
-- Description: Return values for SERVICIO WEB Estado_Afiliado_Consulta_En_Linea_Confa
--
-- Parameters:
--   @tipoDocumento - Tipo identificación del Afiliado. Accepts nulls
--   @numeroDocumento - N° id del Afiliado. Accepts nulls
-- Returns:     Value of discount expressed as % (0-100)
-- =============================================

CREATE PROCEDURE USP_GET_Consultar_Estado_Afiliado_Consulta_En_Linea_Confa
				(@tipoDocumento VARCHAR (20),
				@numeroDocumento VARCHAR (16))

AS
IF EXISTS (SELECT TOP 1 * FROM Persona per
INNER JOIN Afiliado afi ON afi.afiPersona = per.perId
INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
WHERE per.perTipoIdentificacion = @tipoDocumento
AND per.perNumeroIdentificacion = @numeroDocumento)

BEGIN
DECLARE @personaAfi BIGINT, @afiliado BIGINT;

SET @personaAfi = (SELECT TOP 1 perId FROM Persona 
WHERE perNumeroIdentificacion = @numeroDocumento AND perTipoIdentificacion = @tipoDocumento);

SET @afiliado = (SELECT TOP 1 afiId FROM Afiliado WHERE afiPersona = @personaAfi);

WITH categoriaSub (catIdAfiliado, catTipoAfiliado, catId)
AS (SELECT catIdAfiliado, catTipoAfiliado, MAX (catId) 
FROM Categoria
WHERE catAfiliadoPrincipal = 1
AND (catIdAfiliado = @afiliado OR ISNULL (@afiliado, 0) = 0)
GROUP BY catIdAfiliado, catTipoAfiliado
),
clasificacion (sapRolAfiliado, solClasificacion, roaTipoAfiliado, sapId)
AS (SELECT sapRolAfiliado, solClasificacion, roaTipoAfiliado, sapId
	FROM Solicitud 
	INNER JOIN SolicitudAfiliacionPersona ON sapSolicitudGlobal = solId
	INNER JOIN RolAfiliado ON roaId = sapRolAfiliado
	WHERE sapEstadoSolicitud = 'CERRADA'
	AND solResultadoProceso = 'APROBADA'
	AND roaTipoAfiliado IN ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO')
	AND (roaAfiliado = @afiliado OR ISNULL (@afiliado, 0) = 0)
	GROUP BY sapRolAfiliado, solClasificacion, roaTipoAfiliado, sapId
),
clasificacionMax (roaId, roaTipoAfiliado, sapId)
AS (SELECT roaId, roaTipoAfiliado, MAX (sapId) AS sapId
	FROM Solicitud 
	INNER JOIN SolicitudAfiliacionPersona ON sapSolicitudGlobal = solId
	INNER JOIN RolAfiliado ON roaId = sapRolAfiliado
	WHERE sapEstadoSolicitud = 'CERRADA'
	AND solResultadoProceso = 'APROBADA'
	AND roaTipoAfiliado IN ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO')
	AND (roaAfiliado = @afiliado OR ISNULL (@afiliado, 0) = 0)
	GROUP BY roaId, roaTipoAfiliado
)


SELECT 'OK' resultado_consulta
, ISNULL (perAfi.perPrimerNombre, '') AS pri_nom_afiliado
, ISNULL (perAfi.perSegundoNombre, '') AS seg_nom_afiliado
, ISNULL (perAfi.perPrimerApellido, '') AS pri_ape_afiliado
, ISNULL (perAfi.perSegundoApellido, '') AS seg_ape_afiliado
, ISNULL (perAfi.perTipoIdentificacion, '') AS tipo_doc_afiliado
, ISNULL (perAfi.perNumeroIdentificacion, '') AS  num_doc_afiliado
, ISNULL (roa.roaEstadoAfiliado, '') AS estado_afiliado
, ISNULL (cat.catCategoriaPersona, '') AS categoria_afiliado
, ISNULL ((CASE WHEN roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	THEN roa.roaTipoAfiliado
	ELSE cla.solClasificacion
	END), '')
	AS clasificacion
, ISNULL (roa.roaClaseTrabajador, '') AS clase_trabajador
, ISNULL (roa.roaTipoAfiliado, '') AS tipo_afiliacion
, ISNULL (depAfi.depCodigo, '') AS cod_depto_ubicacion
, ISNULL (munAfi.munCodigo, '') AS cod_mun_ubicacion
, ISNULL (ubiAfi.ubiEmail, '') AS correo_afiliado
, ISNULL (CONVERT (VARCHAR (10), pedAfi.pedFechaNacimiento), '') AS fecha_nacimiento
, ISNULL (perEmp.perTipoIdentificacion, '') AS tipo_doc_emp
, ISNULL (perEmp.perNumeroIdentificacion, '') AS num_doc_emp
, ISNULL (empl.empEstadoEmpleador, '') AS estado_emp
, ISNULL (emp.empNombreComercial, '') AS nombre_comercial
FROM Afiliado afi
INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
INNER JOIN PersonaDetalle pedAfi ON pedAfi.pedPersona = perAfi.perId
INNER JOIN Ubicacion ubiAfi ON ubiAfi.ubiId = perAfi.perUbicacionPrincipal
INNER JOIN Municipio munAfi ON munAfi.munId = ubiAfi.ubiMunicipio
INNER JOIN Departamento depAfi ON depAfi.depId = munAfi.munDepartamento
--Dato registro rolAfiliado
LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
--Datos Empleador
LEFT JOIN Empleador empl ON empl.empId = roa.roaEmpleador
LEFT JOIN Empresa emp ON emp.empId = empl.empEmpresa
LEFT JOIN Persona perEmp ON perEmp.perId = emp.empPersona
-- Clasificacion
LEFT JOIN (SELECT cla.sapRolAfiliado, cla.roaTipoAfiliado, claMax.sapId, cla.solClasificacion
	FROM clasificacion cla
	INNER JOIN clasificacionMax claMax ON claMax.sapId = cla.sapId 
	AND claMax.roaId = cla.sapRolAfiliado AND claMax.roaTipoAfiliado = cla.roaTipoAfiliado)
	AS cla
ON cla.sapRolAfiliado = roa.roaId AND roa.roaTipoAfiliado = cla.roaTipoAfiliado
--Categorias
LEFT JOIN categoriaSub catSub ON catSub.catIdAfiliado = afi.afiId 
AND catSub.catTipoAfiliado = roa.roaTipoAfiliado
LEFT JOIN Categoria cat ON cat.catId = catSub.catId
WHERE (perAfi.perTipoIdentificacion = @tipoDocumento OR '0' = ISNULL(@tipoDocumento, 0))
AND (perAfi.perNumeroIdentificacion = @numeroDocumento OR '0' = ISNULL (@numeroDocumento, 0))
AND (roa.roaEstadoAfiliado = 'ACTIVO')

END
	ELSE
		BEGIN
		SELECT 'NO_EXISTE_EL_AFILIADO' AS resultado_consulta
		, ' ' AS pri_nom_afiliado
		, ' ' AS seg_nom_afiliado
		, ' ' AS pri_ape_afiliado
		, ' ' AS seg_ape_afiliado
		, ' ' AS tipo_doc_afiliado
		, ' ' AS  num_doc_afiliado
		, ' ' AS estado_afiliado
		, ' ' AS categoria_afiliado
		, ' ' AS clasificacion
		, ' ' AS clase_trabajador
		, ' ' AS tipo_afiliacion
		, ' ' AS cod_depto_ubicacion
		, ' ' AS cod_mun_ubicacion
		, ' ' AS correo_afiliado
		, ' ' AS fecha_nacimiento
		, ' ' AS tipo_doc_emp
		, ' ' AS num_doc_emp
		, ' ' AS estado_emp
		, ' ' AS nombre_comercial
		END;