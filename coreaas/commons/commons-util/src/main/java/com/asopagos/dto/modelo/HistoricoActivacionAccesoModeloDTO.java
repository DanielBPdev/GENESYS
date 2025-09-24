package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.PersonaDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.personas.HistoricoActivacionAcceso;

@XmlRootElement
public class HistoricoActivacionAccesoModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 18889865640005421L;
	/**
	 * Código identificador de llave primaria
	 */

	private Long idHistoricoActivacionAcceso;

	/**
	 * tipo de movimiento : activacion ,inactivacion ....
	 */

	private String tipoMovimiento;

	/**
	 * movimiento,manual,afiliacion del empleador....
	 */

	private String canal;
	/**
	 * Estado : activo ,inactivo
	 */

	private String estado;
	/**
	 * tipo de vista : pantalla desde donde se realiza la creacion y/o
	 * modificacion
	 */

	private String tipoVista;

	/**
	 * Fecha de creacion y/o modificacion
	 */

	private Date fechaMovimiento;

	/**
	 * Referencia a la ubicación asociada a la persona
	 */

	private Persona personaresponsable;

	/**
	 * Referencia a la ubicación asociada a la persona
	 */
	private Persona personaCreada;

	private String nombreCompleto;

	public HistoricoActivacionAccesoModeloDTO() {

	}

	public HistoricoActivacionAcceso convertToEntity() {
		HistoricoActivacionAcceso ha = new HistoricoActivacionAcceso();
		ha.setIdHistoricoActivacionAcceso(this.getIdHistoricoActivacionAcceso());
		ha.setTipoMovimiento(this.getTipoMovimiento());
		ha.setCanal(this.getCanal());
		ha.setEstado(this.getEstado());
		ha.setTipoVista(this.getTipoVista());
		ha.setFechaMovimiento(this.getFechaMovimiento());
		ha.setPersonaresponsable(this.getPersonaresponsable());
		ha.setPersonaCreada(this.getPersonaCreada());
		return ha;
	}

	public HistoricoActivacionAccesoModeloDTO(HistoricoActivacionAcceso ha) {
		this.setIdHistoricoActivacionAcceso(ha.getIdHistoricoActivacionAcceso());
		convertToDTO(ha);
	}

	private void convertToDTO(HistoricoActivacionAcceso ha) {
		this.setIdHistoricoActivacionAcceso(ha.getIdHistoricoActivacionAcceso());
		this.setTipoMovimiento(ha.getTipoMovimiento());
		this.setCanal(ha.getCanal());
		this.setEstado(ha.getEstado());
		this.setTipoVista(ha.getTipoVista());
		this.setFechaMovimiento(ha.getFechaMovimiento());
		this.setPersonaresponsable(null);
		this.setPersonaCreada(null);
		this.setNombreCompletos(
				(ha.getPersonaCreada().getPrimerNombre() != null ? ha.getPersonaCreada().getPrimerNombre() : "") + " "
						+ (ha.getPersonaCreada().getSegundoNombre() != null ? ha.getPersonaCreada().getSegundoNombre()
								: "")
						+ " "
						+ (ha.getPersonaCreada().getPrimerApellido() != null ? ha.getPersonaCreada().getPrimerApellido()
								: "")
						+ " " + (ha.getPersonaCreada().getSegundoApellido() != null
								? ha.getPersonaCreada().getSegundoApellido() : ""));

	}

	public Long getIdHistoricoActivacionAcceso() {
		return idHistoricoActivacionAcceso;
	}

	public void setIdHistoricoActivacionAcceso(Long idHistoricoActivacionAcceso) {
		this.idHistoricoActivacionAcceso = idHistoricoActivacionAcceso;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoVista() {
		return tipoVista;
	}

	public void setTipoVista(String tipoVista) {
		this.tipoVista = tipoVista;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public Persona getPersonaresponsable() {
		return personaresponsable;
	}

	public void setPersonaresponsable(Persona personaresponsable) {
		this.personaresponsable = personaresponsable;
	}

	public Persona getPersonaCreada() {
		return personaCreada;
	}

	public void setPersonaCreada(Persona personaCreada) {
		this.personaCreada = personaCreada;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombreCompletos() {
		return nombreCompleto;
	}

	public void setNombreCompletos(String nombreCompletos) {
		this.nombreCompleto = nombreCompletos;
	}

}
