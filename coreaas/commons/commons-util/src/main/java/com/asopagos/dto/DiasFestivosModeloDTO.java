package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.transversal.core.DiasFestivos;

/**
 * <b>Descripcion:</b> DTO que representa la confirguración de un día festivo en la BD <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class DiasFestivosModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Código identificador de llave primaria de la entrada de fecha festiva */
    private Long id;

    /** Descripción del concepto de la fecha festiva */
    private String concepto;

    /** Fecha completa del día festivo */
    private Date fecha;
    
    /**
     * Función para convertir de Entity a DTO 
     * */
    public void converToDTO(DiasFestivos diaFestivo){
        this.setConcepto(diaFestivo.getConcepto());
        this.setFecha(diaFestivo.getFecha());
        this.setId(diaFestivo.getId());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
