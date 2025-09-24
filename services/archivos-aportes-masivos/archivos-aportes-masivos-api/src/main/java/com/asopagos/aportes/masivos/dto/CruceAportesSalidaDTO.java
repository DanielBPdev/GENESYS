
package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;


/**
 * <b>Descripcion:</b> DTO empelado para la definición y consulta de aportantes con su tipo de afiliación número de identificación,
 * su ID de registro en BD y día hábil de vencimiento de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-223-169 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CruceAportesSalidaDTO implements Serializable {
    private static final long serialVersionUID = -3413493276135059523L;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String nombre;

    private String tipoAportante;

    private String estadoCCF;

    private String tipoAfiliacionCCF;

    private String fechaRetiro;
    
    
    /** Constructor por defecto */
    public CruceAportesSalidaDTO(){}


    public CruceAportesSalidaDTO(String tipoIdentificacion, String numeroIdentificacion, String nombre, String tipoAportante, String estadoCCF, String tipoAfiliacionCCF, String fechaRetiro) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.tipoAportante = tipoAportante;
        this.estadoCCF = estadoCCF;
        this.tipoAfiliacionCCF = tipoAfiliacionCCF;
        this.fechaRetiro = fechaRetiro;
    }


    public String getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoAportante() {
        return this.tipoAportante;
    }

    public void setTipoAportante(String tipoAportante) {
        this.tipoAportante = tipoAportante;
    }

    public String getEstadoCCF() {
        return this.estadoCCF;
    }

    public void setEstadoCCF(String estadoCCF) {
        this.estadoCCF = estadoCCF;
    }

    public String getTipoAfiliacionCCF() {
        return this.tipoAfiliacionCCF;
    }

    public void setTipoAfiliacionCCF(String tipoAfiliacionCCF) {
        this.tipoAfiliacionCCF = tipoAfiliacionCCF;
    }

    public String getFechaRetiro() {
        return this.fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
 
}
