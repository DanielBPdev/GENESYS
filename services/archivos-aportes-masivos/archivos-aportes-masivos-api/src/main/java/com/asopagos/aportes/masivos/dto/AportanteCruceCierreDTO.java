package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
/**
 * <b>Descripcion:</b> DTO empelado para la definición y consulta de aportantes con su tipo de afiliación número de identificación,
 * su ID de registro en BD y día hábil de vencimiento de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-223-169 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class AportanteCruceCierreDTO extends QueryFilterInDTO {
    private static final long serialVersionUID = -3413493276135059523L;

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String nombre;

    private String tipoAportante;
    
    
    /** Constructor por defecto */
    public AportanteCruceCierreDTO(){}


    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
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

 
}
