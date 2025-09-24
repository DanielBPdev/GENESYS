package com.asopagos.afiliaciones.empleadores.web.composite.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.InformacionContactoDTO;

@XmlRootElement
public class ReenviarCorreoEnrolamientoDTO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String numeroRadicado;
	
	private InformacionContactoDTO informacionContacto;

	private String dominio;


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

	public String getDominio() {
		// TODO Auto-generated method stub
		return dominio;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @param dominio the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	
	
}
