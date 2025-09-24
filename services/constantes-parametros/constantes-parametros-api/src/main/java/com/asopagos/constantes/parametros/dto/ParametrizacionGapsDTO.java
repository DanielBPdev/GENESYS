package com.asopagos.constantes.parametros.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que representara los datos de una entidad <br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class ParametrizacionGapsDTO implements Serializable{

    private String id;

    private String proceso;

    private String nombreParametro;

    private String descripcion;

    private String usuarioModificacion;

    private String fechaModificacion;

    private String versionLiberacion;

    private String tipoDatos;

    private String estado;

    private String glpi;

    public String getGlpi() {
        return this.glpi;
    }

    public void setGlpi(String glpi) {
        this.glpi = glpi;
    }


    public ParametrizacionGapsDTO(){
        
    }

   

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProceso() {
        return this.proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getNombreParametro() {
        return this.nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }


    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }


    public String getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getVersionLiberacion() {
        return this.versionLiberacion;
    }

    public void setVersionLiberacion(String versionLiberacion) {
        this.versionLiberacion = versionLiberacion;
    }

    public String getTipoDatos() {
        return this.tipoDatos;
    }

    public void setTipoDatos(String tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
}
