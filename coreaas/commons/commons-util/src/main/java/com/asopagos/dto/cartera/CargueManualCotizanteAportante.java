package com.asopagos.dto.cartera;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CargueManualCotizanteAportante implements Serializable {
    /**
     * Serial version
     */
    private static final long serialVersionUID = -7977673988421442286L;

    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String razonSocial;
    private TipoLineaCobroEnum lineaCobro;
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitanteMovimientoAporte;
    private EstadoCarteraEnum estadoCartera;
    private TipoAccionCobroEnum tipoAccionCobro;
    private TipoDeudaEnum tipoDeuda;
    private Long periodo;
    private BigDecimal deudaPresunta;
    private Long idCartera;
    private List<String> errores;

    public CargueManualCotizanteAportante() {
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public TipoLineaCobroEnum getLineaCobro() {
        return lineaCobro;
    }

    public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitanteMovimientoAporte() {
        return tipoSolicitanteMovimientoAporte;
    }

    public void setTipoSolicitanteMovimientoAporte(TipoSolicitanteMovimientoAporteEnum tipoSolicitanteMovimientoAporte) {
        this.tipoSolicitanteMovimientoAporte = tipoSolicitanteMovimientoAporte;
    }

    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    public TipoAccionCobroEnum getTipoAccionCobro() {
        return tipoAccionCobro;
    }

    public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
        this.tipoAccionCobro = tipoAccionCobro;
    }

    public TipoDeudaEnum getTipoDeuda() {
        return tipoDeuda;
    }

    public void setTipoDeuda(TipoDeudaEnum tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getDeudaPresunta() {
        return deudaPresunta;
    }

    public void setDeudaPresunta(BigDecimal deudaPresunta) {
        this.deudaPresunta = deudaPresunta;
    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
