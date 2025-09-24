package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class grupoCategoriasSTDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tipo identificación de la persona
	 */
	private TipoIdentificacionEnum tipoId;
	
	/**
	 * Número de identificación de la persona
	 */
	private String identificacion;
	
	/**
	 * Primer nombre de la persona
	 */
	private String primerNombre;
	
	/**
	 * Segundo nombre de la persona
	 */
	private String segundoNombre;
	
	/**
	 * Primer apellido de la persona
	 */
	private String primerApellido;
	
	/**
	 * Segundo apellido de la persona
	 */
	private String segundoApellido;
	
	/**
	 * Estado de afiliación de la persona (Activo - Inactivo)
	 */
	private EstadoAfiliadoEnum estadoAfiliacion;
	
	/**
	 * Arreglo de categorias como dependiente o heredadas del afiliado principal dependiente (caso beneficiario)
	 */
	private CategoriaAfiliacionSTDTO arregloDependiente;
	
	/**
	 * Arreglo de categorias como independiente o heredadas del afiliado principal independiente (caso beneficiario)
	 */
	private CategoriaAfiliacionSTDTO arregloIndependiente;
	
	/**
	 * Arreglo de categorias como pensionado o heredadas del afiliado principal pensionado (caso beneficiario)
	 */
	private CategoriaAfiliacionSTDTO arregloPensionado;

	
	
	public grupoCategoriasSTDTO() {
	}
	
	/**
	 * @return the tipoId
	 */
	public TipoIdentificacionEnum getTipoId() {
		return tipoId;
	}
	/**
	 * @param tipoId the tipoId to set
	 */
	public void setTipoId(TipoIdentificacionEnum tipoId) {
		this.tipoId = tipoId;
	}
	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}
	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}
	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}
	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	/**
	 * @return the estadoAfiliacion
	 */
	public EstadoAfiliadoEnum getEstadoAfiliacion() {
		return estadoAfiliacion;
	}
	/**
	 * @param estadoAfiliacion the estadoAfiliacion to set
	 */
	public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}
	/**
	 * @return the arregloDependiente
	 */
	public CategoriaAfiliacionSTDTO getArregloDependiente() {
		return arregloDependiente;
	}
	/**
	 * @param arregloDependiente the arregloDependiente to set
	 */
	public void setArregloDependiente(CategoriaAfiliacionSTDTO arregloDependiente) {
		this.arregloDependiente = arregloDependiente;
	}
	/**
	 * @return the arregloIndependiente
	 */
	public CategoriaAfiliacionSTDTO getArregloIndependiente() {
		return arregloIndependiente;
	}
	/**
	 * @param arregloIndependiente the arregloIndependiente to set
	 */
	public void setArregloIndependiente(CategoriaAfiliacionSTDTO arregloIndependiente) {
		this.arregloIndependiente = arregloIndependiente;
	}
	/**
	 * @return the arregloPensionado
	 */
	public CategoriaAfiliacionSTDTO getArregloPensionado() {
		return arregloPensionado;
	}
	/**
	 * @param arregloPensionado the arregloPensionado to set
	 */
	public void setArregloPensionado(CategoriaAfiliacionSTDTO arregloPensionado) {
		this.arregloPensionado = arregloPensionado;
	}
}
