/****** Object:  StoredProcedure [dbo].[reporteMaestroAfiliados]    Script Date: 02/05/2023 9:02:22 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteMaestroAfiliados]    Script Date: 22/02/2023 3:05:00 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteMaestroAfiliados]    Script Date: 24/08/2022 10:48:15 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteMaestroAfiliados]    Script Date: 22/08/2022 5:02:00 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteMaestroAfiliados]    Script Date: 09/08/2022 6:36:24 p. m. ******/
 
-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 28 Junio 2022
---Update Date: 18-08-2022  por olga vega actualizando cuando es por dos empresas en la misma semana
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 12.
-- Reporte 12
---EXEC reporteMaestroAfiliados '2023-04-21','2023-04-27'
-- =======================================================
CREATE OR ALTER   PROCEDURE [dbo].[reporteMaestroAfiliados](
	@fechaInicio DATE,
	@fechaFinal DATE
)

AS
BEGIN 
SET NOCOUNT ON

--/---------------------------------------**********---------------------------------------\--
--                          MAESTRO DE AFILIADOS  -  N° 12.
--\---------------------------------------**********---------------------------------------/--

----------TITULARES
	SELECT 
		----------  Tipo Registro  ----------
		'2' AS [Tipo de registro],

		----------  Tipo de Identificacion  ----------
		CASE personaAfiliadaInfo.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA' 
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
			WHEN 'NIT' THEN 'NI'
		END AS [Tipo de identificación del afiliado],

		----------  Numero de Identificación  ----------
		SUBSTRING(personaAfiliadaInfo.perNumeroIdentificacion,0,17) AS [Número de identificación del afiliado],

		----------  Tipo de Identificación Beneficiario  ----------
		null AS [Tipo de identificación del miembro de la población cubierta],

		----------  Numero de identificacion de Beneficiario  ----------
		null AS [Número de identificación del miembro de la población cubierta],

		----------  Genero  ----------
		CASE personaAfiliadaInfoDetalle.pedGenero 
			WHEN 'MASCULINO' THEN 'M' 
			WHEN 'FEMENINO' THEN 'F' 
		END AS [Código género],

		----------  Fecha Nacimiento  ----------
		personaAfiliadaInfoDetalle.pedFechaNacimiento AS [Fecha de nacimiento],

		----------  Primer Apellido  ----------
		SUBSTRING(personaAfiliadaInfo.perPrimerApellido,0,60)  AS [Primer Apellido],

		----------  Segundo Apellido  ----------
		SUBSTRING(personaAfiliadaInfo.persegundoApellido,0,60) AS [Segundo Apellido],

		----------  Primer Nombre  ----------
		SUBSTRING(personaAfiliadaInfo.perPrimerNombre,0,60) AS [Primer nombre],

		----------  Segundo Nombre  ----------
		SUBSTRING(personaAfiliadaInfo.perSegundoNombre,0,60) AS [Segundo Nombre],
	
		----------  Departamento Residencia  ----------
		departamentoAfiliado.depCodigo AS [Departamento de residencia],

		----------  Municipio Rcidencia ----------
		SUBSTRING(municipioAfiliado.munCodigo,3,6) AS [Municipio residencia],

		----------  Fecha Afiliacion  ----------
		--rolAfiliado.roaFechaAfiliacion AS [Fecha de afiliación],
		FORMAT( clasificacion.roaFechaAfiliacion, 'yyyy-MM-dd') AS [Fecha de afiliación],

		----------  Codigo de la caja  ----------
		constante.cnsValor AS [Código caja de compensación familiar],

		----------  Tipo Solicitante  ----------
		CASE 
			WHEN clasificacion.solclasificacion IS NOT NULL OR clasificacion.solclasificacion <> 0
			THEN clasificacion.solclasificacion 
			ELSE 
				CASE 
					WHEN rolAfiliado.roaTipoAfiliado= 'TRABAJADOR_DEPENDIENTE' THEN '1'
					WHEN rolAfiliado.roaTipoAfiliado= 'TRABAJADOR_INDEPENDIENTE' THEN '2'
					WHEN rolAfiliado.roaTipoAfiliado='PENSIONADO' THEN '3'
				END 
		END AS [Código tipo de afiliado],
		--SELECT * from RolAfiliado where roaId = 1013693
		----------  Tipo de Identificación  ----------
		CASE 
			(
			CASE 
				WHEN clasificacion.solclasificacion = '1' 
			    THEN personaAportante.perTipoIdentificacion 
			    WHEN clasificacion.solclasificacion IN ( '3' ) 
			    THEN isnull(personaEmpresaPagadora.perTipoIdentificacion,personaAfiliadaInfo.perTipoIdentificacion )
			    WHEN clasificacion.solclasificacion IN ( '2', '4','6') 
			    THEN personaAfiliadaInfo.perTipoIdentificacion 
			    ELSE personaEmpresaPagadora.perTipoIdentificacion
			END

			)
			WHEN 'NIT' THEN 'NI'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA' 
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'SALVOCONDUCTO' THEN 'SC'

		END AS [Tipo de identificación del aportante],

		----------  Numero Identificacion  ----------
		CASE 
			   WHEN clasificacion.solclasificacion = '1' 
			   THEN personaAportante.perNumeroIdentificacion 
			    WHEN clasificacion.solclasificacion IN ( '3' ) 
			   THEN isnull(personaEmpresaPagadora.perNumeroIdentificacion,personaAfiliadaInfo.perNumeroIdentificacion )
			   WHEN clasificacion.solclasificacion IN ( '2', '4','6') 
			   THEN personaAfiliadaInfo.perNumeroIdentificacion 
			   ELSE personaEmpresaPagadora.perNumeroIdentificacion

		END AS [Número de identificación del aportante],

		----------  Digito de Verificación  ----------
		CASE 
				WHEN clasificacion.solclasificacion = '1' 
				THEN personaAportante.perDigitoVerificacion 
			    WHEN clasificacion.solclasificacion IN ( '3' ) 
			   THEN isnull(personaEmpresaPagadora.perDigitoVerificacion,personaAfiliadaInfo.perDigitoVerificacion )

				WHEN clasificacion.solclasificacion IN ( '2', '4','6') 
				THEN personaAfiliadaInfo.perDigitoVerificacion 
			    ELSE personaEmpresaPagadora.perDigitoVerificacion 

		END AS [Dígito de verificación del aportante],

		----------  Razon Social  ----------
		SUBSTRING
			(
				(
				  CASE 
							WHEN clasificacion.solclasificacion = '1' 
							THEN personaAportante.perRazonSocial 
						    WHEN clasificacion.solclasificacion IN ( '3' ) 
			                THEN isnull(personaEmpresaPagadora.perRazonSocial,personaAfiliadaInfo.perRazonSocial )
							WHEN clasificacion.solclasificacion IN ( '2', '4','6') 
							THEN personaAfiliadaInfo.perRazonSocial 
							ELSE personaEmpresaPagadora.perRazonSocial
				  END
				)
				,0,150
			)
			AS [Razón social del aportante],

		----------  Fecha de Vinculacion con el aportante  ----------  
		ISNULL(rolAfiliado.roaFechaIngreso,rolAfiliado.roaFechaAfiliacion) AS [Fecha de vinculación con el aportante],
			
		----------  Departamento  ----------
		CASE 
			WHEN clasificacion.solclasificacion = '1' 
			THEN ISNULL((SELECT SUBSTRING(depCodigo ,1,2)
					  FROM Municipio  
				 INNER JOIN Departamento ON depId= munDepartamento
					   WHERE munid = roaMunicipioDesempenioLabores),SUBSTRING(departamentoEmpresa.depCodigo,1,2))
			ELSE departamentoAfiliado.depCodigo 
		END AS [Departamento de la ubicación laboral],

		----------  Municipio  ----------
		CASE 
			WHEN clasificacion.solclasificacion = '1' 
			THEN	ISNULL((SELECT SUBSTRING(munCodigo ,3,6)
					  FROM Municipio  
				 INNER JOIN Departamento ON depId= munDepartamento
					   WHERE munid = roaMunicipioDesempenioLabores),SUBSTRING(municipioEmpresa.munCodigo,3,6))
			 
			ELSE SUBSTRING(municipioAfiliado.munCodigo,3,6) 
		END AS [Municipio de la ubicación laboral],

		----------  Estado de Cartera  ----------
		CASE ISNULL(auxCartera.carEstadoCartera,'AL_DIA') 
			WHEN 'AL_DIA' THEN '1' 
			WHEN 'MOROSO' THEN '2' 
		END [Al día],

		----------  Tipo de Miembro de la poblacion Cubierta  ----------
		'1' AS [Código tipo de miembro de la población cubierta],	

		----------  Codigo Condicion del Beneficiario
		null AS [Código condición beneficiario],

		----------  Tipo relacion Con el Afiliado ----------
		null AS [Código tipo relación con afiliado]

		--entiPagadora.epaId,

		--rolAfiliado.roaTipoAfiliado

		/*DECLARE @fechaInicio DATE, @fechaFinal DATE
	SET @fechaInicio = '01-01-2022'
	SET @fechaFinal = '05-02-2022'
		SELECT */
	FROM 
		Afiliado afiliado 
		----------  Se obtiene la informacion del afiliado  ----------
		INNER JOIN Persona personaAfiliadaInfo ON personaAfiliadaInfo.perId = afiliado.afiPersona
		INNER JOIN PersonaDetalle personaAfiliadaInfoDetalle 
		        ON personaAfiliadaInfoDetalle.pedPersona = personaAfiliadaInfo.perId

		INNER JOIN RolAfiliado rolAfiliado ON rolAfiliado.roaAfiliado = afiliado.afiId 
		       AND rolAfiliado.roaEstadoAfiliado = 'ACTIVO'
			

		LEFT JOIN EntidadPagadora AS entiPagadora ON entiPagadora.epaId = rolAfiliado.roaPagadorAportes
		LEFT JOIN Empresa AS empresaPagadora ON entiPagadora.epaEmpresa = empresaPagadora.empId
		LEFT JOIN Persona AS personaEmpresaPagadora ON personaEmpresaPagadora.perId = empresaPagadora.empPersona

 

		LEFT JOIN Ubicacion ubicacionAfiliado ON ubicacionAfiliado.ubiId = personaAfiliadaInfo.perUbicacionPrincipal
		LEFT JOIN Municipio municipioAfiliado ON municipioAfiliado.munId = ubicacionAfiliado.ubiMunicipio
		LEFT JOIN Departamento departamentoAfiliado ON departamentoAfiliado.depId = municipioAfiliado.munDepartamento
	
		----------  Se obtiene la informacion del empleador  ----------
		LEFT JOIN empleador empleador on empleador.empid=rolAfiliado.roaempleador
		LEFT JOIN Empresa empresa ON empresa.empId = empleador.empempresa
		LEFT JOIN Persona personaAportante ON personaAportante.perId = empresa.empPersona
		LEFT JOIN UbicacionEmpresa ubicacionEmpresa ON ubicacionEmpresa.ubeEmpresa = empresa.empId 
			  AND ubicacionEmpresa.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubicacionEmpresaComp ON ubicacionEmpresaComp.ubiId = ubicacionEmpresa.ubeUbicacion
		LEFT JOIN Municipio municipioEmpresa ON municipioEmpresa.munId = ubicacionEmpresaComp.ubiMunicipio
		LEFT JOIN Departamento departamentoEmpresa ON departamentoEmpresa.depId = municipioEmpresa.munDepartamento
 

		----------  Se valida el estado en cartera del afiliado  ----------
		LEFT JOIN (
			SELECT 
				carPersona,
				carEstadoCartera 
			FROM Cartera car 
			WHERE carId IN (
				SELECT MAX(carId) 
				FROM Cartera 
				WHERE 
					carPersona = carPersona 
					AND carEstadoOperacion = 'VIGENTE' 
					AND carDeudaPresunta > 0
				GROUP BY carPersona
			)
		) auxCartera ON auxCartera.carPersona = empresa.emppersona

		LEFT JOIN Constante constante ON constante.cnsNombre = 'CAJA_COMPENSACION_CODIGO'
 
		
		INNER JOIN (SELECT  
						CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) 
						OVER (PARTITION BY roaid, roaTipoAfiliado) THEN solFechaRadicacion ELSE NULL END AS solFechaRadicacion,
						roaafiliado,sapRolAfiliado,roaFechaAfiliacion,
						CASE 
					WHEN roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN '1'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN '4'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN '2'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion <> 'FIDELIDAD_25_ANIOS' THEN '3'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion = 'FIDELIDAD_25_ANIOS' THEN '6'
					ELSE '0'
				END AS solclasificacion
					 FROM solicitudafiliacionpersona (nolock)  
			   INNER JOIN solicitud sx (nolock) 
					   ON sapSolicitudGlobal=sx.solid
			   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
			        WHERE solResultadoProceso = 'APROBADA'
					 AND solTipoTransaccion LIKE '%AFIL%'
					 AND solFechaRadicacion >= @fechaInicio AND solFechaRadicacion < DATEADD(DAY,1,@fechaFinal)
				 
					 AND roaAfiliado NOT IN (  SELECT roaAfiliado
												 FROM solicitudafiliacionpersona (nolock)  
										   INNER JOIN solicitud sx (nolock) 
												   ON sapSolicitudGlobal=sx.solid
										   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
												WHERE solResultadoProceso = 'APROBADA'
												  AND solTipoTransaccion LIKE '%AFIL%'
												  AND solFechaCreacion<@fechaInicio
											 GROUP BY roaAfiliado
										--	   HAVING COUNT(*)>1
											  )
					  
				) as clasificacion 
		ON clasificacion.sapRolAfiliado=rolAfiliado.roaid 
		AND clasificacion.solFechaRadicacion IS NOT NULL
	 	 --	WHERE personaAfiliadaInfo.perNumeroIdentificacion = '91736'
	 

	----------FINAL TITULARES

  UNION

----------BENEFICIARIOS POBLACION CUBIERTA

		SELECT DISTINCT
			----------  Tipo de registro  ----------
			'2' AS tipoRegistro,

			----------  Tipo Identificacion Titular  ----------
			CASE personaAfiliadaInfo.perTipoIdentificacion
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'PASAPORTE' THEN 'PA' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'SALVOCONDUCTO' THEN 'SC'
				WHEN 'NIT' THEN 'NI'
			END tipoIdentificacionAfiliado,

			----------  Numero de identificacion Titular  ----------

			SUBSTRING(personaAfiliadaInfo.perNumeroIdentificacion,0,17) numeroIdentificacionAfiliado,

			----------  Tipo de identificacion Beneficiario  ----------
			CASE personaBeneficiarioInfo.perTipoIdentificacion
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'PASAPORTE' THEN 'PA' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'SALVOCONDUCTO' THEN 'SC'
				WHEN 'NIT' THEN 'NI'
			END tipoIdentificacionPoblacion,

			----------  Numero de identificacion beneficiario  ----------
			SUBSTRING(personaBeneficiarioInfo.perNumeroIdentificacion,0,17) numeroIdentificacionPoblacion,

			----------  Genero  ----------
			CASE personaBeneficiarioInfoDetalle.pedGenero 
				WHEN 'MASCULINO' THEN 'M' 
				WHEN 'FEMENINO' THEN 'F'
			END codigoGenero,

			----------  Fecha Nacimiento  ----------
			personaBeneficiarioInfoDetalle.pedFechaNacimiento fechaNacimiento,

			----------  Primer Apellido  ----------
			SUBSTRING(personaBeneficiarioInfo.perPrimerApellido,0,60) primerApellido,

			----------  Segundo Apellido  ----------
			SUBSTRING(personaBeneficiarioInfo.persegundoApellido,0,60) segundoApellido,

			----------  Primer Nombre  ----------
			SUBSTRING(personaBeneficiarioInfo.perPrimerNombre,0,60) primerNombre,

			----------  Segundo Nombre  ----------
			SUBSTRING(personaBeneficiarioInfo.perSegundoNombre,0,60) segundoNombre,


			----------  Departamento Residencia  ----------
			ISNULL(
				(
					SELECT TOP 1 depcodigo
					FROM 
						[dbo].[GrupoFamiliar]
						INNER JOIN ubicacion ON grfubicacion= ubiId
						INNER JOIN municipio ON ubimunicipio= munid
						INNER JOIN departamento ON mundepartamento=depid
					WHERE grfid=[benGrupoFamiliar]
				),''
			)  departamentoResidencia,

			----------  Municipio Residencia  ----------
			ISNULL(
				(
					SELECT TOP 1 SUBSTRING(muncodigo,3,6)
					FROM 
						[dbo].[GrupoFamiliar]
						INNER JOIN ubicacion ON grfubicacion= ubiId
						INNER JOIN municipio ON ubimunicipio= munid
						INNER JOIN departamento ON mundepartamento=depid
					WHERE grfid=[benGrupoFamiliar]
				),''
			)  MunicipioResidencia,

			----------  Fecha de afiliacion  ----------
			beneficiario.benFechaAfiliacion fechaAfiliacion,

			----------  codigo de la caja  ----------
			constante.cnsValor AS codigoCaja, 																	
	
			----------  Tipo Solicitante  ----------
			/*CASE 
				WHEN beneficiario.benTipoBeneficiario IN ('CONYUGE') 
				THEN '3'
				ELSE ''
			END*/ NULL AS codTipoAfiliado,

	 

			----------  Tipo de Identificación  ----------
		CASE 
			(
			CASE 
				WHEN clasificacion.solclasificacion = '1' 
			    THEN personaAportante.perTipoIdentificacion 
			    WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
			    THEN personaAfiliadaInfo.perTipoIdentificacion 
			    ELSE ISNULL(personaEmpresaPagadora.perTipoIdentificacion,personaAfiliadaInfo.perTipoIdentificacion )
			END

			)
			WHEN 'NIT' THEN 'NI'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA' 
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'SALVOCONDUCTO' THEN 'SC'

		END AS [Tipo de identificación del aportante],

		----------  Numero Identificacion  ----------
		CASE 
			   WHEN clasificacion.solclasificacion = '1' 
			   THEN personaAportante.perNumeroIdentificacion 
			   WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
			   THEN personaAfiliadaInfo.perNumeroIdentificacion 
			   ELSE ISNULL(personaEmpresaPagadora.perNumeroIdentificacion, personaAfiliadaInfo.perNumeroIdentificacion )

		END AS [Número de identificación del aportante],

		----------  Digito de Verificación  ----------
		CASE 
				WHEN clasificacion.solclasificacion = '1' 
				THEN personaAportante.perDigitoVerificacion 
				WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
				THEN personaAfiliadaInfo.perDigitoVerificacion 
			    ELSE ISNULL(personaEmpresaPagadora.perDigitoVerificacion ,personaAfiliadaInfo.perDigitoVerificacion)

		END AS [Dígito de verificación del aportante],

		----------  Razon Social  ----------
		SUBSTRING
			(
				(
				  CASE 
							WHEN clasificacion.solclasificacion = '1' 
							THEN personaAportante.perRazonSocial 
							WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
							THEN personaAfiliadaInfo.perRazonSocial 
							ELSE ISNULL(personaEmpresaPagadora.perRazonSocial, personaAfiliadaInfo.perRazonSocial)
				  END
				)
				,0,150
			)
			AS [Razón social del aportante],

		 

			----------  Fecha de vinculacion con el aportante  ----------
			NULL fechaVinculacion,

			----------  Departamento Aportante  ----------
			'' AS departamentroLaboral,

			----------  Municipio Aportante  ----------
			'' AS municipioLaboral,

			----------  Estado Cartera  ----------
			NULL alDia,

			----------  Codigo Tipo Miembro  ----------
			CASE 
				WHEN beneficiario.benTipoBeneficiario IN ('HIJO_BIOLOGICO', 'HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA','PADRE','MADRE', 'HERMANO_HUERFANO_DE_PADRES') THEN '2'
				WHEN beneficiario.benTipoBeneficiario IN ('CONYUGE') THEN '3'

				--when beneficiario.benTipoBeneficiario IN ('') THEN '4'

			END codigoTipoMiembro,	

			--select * from persona where perNumeroIdentificacion = '1035971960'

			----------  Codigo condicion del beneficiario  ----------
			CASE 
				WHEN condicionInvalidez.coiInvalidez=1 THEN 'D' 
				ELSE (
					SELECT TOP 1 
						CASE 
							WHEN cebFechaVencimiento>=@fechaFinal THEN 'E' 
							ELSE NULL 
						END
					FROM 
						beneficiario benx
						INNER JOIN beneficiariodetalle bdx ON benx.benBeneficiarioDetalle=bedId
						INNER JOIN CertificadoEscolarBeneficiario c ON c.cebBeneficiarioDetalle=bdx.bedid
					WHERE 
						benx.bentipobeneficiario NOT IN( 'CONYUGE','PADRE','MADRE')
						AND benx.benId=beneficiario.benid
				) 
			END CodigoCondicionBeneficiario,

			----------  Tipo relacion con el Afiliado  ----------
			CASE 
				WHEN beneficiario.benTipoBeneficiario = 'CONYUGE' THEN '1'
				WHEN beneficiario.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO', 'BENEFICIARIO_EN_CUSTODIA') THEN '2'
				WHEN beneficiario.benTipoBeneficiario IN ('PADRE','MADRE') THEN '3'
				WHEN beneficiario.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES') THEN '4'
				else beneficiario.benTipoBeneficiario
			END tipoRelacionConAfiliado

		FROM 
			Afiliado afiliado 
			INNER JOIN Persona personaAfiliadaInfo ON personaAfiliadaInfo.perId = afiliado.afiPersona
			INNER JOIN PersonaDetalle personaAfiliadaInfoDetalle ON personaAfiliadaInfoDetalle.pedPersona = personaAfiliadaInfo.perId
			INNER JOIN RolAfiliado rolAfiliado 
				ON rolAfiliado.roaAfiliado = afiliado.afiId 
				AND rolAfiliado.roaEstadoAfiliado = 'ACTIVO'
				
			INNER JOIN Beneficiario beneficiario ON beneficiario.benAfiliado = afiliado.afiId AND beneficiario.benFechaAfiliacion BETWEEN @fechaInicio AND @fechaFinal
			INNER JOIN BeneficiarioDetalle beneficiarioDetalle 
			ON beneficiarioDetalle.bedId = beneficiario.benBeneficiarioDetalle
			 AND benEstadoBeneficiarioAfiliado = 'ACTIVO'

			LEFT JOIN CertificadoEscolarBeneficiario cestificadoEscolarBeneficiario ON cestificadoEscolarBeneficiario.cebBeneficiarioDetalle = beneficiarioDetalle.bedId
			LEFT JOIN Persona personaBeneficiarioInfo ON personaBeneficiarioInfo.perId = beneficiario.benPersona
			LEFT JOIN PersonaDetalle personaBeneficiarioInfoDetalle ON personaBeneficiarioInfoDetalle.pedPersona = personaBeneficiarioInfo.perId
			LEFT JOIN Ubicacion ubicacionAfiliado ON ubicacionAfiliado.ubiId = personaAfiliadaInfo.perUbicacionPrincipal
			
			LEFT JOIN empleador empleador ON empleador.empid = rolAfiliado.roaempleador
			LEFT JOIN Empresa empresa ON empresa.empId = empleador.empempresa
			LEFT JOIN Persona personaAportante ON personaAportante.perId = empresa.emppersona
			
		    LEFT JOIN EntidadPagadora AS entiPagadora ON entiPagadora.epaId = rolAfiliado.roaPagadorAportes
		    LEFT JOIN Empresa AS empresaPagadora ON entiPagadora.epaEmpresa = empresaPagadora.empId
		    LEFT JOIN Persona AS personaEmpresaPagadora ON personaEmpresaPagadora.perId = empresaPagadora.empPersona
 			
			LEFT JOIN CondicionInvalidez condicionInvalidez ON condicionInvalidez.coiPersona = personaBeneficiarioInfo.perId
			LEFT JOIN (
				SELECT 
					carPersona,
					carEstadoCartera 
				FROM 
					Cartera car 
				WHERE 
					carId IN (
						SELECT MAX(carId) 
						FROM Cartera 
						WHERE 
							carPersona = carPersona 
							AND carEstadoOperacion = 'VIGENTE' 
							AND carDeudaPresunta>0
						GROUP BY carPersona
					)
			) auxCartera ON auxCartera.carPersona = empresa.emppersona

	
			LEFT JOIN Constante constante ON constante.cnsNombre = 'CAJA_COMPENSACION_CODIGO'
			
			INNER JOIN (SELECT  
						CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) OVER (PARTITION BY roaid, roaTipoAfiliado) THEN solFechaRadicacion ELSE NULL END AS solFechaRadicacion,
						          roaafiliado,sapRolAfiliado,roaFechaAfiliacion,
						CASE 
					WHEN roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN '1'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN '4'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN '2'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion <> 'FIDELIDAD_25_ANIOS' THEN '3'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion = 'FIDELIDAD_25_ANIOS' THEN '6'
					ELSE '0'
				END AS solclasificacion
					 FROM solicitudafiliacionpersona (nolock)  
			   INNER JOIN solicitud sx (nolock) 
					   ON sapSolicitudGlobal=sx.solid
			   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
			        WHERE solResultadoProceso = 'APROBADA'
					  AND solTipoTransaccion LIKE '%AFIL%'
					  AND solFechaRadicacion >= @fechaInicio AND solFechaRadicacion < DATEADD(DAY,1,@fechaFinal)
					    AND roaAfiliado NOT IN (  SELECT roaAfiliado
												 FROM solicitudafiliacionpersona (nolock)  
										   INNER JOIN solicitud sx (nolock) 
												   ON sapSolicitudGlobal=sx.solid
										   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
												WHERE solResultadoProceso = 'APROBADA'
												  AND solTipoTransaccion LIKE '%AFIL%'
												  AND solFechaCreacion<@fechaInicio
											 GROUP BY roaAfiliado
										--	   HAVING COUNT(*)>1
											  )
				) as afiliacion  

		ON Afiliacion.sapRolAfiliado=rolAfiliado.roaid 
		AND Afiliacion.solFechaRadicacion IS NOT NULL
				INNER JOIN (SELECT  
						CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) 
						OVER (PARTITION BY roaid, roaTipoAfiliado) THEN solFechaRadicacion ELSE NULL END AS solFechaRadicacion,
						roaafiliado,sapRolAfiliado,roaFechaAfiliacion,roaTipoAfiliado,
						CASE 
					WHEN roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN '1'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN '4'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN '2'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion <> 'FIDELIDAD_25_ANIOS' THEN '3'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion = 'FIDELIDAD_25_ANIOS' THEN '6'
					ELSE '0'
				END AS solclasificacion
					 FROM solicitudafiliacionpersona (nolock)  
			   INNER JOIN solicitud sx (nolock) 
					   ON sapSolicitudGlobal=sx.solid
			   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
			        WHERE solResultadoProceso = 'APROBADA'
					 AND solTipoTransaccion LIKE '%AFIL%'
					 AND solFechaRadicacion >= @fechaInicio AND solFechaRadicacion < DATEADD(DAY,1,@fechaFinal)
				 
					 AND roaAfiliado NOT IN (  SELECT roaAfiliado
												 FROM solicitudafiliacionpersona (nolock)  
										   INNER JOIN solicitud sx (nolock) 
												   ON sapSolicitudGlobal=sx.solid
										   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
												WHERE solResultadoProceso = 'APROBADA'
												  AND solTipoTransaccion LIKE '%AFIL%'
												  AND solFechaCreacion<@fechaInicio
											 GROUP BY roaAfiliado
										--	   HAVING COUNT(*)>1
											  )
					  
				) as clasificacion 
				ON clasificacion.sapRolAfiliado=rolAfiliado.roaid 
				AND clasificacion.roaTipoAfiliado=rolAfiliado.roaTipoAfiliado 
		AND clasificacion.solFechaRadicacion IS NOT NULL
 	---  WHERE personaAfiliadaInfo.perNumeroIdentificacion = '91736'
 	 
----------FINAL BENEFICIARIOS POBLACION CUBIERTA
 UNION

-----beneficiarios nuevos por novedad
	SELECT DISTINCT
			----------  Tipo de registro  ----------
			'2' AS tipoRegistro,

			----------  Tipo Identificacion Titular  ----------
			CASE personaAfiliadaInfo.perTipoIdentificacion
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'PASAPORTE' THEN 'PA' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'SALVOCONDUCTO' THEN 'SC'
				WHEN 'NIT' THEN 'NI'
			END tipoIdentificacionAfiliado,

			----------  Numero de identificacion Titular  ----------

			SUBSTRING(personaAfiliadaInfo.perNumeroIdentificacion,0,17) numeroIdentificacionAfiliado,

			----------  Tipo de identificacion Beneficiario  ----------
			CASE personaBeneficiarioInfo.perTipoIdentificacion
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'PASAPORTE' THEN 'PA' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'SALVOCONDUCTO' THEN 'SC'
				WHEN 'NIT' THEN 'NI'
			END tipoIdentificacionPoblacion,

			----------  Numero de identificacion beneficiario  ----------
			SUBSTRING(personaBeneficiarioInfo.perNumeroIdentificacion,0,17) numeroIdentificacionPoblacion,

			----------  Genero  ----------
			CASE personaBeneficiarioInfoDetalle.pedGenero 
				WHEN 'MASCULINO' THEN 'M' 
				WHEN 'FEMENINO' THEN 'F'
			END codigoGenero,

			----------  Fecha Nacimiento  ----------
			personaBeneficiarioInfoDetalle.pedFechaNacimiento fechaNacimiento,

			----------  Primer Apellido  ----------
			SUBSTRING(personaBeneficiarioInfo.perPrimerApellido,0,60) primerApellido,

			----------  Segundo Apellido  ----------
			SUBSTRING(personaBeneficiarioInfo.persegundoApellido,0,60) segundoApellido,

			----------  Primer Nombre  ----------
			SUBSTRING(personaBeneficiarioInfo.perPrimerNombre,0,60) primerNombre,

			----------  Segundo Nombre  ----------
			SUBSTRING(personaBeneficiarioInfo.perSegundoNombre,0,60) segundoNombre,


			----------  Departamento Residencia  ----------
			ISNULL(
				(
					SELECT TOP 1 depcodigo
					FROM 
						[dbo].[GrupoFamiliar]
						INNER JOIN ubicacion ON grfubicacion= ubiId
						INNER JOIN municipio ON ubimunicipio= munid
						INNER JOIN departamento ON mundepartamento=depid
					WHERE grfid=[benGrupoFamiliar]
				),''
			)  departamentoResidencia,

			----------  Municipio Residencia  ----------
			ISNULL(
				(
					SELECT TOP 1 SUBSTRING(muncodigo,3,6)
					FROM 
						[dbo].[GrupoFamiliar]
						INNER JOIN ubicacion ON grfubicacion= ubiId
						INNER JOIN municipio ON ubimunicipio= munid
						INNER JOIN departamento ON mundepartamento=depid
					WHERE grfid=[benGrupoFamiliar]
				),''
			)  MunicipioResidencia,

			----------  Fecha de afiliacion  ----------
			beneficiario.benFechaAfiliacion fechaAfiliacion,

			----------  codigo de la caja  ----------
			constante.cnsValor AS codigoCaja, 																	
	
			----------  Tipo Solicitante  ----------
            NULL AS codTipoAfiliado,

		----------  Tipo de Identificación  ----------
		CASE 
			(
			CASE 
				WHEN clasificacion.solclasificacion = '1' 
			    THEN personaAportante.perTipoIdentificacion 
			    WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
			    THEN personaAfiliadaInfo.perTipoIdentificacion 
			    ELSE ISNULL(personaEmpresaPagadora.perTipoIdentificacion, personaAfiliadaInfo.perTipoIdentificacion )
			END

			)
			WHEN 'NIT' THEN 'NI'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA' 
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'SALVOCONDUCTO' THEN 'SC'

		END AS [Tipo de identificación del aportante],

		----------  Numero Identificacion  ----------
		CASE 
			   WHEN clasificacion.solclasificacion = '1' 
			   THEN personaAportante.perNumeroIdentificacion 
			   WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
			   THEN personaAfiliadaInfo.perNumeroIdentificacion 
			   ELSE ISNULL(personaEmpresaPagadora.perNumeroIdentificacion,personaAfiliadaInfo.perNumeroIdentificacion )

		END AS [Número de identificación del aportante],

		----------  Digito de Verificación  ----------
		CASE 
				WHEN clasificacion.solclasificacion = '1' 
				THEN personaAportante.perDigitoVerificacion 
				WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
				THEN personaAfiliadaInfo.perDigitoVerificacion 
			    ELSE ISNULL(personaEmpresaPagadora.perDigitoVerificacion ,personaAfiliadaInfo.perDigitoVerificacion)

		END AS [Dígito de verificación del aportante],

		----------  Razon Social  ----------
		SUBSTRING
			(
				(
				  CASE 
							WHEN clasificacion.solclasificacion = '1' 
							THEN personaAportante.perRazonSocial 
							WHEN clasificacion.solclasificacion IN ( '2', '4','6' ) 
							THEN personaAfiliadaInfo.perRazonSocial 
							ELSE ISNULL(personaEmpresaPagadora.perRazonSocial,personaAfiliadaInfo.perRazonSocial )
				  END
				)
				,0,150
			)
			AS [Razón social del aportante],

			----------  Fecha de vinculacion con el aportante  ----------
			NULL fechaVinculacion,

			----------  Departamento Aportante  ----------
			'' AS departamentroLaboral,

			----------  Municipio Aportante  ----------
			'' AS municipioLaboral,

			----------  Estado Cartera  ----------
			NULL alDia,

			----------  Codigo Tipo Miembro  ----------
			CASE 
				WHEN beneficiario.benTipoBeneficiario IN ('HIJO_BIOLOGICO', 'HIJO_ADOPTIVO','HIJASTRO','BENEFICIARIO_EN_CUSTODIA','PADRE','MADRE', 'HERMANO_HUERFANO_DE_PADRES') THEN '2'
				WHEN beneficiario.benTipoBeneficiario IN ('CONYUGE') THEN '3'

				--when beneficiario.benTipoBeneficiario IN ('') THEN '4'

			END codigoTipoMiembro,	

			--select * from persona where perNumeroIdentificacion = '1035971960'

			----------  Codigo condicion del beneficiario  ----------
			CASE 
				WHEN condicionInvalidez.coiInvalidez=1 THEN 'D' 
				ELSE (
					SELECT TOP 1 
						CASE 
							WHEN cebFechaVencimiento>=@fechaFinal THEN 'E' 
							ELSE NULL 
						END
					FROM 
						beneficiario benx
						INNER JOIN beneficiariodetalle bdx ON benx.benBeneficiarioDetalle=bedId
						INNER JOIN CertificadoEscolarBeneficiario c ON c.cebBeneficiarioDetalle=bdx.bedid
					WHERE 
						benx.bentipobeneficiario NOT IN( 'CONYUGE','PADRE','MADRE')
						AND benx.benId=beneficiario.benid
				) 
			END CodigoCondicionBeneficiario,

			----------  Tipo relacion con el Afiliado  ----------
			CASE 
				WHEN beneficiario.benTipoBeneficiario = 'CONYUGE' THEN '1'
				WHEN beneficiario.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO', 'BENEFICIARIO_EN_CUSTODIA') THEN '2'
				WHEN beneficiario.benTipoBeneficiario IN ('PADRE','MADRE') THEN '3'
				WHEN beneficiario.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES') THEN '4'
				else beneficiario.benTipoBeneficiario
			END tipoRelacionConAfiliado 
FROM 
			Afiliado afiliado 
			INNER JOIN Persona personaAfiliadaInfo ON personaAfiliadaInfo.perId = afiliado.afiPersona
			INNER JOIN PersonaDetalle personaAfiliadaInfoDetalle ON personaAfiliadaInfoDetalle.pedPersona = personaAfiliadaInfo.perId
			INNER JOIN RolAfiliado rolAfiliado 
				ON rolAfiliado.roaAfiliado = afiliado.afiId 
				AND rolAfiliado.roaEstadoAfiliado = 'ACTIVO'
				
			INNER JOIN Beneficiario beneficiario 
			        ON beneficiario.benAfiliado = afiliado.afiId 
			       AND beneficiario.benFechaAfiliacion >= @fechaInicio 
			       AND beneficiario.benFechaAfiliacion <DATEADD(DAY,1,@fechaFinal)
				   AND benEstadoBeneficiarioAfiliado = 'ACTIVO'
			INNER JOIN BeneficiarioDetalle beneficiarioDetalle ON beneficiarioDetalle.bedId = beneficiario.benBeneficiarioDetalle

			LEFT JOIN CertificadoEscolarBeneficiario cestificadoEscolarBeneficiario ON cestificadoEscolarBeneficiario.cebBeneficiarioDetalle = beneficiarioDetalle.bedId
			LEFT JOIN Persona personaBeneficiarioInfo ON personaBeneficiarioInfo.perId = beneficiario.benPersona
			LEFT JOIN PersonaDetalle personaBeneficiarioInfoDetalle ON personaBeneficiarioInfoDetalle.pedPersona = personaBeneficiarioInfo.perId
			LEFT JOIN Ubicacion ubicacionAfiliado ON ubicacionAfiliado.ubiId = personaAfiliadaInfo.perUbicacionPrincipal
			
			LEFT JOIN empleador empleador ON empleador.empid = rolAfiliado.roaempleador
			LEFT JOIN Empresa empresa ON empresa.empId = empleador.empempresa
			LEFT JOIN Persona personaAportante ON personaAportante.perId = empresa.emppersona
			
		    LEFT JOIN EntidadPagadora AS entiPagadora ON entiPagadora.epaId = rolAfiliado.roaPagadorAportes
		    LEFT JOIN Empresa AS empresaPagadora ON entiPagadora.epaEmpresa = empresaPagadora.empId
		    LEFT JOIN Persona AS personaEmpresaPagadora ON personaEmpresaPagadora.perId = empresaPagadora.empPersona
		
			LEFT JOIN CondicionInvalidez condicionInvalidez ON condicionInvalidez.coiPersona = personaBeneficiarioInfo.perId
			LEFT JOIN (
				SELECT 
					carPersona,
					carEstadoCartera 
				FROM 
					Cartera car 
				WHERE 
					carId IN (
						SELECT MAX(carId) 
						FROM Cartera 
						WHERE 
							carPersona = carPersona 
							AND carEstadoOperacion = 'VIGENTE' 
							AND carDeudaPresunta>0
						GROUP BY carPersona
					)
			) auxCartera ON auxCartera.carPersona = empresa.emppersona

	
			LEFT JOIN Constante constante ON constante.cnsNombre = 'CAJA_COMPENSACION_CODIGO'
			
			INNER JOIN (SELECT  

						CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) OVER (PARTITION BY benpersona, benafiliado) 
						    THEN solFechaRadicacion ELSE NULL END AS solFechaRadicacion,
						          benId,benPersona, benAfiliado,solTipoTransaccion AS solclasificacion
					 FROM solicitud sx (nolock) 
			   INNER JOIN SolicitudNovedad 
					   ON snoSolicitudGlobal = sx.solId
			   INNER JOIN SolicitudNovedadPersona ON snpSolicitudNovedad = snoId
			   INNER JOIN Beneficiario ON benId = snpBeneficiario
			        WHERE solResultadoProceso = 'APROBADA'
					  AND solTipoTransaccion LIKE 'ACTI%'
					 AND  solFechaRadicacion >= @fechaInicio 
					 AND solFechaRadicacion <DATEADD(DAY,1,@fechaFinal)
				) as afiliacion  
		ON Afiliacion.benAfiliado=rolAfiliado.roaAfiliado 
		AND beneficiario.benId = afiliacion.benId
		AND Afiliacion.solFechaRadicacion IS NOT NULL


		INNER JOIN (SELECT  
						CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) OVER (PARTITION BY roaid, roaTipoAfiliado) THEN solFechaRadicacion ELSE NULL END AS solFechaRadicacion,
						roaafiliado,sapRolAfiliado,roaFechaAfiliacion, roaTipoAfiliado,
						CASE 
					WHEN roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN '1'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' THEN '4'
					WHEN roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' 
					     AND solclasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' THEN '2'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion <> 'FIDELIDAD_25_ANIOS' THEN '3'
					WHEN roaTipoAfiliado = 'PENSIONADO' AND solclasificacion = 'FIDELIDAD_25_ANIOS' THEN '6'
					ELSE '0'
				END AS solclasificacion
					 FROM solicitudafiliacionpersona (nolock)  
			   INNER JOIN solicitud sx (nolock) 
					   ON sapSolicitudGlobal=sx.solid
			   INNER JOIN RolAfiliado ON roaid = sapRolAfiliado
			        WHERE solResultadoProceso = 'APROBADA'
					 AND solTipoTransaccion LIKE '%AFIL%'
  				  
				) as clasificacion 
		ON clasificacion.sapRolAfiliado=rolAfiliado.roaid 
		AND clasificacion.solFechaRadicacion IS NOT NULL
		AND clasificacion.roaTipoAfiliado=rolAfiliado.roaTipoAfiliado 
		WHERE beneficiario.benPersona NOT IN (	    SELECT perId 
													  FROM Beneficiario 
												INNER JOIN Persona ON Perid = benPersona 
													 WHERE benFechaAfiliacion < @fechaInicio
											  )
	 --  WHERE personaAfiliadaInfo.perNumeroIdentificacion = '91736'

ORDER BY 3
		
END