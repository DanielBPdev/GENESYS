package com.asopagos.dto.cartera;

import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

public class CarteraAportantePersonaDTO {

    private TipoLineaCobroEnum lineaCobroEnum;

    private EstadoCarteraEnum estadoCarteraEnum;

    private EstadoOperacionCarteraEnum estadoOperacionCarteraEnum;

    private Long idPersona;

    private Long periodo;

    public CarteraAportantePersonaDTO() {
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public TipoLineaCobroEnum getLineaCobroEnum() {
        return lineaCobroEnum;
    }

    public EstadoCarteraEnum getEstadoCarteraEnum() {
        return estadoCarteraEnum;
    }

    public void setEstadoCarteraEnum(EstadoCarteraEnum estadoCarteraEnum) {
        this.estadoCarteraEnum = estadoCarteraEnum;
    }

    public EstadoOperacionCarteraEnum getEstadoOperacionCarteraEnum() {
        return estadoOperacionCarteraEnum;
    }

    public void setEstadoOperacionCarteraEnum(EstadoOperacionCarteraEnum estadoOperacionCarteraEnum) {
        this.estadoOperacionCarteraEnum = estadoOperacionCarteraEnum;
    }

    public void setLineaCobroEnum(TipoLineaCobroEnum lineaCobroEnum) {
        this.lineaCobroEnum = lineaCobroEnum;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }
}
