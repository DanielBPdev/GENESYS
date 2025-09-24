package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudAnalisisNovedadFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAsignacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;

/**
 * DTO que contiene los datos relacionados a la entidad SolicitudAnalisisNovedadFOVIS
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class SolicitudAnalisisNovedadFOVISModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7799817386771484799L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudAnalisisNovedadFovis;

    /**
     * Identificador solicitud novedad
     */
    private Long idSolicitudNovedad;

    /**
     * Informacion solicitud novedad
     */
    private SolicitudNovedadModeloDTO solicitudNovedadModeloDTO;

    /**
     * Estado de la solicitud de analisis novedad.
     */
    private EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud;

    /**
     * Identificador postulación FOVIS
     */
    private Long idPostulacionFOVIS;

    /**
     * Informacion postulación FOVIS
     */
    private PostulacionFOVISModeloDTO postulacionFOVISModeloDTO;

    /**
     * Observaciones de la solicitud de analisis para cierre
     */
    private String observaciones;

    /**
     * Identificador persona asociada a la novedad
     */
    private Long idPersonaNovedad;

    /**
     * Informacion de la persona que fue objeto de la novedad
     */
    private PersonaDTO personaNovedad;

    /**
     * Contiene el numero de radicado de la solicitud de postulacion
     */
    private String numeroRadicadoPostulacion;

    /**
     * Contiene la concatenacion de los estados de las solicitudes de postulacion, asignacion y legalizacion segun cuales existan
     */
    private String descripcionEstado;

    /**
     * Identifica el tipo solicitante de la novedad
     */
    private String tipoSolicitante;

    /**
     * Constructor por defecto
     */
    public SolicitudAnalisisNovedadFOVISModeloDTO() {
    }

    /**
     * Constructor con los datos de la solicitud desde la entidad
     * @param solicitudAnalisisNovedadFovis
     *        Entidad solicitud analisis novedad
     */
    public SolicitudAnalisisNovedadFOVISModeloDTO(SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis) {
        this.convertToDTO(solicitudAnalisisNovedadFovis);
    }

    /**
     * Constructor informacion solicitud novedad
     * @param solicitudAnalisisNovedadFovis
     *        Informacion basica solicitud novedad
     * @param solicitudNovedad
     *        Informacion solicitud novedad persona
     * @param postulacionFOVIS
     *        Informacion Postulacion FOVIS
     * @param persona
     *        Informacion Persona
     * @param numeroRadicadoPostulacion
     *        Numero radicado postulacion
     * @param estadoPostulacion
     *        Estado Postulacion
     * @param estadoAsignacion
     *        Estado solicitud asignacion
     * @param estadoLegalizacion
     *        Estado solicitud legalizacion desembolso
     */
    public SolicitudAnalisisNovedadFOVISModeloDTO(SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis,
            SolicitudNovedad solicitudNovedad, PostulacionFOVIS postulacionFOVIS, Persona persona, String numeroRadicadoPostulacion,
            EstadoSolicitudPostulacionEnum estadoPostulacion, EstadoSolicitudAsignacionEnum estadoAsignacion,
            EstadoSolicitudLegalizacionDesembolsoEnum estadoLegalizacion) {
        this.convertToDTO(solicitudAnalisisNovedadFovis);
        // Informacion solicitud novedad
        SolicitudNovedadModeloDTO solicitudNovedadModeloDTO = new SolicitudNovedadModeloDTO();
        solicitudNovedadModeloDTO.convertToDTO(solicitudNovedad);
        this.setSolicitudNovedadModeloDTO(solicitudNovedadModeloDTO);
        // Informacion Postulacion FOVIS
        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = new PostulacionFOVISModeloDTO(postulacionFOVIS);
        this.setPostulacionFOVISModeloDTO(postulacionFOVISModeloDTO);
        // Informacion Persona
        PersonaDTO personaModeloDTO = new PersonaDTO(persona);
        this.setPersonaNovedad(personaModeloDTO);
        // Descripcion estado creado a partir de los estados la solicitud de Postulacion, Asignacion y Legalizacion 
        StringBuilder descripcionEstado = new StringBuilder();
        descripcionEstado.append(estadoPostulacion.getDescripcion());
        if (estadoAsignacion != null) {
            descripcionEstado.append(" - ");
            descripcionEstado.append(estadoAsignacion.getDescripcion());
        }
        if (estadoLegalizacion != null) {
            descripcionEstado.append(" - ");
            descripcionEstado.append(estadoLegalizacion.getDescripcion());
        }
        this.setDescripcionEstado(descripcionEstado.toString());
        this.setNumeroRadicadoPostulacion(numeroRadicadoPostulacion);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return SolicitudPostulacion
     */
    public SolicitudAnalisisNovedadFovis convertToEntity() {
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = new SolicitudAnalisisNovedadFovis();
        solicitudAnalisisNovedadFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudAnalisisNovedadFovis.setIdSolicitudAnalisisNovedadFovis(this.getIdSolicitudAnalisisNovedadFovis());
        solicitudAnalisisNovedadFovis.setIdPostulacionFovis(this.getIdPostulacionFOVIS());
        solicitudAnalisisNovedadFovis.setObservaciones(this.getObservaciones());
        solicitudAnalisisNovedadFovis.setSolicitudNovedad(this.getIdSolicitudNovedad());
        solicitudAnalisisNovedadFovis.setIdPersonaNovedad(this.getIdPersonaNovedad());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudAnalisisNovedadFovis.setSolicitudGlobal(solicitudGlobal);
        return solicitudAnalisisNovedadFovis;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * @param solicitudPostulacion
     */
    public void convertToDTO(SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis) {
        if (solicitudAnalisisNovedadFovis.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudAnalisisNovedadFovis.getSolicitudGlobal());
        }
        this.setIdSolicitudAnalisisNovedadFovis(solicitudAnalisisNovedadFovis.getIdSolicitudAnalisisNovedadFovis());
        this.setEstadoSolicitud(solicitudAnalisisNovedadFovis.getEstadoSolicitud());
        this.setIdPostulacionFOVIS(solicitudAnalisisNovedadFovis.getIdPostulacionFovis());
        this.setObservaciones(solicitudAnalisisNovedadFovis.getObservaciones());
        this.setIdSolicitudNovedad(solicitudAnalisisNovedadFovis.getSolicitudNovedad());
        this.setIdPersonaNovedad(solicitudAnalisisNovedadFovis.getIdPersonaNovedad());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param SolicitudAnalisisNovedadFovis
     * @return
     */
    public SolicitudAnalisisNovedadFovis copyDTOToEntity(SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis) {
        if (this.getEstadoSolicitud() != null) {
            solicitudAnalisisNovedadFovis.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getIdPostulacionFOVIS() != null) {
            solicitudAnalisisNovedadFovis.setIdPostulacionFovis(this.getIdPostulacionFOVIS());
        }
        if (this.getIdSolicitudAnalisisNovedadFovis() != null) {
            solicitudAnalisisNovedadFovis.setIdSolicitudAnalisisNovedadFovis(this.getIdSolicitudAnalisisNovedadFovis());
        }
        if (this.getObservaciones() != null) {
            solicitudAnalisisNovedadFovis.setObservaciones(this.getObservaciones());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudAnalisisNovedadFovis.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudAnalisisNovedadFovis.setSolicitudGlobal(solicitudGlobal);
        }
        if (this.getSolicitudNovedadModeloDTO() != null) {
            solicitudAnalisisNovedadFovis.setSolicitudNovedad(this.getIdSolicitudNovedad());
        }
        if (this.getIdPersonaNovedad() != null) {
            solicitudAnalisisNovedadFovis.setIdPersonaNovedad(this.getIdPersonaNovedad());
        }
        return solicitudAnalisisNovedadFovis;
    }

    /**
     * @return the idSolicitudAnalisisNovedadFovis
     */
    public Long getIdSolicitudAnalisisNovedadFovis() {
        return idSolicitudAnalisisNovedadFovis;
    }

    /**
     * @param idSolicitudAnalisisNovedadFovis
     *        the idSolicitudAnalisisNovedadFovis to set
     */
    public void setIdSolicitudAnalisisNovedadFovis(Long idSolicitudAnalisisNovedadFovis) {
        this.idSolicitudAnalisisNovedadFovis = idSolicitudAnalisisNovedadFovis;
    }

    /**
     * @return the idSolicitudNovedad
     */
    public Long getIdSolicitudNovedad() {
        return idSolicitudNovedad;
    }

    /**
     * @param idSolicitudNovedad
     *        the idSolicitudNovedad to set
     */
    public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
        this.idSolicitudNovedad = idSolicitudNovedad;
    }

    /**
     * @return the solicitudNovedadModeloDTO
     */
    public SolicitudNovedadModeloDTO getSolicitudNovedadModeloDTO() {
        return solicitudNovedadModeloDTO;
    }

    /**
     * @param solicitudNovedadModeloDTO
     *        the solicitudNovedadModeloDTO to set
     */
    public void setSolicitudNovedadModeloDTO(SolicitudNovedadModeloDTO solicitudNovedadModeloDTO) {
        this.solicitudNovedadModeloDTO = solicitudNovedadModeloDTO;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudAnalisisNovedadFovisEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the idPostulacionFOVIS
     */
    public Long getIdPostulacionFOVIS() {
        return idPostulacionFOVIS;
    }

    /**
     * @param idPostulacionFOVIS
     *        the idPostulacionFOVIS to set
     */
    public void setIdPostulacionFOVIS(Long idPostulacionFOVIS) {
        this.idPostulacionFOVIS = idPostulacionFOVIS;
    }

    /**
     * @return the postulacionFOVISModeloDTO
     */
    public PostulacionFOVISModeloDTO getPostulacionFOVISModeloDTO() {
        return postulacionFOVISModeloDTO;
    }

    /**
     * @param postulacionFOVISModeloDTO
     *        the postulacionFOVISModeloDTO to set
     */
    public void setPostulacionFOVISModeloDTO(PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        this.postulacionFOVISModeloDTO = postulacionFOVISModeloDTO;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
     * @return the personaNovedad
     */
    public PersonaDTO getPersonaNovedad() {
        return personaNovedad;
    }

    /**
     * @param personaNovedad
     *        the personaNovedad to set
     */
    public void setPersonaNovedad(PersonaDTO personaNovedad) {
        this.personaNovedad = personaNovedad;
    }

    /**
     * @return the numeroRadicadoPostulacion
     */
    public String getNumeroRadicadoPostulacion() {
        return numeroRadicadoPostulacion;
    }

    /**
     * @param numeroRadicadoPostulacion
     *        the numeroRadicadoPostulacion to set
     */
    public void setNumeroRadicadoPostulacion(String numeroRadicadoPostulacion) {
        this.numeroRadicadoPostulacion = numeroRadicadoPostulacion;
    }

    /**
     * @return the descripcionEstado
     */
    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    /**
     * @param descripcionEstado
     *        the descripcionEstado to set
     */
    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    /**
     * @return the tipoSolicitante
     */
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

}
