package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import javax.validation.constraints.NotNull;

public class ConsultarSaldoSubsidioTransaccionDTO implements Serializable{

    @NotNull(message = "El Campo ID Admin es obligatorio")
    private TipoIdentificacionEnum tipoIdAdmin;
    @NotNull(message = "El Campo ID Admin es obligatorio")
    private String numeroIdAdmin;
    @NotNull(message = "El Campo ID Admin es obligatorio")
    private String user;
    @NotNull(message = "El Campo ID Admin es obligatorio")
    private String password;

    public ConsultarSaldoSubsidioTransaccionDTO() {
    }

    public ConsultarSaldoSubsidioTransaccionDTO(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin, String user, String password) {
        this.tipoIdAdmin = tipoIdAdmin;
        this.numeroIdAdmin = numeroIdAdmin;
        this.user = user;
        this.password = password;
    }

    public TipoIdentificacionEnum getTipoIdAdmin() {
        return tipoIdAdmin;
    }

    public void setTipoIdAdmin(TipoIdentificacionEnum tipoIdAdmin) {
        this.tipoIdAdmin = tipoIdAdmin;
    }

    public String getNumeroIdAdmin() {
        return numeroIdAdmin;
    }

    public void setNumeroIdAdmin(String numeroIdAdmin) {
        this.numeroIdAdmin = numeroIdAdmin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
