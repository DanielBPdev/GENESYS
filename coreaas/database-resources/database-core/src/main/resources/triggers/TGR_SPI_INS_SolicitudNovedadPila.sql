-- =============================================
-- Author: rcastillo
-- Create date: 2024-08-23
-- Description: Trigger para realizar la novedad de cambio de sucursal dentro del ciclo de la solicitud. 
-- =============================================
CREATE OR ALTER TRIGGER [dbo].[TGR_SPI_INS_SolicitudNovedadPila]
   ON  [dbo].[SolicitudNovedadPila]
   AFTER INSERT
AS 
BEGIN

	SET NOCOUNT ON;

		declare @roaId bigInt = -2
		declare @snoId bigInt = -4
		declare @apdRegistroDetallado bigInt = -4
		declare @empId bigInt = -4
		declare @afiId bigInt = -4
		declare @aplicaSucursal bigInt = -1

		select @snoId = spiSolicitudNovedad, @apdRegistroDetallado = spiRegistroDetallado
		from inserted

		declare @pilaEmpTra as table (regTipoIdentificacionAportante varchar(25), regNumeroIdentificacionAportante varchar(25), redTipoIdentificacionCotizante varchar(25), redNumeroIdentificacionCotizante varchar(25), ori varchar(250))
		insert @pilaEmpTra
		execute sp_execute_remote pilaReferenceData, N'
		select top 1 r.regTipoIdentificacionAportante, r.regNumeroIdentificacionAportante, rd.redTipoIdentificacionCotizante, rd.redNumeroIdentificacionCotizante
		from staging.RegistroGeneral as r with (nolock)
		inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
		where rd.redId = @apdRegistroDetallado', N'@apdRegistroDetallado bigInt', @apdRegistroDetallado = @apdRegistroDetallado

		select top 1 @empId = em.empId
		from dbo.Persona as p with (nolock)
		inner join dbo.Empresa as e with (nolock) on p.perId = e.empPersona
		inner join dbo.Empleador as em with (nolock) on e.empId = em.empEmpresa
		where exists (select 1 from @pilaEmpTra as a where p.perTipoIdentificacion = a.regTipoIdentificacionAportante and p.perNumeroIdentificacion = a.regNumeroIdentificacionAportante)

		select top 1 @afiId = a.afiId
		from dbo.Persona as p with (nolock)
		inner join dbo.Afiliado as a with (nolock) on p.perId = a.afiPersona
		where exists (select 1 from @pilaEmpTra as a where p.perTipoIdentificacion = a.redTipoIdentificacionCotizante and p.perNumeroIdentificacion = a.redNumeroIdentificacionCotizante)


		select @aplicaSucursal = s.solId
		from dbo.Solicitud as s with (nolock)
		inner join dbo.SolicitudNovedad as sn with (nolock) on s.solId = sn.snoSolicitudGlobal
		where s.solTipoTransaccion = N'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB'
		and sn.snoId = @snoId


		if @aplicaSucursal > 0
			begin
				
				declare @suc bigInt = null
				select @roaId = r.roaId, @suc = se.sueId
				from dbo.RolAfiliado as r with (nolock) 
				inner join dbo.Empleador as em with (nolock) on em.empId = r.roaEmpleador
				inner join dbo.SucursalEmpresa se with (nolock) on em.empEmpresa = se.sueEmpresa
				where r.roaAfiliado = @afiId and em.empId = @empId
				and (isnull(em.empValidarSucursalPila, 0) = 1 and se.sueId != r.roaSucursalEmpleador)

				update dbo.RolAfiliado set roaSucursalEmpleador = @suc where roaId = @roaId

				declare @Revision bigInt
				declare @revTimeStamp bigInt = DATEDIFF_BIG(ms, '1969-12-31 19:00:00', dbo.getLocalDate())
				insert aud.Revision (revTimeStamp) values (@revTimeStamp)
				select @Revision = SCOPE_IDENTITY()
				insert aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) values (N'com.asopagos.entidades.ccf.personas.RolAfiliado_sucursal',1,@Revision)

				insert aud.RolAfiliado_aud (roaId,REV,REVTYPE,roaCargo,roaClaseIndependiente,roaClaseTrabajador,roaEstadoAfiliado,roaEstadoEnEntidadPagadora,roaFechaIngreso,roaFechaRetiro,roaHorasLaboradasMes,roaIdentificadorAnteEntidadPagadora,roaPorcentajePagoAportes
				,roaTipoAfiliado,roaTipoContrato,roaTipoSalario,roaValorSalarioMesadaIngresos,roaAfiliado,roaEmpleador,roaPagadorAportes,roaPagadorPension,roaSucursalEmpleador,roaFechaAfiliacion,roaMotivoDesafiliacion,roaSustitucionPatronal,roaFechaFinPagadorAportes
				,roaFechaFinPagadorPension,roaEstadoEnEntidadPagadoraPension,roaDiaHabilVencimientoAporte,roaMarcaExpulsion,roaEnviadoAFiscalizacion,roaMotivoFiscalizacion,roaFechaFiscalizacion,roaOportunidadPago,roaCanalReingreso,roaReferenciaAporteReingreso
				,roaReferenciaSolicitudReingreso,roaFechaFinContrato,roaMunicipioDesempenioLabores,roaFechaFinCondicionVeterano,roaFechaInicioCondicionVeterano)
				select roaId,@Revision as REV,1 as REVTYPE,roaCargo,roaClaseIndependiente,roaClaseTrabajador,roaEstadoAfiliado,roaEstadoEnEntidadPagadora,roaFechaIngreso,roaFechaRetiro,roaHorasLaboradasMes,roaIdentificadorAnteEntidadPagadora,roaPorcentajePagoAportes
				,roaTipoAfiliado,roaTipoContrato,roaTipoSalario,roaValorSalarioMesadaIngresos,roaAfiliado,roaEmpleador,roaPagadorAportes,roaPagadorPension,@suc as roaSucursalEmpleador,roaFechaAfiliacion,roaMotivoDesafiliacion,roaSustitucionPatronal,roaFechaFinPagadorAportes
				,roaFechaFinPagadorPension,roaEstadoEnEntidadPagadoraPension,roaDiaHabilVencimientoAporte,roaMarcaExpulsion,roaEnviadoAFiscalizacion,roaMotivoFiscalizacion,roaFechaFiscalizacion,roaOportunidadPago,roaCanalReingreso,roaReferenciaAporteReingreso
				,roaReferenciaSolicitudReingreso,roaFechaFinContrato,roaMunicipioDesempenioLabores,roaFechaFinCondicionVeterano,roaFechaInicioCondicionVeterano
				from dbo.rolAfiliado with (nolock)
				where roaId = @roaId

			end
END