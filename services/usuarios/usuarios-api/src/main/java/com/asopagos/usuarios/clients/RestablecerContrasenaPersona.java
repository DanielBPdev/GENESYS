package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class RestablecerContrasenaPersona extends ServiceClient{

    private String email;
    private ResultadoDTO result;
    
    public RestablecerContrasenaPersona(String email){
        super();
        this.email = email;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        WebTarget target = webTarget.path(path);
        return target.queryParam("email", this.email)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);
    }

    @Override
    protected void getResultData(Response response) {
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            result = response.readEntity(ResultadoDTO.class);
        } else {
            // Manejo de errores según sea necesario
            String errorMessage = response.readEntity(String.class);
            throw new RuntimeException("Error en el llamado al servicio: " + errorMessage);
        }
    }

    public ResultadoDTO getResult() {
        return result;
    }
}
