package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.fovis.PostulacionProvOfe;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class PostulacionProvOfeDTO implements Serializable {

    private static final long serialVersionUID = 3347595361492121695L;

    private Long idPostulacionProveedor;

    private SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionFovis;

    private String proveedor;

    private Integer ValorDesembolsoProveedor;

    private Date fechaRegistro;

    private Long proyectoSolucionVivienda;

    private Long oferente;

    public PostulacionProvOfeDTO() {
    }

    public PostulacionProvOfe convertToEntity() {
        PostulacionProvOfe postulacionProveedor = new PostulacionProvOfe();
        postulacionProveedor.setProveedor(getProveedor());
        postulacionProveedor.setValorDesembolsoProveedor(getValorDesembolsoProveedor());
        postulacionProveedor.setSolicitudLegalizacionFovis(getSolicitudLegalizacionFovis().convertToEntity());
        postulacionProveedor.setFechaRegistro(getFechaRegistro());
        postulacionProveedor.setProyectoSolucionVivienda(getProyectoSolucionVivienda());
        postulacionProveedor.setOferente(getOferente());
        return postulacionProveedor;
    }

    public Long getIdPostulacionProveedor() {
        return idPostulacionProveedor;
    }

    public void setIdPostulacionProveedor(Long idPostulacionProveedor) {
        this.idPostulacionProveedor = idPostulacionProveedor;
    }

    public SolicitudLegalizacionDesembolsoModeloDTO getSolicitudLegalizacionFovis() {
        return solicitudLegalizacionFovis;
    }

    public void setSolicitudLegalizacionFovis(SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionFovis) {
        this.solicitudLegalizacionFovis = solicitudLegalizacionFovis;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getValorDesembolsoProveedor() {
        return ValorDesembolsoProveedor;
    }

    public void setValorDesembolsoProveedor(Integer valorDesembolsoProveedor) {
        ValorDesembolsoProveedor = valorDesembolsoProveedor;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getProyectoSolucionVivienda() {
        return proyectoSolucionVivienda;
    }

    public void setProyectoSolucionVivienda(Long proyectoSolucionVivienda) {
        this.proyectoSolucionVivienda = proyectoSolucionVivienda;
    }

    public Long getOferente() {
        return oferente;
    }

    public void setOferente(Long oferente) {
        this.oferente = oferente;
    }

    @Override
    public String toString() {
        return "PostulacionProvOfeDTO{" +
                "idPostulacionProveedor=" + idPostulacionProveedor +
                ", solicitudLegalizacionFovis=" + solicitudLegalizacionFovis +
                ", proveedor='" + proveedor + '\'' +
                ", ValorDesembolsoProveedor=" + ValorDesembolsoProveedor +
                ", fechaRegistro=" + fechaRegistro +
                ", proyectoSolucionVivienda=" + proyectoSolucionVivienda +
                ", oferente=" + oferente +
                '}';
    }
}
