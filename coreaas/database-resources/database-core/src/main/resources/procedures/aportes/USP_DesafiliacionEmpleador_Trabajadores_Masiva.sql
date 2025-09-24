CREATE OR ALTER PROCEDURE [dbo].[USP_DesafiliacionEmpleador_Trabajadores_Masiva]
(
	@numerRadicacionEmpresa varchar(30)
	,@idEmpledor bigint
)
as 
	SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON  

	begin try
		begin transaction


			drop table if exists #idSol;
			drop table if exists #final;
			drop table if exists #final2;
			drop table if exists #solNovId;
			drop table if exists #Solicitud_aud_tmp;
			drop table if exists #SolicitudNovedad_temp;
			drop table if exists #SolicitudNovedadPersona_temp;
			drop table if exists #Beneficiario_aud_temp;
			drop table if exists #RolAfiliado_aud_temp;

			declare @numerRadicacionEmpresa2 varchar(30) = @numerRadicacionEmpresa + N'_'

			;with rola as (
				select a.afiPersona, r.roaId, r.roaAfiliado, concat(@numerRadicacionEmpresa2, ROW_NUMBER() over (order by r.roaAfiliado)) as Radicado
				from dbo.Afiliado as a
				inner join dbo.RolAfiliado as r on a.afiId = r.roaAfiliado
				inner join dbo.Empleador as em on r.roaEmpleador = em.empId
				where em.empId = @idEmpledor
				and r.roaEstadoAfiliado ='ACTIVO'
			), benis as (
				select b.benPersona, r.roaId, r.roaAfiliado, b.benId, CONCAT(r.Radicado, N'_', ROW_NUMBER() over (partition by r.roaId, r.roaAfiliado order by r.roaAfiliado)) as radFinal, b.benTipoBeneficiario
				from rola as r
				left join dbo.Beneficiario as b on b.benAfiliado = r.roaAfiliado
			), tipoClasi as (
				select *
				from ( values 
				('CONYUGE','INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'),
				('PADRE','INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL'),
				('MADRE','INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL'),
				('HIJO_BIOLOGICO','INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'),
				('HIJASTRO','INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'),
				('HERMANO_HUERFANO_DE_PADRES','INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL'),
				('HIJO_ADOPTIVO','INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL'),
				('BENEFICIARIO_EN_CUSTODIA','INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL'))
				as t (tipoBen, tipoTran)
			)
			select *
			into #final
			from benis
			left join tipoClasi on benis.benTipoBeneficiario = tipoClasi.tipoBen
			
			
			select *
			into #final2
			from (
			select roaId, roaAfiliado, null as benId, concat(@numerRadicacionEmpresa2, ROW_NUMBER() over (order by roaAfiliado)) as radFinal, 'TRABAJADOR_DEPENDIENTE' as clasificacion, 'RETIRO_TRABAJADOR_DEPENDIENTE' as tipoTran
			from dbo.RolAfiliado
			where roaId in (select roaId from #final)
			union
			select roaId,roaAfiliado,benId,radFinal,tipoBen,tipoTran
			from #final
			) as t
			order by t.roaAfiliado
			
			
			;with idsBorrar as (select *, ROW_NUMBER() over (partition by roaAfiliado order by radFinal) as idBorrado
								from #final2)
								delete 
								from idsBorrar
								where clasificacion is null and tipoTran is null
								and not exists (select benAfiliado from Beneficiario where benAfiliado = idsBorrar.roaAfiliado)
			
			declare @idParametrizacionNovedad bigInt
			
			select @idParametrizacionNovedad = novId from ParametrizacionNovedad where novTipoTransaccion='RETIRO_TRABAJADOR_DEPENDIENTE'

			UPDATE empleadoresFaltantesRetiroMoraAportes SET efrmEstadoProceso = 'EN_PROCESO' where efrmIdEmpleador = @idEmpledor;
			
			
			CREATE TABLE #Solicitud_aud_tmp(
							[solId] [bigint] NOT NULL,
							[REV] [bigint] NULL,
							[REVTYPE] [smallint] NULL,
							[solCanalRecepcion] [varchar](21) NULL,
							[solFechaRadicacion] [datetime2](7) NULL,
							[solInstanciaProceso] [varchar](255) NULL,
							[solNumeroRadicacion] [varchar](30) NULL,
							[solUsuarioRadicacion] [varchar](255) NULL,
							[solCajaCorrespondencia] [bigint] NULL,
							[solTipoTransaccion] [varchar](100) NULL,
							[solCiudadUsuarioRadicacion] [varchar](255) NULL,
							[solEstadoDocumentacion] [varchar](50) NULL,
							[solMetodoEnvio] [varchar](20) NULL,
							[solClasificacion] [varchar](48) NULL,
							[solTipoRadicacion] [varchar](20) NULL,
							[solFechaCreacion] [datetime2](7) NULL,
							[solDestinatario] [varchar](255) NULL,
							[solSedeDestinatario] [varchar](2) NULL,
							[solObservacion] [varchar](500) NULL,
							[solCargaAfiliacionMultipleEmpleador] [bigint] NULL,
							[solResultadoProceso] [varchar](30) NULL,
							[solDiferenciasCargueActualizacion] [bigint] NULL,
							[solAnulada] [bit] NULL
						)
			
			CREATE TABLE #SolicitudNovedad_temp(
				[snoId] [bigint] NOT NULL,
				[REV] [bigint] NOT NULL,
				[REVTYPE] [smallint] NULL,
				[snoEstadoSolicitud] [varchar](50) NULL,
				[snoNovedad] [bigint] NULL,
				[snoSolicitudGlobal] [bigint] NULL,
				[snoObservaciones] [varchar](200) NULL,
				[snoCargaMultiple] [bit] NULL)
			
			CREATE TABLE #SolicitudNovedadPersona_temp(
				[snpId] [bigint] NOT NULL,
				[REV] [bigint] NOT NULL,
				[REVTYPE] [smallint] NULL,
				[snpPersona] [bigint] NOT NULL,
				[snpSolicitudNovedad] [bigint] NOT NULL,
				[snpRolAfiliado] [bigint] NULL,
				[snpBeneficiario] [bigint] NULL)
			
				
			--===== Creaci�n de la revisi�n. 
			DECLARE @iRevision BIGINT;
			EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.afiliaciones.SolicitudNovedadPersona', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
			

			insert Solicitud (solCanalRecepcion,solFechaRadicacion,solNumeroRadicacion,solTipoTransaccion,solClasificacion,solFechaCreacion,solResultadoProceso)
				output 
						inserted.solId
						,@iRevision
						,0
						,inserted.solCanalRecepcion
						,inserted.solFechaRadicacion
						,inserted.solInstanciaProceso
						,inserted.solNumeroRadicacion
						,inserted.solUsuarioRadicacion
						,inserted.solCajaCorrespondencia
						,inserted.solTipoTransaccion
						,inserted.solCiudadUsuarioRadicacion
						,inserted.solEstadoDocumentacion
						,inserted.solMetodoEnvio
						,inserted.solClasificacion
						,inserted.solTipoRadicacion
						,inserted.solFechaCreacion
						,inserted.solDestinatario
						,inserted.solSedeDestinatario
						,inserted.solObservacion
						,inserted.solCargaAfiliacionMultipleEmpleador
						,inserted.solResultadoProceso
						,inserted.solDiferenciasCargueActualizacion
						,inserted.solAnulada
						into #Solicitud_aud_tmp
				select 'PRESENCIAL_INT', dbo.GetLocalDate(), radFinal, tipoTran, clasificacion,dbo.GetLocalDate(),'APROBADA'
				from #final2
			
				
			   insert SolicitudNovedad (snoEstadoSolicitud,snoNovedad,snoSolicitudGlobal)
			   output 
					inserted.snoId
					,@iRevision
					,0
					,inserted.snoEstadoSolicitud
					,inserted.snoNovedad
					,inserted.snoSolicitudGlobal
					,inserted.snoObservaciones
					,inserted.snoCargaMultiple
					into #SolicitudNovedad_temp
			   select 'CERRADA', @idParametrizacionNovedad, solId
			   from #Solicitud_aud_tmp
			
			
			   insert dbo.SolicitudNovedadPersona (snpId,snpPersona,snpSolicitudNovedad,snpRolAfiliado,snpBeneficiario)
			      output
					inserted.snpId
					,@iRevision
					,0
					,inserted.snpPersona
					,inserted.snpSolicitudNovedad
					,inserted.snpRolAfiliado
					,inserted.snpBeneficiario
					into #SolicitudNovedadPersona_temp
			   select NEXT VALUE for SEC_consecutivoSnp as snpId, a.afiPersona, sn.snoId, fin2.roaAfiliado, fin2.benId
			   from #SolicitudNovedad_temp as sn
			   inner join dbo.Solicitud as s on sn.snoSolicitudGlobal = s.solId
			   inner join #final2 as fin2 on s.solNumeroRadicacion = fin2.radFinal
			   inner join dbo.Afiliado as a on a.afiId = fin2.roaAfiliado
			   left join Beneficiario as b on fin2.benId = b.benId
			   
			   CREATE TABLE #RolAfiliado_aud_temp(
				[roaId] [bigint] NOT NULL,
				[REV] [bigint] NOT NULL,
				[REVTYPE] [smallint] NULL,
				[roaCargo] [varchar](200) NULL,
				[roaClaseIndependiente] [varchar](50) NULL,
				[roaClaseTrabajador] [varchar](20) NULL,
				[roaEstadoAfiliado] [varchar](8) NULL,
				[roaEstadoEnEntidadPagadora] [varchar](20) NULL,
				[roaFechaIngreso] [date] NULL,
				[roaFechaRetiro] [datetime] NULL,
				[roaHorasLaboradasMes] [smallint] NULL,
				[roaIdentificadorAnteEntidadPagadora] [varchar](15) NULL,
				[roaPorcentajePagoAportes] [numeric](5, 5) NULL,
				[roaTipoAfiliado] [varchar](30) NOT NULL,
				[roaTipoContrato] [varchar](20) NULL,
				[roaTipoSalario] [varchar](10) NULL,
				[roaValorSalarioMesadaIngresos] [numeric](19, 2) NULL,
				[roaAfiliado] [bigint] NOT NULL,
				[roaEmpleador] [bigint] NULL,
				[roaPagadorAportes] [bigint] NULL,
				[roaPagadorPension] [smallint] NULL,
				[roaSucursalEmpleador] [bigint] NULL,
				[roaFechaAfiliacion] [date] NULL,
				[roaMotivoDesafiliacion] [varchar](50) NULL,
				[roaSustitucionPatronal] [bit] NULL,
				[roaFechaFinPagadorAportes] [date] NULL,
				[roaFechaFinPagadorPension] [date] NULL,
				[roaEstadoEnEntidadPagadoraPension] [varchar](20) NULL,
				[roaDiaHabilVencimientoAporte] [smallint] NULL,
				[roaMarcaExpulsion] [varchar](22) NULL,
				[roaEnviadoAFiscalizacion] [bit] NULL,
				[roaMotivoFiscalizacion] [varchar](30) NULL,
				[roaFechaFiscalizacion] [date] NULL,
				[roaOportunidadPago] [varchar](11) NULL,
				[roaCanalReingreso] [varchar](21) NULL,
				[roaReferenciaAporteReingreso] [bigint] NULL,
				[roaReferenciaSolicitudReingreso] [bigint] NULL,
				[roaFechaFinContrato] [date] NULL,
				[roaMunicipioDesempenioLabores] [smallint] NULL)
			

			/*
			UPDATE RolAfiliado SET roaFechaRetiro=dbo.GetLocalDate(),roaFechaAfiliacion=NULL,roaMotivoDesafiliacion='DESAFILIACION_EMPLEADOR', roaEstadoAfiliado='INACTIVO'
			output 
					inserted.roaId
					,@iRevision
					,1
					,inserted.roaCargo
					,inserted.roaClaseIndependiente
					,inserted.roaClaseTrabajador
					,inserted.roaEstadoAfiliado
					,inserted.roaEstadoEnEntidadPagadora
					,inserted.roaFechaIngreso
					,inserted.roaFechaRetiro
					,inserted.roaHorasLaboradasMes
					,inserted.roaIdentificadorAnteEntidadPagadora
					,inserted.roaPorcentajePagoAportes
					,inserted.roaTipoAfiliado
					,inserted.roaTipoContrato
					,inserted.roaTipoSalario
					,inserted.roaValorSalarioMesadaIngresos
					,inserted.roaAfiliado
					,inserted.roaEmpleador
					,inserted.roaPagadorAportes
					,inserted.roaPagadorPension
					,inserted.roaSucursalEmpleador
					,inserted.roaFechaAfiliacion
					,inserted.roaMotivoDesafiliacion
					,inserted.roaSustitucionPatronal
					,inserted.roaFechaFinPagadorAportes
					,inserted.roaFechaFinPagadorPension
					,inserted.roaEstadoEnEntidadPagadoraPension
					,inserted.roaDiaHabilVencimientoAporte
					,inserted.roaMarcaExpulsion
					,inserted.roaEnviadoAFiscalizacion
					,inserted.roaMotivoFiscalizacion
					,inserted.roaFechaFiscalizacion
					,inserted.roaOportunidadPago
					,inserted.roaCanalReingreso
					,inserted.roaReferenciaAporteReingreso
					,inserted.roaReferenciaSolicitudReingreso
					,inserted.roaFechaFinContrato
					,inserted.roaMunicipioDesempenioLabores
					into #RolAfiliado_aud_temp
			where roaId in (select DISTINCT roaId from #final where #final.roaId IS NOT NULL)
		 */

		 declare @inactivaRolAfiliado table (id int identity (1,1), roaId bigInt)
			insert @inactivaRolAfiliado (roaId)
			select distinct roaId  from #final where #final.roaId IS NOT NULL

			declare @con int = 1
			declare @fin int = (select COUNT(*) from @inactivaRolAfiliado)
			
			while @con <= @fin
				begin

					declare @roaId bigInt = (select roaId from @inactivaRolAfiliado where id = @con)
					UPDATE RolAfiliado SET roaFechaRetiro=dbo.GetLocalDate(),roaMotivoDesafiliacion='DESAFILIACION_EMPLEADOR', roaEstadoAfiliado='INACTIVO'
					where roaId = @roaId

					set @con += 1
				end


			insert #RolAfiliado_aud_temp
			   select 
					roaId
					,@iRevision as [REV] 
					,1 as [REVTYPE]
					,roaCargo
					,roaClaseIndependiente
					,roaClaseTrabajador
					,roaEstadoAfiliado
					,roaEstadoEnEntidadPagadora
					,roaFechaIngreso
					,roaFechaRetiro
					,roaHorasLaboradasMes
					,roaIdentificadorAnteEntidadPagadora
					,roaPorcentajePagoAportes
					,roaTipoAfiliado
					,roaTipoContrato
					,roaTipoSalario
					,roaValorSalarioMesadaIngresos
					,roaAfiliado
					,roaEmpleador
					,roaPagadorAportes
					,roaPagadorPension
					,roaSucursalEmpleador
					,roaFechaAfiliacion
					,roaMotivoDesafiliacion
					,roaSustitucionPatronal
					,roaFechaFinPagadorAportes
					,roaFechaFinPagadorPension
					,roaEstadoEnEntidadPagadoraPension
					,roaDiaHabilVencimientoAporte
					,roaMarcaExpulsion
					,roaEnviadoAFiscalizacion
					,roaMotivoFiscalizacion
					,roaFechaFiscalizacion
					,roaOportunidadPago
					,roaCanalReingreso
					,roaReferenciaAporteReingreso
					,roaReferenciaSolicitudReingreso
					,roaFechaFinContrato
					,roaMunicipioDesempenioLabores
					from dbo.RolAfiliado with (nolock)
					where roaId in (select DISTINCT roaId from #final where #final.roaId IS NOT NULL)
			
			create table #Beneficiario_aud_temp(
				[benId] [bigint] NOT NULL,
				[REV] [bigint] NOT NULL,
				[REVTYPE] [smallint] NULL,
				[benEstadoBeneficiarioAfiliado] [varchar](20) NULL,
				[benEstudianteTrabajoDesarrolloHumano] [bit] NULL,
				[benFechaAfiliacion] [date] NULL,
				[benTipoBeneficiario] [varchar](30) NOT NULL,
				[benGrupoFamiliar] [bigint] NULL,
				[benPersona] [bigint] NOT NULL,
				[benAfiliado] [bigint] NOT NULL,
				[benGradoAcademico] [smallint] NULL,
				[benMotivoDesafiliacion] [varchar](70) NULL,
				[benFechaRetiro] [date] NULL,
				[benFechaInicioSociedadConyugal] [date] NULL,
				[benFechaFinSociedadConyugal] [date] NULL,
				[benRolAfiliado] [bigint] NULL,
				[benBeneficiarioDetalle] [bigint] NULL,
				[benOmitirValidaciones] [bit] NULL)
			
			UPDATE Beneficiario SET benFechaRetiro=dbo.GetLocalDate(),benMotivoDesafiliacion='DESAFILIACION_EMPLEADOR', benEstadoBeneficiarioAfiliado='INACTIVO' 
			output
				inserted.benId
				,@iRevision
				,1
				,inserted.benEstadoBeneficiarioAfiliado
				,inserted.benEstudianteTrabajoDesarrolloHumano
				,inserted.benFechaAfiliacion
				,inserted.benTipoBeneficiario
				,inserted.benGrupoFamiliar
				,inserted.benPersona
				,inserted.benAfiliado
				,inserted.benGradoAcademico
				,inserted.benMotivoDesafiliacion
				,inserted.benFechaRetiro
				,inserted.benFechaInicioSociedadConyugal
				,inserted.benFechaFinSociedadConyugal
				,inserted.benRolAfiliado
				,inserted.benBeneficiarioDetalle
				,inserted.benOmitirValidaciones
				into #Beneficiario_aud_temp
			where benId in (select DISTINCT benId from #final f where benId IS NOT NULL
							and (select count(r.roaAfiliado) from RolAfiliado r
							where r.roaAfiliado = f.roaAfiliado
							and r.roaEstadoAfiliado = 'ACTIVO') = 0)
			
			
			
					insert into aud.Solicitud_aud (solId,REV,REVTYPE,solCanalRecepcion,solFechaRadicacion,solInstanciaProceso,solNumeroRadicacion,solUsuarioRadicacion,solCajaCorrespondencia,solTipoTransaccion,solCiudadUsuarioRadicacion,solEstadoDocumentacion
					,solMetodoEnvio,solClasificacion,solTipoRadicacion,solFechaCreacion,solDestinatario,solSedeDestinatario,solObservacion,solCargaAfiliacionMultipleEmpleador,solResultadoProceso,solDiferenciasCargueActualizacion,solAnulada)
					select solId,REV,REVTYPE,solCanalRecepcion,solFechaRadicacion,solInstanciaProceso,solNumeroRadicacion,solUsuarioRadicacion,solCajaCorrespondencia,solTipoTransaccion,solCiudadUsuarioRadicacion,solEstadoDocumentacion
					,solMetodoEnvio,solClasificacion,solTipoRadicacion,solFechaCreacion,solDestinatario,solSedeDestinatario,solObservacion,solCargaAfiliacionMultipleEmpleador,solResultadoProceso,solDiferenciasCargueActualizacion,solAnulada
					from #Solicitud_aud_tmp
			
					insert into aud.SolicitudNovedad_aud (snoId,REV,REVTYPE,snoEstadoSolicitud,snoNovedad,snoSolicitudGlobal,snoObservaciones,snoCargaMultiple)
					select snoId,REV,REVTYPE,snoEstadoSolicitud,snoNovedad,snoSolicitudGlobal,snoObservaciones,snoCargaMultiple
					from #SolicitudNovedad_temp
			
					insert into aud.SolicitudNovedadPersona_aud (snpId,REV,REVTYPE,snpPersona,snpSolicitudNovedad,snpRolAfiliado,snpBeneficiario)
					select snpId,REV,REVTYPE,snpPersona,snpSolicitudNovedad,snpRolAfiliado,snpBeneficiario
					from #SolicitudNovedadPersona_temp
			
			
					INSERT INTO aud.RolAfiliado_aud(roaId ,REV ,REVTYPE ,roaCargo ,roaClaseIndependiente ,roaClaseTrabajador ,roaEstadoAfiliado ,roaEstadoEnEntidadPagadora ,roaFechaIngreso ,roaFechaRetiro ,roaHorasLaboradasMes 
									,roaIdentificadorAnteEntidadPagadora ,roaPorcentajePagoAportes ,roaTipoAfiliado ,roaTipoContrato ,roaTipoSalario ,roaValorSalarioMesadaIngresos ,roaAfiliado ,roaEmpleador 
									,roaPagadorAportes ,roaPagadorPension ,roaSucursalEmpleador ,roaFechaAfiliacion ,roaMotivoDesafiliacion ,roaSustitucionPatronal ,roaFechaFinPagadorAportes ,roaFechaFinPagadorPension 
									,roaEstadoEnEntidadPagadoraPension ,roaDiaHabilVencimientoAporte ,roaMarcaExpulsion ,roaEnviadoAFiscalizacion ,roaMotivoFiscalizacion ,roaFechaFiscalizacion ,roaOportunidadPago 
									,roaCanalReingreso ,roaReferenciaAporteReingreso ,roaReferenciaSolicitudReingreso ,roaFechaFinContrato ,roaMunicipioDesempenioLabores)
					SELECT roaId ,rev ,REVTYPE ,roaCargo ,roaClaseIndependiente ,roaClaseTrabajador ,roaEstadoAfiliado ,roaEstadoEnEntidadPagadora ,roaFechaIngreso ,roaFechaRetiro ,roaHorasLaboradasMes 
									,roaIdentificadorAnteEntidadPagadora ,roaPorcentajePagoAportes ,roaTipoAfiliado ,roaTipoContrato ,roaTipoSalario ,roaValorSalarioMesadaIngresos ,roaAfiliado ,roaEmpleador 
									,roaPagadorAportes ,roaPagadorPension ,roaSucursalEmpleador ,roaFechaAfiliacion ,roaMotivoDesafiliacion ,roaSustitucionPatronal ,roaFechaFinPagadorAportes ,roaFechaFinPagadorPension 
									,roaEstadoEnEntidadPagadoraPension ,roaDiaHabilVencimientoAporte ,roaMarcaExpulsion ,roaEnviadoAFiscalizacion ,roaMotivoFiscalizacion ,roaFechaFiscalizacion ,roaOportunidadPago 
									,roaCanalReingreso ,roaReferenciaAporteReingreso ,roaReferenciaSolicitudReingreso ,roaFechaFinContrato ,roaMunicipioDesempenioLabores
					FROM #RolAfiliado_aud_temp
			
			
					insert into aud.Beneficiario_aud (benId ,REV ,REVTYPE ,benEstadoBeneficiarioAfiliado ,benEstudianteTrabajoDesarrolloHumano ,benFechaAfiliacion ,benTipoBeneficiario ,benGrupoFamiliar ,benPersona ,benAfiliado 
									,benGradoAcademico ,benMotivoDesafiliacion ,benFechaRetiro ,benFechaInicioSociedadConyugal ,benFechaFinSociedadConyugal ,benRolAfiliado ,benBeneficiarioDetalle ,benOmitirValidaciones)
					select benId ,rev ,REVTYPE ,benEstadoBeneficiarioAfiliado ,benEstudianteTrabajoDesarrolloHumano ,benFechaAfiliacion ,benTipoBeneficiario ,benGrupoFamiliar ,benPersona ,benAfiliado 
									,benGradoAcademico ,benMotivoDesafiliacion ,benFechaRetiro ,benFechaInicioSociedadConyugal ,benFechaFinSociedadConyugal ,benRolAfiliado ,benBeneficiarioDetalle ,benOmitirValidaciones
					from #Beneficiario_aud_temp

					UPDATE empleadoresFaltantesRetiroMoraAportes SET efrmEstadoProceso = 'INACTIVADO' where efrmIdEmpleador = @idEmpledor;
			
			commit transaction
		end try
	begin catch
		if @@TRANCOUNT > 0  rollback transaction;

		declare @msj nvarchar(500), @line int 
		SELECT 
        @line = ERROR_LINE ()
        ,@msj =  ERROR_MESSAGE()
		begin
		INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(),N'Empresa: ' + CONVERT(varchar(20), @idEmpledor) + N' Radicado: ' + @numerRadicacionEmpresa + N' error linea ' + convert(varchar(20),@line) + N' mensaje: ' + @msj, N'Proceso eliminación masiva empresas');
		end

	end catch