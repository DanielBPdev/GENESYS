package com.asopagos.entidades.pagadoras.dto;

import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.afiliaciones.TipoDocumentoEntidadPagadoraEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripción:</b> DTO para el servicio entidades pagadoras 
 * <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class DocumentoEntidadPagadoraDTO {
	
	@NotNull(groups = {GrupoActualizacion.class})
	private Long idDocumentoEntidadPagadora; 
	
	private Long idEntidadPagadora;
	
	@NotNull(groups = {GrupoActualizacion.class, GrupoCreacion.class})
	private TipoDocumentoEntidadPagadoraEnum tipoDocumento;
	
	@NotNull(groups = {GrupoActualizacion.class, GrupoCreacion.class})
	private String identificadorDocumento;
	
	private Short version;
	
	@NotNull(groups = {GrupoActualizacion.class, GrupoCreacion.class})
	private String docName;

	/**
	 * @return the idEntidadPagadora
	 */
	public Long getIdEntidadPagadora() {
		return idEntidadPagadora;
	}

	/**
	 * @param idEntidadPagadora the idEntidadPagadora to set
	 */
	public void setIdEntidadPagadora(Long idEntidadPagadora) {
		this.idEntidadPagadora = idEntidadPagadora;
	}

	/**
	 * @return the tipoDocumento
	 */
	public TipoDocumentoEntidadPagadoraEnum getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(TipoDocumentoEntidadPagadoraEnum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the identificadorDocumento
	 */
	public String getIdentificadorDocumento() {
		return identificadorDocumento;
	}

	/**
	 * @param identificadorDocumento the identificadorDocumento to set
	 */
	public void setIdentificadorDocumento(String identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
	}

	/**
	 * @return the version
	 */
	public Short getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Short version) {
		this.version = version;
	}

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @param docName the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * @return the idDocumentoEntidadPagadora
	 */
	public Long getIdDocumentoEntidadPagadora() {
		return idDocumentoEntidadPagadora;
	}

	/**
	 * @param idDocumentoEntidadPagadora the idDocumentoEntidadPagadora to set
	 */
	public void setIdDocumentoEntidadPagadora(Long idDocumentoEntidadPagadora) {
		this.idDocumentoEntidadPagadora = idDocumentoEntidadPagadora;
	}
}
