package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Descripción:</b> DTO que contiene el resultado del cálculo del valor de intereses de mora de un aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ErrorValidacionValorMoraDTO implements Serializable {
    private static final long serialVersionUID = -2949240255369065284L;
    
    /**
     * Valor calculado del interés de mora del aporte
     * */
    private BigDecimal valorMoraCalculado;
    
    /**
     * Palabra indicio para mensaje de error
     * */
    private String indicioMensaje = null;
    
    /**
     * Mensaje de error detallado
     * */
    private String mensajeErrorDetallado = null;

    /**
     * @return the valorMoraCalculado
     */
    public BigDecimal getValorMoraCalculado() {
        return valorMoraCalculado;
    }

    /**
     * @param valorMoraCalculado the valorMoraCalculado to set
     */
    public void setValorMoraCalculado(BigDecimal valorMoraCalculado) {
        this.valorMoraCalculado = valorMoraCalculado;
    }

    /**
     * @return the indicioMensaje
     */
    public String getIndicioMensaje() {
        return indicioMensaje;
    }

    /**
     * @param indicioMensaje the indicioMensaje to set
     */
    public void setIndicioMensaje(String indicioMensaje) {
        this.indicioMensaje = indicioMensaje;
    }

    /**
     * @return the mensajeErrorDetallado
     */
    public String getMensajeErrorDetallado() {
        return mensajeErrorDetallado;
    }

    /**
     * @param mensajeErrorDetallado the mensajeErrorDetallado to set
     */
    public void setMensajeErrorDetallado(String mensajeErrorDetallado) {
        this.mensajeErrorDetallado = mensajeErrorDetallado;
    }
}
