package com.asopagos.pila.business.interfaces;

import javax.ejb.Local;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.pila.dto.RespuestaRegistroEstadoDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interfaz de definición de EJB para la gestión de los estados en los bloques de validación para los archivos PILA<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393<br>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IGestorEstadosValidacion {

    /**
     * Función para actualizar el estado de validación de un archivo en un bloque específico
     * @param indicePlanilla
     *        Entrada en el índice de la planilla consultada
     * @param estado
     *        Estado del bloque
     * @param accion
     *        Acción para el bloque
     * @param causa
     *        Descripción de la causa del estado
     * @param bloqueValidado
     *        Bloque de validación para el que se establece el estado
     * @param idLectura
     *        ID del registro de lectura del componente File Processor
     * @return DTO con el resultado del registro de estado
     */
    public RespuestaRegistroEstadoDTO registrarEstadoArchivo(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado,
            AccionProcesoArchivoEnum accion, String causa, Integer bloqueValidado, Long idLectura) throws ErrorFuncionalValidacionException;

    /**
     * Función para establecer que un número de planilla cuenta con sus archivos tipo A y tipo I aprobados para un bloque determinado
     * @param indicePlanilla
     *        Entrada en el índice de la planilla consultada
     * @param bloque
     *        Bloque de validación consultado
     * @return DTO con el resultado del crucede estados
     */
    public RespuestaRegistroEstadoDTO parejaArchivosAprobadaPorBloque(IndicePlanilla indicePlanilla, Integer bloque)
            throws ErrorFuncionalValidacionException;

    /**
     * Procedimiento para actualizar el estado de archivo en par
     * @param indicePlanillaA
     *        Entrada en el índice de la planilla consultada de tipo A
     * @param indicePlanillaI
     *        Entrada en el índice de la planilla consultada de tipo I
     * @param bloque
     *        Bloque de validación a actualizar
     * @param estado
     *        Estado para cada índice
     * @param accion
     *        Acción para cada índice
     * @param causa
     *        Causa puntual
     */
    public void registrarEstadoParejaArchivos(IndicePlanilla indicePlanillaA, IndicePlanilla indicePlanillaI, Integer bloque,
            EstadoProcesoArchivoEnum estado, AccionProcesoArchivoEnum accion, String causa) throws ErrorFuncionalValidacionException;

    /**
     * Procedimiento para actualizar el estado de validación de un archivo de OF en un bloque específico
     * @param indicePlanilla
     *        Entrada en el índice de la planilla consultada
     * @param estado
     *        Estado del bloque
     * @param accion
     *        Acción para el bloque
     * @param causa
     *        Descripción de la causa del estado
     * @param bloqueValidado
     *        Bloque de validación para el que se establece el estado
     */
    public void registrarEstadoArchivoOF(IndicePlanillaOF indicePlanilla, EstadoProcesoArchivoEnum estado, AccionProcesoArchivoEnum accion,
            String causa, Integer bloqueValidado) throws ErrorFuncionalValidacionException;

    /**
     * Método para anular el último bloque de validación ejecutado a partir de un índice de planilla
     * 
     * @param indice
     *        Instancia de indice de planilla (OI u OF) para hacer la consulta
     * @throws ErrorFuncionalValidacionException
     */
    public void anularUltimoBloque(Object indice) throws ErrorFuncionalValidacionException;

    /**
     * Método encargado de realizar el cambio de "acción manual" para los bloques 7 a 10 (PILA2)
     * @param indice
     *        Entrada de índice de planilla a actualizar
     */
    public void actualizarAccionManual(IndicePlanilla indice) throws ErrorFuncionalValidacionException;

    /**
     * Método encargado de llevar a cabo la actualización de los estados de un Log Financiero (Archivo OF)
     * 
     * @param registro6
     *        Registro tipo 6 de OF que fue validado
     * @param estadoOF
     *        Estado de la validación del registro tipo 6 de OF
     */
    public void actualizarEstadosOF(PilaArchivoFRegistro6 registro6, EstadoConciliacionArchivoFEnum estadoOF);
}
