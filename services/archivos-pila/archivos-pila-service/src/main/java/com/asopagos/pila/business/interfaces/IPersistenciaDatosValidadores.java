package com.asopagos.pila.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Local;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.ccf.general.EjecucionProgramada;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro2;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.entidades.pila.soporte.ProcesoPila;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.pila.dto.ElementoCombinatoriaArchivosDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;

/**
 * Interfaz para la persistencia de datos de validadores
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IPersistenciaDatosValidadores {

    /**
     * Función para consultar una entrada de índice de Planilla por su ID
     * @param idIndicePlanilla
     *        Índice de planilla a consultar
     * @return IndicePlanilla objeto encontrado
     */
    public IndicePlanilla consultarPlanillaOIPorId(Long idIndicePlanilla);

    /**
     * Función para consultar un índice de planilla OI a partir de su nombre y fecha FTP
     * @param nombreArchivo 
     *        Nombre del archivo a consultar
     * @param fechaFtp
     *        Fecha de modificación del archivo
     * @return IndicePlanilla
     *         Entrada de índice de planilla encontrado
     */
    public IndicePlanilla consultarPlanillaOIporNombreYFecha(String nombreArchivo, Date fechaFtp);

    /**
     * Función para la consulta de un índice de planilla de acuerdo a un número de planilla y un tipo de archivo
     * @param numeroPlanilla
     *        Número de la planilla a consultar
     * @param tipoPlanilla
     *        Tipo de la planilla por la que se almacenan los valores
     * @param codOperador
     *        Código del OI de la planilla
     * @return Indice de planilla solicitado o NULL
     */
    public IndicePlanilla consultarIndicePlanillaTipo(Long numeroPlanilla, TipoArchivoPilaEnum tipoPlanilla, String codOperador);

    /**
     * Procedimiento para el ingreso de un nuevo registro de lectura de archivo de planilla
     * @param registroNuevo
     *        DTO con la información del nuevo registro
     */
    public void registrarEnIndicePlanillas(IndicePlanilla registroNuevo);

    /**
     * Procedimiento para actualizar un registro de índice de planilla
     * @param registroModificado
     *        DTO con la información del registro modificado
     */
    public void actualizarIndicePlanillas(IndicePlanilla registroModificado);

    /**
     * Procedimiento para almacenar valores de un bloque de validación a otro
     * @param valores
     *        Instancia de paso de valores a persistir
     */
    public void almacenarVariables(PasoValores valores);

    /**
     * Este método se encarga de guardar las variables de paso entre bloques usando 
     * mecanismo de batch insert
     * 
     * @param valores
     *        Objeto con los valores a registrar
     * @param bloqueDestino
     *        Bloque de validación para el cual se almacena la variable
     * @param tipoArchivo
     *        Tipo de archivo por el cual se almacena la variable
     * @param numeroPlanilla
     *        Número de la planilla para la cual se almacena la variable
     * @param codOperador
     *        Código del Operador de Información que emite la planilla
     */
    public void almacenarVariablesBatch(Map<String, String[]> valores, BloqueValidacionEnum bloqueDestino, TipoArchivoPilaEnum tipoArchivo,
            Long numeroPlanilla, String codOperador);
    
    /**
     * Procedimiento para eliminar los valores de paso entre bloques
     * @param numeroPlanilla
     *        Número de la planilla de los valores almacenados
     * @param codOperador
     *        Código del OI de la planilla
     */
    public void eliminarVariables(Long numeroPlanilla, String codOperador);

    /**
     * Procedimiento para eliminar una entrada específica de paso de valores entre bloques
     * @param idPaso
     *        Id del registro de paso de valores
     */
    public void eliminarVariableEspecifica(Long idPaso);
    
    /**
     * Este método se encarga de eliminar variables especificas en batch
     * @param idPaso
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    public void eliminarVariableEspecificaBatch(String claveValor, List<PasoValores> valoresEliminar);

    // ----------------------------------------- ARCHIVO OPERADOR FINANCIERO ------------------------------------
    /**
     * Función para la consulta de un archivo OF en el indice
     * @param fechaPago
     *        Fecha de pago al OF
     * @param codBanco
     *        Código del banco que hace el recaudo
     * @return Listado de los registros del índice que concuerdan con el número de planilla suministrado
     */
    public IndicePlanillaOF consultarArchivoOFEnIndice(Date fechaPago, String codBanco);

    /**
     * Función para consultar una entrada de índice de Planilla OF por su ID
     * @param idIndicePlanilla
     *        Índice de planilla a consultar
     * @return IndicePlanillaOF objeto encontrado
     */
    public IndicePlanillaOF consultarPlanillaOFPorId(Long idIndicePlanilla);

    /**
     * Función para el ingreso de un nuevo registro de lectura de archivo de OF
     * @param registroNuevo
     *        DTO con la información del nuevo registro
     * @return DTO persistido con su respectivo ID
     */
    public IndicePlanillaOF registrarEnIndicePlanillasOF(IndicePlanillaOF registroNuevo);

    /**
     * Procedimiento para actualizar un registro de índice de planilla
     * @param registroModificado
     *        DTO con la información del registro modificado
     */
    public void actualizarIndicePlanillasOF(IndicePlanillaOF registroModificado);

    // -------------------------------------- CONSULTA DE INFORMACIÓN PERSISTIDA -----------------------------------
    /**
     * Función para obtener el contenido del registro tipo 1 de un archivo Ix
     * @param indicePlanilla
     *        Entrada de índice de la planilla seleccionada
     * @return Registro 1 del archivo solicitado o null
     */
    public Object consultarRegistro1ArchivoI(IndicePlanilla indicePlanilla);

    /**
     * Función para obtener el contenido del registro tipo 3 de un archivo Ix
     * @param idIndicePlanilla
     *        Entrada de índice de la planilla seleccionada
     * @return Registro 3 del archivo solicitado o null
     */
    public Object consultarRegistro3ArchivoI(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException;

    /**
     * Función para obtener el contenido de un registro tipo 6 de OF
     * @param numeroPlanilla
     *        Numero de la planilla solicitada
     * @param periodoPago
     *        Período de pago de la planilla
     * @param operadorInformacion
     *        Código del operador de información a través del cual se gestionó la planilla (String en OF, Integer en OI)
     * @return Registro 6 del archivo solicitado o null
     */
    public PilaArchivoFRegistro6 consultarRegistro6OF(Long numeroPlanilla, String periodoPago, Short operadorInformacion)
            throws ErrorFuncionalValidacionException;
    
    /**
     * Función para obtener el contenido de un registro tipo 6 de OF
     * @param numeroPlanilla
     *        Numero de la planilla solicitada
     * @param periodoPago
     *        Período de pago de la planilla
     * @param operadorInformacion
     *        Código del operador de información a través del cual se gestionó la planilla (String en OF, Integer en OI)
     * @return Registro 6 del archivo solicitado o null
     */
    public PilaArchivoFRegistro6 consultarRegistro6OFAlCargarF(Long numeroPlanilla, String periodoPago, Short operadorInformacion);

    /**
     * Función para consultar las entradas más recientes de una planilla por tipo de archivo
     * @param indicePlanilla
     *        Entrada de índide de planilla de referencia
     * @return Listado de las entradas anteriores de la misma planilla
     */
    public List<ElementoCombinatoriaArchivosDTO> consultarEntradasAnteriores(IndicePlanilla indicePlanilla);

    public List<ElementoCombinatoriaArchivosDTO> consultarEntradasAnterioresMigradas(IndicePlanilla indicePlanilla);

    /**
     * Procedimiento para eliminar un índice de planilla
     * @param indicePlanilla
     *        Entrada de índice de planilla de OI u OF para ser eliminado del sistema
     * @param usuarioEliminacion
     *        Usuario que solicita la eliminación
     * @param fechaEliminacion
     *        Fecha y hora en la que se realiza la eliminación
     */
    public Object eliminarIndicePlanilla(Object indicePlanilla, Date fechaEliminacion, String usuarioEliminacion);

    /**
     * Función para consultar el contenido del índice de planillas (OI - OF) de acuerdo a un estado
     * @param grupoArchivo
     *        Grupo al cual corresponde la busqueda a realizar
     * @param estado
     *        Estado para filtro
     * @return List<Object> Listado de entradas de índice de planilla
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en consultar los datos
     */
    public List<Object> consultarIndicePorEstado(GrupoArchivoPilaEnum grupoArchivo, EstadoProcesoArchivoEnum estado)
            throws ErrorFuncionalValidacionException;

    /**
     * Función para consultar el contenido del índice de planillas OI de acuerdo a un grupo de estados y de tipos de carga
     * @param estados
     *        Listado de los estados para filtro
     * @param tiposCarga
     *        Listado de tipos de carga para el filtro
     * @param codigoOI
     *        Parámetro opcional que indica un código específico de OI para la consulta de índices
     * @return List<IndicePlanilla> Listado de entradas de índice de planilla
     */
    public List<IndicePlanilla> consultarIndicePorEstadoMultipleOI(List<EstadoProcesoArchivoEnum> estados,
            List<TipoCargaArchivoEnum> tiposCarga, String codigoOI);

    /**
     * Función para consultar el contenido del índice de planillas OF de acuerdo a un grupo de estados
     * @param estados
     *        Listado de los estados para filtro
     * @return List<IndicePlanillaOF> Listado de entradas de índice de planilla
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en consultar los datos
     */
    public List<IndicePlanillaOF> consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum> estados)
            throws ErrorFuncionalValidacionException;

    /**
     * Procedimiento para agregar un nuevo registro de proceso
     * @param proceso
     *        Entrada de proceso PILA a ingresar a BD
     */
    public void registrarProceso(ProcesoPila proceso);

    /**
     * Procedimiento para actualizar un proceso PILA
     * @param procesoActualizado
     *        DTO del proceso con la información actualizada
     */
    public void actualizarProceso(ProcesoPila procesoAcualizado);

    /**
     * Función para consultar un proceso PILA por si ID
     * @param idProceso
     *        ID del proceso a consultar
     * @return ProcesoPila
     *         DTO del proceso consultado
     */
    public ProcesoPila consultarProcesoId(Long idProceso) throws ErrorFuncionalValidacionException;

    /**
     * Función para consultar los operadores de información
     * @return List<OperadorInformacion>
     *         Listado de operadores de información
     * @throws ErrorFuncionalValidacionException
     */
    public List<OperadorInformacion> consultarOperadoresInformacion();

    /**
     * Función para consultar los operadores de información específicos para una CCF
     * @param ccf
     *        Código de la CCF
     * @return List<OperadorInformacion>
     *         Listado de operadores de información válidos para la CCF
     * @throws ErrorFuncionalValidacionException
     */
    public List<OperadorInformacion> consultarOperadoresInformacionCcf(String ccf) throws ErrorFuncionalValidacionException;

    /**
     * Función para consultar las entradas de proceso de acuerdo a un estado específico
     * 
     * @param tipos
     *        Tipos de proceso consultados
     * @param estado
     *        Estado de referencia para consultar los procesos
     * @return List<ProcesoPila>
     *         Listado de los procesos consultados
     * @throws ErrorFuncionalValidacionException
     */
    public List<ProcesoPila> consultarProcesoEstado(List<TipoProcesoPilaEnum> tipos, EstadoProcesoValidacionEnum estado);

    /**
     * Este metodo se encarga de realizar la consulta de operador de informacion de acuerdo al objeto
     * @param idOperadorInformacion
     *        ID del operador de información a consultar
     * @return List<ConexionOperadorInformacion>
     *         Listado de DTOs que contienen la información de conexión por cada operador de información que cumple los criteris de búsqueda
     * @throws ErrorFuncionalValidacionException
     */
    public List<ConexionOperadorInformacion> consultarDatosConexionOperadorInformacion(Long idOperadorInformacion)
            throws ErrorFuncionalValidacionException;

    /**
     * Método encargado de consultar las configuraciones de ejecución programada de descarga, carga y validación de archivos
     * vigentes a la fecha actual
     * 
     * @param idCajaCompensacion
     *        Código SSF de la Caja de Compenasación de la cual se consulta la programación
     * @return List<EjecucionProgramada>
     *         Listado de las entradas de programación consultadas
     * @throws ErrorFuncionalValidacionException
     */
    public List<EjecucionProgramada> consultarProgramacion(String idCajaCompensacion) throws ErrorFuncionalValidacionException;

    /**
     * Método para consultar los registros tipo 2 de un índice
     * @param indicePlanilla
     *        Instancia de índice de planilla consultada
     * @return List<PilaArchivoIRegistro2>
     *         Listado de registros tipo 2 encontrados
     * @throws ErrorFuncionalValidacionException
     */
    public List<PilaArchivoIRegistro2> consultarRegistrosTipo2(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException;

    /**
     * Método para el registro de errores e inconsistencias en la base de datos
     * 
     * @param errorLog
     *        Entrada de log de errores para registrar
     */
    public void registrarError(List<ErrorValidacionLog> errorLog);

    /**
     * Función para consultar los operadores financieros
     * @return List<Banco>
     *         Listado de operadores financieros
     * @throws ErrorFuncionalValidacionException
     */
    public List<Banco> consultarOperadoresFinancieros() throws ErrorFuncionalValidacionException;

    /**
     * Función para consultar entradas anteriores de un índice de planilla con sus estdos por bloque
     * @param numeroPlanilla
     *        Número de planilla base para la consulta
     * @param codOperador
     *        Código del operador de información que emite el archivo
     * @return List<EstadoArchivoPorBloque>
     *         Listado de índices encontrados
     */
    public List<EstadoArchivoPorBloque> consultarEntradasAnterioresConEstado(Long numeroPlanilla, String codOperador);

    /**
     * Función que con base en un número de planilla, habilita planillas de corrección en espera
     * de una planilla original para PILA2
     * @param numeroPlanilla
     *        Número de la planilla referenciada
     * @param codOperador
     *        Código del operador de información que emite el archivo
     */
    public void activarCorreccionPila2(String numeroPlanilla, String codOperador);

    /**
     * Función que cambia el estado de gestión de las entradas de error de validación asociadas a un índice de planilla OI
     * @param idIndicePlanilla
     *        ID del índice de planilla del cual se desea dar las inconsistencias como gestionadas
     */
    public void cerrarInconsistencias(Long idIndicePlanilla);

    /**
     * Función para consultar los índices de planilla OI que correspondan con un listado de números de planilla
     * y presenten un estado inconcluso
     * @param listaPlanillas
     *        Listado de números de planilla a consultar
     * @param estados
     *        Listado de estados de archivo a consultar
     * @return <b>List<IndicePlanilla></b>
     *         Listado de los índices de planilla encontrados
     */
    public List<IndicePlanilla> consultarIndicesOIPorNumeroPlanilla(Set<String> listaPlanillas, List<EstadoProcesoArchivoEnum> estados);

    /**
     * Función para marcar como gestionadas las inconsistencias asociadas a un índice de planilla OI específico en un bloque de
     * validación específico
     * @param idIndicePlanilla
     *        Id de índice de planilla a gestionar
     * @param bloque
     *        Bloque para el cual se gestionan las inconsistencias
     * @param estado
     *        Estado de gestión para las inconsistencias
     */
    public void autogestionarInconsistenciasOIPorBloque(Long idIndicePlanilla, BloqueValidacionEnum bloque,
            EstadoGestionInconsistenciaEnum estado);

    /**
     * Método que inicia el SP encargado de verificar y actualizar el estado de registros tipo 6 asociados
     * @param idIndicePlanilla
     *        ID del índice de planilla OF recién cargada
     */
    public void ejecutarRevisionRegistrosTipo6(Long idIndicePlanilla);

    /**
     * Método encargado de consultar si un archivo corresponde a un aporte propio
     * @param id
     *        ID de índice de planilla consultado
     * @return <Boolean>
     *         Indicador para saber sí se trata de una aporte propio
     */
    public Boolean consultarAportePropio(Long id);

    /**
     * Método encargado de la persistencia de un log de excepción no controlada
     * @param log
     *        DTO con los datos del error a persistir
     */
    public void registrarLogError(LogErrorPilaM1ModeloDTO log);

    /**
     * Método encargado de la consulta de los índices de planilla que presentan estado "PENDIENTE_CONCILIACION"
     * @return
     */
    public List<IndicePlanilla> consultarIndicesPorConciliar(List<Long> ids);

    /**
     * Método encargado de la consulta de los índices de planilla por listado de IDs
     * @param ids
     * @return
     */
    public List<IndicePlanilla> consultarIndicesPorListaIds(List<Long> ids);

    /**
     * Método encargado de la consulta de los índices de planilla OF por listado de IDs
     * @param ids
     * @return
     */
    public List<IndicePlanillaOF> consultarIndicesPorListaIdsOF(List<Long> ids);

    /**
     * Método encargado de la actualización en batch de un listado de índices de planilla
     * @param indicesOI
     */
    public void actualizarListadoIndicePlanillas(List<IndicePlanilla> indicesOI);

    /**
     * Método encargado de la actualización en batch de un listado de índices de planilla OF
     * @param indicesOF
     */
    public void actualizarListadoIndicePlanillasOF(List<IndicePlanillaOF> indicesOF);

    /**
     * Metodo encargado de consultar solicitudes de cambio de ID de aportante por ID de planilla
     * @param idPlanilla
     * @return
     */
    public SolicitudCambioNumIdentAportante consultarSolicitudCambioId(Long idPlanilla);

    /**
     * Metodo encargado de rechazar automáticamente las solicitudes de cambio de ID de aportante
     * @param solicitud
     * @param usuarioAnulacion
     */
    public void rechazarSolicitudesCambioId(SolicitudCambioNumIdentAportante solicitud, String usuarioAnulacion);


    /**
     * Método encargado de determinar los archivos en FTP que no han sido cargados previamente en el índice de planillas
     * @param archivosFTP
     *        Listado de archivos a descargar del FTP
     * @param tipoArchivos
     *        Tipo de archivos esperado
     *        0 -> Operador Información
     *        1 -> Operador Financiero
     *        2 -> Ambos
     * @return
     */
    public List<ArchivoPilaDTO> filtrarExistentesPlanillas(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos);

    /**
     * Método encargado de determinar los archivos en FTP que no han sido cargados previamente en el índice de planillas
     * @param archivosFTP
     *        Listado de archivos a descargar del FTP
     * @param tipoArchivos
     *        Tipo de archivos esperado
     *        0 -> Operador Información
     *        1 -> Operador Financiero
     *        2 -> Ambos
     * @return
     */
    public List<ArchivoPilaDTO> filtrarExistentes(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos);
    
    /**
     * Método que se encarga de actualizar la información del registro 1 con el valor de la lista blanca
     * 
     * @param registro1
     * @param listaBlanca
     */
    public void actualizarRegistro1ConListasBlancasAportantes(Object registro1, ListasBlancasAportantes listaBlanca);
    
    /**
     * Método que se encarga de consultar si la planilla de pensaionados i de independientes tipo E existe
     *   
     * @param idPlanilla
     * @return
     */
    public IndicePlanilla consultarPlanillaAutomaticaPorId(Long idPlanilla);
    
    /**
     * Método que se encarga de consultar las planillas que deben reprocesarse
     *   
     * @param idPlanilla
     * @return
     */
    public List<Long> consultarPlanillasReproceso();

    /**
     * Método que se encarga de consultar las planillas que deben reprocesarse
     *   
     * @param idPlanilla
     * @return
     */
    public List<PilaArchivoFRegistro6> consultarPlanillasPendientesPorConciliacionF();

    public void actualizarConciliacionPlanillasF(List<PilaArchivoFRegistro6> planillasF);

    /**
     * Método que consulta las planillas asociadas a la tabla PilaArchivoOF
     * @return las pilaIndicePlanillas asociadas
     */
    public List<Long> consultarPlanillasOIPorF(List<PilaArchivoFRegistro6> planillasOF);

    public void eliminarPersistenciasPila(Long idIndicePlanilla);

    public List<Long> consultarPlanillasOIPorPlanillas(List<Long> lstIdPlanillas);

    public void anularPlanillasReproceso(IndicePlanilla indicePlanilla);

    public boolean anularPlanillas(IndicePlanilla indicePlanilla);

       /**
     * Método que se encarga de consultar las planillas que deben pasar directo a B8
     *   
     * @param idPlanilla
     * @return
     */
    public List<Long> consultarPlanillasConciliadasB6aB8();

}
