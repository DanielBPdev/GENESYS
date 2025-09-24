-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-10-22
-- Description: Proceso encargado de descartar y simular la devolución masiva de aportes. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_SimularAportesDevolucionMasivosSolicitud]
(
	 @numeroRadicado varchar (30)
)
AS
BEGIN

    SET NOCOUNT ON

	--===========================================================
			--=================== Se crean las solicitudes generales y se relacionan a los aportes que pasan la validación 
			--===========================================================

			create table #actSolicitud (solId bigInt, solNumeroRadicacion varchar(30), origen varchar(250))
			insert #actSolicitud
			execute sp_execute_remote coreReferenceData, N'
			drop table if exists #solicitudesSimuladas
			create table #solicitudesSimuladas (solId bigInt, solNumeroRadicacion varchar(30))
			declare @query1 nvarchar(max) = N''
			select iden int, idSolicitud, radicadoSimulado
			from masivos.aportesDevolucionGeneralSimular where iden > 1 and CHARINDEX('' + CHAR(39) + CHAR(95) + CHAR(39) + N'', radicadoSimulado) > 0
			and LEFT(radicadoSimulado, CHARINDEX('' + CHAR(39) + CHAR(95) + CHAR(39) + N'', radicadoSimulado) -1) = '' + char(39) + @numeroRadicado + char(39)
			declare @pilaCrearSolicitudes as table (iden int, idSolicitud bigInt, radicadoSimulado varchar(30), origen varchar(250))
			insert @pilaCrearSolicitudes
			execute sp_execute_remote pilaReferenceData, @query1
			
				begin try
					begin transaction

						insert dbo.Solicitud (solCanalRecepcion, solFechaRadicacion, solNumeroRadicacion, solUsuarioRadicacion, solTipoTransaccion, solFechaCreacion, solDestinatario)
						output inserted.solId, inserted.solNumeroRadicacion into #solicitudesSimuladas
						select s.solCanalRecepcion, dbo.getLocalDate() as solFechaRadicacion, a.radicadoSimulado, s.solUsuarioRadicacion, s.solTipoTransaccion, dbo.getLocalDate() as solFechaCreacion, s.solDestinatario
						from @pilaCrearSolicitudes as a
						inner join dbo.Solicitud as s on a.idSolicitud = s.solId
						order by iden

				end try
				begin catch
					if @@TRANCOUNT > 0
					rollback transaction;
				end catch

			if @@TRANCOUNT > 0
			commit transaction

			select solId, solNumeroRadicacion from #solicitudesSimuladas


			', N'@numeroRadicado varchar(30)', @numeroRadicado  = @numeroRadicado 
			
			update b set b.idSolicitud = a.solId
			from #actSolicitud as a
			inner join masivos.aportesDevolucionGeneralSimular as b on a.solNumeroRadicacion = b.radicadoSimulado

END