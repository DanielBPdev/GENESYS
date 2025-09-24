package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DetalleCorreccionAportanteVista360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
 
    /**
     * Indica el tipo de solicitante que realizo el movimiento en aporte
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Tipo Identificación del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    
    /**
     * Numero Identificación del aportante
     */
    private String numeroIdentificacionAportante;
    
    /**
     * Primer nombre del aportante
     */
    private String primerNombre;
   
    /**
     * Segundo nombre del aportante
     */
    private String segundoNombre;
    
    /**
     * Primer apellido del aportante
     */
    private String primerApellido;
    
    /**
     * Segundo apellido del aportante
     */
    private String segundoApellido;
    
    /**
     * Razón social de la persona
     * */
    private String razonSocial;
    
    /**
     * Indica el origen del aporte
     */
    private OrigenAporteEnum origenAporte;
    
    /**
     * Período de pago
     */
    private String periodoAporte;
   
    /**
     * Codigo de la entidad financiera recaudadora o receptora
     */
    private Short codigoEntidadFinanciera;
    
    /**
     * Movimiento del aporte.
     */
    private BigDecimal valorAporte;

    /**
     * Movimiento del interés.
     */
    private BigDecimal valorInteres;
    
    /**
     * Movimiento del interés.
     */
    private BigDecimal totalAporte;
    
    /**
     * Identificador registro general
     */
    private Long idRegistroGeneral;
    
    /**
     * Indica la clase de aportante indicada en <code>ClaseAportanteEnum</code>
     */
    private String claseAportante;
    
    /**
     * Indica el campo de salida del proceso: típo de beneficio
     */
    private TipoBeneficioEnum outTipoBeneficio;
   
    /**
     * Indica el campo de salida del proceso: tiene beneficio activo? 1=[si] y 0=[no]
     */
    private Boolean outBeneficioActivo;
    
    /**
     * Indica el tipo de persona
     */
    private String tipoPersona;

    /**
     * Constructor de la clase
     */
    public DetalleCorreccionAportanteVista360DTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String razonSocial, 
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, OrigenAporteEnum origenAporte, String periodoAporte,
            Short codigoEntidadFinanciera, Long registroGeneral, BigDecimal monto, BigDecimal interes) {
        this.tipoSolicitante = tipoSolicitante;
        this.tipoIdentificacionAportante = tipoIdentificacion;
        this.numeroIdentificacionAportante = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.razonSocial = razonSocial;
        this.origenAporte = origenAporte;
        this.periodoAporte = periodoAporte;
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
        BigDecimal montoAporte = monto != null ? monto : BigDecimal.ZERO;
        BigDecimal interesAporte =interes != null ? interes : BigDecimal.ZERO; 
        this.valorAporte = montoAporte;
        this.valorInteres = interesAporte;
        this.totalAporte = montoAporte.add(interesAporte);
        this.idRegistroGeneral = registroGeneral;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionAportante.
     * @return valor de tipoIdentificacionAportante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionAportante.
     * @return valor de numeroIdentificacionAportante.
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
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
     * Método que retorna el valor de origenAporte.
     * @return valor de origenAporte.
     */
    public OrigenAporteEnum getOrigenAporte() {
        return origenAporte;
    }

    /**
     * Método que retorna el valor de periodoAporte.
     * @return valor de periodoAporte.
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * Método que retorna el valor de codigoEntidadFinanciera.
     * @return valor de codigoEntidadFinanciera.
     */
    public Short getCodigoEntidadFinanciera() {
        return codigoEntidadFinanciera;
    }

    /**
     * Método que retorna el valor de valorAporte.
     * @return valor de valorAporte.
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * Método que retorna el valor de valorInteres.
     * @return valor de valorInteres.
     */
    public BigDecimal getValorInteres() {
        return valorInteres;
    }

    /**
     * Método que retorna el valor de totalAporte.
     * @return valor de totalAporte.
     */
    public BigDecimal getTotalAporte() {
        return totalAporte;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionAportante.
     * @param valor para modificar tipoIdentificacionAportante.
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionAportante.
     * @param valor para modificar numeroIdentificacionAportante.
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de origenAporte.
     * @param valor para modificar origenAporte.
     */
    public void setOrigenAporte(OrigenAporteEnum origenAporte) {
        this.origenAporte = origenAporte;
    }

    /**
     * Método encargado de modificar el valor de periodoAporte.
     * @param valor para modificar periodoAporte.
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * Método encargado de modificar el valor de codigoEntidadFinanciera.
     * @param valor para modificar codigoEntidadFinanciera.
     */
    public void setCodigoEntidadFinanciera(Short codigoEntidadFinanciera) {
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
    }

    /**
     * Método encargado de modificar el valor de valorAporte.
     * @param valor para modificar valorAporte.
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    /**
     * Método encargado de modificar el valor de valorInteres.
     * @param valor para modificar valorInteres.
     */
    public void setValorInteres(BigDecimal valorInteres) {
        this.valorInteres = valorInteres;
    }

    /**
     * Método encargado de modificar el valor de totalAporte.
     * @param valor para modificar totalAporte.
     */
    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    /**
     * Método que retorna el valor de idRegistroGeneral.
     * @return valor de idRegistroGeneral.
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * Método encargado de modificar el valor de idRegistroGeneral.
     * @param valor para modificar idRegistroGeneral.
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * Método que retorna el valor de claseAportante.
     * @return valor de claseAportante.
     */
    public String getClaseAportante() {
        return claseAportante;
    }

    /**
     * Método que retorna el valor de outTipoBeneficio.
     * @return valor de outTipoBeneficio.
     */
    public TipoBeneficioEnum getOutTipoBeneficio() {
        return outTipoBeneficio;
    }

    /**
     * Método que retorna el valor de outBeneficioActivo.
     * @return valor de outBeneficioActivo.
     */
    public Boolean getOutBeneficioActivo() {
        return outBeneficioActivo;
    }

    /**
     * Método encargado de modificar el valor de claseAportante.
     * @param valor para modificar claseAportante.
     */
    public void setClaseAportante(String claseAportante) {
        this.claseAportante = claseAportante;
    }

    /**
     * Método encargado de modificar el valor de outTipoBeneficio.
     * @param valor para modificar outTipoBeneficio.
     */
    public void setOutTipoBeneficio(TipoBeneficioEnum outTipoBeneficio) {
        this.outTipoBeneficio = outTipoBeneficio;
    }

    /**
     * Método encargado de modificar el valor de outBeneficioActivo.
     * @param valor para modificar outBeneficioActivo.
     */
    public void setOutBeneficioActivo(Boolean outBeneficioActivo) {
        this.outBeneficioActivo = outBeneficioActivo;
    }

    /**
     * Método que retorna el valor de tipoPersona.
     * @return valor de tipoPersona.
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoPersona.
     * @param valor para modificar tipoPersona.
     */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }    
}
