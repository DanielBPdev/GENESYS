-- =============================================
-- Author: rcastillo
-- Create Date: 2024-12-08 
-- Description: Proceso para aplicar los retiros
-- =============================================
CREATE OR ALTER procedure [dbo].[USP_PG_AplicarRetiros]
(
@tipoIdAdmin varchar(20)
,@numeroIdAdmin varchar(16)
,@valorSolicitado bigint
,@usuario varchar(50)
,@idTransaccionTercerPagador varchar(50)
,@departamento varchar(5)
,@municipio varchar(5)
,@idPuntoCobro varchar(50)
,@usuarioGenesys varchar(50)
)
as

    set nocount on;
	
	begin try

		declare @fecha datetime = dbo.getLocalDate()

		declare @admSubsidio bigInt = -1
		select @admSubsidio = adm.asuId
		from dbo.Persona as p with (nolock)
		inner join dbo.AdministradorSubsidio as adm with (nolock) on p.perId = adm.asuPersona
		where p.perTipoIdentificacion = @tipoIdAdmin and p.perNumeroIdentificacion = @numeroIdAdmin

		declare @estadoUsuarioTer varchar(50) = null
		declare @nombreTercero varchar(50) = null
		select @estadoUsuarioTer = conEstado, @nombreTercero = conNombre
		from dbo.ConvenioTerceroPagador with (nolock)
		where conUsuarioGenesys = @usuario

		declare @saldoActual bigInt = -1
		select casId, cas.casValorRealTransaccion, cas.casTipoTransaccionSubsidio, casMedioDePago, casEstadoTransaccionSubsidio
		into #cuentaAdmin
		from dbo.CuentaAdministradorSubsidio as cas with(nolock)
		where cas.casAdministradorSubsidio = @admSubsidio and cas.casMedioDePagoTransaccion = 'EFECTIVO' 
		and cas.casEstadoTransaccionSubsidio = 'APLICADO' and cas.casTipoTransaccionSubsidio IN ('ABONO','AJUSTE_TRANSACCION_RETIRO_INCOMPLETA','RETIRO')

		
		select @saldoActual = sum(casValorRealTransaccion)
		from #cuentaAdmin
		

		declare @saldoAbonosDisponibles bigInt = 1
		select @saldoAbonosDisponibles = sum(casValorRealTransaccion)
		from #cuentaAdmin
		where casEstadoTransaccionSubsidio = 'APLICADO' and casTipoTransaccionSubsidio = 'ABONO'
		

		declare @casMedioDePago bigint = -1
		select @casMedioDePago = min(casMedioDePago)
		from #cuentaAdmin

		declare @sitioCobro bigInt = -1
		select top 1 @sitioCobro = sipId
		from dbo.SitioPago as sp with (nolock)
		inner join dbo.Infraestructura as inf with (nolock) on sp.sipInfraestructura = inf.infId
		inner join dbo.Municipio as mun with (nolock) on inf.infMunicipio = mun.munId
		inner join dbo.Departamento as dep with (nolock) on mun.munDepartamento = dep.depId
		where sp.sipActivo = 1 and mun.munCodigo = @municipio and dep.depCodigo = @departamento
		order by mun.munCodigo, sp.sipPrincipal desc


		create table #CuentaAdministradorSubsidio_aud ([casId] [bigint] NOT NULL,[casFechaHoraCreacionRegistro] [datetime] NOT NULL,[casUsuarioCreacionRegistro] [varchar](200) NOT NULL,[casTipoTransaccionSubsidio] [varchar](40) NOT NULL,[casEstadoTransaccionSubsidio] [varchar](25) NULL,
		[casEstadoLiquidacionSubsidio] [varchar](25) NULL,[casOrigenTransaccion] [varchar](30) NOT NULL,[casMedioDePagoTransaccion] [varchar](13) NOT NULL,[casNumeroTarjetaAdmonSubsidio] [varchar](50) NULL,[casCodigoBanco] [varchar](7) NULL,
		[casNombreBanco] [varchar](255) NULL,[casTipoCuentaAdmonSubsidio] [varchar](30) NULL,[casNumeroCuentaAdmonSubsidio] [varchar](30) NULL,[casTipoIdentificacionTitularCuentaAdmonSubsidio] [varchar](20) NULL,[casNumeroIdentificacionTitularCuentaAdmonSubsidio] [varchar](20) NULL,
		[casNombreTitularCuentaAdmonSubsidio] [varchar](200) NULL,[casFechaHoraTransaccion] [datetime] NOT NULL,[casUsuarioTransaccion] [varchar](200) NOT NULL,[casValorOriginalTransaccion] [numeric](19, 5) NOT NULL,[casValorRealTransaccion] [numeric](19, 5) NOT NULL,
		[casIdTransaccionOriginal] [bigint] NULL,[casIdRemisionDatosTerceroPagador] [varchar](200) NULL,[casIdTransaccionTerceroPagador] [varchar](200) NULL,[casNombreTerceroPagado] [varchar](200) NULL,[casIdCuentaAdmonSubsidioRelacionado] [bigint] NULL,
		[casFechaHoraUltimaModificacion] [datetime] NULL,[casUsuarioUltimaModificacion] [varchar](200) NULL,[casAdministradorSubsidio] [bigint] NOT NULL,[casSitioDePago] [bigint] NULL,[casSitioDeCobro] [bigint] NULL,[casMedioDePago] [bigint] NOT NULL,
		[casSolicitudLiquidacionSubsidio] [bigint] NULL,[casCondicionPersonaAdmin] [bigint] NULL,[casEmpleador] [bigint] NULL,[casAfiliadoPrincipal] [bigint] NULL,[casBeneficiarioDetalle] [bigint] NULL,[casGrupoFamiliar] [bigint] NULL,[casIdCuentaOriginal] [bigint] NULL,[casIdPuntoDeCobro] [varchar](200) NULL)

		create table #DetalleSubsidioAsignado_aud([dsaId] [bigint] NOT NULL,[dsaUsuarioCreador] [varchar](200) NOT NULL,[dsaFechaHoraCreacion] [datetime] NOT NULL,[dsaEstado] [varchar](20) NOT NULL,[dsaMotivoAnulacion] [varchar](40) NULL,[dsaDetalleAnulacion] [varchar](250) NULL,
		[dsaOrigenRegistroSubsidio] [varchar](30) NOT NULL,[dsaTipoliquidacionSubsidio] [varchar](60) NOT NULL,[dsaTipoCuotaSubsidio] [varchar](80) NOT NULL,[dsaValorSubsidioMonetario] [numeric](19, 5) NOT NULL,[dsaValorDescuento] [numeric](19, 5) NOT NULL,[dsaValorOriginalAbonado] [numeric](19, 5) NOT NULL,
		[dsaValorTotal] [numeric](19, 5) NOT NULL,[dsaFechaTransaccionRetiro] [date] NULL,[dsaUsuarioTransaccionRetiro] [varchar](200) NULL,[dsaFechaTransaccionAnulacion] [date] NULL,[dsaUsuarioTransaccionAnulacion] [varchar](200) NULL,[dsaFechaHoraUltimaModificacion] [datetime] NULL,
		[dsaUsuarioUltimaModificacion] [varchar](200) NULL,[dsaSolicitudLiquidacionSubsidio] [bigint] NOT NULL,[dsaEmpleador] [bigint] NOT NULL,[dsaAfiliadoPrincipal] [bigint] NOT NULL,[dsaGrupoFamiliar] [bigint] NOT NULL,[dsaAdministradorSubsidio] [bigint] NOT NULL,[dsaIdRegistroOriginalRelacionado] [bigint] NULL,
		[dsaCuentaAdministradorSubsidio] [bigint] NOT NULL,[dsaBeneficiarioDetalle] [bigint] NOT NULL,[dsaPeriodoLiquidado] [date] NOT NULL,[dsaResultadoValidacionLiquidacion] [bigint] NOT NULL,[dsaCondicionPersonaBeneficiario] [bigint] NULL,
		[dsaCondicionPersonaAfiliado] [bigint] NULL,[dsaCondicionPersonaEmpleador] [bigint] NULL,[dsaDetalleSolicitudAnulacionSubsidioCobrado] [bigint] NULL,[dsaNombreTerceroPagado] [varchar](200) NULL)

		declare @datosEntrada nvarchar(700) = null		

		;with jsonIn as (
		select @idTransaccionTercerPagador as 'idTransaccionTercerPagador', convert(varchar(150),@valorSolicitado) as 'valorSolicitado', @usuarioGenesys as 'emailUserDTO', @municipio as 'municipio', @tipoIdAdmin as 'tipoIdentificadorAdmin'
		,@departamento as 'departamento',@usuario as 'usuario',@numeroIdAdmin as 'numeroIdentificadorAdmin', @saldoAbonosDisponibles as 'saldoActualSubsidio', 'EFECTIVO' as medioDePago)
		select @datosEntrada = (select * from jsonIn for json path,include_null_values,without_array_wrapper)


		declare @idOperacionTranRet as table (id bigint)
		insert dbo.RegistroOperacionTransaccionSubsidio (rotAdministradorSubsidio, rotTipoOperacion,rotFechaHoraOperacionTransaccion,rotUsuarioOperacionTransaccion, rotParametrosIn)
		output inserted.rotId into @idOperacionTranRet
		values (@admSubsidio, 'SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO',@fecha, @usuarioGenesys, @datosEntrada)
		declare @idOpTranRet bigInt = (select id from @idOperacionTranRet)


		--=== 1. Validar que no hayan transacciones en proceso por el mismo admin
		if not exists (select * from dbo.transaccionTP2 as p where idAdministrador = @admSubsidio and isnull(operacion,'') = 'P')
				begin

					declare @numSal bigint = 0
					execute dbo.USP_PG_AplicarRetirosControlTransaccion @admSubsidio, @num = @numSal output
					
					--=== 2. Valida que la persona identificada con los parámetros de entrada tipoIdAdmin y numeroIdAdmin exista como administrador de subsidio en la base de datos
					if (@admSubsidio > 0)
						begin
							--=== 3. Valida que el usuario esté asociado a un convenio de tercero pagador
							if (@estadoUsuarioTer is not null)
								begin
									--=== 4. Valida que el estado del convenio de tercero pagador sea ‘ACTIVO’
										if (@estadoUsuarioTer = 'ACTIVO')
											begin
												--=== 5. Valida que exista un sitio de pago asociado a la llave departamento y municipio. Si el resultado es NULL, se debe consultar el departamento con el municipio parametrizado en el sistema asociado a la CCF
												if (@sitioCobro > 0)
													begin
														--=== 6. Valida si el resultado de la consulta de saldo (numeral 4.2 consulta de saldo) es igual o mayor al valor solicitado.
															if(@valorSolicitado >= @saldoActual)
																begin
																	if (@valorSolicitado = @saldoAbonosDisponibles) --=== Retiro Completo
																		begin

																			begin transaction

																			begin try

																				declare @iNumeroRegistros smallInt, @iRevision bigInt																					

																					insert dbo.CuentaAdministradorSubsidio (casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion
																						,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio
																						,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion
																						,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador
																						,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion
																						,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio
																						,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro)
																					output inserted.casId,inserted.casFechaHoraCreacionRegistro,inserted.casUsuarioCreacionRegistro,inserted.casTipoTransaccionSubsidio,inserted.casEstadoTransaccionSubsidio,inserted.casEstadoLiquidacionSubsidio
																					,inserted.casOrigenTransaccion,inserted.casMedioDePagoTransaccion,inserted.casNumeroTarjetaAdmonSubsidio,inserted.casCodigoBanco,inserted.casNombreBanco,inserted.casTipoCuentaAdmonSubsidio
																					,inserted.casNumeroCuentaAdmonSubsidio,inserted.casTipoIdentificacionTitularCuentaAdmonSubsidio,inserted.casNumeroIdentificacionTitularCuentaAdmonSubsidio,inserted.casNombreTitularCuentaAdmonSubsidio
																					,inserted.casFechaHoraTransaccion,inserted.casUsuarioTransaccion,inserted.casValorOriginalTransaccion,inserted.casValorRealTransaccion,inserted.casIdTransaccionOriginal,inserted.casIdRemisionDatosTerceroPagador
																					,inserted.casIdTransaccionTerceroPagador,inserted.casNombreTerceroPagado,inserted.casIdCuentaAdmonSubsidioRelacionado,inserted.casFechaHoraUltimaModificacion,inserted.casUsuarioUltimaModificacion
																					,inserted.casAdministradorSubsidio,inserted.casSitioDePago,inserted.casSitioDeCobro,inserted.casMedioDePago,inserted.casSolicitudLiquidacionSubsidio,inserted.casCondicionPersonaAdmin
																					,inserted.casEmpleador,inserted.casAfiliadoPrincipal,inserted.casBeneficiarioDetalle,inserted.casGrupoFamiliar,inserted.casIdCuentaOriginal,inserted.casIdPuntoDeCobro  into #CuentaAdministradorSubsidio_aud
																					values (@fecha,@usuarioGenesys,'RETIRO','FINALIZADO',null,'RETIRO','EFECTIVO',null,null,null,null,null,null,null,null,@fecha
																					,@usuarioGenesys,-@valorSolicitado,-@valorSolicitado,null,@idOpTranRet,@idTransaccionTercerPagador,@nombreTercero,null,@fecha,@usuarioGenesys
																					,@admSubsidio,null,@sitioCobro,@casMedioDePago,null,null,null,null,null,null,null,@idPuntoCobro)

																					declare @casIdRetiro bigInt = (select casId from #CuentaAdministradorSubsidio_aud)
																					update cas set cas.casIdCuentaAdmonSubsidioRelacionado = @casIdRetiro, casEstadoTransaccionSubsidio = 'COBRADO'
																					,cas.casUsuarioUltimaModificacion = @usuarioGenesys, casFechaHoraUltimaModificacion = @fecha
																					output inserted.casId,inserted.casFechaHoraCreacionRegistro,inserted.casUsuarioCreacionRegistro,inserted.casTipoTransaccionSubsidio,inserted.casEstadoTransaccionSubsidio,inserted.casEstadoLiquidacionSubsidio
																					,inserted.casOrigenTransaccion,inserted.casMedioDePagoTransaccion,inserted.casNumeroTarjetaAdmonSubsidio,inserted.casCodigoBanco,inserted.casNombreBanco,inserted.casTipoCuentaAdmonSubsidio
																					,inserted.casNumeroCuentaAdmonSubsidio,inserted.casTipoIdentificacionTitularCuentaAdmonSubsidio,inserted.casNumeroIdentificacionTitularCuentaAdmonSubsidio,inserted.casNombreTitularCuentaAdmonSubsidio
																					,inserted.casFechaHoraTransaccion,inserted.casUsuarioTransaccion,inserted.casValorOriginalTransaccion,inserted.casValorRealTransaccion,inserted.casIdTransaccionOriginal,inserted.casIdRemisionDatosTerceroPagador
																					,inserted.casIdTransaccionTerceroPagador,inserted.casNombreTerceroPagado,inserted.casIdCuentaAdmonSubsidioRelacionado,inserted.casFechaHoraUltimaModificacion,inserted.casUsuarioUltimaModificacion
																					,inserted.casAdministradorSubsidio,inserted.casSitioDePago,inserted.casSitioDeCobro,inserted.casMedioDePago,inserted.casSolicitudLiquidacionSubsidio,inserted.casCondicionPersonaAdmin
																					,inserted.casEmpleador,inserted.casAfiliadoPrincipal,inserted.casBeneficiarioDetalle,inserted.casGrupoFamiliar,inserted.casIdCuentaOriginal,inserted.casIdPuntoDeCobro into #CuentaAdministradorSubsidio_aud
																					from dbo.CuentaAdministradorSubsidio as cas with(rowlock)
																					inner join #cuentaAdmin as a on cas.casId = a.casId
																					where a.casTipoTransaccionSubsidio = 'ABONO'


																					update dsa set dsaUsuarioTransaccionRetiro = @usuarioGenesys, dsaFechaTransaccionRetiro = @fecha, dsaUsuarioUltimaModificacion = @usuarioGenesys, dsaFechaHoraUltimaModificacion = @fecha
																					output inserted.dsaId,inserted.dsaUsuarioCreador,inserted.dsaFechaHoraCreacion,inserted.dsaEstado,inserted.dsaMotivoAnulacion,inserted.dsaDetalleAnulacion,inserted.dsaOrigenRegistroSubsidio,inserted.dsaTipoliquidacionSubsidio
																					,inserted.dsaTipoCuotaSubsidio,inserted.dsaValorSubsidioMonetario,inserted.dsaValorDescuento,inserted.dsaValorOriginalAbonado,inserted.dsaValorTotal,inserted.dsaFechaTransaccionRetiro,inserted.dsaUsuarioTransaccionRetiro
																					,inserted.dsaFechaTransaccionAnulacion,inserted.dsaUsuarioTransaccionAnulacion,inserted.dsaFechaHoraUltimaModificacion,inserted.dsaUsuarioUltimaModificacion,inserted.dsaSolicitudLiquidacionSubsidio,inserted.dsaEmpleador
																					,inserted.dsaAfiliadoPrincipal,inserted.dsaGrupoFamiliar,inserted.dsaAdministradorSubsidio,inserted.dsaIdRegistroOriginalRelacionado,inserted.dsaCuentaAdministradorSubsidio,inserted.dsaBeneficiarioDetalle,inserted.dsaPeriodoLiquidado
																					,inserted.dsaResultadoValidacionLiquidacion,inserted.dsaCondicionPersonaBeneficiario,inserted.dsaCondicionPersonaAfiliado,inserted.dsaCondicionPersonaEmpleador,inserted.dsaDetalleSolicitudAnulacionSubsidioCobrado,inserted.dsaNombreTerceroPagado
																					into #DetalleSubsidioAsignado_aud
																					from dbo.DetalleSubsidioAsignado as dsa with(rowlock)
																					inner join #cuentaAdmin as a on dsa.dsaCuentaAdministradorSubsidio = a.casId
																					where a.casTipoTransaccionSubsidio = 'ABONO'

																					declare @revision bigInt, @miliseg bigInt
																					select @miliseg = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @fecha)
																					insert aud.Revision (revNombreUsuario, revTimeStamp) values (@usuario, @miliseg)
																					select @revision = SCOPE_IDENTITY()
																					insert aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) values ('com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio',1,@revision)

																					insert aud.CuentaAdministradorSubsidio_aud (casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,
																					casEstadoLiquidacionSubsidio,casOrigenTransaccion,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco, casNombreBanco,casTipoCuentaAdmonSubsidio,
																					casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,
																					casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,
																					casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,
																					casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,REV,REVTYPE)
																					select casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion,
																					casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,
																					casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,
																					casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,
																					casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,
																					casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,@revision,case when casId = @casIdRetiro then 0 else 1 end
																					from #CuentaAdministradorSubsidio_aud

																					insert aud.DetalleSubsidioAsignado_aud (dsaId,REV, REVTYPE, dsaUsuarioCreador,dsaFechaHoraCreacion,dsaEstado,dsaMotivoAnulacion,dsaDetalleAnulacion,dsaOrigenRegistroSubsidio,dsaTipoliquidacionSubsidio,dsaTipoCuotaSubsidio,
																					dsaValorSubsidioMonetario,dsaValorDescuento,dsaValorOriginalAbonado,dsaValorTotal,dsaFechaTransaccionRetiro,dsaUsuarioTransaccionRetiro,dsaFechaTransaccionAnulacion,dsaUsuarioTransaccionAnulacion,dsaFechaHoraUltimaModificacion
																					,dsaUsuarioUltimaModificacion,dsaSolicitudLiquidacionSubsidio,dsaEmpleador,dsaAfiliadoPrincipal,dsaGrupoFamiliar,dsaAdministradorSubsidio,dsaIdRegistroOriginalRelacionado,dsaCuentaAdministradorSubsidio
																					,dsaBeneficiarioDetalle,dsaPeriodoLiquidado,dsaResultadoValidacionLiquidacion,dsaCondicionPersonaBeneficiario,dsaCondicionPersonaAfiliado,dsaCondicionPersonaEmpleador,dsaDetalleSolicitudAnulacionSubsidioCobrado,dsaNombreTerceroPagado)
																					select dsaId,@revision as REV, 1 REVTYPE, dsaUsuarioCreador,dsaFechaHoraCreacion,dsaEstado,dsaMotivoAnulacion,dsaDetalleAnulacion,dsaOrigenRegistroSubsidio,dsaTipoliquidacionSubsidio,dsaTipoCuotaSubsidio,dsaValorSubsidioMonetario
																					,dsaValorDescuento,dsaValorOriginalAbonado,dsaValorTotal,dsaFechaTransaccionRetiro,dsaUsuarioTransaccionRetiro,dsaFechaTransaccionAnulacion,dsaUsuarioTransaccionAnulacion,dsaFechaHoraUltimaModificacion
																					,dsaUsuarioUltimaModificacion,dsaSolicitudLiquidacionSubsidio,dsaEmpleador,dsaAfiliadoPrincipal,dsaGrupoFamiliar,dsaAdministradorSubsidio,dsaIdRegistroOriginalRelacionado,dsaCuentaAdministradorSubsidio
																					,dsaBeneficiarioDetalle,dsaPeriodoLiquidado,dsaResultadoValidacionLiquidacion,dsaCondicionPersonaBeneficiario,dsaCondicionPersonaAfiliado,dsaCondicionPersonaEmpleador,dsaDetalleSolicitudAnulacionSubsidioCobrado,dsaNombreTerceroPagado
																					from #DetalleSubsidioAsignado_aud

																					update p set p.operacion = null from dbo.transaccionTP2 as p with(rowlock) where idAdministrador = @admSubsidio


																					declare @datosEntradaExitoso varchar(700) = null		
																					;with jsonIn2 as (
																					select 'TRANSACCION_EXITOSA' as resultado, @casIdRetiro as casIdRetiro, @idOpTranRet as idTransaccionRegistro)
																					select @datosEntradaExitoso = (select * from jsonIn2 for json path,include_null_values,without_array_wrapper)
																					
																					update dbo.RegistroOperacionTransaccionSubsidio with(rowlock) set rotParametrosOut = @datosEntradaExitoso where rotId = @idOpTranRet

																					select @idOpTranRet as idOpeTranSub, @casIdRetiro as idTransaccionRetiro
																					
																				commit transaction

																				end try
																				begin catch
																					if @@TRANCOUNT > 0
																						rollback transaction;

																						insert dbo.transaccionTP_log (idAdministrador, fecha, datosEntrada, error)
																						values (@admSubsidio,@fecha, concat('Datos de entrada , ', @idPuntoCobro, ', ',@idTransaccionTercerPagador, ', ', @nombreTercero),concat('Error -->>', ERROR_MESSAGE()))
																						select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'IDENTIFICADOR_TERCERO_PAGADOR' as mensaje --== Se deja este mensaje como error de llave casIdPuntoDeCobro, casIdTransaccionTerceroPagador, casNombreTerceroPagado

																				end catch

																		end
																	else
																		begin
																			select 'Retiro parcial'	
																		end
																end
															else
																begin
																	
																	if exists (select 1 from dbo.CuentaAdministradorSubsidio as cas with (nolock) where casAdministradorSubsidio = @admSubsidio
																	and casNombreTerceroPagado = @usuario and casIdTransaccionTerceroPagador = @idTransaccionTercerPagador and casIdPuntoDeCobro = @idPuntoCobro)
																		begin
																			select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'IDENTIFICADOR_TERCERO_PAGADOR' as mensaje
																		end
																	else
																		begin
																			select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'ERROR_NO_VALOR_SOLICITADO_NO_DISPONIBLE' as mensaje			
																		end
																end
													end
												else
													begin
														select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'SITIO_PAGO_NO_ENCONTRADO' as mensaje	
													end
											end
										else
											begin
												select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'ERROR_USUARIO_CONVENIO_INACTIVO' as mensaje	
											end
								end
							else
								begin
									select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'ERROR_USUARIO_CONVENIO' as mensaje
								end
						end
					else
						begin
							select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'ERROR_ADMINISTRADOR_SUBSIDIO' as mensaje
						end

				end
			else
				begin
					select @idOpTranRet as idOpeTranSub, 0 as idMensaje, 'IDENTIFICADOR_TERCERO_PAGADOR' as mensaje
				end


		update p set p.operacion = null from dbo.transaccionTP2 as p with(rowlock) where idAdministrador = @admSubsidio


	end try
	begin catch

	 SELECT ERROR_NUMBER() AS ErrorNumber,
        ERROR_SEVERITY() AS ErrorSeverity,
        ERROR_STATE() AS ErrorState,
        ERROR_PROCEDURE() AS ErrorProcedure,
        ERROR_LINE() AS ErrorLine,
        ERROR_MESSAGE() AS ErrorMessage;
	end catch