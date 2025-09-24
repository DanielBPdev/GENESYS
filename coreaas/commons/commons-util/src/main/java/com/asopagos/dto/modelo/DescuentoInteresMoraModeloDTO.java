package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import com.asopagos.entidades.ccf.aportes.DescuentoInteresMora;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;

/**
 * <b>Descripcion:</b> DTO que representa la entidad DescuentoInteresMora <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class DescuentoInteresMoraModeloDTO implements Serializable {
    private static final long serialVersionUID = 283597090261888242L;

    /**
     * Código identificador de llave primaria de la entrada de parametrización de condición de descuento en mora
     * */
    private Long id;
    
    /**
     * Perfil de lectura del archivo que se toma para descuento
     * */
    private PerfilLecturaPilaEnum perfilLecturaPila;

    /**
     * Código indicador de la UGPP (Archivo I-IR, Registro tipo 4 - Campo 2)
     * */
    private Short indicadorUGPP;

    /**
     * Fecha inicial para el rango de pago para aplicación de descuento
     * */
    private Long fechaPagoInicial;

    /**
     * Fecha final para el rango de pago para aplicación de descuento
     * */
    private Long fechaPagoFinal;

    /**
     * Período inicial para el rando de aporte para aplciación del descuento
     * */
    private String periodoPagoInicial;

    /**
     * Período final para el rando de aporte para aplciación del descuento
     * */
    private String periodoPagoFinal;

    /**
     * Porcentaje de descuento a aplicar
     * */
    private BigDecimal porcentajeDescuento;

    /**
     * Códigos de tipo de cotizante que son exluidos de la regla (separados por coma)
     * */
    private String exclusionTipoCotizante;
    
    /**
     * Lista de los tipos de cotizante excluidos a manera de HashSet
     * */
    private Set<String> tiposCotizanteExcluidos;
    
    /**
     * Método para la conversión a de Entity a DTO
     * */
    public void convertToDTO(DescuentoInteresMora descuentoInteres){
        this.id = descuentoInteres.getId();
        this.perfilLecturaPila = descuentoInteres.getPerfilLecturaPila();
        this.indicadorUGPP = descuentoInteres.getIndicadorUGPP();
        if(descuentoInteres.getFechaPagoInicial() != null){
            this.fechaPagoInicial = descuentoInteres.getFechaPagoInicial().getTime();
        }
        if(descuentoInteres.getFechaPagoFinal() != null){
            this.fechaPagoFinal = descuentoInteres.getFechaPagoFinal().getTime();
        }
        this.periodoPagoInicial = descuentoInteres.getPeriodoPagoInicial();
        this.periodoPagoFinal = descuentoInteres.getPeriodoPagoFinal();
        this.porcentajeDescuento = descuentoInteres.getPorcentajeDescuento();
        this.exclusionTipoCotizante = descuentoInteres.getExclusionTipoCotizante();
        
        if(this.tiposCotizanteExcluidos != null){
            this.tiposCotizanteExcluidos = Arrays.stream(this.exclusionTipoCotizante.split(",")).collect(Collectors.toSet());
        }
    }
    
    /**
     * Método para la conversión de DTO a Entity
     * */
    public DescuentoInteresMora convertToEntity(){
        DescuentoInteresMora descuentoInteres = new DescuentoInteresMora();
        
        descuentoInteres.setId(this.getId());
        descuentoInteres.setPerfilLecturaPila(this.getPerfilLecturaPila());
        descuentoInteres.setIndicadorUGPP(this.getIndicadorUGPP());
        if(this.getFechaPagoInicial() != null){
            descuentoInteres.setFechaPagoInicial(new Date(this.getFechaPagoInicial()));
        }
        if(this.getFechaPagoFinal() != null){
            descuentoInteres.setFechaPagoFinal(new Date(this.getFechaPagoFinal()));
        }
        descuentoInteres.setPeriodoPagoInicial(this.getPeriodoPagoInicial());
        descuentoInteres.setPeriodoPagoFinal(this.getPeriodoPagoFinal());
        descuentoInteres.setPorcentajeDescuento(this.getPorcentajeDescuento());
        descuentoInteres.setExclusionTipoCotizante(this.getExclusionTipoCotizante());
        
        return descuentoInteres;
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
     * @return the indicadorUGPP
     */
    public Short getIndicadorUGPP() {
        return indicadorUGPP;
    }

    /**
     * @param indicadorUGPP the indicadorUGPP to set
     */
    public void setIndicadorUGPP(Short indicadorUGPP) {
        this.indicadorUGPP = indicadorUGPP;
    }

    /**
     * @return the fechaPagoInicial
     */
    public Long getFechaPagoInicial() {
        return fechaPagoInicial;
    }

    /**
     * @param fechaPagoInicial the fechaPagoInicial to set
     */
    public void setFechaPagoInicial(Long fechaPagoInicial) {
        this.fechaPagoInicial = fechaPagoInicial;
    }

    /**
     * @return the fechaPagoFinal
     */
    public Long getFechaPagoFinal() {
        return fechaPagoFinal;
    }

    /**
     * @param fechaPagoFinal the fechaPagoFinal to set
     */
    public void setFechaPagoFinal(Long fechaPagoFinal) {
        this.fechaPagoFinal = fechaPagoFinal;
    }

    /**
     * @return the periodoPagoInicial
     */
    public String getPeriodoPagoInicial() {
        return periodoPagoInicial;
    }

    /**
     * @param periodoPagoInicial the periodoPagoInicial to set
     */
    public void setPeriodoPagoInicial(String periodoPagoInicial) {
        this.periodoPagoInicial = periodoPagoInicial;
    }

    /**
     * @return the periodoPagoFinal
     */
    public String getPeriodoPagoFinal() {
        return periodoPagoFinal;
    }

    /**
     * @param periodoPagoFinal the periodoPagoFinal to set
     */
    public void setPeriodoPagoFinal(String periodoPagoFinal) {
        this.periodoPagoFinal = periodoPagoFinal;
    }

    /**
     * @return the porcentajeDescuento
     */
    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    /**
     * @param porcentajeDescuento the porcentajeDescuento to set
     */
    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * @return the exclusionTipoCotizante
     */
    public String getExclusionTipoCotizante() {
        return exclusionTipoCotizante;
    }

    /**
     * @param exclusionTipoCotizante the exclusionTipoCotizante to set
     */
    public void setExclusionTipoCotizante(String exclusionTipoCotizante) {
        this.exclusionTipoCotizante = exclusionTipoCotizante;
    }

    /**
     * @return the perfilLecturaPila
     */
    public PerfilLecturaPilaEnum getPerfilLecturaPila() {
        return perfilLecturaPila;
    }

    /**
     * @param perfilLecturaPila the perfilLecturaPila to set
     */
    public void setPerfilLecturaPila(PerfilLecturaPilaEnum perfilLecturaPila) {
        this.perfilLecturaPila = perfilLecturaPila;
    }

    /**
     * @return the tiposCotizanteExcluidos
     */
    public Set<String> getTiposCotizanteExcluidos() {
        return tiposCotizanteExcluidos;
    }

    /**
     * @param tiposCotizanteExcluidos the tiposCotizanteExcluidos to set
     */
    public void setTiposCotizanteExcluidos(Set<String> tiposCotizanteExcluidos) {
        this.tiposCotizanteExcluidos = tiposCotizanteExcluidos;
    }
}
