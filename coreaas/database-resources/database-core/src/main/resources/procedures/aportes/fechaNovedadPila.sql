-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2021-01-21
-- Description: Procedimiento almacenado encagado de trer la fecha de novedad desde pila
-- =============================================
CREATE PROCEDURE [dbo].[fechaNovedadPila]
@solRegistroDetallado bigInt,
@fechaNovedadPilaResul DATE OUTPUT
as
begin
declare @id bigInt = (select spiRegistroDetallado from solicitudNovedad n inner join SolicitudNovedadPila sp on sp.spiSolicitudNovedad=n.snoId where n.snoSolicitudGlobal =@solRegistroDetallado)
declare @fecha table (idRegistroDetallado date, origen varchar (200))
insert @fecha
exec sp_execute_remote pilaReferenceData, N'select rdnFechaInicioNovedad from staging.RegistroDetalladoNovedad with (nolock) where rdnRegistroDetallado = @id and rdnTipoNovedad = ''NOVEDAD_ING''', N'@id bigInt', @id = @id
select @fechaNovedadPilaResul=idRegistroDetallado from @fecha
end