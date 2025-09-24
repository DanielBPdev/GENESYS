-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
create PROCEDURE USP_GET_HistoricoMaestroAfiliados
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	-- revisar tabla cartera no trae datos

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoMaestroAfiliados(
			hmaFechaHistorico,
			hmaTipoRegistro,
			hmaTipoIdentificacionAfiliado,
			hmaNumeroIdentificacionAfiliado,
			hmaTipoIdentificacionPoblacion,
			hmaNumeroIdentificacionPoblacion,
			hmaCodigoGenero,
			hmaFechaNacimiento,
			hmaPrimerApellido,
			hmaSegundoApellido,
			hmaPrimerNombre,
			hmaSegundoNombre,
			hmaDepartamentoResidencia,
			hmaMunicipioResidencia,
			hmaFechaAfiliacion,
			hmaCodigoCaja,
			hmaCodTipoAfiliado,
			hmaTipoIdentificacionAportante,
			hmaNumeroIdentificacionAportante,
			hmaDigitoVerficacionAportante,
			hmaRazonSocialAportante,
			hmaFechaVinculacion,
			hmaDepartamentroLaboral,
			hmaMunicipioLaboral,
			hmaAlDia,
			hmaCodigoTipoMiembro,
			hmaCondicionBeneficiario, -- nuevo campo
			hmaTipoRelacionConAfiliado,
			hmaFechaInicialReporte,
			hmaFechaFinalReporte)
		SELECT @fechaFin,
			'2' AS tipoRegistro,
		CASE perAfi.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	        WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	        WHEN 'PASAPORTE' THEN 'PA' 
	        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
		END tipoIdentificacionAfiliado,
		perAfi.perNumeroIdentificacion numeroIdentificacionAfiliado,
		null as tipoIdentificacionPoblacion,		
		null as numeroIdentificacionPoblacion,
		CASE pedAfi.pedGenero WHEN 'MASCULINO' THEN 'M' WHEN 'FEMENINO' THEN 'F' END
		codigoGenero,
		pedAfi.pedFechaNacimiento fechaNacimiento,
		substring(perAfi.perPrimerApellido,0,61)  primerApellido,
		substring(perAfi.persegundoApellido,0,61)  segundoApellido,
		substring(perAfi.perPrimerNombre,0,61)  primerNombre,
		substring(perAfi.perSegundoNombre,0,61)  segundoNombre,
		depAfi.depCodigo departamentoResidencia,
		substring(munAfi.munCodigo,3,6) MunicipioResidencia,
		roa.roaFechaAfiliacion fechaAfiliacion,
		cns.cnsValor AS codigoCaja,
		CLASIFICACION.solclasificacion codTipoAfiliado,
		CASE (CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perTipoIdentificacion ELSE perAfi.perTipoIdentificacion END)
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	        WHEN 'PASAPORTE' THEN 'PA'
	        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'NIT' THEN 'NI'
		END tipoIdentificacionAportante,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perNumeroIdentificacion ELSE perAfi.perNumeroIdentificacion END
			AS numeroIdentificacionAportante,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perDigitoVerificacion ELSE perAfi.perDigitoVerificacion END
			AS digitoVerficacionAportante,
		substring((CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perRazonSocial ELSE perAfi.perRazonSocial END),0,151)
			AS razonSocialAportante,
		roa.roaFechaIngreso fechaVinculacion,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN depApo.depCodigo ELSE depAfi.depCodigo END
			AS departamentroLaboral,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN substring(munApo.munCodigo,3,6) ELSE substring(munAfi.munCodigo,3,6) END
			AS municipioLaboral,
		CASE isnull(auxCartera.carEstadoCartera,'AL_DIA') WHEN 'AL_DIA' THEN '1' WHEN 'MOROSO' THEN '2' END alDia,
		'1'codigoTipoMiembro,	
		null CodigoCondicionBeneficiario,
		null tipoRelacionConAfiliado,
		@fechaInicio,
		@fechaFin
		FROM Afiliado afi 
		INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
		INNER JOIN PersonaDetalle pedAfi ON pedAfi.pedPersona = perAfi.perId
		INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
		
		LEFT JOIN Ubicacion ubiAfi ON ubiAfi.ubiId = perAfi.perUbicacionPrincipal
		LEFT JOIN Municipio munAfi ON munAfi.munId = ubiAfi.ubiMunicipio
		LEFT JOIN Departamento depAfi ON depAfi.depId = munAfi.munDepartamento
		
		left join empleador empl on empl.empid=roa.roaempleador
		LEFT JOIN Empresa emp ON emp.empId=empl.empempresa
		LEFT JOIN Persona perApo ON perApo.perId = emp.empPersona
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubiApo ON ubiApo.ubiId = ube.ubeUbicacion
		LEFT JOIN Municipio munApo ON munApo.munId = ubiApo.ubiMunicipio
		LEFT JOIN Departamento depApo ON depApo.depId = munApo.munDepartamento

		LEFT JOIN (SELECT carPersona,
					carEstadoCartera 
					FROM Cartera car 
					WHERE carId IN (SELECT MAX(carId) 
						FROM Cartera 
						WHERE carPersona = carPersona 
						AND carEstadoOperacion = 'VIGENTE' 
						AND carDeudaPresunta>0
						GROUP BY carPersona)) auxCartera ON auxCartera.carPersona = emp.emppersona
		LEFT JOIN Constante cns ON cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO'
--pARA EL SOL CLASIFICACION
LEFT JOIN (select SAPX.SAPROLAFILIADO--, S.solclasificacion,ROAX.roaTipoAfiliado
			,CASE WHEN  ROAX.roaTipoAfiliado= 'TRABAJADOR_DEPENDIENTE' THEN '1'
				 WHEN ROAX.roaTipoAfiliado= 'TRABAJADOR_INDEPENDIENTE' THEN '2'
				 WHEN ROAX.roaTipoAfiliado='PENSIONADO' and S.solclasificacion<>'FIDELIDAD_25_ANIOS' THEN '3'
				 WHEN ROAX.roaTipoAfiliado='PENSIONADO' AND S.solclasificacion='FIDELIDAD_25_ANIOS' THEN '6'
			end as solclasificacion
				from solicitud S
				inner join solicitudafiliacionpersona sapx on sapx.sapSolicitudGlobal =s.solid
				INNER JOIN ROLAFILIADO ROAX ON ROAX.ROAID=sapRolAfiliado
				where s.soltipotransaccion like 'AFILIAC%'
	)CLASIFICACION ON CLASIFICACION.sapRolAfiliado=ROA.ROAID
		WHERE 
		(perAfi.perId IN (SELECT DISTINCT eaePersona 
						FROM EstadoAfiliacionPersonaEmpresa 
						WHERE eaeFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						  AND eaeEstadoAfiliacion = 'ACTIVO')
		  OR perAfi.perId IN (SELECT eapPersona FROM EstadoAfiliacionPersonaPensionado
						WHERE eapFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						 )
		  OR perAfi.perId IN (SELECT eaiPersona FROM EstadoAfiliacionPersonaIndependiente
		  				WHERE --eaiClaseIndependiente = 'VOLUNTARIO'
		  				  eaiEstadoAfiliacion = 'ACTIVO'
		  				  AND eaiFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						  ))
		-- AND perBen.perTipoIdentificacion IN ('TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  AND perAfi.perTipoIdentificacion IN ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  --AND perApo.perTipoIdentificacion IN ('TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  AND pedAfi.pedGenero IN ('MASCULINO','FEMENINO')
          --AND pedBen.pedGenero IN ('MASCULINO','FEMENINO')
		  --and perAfi.perNumeroIdentificacion = '6001123'

union all
--Beneficiarios
SELECT @fechaFin,
			'2' AS tipoRegistro,
		CASE perAfi.perTipoIdentificacion
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	        WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	        WHEN 'PASAPORTE' THEN 'PA' 
	        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
		END tipoIdentificacionAfiliado,
		perAfi.perNumeroIdentificacion numeroIdentificacionAfiliado,
		CASE perBen.perTipoIdentificacion
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	        WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	        WHEN 'PASAPORTE' THEN 'PA' 
	        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
		END tipoIdentificacionPoblacion,		
		perBen.perNumeroIdentificacion numeroIdentificacionPoblacion,
		CASE pedBen.pedGenero WHEN 'MASCULINO' THEN 'M' WHEN 'FEMENINO' THEN 'F'
		END codigoGenero,
		pedBen.pedFechaNacimiento fechaNacimiento,
		perBen.perPrimerApellido primerApellido,
		perBen.persegundoApellido segundoApellido,
		perBen.perPrimerNombre primerNombre,
		perBen.perSegundoNombre segundoNombre,
		isnull((select top 1 depcodigo
		from [dbo].[GrupoFamiliar]
		inner join ubicacion on grfubicacion= ubiId
		inner join municipio on ubimunicipio= munid
		inner join departamento on mundepartamento=depid
		where grfid=[benGrupoFamiliar]),'')  departamentoResidencia,
		isnull((select top 1 substring(muncodigo,3,6)
		from [dbo].[GrupoFamiliar]
		inner join ubicacion on grfubicacion= ubiId
		inner join municipio on ubimunicipio= munid
		inner join departamento on mundepartamento=depid
		where grfid=[benGrupoFamiliar]),'')  MunicipioResidencia,
		ben.benFechaAfiliacion fechaAfiliacion,
		cns.cnsValor AS codigoCaja, 																	
		null
		codTipoAfiliado,
		CASE (CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perTipoIdentificacion ELSE perAfi.perTipoIdentificacion END)
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'       
	        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	        WHEN 'PASAPORTE' THEN 'PA' 
	        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'  
			WHEN 'NIT' THEN 'NI'  
		END tipoIdentificacionAportante,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perNumeroIdentificacion ELSE perAfi.perNumeroIdentificacion END
			AS numeroIdentificacionAportante,
		CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perDigitoVerificacion ELSE perAfi.perDigitoVerificacion END
			AS digitoVerficacionAportante,
		substring((CASE WHEN CLASIFICACION.solclasificacion = '1' THEN perApo.perRazonSocial ELSE perAfi.perRazonSocial END),0,151)
			AS razonSocialAportante,
		null fechaVinculacion,
		null departamentroLaboral,
		null municipioLaboral,
		null alDia,
		CASE WHEN ben.benTipoBeneficiario IN ('HIJO_BIOLOGICO', 'HIJO_ADOPTIVO','HIJASTRO','HERMANO_HUERFANO_DE_PADRES','BENEFICIARIO_EN_CUSTODIA','PADRE','MADRE') THEN '2'
			WHEN ben.benTipoBeneficiario IN ('CONYUGE') THEN '3'
		END codigoTipoMiembro,	
case when coi.coiInvalidez=1 then 'D' ELSE 
   (
	select top 1 CASE WHEN cebFechaVencimiento>=@FECHAFIN THEN 'E' ELSE NULL end
	from beneficiario benx
	 inner join beneficiariodetalle bdx on benx.benBeneficiarioDetalle=bedId-- and bdx.bedid=bd.bedid
	inner join CertificadoEscolarBeneficiario c on c.cebBeneficiarioDetalle=bdx.bedid
	where benx.bentipobeneficiario not in( 'CONYUGE','PADRE','MADRE')

	and benx.benId=ben.benid
	--order by cebid desc
) end CodigoCondicionBeneficiario,
		CASE WHEN ben.benTipoBeneficiario = 'CONYUGE' THEN '1'
			 WHEN ben.benTipoBeneficiario IN ('HIJO_BIOLOGICO','HIJO_ADOPTIVO','HIJASTRO') THEN '2'
			 WHEN ben.benTipoBeneficiario IN ('PADRE','MADRE') THEN '3'
			 WHEN ben.benTipoBeneficiario IN ('HERMANO_HUERFANO_DE_PADRES') THEN '4'
		END tipoRelacionConAfiliado,
		@fechaInicio,
		@fechaFin
		FROM Afiliado afi 
		INNER JOIN Persona perAfi ON perAfi.perId = afi.afiPersona
		INNER JOIN PersonaDetalle pedAfi ON pedAfi.pedPersona = perAfi.perId
		INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
		inner JOIN Beneficiario ben ON ben.benAfiliado = afi.afiId
		inner JOIN BeneficiarioDetalle bed ON bed.bedId = ben.benBeneficiarioDetalle
		LEFT JOIN CertificadoEscolarBeneficiario ceb ON ceb.cebBeneficiarioDetalle = bed.bedId
		LEFT JOIN Persona perBen ON perBen.perId = ben.benPersona
		LEFT JOIN PersonaDetalle pedBen ON pedBen.pedPersona = perBen.perId
		LEFT JOIN Ubicacion ubiAfi ON ubiAfi.ubiId = perAfi.perUbicacionPrincipal
		--LEFT JOIN Municipio munAfi ON munAfi.munId = ubiAfi.ubiMunicipio
		--LEFT JOIN Departamento depAfi ON depAfi.depId = munAfi.munDepartamento
		--LEFT JOIN Ubicacion ubiBen ON ubiBen.ubiId = perBen.perUbicacionPrincipal
		--LEFT JOIN Municipio munBen ON munBen.munId = ubiBen.ubiMunicipio
		--LEFT JOIN Departamento depBen ON depBen.depId = munBen.munDepartamento
		left join empleador empl on empl.empid=roa.roaempleador
		LEFT JOIN Empresa emp ON emp.empId=empl.empempresa
		LEFT JOIN Persona perApo ON perApo.perId = emp.emppersona
		--LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		--LEFT JOIN Ubicacion ubiApo ON ubiApo.ubiId = ube.ubeUbicacion
		--LEFT JOIN Municipio munApo ON munApo.munId = ubiApo.ubiMunicipio
		--LEFT JOIN Departamento depApo ON depApo.depId = munApo.munDepartamento
		LEFT JOIN CondicionInvalidez coi ON coi.coiPersona = perBen.perId
		LEFT JOIN (SELECT carPersona,
					carEstadoCartera 
					FROM Cartera car 
					WHERE carId IN (SELECT MAX(carId) 
						FROM Cartera 
						WHERE carPersona = carPersona 
						AND carEstadoOperacion = 'VIGENTE' 
						AND carDeudaPresunta>0
						GROUP BY carPersona)) auxCartera ON auxCartera.carPersona = emp.emppersona
		LEFT JOIN Constante cns ON cns.cnsNombre = 'CAJA_COMPENSACION_CODIGO'
		LEFT JOIN (select SAPX.SAPROLAFILIADO--, S.solclasificacion,ROAX.roaTipoAfiliado
			,CASE WHEN  ROAX.roaTipoAfiliado= 'TRABAJADOR_DEPENDIENTE' THEN '1'
				 WHEN ROAX.roaTipoAfiliado= 'TRABAJADOR_INDEPENDIENTE' THEN '2'
				 WHEN ROAX.roaTipoAfiliado='PENSIONADO' and S.solclasificacion<>'FIDELIDAD_25_ANIOS' THEN '3'
				 WHEN ROAX.roaTipoAfiliado='PENSIONADO' AND S.solclasificacion='FIDELIDAD_25_ANIOS' THEN '6'
			end as solclasificacion
				from solicitud S
				inner join solicitudafiliacionpersona sapx on sapx.sapSolicitudGlobal =s.solid
				INNER JOIN ROLAFILIADO ROAX ON ROAX.ROAID=sapRolAfiliado
				where s.soltipotransaccion like 'AFILIAC%'
		)CLASIFICACION ON CLASIFICACION.sapRolAfiliado=roa.ROAID
		WHERE 
		-- perAfi.perNumeroIdentificacion='6001123' and
		(perAfi.perId IN (SELECT DISTINCT eaePersona 
						FROM EstadoAfiliacionPersonaEmpresa 
						WHERE eaeFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						  AND eaeEstadoAfiliacion = 'ACTIVO')
		  OR perAfi.perId IN (SELECT eapPersona FROM EstadoAfiliacionPersonaPensionado
						WHERE eapFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						 )
		  OR perAfi.perId IN (SELECT eaiPersona FROM EstadoAfiliacionPersonaIndependiente
		  				WHERE --eaiClaseIndependiente = 'VOLUNTARIO'
		  				  eaiEstadoAfiliacion = 'ACTIVO'
		  				  AND eaiFechaCambioEstado BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
						  ))
		  AND perBen.perTipoIdentificacion IN ('TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  AND perAfi.perTipoIdentificacion IN ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  --AND perApo.perTipoIdentificacion IN ('TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO')
		  AND pedAfi.pedGenero IN ('MASCULINO','FEMENINO')
          AND pedBen.pedGenero IN ('MASCULINO','FEMENINO')
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
