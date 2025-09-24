/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * @author rarboleda
 *
 */
public class Vista360PersonaCabeceraDTO implements Serializable {

	private static final long serialVersionUID = 708459862512335459L;
	
	/** Tipo de Identificacion de la persona */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/** Numero de identificacion de la persona */
	private String numeroIdentificacion;
	
	/** Primer nombre de la persona */
	private String primerNombre;
	
	/** Segundo nombre de la persona */
	private String segundoNombre;
	
	/** Primer apellido de la persona */
	private String primerApellido;
	
	/** Segundo apellido de la persona */
	private String segundoApellido;
	
	/** Estado con respecto a la caja */
	private EstadoAfiliadoEnum estadoConRespectoCaja;
	
	/** Marca si es dependiente */
	private Integer isDependiente;
	
	/** Marca si es independiente */
	private Integer isIndependiente;
	
	/** Marca si es pensionado */
	private Integer isPensionado;
	
	/** Marca si es empleador */
	private Integer isEmpleador;
	
	/** Marca si es administrador de subsidio */
	private Integer isAdministradorSubsidio;
	
	/** Marca si es beneficiario */
	private Integer isBeneficiario;
	
	/** Lista de afiliados a los que la persona esta como beneficiario */
	private List<Vista360PersonaAfiliadoPrincipalDTO> afiliadosPrincipalesPersona;
	
	/** Si la persona es empleador el idEmpleador va aqui */
	private Long idEmpleador;

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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
	 * @return the estadoConRespectoCaja
	 */
	public EstadoAfiliadoEnum getEstadoConRespectoCaja() {
		return estadoConRespectoCaja;
	}

	/**
	 * @param estadoConRespectoCaja the estadoConRespectoCaja to set
	 */
	public void setEstadoConRespectoCaja(EstadoAfiliadoEnum estadoConRespectoCaja) {
		this.estadoConRespectoCaja = estadoConRespectoCaja;
	}

	/**
	 * @return the isDependiente
	 */
	public Integer getIsDependiente() {
		return isDependiente;
	}

	/**
	 * @param isDependiente the isDependiente to set
	 */
	public void setIsDependiente(Integer isDependiente) {
		this.isDependiente = isDependiente;
	}

	/**
	 * @return the isIndependiente
	 */
	public Integer getIsIndependiente() {
		return isIndependiente;
	}

	/**
	 * @param isIndependiente the isIndependiente to set
	 */
	public void setIsIndependiente(Integer isIndependiente) {
		this.isIndependiente = isIndependiente;
	}

	/**
	 * @return the isPensionado
	 */
	public Integer getIsPensionado() {
		return isPensionado;
	}

	/**
	 * @param isPensionado the isPensionado to set
	 */
	public void setIsPensionado(Integer isPensionado) {
		this.isPensionado = isPensionado;
	}

	/**
	 * @return the isEmpleador
	 */
	public Integer getIsEmpleador() {
		return isEmpleador;
	}

	/**
	 * @param isEmpleador the isEmpleador to set
	 */
	public void setIsEmpleador(Integer isEmpleador) {
		this.isEmpleador = isEmpleador;
	}

	/**
	 * @return the isAdministradorSubsidio
	 */
	public Integer getIsAdministradorSubsidio() {
		return isAdministradorSubsidio;
	}

	/**
	 * @param isAdministradorSubsidio the isAdministradorSubsidio to set
	 */
	public void setIsAdministradorSubsidio(Integer isAdministradorSubsidio) {
		this.isAdministradorSubsidio = isAdministradorSubsidio;
	}

	/**
	 * @return the isBeneficiario
	 */
	public Integer getIsBeneficiario() {
		return isBeneficiario;
	}

	/**
	 * @param isBeneficiario the isBeneficiario to set
	 */
	public void setIsBeneficiario(Integer isBeneficiario) {
		this.isBeneficiario = isBeneficiario;
	}

	/**
	 * @return the afiliadosPrincipalesPersona
	 */
	public List<Vista360PersonaAfiliadoPrincipalDTO> getAfiliadosPrincipalesPersona() {
		return afiliadosPrincipalesPersona;
	}

	/**
	 * @param afiliadosPrincipalesPersona the afiliadosPrincipalesPersona to set
	 */
	public void setAfiliadosPrincipalesPersona(List<Vista360PersonaAfiliadoPrincipalDTO> afiliadosPrincipalesPersona) {
		this.afiliadosPrincipalesPersona = afiliadosPrincipalesPersona;
	}

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

}
