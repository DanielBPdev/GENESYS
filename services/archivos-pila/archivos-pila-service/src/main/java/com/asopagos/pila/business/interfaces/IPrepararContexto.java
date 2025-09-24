package com.asopagos.pila.business.interfaces;

import java.util.Map;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> interface que define los servicios para la preconfiguración del mapa de variables de contexto
 * antes de iniciar con un bloque de validación<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public interface IPrepararContexto {

    /**
     * Función para generar un mapa de contexto acorde al bloque de validaciones que dará inicio
     * @param bloque
     *        Número de bloque a preparar
     * @param numeroPlanilla
     *        Parámetro opcional con el número de la planilla
     * @param idPlanilla
     *        Parámetro opcional con el ID de la planilla en el índice
     * @param indicePlanilla
     *        Índice de planilla OI u OF para preparación de contexto que dependa del tipo de archivo
     * @return Mapa de contexto
     * @throws ErrorFuncionalValidacionException
     */
    public Map<String, Object> prepararContexto(BloqueValidacionEnum bloque, Long numeroPlanilla, Object indicePlanilla)
            throws ErrorFuncionalValidacionException;
}
