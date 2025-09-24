package com.asopagos.usuarios.dto;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO que transporta los de ingreso de un Afiliado
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class InformacionReenvioDTO implements Serializable{

	private String numeroIdentificacion;
	
	private TipoIdentificacionEnum tipoIdentificacion;
	
	private Short digitoVerificacion;

	private String correoDestinatario;

	private Map<String, String> parametrosNotificacion;

	private EtiquetaPlantillaComunicadoEnum notificacion;
	
	private Long idSolicitud;

	/**
	 * @return the correoDestinatario
	 */
	public String getCorreoDestinatario() {
		return correoDestinatario;
	}

	/**
	 * @param correoDestinatario
	 *            the correoDestinatario to set
	 */
	public void setCorreoDestinatario(String correoDestinatario) {
		this.correoDestinatario = correoDestinatario;
	}

	/**
	 * @return the parametrosNotificacion
	 */
	public Map<String, String> getParametrosNotificacion() {
		return parametrosNotificacion;
	}

	/**
	 * @param parametrosNotificacion
	 *            the parametrosNotificacion to set
	 */
	public void setParametrosNotificacion(Map<String, String> parametrosNotificacion) {
		this.parametrosNotificacion = parametrosNotificacion;
	}

	/**
	 * @return the notificacion
	 */
	public EtiquetaPlantillaComunicadoEnum getNotificacion() {
		return notificacion;
	}

	/**
	 * @param notificacion
	 *            the notificacion to set
	 */
	public void setNotificacion(EtiquetaPlantillaComunicadoEnum notificacion) {
		this.notificacion = notificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion
	 *            the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion
	 *            the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the digitoVerificacion
	 */
	public Short getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * @param digitoVerificacion
	 *            the digitoVerificacion to set
	 */
	public void setDigitoVerificacion(Short digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
