package com.asopagos.dto.cartera;

import java.io.Serializable;

/**
 * DTO que contiene la línea de cobro con sus detalles de cartera de mora
 * @author Silvio Alexander López Herrera <silopez@heinsohn.com.co>
 * @created 21-feb-2017 3:31:37 p.m.
 */
public class GestionCarteraMoraDTO implements Serializable {
    
    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = -2923455110379172556L;

    /**
     * Linea de cobro 1
     */
    private GestionLineaCobroDTO lineaCobro1;
    
    /**
     * Linea de cobro 2
     */
    private GestionLineaCobroDTO lineaCobro2;
    
    /**
     * Linea de cobro 3
     */
    private GestionLineaCobroDTO lineaCobro3;
    
    /**
     * Linea de cobro 4
     */
    private GestionLineaCobroDTO lineaCobro4;
    
    /**
     * Linea de cobro 5
     */
    private GestionLineaCobroDTO lineaCobro5;
    
    /**
     * Deuda antigua C6
     */
    private GestionDetalleCarteraMoraDTO deudaAntiguaC6;
    
    /**
     * Deuda antigua C7
     */
    private GestionDetalleCarteraMoraDTO deudaAntiguaC7;
    
    /**
     * Deuda antigua C8
     */
    private GestionDetalleCarteraMoraDTO deudaAntiguaC8;

    /**
     * Método que retorna el valor de lineaCobro1.
     * @return valor de lineaCobro1.
     */
    public GestionLineaCobroDTO getLineaCobro1() {
        return lineaCobro1;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro1.
     * @param valor para modificar lineaCobro1.
     */
    public void setLineaCobro1(GestionLineaCobroDTO lineaCobro1) {
        this.lineaCobro1 = lineaCobro1;
    }

    /**
     * Método que retorna el valor de lineaCobro2.
     * @return valor de lineaCobro2.
     */
    public GestionLineaCobroDTO getLineaCobro2() {
        return lineaCobro2;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro2.
     * @param valor para modificar lineaCobro2.
     */
    public void setLineaCobro2(GestionLineaCobroDTO lineaCobro2) {
        this.lineaCobro2 = lineaCobro2;
    }

    /**
     * Método que retorna el valor de lineaCobro3.
     * @return valor de lineaCobro3.
     */
    public GestionLineaCobroDTO getLineaCobro3() {
        return lineaCobro3;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro3.
     * @param valor para modificar lineaCobro3.
     */
    public void setLineaCobro3(GestionLineaCobroDTO lineaCobro3) {
        this.lineaCobro3 = lineaCobro3;
    }

    /**
     * Método que retorna el valor de lineaCobro4.
     * @return valor de lineaCobro4.
     */
    public GestionLineaCobroDTO getLineaCobro4() {
        return lineaCobro4;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro4.
     * @param valor para modificar lineaCobro4.
     */
    public void setLineaCobro4(GestionLineaCobroDTO lineaCobro4) {
        this.lineaCobro4 = lineaCobro4;
    }

    /**
     * Método que retorna el valor de lineaCobro5.
     * @return valor de lineaCobro5.
     */
    public GestionLineaCobroDTO getLineaCobro5() {
        return lineaCobro5;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro5.
     * @param valor para modificar lineaCobro5.
     */
    public void setLineaCobro5(GestionLineaCobroDTO lineaCobro5) {
        this.lineaCobro5 = lineaCobro5;
    }

    /**
     * Método que retorna el valor de deudaAntiguaC6.
     * @return valor de deudaAntiguaC6.
     */
    public GestionDetalleCarteraMoraDTO getDeudaAntiguaC6() {
        return deudaAntiguaC6;
    }

    /**
     * Método encargado de modificar el valor de deudaAntiguaC6.
     * @param valor para modificar deudaAntiguaC6.
     */
    public void setDeudaAntiguaC6(GestionDetalleCarteraMoraDTO deudaAntiguaC6) {
        this.deudaAntiguaC6 = deudaAntiguaC6;
    }

    /**
     * Método que retorna el valor de deudaAntiguaC7.
     * @return valor de deudaAntiguaC7.
     */
    public GestionDetalleCarteraMoraDTO getDeudaAntiguaC7() {
        return deudaAntiguaC7;
    }

    /**
     * Método encargado de modificar el valor de deudaAntiguaC7.
     * @param valor para modificar deudaAntiguaC7.
     */
    public void setDeudaAntiguaC7(GestionDetalleCarteraMoraDTO deudaAntiguaC7) {
        this.deudaAntiguaC7 = deudaAntiguaC7;
    }

    /**
     * Método que retorna el valor de deudaAntiguaC8.
     * @return valor de deudaAntiguaC8.
     */
    public GestionDetalleCarteraMoraDTO getDeudaAntiguaC8() {
        return deudaAntiguaC8;
    }

    /**
     * Método encargado de modificar el valor de deudaAntiguaC8.
     * @param valor para modificar deudaAntiguaC8.
     */
    public void setDeudaAntiguaC8(GestionDetalleCarteraMoraDTO deudaAntiguaC8) {
        this.deudaAntiguaC8 = deudaAntiguaC8;
    }
    
}
