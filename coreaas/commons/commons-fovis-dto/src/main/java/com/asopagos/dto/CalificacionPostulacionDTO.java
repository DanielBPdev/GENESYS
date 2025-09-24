package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.ccf.fovis.CalificacionPostulacion;

/**
 * DTO que representa los datos de la entidad CalificacionPostulacion
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 *
 */
public class CalificacionPostulacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único, llave primaria
     */
    private Long idCalificacionPostulacion;

    /**
     * Informacón de la PostulacionFOVIS
     */
    private Long idPostulacion;

    /**
     * Informacion del ciclo de asignacion de la postulacion
     */
    private Long idCicloAsignacion;

    /**
     * Fecha y hora en que se realizó la calificación del hogar
     */
    private Long fechaCalificacion;

    /**
     * Puntaje obtenido por la postulacion
     */
    private BigDecimal puntaje;

    /**
     * Puntaje asociado valor B1
     */
    private BigDecimal valorB1;

    /**
     * Puntaje asociado valor B2
     */
    private BigDecimal valorB2;

    /**
     * Puntaje asociado valor B3
     */
    private BigDecimal valorB3;

    /**
     * Puntaje asociado valor B4
     */
    private BigDecimal valorB4;

    /**
     * Puntaje asociado valor B5
     */
    private BigDecimal valorB5;

    /**
     * Puntaje asociado valor B6
     */
    private BigDecimal valorB6;

    /**
     * Puntajae asociado valor parte 7
     */
    private BigDecimal valorParte7;

    /**
     * Puntajae asociado valor parte 8
     */
    private BigDecimal valorParte8;

    /**
     * Puntajae asociado valor adicional
     */
    private BigDecimal valorAdicional;

    /**
     * Ejecutado desde el parametto
     */
    private Boolean ejecutado;

    /**
     * Constructor vacio
     */
    public CalificacionPostulacionDTO() {
        super();
    }

    /**
     * Constructor que recibe los datos de la entidad
     * @param calificacionPostulacion
     *        Información de la calificación
     */
    public CalificacionPostulacionDTO(CalificacionPostulacion calificacionPostulacion) {
        this.convertToDTO(calificacionPostulacion);
    }


    /**
     * Asocia los datos de la entidad al DTO
     * @param calificacionPostulacion
     *        Información calificación postulación
     */
    public void convertToDTO(CalificacionPostulacion calificacionPostulacion) {
        this.setIdCalificacionPostulacion(calificacionPostulacion.getIdCalificacionPostulacion());
        this.setPuntaje(calificacionPostulacion.getPuntaje());
        this.setValorB1(calificacionPostulacion.getValorB1());
        this.setValorB2(calificacionPostulacion.getValorB2());
        this.setValorB3(calificacionPostulacion.getValorB3());
        this.setValorB4(calificacionPostulacion.getValorB4());
        this.setValorB5(calificacionPostulacion.getValorB5());
        this.setValorB6(calificacionPostulacion.getValorB6());
        this.setValorParte7(calificacionPostulacion.getValorParte7());
        this.setValorParte8(calificacionPostulacion.getValorParte8());
        this.setValorAdicional(calificacionPostulacion.getValorAdicional());
        this.setFechaCalificacion(
                calificacionPostulacion.getFechaCalificacion() != null ? calificacionPostulacion.getFechaCalificacion().getTime() : null);
        this.setIdPostulacion(calificacionPostulacion.getIdPostulacion());
        this.setIdCicloAsignacion(calificacionPostulacion.getIdCicloAsignacion());
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return CalificacionPostulacion
     */
    public CalificacionPostulacion convertToEntity() {
        CalificacionPostulacion calificacionPostulacion = new CalificacionPostulacion();
        calificacionPostulacion.setIdCalificacionPostulacion(this.getIdCalificacionPostulacion());
        calificacionPostulacion.setPuntaje(this.getPuntaje());
        calificacionPostulacion.setValorB1(this.getValorB1());
        calificacionPostulacion.setValorB2(this.getValorB2());
        calificacionPostulacion.setValorB3(this.getValorB3());
        calificacionPostulacion.setValorB4(this.getValorB4());
        calificacionPostulacion.setValorB5(this.getValorB5());
        calificacionPostulacion.setValorB6(this.getValorB6());
        calificacionPostulacion.setValorParte7(this.getValorParte7());
        calificacionPostulacion.setValorParte8(this.getValorParte8());
        calificacionPostulacion.setValorAdicional(this.getValorAdicional());
        calificacionPostulacion.setFechaCalificacion(this.getFechaCalificacion() != null ? new Date(this.getFechaCalificacion()) : null);
        calificacionPostulacion.setIdPostulacion(this.getIdPostulacion());
        calificacionPostulacion.setIdCicloAsignacion(this.getIdCicloAsignacion());
        calificacionPostulacion.setEjecutado(this.getEjecutado());
        return calificacionPostulacion;
    }

    /**
     * @return the idCalificacionPostulacion
     */
    public Long getIdCalificacionPostulacion() {
        return idCalificacionPostulacion;
    }

    /**
     * @param idCalificacionPostulacion the idCalificacionPostulacion to set
     */
    public void setIdCalificacionPostulacion(Long idCalificacionPostulacion) {
        this.idCalificacionPostulacion = idCalificacionPostulacion;
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the idCicloAsignacion
     */
    public Long getIdCicloAsignacion() {
        return idCicloAsignacion;
    }

    /**
     * @param idCicloAsignacion the idCicloAsignacion to set
     */
    public void setIdCicloAsignacion(Long idCicloAsignacion) {
        this.idCicloAsignacion = idCicloAsignacion;
    }

    /**
     * @return the fechaCalificacion
     */
    public Long getFechaCalificacion() {
        return fechaCalificacion;
    }

    /**
     * @param fechaCalificacion the fechaCalificacion to set
     */
    public void setFechaCalificacion(Long fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    /**
     * @return the puntaje
     */
    public BigDecimal getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * @return the valorB1
     */
    public BigDecimal getValorB1() {
        return valorB1;
    }

    /**
     * @param valorB1 the valorB1 to set
     */
    public void setValorB1(BigDecimal valorB1) {
        this.valorB1 = valorB1;
    }

    /**
     * @return the valorB2
     */
    public BigDecimal getValorB2() {
        return valorB2;
    }

    /**
     * @param valorB2 the valorB2 to set
     */
    public void setValorB2(BigDecimal valorB2) {
        this.valorB2 = valorB2;
    }

    /**
     * @return the valorB3
     */
    public BigDecimal getValorB3() {
        return valorB3;
    }

    /**
     * @param valorB3 the valorB3 to set
     */
    public void setValorB3(BigDecimal valorB3) {
        this.valorB3 = valorB3;
    }

    /**
     * @return the valorB4
     */
    public BigDecimal getValorB4() {
        return valorB4;
    }

    /**
     * @param valorB4 the valorB4 to set
     */
    public void setValorB4(BigDecimal valorB4) {
        this.valorB4 = valorB4;
    }

    /**
     * @return the valorB5
     */
    public BigDecimal getValorB5() {
        return valorB5;
    }

    /**
     * @param valorB5 the valorB5 to set
     */
    public void setValorB5(BigDecimal valorB5) {
        this.valorB5 = valorB5;
    }

    /**
     * @return the valorB6
     */
    public BigDecimal getValorB6() {
        return valorB6;
    }

    /**
     * @param valorB6 the valorB6 to set
     */
    public void setValorB6(BigDecimal valorB6) {
        this.valorB6 = valorB6;
    }

    /**
     * @return the valorParte7
     */
    public BigDecimal getValorParte7() {
        return valorParte7;
    }

    /**
     * @param valorParte7 the valorParte7 to set
     */
    public void setValorParte7(BigDecimal valorParte7) {
        this.valorParte7 = valorParte7;
    }

    /**
     * @return the valorParte8
     */
    public BigDecimal getValorParte8() {
        return valorParte8;
    }

    /**
     * @param valorParte8 the valorParte8 to set
     */
    public void setValorParte8(BigDecimal valorParte8) {
        this.valorParte8 = valorParte8;
    }

    public BigDecimal getValorAdicional() {
        return valorAdicional;
    }

    public void setValorAdicional(BigDecimal valorAdicional) {
        this.valorAdicional = valorAdicional;
    }

    public Boolean getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

}
