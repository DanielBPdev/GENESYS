package com.asopagos.novedades.dto;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * ReporteAfiliadosSupervivencia
 * 
 * @author Alex
 */
public class DatosReporteAfiliadosSupervivenciaDTO {
        /**
     * Variable codigo
     */
    private String codigo;
    
    /**
     * Tipo identificacion de la persona para una novedad de personas..
     */
    private String tipoIdentificacion;
    
    /**
     * Número identificacion de la persona para una novedad de personas.
     */
    private String numeroIdentificacion;
	/**
     * Número identificacion de la persona para una novedad de personas.
     */
    private String codigoEntidad;
		/**
     * Número identificacion de la persona para una novedad de personas.
     */
    private String fechaDescarga;

	/** Número de identificacion NIT */

	private String numeroEntidad;
    

    /**
     * @return the idPersona
     */
    public String getCodigo() {
        return codigo;
    }
	public DatosReporteAfiliadosSupervivenciaDTO() {
    }
	public DatosReporteAfiliadosSupervivenciaDTO(String codigo, String tipoIdentificacion, String numeroIdentificacion, String codigoEntidad, String fechaDescarga) {
        this.codigo = codigo;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
		this.codigoEntidad = codigoEntidad;
		this.fechaDescarga = fechaDescarga;
    }
    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * @return valor de tipoIdentificacion.
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * @param valor para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
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
	 * @param valor para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
		/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * @return valor de numeroIdentificacion.
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	/**
	 * Método encargado de modificar el valor de codigoEntidad.
	 * @param valor para modificar codigoEntidad.
	 */
	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}
		/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * @return valor de numeroIdentificacion.
	 */
	public String getFechaDescarga() {
		return fechaDescarga;
	}

	/**
	 * Método encargado de modificar el valor de fechaDescarga.
	 * @param valor para modificar fechaDescarga.
	 */
	public void setFechaDescarga(String fechaDescarga) {
		this.fechaDescarga = fechaDescarga;
	}


	public String getNumeroEntidad() {
		return this.numeroEntidad;
	}

	public void setNumeroEntidad(String numeroEntidad) {
		this.numeroEntidad = numeroEntidad;
	}

}
