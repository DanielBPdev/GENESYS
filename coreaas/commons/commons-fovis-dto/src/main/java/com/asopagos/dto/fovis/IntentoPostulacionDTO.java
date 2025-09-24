package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.fovis.IntentoPostulacion;
import com.asopagos.entidades.ccf.fovis.IntentoPostulacionRequisito;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * Clase DTO para el intento de Postulación.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class IntentoPostulacionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1787512454005454L;

	/**
	 * Código identificador de llave primaria del intento de afiliación
	 */
	private Long idIntentoPostulacion;

	/**
	 * Referencia a la solicitud asociada al intento de postulación
	 */
	private Long idSolicitud;

	/**
	 * Descripción de la causa por la que un intento de afiliación no resulta
	 * exitoso
	 */
	private CausaIntentoFallidoPostulacionEnum causaIntentoFallido;

	/**
	 * Descripción del tipo de transacción del proceso
	 */
	private TipoTransaccionEnum tipoTransaccion;

	/**
	 * Descripción de la sede de caja de compensación
	 */
	private String sedeCajaCompensacion;

	/**
	 * Fecha de inicio del proceso
	 */
	private Date fechaInicioProceso;

	/**
	 * Indica el proceso de negocio por el cual se registra el intento de
	 * postulación
	 */
	private ProcesoEnum proceso;

	/**
	 * Indica el tipo de solicitante en la postulación. Por defecto, será
	 * <b>HOGAR</b>
	 */
	private String tipoSolicitante;

	/**
	 * Indica la modalidad de vivienda registrado en la postulación FOVIS
	 */
	private ModalidadEnum modalidad;

	/**
	 * Descripción del requisito de intento de afiliación
	 */
	private List<IntentoPostulacionRequisito> requsitos;

	/**
	 * Fecha creación
	 */
	private Date fechaCreacion;

	/**
	 * Usuario de creación
	 */
	private String usuarioCreacion;

	/**
	 * Método encargado de convertir de DTO a Entidad.
	 * 
	 * @return entidad convertida.
	 */
	public IntentoPostulacion convertToIntentoPostulacionEntity() {
		IntentoPostulacion intentoPostulacion = new IntentoPostulacion();
		intentoPostulacion.setCausaIntentoFallido(this.getCausaIntentoFallido());
		intentoPostulacion.setFechaCreacion(this.getFechaCreacion());
		intentoPostulacion.setFechaInicioProceso(this.getFechaInicioProceso());
		intentoPostulacion.setIdIntentoPostulacion(this.getIdIntentoPostulacion());
		intentoPostulacion.setIdSolicitud(this.getIdSolicitud());
		intentoPostulacion.setRequsitos(this.getRequsitos());
		intentoPostulacion.setSedeCajaCompensacion(this.getSedeCajaCompensacion());
		intentoPostulacion.setTipoTransaccion(this.getTipoTransaccion());
		intentoPostulacion.setUsuarioCreacion(this.getUsuarioCreacion());
		intentoPostulacion.setProceso(this.getProceso());
		intentoPostulacion.setTipoSolicitante(this.getTipoSolicitante());
		intentoPostulacion.setModalidad(this.getModalidad());
		return intentoPostulacion;
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param intentoPostulacion
	 *            entidad a convertir.
	 */
	public void convertToDTO(IntentoPostulacion intentoPostulacion) {
		this.setCausaIntentoFallido(intentoPostulacion.getCausaIntentoFallido());
		this.setFechaCreacion(intentoPostulacion.getFechaCreacion());
		this.setFechaInicioProceso(intentoPostulacion.getFechaInicioProceso());
		this.setIdIntentoPostulacion(intentoPostulacion.getIdIntentoPostulacion());
		this.setRequsitos(intentoPostulacion.getRequsitos());
		this.setIdSolicitud(intentoPostulacion.getIdSolicitud());
		this.setSedeCajaCompensacion(intentoPostulacion.getSedeCajaCompensacion());
		this.setTipoTransaccion(intentoPostulacion.getTipoTransaccion());
		this.setUsuarioCreacion(intentoPostulacion.getUsuarioCreacion());
		this.setProceso(intentoPostulacion.getProceso());
		this.setTipoSolicitante(intentoPostulacion.getTipoSolicitante());
		this.setModalidad(intentoPostulacion.getModalidad());
	}

	/**
	 * Método encargado de copiar un DTO a una Entidad.
	 * 
	 * @param intentoPostulacion
	 *            previamente consultado.
	 */
	public IntentoPostulacion copyDTOToEntiy(IntentoPostulacion intentoPostulacion) {
		if (this.getCausaIntentoFallido() != null) {
			// intentoPostulacion.setCausaIntentoFallido(this.getCausaIntentoFallido());
		}
		if (this.getFechaCreacion() != null) {
			intentoPostulacion.setFechaCreacion(this.getFechaCreacion());
		}
		if (this.getFechaInicioProceso() != null) {
			intentoPostulacion.setFechaInicioProceso(this.getFechaInicioProceso());
		}
		if (this.getIdIntentoPostulacion() != null) {
			intentoPostulacion.setIdIntentoPostulacion(this.getIdIntentoPostulacion());
		}
		if (this.getRequsitos() != null) {
			intentoPostulacion.setRequsitos(this.getRequsitos());
		}
		if (this.getSedeCajaCompensacion() != null) {
			intentoPostulacion.setSedeCajaCompensacion(this.getSedeCajaCompensacion());
		}
		if (this.getIdSolicitud() != null) {
			intentoPostulacion.setIdSolicitud(this.getIdSolicitud());
		}
		if (this.getTipoTransaccion() != null) {
			intentoPostulacion.setTipoTransaccion(this.getTipoTransaccion());
		}
		if (this.getUsuarioCreacion() != null) {
			intentoPostulacion.setUsuarioCreacion(this.getUsuarioCreacion());
		}
		if (this.getProceso() != null) {
			intentoPostulacion.setProceso(this.getProceso());
		}
		if (this.getTipoSolicitante() != null) {
			intentoPostulacion.setTipoSolicitante(this.getTipoSolicitante());
		}
		if (this.getModalidad() != null) {
			intentoPostulacion.setModalidad(this.getModalidad());
		}
		return intentoPostulacion;
	}

	/**
	 * @return the idIntentoPostulacion
	 */
	public Long getIdIntentoPostulacion() {
		return idIntentoPostulacion;
	}

	/**
	 * @param idIntentoPostulacion
	 *            the idIntentoPostulacion to set
	 */
	public void setIdIntentoPostulacion(Long idIntentoPostulacion) {
		this.idIntentoPostulacion = idIntentoPostulacion;
	}

	/**
	 * @return the causaIntentoFallido
	 */
	public CausaIntentoFallidoPostulacionEnum getCausaIntentoFallido() {
		return causaIntentoFallido;
	}

	/**
	 * @param causaIntentoFallido
	 *            the causaIntentoFallido to set
	 */
	public void setCausaIntentoFallido(CausaIntentoFallidoPostulacionEnum causaIntentoFallido) {
		this.causaIntentoFallido = causaIntentoFallido;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the sedeCajaCompensacion
	 */
	public String getSedeCajaCompensacion() {
		return sedeCajaCompensacion;
	}

	/**
	 * @param sedeCajaCompensacion
	 *            the sedeCajaCompensacion to set
	 */
	public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
		this.sedeCajaCompensacion = sedeCajaCompensacion;
	}

	/**
	 * @return the fechaInicioProceso
	 */
	public Date getFechaInicioProceso() {
		return fechaInicioProceso;
	}

	/**
	 * @param fechaInicioProceso
	 *            the fechaInicioProceso to set
	 */
	public void setFechaInicioProceso(Date fechaInicioProceso) {
		this.fechaInicioProceso = fechaInicioProceso;
	}

	/**
	 * @return the requsitos
	 */
	public List<IntentoPostulacionRequisito> getRequsitos() {
		return requsitos;
	}

	/**
	 * @param requsitos
	 *            the requsitos to set
	 */
	public void setRequsitos(List<IntentoPostulacionRequisito> requsitos) {
		this.requsitos = requsitos;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * @param usuarioCreacion
	 *            the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud
	 *            the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Obtiene el valor de proceso
	 * 
	 * @return El valor de proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/**
	 * Establece el valor de proceso
	 * 
	 * @param proceso
	 *            El valor de proceso por asignar
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}

	/**
	 * Obtiene el valor de tipoSolicitante
	 * 
	 * @return El valor de tipoSolicitante
	 */
	public String getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Establece el valor de tipoSolicitante
	 * 
	 * @param tipoSolicitante
	 *            El valor de tipoSolicitante por asignar
	 */
	public void setTipoSolicitante(String tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Obtiene el valor de modalidad
	 * 
	 * @return El valor de modalidad
	 */
	public ModalidadEnum getModalidad() {
		return modalidad;
	}

	/**
	 * Establece el valor de modalidad
	 * 
	 * @param modalidad
	 *            El valor de modalidad por asignar
	 */
	public void setModalidad(ModalidadEnum modalidad) {
		this.modalidad = modalidad;
	}
}
