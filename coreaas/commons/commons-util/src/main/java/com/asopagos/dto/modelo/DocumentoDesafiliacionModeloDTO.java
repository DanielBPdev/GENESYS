package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.DocumentoDesafiliacion;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;

/**
 * <b>Descripcion:</b> Clase que representa en modo de DTO
 * a la entidad DocumentoDesafiliacion<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DocumentoDesafiliacionModeloDTO implements Serializable {

    /**
     * Serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la documento de Desafiliacion
     */
    private Long idDocumentoDesafiliacion;

    /**
     * Id de la Solicitud de Desafiliacion
     */
    private Long idSolicitudDesafiliacion;

    /**
     * Objeto documento soporte
     */
    private DocumentoSoporteModeloDTO documentoSoporte;

    /**
     * Constructor de la clase
     */
    public DocumentoDesafiliacionModeloDTO() {
    }

    /**
     * Metodo convierte de entidad a DTO
     * @param documentoDesafiliacion
     *        recibe como parametro una entidad DocumentoDesafiliacion
     * @return DTO DocumentoDesafiliacionModeloDTO
     */
    public DocumentoDesafiliacionModeloDTO convertToDTO(DocumentoDesafiliacion documentoDesafiliacion) {

        /* Se construye el objeto */
        this.setIdDocumentoDesafiliacion(
                documentoDesafiliacion.getIdDocumentoDesafiliacion() != null ? documentoDesafiliacion.getIdDocumentoDesafiliacion() : null);
        this.setIdSolicitudDesafiliacion(
                documentoDesafiliacion.getIdSolicitudDesafiliacion() != null ? getIdSolicitudDesafiliacion() : null);
        
        if (documentoDesafiliacion.getDocumentoSoporte() != null) {
            DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
            documentoSoporteModeloDTO.convertToDTO(documentoDesafiliacion.getDocumentoSoporte());
            this.setDocumentoSoporte(documentoSoporteModeloDTO);
        }
        return this;
    }

    /**
     * Metodo que convierte de DTO a enity
     * @return una entidad DocumentoDesafiliacion
     */
    public DocumentoDesafiliacion convertToEntity() {

        /* Se construye el objeto */
        DocumentoDesafiliacion documentoDesafiliacion = new DocumentoDesafiliacion();
        documentoDesafiliacion
                .setIdDocumentoDesafiliacion(this.getIdDocumentoDesafiliacion() != null ? this.getIdDocumentoDesafiliacion() : null);
        documentoDesafiliacion
                .setIdSolicitudDesafiliacion(this.getIdSolicitudDesafiliacion() != null ? this.getIdSolicitudDesafiliacion() : null);
        if (this.getDocumentoSoporte() != null) {
            DocumentoSoporte documento = this.documentoSoporte.convertToEntity();
            documentoDesafiliacion.setDocumentoSoporte(documento != null ? documento : null);
            }
        
        return documentoDesafiliacion;
    }

    /**
     * Método que retorna el valor de idSolicitudDesafiliacion.
     * @return valor de idSolicitudDesafiliacion.
     */
    public Long getIdSolicitudDesafiliacion() {
        return idSolicitudDesafiliacion;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudDesafiliacion.
     * @param valor
     *        para modificar idSolicitudDesafiliacion.
     */
    public void setIdSolicitudDesafiliacion(Long idSolicitudDesafiliacion) {
        this.idSolicitudDesafiliacion = idSolicitudDesafiliacion;
    }

    /**
     * Método que retorna el valor de idDocumentoSoporte.
     * @return valor de idDocumentoSoporte.
     */
    public DocumentoSoporteModeloDTO getDocumentoSoporte() {
        return documentoSoporte;
    }

    /**
     * Método encargado de modificar el valor de idDocumentoSoporte.
     * @param valor
     *        para modificar idDocumentoSoporte.
     */
    public void setDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    /**
     * Método que retorna el valor de idDocumentoDesafiliacion.
     * @return valor de idDocumentoDesafiliacion.
     */
    public Long getIdDocumentoDesafiliacion() {
        return idDocumentoDesafiliacion;
    }

    /**
     * Método encargado de modificar el valor de idDocumentoDesafiliacion.
     * @param valor
     *        para modificar idDocumentoDesafiliacion.
     */
    public void setIdDocumentoDesafiliacion(Long idDocumentoDesafiliacion) {
        this.idDocumentoDesafiliacion = idDocumentoDesafiliacion;
    }
}
