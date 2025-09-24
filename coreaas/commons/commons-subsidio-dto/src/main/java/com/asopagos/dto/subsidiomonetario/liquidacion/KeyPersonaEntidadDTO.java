package com.asopagos.dto.subsidiomonetario.liquidacion;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la llave de las persona junto a la entidad por la cual tiene descuento en el proceso de
 * liquidación
 * <b>Módulo:</b> Asopagos - Transversal liquidación fallecimiento <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class KeyPersonaEntidadDTO {

    /**
     * Tipo de identificación del beneficiario
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación del beneficiario
     */
    private String numeroIdentificacion;

    /**
     * Código de la entidad de descuento
     */
    private String codigoEntidad;

    /**
     * Constructor con los datos requeridos para la creación de la llave Beneficiario-Entidad
     * @param tipoIdentificacion
     *        Tipo de identificación del beneficiario
     * @param numeroIdentificacion
     *        Número de identificación del beneficiario
     * @param codigoEntidad
     *        Código de la entidad de descuento
     */
    public KeyPersonaEntidadDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String codigoEntidad) {
        super();
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.codigoEntidad = codigoEntidad;
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
     * @return the codigoEntidad
     */
    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    /**
     * @param codigoEntidad
     *        the codigoEntidad to set
     */
    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    /**
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigoEntidad == null) ? 0 : codigoEntidad.hashCode());
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
        KeyPersonaEntidadDTO other = (KeyPersonaEntidadDTO) obj;
        if (codigoEntidad == null) {
            if (other.codigoEntidad != null)
                return false;
        }
        else if (!codigoEntidad.equals(other.codigoEntidad))
            return false;
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
