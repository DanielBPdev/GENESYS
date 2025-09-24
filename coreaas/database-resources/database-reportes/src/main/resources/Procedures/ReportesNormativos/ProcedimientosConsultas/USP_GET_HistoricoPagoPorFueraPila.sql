-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte HistoricoDesagregadoCarteraAportante
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoPagoPorFueraPila
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoPagoPorFueraPila(
			hpfFechaHistorico,
			hpfTipoDocumento,
			hpfNumeroDocumento,
			hpfRazonSocial,
			hpfDireccion,
			hpfMunicipio,
			hpfDepartamento,
			hpfAnio,
			hpfMes,
			hpfSubsistema,
			hpfDiasCotizados,
			hpfIngresoBaseDeCotizacion,
			hpfCedulaCotizante,
			hpfNombreCotizante,
			hpfNovedad,
			hpfPlanilla,
			hpfFechaPago,
			hpfValor,
			hpfNombreAdministradora,
			hpfCodigoAdministradora,
			hpfFechaInicialReporte,
			hpfFechaFinalReporte)
			SELECT @fechaFin,
			CASE 
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC' --
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'-- 
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC' --
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' --
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' --
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NIT' --
			END tipoDocumento,
			per.perNumeroIdentificacion numeroDocumento, 
			SUBSTRING(CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END, 1, 100) razonSocial,
			SUBSTRING(ubi.ubiDireccionFisica, 1, 200) direccion,
			SUBSTRING(mun.munCodigo,3,6) municipio,
			dep.depCodigo departamento,
			DATEPART(YEAR, CONVERT(DATE, apg.apgPeriodoAporte + '-01',121)) anio,
			DATEPART(MONTH, CONVERT(DATE, apg.apgPeriodoAporte + '-01',121)) mes,
			'CAJA DE COMPENSACIÓN' subsistema,
			apd.apdDiasCotizados diasCotizados,
			CONVERT(NUMERIC(9, 0), apd.apdValorIBC) ingresoBaseDeCotizacion,
			pct.perNumeroIdentificacion cedulaCotizante,
			SUBSTRING(CASE WHEN ISNULL(pct.perPrimerNombre, '') = '' THEN pct.perRazonSocial
			ELSE (
				pct.perPrimerNombre +
				CASE WHEN pct.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pct.perSegundoNombre+' ' END +
				pct.perPrimerApellido +
				CASE WHEN pct.perSegundoApellido IS NULL THEN '' ELSE ' '+pct.perSegundoApellido END
			) END, 1, 100) nombreCotizante,
			SUBSTRING(apg.apgOrigenAporte, 1, 100) novedad,
			ISNULL(reg.regNumPlanilla,''),
			CONVERT(VARCHAR, apg.apgFechaProcesamiento, 5) fechaPago,
			CASE WHEN apd.apdId IS NOT NULL THEN CONVERT(NUMERIC(9, 0), (apd.apdAporteObligatorio + apd.apdValorIntMora)) ELSE CONVERT(NUMERIC(9, 0), (apg.apgValTotalApoObligatorio + apg.apgValorIntMora)) END valor,
			(SELECT SUBSTRING(prmValor, 1, 50) FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
			(SELECT SUBSTRING(cns.cnsValor, 1, 5) FROM dbo.Constante cns WHERE cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoAdministradora,
			@fechaInicio,
			@fechaFin
		FROM dbo.AporteGeneral apg
		LEFT JOIN dbo.AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId 
		LEFT JOIN dbo.Persona per ON apg.apgPersona = per.perId
		LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		LEFT JOIN dbo.Persona pct ON apd.apdPersona = pct.perId 
		LEFT JOIN dbo.RegistroGeneral reg ON reg.regId = apg.apgRegistroGeneral
		WHERE apg.apgPersona IS NOT NULL
		AND apg.apgModalidadRecaudoAporte != 'PILA'
		AND apg.apgModalidadRecaudoAporte != 'PILA_MANUAL'
		AND apg.apgFechaRecaudo BETWEEN @fechaInicio AND @fechaFin
		AND per.perTipoIdentificacion IN ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','NIT')
	UNION ALL
		SELECT @fechaFin,
			CASE 
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC' --
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'-- 
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC' --
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' --
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' --
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NIT' --
			END tipoDocumento, 
			per.perNumeroIdentificacion numeroDocumento, 
			SUBSTRING(CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END, 1, 100) razonSocial, 
			SUBSTRING(ubi.ubiDireccionFisica, 1, 200) direccion, 
			SUBSTRING(mun.munCodigo,3,6) municipio, 
			dep.depCodigo departamento, 
			DATEPART(YEAR, CONVERT(DATE, apg.apgPeriodoAporte + '-01',121)) anio, 
			DATEPART(MONTH, CONVERT(DATE, apg.apgPeriodoAporte + '-01',121)) mes, 
			'CAJA DE COMPENSACIÓN' subsistema, 
			apd.apdDiasCotizados diasCotizados, 
			CONVERT(NUMERIC(9, 0), apd.apdValorIBC) ingresoBaseDeCotizacion, 
			pct.perNumeroIdentificacion cedulaCotizante, 
			SUBSTRING(CASE WHEN ISNULL(pct.perPrimerNombre, '') = '' THEN pct.perRazonSocial
			ELSE (
				pct.perPrimerNombre +
				CASE WHEN pct.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pct.perSegundoNombre+' ' END +
				pct.perPrimerApellido +
				CASE WHEN pct.perSegundoApellido IS NULL THEN '' ELSE ' '+pct.perSegundoApellido END
			) END, 1, 100) nombreCotizante,
			'' novedad,
			ISNULL(reg.regNumPlanilla,'') numPlanilla,
			CONVERT(VARCHAR, apg.apgFechaProcesamiento, 5) fechaPago,
			CASE WHEN apd.apdId IS NOT NULL THEN CONVERT(NUMERIC(9, 0), (apd.apdAporteObligatorio + apd.apdValorIntMora)) ELSE CONVERT(NUMERIC(9, 0), (apg.apgValTotalApoObligatorio + apg.apgValorIntMora)) END valor,
			(SELECT SUBSTRING(prmValor, 1, 50) FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
			(SELECT SUBSTRING(cns.cnsValor, 1, 5) FROM dbo.Constante cns WHERE cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoAdministradora,
			@fechaInicio,
			@fechaFin
		FROM dbo.AporteGeneral apg
		LEFT JOIN dbo.AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
		LEFT JOIN dbo.Empresa emp ON apg.apgEmpresa = emp.empId
		LEFT JOIN dbo.Persona per ON emp.empPersona = per.perId
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN dbo.Ubicacion ubi ON ube.ubeUbicacion = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId
		LEFT JOIN dbo.Persona pct ON apd.apdPersona = pct.perId
		LEFT JOIN dbo.RegistroGeneral reg ON reg.regId = apg.apgRegistroGeneral
		WHERE apg.apgEmpresa IS NOT NULL
		AND apg.apgModalidadRecaudoAporte != 'PILA'
		AND apg.apgModalidadRecaudoAporte != 'PILA_MANUAL'
		AND apg.apgFechaRecaudo BETWEEN @fechaInicio AND @fechaFin
		AND per.perTipoIdentificacion IN ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','NIT')
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
