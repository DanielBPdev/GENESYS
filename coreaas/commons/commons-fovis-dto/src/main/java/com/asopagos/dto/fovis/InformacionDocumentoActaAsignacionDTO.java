package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.ActaAsignacionFOVIS;

/**
 * <b>Descripción</b> DTO que representa los datos requeridos para la generación del soporte de la asignación
 * <b>HU-050</b>
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class InformacionDocumentoActaAsignacionDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = -7663533090220467012L;

    /**
     * Número de resolución SSF
     */
    private String numeroResolucionSsf;

    /**
     * Año resolución SSF
     */
    private String anoResolucionSsf;

    /**
     * Fecha resolución.
     */
    private Long fechaResolucion;

    /**
     * Numero de oficio
     */
    private String numeroOficio;

    /**
     * Fecha de oficio
     */
    private Long fechaOficio;

    /**
     * Fecha inicial periodo postulación
     */
    private Long fechaInicialPostulacion;

    /**
     * Fecha inicial periodo postulación
     */
    private Long fechaFinalPostulacion;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable1;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable1;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable2;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable2;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String nombreResponsable3;

    /**
     * Responsables de firmar documento de asignacion FOVIS
     */
    private String cargoResponsable3;

    /**
     * Ciclo de asignacion
     */
    private String cicloAsignacion;

    /**
     * Valor disponible del Ciclo de asignacion
     */
    private BigDecimal valorDisponible;

    /**
     * Fecha de confirmacion del valor por area contable
     */
    private Long fechaConfirmacionAreaContable;

    /**
     * Fecha de corte asociada al valor disponible informado
     */
    private Long fechaCorteAsociadaValorDisponible;

    /**
     * Fecha de publicación
     */
    private Long fechaPublicacion;

    /**
     * Fecha de publicación
     */
    private Long fechaActaAsignacion;

    /**
     * Fecha de inicio de vigencia de los subsidios asignados
     */
    private Long fechaInicioVigenciaSubsidios;

    /**
     * Fecha de vencimiento vigencia de los subsidios asignados
     */
    private Long fechaVencimientoVigenciaSubsidios;

    /**
     * Identificador del acta de asignación
     */
    private Long idActaAsignacion;

    /**
     * Método constructor para devolver los datos consultados relacionados a la generacion del documento de soporte acta de asignación
     */
    public InformacionDocumentoActaAsignacionDTO() {
    }

    /**
     * Método constructor para devolver los datos consultados relacionados a la generacion del documento de soporte acta de asignación
     */
    public InformacionDocumentoActaAsignacionDTO(String numeroResolucionSsf, String anoResolucionSsf, Long fechaResolucion,
            String numeroOficio, Long fechaOficio, Long fechaInicialPostulacion, Long fechaFinalPostulacion, String nombreResponsable1,
            String cargoResponsable1, String nombreResponsable2, String cargoResponsable2, String nombreResponsable3,
            String cargoResponsable3) {
        this.setAnoResolucionSsf(anoResolucionSsf);
        this.setFechaFinalPostulacion(fechaFinalPostulacion);
        this.setFechaInicialPostulacion(fechaInicialPostulacion);
        this.setNumeroOficio(numeroOficio);
        this.setFechaOficio(fechaOficio);
        this.setFechaResolucion(fechaResolucion);
        this.setNumeroResolucionSsf(numeroResolucionSsf);
        this.setCargoResponsable1(cargoResponsable1);
        this.setNombreResponsable1(nombreResponsable1);
        this.setCargoResponsable2(cargoResponsable2);
        this.setNombreResponsable2(nombreResponsable2);
        this.setCargoResponsable3(cargoResponsable3);
        this.setNombreResponsable3(nombreResponsable3);
    }

    /**
     * Convierte la entidad acta de asignación en el DTO
     * @param actaAsignacion
     *        Información acta de asignacion
     */
    public void convertToDTO(ActaAsignacionFOVIS actaAsignacion) {
        this.setAnoResolucionSsf(actaAsignacion.getAnoResolucion());
        this.setCargoResponsable1(actaAsignacion.getCargoResponsable1());
        this.setCargoResponsable2(actaAsignacion.getCargoResponsable2());
        this.setCargoResponsable3(actaAsignacion.getCargoResponsable3());
        this.setFechaActaAsignacion(
                actaAsignacion.getFechaActaAsignacionFovis() != null ? actaAsignacion.getFechaActaAsignacionFovis().getTime() : null);
        this.setFechaConfirmacionAreaContable(
                actaAsignacion.getFechaConfirmacion() != null ? actaAsignacion.getFechaConfirmacion().getTime() : null);
        this.setFechaCorteAsociadaValorDisponible(actaAsignacion.getFechaCorte() != null ? actaAsignacion.getFechaCorte().getTime() : null);
        this.setFechaVencimientoVigenciaSubsidios(
                actaAsignacion.getFechaFinVigencia() != null ? actaAsignacion.getFechaFinVigencia().getTime() : null);
        this.setFechaInicioVigenciaSubsidios(
                actaAsignacion.getFechaInicioVigencia() != null ? actaAsignacion.getFechaInicioVigencia().getTime() : null);
        this.setFechaOficio(actaAsignacion.getFechaOficio() != null ? actaAsignacion.getFechaOficio().getTime() : null);
        this.setFechaPublicacion(actaAsignacion.getFechaPublicacion() != null ? actaAsignacion.getFechaPublicacion().getTime() : null);
        this.setFechaResolucion(actaAsignacion.getFechaResolucion() != null ? actaAsignacion.getFechaResolucion().getTime() : null);
        this.setIdActaAsignacion(actaAsignacion.getIdActaAsignacion());
        this.setNombreResponsable1(actaAsignacion.getNombreResponsable1());
        this.setNombreResponsable2(actaAsignacion.getNombreResponsable2());
        this.setNombreResponsable3(actaAsignacion.getNombreResponsable3());
        this.setNumeroOficio(actaAsignacion.getNumeroOficio());
        this.setNumeroResolucionSsf(actaAsignacion.getNumeroResolucion());
    }

    /**
     * Convierte la entidad acta de asignacion al DTO teniendo en cuenta la regla
     * @param actaAsignacion
     *        Información acta de asignacion
     * @param anterior
     *        Regla de conversion
     */
    public void convertEntityToDTO(ActaAsignacionFOVIS actaAsignacion, Boolean anterior) {
        if (!anterior) {
            this.convertToDTO(actaAsignacion);
        }
        else {
            this.setCargoResponsable1(actaAsignacion.getCargoResponsable1());
            this.setCargoResponsable2(actaAsignacion.getCargoResponsable2());
            this.setCargoResponsable3(actaAsignacion.getCargoResponsable3());
            this.setNombreResponsable1(actaAsignacion.getNombreResponsable1());
            this.setNombreResponsable2(actaAsignacion.getNombreResponsable2());
            this.setNombreResponsable3(actaAsignacion.getNombreResponsable3());
        }
    }

    /**
     * @return the numeroResolucionSsf
     */
    public String getNumeroResolucionSsf() {
        return numeroResolucionSsf;
    }

    /**
     * @param numeroResolucionSsf
     *        the numeroResolucionSsf to set
     */
    public void setNumeroResolucionSsf(String numeroResolucionSsf) {
        this.numeroResolucionSsf = numeroResolucionSsf;
    }

    /**
     * @return the anoResolucionSsf
     */
    public String getAnoResolucionSsf() {
        return anoResolucionSsf;
    }

    /**
     * @param anoResolucionSsf
     *        the anoResolucionSsf to set
     */
    public void setAnoResolucionSsf(String anoResolucionSsf) {
        this.anoResolucionSsf = anoResolucionSsf;
    }

    /**
     * @return the fechaResolucion
     */
    public Long getFechaResolucion() {
        return fechaResolucion;
    }

    /**
     * @param fechaResolucion
     *        the fechaResolucion to set
     */
    public void setFechaResolucion(Long fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    /**
     * @return the fechaOficio
     */
    public Long getFechaOficio() {
        return fechaOficio;
    }

    /**
     * @param fechaOficio
     *        the fechaOficio to set
     */
    public void setFechaOficio(Long fechaOficio) {
        this.fechaOficio = fechaOficio;
    }

    /**
     * @return the fechaInicialPostulacion
     */
    public Long getFechaInicialPostulacion() {
        return fechaInicialPostulacion;
    }

    /**
     * @param fechaInicialPostulacion
     *        the fechaInicialPostulacion to set
     */
    public void setFechaInicialPostulacion(Long fechaInicialPostulacion) {
        this.fechaInicialPostulacion = fechaInicialPostulacion;
    }

    /**
     * @return the fechaFinalPostulacion
     */
    public Long getFechaFinalPostulacion() {
        return fechaFinalPostulacion;
    }

    /**
     * @param fechaFinalPostulacion
     *        the fechaFinalPostulacion to set
     */
    public void setFechaFinalPostulacion(Long fechaFinalPostulacion) {
        this.fechaFinalPostulacion = fechaFinalPostulacion;
    }

    /**
     * @return the nombreResponsable1
     */
    public String getNombreResponsable1() {
        return nombreResponsable1;
    }

    /**
     * @param nombreResponsable1
     *        the nombreResponsable1 to set
     */
    public void setNombreResponsable1(String nombreResponsable1) {
        this.nombreResponsable1 = nombreResponsable1;
    }

    /**
     * @return the cargoResponsable1
     */
    public String getCargoResponsable1() {
        return cargoResponsable1;
    }

    /**
     * @param cargoResponsable1
     *        the cargoResponsable1 to set
     */
    public void setCargoResponsable1(String cargoResponsable1) {
        this.cargoResponsable1 = cargoResponsable1;
    }

    /**
     * @return the nombreResponsable2
     */
    public String getNombreResponsable2() {
        return nombreResponsable2;
    }

    /**
     * @param nombreResponsable2
     *        the nombreResponsable2 to set
     */
    public void setNombreResponsable2(String nombreResponsable2) {
        this.nombreResponsable2 = nombreResponsable2;
    }

    /**
     * @return the cargoResponsable2
     */
    public String getCargoResponsable2() {
        return cargoResponsable2;
    }

    /**
     * @param cargoResponsable2
     *        the cargoResponsable2 to set
     */
    public void setCargoResponsable2(String cargoResponsable2) {
        this.cargoResponsable2 = cargoResponsable2;
    }

    /**
     * @return the nombreResponsable3
     */
    public String getNombreResponsable3() {
        return nombreResponsable3;
    }

    /**
     * @param nombreResponsable3
     *        the nombreResponsable3 to set
     */
    public void setNombreResponsable3(String nombreResponsable3) {
        this.nombreResponsable3 = nombreResponsable3;
    }

    /**
     * @return the cargoResponsable3
     */
    public String getCargoResponsable3() {
        return cargoResponsable3;
    }

    /**
     * @param cargoResponsable3
     *        the cargoResponsable3 to set
     */
    public void setCargoResponsable3(String cargoResponsable3) {
        this.cargoResponsable3 = cargoResponsable3;
    }

    /**
     * @return the numeroOficio
     */
    public String getNumeroOficio() {
        return numeroOficio;
    }

    /**
     * @param numeroOficio
     *        the numeroOficio to set
     */
    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    /**
     * @return the cicloAsignacion
     */
    public String getCicloAsignacion() {
        return cicloAsignacion;
    }

    /**
     * @param cicloAsignacion
     *        the cicloAsignacion to set
     */
    public void setCicloAsignacion(String cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
    }

    /**
     * @return the valorDisponible
     */
    public BigDecimal getValorDisponible() {
        return valorDisponible;
    }

    /**
     * @param valorDisponible
     *        the valorDisponible to set
     */
    public void setValorDisponible(BigDecimal valorDisponible) {
        this.valorDisponible = valorDisponible;
    }

    /**
     * @return the fechaConfirmacionAreaContable
     */
    public Long getFechaConfirmacionAreaContable() {
        return fechaConfirmacionAreaContable;
    }

    /**
     * @param fechaConfirmacionAreaContable
     *        the fechaConfirmacionAreaContable to set
     */
    public void setFechaConfirmacionAreaContable(Long fechaConfirmacionAreaContable) {
        this.fechaConfirmacionAreaContable = fechaConfirmacionAreaContable;
    }

    /**
     * @return the fechaCorteAsociadaValorDisponible
     */
    public Long getFechaCorteAsociadaValorDisponible() {
        return fechaCorteAsociadaValorDisponible;
    }

    /**
     * @param fechaCorteAsociadaValorDisponible
     *        the fechaCorteAsociadaValorDisponible to set
     */
    public void setFechaCorteAsociadaValorDisponible(Long fechaCorteAsociadaValorDisponible) {
        this.fechaCorteAsociadaValorDisponible = fechaCorteAsociadaValorDisponible;
    }

    /**
     * @return the fechaPublicacion
     */
    public Long getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion
     *        the fechaPublicacion to set
     */
    public void setFechaPublicacion(Long fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the fechaActaAsignacion
     */
    public Long getFechaActaAsignacion() {
        return fechaActaAsignacion;
    }

    /**
     * @param fechaActaAsignacion
     *        the fechaActaAsignacion to set
     */
    public void setFechaActaAsignacion(Long fechaActaAsignacion) {
        this.fechaActaAsignacion = fechaActaAsignacion;
    }

    /**
     * @return the fechaInicioVigenciaSubsidios
     */
    public Long getFechaInicioVigenciaSubsidios() {
        return fechaInicioVigenciaSubsidios;
    }

    /**
     * @param fechaInicioVigenciaSubsidios
     *        the fechaInicioVigenciaSubsidios to set
     */
    public void setFechaInicioVigenciaSubsidios(Long fechaInicioVigenciaSubsidios) {
        this.fechaInicioVigenciaSubsidios = fechaInicioVigenciaSubsidios;
    }

    /**
     * @return the fechaVencimientoVigenciaSubsidios
     */
    public Long getFechaVencimientoVigenciaSubsidios() {
        return fechaVencimientoVigenciaSubsidios;
    }

    /**
     * @param fechaVencimientoVigenciaSubsidios
     *        the fechaVencimientoVigenciaSubsidios to set
     */
    public void setFechaVencimientoVigenciaSubsidios(Long fechaVencimientoVigenciaSubsidios) {
        this.fechaVencimientoVigenciaSubsidios = fechaVencimientoVigenciaSubsidios;
    }

    /**
     * @return the idActaAsignacion
     */
    public Long getIdActaAsignacion() {
        return idActaAsignacion;
    }

    /**
     * @param idActaAsignacion
     *        the idActaAsignacion to set
     */
    public void setIdActaAsignacion(Long idActaAsignacion) {
        this.idActaAsignacion = idActaAsignacion;
    }

}
