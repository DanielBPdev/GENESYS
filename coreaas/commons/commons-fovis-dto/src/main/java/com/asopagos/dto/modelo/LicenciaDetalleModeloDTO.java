package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.fovis.LicenciaDetalle;
import com.asopagos.enumeraciones.fovis.ClasificacionLicenciaEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de la tabla
 * <code>LicenciaDetalle</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class LicenciaDetalleModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 2487813079108848431L;

    /**
     * Identificador único, llave primaria
     */
    private Long idLicenciaDetalle;

    /**
     * Número de Resolucion de la Licencia Detalle
     */
    private String numeroResolucion;

    /**
     * Fecha Inicio de la Licencia Detalle
     */
    private Long fechaInicio;

    /**
     * Fecha Fin de la Licencia Detalle
     */
    private Long fechaFin;

    /**
     * Licencia asociada a la Licencia Detalle
     */
    private Long idLicencia;

    /**
     * Estado de la licencia
     */
    private Boolean estadoLicencia;

    /**
     * Clasificacion de la licencia
     */
    private ClasificacionLicenciaEnum clasificacionLicencia;

    /**
     * Constructor por defecto
     */
    public LicenciaDetalleModeloDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor a partir de una entidad LicenciaDetalle
     */
    public LicenciaDetalleModeloDTO(LicenciaDetalle licenciaDetalle) {
        convertToDTO(licenciaDetalle);
    }

    /**
     * Método que convierte el DTO en la entidad equivalente
     * 
     * @return La entidad <code>LicenciaDetalle</code> equivalente
     */
    public LicenciaDetalle convertToEntity() {
        LicenciaDetalle licenciaDetalle = new LicenciaDetalle();

        if (this.fechaFin != null) {
            licenciaDetalle.setFechaFin(new Date(this.fechaFin));
        }

        if (this.fechaInicio != null) {
            licenciaDetalle.setFechaInicio(this.fechaInicio != null ? new Date(this.fechaInicio) : null);
        }

        licenciaDetalle.setIdLicencia(this.idLicencia);
        licenciaDetalle.setIdLicenciaDetalle(this.idLicenciaDetalle);
        licenciaDetalle.setNumeroResolucion(this.numeroResolucion);

        licenciaDetalle.setEstadoLicencia(this.estadoLicencia);
        licenciaDetalle.setClasificacionLicencia(this.clasificacionLicencia);

        return licenciaDetalle;
    }

    /**
     * Método que convierte una entidad <code>LicenciaDetalle</code> en
     * el DTO
     * 
     * @param licenciaDetalle
     *        La entidad a convertir
     */
    public void convertToDTO(LicenciaDetalle licenciaDetalle) {
        this.idLicencia = licenciaDetalle.getIdLicencia();
        this.fechaInicio = licenciaDetalle.getFechaInicio() != null ? licenciaDetalle.getFechaInicio().getTime() : null;
        this.fechaFin = licenciaDetalle.getFechaFin() != null ? licenciaDetalle.getFechaFin().getTime() : null;
        this.idLicenciaDetalle = licenciaDetalle.getIdLicenciaDetalle();
        this.numeroResolucion = licenciaDetalle.getNumeroResolucion();

        this.estadoLicencia = licenciaDetalle.getEstadoLicencia();
        this.clasificacionLicencia = licenciaDetalle.getClasificacionLicencia();

    }

    /**
     * Obtiene el valor de idLicenciaDetalle
     * 
     * @return El valor de idLicenciaDetalle
     */
    public Long getIdLicenciaDetalle() {
        return idLicenciaDetalle;
    }

    /**
     * Establece el valor de idLicenciaDetalle
     * 
     * @param idLicenciaDetalle
     *        El valor de idLicenciaDetalle por asignar
     */
    public void setIdLicenciaDetalle(Long idLicenciaDetalle) {
        this.idLicenciaDetalle = idLicenciaDetalle;
    }

    /**
     * Obtiene el valor de numeroResolucion
     * 
     * @return El valor de numeroResolucion
     */
    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    /**
     * Establece el valor de numeroResolucion
     * 
     * @param numeroResolucion
     *        El valor de numeroResolucion por asignar
     */
    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    /**
     * Obtiene el valor de fechaFin
     * 
     * @return El valor de fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece el valor de fechaFin
     * 
     * @param fechaFin
     *        El valor de fechaFin por asignar
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el valor de idLicencia
     * 
     * @return El valor de idLicencia
     */
    public Long getIdLicencia() {
        return idLicencia;
    }

    /**
     * Establece el valor de idLicencia
     * 
     * @param idLicencia
     *        El valor de idLicencia por asignar
     */
    public void setIdLicencia(Long idLicencia) {
        this.idLicencia = idLicencia;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the estadoLicencia
     */
    public Boolean getEstadoLicencia() {
        return estadoLicencia;
    }

    /**
     * @param estadoLicencia
     *        the estadoLicencia to set
     */
    public void setEstadoLicencia(Boolean estadoLicencia) {
        this.estadoLicencia = estadoLicencia;
    }

    /**
     * @return the clasificacionLicencia
     */
    public ClasificacionLicenciaEnum getClasificacionLicencia() {
        return clasificacionLicencia;
    }

    /**
     * @param clasificacionLicencia
     *        the clasificacionLicencia to set
     */
    public void setClasificacionLicencia(ClasificacionLicenciaEnum clasificacionLicencia) {
        this.clasificacionLicencia = clasificacionLicencia;
    }
}
