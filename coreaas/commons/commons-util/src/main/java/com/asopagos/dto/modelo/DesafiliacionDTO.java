package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
@XmlRootElement
public class DesafiliacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Numero de radicacion de la solicitud
     */
    @NotNull
    private String numeroRadicacion;

    /**
     * Id de la tarea en cuestion
     */
    private Long idTarea;

    /**
     * Observacion del analista
     */
    private String observacionAnalista;

    /**
     * Lista de documentos adjuntos
     */
    private List<DocumentoSoporteModeloDTO> documentoSoporteModeloDTOs;
    
    /**
     * Lista de aportantes seleccionados para desafiliacion
     */
    private List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs;
    
    /**
     * Constructor de la clase
     */
    public DesafiliacionDTO() {
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * Método que retorna el valor de idTarea.
     * @return valor de idTarea.
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * Método encargado de modificar el valor de idTarea.
     * @param valor para modificar idTarea.
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * Método que retorna el valor de observacionAnalista.
     * @return valor de observacionAnalista.
     */
    public String getObservacionAnalista() {
        return observacionAnalista;
    }

    /**
     * Método encargado de modificar el valor de observacionAnalista.
     * @param valor para modificar observacionAnalista.
     */
    public void setObservacionAnalista(String observacionAnalista) {
        this.observacionAnalista = observacionAnalista;
    }

    /**
     * Método que retorna el valor de documentoSoporteModeloDTOs.
     * @return valor de documentoSoporteModeloDTOs.
     */
    public List<DocumentoSoporteModeloDTO> getDocumentoSoporteModeloDTOs() {
        return documentoSoporteModeloDTOs;
    }

    /**
     * Método encargado de modificar el valor de documentoSoporteModeloDTOs.
     * @param valor para modificar documentoSoporteModeloDTOs.
     */
    public void setDocumentoSoporteModeloDTOs(List<DocumentoSoporteModeloDTO> documentoSoporteModeloDTOs) {
        this.documentoSoporteModeloDTOs = documentoSoporteModeloDTOs;
    }

    /**
     * Método que retorna el valor de desafiliacionAportanteDTOs.
     * @return valor de desafiliacionAportanteDTOs.
     */
    public List<DesafiliacionAportanteDTO> getDesafiliacionAportanteDTOs() {
        return desafiliacionAportanteDTOs;
    }

    /**
     * Método encargado de modificar el valor de desafiliacionAportanteDTOs.
     * @param valor para modificar desafiliacionAportanteDTOs.
     */
    public void setDesafiliacionAportanteDTOs(List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs) {
        this.desafiliacionAportanteDTOs = desafiliacionAportanteDTOs;
    }

    @Override
    public String toString() {
        return "DesafiliacionDTO{" +
                "numeroRadicacion='" + numeroRadicacion + '\'' +
                ", idTarea=" + idTarea +
                ", observacionAnalista='" + observacionAnalista + '\'' +
                ", documentoSoporteModeloDTOs=" + documentoSoporteModeloDTOs +
                ", desafiliacionAportanteDTOs=" + desafiliacionAportanteDTOs +
                '}';
    }
}
