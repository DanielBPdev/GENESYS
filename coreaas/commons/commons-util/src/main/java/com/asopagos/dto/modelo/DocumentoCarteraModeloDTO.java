package com.asopagos.dto.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.cartera.DocumentoCartera;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;

/**
 * <b>Descripción: </b> Clase que representa los datos de un documento
 * electrónico de soporte Cartera <br/>
 * <b>Historia de Usuario: </b> Req-223
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class DocumentoCarteraModeloDTO extends DocumentoSoporteModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 5040316948438448153L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idDocumentoCartera;

	/**
	 * Identificador del registro de Cartera
	 */
	private Long idCartera;

	/**
	 * Tipo de acción de cobro
	 */
	private TipoAccionCobroEnum accionCobro;

	/**
	 * Consecutivo del documento, cuando es liquidación de aportes (Acción 2C)
	 */
	private String consecutivoLiquidacion;

	/**
	 * Método encargado de convertir el DTO a entidad
	 * 
	 * @return La entidad equivalente
	 */
	public DocumentoCartera convertToDocumentoCarteraEntity() {
		DocumentoCartera documentoCartera = new DocumentoCartera();
		documentoCartera.setId(this.getIdDocumentoCartera());
		documentoCartera.setIdCartera(this.getIdCartera());
		DocumentoSoporte documentoSoporte = super.convertToEntity();
		documentoCartera.setDocumentoSoporte(documentoSoporte);
		documentoCartera.setAccionCobro(this.getAccionCobro());
		documentoCartera.setConsecutivoLiquidacion(this.consecutivoLiquidacion);
		return documentoCartera;
	}

	/**
	 * Método encargado de convertir una entidad <code>DocumentoCartera</code>
	 * al DTO
	 * 
	 * @param documentoCartera
	 *            La entidad a convertir
	 */
	public void convertToDTO(DocumentoCartera documentoCartera) {
		if (documentoCartera.getDocumentoSoporte() != null) {
			super.convertToDTO(documentoCartera.getDocumentoSoporte());
		}
		this.setAccionCobro(documentoCartera.getAccionCobro());
		this.setIdCartera(documentoCartera.getIdCartera());
		this.setIdDocumentoCartera(documentoCartera.getId());
		this.setConsecutivoLiquidacion(documentoCartera.getConsecutivoLiquidacion());
	}

	/**
	 * Método que obtiene el DTO de la superclase
	 * 
	 * @return Objeto <code>DocumentoSoporteModeloDTO</code> equivalente
	 */
	public DocumentoSoporteModeloDTO obtenerDocumentoSoporteDTO() {
		DocumentoSoporteModeloDTO documentoDTO = new DocumentoSoporteModeloDTO();
		documentoDTO.setDescripcionComentarios(this.getDescripcionComentarios());
		documentoDTO.setFechaHoraCargue(this.getFechaHoraCargue());
		documentoDTO.setIdDocumentoSoporte(this.getIdDocumentoSoporte());
		documentoDTO.setIdentificacionDocumento(this.getIdentificacionDocumento());
		documentoDTO.setNombre(this.getNombre());
		documentoDTO.setTipoDocumento(this.getTipoDocumento());
		documentoDTO.setVersionDocumento(this.getVersionDocumento());
		return documentoDTO;
	}

	/**
	 * Obtiene el valor de idDocumentoCartera
	 * 
	 * @return El valor de idDocumentoCartera
	 */
	public Long getIdDocumentoCartera() {
		return idDocumentoCartera;
	}

	/**
	 * Establece el valor de idDocumentoCartera
	 * 
	 * @param idDocumentoCartera
	 *            El valor de idDocumentoCartera por asignar
	 */
	public void setIdDocumentoCartera(Long idDocumentoCartera) {
		this.idDocumentoCartera = idDocumentoCartera;
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
}
