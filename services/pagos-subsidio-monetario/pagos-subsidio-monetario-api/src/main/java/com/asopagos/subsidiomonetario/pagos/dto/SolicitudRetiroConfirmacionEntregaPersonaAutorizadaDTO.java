package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información necesaria para realizar
 * una solicitud de retiro por una persona autorizada, realizar la confirmación del valor
 * entregado de dicha solicitud y registrar la persona y documento de autorización.
 * <b>Módulo:</b> Asopagos - HU-31-203<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la persona autorizada
     */
    @NotNull
    private Long idPersonaAutorizada;

    /**
     * DTO con la información del documento de autorización
     */
    private DocumentoSoporteModeloDTO documentoSoporte;

    /**
     * Tipo de identificación del administrador del subsidio.
     */
    @NotNull
    private TipoIdentificacionEnum tipoIdAdmin;

    /**
     * Número de identificación del administrador del subsidio
     */
    @NotNull
    private String numeroIdAdmin;

    /**
     * Tipo de medio de pago de la
     */
    @NotNull
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Saldo disponible que tiene el administrador del subsidio.
     */
    @NotNull
    private BigDecimal saldoActualSubsidio;

    /**
     * Valor solicitado a retirar por el administrador del subsidio.
     */
    @NotNull
    private BigDecimal valorSolicitado;

    /**
     * Fecha en la cual se solicito el retiro.
     */
    @NotNull
    private Long fecha;

    /**
     * Identificador de transacción del tercero pagador único asociado al retiro.
     */
    private String idTransaccionTercerPagador;

    /**
     * Código del DANE que representa el departamento.
     */
    private String departamento;

    /**
     * Código del DANE que representa el municipio.
     */
    private String municipio;

    /**
     * Usuario que solicita el retiro (susuerte, ventanilla, redeban)
     */
    private String usuario;
    
    /**
     *  Código que la caja define para la identificación de cada establecimiento/punto de cobro asociado al mismo tercero pagador
     */
    private String idPuntoCobro;

    /**
     * @return the idPersonaAutorizada
     */
    public Long getIdPersonaAutorizada() {
        return idPersonaAutorizada;
    }

    /**
     * @param idPersonaAutorizada
     *        the idPersonaAutorizada to set
     */
    public void setIdPersonaAutorizada(Long idPersonaAutorizada) {
        this.idPersonaAutorizada = idPersonaAutorizada;
    }

    /**
     * @return the documentoSoporte
     */
    public DocumentoSoporteModeloDTO getDocumentoSoporte() {
        return documentoSoporte;
    }

    /**
     * @param documentoSoporte
     *        the documentoSoporte to set
     */
    public void setDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    /**
     * @return the tipoIdAdmin
     */
    public TipoIdentificacionEnum getTipoIdAdmin() {
        return tipoIdAdmin;
    }

    /**
     * @param tipoIdAdmin
     *        the tipoIdAdmin to set
     */
    public void setTipoIdAdmin(TipoIdentificacionEnum tipoIdAdmin) {
        this.tipoIdAdmin = tipoIdAdmin;
    }

    /**
     * @return the numeroIdAdmin
     */
    public String getNumeroIdAdmin() {
        return numeroIdAdmin;
    }

    /**
     * @param numeroIdAdmin
     *        the numeroIdAdmin to set
     */
    public void setNumeroIdAdmin(String numeroIdAdmin) {
        this.numeroIdAdmin = numeroIdAdmin;
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
     * @return the saldoActualSubsidio
     */
    public BigDecimal getSaldoActualSubsidio() {
        return saldoActualSubsidio;
    }

    /**
     * @param saldoActualSubsidio
     *        the saldoActualSubsidio to set
     */
    public void setSaldoActualSubsidio(BigDecimal saldoActualSubsidio) {
        this.saldoActualSubsidio = saldoActualSubsidio;
    }

    /**
     * @return the valorSolicitado
     */
    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    /**
     * @param valorSolicitado
     *        the valorSolicitado to set
     */
    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    /**
     * @return the fecha
     */
    public Long getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *        the fecha to set
     */
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the idTransaccionTercerPagador
     */
    public String getIdTransaccionTercerPagador() {
        return idTransaccionTercerPagador;
    }

    /**
     * @param idTransaccionTercerPagador
     *        the idTransaccionTercerPagador to set
     */
    public void setIdTransaccionTercerPagador(String idTransaccionTercerPagador) {
        this.idTransaccionTercerPagador = idTransaccionTercerPagador;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento
     *        the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio
     *        the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the idPuntoCobro
     */
    public String getIdPuntoCobro() {
        return idPuntoCobro;
    }

    /**
     * @param idPuntoCobro the idPuntoCobro to set
     */
    public void setIdPuntoCobro(String idPuntoCobro) {
        this.idPuntoCobro = idPuntoCobro;
    }

}
