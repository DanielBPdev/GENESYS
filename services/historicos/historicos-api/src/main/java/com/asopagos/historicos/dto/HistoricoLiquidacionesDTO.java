package com.asopagos.historicos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.entidades.transversal.core.EstadisticasGenesys;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

public class HistoricoLiquidacionesDTO implements Serializable {
    private String liquidacionMesUno;
    private Integer liquidacionConDerechoUno;
    private Integer liquidacionSinDerechoUno;

    private String liquidacionMesDos;
    private Integer liquidacionSinDerechoDos;
    private Integer liquidacionConDerechoDos;

    private String liquidacionMesTres;
    private Integer liquidacionConDerechoTres;
    private Integer liquidacionSinDerechoTres;

    private String liquidacionMesCuatro;
    private Integer liquidacionConDerechoCuatro;
    private Integer liquidacionSinDerechoCuatro;

    private String liquidacionMesCinco;
    private Integer liquidacionConDerechoCinco;
    private Integer liquidacionSinDerechoCinco;

    private String liquidacionMesSeis;
    private Integer liquidacionConDerechoSeis;
    private Integer liquidacionSinDerechoSeis;

    public HistoricoLiquidacionesDTO() {
        super();
    }
    public HistoricoLiquidacionesDTO(EstadisticasGenesys estadisticasGenesys) {
        convertToHistoricoLiquidacionesDTO(estadisticasGenesys);
    }

    // Getters and Setters
    public String getLiquidacionMesUno() {
        return liquidacionMesUno;
    }

    public void setLiquidacionMesUno(String liquidacionMesUno) {
        this.liquidacionMesUno = liquidacionMesUno;
    }

    public Integer getLiquidacionConDerechoUno() {
        return liquidacionConDerechoUno;
    }

    public void setLiquidacionConDerechoUno(Integer liquidacionConDerechoUno) {
        this.liquidacionConDerechoUno = liquidacionConDerechoUno;
    }

    public Integer getLiquidacionSinDerechoUno() {
        return liquidacionSinDerechoUno;
    }

    public void setLiquidacionSinDerechoUno(Integer liquidacionSinDerechoUno) {
        this.liquidacionSinDerechoUno = liquidacionSinDerechoUno;
    }

    public String getLiquidacionMesDos() {
        return liquidacionMesDos;
    }

    public void setLiquidacionMesDos(String liquidacionMesDos) {
        this.liquidacionMesDos = liquidacionMesDos;
    }

    public Integer getLiquidacionSinDerechoDos() {
        return liquidacionSinDerechoDos;
    }

    public void setLiquidacionSinDerechoDos(Integer liquidacionSinDerechoDos) {
        this.liquidacionSinDerechoDos = liquidacionSinDerechoDos;
    }

    public Integer getLiquidacionConDerechoDos() {
        return liquidacionConDerechoDos;
    }

    public void setLiquidacionConDerechoDos(Integer liquidacionConDerechoDos) {
        this.liquidacionConDerechoDos = liquidacionConDerechoDos;
    }

    public String getLiquidacionMesTres() {
        return liquidacionMesTres;
    }

    public void setLiquidacionMesTres(String liquidacionMesTres) {
        this.liquidacionMesTres = liquidacionMesTres;
    }

    public Integer getLiquidacionConDerechoTres() {
        return liquidacionConDerechoTres;
    }

    public void setLiquidacionConDerechoTres(Integer liquidacionConDerechoTres) {
        this.liquidacionConDerechoTres = liquidacionConDerechoTres;
    }

    public Integer getLiquidacionSinDerechoTres() {
        return liquidacionSinDerechoTres;
    }

    public void setLiquidacionSinDerechoTres(Integer liquidacionSinDerechoTres) {
        this.liquidacionSinDerechoTres = liquidacionSinDerechoTres;
    }

    public String getLiquidacionMesCuatro() {
        return liquidacionMesCuatro;
    }

    public void setLiquidacionMesCuatro(String liquidacionMesCuatro) {
        this.liquidacionMesCuatro = liquidacionMesCuatro;
    }

    public Integer getLiquidacionConDerechoCuatro() {
        return liquidacionConDerechoCuatro;
    }

    public void setLiquidacionConDerechoCuatro(Integer liquidacionConDerechoCuatro) {
        this.liquidacionConDerechoCuatro = liquidacionConDerechoCuatro;
    }

    public Integer getLiquidacionSinDerechoCuatro() {
        return liquidacionSinDerechoCuatro;
    }

    public void setLiquidacionSinDerechoCuatro(Integer liquidacionSinDerechoCuatro) {
        this.liquidacionSinDerechoCuatro = liquidacionSinDerechoCuatro;
    }

    public String getLiquidacionMesCinco() {
        return this.liquidacionMesCinco;
    }

    public void setLiquidacionMesCinco(String liquidacionMesCinco) {
        this.liquidacionMesCinco = liquidacionMesCinco;
    }

    public Integer getLiquidacionConDerechoCinco() {
        return this.liquidacionConDerechoCinco;
    }

    public void setLiquidacionConDerechoCinco(Integer liquidacionConDerechoCinco) {
        this.liquidacionConDerechoCinco = liquidacionConDerechoCinco;
    }

    public Integer getLiquidacionSinDerechoCinco() {
        return this.liquidacionSinDerechoCinco;
    }

    public void setLiquidacionSinDerechoCinco(Integer liquidacionSinDerechoCinco) {
        this.liquidacionSinDerechoCinco = liquidacionSinDerechoCinco;
    }

    public String getLiquidacionMesSeis() {
        return this.liquidacionMesSeis;
    }

    public void setLiquidacionMesSeis(String liquidacionMesSeis) {
        this.liquidacionMesSeis = liquidacionMesSeis;
    }

    public Integer getLiquidacionConDerechoSeis() {
        return this.liquidacionConDerechoSeis;
    }

    public void setLiquidacionConDerechoSeis(Integer liquidacionConDerechoSeis) {
        this.liquidacionConDerechoSeis = liquidacionConDerechoSeis;
    }

    public Integer getLiquidacionSinDerechoSeis() {
        return this.liquidacionSinDerechoSeis;
    }

    public void setLiquidacionSinDerechoSeis(Integer liquidacionSinDerechoSeis) {
        this.liquidacionSinDerechoSeis = liquidacionSinDerechoSeis;
    }

    public void convertToHistoricoLiquidacionesDTO(EstadisticasGenesys estadisticasGenesys) {
        this.setLiquidacionMesUno(estadisticasGenesys.getLiquidacionMesUno());
        this.setLiquidacionConDerechoUno(estadisticasGenesys.getLiquidacionConDerechoUno());
        this.setLiquidacionSinDerechoUno(estadisticasGenesys.getLiquidacionSinDerechoUno());
        this.setLiquidacionMesDos(estadisticasGenesys.getLiquidacionMesDos());
        this.setLiquidacionConDerechoDos(estadisticasGenesys.getLiquidacionConDerechoDos());
        this.setLiquidacionSinDerechoDos(estadisticasGenesys.getLiquidacionSinDerechoDos());
        this.setLiquidacionMesTres(estadisticasGenesys.getLiquidacionMesTres());
        this.setLiquidacionConDerechoTres(estadisticasGenesys.getLiquidacionConDerechoTres());
        this.setLiquidacionSinDerechoTres(estadisticasGenesys.getLiquidacionSinDerechoTres());
        this.setLiquidacionMesCuatro(estadisticasGenesys.getLiquidacionMesCuatro());
        this.setLiquidacionConDerechoCuatro(estadisticasGenesys.getLiquidacionConDerechoCuatro());
        this.setLiquidacionSinDerechoCuatro(estadisticasGenesys.getLiquidacionSinDerechoCuatro());
        this.setLiquidacionMesCinco(estadisticasGenesys.getLiquidacionMesCinco());
        this.setLiquidacionConDerechoCinco(estadisticasGenesys.getLiquidacionConDerechoCuatro());
        this.setLiquidacionSinDerechoCinco(estadisticasGenesys.getLiquidacionSinDerechoCuatro());
        this.setLiquidacionMesSeis(estadisticasGenesys.getLiquidacionMesSeis());
        this.setLiquidacionConDerechoSeis(estadisticasGenesys.getLiquidacionConDerechoSeis());
        this.setLiquidacionSinDerechoSeis(estadisticasGenesys.getLiquidacionSinDerechoSeis());
    }

    @Override
    public String toString() {
        return "HistoricoLiquidacionesDTO{" +
                "liquidacionMesUno='" + liquidacionMesUno + '\'' +
                ", liquidacionConDerechoUno=" + liquidacionConDerechoUno +
                ", liquidacionSinDerechoUno=" + liquidacionSinDerechoUno +
                ", liquidacionMesDos='" + liquidacionMesDos + '\'' +
                ", liquidacionSinDerechoDos=" + liquidacionSinDerechoDos +
                ", liquidacionConDerechoDos=" + liquidacionConDerechoDos +
                ", liquidacionMesTres='" + liquidacionMesTres + '\'' +
                ", liquidacionConDerechoTres=" + liquidacionConDerechoTres +
                ", liquidacionSinDerechoTres=" + liquidacionSinDerechoTres +
                ", liquidacionMesCuatro='" + liquidacionMesCuatro + '\'' +
                ", liquidacionConDerechoCuatro=" + liquidacionConDerechoCuatro +
                ", liquidacionSinDerechoCuatro=" + liquidacionSinDerechoCuatro +
                ", liquidacionMesCinco='" + liquidacionMesCinco + '\'' +
                ", liquidacionConDerechoCinco=" + liquidacionConDerechoCinco +
                ", liquidacionSinDerechoCinco=" + liquidacionSinDerechoCinco +
                ", liquidacionMesSeis='" + liquidacionMesSeis + '\'' +
                ", liquidacionConDerechoSeis=" + liquidacionConDerechoSeis +
                ", liquidacionSinDerechoSeis=" + liquidacionSinDerechoSeis +
                '}';
    }
}