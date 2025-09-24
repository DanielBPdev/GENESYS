-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2023-07-23
-- Description: procedimiento encargado de persistir el registroGeneral y detallado después de la aprobación del proceso de aportes masivos. 
-- =============================================
CREATE OR ALTER procedure [masivos].[ASP_Execute_pila2FaseInicial] (@nombreArchivo varchar(300))
as 
begin
    SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON
	begin try

		--begin transaction

			--execute masivos.ASP_Execute_Validacion_Inicial @nombreArchivo
			;with asignarMarcaProcesamiento as (
			select * , DENSE_RANK() over (order by maaNombreArchivo, magTipoAportante, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magPeriodoPago) as idParaJoin
			from masivos.SimilacionLogGeneral as slg
			where slg.maaNombreArchivo = @nombreArchivo
			and magTipoAportante = 'EMPLEADOR')
			update asignarMarcaProcesamiento set idParaJoinRegistroGeneral = idParaJoin
			
			
			;with asignarMarcaProcesamiento as (
			select * , DENSE_RANK() over (order by maaNombreArchivo, magTipoAportante, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magPeriodoPago, madId) as idParaJoin
			from masivos.SimilacionLogGeneral as slg
			where slg.maaNombreArchivo = @nombreArchivo
			and magTipoAportante <> 'EMPLEADOR')
			update asignarMarcaProcesamiento set idParaJoinRegistroGeneral = idParaJoin

			--===========================================
			--=== ACTUALIZACIÓN PARA LISTAS BLANCAS 
			--===========================================

			declare @listaBlanca as table (lblNumeroIdentificacionPlanilla varchar (16) NOT NULL,lblTipoIdentificacionEmpleador varchar (20) NOT NULL,lblNumeroIdentificacionEmpleador varchar (16) NOT NULL,lblActivo bit NOT NULL, origen varchar(200))
			insert @listaBlanca
			execute sp_execute_remote coreReferenceData, N'select lblNumeroIdentificacionPlanilla,lblTipoIdentificacionEmpleador,lblNumeroIdentificacionEmpleador,lblActivo from dbo.ListasBlancasAportantes with (nolock) where lblActivo = 1'

			;with actListaBlanca as (
			select *
			from masivos.SimilacionLogGeneral as slg
			inner join @listaBlanca as b on slg.magNumeroIdentificacionAportante = b.lblNumeroIdentificacionPlanilla
			where slg.extAporLisBlan = 1
			)
			update actListaBlanca set magTipoIdentificacionAportante = lblTipoIdentificacionEmpleador, magNumeroIdentificacionAportante = lblNumeroIdentificacionEmpleador
			from actListaBlanca


			create table #updateRegistroDetallado (idParaJoinRegistroGeneral int, magRazonSocial varchar(300), magTipoIdentificacionAportante varchar(50), magNumeroIdentificacionAportante varchar(30), magPeriodoPago varchar(7), regId bigInt, nombreArchivo varchar(300), regIdTransaccion bigInt)

			declare @cont int = 1
			declare @cant int = (select max(idParaJoinRegistroGeneral) from masivos.SimilacionLogGeneral as slg where slg.maaNombreArchivo = @nombreArchivo and slg.magTipoAportante = 'EMPLEADOR')

			while @cont <= @cant
				begin
					declare @localDate datetime = dbo.getLocalDate()
					declare @IdTransaccionMasTab as table (id int, idTransact bigInt)
					declare @IdTransaccionMas bigInt = 0
					INSERT INTO [staging].[Transaccion] (traFechaTransaccion) 
					output @cont, inserted.traId
					into @IdTransaccionMasTab
					select @localDate
					set @IdTransaccionMas = (select idTransact from @IdTransaccionMasTab where id = @cont)
					INSERT INTO [staging].[RegistroGeneral] (regTransaccion, regEsAportePensionados ,regNombreAportante ,regTipoIdentificacionAportante ,regNumeroIdentificacionAportante ,regDigVerAportante ,regPeriodoAporte ,regTipoPlanilla ,regClaseAportante ,regCodSucursal 
					,regNomSucursal ,regDireccion ,regCodCiudad ,regCodDepartamento ,regTelefono ,regFax ,regEmail ,regFechaMatricula ,regNaturalezaJuridica ,regCantPensionados ,regModalidadPlanilla ,regValTotalApoObligatorio 
					,regValorIntMora ,regFechaRecaudo ,regCodigoEntidadFinanciera ,regOperadorInformacion ,regNumeroCuenta ,regFechaActualizacion ,regRegistroControl ,regRegistroControlManual ,regRegistroFControl, regEsSimulado, regEstadoEvaluacion, regOUTEstadoArchivo)
					output @cont, inserted.regNombreAportante, inserted.regTipoIdentificacionAportante, inserted.regNumeroIdentificacionAportante, inserted.regPeriodoAporte, inserted.regId, @nombreArchivo, inserted.regTransaccion
					into #updateRegistroDetallado
					select 
					@IdTransaccionMas as regTransaccion,
					case when magTipoAportante = N'PENSIONADO' then 1 else 0 end as regEsAportePensionados,
					magRazonSocial as regNombreAportante,
					magTipoIdentificacionAportante as regTipoIdentificacionAportante,
					magNumeroIdentificacionAportante as regNumeroIdentificacionAportante,
					null as regDigVerAportante, magPeriodoPago as regPeriodoAporte, null as regTipoPlanilla, 
					case when magTipoAportante = N'EMPLEADOR' then 'B' else null end as regClaseAportante, null as regCodSucursal, null as regNomSucursal, null as regDireccion,
					null as regCodCiudad, null as regCodDepartamento, null as regTelefono, null as regFax, null as regEmail, null as regFechaMatricula, null as regNaturalezaJuridica, 
					case when magTipoAportante = N'PENSIONADO' then 1 else null end as regCantPensionados, null as regModalidadPlanilla, sum(madAporteObligatorio) as regValTotalApoObligatorio, sum(madValorIntereses) as regValorIntMora, 
					magFechaPago as regFechaRecaudo, null as regCodigoEntidadFinanciera, null as regOperadorInformacion, null as regNumeroCuenta, null as regFechaActualizacion, null as regRegistroControl, maaSolicitud as regRegistroControlManual,
					null as regRegistroFControl, 0 as regEsSimulado, N'VIGENTE' as regEstadoEvaluacion, N'RECAUDO_CONCILIADO' as regOUTEstadoArchivo -- Se deja este estado, para que pase por las validaciones de pila. 
					from masivos.SimilacionLogGeneral
					where maaNombreArchivo = @nombreArchivo and idParaJoinRegistroGeneral = @cont and magTipoAportante = 'EMPLEADOR'
					group by magTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magPeriodoPago, magFechaPago, maaSolicitud, idParaJoinRegistroGeneral
					set @cont += 1
				end


			declare @cont2 int = 1
			declare @cant2 int = (select max(idParaJoinRegistroGeneral) from masivos.SimilacionLogGeneral as slg where slg.maaNombreArchivo = @nombreArchivo and slg.magTipoAportante <> 'EMPLEADOR')

			while @cont2 <= @cant2
				begin
					declare @localDate2 datetime = dbo.getLocalDate()
					declare @IdTransaccionMasTab2 as table (id int, idTransact bigInt)
					declare @IdTransaccionMas2 bigInt = 0
					INSERT INTO [staging].[Transaccion] (traFechaTransaccion) 
					output @cont2, inserted.traId
					into @IdTransaccionMasTab2
					select @localDate2
					set @IdTransaccionMas2 = (select idTransact from @IdTransaccionMasTab2 where id = @cont2)
					INSERT INTO [staging].[RegistroGeneral] (regTransaccion, regEsAportePensionados ,regNombreAportante ,regTipoIdentificacionAportante ,regNumeroIdentificacionAportante ,regDigVerAportante ,regPeriodoAporte ,regTipoPlanilla ,regClaseAportante ,regCodSucursal 
					,regNomSucursal ,regDireccion ,regCodCiudad ,regCodDepartamento ,regTelefono ,regFax ,regEmail ,regFechaMatricula ,regNaturalezaJuridica ,regCantPensionados ,regModalidadPlanilla ,regValTotalApoObligatorio 
					,regValorIntMora ,regFechaRecaudo ,regCodigoEntidadFinanciera ,regOperadorInformacion ,regNumeroCuenta ,regFechaActualizacion ,regRegistroControl ,regRegistroControlManual ,regRegistroFControl, regEsSimulado, regEstadoEvaluacion, regOUTEstadoArchivo)
					output @cont2, inserted.regNombreAportante, inserted.regTipoIdentificacionAportante, inserted.regNumeroIdentificacionAportante, inserted.regPeriodoAporte, inserted.regId, @nombreArchivo, inserted.regTransaccion
					into #updateRegistroDetallado
					select 
					@IdTransaccionMas2 as regTransaccion,
					case when magTipoAportante = N'PENSIONADO' then 1 else 0 end as regEsAportePensionados,
					magRazonSocial as regNombreAportante,
					magTipoIdentificacionAportante as regTipoIdentificacionAportante,
					magNumeroIdentificacionAportante as regNumeroIdentificacionAportante,
					null as regDigVerAportante, magPeriodoPago as regPeriodoAporte, null as regTipoPlanilla, 
					case when magTipoAportante = N'EMPLEADOR' then 'B' else null end as regClaseAportante, null as regCodSucursal, null as regNomSucursal, null as regDireccion,
					null as regCodCiudad, null as regCodDepartamento, null as regTelefono, null as regFax, null as regEmail, null as regFechaMatricula, null as regNaturalezaJuridica, 
					case when magTipoAportante = N'PENSIONADO' then 1 else null end as regCantPensionados, null as regModalidadPlanilla, madAporteObligatorio as regValTotalApoObligatorio, madValorIntereses as regValorIntMora, 
					magFechaPago as regFechaRecaudo, null as regCodigoEntidadFinanciera, null as regOperadorInformacion, null as regNumeroCuenta, null as regFechaActualizacion, null as regRegistroControl, maaSolicitud as regRegistroControlManual,
					null as regRegistroFControl, 0 as regEsSimulado, N'VIGENTE' as regEstadoEvaluacion, N'RECAUDO_CONCILIADO' as regOUTEstadoArchivo -- Se deja este estado, para que pase por las validaciones de pila. 
					from masivos.SimilacionLogGeneral
					where maaNombreArchivo = @nombreArchivo and idParaJoinRegistroGeneral = @cont2 and magTipoAportante <> 'EMPLEADOR'
					--group by magTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magPeriodoPago, magFechaPago, maaSolicitud, idParaJoinRegistroGeneral
					set @cont2 += 1
				end
				

				declare @rad varchar(50) = (select top 1 maaNumeroRadicacion from masivos.MasivoArchivo where maaNombreArchivo = @nombreArchivo)

				create table #solicitudManualRegGeneral (agTipoAportante varchar(50), magRazonSocial varchar(250), magTipoIdentificacionAportante varchar(30), magNumeroIdentificacionAportante varchar(30), 
				magPeriodoPago varchar(7), magFechaPago date, maaNumeroRadicacion varchar(50), maaSolId bigInt, maaRegistroGeneral bigInt, idParaJoinRegistroGeneral int)

				update a set a.regIdRegistroGeneral = b.regId, a.regIdTransaccion = b.regIdTransaccion
				output 
				inserted.magTipoAportante, inserted.magRazonSocial, inserted.magTipoIdentificacionAportante, inserted.magNumeroIdentificacionAportante, inserted.magPeriodoPago, 
				inserted.magFechaPago, @rad, null,  inserted.regIdRegistroGeneral, inserted.idParaJoinRegistroGeneral into #solicitudManualRegGeneral
				from masivos.SimilacionLogGeneral as a
				inner join #updateRegistroDetallado as b on a.idParaJoinRegistroGeneral = b.idParaJoinRegistroGeneral and 
				a.magTipoIdentificacionAportante = b.magTipoIdentificacionAportante and a.magNumeroIdentificacionAportante = b.magNumeroIdentificacionAportante	and a.magPeriodoPago = b.magPeriodoPago  
				and a.maaNombreArchivo = b.nombreArchivo and a.magRazonSocial = b.magRazonSocial

				select agTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante,magPeriodoPago,magFechaPago,maaNumeroRadicacion,maaSolId,maaRegistroGeneral
				into #solicitudManualRegGeneral2
				from #solicitudManualRegGeneral as a
				group by agTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante,magPeriodoPago,magFechaPago,maaNumeroRadicacion,maaSolId,maaRegistroGeneral


				;with act as (
				select *, concat(maaNumeroRadicacion,N'_',row_number() over (order by maaRegistroGeneral)) as rad2
				from #solicitudManualRegGeneral2 as a)
				update act set maaNumeroRadicacion = rad2


				insert masivos.solicitudAporteMasivo (magTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante,magPeriodoPago,magFechaPago,maaNumeroRadicacion,maaSolId,maaRegistroGeneral)
				select agTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante,magPeriodoPago,magFechaPago,maaNumeroRadicacion,maaSolId,maaRegistroGeneral 
				from #solicitudManualRegGeneral2 as a
				where not exists (select ms.maaNumeroRadicacion from masivos.solicitudAporteMasivo as ms where ms.maaNumeroRadicacion = a.maaNumeroRadicacion)
				group by agTipoAportante, magRazonSocial, magTipoIdentificacionAportante, magNumeroIdentificacionAportante,magPeriodoPago,magFechaPago,maaNumeroRadicacion,maaSolId,maaRegistroGeneral
			
				--===== Creación de la solicitud 2024-01-30

				declare @solManual as table (solId bigint, rad varchar(50), reg bigInt, origen varchar(250))
				insert @solManual
				execute sp_execute_remote coreReferenceData, N'
					drop table if exists #solMasivoAporte
					drop table if exists #solSimuladoMasivo
					drop table if exists #SolicitudAporteSimuladoMasivo
					create table #SolicitudAporteSimuladoMasivo (soaSolicitudGlobal bigInt, rad varchar(50))
					
					create table #solMasivoAporte (rad varchar(50), reg bigInt, magTipoIdentificacionAportante varchar(30), magNumeroIdentificacionAportante varchar(30), magPeriodoPago varchar(7), 
					magTipoAportante varchar(30), magRazonSocial varchar(250), origen1 varchar(250))
					declare @solicitud varchar(50) = @rad
					declare @query nvarchar(max) = N''
						select maaNumeroRadicacion, maaRegistroGeneral, magTipoIdentificacionAportante, magNumeroIdentificacionAportante, magPeriodoPago, magTipoAportante, magRazonSocial from masivos.solicitudAporteMasivo
						where maaNumeroRadicacion like '' + char(39) + @solicitud + N''%'' + char(39) 
					insert #solMasivoAporte
					execute sp_execute_remote pilaReferenceData, @query
					
					
					select s.solCanalRecepcion,s.solFechaRadicacion,null as solInstanciaProceso,a.rad,s.solUsuarioRadicacion,s.solCajaCorrespondencia,s.solTipoTransaccion,s.solCiudadUsuarioRadicacion,s.solEstadoDocumentacion
					,s.solMetodoEnvio,s.solClasificacion,s.solTipoRadicacion,s.solFechaCreacion,s.solDestinatario,s.solSedeDestinatario,s.solObservacion,s.solCargaAfiliacionMultipleEmpleador,s.solResultadoProceso,s.solDiferenciasCargueActualizacion
					,s.solAnulada, a.reg, a.magTipoAportante, a.magTipoIdentificacionAportante, a.magNumeroIdentificacionAportante, a.magPeriodoPago, a.magRazonSocial
					into #solSimuladoMasivo
					from #solMasivoAporte as a
					inner join dbo.solicitud as s on left(rad, charindex(''_'', rad) - 1) = s.solNumeroRadicacion
					
					
					if (select count(*) from #solSimuladoMasivo as a where not exists (select solNumeroRadicacion from dbo.solicitud as s with (nolock) where s.solNumeroRadicacion = a.rad)) > 0

						begin

							insert dbo.solicitud (solCanalRecepcion,solFechaRadicacion,solInstanciaProceso,solNumeroRadicacion,solUsuarioRadicacion,solCajaCorrespondencia,solTipoTransaccion,solCiudadUsuarioRadicacion,solEstadoDocumentacion
							,solMetodoEnvio,solClasificacion,solTipoRadicacion,solFechaCreacion,solDestinatario,solSedeDestinatario,solObservacion,solCargaAfiliacionMultipleEmpleador,solResultadoProceso,solDiferenciasCargueActualizacion,solAnulada)
							output inserted.solId, inserted.solNumeroRadicacion into #SolicitudAporteSimuladoMasivo
							select solCanalRecepcion,solFechaRadicacion,solInstanciaProceso,rad,solUsuarioRadicacion,solCajaCorrespondencia,''APORTES_MANUALES'' as solTipoTransaccion,solCiudadUsuarioRadicacion,solEstadoDocumentacion
							,solMetodoEnvio,solClasificacion,solTipoRadicacion,solFechaCreacion,solDestinatario,solSedeDestinatario,solObservacion,solCargaAfiliacionMultipleEmpleador,solResultadoProceso,solDiferenciasCargueActualizacion,solAnulada
							from #solSimuladoMasivo as a
							where not exists (select solNumeroRadicacion from dbo.solicitud as s where s.solNumeroRadicacion = a.rad)
							
							
							insert dbo.SolicitudAporte (soaSolicitudGlobal,soaEstadoSolicitud,soaRegistroGeneral,soaNumeroIdentificacion,soaTipoIdentificacion
							,soaNombreAportante,soaPeriodoAporte,soaTipoSolicitante,soaCuentaBancariaRecaudo)
							select b.soaSolicitudGlobal, ''CERRADA'', a.reg, a.magNumeroIdentificacionAportante, a.magTipoIdentificacionAportante
							,a.magRazonSocial, a.magPeriodoPago, a.magTipoAportante, null
							from #solSimuladoMasivo as a
							inner join #SolicitudAporteSimuladoMasivo as b on a.rad = b.rad
					
						end
					


					select a.*, b.reg
					from #SolicitudAporteSimuladoMasivo as a
					inner join #solMasivoAporte as b on a.rad = b.rad
				
				--select * from #solSimuladoMasivo
				', N'@rad varchar(50)', @rad = @rad


				update r set r.regRegistroControlManual = a.solId
				from staging.registroGeneral as r
				inner join @solManual as a on r.regId = a.reg



				begin

					INSERT INTO [staging].[RegistroDetallado] (
					redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
					,redCodDepartamento,redCodMunicipio,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redNovIngreso,redNovRetiro,redNovVSP
					,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL,redDiasCotizados
					,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio,redSalarioIntegral,redHorasLaboradas, redEstadoEvaluacion,redUsuarioAprobadorAporte, redOUTValorMoraCotizante
					)
					select b.regId as redRegistroGeneral, a.madTipoIdentificacionCotizante as redTipoIdentificacionCotizante, a.madNumeroIdentificacionCotizante as redNumeroIdentificacionCotizante, a.madTipoCotizante as redTipoCotizante, 
					null as redCodDepartamento, null as redCodMunicipio,null as redPrimerApellido, null as redSegundoApellido, null as redPrimerNombre, null as redSegundoNombre, a.madNovING as redNovIngreso, a.madNovRET as redNovRetiro, a.madNovVSP as redNovVSP
					,a.madNovVST as redNovVST, a.madNovSLN as redNovSLN, a.madNovIGE as redNovIGE, a.madNovLMA as redNovLMA, a.madNovVACLR as redNovVACLR, a.madNovIRL as redDiasIRL, a.madDiasCotizados as redDiasCotizados
					,a.madSalarioBasico as redSalarioBasico, a.madIbc as redValorIBC, a.madTarifa as redTarifa, a.madAporteObligatorio as redAporteObligatorio
					,null as redSalarioIntegral, a.madHorasLaboradas as redHorasLaboradas ,N'VIGENTE' as redEstadoEvaluacion,N'SISTEMA' as  redUsuarioAprobadorAporte, madValorIntereses
					from masivos.SimilacionLogGeneral as a
					inner join #updateRegistroDetallado as b on a.idParaJoinRegistroGeneral = b.idParaJoinRegistroGeneral and 
					a.magTipoIdentificacionAportante = b.magTipoIdentificacionAportante and a.magNumeroIdentificacionAportante = b.magNumeroIdentificacionAportante	and a.magPeriodoPago = b.magPeriodoPago  
					and a.maaNombreArchivo = b.nombreArchivo and a.magRazonSocial = b.magRazonSocial

				end

				select r.regTransaccion, r.regRegistroControlManual, row_number() over (order by regId) as id
				into #procesaAporte
				from staging.registroGeneral as r
				inner join @solManual as a on r.regId = a.reg

				declare @i int = 1
				declare @c int = (select count(*) from #procesaAporte)

				while @i <= @c
					begin
						
						declare @iIdIndicePlanilla bigInt, @IdTransaccion bigint
						select @IdTransaccion = regTransaccion, @iIdIndicePlanilla = regRegistroControlManual
						from #procesaAporte
						where id = @i

						execute masivos.ASP_Execute_pilaProcesarAporte @iIdIndicePlanilla = @iIdIndicePlanilla, @IdTransaccion = @IdTransaccion

						set @i += 1

					end

			--commit transaction
				select regRegistroControlManual
				from #procesaAporte


			drop table #updateRegistroDetallado

		--==== Retorna el registroGeneral



	end try
	begin catch
		if @@TRANCOUNT > 0  rollback transaction;
		--select 'Se genero error'
		SELECT   
         ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_SEVERITY() AS ErrorSeverity  
        ,ERROR_STATE() AS ErrorState  
        ,ERROR_LINE () AS ErrorLine  
        ,ERROR_PROCEDURE() AS ErrorProcedure  
        ,ERROR_MESSAGE() AS ErrorMessage; 
	end catch
end