package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.ccf.fovis.SolicitudGestionCruce;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class SolicitudGestionCruceDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 7290008765824352428L;

    /**
     * Identificador gestion cruce
     */
    private Long idSolicitudGestionCruce;

    /**
     * Identificador solicitud postulacion
     */
    private Long idSolicitudPostulacion;

    /**
     * Estado cruce hogar
     */
    private EstadoCruceHogarEnum estadoCruceHogar;

    /**
     * Lista de cruces asociados a una solicitud gestion
     */
    private List<CruceDetalleDTO> listCrucesAsociados;

    /**
     * Tipo cruce
     */
    private TipoCruceEnum tipoCruce;

    /**
     * Estado Solicitud gestion cruce
     */
    private EstadoSolicitudGestionCruceEnum estadoSolicitudGestionCruce;

    /**
     * Objeto que trae todos los datos de la postulación
     */
    private SolicitudPostulacionFOVISDTO datosPostulacionFovis;

    /**
     * Contiene el numero de radicado de la postulación
     */
    private String numeroRadicadoPostulacion;

    private Date fechaValidacionCruce;

    /**
     * Constructor de la clase con la entidad
     * 
     * @param solicitudGestionCruce
     *        Entidad
     */
    public SolicitudGestionCruceDTO(SolicitudGestionCruce solicitudGestionCruce) {
        convertEntityToDTO(solicitudGestionCruce);
    }

    /**
     * Constructor por defecto
     */
    public SolicitudGestionCruceDTO() {
        super();
    }

    /**
     * Convierte la entidad en DTO
     * 
     * @param cargueArchivoCruceFovis
     *        Entidad
     * @return DTO
     */
    public void convertEntityToDTO(SolicitudGestionCruce solicitudGestionCruce) {
        if (solicitudGestionCruce.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudGestionCruce.getSolicitudGlobal());
        }
        this.setEstadoCruceHogar(solicitudGestionCruce.getEstadoCruceHogar());
        this.setEstadoSolicitudGestionCruce(solicitudGestionCruce.getEstadoSolicitudGestionCruce());
        this.setIdSolicitudGestionCruce(solicitudGestionCruce.getIdSolicitudGestionCruce());
        this.setIdSolicitudPostulacion(solicitudGestionCruce.getIdSolicitudPostulacion());
        this.setTipoCruce(solicitudGestionCruce.getTipoCruce());
        this.setFechaValidacionCruce(solicitudGestionCruce.getFechaValidacionCruce());
    }

    /**
     * Convierte el DTO a entity
     * 
     * @return Entidad
     */
    public SolicitudGestionCruce convertToEntity() {
        SolicitudGestionCruce solicitudGestionCruce = new SolicitudGestionCruce();
        solicitudGestionCruce.setEstadoCruceHogar(this.getEstadoCruceHogar());
        solicitudGestionCruce.setEstadoSolicitudGestionCruce(this.getEstadoSolicitudGestionCruce());
        solicitudGestionCruce.setIdSolicitudGestionCruce(this.getIdSolicitudGestionCruce());
        solicitudGestionCruce.setIdSolicitudPostulacion(this.getIdSolicitudPostulacion());
        solicitudGestionCruce.setTipoCruce(this.getTipoCruce());
        solicitudGestionCruce.setFechaValidacionCruce(this.getFechaValidacionCruce());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudGestionCruce.setSolicitudGlobal(solicitudGlobal);
        return solicitudGestionCruce;
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
     * @return the idSolicitudPostulacion
     */
    public Long getIdSolicitudPostulacion() {
        return idSolicitudPostulacion;
    }

    /**
     * @param idSolicitudPostulacion
     *        the idSolicitudPostulacion to set
     */
    public void setIdSolicitudPostulacion(Long idSolicitudPostulacion) {
        this.idSolicitudPostulacion = idSolicitudPostulacion;
    }

    /**
     * @return the estadoCruceHogar
     */
    public EstadoCruceHogarEnum getEstadoCruceHogar() {
        return estadoCruceHogar;
    }

    /**
     * @param estadoCruceHogar
     *        the estadoCruceHogar to set
     */
    public void setEstadoCruceHogar(EstadoCruceHogarEnum estadoCruceHogar) {
        this.estadoCruceHogar = estadoCruceHogar;
    }

    /**
     * @return the listCrucesAsociados
     */
    public List<CruceDetalleDTO> getListCrucesAsociados() {
        return listCrucesAsociados;
    }

    /**
     * @param listCrucesAsociados
     *        the listCrucesAsociados to set
     */
    public void setListCrucesAsociados(List<CruceDetalleDTO> listCrucesAsociados) {
        this.listCrucesAsociados = listCrucesAsociados;
    }

    /**
     * @return the tipoCruce
     */
    public TipoCruceEnum getTipoCruce() {
        return tipoCruce;
    }

    /**
     * @param tipoCruce
     *        the tipoCruce to set
     */
    public void setTipoCruce(TipoCruceEnum tipoCruce) {
        this.tipoCruce = tipoCruce;
    }

    /**
     * @return the estadoSolicitudGestionCruce
     */
    public EstadoSolicitudGestionCruceEnum getEstadoSolicitudGestionCruce() {
        return estadoSolicitudGestionCruce;
    }

    /**
     * @param estadoSolicitudGestionCruce
     *        the estadoSolicitudGestionCruce to set
     */
    public void setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum estadoSolicitudGestionCruce) {
        this.estadoSolicitudGestionCruce = estadoSolicitudGestionCruce;
    }

    /**
     * @return the datosPostulacionFovis
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacionFovis() {
        return datosPostulacionFovis;
    }

    /**
     * @param datosPostulacionFovis
     *        the datosPostulacionFovis to set
     */
    public void setDatosPostulacionFovis(SolicitudPostulacionFOVISDTO datosPostulacionFovis) {
        this.datosPostulacionFovis = datosPostulacionFovis;
    }

    /**
     * @return the numeroRadicadoPostulacion
     */
    public String getNumeroRadicadoPostulacion() {
        return numeroRadicadoPostulacion;
    }

    /**
     * @param numeroRadicadoPostulacion the numeroRadicadoPostulacion to set
     */
    public void setNumeroRadicadoPostulacion(String numeroRadicadoPostulacion) {
        this.numeroRadicadoPostulacion = numeroRadicadoPostulacion;
    }

    public Date getFechaValidacionCruce() {
        return fechaValidacionCruce;
    }

    public void setFechaValidacionCruce(Date fechaValidacionCruce) {
        this.fechaValidacionCruce = fechaValidacionCruce;
    }
}
