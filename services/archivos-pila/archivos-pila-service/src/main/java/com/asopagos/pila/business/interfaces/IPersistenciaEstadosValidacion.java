package com.asopagos.pila.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * Servicios de persistencia de registros de estado por bloques
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IPersistenciaEstadosValidacion {

    /**
     * Método para el registro de una nueva entrada de estados por bloque de OI
     * @param nuevoEstado
     *        Nueva instancia de estado por bloque para persistir
     */
    public void registrarEstadoOI(EstadoArchivoPorBloque nuevoEstado);

    /**
     * Función para la consulta del estado de un archivo de planilla por tipo de archivo
     * @param indicePlanilla
     *        Entrada en el índice de la planilla consultada
     * @return EstadoArchivoPorBloque
     *         DTO con el registro del estado del archivo
     * @throws ErrorFuncionalValidacionException
     */
    public EstadoArchivoPorBloque consultarEstadoEspecificoOI(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException;

    /**
     * Función para la consulta de todos los estados por bloque OI asociados a un número de planilla
     * @param numeroPlanilla
     *        Número de la planilla consultada
     * @param codOperador
     *        Código del operador de información que emite la planilla
     * @return List<EstadoArchivoPorBloque>
     *         Listado con las instancias de estado por bloque encontradas para el número de planilla ingresado
     */
    public List<EstadoArchivoPorBloque> consultarEstadoGeneralOI(Long numeroPlanilla, String codOperador);

    /**
     * Método para actualizar una entrada de estados por bloque de OI
     * @param estadoActualizado
     *        Instancia actualizada del estado por bloque de OI para persistir
     */
    public void actualizarEstadoOI(EstadoArchivoPorBloque estadoActualizado);

    /**
     * Método para el registro de una nueva entrada de estados por bloque de OF
     * @param nuevoEstado
     *        Nueva instancia de estado por bloque para persistir
     */
    public void registrarEstadoOF(EstadoArchivoPorBloqueOF nuevoEstado);

    /**
     * Función para la consulta del estado de un archivo de OF
     * @param indicePlanilla
     *        Entrada en el índice de la planilla consultada
     * @return DTO con el registro del estado del archivo
     */
    public EstadoArchivoPorBloqueOF consultarEstadoOF(IndicePlanillaOF indicePlanilla) throws ErrorFuncionalValidacionException;

    /**
     * Método para actualizar una entrada de estados por bloque de OF
     * @param estadoActualizado
     *        Instancia actualizada del estado por bloque de OF para persistir
     */
    public void actualizarEstadoOF(EstadoArchivoPorBloqueOF estadoActualizado);

    /**
     * Procedimiento para actualizar el estado de un registro 6 de archivo de OF
     * @param registro6Fid
     *        Id del registro a actualizar
     * @param estado
     *        Estado a asignar al registro
     */
    public void actualizarEstadoArchivoFRegistro6(Long registro6Fid, EstadoConciliacionArchivoFEnum estado);

    /**
     * Método encargado de consultar los diferentes estados de conciliación de los registros tipo 6 de un
     * archivo OF
     * 
     * @param indicePlanillaOF
     *        Entrada de índice planilla para la consulta
     * @return Listado de los diferentes estados de validación de los registros tipo 6
     */
    public List<String> consultarEstadosRegistros6(IndicePlanillaOF indicePlanillaOF);

    /**
     * Método encargado de la actualización del estado de conciliación general de archivo OF
     * @param indicePlanillaOF
     *        Entrada de índice planilla para la consulta
     * @param estado
     *        Estado a asignar en el bloque 6 de OF
     * @param accionArchivoOF
     *        Acción a asignar en el bloque 6 de OF
     */
    public void actualizarEstadoConciliacionOF(IndicePlanillaOF indicePlanillaOF, EstadoProcesoArchivoEnum estado,
            AccionProcesoArchivoEnum accionArchivoOF);

    /**
     * Método encargado de hacer la persistencia de una entrada de histórico de estado
     * @param historicoEstado
     *        Entrada de Histórico de estados a persistir
     */
    public void registrarHistoricoEstado(HistorialEstadoBloque historicoEstado);
    
    /**
     * Método encargado de consultar las planillas que se encuentran detenida y en estado por conciliar
     * @return lista de planillas
     */
    public List<IndicePlanilla> consultarPlanilalsOIDetenidasPorConciliar();

    public List<IndicePlanilla> consultarPlanilalsOISegunSpReiniciarPlanillas(List<Long> indicesPlanilla);

    
    /**
     * Método encargado de consultar las planillas que se encuentran detenida y en estado por conciliar
     * @return lista de planillas
     */
    public List<IndicePlanilla> consultarPlanilalsOIDetenidasValorCero();
}
