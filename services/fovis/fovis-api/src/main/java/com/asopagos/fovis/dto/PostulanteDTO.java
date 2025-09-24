package com.asopagos.fovis.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta de un postulante a FOVIS.
 * <b>HU-032</b>
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra Zuluaga</a>
 */
@XmlRootElement
public class PostulanteDTO implements Serializable {

	/**
	 * Serial autogenerado.
	 */
	private static final long serialVersionUID = -7671976893350969838L;
	
	/**
	 * Número de identificación del jefe de hogar.
	 */
	private String numeroIdentificacionJefeHogar;
	
	/**
	 * Número de identificación del integrante de hogar.
	 */
	private String numeroIdentificacionIntegranteHogar;
	
	/**
	 * Método constructor para devolver los datos consultados relacionados a la lista de postulantes a FOVIS.
	 */
	public PostulanteDTO (String personaJefeHogar, String personaIntegranteHogar) {
		this.setNumeroIdentificacionJefeHogar(personaJefeHogar);
		this.setNumeroIdentificacionIntegranteHogar(personaIntegranteHogar);
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionJefeHogar.
	 * @return valor de numeroIdentificacionJefeHogar.
	 */
	public String getNumeroIdentificacionJefeHogar() {
		return numeroIdentificacionJefeHogar;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionJefeHogar.
	 * @param valor para modificar numeroIdentificacionJefeHogar.
	 */
	public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
		this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionIntegranteHogar.
	 * @return valor de numeroIdentificacionIntegranteHogar.
	 */
	public String getNumeroIdentificacionIntegranteHogar() {
		return numeroIdentificacionIntegranteHogar;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionIntegranteHogar.
	 * @param valor para modificar numeroIdentificacionIntegranteHogar.
	 */
	public void setNumeroIdentificacionIntegranteHogar(String numeroIdentificacionIntegranteHogar) {
		this.numeroIdentificacionIntegranteHogar = numeroIdentificacionIntegranteHogar;
	}
	
	public PostulanteDTO() {
		// TODO Auto-generated constructor stub
	}
}
