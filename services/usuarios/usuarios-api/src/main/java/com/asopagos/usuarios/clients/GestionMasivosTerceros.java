package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioCCF;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/terceros/gestionMasivosTerceros
 */
public class GestionMasivosTerceros extends ServiceClient {

    private List<UsuarioCCF> usuarios;

    // Constructor para aceptar una lista de usuarios
    public GestionMasivosTerceros(List<UsuarioCCF> usuarios) {
        super();
        this.usuarios = usuarios;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        // Enviar la lista de usuarios en lugar de un solo objeto
        Response response = webTarget.path(path)
            .request(MediaType.APPLICATION_JSON)
            .post(usuarios == null ? null : Entity.json(usuarios));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        // Aquí puedes manejar los datos de la respuesta si es necesario
    }

    // Getters y setters para la lista de usuarios
    public void setUsuarios(List<UsuarioCCF> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuarioCCF> getUsuarios() {
        return usuarios;
    }
}