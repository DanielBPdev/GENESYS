package com.asopagos.fovis.clients;

import com.asopagos.dto.PostulacionAsignacionDTO;
import com.asopagos.dto.modelo.PostulacionFovisDevDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarPostulacionAsignacion
 */
public class CrearActualizarPostulacionFovisDev extends ServiceClient {
    private PostulacionFovisDevDTO postulacionFovisDevDTO;


    public CrearActualizarPostulacionFovisDev(PostulacionFovisDevDTO postulacionFovisDevDTO){
        super();
        this.postulacionFovisDevDTO=postulacionFovisDevDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(postulacionFovisDevDTO == null ? null : Entity.json(postulacionFovisDevDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

    public PostulacionFovisDevDTO getPostulacionFovisDevDTO() {
        return postulacionFovisDevDTO;
    }

    public void setPostulacionFovisDevDTO(PostulacionFovisDevDTO postulacionFovisDevDTO) {
        this.postulacionFovisDevDTO = postulacionFovisDevDTO;
    }
}