package com.asopagos.dto.webservices;

import java.io.Serializable;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;

public class UsuarioDTO implements Serializable, Cloneable{

    @NotNull(message = "El campo tipoID es obligatorio.")
    TipoIdentificacionEnum tipoID;

    @NotNull(message = "El campo identificacionUsuario es obligatorio.")
    String identificacionUsuario;

    @NotNull(message = "El campo emailUsuario es obligatorio.")
    @Email(message = "El campo emailUsuario no cumple con la estructura de un correo valido.")
    String emailUsuario;

    String razonSocialUsuario;

    @NotNull(message = "El campo tipoAfiliado es obligatorio.")
    TipoTipoSolicitanteEnum tipoAfiliado;

    public UsuarioDTO() {
    }

    public UsuarioDTO(TipoIdentificacionEnum tipoID, String identificacionUsuario, String emailUsuario, String razonSocialUsuario, TipoTipoSolicitanteEnum tipoAfiliado) {
        this.tipoID = tipoID;
        this.identificacionUsuario = identificacionUsuario;
        this.emailUsuario = emailUsuario;
        this.razonSocialUsuario = razonSocialUsuario;
        this.tipoAfiliado = tipoAfiliado;
    }

    public TipoIdentificacionEnum getTipoID() {
        return this.tipoID;
    }

    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    public String getIdentificacionUsuario() {
        return this.identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public String getEmailUsuario() {
        return this.emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getRazonSocialUsuario() {
        return this.razonSocialUsuario;
    }

    public void setRazonSocialUsuario(String razonSocialUsuario) {
        this.razonSocialUsuario = razonSocialUsuario;
    }

    public TipoTipoSolicitanteEnum getTipoAfiliado() {
        return this.tipoAfiliado;
    }

    public void setTipoAfiliado(TipoTipoSolicitanteEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoID='" + getTipoID() + "'" +
            ", identificacionUsuario='" + getIdentificacionUsuario() + "'" +
            ", emailUsuario='" + getEmailUsuario() + "'" +
            ", razonSocialUsuario='" + getRazonSocialUsuario() + "'" +
            ", tipoAfiliado='" + getTipoAfiliado() + "'" +
            "}";
    }

}
