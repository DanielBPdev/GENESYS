package com.asopagos.subsidiomonetario.pagos.business.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.entidades.subsidiomonetario.pagos.*;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.ArchivoRetiroTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoRetiroTercerPagadorEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoTransDetaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoOperacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.pagos.dto.AbonosSolicitudAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ArchivoRetiroTerceroPagadorEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DescuentoSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionMontoLiquidadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoBancoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GruposMedioTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.IncosistenciaConciliacionConvenioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoDetallesSubsidioAgrupadosDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionValidacionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaReexpedicionDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ListadoSubsidiosAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResumenListadoSubsidiosAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RetiroCandidatoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioConsultaCambioPagosDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TarjetaRetiroCandidatoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TempArchivoRetiroTerceroPagadorEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionConsultadaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleBandejaTransaccionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GestionDeTransaccionesDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Local
public interface IConsultasModeloCore {

    /**
     * Metodo que registrar la carga del archivo de retiro del tercero pagador
     *
     * @param cargueArchivoRetiro objeto que contiene información relevante de la caarga del archivo de retiro para ser
     *                            almacenada en la base de datos.
     * @return identificador de la carga del archivo de retiro creada en la base de datos
     */
    public Long registrarCargueArchivoTerceroPagador(ArchivoRetiroTerceroPagador cargueArchivoRetiro);

    /**
     * Metodo encargado de crear el registro de cada archivo de retiro del tercer pagador
     * y cada campo
     *
     * @param retirosCandidatos     registros de retiro que son candidatos a ser conciliados despues de la validación en base de datos
     * @param idCargueArchivoRetiro identificador que tiene registrado en base de datos el cargue del archivo de retiro
     */
    public void crearRegistrosCamposArchivoTerceroPagador(List<RetiroCandidatoDTO> retirosCandidatos, Long idCargueArchivoRetiro);

    /**
     * Metodo encargado de registrar la solicitud de convenio y el convenio.
     *
     * @param convenio variable que contiene la información relevante para crear la solicitud del registro
     *                 del convenio y el convenio.
     * @return identificador de la solicitud global relacionada con la solicitud del registro del convenio
     */
    public Long crearSolicitudRegistroConvenio(ConvenioTercerPagadorDTO convenio);

    /**
     * Método que se encarga de registrar los detalles de subsidios asignados
     * asociado a un abono (cuenta de administrador del subsidio)
     *
     * @param listaDetallesSubsidioAsignado lista que contiene los detalles de subsidios asignados para ser registrados.
     */
    public void crearDetallesSubsidiosAsignados(List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidioAsignado);

    /**
     * Método que se encarga de registrar una cuenta de administrador de subsidio
     * asignado con sus respectivos detalles de subsidios asignados.
     *
     * @param cuentaAdministradorSubsidioDTO variable que contiene toda la información de la cuenta del administrador de subsidio
     * @return id de la cuenta del administrador de subsidio
     */
    public Long crearCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO);

    /**
     * Método que se encarga de registrar una cuenta de administrador de subsidio
     * asignado con sus respectivos detalles de subsidios asignados.
     *
     * @param cuentaAdministradorSubsidioDTO variable que contiene toda la información de la cuenta del administrador de subsidio
     * @return id de la cuenta del administrador de subsidio
     */
    public CuentaAdministradorSubsidio crearCuentaAdministradorSubsidioCuenta(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO);

    /**
     * Metodo que busca una cuenta del administrador del subsidio a partir del id.
     *
     * @param idCuentaAdmonSubsidio identificador de base de datos de la cuenta del administrador del subsidio.
     * @return DTO de la cuenta del administrador del subsidio.
     */
    public CuentaAdministradorSubsidioDTO consultarCuentaAdministradorSubsidio(Long idCuentaAdmonSubsidio);

    /**
     * Metodo encargado de actualizar la información de una cuenta de administrador del subsidio.
     *
     * @param cuentaAdministradorSubsidioDTO Información de la cuenta de administrador del subsidio que se actualizara en la base de datos.
     */
    public void actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO);

    /**
     * Metodo encargado de actualizar la información de una cuenta de administrador del subsidio.
     *
     * @param cuentaAdministradorSubsidioDTO Información de la cuenta de administrador del subsidio que se actualizara en la base de datos.
     */
    public void actualizarCuentaAdministradorSubsidioValor(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO);

    /**
     * @param detalleSubsidioAsignadoDTO
     */
    public void actualizarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO);

    /**
     * Metodo que obtiene los id de los detalles de subsidio asignado y los retorna como una lista de long
     *
     * @param listaDetalles lista de detalles de subsidios asignados
     * @return lista de Long con los id de los detalles.
     */
    public List<Long> obtenerListaIdsDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> listaDetalles);

    /**
     * Metodo encargado de buscar los detalles de subsidio asignados que no va a ser anulados
     * por medio de una diferencia con la lista que entra por parametros de los que si seran anulados.
     *
     * @param listaDetallesDTO lista de id de los detalles de subsidio asignado que serán anulados.
     * @return lista de DTOs con los detalles de subsidio asignados que no serán anulados
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsinadosNoAnulados(List<Long> listaDetallesDTO,
                                                                                        Long idCuentaAdmonSubsidio);

    /**
     * Metodo encargado de crear una transacción fallida.
     *
     * @param transaccionFallidaDTO información relevante del porque ocurrio un fallo en la transacción.
     * @return id de la transacción que se registro en base de datos.
     */
    public Long crearTransaccionFallida(TransaccionFallidaDTO transaccionFallidaDTO);

    /**
     * Metodo encargado de actualizar una transacción fallida
     *
     * @param transaccionFallidaDTO información que se actualizara en la entidad de la transacción fallida.
     * @return id de la transacción que se actualizo.
     */
    public Long actualizarTransaccionFallida(TransaccionFallidaDTO transaccionFallidaDTO);

    /**
     * Nueva mejora del método en el cual se cambia de una consulta JPQL a Nativa por cuestiones de rendimiento. glpi 62810
     * Metodo que se encarga de consultar las transacciones fallidas según el rango de fechas.
     * Si las fechas están en null, se trae todas las transacciones desde la fecha actual hasta lops ultimos 5 días.
     *
     * @param fechaInicial fecha inicial que tiene el rango de busqueda de las transacciones.
     * @param fechaFinal   fecha final que tiene el rango de busqueda de las transacciones.
     * @return lista de transacciones fallidas.
     */
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasPorFechas(Long fechaInicial, Long fechaFinal);

    /**
     * Metodo que se encarga de consultar las transacciones fallidas según el rango de fechas.
     * Si las fechas están en null, se trae todas las transacciones desde la fecha actual hasta lops ultimos 5 días.
     *
     * @param fechaInicial fecha inicial que tiene el rango de busqueda de las transacciones.
     * @param fechaFinal   fecha final que tiene el rango de busqueda de las transacciones.
     * @return lista de transacciones fallidas.
     */
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasPorFechasDeprecated(Long fechaInicial, Long fechaFinal);

    /**
     * Metodo encargado de consultar los detalles de subsidios asignados que están asociados
     * a una cuenta de administrador del subsidio (abono)
     *
     * @param idCuentaAdminSubsidio identificador de la cuenta del administrador del subsidio.
     * @return lista de detalles de subsidios asignados a un abono.
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbono(Long idCuentaAdminSubsidio);

    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios que sean de tipo abono,
     * con estado enviado y que tengan el medio de pago bancos (transferencia) relacionado.
     *
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancos();

    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios a partir de un filtro.
     *
     * @param transaccionConsultada dto que contiene los filtros para la consulta
     * @param lstIdCuentas          lista de id de los abonos
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarTransacciones(DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada);

    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios a partir de un filtro de forma paginada.
     *
     * @param transaccionConsultada dto que contiene los filtros para la consulta
     * @param lstIdCuentas          lista de id de los abonos
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesPaginada(UriInfo uriInfo,
                                                                               HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada);

    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios a partir de un filtro de forma paginada.
     *
     * @param transaccionConsultada dto que contiene los filtros para la consulta
     * @param lstIdCuentas          lista de id de los abonos
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<Object[]> consultarTransaccionesSP(UriInfo uriInfo,
                                                   HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada, Integer offSetParametro);

    /**
     * Método encargado de consultar los detalles de subsidios asignados por medio
     * de filtros.
     *
     * @param detalleConsultado dto que contiene todos los filtros con los cuales se realizara la consulta.
     * @return lista de detalles de subsidios asignados.
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetalles(DetalleTransaccionAsignadoConsultadoDTO detalleConsultado);

    /**
     * Método encargado de consultar los detalles de subsidios asignados por medio
     * de filtros.
     *
     * @param detalleConsultado dto que contiene todos los filtros con los cuales se realizara la consulta.
     * @return lista de detalles de subsidios asignados.
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesPaginado(DetalleTransaccionAsignadoConsultadoDTO detalleConsultado, UriInfo uri, HttpServletResponse response);

    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios a partir de un filtro mediante un SP.
     *
     * @param transaccionConsultada dto que contiene los filtros para la consulta
     * @param lstIdCuentas          lista de id de los abonos
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<Object[]> consultarDetallesSP(UriInfo uriInfo,
                                              HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada, Integer offsetParametro);


    /**
     * Metodo encargado de consultar las cuentas de administradores de subsidios y sus detalles a partir de un filtro mediante un SP.
     *
     * @param uriInfo
     * @param response
     * @param transaccionConsultada filtros para consultar
     * @param offsetParametro
     * @return
     */
    public List<Object[]> consultarTransDetallesSP(UriInfo uriInfo,
                                                   HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada, Integer offsetParametro);


    /**
     * Metodo encargado de consultar las cuentas de los administradores por diferentes filtros mediante un SP
     *
     * @param uriInfo
     * @param response
     * @param detallesTransacciones
     * @param offsetParametro
     * @return
     */
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesTodosFiltrosSP(UriInfo uriInfo, HttpServletResponse response,
                                                                                     DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones);

    /**
     * Metodo encargado de consultar las transacciones de las cuentas de administradores de subsidios
     * y los detalles asignados por medio de filtros.
     *
     * @param transaccionDetalleSubsidio dto que contiene todos los filtros con los cuales se realizara la consulta.
     * @return lista de cuentas de administradores de subsidios con sus respectivos detalles.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesDetalles(
            DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio);

    /**
     * Método encargado de consultar las cuentas de administradores de subsidios que correspondan a un mismo
     * administrador del subsidio y la cuenta este en estado APLICADO.
     *
     * @param tipoIdAdmin   tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin numero de identificación del administrador del subsidio
     * @param medioDePago   medio de pago por el cual se buscara consultar la cuenta.
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarRegistrosAbonosParaCalcularSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, TipoMedioDePagoEnum medioDePago);

    /**
     * Metodo encargado de registrar todas las operaciones (transacciones) en linea
     * que se realizan (Consultar Saldo, Solicitar Retiro, Confirmar Valor Entregado, Solicitar retiro, confirmar valor entregado (susuerte
     * y ventanilla), Solicitar retiro (persona autorizada))
     * HU-31-203
     *
     * @param tipoDeOperacion  tipo de operación que se realizo y sera registrada.
     * @param parametrosIN     parametros que recibio dicha operación para ser realizada.
     * @param parametrosOUT    parametros que saldrán de dicha operación.
     * @param usuarioOperacion nombre de usuario que realizo la operación.
     * @param idAdminSubsidio  identificador del administrador del subsidio.
     * @return identificador de la operación registrada en base de datos.
     */
    public Long registrarOperacionesSubsidio(TipoOperacionSubsidioEnum tipoDeOperacion, String parametrosIN, String usuarioOperacion,
                                             Long idAdminSubsidio);

    /**
     * Metodo encargado de consultar el sitio de pago según el municipio
     *
     * @param municipio código del municipio por el cual se va a realizar la busqueda.
     * @return identifificador del sitio de pago
     */
    public Long consultarSitioDePago(String municipio);

    /**
     * Metodo encargado de consultar el departamento según el municipio
     *
     * @param municipio código del municipio por el cual se va a realizar la busqueda.
     * @return identifificador del departamento
     */
    public String consultarDepartamentoPorMunicipio(String codMunicipio);

    /**
     * Metodo encargado de consultar el usuario que se registra en la transacción de retiro
     * esta asociado con un convenio.
     *
     * @param usuario
     * @return nombre del convenio.
     */
    public String consultarNombreTercerPagadorConvenio(String usuario);

    /**
     * Metodo encargado de consultar todos los convenios del tercero pagador que estan registrados.
     * HU-31-210
     *
     * @return lista de convenios del tercero pagador.
     */
    public List<ConvenioTercerPagadorDTO> consultarConveniosTercerPagador();

    /**
     * Metodo encargado de consultar todos los convenios del tercero pagador que estan registrados.
     * HU-31-210
     *
     * @return lista de convenios del tercero pagador.
     */
    public ConvenioTercerPagadorDTO consultarConvenioTercerPagador(Long idConvenio);


    /**
     * Metodo encargado de consultar todos los registros que se encuentran en la cuenta
     * del administrador del subsidio.
     *
     * @return lista de cuentas de administradores de subsidios.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradoresSubsidio();

    /**
     * Metodo encargado de consultar los detalles de subsidios asignados a partir de los IDs
     *
     * @param listaIdsDetalles lista de identificadores de los detalles de subsidios asignados a ser consultados.
     * @return lista de detalles de subsidios asignados DTO.
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesDTOPorIDs(List<Long> listaIdsDetalles);

    /**
     * Metodo encargado de buscar el identificador de la persona asociada para autorizar el cobro de la cuenta
     * del administrador de subsidios
     *
     * @param idCuentaAdminSubsidio identificador de la base de datos del registro de la cuenta del administrador de subsidio.
     * @return identificador de la persona.
     */
    public Long consultarPersonaAsociadaPorIdCuentaAdminSubsidio(Long idCuentaAdminSubsidio);

    /**
     * Metodo encargado de actualizar la información del convenio del tercero pagador
     *
     * @param convenioTercerPagadorDTO información del convenio que será actualizada.
     * @return id del convenio que se actualizo.
     */
    public Long actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioTercerPagadorDTO);

    /**
     * Metodo encargado de consultar los registros de la cuenta del administrador de subsidio por medio de unos filtros,
     * para obtener los informes de retiros
     *
     * @param suConsumoDTO
     * @return
     */
    public List<CuentaAdministradorSubsidioDTO> consultarInformesRetiros(SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO);

    /**
     * Metodo encargado de generar el listado de los subsidios monetarios candidatos a ser anulados por perdida de derecho
     * por medio de los filtros ingresados.
     *
     * @param suConsumoDTO DTO que contiene los filtros para generar el listado de subsidios monetarios a ser anulados.
     * @return lista de subsidios monetarios a ser anulados.
     */
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> generarListadoSubsidiosAnularPorPerdidaDeDerecho(
            SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO);

    /**
     * Metodo encargado de llamar el SP para verificar desde la base de datos
     * si los datos coinciden con los registros de la cuenta
     *
     * @param identificadorDocumento identificador del archivo que se registro.
     * @param versionDocumento       versión del archivo que se registro
     * @param nombreTerceroPagador   nombre del tercero pagador (nombre convenio)
     */
    public void compararRegistrosCamposArchivoTerceroPagadorSP(String identificadorDocumento, String versionDocumento,
                                                               String nombreTerceroPagador);

    /**
     * Metodo que obtiene si el estado de la conciliación de los datos del archivo de tercero
     * pagador fueron procesados con o sin inconsistencias.
     *
     * @param identificadorDocumento identificador del archivo que se registro
     * @param versionDocumento       versión del archivo que se registro.
     * @return PROCESADO, PROCESADO_CON_INCOSISTENCIAS o PREVIAMENTE_PROCESADO.
     */
    public EstadoArchivoRetiroTercerPagadorEnum consultarEstadoArchivoRetiroTerceroPagador(String identificadorDocumento,
                                                                                           String versionDocumento);

    /**
     * Metodo que obtiene las inconsistencias del archivo de retiro del tercero pagador
     * que se encontraron en la conciliación con la cuenta.
     *
     * @param idArchivoRetiroTerceroPagador identificador principal del registro que contiene las inconsistencias
     * @return lista de incosistencias.
     */
    public List<IncosistenciaConciliacionConvenioDTO> consultarInconsistenciasArchivoRetiroTerceroPagador(
            Long idArchivoRetiroTerceroPagador);

    /**
     * Metodo que actualiza los estados de las cuentas del administrador de subsidio cuyos registros de retiros del tercero pagador
     * fueron conciliados.
     *
     * @param idArchivoRetiroTerceroPagador identificador principal del registro del archivo de retiros del tercero pagador
     */
    public void actualizarEstadoRetirosConciliadosTerceroPagador(Long idArchivoRetiroTerceroPagador);

    /**
     * Metodo encargado de obtener los subsidios monetarios candidatos para reemplazar el medio de pago
     * asociados a ellos.
     *
     * @param cambioPagosDTO DTO que contiene los filtros para la consulta para buscar los subsidios monetarios.
     * @return lista subsidios monetarios candidatos.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarSubsidiosCambioMedioDePago(SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar las transacciones de subsidios asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @param transaccionDetalleSubsidio <code>DetalleTransaccionAsignadoConsultadoDTO</code>
     *                                   dto que contiene todos los filtros con los cuales se realizara la consulta
     * @return <code>List<CuentaAdministradorSubsidioDTO></code>
     * lista de transacciones de abono cobradas de cuentas de administradores de subsidios con su respectivo detalle.
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesAbonoCobrados(
            DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio);

    /**
     * <b>Descripción:</b>Método encargado de realizar el registro de anulacion de subsidio cobrado.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @param solicitudAnulacionSubsidioCobradoDTO <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *                                             Representa la información que representa los abonos cobrados asociados a una solicitud de anulación de subsidio cobrado
     * @param nombreUsuario                        <code>String</code>
     *                                             Representa el nombre de usuario de quien realiza el registro de una solicitud de anulación de subsidio cobrado
     * @return <code>List<SolicitudAnulacionSubsidioCobradoDTO></code>
     * representa la información que representa los abonos cobrados asociados a una solicitud de anulación de subsidio cobrado
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public SolicitudAnulacionSubsidioCobradoDTO registrarAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, String nombreUsuario);

    /**
     * Método encargado de mostrar los nombres de los convenios de terceros pagadores registrados.
     *
     * @return <code>List<ConvenioTercerPagadorDTO></code>
     * lista de convenios con los nombres e identificadores.
     */
    public List<ConvenioTercerPagadorDTO> mostrarNombreConveniosTerceroPagador();

    /**
     * Metodo encargado de actualizar el registro de operaciones que se realizan en linea.
     *
     * @param identificadorRespuesta identificador del registro de operación que tiene en base de datos.
     * @param parametrosOUT          parametros de salida de dicha operación.
     * @param tiempo                 Tiempo de ejecucion de la peticion.
     * @param url                    Url de la peticion
     *
     */
    public void actualizarRegistroOperacionSubsidio(Long identificadorRespuesta, String parametrosOUT, String tiempo, String url);

    /**
     * Metodo encargado de buscar la existencia del identificador de transacción del tercero pagador
     * al momento de realizar un retiro.
     *
     * @param idTransaccionTercerPagador identificador del tercero pagador por el cual se realizara el retiro
     * @return True si existe el identificador, False de lo contrario.
     */
    public CuentaAdministradorSubsidioDTO buscarRetiroPorIdTransaccionTerceroPagadorRetiro(String idTransaccionTercerPagador, String usuario, String idPuntoCobro);

    /**
     * Metodo encargado de buscar los medios de pagos relacionados a un administrador de subsidio por medio
     * del grupo familiar asociado.
     *
     * @param idAdminSubsidio
     * @return
     */
    public List<TipoMedioDePagoEnum> consultarMediosDePagoRelacionadosAdminSubsidio(Long idAdminSubsidio);

    /**
     * Metodo encargado de buscar los medios de pagos relacionados a un administrador de subsidio dependiendo
     * del tipo de medio de pago.
     *
     * @param medioDePago     <code>TipoMedioDePagoEnum</code>
     *                        tipo de medio de pago asociado a los registros del administrador de subsidio.
     * @param idAdminSubsidio <code>Long</code>
     *                        Identificador del administrador del subsidio por el cual se buscara los medios de pagos asociados.
     * @param lstMediosDePago <code>List<Long></code>
     *                        Lista de identificadores de medios de pagos asociados al administrador del subsidio por medio de los abonos.
     * @return lista de registros de medios de pagos del administrador del subsidio.
     */
    public List<MedioDePagoModeloDTO> consultarMedioDePagoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio, List<Long> lstIdsCuentas);

    /**
     * Metodo encargado de buscar los medios de pagos inactivos relacionados a un administrador de subsidio dependiendo
     * del tipo de medio de pago.
     *
     * @param medioDePago     <code>TipoMedioDePagoEnum</code>
     *                        tipo de medio de pago asociado a los registros del administrador de subsidio.
     * @param idAdminSubsidio <code>Long</code>
     *                        Identificador del administrador del subsidio por el cual se buscara los medios de pagos asociados.
     * @param lstMediosDePago <code>List<Long></code>
     *                        Lista de identificadores de medios de pagos asociados al administrador del subsidio por medio de los abonos.
     * @return lista de registros de medios de pagos del administrador del subsidio.
     */
    public List<MedioDePagoModeloDTO> consultarMedioDePagoInactivoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio, List<Long> lstMediosDePago);


    /**
     * Metodo encargado de obtener la lista de detalles de subsidios asignados que están asociados con las cuentas
     * de administrador de subsidio para calcular la consulta de saldo por un medio de pago determinado.
     *
     * @param listaCuentaAdminSubsidio Lista de cuentas de administradores de subsidios.
     * @return lista de detalles de subsidios asignados relacionados con las cuentas.
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(
            List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio);

    /**
     * Metodo encargado de traer los abonos que fueron retirados y quedaron en estado 'SOLICITADO'
     * por la solicitud de retiro por un administrador de subsidio, con un identificador de transacción
     * de tercero pagador único.
     *
     * @param tipoIdAdmin                Tipo de identificación del administrador del subsidio.
     * @param numeroIdAdmin              Número de identificación del administrador del subsidio.
     * @param idTransaccionTercerPagador Identificador de transacción del tercero pagador.
     * @return Lista de cuentas de administradores de subsidio asociado a un administrado de subsisido que están asociado al identificador
     * de transacción de tercero pagador de una solicitud de retiro
     * con estado 'SOLICITADO'
     */
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, String idTransaccionTercerPagador);

    /**
     * Metodo encargado de traer los abonos que fueron retirados y quedaron en estado 'SOLICITADO'
     * por la solicitud de retiro por un administrador de subsidio, con un identificador de transaccion y
     * punto de pago unicos
     *
     * @param tipoIdAdmin                Tipo de identificación del administrador del subsidio.
     * @param numeroIdAdmin              Número de identificación del administrador del subsidio.
     * @param idTransaccionTercerPagador Identificador de transacción del tercero pagador.
     * @param idPuntoCobro               Identificador de punto de cobro (CC tercero pagador)
     * @return Lista de cuentas de administradores de subsidio asociado a un administrado de subsisido que están asociado al identificador
     * de transacción de tercero pagador de una solicitud de retiro
     * con estado 'SOLICITADO'
     */
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum tipoIdAdmin,
                                                                                                  String numeroIdAdmin, String idTransaccionTercerPagador, String usuario, String idPuntoCobro);

    /**
     * Metodo encargado de obtener el valor del retiro que se origino por un administrador de subsidio en especifico, asociado
     * a un identificador de subsidio asignado.
     *
     * @param tipoIdAdmin                Tipo de identificación del administrador del subsidio.
     * @param numeroIdAdmin              Número de identificación del administrador del subsidio.
     * @param idTransaccionTercerPagador Identificador de transacción del tercero pagador.
     * @return Valor del retiro.
     */
    public BigDecimal obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(TipoIdentificacionEnum tipoIdAdmin,
                                                                                    String numeroIdAdmin, String idTransaccionTercerPagador);

    /**
     * Metodo encargado de realizar el registro de la persona autorizada para realizar un retiro.
     *
     * @param idPersona        Identificador principal de la base de datos de la persona autorizada.
     * @param idRetiroCuenta   Identificador principal de la Cuenta de administrador de subsidio de tipo retiro.
     * @param documentoSoporte <code>DocumentoSoporteModeloDTO</code>
     *                         DTO de fovis que contiene la información documental de la autorización de la persona
     */
    public void registrarPersonaAutorizadaParaRealizarRetiro(Long idPersona, Long idRetiroCuenta,
                                                             DocumentoSoporteModeloDTO documentoSoporte);

    /**
     * <b>Descripción:</b>Metodo que permite consultar la dispersión de una liquidación masiva que ya haya sido aprobada en segundo nivel.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     *
     * @param numeroRadicacion <code>String</code>
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     * @author rlopez
     */
    public DispersionMontoLiquidadoDTO consultarDispersionMontoLiquidacion(String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio tarjeta, que se encuentran pendientes por dispersdar en un proceso
     * de liquidación
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     *
     * @param numeroRadicacion <code>String</code>
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     * @author rlopez
     */
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoPagoTarjeta(String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio efectivo, que se encuentran pendientes por dispersar en un proceso
     * de liquidación.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     *
     * @param numeroRadicacion <code>String</code>
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     * @author rlopez
     */
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoPagoEfectivo(String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio efectivo, que se encuentran pendientes por dispersar en un proceso
     * de liquidación.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     *
     * @param numeroRadicacion <code>String</code>
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     * @author rlopez
     */
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoPagoBanco(String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los descuentos por entidad, que se encuentran pendientes por dispersar.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     *
     * @param numeroRadicacion <code>String</code>
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     * @author rlopez
     */
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoDescuentosPorEntidad(String numeroRadicacion);


    RegistroOperacionTransaccionSubsidio buscarRegistroOperacionSubsidio(Long identificadorRespuesta);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar
     *
     * @param numeroRadicacion  <code>String</code>
     *                          valor del número de radicación
     * @param estadoTransaccion <code>EstadoTransaccionSubsidioEnum</code>
     *                          Estado al que se actualizarán los pagos
     * @param mediosDePago      <code>List<TipoMedioDePagoEnum></code>
     *                          Lista de medios de pago a los cuales se realizará la actualización
     * @author rlopez
     */
    public void dispersarPagosEstadoEnviado(String numeroRadicacion, EstadoTransaccionSubsidioEnum estadoTransaccion,
                                            List<TipoMedioDePagoEnum> mediosDePago, UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método que permite consultar las cuentas de administrador de subsidio en una liquidación para el medio de pago
     * tarjeta
     *
     * @param numeroRadicacion <code>String</code>
     *                         valor del número de radicación
     * @return información de las cuentas de administrador
     * @author rlopez
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjeta(String numeroRadicacion);


    /**
     * @param idCuentaAdminSubsidio
     * @return
     */
    public CuentaAdministradorSubsidioDTO consultarCuentaAdministradorMedioTarjeta(Long idCuentaAdminSubsidio);


    /**
     * <b>Descripción:</b>Método que permite actualizar al estado APLICADO los pagos a dispersar en los medio efectivo y tarjeta
     *
     * @param numeroRadicacion <code>String</code>
     *                         valor del número de radicación
     * @param abonosExitosos   <code>List<Long></code>
     *                         lista de indentificadores de las cuentas con abonos exitosos
     * @author rlopez
     */
    public void dispersarPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos, UserDTO userDTO);

    /**
     * Metodo encargado de registrar la relación de una transacción fallida con un registro de operación de una transacción en linea,
     *
     * @param idRegistroOperacion  <code>Long</code>
     *                             Identificador principal del registro en base de datos del registro de operación.
     * @param idTransaccionFallida <code>Long</code>
     *                             Identificador principal del registro en base de datos de la transacción fallida.
     */
    public void registrarTransaccionesFallidasRegistroOperacionesSubsidio(Long idRegistroOperacion, Long idTransaccionFallida);

    /**
     * Método encargado de consultar el valor del saldo a favor que tiene un administrador de subsidio
     * asociado a los abonos.
     *
     * @param tipoIdAdmin   <code>TipoIdentificacionEnum</code>
     *                      tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code>
     *                      numero de identificación del administrador del subsidio
     * @param medioDePago   <code>TipoMedioDePagoEnum</code>
     *                      medio de pago por el cual se buscara consultar la cuenta.
     * @return Map que contiene el valor del saldo y el nombre del administrador del subsidio
     * llave : valorSaldo , valor: BigDecimal valor del saldo
     * llave : nombreAdmin, valor: String nombre administrador
     * llave: idAdminSubsidio, valor Long identificador de base de datos del administrador del subsidio
     */
    public Map<String, Object> consultarSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                      TipoMedioDePagoEnum medioDePago);

    /**
     * Método encargado de consultar si una empresa esta relacionada con un convenio.
     *
     * @param idEmpresa <code>Long</code>
     *                  Identificador de la empresa
     * @return Entidad del convenio del tercero pagador
     */
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorIdEmpresa(Long idEmpresa);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar con origen anulación
     *
     * @param identificadoresCuentas <code>List<Long></code>
     * @author rlopez
     */
    public void dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long> identificadoresCuentas);

    /**
     * <b>Descripción:</b>Método que permite consultar las cuentas de administrador de subsidio para el medio de tarjeta, asociadas a los
     * identificadores parametrizados
     *
     * @param identificadoresCuentas <code>List<Long></code>
     *                               Lista de identificadores de las cuentas de administrador
     * @return Lista de cuentas de administradores de subsidio
     * @author rlopez
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(List<Long> identificadoresCuentas);

    /**
     * <b>Descripción:</b>Método que permite actulizar al estado APLICADO los pagos a dispersar con origen anulación al medio tarjeta y
     * efectivo
     *
     * @param identificadoresCuentas <code>List<Long></code>
     *                               Lista de indentificadores de las cuentas de administrador
     * @author rlopez
     */
    public void dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long> identificadoresCuentas);

    /**
     * Metodo encargado de obtener el retiro que se efectuo por tipo de medio de pago TARJETA el cual sera reversado.
     *
     * @param idTransaccionTerceroPagador <code>String</code>
     *                                    Identificador de transacción del tercero pagador que identifica al retiro.
     * @param numeroTarjeta               <code>String</code>
     *                                    Número de tarjeta asociado al retiro.
     * @return retiro asociado al identificador del tercero pagador y el número de tarjeta
     */
    public CuentaAdministradorSubsidioDTO consultarRetiroTarjetaParaReversion(String idTransaccionTerceroPagador, String numeroTarjeta);

    /**
     * Metodo encargado de obtener los abonos cobrados que están asociados al retiro que se revertira.
     *
     * @param idTransaccionTerceroPagador <code>String</code>
     *                                    Identificador de transacción del tercero pagador que identifica al retiro.
     * @return Lista de abonos cobrados asociados al retiro.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarAbonosCobradosAsociadosRetiroParaReversion(String idTransaccionTerceroPagador);

    /**
     * <b>Descripción:</b>Método encargado de actualizar informacion de la solicitud de anulacion de subsidio cobrado
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @param solicitudAnulacionSubsidioCobrado <code>SolicitudAnulacionSubsidioCobrado</code>
     *                                          Representa una solicitud de anulación de subsidio cobrado a actualizar
     * @return <code>Boolean</code>
     * Representa si la actualizacion de la informacion de la solicitud fue realizada con exito o no
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public Boolean actualizarSolicitudAnulacionSubsidioCobrado(SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado);

    /**
     * <b>Descripción:</b>Método encargado de consultar el detalle de una solicitud de subsidio cobrado.
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @param solicitudAnulacionSubsidioCobradoDTO <code>DetalleSolicitudAnulacionSubsidioCobradoDTO</code>
     *                                             dto que contiene todos los filtros con los cuales se realizara la consulta
     * @return <code>List<AbonosSolicitudAnulacionSubsidioCobradoDTO></code>
     * lista de transacciones de abono cobradas de cuentas de administradores de subsidios con su respectivo detalle.
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public List<AbonosSolicitudAnulacionSubsidioCobradoDTO> consultarDetalleSolicitudAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar el detalle de una solicitud de subsidio cobrado.
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @param idSolicitudAnulacionSubsidioCobrado <code>Long</code>
     *                                            Identificador para una solicitud de anulación de subsidio cobrado
     * @return <code>SolicitudAnulacionSubsidioCobrado</code>
     * Representa los datos de una solicitud de anulación de subsidio cobrado consultada
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public SolicitudAnulacionSubsidioCobrado consultarSolicitudAnulacionSubsidioCobrado(Long idSolicitudAnulacionSubsidioCobrado);

    /**
     * Método encargado de buscar si un nombre de archivo de consumo de tarjeta enviado por ANIBOL se encuentra
     * registrado y procesado.
     *
     * @param nombreArchivo <code>String</code>
     *                      Nombre del archivo que se requiere procesar.
     * @return TRUE si se encuentra el nombre del archivo y se encuentra procesado, FALSE de lo contrario.
     */
    public Boolean consultarNombreArchivoConsumoTarjetaANIBOL(String nombreArchivo);

    /**
     * Método encargado de buscar si un nombre de archivo de consumo de tarjeta enviado por ANIBOL se encuentra
     * registrado y procesado.
     *
     * @param nombreArchivo <code>String</code>
     *                      Nombre del archivo que se requiere procesar.
     * @return TRUE si se encuentra el nombre del archivo y se encuentra procesado, FALSE de lo contrario.
     */
    public Boolean consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(String nombreArchivo);


    /**
     * Método encargado de registrar la información global del archivo de consumo de tarjetas que viene desde ANIBOL
     *
     * @param archivoConsumosAnibolModeloDTO <code>ArchivoConsumosAnibolModeloDTO</code>
     *                                       DTO que contiene la información del archivo de consumo.
     * @return Identificador con el que se guarda en base de datos el registro.
     */
    public Long registrarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO);

    /**
     * Método encargado de registrar la información global del archivo de consumo de tarjetas que viene desde ANIBOL
     *
     * @param archivoConsumosAnibolModeloDTO <code>ArchivoConsumosAnibolModeloDTO</code>
     *                                       DTO que contiene la información del archivo de consumo.
     * @return Identificador con el que se guarda en base de datos el registro.
     */
    public Long registrarArchivoConsumosTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO);


    /**
     * Método encargado de registrar la información de los registros de cada tarjeta con sus respectivos errores.
     *
     * @param idArchivoConsumoTarjeta <code>Long</code>
     *                                Identificador principal de la base de datos del registro del archivo global.
     * @param listaErroresHallazgos   <code>List<TarjetaRetiroCandidatoDTO></code>
     *                                Lista de registros de las tarjetas que tienen error.
     * @param listaHallazgos          <code>List<ResultadoHallazgosValidacionArchivoDTO></code>
     *                                Lista de errores de cada registro de tarjeta.
     * @param camposErroresPorLinea   <code>List<ResultadoHallazgosValidacionArchivoDTO></code>
     *                                Valores de los campos que contienen un error, asociado al número de linea del registro.
     */
    public void crearCamposInconsistenciasArchivoConsumoTarjetaANIBOL(Long idArchivoConsumoTarjeta,
                                                                      List<TarjetaRetiroCandidatoDTO> listaErroresHallazgos, List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos,
                                                                      List<ResultadoHallazgosValidacionArchivoDTO> camposErroresPorLinea);

    /**
     * Método encargado de buscar las cuentas de administradores de subsidio que se encuentren asociada al número de tarjeta
     * y activas (estado APLICADO).
     *
     * @param numeroTarjeta <code>String</code>
     *                      Número de tarjeta asociada al registro
     * @return Lista de cuentas de administradores de subsidio.
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidioAsociadasRegistroTarjeta(String numeroTarjeta);

    /**
     * Método que permite guardar los registros de tarjetas que se encuentran dentro del archivo de consumo de ANIBOL.
     *
     * @param idArchivoConsumoTarjeta <code>Long</code>
     *                                Identificador principal de la base de datos del registro del archivo global.
     * @param listaCandidatos         <code>List<TarjetaRetiroCandidatoDTO></code>
     *                                Lista DTO que contiene la información de cada registro de tarjeta
     */
    public void guardarRegistrosTarjetasArchivoConsumoANIBOL(Long idArchivoConsumoTarjeta, List<TarjetaRetiroCandidatoDTO> listaCandidatos);

    /**
     * <b>Descripción:</b>Método encargado de consultar un municipio por codigo DANE
     * <b>Módulo:</b> Asopagos - HU-31-202<br/>
     *
     * @param idMunicipio <code>String</code>
     *                    Representa el identificador de municipio a consultar
     * @return <code>String</code>
     * El identificador del municipio
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     */
    public String buscarMunicipioPorCodigo(String idMunicipio);

    /**
     * Método encargado de actualizar la información del archivo de consumo de ANIBOL.
     *
     * @param archivoConsumosAnibolModeloDTO <code>ArchivoConsumosAnibolModeloDTO</code>
     *                                       Clase DTO con la información que se debe actualizar
     */
    public void actualizarArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO);

    /**
     * Método encargado de registrar una solicitud enviada por medio de los servicios que
     * se consumen desde ANIBOL.
     *
     * @param reAnibolModeloDTO <code>RegistroSolicitudAnibolModeloDTO</code>
     *                          DTO
     * @return identificador del registro de solicitud
     */
    public Long registrarSolicitudAnibol(RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO);

    /**
     * Método encargado de actualizar el registro de solicitud de ANIBOL con los
     * parametros de salida que tiene ANIBOL
     *
     * @param idRegistroSolicitudAnibol <code>Long</code>
     *                                  Identificador del registro de solicitud de ANIBOL registrado.
     * @param parametrosOUT             <code>String</code>
     *                                  Variable que contiene los parametros de salida por parte de ANIBOl en formato Json
     */
    public boolean actualizarRegistroSolicitudAnibol(Long idRegistroSolicitudAnibol, String parametrosOUT);

    /**
     * Método que permite realizar la actualización del registro de solicitud de aibol si hay un error.
     *
     * @param idRegistroSolicitudAnibol <code>Long</code>
     *                                  Identificador del registro de solicitud de ANIBOL registrado.
     */
    public void actualizarRegistroSolicitudAnibolError(Long idRegistroSolicitudAnibol);

    /**
     * Método que permite buscar los detalles programados apartir de el id de condiciones de beneficiarios.
     *
     * @param lstIdsCondicionesBeneficiarios <code>List<Long></code>
     *                                       Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return lista de los detalles de subsidios programados
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiarios(
            List<Long> lstIdsCondicionesBeneficiarios);

    /**
     * Método que permite buscar los detalles programados apartir de el id de condiciones de beneficiarios.
     *
     * @param lstIdsCondicionesBeneficiarios <code>List<Long></code>
     *                                       Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return lista de los detalles de subsidios programados
     */
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiariosYRadicado(
            List<Long> lstIdsCondicionesBeneficiarios, String numeroRadicado);

    /**
     * Método encargado de actualizar el estado de los derechos programados para que no se tengan en cuenta para proximas
     * liquidaciones para dicho administrador de subsidio.
     *
     * @param lstDetallesProgramados <code>List<Long> </code>
     *                               Lista de id de los detalles programados a ser actualizados
     */
    public void actualizarEstadoDetalleADerechoProgramado(List<Long> lstDetallesProgramados);

    /**
     * Metodo encargado de consultar el identificador de las cuentas de administradores de subsidios a partir de un filtro.
     *
     * @param idResultadoValidacionLiquidacion identificador del resultado de liquidación
     * @return lista de detalles de cuentas de administradores de subsidios.
     * @author jocampo
     */
    public List<Object[]> consultarDetallesSubsidioPorResultadoLiquidacion(Long idResultadoValidacionLiquidacion);

    /**
     * Metodo encargado de obtener los diferentes pagos de subsidio monetario que están pendiente por programar.
     *
     * @param idAdminSubsidio  <code>Long</code>
     *                         Id del administrador del subsidio
     * @param numeroRadicacion <code>String</code>
     *                         Número de radicado al cual pertenece los pagos programados
     * @return Lista de pagos de subsidio que faltan por programar
     */
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidiosProgramados(Long idAdminSubsidio, String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar con origen anulación del medio de pago TRANSFERENCIA
     *
     * @param identificadoresCuentas <code>List<Long></code>
     *                               Lista de identificadores de las cuentas de administrador
     */
    public void dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(List<Long> identificadoresCuentas);

    /**
     * <b>Descripción</b>: Metodo que permite invocar el metodo privado de filtrar las cuentas.
     *
     * @param transaccionConsultada
     * @return
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasConFiltros(TransaccionConsultadaDTO transaccionConsultada);

    /**
     * Metodo encargado de buscar los medios de pagos inactivos relacionados a un administrador de subsidio por medio
     * del grupo familiar asociado.
     *
     * @param idAdminSubsidio
     * @return
     */
    public List<TipoMedioDePagoEnum> consultarMediosDePagosInactivosRelacionadosAdminSubsidio(Long idAdminSubsidio, List<Long> cuentas);

    /**
     * Metodo encargado de buscar los medios de pagos por id
     *
     * @param idMedioDePago
     * @return
     */
    public MedioDePagoModeloDTO consultarMediosDePagoPorId(Long idMedioDePago);

    public List<DetalleSubsidioAsignadoDTO> consultarDetallesCuentas(DetalleTransaccionAsignadoConsultadoDTO detalleConsultado,
                                                                     List<CuentaAdministradorSubsidioDTO> listaCuentas);

    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesPorRVL(Long idRvl);

    /**
     * Consulta registro Anibol a partir del idProceso el cual es un identificador generado en Anibol
     *
     * @param idProceso
     * @return
     */
    public RegistroSolicitudAnibol buscarRegistroSolicitudAnibolPorIdProceso(String idProceso);

    /**
     * Consulta registro si existe tercero pagador por nombre
     *
     * @param nombreTerceroPagador
     * @return
     */
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorNombrePagos(String nombreTerceroPagador);

    /**
     * Consulta los id de procesos de anibol para consultar que ha pasado con los pagos
     *
     * @return Lista de id de procesos
     */
    public List<Long> consultarIdsProcesoAnibol();

    /**
     * Consulta los numeros de radicado en el registro de anibol que est
     *
     * @return
     */
    public List<String> consultarRadicadosProcesoAnibol();

    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnibol();


    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoAnibol();

    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoPrescripcionAnibol(Long idProceso);

    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarRegistroDispersionAnibol();

    /**
     * @param solicitudLiquidacionSubsidio
     * @return
     */
    public List<CuentaAdministradorSubsidio> consultarCuentaAdminSubsidioPorSolicitud(Long solicitudLiquidacionSubsidio);

    /**
     * Actualiza el estado de la solicitud a ANIBOL
     *
     * @param idRegistroSolicitudAnibol
     * @param estadoSolicitudAnibol
     */
    public void actualizarEstadoSolicitudAnibol(Long idRegistroSolicitudAnibol, EstadoSolicitudAnibolEnum estadoSolicitudAnibol);

    /**
     * @param listaIdsDetalle
     * @return
     */
    public List<DetalleSubsidioAsignadoDTO> obtenerListaDetallesSubsidioAsingnado(List<Long> listaIdsDetalle);

    /**
     * @param listaIds
     * @return
     */
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidio(List<Long> listaIds);

    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarLiquidacionFallecimientoAnibol();

    public void aplicarCuentasLiqFallecimiento(List<Long> listaIdsAdminSubsidio);

    public InfoPersonaReexpedicionDTO consultarInfoPersonaReexpedicion(String tipoIdentificacion, String identificacion, String numeroTarjeta);

    public InfoPersonaExpedicionDTO consultarInfoPersonaExpedicion(String tipoIdentificacion, String identificacion);

    public List<InfoPersonaExpedicionValidacionesDTO> consultarInfoPersonaExpedicionValidaciones(String tipoIdentificacion, String identificacion);

    public BigDecimal consultarSaldoTarjetaGenesys(String numeroTarjeta, Long idPersona);

    public void bloquearTarjeta(String numeroTarjeta);

    public void persistirRegistroInconsistencia(RegistroInconsistenciaTarjeta registroInconsistencia);

    public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorSubAbono(String numeroTarjeta, Long idPersona);

    public List<GruposMedioTarjetaDTO> consultarGruposTrabajadorMedioTarjeta(TipoIdentificacionEnum tipoIdentificacion, String identificacion, String numeroTarjeta);

    public void persistirDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignado);

    public Long persistirDetalleSubsidioAsignadoObtenerId(DetalleSubsidioAsignadoDTO detalleSubsidioAsignado);

    public List<RegistroInconsistenciaTarjeta> consultarRegistroInconsistencias(Long fechaInicio, Long fechaFin);

    public void cerrarCasoInconsistencia(Long idRegistroInconsistencia, ResultadoGestionInconsistenciaEnum resultadoGestion, String detalleResolucion);

    public List<RegistroInconsistenciaTarjeta> consultarHistoricoRegistroInconsistenciaTarjeta(Long fechaInicial, Long fechaFinal,
                                                                                               TipoIdentificacionEnum tipoId, String numeroId, EstadoResolucionInconsistenciaEnum estadoResolucion, TipoNovedadInconsistenciaEnum tipoNovedad);

    public List<InfoDetallesSubsidioAgrupadosDTO> obtenerInfoDetallesAgrupados(List<Long> idsDetalles);

    public void actualizarDatosDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> nuevosDetallesSubsidioAsignado, List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados, String nombreUsuario);

    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbonoOrdenados(Long idCuentaAdminSubsidio);

    public List<DetalleSubsidioAsignadoDTO> consultaDetallesRetirosAnulacionCuenta(Long idCuentaAdministradorSubsidio, TipoTransaccionSubsidioMonetarioEnum tipoTransaccion);

    public void persistirTempArchivoRetiroTerceroPagadorEfectivo(ArrayList<String[]> lineas, String usuario, Long idConvenio, Long idArchivoTerceroPagadorEfectivo);

    public void ejecutarSPValidarContenidoArchivoTerceroPagadorEfectivo(Long idConvenio, Long idArchivoTerceroPagadorEfectivo);

    public void InsertRestuladosValidacionCargaManualRetiroTerceroPag(InformacionArchivoDTO informacionArchivoDTO, Long idConvenio, String nombreUsuario, Long idArchivoTerceroPagadorEfectivo);

    /**
     * Metodo que busca una empArchivoRetiroTerceroPagadorEfectivo
     *
     * @param idCuentaAdmonSubsidio identificador de base de datos de la cuenta del administrador del subsidio.
     * @return DTO de la cuenta del administrador del subsidio.
     */
    public List<TempArchivoRetiroTerceroPagadorEfectivoDTO> consultarTempArchivoRetiroTerceroPagadorEfectivo(Long idConvenio, Long idArchivoRetiroTerceroPagadorEfectivo);

    public Long registrarArchivoTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivo cargueArchivoRetiro);

    public Long persistirTempArchivoRetiroTerceroPagadorEfectivo(TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO);

    public Long actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(Long id, EstadoArchivoConsumoTerceroPagadorEfectivo estado);

    public void persistirValidacionesNombreArchivoTerceroPagador(
            Map<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> validaciones,
            Long idArchivoRetiroTerceroPagadorEfectivo);

    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosPaginado(UriInfo uriInfo,
                                                                                                 HttpServletResponse response);

    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosArchivo();

    public Map<String, Object> consultarSaldoSubsidioAbono(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                           TipoMedioDePagoEnum medioDePago);

    /**
     * @param idConvenio
     * @return
     */
    public String consultarNombreTercerPagadorIdConvenio(Long idConvenio);

    public ConvenioTerceroPagador consultarConvenioTerceroPagador(Long idConvenio);

    public Long actualizarTempArchivoRetiroTerceroPagadorEfectivo(
            TempArchivoRetiroTerceroPagadorEfectivoDTO tempArchivoRetiroTerceroPagadorEfectivoDTO);

    public BigDecimal obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(Long idCuentaAdminSubsidio);

    public Boolean existeIdentificadorTransaccionTerceroPagador(String idTransaccionTercerPagador, Long idConvenio);

    public List<CuentaAdministradorSubsidioDTO> consultarAbonosEnviadosMedioDePagoBancosPaginadoConFiltro(
            UriInfo uriInfo, HttpServletResponse response, String textoFiltro);

    /**
     * @return Sumatoria de los abonos bancarios
     */
    public BigDecimal consultarSumatoriaAbonosEnviadosMedioDePagoBancos();

    /**
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<CuentaAdministradorSubsidioDTO> consultarRetirosConEstadoSolicitado(Long fechaInicio, Long fechaFin);

    /**
     * @param idTerceroPagador
     * @return
     */
    public List<Object[]> consultarCuotasDispersadasPorTerceroPagador(Long idTerceroPagador);

    public Object[] consultarEncabezadoCuotasDispersadasPorTerceroPagador(Long idTerceroPagador);

    public void ejecutarAbonosMedioPagoBancos(List<Long> idCuentaAdmonSubsidio, UserDTO userDTO);

    public Long persistirArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio);


    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioTodos();

    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioEstado(EstadoArchivoTransDetaSubsidioEnum estadoArchivoTransDeta);

    public void limpiarBufferArchivoTransDetaSubsidio();


    public List<DetalleSubsidioAsignadoDTO> consultarDetallesPorCuenta(Long idCuentaAdministradorSubsidio);

    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> consultarCuentasPorAnularMantis266382();

    /**
     * Método encargado de consultar el valor total de los subsidios a anular
     *
     * @param listaMediosDePago           medios de pagos por los cuales se realizara la anulación
     * @param diasParametrizadosAnulacion dias parametrizados por la CCF para anular un subsidio monetario
     * @param fechaActual
     * @return el valor total de los subsidios a anular
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    public BigDecimal consultarValorTotalSubsidiosAnular(List<String> listaMediosDePago, String diasParametrizadosAnulacion, Date fechaActual);

    /**
     * Método que se encarga de generar el resumen de la lista de subsidios a anular por prescripción o por vencimiento
     *
     * @param listaMediosDePago  medios de pagos por los cuales se realizara la anulación
     * @param diasParametrizados dias parametrizados por la CCF para anular un subsidio monetario
     * @param firstRequest       indica si es la primera vez que se llama al servicio
     * @param offset             indica la posición desde donde se empezarán a retornar registros
     * @param orderBy            indica el ordenamiento de los registros
     * @param limit              indica cuantos registros se deben traer
     * @return lista de resumen de los subsidios a anular por prescripción o por vencimiento
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    public ResumenListadoSubsidiosAnularDTO generarResumenListadoSubsidiosAnular(
            List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, Integer filter, String orderBy, Integer limit);

    /**
     * Metodo encargado de consultar los registros asociados a los detalles de los abonos
     * que sean de tipo ABONO, su medio de pago (si el por fecha de vencimiento será solo por EFECTIVO; si es por prescripción será por
     * cualquiera) y el estado de la transacción sea APLICADO, para presentar la información de los subsidios monetarios que seran anulados
     * por fecha de vencimiento.
     * HU-31-223 & HU-31-224
     *
     * @param listaMediosDePago  medios de pagos por los cuales se realizara la anulación
     * @param diasParametrizados dias parametrizados por la CCF para anular un subsidio monetario
     * @param firstRequest       indica si es la primera vez que se llama al servicio
     * @param offset             indica la posición desde donde se empezarán a retornar registros
     * @param orderBy            indica el ordenamiento de los registros
     * @param limit              indica cuantos registros se deben traer
     * @return lista de subsidios monetarios a ser anulados.
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     */
    public ListadoSubsidiosAnularDTO generarlistadoSubsidiosAnular(
            List<String> listaMediosDePago, String diasParametrizados, Boolean firstRequest, Integer offset, String orderBy, Integer limit,String numeroIdentificacionAdminSub);

    /**
     * Consulta los Descuentos asignados a un subsidio
     *
     * @param descuento
     * @return
     */
    public List<DescuentoSubsidioAsignadoDTO> consultarDescuentosSubsidio(Long idDetalleSubsidioAsignado);

    /**
     * @return
     */
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDispersionSubsidioMonetario(Long idProceso);

    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnulacionSubsidioMonetario(Long idProceso);

    public void actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long idProceso);

    public Long consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(Long idSolicitud, String numeroTarjetaAdmonSubsidio);

    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatariosPrescripcion(Long idCuentaAdmonSubsidio);

    public List<Long> obtenerIdsAbonosPrescripcion(String parametro);

    public void ejecutarAbonosMedioPagoBancosArchivo(List<String> idCuentaAdmonSubNoexitoso, List<String> idCuentaAdmonSubExitoso, String userEmail);

    public boolean buscarExistenciaRetiroPorIdTransaccionTerceroPagadorRetiro(String idTransaccionTercerPagador, String usuario, String idPuntoCobro);

    public Long persistirAuditoriaRecaudoYPagos(AuditoriaRecaudosYPagos auditoriaRecaudosYPagos);

    public Map<String,String> modificarCuentaYDetallePorReverso(String idTransaccionTerceroPagador,String nombreTerceroPagador);

    Object consultarUsuarioTerceroPagador(String nombreTerceroPagador);

    public List<CuentaAdministradorSubsidioDTO> listadoValoresRetiros(String idTransaccionTerceroPagador, String nombreTerceroPagador, String idPuntoCobro);

    List<CuentaAdministradorSubsidioDTO> listadoAbonosRetiro(String idTransaccionTerceroPagador, String nombreTerceroPagador, String idPuntoCobro);

    public void eliminarCuentAdministradorSubsidio(CuentaAdministradorSubsidio cuentaAdmin);

    public void restaurarDetallesSubsidioAsignado(List<DetalleSubsidioAsignado> detallesRestaurar);

    public List<DetalleSubsidioAsignado> consultarDetallesRestaurar(List<Long> idCuentaAdminSubsidio);

    public void consultarRetirosParciales(Long idRetiro);

    public void restaurarCuentaSubsidioAsignado(List<CuentaAdministradorSubsidio> cuentaAdmin);

    public Boolean validarExistenciaTarjeta(String numeroExpedido);

    public List<Long> consultarGruposFamiliaresConMarcaYAdmin(String tipoIdentificacion, String numeroIdentificacion, String numeroTarjeta, Boolean expedicion);

    public String consultarEstadoDispercion(String numeroRadicacion);

    public String consultarMedioYgruposParaTraslado(String numeroDocumento,TipoIdentificacionEnum tipoDocumento,List<TipoMedioDePagoEnum> medioDePago,String numeroTarjeta);

        public List<MedioDePagoModeloDTO> consultarInfoMedioTarjetaTraslado(String idAdmin);

        public List<MedioDePagoModeloDTO> consultarInfoMedioTransferenciaTraslado(String idAdmin);

        public List<MedioDePagoModeloDTO> consultarInfoMedioEfectivoTraslado(String idAdmin);

        public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorTraslado(Long idAdmin,List<Long> idsGrupoFamiliar,List<Long> idsMediosDePagoPrevios);

        public List<DetalleSubsidioAsignado> consultarDetallesCuentaTraslado(Long idCuenta, List<Long> idsGrupoFamiliar);

        public MedioDePagoModeloDTO consultarMedioDePagoTraslado(Long idMedioDePago);

        public void guardarJsonRespuestaAnibol(String salidaRespuestaAnibol, Long idProceso);

        public PersonaModeloDTO consultarPersonaAdmin(Long idAdmin);

        public void bloquearAbonosATrasladar(List<CuentaAdministradorSubsidio> cuentas,UserDTO user);

        public List<RegistroSolicitudAnibol> consultarRegistrosAnibolTraslado();

        public void restablecerEstadoCuenta(Long idCuenta, UserDTO user);

        public CuentaAdministradorSubsidio consultarCuentasAdministradorTraslado(Long idCuenta);

        public List<DetalleSubsidioAsignado> consultarDetallesTraslado(Long idCuenta, List<Long> idsDetalles);

        public Long crearMedioDePagoParaTraslado(Long idSitioPago);

        public Long registrarProcesoBandeja(BandejaDeTransacciones bandeja);

        public void actualizarProcesoBandeja(Long idBandeja,EstadoBandejaTransaccionEnum estado,Long idSolicitud);

        public Object[] consultarDatosAdminRegistro(Long idAdmin);

        public List<Long> consultarBandejaTransacciones(List<Long> idsCuenta,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso);

        public Long consultarUltimaSolicitud(Long idAdmin);

        public Long consultarUltimaSolicitud(TipoIdentificacionEnum tipoIdentificacionAdmin,String numeroIdentificacionAdmin);

        public List<BandejaDeTransacciones> consultarBandejaTransaccionesPorPersona(String proceso, PersonaModeloDTO persona);

        public List<PersonaModeloDTO> consultarPersonasBandejaTransacciones(String proceso,PersonaModeloDTO persona);

        public DetalleBandejaTransaccionesDTO consultarDetalleBandejaTransacciones(Long idBandeja);

        public Long consultarIdMedioDePagoTarjeta(String tipoIdentificacion,String numeroIdentificacion);

        public List<GestionDeTransaccionesDTO> consultarGestionDeTransacciones();

        public Long consultarBandejaTransacciones(Long idProceso);

        public List<TipoMedioDePagoEnum> consultarMediosDeTrasladoAdminSubsidio(Long idAdminSubsidio);

        public List<CuentaAdministradorSubsidioDTO> listadoValoresRetirosIntermedios(String tipoIdAdmin,String numeroIdAdmin,String usuario,String idTransaccionTercerPagador,String idPuntoCobro);

        void actualizarEstadoTransaccionRetiro(String estadoTransaccion, Long id);

        public Integer consultarTransaccionProceso(String tipoIdAdmin, String numeroIdAdmin);

        void actualizarListaCuentaAdministradorSubsidio(String estado, List<Long> listId, String estadoOperacion);

        public List<CuentaAdministradorSubsidio> consultarAbonosRelacionadosRetiro(Long idRetiro);

        public Object registrarRetiroSP(String tipoIdentificacion, String numeroIdentificacion, Long valorSolicitado, String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String idPuntoCobro, String usuarioGenesys);

        public List<CuentaAdministradorSubsidioDTO> listadoAbonosIntermedios(String idTransaccionTerceroPagador, String nombreTerceroPagador, String idPuntoCobro);

        public void cambioEstadoCuentasAdminSubsidio (List<Long> idCuentaAdminList, String usuarioNombre);

        public Long consultarTransaccionesTodosFiltrosSPCount(UriInfo uriInfo, HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO detallesTransacciones);
}
