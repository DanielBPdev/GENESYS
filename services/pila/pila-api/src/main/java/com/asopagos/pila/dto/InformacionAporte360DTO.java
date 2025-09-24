package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;

public class InformacionAporte360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private EstadoAporteEnum estadoAporte;
    private BigDecimal aporteFinalRegistro;
    private String interesesFinalesAjustados;
    private Short diasCotizados;
    private Short numeroHorasLaboradas;
    private BigDecimal salarioBasico;
    private BigDecimal ingresoBaseCotizacion;
    private BigDecimal tarifa;
    private BigDecimal aporteObligatorio;
    private BigDecimal moraCotizante;
    private String salarioIntegral;
    private BigDecimal valorAporte;
    private String tipoCotizante;
    private EstadoValidacionRegistroAporteEnum dependienteV0;
    private EstadoValidacionRegistroAporteEnum dependienteV1;
    private EstadoValidacionRegistroAporteEnum dependienteV2;
    private EstadoValidacionRegistroAporteEnum dependienteV3;
    private EstadoValidacionRegistroAporteEnum independienteV1;
    private EstadoValidacionRegistroAporteEnum pensionadoV1;
    private Long registroControl;
    
    
    /**
     * 
     */
    public InformacionAporte360DTO() {
    }
    

    /**
     * @param estadoAporte
     * @param aporteFinalRegistro
     * @param diasCotizados
     * @param numeroHorasLaboradas
     * @param salarioBasico
     * @param ingresoBaseCotizacion
     * @param tarifa
     * @param aporteObligatorio
     * @param moraCotizante
     * @param salarioIntegral
     * @param valorAporte
     * @param tipoCotizante
     * @param dependienteV0
     * @param dependienteV1
     * @param dependienteV2
     * @param dependienteV3
     * @param independienteV1
     * @param pensionadoV1
     * @param registroControl
     */
    public InformacionAporte360DTO(EstadoAporteEnum estadoAporte, BigDecimal aporteFinalRegistro, Short diasCotizados,
            Short numeroHorasLaboradas, BigDecimal salarioBasico, BigDecimal ingresoBaseCotizacion, BigDecimal tarifa,
            BigDecimal aporteObligatorio, BigDecimal moraCotizante, String salarioIntegral, BigDecimal valorAporte, String tipoCotizante,
            EstadoValidacionRegistroAporteEnum dependienteV0, EstadoValidacionRegistroAporteEnum dependienteV1,
            EstadoValidacionRegistroAporteEnum dependienteV2, EstadoValidacionRegistroAporteEnum dependienteV3,
            EstadoValidacionRegistroAporteEnum independienteV1, EstadoValidacionRegistroAporteEnum pensionadoV1, Long registroControl) {
        this.estadoAporte = estadoAporte;
        this.aporteFinalRegistro = aporteFinalRegistro;
        this.diasCotizados = diasCotizados;
        this.numeroHorasLaboradas = numeroHorasLaboradas;
        this.salarioBasico = salarioBasico;
        this.ingresoBaseCotizacion = ingresoBaseCotizacion;
        this.tarifa = tarifa;
        this.aporteObligatorio = aporteObligatorio;
        this.moraCotizante = moraCotizante;
        this.salarioIntegral = salarioIntegral;
        this.valorAporte = valorAporte;
        this.tipoCotizante = tipoCotizante;
        this.dependienteV0 = dependienteV0;
        this.dependienteV1 = dependienteV1;
        this.dependienteV2 = dependienteV2;
        this.dependienteV3 = dependienteV3;
        this.independienteV1 = independienteV1;
        this.pensionadoV1 = pensionadoV1;
        this.registroControl = registroControl;
    }


    /**
     * @return the estadoAporte
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }
    /**
     * @param estadoAporte the estadoAporte to set
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }
    /**
     * @return the aporteFinalRegistro
     */
    public BigDecimal getAporteFinalRegistro() {
        return aporteFinalRegistro;
    }
    /**
     * @param aporteFinalRegistro the aporteFinalRegistro to set
     */
    public void setAporteFinalRegistro(BigDecimal aporteFinalRegistro) {
        this.aporteFinalRegistro = aporteFinalRegistro;
    }
    /**
     * @return the interesesFinalesAjustados
     */
    public String getInteresesFinalesAjustados() {
        return interesesFinalesAjustados;
    }
    /**
     * @param interesesFinalesAjustados the interesesFinalesAjustados to set
     */
    public void setInteresesFinalesAjustados(String interesesFinalesAjustados) {
        this.interesesFinalesAjustados = interesesFinalesAjustados;
    }
    /**
     * @return the diasCotizados
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }
    /**
     * @param diasCotizados the diasCotizados to set
     */
    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }
    /**
     * @return the numeroHorasLaboradas
     */
    public Short getNumeroHorasLaboradas() {
        return numeroHorasLaboradas;
    }
    /**
     * @param numeroHorasLaboradas the numeroHorasLaboradas to set
     */
    public void setNumeroHorasLaboradas(Short numeroHorasLaboradas) {
        this.numeroHorasLaboradas = numeroHorasLaboradas;
    }
    /**
     * @return the salarioBasico
     */
    public BigDecimal getSalarioBasico() {
        return salarioBasico;
    }
    /**
     * @param salarioBasico the salarioBasico to set
     */
    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }
    /**
     * @return the ingresoBaseCotizacion
     */
    public BigDecimal getIngresoBaseCotizacion() {
        return ingresoBaseCotizacion;
    }
    /**
     * @param ingresoBaseCotizacion the ingresoBaseCotizacion to set
     */
    public void setIngresoBaseCotizacion(BigDecimal ingresoBaseCotizacion) {
        this.ingresoBaseCotizacion = ingresoBaseCotizacion;
    }
    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }
    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }
    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }
    /**
     * @param aporteObligatorio the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }
    /**
     * @return the moraCotizante
     */
    public BigDecimal getMoraCotizante() {
        return moraCotizante;
    }
    /**
     * @param moraCotizante the moraCotizante to set
     */
    public void setMoraCotizante(BigDecimal moraCotizante) {
        this.moraCotizante = moraCotizante;
    }
    /**
     * @return the salarioIntegral
     */
    public String getSalarioIntegral() {
        return salarioIntegral;
    }
    /**
     * @param salarioIntegral the salarioIntegral to set
     */
    public void setSalarioIntegral(String salarioIntegral) {
        this.salarioIntegral = salarioIntegral;
    }
    /**
     * @return the valorAporte
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }
    /**
     * @param valorAporte the valorAporte to set
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }
    /**
     * @return the tipoCotizante
     */
    public String getTipoCotizante() {
        return tipoCotizante;
    }
    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(String tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }
    /**
     * @return the dependienteV0
     */
    public EstadoValidacionRegistroAporteEnum getDependienteV0() {
        return dependienteV0;
    }
    /**
     * @param dependienteV0 the dependienteV0 to set
     */
    public void setDependienteV0(EstadoValidacionRegistroAporteEnum dependienteV0) {
        this.dependienteV0 = dependienteV0;
    }
    /**
     * @return the dependienteV1
     */
    public EstadoValidacionRegistroAporteEnum getDependienteV1() {
        return dependienteV1;
    }
    /**
     * @param dependienteV1 the dependienteV1 to set
     */
    public void setDependienteV1(EstadoValidacionRegistroAporteEnum dependienteV1) {
        this.dependienteV1 = dependienteV1;
    }
    /**
     * @return the dependienteV2
     */
    public EstadoValidacionRegistroAporteEnum getDependienteV2() {
        return dependienteV2;
    }
    /**
     * @param dependienteV2 the dependienteV2 to set
     */
    public void setDependienteV2(EstadoValidacionRegistroAporteEnum dependienteV2) {
        this.dependienteV2 = dependienteV2;
    }
    /**
     * @return the dependienteV3
     */
    public EstadoValidacionRegistroAporteEnum getDependienteV3() {
        return dependienteV3;
    }
    /**
     * @param dependienteV3 the dependienteV3 to set
     */
    public void setDependienteV3(EstadoValidacionRegistroAporteEnum dependienteV3) {
        this.dependienteV3 = dependienteV3;
    }
    /**
     * @return the independienteV1
     */
    public EstadoValidacionRegistroAporteEnum getIndependienteV1() {
        return independienteV1;
    }
    /**
     * @param independienteV1 the independienteV1 to set
     */
    public void setIndependienteV1(EstadoValidacionRegistroAporteEnum independienteV1) {
        this.independienteV1 = independienteV1;
    }
    /**
     * @return the pensionadoV1
     */
    public EstadoValidacionRegistroAporteEnum getPensionadoV1() {
        return pensionadoV1;
    }
    /**
     * @param pensionadoV1 the pensionadoV1 to set
     */
    public void setPensionadoV1(EstadoValidacionRegistroAporteEnum pensionadoV1) {
        this.pensionadoV1 = pensionadoV1;
    }

    /**
     * @return the registroControl
     */
    public Long getRegistroControl() {
        return registroControl;
    }

    /**
     * @param registroControl the registroControl to set
     */
    public void setRegistroControl(Long registroControl) {
        this.registroControl = registroControl;
    }    
}
