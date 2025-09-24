package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.Cruce;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.fovis.EstadoCruceEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CruceDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3393933816714948968L;

    /**
     * Identificador cruce
     */
    private Long idCruce;

    /**
     * Identificador archivo cruce
     */
    private Long idCargueArchivoCruceFovis;

    /**
     * Numero de la postulacion asociada al cruce
     */
    private String numeroPostulacion;

    /**
     * Persona asociada en el cruce
     */
    private PersonaDTO persona;

    /**
     * Estado cruce
     */
    private EstadoCruceEnum estadoCruce;

    /**
     * Solicitud gestion cruce asociada
     */
    private Long idSolicitudGestionCruce;

    /**
     * Codigo identificador archivo soporte resultado cruce
     */
    private String codigoIdentificacionECMResultado;

    /**
     * Observacion resultado cruce
     */
    private String observacionResultado;

    /**
     * Identificador ejecucion proceso asincrono origino cruce
     */
    private Long idEjecucionProcesoAsincrono;

    /**
     * Indica la fecha de registro del cruce
     */
    private Date fechaRegistro;

    /**
     * Constructor por defecto
     */
    public CruceDTO() {
        super();
    }

    /**
     * Constructor a partir de la entidad
     * @param cruce Información cruce
     */
    public CruceDTO(Cruce cruce){
        convertToDTO(cruce, null);
    }

    /**
     * Constructor del cruce con la informacion de la persona asociada
     * @param cruce
     *        Información cruce
     * @param persona
     *        Información persona asociada
     */
    public CruceDTO(Cruce cruce, Persona persona) {
        this.convertToDTO(cruce, persona);
    }
    
    /**
     * Convierte la informacion de las entidades al DTO
     * @param cruce Información cruce
     * @param persona Información persona asociada
     */
    public void convertToDTO(Cruce cruce, Persona persona){
        this.setIdCargueArchivoCruceFovis(cruce.getIdCargueArchivoCruceFovis());
        this.setIdCruce(cruce.getIdCruce());
        this.setNumeroPostulacion(cruce.getNumeroPostulacion());
        this.setEstadoCruce(cruce.getEstadoCruce());
        this.setIdSolicitudGestionCruce(cruce.getIdSolicitudGestionCruce());
        this.setCodigoIdentificacionECMResultado(cruce.getCodigoIdentificacionECMResultado());
        this.setObservacionResultado(cruce.getObservacionResultado());
        this.setIdEjecucionProcesoAsincrono(cruce.getIdEjecucionProcesoAsincrono());
        this.setFechaRegistro(cruce.getFechaRegistro());
        PersonaDTO personaDTO = null;
        if (persona != null) {
            personaDTO = new PersonaDTO(persona);
        } else {
            personaDTO = new PersonaDTO();
            personaDTO.setIdPersona(cruce.getIdPersona());
        }
        this.setPersona(personaDTO);
    }

    /**
     * Convierte la entidad en DTO
     * @param cargueArchivoCruceFovis
     *        Entidad
     * @return DTO
     */
    public static CruceDTO convertEntityToDTO(Cruce cruce) {
        CruceDTO cruceDTO = new CruceDTO(cruce);
        return cruceDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public Cruce convertToEntity() {
        Cruce cruce = new Cruce();
        cruce.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cruce.setIdCruce(this.getIdCruce());
        if (this.getPersona() != null && this.getPersona().getIdPersona() != null) {
            cruce.setIdPersona(this.getPersona().getIdPersona());
        }
        cruce.setNumeroPostulacion(this.getNumeroPostulacion());
        cruce.setEstadoCruce(this.getEstadoCruce());
        cruce.setIdSolicitudGestionCruce(this.getIdSolicitudGestionCruce());
        cruce.setCodigoIdentificacionECMResultado(this.getCodigoIdentificacionECMResultado());
        cruce.setObservacionResultado(this.getObservacionResultado());
        cruce.setIdEjecucionProcesoAsincrono(this.getIdEjecucionProcesoAsincrono());
        cruce.setFechaRegistro(this.getFechaRegistro());
        return cruce;
    }

    /**
     * @return the idCruce
     */
    public Long getIdCruce() {
        return idCruce;
    }

    /**
     * @param idCruce
     *        the idCruce to set
     */
    public void setIdCruce(Long idCruce) {
        this.idCruce = idCruce;
    }

    /**
     * @return the idCargueArchivoCruceFovis
     */
    public Long getIdCargueArchivoCruceFovis() {
        return idCargueArchivoCruceFovis;
    }

    /**
     * @param idCargueArchivoCruceFovis
     *        the idCargueArchivoCruceFovis to set
     */
    public void setIdCargueArchivoCruceFovis(Long idCargueArchivoCruceFovis) {
        this.idCargueArchivoCruceFovis = idCargueArchivoCruceFovis;
    }

    /**
     * @return the numeroPostulacion
     */
    public String getNumeroPostulacion() {
        return numeroPostulacion;
    }

    /**
     * @param numeroPostulacion
     *        the numeroPostulacion to set
     */
    public void setNumeroPostulacion(String numeroPostulacion) {
        this.numeroPostulacion = numeroPostulacion;
    }

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona
     *        the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    /**
     * @return the estadoCruce
     */
    public EstadoCruceEnum getEstadoCruce() {
        return estadoCruce;
    }

    /**
     * @param estadoCruce
     *        the estadoCruce to set
     */
    public void setEstadoCruce(EstadoCruceEnum estadoCruce) {
        this.estadoCruce = estadoCruce;
    }

    /**
     * @return the idSolicitudGestionCruce
     */
    public Long getIdSolicitudGestionCruce() {
        return idSolicitudGestionCruce;
    }

    /**
     * @param idSolicitudGestionCruce
     *        the idSolicitudGestionCruce to set
     */
    public void setIdSolicitudGestionCruce(Long idSolicitudGestionCruce) {
        this.idSolicitudGestionCruce = idSolicitudGestionCruce;
    }

    /**
     * @return the codigoIdentificacionECMResultado
     */
    public String getCodigoIdentificacionECMResultado() {
        return codigoIdentificacionECMResultado;
    }

    /**
     * @param codigoIdentificacionECMResultado
     *        the codigoIdentificacionECMResultado to set
     */
    public void setCodigoIdentificacionECMResultado(String codigoIdentificacionECMResultado) {
        this.codigoIdentificacionECMResultado = codigoIdentificacionECMResultado;
    }

    /**
     * @return the observacionResultado
     */
    public String getObservacionResultado() {
        return observacionResultado;
    }

    /**
     * @param observacionResultado
     *        the observacionResultado to set
     */
    public void setObservacionResultado(String observacionResultado) {
        this.observacionResultado = observacionResultado;
    }

    /**
     * @return the idEjecucionProcesoAsincrono
     */
    public Long getIdEjecucionProcesoAsincrono() {
        return idEjecucionProcesoAsincrono;
    }

    /**
     * @param idEjecucionProcesoAsincrono
     *        the idEjecucionProcesoAsincrono to set
     */
    public void setIdEjecucionProcesoAsincrono(Long idEjecucionProcesoAsincrono) {
        this.idEjecucionProcesoAsincrono = idEjecucionProcesoAsincrono;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro
     *        the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
