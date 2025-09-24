package com.asopagos.listaschequeo.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;

/**
 * <b>Descripción:</b> DTO para intercambio de requisitos por listas de chequeo
 * <b>Historia de Usuario:</b> HU-TRA-059 Digitar datos y verificar requisitos
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class ConsultarRequisitosListaChequeoOutDTO implements Serializable {
    
    
    private Long idRequisito;
    
    private String nombreRequisito;
    
    private EstadoRequisitoTipoSolicitanteEnum estadoRequisito;
    
    private String identificadorDocumento;

    private Short versionArchivo;
    
    private String tipoRequisito;
    
    private String textoAyuda;
    
    private String identificadorDocumentoPrevio;
    
    private Long fechaRecepcionDocumentos;

    /**
     * Contructor vacio
     */
    public ConsultarRequisitosListaChequeoOutDTO() {
    	
	}
    
    /**
     * Constructor de la clase
     * @param idRequisito
     * @param nombreRequisito
     * @param estadoRequisito
     */
    public ConsultarRequisitosListaChequeoOutDTO(Long idRequisito, 
            String nombreRequisito, EstadoRequisitoTipoSolicitanteEnum estadoRequisito) {
        this.idRequisito = idRequisito;
        this.nombreRequisito = nombreRequisito;
        this.estadoRequisito = estadoRequisito;
    }    
    
    /**
     * Constructor de la clase
     * @param idRequisito
     * @param nombreRequisito
     * @param estadoRequisito
     * @param identificadorDocumento
     */
    public ConsultarRequisitosListaChequeoOutDTO(Long idRequisito, String nombreRequisito, 
            EstadoRequisitoTipoSolicitanteEnum estadoRequisito, String identificadorDocumento) {
        this.idRequisito = idRequisito;
        this.nombreRequisito = nombreRequisito;
        this.estadoRequisito = estadoRequisito;
        this.identificadorDocumento = identificadorDocumento;
    }    
    
    /**
     * @return the idRequisito
     */
    public Long getIdRequisito() {
        return idRequisito;
    }

    /**
     * @param idRequisito the idRequisito to set
     */
    public void setIdRequisito(Long idRequisito) {
        this.idRequisito = idRequisito;
    }

    /**
     * @return the nombreRequisito
     */
    public String getNombreRequisito() {
        return nombreRequisito;
    }

    /**
     * @param nombreRequisito the nombreRequisito to set
     */
    public void setNombreRequisito(String nombreRequisito) {
        this.nombreRequisito = nombreRequisito;
    }

    /**
     * @return the estadoRequisito
     */
    public EstadoRequisitoTipoSolicitanteEnum getEstadoRequisito() {
        return estadoRequisito;
    }

    /**
     * @param estadoRequisito the estadoRequisito to set
     */
    public void setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum estadoRequisito) {
        this.estadoRequisito = estadoRequisito;
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
     * @return the versionArchivo
     */
    public Short getVersionArchivo() {
        return versionArchivo;
    }

    /**
     * @param versionArchivo the versionArchivo to set
     */
    public void setVersionArchivo(Short versionArchivo) {
        this.versionArchivo = versionArchivo;
    }

	/**
	 * @return the tipoRequisito
	 */
	public String getTipoRequisito() {
		return tipoRequisito;
	}

	/**
	 * @param tipoRequisito the tipoRequisito to set
	 */
	public void setTipoRequisito(String tipoRequisito) {
		this.tipoRequisito = tipoRequisito;
	}

    /**
     * @return the textoAyuda
     */
    public String getTextoAyuda() {
        return textoAyuda;
    }

    /**
     * @param textoAyuda the textoAyuda to set
     */
    public void setTextoAyuda(String textoAyuda) {
        this.textoAyuda = textoAyuda;
    }

    /**
     * @return the identificadorDocumentoPrevio
     */
    public String getIdentificadorDocumentoPrevio() {
        return identificadorDocumentoPrevio;
    }

    /**
     * @param identificadorDocumentoPrevio the identificadorDocumentoPrevio to set
     */
    public void setIdentificadorDocumentoPrevio(String identificadorDocumentoPrevio) {
        this.identificadorDocumentoPrevio = identificadorDocumentoPrevio;
    }

    /**
     * @return the fechaRecepcionDocumentos
     */
    public Long getFechaRecepcionDocumentos() {
        return fechaRecepcionDocumentos;
    }

    /**
     * @param fechaRecepcionDocumentos the fechaRecepcionDocumentos to set
     */
    public void setFechaRecepcionDocumentos(Long fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }
}
