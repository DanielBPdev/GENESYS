package com.asopagos.cartera.dto;

import java.io.Serializable;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> Clase que representa los datos de un aportante que está
 * en proceso de gestión de cobro <br/>
 * <b>Historia de Usuario: </b> Req-223
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AportanteAccionCobroDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4652857155927746149L;

	/**
	 * Indica si el aportante autoriza envío de correo electrónico
	 */
	private Boolean autorizaEnvioCorreo;

	/**
	 * Tipo de aportante
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoAportante;

	/**
	 * Identificador único del registro en cartera para el aportante
	 */
	private Long idCartera;

	/**
	 * Tipo de acción de cobro asignada al aportante
	 */
	private TipoAccionCobroEnum accionCobro;

	/**
	 * Tipo de identificación del aportante
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del aportante
	 */
	private String numeroIdentificacion;

	/**
	 * Obtiene el valor de autorizaEnvioCorreo
	 * 
	 * @return El valor de autorizaEnvioCorreo
	 */
	public Boolean getAutorizaEnvioCorreo() {
		return autorizaEnvioCorreo;
	}

	/**
	 * Establece el valor de autorizaEnvioCorreo
	 * 
	 * @param autorizaEnvioCorreo
	 *            El valor de autorizaEnvioCorreo por asignar
	 */
	public void setAutorizaEnvioCorreo(Boolean autorizaEnvioCorreo) {
		this.autorizaEnvioCorreo = autorizaEnvioCorreo;
	}

	/**
	 * Obtiene el valor de tipoAportante
	 * 
	 * @return El valor de tipoAportante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
		return tipoAportante;
	}

	/**
	 * Establece el valor de tipoAportante
	 * 
	 * @param tipoAportante
	 *            El valor de tipoAportante por asignar
	 */
	public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
		this.tipoAportante = tipoAportante;
	}

	/**
	 * Obtiene el valor de idCartera
	 * 
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * Establece el valor de idCartera
	 * 
	 * @param idCartera
	 *            El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

	/**
	 * Obtiene el valor de accionCobro
	 * 
	 * @return El valor de accionCobro
	 */
	public TipoAccionCobroEnum getAccionCobro() {
		return accionCobro;
	}

	/**
	 * Establece el valor de accionCobro
	 * 
	 * @param accionCobro
	 *            El valor de accionCobro por asignar
	 */
	public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
		this.accionCobro = accionCobro;
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
}
