/****** Object:  StoredProcedure [dbo].[USP_GET_RegistroUnicoEmpleadores]    Script Date: 6/06/2024 9:00:18 a. m. ******/-- 
--=============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE OR ALTER PROCEDURE USP_GET_HistoricoRegistroUnicoEmpleadores
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
		INSERT rno.HistoricoRegistroUnicoEmpleadores(
			hruFechaHistorico,
			hruAportante,
			hruTipoDocumento,
			hruPerNumeroIdentificacion,
			hruRepresentanteLegal,
			hruDireccion,
			hruMunicipio,
			hruDepartamento,
			hruDivipola,
			hruTelefono,
			hruCorreoElectronico,
			hruPaginaWeb,
			hruTrabajadoresA,
			hruTrabajadoresB,
			hruTrabajadoresC,
			hruTotalTrabajadores,
			hruCiiCodigo,
			hruResponsableAfiliaciones,
			hruTelefonoFijoResponsable,
			hruTelefonoCelularResponsable,
			hruEMailResponsable,
			hruFechaInicialReporte,
			hruFechaFinalReporte)
		SELECT @fechaFin,
			CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END aportante,
			CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 1 ELSE 2 END tipoDocumento,
			per.perNumeroIdentificacion,
			CASE WHEN ISNULL(prl.perPrimerNombre, '') = '' THEN prl.perRazonSocial
			ELSE (
				prl.perPrimerNombre + 
				CASE WHEN prl.perSegundoNombre IS NULL THEN ' ' ELSE ' '+prl.perSegundoNombre+' ' END +
				prl.perPrimerApellido + 
				CASE WHEN prl.perSegundoApellido IS NULL THEN '' ELSE ' '+prl.perSegundoApellido END
			) END representanteLegal,
			TRIM(ubi.ubiDireccionFisica) direccion, 
			mun.munNombre municipio, dep.depNombre departamento, mun.munCodigo divipola,
			ubi.ubiTelefonoFijo telefono, ubi.ubiEmail correoElectronico, emp.empPaginaWeb paginaWeb,
			ISNULL(categorias.cantidadA, 0) trabajadoresA,
			ISNULL(categorias.cantidadB, 0) trabajadoresB,
			ISNULL(categorias.cantidadC, 0) trabajadoresC,
			ISNULL(categorias.cantidadTotal, 0) totalTrabajadores, 
			cii.ciiCodigo,
			CASE WHEN ISNULL(pce.perPrimerNombre, '') = '' THEN pce.perRazonSocial
			ELSE (
				pce.perPrimerNombre + 
				CASE WHEN pce.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pce.perSegundoNombre+' ' END +
				pce.perPrimerApellido + 
				CASE WHEN pce.perSegundoApellido IS NULL THEN '' ELSE ' '+pce.perSegundoApellido END
			) END responsableAfiliaciones, 
			uce.ubiTelefonoFijo telefonoFijoResponsable, 
			uce.ubiTelefonoCelular telefonoCelularResponsable, 
			uce.ubiEmail eMailResponsable,
			@fechaInicio,
			@fechaFin		
		FROM (
				SELECT eec.eecId, eec.eecPersona, eec.eecEstadoAfiliacion, eec.eecFechaCambioEstado 
				FROM dbo.EstadoAfiliacionEmpleadorCaja eec
				INNER JOIN (
					SELECT MAX(eec2.eecId) eecId, eec2.eecPersona 
					FROM dbo.EstadoAfiliacionEmpleadorCaja eec2
					WHERE eec2.eecEstadoAfiliacion = 'ACTIVO'
					AND eec2.eecFechaCambioEstado <= @fechaFin
					GROUP BY eec2.eecPersona
				) AS T ON eec.eecId = T.eecId
				) eec
		INNER JOIN dbo.Persona per ON per.perId = eec.eecPersona
		INNER JOIN dbo.Empresa emp ON emp.empPersona = eec.eecPersona
		INNER JOIN dbo.Empleador eml ON eml.empEmpresa = emp.empId
		LEFT JOIN (
			SELECT rce.rceEmpleador, rce.rcePersona, rce.rceUbicacion 
			FROM dbo.RolContactoEmpleador rce
			INNER JOIN (
				SELECT MAX(rce2.rceId) rceId, rce2.rceEmpleador
				FROM dbo.RolContactoEmpleador rce2
				WHERE rce2.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_AFILIACIONES'
				GROUP BY rce2.rceEmpleador
			) AS T ON rce.rceId = T.rceId
		) rce ON rce.rceEmpleador = eml.empId 
		LEFT JOIN dbo.Persona pce ON rce.rcePersona = pce.perId
		LEFT JOIN dbo.Ubicacion uce ON rce.rceUbicacion = uce.ubiId
		LEFT JOIN dbo.CodigoCIIU cii ON emp.empCodigoCIIU = cii.ciiId
		LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		LEFT JOIN dbo.Persona prl ON emp.empRepresentanteLegal = prl.perId
		LEFT JOIN (
			SELECT cat.empId,
				SUM(CASE WHEN cat.ctaCategoria = 'A' THEN 1 ELSE 0 END) cantidadA, 
				SUM(CASE WHEN cat.ctaCategoria = 'B' THEN 1 ELSE 0 END) cantidadB, 
				SUM(CASE WHEN cat.ctaCategoria = 'C' THEN 1 ELSE 0 END) cantidadC,
				COUNT(*) cantidadTotal
			FROM (
				SELECT eml.empId, cta.ctaAfiliado, MAX(cta.ctaCategoria) ctaCategoria, MAX(ctaFechaCambioCategoria) ctaFechaCambioCategoria
				FROM (
						SELECT cta.ctaAfiliado, cta.ctaEstadoAfiliacion, cta.ctaFechaCambioCategoria, cta.ctaCategoria
						FROM dbo.CategoriaAfiliado cta
						INNER JOIN (
							SELECT MAX(cta2.ctaId) ctaId, cta2.ctaAfiliado
							FROM dbo.CategoriaAfiliado cta2
							WHERE cta2.ctaEstadoAfiliacion = 'ACTIVO'
							AND cta2.ctaFechaCambioCategoria <= @fechaFin
							GROUP BY cta2.ctaAfiliado
						) AS T ON cta.ctaId = T.ctaId
					) cta
				INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = cta.ctaAfiliado
				INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId
				WHERE cta.ctaEstadoAfiliacion = 'ACTIVO'
				AND cta.ctaFechaCambioCategoria <= @fechaFin
				GROUP BY eml.empId, cta.ctaAfiliado
			) AS cat
			GROUP BY cat.empId
		) AS categorias ON eml.empId = categorias.empId
	END
	ELSE	
	BEGIN
		SELECT CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END aportante,
			CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 1 ELSE 2 END tipoDocumento,
			per.perNumeroIdentificacion,
			CASE WHEN ISNULL(prl.perPrimerNombre, '') = '' THEN prl.perRazonSocial
			ELSE (
				prl.perPrimerNombre + 
				CASE WHEN prl.perSegundoNombre IS NULL THEN ' ' ELSE ' '+prl.perSegundoNombre+' ' END +
				prl.perPrimerApellido + 
				CASE WHEN prl.perSegundoApellido IS NULL THEN '' ELSE ' '+prl.perSegundoApellido END
			) END representanteLegal,
			CASE WHEN TRIM(ubi.ubiDireccionFisica) like 'SIN DIRECCI%' THEN 'S.R' ELSE TRIM(ubi.ubiDireccionFisica) END  direccion, -- cambio GLPI 81246
			mun.munNombre municipio, dep.depNombre departamento, mun.munCodigo divipola,
			ubi.ubiTelefonoFijo telefono, ubi.ubiEmail correoElectronico, emp.empPaginaWeb paginaWeb,
			ISNULL(categorias.cantidadA, 0) trabajadoresA,
			ISNULL(categorias.cantidadB, 0) trabajadoresB,
			ISNULL(categorias.cantidadC, 0) trabajadoresC,
			ISNULL(categorias.cantidadTotal, 0) totalTrabajadores, 
			cii.ciiCodigo,
			CASE WHEN ISNULL(pce.perPrimerNombre, '') = '' THEN pce.perRazonSocial
			ELSE (
				pce.perPrimerNombre + 
				CASE WHEN pce.perSegundoNombre IS NULL THEN ' ' ELSE ' '+pce.perSegundoNombre+' ' END +
				pce.perPrimerApellido + 
				CASE WHEN pce.perSegundoApellido IS NULL THEN '' ELSE ' '+pce.perSegundoApellido END
			) END responsableAfiliaciones, 
			uce.ubiTelefonoFijo telefonoFijoResponsable, 
			uce.ubiTelefonoCelular telefonoCelularResponsable, 
			uce.ubiEmail eMailResponsable		
		FROM (
				SELECT eec.eecId, eec.eecPersona, eec.eecEstadoAfiliacion, eec.eecFechaCambioEstado 
				FROM dbo.EstadoAfiliacionEmpleadorCaja eec
				INNER JOIN (
					SELECT MAX(eec2.eecId) eecId, eec2.eecPersona 
					FROM dbo.EstadoAfiliacionEmpleadorCaja eec2
					WHERE eec2.eecEstadoAfiliacion = 'ACTIVO'
					AND eec2.eecFechaCambioEstado <= @fechaFin
					GROUP BY eec2.eecPersona
				) AS T ON eec.eecId = T.eecId
				) eec
		INNER JOIN dbo.Persona per ON per.perId = eec.eecPersona
		INNER JOIN dbo.Empresa emp ON emp.empPersona = eec.eecPersona
		INNER JOIN dbo.Empleador eml ON eml.empEmpresa = emp.empId
		LEFT JOIN (
			SELECT rce.rceEmpleador, rce.rcePersona, rce.rceUbicacion 
			FROM dbo.RolContactoEmpleador rce
			INNER JOIN (
				SELECT MAX(rce2.rceId) rceId, rce2.rceEmpleador
				FROM dbo.RolContactoEmpleador rce2
				WHERE rce2.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_AFILIACIONES'
				GROUP BY rce2.rceEmpleador
			) AS T ON rce.rceId = T.rceId
		) rce ON rce.rceEmpleador = eml.empId 
		LEFT JOIN dbo.Persona pce ON rce.rcePersona = pce.perId
		LEFT JOIN dbo.Ubicacion uce ON rce.rceUbicacion = uce.ubiId
		LEFT JOIN dbo.CodigoCIIU cii ON emp.empCodigoCIIU = cii.ciiId
		LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		LEFT JOIN dbo.Persona prl ON emp.empRepresentanteLegal = prl.perId
		LEFT JOIN (
			SELECT cat.empId,
				SUM(CASE WHEN cat.ctaCategoria = 'A' THEN 1 ELSE 0 END) cantidadA, 
				SUM(CASE WHEN cat.ctaCategoria = 'B' THEN 1 ELSE 0 END) cantidadB, 
				SUM(CASE WHEN cat.ctaCategoria = 'C' THEN 1 ELSE 0 END) cantidadC,
				COUNT(*) cantidadTotal
				FROM (
					SELECT eml.empId, cta.ctaAfiliado, MAX(cta.ctaCategoria) ctaCategoria, MAX(ctaFechaCambioCategoria) ctaFechaCambioCategoria
					FROM (
							SELECT cta.ctaAfiliado, cta.ctaEstadoAfiliacion, cta.ctaFechaCambioCategoria, cta.ctaCategoria
							FROM dbo.CategoriaAfiliado cta
							INNER JOIN (
								SELECT MAX(cta2.ctaId) ctaId, cta2.ctaAfiliado
								FROM dbo.CategoriaAfiliado cta2
								WHERE cta2.ctaEstadoAfiliacion = 'ACTIVO'
								AND cta2.ctaFechaCambioCategoria <= @fechaFin
								GROUP BY cta2.ctaAfiliado
							) AS T ON cta.ctaId = T.ctaId
						) cta
					INNER JOIN dbo.RolAfiliado roa ON roa.roaAfiliado = cta.ctaAfiliado
					INNER JOIN dbo.Empleador eml ON roa.roaEmpleador = eml.empId
					WHERE cta.ctaEstadoAfiliacion = 'ACTIVO'
					AND cta.ctaFechaCambioCategoria <= @fechaFin
					GROUP BY eml.empId, cta.ctaAfiliado
				) AS cat
				GROUP BY cat.empId
		) AS categorias ON eml.empId = categorias.empId
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
