package com.asopagos.solicitud.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> Entidad que representa los datos necesarios para representar una solicitud
 * que va a ser cerrada <b>Historia de Usuario: </b>Solicitudes Simultaneas
 * 
 * @author Jerson Zambrano <jzambrano@heinsohn.com.co>
 */
@XmlRootElement
public class DatosAbortarSolicitudDTO  implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private TipoIdentificacionEnum tipoIdentificacion;

	private String numeroIdentificacion;

	private TipoSolicitudEnum tipoSolicitud;

	
	public DatosAbortarSolicitudDTO() {
		// TODO Auto-generated constructor stub
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
	 * @return the tipoSolicitud
	 */
	public TipoSolicitudEnum getTipoSolicitud() {
		return tipoSolicitud;
	}


	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
