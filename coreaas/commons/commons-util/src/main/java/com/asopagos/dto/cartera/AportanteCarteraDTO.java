package com.asopagos.dto.cartera;

import java.io.Serializable;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Clase que representa la información de un aportante
 * registrado en cartera <br/>
 * <b>Historia de Usuario: </b> HU-239
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AportanteCarteraDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1221178070782829389L;

	/**
	 * Tipo de identificación del aportante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del aportante
	 */
	private String numeroIdentificacion;

	/**
	 * Dígito de verificación, cuando tipoIdentificacion=NIT
	 */
	private Short digitoVerificacion;

	/**
	 * Tipo de solicitante
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Nombre completo del aportante o razón social del empleador
	 */
	private String nombreRazonSocial;

	/**
	 * Primer nombre del aportante
	 */
	private String primerNombre;

	/**
	 * Segundo nombre del aportante
	 */
	private String segundoNombre;

	/**
	 * Primer apellido del aportante
	 */
	private String primerApellido;

	/**
	 * Segundo apellido del aportante
	 */
	private String segundoApellido;

	/**
	 * Estado del aportante respecto a la caja de compensación
	 */
	private EstadoAfiliadoEnum estadoCCF;

	/**
	 * Estado del aportante en cartera
	 */
	private EstadoCarteraEnum estadoCartera;

	/**
	 * Identificador de la persona como aportante
	 */
	private Long idPersona;

	/**
	 * Constructor por defecto
	 */
	public AportanteCarteraDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param aportante
	 *            Información del aportante
	 */
	public AportanteCarteraDTO(Object[] aportante) {
		this.tipoIdentificacion = aportante[0] != null ? TipoIdentificacionEnum.valueOf(aportante[0].toString()) : null;
		this.numeroIdentificacion = aportante[1] != null ? aportante[1].toString() : null;
		this.digitoVerificacion = aportante[2] != null ? Short.parseShort(aportante[2].toString()) : null;
		this.tipoSolicitante = aportante[3] != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(aportante[3].toString()) : null;
		this.nombreRazonSocial = aportante[4] != null ? aportante[4].toString() : null;
		this.primerNombre = aportante[5] != null ? aportante[5].toString() : null;
		this.segundoNombre = aportante[6] != null ? aportante[6].toString() : null;
		this.primerApellido = aportante[7] != null ? aportante[7].toString() : null;
		this.segundoApellido = aportante[8] != null ? aportante[8].toString() : null;
		this.estadoCartera = aportante[9] != null ? EstadoCarteraEnum.valueOf(aportante[9].toString()) : null;
		this.idPersona = aportante[10] != null ? Long.parseLong(aportante[10].toString()) : null;

		if (aportante.length > 11) {
			this.estadoCCF = aportante[11] != null ? EstadoAfiliadoEnum.valueOf(aportante[11].toString()) : null;
		}
	}

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de tipoSolicitante
	 * 
	 * @return El valor de tipoSolicitante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Establece el valor de tipoSolicitante
	 * 
	 * @param tipoSolicitante
	 *            El valor de tipoSolicitante por asignar
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Obtiene el valor de primerNombre
	 * 
	 * @return El valor de primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Establece el valor de primerNombre
	 * 
	 * @param primerNombre
	 *            El valor de primerNombre por asignar
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Obtiene el valor de segundoNombre
	 * 
	 * @return El valor de segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Establece el valor de segundoNombre
	 * 
	 * @param segundoNombre
	 *            El valor de segundoNombre por asignar
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Obtiene el valor de primerApellido
	 * 
	 * @return El valor de primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Establece el valor de primerApellido
	 * 
	 * @param primerApellido
	 *            El valor de primerApellido por asignar
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Obtiene el valor de segundoApellido
	 * 
	 * @return El valor de segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Establece el valor de segundoApellido
	 * 
	 * @param segundoApellido
	 *            El valor de segundoApellido por asignar
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Obtiene el valor de estadoCCF
	 * 
	 * @return El valor de estadoCCF
	 */
	public EstadoAfiliadoEnum getEstadoCCF() {
		return estadoCCF;
	}

	/**
	 * Establece el valor de estadoCCF
	 * 
	 * @param estadoCCF
	 *            El valor de estadoCCF por asignar
	 */
	public void setEstadoCCF(EstadoAfiliadoEnum estadoCCF) {
		this.estadoCCF = estadoCCF;
	}

	/**
	 * Obtiene el valor de estadoCartera
	 * 
	 * @return El valor de estadoCartera
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * Establece el valor de estadoCartera
	 * 
	 * @param estadoCartera
	 *            El valor de estadoCartera por asignar
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de digitoVerificacion
	 * 
	 * @return El valor de digitoVerificacion
	 */
	public Short getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * Establece el valor de digitoVerificacion
	 * 
	 * @param digitoVerificacion
	 *            El valor de digitoVerificacion por asignar
	 */
	public void setDigitoVerificacion(Short digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * Obtiene el valor de nombreRazonSocial
	 * 
	 * @return El valor de nombreRazonSocial
	 */
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	/**
	 * Establece el valor de nombreRazonSocial
	 * 
	 * @param nombreRazonSocial
	 *            El valor de nombreRazonSocial por asignar
	 */
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
}
