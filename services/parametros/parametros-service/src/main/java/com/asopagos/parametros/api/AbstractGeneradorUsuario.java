package main.java.com.asopagos.parametros.api;

import com.asopagos.usuarios.clients.GenerarTokenAccesoSystem;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.rest.security.filter.AccessToken;
import com.asopagos.util.ContextUtil;

public abstract class AbstractGeneradorUsuario {

    /**
     * Provee los datos del usuario sistema al contexto para el registro de la tarea
     */
    public void initContextUsuarioSistema() {
        // Se genera el token de conexion
        GenerarTokenAccesoSystem tokenAcceso = new GenerarTokenAccesoSystem();
        tokenAcceso.execute();
        TokenDTO token = tokenAcceso.getResult();
        // Se agrega al contexto el usuario y el token
        ContextUtil.addValueContext(AccessToken.class, new AccessToken(token.getToken()));
    }
}
