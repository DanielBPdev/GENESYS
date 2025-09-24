package com.asopagos.validaciones.fovis.ejb;

import com.asopagos.cache.CacheManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.ssl.SSLContexts;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.converter.ObjetoValidacionUtils;
import com.asopagos.dto.ListaDatoValidacionDTO;
import com.asopagos.dto.ParametrizacionNovedadDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoRegistraduriaNacional;
import com.asopagos.entidades.ccf.afiliaciones.DatosRegistroNacionalAud;
import com.asopagos.entidades.ccf.core.ParametrizacionNovedad;
import com.asopagos.entidades.transversal.core.ValidacionProceso;
import com.asopagos.entidades.transversal.personas.IValidable;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.HttpWrapper;
import com.asopagos.util.Interpolator;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.dto.AutenticacionTokenDTO;
import com.asopagos.validaciones.fovis.dto.ConsultaRegistroCivilDTO;
import com.asopagos.validaciones.fovis.dto.HistoricoDTO;
import com.asopagos.validaciones.fovis.dto.ParametrosRegistroCivilDTO;
import com.asopagos.validaciones.fovis.service.ValidacionesFovisAPIService;
import com.asopagos.validaciones.fovis.service.ValidacionesFovisPersistenceService;
import com.asopagos.validaciones.fovis.util.ValidacionesFovisUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import javax.persistence.NoResultException;

import org.jose4j.json.JsonUtil;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.io.IOException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.asopagos.constants.ParametrosGapConstants;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.asopagos.dto.PersonaDTO;

import javax.persistence.StoredProcedureQuery;
/**
 * <b>Descripción:</b> Clase que <br/>
 * implementa los servicios relacionados con la gestión de validaciones de
 * negocio <b>Módulo:</b> Asopagos - HU-TRA<br/>
 *
 *
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
@Stateless
public class ValidacionesFovisAPIBusiness implements ValidacionesFovisAPIService {
    /**
     * Instancia del EntityManager
     */
    @PersistenceContext(unitName = "validaciones_PU")
    private EntityManager entityManager;
    /**
     * Instancia del gestor de registro de eventos
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionesFovisAPIBusiness.class);
    /**
     * Instancia el servicio de ejecución
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/validacionesFovis")
    private ManagedExecutorService mes;

    @Inject
    private ValidacionesFovisPersistenceService validacionesPersistence;

    private static final String FORMATO_FECHA = "EEE, dd MMM yyyy HH:mm:ss zzz";
    /**
     * Bundle que contiene los mensajes de validación.
     */
    protected ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ValidacionDTO> validarEmpleadores(ProcesoEnum proceso, String bloque, Map<String, String> datosValidacion) {
        logger.debug("Inicia método validar(ProcesoEnum proceso, String bloque,	Map<String, String> datosValidacion)");
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(ConstantesValidaciones.NAMED_QUERY_PARAM_PROCESO, proceso);
            params.put(ConstantesValidaciones.NAMED_QUERY_PARAM_BLOQUE, bloque);
            logger.debug("termina método validar(ProcesoEnum proceso, String bloque, Map<String, String> datosValidacion)");
            return ejecutarValidaciones(datosValidacion, params);
        } catch (Exception e) {

            if (e instanceof FunctionalConstraintException || e instanceof AsopagosException
                    || (e.getCause() != null && e.getCause() instanceof FunctionalConstraintException)) {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_HTTP_CONFLICT,
                        "El empleador se encuentra con datos inconsistentes");
            }
            else {
                logger.error("termina método validar("
                        + "ProcesoEnum proceso, String bloque, Map<String, String> datosValidacion) con excepción " + e);
                logger.debug("termina método validar("
                        + "ProcesoEnum proceso, String bloque, Map<String, String> datosValidacion) con excepción " + e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + ": " + e);
            }

        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ValidacionDTO> validarPersonasFovis(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                                    Map<String, String> datosValidacion) throws ExecutionException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException {
        return validarPersona(proceso, objetoValidacion, bloque, datosValidacion);
    }

    /**
     * Método que consulta y ejecuta las validaciones
     *
     * @param datosValidacion
     *        Datos necesarios para la validación
     * @param params
     *        Parámetros para la consulta de validaciones
     * @return Los resultados de las validaciones ejecutadas
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @SuppressWarnings("unchecked")
    private List<ValidacionDTO> ejecutarValidaciones(Map<String, String> datosValidacion, Map<String, Object> params)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        logger.info("params " +params );
        logger.debug("Inicia método ejecutarValidaciones(Map<String, String> datosValidacion,Map<String, Object> params)");
        Query consulta = armarConsulta(ValidacionProceso.class, params, ConstantesValidaciones.ORDER_FIELD);
        List<ValidacionProceso> validaciones = consulta.getResultList();
        for(ValidacionProceso validacionProceso : validaciones){
            logger.info("=============================");
            logger.info("validacionProceso---> " +validacionProceso.getValidacion());
            logger.info("validacionProceso bloque---> " +validacionProceso.getBloque());
            logger.info("validacionProceso codigo---> " +validacionProceso.getValidacion().getCodigo());
        }
        List<ValidacionDTO> resultadosValidaciones = ejecutar(datosValidacion, validaciones);
        // Se llama el servicio de persistencia de historico de validaciones
        persistirHistoricoResultados(resultadosValidaciones, datosValidacion);
        logger.debug("Termina método ejecutarValidaciones(Map<String, String> datosValidacion,Map<String, Object> params)");
        return resultadosValidaciones;
    }

    /**
     * Método encargado de ejecutar las validaciones, que llegan por parámetro.
     *
     * @param datosValidacion
     *        datos a validar.
     * @param validaciones
     *        validaciones que se quieren ejecutar.
     * @return listado con los resultado de la validación.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private List<ValidacionDTO> ejecutar(Map<String, String> datosValidacion, List<ValidacionProceso> validaciones)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        logger.debug("Inicio de método ejecutar(Map<String, String> datosValidacion,List<ValidacionProceso> validaciones) ");
        List<ValidacionDTO> resultadosValidaciones = new LinkedList<>();
        List<Future<ValidacionDTO>> resultadosFuturos = new LinkedList<>();
        List<Callable<ValidacionDTO>> tareasParalelas = new LinkedList<>();
        for (ValidacionProceso validacionProceso : validaciones) {
            // La validacion puede ser nula cuando se intenta habilitar una novedad que no tiene restricciones
            if (validacionProceso.getValidacion() == null) {
                continue;
            }
            if (!validacionEjecutada(resultadosValidaciones, validacionProceso.getValidacion())) {
                ValidadorFovisCore valid = ValidadorFovisCoreFactory.getInstance().getValidador(validacionProceso.getValidacion().getCodigo());
                valid.setEntityManager(entityManager);
                Callable<ValidacionDTO> parallelTask = () -> {
                    ValidacionDTO validacion = valid.execute(datosValidacion);
                    validacion.setBloque(validacionProceso.getBloque());
                    return validacion;
                };
                tareasParalelas.add(parallelTask);
            }
        }
        resultadosFuturos = mes.invokeAll(tareasParalelas);
        for (Future<ValidacionDTO> future : resultadosFuturos) {
            resultadosValidaciones.add(future.get());
        }

        for(ValidacionDTO resultado : resultadosValidaciones){
            logger.info("====== ejecutar antes del inverso ======" + resultado.toString());
        }

        //se hacen los ajuste en las validaciones que:
        // 1. requieran un resultado inverso al dado.
        // 2. arrojen para la novedad ejecutada, un tipo de excepción funcional diferente a 2.
        List<ValidacionDTO> resultadosValidacionesInversa = new LinkedList<>();
        resultadosValidacionesInversa = ajustarValidacionesEspeciales(resultadosValidaciones, validaciones, datosValidacion);
        for(ValidacionDTO resultado : resultadosValidacionesInversa){
            logger.info("====== ejecutar despues del inverso ======" + resultado.toString());

        }

        logger.debug("Fin de método ejecutar(Map<String, String> datosValidacion,List<ValidacionProceso> validaciones) ");
        return resultadosValidacionesInversa;
    }

    /**
     * Método encargado de ajustar validaciones especiales como la inversa y el tipo de transaccion.
     * @param resultadosValidaciones
     * @param validaciones
     */
    private List<ValidacionDTO> ajustarValidacionesEspeciales(List<ValidacionDTO> resultadosValidaciones, List<ValidacionProceso> validaciones,
                                               Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ajustarValidacionesEspeciales("
                + "List<ValidacionAfiliacionDTO> resultadosValidaciones,Map<String, String> datosValidacion)");
        List<ValidacionDTO> resultadosValidacionesInversa = new ArrayList<ValidacionDTO>();
        TipoTransaccionEnum tipoTransaccion = null;
        if (datosValidacion.get(ConstantesValidaciones.TIPO_TRANSACCION) != null) {
            tipoTransaccion = TipoTransaccionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_TRANSACCION));
        }

        Boolean inversa = false;


        for (ValidacionDTO resultado : resultadosValidaciones) {
            for (ValidacionProceso validacionProceso : validaciones) {
                if (resultado.getValidacion().equals(validacionProceso.getValidacion())
                        && resultado.getBloque().equals(validacionProceso.getBloque())) {
                    // Se identifica si la validacion es inversa y se ajusta el mensaje
                    inversa = validacionProceso.getInversa();
                }
            }
            ValidacionDTO resultadoInversa = verificarValidacionInversa(inversa, resultado);

            // Se ajusta el tipo de excepcion de la validacion
            if (tipoTransaccion != null && ResultadoValidacionEnum.NO_APROBADA.equals(resultado.getResultado())) {
                TipoExcepcionFuncionalEnum tipoExcepcion = ValidacionesFovisUtils
                        .obtenerTipoExcepcion(tipoTransaccion.name() + resultado.getValidacion().name());
                resultadoInversa.setTipoExcepcion(tipoExcepcion);
            }
            resultadosValidacionesInversa.add(resultadoInversa);
        }

        for(ValidacionDTO resultado : resultadosValidacionesInversa){
            logger.info("____ciclando posterior insercionlista: " +resultado.toString());
        }

        logger.debug("Fin de método ajustarValidacionesEspeciales("
                + "List<ValidacionAfiliacionDTO> resultadosValidaciones,Map<String, String> datosValidacion)");

        return resultadosValidacionesInversa;
    }

    /**
     * Verifica si la validacion esta parametrizada como inversa para generar el comportamiento esperado
     * @param validacionProceso
     *        Parametrizacion de la validacion
     * @param resultado
     *        Resultado ejecución validación
     */
    private ValidacionDTO verificarValidacionInversa(Boolean inversa, ValidacionDTO resultado){
        if (inversa) {
            if (ResultadoValidacionEnum.NO_APROBADA.equals(resultado.getResultado())) {
                // Se limpia la informacion del mensaje y la excepcion
                resultado.setDetalle(null);
                resultado.setTipoExcepcion(null);
                resultado.setResultado(ResultadoValidacionEnum.APROBADA);
            }
            else if (ResultadoValidacionEnum.APROBADA.equals(resultado.getResultado())) {
                resultado.setResultado(ResultadoValidacionEnum.NO_APROBADA);
                // Se cambia la información del mensaje y la excepcion
                resultado.setDetalle(ValidacionesFovisUtils.obtenerMensajeValidacionInversa(resultado.getValidacion()));
                resultado.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }

        }
        logger.info("=========== finaliza verificarValidacionInversa: " + resultado.toString());
        return resultado;
    }

    /**
     * Método que persiste los resultados de las validaciones realizadas
     *
     * @param resultadosValidaciones
     *        Listado de validaciones
     * @param datosValidacion
     *        Mapa con los datos necesarios para la validación
     */
    private void persistirHistoricoResultados(List<ValidacionDTO> resultadosValidaciones, Map<String, String> datosValidacion) {
        HistoricoDTO datoHistorico = new HistoricoDTO();
        datoHistorico.setDatosValidacion(datosValidacion);
        datoHistorico.setResultadoValidacion(resultadosValidaciones);
        validacionesPersistence.persistirHistoricoValidaciones(datoHistorico);
    }

    /**
     * Método que arma una consulta con respecto a los parámetros recibidos
     *
     * @param clazz
     *        Es la entidad que se desea consultar
     * @param params
     *        Es el mapa con los parámetros
     * @param campoOrden
     *        Es el campo de ordenamiento para la consulta
     * @return Devuelve la consulta final con parámetros asignados
     */
    private <T> Query armarConsulta(Class<T> clazz, Map<String, Object> params, String campoOrden) {
        logger.debug("Inicia método armarConsulta(Class<T> clazz, Map<String, Object> params, String campoOrden)");
        StringBuilder consultaInicial = new StringBuilder();
        consultaInicial.append(ConstantesValidaciones.QUERY_VALIDACIONES);
        consultaInicial.append(clazz.getSimpleName());
        consultaInicial.append(ConstantesValidaciones.OBJ_QUERY_VALID);
        boolean condicionado = false;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                if (!condicionado) {
                    consultaInicial.append(ConstantesValidaciones.WHERE_CLAUSE);
                    condicionado = true;
                }
                else {
                    consultaInicial.append(ConstantesValidaciones.AND_CLAUSE);
                }
                consultaInicial.append(ConstantesValidaciones.OBJ_REF_QUERY);
                consultaInicial.append(entry.getKey());
                consultaInicial.append(entry.getValue() instanceof String ? ConstantesValidaciones.LIKE_CLAUSE
                        : entry.getValue() instanceof List ? ConstantesValidaciones.IN_CLAUSE : ConstantesValidaciones.EQUAL_SYMBOL);
                consultaInicial.append(entry.getValue() instanceof List ? ConstantesValidaciones.OPEN_PAR_SYMBOL : "");
                consultaInicial.append(ConstantesValidaciones.COLON_SYMBOL);
                consultaInicial.append(entry.getKey());
                consultaInicial.append(entry.getValue() instanceof List ? ConstantesValidaciones.CLOSE_PAR_SYMBOL : "");
                consultaInicial.append(ConstantesValidaciones.SPACE);
            }
        }
        consultaInicial.append("AND obj.estadoProceso != 'INACTIVO'");

        if (campoOrden != null) {
            consultaInicial.append(ConstantesValidaciones.ORDER_BY_CLAUSE);
            consultaInicial.append(campoOrden);
        }
        Query query = entityManager.createQuery(consultaInicial.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                query = query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        logger.debug("Termina método armarConsulta(Class<T> clazz, Map<String, Object> params, String campoOrden)");
        return query;
    }

    /**
     * Método que verifica si una validación ya fue ejecutada
     *
     * @param validaciones
     *        Son los resultados de la validación
     * @param validacion
     *        Es la validación a consultar
     * @return true, si la validación fue ejecutada, false en el caso contrario
     */
    private boolean validacionEjecutada(List<ValidacionDTO> validaciones, ValidacionCoreEnum validacion) {
        logger.debug("Inicia método validacionEjecutada(List<ValidacionAfiliacionDTO> validaciones, ValidacionCoreEnum validacion)");
        logger.info("validacionEjecutada " + validacion);
        for (ValidacionDTO validacionAfiliacionDTO : validaciones) {
            if (validacionAfiliacionDTO.getValidacion().equals(validacion)) {
                logger.info("Termina método validacionEjecutada("
                        + "List<ValidacionAfiliacionDTO> validaciones, ValidacionCoreEnum validacion)");
                return true;
            }
        }
        logger.debug("Termina método validacionEjecutada(List<ValidacionAfiliacionDTO> validaciones, ValidacionCoreEnum validacion)");
        return false;
    }

    /*@Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ListaDatoValidacionDTO validarCargaMultipleAfiliaciones(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                                                   List<AfiliarTrabajadorCandidatoDTO> candidatosDTO) {
        try {
            logger.debug("Inicia método validarPersonasCargaMultiple("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Map<String, String> datosValidacion)");
            List<AfiliarTrabajadorCandidatoDTO> candidatoDTOAprobado = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
            List<AfiliarTrabajadorCandidatoDTO> candidatoDTONoAprobado = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
            ListaDatoValidacionDTO listadoDatoValidacionDTO = new ListaDatoValidacionDTO();

            for (AfiliarTrabajadorCandidatoDTO trabajadorValidar : candidatosDTO) {
                Boolean aprobado = Boolean.TRUE;
                Map<String, String> listaDatosValidacion = obtenerDatosValidacionMultipleAfiliaciones(trabajadorValidar);
                List<ValidacionDTO> lstValidaciones = new ArrayList<ValidacionDTO>();

                //Se valida que el candidato a afiliar no tenga solicitudes de novedad y/o afiliación en curso
                ValidacionDTO validacionSolicitudes = this.verificarSolicitudesEnCurso(listaDatosValidacion.get("numeroIdentificacion"),
                        TipoIdentificacionEnum.valueOf(listaDatosValidacion.get("tipoIdentificacion")), null,Boolean.FALSE,null, null);
                logger.info("validacionsoli: "+ validacionSolicitudes.getResultado());
                if (ResultadoValidacionEnum.NO_APROBADA.equals(validacionSolicitudes.getResultado()) ) {
                    aprobado = Boolean.FALSE;
                }else {
                    lstValidaciones = validarPersona(proceso, objetoValidacion, bloque, listaDatosValidacion);
                }

                for (ValidacionDTO validacionAfiliacionDTO : lstValidaciones) {
                    /* si alguna de las validaciones no está aprobada no se validan el resto. */
                    /*if (!validacionAfiliacionDTO.getResultado().equals(ResultadoValidacionEnum.APROBADA)) {
                        aprobado = Boolean.FALSE;
                        break;
                    }
                }
                trabajadorValidar.setAfiliable(aprobado);
                if (aprobado) {
                    candidatoDTOAprobado.add(trabajadorValidar);
                }
                else {
                    candidatoDTONoAprobado.add(trabajadorValidar);
                }
            }
            listadoDatoValidacionDTO.setCandidatoAfiliacionDTOAprobado(candidatoDTOAprobado);
            listadoDatoValidacionDTO.setCandidatoAfiliacionDTONoAprobado(candidatoDTONoAprobado);
            logger.debug("Finaliza método validarPersonasCargaMultiple("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Lis<Map<String, String>> datosValidacion)");
            return listadoDatoValidacionDTO;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("termina método validarPersonasCargaMultiple(ProcesoEnum proceso, String objetoValidacion, "
                    + "String bloque, List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO) con excepción " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + ": " + e);
        }
    }*/
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ListaDatoValidacionDTO validarCargaMultipleAfiliacionesStoredProcedure(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                                                                  List<AfiliarTrabajadorCandidatoDTO> candidatosDTO) {
        try {
            logger.info("Inicia método validarCargaMultipleAfiliacionesStoredProcedure("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Map<String, String> datosValidacion)");
            List<AfiliarTrabajadorCandidatoDTO> candidatoDTOAprobado = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
            List<AfiliarTrabajadorCandidatoDTO> candidatoDTONoAprobado = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
            ListaDatoValidacionDTO listadoDatoValidacionDTO = new ListaDatoValidacionDTO();

            Gson gsonEntrada = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();

            ArrayList<Map<String, String>> parametrosArray = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (AfiliarTrabajadorCandidatoDTO trabajadorValidar : candidatosDTO) {
                HashMap<String, String> map = new HashMap<>();
                map.put("numeroIdentificacion", trabajadorValidar.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
                map.put("tipoIdentificacion", trabajadorValidar.getAfiliadoInDTO().getPersona().getTipoIdentificacion().toString());
                map.put("numeroIdentificacionEmpleador", trabajadorValidar.getNumeroIdentificacionEmpleador());
                map.put("tipoIdentificacionEmpleador", trabajadorValidar.getTipoIdentificacionEmpleador().toString());
                map.put("fechaNacimiento", dateFormat.format(new Date(trabajadorValidar.getAfiliadoInDTO().getPersona().getFechaNacimiento())));
                parametrosArray.add(map);
            }
            String parametrosJson = gsonEntrada.toJson(parametrosArray);
            List<PersonaDTO> resultadoSP = new ArrayList<PersonaDTO>();
            StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(NamedQueriesConstants.ASP_JSON_VALIDACIONES_CARGUE_MULTIPLE);
            query.setParameter("personas", parametrosJson);
            query.execute();
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<List<PersonaDTO>>(){}.getType();
            resultadoSP = gson.fromJson((String)query.getSingleResult(), listType);

            for (AfiliarTrabajadorCandidatoDTO trabajadorValidar : candidatosDTO) {
                int cont =0;
                for (PersonaDTO persona: resultadoSP){
                    if(persona.getNumeroIdentificacion().equals(trabajadorValidar.getAfiliadoInDTO().getPersona().getNumeroIdentificacion()) &&
                            persona.getTipoIdentificacion().equals(trabajadorValidar.getAfiliadoInDTO().getPersona().getTipoIdentificacion()) ){
                        trabajadorValidar.setAfiliable(persona.getAfiliable() == Boolean.TRUE ? Boolean.FALSE : Boolean.TRUE);
                        if (trabajadorValidar.getAfiliable().equals(Boolean.TRUE)) {
                            candidatoDTOAprobado.add(trabajadorValidar);
                        }
                        else {
                            candidatoDTONoAprobado.add(trabajadorValidar);
                        }
                        resultadoSP.remove(cont);
                        break;
                    }
                    cont++;
                }
            }
          /*  for (AfiliarTrabajadorCandidatoDTO trabajadorValidar : candidatosDTO) {
                Boolean aprobado = Boolean.TRUE;
                Map<String, String> listaDatosValidacion = obtenerDatosValidacionMultipleAfiliaciones(trabajadorValidar);
                List<ValidacionDTO> lstValidaciones = new ArrayList<ValidacionDTO>();

                //Se valida que el candidato a afiliar no tenga solicitudes de novedad y/o afiliación en curso
                ValidacionDTO validacionSolicitudes = this.verificarSolicitudesEnCurso(listaDatosValidacion.get("numeroIdentificacion"),
                        TipoIdentificacionEnum.valueOf(listaDatosValidacion.get("tipoIdentificacion")), null,Boolean.FALSE,null);
                logger.info("validacionsoli: "+ validacionSolicitudes.getResultado());
                if (ResultadoValidacionEnum.NO_APROBADA.equals(validacionSolicitudes.getResultado()) ) {
                    aprobado = Boolean.FALSE;
                }else {
                    lstValidaciones = validarPersona(proceso, objetoValidacion, bloque, listaDatosValidacion);
                }

                for (ValidacionDTO validacionAfiliacionDTO : lstValidaciones) {
                   //si alguna de las validaciones no está aprobada no se validan el resto.
                    if (!validacionAfiliacionDTO.getResultado().equals(ResultadoValidacionEnum.APROBADA)) {
                        aprobado = Boolean.FALSE;
                        break;
                    }
                }
                trabajadorValidar.setAfiliable(aprobado);
                if (aprobado) {
                    candidatoDTOAprobado.add(trabajadorValidar);
                }
                else {
                    candidatoDTONoAprobado.add(trabajadorValidar);
                }
            }*/
            listadoDatoValidacionDTO.setCandidatoAfiliacionDTOAprobado(candidatoDTOAprobado);
            listadoDatoValidacionDTO.setCandidatoAfiliacionDTONoAprobado(candidatoDTONoAprobado);
            logger.info("Finaliza método validarCargaMultipleAfiliacionesStoredProcedure("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Lis<Map<String, String>> datosValidacion) cantidad trabajadores:");
            return listadoDatoValidacionDTO;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("termina método validarPersonasCargaMultiple(ProcesoEnum proceso, String objetoValidacion, "
                    + "String bloque, List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO) con excepción " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + ": " + e);
        }
    }
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ListaDatoValidacionDTO validarCargaMultipleNovedades(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                                                List<TrabajadorCandidatoNovedadDTO> candidatosDTO) {
        try {
            logger.debug("Inicia método validarCargaMultipleNovedades("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Map<String, String> datosValidacion)");
            List<TrabajadorCandidatoNovedadDTO> candidatoDTOAprobado = new ArrayList<TrabajadorCandidatoNovedadDTO>();
            List<TrabajadorCandidatoNovedadDTO> candidatoDTONoAprobado = new ArrayList<TrabajadorCandidatoNovedadDTO>();
            ListaDatoValidacionDTO listadoDatoValidacionDTO = new ListaDatoValidacionDTO();

            for (TrabajadorCandidatoNovedadDTO trabajadorValidar : candidatosDTO) {
                Boolean aprobado = Boolean.TRUE;
                Map<String, String> listaDatosValidacion = obtenerDatosValidacionMultipleNovedades(trabajadorValidar);
                List<ValidacionDTO> lstValidaciones = validarPersona(proceso, objetoValidacion, bloque, listaDatosValidacion);
                for (ValidacionDTO validacionAfiliacionDTO : lstValidaciones) {
                    /* si alguna de las validaciones no está aprobada no se validan el resto. */
                    if (!validacionAfiliacionDTO.getResultado().equals(ResultadoValidacionEnum.APROBADA)) {
                        aprobado = Boolean.FALSE;
                        break;
                    }
                }
                if (aprobado) {
                    candidatoDTOAprobado.add(trabajadorValidar);
                }
                else {
                    candidatoDTONoAprobado.add(trabajadorValidar);
                }
            }
            listadoDatoValidacionDTO.setCandidatoNovedadDTOAprobado(candidatoDTOAprobado);
            listadoDatoValidacionDTO.setCandidatoNovedadDTONoAprobado(candidatoDTONoAprobado);
            logger.debug("Finaliza método validarCargaMultipleNovedades("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque, Lis<Map<String, String>> datosValidacion)");
            return listadoDatoValidacionDTO;
        } catch (Exception e) {
            logger.error("termina método validarCargaMultipleNovedades(ProcesoEnum proceso, String objetoValidacion, "
                    + "String bloque, List<AfiliarTrabajadorCandidatoDTO> afiliarTrabajadorCandidatoDTO) con excepción " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + ": " + e);
        }
    }

    /**
     * Método encargado de obtener los datos de validacion de una carga multiple afiliaciones.
     *
     * @param candidatoDTO
     *        candidato a validarse.
     * @return datos de validacion.
     */
    private Map<String, String> obtenerDatosValidacionMultipleAfiliaciones(AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidato) {
        Map<String, String> datosValidacion = new HashMap<>();

        datosValidacion.put("fechaNacimiento",
                String.valueOf(afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getFechaNacimiento()));
        datosValidacion.put("numeroIdentificacion", afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getNumeroIdentificacion());
        datosValidacion.put("primerApellido", afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getPrimerApellido());
        datosValidacion.put("primerNombre", afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getPrimerNombre());
        datosValidacion.put("tipoIdentificacion",
                afiliarTrabajadorCandidato.getAfiliadoInDTO().getPersona().getTipoIdentificacion().toString());
        String numeroIdentificacionEmpleador = afiliarTrabajadorCandidato.getNumeroIdentificacionEmpleador();
        TipoIdentificacionEnum tipoIdentificacionEmpleador = afiliarTrabajadorCandidato.getTipoIdentificacionEmpleador();
        datosValidacion.put("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador);
        datosValidacion.put("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador.toString());
        datosValidacion.put("tipoAfiliado", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.toString());

        return datosValidacion;
    }

    /**
     * Método encargado de obtener los datos de validacion de una carga
     * multiple.
     *
     * @param candidatoDTO
     *        candidato a validarse.
     * @return datos de validacion.
     */
    private Map<String, String> obtenerDatosValidacionMultipleNovedades(TrabajadorCandidatoNovedadDTO candidatoNovedad) {
        Map<String, String> datosValidacion = new HashMap<>();
        // Datos del empleador
        datosValidacion.put("tipoIdentificacion", String.valueOf(candidatoNovedad.getPersonaDTO().getTipoIdentificacion()));
        datosValidacion.put("numeroIdentificacion", candidatoNovedad.getPersonaDTO().getNumeroIdentificacion());
        return datosValidacion;
    }

    /**
     * Método encargado de realizar la validaciones pertenecientes a la persona
     *
     * @param proceso,
     *        proceso el cual sera validado
     * @param objetoValidacion,
     *        objeto de validacion
     * @param bloque,
     *        bloque al que pertenece la validacion
     * @param datosValidacion,
     *        listado de los datos a validar
     * @return retorna el listado de dto de validacion
     */
    private List<ValidacionDTO> validarPersona(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                               Map<String, String> datosValidacion)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, ExecutionException{
        //try {
            logger.debug("Inicia método validarPersona("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque,Map<String, String> datosValidacion)");
            if (datosValidacion != null && objetoValidacion != null) {
                logger.debug("Estas son los datos de validacion que estan ingresando:  " + datosValidacion + " El proceso que realiza : " + proceso + " el bloque es:  "+ bloque + " este es el objeto de validacion:  " + objetoValidacion);
                datosValidacion.put(ConstantesValidaciones.NAMED_QUERY_PARAM_OBJ_VAL, objetoValidacion);
                datosValidacion.put(ConstantesValidaciones.NAMED_QUERY_PARAM_BLOQUE, bloque);
                if (!objetoValidacion.equals("")) {
                    datosValidacion.put(ConstantesValidaciones.CLASIFICACION_PARAM, objetoValidacion);
                }
            }
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(ConstantesValidaciones.NAMED_QUERY_PARAM_PROCESO, proceso);
            params.put(ConstantesValidaciones.NAMED_QUERY_PARAM_BLOQUE, bloque);
            if (objetoValidacion != null) {
                IValidable objetoValidacionEnum = ObjetoValidacionUtils.toObjetoValidacion(objetoValidacion);
                List<Object> objetoValidacionList = new ArrayList<>();
                if (objetoValidacionEnum instanceof ClasificacionEnum) {
                    objetoValidacionList.add(((ClasificacionEnum) objetoValidacionEnum).getSujetoTramite());
                }
                objetoValidacionList.add(objetoValidacionEnum);
                params.put(ConstantesValidaciones.NAMED_QUERY_PARAM_OBJ_VAL, objetoValidacionList);
            }
            logger.debug("Inicia método validarPersona("
                    + "ProcesoEnum proceso, String objetoValidacion, String bloque,Map<String, String> datosValidacion) con excepción");
            return ejecutarValidaciones(datosValidacion, params);
        /*} catch (Exception e) {
            logger.error("termina método validar(ProcesoEnum proceso, TipoAfiliadoEnum tipoAfiliado, "
                    + "BeneficiarioAfiliadoEnum tipoBeneficiario, String bloque, Map<String, String> datosValidacion) con excepción " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + ": " + e);
        }*/
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.validaciones.service.ValidacionesAPIService#
     * validadorNovedadesHabilitadas(com.asopagos.enumeraciones.core.
     * ProcesoEnum, java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ParametrizacionNovedadDTO> ValidadorNovedadesHabilitadasFovis(ProcesoEnum proceso, List<String> objetoValidacion,
                                                                              TipoNovedadEnum tipoNovedad, Map<String, String> datosValidacion) {
        try {
            logger.debug("Inicia método validadorNovedadesHabilitadas(ProcesoEnum proceso, Map<String,String> datos)" +objetoValidacion.size()+" objeto "+ objetoValidacion.get(0) );
            /*
             * se arman los parametros para consultar que validaciones realizar
             */
            List<IValidable> objetosValidables = new ArrayList<>();
            for (String objeto : objetoValidacion) {
                objeto = objeto.replaceAll("\\[|\\]", "");
                IValidable validable = ObjetoValidacionUtils.toObjetoValidacion(objeto);
                objetosValidables.add(validable);
                /* si el objeto es un clasificación */
                if (validable instanceof ClasificacionEnum) {
                    ClasificacionEnum clasificacion = (ClasificacionEnum) validable;
                    objetosValidables.add((IValidable) clasificacion.getSujetoTramite());
                }
            }

            /*logger.info("Objetos validables: " + objetosValidables);
            for (IValidable element : objetosValidables) {
            logger.info(element.toString());
            }       
            logger.info(proceso);*/

            logger.info("objetosValidables  " + objetosValidables);
            List<ValidacionProceso> validadores = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_NOVEDAD_POR_VALIDACIONES_NOVEDAD).setParameter("proceso", proceso)
                    .setParameter("objetoValidacion", objetosValidables).setParameter("filtro", "NOVEDAD").getResultList();
            for(ValidacionProceso  validacionProceso : validadores){
                logger.info("validacionnn--> " + validacionProceso.getBloque());
                logger.info("validacionnn--> " + validacionProceso.getValidacion());
                logger.info("validacionnn--> " + validacionProceso.getProceso());
                logger.info("validacionnn--> " + validacionProceso.getObjetoValidacion());
            }

            /* se ejecuta las validaciones */
            List<ValidacionDTO> validaciones = ejecutar(datosValidacion, validadores);
            /*
             * para las validaciones aprobadas se consulta se revisa cuales
             * novedades dependen de ella
             */
            Set<TipoTransaccionEnum> transaccionesNoHabilitadas = new HashSet<TipoTransaccionEnum>();
            Set<TipoTransaccionEnum> transacciones = new HashSet<TipoTransaccionEnum>();
            for (ValidacionDTO validacionAfiliacionDTO : validaciones) {
                TipoTransaccionEnum tipoTransaccion = null;
                for (ValidacionProceso validador : validadores) {
                    if (validador.getValidacion() != null && validador.getValidacion().equals(validacionAfiliacionDTO.getValidacion())
                            && validador.getBloque() != null && validador.getBloque().equals(validacionAfiliacionDTO.getBloque())) {
                        tipoTransaccion = TipoTransaccionEnum.valueOf(validador.getBloque().substring(8, validador.getBloque().length()));
                        if(tipoTransaccion == TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS_DEPWEB ||
                                tipoTransaccion == TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS ||
                                tipoTransaccion == TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS_WEB){
                            transaccionesNoHabilitadas.add(tipoTransaccion);
                        }
                        if (validacionAfiliacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)
                                || validacionAfiliacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_EVALUADA)) {
                            transaccionesNoHabilitadas.add(tipoTransaccion);
                        }
                        else {
                            transacciones.add(tipoTransaccion);
                        }
                    }
                }

            }
            // Mantis 0229083 - Ajuste para la presentacion de novedades que no ejecutan validaciones de habilitacion
            // De la parametrizacion en BD Se obtiene las novedades que no requieren validacion de habilitacion
            for (ValidacionProceso validador : validadores) {
                if (validador.getValidacion() == null) {
                    TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum
                            .valueOf(validador.getBloque().substring(8, validador.getBloque().length()));
                    transacciones.add(tipoTransaccion);
                }
            }
            transacciones.removeAll(transaccionesNoHabilitadas);

            logger.info("transacciones " +transacciones.toString());
            /*
             * se consultan las novedades y se convierten al dto para ser
             * retornadas
             */
            List<ParametrizacionNovedad> novedades = null;
            if (!transacciones.isEmpty()) {
                if (tipoNovedad != null) {
                    novedades = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_NOVEDAD_POR_TIPO_TRANSACCION_TIPO_NOVEDAD)
                            .setParameter("tipoTransaccion", transacciones).setParameter("tipoNovedad", tipoNovedad)
                            .setParameter("proceso", proceso).getResultList();
                    for(ParametrizacionNovedad novedad: novedades){
                        logger.info("novedad "  +novedad.getNovedad());
                        logger.info("novedad "  +novedad.getRutaCualificada());
                        logger.info("novedad "  +novedad.getTipoNovedad());
                    }
                }
                else {
                    novedades = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_NOVEDAD_POR_TIPO_TRANSACCION)
                            .setParameter("tipoTransaccion", transacciones).setParameter("proceso", proceso).getResultList();
                    for(ParametrizacionNovedad novedad: novedades){
                        logger.info("novedad "  +novedad.getNovedad());
                        logger.info("novedad "  +novedad.getRutaCualificada());
                        logger.info("novedad "  +novedad.getTipoNovedad());
                    }
                }
            }
            else {
                return null;
            }
            List<ParametrizacionNovedadDTO> novedadesDTO = new ArrayList<ParametrizacionNovedadDTO>();
            for (ParametrizacionNovedad novedad : novedades) {
                logger.info("novedad 2.0" +novedad.getNovedad());
                novedadesDTO.add(ParametrizacionNovedadDTO.convertNovedadToDTO(novedad));
            }
            logger.debug("Fin método validadorNovedadesHabilitadas(ProcesoEnum proceso, Map<String,String> datos)");
            return novedadesDTO;

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ValidacionDTO> validarReglasNegocio(ProcesoEnum proceso, String objetoValidacion, String bloque,
                                                    Map<String, String> datosValidacion) throws ExecutionException, ClassNotFoundException, InterruptedException, InstantiationException, IllegalAccessException {
        return validarPersona(proceso, objetoValidacion, bloque, datosValidacion);
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TipoTransaccionEnum> habilitarNovedadesAportes(TipoAfiliadoEnum tipoCotizante) {
        try {
            logger.debug("Inicio de método habilitarNovedadesAportes(TipoAfiliadoEnum tipoCotizante)");
            List<String> tiposTransaccion = new ArrayList<>();
            for (TipoTransaccionEnum tipoTransaccion : TipoTransaccionEnum.values()) {
                if (tipoTransaccion.getCodigoPila() != null) {
                    tiposTransaccion.add(tipoTransaccion.name());
                }
            }
            IValidable objetoValidacion = ObjetoValidacionUtils.toObjetoValidacion(tipoCotizante.name());
            Boolean esPensionado = false;
            if (TipoAfiliadoEnum.PENSIONADO.equals(tipoCotizante)) {
                esPensionado = true;
                objetoValidacion = ObjetoValidacionUtils.toObjetoValidacion(ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO.name());
            }
            List<String> tiposTransaccionFiltrados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_APORTES)
                    .setParameter("tipoAfiliado", objetoValidacion).setParameter("transacciones", tiposTransaccion).getResultList();

            List<TipoTransaccionEnum> tiposTransaccionHabilitados = new ArrayList<>();
            for (String tipoTransaccion : tiposTransaccionFiltrados) {
                TipoTransaccionEnum tipotransacc = TipoTransaccionEnum.valueOf(tipoTransaccion);
                // Se verifica contra las novedades permitidas por pila
                TipoNovedadPilaEnum tipoNovedad = null;
                if (tipotransacc.getCodigoPila() != null) {
                    tipoNovedad = TipoNovedadPilaEnum.obtenerTipoNovedadPila(tipotransacc.getCodigoPila());
                }
                if (esPensionado && tipoNovedad != null && tipoNovedad.getAplicaPensionado()) {
                    tiposTransaccionHabilitados.add(TipoTransaccionEnum.valueOf(tipoTransaccion));
                }
                else if(!esPensionado){
                    tiposTransaccionHabilitados.add(TipoTransaccionEnum.valueOf(tipoTransaccion));
                }
            }
            tiposTransaccionHabilitados.add(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO);
            if (esPensionado) {
                tiposTransaccionHabilitados.add(TipoTransaccionEnum.SUSPENSION_PENSIONADO_SUS);
            }
            logger.debug("Fin de método habilitarNovedadesAportes(TipoAfiliadoEnum tipoCotizante)");
            return tiposTransaccionHabilitados;

        } catch (Exception e) {
            logger.error("Ocurrio un error en habilitarNovedadesAportes", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*@Override
    public ValidacionDTO verificarSolicitudesEnCurso(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,
                                                     Long idSolicitud, Boolean esNovedad, UserDTO userDTO, TipoTransaccionEnum tipoNovedad) {
        logger.debug("Inicio de método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)");

        List<String> estadosNovedad = new ArrayList<String>();
        List<EstadoSolicitudAfiliacionPersonaEnum> estadosAfiliacion = new ArrayList<EstadoSolicitudAfiliacionPersonaEnum>();
        List<Object> estadosSolicitudesAfiliacion = new ArrayList<Object>();
        List<Object> idSolicitudesNovedad = new ArrayList<Object>();
        List<Object> tipoSolicitudNovedad = new ArrayList<Object>();
        ValidacionDTO validacion = new ValidacionDTO();
        String proceso = esNovedad != null && esNovedad ? "novedad" : "afiliación";

        try {
            validacion.setResultado(ResultadoValidacionEnum.APROBADA);

            estadosNovedad.add(EstadoSolicitudNovedadEnum.CERRADA.name());
            estadosNovedad.add(EstadoSolicitudNovedadEnum.CANCELADA.name());
            estadosNovedad.add(EstadoSolicitudNovedadEnum.RECHAZADA.name());
            estadosNovedad.add(EstadoSolicitudNovedadEnum.DESISTIDA.name());
            estadosNovedad.add(EstadoSolicitudNovedadEnum.APROBADA.name());

            // Lista de novedades permitidas
           List<String> novedadesPermitidas = Arrays.asList(
                "ACTIVAR_BENEFICIARIO_",
                "ACTIVAR_BENEFICIO_",
                "ACTIVA_BENEFICIARIO_",
                "INACTIVAR_BENEFICIARIO_",
                "INACTIVAR_BENEFICIO_",
                "INACTIVAR_BENEFICIOS_PADRE",
                "INACTIVAR_BENEFICIOS_MADRE",
                "ACTUALIZACION_CERTIFICADO_ESTUDIOS_",
                "ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_",
                "REPORTE_FALLECIMIENTO_",
                "CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_",
                "REPORTE_INVALIDEZ_",
                "CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_",
                "CAMBIO_MEDIO_PAGO_"
            ); 

            // Novedad a registrar
            String novedadARegistrar = (tipoNovedad != null) ? tipoNovedad.name() : "";
            logger.info("Novedad Nueva a Realizar: " + novedadARegistrar);

            // Se verifica si la novedad está permitida
            boolean novedadPermitida = novedadesPermitidas.stream()
                                         .anyMatch(novedadARegistrar::contains);

            //Se eximen temporalmente algunos canales de recepcion que no aplican las novedades correctamente
            ArrayList<String> canalesRecepcion = new ArrayList<String>();
            canalesRecepcion.add(CanalRecepcionEnum.PRESENCIAL_INT.name());
            canalesRecepcion.add(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR.name());
            canalesRecepcion.add(CanalRecepcionEnum.PILA.name());

            idSolicitudesNovedad = (List<Object>)entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_NOVEDAD_EN_CURSO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("estados", estadosNovedad)
                    .setParameter("canales", canalesRecepcion)
                    .getResultList();

            tipoSolicitudNovedad = (List<Object>)entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITUD_NOVEDAD_EN_CURSO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("estados", estadosNovedad)
                    .setParameter("canales", canalesRecepcion)
                    .getResultList();

            if (!idSolicitudesNovedad.isEmpty()) {
                // Si es una novedad permitida, dejarla pasar
                if (!tipoSolicitudNovedad.isEmpty()) {
                    // Verifica si alguna novedad en curso coincide exactamente con la que se intenta registrar
                    boolean novedadYaIniciada = tipoSolicitudNovedad.stream()
                            .map(Object::toString) 
                            .anyMatch(novedadEnCurso -> novedadEnCurso.contains(novedadARegistrar));

                    // Si la novedad ya está iniciada y no ha finalizado
                    if (novedadYaIniciada) {
                        logger.info("La novedad " + novedadARegistrar + " ya está en curso y no puede ser iniciada nuevamente.");
                        validacion.setResultado(ResultadoValidacionEnum.NO_APROBADA);
                        validacion.setDetalle(Interpolator.interpolate(
                            "No es posible comenzar un proceso de solictud de novedad debido a que la novedad que se quiere realizar ya esta en curso",
                            proceso));
                        validacion.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                        return validacion;
                    }
                    // Si la novedad está permitida pero no hay conflicto con las iniciadas
                    if (novedadPermitida) {
                        logger.info("Novedad permitida " + novedadARegistrar + ", continuando con el flujo de novedad.");
                        return validacion;
                    }
                }else{
                    validacion.setResultado(ResultadoValidacionEnum.NO_APROBADA);
                    validacion.setDetalle(Interpolator.interpolate(myResources.getString(ConstantesValidaciones.KEY_MSG_SOLICITUD_NOVEDAD_RADICADA_EN_CURSO), proceso));
                    validacion.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    logger.debug("Fin de método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)");
                    return validacion;
                }
            }

            estadosAfiliacion.add(EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
            estadosAfiliacion.add(EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);
            estadosAfiliacion.add(EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
            estadosAfiliacion.add(EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA);
            estadosAfiliacion.add(EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION);

            estadosSolicitudesAfiliacion = (List<Object>)entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_AFILIACION_EN_CURSO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("idSolicitud", idSolicitud)
                    .getResultList();

            for (Object estado : estadosSolicitudesAfiliacion) {

                if (EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA.equals(EstadoSolicitudAfiliacionPersonaEnum.valueOf(estado.toString()))) {
                    validacion.setResultado(ResultadoValidacionEnum.NO_APROBADA);
                    validacion.setDetalle(Interpolator.interpolate(myResources.getString(ConstantesValidaciones.KEY_MSG_SOLICITUD_AFILIACION_PRE_RADICADA_EN_CURSO), proceso));
                    validacion.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

                    logger.debug("Fin de método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)");
                    return validacion;
                }
                if (!estadosAfiliacion.contains(EstadoSolicitudAfiliacionPersonaEnum.valueOf(estado.toString()))) {
                    validacion.setResultado(ResultadoValidacionEnum.NO_APROBADA);
                    validacion.setDetalle(Interpolator.interpolate(myResources.getString(ConstantesValidaciones.KEY_MSG_SOLICITUD_AFILIACION_RADICADA_EN_CURSO), proceso));
                    validacion.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

                    logger.debug("Fin de método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)");
                    return validacion;
                }
            }
        } catch (Exception e) {
            validacion.setResultado(ResultadoValidacionEnum.NO_EVALUADA);
            validacion.setDetalle(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV));
            validacion.setTipoExcepcion(TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);

            logger.error("Ocurrió un error en el método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)",e);
            return validacion;
        }

        logger.info("Resultado : " + validacion.getResultado());
        logger.debug("Fin de método verificarSolicitudesEnCurso(String, TipoIdentificacionEnum)");
        return validacion;
    }*/

    @Override
    public ConsultaRegistroCivilDTO consultarDatosRegistraduriaNacional(ParametrosRegistroCivilDTO parametro, UserDTO userDTO)throws Exception {
        logger.info("Comienza llamado servicios consultarDatosRegistroCivil");
        Object parametroConsulta = null;
        ConsultaRegistroCivilDTO consultaRegistro = new ConsultaRegistroCivilDTO();
        Object[] datosRegistraduriaDB = null;

        Object consultaRegistraduria = CacheManager.getParametroGap(ParametrosGapConstants.CONSULTA_REGISTRADURIA);
        logger.info(consultaRegistraduria);

        if (consultaRegistraduria.toString().equals("ACTIVO")) {
            Boolean hacerConsultaAPI = false;
            DatosRegistroNacionalAud auditoria = new DatosRegistroNacionalAud();

            Map<String, Object> objeto = new HashMap<String, Object>();
            //String token = getToken(parametros, persona);
            Map<String, Object> wsResponse = new HashMap<String, Object>();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            if (parametro.getTipoBusqueda().equals("REG")) {
                logger.info("REG");
                objeto.put("nombre1Inscrito", parametro.getNombre1() != null ? parametro.getNombre1() : "");
                objeto.put("nombre2Inscrito", parametro.getNombre2() != null ? parametro.getNombre2() : "");
                objeto.put("apellido1Inscrito", parametro.getApellido1() != null ? parametro.getApellido1() : "");
                objeto.put("apellido2Inscrito", parametro.getApellido2() != null ? parametro.getApellido2() : "");
                objeto.put("fechaNacInscrito", parametro.getFechaNac() != null ? parametro.getFechaNac() : "");
                objeto.put("sexoInscrito", parametro.getGenero() != null ? parametro.getGenero() : "");

            }
            try {

                if (parametro.getTipoBusqueda().equals("CC")) {
                    logger.info("CC");
                    objeto.put("Cedula", parametro.getCedula() != null ? parametro.getCedula() : "");
                    logger.info(objeto.get("cedula"));

                    datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                            .setParameter("tipoIdentificacion", TipoIdentificacionEnum.CEDULA_CIUDADANIA.name()).setParameter("numeroIdentificacion", parametro.getCedula()).getResultList().get(0);
                    ;

                } else if (parametro.getTipoBusqueda().equals("REGN")) {
                    logger.info("REGN");
                    objeto.put("nuip", parametro.getNuip() != null ? parametro.getNuip() : "");
                    logger.info(objeto.get("nuip"));

                    datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                            .setParameter("tipoIdentificacion", TipoIdentificacionEnum.TARJETA_IDENTIDAD.name()).setParameter("numeroIdentificacion", parametro.getNuip()).getResultList().get(0);
                    ;

                } else if (parametro.getTipoBusqueda().equals("REGS")) {
                    logger.info("REGS");
                    objeto.put("nuip", parametro.getSerial() != null ? parametro.getSerial() : "");
                    logger.info(objeto.get("Serial"));

                    datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                            .setParameter("tipoIdentificacion", TipoIdentificacionEnum.REGISTRO_CIVIL.name()).setParameter("numeroIdentificacion", parametro.getSerial()).getResultList().get(0);
                    ;
                }

                consultaRegistro.setFechaNacInscrito(datosRegistraduriaDB[0] != null ? datosRegistraduriaDB[0].toString() : null);
                consultaRegistro.setApellido1Inscrito(datosRegistraduriaDB[1] != null ? datosRegistraduriaDB[1].toString() : null);
                consultaRegistro.setApellido2Inscrito(datosRegistraduriaDB[2] != null ? datosRegistraduriaDB[2].toString() : null);
                consultaRegistro.setNombre1Inscrito(datosRegistraduriaDB[3] != null ? datosRegistraduriaDB[3].toString() : null);
                consultaRegistro.setNombre2Inscrito(datosRegistraduriaDB[4] != null ? datosRegistraduriaDB[4].toString() : null);
                consultaRegistro.setFechaExpedicionCedula(datosRegistraduriaDB[5] != null ? datosRegistraduriaDB[5].toString() : datosRegistraduriaDB[6].toString());
                if (datosRegistraduriaDB[7] != null) {
                    consultaRegistro.setGenero(GeneroEnum.valueOf(datosRegistraduriaDB[7].toString()));

                } else if (datosRegistraduriaDB[8] != null) {
                    consultaRegistro.setGenero(GeneroEnum.valueOf(datosRegistraduriaDB[8].toString()));

                } else if (datosRegistraduriaDB[8] == null && datosRegistraduriaDB[7] == null) {
                    consultaRegistro.setGenero(null);

                }
                consultaRegistro.setEstadoDescCedula(datosRegistraduriaDB[9] != null ? datosRegistraduriaDB[9].toString() : null);
                consultaRegistro.setEstadoDesc(datosRegistraduriaDB[10] != null ? datosRegistraduriaDB[10].toString() : null);


            } catch (NoResultException e) {
                logger.info("catch " + e);
                hacerConsultaAPI = true;
            } catch (Exception e) {
                logger.error(e);
                hacerConsultaAPI = true;

            }

            if (hacerConsultaAPI == true) {
                try{
                    AutenticacionTokenDTO token = registraduriaToken();
                    logger.info(token.toString());
                    logger.info("Validación" + token.getToken());
                    if (parametro.getTipoBusqueda().equals("CC")) {
                        //wsResponse = HttpWrapper.sendHttpPostJSON("https://638f77349cbdb0dbe32859e1.mockapi.io/api/v1/data", json, token);
                        wsResponse = HttpWrapper.sendHttpPost(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_CEDULA).toString(), objeto, token.getToken(), certificadoSSL());
                        consultaRegistro.setNumeroIdentificacion(parametro.getCedula());
                        consultaRegistro.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA_CIUDADANIA);

                        auditoria.setNumeroIdentificacion(parametro.getCedula());
                        auditoria.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
                        Date fechaIni = Calendar.getInstance().getTime();
                        auditoria.setFechaInicio(format.format(fechaIni));
                        auditoria.setWebService(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_CEDULA).toString());
                        Date fechaFin = Calendar.getInstance().getTime();
                        auditoria.setFechaFinal(format.format(fechaFin));

                    } else if (parametro.getTipoBusqueda().equals("REGN")) {
                        wsResponse = HttpWrapper.sendHttpPost(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_NUIP).toString(), objeto, token.getToken(), certificadoSSL());
                        consultaRegistro.setNumeroIdentificacion(parametro.getNuip());
                        consultaRegistro.setTipoIdentificacion(TipoIdentificacionEnum.TARJETA_IDENTIDAD);

                        auditoria.setNumeroIdentificacion(parametro.getNuip());
                        auditoria.setTipoIdentificacion(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
                        Date fechaIni = Calendar.getInstance().getTime();
                        auditoria.setFechaInicio(format.format(fechaIni));
                        auditoria.setWebService(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_NUIP).toString());
                        Date fechaFin = Calendar.getInstance().getTime();
                        auditoria.setFechaFinal(format.format(fechaFin));

                    } else if (parametro.getTipoBusqueda().equals("REGS")) {
                        wsResponse = HttpWrapper.sendHttpPost(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_NUIP).toString(), objeto, token.getToken(), certificadoSSL());
                        consultaRegistro.setNumeroIdentificacion(parametro.getSerial());
                        consultaRegistro.setTipoIdentificacion(TipoIdentificacionEnum.REGISTRO_CIVIL);

                        auditoria.setNumeroIdentificacion(parametro.getSerial());
                        auditoria.setTipoIdentificacion(TipoIdentificacionEnum.REGISTRO_CIVIL);
                        Date fechaIni = Calendar.getInstance().getTime();
                        auditoria.setFechaInicio(format.format(fechaIni));
                        auditoria.setWebService(CacheManager.getParametro(ParametrosSistemaConstants.REG_URL_API_NUIP).toString());
                        Date fechaFin = Calendar.getInstance().getTime();
                        auditoria.setFechaFinal(format.format(fechaFin));
                    }

                    auditoria.setConsulta(wsResponse.toString());

                    if (wsResponse.get("status").equals("KO")) {
                        logger.info("WS-ERROR");
                        auditoria.setMarca("consulta intentada registraduría");
                        auditoria.setRespuesta("Consulta fallida");


                        //fallidos++;
                        //guardarBitacoraAfiliacionPrimeraVez(new Integer(obj[0].toString()), "SI",parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()),json,wsResponse);
                        //logger.info("APV - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    } else {
                        logger.info(wsResponse);
                        auditoria.setRespuesta("Consulta exitosa");
                        auditoria.setMarca("consultado en registraduría");
                        Map<String, Object> retorno = new HashMap<>();
                        try {
                            retorno = JsonUtil.parseJson(wsResponse.get("mensaje").toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        logger.info("retorno: " + retorno);
                        logger.info("ok " + retorno.get("ok").toString());
                        logger.info(retorno.get("ok").toString().equals("true"));
                        String data = "";
                        String dataCedula = "";
                        String dataConsulta = "";
                        DatoRegistraduriaNacional datoRegistraduriaNacional = new DatoRegistraduriaNacional();
                        if (retorno.get("ok").toString().equals("true")) {
                            if (parametro.getTipoBusqueda().equals("REGN") || parametro.getTipoBusqueda().equals("REGS")) {
                                logger.info("registro civil");
                                try {
                                    data = wsResponse.get("mensaje").toString().substring(wsResponse.get("mensaje").toString().indexOf("{\"apellido1Inscrito"), wsResponse.get("mensaje").toString().indexOf("reducible") - 3);

                                    logger.info("datos : " + data);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                                Map<String, Object> datos = new HashMap<>();
                                try {
                                    datos = JsonUtil.parseJson(data);
                                } catch (Exception e) {
                                    logger.error("error _" + e);
                                }
                                logger.info("datos 3: " + datos);

                                consultaRegistro.convertToDTO(datos, parametro.getTipoBusqueda());

                                if (consultaRegistro.getEstadoDescCedula() == "ANOMALO INVALIDO" || consultaRegistro.getEstadoDescCedula() == "ANOMALO REEMPLAZADO INVALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "ANOMALO REEMPLAZADO VALIDO" || consultaRegistro.getEstadoDescCedula() == "ANOMALO VALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "INVALIDO" || consultaRegistro.getEstadoDescCedula() == "REEMPLAZADO INVALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "BORRADO" || consultaRegistro.getEstadoDescCedula() == "POR AUTORIZAR") {

                                    auditoria.setMarca("consulta intentada registraduría");

                                }
                                auditoria.setEstadoDocumento(consultaRegistro.getEstadoDesc());
                                datoRegistraduriaNacional = consultaRegistro.convertToEntity();
                                auditoria.setUsuario(userDTO.getNombreUsuario());
                                entityManager.persist(auditoria);

                                entityManager.persist(datoRegistraduriaNacional);
                                logger.info(consultaRegistro.toString());
                            } else if (parametro.getTipoBusqueda().equals("CC")) {
                                logger.info("cedula");
                                try {
                                    dataCedula = wsResponse.get("mensaje").toString().substring(wsResponse.get("mensaje").toString().indexOf("{\"nuip"), wsResponse.get("mensaje").toString().indexOf("cedulasNotValidated") - 3);
                                    dataConsulta = wsResponse.get("mensaje").toString().substring(wsResponse.get("mensaje").toString().indexOf("{\"numeroControl"), wsResponse.get("mensaje").toString().indexOf("}"));

                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                Map<String, Object> datos = new HashMap<>();
                                logger.info("datocedula " + dataCedula);
                                datos = JsonUtil.parseJson(dataCedula);
                                logger.info("datos " + datos);


                                logger.info(datos.get("primerNombre"));
                                logger.info(wsResponse.get("primerNombre"));
                                consultaRegistro.convertToDTO(datos, parametro.getTipoBusqueda());

                                logger.info("estado " + consultaRegistro.getEstadoDescCedula());
                                if (consultaRegistro.getEstadoDescCedula() == "ANOMALO INVALIDO" || consultaRegistro.getEstadoDescCedula() == "ANOMALO REEMPLAZADO INVALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "ANOMALO REEMPLAZADO VALIDO" || consultaRegistro.getEstadoDescCedula() == "ANOMALO VALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "INVALIDO" || consultaRegistro.getEstadoDescCedula() == "REEMPLAZADO INVALIDO" ||
                                        consultaRegistro.getEstadoDescCedula() == "BORRADO" || consultaRegistro.getEstadoDescCedula() == "POR AUTORIZAR") {

                                    auditoria.setMarca("consulta intentada registraduría");

                                }
                                auditoria.setEstadoDocumento(consultaRegistro.getEstadoDescCedula());

                                datoRegistraduriaNacional = consultaRegistro.convertToEntity();
                                consultaRegistro.setDescripcionError(datos.get("descripcionError") != null ? datos.get("descripcionError").toString() : "");
                                consultaRegistro.setFechaHoraConsulta(datos.get("fechaHoraConsulta") != null ? datos.get("fechaHoraConsulta").toString() : "");
                                consultaRegistro.setNumeroControl(datos.get("numeroCOntrol") != null ? datos.get("numeroCOntrol").toString() : "");
                                logger.info(consultaRegistro.toString());
                                datoRegistraduriaNacional.setNumeroIdentificacion(parametro.getCedula());
                                datoRegistraduriaNacional.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
                                consultaRegistro.setFechaNacInscrito(datoRegistraduriaNacional.getFechaNacimientoCED() != null ? datoRegistraduriaNacional.getFechaNacimientoCED().toString() : datoRegistraduriaNacional.getFechaNacimientoREG().toString());
                                auditoria.setUsuario(userDTO.getNombreUsuario());
                                entityManager.persist(datoRegistraduriaNacional);
                                entityManager.persist(auditoria);


                            }
                        } else {
                            auditoria.setMarca("consulta intentada registraduría");
                            auditoria.setRespuesta("Consulta fallida");
                            auditoria.setUsuario(userDTO.getNombreUsuario());
                            entityManager.persist(auditoria);

                        }
                    }

                }catch(Exception e){
                    logger.error("error consulta registraduria " +e);
                }

            }
        }
        try {
            if (parametro.getTipoBusqueda().equals("CC")) {

                datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                        .setParameter("tipoIdentificacion", TipoIdentificacionEnum.CEDULA_CIUDADANIA.name()).setParameter("numeroIdentificacion", parametro.getCedula()).getResultList().get(0);

            } else if (parametro.getTipoBusqueda().equals("REGN")) {
                datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                        .setParameter("tipoIdentificacion", TipoIdentificacionEnum.TARJETA_IDENTIDAD.name()).setParameter("numeroIdentificacion", parametro.getNuip()).getResultList().get(0);

            } else if (parametro.getTipoBusqueda().equals("REGS")) {

                datosRegistraduriaDB = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL)
                        .setParameter("tipoIdentificacion", TipoIdentificacionEnum.REGISTRO_CIVIL.name()).setParameter("numeroIdentificacion", parametro.getSerial()).getResultList().get(0);
            }
            consultaRegistro.setFechaNacInscrito(datosRegistraduriaDB[0] != null ? datosRegistraduriaDB[0].toString() : null);
            consultaRegistro.setFechaExpedicionCedula(datosRegistraduriaDB[5] != null ? datosRegistraduriaDB[5].toString() : datosRegistraduriaDB[6].toString());


        } catch (NoResultException e) {
        } catch (Exception e) {
            logger.error(e);

        }
        logger.info("Termina llamado servicios consultarDatosRegistroCivil");
        return consultaRegistro;
    }


    public AutenticacionTokenDTO registraduriaToken() throws Exception {

        String uri = (String) CacheManager.getParametro(ParametrosSistemaConstants.REG_URI_TOKENSERV);
        String user = (String) CacheManager.getParametro(ParametrosSistemaConstants.REG_USER_TOKENSERV);
        String pass = (String) CacheManager.getParametro(ParametrosSistemaConstants.REG_PASS_TOKENSERV);

        logger.info(uri + " user:"+ user + " pass:" + pass);

        Client client = ClientBuilder.newBuilder().sslContext(certificadoSSL()).build();
        WebTarget webTarget = client.target(uri)
                .queryParam("User", user)
                .queryParam("Pass", pass);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        logger.info("Response 1***");
        Response response  = invocationBuilder.post(Entity.entity("AutenticacionTokenDTO", MediaType.APPLICATION_JSON_TYPE));

        AutenticacionTokenDTO result = response.readEntity(AutenticacionTokenDTO.class);

        return result;
    }

    public SSLContext certificadoSSL() throws KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyManagementException{

        char[] passphrase = CacheManager.getParametro(ParametrosSistemaConstants.REG_PASSWORD_CERTIFICADO).toString().toCharArray();
        String bucket = (String) CacheManager.getParametro(ParametrosSistemaConstants.REG_BUCKET_CERTIFICADO);
        String fileName = (String) CacheManager.getParametro(ParametrosSistemaConstants.REG_FILENAME_CERTIFICADO);

        logger.info(passphrase + " bucket:" + bucket + " fileName:" + fileName);

        //Using Google Cloud Storage
        Storage storage = StorageOptions.getDefaultInstance().getService();

        BlobId id = BlobId.of(bucket, fileName);
        byte[] content = storage.readAllBytes(id);
        InputStream inputStream = new ByteArrayInputStream(content);

        // First initialize the key and trust material
        KeyStore ksKeys = KeyStore.getInstance("PKCS12");
        ksKeys.load(inputStream, passphrase);

        // Get an instance of SSLContext for DTLS protocols
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(ksKeys, passphrase)
                .build();

        return sslContext;
    }

    @Override
    public Boolean existeRegistraduriaNacional(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.info(tipoIdentificacion +" "+numeroIdentificacion);
        try{
            List<String> estadosNovedad = new ArrayList<String>();
            estadosNovedad.add("ANOMALO INVALIDO");
            estadosNovedad.add("ANOMALO REEMPLAZADO INVALIDO");
            estadosNovedad.add("ANOMALO REEMPLAZADO VALIDO");
            estadosNovedad.add("ANOMALO VALIDO");
            estadosNovedad.add("INVALIDO");
            estadosNovedad.add("REEMPLAZADO INVALIDO");
            estadosNovedad.add("BORRADO");
            estadosNovedad.add("POR AUTORIZAR");
            Object datosRegistraduriaDB = (Object) entityManager.createNamedQuery(NamedQueriesConstants.EXISTE_REGISTRADURIA_NACIONAL)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("estadosNovedad", estadosNovedad).getResultList().get(0);
            logger.info(datosRegistraduriaDB);
        }catch(NoResultException e){
            logger.info(e);
            return false;
        }catch(IndexOutOfBoundsException e){
            logger.info(e);
            return false;
        }
        catch(Exception e){
            logger.info(e);
            return false;
        }
        return true;
    }
    @Override
    public Map<String, String> validarConyugeCuidador(Long idAfiliado,TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion) {
        Map<String, String> datos = new HashMap<String, String>();
        List<Object[]> validaciones = new ArrayList<Object[]>();
        try {
            validaciones = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_CONYUGE_CUIDADOR)
                    .setParameter("idAfiliado", idAfiliado)
                    .setParameter("tipoIdentificacionBen", tipoIdentificacion)
                    .setParameter("numeroIdentificacionBen", numeroIdentificacion)
                    .getResultList();
        }
        catch (Exception e) {
            logger.error("error validarConyugeCuidador "+e);
            datos.put("activoAfiliado", "false");
            datos.put("activoBeneficiario", "false");
            datos.put("labora", "false");
            datos.put("salarioMensual", "false");
            datos.put("tipoIdentificacion", null);
            datos.put("numeroIdentificacion", null);
            datos.put("razonSocial", null);
            datos.put("tieneConyugeCuidador", "false");
            return datos;

        }

        if(!validaciones.isEmpty()){
            logger.info("validarConyugeCuidador " +validaciones);
            for (Object[] validacion : validaciones) {
                if (datos.get("activoAfiliado") != "false") {
                    datos.put("activoAfiliado", validacion[0] != null ? validacion[0].toString() : "false");
                }
                if (datos.get("activoBeneficiario") != "false") {
                    datos.put("activoBeneficiario", validacion[1] != null ? validacion[1].toString() : "false");

                }
                if (datos.get("labora") != "false") {
                    datos.put("labora", validacion[2] != null ? validacion[2].toString() : "false");
                }
                if (datos.get("salarioMensual") != "false") {
                    datos.put("salarioMensual", validacion[3] != null ? validacion[3].toString() : "false");
                }
                if (datos.get("tieneConyugeCuidador") != "false") {
                    datos.put("tieneConyugeCuidador", validacion[8] != null ? validacion[8].toString() : "false");
                }
                datos.put("tipoIdentificacion", validacion[4] != null ? validacion[4].toString() : null);
                datos.put("numeroIdentificacion", validacion[5] != null ? validacion[5].toString() : null);
                datos.put("razonSocial", validacion[6] != null ? validacion[6].toString() : null);
                datos.put("perid", validacion[7] != null ? validacion[7].toString() : null);

            }
        }else{
            logger.info("lista vacia");
            datos.put("activoAfiliado", "false");
            datos.put("activoBeneficiario", "false");
            datos.put("labora", "false");
            datos.put("salarioMensual", "false");
            datos.put("tipoIdentificacion", null);
            datos.put("numeroIdentificacion", null);
            datos.put("razonSocial", null);
            datos.put("tieneConyugeCuidador", "false");
            return datos;
        }



        return datos;
    }

}
