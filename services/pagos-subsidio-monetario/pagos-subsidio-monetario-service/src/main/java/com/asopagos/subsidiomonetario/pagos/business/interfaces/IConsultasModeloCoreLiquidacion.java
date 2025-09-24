package com.asopagos.subsidiomonetario.pagos.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en el modelo de datos Core para el proceso de
 * dispersión<br/>
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Local
public interface IConsultasModeloCoreLiquidacion {

    /**
     * <b>Descripción:</b>Método que permite consultar la dispersión de una liquidación de fallecimiento.
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionMontoLiquidacionFallecimiento(String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio tarjeta, que se encuentran pendientes por dispersdar en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String numeroRadicacion,
            Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio efectivo, que se encuentran pendientes por dispersdar en un
     * proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String numeroRadicacion,
            Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio banco-consignaciones, que se encuentran pendientes por dispersdar
     * en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            String numeroRadicacion, Long identificadorCondicion);

    /**
     * <b>Descripción:</b>Método que permite consultar los pagos al medio banco-pagos judiciales, que se encuentran pendientes por
     * dispersar en un proceso
     * de liquidación por fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoJudiciales(
            String numeroRadicacion, Long identificadorCondicion);
    
    /**
     * <b>Descripción:</b>Método que permite consultar los descuentos aplicados a la liquidación de fallecimiento
     * <b>Módulo:</b> Asopagos - HU-317-508<br/>
     * @author rlopez
     * 
     * @param numeroRadicacion
     *        Valor del número de radicado
     *        <code>String</code>
     * 
     * @param identificadorCondicion
     *        Identificador de condición para el administrador de subsidio
     *        <code>Long</code>
     * 
     * @return información de la dispersión del monto liquidado por la liquidación de fallecimiento
     */
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoDescuentos(String numeroRadicacion,
            Long identificadorCondicion);

}
