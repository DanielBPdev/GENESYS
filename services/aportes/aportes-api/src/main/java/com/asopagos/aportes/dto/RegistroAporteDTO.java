package com.asopagos.aportes.dto;


import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;


/**
 * Representa un registro para generar las tablas con la información de los aportes
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */


public class RegistroAporteDTO {


    /**
     * Valor del Monto registrado para dependientes
     */
    private BigDecimal montoRegistradoDependiente;


    /**
     * Valor del Interes registrado para dependientes
     */
    private BigDecimal interesRegistradoDependiente;


    /**
     * Valor total obtenido por la suma del monto e interes registrado para dependientes
     */
    private BigDecimal totalRegistradoDependiente;


    /**
     * Valor del Monto relacionado para dependientes
     */
    private BigDecimal montoRelacionadoDependiente;


    /**
     * Valor del Interes relacionado para dependientes
     */
    private BigDecimal interesRelacionadoDependiente;


    /**
     * Valor total obtenido por la suma del monto e interes relacionado para dependientes
     */
    private BigDecimal totalRelacionadoDependiente;


    /**
     * Valor del Monto registrado para independientes
     */
    private BigDecimal montoRegistradoIndependiente;


    /**
     * Valor del Interes registrado para independientes
     */
    private BigDecimal interesRegistradoIndependiente;


    /**
     * Valor total obtenido por la suma del monto e interes registrado para independientes
     */
    private BigDecimal totalRegistradoIndependiente;


    /**
     * Valor del Monto relacionado para independientes
     */
    private BigDecimal montoRelacionadoIndependiente;


    /**
     * Valor del Interes relacionado para independientes
     */
    private BigDecimal interesRelacionadoIndependiente;


    /**
     * Valor total obtenido por la suma del monto e interes relacionado para independientes
     */
    private BigDecimal totalRelacionadoIndependiente;    


    /**
     * Valor del Monto registrado para Pensionado
     */
    private BigDecimal montoRegistradoPensionado;


    /**
     * Valor del Interes registrado para Pensionado
     */
    private BigDecimal interesRegistradoPensionado;


    /**
     * Valor total obtenido por la suma del monto e interes registrado para Pensionado
     */
    private BigDecimal totalRegistradoPensionado;


    /**
     * Valor del Monto relacionado para Pensionado
     */
    private BigDecimal montoRelacionadoPensionado;


    /**
     * Valor del Interes relacionado para Pensionado
     */
    private BigDecimal interesRelacionadoPensionado;


    /**
     * Valor total obtenido por la suma del monto e interes relacionado para Pensionado
     */
    private BigDecimal totalRelacionadoPensionado;
   
    /**
     * Representa el tipo de recaudo (aportes, devoluciones, registrados, correcciones)  
     */
    private TipoRegistroEnum tipoRegistro;


    /**
     * Valor del Monto registrado para independientes
     */
    private BigDecimal montoRegistradoIndependiente_02;
    private BigDecimal montoRegistradoPensionado_02;


    /**
     * Valor del Interes registrado para independientes
     */
    private BigDecimal interesRegistradoIndependiente_02;
    private BigDecimal interesRegistradoPensionado_02;


    /**
     * Valor total obtenido por la suma del monto e interes registrado para independientes
     */
    private BigDecimal totalRegistradoIndependiente_02;
    private BigDecimal totalRegistradoPensionado_02;
        /**
     * Valor del Monto Relacionado para independientes
     */
    private BigDecimal montoRelacionadoIndependiente_02;
    private BigDecimal montoRelacionadoPensionado_02;


    /**
     * Valor del Interes Relacionado para independientes
     */
    private BigDecimal interesRelacionadoIndependiente_02;
    private BigDecimal interesRelacionadoPensionado_02;


    /**
     * Valor total obtenido por la suma del monto e interes Relacionado para independientes
     */
    private BigDecimal totalRelacionadoIndependiente_02;
    private BigDecimal totalRelacionadoPensionado_02;


    /**
     * Valor del Monto relacionado para independientes
     */
    private BigDecimal montoRelacionadoIndependiente_06;
    private BigDecimal montoRelacionadoPensionado_06;


    /**
     * Valor del Interes relacionado para independientes
     */
    private BigDecimal interesRelacionadoIndependiente_06;
    private BigDecimal interesRelacionadoPensionado_06;


    /**
     * Valor total obtenido por la suma del monto e interes relacionado para independientes
     */
    private BigDecimal totalRelacionadoIndependiente_06;  
    private BigDecimal totalRelacionadoPensionado_06;  


        /**
     * Valor del Monto Registrado para independientes
     */
    private BigDecimal montoRegistradoIndependiente_06;
    private BigDecimal montoRegistradoPensionado_06;


    /**
     * Valor del Interes Registrado para independientes
     */
    private BigDecimal interesRegistradoIndependiente_06;
    private BigDecimal interesRegistradoPensionado_06;


    /**
     * Valor total obtenido por la suma del monto e interes Registrado para independientes
     */
    private BigDecimal totalRegistradoIndependiente_06;  
    private BigDecimal totalRegistradoPensionado_06;  

   
    /**
     * Constructor con incialización de valores en cero
     */
    public RegistroAporteDTO() {
        super();
        this.montoRegistradoDependiente = BigDecimal.ZERO;
        this.interesRegistradoDependiente = BigDecimal.ZERO;
        this.totalRegistradoDependiente = BigDecimal.ZERO;
        this.montoRelacionadoDependiente = BigDecimal.ZERO;
        this.interesRelacionadoDependiente = BigDecimal.ZERO;
        this.totalRelacionadoDependiente = BigDecimal.ZERO;
        this.montoRegistradoIndependiente = BigDecimal.ZERO;
        this.interesRegistradoIndependiente = BigDecimal.ZERO;
        this.totalRegistradoIndependiente = BigDecimal.ZERO;
        this.montoRelacionadoIndependiente = BigDecimal.ZERO;
        this.interesRelacionadoIndependiente = BigDecimal.ZERO;
        this.totalRelacionadoIndependiente = BigDecimal.ZERO;
        this.montoRegistradoPensionado = BigDecimal.ZERO;
        this.interesRegistradoPensionado = BigDecimal.ZERO;
        this.totalRegistradoPensionado = BigDecimal.ZERO;
        this.montoRelacionadoPensionado = BigDecimal.ZERO;
        this.interesRelacionadoPensionado = BigDecimal.ZERO;
        this.totalRelacionadoPensionado = BigDecimal.ZERO;
        this.montoRegistradoIndependiente_02 = BigDecimal.ZERO;
        this.montoRegistradoPensionado_02 = BigDecimal.ZERO;
        this.interesRegistradoIndependiente_02 = BigDecimal.ZERO;
        this.interesRegistradoPensionado_02 = BigDecimal.ZERO;
        this.totalRegistradoIndependiente_02 = BigDecimal.ZERO;
        this.totalRegistradoPensionado_02 = BigDecimal.ZERO;
        this.montoRelacionadoIndependiente_06 = BigDecimal.ZERO;
        this.montoRelacionadoPensionado_06 = BigDecimal.ZERO;
        this.interesRelacionadoIndependiente_06 = BigDecimal.ZERO;
        this.interesRelacionadoPensionado_06 = BigDecimal.ZERO;
        this.totalRelacionadoIndependiente_06 = BigDecimal.ZERO;
        this.totalRelacionadoPensionado_06 = BigDecimal.ZERO;
        this.montoRelacionadoIndependiente_02 = BigDecimal.ZERO;
        this.montoRelacionadoPensionado_02 = BigDecimal.ZERO;
        this.interesRelacionadoIndependiente_02 = BigDecimal.ZERO;
        this.interesRelacionadoPensionado_02 = BigDecimal.ZERO;
        this.totalRelacionadoIndependiente_02 = BigDecimal.ZERO;
        this.totalRelacionadoPensionado_02 = BigDecimal.ZERO;
        this.montoRegistradoIndependiente_06 = BigDecimal.ZERO;
        this.montoRegistradoPensionado_06 = BigDecimal.ZERO;
        this.interesRegistradoIndependiente_06 = BigDecimal.ZERO;
        this.interesRegistradoPensionado_06 = BigDecimal.ZERO;
        this.totalRegistradoIndependiente_06 = BigDecimal.ZERO;
        this.totalRegistradoPensionado_06 = BigDecimal.ZERO;
       
    }

    public BigDecimal getMontoRegistradoDependiente() {
        return this.montoRegistradoDependiente;
    }

    public void setMontoRegistradoDependiente(BigDecimal montoRegistradoDependiente) {
        this.montoRegistradoDependiente = montoRegistradoDependiente;
    }

    public BigDecimal getInteresRegistradoDependiente() {
        return this.interesRegistradoDependiente;
    }

    public void setInteresRegistradoDependiente(BigDecimal interesRegistradoDependiente) {
        this.interesRegistradoDependiente = interesRegistradoDependiente;
    }

    public BigDecimal getTotalRegistradoDependiente() {
        return this.totalRegistradoDependiente;
    }

    public void setTotalRegistradoDependiente(BigDecimal totalRegistradoDependiente) {
        this.totalRegistradoDependiente = totalRegistradoDependiente;
    }

    public BigDecimal getMontoRelacionadoDependiente() {
        return this.montoRelacionadoDependiente;
    }

    public void setMontoRelacionadoDependiente(BigDecimal montoRelacionadoDependiente) {
        this.montoRelacionadoDependiente = montoRelacionadoDependiente;
    }

    public BigDecimal getInteresRelacionadoDependiente() {
        return this.interesRelacionadoDependiente;
    }

    public void setInteresRelacionadoDependiente(BigDecimal interesRelacionadoDependiente) {
        this.interesRelacionadoDependiente = interesRelacionadoDependiente;
    }

    public BigDecimal getTotalRelacionadoDependiente() {
        return this.totalRelacionadoDependiente;
    }

    public void setTotalRelacionadoDependiente(BigDecimal totalRelacionadoDependiente) {
        this.totalRelacionadoDependiente = totalRelacionadoDependiente;
    }

    public BigDecimal getMontoRegistradoIndependiente() {
        return this.montoRegistradoIndependiente;
    }

    public void setMontoRegistradoIndependiente(BigDecimal montoRegistradoIndependiente) {
        this.montoRegistradoIndependiente = montoRegistradoIndependiente;
    }

    public BigDecimal getInteresRegistradoIndependiente() {
        return this.interesRegistradoIndependiente;
    }

    public void setInteresRegistradoIndependiente(BigDecimal interesRegistradoIndependiente) {
        this.interesRegistradoIndependiente = interesRegistradoIndependiente;
    }

    public BigDecimal getTotalRegistradoIndependiente() {
        return this.totalRegistradoIndependiente;
    }

    public void setTotalRegistradoIndependiente(BigDecimal totalRegistradoIndependiente) {
        this.totalRegistradoIndependiente = totalRegistradoIndependiente;
    }

    public BigDecimal getMontoRelacionadoIndependiente() {
        return this.montoRelacionadoIndependiente;
    }

    public void setMontoRelacionadoIndependiente(BigDecimal montoRelacionadoIndependiente) {
        this.montoRelacionadoIndependiente = montoRelacionadoIndependiente;
    }

    public BigDecimal getInteresRelacionadoIndependiente() {
        return this.interesRelacionadoIndependiente;
    }

    public void setInteresRelacionadoIndependiente(BigDecimal interesRelacionadoIndependiente) {
        this.interesRelacionadoIndependiente = interesRelacionadoIndependiente;
    }

    public BigDecimal getTotalRelacionadoIndependiente() {
        return this.totalRelacionadoIndependiente;
    }

    public void setTotalRelacionadoIndependiente(BigDecimal totalRelacionadoIndependiente) {
        this.totalRelacionadoIndependiente = totalRelacionadoIndependiente;
    }

    public BigDecimal getMontoRegistradoPensionado() {
        return this.montoRegistradoPensionado;
    }

    public void setMontoRegistradoPensionado(BigDecimal montoRegistradoPensionado) {
        this.montoRegistradoPensionado = montoRegistradoPensionado;
    }

    public BigDecimal getInteresRegistradoPensionado() {
        return this.interesRegistradoPensionado;
    }

    public void setInteresRegistradoPensionado(BigDecimal interesRegistradoPensionado) {
        this.interesRegistradoPensionado = interesRegistradoPensionado;
    }

    public BigDecimal getTotalRegistradoPensionado() {
        return this.totalRegistradoPensionado;
    }

    public void setTotalRegistradoPensionado(BigDecimal totalRegistradoPensionado) {
        this.totalRegistradoPensionado = totalRegistradoPensionado;
    }

    public BigDecimal getMontoRelacionadoPensionado() {
        return this.montoRelacionadoPensionado;
    }

    public void setMontoRelacionadoPensionado(BigDecimal montoRelacionadoPensionado) {
        this.montoRelacionadoPensionado = montoRelacionadoPensionado;
    }

    public BigDecimal getInteresRelacionadoPensionado() {
        return this.interesRelacionadoPensionado;
    }

    public void setInteresRelacionadoPensionado(BigDecimal interesRelacionadoPensionado) {
        this.interesRelacionadoPensionado = interesRelacionadoPensionado;
    }

    public BigDecimal getTotalRelacionadoPensionado() {
        return this.totalRelacionadoPensionado;
    }

    public void setTotalRelacionadoPensionado(BigDecimal totalRelacionadoPensionado) {
        this.totalRelacionadoPensionado = totalRelacionadoPensionado;
    }

    public TipoRegistroEnum getTipoRegistro() {
        return this.tipoRegistro;
    }

    public void setTipoRegistro(TipoRegistroEnum tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public BigDecimal getMontoRegistradoIndependiente_02() {
        return this.montoRegistradoIndependiente_02;
    }

    public void setMontoRegistradoIndependiente_02(BigDecimal montoRegistradoIndependiente_02) {
        this.montoRegistradoIndependiente_02 = montoRegistradoIndependiente_02;
    }

    public BigDecimal getMontoRegistradoPensionado_02() {
        return this.montoRegistradoPensionado_02;
    }

    public void setMontoRegistradoPensionado_02(BigDecimal montoRegistradoPensionado_02) {
        this.montoRegistradoPensionado_02 = montoRegistradoPensionado_02;
    }

    public BigDecimal getInteresRegistradoIndependiente_02() {
        return this.interesRegistradoIndependiente_02;
    }

    public void setInteresRegistradoIndependiente_02(BigDecimal interesRegistradoIndependiente_02) {
        this.interesRegistradoIndependiente_02 = interesRegistradoIndependiente_02;
    }

    public BigDecimal getInteresRegistradoPensionado_02() {
        return this.interesRegistradoPensionado_02;
    }

    public void setInteresRegistradoPensionado_02(BigDecimal interesRegistradoPensionado_02) {
        this.interesRegistradoPensionado_02 = interesRegistradoPensionado_02;
    }

    public BigDecimal getTotalRegistradoIndependiente_02() {
        return this.totalRegistradoIndependiente_02;
    }

    public void setTotalRegistradoIndependiente_02(BigDecimal totalRegistradoIndependiente_02) {
        this.totalRegistradoIndependiente_02 = totalRegistradoIndependiente_02;
    }

    public BigDecimal getTotalRegistradoPensionado_02() {
        return this.totalRegistradoPensionado_02;
    }

    public void setTotalRegistradoPensionado_02(BigDecimal totalRegistradoPensionado_02) {
        this.totalRegistradoPensionado_02 = totalRegistradoPensionado_02;
    }

    public BigDecimal getMontoRelacionadoIndependiente_02() {
        return this.montoRelacionadoIndependiente_02;
    }

    public void setMontoRelacionadoIndependiente_02(BigDecimal montoRelacionadoIndependiente_02) {
        this.montoRelacionadoIndependiente_02 = montoRelacionadoIndependiente_02;
    }

    public BigDecimal getMontoRelacionadoPensionado_02() {
        return this.montoRelacionadoPensionado_02;
    }

    public void setMontoRelacionadoPensionado_02(BigDecimal montoRelacionadoPensionado_02) {
        this.montoRelacionadoPensionado_02 = montoRelacionadoPensionado_02;
    }

    public BigDecimal getInteresRelacionadoIndependiente_02() {
        return this.interesRelacionadoIndependiente_02;
    }

    public void setInteresRelacionadoIndependiente_02(BigDecimal interesRelacionadoIndependiente_02) {
        this.interesRelacionadoIndependiente_02 = interesRelacionadoIndependiente_02;
    }

    public BigDecimal getInteresRelacionadoPensionado_02() {
        return this.interesRelacionadoPensionado_02;
    }

    public void setInteresRelacionadoPensionado_02(BigDecimal interesRelacionadoPensionado_02) {
        this.interesRelacionadoPensionado_02 = interesRelacionadoPensionado_02;
    }

    public BigDecimal getTotalRelacionadoIndependiente_02() {
        return this.totalRelacionadoIndependiente_02;
    }

    public void setTotalRelacionadoIndependiente_02(BigDecimal totalRelacionadoIndependiente_02) {
        this.totalRelacionadoIndependiente_02 = totalRelacionadoIndependiente_02;
    }

    public BigDecimal getTotalRelacionadoPensionado_02() {
        return this.totalRelacionadoPensionado_02;
    }

    public void setTotalRelacionadoPensionado_02(BigDecimal totalRelacionadoPensionado_02) {
        this.totalRelacionadoPensionado_02 = totalRelacionadoPensionado_02;
    }

    public BigDecimal getMontoRelacionadoIndependiente_06() {
        return this.montoRelacionadoIndependiente_06;
    }

    public void setMontoRelacionadoIndependiente_06(BigDecimal montoRelacionadoIndependiente_06) {
        this.montoRelacionadoIndependiente_06 = montoRelacionadoIndependiente_06;
    }

    public BigDecimal getMontoRelacionadoPensionado_06() {
        return this.montoRelacionadoPensionado_06;
    }

    public void setMontoRelacionadoPensionado_06(BigDecimal montoRelacionadoPensionado_06) {
        this.montoRelacionadoPensionado_06 = montoRelacionadoPensionado_06;
    }

    public BigDecimal getInteresRelacionadoIndependiente_06() {
        return this.interesRelacionadoIndependiente_06;
    }

    public void setInteresRelacionadoIndependiente_06(BigDecimal interesRelacionadoIndependiente_06) {
        this.interesRelacionadoIndependiente_06 = interesRelacionadoIndependiente_06;
    }

    public BigDecimal getInteresRelacionadoPensionado_06() {
        return this.interesRelacionadoPensionado_06;
    }

    public void setInteresRelacionadoPensionado_06(BigDecimal interesRelacionadoPensionado_06) {
        this.interesRelacionadoPensionado_06 = interesRelacionadoPensionado_06;
    }

    public BigDecimal getTotalRelacionadoIndependiente_06() {
        return this.totalRelacionadoIndependiente_06;
    }

    public void setTotalRelacionadoIndependiente_06(BigDecimal totalRelacionadoIndependiente_06) {
        this.totalRelacionadoIndependiente_06 = totalRelacionadoIndependiente_06;
    }

    public BigDecimal getTotalRelacionadoPensionado_06() {
        return this.totalRelacionadoPensionado_06;
    }

    public void setTotalRelacionadoPensionado_06(BigDecimal totalRelacionadoPensionado_06) {
        this.totalRelacionadoPensionado_06 = totalRelacionadoPensionado_06;
    }

    public BigDecimal getMontoRegistradoIndependiente_06() {
        return this.montoRegistradoIndependiente_06;
    }

    public void setMontoRegistradoIndependiente_06(BigDecimal montoRegistradoIndependiente_06) {
        this.montoRegistradoIndependiente_06 = montoRegistradoIndependiente_06;
    }

    public BigDecimal getMontoRegistradoPensionado_06() {
        return this.montoRegistradoPensionado_06;
    }

    public void setMontoRegistradoPensionado_06(BigDecimal montoRegistradoPensionado_06) {
        this.montoRegistradoPensionado_06 = montoRegistradoPensionado_06;
    }

    public BigDecimal getInteresRegistradoIndependiente_06() {
        return this.interesRegistradoIndependiente_06;
    }

    public void setInteresRegistradoIndependiente_06(BigDecimal interesRegistradoIndependiente_06) {
        this.interesRegistradoIndependiente_06 = interesRegistradoIndependiente_06;
    }

    public BigDecimal getInteresRegistradoPensionado_06() {
        return this.interesRegistradoPensionado_06;
    }

    public void setInteresRegistradoPensionado_06(BigDecimal interesRegistradoPensionado_06) {
        this.interesRegistradoPensionado_06 = interesRegistradoPensionado_06;
    }

    public BigDecimal getTotalRegistradoIndependiente_06() {
        return this.totalRegistradoIndependiente_06;
    }

    public void setTotalRegistradoIndependiente_06(BigDecimal totalRegistradoIndependiente_06) {
        this.totalRegistradoIndependiente_06 = totalRegistradoIndependiente_06;
    }
     public BigDecimal getTotalRegistradoPensionado_06() {
        return this.totalRegistradoPensionado_06;
    }

    public void setTotalRegistradoPensionado_06(BigDecimal totalRegistradoPensionado_06) {
        this.totalRegistradoPensionado_06 = totalRegistradoPensionado_06;
    }

}



