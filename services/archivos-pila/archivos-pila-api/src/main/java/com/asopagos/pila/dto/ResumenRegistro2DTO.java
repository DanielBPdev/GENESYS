package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;

/**
 * <b>Descripcion:</b>DTO que simplifica el contenido de una línea de resgistro 2 de Archivo I
 * 
 * CONTROL DE CAMBIO 219141 - Anexo 2.1.1<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class ResumenRegistro2DTO implements Serializable{
    private static final long serialVersionUID = 1L;

    /** Valor de campo 29 registro tipo 2 de archivo tipo I - Correcciones */
    private String correcciones;
    
    /** Número de la línea en el archivo */
    private Long numeroLinea;
    
    /** Valor del salario / mesada pensional */
    private BigDecimal valorSalario;
    
    /** Valor del IBC */
    private BigDecimal valorIBC;
    
    /** Días cotizados */
    private Integer diasCotizados;
    
    /** Tipo de cotizante */
    private TipoCotizanteEnum tipoCotizante;
    
    /** Indicador de presencia de nov ING */
    private Boolean presentaING = false;
      
    /** Indicador de presencia de nov RET */
    private Boolean presentaRET = false;
    
    /** Indicador de presencia de nov SLN */
    private Boolean presentaSLN = false;

    /** Indicador de presencia de nov IGE */
    private Boolean presentaIGE = false;

    /** Indicador de presencia de nov LMA */
    private Boolean presentaLMA = false;

    /** Indicador de presencia de nov VAC-LR */
    private Boolean presentaVAC_LR = false;

    /** Indicador de presencia de nov VSP */
    private Boolean presentaVSP = false;

    /** Indicador de presencia de nov VST */
    private Boolean presentaVST = false;

    /** Indicador de presencia de nov IRL */
    private Boolean presentaIRL = false;

    /**
     * @return the correcciones
     */
    public String getCorrecciones() {
        return correcciones;
    }

    /**
     * @param correcciones the correcciones to set
     */
    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    /**
     * @return the numeroLinea
     */
    public Long getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * @param numeroLinea the numeroLinea to set
     */
    public void setNumeroLinea(Long numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    /**
     * @return the presentaING
     */
    public Boolean getPresentaING() {
        return presentaING;
    }

    /**
     * @param presentaING the presentaING to set
     */
    public void setPresentaING(Boolean presentaING) {
        this.presentaING = presentaING;
    }

    /**
     * @return the presentaRET
     */
    public Boolean getPresentaRET() {
        return presentaRET;
    }

    /**
     * @param presentaRET the presentaRET to set
     */
    public void setPresentaRET(Boolean presentaRET) {
        this.presentaRET = presentaRET;
    }

    /**
     * @return the valorIBC
     */
    public BigDecimal getValorIBC() {
        return valorIBC;
    }

    /**
     * @param valorIBC the valorIBC to set
     */
    public void setValorIBC(BigDecimal valorIBC) {
        this.valorIBC = valorIBC;
    }

    /**
     * @return the diasCotizados
     */
    public Integer getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * @param diasCotizados the diasCotizados to set
     */
    public void setDiasCotizados(Integer diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    /**
     * @return the presentaSLN
     */
    public Boolean getPresentaSLN() {
        return presentaSLN;
    }

    /**
     * @param presentaSLN the presentaSLN to set
     */
    public void setPresentaSLN(Boolean presentaSLN) {
        this.presentaSLN = presentaSLN;
    }

    /**
     * @return the presentaIGE
     */
    public Boolean getPresentaIGE() {
        return presentaIGE;
    }

    /**
     * @param presentaIGE the presentaIGE to set
     */
    public void setPresentaIGE(Boolean presentaIGE) {
        this.presentaIGE = presentaIGE;
    }

    /**
     * @return the presentaLMA
     */
    public Boolean getPresentaLMA() {
        return presentaLMA;
    }

    /**
     * @param presentaLMA the presentaLMA to set
     */
    public void setPresentaLMA(Boolean presentaLMA) {
        this.presentaLMA = presentaLMA;
    }

    /**
     * @return the presentaVAC_LR
     */
    public Boolean getPresentaVAC_LR() {
        return presentaVAC_LR;
    }

    /**
     * @param presentaVAC_LR the presentaVAC_LR to set
     */
    public void setPresentaVAC_LR(Boolean presentaVAC_LR) {
        this.presentaVAC_LR = presentaVAC_LR;
    }

    /**
     * @return the presentaVSP
     */
    public Boolean getPresentaVSP() {
        return presentaVSP;
    }

    /**
     * @param presentaVSP the presentaVSP to set
     */
    public void setPresentaVSP(Boolean presentaVSP) {
        this.presentaVSP = presentaVSP;
    }

    /**
     * @return the presentaVST
     */
    public Boolean getPresentaVST() {
        return presentaVST;
    }

    /**
     * @param presentaVST the presentaVST to set
     */
    public void setPresentaVST(Boolean presentaVST) {
        this.presentaVST = presentaVST;
    }

    /**
     * @return the presentaIRL
     */
    public Boolean getPresentaIRL() {
        return presentaIRL;
    }

    /**
     * @param presentaIRL the presentaIRL to set
     */
    public void setPresentaIRL(Boolean presentaIRL) {
        this.presentaIRL = presentaIRL;
    }

    /**
     * @return the valorSalario
     */
    public BigDecimal getValorSalario() {
        return valorSalario;
    }

    /**
     * @param valorSalario the valorSalario to set
     */
    public void setValorSalario(BigDecimal valorSalario) {
        this.valorSalario = valorSalario;
    }

    /**
     * @return the tipoCotizante
     */
    public TipoCotizanteEnum getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }
}
