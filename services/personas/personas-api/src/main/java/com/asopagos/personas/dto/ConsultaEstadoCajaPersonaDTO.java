package com.asopagos.personas.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de un empleador e identificacion de una persona y su estado respecto a la caja
 * 
 * @author Julián Andrés Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class ConsultaEstadoCajaPersonaDTO implements Serializable {

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = 933225276165430223L;
    /**
     * Identificador del empleador
     */
    private Long idEmpleador;
    /**
     * Número de identificacion de la persona
     */
    private String numeroIdentificacion;
    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Estado del afiliado
     */
    private EstadoAfiliadoEnum estadoAfiliadoEnum;

    /**
     * 
     */
    public ConsultaEstadoCajaPersonaDTO() {
      
    }
    
    /**
     * Método que retorna el valor de idEmpleador.
     * @return valor de idEmpleador.
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de idEmpleador.
     * @param valor
     *        para modificar idEmpleador.
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de estadoAfiliadoEnum.
     * @return valor de estadoAfiliadoEnum.
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoEnum() {
        return estadoAfiliadoEnum;
    }

    /**
     * Método encargado de modificar el valor de estadoAfiliadoEnum.
     * @param valor
     *        para modificar estadoAfiliadoEnum.
     */
    public void setEstadoAfiliadoEnum(EstadoAfiliadoEnum estadoAfiliadoEnum) {
        this.estadoAfiliadoEnum = estadoAfiliadoEnum;
    }

}
