package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.core.OperadorInformacionCcf;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.ccf.general.EjecucionProgramada;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro3;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro2;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro3;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.entidades.pila.soporte.LogErrorPilaM1;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.entidades.pila.soporte.ProcesoPila;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.pila.AccionCorreccionPilaEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoValidacionEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.constants.MensajesPersistenciaDatosEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.dto.ComprobacionArchivoFtpDTO;
import com.asopagos.pila.dto.ElementoCombinatoriaArchivosDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;

/**
 * <b>Descripción:</b> Clase que se encarga de el registro y consulta de información relacionada
 * con el procesamiento de los archivos PILA, principalmente índices de planillas OI y OF<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393<br>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:jpiraban@heinsohn.com.co">Jhon Angel Piraban Castellanos </a>
 */
@Stateless
public class PersistenciaDatosValidadores implements IPersistenciaDatosValidadores, Serializable {
    private static final long serialVersionUID = 1L;

    private final int BATCH_SIZE = 300;
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(PersistenciaDatosValidadores.class);

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "archivosPila_PU")
    private EntityManager entityManager;

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;
    
    /** Constante para el mensaje de índice de planilla */
    private static final String INDICE_PLANILLA = "índice de planilla";
    /** Constante para el mensaje de procesos */
    private static final String PROCESO = "procesos";
    /** Constante para el mensaje de registro tipo 2 OI */
    private static final String REGISTRO_2_OI = "registros Tipo 2 de Operador de Información";
    /** Constante para el mensaje de registro tipo 3 OI */
    private static final String REGISTRO_3_OI = "registro Tipo 3 de Operador de Información";
    /** Constante para el mensaje de registro tipo 6 OF */
    private static final String REGISTRO_6_OF = "registros Tipo 6 de Operador Financiero";
    /** Constante para el mensaje de operador de información */
    private static final String OPERADOR_INFORMACION = "Operadores de Información";
    /** Constante para el mensaje de operador de información */
    private static final String OPERADOR_FINANCIERO = "Operadores Financieros";
    /** Constante para el mensaje de parámetros de conexión con el operador de información */
    private static final String CONEXION_OI = "parámetros de conexión para el(los) Operador(es) de Información";
    /** Constante para el mensaje de configuración de procesamiento programado */
    private static final String EJECUCION_PROGRAMADA = "configuración de procesamiento programado";

    /** Constante con el nombre del SP de revisión de registros tipo 6 duplicados */
    private static final String USP_INICIAR_REVISION_REGISTRO_6_DUPLICADO = "USP_ValidateHU407Registro6Duplicado";

    /** Consulta para determinar que un archivo PILA representa un aporte propio de independientes o pensionados */
    private static final String USP_CONSULTAR_APORTE_PROPIO = "USP_ValidateHU391EsAportePropio";

    private static final String USP_ELIMINAR_PERSISTENCIAS_PILA = "ASP_PILA_REPROCESO";

    /**
     * Procedimiento para agregar un nuevo registro de proceso
     * @param proceso
     *        Entrada de proceso PILA a ingresar a BD
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarProceso(ProcesoPila proceso) {
        logger.warn("Inicia registrarProceso(ProcesoPila)");
        try {
            logger.warn("proceso a persistir");
            logger.warn(proceso.toString());
            entityManager.persist(proceso);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PROCESO.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarProceso(ProcesoPila) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.warn("Finaliza registrarProceso(ProcesoPila)");
    }

    /**
     * Procedimiento para actualizar un proceso PILA
     * @param procesoActualizado
     *        DTO del proceso con la información actualizada
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarProceso(ProcesoPila procesoAcualizado) {
        logger.debug("Inicia actualizarProceso(ProcesoPila)");
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_PROCESO_PILA).setParameter("id", procesoAcualizado.getId())
                    .setParameter("numeroRadicado", procesoAcualizado.getNumeroRadicado())
                    .setParameter("tipoProceso", procesoAcualizado.getTipoProceso())
                    .setParameter("fechaInicioProceso", procesoAcualizado.getFechaInicioProceso())
                    .setParameter("fechaFinProceso", procesoAcualizado.getFechaFinProceso())
                    .setParameter("usuarioProceso", procesoAcualizado.getUsuarioProceso())
                    .setParameter("estadoProceso", procesoAcualizado.getEstadoProceso()).executeUpdate();

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PROCESO.getReadableMessage(e.getMessage());
            logger.error("Finaliza actualizarProceso(ProcesoPila) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza actualizarProceso(ProcesoPila)");
    }

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
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object> consultarIndicePorEstado(GrupoArchivoPilaEnum grupoArchivo, EstadoProcesoArchivoEnum estado)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarIndicePorEstado(GrupoArchivoPilaEnum, EstadoProcesoArchivoEnum)");
        List<Object> result = null;
        try {
            if (GrupoArchivoPilaEnum.OPERADOR_FINANCIERO.equals(grupoArchivo)) {
                result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_OF_ESTADO).setParameter("estado", estado)
                        .getResultList();
            }
            else {
                result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_OI_ESTADO).setParameter("estado", estado)
                        .getResultList();
            }

            logger.debug("Finaliza consultarIndicePorEstado(GrupoArchivoPilaEnum, EstadoProcesoArchivoEnum)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(INDICE_PLANILLA);
            logger.debug("Finaliza consultarIndicePorEstado(GrupoArchivoPilaEnum, EstadoProcesoArchivoEnum) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarIndicePorEstado(GrupoArchivoPilaEnum, EstadoProcesoArchivoEnum) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarProcesoId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ProcesoPila consultarProcesoId(Long idProceso) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarProcesoId(Long)");

        ProcesoPila result = null;

        try {
            result = (ProcesoPila) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PROCESO_PILA_ID)
                    .setParameter("id", idProceso).setMaxResults(1).getSingleResult();

            logger.debug("Finaliza consultarProcesoId(Long)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(PROCESO);
            logger.debug("Finaliza consultarProcesoId(Long) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_PROCESO.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarProcesoId(Long) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /**
     * Función para consultar una entrada de índice de Planilla por su ID
     * @param idIndicePlanilla
     *        Índice de planilla a consultar
     * @return IndicePlanilla objetos encontrados
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IndicePlanilla consultarPlanillaOIPorId(Long idIndicePlanilla) {
        logger.debug("Inicia consultarPlanillaOIPorId(Long)");
        IndicePlanilla resultado = null;

        resultado = (IndicePlanilla) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_ID)
                .setParameter("idIndicePlanilla", idIndicePlanilla).setMaxResults(1).getSingleResult();

        logger.debug("Finaliza consultarPlanillaOIPorId(Long)");
        return resultado;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarPlanillaOIporNombreYFecha(java.lang.String,
     * java.util.Date)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IndicePlanilla consultarPlanillaOIporNombreYFecha(String nombreArchivo, Date fechaFtp) {
        logger.debug("Inicia consultarPlanillaOIporNombreYFecha(String, Date)");

        IndicePlanilla resultado;
        try {
            resultado = (IndicePlanilla) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_FECHA)
                    .setParameter("nombreArchivo", nombreArchivo).setParameter("fechaFtp", fechaFtp).setMaxResults(1).getSingleResult();

            logger.debug("Finaliza consultarPlanillaOIporNombreYFecha(String, Date)");
        } catch (NoResultException e) {
            resultado = null;
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarPlanillaOIporNombreYFecha(String, Date) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarPlanillaOIporNombreYFecha(String, Date)");
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarIndicePlanillaTipo(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IndicePlanilla consultarIndicePlanillaTipo(Long numeroPlanilla, TipoArchivoPilaEnum tipoPlanilla, String codOperador) {
        logger.debug("Inicia consultarIndicePlanillaTipo(Long, TipoArchivoPilaEnum, String)");
        IndicePlanilla indicePlanilla = null;

        indicePlanilla = (IndicePlanilla) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_TIPO)
                .setParameter("numeroPlanilla", numeroPlanilla).setParameter("tipoArchivo", tipoPlanilla)
                .setParameter("codOperador", codOperador).setMaxResults(1).getSingleResult();

        logger.debug("Finaliza consultarIndicePlanillaTipo(Long, TipoArchivoPilaEnum)");
        return indicePlanilla;
    }

    /**
     * Este método se encarga de guardar el índice de la planilla
     * @param IndicePlanilla
     *        objeto que se va a registrar
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en registrar los datos
     */

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarEnIndicePlanillas(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia registrarEnIndicePlanillas(IndicePlanilla)");
        try {
            entityManager.persist(indicePlanilla);

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarEnIndicePlanillas(IndicePlanilla) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.debug("Finaliza registrarEnIndicePlanillas(IndicePlanilla)");
    }

    /**
     * Este método se encarga de guardar el índice de la planilla
     * @param IndicePlanilla
     *        objeto que se va a actualizar
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en actualizar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarIndicePlanillas(IndicePlanilla registroModificado) {
        logger.info("Inicia actualizarIndicePlanillas(IndicePlanilla) " + registroModificado.getId() +" - " + registroModificado.getEstadoArchivo());
        registroModificado = entityManager.merge(registroModificado);
        //        try {
        //            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_REGISTRO_INDICE).setParameter("id", registroModificado.getId())
        //                    .setParameter("fechaProceso", registroModificado.getFechaProceso())
        //                    .setParameter("usuarioProceso", registroModificado.getUsuarioProceso())
        //                    .setParameter("estadoArchivo", registroModificado.getEstadoArchivo())
        //                    .setParameter("enLista", registroModificado.getEnLista()).executeUpdate();
        //
        //        } catch (Exception e) {
        //            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INDICE.getReadableMessage(e.getMessage());
        //            logger.error("Finaliza actualizarIndicePlanillas(IndicePlanilla) - " + mensaje);
        //            throw new TechnicalException(mensaje, e);
        //        }
        logger.debug("Finaliza actualizarIndicePlanillas(IndicePlanilla)");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void almacenarVariablesBatch(Map<String, String[]> valores, BloqueValidacionEnum bloqueDestino, TipoArchivoPilaEnum tipoArchivo,
            Long numeroPlanilla, String codOperador) {
        String firmaMetodo = "almacenarVariablesBatch(Map<String, String[]>, BloqueValidacionEnum, TipoArchivoPilaEnum, Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            int i = 1;
            for (Map.Entry<String, String[]> valor : valores.entrySet()) {
                
                if (i > 0 && i % BATCH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
         
                PasoValores pasoValores = new PasoValores();
                pasoValores.setIdPlanilla(numeroPlanilla);
                pasoValores.setCodOperadorInf(codOperador);
                pasoValores.setTipoPlanilla(tipoArchivo);
                pasoValores.setBloque(bloqueDestino);
    
                pasoValores.setNombreVariable(valor.getKey());
                pasoValores.setValorVariable(valor.getValue()[1]);
                pasoValores.setCodigoCampo(valor.getValue()[0]);
                
                entityManager.persist(pasoValores);
                i++;
            }
            
            entityManager.flush();
            
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PASO_VALORES.getReadableMessage(e.getMessage());
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }         
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void almacenarVariables(PasoValores valores) {
        logger.debug("Inicia almacenarVariables(PasoValores)");

        try {
            entityManager.persist(valores);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PASO_VALORES.getReadableMessage(e.getMessage());
            logger.error("Finaliza almacenarVariables(PasoValores) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        
        logger.debug("Finaliza almacenarVariables(PasoValores)");
    }

    /**
     * Este método se encarga de eliminar las variables
     * @param numeroPlanilla
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarVariables(Long numeroPlanilla, String codOperador) {
        logger.debug("Inicia eliminarVariables(Long, String)");

        entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_VARIABLES_DE_PASO).setParameter("idPlanilla", numeroPlanilla)
                .setParameter("codOperador", codOperador).executeUpdate();

        logger.debug("Finaliza eliminarVariables(Long, String)");
    }

    /**
     * Este método se encarga de eliminar una variable especifica
     * @param idPaso
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarVariableEspecifica(Long idPaso) {
        logger.debug("Inicia eliminarVariableEspecifica(Integer)");
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_VARIABLE_DE_PASO_ESPECIFICA).setParameter("id", idPaso)
                    .executeUpdate();

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PASO_VALORES.getReadableMessage(e.getMessage());
            logger.error("Finaliza eliminarVariableEspecifica(Integer) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.debug("Finaliza eliminarVariableEspecifica(Integer)");
    }

    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarVariableEspecificaBatch(String claveValor, List<PasoValores> valoresEliminar) {
        logger.debug("Inicia eliminarVariableEspecificaBatch(List<PasoValores>)");
        
        try {
            int i = 1;
            for (PasoValores pasoValores : valoresEliminar) {
                if (pasoValores.getNombreVariable().equals(claveValor) && 
                        i > 0 && i % BATCH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.remove(pasoValores);
                i++;
            }
            entityManager.flush();
            
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_PASO_VALORES.getReadableMessage(e.getMessage());
            logger.error("Finaliza eliminarVariableEspecifica(Integer) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.debug("Finaliza eliminarVariableEspecificaBatch(List<PasoValores>)");
    }
    
    /**
     * Este método se encarga de consultar el archivo operador financiero
     * @param fechaPago
     *        fecha de pago
     * @param codBanco
     *        codigo del banco
     * @return IndicePlanillaOF objeto consultado
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public IndicePlanillaOF consultarArchivoOFEnIndice(Date fechaPago, String codBanco) {
        logger.debug("Inicia consultarArchivoOFEnIndice(Date, String)");

        IndicePlanillaOF result = null;
        result = (IndicePlanillaOF) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_ARCHIVO_OF)
                .setParameter("pipofFechaPago", fechaPago).setParameter("pipofCodigoBancoRecaudador", codBanco).setMaxResults(1)
                .getSingleResult();

        logger.debug("Finaliza consultarArchivoOFEnIndice(Date, String)");
        return result;
    }

    /**
     * Función para consultar una entrada de índice de Planilla OF por su ID
     * @param idIndicePlanilla
     *        Índice de planilla a consultar
     * @return IndicePlanilla objetos encontrados
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en consultar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public IndicePlanillaOF consultarPlanillaOFPorId(Long idIndicePlanilla) {
        logger.debug("Inicia consultarPlanillaOIPorId(Long)");
        IndicePlanillaOF resultado = null;

        resultado = (IndicePlanillaOF) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_POR_ID_OF)
                .setParameter("idIndicePlanilla", idIndicePlanilla).setMaxResults(1).getSingleResult();

        logger.debug("Finaliza consultarPlanillaOIPorId(Long)");

        return resultado;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public IndicePlanillaOF registrarEnIndicePlanillasOF(IndicePlanillaOF registroNuevo) {
        logger.debug("Inicia registrarEnIndicePlanillasOF(IndicePlanillaOF)");
        try {
            entityManager.persist(registroNuevo);

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarEnIndicePlanillasOF(IndicePlanillaOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.debug("Finaliza registrarEnIndicePlanillasOF(IndicePlanillaOF)");
        return registroNuevo;
    }

    /**
     * Este método se encarga de actualizar índice planilla operador financiero
     * @param IndicePlanillaOF
     *        objeto que va hacer persistido
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarIndicePlanillasOF(IndicePlanillaOF registroModificado) {
        logger.debug("Inicia actualizarIndicePlanillasOF(IndicePlanillaOF)");
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_REGISTRO_INDICE_OF)
                    .setParameter("id", registroModificado.getId()).setParameter("fechaProceso", registroModificado.getFechaProceso())
                    .setParameter("usuarioProceso", registroModificado.getUsuarioProceso())
                    .setParameter("estado", registroModificado.getEstado()).executeUpdate();

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza actualizarIndicePlanillasOF(IndicePlanillaOF) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        logger.debug("Finaliza actualizarIndicePlanillasOF(IndicePlanillaOF)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarRegistro1ArchivoI(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object consultarRegistro1ArchivoI(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia consultarRegistro1ArchivoI(IndicePlanilla)");

        if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
            try {
                if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(indicePlanilla.getTipoArchivo().getGrupo())) {
                    PilaArchivoIRegistro1 result = null;
                    result = (PilaArchivoIRegistro1) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_I_REGISTRO_1)
                            .setParameter("indicePlanilla", indicePlanilla).setMaxResults(1).getSingleResult();

                    logger.debug("Finaliza consultarRegistro1ArchivoI(IndicePlanilla)");
                    return result;
                }
                else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(indicePlanilla.getTipoArchivo().getGrupo())) {
                    PilaArchivoIPRegistro1 result = null;
                    result = (PilaArchivoIPRegistro1) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_IP_REGISTRO_1)
                            .setParameter("indicePlanilla", indicePlanilla).setMaxResults(1).getSingleResult();

                    logger.debug("Finaliza consultarRegistro1ArchivoI(IndicePlanilla)");
                    return result;
                }
            } catch (NoResultException e) {
                logger.debug("Finaliza consultarRegistro1ArchivoI(IndicePlanilla)");
                return null;
            }
        }
        logger.debug("Finaliza consultarRegistro1ArchivoI(IndicePlanilla)");
        return null;
    }

    /**
     * Este método se encarga de consultar el resgistro del tipo de archivo independiente
     * de acuerdo al índice de la planilla
     * @param IndicePlanillaOF
     *        objeto que va hacer persistido
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object consultarRegistro3ArchivoI(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarRegistro3ArchivoI(IndicePlanilla)");

        if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(indicePlanilla.getTipoArchivo().getSubtipo())) {
            try {
                if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(indicePlanilla.getTipoArchivo().getGrupo())) {
                    PilaArchivoIRegistro3 result = null;
                    result = (PilaArchivoIRegistro3) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_I_REGISTRO_3)
                            .setParameter("indicePlanilla", indicePlanilla).setMaxResults(1).getSingleResult();

                    logger.debug("Finaliza consultarRegistro3ArchivoI(IndicePlanilla)");
                    return result;
                }
                else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(indicePlanilla.getTipoArchivo().getGrupo())) {
                    PilaArchivoIPRegistro3 result = null;
                    result = (PilaArchivoIPRegistro3) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_IP_REGISTRO_3)
                            .setParameter("indicePlanilla", indicePlanilla).setMaxResults(1).getSingleResult();

                    logger.debug("Finaliza consultarRegistro3ArchivoI(IndicePlanilla)");
                    return result;
                }
            } catch (NoResultException e) {
                String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(REGISTRO_3_OI);
                logger.debug("Finaliza consultarRegistro3ArchivoI(IndicePlanilla) - " + mensaje);
                throw new ErrorFuncionalValidacionException(mensaje, e);
            } catch (Exception e) {
                String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_REGISTRO.getReadableMessage("3",
                        indicePlanilla.getTipoArchivo().getDescripcion(), e.getMessage());
                logger.error("Finaliza consultarRegistro3ArchivoI(IndicePlanilla) - " + mensaje);
                throw new TechnicalException(mensaje, e);
            }
        }
        logger.debug("Finaliza consultarRegistro3ArchivoI(IndicePlanilla)");
        return null;
    }

    /**
     * Este método se encarga de consultar el registro del tipo de archivo operador financiero
     * de acuerdo al índice de la planilla, período de pago y operador de información
     * @param numeroPlanilla
     * @param periodoPago
     * @param operadorInformacion
     * @return PilaArchivoFRegistro6 objeto encontrado
     * @exception ErrorFuncionalValidacionException
     *            excepcion lanzada por error en guardar los datos
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PilaArchivoFRegistro6 consultarRegistro6OF(Long numeroPlanilla, String periodoPago, Short operadorInformacion)
            throws ErrorFuncionalValidacionException {
        String firmaMetodo = "consultarRegistro6OF(String, String, Integer, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PilaArchivoFRegistro6 result = null;

        // se elimina el guión del período
        if (periodoPago.contains("-")) {
            periodoPago = periodoPago.replace("-", "");
        }

        try {
            result = (PilaArchivoFRegistro6) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_F_REGISTRO_6)
                    .setParameter("numeroPlanilla", Long.toString(numeroPlanilla)).setParameter("periodoPago", periodoPago)
                    .setParameter("operadorInformacion", operadorInformacion).setMaxResults(1).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(REGISTRO_6_OF);
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_REGISTRO.getReadableMessage("6",
                    TipoArchivoPilaEnum.ARCHIVO_OF.getDescripcion(), e.getMessage());
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarRegistro6OFAlCargarF(java.lang.Long,java.lang.String,java.lang.Short)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PilaArchivoFRegistro6 consultarRegistro6OFAlCargarF(Long numeroPlanilla, String periodoPago, Short operadorInformacion) {
        String firmaMetodo = "consultarRegistro6OF(String, String, Integer, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PilaArchivoFRegistro6 result = null;

        // se elimina el guión del período
        if (periodoPago.contains("-")) {
            periodoPago = periodoPago.replace("-", "");
        }
        
        try {
        result = (PilaArchivoFRegistro6) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_F_REGISTRO_6_AL_CARGAR_F)
                .setParameter("numeroPlanilla", numeroPlanilla).setParameter("periodoPago", periodoPago)
                .setParameter("operadorInformacion", operadorInformacion).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
			result = null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarEntradasAnteriores(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoCombinatoriaArchivosDTO> consultarEntradasAnteriores(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia consultarEntradasAnteriores(IndicePlanilla)");
        logger.info("consultarEntradasAnteriores");
        logger.info("idPlanilla: " + indicePlanilla.getIdPlanilla());
        logger.info("idIndice: " + indicePlanilla.getId());
        logger.info("nombreArchivo: " + indicePlanilla.getNombreArchivo());
        


        // Se consultan los archivos cargados asociados al numero de planilla
        List<ElementoCombinatoriaArchivosDTO> result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_CARGADOS_Y_EJECUTADOS_POR_PLANILLA,
                        ElementoCombinatoriaArchivosDTO.class)
                .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
                .setParameter("idIndice", indicePlanilla.getId())
                .setParameter("codOperador", indicePlanilla.getCodigoOperadorInformacion())
                .setParameter("nombreArchivo", indicePlanilla.getNombreArchivo())
                .getResultList();

        logger.debug("Finaliza consultarEntradasAnteriores(IndicePlanilla)");
        return result;
    }
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void anularPlanillasReproceso(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia anularPlanillasReproceso(IndicePlanilla)");
        logger.info("anularPlanillasReproceso");

        // Se consultan los archivos cargados asociados al numero de planilla
        entityManager
                .createNamedQuery(NamedQueriesConstants.ANULAR_PLANILLAS_REPROCESO)
                .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
                .executeUpdate();

        entityManager
                .createNamedQuery(NamedQueriesConstants.ANULAR_PLANILLAS_REPROCESO_ESTADO_BLOQUE)
                .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
                .executeUpdate();

                

        logger.debug("Finaliza anularPlanillasReproceso(IndicePlanilla)");
        
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean anularPlanillas(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia anularPlanillas(IndicePlanilla)");
        logger.info("anularPlanillas");

        
        int anular = (int) entityManager
            .createNamedQuery(NamedQueriesConstants.ANULAR_PLANILLAS_CONDICION)
            .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
            .getSingleResult();
        // Se consultan los archivos cargados asociados al numero de planilla
            entityManager
            .createNamedQuery(NamedQueriesConstants.ANULAR_PLANILLAS)
            .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
            .executeUpdate();
            logger.info("anular: " + anular);
            if(anular == 1){
                return true;
            }
                return false;
            
                
    }


    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarEntradasAnterioresMigradas(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoCombinatoriaArchivosDTO> consultarEntradasAnterioresMigradas(IndicePlanilla indicePlanilla) {
        logger.debug("Inicia consultarEntradasAnterioresMigradas(IndicePlanilla)");
        logger.info("consultarEntradasAnterioresMigradas");
        logger.info("idPlanilla: " + indicePlanilla.getIdPlanilla());
        logger.info("idIndice: " + indicePlanilla.getId());
        logger.info("nombreArchivo: " + indicePlanilla.getNombreArchivo());
        logger.info("codigoOperadorInformacion: " + indicePlanilla.getCodigoOperadorInformacion());

        // Se consultan los archivos cargados asociados al numero de planilla
        List<ElementoCombinatoriaArchivosDTO> result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVOS_CARGADOS_Y_EJECUTADOS_POR_PLANILLA_MIGRADAS,
                        ElementoCombinatoriaArchivosDTO.class)
                .setParameter("idPlanilla", indicePlanilla.getIdPlanilla())
                .setParameter("idIndice", indicePlanilla.getId())
                .setParameter("nombreArchivo", indicePlanilla.getNombreArchivo())
                .setParameter("codigoOperadorInformacion", indicePlanilla.getCodigoOperadorInformacion())
                .getResultList();

        logger.debug("Finaliza consultarEntradasAnterioresMigradas(IndicePlanilla)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#eliminarIndicePlanilla(java.lang.Object)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object eliminarIndicePlanilla(Object indicePlanilla, Date fechaEliminacion, String usuarioEliminacion) {
        logger.debug("Inicia eliminarIndicePlanilla(Object)");

        Object indiceTemp = null;

        try {
            //            Long id = null;
            //se verifica si el indice planilla enviado esta relacionado con la entidad IndicePlanilla si es asi se procede a anularlo
            if (indicePlanilla instanceof IndicePlanilla) {
                IndicePlanilla indice = entityManager.merge((IndicePlanilla) indicePlanilla);
                indice.setRegistroActivo(Boolean.FALSE);
                indice.setFechaEliminacion(fechaEliminacion);
                indice.setUsuarioEliminacion(usuarioEliminacion != null ? usuarioEliminacion : "USUARIO_NO_IDENTIFICADO");
                indice.setEstadoArchivo(EstadoProcesoArchivoEnum.ANULADO);

                indice.setFechaUpdate(new Date());
                indiceTemp = indice;

                //                id = ((IndicePlanilla) indicePlanilla).getId();
                //
                //                entityManager.createNamedQuery(NamedQueriesConstants.INACTIVAR_INDICE_OI).setParameter("idIndicePlanilla", id)
                //                        .setParameter("fechaEliminacion", fechaEliminacion)
                //                        .setParameter("usuarioEliminacion", usuarioEliminacion != null ? usuarioEliminacion : "USUARIO_NO_IDENTIFICADO")
                //                        .setParameter("estado", EstadoProcesoArchivoEnum.ANULADO).executeUpdate();
            }
            //se verifica si el indice planilla enviado esta relacionado con la entidad IndicePlanillaOF si es asi se procede a anularlo
            else if (indicePlanilla instanceof IndicePlanillaOF) {
                IndicePlanillaOF indice = entityManager.merge((IndicePlanillaOF) indicePlanilla);
                indice.setRegistroActivo(Boolean.FALSE);
                indice.setFechaEliminacion(fechaEliminacion);
                indice.setUsuarioEliminacion(usuarioEliminacion != null ? usuarioEliminacion : "USUARIO_NO_IDENTIFICADO");
                indice.setEstado(EstadoProcesoArchivoEnum.ANULADO);

                indiceTemp = indice;

                //                id = ((IndicePlanillaOF) indicePlanilla).getId();
                //
                //                entityManager.createNamedQuery(NamedQueriesConstants.INACTIVAR_INDICE_OF).setParameter("idIndicePlanilla", id)
                //                        .setParameter("fechaEliminacion", fechaEliminacion)
                //                        .setParameter("usuarioEliminacion", usuarioEliminacion != null ? usuarioEliminacion : "USUARIO_NO_IDENTIFICADO")
                //                        .setParameter("estado", EstadoProcesoArchivoEnum.ANULADO).executeUpdate();
            }

        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza eliminarIndicePlanilla(Object) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza eliminarIndicePlanilla(Object)");
        return indiceTemp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarOperadoresInformacion()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<OperadorInformacion> consultarOperadoresInformacion() {
        logger.debug("Inicia consultarOperadoresInformacion()");

        List<OperadorInformacion> result = null;

        try {
            //se realiza una consulta para obtener todos los operadores de informacion activos
            result = (List<OperadorInformacion>) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADORES_INFORMACION_ACTIVOS).getResultList();

            logger.debug("Finaliza consultarOperadoresInformacion()");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(OPERADOR_INFORMACION);
            logger.debug("Finaliza consultarOperadoresInformacion() - " + mensaje);
            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_OPERADOR_INFORMACION.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarOperadoresInformacion() - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarOperadoresInformacionCcf(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    //    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<OperadorInformacion> consultarOperadoresInformacionCcf(String ccf) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarOperadoresInformacionCcf(String)");

        List<OperadorInformacion> result = null;

        try {

            // si no se recibe un código de ccf, se consultan todas los OI
            if (ccf == null || ccf.isEmpty()) {
                result = consultarOperadoresInformacion();
            }
            else {
                result = new ArrayList<OperadorInformacion>();

                List<OperadorInformacionCcf> listado = (List<OperadorInformacionCcf>) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADORES_INFORMACION_ACTIVOS_CCF).setParameter("codigoCcf", ccf)
                        .getResultList();

                if (listado != null) {
                    for (OperadorInformacionCcf operadorInformacionCcf : listado) {
                        result.add(operadorInformacionCcf.getOperadorInformacion());
                    }
                }
            }

            logger.debug("Finaliza consultarOperadoresInformacionCcf(String)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(OPERADOR_INFORMACION);
            logger.debug("Finaliza consultarOperadoresInformacionCcf(String) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_OPERADOR_INFORMACION.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarOperadoresInformacionCcf(String) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarOperadoresInformacion()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConexionOperadorInformacion> consultarDatosConexionOperadorInformacion(Long idOperadorInformacion)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarDatosConexionOperadorInformacion(OperadorInformacion)");

        List<ConexionOperadorInformacion> result = null;

        try {
            //se verifica si existe el id de operador de informacion ,si no se buscan todos los operadores
            if (idOperadorInformacion != null) {

                result = (List<ConexionOperadorInformacion>) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETROS_OPERADOR_INFORMACION_ACTIVO_ESPECIFICO)
                        .setParameter("idOperador", idOperadorInformacion).getResultList();
            }
            else {

                result = (List<ConexionOperadorInformacion>) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETROS_OPERADOR_INFORMACION_ACTIVOS).getResultList();
            }

            logger.debug("Finaliza consultarDatosConexionOperadorInformacion(OperadorInformacion)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(CONEXION_OI);
            logger.debug("Finaliza consultarDatosConexionOperadorInformacion(OperadorInformacion) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_OPERADOR_INFORMACION.getReadableMessage(e.getMessage());
            logger.debug("Finaliza consultarDatosConexionOperadorInformacion(OperadorInformacion) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#concultarProcesoEstado(com.asopagos.enumeraciones.pila.
     * EstadoProcesoValidacionEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ProcesoPila> consultarProcesoEstado(List<TipoProcesoPilaEnum> tipos, EstadoProcesoValidacionEnum estado) {
        logger.debug("Inicia concultarProcesoEstado(List<TipoProcesoPilaEnum>, EstadoProcesoValidacionEnum)");
        //se realiza una busqueda del proceso dependiendo del estado que sea enviado
        List<ProcesoPila> result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PROCESO_POR_ESTADO, ProcesoPila.class)
                .setParameter("estado", estado).setParameter("tipos", tipos).getResultList();

        logger.debug("Finaliza concultarProcesoEstado(EstadoProcesoValidacionEnum)");
    
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarIndicePorEstadoMultipleOF(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanillaOF> consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum> estados)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum>)");
        List<IndicePlanillaOF> result = null;
        try {
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_OF_ESTADO_MULTIPLE)
                    .setParameter("estados", estados).getResultList();

            logger.debug("Finaliza consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum>)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(INDICE_PLANILLA);
            logger.debug("Finaliza consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum>) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_INDICE.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarIndicePorEstadoMultipleOF(List<EstadoProcesoArchivoEnum>) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarIndicePorEstadoMultipleOI(java.util.List,
     * java.util.List, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanilla> consultarIndicePorEstadoMultipleOI(List<EstadoProcesoArchivoEnum> estados,
            List<TipoCargaArchivoEnum> tiposCarga, String codigoOI) {
        logger.debug("Inicia consultarIndicePorEstadoMultipleOI(List<EstadoProcesoArchivoEnum>, List<TipoCargaArchivoEnum>, String)");
        List<IndicePlanilla> result = null;
        // sí los tipos de carga no se especifican, se debe buscar por todos los estados
        if (tiposCarga == null) {
            tiposCarga = new ArrayList<TipoCargaArchivoEnum>();

            for (TipoCargaArchivoEnum tipoCarga : TipoCargaArchivoEnum.values()) {
                tiposCarga.add(tipoCarga);
            }
        }

        // sí el OI no se especifica, se buscan todos
        if (codigoOI != null && !codigoOI.isEmpty()) {
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_OI_ESPECIFICO_ESTADO_MULTIPLE)
                    .setParameter("estados", estados).setParameter("tiposCarga", tiposCarga).setParameter("codigoOI", codigoOI)
                    .getResultList();
        }
        else {
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_OI_ESTADO_MULTIPLE)
                    .setParameter("estados", estados).setParameter("tiposCarga", tiposCarga).getResultList();
        }

        logger.debug("Finaliza consultarIndicePorEstadoMultipleOI(List<EstadoProcesoArchivoEnum>, List<TipoCargaArchivoEnum>, String)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarProgramacion(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EjecucionProgramada> consultarProgramacion(String cajaCompensacion) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarProgramacion(String)");

        List<EjecucionProgramada> result = null;
        Date fechaActual = Calendar.getInstance().getTime();

        try {
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETROS_EJECUCION_ACTUAL)
                    .setParameter("fechaActual", fechaActual).setParameter("cajaCompensacion", cajaCompensacion).getResultList();

            logger.debug("Finaliza consultarProgramacion(String)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(EJECUCION_PROGRAMADA);
            logger.debug("Finaliza consultarProgramacion(String) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_PARAMETROS_PROGRAMACION.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarProgramacion(String) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadoresInterface#consultarRegistrosTipo2(com.asopagos.entidades.pila.
     * IndicePlanilla)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PilaArchivoIRegistro2> consultarRegistrosTipo2(IndicePlanilla indicePlanilla) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarRegistrosTipo2(IndicePlanilla)");

        List<PilaArchivoIRegistro2> result = null;

        try {
            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_I_REGISTRO_2)
                    .setParameter("indicePlanilla", indicePlanilla).getResultList();

            logger.debug("Finaliza consultarRegistrosTipo2(IndicePlanilla)");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(REGISTRO_2_OI);
            logger.debug("Finaliza consultarRegistrosTipo2(IndicePlanilla) - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_REGISTRO.getReadableMessage("2",
                    indicePlanilla.getTipoArchivo().getDescripcion(), e.getMessage());
            logger.error("Finaliza consultarRegistrosTipo2(IndicePlanilla) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#registrarError(com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarError(List<ErrorValidacionLog> errores) {
        logger.debug("Inicia registrarError(ErrorValidacionLog)");
        try {
            for (ErrorValidacionLog errorLog : errores) {
                entityManager.persist(errorLog);
            }
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_ACTUALIZACION_INCONSISTENCIA.getReadableMessage(e.getMessage());
            logger.error("Finaliza registrarError(ErrorValidacionLog) - ", e);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza registrarError(ErrorValidacionLog)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarOperadoresFinancieros()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Banco> consultarOperadoresFinancieros() throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarOperadoresFinancieros()");

        List<Banco> result = null;

        try {
            result = (List<Banco>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADORES_FINANCIEROS).getResultList();

            logger.debug("Finaliza consultarOperadoresFinancieros()");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(OPERADOR_FINANCIERO);
            logger.debug("Finaliza consultarOperadoresFinancieros() - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_OPERADOR_FINANCIERO.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarOperadoresFinancieros() - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarEntradasAnterioresConEstado(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EstadoArchivoPorBloque> consultarEntradasAnterioresConEstado(Long numeroPlanilla, String codOperador) {
        String firmaMetodo = "consultarEntradasAnterioresConEstado(IndicePlanilla, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<EstadoArchivoPorBloque> result = null;

        result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICE_CON_ESTADO)
                .setParameter("idPlanilla", numeroPlanilla).setParameter("codOperador", codOperador).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#activarCorreccionPila2(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void activarCorreccionPila2(String numeroPlanilla, String codOperador) {
        logger.debug("Inicia activarCorreccionPila2(Long, String)");

        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_REGISTRO_INDICE)
                .setParameter("numeroPlanillaAsociada", numeroPlanilla)
                .setParameter("codOperador", new Short(codOperador))
                .executeUpdate();

        logger.debug("Finaliza activarCorreccionPila2(Long, String)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#cerrarInconsistencias(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cerrarInconsistencias(Long idIndicePlanilla) {
        logger.debug("Inicia cerrarInconsistencias(Long)");
        List<ErrorValidacionLog> result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_INCONSISTENCIA, ErrorValidacionLog.class)
                .setParameter("idIndicePlanilla", idIndicePlanilla).getResultList();
		for(ErrorValidacionLog r : result) {
        	entityManager.merge(r);
        	r.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA);
        }
				
//        entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_GESTION_INCONSISTENCIA)
//                .setParameter("idIndicePlanilla", idIndicePlanilla)
//                .setParameter("estadoGestion", EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA).executeUpdate();

        logger.debug("Finaliza cerrarInconsistencias(Long)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarIndicesOIPorNumeroPlanilla(java.util.Set,
     *      java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanilla> consultarIndicesOIPorNumeroPlanilla(Set<String> listaPlanillas, List<EstadoProcesoArchivoEnum> estados) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarIndicesOIPorNumeroPlanilla(Set<Long>, List<EstadoProcesoArchivoEnum>)..";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
	List<String> listaPlanillasList = new ArrayList<>();
        listaPlanillasList.addAll(listaPlanillas);


        List<IndicePlanilla> resultCompleto = new ArrayList<>();
        // TODO: paginar peticion parametros IN()
//try catch
		try {
			int registrosPag = 1000;
			int contRegistros = 0;
			int cantPlanillas  = listaPlanillasList.size();
			
			ArrayList<String> paginaIdPlanillas = new ArrayList<>();
			
			do{
				for (int i = 0; i < registrosPag && contRegistros<cantPlanillas; i++) {
					paginaIdPlanillas.add(listaPlanillasList.get(contRegistros));
					contRegistros++;
				}
				 List<IndicePlanilla> result   = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_MULTIPLE)
				.setParameter("numerosPlanilla", paginaIdPlanillas).setParameter("estados", estados).getResultList();

				if (result != null && !result.isEmpty()) {
                    resultCompleto.addAll(result);
				}
				paginaIdPlanillas.clear();
			}while(contRegistros<cantPlanillas);
 /**
  * 
  */		logger.debug("Finaliza operacincc consultarIndicesOIPorNume roPlanilla(List<Long>)");
		} catch (Exception e) {
			logger.error("Ocurri0 un error inesperado en el mtodo consultarIndicesOIPorNumeroPlanilla(List<Long>): ", e);
		}

		//result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_PILA_EN_INDICE_MULTIPLE)
		//		.setParameter("numerosPlanilla", listaPlanillas).setParameter("estados", estados).getResultList();
		//logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " result -> " + result.size());
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultCompleto;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void autogestionarInconsistenciasOIPorBloque(Long idIndicePlanilla, BloqueValidacionEnum bloque,
            EstadoGestionInconsistenciaEnum estado) {
        String firmaMetodo = "PersistenciaDatosValidadores.autogestionarInconsistenciasOIPorBloque(Long, BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // TODO: cambiar execute update
        List<ErrorValidacionLog> result = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_GESTION_INCONSISTENCIA_BLOQUE, ErrorValidacionLog.class)
                .setParameter("idIndicePlanilla", idIndicePlanilla).setParameter("bloque", bloque).getResultList();
        for(ErrorValidacionLog r : result) {
        	entityManager.merge(r);
        	r.setEstadoInconsistencia(estado);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#ejecutarRevisionRegistrosTipo6(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarRevisionRegistrosTipo6(Long idIndicePlanilla) {
        String firmaMetodo = "PersistenciaDatosValidadores.ejecutarRevisionRegistrosTipo6(Long)---";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(USP_INICIAR_REVISION_REGISTRO_6_DUPLICADO);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
        storedProcedure.execute();

        logger.info(ConstantesComunes.FIN_LOGGER +" ===="+ firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarAportePropio(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean consultarAportePropio(Long idIndicePlanilla) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarAportePropio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = false;

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(USP_CONSULTAR_APORTE_PROPIO);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
        // salida
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ES_APORTE_PROPIO, Boolean.class, ParameterMode.OUT);
        storedProcedure.execute();

        result = (Boolean) storedProcedure.getOutputParameterValue(ConstantesParametrosSp.ES_APORTE_PROPIO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#registrarLogError(com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarLogError(LogErrorPilaM1ModeloDTO log) {
        String firmaMetodo = "PersistenciaDatosValidadores.registrarLogError(LogErrorPilaM1ModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        LogErrorPilaM1 logEntity = log.convertToEntity();
        entityManager.persist(logEntity);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarIndicesPorConciliar()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanilla> consultarIndicesPorConciliar(List<Long> ids) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarIndicesPorConciliar()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanilla> result = null;

        result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PENDIENTES_CONCILIACION, IndicePlanilla.class)
                .setParameter("ids", ids == null || ids.isEmpty() ? null : ids)
                .setParameter("tieneIds", ids == null || ids.isEmpty() ? 0 : 1).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarIndicesPorConciliar()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanilla> consultarIndicesPorListaIds(List<Long> ids) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarIndicesPorListaIds(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanilla> result = null;

        result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICES_LISTADO_ID, IndicePlanilla.class)
                .setParameter("ids", ids == null || ids.isEmpty() ? null : ids)
                .setParameter("tieneIds", ids == null || ids.isEmpty() ? 0 : 1).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarIndicesPorListaIdsOF(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicePlanillaOF> consultarIndicesPorListaIdsOF(List<Long> ids) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarIndicesPorListaIdsOF(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<IndicePlanillaOF> result = null;

        result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICES_LISTADO_ID_OF, IndicePlanillaOF.class)
                .setParameter("ids", ids == null || ids.isEmpty() ? null : ids)
                .setParameter("tieneIds", ids == null || ids.isEmpty() ? 0 : 1).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#actualizarListadoIndicePlanillas(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarListadoIndicePlanillas(List<IndicePlanilla> indicesOI) {
        String firmaMetodo = "PersistenciaDatosValidadores.actualizarListadoIndicePlanillas(List<IndicePlanilla>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (IndicePlanilla indicePlanilla : indicesOI) {
            entityManager.merge(indicePlanilla);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#actualizarListadoIndicePlanillasOF(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarListadoIndicePlanillasOF(List<IndicePlanillaOF> indicesOF) {
        String firmaMetodo = "PersistenciaDatosValidadores.actualizarListadoIndicePlanillasOF(List<IndicePlanillaOF>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (IndicePlanillaOF indicePlanilla : indicesOF) {
            entityManager.merge(indicePlanilla);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#consultarSolicitudCambioId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudCambioNumIdentAportante consultarSolicitudCambioId(Long idPlanilla) {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarSolicitudCambioId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudCambioNumIdentAportante result = null;

        try {
            result = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_CAMBIO_ID, SolicitudCambioNumIdentAportante.class)
                    .setParameter("idPlanilla", idPlanilla).getSingleResult();
        } catch (NoResultException e) {
            // no se trata de un error
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#rechazarSolicitudesCambioId(java.lang.Long,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void rechazarSolicitudesCambioId(SolicitudCambioNumIdentAportante solicitud, String usuarioAnulacion) {
        String firmaMetodo = "PersistenciaDatosValidadores.rechazarSolicitudesCambioId(SolicitudCambioNumIdentAportante, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        solicitud = entityManager.merge(solicitud);
        solicitud.setAccionCorreccion(AccionCorreccionPilaEnum.ANULAR_SOLICITUD_CAMBIO_IDENTIFICACION);
        solicitud.setFechaRespuesta(new Date());
        solicitud.setUsuarioAprobador(usuarioAnulacion);
        solicitud.setRazonRechazo(RazonRechazoSolicitudCambioIdenEnum.ANULACION_REPROCESO);
        solicitud.setComentarios(RazonRechazoSolicitudCambioIdenEnum.ANULACION_REPROCESO.getDescripcion());

        /* Se prepara la entrada en histórico de estado */
        HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
        historialEstado.setIdIndicePlanilla(solicitud.getIndicePlanilla().getId());
        historialEstado.setEstado(EstadoProcesoArchivoEnum.PENDIENTE_POR_APROBAR);
        historialEstado.setAccion(AccionProcesoArchivoEnum.PASAR_A_BANDEJA);
        historialEstado.setFechaEstado(new Date());
        historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
        historialEstado.setUsuarioEspecifico(usuarioAnulacion);
        historialEstado.setClaseUsuario((short) 4);

        entityManager.persist(historialEstado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoPilaDTO> filtrarExistentesPlanillas(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos) {
        logger.warn("inicia List<ArchivoPilaDTO> filtrarExistentesPlanillas(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos)");

        HashSet<ArchivoPilaDTO> archivosFTPSet = new HashSet<>(archivosFTP);
        List<String> nombres = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_FTP_ARCHIVO).getResultList();

        Set<String> nombresSet = new HashSet<>(nombres);

        List<ArchivoPilaDTO> result = new ArrayList<>();
        for (ArchivoPilaDTO n : archivosFTPSet) {
            if (!nombresSet.contains(n.getFileName())) {
                result.add(n);
            }
            logger.warn("archivo pila");
            logger.warn(n.toString());
        }

        return result;
    }
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#filtrarExistentes(java.util.List, java.lang.Integer)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoPilaDTO> filtrarExistentes(List<ArchivoPilaDTO> archivosFTP, Integer tipoArchivos) {
        String firmaMetodo = "PersistenciaDatosValidadores.filtrarExistentes(List<ArchivoPilaDTO>, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ArchivoPilaDTO> result = new ArrayList<>();
        ComprobacionArchivoFtpDTO archivo = null;
        List<ComprobacionArchivoFtpDTO> archivos = new ArrayList<>();
        List<ComprobacionArchivoFtpDTO> archivosResultado = new ArrayList<>();
        Map<Long, ComprobacionArchivoFtpDTO> archivosResultadoMapa = new HashMap<>();

        Long llaveTemporal = 1L;

        TipoArchivoPilaEnum tipoArchivo = null;
        if (tipoArchivos == 0 || tipoArchivos == 2) {

            for (ArchivoPilaDTO archivoFTP : archivosFTP) {
                tipoArchivo = FuncionesValidador.getTipoArchivo(archivoFTP.getFileName());
                if(tipoArchivo != null && !PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(tipoArchivo.getPerfilArchivo())){
                    archivoFTP.setIdIndicePlanillaOI(llaveTemporal++);
                    archivo = new ComprobacionArchivoFtpDTO();
                    archivo.setNombreArchivo(archivoFTP.getFileName());
                    archivo.setFechaModificacion(archivoFTP.getFechaModificacion());
                    archivo.setLlaveTemporal(archivoFTP.getIdIndicePlanillaOI());
                    archivos.add(archivo);

                    if (archivos.size() == 1000) {
                        archivosResultado.addAll(comprobarExistenciaOI(archivos));
                        archivos.clear();
                    }
                }
            }

            if (!archivos.isEmpty()) {
                archivosResultado.addAll(comprobarExistenciaOI(archivos));
            }

            for (ComprobacionArchivoFtpDTO resultado : archivosResultado) {
                archivosResultadoMapa.put(resultado.getLlaveTemporal(), resultado);
            }

            for (ArchivoPilaDTO archivoFTP : archivosFTP) {
                if (archivosResultadoMapa.containsKey(archivoFTP.getIdIndicePlanillaOI())) {
                    result.add(archivoFTP);
                }
                archivoFTP.setIdIndicePlanillaOI(null);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * @param archivos
     * @return
     */
    private List<ComprobacionArchivoFtpDTO> comprobarExistenciaOI(List<ComprobacionArchivoFtpDTO> archivos) {
        String jsonPayload = null;
        ObjectMapper mapper = null;

        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            jsonPayload = mapper.writeValueAsString(archivos);
        } catch (JsonProcessingException e) {
            logger.error("Error mapeando datos de archivo FTP");
        }

        return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTENCIA_FTP_OI, ComprobacionArchivoFtpDTO.class)
                .setParameter("archivos", jsonPayload).getResultList();
    }
    
	public void actualizarRegistro1ConListasBlancasAportantes(Object registro1, ListasBlancasAportantes listaBlanca) {
		if (registro1 instanceof PilaArchivoIRegistro1) {
			PilaArchivoIRegistro1 result = null;
			result = (PilaArchivoIRegistro1) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_I_REGISTRO_1)
					.setParameter("indicePlanilla", ((PilaArchivoIRegistro1) registro1).getIndicePlanilla())
					.setMaxResults(1).getSingleResult();

			result.setTipoDocAportante(listaBlanca.getTipoIdentificacionEmpleador().getValorEnPILA());
			result.setIdAportante(listaBlanca.getNumeroIdentificacionEmpleador());

			entityManager.merge(result);
		} else if (registro1 instanceof PilaArchivoIPRegistro1) {

			PilaArchivoIPRegistro1 result = null;
			result = (PilaArchivoIPRegistro1) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_IP_REGISTRO_1)
					.setParameter("indicePlanilla", ((PilaArchivoIPRegistro1) registro1).getIndicePlanilla())
					.setMaxResults(1).getSingleResult();

			result.setTipoIdPagador(listaBlanca.getTipoIdentificacionEmpleador().getValorEnPILA());
			result.setIdPagador(listaBlanca.getNumeroIdentificacionEmpleador());

			entityManager.merge(result);
		}
	}
	
	public IndicePlanilla consultarPlanillaAutomaticaPorId(Long idPlanilla) {
        try {
            return entityManager
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_ES_AUTIMATICA, IndicePlanilla.class)
            .setParameter("idPlanilla", idPlanilla).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nur) {
            return null;
        }
	}
	 
	@Override
    public List<Long> consultarPlanillasReproceso() {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarIndicesPorListaIds(List<Long> {{{}}})";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> result = new ArrayList<Long>();
        List<Object> resultTmp = null;
       // resultTmp = entityManager.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_A_REPROCESAR).getResultList();
        try {
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_A_REPROCESAR);
            // query.execute();
            resultTmp = query.getResultList();
        } catch (Exception e) {
            logger.error(" :: Hubo un error en el SP USP_ReiniciarPlanillas: " + e);
        }
        entityManager.flush();
        entityManager.clear();
        if (resultTmp != null) {
        	logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " resultTmp " +resultTmp.size());
	        for (Object o : resultTmp) {
	        	result.add(Long.valueOf(o.toString()));
	        }
	        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " resultcxc " +result.size());
        }
        logger.info(ConstantesComunes.FIN_LOGGER+ firmaMetodo);
        return result;
    }

    @Override
    public List<PilaArchivoFRegistro6> consultarPlanillasPendientesPorConciliacionF() {
        String firmaMetodo = "PersistenciaDatosValidadores.consultarPlanillasPendientesPorConciliacionF(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<PilaArchivoFRegistro6> planillasFIndices = new ArrayList<PilaArchivoFRegistro6>();
        List<Object> result = entityManager
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLAS_F_PENDIENTES_ACTUALIZACION)
            .getResultList();
        for (Object idPF6 : result) {
            if (idPF6 != null) {
                planillasFIndices.add(entityManager.find(PilaArchivoFRegistro6.class, Long.valueOf(idPF6.toString())));
            }
        }
        return planillasFIndices;
    }

    @Override
    public void eliminarPersistenciasPila(Long idIndicePlanilla) {
        // Consulta sp para eliminar registros de pila
        String firmaMetodo = "PersistenciaDatosValidadores.eliminarPersistenciasPila(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "planilla: " + idIndicePlanilla.toString());
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(USP_ELIMINAR_PERSISTENCIAS_PILA);
        storedProcedure.registerStoredProcedureParameter("planillasIds", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("planillasIds", idIndicePlanilla);
        storedProcedure.execute();

    }

    @Override
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarConciliacionPlanillasF(List<PilaArchivoFRegistro6> planillasF) {
        String firmaMetodo = "PersistenciaDatosValidadores.actualizarConciliacionPlanillasF(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        for (PilaArchivoFRegistro6 pilaArchivoFRegistro6 : planillasF) {
            pilaArchivoFRegistro6.setEstadoConciliacion(null);
            entityManager.merge(pilaArchivoFRegistro6);
        }
        logger.info(ConstantesComunes.FIN_LOGGER+ firmaMetodo);
    }


    @Override
    public List<Long> consultarPlanillasOIPorF(List<PilaArchivoFRegistro6> planillasOF){
        String firmaMetodo = "PersistenciaDatosValidadores.consultarPlanillasOIPorF(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> resListaNumPlanillas = new ArrayList<Long>();
        List<Long> listaNumPlanillas = new ArrayList<Long>();

        if (planillasOF != null && !planillasOF.isEmpty()) {
            for (PilaArchivoFRegistro6 planillaF : planillasOF) {
                listaNumPlanillas.add(Long.valueOf(planillaF.getNumeroPlanilla()));
            }
        }
        
        
        
        if (listaNumPlanillas != null && !listaNumPlanillas.isEmpty()) {
            List<IndicePlanilla> result = null;
            logger.info("Consulta 1" + listaNumPlanillas.toString());

            result = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICES_LISTADO_ID_PLANILLA, IndicePlanilla.class)
                    .setParameter("idsPlanilla",listaNumPlanillas).getResultList();
            logger.info("Result contulta 1:"+ result);
            // se agrega las planillas que vienen con valor 0 conciliado y las que quedaron en estado pasar a cruce con bd pero sin
            // persistencia de m2
            if (result != null && !result.isEmpty()) {
                for (IndicePlanilla planilla : result) {
                    resListaNumPlanillas.add(planilla.getIdPlanilla());
                }
            }
        }

        logger.info("Consulta 2");
        List<Object> result2 = entityManager
            .createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANILLAS_F_PENDIENTES_ACTUALIZACION_ESTADO_CONCILIADO)
            .getResultList();

        if (result2 != null && !result2.isEmpty()) {
            for (Object planillasEstadoConciliado : result2) {
                resListaNumPlanillas.add(Long.valueOf(planillasEstadoConciliado.toString()));
            }
        }

        logger.info("*** Planillas para schedule conciliacion 2: "+ resListaNumPlanillas);
    
        return resListaNumPlanillas;

    }

    @Override
    public List<Long> consultarPlanillasOIPorPlanillas(List<Long> lstIdPlanillas) {

        List<Long> planillas = new ArrayList<>();

        if (lstIdPlanillas != null && !lstIdPlanillas.isEmpty()) {
            List<Object> result = null;
            logger.info("Consulta 1" + lstIdPlanillas.toString());

            result = (List<Object>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INDICES_PLANILLAS_OI)
                    .setParameter("indicesPlanillas",lstIdPlanillas).getResultList();
            logger.info("Result contulta 1:"+ result);
            // se agrega las planillas que vienen con valor 0 conciliado y las que quedaron en estado pasar a cruce con bd pero sin
            // persistencia de m2
            if (result != null && !result.isEmpty()) {
                for (Object idPlanilla : result) {
                    planillas.add(Long.valueOf(idPlanilla.toString()));
                }
            }
        }

        return planillas;

    }
//Alexander Camelo B6 Directo B8
    @Override
    public List<Long> consultarPlanillasConciliadasB6aB8() {
        String firmaMetodo = "**__**PersistenciaDatosValidadores.consultarPlanillasConciliadasB6aB8(List<Long> {{{}}})";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> result = new ArrayList<Long>();
        List<Object> resultTmp = null;
       // resultTmp = entityManager.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_A_REPROCESAR).getResultList();
        try {
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_PLANILLA_A_PROCESAR_DE_B6_A_B8);
            // query.execute();
            resultTmp = query.getResultList();
        } catch (Exception e) {
            logger.error(" :: Hubo un error en el SP USP_ConsultarPlanillasConciliadasB6aB8: " + e);
        }
        entityManager.flush();
        entityManager.clear();
        if (resultTmp != null) {
        	logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " resultTmp " +resultTmp.size());
	        for (Object o : resultTmp) {
	        	result.add(Long.valueOf(o.toString()));
	        }
	        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " resultcxc " +result.size());
        }
        logger.info(ConstantesComunes.FIN_LOGGER+ firmaMetodo);
        return result;
    }

}
