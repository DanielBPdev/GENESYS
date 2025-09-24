-- Nombre archivo...  TGR_AF_INS_SolicitudesReportesNormativos.sql

CREATE OR ALTER TRIGGER [dbo].[TGR_AF_INS_SolicitudesReportesNormativos]
ON [dbo].[SolicitudesReportesNormativos]
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
	declare @idSolicitud int;
	declare @fechaInicio date;
	declare @fechafin date;
	declare @prnId int;

	select top 1 @idSolicitud = srId , @fechaInicio = srFechaInicio, @fechafin = srFechaFin, @prnId = prnId
	from Inserted sr
	inner join ParametrizacionReportesNormativos prn with (nolock) on sr.srParamReportesNormativosId = prn.prnId


	if @prnId = 18 -- Reporte afiliados
	begin
		UPDATE S
		SET S.srEstado = 'PROCESANDO...'
		FROM SolicitudesReportesNormativos S
		INNER JOIN inserted i ON S.srId = i.srId;

		execute [dbo].[reporteAfiliados] @fechaInicio, @fechafin, @prnId
	end;
END;


