package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.DocumentosSeguimientoGestionDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/DocumentosSeguimiento/Gestion
 */
public class CreateDocumentosSeguimientoGestion extends ServiceClient {

    private DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private DocumentosSeguimientoGestionDTO result;

    public CreateDocumentosSeguimientoGestion(DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO) {
        super();
        this.documentosSeguimientoGestionDTO = documentosSeguimientoGestionDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(documentosSeguimientoGestionDTO == null ? null : Entity.json(documentosSeguimientoGestionDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = response.readEntity(DocumentosSeguimientoGestionDTO.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public DocumentosSeguimientoGestionDTO getResult() {
        return result;
    }

    public DocumentosSeguimientoGestionDTO getDocumentosSeguimientoGestionDTO() {
        return documentosSeguimientoGestionDTO;
    }

    public void setDocumentosSeguimientoGestionDTO(DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO) {
        this.documentosSeguimientoGestionDTO = documentosSeguimientoGestionDTO;
    }
}