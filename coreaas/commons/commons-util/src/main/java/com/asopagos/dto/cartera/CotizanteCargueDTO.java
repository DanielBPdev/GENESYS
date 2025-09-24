package com.asopagos.dto.cartera;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.math.BigDecimal;

public class CotizanteCargueDTO {
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private String primerNombre;
    private String primerApellido;
    private BigDecimal deudaUnitaria;

    public CotizanteCargueDTO() {
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public BigDecimal getDeudaUnitaria() {
        return deudaUnitaria;
    }

    public void setDeudaUnitaria(BigDecimal deudaUnitaria) {
        this.deudaUnitaria = deudaUnitaria;
    }
}
