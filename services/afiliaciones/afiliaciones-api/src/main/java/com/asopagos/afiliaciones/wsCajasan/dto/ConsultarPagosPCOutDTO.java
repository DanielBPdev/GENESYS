package com.asopagos.afiliaciones.wsCajasan.dto;

import java.io.Serializable;

public class ConsultarPagosPCOutDTO {

    private Integer cuotas;        // Número de cuotas ingresadas en la tarjeta
    private String medioPago;      // Medio de pago (p.ej., TARJETA MULTISERVICIOS)
    private String personaCargo;   // Nombre de la persona a cargo
    private String tercero;        // Nombre del trabajador que recibe la cuota
    private Integer vigencia;      // Año y mes (yyyymm)
    private Double vrCuota;        // Valor de la cuota


    public ConsultarPagosPCOutDTO() {
        // Constructor por defecto
    }

    public ConsultarPagosPCOutDTO(Integer cuotas, String medioPago, String personaCargo, String tercero, Integer vigencia, Double vrCuota) {
        this.cuotas = cuotas;
        this.medioPago = medioPago;
        this.personaCargo = personaCargo;
        this.tercero = tercero;
        this.vigencia = vigencia;
        this.vrCuota = vrCuota;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getPersonaCargo() {
        return personaCargo;
    }

    public void setPersonaCargo(String personaCargo) {
        this.personaCargo = personaCargo;
    }

    public String getTercero() {
        return tercero;
    }

    public void setTercero(String tercero) {
        this.tercero = tercero;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Double getVrCuota() {
        return vrCuota;
    }

    public void setVrCuota(Double vrCuota) {
        this.vrCuota = vrCuota;
    }
}
