package com.asopagos.dto.subsidiomonetario.liquidacion;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la llave de las persona en el proceso de liquidación
 * <b>Módulo:</b> Asopagos - Transversal liquidación <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class KeyPersonaDTO {

    /**
     * Tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;

    /**
     * Constructor con datos básicos para la creación de la llave de persona
     * @param tipoIdentificacion
     *        valor del tipo de identificación
     * @param numeroIdentificacion
     *        valor del número de identificación
     */
    public KeyPersonaDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
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

    /**
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroIdentificacion == null) ? 0 : numeroIdentificacion.hashCode());
        result = prime * result + ((tipoIdentificacion == null) ? 0 : tipoIdentificacion.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyPersonaDTO other = (KeyPersonaDTO) obj;
        if (numeroIdentificacion == null) {
            if (other.numeroIdentificacion != null)
                return false;
        }
        else if (!numeroIdentificacion.equals(other.numeroIdentificacion))
            return false;
        if (tipoIdentificacion != other.tipoIdentificacion)
            return false;
        return true;
    }

}
