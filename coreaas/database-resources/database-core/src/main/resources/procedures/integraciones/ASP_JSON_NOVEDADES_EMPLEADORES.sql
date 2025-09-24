
CREATE OR ALTER PROCEDURE [sap].[ASP_JSON_NOVEDADES_EMPLEADORES] @solid BIGINT = 5615963 AS
--5323835 AS 

BEGIN 
			
		 ---DATOS DEMOGRAFICOS DEL REPRESENTANTE LEGAL
		 ---DATOS DEMOGRAFICOS DEL REPRESENTANTE LEGAL SUPLENTE 
		--	IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'UltimoSueldoTrab')
	 

				SELECT DISTINCT prl.perid AS peridrl,
				        prl.perTipoIdentificacion AS tipoIdentificacionRepLegal,
						prl.pernumeroidentificacion AS numeroIdentificacionRepLegal,
						prl.perPrimerNombre AS primerNombreRepLegal,
						prl.perSegundoNombre AS segundoNombreRepLegal,
						prl.perPrimerApellido AS primerApellidoRepLegal,
						prl.perSegundoApellido AS segundoApellidoRepLegal,
						urepleg.ubiid AS ubiidrl,
						urepleg.ubiEmail AS emailRepLegal,
						urepleg.ubiIndicativoTelFijo AS indicativoTelFijoRepLegal,
						urepleg.ubiTelefonoFijo AS telefonoFijoRepLegal,
						urepleg.ubiTelefonoCelular AS telefonoCelularRepLegal,
						prls.perid AS peridrls,
						prls.perTipoIdentificacion AS tipoIdentificacionRepLegalSupl,
						prls.pernumeroidentificacion AS numeroIdentificacionRepLegalSupl,
						prls.perPrimerNombre AS primerNombreRepLegalSupl,
						prls.perSegundoNombre AS segundoNombreRepLegalSupl,
						prls.perPrimerApellido AS primerApellidoRepLegalSupl,
						prls.perSegundoApellido AS segundoApellidoRepLegalSupl,
						ureplegs.ubiid AS ubiidrls,
						ureplegs.ubiEmail AS emailRepLegalSupl,
						ureplegs.ubiIndicativoTelFijo AS indicativoTelFijoRepLegalSupl,
						ureplegs.ubiTelefonoFijo AS telefonoFijoRepLegalSupl,
						ureplegs.ubiTelefonoCelular AS telefonoCelularRepLegalSupl,
						datosnov.solNumeroRadicacion 
					INTO #DATOS_RL_RLS
					FROM Solicitud datosnov WITH(NOLOCK)
			INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
					ON snov.snoSolicitudGlobal = datosnov.solId
			INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
					ON snov.snoid = sneIdSolicitudNovedad
			INNER JOIN Empleador em  WITH(NOLOCK) ON em.empid = sne.sneIdEmpleador
			INNER JOIN Empresa e  WITH(NOLOCK) ON e.empId = em.empEmpresa
			INNER JOIN Persona pe  WITH(NOLOCK) ON  e.empPersona = pe.perid 
			INNER JOIN persona prl  WITH(NOLOCK) ON prl.perid = empRepresentanteLegal
			INNER JOIN Ubicacion urepleg  WITH(NOLOCK) ON urepleg.ubiid = empUbicacionRepresentanteLegal
			 LEFT JOIN persona prls  WITH(NOLOCK) ON prls.perid = empRepresentanteLegalSuplente
			 LEFT JOIN Ubicacion ureplegs  WITH(NOLOCK) ON ureplegs.ubiid = empUbicacionRepresentanteLegalSuplente	
					WHERE datosnov.solid = @solid ---5323715 --@solid 
								 
			CREATE NONCLUSTERED INDEX IX_DATOS_RL_RLS_IDRAD ON  #DATOS_RL_RLS (tipoIdentificacionRepLegal ASC,numeroIdentificacionRepLegal,solNumeroRadicacion ASC )


				--SELECT * FROM #DATOS_RL_RLS

		 		------DATOS POR TIPO DE UBICACION O NOTIFICACION
				SELECT DISTINCT e.empid AS empidempresa,
						uc.ubeUbicacion AS  idUbicacionc,
						muec.munDepartamento AS departamentoc ,
						muec.munId AS  idMunicipioc,
						muec.munCodigo AS codigoc,
						muec.munNombre As nombrec,
						muec.munDepartamento AS  idDepartamentoc,
						uec.ubiDireccionFisica AS direccionFisicac,
						uc.ubeTipoUbicacion AS descripcionIndicacionc,
						uec.ubiCodigoPostal AS  codigoPostalc,
						uec.ubiIndicativoTelFijo AS  indicativoTelFijoc,
						uec.ubiTelefonoFijo As telefonoFijoc,
						uec.ubiTelefonoCelular AS telefonoCelularc,
						uec.ubiAutorizacionEnvioEmail AS autorizacionEnvioEmailc,
						uec.ubiEmail AS emailc,
						---
						uep.ubiId AS idUbicacionPrincipalp,
						muep.munid AS idMunicipiop,
						muep.munCodigo AS codigop,
						muep.munNombre AS nombrep,
						muep.munDepartamento AS idDepartamentop,
						uep.ubiDireccionFisica as direccionFisicaOficinaPrincipal,
						up.ubeTipoUbicacion as descripcionIndicacionOficinaPrincipal,
						uep.ubiCodigoPostal as codigoPostalOficinaPrincipal,
						uep.ubiIndicativoTelFijo AS indicativoTelFijoOficinaPrincipal,
						uep.ubiTelefonoFijo As telefonoFijoOficinaPrincipal,
						uep.ubiTelefonoCelular AS telefonoCelularOficinaPrincipal,
						
						----
						uej.ubiId AS idUbicacionJudicial,
						muej.munId AS idMunicipioj,
						muej.munCodigo AS codigoj,
						muej.munNombre AS nombrej,
						muej.munDepartamento AS idDepartamentoj,
						uej.ubiDireccionFisica AS direccionFisicaJudicial,
						uj.ubeTipoUbicacion AS descripcionIndicacionJudicial,
						uej.ubiCodigoPostal AS codigoPostalJudicial,
						uej.ubiIndicativoTelFijo AS indicativoTelFijoJudicial,
						uej.ubiTelefonoFijo As telefonoFijoJudicial,
						uej.ubiTelefonoCelular AS telefonoCelularJudicial
		 	      INTO #DATOS_TIPO_NOT
				  FROM Solicitud datosnov WITH(NOLOCK)
			INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
					ON snov.snoSolicitudGlobal = datosnov.solId
			INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
					ON snov.snoid = sneIdSolicitudNovedad
			INNER JOIN Empleador em  WITH(NOLOCK) ON em.empid = sne.sneIdEmpleador
			INNER JOIN Empresa e  WITH(NOLOCK) ON e.empId = em.empEmpresa
			 LEFT JOIN UbicacionEmpresa uc  WITH(NOLOCK) ON e.empid =  uc.ubeEmpresa AND uc.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA' 
			 LEFT JOIN Ubicacion uec WITH(NOLOCK) ON uc.ubeUbicacion = uec.ubiId
			 LEFT JOIN Municipio muec WITH(NOLOCK) ON uec.ubiMunicipio = muec.munId 
			 LEFT JOIN Departamento duec WITH(NOLOCK) ON duec.depId = muec.munDepartamento 
			 LEFT JOIN UbicacionEmpresa up WITH(NOLOCK) ON e.empid =  up.ubeEmpresa AND up.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
			 LEFT JOIN Ubicacion uep WITH(NOLOCK) ON up.ubeUbicacion= uep.ubiid
			 LEFT JOIN Municipio muep WITH(NOLOCK) ON uep.ubiMunicipio = muep.munId 
			 LEFT JOIN Departamento duep WITH(NOLOCK) ON duep.depId = muep.munDepartamento 
			 LEFT JOIN UbicacionEmpresa uj WITH(NOLOCK) ON e.empid =  uj.ubeEmpresa AND uj.ubeTipoUbicacion = 'NOTIFICACION_JUDICIAL'
			 LEFT JOIN Ubicacion uej  WITH(NOLOCK) ON  uj.ubeUbicacion = uej.ubiid
			 LEFT JOIN Municipio muej  WITH(NOLOCK) ON uej.ubiMunicipio = muej.munId 
			 LEFT JOIN Departamento duej  WITH(NOLOCK) ON  muej.munDepartamento = duej.depId
				 WHERE datosnov.solid = @solid--- 5323715 --@solid 
								 
		 CREATE NONCLUSTERED INDEX IX_DATOS_TIPO_NOT ON  #DATOS_TIPO_NOT (idUbicacionc ASC,idUbicacionPrincipalp ASC ,idUbicacionJudicial ASC )

	--	 SELECT * FROM #DATOS_TIPO_NOT

			 ------DATOS POR ROL DE CONTACTO SUBSIDIO
 
			 SELECT DISTINCT rceEmpleador,rceTipoRolContactoEmpleador,
					rceid AS idRolSubsidio,
					psub.perTipoIdentificacion AS tipoIdentificacionRolSubsidio,
					psub.perNumeroIdentificacion AS numeroIdentificacionRolSubsidio ,
					psub.perPrimerNombre AS primerNombreRolSubsidio,
					psub.perSegundoNombre AS segundoNombreRolSubsidio,
					psub.perPrimerApellido AS primerApellidoRolSubsidio,
					psub.perSegundoApellido AS segundoApellidoRolSubsidio,
					Usub.ubiEmail AS emailRolSubsidio,
					Usub.ubiIndicativoTelFijo AS indicativoTelFijoRolSubsidio,
					Usub.ubiTelefonoFijo AS telefonoFijoRolSubsidio,
					Usub.ubiTelefonoCelular AS telefonoCelularRolSubsidio,
					null AS sucursalesRolAfiliacion
			   INTO #DATOS_RC_SUB
 	 		   FROM Solicitud datosnov WITH(NOLOCK)
		 INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
				 ON snov.snoSolicitudGlobal = datosnov.solId
		 INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
		  		 ON snov.snoid = sneIdSolicitudNovedad
		 INNER JOIN Empleador em  WITH(NOLOCK)		         
		         ON sne.sneIdEmpleador = em.empid 
		 INNER JOIN RolContactoEmpleador rcsub WITH(NOLOCK)
		         ON rcsub.rceEmpleador = em.empid AND rcsub.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_SUBSIDIOS'
		 INNER JOIN Persona psub  WITH(NOLOCK)
		         ON rcsub.rcePersona = psub.perId 
		 INNER JOIN Ubicacion Usub WITH(NOLOCK)
		         ON rcsub.rceubicacion = Usub.ubiId
			  WHERE solid = @solid--- 5323715---@solid 
		---	  WHERE em.empId = 2336--37902

 CREATE NONCLUSTERED INDEX IX_rc_sub ON  #DATOS_RC_SUB (rceEmpleador  ASC ,rceTipoRolContactoEmpleador  ASC ,idRolSubsidio  ASC )

 --SELECT * FROM #DATOS_RC_SUB
			 ----DATOS ROL APORTES


   			 SELECT DISTINCT rcapo.rceEmpleador,
			        rcapo.rceTipoRolContactoEmpleador,
					rcapo.rceid AS idRolAportes,
					papo.perTipoIdentificacion AS tipoIdentificacionRolAportes,
					papo.perNumeroIdentificacion AS numeroIdentificacionRolAportes ,
					papo.perPrimerNombre AS primerNombreRolAportes,
					papo.perSegundoNombre AS segundoNombreRolAportes,
					papo.perPrimerApellido AS primerApellidoRolAportes,
					papo.perSegundoApellido AS segundoApellidoRolAportes,
					Uapo.ubiEmail AS emailRolAportes,
					Uapo.ubiIndicativoTelFijo AS indicativoTelFijoRolAportes ,
					Uapo.ubiTelefonoFijo AS telefonoFijoRolAportes,
					Uapo.ubiTelefonoCelular AS telefonoCelularRolAportes,
					null AS sucursalesRolAportes
			   INTO #DATOS_RC_APO
 	 		   FROM Solicitud datosnov WITH(NOLOCK)
		 INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
				 ON snov.snoSolicitudGlobal = datosnov.solId
		 INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
		  		 ON snov.snoid = sneIdSolicitudNovedad
		 INNER JOIN Empleador em  WITH(NOLOCK)		         
		         ON sne.sneIdEmpleador = em.empid 	         
		 INNER JOIN RolContactoEmpleador rcapo WITH(NOLOCK)
		         ON rcapo.rceEmpleador = em.empid AND rcapo.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_APORTES'
		 INNER JOIN Persona papo  WITH(NOLOCK)
		         ON rcapo.rcePersona = papo.perId 
		 INNER JOIN Ubicacion Uapo WITH(NOLOCK)
		         ON rcapo.rceubicacion = Uapo.ubiId
		--	  WHERE em.empId = 2336--37902
		      WHERE solid =  @solid --5323715---@solid 
			   
CREATE NONCLUSTERED INDEX IX_RC_APO ON  #DATOS_RC_APO (rceEmpleador  ASC ,rceTipoRolContactoEmpleador  ASC ,idRolaportes  ASC )

--SELECT * FROM #DATOS_RC_APO

	         SELECT DISTINCT rceEmpleador,rceTipoRolContactoEmpleador,
					rceid AS idRolAfiliacion,
					pafi.perTipoIdentificacion AS tipoIdentificacionRolAfiliacion,
					pafi.perNumeroIdentificacion AS numeroIdentificacionRolAfiliacion ,
					pafi.perPrimerNombre AS primerNombreRolAfiliacion,
					pafi.perSegundoNombre AS segundoNombreRolAfiliacion,
					pafi.perPrimerApellido AS primerApellidoRolAfiliacion,
					pafi.perSegundoApellido AS segundoApellidoRolAfiliacion,
					Uafi.ubiEmail AS emailRolAfiliacion,
					Uafi.ubiIndicativoTelFijo AS indicativoTelFijoRolAfiliacion ,
					Uafi.ubiTelefonoFijo AS telefonoFijoRolAfiliacion,
					Uafi.ubiTelefonoCelular AS telefonoCelularRolAfiliacion,
					null AS sucursalesRolAfiliacion
			   INTO #DATOS_RC_AFI
 	 		   FROM Solicitud datosnov WITH(NOLOCK)
		 INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
				 ON snov.snoSolicitudGlobal = datosnov.solId
		 INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
		  		 ON snov.snoid = sneIdSolicitudNovedad
		 INNER JOIN Empleador em  WITH(NOLOCK)		         
		         ON sne.sneIdEmpleador = em.empid 		         
		 INNER JOIN RolContactoEmpleador rcafi WITH(NOLOCK)
		         ON rcafi.rceEmpleador = em.empid AND rcafi.rceTipoRolContactoEmpleador = 'ROL_RESPONSABLE_AFILIACIONES'
		 INNER JOIN Persona pafi  WITH(NOLOCK)
		         ON rcafi.rcePersona = pafi.perId 
		 INNER JOIN Ubicacion Uafi WITH(NOLOCK)
		         ON rcafi.rceubicacion = Uafi.ubiId
			  WHERE solid = @solid 
			  --WHERE em.empId = 2336--37902

CREATE NONCLUSTERED INDEX IX_RC_AFI ON #DATOS_RC_AFI (rceEmpleador  ASC ,rceTipoRolContactoEmpleador  ASC ,idRolAfiliacion  ASC)

--SELECT * FROM #DATOS_RC_AFI

------DATOS RESPONSABLE 


			 SELECT DISTINCT ar1.areNombreUsuario AS responsable1CajaContacto,
			        ar2.areNombreUsuario AS responsable2CajaContacto,
					CASE WHEN bem1429.bemBeneficioActivo =1 THEN 'true' ELSE 'false' END AS empleadorCubiertoLey1429,
					ISNULL(CONVERT(VARCHAR,YEAR(bem1429.bemFechaVinculacion)),null) AS anoInicioBeneficioLey1429,
					ISNULL(CONVERT(VARCHAR,DATEDIFF(YEAR,bem1429.bemFechaVinculacion, GETDATE())),null) AS numeroConsecutivoAnosBeneficioLey1429,
					ISNULL(CONVERT(VARCHAR,DATEDIFF(YEAR,bem1429.bemFechaDesvinculacion, GETDATE())),null) AS anoFinBeneficioLey1429,
					ISNULL(CONVERT(VARCHAR,bem1429.bemMotivoInactivacion),null) AS motivoInactivacionBeneficioLey1429,
					--CASE WHEN bem590.bemBeneficioActivo = 2 THEN 'true' ELSE 'false' END AS empleadorCubiertoLey590,
					bem590.bemBeneficioActivo as empleadorCubiertoLey590,
					ISNULL(CONVERT(VARCHAR,YEAR(bem590.bemFechaVinculacion)),null) AS periodoInicioBeneficioLey590,
					ISNULL(CONVERT(VARCHAR,DATEDIFF(YEAR,bem590.bemFechaVinculacion, GETDATE())),null) AS numeroConsecutivoAnosBeneficioLey590,
					ISNULL(CONVERT(VARCHAR,YEAR(bem590.bemFechaDesvinculacion)),null) AS periodoFinBeneficioLey590,
					ISNULL(CONVERT(VARCHAR,bem590.bemMotivoInactivacion),null) AS motivoInactivacionBeneficioLey590,
					null AS sucursalOrigenTraslado,
					'false' AS codigoNombreCoincidePILA,
					'false' AS inactivarSucursal,
					null AS sucursalDestinoTraslado,
					'[]' AS trabajadoresTraslado ,
					ar1.areEmpleador
			   INTO  #DATOS_ASESOR_BENF
			   FROM Solicitud datosnov WITH(NOLOCK)
		 INNER JOIN Solicitudnovedad snov  WITH(NOLOCK)
				 ON snov.snoSolicitudGlobal = datosnov.solId
		 INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
		  		 ON snov.snoid = sneIdSolicitudNovedad
		 INNER JOIN Empleador em  WITH(NOLOCK)		         
		         ON sne.sneIdEmpleador = em.empid 	 
		 INNER JOIN AsesorResponsableEmpleador ar1 WITH(NOLOCK)
				 ON em.empid = ar1.areEmpleador AND ar1.arePrimario = 1
		 INNER JOIN AsesorResponsableEmpleador ar2 WITH(NOLOCK)
				 ON em.empid = ar2.areEmpleador AND ar2.arePrimario = 0
		  LEFT JOIN BeneficioEmpleador bem1429 
		         ON bem1429.bemEmpleador = em.empId AND bem1429.bemBeneficio=1 AND bem1429.bemBeneficioActivo = 1
		  LEFT JOIN Beneficio b1429 
		         ON b1429.befid = bem1429.bemBeneficio
		  LEFT JOIN BeneficioEmpleador bem590 
		         ON bem590.bemEmpleador = em.empId AND bem590.bemBeneficio=2 AND bem590.bemBeneficioActivo = 1
		  LEFT JOIN Beneficio b590 
		         ON b590.befid = bem590.bemBeneficio
		 	  WHERE solid =  @solid 	

 CREATE NONCLUSTERED INDEX IX_ASESOR_BENF ON #DATOS_ASESOR_BENF (areEmpleador  ASC  )

 ---INICIA JSON

 --SELECT * FROM #DATOS_ASESOR_BENF



 DECLARE @JSON VARCHAR(MAX)

		SET @JSON =(		SELECT  distinct
						datosnov.solId as [inicio.idSolicitud],
						snov.snoid as [inicio.idSolicitudNovedad],
						null as [inicio.idInstancia],
						snov.snoNovedad as [inicio.novedadDTO.idNovedad],
						'FRONT' AS [inicio.novedadDTO.puntoResolucion],
						datosnov.solTipoTransaccion AS [inicio.novedadDTO.novedad],
						'com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' AS [inicio.novedadDTO.rutaCualificada],
						'GENERAL' AS [inicio.novedadDTO.tipoNovedad], 
						REPLACE(REPLACE(REPLACE(LOWER(datosnov.solTipoTransaccion),'_PRESENCIAL',''),'_PERSONAS',''),'_',' ') AS [inicio.novedadDTO.nombre], 
						--{
						solCanalRecepcion AS [inicio.canalRecepcion],
						solMetodoEnvio AS  [inicio.metodoenvio],
						solClasificacion AS [inicio.clasificacion], 
						solTipoTransaccion AS [inicio.tipoTransaccion], 
						ISNULL(snoObservaciones,'') AS [inicio.observaciones],
						solResultadoProceso AS [inicio.resultadoValidacion],
						null AS [inicio.excepcionTipoDos],
						----datosempleador
						em.empId AS [inicio.datosEmpleador.idEmpleador],
						pe.perTipoIdentificacion AS [inicio.datosEmpleador.tipoIdentificacion],
						pe.pernumeroidentificacion AS [inicio.datosEmpleador.numeroIdentificacion],
						--':[{' as [inicio.novedadDTO.datosEmpleador.listaChequeoNovedad.],
						null as [inicio.datosEmpleador.listaChequeoNovedad.idTemChequeo],
						solid AS [inicio.datosEmpleador.listaChequeoNovedad.idSolicitudGlobal],
						reqId AS [inicio.datosEmpleador.listaChequeoNovedad.idRequisito],
						reqDescripcion AS [inicio.datosEmpleador.listaChequeoNovedad.nombreRequisito],
						'ESTANDAR' AS [inicio.datosEmpleador.listaChequeoNovedad.tipoRequisito],
						ichIdentificadorDocumento AS [inicio.datosEmpleador.listaChequeoNovedad.identificadorDocumento],
						ichVersionDocumento AS [inicio.datosEmpleador.listaChequeoNovedad.versionDocumento],
						ichEstadoRequisito AS [inicio.datosEmpleador.listaChequeoNovedad.estadoRequisito],
						ichCumpleRequisito AS [inicio.datosEmpleador.listaChequeoNovedad.cumpleRequisito],
						ichFormatoEntregaDocumento AS [inicio.datosEmpleador.listaChequeoNovedad.formatoEntregaDocumento],
						ichComentarios AS [inicio.datosEmpleador.listaChequeoNovedad.comentarios],
						ichCumpleRequisitoBack AS [inicio.datosEmpleador.listaChequeoNovedad.cumpleRequisitoBack],
						ichComentariosBack AS [inicio.datosEmpleador.listaChequeoNovedad.comentariosBack],
						'' AS [inicio.datosEmpleador.listaChequeoNovedad.textoAyuda],
						ichIdentificadorDocumentoPrevio AS [inicio.datosEmpleador.listaChequeoNovedad.identificadorDocumentoPrevio],
						null AS [inicio.datosEmpleador.listaChequeoNovedad.grupoRequisitos],
						null AS [inicio.datosEmpleador.listaChequeoNovedad.idRequisitoCajaClasificacion],
						ichFechaRecepcionDocumento AS [inicio.datosEmpleador.listaChequeoNovedad.fechaRecepcionDocumentos],
						--]
						pe.perDigitoVerificacion AS [inicio.datosEmpleador.digitoVerificacion],
						pe.perRazonSocial AS [inicio.datosEmpleador.razonSocial],
						pe.perPrimerNombre AS [inicio.datosEmpleador.primerNombre],
						pe.perSegundoNombre AS  [inicio.datosEmpleador.segundoNombre],
						pe.perPrimerApellido AS [inicio.datosEmpleador.primerApellido],
						pe.perSegundoApellido AS [inicio.datosEmpleador.segundoApellido],
						null as [inicio.datosEmpleador.tipoSolicitante],
						e.empNombreComercial AS [inicio.datosEmpleador.nombreComercial],
						cast(cast(datediff(day, '19700101', dateadd(month,-1,convert(varchar,e.empFechaConstitucion,12)))* 24 * 60 * 60 as varchar(250))+'000' as bigint) AS [inicio.datosEmpleador.fechaConstitucion],
						e.empNaturalezaJuridica AS  [inicio.datosEmpleador.naturalezaJuridica],
--grupo codigociiu
						--cce.ciiCodigo AS [inicio.datosEmpleador.codigoCIIU.codigoCIIU],
						cce.ciiId AS [inicio.datosEmpleador.codigoCIIU.idCodigoCIIU],
						null AS [inicio.datosEmpleador.codigoCIIU.codigo],
									
						cce.ciiDescripcion AS [inicio.datosEmpleador.codigoCIIU.descripcion],
						cce.ciiCodigoSeccion AS [inicio.datosEmpleador.codigoCIIU.codigoSeccion],
						cce.ciiDescripcionSeccion AS [inicio.datosEmpleador.codigoCIIU.descripcionSeccion],
						cce.ciiCodigoDivision AS [inicio.datosEmpleador.codigoCIIU.codigoDivision],
						cce.ciiDescripcionDivision AS [inicio.datosEmpleador.codigoCIIU.descripcionDivision],
						cce.ciiCodigoGrupo As [inicio.datosEmpleador.codigoCIIU.codigoGrupo],
						cce.ciiDescripcionGrupo AS [inicio.datosEmpleador.codigoCIIU.descripcionGrupo],
--termina el grupo codigociiu
						null AS [inicio.datosEmpleador.arl.idARL],
						null AS [inicio.datosEmpleador.arl.nombre],
---cierro creo que arl
						empNumeroTotalTrabajadores AS [inicio.datosEmpleador.numeroTotalTrabajadores],
						empValorTotalUltimaNomina AS [inicio.datosEmpleador.valorTotalUltimaNomina],
						---cast(cast(datediff(day, '19700101', dateadd(month,-1,convert(varchar, empPeriodoUltimaNomina,12)))* 24 * 60 * 60)) 
						empPeriodoUltimaNomina AS [inicio.datosEmpleador.periodoUltimaNomina],
						null AS [inicio.datosEmpleador.idUltimaCajaCompensacion],
						empPaginaWeb AS [inicio.datosEmpleador.paginaWeb],
						tiponot.idUbicacionc AS [inicio.datosEmpleador.idUbicacion],
						tiponot.departamentoc AS [inicio.datosEmpleador.departamento] ,
--'{' grupo municipio
						tiponot.idMunicipioc AS [inicio.datosEmpleador.municipio.idMunicipio],
						tiponot.codigoc AS [inicio.datosEmpleador.municipio.codigo],
						tiponot.nombrec As [inicio.datosEmpleador.municipio.nombre],
						tiponot.idDepartamentoc AS [inicio.datosEmpleador.municipio.idDepartamento],

						tiponot.direccionFisicac AS [inicio.datosEmpleador.direccionFisica],
						tiponot.descripcionIndicacionc AS [inicio.datosEmpleador.descripcionIndicacion],
						tiponot.codigoPostalc AS [inicio.datosEmpleador.codigoPostal],
						tiponot.indicativoTelFijoc AS [inicio.datosEmpleador.indicativoTelFijo],
						tiponot.telefonoFijoc As [inicio.datosEmpleador.telefonoFijo],
						tiponot.telefonoCelularc AS [inicio.datosEmpleador.telefonoCelular],
						tiponot.autorizacionEnvioEmailc AS [inicio.datosEmpleador.autorizacionEnvioEmail],
						tiponot.emailc AS [inicio.datosEmpleador.email],
						tiponot.idUbicacionPrincipalp AS [inicio.datosEmpleador.idUbicacionPrincipal],
						null AS [inicio.datosEmpleador.correspondenciaIgualOficinaPrincipal],

--'{' municipioOficinaPrincipal
						tiponot.idMunicipiop AS [inicio.datosEmpleador.municipioOficinaPrincipal.idMunicipio],
						tiponot.codigop AS [inicio.datosEmpleador.municipioOficinaPrincipal.codigo],
						tiponot.nombrep AS [inicio.datosEmpleador.municipioOficinaPrincipal.nombre],
						tiponot.idDepartamentop AS [inicio.datosEmpleador.municipioOficinaPrincipal.idDepartamento],

						tiponot.direccionFisicaOficinaPrincipal as [inicio.datosEmpleador.direccionFisicaOficinaPrincipal],
						tiponot.descripcionIndicacionOficinaPrincipal as [inicio.datosEmpleador.descripcionIndicacionOficinaPrincipal],
						tiponot.codigoPostalOficinaPrincipal as [inicio.datosEmpleador.codigoPostalOficinaPrincipal],
						tiponot.indicativoTelFijoOficinaPrincipal AS [inicio.datosEmpleador.indicativoTelFijoOficinaPrincipal],
						tiponot.telefonoFijoOficinaPrincipal As [inicio.datosEmpleador.telefonoFijoOficinaPrincipal],
						tiponot.telefonoCelularOficinaPrincipal AS [inicio.datosEmpleador.telefonoCelularOficinaPrincipal],
						tiponot.idUbicacionJudicial AS [inicio.datosEmpleador.idUbicacionJudicial],
						'1' AS [inicio.datosEmpleador.judicialIgualOficinaPrincipal],
						tiponot.idMunicipioj  AS [inicio.datosEmpleador.municipioJudicial.idMunicipio],
						tiponot.codigoj  AS [inicio.datosEmpleador.municipioJudicial.codigo],
						tiponot.nombrej  AS [inicio.datosEmpleador.municipioJudicial.nombre],
						tiponot.idDepartamentoj  AS [inicio.datosEmpleador.municipioJudicial.idDepartamento],
						tiponot.direccionFisicaJudicial AS [inicio.datosEmpleador.direccionFisicaJudicial],
						tiponot.descripcionIndicacionJudicial AS [inicio.datosEmpleador.descripcionIndicacionJudicial],
						tiponot.codigoPostalJudicial AS [inicio.datosEmpleador.codigoPostalJudicial],
						tiponot.indicativoTelFijoJudicial AS [inicio.datosEmpleador.indicativoTelFijoJudicial],
						tiponot.telefonoFijoJudicial As [inicio.datosEmpleador.telefonoFijoJudicial],
						tiponot.telefonoCelularJudicial AS [inicio.datosEmpleador.telefonoCelularJudicial],
						
						rl.tipoIdentificacionRepLegal AS [inicio.datosEmpleador.tipoIdentificacionRepLegal],
						rl.numeroIdentificacionRepLegal AS [inicio.datosEmpleador.numeroIdentificacionRepLegal],
						rl.primerNombreRepLegal AS [inicio.datosEmpleador.primerNombreRepLegal],
						rl.segundoNombreRepLegal AS [inicio.datosEmpleador.segundoNombreRepLegal],
						rl.primerApellidoRepLegal AS [inicio.datosEmpleador.primerApellidoRepLegal],
						rl.segundoApellidoRepLegal AS [inicio.datosEmpleador.segundoApellidoRepLegal],
						
						rl.emailRepLegal AS [inicio.datosEmpleador.emailRepLegal],
						rl.indicativoTelFijoRepLegal AS [inicio.datosEmpleador.indicativoTelFijoRepLegal],
						rl.telefonoFijoRepLegal AS [inicio.datosEmpleador.telefonoFijoRepLegal],
						rl.telefonoCelularRepLegal AS [inicio.datosEmpleador.telefonoCelularRepLegal],

						rl.tipoIdentificacionRepLegalSupl AS [inicio.datosEmpleador.tipoIdentificacionRepLegalSupl],
						rl.numeroIdentificacionRepLegalSupl AS [inicio.datosEmpleador.numeroIdentificacionRepLegalSupl],
						rl.primerNombreRepLegalSupl AS [inicio.datosEmpleador.primerNombreRepLegalSupl],
						rl.segundoNombreRepLegalSupl AS [inicio.datosEmpleador.segundoNombreRepLegalSupl],
						rl.primerApellidoRepLegalSupl AS [inicio.datosEmpleador.primerApellidoRepLegalSupl],
						rl.segundoApellidoRepLegalSupl AS [inicio.datosEmpleador.segundoApellidoRepLegalSupl],
						
						rl.emailRepLegalSupl AS [inicio.datosEmpleador.emailRepLegalSupl],
						rl.indicativoTelFijoRepLegalSupl AS [inicio.datosEmpleador.indicativoTelFijoRepLegalSupl],
						rl.telefonoFijoRepLegalSupl AS [inicio.datosEmpleador.telefonoFijoRepLegalSupl],
						rl.telefonoCelularRepLegalSupl AS [inicio.datosEmpleador.telefonoCelularRepLegalSupl],
		
					   null AS [inicio.datosEmpleador.sucursalIgualOficinaPrincipal],
						se.sueCodigo AS [inicio.datosEmpleador.codigoSucursal],
						se.sueNombre AS [inicio.datosEmpleador.nombreSucursal],
						sem.munId AS [inicio.datosEmpleador.municipioSucursal.idMunicipio],
						sem.munCodigo AS [inicio.datosEmpleador.municipioSucursal.codigo],
						sem.munNombre AS [inicio.datosEmpleador.municipioSucursal.nombre],
						sed.depId AS [inicio.datosEmpleador.municipioSucursal.idDepartamento],---cierre
						seu.ubiDireccionFisica AS [inicio.datosEmpleador.direccionFisicaSucursal],
						seu.ubiDescripcionIndicacion AS [inicio.datosEmpleador.descripcionIndicacionSucursal],
						seu.ubiId AS [inicio.datosEmpleador.idUbicacionSucursal],
						seu.ubiCodigoPostal AS [inicio.datosEmpleador.codigoPostalSucursal],
						seu.ubiIndicativoTelFijo AS [inicio.datosEmpleador.indicativoTelFijoSucursal],
						seu.ubiTelefonoFijo AS [inicio.datosEmpleador.telefonoFijoSucursal],
						seu.ubiTelefonoCelular AS [inicio.datosEmpleador.telefonoCelularSucursal],
						se.sueId AS [inicio.datosEmpleador.idSucursalEmpresa] ,
						ccse.ciiId AS [inicio.datosEmpleador.codigoCIIUSucursal.idCodigoCIIU],

						ccse.ciiCodigo AS [inicio.datosEmpleador.codigoCIIUSucursal.codigo],									
						ccse.ciiDescripcion AS [inicio.datosEmpleador.codigoCIIUSucursal.descripcion],
						ccse.ciiCodigoSeccion AS [inicio.datosEmpleador.codigoCIIUSucursal.codigoSeccion],
						ccse.ciiDescripcionSeccion AS [inicio.datosEmpleador.codigoCIIUSucursal.descripcionSeccion],
						ccse.ciiCodigoDivision AS [inicio.datosEmpleador.codigoCIIUSucursal.codigoDivision],
						ccse.ciiDescripcionDivision AS [inicio.datosEmpleador.codigoCIIUSucursal.descripcionDivision],
						ccse.ciiCodigoGrupo As [inicio.datosEmpleador.codigoCIIUSucursal.codigoGrupo],
						ccse.ciiDescripcionGrupo AS [inicio.datosEmpleador.codigoCIIUSucursal.descripcionGrupo],
						rafi.idRolAfiliacion AS [inicio.datosEmpleador.idRolAfiliacion],
						null AS [inicio.datosEmpleador.rolAfiliacionIgualRepresentanteLegal],
						rafi.tipoIdentificacionRolAfiliacion AS [inicio.datosEmpleador.tipoIdentificacionRolAfiliacion],
						rafi.numeroIdentificacionRolAfiliacion AS [inicio.datosEmpleador.numeroIdentificacionRolAfiliacion],
						rafi.primerNombreRolAfiliacion AS [inicio.datosEmpleador.primerNombreRolAfiliacion],
						rafi.segundoNombreRolAfiliacion AS [inicio.datosEmpleador.segundoNombreRolAfiliacion],
						rafi.primerApellidoRolAfiliacion AS [inicio.datosEmpleador.primerApellidoRolAfiliacion],
						rafi.segundoApellidoRolAfiliacion AS [inicio.datosEmpleador.segundoApellidoRolAfiliacion],
						rafi.emailRolAfiliacion AS [inicio.datosEmpleador.emailRolAfiliacion],
						rafi.indicativoTelFijoRolAfiliacion AS [inicio.datosEmpleador.indicativoTelFijoRolAfiliacion],
						rafi.telefonoFijoRolAfiliacion AS [inicio.datosEmpleador.telefonoFijoRolAfiliacion],
						rafi.telefonoCelularRolAfiliacion AS [inicio.datosEmpleador.telefonoCelularRolAfiliacion],
						'[]' AS [inicio.datosEmpleador.sucursalesRolAfiliacion],
						---quitar comillas dobles
						rapo.idRolAportes AS [inicio.datosEmpleador.idRolAportes],
						null AS [inicio.datosEmpleador.rolAportesIgualRepresentanteLegal],
						rapo.tipoIdentificacionRolAportes AS [inicio.datosEmpleador.tipoIdentificacionRolAportes],
						rapo.numeroIdentificacionRolAportes AS [inicio.datosEmpleador.numeroIdentificacionRolAportes],
						rapo.primerNombreRolAportes AS [inicio.datosEmpleador.primerNombreRolAportes],
						rapo.segundoNombreRolAportes AS [inicio.datosEmpleador.segundoNombreRolAportes],
						rapo.primerApellidoRolAportes AS [inicio.datosEmpleador.primerApellidoRolAportes],
						rapo.segundoApellidoRolAportes AS [inicio.datosEmpleador.segundoApellidoRolAportes],
						rapo.emailRolAportes AS [inicio.datosEmpleador.emailRolAportes],
						rapo.indicativoTelFijoRolAportes AS [inicio.datosEmpleador.indicativoTelFijoRolAportes],
						rapo.telefonoFijoRolAportes AS [inicio.datosEmpleador.telefonoFijoRolAportes],
						rapo.telefonoCelularRolAportes AS [inicio.datosEmpleador.telefonoCelularRolAportes],
						'[]' AS [inicio.datosEmpleador.sucursalesRolAportes],
 ---quitar comillas dobles
 						rsub.idRolSubsidio AS [inicio.datosEmpleador.idRolSubsidio],
						null AS [inicio.datosEmpleador.rolSubsidioIgualRepresentanteLegal],
						rsub.tipoIdentificacionRolSubsidio AS [inicio.datosEmpleador.tipoIdentificacionRolSubsidio],
						rsub.numeroIdentificacionRolSubsidio AS [inicio.datosEmpleador.numeroIdentificacionRolSubsidio],
						rsub.primerNombreRolSubsidio AS [inicio.datosEmpleador.primerNombreRolSubsidio],
						rsub.segundoNombreRolSubsidio AS [inicio.datosEmpleador.segundoNombreRolSubsidio],
						rsub.primerApellidoRolSubsidio AS [inicio.datosEmpleador.primerApellidoRolSubsidio],
						rsub.segundoApellidoRolSubsidio AS [inicio.datosEmpleador.segundoApellidoRolSubsidio],
						rsub.emailRolSubsidio AS [inicio.datosEmpleador.emailRolSubsidio],
						rsub.indicativoTelFijoRolSubsidio AS [inicio.datosEmpleador.indicativoTelFijoRolSubsidio],
						rsub.telefonoFijoRolSubsidio AS [inicio.datosEmpleador.telefonoFijoRolSubsidio],
						rsub.telefonoCelularRolSubsidio AS [inicio.datosEmpleador.telefonoCelularRolSubsidio],
						'[]' AS [inicio.datosEmpleador.sucursalesRolSubsidio],
						---quitar comillas dobles
						aseben.responsable1CajaContacto AS [inicio.datosEmpleador.responsable1CajaContacto],
						aseben.responsable2CajaContacto AS [inicio.datosEmpleador.responsable2CajaContacto],
						aseben.codigoNombreCoincidePILA AS [inicio.datosEmpleador.codigoNombreCoincidePILA],
						aseben.inactivarSucursal AS [inicio.datosEmpleador.inactivarSucursal],
						aseben.empleadorCubiertoLey1429 AS [inicio.datosEmpleador.empleadorCubiertoLey1429],
						aseben.anoInicioBeneficioLey1429 AS [inicio.datosEmpleador.anoInicioBeneficioLey1429],
						aseben.numeroConsecutivoAnosBeneficioLey1429 AS [inicio.datosEmpleador.numeroConsecutivoAnosBeneficioLey1429],
						--aseben.empleadorCubiertoLey1429 AS [inicio.novedadDTO.datosEmpleador.listaChequeoNovedad.empleadorCubiertoLey1429],
						--aseben.anoInicioBeneficioLey1429 AS [inicio.novedadDTO.datosEmpleador.listaChequeoNovedad.anoInicioBeneficioLey1429],
						--aseben.numeroConsecutivoAnosBeneficioLey1429 AS [inicio.novedadDTO.datosEmpleador.listaChequeoNovedad.numeroConsecutivoAnosBeneficioLey1429],
						aseben.anoFinBeneficioLey1429 AS [inicio.datosEmpleador.anoFinBeneficioLey1429],
						aseben.motivoInactivacionBeneficioLey1429 AS [inicio.datosEmpleador.motivoInactivacionBeneficioLey1429],
						aseben.empleadorCubiertoLey590 AS [inicio.datosEmpleador.empleadorCubiertoLey590],
						aseben.periodoInicioBeneficioLey590 AS [inicio.datosEmpleador.periodoInicioBeneficioLey590],
						aseben.numeroConsecutivoAnosBeneficioLey590 AS [inicio.datosEmpleador.numeroConsecutivoAnosBeneficioLey590],
						aseben.periodoFinBeneficioLey590 AS [inicio.datosEmpleador.periodoFinBeneficioLey590],
						aseben.motivoInactivacionBeneficioLey590 AS [inicio.datosEmpleador.motivoInactivacionBeneficioLey590],
						aseben.sucursalOrigenTraslado AS [inicio.datosEmpleador.sucursalOrigenTraslado],
						aseben.sucursalDestinoTraslado AS [inicio.datosEmpleador.sucursalDestinoTraslado],
						aseben.trabajadoresTraslado AS [inicio.datosEmpleador.trabajadoresTraslado],
						null AS [inicio.datosEmpleador.fechaFinLaboresSucursalOrigenTraslado],
						null AS [inicio.datosEmpleador.tipoIdentificacionOrigenSustPatronal],
 						null AS [inicio.datosEmpleador.numeroIdentificacionOrigenSustPatronal],
 						null AS [inicio.datosEmpleador.razonSocialOrigenSustPatronal],
						'[]' AS [inicio.datosEmpleador.sucursalesOrigenSustPatronal],
 						--quitar doble comillas
						null as [inicio.datosEmpleador.fechaFinLaboresOrigenSustPatronal],
						null AS [inicio.datosEmpleador.tipoIdentificacionDestinoSustPatronal],
 						null AS [inicio.datosEmpleador.numeroIdentificacionDestinoSustPatronal],
 						null AS [inicio.datosEmpleador.razonSocialDestinoSustPatronal],
						'[]' AS [inicio.datosEmpleador.trabajadoresSustPatronal],
						  
						  ---quitar comillas dobles
						null AS [inicio.datosEmpleador.motivoDesafiliacion],
						null AS [inicio.datosEmpleador.motivoAnulacionAfiliacion],
						'false' AS [inicio.datosEmpleador.requiereInactivacionCuentaWeb],
						null AS [inicio.datosEmpleador.medioDePagoSubsidioMonetarioSucursal],

						'EFECTIVO' AS [inicio.datosEmpleador.medioDePagoSubsidioMonetario],
						'[]' AS [inicio.datosEmpleador.sociosEmpleador],
						---quitar comillas dobles
						null AS [inicio.datosEmpleador.idEmpleadoresPersona],
						null AS [inicio.datosEmpleador.idEmpleadorDestinoSustPatronal],
						em.empEstadoEmpleador AS [inicio.datosEmpleador.estadoAfiliacion],
						em.empMotivoSubsanacionExpulsion AS [inicio.datosEmpleador.motivoSubsanacionExpulsion],
 						null AS [inicio.datosEmpleador.idEmpresaDestinoSustPatronal],
 						null AS [inicio.datosEmpleador.retencionSubsidioActivaEmpleador],  
 						null AS [inicio.datosEmpleador.motivoRetencionSubsidioEmpleador],
 						null AS [inicio.datosEmpleador.motivoInactivaRetencionSubsidioEmpleador],
 						null AS [inicio.datosEmpleador.retencionSubsidioActivaSucursal],
 						null AS [inicio.datosEmpleador.motivoRetencionSubsidioSucursal],
 						null AS [inicio.datosEmpleador.motivoInactivaRetencionSubsidioSucursal],
						 pe.perTipoIdentificacion AS [inicio.datosEmpleador.tipoIdentificacionNuevo],
						 pe.perNumeroIdentificacion AS [inicio.datosEmpleador.numeroIdentificacionNuevo],
						 pe.perDigitoVerificacion AS [inicio.datosEmpleador.digitoVerificacionNuevo],
						 'false' AS [inicio.datosEmpleador.perteneceDepartamento],
						 null AS [inicio.datosPersona],
						 null AS [inicio.personasRetiroAutomatico],
						 null AS [inicio.idCargueMultipleNovedad],
						 snov.snoEstadoSolicitud AS [inicio.estadoSolicitudNovedad],
						 datosnov.solNumeroRadicacion AS [inicio.numeroRadicacion],
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
			INNER JOIN SolicitudNovedadEmpleador  sne  WITH(NOLOCK)
					ON snov.snoid = sneIdSolicitudNovedad
			 LEFT JOIN ItemChequeo WITH(NOLOCK) ON ichSolicitud = datosnov.solid
			 LEFT JOIN Requisito  WITH(NOLOCK) ON ichRequisito = reqId AND reqid = 44
			INNER JOIN Empleador em  WITH(NOLOCK) ON em.empid = sne.sneIdEmpleador
			INNER JOIN Empresa e  WITH(NOLOCK) ON e.empId = em.empEmpresa
			INNER JOIN Persona pe  WITH(NOLOCK) ON  e.empPersona = pe.perid 
			INNER JOIN Codigociiu cce WITH(NOLOCK) ON  e.empCodigoCIIU = cce.ciiId
			
			INNER JOIN #DATOS_RL_RLS rl WITH(NOLOCK) ON rl.peridrl = e.empRepresentanteLegal
							  
			LEFT JOIN SucursalEmpresa se  WITH(NOLOCK) ON  se.sueEmpresa = e.empId and se.sueSucursalPrincipal = 1 ---and se.sueEstadoSucursal = 'ACTIVO'
			LEFT JOIN CodigoCIIU ccse  WITH(NOLOCK) ON se.sueCodigoCIIU = ccse.ciiId
			LEFT JOIN Ubicacion seu  WITH(NOLOCK) ON seu.ubiId = se.sueUbicacion
			LEFT JOIN Municipio sem  WITH(NOLOCK) ON seu.ubiMunicipio = sem.munId 
			LEFT JOIN Departamento sed  WITH(NOLOCK) ON sed.depId = sem.munDepartamento 
	 
			INNER JOIN #DATOS_TIPO_NOT tiponot ON  e.empid = tiponot.empidempresa
			INNER JOIN #DATOS_RC_AFI rafi ON rafi.rceEmpleador = EM.empId
			INNER JOIN #DATOS_RC_APO rapo ON rapo.rceEmpleador = EM.empId
			INNER JOIN #DATOS_RC_SUB rsub ON rsub.rceEmpleador = EM.empId
			INNER JOIN #DATOS_ASESOR_BENF aseben ON aseben.areEmpleador = EM.empId
							
		WHERE  solTipoTransaccion IN (  'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL',
										'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL',---,tenemos problemas y duplicidad
									---	'ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL',
										'CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL',
										'CAMBIOS_ROLES_CONTACTO_PRESENCIAL'
									) 
		AND  solId = @solid --5420881-- @solid--- 5323712
		FOR JSON PATH , ROOT (''),   INCLUDE_NULL_VALUES)
 
  ---INICIA JSON
  ---DROP TABLE jsonnovintegracion
 --CREATE TABLE jsonnovintegracion (DtsSolicitud VARCHAR(20),dtsJsonPayload nvarchar(max))
 DELETE sap.jsonnovintegracion_emp
 INSERT INTO sap.jsonnovintegracion_emp
	 SELECT @solid, @JSON

	UPDATE sap.jsonnovintegracion_emp SET DtsSolicitud = @solid
	UPDATE sap.jsonnovintegracion_emp SET dtsJsonPayload = SUBSTRING(dtsJsonPayload,16,LEN(dtsJsonPayload))
	UPDATE sap.jsonnovintegracion_emp SET dtsJsonPayload=  REPLACE (dtsJsonPayload,'"[]"','[]')
	UPDATE sap.jsonnovintegracion_emp SET dtsJsonPayload = REPLACE (dtsJsonPayload,':null}}]}',': null}') 
	UPDATE sap.jsonnovintegracion_emp SET dtsJsonPayload = REPLACE ( dtsJsonPayload, '"listaChequeoNovedad":{"idTemChequeo"','"listaChequeoNovedad":[{"idTemChequeo"')
	UPDATE sap.jsonnovintegracion_emp SET dtsJsonPayload = REPLACE ( dtsJsonPayload, '"fechaRecepcionDocumentos":null},"digitoVerificacion"','"fechaRecepcionDocumentos":null}],"digitoVerificacion"')
	
	---select max(dtsSolicitud) from DatoTemporalSolicitud--5404221
	insert into DatoTemporalSolicitud
		(dtsSolicitud
		,dtsJsonPayload
		,dtsTipoIdentificacion
		,dtsNumeroIdentificacion)	
	 SELECT *,NULL,NULL FROM sap.jsonnovintegracion_emp

		DROP TABLE #DATOS_RL_RLS
		DROP TABLE #DATOS_TIPO_NOT
		DROP TABLE #DATOS_RC_AFI
		DROP TABLE #DATOS_RC_SUB
		DROP TABLE #DATOS_RC_APO
		DROP TABLE #DATOS_ASESOR_BENF
		SET @solid = NULL
		SET @JSON = NULL
END