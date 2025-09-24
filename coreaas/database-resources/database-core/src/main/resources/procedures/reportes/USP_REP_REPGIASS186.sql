/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS186]    Script Date: 2023-10-13 8:41:03 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS186]    Script Date: 2023-09-27 11:08:46 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS186]    Script Date: 2023-09-27 8:56:55 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS186]    Script Date: 2023-09-20 9:08:09 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS186]    Script Date: 2023-09-19 8:30:16 AM ******/

-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 186
---AFILIADOS BENEFICIARIOS
-- =============================================
CREATE OR ALTER   PROCEDURE [dbo].[USP_REP_REPGIASS186]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON

--REPORTE 186 GIASS
--Calculo de las fechas de reporte
DECLARE @fecha DATE = dbo.getLocalDate(),
		@fechaHora DATETIME ;
SET @fechaHora = DATEADD (HOUR,16,CONVERT (DATETIME,@fecha)); 

SELECT DISTINCT
CASE ISNULL(pe.perTipoIdentificacion , pa.perTipoIdentificacion)---SE ADICIONA pa.perTipoIdentificacion para independiente y pensionados YA QUE ES UN CAMPO OBLIGATORIO EL DIA 20210518
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
	AS 'C8',
ISNULL (pe.perNumeroIdentificacion, pa.perNumeroIdentificacion) AS 'C9',
CASE pa.perTipoIdentificacion WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
AS 'C47',
pa.perNumeroIdentificacion AS 'C48',
CASE ben.benTipoBeneficiario WHEN 'CONYUGE' THEN 1
		WHEN 'HIJO_ADOPTIVO' THEN 2
		WHEN 'HIJO_BIOLOGICO' THEN 2
		WHEN 'MADRE' THEN 3
		WHEN 'PADRE' THEN 3
		WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN 4
		WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN 8
		WHEN 'HIJASTRO' THEN 9
		END AS 'C745',
CASE pb.perTipoIdentificacion WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''

	END 
AS 'C94',
pb.perNumeroIdentificacion AS 'C95',
CASE WHEN ben.benTipoBeneficiario = 'CONYUGE' THEN 0 ELSE 1 END AS 'C567',-- Indicar ¿Cómo se realiza este calculo?
ISNULL (CONVERT (VARCHAR (10), (dsa.dsaPeriodoLiquidado)), '') AS 'C861',
CASE	WHEN (ben.benTipoBeneficiario NOT IN ('PADRE', 'MADRE', 'CONYUGE')) AND (ceb.cebBeneficiarioDetalle IS NOT NULL) THEN 'E'
		WHEN coi.coiInvalidez = 1 THEN 'D'
		ELSE ''
		END AS 'C103',
dep.depCodigo AS 'C194',
RIGHT (mun.munCodigo, 3) AS 'C195',
SUBSTRING (REPLACE(ubi.ubiDireccionFisica,',',''), 1, 40) AS 'C161',
concat (cast(ben.benFechaAfiliacion AS VARCHAR (10)),';') AS 'C523'
FROM beneficiario ben 
INNER JOIN persona pb ON pb.perId = ben.benPersona
INNER JOIN afiliado afi ON afi.afiId = ben.benAfiliado
INNER JOIN rolAfiliado roa ON roa.roaAfiliado = afi.afiId
INNER JOIN persona pa ON pa.perId = afi.afiPersona
INNER JOIN SolicitudAfiliacionPersona WITH(NOLOCK) 
        ON sapRolAfiliado = roa.roaid AND sapEstadoSolicitud= 'CERRADA'
INNER JOIN Solicitud s WITH(NOLOCK) on sapSolicitudGlobal= s.solid AND s.solResultadoProceso = 'APROBADA'  
LEFT JOIN empleador em ON em.empId = roa.roaEmpleador
LEFT JOIN empresa e ON e.empId = em.empEmpresa
LEFT JOIN persona pe ON pe.perId = e.empPersona
---afiliacion empresa
--join SolicitudAfiliaciEmpleador sae on em.empId=sae.saeEmpleador
--join solicitud s2 on s2.solId=sae.saeSolicitudGlobal
-- Identificador de cuota monetaria
LEFT JOIN (SELECT * FROM (
			SELECT CASE WHEN MAX (dsaPeriodoLiquidado) OVER (PARTITION BY dsaEmpleador, dsaAfiliadoPrincipal, dsaBeneficiarioDetalle) = dsaPeriodoLiquidado THEN dsaPeriodoLiquidado ELSE NULL END AS dsaPeriodoLiquidado,
			dsaEmpleador, dsaAfiliadoPrincipal, dsaBeneficiarioDetalle
			FROM detalleSubsidioAsignado) AS X 
			WHERE X.dsaPeriodoLiquidado IS NOT NULL
		) AS dsa
ON dsa.dsaEmpleador = em.empId AND dsa.dsaAfiliadoPrincipal = afi.afiId AND dsa.dsaBeneficiarioDetalle = ben.benBeneficiarioDetalle
--Condicion Discapacidad  Beneficiario
LEFT JOIN condicionInvalidez coi ON coi.coiPersona = pb.perId
-- Condicion Escolaridad Beneficiario
LEFT JOIN 
(SELECT DISTINCT T.cebBeneficiarioDetalle
FROM certificadoEscolarBeneficiario T
WHERE T.cebFechaRecepcion < @fecha
AND T.cebFechaVencimiento >= @fecha) AS ceb 
ON ceb.cebBeneficiarioDetalle = ben.benBeneficiarioDetalle
--Ubicación Beneficiario
LEFT JOIN grupoFamiliar grf ON grf. grfId = ben.benGrupoFamiliar
LEFT JOIN ubicacion ubi ON ubi.ubiId = grf.grfUbicacion
LEFT JOIN municipio mun ON mun.munId = ubi.ubiMunicipio
LEFT JOIN departamento dep ON dep.depId = mun.munDepartamento

--Reporte con corte semanal
WHERE --benFechaAfiliacion <= @fechaHora AND (benEstadoBeneficiarioAfiliado = 'ACTIVO' OR (benEstadoBeneficiarioAfiliado = 'INACTIVO' AND benFechaRetiro > @fechaHora))
--AND --GLPI 	 
benEstadoBeneficiarioAfiliado IS NOT NULL
AND ben.benFechaAfiliacion IS NOT NULL
and sapEstadoSolicitud = 'CERRADA' AND s.solResultadoProceso = 'APROBADA'
AND roa.roaEstadoAfiliado IN ( 'ACTIVO','INACTIVO')
and dsa.dsaPeriodoLiquidado>=dateadd(month,-18,dbo.GetLocalDate())
--and s2.solResultadoProceso='APROBADA' and sae.saeEstadoSolicitud='CERRADA'
--and bengrupofamiliar is not null 
--Reporte Sin corte minimo Semanal
--WHERE ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
--AND ben.benFechaAfiliacion < @fechaHora

--([perTipoIdentificacion]='PERM_ESP_PERMANENCIA' 
--OR [perTipoIdentificacion]='SALVOCONDUCTO' 
--OR [perTipoIdentificacion]='NIT' 
--OR [perTipoIdentificacion]='CARNE_DIPLOMATICO'
--OR [perTipoIdentificacion]='PASAPORTE' 
--OR [perTipoIdentificacion]='CEDULA_EXTRANJERIA'
--OR [perTipoIdentificacion]='CEDULA_CIUDADANIA' 
--OR [perTipoIdentificacion]='TARJETA_IDENTIDAD' 
--OR [perTipoIdentificacion]='REGISTRO_CIVIL')

--([benTipoBeneficiario]='BENEFICIARIO_EN_CUSTODIA' --8 ¿A que código va amarrado?
--OR [benTipoBeneficiario]='HERMANO_HUERFANO_DE_PADRES' --4
--OR [benTipoBeneficiario]='HIJASTRO' --9
--OR [benTipoBeneficiario]='HIJO_ADOPTIVO'--2 
--OR [benTipoBeneficiario]='HIJO_BIOLOGICO' --2
--OR [benTipoBeneficiario]='MADRE' --3
--OR [benTipoBeneficiario]='PADRE' --3
--OR [benTipoBeneficiario]='CONYUGE')--1
END