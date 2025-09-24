/****** Object:  StoredProcedure [odoo].[ConsultaNovedadesEmpresasPersonas]    Script Date: 8/06/2021 3:16:04 p. m. ******/

CREATE PROCEDURE [odoo].[ConsultaNovedadesEmpresasPersonas](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[novedades_empresas_personas] (tipo_identificacion_tercero, numero_identificacion_tercero, direccion_tercero,
		pais_tercero, departamento_tercero, ciudad_tercero, telefono_tercero, movil_tercero, email_tercero, fecha_contable, tipo_plantilla)

		SELECT DISTINCT
			isnull(p.perTipoIdentificacion,'')  as [Tipo de identificación del tercero],
			isnull(p.perNumeroIdentificacion,'')  as [Número de identificación del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			(SELECT cast(paidescripcion AS varchar(10)) FROM pais WHERE paiID=pd.pedPaisResidencia) AS [País del tercero],
			isnull(dep.depnombre ,'') As [Departamento del tercero],
			isnull(mun.munNombre,'') AS [Ciudad del tercero],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			cast(solfecharadicacion as varchar(10)) [Fecha contable],
			'ACT_TER' [Campo tipo plantilla]
			FROM 
			[dbo].[SolicitudNovedadPersona]
			inner join persona p on p.perid =[snpPersona]
			inner join [SolicitudNovedad] on snoId=[snpSolicitudNovedad]
			inner join solicitud s on snoSolicitudGlobal=s.solid
			--
			LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
			LEFT join municipio mun on mun.munid=u.ubiMunicipio
			LEFT join departamento dep on dep.depid=mun.munDepartamento 
			where s.soltipotransaccion in(
			'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL'
			,'CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS'
			,'CAMBIO_NOMBRE_APELLIDOS_PERSONAS'
			,'CAMBIO_RAZON_SOCIAL_NOMBRE'
			)
			and cast(solfecharadicacion as date) =@fecha_procesamiento
END
