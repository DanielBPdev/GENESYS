-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoDevolucionesUGPP
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
		INSERT rno.HistoricoDevolucionesUGPP(
			hduFechaHistorico,
			hduTipoDocumento,
			hduNumeroDocumento,
			hduRazonSocial,
			hduDireccion,
			hduMunicipio,
			hduDepartamento,
			hduConcepto,
			hduAnio,
			hduMes,
			hduSubsistemaDevolucion,
			hduNombreActo,
			hduNumeroActo,
			hduFechaActo,
			hduValor,
			hduNombreAdministradora,
			hduCodigoAdministradora,
			hduFechaInicialReporte,
			hduFechaFinalReporte)
		SELECT @fechaFin,
			CASE
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NIT'
			END tipoDocumento,
			per.perNumeroIdentificacion numeroDocumento,
			SUBSTRING (CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END, 0, 101) razonSocial, 
			SUBSTRING (ubi.ubiDireccionFisica, 1, 100) direccion,
			SUBSTRING (mun.munCodigo,3,6) municipio,
			dep.depCodigo departamento,
			SUBSTRING (dap.dapMotivoPeticion, 1, 200) concepto,
			DATEPART(YEAR, DATEADD(MILLISECOND, (CAST(dap.dapPeriodoReclamado AS BIGINT)) % 1000, DATEADD(SECOND, (CAST(dap.dapPeriodoReclamado AS BIGINT)) / 1000, '19700101'))) anio, 
			DATEPART(MONTH, DATEADD(MILLISECOND, (CAST(dap.dapPeriodoReclamado AS BIGINT)) % 1000, DATEADD(SECOND, (CAST(dap.dapPeriodoReclamado AS BIGINT)) / 1000, '19700101'))) mes,
			'CCF' subsistemaDevolucion,
			SUBSTRING (sol.solTipoTransaccion, 1, 100) nombreActo,
			sol.solNumeroRadicacion numeroActo,
			CONVERT(VARCHAR, moa.moaFechaCreacion, 5) fechaActo,
			CONVERT(NUMERIC(9, 0), (moa.moaValorAporte + moa.moaValorInteres)) valor,
			(SELECT SUBSTRING(prmValor, 1, 50) FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
			(SELECT SUBSTRING(cns.cnsValor,1,150) FROM dbo.Constante cns WHERE cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO') codigoAdministradora,
			@fechaInicio,
			@fechaFin
		FROM dbo.MovimientoAporte moa
		INNER JOIN dbo.DevolucionAporteDetalle dad ON dad.dadMovimientoAporte = moa.moaId
		INNER JOIN dbo.DevolucionAporte dap ON dad.dadDevolucionAporte = dap.dapId
		INNER JOIN dbo.SolicitudDevolucionAporte sda ON sda.sdaDevolucionAporte = dap.dapId
		INNER JOIN dbo.Solicitud sol ON sda.sdaSolicitudGlobal = sol.solId
		INNER JOIN dbo.AporteGeneral apg ON moa.moaAporteGeneral = apg.apgId 
		INNER JOIN dbo.Empresa emp ON apg.apgEmpresa = emp.empId
		INNER JOIN dbo.Persona per ON emp.empPersona = per.perId
		INNER JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		WHERE moa.moaTipoMovimiento = 'DEVOLUCION_APORTES'
		AND moa.moaFechaCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
		AND per.perTipoIdentificacion IN ('CEDULA_CIUDADANIA','NIT')
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
