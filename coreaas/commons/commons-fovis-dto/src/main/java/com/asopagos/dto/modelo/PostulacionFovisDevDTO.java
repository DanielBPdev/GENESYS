package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.fovis.ActaAsignacionFOVIS;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.PostulacionFovisDev;
import com.asopagos.entidades.ccf.fovis.SolicitudAsignacion;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * Contiene la información del Acta de Asignacion FOVIS - Procesos FOVIS 3.2.3
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class PostulacionFovisDevDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 3347595361492121695L;

    private Long idPostulacionDev;

    private Integer valorRestituir;

    private String medioPago;

    private Integer rendimientofinanciero;

    private String numeroIdQuienHaceDevolucion;

    private String tipoIdQuienHaceDevolucion;

    private String nombreCompleto;

    private Long postulacionfovis;

    private Long solicitudglobal;

    private Date fechaRegistro;

    /**
     * Constructor vacio
     */
    public PostulacionFovisDevDTO() {
    }

    public PostulacionFovisDev convertToEntity() {
        PostulacionFovisDev postulacionFovisDev = new PostulacionFovisDev();
        postulacionFovisDev.setValorRestituir(getValorRestituir());
        postulacionFovisDev.setSolicitudglobal(getSolicitudglobal());
        postulacionFovisDev.setRendimientofinanciero(getRendimientofinanciero());
        postulacionFovisDev.setNombreCompleto(getNombreCompleto());
        postulacionFovisDev.setPostulacionfovis(getPostulacionfovis());
        postulacionFovisDev.setTipoIdQuienHaceDevolucion(getTipoIdQuienHaceDevolucion());
        postulacionFovisDev.setNumeroIdQuienHaceDevolucion(getNumeroIdQuienHaceDevolucion());
        postulacionFovisDev.setMedioPago(getMedioPago());
        postulacionFovisDev.setFechaRegistro(new Date());
        return postulacionFovisDev;
    }

    public Long getIdPostulacionDev() {
        return idPostulacionDev;
    }

    public void setIdPostulacionDev(Long idPostulacionDev) {
        this.idPostulacionDev = idPostulacionDev;
    }

    public Integer getValorRestituir() {
        return valorRestituir;
    }

    public void setValorRestituir(Integer valorRestituir) {
        this.valorRestituir = valorRestituir;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public Integer getRendimientofinanciero() {
        return rendimientofinanciero;
    }

    public void setRendimientofinanciero(Integer rendimientofinanciero) {
        this.rendimientofinanciero = rendimientofinanciero;
    }

    public String getNumeroIdQuienHaceDevolucion() {
        return numeroIdQuienHaceDevolucion;
    }

    public void setNumeroIdQuienHaceDevolucion(String numeroIdQuienHaceDevolucion) {
        this.numeroIdQuienHaceDevolucion = numeroIdQuienHaceDevolucion;
    }

    public String getTipoIdQuienHaceDevolucion() {
        return tipoIdQuienHaceDevolucion;
    }

    public void setTipoIdQuienHaceDevolucion(String tipoIdQuienHaceDevolucion) {
        this.tipoIdQuienHaceDevolucion = tipoIdQuienHaceDevolucion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Long getPostulacionfovis() {
        return postulacionfovis;
    }

    public void setPostulacionfovis(Long postulacionfovis) {
        this.postulacionfovis = postulacionfovis;
    }

    public Long getSolicitudglobal() {
        return solicitudglobal;
    }

    public void setSolicitudglobal(Long solicitudglobal) {
        this.solicitudglobal = solicitudglobal;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "PostulacionFovisDevDTO{" +
                "idPostulacionDev=" + idPostulacionDev +
                ", valorRestituir=" + valorRestituir +
                ", medioPago='" + medioPago + '\'' +
                ", rendimientofinanciero=" + rendimientofinanciero +
                ", numeroIdQuienHaceDevolucion='" + numeroIdQuienHaceDevolucion + '\'' +
                ", tipoIdQuienHaceDevolucion='" + tipoIdQuienHaceDevolucion + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", postulacionfovis=" + postulacionfovis +
                ", solicitudglobal=" + solicitudglobal +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
