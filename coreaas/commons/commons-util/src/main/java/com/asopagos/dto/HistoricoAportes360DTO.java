package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class HistoricoAportes360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Date fechaRecaudo;
    private Long operacionN1;
    private Long operacionN2;
    private ModalidadRecaudoAporteEnum metodoRecaudo;
    private String numeroPlanilla;
    private TipoPlanillaEnum tipoPlanilla;
    private String periodo;
    private int tieneModificaciones;
    private BigDecimal montoAportes;
    private BigDecimal montoIntereses;
    private BigDecimal montoTotal;
    private Long idRegistroDetallado;
    private Long idRegistroGeneral;
    private TipoIdentificacionEnum tipoIdentificacionPpt;
    private String numeroIdentificacionPpt;
    private String razonSocialPpt;
    private Date fechaPago;
    private Boolean pagoPorSiMismo;
    
    
    /**
     * 
     */
    public HistoricoAportes360DTO() {
    }


    /**
     * @param fechaRecaudo
     * @param operacionN1
     * @param operacionN2
     * @param metodoRecaudo
     * @param periodo
     * @param tieneModificaciones
     * @param montoAportes
     * @param montoIntereses
     * @param montoTotal
     * @param idRegistroDetallado
     * @param idRegistroGeneral
     */
    public HistoricoAportes360DTO(Date fechaRecaudo, Long operacionN1, Long operacionN2, String metodoRecaudo, String periodo,
            Integer tieneModificaciones, BigDecimal montoAportes, BigDecimal montoIntereses, BigDecimal montoTotal,
            Long idRegistroDetallado, Long idRegistroGeneral, Date fechaPago) {
        this.fechaRecaudo = fechaRecaudo;
        this.operacionN1 = operacionN1;
        this.operacionN2 = operacionN2;
        this.metodoRecaudo = ModalidadRecaudoAporteEnum.valueOf(metodoRecaudo);
        this.periodo = periodo;
        this.tieneModificaciones = tieneModificaciones;
        this.montoAportes = montoAportes;
        this.montoIntereses = montoIntereses;
        this.montoTotal = montoTotal;
        this.idRegistroDetallado = idRegistroDetallado;
        this.idRegistroGeneral = idRegistroGeneral;
        this.fechaPago = fechaPago;
    }
    
    /**
     * @param fechaRecaudo
     * @param operacionN1
     * @param operacionN2
     * @param metodoRecaudo
     * @param periodo
     * @param tieneModificaciones
     * @param montoAportes
     * @param montoIntereses
     * @param montoTotal
     * @param idRegistroDetallado
     * @param idRegistroGeneral
     * @param tipoIdentificacionPpt
     * @param numeroIdentificacionPpt
     * @param razonSocialPpt
     */
    public HistoricoAportes360DTO(Date fechaRecaudo, Long operacionN1, Long operacionN2, String metodoRecaudo, String periodo,
            Integer tieneModificaciones, BigDecimal montoAportes, BigDecimal montoIntereses, BigDecimal montoTotal,
            Long idRegistroDetallado, Long idRegistroGeneral, Date fechaPago, 
            String tipoIdentificacionPpt, String numeroIdentificacionPpt, String razonSocialPpt,
            Boolean apgPagadorPorTerceros) {
        this.fechaRecaudo = fechaRecaudo;
        this.operacionN1 = operacionN1;
        this.operacionN2 = operacionN2;
        this.metodoRecaudo = ModalidadRecaudoAporteEnum.valueOf(metodoRecaudo);
        this.periodo = periodo;
        this.tieneModificaciones = tieneModificaciones;
        this.montoAportes = montoAportes;
        this.montoIntereses = montoIntereses;
        this.montoTotal = montoTotal;
        this.idRegistroDetallado = idRegistroDetallado;
        this.idRegistroGeneral = idRegistroGeneral;
        this.fechaPago = fechaPago;
        
        if(tipoIdentificacionPpt != null) {
        	this.tipoIdentificacionPpt = TipoIdentificacionEnum.valueOf(tipoIdentificacionPpt);
        }
        
        if(numeroIdentificacionPpt != null) {
        	this.numeroIdentificacionPpt = numeroIdentificacionPpt;
        }
        
        if(razonSocialPpt != null) {
        	this.razonSocialPpt = razonSocialPpt;
        }
        this.pagoPorSiMismo = Boolean.FALSE;
        if (apgPagadorPorTerceros != null) {
            this.pagoPorSiMismo = !apgPagadorPorTerceros;
        }
    }



    /**
     * @return the fechaRecaudo
     */
    public Date getFechaRecaudo() {
        return fechaRecaudo;
    }
    
    /**
     * @param fechaRecaudo the fechaRecaudo to set
     */
    public void setFechaRecaudo(Date fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }
    
    /**
     * @return the operacionN1
     */
    public Long getOperacionN1() {
        return operacionN1;
    }
    
    /**
     * @param operacionN1 the operacionN1 to set
     */
    public void setOperacionN1(Long operacionN1) {
        this.operacionN1 = operacionN1;
    }
    
    /**
     * @return the operacionN2
     */
    public Long getOperacionN2() {
        return operacionN2;
    }
    
    /**
     * @param operacionN2 the operacionN2 to set
     */
    public void setOperacionN2(Long operacionN2) {
        this.operacionN2 = operacionN2;
    }
    
    /**
     * @return the metodoRecaudo
     */
    public ModalidadRecaudoAporteEnum getMetodoRecaudo() {
        return metodoRecaudo;
    }
    
    /**
     * @param metodoRecaudo the metodoRecaudo to set
     */
    public void setMetodoRecaudo(ModalidadRecaudoAporteEnum metodoRecaudo) {
        this.metodoRecaudo = metodoRecaudo;
    }
    
    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }
    
    /**
     * @param numeroPlanilla the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }
    
    /**
     * @return the tipoPlanilla
     */
    public TipoPlanillaEnum getTipoPlanilla() {
        return tipoPlanilla;
    }
    
    /**
     * @param tipoPlanilla the tipoPlanilla to set
     */
    public void setTipoPlanilla(TipoPlanillaEnum tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }
    
    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }
    
    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    /**
     * @return the tieneModificaciones
     */
    public int getTieneModificaciones() {
        return tieneModificaciones;
    }
    
    /**
     * @param tieneModificaciones the tieneModificaciones to set
     */
    public void setTieneModificaciones(int tieneModificaciones) {
        this.tieneModificaciones = tieneModificaciones;
    }
    
    /**
     * @return the montoAportes
     */
    public BigDecimal getMontoAportes() {
        return montoAportes;
    }
    
    /**
     * @param montoAportes the montoAportes to set
     */
    public void setMontoAportes(BigDecimal montoAportes) {
        this.montoAportes = montoAportes;
    }
    
    /**
     * @return the montoIntereses
     */
    public BigDecimal getMontoIntereses() {
        return montoIntereses;
    }
    
    /**
     * @param montoIntereses the montoIntereses to set
     */
    public void setMontoIntereses(BigDecimal montoIntereses) {
        this.montoIntereses = montoIntereses;
    }
    
    /**
     * @return the montoTotal
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    /**
     * @param montoTotal the montoTotal to set
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }


    /**
     * @return the tipoIdentificacionPpt
     */
    public TipoIdentificacionEnum getTipoIdentificacionPpt() {
        return tipoIdentificacionPpt;
    }


    /**
     * @param tipoIdentificacionPpt the tipoIdentificacionPpt to set
     */
    public void setTipoIdentificacionPpt(TipoIdentificacionEnum tipoIdentificacionPpt) {
        this.tipoIdentificacionPpt = tipoIdentificacionPpt;
    }


    /**
     * @return the numeroIdentificacionPpt
     */
    public String getNumeroIdentificacionPpt() {
        return numeroIdentificacionPpt;
    }


    /**
     * @param numeroIdentificacionPpt the numeroIdentificacionPpt to set
     */
    public void setNumeroIdentificacionPpt(String numeroIdentificacionPpt) {
        this.numeroIdentificacionPpt = numeroIdentificacionPpt;
    }


    /**
     * @return the razonSocialPpt
     */
    public String getRazonSocialPpt() {
        return razonSocialPpt;
    }


    /**
     * @param razonSocialPpt the razonSocialPpt to set
     */
    public void setRazonSocialPpt(String razonSocialPpt) {
        this.razonSocialPpt = razonSocialPpt;
    }


    /**
     * @return the fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }


    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Boolean getPagoPorSiMismo() {
        return this.pagoPorSiMismo;
    }

    public void setPagoPorSiMismo(Boolean pagoPorSiMismo) {
        this.pagoPorSiMismo = pagoPorSiMismo;
    }

}
