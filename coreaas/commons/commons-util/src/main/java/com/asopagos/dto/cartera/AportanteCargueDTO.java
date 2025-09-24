package com.asopagos.dto.cartera;

import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.math.BigDecimal;

public class AportanteCargueDTO {
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private TipoLineaCobroEnum lineaCobro;
    private Long periodo;
    private BigDecimal deudaTotal;

    public AportanteCargueDTO() {
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

    public TipoLineaCobroEnum getLineaCobro() {
        return lineaCobro;
    }

    public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(BigDecimal deudaTotal) {
        this.deudaTotal = deudaTotal;
    }
}
