package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.entidades.ccf.fovis.RecursoComplementario;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>RecursoComplementario</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class RecursoComplementarioModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4826576540636762432L;

	/**
	 * Identificador del recurso
	 */
	private Long idRecurso;

	/**
	 * Nombre del recurso
	 */
	private TipoRecursoComplementarioEnum nombre;

	/**
	 * Nombre de la entidad
	 */
	private String entidad;

	/**
	 * Fecha en la que se registra el recurso
	 */
	private Long fecha;

	/**
	 * Nombre de otro recurso
	 */
	private String otroRecurso;

	/**
	 * Valor del recurso
	 */
	private BigDecimal valor;

	/**
	 * Identificador de la postulación
	 */
	private Long postulacion;

	/**
	 * Método que convierte el DTO a entidad
	 * 
	 * @return La entidad <code>RecursoComplementario</code> equivalente
	 */
	
	/**
	 * Constructor por defecto
	 */
	 public RecursoComplementarioModeloDTO() {
		// TODO Auto-generated constructor stub
	}
	 
	 /**
	  * Constructor a partir de una entidad RecursoComplementario
	  * @param recursoComplementario
	  */
	 public RecursoComplementarioModeloDTO(RecursoComplementario recursoComplementario){
		 this.setIdRecurso(recursoComplementario.getIdRecurso());
			this.setPostulacion(recursoComplementario.getPostulacion());
			this.setEntidad(recursoComplementario.getEntidad() != null ? recursoComplementario.getEntidad() : null);
			this.setFecha(recursoComplementario.getFecha() != null ? recursoComplementario.getFecha().getTime() : null);
			this.setNombre(recursoComplementario.getNombre() != null ? recursoComplementario.getNombre() : null);
			this.setOtroRecurso(recursoComplementario.getOtroRecurso() != null ? recursoComplementario.getOtroRecurso() : null);
			this.setValor(recursoComplementario.getValor() != null ? recursoComplementario.getValor() : null);
	 }
	 
	public RecursoComplementario convertToEntity() {
		RecursoComplementario recursoComplementario = new RecursoComplementario();
		recursoComplementario.setEntidad(this.entidad);

		if (this.fecha != null) {
			recursoComplementario.setFecha(new Date(fecha));
		}

		recursoComplementario.setIdRecurso(this.idRecurso);
		recursoComplementario.setNombre(this.nombre);
		recursoComplementario.setOtroRecurso(this.otroRecurso);
		recursoComplementario.setPostulacion(this.postulacion);
		recursoComplementario.setValor(this.valor);
		return recursoComplementario;
	}
	
	/**
	 * Método que convierte una entidad <code>AhorroPrevio</code> al DTO
	 * 
	 * @param ahorroPrevio
	 *            La entidad <code>AhorroPrevio</code> a convertir
	 */
	public void convertToDTO(RecursoComplementario recursoComplementario) {
		
		this.setIdRecurso(recursoComplementario.getIdRecurso());
		this.setPostulacion(recursoComplementario.getPostulacion());
		this.setEntidad(recursoComplementario.getEntidad() != null ? recursoComplementario.getEntidad() : null);
		this.setFecha(recursoComplementario.getFecha() != null ? recursoComplementario.getFecha().getTime() : null);
		this.setNombre(recursoComplementario.getNombre() != null ? recursoComplementario.getNombre() : null);
		this.setOtroRecurso(recursoComplementario.getOtroRecurso() != null ? recursoComplementario.getOtroRecurso() : null);
		this.setValor(recursoComplementario.getValor() != null ? recursoComplementario.getValor() : null);
		
	}

	/**
	 * Obtiene el valor de idRecurso
	 * 
	 * @return El valor de idRecurso
	 */
	public Long getIdRecurso() {
		return idRecurso;
	}

	/**
	 * Establece el valor de idRecurso
	 * 
	 * @param idRecurso
	 *            El valor de idRecurso por asignar
	 */
	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	/**
	 * Obtiene el valor de nombre
	 * 
	 * @return El valor de nombre
	 */
	public TipoRecursoComplementarioEnum getNombre() {
		return nombre;
	}

	/**
	 * Establece el valor de nombre
	 * 
	 * @param nombre
	 *            El valor de nombre por asignar
	 */
	public void setNombre(TipoRecursoComplementarioEnum nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el valor de entidad
	 * 
	 * @return El valor de entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Establece el valor de entidad
	 * 
	 * @param entidad
	 *            El valor de entidad por asignar
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Obtiene el valor de fecha
	 * 
	 * @return El valor de fecha
	 */
	public Long getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor de fecha
	 * 
	 * @param fecha
	 *            El valor de fecha por asignar
	 */
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor de otroRecurso
	 * 
	 * @return El valor de otroRecurso
	 */
	public String getOtroRecurso() {
		return otroRecurso;
	}

	/**
	 * Establece el valor de otroRecurso
	 * 
	 * @param otroRecurso
	 *            El valor de otroRecurso por asignar
	 */
	public void setOtroRecurso(String otroRecurso) {
		this.otroRecurso = otroRecurso;
	}

	/**
	 * Obtiene el valor de valor
	 * 
	 * @return El valor de valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * Establece el valor de valor
	 * 
	 * @param valor
	 *            El valor de valor por asignar
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * @return the postulacion
	 */
	public Long getPostulacion() {
		return postulacion;
	}

	/**
	 * @param postulacion the postulacion to set
	 */
	public void setPostulacion(Long postulacion) {
		this.postulacion = postulacion;
	}

}
