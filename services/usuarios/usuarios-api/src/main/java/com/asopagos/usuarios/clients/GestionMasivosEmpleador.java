package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/empleador/gestionMasivosEmpleador
 */
public class GestionMasivosEmpleador extends ServiceClient {

    private List<UsuarioCCF> usuarios;
    private UserDTO userDTO;

    /** Atributo que almacena los datos resultado del llamado al servicio */
 	private UsuarioCCF result;

    // Constructor para aceptar una lista de usuarios
    public GestionMasivosEmpleador(List<UsuarioCCF> usuarios) {
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

    /**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UsuarioCCF getResult() {
		return result;
	}

    // Getters y setters para la lista de usuarios
    public void setUsuarios(List<UsuarioCCF> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuarioCCF> getUsuarios() {
        return usuarios;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}