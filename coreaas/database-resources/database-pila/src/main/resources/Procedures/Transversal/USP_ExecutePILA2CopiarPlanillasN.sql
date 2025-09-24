-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2022-07-06
-- Description: Este sp filtra las planillas N para solo mover registro C. 
-- 2022-10-20 Se realiza ajuste para encontrar los registros detallados originales y agrupar los C si no tienen un original. 
-- 2022-11-03 Se realiza ajuste, para que la planilla solo pase, cuando el valor de la suma de los aportes sea igual al valor de la planilla. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanillasN]
(
	@pipId bigInt,
    @IdTransaccion2 bigInt,
	@bSimulado bit,
	@sUsuarioProceso varchar(50)
)
AS
BEGIN
	declare @RegistroGeneralId as table (regId bigInt)
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	declare @registrosDetallados table (redId bigInt, redIdRegistro2pila bigInt)
	
	select *
	into #TiposId
	from (
	VALUES
	('RC','REGISTRO_CIVIL'),
	('TI','TARJETA_IDENTIDAD'),
	('CC','CEDULA_CIUDADANIA'),
	('CE','CEDULA_EXTRANJERIA'),
	('PA','PASAPORTE'),
	('CD','CARNE_DIPLOMATICO'),
	('NI','NIT'),
	('SC','SALVOCONDUCTO'),
	('PE','PERM_ESP_PERMANENCIA'),
	('PT','PERM_PROT_TEMPORAL')) as t (TipoIdCorto, TipoIdLargo)
	
	
	select distinct p.pipId, pi1.pi1NumPlanillaAsociada, pi1.pi1FechaPagoAsociado,pi1.pi1IdAportante, tia.TipoIdLargo, p.pipIdPlanilla, 0 fechaPago, pi1.pi1PeriodoAporte --=== Se agrega el periodo del aporte para el log General. 
	into #planillaNBuscarOriginal_sp
	from dbo.PilaIndicePlanilla as p with (nolock)
	inner join dbo.PilaArchivoIRegistro1 as pi1 with (nolock) on p.pipId = pi1.pi1IndicePlanilla
	inner join #TiposId as tia on tia.TipoIdCorto = pi1.pi1TipoDocAportante
	inner join dbo.PilaArchivoIRegistro2 as pi2 with (nolock) on pi2.pi2IndicePlanilla = p.pipId
	inner join #TiposId as tic on tic.TipoIdCorto = pi2.pi2TipoIdCotizante
	where p.pipId = @pipId and pi1.pi1TipoPlanilla = 'N' and pi1.pi1NumPlanillaAsociada is not null and isnull(pi2.pi2Correcciones,'') in ('A', 'C') --and isnull(p.pipEstadoArchivo, '') not in ('ANULADO','RECAUDO_NOTIFICADO')
	
	
	update rn set fechaPago = 1
	from staging.registroGeneral as rOri with (nolock)
	inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
	and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte <> rn.pi1FechaPagoAsociado
	and rOri.regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO'
	
	
	declare @mismoPago bit = (select fechaPago from #planillaNBuscarOriginal_sp)
	
	declare @estadoPlanillaOri varchar(100)
	select @estadoPlanillaOri = rOri.regOUTEstadoArchivo
	from staging.registroGeneral as rOri
	inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
	and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte = rn.pi1FechaPagoAsociado
	where rOri.regRegistroControlManual is null
	
	---==================================================
	---====== Se agregan validaciones para el log de planillas N 2023-11-03
	---==================================================
	declare @idPlanilla bigInt = (select pipIdPlanilla from dbo.PilaIndicePlanilla with(nolock) where pipId = @pipId)
	
	declare @extPilaI1 bit = 0, @extPilaI2 bit = 0, @extPilaI3 bit = 0, @extPilaA1 bit = 0, @pipidArchivoA bigInt, @pipidArchivoI bigInt
	
	select @pipidArchivoI = p.pipId
	from dbo.PilaIndicePlanilla as p with (nolock)
	where p.pipIdPlanilla = @idPlanilla
	and p.pipTipoArchivo like 'ARCHIVO_OI_I%'
	and isnull(p.pipEstadoArchivo,'') <> 'ANULADO' and p.pipUsuario not like '%MIGRACION%'
	
	select @pipidArchivoA = p.pipId
	from dbo.PilaIndicePlanilla as p with (nolock)
	where p.pipIdPlanilla = @idPlanilla
	and p.pipTipoArchivo like 'ARCHIVO_OI_A%'
	and isnull(p.pipEstadoArchivo,'') <> 'ANULADO'
	
	select @extPilaI1 = 1
	from dbo.PilaArchivoIRegistro1 with(nolock)
	where pi1IndicePlanilla = @pipidArchivoI
	
	select top 1 @extPilaI2 = 1
	from dbo.PilaArchivoIRegistro2 with(nolock)
	where pi2IndicePlanilla = @pipidArchivoI
	
	select top 1 @extPilaI3 = 1
	from dbo.PilaArchivoIRegistro3 with(nolock)
	where pi3IndicePlanilla = @pipidArchivoI
	
	
	select top 1 @extPilaA1 = 1
	from dbo.PilaArchivoARegistro1 with(nolock)
	where pa1IndicePlanilla = @pipidArchivoA
	

	 ---------validacion si la planilla original tiene devolucion o correcion 
		--			--debe insertar en el log y no en el detalle ya que no hay diferencias GLPI 85767
		--			declare @pipIdOri1 bigInt
		--			declare @planillaOriginal2 bigInt
		--			declare @regId1 bigInt
		--			declare @registroGeneralConteo smallint

		--			----- asignacion valores
		--			declare @planillaN1 bigInt = 
		--										(select rn.pipIdPlanilla 
		--										from staging.registroGeneral as rOri with (nolock)
		--										inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
		--										and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte = rn.pi1FechaPagoAsociado
		--										where rOri.regRegistroControlManual is null
		--										)

		--			select @pipIdOri1 = p.pipId
		--			from dbo.PilaIndicePlanilla as p with (nolock)
		--			where p.pipIdPlanilla = @planillaN1 and p.pipTipoArchivo like 'ARCHIVO_OI_I%' and p.pipEstadoArchivo <> 'ANULADO' and p.pipUsuario not like '%MIGRACION%'
	
		--			select @planillaOriginal2 = pi1.pi1NumPlanillaAsociada
		--			from dbo.PilaArchivoIRegistro1 as pi1 with (nolock)
		--			where pi1.pi1IndicePlanilla = @pipIdOri1
	
		--			select @regId1 = r.regId
		--			from dbo.PilaIndicePlanilla as p with (nolock)
		--			inner join staging.RegistroGeneral as r on p.pipId = r.regRegistroControl
		--			where p.pipIdPlanilla = @planillaOriginal2
	
		--			select @registroGeneralConteo=count(r.regId)
		--			from dbo.PilaIndicePlanilla as p with (nolock)
		--			inner join staging.RegistroGeneral as r on p.pipId = r.regRegistroControl
		--			where p.pipIdPlanilla =@planillaOriginal2
	
	
	if @extPilaI1 = 0 or  @extPilaI2 = 0 or @extPilaI3 = 0 or @extPilaA1 = 0
	begin
		if @extPilaA1 = 0
			begin
				insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
				select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() , N'No existe persistencia en archio A -->> Escalar al equipo de db' , rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
				from #planillaNBuscarOriginal_sp as rn
			end
		else if @extPilaI3 = 0
			begin
				insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
				select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() , N'No existe persistencia en archio I3 -->> Escalar al equipo de db' , rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
				from #planillaNBuscarOriginal_sp as rn
			end		
		else if @extPilaI2 = 0
			begin
				insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
				select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio I2 -->> Escalar al equipo de db', null, null
			end
		else
			begin
				insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
				select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio I1 -->> Escalar al equipo de db', null, null
			end
	end
	
	---==================================================
	---====== Finaliza validaciones para el log de planillas N 2023-11-03
	--======= Continúa con las demás validaciones. 
	---==================================================
	
	else if @mismoPago = 1
	
			begin
	
				insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
				select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() , concat(N'La fecha de pago entre la planilla N y original no es la misma ', rn.pi1FechaPagoAsociado, N' <> ', rOri.regFechaPagoAporte)
				, rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
				from staging.registroGeneral as rOri with (nolock)
				inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
				and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte <> rn.pi1FechaPagoAsociado
				and rOri.regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO'
				left join dbo.logPlanillasN as l on rn.pipIdPlanilla = L.planillaN and l.Mensaje like 'La fecha de pago entre la planilla N y original no es la misma%'
				where l.planillaN is null
	
			end
	
	--=== Se agrega log 1
	else if isnull(@estadoPlanillaOri,(select top 1 pipEstadoArchivo from dbo.PilaIndicePlanilla where pipIdPlanilla = (select pi1NumPlanillaAsociada from #planillaNBuscarOriginal_sp) and pipTipoArchivo like 'ARCHIVO_OI_I%')) <> 'RECAUDO_NOTIFICADO'
		or (@estadoPlanillaOri is null and @mismoPago = 0)  --=== Aplica para el caso de que la planilla no exista
		begin
	
			insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
			select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() 
			,concat(N'La planilla original no está notificada, estado planilla original ',
			isnull(isnull(@estadoPlanillaOri,(select pipEstadoArchivo from dbo.PilaIndicePlanilla where pipIdPlanilla = (select pi1NumPlanillaAsociada from #planillaNBuscarOriginal_sp) and pipTipoArchivo like 'ARCHIVO_OI_I%')), ' *.* ')
			,N' si es null o vacio la planilla original no existe.') as mensaje, rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
			from #planillaNBuscarOriginal_sp as rn
			left join dbo.logPlanillasN as l on rn.pipIdPlanilla = L.planillaN and l.Mensaje = concat(N'La planilla original no está notificada, estado planilla original ',
			isnull(isnull(@estadoPlanillaOri,(select pipEstadoArchivo from dbo.PilaIndicePlanilla where pipIdPlanilla = (select pi1NumPlanillaAsociada from #planillaNBuscarOriginal_sp) and pipTipoArchivo like 'ARCHIVO_OI_I%')), ' *.* ')
			,N' si es null o vacio la planilla original no existe.')
			where l.planillaN is null
	
		end

	--validacion si la planilla original tiene devolucion o correcion 
	 -- else if  (@registroGeneralConteo>1) and exists (select * from staging.RegistroGeneral where regNumPlanilla=@planillaOriginal2 and regRegistroControlManual is not null)
			 --  begin
	         --  insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
			 --  select @planillaN1,@planillaOriginal2,dbo.GetLocalDate() , N'Planilla original participa en corrección a la baja o devolución, revisar que acción tomar.' , null, null
	--  end
	--===============================================================
	--==== Entramos a validar la planilla en el detalle. 
	--===============================================================
	
	else if exists (select * 
					from staging.registroGeneral as rOri with (nolock)
					inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
					and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte = rn.pi1FechaPagoAsociado
					and rOri.regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO'--=== Se agrega está validación, para garantizar que la planilla original este notificada. 
					and rOri.regRegistroControlManual is null --=== Se agrega validación ya que la planilla puede duplicar cuando tiene devolución o corrección. 2023-09-04
					)
	
		begin
	
	
			begin transaction
	
			begin try
				
					declare @planillaN bigInt = 
												(select rn.pipIdPlanilla 
												from staging.registroGeneral as rOri with (nolock)
												inner join #planillaNBuscarOriginal_sp as rn on rOri.regTipoIdentificacionAportante = rn.TipoIdLargo and rOri.regNumeroIdentificacionAportante = rn.pi1IdAportante 
												and rOri.regNumPlanilla = pi1NumPlanillaAsociada and rOri.regFechaPagoAporte = rn.pi1FechaPagoAsociado
												where rOri.regRegistroControlManual is null --=== Se agrega validación ya que la planilla puede duplicar cuando tiene devolución o corrección. 2023-09-04
												)
											
					declare @pipIdOri bigInt
					declare @planillaOriginal bigInt
					declare @regId bigInt
	
					select @pipIdOri = p.pipId
					from dbo.PilaIndicePlanilla as p with (nolock)
					where p.pipIdPlanilla = @planillaN and p.pipTipoArchivo like 'ARCHIVO_OI_I%' and p.pipEstadoArchivo <> 'ANULADO' and p.pipUsuario not like '%MIGRACION%'
	
					select @planillaOriginal = pi1.pi1NumPlanillaAsociada
					from dbo.PilaArchivoIRegistro1 as pi1 with (nolock)
					where pi1.pi1IndicePlanilla = @pipIdOri
	
					select @regId = r.regId
					from dbo.PilaIndicePlanilla as p with (nolock)
					inner join staging.RegistroGeneral as r on p.pipId = r.regRegistroControl
					where p.pipIdPlanilla = @planillaOriginal and r.regRegistroControlManual is null
	
					--=============================================================
					-- Vamos a validar la cantidad de registros que trae la planilla N
					--=============================================================
					declare @cantA int, @cantC int, @totalAporteA numeric(19,5), @totalAporteC numeric(19,5), @totalAporte3 numeric(19,5), @mora numeric(19,5)
					select @cantA = count(*), @totalAporteA = sum(pi2.pi2AporteObligatorio)
					from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
					where pi2.pi2IndicePlanilla = @pipIdOri and pi2Correcciones = 'A'
	
					select @cantC = count(*), @totalAporteC = sum(pi2.pi2AporteObligatorio)
					from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
					where pi2.pi2IndicePlanilla = @pipIdOri and pi2Correcciones = 'C'
	
					select @totalAporte3 = pi3ValorTotalAporteObligatorio, @mora = pi3.pi3ValorMora
					from dbo.PilaArchivoIRegistro3 as pi3 with (nolock)
					where pi3.pi3IndicePlanilla = @pipIdOri
	
	
					if @cantA = @cantC
						begin
							if @totalAporte3 = (@totalAporteC - @totalAporteA)
								begin
									;with RegA as (
									select pi2.pi2Id as pi2IdA, ti.TipoIdLargo as tipoIdA, pi2.pi2IdCotizante as numIdCotA, pi2.pi2AporteObligatorio as aporteAnt, pi2.pi2ValorIBC as IBCant, 
									row_number() over (partition by pi2.pi2TipoIdCotizante, pi2.pi2IdCotizante order by pi2.pi2DiasCotizados, pi2.pi2ValorIBC, pi2.pi2SalarioBasico, pi2.pi2AporteObligatorio
									, pi2FechaIngreso, pi2FechaRetiro, pi2FechaInicioVSP, pi2FechaInicioSLN, pi2FechaFinSLN, pi2FechaInicioIGE, pi2FechaFinIGE, pi2FechaInicioLMA, pi2FechaFinLMA, pi2FechaInicioVACLR, pi2FechaFinVACLR, pi2FechaInicioVCT, pi2FechaFinVCT, pi2FechaInicioIRL, pi2FechaFinIRL) as idA
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipIdOri
									and pi2.pi2Correcciones = 'A'),
									regC as (
									select pi2.pi2Id as pi2IdC, ti.TipoIdLargo as tipoIdC, pi2.pi2IdCotizante as numIdCotC, pi2.pi2AporteObligatorio as aporteNue, pi2.pi2ValorIBC as IBCnue, 
									row_number() over (partition by pi2.pi2TipoIdCotizante, pi2.pi2IdCotizante order by pi2.pi2DiasCotizados, pi2.pi2ValorIBC, pi2.pi2SalarioBasico, pi2.pi2AporteObligatorio
									, pi2FechaIngreso, pi2FechaRetiro, pi2FechaInicioVSP, pi2FechaInicioSLN, pi2FechaFinSLN, pi2FechaInicioIGE, pi2FechaFinIGE, pi2FechaInicioLMA, pi2FechaFinLMA, pi2FechaInicioVACLR, pi2FechaFinVACLR, pi2FechaInicioVCT, pi2FechaFinVCT, pi2FechaInicioIRL, pi2FechaFinIRL) as idC
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipIdOri
									and pi2.pi2Correcciones = 'C'),
									final as (
									select *, case when a.aporteAnt = c.aporteNue then 0 else 1 end as Modificar
									from RegA as a
									full join regC as c on a.tipoIdA = c.tipoIdC and a.numIdCotA = c.numIdCotC and a.idA = c.idC)
									select *, case when Modificar = 1 then aporteNue - aporteAnt else 0 end as valParaUpdate,
									case when Modificar = 1 then IBCnue - IBCant else 0 end as valParaUpdateIBC,
									sum(case when Modificar = 1 then aporteNue - aporteAnt else 0 end) over (partition by Modificar) as EstaValDebeSerIgualAlMensaje2, @regId as registroGeneral
									into #regValidar
									from final

									--=== Ajuste 2025-01-13
									select *,  
									row_number() over (partition by rd.redTipoCotizante, rd.redNumeroIdentificacionCotizante order by rd.redDiasCotizados, rd.redValorIBC, rd.redSalarioBasico, rd.redAporteObligatorio
									,redFechaIngreso, redFechaRetiro, redFechaInicioVSP, redFechaInicioSLN, redFechaFinSLN, redFechaInicioIGE, redFechaFinIGE, redFechaInicioLMA, redFechaFinLMA, redFechaInicioVACLR, redFechaFinVACLR, redFechaInicioVCT, redFechaFinVCT, redFechaInicioIRL, redFechaFinIRL) as idOrden
									into #RegistroDetallado
									from staging.RegistroDetallado as rd
									where rd.redRegistroGeneral = @regId

					
									select val.pi2IdA, val.pi2IdC, rd.redId as registroDetalladoOriginal, rd.redRegistroGeneral, val.Modificar, val.valParaUpdate, val.valParaUpdateIBC
									into #regValidar2
									from #regValidar as val
									inner join dbo.PilaArchivoIRegistro2 as pi2 on val.pi2IdA = pi2.pi2Id
									inner join #RegistroDetallado as rd on val.tipoIdA = rd.redTipoIdentificacionCotizante and val.numIdCotA = rd.redNumeroIdentificacionCotizante and val.aporteAnt = rd.redAporteObligatorio and val.registroGeneral = rd.redRegistroGeneral and val.idA = rd.idOrden
									and isnull(upper(rtrim(ltrim(rd.redNovIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIngreso))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovRetiro))),'') and isnull(upper(rtrim(ltrim(rd.redNovVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVSP))),'') and isnull(upper(rtrim(ltrim(rd.redNovVST))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVST))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovSLN))),'') and isnull(upper(rtrim(ltrim(rd.redNovIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIGE))),'') and isnull(upper(rtrim(ltrim(rd.redNovLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVACLR))),'') and convert(smallint,isnull(upper(rtrim(ltrim(rd.redDiasIRL))),'0')) = convert(smallint,isnull(upper(rtrim(ltrim(pi2.pi2DiasIRL))),'0')) 
									and isnull(upper(rtrim(ltrim(rd.redDiasCotizados))),'') = isnull(upper(rtrim(ltrim(pi2.pi2DiasCotizados))),'') and rd.redSalarioBasico = pi2.pi2SalarioBasico and rd.redValorIBC = pi2.pi2ValorIBC and rd.redTarifa = pi2.pi2Tarifa and rd.redAporteObligatorio = pi2.pi2AporteObligatorio 
									and isnull(upper(rtrim(ltrim(rd.redFechaIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaIngreso))),'') and isnull(upper(rtrim(ltrim(rd.redFechaRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaRetiro))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaInicioVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVSP))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioSLN))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinSLN))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIGE))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIGE))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinLMA))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVACLR))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVACLR))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVCT))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVCT))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIRL))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIRL))),'')
	
	
									select pi2.pi2Id
									,case when tipCotI.tipoTra = tipCotR.tipoTra then 0 else 1 end as redTipoCotizante
									,case when isnull(upper(rtrim(ltrim(rd.redPrimerApellido))),'') = isnull(upper(rtrim(ltrim(pi2.pi2PrimerApellido))),'')  then 0 else 1 end as pi2PrimerApellido
									,case when isnull(upper(rtrim(ltrim(rd.redSegundoApellido))),'') = isnull(upper(rtrim(ltrim(pi2.pi2SegundoApellido))),'')  then 0 else 1 end as pi2SegundoApellido
									,case when isnull(upper(rtrim(ltrim(rd.redPrimerNombre))),'') = isnull(upper(rtrim(ltrim(pi2.pi2PrimerNombre))),'')  then 0 else 1 end as pi2PrimerNombre
									,case when isnull(upper(rtrim(ltrim(rd.redSegundoNombre))),'') = isnull(upper(rtrim(ltrim(pi2.pi2SegundoNombre))),'') then 0 else 1 end as pi2SegundoNombre
									,case when isnull(upper(rtrim(ltrim(rd.redNovIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIngreso))),'') then 0 else 1 end as pi2NovIngreso
									,case when isnull(upper(rtrim(ltrim(rd.redNovRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovRetiro))),'') then 0 else 1 end as pi2NovRetiro 
									,case when isnull(upper(rtrim(ltrim(rd.redNovVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVSP))),'') then 0 else 1 end as pi2NovVSP
									,case when isnull(upper(rtrim(ltrim(rd.redNovVST))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVST))),'') then 0 else 1 end as pi2NovVST
									,case when isnull(upper(rtrim(ltrim(rd.redNovSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovSLN))),'') then 0 else 1 end as pi2NovSLN
									,case when isnull(upper(rtrim(ltrim(rd.redNovIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIGE))),'') then 0 else 1 end as  pi2NovIGE
									,case when isnull(upper(rtrim(ltrim(rd.redNovLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovLMA))),'') then 0 else 1 end as  pi2NovLMA
									,case when isnull(upper(rtrim(ltrim(rd.redNovVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVACLR))),'') then 0 else 1 end as pi2NovVACLR
									,case when convert(smallint,isnull(upper(rtrim(ltrim(rd.redDiasIRL))),'0')) = convert(smallint,isnull(upper(rtrim(ltrim(pi2.pi2DiasIRL))),'0')) then 0 else 1 end as pi2DiasIRL
									,case when isnull(upper(rtrim(ltrim(rd.redDiasCotizados))),'') = isnull(upper(rtrim(ltrim(pi2.pi2DiasCotizados))),'') then 0 else 1 end as pi2DiasCotizados
									,case when rd.redSalarioBasico = pi2.pi2SalarioBasico then 0 else 1 end as pi2SalarioBasico
									,case when rd.redValorIBC = pi2.pi2ValorIBC then 0 else 1 end as pi2ValorIBC
									,case when rd.redTarifa = pi2.pi2Tarifa then 0 else 1 end as pi2Tarifa
									,case when rd.redAporteObligatorio = pi2.pi2AporteObligatorio then 0 else 1 end as pi2AporteObligatorio
									,0 as pi2SalarioIntegral
									,case when isnull(upper(rtrim(ltrim(rd.redFechaIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaIngreso))),'') then 0 else 1 end as pi2FechaIngreso
									,case when isnull(upper(rtrim(ltrim(rd.redFechaRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaRetiro))),'')  then 0 else 1 end as pi2FechaRetiro
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVSP))),'') then 0 else 1 end as pi2FechaInicioVSP
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioSLN))),'')  then 0 else 1 end as pi2FechaInicioSLN
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinSLN))),'') then 0 else 1 end as pi2FechaFinSLN
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIGE))),'')  then 0 else 1 end as pi2FechaInicioIGE
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIGE))),'') then 0 else 1 end as pi2FechaFinIGE
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioLMA))),'')  then 0 else 1 end as pi2FechaInicioLMA
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinLMA))),'') then 0 else 1 end as pi2FechaFinLMA
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVACLR))),'')  then 0 else 1 end as pi2FechaInicioVACLR
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVACLR))),'') then 0 else 1 end as pi2FechaFinVACLR
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVCT))),'')  then 0 else 1 end as pi2FechaInicioVCT
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVCT))),'') then 0 else 1 end as pi2FechaFinVCT
									,case when isnull(upper(rtrim(ltrim(rd.redFechaInicioIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIRL))),'')  then 0 else 1 end as pi2FechaInicioIRL
									,case when isnull(upper(rtrim(ltrim(rd.redFechaFinIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIRL))),'') then 0 else 1 end as pi2FechaFinIRL
									,rd.redId as registroDetalladoOriginal
									into #tblLog
									from #regValidar as val
									inner join dbo.PilaArchivoIRegistro2 as pi2 on val.pi2IdA = pi2.pi2Id
									inner join staging.StagingTiposCotizantes as tipCotI on pi2.pi2TipoCotizante = tipCotI.tipoCot
									left join #RegistroDetallado as rd on val.tipoIdA = rd.redTipoIdentificacionCotizante and val.numIdCotA = rd.redNumeroIdentificacionCotizante and val.aporteAnt = rd.redAporteObligatorio and val.registroGeneral = rd.redRegistroGeneral and val.idA = rd.idOrden
									left join staging.StagingTiposCotizantes as tipCotR on rd.redTipoCotizante = tipCotR.tipoCot
															
									declare @cantfin int = (select COUNT(*) from #regValidar2 where registroDetalladoOriginal is not null)
									
									if @cantC = @cantfin
										begin  --=================================================
											select pi2.*, val2.registroDetalladoOriginal, val2.Modificar, val2.valParaUpdate, val2.valParaUpdateIBC
											into #PilaArchivoIRegistroC
											from #regValidar2 as val2
											inner join dbo.PilaArchivoIRegistro2 as pi2 on val2.pi2IdC = pi2.pi2Id
	
											--select * from #PilaArchivoIRegistroC
					
											declare @cantFin2 int = (select COUNT(*) from #PilaArchivoIRegistroC)
											declare @ibcMenos bit, @aporMenos bit
											select @aporMenos = case when sum(valParaUpdate) < 0 then 1 else 0 end, @ibcMenos = case when sum(valParaUpdateIBC) < 0 then 1 else 0 end
											from #PilaArchivoIRegistroC
											where modificar = 1

											/*
											declare @regValCore bigInt
											select @regValCore =  redRegistroGeneral
											from staging.RegistroDetallado as rd with (nolock)
											where redId in (select registroDetalladoOriginal from #PilaArchivoIRegistroC)
											group by redRegistroGeneral
											declare @tblValAporteCorDev as table (moaAporteGeneral bigInt, origen varchar(250))
											insert @tblValAporteCorDev
											execute sp_execute_remote coreReferenceData, N'
												select moaAporteGeneral
												from dbo.MovimientoAporte as moa with (nolock)
												inner join dbo.aporteGeneral as apg with (nolock) on apg.apgId = moa.moaAporteGeneral
												where apg.apgRegistroGeneral = @regValCore
												and moaTipoAjuste in (''CORRECCION_A_LA_BAJA'' , ''DEVOLUCION'')
												group by moaAporteGeneral
											', N'@regValCore bigInt', @regValCore = @regValCore
											*/

											--=======================================================================
											--==== Se realiza cambio, para hacer la validación a nivel detalle de si el registro partició en corrección o devolución 2024-05-22
											--=======================================================================

										--declare @tblValAporteCorDev as table (moaAporteDetallado bigInt, origen varchar(250))
											
											--create table #redIds (id int identity(1,1),redId bigint)
											--insert #redIds (redId)
											--select registroDetalladoOriginal
											--from #PilaArchivoIRegistroC

											--declare @i int = 1
											--declare @contIds int = (select count(*) from #redIds)
											--while @i <= @contIds
											--	begin
													
												--	declare @redIs varchar(max) = ''
												--	select @redIs = @redIs + t.id + N',' from (select convert(varchar(50),redId) as id from #redIds where id >= @i and id < @i + 2000) as t
												--	select @redIs = left(@redIs,len(@redIs)-1)
											

											 --         -------------- cambios sbayona calculo planilla original con correccion o devolucion
												--	declare @query nvarchar(max) = ''
												--	set @query = N'
												--		select moa.moaId
												--		from dbo.AporteGeneral as apg 
												--		inner join dbo.AporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
												--		inner join dbo.MovimientoAporte as moa on apd.apdAporteGeneral = moa.moaAporteGeneral
												--		where apd.apdRegistroDetallado in (' + @redIs + N') 
												--		and moaTipoAjuste in (''CORRECCION_A_LA_BAJA'' , ''DEVOLUCION'')
														
												--		union
														
												--		select moa.moaId
												--		from dbo.AporteGeneral as apg 
												--		inner join dbo.AporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
												--		inner join dbo.MovimientoAporte as moa on apd.apdId = moa.moaAporteDetallado
												--		where moa.moaAporteDetallado in (' + @redIs + N') 
												--		and moaTipoAjuste in (''CORRECCION_A_LA_BAJA'' , ''DEVOLUCION'')
							
												--		'
											
												--	insert @tblValAporteCorDev
												--	execute sp_execute_remote coreReferenceData, @query
											
												--	set @i += 2000
												--end

											--=======================================================================
											--==== Se realiza cambio, temina en corrección o devolución 2024-05-22
											--=======================================================================
											
					
											
											--if exists (select 1 from @tblValAporteCorDev)
											--	begin
											--		--select 'Planilla original participa en corrección a la baja o devolución, revisar que acción tomar.'
											--		insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
											--		select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() , N'Planilla original participa en corrección a la baja o devolución, revisar que acción tomar.' , rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
											--		from #planillaNBuscarOriginal_sp as rn
											--	end
											--else 
											if @aporMenos = 1 or @ibcMenos = 1
											begin
												--select 'Hay valoes negativos en la diferencia entre el C y el A'
												insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
												select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() , N'Hay valoes negativos en la diferencia entre el C y el A (aporte o ibc) -->> Revisar los registros.' , rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
												from #planillaNBuscarOriginal_sp as rn
	
											end
											else if @cantC = @cantFin2
												begin
													--select 'Aca hacemos la persistencia de las tablas staging para el flujo de novedades.'
													-- Registro general Dependiente/Independiente
													--INSERT INTO [staging].[RegistroGeneral] (
													insert into staging.RegistroGenCtrl(
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													--OUTPUT INSERTED.regId INTO @RegistroGeneralId
													SELECT @IdTransaccion2, 0 AS regEsAportePensionados,pi1RazonSocial,ti.TipoIdLargo,pi1IdAportante,pi1DigVerAportante
														,pi1PeriodoAporte,pi1TipoPlanilla,pi1ClaseAportante,pi1CodSucursal,pi1NomSucursal,pa1Direccion,pa1CodCiudad
														,pa1CodDepartamento,pa1Telefono,pa1Fax,pa1Email,pa1FechaMatricula,pa1NaturalezaJuridica,pi1ModalidadPlanilla
														,pi3ValorTotalAporteObligatorio,pi3ValorMora,CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112)
														,pf1CodigoEntidadFinanciera,pi1CodOperador,pf5NumeroCuenta,pip.pipEstadoArchivo,pi1FechaActualizacion
														,pi1IndicePlanilla,pf1IndicePlanillaOF,pi1NumPlanilla,@bSimulado
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,pip.pipMotivoProcesoManual,pi1NumPlanillaAsociada,pi1DiasMora,pi1FechaPago,pi1Presentacion,pi1CantidadEmpleados,pi1CantidadAfiliados
														,pi1TipoPersona, 1, pi1CantidadReg2, pi1FechaPagoAsociado,dbo.getLocalDate(),dbo.getLocalDate()
													FROM PilaArchivoIRegistro1 pi1 
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi1.pi1TipoDocAportante
													INNER JOIN PilaIndicePlanilla pip ON pi1.pi1IndicePlanilla = pip.pipId
													INNER JOIN PilaIndicePlanilla pipA ON (
														pip.pipIdPlanilla = pipA.pipIdPlanilla 
														AND ISNULL(pipA.pipEstadoArchivo, '') <> 'ANULADO'
														AND pip.pipTipoArchivo != pipA.pipTipoArchivo
														AND pip.pipCodigoOperadorInformacion = pipA.pipCodigoOperadorInformacion
													)
													INNER JOIN PilaArchivoARegistro1 pa1 ON pa1.pa1IndicePlanilla = pipA.pipId
													INNER JOIN PilaArchivoIRegistro3 pi3 ON pi1.pi1IndicePlanilla = pi3.pi3IndicePlanilla
													LEFT JOIN (
																SELECT MAX(pf6Id) pf6Id, pf6NumeroPlanilla, pf6PeriodoPago, CAST(pf6CodOperadorInformacion AS SMALLINT) pf6CodOperadorInformacion
																FROM dbo.PilaArchivoFRegistro6
																WHERE pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
																GROUP BY pf6IndicePlanillaOF, pf6CodOperadorInformacion,pf6NumeroPlanilla,pf6PeriodoPago
																) pf6 ON pf6.pf6CodOperadorInformacion = pi1.pi1CodOperador 
																	AND CAST(pf6.pf6NumeroPlanilla AS BIGINT) = CAST(pi1.pi1NumPlanilla AS BIGINT) 
																	AND pf6.pf6PeriodoPago = CONVERT(VARCHAR(6),CONVERT(DATETIME,pi1.pi1PeriodoAporte+'-01'),112)
													LEFT JOIN dbo.PilaArchivoFRegistro6 pf6_1 ON pf6.pf6Id = pf6_1.pf6Id
													LEFT JOIN dbo.PilaArchivoFRegistro5 pf5 ON pf6_1.pf6PilaArchivoFRegistro5 = pf5.pf5Id
													LEFT JOIN dbo.PilaArchivoFRegistro1 pf1 ON pf6_1.pf6IndicePlanillaOF = pf1.pf1IndicePlanillaOF
													WHERE pi1IndicePlanilla IN (@pipId)
													AND pi1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] with (nolock) WHERE regRegistroControl IS NOT NULL)

													INSERT INTO [staging].[RegistroGeneral] (
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													OUTPUT INSERTED.regId INTO @RegistroGeneralId
													select 
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate
													from staging.RegistroGenCtrl
													where regRegistroControl = @pipId


													begin 
														---////////*****************************
														---**** CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													;with controlDuplicados as (
													select r.*, a.regId as regId2, DENSE_RANK() over (order by r.regId) as total
													from @RegistroGeneralId as a
													inner join staging.RegistroGeneral as r on r.regId = a.regId
													)
													delete from staging.RegistroGeneral where regId in (select controlDuplicados.regId from controlDuplicados where total > 1)
														---////////*****************************
														---**** TERMINAR CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													end
	
													-- Registro detallado Dependiente/Independiente
													INSERT INTO [staging].[RegistroDetallado] (
														redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
														,redCodDepartamento,redCodMunicipio,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redNovIngreso,redNovRetiro,redNovVSP
														,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL,redDiasCotizados,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio
														,redCorrecciones,redSalarioIntegral,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSLN,redFechaFinSLN,redFechaInicioIGE
														,redFechaFinIGE,redFechaInicioLMA,redFechaFinLMA,redFechaInicioVACLR,redFechaFinVACLR,redFechaInicioVCT,redFechaFinVCT,redFechaInicioIRL
														,redFechaFinIRL,redHorasLaboradas,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte,redOUTValorMoraCotizante,redIdRegistro2pila
														,redOUTRegistroActual,redDateTimeUpdate)
													output inserted.redId, inserted.redIdRegistro2pila into @registrosDetallados
													SELECT regId,ti.TipoIdLargo,pi2.pi2IdCotizante,pi2.pi2TipoCotizante,pi2CodDepartamento
														,pi2CodDepartamento+pi2CodMunicipio,pi2.pi2PrimerApellido,pi2.pi2SegundoApellido,pi2.pi2PrimerNombre,pi2.pi2SegundoNombre,pi2.pi2NovIngreso,pi2.pi2NovRetiro,
														pi2.pi2NovVSP,pi2.pi2NovVST,pi2.pi2NovSLN,pi2.pi2NovIGE,pi2.pi2NovLMA,pi2.pi2NovVACLR,pi2.pi2DiasIRL,pi2.pi2DiasCotizados,pi2.pi2SalarioBasico,
														pi2.pi2ValorIBC, --pi2.valParaUpdateIBC, --== Se deja como ibc la diferencia, cuando hay cambio en el valor del aporte -- 2024-04-15
														pi2.pi2Tarifa,
														pi2.pi2AporteObligatorio--pi2.valParaUpdate --== Se deja como aporte la diferencia para update -- 2024-04-15
														,pi2.pi2Correcciones,pi2.pi2SalarioIntegral,pi2.pi2FechaIngreso,pi2.pi2FechaRetiro,pi2.pi2FechaInicioVSP,
														pi2.pi2FechaInicioSLN,pi2.pi2FechaFinSLN,pi2.pi2FechaInicioIGE,pi2.pi2FechaFinIGE,pi2.pi2FechaInicioLMA,pi2.pi2FechaFinLMA,pi2.pi2FechaInicioVACLR,
														pi2.pi2FechaFinVACLR,pi2.pi2FechaInicioVCT,pi2.pi2FechaFinVCT,pi2.pi2FechaInicioIRL,pi2.pi2FechaFinIRL,pi2.pi2HorasLaboradas,pi2.pi2Secuencia
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,@sUsuarioProceso
														,CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
															(pi2.valParaUpdate * regValorIntMora) / regValTotalApoObligatorio
														END, pi2.pi2Id, 1, @redDateTimeUpdate
													from #PilaArchivoIRegistroC as pi2
													INNER JOIN PilaIndicePlanilla pip ON pi2IndicePlanilla = pipId
													INNER JOIN PilaArchivoIRegistro1 ON pi2IndicePlanilla = PilaArchivoIRegistro1.pi1IndicePlanilla 		
													INNER JOIN staging.RegistroGeneral ON regRegistroControl = pi1IndicePlanilla
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi2.pi2TipoIdCotizante
													WHERE regId IN (SELECT min(regId) FROM @RegistroGeneralId)
	
													insert [staging].[RegistroDetalladoPlanillaN] (redId, redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redTipoCotizante, redCodDepartamento, redCodMunicipio, redPrimerApellido, redSegundoApellido, 
													redPrimerNombre, redSegundoNombre, redNovIngreso, redNovRetiro, redNovVSP, redNovVST, redNovSLN, redNovIGE, redNovLMA, redNovVACLR, redNovSUS, redDiasIRL, redDiasCotizados, redSalarioBasico, 
													redValorIBC, redTarifa, redAporteObligatorio, redCorrecciones, redSalarioIntegral, redFechaIngreso, redFechaRetiro, redFechaInicioVSP, redFechaInicioSLN, redFechaFinSLN, redFechaInicioIGE, 
													redFechaFinIGE, redFechaInicioLMA, redFechaFinLMA, redFechaInicioVACLR, redFechaFinVACLR, redFechaInicioVCT, redFechaFinVCT, redFechaInicioIRL, redFechaFinIRL, redFechaInicioSuspension, 
													redFechaFinSuspension, redHorasLaboradas, redRegistroControl, redOUTMarcaValRegistroAporte, redOUTEstadoRegistroAporte, redOUTAnalisisIntegral, redOUTFechaProcesamientoValidRegAporte, 
													redOUTEstadoValidacionV0, redOUTEstadoValidacionV1, redOUTEstadoValidacionV2, redOUTEstadoValidacionV3, redOUTClaseTrabajador, redOUTPorcentajePagoAportes, redOUTEstadoSolicitante, 
													redOUTEsTrabajadorReintegrable, redOUTFechaIngresoCotizante, redOUTFechaUltimaNovedad, redOUTFechaFallecimiento, redUsuarioAprobadorAporte, redNumeroOperacionAprobacion, redEstadoEvaluacion, 
													redEstadoRegistroCorreccion, redOUTCodSucursal, redOUTNomSucursal, redOUTDiasCotizadosPlanillas, redOUTDiasCotizadosBD, redOUTDiasCotizadosNovedades, redOUTTipoAfiliado, redOUTRegistrado, 
													redOUTValorMoraCotizante, redOUTAporteObligatorioMod, redOUTDiasCotizadosMod, redOUTRegistradoAporte, redOUTRegistradoNovedad, redOUTTipoNovedadSituacionPrimaria, redOUTFechaInicioNovedadSituacionPrimaria, 
													redOUTFechaFinNovedadSituacionPrimaria, redOUTRegDetOriginal, redOUTEstadoRegistroRelacionAporte, redOUTEstadoEvaluacionAporte, redOUTFechaRetiroCotizante, redOUTValorIBCMod, redOUTValorMoraCotizanteMod, 
													redFechaInicioVST, redFechaFinVST, redOUTDiasCotizadosNovedadesBD, redOUTGrupoFamiliarReintegrable, redIdRegistro2pila, redOUTEnviadoAFiscalizacionInd, redOUTMotivoFiscalizacionInd, redOUTRegistroActual, 
													redOUTRegInicial, redOUTGrupoAC, redOUTTarifaMod, redDateTimeInsert, redDateTimeUpdate, redOUTPeriodicidad, redUsuarioAccion, redFechaAccion, redRegistroDetalladoAnterior, actualizarApd)
													select rd.redId, rd.redRegistroGeneral, rd.redTipoIdentificacionCotizante, rd.redNumeroIdentificacionCotizante, rd.redTipoCotizante, rd.redCodDepartamento, 
													rd.redCodMunicipio, rd.redPrimerApellido, rd.redSegundoApellido, rd.redPrimerNombre, rd.redSegundoNombre, rd.redNovIngreso, rd.redNovRetiro, rd.redNovVSP, 
													rd.redNovVST, rd.redNovSLN, rd.redNovIGE, rd.redNovLMA, rd.redNovVACLR, rd.redNovSUS, rd.redDiasIRL, rd.redDiasCotizados, rd.redSalarioBasico, 
													c.valParaUpdateIBC, --rd.redValorIBC, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15
													rd.redTarifa, 
													c.valParaUpdate, --rd.redAporteObligatorio, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15 
													rd.redCorrecciones, rd.redSalarioIntegral, rd.redFechaIngreso, rd.redFechaRetiro, rd.redFechaInicioVSP, rd.redFechaInicioSLN, 
													rd.redFechaFinSLN, rd.redFechaInicioIGE, rd.redFechaFinIGE, rd.redFechaInicioLMA, rd.redFechaFinLMA, rd.redFechaInicioVACLR, rd.redFechaFinVACLR, rd.redFechaInicioVCT, 
													rd.redFechaFinVCT, rd.redFechaInicioIRL, rd.redFechaFinIRL, rd.redFechaInicioSuspension, rd.redFechaFinSuspension, rd.redHorasLaboradas, rd.redRegistroControl, 
													rd.redOUTMarcaValRegistroAporte, rd.redOUTEstadoRegistroAporte, rd.redOUTAnalisisIntegral, rd.redOUTFechaProcesamientoValidRegAporte, rd.redOUTEstadoValidacionV0, 
													rd.redOUTEstadoValidacionV1, rd.redOUTEstadoValidacionV2, rd.redOUTEstadoValidacionV3, rd.redOUTClaseTrabajador, rd.redOUTPorcentajePagoAportes, rd.redOUTEstadoSolicitante, 
													rd.redOUTEsTrabajadorReintegrable, rd.redOUTFechaIngresoCotizante, rd.redOUTFechaUltimaNovedad, rd.redOUTFechaFallecimiento, rd.redUsuarioAprobadorAporte, 
													rd.redNumeroOperacionAprobacion, rd.redEstadoEvaluacion, rd.redEstadoRegistroCorreccion, rd.redOUTCodSucursal, rd.redOUTNomSucursal, rd.redOUTDiasCotizadosPlanillas, 
													rd.redOUTDiasCotizadosBD, rd.redOUTDiasCotizadosNovedades, rd.redOUTTipoAfiliado, rd.redOUTRegistrado, rd.redOUTValorMoraCotizante, rd.redOUTAporteObligatorioMod, 
													rd.redOUTDiasCotizadosMod, rd.redOUTRegistradoAporte, rd.redOUTRegistradoNovedad, rd.redOUTTipoNovedadSituacionPrimaria, rd.redOUTFechaInicioNovedadSituacionPrimaria, 
													rd.redOUTFechaFinNovedadSituacionPrimaria, rd.redOUTRegDetOriginal, rd.redOUTEstadoRegistroRelacionAporte, rd.redOUTEstadoEvaluacionAporte, rd.redOUTFechaRetiroCotizante, 
													rd.redOUTValorIBCMod, rd.redOUTValorMoraCotizanteMod, rd.redFechaInicioVST, rd.redFechaFinVST, rd.redOUTDiasCotizadosNovedadesBD, rd.redOUTGrupoFamiliarReintegrable, 
													rd.redIdRegistro2pila, rd.redOUTEnviadoAFiscalizacionInd, rd.redOUTMotivoFiscalizacionInd, rd.redOUTRegistroActual, rd.redOUTRegInicial, rd.redOUTGrupoAC, rd.redOUTTarifaMod, 
													rd.redDateTimeInsert, rd.redDateTimeUpdate, rd.redOUTPeriodicidad, rd.redUsuarioAccion, rd.redFechaAccion, c.registroDetalladoOriginal, 
													case when (c.valParaUpdate + rd.redOUTValorMoraCotizante) > 0 then 1 else 0 end as actualizar
													from staging.RegistroDetallado as rd
													inner join @registrosDetallados as rdInsert on rd.redId = rdInsert.redId and rd.redIdRegistro2pila = rdInsert.redIdRegistro2pila
													inner join #PilaArchivoIRegistroC as c on c.pi2Id = rd.redIdRegistro2pila
	
												end
											else
												begin
													--select 'La cantidad de registros finales no es igual después de la validación final 2.'
													;with log_N_exis as (select pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,
													pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,
													pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal
													from dbo.logPLanillasNDetalle
													where pipIdPlanilla = @idPlanilla
													and (redTipoCotizante = 1 or pi2PrimerApellido = 1 or pi2SegundoApellido = 1 or pi2PrimerNombre = 1 or pi2SegundoNombre = 1 or pi2NovIngreso = 1 or pi2NovRetiro = 1 or pi2NovVSP = 1 or pi2NovVST = 1 or pi2NovSLN = 1 or 
													pi2NovIGE = 1 or pi2NovLMA = 1 or pi2NovVACLR = 1 or pi2DiasIRL = 1 or pi2DiasCotizados = 1 or pi2SalarioBasico = 1 or pi2ValorIBC = 1 or pi2Tarifa = 1 or pi2AporteObligatorio = 1 or pi2SalarioIntegral = 1 or pi2FechaIngreso = 1 or 
													pi2FechaRetiro = 1 or pi2FechaInicioVSP = 1 or pi2FechaInicioSLN = 1 or pi2FechaFinSLN = 1 or pi2FechaInicioIGE = 1 or pi2FechaFinIGE = 1 or pi2FechaInicioLMA = 1 or pi2FechaFinLMA = 1 or pi2FechaInicioVACLR = 1 or pi2FechaFinVACLR = 1 or 
													pi2FechaInicioVCT = 1 or pi2FechaFinVCT = 1 or pi2FechaInicioIRL = 1 or pi2FechaFinIRL = 1)
													group by pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,
													pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,
													pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal)

													insert dbo.logPlanillasNDetalle (pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico
													,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT
													,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal, fechaProceso, fechaPago, Periodo)
													select	@planillaN as pipIdPlanilla, a.pi2Id,a.redTipoCotizante,a.pi2PrimerApellido,a.pi2SegundoApellido,a.pi2PrimerNombre,a.pi2SegundoNombre,a.pi2NovIngreso,a.pi2NovRetiro,a.pi2NovVSP,a.pi2NovVST,a.pi2NovSLN,a.pi2NovIGE,a.pi2NovLMA,a.pi2NovVACLR,a.pi2DiasIRL,a.pi2DiasCotizados
														,a.pi2SalarioBasico,a.pi2ValorIBC,a.pi2Tarifa,a.pi2AporteObligatorio,a.pi2SalarioIntegral,a.pi2FechaIngreso,a.pi2FechaRetiro,a.pi2FechaInicioVSP,a.pi2FechaInicioSLN,a.pi2FechaFinSLN,a.pi2FechaInicioIGE,a.pi2FechaFinIGE,a.pi2FechaInicioLMA,a.pi2FechaFinLMA,a.pi2FechaInicioVACLR
														,a.pi2FechaFinVACLR,a.pi2FechaInicioVCT,a.pi2FechaFinVCT,a.pi2FechaInicioIRL,a.pi2FechaFinIRL,a.registroDetalladoOriginal, dbo.GetLocalDate() as fechaProceso
														,(select pi1FechaPagoAsociado from #planillaNBuscarOriginal_sp) as fechaPago
														,(select pi1PeriodoAporte from #planillaNBuscarOriginal_sp) as Periodo
													from #tblLog as a
													left join log_N_exis as b on b.pi2Id = a.pi2Id and b.redTipoCotizante = a.redTipoCotizante and b.pi2PrimerApellido = a.pi2PrimerApellido and b.pi2SegundoApellido = a.pi2SegundoApellido and 
													b.pi2PrimerNombre = a.pi2PrimerNombre and b.pi2SegundoNombre = a.pi2SegundoNombre and b.pi2NovIngreso = a.pi2NovIngreso and b.pi2NovRetiro = a.pi2NovRetiro and b.pi2NovVSP = a.pi2NovVSP and b.pi2NovVST = a.pi2NovVST and 
													b.pi2NovSLN = a.pi2NovSLN and b.pi2NovIGE = a.pi2NovIGE and b.pi2NovLMA = a.pi2NovLMA and b.pi2NovVACLR = a.pi2NovVACLR and b.pi2DiasIRL = a.pi2DiasIRL and b.pi2DiasCotizados = a.pi2DiasCotizados and b.pi2SalarioBasico = a.pi2SalarioBasico and 
													b.pi2ValorIBC = a.pi2ValorIBC and b.pi2Tarifa = a.pi2Tarifa and b.pi2AporteObligatorio = a.pi2AporteObligatorio and b.pi2SalarioIntegral = a.pi2SalarioIntegral and b.pi2FechaIngreso = a.pi2FechaIngreso and b.pi2FechaRetiro = a.pi2FechaRetiro and 
													b.pi2FechaInicioVSP = a.pi2FechaInicioVSP and b.pi2FechaInicioSLN = a.pi2FechaInicioSLN and b.pi2FechaFinSLN = a.pi2FechaFinSLN and b.pi2FechaInicioIGE = a.pi2FechaInicioIGE and b.pi2FechaFinIGE = a.pi2FechaFinIGE and b.pi2FechaInicioLMA = a.pi2FechaInicioLMA and 
													b.pi2FechaFinLMA = a.pi2FechaFinLMA and b.pi2FechaInicioVACLR = a.pi2FechaInicioVACLR and b.pi2FechaFinVACLR = a.pi2FechaFinVACLR and b.pi2FechaInicioVCT = a.pi2FechaInicioVCT and b.pi2FechaFinVCT = a.pi2FechaFinVCT and 
													b.pi2FechaInicioIRL = a.pi2FechaInicioIRL and b.pi2FechaFinIRL = a.pi2FechaFinIRL and isnull(b.registroDetalladoOriginal, 0) = isnull(a.registroDetalladoOriginal,0)
													where (a.redTipoCotizante+ a.pi2PrimerApellido+ a.pi2SegundoApellido+ a.pi2PrimerNombre+ a.pi2SegundoNombre+ a.pi2NovIngreso+ a.pi2NovRetiro+ a.pi2NovVSP+ a.pi2NovVST+ a.pi2NovSLN+ a.pi2NovIGE+ a.pi2NovLMA
													+ a.pi2NovVACLR+ a.pi2DiasIRL+ a.pi2DiasCotizados+ a.pi2SalarioBasico+ a.pi2ValorIBC+ a.pi2Tarifa+ a.pi2AporteObligatorio+ a.pi2SalarioIntegral+ a.pi2FechaIngreso+ a.pi2FechaRetiro+ a.pi2FechaInicioVSP+ a.pi2FechaInicioSLN
													+ a.pi2FechaFinSLN+ a.pi2FechaInicioIGE+ a.pi2FechaFinIGE+ a.pi2FechaInicioLMA+ a.pi2FechaFinLMA+ a.pi2FechaInicioVACLR+ a.pi2FechaFinVACLR+ a.pi2FechaInicioVCT+ a.pi2FechaFinVCT+ a.pi2FechaInicioIRL+ a.pi2FechaFinIRL) > 0
													and b.pi2Id is null
												end
										end 
									else
										begin
											--select 'La cantidad de registros finales no es igual después de la validación final 1.'
											;with log_N_exis as (select pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,
											pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,
											pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal
											from dbo.logPLanillasNDetalle
											where pipIdPlanilla = @idPlanilla
											and (redTipoCotizante = 1 or pi2PrimerApellido = 1 or pi2SegundoApellido = 1 or pi2PrimerNombre = 1 or pi2SegundoNombre = 1 or pi2NovIngreso = 1 or pi2NovRetiro = 1 or pi2NovVSP = 1 or pi2NovVST = 1 or pi2NovSLN = 1 or 
											pi2NovIGE = 1 or pi2NovLMA = 1 or pi2NovVACLR = 1 or pi2DiasIRL = 1 or pi2DiasCotizados = 1 or pi2SalarioBasico = 1 or pi2ValorIBC = 1 or pi2Tarifa = 1 or pi2AporteObligatorio = 1 or pi2SalarioIntegral = 1 or pi2FechaIngreso = 1 or 
											pi2FechaRetiro = 1 or pi2FechaInicioVSP = 1 or pi2FechaInicioSLN = 1 or pi2FechaFinSLN = 1 or pi2FechaInicioIGE = 1 or pi2FechaFinIGE = 1 or pi2FechaInicioLMA = 1 or pi2FechaFinLMA = 1 or pi2FechaInicioVACLR = 1 or pi2FechaFinVACLR = 1 or 
											pi2FechaInicioVCT = 1 or pi2FechaFinVCT = 1 or pi2FechaInicioIRL = 1 or pi2FechaFinIRL = 1)
											group by pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,
											pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,
											pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal)

											insert dbo.logPlanillasNDetalle (pipIdPlanilla,pi2Id,redTipoCotizante,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico
											,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT
											,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,registroDetalladoOriginal, fechaProceso, fechaPago, Periodo)
											select	@planillaN as pipIdPlanilla, a.pi2Id,a.redTipoCotizante,a.pi2PrimerApellido,a.pi2SegundoApellido,a.pi2PrimerNombre,a.pi2SegundoNombre,a.pi2NovIngreso,a.pi2NovRetiro,a.pi2NovVSP,a.pi2NovVST,a.pi2NovSLN,a.pi2NovIGE,a.pi2NovLMA,a.pi2NovVACLR,a.pi2DiasIRL,a.pi2DiasCotizados
												,a.pi2SalarioBasico,a.pi2ValorIBC,a.pi2Tarifa,a.pi2AporteObligatorio,a.pi2SalarioIntegral,a.pi2FechaIngreso,a.pi2FechaRetiro,a.pi2FechaInicioVSP,a.pi2FechaInicioSLN,a.pi2FechaFinSLN,a.pi2FechaInicioIGE,a.pi2FechaFinIGE,a.pi2FechaInicioLMA,a.pi2FechaFinLMA,a.pi2FechaInicioVACLR
												,a.pi2FechaFinVACLR,a.pi2FechaInicioVCT,a.pi2FechaFinVCT,a.pi2FechaInicioIRL,a.pi2FechaFinIRL,a.registroDetalladoOriginal, dbo.GetLocalDate() as fechaProceso
												,(select pi1FechaPagoAsociado from #planillaNBuscarOriginal_sp) as fechaPago
												,(select pi1PeriodoAporte from #planillaNBuscarOriginal_sp) as Periodo
											from #tblLog as a
											left join log_N_exis as b on b.pi2Id = a.pi2Id and b.redTipoCotizante = a.redTipoCotizante and b.pi2PrimerApellido = a.pi2PrimerApellido and b.pi2SegundoApellido = a.pi2SegundoApellido and 
											b.pi2PrimerNombre = a.pi2PrimerNombre and b.pi2SegundoNombre = a.pi2SegundoNombre and b.pi2NovIngreso = a.pi2NovIngreso and b.pi2NovRetiro = a.pi2NovRetiro and b.pi2NovVSP = a.pi2NovVSP and b.pi2NovVST = a.pi2NovVST and 
											b.pi2NovSLN = a.pi2NovSLN and b.pi2NovIGE = a.pi2NovIGE and b.pi2NovLMA = a.pi2NovLMA and b.pi2NovVACLR = a.pi2NovVACLR and b.pi2DiasIRL = a.pi2DiasIRL and b.pi2DiasCotizados = a.pi2DiasCotizados and b.pi2SalarioBasico = a.pi2SalarioBasico and 
											b.pi2ValorIBC = a.pi2ValorIBC and b.pi2Tarifa = a.pi2Tarifa and b.pi2AporteObligatorio = a.pi2AporteObligatorio and b.pi2SalarioIntegral = a.pi2SalarioIntegral and b.pi2FechaIngreso = a.pi2FechaIngreso and b.pi2FechaRetiro = a.pi2FechaRetiro and 
											b.pi2FechaInicioVSP = a.pi2FechaInicioVSP and b.pi2FechaInicioSLN = a.pi2FechaInicioSLN and b.pi2FechaFinSLN = a.pi2FechaFinSLN and b.pi2FechaInicioIGE = a.pi2FechaInicioIGE and b.pi2FechaFinIGE = a.pi2FechaFinIGE and b.pi2FechaInicioLMA = a.pi2FechaInicioLMA and 
											b.pi2FechaFinLMA = a.pi2FechaFinLMA and b.pi2FechaInicioVACLR = a.pi2FechaInicioVACLR and b.pi2FechaFinVACLR = a.pi2FechaFinVACLR and b.pi2FechaInicioVCT = a.pi2FechaInicioVCT and b.pi2FechaFinVCT = a.pi2FechaFinVCT and 
											b.pi2FechaInicioIRL = a.pi2FechaInicioIRL and b.pi2FechaFinIRL = a.pi2FechaFinIRL and isnull(b.registroDetalladoOriginal, 0) = isnull(a.registroDetalladoOriginal,0)
											where (a.redTipoCotizante+ a.pi2PrimerApellido+ a.pi2SegundoApellido+ a.pi2PrimerNombre+ a.pi2SegundoNombre+ a.pi2NovIngreso+ a.pi2NovRetiro+ a.pi2NovVSP+ a.pi2NovVST+ a.pi2NovSLN+ a.pi2NovIGE+ a.pi2NovLMA
											+ a.pi2NovVACLR+ a.pi2DiasIRL+ a.pi2DiasCotizados+ a.pi2SalarioBasico+ a.pi2ValorIBC+ a.pi2Tarifa+ a.pi2AporteObligatorio+ a.pi2SalarioIntegral+ a.pi2FechaIngreso+ a.pi2FechaRetiro+ a.pi2FechaInicioVSP+ a.pi2FechaInicioSLN
											+ a.pi2FechaFinSLN+ a.pi2FechaInicioIGE+ a.pi2FechaFinIGE+ a.pi2FechaInicioLMA+ a.pi2FechaFinLMA+ a.pi2FechaInicioVACLR+ a.pi2FechaFinVACLR+ a.pi2FechaInicioVCT+ a.pi2FechaFinVCT+ a.pi2FechaInicioIRL+ a.pi2FechaFinIRL) > 0
											and b.pi2Id is null
										end
											--================================================= 			
								end
							else
								begin
									--select 'La diferencia entre valores en plata de los registros C - A es no igual al valor del registro 3' as mensaje, @totalAporte3 total, @mora as moraACalcular
											insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
											select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() 
											,N'Los valores de aportes entre los registros A y C no es igual al registro 3' as mensaje, rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
											from #planillaNBuscarOriginal_sp as rn
											left join dbo.logPlanillasN as l on rn.pipIdPlanilla = L.planillaN and l.Mensaje = N'Los valores de aportes entre los registros A y C no es igual al registro 3'
											where l.planillaN is null
								end
						end
					else
						begin
						----select 'La cantidad de registros A no es igual a la de registros C' as mensaje1, @cantA as totalRegVal2 
							--insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
							--select top 1 rn.pipIdPlanilla, rn.pi1NumPlanillaAsociada, dbo.GetLocalDate() 
							--,N'La cantidad de registros C no es igual a la cantidad de registros A - Escalar a DB para revisar' as mensaje, rn.pi1FechaPagoAsociado, rn.pi1PeriodoAporte
							--from #planillaNBuscarOriginal_sp as rn
							--left join dbo.logPlanillasN as l on rn.pipIdPlanilla = L.planillaN and l.Mensaje = N'La cantidad de registros C no es igual a la cantidad de registros A - Escalar a DB para revisar'
							--where l.planillaN is null

							--select * from PilaArchivoIRegistro2 where pi2IndicePlanilla=2355407

								;with RegA as (
									select max(pi2.pi2Id) as pi2IdA,max(ti.TipoIdLargo) as tipoIdA, pi2.pi2IdCotizante as numIdCotA, sum(pi2.pi2AporteObligatorio) as aporteAnt, 
									sum(pi2.pi2ValorIBC) as IBCant,sum(pi2.pi2diascotizados)as diascotizadosA,max(pi2.pi2SalarioBasico) as salariobA, 
									max(pi2HorasLaboradas)as horasA
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipIdOri
									and pi2.pi2Correcciones = 'A'
									group by pi2.pi2IdCotizante
									),
									regC as (
									select max(pi2.pi2Id) as pi2IdC, max(ti.TipoIdLargo) as tipoIdC, pi2.pi2IdCotizante as numIdCotC,sum(pi2.pi2AporteObligatorio) as aporteNue,
									sum(pi2.pi2ValorIBC) as IBCnue,sum(pi2.pi2diascotizados)as diascotizadosC,max(pi2.pi2SalarioBasico) as salariobC, 
									max(pi2HorasLaboradas)as horasC
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipIdOri
									and pi2.pi2Correcciones = 'C'
									group by pi2.pi2IdCotizante
									),
									final as (
									select *, case when a.aporteAnt = c.aporteNue then 0 else 1 end as Modificar
									from RegA as a
									full join regC as c on a.tipoIdA = c.tipoIdC and a.numIdCotA = c.numIdCotC)
									select *,  aporteNue - aporteAnt  as valParaUpdate,IBCnue - IBCant  as valParaUpdateIBC,
									sum(case when Modificar = 1 then aporteNue - aporteAnt else 0 end) over (partition by Modificar) as EstaValDebeSerIgualAlMensaje2, @regId as registroGeneral
									into #regValidar1
									from final


									select val.pi2IdA, val.pi2IdC, rd.redId as registroDetalladoOriginal, rd.redRegistroGeneral, val.Modificar, val.valParaUpdate, val.valParaUpdateIBC,diascotizadosC,salariobC
									into #regValidar2Final
									from #regValidar1 as val
									inner join dbo.PilaArchivoIRegistro2 as pi2 on val.pi2IdA = pi2.pi2Id
									inner join staging.RegistroDetallado as rd on val.tipoIdA = rd.redTipoIdentificacionCotizante and val.numIdCotA = rd.redNumeroIdentificacionCotizante and val.registroGeneral = rd.redRegistroGeneral --and val.aporteAnt = rd.redAporteObligatorio 
									and isnull(upper(rtrim(ltrim(rd.redNovIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIngreso))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovRetiro))),'') and isnull(upper(rtrim(ltrim(rd.redNovVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVSP))),'') and isnull(upper(rtrim(ltrim(rd.redNovVST))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVST))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovSLN))),'') and isnull(upper(rtrim(ltrim(rd.redNovIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIGE))),'') and isnull(upper(rtrim(ltrim(rd.redNovLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVACLR))),'') and convert(smallint,isnull(upper(rtrim(ltrim(rd.redDiasIRL))),'0')) = convert(smallint,isnull(upper(rtrim(ltrim(pi2.pi2DiasIRL))),'0')) 
									and isnull(upper(rtrim(ltrim(rd.redDiasCotizados))),'') = isnull(upper(rtrim(ltrim(pi2.pi2DiasCotizados))),'') and rd.redSalarioBasico = pi2.pi2SalarioBasico and rd.redValorIBC = pi2.pi2ValorIBC and rd.redTarifa = pi2.pi2Tarifa and rd.redAporteObligatorio = pi2.pi2AporteObligatorio 
									and isnull(upper(rtrim(ltrim(rd.redFechaIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaIngreso))),'') and isnull(upper(rtrim(ltrim(rd.redFechaRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaRetiro))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaInicioVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVSP))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioSLN))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinSLN))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIGE))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIGE))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinLMA))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVACLR))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVACLR))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVCT))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVCT))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIRL))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIRL))),'')

										select pi2IndicePlanilla,pi2Secuencia,pi2TipoIdCotizante,pi2IdCotizante,pi2TipoCotizante,pi2SubTipoCotizante,pi2ExtrangeroNoObligado,
										pi2ColombianoExterior,pi2CodDepartamento,pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,
										pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN, pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,case when diascotizadosC>30 then 30 else diascotizadosC end as pi2DiasCotizados,
										salariobC as pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,
										pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,
										pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,pi2HorasLaboradas,pi2Id
                                        ,val2.registroDetalladoOriginal, val2.Modificar, val2.valParaUpdate, val2.valParaUpdateIBC
											into #PilaArchivoIRegistroC_1
											from #regValidar2Final as val2
											inner join dbo.PilaArchivoIRegistro2 as pi2 on val2.pi2IdC = pi2.pi2Id
	

										insert into staging.RegistroGenCtrl(
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													--OUTPUT INSERTED.regId INTO @RegistroGeneralId
													SELECT @IdTransaccion2, 0 AS regEsAportePensionados,pi1RazonSocial,ti.TipoIdLargo,pi1IdAportante,pi1DigVerAportante
														,pi1PeriodoAporte,pi1TipoPlanilla,pi1ClaseAportante,pi1CodSucursal,pi1NomSucursal,pa1Direccion,pa1CodCiudad
														,pa1CodDepartamento,pa1Telefono,pa1Fax,pa1Email,pa1FechaMatricula,pa1NaturalezaJuridica,pi1ModalidadPlanilla
														,pi3ValorTotalAporteObligatorio,pi3ValorMora,CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112)
														,pf1CodigoEntidadFinanciera,pi1CodOperador,pf5NumeroCuenta,pip.pipEstadoArchivo,pi1FechaActualizacion
														,pi1IndicePlanilla,pf1IndicePlanillaOF,pi1NumPlanilla,@bSimulado
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,pip.pipMotivoProcesoManual,pi1NumPlanillaAsociada,pi1DiasMora,pi1FechaPago,pi1Presentacion,pi1CantidadEmpleados,pi1CantidadAfiliados
														,pi1TipoPersona, 1, pi1CantidadReg2, pi1FechaPagoAsociado,dbo.getLocalDate(),dbo.getLocalDate()
													FROM PilaArchivoIRegistro1 pi1 
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi1.pi1TipoDocAportante
													INNER JOIN PilaIndicePlanilla pip ON pi1.pi1IndicePlanilla = pip.pipId
													INNER JOIN PilaIndicePlanilla pipA ON (
														pip.pipIdPlanilla = pipA.pipIdPlanilla 
														AND ISNULL(pipA.pipEstadoArchivo, '') <> 'ANULADO'
														AND pip.pipTipoArchivo != pipA.pipTipoArchivo
														AND pip.pipCodigoOperadorInformacion = pipA.pipCodigoOperadorInformacion
													)
													INNER JOIN PilaArchivoARegistro1 pa1 ON pa1.pa1IndicePlanilla = pipA.pipId
													INNER JOIN PilaArchivoIRegistro3 pi3 ON pi1.pi1IndicePlanilla = pi3.pi3IndicePlanilla
													LEFT JOIN (
																SELECT MAX(pf6Id) pf6Id, pf6NumeroPlanilla, pf6PeriodoPago, CAST(pf6CodOperadorInformacion AS SMALLINT) pf6CodOperadorInformacion
																FROM dbo.PilaArchivoFRegistro6
																WHERE pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
																GROUP BY pf6IndicePlanillaOF, pf6CodOperadorInformacion,pf6NumeroPlanilla,pf6PeriodoPago
																) pf6 ON pf6.pf6CodOperadorInformacion = pi1.pi1CodOperador 
																	AND CAST(pf6.pf6NumeroPlanilla AS BIGINT) = CAST(pi1.pi1NumPlanilla AS BIGINT) 
																	AND pf6.pf6PeriodoPago = CONVERT(VARCHAR(6),CONVERT(DATETIME,pi1.pi1PeriodoAporte+'-01'),112)
													LEFT JOIN dbo.PilaArchivoFRegistro6 pf6_1 ON pf6.pf6Id = pf6_1.pf6Id
													LEFT JOIN dbo.PilaArchivoFRegistro5 pf5 ON pf6_1.pf6PilaArchivoFRegistro5 = pf5.pf5Id
													LEFT JOIN dbo.PilaArchivoFRegistro1 pf1 ON pf6_1.pf6IndicePlanillaOF = pf1.pf1IndicePlanillaOF
													WHERE pi1IndicePlanilla IN (@pipId)
													AND pi1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] with (nolock) WHERE regRegistroControl IS NOT NULL)

													INSERT INTO [staging].[RegistroGeneral] (
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													OUTPUT INSERTED.regId INTO @RegistroGeneralId
													select 
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate
													from staging.RegistroGenCtrl
													where regRegistroControl = @pipId


													begin 
														---////////*****************************
														---**** CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													;with controlDuplicados as (
													select r.*, a.regId as regId2, DENSE_RANK() over (order by r.regId) as total
													from @RegistroGeneralId as a
													inner join staging.RegistroGeneral as r on r.regId = a.regId
													)
													delete from staging.RegistroGeneral where regId in (select controlDuplicados.regId from controlDuplicados where total > 1)
														---////////*****************************
														---**** TERMINAR CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													end
	
													-- Registro detallado Dependiente/Independiente
													INSERT INTO [staging].[RegistroDetallado] (
														redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
														,redCodDepartamento,redCodMunicipio,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redNovIngreso,redNovRetiro,redNovVSP
														,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL,redDiasCotizados,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio
														,redCorrecciones,redSalarioIntegral,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSLN,redFechaFinSLN,redFechaInicioIGE
														,redFechaFinIGE,redFechaInicioLMA,redFechaFinLMA,redFechaInicioVACLR,redFechaFinVACLR,redFechaInicioVCT,redFechaFinVCT,redFechaInicioIRL
														,redFechaFinIRL,redHorasLaboradas,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte,redOUTValorMoraCotizante,redIdRegistro2pila
														,redOUTRegistroActual,redDateTimeUpdate,redUsuarioAccion)
													output inserted.redId, inserted.redIdRegistro2pila into @registrosDetallados
													SELECT regId,ti.TipoIdLargo,pi2.pi2IdCotizante,pi2.pi2TipoCotizante,pi2CodDepartamento
														,pi2CodDepartamento+pi2CodMunicipio,pi2.pi2PrimerApellido,pi2.pi2SegundoApellido,pi2.pi2PrimerNombre,pi2.pi2SegundoNombre,pi2.pi2NovIngreso,pi2.pi2NovRetiro,
														pi2.pi2NovVSP,pi2.pi2NovVST,pi2.pi2NovSLN,pi2.pi2NovIGE,pi2.pi2NovLMA,pi2.pi2NovVACLR,pi2.pi2DiasIRL,pi2.pi2DiasCotizados,pi2.pi2SalarioBasico,
														--pi2.pi2ValorIBC, 
														pi2.valParaUpdateIBC, --== Se deja como ibc la diferencia, cuando hay cambio en el valor del aporte -- 2024-04-15
														pi2.pi2Tarifa,
														--pi2.pi2AporteObligatorio--
														pi2.valParaUpdate --== Se deja como aporte la diferencia para update -- 2024-04-15
														,pi2.pi2Correcciones,pi2.pi2SalarioIntegral,pi2.pi2FechaIngreso,pi2.pi2FechaRetiro,pi2.pi2FechaInicioVSP,
														pi2.pi2FechaInicioSLN,pi2.pi2FechaFinSLN,pi2.pi2FechaInicioIGE,pi2.pi2FechaFinIGE,pi2.pi2FechaInicioLMA,pi2.pi2FechaFinLMA,pi2.pi2FechaInicioVACLR,
														pi2.pi2FechaFinVACLR,pi2.pi2FechaInicioVCT,pi2.pi2FechaFinVCT,pi2.pi2FechaInicioIRL,pi2.pi2FechaFinIRL,pi2.pi2HorasLaboradas,pi2.pi2Secuencia
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,@sUsuarioProceso
														,CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
															(pi2.valParaUpdate * regValorIntMora) / regValTotalApoObligatorio
														END, pi2.pi2Id, 1, @redDateTimeUpdate,'@asopagos_TI'
													from #PilaArchivoIRegistroC_1 as pi2
													INNER JOIN PilaIndicePlanilla pip ON pi2IndicePlanilla = pipId
													INNER JOIN PilaArchivoIRegistro1 ON pi2IndicePlanilla = PilaArchivoIRegistro1.pi1IndicePlanilla 		
													INNER JOIN staging.RegistroGeneral ON regRegistroControl = pi1IndicePlanilla
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi2.pi2TipoIdCotizante
													WHERE regId IN (SELECT min(regId) FROM @RegistroGeneralId)
	
													insert [staging].[RegistroDetalladoPlanillaN] (redId, redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redTipoCotizante, redCodDepartamento, redCodMunicipio, redPrimerApellido, redSegundoApellido, 
													redPrimerNombre, redSegundoNombre, redNovIngreso, redNovRetiro, redNovVSP, redNovVST, redNovSLN, redNovIGE, redNovLMA, redNovVACLR, redNovSUS, redDiasIRL, redDiasCotizados, redSalarioBasico, 
													redValorIBC, redTarifa, redAporteObligatorio, redCorrecciones, redSalarioIntegral, redFechaIngreso, redFechaRetiro, redFechaInicioVSP, redFechaInicioSLN, redFechaFinSLN, redFechaInicioIGE, 
													redFechaFinIGE, redFechaInicioLMA, redFechaFinLMA, redFechaInicioVACLR, redFechaFinVACLR, redFechaInicioVCT, redFechaFinVCT, redFechaInicioIRL, redFechaFinIRL, redFechaInicioSuspension, 
													redFechaFinSuspension, redHorasLaboradas, redRegistroControl, redOUTMarcaValRegistroAporte, redOUTEstadoRegistroAporte, redOUTAnalisisIntegral, redOUTFechaProcesamientoValidRegAporte, 
													redOUTEstadoValidacionV0, redOUTEstadoValidacionV1, redOUTEstadoValidacionV2, redOUTEstadoValidacionV3, redOUTClaseTrabajador, redOUTPorcentajePagoAportes, redOUTEstadoSolicitante, 
													redOUTEsTrabajadorReintegrable, redOUTFechaIngresoCotizante, redOUTFechaUltimaNovedad, redOUTFechaFallecimiento, redUsuarioAprobadorAporte, redNumeroOperacionAprobacion, redEstadoEvaluacion, 
													redEstadoRegistroCorreccion, redOUTCodSucursal, redOUTNomSucursal, redOUTDiasCotizadosPlanillas, redOUTDiasCotizadosBD, redOUTDiasCotizadosNovedades, redOUTTipoAfiliado, redOUTRegistrado, 
													redOUTValorMoraCotizante, redOUTAporteObligatorioMod, redOUTDiasCotizadosMod, redOUTRegistradoAporte, redOUTRegistradoNovedad, redOUTTipoNovedadSituacionPrimaria, redOUTFechaInicioNovedadSituacionPrimaria, 
													redOUTFechaFinNovedadSituacionPrimaria, redOUTRegDetOriginal, redOUTEstadoRegistroRelacionAporte, redOUTEstadoEvaluacionAporte, redOUTFechaRetiroCotizante, redOUTValorIBCMod, redOUTValorMoraCotizanteMod, 
													redFechaInicioVST, redFechaFinVST, redOUTDiasCotizadosNovedadesBD, redOUTGrupoFamiliarReintegrable, redIdRegistro2pila, redOUTEnviadoAFiscalizacionInd, redOUTMotivoFiscalizacionInd, redOUTRegistroActual, 
													redOUTRegInicial, redOUTGrupoAC, redOUTTarifaMod, redDateTimeInsert, redDateTimeUpdate, redOUTPeriodicidad, redUsuarioAccion, redFechaAccion, redRegistroDetalladoAnterior, actualizarApd)
													select rd.redId, rd.redRegistroGeneral, rd.redTipoIdentificacionCotizante, rd.redNumeroIdentificacionCotizante, rd.redTipoCotizante, rd.redCodDepartamento, 
													rd.redCodMunicipio, rd.redPrimerApellido, rd.redSegundoApellido, rd.redPrimerNombre, rd.redSegundoNombre, rd.redNovIngreso, rd.redNovRetiro, rd.redNovVSP, 
													rd.redNovVST, rd.redNovSLN, rd.redNovIGE, rd.redNovLMA, rd.redNovVACLR, rd.redNovSUS, rd.redDiasIRL, rd.redDiasCotizados, rd.redSalarioBasico, 
													c.valParaUpdateIBC, --rd.redValorIBC, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15
													rd.redTarifa, 
													c.valParaUpdate, --rd.redAporteObligatorio, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15 
													rd.redCorrecciones, rd.redSalarioIntegral, rd.redFechaIngreso, rd.redFechaRetiro, rd.redFechaInicioVSP, rd.redFechaInicioSLN, 
													rd.redFechaFinSLN, rd.redFechaInicioIGE, rd.redFechaFinIGE, rd.redFechaInicioLMA, rd.redFechaFinLMA, rd.redFechaInicioVACLR, rd.redFechaFinVACLR, rd.redFechaInicioVCT, 
													rd.redFechaFinVCT, rd.redFechaInicioIRL, rd.redFechaFinIRL, rd.redFechaInicioSuspension, rd.redFechaFinSuspension, rd.redHorasLaboradas, rd.redRegistroControl, 
													rd.redOUTMarcaValRegistroAporte, rd.redOUTEstadoRegistroAporte, rd.redOUTAnalisisIntegral, rd.redOUTFechaProcesamientoValidRegAporte, rd.redOUTEstadoValidacionV0, 
													rd.redOUTEstadoValidacionV1, rd.redOUTEstadoValidacionV2, rd.redOUTEstadoValidacionV3, rd.redOUTClaseTrabajador, rd.redOUTPorcentajePagoAportes, rd.redOUTEstadoSolicitante, 
													rd.redOUTEsTrabajadorReintegrable, rd.redOUTFechaIngresoCotizante, rd.redOUTFechaUltimaNovedad, rd.redOUTFechaFallecimiento, rd.redUsuarioAprobadorAporte, 
													rd.redNumeroOperacionAprobacion, rd.redEstadoEvaluacion, rd.redEstadoRegistroCorreccion, rd.redOUTCodSucursal, rd.redOUTNomSucursal, rd.redOUTDiasCotizadosPlanillas, 
													rd.redOUTDiasCotizadosBD, rd.redOUTDiasCotizadosNovedades, rd.redOUTTipoAfiliado, rd.redOUTRegistrado, rd.redOUTValorMoraCotizante, rd.redOUTAporteObligatorioMod, 
													rd.redOUTDiasCotizadosMod, rd.redOUTRegistradoAporte, rd.redOUTRegistradoNovedad, rd.redOUTTipoNovedadSituacionPrimaria, rd.redOUTFechaInicioNovedadSituacionPrimaria, 
													rd.redOUTFechaFinNovedadSituacionPrimaria, rd.redOUTRegDetOriginal, rd.redOUTEstadoRegistroRelacionAporte, rd.redOUTEstadoEvaluacionAporte, rd.redOUTFechaRetiroCotizante, 
													rd.redOUTValorIBCMod, rd.redOUTValorMoraCotizanteMod, rd.redFechaInicioVST, rd.redFechaFinVST, rd.redOUTDiasCotizadosNovedadesBD, rd.redOUTGrupoFamiliarReintegrable, 
													rd.redIdRegistro2pila, rd.redOUTEnviadoAFiscalizacionInd, rd.redOUTMotivoFiscalizacionInd, rd.redOUTRegistroActual, rd.redOUTRegInicial, rd.redOUTGrupoAC, rd.redOUTTarifaMod, 
													rd.redDateTimeInsert, rd.redDateTimeUpdate, rd.redOUTPeriodicidad, rd.redUsuarioAccion, rd.redFechaAccion, c.registroDetalladoOriginal, 
													case when (c.valParaUpdate + rd.redOUTValorMoraCotizante) > 0 then 1 else 0 end as actualizar
													from staging.RegistroDetallado as rd
													inner join @registrosDetallados as rdInsert on rd.redId = rdInsert.redId and rd.redIdRegistro2pila = rdInsert.redIdRegistro2pila
													inner join #PilaArchivoIRegistroC_1 as c on c.pi2Id = rd.redIdRegistro2pila
						end
	
			end try
			begin catch
				if @@TRANCOUNT > 0  rollback transaction;
				    SELECT   
					 ERROR_NUMBER() AS ErrorNumber  
					,ERROR_SEVERITY() AS ErrorSeverity  
					,ERROR_STATE() AS ErrorState  
					,ERROR_LINE () AS ErrorLine  
					,ERROR_PROCEDURE() AS ErrorProcedure  
					,ERROR_MESSAGE() AS ErrorMessage;  
			end catch;
	
		if @@TRANCOUNT > 0  
	    commit transaction;  
	
		end --======= Finaliza validación planillas. 
	else
		begin
			select N'Exepción no controlada.'
		end
	
END