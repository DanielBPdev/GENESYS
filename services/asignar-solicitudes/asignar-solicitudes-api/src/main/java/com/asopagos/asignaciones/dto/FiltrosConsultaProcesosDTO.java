package com.asopagos.asignaciones.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ProcesoEnum;

@XmlRootElement
public class FiltrosConsultaProcesosDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long sede;
	
	private String grupo;
	
	private String usuario;
	
	private ProcesoEnum proceso;

	/**
	 * @return the sede
	 */
	public Long getSede() {
		return sede;
	}

	/**
	 * @param sede the sede to set
	 */
	public void setSede(Long sede) {
		this.sede = sede;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}
}
