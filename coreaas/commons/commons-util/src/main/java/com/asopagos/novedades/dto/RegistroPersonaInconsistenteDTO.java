/**
 *
 */
package com.asopagos.novedades.dto;

import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.novedades.CargueMultipleSupervivencia;
import com.asopagos.entidades.ccf.novedades.RegistroPersonaInconsistente;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.novedades.EstadoGestionEnum;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jzambrano
 *
 */
public class RegistroPersonaInconsistenteDTO {

	private Long idCargueMultiple;

	private Long idRegistroPersonaInconsitente;

	private String nombreCargue;

	private PersonaModeloDTO persona;

	private CanalRecepcionEnum canalContacto;

	private Long fechaIngreso;

	private EstadoGestionEnum estadoGestion;

	private String observaciones;

	private TipoInconsistenciaANIEnum tipoInconsistencia;

	/**
	 * @return the idCargueMultiple
	 */
	public Long getIdCargueMultiple() {
		return idCargueMultiple;
	}

	/**
	 * @param idCargueMultiple
	 *            the idCargueMultiple to set
	 */
	public void setIdCargueMultiple(Long idCargueMultiple) {
		this.idCargueMultiple = idCargueMultiple;
	}

	/**
	 * @return the nombreCargue
	 */
	public String getNombreCargue() {
		return nombreCargue;
	}

	/**
	 * @param nombreCargue
	 *            the nombreCargue to set
	 */
	public void setNombreCargue(String nombreCargue) {
		this.nombreCargue = nombreCargue;
	}

	/**
	 * @return the persona
	 */
	public PersonaModeloDTO getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaModeloDTO persona) {
		this.persona = persona;
	}

	/**
	 * @return the canalContacto
	 */
	public CanalRecepcionEnum getCanalContacto() {
		return canalContacto;
	}

	/**
	 * @param canalContacto
	 *            the canalContacto to set
	 */
	public void setCanalContacto(CanalRecepcionEnum canalContacto) {
		this.canalContacto = canalContacto;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso
	 *            the fechaIngreso to set
	 */
	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the estadoGestion
	 */
	public EstadoGestionEnum getEstadoGestion() {
		return estadoGestion;
	}

	/**
	 * @param estadoGestion
	 *            the estadoGestion to set
	 */
	public void setEstadoGestion(EstadoGestionEnum estadoGestion) {
		this.estadoGestion = estadoGestion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the tipoInconsistencia
	 */
	public TipoInconsistenciaANIEnum getTipoInconsistencia() {
		return tipoInconsistencia;
	}

	/**
	 * @param tipoInconsistencia
	 *            the tipoInconsistencia to set
	 */
	public void setTipoInconsistencia(TipoInconsistenciaANIEnum tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}

	public RegistroPersonaInconsistenteDTO(RegistroPersonaInconsistente personaInconsistente, Persona persona,
			CargueMultipleSupervivencia cargueSupervivencia) {

		this.canalContacto = personaInconsistente.getCanalContacto();
		this.estadoGestion = personaInconsistente.getEstadoGestion();
		this.fechaIngreso = personaInconsistente.getFechaIngreso().getTime();
		this.observaciones = personaInconsistente.getObservaciones();
		this.tipoInconsistencia = personaInconsistente.getTipoInconsistencia();
		this.idRegistroPersonaInconsitente = personaInconsistente.getIdRegistroPersonaInconsistente();
		if (cargueSupervivencia != null) {
			this.idCargueMultiple = cargueSupervivencia.getIdCargueMultipleSupervivencia();
			this.nombreCargue=cargueSupervivencia.getNombreArchivo();
		}
		this.persona = new PersonaModeloDTO(persona, null);
	}
	public RegistroPersonaInconsistenteDTO() {
	}

	/**
	 * @return the idRegistroPersonaInconsitente
	 */
	public Long getIdRegistroPersonaInconsitente() {
		return idRegistroPersonaInconsitente;
	}

	/**
	 * @param idRegistroPersonaInconsitente
	 *            the idRegistroPersonaInconsitente to set
	 */
	public void setIdRegistroPersonaInconsitente(Long idRegistroPersonaInconsitente) {
		this.idRegistroPersonaInconsitente = idRegistroPersonaInconsitente;
	}

}
