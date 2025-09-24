package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.EstadoListaEspecialRevisionEnum;

/**
 * <b>Descripción:</b> DTO <b>Historia de Usuario:</b> Transversal-336
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class ActualizacionEstadoListaEspecialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idListaEspecialRevision;
	private Date fechaInicioInclusion;
	private Date fechaFinInclusion;
	private EstadoListaEspecialRevisionEnum estado;
	private String comentario;

	public ActualizacionEstadoListaEspecialDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fechaInicioInclusion
	 */
	public Date getFechaInicioInclusion() {
		return fechaInicioInclusion;
	}

	/**
	 * @return the estado
	 */
	public EstadoListaEspecialRevisionEnum getEstado() {
		return estado;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param fechaInicioInclusion
	 *            the fechaInicioInclusion to set
	 */
	public void setFechaInicioInclusion(Date fechaInicioInclusion) {
		this.fechaInicioInclusion = fechaInicioInclusion;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(EstadoListaEspecialRevisionEnum estado) {
		this.estado = estado;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the fechaFinInclusion
	 */
	public Date getFechaFinInclusion() {
		return fechaFinInclusion;
	}

	/**
	 * @param fechaFinInclusion
	 *            the fechaFinInclusion to set
	 */
	public void setFechaFinInclusion(Date fechaFinInclusion) {
		this.fechaFinInclusion = fechaFinInclusion;
	}

	/**
	 * @return the idListaEspecialRevision
	 */
	public Long getIdListaEspecialRevision() {
		return idListaEspecialRevision;
	}

	/**
	 * @param idListaEspecialRevision
	 *            the idListaEspecialRevision to set
	 */
	public void setIdListaEspecialRevision(Long idListaEspecialRevision) {
		this.idListaEspecialRevision = idListaEspecialRevision;
	}

}
