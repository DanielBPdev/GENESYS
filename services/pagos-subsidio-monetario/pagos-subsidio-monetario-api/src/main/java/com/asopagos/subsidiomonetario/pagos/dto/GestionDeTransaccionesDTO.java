package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.subsidiomonetario.pagos.DescuentosSubsidioAsignado;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;

@XmlRootElement
public class GestionDeTransaccionesDTO implements Serializable, Cloneable{
 
    private String numeroRadicado;

    private MedioDePagoModeloDTO medioDePagoOrigen;

    private MedioDePagoModeloDTO medioDePagoDestino;

    private Long idMedioDePagoOrigen;

    private Long idMedioDePagoDestino;

    private Date fechaYHoraCreacion;

    private Date fechaYHoraModificacion;

    private EstadoBandejaTransaccionEnum estado;

    public GestionDeTransaccionesDTO(){}


    public GestionDeTransaccionesDTO(String numeroRadicado,Long idMedioDePagoOrigen,
        Long idMedioDePagoDestino,Date fechaYHoraCreacion, Date fechaYHoraModificacion, String estado) {
        this.numeroRadicado = numeroRadicado;
        this.idMedioDePagoOrigen = idMedioDePagoOrigen;
        this.idMedioDePagoDestino = idMedioDePagoDestino;
        this.fechaYHoraCreacion = fechaYHoraCreacion;
        this.fechaYHoraModificacion = fechaYHoraModificacion;
        this.estado = EstadoBandejaTransaccionEnum.valueOf(estado);
    }



    public String getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
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

    public Long getIdMedioDePagoOrigen() {
        return this.idMedioDePagoOrigen;
    }

    public void setIdMedioDePagoOrigen(Long idMedioDePagoOrigen) {
        this.idMedioDePagoOrigen = idMedioDePagoOrigen;
    }

    public Long getIdMedioDePagoDestino() {
        return this.idMedioDePagoDestino;
    }

    public void setIdMedioDePagoDestino(Long idMedioDePagoDestino) {
        this.idMedioDePagoDestino = idMedioDePagoDestino;
    }

    public Date getFechaYHoraCreacion() {
        return this.fechaYHoraCreacion;
    }

    public void setFechaYHoraCreacion(Date fechaYHoraCreacion) {
        this.fechaYHoraCreacion = fechaYHoraCreacion;
    }

    public Date getFechaYHoraModificacion() {
        return this.fechaYHoraModificacion;
    }

    public void setFechaYHoraModificacion(Date fechaYHoraModificacion) {
        this.fechaYHoraModificacion = fechaYHoraModificacion;
    }

    public EstadoBandejaTransaccionEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoBandejaTransaccionEnum estado) {
        this.estado = estado;
    }

}
