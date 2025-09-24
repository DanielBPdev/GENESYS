package com.asopagos.dto;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase que representa los parametros de consulta de Postulacion de persona
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class ParametroConsultaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Referencia entityManager para la ejecucion de consulta
     */
    @JsonIgnore
    private EntityManager entityManager;

    /**
     * Representa el tipo de identificación
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Representa el numero de identificación
     */
    private String numeroIdentificacion;

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager
     *        the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

}
