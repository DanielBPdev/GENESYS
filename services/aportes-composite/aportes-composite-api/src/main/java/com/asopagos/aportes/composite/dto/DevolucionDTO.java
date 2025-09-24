package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.AnalisisDevolucionDTO;

/**
 * Clase DTO con los datos de Devoluciones manejados temporalmente.
 * 
 * @author <a href="criparra@heinsohn.com.co">Cristian David Parra Zuluaga </a>
 * @author <a href="fvasquez@heinsohn.com.co">Ferney Alonso Vásquez Benavides</a>
 */
@XmlRootElement
public class DevolucionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 482785595804133596L;

	/**
	 * Campo tipo DTO que representa los datos de la solicitud.
	 */
	private SolicitudDevolucionDTO solicitud;

	/**
	 * Campo tipo DTO que da forma al análisis de la devolución.
	 */
	private List<AnalisisDevolucionDTO> analisis;

	/**
	 * Descuentos por gestión de pago de operador de información. Un valor
	 * <code>null</code> en este campo, indica que este descuento no fue
	 * aplicado
	 */
	private BigDecimal descuentoGestionPagoOI;

	/**
	 * Descuentos por gestión financiera. Un valor <code>null</code> en este
	 * campo, indica que este descuento no fue aplicado
	 */
	private BigDecimal descuentoGestionFinanciera;

	/**
	 * Otros descuentos. Un valor <code>null</code> en este campo, indica que
	 * este descuento no fue aplicado
	 */
	private BigDecimal descuentoOtro;
	
	/**
	 * Gestionar información faltante.
	 */
	private GestionInformacionFaltanteDTO informacionFaltanteDTO;

	/**
	 * Método que retorna el valor de solicitud.
	 * 
	 * @return valor de solicitud.
	 */
	public SolicitudDevolucionDTO getSolicitud() {
		return solicitud;
	}

	/**
	 * Método encargado de modificar el valor de solicitud.
	 * 
	 * @param valor
	 *            para modificar solicitud.
	 */
	public void setSolicitud(SolicitudDevolucionDTO solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * Método que retorna el valor de analisis.
	 * 
	 * @return valor de analisis.
	 */
	public List<AnalisisDevolucionDTO> getAnalisis() {
		return analisis;
	}

	/**
	 * Método encargado de modificar el valor de analisis.
	 * 
	 * @param valor
	 *            para modificar analisis.
	 */
	public void setAnalisis(List<AnalisisDevolucionDTO> analisis) {
		this.analisis = analisis;
	}

	/**
	 * Obtiene el valor de descuentoGestionPagoOI
	 * 
	 * @return El valor de descuentoGestionPagoOI
	 */
	public BigDecimal getDescuentoGestionPagoOI() {
		return descuentoGestionPagoOI;
	}

	/**
	 * Establece el valor de descuentoGestionPagoOI
	 * 
	 * @param descuentoGestionPagoOI
	 *            El valor de descuentoGestionPagoOI por asignar
	 */
	public void setDescuentoGestionPagoOI(BigDecimal descuentoGestionPagoOI) {
		this.descuentoGestionPagoOI = descuentoGestionPagoOI;
	}

	/**
	 * Obtiene el valor de descuentoGestionFinanciera
	 * 
	 * @return El valor de descuentoGestionFinanciera
	 */
	public BigDecimal getDescuentoGestionFinanciera() {
		return descuentoGestionFinanciera;
	}

	/**
	 * Establece el valor de descuentoGestionFinanciera
	 * 
	 * @param descuentoGestionFinanciera
	 *            El valor de descuentoGestionFinanciera por asignar
	 */
	public void setDescuentoGestionFinanciera(BigDecimal descuentoGestionFinanciera) {
		this.descuentoGestionFinanciera = descuentoGestionFinanciera;
	}

	/**
	 * Obtiene el valor de descuentoOtro
	 * 
	 * @return El valor de descuentoOtro
	 */
	public BigDecimal getDescuentoOtro() {
		return descuentoOtro;
	}

	/**
	 * Establece el valor de descuentoOtro
	 * 
	 * @param descuentoOtro
	 *            El valor de descuentoOtro por asignar
	 */
	public void setDescuentoOtro(BigDecimal descuentoOtro) {
		this.descuentoOtro = descuentoOtro;
	}

    /**
     * Método que retorna el valor de informacionFaltanteDTO.
     * @return valor de informacionFaltanteDTO.
     */
    public GestionInformacionFaltanteDTO getInformacionFaltanteDTO() {
        return informacionFaltanteDTO;
    }

    /**
     * Método encargado de modificar el valor de informacionFaltanteDTO.
     * @param valor para modificar informacionFaltanteDTO.
     */
    public void setInformacionFaltanteDTO(GestionInformacionFaltanteDTO informacionFaltanteDTO) {
        this.informacionFaltanteDTO = informacionFaltanteDTO;
    }
}
