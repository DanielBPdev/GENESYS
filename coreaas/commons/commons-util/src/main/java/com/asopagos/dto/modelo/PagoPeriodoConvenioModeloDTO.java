package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.PagoPeriodoConvenio;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que representa el modelo de pago de cada uno de los periodos para un Convenio
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class PagoPeriodoConvenioModeloDTO implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2756880659518774158L;
    /**
     * Identificador de la entidad PagoConvenio
     */
    private Long idPagoConvenio;
    /**
     * Fecha en la cual se realizo el pago de un periodo para un convenio
     */
    private Long fechaPago;
    /**
     * Valor de la cuota que se pago para el periodo
     */
    private BigDecimal valorCuota;
    /**
     * Periodo relacionado al pago
     */
    private Long periodo;
    /**
     * Identificador de la entidad ConvenioPago relacionado con la entidad actual
     */
    private Long idConvenioPago;

    /**
     * Tipo de identificación
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación
     */
    private String numeroIdentificacion;
    /**
     * Nombre de la razón social
     */
    private String razonSocial;
    /**
     * Descripción del primer nombre
     */
    private String primerNombre;
    /**
     * Descripción del segundo nombre
     */
    private String segundoNombre;
    /**
     * Descripción del primer apellido
     */
    private String primerApellido;

    /**
     * Descripción del segundo apellido
     */
    private String segundoApellido;

    /**
     * Método constructor
     */
    public PagoPeriodoConvenioModeloDTO() {
    }

    /**
     * Método encargado de convertir de DTO a entidad
     * @return {@link PagoPeriodoConvenio}
     */
    public PagoPeriodoConvenio convertToEntity() {
        PagoPeriodoConvenio pagoPeriodoConvenio = new PagoPeriodoConvenio();
        pagoPeriodoConvenio.setIdConvenioPago(this.getIdConvenioPago());
        pagoPeriodoConvenio.setIdPagoConvenio(this.getIdPagoConvenio());
        pagoPeriodoConvenio.setPeriodo(this.getPeriodo() != null ? new Date(this.getPeriodo()) : null);
        pagoPeriodoConvenio.setFechaPago(this.getFechaPago() != null ? new Date(this.getFechaPago()) : null);
        pagoPeriodoConvenio.setValorCuota(this.getValorCuota());
        return pagoPeriodoConvenio;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param ConvenioPago
     *        entidad a convertir.
     */
    public void convertToDTO(PagoPeriodoConvenio pagoPeriodoConvenio) {
        this.setIdConvenioPago(pagoPeriodoConvenio.getIdConvenioPago());
        this.setIdPagoConvenio(pagoPeriodoConvenio.getIdPagoConvenio());
        this.setFechaPago(pagoPeriodoConvenio.getFechaPago() != null ? pagoPeriodoConvenio.getFechaPago().getTime() : null);
        this.setPeriodo(pagoPeriodoConvenio.getPeriodo() != null ? pagoPeriodoConvenio.getPeriodo().getTime() : null);
        this.setValorCuota(pagoPeriodoConvenio.getValorCuota());
    }

    /**
     * @param idPagoConvenio
     * @param fechaPago
     * @param valorCuota
     * @param periodo
     * @param idConvenioPago
     */
    public PagoPeriodoConvenioModeloDTO(PagoPeriodoConvenio pagoPeriodoConvenio) {
        this.setIdConvenioPago(pagoPeriodoConvenio.getIdConvenioPago());
        this.setIdPagoConvenio(pagoPeriodoConvenio.getIdPagoConvenio());
        this.setFechaPago(pagoPeriodoConvenio.getFechaPago() != null ? pagoPeriodoConvenio.getFechaPago().getTime() : null);
        this.setPeriodo(pagoPeriodoConvenio.getPeriodo() != null ? pagoPeriodoConvenio.getPeriodo().getTime() : null);
        this.setValorCuota(pagoPeriodoConvenio.getValorCuota());
    }
 
    /**
     * 
     * @param persona
     * @param pagoPeriodoConvenio
     */
    public PagoPeriodoConvenioModeloDTO(Persona persona,PagoPeriodoConvenio pagoPeriodoConvenio ){
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.primerNombre = persona.getPrimerNombre();
        this.segundoNombre = persona.getSegundoNombre();
        this.primerApellido = persona.getPrimerApellido();
        this.segundoApellido = persona.getSegundoApellido();
        this.razonSocial = persona.getRazonSocial();
        
        this.setIdConvenioPago(pagoPeriodoConvenio.getIdConvenioPago());
        this.setIdPagoConvenio(pagoPeriodoConvenio.getIdPagoConvenio());
        this.setFechaPago(pagoPeriodoConvenio.getFechaPago() != null ? pagoPeriodoConvenio.getFechaPago().getTime() : null);
        this.setPeriodo(pagoPeriodoConvenio.getPeriodo() != null ? pagoPeriodoConvenio.getPeriodo().getTime() : null);
        this.setValorCuota(pagoPeriodoConvenio.getValorCuota());
    }

    /**
     * @return the idPagoConvenio
     */
    public Long getIdPagoConvenio() {
        return idPagoConvenio;
    }

    /**
     * @param idPagoConvenio
     *        the idPagoConvenio to set
     */
    public void setIdPagoConvenio(Long idPagoConvenio) {
        this.idPagoConvenio = idPagoConvenio;
    }

    /**
     * @return the fechaPago
     */
    public Long getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago
     *        the fechaPago to set
     */
    public void setFechaPago(Long fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * @return the valorCuota
     */
    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    /**
     * @param valorCuota
     *        the valorCuota to set
     */
    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    /**
     * @return the periodo
     */
    public Long getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
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
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de razonSocial.
     * @return valor de razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de razonSocial.
     * @param valor
     *        para modificar razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor
     *        para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor
     *        para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor
     *        para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor
     *        para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

}
