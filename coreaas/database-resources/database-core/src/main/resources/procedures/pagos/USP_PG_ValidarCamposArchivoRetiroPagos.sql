/****** Object:  StoredProcedure [dbo].[USP_PG_ValidarCamposArchivoRetiroPagos]    Script Date: 13/12/2023 1:43:02 p. m. ******/

-- =============================================
-- Author: Keyner Vides
-- Create date: 2023-12-13
-- Description:	Procedimiento almacenado que obtiene los valores del archivo que se cargan de los retiros del tercero pagador y llama el proceso almacenado USP_VALIDAR_CAMPOS_ARCHIVO_RETIRO_CUENTA para conciliar los campos del archivo con los de la cuenta del administrador de subsidio. 
-- =============================================
CREATE PROCEDURE [dbo].[USP_PG_ValidarCamposArchivoRetiroPagos]
@IdDocumentoRetiroTerceroPagador VARCHAR(255),
@NumeroVersionDocumento  VARCHAR(50),
@NombreTerceroPagador VARCHAR(200)
AS

---=== para validacion 
--declare @IdDocumentoRetiroTerceroPagador VARCHAR(255) = '5f68da1d-a867-4b65-84be-957ceea672b5.txt'
--declare @NumeroVersionDocumento  VARCHAR(50) = '1702494055089768'
--declare @NombreTerceroPagador VARCHAR(200) = 'susuerte'


	--Variable que contienen la información de los campos de los archivos de cada registros
	declare @IdArchivoRetiro bigint
	declare @isConciliado bit
	set @isConciliado = 0

	--id para saber si hay registros previamente conciliados con ese identificador de tercero pagador
	declare @IdRegistroPrevio bigint

	declare @RegistroPreviamenteConciliado bit
	set @RegistroPreviamenteConciliado = 0

	drop table if exists #ConsultarDatosArchivo
	create table #ConsultarDatosArchivo (arrId bigint, rarId bigint, rarIdTransaccionTerceroPagador varchar(200), rarTipoIdentificacionAdminSubsidio varchar(20) , rarNumeroIdentificacionAdminSubsidio varchar(16), rarValorRealTransaccion numeric, rarFechaTransaccion varchar(50) , rarHoraTransaccion varchar(50), rarCodigoDepartamento varchar(2), rarCodigoMunicipio varchar(6))

	declare @CountUno int
	declare @CountDos int
	  
	if exists (select * from ArchivoRetiroTerceroPagador inner join RegistroArchivoRetiroTerceroPagador on arrId = rarArchivoRetiroTerceroPagador
		       where arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador and rarEstado is not null and rarEstado = 'SIN_CONCILIAR')
	begin 
		set @isConciliado = 1
	end 
	else
	begin
		 select @CountUno = count(*) from ArchivoRetiroTerceroPagador inner join RegistroArchivoRetiroTerceroPagador on arrId = rarArchivoRetiroTerceroPagador
	     where arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador and rarEstado is not null and rarEstado = 'SIN_CONCILIAR'
		 
	     select @CountDos= count(*) from ArchivoRetiroTerceroPagador inner join RegistroArchivoRetiroTerceroPagador on arrId = rarArchivoRetiroTerceroPagador
	     where arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador and rarEstado is not null
		 
	     if @CountUno = @CountDos
	     begin
			 set @isConciliado = 1
		 end  
	end 

    if @isConciliado = 1 --los registros del archivo no han sido conciliados previamente
	begin
	
		insert into #ConsultarDatosArchivo 
		select 
			arr.arrId, rar.rarId, rar.rarIdTransaccionTerceroPagador, rar.rarTipoIdentificacionAdminSubsidio,
			rar.rarNumeroIdentificacionAdminSubsidio, rar.rarValorRealTransaccion, rar.rarFechaTransaccion,
			rar.rarHoraTransaccion, rar.rarCodigoDepartamento, rar.rarCodigoMunicipio
		from ArchivoRetiroTerceroPagador arr with(nolock)
		inner join RegistroArchivoRetiroTerceroPagador rar with(nolock) on arr.arrId = rar.rarArchivoRetiroTerceroPagador
		where arr.arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador
		and arr.arrVersionDocumento = @NumeroVersionDocumento

	declare @RespuestaEstadoArchivo varchar(30) 
	set @RespuestaEstadoArchivo = 'PROCESADO'		

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'TIPO_IDENTIFICACION_ADMININISTRADOR_SUBSIDIO',cda.rarTipoIdentificacionAdminSubsidio,per.perTipoIdentificacion ,'No coincide el tipo de identificación del Administrador del subsidio',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where per.perTipoIdentificacion != cda.rarTipoIdentificacionAdminSubsidio
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'NUMERO_IDENTIFICACION_ADMININSTRADOR_SUBSIDIO',cda.rarNumeroIdentificacionAdminSubsidio,per.perNumeroIdentificacion ,'No coincide el número de identificación del Administrador del subsidio',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where per.perNumeroIdentificacion != cda.rarNumeroIdentificacionAdminSubsidio
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end


	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'VALOR_REAL_TRANSACCION',cda.rarValorRealTransaccion, cas.casValorRealTransaccion,'No coincide el valor real de la transacción',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where abs(cas.casValorRealTransaccion) != cda.rarValorRealTransaccion
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'FECHA_TRANSACCION',cast(cda.rarFechaTransaccion as date), cast(cas.casFechaHoraTransaccion as date),'No coincide la fecha de transacción',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where cast(cas.casFechaHoraTransaccion as date) != cast(cda.rarFechaTransaccion as date)
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	--begin
	--	insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador)
	--	select
	--		'HORA_TRANSACCION',convert(varchar(8),convert(time,cda.rarHoraTransaccion),108), convert(varchar(8),cas.casFechaHoraTransaccion,108),'No coincide la hora de transacción',cda.rarId
	--	from CuentaAdministradorSubsidio cas
	--	inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
	--	inner join Persona per ON adm.asuPersona = per.perId
	--	inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro
	--	inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
	--	inner join Municipio mun ON mun.munId = inf.infMunicipio
	--	inner join Departamento	dep ON dep.depId = mun.munDepartamento
	--	inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
	--	where convert(varchar(8),cas.casFechaHoraTransaccion,108) != convert(varchar(8),convert(time,cda.rarHoraTransaccion),108)
	--		if @@rowcount > 0
	--		begin
	--				set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
	--				print (@RespuestaEstadoArchivo)
	--		end
	--end

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'DEPARTAMENTO',cda.rarCodigoDepartamento, dep.depCodigo,'No coincide el Departamento del sitio de cobro',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where dep.depCodigo != cda.rarCodigoDepartamento
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'MUNICIPIO',cda.rarCodigoMunicipio, mun.munCodigo,'No coincide el Municipio del sitio de cobro',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		inner join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where mun.munCodigo != cda.rarCodigoMunicipio
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	begin
		insert into CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
		select 
			'IDENTIFICADOR_TRANSACCION_TERCERO_PAGADOR', 'IDENTIFICADOR_TRANSACCION_TERCERO_PAGADOR',cda.rarIdTransaccionTerceroPagador,'No coincide el identificador de tercero pagador',cda.rarId
		from CuentaAdministradorSubsidio cas
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId
		inner join SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
		inner join Infraestructura inf ON inf.infId = sip.sipInfraestructura
		inner join Municipio mun ON mun.munId = inf.infMunicipio
		inner join Departamento	dep ON dep.depId = mun.munDepartamento
		right join #ConsultarDatosArchivo cda on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		where cas.casIdTransaccionTerceroPagador is null
			if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					print (@RespuestaEstadoArchivo)
			end
	end

	declare @EstadoArchivo varchar(30)	
	select @EstadoArchivo = arrEstado from ArchivoRetiroTerceroPagador  where arrId = @IdArchivoRetiro
	print(concat('@EstadoArchivo :',@EstadoArchivo))

	if @EstadoArchivo IS NULL 
	begin
		
		update ArchivoRetiroTerceroPagador 
		set arrEstado = @RespuestaEstadoArchivo
		from ArchivoRetiroTerceroPagador art 
		inner join #ConsultarDatosArchivo cda on art.arrId = cda.arrId
	
		set @EstadoArchivo = @RespuestaEstadoArchivo
	end 
	
	--se actualiza el estado del registro que contiene los campos por conciliado o sin conciliar
	if @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
	begin
		begin
		update RegistroArchivoRetiroTerceroPagador 
		set rarEstado = 'SIN_CONCILIAR'
		from RegistroArchivoRetiroTerceroPagador rar
		inner join #ConsultarDatosArchivo cda on rar.rarId = cda.rarId
		inner join CampoArchivoRetiroTerceroPagador  car on rar.rarId = car.carRegistroArchivoRetiroTerceroPagador
		end
		begin
		update RegistroArchivoRetiroTerceroPagador 
		set rarEstado = 'CONCILIADO',rarCuentaAdministradorSubsidio = cas.casid
		from RegistroArchivoRetiroTerceroPagador rar
		inner join #ConsultarDatosArchivo cda on rar.rarId = cda.rarId
		left join CampoArchivoRetiroTerceroPagador  car on rar.rarId = car.carRegistroArchivoRetiroTerceroPagador
		inner join CuentaAdministradorSubsidio cas on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
		inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		inner join Persona per ON adm.asuPersona = per.perId 
		where car.carRegistroArchivoRetiroTerceroPagador is null
		
		if @@rowcount > 0
			begin
					set @RespuestaEstadoArchivo ='PROCESADO'
					print (@RespuestaEstadoArchivo)
					
					update ArchivoRetiroTerceroPagador 
					set arrEstado = @RespuestaEstadoArchivo
					from ArchivoRetiroTerceroPagador art 
					inner join #ConsultarDatosArchivo cda on art.arrId = cda.arrId
			end

		end
	end 

	else
		begin 
	
			update RegistroArchivoRetiroTerceroPagador 
			set rarEstado = 'CONCILIADO',
				rarCuentaAdministradorSubsidio = cas.casid
			from RegistroArchivoRetiroTerceroPagador rar
			inner join #ConsultarDatosArchivo cda on rar.rarId = cda.rarId
			inner join CuentaAdministradorSubsidio cas on cas.casIdTransaccionTerceroPagador = cda.rarIdTransaccionTerceroPagador
			inner join AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
			inner join Persona per ON adm.asuPersona = per.perId 
			
		end 
	end 
	else
	begin
		set @RegistroPreviamenteConciliado = 1
		insert into #ConsultarDatosArchivo 
		select 
			 arr.arrId, rar.rarId, rar.rarIdTransaccionTerceroPagador, rar.rarTipoIdentificacionAdminSubsidio,
			 rar.rarNumeroIdentificacionAdminSubsidio, rar.rarValorRealTransaccion, rar.rarFechaTransaccion,
			 rar.rarHoraTransaccion, rar.rarCodigoDepartamento, rar.rarCodigoMunicipio
		from ArchivoRetiroTerceroPagador arr with(nolock)
		inner join RegistroArchivoRetiroTerceroPagador rar with(nolock) on arr.arrId = rar.rarArchivoRetiroTerceroPagador
		where arr.arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador
		and arr.arrVersionDocumento = @NumeroVersionDocumento
		
		begin
			update RegistroArchivoRetiroTerceroPagador 
			set rarEstado = 'REGISTRO_PREVIAMENTE_CONCILIADO'
			from RegistroArchivoRetiroTerceroPagador rat
			inner join #ConsultarDatosArchivo cda on rat.rarId = cda.rarId 
		end 
	
	if @RegistroPreviamenteConciliado = 1
	begin	
		update ArchivoRetiroTerceroPagador 
		set arrEstado = 'PREVIAMENTE_PROCESADO'
		from ArchivoRetiroTerceroPagador rat
		inner join #ConsultarDatosArchivo cda on rat.arrId = cda.arrId 
	end
end;