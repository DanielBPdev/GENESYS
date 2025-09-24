package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los registros para la tabla de detalle del archivo procesado <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class DetalleBloqueValidacionArchivoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Estado del archivo en el bloque 0 */
    private EstadoProcesoArchivoEnum estadoB0;
    
    /** Estado del archivo en el bloque 1 */
    private EstadoProcesoArchivoEnum estadoB1;
    
    /** Estado del archivo en el bloque 2 */
    private EstadoProcesoArchivoEnum estadoB2;
    
    /** Estado del archivo en el bloque 3 */
    private EstadoProcesoArchivoEnum estadoB3;
    
    /** Estado del archivo en el bloque 4 */
    private EstadoProcesoArchivoEnum estadoB4;
    
    /** Estado del archivo en el bloque 5 */
    private EstadoProcesoArchivoEnum estadoB5;
    
    /** Estado del archivo en el bloque 6 */
    private EstadoProcesoArchivoEnum estadoB6;
    
    /** Estado del archivo en el bloque 7 */
    private EstadoProcesoArchivoEnum estadoB7;
    
    /** Estado del archivo en el bloque 8 */
    private EstadoProcesoArchivoEnum estadoB8;
    
    /** Estado del archivo en el bloque 9 */
    private EstadoProcesoArchivoEnum estadoB9;
    
    /** Estado del archivo en el bloque 10 */
    private EstadoProcesoArchivoEnum estadoB10;
    
    /** Fecha en la cual se procesó el B0 */
    private Long fechaB0;
    
    /** Fecha en la cual se procesó el B1 */
    private Long fechaB1;
    
    /** Fecha en la cual se procesó el B2 */
    private Long fechaB2;
    
    /** Fecha en la cual se procesó el B3 */
    private Long fechaB3;
    
    /** Fecha en la cual se procesó el B4 */
    private Long fechaB4;
    
    /** Fecha en la cual se procesó el B5 */
    private Long fechaB5;
    
    /** Fecha en la cual se procesó el B6 */
    private Long fechaB6;
    
    /** Fecha en la cual se procesó el B7 */
    private Long fechaB7;
    
    /** Fecha en la cual se procesó el B8 */
    private Long fechaB8;
    
    /** Fecha en la cual se procesó el B9 */
    private Long fechaB9;
    
    /** Fecha en la cual se procesó el B10 */
    private Long fechaB10;
    
    /** Usuario que proceso el archivo */
    private String usuario;
    
    /**
     * Constructor para OI
     * 
     * @param estadoBO
     * @param estadoB1
     * @param estadoB2
     * @param estadoB3
     * @param estadoB4
     * @param estadoB5
     * @param estadoB6
     * @param estadoB7
     * @param estadoB8
     * @param estadoB9
     * @param estadoB10
     * @param fechaB0
     * @param usuario
     */
    public DetalleBloqueValidacionArchivoDTO(EstadoProcesoArchivoEnum estadoB0, EstadoProcesoArchivoEnum estadoB1,
            EstadoProcesoArchivoEnum estadoB2, EstadoProcesoArchivoEnum estadoB3, EstadoProcesoArchivoEnum estadoB4,
            EstadoProcesoArchivoEnum estadoB5, EstadoProcesoArchivoEnum estadoB6, EstadoProcesoArchivoEnum estadoB7,
            EstadoProcesoArchivoEnum estadoB8, EstadoProcesoArchivoEnum estadoB9, EstadoProcesoArchivoEnum estadoB10, 
            Date fechaB0, Date fechaB1, Date fechaB2, Date fechaB3, Date fechaB4, Date fechaB5, Date fechaB6, Date fechaB7,
            Date fechaB8, Date fechaB9, Date fechaB10, String usuario) {
        this.estadoB0 = estadoB0;
        this.estadoB1 = estadoB1;
        this.estadoB2 = estadoB2;
        this.estadoB3 = estadoB3;
        this.estadoB4 = estadoB4;
        this.estadoB5 = estadoB5;
        this.estadoB6 = estadoB6;
        this.estadoB7 = estadoB7;
        this.estadoB8 = estadoB8;
        this.estadoB9 = estadoB9;
        this.estadoB10 = estadoB10;
        this.fechaB0 = fechaB0 != null ? fechaB0.getTime() : null;
        this.fechaB1 = fechaB1 != null ? fechaB1.getTime() : null;
        this.fechaB2 = fechaB2 != null ? fechaB2.getTime() : null;
        this.fechaB3 = fechaB3 != null ? fechaB3.getTime() : null;
        this.fechaB4 = fechaB4 != null ? fechaB4.getTime() : null;
        this.fechaB5 = fechaB5 != null ? fechaB5.getTime() : null;
        this.fechaB6 = fechaB6 != null ? fechaB6.getTime() : null;
        this.fechaB7 = fechaB7 != null ? fechaB7.getTime() : null;
        this.fechaB8 = fechaB8 != null ? fechaB8.getTime() : null;
        this.fechaB9 = fechaB9 != null ? fechaB9.getTime() : null;
        this.fechaB10 = fechaB10 != null ? fechaB10.getTime() : null;
        this.usuario = usuario;
    }
    
    /**
     * Constructor para OF
     * 
     * @param estadoB0
     * @param estadoB1
     * @param estadoB6
     * @param fechaB0
     * @param fechaB1
     * @param fechaB6
     * @param usuario
     */
    public DetalleBloqueValidacionArchivoDTO(EstadoProcesoArchivoEnum estadoB0, EstadoProcesoArchivoEnum estadoB1,
            EstadoProcesoArchivoEnum estadoB6, Date fechaB0, Date fechaB1, Date fechaB6, String usuario){
        this.estadoB0 = estadoB0;
        this.estadoB1 = estadoB1;
        this.estadoB6 = estadoB6;
        this.fechaB0 = fechaB0 != null ? fechaB0.getTime() : null;
        this.fechaB1 = fechaB1 != null ? fechaB1.getTime() : null;
        this.fechaB6 = fechaB6 != null ? fechaB6.getTime() : null;
        this.usuario = usuario;
    }
    
    /**
     * @return the estadoBO
     */
    public EstadoProcesoArchivoEnum getEstadoB0() {
        return estadoB0;
    }
    /**
     * @param estadoBO the estadoBO to set
     */
    public void setEstadoB0(EstadoProcesoArchivoEnum estadoBO) {
        this.estadoB0 = estadoBO;
    }
    /**
     * @return the estadoB1
     */
    public EstadoProcesoArchivoEnum getEstadoB1() {
        return estadoB1;
    }
    /**
     * @param estadoB1 the estadoB1 to set
     */
    public void setEstadoB1(EstadoProcesoArchivoEnum estadoB1) {
        this.estadoB1 = estadoB1;
    }
    /**
     * @return the estadoB2
     */
    public EstadoProcesoArchivoEnum getEstadoB2() {
        return estadoB2;
    }
    /**
     * @param estadoB2 the estadoB2 to set
     */
    public void setEstadoB2(EstadoProcesoArchivoEnum estadoB2) {
        this.estadoB2 = estadoB2;
    }
    /**
     * @return the estadoB3
     */
    public EstadoProcesoArchivoEnum getEstadoB3() {
        return estadoB3;
    }
    /**
     * @param estadoB3 the estadoB3 to set
     */
    public void setEstadoB3(EstadoProcesoArchivoEnum estadoB3) {
        this.estadoB3 = estadoB3;
    }
    /**
     * @return the estadoB4
     */
    public EstadoProcesoArchivoEnum getEstadoB4() {
        return estadoB4;
    }
    /**
     * @param estadoB4 the estadoB4 to set
     */
    public void setEstadoB4(EstadoProcesoArchivoEnum estadoB4) {
        this.estadoB4 = estadoB4;
    }
    /**
     * @return the estadoB5
     */
    public EstadoProcesoArchivoEnum getEstadoB5() {
        return estadoB5;
    }
    /**
     * @param estadoB5 the estadoB5 to set
     */
    public void setEstadoB5(EstadoProcesoArchivoEnum estadoB5) {
        this.estadoB5 = estadoB5;
    }
    /**
     * @return the estadoB6
     */
    public EstadoProcesoArchivoEnum getEstadoB6() {
        return estadoB6;
    }
    /**
     * @param estadoB6 the estadoB6 to set
     */
    public void setEstadoB6(EstadoProcesoArchivoEnum estadoB6) {
        this.estadoB6 = estadoB6;
    }
    /**
     * @return the estadoB7
     */
    public EstadoProcesoArchivoEnum getEstadoB7() {
        return estadoB7;
    }
    /**
     * @param estadoB7 the estadoB7 to set
     */
    public void setEstadoB7(EstadoProcesoArchivoEnum estadoB7) {
        this.estadoB7 = estadoB7;
    }
    /**
     * @return the estadoB8
     */
    public EstadoProcesoArchivoEnum getEstadoB8() {
        return estadoB8;
    }
    /**
     * @param estadoB8 the estadoB8 to set
     */
    public void setEstadoB8(EstadoProcesoArchivoEnum estadoB8) {
        this.estadoB8 = estadoB8;
    }
    /**
     * @return the estadoB9
     */
    public EstadoProcesoArchivoEnum getEstadoB9() {
        return estadoB9;
    }
    /**
     * @param estadoB9 the estadoB9 to set
     */
    public void setEstadoB9(EstadoProcesoArchivoEnum estadoB9) {
        this.estadoB9 = estadoB9;
    }
    /**
     * @return the estadoB10
     */
    public EstadoProcesoArchivoEnum getEstadoB10() {
        return estadoB10;
    }
    /**
     * @param estadoB10 the estadoB10 to set
     */
    public void setEstadoB10(EstadoProcesoArchivoEnum estadoB10) {
        this.estadoB10 = estadoB10;
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * @return the fechaB0
     */
    public Long getFechaB0() {
        return fechaB0;
    }

    /**
     * @param fechaB0 the fechaB0 to set
     */
    public void setFechaB0(Long fechaB0) {
        this.fechaB0 = fechaB0;
    }

    /**
     * @return the fechaB1
     */
    public Long getFechaB1() {
        return fechaB1;
    }

    /**
     * @param fechaB1 the fechaB1 to set
     */
    public void setFechaB1(Long fechaB1) {
        this.fechaB1 = fechaB1;
    }

    /**
     * @return the fechaB2
     */
    public Long getFechaB2() {
        return fechaB2;
    }

    /**
     * @param fechaB2 the fechaB2 to set
     */
    public void setFechaB2(Long fechaB2) {
        this.fechaB2 = fechaB2;
    }

    /**
     * @return the fechaB3
     */
    public Long getFechaB3() {
        return fechaB3;
    }

    /**
     * @param fechaB3 the fechaB3 to set
     */
    public void setFechaB3(Long fechaB3) {
        this.fechaB3 = fechaB3;
    }

    /**
     * @return the fechaB4
     */
    public Long getFechaB4() {
        return fechaB4;
    }

    /**
     * @param fechaB4 the fechaB4 to set
     */
    public void setFechaB4(Long fechaB4) {
        this.fechaB4 = fechaB4;
    }

    /**
     * @return the fechaB5
     */
    public Long getFechaB5() {
        return fechaB5;
    }

    /**
     * @param fechaB5 the fechaB5 to set
     */
    public void setFechaB5(Long fechaB5) {
        this.fechaB5 = fechaB5;
    }

    /**
     * @return the fechaB6
     */
    public Long getFechaB6() {
        return fechaB6;
    }

    /**
     * @param fechaB6 the fechaB6 to set
     */
    public void setFechaB6(Long fechaB6) {
        this.fechaB6 = fechaB6;
    }

    /**
     * @return the fechaB7
     */
    public Long getFechaB7() {
        return fechaB7;
    }

    /**
     * @param fechaB7 the fechaB7 to set
     */
    public void setFechaB7(Long fechaB7) {
        this.fechaB7 = fechaB7;
    }

    /**
     * @return the fechaB8
     */
    public Long getFechaB8() {
        return fechaB8;
    }

    /**
     * @param fechaB8 the fechaB8 to set
     */
    public void setFechaB8(Long fechaB8) {
        this.fechaB8 = fechaB8;
    }

    /**
     * @return the fechaB9
     */
    public Long getFechaB9() {
        return fechaB9;
    }

    /**
     * @param fechaB9 the fechaB9 to set
     */
    public void setFechaB9(Long fechaB9) {
        this.fechaB9 = fechaB9;
    }

    /**
     * @return the fechaB10
     */
    public Long getFechaB10() {
        return fechaB10;
    }

    /**
     * @param fechaB10 the fechaB10 to set
     */
    public void setFechaB10(Long fechaB10) {
        this.fechaB10 = fechaB10;
    }

    public DetalleBloqueValidacionArchivoDTO() {}
}
