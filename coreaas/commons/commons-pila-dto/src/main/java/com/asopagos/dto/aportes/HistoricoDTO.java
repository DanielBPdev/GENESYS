package com.asopagos.dto.aportes;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase DTO que representa el histórico de un aporte durante una gestión de devolución. 
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra Zuluaga. </a>
 */
@XmlRootElement
public class HistoricoDTO implements Serializable{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Campo que verifica si es o no un archivo de reemplazo.
	 */
	private Boolean archivoRemplazado;
	
	/**
	 * Campo que verifica si es o no un archivo finalizado.
	 */
	private Boolean archivoFinalizado;
	
	/**
	 * Campo que verifica si es o no un archivo vigente.
	 */
	private Boolean aporteVigente;
	
	/**
	 * Campo que verifica si se ha pagado o no en el período.
	 */
	private Boolean seHaPagadoEnPeriodo;
	
	/**
	 * Campo que verifica si cumple o no.
	 */
	private Boolean cumple;
	
	/**
	 * Comentarios dentro del proceso.
	 */
	private String comentarios;
	
	/**
	 * Aporte obligatorio.
	 */
	private String aporteObligatorio;
	
	/**
	 * Mora en el pago de aportes.
	 */
	private String mora;

	/**
	 * Método que retorna el valor de archivoRemplazado.
	 * @return valor de archivoRemplazado.
	 */
	public Boolean getArchivoRemplazado() {
		return archivoRemplazado;
	}

	/**
	 * Método encargado de modificar el valor de archivoRemplazado.
	 * @param valor para modificar archivoRemplazado.
	 */
	public void setArchivoRemplazado(Boolean archivoRemplazado) {
		this.archivoRemplazado = archivoRemplazado;
	}

	/**
	 * Método que retorna el valor de archivoFinalizado.
	 * @return valor de archivoFinalizado.
	 */
	public Boolean getArchivoFinalizado() {
		return archivoFinalizado;
	}

	/**
	 * Método encargado de modificar el valor de archivoFinalizado.
	 * @param valor para modificar archivoFinalizado.
	 */
	public void setArchivoFinalizado(Boolean archivoFinalizado) {
		this.archivoFinalizado = archivoFinalizado;
	}

	/**
	 * Método que retorna el valor de aporteVigente.
	 * @return valor de aporteVigente.
	 */
	public Boolean getAporteVigente() {
		return aporteVigente;
	}

	/**
	 * Método encargado de modificar el valor de aporteVigente.
	 * @param valor para modificar aporteVigente.
	 */
	public void setAporteVigente(Boolean aporteVigente) {
		this.aporteVigente = aporteVigente;
	}

	/**
	 * Método que retorna el valor de seHaPagadoEnPeriodo.
	 * @return valor de seHaPagadoEnPeriodo.
	 */
	public Boolean getSeHaPagadoEnPeriodo() {
		return seHaPagadoEnPeriodo;
	}

	/**
	 * Método encargado de modificar el valor de seHaPagadoEnPeriodo.
	 * @param valor para modificar seHaPagadoEnPeriodo.
	 */
	public void setSeHaPagadoEnPeriodo(Boolean seHaPagadoEnPeriodo) {
		this.seHaPagadoEnPeriodo = seHaPagadoEnPeriodo;
	}

	/**
	 * Método que retorna el valor de cumple.
	 * @return valor de cumple.
	 */
	public Boolean getCumple() {
		return cumple;
	}

	/**
	 * Método encargado de modificar el valor de cumple.
	 * @param valor para modificar cumple.
	 */
	public void setCumple(Boolean cumple) {
		this.cumple = cumple;
	}

	/**
	 * Método que retorna el valor de comentarios.
	 * @return valor de comentarios.
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * Método encargado de modificar el valor de comentarios.
	 * @param valor para modificar comentarios.
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Método que retorna el valor de aporteObligatorio.
	 * @return valor de aporteObligatorio.
	 */
	public String getAporteObligatorio() {
		return aporteObligatorio;
	}

	/**
	 * Método encargado de modificar el valor de aporteObligatorio.
	 * @param valor para modificar aporteObligatorio.
	 */
	public void setAporteObligatorio(String aporteObligatorio) {
		this.aporteObligatorio = aporteObligatorio;
	}

	/**
	 * Método que retorna el valor de mora.
	 * @return valor de mora.
	 */
	public String getMora() {
		return mora;
	}

	/**
	 * Método encargado de modificar el valor de mora.
	 * @param valor para modificar mora.
	 */
	public void setMora(String mora) {
		this.mora = mora;
	}
}