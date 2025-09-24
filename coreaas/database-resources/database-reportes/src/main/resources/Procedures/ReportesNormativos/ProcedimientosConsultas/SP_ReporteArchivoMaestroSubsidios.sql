/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 2023-06-26 12:02:04 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 2023-06-23 10:36:48 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 2023/06/20 11:22:06 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 23/03/2023 8:30:45 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 10/11/2022 9:55:36 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 28/10/2022 1:46:26 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 05/10/2022 1:03:35 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 29/09/2022 4:35:43 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 23/09/2022 4:54:23 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 22/08/2022 4:38:51 p. m. ******/ 
/****** Object:  StoredProcedure [dbo].[reporteArchivoMaestroSubsidios]    Script Date: 08/04/2021 04:30:40 p. m. ******/
-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 8 Abril 2021
-- Description: Procedimiento almacenado encargado de ejecutar la consulta de Archivo Maestros de Subsidios.
--				Reportar los subsidios otorgados a los afiliados, no afiliados y los aportantes.
-- Reporte 13
---EXEC reporteArchivoMaestroSubsidios '2023-05-01','2023-06-26','CCF666'
-- =============================================
CREATE OR ALTER   PROCEDURE [dbo].[reporteArchivoMaestroSubsidios]
(
    @fechaInicio DATE,
	@fechaFin DATE,
	@codigoCaja VARCHAR(6)--,
	---@Oficial VARCHAR(1) = 0
)
AS
BEGIN
SET ANSI_NULLS ON 
SET QUOTED_IDENTIFIER ON
 
	SET NOCOUNT ON
    -- (REPORTE 13) Archivo Maestro de Subsidios
	-- Descripción: Reportar los subsidios otorgados a los afiliados, no afiliados y los aportantes.
	
	-- Variables que contendrán la data de la tabla temporal en el cursor
	DECLARE @tipoRegistro SMALLINT, @identificadorUnicoSubsidio BIGINT, @codCaja VARCHAR(6), @quienSeOtorgo SMALLINT, @tipoIdAfiliado VARCHAR(3), @numIdAfiliado BIGINT, @primerApellido VARCHAR(100);
	DECLARE @segundoApellido VARCHAR(100), @primerNombre VARCHAR(100), @segundoNombre VARCHAR(100), @codGeneroAfiliado VARCHAR(1), @fechaNacimAfiliado VARCHAR(10), @fechaAsigSubsidio varchar(10), 
			@valorSubsidio BIGINT, @codTipoSubsidio SMALLINT, @estadoSubsidio SMALLINT, @deptoSubsidio SMALLINT;
	DECLARE @municipioSubsidio varchar(3), @fechaEntregaUltSub DATE, @tipoIdBeneficiario VARCHAR(3), @numIdBeneficiario nvarchar(17), @codGeneroBeneficiario VARCHAR(1), @fechaNactipoIdBeneficiario DATE, @primerApellidoBen VARCHAR(100);
	DECLARE @segundoApellidoBen VARCHAR(100), @primerNombreBen VARCHAR(100), @segundoNombreBen VARCHAR(100), @tipoIdEmpresaRecibe VARCHAR(3), @numIdEmpresaRecibe VARCHAR(17), @digitoVerificacion varchar(1), @razonSocialEmpresaRecibe VARCHAR(255);

		-----llenando la variable  20230323
     SELECT @codigoCaja = cnsValor FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'


	-- Tabla temporal que resulta de haber pasado por el cursor de los subsidios agregando el afiliado y el jefe de hogar en cada caso
	DROP TABLE IF EXISTS #DataMaestroSCMModificado;
	CREATE TABLE #DataMaestroSCMModificado (tipoRegistro SMALLINT, identificadorUnicoSubsidio varchar(9), codCaja VARCHAR(6), quienSeOtorgo SMALLINT, tipoIdAfiliado VARCHAR(3), numIdAfiliado BIGINT, primerApellido VARCHAR(100),
		segundoApellido VARCHAR(100), primerNombre VARCHAR(100), segundoNombre VARCHAR(100), 
		codGeneroAfiliado VARCHAR(1), fechaNacimAfiliado VARCHAR(10), fechaAsigSubsidio varchar(10), valorSubsidio BIGINT, codTipoSubsidio SMALLINT, estadoSubsidio SMALLINT, deptoSubsidio varchar(2),
		municipioSubsidio varchar(3), fechaEntregaUltSub DATE, tipoIdBeneficiario VARCHAR(3), numIdBeneficiario nvarchar(17), codGeneroBeneficiario VARCHAR(1), fechaNactipoIdBeneficiario DATE, primerApellidoBen VARCHAR(100),
		segundoApellidoBen VARCHAR(100), primerNombreBen VARCHAR(100), segundoNombreBen VARCHAR(100), tipoIdEmpresaRecibe VARCHAR(3), numIdEmpresaRecibe VARCHAR(17), digitoVerificacion varchar(1), razonSocialEmpresaRecibe VARCHAR(255))

	-- Sección FOVIS
	DECLARE @consultarDataMaestroSV AS CURSOR;
	-- Tabla temporal que contiene la información de subsidio de vivienda
	DROP TABLE IF EXISTS #DataMaestroSV;
	CREATE TABLE #DataMaestroSV (tipoRegistro SMALLINT, identificadorUnicoSubsidio varchar(9), codCaja VARCHAR(6), quienSeOtorgo SMALLINT, tipoIdAfiliado VARCHAR(3), numIdAfiliado varchar(17), primerApellido VARCHAR(100),
		segundoApellido VARCHAR(100), primerNombre VARCHAR(100), segundoNombre VARCHAR(100), 
		codGeneroAfiliado VARCHAR(1), fechaNacimAfiliado VARCHAR(10),
		fechaAsigSubsidio varchar(10), valorSubsidio BIGINT, codTipoSubsidio SMALLINT, estadoSubsidio SMALLINT, deptoSubsidio SMALLINT,
		municipioSubsidio varchar(3), fechaEntregaUltSub DATE, tipoIdBeneficiario VARCHAR(3), numIdBeneficiario BIGINT, codGeneroBeneficiario VARCHAR(1), fechaNactipoIdBeneficiario DATE, primerApellidoBen VARCHAR(100),
		segundoApellidoBen VARCHAR(100), primerNombreBen VARCHAR(100), segundoNombreBen VARCHAR(100), tipoIdEmpresaRecibe VARCHAR(3), numIdEmpresaRecibe VARCHAR(20), digitoVerificacion varchar(1), razonSocialEmpresaRecibe VARCHAR(255))

	-- LLena la tabla temporal con los subsidios de vivienda
	INSERT INTO #DataMaestroSV (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
		segundoApellido, primerNombre, segundoNombre, 
		codGeneroAfiliado , fechaNacimAfiliado  ,
		fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
		municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
		segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

		SELECT DISTINCT
		-- Tipo de registro: 2. Valor que significa que el registro es de detalle
		2 [Tipo de registro],
		-- Identificador único del subsidio: Para vivienda, se usa el número de solicitud de postulación del hogar"
		right(sol.solNumeroRadicacion,9) [Identificador único del subsidio],
		-- Código caja de compensación familiar
		@codigoCaja [Código caja de compensación familiar],
		-- A quien se otorgó el subsidio: Desde Genesys solo se obtiene el dato de todos los afiliados a los que se les otorgo el subsidio
		1 as [A quien se otorgó el subsidio],

		-- Tipo de identificación del afiliado o jefe cabeza de hogar:
		CASE per.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'  
		END [Tipo de identificación del afiliado o jefe cabeza de hogar],
		-- Número de identificación del afiliado o jefe cabeza de hogar: 
		per.perNumeroIdentificacion [Número de identificación del afiliado o jefe cabeza de hogar],
		-- Primer Apellido
		per.perPrimerApellido [Primer apellido],
		-- Segundo Apellido
		per.perSegundoApellido [Segundo apellido],
		-- Primer Nombre
		per.perPrimerNombre [Primer nombre],
		-- Segundo Nombre
		per.perSegundoNombre [Segundo nombre],
			-- Codigo genero del afiliado (esta columna se usa en el cursor)
		CASE (SELECT detPerAfi.pedGenero 
		       FROM Afiliado afidemo 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = afidemo.afiId)
			WHEN 'MASCULINO' THEN 'M'
			WHEN 'FEMENINO' THEN 'F'
		END as codGeneroAfiliado ,
		-- Fecha de nacimiento del afiliado (esta columna se usa en el cursor)
		CONVERT (DATE, (SELECT detPerAfi.pedFechaNacimiento FROM Afiliado afidemo 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = afidemo.afiId)) 
			as fechaNacimAfiliado,
		-- Fecha de asignación del subsidio: AAAA-MM-DD fecha de la aceptación de resultados para el ciclo de asignación asociado a los resultados de asignación
		CONVERT (DATE, solAsig.safFechaAceptacion) as [Fecha de asignación del subsidio],
		-- Valor del subsidio: Para vivienda, corresponde al valor del campo "valor SFV a asignar"
		CAST(pof.pofValorAsignadoSFV AS INT) as [Valor del subsidio],
		-- Código tipo de subsidio: 4 para subsidios de vivienda
		4 [Código tipo de subsidio],

		-- Estado del subsidio
		CASE 
			WHEN pof.pofEstadoHogar IN ('ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'ASIGNADO_SIN_PRORROGA', 'SUBSIDIO_LEGALIZADO') THEN 1
			WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA') THEN 2
			WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO', 'SUBSIDIO_DESEMBOLSADO') THEN 3
			WHEN pof.pofEstadoHogar = 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA' THEN 4
			WHEN pof.pofEstadoHogar = 'RECHAZADO' THEN 5
		END [Estado del subsidio],

		-- Departamento donde recibe el subsidio: Departamento de ubicación de la caja de compensación
--		(SELECT TOP 1 parame.prmValor FROM Parametro parame WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') as [Departamento donde recibe el subsidio],
---CAMBIO GLPI 61594 POR EL DE LA POSTULACION
    (SELECT RIGHT(munCodigo,2) FROM  Municipio  with(nolock) WHERE munid = JSON_VALUE(CAST( pofJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.jefeHogar.ubicacionModeloDTO.idMunicipio')) as [Departamento donde recibe el subsidio],

		-- Municipio donde recibe el subsidio: Municipio de ubicación de la caja de compensación
	--	(SELECT TOP 1 parame.prmValor FROM Parametro parame WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') as  [Municipio donde recibe el subsidio],
	(SELECT LEFT(munCodigo,3) FROM  Municipio  with(nolock) WHERE munid = JSON_VALUE(CAST( pofJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.jefeHogar.ubicacionModeloDTO.idMunicipio')) as [Municipio donde recibe el subsidio],
		-- Fecha de entrega del último subsidio: Fecha de confirmación que se ha hecho efectiva la transacción de desembolso fovis
		CONVERT (DATE, (SELECT TOP 1 legDes.lgdFechaTransferencia FROM SolicitudLegalizacionDesembolso solLegDes
				INNER JOIN LegalizacionDesembolso legDes ON legDes.lgdId = solLegDes.sldLegalizacionDesembolso
				WHERE solLegDes.sldPostulacionFOVIS = pof.pofId 
				AND (SELECT sol.solResultadoProceso FROM Solicitud sol WHERE solId = solLegDes.sldSolicitudGlobal) = 'APROBADA')
		) [Fecha de entrega del último subsidio],

		-- Datos que corresponden al beneficiario en este caso el integrante del hogar
		CASE ISNULL(perIntHogar.pertipoidentificacion , per.pertipoidentificacion )
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		END [Tipo de identificación del beneficiario],
		REPLACE(ISNULL(perIntHogar.perNumeroIdentificacion,per.perNumeroIdentificacion),'-', '')  as [Número de identificación del beneficiario],
		
		 CASE  (SELECT TOP 1 ISNULL(perDetIntHogar.pedGenero ,perdet.pedGenero)
						FROM PersonaDetalle perDetIntHogar 
						WHERE perDetIntHogar.pedPersona = perIntHogar.perId)
		 WHEN 'MASCULINO' THEN 'M'
		 WHEN 'FEMENINO' THEN 'F'
		  END AS [Código género del beneficiario],
		
		CONVERT (DATE, (SELECT TOP 1 ISNULL(perDetIntHogar.pedFechaNacimiento ,perdet.pedfechanacimiento)
					      FROM PersonaDetalle perDetIntHogar 
						 WHERE perDetIntHogar.pedPersona = perIntHogar.perId)) AS [Fecha de nacimiento del beneficiario],
	    CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perPrimerApellido
			 ELSE per.perPrimerApellido END AS [Primer apellido del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perSegundoApellido 
			 ELSE per.perSegundoApellido END AS [Segundo apellido del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perPrimerNombre 
			 ELSE per.perPrimerNombre END AS [Primer nombre del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perSegundoNombre
			 ELSE per.perSegundoNombre END AS [Segundo nombre del beneficiario],
		-- Datos de la empresa que recibe el subsidio, para FOVIS van vacíos
		-- Tipo de identificación de la empresa que recibe el subsidio:
		'' [Tipo de identificación de la empresa que recibe el subsidio],
		-- Número de identificación de la empresa que recibe el subsidio:
		'' [Número de identificación de la empresa que recibe el subsidio],
		-- Digito de verificación de la identificación de la empresa que recibe el subsidio:
		'' [Digito de verificación de la identificación de la empresa que recibe el subsidio],
		-- Razón social de la empresa que recibe el subsidio:
		' ' [Razón social de la empresa que recibe el subsidio]

		FROM [dbo].[PostulacionFOVIS] pof (NOLOCK)
		INNER JOIN [dbo].[SolicitudPostulacion] solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
		INNER JOIN [dbo].[Solicitud] sol (NOLOCK) ON sol.solId = solPost.spoSolicitudGlobal
		INNER JOIN [dbo].[JefeHogar] jH (NOLOCK) ON jH.jehId = pof.pofJefeHogar
		INNER JOIN [dbo].[Afiliado] afi (NOLOCK) ON afi.afiId = jH.jehAfiliado
		INNER JOIN [dbo].[Persona] per (NOLOCK) ON afi.afiPersona = per.perId
		INNER JOIN [dbo].[PersonaDetalle] perdet (NOLOCK) ON perdet.pedPersona = per.perId
---asignadas
		INNER JOIN [dbo].[SolicitudAsignacion] solAsig (NOLOCK) ON pof.pofSolicitudAsignacion = solAsig.safId

		LEFT JOIN IntegranteHogar  intHogar WITH(NOLOCK) ON intHogar.inhPostulacionFovis = pof.pofId
		LEFT JOIN Persona  perIntHogar WITH(NOLOCK) ON intHogar.inhPersona = perIntHogar.perid
		WHERE 
			pof.pofEstadoHogar IN ('ASIGNADO_CON_SEGUNDA_PRORROGA', 'ASIGNADO_CON_PRIMERA_PRORROGA', 
			'ASIGNADO_SIN_PRORROGA')
			AND  solAsig.safFechaAceptacion BETWEEN @fechaInicio AND @fechaFin
				 
----*******************DESEMBOLSOS********************---------------------------

UNION

	SELECT DISTINCT
		-- Tipo de registro: 2. Valor que significa que el registro es de detalle
		2 [Tipo de registro],
		-- Identificador único del subsidio: Para vivienda, se usa el número de solicitud de postulación del hogar"
		right(sol.solNumeroRadicacion,9) [Identificador único del subsidio],
		-- Código caja de compensación familiar
		@codigoCaja [Código caja de compensación familiar],
		-- A quien se otorgó el subsidio: Desde Genesys solo se obtiene el dato de todos los afiliados a los que se les otorgo el subsidio
		1 as [A quien se otorgó el subsidio],
		-- Tipo de identificación del afiliado o jefe cabeza de hogar:
		CASE per.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'  
		END [Tipo de identificación del afiliado o jefe cabeza de hogar],
		-- Número de identificación del afiliado o jefe cabeza de hogar: 
		per.perNumeroIdentificacion [Número de identificación del afiliado o jefe cabeza de hogar],
		-- Primer Apellido
		per.perPrimerApellido [Primer apellido],
		-- Segundo Apellido
		per.perSegundoApellido [Segundo apellido],
		-- Primer Nombre
		per.perPrimerNombre [Primer nombre],
		-- Segundo Nombre
		per.perSegundoNombre [Segundo nombre],
			-- Codigo genero del afiliado (esta columna se usa en el cursor)
		CASE (SELECT detPerAfi.pedGenero 
		       FROM Afiliado afidemo 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = afidemo.afiId)
			WHEN 'MASCULINO' THEN 'M'
			WHEN 'FEMENINO' THEN 'F'
		END as codGeneroAfiliado ,
		-- Fecha de nacimiento del afiliado (esta columna se usa en el cursor)
		CONVERT (DATE, (SELECT detPerAfi.pedFechaNacimiento FROM Afiliado afidemo 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = afidemo.afiId)) 
			as fechaNacimAfiliado,
		-- Fecha de asignación del subsidio: AAAA-MM-DD fecha de la aceptación de resultados para el ciclo de asignación asociado a los resultados de asignación
		CONVERT (DATE, solAsig.safFechaAceptacion) as [Fecha de asignación del subsidio],
		-- Valor del subsidio: Para vivienda, corresponde al valor del campo "valor SFV a asignar"
		CAST(pof.pofValorAsignadoSFV AS INT) as [Valor del subsidio],
		-- Código tipo de subsidio: 4 para subsidios de vivienda
		4 [Código tipo de subsidio],

		-- Estado del subsidio
		CASE 
			WHEN pof.pofEstadoHogar IN ('ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'ASIGNADO_SIN_PRORROGA', 'SUBSIDIO_LEGALIZADO') THEN 1
			WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA') THEN 2
			WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO', 'SUBSIDIO_DESEMBOLSADO') THEN 3
			WHEN pof.pofEstadoHogar = 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA' THEN 4
			WHEN pof.pofEstadoHogar = 'RECHAZADO' THEN 5
		END [Estado del subsidio],
		-- Departamento donde recibe el subsidio: Departamento de ubicación de la caja de compensación
--		(SELECT TOP 1 parame.prmValor FROM Parametro parame WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') as [Departamento donde recibe el subsidio],
---CAMBIO GLPI 61594 POR EL DE LA POSTULACION
        (SELECT RIGHT(munCodigo,2) FROM  Municipio  with(nolock) WHERE munid = JSON_VALUE(CAST( pofJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.jefeHogar.ubicacionModeloDTO.idMunicipio')) as [Departamento donde recibe el subsidio],
		-- Municipio donde recibe el subsidio: Municipio de ubicación de la caja de compensación
	--	(SELECT TOP 1 parame.prmValor FROM Parametro parame WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') as  [Municipio donde recibe el subsidio],
	   (SELECT LEFT(munCodigo,3) FROM  Municipio  with(nolock) WHERE munid = JSON_VALUE(CAST( pofJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.jefeHogar.ubicacionModeloDTO.idMunicipio')) as [Municipio donde recibe el subsidio],
		-- Fecha de entrega del último subsidio: Fecha de confirmación que se ha hecho efectiva la transacción de desembolso fovis
		CONVERT (DATE,lgdFechaTransferencia) as  [Fecha de entrega del último subsidio],

		-- Datos que corresponden al beneficiario en este caso el integrante del hogar
		CASE ISNULL(perIntHogar.pertipoidentificacion , per.pertipoidentificacion )
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
		END [Tipo de identificación del beneficiario],
		REPLACE(ISNULL(perIntHogar.perNumeroIdentificacion,per.perNumeroIdentificacion),'-', '')  as [Número de identificación del beneficiario],
		
		 CASE  (SELECT TOP 1 ISNULL(perDetIntHogar.pedGenero ,perdet.pedGenero)
						FROM PersonaDetalle perDetIntHogar 
						WHERE perDetIntHogar.pedPersona = perIntHogar.perId)
		 WHEN 'MASCULINO' THEN 'M'
		 WHEN 'FEMENINO' THEN 'F'
		  END 
			   AS [Código género del beneficiario],
		
		CONVERT (DATE, (SELECT TOP 1 ISNULL(perDetIntHogar.pedFechaNacimiento ,perdet.pedfechanacimiento)
					      FROM PersonaDetalle perDetIntHogar 
						 WHERE perDetIntHogar.pedPersona = perIntHogar.perId)) AS [Fecha de nacimiento del beneficiario],

	    CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perPrimerApellido
			 ELSE per.perPrimerApellido END AS [Primer apellido del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perSegundoApellido 
			 ELSE per.perSegundoApellido END AS [Segundo apellido del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perPrimerNombre 
			 ELSE per.perPrimerNombre END AS [Primer nombre del beneficiario],
		CASE WHEN perIntHogar.perNumeroIdentificacion IS NOT NULL 
		     THEN perIntHogar.perSegundoNombre
			 ELSE per.perSegundoNombre END AS [Segundo nombre del beneficiario],
		-- Datos de la empresa que recibe el subsidio, para FOVIS van vacíos
		-- Tipo de identificación de la empresa que recibe el subsidio:
		'' [Tipo de identificación de la empresa que recibe el subsidio],
		-- Número de identificación de la empresa que recibe el subsidio:
		'' [Número de identificación de la empresa que recibe el subsidio],
		-- Digito de verificación de la identificación de la empresa que recibe el subsidio:
		'' [Digito de verificación de la identificación de la empresa que recibe el subsidio],
		-- Razón social de la empresa que recibe el subsidio:
		' ' [Razón social de la empresa que recibe el subsidio]
		FROM [dbo].[PostulacionFOVIS] pof (NOLOCK)
  INNER JOIN [dbo].[SolicitudPostulacion] solPost (NOLOCK) ON solPost.spoPostulacionFOVIS = pof.pofId
  INNER JOIN [dbo].[Solicitud] sol (NOLOCK) ON sol.solId = solPost.spoSolicitudGlobal
  INNER JOIN [dbo].[JefeHogar] jH (NOLOCK) ON jH.jehId = pof.pofJefeHogar
  INNER JOIN [dbo].[Afiliado] afi (NOLOCK) ON afi.afiId = jH.jehAfiliado
  INNER JOIN [dbo].[Persona] per (NOLOCK) ON afi.afiPersona = per.perId
  INNER JOIN [dbo].[PersonaDetalle] perdet (NOLOCK) ON perdet.pedPersona = per.perId
---asignadas
  INNER JOIN [dbo].[SolicitudAsignacion] solAsig (NOLOCK) ON pof.pofSolicitudAsignacion = solAsig.safId
----desembolsadas
  INNER JOIN solicitudlegalizaciondesembolso (NOLOCK) 
          ON sldPostulacionFOVIS = pofid 
		 AND sldEstadoSolicitud LIKE '%CERRADO%'
  INNER JOIN legalizaciondesembolso (NOLOCK) ON sldLegalizacionDesembolso= lgdId
   LEFT JOIN IntegranteHogar intHogar WITH(NOLOCK) ON intHogar.inhPostulacionFovis = pof.pofId
   LEFT JOIN Persona perIntHogar WITH(NOLOCK) ON intHogar.inhPersona = perIntHogar.perid
	   WHERE pof.pofEstadoHogar IN ('SUBSIDIO_DESEMBOLSADO' ,  'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		 AND lgdFechaTransferencia BETWEEN @fechaInicio AND @fechaFin 
 
	-- Verifica si encontró información se subsidio de vivienda
	IF EXISTS (SELECT 1 FROM #DataMaestroSV)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataMaestroSV = CURSOR FAST_FORWARD FOR
		SELECT tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
			segundoApellido, primerNombre, segundoNombre, fechaNacimAfiliado,fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
			municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, 
			isnull(codGeneroBeneficiario,codGeneroAfiliado), isnull(fechaNactipoIdBeneficiario,fechaNacimAfiliado), 
			primerApellidoBen,
			segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe
		FROM #DataMaestroSV

		OPEN @consultarDataMaestroSV
		FETCH NEXT FROM @consultarDataMaestroSV INTO
		@tipoRegistro,
		@identificadorUnicoSubsidio,
		@codCaja,
		@quienSeOtorgo,
		@tipoIdAfiliado,
		@numIdAfiliado,
		@primerApellido,
		@segundoApellido,
		@primerNombre,
		@segundoNombre,
		@fechaNacimAfiliado,
		@fechaAsigSubsidio,
		@valorSubsidio,
		@codTipoSubsidio,
		@estadoSubsidio,
		@deptoSubsidio,
		@municipioSubsidio,
		@fechaEntregaUltSub,
		@tipoIdBeneficiario,
		@numIdBeneficiario,
		@codGeneroBeneficiario,
		@fechaNactipoIdBeneficiario,
		@primerApellidoBen,
		@segundoApellidoBen,
		@primerNombreBen,
		@segundoNombreBen,
		@tipoIdEmpresaRecibe,
		@numIdEmpresaRecibe,
		@digitoVerificacion,
		@razonSocialEmpresaRecibe

		WHILE @@FETCH_STATUS = 0

		-- Agrega un registro por el jefe de hogar y luego todos los integrantes
		BEGIN
			IF (SELECT TOP 1 dMaestroTemp.tipoRegistro 
			     FROM #DataMaestroSCMModificado dMaestroTemp 
			       WHERE dMaestroTemp.numIdBeneficiario = @numIdBeneficiario) IS NULL
			BEGIN
				-- Consulta el genero del jefe de hogar
				DECLARE @codGeneroJefeHogar VARCHAR(1);
				IF (SELECT TOP 1 perDetIntHogar.pedGenero FROM PersonaDetalle perDetIntHogar
					INNER JOIN Persona per ON per.perId = perDetIntHogar.pedPersona 
					WHERE per.perNumeroIdentificacion = CAST(@numIdAfiliado AS VARCHAR)) = 'MASCULINO'
				BEGIN
					SET @codGeneroJefeHogar = 'M';
				END
				ELSE
				BEGIN
					SET @codGeneroJefeHogar = 'F';
				END
 

				IF NOT EXISTS (SELECT * FROM #DataMaestroSCMModificado WHERE numIdBeneficiario = @numIdAfiliado)
				BEGIN
				-- Agrega el jefe de hogar de primeras
				INSERT INTO #DataMaestroSCMModificado (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
					segundoApellido, primerNombre, segundoNombre, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
					municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
					segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

				 SELECT @tipoRegistro, @identificadorUnicoSubsidio, @codCaja, @quienSeOtorgo, @tipoIdAfiliado, @numIdAfiliado,
						@primerApellido, @segundoApellido, @primerNombre, @segundoNombre, @fechaAsigSubsidio, @valorSubsidio , @codTipoSubsidio,
						@estadoSubsidio, @deptoSubsidio, @municipioSubsidio, @fechaEntregaUltSub, @tipoIdAfiliado, 
						@numIdAfiliado, @codGeneroJefeHogar,
						@fechaNacimAfiliado, @primerApellido, @segundoApellido, @primerNombre, @segundoNombre,
						@tipoIdEmpresaRecibe, @numIdEmpresaRecibe, @digitoVerificacion, @razonSocialEmpresaRecibe
 			 
			   END
			END

	 
			-- Agrega el Integrante del hogar
			INSERT INTO #DataMaestroSCMModificado (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
				segundoApellido, primerNombre, segundoNombre, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
				municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
				segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

			SELECT @tipoRegistro, @identificadorUnicoSubsidio, @codCaja, @quienSeOtorgo, @tipoIdAfiliado, @numIdAfiliado,
					@primerApellido, @segundoApellido, @primerNombre, @segundoNombre, @fechaAsigSubsidio, 0, @codTipoSubsidio,
					@estadoSubsidio, @deptoSubsidio, @municipioSubsidio, @fechaEntregaUltSub, @tipoIdBeneficiario, @numIdBeneficiario, @codGeneroBeneficiario,
					@fechaNactipoIdBeneficiario, @primerApellidoBen, @segundoApellidoBen, @primerNombreBen, @segundoNombreBen, @tipoIdEmpresaRecibe, @numIdEmpresaRecibe, @digitoVerificacion, @razonSocialEmpresaRecibe
 

              
			DELETE #DataMaestroSCMModificado WHERE numIdBeneficiario = @numIdAfiliado AND valorSubsidio = 0
		 	
			SET @numIdAfiliado = NULL

			----CAMBIO PARA FOVIS


		FETCH NEXT FROM @consultarDataMaestroSV INTO
		@tipoRegistro,
		@identificadorUnicoSubsidio,
		@codCaja,
		@quienSeOtorgo,
		@tipoIdAfiliado,
		@numIdAfiliado,
		@primerApellido,
		@segundoApellido,
		@primerNombre,
		@segundoNombre,
		@fechaNacimAfiliado,
		@fechaAsigSubsidio,
		@valorSubsidio,
		@codTipoSubsidio,
		@estadoSubsidio,
		@deptoSubsidio,
		@municipioSubsidio,
		@fechaEntregaUltSub,
		@tipoIdBeneficiario,
		@numIdBeneficiario,
		@codGeneroBeneficiario,
		@fechaNactipoIdBeneficiario,
		@primerApellidoBen,
		@segundoApellidoBen,
		@primerNombreBen,
		@segundoNombreBen,
		@tipoIdEmpresaRecibe,
		@numIdEmpresaRecibe,
		@digitoVerificacion,
		@razonSocialEmpresaRecibe
		END
		CLOSE @consultarDataMaestroSV;
		DEALLOCATE @consultarDataMaestroSV;
	END


---	SELECT '#DataMaestroSCMModificado',* FROM  #DataMaestroSCMModificado

	-- Sección Cuota Monetaria
	DECLARE @consultarDataMaestroSCM AS CURSOR;

	-- Tabla temporal que almacena la data detallada de la lógica del reporte
	DROP TABLE IF EXISTS #DataMaestroSCM;
	CREATE TABLE #DataMaestroSCM (tipoRegistro SMALLINT, identificadorUnicoSubsidio varchar(9), codCaja VARCHAR(6), quienSeOtorgo SMALLINT, tipoIdAfiliado VARCHAR(3), numIdAfiliado BIGINT, primerApellido VARCHAR(100),
		segundoApellido VARCHAR(100), primerNombre VARCHAR(100), segundoNombre VARCHAR(100), codGeneroAfiliado VARCHAR(1), fechaNacimAfiliado DATE, fechaAsigSubsidio varchar(10), 
		valorSubsidio BIGINT, codTipoSubsidio SMALLINT, estadoSubsidio SMALLINT, deptoSubsidio SMALLINT,
		municipioSubsidio varchar(3), fechaEntregaUltSub DATE, tipoIdBeneficiario VARCHAR(3), 
		numIdBeneficiario varchar(17), codGeneroBeneficiario VARCHAR(1), fechaNactipoIdBeneficiario DATE, primerApellidoBen VARCHAR(100),
		segundoApellidoBen VARCHAR(100), primerNombreBen VARCHAR(100), segundoNombreBen VARCHAR(100), 
		tipoIdEmpresaRecibe VARCHAR(3), numIdEmpresaRecibe VARCHAR(17), digitoVerificacion VARCHAR(1), razonSocialEmpresaRecibe VARCHAR(255))

	-- LLena la tabla temporal con los subsidios
	INSERT INTO #DataMaestroSCM (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
		segundoApellido, primerNombre, segundoNombre, codGeneroAfiliado, fechaNacimAfiliado, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
		municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
		segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

		SELECT 
		-- Tipo de registro: 2. Valor que significa que el registro es de detalle
		2 [Tipo de registro],
		-- Identificador único del subsidio: Para cuota es dsaId Detalle Subsidio Asignado
		dSubAsig.dsaId [Identificador único del subsidio],
		-- Código caja de compensación familiar
		@codigoCaja [Código caja de compensación familiar],
		-- A quien se otorgó el subsidio: Desde Genesys solo se obtiene el dato de todos los afiliados a los que se les otorgo el subsidio
		-- A quien se otorgó el subsidio: Desde Genesys solo se obtiene el dato de todos los afiliados a los que se les otorgo el subsidio
		CASE 
			WHEN (SELECT TOP 1 benPersona
			        FROM persona afiPer
			         INNER JOIN beneficiario on benpersona = perid 
			    WHERE benPersona = per.perId) IS NOT NULL
				THEN 1 -- Afiliado
			WHEN (SELECT TOP 1 empPer.empId FROM Empresa empPer WHERE empPer.emppersona = per.perId) IS NOT NULL
				THEN 3 -- Aportante
			ELSE 2 -- No Afiliado
		END [A quien se otorgó el subsidio],
		-- Tipo de identificación del afiliado o jefe cabeza de hogar:
		CASE (
			SELECT perAfi.perTipoIdentificacion FROM Afiliado afi 
				INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
				WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal)
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END [Tipo de identificación del afiliado o jefe cabeza de hogar],

		-- Número de identificación del afiliado o jefe cabeza de hogar: 
		(SELECT perAfi.perNumeroIdentificacion FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal) [Número de identificación del afiliado o jefe cabeza de hogar],

		-- Primer Apellido
		(SELECT perAfi.perPrimerApellido FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal) [Primer Apellido],

		-- Segundo Apellido
		(SELECT perAfi.perSegundoApellido FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal) [Segundo Apellido],

		-- Primer Nombre
		(SELECT perAfi.perPrimerNombre FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal) [Primer Nombre],

		-- Segundo Nombre
		(SELECT perAfi.perSegundoNombre FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal) [Segundo Nombre],

		-- Codigo genero del afiliado (esta columna se usa en el cursor)
		CASE (SELECT detPerAfi.pedGenero FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal)
			WHEN 'MASCULINO' THEN 'M'
			WHEN 'FEMENINO' THEN 'F'
		END [Código género del afiliado o jefe cabeza de hogar],
		-- Fecha de nacimiento del afiliado (esta columna se usa en el cursor)
		CONVERT (DATE, (SELECT detPerAfi.pedFechaNacimiento FROM Afiliado afi 
			INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
			INNER JOIN PersonaDetalle detPerAfi ON detPerAfi.pedPersona = perAfi.perId
			WHERE afi.afiId = dSubAsig.dsaAfiliadoPrincipal)) as [Fecha de nacimiento del afiliado o jefe cabeza de hogar],

		-- Fecha de asignación del subsidio: AAAA-MM-DD
		--CONVERT (DATE, dSubAsig.dsaFechaHoraCreacion) [Fecha de asignación del subsidio],
		'' AS [Fecha de asignación del subsidio],
		-- Valor del subsidio: "Para cuota monetaria, corresponde al valor del campo "valor de subsidio asignado"
		CAST(dSubAsig.dsaValorSubsidioMonetario AS INT) [Valor del subsidio],
		-- Código tipo de subsidio: 1 para Subsidio económico (cuota monetaria)
		1 AS [Código tipo de subsidio],
		3 AS [Estado del subsidio], -- se envía siempre estado 3 pues siempre se deben mostrar los subsidios con derecho asignado de origien liquidacion sin importar el metodo de pago
		-- Departamento donde recibe el subsidio: Departamento de ubicación de la caja de compensación
		(SELECT dep.depCodigo 
		     FROM SitioPago sPag 
			INNER JOIN Infraestructura as it on sPag.sipInfraestructura = it.infId
			INNER JOIN Municipio as mun on mun.munId = it.infMunicipio
			INNER JOIN Departamento as dep on dep.depId = mun.munDepartamento
			WHERE sPag.sipId = cAdminSub.casSitioDePago) AS [Departamento donde recibe el subsidio],

		-- Municipio donde recibe el subsidio: Municipio de ubicación de la caja de compensación
		(SELECT RIGHT(mun.munCodigo,3) FROM SitioPago sPag 
			INNER JOIN Infraestructura as it on sPag.sipInfraestructura = it.infId
			INNER JOIN Municipio as mun on mun.munId = it.infMunicipio
			INNER JOIN Departamento as dep on dep.depId = mun.munDepartamento
			WHERE sPag.sipId = cAdminSub.casSitioDePago) AS [Municipio donde recibe el subsidio],

		-- Fecha de entrega del último subsidio: dsaFechaTransaccionRetiro
		-- Si el subsidio es Otorgado (1) la fecha debe estar vacía
		--CASE 
		-- 	WHEN dSubAsig.dsaEstado = 'DERECHO_ASIGNADO' AND cAdminSub.casEstadoTransaccionSubsidio IN ('GENERADO', 'ENVIADO', 'APLICADO') THEN NULL
		--	WHEN dSubAsig.dsaFechaTransaccionRetiro <> '' OR dSubAsig.dsaFechaTransaccionRetiro IS NOT NULL THEN dSubAsig.dsaFechaTransaccionRetiro
		--	ElSE CONVERT (DATE, dSubAsig.dsaFechaHoraCreacion)
		--END [Fecha de entrega del último subsidio],
		CONVERT (DATE, dSubAsig.dsaFechaHoraCreacion) [Fecha de entrega del último subsidio],
		-- Datos que corresponden al beneficiario y sólo aplica para Cuota monetaria
		-- Tipo de identificación del beneficiario
		CASE per.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END [Tipo de identificación del beneficiario],
		-- Número de identificación del beneficiario
		per.perNumeroIdentificacion [Número de identificación del beneficiario],
		CASE perDet.pedGenero
			WHEN 'MASCULINO' THEN 'M'
			WHEN 'FEMENINO' THEN 'F'
		END [Código género del beneficiario],
		-- Fecha de nacimiento del beneficiario
		CONVERT (DATE, perDet.pedFechaNacimiento) [Fecha de nacimiento del beneficiario],
		-- Primer Apellido del Beneficiario
		per.perPrimerApellido [Primer Apellido del Beneficiario],
		-- Segundo Apellido del Beneficiario
		per.perSegundoApellido [Segundo Apellido del Beneficiario],
		-- Primer Nombre del Beneficiario
		per.perPrimerNombre [Primer Nombre del Beneficiario],
		-- Segundo Nombre del Beneficiario
		per.perSegundoNombre [Segundo Nombre del Beneficiario],
		'' AS [Tipo de identificación de la empresa que recibe el subsidio],
		'' AS [Número de identificación de la empresa que recibe el subsidio],	 
		'' AS [Digito de verificación de la identificación de la empresa que recibe el subsidio],
		' ' AS [Razón social de la empresa que recibe el subsidio]
		FROM [dbo].[DetalleSubsidioAsignado] dSubAsig (NOLOCK)
		INNER JOIN [dbo].[CuentaAdministradorSubsidio] cAdminSub (NOLOCK) ON cAdminSub.casId = dSubAsig.dsaCuentaAdministradorSubsidio
		INNER JOIN [dbo].[BeneficiarioDetalle] benDet (NOLOCK) ON benDet.bedId = dSubAsig.dsaBeneficiarioDetalle
		INNER JOIN [dbo].[PersonaDetalle] perDet (NOLOCK) ON perDet.pedId = benDet.bedPersonaDetalle
		INNER JOIN [dbo].[Persona] as  per (NOLOCK) ON per.perId = perDet.pedPersona
		WHERE dSubAsig.dsaEstado IN ( 'DERECHO_ASIGNADO' ,'ANULADO_REEMPLAZADO')
			AND dSubAsig.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
			-- AND cAdminSub.casEstadoTransaccionSubsidio IN ('COBRADO', 'APLICADO', 'ENVIADO', 'SOLICITADO') 
			AND CONVERT(DATE,dSubAsig.dsaFechaHoraCreacion) BETWEEN  @fechaInicio  AND  @fechaFin 

 
	-- Verifica si encontró información se subsidio cuota monetaria
	IF EXISTS (SELECT 1 FROM #DataMaestroSCM)
	BEGIN
		-- Asigna la data al cursor
		SET @consultarDataMaestroSCM = CURSOR FAST_FORWARD FOR
		SELECT tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
			segundoApellido, primerNombre, segundoNombre, codGeneroAfiliado, fechaNacimAfiliado, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
			municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
			segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe
		FROM #DataMaestroSCM

		OPEN @consultarDataMaestroSCM
		FETCH NEXT FROM @consultarDataMaestroSCM INTO
		@tipoRegistro,
		@identificadorUnicoSubsidio,
		@codCaja,
		@quienSeOtorgo,
		@tipoIdAfiliado,
		@numIdAfiliado,
		@primerApellido,
		@segundoApellido,
		@primerNombre,
		@segundoNombre,
		@codGeneroAfiliado,
		@fechaNacimAfiliado,
		@fechaAsigSubsidio,
		@valorSubsidio,
		@codTipoSubsidio,
		@estadoSubsidio,
		@deptoSubsidio,
		@municipioSubsidio,
		@fechaEntregaUltSub,
		@tipoIdBeneficiario,
		@numIdBeneficiario,
		@codGeneroBeneficiario,
		@fechaNactipoIdBeneficiario,
		@primerApellidoBen,
		@segundoApellidoBen,
		@primerNombreBen,
		@segundoNombreBen,
		@tipoIdEmpresaRecibe,
		@numIdEmpresaRecibe,
		@digitoVerificacion,
		@razonSocialEmpresaRecibe

		WHILE @@FETCH_STATUS = 0

		-- Por cada item (Beneficiario) entonces agrega un afiliado
		BEGIN
			-- Agrega el AFILIADO como registro nuevo
			INSERT INTO #DataMaestroSCMModificado (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
				segundoApellido, primerNombre, segundoNombre, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
				municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
				segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

				SELECT @tipoRegistro, @identificadorUnicoSubsidio, @codCaja, @quienSeOtorgo, @tipoIdAfiliado, @numIdAfiliado,
					@primerApellido, @segundoApellido, @primerNombre, @segundoNombre, @fechaAsigSubsidio, 0, @codTipoSubsidio,
					@estadoSubsidio, @deptoSubsidio, @municipioSubsidio, @fechaEntregaUltSub, @tipoIdAfiliado, @numIdAfiliado, @codGeneroAfiliado, @fechaNacimAfiliado, @primerApellido, @segundoApellido, @primerNombre, @segundoNombre,
					@tipoIdEmpresaRecibe, @numIdEmpresaRecibe, @digitoVerificacion, @razonSocialEmpresaRecibe

			-- Agrega el BENEFICIARIO
			INSERT INTO #DataMaestroSCMModificado (tipoRegistro, identificadorUnicoSubsidio, codCaja, quienSeOtorgo, tipoIdAfiliado, numIdAfiliado, primerApellido,
				segundoApellido, primerNombre, segundoNombre, fechaAsigSubsidio, valorSubsidio, codTipoSubsidio, estadoSubsidio, deptoSubsidio,
				municipioSubsidio, fechaEntregaUltSub, tipoIdBeneficiario, numIdBeneficiario, codGeneroBeneficiario, fechaNactipoIdBeneficiario, primerApellidoBen,
				segundoApellidoBen, primerNombreBen, segundoNombreBen, tipoIdEmpresaRecibe, numIdEmpresaRecibe, digitoVerificacion, razonSocialEmpresaRecibe)

				SELECT @tipoRegistro, @identificadorUnicoSubsidio, @codCaja, @quienSeOtorgo, @tipoIdAfiliado, @numIdAfiliado,
					@primerApellido, @segundoApellido, @primerNombre, @segundoNombre, @fechaAsigSubsidio, @valorSubsidio, @codTipoSubsidio,
					@estadoSubsidio, @deptoSubsidio, @municipioSubsidio, @fechaEntregaUltSub, @tipoIdBeneficiario, @numIdBeneficiario, @codGeneroBeneficiario,
					@fechaNactipoIdBeneficiario, @primerApellidoBen, @segundoApellidoBen, @primerNombreBen, @segundoNombreBen, @tipoIdEmpresaRecibe, @numIdEmpresaRecibe, @digitoVerificacion, @razonSocialEmpresaRecibe


		FETCH NEXT FROM @consultarDataMaestroSCM INTO
						@tipoRegistro,
						@identificadorUnicoSubsidio,
						@codCaja,
						@quienSeOtorgo,
						@tipoIdAfiliado,
						@numIdAfiliado,
						@primerApellido,
						@segundoApellido,
						@primerNombre,
						@segundoNombre,
						@codGeneroAfiliado,
						@fechaNacimAfiliado,
						@fechaAsigSubsidio,
						@valorSubsidio,
						@codTipoSubsidio,
						@estadoSubsidio,
						@deptoSubsidio,
						@municipioSubsidio,
						@fechaEntregaUltSub,
						@tipoIdBeneficiario,
						@numIdBeneficiario,
						@codGeneroBeneficiario,
						@fechaNactipoIdBeneficiario,
						@primerApellidoBen,
						@segundoApellidoBen,
						@primerNombreBen,
						@segundoNombreBen,
						@tipoIdEmpresaRecibe,
						@numIdEmpresaRecibe,
						@digitoVerificacion,
						@razonSocialEmpresaRecibe
		END
		CLOSE @consultarDataMaestroSCM;
		DEALLOCATE @consultarDataMaestroSCM;

		SELECT 
			tablaFinalSCM.tipoRegistro [Tipo de registro],
			tablaFinalSCM.identificadorUnicoSubsidio [Identificador único del subsidio],
			tablaFinalSCM.codCaja [Código caja de compensación familiar],
			tablaFinalSCM.quienSeOtorgo [A quien se otorgó el subsidio],
			tablaFinalSCM.tipoIdAfiliado [Tipo de identificación del afiliado o jefe cabeza de hogar],
			tablaFinalSCM.numIdAfiliado [Número de identificación del afiliado o jefe cabeza de hogar],
			tablaFinalSCM.primerApellido [Primer apellido],
			tablaFinalSCM.segundoApellido [Segundo apellido],
			tablaFinalSCM.primerNombre [Primer nombre],
			tablaFinalSCM.segundoNombre [Segundo nombre],
			tablaFinalSCM.fechaAsigSubsidio [Fecha de asignación del subsidio],
			tablaFinalSCM.valorSubsidio [Valor del subsidio],
			tablaFinalSCM.codTipoSubsidio [Código tipo de subsidio],
			tablaFinalSCM.estadoSubsidio [Estado del subsidio],
			tablaFinalSCM.deptoSubsidio [Departamento donde recibe el subsidio],
			tablaFinalSCM.municipioSubsidio [Municipio donde recibe el subsidio],
			tablaFinalSCM.fechaEntregaUltSub [Fecha de entrega del último subsidio],
			tablaFinalSCM.tipoIdBeneficiario [Tipo de identificación del beneficiario],
			tablaFinalSCM.numIdBeneficiario [Número de identificación del beneficiario],
			tablaFinalSCM.codGeneroBeneficiario [Código género del beneficiario],
			tablaFinalSCM.fechaNactipoIdBeneficiario [Fecha de nacimiento del beneficiario],
			tablaFinalSCM.primerApellidoBen [Primer apellido del beneficiario],
			tablaFinalSCM.segundoApellidoBen [Segundo apellido del beneficiario],
			tablaFinalSCM.primerNombreBen [Primer nombre del beneficiario],
			tablaFinalSCM.segundoNombreBen [Segundo nombre del beneficiario],
			tablaFinalSCM.tipoIdEmpresaRecibe [Tipo de identificación de la empresa que recibe el subsidio],
			tablaFinalSCM.numIdEmpresaRecibe [Número de identificación de la empresa que recibe el subsidio],
			tablaFinalSCM.digitoVerificacion [Dígito de verificación de la identificación de la empresa que recibe el subsidio],
			tablaFinalSCM.razonSocialEmpresaRecibe [Razón social de la empresa que recibe el subsidio]
		FROM #DataMaestroSCMModificado tablaFinalSCM
	END
	ELSE 
	BEGIN
		SELECT 
			tablaMaestroSVElse.tipoRegistro [Tipo de registro],
			tablaMaestroSVElse.identificadorUnicoSubsidio [Identificador único del subsidio],
			tablaMaestroSVElse.codCaja [Código caja de compensación familiar],
			tablaMaestroSVElse.quienSeOtorgo [A quien se otorgó el subsidio],
			tablaMaestroSVElse.tipoIdAfiliado [Tipo de identificación del afiliado o jefe cabeza de hogar],
			tablaMaestroSVElse.numIdAfiliado [Número de identificación del afiliado o jefe cabeza de hogar],
			tablaMaestroSVElse.primerApellido [Primer Apellido],
			tablaMaestroSVElse.segundoApellido [Segundo Apellido],
			tablaMaestroSVElse.primerNombre [Primer Nombre],
			tablaMaestroSVElse.segundoNombre [Segundo Nombre],
			tablaMaestroSVElse.fechaAsigSubsidio [Fecha de asignación del subsidio],
			tablaMaestroSVElse.valorSubsidio [Valor del subsidio],
			tablaMaestroSVElse.codTipoSubsidio [Código tipo de subsidio],
			tablaMaestroSVElse.estadoSubsidio [Estado del subsidio],
			tablaMaestroSVElse.deptoSubsidio [Departamento donde recibe el subsidio],
			tablaMaestroSVElse.municipioSubsidio [Municipio donde recibe el subsidio],
			tablaMaestroSVElse.fechaEntregaUltSub [Fecha de entrega del último subsidio],
			tablaMaestroSVElse.tipoIdBeneficiario [Tipo de identificación del beneficiario],
			tablaMaestroSVElse.numIdBeneficiario [Número de identificación del beneficiario],
			tablaMaestroSVElse.codGeneroBeneficiario [Código género del beneficiario],
			tablaMaestroSVElse.fechaNactipoIdBeneficiario [Fecha de nacimiento del beneficiario],
			tablaMaestroSVElse.primerApellidoBen [Primer Apellido del Beneficiario],
			tablaMaestroSVElse.segundoApellidoBen [Segundo Apellido del Beneficiario],
			tablaMaestroSVElse.primerNombreBen [Primer Nombre del Beneficiario],
			tablaMaestroSVElse.segundoNombreBen [Segundo Nombre del Beneficiario],
			tablaMaestroSVElse.tipoIdEmpresaRecibe [Tipo de identificación de la empresa que recibe el subsidio],
			tablaMaestroSVElse.numIdEmpresaRecibe [Número de identificación de la empresa que recibe el subsidio],
			tablaMaestroSVElse.digitoVerificacion [Digito de verificación de la identificación de la empresa que recibe el subsidio],
			tablaMaestroSVElse.razonSocialEmpresaRecibe [Razón social de la empresa que recibe el subsidio]
		FROM #DataMaestroSV tablaMaestroSVElse
	 
		UNION ALL
		SELECT
			tablaMaestroSCMElse.tipoRegistro [Tipo de registro],
			tablaMaestroSCMElse.identificadorUnicoSubsidio [Identificador único del subsidio],
			tablaMaestroSCMElse.codCaja [Código caja de compensación familiar],
			tablaMaestroSCMElse.quienSeOtorgo [A quien se otorgó el subsidio],
			tablaMaestroSCMElse.tipoIdAfiliado [Tipo de identificación del afiliado o jefe cabeza de hogar],
			tablaMaestroSCMElse.numIdAfiliado [Número de identificación del afiliado o jefe cabeza de hogar],
			tablaMaestroSCMElse.primerApellido [Primer Apellido],
			tablaMaestroSCMElse.segundoApellido [Segundo Apellido],
			tablaMaestroSCMElse.primerNombre [Primer Nombre],
			tablaMaestroSCMElse.segundoNombre [Segundo Nombre],
			tablaMaestroSCMElse.fechaAsigSubsidio [Fecha de asignación del subsidio],
			tablaMaestroSCMElse.valorSubsidio [Valor del subsidio],
			tablaMaestroSCMElse.codTipoSubsidio [Código tipo de subsidio],
			tablaMaestroSCMElse.estadoSubsidio [Estado del subsidio],
			tablaMaestroSCMElse.deptoSubsidio [Departamento donde recibe el subsidio],
			tablaMaestroSCMElse.municipioSubsidio [Municipio donde recibe el subsidio],
			tablaMaestroSCMElse.fechaEntregaUltSub [Fecha de entrega del último subsidio],
			tablaMaestroSCMElse.tipoIdBeneficiario [Tipo de identificación del beneficiario],
			tablaMaestroSCMElse.numIdBeneficiario [Número de identificación del beneficiario],
			tablaMaestroSCMElse.codGeneroBeneficiario [Código género del beneficiario],
			tablaMaestroSCMElse.fechaNactipoIdBeneficiario [Fecha de nacimiento del beneficiario],
			tablaMaestroSCMElse.primerApellidoBen [Primer Apellido del Beneficiario],
			tablaMaestroSCMElse.segundoApellidoBen [Segundo Apellido del Beneficiario],
			tablaMaestroSCMElse.primerNombreBen [Primer Nombre del Beneficiario],
			tablaMaestroSCMElse.segundoNombreBen [Segundo Nombre del Beneficiario],
			tablaMaestroSCMElse.tipoIdEmpresaRecibe [Tipo de identificación de la empresa que recibe el subsidio],
			tablaMaestroSCMElse.numIdEmpresaRecibe [Número de identificación de la empresa que recibe el subsidio],
			tablaMaestroSCMElse.digitoVerificacion [Digito de verificación de la identificación de la empresa que recibe el subsidio],
			tablaMaestroSCMElse.razonSocialEmpresaRecibe [Razón social de la empresa que recibe el subsidio]
		FROM #DataMaestroSCM tablaMaestroSCMElse
		 ORDER BY  codTipoSubsidio, identificadorUnicoSubsidio ASC
	END

	
END