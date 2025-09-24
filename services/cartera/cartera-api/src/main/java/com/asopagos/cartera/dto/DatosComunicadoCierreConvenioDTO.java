package com.asopagos.cartera.dto;

import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DatosComunicadoCierreConvenioDTO {
    
    /* Constante para espacios */
    private static final String ESPACIO = " ";
    /* nombreCompleto de la persona o empleador */
    private String nombreCompleto;
    /* razonSocial de la persona o empleador */
    private String razonSocial;
    /* direccionRepresentanteLegal de la persona o empleador */
    private String direccionRepresentanteLegal;
    /* telefonoRepresentanteLegal de la persona o empleador */
    private String telefonoRepresentanteLegal;
    /* ciudadRepresentanteLegal de la persona o empleador */
    private String ciudadRepresentanteLegal;
    
    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;
    
    /**
     * Identificador del empleador en caso de ser parte del proceso
     */
    private Long idEmpleador;

    /**
     * Identificador del convenio de pago
     */
    private Long numeroConvenio;
    
    /**
     * Constructor de la clase
     */
    public DatosComunicadoCierreConvenioDTO() {
    }
    
    /**
     * Constructor para empleador
     */
    public DatosComunicadoCierreConvenioDTO(Persona p, Empresa e, Empleador em, Ubicacion u , Municipio m) {
        nombreCompleto = construirNombreCompleto(p).toString();
        razonSocial = p.getRazonSocial();
        direccionRepresentanteLegal = u.getDescripcionIndicacion();
        telefonoRepresentanteLegal = (u.getTelefonoCelular() != null ? u.getTelefonoCelular() : u.getTelefonoFijo()); 
        ciudadRepresentanteLegal = m.getNombre();
        tipoIdentificacion = p.getTipoIdentificacion();
        numeroIdentificacion = p.getNumeroIdentificacion();
        idEmpleador = em.getIdEmpleador();
    }

    /**
     * Constructor para persona
     */
    public DatosComunicadoCierreConvenioDTO(Persona p, Ubicacion u , Municipio m) {
        nombreCompleto = construirNombreCompleto(p).toString();
        razonSocial = p.getRazonSocial();
        direccionRepresentanteLegal = u.getDescripcionIndicacion();
        telefonoRepresentanteLegal = (u.getTelefonoCelular() != null ? u.getTelefonoCelular() : u.getTelefonoFijo()); 
        ciudadRepresentanteLegal = m.getNombre();
        tipoIdentificacion = p.getTipoIdentificacion();
        numeroIdentificacion = p.getNumeroIdentificacion();
    }
    
    /**
     * Metodo que construye el nombre completo de la persona ó empleador
     */
    private StringBuilder construirNombreCompleto(Persona p) {
        StringBuilder nombreCompletoString = new StringBuilder();
        nombreCompletoString.append(p.getPrimerNombre());
        nombreCompletoString.append(ESPACIO);
        nombreCompletoString.append(p.getSegundoNombre() != null ? p.getSegundoNombre() : "");
        nombreCompletoString.append(ESPACIO);
        nombreCompletoString.append(p.getPrimerApellido());
        nombreCompletoString.append(ESPACIO);
        nombreCompletoString.append(p.getSegundoApellido() != null ? p.getSegundoApellido() : "");
        return nombreCompletoString;
    }

    /**
     * Método que retorna el valor de nombreCompleto.
     * @return valor de nombreCompleto.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Método encargado de modificar el valor de nombreCompleto.
     * @param valor para modificar nombreCompleto.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Método que retorna el valor de razonSocial.
     * @return valor de razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Método encargado de modificar el valor de razonSocial.
     * @param valor para modificar razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Método que retorna el valor de direccionRepresentanteLegal.
     * @return valor de direccionRepresentanteLegal.
     */
    public String getDireccionRepresentanteLegal() {
        return direccionRepresentanteLegal;
    }

    /**
     * Método encargado de modificar el valor de direccionRepresentanteLegal.
     * @param valor para modificar direccionRepresentanteLegal.
     */
    public void setDireccionRepresentanteLegal(String direccionRepresentanteLegal) {
        this.direccionRepresentanteLegal = direccionRepresentanteLegal;
    }

    /**
     * Método que retorna el valor de telefonoRepresentanteLegal.
     * @return valor de telefonoRepresentanteLegal.
     */
    public String getTelefonoRepresentanteLegal() {
        return telefonoRepresentanteLegal;
    }

    /**
     * Método encargado de modificar el valor de telefonoRepresentanteLegal.
     * @param valor para modificar telefonoRepresentanteLegal.
     */
    public void setTelefonoRepresentanteLegal(String telefonoRepresentanteLegal) {
        this.telefonoRepresentanteLegal = telefonoRepresentanteLegal;
    }

    /**
     * Método que retorna el valor de ciudadRepresentanteLegal.
     * @return valor de ciudadRepresentanteLegal.
     */
    public String getCiudadRepresentanteLegal() {
        return ciudadRepresentanteLegal;
    }

    /**
     * Método encargado de modificar el valor de ciudadRepresentanteLegal.
     * @param valor para modificar ciudadRepresentanteLegal.
     */
    public void setCiudadRepresentanteLegal(String ciudadRepresentanteLegal) {
        this.ciudadRepresentanteLegal = ciudadRepresentanteLegal;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

    /**
     * @return the numeroConvenio
     */
    public Long getNumeroConvenio() {
        return numeroConvenio;
    }

    /**
     * @param numeroConvenio the numeroConvenio to set
     */
    public void setNumeroConvenio(Long numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }
}
