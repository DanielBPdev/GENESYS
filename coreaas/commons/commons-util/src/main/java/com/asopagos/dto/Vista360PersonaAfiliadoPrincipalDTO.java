/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * @author rarboleda
 *
 */
public class Vista360PersonaAfiliadoPrincipalDTO implements Serializable {

	private static final long serialVersionUID = 2886884567008643811L;
	
	/**
	 * Nombre completo del afiliado al que esta asociada la persona
	 */
	private String nombreCompletoAfiliado;
	
	/**
	 * Estado del afiliado con respecto al beneficiario
	 */
	private EstadoAfiliadoEnum estadoAfiliadoBeneficiario;
	
	/**
	 * Parentezco de la persona con el afiliado
	 */
	private ClasificacionEnum parentezco;
	
	/**
	 * Tipo de identificacion del afiliado
	 */
	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
	
	/**
	 * Numero de identificacion del afiliado
	 */
	private String numeroIdentificacionAfiliado;
	
	
	/**
	 * @return the nombreCompletoAfiliado
	 */
	public String getNombreCompletoAfiliado() {
		return nombreCompletoAfiliado;
	}
	/**
	 * @param nombreCompletoAfiliado the nombreCompletoAfiliado to set
	 */
	public void setNombreCompletoAfiliado(String nombreCompletoAfiliado) {
		this.nombreCompletoAfiliado = nombreCompletoAfiliado;
	}
	/**
	 * @return the estadoAfiliadoBeneficiario
	 */
	public EstadoAfiliadoEnum getEstadoAfiliadoBeneficiario() {
		return estadoAfiliadoBeneficiario;
	}
	/**
	 * @param estadoAfiliadoBeneficiario the estadoAfiliadoBeneficiario to set
	 */
	public void setEstadoAfiliadoBeneficiario(EstadoAfiliadoEnum estadoAfiliadoBeneficiario) {
		this.estadoAfiliadoBeneficiario = estadoAfiliadoBeneficiario;
	}
	/**
	 * @return the parentezco
	 */
	public ClasificacionEnum getParentezco() {
		return parentezco;
	}
	/**
	 * @param parentezco the parentezco to set
	 */
	public void setParentezco(ClasificacionEnum parentezco) {
		this.parentezco = parentezco;
	}
	/**
	 * @return the tipoIdentificacionAfiliado
	 */
	public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
		return tipoIdentificacionAfiliado;
	}
	/**
	 * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
	 */
	public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
		this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
	}
	/**
	 * @return the numeroIdentificacionAfiliado
	 */
	public String getNumeroIdentificacionAfiliado() {
		return numeroIdentificacionAfiliado;
	}
	/**
	 * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
	 */
	public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
		this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
	}
}
