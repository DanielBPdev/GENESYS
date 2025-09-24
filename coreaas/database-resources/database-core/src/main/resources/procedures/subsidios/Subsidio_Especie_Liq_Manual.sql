/****** Object:  StoredProcedure [dbo].[Subsidio_Especie_Liq_Manual]    Script Date: 29/07/2022 8:33:29 p. m. ******/

-- =============================================
-- Author:      Juan Monta�a
-- Create date: 11/5/2022
-- Description: Return values for SERVICIO WEB SUBSIDIO EN ESPECIE LIQUIDACI�N MANUAL - CONFA
--
-- Parameters:
--   @tipoDocumento - Tipo identificaci�n del Afiliado. Accepts nulls
--   @numeroDocumento - N� id del Afiliado. Accepts nulls
--   @@Periodo - periodo posible liquidaci�n asociada al afiliado. Accepts nulls
-- Returns:     Value of discount expressed as % (0-100)
-- =============================================

CREATE PROCEDURE Subsidio_Especie_Liq_Manual
				(@tipoDocumento VARCHAR (20),
				@numeroDocumento VARCHAR (16),
				@Periodo VARCHAR (7))

AS
IF EXISTS (SELECT TOP 1 * FROM Persona per
INNER JOIN Afiliado afi ON afi.afiPersona = per.perId
INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
WHERE per.perTipoIdentificacion = @tipoDocumento
AND per.perNumeroIdentificacion = @numeroDocumento)

BEGIN

DECLARE @personaAfi BIGINT, @afiliado BIGINT, @periodoConvert DATE;

SET @personaAfi = (SELECT TOP 1 perId FROM Persona 
WHERE perNumeroIdentificacion = @numeroDocumento AND perTipoIdentificacion = @tipoDocumento);

SET @afiliado = (SELECT TOP 1 afiId FROM Afiliado WHERE afiPersona = @personaAfi);

SET @periodoConvert = CONCAT (@Periodo, '-01');

WITH aportesDependiente (apgEmpresa, apdPersona, apgPeriodo)
AS (SELECT apg.apgEmpresa, apd.apdPersona, apg.apgPeriodoAporte
	FROM AporteDetallado apd 
	INNER JOIN AporteGeneral apg ON apg.apgId = apd.apdAporteGeneral
	WHERE apg.apgTipoSolicitante = 'EMPLEADOR'
	AND apdEstadoAporteAjuste = 'VIGENTE'
	AND apg.apgPeriodoAporte = @Periodo
	AND (apd.apdPersona = @personaAfi OR ISNULL(@personaAfi, 0) = 0)
	GROUP BY apg.apgEmpresa, apd.apdPersona, apg.apgPeriodoAporte
),
aportesIndPen (apdTipoCotizante, apdPersona/*, apgPeriodo*/)
AS (SELECT apd.apdTipoCotizante, apd.apdPersona--, apg.apgPeriodoAporte
	FROM AporteDetallado apd 
	INNER JOIN AporteGeneral apg ON apg.apgId = apd.apdAporteGeneral
	WHERE apd.apdTipoCotizante <> 'TRABAJADOR_DEPENDIENTE'
	AND apg.apgPeriodoAporte = @Periodo
	AND (apg.apgPersona = @personaAfi OR ISNULL(@personaAfi, 0) = 0)
	GROUP BY apd.apdTipoCotizante, apd.apdPersona--, apg.apgPeriodoAporte
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
),
carteraSub (carPersona)
AS (SELECT carPersona 
FROM Cartera 
WHERE carEstadoCartera = 'MOROSO'
AND carEstadoOperacion = 'VIGENTE'
GROUP BY carPersona
),
certificadoEscolar (cebId, cebBeneficiarioDetalle)
AS (SELECT MIN (cebId), cebBeneficiarioDetalle
FROM CertificadoEscolarBeneficiario
INNER JOIN beneficiarioDetalle ON bedId = cebBeneficiarioDetalle
INNER JOIN beneficiario ON benBeneficiarioDetalle = bedId
WHERE cebFechaCreacion <= dbo.getlocaldate()
AND cebFechaVencimiento >= dbo.getlocaldate()
AND cebFechaRecepcion <= dbo.getlocaldate()
AND (benAfiliado = @afiliado OR ISNULL (@afiliado, 0) = 0)
GROUP BY cebBeneficiarioDetalle
), 
categoriaSub (catIdAfiliado, catTipoAfiliado, catId)
AS (SELECT catIdAfiliado, catTipoAfiliado, MAX (catId) 
FROM Categoria
WHERE catAfiliadoPrincipal = 1
AND (catIdAfiliado = @afiliado OR ISNULL (@afiliado, 0) = 0)
GROUP BY catIdAfiliado, catTipoAfiliado
)


SELECT 'OK' resultado_consulta
, ISNULL (perAsu.perTipoIdentificacion, '') AS tipo_doc_adminsub
, ISNULL (perAsu.perNumeroIdentificacion, '') AS num_doc_adminsub
, ISNULL (perAsu.perPrimerNombre, '') AS pri_nom_adminsub
, ISNULL (perAsu.perSegundoNombre, '') AS seg_nom_admin_sub
, ISNULL (perAsu.perPrimerApellido, '') AS pri_ape_admin_sub
, ISNULL (perAsu.perSegundoApellido, '') AS seg_ape_adminsub
, ISNULL (ubiAsu.ubiEmail, '') AS corr_ben_pago
, ISNULL (ubiAsu.ubiTelefonoCelular, '') AS celular_ben_pago
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
, ISNULL (CONVERT (VARCHAR (10), pedAfi.pedFechaDefuncion), '') AS fecha_fall_trab
, ISNULL (depAfi.depCodigo, '') AS cod_depto_ubicacion
, ISNULL (munAfi.munCodigo, '') AS cod_mun_ubicacion
, ISNULL (ubiAfi.ubiEmail, '') AS correo_afiliado
, ISNULL ((CASE WHEN (apo.apdPersona IS NOT NULL OR apoIndPen.apdPersona IS NOT NULL) THEN 'SI' ELSE 'NO' END), '') AS aporte_pila
, ISNULL (pben.perTipoIdentificacion, '') AS tipo_doc_ben
, ISNULL (pben.perNumeroIdentificacion, '') AS num_doc_ben
, ISNULL (pben.perPrimerNombre, '') AS pri_nom_ben
, ISNULL (pben.perSegundoNombre, '') AS seg_nom_ben
, ISNULL (pben.perPrimerApellido, '') AS pri_ape_ben
, ISNULL (pben.perSegundoApellido, '') AS seg_ape_ben
, ISNULL (ben.benEstadoBeneficiarioAfiliado, '') AS estado_beneficiario
, ISNULL (coi.coiInvalidez, '') AS condicion_inv
, ISNULL ((DATEDIFF (YEAR,pedben.pedFechaNacimiento ,dbo.getlocaldate())), '') AS edad
, ISNULL (CONVERT (VARCHAR (10), pedben.pedFechaNacimiento), '') AS fecha_nacimiento
, ISNULL (grf.grfNumero, '') AS num_grupo_fam
, ISNULL (gra.graNombre, '') AS grado
, ISNULL (pedben.pedNivelEducativo, '') AS nivel_educativo
, ISNULL (sip.sipNombre, '') AS sitio_pago
, ISNULL (ben.benEstadoBeneficiarioAfiliado, '') AS estado_grupo_fam-- Pendiente eliminar de documentaci�n
, ISNULL (ben.benTipoBeneficiario, '') AS parentesco
--
, ISNULL (perPadre.perTipoIdentificacion, '') AS tipo_doc_otro_padre
, ISNULL (perPadre.perNumeroIdentificacion, '') AS num_doc_otro_padre
--
--No existentes
, ISNULL (CONVERT (VARCHAR (10), ben.benFechaAfiliacion), '') AS fecha_afiliacion_ben
, ISNULL (CONVERT (VARCHAR (10), ben.benFechaRetiro), '') AS fecha_retiro_ben
, ISNULL ((CONVERT (VARCHAR (4), ceb.cebFechaRecepcion)), '') AS anio_certificado

, ISNULL (perEmp.perTipoIdentificacion, '') AS tipo_doc_emp
, ISNULL (perEmp.perNumeroIdentificacion, '') AS num_doc_emp
, ISNULL (empl.empEstadoEmpleador, '') AS estado_emp
, ISNULL (emp.empNombreComercial, '') AS nombre_comercial
, ISNULL ((CASE WHEN car.carPersona IS NOT NULL THEN 'MOROSO' ELSE 'AL_DIA' END), '') AS estado_mora
, ISNULL (dsa.dsaEstado, '') AS estado_subsidio
, ISNULL ((CONCAT (SUBSTRING (CONVERT (VARCHAR (10),dsa.dsaPeriodoLiquidado), 1,4), 
	SUBSTRING (CONVERT (VARCHAR (10),dsa.dsaPeriodoLiquidado), 6,2))), '')
AS periodo_liquidado
, ISNULL ((CASE WHEN cer.cebId IS NOT NULL THEN 1 ELSE 0 END), '') AS certificado_vigente
, ISNULL (CONVERT (VARCHAR (10), ceb.cebFechaRecepcion), '') AS fecha_ini_cert
, ISNULL (CONVERT (VARCHAR (10), ceb.cebFechaVencimiento), '') AS fecha_fin_cert
, ISNULL (CONVERT (VARCHAR (10), CONVERT (DATE, dsa.dsaFechaHoraCreacion)), '') AS fecha_liquidacion
FROM Afiliado afi
INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
INNER JOIN PersonaDetalle pedAfi ON pedAfi.pedPersona = perAfi.perId
INNER JOIN Ubicacion ubiAfi ON ubiAfi.ubiId = perAfi.perUbicacionPrincipal
INNER JOIN Municipio munAfi ON munAfi.munId = ubiAfi.ubiMunicipio
INNER JOIN Departamento depAfi ON depAfi.depId = munAfi.munDepartamento
--
LEFT JOIN detalleSubsidioAsignado dsa ON afi.afiId = dsa.dsaAfiliadoPrincipal
LEFT JOIN beneficiarioDetalle bed ON bed.bedId = dsa.dsaBeneficiarioDetalle
LEFT JOIN PersonaDetalle pedBen ON pedBen.pedId = bed.bedPersonaDetalle
LEFT JOIN Persona pben ON pben.perId = pedBen.pedPersona
LEFT JOIN CondicionInvalidez coi ON coi.coiPersona = pben.perId
--Datos Administrador del subsidio
LEFT JOIN CuentaAdministradorSubsidio cas ON cas.casId = dsa.dsaCuentaAdministradorSubsidio
LEFT JOIN AdministradorSubsidio asu ON asu.asuId = cas.casAdministradorSubsidio
LEFT JOIN Persona perAsu ON perAsu.perId = asu.asuPersona
LEFT JOIN Ubicacion ubiAsu ON ubiAsu.ubiId = perAsu.perUbicacionPrincipal
--Datos Grupo familiar
LEFT JOIN GrupoFamiliar grf ON grf.grfId = dsa.dsaGrupoFamiliar
--Datos Beneficiario por afiliado
LEFT JOIN Beneficiario ben ON ben.benAfiliado = afi.afiId AND ben.benBeneficiarioDetalle = bed.bedId
--Datos Empleador
LEFT JOIN Empleador empl ON empl.empId = dsa.dsaEmpleador
LEFT JOIN Empresa emp ON emp.empId = empl.empEmpresa
LEFT JOIN Persona perEmp ON perEmp.perId = emp.empPersona
--Dato registro de afiliaci�n afiliado-Empleador
LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND ISNULL (roa.roaEmpleador, 0) = ISNULL (empl.empId, 0)
-- Clasificacion
LEFT JOIN (SELECT cla.sapRolAfiliado, cla.roaTipoAfiliado, claMax.sapId, cla.solClasificacion
	FROM clasificacion cla
	INNER JOIN clasificacionMax claMax ON claMax.sapId = cla.sapId 
	AND claMax.roaId = cla.sapRolAfiliado AND claMax.roaTipoAfiliado = cla.roaTipoAfiliado)
	AS cla
ON cla.sapRolAfiliado = roa.roaId AND roa.roaTipoAfiliado = cla.roaTipoAfiliado
--Aporte Pila
LEFT JOIN aportesDependiente apo ON apo.apgEmpresa = emp.empId AND apo.apdPersona = perAfi.perId
AND apo.apgPeriodo = CONVERT (varchar (7), dsa.dsaPeriodoLiquidado)
LEFT JOIN aportesIndPen apoIndPen ON apoIndPen.apdPersona = afi.afiPersona  
AND apoIndPen.apdTipoCotizante = roa.roaTipoAfiliado
--Grado academico
LEFT JOIN gradoAcademico gra ON graId = ben.benGradoAcademico
--Sitio de pago
LEFT JOIN sitioPago sip ON sip.sipId = cas.casSitioDePago
--Estado Cartera
LEFT JOIN carteraSub car ON car.carPersona = emp.empPersona
--Certificado Escolar
LEFT JOIN certificadoEscolar cer ON cer.cebBeneficiarioDetalle = bed.bedId
LEFT JOIN CertificadoEscolarBeneficiario ceb ON ceb.cebId = cer.cebId
--Categorias
LEFT JOIN categoriaSub catSub ON catSub.catIdAfiliado = afi.afiId 
AND catSub.catTipoAfiliado = roa.roaTipoAfiliado
LEFT JOIN Categoria cat ON cat.catId = catSub.catId
-- Otros padres-Padre
LEFT JOIN Persona perPadre ON perPadre.perId = pedBen.pedPersonaMadre OR perPadre.perId = pedBen.pedPersonaPadre
WHERE (perAfi.perTipoIdentificacion = @tipoDocumento OR '0' = ISNULL(@tipoDocumento, 0))
AND (perAfi.perNumeroIdentificacion = @numeroDocumento OR '0' = ISNULL (@numeroDocumento, 0))
AND (dsa.dsaPeriodoLiquidado = @periodoConvert OR ISNULL (dsa.dsaPeriodoLiquidado, '1900-01-01') = '1900-01-01');
END
	ELSE
		BEGIN
		SELECT 'NO_EXISTE_EL_AFILIADO' AS resultado_consulta
		, '' AS tipo_doc_adminsub
		, '' AS num_doc_adminsub
		, '' AS pri_nom_adminsub
		, '' AS seg_nom_admin_sub
		, '' AS pri_ape_admin_sub
		, '' AS seg_ape_adminsub
		, '' AS corr_ben_pago
		, '' AS celular_ben_pago
		, '' AS pri_nom_afiliado
		, '' AS seg_nom_afiliado
		, '' AS pri_ape_afiliado
		, '' AS seg_ape_afiliado
		, '' AS tipo_doc_afiliado
		, '' AS num_doc_afiliado
		, '' AS estado_afiliado
		, '' AS categoria_afiliado
		, '' AS clasificacion
		, '' AS clase_trabajador
		, '' AS tipo_afiliacion
		, '' AS fecha_fall_trab
		, '' AS cod_depto_ubicacion
		, '' AS cod_mun_ubicacion
		, '' AS correo_afiliado
		, '' AS aporte_pila
		, '' AS tipo_doc_ben
		, '' AS num_doc_ben
		, '' AS pri_nom_ben
		, '' AS seg_nom_ben
		, '' AS pri_ape_ben
		, '' AS seg_ape_ben
		, '' AS estado_beneficiario
		, '' AS condicion_inv
		, '' AS edad
		, '' AS fecha_nacimiento
		, '' AS num_grupo_fam
		, '' AS grado
		, '' AS nivel_educativo
		, '' AS sitio_pago
		, '' AS estado_grupo_fam
		, '' AS parentesco
		, '' AS tipo_doc_otro_padre
		, '' AS num_doc_otro_padre
		, '' AS fecha_afiliacion_ben
		, '' AS fecha_retiro_ben
		, '' AS anio_certificado
		, '' AS tipo_doc_emp
		, '' AS num_doc_emp
		, '' AS estado_emp
		, '' AS nombre_comercial
		, '' AS estado_mora
		, '' AS estado_subsidio
		, '' AS periodo_liquidado
		, '' AS certificado_vigente
		, '' AS fecha_ini_cert
		, '' AS fecha_fin_cert
		, '' AS fecha_liquidacion
		END;



