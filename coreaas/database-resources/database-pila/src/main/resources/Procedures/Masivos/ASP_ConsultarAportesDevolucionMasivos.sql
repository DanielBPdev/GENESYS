-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-10-17
-- Description: Objeto encargado de crear las devoluciones masivas a nivel general
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ConsultarAportesDevolucionMasivos]
(
	 @solicitud varchar (25)
)
AS
BEGIN

    SET NOCOUNT ON

			declare @coreAportes as table (perTipoIdentificacion varchar(25), perNumeroIdentificacion varchar(25), perRazonSocial varchar(200), perPrimerNombre varchar(50), perPrimerApellido varchar(50), 
			apgId bigInt, apgFechaRecaudo date, apgModalidadPlanilla varchar(50), apgApoConDetalle varchar(2), apgRegistroGeneral bigInt, apgPeriodoAporte varchar(7), tieneModificaciones varchar(15), 
			apgValTotalApoObligatorio numeric(19,5), apgValorIntMora numeric(19,5), totalAporte numeric(19,5), apgEstadoAporteAportante varchar(40), apgModalidadRecaudoAporte varchar(30), soaEstadoSolicitud varchar(40),
			moficaciones varchar(2), origen varchar(250))
			
			insert @coreAportes
		
			execute sp_execute_remote coreReferenceData, N' 
			
			declare @devAportesMasivos as table (tipoAportante varchar(20), tipoIdAportante varchar(25), numIdAportante varchar(25), periodo varchar(7), devTodo bit, origen varchar(250))
			declare @query1 nvarchar(max) = N''select mad.mgdTipoAportante, mad.mgdTipoIdentificacionAportante, mad.mgdNumeroIdentificacionAportante, mad.mgdPeriodoPago, case when mad.mgdPeriodoPago is null then 1 else 0 end as devolverTodo
			from masivos.MasivoArchivo ma
			JOIN masivos.MasivoGeneralDevolucion mad on mad.mgdMasivoArchivo = ma.maaId
			where maaNumeroRadicacion = ''  + char(39) + @solicitud + char(39) 
			insert @devAportesMasivos
			execute sp_execute_remote pilaReferenceData, @query1
			
			
			;with empleDev as (
				select e.empId, a.devTodo, a.periodo, p.perTipoIdentificacion, p.perNumeroIdentificacion, p.perPrimerNombre, p.perPrimerApellido, p.perRazonSocial
				from @devAportesMasivos as a
				inner join dbo.Persona as p on p.perTipoIdentificacion = a.tipoIdAportante and p.perNumeroIdentificacion = a.numIdAportante
				inner join dbo.Empresa as e on e.empPersona = p.perId
				where tipoAportante = ''EMPLEADOR''
			),
			indPenDev as (
				select p.perId, a.devTodo, a.periodo, p.perTipoIdentificacion, p.perNumeroIdentificacion, p.perPrimerNombre, p.perPrimerApellido, p.perRazonSocial
				from @devAportesMasivos as a
				inner join dbo.Persona as p on p.perTipoIdentificacion = a.tipoIdAportante and p.perNumeroIdentificacion = a.numIdAportante
				where tipoAportante <> ''EMPLEADOR''
			),
			emplDevPer as (
				select e.perTipoIdentificacion, e.perNumeroIdentificacion, e.perRazonSocial, e.perPrimerNombre, e.perPrimerApellido,
				apg.apgId, apg.apgFechaRecaudo, apg.apgModalidadPlanilla, apg.apgApoConDetalle, apgRegistroGeneral,  apgPeriodoAporte, null as tieneModificaciones
				,apg.apgValTotalApoObligatorio, apg.apgValorIntMora, (apg.apgValTotalApoObligatorio + apg.apgValorIntMora) as totalAporte, apg.apgEstadoAporteAportante, apg.apgModalidadRecaudoAporte
				from empleDev as e
				inner join dbo.AporteGeneral as apg on e.empId = apg.apgEmpresa and e.periodo = apg.apgPeriodoAporte
				where e.devTodo = 0
			),
			emplDevAll as (
				select e.perTipoIdentificacion, e.perNumeroIdentificacion, e.perRazonSocial, e.perPrimerNombre, e.perPrimerApellido,
				apg.apgId, apg.apgFechaRecaudo, apg.apgModalidadPlanilla, apg.apgApoConDetalle, apgRegistroGeneral,  apgPeriodoAporte, null as tieneModificaciones
				,apg.apgValTotalApoObligatorio, apg.apgValorIntMora, (apg.apgValTotalApoObligatorio + apg.apgValorIntMora) as totalAporte, apg.apgEstadoAporteAportante, apg.apgModalidadRecaudoAporte
				from empleDev as e
				inner join dbo.AporteGeneral as apg on e.empId = apg.apgEmpresa
				where e.devTodo = 1
				and apg.apgEstadoRegistroAporteAportante = ''RELACIONADO''
			),
			indPenPer as (
				select e.perTipoIdentificacion, e.perNumeroIdentificacion, e.perRazonSocial, e.perPrimerNombre, e.perPrimerApellido,
				apg.apgId, apg.apgFechaRecaudo, apg.apgModalidadPlanilla, apg.apgApoConDetalle, apgRegistroGeneral,  apgPeriodoAporte, null as tieneModificaciones
				,apg.apgValTotalApoObligatorio, apg.apgValorIntMora, (apg.apgValTotalApoObligatorio + apg.apgValorIntMora) as totalAporte, apg.apgEstadoAporteAportante, apg.apgModalidadRecaudoAporte
				from indPenDev as e
				inner join dbo.AporteGeneral as apg on e.perId = apg.apgPersona and e.periodo = apg.apgPeriodoAporte
				where e.devTodo = 0
			), 
			indPenAll as (
				select e.perTipoIdentificacion, e.perNumeroIdentificacion, e.perRazonSocial, e.perPrimerNombre, e.perPrimerApellido,
				apg.apgId, apg.apgFechaRecaudo, apg.apgModalidadPlanilla, apg.apgApoConDetalle, apgRegistroGeneral,  apgPeriodoAporte, null as tieneModificaciones
				,apg.apgValTotalApoObligatorio, apg.apgValorIntMora, (apg.apgValTotalApoObligatorio + apg.apgValorIntMora) as totalAporte, apg.apgEstadoAporteAportante, apg.apgModalidadRecaudoAporte
				from indPenDev as e
				inner join dbo.AporteGeneral as apg on e.perId = apg.apgPersona
				where e.devTodo = 1
				and apg.apgEstadoRegistroAporteAportante = ''RELACIONADO''
			),
			aportesAllDev as (
			select perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,perPrimerNombre,perPrimerApellido,apgId,apgFechaRecaudo,apgModalidadPlanilla,apgApoConDetalle,apgRegistroGeneral,apgPeriodoAporte,tieneModificaciones,apgValTotalApoObligatorio,apgValorIntMora,totalAporte
			, apgEstadoAporteAportante, apgModalidadRecaudoAporte from emplDevPer
			union all
			select perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,perPrimerNombre,perPrimerApellido,apgId,apgFechaRecaudo,apgModalidadPlanilla,apgApoConDetalle,apgRegistroGeneral,apgPeriodoAporte,tieneModificaciones,apgValTotalApoObligatorio,apgValorIntMora,totalAporte 
			, apgEstadoAporteAportante, apgModalidadRecaudoAporte from emplDevAll
			union all 
			select perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,perPrimerNombre,perPrimerApellido,apgId,apgFechaRecaudo,apgModalidadPlanilla,apgApoConDetalle,apgRegistroGeneral,apgPeriodoAporte,tieneModificaciones,apgValTotalApoObligatorio,apgValorIntMora,totalAporte 
			, apgEstadoAporteAportante, apgModalidadRecaudoAporte from indPenPer
			union all 
			select perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,perPrimerNombre,perPrimerApellido,apgId,apgFechaRecaudo,apgModalidadPlanilla,apgApoConDetalle,apgRegistroGeneral,apgPeriodoAporte,tieneModificaciones,apgValTotalApoObligatorio,apgValorIntMora,totalAporte 
			, apgEstadoAporteAportante, apgModalidadRecaudoAporte from indPenAll
			),
			solAporteManual as (
			select sa.soaRegistroGeneral, sa.soaEstadoSolicitud
			from dbo.Solicitud as s
			inner join dbo.SolicitudAporte as sa on s.solId = sa.soaSolicitudGlobal 
			where isnull(sa.soaEstadoSolicitud,'''') = ''CERRADA'' and isnull(s.solResultadoProceso,'''') = ''APROBADA''
			and exists (select 1 from aportesAllDev where sa.soaRegistroGeneral = apgRegistroGeneral)
			),
			modificaciones as (
			select apgx.apgId,
			case when COUNT(distinct moa.moaId) >= 1 then ''SI'' else ''NO'' end as ''valor''
			from dbo.AporteGeneral as apgx (nolock)
			inner join dbo.MovimientoAporte as moa (nolock) on apgx.apgId = moa.moaAporteGeneral
			where moa.moaTipoAjuste is not null 
			and exists (select 1 from aportesAllDev where apgx.apgId = apgRegistroGeneral)
			group by apgx.apgId
			)
			select a.*, b.soaEstadoSolicitud, m.valor
			from aportesAllDev as a
			left join solAporteManual as b on a.apgRegistroGeneral = b.soaRegistroGeneral
			left join modificaciones as m on a.apgId = m.apgId
			', N'@solicitud varchar(25)', @solicitud = @solicitud
			
			

			insert masivos.aportesDevolucion (apgId
				,apgFechaRecaudo
				,apgModalidadPlanilla
				,apgApoConDetalle
				,pipIdPlanilla
				,estadoArchivo
				,tipoArchivoDetalle
				,regTipoPlanilla
				,apgPeriodoAporte
				,tieneModificaciones
				,apgValTotalApoObligatorio
				,apgValorIntMora
				,totalAporte
				,perTipoIdentificacion
				,perNumeroIdentificacion
				,perRazonSocial
				,perPrimerNombre
				,perPrimerApellido
				,numeroRadicado
				,a.apgEstadoAporteAportante
				,a.apgModalidadRecaudoAporte
				,a.soaEstadoSolicitud)

			select
			a.apgId 
			,a.apgFechaRecaudo
			,a.apgModalidadPlanilla
			,a.apgApoConDetalle
			,p.pipIdPlanilla
			,case when r.regRegistroControl is not null then p.pipEstadoArchivo else r.regOUTEstadoArchivo end as estadoArchivo
			,case when p.pipTipoArchivo = 'ARCHIVO_OI_I' then substring(reverse(p.pipTipoArchivo),1,1)
			 when p.pipTipoArchivo = 'ARCHIVO_OI_IR' then reverse(substring(reverse(p.pipTipoArchivo),1,2))
			 when p.pipTipoArchivo = 'ARCHIVO_OI_IP' then reverse(substring(reverse(p.pipTipoArchivo),1,2))
			 when p.pipTipoArchivo = 'ARCHIVO_OI_IPR' then reverse(substring(reverse(p.pipTipoArchivo),1,3))
			 else null 
			 end as tipoArchivoDetalle
			,r.regTipoPlanilla
			,a.apgPeriodoAporte
			,a.moficaciones as tieneModificaciones
			,a.apgValTotalApoObligatorio
			,a.apgValorIntMora
			,a.totalAporte
			,a.perTipoIdentificacion
			,a.perNumeroIdentificacion
			,a.perRazonSocial
			,a.perPrimerNombre
			,a.perPrimerApellido
			,@solicitud as numeroRadicado
			,a.apgEstadoAporteAportante
			,a.apgModalidadRecaudoAporte
			,a.soaEstadoSolicitud
			from @coreAportes as a
			inner join staging.RegistroGeneral as r on a.apgRegistroGeneral = r.regId
			left join dbo.PilaIndicePlanilla as p on r.regRegistroControl = p.pipId
			
			/*
			create table masivos.aportesDevolucion (apgId bigInt, 
			apgFechaRecaudo date, 
			apgModalidadPlanilla varchar(50), 
			apgApoConDetalle varchar(2), 
			pipIdPlanilla bigInt, 
			estadoArchivo varchar(30), 
			tipoArchivoDetalle varchar(3), 
			regTipoPlanilla varchar(2), 
			apgPeriodoAporte varchar(7), 
			tieneModificaciones varchar(2), 
			apgValTotalApoObligatorio numeric(19,5), 
			apgValorIntMora numeric(19,5), 
			totalAporte numeric(19,5), 
			perTipoIdentificacion varchar(25), 
			perNumeroIdentificacion varchar(25), 
			perRazonSocial varchar(70), 
			perPrimerNombre varchar(50), 
			perPrimerApellido varchar(50), 
			numeroRadicado varchar(25), 
			apgEstadoAporteAportante varchar(40), 
			apgModalidadRecaudoAporte varchar(30), 
			soaEstadoSolicitud varchar(40))
			*/

END