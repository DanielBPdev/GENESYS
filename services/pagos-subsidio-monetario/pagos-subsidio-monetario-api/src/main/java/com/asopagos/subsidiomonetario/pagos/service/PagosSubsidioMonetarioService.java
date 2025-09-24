package com.asopagos.subsidiomonetario.pagos.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletResponse;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobradoModeloDTO;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.pagos.dto.*;
import com.asopagos.subsidiomonetario.pagos.enums.ComparacionSaldoTarjetaEnum;
import com.asopagos.dto.MediosPagoYGrupoTrasladoDTO; 
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleBandejaTransaccionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GestionDeTransaccionesDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.subsidiomonetario.pagos.BandejaDeTransacciones;

/**
 * <b>Descripcion:</b> Clase que define los servicios para la manipulación de las capacidades que soportan el proceso de pagos<br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Path("pagosSubsidioMonetario")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PagosSubsidioMonetarioService {

    /**
     * <b>Descripción:</b>Método encargado de realizar la validación del archivo
     * del convenio del tercer pagador.
     * HU-31-205
     *
     * @param informacionArchivoDTO
     *        DTO con la información del archivo
     * @param nombreUsuario
     *        nombre del usuario que realizo la carga del archivo
     * @param nombreTerceroPagador
     *        nombre del tercero pagador (nombre convenio)
     * @author mosorio
     * @return DTO con el resultado de la validación del archivo
     */
    @POST
    @Path("validarEstructuraArchivoRetiros/{nombreUsuario}/{nombreTerceroPagador}")
    public ResultadoValidacionArchivoDTO validarEstructuraArchivoRetiros(@NotNull InformacionArchivoDTO informacionArchivoDTO,
            @NotNull @PathParam("nombreUsuario") String nombreUsuario,
            @NotNull @PathParam("nombreTerceroPagador") String nombreTerceroPagador);
    
    /**
     * <b>Descripción:</b>Método encargado de realizar la validación del archivo
     * del convenio del tercer pagador.
     * HU-31-205
     *
     * @param informacionArchivoDTO
     *        DTO con la información del archivo
     * @param nombreUsuario
     *        nombre del usuario que realizo la carga del archivo
     * @param nombreTerceroPagador
     *        nombre del tercero pagador (nombre convenio)
     * @author mosorio
     * @return DTO con el resultado de la validación del archivo
     */
    @POST
    @Path("validarArchivoConsumoTerceroPagadorEfectivo/{idConvenio}/{nombreUsuario}/{idArchivoTerceroPagadorEfectivo}")
    public Long validarArchivoConsumoTerceroPagadorEfectivo(@NotNull InformacionArchivoDTO informacionArchivoDTO,
    		@NotNull @PathParam("idConvenio")Long idConvenio,@PathParam("nombreUsuario")String nombreUsuario,@PathParam("idArchivoTerceroPagadorEfectivo")Long idArchivoTerceroPagadorEfectivo);
    
    @POST
    @Path("InsertRestuladosValidacionCargaManualRetiroTerceroPag/{idConvenio}/{nombreUsuario}/{idArchivoTerceroPagadorEfectivo}")
    public Long InsertRestuladosValidacionCargaManualRetiroTerceroPag(@NotNull InformacionArchivoDTO informacionArchivoDTO,
    		@NotNull @PathParam("idConvenio")Long idConvenio,@PathParam("nombreUsuario")String nombreUsuario,@NotNull @PathParam("idArchivoTerceroPagadorEfectivo")Long idArchivoTerceroPagadorEfectivo);

    /**
     * <b>Descripción:</b>Método que se encarga de crear un convenio y su documentación respectiva,
     * en el proceso de pago.
     * HU-31-210
     * 
     * @param ConvenioTercerPagadorDTO
     *        Convenio y documentación asociada que seran creados.
     * @author mosorio
     * @return id del convenio registrado.
     */
    @POST
    @Path("registrarConvenioTercerPagador")
    public Long registrarConvenioTercerPagador(ConvenioTercerPagadorDTO convenio);

    /**
     * <b>Descripción:</b>Método que se encarga de registrar una cuenta de administrador de subsidio
     * asignado con sus respectivos detalles de subsidios asignados.
     * HU-31-217
     * 
     * @param cuentaAdministradorSubsidioDTO
     *        variable que contiene toda la información de la cuenta del administrador de subsidio
     * @param userDTO
     *        usuario registrado en el sistema que registra la cuenta.
     * @author mosorio
     * @return id de la cuenta del administrador de subsidio
     */
    @POST
    @Path("registrarCuentaAdministradorSubsidio")
    public Long registrarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO,
                                                     @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b> Método que se encarga de anular los detalles de subsidio monetario
     * sin reemplazo. HU-31-208 & HU-31-221.
     * @author mosorio
     * 
     * @param subsidioAnulacionDTO
     *        <code>SubsidioAnulacionDTO</code>
     *        DTO que contiene la Lista de los detalles DTO que se van anular con reemplazo y
     *        Cuenta del administrador del subsidio al cual se anulara a partir de sus detalles.
     * @param resultadoValidacion
     *        <code>Boolean</code>
     *        Parametro que puede venir en false si el medio de pago es TARJETA y la devolución de ANIBOL no es procedente o true
     *        cualquier otro caso sería siempre true
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @return id de la nueva cuenta del administrador (abono) asociada a los nuevos detalles.
     */
    @POST
    @Path("anularSubsidioMonetarioSinReemplazo")
    public Long anularSubsidioMonetarioSinReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO,
                                                    @QueryParam("resultadoValidacion") Boolean resultadoValidacion, @Context UserDTO userDTO);
    

    /**
     * <b>Descripción:</b> Método que se encarga de anular los detalles de subsidio monetario
     * sin reemplazo. HU-31-208 & HU-31-221.
     * @author mosorio
     * 
     * @param subsidioAnulacionDTO
     *        <code>SubsidioAnulacionDTO</code>
     *        DTO que contiene la Lista de los detalles DTO que se van anular con reemplazo y
     *        Cuenta del administrador del subsidio al cual se anulara a partir de sus detalles.
     * @param resultadoValidacion
     *        <code>Boolean</code>
     *        Parametro que puede venir en false si el medio de pago es TARJETA y la devolución de ANIBOL no es procedente o true
     *        cualquier otro caso sería siempre true
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @return id de la nueva cuenta del administrador (abono) asociada a los nuevos detalles.
     */
    @POST
    @Path("anularSubsidioMonetarioSinReemplazoH")
    public Long anularSubsidioMonetarioSinReemplazoH(SubsidioAnulacionDTO subsidioAnulacionDTO,
            @QueryParam("resultadoValidacion") Boolean resultadoValidacion);


    /**
     * <b>Descripción:</b> Método que se encarga de anular los detalles de subsidio monetario
     * con reemplazo. HU-31-207 & HU-31-220.
     * @author mosorio
     * 
     * @param subsidioAnulacionDTO
     *        <code>SubsidioAnulacionDTO</code>
     *        DTO que contiene la Lista de los detalles DTO que se van anular con reemplazo y
     *        Cuenta del administrador del subsidio al cual se anulara a partir de sus detalles.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param resultadoValidacion
     *        <code>Boolean</code>
     *        Parametro que puede venir en false si el medio de pago es TARJETA y la devolución de ANIBOL no es procedente o true
     *        cualquier otro caso sería siempre true
     * @return id de la nueva cuenta del administrador (abono) asociada a los nuevos detalles.
     */
    @POST
    @Path("anularSubsidioMonetarioConReemplazo")
    public Long anularSubsidioMonetarioConReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO, @Context UserDTO userDTO,
            @QueryParam("resultadoValidacion") Boolean resultadoValidacion);

    /**
     * <b>Descripción:</b>Método que se encarga de registrar un detalle de subsidio
     * asignado en el proceso de pagos.
     * HU-31-216
     * 
     * @param detallesSubsidioAsignadoDTO
     *        lista que contiene toda la información necesaria para crear los detalles
     *        de subsidios asignados
     * @author mosorio
     */
    @POST
    @Path("registrarDetalleSubsidioAsignado")
    public void registrarDetalleSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> detallesSubsidioAsignadoDTO);

    /**
     * <b>Descripción:</b>Método que se encarga de gestionar(guardar la transacción si viene sin el id de la base de datos o actualizar la
     * si tiene un id) una transacción fallida en el proceso de pagos.
     * HU-31-200
     * 
     * @param transaccionFallidDTO
     *        transacción fallida que han sido resuelta.
     * @author mosorio
     * @return identificador de la transacción fallida que se gestiono
     */
    @POST
    @Path("gestionarTransaccionesFallidas")
    public Long gestionarTransaccionesFallidas(TransaccionFallidaDTO transaccionFallidaDTO);

    /**
     * <b>Descripción:</b>Método que se encarga de buscar las transacciones fallidas por un rango de fechas. Si no hay
     * fechas, consulta todas las transacciones registradas desde los ultimos 5 días.
     * HU-31-200
     * 
     * @param fechaInicial
     *        fecha inicial que tendra el rango para la busqueda de las transacciones fallidas.
     * @param fechaFinal
     *        fecha final que tendra el rango para la busqueda de las transacciones fallidas
     * @author mosorio
     * @return lista de transacciones fallidas.
     */
    @GET
    @Path("consultarTransaccionesFallidasSubsidioPorFechas")
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasSubsidioPorFechas(@QueryParam("fechaInicial") Long fechaInicial,
            @QueryParam("fechaFinal") Long fechaFinal);

    /**
     * <b>Descripción:</b>Método encargado de traer todos los detalles de subsidios asignados
     * asociados al identificador de una cuenta (abono).
     * HU-31-200
     * 
     * @param idCuentaAdminSubsidio
     *        identificador de la cuenta del administrador del subsidio asociado a los detalles.
     * @author mosorio
     * @return lista de detalles de subsidios asignados.
     */
    @GET
    @Path("verDetallesSubsidioAsignado/{idCuentaAdminSubsidio}")
    public List<DetalleSubsidioAsignadoDTO> verDetallesSubsidioAsignado(@PathParam("idCuentaAdminSubsidio") Long idCuentaAdminSubsidio);

    /**
     * 
     * <b>Descripción:</b>Método encargado de traer todos los registro de la cuenta de administrador del subsidio
     * que sean de tipo Abono, con estado Enviado y que el medio de pago relacionado sea Bancos (Transferencia).
     * HU-31-199
     * 
     * @return lista de cuentas de administradores de subsidios.
     */
    @GET
    @Path("consultarCuentaAdminSubsidio/tipoAbono/estadoEnviado/medioDePagoBancos")
    public CuentaAdministradorSubsidioTotalAbonoDTO consultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @QueryParam ("textoFiltro") String textoFiltro);

    /**
     * 
     * <b>Descripción:</b>Método encargado de generar archivo de la cuenta de administrador del subsidio
     * que sean de tipo Abono, con estado Enviado y que el medio de pago relacionado sea Bancos (Transferencia).
     * HU-31-199
     * 
     * @return Id del Archivo en el ECM
     */
    @GET
    @Path("generarArchivoAbonoBancos")
    public String generarArchivoAbonoBancos();
    
    /**
     * <b>Descripción:</b>Método encargado de procesar si un abono fue o no exitoso.
     * Si fue exitoso se actualiza el estado de transacción de aplicado a cobrado.
     * Si no es exitoso se remite a la HU-31-200 "Gestionar transacciones no exitosas"
     * HU-31-199
     * 
     * @author mosorio
     * @param listaAbonos
     *        lista de abonos (cuentas administradores de subsidio) que serán procesadas.
     */
    @PUT
    @Path("confirmarResultadosAbonosBancarios")
    public void confirmarResultadosAbonosBancarios(List<CuentaAdministradorSubsidioDTO> listaAbonos, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar las transacciones realizadas en la cuenta
     * del administrador del subsidio por medio de un filtro.
     * HU-31-201
     * 
     * @param transaccionConsultada
     *        dto que contiene todos los filtros con los cuales se realizara la consulta.
     * 
     * @author mosorio
     * @return lista de cuentas de los administradores de subsidios.
     */
    @PUT
    @Path("consultarTransaccionesSubsidio")
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesSubsidio(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada);
     
    
    @PUT
    @Path("exportarArchivoTransaccionesSubsidioAsync")
    public void exportarArchivoTransaccionesSubsidioAsync(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada
             )
            		throws FileNotFoundException, IOException;
    
    
    @PUT
    @Path("generarArchivoTransaccionesSubsidio")
    public Map<String,String> generarArchivoTransaccionesSubsidio(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            		throws FileNotFoundException, IOException;
    
    
    
    /**
     * <b>Descripción:</b>Método encargado de exportar el archivo en CSV del detalle de subsidios.
     * HU-31-201
     * 
     * @param transaccionConsultada
     *        dto que contiene todos los filtros con los cuales se realizara la consulta.
     * 
     * @author mosorio
     * @return lista de cuentas de los administradores de subsidios.
     */
    @PUT
    @Path("exportarArchivoDetallesSubsidioAsync")
    public void exportarArchivoDetallesSubsidioAsync(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)throws IOException;

    
    @PUT
    @Path("exportarArchivoTransDetallesSubsidioAsync")
    public void exportarArchivoTransDetallesSubsidioAsync(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)throws IOException;

    
    
    
    @PUT
    @Path("generarArchivoDetallesSubsidio")
    public Map<String,String> generarArchivoDetallesSubsidio(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            		throws FileNotFoundException, IOException;
    
    
    @PUT
    @Path("generarArchivoTranDetaSubsidio")
    public Map<String,String> generarArchivoTranDetaSubsidio(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            		throws FileNotFoundException, IOException;
    
    
    /**
     * <b>Descripción:</b>Método encargado de consultar los detalles de subsidios asignados por medio
     * de filtros.
     * HU-31-201
     * 
     * @param detalleConsultado
     *        dto que contiene todos los filtros con los cuales se realizara la consulta.
     * @author mosorio
     * @return lista de detalles de subsidios asignados.
     */
    @PUT
    @Path("consultarDetallesSubsidio")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidio(@Context UriInfo uriInfo,
            @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO detalleConsultado);

    /**
     * <b>Descripción:</b>Método encargado de consultar las transacciones de cuentas de administradores de subsidios y los detalles de
     * subsidios asignados por medio
     * de filtros.
     * HU-31-201
     * 
     * @param transaccionDetalleSubsidio
     *        dto que contiene todos los filtros con los cuales se realizara la consulta
     * @author mosorio
     * @return lista de transacciones de cuentas de administradores de subsidios con su respectivo detalle.
     */
    @PUT
    @Path("consultarTransaccionesDetallesSubsidios")
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesDetallesSubsidios(@Context UriInfo uriInfo,
            @Context HttpServletResponse response,
            @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio);

    
    @GET
    @Path("consultarDetallesRetirosAnulacion")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesRetirosAnulacion(
    		@QueryParam("idCuentaAdmin") Long idCuentaAdmin, 
    		@QueryParam("tipoTransaccion") TipoTransaccionSubsidioMonetarioEnum tipoTransaccion);  
    
    /**
     * <b>Descripción:</b>Método encargado de consultar el saldo que tiene un administrador del subsidio en la cuenta,
     * se filtra por el estado de transacción en aplicado,se suman dichos registros mas los retiros parciales y se le
     * restan los retiros aplicados.
     * HU-31-203
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago
     * @return Map que contiene el identificador de respuesta del registro de la operación, el nombre del administrador del subsidio y el
     *         saldo que tiene en ese medio de pago.
     */
    @GET
    @Path("consultarSaldoSubsidio")
    public Map<String, String> consultarSaldoSubsidio(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @Context UserDTO userDTO, @QueryParam("user") String user);
    
    
    /**
     * <b>Descripción:</b>Método encargado de consultar el saldo que tiene un administrador del subsidio en la cuenta,
     * se filtra por el estado de transacción en aplicado,se suman dichos registros mas los retiros parciales y se le
     * restan los retiros aplicados.
     * HU-31-203
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago
     * @return Map que contiene el identificador de respuesta del registro de la operación, el nombre del administrador del subsidio y el
     *         saldo que tiene en ese medio de pago.
     */
    @GET
    @Path("consultarSaldoSubsidioSinOperacion")
    public Double consultarSaldoSubsidioSinOperacion(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago);    
    

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: consultarSaldoSubsidio)
     * HU-31-203
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param saldoActualSubsidio
     *        saldo actual del subsidio (valor entregado por el servicio: consutlarSaldoSubsidio
     * @param valorSolicitado
     *        valor que se solicita retirar del saldo actual.
     * @param fecha
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercero pagador.
     * @param departamento
     *        código del DANE del departamento.
     * @param municipio
     *        código del DANE del municipio.
     * @param usuario
     *        usuario que realiza la transacción de retiro.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago.
     * @param idPuntoCobro
     *        Identificador punto cobro.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiroSubsidio")
    public Map<String, String> solicitarRetiroSubsidio(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @NotNull @QueryParam("saldoActualSubsidio") BigDecimal saldoActualSubsidio,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento, @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("usuario") String usuario, @Context UserDTO userDTO, @QueryParam("user") String user,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método que se encarga de consultar todos los convenios del tercero pagador registrados.
     * HU-31-210
     * 
     * @author mosorio
     * @return lista de convenios del tercero pagador
     */
    @GET
    @Path("consultarConveniosTerceroPagador")
    public List<ConvenioTercerPagadorDTO> consultarConveniosTerceroPagador();
    
    /**
     * <b>Descripción:</b>Método que se encarga de consultar todos los convenios del tercero pagador registrados.
     * HU-31-210
     * 
     * @author mosorio
     * @return lista de convenios del tercero pagador
     */
    @GET
    @Path("consultarConvenioTercero/{idConvenio}")
    public ConvenioTercerPagadorDTO consultarConvenioTercero(@PathParam("idConvenio") Long idConvenio);
    
    /**
     * <b>Descripción:</b>Método que se encarga de actualizar la información de un convenio del tercero
     * pagador.
     * HU-31-210
     * 
     * @param ConvenioTercerPagadorDTO
     *        Convenio con los datos actualizados.
     * @author mosorio
     * @return id del convenio actualizado.
     */
    @PUT
    @Path("actualizarConvenioTerceroPagador")
    public Long actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioTercerPagadorDTO);

    /**
     * <b>Descripción:</b>Método que se encarga de mostrar los registros de la cuenta del administrador de subsidio
     * que cumpla con los filtros descritos para mostrar los informes de retiros de subsidios.
     * HU-31-206
     * 
     * @param suConsumoDTO
     *        DTO que contiene los filtros para generar los informes de retiros.
     * @author mosorio
     * @return Lista con los datos de la cuenta del administrador del subsidio.
     */
    @PUT
    @Path("generar/InformesRetiros/SubsidioMonetario")
    public List<CuentaAdministradorSubsidioDTO> generarInformesRetirosSubsidioMonetario(
            @NotNull SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO);

    /**
     * <b>Descripción:</b>Método que se encarga de generar el listado de subsidios monetarios candidatos a ser
     * anulados por perdida de derecho.
     * HU-31-225
     * 
     * @param suConsumoDTO
     *        DTO que contiene los filtros para generar el listado de los subsidios.
     * @author mosorio
     * @return lista de subsidios monetarios candidatos a ser anulados.
     */
    @PUT
    @Path("generarListado/subsidiosAnular/porPerdidaDeDerecho")
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> generarListadoSubsidiosAnularPorPerdidaDerecho(
            @NotNull SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO);

    /**
     * <b>Descripción:</b>Metodo encargardo de mostrar las incosistencias que ocurrierón al momento
     * de validar y conciliar cada registro del archivo de retiros.
     * HU-31-205
     *
     * @param idArchivoRetiroTerceroPagador
     *        Numero de identificación del registro en el cual se tienen las incosistencias
     *        de la validación y conciliación del archivo de retiros del tercero pagador.
     * @author mosorio
     * @return lista de DTOs con las incosistencias encontradas al momento de cargar el archivo de retiro
     */
    @GET
    @Path("verIncosistenciasArchivoRetiros/{idArchivoRetiroTerceroPagador}")
    public List<IncosistenciaConciliacionConvenioDTO> verIncosistenciasArchivoRetiros(
            @PathParam("idArchivoRetiroTerceroPagador") Long idArchivoRetiroTerceroPagador);

    /**
     * <b>Descripción:</b>Metodo encargardo de mostrar las incosistencias que ocurrierón al momento
     * de validar y conciliar cada registro del archivo de retiros.
     * HU-31-205
     * 
     * @param idArchivoRetiroTerceroPagador
     *        Numero de identificación del registro del archivo el cual fue procesado
     *        satisfactoriamente
     * @author mosorio
     */
    @PUT
    @Path("actualizarEstado/retirosConciliados/terceroPagador/{idArchivoRetiroTerceroPagador}")
    public void actualizarEstadoRetirosConciliadosTerceroPagador(
            @PathParam("idArchivoRetiroTerceroPagador") Long idArchivoRetiroTerceroPagador);

    /**
     * <b>Descripción:</b>Metodo encargardo consultar los subsidios monetarios candidatos para cambiar su respectivo
     * medio de pago.
     * HU-31-219
     * 
     * @param cambioPagosDTO
     *        DTO que contiene los filtros para consultar los subsidios monetarios candidatos a cambiar el medio de pago.
     * @author mosorio
     * @return lista de abonos candidatos para cambiar el medio de pago.
     */
    @PUT
    @Path("consultarSubsidiosMonetarios/CambioMedioDePago")
    public List<CuentaAdministradorSubsidioDTO> consultarSubsidiosMonetariosCambioMedioDePago(
            SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar las transacciones de subsidios asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param transaccionDetalleSubsidio
     *        <code>DetalleTransaccionAsignadoConsultadoDTO</code>
     *        dto que contiene todos los filtros con los cuales se realizara la consulta
     * 
     * @return lista de transacciones de abono cobradas de cuentas de administradores de subsidios con su respectivo detalle.
     */
    @PUT
    @Path("consultarTransaccionesAbonoCobrados")
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesAbonoCobrados(
            @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio);

    /**
     * <b>Descripción:</b>Método encargado de registrar anulación de subsidios asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param solicitudAnulacionSubsidioCobradoDTO
     *        <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *        representa la información que representa los abonos cobrados asociados a una solicitud de anulación de subsidio cobrado
     * 
     * @return <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *         Datos que representan el registro de anulacion de subsido cobrado
     */
    @POST
    @Path("registrarAnulacionSubsidioCobrado")
    public SolicitudAnulacionSubsidioCobradoDTO registrarAnulacionSubsidioCobrado(
            @Valid SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de listar los nombres de los convenios de terceros pagadores
     * que se encuentran registrados.
     * <b>Módulo:</b> Asopagos - HU-31-205<br/>
     * @author mosorio
     * 
     * @return lista de convenios.
     */
    @GET
    @Path("mostrarNombreConveniosTerceroPagador")
    public List<ConvenioTercerPagadorDTO> mostrarNombreConveniosTerceroPagador();

    /**
     * <b>Descripción:</b>Método encargado de mostrar los medios de pagos asociados al administrador de subsidio
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * @author mosorio
     * 
     * @param idAdminSubsidio
     *        identificador principal de la base de datos del administrador del subsidio
     * @return lista de medios de pagos que se encuentran asociados a los diferentes grupos familiares administrados por el mismos
     *         administrador de subsidio
     */
    @GET
    @Path("consultarMediosDePagos/AdminSubsidio/{idAdminSubsidio}")
    public List<TipoMedioDePagoEnum> consultarMediosDePagosRelacionadosAdminSubsidio(
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio);
    
    
    /**
     * <b>Descripción:</b>Método encargado de mostrar los medios de pagos asociados al administrador de subsidio
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * @author mosorio
     * 
     * @param idAdminSubsidio
     *        identificador principal de la base de datos del administrador del subsidio
     * @return lista de medios de pagos que se encuentran asociados a los diferentes grupos familiares administrados por el mismos
     *         administrador de subsidio
     */
    @POST
    @Path("consultarMediosDePagosInactivos/AdminSubsidio/{idAdminSubsidio}")
    public List<TipoMedioDePagoEnum> consultarMediosDePagosInactivosRelacionadosAdminSubsidio(
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio,List<Long> cuentas); 

    /**
     * <b>Descripción:</b>Método encargado de consultar los registros asociados al administrador de subsidio
     * de un medio de pago respectivo.
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * @author mosorio
     * 
     * @param medioDePago
     *        <code>TipoMedioDePagoEnum</code>
     *        tipo de medio de pago asociado a los registros del administrador de subsidio.
     * @param idAdminSubsidio
     *        <code>Long</code>
     *        Identificador del administrador del subsidio por el cual se buscara los medios de pagos asociados.
     * @param lstMediosDePago
     *        <code>List<Long></code>
     *        Lista de identificadores de medios de pagos asociados al administrador del subsidio por medio de los abonos.
     * @return lista de medios de pagos asociados al administrador del subsidio.
     */
    @PUT
    @Path("consultarMedioDePagoAsignar/{idAdminSubsidio}")
    public List<MedioDePagoModeloDTO> consultarMedioDePagoAsignarAdminSubsidio(
            @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio, List<Long> lstMediosDePago);

    /**
     * <b>Descripción:</b>Método encargado de consultar los registros asociados al administrador de subsidio
     * de un medio de pago respectivo.
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * @author mosorio
     * 
     * @param medioDePago
     *        <code>TipoMedioDePagoEnum</code>
     *        tipo de medio de pago asociado a los registros del administrador de subsidio.
     * @param idAdminSubsidio
     *        <code>Long</code>
     *        Identificador del administrador del subsidio por el cual se buscara los medios de pagos asociados.
     * @param lstMediosDePago
     *        <code>List<Long></code>
     *        Lista de identificadores de medios de pagos asociados al administrador del subsidio por medio de los abonos.
     * @return lista de medios de pagos asociados al administrador del subsidio.
     */
    @PUT
    @Path("consultarMedioDePagoInactivoAsignar/{idAdminSubsidio}")
    public List<MedioDePagoModeloDTO> consultarMedioDePagoInactivoAsignarAdminSubsidio(
            @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio, List<Long> lstIdsCuentas);

    
    
    /**
     * <b>Descripción:</b>Método encargado de cambiar los medios de pagos de la lista de abonos seleccionados
     * <b>Módulo:</b> Asopagos - HU-31-202<br/>
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del administrador del subsidio.
     * @param numeroIdAdmin
     *        <code>String</code>
     *        Número de identificación del administrador del subsidio.
     * @param medioDePago
     *        <code>TipoMedioDePagoEnum</code>
     *        Tipo de medio de pago por el cual se quiere consultar los detalles asociados al saldo del administrador del subsidio
     * @return Lista de detalles de subsidios asignados relacionados con el saldo del administrador del subsidio.
     */
    @GET
    @Path("consultar/detallesSubsidioAsignados/asociadosSaldoAdminSubsidio")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,
            @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago);

    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado
     * y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: confirmarValorEntregado)
     * HU-31-203
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param valorSolicitado
     *        valor que se solicita retirar del saldo actual.
     * @param valorEntregado
     *        valor que fue entregado en la solicitud de retiro previa
     * @param fecha
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        usuario que realiza la transacción de retiro.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago.      
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("confirmarValorEntregadoSubsidio")
    public Map<String, String> confirmarValorEntregadoSubsidio(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("usuario") String usuario, @Context UserDTO userDTO, @QueryParam("user") String user,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);
    
    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado
     * y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: confirmarValorEntregado)
     * HU-31-203
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param valorSolicitado
     *        valor que se solicita retirar del saldo actual.
     * @param valorEntregado
     *        valor que fue entregado en la solicitud de retiro previa
     * @param fecha
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        usuario que realiza la transacción de retiro.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.     
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("confirmarValorEntregadoSubsidioCasoB")
    public Map<String, String> confirmarValorEntregadoSubsidioCasoB(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado, @NotNull @QueryParam("fecha") Long fecha,
            @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @QueryParam("usuario") String usuario, @Context UserDTO userDTO,
            @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla))
     * HU-31-203
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param saldoActualSubsidio
     *        saldo actual del subsidio (valor entregado por el servicio: consutlarSaldoSubsidio
     * @param valorSolicitado
     *        valor que se solicita retirar del saldo actual.
     * @param fecha
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercero pagador.
     * @param departamento
     *        código del DANE del departamento.
     * @param municipio
     *        código del DANE del municipio.
     * @param usuario
     *        usuario que realiza la transacción de retiro.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago. 
     * @param isVentanilla
     *        bandera: es true si viene por ventanilla, false de lo contrario 
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidio")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidio(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, 
            @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @NotNull @QueryParam("saldoActualSubsidio") BigDecimal saldoActualSubsidio,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado, 
            @NotNull @QueryParam("fecha") Long fecha,
            @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador, 
            @QueryParam("departamento") String departamento,
            @QueryParam("municipio") String municipio, 
            @QueryParam("usuario") String usuario,
            @QueryParam("isVentanilla") Boolean isVentanilla, 
            @Context UserDTO userDTO, 
            @QueryParam("user") String user,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro,  
            @QueryParam("check") String check);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla))
     * HU-31-203
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param saldoActualSubsidio
     *        saldo actual del subsidio (valor entregado por el servicio: consutlarSaldoSubsidio
     * @param valorSolicitado
     *        valor que se solicita retirar del saldo actual.
     * @param fecha
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercero pagador.
     * @param departamento
     *        código del DANE del departamento.
     * @param municipio
     *        código del DANE del municipio.
     * @param usuario
     *        usuario que realiza la transacción de retiro.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago. 
     * @param isVentanilla
     *        bandera: es true si viene por ventanilla, false de lo contrario 
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/solicitarRetiroConfirmarValorEntregadoSubsidioTerceroPag")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTerceroPag(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago,
            @NotNull @QueryParam("saldoActualSubsidio") BigDecimal saldoActualSubsidio,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado, @NotNull @QueryParam("fecha") Long fecha,
            @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador, @QueryParam("departamento") String departamento,
            @QueryParam("municipio") String municipio, @QueryParam("usuario") String usuario,
            @QueryParam("isVentanilla") Boolean isVentanilla, @QueryParam("user") String user,@QueryParam("idSitioPago") Long idSitioPago, @QueryParam("idConvenio") Long idConvenio);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero por una persona autorizada por el administrador del subsidio
     * y confirmar el valor entregado(HU-31-203 servicio: Solicitar retiro (persona autorizada) y confirmar valor entregado))
     * HU-31-203
     * @author mosorio
     * 
     * @param confirmacionEntregaPersonaAutorizadaDTO
     *        <code>SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO</code>
     *        DTO que contiene la información relevante para crear la solicitud de retiro, confirmación del valor entregado y creación de la
     *        persona autorizada junto al documento de autorización.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @POST
    @Path("solicitarRetiroPersonaAutorizada/confirmarValorEntregado")
    public Map<String, String> solicitarRetiroPersonaAutorizadaConfirmarValorEntregado(
            @Valid SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO confirmacionEntregaPersonaAutorizadaDTO,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Metodo que permite consultar la dispersión de una liquidación masiva que ya haya sido aprobada en segundo nivel.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     */
    @GET
    @Path("/consultar/dispersion/montoLiquidacion/{numeroRadicacion}")
    public DispersionMontoLiquidadoDTO consultarDispersionMontoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio tarjeta, que se encuentran pendientes por dispersdar en un proceso
     * de liquidación
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     */
    @GET
    @Path("/consultar/dispersion/montoLiquidado/pagoTarjeta/{numeroRadicacion}")
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoPagoTarjeta(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio efectivo, que se encuentran pendientes por dispersar en un proceso
     * de liquidación.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     */
    @GET
    @Path("/consultar/dispersion/montoLiquidado/pagoEfectivo/{numeroRadicacion}")
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoPagoEfectivo(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los pagos al medio efectivo, que se encuentran pendientes por dispersar en un proceso
     * de liquidación.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     */
    @GET
    @Path("/consultar/dispersion/montoLiquidado/pagoBanco/{numeroRadicacion}")
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoPagoBanco(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los descuentos por entidad, que se encuentran pendientes por dispersar.
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación masiva
     */
    @GET
    @Path("/consultar/dispersion/montoLiquidado/descuentoPorEntidad/{numeroRadicacion}")
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoDescuentosPorEntidad(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Metodo que permite consultar una cuenta de administrador de subsidio a partir de su id.
     * <b>Módulo:</b> Asopagos - HU-311-XXX<br/>
     * @author mosorio
     * 
     * @param idCuentaAdmonSubsidio
     *        <code>Long</code>
     *        Identificador de la cuenta del administrador del subsidio
     * @return CuentaAdministradorSubsdioDTO
     */
    @GET
    @Path("consultaCuentaAdmonSubsidio/{idCuentaAdmonSubsidio}")
    public CuentaAdministradorSubsidioDTO consultarCuentaAdmonSubsidioDTO(@PathParam("idCuentaAdmonSubsidio") Long idCuentaAdmonSubsidio);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los detalles de subsidios asignados
     * a partir de los identificadores principales.
     * <b>Módulo:</b> Asopagos - HU-311-XXX<br/>
     * @author mosorio
     * 
     * @param listaIdsDetalles
     *        <code>List<Long></code>
     *        Lista de identificadores principales de los detalles de subsidios asignados
     * @return Lista de detalles de subsidios asignados en forma de DTO
     */
    @POST
    @Path("consultarDetallesDTOPorIDs")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesDTOPorIDs(List<Long> listaIdsDetalles);

    /**
     * <b>Descripción:</b>Metodo que permite consultar los detalles de subsidios asignados a partir del identificador
     * de la cuenta de administrador del subsidio asignado a los anteriores.
     * <b>Módulo:</b> Asopagos - HU-311-XXX<br/>
     * @author mosorio
     * 
     * @param idCuentaAdmin
     *        <code>Long</code>
     *        Identificador de la cuenta del administrador del subsidio
     * @return Lista de detalles de subsidios asignados.
     */
    @GET
    @Path("consultarDetallesSubsidiosAsignadosAsociadosAbono/{idCuentaAdmin}")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbono(
            @PathParam("idCuentaAdmin") Long idCuentaAdmin);

    /**
     * <b>Descripción:</b>Metodo que consulta si un convenio del tercero pagador esta relacionado con una empresa.
     * <b>Módulo:</b> Asopagos - HU-311-210<br/>
     * @author mosorio
     * 
     * @param idEmpresa
     *        <code>Long</code>
     * @return DTO del convenio tercero pagador si existe, sino devuelve un nulo
     */
    @GET
    @Path("consultar/convenioTerceroPagadorPorIdEmpresa/{idEmpresa}")
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorIdEmpresa(@PathParam("idEmpresa") Long idEmpresa);

    /**
     * <b>Descripción:</b>Método que permite generar el archivo de consignaciones a bancos con la estructura definida para sudameris
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación de la liquidación
     * 
     * @return DTO con la información del archivo
     */
    @GET
    @Path("/generarArchivoResultadoConsignacionesBancos/{numeroRadicacion}")
    public InformacionArchivoDTO generarArchivoResultadoConsignacionesBancos(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite generar el archivo de pagos judiciales con la estructura definida para sudameris
     * <b>Módulo:</b> Asopagos - HU-311-441<br/>
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación de la liquidación
     * 
     * @return DTO con la información del archivo
     */
    @GET
    @Path("/generarArchivoResultadoPagosJudiciales/{numeroRadicacion}")
    public InformacionArchivoDTO generarArchivoResultadoPagosJudiciales(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @param estadoTransaccion
     *        <code>EstadoTransaccionSubsidioEnum</code>
     *        Estado al que se actualizarán los pagos
     * 
     * @param mediosDePago
     *        <code>List<TipoMedioDePagoEnum></code>
     *        Lista de medios de pago a los cuales se realizará la actualización
     */
    @GET
    @Path("/dispersarPagos/estadoEnviado/{numeroRadicacion}")
    public void dispersarPagosEstadoEnviado(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("estadoTransaccion") EstadoTransaccionSubsidioEnum estadoTransaccion,
            @QueryParam("mediosDePago") List<TipoMedioDePagoEnum> mediosDePago, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método que permite consultar las cuentas de administrador de subsidio en una liquidación para el medio de pago
     * tarjeta
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @return información de las cuentas de administrador
     */
    @GET
    @Path("/consultar/cuentasAdministrador/medioTarjeta/{numeroRadicacion}")
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjeta(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado APLICADO los pagos a dispersar en los medio efectivo y tarjeta
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @param abonosExitosos
     *        <code>List<Long></code>
     *        lista de indentificadores de las cuentas con abonos exitosos
     * 
     */
    @GET
    @Path("/dispersarPagos/estadoAplicado/{numeroRadicacion}")
    public void dispersarPagosEstadoAplicado(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("abonosExitosos") List<Long> abonosExitosos, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método que permite actualizar al estado APLICADO los pagos a dispersar en los medio efectivo y tarjeta
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @param abonosExitosos
     *        <code>List<Long></code>
     *        lista de indentificadores de las cuentas con abonos exitosos
     * 
     */
    @POST
    @Path("dispersarMasivoPagosEstadoAplicado")
    public void dispersarMasivoPagosEstadoAplicado(@QueryParam("numeroRadicacion") String numeroRadicacion,
            List<Long> abonosExitosos, @Context UserDTO userDTO);
    
    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar con origen anulación
     * 
     * @author rlopez
     * 
     * @param identificadoresCuentas
     *        <code>List<Long></code>
     *        Lista de identificadores de las cuentas de administrador
     */
    @GET
    @Path("/dispersarPagos/estadoEnviado/origenAnulacion")
    public void dispersarPagosEstadoEnviadoOrigenAnulacion(@QueryParam("identificadoresCuentas") List<Long> identificadoresCuentas);
    
    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar con origen anulación
     * 
     * @author rlopez
     * 
     * @param identificadoresCuentas
     *        <code>List<Long></code>
     *        Lista de identificadores de las cuentas de administrador
     */
    @POST 
    @Path("/dispersarPagos/estadoEnviadoH/origenAnulacionH")
    public void dispersarPagosEstadoEnviadoOrigenAnulacionH(List<Long> identificadoresCuentas); 

    /**
     * <b>Descripción:</b>Método que permite consultar las cuentas de administrador de subsidio para el medio de tarjeta, asociadas a los
     * identificadores parametrizados
     * 
     * @author rlopez
     * 
     * @param identificadoresCuentas
     *        <code>List<Long></code>
     *        Lista de identificadores de las cuentas de administrador
     * 
     * @return Lista de cuentas de administradores de subsidio
     */
    @GET
    @Path("/consultar/cuentasAdministrador/medioTarjeta/origenAnulacion")
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
            @QueryParam("identificadoresCuentas") List<Long> identificadoresCuentas);

    /**
     * <b>Descripción:</b>Método que permite actulizar al estado APLICADO los pagos a dispersar con origen anulación al medio tarjeta y
     * efectivo
     * 
     * @author rlopez
     * 
     * @param identificadoresCuentas
     *        <code>List<Long></code>
     *        Lista de indentificadores de las cuentas del administrador
     */
    @GET
    @Path("/dispersarPagos/estadoAplicado/origenAnulacion")
    public void dispersarPagosEstadoAplicadoOrigenAnulacion(@QueryParam("identificadoresCuentas") List<Long> identificadoresCuentas);

    /**
     * <b>Descripción:</b>Método que permite actulizar al estado APLICADO los pagos a dispersar con origen anulación al medio tarjeta y
     * efectivo
     * @author mosorio
     * 
     * @param retirosReversion
     *        <code>List<CuentaAdministradorSubsidioDTO></code>
     *        DTO de cuentas con el identificador del tercero pagador y el número de tarjeta para
     *        buscar los retiros que se les ejecutara la reversión
     */
    @POST
    @Path("reversion/retiros")
    public void realizarReversionRetiros(List<CuentaAdministradorSubsidioDTO> retirosReversion);

    /**
     * <b>Descripción:</b>Método encargado de realizar la validación y el cargue del archivo
     * de retiro de tarjetas.
     * <b>Módulo:</b> Asopagos - HU-311-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @param informacionArchivoDTO
     *        <code>InformacionArchivoDTO</code>
     *        DTO con la información del archivo de retiro de tarjetas
     * @return DTO con la información correspondiente a la validación y cargue del archivo.
     */
    @POST
    @Path("validarCargarArchivoRetiroTarjetaAnibol")
    public ResultadoValidacionArchivoRetiroDTO validarCargarArchivoRetiroTarjetaAnibol(@NotNull InformacionArchivoDTO informacionArchivoDTO,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar la solicitud de anulacion de subsidio cobrado y sus detalles en la
     * cuenta administrador de subsidio.
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        El numero de radicacion de una solicitud de anulación de subsidio cobrado
     * 
     * @return <code>List<DetalleSolicitudAnulacionSubsidioCobradoDTO></code>
     *         Lista de transacciones de abono cobradas de cuentas de administradores de subsidios
     *         con su respectivo detalle asociadas a la solicitud de anulacion de subsidio cobrado.
     */
    @GET
    @Path("solicitudAnulacionSubsidioCobrado/consultar/{numeroRadicacion}")
    public DetalleSolicitudAnulacionSubsidioCobradoDTO consultarDetalleSolicitudAnulacionSubsidioCobrado(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método encargado de actualizar informacion de la solicitud de anulacion de subsidio cobrado
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param solicitudAnulacionSubsidioCobradoModeloDTO
     *        <code>SolicitudAnulacionSubsidioCobradoModeloDTO</code>
     *        Representa una solicitud de anulación de subsidio cobrado a actualizar
     * 
     * @return <code>Boolean</code>
     *         Representa si la actualizacion de la informacion de la solicitud fue realizada con exito o no
     */
    @POST
    @Path("solicitudAnulacionSubsidioCobrado/actualizar")
    public Boolean actualizarSolicitudAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO);

    /**
     * <b>Descripción:</b>Método encargado de buscar el nombre de un archivo de consumo que viene desde ANIBOL,
     * si se encuentra y el archivo esta procesado se devuelve un TRUE, de lo contrario séra un FALSE.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @param nombreArchivo
     *        <code>String</code>
     *        Nombre del archivo al cual se requiere realizar la validación, guardar los datos y realizar las operaciones pertinentes.
     * @return TRUE si se encuentra coincidencia con el nombre del archivo y esta procesado, de lo contrario FALSE.
     */
    @GET
    @Path("buscarNombreArchivoConsumoTarjetaANIBOL/{nombreArchivo}")
    public Boolean buscarNombreArchivoConsumoTarjetaANIBOL(@NotNull @PathParam("nombreArchivo") String nombreArchivo);
    
    /**
     * <b>Descripción:</b>Método encargado de buscar el nombre de un archivo de consumo que viene desde ANIBOL,
     * si se encuentra y el archivo esta procesado se devuelve un TRUE, de lo contrario séra un FALSE.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @param nombreArchivo
     *        <code>String</code>
     *        Nombre del archivo al cual se requiere realizar la validación, guardar los datos y realizar las operaciones pertinentes.
     * @return TRUE si se encuentra coincidencia con el nombre del archivo y esta procesado, de lo contrario FALSE.
     */
    @GET
    @Path("consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre/{nombreArchivo}")
    public Boolean consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(@NotNull @PathParam("nombreArchivo") String nombreArchivo);
    

    /**
     * <b>Descripción:</b>Método encargado de guardar el registro de la información global
     * del archivo de consumo de tarjeta de parte de ANIBOL.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @param archivoConsumosAnibolModeloDTO
     *        <code>ArchivoConsumosAnibolModeloDTO</code>
     *        DTO que contiene la información del archivo de consumo.
     */
    @POST
    @Path("/guardarRegistroArchivoConsumosAnibol")
    public Long guardarRegistroArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO);
    
    /**
     * <b>Descripción:</b>Método encargado de guardar el registro de la información global
     * del archivo de consumo de tarjeta de parte de ANIBOL.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @param archivoConsumosAnibolModeloDTO
     *        <code>ArchivoConsumosAnibolModeloDTO</code>
     *        DTO que contiene la información del archivo de consumo.
     */
    @POST
    @Path("/guardarRegistroArchivoConsumosTerceroPagadorEfectivo")
    public Long guardarRegistroArchivoConsumosTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO);
    
    

    /**
     * <b>Descripción:</b>Método encargado de obtener el archivo de consumo dejado en el FTP por ANIBOL.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     */
    @GET
    @Path("obtenerArchivoConsumoTarjetaANIBOLFTP")
    public List<InformacionArchivoDTO> obtenerArchivoConsumoTarjetaANIBOLFTP();
    
    /**
     * <b>Descripción:</b>Método encargado de obtener la información de dispersión para una liquidación de fallecimiento.
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * 
     * @author rlopez
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/montoLiquidacion/{numeroRadicacion}")
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionMontoLiquidacionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio tarjeta, que se encuentran pendientes por dispersdar en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/detalleAdministrador/medioTarjeta/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio tarjeta, que se encuentran pendientes por dispersdar en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/detalleAdministrador/medioEfectivo/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio banco-consignaciones, que se encuentran pendientes por dispersdar
     * en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/detalleAdministrador/medioBancoConsignaciones/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los descuentos aplicados a la liquidación de fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/detalleAdministrador/descuentos/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoDescuentos(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método encargado de registrar una solicitud enviada por medio de los servicios que
     * se consumen desde ANIBOL.
     * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
     * 
     * @param reAnibolModeloDTO
     *        <code>RegistroSolicitudAnibolModeloDTO</code>
     *        DTO que contiene la información para registrar una solicitud enviada a ANIBOL.
     * 
     * @return Identificador del registro de la solicitud.
     */
    @POST
    @Path("/registrarSolicitudAnibol")
    public Long registrarSolicitudAnibol(@NotNull RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO);
    
    /**
     * Consulta los id de procesos de anibol para consultar que ha pasado con los pagos
     * @return
     */
    @GET
    @Path("/consultarIdsProcesoAnibol")
    public List<Long> consultarIdsProcesoAnibol();

    /**
     * <b>Descripción:</b>Método encargado de actualizar el registro de solicitud de ANIBOL con los
     * parametros de salida que tiene ANIBOL
     * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
     * 
     * @param idRegistroSolicitudAnibol
     *        <code>Long</code>
     *        Identificador del registro de solicitud de ANIBOL registrado.
     * @param parametrosOUT
     *        <code>String</code>
     *        Variable que contiene los parametros de salida por parte de ANIBOl en formato Json
     */
    @PUT
    @Path("/actualizarRegistroSolicitudAnibol/{idRegistroSolicitudAnibol}")
    public void actualizarRegistroSolicitudAnibol(@PathParam("idRegistroSolicitudAnibol")Long idRegistroSolicitudAnibol, String parametrosOUT);
    
    /**
     * <b>Descripción:</b> Método que permite realizar la dispersión inmediata del medio de pago.
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author mosorio
     * 
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param lstIdsCondicionesBeneficiarios
     *        <code>List<Long></code>
     *        Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return Lista con los abonos creados para realizar la aprobación de la liquidación y terminar el proceso de la HU.
     */
    @POST
    @Path("/dispersarLiquidacionFallecimiento/porAdmin")
    public List<Long> dispersarLiquidacionFallecimientoPorAdmin(@Context UserDTO userDTO,@NotNull List<Long> lstIdsCondicionesBeneficiarios);
    
    /**
     * <b>Descripción:</b> Método que permite obtener los identificadores de los abonos relacionados con los detalles programados.
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author mosorio
     *         
     * @param lstIdsCondicionesBeneficiarios
     *        <code>List<Long></code>
     *        Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return Lista de identificadores de los abonos asociados a los detalles programados.
     */
    @GET
    @Path("/buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios")
    public List<Long> buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(@QueryParam("lstIdsCondicionesBeneficiarios")@NotNull List<Long> lstIdsCondicionesBeneficiarios);

    /**
     * <b>Descripción:</b> Método que permite obtener los identificadores de los abonos relacionados con los detalles programados.
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author mosorio
     *         
     * @param lstIdsCondicionesBeneficiarios
     *        <code>List<Long></code>
     *        Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return Lista de identificadores de los abonos asociados a los detalles programados.
     */
    @GET
    @Path("/buscarCasPorIdCondicionesBeneficiariosYsolicitud")
    public List<Long> buscarCasPorIdCondicionesBeneficiariosYsolicitud(@QueryParam("lstIdsCondicionesBeneficiarios")@NotNull List<Long> lstIdsCondicionesBeneficiarios, @QueryParam("numeroRadicado") String numeroRadicado);
    /**
     * <b>Descripción:</b>Método encargado de consultar las transacciones realizadas en la cuenta
     * del administrador del subsidio por medio de un filtro.
     * HU-TRA-001
     * 
     * @param transaccionConsultada
     *        dto que contiene todos los filtros con los cuales se realizara la consulta.
     * 
     * @author jocampo
     * @return lista de cuentas de los administradores de subsidios.
     */
    @GET
    @Path("consultarTransaccionesSubsidioPorResultadoLiquidacion")
    public VistaRetirosSubsidioDTO consultarTransaccionesSubsidioPorResultadoLiquidacion(@QueryParam("idResultadoValidacionLiquidacion") Long idResultadoValidacionLiquidacion);
    
    /**
     * Metodo encargado de obtener los diferentes pagos de subsidio monetario que están pendiente por programar.
     * @author mosorio
     * 
     * @param idAdminSubsidio
     *        <code>Long</code>
     *        Id del administrador del subsidio
     * @param numeroRadicacion
     *        <code>String</code>
     *        Número de radicado al cual pertenece los pagos programados 
     * @return Lista de pagos de subsidio que faltan por programar
     */
    @GET
    @Path("/obtenerPagosSubsidio/{idAdminSubsidio}")
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidio(@PathParam("idAdminSubsidio") Long idAdminSubsidio,@QueryParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * <b>Descripción:</b>Método que permite actualizar al estado ENVIADO los pagos a dispersar con origen anulación del medio de pago TRANSFERENCIA
     * 
     * @author mosorio
     * 
     * @param identificadoresCuentas
     *        <code>List<Long></code>
     *        Lista de identificadores de las cuentas de administrador
     */
    @PUT
    @Path("/dispersarPagos/estadoEnviado/transferencia/origenAnulacion")
    public void dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(@NotNull List<Long> identificadoresCuentas);
    
    /**
     * <b>Descripción:</b>Método que permite generar el archivo de consignaciones a bancos con la estructura definida para sudameris
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * 
     * @author mosorio
     * 
     * @param esPagoJudicial
     *        Si es TRUE es porque es pago judicial, FALSE de lo contrario.
     * @param lstIdCuentasBancos
     *        Lista de ids con las cuentas de los nuevos registros por los cuales se generara el archivo
     * 
     * @return DTO con la información del archivo
     */
    @POST
    @Path("/generarArchivo/cambioMedioPago/bancos")
    public InformacionArchivoDTO generarArchivoCambioMedioPagoBancos(@QueryParam("esPagoJudicial") Boolean esPagoJudicial, List<Long>lstIdCuentasBancos);

    /**
     * Consulta los radicados de solicitudes de pago enviados a anibol 
     * @return
     */
    @GET
    @Path("consultarRadicadosProcesoAnibol")
    public List<String> consultarRadicadosProcesoAnibol();

    @GET
    @Path("consultarConvenioTerceroPagadorPorNombrePagos")
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorNombrePagos(@QueryParam("nombreTerceroPagador") String nombreTerceroPagador);



    /**
     * @return
     */
    @GET
    @Path("consultarRegistroSolicitudAnibol")
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnibol();

    /**
     * @return
     */
    @GET
    @Path("consultarRegistroSolicitudDescuentoAnibol")
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoAnibol();
    
    /**
     * @return
     */
    @GET
    @Path("consultarRegistroSolicitudDescuentoPrescripcionAnibol")
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoPrescripcionAnibol(@QueryParam("idSolicitud") Long idSolicitud);
    
    /**
     * @return
     */
    @GET
    @Path("consultarRegistroDispersionAnibol")
    public List<RegistroSolicitudAnibol> consultarRegistroDispersionAnibol();
    
    /**
     * @param solicitudLiquidacionSubsidio
     * @return
     */
    @GET
    @Path("consultarCuentaAdminSubsidioPorSolicitud")
    public List<CuentaAdministradorSubsidio> consultarCuentaAdminSubsidioPorSolicitud(@NotNull @QueryParam("solicitudLiquidacionSubsidio") Long solicitudLiquidacionSubsidio);

    /**
     * @param idRegistroSolicitudAnibol
     * @param estadoSolicitudAnibol
     */
    @PUT
    @Path("/actualizarEstadoSolicitudAnibol/{idRegistroSolicitudAnibol}/{estadoSolicitudAnibol}")
    public void actualizarEstadoSolicitudAnibol(@PathParam("idRegistroSolicitudAnibol")Long idRegistroSolicitudAnibol, @PathParam("estadoSolicitudAnibol")EstadoSolicitudAnibolEnum estadoSolicitudAnibol);

    /**
     * @param idCuentaAdminSubsidio
     * @return
     */
    @GET
    @Path("/consultar/cuentaAdmin/medioTarjeta/{idCuentaAdminSubsidio}")
	public CuentaAdministradorSubsidioDTO consultarCuentaAdminMedioTarjeta(@PathParam("idCuentaAdminSubsidio") Long idCuentaAdminSubsidio);

    /**
     * @param listaIdsDetalle
     * @return
     */
    @POST
    @Path("/consultar/listaDetallesSubsidioAsignado")
	List<DetalleSubsidioAsignadoDTO> obtenerListadoDetallesSubsidioAsingnado(List<Long> listaIdsDetalle);
    
    /**
     * @param listaIds
     * @return
     */
    @GET
    @Path("/consultar/cuentasAdministrador/medioTarjeta")
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidio(List<Long> listaIds);
    
    /**
     * @param lstIdsCondicionesBeneficiarios
     * @return
     */
    @POST
    @Path("/consultarDetallesProgramadosPorIdCondicionesBeneficiarios")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiarios(List<Long> lstIdsCondicionesBeneficiarios);
    
    @POST
    @Path("/consultarDetallesProgramadosPorSolicitud")
    List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorSolicitud(@QueryParam("numeroRadicado") String numeroRadicado, List<Long> lstIdsCondicionesBeneficiarios);
    
    /**
     * @param cuenta
     */
    @POST
    @Path("/actualizarCuentaAdministradorSubsidio")
    public void actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuenta);
    
    /**
     * @param detalleSubsidioAsignadoDTO
     */
    @POST
    @Path("/actualizarDetalleSubsidioAsignado")
    public void actualizarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO);
    
    @GET
    @Path("/consultarLiquidacionFallecimientoAnibol")
    public List<RegistroSolicitudAnibol> consultarLiquidacionFallecimientoAnibol();
    
    @POST
    @Path("/marcarAplicadoCuentasLiqFallecimiento")
    public void marcarAplicadoCuentasLiqFallecimiento(List<Long> listaIdsAdminSubsidio);
    
    /**
     * Servicio encargado de recibir la información respuesta de las transacciones no monetarias ejecutadas en Anibol 
     * para actualizar la información en Genesys 
     * 
     * @param consulta
     * 			Arreglo con la información de las transacciones procesadas por Anibol.
     */
    @POST
    @Path("/procesarCapturaResultadoReexpedicionBloqueo")
    public void procesarCapturaResultadoReexpedicionBloqueo(List<ResultadoReexpedicionBloqueoInDTO> listaConsulta);
    
    
    ///////////////////////////////////////////////////////////////////
    
    
    /**
     * Consulta si una persona está registrada en la aplicación y tiene asociada una tarjeta dada.
     * 
     * @param tipoIdentificacion
     * 			tipo de identificación de la persona a consultar.
     * 
     * @param identficacion
     * 			número de identificación de la persona a consultar.
     * 
     * @param numeroTarjeta
     * 			número de la tarjeta para validar si está asociada a la persona.
     * 
     * @return InfoPersonaReexpedicionDTO con la información encontrada.
     */
    @GET
    @Path("/consultarInfoPersonaReexpedicion")
    public InfoPersonaReexpedicionDTO consultarInfoPersonaReexpedicion(@QueryParam("tipoIdentificacion") String tipoIdentificacion, @QueryParam("identficacion") String identficacion, @QueryParam("numeroTarjeta") String numeroTarjeta);
    
    @GET
    @Path("/consultarInfoPersonaExpedicion")
    public InfoPersonaExpedicionDTO consultarInfoPersonaExpedicion(@QueryParam("tipoIdentificacion") String tipoIdentificacion, @QueryParam("identificacion") String identificacion);

    @GET
    @Path("/consultarInfoPersonaExpedicionValidaciones")
    public List<InfoPersonaExpedicionValidacionesDTO> consultarInfoPersonaExpedicionValidaciones(@QueryParam("tipoIdentificacion") String tipoIdentificacion, @QueryParam("identificacion") String identificacion);

    @POST
    @Path("/persistirRegistroInconsistenciaTarjeta")
    public void persistirRegistroInconsistenciaTarjeta(RegistroInconsistenciaTarjeta registroInconsistencia);
    
    @GET
    @Path("/realizarComparacionSaldoTarjetas")
    public ComparacionSaldoTarjetaEnum realizarComparacionSaldoTarjetas(@QueryParam("numeroTarjeta") String numeroTarjeta, @QueryParam("idPersona") Long idPersona, @QueryParam("saldoNuevaTarjeta") BigDecimal saldoNuevaTarjeta);
    
    @GET
    @Path("/consultarCuentasAdministradorSubAbono")
    public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorSubAbono(@QueryParam("numeroTarjeta") String numeroTarjeta, @QueryParam("idPersona") Long idPersona);
    
    @GET
    @Path("/consultarGruposTrabajadorMedioTarjeta")
    public List<GruposMedioTarjetaDTO> consultarGruposTrabajadorMedioTarjeta(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("identificacion") String identificacion, @QueryParam("numeroTarjeta") String numeroTarjeta);
    
    @GET
    @Path("/bloquearTarjeta")
    public void bloquearTarjeta(@QueryParam("numeroTarjeta") String numeroTarjeta);
    
    @POST
    @Path("/registrarMovimientosCasoUno")
    public void registrarMovimientosCasoUno(@QueryParam("idPersona") Long idPersona, ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, @Context UserDTO userDTO);
    
    @POST
    @Path("/registrarMovimientosCasoDos")
    public void registrarMovimientosCasoDos(@QueryParam("idPersona") Long idPersona, ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, @Context UserDTO userDTO);
    
    @GET
    @Path("/consultarRegistroInconsistencia")
    public List<RegistroInconsistenciaTarjeta> consultarRegistroInconsistencia(@QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin);
    
    @POST
    @Path("/cerrarCasoInconsistenciaTarjeta")
    public void cerrarCasoInconsistenciaTarjeta(@QueryParam("idRegistro") @NotNull Long idRegistro, @QueryParam("resultadoGestion") ResultadoGestionInconsistenciaEnum resultadoGestion, String detalleResolucion);
    
    @GET
    @Path("/consultarHistoricoRegistroInconsistencia")
    public List<RegistroInconsistenciaTarjeta> consultarHistoricoRegistroInconsistencia(@QueryParam("fechaInicial") Long fechaInicial, @QueryParam("fechaFinal") Long fechaFinal, @QueryParam("tipoId") TipoIdentificacionEnum tipoId, 
    		@QueryParam("numeroId") String numeroId, @QueryParam("estadoResolucion") EstadoResolucionInconsistenciaEnum estadoResolucion, @QueryParam("tipoNovedad") TipoNovedadInconsistenciaEnum tipoNovedad);
    
    @GET
    @Path("/consultarTempArchivoRetiroTerceroPagadorEfectivo") 
    public List<TempArchivoRetiroTerceroPagadorEfectivoDTO> consultarTempArchivoRetiroTerceroPagadorEfectivo(@QueryParam("idConvenio")Long idConvenio,@QueryParam("idArchivoRetiroTerceroPagadorEfectivo")Long idArchivoRetiroTerceroPagadorEfectivo);
    
    @GET
    @Path("/registrarArchivoTerceroPagadorEfectivo")
    public Long registrarArchivoTerceroPagadorEfectivo(@QueryParam("idDocumento")String idDocumento, 
    		@QueryParam("nombreDocumento")String nombreDocumento, @QueryParam("versionDocumento")String versionDocumento,
    		@QueryParam("nombreUsuario")String nombreUsuario);
    
    @POST
    @Path("/persistirTempArchivoRetiroTerceroPagador")
    public Long persistirTempArchivoRetiroTerceroPagador(TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO);
    
    @POST
    @Path("/actualizarTempArchivoRetiroTerceroPagador")
    public Long actualizarTempArchivoRetiroTerceroPagador(TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO);

    @POST
    @Path("/actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo")
	public Long actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(@QueryParam("archivoTerceroPagadorEfectivo")Long archivoTerceroPagadorEfectivo,
			@QueryParam("estado")EstadoArchivoConsumoTerceroPagadorEfectivo estado);

    @POST
    @Path("/persistirValidacionesNombreArchivoTerceroPagador")
	public void persistirValidacionesNombreArchivoTerceroPagador(Map<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> validaciones,
			@QueryParam("idArchivoRetiroTerceroPagadorEfectivo")Long idArchivoRetiroTerceroPagadorEfectivo);
    /**
     * <b>Descripción:</b>Método encargado de consultar el saldo que tiene un administrador del subsidio en la cuenta,
     * se filtra por el estado de transacción en aplicado,se suman dichos registros mas los retiros parciales y se le
     * restan los retiros aplicados.
     * HU-31-203
     * 
     * @param tipoIdAdmin
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        numero de identificación del administrador del subsidio
     * @param medioDePago
     *        medio de pago por el cual se buscara consultar el saldo.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param user
     *        usuario verificado por el canal de pago
     * @return Map que contiene el identificador de respuesta del registro de la operación, el nombre del administrador del subsidio y el
     *         saldo que tiene en ese medio de pago.
     */
    @GET
    @Path("consultarSaldoSubsidioSinOperacionAbono")
    public Double consultarSaldoSubsidioSinOperacionAbono(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago);
    @GET
    @Path("validarExistenciaIdentificadorTransaccion")
    public Boolean validarExistenciaIdentificadorTransaccion(@NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
    		@NotNull @QueryParam("idConvenio") Long idConvenio);
    
    /**
     * Método encargado de consultar los retiros con estado Solicitado
     * HU-31-415
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @GET
    @Path("consultarRetirosAbonosEstadoSolicitado")
    public List<CuentaAdministradorSubsidioDTO> consultarRetirosAbonosEstadoSolicitado(@NotNull @QueryParam("fechaInicial") Long fechaInicial,
            @NotNull @QueryParam("fechaFinal") Long fechaFinal);
    
    /**
     * Método encargado de generar un archivo excel de los retiros con estado solicitado
     * HU-31-415
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @POST
    @Path("exportarRetirosAbonosEstadoSolicitado")
    public Response exportarRetirosAbonosEstadoSolicitado(@NotNull @QueryParam("fechaInicial") Long fechaInicial,
            @NotNull @QueryParam("fechaFinal") Long fechaFinal);
    
    /**
     * Método que exporta en formato csv o xls la información de las cuotas dispersadas por un tercero pagador
     * PT-INGE-035-31-227
     * 
     * @param idTerceroPagador
     * @return
     */
    @POST
    @Path("exportarCuotasDispersadasPorTerceroPagador")
    public Response exportarCuotasDispersadasPorTerceroPagador(@NotNull @QueryParam("idConvenio") Long idConvenio);
    
   
    /**
     * Persiste un registro en ArchivoTransDetaSubsidio
     * @param archivoTransDetaSubsidio
     */
    @POST
    @Path("persistirArchivoTransDetaSubsidio")
	public Long persistirArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio);
    
    
    /**
     * Actualiza un registro en ArchivoTransDetaSubsidio
     * @param archivoTransDetaSubsidio
     */
    @PUT
    @Path("actualizarArchivoTransDetaSubsidio")
	public void actualizarArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio);
    
    
    
    /**
     * Consulta todos los registros de ArchivoTransDetaSubsidio
     * @return
     */
    @GET
    @Path("consultarArchivoTransDetaSubsidioTodos")
    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioTodos();
    
    /**
     * Consulta todos los registros de ArchivoTransDetaSubsidio
     * @return
     */
    @GET
    @Path("consultarCuentasPorAnularMantis266382")
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> consultarCuentasPorAnularMantis266382();
    
    @GET
    @Path("consultarDetallesSubsidioAsignadosPorCuentaAdmin")
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosPorCuentaAdmin(@NotNull @QueryParam("idCuentaAdminSub")Long idCuentaAdminSub);
    
    /**
     * Servicio encargado de consultar el valor total de los subsidios a anular
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param tipo
     *        Si se va a anular por prescripción o por vencimiento
     * @return el valor total de los subsidios a anular
     */
    @GET
    @Path("consultarValorTotalSubsidiosAnular")
    public BigDecimal consultarValorTotalSubsidiosAnular(@NotNull @QueryParam("tipo") String tipo); 
    
    /**
     * Servicio que se encarga de generar el resumen de la lista de subsidios a anular por prescripción o por vencimiento
     *
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param firstRequest
     *        Indica si es la primera vez que se llama al servicio
     * @param offset
     *        Indica la posición desde donde se empezarán a retornar registros
     * @param filter 
     *        Indica el tipo de filtro que se eligio por pantallas  LIQUIDACION_ASOCIADA 1, PERIODO_LIQUIDADO 2, TIPO_CUOTA 3, TIPO_LIQUIDACION 4, MEDIO_DE_PAGO 5, SITIO_DE_PAGO 6
     * @param orderBy
     *        Indica el ordenamiento de los registros
     * @param limit
     *        Indica cuantos registros se deben traer
     * @param tipo 
     *        Si se va a anular por prescripción o por vencimiento
     * @return lista de resumen de los subsidios a anular por prescripción o por vencimiento
     */
    @GET
    @Path("generarResumenListado/subsidiosAnular")
    public ResumenListadoSubsidiosAnularDTO generarResumenListadoSubsidiosAnular(@NotNull @QueryParam("firstRequest") Boolean firstRequest, @NotNull @QueryParam("offset") Integer offset, @NotNull @QueryParam("filter") Integer filter, @NotNull @QueryParam("orderBy") String orderBy, @NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("tipo") String tipo);
    
    
    /**
     * Servicio que se encarga de generar la lista de subsidios a anular por prescripción o por vencimiento
     *
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param firstRequest 
     *        Indica si es la primera vez que se llama al servicio
     * @param offset 
     *        Indica la posición desde donde se empezarán a retornar registros
     * @param orderBy 
     *        Indica el ordenamiento de los registros
     * @param limit 
     *        Indica cuantos registros se deben traer
     * @param tipo 
     *        Si se va a anular por prescripción o por vencimiento
     * @return lista de subsidios a anular por prescripción o por vencimiento
     */
    @GET
    @Path("generarListado/subsidiosAnular")
    public ListadoSubsidiosAnularDTO generarlistadoSubsidiosAnular(@NotNull @QueryParam("firstRequest") Boolean firstRequest, @NotNull @QueryParam("offset") Integer offset, @NotNull @QueryParam("orderBy") String orderBy, @NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("tipo") String tipo,@QueryParam("numeroIdentificacionAdminSub") String numeroIdentificacionAdminSub);
    
    /**
     * Servicio que se encarga de generar un archivo xlsx con la lista de subsidios a anular por prescripción o por vencimiento
     *
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param limit
     *        Indica cuantos registros se deben traer
     * @param tipo
     *        Si se va a anular por prescripión o por vencimiento
     * @return 
     */
    @GET
    @Path("generarExcelListado/subsidiosAnular")
    public byte[] generarExcelListadoSubsidiosAnular(@NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("tipo") String tipo);

    /**
     * Consulta los descuentos asociados a un detalle de subsidio asingado.
     * @param uriInfo
     * @param response
     * @param idDetalleSubsidioAsignado
     * @return 
     */
    @GET
    @Path("consultarDescuentosSubsidioAsignado")
    public List<DescuentoSubsidioAsignadoDTO> consultarDescuentosSubsidioAsignado(@QueryParam("idDetalleSubsidioAsignado") Long idDetalleSubsidioAsignado);


    /**
     * Servicio que se encarga de obtener la lista información de la solicitud de la dispersion de subsidio monetario
     *
     * @author <a href="camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @return Lista con la información de la solicitud de la dispersion
     */
    @GET
    @Path("consultarRegistroSolicitudDispersionSubsidioMonetario")
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDispersionSubsidioMonetario(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de obtener la lista información de la solicitud de la dispersion de subsidio monetario
     *
     * @author <a href="camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @return Lista con la información de la solicitud de la dispersion
     */
    @GET
    @Path("consultarRegistroSolicitudAnulacionSubsidioMonetario")
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnulacionSubsidioMonetario(@QueryParam("idSolicitud") Long idSolicitud);


    /**
     * Servicio que se encarga de actualizar el estado de las transacciones de la dispersion de subsidio monetario
     * que se procesaron correctamente
     *
     * @author <a href="camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     */
    @POST
    @Path("actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario")
    public void actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de obtener el id de la cuenta admin subsidio
     *
     * @author <a href="camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @param numeroTarjetaAdmonSubsidio
     *        Identificador de la solicitud a nivel de Anibol
     */
    @POST
    @Path("consultaCuentaAdmonSubsidioDispersionSubsidioMonetario")
    public Long consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(@QueryParam("idSolicitud") Long idSolicitud, @QueryParam("numeroTarjetaAdmonSubsidio") String numeroTarjetaAdmonSubsidio);
    
 /**
     * Servicio que consulta los correos de los destinatarios
     *
     * @param parametrizacion      Parametrización de la gestión de cobro
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param lineaCobro           Línea de cobro
     * @return Lista de correos
     */
    @POST
    @Path("/obtenerRolesDestinatariosPrescripcion")
    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatariosPrescripcion(@QueryParam("idCuentaAdmonSubsidio") Long idCuentaAdmonSubsidio) ;
  

    
    @GET
    @Path("/obtenerIdsAbonosPrescripcion/{parametro}")
    public List<Long> obtenerIdsAbonosPrescripcion(@NotNull @PathParam("parametro") String parametro);
    
       /**
     * <b>Descripción:</b>Método encargado de procesar si un abono fue o no exitoso. para archivo
     * Si fue exitoso se actualiza el estado de transacción de aplicado a cobrado.
     * Si no es exitoso se remite a la HU-31-200 "Gestionar transacciones no exitosas"
     * HU-31-199
     * 
     * @author eprocess
     * @param listaAbonos
     *        lista de abonos (cuentas administradores de subsidio) que serán procesadas.
     * @param userEmail
     */
    @PUT
    @Path("confirmarResultadosAbonosBancariosArchivo")
    public void confirmarResultadosAbonosBancariosArchivo(List<ConfirmacionAbonoBancarioCargueDTO> listaAbonos, @NotNull @QueryParam("userEmail") String userEmail);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla)).
     * Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author dmorales
     *
     * @param tipoIdAdmin
     *        <code>TipoIdentificacionEnum</code>
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        <code>String</code>
     *        numero de identificación del administrador del subsidio
     * @param valorSolicitado
     *        <code>BigDecimal</code>
     *        valor que se solicita retirar del saldo actual.
     * @param usuario
     *        <code>String</code>
     *        usuario que realiza la transacción de retiro.
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercero pagador.
     * @param departamento
     *        <code>String</code>
     *        código del DANE del departamento.
     * @param municipio
     *        <code>String</code>
     *        código del DANE del municipio.
     * @param user
     *        <code>String</code>
     *        Nombre del usuario que realiza la consulta del saldo
     * @param password
     *        <code>String</code>
     *        Contraseña asociada al usuario.
     * @param idPuntoCobro
     *        <code>String</code>
     *        Identificador del punto de cobro
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV2(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, 
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario, 
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento, 
            @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user, 
            @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro,
            @Context UserDTO userDTO);

    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento,
            @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro,
            @Context UserDTO userDTO);

    @POST
    @Path("/reversionRetiro")
    public Response reversionRetiro(@QueryParam("tipoIdentificacion") String tipoIdentificacion, @QueryParam("numeroIdentificacion")String numeroIdentificacion,
                                    @QueryParam("valorTransaccion") String valorTransaccion, @QueryParam("codigoMotivo") String codigoMotivo,
                                    @QueryParam("usuarioTerceroPagador") String usuarioTerceroPagador, @QueryParam("idTransaccion") String idTransaccion,
                                    @QueryParam("idPuntoCobro")String idPuntoCobro, @QueryParam("lineaNegocio") String lineaNegocio,
                                    @QueryParam("usuario")String usuario, @QueryParam("contrasena")String contrasena);

        @GET
        @Path("/validarExistenciaTarjeta")
        public Boolean validarExistenciaTarjeta(@QueryParam("numeroExpedido") String numeroExpedido);

        @GET
        @Path("/consultarGruposFamiliaresConMarcaYAdmin")
        public List<Long> consultarGruposFamiliaresConMarcaYAdmin(@QueryParam("tipoIdentificacion") String tipoIdentificacion, @QueryParam("numeroIdentificacion")String numeroIdentificacion, @QueryParam("numeroTarjeta") String numeroTarjeta, @QueryParam("expedicion") Boolean expedicion);

        @GET
        @Path("/consultarCuentaAdministradorSubsidio")
        CuentaAdministradorSubsidioYDetallesDTO consultarInfoRetiro(@QueryParam("tipoIdentificacion") String tipoIdAdmin, @QueryParam("numeroIdentificacion") String numeroIdAdmin, @QueryParam("medioDePago") TipoMedioDePagoEnum medioDePago);

        @POST
        @Path("/rollbackRetiro")
        void rollbackRetiro(@QueryParam("idPuntoCobro") String idPuntoCobro, @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
                            @QueryParam("usuario") String usuario, CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO, String idRetiro);

   /**
    * @param numeroRadicacion
    * @return información del estado del proceso
    * @author jpoveda
    */
   @GET
   @Path("/consultar/dispersion/estado/{numeroRadicacion}")
   public Map<String, String> consultarEstadoDispercion(@PathParam("numeroRadicacion") String numeroRadicacion);

        @POST
        @Path("/consultaTrasladoMedioDePago")
        public String consultaTrasladoMedioDePago(@NotNull @QueryParam("numeroDocumento") String numeroDocumento,@NotNull @QueryParam("tipoDocumento") TipoIdentificacionEnum tipoDocumento, List<TipoMedioDePagoEnum> medioDePago,@QueryParam("numeroTarjeta") String numeroTarjeta);

        @GET
        @Path("/consultarInfoMedioDePagoTraslado")
        public List<MedioDePagoModeloDTO> consultarInfoMedioDePagoTraslado(@NotNull @QueryParam("idAdmin") String idAdmin,@NotNull @QueryParam("tipoMedioDePago") TipoMedioDePagoEnum tipoMedioDePago);

        @POST
        @Path("/procesarTrasladoMedioDePago")
        public void procesarTrasladoMedioDePago(@NotNull @QueryParam("idAdmin") Long idAdmin,@NotNull @QueryParam("idMedioDePagoTraslado") Long idMedioDePagoTraslado,MediosPagoYGrupoTrasladoDTO mediosPagoYGrupoTrasladoDTO, @Context UserDTO userDTO);

        @POST
        @Path("/retomarTrasladoDeSaldos")
        public void retomarTrasladoDeSaldos(@Context UserDTO user);

        @POST
        @Path("/crearMedioDePagoYProcesarTraslado")
        public void crearMedioDePagoParaTraslado(@NotNull @QueryParam("idSitioPago") Long idSitioPago,@NotNull @QueryParam("idAdmin") Long idAdmin,MediosPagoYGrupoTrasladoDTO mediosPagoYGrupoTrasladoDTO, UserDTO user);

        @POST
        @Path("/registrarProcesoBandeja")
        public Long registrarProcesoBandeja(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,@NotNull @QueryParam("estado")  EstadoBandejaTransaccionEnum estado,
                @NotNull @QueryParam("proceso") ProcesoBandejaTransaccionEnum proceso,@QueryParam("idMedioDePagoOrigen") Long idMedioDePagoOrigen,@QueryParam("idMedioDePagoDestino") Long idMedioDePagoDestino);

        @POST
        @Path("/actualizarProcesoBandeja")
        public void actualizarProcesoBandeja(@NotNull @QueryParam("idBandeja")Long idBandeja,@QueryParam("estado") EstadoBandejaTransaccionEnum estado,@QueryParam("idSolicitud") Long idSolicitud);

        @POST
        @Path("/consultarltimaSolicitud")
        public Long consultarUltimaSolicitud(@NotNull @QueryParam("tipo")TipoIdentificacionEnum tipo, @NotNull @QueryParam("numero") String numero);

        @POST
        @Path("/consultarBandejaTransaccionesPorPersona")
        public List<BandejaDeTransacciones> consultarBandejaTransaccionesPorPersona( @QueryParam("proceso")String proceso, PersonaModeloDTO persona);

        @POST
        @Path("/consultarPersonasBandejaTransacciones")
        public List<PersonaModeloDTO> consultarPersonasBandejaTransacciones(@QueryParam("proceso") String proceso,PersonaModeloDTO persona);

        @GET
        @Path("/consultarDetalleBandejaTransacciones")
        public DetalleBandejaTransaccionesDTO consultarDetalleBandejaTransacciones(@QueryParam("idBandeja")Long idBandeja);

        @GET
        @Path("/consultarIdMedioDePagoTarjeta")
        public Long consultarIdMedioDePagoTarjeta(@NotNull @QueryParam("tipoIdentificacion")String tipoIdentificacion,@NotNull @QueryParam("numeroIdentificacion")String numeroIdentificacion);

        @GET
        @Path("/gestionBandejaTransacciones")
        public List<GestionDeTransaccionesDTO> gestionBandejaTransacciones();

        @GET
        @Path("consultarMediosDePagosTraslado/AdminSubsidio/{idAdminSubsidio}")
        public List<TipoMedioDePagoEnum> consultarMediosDePagosTrasladoAdminSubsidio(
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio);

        @GET
        @Path("/solicitarRetiro/consultarRetirosIntermedios")
        public Response consultarRetirosIntermedios(
                @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
                @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,
                @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
                @NotNull @QueryParam("usuario") String usuario,
                @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
                @NotNull @QueryParam("user") String user,
                @NotNull @QueryParam("password") String password,
                @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro,
                @Context UserDTO userDTO);
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV4")
    public Response solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV4(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento,
            @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro,
            @Context UserDTO userDTO);

    @POST
    @Path("cambioEstadoCuentasAdminSubsidio")
    public void cambioEstadoCuentasAdminSubsidio(
            List<Long> idCuentaList,
            @NotNull @QueryParam("usuarioNombre")String usuarioNombre);

    @PUT
    @Path("consultarTransaccionesDetallesSubsidiosCount")
    public Long consultarTransaccionesDetallesSubsidiosCount(@Context UriInfo uriInfo,
                                                             @Context HttpServletResponse response,
                                                             @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio);

    @POST
    @Path("/consultarCuentasAdminSubsidioPorIds")
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidioPorIds(List<Long> ltsIdsCuentaAdministradorSubsidio);
}
