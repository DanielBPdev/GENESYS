package com.asopagos.fovis.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta de las cartas de asignacion generadas
 * <b>HU-051</b>
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class CartasAsignacionDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = 2498089760493805105L;

    /**
     * Número de radicación de la solicitud
     */
    private String numeroRadicacion;

    /**
     * Número de identificación del jefe de hogar.
     */
    private String numeroIdentificacionJefeHogar;

    /**
     * Número de identificación del jefe de hogar.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Nombre del jefe de hogar.
     */
    private String nombreJefeHogar;

    /**
     * Apellidos del integrante de hogar.
     */
    private String apellidosJefeHogar;

    /**
     * Modalidad de la postulacion
     */
    private ModalidadEnum modalidad;

    /**
     * Identificador de la carta de asignacion generada para la postulacion
     */
    private String idCartaAsignacion;

    /**
     * Identificador del consolidado la cartaa de asignacion generada para las postulaciones
     */
    private String idConsolidadoCartasAsignacion;

    /**
     * Método constructor para devolver los datos consultados relacionados a la lista de cartas de asignacion
     */
    public CartasAsignacionDTO(String numeroRadicacion, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacionJefeHogar,
            String nombreJefeHogar, String apellidosJefeHogar, ModalidadEnum modalidad, String idCartaAsignacion,
            String idConsolidadoCartasAsignacion) {
        this.setNumeroRadicacion(numeroRadicacion);
        this.setNumeroIdentificacionJefeHogar(numeroIdentificacionJefeHogar);
        this.setTipoIdentificacion(tipoIdentificacion);
        this.setNombreJefeHogar(nombreJefeHogar);
        this.setApellidosJefeHogar(apellidosJefeHogar);
        this.setModalidad(modalidad);
        this.setIdCartaAsignacion(idCartaAsignacion);
        this.setIdConsolidadoCartasAsignacion(idConsolidadoCartasAsignacion);
    }

    /**
     * Método que retorna el valor de numeroIdentificacionJefeHogar.
     * @return valor de numeroIdentificacionJefeHogar.
     */
    public String getNumeroIdentificacionJefeHogar() {
        return numeroIdentificacionJefeHogar;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionJefeHogar.
     * @param valor
     *        para modificar numeroIdentificacionJefeHogar.
     */
    public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
        this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
    }

    public CartasAsignacionDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the nombreJefeHogar
     */
    public String getNombreJefeHogar() {
        return nombreJefeHogar;
    }

    /**
     * @param nombreJefeHogar
     *        the nombreJefeHogar to set
     */
    public void setNombreJefeHogar(String nombreJefeHogar) {
        this.nombreJefeHogar = nombreJefeHogar;
    }

    /**
     * @return the apellidosJefeHogar
     */
    public String getApellidosJefeHogar() {
        return apellidosJefeHogar;
    }

    /**
     * @param apellidosJefeHogar
     *        the apellidosJefeHogar to set
     */
    public void setApellidosJefeHogar(String apellidosJefeHogar) {
        this.apellidosJefeHogar = apellidosJefeHogar;
    }

    /**
     * @return the modalidad
     */
    public ModalidadEnum getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *        the modalidad to set
     */
    public void setModalidad(ModalidadEnum modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the idCartaAsignacion
     */
    public String getIdCartaAsignacion() {
        return idCartaAsignacion;
    }

    /**
     * @param idCartaAsignacion
     *        the idCartaAsignacion to set
     */
    public void setIdCartaAsignacion(String idCartaAsignacion) {
        this.idCartaAsignacion = idCartaAsignacion;
    }

    /**
     * @return the idConsolidadoCartasAsignacion
     */
    public String getIdConsolidadoCartasAsignacion() {
        return idConsolidadoCartasAsignacion;
    }

    /**
     * @param idConsolidadoCartasAsignacion
     *        the idConsolidadoCartasAsignacion to set
     */
    public void setIdConsolidadoCartasAsignacion(String idConsolidadoCartasAsignacion) {
        this.idConsolidadoCartasAsignacion = idConsolidadoCartasAsignacion;
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
