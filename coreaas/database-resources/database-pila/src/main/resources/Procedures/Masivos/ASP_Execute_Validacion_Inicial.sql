-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2023-07-23
-- Description: procedimiento encargado de procesar la parte inicial de los aportes masivos, una vez sea aprobado se pasa por el flujo de pila. 
-- =============================================
CREATE OR ALTER procedure [masivos].[ASP_Execute_Validacion_Inicial] (@nombreArchivo varchar(300))
as 
begin
    SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON

	begin try

		if exists (select 1 from masivos.MasivoSimulado where maaNombreArchivo = @nombreArchivo)

			begin
				select 'El archivo ya existe --->>> ' + @nombreArchivo
			end
		
		else 
			begin
				begin transaction
					--==============================
					--==============================
					--======= Rechazo 1
					--==============================
					--==============================
					;with val1 as (
					select *
					, case when mag.magTipoAportante = 'EMPLEADOR' then null
					  else
						case when mag.magTipoIdentificacionAportante = mad.madTipoIdentificacionCotizante and mag.magNumeroIdentificacionAportante = mad.madNumeroIdentificacionCotizante 
								then null 
								else 1
								end
					  end as validacion1
					from masivos.MasivoArchivo as ma
					inner join masivos.MasivoGeneral as mag on mag.magMasivoArchivo = ma.maaId
					inner join masivos.MasivoDetallado  as mad on mag.magId = mad.madMasivoGeneral
					where ma.maaNombreArchivo = @nombreArchivo
					order by mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, mag.magPeriodoPago, mad.madTipoIdentificacionCotizante, mad.madNumeroIdentificacionCotizante, mad.madTotalAporte
					offset 0 rows fetch next 10000000 rows only)
					insert masivos.MasivoApGeeralRechazos (magrMasivoNombreArchivo, magrMasivoGeneralId, magrMasivoDetalleId, magrObservacion, magrProceso)
					select val1.maaNombreArchivo, val1.madMasivoGeneral, val1.madId, N'Aportante independiente o pensionado que paga aportes por otros cotizantes', 'APORTE'
					from val1
					where validacion1 is not null
					--==============================
					--==============================
					--======= Rechazo 2
					--==============================
					--==============================
					;with val2 as (
						select *
						,case when mag.magTipoAportante = 'EMPLEADOR' 
									then case when mad.madTipoCotizante in (select tipoCot from staging.StagingTiposCotizantes where tipotra = 'TRABAJADOR_DEPENDIENTE') --=== Esta tabla se trae de pila confa, debe estar en la rama master. 
										then 1
										else 0
										end
							when mag.magTipoAportante = 'INDEPENDIENTE'
								then case when mad.madTipoCotizante in (select tipoCot from staging.StagingTiposCotizantes where tipotra = 'TRABAJADOR_INDEPENDIENTE') --=== Esta tabla se trae de pila confa, debe estar en la rama master. 
										then 1
										else 0
										end
							when mag.magTipoAportante = 'PENSIONADO' and mad.madTipoCotizante is not null 
									then 0
							else 1
							end as valTipocot
						,case when mag.magTipoAportante = 'EMPLEADOR'
								then FIRST_VALUE(mag.magId) over (partition by mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, mag.magPeriodoPago, mag.magFechaPago order by mad.madId)
								else FIRST_VALUE(mad.madId) over (partition by mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, mag.magPeriodoPago, mag.magFechaPago, mad.madTipoCotizante order by mad.madId)
								end valAporte
						from masivos.MasivoArchivo as ma
						inner join masivos.MasivoGeneral as mag on mag.magMasivoArchivo = ma.maaId
						inner join masivos.MasivoDetallado  as mad on mag.magId = mad.madMasivoGeneral
						where not exists (select 1 from masivos.MasivoApGeeralRechazos where magrMasivoGeneralId = mag.magId and magrMasivoDetalleId = mad.madId and magrMasivoNombreArchivo = @nombreArchivo)
						order by mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, mag.magPeriodoPago, mad.madTipoIdentificacionCotizante, mad.madNumeroIdentificacionCotizante, mad.madTotalAporte
						offset 0 rows fetch next 10000000 rows only),
					val3 as (
					select *
					,case when val2.magTipoAportante = 'EMPLEADOR' 
						then case when sum(valTipocot) over (partition by valAporte) > 0
								then valAporte
								else null
								end
						else case when val2.magTipoAportante <> 'EMPLEADOR' and valTipocot = 1
							then valAporte
							else null
							end
						end as idsRechazar
					from val2
					order by val2.magTipoAportante, val2.magTipoIdentificacionAportante, val2.magNumeroIdentificacionAportante
					offset 0 rows fetch next 10000000 rows only)
					insert masivos.MasivoApGeeralRechazos (magrMasivoNombreArchivo, magrMasivoGeneralId, magrMasivoDetalleId, magrObservacion, magrProceso)
					select val3.maaNombreArchivo, val3.madMasivoGeneral, val3.madId, N'Tipo de cotizante que no pertenece al grupo de cotizante', 'APORTE'
					from val3
					where idsRechazar is null
					and maaNombreArchivo = @nombreArchivo
					--==============================
					--==============================
					--======= Buscamos si existen en core persona
					--==============================
					--==============================
					select ma.maaNombreArchivo, mag.magId, mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, magRazonSocial, madId, mad.madTipoIdentificacionCotizante, madNumeroIdentificacionCotizante--, madNombreCotizante
					,madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante
					,null as extApo, null as extCot, null as extAporLisBlan
					into #validarPersonas
					from masivos.MasivoArchivo as ma
					inner join masivos.MasivoGeneral as mag on mag.magMasivoArchivo = ma.maaId
					inner join masivos.MasivoDetallado  as mad on mag.magId = mad.madMasivoGeneral
					where not exists (select 1 from masivos.MasivoApGeeralRechazos where magrMasivoGeneralId = mag.magId and magrMasivoDetalleId = mad.madId and magrMasivoNombreArchivo = @nombreArchivo)
					and ma.maaNombreArchivo = @nombreArchivo

					update a set a.magRazonSocial = concat(a.madPrimerNombreCotizante,N' ',a.madSegundoNombreCotizante,N' ',a.madPrimerApellidoCotizante,N' ',a.madSegundoApellidoCotizante) --a.madNombreCotizante
					from #validarPersonas as a 
					where magTipoAportante <> 'EMPLEADOR'

					--==============================
					--==============================
					create table #buscarPersona (perId bigInt, pertipoId varchar(25), perNumId varchar(20), origen varchar(200))
					insert #buscarPersona
					execute sp_execute_remote coreReferenceData, N'
					select perId, perTipoIdentificacion, perNumeroIdentificacion
					from dbo.persona with (nolock)'

					update a set procesado = 1
					from masivos.crearPersonaAporCot as a 
					where exists (select 1 from #buscarPersona as b where a.perTipoIdentificacion = b.pertipoId and a.perNumeroIdentificacion = b.perNumId)
					
					update vap set extApo = case when bp.perId is null then 0 else 1 end, extCot = case when bp.perId is null then 0 else 1 end
					from #validarPersonas as vap
					left join #buscarPersona as bp on vap.magTipoIdentificacionAportante = bp.pertipoId and vap.magNumeroIdentificacionAportante = bp.perNumId
					
					
					update vap set extApo = case when bp.perId is null then 0 else 1 end, extCot = case when bp.perId is null then 0 else 1 end
					from #validarPersonas as vap
					left join #buscarPersona as bp on vap.madTipoIdentificacionCotizante = bp.pertipoId and vap.madNumeroIdentificacionCotizante = bp.perNumId
					
					
					--==============================
					--==============================
					--======= Buscamos si existe cotizante en lista blanca
					--==============================
					--==============================
					create table #buscarListaBlanca (lblId bigInt, lblNumeroIdentificacionPlanilla varchar(20),lblTipoIdentificacionEmpleador varchar(22),lblNumeroIdentificacionEmpleador varchar(20),lblActivo tinyInt, esEmp tinyInt, esIndPen tinyInt, origen varchar(250))
					insert #buscarListaBlanca
					execute sp_execute_remote coreReferenceData, N'
					;with perAport as (
					select p.perId, p.perTipoIdentificacion, p.perNumeroIdentificacion, r.roaTipoAfiliado
					from persona as p with (nolock)
					inner join dbo.Afiliado as a with (nolock) on p.perId = a.afiPersona
					inner join dbo.RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
					where isnull(r.roaTipoAfiliado,'''') in (''PENSIONADO'', ''TRABAJADOR_INDEPENDIENTE'')),
					empAport as (select p.perId, p.perTipoIdentificacion, p.perNumeroIdentificacion
					from persona as p with (nolock)
					inner join Empresa as e with (nolock) on p.perId = e.empPersona),
					buscarEmpLista as (select lbl.*, case when perId is not null then 1 else null end as [Es empleador]
					from dbo.ListasBlancasAportantes as lbl
					left join empAport as e on e.perTipoIdentificacion = lbl.lblTipoIdentificacionEmpleador and e.perNumeroIdentificacion = lbl.lblNumeroIdentificacionEmpleador)
					select lblId,lblNumeroIdentificacionPlanilla,lblTipoIdentificacionEmpleador,lblNumeroIdentificacionEmpleador,lblActivo,[Es empleador],  case when perId is not null then 1 else null end as [Es indPen]
					from buscarEmpLista as a
					left join perAport as b on a.lblTipoIdentificacionEmpleador = b.perTipoIdentificacion and a.lblNumeroIdentificacionEmpleador = b.perNumeroIdentificacion'
					
					alter table #validarPersonas add tipoIdListaBlanca varchar(25), numIdListaBlanca varchar(20)
					
					update vap set extAporLisBlan = case when bl.lblId is null then 0 else 1 end, 
					tipoIdListaBlanca = case when (case when bl.lblId is null then 0 else 1 end) = 1 then bl.lblTipoIdentificacionEmpleador else null end,
					numIdListaBlanca = case when (case when bl.lblId is null then 0 else 1 end) = 1 then bl.lblNumeroIdentificacionEmpleador else null end
					from #validarPersonas as vap
					left join #buscarListaBlanca as bl on vap.magNumeroIdentificacionAportante = bl.lblNumeroIdentificacionPlanilla
					--==============================
					--==============================
					--======= Crear los aportantes que no estánen l
					--==============================
					--==============================
					;with crearAportante as (
					select magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante
					,case when (extApo + extCot + extAporLisBlan) = 0 then 1 else 0 end as crear, magTipoAportante
					from #validarPersonas
					group by  magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, extApo, extCot, extAporLisBlan, magRazonSocial, magTipoAportante
					order by magTipoIdentificacionAportante, magNumeroIdentificacionAportante
					offset 0 rows fetch next 10000000 rows only),
					crearCot as (
					select madTipoIdentificacionCotizante, madNumeroIdentificacionCotizante, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, 'DEPENDIENTE' as magTipoAportante
					,case when (extApo + extCot + extAporLisBlan) = 0 then 1 else 0 end as crear
					from #validarPersonas
					where magTipoAportante = 'EMPLEADOR'
					group by madTipoIdentificacionCotizante, madNumeroIdentificacionCotizante, extApo, extCot, extAporLisBlan, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, magTipoAportante
					order by madTipoIdentificacionCotizante, madNumeroIdentificacionCotizante
					offset 0 rows fetch next 10000000 rows only),
					allcrear as (
					select magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, crear, magTipoAportante
					from crearAportante where crear = 1
					union
					select madTipoIdentificacionCotizante, madNumeroIdentificacionCotizante, magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, crear, magTipoAportante
					from crearCot where crear = 1)
					insert masivos.crearPersonaAporCot (perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial, perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,  crear,procesado, magTipoAportante)
					select magTipoIdentificacionAportante,magNumeroIdentificacionAportante,magRazonSocial, madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante, crear, 0 procesado, magTipoAportante
					from allcrear
					where not exists (select 1 from masivos.crearPersonaAporCot as a where a.perTipoIdentificacion = magTipoIdentificacionAportante and a.perNumeroIdentificacion = magNumeroIdentificacionAportante)
					group by magTipoIdentificacionAportante,magNumeroIdentificacionAportante,magRazonSocial,madPrimerNombreCotizante, madSegundoNombreCotizante, madPrimerApellidoCotizante, madSegundoApellidoCotizante,crear, magTipoAportante

					--==============================
					--==============================
					--======= Creación de las personas. 
					--==============================
					--==============================

					--execute [masivos].[crearPersonas]

					/*
					create table #buscarPersonaUpte (perId bigInt, pertipoId varchar(25), perNumId varchar(20), origen varchar(200))
					insert #buscarPersonaUpte
					execute sp_execute_remote coreReferenceData, N'
					select perId, perTipoIdentificacion, perNumeroIdentificacion
					from dbo.persona with (nolock)'
					*/
					--==============================
					--==============================
					--======= Estos son los registros finales con los que se va a trabajar. 
					--======= Sacamos información, para simulación del resultado. 
					--==============================
					--==============================
					declare @total int = ( select count(*)
								from masivos.MasivoArchivo as ma
								inner join masivos.MasivoGeneral as mag on mag.magMasivoArchivo = ma.maaId
								inner join masivos.MasivoDetallado  as mad on mag.magId = mad.madMasivoGeneral
								inner join #validarPersonas as valp on valp.maaNombreArchivo = ma.maaNombreArchivo and mag.magId = valp.magId and mad.madId = valp.madId
								and valp.magTipoAportante = mag.magTipoAportante and valp.magNumeroIdentificacionAportante = mag.magNumeroIdentificacionAportante
								and valp.madTipoIdentificacionCotizante = mad.madTipoIdentificacionCotizante and valp.madNumeroIdentificacionCotizante = mad.madNumeroIdentificacionCotizante
								where not exists (select 1 from masivos.MasivoApGeeralRechazos where magrMasivoGeneralId = mag.magId and magrMasivoDetalleId = mad.madId and magrMasivoNombreArchivo = @nombreArchivo) and ma.maaNombreArchivo = @nombreArchivo)


					declare @contado as table (madId bigInt)

					insert into masivos.SimilacionLogGeneral (maaId,maaNombreArchivo,maaEstado,maaNumeroRadicacion,maaFechaProcesamiento,maaFechaActualizacion,maaUsuario,maaSolicitud,maaTipoArchivo ,magId,magTipoIdentificacionAportante,magNumeroIdentificacionAportante,magDepartamento
					,magMunicipio,magRazonSocial,magEstadoArchivo,magPeriodoPago,magFechaRecepcionAporte,magFechaPago,magMasivoArchivo,magTipoAportante,madId,madMasivoGeneral,madTipoCotizante,madTipoIdentificacionCotizante,madNumeroIdentificacionCotizante,madConceptoPago
					,madIbc,madSalarioBasico,madDiasCotizados,madAporteObligatorio,madTarifa,madTotalAporte,madValorIntereses,madHorasLaboradas,madDiasMora,madNovING,madNovRET,madNovIRL,madNovVSP,madNovVST,madNovSLN,madNovIGE,madNovLMA,madNovVACLR, extApo, extAporLisBlan, extCot, cantNov)
					output inserted.madId into @contado 
					select
					ma.maaId
					,ma.maaNombreArchivo
					,ma.maaEstado
					,ma.maaNumeroRadicacion
					,ma.maaFechaProcesamiento
					,ma.maaFechaActualizacion
					,ma.maaUsuario
					,ma.maaSolicitud
					,ma.maaTipoArchivo 
					,mag.magId
					,mag.magTipoIdentificacionAportante
					,mag.magNumeroIdentificacionAportante
					,mag.magDepartamento
					,mag.magMunicipio
					,mag.magRazonSocial
					,mag.magEstadoArchivo
					,mag.magPeriodoPago
					,mag.magFechaRecepcionAporte
					,mag.magFechaPago
					,mag.magMasivoArchivo
					,mag.magTipoAportante
					,mad.madId
					,mad.madMasivoGeneral
					,mad.madTipoCotizante
					,mad.madTipoIdentificacionCotizante
					,mad.madNumeroIdentificacionCotizante
					,mad.madConceptoPago
					,mad.madIbc
					,mad.madSalarioBasico
					,mad.madDiasCotizados
					,mad.madAporteObligatorio
					,mad.madTarifa
					,mad.madTotalAporte
					,mad.madValorIntereses
					,mad.madHorasLaboradas
					,mad.madDiasMora
					,mad.madNovING
					,mad.madNovRET
					,mad.madNovIRL
					,mad.madNovVSP
					,mad.madNovVST
					,mad.madNovSLN
					,mad.madNovIGE
					,mad.madNovLMA
					,mad.madNovVACLR
					, valp.extApo, valp.extAporLisBlan, valp.extCot
					,(case when mad.madNovING is not null then 1 else 0 end
					+ case when mad.madNovRET is not null then 1 else 0 end
					+ case when mad.madNovIRL is not null then 1 else 0 end
					+ case when mad.madNovVSP is not null then 1 else 0 end
					+ case when mad.madNovVST is not null then 1 else 0 end
					+ case when mad.madNovSLN is not null then 1 else 0 end
					+ case when mad.madNovIGE is not null then 1 else 0 end
					+ case when mad.madNovLMA is not null then 1 else 0 end
					+ case when mad.madNovVACLR is not null then 1 else 0 end) as cantNov
					from masivos.MasivoArchivo as ma
					inner join masivos.MasivoGeneral as mag on mag.magMasivoArchivo = ma.maaId
					inner join masivos.MasivoDetallado  as mad on mag.magId = mad.madMasivoGeneral
					inner join #validarPersonas as valp on valp.maaNombreArchivo = ma.maaNombreArchivo and mag.magId = valp.magId and mad.madId = valp.madId
					and valp.magTipoAportante = mag.magTipoAportante and valp.magNumeroIdentificacionAportante = mag.magNumeroIdentificacionAportante
					and valp.madTipoIdentificacionCotizante = mad.madTipoIdentificacionCotizante and valp.madNumeroIdentificacionCotizante = mad.madNumeroIdentificacionCotizante
					where not exists (select 1 from masivos.MasivoApGeeralRechazos where magrMasivoGeneralId = mag.magId and magrMasivoDetalleId = mad.madId and magrMasivoNombreArchivo = @nombreArchivo) and ma.maaNombreArchivo = @nombreArchivo
					order by mag.magTipoAportante, mag.magTipoIdentificacionAportante, mag.magNumeroIdentificacionAportante, mag.magPeriodoPago
					offset 0 rows fetch next 1000000 rows only
					
					
					declare @cantidadFilas int  = (select count(*) from @contado)

					
					if @cantidadFilas =  @total
						begin
							
							update ma set ma.maaEstado = 'SIMULADO'
							from masivos.masivoArchivo as ma
							where maaNombreArchivo = @nombreArchivo
						end

					
					delete from @contado


					insert masivos.MasivoSimulado (maaFechaProcesamiento,maaNombreArchivo,magTipoIdentificacionAportante,magNumeroIdentificacionAportante,magRazonSocial,magPeriodoPago,maaEstado,maaUsuario,cantRegistros,cantidadNovedade,MontoToal)
					select maaFechaProcesamiento, maaNombreArchivo, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magRazonSocial, magPeriodoPago, maaEstado, maaUsuario, count(*) as cantRegistros, sum(cantNov) as cantidadNovedade, sum(madTotalAporte) as MontoToal
					from masivos.SimilacionLogGeneral
					where maaNombreArchivo = @nombreArchivo
					group by maaFechaProcesamiento, maaNombreArchivo, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magRazonSocial, magPeriodoPago, maaEstado, maaUsuario
					
					
					--=== Borrado tablas temporales. 
					drop table #validarPersonas
					drop table #buscarPersona
					drop table #buscarListaBlanca

					--=== FINALIZA PROCESO INICIAL, EN ESTE PUNTO SE ESPERA SI APRUEBAN O NO EL APORTE, PARA CONTINUAR. 

				commit transaction

			end

	end try
	begin catch
		
		SELECT  
		ERROR_NUMBER() AS ErrorNumber  
		,ERROR_SEVERITY() AS ErrorSeverity  
		,ERROR_STATE() AS ErrorState  
		,ERROR_PROCEDURE() AS ErrorProcedure  
		,ERROR_LINE() AS ErrorLine  
		,ERROR_MESSAGE() AS ErrorMessage;  
 
		IF (XACT_STATE()) = -1  
		BEGIN    
		    ROLLBACK TRANSACTION;  
		END;  

		IF (XACT_STATE()) = 1  
		BEGIN  
		    COMMIT TRANSACTION;     
		END;  

	end catch

end