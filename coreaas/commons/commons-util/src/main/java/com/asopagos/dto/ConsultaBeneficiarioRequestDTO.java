package com.asopagos.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
public class ConsultaBeneficiarioRequestDTO {
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacionBeneficiario;
    private String numeroIdentificacionAfiliado;
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    public String getNumeroIdentificacionBeneficiario() {
        return this.numeroIdentificacionBeneficiario;
    }
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }
    public String getNumeroIdentificacionAfiliado() {
        return this.numeroIdentificacionAfiliado;
    }
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }
   
}
