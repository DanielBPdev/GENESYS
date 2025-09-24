package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.subsidiomonetario.pagos.DescuentosSubsidioAsignado;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import java.math.BigInteger;

@XmlRootElement
public class DetalleBandejaTransaccionesDTO implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 4194410649323554823L;

    private Long idBandeja;

    private String numeroRadicado;

    private String tipoNovedad;

    private String usuarioTransaccion;

    private Date fechaYHoraTransaccion;

    private Long idMedioDePagoDestino;

    private Long idMedioDePagoOrigen;

    private MedioDePagoModeloDTO medioDePagoOrigen;

    private MedioDePagoModeloDTO medioDePagoDestino;

    private BigInteger saldoTrasladado;


    public DetalleBandejaTransaccionesDTO() {
    }


    public DetalleBandejaTransaccionesDTO(Long idBandeja, String numeroRadicado, String tipoNovedad, 
        String usuarioTransaccion, Date fechaYHoraTransaccion, Long idMedioDePagoOrigen, 
        Long idMedioDePagoDestino,BigInteger saldoTrasladado) {
        this.idBandeja = idBandeja;
        this.numeroRadicado = numeroRadicado;
        this.tipoNovedad = tipoNovedad;
        this.usuarioTransaccion = usuarioTransaccion;
        this.fechaYHoraTransaccion = fechaYHoraTransaccion;
        this.idMedioDePagoOrigen = idMedioDePagoOrigen;
        this.idMedioDePagoDestino = idMedioDePagoDestino;
        this.saldoTrasladado = saldoTrasladado;
    }


    public Long getIdBandeja() {
        return this.idBandeja;
    }

    public void setIdBandeja(Long idBandeja) {
        this.idBandeja = idBandeja;
    }

    public String getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public String getTipoNovedad() {
        return this.tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public String getUsuarioTransaccion() {
        return this.usuarioTransaccion;
    }

    public void setUsuarioTransaccion(String usuarioTransaccion) {
        this.usuarioTransaccion = usuarioTransaccion;
    }

    public Date getFechaYHoraTransaccion() {
        return this.fechaYHoraTransaccion;
    }

    public void setFechaYHoraTransaccion(Date fechaYHoraTransaccion) {
        this.fechaYHoraTransaccion = fechaYHoraTransaccion;
    }

    public Long getIdMedioDePagoDestino() {
        return this.idMedioDePagoDestino;
    }

    public void setIdMedioDePagoDestino(Long idMedioDePagoDestino) {
        this.idMedioDePagoDestino = idMedioDePagoDestino;
    }

    public Long getIdMedioDePagoOrigen() {
        return this.idMedioDePagoOrigen;
    }

    public void setIdMedioDePagoOrigen(Long idMedioDePagoOrigen) {
        this.idMedioDePagoOrigen = idMedioDePagoOrigen;
    }

    public MedioDePagoModeloDTO getMedioDePagoOrigen() {
        return this.medioDePagoOrigen;
    }

    public void setMedioDePagoOrigen(MedioDePagoModeloDTO medioDePagoOrigen) {
        this.medioDePagoOrigen = medioDePagoOrigen;
    }

    public MedioDePagoModeloDTO getMedioDePagoDestino() {
        return this.medioDePagoDestino;
    }

    public void setMedioDePagoDestino(MedioDePagoModeloDTO medioDePagoDestino) {
        this.medioDePagoDestino = medioDePagoDestino;
    }

    public BigInteger getSaldoTrasladado() {
        return this.saldoTrasladado;
    }

    public void setSaldoTrasladado(BigInteger saldoTrasladado) {
        this.saldoTrasladado = saldoTrasladado;
    }


    @Override
    public String toString() {
        return "{" +
            " idBandeja='" + getIdBandeja() + "'" +
            ", numeroRadicado='" + getNumeroRadicado() + "'" +
            ", tipoNovedad='" + getTipoNovedad() + "'" +
            ", usuarioTransaccion='" + getUsuarioTransaccion() + "'" +
            ", fechaYHoraTransaccion='" + getFechaYHoraTransaccion() + "'" +
            ", medioDePagoOrigen='" + getMedioDePagoOrigen() + "'" +
            ", medioDePagoDestino='" + getMedioDePagoDestino() + "'" +
            ", saldoTrasladado='" + getSaldoTrasladado() + "'" +
            "}";
    }

}