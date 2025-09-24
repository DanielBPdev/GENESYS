package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de la Deuda
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 27-Febr-2018 12:28:50 p.m.
 */
@XmlRootElement
public class DeudaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5627432910378090901L;
    /**
     * Periodo al que pertenece el periodo 
     */
    private Long periodo;
    /**
     * Deuda perteneciente al periodo 
     */
    private BigDecimal deuda;
    
    /**
     * Tipo de identificación del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación del aportante
     */
    private String numeroIdentificacion;
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
     * Deuda presunta
     */
    private BigDecimal deudaPresunta;
    /**
     * Deuda Real
     */
    private BigDecimal deudaReal;
    /**
     * Estado en periodo del aportante
     */
    private EstadoAfiliadoEnum estadoEnPeriodo;

    /**
     * 
     */
    public DeudaDTO() {
     
    }
    
    /**
     * Método constructor de la deuda 
     * @param periodoDeuda, periodo deuda 
     * @param deuda, valor de la deuda en el periodo 
     */
    public DeudaDTO(Date periodoDeuda, BigDecimal deuda) {
        this.periodo = periodoDeuda !=null ? periodoDeuda.getTime() : null;
        this.deuda = deuda;
    }
    
    /**
     * Constructor perteneciente al detalle de la deuda
     * @param cartera
     * @param persona
     */
    public DeudaDTO(Cartera cartera,Persona persona) {
        this.tipoIdentificacion=persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.primerNombre=persona.getPrimerNombre();
        this.primerApellido=persona.getPrimerApellido();
        this.segundoNombre=persona.getSegundoNombre();
        this.segundoNombre=persona.getSegundoApellido();
        this.deudaPresunta=cartera.getDeudaPresunta();
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
     * Método que retorna el valor de deudaPresunta.
     * @return valor de deudaPresunta.
     */
    public BigDecimal getDeudaPresunta() {
        return deudaPresunta;
    }

    /**
     * Método que retorna el valor de deudaReal.
     * @return valor de deudaReal.
     */
    public BigDecimal getDeudaReal() {
        return deudaReal;
    }

    /**
     * Método que retorna el valor de estadoEnPeriodo.
     * @return valor de estadoEnPeriodo.
     */
    public EstadoAfiliadoEnum getEstadoEnPeriodo() {
        return estadoEnPeriodo;
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

    /**
     * Método encargado de modificar el valor de deudaPresunta.
     * @param valor
     *        para modificar deudaPresunta.
     */
    public void setDeudaPresunta(BigDecimal deudaPresunta) {
        this.deudaPresunta = deudaPresunta;
    }

    /**
     * Método encargado de modificar el valor de deudaReal.
     * @param valor
     *        para modificar deudaReal.
     */
    public void setDeudaReal(BigDecimal deudaReal) {
        this.deudaReal = deudaReal;
    }

    /**
     * Método encargado de modificar el valor de estadoEnPeriodo.
     * @param valor
     *        para modificar estadoEnPeriodo.
     */
    public void setEstadoEnPeriodo(EstadoAfiliadoEnum estadoEnPeriodo) {
        this.estadoEnPeriodo = estadoEnPeriodo;
    }

    /**
     * Método que retorna el valor de periodo.
     * @return valor de periodo.
     */
    public Long getPeriodo() {
        return periodo;
    }

    /**
     * Método que retorna el valor de deuda.
     * @return valor de deuda.
     */
    public BigDecimal getDeuda() {
        return deuda;
    }

    /**
     * Método encargado de modificar el valor de periodo.
     * @param valor para modificar periodo.
     */
    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    /**
     * Método encargado de modificar el valor de deuda.
     * @param valor para modificar deuda.
     */
    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

}