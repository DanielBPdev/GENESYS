package com.asopagos.sat.dto;

import com.asopagos.sat.dto.SolicitudAfiliacionDTO;
import com.asopagos.sat.dto.EmpleadorDTO;


public class RadicacionSatDTO {
    private String resultadoRadicacion;
    private SolicitudAfiliacionDTO solicitudAfiliacion;
    private String idTarea;
    private String tipoTransaccion;
    private String idInstanciaProceso;
    private EmpleadorDTO empleador;

    public String getResultadoRadicacion() {
        return resultadoRadicacion;
    }

    public void setResultadoRadicacion(String resultadoRadicacion) {
        this.resultadoRadicacion = resultadoRadicacion;
    }

    public SolicitudAfiliacionDTO getSolicitudAfiliacion() {
        return solicitudAfiliacion;
    }

    public void setSolicitudAfiliacion(SolicitudAfiliacionDTO solicitudAfiliacion) {
        this.solicitudAfiliacion = solicitudAfiliacion;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    public EmpleadorDTO getEmpleador() {
        return empleador;
    }

    public void setEmpleador(EmpleadorDTO empleador) {
        this.empleador = empleador;
    }
}
