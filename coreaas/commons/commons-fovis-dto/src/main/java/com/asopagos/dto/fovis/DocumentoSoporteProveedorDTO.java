/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.dto.fovis;

import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.ProveedorModeloDTO;

/**
 *
 * @author linam
 */
public class DocumentoSoporteProveedorDTO {
    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -3370378871197987487L;

    /**
     * DTO con la informacion del oferente
     */
    private ProveedorModeloDTO proveedorDTO;

    /**
     * DTO con la informacion del documento de soporte
     */
    private DocumentoSoporteModeloDTO documentoSoporteDTO;

    /**
     * Método constructor
     */
    public DocumentoSoporteProveedorDTO() {
    }

    /**
     * @return the proveedorDTO
     */
    public ProveedorModeloDTO getProveedorDTO() {
        return proveedorDTO;
    }

    /**
     * @param proveedorDTO
     *        the proveedorDTO to set
     */
    public void setProveedorDTO(ProveedorModeloDTO proveedorDTO) {
        this.proveedorDTO = proveedorDTO;
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
