package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.subsidiomonetario.pagos.DescuentosSubsidioAsignado;

/**
 * <b>Descripcion:</b> Clase DTO que representa el descuento de subisdio asignado <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 -201<br/>
 *
 * @author <a href="flopez:flopez@heinsohn.com.co"> Fabian López</a>
 */
@XmlRootElement
public class DescuentoSubsidioAsignadoDTO implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 4194410649323554823L;

    /**
     * Identificador de la base de datos del detalle del subsidio asignado
     */
    private Long idEntidadDescuento;
    
    /**
     * Identificador de la base de datos del detalle del subsidio asignado
     */
    private Long codigoConvenio;

    /**
     * Nombre de usuario que crea el registro del detalle
     */
    private String nombreConvenio;

    /**
     * Monto descontado del subsidio
     */
    private BigDecimal montoDescontado;

    /**
     * Nombre OUT del archivo
     */
    private String nombreOUT;

    /**
     * Fecha de cargue del archivo
     */
    private Date fechaCargue;

    /**
     * Codigo Referencia Pdte CC706
     */
    private String codigoReferencia;
    
    /**
     * Numero Radicado de la solicitud
     */
    private String numeroRadicado;

    

    /**
     * Constructor vacio
     */
    public DescuentoSubsidioAsignadoDTO() {
    }

    /**
     * Constructor que convierte una instancia de DetalleSubsidioAsignadoDTO a partir de la entidad.
     * @param detalleSubsidioAsignado
     *        entidad del detalle de subsidio asignado
     */
    public DescuentoSubsidioAsignadoDTO(DescuentosSubsidioAsignado descuentosSubsidioAsignado) {

      
    }
    
    /**
     * Metodo que convierte un DTO a un entity
     * 
     * @return entity de detalle subsidio asignado
     */
    public DescuentosSubsidioAsignado convertToEntity() {
 
       
        return null;
    }

    /**
     * Metodo que permite clonar un objeto
     * 
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public DescuentoSubsidioAsignadoDTO clone() {
        DescuentoSubsidioAsignadoDTO obj = null;
        try {
            obj = (DescuentoSubsidioAsignadoDTO) super.clone();
        } catch (CloneNotSupportedException ex) {

        }
        return obj;
    }

    /**
	 * @return the codigoConvenio
	 */
	public Long getCodigoConvenio() {
		return codigoConvenio;
	}

	/**
	 * @param codigoConvenio the codigoConvenio to set
	 */
	public void setCodigoConvenio(Long codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	/**
	 * @return the idEntidadDescuento
	 */
	public Long getIdEntidadDescuento() {
		return idEntidadDescuento;
	}

	/**
	 * @param idEntidadDescuento the idEntidadDescuento to set
	 */
	public void setIdEntidadDescuento(Long idEntidadDescuento) {
		this.idEntidadDescuento = idEntidadDescuento;
	}

	/**
	 * @return the nombreConvenio
	 */
	public String getNombreConvenio() {
		return nombreConvenio;
	}

	/**
	 * @param nombreConvenio the nombreConvenio to set
	 */
	public void setNombreConvenio(String nombreConvenio) {
		this.nombreConvenio = nombreConvenio;
	}

	/**
	 * @return the montoDescontado
	 */
	public BigDecimal getMontoDescontado() {
		return montoDescontado;
	}

	/**
	 * @param montoDescontado the montoDescontado to set
	 */
	public void setMontoDescontado(BigDecimal montoDescontado) {
		this.montoDescontado = montoDescontado;
	}

	/**
	 * @return the nombreOUT
	 */
	public String getNombreOUT() {
		return nombreOUT;
	}

	/**
	 * @param nombreOUT the nombreOUT to set
	 */
	public void setNombreOUT(String nombreOUT) {
		this.nombreOUT = nombreOUT;
	}

	/**
	 * @return the fechaCargue
	 */
	public Date getFechaCargue() {
		return fechaCargue;
	}

	/**
	 * @param fechaCargue the fechaCargue to set
	 */
	public void setFechaCargue(Date fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	/**
	 * @return the codigoReferencia
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia the codigoReferencia to set
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DescuentoSubsidioAsignadoDTO [codigoConvenio=");
		builder.append(codigoConvenio);
		builder.append(", nombreConvenio=");
		builder.append(nombreConvenio);
		builder.append(", montoDescontado=");
		builder.append(montoDescontado);
		builder.append(", nombreOUT=");
		builder.append(nombreOUT);
		builder.append(", fechaCargue=");
		builder.append(fechaCargue);
		builder.append(", codigoReferencia=");
		builder.append(codigoReferencia);
		builder.append("]");
		return builder.toString();
	}

    
    
}
