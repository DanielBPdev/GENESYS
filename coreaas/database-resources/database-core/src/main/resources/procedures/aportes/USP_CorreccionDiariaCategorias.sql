CREATE OR ALTER procedure [dbo].[USP_CorreccionDiariaCategorias]
as
begin 

	set nocount on;

	--INACTIVOS CON CATEGORIA
	drop table if exists #UltimaFechaRetiro
	select *
	into #UltimaFechaRetiro
	from 
		(select *
		from
			(select 
				roa.roaAfiliado
				,roa.roaFechaRetiro
				,ROW_NUMBER() over (
					partition by roa.roaAfiliado
					order by roa.roaFechaRetiro desc) as fila
			from RolAfiliado as roa
			where roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') as x
		where
			(select COUNT(*) 
				from RolAfiliado r 
				where r.roaAfiliado= x.roaAfiliado
					and r.roaEstadoAfiliado = 'ACTIVO' 
					and r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE') = 0) as x
	where x.fila = 1


	delete cab
	from CategoriaBeneficiario as cab
		inner join CategoriaAfiliado as cta on cab.ctbCategoriaAfiliado = cta.ctaId
		inner join #UltimaFechaRetiro as ufr on ufr.roaAfiliado = cta.ctaAfiliado
	where cta.ctaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		and cta.ctaCategoria <> 'SIN_CATEGORIA'
		and cast(cta.ctaFechaCambioCategoria as date) > cast(ufr.roaFechaRetiro as date)


	delete cta
	from CategoriaAfiliado as cta
		inner join #UltimaFechaRetiro as ufr on ufr.roaAfiliado = cta.ctaAfiliado
	where cta.ctaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		and cta.ctaCategoria <> 'SIN_CATEGORIA'
		and cast(cta.ctaFechaCambioCategoria as date) > cast(ufr.roaFechaRetiro as date)


	drop table if exists #poblacionInactivosConCategoria
	create table #poblacionInactivosConCategoria(idAfiliado bigint)
	insert into #poblacionInactivosConCategoria
	select  distinct  
		a.afiId 
	from Afiliado a 
		inner join Persona p on p.perId=a.afiPersona
		left join CategoriaAfiliado c on c.ctaAfiliado=a.afiId
	where --AND c.ctaCategoria IS NULL
		(select COUNT(*) 
		from RolAfiliado r 
		where r.roaAfiliado=a.afiId 
			and roaEstadoAfiliado='ACTIVO' 
			and r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE')=0
		and (select top 1 ca.ctaCategoria 
			from CategoriaAfiliado ca 
			where ca.ctaAfiliado=a.afiId 
				AND ca.ctaTipoAfiliado='TRABAJADOR_DEPENDIENTE' 
			order by ca.ctaFechaCambioCategoria desc) != 'SIN_CATEGORIA'
		and c.ctaTipoAfiliado='TRABAJADOR_DEPENDIENTE'
	--FIN VERIFICAR  INACTIVOS Con Categoria

	drop table if exists #final;
	select ROW_NUMBER() over (order by roaAfiliado desc) as fila, 
		x.afiId, x.roaEmpleador as roaEmpleador, x.perid, x.roaEstadoAfiliado, x.roaId, x.roaFechaRetiro
	into #final
	from 
		(select ROW_NUMBER() over (partition by roaAfiliado order by roaFechaRetiro desc) as fila, * 
		from RolAfiliado as roa
			inner join Afiliado as afi on roa.roaAfiliado = afi.afiId
			inner join Persona as per on afi.afiPersona = per.perId
		where roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		and roa.roaEstadoAfiliado = 'INACTIVO'
		and afi.afiId in (select * from #poblacionInactivosConCategoria)
		) as x
	where x.fila = 1


	declare @con int = 1
	declare @fin int = (select COUNT(*) from #final)
	declare @var bigint;  	
	declare @var2 bigint;  
	declare @var3 bigint;
	declare @var4 varchar(20);
	declare @var5 bigint;
	declare @var6 datetime;

	while @con <= @fin
		begin 
			(select 
				@var = afiId
				,@var2 = roaEmpleador
				,@var3 = perid
				,@var4 = roaEstadoAfiliado
				,@var5 = roaId
			from #final where fila = @con)		
			exec USP_REP_CalcularCategoriaNuevaAfiliacion @var, @var2, @var3, @var4, @var5 
		
		
			update CategoriaAfiliado
			set ctaFechaCambioCategoria = f.roaFechaRetiro
			--select f.roaFechaRetiro
			from #final as f
				inner join CategoriaAfiliado as cta 
					on cta.ctaAfiliado = f.afiId	
					and cta.ctaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
			where f.fila = @con
				and cta.ctaId in (select max(ctaId) from CategoriaAfiliado where ctaAfiliado = @var)

			set @con += 1

		end


	--ACTIVOS SIN CATEGORIA
	
	drop table if exists #poblacionActivosSIN_CATEGORIA
	create table #poblacionActivosSIN_CATEGORIA(idAfiliado bigint)
	insert into #poblacionActivosSIN_CATEGORIA
	--VERIFICAR INICIA CONSULTA ACTIVOS SIN_CATEGORIA
	select  DISTINCT  a.afiId from Afiliado a 
		inner join Persona p on p.perId=a.afiPersona
		left join CategoriaAfiliado c on c.ctaAfiliado=a.afiId
	where --AND c.ctaCategoria IS NULL
		(select COUNT(*) 
		from RolAfiliado r 
		where r.roaAfiliado=a.afiId 
			and roaEstadoAfiliado='ACTIVO' 
			and r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE') >= 1
		and (select top 1 
				ca.ctaCategoria 
			from CategoriaAfiliado ca 
			where ca.ctaAfiliado=a.afiId 
				AND ca.ctaTipoAfiliado='TRABAJADOR_DEPENDIENTE'  
			order by ca.ctaFechaCambioCategoria desc) = 'SIN_CATEGORIA'
		and (select COUNT(*) 
			from SolicitudAfiliacionPersona sp 
				inner join RolAfiliado r on sp.sapRolAfiliado=r.roaId 
			where r.roaEstadoAfiliado = 'ACTIVO' 
				and r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' 
				and r.roaAfiliado = a.afiId 
				and (SP.sapEstadoSolicitud = 'ASIGNADA_AL_BACK' 
					or SP.sapEstadoSolicitud = 'RADICADA' 
					or SP.sapEstadoSolicitud = 'NO_CONFORME_SUBSANABLE')) = 0
	--FIN VERIFICAR ACTIVOS SIN_CATEGORIA

	drop table if exists #final2;
	select ROW_NUMBER() over (order by roaAfiliado desc) as fila, 
		x.afiId, x.roaEmpleador as roaEmpleador, x.perid, x.roaEstadoAfiliado, x.roaId, x.roaFechaAfiliacion
	into #final2
	from 
		(select ROW_NUMBER() over (partition by roaAfiliado order by roaId desc) as fila, * 
		from RolAfiliado as roa
			inner join Afiliado as afi on roa.roaAfiliado = afi.afiId
			inner join Persona as per on afi.afiPersona = per.perId
		where roa.roaTipoAfiliado  = 'TRABAJADOR_DEPENDIENTE'
		and roa.roaEstadoAfiliado = 'ACTIVO'
		and afi.afiId in(select * from #poblacionActivosSIN_CATEGORIA)
		) as x
	where x.fila = 1

	set @con = 1
	set @fin = (select COUNT(*) from #final2)

	while @con <= @fin
	begin 
		(select 
			@var = afiId
			,@var2 = roaEmpleador
			,@var3 = perid
			,@var4 = roaEstadoAfiliado
			,@var5 = roaId
		from #final2 where fila = @con)		
		exec USP_REP_CalcularCategoriaNuevaAfiliacion @var, @var2, @var3, @var4, @var5 
		
		--select @var
		--,@var2
		--,@var3
		--,@var4
		--,@var5 
		
		update CategoriaAfiliado
		set ctaFechaCambioCategoria = f.roaFechaAfiliacion
		--select f.roaFechaRetiro
		from #final2 as f
			inner join CategoriaAfiliado as cta 
				on cta.ctaAfiliado = f.afiId	
				and cta.ctaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		where f.fila = @con
			and cta.ctaId in (select max(ctaId) from CategoriaAfiliado where ctaAfiliado = @var)

		set @con += 1

	end


	--BENEFICIARIO ACTIVOS SIN_CATEGORIA COMO ULTIMO REGISTRO

	drop table if exists #benActivosSinCategoria
	select 
		cat.catId, cat.catIdAfiliado, cat.catIdBeneficiario
		,null as ctrl, ROW_NUMBER() over (order by cat.catFechaCambioCategoria desc) as fila
	into #benActivosSinCategoria
	from Beneficiario as ben
		inner join Afiliado as afi on ben.benAfiliado = afi.afiId
		inner join Categoria as cat 
			on cat.catIdAfiliado = afi.afiId
			and cat.catIdBeneficiario = ben.benId
	where ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'	
		and cat.catid in
			(select catId
			from 
				(select 
					*, row_number() over (
						partition by catIdAfiliado, catIdBeneficiario
						order by catIdAfiliado, catIdBeneficiario, catFechaCambioCategoria desc) as fila
				from Categoria as cat
				where cat.catIdAfiliado = afi.afiId
					and cat.catIdBeneficiario = ben.benId) as x
			where x.fila = 1
				and x.catCategoriaPersona = 'SIN_CATEGORIA')

	
	delete cat
	from #benActivosSinCategoria as b
		inner join Categoria as cat on b.catId = cat.catId

	declare @i bigint = 0
	declare @j bigint = 0
	declare @catIdAfiliado bigint
	declare @catIdBeneficiario bigint
	
	select @i = count(*)
	from #benActivosSinCategoria

	while @j <= @i
	begin 
		
		select 
			@catIdAfiliado = catIdAfiliado
			,@catIdBeneficiario = catIdBeneficiario
		from #benActivosSinCategoria as b
		where b.fila = @j
			and b.ctrl is not null

		exec USP_REP_CalcularCategoriaBeneficiarioInsert @catIdBeneficiario, @catIdAfiliado 

		update b
		set b.ctrl = 1
		from #benActivosSinCategoria as b
		where b.fila = @j
			and b.ctrl is not null

		set @j = @j + 1

	end	



	--BENEFICIARIO INACTIVOS CON CATEGORIA COMO ULTIMO REGISTRO

	drop table if exists #benInactivosConCategoria
	select 
		cat.catId, cat.catIdAfiliado, cat.catIdBeneficiario
		,null as ctrl, ROW_NUMBER() over (order by cat.catFechaCambioCategoria desc) as fila
	into #benInactivosConCategoria
	from Beneficiario as ben
		inner join Afiliado as afi on ben.benAfiliado = afi.afiId
		inner join Categoria as cat 
			on cat.catIdAfiliado = afi.afiId
			and cat.catIdBeneficiario = ben.benId
	where ben.benEstadoBeneficiarioAfiliado = 'INACTIVO'
		and cat.catid in
			(select catId
			from 
				(select 
					*, row_number() over (
						partition by catIdAfiliado, catIdBeneficiario
						order by catIdAfiliado, catIdBeneficiario, catFechaCambioCategoria desc) as fila
				from Categoria as cat
				where cat.catIdAfiliado = afi.afiId
					and cat.catIdBeneficiario = ben.benId) as x
			where x.fila = 1
				and x.catCategoriaPersona <> 'SIN_CATEGORIA')


	delete cat
	from #benInactivosConCategoria as b
		inner join Categoria as cat on b.catId = cat.catId

	set @i = 0
	set @j = 0
	set @catIdAfiliado = null
	set @catIdBeneficiario = null
	
	select @i = count(*)
	from #benInactivosConCategoria

	while @j <= @i
	begin 
		
		select 
			@catIdAfiliado = catIdAfiliado
			,@catIdBeneficiario = catIdBeneficiario
		from #benInactivosConCategoria as b
		where b.fila = @j
			and b.ctrl is not null

		exec USP_REP_CalcularCategoriaBeneficiarioInsert @catIdBeneficiario, @catIdAfiliado 

		update b
		set b.ctrl = 1
		from #benInactivosConCategoria as b
		where b.fila = @j
			and b.ctrl is not null

		set @j = @j + 1

	end	


	--BENEFICIARIO INACTIVOS CON CATEGORIA COMO ULTIMO REGISTRO

	drop table if exists #BenInacConCatogoria
	select *
	into #BenInacConCatogoria
	from 
		(select 
			cat.catTipoAfiliado,
			cat.catClasificacion,
			catIdAfiliado,
			catIdBeneficiario,
			cat.catFechaCambioCategoria,
			cat.catMotivoCambioCategoria,
			row_number() over (
				partition by catIdAfiliado, catIdBeneficiario
				order by catIdAfiliado, catIdBeneficiario, cat.catFechaCambioCategoria desc) as fila
		from Categoria as cat
			inner join Beneficiario as ben on cat.catIdBeneficiario = ben.benId
		where ben.benEstadoBeneficiarioAfiliado = 'INACTIVO'
			and cat.catCategoriaPersona <> 'SIN_CATEGORIA'
		group by 
			cat.catTipoAfiliado,
			cat.catClasificacion,
			catIdAfiliado,
			catIdBeneficiario,
			cat.catFechaCambioCategoria,
			cat.catMotivoCambioCategoria) as x



	drop table if exists #UltimoRegistroCategoria
	select *
	into #UltimoRegistroCategoria
	from 
		(select 
			cat.catIdAfiliado
			,cat.catIdBeneficiario
			,cat.catTipoAfiliado
			,cat.catClasificacion
			,cast(cat.catFechaCambioCategoria as date) as catFechaCambioCategoria
			,ROW_NUMBER() over (
				partition by cat.catIdAfiliado, cat.catIdBeneficiario
				order by cast(cat.catFechaCambioCategoria as date) desc) as fila
		from Categoria as cat
			inner join Beneficiario as ben on cat.catIdBeneficiario = ben.benId
		group by 
			cat.catIdAfiliado
			,cat.catIdBeneficiario
			,cat.catTipoAfiliado
			,cat.catClasificacion
			,cast(cat.catFechaCambioCategoria as date)) as x
	where x.fila = 1


	drop table if exists #BorrarBenInacConCatogoria
	select b.*
	into #BorrarBenInacConCatogoria
	from #BenInacConCatogoria as b
		inner join #UltimoRegistroCategoria as u 
			on u.catIdAfiliado = b.catIdAfiliado
			and u.catIdBeneficiario = b.catIdBeneficiario
			and u.catTipoAfiliado = b.catTipoAfiliado
			and u.catClasificacion = b.catClasificacion
			and cast(u.catFechaCambioCategoria as date) = cast(b.catFechaCambioCategoria as date)
	where b.catMotivoCambioCategoria <> 'RETIRO'


	delete cat
	from Categoria as cat
		inner join #BorrarBenInacConCatogoria as b
			on  cat.catIdAfiliado = b.catIdAfiliado
			and cat.catIdBeneficiario = b.catIdBeneficiario
			and cat.catTipoAfiliado = b.catTipoAfiliado
			and cat.catClasificacion = b.catClasificacion
			and cast(cat.catFechaCambioCategoria as date) = cast(b.catFechaCambioCategoria as date)
	where cat.catMotivoCambioCategoria not in ('RETIRO', 'RETIRO_NO_APLICA_SERVICIOS_CAJA')

end