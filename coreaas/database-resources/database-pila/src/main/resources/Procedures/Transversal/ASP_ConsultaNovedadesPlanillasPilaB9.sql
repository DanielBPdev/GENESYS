-- =============================================
-- Author:      <Author, , Name>
-- Create Date: 2023-09-21
-- Description: Retorna el listado de novedades a procesar. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ConsultaNovedadesPlanillasPilaB9] 
(@idRegistroGeneral bigInt)
AS
BEGIN
    SET NOCOUNT ON

 declare @redTipoIdentificacionCotizante varchar(40)
 declare @redNumeroIdentificacionCotizante varchar(40)
 declare @regNumeroIdentificacionAportante varchar(40)
 declare @regTipoIdentificacionAportante varchar (40)
 declare @isIngresoRetiro bit
 declare @tipoMovimiento varchar(20)

declare cursorIds cursor for

select distinct redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,regNumeroIdentificacionAportante,regTipoIdentificacionAportante
from staging.RegistroGeneral rg
inner join staging.RegistroDetallado rd on rg.regId=rd.redRegistroGeneral
inner join staging.RegistroDetalladoNovedad rdn on rd.redId=rdn.rdnRegistroDetallado
where (redNovIngreso is not null or redNovRetiro is not null)
and regId=@idRegistroGeneral

    SELECT top 1 @tipoMovimiento =
        CASE
            WHEN tenEsIngreso = 1 THEN 'Ingreso'
            WHEN tenEsRetiro = 1 THEN 'Retiro'
            ELSE 'Desconocido'
        END
    FROM TemNovedad
    where tenRegistroGeneral = @idRegistroGeneral
    order by tenTipoIdCotizante, tenNumeroIdCotizante, isnull(tenFechaInicioNovedad, tenFechaFinNovedad), tenFechaFinNovedad, (case when tenTipoTransaccion = 'NOVEDAD_REINTEGRO' then 'A' else tenTipoTransaccion end)


	drop table if exists #beneficiarios
	create table #beneficiarios (benId INT, shard varchar(max))


open cursorIds;
	fetch next from cursorIds
	into
	@redTipoIdentificacionCotizante,@redNumeroIdentificacionCotizante,@regNumeroIdentificacionAportante,@regTipoIdentificacionAportante


	---------inicia ciclado
	while @@fetch_status = 0
	begin


if @tipoMovimiento = 'Ingreso'
begin
	insert into #beneficiarios
    EXEC sp_execute_remote
        CoreReferenceData,
        N'
        SELECT DISTINCT b.benId
        FROM persona p
        INNER JOIN afiliado a ON a.afiPersona = p.perId
        INNER JOIN beneficiario b ON b.benAfiliado = a.afiId
        INNER JOIN persona p2 ON p2.perId = b.benPersona
        INNER JOIN rolAfiliado ra ON ra.roaAfiliado = a.afiId
        INNER JOIN Empleador e ON e.empId = ra.roaEmpleador
        INNER JOIN empresa emp ON emp.empId = e.empEmpresa
        INNER JOIN Persona p3 ON p3.perId = emp.empPersona
        INNER JOIN PersonaDetalle pd on pd.pedPersona=p2.perId
		WHERE b.benFechaRetiro=ra.roaFechaRetiro and p.perNumeroIdentificacion=@redNumeroIdentificacionCotizante and p.perTipoIdentificacion=@redTipoIdentificacionCotizante
		and p3.perNumeroIdentificacion=@regNumeroIdentificacionAportante  and p3.perTipoIdentificacion=@regTipoIdentificacionAportante'
		,N'@redNumeroIdentificacionCotizante varchar(30),@redTipoIdentificacionCotizante varchar(30),@regNumeroIdentificacionAportante varchar(30),@regTipoIdentificacionAportante varchar(30)',
		@redNumeroIdentificacionCotizante=@redNumeroIdentificacionCotizante,@redTipoIdentificacionCotizante=@redTipoIdentificacionCotizante,@regNumeroIdentificacionAportante=@regNumeroIdentificacionAportante,@regTipoIdentificacionAportante=@regTipoIdentificacionAportante


end
else
begin
insert into #beneficiarios
EXEC sp_execute_remote
    CoreReferenceData,
    N'
    SELECT DISTINCT b.benId
    FROM persona p
    INNER JOIN afiliado a ON a.afiPersona = p.perId
    INNER JOIN beneficiario b ON b.benAfiliado = a.afiId
    INNER JOIN persona p2 ON p2.perId = b.benPersona
    INNER JOIN rolAfiliado ra ON ra.roaAfiliado = a.afiId
    WHERE
        p.perNumeroIdentificacion = @redNumeroIdentificacionCotizante
        AND p.perTipoIdentificacion = @redTipoIdentificacionCotizante AND b.benEstadoBeneficiarioAfiliado = ''ACTIVO''
    ',
    N'@redNumeroIdentificacionCotizante varchar(30), @redTipoIdentificacionCotizante varchar(30)',
    @redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante,
    @redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante;
end


	fetch next from cursorIds
	into
	@redTipoIdentificacionCotizante,@redNumeroIdentificacionCotizante,@regNumeroIdentificacionAportante,@regTipoIdentificacionAportante
	end
	close cursorIds;
	deallocate cursorIds;

	DECLARE @cadenaBenIds VARCHAR(MAX);
	SELECT @cadenaBenIds = STRING_AGG(CONVERT(VARCHAR(MAX), benId), ',')
    FROM #beneficiarios;

	    select
		canal as canal,novedadexistenteCore,tenId,tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
		,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion,tenEsIngreso,tenEsRetiro,tenFechaInicioNovedad,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad
		,tenTipoCotizante,tenPrimerApellido,tenSegundoApellido,tenPrimerNombre,tenSegundoNombre,tenCodigoDepartamento,tenCodigoMunicipio,tenModalidadRecaudoAporte,tenValor
		,tenEsEmpleadorReintegrable,tenEsTrabajadorReintegrable,tenRegistroDetalladoNovedad,tenEnProceso, @cadenaBenIds beneficiarios
	    from TemNovedad_val_proc
		where tenRegistroGeneral = @idRegistroGeneral
		and novedadexistenteCore = 0
		order by tenTipoIdCotizante, tenNumeroIdCotizante, isnull(tenFechaInicioNovedad, tenFechaFinNovedad), tenFechaFinNovedad, (case when tenTipoTransaccion = 'NOVEDAD_REINTEGRO' then 'A' else tenTipoTransaccion end)
END