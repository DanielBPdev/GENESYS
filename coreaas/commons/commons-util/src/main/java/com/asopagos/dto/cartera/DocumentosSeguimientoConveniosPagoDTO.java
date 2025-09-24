package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.Date;

public class DocumentosSeguimientoConveniosPagoDTO implements Serializable {

    private static final long serialVersionUID = -1592367226059764113L;

    private Long dscpId;
    private Long dscpNrOperacion;
    private Date dscpFecha;
    private String dscpValorDeuda;
    private Date dscpPeriodo;
    private Long dscpNrCuotas;
    private Date dscpFechaInicial;
    private Date dscpFechaFinal;
    private String dscpResultado;
    private String dscpSoporteDocumental;
    private Long dscpNorConvenio;
    private String dscpUsuario;

    public DocumentosSeguimientoConveniosPagoDTO() {
    }

    public DocumentosSeguimientoConveniosPagoDTO(Long dscpId, Long dscpNrOperacion, Date dscpFecha, String dscpValorDeuda, Date dscpPeriodo, Long dscpNrCuotas, Date dscpFechaInicial, Date dscpFechaFinal, String dscpResultado, String dscpSoporteDocumental) {
        this.dscpId = dscpId;
        this.dscpNrOperacion = dscpNrOperacion;
        this.dscpFecha = dscpFecha;
        this.dscpValorDeuda = dscpValorDeuda;
        this.dscpPeriodo = dscpPeriodo;
        this.dscpNrCuotas = dscpNrCuotas;
        this.dscpFechaInicial = dscpFechaInicial;
        this.dscpFechaFinal = dscpFechaFinal;
        this.dscpResultado = dscpResultado;
        this.dscpSoporteDocumental = dscpSoporteDocumental;
    }

    public Long getDscpId() {
        return dscpId;
    }

    public void setDscpId(Long dscpId) {
        this.dscpId = dscpId;
    }

    public Long getDscpNrOperacion() {
        return dscpNrOperacion;
    }

    public void setDscpNrOperacion(Long dscpNrOperacion) {
        this.dscpNrOperacion = dscpNrOperacion;
    }

    public Date getDscpFecha() {
        return dscpFecha;
    }

    public void setDscpFecha(Date dscpFecha) {
        this.dscpFecha = dscpFecha;
    }

    public String getDscpValorDeuda() {
        return dscpValorDeuda;
    }

    public void setDscpValorDeuda(String dscpValorDeuda) {
        this.dscpValorDeuda = dscpValorDeuda;
    }

    public Date getDscpPeriodo() {
        return dscpPeriodo;
    }

    public void setDscpPeriodo(Date dscpPeriodo) {
        this.dscpPeriodo = dscpPeriodo;
    }

    public Long getDscpNrCuotas() {
        return dscpNrCuotas;
    }

    public void setDscpNrCuotas(Long dscpNrCuotas) {
        this.dscpNrCuotas = dscpNrCuotas;
    }

    public Date getDscpFechaInicial() {
        return dscpFechaInicial;
    }

    public void setDscpFechaInicial(Date dscpFechaInicial) {
        this.dscpFechaInicial = dscpFechaInicial;
    }

    public Date getDscpFechaFinal() {
        return dscpFechaFinal;
    }

    public void setDscpFechaFinal(Date dscpFechaFinal) {
        this.dscpFechaFinal = dscpFechaFinal;
    }

    public String getDscpResultado() {
        return dscpResultado;
    }

    public void setDscpResultado(String dscpResultado) {
        this.dscpResultado = dscpResultado;
    }

    public String getDscpSoporteDocumental() {
        return dscpSoporteDocumental;
    }

    public void setDscpSoporteDocumental(String dscpSoporteDocumental) {
        this.dscpSoporteDocumental = dscpSoporteDocumental;
    }

    public Long getDscpNorConvenio() {
        return dscpNorConvenio;
    }

    public void setDscpNorConvenio(Long dscpNorConvenio) {
        this.dscpNorConvenio = dscpNorConvenio;
    }

    public String getDscpUsuario() {
        return dscpUsuario;
    }

    public void setDscpUsuario(String dscpUsuario) {
        this.dscpUsuario = dscpUsuario;
    }
}
