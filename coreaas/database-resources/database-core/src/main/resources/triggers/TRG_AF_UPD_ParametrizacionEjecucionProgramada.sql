-- =============================================
-- Author:		Fabian López
-- Create date: 2020/11/03 
-- Description:	
-- =============================================
CREATE TRIGGER TRG_AF_UPD_ParametrizacionEjecucionProgramada
ON ParametrizacionEjecucionProgramada
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;
	INSERT HistoricoParametrizacionEjecucionProgramada(hpeProceso,
		hpeHoras,
		hpeMinutos,
		hpeSegundos,
		hpeDiaSemana,
		hpeDiaMes,
		hpeMes,
		hpeAnio,
		hpeFechaInicio,
		hpeFechaFin,
		hpeFrecuencia,
		hpeEstado, 
		hpeFechaActualizacion,
		hpeIsActualizacion)
		SELECT ins.pepProceso, 
	                ins.pepHoras,
	                ins.pepMinutos,
	                ins.pepSegundos,
	                ins.pepDiaSemana,
	                ins.pepDiaMes,
	                ins.pepMes,
	                ins.pepAnio,
	                ins.pepFechaInicio,
	                ins.pepFechaFin,
	                ins.pepFrecuencia,
	                ins.pepEstado,
	                dbo.getLocalDate(),
	                1
	        FROM INSERTED ins 
END