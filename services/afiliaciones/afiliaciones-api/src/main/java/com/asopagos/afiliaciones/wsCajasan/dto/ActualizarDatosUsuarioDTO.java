package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.TiposUsuarioWebServiceEnum;;

public class ActualizarDatosUsuarioDTO {
    private TipoIdentificacionEnum tipoDcto;
    private Long documento;
    private String email;
    private String telefono;
    private TiposUsuarioWebServiceEnum usuario;

    // Constructor vacío
    public ActualizarDatosUsuarioDTO() {
    }

    // Constructor con todos los atributos
    public ActualizarDatosUsuarioDTO(TipoIdentificacionEnum tipoDcto, Long documento, String email, String telefono, TiposUsuarioWebServiceEnum usuario) {
        this.tipoDcto = tipoDcto;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    // Getters y Setters
    public TipoIdentificacionEnum getTipoDcto() {
        return tipoDcto;
    }

    public void setTipoDcto(TipoIdentificacionEnum tipoDcto) {
        this.tipoDcto = tipoDcto;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TiposUsuarioWebServiceEnum getUsuario() {
        return usuario;
    }

    public void setUsuario(TiposUsuarioWebServiceEnum usuario) {
        this.usuario = usuario;
    }

}
