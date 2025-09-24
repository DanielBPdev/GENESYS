package com.asopagos.dto.cartera;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.CarteraModeloDTO;

/**
 * <b>Descripción: </b> Clase que representa la información manejada por la
 * funcionalidad de generación de liquidación de aportes en cartera <br/>
 * <b>Historia de Usuario: </b>HU-194
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class LiquidacionAporteCarteraDTO extends CarteraModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8299989640016617728L;

	/**
	 * Consecutivo de liquidación generado
	 */
	private String consecutivoLiquidacion;

	/**
	 * Identificador del documento almacenado en el ECM
	 */
	private String idECM;

	/**
	 * Nombre de la CCF
	 */
	private String nombreCcf;

	/**
	 * Departamento de la CCF
	 */
	private String departamentoCcf;

	/**
	 * Ciudad de la CCF
	 */
	private String ciudadCcf;

	/**
	 * Dirección de la CCF
	 */
	private String direccionCcf;

	/**
	 * Teléfono de la CCF
	 */
	private String telefonoCcf;

	/**
	 * Web de la CCF
	 */
	private String webCcf;

	/**
	 * Responsable de la CCF
	 */
	private String responsableCcf;

	/**
	 * Cargo responsable de la CCF
	 */
	private String cargoResponsableCcf;

	/**
	 * Logo de la CCF
	 */
	private String logoDeLaCcf;

	/**
	 * Logo súperservicios
	 */
	private String logoSuperservicios;

	/**
	 * Constructor
	 * 
	 * @param carteraDTO
	 *            Información del registro en cartera
	 */
	public LiquidacionAporteCarteraDTO(CarteraModeloDTO carteraDTO) {
		this.setIdCartera(carteraDTO.getIdCartera());
		this.setDeudaPresunta(carteraDTO.getDeudaPresunta());
		this.setIdPersona(carteraDTO.getIdPersona());
		this.setPeriodoDeuda(carteraDTO.getPeriodoDeuda());
		this.setTipoSolicitante(carteraDTO.getTipoSolicitante());
		this.setEstadoCartera(carteraDTO.getEstadoCartera());
		this.setEstadoOperacion(carteraDTO.getEstadoOperacion());
		this.setFechaCreacion(carteraDTO.getFechaCreacion());
		this.setMetodo(carteraDTO.getMetodo());
		this.setRiesgoIncobrabilidad(carteraDTO.getRiesgoIncobrabilidad());
		this.setTipoAccionCobro(carteraDTO.getTipoAccionCobro());
		this.setTipoDeuda(carteraDTO.getTipoDeuda());
		this.setTipoLineaCobro(carteraDTO.getTipoLineaCobro());
		this.setUsuarioTraspaso(carteraDTO.getUsuarioTraspaso());
		this.setFechaAsignacionAccion(carteraDTO.getFechaAsignacionAccion());
	}

	/**
	 * Obtiene el valor de consecutivoLiquidacion
	 * 
	 * @return El valor de consecutivoLiquidacion
	 */
	public String getConsecutivoLiquidacion() {
		return consecutivoLiquidacion;
	}

	/**
	 * Establece el valor de consecutivoLiquidacion
	 * 
	 * @param consecutivoLiquidacion
	 *            El valor de consecutivoLiquidacion por asignar
	 */
	public void setConsecutivoLiquidacion(String consecutivoLiquidacion) {
		this.consecutivoLiquidacion = consecutivoLiquidacion;
	}

	/**
	 * Obtiene el valor de idECM
	 * 
	 * @return El valor de idECM
	 */
	public String getIdECM() {
		return idECM;
	}

	/**
	 * Establece el valor de idECM
	 * 
	 * @param idECM
	 *            El valor de idECM por asignar
	 */
	public void setIdECM(String idECM) {
		this.idECM = idECM;
	}

	/**
	 * Obtiene el valor de nombreCcf
	 * 
	 * @return El valor de nombreCcf
	 */
	public String getNombreCcf() {
		return nombreCcf;
	}

	/**
	 * Establece el valor de nombreCcf
	 * 
	 * @param nombreCcf
	 *            El valor de nombreCcf por asignar
	 */
	public void setNombreCcf(String nombreCcf) {
		this.nombreCcf = nombreCcf;
	}

	/**
	 * Obtiene el valor de departamentoCcf
	 * 
	 * @return El valor de departamentoCcf
	 */
	public String getDepartamentoCcf() {
		return departamentoCcf;
	}

	/**
	 * Establece el valor de departamentoCcf
	 * 
	 * @param departamentoCcf
	 *            El valor de departamentoCcf por asignar
	 */
	public void setDepartamentoCcf(String departamentoCcf) {
		this.departamentoCcf = departamentoCcf;
	}

	/**
	 * Obtiene el valor de ciudadCcf
	 * 
	 * @return El valor de ciudadCcf
	 */
	public String getCiudadCcf() {
		return ciudadCcf;
	}

	/**
	 * Establece el valor de ciudadCcf
	 * 
	 * @param ciudadCcf
	 *            El valor de ciudadCcf por asignar
	 */
	public void setCiudadCcf(String ciudadCcf) {
		this.ciudadCcf = ciudadCcf;
	}

	/**
	 * Obtiene el valor de direccionCcf
	 * 
	 * @return El valor de direccionCcf
	 */
	public String getDireccionCcf() {
		return direccionCcf;
	}

	/**
	 * Establece el valor de direccionCcf
	 * 
	 * @param direccionCcf
	 *            El valor de direccionCcf por asignar
	 */
	public void setDireccionCcf(String direccionCcf) {
		this.direccionCcf = direccionCcf;
	}

	/**
	 * Obtiene el valor de telefonoCcf
	 * 
	 * @return El valor de telefonoCcf
	 */
	public String getTelefonoCcf() {
		return telefonoCcf;
	}

	/**
	 * Establece el valor de telefonoCcf
	 * 
	 * @param telefonoCcf
	 *            El valor de telefonoCcf por asignar
	 */
	public void setTelefonoCcf(String telefonoCcf) {
		this.telefonoCcf = telefonoCcf;
	}

	/**
	 * Obtiene el valor de webCcf
	 * 
	 * @return El valor de webCcf
	 */
	public String getWebCcf() {
		return webCcf;
	}

	/**
	 * Establece el valor de webCcf
	 * 
	 * @param webCcf
	 *            El valor de webCcf por asignar
	 */
	public void setWebCcf(String webCcf) {
		this.webCcf = webCcf;
	}

	/**
	 * Obtiene el valor de responsableCcf
	 * 
	 * @return El valor de responsableCcf
	 */
	public String getResponsableCcf() {
		return responsableCcf;
	}

	/**
	 * Establece el valor de responsableCcf
	 * 
	 * @param responsableCcf
	 *            El valor de responsableCcf por asignar
	 */
	public void setResponsableCcf(String responsableCcf) {
		this.responsableCcf = responsableCcf;
	}

	/**
	 * Obtiene el valor de cargoResponsableCcf
	 * 
	 * @return El valor de cargoResponsableCcf
	 */
	public String getCargoResponsableCcf() {
		return cargoResponsableCcf;
	}

	/**
	 * Establece el valor de cargoResponsableCcf
	 * 
	 * @param cargoResponsableCcf
	 *            El valor de cargoResponsableCcf por asignar
	 */
	public void setCargoResponsableCcf(String cargoResponsableCcf) {
		this.cargoResponsableCcf = cargoResponsableCcf;
	}

	/**
	 * Obtiene el valor de logoDeLaCcf
	 * 
	 * @return El valor de logoDeLaCcf
	 */
	public String getLogoDeLaCcf() {
		return logoDeLaCcf;
	}

	/**
	 * Establece el valor de logoDeLaCcf
	 * 
	 * @param logoDeLaCcf
	 *            El valor de logoDeLaCcf por asignar
	 */
	public void setLogoDeLaCcf(String logoDeLaCcf) {
		this.logoDeLaCcf = logoDeLaCcf;
	}

	/**
	 * Obtiene el valor de logoSuperservicios
	 * 
	 * @return El valor de logoSuperservicios
	 */
	public String getLogoSuperservicios() {
		return logoSuperservicios;
	}

	/**
	 * Establece el valor de logoSuperservicios
	 * 
	 * @param logoSuperservicios
	 *            El valor de logoSuperservicios por asignar
	 */
	public void setLogoSuperservicios(String logoSuperservicios) {
		this.logoSuperservicios = logoSuperservicios;
	}
}