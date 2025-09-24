-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2023-10-17
-- Description: Objeto encargado de crear los detalles para las devoluciones masivas. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ConsultarAportesDevolucionMasivosDetalle]
(
	 @numeroRadicado varchar (25)
)
AS
BEGIN

    SET NOCOUNT ON

		declare @aportDet as table (numeroRadicado varchar(25), apgId bigInt, apgPeriodoAporte varchar(7), apdId bigInt, perTipoIdentificacion varchar(25), perNumeroIdentificacion varchar(25), perPrimerNombre varchar(50), perSegundoNombre varchar(50), 
		perPrimerApellido varchar(50), perSegundoApellido varchar(50), apdRegistroDetallado bigInt, apdSalarioBasico numeric(19,5), apdDiasCotizados int, apdTarifa numeric(19,5), tieneSub bit, tieneMod varchar(2), origen varchar(250))
		insert @aportDet
		execute sp_execute_remote coreReferenceData, N'
		
		declare @query2 nvarchar(max) = N''select apgId, numeroRadicado, apgPeriodoAporte
		from masivos.aportesDevolucion
		where numeroRadicado = '' + char(39) + @numeroRadicado + char(39)
		declare @aportesDev as table (apgId bigInt, numeroRadicado varchar(25), apgPeriodoAporte varchar(7), origen varchar(250))
		insert @aportesDev
		execute sp_execute_remote pilaReferenceData, @query2
		
		;with devDetalles as (
		select apg.numeroRadicado, apg.apgId, apg.apgPeriodoAporte, apd.apdId, p.perTipoIdentificacion, p.perNumeroIdentificacion, p.perPrimerNombre,p.perSegundoNombre,p.perPrimerApellido,p.perSegundoApellido, apd.apdRegistroDetallado, apd.apdSalarioBasico, apd.apdDiasCotizados, apd.apdTarifa
		from @aportesDev as apg
		inner join dbo.AporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
		inner join dbo.Persona as p on apd.apdPersona = p.perId),
		tieneSub as (
		SELECT per.perId, per.perTipoIdentificacion, per.perNumeroIdentificacion
		FROM dbo.Persona per
		INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN dbo.DetalleSubsidioAsignado dsa ON dsa.dsaAfiliadoPrincipal = afi.afiId
		where exists (select * from devDetalles as a where a.perTipoIdentificacion = per.perTipoIdentificacion
			and a.perNumeroIdentificacion = per.perNumeroIdentificacion
			and a.apgPeriodoAporte = CONVERT(VARCHAR(7), dsa.dsaPeriodoLiquidado, 121))
		union all
		SELECT per.perId, per.perTipoIdentificacion, per.perNumeroIdentificacion
		FROM dbo.Persona per
		INNER JOIN dbo.Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN dbo.DetalleSubsidioAsignadoProgramado dpr ON dpr.dprAfiliadoPrincipal = afi.afiId
		where exists (select 1 from devDetalles as a where a.perTipoIdentificacion = per.perTipoIdentificacion
		and a.perNumeroIdentificacion = per.perNumeroIdentificacion
		and a.apgPeriodoAporte = CONVERT(VARCHAR(7), dpr.dprPeriodoLiquidado, 121))
		),
		devTieneMod as (
		select moa.moaAporteGeneral, moa.moaAporteDetallado,
		case when count(distinct moa.moaId) >= 1 then ''SI'' else ''NO'' end as ''valor''
		from dbo.movimientoAporte as moa
		where moa.moaTipoAjuste is not null
		and exists (select 1 from devDetalles as a where a.apgId = moa.moaAporteGeneral and a.apdId = moa.moaAporteDetallado)
		group by moa.moaAporteGeneral, moa.moaAporteDetallado
		)
		select a.*, case when b.perId is not null then 1 else 0 end as tieneSub, m.valor
		from devDetalles as a
		left join tieneSub as b on a.perTipoIdentificacion = b.perTipoIdentificacion and a.perNumeroIdentificacion = b.perNumeroIdentificacion
		left join devTieneMod as m on a.apgId = m.moaAporteGeneral and a.apdId = m.moaAporteDetallado
		group by a.numeroRadicado, a.apgId, a.apgPeriodoAporte, a.apdId, a.perTipoIdentificacion, a.perNumeroIdentificacion, a.perPrimerNombre,a.perSegundoNombre,a.perPrimerApellido,a.perSegundoApellido, a.apdRegistroDetallado, 
		a.apdSalarioBasico, a.apdDiasCotizados, a.apdTarifa, b.perId, m.valor
		', N'@numeroRadicado varchar(25)', @numeroRadicado = @numeroRadicado
		

		insert masivos.aportesDevolucionDetalle (numeroRadicado,apgId,apdId,apgPeriodoAporte, perTipoIdentificacion,perNumeroIdentificacion,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,apdRegistroDetallado
		,apdSalarioBasico,apdDiasCotizados,apdTarifa,redNovIngreso,redNovRetiro,redNovVSP,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redNovSUS,redDiasIRL, tieneSub, tieneMod)
		
		select 
		a.numeroRadicado,a.apgId,a.apdId, a.apgPeriodoAporte, a.perTipoIdentificacion,a.perNumeroIdentificacion,a.perPrimerNombre,a.perSegundoNombre,a.perPrimerApellido,a.perSegundoApellido
		,a.apdRegistroDetallado,a.apdSalarioBasico,a.apdDiasCotizados,a.apdTarifa,rd.redNovIngreso,rd.redNovRetiro,rd.redNovVSP,rd.redNovVST,rd.redNovSLN,rd.redNovIGE,rd.redNovLMA,rd.redNovVACLR,rd.redNovSUS,rd.redDiasIRL
		,a.tieneSub, a.tieneMod
		from @aportDet as a
		inner join staging.registroDetallado as rd on a.apdRegistroDetallado = rd.redId
		
		/*
		create table masivos.aportesDevolucionDetalle (
		numeroRadicado varchar(25), apgId bigInt,apdId bigInt, apgPeriodoAporte varchar(7),  perTipoIdentificacion varchar(25), perNumeroIdentificacion varchar(25), perPrimerNombre varchar(50), perSegundoNombre varchar(50), 
		perPrimerApellido varchar(50), perSegundoApellido varchar(50), apdRegistroDetallado bigInt, apdSalarioBasico numeric(19,5), apdDiasCotizados int, apdTarifa numeric(19,5),
		redNovIngreso varchar(2), redNovRetiro varchar(2), redNovVSP varchar(2), redNovVST varchar(2), redNovSLN varchar(2), redNovIGE varchar(2), redNovLMA varchar(2), redNovVACLR varchar(2), 
		redNovSUS varchar(2), redDiasIRL varchar(2),tieneSub bit, tieneMod varchar(2))
		*/


END
