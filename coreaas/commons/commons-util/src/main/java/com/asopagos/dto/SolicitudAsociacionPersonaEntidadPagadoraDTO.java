package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;

/**
 * <b>Descripción:</b> DTO para que contiene los datos de la solicitud de asociacion de personas a entidades pagadoras <b>Historia de
 * Usuario:</b> HU-123-380
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class SolicitudAsociacionPersonaEntidadPagadoraDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    private SolicitudDTO solicitudDTO;

    private Long idSolicitudAsociacionPersonaEntidadPagadora;

    private EstadoSolicitudPersonaEntidadPagadoraEnum estado;

    private TipoGestionSolicitudAsociacionEnum tipoGestion;

    private Date fechaGestion;

    private String consecutivo;

    private String identificadorArchivoPlano;

    private String identificadorArchivoCarta;

    private String identificadorCartaResultadoGestion;

    private String usuarioGestion;

    public SolicitudAsociacionPersonaEntidadPagadoraDTO() {
    }

    public SolicitudAsociacionPersonaEntidadPagadoraDTO(SolicitudAsociacionPersonaEntidadPagadora solicitud) {
        solicitudDTO = new SolicitudDTO(solicitud.getSolicitudGlobal());
        idSolicitudAsociacionPersonaEntidadPagadora = solicitud.getIdSolicitudAsociacionPersonaEntidadPagadora();
        estado = solicitud.getEstado();
        tipoGestion = solicitud.getTipoGestion();
        fechaGestion = solicitud.getFechaGestion();
        consecutivo = solicitud.getConsecutivo();
        identificadorArchivoPlano = solicitud.getIdentificadorArchivoPlano();
        identificadorArchivoCarta = solicitud.getIdentificadorArchivoCarta();
        identificadorCartaResultadoGestion = solicitud.getIdentificadorCartaResultadoGestion();
        usuarioGestion = solicitud.getUsuarioGestion();
    }

    /**
     * @return the idSolicitudAsociacionPersonaEntidadPagadora
     */
    public Long getIdSolicitudAsociacionPersonaEntidadPagadora() {
        return idSolicitudAsociacionPersonaEntidadPagadora;
    }

    /**
     * @param idSolicitudAsociacionPersonaEntidadPagadora
     *        the idSolicitudAsociacionPersonaEntidadPagadora to set
     */
    public void setIdSolicitudAsociacionPersonaEntidadPagadora(Long idSolicitudAsociacionPersonaEntidadPagadora) {
        this.idSolicitudAsociacionPersonaEntidadPagadora = idSolicitudAsociacionPersonaEntidadPagadora;
    }

    /**
     * @return the estado
     */
    public EstadoSolicitudPersonaEntidadPagadoraEnum getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoSolicitudPersonaEntidadPagadoraEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoGestion
     */
    public TipoGestionSolicitudAsociacionEnum getTipoGestion() {
        return tipoGestion;
    }

    /**
     * @param tipoGestion
     *        the tipoGestion to set
     */
    public void setTipoGestion(TipoGestionSolicitudAsociacionEnum tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    /**
     * @return the fechaGestion
     */
    public Date getFechaGestion() {
        return fechaGestion;
    }

    /**
     * @param fechaGestion
     *        the fechaGestion to set
     */
    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    /**
     * @return the consecutivo
     */
    public String getConsecutivo() {
        return consecutivo;
    }

    /**
     * @param consecutivo
     *        the consecutivo to set
     */
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    /**
     * @return the identificadorArchivoPlano
     */
    public String getIdentificadorArchivoPlano() {
        return identificadorArchivoPlano;
    }

    /**
     * @param identificadorArchivoPlano
     *        the identificadorArchivoPlano to set
     */
    public void setIdentificadorArchivoPlano(String identificadorArchivoPlano) {
        this.identificadorArchivoPlano = identificadorArchivoPlano;
    }

    /**
     * @return the identificadorArchivoCarta
     */
    public String getIdentificadorArchivoCarta() {
        return identificadorArchivoCarta;
    }

    /**
     * @param identificadorArchivoCarta
     *        the identificadorArchivoCarta to set
     */
    public void setIdentificadorArchivoCarta(String identificadorArchivoCarta) {
        this.identificadorArchivoCarta = identificadorArchivoCarta;
    }

    /**
     * @return the identificadorCartaResultadoGestion
     */
    public String getIdentificadorCartaResultadoGestion() {
        return identificadorCartaResultadoGestion;
    }

    /**
     * @param identificadorCartaResultadoGestion
     *        the identificadorCartaResultadoGestion to set
     */
    public void setIdentificadorCartaResultadoGestion(String identificadorCartaResultadoGestion) {
        this.identificadorCartaResultadoGestion = identificadorCartaResultadoGestion;
    }

    /**
     * @return the usuarioGestion
     */
    public String getUsuarioGestion() {
        return usuarioGestion;
    }

    /**
     * @param usuarioGestion
     *        the usuarioGestion to set
     */
    public void setUsuarioGestion(String usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }

    /**
     * @return the solicitudDTO
     */
    public SolicitudDTO getSolicitudDTO() {
        return solicitudDTO;
    }

    /**
     * @param solicitudDTO the solicitudDTO to set
     */
    public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
        this.solicitudDTO = solicitudDTO;
    }
    
}
