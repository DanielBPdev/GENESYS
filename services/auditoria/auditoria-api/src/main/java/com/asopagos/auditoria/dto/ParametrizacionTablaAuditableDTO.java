package com.asopagos.auditoria.dto;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.auditoria.ParametrizacionTablaAuditable;;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class ParametrizacionTablaAuditableDTO {
    
    private Long id;
    
    private String nombreTabla;
    
    private Boolean crear;
    
    private Boolean actualizar;
    
    private Boolean consultar;
    
    public static ParametrizacionTablaAuditableDTO convertToDTO(ParametrizacionTablaAuditable eventoAuditoria){
        ParametrizacionTablaAuditableDTO tablaAuditableDTO = new ParametrizacionTablaAuditableDTO();
        tablaAuditableDTO.setNombreTabla(eventoAuditoria.getNombreTabla());
        tablaAuditableDTO.setCrear(eventoAuditoria.getCrear());
        tablaAuditableDTO.setActualizar(eventoAuditoria.getActualizar());
        tablaAuditableDTO.setConsultar(eventoAuditoria.getConsultar());
        return tablaAuditableDTO;
    }
    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the nombreTabla
     */
    public String getNombreTabla() {
        return nombreTabla;
    }
    /**
     * @param nombreTabla the nombreTabla to set
     */
    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }
    /**
     * @return the crear
     */
    public Boolean getCrear() {
        return crear;
    }
    /**
     * @param crear the crear to set
     */
    public void setCrear(Boolean crear) {
        this.crear = crear;
    }
    /**
     * @return the actualizar
     */
    public Boolean getActualizar() {
        return actualizar;
    }
    /**
     * @param actualizar the actualizar to set
     */
    public void setActualizar(Boolean actualizar) {
        this.actualizar = actualizar;
    }
    /**
     * @return the consultar
     */
    public Boolean getConsultar() {
        return consultar;
    }
    /**
     * @param consultar the consultar to set
     */
    public void setConsultar(Boolean consultar) {
        this.consultar = consultar;
    }
    
    

}




