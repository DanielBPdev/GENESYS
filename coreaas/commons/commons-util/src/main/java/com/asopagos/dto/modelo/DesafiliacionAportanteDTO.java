package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
@XmlRootElement
public class DesafiliacionAportanteDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación.
     */
    private String numeroIdentificacion;
    /**
     * Nombre completo o razón social.
     */
    private String nombreRazonSocial;
    /**
     * Tipo de aportante/solicitante.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    /**
     * Estado de cartera.
     */
    private EstadoCarteraEnum estadoCartera;
    /**
     * Deuda.
     */
    private TipoDeudaEnum deuda;
    /**
     * Monto de deuda.
     */
    private BigDecimal monto;
    /**
     * Tipo de deuda.
     */
    private String tipoDeuda;
    /**
     * Tipo de línea de cobro
     */
    private TipoLineaCobroEnum tipoLineaCobro;
    /**
     * Id de la persona.
     */
    private Long idPersona;
    /**
     * Se obtiene el identificador de cartera que representaría el número de operación para BitacoraCartera
     */
    private String idCartera;
    
    /** Número de agrupación de las carteras */
    private Long numeroOperacion;

    /**
     * Constructor de la clase
     */
    public DesafiliacionAportanteDTO() {
    }

    /**
     * Constructor de la clase para la querie
     */
    public DesafiliacionAportanteDTO(Persona p, Cartera c) {

        /* Se setean los valores */
        this.setTipoIdentificacion(p.getTipoIdentificacion() != null ? p.getTipoIdentificacion() : null);
        this.setNumeroIdentificacion(p.getNumeroIdentificacion() != null ? p.getNumeroIdentificacion() : null);
        this.setNombreRazonSocial(p.getRazonSocial() != null ? p.getRazonSocial() : null);
        if (this.getNombreRazonSocial() == null && !TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(c.getTipoSolicitante())) {
            /* Se construye el nombre completo ya sea independiente o pensionado */
            this.setNombreRazonSocial(generarNombreCompleto(p));
        }
        this.setTipoSolicitante(c.getTipoSolicitante() != null ? c.getTipoSolicitante() : null);
        this.setDeuda(c.getTipoDeuda() != null ? c.getTipoDeuda() : null);
        this.setMonto(c.getDeudaPresunta() != null ? c.getDeudaPresunta() : null);
        //TODO: para ese tipo de deuda queda pendiente no se sabe como traer
        this.setTipoDeuda("Deuda presunta");
        this.setTipoLineaCobro(c.getTipoLineaCobro() != null ? c.getTipoLineaCobro() : null);
        this.setIdPersona(p.getIdPersona());
    }
    
    /**
     * Metodo que sirve para construir el nombre completo de la persona cuando este no tenga razon social
     * @param p parametro que recibe la persona que no posee razon social
     * @return el nombre completo
     */
    private String generarNombreCompleto(Persona p) {

        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(p.getPrimerNombre() + " ");
        nombreCompleto.append(p.getSegundoNombre() != null ? p.getSegundoNombre() : "");
        nombreCompleto.append(p.getPrimerApellido() + " ");
        nombreCompleto.append(p.getSegundoApellido() != null ? p.getSegundoApellido() : "");
        return nombreCompleto.toString();
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
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
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
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
     * Método que retorna el valor de nombreRazonSocial.
     * @return valor de nombreRazonSocial.
     */
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    /**
     * Método encargado de modificar el valor de nombreRazonSocial.
     * @param valor
     *        para modificar nombreRazonSocial.
     */
    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de estadoCartera.
     * @return valor de estadoCartera.
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoCartera.
     * @param valor
     *        para modificar estadoCartera.
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Método que retorna el valor de deuda.
     * @return valor de deuda.
     */
    public TipoDeudaEnum getDeuda() {
        return deuda;
    }

    /**
     * Método encargado de modificar el valor de deuda.
     * @param valor
     *        para modificar deuda.
     */
    public void setDeuda(TipoDeudaEnum deuda) {
        this.deuda = deuda;
    }

    /**
     * Método que retorna el valor de monto.
     * @return valor de monto.
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Método encargado de modificar el valor de monto.
     * @param valor
     *        para modificar monto.
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * Método que retorna el valor de tipoDeuda.
     * @return valor de tipoDeuda.
     */
    public String getTipoDeuda() {
        return tipoDeuda;
    }

    /**
     * Método encargado de modificar el valor de tipoDeuda.
     * @param valor
     *        para modificar tipoDeuda.
     */
    public void setTipoDeuda(String tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    /**
     * Método que retorna el valor de tipoLineaCobro.
     * @return valor de tipoLineaCobro.
     */
    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    /**
     * Método encargado de modificar el valor de tipoLineaCobro.
     * @param valor
     *        para modificar tipoLineaCobro.
     */
    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the idCartera
     */
    public String getIdCartera() {
        return idCartera;
    }

    /**
     * @param idCartera the idCartera to set
     */
    public void setIdCartera(String idCartera) {
        this.idCartera = idCartera;
    }

	/**
	 * @return the numeroOperacion
	 */
	public Long getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * @param numeroOperacion the numeroOperacion to set
	 */
	public void setNumeroOperacion(Long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
    
}
