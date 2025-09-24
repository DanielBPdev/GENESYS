/****** Object:  StoredProcedure [dbo].[reporteRegistroUnicoDeEmpleadores]    Script Date: 2024-06-27 2:54:36 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteRegistroUnicoDeEmpleadores]    Script Date: 17/06/2024 3:07:30 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteRegistroUnicoDeEmpleadores]    Script Date: 2024-06-07 12:06:11 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteRegistroUnicoDeEmpleadores]    Script Date: 14/03/2023 5:59:06 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteRegistroUnicoDeEmpleadores]    Script Date: 03/08/2022 5:52:32 p. m. ******/
 
-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 06 Julio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 11.
-- Reporte 11
---EXECUTE reporteRegistroUnicoDeEmpleadores '2024-01-01','2024-06-27'
---update : 20220803
-- =============================================
CREATE OR ALTER     PROCEDURE [dbo].[reporteRegistroUnicoDeEmpleadores](
	@FECHA_INICIAL DATETIME,
	@FECHA_FINAL DATETIME
)

AS
BEGIN
SET NOCOUNT ON
--/---------------------------------------**********---------------------------------------\--
--                       REPORTE REGISTRO UNICO DE EMPLEADORES  -  N° 11.
--\---------------------------------------**********---------------------------------------/--
--------------
------
------
----
SELECT  
	
	----------  Razon Social o Nombre Empleador  ----------
	CASE 
		WHEN ISNULL(perEmpleador.perRazonSocial, '') = '' 
		THEN (
			perEmpleador.perPrimerNombre + 
			CASE 
				WHEN perEmpleador.perSegundoNombre IS NULL 
				THEN ' ' 
				ELSE ' '+perEmpleador.perSegundoNombre+' ' 
			END +
			perEmpleador.perPrimerApellido + 
			CASE 
				WHEN perEmpleador.perSegundoApellido IS NULL 
				THEN ' ' 
				ELSE ' '+perEmpleador.perSegundoApellido 
			END
		) 
		ELSE perEmpleador.perRazonSocial
	END AS [Razón social o nombre del empleador],

	----------  Tipo de Identificacion  ----------
	CASE 
		WHEN perEmpleador.perTipoIdentificacion = 'NIT' 
		THEN '1' 
		WHEN perEmpleador.perTipoIdentificacion = 'CEDULA_CIUDADANIA' 
		THEN '2' 
		ELSE REPLACE ( perEmpleador.perTipoIdentificacion, '_', ' ')
			/*CASE 
				WHEN perEmpleador.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' 
				THEN 'CEDULA EXTRANJERIA'
				ELSE REPLACE ( perEmpleador.perTipoIdentificacion, '_', ' ') 
			END*/
	END AS [Tipo de documento de identidad],

	----------  Numero de Identificacion  ----------
	CASE
		WHEN perEmpleador.perDigitoVerificacion IS NULL
		THEN perEmpleador.perNumeroIdentificacion
		ELSE CONCAT ( perEmpleador.perNumeroIdentificacion, perEmpleador.perDigitoVerificacion )
	END AS [Número del NIT o de la cédula de ciudadanía del empleador],

	----------  Nombre Representante Legal  ----------
	CASE 
		WHEN ISNULL(perRepresentanteLegal.perPrimerNombre, '') = '' 
		THEN ISNULL(perRepresentanteLegal.perRazonSocial, 'S.R')
		ELSE (
			perRepresentanteLegal.perPrimerNombre + 
			CASE 
				WHEN perRepresentanteLegal.perSegundoNombre IS NULL 
				THEN ' ' 
				ELSE ' '+perRepresentanteLegal.perSegundoNombre+' ' 
			END +
			perRepresentanteLegal.perPrimerApellido + 
			CASE 
				WHEN perRepresentanteLegal.perSegundoApellido IS NULL THEN '' 
				ELSE ' '+perRepresentanteLegal.perSegundoApellido 
			END
		) 
	END AS [Nombre del representante legal(persona Jurúdica)],

	----------  Direccion Empleador  ----------
	CASE 
		WHEN  ISNULL(ubiEmpleador.ubiDireccionFisica,'S.R')=''  THEN  'S.R' 
		WHEN  ubiEmpleador.ubiDireccionFisica = 'SIN DIRECCION'  THEN  'S.R' 
		ELSE ISNULL(ubiEmpleador.ubiDireccionFisica,'S.R')		 
	END AS [Dirección del empleador],

	----------  Nombre Municipio  ----------
	CASE 
		WHEN  municipioEmpleador.munNombre IS NULL THEN 'S.R'
		ELSE  municipioEmpleador.munNombre 
	END AS [Municipio],
	
	----------  Nombre Departamento  ----------
	CASE
		WHEN  departamentoEmpleador.depNombre IS NULL THEN 'S.R'
		ELSE   departamentoEmpleador.depNombre 
	END AS [Departamento], 

	----------  Codigo Municipio  ----------
	CASE
		WHEN municipioEmpleador.munCodigo IS NULL THEN 'S.R'
		ELSE  municipioEmpleador.munCodigo 
	END AS [Divipola-DANE], 

	----------  Numero de Telefono  ----------
	 
	CASE
		WHEN  ubiEmpleador.ubiTelefonoFijo ='' 
		THEN  ubiEmpleador.ubiTelefonoCelular
	ELSE  
		 ISNULL(ubiEmpleador.ubiTelefonoFijo, ubiEmpleador.ubiTelefonoCelular)
	 END
		  AS [Número de teléfono (Principal)],

	----------  Correo Electronico  ----------
	CASE
		WHEN  ISNULL(ubiEmpleador.ubiEmail,'S.R') = '' THEN 'S.R'
		ELSE ISNULL(ubiEmpleador.ubiEmail,'S.R')
	END
	 AS [Correo electrónico],

	----------  Pagina Web  ----------
	CASE WHEN ISNULL(empresa.empPaginaWeb, 'S.R') = ''
	THEN 'S.R'
	ELSE ISNULL(empresa.empPaginaWeb, 'S.R')
	END
	AS [Página web],

	----------  Numero de Trabajadores Activos CAT A  ----------
	SUM(
		CASE 
			WHEN categoria.categoria = 'A' THEN 1 
			ELSE 0 
		END
	) AS [Número de trabajadores activos- Categoría A],

	----------  Numero de Trabajadores Activos CAT B  ----------
	SUM(
		CASE 
			WHEN categoria.categoria = 'B' THEN 1 
			ELSE 0 
		END
	) AS [Número de trabajadores activos- Categoría B],

	----------  Numero de Trabajadores Activos CAT C  ----------
	SUM(
		CASE 
			WHEN categoria.categoria = 'C' THEN 1 
			ELSE 0 
		END
	) AS [Número de trabajadores activos- Categoría C],

	----------  Numero de Trabajadores Activos TOTAL  ----------
	SUM(  
		ISNULL(
		CASE
			WHEN categoria.categoria IN ( 'A', 'B', 'C' )
			THEN 1
		END, 0) 
	) AS [Número total de trabajadores activos], 

	----------  Codigo CIIU  ----------
	cii.ciiCodigo AS [Código CIIU v4 AC a cuatro dígitos],

	----------  Nombre Persona Talento Humano  ----------
	CASE 
		WHEN ISNULL(perRepresentanteLegal.perPrimerNombre, '') = '' THEN perRepresentanteLegal.perRazonSocial
		ELSE (
			perRepresentanteLegal.perPrimerNombre + 
			CASE 
				WHEN perRepresentanteLegal.perSegundoNombre IS NULL THEN ' ' 
				ELSE ' '+perRepresentanteLegal.perSegundoNombre+' ' 
			END +
			perRepresentanteLegal.perPrimerApellido + 
			CASE 
				WHEN perRepresentanteLegal.perSegundoApellido IS NULL THEN ' ' 
				ELSE ' '+perRepresentanteLegal.perSegundoApellido 
			END
		) 
	END AS [Nombre de la persona encargada del talento Humano],
	
	----------  Telefono Persona Talento Humano  ----------
	CASE WHEN ubiPersonaTalentoHumano.ubiTelefonoFijo = '' THEN 'S.R'
	ELSE ISNULL(ubiPersonaTalentoHumano.ubiTelefonoFijo, 'S.R') END
	  AS [Teléfono fijo de la persona encargada del talento humano], 

	----------  Celular Persona Talento Humano  ----------
	CASE WHEN ubiPersonaTalentoHumano.ubiTelefonoCelular = '' THEN 'S.R'
	ELSE ISNULL(ubiPersonaTalentoHumano.ubiTelefonoCelular, 'S.R') END
	 AS [Celular de la persona encargada del talento humano], 

	----------  Email Persona Talento Humano  ----------
	case when ubiPersonaTalentoHumano.ubiEmail= '' THEN 'S.R'
	ELSE ISNULL(ubiPersonaTalentoHumano.ubiEmail, 'S.R') END 
	AS [Correo electrónico de la persona encargada del talento humano]
	--,empleador.empId
/*declare @FECHA_INICIAL date, @FECHA_FINAL date
set @FECHA_INICIAL = '06-01-2022'
set @FECHA_FINAL = '06-30-2022' 
select  *
 ,empFechaCambioEstadoAfiliacion*/
FROM

	----------SE OBTIENE LA INFORMACIÓN DE LA EMPRESA
	Empresa AS empresa
	INNER JOIN UbicacionEmpresa ubiEmpresa 
		ON ( ubiEmpresa.ubeEmpresa = empresa.empId AND ubiEmpresa.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA')
	INNER JOIN dbo.Ubicacion ubicacionEmpresa ON ubicacionEmpresa.ubiId =ubiEmpresa.ubeUbicacion
	LEFT JOIN dbo.Municipio municipioEmpresa ON ubicacionEmpresa.ubiMunicipio = municipioEmpresa.munId
	LEFT JOIN dbo.Departamento departamentoEmpresa ON municipioEmpresa.munDepartamento = departamentoEmpresa.depId 

	----------SE OBTIENE LA INFORMACIÓN DEL EMPLEADOR
	INNER JOIN Empleador AS empleador ON empleador.empEmpresa = empresa.empId 
	LEFT JOIN PERSONA perEmpleador ON perEmpleador.perId = empPersona
	
	LEFT JOIN UbicacionEmpresa ubiEmp 
		ON ( ubiEmp.ubeEmpresa = empresa.empId AND ubiEmp.ubeTipoUbicacion = 'UBICACION_PRINCIPAL')
	---
	 LEFT JOIN Ubicacion ubiEmpleador ON ubiEmpleador.ubiId = ubiEmp.ubeUbicacion

	LEFT JOIN dbo.Municipio municipioEmpleador ON ubiEmpleador.ubiMunicipio = municipioEmpleador.munId
	LEFT JOIN dbo.Departamento departamentoEmpleador ON municipioEmpleador.munDepartamento = departamentoEmpleador.depId 

	
	----------SE OBTIENE LA INFORMACIÓN DEL RESPONSABLE DE TALENTO HUMANO
	/*LEFT JOIN (
		SELECT 
			rce.rceEmpleador, 
			rce.rcePersona, 
			rce.rceUbicacion 
		FROM dbo.RolContactoEmpleador rce
		INNER JOIN (
			SELECT 
				MAX(rce2.rceId) rceId, 
				rce2.rceEmpleador
			FROM 
				dbo.RolContactoEmpleador rce2
			WHERE 
				rce2.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_AFILIACIONES'
			GROUP BY 
				rce2.rceEmpleador
		) AS T ON rce.rceId = T.rceId
	) ResponsableTalentoHumano ON ResponsableTalentoHumano.rceEmpleador = empleador.empId */
	
	LEFT JOIN Persona perRepresentanteLegal ON empresa.empRepresentanteLegal = perRepresentanteLegal.perId
	
	LEFT JOIN dbo.Ubicacion ubiPersonaTalentoHumano 
	ON empresa.empUbicacionRepresentanteLegal = ubiPersonaTalentoHumano.ubiId
	LEFT JOIN dbo.CodigoCIIU cii ON empresa.empCodigoCIIU = cii.ciiId

	----------SE OBTIENE LA INFORMACIÓN DE LA AFILIACION DE CADA FUNCIONARIO
	LEFT JOIN RolAfiliado AS rolAfiliado 
		ON roaEmpleador  = empleador.empId 
	  	AND rolAfiliado.roaEstadoAfiliado = 'ACTIVO'
		 AND RolAfiliado.roaFechaAfiliacion <= @FECHA_FINAL
	LEFT JOIN Afiliado AS afiliado ON afiliado.afiId = rolAfiliado.roaAfiliado
	LEFT JOIN Persona perAfiliado on afiliado.afiPersona = perAfiliado.perId
	
	----------SE OBTIENE LA INFORMACIÓN DE LA CATEGORIA DE CADA FUNCIONARIO
	LEFT JOIN(
	 
		 SELECT DISTINCT CASE WHEN ctaFechaCambioCategoria  = MAX(ctaFechaCambioCategoria ) 
				OVER (PARTITION BY ctaafiliado) THEN ctaFechaCambioCategoria ELSE NULL END AS fechaCambioCategoria,
				ctaAfiliado AS idAfiliado,   ctaCategoria AS categoria
		  FROM  CategoriaAfiliado (nolock)  
		 WHERE   ctaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
			AND ctaFechaCambioCategoria <= @FECHA_FINAL
			 --AND ctaEstadoAfiliacion = 'ACTIVO'
	)AS categoria 
		ON categoria.idAfiliado = afiliado.afiId AND fechaCambioCategoria IS NOT NULL

	----------SE OBTIENE LA INFORMACIÓN DE LA CATEGORIA DE CADA FUNCIONARIO
	INNER JOIN SolicitudAfiliaciEmpleador on saeEmpleador = empleador.empId  
	INNER JOIN dbo.Solicitud sol ON sol.solId = saeSolicitudGlobal
		AND sol.solResultadoProceso IN ( 'APROBADA', 'CERRADA' )
	--WHERE perEmpleador.perNumeroIdentificacion = '796000786'
WHERE  --perEmpleador.perNumeroIdentificacion = '890806490' AND
--empleador.empId = 24601 AND
	empleador.empEstadoEmpleador = 'ACTIVO'
	AND CAST( empleador.empFechaCambioEstadoAfiliacion AS DATE ) <= @FECHA_FINAL
	--AND rolAfiliado.roaFechaAfiliacion <= @FECHA_FINAL----cambio olga vega 2022-07-27
	 ---  AND perEmpleador.perNumeroIdentificacion in( '900744340')---'16079434','900217784')
	
GROUP BY 
	empresa.empPaginaWeb,
	ubicacionEmpresa.ubiDireccionFisica,
	ubicacionEmpresa.ubiTelefonoFijo,
	ubicacionEmpresa.ubiEmail,
	municipioEmpresa.munNombre,
	departamentoEmpresa.depNombre,
	municipioEmpresa.munCodigo,
	departamentoEmpresa.depCodigo,
	perEmpleador.perRazonSocial,
	perEmpleador.perPrimerNombre,
	perEmpleador.perPrimerApellido,
	perEmpleador.perSegundoNombre,
	perEmpleador.perSegundoApellido,
	perEmpleador.perTipoIdentificacion,
	perEmpleador.perNumeroIdentificacion,
	perEmpleador.perDigitoVerificacion,
	ubiEmpleador.ubiDireccionFisica,
	ubiEmpleador.ubiTelefonoFijo,
	ubiEmpleador.ubiEmail,
	departamentoEmpleador.depCodigo,
	departamentoEmpleador.depNombre,
	municipioEmpleador.munCodigo,
	municipioEmpleador.munNombre,
	perRepresentanteLegal.perRazonSocial,
	perRepresentanteLegal.perPrimerNombre,
	perRepresentanteLegal.perPrimerApellido,
	perRepresentanteLegal.perSegundoNombre,
	perRepresentanteLegal.perSegundoApellido,
	cii.ciiCodigo,
	perRepresentanteLegal.perPrimerNombre,
	perRepresentanteLegal.perPrimerApellido,
	perRepresentanteLegal.perSegundoNombre,
	perRepresentanteLegal.perSegundoApellido,
	perRepresentanteLegal.perRazonSocial,
	ubiPersonaTalentoHumano.ubiTelefonoFijo,
	ubiPersonaTalentoHumano.ubiTelefonoCelular,
	ubiPersonaTalentoHumano.ubiEmail,
	ubicacionEmpresa.ubiTelefonoCelular,
	ubiEmpleador.ubiTelefonoCelular,empresa.empid--,
--	empFechaCambioEstadoAfiliacion
END