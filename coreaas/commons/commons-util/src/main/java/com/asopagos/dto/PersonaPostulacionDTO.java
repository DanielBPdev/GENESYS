package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class PersonaPostulacionDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador postulacion
     */
    private Long idPostulacionFovis;

    /**
     * Estado del hogar
     */
    private EstadoHogarEnum estadoHogar;

    /**
     * Identificador persona novedad
     */
    private Long idPersonaNovedad;

    /**
     * Numero identificacion persona
     */
    private String numeroIdentificacion;

    /**
     * Tipo identificacion persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * @return the idPostulacionFovis
     */
    public Long getIdPostulacionFovis() {
        return idPostulacionFovis;
    }

    /**
     * @param idPostulacionFovis
     *        the idPostulacionFovis to set
     */
    public void setIdPostulacionFovis(Long idPostulacionFovis) {
        this.idPostulacionFovis = idPostulacionFovis;
    }

    /**
     * @return the estadoHogar
     */
    public EstadoHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * @param estadoHogar
     *        the estadoHogar to set
     */
    public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    /**
     * @return the idPersonaNovedad
     */
    public Long getIdPersonaNovedad() {
        return idPersonaNovedad;
    }

    /**
     * @param idPersonaNovedad
     *        the idPersonaNovedad to set
     */
    public void setIdPersonaNovedad(Long idPersonaNovedad) {
        this.idPersonaNovedad = idPersonaNovedad;
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

}
