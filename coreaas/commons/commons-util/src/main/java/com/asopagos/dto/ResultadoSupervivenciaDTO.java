/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * @author jzambrano
 *
 */
@XmlRootElement
public class ResultadoSupervivenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Descripción del tipo de identificación de la persona
	 */
	@NotNull
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación de la persona
	 */
	@NotNull
	private String numeroIdentificacion;

	private TipoInconsistenciaANIEnum tipoInconsistencia;

	private Long fechaDefuncion;
	
	@NotNull
	private Long idCargueMultipleSupervivencia;

	/**
	 * 
	 */
	public ResultadoSupervivenciaDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idCargueMultipleSupervivencia
	 */
	public Long getIdCargueMultipleSupervivencia() {
		return idCargueMultipleSupervivencia;
	}

	/**
	 * @param idCargueMultipleSupervivencia the idCargueMultipleSupervivencia to set
	 */
	public void setIdCargueMultipleSupervivencia(Long idCargueMultipleSupervivencia) {
		this.idCargueMultipleSupervivencia = idCargueMultipleSupervivencia;
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
	 * @return the tipoInconsistencia
	 */
	public TipoInconsistenciaANIEnum getTipoInconsistencia() {
		return tipoInconsistencia;
	}

	/**
	 * @param tipoInconsistencia the tipoInconsistencia to set
	 */
	public void setTipoInconsistencia(TipoInconsistenciaANIEnum tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}

	/**
	 * @return the fechaDefuncion
	 */
	public Long getFechaDefuncion() {
		return fechaDefuncion;
	}

	/**
	 * @param fechaDefuncion the fechaDefuncion to set
	 */
	public void setFechaDefuncion(Long fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
}
