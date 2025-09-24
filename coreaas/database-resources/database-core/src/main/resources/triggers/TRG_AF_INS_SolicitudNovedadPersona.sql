CREATE TRIGGER [dbo].[TRG_AF_INS_SolicitudNovedadPersona]
   ON  [dbo].[SolicitudNovedadPersona]
   AFTER INSERT
AS 
BEGIN
	SET NOCOUNT ON;	
    declare @sapEstadoSolicitud varchar (50)
	declare @sapRolAfiliado bigint
	declare @sapSolicitudGlobal bigint
	declare @soltipoTransaccion varchar(50)  
	declare @solresultadoProceso varchar(50)
	declare @solcanal varchar(50)
	declare @redId bigInt
	 DECLARE @emplId BIGINT

	DECLARE @afiPersona BIGINT
	 DECLARE @idAfiliado BIGINT;

    	SELECT @sapSolicitudGlobal = s.solId, @sapRolAfiliado=r.roaId, @sapEstadoSolicitud = sn.snoEstadoSolicitud, 
		@soltipoTransaccion = s.solTipoTransaccion, @solresultadoProceso = s.solResultadoProceso, @solcanal = s.solCanalRecepcion
		from solicitud as s
		inner join SolicitudNovedad as sn on s.solId = sn.snoSolicitudGlobal
		inner join inserted as snp on sn.snoId = snp.snpSolicitudNovedad
		inner join RolAfiliado as r on r.roaId = snp.snpRolAfiliado
		where s.solTipoTransaccion = 'NOVEDAD_REINTEGRO'

		declare @perNumIdValidarReintegro varchar (25) = null
		select @perNumIdValidarReintegro = perNumeroIdentificacion,@afiPersona=p.perId,@idAfiliado=a.afiId,@emplId=r.roaEmpleador
		from persona as p
		inner join Afiliado as a on p.perId = a.afiPersona
		inner join RolAfiliado as r on a.afiId = r.roaAfiliado
		where r.roaId = @sapRolAfiliado

		declare @tablaReintegro table (aplicarReintegro smallInt, origen varchar (200))
		insert @tablaReintegro
		execute sp_execute_remote pilaReferenceData, N'select aplicarReintegro from dbo.cotReintegro where perNumeroIdentificacion = @perNumIdValidarReintegro', N'@perNumIdValidarReintegro varchar(50)', @perNumIdValidarReintegro = @perNumIdValidarReintegro

		declare @aplicaReintegro  smallInt = 0
		set @aplicaReintegro = (select top 1 aplicarReintegro from @tablaReintegro)

		select @redId = spiRegistroDetallado
		from SolicitudNovedadPila as snp with (nolock)
		inner join SolicitudNovedad as sn with (nolock) on sn.snoId =snp.spiSolicitudNovedad
		where sn.snoSolicitudGlobal = @sapSolicitudGlobal

		declare @aplicarNovedad table (rdnAccion varchar (50), origen varchar(250))
		insert @aplicarNovedad
		execute sp_execute_remote pilaReferenceData, N'select rdnAccionNovedad from staging.RegistroDetalladoNovedad with (nolock) where rdnTipoNovedad = ''NOVEDAD_ING'' and rdnRegistroDetallado = @redId', N'@redId bigInt', @redId = @redId
		
		declare @rdnAccionNovedad varchar (50) = (select rdnAccion from @aplicarNovedad)
		IF @soltipoTransaccion='NOVEDAD_REINTEGRO' and @solresultadoProceso='APROBADA' and @aplicaReintegro = 1 and @rdnAccionNovedad = 'APLICAR_NOVEDAD' and (@solcanal='PILA' or @solcanal='APORTE_MANUAL')
		BEGIN 
			DECLARE @SolicitudAfiliacionPersona_aud TABLE(
				sapid bigint, sapEstadoSolicitud varchar(50), sapRolAfiliado bigint, sapSolicitudGlobal bigint
			)

		   INSERT dbo.SolicitudAfiliacionPersona (sapEstadoSolicitud, sapRolAfiliado, sapSolicitudGlobal)
		   OUTPUT INSERTED.sapid,INSERTED.sapEstadoSolicitud, INSERTED.sapRolAfiliado, INSERTED.sapSolicitudGlobal
		   INTO @SolicitudAfiliacionPersona_aud
			VALUES ('CERRADA', @sapRolAfiliado, @sapSolicitudGlobal)

			DECLARE @iNumeroRegistros BIGINT,
			@iRevision BIGINT;

			SELECT @iNumeroRegistros = COUNT(*) FROM @SolicitudAfiliacionPersona_aud

			IF @iNumeroRegistros > 0
			BEGIN
			EXEC [dbo].[USP_UTIL_GET_CrearRevision] 'com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona',@iNumeroRegistros,'','NovedadReintegro',@iRevision OUTPUT

			INSERT aud.solicitudafiliacionpersona_aud(sapId,REV,REVTYPE,sapEstadoSolicitud,sapRolAfiliado,sapSolicitudGlobal)
			SELECT sapid,@iRevision, 0,	sapEstadoSolicitud,	sapRolAfiliado,sapSolicitudGlobal
			FROM @SolicitudAfiliacionPersona_aud
			END
		execute [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion] @idAfiliado, @emplId, @afiPersona, 'ACTIVO', @sapRolAfiliado
		END	
END