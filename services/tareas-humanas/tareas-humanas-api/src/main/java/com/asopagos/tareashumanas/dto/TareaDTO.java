package com.asopagos.tareashumanas.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;

/**
 * <b>Descripción:</b> DTO para la transmisión de los datos de una tarea dentro
 * del contexto del BPM
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class TareaDTO implements Serializable {
    
    
    private Long id;
    
    private Long idInstanciaProceso;
    
    private String deploymentParamName;
    
    private String nombreProceso;
    
    private String nombre;
    
    private String descripcion;
    
    private String propietario;
    
    private Date fechaCreacion;
    
    private Date fechaExpiracion;
    
    private EstadoTareaEnum estado;
    
    private String idDefincionProceso;
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the propietario
     */
    public String getPropietario() {
        return propietario;
    }

    /**
     * @param propietario the propietario to set
     */
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    /**
     * @return the estado
     */
    public EstadoTareaEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoTareaEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the nombreProceso
     */
    public String getNombreProceso() {
        return nombreProceso;
    }

    /**
     * @param nombreProceso the nombreProceso to set
     */
    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaExpiracion
     */
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * @param fechaExpiracion the fechaExpiracion to set
     */
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

	/**
	 * @return the idDefincionProceso
	 */
	public String getIdDefincionProceso() {
		return idDefincionProceso;
	}

	/**
	 * @param idDefincionProceso the idDefincionProceso to set
	 */
	public void setIdDefincionProceso(String idDefincionProceso) {
		this.idDefincionProceso = idDefincionProceso;
	}

    /**
     * @return the deploymentParamName
     */
    public String getDeploymentParamName() {
        return deploymentParamName;
    }

    /**
     * @param deploymentParamName the deploymentParamName to set
     */
    public void setDeploymentParamName(String deploymentParamName) {
        this.deploymentParamName = deploymentParamName;
    }
}
