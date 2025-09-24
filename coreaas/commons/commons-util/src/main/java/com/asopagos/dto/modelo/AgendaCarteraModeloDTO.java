package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

import com.asopagos.entidades.ccf.cartera.AgendaCartera;
import com.asopagos.enumeraciones.cartera.VisitaAgendaEnum;

/**
 * Representa la agenda para iniciar el proceso de fiscalización sobre el
 * aportante
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class AgendaCarteraModeloDTO implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 6627196549291700132L;
	/**
	 * Identificador unico para la agenda de fiscalización
	 */
	private Long idAgendaFiscalizacion;
	/**
	 * Representa los valores Visita, Llamado, Visita a Caja, Otro
	 */
	private VisitaAgendaEnum visitaAgenda;
	/**
	 * Fecha para la cual se agendo la visita al aportante
	 */
	@NotNull
	private Long fecha;
	/**
	 * Representa la hora especifica en que se agendo la visita
	 */
	@NotNull
	private Long horario;
	/**
	 * Nombre del contacto con el empleador
	 */
	@NotNull
	private String contacto;
	/**
	 * Telefono del contacto con el empleador
	 */
	private String telefono;

	/**
	 * Identificador único del ciclo del aportante
	 */
	private Long idCicloAportante;

	/**
	 * Documento soporte de la agenda
	 */
	private DocumentoSoporteModeloDTO documentosoportemodeloDTO;

	/**
	 * Número de operación de cartera
	 */
	private Long idCartera;

    /**
     * Valor que permite identificar cuando la agenda de la cartera debe estar visible en la pestaña gestión para solicitudes manuales o del
     * aportante
     */
    private Boolean esVisible;
	
	/**
	 * Método constructor
	 */
	public AgendaCarteraModeloDTO() {

	}

	public AgendaCarteraModeloDTO(AgendaCartera agendaCartera){
	    convertToDTO(agendaCartera);
	}
	
	/**
	 * @return the idAgendaFiscalizacion
	 */
	public Long getIdAgendaFiscalizacion() {
		return idAgendaFiscalizacion;
	}

	/**
	 * @param idAgendaFiscalizacion
	 *            the idAgendaFiscalizacion to set
	 */
	public void setIdAgendaFiscalizacion(Long idAgendaFiscalizacion) {
		this.idAgendaFiscalizacion = idAgendaFiscalizacion;
	}

	/**
	 * @return the visitaAgenda
	 */
	public VisitaAgendaEnum getVisitaAgenda() {
		return visitaAgenda;
	}

	/**
	 * @param visitaAgenda
	 *            the visitaAgenda to set
	 */
	public void setVisitaAgenda(VisitaAgendaEnum visitaAgenda) {
		this.visitaAgenda = visitaAgenda;
	}

	/**
	 * @return the fecha
	 */
	public Long getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the horario
	 */
	public Long getHorario() {
		return horario;
	}

	/**
	 * @param horario
	 *            the horario to set
	 */
	public void setHorario(Long horario) {
		this.horario = horario;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto
	 *            the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Método que retorna el valor de idCicloAportante.
	 * 
	 * @return valor de idCicloAportante.
	 */
	public Long getIdCicloAportante() {
		return idCicloAportante;
	}

	/**
	 * Método encargado de modificar el valor de idCicloAportante.
	 * 
	 * @param valor
	 *            para modificar idCicloAportante.
	 */
	public void setIdCicloAportante(Long idCicloAportante) {
		this.idCicloAportante = idCicloAportante;
	}

	/**
	 * Método que retorna el valor de documentosoportemodeloDTO.
	 * 
	 * @return valor de documentosoportemodeloDTO.
	 */
	public DocumentoSoporteModeloDTO getDocumentosoportemodeloDTO() {
		return documentosoportemodeloDTO;
	}

	/**
	 * Método encargado de modificar el valor de documentosoportemodeloDTO.
	 * 
	 * @param valor
	 *            para modificar documentosoportemodeloDTO.
	 */
	public void setDocumentosoportemodeloDTO(DocumentoSoporteModeloDTO documentosoportemodeloDTO) {
		this.documentosoportemodeloDTO = documentosoportemodeloDTO;
	}

	/**
	 * Metodo que sirve para convertir de entidad a DTO
	 * 
	 * @param agendaFiscalizacion
	 *            recibe un objeto AgendaFiscalizacion
	 */
	public void convertToDTO(AgendaCartera agendaFiscalizacion) {
		/*
		 * Se setea la informacion al objeto AgendaFiscalizacionModeloDTO
		 */
		this.setIdAgendaFiscalizacion(agendaFiscalizacion.getIdAgendaCartera());
		this.setVisitaAgenda(agendaFiscalizacion.getVisitaAgenda());
		this.setFecha(agendaFiscalizacion.getFecha() != null ? agendaFiscalizacion.getFecha().getTime() : null);
		this.setHorario(agendaFiscalizacion.getHorario() != null ? agendaFiscalizacion.getHorario().getTime() : null);
		this.setContacto(agendaFiscalizacion.getContacto());
		this.setTelefono(agendaFiscalizacion.getTelefono());
		this.setIdCicloAportante(agendaFiscalizacion.getIdCicloAportante());
		this.setEsVisible(agendaFiscalizacion.getEsVisible());
		this.setIdCartera(agendaFiscalizacion.getIdCartera());
	}

	/**
	 * Metodo que sirve para convertir de DTO a entidad
	 * 
	 * @return un objeto AgendaFiscalizacion
	 */
	public AgendaCartera convertToEntity() {
		/*
		 * Se setea la informacion al objeto AgendaFiscalizacion
		 */
		AgendaCartera agendaFiscalizacion = new AgendaCartera();
		agendaFiscalizacion.setIdAgendaCartera(this.getIdAgendaFiscalizacion() != null ? this.getIdAgendaFiscalizacion() : null);
		agendaFiscalizacion.setVisitaAgenda(this.getVisitaAgenda() != null ? this.getVisitaAgenda() : null);
		agendaFiscalizacion.setFecha(this.getFecha() != null ? new Date(this.getFecha()) : null);
		agendaFiscalizacion.setHorario(this.getHorario() != null ? new Date(this.getHorario()) : null);
		agendaFiscalizacion.setContacto(this.getContacto() != null ? this.getContacto() : null);
		agendaFiscalizacion.setTelefono(this.getTelefono() != null ? this.getTelefono() : null);
		agendaFiscalizacion.setIdCicloAportante(this.getIdCicloAportante() != null ? this.getIdCicloAportante() : null);
		agendaFiscalizacion.setIdCartera(this.getIdCartera());
	    agendaFiscalizacion.setEsVisible(this.getEsVisible());
		return agendaFiscalizacion;

	}

	/**
	 * Obtiene el valor de idCartera
	 * 
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * Establece el valor de idCartera
	 * 
	 * @param idCartera
	 *            El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

    /**
     * @return the esVisible
     */
    public Boolean getEsVisible() {
        return esVisible;
    }

    /**
     * @param esVisible the esVisible to set
     */
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }

}
