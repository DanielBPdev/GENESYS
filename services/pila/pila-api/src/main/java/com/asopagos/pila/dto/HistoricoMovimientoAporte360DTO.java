package com.asopagos.pila.dto;

import java.io.Serializable;

public class HistoricoMovimientoAporte360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String numeroOperacion;
    private String tipoMovimiento;
    private String tipoIdAportante;
    private String numeroIdAportante;
    private String periodo;
    private String fechaRegistro;
    private String numeroPlanilla;
    private String numeroPlanillaCorregida;
    private String numeroOperacion2;
    private String tipoMovimiento2;
    private String tipoIdAportante2;
    private String periodo2;
    
    
    /**
     * 
     */
    public HistoricoMovimientoAporte360DTO() {
    }


    /**
     * @param numeroOperacion
     * @param tipoMovimiento
     * @param tipoIdAportante
     * @param numeroIdAportante
     * @param periodo
     * @param fechaRegistro
     * @param numeroPlanilla
     * @param numeroPlanillaCorregida
     * @param numeroOperacion2
     * @param tipoMovimiento2
     * @param tipoIdAportante2
     * @param periodo2
     */
    public HistoricoMovimientoAporte360DTO(String numeroOperacion, String tipoMovimiento, String tipoIdAportante, String numeroIdAportante,
            String periodo, String fechaRegistro, String numeroPlanilla, String numeroPlanillaCorregida, String numeroOperacion2,
            String tipoMovimiento2, String tipoIdAportante2, String periodo2) {
        this.numeroOperacion = numeroOperacion;
        this.tipoMovimiento = tipoMovimiento;
        this.tipoIdAportante = tipoIdAportante;
        this.numeroIdAportante = numeroIdAportante;
        this.periodo = periodo;
        this.fechaRegistro = fechaRegistro;
        this.numeroPlanilla = numeroPlanilla;
        this.numeroPlanillaCorregida = numeroPlanillaCorregida;
        this.numeroOperacion2 = numeroOperacion2;
        this.tipoMovimiento2 = tipoMovimiento2;
        this.tipoIdAportante2 = tipoIdAportante2;
        this.periodo2 = periodo2;
    }


    /**
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }


    /**
     * @param numeroOperacion the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }


    /**
     * @return the tipoMovimiento
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }


    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }


    /**
     * @return the tipoIdAportante
     */
    public String getTipoIdAportante() {
        return tipoIdAportante;
    }


    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(String tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }


    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }


    /**
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
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
     * @return the fechaRegistro
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
     * @return the numeroPlanillaCorregida
     */
    public String getNumeroPlanillaCorregida() {
        return numeroPlanillaCorregida;
    }


    /**
     * @param numeroPlanillaCorregida the numeroPlanillaCorregida to set
     */
    public void setNumeroPlanillaCorregida(String numeroPlanillaCorregida) {
        this.numeroPlanillaCorregida = numeroPlanillaCorregida;
    }


    /**
     * @return the numeroOperacion2
     */
    public String getNumeroOperacion2() {
        return numeroOperacion2;
    }


    /**
     * @param numeroOperacion2 the numeroOperacion2 to set
     */
    public void setNumeroOperacion2(String numeroOperacion2) {
        this.numeroOperacion2 = numeroOperacion2;
    }


    /**
     * @return the tipoMovimiento2
     */
    public String getTipoMovimiento2() {
        return tipoMovimiento2;
    }


    /**
     * @param tipoMovimiento2 the tipoMovimiento2 to set
     */
    public void setTipoMovimiento2(String tipoMovimiento2) {
        this.tipoMovimiento2 = tipoMovimiento2;
    }


    /**
     * @return the tipoIdAportante2
     */
    public String getTipoIdAportante2() {
        return tipoIdAportante2;
    }


    /**
     * @param tipoIdAportante2 the tipoIdAportante2 to set
     */
    public void setTipoIdAportante2(String tipoIdAportante2) {
        this.tipoIdAportante2 = tipoIdAportante2;
    }


    /**
     * @return the periodo2
     */
    public String getPeriodo2() {
        return periodo2;
    }


    /**
     * @param periodo2 the periodo2 to set
     */
    public void setPeriodo2(String periodo2) {
        this.periodo2 = periodo2;
    }
}
