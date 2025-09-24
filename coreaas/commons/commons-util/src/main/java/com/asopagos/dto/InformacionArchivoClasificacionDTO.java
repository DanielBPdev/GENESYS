package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que representa la informacion del procesamiento de analisis y clasificación de 
 * un archivo procedente para escaneo masivo
 * 
 * <b>Módulo:</b> Asopagos - HU Transversal<br/>
 * 
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> </a>
 * 
 */
public class InformacionArchivoClasificacionDTO implements Serializable{


	/**
     * Indica si el procesamiento de fue exitodo
     */
    private Boolean procesamientoExitoso;
    
    /**
     * Indica un mensaje desde el servicio sobre el estado de procesamiento del mismo
     */
    private String mensajeEstadoProcesamiento;
    
    /**
	 * Lista que corresponde con los documentos identificados y almacenados, en proceso de analisis y procesamiento exitoso 
	 */
    private List<InformacionArchivoDTO> archivosClasificados;
    
    /**
     * Indica la cantidad de documentos identificados en el archivo
     */
    private Integer documentosIdentificados;
    
    /**
     * Indica la cantidad de documentos clasificados correctamente
     */
    private Integer documentosClasificados;
    
    /**
	 * @return the procesamientoExitoso
	 */
	public Boolean getProcesamientoExitoso() {
		return procesamientoExitoso;
	}

	/**
	 * @param procesamientoExitoso the procesamientoExitoso to set
	 */
	public void setProcesamientoExitoso(Boolean procesamientoExitoso) {
		this.procesamientoExitoso = procesamientoExitoso;
	}

	/**
	 * @return the mensajeEstadoProcesamiento
	 */
	public String getMensajeEstadoProcesamiento() {
		return mensajeEstadoProcesamiento;
	}

	/**
	 * @param mensajeEstadoProcesamiento the mensajeEstadoProcesamiento to set
	 */
	public void setMensajeEstadoProcesamiento(String mensajeEstadoProcesamiento) {
		this.mensajeEstadoProcesamiento = mensajeEstadoProcesamiento;
	}

	/**
	 * @return the archivosClasificados
	 */
	public List<InformacionArchivoDTO> getArchivosClasificados() {
		return archivosClasificados;
	}

	/**
	 * @param archivosClasificados the archivosClasificados to set
	 */
	public void setArchivosClasificados(List<InformacionArchivoDTO> archivosClasificados) {
		this.archivosClasificados = archivosClasificados;
	}

	public Integer getDocumentosIdentificados() {
		return documentosIdentificados;
	}

	public void setDocumentosIdentificados(Integer documentosIdentificados) {
		this.documentosIdentificados = documentosIdentificados;
	}

	public Integer getDocumentosClasificados() {
		return documentosClasificados;
	}

	public void setDocumentosClasificados(Integer documentosClasificados) {
		this.documentosClasificados = documentosClasificados;
	}
    
}