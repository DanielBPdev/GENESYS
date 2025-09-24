CREATE OR ALTER     PROCEDURE [dbo].[SP_ConsultarInfoBeneficiario]

	(@tipoIdentificacion VARCHAR (30),
	@numeroIdentificacion VARCHAR (30),
	@numeroIdentificacionAfiliado VARCHAR (30))
	AS
	Begin
	SET NOCOUNT ON 
	-- certificado escolaridad
	drop table if exists #tempCertificado

	SELECT top 1 ceb.* , p.* , pd.*, b.*, ga.*,bd.*, a.*
	into #tempCertificado
	FROM Persona p
	left join PersonaDetalle pd on pd.pedPersona = p.perid
	left join Beneficiario b  on b.benPersona = p.perid
	left join BeneficiarioDetalle bd on bd.bedPersonaDetalle = pd.pedId
	LEFT JOIN CertificadoEscolarBeneficiario ceb WITH (NOLOCK) ON ceb.cebBeneficiarioDetalle = bd.bedId
	LEFT JOIN GradoAcademico ga WITH (NOLOCK) ON ga.graid = b.benGradoAcademico
	left join Afiliado a on a.afiId = b.benAfiliado
	left join Persona pa on pa.perId = a.afiPersona
	WHERE (
			(p.perNumeroIdentificacion = @numeroIdentificacion and p.perTipoIdentificacion = @tipoIdentificacion)
			OR 
			(pa.perNumeroIdentificacion = @numeroIdentificacionAfiliado and pa.perTipoIdentificacion = @tipoIdentificacion)
		  )
	order by cebFechaCreacion DESC

	declare @gradoAcademico varchar(100)
	SELECT @gradoAcademico = graNombre from #tempCertificado

---
		SELECT
			ben.benTipoBeneficiario AS tipoBeneficiario,
			p.perTipoIdentificacion AS tipoIdentifiacion,
			p.perNumeroIdentificacion AS numeroIdentifacion,
			p.perPrimerNombre AS primerNombre,
			p.perSegundoNombre AS segundoNombre,
			p.perPrimerApellido AS primerApellido,
			p.perSegundoApellido AS segundoApellido,
			p.perRazonSocial AS nombreCompleto,
			ped.pedFechaNacimiento AS fechaNacimiento,
			DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.getlocaldate()) AS edad,
			ped.pedFechaFallecido AS fechaFallecimiento,
			ped.pedEstadoCivil AS estadoCivil,
			ped.pedGenero AS genero,
			ubi.ubiDireccionFisica AS direccionRecidencia,
			mun.munCodigo AS codigoMunicipio,
			mun.munNombre AS nombreMunicipio,
			dep.depCodigo AS codigoDepartamento,
			dep.depNombre AS nombreDepartamento,
			ubi.ubiCodigoPostal AS codigoPostal,  	
			COALESCE(ubi.ubiIndicativoTelFijo, '') + COALESCE(ubiTelefonoFijo, '') AS indicativoYTelFijo,
			ubi.ubiTelefonoCelular AS celular,
			ubi.ubiEmail AS correoElectronico,
			ben.benEstadoBeneficiarioAfiliado AS estadoAfiliacion,
			--
			ben.benFechaRetiro AS fechaRetiro,
			ben.benMotivoDesafiliacion AS motivoDesafiliacion,
			(select MAX(cas.casFechaHoraCreacionRegistro)
				from cuentaadministradorsubsidio cas
				inner join detallesubsidioasignado dsa on cas.casid = dsa.dsacuentaadministradorsubsidio
				where dsa.dsaBeneficiarioDetalle = bed.bedId
				  and cas.casTipoTransaccionSubsidio = 'ABONO') as ultimpoPagoCuotaMonetaria, 
			CASE WHEN coi.coiInvalidez = 1 THEN 'SI' ELSE 'NO'  END AS condicionInvalidez,
			ped.pedEstudianteTrabajoDesarrolloHumano AS estudianteTrabajoDesarrolloHumano,
			--
			CASE WHEN ben.benGradoAcademico IS NULL THEN 'NO' ELSE 'SI' END AS debePresentarEscolaridad,
			(select max(cebFechaVencimiento) from #tempCertificado ) AS fechaFinVigencia,
			CASE WHEN t.cebFechaVencimiento < dbo.GetLocalDate() THEN 'Vencida'ELSE 'Vigente' END AS estadoCertificadoEscolaridad,
			@gradoAcademico AS gradoEscolar,
			t.cebFechaRecepcion AS fechaRecepcion,
			--
			per.perTipoIdentificacion AS tipoIdentificacionAfiliado,
			per.perNumeroIdentificacion AS numeroIdentificacionAfiliado,
			per.perPrimerNombre AS primerNombreAfiliado,
			per.perSegundoNombre AS segundoNombreAfiliado,
			per.perPrimerApellido AS primerApellidoAfiliado,
			per.perSegundoApellido AS segundoApellidoAfiliado,
			case when ben.benestadobeneficiarioAfiliado = 'ACTIVO'
			then( select top 1 ctaCategoria  from categoriaafiliado where ctaAfiliado =@numeroIdentificacionAfiliado  order by ctaFechaCambioCategoria desc )
			else
			(select top 1 ctacategoria from categoriabeneficiario 
			inner join categoriaafiliado on  ctaId =ctbCategoriaAfiliado 
			where bed.bedid = ctbbeneficiariodetalle
			order by ctbid desc
			)
			end as categoria
			
		FROM Persona p  WITH (NOLOCK)
		LEFT join Beneficiario  ben with (NOLOCK) ON p.perId = ben.benPersona 
		LEFT JOIN BeneficiarioDetalle bed WITH (NOLOCK) ON bed.bedId = ben.benBeneficiarioDetalle
		LEFT JOIN PersonaDetalle ped WITH (NOLOCK) ON ped.pedPersona = p.perId
		LEFT JOIN GradoAcademico gra WITH (NOLOCK) ON graId = ped.pedGradoAcademico
		LEFT JOIN Ubicacion ubi WITH (NOLOCK) ON ubi.ubiId = p.perUbicacionPrincipal
		LEFT JOIN Municipio mun WITH (NOLOCK) ON mun.munId = ubi.ubiMunicipio
		LEFT JOIN Departamento dep WITH (NOLOCK) ON dep.depId = mun.munDepartamento
		LEFT JOIN #tempCertificado t WITH (NOLOCK) ON t.cebBeneficiarioDetalle = bed.bedId
		LEFT JOIN Afiliado afi WITH (NOLOCK) on afi.afiId = ben.benAfiliado
		LEFT JOIN Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		LEFT JOIN CondicionInvalidez coi WITH (NOLOCK) ON coi.coiPersona = p.perId
		WHERE (
				(p.perTipoIdentificacion = @tipoIdentificacion and p.perNumeroIdentificacion = @numeroIdentificacion)
				or 
				(per.perTipoIdentificacion = @tipoIdentificacion and per.perNumeroIdentificacion = @numeroIdentificacionAfiliado)
				)
	END;