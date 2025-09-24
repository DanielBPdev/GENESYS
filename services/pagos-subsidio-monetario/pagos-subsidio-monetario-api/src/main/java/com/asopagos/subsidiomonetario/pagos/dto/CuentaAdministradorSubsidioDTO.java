package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.MedioTarjeta;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoAbonoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de la cuenta del administrador de subsidio <br/>
 * <b>Cambio:</b> 03/03/2022 Se adicionan los campos de establecimientoCodigo, establecimientoNombre y fechaTransaccionConsumo <br/>
 * <b>Módulo:</b> Asopagos - HU-31-198<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class CuentaAdministradorSubsidioDTO implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -8739778805610614015L;

    /**
     * Identificador de la base de datos asociada a la cuenta del administrador del subsidio.
     */
    private Long idCuentaAdministradorSubsidio;

    /**
     * Indica la fecha y hora de creación del registro del abono
     */
    private Date fechaHoraCreacionRegistro;

    /**
     * Nombre de usuario que creao el registro del abono.
     */
    private String usuarioCreacionRegistro;

    /**
     * Tipo de transacción de la cuenta del administrador del subsidio.
     */
    private TipoTransaccionSubsidioMonetarioEnum tipoTransaccion;

    /**
     * Estado de la transacción de la cuenta del administrador del subsidio.
     */
    private EstadoTransaccionSubsidioEnum estadoTransaccion;

    /**
     * Origen de la transacción de la cuenta del administrador del subsidio.
     */
    private OrigenTransaccionEnum origenTransaccion;

    /**
     * Tipo de medio de pago relacionado con la cuenta del administrador del subsidio.
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Sumatoria del campo "valor original abonado" de cada uno
     * de los registros del detalle del subsidio asignado
     */
    private BigDecimal valorOriginalTransaccion;

    /**
     * Número de la tarjeta del administrador del subsidio
     * si el medio de pago tipo tarjeta.
     */
    private String numeroTarjetaAdminSubsidio;

    /**
     * Código del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String codigoBancoAdminSubsidio;

    /**
     * Nombre del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String nombreBancoAdminSubsidio;

    /**
     * Tipo de cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoCuentaEnum tipoCuentaAdminSubsidio;

    /**
     * Número de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String numeroCuentaAdminSubsidio;

    /**
     * Tipo de identificación del titular de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio;

    /**
     * Número de identificación del titular del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String numeroIdentificacionTitularCuentaAdminSubsidio;

    /**
     * Nombre del titular de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String nombreTitularCuentaAdminSubsidio;

    /**
     * variable que contiene el mismo valor del campo "valor original transacción"
     */
    private BigDecimal valorRealTransaccion;

    /**
     * Fecha y hora en la cual se realiza la transacción en la
     * cuenta del administrador del subsidio.
     */
    private Date fechaHoraTransaccion;

    /**
     * Nombre del usuario que aprobo los resultados de la liquidación
     */
    private String usuarioTransaccionLiquidacion;

    /**
     * Identificación de la cuenta del administrador del subsidio por si
     * esta nueva cuenta es creada a partir de otra por un proceso (Anulación, pago parcial)
     */
    private Long idTransaccionOriginal;

    /**
     * Identificador de la remisión de datos del tercero pagador de la cuenta
     * del administrador del subisdio
     */
    private String idRemisionDatosTerceroPagador;

    /**
     * Identificador de la transacción del tercero pagador en la cuenta
     * del administrador del subsidio
     */
    private String idTransaccionTerceroPagador;

    /**
     * Nombre del tercero pagador relacionado con la cuenta del administrador del subsidio.
     */
    private String nombreTerceroPagador;

    /**
     * Identificador de la cuenta del administrador de subsidio relacionada con
     * esta cuenta.
     */
    private Long idCuentaAdminSubsidioRelacionado;

    /**
     * Fecha y hora en la que se realizo la ultima moficaficación
     * al registro de la cuenta del administrador del subsidio.
     */
    private Date fechaHoraUltimaModificacion;

    /**
     * Nombre del usuario que realizo la ultima modificación
     * del registro de la cuenta del administrador del subsidio.
     */
    private String usuarioUltimaModificacion;

    /**
     * Identificador del administrador del subsidio relacionado con la cuenta del administrador del subsidio.
     */
    private Long idAdministradorSubsidio;

    /**
     * Identificador del sitio de pago relacionado con la cuenta del administrador del subsidio.
     */
    private Long idSitioDePago;

    /**
     * Identificador del sitio de cobro relacionado con la cuenta del administrador del subsidio.
     */
    private Long idSitioDeCobro;

    /**
     * Lista de detalles de subsidios asignados relacionados con la cuenta
     * del administrador del subsidio.
     */
    private List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidioAsignadoDTO;

    /**
     * Nombre completo del administrador del subsidio
     */
    private String nombresApellidosAdminSubsidio;

    /**
     * Estado del abono para la HU-31-199
     */
    private EstadoAbonoEnum estadoAbono;

    /**
     * Tipo de identificación del administrador de subsidio.
     */
    private TipoIdentificacionEnum tipoIdAdminSubsidio;

    /**
     * Número de identificación del administrador de subsidio
     */
    private String numeroIdAdminSubsidio;

    /**
     * Identificador principal del medio de pago relacionado a la cuenta del administrador del subsidio.
     */
    private Long idMedioDePago;

    /**
     * detalles de subsidios asignado relacionado con la cuenta del administrador del subsidio.
     */
    private DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO;

    /**
     * Nombre del sitio de pago (Departamento,Municipio)
     */
    private String nombreSitioPago;

    /**
     * Nombre del sitio de cobro (Departamento,Municipio)
     */
    private String nombreSitioCobro;

    /**
     * Nombre de la persona autorizada si se realiza un retiro con este criterio
     */
    private String nombrePersonaAutorizada;


    private Long idEmpleador;


    private Long idAfiliadoPrincipal;


    private Long idBeneficiarioDetalle;

    private Long idGrupoFamiliar;

    private EstadoTarjetaMultiserviciosEnum estadoTarjeta;

    private Long solicitudLiquidacionSubsidio;

    private Long idCuentaOriginal;

    /**
     * Sumatoria del campo valorRealTransaccion
     */
    private BigDecimal totalValorAbono;

    /**
     * Nombre del punto de cobro enviado por el medio de pago
     */
    private String idPuntoDeCobro;

    /**
     * Codigo del establecimiento donde se efectua el consumo (casNombreTerceroPagado)
     */
    private String establecimientoCodigo;

    /**
     * Nombre del establecimiento donde se efectua el consumo (estNombre)
     */
    private String establecimientoNombre;

    /**
     * Fecha Transaccion del consumo (racFechaTransaccion)
     */
    private String fechaTransaccionConsumo;

    private Boolean esFlujoTraslado;
    /**
     * Estado que tiene la cuenta del administrador del subsidio al momento de ser creado por la liquidación.
     */

    private EstadoTransaccionSubsidioEnum estadoLiquidacionSubsidio;

    /**
     * Identificador de condición de persona
     */

    private Long condicionPersonaAdmin;

    private String parametrosOutRegistroOperacion;

    // estado calculado de la transaccion de retiro sobre un abono
    private String estadoTransaccionRetiro;

    // id que representa el casid de un retiro, agregado para 
    // legibilidad de usuario frente webservice de susuerte
    private Long idTransaccionRetiro;

    private String fechaYHoraTransaccionRetiro;
    
    private String valorRealTransaccionRetiro;

    private String totalRegistro;


    /**
     * Constructor vacio
     */
    public CuentaAdministradorSubsidioDTO() {
    }

    /**
     * Constructor de la clase que se encarga de registrar la cuenta de administrador del subsidio con
     * su respectivo detalle subsidio asignado.
     *
     * @param cuentaAdministradorSubsidio
     *        entidad de la cuenta del administrador del subsidio
     * @param detalleSubsidioAsignado
     *        entidad del detalle de subsidio asignado.
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio,
                                          DetalleSubsidioAsignado detalleSubsidioAsignado) {

        //se instancia el dto de cuenta
        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setFechaHoraCreacionRegistro(cuentaAdministradorSubsidio.getFechaHoraCreacionRegistro());
        this.setUsuarioCreacionRegistro(cuentaAdministradorSubsidio.getUsuarioCreacionRegistro());
        this.setTipoTransaccion(cuentaAdministradorSubsidio.getTipoTransaccionSubsidio());
        this.setEstadoTransaccion(cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        this.setOrigenTransaccion(cuentaAdministradorSubsidio.getOrigenTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setIdMedioDePago(cuentaAdministradorSubsidio.getIdMedioDePago());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setUsuarioTransaccionLiquidacion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion());
        this.setIdTransaccionOriginal(cuentaAdministradorSubsidio.getIdTransaccionOriginal());
        this.setIdRemisionDatosTerceroPagador(cuentaAdministradorSubsidio.getIdRemisionDatosTerceroPagador());
        this.setIdTransaccionTerceroPagador(cuentaAdministradorSubsidio.getIdTransaccionTerceroPagador());
        this.setNombreTerceroPagador(cuentaAdministradorSubsidio.getNombreTerceroPagador());
        this.setIdCuentaAdminSubsidioRelacionado(cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        this.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidio.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(cuentaAdministradorSubsidio.getUsuarioUltimaModificacion());
        this.setIdEmpleador(cuentaAdministradorSubsidio.getIdEmpleador());
        this.setIdAfiliadoPrincipal(cuentaAdministradorSubsidio.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(cuentaAdministradorSubsidio.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(cuentaAdministradorSubsidio.getIdGrupoFamiliar());

        this.setIdAdministradorSubsidio(cuentaAdministradorSubsidio.getIdAdministradorSubsidio());
        this.setIdSitioDePago(cuentaAdministradorSubsidio.getIdSitioDePago());
        this.setIdSitioDeCobro(cuentaAdministradorSubsidio.getIdSitioDeCobro());
        this.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidio.getSolicitudLiquidacionSubsidio());
        this.setIdCuentaOriginal(cuentaAdministradorSubsidio.getIdCuentaOriginal());
        this.setIdPuntoDeCobro(cuentaAdministradorSubsidio.getIdPuntoDeCobro());


        //se instancia el dto de detalle
        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO(detalleSubsidioAsignado);

        List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();
        detalles.add(detalleSubsidioAsignadoDTO);
        this.setListaDetallesSubsidioAsignadoDTO(detalles);
    }

    /**
     * Metodo que convierte un DTO a un entity
     *
     * @return entity de cuenta de administrador de subsidio.
     */
    public CuentaAdministradorSubsidio convertToEntity() {

        CuentaAdministradorSubsidio cuentaAdministradorSubsidio = new CuentaAdministradorSubsidio();

        cuentaAdministradorSubsidio.setIdCuentaAdministradorSubsidio(this.getIdCuentaAdministradorSubsidio());
        cuentaAdministradorSubsidio.setFechaHoraCreacionRegistro(this.getFechaHoraCreacionRegistro() != null ? getFechaHoraCreacionRegistro() : new Date());
        cuentaAdministradorSubsidio.setUsuarioCreacionRegistro(this.getUsuarioCreacionRegistro());
        cuentaAdministradorSubsidio.setTipoTransaccionSubsidio(this.getTipoTransaccion());
        cuentaAdministradorSubsidio.setEstadoTransaccionSubsidio(this.getEstadoTransaccion());
        cuentaAdministradorSubsidio.setOrigenTransaccion(this.getOrigenTransaccion());
        cuentaAdministradorSubsidio.setMedioDePagoTransaccion(this.getMedioDePago());
        cuentaAdministradorSubsidio.setIdMedioDePago(this.getIdMedioDePago());
        cuentaAdministradorSubsidio.setNumeroTarjetaAdmonSubsidio(this.getNumeroTarjetaAdminSubsidio());
        cuentaAdministradorSubsidio.setCodigoBancoAdmonSubsidio(this.getCodigoBancoAdminSubsidio());
        cuentaAdministradorSubsidio.setNombreBancoAdmonSubsidio(this.getNombreBancoAdminSubsidio());
        cuentaAdministradorSubsidio.setTipoCuentaAdmonSubsidio(this.getTipoCuentaAdminSubsidio());
        cuentaAdministradorSubsidio.setNumeroCuentaAdmonSubsidio(this.getNumeroCuentaAdminSubsidio());
        cuentaAdministradorSubsidio.setTipoIdentificacionTitularCuentaAdmonSubsidio(this.getTipoIdentificacionTitularCuentaAdminSubsidio());
        cuentaAdministradorSubsidio
                .setNumeroIdentificacionTitularCuentaAdmonSubsidio(this.getNumeroIdentificacionTitularCuentaAdminSubsidio());
        cuentaAdministradorSubsidio.setNombreTitularCuentaAdmonSubsidio(this.getNombreTitularCuentaAdminSubsidio());
        cuentaAdministradorSubsidio.setFechaHoraTransaccion(this.getFechaHoraTransaccion());
        cuentaAdministradorSubsidio.setUsuarioTransaccion(this.getUsuarioTransaccionLiquidacion());
        cuentaAdministradorSubsidio.setValorOriginalTransaccion(this.getValorOriginalTransaccion());
        cuentaAdministradorSubsidio.setValorRealTransaccion(this.getValorRealTransaccion());
        cuentaAdministradorSubsidio.setIdTransaccionOriginal(this.getIdTransaccionOriginal());
        cuentaAdministradorSubsidio.setIdRemisionDatosTerceroPagador(this.getIdRemisionDatosTerceroPagador());
        cuentaAdministradorSubsidio.setIdTransaccionTerceroPagador(this.getIdTransaccionTerceroPagador());
        cuentaAdministradorSubsidio.setNombreTerceroPagador(this.getNombreTerceroPagador());
        cuentaAdministradorSubsidio.setIdCuentaAdmonSubsidioRelacionado(this.getIdCuentaAdminSubsidioRelacionado());
        cuentaAdministradorSubsidio.setFechaHoraUltimaModificacion(this.getFechaHoraUltimaModificacion());
        cuentaAdministradorSubsidio.setUsuarioUltimaModificacion(this.getUsuarioUltimaModificacion());

        cuentaAdministradorSubsidio.setIdEmpleador(this.getIdEmpleador());
        cuentaAdministradorSubsidio.setIdAfiliadoPrincipal(this.getIdAfiliadoPrincipal());
        cuentaAdministradorSubsidio.setIdBeneficiarioDetalle(this.getIdBeneficiarioDetalle());
        cuentaAdministradorSubsidio.setIdGrupoFamiliar(this.getIdGrupoFamiliar());

        cuentaAdministradorSubsidio.setIdAdministradorSubsidio(this.getIdAdministradorSubsidio());
        cuentaAdministradorSubsidio.setIdSitioDePago(this.getIdSitioDePago());
        cuentaAdministradorSubsidio.setIdSitioDeCobro(this.getIdSitioDeCobro());
        cuentaAdministradorSubsidio.setSolicitudLiquidacionSubsidio(this.getSolicitudLiquidacionSubsidio());
        cuentaAdministradorSubsidio.setIdCuentaOriginal(this.getIdCuentaOriginal());
        cuentaAdministradorSubsidio.setIdPuntoDeCobro(this.getIdPuntoDeCobro());

        return cuentaAdministradorSubsidio;
    }

    /**
     * Constructor que convierte una instancia de cuentaAdministradorDTO a partir de la entidad.
     * @param cuentaAdministradorSubsidio
     *        entidad de la cuenta.
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio) {

        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setFechaHoraCreacionRegistro(cuentaAdministradorSubsidio.getFechaHoraCreacionRegistro());
        this.setUsuarioCreacionRegistro(cuentaAdministradorSubsidio.getUsuarioCreacionRegistro());
        this.setTipoTransaccion(cuentaAdministradorSubsidio.getTipoTransaccionSubsidio());
        this.setEstadoTransaccion(cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        this.setOrigenTransaccion(cuentaAdministradorSubsidio.getOrigenTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setUsuarioTransaccionLiquidacion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion().abs());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion().abs());
        this.setIdTransaccionOriginal(cuentaAdministradorSubsidio.getIdTransaccionOriginal());
        this.setIdRemisionDatosTerceroPagador(cuentaAdministradorSubsidio.getIdRemisionDatosTerceroPagador());
        this.setIdTransaccionTerceroPagador(cuentaAdministradorSubsidio.getIdTransaccionTerceroPagador());
        this.setNombreTerceroPagador(cuentaAdministradorSubsidio.getNombreTerceroPagador());
        this.setIdCuentaAdminSubsidioRelacionado(cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        this.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidio.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(cuentaAdministradorSubsidio.getUsuarioUltimaModificacion());
        this.setIdMedioDePago(cuentaAdministradorSubsidio.getIdMedioDePago());
        this.setIdAdministradorSubsidio(cuentaAdministradorSubsidio.getIdAdministradorSubsidio());
        this.setIdSitioDePago(cuentaAdministradorSubsidio.getIdSitioDePago());
        this.setIdSitioDeCobro(cuentaAdministradorSubsidio.getIdSitioDeCobro());
        this.setIdEmpleador(cuentaAdministradorSubsidio.getIdEmpleador());
        this.setIdAfiliadoPrincipal(cuentaAdministradorSubsidio.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(cuentaAdministradorSubsidio.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(cuentaAdministradorSubsidio.getIdGrupoFamiliar());
        this.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidio.getSolicitudLiquidacionSubsidio());
        this.setIdCuentaOriginal(cuentaAdministradorSubsidio.getIdCuentaOriginal());
        this.setIdPuntoDeCobro(cuentaAdministradorSubsidio.getIdPuntoDeCobro());

    }

    /**
     * Constructor que convierte una instancia de cuentaAdministradorDTO a partir de la entidad.
     * @param cuentaAdministradorSubsidio
     *        entidad de la cuenta.
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio, Persona persona) {

        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setFechaHoraCreacionRegistro(cuentaAdministradorSubsidio.getFechaHoraCreacionRegistro());
        this.setUsuarioCreacionRegistro(cuentaAdministradorSubsidio.getUsuarioCreacionRegistro());
        this.setTipoTransaccion(cuentaAdministradorSubsidio.getTipoTransaccionSubsidio());
        this.setEstadoTransaccion(cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        this.setOrigenTransaccion(cuentaAdministradorSubsidio.getOrigenTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setUsuarioTransaccionLiquidacion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion());
        this.setIdTransaccionOriginal(cuentaAdministradorSubsidio.getIdTransaccionOriginal());
        this.setIdRemisionDatosTerceroPagador(cuentaAdministradorSubsidio.getIdRemisionDatosTerceroPagador());
        this.setIdTransaccionTerceroPagador(cuentaAdministradorSubsidio.getIdTransaccionTerceroPagador());
        this.setNombreTerceroPagador(cuentaAdministradorSubsidio.getNombreTerceroPagador());
        this.setIdCuentaAdminSubsidioRelacionado(cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        this.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidio.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(cuentaAdministradorSubsidio.getUsuarioUltimaModificacion());
        this.setIdMedioDePago(cuentaAdministradorSubsidio.getIdMedioDePago());
        this.setIdAdministradorSubsidio(cuentaAdministradorSubsidio.getIdAdministradorSubsidio());
        this.setIdSitioDePago(cuentaAdministradorSubsidio.getIdSitioDePago());
        this.setIdSitioDeCobro(cuentaAdministradorSubsidio.getIdSitioDeCobro());
        this.setIdEmpleador(cuentaAdministradorSubsidio.getIdEmpleador());
        this.setIdAfiliadoPrincipal(cuentaAdministradorSubsidio.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(cuentaAdministradorSubsidio.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(cuentaAdministradorSubsidio.getIdGrupoFamiliar());
        this.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidio.getSolicitudLiquidacionSubsidio());
        this.setIdCuentaOriginal(cuentaAdministradorSubsidio.getIdCuentaOriginal());
        this.setIdPuntoDeCobro(cuentaAdministradorSubsidio.getIdPuntoDeCobro());

        String primerNombre = persona.getPrimerNombre();
        String nombres = null;
        if (persona.getSegundoNombre() != null)
            nombres = primerNombre.concat(" " + persona.getSegundoNombre());
        else
            nombres = primerNombre;
        String primerApellido = persona.getPrimerApellido();
        String apellidos = null;
        if (persona.getSegundoApellido() != null)
            apellidos = primerApellido.concat(" " + persona.getSegundoApellido());
        else
            apellidos = primerApellido;
        String nombreCompleto = nombres.concat(" " + apellidos);

        this.setNombresApellidosAdminSubsidio(nombreCompleto);

        this.setTipoIdAdminSubsidio(persona.getTipoIdentificacion());
        this.setNumeroIdAdminSubsidio(persona.getNumeroIdentificacion());
    }

    /**
     * Constructor con los datos de CuentaAdministradorSubsidio, Persona y Tarjeta
     * @param cuentaAdministradorSubsidio
     * @param persona
     * @param tarjeta
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio, Persona persona, MedioTarjeta tarjeta) {
        this(cuentaAdministradorSubsidio, persona);
        this.estadoTarjeta = tarjeta.getEstadoTarjetaMultiservicios();
    }

    /**
     * Constructor con los datos de CuentaAdministradorSubsidio, Persona y estadoTarjeta
     * @param cuentaAdministradorSubsidio
     * @param persona
     * @param estadoTarjeta
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio, Persona persona, String estadoTarjeta) {
        this(cuentaAdministradorSubsidio, persona);
        this.estadoTarjeta = estadoTarjeta != null ? EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta) : null;
    }

    /**
     * Constructor con los datos enviados por parametro desde JPA para CuentaAdministradorSubsidio, Persona y Tarjeta
     * @param idCuentaAdministradorSubsidio
     * @param fechaHoraCreacionRegistro
     * @param usuarioCreacionRegistro
     * @param tipoTransaccion
     * @param estadoTransaccion
     * @param origenTransaccion
     * @param medioDePago
     * @param numeroTarjetaAdminSubsidio
     * @param codigoBancoAdminSubsidio
     * @param nombreBancoAdmonSubsidio
     * @param tipoCuentaAdminSubsidio
     * @param numeroCuentaAdminSubsidio
     * @param tipoIdentificacionTitularCuentaAdminSubsidio
     * @param numeroIdentificacionTitularCuentaAdminSubsidio
     * @param nombreTitularCuentaAdminSubsidio
     * @param fechaHoraTransaccion
     * @param usuarioTransaccionLiquidacion
     * @param valorOriginalTransaccion
     * @param valorRealTransaccion
     * @param idTransaccionOriginal
     * @param idRemisionDatosTerceroPagador
     * @param idTransaccionTerceroPagador
     * @param nombreTerceroPagador
     * @param idCuentaAdminSubsidioRelacionado
     * @param fechaHoraUltimaModificacion
     * @param usuarioUltimaModificacion
     * @param idMedioDePago
     * @param idAdministradorSubsidio
     * @param idSitioDePago
     * @param idSitioDeCobro
     * @param idEmpleador
     * @param idAfiliadoPrincipal
     * @param idBeneficiarioDetalle
     * @param idGrupoFamiliar
     * @param solicitudLiquidacionSubsidio
     * @param idCuentaOriginal
     * @param idPuntoDeCobro
     * @param personaPrimerNombre
     * @param personaSegundoNombre
     * @param personaPrimerApellido
     * @param personaSegundoApellido
     * @param tipoIdAdminSubsidio
     * @param numeroIdAdminSubsidio
     * @param estadoTarjeta
     */
    public CuentaAdministradorSubsidioDTO(Long idCuentaAdministradorSubsidio, Date fechaHoraCreacionRegistro, String usuarioCreacionRegistro, TipoTransaccionSubsidioMonetarioEnum tipoTransaccion,
                                          EstadoTransaccionSubsidioEnum estadoTransaccion, OrigenTransaccionEnum origenTransaccion, TipoMedioDePagoEnum medioDePago,String numeroTarjetaAdminSubsidio,
                                          String codigoBancoAdminSubsidio, String nombreBancoAdmonSubsidio, TipoCuentaEnum tipoCuentaAdminSubsidio, String numeroCuentaAdminSubsidio, TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio,
                                          String numeroIdentificacionTitularCuentaAdminSubsidio, String nombreTitularCuentaAdminSubsidio, Date fechaHoraTransaccion, String usuarioTransaccionLiquidacion, BigDecimal valorOriginalTransaccion,
                                          BigDecimal valorRealTransaccion, Long idTransaccionOriginal, String idRemisionDatosTerceroPagador, String idTransaccionTerceroPagador, String nombreTerceroPagador,
                                          Long idCuentaAdminSubsidioRelacionado, Date fechaHoraUltimaModificacion, String usuarioUltimaModificacion, Long idMedioDePago, Long idAdministradorSubsidio, Long idSitioDePago,
                                          Long idSitioDeCobro, Long idEmpleador, Long idAfiliadoPrincipal, Long idBeneficiarioDetalle, Long idGrupoFamiliar, Long solicitudLiquidacionSubsidio, Long idCuentaOriginal,
                                          String idPuntoDeCobro, String personaPrimerNombre, String personaSegundoNombre, String personaPrimerApellido, String personaSegundoApellido, TipoIdentificacionEnum tipoIdAdminSubsidio, String numeroIdAdminSubsidio, String estadoTarjeta) {
        this.setIdCuentaAdministradorSubsidio(idCuentaAdministradorSubsidio);
        this.setFechaHoraCreacionRegistro(fechaHoraCreacionRegistro);
        this.setUsuarioCreacionRegistro(usuarioCreacionRegistro);
        this.setTipoTransaccion(tipoTransaccion);
        this.setEstadoTransaccion(estadoTransaccion);
        this.setOrigenTransaccion(origenTransaccion);
        this.setMedioDePago(medioDePago);
        this.setNumeroTarjetaAdminSubsidio(numeroTarjetaAdminSubsidio);
        this.setCodigoBancoAdminSubsidio(codigoBancoAdminSubsidio);
        this.setNombreBancoAdminSubsidio(nombreBancoAdmonSubsidio);
        this.setTipoCuentaAdminSubsidio(tipoCuentaAdminSubsidio);
        this.setNumeroCuentaAdminSubsidio(numeroCuentaAdminSubsidio);
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(tipoIdentificacionTitularCuentaAdminSubsidio);
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(numeroIdentificacionTitularCuentaAdminSubsidio);
        this.setNombreTitularCuentaAdminSubsidio(nombreTitularCuentaAdminSubsidio);
        this.setFechaHoraTransaccion(fechaHoraTransaccion);
        this.setUsuarioTransaccionLiquidacion(usuarioTransaccionLiquidacion);
        this.setValorOriginalTransaccion(valorOriginalTransaccion);
        this.setValorRealTransaccion(valorRealTransaccion);
        this.setIdTransaccionOriginal(idTransaccionOriginal);
        this.setIdRemisionDatosTerceroPagador(idRemisionDatosTerceroPagador);
        this.setIdTransaccionTerceroPagador(idTransaccionTerceroPagador);
        this.setNombreTerceroPagador(nombreTerceroPagador);
        this.setIdCuentaAdminSubsidioRelacionado(idCuentaAdminSubsidioRelacionado);
        this.setFechaHoraUltimaModificacion(fechaHoraUltimaModificacion);
        this.setUsuarioUltimaModificacion(usuarioUltimaModificacion);
        this.setIdMedioDePago(idMedioDePago);
        this.setIdAdministradorSubsidio(idAdministradorSubsidio);
        this.setIdSitioDePago(idSitioDePago);
        this.setIdSitioDeCobro(idSitioDeCobro);
        this.setIdEmpleador(idEmpleador);
        this.setIdAfiliadoPrincipal(idAfiliadoPrincipal);
        this.setIdBeneficiarioDetalle(idBeneficiarioDetalle);
        this.setIdGrupoFamiliar(idGrupoFamiliar);
        this.setSolicitudLiquidacionSubsidio(solicitudLiquidacionSubsidio);
        this.setIdCuentaOriginal(idCuentaOriginal);
        this.setIdPuntoDeCobro(idPuntoDeCobro);

        String primerNombre = personaPrimerNombre;
        String nombres = null;
        if (personaSegundoNombre != null)
            nombres = primerNombre.concat(" " + personaSegundoNombre);
        else
            nombres = primerNombre;
        String primerApellido = personaPrimerApellido;
        String apellidos = null;
        if (personaSegundoApellido != null)
            apellidos = primerApellido.concat(" " + personaSegundoApellido);
        else
            apellidos = primerApellido;
        String nombreCompleto = nombres.concat(" " + apellidos);

        this.setNombresApellidosAdminSubsidio(nombreCompleto);

        this.setTipoIdAdminSubsidio(tipoIdAdminSubsidio);
        this.setNumeroIdAdminSubsidio(numeroIdAdminSubsidio);

        this.estadoTarjeta = estadoTarjeta != null ? EstadoTarjetaMultiserviciosEnum.valueOf(estadoTarjeta) : null;
    }

    /**
     * Constructor de la clase que se encarga de registrar la cuenta de administrador del subsidio con
     * su respectivo detalle subsidio asignado.
     *
     * @param cuentaAdministradorSubsidio
     *        <code>CuentaAdministradorSubsidio</code>
     *        Entidad de la cuenta del administrador del subsidio.
     * @param detalleSubsidioAsignado
     *        <code>DetalleSubsidioAsignado</code>
     *        Entidad del detalle de subsidio asignado.
     * @param empleador
     *        <code>Empleador</code>
     *        Representa un empleador afiliado a la caja de compensación.
     * @param afiliado
     *        <code>Afiliado</code>
     *        Representa una persona afiliada a la caja de compensación.
     * @param grupoFamiliar
     *        <code>GrupoFamiliar</code>
     *        Representa los grupos familiares de un afiliado.
     * @param beneficiario
     *        <code>Beneficiario</code>
     *        Representa la información del beneficiario.
     * @param administradorSubsidio
     *        <code>Persona</code>
     *        Representa una administrador de subsidio y contiene sus datos básicos.
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio,
                                          DetalleSubsidioAsignado detalleSubsidioAsignado, Empleador empleador, Afiliado afiliado, GrupoFamiliar grupoFamiliar,
                                          Beneficiario beneficiario, Persona administradorSubsidio) {

        //se instancia el dto de cuenta
        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setFechaHoraCreacionRegistro(cuentaAdministradorSubsidio.getFechaHoraCreacionRegistro());
        this.setUsuarioCreacionRegistro(cuentaAdministradorSubsidio.getUsuarioCreacionRegistro());
        this.setTipoTransaccion(cuentaAdministradorSubsidio.getTipoTransaccionSubsidio());
        this.setEstadoTransaccion(cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        this.setOrigenTransaccion(cuentaAdministradorSubsidio.getOrigenTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setIdMedioDePago(cuentaAdministradorSubsidio.getIdMedioDePago());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setUsuarioTransaccionLiquidacion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion());
        this.setIdTransaccionOriginal(cuentaAdministradorSubsidio.getIdTransaccionOriginal());
        this.setIdRemisionDatosTerceroPagador(cuentaAdministradorSubsidio.getIdRemisionDatosTerceroPagador());
        this.setIdTransaccionTerceroPagador(cuentaAdministradorSubsidio.getIdTransaccionTerceroPagador());
        this.setNombreTerceroPagador(cuentaAdministradorSubsidio.getNombreTerceroPagador());
        this.setIdCuentaAdminSubsidioRelacionado(cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        this.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidio.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(cuentaAdministradorSubsidio.getUsuarioUltimaModificacion());
        this.setIdEmpleador(cuentaAdministradorSubsidio.getIdEmpleador());
        this.setIdAfiliadoPrincipal(cuentaAdministradorSubsidio.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(cuentaAdministradorSubsidio.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(cuentaAdministradorSubsidio.getIdGrupoFamiliar());
        this.setIdAdministradorSubsidio(cuentaAdministradorSubsidio.getIdAdministradorSubsidio());
        this.setIdSitioDePago(cuentaAdministradorSubsidio.getIdSitioDePago());
        this.setIdSitioDeCobro(cuentaAdministradorSubsidio.getIdSitioDeCobro());
        this.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidio.getSolicitudLiquidacionSubsidio());
        this.setIdCuentaOriginal(cuentaAdministradorSubsidio.getIdCuentaOriginal());
        this.setIdPuntoDeCobro(cuentaAdministradorSubsidio.getIdPuntoDeCobro());

        //se instancia el dto de detalle
        DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = new DetalleSubsidioAsignadoDTO(detalleSubsidioAsignado, empleador, afiliado,
                grupoFamiliar, beneficiario, administradorSubsidio, null);

        List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();
        detalles.add(detalleSubsidioAsignadoDTO);
        this.setListaDetallesSubsidioAsignadoDTO(detalles);

        //Datos basicos del administrador de subsidio relacionado
        String primerNombre = administradorSubsidio.getPrimerNombre();
        String nombres = null;
        if (administradorSubsidio.getSegundoNombre() != null)
            nombres = primerNombre.concat(" " + administradorSubsidio.getSegundoNombre());
        else
            nombres = primerNombre;
        String primerApellido = administradorSubsidio.getPrimerApellido();
        String apellidos = null;
        if (administradorSubsidio.getSegundoApellido() != null)
            apellidos = primerApellido.concat(" " + administradorSubsidio.getSegundoApellido());
        else
            apellidos = primerApellido;
        String nombreCompleto = nombres.concat(" " + apellidos);

        this.setNombresApellidosAdminSubsidio(nombreCompleto);

        this.setTipoIdAdminSubsidio(administradorSubsidio.getTipoIdentificacion());
        this.setNumeroIdAdminSubsidio(administradorSubsidio.getNumeroIdentificacion());
    }

    /**
     * Constructor que convierte una instancia de cuentaAdministradorDTO a partir de la entidad.
     * @param cuentaAdministradorSubsidio
     *        entidad de la cuenta.
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio, Persona persona, Date periodoLiquidado) {

        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setFechaHoraCreacionRegistro(cuentaAdministradorSubsidio.getFechaHoraCreacionRegistro());
        this.setUsuarioCreacionRegistro(cuentaAdministradorSubsidio.getUsuarioCreacionRegistro());
        this.setTipoTransaccion(cuentaAdministradorSubsidio.getTipoTransaccionSubsidio());
        this.setEstadoTransaccion(cuentaAdministradorSubsidio.getEstadoTransaccionSubsidio());
        this.setOrigenTransaccion(cuentaAdministradorSubsidio.getOrigenTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(
                cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setUsuarioTransaccionLiquidacion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion());
        this.setIdTransaccionOriginal(cuentaAdministradorSubsidio.getIdTransaccionOriginal());
        this.setIdRemisionDatosTerceroPagador(cuentaAdministradorSubsidio.getIdRemisionDatosTerceroPagador());
        this.setIdTransaccionTerceroPagador(cuentaAdministradorSubsidio.getIdTransaccionTerceroPagador());
        this.setNombreTerceroPagador(cuentaAdministradorSubsidio.getNombreTerceroPagador());
        this.setIdCuentaAdminSubsidioRelacionado(cuentaAdministradorSubsidio.getIdCuentaAdmonSubsidioRelacionado());
        this.setFechaHoraUltimaModificacion(cuentaAdministradorSubsidio.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(cuentaAdministradorSubsidio.getUsuarioUltimaModificacion());
        this.setIdMedioDePago(cuentaAdministradorSubsidio.getIdMedioDePago());
        this.setIdAdministradorSubsidio(cuentaAdministradorSubsidio.getIdAdministradorSubsidio());
        this.setIdSitioDePago(cuentaAdministradorSubsidio.getIdSitioDePago());
        this.setIdSitioDeCobro(cuentaAdministradorSubsidio.getIdSitioDeCobro());
        this.setIdEmpleador(cuentaAdministradorSubsidio.getIdEmpleador());
        this.setIdAfiliadoPrincipal(cuentaAdministradorSubsidio.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(cuentaAdministradorSubsidio.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(cuentaAdministradorSubsidio.getIdGrupoFamiliar());
        this.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidio.getSolicitudLiquidacionSubsidio());
        this.setIdCuentaOriginal(cuentaAdministradorSubsidio.getIdCuentaOriginal());
        this.setIdPuntoDeCobro(cuentaAdministradorSubsidio.getIdPuntoDeCobro());
        this.setEstadoLiquidacionSubsidio(cuentaAdministradorSubsidio.getEstadoLiquidacionSubsidio());
        this.setCondicionPersonaAdmin(cuentaAdministradorSubsidio.getCondicionPersonaAdmin());

        String primerNombre = persona.getPrimerNombre();
        String nombres = null;
        if (persona.getSegundoNombre() != null)
            nombres = primerNombre.concat(" " + persona.getSegundoNombre());
        else
            nombres = primerNombre;
        String primerApellido = persona.getPrimerApellido();
        String apellidos = null;
        if (persona.getSegundoApellido() != null)
            apellidos = primerApellido.concat(" " + persona.getSegundoApellido());
        else
            apellidos = primerApellido;
        String nombreCompleto = nombres.concat(" " + apellidos);

        this.setNombresApellidosAdminSubsidio(nombreCompleto);

        this.setTipoIdAdminSubsidio(persona.getTipoIdentificacion());
        this.setNumeroIdAdminSubsidio(persona.getNumeroIdentificacion());
    }

    /**
     *
     */
    public CuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio, Persona persona, Municipio municipio, Departamento departamento) {
        this(cuentaAdministradorSubsidio, persona);
        this.nombreSitioCobro = municipio.getNombre() + ", " + departamento.getNombre();
    }

    /**
     * Metodo que permite clonar un objeto
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public CuentaAdministradorSubsidioDTO clone() {
        CuentaAdministradorSubsidioDTO obj = null;
        try {
            obj = (CuentaAdministradorSubsidioDTO) super.clone();
        } catch (CloneNotSupportedException ex) {

        }
        return obj;
    }


    public Long getIdEmpleador() {
        return idEmpleador;
    }

    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    public Long getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }

    public void setIdAfiliadoPrincipal(Long idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }

    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }


    /**
     * @return the idCuentaAdministradorSubsidio
     */
    public Long getIdCuentaAdministradorSubsidio() {
        return idCuentaAdministradorSubsidio;
    }

    /**
     * @param idCuentaAdministradorSubsidio
     *        the idCuentaAdministradorSubsidio to set
     */
    public void setIdCuentaAdministradorSubsidio(Long idCuentaAdministradorSubsidio) {
        this.idCuentaAdministradorSubsidio = idCuentaAdministradorSubsidio;
    }

    /**
     * @return the fechaHoraCreacionRegistro
     */
    public Date getFechaHoraCreacionRegistro() {
        return fechaHoraCreacionRegistro;
    }

    /**
     * @param fechaHoraCreacionRegistro
     *        the fechaHoraCreacionRegistro to set
     */
    public void setFechaHoraCreacionRegistro(Date fechaHoraCreacionRegistro) {
        this.fechaHoraCreacionRegistro = fechaHoraCreacionRegistro;
    }

    /**
     * @return the usuarioCreacionRegistro
     */
    public String getUsuarioCreacionRegistro() {
        return usuarioCreacionRegistro;
    }

    /**
     * @param usuarioCreacionRegistro
     *        the usuarioCreacionRegistro to set
     */
    public void setUsuarioCreacionRegistro(String usuarioCreacionRegistro) {
        this.usuarioCreacionRegistro = usuarioCreacionRegistro;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionSubsidioMonetarioEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the estadoTransaccion
     */
    public EstadoTransaccionSubsidioEnum getEstadoTransaccion() {
        return estadoTransaccion;
    }

    /**
     * @param estadoTransaccion
     *        the estadoTransaccion to set
     */
    public void setEstadoTransaccion(EstadoTransaccionSubsidioEnum estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    /**
     * @return the origenTransaccion
     */
    public OrigenTransaccionEnum getOrigenTransaccion() {
        return origenTransaccion;
    }

    /**
     * @param origenTransaccion
     *        the origenTransaccion to set
     */
    public void setOrigenTransaccion(OrigenTransaccionEnum origenTransaccion) {
        this.origenTransaccion = origenTransaccion;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago
     *        the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the valorOriginalTransaccion
     */
    public BigDecimal getValorOriginalTransaccion() {
        return valorOriginalTransaccion;
    }

    /**
     * @param valorOriginalTransaccion
     *        the valorOriginalTransaccion to set
     */
    public void setValorOriginalTransaccion(BigDecimal valorOriginalTransaccion) {
        this.valorOriginalTransaccion = valorOriginalTransaccion;
    }

    /**
     * @return the numeroTarjetaAdminSubsidio
     */
    public String getNumeroTarjetaAdminSubsidio() {
        return numeroTarjetaAdminSubsidio;
    }

    /**
     * @param numeroTarjetaAdminSubsidio
     *        the numeroTarjetaAdminSubsidio to set
     */
    public void setNumeroTarjetaAdminSubsidio(String numeroTarjetaAdminSubsidio) {
        this.numeroTarjetaAdminSubsidio = numeroTarjetaAdminSubsidio;
    }

    /**
     * @return the codigoBancoAdminSubsidio
     */
    public String getCodigoBancoAdminSubsidio() {
        return codigoBancoAdminSubsidio;
    }

    /**
     * @param codigoBancoAdminSubsidio
     *        the codigoBancoAdminSubsidio to set
     */
    public void setCodigoBancoAdminSubsidio(String codigoBancoAdminSubsidio) {
        this.codigoBancoAdminSubsidio = codigoBancoAdminSubsidio;
    }

    /**
     * @return the nombreBancoAdmonSubsidio
     */
    public String getNombreBancoAdminSubsidio() {
        return nombreBancoAdminSubsidio;
    }

    /**
     * @param nombreBancoAdmonSubsidio
     *        the nombreBancoAdmonSubsidio to set
     */
    public void setNombreBancoAdminSubsidio(String nombreBancoAdmonSubsidio) {
        this.nombreBancoAdminSubsidio = nombreBancoAdmonSubsidio;
    }

    /**
     * @return the tipoCuentaAdminSubsidio
     */
    public TipoCuentaEnum getTipoCuentaAdminSubsidio() {
        return tipoCuentaAdminSubsidio;
    }

    /**
     * @param tipoCuentaAdminSubsidio
     *        the tipoCuentaAdminSubsidio to set
     */
    public void setTipoCuentaAdminSubsidio(TipoCuentaEnum tipoCuentaAdminSubsidio) {
        this.tipoCuentaAdminSubsidio = tipoCuentaAdminSubsidio;
    }

    /**
     * @return the numeroCuentaAdminSubsidio
     */
    public String getNumeroCuentaAdminSubsidio() {
        return numeroCuentaAdminSubsidio;
    }

    /**
     * @param numeroCuentaAdminSubsidio
     *        the numeroCuentaAdminSubsidio to set
     */
    public void setNumeroCuentaAdminSubsidio(String numeroCuentaAdminSubsidio) {
        this.numeroCuentaAdminSubsidio = numeroCuentaAdminSubsidio;
    }

    /**
     * @return the tipoIdentificacionTitularCuentaAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdentificacionTitularCuentaAdminSubsidio() {
        return tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @param tipoIdentificacionTitularCuentaAdminSubsidio
     *        the tipoIdentificacionTitularCuentaAdminSubsidio to set
     */
    public void setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio) {
        this.tipoIdentificacionTitularCuentaAdminSubsidio = tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @return the numeroIdentificacionTitularCuentaAdminSubsidio
     */
    public String getNumeroIdentificacionTitularCuentaAdminSubsidio() {
        return numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @param numeroIdentificacionTitularCuentaAdminSubsidio
     *        the numeroIdentificacionTitularCuentaAdminSubsidio to set
     */
    public void setNumeroIdentificacionTitularCuentaAdminSubsidio(String numeroIdentificacionTitularCuentaAdminSubsidio) {
        this.numeroIdentificacionTitularCuentaAdminSubsidio = numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @return the nombreTitularCuentaAdminSubsidio
     */
    public String getNombreTitularCuentaAdminSubsidio() {
        return nombreTitularCuentaAdminSubsidio;
    }

    /**
     * @param nombreTitularCuentaAdminSubsidio
     *        the nombreTitularCuentaAdminSubsidio to set
     */
    public void setNombreTitularCuentaAdminSubsidio(String nombreTitularCuentaAdminSubsidio) {
        this.nombreTitularCuentaAdminSubsidio = nombreTitularCuentaAdminSubsidio;
    }

    /**
     * @return the valorRealTransaccion
     */
    public BigDecimal getValorRealTransaccion() {
        return valorRealTransaccion;
    }

    /**
     * @param valorRealTransaccion
     *        the valorRealTransaccion to set
     */
    public void setValorRealTransaccion(BigDecimal valorRealTransaccion) {
        this.valorRealTransaccion = valorRealTransaccion;
    }

    /**
     * @return the fechaHoraTransaccion
     */
    public Date getFechaHoraTransaccion() {
        return fechaHoraTransaccion;
    }

    /**
     * @param fechaHoraTransaccion
     *        the fechaHoraTransaccion to set
     */
    public void setFechaHoraTransaccion(Date fechaHoraTransaccion) {
        this.fechaHoraTransaccion = fechaHoraTransaccion;
    }

    /**
     * @return the usuarioTransaccionLiquidacion
     */
    public String getUsuarioTransaccionLiquidacion() {
        return usuarioTransaccionLiquidacion;
    }

    /**
     * @param usuarioTransaccionLiquidacion
     *        the usuarioTransaccionLiquidacion to set
     */
    public void setUsuarioTransaccionLiquidacion(String usuarioTransaccionLiquidacion) {
        this.usuarioTransaccionLiquidacion = usuarioTransaccionLiquidacion;
    }

    /**
     * @return the idTransaccionOriginal
     */
    public Long getIdTransaccionOriginal() {
        return idTransaccionOriginal;
    }

    /**
     * @param idTransaccionOriginal
     *        the idTransaccionOriginal to set
     */
    public void setIdTransaccionOriginal(Long idTransaccionOriginal) {
        this.idTransaccionOriginal = idTransaccionOriginal;
    }

    /**
     * @return the idRemisionDatosTerceroPagador
     */
    public String getIdRemisionDatosTerceroPagador() {
        return idRemisionDatosTerceroPagador;
    }

    /**
     * @param idRemisionDatosTerceroPagador
     *        the idRemisionDatosTerceroPagador to set
     */
    public void setIdRemisionDatosTerceroPagador(String idRemisionDatosTerceroPagador) {
        this.idRemisionDatosTerceroPagador = idRemisionDatosTerceroPagador;
    }

    /**
     * @return the idTransaccionTerceroPagador
     */
    public String getIdTransaccionTerceroPagador() {
        return idTransaccionTerceroPagador;
    }

    /**
     * @param idTransaccionTerceroPagador
     *        the idTransaccionTerceroPagador to set
     */
    public void setIdTransaccionTerceroPagador(String idTransaccionTerceroPagador) {
        this.idTransaccionTerceroPagador = idTransaccionTerceroPagador;
    }

    /**
     * @return the nombreTerceroPagador
     */
    public String getNombreTerceroPagador() {
        return nombreTerceroPagador;
    }

    /**
     * @param nombreTerceroPagador
     *        the nombreTerceroPagador to set
     */
    public void setNombreTerceroPagador(String nombreTerceroPagador) {
        this.nombreTerceroPagador = nombreTerceroPagador;
    }

    /**
     * @return the idCuentaAdminSubsidioRelacionado
     */
    public Long getIdCuentaAdminSubsidioRelacionado() {
        return idCuentaAdminSubsidioRelacionado;
    }

    /**
     * @param idCuentaAdminSubsidioRelacionado
     *        the idCuentaAdminSubsidioRelacionado to set
     */
    public void setIdCuentaAdminSubsidioRelacionado(Long idCuentaAdminSubsidioRelacionado) {
        this.idCuentaAdminSubsidioRelacionado = idCuentaAdminSubsidioRelacionado;
    }

    /**
     * @return the fechaHoraUltimaModificacion
     */
    public Date getFechaHoraUltimaModificacion() {
        return fechaHoraUltimaModificacion;
    }

    /**
     * @param fechaHoraUltimaModificacion
     *        the fechaHoraUltimaModificacion to set
     */
    public void setFechaHoraUltimaModificacion(Date fechaHoraUltimaModificacion) {
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    /**
     * @return the usuarioUltimaModificacion
     */
    public String getUsuarioUltimaModificacion() {
        return usuarioUltimaModificacion;
    }

    /**
     * @param usuarioUltimaModificacion
     *        the usuarioUltimaModificacion to set
     */
    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    /**
     * @return the idAdministradorSubsidio
     */
    public Long getIdAdministradorSubsidio() {
        return idAdministradorSubsidio;
    }

    /**
     * @param idAdministradorSubsidio
     *        the idAdministradorSubsidio to set
     */
    public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
        this.idAdministradorSubsidio = idAdministradorSubsidio;
    }

    /**
     * @return the idSitioDePago
     */
    public Long getIdSitioDePago() {
        return idSitioDePago;
    }

    /**
     * @param idSitioDePago
     *        the idSitioDePago to set
     */
    public void setIdSitioDePago(Long idSitioDePago) {
        this.idSitioDePago = idSitioDePago;
    }

    /**
     * @return the idSitioDeCobro
     */
    public Long getIdSitioDeCobro() {
        return idSitioDeCobro;
    }

    /**
     * @param idSitioDeCobro
     *        the idSitioDeCobro to set
     */
    public void setIdSitioDeCobro(Long idSitioDeCobro) {
        this.idSitioDeCobro = idSitioDeCobro;
    }

    /**
     * @return the listaDetallesSubsidioAsignadoDTO
     */
    public List<DetalleSubsidioAsignadoDTO> getListaDetallesSubsidioAsignadoDTO() {
        return listaDetallesSubsidioAsignadoDTO;
    }

    /**
     * @param listaDetallesSubsidioAsignadoDTO
     *        the listaDetallesSubsidioAsignadoDTO to set
     */
    public void setListaDetallesSubsidioAsignadoDTO(List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidioAsignadoDTO) {
        this.listaDetallesSubsidioAsignadoDTO = listaDetallesSubsidioAsignadoDTO;
    }

    /**
     * @return the nombresApellidosAdminSubsidio
     */
    public String getNombresApellidosAdminSubsidio() {
        return nombresApellidosAdminSubsidio;
    }

    /**
     * @param nombresApellidosAdminSubsidio
     *        the nombresApellidosAdminSubsidio to set
     */
    public void setNombresApellidosAdminSubsidio(String nombresApellidosAdminSubsidio) {
        this.nombresApellidosAdminSubsidio = nombresApellidosAdminSubsidio;
    }

    /**
     * @return the estadoAbono
     */
    public EstadoAbonoEnum getEstadoAbono() {
        return estadoAbono;
    }

    /**
     * @param estadoAbono
     *        the estadoAbono to set
     */
    public void setEstadoAbono(EstadoAbonoEnum estadoAbono) {
        this.estadoAbono = estadoAbono;
    }

    /**
     * @return the tipoIdAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdAdminSubsidio() {
        return tipoIdAdminSubsidio;
    }

    /**
     * @param tipoIdAdminSubsidio
     *        the tipoIdAdminSubsidio to set
     */
    public void setTipoIdAdminSubsidio(TipoIdentificacionEnum tipoIdAdminSubsidio) {
        this.tipoIdAdminSubsidio = tipoIdAdminSubsidio;
    }

    /**
     * @return the numeroIdAdminSubsidio
     */
    public String getNumeroIdAdminSubsidio() {
        return numeroIdAdminSubsidio;
    }

    /**
     * @param numeroIdAdminSubsidio
     *        the numeroIdAdminSubsidio to set
     */
    public void setNumeroIdAdminSubsidio(String numeroIdAdminSubsidio) {
        this.numeroIdAdminSubsidio = numeroIdAdminSubsidio;
    }

    /**
     * @return the idMedioDePago
     */
    public Long getIdMedioDePago() {
        return idMedioDePago;
    }

    /**
     * @param idMedioDePago
     *        the idMedioDePago to set
     */
    public void setIdMedioDePago(Long idMedioDePago) {
        this.idMedioDePago = idMedioDePago;
    }

    /**
     * @return the detalleSubsidioAsignadoDTO
     */
    public DetalleSubsidioAsignadoDTO getDetalleSubsidioAsignadoDTO() {
        return detalleSubsidioAsignadoDTO;
    }

    /**
     * @param detalleSubsidioAsignadoDTO the detalleSubsidioAsignadoDTO to set
     */
    public void setDetalleSubsidioAsignadoDTO(DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO) {
        this.detalleSubsidioAsignadoDTO = detalleSubsidioAsignadoDTO;
    }

    /**
     * @return the nombreSitioPago
     */
    public String getNombreSitioPago() {
        return nombreSitioPago;
    }

    /**
     * @param nombreSitioPago the nombreSitioPago to set
     */
    public void setNombreSitioPago(String nombreSitioPago) {
        this.nombreSitioPago = nombreSitioPago;
    }

    /**
     * @return the nombreSitioCobro
     */
    public String getNombreSitioCobro() {
        return nombreSitioCobro;
    }

    /**
     * @param nombreSitioCobro the nombreSitioCobro to set
     */
    public void setNombreSitioCobro(String nombreSitioCobro) {
        this.nombreSitioCobro = nombreSitioCobro;
    }

    /**
     * @return the nombrePersonaAutorizada
     */
    public String getNombrePersonaAutorizada() {
        return nombrePersonaAutorizada;
    }

    /**
     * @param nombrePersonaAutorizada the nombrePersonaAutorizada to set
     */
    public void setNombrePersonaAutorizada(String nombrePersonaAutorizada) {
        this.nombrePersonaAutorizada = nombrePersonaAutorizada;
    }

    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the estadoTarjeta
     */
    public EstadoTarjetaMultiserviciosEnum getEstadoTarjeta() {
        return estadoTarjeta;
    }

    /**
     * @param estadoTarjeta the estadoTarjeta to set
     */
    public void setEstadoTarjeta(EstadoTarjetaMultiserviciosEnum estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }



    public Long getSolicitudLiquidacionSubsidio() {
        return solicitudLiquidacionSubsidio;
    }

    public void setSolicitudLiquidacionSubsidio(Long solicitudLiquidacionSubsidio) {
        this.solicitudLiquidacionSubsidio = solicitudLiquidacionSubsidio;
    }



    public Long getIdCuentaOriginal() {
        return idCuentaOriginal;
    }

    public void setIdCuentaOriginal(Long idCuentaOriginal) {
        this.idCuentaOriginal = idCuentaOriginal;
    }



    /**
     * @return the totalValorAbono
     */
    public BigDecimal getTotalValorAbono() {
        return totalValorAbono;
    }

    /**
     * @param totalValorAbono the totalValorAbono to set
     */
    public void setTotalValorAbono(BigDecimal totalValorAbono) {
        this.totalValorAbono = totalValorAbono;
    }

    /**
     * @return the nombrePuntoDeCobro
     */
    public String getIdPuntoDeCobro() {
        return idPuntoDeCobro;
    }

    /**
     * @param nombrePuntoDeCobro the nombrePuntoDeCobro to set
     */
    public void setIdPuntoDeCobro(String idPuntoDeCobro) {
        this.idPuntoDeCobro = idPuntoDeCobro;
    }

    public String getEstablecimientoCodigo() {
        return establecimientoCodigo;
    }

    public void setEstablecimientoCodigo(String establecimientoCodigo) {
        this.establecimientoCodigo = establecimientoCodigo;
    }

    public String getEstablecimientoNombre() {
        return establecimientoNombre;
    }

    public void setEstablecimientoNombre(String establecimientoNombre) {
        this.establecimientoNombre = establecimientoNombre;
    }

    public String getFechaTransaccionConsumo() {
        return fechaTransaccionConsumo;
    }

    public void setFechaTransaccionConsumo(String fechaTransaccionConsumo) {
        this.fechaTransaccionConsumo = fechaTransaccionConsumo;
    }

    // inicia 80008
    public Boolean isEsFlujoTraslado() {
        return this.esFlujoTraslado;
    }

    public Boolean getEsFlujoTraslado() {
        return this.esFlujoTraslado;
    }

    public void setEsFlujoTraslado(Boolean esFlujoTraslado) {
        this.esFlujoTraslado = esFlujoTraslado;
    }
    // finaliza 80008

    public EstadoTransaccionSubsidioEnum getEstadoLiquidacionSubsidio() {
        return estadoLiquidacionSubsidio;
    }

    public void setEstadoLiquidacionSubsidio(EstadoTransaccionSubsidioEnum estadoLiquidacionSubsidio) {
        this.estadoLiquidacionSubsidio = estadoLiquidacionSubsidio;
    }

    public Long getCondicionPersonaAdmin() {
        return condicionPersonaAdmin;
    }

    public void setCondicionPersonaAdmin(Long condicionPersonaAdmin) {
        this.condicionPersonaAdmin = condicionPersonaAdmin;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("idCuentaAdministradorSubsidio: ");
        builder.append(idCuentaAdministradorSubsidio);
        builder.append(", fechaHoraCreacionRegistro: ");
        builder.append(fechaHoraCreacionRegistro);
        builder.append(", usuarioCreacionRegistro: ");
        builder.append(usuarioCreacionRegistro);
        builder.append(", tipoTransaccion: ");
        builder.append(tipoTransaccion);
        builder.append(", estadoTransaccion: ");
        builder.append(estadoTransaccion);
        builder.append(", origenTransaccion: ");
        builder.append(origenTransaccion);
        builder.append(", medioDePago: ");
        builder.append(medioDePago);
        builder.append(", valorOriginalTransaccion: ");
        builder.append(valorOriginalTransaccion);
        builder.append(", numeroTarjetaAdminSubsidio: ");
        builder.append(numeroTarjetaAdminSubsidio);
        builder.append(", codigoBancoAdminSubsidio: ");
        builder.append(codigoBancoAdminSubsidio);
        builder.append(", nombreBancoAdminSubsidio: ");
        builder.append(nombreBancoAdminSubsidio);
        builder.append(", tipoCuentaAdminSubsidio: ");
        builder.append(tipoCuentaAdminSubsidio);
        builder.append(", numeroCuentaAdminSubsidio: ");
        builder.append(numeroCuentaAdminSubsidio);
        builder.append(", tipoIdentificacionTitularCuentaAdminSubsidio: ");
        builder.append(tipoIdentificacionTitularCuentaAdminSubsidio);
        builder.append(", numeroIdentificacionTitularCuentaAdminSubsidio: ");
        builder.append(numeroIdentificacionTitularCuentaAdminSubsidio);
        builder.append(", nombreTitularCuentaAdminSubsidio: ");
        builder.append(nombreTitularCuentaAdminSubsidio);
        builder.append(", valorRealTransaccion: ");
        builder.append(valorRealTransaccion);
        builder.append(", fechaHoraTransaccion: ");
        builder.append(fechaHoraTransaccion);
        builder.append(", usuarioTransaccionLiquidacion: ");
        builder.append(usuarioTransaccionLiquidacion);
        builder.append(", idTransaccionOriginal: ");
        builder.append(idTransaccionOriginal);
        builder.append(", idRemisionDatosTerceroPagador: ");
        builder.append(idRemisionDatosTerceroPagador);
        builder.append(", idTransaccionTerceroPagador: ");
        builder.append(idTransaccionTerceroPagador);
        builder.append(", nombreTerceroPagador: ");
        builder.append(nombreTerceroPagador);
        builder.append(", idCuentaAdminSubsidioRelacionado: ");
        builder.append(idCuentaAdminSubsidioRelacionado);
        builder.append(", fechaHoraUltimaModificacion: ");
        builder.append(fechaHoraUltimaModificacion);
        builder.append(", usuarioUltimaModificacion: ");
        builder.append(usuarioUltimaModificacion);
        builder.append(", idAdministradorSubsidio: ");
        builder.append(idAdministradorSubsidio);
        builder.append(", idSitioDePago: ");
        builder.append(idSitioDePago);
        builder.append(", idSitioDeCobro: ");
        builder.append(idSitioDeCobro);
        builder.append(", listaDetallesSubsidioAsignadoDTO: ");
        builder.append(listaDetallesSubsidioAsignadoDTO);
        builder.append(", nombresApellidosAdminSubsidio: ");
        builder.append(nombresApellidosAdminSubsidio);
        builder.append(", estadoAbono: ");
        builder.append(estadoAbono);
        builder.append(", tipoIdAdminSubsidio: ");
        builder.append(tipoIdAdminSubsidio);
        builder.append(", numeroIdAdminSubsidio: ");
        builder.append(numeroIdAdminSubsidio);
        builder.append(", idMedioDePago: ");
        builder.append(idMedioDePago);
        builder.append(", detalleSubsidioAsignadoDTO: ");
        builder.append(detalleSubsidioAsignadoDTO);
        builder.append(", nombreSitioPago: ");
        builder.append(nombreSitioPago);
        builder.append(", nombreSitioCobro: ");
        builder.append(nombreSitioCobro);
        builder.append(", nombrePersonaAutorizada: ");
        builder.append(nombrePersonaAutorizada);
        builder.append(", idEmpleador: ");
        builder.append(idEmpleador);
        builder.append(", idAfiliadoPrincipal: ");
        builder.append(idAfiliadoPrincipal);
        builder.append(", idBeneficiarioDetalle: ");
        builder.append(idBeneficiarioDetalle);
        builder.append(", idGrupoFamiliar: ");
        builder.append(idGrupoFamiliar);
        builder.append(", estadoTarjeta: ");
        builder.append(estadoTarjeta);
        builder.append(", idCuentaOriginal: ");
        builder.append(idCuentaOriginal);
        builder.append(", idPuntoDeCobro: ");
        builder.append(idPuntoDeCobro);
        builder.append(", establecimientoCodigo: ");
        builder.append(establecimientoCodigo);
        builder.append(", establecimientoNombre: ");
        builder.append(establecimientoNombre);
        builder.append(", fechaTransaccionConsumo: ");
        builder.append(fechaTransaccionConsumo);
        builder.append(", esFlujoTraslado: ");
        builder.append(esFlujoTraslado);
        return builder.toString();
    }

    public void setParametrosOutRegistroOperacion(String parametrosOutRegistroOperacion) {
        this.parametrosOutRegistroOperacion = parametrosOutRegistroOperacion;
    }

    public String getEstadoTransaccionRetiro() {
        return this.estadoTransaccionRetiro;
    }

    public void setEstadoTransaccionRetiro(String estadoTransaccionRetiro) {
        this.estadoTransaccionRetiro = estadoTransaccionRetiro;
    }

    public Long getIdTransaccionRetiro() {
        return this.idTransaccionRetiro;
    }

    public void setIdTransaccionRetiro(Long idTransaccionRetiro) {
        this.idTransaccionRetiro = idTransaccionRetiro;
    }

    public String getFechaYHoraTransaccionRetiro() {
        return this.fechaYHoraTransaccionRetiro;
    }

    public void setFechaYHoraTransaccionRetiro(String fechaYHoraTransaccionRetiro) {
        this.fechaYHoraTransaccionRetiro = fechaYHoraTransaccionRetiro;
    }

    public String getValorRealTransaccionRetiro() {
        return this.valorRealTransaccionRetiro;
    }

    public void setValorRealTransaccionRetiro(String valorRealTransaccionRetiro) {
        this.valorRealTransaccionRetiro = valorRealTransaccionRetiro;
    }


    public String getTotalRegistro() {
        return this.totalRegistro;
    }

    public void setTotalRegistro(String totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

}
