package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

@XmlRootElement
public class DigitarInformacionContactoDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private EmpleadorDTO empleador;
	
	private String dominio;

	private InformacionContactoDTO informacionContacto;
	
	private ClasificacionEnum tipoEmpleador;
	
	private boolean correoEmpleadorNuevo;

	public EmpleadorDTO getEmpleador() {
		return empleador;
	}

	public void setEmpleador(EmpleadorDTO empleador) {
		this.empleador = empleador;
	}
		

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	

	/**
	 * @return the informacionContacto
	 */
	public InformacionContactoDTO getInformacionContacto() {
		return informacionContacto;
	}

	/**
	 * @param informacionContacto the informacionContacto to set
	 */
	public void setInformacionContacto(InformacionContactoDTO informacionContacto) {
		this.informacionContacto = informacionContacto;
	}

	/**
	 * @return the tipoEmpleador
	 */
	public ClasificacionEnum getTipoEmpleador() {
		return tipoEmpleador;
	}

	/**
	 * @param tipoEmpleador the tipoEmpleador to set
	 */
	public void setTipoEmpleador(ClasificacionEnum tipoEmpleador) {
		this.tipoEmpleador = tipoEmpleador;
	}
	
	/**
	 * @return the correoEmpleadorNuevo
	 */
	public boolean isCorreoEmpleadorNuevo() {
		return correoEmpleadorNuevo;
	}

	/**
	 * @param correoEmpleadorNuevo
	 *            the correoEmpleadorNuevo to set
	 */
	public void setCorreoEmpleadorNuevo(boolean correoEmpleadorNuevo) {
		this.correoEmpleadorNuevo = correoEmpleadorNuevo;
	}
    
}
