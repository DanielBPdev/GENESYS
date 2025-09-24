-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2024-02-13
-- Description: Objeto para revisar las novedades de pila para las planillas y saber si pueden notificar. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ExecuteValidateNovPila]
(
	@regId bigInt, @validar bit output
)
AS
BEGIN

    SET NOCOUNT ON
		

	declare @novPila as table  (pipId bigint, regId bigInt, registroDetalle bigInt, regisotroDatalleNov bigInt, actualizarApd int, nov int, redUsuarioAccion varchar(30), origen varchar(250))
		insert @novPila
		execute sp_execute_remote pilaReferenceData, N'
			select p.pipId, r.regId, rd.redId, rdn.rdnId, rd.actualizarApd, case when rdn.rdnId is not null then 1 else 0 end as nov, rd.redUsuarioAccion
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.pilaEstadoBLoque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla 
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			inner join staging.RegistroDetalladoPlanillaN as rd with (nolock) on r.regId = rd.redRegistroGeneral
			left join staging.registroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where p.pipId = @pipId
		', N'@pipId bigInt', @pipId = @regId
		
		

        declare @cantNovCore int = 0
		declare @cantnovPila int = 0
		declare @cantAporte int = 0
		declare @pipId bigInt = 0
		declare @regId2 bigInt = 0
		declare @redUsuarioAccion varchar(30) = null
		select @redUsuarioAccion = redUsuarioAccion from @novPila group by pipId,redUsuarioAccion
		declare @apgRegistroGeneral bigInt = (select regId from @novPila group by regId)
		
		
		;with novFalta as (
		--select a.registroDetalle, a.regisotroDatalleNov, spi.*, case when spi.spiId is null then 1 else 0 end as novFalta
		select a.pipId, a.regId, a.registroDetalle, a.regisotroDatalleNov, a.actualizarApd, a.nov, case when spi.spiId is null then 0 else 1 end as novFalta
		from @novPila as a
		left join dbo.solicitudNovedadPila as spi on a.registroDetalle = spi.spiRegistroDetallado and a.regisotroDatalleNov = spi.spiIdRegistroDetalladoNovedad)
		select @pipId = pipId, @regId2 = regId, @cantAporte = count(distinct (iif(actualizarApd = 1, registroDetalle , null))), @cantnovPila = sum(nov), @cantNovCore = sum(novFalta)
		from novFalta
		group by pipId,regId
		
				if @cantAporte > 0 and @redUsuarioAccion<>'@asopagos_TI'--=== Validar si tiene aportes. 
					begin
						declare @totalAportesPila int = (select count(*) from dbo.aporteDetalladoRegistroControlN where pipId = @pipId)

						 if @cantnovPila > 0 --=== Validar si tiene nov con aportes. 
							begin

								if @redUsuarioAccion = '@asopagos_TI'
									begin
										
										declare @totalAportesPlanillaCmasA int = (
										select count(*) 
										from dbo.aporteGeneral as apg with (nolock) 
										inner join dbo.aporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
										where apg.apgRegistroGeneral = @apgRegistroGeneral)

										if (@cantnovPila = @cantNovCore) and (@cantAporte = @totalAportesPlanillaCmasA)
											begin
												set @validar = 1
											end
										else
											begin
											  set @validar = 0
											end
									end

								else if (@cantnovPila = @cantNovCore) and (@totalAportesPila = @cantAporte)
									begin
										set @validar = 1
									end
								else
									begin
									  set @validar = 0
									end
							end
						else --== Solo con aportes
							begin
								if (@totalAportesPila = @cantAporte)
									begin
										set @validar =  1
									end
								else
									begin
										set @validar = 0
									end
							end
					end
				else --=== Es un aporte de solo novedades, por lo cual se valida la totalidad de las novedades, por integridad. 
					begin
						if (@cantnovPila = @cantNovCore)
							begin
								set @validar = 1
							end
						else 
							begin
								set @validar = 0
							end
					end
END