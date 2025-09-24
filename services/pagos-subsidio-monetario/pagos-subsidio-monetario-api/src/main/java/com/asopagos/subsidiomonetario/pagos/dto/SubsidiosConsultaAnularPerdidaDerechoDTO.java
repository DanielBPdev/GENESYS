package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioEnum;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * <b>Descripcion:</b> Clase DTO que contiene los registros candidatos de los subsidios monetarios
 * para ser anulados por perdida de derecho <br/>
 * <b>Módulo:</b> Asopagos - HU -31 - 225 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */
@XmlRootElement
public class SubsidiosConsultaAnularPerdidaDerechoDTO implements Serializable {

    private static final long serialVersionUID = -897560930327085593L;

    /**
     * Identificador de la cuenta del administrador del subsidio asociado al detalle.
     */
    private Long idCuentaAdminSubsidio;

    /**
     * Identificador del detalle de subsidio asignado a la solicitud de liquidación
     */
    private Long idDetalleSubsidioAsignado;

    /**
     * Periodo de liquidación del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date periodoLiquidado;

    /**
     * Identificador de la liquidación asociada del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Long idLiquidacionAsociada;

    /**
     * Fecha de la liquidación asociada del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date fechaLiquidacionAsociada;

    /**
     * Empleador relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO empleador;

    /**
     * Afiliado principal relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO afiliadoPrincipal;

    /**
     * Código del grupo familiar relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Short codigoGrupoFamiliarRelacionado;

    /**
     * Beneficiario del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO beneficiario;

    /**
     * Administrador del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO administradorSubsidio;

    /**
     * Medio de pago del subsidio monetario
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Número de la tarjeta del administrador del subsidio
     * si el medio de pago tipo tarjeta.
     */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String numeroTarjetaAdminSubsidio;

    /**
     * Código del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String codigoBancoAdminSubsidio;

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
     * Fecha y hora en la cual se realiza la transacción en la
     * cuenta del administrador del subsidio.
     */
    private Date fechaHoraTransaccion;

    /**
     * Nombre del usuario que registró la transacción
     */
    private String usuarioTransaccion;

    /**
     * Sumatoria del campo "valor original abonado" de cada uno
     * de los registros del detalle del subsidio asignado
     */
    private BigDecimal valorOriginalTransaccion;

    /**
     * variable que contiene el mismo valor del campo "valor original transacción"
     */
    private BigDecimal valorRealTransaccion;

    /**
     * Motivo de anulación del subsidio (Detalle)
     */
    private MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion;

    /**
     * Otros detalles sobre la anulación.
     */
    private String detalleAnulacion;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Date fechaHoraCreacionRegistro;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String usuarioCreacionRegistro;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String tipoTransaccionSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String estadoTransaccionSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private OrigenTransaccionEnum origenTransaccion;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String nombreBancoAdminSubsidio;

   @JsonInclude(JsonInclude.Include.ALWAYS)
    private TipoCuentaEnum  tipoCuentaAdmonSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String numeroCuentaAdmonSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdmonSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String numeroIdentificacionTitularCuentaAdmonSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String nombreTitularCuentaAdmonSubsidio;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String sitioDePago;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Date fechaHoraUltimaModificacion;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String usuarioUltimaModificacion;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String idCuentaAdmonSubsidioRelacionado;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long  idTransaccionOriginal;




    /**
     * Constructor vacio de la clase
     */
    public SubsidiosConsultaAnularPerdidaDerechoDTO() {
    }

    /**
     * Constructor que inicializa la clase con la información de diferentes entidades
     * @param cuentaAdministradorSubsidio
     *        entidad que representa la cuenta del administrador de subsidio
     * @param detalleSubsidioAsignado
     *        entidad que representa el detalle de subsidio asignado.
     * @param fechaPeriodo
     *        fecha del periodo de liquidación.
     * @param liquidacionSubsidio
     *        entidad que representa los datos de la solicitud de liquidación del subsidio.
     * @param empleador
     *        entidad que representa la información basica del empleador.
     * @param afiliado
     *        entidad que representa la información basica del afiliado.
     * @param codigoGrupoFamiliar
     *        código que representa el grupo familiar asociado al afiliado.
     * @param beneficiario
     *        entidad que representa la información basica del beneficiario.
     * @param administradorSubsidio
     *        entidad que representa la información basica del administrador subsidio.
     */
    public SubsidiosConsultaAnularPerdidaDerechoDTO(CuentaAdministradorSubsidio cuentaAdministradorSubsidio,
            DetalleSubsidioAsignado detalleSubsidioAsignado, Date fechaPeriodo, SolicitudLiquidacionSubsidio liquidacionSubsidio,
            Persona empleador, Persona afiliado, Short codigoGrupoFamiliar, Persona beneficiario, Persona administradorSubsidio) {

        this.setPeriodoLiquidado(fechaPeriodo);
        this.setIdLiquidacionAsociada(liquidacionSubsidio.getIdProcesoLiquidacionSubsidio());
        this.setFechaLiquidacionAsociada(liquidacionSubsidio.getFechaEvaluacionSegundoNivel());
        this.setIdCuentaAdminSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setValorRealTransaccion(cuentaAdministradorSubsidio.getValorRealTransaccion());
        this.setValorOriginalTransaccion(cuentaAdministradorSubsidio.getValorOriginalTransaccion());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setFechaHoraTransaccion(cuentaAdministradorSubsidio.getFechaHoraTransaccion());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setUsuarioTransaccion(cuentaAdministradorSubsidio.getUsuarioTransaccion());
        this.setIdDetalleSubsidioAsignado(detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
        this.setCodigoGrupoFamiliarRelacionado(codigoGrupoFamiliar);

        PersonaDTO persona = new PersonaDTO(empleador);
        PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();

        personaModeloDTO = new PersonaModeloDTO();
        personaModeloDTO.convertFromPersonaDTO(persona);
        this.setEmpleador(personaModeloDTO);

        PersonaDTO personaAfi  = new PersonaDTO(afiliado);
        PersonaModeloDTO personaAfiModeloDTO = new PersonaModeloDTO();
        personaAfiModeloDTO.convertFromPersonaDTO(personaAfi);
        this.setAfiliadoPrincipal(personaAfiModeloDTO);

        PersonaDTO personaBen = new PersonaDTO(beneficiario);
        PersonaModeloDTO personaBenModeloDTO = new PersonaModeloDTO();
        personaBenModeloDTO.convertFromPersonaDTO(personaBen);
        this.setBeneficiario(personaBenModeloDTO);

        PersonaDTO personaAdm = new PersonaDTO(administradorSubsidio);
        PersonaModeloDTO personaAdmModeloDTO = new PersonaModeloDTO();
        personaAdmModeloDTO.convertFromPersonaDTO(personaAdm);
        this.setAdministradorSubsidio(personaAdmModeloDTO);
    }

    public Long getIdCuentaAdminSubsidio() {
        return this.idCuentaAdminSubsidio;
    }

    public void setIdCuentaAdminSubsidio(Long idCuentaAdminSubsidio) {
        this.idCuentaAdminSubsidio = idCuentaAdminSubsidio;
    }

    public Long getIdDetalleSubsidioAsignado() {
        return this.idDetalleSubsidioAsignado;
    }

    public void setIdDetalleSubsidioAsignado(Long idDetalleSubsidioAsignado) {
        this.idDetalleSubsidioAsignado = idDetalleSubsidioAsignado;
    }

    public Date getPeriodoLiquidado() {
        return this.periodoLiquidado;
    }

    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    public Long getIdLiquidacionAsociada() {
        return this.idLiquidacionAsociada;
    }

    public void setIdLiquidacionAsociada(Long idLiquidacionAsociada) {
        this.idLiquidacionAsociada = idLiquidacionAsociada;
    }

    public Date getFechaLiquidacionAsociada() {
        return this.fechaLiquidacionAsociada;
    }

    public void setFechaLiquidacionAsociada(Date fechaLiquidacionAsociada) {
        this.fechaLiquidacionAsociada = fechaLiquidacionAsociada;
    }

    public PersonaModeloDTO getEmpleador() {
        return this.empleador;
    }

    public void setEmpleador(PersonaModeloDTO empleador) {
        this.empleador = empleador;
    }

    public PersonaModeloDTO getAfiliadoPrincipal() {
        return this.afiliadoPrincipal;
    }

    public void setAfiliadoPrincipal(PersonaModeloDTO afiliadoPrincipal) {
        this.afiliadoPrincipal = afiliadoPrincipal;
    }

    public Short getCodigoGrupoFamiliarRelacionado() {
        return this.codigoGrupoFamiliarRelacionado;
    }

    public void setCodigoGrupoFamiliarRelacionado(Short codigoGrupoFamiliarRelacionado) {
        this.codigoGrupoFamiliarRelacionado = codigoGrupoFamiliarRelacionado;
    }

    public PersonaModeloDTO getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(PersonaModeloDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    public PersonaModeloDTO getAdministradorSubsidio() {
        return this.administradorSubsidio;
    }

    public void setAdministradorSubsidio(PersonaModeloDTO administradorSubsidio) {
        this.administradorSubsidio = administradorSubsidio;
    }

    public TipoMedioDePagoEnum getMedioDePago() {
        return this.medioDePago;
    }

    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    public String getNumeroTarjetaAdminSubsidio() {
        return this.numeroTarjetaAdminSubsidio;
    }

    public void setNumeroTarjetaAdminSubsidio(String numeroTarjetaAdminSubsidio) {
        this.numeroTarjetaAdminSubsidio = numeroTarjetaAdminSubsidio;
    }

    public String getCodigoBancoAdminSubsidio() {
        return this.codigoBancoAdminSubsidio;
    }

    public void setCodigoBancoAdminSubsidio(String codigoBancoAdminSubsidio) {
        this.codigoBancoAdminSubsidio = codigoBancoAdminSubsidio;
    }

    public TipoCuentaEnum getTipoCuentaAdminSubsidio() {
        return this.tipoCuentaAdminSubsidio;
    }

    public void setTipoCuentaAdminSubsidio(TipoCuentaEnum tipoCuentaAdminSubsidio) {
        this.tipoCuentaAdminSubsidio = tipoCuentaAdminSubsidio;
    }

    public String getNumeroCuentaAdminSubsidio() {
        return this.numeroCuentaAdminSubsidio;
    }

    public void setNumeroCuentaAdminSubsidio(String numeroCuentaAdminSubsidio) {
        this.numeroCuentaAdminSubsidio = numeroCuentaAdminSubsidio;
    }

    public TipoIdentificacionEnum getTipoIdentificacionTitularCuentaAdminSubsidio() {
        return this.tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    public void setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio) {
        this.tipoIdentificacionTitularCuentaAdminSubsidio = tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    public String getNumeroIdentificacionTitularCuentaAdminSubsidio() {
        return this.numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    public void setNumeroIdentificacionTitularCuentaAdminSubsidio(String numeroIdentificacionTitularCuentaAdminSubsidio) {
        this.numeroIdentificacionTitularCuentaAdminSubsidio = numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    public String getNombreTitularCuentaAdminSubsidio() {
        return this.nombreTitularCuentaAdminSubsidio;
    }

    public void setNombreTitularCuentaAdminSubsidio(String nombreTitularCuentaAdminSubsidio) {
        this.nombreTitularCuentaAdminSubsidio = nombreTitularCuentaAdminSubsidio;
    }

    public Date getFechaHoraTransaccion() {
        return this.fechaHoraTransaccion;
    }

    public void setFechaHoraTransaccion(Date fechaHoraTransaccion) {
        this.fechaHoraTransaccion = fechaHoraTransaccion;
    }

    public String getUsuarioTransaccion() {
        return this.usuarioTransaccion;
    }

    public void setUsuarioTransaccion(String usuarioTransaccion) {
        this.usuarioTransaccion = usuarioTransaccion;
    }

    public BigDecimal getValorOriginalTransaccion() {
        return this.valorOriginalTransaccion;
    }

    public void setValorOriginalTransaccion(BigDecimal valorOriginalTransaccion) {
        this.valorOriginalTransaccion = valorOriginalTransaccion;
    }

    public BigDecimal getValorRealTransaccion() {
        return this.valorRealTransaccion;
    }

    public void setValorRealTransaccion(BigDecimal valorRealTransaccion) {
        this.valorRealTransaccion = valorRealTransaccion;
    }

    public MotivoAnulacionSubsidioAsignadoEnum getMotivoAnulacion() {
        return this.motivoAnulacion;
    }

    public void setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public String getDetalleAnulacion() {
        return this.detalleAnulacion;
    }

    public void setDetalleAnulacion(String detalleAnulacion) {
        this.detalleAnulacion = detalleAnulacion;
    }

    public Date getFechaHoraCreacionRegistro() {
        return this.fechaHoraCreacionRegistro;
    }

    public void setFechaHoraCreacionRegistro(Date fechaHoraCreacionRegistro) {
        this.fechaHoraCreacionRegistro = fechaHoraCreacionRegistro;
    }

    public String getUsuarioCreacionRegistro() {
        return this.usuarioCreacionRegistro;
    }

    public void setUsuarioCreacionRegistro(String usuarioCreacionRegistro) {
        this.usuarioCreacionRegistro = usuarioCreacionRegistro;
    }

    public String getTipoTransaccionSubsidio() {
        return this.tipoTransaccionSubsidio;
    }

    public void setTipoTransaccionSubsidio(String tipoTransaccionSubsidio) {
        this.tipoTransaccionSubsidio = tipoTransaccionSubsidio;
    }

    public String getEstadoTransaccionSubsidio() {
        return this.estadoTransaccionSubsidio;
    }

    public void setEstadoTransaccionSubsidio(String estadoTransaccionSubsidio) {
        this.estadoTransaccionSubsidio = estadoTransaccionSubsidio;
    }

    public OrigenTransaccionEnum getOrigenTransaccion() {
        return this.origenTransaccion;
    }

    public void setOrigenTransaccion(OrigenTransaccionEnum origenTransaccion) {
        this.origenTransaccion = origenTransaccion;
    }

    public String getNombreBancoAdminSubsidio() {
        return this.nombreBancoAdminSubsidio;
    }

    public void setNombreBancoAdminSubsidio(String nombreBancoAdminSubsidio) {
        this.nombreBancoAdminSubsidio = nombreBancoAdminSubsidio;
    }

    public TipoCuentaEnum getTipoCuentaAdmonSubsidio() {
        return this.tipoCuentaAdmonSubsidio;
    }

    public void setTipoCuentaAdmonSubsidio(TipoCuentaEnum tipoCuentaAdmonSubsidio) {
        this.tipoCuentaAdmonSubsidio = tipoCuentaAdmonSubsidio;
    }

    public String getNumeroCuentaAdmonSubsidio() {
        return this.numeroCuentaAdmonSubsidio;
    }

    public void setNumeroCuentaAdmonSubsidio(String numeroCuentaAdmonSubsidio) {
        this.numeroCuentaAdmonSubsidio = numeroCuentaAdmonSubsidio;
    }

    public TipoIdentificacionEnum getTipoIdentificacionTitularCuentaAdmonSubsidio() {
        return this.tipoIdentificacionTitularCuentaAdmonSubsidio;
    }

    public void setTipoIdentificacionTitularCuentaAdmonSubsidio(TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdmonSubsidio) {
        this.tipoIdentificacionTitularCuentaAdmonSubsidio = tipoIdentificacionTitularCuentaAdmonSubsidio;
    }

    public String getNumeroIdentificacionTitularCuentaAdmonSubsidio() {
        return this.numeroIdentificacionTitularCuentaAdmonSubsidio;
    }

    public void setNumeroIdentificacionTitularCuentaAdmonSubsidio(String numeroIdentificacionTitularCuentaAdmonSubsidio) {
        this.numeroIdentificacionTitularCuentaAdmonSubsidio = numeroIdentificacionTitularCuentaAdmonSubsidio;
    }

    public String getNombreTitularCuentaAdmonSubsidio() {
        return this.nombreTitularCuentaAdmonSubsidio;
    }

    public void setNombreTitularCuentaAdmonSubsidio(String nombreTitularCuentaAdmonSubsidio) {
        this.nombreTitularCuentaAdmonSubsidio = nombreTitularCuentaAdmonSubsidio;
    }

    public String getSitioDePago() {
        return this.sitioDePago;
    }

    public void setSitioDePago(String sitioDePago) {
        this.sitioDePago = sitioDePago;
    }

    public Date getFechaHoraUltimaModificacion() {
        return this.fechaHoraUltimaModificacion;
    }

    public void setFechaHoraUltimaModificacion(Date fechaHoraUltimaModificacion) {
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    public String getUsuarioUltimaModificacion() {
        return this.usuarioUltimaModificacion;
    }

    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    public String getIdCuentaAdmonSubsidioRelacionado() {
        return this.idCuentaAdmonSubsidioRelacionado;
    }

    public void setIdCuentaAdmonSubsidioRelacionado(String idCuentaAdmonSubsidioRelacionado) {
        this.idCuentaAdmonSubsidioRelacionado = idCuentaAdmonSubsidioRelacionado;
    }

    public Long getIdTransaccionOriginal() {
        return this.idTransaccionOriginal;
    }

    public void setIdTransaccionOriginal(Long idTransaccionOriginal) {
        this.idTransaccionOriginal = idTransaccionOriginal;
    }




}
