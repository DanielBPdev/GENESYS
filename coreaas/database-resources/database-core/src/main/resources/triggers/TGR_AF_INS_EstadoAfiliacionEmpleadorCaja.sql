-- =============================================
-- Author: Robinson Castillo
-- Create date: 2022/12/16
-- Description: Actualizar la fecha del empleador para los ingresos por 0 trabajadores
-- =============================================
CREATE OR ALTER TRIGGER [dbo].[TGR_AF_INS_EstadoAfiliacionEmpleadorCaja]
   ON  [core].[dbo].[EstadoAfiliacionEmpleadorCaja]
   AFTER INSERT
AS 
BEGIN

	SET NOCOUNT ON;

		declare @emplId bigInt;
		declare @fechaNew datetime;
		declare @ideaec bigInt;
		declare @eecPersona bigInt;
		declare @snoId bigInt;
		declare @redId bigInt;

		select top 1 @emplId = em.empId, @ideaec = eecId, @eecPersona = eecPersona
		from inserted as eaec
		inner join core.dbo.Persona as p with (nolock) on  p.perId = eaec.eecPersona
		inner join core.dbo.Empresa as e  with (nolock) on p.perId = e.empPersona
		inner join core.dbo.Empleador as em with (nolock) on em.empEmpresa = e.empId
		where eaec.eecEstadoAfiliacion = 'ACTIVO' and eaec.eecMotivoDesafiliacion is null and (eecNumeroTrabajadores > 0 or em.empNumeroTotalTrabajadores > 0)

		
		select @fechaNew = DATEADD(MONTH, -1, DATEADD(day , 1 , EOMONTH(max(roaFechaAfiliacion)))), @snoId = max(sn.snoId)
		from core.dbo.RolAfiliado as r with (nolock)
		inner join core.dbo.SolicitudNovedadPersona as snp with (nolock) on r.roaId = snp.snpRolAfiliado
		inner join core.dbo.SolicitudNovedad as sn with (nolock) on sn.snoId = snp.snpSolicitudNovedad
		inner join core.dbo.solicitud as s with (nolock) on s.solId = sn.snoSolicitudGlobal
		where roaEmpleador = @emplId and solCanalRecepcion in ('PILA', 'APORTE_MANUAL') and s.solTipoTransaccion = 'NOVEDAD_REINTEGRO' and s.solResultadoProceso = 'APROBADA'
		group by roaEmpleador;

		select @redId = spiRegistroDetallado from SolicitudNovedadPila with  (nolock) where spiSolicitudNovedad = @snoId

		declare @tablaCantTrab table (total int, origen varchar(250))
		insert @tablaCantTrab
		execute sp_execute_remote pilaReferenceData, N'
		declare @regId bigInt = (select redRegistroGeneral from staging.registroDetallado where redId = @redId)
		select count(*)
		from pila.staging.RegistroGeneral as r with (nolock)
		inner join pila.staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
		inner join pila.staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
		where r.regId = @regId
		and rdn.rdnTipoNovedad = ''NOVEDAD_ING''
		and rdn.rdnAccionNovedad = ''APLICAR_NOVEDAD''
		group by r.regId', N'@redId bigInt', @redId = @redId

		declare @totalTra int = (select total from @tablaCantTrab)

		
		if @ideaec is not null and @fechaNew is not null
			begin
				update core.dbo.EstadoAfiliacionEmpleadorCaja set eecFechaCambioEstado = @fechaNew, eecNumeroTrabajadores = @totalTra where eecId = @ideaec;
				update core.dbo.empleador set empFechaCambioEstadoAfiliacion = @fechaNew, empFechaGestionDesafiliacion = null where empId = @emplId;

				begin
					drop table if exists #idBorrado
					declare @eecIdBorrar bigInt
					;with act as (select *, ROW_NUMBER() over (order by eecId) as idAct
					from EstadoAfiliacionEmpleadorCaja with (nolock)
					where eecPersona = @eecPersona),
					act2 as (select *
					,(iif(@ideaec = eecId,idAct-1,null)) as regControl
					from act)
					select *, case when idAct = LEAD(regControl) over (order by idAct) and eecEstadoAfiliacion in ('NO_FORMALIZADO_RETIRADO_CON_APORTES')  
						and eecMotivoDesafiliacion in ('CERO_TRABAJADORES_NOVEDAD_INTERNA','CERO_TRABAJADORES_SOLICITUD_EMPLEADOR') and eecNumeroTrabajadores is null then eecId else null end as eecIdBorrar
					into #idBorrado
					from act2
					
					select @eecIdBorrar = eecIdBorrar
					from #idBorrado
					where eecIdBorrar is not null
					
					if @eecIdBorrar is not null
						begin
							delete from EstadoAfiliacionEmpleadorCaja where eecId = @eecIdBorrar
						end
				end
			end
END