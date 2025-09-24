package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.ccf.cartera.ConvenioPago;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoConvenioPagoEnum;
import com.asopagos.enumeraciones.cartera.MotivoAnulacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que tiene el modelo que representa el convenio de pago para un aportante y sus cotizantes
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ConvenioPagoModeloDTO implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8781028927397879831L;
    /**
     * Número de registro del convenio de pago
     */
    private Long idConvenioPago;
    /**
     * Identificador de la persona que asume el convenio
     */
    private Long idPersona;
    /**
     * Representa los posibles estados que puede tener un convenio de pago (Activo,
     * Anulado, Cerrado Satisfactoriamente)
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    /**
     * Representa los posibles estados que puede tener un convenio de pago (Activo,
     * Anulado, Cerrado Satisfactoriamente)
     */
    private EstadoConvenioPagoEnum estadoConvenioPago;
    /**
     * Fecha en la cual se creo o registro el convenio de pago para el aportante
     */
    private Long fechaRegistro;
    /**
     * Valor de la deuda presunta registrada
     */
    private BigDecimal deudaPresuntaRegistrada;
    /**
     * Valor de la deuda real registrada
     */
    private BigDecimal deudaRealRegistrada;
    /**
     * Número de cuotas relacionados a la deuda del convenio de pago
     */
    private Short cuotasPorPagar;
    /**
     * Enumeración que representa el motivo de anulación de un convenio de pago
     */
    private MotivoAnulacionEnum motivoAnulacion;
    /**
     * Nombre del usuario que realiza la creación del convenio de pago
     */
    private String usuarioCreacion;
    /**
     * Nombre del usuario que realiza la anulación del convenio de pago
     */
    private String usuarioAnulacion;
    /**
     * Fecha de anulación del convenio de pago
     */
    private Long fechaAnulacion;

    /**
     * Fecha limite de pago para un convenio
     */
    private Long fechaLimitePago;

    /**
     * Lista de los pagos de periodos acordados para el aportante
     */
    private List<PagoPeriodoConvenioModeloDTO> pagoPeriodos;
    
    /**
     * Atributo que hace referencia el numeroIdentificacion del aportante
     */
    private String numeroIdentificacion;
    /**
     * Atributo que hace referencia el tipoIndentificacion del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Método Constructor
     */
    public ConvenioPagoModeloDTO() {
    }

    /**
     * Método constructor
     * @param fechaLimite
     *        fecha limite de pago para un convenio
     * @param convenioPago
     *        convenio de pago relacionado a un aportante
     */
    public ConvenioPagoModeloDTO(Date fechaLimite, ConvenioPago convenioPago) {
        this.setIdConvenioPago(convenioPago.getIdConvenioPago());
        this.setEstadoConvenioPago(convenioPago.getEstadoConvenioPago());
        if (convenioPago.getDeudaRealRegistrada() != null && convenioPago.getDeudaRealRegistrada().equals(BigDecimal.ZERO)) {
            this.setDeudaRealRegistrada(convenioPago.getDeudaRealRegistrada());
        }
        else {
            this.setDeudaPresuntaRegistrada(convenioPago.getDeudaPresuntaRegistrada());
        }
        this.setFechaLimitePago(fechaLimite != null ? fechaLimite.getTime() : null);
        this.setFechaRegistro(convenioPago.getFechaRegistro() != null ? convenioPago.getFechaRegistro().getTime() : null);
    }

    /**
     * Método constructor
     * @param fechaLimite
     *        fecha limite de pago para un convenio
     * @param convenioPago
     *        convenio de pago relacionado a un aportante
     */
    public ConvenioPagoModeloDTO(ConvenioPago convenioPago, Date fechaLimite) {
        this.convertToDTO(convenioPago);
    }

    /**
     * Método constructor de un convenio de pago.
     * @param convenioPago
     *        convenio de pago.
     */
    public ConvenioPagoModeloDTO(ConvenioPago convenioPago) {
        this.convertToDTO(convenioPago);
    }

    /**
     * Método encargado de convertir de DTO a entidad
     * @return {@link ConvenioPago}
     */
    public ConvenioPago convertToEntity() {
        ConvenioPago convenioPago = new ConvenioPago();
        convenioPago.setIdConvenioPago(this.getIdConvenioPago());
        convenioPago.setIdPersona(this.getIdPersona());
        convenioPago.setEstadoConvenioPago(this.getEstadoConvenioPago());
        convenioPago.setDeudaPresuntaRegistrada(this.getDeudaPresuntaRegistrada());
        convenioPago.setDeudaRealRegistrada(this.getDeudaRealRegistrada());
        convenioPago.setCuotasPorPagar(this.getCuotasPorPagar());
        convenioPago.setFechaAnulacion(this.getFechaAnulacion() != null ? new Date(this.getFechaAnulacion()) : null);
        convenioPago.setFechaRegistro(this.getFechaRegistro() != null ? new Date(this.getFechaRegistro()) : null);
        convenioPago.setMotivoAnulacion(this.getMotivoAnulacion());
        convenioPago.setUsuarioCreacion(this.getUsuarioCreacion());
        convenioPago.setUsuarioAnulacion(this.getUsuarioAnulacion());
        convenioPago.setTipoSolicitante(this.getTipoSolicitante());
        return convenioPago;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param ConvenioPago
     *        entidad a convertir.
     */
    public void convertToDTO(ConvenioPago convenioPago) {
        this.setIdConvenioPago(convenioPago.getIdConvenioPago());
        this.setIdPersona(convenioPago.getIdPersona());
        this.setDeudaPresuntaRegistrada(convenioPago.getDeudaPresuntaRegistrada());
        this.setDeudaRealRegistrada(convenioPago.getDeudaRealRegistrada());
        this.setEstadoConvenioPago(convenioPago.getEstadoConvenioPago());
        this.setCuotasPorPagar(convenioPago.getCuotasPorPagar());
        this.setFechaAnulacion(convenioPago.getFechaAnulacion() != null ? convenioPago.getFechaAnulacion().getTime() : null);
        this.setFechaRegistro(convenioPago.getFechaRegistro() != null ? convenioPago.getFechaRegistro().getTime() : null);
        this.setMotivoAnulacion(convenioPago.getMotivoAnulacion());
        this.setUsuarioCreacion(convenioPago.getUsuarioCreacion());
        this.setUsuarioAnulacion(convenioPago.getUsuarioAnulacion());
        this.setTipoSolicitante(convenioPago.getTipoSolicitante());
    }

    /**
     * @return the idConvenioPago
     */
    public Long getIdConvenioPago() {
        return idConvenioPago;
    }

    /**
     * @param idConvenioPago
     *        the idConvenioPago to set
     */
    public void setIdConvenioPago(Long idConvenioPago) {
        this.idConvenioPago = idConvenioPago;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona
     *        the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the estadoConvenioPago
     */
    public EstadoConvenioPagoEnum getEstadoConvenioPago() {
        return estadoConvenioPago;
    }

    /**
     * @param estadoConvenioPago
     *        the estadoConvenioPago to set
     */
    public void setEstadoConvenioPago(EstadoConvenioPagoEnum estadoConvenioPago) {
        this.estadoConvenioPago = estadoConvenioPago;
    }

    /**
     * @return the fechaRegistro
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro
     *        the fechaRegistro to set
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the deudaPresuntaRegistrada
     */
    public BigDecimal getDeudaPresuntaRegistrada() {
        return deudaPresuntaRegistrada;
    }

    /**
     * @param deudaPresuntaRegistrada
     *        the deudaPresuntaRegistrada to set
     */
    public void setDeudaPresuntaRegistrada(BigDecimal deudaPresuntaRegistrada) {
        this.deudaPresuntaRegistrada = deudaPresuntaRegistrada;
    }

    /**
     * @return the deudaRealRegistrada
     */
    public BigDecimal getDeudaRealRegistrada() {
        return deudaRealRegistrada;
    }

    /**
     * @param deudaRealRegistrada
     *        the deudaRealRegistrada to set
     */
    public void setDeudaRealRegistrada(BigDecimal deudaRealRegistrada) {
        this.deudaRealRegistrada = deudaRealRegistrada;
    }

    /**
     * @return the cuotasPorPagar
     */
    public Short getCuotasPorPagar() {
        return cuotasPorPagar;
    }

    /**
     * @param cuotasPorPagar
     *        the cuotasPorPagar to set
     */
    public void setCuotasPorPagar(Short cuotasPorPagar) {
        this.cuotasPorPagar = cuotasPorPagar;
    }

    /**
     * @return the motivoAnulacion
     */
    public MotivoAnulacionEnum getMotivoAnulacion() {
        return motivoAnulacion;
    }

    /**
     * @param motivoAnulacion
     *        the motivoAnulacion to set
     */
    public void setMotivoAnulacion(MotivoAnulacionEnum motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion
     *        the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the usuarioAnulacion
     */
    public String getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    /**
     * @param usuarioAnulacion
     *        the usuarioAnulacion to set
     */
    public void setUsuarioAnulacion(String usuarioAnulacion) {
        this.usuarioAnulacion = usuarioAnulacion;
    }

    /**
     * @return the fechaAnulacion
     */
    public Long getFechaAnulacion() {
        return fechaAnulacion;
    }

    /**
     * @param fechaAnulacion
     *        the fechaAnulacion to set
     */
    public void setFechaAnulacion(Long fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    /**
     * @return the pagoPeriodos
     */
    public List<PagoPeriodoConvenioModeloDTO> getPagoPeriodos() {
        return pagoPeriodos;
    }

    /**
     * @param pagoPeriodos
     *        the pagoPeriodos to set
     */
    public void setPagoPeriodos(List<PagoPeriodoConvenioModeloDTO> pagoPeriodos) {
        this.pagoPeriodos = pagoPeriodos;
    }

    /**
     * @return the fechaLimitePago
     */
    public Long getFechaLimitePago() {
        return fechaLimitePago;
    }

    /**
     * @param fechaLimitePago
     *        the fechaLimitePago to set
     */
    public void setFechaLimitePago(Long fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de tipoIndentificacion.
     * @return valor de tipoIndentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIndentificacion.
     * @param valor para modificar tipoIndentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

}
