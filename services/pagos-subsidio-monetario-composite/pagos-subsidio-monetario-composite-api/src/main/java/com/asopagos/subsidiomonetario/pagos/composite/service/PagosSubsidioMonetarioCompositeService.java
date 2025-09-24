package com.asopagos.subsidiomonetario.pagos.composite.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.pagos.dto.AbonoAnuladoDetalleAnuladoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoEntidadDescuentoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoBancoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoEfectivoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DispersionResultadoPagoTarjetaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroCambioMedioDePagoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoOutDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroTerceroPagDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioAnularDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioPrescribirAnularFechaDTO;
import com.asopagos.subsidiomonetario.pagos.dto.SubsidiosConsultaAnularPerdidaDerechoDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * a la definicion de servicios de composicion de pagos de subsidio monetario
 * <b>Módulo:</b> Asopagos - 31 - XXX<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co> Miguel Angel Osorio</a>
 */

@Path("PagosSubsidioMonetarioComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PagosSubsidioMonetarioCompositeService {

    /**
     * <b>Descripción:</b>Método que valida si el archivo de retiro identificado por la el
     * <code>idArchivoRetiro</code> tiene incosistencias en su estructura o en
     * sus campos.
     * HU-31-205
     * 
     * @author mosorio
     * @param idArchivoRetiro
     *        identificador que tiene el archivo de retiro en el CM
     * @param userDTO
     *        usuario que registra el archivo de retiro del tercero pagador.
     * @param nombreTerceroPagador
     *        nombre del tercero pagador asociado (nombre del convenio)
     * @return DTO con la información de la validación del archivo de retiro
     */

    @GET
    @Path("validarArchivoRetiro/{idArchivoRetiro}")
    public ResultadoValidacionArchivoRetiroDTO validarArchivoRetiro(@NotNull @PathParam("idArchivoRetiro") String idArchivoRetiro,
            @Context UserDTO userDTO, @QueryParam("nombreTerceroPagador") String nombreTerceroPagador);
    
    
    /**
     * <b>Descripción:</b>Método que valida si el archivo de retiro identificado por la el
     * <code>idArchivoRetiro</code>
     * sus campos.
     * HU-31-205
     * 
     * @author dsuesca
     * @param idArchivoRetiro
     *        identificador que tiene el archivo de retiro en el CM
     * @param userDTO
     *        usuario que registra el archivo de retiro del tercero pagador.
     * @param nombreTerceroPagador
     *        nombre del tercero pagador asociado (nombre del convenio)
     * @return DTO con la información de la validación del archivo de retiro
     */

    @GET
    @Path("cargarArchivoConsumoTerceroPagadorEfectivo/{idArchivoRetiro}/{idConvenio}")
    public ResultadoValidacionArchivoRetiroTerceroPagDTO cargarArchivoConsumoTerceroPagadorEfectivo(@NotNull @PathParam("idArchivoRetiro") String idArchivoRetiro,
    		@PathParam("idConvenio") Long idConvenio,@Context UserDTO userDTO); 
    
    /**
     * <b>Descripción:</b>Método que valida si el archivo de retiro identificado por la el
     * <code>idArchivoRetiro</code>
     * sus campos.
     * HU-31-205
     * 
     * @author dsuesca
     * @param idArchivoRetiro
     *        identificador que tiene el archivo de retiro en el CM
     * @param userDTO
     *        usuario que registra el archivo de retiro del tercero pagador.
     * @param nombreTerceroPagador
     *        nombre del tercero pagador asociado (nombre del convenio)
     * @return DTO con la información de la validación del archivo de retiro
     */

    @GET
    @Path("validarArchivoConsumoTerceroPagadorEfectivo/{idConvenio}/{idArchivoRetiro}/{IdArchivoRetiroTerceroPagadorEfectivo}")
    public Long validarArchivoConsumoTerceroPagadorEfectivo(@NotNull @PathParam("idConvenio")Long idConvenio,
    		@NotNull @PathParam("idArchivoRetiro") String idArchivoRetiro,
    		@NotNull @PathParam("IdArchivoRetiroTerceroPagadorEfectivo")Long IdArchivoRetiroTerceroPagadorEfectivo,@Context UserDTO userDTO);
    	  

    
    /**
     * <b>Descripción:</b> Metodo que crea el convenio del tercero pagador en el proceso
     * de pago.
     * HU-31-210
     * 
     * @author mosorio
     * @param convenio
     *        DTO que contiene toda la información relevevante para crear el convenio del tercero pagador
     * @return número de radicado de la solicitud global que esta relacionada con la solicitud del registro del convenio.
     */
    @POST
    @Path("crearConvenioTerceroPagador")
    public Long crearConvenioTerceroPagador(ConvenioTercerPagadorDTO convenio);

    /**
     * <b>Descripción:</b> Metodo que registra los detalles de subsidios asignados relacionados
     * a un abono.
     * HU-31-216
     * 
     * @author mosorio
     * @param detallesSubsidiosAsignados
     *        lista de detalles de subsidios asignados que seran registrados en un abono.
     * @param userDTO
     *        usuario que registra los detalles de los subsidios asignados.
     * @return
     */
    @POST
    @Path("crearDetallesSubsidiosAsignadosPagos")
    public Long crearDetallesSubsidiosAsignadosPagos(List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignados, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de registrar una solicitud de anulación de subsidios asignados cobrados.
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
    @Path("registrarSolicitudAnulacionSubsidioCobrado")
    public SolicitudAnulacionSubsidioCobradoDTO registrarSolicitudAnulacionSubsidioCobrado(
            @Valid SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado, @Context UserDTO userDTO);

    /**
     * /**
     * <b>Descripción:</b> Método que se encarga de anular los detalles de subsidio monetario
     * sin reemplazo de cada uno de los abonos.
     * <b>Módulo:</b> Asopagos - HU-31-208 & HU-31-221<br/>
     * @author mosorio
     * 
     * @param abonoAnuladoDetalleAnuladoDTO
     *        objeto que contiene la lista de detalles de subsidios asignados a ser anulados y la lista de id de los abonos
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @return lista de id de las nuevos abonos (Cuentas de administradores del subsidio) creados con sus respectivos detalles
     */
    @POST
    @Path("anularSubsidiosMonetariosSinReemplazo")
    public List<Long> anularSubsidiosMonetariosSinReemplazo(AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b> Método que se encarga de anular los detalles de subsidio monetario
     * con reemplazo de cada uno de los abonos.
     * <b>Módulo:</b> Asopagos - HU-31-207 & HU-31-220<br/>
     * 
     * @param abonoAnuladoDetalleAnuladoDTO
     *        objeto que contiene la lista de detalles de subsidios asignados a ser anulados y la lista de id de los abonos
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @author mosorio
     * @return lista de id de las nuevos abonos (Cuentas de administradores del subsidio) creados con sus respectivos detalles
     */
    @POST
    @Path("anularSubsidiosMonetariosConReemplazo")
    public List<Long> anularSubsidiosMonetariosConReemplazo(AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b> Metodo encargado de anular los registros relacionados a los abonos
     * por fecha de venicmiento.
     * <b>Módulo:</b> Asopagos - HU-31-223<br/>
     * @author mosorio
     * 
     * @param listaSubsidiosAnular
     *        lista de subsidios seleccionados para ser anulados
     * @param userDTO
     *        usuario registrado en el sistema que registra la cuenta.
     * @return lista de id de las nuevos abonos (Cuentas de administradores del subsidio) creados con sus respectivos detalles
     */
    @POST
    @Path("ejecutarAnulacion/porFechaDeVencimiento")
    public void ejecutarAnulacionPorFechaDeVencimiento(@Context UserDTO userDTO, @QueryParam("limit")Integer limit);

    /**
     * <b>Descripción:</b> Metodo encargado de anular los registros relacionados a los abonos
     * por prescripción.
     * <b>Módulo:</b> Asopagos - HU-31-224<br/>
     * @author mosorio
     * 
     * @param userDTO
     *        usuario registrado en el sistema que registra la cuenta.
     * @return lista de id de las nuevos abonos (Cuentas de administradores del subsidio) creados con sus respectivos detalles
     */
    @POST
    @Path("ejecutarAnulacion/porPrescripcion")
    public void ejecutarAnulacionPorPrescripcion(@Context UserDTO userDTO, @QueryParam("limit")Integer limit);

    /**
     * <b>Descripción:</b>Método encargado de anular los subsidios monetarios por prescripción.
     * <b>Módulo:</b> Asopagos - HU-31-225<br/>
     * @author mosorio
     * 
     * @param subsidiosCandidatosAnular
     *        lista de subsidios monetarios a ser anulados.
     * @param userDTO
     *        usuario registrado en el sistema que registra la cuenta.
     * @return lista de id de los nuevos abonos que se generan por la anulación.
     */
    @POST
    @Path("ejecutarAnulacion/porPerdidaDeDerecho") 
    public List<Long> ejecutarAnulacionPorPerdidaDeDerecho(List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular,
            @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de anular los subsidios monetarios por prescripción.
     * <b>Módulo:</b> Asopagos - HU-31-225<br/>
     * @author mosorio
     * 
     * @param subsidiosCandidatosAnular
     *        lista de subsidios monetarios a ser anulados.
     * @param userDTO
     *        usuario registrado en el sistema que registra la cuenta.
     * @return lista de id de los nuevos abonos que se generan por la anulación.
     */
    @POST
    @Path("utilitarioAnularCuota20200211")
    public Boolean utilitarioAnularCuota20200211(@Context UserDTO userDTO);
    
    /**
     * <b>Descripción:</b>Método encargado de cambiar los medios de pagos de la lista de abonos seleccionados
     * <b>Módulo:</b> Asopagos - HU-31-219<br/>
     * @author mosorio
     * 
     * @param registroCambioMedioDePagoDTO
     *        DTO que contiene la información de los registros seleccionados para cambiar su medio de pago y el medio de pago seleccionado.
     * 
     * @return lista de abonos generados por las anulaciones.
     */
    @POST
    @Path("registrar/cambioMedioDePago/{idAdminSubsidio}")
    public List<Long> registrarCambioMedioDePagoSubsidio(@NotNull RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO,
            @NotNull @PathParam("idAdminSubsidio") Long idAdminSubsidio, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de consultar si una empresa existe; si existe y esta asociada a un
     * convenio del tercero pagador, se lanza una excepción, si existe y no esta asociada se muestra la información
     * de dicha empresa y si por lo contrario no existe se devuelve un nulo.
     * <b>Módulo:</b> Asopagos - HU-31-10<br/>
     * @author mosorio
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación asociada a la empresa.
     * @param numeroIdentificacion
     *        <code>String</code>
     *        Número de identificación asociado a la empresa
     * @return
     */
    @GET
    @Path("consultar/empresaConvenioTerceroPagador")
    public EmpresaModeloDTO consultarEmpresaConvenioTerceroPagador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * <b>Descripción:</b>Método encargado de generar el archivo de consignaciones a bancos para una liquidación
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @return identificador del archivo en el ECM
     */
    @GET
    @Path("/generarArchivoConsignacionesBancos/{numeroRadicacion}")
    public String generarArchivoConsignacionesBancos(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método encargado de generar el archivo de consignaciones a bancos por concepto de pagos judiciales
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicación
     * 
     * @return identificador del archivo en el ECM
     */
    @GET
    @Path("/generarArchivoPagosJudiciales/{numeroRadicacion}")
    public String generarArchivoPagosJudiciales(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite obtener el identificador del archivo de descuentos aplicados en un proceso de liquidación
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        valor del número de radicado
     * 
     * @param idEntidadDescuento
     *        <code>Long</code>
     *        identificador de la entidad de descuento para la cual se desea obtener el archivo
     * 
     * @return identificador del archivo en el ECM
     */
    @GET
    @Path("/generarArchivoDescuentosPorEntidad")
    public String generarArchivoDescuentosPorEntidad(@QueryParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("idEntidadDescuento") Long idEntidadDescuento);

    /**
     * <b>Descripción:</b> Método que permite cargar el archivo de consumo de las tarjetas en una carpeta respectiva que deja ANIBOL
     * por medio de FTP se obtienen los archivos, se realizan las validaciones respectivas y se cobran los retiros.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @return DTO con la información de la validación del archivo de retiro
     */
    @GET
    @Path("/cargarArchivoConsumoTarjetaAnibol")
    public void cargarArchivoConsumoTarjetaAnibol();

    /**
     * <b>Descripción:</b>Método encargado de realizar el proceso de aprobacion de una solicitud de anulacion de
     * subsidio cobrado y sus detalles en la cuenta administrador de subsidio.
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param solicitudAnulacionSubsidioCobradoDTO
     *        <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *        representa una solicitud de anulación de subsidio cobrado y sus detalles de abonos asociados
     * 
     * @return <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *         representa los datos del proceso de aprobacion de solicitud de anulación de subsidio cobrado
     */
    @POST
    @Path("solicitudAnulacionSubsidioCobrado/aprobar/{numeroSolicitudAnulacion}/{idTarea}")
    public SolicitudAnulacionSubsidioCobradoDTO aprobarSolicitudAnulacionSubsidioCobrado(
            @PathParam("numeroSolicitudAnulacion") String numeroSolicitudAnulacion, @PathParam("idTarea") String idTarea,
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, @Context UserDTO userDTO);

    /**
     * <b>Descripción:</b>Método encargado de realizar el proceso de rechazo de una solicitud de anulacion
     * de subsidio cobrado y sus detalles en la cuenta administrador de subsidio.
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
     * 
     * @param solicitudAnulacionSubsidioCobradoDTO
     *        <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *        representa una solicitud de anulación de subsidio cobrado y sus detalles de abonos asociados
     * 
     * @return <code>SolicitudAnulacionSubsidioCobradoDTO</code>
     *         representa los datos del proceso de aprobacion de solicitud de anulación de subsidio cobrado
     */
    @POST
    @Path("solicitudAnulacionSubsidioCobrado/rechazar/{numeroSolicitudAnulacion}/{idTarea}")
    public SolicitudAnulacionSubsidioCobradoDTO rechazarSolicitudAnulacionSubsidioCobrado(
            @PathParam("numeroSolicitudAnulacion") String numeroSolicitudAnulacion, @PathParam("idTarea") String idTarea,
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, @Context UserDTO userDTO);
    
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
    @Path("/consultar/dispersion/montoLiquidado/resultadoPagoTarjeta/{numeroRadicacion}")
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoResultadoPagoTarjeta(
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
    @Path("/consultar/dispersion/montoLiquidado/resultadoPagoEfectivo/{numeroRadicacion}")
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoResultadoPagoEfectivo(
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
    @Path("/consultar/dispersion/montoLiquidado/resultadoPagoBanco/{numeroRadicacion}")
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoResultadoPagoBanco(
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
    @Path("/consultar/dispersion/montoLiquidado/resultadoDescuentoPorEntidad/{numeroRadicacion}")
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoResultadoDescuentosPorEntidad(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    
    /**
     * <b>Descripción:</b> Método que permite cargar el archivo de consumo de las tarjetas en una carpeta respectiva de ANIBOL manualmente, esto es
     * posterior al cargo automatico por si ocurre un error.
     * <b>Módulo:</b> Asopagos - HU-31-ANEXO<br/>
     * 
     * @author mosorio
     * 
     * @return DTO con la información de la validación del archivo de retiro
     */
    @GET
    @Path("/cargar/manual/ArchivoConsumoTarjetaAnibol/{idArchivoConsumo}")
    public ResultadoValidacionArchivoRetiroDTO cargarManualArchivoConsumoTarjetaAnibol(@NotNull @PathParam("idArchivoConsumo") String idArchivoConsumo);
    
    /**
     * <b>Descripción:</b>Método encargado de consultar el saldo que tiene un administrador del subsidio en la cuenta,
     * se filtra por el estado de transacción en aplicado,se suman dichos registros mas los retiros parciales y se le
     * restan los retiros aplicados. Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        <code>TipoIdentificacionEnum</code>
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        <code>String</code>
     *        numero de identificación del administrador del subsidio
     * @param user
     *        <code>String</code>
     *        Nombre del usuario que realiza la consulta del saldo
     * @param password
     *        <code>String</code>
     *        Contraseña asociada al usuario.
     * @return Map que contiene el identificador de respuesta del registro de la operación, el nombre del administrador del subsidio y el
     *         saldo que tiene en ese medio de pago.
     */
    @GET
    @Path("/consultarSaldoSubsidioTransaccion")
    public Map<String, String> consultarSaldoSubsidioTransaccion(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: consultarSaldoSubsidioTransaccion). Este servicio se ejecuta desde los canales de medios de
     * pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @param fecha
     *        <code>Long</code>
     *        fecha en la cual ocurre la transacción de retiro
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
     *        identificador punto de cobro.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/solicitarRetiroSubsidioTransaccion")
    public Map<String, String> solicitarRetiroSubsidioTransaccion(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento, @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user, @NotNull @QueryParam("password") String password, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado y retirar el dinero de un administrador del subsidio
     * despúes de que haya consultado su saldo. Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @param valorEntregado
     *        <code>BigDecimal</code>
     *        valor que fue entregado en la solicitud de retiro previa
     * @param fecha
     *        <code>Long</code>
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        <code>String</code>
     *        usuario que realiza la transacción de retiro.
     * @param user
     *        <code>String</code>
     *        Nombre del usuario que realiza la consulta del saldo
     * @param password
     *        <code>String</code>
     *        Contraseña asociada al usuario.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/confirmarValorEntregadoSubsidioTransaccion")
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccion(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);
    
    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado y retirar el dinero de un administrador del subsidio
     * despúes de que haya consultado su saldo. Este servicio se ejecuta desde la funcionalidad de la 
     * HU-31-XX-Confirmación de valor entregado para transacciones de retiro.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author amarin
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
     * @param valorEntregado
     *        <code>BigDecimal</code>
     *        valor que fue entregado en la solicitud de retiro previa
     * @param fecha
     *        <code>Long</code>
     *        fecha en la cual ocurre la transacción de retiro
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        <code>String</code>
     *        usuario que realiza la transacción de retiro.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/confirmarValorEntregadoSubsidioTransaccionCasoB")
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionCasoB(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla)).
     * Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @param fecha
     *        <code>Long</code>
     *        fecha en la cual ocurre la transacción de retiro
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
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccion")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(
            @NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("fecha") Long fecha,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento, @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user, @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);


    /**
     * <b>Descripción:</b>Método encargado de consultar el saldo que tiene un administrador del subsidio en la cuenta,
     * se filtra por el estado de transacción en aplicado,se suman dichos registros mas los retiros parciales y se le
     * restan los retiros aplicados. Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
     *
     * @param tipoIdAdmin
     *        <code>TipoIdentificacionEnum</code>
     *        tipo de identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin
     *        <code>String</code>
     *        numero de identificación del administrador del subsidio
     * @param user
     *        <code>String</code>
     *        Nombre del usuario que realiza la consulta del saldo
     * @param password
     *        <code>String</code>
     *        Contraseña asociada al usuario.
     * @return Map que contiene el identificador de respuesta del registro de la operación, el nombre del administrador del subsidio y el
     *         saldo que tiene en ese medio de pago.
     */
    @GET
    @Path("/consultarSaldoSubsidioTransaccionValidaciones")
    public Map<String, String> consultarSaldoSubsidioTransaccionValidaciones(@Valid @NotNull(message = "El Campo ID Admin es obligatorio") @QueryParam("tipoIdAdmin") String tipoIdAdmin,
                                                                             @Valid @NotNull(message = "El Campo numeroIdAdmin es obligatorio") @QueryParam("numeroIdAdmin") String numeroIdAdmin,
                                                                             @Valid @NotNull(message = "El Campo user es obligatorio") @QueryParam("user") String user,
                                                                             @Valid @NotNull(message = "El Campo password es obligatorio") @QueryParam("password") String password);

    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: consultarSaldoSubsidioTransaccion). Este servicio se ejecuta desde los canales de medios de
     * pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     *        identificador punto de cobro.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/solicitarRetiroSubsidioTransaccionValidaciones")
    public Map<String, String> solicitarRetiroSubsidioTransaccionValidaciones(@NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
                                                                              @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
                                                                              @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
                                                                              @NotNull @QueryParam("departamento") String departamento, @NotNull @QueryParam("municipio") String municipio,
                                                                              @NotNull @QueryParam("user") String user, @NotNull @QueryParam("password") String password, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado y retirar el dinero de un administrador del subsidio
     * despúes de que haya consultado su saldo. Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @param valorEntregado
     *        <code>BigDecimal</code>
     *        valor que fue entregado en la solicitud de retiro previa
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        <code>String</code>
     *        usuario que realiza la transacción de retiro.
     * @param user
     *        <code>String</code>
     *        Nombre del usuario que realiza la consulta del saldo
     * @param password
     *        <code>String</code>
     *        Contraseña asociada al usuario.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/confirmarValorEntregadoSubsidioTransaccionValidaciones")
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionValidaciones(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado, @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);



    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla)).
     * Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidaciones")
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidaciones(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, 
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario, 
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento, 
            @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user, 
            @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);


    /**
     * <b>Descripción:</b>Método encargado de solicitar y retirar el dinero de un administrador del subsidio despúes de que haya
     * consultado su saldo (HU-31-203 servicio: Solicitar retiro y confirmar valor entregado (susuerte y ventanilla)).
     * Este servicio se ejecuta desde los canales de medios de pago.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author mosorio
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
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3")
    public Response solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,
            @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("usuario") String usuario,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("departamento") String departamento,
            @NotNull @QueryParam("municipio") String municipio,
            @NotNull @QueryParam("user") String user,
            @NotNull @QueryParam("password") String password,
            @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);

    /**
     * <b>Descripción:</b>Método encargado de confirmar el valor entregado y retirar el dinero de un administrador del subsidio
     * despúes de que haya consultado su saldo. Este servicio se ejecuta desde la funcionalidad de la
     * HU-31-XX-Confirmación de valor entregado para transacciones de retiro.
     * <b>Módulo:</b> Asopagos - HU-31-203<br/>
     * @author amarin
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
     * @param valorEntregado
     *        <code>BigDecimal</code>
     *        valor que fue entregado en la solicitud de retiro previa
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercero pagador.
     * @param usuario
     *        <code>String</code>
     *        usuario que realiza la transacción de retiro.
     * @return map que contiene el identificador de respuesta del registro de la operación y un booleano.
     */
    @GET
    @Path("/confirmarValorEntregadoSubsidioTransaccionCasoBValidaciones")
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionCasoBValidaciones(
            @NotNull @QueryParam("tipoIdAdmin") String tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin, @NotNull @QueryParam("valorSolicitado") BigDecimal valorSolicitado,
            @NotNull @QueryParam("valorEntregado") BigDecimal valorEntregado,
            @NotNull @QueryParam("idTransaccionTercerPagador") String idTransaccionTercerPagador,
            @NotNull @QueryParam("usuario") String usuario, @NotNull @QueryParam("idPuntoCobro") String idPuntoCobro);


    /**
     * <b>Descripción:</b>Método encargado de obtener la información de dispersión para una liquidación de fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * 
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        Valor del número de radicación
     * 
     * @return DTO con la información de dispersión
     */
    @GET
    @Path("/consultar/dispersionFallecimiento/resultadoMontoLiquidacion/{numeroRadicacion}")
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionResultadoMontoLiquidacionFallecimiento(
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
    @Path("/consultar/dispersionFallecimiento/resultadoDetalleAdministrador/medioTarjeta/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoTarjeta(
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
    @Path("/consultar/dispersionFallecimiento/resultadoDetalleAdministrador/medioEfectivo/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoEfectivo(
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
    @Path("/consultar/dispersionFallecimiento/resultadoDetalleAdministrador/medioBancoConsignaciones/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoBancoConsignaciones(
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
    @Path("/consultar/dispersionFallecimiento/resultadoDetalleAdministrador/descuentos/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoDescuentos(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);
    
    /**
     * <b>Descripción:</b> Método que permite realizar la dispersión inmediata del medio de pago tarjeta para la liquidación de
     * fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author mosorio
     * 
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param numeroIdentificacion
     *        <code>String</code>
     * @param tipoIdentificacion
     *        <code>String</code>
     * @param lstIdsCondicionesBeneficiarios
     *        <code>List<Long></code>
     *        Lista de los identificadores asociados a las condiciones de los beneficiarios relacionados a los detalles.
     * @return si retorna un -1 es porque la tarjeta no esta activa; si retorna un 1 es porque el proceso se realizo adecuadamente.
     */
    @POST
    @Path("/dispersarLiquidacionFallecimientoTarjeta/{numeroIdentificacion}/{tipoIdentificacion}/{numeroRadicado}")
    public Long dispersarLiquidacionFallecimientoTarjeta(@Context UserDTO userDTO,
            @PathParam("numeroIdentificacion") String numeroIdentificacion,
            @PathParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
            @PathParam("numeroRadicado") String numeroRadicado,
            @NotNull List<Long> lstIdsCondicionesBeneficiarios);
    
    /**
     * Metodo encargado de obtener los diferentes pagos de subsidio monetario que están pendiente por programar por administrador de
     * subsidio
     * @author mosorio
     * 
     * @param tipoIdAdmin
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del administrador del subsidio.
     * @param numeroIdAdmin
     *        <code>String</code>
     *        Número de identificación del administrador del subsidio.
     * @param numeroRadicacion
     *        <code>String</code>
     *        Número de radicado al cual pertenece los pagos programados 
     * @return Lista de pagos de subsidio que faltan por programar para un administrador del subsidio.
     */
    @GET
    @Path("/obtenerPagosSubsidioPendientes")
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidioPendientes(@NotNull @QueryParam("tipoIdAdmin") TipoIdentificacionEnum tipoIdAdmin,
            @NotNull @QueryParam("numeroIdAdmin") String numeroIdAdmin,@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);
    
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
     * @return ID del archivo
     */
    @POST
    @Path("/generarArchivoBancos219/{esPagoJudicial}")
    public String generarArchivoBancos219(@PathParam("esPagoJudicial") Boolean esPagoJudicial ,List<Long>lstIdCuentasBancos);

    @POST
    @Path("/dispersarDescuentos")
	public void dispersarDescuentos();
    
    @POST
    @Path("/dispersarAnulacionesPrescripcionPaso1")
	public void dispersarAnulacionesPrescripcionPaso1();
    
    @POST
    @Path("/dispersarAnulacionesPrescripcionPaso2")
	public void dispersarAnulacionesPrescripcionPaso2(ResultadoDispersionAnibolDTO respuestaAnibol);
    
    @POST
    @Path("/dispersarPagosCambioMedioPago")
	public void dispersarPagosCambioMedioPago();
    
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
    @Path("/dispersarLiquidacionFallecimiento")
    public List<Long> dispersarLiquidacionFallecimiento(@Context UserDTO userDTO, @QueryParam("numeroRadicado") String numeroRadicado, @NotNull List<Long> lstIdsCondicionesBeneficiarios);
    
    
    @POST
    @Path("/dispersarPagosLiquidacionFallecimiento")
	public void dispersarPagosLiquidacionFallecimiento();
    
    /**
     * Servicio encargado de recibir la información respuesta de las transacciones no monetarias ejecutadas en Anibol 
     * para actualizar la información en Genesys 
     * 
     * @param consulta
     * 			Arreglo con la información de las transacciones procesadas por Anibol.
     * 
     * @return List<ResultadoReexpedicionBloqueoOutDTO> con la información de las transacciones recibidas y el estado de dicha recepción.
     */
    @POST
    @Path("/capturarResultadoReexpedicionBloqueo")
    public List<ResultadoReexpedicionBloqueoOutDTO> capturarResultadoReexpedicionBloqueo(@NotNull List<ResultadoReexpedicionBloqueoInDTO> consulta);
    
    /**
     * Servicio encargado de recibir la información respuesta de las transacciones no monetarias ejecutadas en Anibol 
     * para actualizar la información en Genesys 
     * 
     * @param consulta
     * 			Arreglo con la información de las transacciones procesadas por Anibol.
     */
    @POST
    @Path("/procesarResultadoReexpedicionBloqueoAnibol")
    public void procesarResultadoReexpedicionBloqueoAnibol(List<ResultadoReexpedicionBloqueoInDTO> listaConsulta);
    
    /**
     * Servicio encargado de obtener el valor total de los subsidios a anular
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param tipo si se va a anular por prescripción o por vencimiento
     * @return el valor total de los subsidios a anular
     */
    @GET
    @Path("obtenerValorTotalSubsidiosAnular")
    public BigDecimal obtenerValorTotalSubsidiosAnular(@NotNull @QueryParam("tipo") String tipo); 
    
    /**
     * Servicio encargado de obtener el resumen de los subsidios a anular
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param uriInfo
     * @param response
     * @param tipo 
     *        Si se va a anular por prescripción o por vencimiento
     * @return lista agrupada de los subsidios a anular
     */
    @GET
    @Path("resumenListado/subsidiosAnular")
    public List<SubsidioAnularDTO> resumenListadoSubsidiosAnular(@Context UriInfo uriInfo, @Context HttpServletResponse response, @NotNull @QueryParam("tipo") String tipo);

    /**
     * Servicio que se encarga de consultar la información asociada al detalle y al abono del detalle
     * para posteriormente ser anulada por prescripción o por vencimiento
     * HU-31-224
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param uriInfo
     * @param response
     * @param tipo 
     *        Si se va a anular por prescripción o por vencimiento
     * @return lista de subsidios a anular
     */
    @GET
    @Path("listado/subsidiosAnular")
    public List<SubsidioMonetarioPrescribirAnularFechaDTO> listadoSubsidiosAnular(@Context UriInfo uriInfo, @Context HttpServletResponse response,  @NotNull @QueryParam("tipo") String tipo,@QueryParam("numeroIdentificacionAdminSub") String numeroIdentificacionAdminSub);

    /**
     * Servicio que se encarga de exportar un archivo xlsx con el listado de los subsidios a anular por prescripción o por vencimiento
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param uri
     * @param response
     * @return archio xlsx
     */
    @POST
    @Path("exportarListado/subsidiosAnular")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response exportarListadoSubsidiosAnular(@Context UriInfo uri, @Context HttpServletResponse response, @NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("tipo") String tipo);

    /**
     * Servicio encargado de recibir la información respuesta de las transacciones de dispersion del subsidio monetario ejecutadas en Anibol
     * para actualizar la información en Genesys
     * HU-311-441
     *
     * @author <a href="mailto:camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param respuestaAnibol
     * 		  Arreglo con la información de las transacciones rechazadas por Anibol.
     */
    @POST
    @Path("/procesarResultadoDispersionSubsidioMonetario")
    public void procesarResultadoDispersionSubsidioMonetario(ResultadoDispersionAnibolDTO respuestaAnibol);

    @POST
    @Path("/procesarResultadoAnulacionSubsidioMonetario")
    public void procesarResultadoAnulacionSubsidioMonetario(ResultadoDispersionAnibolDTO respuestaAnibol);

    @POST
    @Path("/ejecucionAvisoPrescripcionSubsidio")
    public void ejecucionAvisoPrescripcionSubsidio();
    
    @POST
    @Path("registrar/cambioMedioDePagoArchivo")
    public List<Long> registrarCambioMedioDePagoSubsidioArchivo(@NotNull @QueryParam("idAdminSubsidio") Long idAdminSubsidio,RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO);

}
