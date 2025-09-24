/**
 * 
 */
package com.asopagos.dto.modelo;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;

/**
 * Clase DTO que contiene los datos de un documento solicitud.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class DocumentoSolicitudModeloDTO {

	/**
	 * Identificador del documento solicitud..
	 */
	private Long idDocumentoSolicitud;

	/**
	 * Id de la solicitud global.
	 */
	private Long idSolicitud;
	
	/**
	 * Atributo que indica el identificador del documento en el ECM.
	 */
	private Long identificadorDocumento;

	/**
	 * Atributo que indica el tipo de documento adjunto.
	 */
	private TipoDocumentoAdjuntoEnum tipoDocumento;

	/**
	 * Método que retorna el valor de idDocumentoSolicitud.
	 * @return valor de idDocumentoSolicitud.
	 */
	public Long getIdDocumentoSolicitud() {
		return idDocumentoSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idDocumentoSolicitud.
	 * @param valor para modificar idDocumentoSolicitud.
	 */
	public void setIdDocumentoSolicitud(Long idDocumentoSolicitud) {
		this.idDocumentoSolicitud = idDocumentoSolicitud;
	}

	/**
	 * Método que retorna el valor de idSolicitud.
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de identificadorDocumento.
	 * @return valor de identificadorDocumento.
	 */
	public Long getIdentificadorDocumento() {
		return identificadorDocumento;
	}

	/**
	 * Método encargado de modificar el valor de identificadorDocumento.
	 * @param valor para modificar identificadorDocumento.
	 */
	public void setIdentificadorDocumento(Long identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
	}

	/**
	 * Método que retorna el valor de tipoDocumento.
	 * @return valor de tipoDocumento.
	 */
	public TipoDocumentoAdjuntoEnum getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Método encargado de modificar el valor de tipoDocumento.
	 * @param valor para modificar tipoDocumento.
	 */
	public void setTipoDocumento(TipoDocumentoAdjuntoEnum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
