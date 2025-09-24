CREATE OR ALTER PROCEDURE [sap].[ASP_JSON_NOVEDADES_PERSONAS] @solid BIGINT  
AS
BEGIN 
			
		 ---DATOS DEMOGRAFICOS DE LA PERSONA
		 
		 set nocount on;

		SELECT DISTINCT datosnov.solid,
		                pa.perId,
						pa.perTipoIdentificacion,
						pa.perNumeroIdentificacion,
						pa.perRazonSocial,
						pa.perPrimerNombre,
						pa.perSegundoNombre,
						pa.perPrimerApellido,
						pa.perSegundoApellido,
						pda.pedId,
						pda.pedFechaNacimiento,
						pda.pedFechaExpedicionDocumento,
						pda.pedGenero,
						pda.pedNivelEducativo,
						pda.pedCabezaHogar,
						pda.pedAutorizaUsoDatosPersonales,
						pda.pedResideSectorRural,
						pda.pedEstadoCivil,
						pda.pedHabitaCasaPropia,
						pda.pedFallecido,
						pda.pedFechaFallecido,
						pda.pedBeneficiarioSubsidio,
						pda.pedEstudianteTrabajoDesarrolloHumano,
						pda.pedFechaDefuncion,
						pda.pedPersonaPadre,
						pda.pedPersonaMadre,
						pda.pedOrientacionSexual,
						pda.pedFactorVulnerabilidad,
						pda.pedPertenenciaEtnica,
						pda.pedPaisResidencia,
						ga.graNivelEducativo,
						ga.graNombre,ga.graId,
						ut.ubiAutorizacionEnvioEmail,
						ut.ubiDireccionFisica,
						ut.ubiEmail,
						ut.ubiIndicativoTelFijo,
						ut.ubiTelefonoCelular,
						ut.ubiTelefonoFijo,
						ut.ubiMunicipio,
						ut.ubiDescripcionIndicacion,
						ut.ubiSectorUbicacion,
						ut.ubiCodigoPostal,
						m.munCodigo,
						m.munDepartamento,
						m.munNombre,
						m.munId,
						op.ocuId,
						op.ocuNombre 
				  INTO #DATOS_PERSONA_TRABAJADOR
				  FROM Solicitud datosnov WITH(NOLOCK)
			INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
					ON snov.snoSolicitudGlobal = datosnov.solId
			INNER JOIN SolicitudNovedadPersona  snp  WITH(NOLOCK)
					ON snov.snoid = snpSolicitudNovedad 
			INNER JOIN Persona pa WITH(NOLOCK)
				    ON snp.snpPersona = pa.perId
			INNER JOIN PersonaDetalle pda WITH(NOLOCK)
				    ON pa.perId = pda.pedPersona
			 LEFT JOIN OcupacionProfesion op WITH(NOLOCK)
				    ON pda.pedOcupacionProfesion = op.ocuId	
			 LEFT JOIN GradoAcademico ga WITH(NOLOCK)
				    ON pda.pedGradoAcademico = ga.graId	
			INNER JOIN Ubicacion ut WITH(NOLOCK)
				    ON pa.perUbicacionPrincipal = ut.ubiId	
			 LEFT JOIN Municipio m WITH(NOLOCK)
			        ON ut.ubiMunicipio = m.munId
				 WHERE datosnov.solid = @solid ---5323709 --@solid 
								 
 CREATE NONCLUSTERED INDEX IX_DATOS_TR_ID ON #DATOS_PERSONA_TRABAJADOR (solid ,perid ASC)


 

 ---datos persona como beneficiario 
		SELECT DISTINCT datosnov.solid,
		                pb.perId,
						pb.perTipoIdentificacion,
						pb.perNumeroIdentificacion,
						pb.perRazonSocial,
						pb.perPrimerNombre,
						pb.perSegundoNombre,
						pb.perPrimerApellido,
						pb.perSegundoApellido,
						b.benid ,b.benTipoBeneficiario,
						pdb.pedId,
						pdb.pedFechaNacimiento,
						pdb.pedFechaExpedicionDocumento,
						pdb.pedGenero,
						pdb.pedNivelEducativo,
						pdb.pedCabezaHogar,
						pdb.pedAutorizaUsoDatosPersonales,
						pdb.pedResideSectorRural,
						pdb.pedEstadoCivil,
						pdb.pedHabitaCasaPropia,
						pdb.pedFallecido,
						pdb.pedFechaFallecido,
						pdb.pedBeneficiarioSubsidio,
						pdb.pedEstudianteTrabajoDesarrolloHumano,
						pdb.pedFechaDefuncion,
						pdb.pedPersonaPadre,
						pdb.pedPersonaMadre,
						pdb.pedOrientacionSexual,
						pdb.pedFactorVulnerabilidad,
						pdb.pedPertenenciaEtnica,
						pdb.pedPaisResidencia,
						ga.graNivelEducativo,
						ga.graNombre,
						ga.graId,
						ub.ubiAutorizacionEnvioEmail,
						ub.ubiDireccionFisica,
						ub.ubiEmail,
						ub.ubiIndicativoTelFijo,
						ub.ubiTelefonoCelular,
						ub.ubiTelefonoFijo,
						ub.ubiMunicipio,
						ub.ubiDescripcionIndicacion,
						ub.ubiSectorUbicacion,
						ub.ubiCodigoPostal,
						m.munCodigo,
						m.munDepartamento,
						m.munNombre,
						m.munId,
						op.ocuId,
						op.ocuNombre,
						ce.cebId,
						ce.cebBeneficiarioDetalle,	
						ce.cebFechaRecepcion, 
						ce.cebFechaVencimiento,
						ce.cebFechaCreacion,
						coiId,
						coiInvalidez,
						coiFechaReporteInvalidez,
						coiComentarioInvalidez
						coiFechaInicioInvalidez
				  INTO #DATOS_PERSONA_BENEFICIARIO
 				  FROM Solicitud datosnov WITH(NOLOCK)
			INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
					ON snov.snoSolicitudGlobal = datosnov.solId
			INNER JOIN SolicitudNovedadPersona  sne  WITH(NOLOCK)
					ON snov.snoid = snpSolicitudNovedad
		    INNER JOIN Beneficiario b WITH(NOLOCK)
					ON snpBeneficiario = b.benId
			INNER JOIN Persona pb WITH(NOLOCK)
				    ON b.benPersona = pb.perId
			INNER JOIN Ubicacion ub WITH(NOLOCK)
				    ON pb.perUbicacionPrincipal = ub.ubiId	
			 LEFT JOIN PersonaDetalle pdb WITH(NOLOCK)
				    ON pb.perId = pdb.pedPersona 
			 LEFT JOIN OcupacionProfesion op WITH(NOLOCK)
				    ON pdb.pedOcupacionProfesion = op.ocuId	
			 LEFT JOIN GradoAcademico ga WITH(NOLOCK)
				    ON pdb.pedGradoAcademico = ga.graId	
			 LEFT JOIN Municipio m WITH(NOLOCK)
			        ON ub.ubiMunicipio = m.munId
			 LEFT JOIN ( SELECT CASE WHEN cebId =  MAX(cebId) OVER (PARTITION BY cebBeneficiarioDetalle ) 
								THEN cebId  ELSE NULL END AS Cebid,
								cebBeneficiarioDetalle,
								cebFechaRecepcion,
								cebFechaVencimiento,
								cebFechaCreacion
					       FROM CertificadoEscolarBeneficiario WITH(NOLOCK)) ce
			        ON ce.cebBeneficiarioDetalle = b.benBeneficiarioDetalle
			 LEFT JOIN CondicionInvalidez ON coiPersona = b.benPersona
				 WHERE datosnov.solid = @solid ---5323715 --@solid 
 


   CREATE NONCLUSTERED INDEX IX_DATOS_BEN_ID ON #DATOS_PERSONA_BENEFICIARIO (solid,perid, benid  ASC)

  -- select * from #DATOS_PERSONA_BENEFICIARIO
  -- select * from #DATOS_PERSONA_TRABAJADOR


 DECLARE @JSON VARCHAR(MAX)

		SET @JSON =( SELECT DISTINCT
							 
									datosnov.solId AS  [inicio.idSolicitud],
									snoId AS [inicio.idSolicitudNovedad], 
									null AS [inicio.idInstancia],
									snoNovedad AS [inicio.novedadDTO.idNovedad], 
									'FRONT' AS [inicio.novedadDTO.puntoResolucion], 
									solTipoTransaccion AS [inicio.novedadDTO.novedad], 
									'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' AS [inicio.novedadDTO.rutaCualificada],
									'GENERAL' AS [inicio.novedadDTO.tipoNovedad],
									CASE WHEN snoNovedad = 231 
									     THEN 'Cambio de municipio, dirección de residencia, condición de casa propia, código postal, teléfono fijo, teléfono celular, correo electrónico, residencia en sector rural - Persona' 
										 ELSE REPLACE(REPLACE(REPLACE(LOWER(datosnov.solTipoTransaccion),'_PRESENCIAL',''),'_PERSONAS',''),'_',' ') 
										  END AS [inicio.novedadDTO.nombre], 
									solCanalRecepcion AS [inicio.canalRecepcion], 
									solMetodoEnvio AS [inicio.metodoEnvio], 
									solClasificacion AS [inicio.clasificacion], 
									solTipoTransaccion AS [inicio.tipoTransaccion],
									solObservacion AS [inicio.observaciones], 
									'EXITOSA' AS [inicio.resultadoValidacion], 
									null AS [inicio.excepcionTipoDos],
									null AS [inicio.datosEmpleador],
			
									null AS [inicio.datosPersona.idPersona],
									null AS [inicio.datosPersona.idBeneficiario],
									null AS [inicio.datosPersona.idGrupoFamiliar],
									null AS [inicio.datosPersona.idRolAfiliado],
									null AS [inicio.datosPersona.idAdministradorSubsidio],
									dt.perNumeroIdentificacion AS [inicio.datosPersona.numeroIdentificacion], 
									dt.perTipoIdentificacion AS [inicio.datosPersona.tipoIdentificacion], 
									null AS [inicio.datosPersona.tipoIdentificacionEmpleador],
									null AS [inicio.datosPersona.numeroIdentificacionEmpleador],
									null AS [inicio.datosPersona.nombreRazonSocialEmpleador],
									null AS [inicio.datosPersona.tipoSolicitanteTrabajador],
									dt.perTipoIdentificacion AS [inicio.datosPersona.tipoIdentificacionTrabajador], 
									dt.perNumeroIdentificacion AS [inicio.datosPersona.numeroIdentificacionTrabajador],
									dt.perPrimerApellido AS [inicio.datosPersona.primerApellidoTrabajador], 
									dt.perSegundoApellido AS [inicio.datosPersona.segundoApellidoTrabajador], 
									dt.perPrimerNombre AS [inicio.datosPersona.primerNombreTrabajador], 
									dt.perSegundoNombre AS [inicio.datosPersona.segundoNombreTrabajador],
									db.perTipoIdentificacion AS [inicio.datosPersona.tipoIdentificacionBeneficiario],
									db.perNumeroIdentificacion AS [inicio.datosPersona.numeroIdentificacionBeneficiario],
									null AS [inicio.datosPersona.tipoIdentificacionBeneficiarioAnterior],
									null AS [inicio.datosPersona.numeroIdentificacionBeneficiarioAnterior],
									dt.perPrimerApellido AS [inicio.datosPersona.primerApellidoBeneficiario],
									dt.perSegundoApellido AS [inicio.datosPersona.segundoApellidoBeneficiario],
									dt.perPrimerNombre AS [inicio.datosPersona.primerNombreBeneficiario],
									dt.perSegundoNombre AS [inicio.datosPersona.segundoNombreBeneficiario],
									solClasificacion AS [inicio.datosPersona.clasificacion], 
									DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dt.pedFechaExpedicionDocumento) AS [inicio.datosPersona.fechaExpedicionDocumentoTrabajador], 
									dt.pedGenero AS [inicio.datosPersona.generoTrabajador], 
					 				DATEDIFF_BIG (ms, '1969-12-31 19:00:00',dt.pedFechaNacimiento) AS [inicio.datosPersona.fechaNacimientoTrabajador], 
									DATEDIFF_BIG (ms, '1969-12-31 19:00:00',db.pedFechaNacimiento) AS [inicio.datosPersona.fechaNacimientoBeneficiario],
									dt.pedNivelEducativo AS [inicio.datosPersona.nivelEducativoTrabajador], 
									dt.ocuId AS [inicio.datosPersona.ocupacionProfesionTrabajador.idOcupacionProfesion],
									dt.ocuNombre AS [inicio.datosPersona.ocupacionProfesionTrabajador.nombre],

									dt.pedCabezaHogar AS [inicio.datosPersona.cabezaHogar],
									null AS [inicio.datosPersona.departamentoTrabajador],---nombre del dep?

									dt.munId AS [inicio.datosPersona.municipioTrabajador.idMunicipio],  
									dt.munCodigo AS [inicio.datosPersona.municipioTrabajador.codigo],
									dt.munNombre AS [inicio.datosPersona.municipioTrabajador.nombre],
									dt.munDepartamento AS [inicio.datosPersona.municipioTrabajador.idDepartamento], 

									dt.ubiDireccionFisica AS [inicio.datosPersona.direccionResidenciaTrabajador], 
									dt.ubiDescripcionIndicacion AS [inicio.datosPersona.descripcionIndicacionResidenciaTrabajador],
									'false' AS [inicio.datosPersona.viveEnCasaPropia], 
									dt.ubiCodigoPostal AS [inicio.datosPersona.codigoPostalTrabajador],
									dt.ubiIndicativoTelFijo AS [inicio.datosPersona.indicativoTelFijoTrabajador],  
									dt.ubiTelefonoFijo AS [inicio.datosPersona.telefonoFijoTrabajador], 
									dt.ubiTelefonoCelular AS [inicio.datosPersona.telefonoCelularTrabajador], 
									dt.ubiEmail AS [inicio.datosPersona.correoElectronicoTrabajador], 
									'false' AS [inicio.datosPersona.autorizacionEnvioCorreoElectronicoTrabajador], 
									'false' AS [inicio.datosPersona.autorizaUtilizarDatosPersonales], 
									'false' AS [inicio.datosPersona.resideEnSectorRural], 
									null AS [inicio.datosPersona.resideEnSectorRuralGrupoFamiliar],
									null AS [inicio.datosPersona.disponeDeTarjeta],
									null AS [inicio.datosPersona.emitirTarjeta],
									null AS [inicio.datosPersona.emitirInmediatamente],
									null AS [inicio.datosPersona.claseTrabajador],
									null AS [inicio.datosPersona.fechaInicioLaboresConEmpleador],
									null AS [inicio.datosPersona.tipoSalarioTrabajador],
									null AS [inicio.datosPersona.valorSalarioMensualTrabajador],
									null AS [inicio.datosPersona.horasLaboralesMesTrabajador],
									null AS [inicio.datosPersona.cargoOficioDesempeniadoTrabajador],
									null AS [inicio.datosPersona.tipoContratoLaboralTrabajador],
									null AS [inicio.datosPersona.fechaTerminacioContratoTrabajador],
									null AS [inicio.datosPersona.trabajadorMismoReptLegalDelEpleador],
									null AS [inicio.datosPersona.trabajadorEsSocioDelEmpleador],
									null AS [inicio.datosPersona.trabajadorEsConyugeDelSocioDelEmpleador],
									null AS [inicio.datosPersona.trabajadorInhabilitadoParaSubsidio],
			
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.idSucursalEmpresa],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.idEmpresa],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.codigo],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.nombre],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.ubicacion],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.codigoCIIU],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.medioDePagoSubsidioMonetario],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.estadoSucursal],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.coincidirCodigoPila],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.sucursalPrincipal],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.retencionSubsidioActiva],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.motivoRetencionSubsidio],
									null AS [inicio.datosPersona.sucursalEmpleadorTrabajador.motivoInactivaRetencionSubsidio],
			
									null AS [inicio.datosPersona.claseIndependiente],
									null AS [inicio.datosPersona.porcentajePagoAportesIndependiente],
									null AS [inicio.datosPersona.ingresosMensualesIndependiente],

									null AS [inicio.datosPersona.entidadPagadoraDeAportes.idEntidadPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.empresa],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.estadoEntidadPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.aportante],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.canalComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.medioComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.emailComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.nombreContacto],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.sucursalPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.tipoAfiliacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.cargoContacto],
									null AS [inicio.datosPersona.entidadPagadoraDeAportes.fechaCreacion],
			
									null AS [inicio.datosPersona.valorMesadaPensional],
			
									null AS [inicio.datosPersona.pagadorFondoPensiones.idAFP],
									null AS [inicio.datosPersona.pagadorFondoPensiones.nombre],
									null AS [inicio.datosPersona.pagadorFondoPensiones.codigoPila],

									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.idEntidadPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.empresa],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.estadoEntidadPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.aportante],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.canalComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.medioComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.emailComunicacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.nombreContacto],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.sucursalPagadora],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.tipoAfiliacion],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.cargoContacto],
									null AS [inicio.datosPersona.entidadPagadoraDeAportesPensionado.fechaCreacion],

									null AS [inicio.datosPersona.idEntidadPagadora],
									dt.pedEstadoCivil AS [inicio.datosPersona.estadoCivilTrabajador], 
									null AS [inicio.datosPersona.mismaDireccionAfiliadoPrincipalGrupoFam],
									null AS [inicio.datosPersona.departamentoResidenciaGrupoFam],
			
									null AS [inicio.datosPersona.municipioResidenciaGrupoFam.idMunicipio],
									null AS [inicio.datosPersona.municipioResidenciaGrupoFam.codigo],
									null AS [inicio.datosPersona.municipioResidenciaGrupoFam.nombre],
									null AS [inicio.datosPersona.municipioResidenciaGrupoFam.idDepartamento],

									null AS [inicio.datosPersona.direccionResidenciaGrupoFam],
									null AS [inicio.datosPersona.descripcionIndicacionGrupoFam],
									null AS [inicio.datosPersona.codigoPostalGrupoFam],
									null AS [inicio.datosPersona.indicativoTelFijoGrupoFam],
									null AS [inicio.datosPersona.telefonoFijoGrupoFam],
									null AS [inicio.datosPersona.telCelularGrupoFam],
									null AS [inicio.datosPersona.emailGrupoFam],
									null AS [inicio.datosPersona.afiliadoPpalMismoAdminDeSubsidio],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN DATEDIFF_BIG (ms, '1969-12-31 19:00:00',db.pedFechaExpedicionDocumento)
										 ELSE null
										 END AS [inicio.datosPersona.fechaExpedicionDocConyuge],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.pedGenero
										 ELSE null
										 END AS [inicio.datosPersona.generoConyuge],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.pedFechaNacimiento
										 ELSE null
										 END AS [inicio.datosPersona.fechaNacimientoConyuge],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.pedNivelEducativo
										 ELSE null
										 END AS [inicio.datosPersona.nivelEducativoConyuge],

									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.ocuId
										 ELSE null
										 END AS [inicio.datosPersona.profesionConyuge.idOcupacionProfesion],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.ocuNombre
										 ELSE null
										 END AS [inicio.datosPersona.profesionConyuge.nombre],
									null AS [inicio.datosPersona.conyugeLabora],--???
									null AS [inicio.datosPersona.valorIngresoMensualConyuge],--???
									
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.pedFechaExpedicionDocumento
										 ELSE null
										 END AS [inicio.datosPersona.fechaExpedicionDocHijo],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.pedGenero
										 ELSE null
										 END  AS [inicio.datosPersona.generoHijo],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.pedFechaNacimiento
										 ELSE null
										 END AS [inicio.datosPersona.fechaNacimientoHijo],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.pedNivelEducativo
										 ELSE null
										 END  AS [inicio.datosPersona.nivelEducativoHijo],

									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.graId
										 ELSE null
										 END  AS [inicio.datosPersona.gradoCursadoHijo.idgradoAcademico],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.graNombre
										 ELSE null
										 END  AS [inicio.datosPersona.gradoCursadoHijo.nombre],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.pedNivelEducativo
										 ELSE null
										 END  AS [inicio.datosPersona.gradoCursadoHijo.nivelEducativo],
			
									dt.graId AS [inicio.datosPersona.gradoCursado.idgradoAcademico],
									dt.graNombre AS [inicio.datosPersona.gradoCursado.nombre],
									dt.graNivelEducativo AS [inicio.datosPersona.gradoCursado.nivelEducativo],

									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.ocuId
										 ELSE null
										 END  AS [inicio.datosPersona.profesionHijo.idOcupacionProfesion],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.ocuNombre
										 ELSE null
										 END AS [inicio.datosPersona.profesionHijo.nombre],

									--CASE WHEN db.benTipoBeneficiario like '%HIJ%' and db.cebId IS NOT NULL
									--     THEN 'true'
									--	 ELSE 'false'
									--	 END AS [inicio.datosPersona.certificadoEscolarHijo], 
									null AS [inicio.datosPersona.fechaVencimientoCertEscolar],
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.cebFechaCreacion  
										 ELSE NULL
										 END AS [inicio.datosPersona.fechaReporteCertEscolarHijo],
									null AS [inicio.datosPersona.beneficioProgramaTrabajoDesarrollo],
									--CASE WHEN db.benTipoBeneficiario like '%HIJ%' AND db.coiInvalidez IS NOT NULL
									--     THEN 'true'
									--	 ELSE 'false'
									--	 END  AS [inicio.datosPersona.condicionInvalidezHijo], 
									CASE WHEN db.benTipoBeneficiario like '%HIJ%'
									     THEN db.coiFechaReporteInvalidez  
										 ELSE NULL
										 END AS [inicio.datosPersona.fechaRepoteinvalidezHijo],
									null AS [inicio.datosPersona.observacionesHijo],

									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.pedFechaExpedicionDocumento
										 ELSE NULL
										 END AS [inicio.datosPersona.expediciondocPadre],
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.pedGenero  
										 ELSE NULL
										 END AS [inicio.datosPersona.generoPadre],
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.pedFechaFallecido 
										 ELSE NULL
										 END AS [inicio.datosPersona.fechaNacimientoPadre],
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.pedNivelEducativo 
										 ELSE NULL
										 END AS [inicio.datosPersona.nivelEducativoPadre],

									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.ocuId 
										 ELSE NULL
										 END AS [inicio.datosPersona.ocupacionProfesionPadre.idOcupacionProfesion],
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.ocuNombre 
										 ELSE NULL
										 END  AS [inicio.datosPersona.ocupacionProfesionPadre.nombre],
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')  AND db.coiid IS NOT NULL
									     THEN 'true'
										 ELSE 'false'
										 END AS [inicio.datosPersona.condicionInvalidezPadre], 
									CASE WHEN db.benTipoBeneficiario IN ('PADRE')
									     THEN db.coiFechaReporteInvalidez
										 ELSE null
										 END AS [inicio.datosPersona.fechaReporteInvalidezPadre],
									null AS [inicio.datosPersona.observacionesPadre],
									null AS [inicio.datosPersona.estadoAfiliadoPrincipalBeneficiario],---????
									null AS [inicio.datosPersona.parentescoBeneficiarios], ---'MENOS_1_5_SM_2_POR_CIENTO'????depronto solo pensionado e ind
									null AS [inicio.datosPersona.fechaInicioUnionConAfiliadoPrincipal],
									null AS [inicio.datosPersona.fechaRegistroActivacionBeneficiario],
									null AS [inicio.datosPersona.fechaFinsociedadConyugal],
									null AS [inicio.datosPersona.motivoDesafiliacionTrabajador],
									null AS [inicio.datosPersona.motivoDesafiliacionBeneficiario],
									null AS [inicio.datosPersona.fechaInactivacionBeneficiario],
									CASE WHEN db.benTipoBeneficiario = 'CONYUGE'
									     THEN db.pedEstadoCivil
										 ELSE null END AS [inicio.datosPersona.estadoCivilConyuge],
									'false' AS [inicio.datosPersona.novedadVigente], 
									null AS [inicio.datosPersona.fechaInicioNovedad],
									null AS [inicio.datosPersona.fechaFinNovedad],
									null AS [inicio.datosPersona.vigenciaPagosPensionado],
									null AS [inicio.datosPersona.vigenciaPagosDependiente],
									null AS [inicio.datosPersona.tarifaPagoAportesIndependiente],
									null AS [inicio.datosPersona.tarifaPagoAportesPensionado],
									null AS [inicio.datosPersona.emitirTarjetaDependiente],
									null AS [inicio.datosPersona.solicitudTarjeta],
									null AS [inicio.datosPersona.motivoReexpedTarjetaDependiente],
									'false' AS [inicio.datosPersona.condicionInvalidezTrabajador], 
									null AS [inicio.datosPersona.fechaReporteInvalidezTrabajador],
									null AS [inicio.datosPersona.personaFallecidaTrabajador],
									null AS [inicio.datosPersona.personaFallecidaBeneficiarios],
									null AS [inicio.datosPersona.fechaReporteFallecimientoTrabajador],
									null AS [inicio.datosPersona.estadoAfiliacionTrabajador],
									null AS [inicio.datosPersona.pignoracionSubsidioTrabajador],
									null AS [inicio.datosPersona.estadoTarjetaTrabajador],
									null AS [inicio.datosPersona.estadoTarjetaGrupoFam],
									null AS [inicio.datosPersona.motivoAnulacionTrabajador],
									null AS [inicio.datosPersona.motivoAnulacionBeneficiario],
									null AS [inicio.datosPersona.primerNombreTitularCuentaTrabajador],
									null AS [inicio.datosPersona.segundoNombreTitularCuentaTrabajador],
									null AS [inicio.datosPersona.primerApellidoTitularCuentaTrabajador],
									null AS [inicio.datosPersona.segundoApellidoTitularCuentaTrabajador],
									null AS [inicio.datosPersona.numeroDocTitularTrabajador],
									null AS [inicio.datosPersona.tipoDocTitularTrabajador],
									null AS [inicio.datosPersona.bancoDondeRegistraCuentaTrabajador],
									null AS [inicio.datosPersona.tipoCuentaTrabajador],
									null AS [inicio.datosPersona.numeroCuentaTrabajador],
									null AS [inicio.datosPersona.estadoAfiliadoPrincipalTrabajador],
									null AS [inicio.datosPersona.estadoGeneralSolicitanteCajacompTrabajador],
									null AS [inicio.datosPersona.canalTrabajador],
									null AS [inicio.datosPersona.serviciosSinAfiliacionTrabajador],
									null AS [inicio.datosPersona.causaInactivacionServiciosSinAfiliarTrabajador],
									null AS [inicio.datosPersona.fechaNovedadActivacionServiciosSinAfiliarTrabajador],
									null AS [inicio.datosPersona.categoriaTrabajador],
									null AS [inicio.datosPersona.fechaFinaliazaServicioSinAfiliacionTrabajador],
									null AS [inicio.datosPersona.grupoFamiliarInembargable],
									'false' AS [inicio.datosPersona.inactivarCuentaWeb], 
									null AS [inicio.datosPersona.rentencionSubsidioActivaGF],
									null AS [inicio.datosPersona.tipoDocumentoAdminGF],
									null AS [inicio.datosPersona.numeroDocumentoAdminGF],
									null AS [inicio.datosPersona.primNombreAdminGF],
									null AS [inicio.datosPersona.segNombreAdminGF],
									null AS [inicio.datosPersona.primApellidoAdminGF],
									null AS [inicio.datosPersona.segApellidoAdminGF],
			
									null AS [inicio.datosPersona.relacionConGrupoFam.idRelacionGrupoFamiliar],
									null AS [inicio.datosPersona.relacionConGrupoFam.nombre],

									null AS [inicio.datosPersona.activarMedioPagoEfectivo],
									null AS [inicio.datosPersona.cesionSubsidio], 
			
									'[]' AS [inicio.datosPersona.listaChequeoNovedad] ,
									null AS [inicio.datosPersona.idPersonas],

									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.idGrupoFamiliar],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.idAfiliado],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.numero],

									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.idUbicacion],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.direccionFisica],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.codigoPostal],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.telefonoFijo],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.indicativoTelFijo],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.telefonoCelular],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.email],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.autorizacionEnvioEmail],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.idMunicipio],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.descripcionIndicacion],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.ubicacionGrupoFamiliar.sectorUbicacion],

									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.administradorSubsidio],
									null AS [inicio.datosPersona.grupoFamiliarBeneficiario.observaciones],
			
									null AS [inicio.datosPersona.medioDePagoModeloDTO.idMedioDePago],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.tipoMedioDePago],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.efectivo],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.sede],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.numeroTarjeta],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.disponeTarjeta],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.estadoTarjetaMultiservicios],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.solicitudTarjeta],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.cobroJudicial],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.infoRelacionadaCobroJudicial],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.idBanco],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.nombreBanco],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.codigoBanco],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.tipoCuenta],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.numeroCuenta],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.tipoIdentificacionTitular],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.numeroIdentificacionTitular],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.digitoVerificacionTitular],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.nombreTitularCuenta],
			
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idPersona],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacion],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacion],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.ubicacionModeloDTO],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.digitoVerificacion],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.primerNombre],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.segundoNombre],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.primerApellido],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.segundoApellido],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.razonSocial],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.creadoPorPila],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idPersonaDetalle],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.fechaNacimiento],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.fechaExpedicionDocumento],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.genero],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idOcupacionProfesion],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.nivelEducativo],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.gradoAcademico],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.cabezaHogar],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.habitaCasaPropia],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.autorizaUsoDatosPersonales],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.resideSectorRural],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.estadoCivil],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.fallecido],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.fechaFallecido],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.beneficiarioSubsidio],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.fechaDefuncion],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.estudianteTrabajoDesarrolloHumano],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idPersonaPadre],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idPersonaMadre],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacionNuevo],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacionNuevo],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idMedioPago],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.postulableFOVIS],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.orientacionSexual],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.factorVulnerabilidad],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.pertenenciaEtnica],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.admonSubsidio.idPaisResidencia],
			
									null AS [inicio.datosPersona.medioDePagoModeloDTO.persona],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.idGrupoFamiliar],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.afiliadoEsAdministradorSubsidio],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.idRelacionGrupoFamiliar],
									null AS [inicio.datosPersona.medioDePagoModeloDTO.sitioPago],

									null AS [inicio.datosPersona.inembargable],
			
									null AS [inicio.afiliacion],
									null AS [inicio.idEmpleadorDependiente],
			
									null AS [inicio.medioDePagoModeloDTO.idMedioDePago],
									null AS [inicio.medioDePagoModeloDTO.tipoMedioDePago],
									null AS [inicio.medioDePagoModeloDTO.efectivo],
									null AS [inicio.medioDePagoModeloDTO.sede],
									null AS [inicio.medioDePagoModeloDTO.numeroTarjeta],
									null AS [inicio.medioDePagoModeloDTO.disponeTarjeta],
									null AS [inicio.medioDePagoModeloDTO.estadoTarjetaMultiservicios],
									null AS [inicio.medioDePagoModeloDTO.solicitudTarjeta],
									null AS [inicio.medioDePagoModeloDTO.cobroJudicial],
									null AS [inicio.medioDePagoModeloDTO.infoRelacionadaCobroJudicial],
									null AS [inicio.medioDePagoModeloDTO.idBanco],
									null AS [inicio.medioDePagoModeloDTO.nombreBanco],
									null AS [inicio.medioDePagoModeloDTO.codigoBanco],
									null AS [inicio.medioDePagoModeloDTO.tipoCuenta],
									null AS [inicio.medioDePagoModeloDTO.numeroCuenta],
									null AS [inicio.medioDePagoModeloDTO.tipoIdentificacionTitular],
									null AS [inicio.medioDePagoModeloDTO.numeroIdentificacionTitular],
									null AS [inicio.medioDePagoModeloDTO.digitoVerificacionTitular],
									null AS [inicio.medioDePagoModeloDTO.nombreTitularCuenta],
			
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idPersona],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacion],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacion],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.ubicacionModeloDTO],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.digitoVerificacion],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.primerNombre],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.segundoNombre],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.primerApellido],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.segundoApellido],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.razonSocial],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.creadoPorPila],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idPersonaDetalle],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.fechaNacimiento],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.fechaExpedicionDocumento],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.genero],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idOcupacionProfesion],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.nivelEducativo],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.gradoAcademico],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.cabezaHogar],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.habitaCasaPropia],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.autorizaUsoDatosPersonales],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.resideSectorRural],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.estadoCivil],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.fallecido],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.fechaFallecido],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.beneficiarioSubsidio],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.fechaDefuncion],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.estudianteTrabajoDesarrolloHumano],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idPersonaPadre],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idPersonaMadre],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacionNuevo],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacionNuevo],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idMedioPago],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.postulableFOVIS],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.orientacionSexual],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.factorVulnerabilidad],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.pertenenciaEtnica],
									null AS [inicio.medioDePagoModeloDTO.admonSubsidio.idPaisResidencia],
			
									null AS [inicio.medioDePagoModeloDTO.persona],
									null AS [inicio.medioDePagoModeloDTO.idGrupoFamiliar],
									null AS [inicio.medioDePagoModeloDTO.afiliadoEsAdministradorSubsidio],
									null AS [inicio.medioDePagoModeloDTO.idRelacionGrupoFamiliar],
									null AS [inicio.medioDePagoModeloDTO.sitioPago],
			
									null AS [inicio.idBeneficiarios],
									null AS [inicio.fechaExpedicionDocumentoBeneficiario],
									null AS [inicio.motivoSubsanacionExpulsion],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.idGrupoFamiliar],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.idAfiliado],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.numero],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.idUbicacion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.direccionFisica],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.codigoPostal],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.telefonoFijo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.indicativoTelFijo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.telefonoCelular],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.email],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.autorizacionEnvioEmail],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.idMunicipio],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.descripcionIndicacion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.ubicacionGrupoFamiliar.sectorUbicacion],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.administradorSubsidio],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.observaciones],

									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.idMedioDePago],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.tipoMedioDePago],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.efectivo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.sede],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.numeroTarjeta],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.disponeTarjeta],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.estadoTarjetaMultiservicios],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.solicitudTarjeta],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.cobroJudicial],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.infoRelacionadaCobroJudicial],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.idBanco],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.nombreBanco],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.codigoBanco],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.tipoCuenta],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.numeroCuenta],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.tipoIdentificacionTitular],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.numeroIdentificacionTitular],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.digitoVerificacionTitular],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.nombreTitularCuenta],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idPersona],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.ubicacionModeloDTO],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.digitoVerificacion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.primerNombre],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.segundoNombre],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.primerApellido],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.segundoApellido],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.razonSocial],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.creadoPorPila],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idPersonaDetalle],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.fechaNacimiento],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.fechaExpedicionDocumento],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.genero],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idOcupacionProfesion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.nivelEducativo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.gradoAcademico],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.cabezaHogar],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.habitaCasaPropia],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.autorizaUsoDatosPersonales],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.resideSectorRural],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.estadoCivil],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.fallecido],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.fechaFallecido],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.beneficiarioSubsidio],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.fechaDefuncion],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.estudianteTrabajoDesarrolloHumano],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idPersonaPadre],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idPersonaMadre],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.tipoIdentificacionNuevo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.numeroIdentificacionNuevo],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idMedioPago],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.postulableFOVIS],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.orientacionSexual],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.factorVulnerabilidad],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.pertenenciaEtnica],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.admonSubsidio.idPaisResidencia],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.persona],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.idGrupoFamiliar],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.afiliadoEsAdministradorSubsidio],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.idRelacionGrupoFamiliar],
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.medioDePagoModeloDTO.sitioPago],
			
									null AS [inicio.grupoFamiliarTrasladoBeneficiario.inembargable],

									null AS [inicio.fechaDefuncion],
									null AS [inicio.oportunidadAporte],
									null AS [inicio.generoBeneficiario],
									null AS [inicio.estadoCivilBeneficiario],
									null AS [inicio.fechaReporteInvalidezBeneficiario],
									null AS [inicio.condicionInvalidezBeneficiario],

									null AS [inicio.padreBiologico.idPersona],
									null AS [inicio.padreBiologico.tipoIdentificacion],
									null AS [inicio.padreBiologico.numeroIdentificacion],
									null AS [inicio.padreBiologico.ubicacionModeloDTO],
									null AS [inicio.padreBiologico.digitoVerificacion],
									null AS [inicio.padreBiologico.primerNombre],
									null AS [inicio.padreBiologico.segundoNombre],
									null AS [inicio.padreBiologico.primerApellido],
									null AS [inicio.padreBiologico.segundoApellido],
									null AS [inicio.padreBiologico.razonSocial],
									null AS [inicio.padreBiologico.creadoPorPila],
									null AS [inicio.padreBiologico.idPersonaDetalle],
									null AS [inicio.padreBiologico.fechaNacimiento],
									null AS [inicio.padreBiologico.fechaExpedicionDocumento],
									null AS [inicio.padreBiologico.genero],
									null AS [inicio.padreBiologico.idOcupacionProfesion],
									null AS [inicio.padreBiologico.nivelEducativo],
									null AS [inicio.padreBiologico.gradoAcademico],
									null AS [inicio.padreBiologico.cabezaHogar],
									null AS [inicio.padreBiologico.habitaCasaPropia],
									null AS [inicio.padreBiologico.autorizaUsoDatosPersonales],
									null AS [inicio.padreBiologico.resideSectorRural],
									null AS [inicio.padreBiologico.estadoCivil],
									null AS [inicio.padreBiologico.fallecido],
									null AS [inicio.padreBiologico.fechaFallecido],
									null AS [inicio.padreBiologico.beneficiarioSubsidio],
									null AS [inicio.padreBiologico.fechaDefuncion],
									null AS [inicio.padreBiologico.estudianteTrabajoDesarrolloHumano],
									null AS [inicio.padreBiologico.idPersonaPadre],
									null AS [inicio.padreBiologico.idPersonaMadre],
									null AS [inicio.padreBiologico.tipoIdentificacionNuevo],
									null AS [inicio.padreBiologico.numeroIdentificacionNuevo],
									null AS [inicio.padreBiologico.idMedioPago],
									null AS [inicio.padreBiologico.postulableFOVIS],
									null AS [inicio.padreBiologico.orientacionSexual],
									null AS [inicio.padreBiologico.factorVulnerabilidad],
									null AS [inicio.padreBiologico.pertenenciaEtnica],
									null AS [inicio.padreBiologico.idPaisResidencia],
			
									null AS [inicio.madreBiologica.idPersona],
									null AS [inicio.madreBiologica.tipoIdentificacion],
									null AS [inicio.madreBiologica.numeroIdentificacion],
									null AS [inicio.madreBiologica.ubicacionModeloDTO],
									null AS [inicio.madreBiologica.digitoVerificacion],
									null AS [inicio.madreBiologica.primerNombre],
									null AS [inicio.madreBiologica.segundoNombre],
									null AS [inicio.madreBiologica.primerApellido],
									null AS [inicio.madreBiologica.segundoApellido],
									null AS [inicio.madreBiologica.razonSocial],
									null AS [inicio.madreBiologica.creadoPorPila],
									null AS [inicio.madreBiologica.idPersonaDetalle],
									null AS [inicio.madreBiologica.fechaNacimiento],
									null AS [inicio.madreBiologica.fechaExpedicionDocumento],
									null AS [inicio.madreBiologica.genero],
									null AS [inicio.madreBiologica.idOcupacionProfesion],
									null AS [inicio.madreBiologica.nivelEducativo],
									null AS [inicio.madreBiologica.gradoAcademico],
									null AS [inicio.madreBiologica.cabezaHogar],
									null AS [inicio.madreBiologica.habitaCasaPropia],
									null AS [inicio.madreBiologica.autorizaUsoDatosPersonales],
									null AS [inicio.madreBiologica.resideSectorRural],
									null AS [inicio.madreBiologica.estadoCivil],
									null AS [inicio.madreBiologica.fallecido],
									null AS [inicio.madreBiologica.fechaFallecido],
									null AS [inicio.madreBiologica.beneficiarioSubsidio],
									null AS [inicio.madreBiologica.fechaDefuncion],
									null AS [inicio.madreBiologica.estudianteTrabajoDesarrolloHumano],
									null AS [inicio.madreBiologica.idPersonaPadre],
									null AS [inicio.madreBiologica.idPersonaMadre],
									null AS [inicio.madreBiologica.tipoIdentificacionNuevo],
									null AS [inicio.madreBiologica.numeroIdentificacionNuevo],
									null AS [inicio.madreBiologica.idMedioPago],
									null AS [inicio.madreBiologica.postulableFOVIS],
									null AS [inicio.madreBiologica.orientacionSexual],
									null AS [inicio.madreBiologica.factorVulnerabilidad],
									null AS [inicio.madreBiologica.pertenenciaEtnica],
									null AS [inicio.madreBiologica.idPaisResidencia],
			
									null AS [inicio.fechaInicioInvalidezHijo],
									null AS [inicio.fechaInicioInvalidezTrabajador],
									null AS [inicio.fechaInicioInvalidezPadre],
									null AS [inicio.orientacionSexual],
									null AS [inicio.factorVulnerabilidad],
									null AS [inicio.pertenenciaEtnica],
									null AS [inicio.sectorUbicacion],
									42 AS [inicio.paisResidencia], 
									null AS [inicio.fechaRetiro],
									null AS [inicio.personasRetiroAutomatico],
									null AS [inicio.idCargueMultipleNovedad],
									snoEstadoSolicitud AS [inicio.estadoSolicitudNovedad], 
									solNumeroRadicacion AS [inicio.numeroRadicacion], 
									null AS [inicio.idRegistroDetallado],
									null AS [inicio.idDiferenciaCargueActualizacion],
									null AS [inicio.infoNovedadArchivoActualizacion],
									null AS [inicio.fechaRadicacion],
									null AS [inicio.listResultadoValidacion],
									null AS [inicio.continuaProceso],
									null AS [inicio.postuladoFOVIS],
									null AS [inicio.cargaMultiple],
									null AS [inicio.novedadEnProceso],
									null AS [inicio.validacionFallecido],
									solUsuarioRadicacion AS [inicio.usuarioRadicacion], 
									null AS [inicio.novedadAsincrona],
									null AS [inicio.datosNovedadMasiva],
									null AS [inicio.novedadCargaArchivoRespuestaSupervivencia],
									null AS [inicio.esUtilitario],
									null AS [inicio.tenNovedadId]
	 			 
					  FROM Solicitud datosnov WITH(NOLOCK)
				INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
						ON snov.snoSolicitudGlobal = datosnov.solId
				INNER JOIN SolicitudNovedadPersona  snp  WITH(NOLOCK)
						ON snov.snoid = snpSolicitudNovedad
				 LEFT JOIN #DATOS_PERSONA_TRABAJADOR dt
				        ON dt.solId = datosnov.solId 
				 LEFT JOIN #DATOS_PERSONA_BENEFICIARIO db
						ON db.solId = datosnov.solId 
						/*
			WHERE  solTipoTransaccion IN (  'CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB',
											'CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS',
											'CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_WEB',
											'CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD',
											'CAMBIO_ESTADO_CIVIL_PERSONAS_WEB',
											'CAMBIO_ESTADO_CIVIL_PERSONAS'
										)
										*/
			where  datosnov.solId = @solid 
		FOR JSON PATH , ROOT (''),   INCLUDE_NULL_VALUES)
 
	-- SELECT  @JSON
  ---INICIA JSON
 --DROP TABLE sap.jsonnovintegracion_per
 --CREATE TABLE sap.jsonnovintegracion_per (DtsSolicitud VARCHAR(20),dtsJsonPayload nvarchar(max))

 DELETE sap.jsonnovintegracion_per
 INSERT INTO sap.jsonnovintegracion_per
	 SELECT @solid, @JSON
	
	UPDATE sap.jsonnovintegracion_per SET DtsSolicitud = @solid
	UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload = SUBSTRING(dtsJsonPayload,16,LEN(dtsJsonPayload))
	UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload=  REPLACE (dtsJsonPayload,'{"":[{"inicio":','')
	UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload=  REPLACE (dtsJsonPayload,'"[]"','[]')
	UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload = REPLACE (dtsJsonPayload,'"tenNovedadId":null}}]}','"tenNovedadId":null}') 
	--UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload = REPLACE ( dtsJsonPayload, '"listaChequeoNovedad":{"idTemChequeo"','"listaChequeoNovedad":[{"idTemChequeo"')
	--UPDATE sap.jsonnovintegracion_per SET dtsJsonPayload = REPLACE ( dtsJsonPayload, '"fechaRecepcionDocumentos":null},"digitoVerificacion"','"fechaRecepcionDocumentos":null}],"digitoVerificacion"')
	
	-----select max(dtsSolicitud) from DatoTemporalSolicitud--5404221
	insert into DatoTemporalSolicitud
		(dtsSolicitud
		,dtsJsonPayload
		,dtsTipoIdentificacion
		,dtsNumeroIdentificacion)
	 SELECT *,NULL,NULL FROM sap.jsonnovintegracion_per

	 -- SELECT *,NULL,NULL FROM sap.jsonnovintegracion_per

		DROP TABLE #DATOS_PERSONA_TRABAJADOR
		DROP TABLE #DATOS_PERSONA_BENEFICIARIO
		SET @solid = NULL
		SET @JSON = NULL
END