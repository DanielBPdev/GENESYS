package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.ActoAceptacionProrrogaFovis;

/**
 * Entidad que relaciona el acta de aceptación de la novedad de prorroga para Fovis <br/>
 * <b>Historia de Usuario: </b> 325-99, 325-40
 * @author Alexander Quintero Duarte <alquintero@heinsohn.com.co>
 *
 */
@XmlRootElement
public class ActoAceptacionProrrogaFovisModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 871140443868977514L;

    /**
     * Código identificador de llave primaria de una SolicitudNovedadPersona
     */
    private Long idActoAceptacionProrrogaFovis;

    /**
     * Referencia a la SolicitudNovedadFovis
     */
    private Long idSolicitudNovedadFovis;

    /**
     * Número del documento del acto administrativo de aprobación de la prórroga
     */
    private String numeroActoAdministrativo;

    /**
     * Fecha de aprobacion del consejo parqa la prórroga
     */
    private Long fechaAprobacionConsejo;

    public ActoAceptacionProrrogaFovisModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity
     * @param actoAceptacionProrrogaFovis
     */
    public ActoAceptacionProrrogaFovisModeloDTO(ActoAceptacionProrrogaFovis actoAceptacionProrrogaFovis) {
        convertToDTO(actoAceptacionProrrogaFovis);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return ActoAceptacionProrrogaFovis
     */
    public ActoAceptacionProrrogaFovis convertToEntity() {
        ActoAceptacionProrrogaFovis actoAceptacionProrrogaFovis = new ActoAceptacionProrrogaFovis();
        actoAceptacionProrrogaFovis.setIdActoAceptacionProrrogaFovis(this.getIdActoAceptacionProrrogaFovis());
        actoAceptacionProrrogaFovis.setIdSolicitudNovedadFovis(this.getIdSolicitudNovedadFovis());
        actoAceptacionProrrogaFovis.setNumeroActoAdministrativo(this.getNumeroActoAdministrativo());
        actoAceptacionProrrogaFovis
                .setFechaAprobacionConsejo(this.getFechaAprobacionConsejo() != null ? new Date(this.getFechaAprobacionConsejo()) : null);
        return actoAceptacionProrrogaFovis;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param ActoAceptacionProrrogaFovis
     */
    public void convertToDTO(ActoAceptacionProrrogaFovis actoAceptacionProrrogaFovis) {
        this.setIdActoAceptacionProrrogaFovis(actoAceptacionProrrogaFovis.getIdActoAceptacionProrrogaFovis());
        this.setIdSolicitudNovedadFovis(actoAceptacionProrrogaFovis.getIdSolicitudNovedadFovis());
        this.setNumeroActoAdministrativo(actoAceptacionProrrogaFovis.getNumeroActoAdministrativo());
        this.setFechaAprobacionConsejo(actoAceptacionProrrogaFovis.getFechaAprobacionConsejo().getTime());
    }

    /**
     * @return the idActoAceptacionProrrogaFovis
     */
    public Long getIdActoAceptacionProrrogaFovis() {
        return idActoAceptacionProrrogaFovis;
    }

    /**
     * @param idActoAceptacionProrrogaFovis
     *        the idActoAceptacionProrrogaFovis to set
     */
    public void setIdActoAceptacionProrrogaFovis(Long idActoAceptacionProrrogaFovis) {
        this.idActoAceptacionProrrogaFovis = idActoAceptacionProrrogaFovis;
    }

    /**
     * @return the idSolicitudNovedadFovis
     */
    public Long getIdSolicitudNovedadFovis() {
        return idSolicitudNovedadFovis;
    }

    /**
     * @param idSolicitudNovedadFovis
     *        the idSolicitudNovedadFovis to set
     */
    public void setIdSolicitudNovedadFovis(Long idSolicitudNovedadFovis) {
        this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
    }

    /**
     * @return the numeroActoAdministrativo
     */
    public String getNumeroActoAdministrativo() {
        return numeroActoAdministrativo;
    }

    /**
     * @param numeroActoAdministrativo
     *        the numeroActoAdministrativo to set
     */
    public void setNumeroActoAdministrativo(String numeroActoAdministrativo) {
        this.numeroActoAdministrativo = numeroActoAdministrativo;
    }

    /**
     * @return the fechaAprobacionConsejo
     */
    public Long getFechaAprobacionConsejo() {
        return fechaAprobacionConsejo;
    }

    /**
     * @param fechaAprobacionConsejo
     *        the fechaAprobacionConsejo to set
     */
    public void setFechaAprobacionConsejo(Long fechaAprobacionConsejo) {
        this.fechaAprobacionConsejo = fechaAprobacionConsejo;
    }

}
