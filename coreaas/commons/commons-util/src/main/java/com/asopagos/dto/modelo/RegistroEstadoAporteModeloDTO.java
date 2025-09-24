/**
 * 
 */
package com.asopagos.dto.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.aportes.RegistroEstadoAporte;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.comunicados.MedioComunicadoEnum;

/**
 * Clase DTO que contiene los datos de trazabilidad para diversas acciones
 * realizadas en el sistema.
 * 
 * @author Cristian David Parra Zuluaga <criparra@heinsohn.com.co>
 *
 */
@XmlRootElement
public class RegistroEstadoAporteModeloDTO {

	/**
	 * Código identificador de llave primaria del registro con el estado del
	 * aporte.
	 */
	private Long idRegistroEstadoAporte;

	/**
	 * Número de la solicitud de devolución de aportes
	 */
	private Long idSolicitud;

	/**
	 * Tipo de actividad realizada que generó la traza.
	 */
	private ActividadEnum actividadRealizada;

	/**
	 * Estado de la solicitud al finalizar la actividad realizada.
	 */
	private EstadoSolicitudAporteEnum estadoFinalSolicitud;

	/**
	 * Variable que registra la fecha y hora del cambio de estado por una
	 * solicitud realizada.
	 */
	private Long fechaHoraCambio;

	/**
	 * Variable que representa los comunicados que se enviaron.
	 */
	private Long idComunicado;

	/**
	 * Nombre del usuario que realizó la actividad sobre la solicitud de
	 * traslado de aportes.
	 */
	private String usuario;
	
	   /**
     * Nombre del usuario que realizó la actividad sobre la solicitud de
     * traslado de aportes.
     */
    private String destinatario;
    
    /**
     * Nombre del usuario que realizó la actividad sobre la solicitud de
     * traslado de aportes.
     */
    private String idEcm;

    /**
     * Descripción del medio de comunicado
     */
    private MedioComunicadoEnum medioComunicado;
    
	/**
	 * Contructor por defecto
	 */
	public RegistroEstadoAporteModeloDTO() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param idSolicitud
	 *            Identificador de la solicitud global
	 * @param actividadRealizada
	 *            Actividad realizada
	 * @param estadoFinalSolicitud
	 *            Estado siguiente de la solicitud de aportes
	 * @param fechaHoraCambio
	 *            Fecha y hora del cambio de estado
	 * @param idComunicado
	 *            Identificador único del comunicado enviado durante el cambio
	 *            de estado
	 * @param usuario
	 *            Usuario que realizó el cambio de estado
	 */
	public RegistroEstadoAporteModeloDTO(Long idSolicitud, ActividadEnum actividadRealizada, EstadoSolicitudAporteEnum estadoFinalSolicitud, Long fechaHoraCambio, Long idComunicado, String usuario) {
		super();
		this.idSolicitud = idSolicitud;
		this.actividadRealizada = actividadRealizada;
		this.estadoFinalSolicitud = estadoFinalSolicitud;
		this.fechaHoraCambio = fechaHoraCambio;
		this.idComunicado = idComunicado;
		this.usuario = usuario;
	}

	/**
	 * Método que convierte la entidad a una Traza.
	 * 
	 * @param trazabilidad
	 *            representada en forma de entidad.
	 */
	public void convertToDTO(RegistroEstadoAporte registroEstadoAporte) {

		this.setIdRegistroEstadoAporte(registroEstadoAporte.getIdRegistroEstadoAporte());
		this.setIdSolicitud(registroEstadoAporte.getIdSolicitud());
		this.setActividadRealizada(registroEstadoAporte.getActividadRealizada());
		this.setEstadoFinalSolicitud(registroEstadoAporte.getEstadoFinalSolicitud());
		if (registroEstadoAporte.getFechaHoraCambio() != null) {
			this.setFechaHoraCambio(registroEstadoAporte.getFechaHoraCambio().getTime());
		}
		this.setIdComunicado(registroEstadoAporte.getIdComunicado());
		this.setUsuario(registroEstadoAporte.getUsuario());
	}

	/**
	 * Método que convierte de DTO a una Entidad.
	 * 
	 * @return trazabilidad convertida.
	 */
	public RegistroEstadoAporte convertToEntity() {

		RegistroEstadoAporte registroEstadoAporte = new RegistroEstadoAporte();
		registroEstadoAporte.setIdRegistroEstadoAporte(this.getIdRegistroEstadoAporte());
		registroEstadoAporte.setIdSolicitud(this.getIdSolicitud());
		registroEstadoAporte.setActividadRealizada(this.getActividadRealizada());
		registroEstadoAporte.setEstadoFinalSolicitud(this.getEstadoFinalSolicitud());
		if (this.getFechaHoraCambio() != null) {
			registroEstadoAporte.setFechaHoraCambio(new Date(this.getFechaHoraCambio()));
		}
		registroEstadoAporte.setIdComunicado(this.getIdComunicado());
		registroEstadoAporte.setUsuario(this.getUsuario());
		return registroEstadoAporte;
	}

	/**
	 * Copia los datos del DTO a la Entidad.
	 * 
	 * @param trazabilidad
	 *            previamente consultada.
	 */
	public RegistroEstadoAporte copyDTOToEntity(RegistroEstadoAporte registroEstadoAporte) {
		if (this.getIdRegistroEstadoAporte() != null) {
			registroEstadoAporte.setIdRegistroEstadoAporte(this.getIdRegistroEstadoAporte());
		}
		if (this.getIdSolicitud() != null) {
			registroEstadoAporte.setIdSolicitud(this.getIdSolicitud());
		}
		if (this.getActividadRealizada() != null) {
			registroEstadoAporte.setActividadRealizada(this.getActividadRealizada());
		}
		if (this.getEstadoFinalSolicitud() != null) {
			registroEstadoAporte.setEstadoFinalSolicitud(this.getEstadoFinalSolicitud());
		}
		if (this.getFechaHoraCambio() != null) {
			registroEstadoAporte.setFechaHoraCambio(new Date(this.getFechaHoraCambio()));
		}
		if (this.getIdComunicado() != null) {
			registroEstadoAporte.setIdComunicado(this.getIdComunicado());
		}
		if (this.getUsuario() != null) {
			registroEstadoAporte.setUsuario(this.getUsuario());
		}
		return registroEstadoAporte;

	}

	/**
	 * @return the idRegistroEstadoAporte
	 */
	public Long getIdRegistroEstadoAporte() {
		return idRegistroEstadoAporte;
	}

	/**
	 * @param idRegistroEstadoAporte
	 *            the idRegistroEstadoAporte to set
	 */
	public void setIdRegistroEstadoAporte(Long idRegistroEstadoAporte) {
		this.idRegistroEstadoAporte = idRegistroEstadoAporte;
	}

	/**
	 * Método que retorna el valor de idSolicitud.
	 * 
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * 
	 * @param valor
	 *            para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de actividadRealizada.
	 * 
	 * @return valor de actividadRealizada.
	 */
	public ActividadEnum getActividadRealizada() {
		return actividadRealizada;
	}

	/**
	 * Método encargado de modificar el valor de actividadRealizada.
	 * 
	 * @param valor
	 *            para modificar actividadRealizada.
	 */
	public void setActividadRealizada(ActividadEnum actividadRealizada) {
		this.actividadRealizada = actividadRealizada;
	}


	/**
	 * Método que retorna el valor de estadoFinalSolicitud.
	 * 
	 * @return valor de estadoFinalSolicitud.
	 */
	public EstadoSolicitudAporteEnum getEstadoFinalSolicitud() {
		return estadoFinalSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de estadoFinalSolicitud.
	 * 
	 * @param valor
	 *            para modificar estadoFinalSolicitud.
	 */
	public void setEstadoFinalSolicitud(EstadoSolicitudAporteEnum estadoFinalSolicitud) {
		this.estadoFinalSolicitud = estadoFinalSolicitud;
	}

	/**
	 * Método que retorna el valor de fechaHoraCambio.
	 * 
	 * @return valor de fechaHoraCambio.
	 */
	public Long getFechaHoraCambio() {
		return fechaHoraCambio;
	}

	/**
	 * Método encargado de modificar el valor de fechaHoraCambio.
	 * 
	 * @param valor
	 *            para modificar fechaHoraCambio.
	 */
	public void setFechaHoraCambio(Long fechaHoraCambio) {
		this.fechaHoraCambio = fechaHoraCambio;
	}

	/**
	 * Método que retorna el valor de idComunicado.
	 * 
	 * @return valor de idComunicado.
	 */
	public Long getIdComunicado() {
		return idComunicado;
	}

	/**
	 * Método encargado de modificar el valor de idComunicado.
	 * 
	 * @param valor
	 *            para modificar idComunicado.
	 */
	public void setIdComunicado(Long idComunicado) {
		this.idComunicado = idComunicado;
	}

	/**
	 * Método que retorna el valor de usuario.
	 * 
	 * @return valor de usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método encargado de modificar el valor de usuario.
	 * 
	 * @param valor
	 *            para modificar usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario the destinatario to set
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @return the idEcm
     */
    public String getIdEcm() {
        return idEcm;
    }

    /**
     * @param idEcm the idEcm to set
     */
    public void setIdEcm(String idEcm) {
        this.idEcm = idEcm;
    }

    /**
     * @return the medioComunicado
     */
    public MedioComunicadoEnum getMedioComunicado() {
        return medioComunicado;
    }

    /**
     * @param medioComunicado the medioComunicado to set
     */
    public void setMedioComunicado(MedioComunicadoEnum medioComunicado) {
        this.medioComunicado = medioComunicado;
    }
}
