/****** Object:  StoredProcedure [odoo].[consultasTerceros]    Script Date: 8/06/2021 3:14:39 p. m. ******/

CREATE PROCEDURE [odoo].[consultasTerceros] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

INSERT INTO [odoo].[terceros] (tipo_identificacion, numero_identificacion, razon_social, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, direccion_fisica, pais,
	departamento, municipio, codigo_municipio, telefono_fijo, telefono_celular, fecha_nacimiento, email, fecha_contable, tipo_plantilla)

SELECT  [terceros].[Tipo de identificación del tercero], [terceros].[Número de identificación del tercero], [terceros].[Nombre completo del tercero], [terceros].[Primer nombre del tercero], [terceros].[Segundo nombre del tercero], [terceros].[Primer apellido del tercero], 
		[terceros].[Segundo apellido del tercero], [terceros].[Dirección del tercero], [terceros].[País del tercero], [terceros].[Departamento del tercero], [terceros].[Ciudad del tercero], [terceros].[Código del municipio ubicación oficina principal], [terceros].[Teléfono del tercero], 
		[terceros].[Móvil del tercero], [terceros].[Fecha de nacimiento del tercero], [terceros].[Email del tercero], [terceros].[Fecha contable], [terceros].[Campo tipo plantilla]
	FROM (
		--SEGUN LA CONFIRMACION DE ERICA YA NO IRÍAN LOS EMPLEADORES Y LOS DE SUBSIDIO PORQUE AMBOS ENTRAN POR LOS APORTES
		--SE DEJAN COMENTARIADOS LOS QUERYS COMO REFERENCIA PARA OTRAS CONSULTAS
		--Empleadores
		------
 
		SELECT 
			isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del tercero], 
			cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [Número de identificación del tercero], 
			isnull(p.perRazonSocial,'')[Nombre completo del tercero],
			isnull(p.perPrimerNombre,'') [Primer nombre del tercero],
			isnull(p.perSegundoNombre,'') [Segundo nombre del tercero],
			isnull(p.perPrimerapellido,'') [Primer apellido del tercero],
			isnull(p.perSegundoapellido,'') [Segundo apellido del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when m.munNombre is null then '' else 'COLOMBIA' end [País del tercero],---EXISTE LA TABLA PAISES SE DEBE AVERIGUAR CON FUNCIONAL COMO SE HACE LA RELACIÓN
			isnull(d.depNombre,'') As [Departamento del tercero],
			isnull(m.munNombre,'') AS [Ciudad del tercero],
			isnull(m.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			(select pebfechabloque6 
			from AporteGeneral2 as apgx 
			inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
			inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			where apgx.apgId = apg.apgId
			and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			)[Fecha de conciliación],
			apgFechaProcesamiento [Fecha contable]---con funcional apgFechaRecaudo?
			,'CRE_TER' [Campo tipo plantilla]
			
			--
			FROM AporteGeneral2 as apg (nolock) -- 4301 registros
			left join aportedetallado2 apd on apd.apdaportegeneral=apg.apgid
			left join Persona as p (nolock) on apg.apgPersona = p.perId
			left JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			left join Afiliado as af (nolock) on af.afiPersona = p.perId
			left join RolAfiliado as rolaf (nolock) on rolaf.roaAfiliado = af.afiId
			left join Ubicacion as u (nolock) on p.perUbicacionPrincipal = u.ubiId
			left join Municipio as m (nolock) on u.ubiMunicipio = m.munId
			left join Departamento as d (nolock) on m.munDepartamento = d.depId
			--LEFT JOIN PAIS AS PA (NOLOCK) ON PA.paiId=?¡?¡¡??¡??¡
			where apg.apgTipoSolicitante <> 'EMPLEADOR' 
			and apg.apgEstadoRegistroAporteAportante='REGISTRADO'	   
			and cast(apg.apgfechaprocesamiento as date)=@fecha_procesamiento--'2021-02-10' 

		UNION ALL

		SELECT DISTINCT
			isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del tercero], 
			cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [Número de identificación del tercero], 
			isnull(p.perRazonSocial,'')[Nombre completo del tercero],
			isnull(p.perPrimerNombre,'') [Primer nombre del tercero],
			isnull(p.perSegundoNombre,'') [Segundo nombre del tercero],
			isnull(p.perPrimerapellido,'') [Primer apellido del tercero],
			isnull(p.perSegundoapellido,'') [Segundo apellido del tercero],
			isnull(u2.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when me.munNombre is null then '' else 'COLOMBIA' end [País del tercero],---EXISTE LA TABLA PAISES SE DEBE AVERIGUAR CON FUNCIONAL COMO SE HACE LA RELACIÓN
			isnull(de.depNombre,'') As [Departamento del tercero],
			isnull(me.munNombre,'') AS [Ciudad del tercero],
			isnull(me.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u2.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u2.ubiTelefonoCelular,'') as [Móvil del tercero],
			Cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u2.ubiEmail,'') [Email del tercero],
			(select pebfechabloque6 

			FROM AporteGeneral2 as apgx 
			inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
			inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			where apgx.apgId = apg.apgId
			and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			)[Fecha de conciliación],
			apgFechaProcesamiento [Fecha contable]---con funcional apgFechaRecaudo?
			,'CRE_TER' [Campo tipo plantilla]
				from AporteGeneral2 as apg (nolock)
				left join aportedetallado apd on apd.apdaportegeneral=apg.apgid
				left join Empresa as emp  (nolock) on apg.apgEmpresa = emp.empId
				left join Empleador as empl (nolock) on empl.empEmpresa = emp.empId
				left join Persona as p (nolock) on emp.empPersona = p.perId
				left JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
				--left join CodigoCIIU as c (nolock) on emp.empCodigoCIIU = c.ciiId
				LEFT JOIN Ubicacionempresa ue (NOLOCK) ON emp.empid = ue.ubeempresa and ue.ubetipoubicacion='UBICACION_PRINCIPAL'
				left join ubicacion u2 on ue.ubeUbicacion=u2.ubiid
				LEFT JOIN Municipio me (NOLOCK) ON u2.ubiMunicipio =me.munId
				LEFT JOIN departamento de  (NOLOCK) ON me.mundepartamento = de.depid
				where apg.apgTipoSolicitante = 'EMPLEADOR' 
			and apg.apgEstadoRegistroAporteAportante='REGISTRADO'
			and cast(apg.apgfechaprocesamiento as date)=@fecha_procesamiento--'2021-02-10' 

		UNION ALL

			----####################################
			---DE FOVIS
			--1- ASIGNACION
		SELECT DISTINCT
			isnull(p.perTipoIdentificacion,'')  as [Tipo de identificación del tercero]
			,isnull(p.perNumeroIdentificacion,'')  as [Número de identificación del tercero]
			,isnull(p.perRazonSocial,'')  as [Nombre completo del tercero]
			,isnull(p.perPrimerNombre,'') as [Primer nombre del tercero]
			,isnull(p.perSegundoNombre,'') as [Segundo nombre del tercero]
			,isnull(p.perPrimerApellido,'') as [Primer apellido del tercero]
			,isnull(p.perSegundoApellido,'') as [Segundo apellido del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when mun.munNombre is null then '' else 'COLOMBIA' end [País del tercero],
			isnull(dep.depnombre ,'') As [Departamento del tercero],
			isnull(mun.munNombre,'') AS [Ciudad del tercero],
			isnull(mun.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			--(select pebfechabloque6 
			--from AporteGeneral as apgx 
			-- inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId 
			--inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			--inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			--where apgx.apgId = apg.apgId
			--and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			--)
			null
			[Fecha de conciliación],
			cast(solfecharadicacion as date) [Fecha contable],
			'CRE_TER' [Campo tipo plantilla]

			FROM postulacionfovis pf
			left join jefehogar j on j.jehId=pofJefeHogar
			left join afiliado a on a.afiId=j.jehAfiliado
			LEFT join persona p on a.afiPersona=p.perId
			LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
			LEFT join municipio mun on mun.munid=u.ubiMunicipio
			LEFT join departamento dep on dep.depid=mun.munDepartamento 
			INNER join [dbo].[solicitudasignacion] sa WITH(NOLOCK) on sa.safId=pf.pofSolicitudAsignacion
			left join solicitud s on sa.safsolicitudglobal=s.solid
			where S.SOLRESULTADOPROCESO='APROBADA'----POR VALIDAR
			AND cast(solfecharadicacion as date) =@fecha_procesamiento

		UNION ALL

			------FOVIs Desembolso / Pago del subsidio de vivienda
			--declare @fecha_procesamiento date='2021-05-06'; 
			---Información del Beneficiario DEL SUBSIDIO
		SELECT DISTINCT
			isnull(p.perTipoIdentificacion,'')  as [Tipo de identificación del tercero]
			,isnull(p.perNumeroIdentificacion,'')  as [Número de identificación del tercero]
			,isnull(p.perRazonSocial,'')  as [Nombre completo del tercero]
			,isnull(p.perPrimerNombre,'') as [Primer nombre del tercero]
			,isnull(p.perSegundoNombre,'') as [Segundo nombre del tercero]
			,isnull(p.perPrimerApellido,'') as [Primer apellido del tercero]
			,isnull(p.perSegundoApellido,'') as [Segundo apellido del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when mun.munNombre is null then '' else 'COLOMBIA' end [País del tercero],
			isnull(dep.depnombre ,'') As [Departamento del tercero],
			isnull(mun.munNombre,'') AS [Ciudad del tercero],
			isnull(mun.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			--(select pebfechabloque6 
			-- from AporteGeneral as apgx 
			-- inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId 
			--inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			--inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			--where apgx.apgId = apg.apgId
			--and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			--)
			null [Fecha de conciliación],
			cast(solfecharadicacion as date) [Fecha contable],
			'CRE_TER' [Campo tipo plantilla]

			FROM postulacionfovis pf
			left join jefehogar j on j.jehId=pofJefeHogar
			left join afiliado a on a.afiId=j.jehAfiliado
			LEFT join persona p on a.afiPersona=p.perId
			LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
			LEFT join municipio mun on mun.munid=u.ubiMunicipio
			LEFT join departamento dep on dep.depid=mun.munDepartamento 
			INNER join [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK) on sa.[sldPostulacionFOVIS]=pf.pofid
			left join legalizaciondesembolso WITH(NOLOCK) on sa.[sldLegalizacionDesembolso]=lgdid
			left join solicitud s on sa.sldsolicitudglobal=s.solid
			where S.SOLRESULTADOPROCESO='APROBADA'--'CERRADA'
			AND cast(solfecharadicacion as date) =@fecha_procesamiento

		UNION ALL

			------FOVIs Desembolso / Pago del subsidio de vivienda
			---Registro del oferente
		SELECT DISTINCT
			isnull(p.perTipoIdentificacion,'')  as [Tipo de identificación del tercero]
			,isnull(p.perNumeroIdentificacion,'')  as [Número de identificación del tercero]
			,isnull(p.perRazonSocial,'')  as [Nombre completo del tercero]
			,isnull(p.perPrimerNombre,'') as [Primer nombre del tercero]
			,isnull(p.perSegundoNombre,'') as [Segundo nombre del tercero]
			,isnull(p.perPrimerApellido,'') as [Primer apellido del tercero]
			,isnull(p.perSegundoApellido,'') as [Segundo apellido del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when mun.munNombre is null then '' else 'COLOMBIA' end [País del tercero],
			isnull(dep.depnombre ,'') As [Departamento del tercero],
			isnull(mun.munNombre,'') AS [Ciudad del tercero],
			isnull(mun.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			--(select pebfechabloque6 
			-- from AporteGeneral as apgx 
			-- inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId 
			--inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			--inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			--where apgx.apgId = apg.apgId
			--and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			--)
			null[Fecha de conciliación],
			cast(solfecharadicacion as date) [Fecha contable],
			'CRE_TER' [Campo tipo plantilla]
			
			FROM postulacionfovis pf
			inner join oferente o on pf.pofOferente=o.ofeId
			LEFT join persona p on o.ofePersona=p.perId
			LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
			LEFT join municipio mun on mun.munid=u.ubiMunicipio
			LEFT join departamento dep on dep.depid=mun.munDepartamento 
			INNER join [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK) on sa.[sldPostulacionFOVIS]=pf.pofid
			left join legalizaciondesembolso WITH(NOLOCK) on sa.[sldLegalizacionDesembolso]=lgdid
			left join solicitud s on sa.sldsolicitudglobal=s.solid
			where S.SOLRESULTADOPROCESO='APROBADA'--'CERRADA'
			AND cast(solfecharadicacion as date) =@fecha_procesamiento

		UNION ALL

			-- FOVIS NOVEDADES
		SELECT 
			isnull(p.perTipoIdentificacion,'')  as [Tipo de identificación del tercero]
			,isnull(p.perNumeroIdentificacion,'')  as [Número de identificación del tercero]
			,isnull(p.perRazonSocial,'')  as [Nombre completo del tercero]
			,isnull(p.perPrimerNombre,'') as [Primer nombre del tercero]
			,isnull(p.perSegundoNombre,'') as [Segundo nombre del tercero]
			,isnull(p.perPrimerApellido,'') as [Primer apellido del tercero]
			,isnull(p.perSegundoApellido,'') as [Segundo apellido del tercero],
			isnull(u.ubiDireccionFisica,'')  AS [Dirección del tercero],
			case when mun.munNombre is null then '' else 'COLOMBIA' end [País del tercero],
			isnull(dep.depnombre ,'') As [Departamento del tercero],
			isnull(mun.munNombre,'') AS [Ciudad del tercero],
			isnull(mun.munCodigo,'') AS [Código del municipio ubicación oficina principal],
			isnull(u.ubiTelefonoFijo,'') as [Teléfono del tercero],
			isnull(u.ubiTelefonoCelular,'') as [Móvil del tercero],
			cast(pd.pedFechaNacimiento as date) [Fecha de nacimiento del tercero],
			isnull(u.ubiEmail,'') [Email del tercero],
			--(select pebfechabloque6 
			-- from AporteGeneral as apgx 
			-- inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId 
			--inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			--inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			--where apgx.apgId = apg.apgId
			--and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
			--)
			null [Fecha de conciliación],
			cast(solfecharadicacion as date) [Fecha contable],
			'CRE_TER' [Campo tipo plantilla]

			FROM
			[dbo].[SolicitudNovedadPersonaFovis]
			inner join persona p on p.perid =[spfPersona]
			inner join [SolicitudNovedadFovis] on snfId=[spfSolicitudNovedadFovis]
			inner join solicitud s on snfSolicitudGlobal=s.solid
			--
			LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
			LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
			LEFT join municipio mun on mun.munid=u.ubiMunicipio
			LEFT join departamento dep on dep.depid=mun.munDepartamento 
			--
			where s.soltipotransaccion in('RENUNCIO_SUBISIDIO_ASIGNADO','RESTITUCION_SUBSIDIO_INCUMPLIMIENTO')
			and S.SOLRESULTADOPROCESO='APROBADA'--'CERRADA'
			and cast(solfecharadicacion as date) =@fecha_procesamiento
	)AS terceros


	--WHERE 
		--terceros.[Fecha contable] =	CONVERT(VARCHAR(10), GETDATE(), 103) 
END 
