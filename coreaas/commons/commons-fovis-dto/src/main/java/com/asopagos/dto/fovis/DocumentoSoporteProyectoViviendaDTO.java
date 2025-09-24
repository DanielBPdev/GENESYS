package com.asopagos.dto.fovis;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;

/**
 * <b>Descripción</b> DTO que representa los datos para el registro de los documentos de soporte para el proyecto de vivienda
 * <b>HU-057</b>
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class DocumentoSoporteProyectoViviendaDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -799881856886077557L;

    /**
     * DTO con la informacion del oferente
     */
    private ProyectoSolucionViviendaModeloDTO proyectoViviendaDTO;

    /**
     * DTO con la informacion del documento de soporte
     */
    private DocumentoSoporteModeloDTO documentoSoporteDTO;

    /**
     * Método constructor
     */
    public DocumentoSoporteProyectoViviendaDTO() {
    }

    /**
     * @return the documentoSoporteDTO
     */
    public DocumentoSoporteModeloDTO getDocumentoSoporteDTO() {
        return documentoSoporteDTO;
    }

    /**
     * @param documentoSoporteDTO
     *        the documentoSoporteDTO to set
     */
    public void setDocumentoSoporteDTO(DocumentoSoporteModeloDTO documentoSoporteDTO) {
        this.documentoSoporteDTO = documentoSoporteDTO;
    }

    /**
     * @return the proyectoViviendaDTO
     */
    public ProyectoSolucionViviendaModeloDTO getProyectoViviendaDTO() {
        return proyectoViviendaDTO;
    }

    /**
     * @param proyectoViviendaDTO
     *        the proyectoViviendaDTO to set
     */
    public void setProyectoViviendaDTO(ProyectoSolucionViviendaModeloDTO proyectoViviendaDTO) {
        this.proyectoViviendaDTO = proyectoViviendaDTO;
    }

}
