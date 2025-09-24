package com.asopagos.dto.modelo;

import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.enumeraciones.pila.TipoInteresEnum;

public class TasasInteresMoraModeloDTO {
    
    /**
     * Código identificador de llave primaria de la entrada de tasa de interés
     */
    private Long id;

    /**
     * Fecha de inicio del período de vigencia de la tasa de interés
     */
    private Date fechaInicioTasa;

    /**
     * Fecha de finalización del período de vigencia de la tasa de interés
     */
    private Date fechaFinTasa;

    /**
     * Númeración consecutiva del período de tasa de interés
     */
    private Short numeroPeriodoTasa;

    /**
     * Porcentaje de tasa de interés
     */
    private BigDecimal porcentajeTasa;

    /**
     * Descripción de la normatividad que determina la tasa de interés
     */
    private String normativa;

    /**
     * Descripción del tipo de interés definido
     */
    private TipoInteresEnum tipoInteres;
    
    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param Entidad
     *        a convertir
     */
    public void convertToDTO(TasasInteresMora tasaInteresMora) {
        this.setId(tasaInteresMora.getId());
        this.setFechaInicioTasa(tasaInteresMora.getFechaInicioTasa());
        this.setFechaFinTasa(tasaInteresMora.getFechaFinTasa());
        this.setNumeroPeriodoTasa(tasaInteresMora.getNumeroPeriodoTasa());
        this.setPorcentajeTasa(tasaInteresMora.getPorcentajeTasa());
        this.setNormativa(tasaInteresMora.getNormativa());
        this.setTipoInteres(tasaInteresMora.getTipoInteres());
    }
    
    /**
     * Metodo encargado de convertir de DTO a entidad
     * @return Entidad convertida
     */
    public TasasInteresMora convertToEntity() {
        TasasInteresMora tasasInteresMora = new TasasInteresMora();
        tasasInteresMora.setId(this.getId());
        tasasInteresMora.setFechaInicioTasa(this.getFechaInicioTasa());
        tasasInteresMora.setFechaFinTasa(this.getFechaFinTasa());
        tasasInteresMora.setNumeroPeriodoTasa(this.getNumeroPeriodoTasa());
        tasasInteresMora.setPorcentajeTasa(this.getPorcentajeTasa());        
        tasasInteresMora.setNormativa(this.getNormativa());
        tasasInteresMora.setTipoInteres(this.getTipoInteres());
        
        return tasasInteresMora;
    }
    
    public TasasInteresMoraModeloDTO(TasasInteresMora tasaInteresMora) {
        this.convertToDTO(tasaInteresMora);
    }

    public TasasInteresMoraModeloDTO() {}

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
     * @return the fechaInicioTasa
     */
    public Date getFechaInicioTasa() {
        return fechaInicioTasa;
    }

    /**
     * @param fechaInicioTasa the fechaInicioTasa to set
     */
    public void setFechaInicioTasa(Date fechaInicioTasa) {
        this.fechaInicioTasa = fechaInicioTasa;
    }

    /**
     * @return the fechaFinTasa
     */
    public Date getFechaFinTasa() {
        return fechaFinTasa;
    }

    /**
     * @param fechaFinTasa the fechaFinTasa to set
     */
    public void setFechaFinTasa(Date fechaFinTasa) {
        this.fechaFinTasa = fechaFinTasa;
    }

    /**
     * @return the numeroPeriodoTasa
     */
    public Short getNumeroPeriodoTasa() {
        return numeroPeriodoTasa;
    }

    /**
     * @param numeroPeriodoTasa the numeroPeriodoTasa to set
     */
    public void setNumeroPeriodoTasa(Short numeroPeriodoTasa) {
        this.numeroPeriodoTasa = numeroPeriodoTasa;
    }

    /**
     * @return the porcentajeTasa
     */
    public BigDecimal getPorcentajeTasa() {
        return porcentajeTasa;
    }

    /**
     * @param porcentajeTasa the porcentajeTasa to set
     */
    public void setPorcentajeTasa(BigDecimal porcentajeTasa) {
        this.porcentajeTasa = porcentajeTasa;
    }

    /**
     * @return the normativa
     */
    public String getNormativa() {
        return normativa;
    }

    /**
     * @param normativa the normativa to set
     */
    public void setNormativa(String normativa) {
        this.normativa = normativa;
    }

    /**
     * @return the tipoInteres
     */
    public TipoInteresEnum getTipoInteres() {
        return tipoInteres;
    }

    /**
     * @param tipoInteres the tipoInteres to set
     */
    public void setTipoInteres(TipoInteresEnum tipoInteres) {
        this.tipoInteres = tipoInteres;
    }
    
}
