package com.asopagos.dto.fovis;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;

/**
 * <b>Descripción</b> DTO que representa los datos para el registro de los documentos de soporte para el oferente
 * <b>HU-051</b>
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class DocumentoSoporteOferenteDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -3370378871197987487L;

    /**
     * DTO con la informacion del oferente
     */
    private OferenteModeloDTO oferenteDTO;

    /**
     * DTO con la informacion del documento de soporte
     */
    private DocumentoSoporteModeloDTO documentoSoporteDTO;

    /**
     * Método constructor
     */
    public DocumentoSoporteOferenteDTO() {
    }

    /**
     * @return the oferenteDTO
     */
    public OferenteModeloDTO getOferenteDTO() {
        return oferenteDTO;
    }

    /**
     * @param oferenteDTO
     *        the oferenteDTO to set
     */
    public void setOferenteDTO(OferenteModeloDTO oferenteDTO) {
        this.oferenteDTO = oferenteDTO;
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

}
