-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/22
-- Description:	Inserta datos para reporte HistoricoAfiliadosACargo
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoAfiliadosVivienda
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
		INSERT rno.HistoricoAfiliadosVivienda(
			havFechaHistorico,
		  	havNitEntidad,
			havNumeroDocAfiliado,
			havApellidos,
			havNombres,
			havNombreEntidad,
			havFechaAfiliacion,
			havSalario,
			havTipoDocAfiliado,
			havFechaInicialReporte,
			havFechaFinalReporte)
		SELECT @fechaFin,
				REPLACE(nitEntidad,'-',''),
				numeroDocAfiliado,
				apellidos,
				nombres,
				nombreEntidad,
				CONVERT(VARCHAR,fechaAfiliacion,111) fechaAfiliacion,
				CASE WHEN apdSalarioBasico IS NOT NULL THEN apdSalarioBasico
					WHEN roaValorSalarioMesadaIngresos IS NOT NULL THEN roaValorSalarioMesadaIngresos
					WHEN apdValorIBC IS NOT NULL THEN apdValorIBC 
				END salario,
				CASE tipoDocAfiliado WHEN 'CEDULA_CIUDADANIA' THEN '1' WHEN 'CEDULA_EXTRANJERIA' THEN '2' END tipoDocAfiliado,
				@fechaInicio,
				@fechaFin
			FROM(
				SELECT prmNit.prmValor nitEntidad,
				perAfi.perNumeroIdentificacion numeroDocAfiliado,
				RTrim(Coalesce(perAfi.perPrimerApellido + ' ', '')
				    + Coalesce(perAfi.perSegundoApellido, '')) apellidos,
				RTrim(Coalesce(perAfi.perPrimerNombre + ' ', '')
				    + Coalesce(perAfi.perSegundoNombre, '')) nombres,
				prmNombre.prmValor nombreEntidad,
				ultimoEstadoActivo.fechaCambio fechaAfiliacion,
				SUM(apd.apdSalarioBasico) apdSalarioBasico,
				SUM(roa.roaValorSalarioMesadaIngresos) roaValorSalarioMesadaIngresos,
				SUM(apd.apdValorIBC) apdValorIBC,
				perAfi.perTipoIdentificacion tipoDocAfiliado,
				MAX(apg.apgId) maxAporte,
				roa.roaTipoAfiliado
				FROM Afiliado afi 
				INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
				INNER JOIN PersonaDetalle pedAfi ON pedAfi.pedPersona = perAfi.perId
				INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
				LEFT JOIN Empleador empl ON empl.empId = roa.roaEmpleador
				LEFT JOIN Empresa emp ON emp.empId = empl.empEmpresa
				INNER JOIN Parametro prmNit ON prmNit.prmNombre = 'NUMERO_ID_CCF'
				INNER JOIN Parametro prmNombre ON prmNombre.prmNombre = 'NOMBRE_CCF'
				INNER JOIN (SELECT MAX(eacId) id,
									MAX(eacFechaCambioEstado) fechaCambio, 
									eacPersona 
							FROM EstadoAfiliacionPersonaCaja 
							WHERE --eacFechaCambioEstado BETWEEN @fechaInicio AND @fechaFin 
							   eacEstadoAfiliacion = 'ACTIVO'
							  GROUP BY eacPersona) ultimoEstadoActivo ON perAfi.perId = ultimoEstadoActivo.eacPersona
				LEFT JOIN AporteGeneral apg ON apg.apgPeriodoAporte = CAST(YEAR(@fechaFin) AS VARCHAR(4)) + '-' + REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaFin) AS VARCHAR))) + CAST(MONTH(@fechaFin) AS VARCHAR)
											AND ((apg.apgEmpresa IS NOT NULL AND emp.empId IS NOT NULL AND apg.apgEmpresa = emp.empId)
											OR (apg.apgPersona IS NOT NULL AND apg.apgPersona = afi.afiPersona))
				LEFT JOIN AporteDetallado apd ON apg.apgId = apd.apdAporteGeneral AND apd.apdPersona = perAfi.perId
				WHERE roa.roaEstadoAfiliado = 'ACTIVO'
				  AND perAfi.perTipoIdentificacion IN ('CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA')
				GROUP BY prmNit.prmValor,
				perAfi.perNumeroIdentificacion,
				perAfi.perPrimerApellido,
				perAfi.perSegundoApellido,
				perAfi.perPrimerNombre,
				perAfi.perSegundoNombre,
				prmNombre.prmValor,
				ultimoEstadoActivo.fechaCambio,
				perAfi.perTipoIdentificacion,
				roa.roaTipoAfiliado
			) AUS
			WHERE (AUS.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	  		OR (AUS.roaTipoAfiliado = 'PENSIONADO' AND AUS.maxAporte IS NOT NULL))
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
