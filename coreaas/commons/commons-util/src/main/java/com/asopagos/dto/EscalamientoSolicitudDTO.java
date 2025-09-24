package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.afiliaciones.EscalamientoSolicitud;
import com.asopagos.enumeraciones.afiliaciones.OrigenEscalamientoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoAnalisisAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAnalistaEstalamientoFOVISEnum;

/**
 *
 * @author sbrinez
 */
public class EscalamientoSolicitudDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria del escalamiento de las solicitudes de afiliación empleador
     */
    private Long idEscalamientoSolicitud;

    /**
     * Id que identifica a la solicitud asociada al escalamiento
     */
    private Long idSolicitud;

    /**
     * Descripción del asunto
     */
    private String asunto;

    /**
     * Descripción del escalamiento de la solicitud de afiliación del empleador
     */
    private String descripcion;

    /**
     * Usuario destinatario
     */
    private String destinatario;

    /**
     * Descripción del resultado analista
     */
    private ResultadoAnalisisAfiliacionEnum resultadoAnalista;

    /**
     * Descripción de los comentarios del analista
     */
    private String comentarioAnalista;

    /**
     * Identificador del documento del soporte analista
     */
    private String identificadorDocumentoSoporteAnalista;

    /**
     * Usuario de creación
     */
    private String usuarioCreacion;

    /**
     * Fecha de creación
     */
    private Date fechaCreacion;

    /**
     * Representa el tipo de analista al que fue escalada la solicitud.
     */
    private TipoAnalistaEstalamientoFOVISEnum tipoAnalistaFOVIS;

    /**
     * Indica el origen del escalamiento (cuando aplique)
     */
    private OrigenEscalamientoEnum origen;

    /**
     * @return the idEscalamientoSolicitud
     */
    public Long getIdEscalamientoSolicitud() {
        return idEscalamientoSolicitud;
    }

    /**
     * @param idEscalamientoSolicitud
     *        the idEscalamientoSolicitud to set
     */
    public void setIdEscalamientoSolicitud(Long idEscalamientoSolicitud) {
        this.idEscalamientoSolicitud = idEscalamientoSolicitud;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto
     *        the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *        the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param destinatario
     *        the destinatario to set
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @return the resultadoAnalista
     */
    public ResultadoAnalisisAfiliacionEnum getResultadoAnalista() {
        return resultadoAnalista;
    }

    /**
     * @param resultadoAnalista
     *        the resultadoAnalista to set
     */
    public void setResultadoAnalista(ResultadoAnalisisAfiliacionEnum resultadoAnalista) {
        this.resultadoAnalista = resultadoAnalista;
    }

    /**
     * @return the comentarioAnalista
     */
    public String getComentarioAnalista() {
        return comentarioAnalista;
    }

    /**
     * @param comentarioAnalista
     *        the comentarioAnalista to set
     */
    public void setComentarioAnalista(String comentarioAnalista) {
        this.comentarioAnalista = comentarioAnalista;
    }

    /**
     * @return the identificadorDocumentoSoporteAnalista
     */
    public String getIdentificadorDocumentoSoporteAnalista() {
        return identificadorDocumentoSoporteAnalista;
    }

    /**
     * @param identificadorDocumentoSoporteAnalista
     *        the identificadorDocumentoSoporteAnalista to set
     */
    public void setIdentificadorDocumentoSoporteAnalista(String identificadorDocumentoSoporteAnalista) {
        this.identificadorDocumentoSoporteAnalista = identificadorDocumentoSoporteAnalista;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion
     *        the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion
     *        the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the tipoAnalistaFOVIS
     */
    public TipoAnalistaEstalamientoFOVISEnum getTipoAnalistaFOVIS() {
        return tipoAnalistaFOVIS;
    }

    /**
     * @param tipoAnalistaFOVIS
     *        the tipoAnalistaFOVIS to set
     */
    public void setTipoAnalistaFOVIS(TipoAnalistaEstalamientoFOVISEnum tipoAnalistaFOVIS) {
        this.tipoAnalistaFOVIS = tipoAnalistaFOVIS;
    }

    /**
     * @return the origen
     */
    public OrigenEscalamientoEnum getOrigen() {
        return origen;
    }

    /**
     * @param origen
     *        the origen to set
     */
    public void setOrigen(OrigenEscalamientoEnum origen) {
        this.origen = origen;
    }

    /**
     * Método encargado de convertir la entidad a su correspondiente DTO
     * 
     * @param escalamientoSolicitud
     * @return
     */
    public static EscalamientoSolicitudDTO convertEscalamientoSolicitudToDTO(EscalamientoSolicitud escalamientoSolicitud) {
        EscalamientoSolicitudDTO escalamientoSolicitudDTO = new EscalamientoSolicitudDTO();
        escalamientoSolicitudDTO.setAsunto(escalamientoSolicitud.getAsunto());
        escalamientoSolicitudDTO.setComentarioAnalista(escalamientoSolicitud.getComentarioAnalista());
        escalamientoSolicitudDTO.setDescripcion(escalamientoSolicitud.getDescripcion());
        escalamientoSolicitudDTO.setDestinatario(escalamientoSolicitud.getDestinatario());
        escalamientoSolicitudDTO.setFechaCreacion(escalamientoSolicitud.getFechaCreacion());
        escalamientoSolicitudDTO.setIdentificadorDocumentoSoporteAnalista(escalamientoSolicitud.getIdentificadorDocumentoSoporteAnalista());
        escalamientoSolicitudDTO.setIdEscalamientoSolicitud(escalamientoSolicitud.getIdEscalamientoSolicitud());
        escalamientoSolicitudDTO.setIdSolicitud(escalamientoSolicitud.getIdSolicitud());
        escalamientoSolicitudDTO.setResultadoAnalista(escalamientoSolicitud.getResultadoAnalista());
        escalamientoSolicitudDTO.setUsuarioCreacion(escalamientoSolicitud.getUsuarioCreacion());
        escalamientoSolicitudDTO.setTipoAnalistaFOVIS(escalamientoSolicitud.getTipoAnalistaFOVIS());
        escalamientoSolicitudDTO.setOrigen(escalamientoSolicitud.getOrigen());
        return escalamientoSolicitudDTO;
    }

    /**
     * Método encargado de convertir el DTO a su correspondiente entidad
     * 
     * @param escalamientoSolicitud
     * @return
     */
    public static EscalamientoSolicitud convertEscalamientoSolicitudDTOToEntity(EscalamientoSolicitudDTO escalamientoSolicitudDTO) {
        EscalamientoSolicitud escalamientoSolicitud = new EscalamientoSolicitud();
        escalamientoSolicitud.setAsunto(escalamientoSolicitudDTO.getAsunto());
        escalamientoSolicitud.setComentarioAnalista(escalamientoSolicitudDTO.getComentarioAnalista());
        escalamientoSolicitud.setDescripcion(escalamientoSolicitudDTO.getDescripcion());
        escalamientoSolicitud.setDestinatario(escalamientoSolicitudDTO.getDestinatario());
        escalamientoSolicitud.setFechaCreacion(escalamientoSolicitudDTO.getFechaCreacion());
        escalamientoSolicitud.setIdentificadorDocumentoSoporteAnalista(escalamientoSolicitudDTO.getIdentificadorDocumentoSoporteAnalista());
        escalamientoSolicitud.setIdEscalamientoSolicitud(escalamientoSolicitudDTO.getIdEscalamientoSolicitud());
        escalamientoSolicitud.setIdSolicitud(escalamientoSolicitudDTO.getIdSolicitud());
        escalamientoSolicitud.setResultadoAnalista(escalamientoSolicitudDTO.getResultadoAnalista());
        escalamientoSolicitud.setUsuarioCreacion(escalamientoSolicitudDTO.getUsuarioCreacion());
        escalamientoSolicitud.setTipoAnalistaFOVIS(escalamientoSolicitudDTO.getTipoAnalistaFOVIS());
        escalamientoSolicitud.setOrigen(escalamientoSolicitudDTO.getOrigen());
        return escalamientoSolicitud;
    }

}
