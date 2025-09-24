package com.asopagos.usuarios.service;

import java.util.List;
import com.asopagos.usuarios.dto.UsuarioDTO;

public interface KeycloakQuery {
    
    public List<UsuarioDTO> obtenerUsuariosPorAtributo(String nombreAtributo, String valorAtributo);
}
