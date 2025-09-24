package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.AccionCobro1C;
import com.asopagos.enumeraciones.cartera.VariableCalculoCotizantesEnum;

/**
 * Entidad donde se parametriza la acción de cobro para el metodo 1C
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:19 p. m.
 */
public class AccionCobro1CModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 4314594262502698876L;
    /**
     * Fecha de generación del documento liquidación deuda presunta
     */
    private Long diasLiquidacion;
    /**
     * Hora inicio de ejecución del proceso
     */
    private Long horaEjecucion;
    /**
     * Plazo límite de envío del documento liquidación deuda presunta
     */
    private Long limiteEnvioDocumento;
    /**
     * Variable utilizada para el cálculo promedio de ingresos de cotizantes
     */
    private VariableCalculoCotizantesEnum variableCalculo;
    /**
     * Cantidad máxima de periodos previos considerados para el cálculo del promedio
     * de ingresos del cotizante
     */
    private Long cantidadPeriodos;

    /**
     * @return the diasLiquidacion
     */
    public Long getDiasLiquidacion() {
        return diasLiquidacion;
    }

    /**
     * @param diasLiquidacion the diasLiquidacion to set
     */
    public void setDiasLiquidacion(Long diasLiquidacion) {
        this.diasLiquidacion = diasLiquidacion;
    }

    /**
     * @return the horaEjecucion
     */
    public Long getHoraEjecucion() {
        return horaEjecucion;
    }

    /**
     * @param horaEjecucion the horaEjecucion to set
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     * @return the limiteEnvioDocumento
     */
    public Long getLimiteEnvioDocumento() {
        return limiteEnvioDocumento;
    }

    /**
     * @param limiteEnvioDocumento
     *        the limiteEnvioDocumento to set
     */
    public void setLimiteEnvioDocumento(Long limiteEnvioDocumento) {
        this.limiteEnvioDocumento = limiteEnvioDocumento;
    }

    /**
     * @return the variableCalculo
     */
    public VariableCalculoCotizantesEnum getVariableCalculo() {
        return variableCalculo;
    }

    /**
     * @param variableCalculo
     *        the variableCalculo to set
     */
    public void setVariableCalculo(VariableCalculoCotizantesEnum variableCalculo) {
        this.variableCalculo = variableCalculo;
    }

    /**
     * @return the cantidadPeriodos
     */
    public Long getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    /**
     * @param cantidadPeriodos
     *        the cantidadPeriodos to set
     */
    public void setCantidadPeriodos(Long cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro1CModeloDTO
     * @param accionCobro1C
     *        recibe la entity
     * @return devuelve un objeto DTO AccionCobro1CModeloDTO
     */
    public AccionCobro1CModeloDTO convertToDTO(AccionCobro1C accionCobro1C) {
        super.convertToDTO(accionCobro1C);
        /* Se setean la información al objeto AccionCobro1CModeloDTO */
        this.setDiasLiquidacion(accionCobro1C.getDiasLiquidacion());
        this.setHoraEjecucion(accionCobro1C.getHoraEjecucion()!=null? accionCobro1C.getHoraEjecucion().getTime():null);
        this.setVariableCalculo(accionCobro1C.getVariableCalculo());
        this.setLimiteEnvioDocumento(accionCobro1C.getLimiteEnvioDocumento());
        this.setCantidadPeriodos(accionCobro1C.getCantidadPeriodos());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro1C}
     * @return devuelve un objeto Entity de {@link AccionCobro1C}
     */
    public AccionCobro1C convertToAccionCobro1CEntity() {
        /* Se instancia objeto AccionCobro1C */
        AccionCobro1C accionCobro1C = new AccionCobro1C();
        /* Se carga información de la clase padre */
        accionCobro1C = (AccionCobro1C) super.convertToEntity(accionCobro1C);
        /* Se setan los valores a accionCobro1C */
        accionCobro1C.setDiasLiquidacion(this.getDiasLiquidacion());
        accionCobro1C.setHoraEjecucion(this.getHoraEjecucion()!=null? new Date(this.getHoraEjecucion()):null);
        accionCobro1C.setVariableCalculo(this.getVariableCalculo());
        accionCobro1C.setLimiteEnvioDocumento(this.getLimiteEnvioDocumento());
        accionCobro1C.setCantidadPeriodos(this.getCantidadPeriodos());
        return accionCobro1C;
    }
}