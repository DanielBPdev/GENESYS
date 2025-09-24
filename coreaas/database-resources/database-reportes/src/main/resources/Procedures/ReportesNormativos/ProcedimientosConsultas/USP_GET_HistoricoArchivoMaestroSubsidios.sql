-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoArchivoMaestroSubsidios

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
		INSERT rno.HistoricoArchivoMaestroSubsidios(
			hmsFechaHistorico,
			hmsTipoRegistro,
			hmsIdentificadorUnicoSubsidio,
			hmsCodigoCCF,
			hmsAQuienSeOtorgoSubsidio,
			hmsTipoIdAfiliado,
			hmsNumeroIdAfiliado,
			hmsPrimerApellidoAfiliado,
			hmsSegundoApellidoAfiliado,
			hmsPrimerNombreAfiliado,
			hmsSegundoNombreAfiliado,
			hmsFechaAsignacionSubsidio,
			hmsValorSubsidio,
			hmsCodigoTipoSubsidio,
			hmsEstadoSubsidio,
			hmsDepartamentoSubsidio,
			hmsMunicipioSubsidio,
			hmsFechaEntregaUltimoSubsidio,
			hmsTipoIdbeneficiario,
			hmsNumeroIdBeneficiario,
			hmsCodigoGeneroBeneficiario,
			hmsFechaNacimientoBeneficiario,
			hmsPrimerApellidoBeneficiario,
			hmsSegundoApellidoBeneficiario,
			hmsPrimerNombreBeneficiario,
			hmsSegundoNombreBeneficiario,
			hmsTipoIdEmpresaRecibeSubsidio,
			hmsNumeroIdEmpresaRecibeSubsidio,
			hmsDigitoVerificacionIdEmpresaSubsidio,
			hmsRazonSocialEmpresaSubsidio,
			hmsFechaInicialReporte,
			hmsFechaFinalReporte)
		SELECT @fechaFin as fechafin,
			2 AS tipoRegistro,
			detalle.dsaId AS identificadorUnicoSubsidio,
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
			1 AS aQuienSeOtorgoSubsidio,
			CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdAfiliado,
			perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
			substring(perAfiCore.perPrimerApellido,0,61) AS primerApellidoAfiliado,
			substring(perAfiCore.perSegundoApellido,0,61) AS segundoApellidoAfiliado,
			substring(perAfiCore.perPrimerNombre,0,61) AS primerNombreAfiliado,
			substring(perAfiCore.perSegundoNombre,0,61) AS segundoNombreAfiliado,
			null AS fechaAsignacionSubsidio,			
			0 AS valorSubsidio,
			1 AS codigoTipoSubsidio, 
			CASE 
				--WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'RETENIDO' THEN 2
				WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'ANULADO' THEN 5
			    WHEN dbo.CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'COBRADO' OR 
					 dbo.CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'APLICADO'OR 
					 dbo.CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'ENVIADO'  
					 THEN 3
			END AS estadoSubsidio,

			NULL AS departamentoSubsidio,
			NULL AS municipioSubsidio,
			NULL AS fechaEntregaUltimoSubsidio,
			CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdbeneficiario,
					perAfiCore.perNumeroIdentificacion numeroIdBeneficiario,
			CASE pd.pedGenero 
				WHEN 'MASCULINO' THEN 'M'
				WHEN 'FEMENINO' THEN 'F'
			END AS codigoGeneroBeneficiario,
			pd.pedFechaNacimiento AS fechaNacimientoBeneficiario,
			substring(perAfiCore.perPrimerApellido,0,61) AS primerApellidoBeneficiario,
			substring(perAfiCore.perSegundoApellido,0,61) AS segundoApellidoBeneficiario,
			substring(perAfiCore.perPrimerNombre,0,61)  AS primerNombreBeneficiario,
			substring(perAfiCore.perSegundoNombre,0,61) AS segundoNombreBeneficiario, 
			null AS tipoIdEmpresaRecibeSubsidio,
			null AS numeroIdEmpresaRecibeSubsidio,
			null AS digitoVerificacionIdEmpresaSubsidio,
			null AS razonSocialEmpresaSubsidio,
			@fechaInicio as fechaInicio,
			@fechaFin	as fechafin2 
			FROM dbo.SolicitudLiquidacionSubsidio
			INNER JOIN dbo.DetalleSubsidioAsignado AS detalle ON dbo.SolicitudLiquidacionSubsidio.slsId = detalle.dsaSolicitudLiquidacionSubsidio
			AND detalle.dsaEstado = 'DERECHO_ASIGNADO'
			INNER JOIN dbo.CuentaAdministradorSubsidio ON detalle .dsaCuentaAdministradorSubsidio = dbo.CuentaAdministradorSubsidio.casId
			INNER JOIN dbo.Afiliado AS afiCore ON detalle.dsaAfiliadoPrincipal = afiCore.afiId
			INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
			--
			left join dbo.PersonaDetalle pd ON pd.pedPersona = perAfiCore.perId
			--
			INNER JOIN dbo.Beneficiario AS benCore ON detalle.dsaBeneficiarioDetalle = benCore.benBeneficiarioDetalle
			INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona
			INNER JOIN dbo.PersonaDetalle AS perDetBenCore ON perDetBenCore.pedPersona = perBenCore.perId
			--INNER JOIN dbo.Empleador ON detalle.dsaEmpleador = dbo.Empleador.empId
			--INNER JOIN dbo.Empresa ON dbo.Empleador.empEmpresa = dbo.Empresa.empId
			--INNER JOIN dbo.Persona AS perEmpresaCore ON dbo.Empresa.empPersona = perEmpresaCore.perId
			WHERE detalle.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
			--RESTRICCION TIPOS DOC
			AND (CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END) IN ('CC','TI','CE','PA','CD')
			AND dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio<>'GENERADO'
			--AND pd.pedGenero IN ('MASCULINO','')
--Beneficiarios cuota monetaria
union all
SELECT @fechaFin as fechafin,
			2 AS tipoRegistro,
			detalle.dsaId AS identificadorUnicoSubsidio,
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
			1 AS aQuienSeOtorgoSubsidio,
			CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdAfiliado,
			perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
			substring(perAfiCore.perPrimerApellido,0,61) AS primerApellidoAfiliado,
			substring(perAfiCore.perSegundoApellido,0,61) AS segundoApellidoAfiliado,
			substring(perAfiCore.perPrimerNombre,0,61) AS primerNombreAfiliado,
			substring(perAfiCore.perSegundoNombre,0,61) AS segundoNombreAfiliado,
			CASE WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio <> 'COBRADO' AND
					 dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio <> 'APLICADO' AND
					 dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio <> 'ENVIADO' THEN
			CAST(dbo.SolicitudLiquidacionSubsidio.slsFechaInicio AS DATE) END AS fechaAsignacionSubsidio,
			CAST(cast(detalle.dsaValorSubsidioMonetario as bigint)  AS VARCHAR) AS valorSubsidio,
			1 AS codigoTipoSubsidio, 
			CASE 
				--WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'RETENIDO' THEN 2
				WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'ANULADO' THEN 5
			    WHEN dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'COBRADO' OR 
					 dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'APLICADO'OR 
					 dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'ENVIADO'  
					 THEN 3
			END AS estadoSubsidio,

			NULL AS departamentoSubsidio,
			NULL AS municipioSubsidio,
			NULL AS fechaEntregaUltimoSubsidio,
			CASE perBenCore.perTipoIdentificacion 
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdbeneficiario,
			perBenCore.perNumeroIdentificacion AS numeroIdBeneficiario,
			CASE perDetBenCore.pedGenero 
				WHEN 'MASCULINO' THEN 'M'
				WHEN 'FEMENINO' THEN 'F'
			END AS codigoGeneroBeneficiario,
			perDetBenCore.pedFechaNacimiento AS fechaNacimientoBeneficiario,
			substring(perBenCore.perPrimerApellido,0,61) AS primerApellidoBeneficiario,
			substring(perBenCore.perSegundoApellido,0,61) AS segundoApellidoBeneficiario,
			substring(perBenCore.perPrimerNombre,0,61)  AS primerNombreBeneficiario,
			substring(perBenCore.perSegundoNombre,0,61) AS segundoNombreBeneficiario, 
			null AS tipoIdEmpresaRecibeSubsidio,
			null AS numeroIdEmpresaRecibeSubsidio,
			null digitoVerificacionIdEmpresaSubsidio,
			null AS razonSocialEmpresaSubsidio,
			@fechaInicio as fechainicio,
			@fechaFin as fechafin2	
			FROM dbo.SolicitudLiquidacionSubsidio
			INNER JOIN dbo.DetalleSubsidioAsignado AS detalle ON dbo.SolicitudLiquidacionSubsidio.slsId = detalle.dsaSolicitudLiquidacionSubsidio
			AND detalle.dsaEstado = 'DERECHO_ASIGNADO'
			INNER JOIN dbo.CuentaAdministradorSubsidio ON detalle .dsaCuentaAdministradorSubsidio = dbo.CuentaAdministradorSubsidio.casId
			INNER JOIN dbo.Afiliado AS afiCore ON detalle.dsaAfiliadoPrincipal = afiCore.afiId
			INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
			INNER JOIN dbo.Beneficiario AS benCore ON detalle.dsaBeneficiarioDetalle = benCore.benBeneficiarioDetalle
			INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona
			INNER JOIN dbo.PersonaDetalle AS perDetBenCore ON perDetBenCore.pedPersona = perBenCore.perId
			--INNER JOIN dbo.Empleador ON detalle.dsaEmpleador = dbo.Empleador.empId
			--INNER JOIN dbo.Empresa ON dbo.Empleador.empEmpresa = dbo.Empresa.empId
			--INNER JOIN dbo.Persona AS perEmpresaCore ON dbo.Empresa.empPersona = perEmpresaCore.perId
			WHERE detalle.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
			--RESTRICCION TIPOS DOC
			AND (CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END) IN ('CC','TI','CE','PA','CD')
			AND dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio<>'GENERADO'
			AND  perBenCore.perTipoIdentificacion IN ('REGISTRO_CIVIL','CEDULA_CIUDADANIA','TARJETA_IDENTIDAD','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')

--fovis afiliados
						UNION ALL
			SELECT @fechaFin,
			2 AS tipoRegistro,
			(select TOP 1 sy.solnumeroradicacion 
			from  postulacionfovis pofv 
			inner join solicitudpostulacion spo on spo.spopostulacionfovis=pofv.pofid
			inner join solicitud sy on sy.solid=spo.sposolicitudglobal
			inner join postulacionasignacion pas on pas.pasPostulacionFovis=pofv.pofid
			where pas.pasresultadoasignacion='ESTADO_ASIGNADO'  
			and pofv.pofid=poft.pofid) AS identificadorUnicoSubsidio, 
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
			1 AS aQuienSeOtorgoSubsidio, 
			CASE perJefeHogarAfiFovis.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdAfiliado,
			perJefeHogarAfiFovis.perNumeroIdentificacion AS numeroIdAfiliado,
			substring(perJefeHogarAfiFovis.perPrimerApellido,0,61) AS primerApellidoAfiliado,
			substring(perJefeHogarAfiFovis.perSegundoApellido,0,61) AS segundoApellidoAfiliado,
			substring(perJefeHogarAfiFovis.perPrimerNombre,0,61) AS primerNombreAfiliado,
			substring(perJefeHogarAfiFovis.perSegundoNombre,0,61) AS segundoNombreAfiliado,
			CASE WHEN poft.pofEstadoHogar <> 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO' OR
					 poft.pofEstadoHogar <> 'SUBSIDIO_DESEMBOLSADO' THEN
			CAST(dbo.SolicitudAsignacion.safFechaAceptacion AS DATE) END AS fechaAsignacionSubsidio, 			
			CAST(cast((select TOP 1 pofv.pofvalorasignadosfv
			from  postulacionfovis pofv 
			inner join solicitudpostulacion spo on spo.spopostulacionfovis=pofv.pofid
			inner join solicitud sy on sy.solid=spo.sposolicitudglobal
			inner join postulacionasignacion pas on pas.pasPostulacionFovis=pofv.pofid
	where pas.pasresultadoasignacion='ESTADO_ASIGNADO'  
	and pofv.pofid=poft.pofid) as bigint) AS VARCHAR)  AS valorSubsidio,
			4 AS codigoTipoSubsidio, 
			CASE 
				WHEN poft.pofEstadoHogar = 'ASIGNADO_SIN_PRORROGA' OR
					 poft.pofEstadoHogar = 'ASIGNADO_CON_PRIMERA_PRORROGA' OR
					 poft.pofEstadoHogar = 'ASIGNADO_CON_SEGUNDA_PRORROGA' OR
					 poft.pofEstadoHogar = 'SUBSIDIO_LEGALIZADO' THEN 1
				
			    WHEN poft.pofEstadoHogar = 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO' OR
					 poft.pofEstadoHogar = 'SUBSIDIO_DESEMBOLSADO' THEN 3
			    
			END AS estadoSubsidio,
			NULL AS departamentoSubsidio,
			NULL AS municipioSubsidio,
			NULL AS fechaEntregaUltimoSubsidio, 
			CASE perJefeHogarAfiFovis.perTipoIdentificacion 
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdbeneficiario,
			perJefeHogarAfiFovis.perNumeroIdentificacion AS numeroIdBeneficiario,
			CASE perDetBenFovis.pedGenero 
				WHEN 'MASCULINO' THEN 'M'
				ELSE 'F'
			END AS codigoGeneroBeneficiario,
			perDetBenFovis.pedFechaNacimiento AS fechaNacimientoBeneficiario,
			substring(perJefeHogarAfiFovis.perPrimerApellido,0,61) AS primerApellidoBeneficiario,
			substring(perJefeHogarAfiFovis.perSegundoApellido,0,61) AS segundoApellidoBeneficiario,
			substring(perJefeHogarAfiFovis.perPrimerNombre,0,61)  AS primerNombreBeneficiario,
			substring(perJefeHogarAfiFovis.perSegundoNombre,0,61) AS segundoNombreBeneficiario,
			null AS tipoIdEmpresaRecibeSubsidio,
			null AS numeroIdEmpresaRecibeSubsidio,
			null AS digitoVerificacionIdEmpresaSubsidio,
			null AS razonSocialEmpresaSubsidio,
			@fechaInicio,
			@fechaFin
						from dbo.SolicitudAsignacion
			INNER JOIN dbo.PostulacionFOVIS poft ON poft.pofSolicitudAsignacion = dbo.SolicitudAsignacion.safId
			INNER JOIN dbo.JefeHogar ON dbo.JefeHogar.jehId = poft.pofJefeHogar
			INNER JOIN dbo.Afiliado AS afiFovis ON afiFovis.afiId =dbo.JefeHogar.jehAfiliado
			INNER JOIN dbo.Persona AS perJefeHogarAfiFovis ON perJefeHogarAfiFovis.perId = afiFovis.afiPersona
			INNER JOIN dbo.PersonaDetalle AS perDetBenFovis ON perDetBenFovis.pedPersona = perJefeHogarAfiFovis.perId
			left JOIN dbo.SolicitudLegalizacionDesembolso ON dbo.SolicitudLegalizacionDesembolso.sldPostulacionFOVIS = poft.pofId
			left JOIN (SELECT dbo.LegalizacionDesembolso.lgdFechaTransferencia AS fechaTransferencia, dbo.LegalizacionDesembolso.lgdId AS id
					    FROM dbo.LegalizacionDesembolso
			 			) AS Legalizacion 
			ON Legalizacion.id = dbo.SolicitudLegalizacionDesembolso.sldLegalizacionDesembolso
			--LEFT JOIN dbo.ProyectoSolucionVivienda ON dbo.ProyectoSolucionVivienda.psvId = poft.pofProyectoSolucionVivienda
			--LEFT JOIN dbo.Oferente ON dbo.Oferente.ofeId = dbo.ProyectoSolucionVivienda.psvOferente
			--LEFT JOIN dbo.Empresa ON dbo.Oferente.ofeEmpresa = dbo.Empresa.empId
			--LEFT JOIN dbo.Persona AS perEmpresaFovis ON perEmpresaFovis.perId = dbo.Empresa.empPersona
			WHERE poft.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA',
										'ASIGNADO_CON_SEGUNDA_PRORROGA','SUBSIDIO_DESEMBOLSADO','SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
			AND dbo.SolicitudAsignacion.safFechaAceptacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
			--
			and(CASE perJefeHogarAfiFovis.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 	END) in ('CC','TI','CE','PA','CD')

			--restricción tipos documento:
					
			AND poft.pofEstadoHogar NOT IN( 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA' , 'PENDIENTE_APROBACION_PRORROGA' ,'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA','RECHAZADO' )

-----------------------------------------	FOVIS Miembros hogar	
			UNION ALL
			SELECT @fechaFin,
			2 AS tipoRegistro,
			(select TOP 1 sy.solnumeroradicacion 
			from  postulacionfovis pofv 
			inner join solicitudpostulacion spo on spo.spopostulacionfovis=pofv.pofid
			inner join solicitud sy on sy.solid=spo.sposolicitudglobal
			inner join postulacionasignacion pas on pas.pasPostulacionFovis=pofv.pofid
	where pas.pasresultadoasignacion='ESTADO_ASIGNADO'  
	and pofv.pofid=poft.pofid) AS identificadorUnicoSubsidio, 
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
			1 AS aQuienSeOtorgoSubsidio, 
			CASE perJefeHogarAfiFovis.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdAfiliado,
			perJefeHogarAfiFovis.perNumeroIdentificacion AS numeroIdAfiliado,
			substring(perJefeHogarAfiFovis.perPrimerApellido,0,61) AS primerApellidoAfiliado,
			substring(perJefeHogarAfiFovis.perSegundoApellido,0,61) AS segundoApellidoAfiliado,
			substring(perJefeHogarAfiFovis.perPrimerNombre,0,61) AS primerNombreAfiliado,
			substring(perJefeHogarAfiFovis.perSegundoNombre,0,61) AS segundoNombreAfiliado,
			CAST(dbo.SolicitudAsignacion.safFechaAceptacion AS DATE) AS fechaAsignacionSubsidio, 			
			CAST(cast((0) as bigint) AS VARCHAR)  AS valorSubsidio,
			4 AS codigoTipoSubsidio, 
			CASE 
				WHEN poft.pofEstadoHogar = 'ASIGNADO_SIN_PRORROGA' OR
					 poft.pofEstadoHogar = 'ASIGNADO_CON_PRIMERA_PRORROGA' OR
					 poft.pofEstadoHogar = 'ASIGNADO_CON_SEGUNDA_PRORROGA' OR
					 poft.pofEstadoHogar = 'SUBSIDIO_LEGALIZADO' THEN 1
				
			    WHEN poft.pofEstadoHogar = 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO' OR
					 poft.pofEstadoHogar = 'SUBSIDIO_DESEMBOLSADO' THEN 3
			    
			END AS estadoSubsidio,
			NULL AS departamentoSubsidio,
			NULL AS municipioSubsidio,
			NULL AS fechaEntregaUltimoSubsidio, 
			CASE perBenFovis.perTipoIdentificacion 
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdbeneficiario,
			perBenFovis.perNumeroIdentificacion AS numeroIdBeneficiario,
			CASE perDetBenFovis.pedGenero 
				WHEN 'MASCULINO' THEN 'M'
				ELSE 'F'
			END AS codigoGeneroBeneficiario,
			perDetBenFovis.pedFechaNacimiento AS fechaNacimientoBeneficiario,
			substring(perBenFovis.perPrimerApellido,0,61) AS primerApellidoBeneficiario,
			substring(perBenFovis.perSegundoApellido,0,61) AS segundoApellidoBeneficiario,
			substring(perBenFovis.perPrimerNombre,0,61)  AS primerNombreBeneficiario,
			substring(perBenFovis.perSegundoNombre,0,61) AS segundoNombreBeneficiario,
			null AS tipoIdEmpresaRecibeSubsidio,
			null AS numeroIdEmpresaRecibeSubsidio,
			null AS digitoVerificacionIdEmpresaSubsidio,
			null AS razonSocialEmpresaSubsidio,
			@fechaInicio,
			@fechaFin
						from dbo.SolicitudAsignacion
			INNER JOIN dbo.PostulacionFOVIS poft ON poft.pofSolicitudAsignacion = dbo.SolicitudAsignacion.safId
			INNER JOIN dbo.JefeHogar ON dbo.JefeHogar.jehId = poft.pofJefeHogar
			INNER JOIN dbo.Afiliado AS afiFovis ON afiFovis.afiId =dbo.JefeHogar.jehAfiliado
			INNER JOIN dbo.Persona AS perJefeHogarAfiFovis ON perJefeHogarAfiFovis.perId = afiFovis.afiPersona
			inner join (select inhy.inhjefehogar, inhy.inhpersona from integrantehogar inhy where inhid=(select max(inhz.inhid) from integrantehogar inhz where  inhz.inhjefehogar= inhy.inhjefehogar)    )as benFovis ON benFovis.inhjefehogar=dbo.JefeHogar.jehId
			INNER JOIN dbo.Persona AS perBenFovis ON perBenFovis.perId = benFovis.inhPersona
			INNER JOIN dbo.PersonaDetalle AS perDetBenFovis ON perDetBenFovis.pedPersona = perBenFovis.perId
			left JOIN dbo.SolicitudLegalizacionDesembolso ON dbo.SolicitudLegalizacionDesembolso.sldPostulacionFOVIS = poft.pofId
			left JOIN (SELECT dbo.LegalizacionDesembolso.lgdFechaTransferencia AS fechaTransferencia, dbo.LegalizacionDesembolso.lgdId AS id
					    FROM dbo.LegalizacionDesembolso
			 			) AS Legalizacion 
			ON Legalizacion.id = dbo.SolicitudLegalizacionDesembolso.sldLegalizacionDesembolso
			--LEFT JOIN dbo.ProyectoSolucionVivienda ON dbo.ProyectoSolucionVivienda.psvId = poft.pofProyectoSolucionVivienda
			--LEFT JOIN dbo.Oferente ON dbo.Oferente.ofeId = dbo.ProyectoSolucionVivienda.psvOferente
			--LEFT JOIN dbo.Empresa ON dbo.Oferente.ofeEmpresa = dbo.Empresa.empId
			--LEFT JOIN dbo.Persona AS perEmpresaFovis ON perEmpresaFovis.perId = dbo.Empresa.empPersona
			WHERE poft.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA',
										'ASIGNADO_CON_SEGUNDA_PRORROGA','SUBSIDIO_DESEMBOLSADO','SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
			AND dbo.SolicitudAsignacion.safFechaAceptacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
			--
			and(CASE perJefeHogarAfiFovis.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 	END) in ('CC','TI','CE','PA','CD')

			--restricción tipos documento:
					
			AND poft.pofEstadoHogar NOT IN( 'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA' , 'PENDIENTE_APROBACION_PRORROGA' ,'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA','RECHAZADO' )
			AND perBenFovis.perTipoIdentificacion IN ('REGISTRO_CIVIL','CEDULA_CIUDADANIA','TARJETA_IDENTIDAD','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')

			--------------------------
			
			--) t
			order by 14,3,7,13
	END
	
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
