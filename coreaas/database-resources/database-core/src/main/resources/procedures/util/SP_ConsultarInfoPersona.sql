CREATE OR ALTER PROCEDURE [dbo].[SP_ConsultarInfoPersona]
	
	(@tipoIdentificacion VARCHAR (30),
	@numeroIdentificacion VARCHAR (30))
	AS
	begin
	SET NOCOUNT ON 
	
--sumatoria valor pendiente a descontar
	declare @descuento as table (valorDescontar numeric(18,5), origen varchar(max))
	insert into @descuento
	exec sp_execute_remote subsidioreferencedata, N'
	select  sum(smvValorPignorar) as valorADescontar from SubsidioMonetarioValorPignorado smv
		JOIN dbo.ArchivoEntidadDescuentoSubsidioPignorado ard on ard.ardId = smv.smvArchivoEntidadDescuentoSubsidioPignorado 
		WHERE (smv.smvResultado = ''NO_APLICADO'' OR smv.smvResultado IS NULL)
	AND smv.smvNumeroIdentificacionTrabajador=@numeroIdentificacionP
	AND smv.smvTipoIdentificacionTrabajador = @tipoIdentificacionP
	and ard.ardEstado = ''CARGADO''', N'@numeroIdentificacionP varchar(100), @tipoIdentificacionP varchar(100)',@numeroIdentificacionP=@numeroIdentificacion, @tipoIdentificacionP=@tipoIdentificacion
--aportes y salario por empresa
	drop table if exists #tempAportes
	select apgempresa,apdpersona,apgperiodoaporte,apdsalariobasico,apdtarifa, ROW_NUMBER() over (partition by apgempresa order by apgperiodoaporte desc) as id
	into #tempAportes
	from aportegeneral
	inner join aportedetallado apd on apdaportegeneral = apgid
	inner join persona on apdpersona = perid
	where pernumeroidentificacion = @numeroIdentificacion AND perTipoIdentificacion = @tipoIdentificacion 
	order by apgperiodoaporte desc

--nombre y codigo entidad descuento del ultimo descuento aplicado
	declare @codigoDescuento varchar(35)
	declare @nombreDescuento varchar(100)
	select 
	top 1 @codigoDescuento=endcodigo,@nombreDescuento=endnombre
	from cuentaadministradorsubsidio
	inner join detallesubsidioasignado d on casid = dsacuentaadministradorsubsidio
	inner join descuentossubsidioasignado de on dsaid = desdetallesubsidioasignado
	inner join entidaddescuento on desentidaddescuento = endid
	inner join afiliado on afiid = casAfiliadoPrincipal
	inner join persona on perid = afipersona
	where  perNumeroIdentificacion=@numeroIdentificacion
	and perTipoIdentificacion=@tipoIdentificacion
	order by desid desc


--catetoria y clasificacion del afiliado

	declare @categoria varchar(50)
	declare @clasificacion varchar(50)
	declare @estadoafiliacion varchar(30)
		select top 1 @categoria=ctacategoria,@clasificacion=ctaclasificacion,@estadoafiliacion=ctaestadoafiliacion
		from categoriaAfiliado
		inner join afiliado on afiid = ctaAfiliado
		inner join persona p on perid =afipersona
			WHERE
		p.perTipoIdentificacion = @tipoIdentificacion 
		and p.perNumeroIdentificacion = @numeroIdentificacion
		order by 1 desc

--determinar si es afiliado principal
	declare @afiliadoPpl varchar(3)
	select top 1  @afiliadoPpl= case when catafiliadoprincipal=1 then 'Si' else 'No' end
	from  categoria
	inner join afiliado on afiid = catidAfiliado
	inner join persona p on perid =afipersona
		WHERE
	p.perTipoIdentificacion = @tipoIdentificacion 
	and p.perNumeroIdentificacion = @numeroIdentificacion
	order by 1 desc

------ Temporal para aportes y empresa
	declare @nitEmpresaA VARCHAR(30)
	declare @nombreEmpresaA VARCHAR(30)
	declare @fechaIngresoA date
	declare @fechaRetiroA date
	drop table if exists #tempultima
	--ultimo aporte

	drop table if exists #tempEmpresa

	select roaId, pa.perid as idPersona,pa.perRazonSocial as afiliado, roaEstadoAfiliado, roaFechaIngreso, pe.perRazonSocial as empresa,
	roaFechaAfiliacion, roaFechaRetiro,
	pe.perNumeroIdentificacion, pe.perRazonSocial
	into #tempEmpresa
	from RolAfiliado
	inner join afiliado on afiid = roaafiliado
	inner join Empleador em on em.empId = roaEmpleador
	inner join Empresa emp on emp.empId = em.empEmpresa
	inner join Persona pe on pe.perId = emp.empPersona
	inner join persona pa on pa.perid = afipersona
	where pa.pernumeroidentificacion = @numeroIdentificacion AND pa.perTipoIdentificacion = @tipoIdentificacion 

	drop table if exists #tempUltimainactiva
	select top 1*
	into #tempUltimainactiva
	from #tempEmpresa
	where roaFechaRetiro between dbo.GetLocalDate() and DATEADD(month, -6,  dbo.GetLocalDate())
	and roaEstadoAfiliado ='INACTIVO' order by roaFechaRetiro desc

	drop table if exists #tempUltimaactiva
	select top 1*
	into #tempUltimaactiva
	from #tempEmpresa
	where roaEstadoAfiliado ='ACTIVO' order by roaFechaIngreso desc
	select 
	@nitEmpresaA = COALESCE((select perNumeroIdentificacion from #tempUltimainactiva), (select perNumeroIdentificacion from #tempUltimaactiva)),
    @nombreEmpresaA = COALESCE((select empresa from #tempUltimainactiva), (select empresa from #tempUltimaactiva)),
    @fechaIngresoA = COALESCE((select roaFechaIngreso from #tempUltimainactiva), (select roaFechaIngreso from #tempUltimaactiva)),
    @fechaRetiroA = COALESCE((select roaFechaRetiro from #tempUltimainactiva), (select roaFechaRetiro from #tempUltimaactiva))

	SELECT 
		rol.roaTipoAfiliado AS tipoAfiliado,
		rol.roaClaseIndependiente AS claseIndependiente,
		rol.roaClaseTrabajador AS claseTrabajador,
		 @afiliadoPpl as afiliadoPrincipal,
		p.perTipoIdentificacion AS tipoIdentificacion, 
		p.perNumeroIdentificacion AS numeroIdentificacion, 
		p.perRazonSocial AS nombreCompletoPersona,
		ped.pedFechaNacimiento AS fechaNacimiento,
		DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.getlocaldate()) AS edad,
		ped.pedFechaFallecido AS fechaFallecimiento,
		ped.pedEstadoCivil AS estadoCivil, 
		ped.pedGenero AS genero,
		--mejorar
		(SELECT COUNT(*)
		FROM Beneficiario benSub	
		WHERE benSub.benAfiliado = afi.afiId
		AND benSub.benTipoBeneficiario IN ('HIJO_BIOLOGICO', 'BENEFICIARIO_EN_CUSTODIA', 'HIJASTRO', 'HERMANO_HUERFANO')) AS cantidadHijos,
		CASE WHEN coi.coiInvalidez = 1 THEN 'SI' ELSE 'NO'  END AS condicionInvalidez,
		ubi.ubiDireccionFisica AS direccionRecidencia,
		ped.pedHabitaCasaPropia AS habitaCasaPropia, 
		mun.munCodigo AS codigoMunicipio,
		mun.munNombre AS nombreMunicipio,
		dep.depCodigo AS codigoDepartamento,
		dep.depNombre AS nombreDepartamento,
		ubi.ubiCodigoPostal AS codigoPostal,  
		COALESCE(ubi.ubiIndicativoTelFijo, '') + COALESCE(ubiTelefonoFijo, '') AS indicativoYTelFijo,
		ubi.ubiTelefonoCelular AS celular,
		ubi.ubiEmail AS correoElectronico,
		CASE WHEN ubi.ubiAutorizacionEnvioEmail = 1 THEN 'SI' ELSE 'NO'  END AS autorizacionEnvioEmail,
		CASE WHEN ped.pedAutorizaUsoDatosPersonales = 1 THEN 'SI' ELSE 'NO'  END AS autorizaUsoDatosPersonales,
		ccfCodigo AS codigoCCF,
		@categoria as categoria,
		@clasificacion as clasificacion	,
		@estadoafiliacion as estadoAfiliacion,
		rol.roaFechaAfiliacion AS fechaAfiliacionCCF,
		(select 
			max(dsaFechaHoraCreacion)
			from cuentaadministradorsubsidio
			inner join detallesubsidioasignado on casid = dsacuentaadministradorsubsidio
			inner join solicitudliquidacionsubsidio on slsid = casSolicitudLiquidacionSubsidio
			inner join afiliado on afiid = casAfiliadoPrincipal
			inner join persona on perid = afipersona
			where  perNumeroIdentificacion=@numeroIdentificacion
			and perTipoIdentificacion=@tipoIdentificacion
			and castipotransaccionsubsidio = 'ABONO') 
			as ultimoPagoCuotaMonetaria, 
		(select top 1 isnull(valorDescontar,0) from @descuento) as saldoDescuentoPorCuotaMonetaria,
		@codigoDescuento as codigoEntidadDescuento,
		@nombreDescuento as nombreEntidadDescuento,
		ped.pedFechaExpedicionDocumento as fechaExpedicionDocumento,
		ped.pedNivelEducativo as nivelEducativo,
		ga.graNombre as gradoAcademico,
		--
		pe.pertipoidentificacion as tipoIdentificacionEmpleador,
		pe.pernumeroidentificacion as numeroIdentificacionEmpleador,
		pe.perdigitoverificacion as digitoVerificacion,
		pe.perrazonsocial as nombreEmpleador,
		rol.roaSucursalEmpleador as sucursalEmpleador,
		sue.suenombre as nombreSucursalEmpleador,
		rol.roafechaingreso as fechaIngresoEmpresa,
		isnull(t.apdsalariobasico ,rol.roaValorSalarioMesadaIngresos) as salario,
		isnull(t.apdtarifa, rol.roaPorcentajePagoAportes) as porcentajeAporte, 
		rol.roacargo as cargo,
		rol.roafecharetiro as fechaRetiro,
		rol.roamotivodesafiliacion as motivoDesafiliacion,
		t.apgperiodoaporte as ultimoPeriodoPagoAportes,
		--
		@nitEmpresaA AS nitEmpresaAnterior,
		@nombreEmpresaA AS nombreEmpresaAnterior,
		@fechaIngresoA AS fechaIngresoEmpresaAnterior,
		@fechaRetiroA AS fechaRetiroEmpresaAnterior
	FROM Persona p with (nolock)
	LEFT JOIN PersonaDetalle ped WITH (NOLOCK) ON ped.pedPersona = p.perId
	LEFT JOIN Afiliado afi WITH (NOLOCK) ON afi.afiPersona = p.perId
	LEFT JOIN RolAfiliado rol WITH (NOLOCK) ON rol.roaAfiliado = afi.afiId
	LEFT JOIN sucursalempresa sue on sue.sueid = rol.roasucursalempleador
	LEFT JOIN Empleador em WITH (NOLOCK) ON em.empId = rol.roaEmpleador
	LEFT JOIN Empresa emp WITH (NOLOCK) ON emp.empId = em.empEmpresa
	LEFT JOIN #tempAportes t on t.apgempresa= emp.empid and t.id =1
	LEFT JOIN CajaCompensacion ccf WITH (NOLOCK) ON ccf.ccfId = emp.empUltimaCajaCompensacion
	LEFT JOIN Persona pe WITH (NOLOCK) ON pe.perId = emp.empPersona
	LEFT JOIN Beneficiario ben WITH (NOLOCK) ON ben.benAfiliado = afi.afiId
	LEFT JOIN CondicionInvalidez coi WITH (NOLOCK) ON coiPersona = p.perid
	LEFT JOIN Ubicacion ubi WITH (NOLOCK) ON ubi.ubiId = p.perUbicacionPrincipal
	LEFT JOIN Municipio mun WITH (NOLOCK) ON mun.munId = ubi.ubiMunicipio
	LEFT JOIN Departamento dep WITH (NOLOCK) ON dep.depid = mun.munDepartamento
	LEFT JOIN GradoAcademico ga WITH (NOLOCK) ON ga.graId = pedGradoAcademico
	WHERE
	p.perTipoIdentificacion = @tipoIdentificacion 
	and p.perNumeroIdentificacion = @numeroIdentificacion;
END;