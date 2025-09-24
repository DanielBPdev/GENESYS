package com.asopagos.usuarios.dto;

import java.util.List;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.usuarios.dto.UsuarioGestionDTO;
import java.io.Serializable;

public class DatosConsultaGestionUsuariosDTO implements Serializable {

    private List<UsuarioGestionDTO> usuarios; // Lista de resultados de las consultas

    // Constructor por defecto (sin parámetros)
    public DatosConsultaGestionUsuariosDTO() {
    }

    // Constructor
    public DatosConsultaGestionUsuariosDTO(List<UsuarioGestionDTO> usuarios) {
        this.usuarios = usuarios;
    }

    // Getter y Setter
    public List<UsuarioGestionDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioGestionDTO> usuarios) {
        this.usuarios = usuarios;
    }
}