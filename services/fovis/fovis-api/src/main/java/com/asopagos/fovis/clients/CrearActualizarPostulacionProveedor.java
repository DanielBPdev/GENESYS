package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.PostulacionProvOfeDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarPostulacionProveedor
 */
public class CrearActualizarPostulacionProveedor extends ServiceClient {
    private PostulacionProvOfeDTO postulacionProvOfeDTO;


    public CrearActualizarPostulacionProveedor(PostulacionProvOfeDTO postulacionProvOfeDTO){
        super();
        this.postulacionProvOfeDTO=postulacionProvOfeDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(postulacionProvOfeDTO == null ? null : Entity.json(postulacionProvOfeDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

    public PostulacionProvOfeDTO getPostulacionFovisDevDTO() {
        return postulacionProvOfeDTO;
    }

    public void setPostulacionFovisDevDTO(PostulacionProvOfeDTO postulacionProveedorDTO) {
        this.postulacionProvOfeDTO = postulacionProveedorDTO;
    }
}