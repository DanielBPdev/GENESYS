package com.asopagos.historicos.dto;

import com.asopagos.entidades.transversal.core.EstadisticasGenesys;

import java.io.Serializable;


public class HistoricoAportesDTO implements Serializable {
    private String aporteMesUno;
    private Integer aportesRelacionadosUno;
    private Integer aportesRegistradosUno;

    private String aporteMesDos;
    private Integer aportesRelacionadosDos;
    private Integer aportesRegistradosDos;

    private String aporteMesTres;
    private Integer aportesRelacionadosTres;
    private Integer aportesRegistradosTres;

    private String aporteMesCuatro;
    private Integer aportesRelacionadosCuatro;
    private Integer aportesRegistradosCuatro;

    private String aporteMesCinco;
    private Integer aportesRelacionadosCinco;
    private Integer aportesRegistradosCinco;

    private String aporteMesSeis;
    private Integer aportesRelacionadosSeis;
    private Integer aportesRegistradosSeis;

    public HistoricoAportesDTO() {
        super();
    }
    public HistoricoAportesDTO(EstadisticasGenesys estadisticasGenesys) {
        convertToHistoricoAportesDTO(estadisticasGenesys);
    }

    // Getters and Setters
    public String getAporteMesUno() {
        return aporteMesUno;
    }

    public void setAporteMesUno(String aporteMesUno) {
        this.aporteMesUno = aporteMesUno;
    }

    public Integer getAportesRelacionadosUno() {
        return aportesRelacionadosUno;
    }

    public void setAportesRelacionadosUno(Integer aportesRelacionadosUno) {
        this.aportesRelacionadosUno = aportesRelacionadosUno;
    }

    public Integer getAportesRegistradosUno() {
        return aportesRegistradosUno;
    }

    public void setAportesRegistradosUno(Integer aportesRegistradosUno) {
        this.aportesRegistradosUno = aportesRegistradosUno;
    }

    public String getAporteMesDos() {
        return aporteMesDos;
    }

    public void setAporteMesDos(String aporteMesDos) {
        this.aporteMesDos = aporteMesDos;
    }

    public Integer getAportesRelacionadosDos() {
        return aportesRelacionadosDos;
    }

    public void setAportesRelacionadosDos(Integer aportesRelacionadosDos) {
        this.aportesRelacionadosDos = aportesRelacionadosDos;
    }

    public Integer getAportesRegistradosDos() {
        return aportesRegistradosDos;
    }

    public void setAportesRegistradosDos(Integer aportesRegistradosDos) {
        this.aportesRegistradosDos = aportesRegistradosDos;
    }

    public String getAporteMesTres() {
        return aporteMesTres;
    }

    public void setAporteMesTres(String aporteMesTres) {
        this.aporteMesTres = aporteMesTres;
    }

    public Integer getAportesRelacionadosTres() {
        return aportesRelacionadosTres;
    }

    public void setAportesRelacionadosTres(Integer aportesRelacionadosTres) {
        this.aportesRelacionadosTres = aportesRelacionadosTres;
    }

    public Integer getAportesRegistradosTres() {
        return aportesRegistradosTres;
    }

    public void setAportesRegistradosTres(Integer aportesRegistradosTres) {
        this.aportesRegistradosTres = aportesRegistradosTres;
    }

    public String getAporteMesCuatro() {
        return aporteMesCuatro;
    }

    public void setAporteMesCuatro(String aporteMesCuatro) {
        this.aporteMesCuatro = aporteMesCuatro;
    }

    public Integer getAportesRelacionadosCuatro() {
        return aportesRelacionadosCuatro;
    }

    public void setAportesRelacionadosCuatro(Integer aportesRelacionadosCuatro) {
        this.aportesRelacionadosCuatro = aportesRelacionadosCuatro;
    }

    public Integer getAportesRegistradosCuatro() {
        return aportesRegistradosCuatro;
    }

    public void setAportesRegistradosCuatro(Integer aportesRegistradosCuatro) {
        this.aportesRegistradosCuatro = aportesRegistradosCuatro;
    }

    public String getAporteMesCinco() {
        return aporteMesCinco;
    }

    public void setAporteMesCinco(String aporteMesCinco) {
        this.aporteMesCinco = aporteMesCinco;
    }

    public Integer getAportesRelacionadosCinco() {
        return aportesRelacionadosCinco;
    }

    public void setAportesRelacionadosCinco(Integer aportesRelacionadosCinco) {
        this.aportesRelacionadosCinco = aportesRelacionadosCinco;
    }

    public Integer getAportesRegistradosCinco() {
        return aportesRegistradosCinco;
    }

    public void setAportesRegistradosCinco(Integer aportesRegistradosCinco) {
        this.aportesRegistradosCinco = aportesRegistradosCinco;
    }

    public String getAporteMesSeis() {
        return this.aporteMesSeis;
    }

    public void setAporteMesSeis(String aporteMesSeis) {
        this.aporteMesSeis = aporteMesSeis;
    }

    public Integer getAportesRelacionadosSeis() {
        return this.aportesRelacionadosSeis;
    }

    public void setAportesRelacionadosSeis(Integer aportesRelacionadosSeis) {
        this.aportesRelacionadosSeis = aportesRelacionadosSeis;
    }

    public Integer getAportesRegistradosSeis() {
        return this.aportesRegistradosSeis;
    }

    public void setAportesRegistradosSeis(Integer aportesRegistradosSeis) {
        this.aportesRegistradosSeis = aportesRegistradosSeis;
    }

    public void convertToHistoricoAportesDTO(EstadisticasGenesys estadisticasGenesys){
        this.setAporteMesUno(estadisticasGenesys.getAporteMesUno());
        this.setAportesRelacionadosUno(estadisticasGenesys.getAportesRelacionadosUno());
        this.setAportesRegistradosUno(estadisticasGenesys.getAportesRegistradosUno());
        this.setAporteMesDos(estadisticasGenesys.getAporteMesDos());
        this.setAportesRelacionadosDos(estadisticasGenesys.getAportesRelacionadosDos());
        this.setAportesRegistradosDos(estadisticasGenesys.getAportesRegistradosDos());
        this.setAporteMesTres(estadisticasGenesys.getAporteMesTres());
        this.setAportesRelacionadosTres(estadisticasGenesys.getAportesRelacionadosTres());
        this.setAportesRegistradosTres(estadisticasGenesys.getAportesRegistradosTres());
        this.setAporteMesCuatro(estadisticasGenesys.getAporteMesCuatro());
        this.setAportesRelacionadosCuatro(estadisticasGenesys.getAportesRelacionadosCuatro());
        this.setAportesRegistradosCuatro(estadisticasGenesys.getAportesRegistradosCuatro());
        this.setAporteMesCinco(estadisticasGenesys.getAporteMesCinco());
        this.setAportesRelacionadosCinco(estadisticasGenesys.getAportesRelacionadosCinco());
        this.setAportesRegistradosCinco(estadisticasGenesys.getAportesRegistradosCinco());
        this.setAporteMesSeis(estadisticasGenesys.getAporteMesSeis());
        this.setAportesRelacionadosSeis(estadisticasGenesys.getAportesRelacionadosSeis());
        this.setAportesRegistradosSeis(estadisticasGenesys.getAportesRegistradosSeis());
    }

}