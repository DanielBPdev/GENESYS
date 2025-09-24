package com.asopagos.pila.validadores.bloque7.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.FasePila2Enum;

/**
 * <b>Descripcion:</b> Interfaz de definición de EJB para el llamado a USP de ejecución de validaciones de PILA 2<br/>
 * <b>Módulo:</b> Asopagos - HU-211-395, HU-211-396, HU-211-480, HU-211-397, HU-211-398 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel.</a>
 */
@Local
public interface IGestorPila2 {

    /**
     * Función que da inicio a la verificación de los datos contenidos en el archivo PILA contra la base de datos de afiliados
     * @param indicesPlanilla
     *        Listado de índices de planilla para marcar como ejecución manual
     * @param idProcesoAgrupador
     * 		  ID del proceso que solicita la validación
     */
    public void iniciarPila2(List<IndicePlanilla> indicesPlanilla, Long idProcesoAgrupador);


    public void iniciarPila2SinValidaciones(List<Long>  indicesPlanilla);
    /**
     * Metodo que permite el reprocesamiento de una planilla en una fase del proceso de PILA 2
     * 
     * @param idIndicePlanilla
     *        <code>Long</code>
     *        Identificador de índice de planilla a reprocesar
     * 
     * @param faseProceso
     *        <code>FasePila2Enum</code>
     *        Representa la fase de procesamiento de PILA 2
     * 
     * @param esSimulado
     *        <code>Boolean</code>
     *        Marca para saber que la ejecución es una simulación de proceso asistido
     * 
     * @param usuarioProceso
     *        <code>String</code>
     *        Indica el usuario que realiza la ejecución de un proceso asistido
     */
    public void reprocesarPlanilla(Long idIndicePlanilla, FasePila2Enum faseProceso, Boolean esSimulado, String usuarioProceso);
}
