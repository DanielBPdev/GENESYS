
CREATE OR ALTER PROCEDURE [dbo].[USP_REP_CalcularCategoriaBeneficiarioInsert]

@idBeneficiario BIGINT,
@idafiliado BIGINT
--@idBeneficiarioDetalle BIGINT ,
--@tipoIdentificacionBeneficiario varchar(50),
--@identificacionBeneficiario varchar(30),
--@tipoIdentificacionAfiliado varchar(50),
--@identificacionAfiliado varchar(30)


    --REQUERIDO ALGUN DATO DE BENFICIARIO ASI COMO ALGUN DATO DEL AFILIADO

		with execute as owner
AS
		BEGIN /*
--PARA PRUEBAS INICIA
--IDENTIFICACION BENFICIARIO
DECLARE @tipoIdentificacionBeneficiario varchar(50)='';
DECLARE @identificacionBeneficiario varchar(30) ='';

--IDENTIFICACION AFILIADO PRINCIPAL
DECLARE @tipoIdentificacionAfiliado varchar(50)='';
DECLARE @identificacionAfiliado varchar(30) =''; 

DECLARE @idBeneficiario BIGINT=NULL;
DECLARE @idafiliado BIGINT =NULL;
DECLARE @idBeneficiarioDetalle BIGINT =NULL;
*/


	    SET NOCOUNT ON;   
		--CALCULAR CATEGORIA BENEFICIARIO MASIVA POR AFILIADO
		drop table if exists #categoriasAfiliado
		drop table if exists #beneficiariosHerencia
		declare @getlocaldate datetime = dbo.getlocaldate()

		create table #categoriasAfiliado (ctaId bigint,ctaAfiliado bigint,ctaCategoria nvarchar(20),ctaClasificacion nvarchar(50),ctaEstadoAfiliacion nvarchar(20),ctaFechaCambioCategoria datetime,ctaMotivoCambioCategoria nvarchar(100),ctaTipoAfiliado nvarchar(50),propiedad nvarchar(20),prioridad int, tarifaUVT varchar(50),fechaFinServicioSinAfiliacion datetime,ctaTotalIngresoMesada numeric(9,0))
		insert #categoriasAfiliado (ctaId,ctaAfiliado, ctaCategoria, ctaClasificacion, ctaEstadoAfiliacion, ctaFechaCambioCategoria,ctaMotivoCambioCategoria,ctaTipoAfiliado,propiedad,prioridad, tarifaUVT,fechaFinServicioSinAfiliacion,ctaTotalIngresoMesada)
		select t.ctaId,t.ctaAfiliado,t.ctaCategoria,t.ctaClasificacion,t.ctaEstadoAfiliacion,t.ctaFechaCambioCategoria,t.ctaMotivoCambioCategoria,t.ctaTipoAfiliado,  t.c1,t.c2, t.ctaTarifaUVT,t.ctaFechaFinServicioSinAfiliacion,t.ctaTotalIngresoMesada
		from (select c.ctaId,c.ctaAfiliado,c.ctaCategoria,c.ctaClasificacion,c.ctaEstadoAfiliacion,c.ctaFechaCambioCategoria,c.ctaMotivoCambioCategoria,c.ctaTipoAfiliado, 'AFILIADO' as c1,0 as c2, ctaTarifaUVT,ctaFechaFinServicioSinAfiliacion,c.ctaTotalIngresoMesada,
		row_number() over (partition by afiid order by ctaFechaCambioCategoria desc) as id
		from CategoriaAfiliado c with(nolock)
		inner join Afiliado a with(nolock) on a.afiId = c.ctaAfiliado
		where a.afiid = @idafiliado) t
		where t.id = 1

		create table #beneficiariosHerencia (catTipoAfiliado varchar(30),catCategoriaPersona  varchar(50),catTipoBeneficiario  varchar(30),catClasificacion  varchar(48),catTotalIngresoMesada numeric,catFechaCambioCategoria datetime,catMotivoCambioCategoria  varchar(60),catAfiliadoPrincipal bit ,catIdAfiliado bigint,catIdBeneficiario bigint,catTarifaUVTPersona  varchar(50))

		IF (@idBeneficiario IS  NULL AND @idBeneficiario IS NULL) --busca por beneficiario detalle
		
		BEGIN 
			insert  into #beneficiariosHerencia (catTipoAfiliado ,catCategoriaPersona,catTipoBeneficiario,catClasificacion,catTotalIngresoMesada,catFechaCambioCategoria,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona)
			select ca.ctaTipoAfiliado,ca.ctaCategoria,b.benTipoBeneficiario,ca.ctaClasificacion,ca.ctaTotalIngresoMesada,ca.ctaFechaCambioCategoria,ca.ctaMotivoCambioCategoria,0,ca.ctaAfiliado,b.benId,ca.tarifaUVT
			from Beneficiario b with(nolock)
			inner join #categoriasAfiliado ca on ca.ctaAfiliado=b.benAfiliado
			where b.benId not in (select distinct benId
									from Beneficiario b with(nolock)
									inner join Afiliado a with(nolock) on b.benAfiliado=a.afiId
									inner join #categoriasAfiliado ca on ca.ctaAfiliado=b.benAfiliado
									left join CondicionInvalidez c with(nolock) on c.coiPersona=b.benPersona
									left join CertificadoEscolarBeneficiario ceb with(nolock) on ceb.cebBeneficiarioDetalle = b.benBeneficiarioDetalle
									inner join PersonaDetalle ped with(nolock) on ped.pedPersona = b.benPersona
									where b.benEstadoBeneficiarioAfiliado = 'INACTIVO' OR
									 ((b.benTipoBeneficiario!='PADRE' and  b.benTipoBeneficiario!='MADRE' and   b.benTipoBeneficiario!='CONYUGE')
										AND (ceb.cebFechaVencimiento IS NULL OR ceb.cebFechaVencimiento < CONVERT(DATE,@getlocaldate) OR 1 = 0)   AND DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate)-(CASE
										WHEN DATEADD(YY,DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate),ped.pedFechaNacimiento) > @getlocaldate THEN	1 ELSE	0 	END) > 18 
										AND DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate)-(CASE WHEN DATEADD(YY,DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate),ped.pedFechaNacimiento) > @getlocaldate THEN	1 ELSE	0 	END)  < 23
										AND  (isnull(c.coiInvalidez,0) != 1) 
									OR
										(ceb.cebFechaVencimiento >= CONVERT(DATE,dbo.GetLocalDate())  AND DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate)-(CASE
										WHEN DATEADD(YY,DATEDIFF(YEAR,ped.pedFechaNacimiento,@getlocaldate),ped.pedFechaNacimiento) > @getlocaldate THEN	1 ELSE	0 	END) < 60 
										AND  (isnull(c.coiInvalidez,0) != 1)  and (b.benTipoBeneficiario='PADRE' OR  b.benTipoBeneficiario='MADRE' )))
									)

		END
		ELSE IF (@idBeneficiario IS NOT NULL OR @idBeneficiario !='') --busca por tipo y numero identificacion al id de beneficiario teniendo en cuenta el id de afiliado
				BEGIN 
				-- CONDICIONAL PARA CUANDO SE REITRA EL BENEFICIARIO UNICAMENTE
					IF EXISTS( SELECT 1 FROM Beneficiario b with(nolock) inner join  Persona p with(nolock) on p.perId=b.benPersona
						inner join #categoriasAfiliado ca on ca.ctaAfiliado=b.benAfiliado
						where benid = @idBeneficiario and benEstadoBeneficiarioAfiliado='INACTIVO') 
						and (select count(*) from RolAfiliado r with (nolock) inner join #categoriasAfiliado c on c.ctaAfiliado = r.roaAfiliado
						 where r.roaTipoAfiliado=c.ctaTipoAfiliado and r.roaEstadoAfiliado='ACTIVO') > 0
					BEGIN
						insert into #beneficiariosHerencia (catTipoAfiliado ,catCategoriaPersona,catTipoBeneficiario,catClasificacion,catTotalIngresoMesada,catFechaCambioCategoria,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona)
						select ca.ctaTipoAfiliado,'SIN_CATEGORIA',b.benTipoBeneficiario,ca.ctaClasificacion,ca.ctaTotalIngresoMesada,@getlocaldate,'RETIRO',0,ca.ctaAfiliado,b.benId,'SIN_TARIFA'
						from Beneficiario b with(nolock)
						inner join #categoriasAfiliado ca on ca.ctaAfiliado=b.benAfiliado
						where benid = @idBeneficiario
					END	ELSE
					BEGIN					
							insert into #beneficiariosHerencia (catTipoAfiliado ,catCategoriaPersona,catTipoBeneficiario,catClasificacion,catTotalIngresoMesada,catFechaCambioCategoria,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona)
							select ca.ctaTipoAfiliado,ca.ctaCategoria,b.benTipoBeneficiario,ca.ctaClasificacion,ca.ctaTotalIngresoMesada,ca.ctaFechaCambioCategoria,ca.ctaMotivoCambioCategoria,0,ca.ctaAfiliado,b.benId,ca.tarifaUVT
							from Beneficiario b with(nolock)
							inner join #categoriasAfiliado ca on ca.ctaAfiliado=b.benAfiliado
							where benid = @idBeneficiario
					END
				END

			create nonclustered index IX_beneficiariosHerencia_BeneficiarioAfiliado on #beneficiariosHerencia (catIdBeneficiario, catIdAfiliado);
			drop table if exists #cat
			create table #cat (catId bigint,catTipoAfiliado varchar(30),catCategoriaPersona varchar(50),catTipoBeneficiario varchar(30),catClasificacion varchar(50),catTotalIngresoMesada numeric(9,0),catMotivoCambioCategoria varchar(60),catAfiliadoPrincipal bit,catIdAfiliado bigint,catIdBeneficiario bigint,catTarifaUVTPersona varchar(50))

			drop table if exists #Categoria
			select catId,catTipoAfiliado,catCategoriaPersona,catTipoBeneficiario,catClasificacion,catTotalIngresoMesada,catFechaCambioCategoria,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona
			into #Categoria
			from Categoria
			where catIdAfiliado = @idafiliado	
			create nonclustered index ix_#Categoria_catIdAfiliado_catIdBeneficiario on #Categoria (catIdAfiliado,catIdBeneficiario) include(catTipoAfiliado,catFechaCambioCategoria)

			;with MaxFecha as (select c.catidbeneficiario,c.catTipoAfiliado,max(c.catFechaCambioCategoria) as MaxFechaCambio
								from #Categoria c with(nolock)
								inner join #beneficiariosHerencia ca on (ca.catIdBeneficiario = c.catIdBeneficiario and c.catIdAfiliado = ca.catIdAfiliado)
								group by c.catidbeneficiario,c.catTipoAfiliado)

			insert #cat(catId,catTipoAfiliado,catCategoriaPersona,catTipoBeneficiario,catClasificacion,catTotalIngresoMesada,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona)
			select c.catId,c.catTipoAfiliado,c.catCategoriaPersona,c.catTipoBeneficiario,c.catClasificacion,c.catTotalIngresoMesada,c.catMotivoCambioCategoria,c.catAfiliadoPrincipal,c.catIdAfiliado,c.catIdBeneficiario,c.catTarifaUVTPersona
			from #Categoria c with(nolock)
			inner join MaxFecha mf on c.catidbeneficiario = mf.catidbeneficiario and c.catTipoAfiliado = mf.catTipoAfiliado and c.catFechaCambioCategoria = mf.MaxFechaCambio

			create index IX_Cat_Beneficiario_Tipo_Categoria_Motivo on #cat(catIdBeneficiario,catTipoAfiliado,catCategoriaPersona,catMotivoCambioCategoria);

			insert  into Categoria(catTipoAfiliado ,catCategoriaPersona,catClasificacion,catTotalIngresoMesada,catFechaCambioCategoria,catMotivoCambioCategoria,catAfiliadoPrincipal,catIdAfiliado,catIdBeneficiario,catTarifaUVTPersona)
			select cat.catTipoAfiliado,cat.catCategoriaPersona,cat.catClasificacion,isnull(cat.catTotalIngresoMesada,0) catTotalIngresoMesada,cat.catFechaCambioCategoria,cat.catMotivoCambioCategoria,cat.catAfiliadoPrincipal,cat.catIdAfiliado,cat.catIdBeneficiario,cat.catTarifaUVTPersona 
			from #beneficiariosHerencia cat
			left join #cat as b on cat.catIdBeneficiario = b.catIdBeneficiario and cat.catTipoAfiliado = b.catTipoAfiliado and cat.catCategoriaPersona = b.catCategoriaPersona and cat.catMotivoCambioCategoria = b.catMotivoCambioCategoria
			where b.catIdBeneficiario is null
	END