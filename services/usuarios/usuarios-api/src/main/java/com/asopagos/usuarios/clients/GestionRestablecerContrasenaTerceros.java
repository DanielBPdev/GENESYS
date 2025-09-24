package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.ResultadoDTO;
import com.asopagos.usuarios.dto.CambiarContrasenaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/gestionRestablecerContrasenaTerceros
 */
public class GestionRestablecerContrasenaTerceros extends ServiceClient { 

    private CambiarContrasenaDTO dto;
    private ResultadoDTO result;

    public GestionRestablecerContrasenaTerceros(CambiarContrasenaDTO dto) {
        super();
        this.dto = dto;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        WebTarget target = webTarget.path(path);
        return target.request(MediaType.APPLICATION_JSON)
                     .post(Entity.json(dto));
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

    public void setDto(CambiarContrasenaDTO dto) {
        this.dto = dto;
    }

    public CambiarContrasenaDTO getDto() {
        return dto;
    }
}