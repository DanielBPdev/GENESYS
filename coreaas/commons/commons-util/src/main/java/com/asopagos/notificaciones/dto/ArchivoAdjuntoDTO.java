package com.asopagos.notificaciones.dto;

import java.io.Serializable;

/**
 * DTO con la información que componen un archivo adjunto para un mensaje email
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 *
 */
public class ArchivoAdjuntoDTO  implements Serializable {
	
	/** version serial */
	private static final long serialVersionUID = 1L;
	
	/** Nombre del archivo */
	private String fileName;

	/** Contenido del archivo. */
	private byte[] content;

	/** Posibles tipos de archivo */
	private String fileType;

	/** Formato de fecha */
	private String formatDate;

	/**
	 * Indica si el nombre del archivo debe ser o no concatenado con la fecha en
	 * formato fomatDate
	 */
	private Boolean nameDate;

	/**
	 * Indica si un archivo adjunto de tipo Imagen va como archivo adjunto o
	 * como contenido de la notificación.
	 */
	private Boolean integratedImage;
	
	/** Nombre del archivo dentro del ECM con versión*/
    private String idECM;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the formatDate
	 */
	public String getFormatDate() {
		return formatDate;
	}

	/**
	 * @param formatDate
	 *            the formatDate to set
	 */
	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	/**
	 * @return the nameDate
	 */
	public Boolean getNameDate() {
		return nameDate;
	}

	/**
	 * @param nameDate
	 *            the nameDate to set
	 */
	public void setNameDate(Boolean nameDate) {
		this.nameDate = nameDate;
	}

	/**
	 * @return the integratedImage
	 */
	public Boolean getIntegratedImage() {
		return integratedImage;
	}

	/**
	 * @param integratedImage
	 *            the integratedImage to set
	 */
	public void setIntegratedImage(Boolean integratedImage) {
		this.integratedImage = integratedImage;
	}

    /**
     * @return the idECM
     */
    public String getIdECM() {
        return idECM;
    }

    /**
     * @param idECM the idECM to set
     */
    public void setIdECM(String idECM) {
        this.idECM = idECM;
    }
}
