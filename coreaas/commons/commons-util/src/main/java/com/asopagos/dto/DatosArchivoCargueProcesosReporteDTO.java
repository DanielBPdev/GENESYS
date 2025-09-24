package com.asopagos.dto;

import java.util.List;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;

import java.io.Serializable;

public class DatosArchivoCargueProcesosReporteDTO implements Serializable {

    private static final long serialVersionUID = 5165142646354284L;

    private List<String> cabeceras;
    private TipoProcesoMasivoEnum tipoProceso;
    private EstadoCargueMasivoEnum estado;
    private Long fechaInicio;
    private Long fechaFin;

    public List<String> getCabeceras() {
        return this.cabeceras;
    }

    public void setCabeceras(List<String> cabeceras) {
        this.cabeceras = cabeceras;
    }

    public TipoProcesoMasivoEnum getTipoProceso() {
        return this.tipoProceso;
    }

    public void setTipoProceso(TipoProcesoMasivoEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public EstadoCargueMasivoEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoCargueMasivoEnum estado) {
        this.estado = estado;
    }

    public Long getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "{" +
            " cabeceras='" + getCabeceras() + "'" +
            ", tipoProceso='" + getTipoProceso() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}