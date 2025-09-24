package com.asopagos.bandejainconsistencias.dto;

/**
 * <b>Descripcion:</b> Clase que contiene el identificador y la version del documento de una planilla OI o OF <br/>
 * <b>Módulo:</b> Asopagos - HU 392 <br/>
 *
 * @author  <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */

public class IdentificadorDocumentoDTO {

    /**
     * Identificador del archivo almacenado en el repositorio ECM
     */
    private String idDocumento;
    
    /**
     * Versión del documento en el repositorio ECM
     */
    private String versionDocumento;

	/**
	 * @return the idDocumento
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the versionDocumento
	 */
	public String getVersionDocumento() {
		return versionDocumento;
	}

	/**
	 * @param versionDocumento the versionDocumento to set
	 */
	public void setVersionDocumento(String versionDocumento) {
		this.versionDocumento = versionDocumento;
	}

	/**
	 * @param idDocumento
	 * @param versionDocumento
	 */
	public IdentificadorDocumentoDTO(String idDocumento, String versionDocumento) {
		super();
		this.idDocumento = idDocumento;
		this.versionDocumento = versionDocumento;
	}
    
	public IdentificadorDocumentoDTO() {}
}
