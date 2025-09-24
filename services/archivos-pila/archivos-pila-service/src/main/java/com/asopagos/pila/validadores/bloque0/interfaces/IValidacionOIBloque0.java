package com.asopagos.pila.validadores.bloque0.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.pila.dto.RespuestaValidacionDTO;

/**
 * <b>Descripción:</b> Interfaz de definición de EJB para las validaciones de Bloque 0 de Operador de Información<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IValidacionOIBloque0 {

    /**
     * Función para la validación del bloque cero de Operador de Información
     * @param indicePlanilla
     *        Entrada de índice de planilla a validar
     * @param contexto
     *        Mapa de valores de contexto
     * @param usuario
     *        Nombre del usuario que solicita la validación
     * @return RespuestaValidacionDTO
     *         DTO de respuesta de bloque de validación que inluye la instancia de indice de planilla, presenta ID sí el proceso de
     *         validación fue exitosos
     */
    public RespuestaValidacionDTO validarBloqueCero(IndicePlanilla indicePlanilla, Map<String, Object> contexto, String usuario);

    /**
     * @param archivoPila
     *        DTO de carga del archivo
     * @param contexto
     *        Mapa de valores de contexto
     * @param usuario
     *        Nombre del usuario que solicita la validación
     * @param validarB0
     *        Indicador para determinar sí se debe ejecutar el B0 de validación luego de la carga del archivo
     * @return RespuestaValidacionDTO
     *         DTO de respuesta de bloque de validación que inluye la instancia de indice de planilla, presenta ID sí el proceso de
     *         validación fue exitosos
     */
    public RespuestaValidacionDTO registrarIndice(ArchivoPilaDTO archivoPila, Map<String, Object> contexto, String usuario,
            Boolean validarB0);

}
