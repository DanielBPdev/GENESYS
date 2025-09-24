package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;

/**
 * <b>Descripción:</b> DTO que contiene los datos de una Solicitud de Afiliación
 * Persona
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class SolicitudAfiliacionPersonaModeloDTO extends SolicitudModeloDTO implements Serializable{

	/**
	 * Código identificador de la solicitud de afiliación de la persona
	 */
	private Long idSolicitudAfiliacionPersona;

	/**
	 * Id que identifica el rol afiliado asociada a la afiliación de la persona
	 */
	private Long idRolAfiliado;

	/**
	 * Descripción del estado de la solicitud
	 */
	private EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud;

	 /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public SolicitudAfiliacionPersona convertToEntity(){
    	SolicitudAfiliacionPersona solicitudAfiliacionPersona = new SolicitudAfiliacionPersona();
    	solicitudAfiliacionPersona.setSolicitudGlobal(this.convertToSolicitudEntity());
    	solicitudAfiliacionPersona.setEstadoSolicitud(this.getEstadoSolicitud());
    	solicitudAfiliacionPersona.setIdSolicitudAfiliacionPersona(this.getIdSolicitudAfiliacionPersona());
    	if (this.getIdRolAfiliado() != null) {
    	    RolAfiliado rolAfiliado = new RolAfiliado();
            rolAfiliado.setIdRolAfiliado(this.getIdRolAfiliado());
            solicitudAfiliacionPersona.setRolAfiliado(rolAfiliado);
        }
    	return solicitudAfiliacionPersona;
    }
    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitudAfiliacionPersona entidad a convertir.
     */
    public void convertToDTO(SolicitudAfiliacionPersona solicitudAfiliacionPersona){
    	super.convertToDTO(solicitudAfiliacionPersona.getSolicitudGlobal());
		this.setEstadoSolicitud(solicitudAfiliacionPersona.getEstadoSolicitud());
		this.setIdRolAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getIdRolAfiliado());
		this.setIdSolicitudAfiliacionPersona(solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona());
		
    }
    
    /**
     * Método encargado de copiar un DTO  a una Entidad.
     * @param solicitudAfiliacionPersona previamente consultado.
     */
	public SolicitudAfiliacionPersona  copyDTOToEntiy(SolicitudAfiliacionPersona solicitudAfiliacionPersona) {
		if(this.getIdSolicitudAfiliacionPersona() != null) {
			solicitudAfiliacionPersona.setIdSolicitudAfiliacionPersona(this.getIdSolicitudAfiliacionPersona());
		}
		if (this.getEstadoSolicitud() != null) {
			solicitudAfiliacionPersona.setEstadoSolicitud(this.getEstadoSolicitud());
		}
		if (this.getIdRolAfiliado() != null) {
		    RolAfiliado rolAfiliado = new RolAfiliado();
            rolAfiliado.setIdRolAfiliado(this.getIdRolAfiliado());
            solicitudAfiliacionPersona.setRolAfiliado(rolAfiliado);
		}
		if (this.getIdSolicitud() != null) {
			solicitudAfiliacionPersona.setSolicitudGlobal(super.convertToSolicitudEntity());
		}
		return solicitudAfiliacionPersona;
	}
	
	/**
	 * @return the idSolicitudAfiliacionPersona
	 */
	public Long getIdSolicitudAfiliacionPersona() {
		return idSolicitudAfiliacionPersona;
	}

	/**
	 * @param idSolicitudAfiliacionPersona the idSolicitudAfiliacionPersona to set
	 */
	public void setIdSolicitudAfiliacionPersona(Long idSolicitudAfiliacionPersona) {
		this.idSolicitudAfiliacionPersona = idSolicitudAfiliacionPersona;
	}

	/**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }
    
    /**
     * @param idRolAfiliado the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }
    
    /**
	 * @return the estadoSolicitud
	 */
	public EstadoSolicitudAfiliacionPersonaEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

}
