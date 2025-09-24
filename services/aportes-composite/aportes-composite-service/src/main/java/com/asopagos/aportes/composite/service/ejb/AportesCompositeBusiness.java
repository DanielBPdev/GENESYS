package com.asopagos.aportes.composite.service.ejb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.math.BigInteger;

import com.asopagos.entidades.ccf.aportes.ControladorCarteraAportes;
import com.asopagos.entidades.ccf.aportes.ControladorCarteraPlanilla;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.aportes.composite.clients.*;

import com.asopagos.aportes.composite.clients.RegistrarNovedadConTipoTransaccionAportesCompositeAsync;
import com.asopagos.aportes.composite.dto.RegistrarNovedadConTransaccionDTO;
import javax.persistence.ParameterMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.asopagos.aportes.composite.service.constants.NamedQueriesConstants;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import javax.ws.rs.QueryParam;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliado;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.afiliados.clients.ConsultarEstadoAfiliacionRespectoCCF;
import com.asopagos.afiliados.clients.ConsultarEstadoRolAfiliadoConEmpleador;
import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import com.asopagos.aportes.clients.ActualizacionAportesRecalculados;
import com.asopagos.aportes.clients.ActualizarMarcaProcesoTemporales;
import com.asopagos.aportes.clients.ActualizarTemAporteProcesado;
import com.asopagos.aportes.clients.ActualizarTemAporteProcesadoByIdPlanilla;
import com.asopagos.aportes.clients.ConsultarAportesGeneralesPorIdRegGeneral;
import com.asopagos.aportes.clients.ConsultarAportesPlanillasRegistrarProcesar;
import com.asopagos.aportes.clients.ConsultarDatosComunicado;
import com.asopagos.aportes.clients.ConsultarDatosComunicadoByIdPlanilla;
import com.asopagos.aportes.clients.ConsultarInformacionNovedadesRegistrarProcesar;
import com.asopagos.aportes.clients.ConsultarInformacionNovedadesRegistrarProcesarByIdPlanilla;
import com.asopagos.aportes.clients.ConsultarInformacionNovedadesRegistrarProcesarFuturas;
import com.asopagos.aportes.clients.ConsultarInformacionPlanillasRegistrarProcesar;
import com.asopagos.aportes.clients.ConsultarNovedadesPlanillasRegistrarProcesar;

import com.asopagos.aportes.clients.ConsultarPlanilla;
import com.asopagos.aportes.clients.ConsultarRegistroDetalladoPorId;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralId;
import com.asopagos.aportes.clients.ConsultarTasasInteresMoraAportes;
import com.asopagos.aportes.clients.ContarTemAportesPendientes;
import com.asopagos.aportes.clients.CopiarDatosAportesPlanilla;
import com.asopagos.aportes.clients.CrearNotificacionesParametrizadas;
import com.asopagos.aportes.clients.CrearPilaEstadoTransitorio;
import com.asopagos.aportes.clients.CrearTasaInteresInteresMora;
import com.asopagos.aportes.clients.CrearTemAporteProcesadosNuevos;
import com.asopagos.aportes.clients.EliminarTemAporteProcesado;
import com.asopagos.aportes.clients.EliminarTemporalesAporte;
import com.asopagos.aportes.clients.EliminarTemporalesNovedad;
import com.asopagos.aportes.clients.ModificarTasaInteresMoraAportes;
import com.asopagos.aportes.clients.PrepararProcesoRegistroAportes;
import com.asopagos.aportes.clients.ProcesarPaqueteAportesDetallados;
import com.asopagos.aportes.clients.ProcesarPaqueteAportesGenerales;
import com.asopagos.aportes.clients.BuscarNotificacionPlanillasN;
import com.asopagos.aportes.clients.ConsultarPlanillaNNotificar;

import com.asopagos.aportes.composite.clients.ProcesarPlanillaBandejaTransitoria;
import com.asopagos.aportes.composite.dto.ProcesoIngresoUtilitarioDTO;
import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import com.asopagos.aportes.composite.dto.RegistrarNovedadesPilaServiceDTO;
import com.asopagos.aportes.composite.service.AportesCompositeService;
import com.asopagos.aportes.composite.service.constants.ConstantesMayaValidacion;
import com.asopagos.aportes.composite.service.interfaces.IAportesNovedadesLocal;
import com.asopagos.aportes.composite.util.FuncionesUtilitarias;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultaPlanillaResultDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.DatosPersistenciaAportesDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.aportes.dto.PaqueteProcesoAportesDTO;
import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.ResultadoProcesoAportesDTO;
import com.asopagos.bandejainconsistencias.clients.ActualizarEstadoBandejaTransitoriaGestion;
import com.asopagos.bandejainconsistencias.clients.ActualizarEstadoEnProcesoAportes;
import com.asopagos.bandejainconsistencias.clients.BandejaTransitoriaGestion;
import com.asopagos.bandejainconsistencias.clients.ConsultarRegistroGeneralxRegistroControl;
import com.asopagos.bandejainconsistencias.dto.DatosBandejaTransitoriaDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCartera;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.aportes.AportePilaDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.cartera.NovedadCarteraDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.clients.VerificarExisteEmpleadorAsociado;
import com.asopagos.empresas.clients.ConsultarEmpresaPorId;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresa;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.enumeraciones.aportes.PilaEstadoTransitorioEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.EstadoGestionEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.clients.GuardarRegistroPersonaInconsistencia;

import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.pila.clients.ActualizarEstadosRegistroPlanilla;
import com.asopagos.pila.clients.IniciarVariablesGenerales;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliadosrutines.activarafiliado.ActivarAfiliadoRutine;
import com.asopagos.rutine.novedadescompositerutines.procesaractivacionbeneficiarioPILA.ProcesarActivacionBeneficiarioPILARutine;
import com.asopagos.usuarios.clients.ActualizarUsuarioCCF;
import com.asopagos.usuarios.clients.ConsultarUsuarios;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAportes;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.novedades.clients.GuardarExcepcionNovedadPila;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;
import com.asopagos.aportes.composite.service.business.interfaces.IConsultasModeloCoreComposite;
import com.asopagos.rutine.afiliadosrutines.deshacergestioncerotrabajadores.DeshacerGestionCeroTrabajadoresRutine;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.afiliados.clients.ActivarAfiliado;
import com.asopagos.aportes.clients.ValidarProcesadoNovedad;

import com.asopagos.aportes.clients.ConsultarNovedadesYaProcesadasCORE;
import com.asopagos.empleadores.clients.ActualizarEstadoEmpleadorPorAportes;
import com.asopagos.afiliaciones.empleadores.composite.clients.CrearSolicitudAfiliacionEmpleadorAportes;
import java.util.stream.Collectors;


//import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAfiliacionPersona;
//import com.asopagos.afiliaciones.personas.composite.clients.RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados;

/**
 * <b>Descripción:</b> Clase que implementa los métodos de negocio relacionados
 * con el proceso de registro o relación de aportes
 *
 * <b>Módulo:</b> Asopagos - HU-211-397, HU-211-403, HU-211-404, HU-211-405,
 * HU-211-399, HU-211-392<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <anbuitrago@heinsohn.com.co>Andres Felipe Buitrago</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 * @author <a href="mailto:squintero@heinsohn.com.co">Steven Quintero
 * González.</a>
 */
/**
 * @author Juan Diego
 *
 */
@Stateless
public class AportesCompositeBusiness implements AportesCompositeService {

    @PersistenceContext(unitName = "pila_PU")
    private EntityManager entityManagerPila;

    @PersistenceContext(unitName = "core_PU_APORTE")
    private EntityManager entityManager;

    private static final String NOVEDADES_LIMPIAS = "ListadoNovedadesLimpio";

    private static final String NOVEDADES_REPETIDAS = "idsNovedadesProcesadas";

    private final String CANAL_PILA = "PILA";

    private final String CANAL_PRESENCIAL = "PRESENCIAL";

    private static final int MAX_REINTENTOS = 3;

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(AportesCompositeBusiness.class);

    /**
     * Instancia del Excecutor Manager
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/aportes")
    private ManagedExecutorService mes;

    /**
     * Instancia del Excecutor Manager
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/procesoAportesNovedades")
    private ManagedExecutorService mesAportes;

    @Inject
    private IAportesNovedadesLocal aporteNovedad;

    /**
     * Inject del EJB para consultas en modelo Core entityManager
     */
    @Inject
    private IConsultasModeloCoreComposite consultasCore;

    @Resource
    private ManagedExecutorService managedExecutorService;
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.aportes.composite.service.AportesCompositeService#prepararYProcesarPlanillas()
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Deprecated
    public void prepararYProcesarPlanillas() {
        String firmaServicio = "AportesCompositeBusiness.prepararYProcesarPlanillas()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
        Integer tamanoPaginador = new Integer(sTamanoPaginador);

        // se consultan los aportes por procesar
        List<InformacionPlanillasRegistrarProcesarDTO> infoAportes = consultarInformacionAportesProcesar();
        List<Callable<Boolean>> parallelTaskListAportes = new ArrayList<>();
        for (InformacionPlanillasRegistrarProcesarDTO infoAporte : infoAportes) {
            Callable<Boolean> parallelTask = () -> {
                return procesarPlanilla(infoAporte, tamanoPaginador);
            };
            parallelTaskListAportes.add(parallelTask);
        }

        try {
            mes.invokeAll(parallelTaskListAportes);
        } catch (InterruptedException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio, e);
        }

        // se consultan las novedades por procesar
        List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = consultarInformacionNovedadesProcesar();
        List<Callable<Boolean>> parallelTaskListNovedades = new ArrayList<>();
        for (InformacionPlanillasRegistrarProcesarDTO infoNovedad : infoNovedades) {
            Callable<Boolean> parallelTask = () -> {
                return procesarNovedadesPlanilla(infoNovedad, tamanoPaginador, null);
            };
            parallelTaskListNovedades.add(parallelTask);
        }

        try {
            mes.invokeAll(parallelTaskListNovedades);
        } catch (InterruptedException e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio, e);
        }

        // una vez se procesan novedades, se actualizan los temAporteProcesados
        ActualizarTemAporteProcesado actualizarTemAporteProcesado = new ActualizarTemAporteProcesado();
        actualizarTemAporteProcesado.execute();

        //notificarAportesPila()
        // se procesan los cambios en aportes por análisis integral
        ActualizacionAportesRecalculados actualizacionAportes = new ActualizacionAportesRecalculados(Boolean.FALSE, new ArrayList<Long>());
        actualizacionAportes.execute();
        /*COMENTADI POR NUEVO PROCESO DE CATEGORIAS GLPI 58490 24/05/2022
                EjecutarCalculoCategoriasMasiva ejecutar = new EjecutarCalculoCategoriasMasiva();
                ejecutar.execute();
         */
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Método que consulta el micro servicio para consultar la información de
     * los aportes a registrar o relacionar
     *
     * @return
     */
    private List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionAportesProcesar() {
        String firmaServicio = "AportesCompositeBusiness.consultarInformacionAportesProcesar()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ConsultarInformacionPlanillasRegistrarProcesar servicio = new ConsultarInformacionPlanillasRegistrarProcesar();
        servicio.execute();
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return servicio.getResult();
    }

    /**
     * Método que consult el micro servicio para consultar la información de las
     * novedades a registrar o relacionar
     *
     * @return
     */
    private List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesar() {
        String firmaServicio = "AportesCompositeBusiness.consultarInformacionPlanillasRegistrarProcesar()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultarInformacionNovedadesRegistrarProcesar servicio = new ConsultarInformacionNovedadesRegistrarProcesar();
        servicio.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return servicio.getResult();
    }

    /**
     * Método que consult el micro servicio para consultar la información de las
     * notificaciones a procesar
     *
     * @return
     */
    private List<DatosComunicadoPlanillaDTO> consultarDatosComunicados() {
        String firmaServicio = "AportesCompositeBusiness.consultarDatosComunicados()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultarDatosComunicado servicio = new ConsultarDatosComunicado();
        servicio.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return servicio.getResult();
    }

    /**
     * Método que se encarga de registrar o relacionar los aportes
     *
     * @param infoPanilla
     * @param tamanoPaginador
     * @return
     */
    @Deprecated
    private Boolean procesarPlanilla(InformacionPlanillasRegistrarProcesarDTO infoPanilla, Integer tamanoPaginador) {
        StringBuilder firmaServicio = new StringBuilder("AportesCompositeBusiness.procesarPlanilla(");
        firmaServicio.append(infoPanilla.toString());
        firmaServicio.append(")");
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio.toString());

        Boolean resultadoExitoso = Boolean.TRUE;
        int pagina = 0;
        List<Long> idsProcesados = new ArrayList<>();
        long procesados = 0;
        if (infoPanilla.getCantidadTemAportes() > 0) {
            List<AporteDTO> aportes = null;
            do {
                try {
                    List<Long> idsProcesadosIteracion = new ArrayList<>();
                    aportes = consultarAportesPlanillasRegistrarProcesar(infoPanilla.getRegistroGeneral(), pagina);
                    idsProcesadosIteracion.addAll(registrarRelacionarListadoAportes(aportes));

                    logger.info("Se procesaron " + idsProcesadosIteracion.size() + " aportes detallados (parcial).");

                    if (!idsProcesadosIteracion.isEmpty()) {
                        procesados += idsProcesadosIteracion.size();
                        idsProcesados.addAll(idsProcesadosIteracion);
                        idsProcesadosIteracion.clear();
                    }
                } catch (Exception e) {
                    logger.error(firmaServicio.toString() + " " + e.getMessage());
                }

                pagina++;
            } while (infoPanilla.getCantidadTemAportes() > (pagina * tamanoPaginador));
        }
        // se eliminan los registros temporales ya procesados
        for (int i = 0; i < idsProcesados.size(); i += tamanoPaginador) {
            List<Long> paginaProceso = new ArrayList<>();
            try {
                paginaProceso = (idsProcesados.subList(i, i + tamanoPaginador >= idsProcesados.size() ? idsProcesados.size() : i + tamanoPaginador));
                EliminarTemporalesAporte eliminarTemporales = new EliminarTemporalesAporte(paginaProceso);
                eliminarTemporales.execute();

            } catch (Exception e) {
                logger.error("No se pudieron eliminar TemAporte " + " " + paginaProceso.toString());
                logger.error(firmaServicio.toString() + " " + e.getMessage());
            }
        }

        if (procesados > 0) {
            firmaServicio.append(" Resultado: Se procesaron " + procesados + " aportes detallados (final).");
        } else {
            resultadoExitoso = Boolean.FALSE;

            firmaServicio.append(" Resultado: No se procesaron los aportes.");
        }

        // se desmarcan los temporales para un nuevo intento (sí aplica)
        actualizarMarcaProcesoTemporales(infoPanilla, Boolean.TRUE, Boolean.FALSE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio.toString());
        return resultadoExitoso;
    }

    /**
     * Método que se encarga de registrar o relacionar las novedades
     *
     * @param infoPanilla
     * @param tamanoPaginador
     * @return
     */
    private Boolean procesarNovedadesPlanilla(InformacionPlanillasRegistrarProcesarDTO infoPanilla,
            Integer tamanoPaginador, UserDTO userDTO) {
        StringBuilder firmaServicio = new StringBuilder("AportesCompositeBusiness.procesarNovedadesPlanilla(");
        firmaServicio.append(infoPanilla.toString());
        firmaServicio.append(")");
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio.toString());

        Boolean resultadoExitoso = Boolean.TRUE;

        // se consultan las novedades temporales
        List<NovedadesProcesoAportesDTO> novedadesPlanilla = null;
        List<Long> idsProcesados = new ArrayList<>();
        long procesados = 0;
        if (infoPanilla.getCantidadTemNovedad() > 0) {
            int pagina = 0;
            do {
                List<Long> idsProcesadosIteracion = new ArrayList<>();
                logger.info("Se procesaron novedades para " + idsProcesadosIteracion.size() + " aportes detallados (parcial).");
                novedadesPlanilla = consultarNovedadesPlanilla(infoPanilla.getRegistroGeneral(), pagina);
                idsProcesadosIteracion.addAll(registrarNovedadesFuturasIndependientes(novedadesPlanilla, userDTO));
                //termino ejecucion de novedad byidplanilla bloque 9
                if (!idsProcesadosIteracion.isEmpty()) {
                    procesados += idsProcesadosIteracion.size();
                    idsProcesados.addAll(idsProcesadosIteracion);
                    idsProcesadosIteracion.clear();
                }
                pagina++;

            } while (infoPanilla.getCantidadTemNovedad() > (pagina * tamanoPaginador));
        }
        //if creado para verificar el paso de todas a novedades, si no estan completas no borra las temporales de pila
           if(validarProcesadoNovedades(infoPanilla.getRegistroGeneral())){
                // se eliminan los registros temporales ya procesados
                for (int i = 0; i < idsProcesados.size(); i += tamanoPaginador) {
                    List<Long> paginaProceso = new ArrayList<>();
                    try {
                        paginaProceso = (idsProcesados.subList(i, i + tamanoPaginador >= idsProcesados.size() ? idsProcesados.size() : i + tamanoPaginador));

                        EliminarTemporalesNovedad eliminarTemporales = new EliminarTemporalesNovedad(paginaProceso);
                        eliminarTemporales.execute();

                    } catch (Exception e) {
                        logger.error("No se pudieron eliminar TemNovedad " + " " + paginaProceso.toString());
                        logger.error(firmaServicio.toString() + " " + e.getMessage());
                    }
                }
            }
        if (procesados > 0) {
            firmaServicio.append(
                    " Resultado: Se procesaron novedades para " + procesados + " registros detallados (final).");
        } else {
            resultadoExitoso = Boolean.FALSE;

            firmaServicio.append(" Resultado: No se procesaron las novedades.");
        }

        // se desmarcan los temporales para un nuevo intento (sí aplica)
        actualizarMarcaProcesoTemporales(infoPanilla, Boolean.FALSE, Boolean.FALSE);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio.toString());
        return resultadoExitoso;
    }

    /**
     * @param infoPanilla
     * @param esAporte
     * @param enProceso
     */
    private void actualizarMarcaProcesoTemporales(InformacionPlanillasRegistrarProcesarDTO infoPanilla, Boolean esAporte,
            Boolean enProceso) {
        List<InformacionPlanillasRegistrarProcesarDTO> infoPlanillas = new ArrayList<>();
        infoPlanillas.add(infoPanilla);
        ActualizarMarcaProcesoTemporales actualizarMarca = new ActualizarMarcaProcesoTemporales(enProceso, esAporte, infoPlanillas);
        actualizarMarca.execute();
    }

    /**
     * Método que consulta el micro servicio para consultar la información de
     * los aportes de la planilla
     *
     * @param planillaAProcesar
     * @param pagina
     * @return
     */
    //TODO: en revisión
    private List<AporteDTO> consultarAportesPlanillasRegistrarProcesar(Long planillaAProcesar, Integer pagina) {
        String firmaServicio = "AportesCompositeBusiness.consultarInformacionPlanillasRegistrarProcesar(Long, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ConsultarAportesPlanillasRegistrarProcesar aportes = new ConsultarAportesPlanillasRegistrarProcesar(
                planillaAProcesar, pagina);
        aportes.execute();
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return aportes.getResult();
    }

    /**
     * Método para consultar las novedades de una planilla a procesar
     */
    private List<NovedadesProcesoAportesDTO> consultarNovedadesPlanilla(Long planillaAProcesar, Integer pagina) {
        String firmaServicio = "AportesCompositeBusiness.consultarNovedadesPlanillaLong(Long, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultarNovedadesPlanillasRegistrarProcesar novedades = new ConsultarNovedadesPlanillasRegistrarProcesar(
                planillaAProcesar, pagina);
        novedades.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return novedades.getResult();
    }

    /**
     * Método para solicitar la eliminación de los TemAporteProcesado de los
     * comunicados procesados
     *
     * @param aportesNotificados
     */
    private void eliminarTemAportesProcesadosNotificados(List<Long> aportesNotificados) {
        String firmaServicio = "AportesCompositeBusiness.eliminarTemAportesProcesadosNotificados()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EliminarTemAporteProcesado servicio = new EliminarTemAporteProcesado(aportesNotificados);
        servicio.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesCompositeService#
     * registrarRelacionarListadoAportes(java.util.List)
     */
    @Override
    @Deprecated
    public List<Long> registrarRelacionarListadoAportes(List<AporteDTO> aportes) {
        String firmaServicio = "AportesCompositeBusiness.registrarRelacionarListadoAportes(List<AporteDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ResultadoProcesoAportesDTO resultadoProcesoAportes = new ResultadoProcesoAportesDTO();
        List<Long> resultado = new ArrayList<>();
        logger.info("Aportes a procesar :c " + aportes.size());

        try {
            if (aportes != null && !aportes.isEmpty()) {
                List<AporteDTO> aportesLocal = aportes;

                // se prepara la información que se debe consultar en base de
                // datos para los aportes recibidos
                PrepararProcesoRegistroAportes prepararProcesoRegistroAportes = new PrepararProcesoRegistroAportes(
                        aportesLocal);
                prepararProcesoRegistroAportes.execute();
                DatosPersistenciaAportesDTO datosBd = prepararProcesoRegistroAportes.getResult();

                logger.info("datosBd a procesar : " + datosBd.toString());
                // se limpia de la listan de aportes, todos los aportes
                // detallados ya creados
                for(AporteDTO aporte : aportesLocal) {
                    logger.info("Aportes a procesar : " + aportesLocal.toString());
                    
                }
                ResultadoProcesoAportesDTO resultadoLimpieza = limpiarAportesProcesados(aportesLocal, datosBd);
                aportesLocal = resultadoLimpieza.getAportesActualizados();
                logger.info("resultadoLimpieza a procesar : " + resultadoLimpieza.toString());

                if (!aportesLocal.isEmpty()) {
                    logger.info("*****_______****** ingreso a if aporteslocal no es vacio");
                    // con la información consultada, se determinan las personas
                    // y/o empresas pendientes por crear
                    procesarPersonasEmpresasFaltantes(aportesLocal, datosBd);
                    aportesLocal = quitarAportesIncompletos(aportesLocal);

                    PaqueteProcesoAportesDTO datosProceso = new PaqueteProcesoAportesDTO();
                    datosProceso.prepararAportes(aportesLocal, datosBd.getAportesGenerales(), datosBd.getAportesDetallados());

                    // se solicitan los procesamientos en paralelo de los
                    // aportes
                    // en primer lugar los aportes generales
                    Map<String, Long> llavesProcesadas = procesarAportesGeneralesParalelo(datosProceso);
                    datosProceso.actualizarIdsParaAportesDetallados(llavesProcesadas);

                    logger.info("esta en el if y va a entrar a procesarAportesDetalladosParalelo");
                    resultadoProcesoAportes = procesarAportesDetalladosParalelo(datosProceso, aportesLocal);
                    logger.info("sale de procesarAportesDetalladosParalelo");

                    aportesLocal = resultadoProcesoAportes.getAportesActualizados();

                    // se crean los registros de TemAporteProcesados faltantes
                    crearTemAporteProcesado(datosBd, aportesLocal);
                }

                if (resultadoProcesoAportes.getListaRegistrosDetalladosProcesados() != null) {
                    resultadoProcesoAportes.getListaRegistrosDetalladosProcesados()
                            .addAll(resultadoLimpieza.getListaRegistrosDetalladosProcesados());
                } else {
                    resultadoProcesoAportes.setListaRegistrosDetalladosProcesados(
                            resultadoLimpieza.getListaRegistrosDetalladosProcesados());
                }

                // se inician las tareas posteriores al registro de los aportes
                // se actualizan las marcas de fiscalización de los roles
                // afiliados
                //TODO: jdo que se debe actualizar ???
                for (RolAfiliadoModeloDTO rolAfiliado : datosBd.getRolesAfiliadosPorActualizar()) {
                    actualizarRolAfiliado(rolAfiliado);
                }
                

                /*
                 * se persisten las personas con inconsistencia no procesadas
                 * aún y se procede a actualizar la deuda presunta de los
                 * aportes que ya fueron procesados
                 */
                List<Long> listaAportesConDeudaActualizada = new ArrayList<>();
                for (AporteDTO aporte : aportesLocal) {
                    if (aporte.getEsCotizanteFallecido() && !datosBd.getIdsPersonasConInconsistencia()
                            .contains(aporte.getPersonaCotizante().getIdPersona())) {
                        guardarRegistroPersonaInconsistencia(aporte);
                    }

                    if (aporte.getAporteGeneralProcesado() && aporte.getAporteDetalladoProcesado()
                            && !listaAportesConDeudaActualizada.contains(aporte.getAporteGeneral().getId())) {
                        actualizarDeudaPresunta(aporte);
                        listaAportesConDeudaActualizada.add(aporte.getAporteGeneral().getId());
                    }
                }

                // se prepara la ejecución paralela de las novedades incluidas
                // en el aporte
                List<Callable<Boolean>> tareasParalelasNovedades = new LinkedList<>();

                try {
                    // se recorren los DTO para preparar la ejecución paralela
                    // para el proceso de aportes detallados
                    for (AporteDTO aporte : aportesLocal) {

                        // si hay novedades asociadas al aporte se envían al
                        // servicio encargado de procesarlas
                        // if(aporte.getCanal() != CanalRecepcionEnum.PILA ){
                        //     Collections.sort(aporte.getNovedades(), (o1, o2) -> o2.getFechaInicioNovedad().compareTo(o1.getFechaInicioNovedad()));
                        // }
                        if (aporte.getNovedades() != null && !aporte.getNovedades().isEmpty()) {
                            //registrarNovedadesAportes(aporte);
                            
                            Callable<Boolean> parallelTaskNovedades = () -> {

                                logger.info("**__**:registrarNovedadesAportes metodo  -> registrarRelacionarListadoAportes");
                                return registrarNovedadesAportes(aporte);
                            };
                            tareasParalelasNovedades.add(parallelTaskNovedades);
                        }
                    }
                    
                    if (!tareasParalelasNovedades.isEmpty()) {
                        mesAportes.invokeAll(tareasParalelasNovedades);
                    }
                    // se procesan los reintegros de los empleadores que aplican
                    logger.info("Datos bd: " + datosBd.toString());
                    for (ActivacionEmpleadorDTO datosReintegro : datosBd.getDatosReintegroEmpleadores()) {
                        procesarActivacionEmpleador(datosReintegro);
                    }                    
                } catch (Exception e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                }
            }
            
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            resultadoProcesoAportes = null;
        }

        // sí no hay nada para eliminar, se devuelve el listado sólo con el
        // valor cero (0)
        if (resultadoProcesoAportes != null && resultadoProcesoAportes.getListaRegistrosDetalladosProcesados() != null
                && !resultadoProcesoAportes.getListaRegistrosDetalladosProcesados().isEmpty()) {
            resultado = resultadoProcesoAportes.getListaRegistrosDetalladosProcesados();
        } else if (resultadoProcesoAportes == null) {
            resultado = Collections.emptyList();
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    /**
     * @param aportes
     * @return
     */
    @Deprecated
    private List<AporteDTO> quitarAportesIncompletos(List<AporteDTO> aportes) {
        List<AporteDTO> result = new ArrayList<>();

        for (AporteDTO aporte : aportes) {
            if (aporte.getAporteDetallado().getIdPersona() != null && (aporte.getAporteGeneral().getIdEmpresa() != null
                    || aporte.getAporteGeneral().getIdPersona() != null)) {
                result.add(aporte);
            } else {
                logger.warn("Datos incompletos para el registro general "
                        + aporte.getAporteGeneral().getIdRegistroGeneral() + " y registro detallado "
                        + aporte.getAporteDetallado().getIdRegistroDetallado() + ". El aporte no será procesado");
            }
        }

        return result;
    }

    /**
     * @param datosProceso
     * @param resultadoProcesoAportes
     * @return
     */
    private Map<String, Long> procesarAportesGeneralesParalelo(PaqueteProcesoAportesDTO datosProceso) {
        String firmaServicio = "AportesCompositeBusiness.procesarAportesGeneralesParalelo(PaqueteProcesoAportesDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, Long> result = new HashMap<>();

        try {
            // listado de tareas paralelas de aportes generales
            List<Callable<Map<String, Long>>> tareasParalelasAportesGenerales = new LinkedList<>();
            List<Future<Map<String, Long>>> resultadosFuturos;

            for (String llave : datosProceso.getAportesGenerales().keySet()) {
                Map<String, AporteGeneralModeloDTO> paquete = new HashMap<>();
                paquete.put(llave, datosProceso.getAportesGenerales().get(llave));

                Callable<Map<String, Long>> parallelTaskAportes = () -> {
                    return procesarAporteGeneral(paquete);
                };
                tareasParalelasAportesGenerales.add(parallelTaskAportes);
            }

            // se invoca la creación paralela de los aportes Generales
            if (!tareasParalelasAportesGenerales.isEmpty()) {
                resultadosFuturos = mesAportes.invokeAll(tareasParalelasAportesGenerales);

                // se agrega el id de transacción a la lista para los procesos
                // exitosos.
                for (Future<Map<String, Long>> future : resultadosFuturos) {
                    result.putAll(future.get());
                }
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * @param datosProceso
     * @param aportesDTO
     * @return
     */
    private ResultadoProcesoAportesDTO procesarAportesDetalladosParalelo(PaqueteProcesoAportesDTO datosProceso,
            List<AporteDTO> aportesDTO) {
        String firmaServicio = "AportesCompositeBusiness.procesarAportesDetalladosParalelo(PaqueteProcesoAportesDTO, List<AporteDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoProcesoAportesDTO result = new ResultadoProcesoAportesDTO();

        List<JuegoAporteMovimientoDTO> aportesDetallados = new ArrayList<>();
        List<Long> idsRegistrosDetalladosProcesados = new ArrayList<>();

        for (List<JuegoAporteMovimientoDTO> aportes : datosProceso.getAportesDetallados().values()) {
            aportesDetallados.addAll(aportes);
        }

        try {
            // listado de tareas paralelas de aportes generales
            List<Callable<List<Long>>> tareasParalelasAportesDetallados = new LinkedList<>();
            List<Future<List<Long>>> resultadosFuturos;

            List<JuegoAporteMovimientoDTO> paquete = new ArrayList<>();
            for (JuegoAporteMovimientoDTO aporte : aportesDetallados) {
                paquete.add(aporte);
                if (paquete.size() == 10) {
                    List<JuegoAporteMovimientoDTO> paqueteDummy = new ArrayList<>();
                    paqueteDummy.addAll(paquete);
                    Callable<List<Long>> parallelTaskAportes = () -> {
                        return procesarAporteDetallado(paqueteDummy);
                    };
                    tareasParalelasAportesDetallados.add(parallelTaskAportes);

                    paquete.clear();
                }
            }

            if (!paquete.isEmpty()) {
                Callable<List<Long>> parallelTaskAportes = () -> {
                    return procesarAporteDetallado(paquete);
                };
                tareasParalelasAportesDetallados.add(parallelTaskAportes);
            }

            // se invoca la creación paralela de los aportes detallados
            if (!tareasParalelasAportesDetallados.isEmpty()) {
                resultadosFuturos = mesAportes.invokeAll(tareasParalelasAportesDetallados);

                // se agrega el id de transacción a la lista para los procesos
                // exitosos.
                for (Future<List<Long>> future : resultadosFuturos) {
                    idsRegistrosDetalladosProcesados.addAll(future.get());
                }
            }
            logger.info("**__**finaliza correcto tarea paralelas: ");
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        // se seleccionan los DTOs de aportes que fueron procesados
        List<AporteDTO> aportesProcesados = new ArrayList<>();
        for (AporteDTO aporte : aportesDTO) {
            if (idsRegistrosDetalladosProcesados.contains(aporte.getAporteDetallado().getIdRegistroDetallado())) {
                aportesProcesados.add(aporte);
                aporte.setAporteDetalladoProcesado(Boolean.TRUE);
                aporte.setAporteGeneralProcesado(Boolean.TRUE);
            }
        }

        // se prepara la respuesta
        result.setListaRegistrosDetalladosProcesados(idsRegistrosDetalladosProcesados);
        result.setAportesActualizados(aportesProcesados);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * @param paquete
     * @return
     */
    private Map<String, Long> procesarAporteGeneral(Map<String, AporteGeneralModeloDTO> paquete) {
        ProcesarPaqueteAportesGenerales procesoAportesGenerales = new ProcesarPaqueteAportesGenerales(paquete);
        procesoAportesGenerales.execute();
        return procesoAportesGenerales.getResult();
    }

    /**
     * @param paquete
     * @return
     */
    private List<Long> procesarAporteDetallado(List<JuegoAporteMovimientoDTO> aportesDetallados) {
        String firmaServicio = "--->>AportesBusiness.procesarPaqueteAportesDetallados(List<AporteDetalladoModeloDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            logger.info("**__**inicia ProcesarPaqueteAportesDetallados : ");
            ProcesarPaqueteAportesDetallados procesoAportesDetallados = new ProcesarPaqueteAportesDetallados(aportesDetallados);
            procesoAportesDetallados.execute();
            logger.info("**__**finaliza ProcesarPaqueteAportesDetallados : ");
            return procesoAportesDetallados.getResult();

        } catch (Exception e) {
            logger.info("**__**catch procesarAporteDetallado: " + e);
            List<Long> listaRegistrosProcesados = consultasCore.registrarAportesDetallados(aportesDetallados);
            logger.info("**__**finaliza correcto: ");
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return listaRegistrosProcesados;
        }
    }

    /**
     * Método encargado de llevar limpiar la lista de aportes para conservar
     * sólo aquellos que no hayan sido completamente procesados
     *
     * @param aportesLocal
     * @param datosBd
     * @return
     */
   private ResultadoProcesoAportesDTO limpiarAportesProcesados(List<AporteDTO> aportes,
            DatosPersistenciaAportesDTO datosBd) {
        String firmaMetodo = "AportesCompositeBusiness.limpiarAportesProcesados(List<AporteDTO>, DatosPersistenciaAportesDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoProcesoAportesDTO result = new ResultadoProcesoAportesDTO();
        Set<Long> registrosProcesados = new HashSet<>();
        List<AporteDTO> aportesDepurado = new ArrayList<>();

        if (!datosBd.getAportesDetallados().isEmpty()) {
            logger.info("Aportes detallados encontrados si esta en el if");
            // en primer lugar, se crea un listado de llaves de los aportes existentes
            for (AporteDetalladoModeloDTO apd : datosBd.getAportesDetallados()) {
                logger.info("Estamos en el for con el apd: " + apd.toString());
                registrosProcesados.add(apd.getIdRegistroDetallado());
            }

            // se revisa el listado de los aportes encontrados para establecer
            // sí el aporte ya se encuentra procesado
            for (AporteDTO aporte : aportes) {
                if (aporte.getAporteDetallado() != null
                        && !registrosProcesados.contains(aporte.getAporteDetallado().getIdRegistroDetallado())) {
                            logger.info("Aporte no procesado: " + aporte.toString());
                    aportesDepurado.add(aporte);
                }
            }
        } else {
            aportesDepurado = aportes;
            for (AporteDTO aporte : aportesDepurado) {
                logger.info("Aportes depurados " + aporte.toString());
            }
        }

        result.setAportesActualizados(aportesDepurado);
        result.setListaRegistrosDetalladosProcesados(new ArrayList<>(registrosProcesados));

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * @param aportes
     * @param datosBd
     */
    @Deprecated
    private void procesarPersonasEmpresasFaltantes(List<AporteDTO> aportes, DatosPersistenciaAportesDTO datosBd) {
        String firmaMetodo = "AportesCompositeBusiness.procesarPersonasEmpresasFaltantes(List<AporteDTO>, DatosPersistenciaAportesDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<PersonaModeloDTO> personasAportantesPorCrear = new ArrayList<>();
        List<PersonaModeloDTO> personasCotizantesPorCrear = new ArrayList<>();
        List<EmpresaModeloDTO> empresasPorCrear = new ArrayList<>();
        List<RolAfiliadoModeloDTO> rolesAfiliadoPorActualizar = new ArrayList<>();
        List<Long> empleadoresReintegrables = new ArrayList<>();
        List<ActivacionEmpleadorDTO> datosReintegroEmpleador = new ArrayList<>();

        Map<String, Long> idsAportantesCreados = new HashMap<>();
        Map<String, Long> idsEmpAportantesCreadas = new HashMap<>();
        Map<String, Long> idsCotizantesCreados = new HashMap<>();

        // se determina sí el aporte presenta al menos un aportante dependiente
        // reintegrable
        Boolean tieneCotizanteDependienteReintegrable = cotizanteReintegable(aportes);

        // se recorren los dto de aporte, para determinar sí su empresa, persona
        // aportante, tramitador y/o persona cotizante existen en bd
        for (AporteDTO aporte : aportes) {
            // se actualiza la marca de tipo de solicitante
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aporte.getAporteGeneral().getTipoSolicitante())
                    && aporte.getEmpresaAportante() == null) {
                aporte.getAporteGeneral().setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE);
            }

            // se comprueba la empresa aportante
            if (aporte.getEmpresaAportante() != null) {
                // se comprueba que exista la persona de la empresa
                Long idPersonaEmpresa = FuncionesUtilitarias.ubicarPersona(
                        aporte.getEmpresaAportante().getTipoIdentificacion(),
                        aporte.getEmpresaAportante().getNumeroIdentificacion(), datosBd.getPersonasAportantes());

                if (idPersonaEmpresa == null) {
                    aporte.getEmpresaAportante().setCreadoPorPila(Boolean.TRUE);//--
                    aporte.getEmpresaAportante().getUbicacionModeloDTO().setIdMunicipio(FuncionesUtilitarias
                            .ubicarMunicipio(aporte.getCodigoMunicioAportante(), datosBd.getMunicipios()));//--
                    aporte.getAporteGeneral()
                            .setEstadoAportante(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                    empresasPorCrear.add(aporte.getEmpresaAportante());
                } else {
                    Long idEmpresaAportante = FuncionesUtilitarias.ubicarEmpresa(
                            aporte.getEmpresaAportante().getTipoIdentificacion(),
                            aporte.getEmpresaAportante().getNumeroIdentificacion(), datosBd.getEmpresasAportantes());

                    if (idEmpresaAportante == null) {
                        aporte.getEmpresaAportante().setCreadoPorPila(Boolean.TRUE);//--
                        aporte.getEmpresaAportante().getUbicacionModeloDTO().setIdMunicipio(FuncionesUtilitarias
                                .ubicarMunicipio(aporte.getCodigoMunicioAportante(), datosBd.getMunicipios()));//--
                        aporte.getAporteGeneral()
                                .setEstadoAportante(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                        empresasPorCrear.add(aporte.getEmpresaAportante());
                    } else {
                        // sí la empresa existe, se actualiza su ID en el aporte
                        // general y su sucursal y el estado del empleador
                        aporte.getAporteGeneral().setIdEmpresa(idEmpresaAportante);
                        if (aporte.getSucursalEmpresa() != null) {
                            aporte.getSucursalEmpresa().setIdEmpresa(idEmpresaAportante);
                        }

                        // TODO: para que se usa el estado del aportante?
                        EmpleadorModeloDTO empleador = FuncionesUtilitarias.obtenerEmpleador(idEmpresaAportante,
                                datosBd.getEmpleadoresAportantes());
                        aporte.getAporteGeneral().setEstadoAportante(empleador != null ? empleador.getEstadoEmpleador()
                                : EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);

                        // Se verifica que este en el periodo regular para poder realizar un reintegro de empleador
                        MarcaPeriodoEnum marcaPeriodo = FuncionesUtilitarias.calcularMarcaPeriodo(
                                aporte.getAporteGeneral().getPeriodoAporte(), PeriodoPagoPlanillaEnum.MES_VENCIDO);
                        // Al menos uno de los cotizantes debe tener novedad de ingreso para poder reintegrar un empleador
                        Boolean novedadIngreso = Boolean.FALSE;
                        if (aporte.getNovedades() != null && !aporte.getNovedades().isEmpty()) {
                            for (NovedadPilaDTO novedad : aporte.getNovedades()) {
                                novedadIngreso = novedad.getEsIngreso() ? Boolean.TRUE : Boolean.FALSE;
                                break;
                            }
                        }

                        // se determina sí el empleador es reintegrable
                        if (empleador != null && aporte.getEsEmpleadorReintegrable() != null
                                && aporte.getEsEmpleadorReintegrable() && tieneCotizanteDependienteReintegrable
                                && !empleadoresReintegrables.contains(empleador.getIdEmpleador())
                                && MarcaPeriodoEnum.PERIODO_REGULAR.equals(marcaPeriodo) && novedadIngreso) {

                            // se prepara el DTO con los datos del reintegro del
                            // empleador
                            ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
                            datosReintegro.setIdAportante(empleador.getIdEmpresa());
                            datosReintegro.setIdRegistroGeneral(aporte.getAporteGeneral().getIdRegistroGeneral());
                            datosReintegro.setCanalReintegro(aporte.getCanal());
                            datosReintegro.setFechaReintegro(FuncionesUtilitarias
                                    .obtenerFechaMillis(aporte.getAporteGeneral().getPeriodoAporte() + "-01"));
                            datosReintegro.setTipoIdEmpleador(aporte.getEmpresaAportante().getTipoIdentificacion());
                            datosReintegro.setNumIdEmpleador(aporte.getEmpresaAportante().getNumeroIdentificacion());

                            datosReintegroEmpleador.add(datosReintegro);
                            empleadoresReintegrables.add(empleador.getIdEmpleador());
                        }
                    }
                }

                // se agrega la marca de período para dependientes
                aporte.getAporteGeneral().setMarcaPeriodo(FuncionesUtilitarias.calcularMarcaPeriodo(
                        aporte.getAporteGeneral().getPeriodoAporte(), PeriodoPagoPlanillaEnum.MES_VENCIDO));
            }

            // se comprueba la persona aportante
            if (aporte.getPersonaAportante() != null) {
                // se comprueba que exista la persona
                Long idPersonaAportante = FuncionesUtilitarias.ubicarPersona(
                        aporte.getPersonaAportante().getTipoIdentificacion(),
                        aporte.getPersonaAportante().getNumeroIdentificacion(), datosBd.getPersonasAportantes());

                if (idPersonaAportante == null) {
                    aporte.getPersonaAportante().setCreadoPorPila(Boolean.TRUE);
                    asignarUbicacionPersonaIndependiente(aporte, FuncionesUtilitarias
                            .ubicarMunicipio(aporte.getCodigoMunicioAportante(), datosBd.getMunicipios()));
                    aporte.getAporteGeneral()
                            .setEstadoAportante(EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                    personasAportantesPorCrear.add(aporte.getPersonaAportante());
                } else {
                    // sí existe la persona, se actualiza su ID en el aporte
                    // general y su estado como RolAfiliado (ind y pen)
                    aporte.getAporteGeneral().setIdPersona(idPersonaAportante);
                    RolAfiliadoModeloDTO rolAfiliado = FuncionesUtilitarias.obtenerRolAfiliado(
                            aporte.getAporteDetallado().getTipoCotizante(), idPersonaAportante,
                            datosBd.getRolesAfiliadosCotizantes());
                    aporte.getAporteGeneral()
                            .setEstadoAportante(rolAfiliado != null && rolAfiliado.getEstadoAfiliado() != null
                                    ? EstadoEmpleadorEnum.valueOf(rolAfiliado.getEstadoAfiliado().name())
                                    : EstadoEmpleadorEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);

                    // se actualizan las marcas de fiscalización del rol
                    // afiliado por tipo de afiliación
                    // TODO: jdo nunca funcionó? solo para aportantes tipo persona?
                    if (aporte.getEnviadoAFiscalizacion() != null && aporte.getEnviadoAFiscalizacion()
                            && rolAfiliado != null && !rolesAfiliadoPorActualizar.contains(rolAfiliado)) {
                        rolAfiliado.setEnviadoAFiscalizacion(aporte.getEnviadoAFiscalizacion());
                        rolAfiliado.setMotivoFiscalizacion(aporte.getMotivoFiscalizacion());
                        rolAfiliado.setFechaFiscalizacion(new Date().getTime());

                        rolesAfiliadoPorActualizar.add(rolAfiliado);
                    }
                }

                // se agrega la marca de período para independientes o
                // pensionados
                PeriodoPagoPlanillaEnum oportunidadPago = null;
                if (idPersonaAportante != null) {
                    oportunidadPago = FuncionesUtilitarias.ubicarOportunidadRolAfiliado(idPersonaAportante,
                            aporte.getAporteGeneral().getTipoSolicitante(), datosBd.getRolesAfiliadosCotizantes());
                }

                if (oportunidadPago == null) {
                    oportunidadPago = PeriodoPagoPlanillaEnum.MES_VENCIDO;
                }

                if (aporte.getAporteGeneral().getEstadoRegistroAporteAportante() != null && aporte.getAporteGeneral()
                        .getEstadoRegistroAporteAportante().equals(EstadoRegistroAporteEnum.REGISTRADO)) {
                    aporte.getAporteGeneral().setFechaReconocimiento(new Date().getTime());
                }

                // TODO : jdo para que?
                aporte.getAporteGeneral().setMarcaPeriodo(FuncionesUtilitarias
                        .calcularMarcaPeriodo(aporte.getAporteGeneral().getPeriodoAporte(), oportunidadPago));
            }

            // se agregan las marcas de reconocimiento automático que apliquen
            if (EstadoRegistroAporteEnum.REGISTRADO
                    .equals(aporte.getAporteGeneral().getEstadoRegistroAporteAportante())) {
                aporte.getAporteGeneral().setFechaReconocimiento(new Date().getTime());
                aporte.getAporteGeneral()
                        .setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO);
            }

            if (EstadoRegistroAporteEnum.REGISTRADO
                    .equals(aporte.getAporteDetallado().getEstadoRegistroAporteCotizante())) {
                aporte.getAporteDetallado().setFechaMovimiento(new Date().getTime());
                aporte.getAporteDetallado()
                        .setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO);
            }

            // se comprueba al tramitador
            // TODO: jdo que es y como se debe tratar.. pero igual se crea el aportante y el tramitado en caso que sean diferentes? o siempre son iguales? o nunca son iguales
            if (aporte.getTipoDocTramitador() != null && aporte.getIdTramitador() != null) {
                // se comprueba que exista la persona de la empresa
                Long idPersonaEmpresa = FuncionesUtilitarias.ubicarPersona(aporte.getTipoDocTramitador(),
                        aporte.getIdTramitador(), datosBd.getPersonasTramitadoras());

                if (idPersonaEmpresa == null) {
                    empresasPorCrear.add(prepararTramitador(aporte));
                } else {
                    Long idEmpresaAportante = FuncionesUtilitarias.ubicarEmpresa(aporte.getTipoDocTramitador(),
                            aporte.getIdTramitador(), datosBd.getEmpresasAportantes());

                    if (idEmpresaAportante == null) {
                        empresasPorCrear.add(prepararTramitador(aporte));
                    } else {
                        // sí la empresa existe, se actualiza su ID en el aporte
                        // general
                        aporte.getAporteGeneral().setEmpresaTramitadoraAporte(idEmpresaAportante);
                    }
                }
            }

            // se comprueba la persona cotizante
            if (aporte.getPersonaCotizante() != null) {
                Long idCotizante = FuncionesUtilitarias.ubicarPersona(
                        aporte.getPersonaCotizante().getTipoIdentificacion(),
                        aporte.getPersonaCotizante().getNumeroIdentificacion(), datosBd.getPersonasCotizantes());

                if (idCotizante == null) {
                    aporte.getPersonaCotizante().setCreadoPorPila(Boolean.TRUE);
                    asignarUbicacionPersonaCotizante(aporte, FuncionesUtilitarias
                            .ubicarMunicipio(aporte.getCodigoMunicioAportante(), datosBd.getMunicipios()));
                    aporte.getAporteDetallado()
                            .setEstadoCotizante(EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                    personasCotizantesPorCrear.add(aporte.getPersonaCotizante());
                } else {
                    // sí ya existe la persona cotizante, su ID de persona se
                    // agrega al aporte detallado
                    aporte.getAporteDetallado().setIdPersona(idCotizante);
                    RolAfiliadoModeloDTO rolAfiliado = FuncionesUtilitarias.obtenerRolAfiliado(
                            aporte.getAporteDetallado().getTipoCotizante(), idCotizante,
                            datosBd.getRolesAfiliadosCotizantes());
                    aporte.getAporteDetallado()
                            .setEstadoCotizante(rolAfiliado != null && rolAfiliado.getEstadoAfiliado() != null
                                    ? rolAfiliado.getEstadoAfiliado()
                                    : EstadoAfiliadoEnum.NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES);
                }

                // se agrega la marca de período para el aporte detallado, igual
                // a la del aporte general
                aporte.getAporteDetallado().setMarcaPeriodo(aporte.getAporteGeneral().getMarcaPeriodo());
            }
        }

        // se crean las empresas y/o personas requeridas
        for (EmpresaModeloDTO nuevaEmpresa : empresasPorCrear) {
            String llaveCompuesta = nuevaEmpresa.getTipoIdentificacion().name()
                    + nuevaEmpresa.getNumeroIdentificacion();
            Long idNuevaEmpresa = crearEmpresa(nuevaEmpresa);

            if (!idsEmpAportantesCreadas.containsKey(llaveCompuesta)) {
                idsEmpAportantesCreadas.put(llaveCompuesta, idNuevaEmpresa);
            }
        }

        for (PersonaModeloDTO nuevaPersonaApo : personasAportantesPorCrear) {
            String llaveCompuesta = nuevaPersonaApo.getTipoIdentificacion().name()
                    + nuevaPersonaApo.getNumeroIdentificacion();
            Long idNuevaPersona = crearPersona(nuevaPersonaApo);

            if (!idsAportantesCreados.containsKey(llaveCompuesta)) {
                idsAportantesCreados.put(llaveCompuesta, idNuevaPersona);
            }
        }

        for (PersonaModeloDTO nuevaPersonaCot : personasCotizantesPorCrear) {
            String llaveCompuesta = nuevaPersonaCot.getTipoIdentificacion().name()
                    + nuevaPersonaCot.getNumeroIdentificacion();
            Long idNuevaPersona = crearPersona(nuevaPersonaCot);
            nuevaPersonaCot.setIdPersona(idNuevaPersona);

            if (!idsCotizantesCreados.containsKey(llaveCompuesta)) {
                idsCotizantesCreados.put(llaveCompuesta, idNuevaPersona);
            }
        }

        // se agregan los ids de las nuevas empresas o personas en sus
        // respectivos aportes
        for (AporteDTO aporte : aportes) {
            String llaveCompuesta = null;
            // se actualiza id empresa
            if (aporte.getEmpresaAportante() != null && aporte.getAporteGeneral().getIdEmpresa() == null) {
                llaveCompuesta = aporte.getEmpresaAportante().getTipoIdentificacion().name()
                        + aporte.getEmpresaAportante().getNumeroIdentificacion();

                aporte.getAporteGeneral().setIdEmpresa(idsEmpAportantesCreadas.get(llaveCompuesta));
            }

            // se actualiza id persona aportante
            if (aporte.getPersonaAportante() != null && aporte.getAporteGeneral().getIdPersona() == null) {
                llaveCompuesta = aporte.getPersonaAportante().getTipoIdentificacion().name()
                        + aporte.getPersonaAportante().getNumeroIdentificacion();

                aporte.getAporteGeneral().setIdPersona(idsAportantesCreados.get(llaveCompuesta));
            }

            // se actualiza id tramitador
            if (aporte.getIdTramitador() != null && aporte.getAporteGeneral().getEmpresaTramitadoraAporte() == null) {
                llaveCompuesta = aporte.getTipoDocTramitador() + aporte.getIdTramitador();

                aporte.getAporteGeneral().setEmpresaTramitadoraAporte(idsEmpAportantesCreadas.get(llaveCompuesta));
            }

            // se actualiza id persona cotizante
            if (aporte.getPersonaCotizante() != null && aporte.getAporteDetallado().getIdPersona() == null) {
                llaveCompuesta = aporte.getPersonaCotizante().getTipoIdentificacion().name()
                        + aporte.getPersonaCotizante().getNumeroIdentificacion();

                aporte.getAporteDetallado().setIdPersona(idsCotizantesCreados.get(llaveCompuesta));
            }

            // se agrega la sucursal al aporte
            asignarSucursalAlAporte(aporte, datosBd.getSucursales());

            // se agrega el código del operador de información
            if (aporte.getAporteGeneral().getIdOperadorInformacion() != null) {
                aporte.getAporteGeneral()
                        .setIdOperadorInformacion(FuncionesUtilitarias.ubicarIdOI(
                                aporte.getAporteGeneral().getIdOperadorInformacion().intValue(),
                                datosBd.getOperadoresInformacion()));
            }

            // se agrega la marca usada para identificar cuáles fueron los
            // aportes que llegaron en cierta planilla
            aporte.getAporteGeneral().setMarcaActualizacionCartera(Boolean.TRUE);
        }

        datosBd.setRolesAfiliadosPorActualizar(rolesAfiliadoPorActualizar);
        datosBd.setDatosReintegroEmpleadores(datosReintegroEmpleador);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param aportesLocal
     * @param datosBd
     * @param idsTemAporteProcesado
     * @param aportes
     */
    private void crearTemAporteProcesado(DatosPersistenciaAportesDTO datosBd, List<AporteDTO> aportes) {
        String firmaMetodo = "crearTemAporteProcesado(List<Long>, List<AporteDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> idsTemAporteProcesado = datosBd.getIdsTemAporteProcesado();
        List<ConsultaPresenciaNovedadesDTO> datosCreacion = new ArrayList<>();
        Set<Long> idsIncluidos = new HashSet<>();

        // se toman los ID de registro general de los aportes que fueron
        // procesados
        for (AporteDTO aporte : aportes) {
            Long idRegGen = aporte.getAporteGeneral().getIdRegistroGeneral();
            if (!idsTemAporteProcesado.contains(idRegGen) && !aporte.getEsManual()
                    && !idsIncluidos.contains(idRegGen)) {
                idsIncluidos.add(idRegGen);
                datosCreacion.add(datosBd.getMapaPresenciaNovedades().get(idRegGen));
            }
        }

        // se solicita la creación de los registros
        if (!datosCreacion.isEmpty()) {
            CrearTemAporteProcesadosNuevos crearTemAporteProcesadosNuevos = new CrearTemAporteProcesadosNuevos(
                    datosCreacion);
            crearTemAporteProcesadosNuevos.execute();
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param aportes
     * @return
     */
    private Boolean cotizanteReintegable(List<AporteDTO> aportes) {
        for (AporteDTO aporte : aportes) {
            if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(aporte.getAporteDetallado().getTipoCotizante())
                    && aporte.getEsTrabajadorReintegrable()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Método encargado de la consulta o creación de una empresa tramitadora de
     * aporte
     *
     * @param aporte
     */
    @Deprecated
    private EmpresaModeloDTO prepararTramitador(AporteDTO aporte) {
        String firmaMetodo = "AportesCompositeBusiness.prepararTramitador(AporteDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoIdentificacionEnum tipoIdTram = aporte.getTipoDocTramitador();
        String numIdTram = aporte.getIdTramitador();

        Persona tramitador = new Persona();
        tramitador.setTipoIdentificacion(tipoIdTram);
        tramitador.setNumeroIdentificacion(numIdTram);

        if (aporte.getDigVerTramitador() != null) {
            tramitador.setDigitoVerificacion(aporte.getDigVerTramitador());
        }
        if (aporte.getNombreTramitador() != null) {
            tramitador.setRazonSocial(aporte.getNombreTramitador());
        }
        Empresa empresaTramitadora = new Empresa();
        empresaTramitadora.setPersona(tramitador);
        EmpresaModeloDTO empresaTramitadoraModeloDTO = new EmpresaModeloDTO();
        empresaTramitadoraModeloDTO.convertToDTO(empresaTramitadora);
        empresaTramitadoraModeloDTO.setCreadoPorPila(true);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return empresaTramitadoraModeloDTO;
    }

    /**
     * @param aporte
     * @param sucursales
     * @param idAportante
     * @param idCotizante
     */
    private void asignarSucursalAlAporte(AporteDTO aporte, Map<Long, List<SucursalEmpresaModeloDTO>> sucursales) {
        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(aporte.getAporteDetallado().getTipoCotizante())
                && aporte.getSucursalEmpresa() != null) {
            SucursalEmpresaModeloDTO sucursalEmpresa = null;

            List<SucursalEmpresaModeloDTO> sucursalesEmpleador = sucursales
                    .get(aporte.getAporteGeneral().getIdEmpresa());
            if (sucursalesEmpleador != null) {
                for (SucursalEmpresaModeloDTO sucursalEmp : sucursalesEmpleador) {
                    if (sucursalEmp.getCodigo().equals(aporte.getSucursalEmpresa().getCodigo())
                            && sucursalEmp.getNombre().equals(aporte.getSucursalEmpresa().getNombre())) {
                        sucursalEmpresa = sucursalEmp;
                    }
                }
            }

            if (sucursalEmpresa == null) {
                aporte.getAporteDetallado().setCodSucursal(aporte.getSucursalEmpresa().getCodigo());
                aporte.getAporteDetallado().setNomSucursal(aporte.getSucursalEmpresa().getNombre());
            } else {
                aporte.getAporteDetallado().setCodSucursal(sucursalEmpresa.getCodigo());
                aporte.getAporteDetallado().setNomSucursal(sucursalEmpresa.getNombre());
                aporte.getAporteGeneral().setIdSucursalEmpresa(sucursalEmpresa.getIdSucursalEmpresa());
            }
        } else if (aporte.getSucursalEmpresa() != null) {
            aporte.getAporteDetallado().setCodSucursal(aporte.getSucursalEmpresa().getCodigo());
            aporte.getAporteDetallado().setNomSucursal(aporte.getSucursalEmpresa().getNombre());
        }
    }

    /**
     * @param aporte
     * @param municipioAportante
     */
    private void asignarUbicacionPersonaIndependiente(AporteDTO aporte, Short municipioAportante) {
        if (aporte.getPersonaAportante().getUbicacionModeloDTO() != null) {
            aporte.getPersonaAportante().getUbicacionModeloDTO().setIdMunicipio(municipioAportante);
        } else {
            UbicacionModeloDTO ubicacionPersonaAportante = new UbicacionModeloDTO();
            ubicacionPersonaAportante.setIdMunicipio(municipioAportante);
            aporte.getPersonaAportante().setUbicacionModeloDTO(ubicacionPersonaAportante);
        }
    }

    /**
     * @param aporte
     * @param municipioAportante
     */
    private void asignarUbicacionPersonaCotizante(AporteDTO aporte, Short municipioAportante) {
        if (aporte.getPersonaCotizante().getUbicacionModeloDTO() != null) {
            aporte.getPersonaCotizante().getUbicacionModeloDTO().setIdMunicipio(municipioAportante);
        } else {
            UbicacionModeloDTO ubicacionPersonaAportante = new UbicacionModeloDTO();
            ubicacionPersonaAportante.setIdMunicipio(municipioAportante);
            aporte.getPersonaCotizante().setUbicacionModeloDTO(ubicacionPersonaAportante);
        }
    }

    /**
     * Método encargado de hacer el llamado al microservicio que crea una
     * persona
     *
     * @param personaModeloDTO el DTO que contiene los datos de la persona a
     * crear
     * @return Long con el id de la persona creada
     */
    @Deprecated
    private Long crearPersona(PersonaModeloDTO personaModeloDTO) {
        CrearPersona nuevaPersona = new CrearPersona(personaModeloDTO);
        nuevaPersona.execute();
        return nuevaPersona.getResult();
    }

    /**
     * Método encargado de actualizar la deuda presunta
     *
     * @param aporte datos del aporte que se procesó
     */
    private void actualizarDeudaPresunta(AporteDTO aporte) {
        String firmaMetodo = "actualizarDeudaPresunta(AporteDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " tipoSolicitante: " + aporte.getAporteGeneral().getTipoSolicitante()
        );

        AporteGeneralModeloDTO aporteGeneralModeloDTO = aporte.getAporteGeneral();

        TipoIdentificacionEnum tipoId = null;
        String numId = null;
        String periodo = null;
        TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;

        // Actualiza la cartera del aportante -> HU-169
        if (aporteGeneralModeloDTO != null
                && ModalidadRecaudoAporteEnum.PILA.equals(aporteGeneralModeloDTO.getModalidadRecaudoAporte())) {

            periodo = aporteGeneralModeloDTO.getPeriodoAporte();
            tipoSolicitante = aporteGeneralModeloDTO.getTipoSolicitante();

            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aporteGeneralModeloDTO.getTipoSolicitante())) {
                tipoId = aporte.getEmpresaAportante() != null ? aporte.getEmpresaAportante().getTipoIdentificacion() : null;
                numId = aporte.getEmpresaAportante() != null ? aporte.getEmpresaAportante().getNumeroIdentificacion() : null;
            } else {
                tipoId = aporte.getPersonaAportante() != null ? aporte.getPersonaAportante().getTipoIdentificacion() : null;
                numId = aporte.getPersonaAportante() != null ? aporte.getPersonaAportante().getNumeroIdentificacion() : null;
            }

            if (tipoId == null || numId == null || periodo == null || tipoSolicitante == null) {
                NullPointerException e = new NullPointerException(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo
                        + " :: Parámetros incompletos para la operación.");

                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Parámetros incompletos para la operación.");
                logger.error(firmaMetodo + " :: No es posible actualizar la cartera con los datos: ");
                logger.error(firmaMetodo + " :: Tipo Solicitante: " + (tipoSolicitante != null ? tipoSolicitante.name() : " NULL"));
                logger.error(firmaMetodo + " :: Período: " + (periodo != null ? periodo : " NULL"));
                logger.error(firmaMetodo + " :: Tipo ID Aportante: " + (tipoId != null ? tipoId.name() : " NULL"));
                logger.error(firmaMetodo + " :: Número ID Aportante: " + (numId != null ? numId : " NULL"));

                throw e;
            }

            actualizarDeudaPresuntaCartera(tipoId, numId, periodo, tipoSolicitante);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método que invoca el servicio que actualiza la deuda presunta de un
     * aportante
     *
     * @param tipoIdentificacion Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param periodoEvaluacion Periodo de evaluación. Formato YYYY-MM
     * @param tipoAportante Tipo de aportante
     */
    private void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        try {
            logger.info("Inicia el método actualizarDeudaPresuntaCartera CC " + numeroIdentificacion);
            ActualizarDeudaPresuntaCartera service = new ActualizarDeudaPresuntaCartera(tipoAportante,
                    numeroIdentificacion, periodoEvaluacion, tipoIdentificacion);
            service.execute();
            logger.info("Finaliza el método actualizarDeudaPresuntaCartera");
        } catch (Exception e) {
            logger.error("Error en el método actualizarDeudaPresuntaCartera", e);
        }
    }

    /**
     * Método encargado de registrar las novedades del aporte
     *
     * @param aporte datos del aporte que se está procesando
     */
    private Boolean registrarNovedadesAportes(AporteDTO aporte) {
        logger.info(ConstantesComunes.INICIO_LOGGER + " registrarNovedadesAportes");
        logger.info("aporte.getNovedades()" + aporte.getNovedades());

        if (aporte.getNovedades() != null && !aporte.getNovedades().isEmpty()) {
            registrarNovedadesPila(aporte.getNovedades(), aporte.getCanal(),
                    aporte.getPersonaAportante() != null ? aporte.getPersonaAportante().getTipoIdentificacion()
                    : aporte.getEmpresaAportante().getTipoIdentificacion(),
                    aporte.getPersonaAportante() != null ? aporte.getPersonaAportante().getNumeroIdentificacion()
                    : aporte.getEmpresaAportante().getNumeroIdentificacion(),
                    aporte.getPersonaCotizante(), aporte.getEsTrabajadorReintegrable(),
                    aporte.getEsEmpleadorReintegrable(), null);
        }
        return Boolean.TRUE;
    }

    /**
     * Servicio encargado de registrar las novedades enviadas desde cartera
     *
     * @param listaNovedades Lista de novedades a registrar
     */
    @Override
    public void registrarNovedadesCartera(NovedadCarteraDTO listaNovedades) {
        if (listaNovedades.getNovedades() != null && !listaNovedades.getNovedades().isEmpty()) {
            registrarNovedadesPila(listaNovedades.getNovedades(), listaNovedades.getCanal(),
                    listaNovedades.getPersonaAportante().getTipoIdentificacion(),
                    listaNovedades.getPersonaAportante().getNumeroIdentificacion(),
                    listaNovedades.getPersonaCotizante(), listaNovedades.getEsTrabajadorReintegrable(), null, null);
        }
    }

    /**
     * Método encargado de hacer el llamado al servicio que procesa las
     * novedades de PILA
     *
     * @param novedades es el listado de novedades a procesar
     */
    private List<Long> registrarNovedadesPila(List<NovedadPilaDTO> novedades, CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO) {
        String firmaMetodo = "AportesCompositeBusiness.registrarNovedadesPila(List<NovedadPilaDTO>, CanalRecepcionEnum, "
                + "TipoIdentificacionEnum,String, PersonaModeloDTO, Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistrarNovedadesPilaServiceDTO regNovDTO = new RegistrarNovedadesPilaServiceDTO();
        regNovDTO.setNovedades(novedades);
        regNovDTO.setCanal(canal);
        regNovDTO.setTipoIdAportante(tipoIdAportante);
        regNovDTO.setNumeroIdAportante(numeroIdAportante);
        regNovDTO.setPersonaCotizante(personaCotizante);
        regNovDTO.setEsTrabajadorReintegrable(esTrabajadorReintegrable);
        regNovDTO.setEsEmpleadorReintegrable(esEmpleadorReintegrable);
        System.out.println("**__**Inicio RegistrarNovedadesPilaService: ");
        //posible correccion este metodo pertenece al mismo microservicio evitar llmado
        // RegistrarNovedadesPilaService r = new RegistrarNovedadesPilaService(regNovDTO);
        // r.execute();
        System.out.println("**__**FIN RegistrarNovedadesPilaService: ");
        // List<Long> idsNovedadesProcesadas = r.getResult();
        List<Long> idsNovedadesProcesadas = registrarNovedadesPilaService(regNovDTO, userDTO);

        /*
        List<Long> idsNovedadesProcesadas = aporteNovedad.registrarNovedadesPila(novedades, canal,
                tipoIdAportante, numeroIdAportante, personaCotizante,
                esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO);
         */
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idsNovedadesProcesadas;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> registrarNovedadesPilaService(RegistrarNovedadesPilaServiceDTO regNovDTO, UserDTO userDTO) {
        String firmaMetodo = "AportesCompositeBusiness.registrarNovedadesPila(List<NovedadPilaDTO>, CanalRecepcionEnum, "
                + "TipoIdentificacionEnum,String, PersonaModeloDTO, Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        System.out.println("**__**INICIO RegistrarNovedadesPilaService Metodo novedades composite: ");
        //   List<Long> idsNovedadesProcesadas = aporteNovedad.registrarNovedadesPila(
        List<Long> idsNovedadesProcesadas = registrarNovedadesPilaAportes(
                regNovDTO.getNovedades(),
                regNovDTO.getCanal(),
                regNovDTO.getTipoIdAportante(),
                regNovDTO.getNumeroIdAportante(),
                regNovDTO.getPersonaCotizante(),
                regNovDTO.getEsTrabajadorReintegrable(),
                regNovDTO.getEsEmpleadorReintegrable(),
                userDTO);
        System.out.println("**__**FIN RegistrarNovedadesPilaService Metodo novedades composite: ");
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idsNovedadesProcesadas;
    }
//**comienza los metodos de pila nuevos traidos de aportesnovedadesbussines 6/01/2022 */

    /**
     * ******************************************************************
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    //metodo usado por medio de la interfaz
   public List<Long> registrarNovedadesPilaAportes(List<NovedadPilaDTO> novedades, CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO) {
        String firmaMetodo = "AportesNovedadesBusiness.registrarNovedadesPila(List<NovedadPilaDTO>, CanalRecepcionEnum, "
                + "TipoIdentificacionEnum,String, PersonaModeloDTO, Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Callable<Void>> tareasParalelas = new LinkedList<>();
        List<Long> idsNovedadesProcesadas = new ArrayList<>();
        RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO = new RegistrarNovedadConTransaccionDTO();
        registrarNovedadConTransaccionDTO.setPersonaCotizante(personaCotizante);
        //   if(canal != CanalRecepcionEnum.PILA ){
        //       System.out.println("**__**  " );
        //    Collections.sort(novedades, (o1, o2) -> o2.getFechaInicioNovedad().compareTo(o1.getFechaInicioNovedad()));
        //  // novedades.sort((o1, o2) -> Long.compare(o1.getFechaInicioNovedad(),o2.getFechaInicioNovedad() )); 
        //   }
        // novedades.sort((o1, o2) -> Long.compare(o1.getIdRegistroDetallado(),o2.getIdRegistroDetallado() ));
        logger.info("novedades.size() " + novedades.size());
        if(novedades.size() > 50){
            for (NovedadPilaDTO novedadPilaDTO : novedades) {
            //comentado porque consulta la tabla TransaccionNovedadPilaCompleta en core      
            // TransaccionNovedadPilaCompleta t = new TransaccionNovedadPilaCompleta(novedadPilaDTO.getIdTenNovedad());
            // t.execute();
            // Long transaccionNovedadEjecutada = t.getResult();
            // if (transaccionNovedadEjecutada == null) {
            try {
                registrarNovedadConTransaccionDTO.setNovedadPilaDTO(novedadPilaDTO);
                if (novedadPilaDTO.getTipoTransaccion() == null) {
                    registrarNovedadSinTipoTransaccionAportesComposite(novedadPilaDTO, canal, tipoIdAportante, numeroIdAportante, personaCotizante,
                            esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO, idsNovedadesProcesadas);
                    
                    }else if(novedadPilaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO) || novedadPilaDTO.getTipoTransaccion().toString().contains("RETIRO")){
                        final List<NovedadPilaDTO> novedadesOrdenadasPorCotizante = novedades.stream().filter(novedad -> novedad.getTipoIdentificacionCotizante().equals(novedadPilaDTO.getTipoIdentificacionCotizante()) && novedad.getNumeroIdentificacionCotizante().equals(novedadPilaDTO.getNumeroIdentificacionCotizante())).collect(Collectors.toList());
                       
                            Callable<Void> parallelTask = () -> {
                                for (NovedadPilaDTO novedadPilaDTO2 : novedadesOrdenadasPorCotizante) {
                                    registrarNovedadConTransaccionDTO.setNovedadPilaDTO(novedadPilaDTO2);
                                    registrarNovedadConTipoTransaccionAportesComposite(registrarNovedadConTransaccionDTO, canal, tipoIdAportante, numeroIdAportante,
                                    esTrabajadorReintegrable, esEmpleadorReintegrable);
                                    idsNovedadesProcesadas.add(novedadPilaDTO2.getIdRegistroDetalladoNovedad());
                                }
                                return null;
                            };
                            novedades.removeAll(novedadesOrdenadasPorCotizante);
                            tareasParalelas.add(parallelTask); 
                                        
                    }else{
                    Callable<Void> parallelTask = () -> {
                        RegistrarNovedadConTipoTransaccionAportesCompositeAsync registrarNovedadConTipoTransaccionAportesComposite = new RegistrarNovedadConTipoTransaccionAportesCompositeAsync(registrarNovedadConTransaccionDTO, canal, tipoIdAportante, numeroIdAportante,
                            esTrabajadorReintegrable, esEmpleadorReintegrable);
                        registrarNovedadConTipoTransaccionAportesComposite.execute();
                        idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
                        return null;
                    };
                    tareasParalelas.add(parallelTask); 
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                if (novedadPilaDTO.getIdTenNovedad() != null) {
                    GuardarExcepcionNovedadPila guardarSrv = new GuardarExcepcionNovedadPila(novedadPilaDTO.getIdTenNovedad(), exceptionAsString);
                    guardarSrv.execute();
                }
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }

            // } else {
            //     logger.info("TENID ya existe ya se aplico la novedad ");
            //     idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
            // }
            }
            try {
                mesAportes.invokeAll(tareasParalelas);
            } catch (Exception e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
        }
        else{
            for (NovedadPilaDTO novedadPilaDTO : novedades) {
                try {
                    registrarNovedadConTransaccionDTO.setNovedadPilaDTO(novedadPilaDTO);
                    if (novedadPilaDTO.getTipoTransaccion() == null) {
                    registrarNovedadSinTipoTransaccionAportesComposite(novedadPilaDTO, canal, tipoIdAportante, numeroIdAportante, personaCotizante,
                            esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO, idsNovedadesProcesadas);
                    
                    }else if(novedadPilaDTO.getTipoTransaccion().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO) || novedadPilaDTO.getTipoTransaccion().toString().contains("RETIRO")){
                        registrarNovedadConTipoTransaccionAportesComposite(registrarNovedadConTransaccionDTO, canal, tipoIdAportante, numeroIdAportante,
                                esTrabajadorReintegrable, esEmpleadorReintegrable);
                        idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
                    
                    }else{
                        registrarNovedadConTipoTransaccionAportesComposite(
                    registrarNovedadConTransaccionDTO,
                    canal,
                    tipoIdAportante,
                    numeroIdAportante,
                    esTrabajadorReintegrable,
                    esEmpleadorReintegrable);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();

                    if (novedadPilaDTO.getIdTenNovedad() != null) {
                        GuardarExcepcionNovedadPila guardarSrv = new GuardarExcepcionNovedadPila(novedadPilaDTO.getIdTenNovedad(), exceptionAsString);
                        guardarSrv.execute();
                    }
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                }
            }
        }
        
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idsNovedadesProcesadas;
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    @Asynchronous
    public void registrarNovedadConTipoTransaccionAportesCompositeAsync(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO, CanalRecepcionEnum canal,
        TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
        Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable) {
            registrarNovedadConTipoTransaccionAportesComposite(
                registrarNovedadConTransaccionDTO,
                canal,
                tipoIdAportante,
                numeroIdAportante,
                esTrabajadorReintegrable,
                esEmpleadorReintegrable);
        }



    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void registrarNovedadSinTipoTransaccionAportesComposite(NovedadPilaDTO novedadPilaDTO, CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO, List<Long> idsNovedadesProcesadas) throws Exception {
        String firmaMetodo = "AportesNovedadesBussines.registrarNovedadSinTipoTransaccion()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        /**
         * nuevo 17/01/2022
         */
        logger.info("**__**LLEGA A METODO registrarNovedadSinTipoTransaccion");
        logger.info("**__**COMPOSITEConfirmarTransaccionNovedadPilaRutine" + novedadPilaDTO.getTipoTransaccion());
        logger.info("**__** desdepues de posible novedadPilaDTO.getAccionNovedad()" + novedadPilaDTO.getAccionNovedad());
        //if(novedadPilaDTO.getTipoTransaccion() == null){
        //    novedadPilaDTO.setTipoTransaccion(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
        //    novedadPilaDTO.setAccionNovedad(MarcaAccionNovedadEnum.APLICAR_NOVEDAD.name());
        //}
        logger.info("**__** desdepues de posible setCOMPOSITEConfirmarTransaccionNovedadPilaRutine" + novedadPilaDTO.getTipoTransaccion());
        logger.info("**__** desdepues de posible novedadPilaDTO.getAccionNovedad()" + novedadPilaDTO.getAccionNovedad());
        /*
        if(novedadPilaDTO.getTipoTransaccion()==TipoTransaccionEnum.NOVEDAD_REINTEGRO && novedadPilaDTO.getAccionNovedad() == MarcaAccionNovedadEnum.APLICAR_NOVEDAD.name()){
   
           NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
        List<ClasificacionEnum> clasificacionesAfiliado;

        novedadAporte.setTenNovedadId(novedadPilaDTO.getIdTenNovedad());

        // se consultan las clasificaciones del afiliado (si lo está)
        if (personaCotizante != null) {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(personaCotizante.getTipoIdentificacion(),
                    personaCotizante.getNumeroIdentificacion());
        } else {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(
                    novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante());
        }

        // se asigna el valor de la marca de novedad que viene por pila
        novedadAporte.setAplicar(MarcaAccionNovedadEnum.valueOf(novedadPilaDTO.getAccionNovedad()).getMarca());

        // se determina cual de ellas es la que corresponde a la presente
        // solicitud de novedad
        for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
            if (clasificacion.getSujetoTramite() != null
                    && clasificacion.getSujetoTramite().equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(clasificacion);
                break;
            }
        }

        // si no se logró determinar la clasificación del afiliado, quiere
        // decir que la persona no está afiliada
        // por tanto se asigna un valor cualquiera correspondiente a su
        // clasificación y se asigna el valor de la
        // marca de novedad como NO_APLICADA
        if (novedadAporte.getClasificacionAfiliado() == null) {
            if (TipoAfiliadoEnum.PENSIONADO.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            }

            // novedadAporte.setAplicar(MarcaAccionNovedadEnum.NO_APLICADA.getMarca());
        }

        // se asigna el resto de valores corrspondientes a la novedad que se
        // desea radicar
        if (novedadPilaDTO.getFechaInicioNovedad() != null) {
            novedadAporte.setFechaInicio(novedadPilaDTO.getFechaInicioNovedad().getTime());
        }

        novedadAporte.setComentarios(novedadPilaDTO.getMensajeNovedad());
        novedadAporte.setTipoNovedad(novedadPilaDTO.getTipoTransaccion());
        if (novedadPilaDTO.getFechaFinNovedad() != null) {
            novedadAporte.setFechaFin(novedadPilaDTO.getFechaFinNovedad().getTime());
        }
        if (personaCotizante != null) {
            novedadAporte.setNumeroIdentificacion(personaCotizante.getNumeroIdentificacion());
            novedadAporte.setTipoIdentificacion(personaCotizante.getTipoIdentificacion());
        } else {
            novedadAporte.setNumeroIdentificacion(novedadPilaDTO.getNumeroIdentificacionCotizante());
            novedadAporte.setTipoIdentificacion(novedadPilaDTO.getTipoIdentificacionCotizante());
        }

        novedadAporte.setNumeroIdentificacionAportante(numeroIdAportante);
        novedadAporte.setTipoIdentificacionAportante(tipoIdAportante);
        novedadAporte.setCanalRecepcion(canal);
        novedadAporte.setIdRegistroDetallado(novedadPilaDTO.getIdRegistroDetallado());
        
            ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
          //  return p.construirSolicitudNovedad(novedadAportesDTO);
           logger.info("**__**SE EJECUTA EN APORTES ProcesarActivacionBeneficiarioPILARutine");
            //p.construirSolicitudNovedad(novedadAporte);
                p.procesarActivacionBeneficiarioPILA(novedadAporte, userDTO, entityManager);
        }*/
        /**
         * Fin nuevo 17/01/2022
         */
        //TransaccionRegistrarNovedadService trns =  new TransaccionRegistrarNovedadService( 
        transaccionRegistrarNovedadService(canal,
                tipoIdAportante,
                numeroIdAportante,
                esTrabajadorReintegrable,
                esEmpleadorReintegrable,
                novedadPilaDTO,
                userDTO
        );
      
        //  trns.execute();
        idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
        logger.info("**__**Finaliza a transaccionRegistrarNovedadService listadoSalida:"+idsNovedadesProcesadas.size());
        logger.info("**__**FIn A METODO registrarNovedadSinTipoTransaccion");
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }

    /**
     * @param novedadPilaDTO
     * @param canal
     * @param tipoIdAportante
     * @param numeroIdAportante
     * @param personaCotizante
     * @param esTrabajadorReintegrable
     * @param esEmpleadorReintegrable
     * @param idsNovedadesProcesadas
     * @param userDTO
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void registrarNovedadConTipoTransaccionAportesComposite(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO, CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable) {
        NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
        List<ClasificacionEnum> clasificacionesAfiliado;

        novedadAporte.setTenNovedadId(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getIdTenNovedad());

        novedadAporte.setIdRegistroDetalladoNovedad(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getIdRegistroDetalladoNovedad());
        novedadAporte.setIsIngresoRetiro(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getIsIngresoRetiro());
        novedadAporte.setBeneficiariosCadena(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getBeneficiarios());

        // se consultan las clasificaciones del afiliado (si lo está)
        if (registrarNovedadConTransaccionDTO.getPersonaCotizante() != null) {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(registrarNovedadConTransaccionDTO.getPersonaCotizante().getTipoIdentificacion(),
                    registrarNovedadConTransaccionDTO.getPersonaCotizante().getNumeroIdentificacion());
        } else {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(
                    registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoIdentificacionCotizante(),
                    registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getNumeroIdentificacionCotizante());
        }

        // se asigna el valor de la marca de novedad que viene por pila
        novedadAporte.setAplicar(MarcaAccionNovedadEnum.valueOf(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getAccionNovedad()).getMarca());

        // se determina cual de ellas es la que corresponde a la presente
        // solicitud de novedad
        for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
            if (clasificacion.getSujetoTramite() != null
                    && clasificacion.getSujetoTramite().equals(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(clasificacion);
                break;
            }
        }

        // si no se logró determinar la clasificación del afiliado, quiere
        // decir que la persona no está afiliada
        // por tanto se asigna un valor cualquiera correspondiente a su
        // clasificación y se asigna el valor de la
        // marca de novedad como NO_APLICADA
        if (novedadAporte.getClasificacionAfiliado() == null) {
            if (TipoAfiliadoEnum.PENSIONADO.equals(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            }

            // novedadAporte.setAplicar(MarcaAccionNovedadEnum.NO_APLICADA.getMarca());
        }

        // se asigna el resto de valores corrspondientes a la novedad que se
        // desea radicar
        if (registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getFechaInicioNovedad() != null) {
            novedadAporte.setFechaInicio(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getFechaInicioNovedad().getTime());
        }
        novedadAporte.setComentarios(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getMensajeNovedad());
        novedadAporte.setTipoNovedad(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoTransaccion());
        if (registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getFechaFinNovedad() != null) {
            novedadAporte.setFechaFin(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getFechaFinNovedad().getTime());
        }
        if (registrarNovedadConTransaccionDTO.getPersonaCotizante() != null) {
            novedadAporte.setNumeroIdentificacion(registrarNovedadConTransaccionDTO.getPersonaCotizante().getNumeroIdentificacion());
            novedadAporte.setTipoIdentificacion(registrarNovedadConTransaccionDTO.getPersonaCotizante().getTipoIdentificacion());
        } else {
            novedadAporte.setNumeroIdentificacion(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getNumeroIdentificacionCotizante());
            novedadAporte.setTipoIdentificacion(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoIdentificacionCotizante());
        }

        novedadAporte.setNumeroIdentificacionAportante(numeroIdAportante);
        novedadAporte.setTipoIdentificacionAportante(tipoIdAportante);
        novedadAporte.setCanalRecepcion(canal);
        novedadAporte.setIdRegistroDetallado(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getIdRegistroDetallado());
        if (registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoTransaccion() != null
                && TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB
                        .equals(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getTipoTransaccion())) {

            SucursalEmpresa sucursal = consultarSucursalEmpresa(registrarNovedadConTransaccionDTO.getNovedadPilaDTO().getValor(), tipoIdAportante,
                    numeroIdAportante);
            novedadAporte.setSucursal(sucursal);
        }
        RadicarSolicitudNovedadAportes radicarSolicitudNovedad = new RadicarSolicitudNovedadAportes( 
                novedadAporte);
        radicarSolicitudNovedad.execute();
        System.out.println("**__**FIN de  RadicarSolicitudNovedadAportes");

    }

//**FINALIZA 6/01/2022 comienza los metodos de pila nuevos traidos de aportesnovedadesbussines */
    /**
     * Método encargado de hacer el llamado al microservicio que crea una
     * empresa
     *
     * @param empresaModeloDTO el DTO que contiene los datos de la empresa a
     * crear
     * @return Long con el id de la empresa creada
     */
    @Deprecated
    private Long crearEmpresa(EmpresaModeloDTO empresaModeloDTO) {
        CrearEmpresa nuevoEmpresa = new CrearEmpresa(empresaModeloDTO);
        nuevoEmpresa.execute();
        return nuevoEmpresa.getResult();
    }

    /**
     * Método encargado de crear el DTO que recibirá los datos del aporte para
     * la bandeja de gestión de personas pendientes por actualizar datos.
     *
     * @param aporte es el objeto que contiene la información del
     * aporte(aportante, cotizante, aporte general, aporte detallado y novedades
     * relacionadas).
     * @return RegistroPersonaInconsistenteDTO con los datos a enviar a la
     * bandeja.
     */
    private RegistroPersonaInconsistenteDTO crearRegistroPersonaInconsistente(AporteDTO aporte) {
        RegistroPersonaInconsistenteDTO registroPersonaInconsistenteDTO = new RegistroPersonaInconsistenteDTO();
        registroPersonaInconsistenteDTO.setCanalContacto(CanalRecepcionEnum.PILA);
        registroPersonaInconsistenteDTO.setPersona(aporte.getPersonaCotizante());
        registroPersonaInconsistenteDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.OTROS_CASOS);
        registroPersonaInconsistenteDTO.setEstadoGestion(EstadoGestionEnum.PENDIENTE_GESITONAR);
        registroPersonaInconsistenteDTO.setFechaIngreso(Calendar.getInstance().getTimeInMillis());
        String observaciones = "Se intentó registra un aporte para un período posterior a la fecha de fallecimiento del cotizante.";
        registroPersonaInconsistenteDTO.setObservaciones(observaciones);
        return registroPersonaInconsistenteDTO;
    }

    /**
     * Método encargado de hacer el llamado al microservicio que crea el
     * registro de la inconsistencia
     *
     * @param aporte es el DTO que contiene los datos de la persona y el aporte
     * que presentan las inconsistencias.
     */
    private void guardarRegistroPersonaInconsistencia(AporteDTO aporte) {
        List<RegistroPersonaInconsistenteDTO> registroPersonaInconsistenteDTOs = new ArrayList<>();
        registroPersonaInconsistenteDTOs.add(crearRegistroPersonaInconsistente(aporte));
        GuardarRegistroPersonaInconsistencia guardarRegistro = new GuardarRegistroPersonaInconsistencia(
                registroPersonaInconsistenteDTOs);
        guardarRegistro.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesCompositeService#
     * enviarComunicadoPila(java.lang.Long)
     */
    @Override
    public Long enviarComunicadoPila(DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO) {
        String firmaServicio = "AportesCompositeService.enviarComunicadoPila(DatosComunicadoPlanillaDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("**__**enviarComunicadoPila entra a datosComunicadoPlanillaDTO.getIdPlanilla(): " + datosComunicadoPlanillaDTO.getIdPlanilla());
        Boolean success = false;
        // se consulta la planilla con el id

        ConsultaPlanillaResultDTO datosPlanilla = consultarPlanilla(datosComunicadoPlanillaDTO.getIdPlanilla());

        /*
         * sí la solicitud se hace desde la finalización de una
         * planillamanual, se sobreescribe la marca de planilla manual en
         * los datos de la planilla
         */
        if (datosComunicadoPlanillaDTO.getPlanillaManual()) {
            datosPlanilla.setEsPilaManual(Boolean.FALSE);
        }

        // se verifica, en base al dato recibido por parametro, si la
        // cantidad de aportes registrados
        // es igual a la que se debían procesar
        // la comparación menor o igual se hace en base al funcionamiento
        // del bus, ya que este puede
        // realizar el procesamiento de la planilla por bloques, lo que hace
        // que el valor de getNumeroAportesEnPlanilla()
        // pueda ser menor que la cantidad de aportes procesados, pero nunca
        // mayor.
        //TEMPORAL
   if (datosPlanilla.getCantidadAportes() >= datosComunicadoPlanillaDTO.getNumeroAportesEnPlanilla()
               /*comentado GLPI 42189  && !datosPlanilla.getEsPilaManual()*/) {
            //notificacion en planilla actulizara a BLOQUE 10
            //if (!datosComunicadoPlanillaDTO.getPlanillaManual()) {
                if(validarProcesadoNovedades(datosComunicadoPlanillaDTO.getIdPlanilla())){
                        success = actualizarEstadosPlanilla(datosComunicadoPlanillaDTO.getIdPlanilla());
                        logger.info("**__**EXITO NOTIFICACION DE PLANILLA EXITOSO CON NOVEDADES IDAPORTEGENERAL: " + datosComunicadoPlanillaDTO.getIdPlanilla()+" success:"+success);
                    }
                    else{
                        success = false;
                       logger.error("====NO NOTIFICA- MOTIVO NO PASO TODAS LAS NOVEDADES - Las novedades no coinciden con el numero de novedades dadas en ID APORTE GENERAL :"
                       +datosComunicadoPlanillaDTO.getIdPlanilla());
                    }
                if (success == false) {
                    logger.error("No se pudo actualizar el estado de la planilla : " + datosComunicadoPlanillaDTO.getIdPlanilla());
                } else {
                    logger.info("**__**EXITO NOTIFICACION DE PLANILLA EXITOSO indice: " + datosComunicadoPlanillaDTO.getIdPlanilla());
                }
            //}
            //FIN notificacion en planilla actulizara a BLOQUE 10
            //if(success){
            try {
                
                verificarEmailAportante(datosPlanilla.getListadoAportes());
            } catch (Exception e) {
                logger.info("**__**Excepcion verificarEmailAportante error: " + e);
            }

            List<NotificacionParametrizadaDTO> notificaciones = crearNotificacionesParametrizadas(
                    datosPlanilla.getListadoAportes());
            try {
                for (NotificacionParametrizadaDTO notificacion : notificaciones) {
                    logger.info("Email"+ String.join("|", notificacion.getDestinatarioTO()));
                    enviarNotificacionComunicado(notificacion);
                }
            } catch (Exception y) {
                logger.info("**__**Excepcion enviarNotificacionComunicado error: " + y);
            }
        //}

        } else {
            logger.info("**__**ELSE datosPlanilla.getCantidadAportes() >= datosComunicadoPlanillaDTO.getNumeroAportesEnPlanilla()");
            if (!datosPlanilla.getEsPilaManual()) {
                return 0L;
            }
        }

        logger.info("Notificacion ->" + ConstantesComunes.FIN_LOGGER + firmaServicio);
        return datosComunicadoPlanillaDTO.getIdPlanilla();

    }

    /**
     * Método encargado de verificar si al consultar la planilla no se obtuvo un
     * correo para el aportante desde core y de ser así, asigna a dicho atributo
     * el valor recibido en la planilla
     *
     * @param planilla conjunto de datos que representan la información
     * consiganda en una planilla PILA
     */
    private void verificarEmailAportante(List<AportePilaDTO> planilla) {
        logger.info("verificaremailAportante");
        for (AportePilaDTO aportePila : planilla) {
            logger.info("aportePila.getEmailAportante()" + aportePila.getEmailAportante());
            if (aportePila.getEmailAportante() == null) {
                logger.info("aportePila.getAporteGeneral().getEmailAportante()" + aportePila.getAporteGeneral().getEmailAportante());
                aportePila.setEmailAportante(aportePila.getAporteGeneral().getEmailAportante());
            }
        }
    }

    /**
     * Método que envía los parametros para la notificación
     *
     * @param notificacion
     * @throws Exception
     */
    private void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion) {

        logger.info("Entra por aqui enviarNotificacionComunicado"+ String.join(",", notificacion.getDestinatarioTO()));
        EnviarNotificacionComunicado enviarNotificacion = new EnviarNotificacionComunicado(notificacion);
        enviarNotificacion.execute();
    }

    /**
     * Método encargado de hacer el llamado al microservicio que crea un arreglo
     * con las notificaciones (comunicados) a enviar en base a una planilla
     * dada.
     *
     * @param planilla es la planilla para la cual se desea enviar el(los)
     * comunicado(s).
     * @return List<NotificacionParametrizadaDTO> con los comunicados a enviar.
     */
    private List<NotificacionParametrizadaDTO> crearNotificacionesParametrizadas(List<AportePilaDTO> planilla) {
        List<NotificacionParametrizadaDTO> result = new ArrayList<>();

        Integer incremento = 200;

        Integer inicio = 0;
        Integer fin = planilla.size() > incremento ? incremento : planilla.size();
        logger.info("Tamaño planilla " + planilla.size());
        while (inicio < planilla.size()) {
            CrearNotificacionesParametrizadas crearNotificaciones = new CrearNotificacionesParametrizadas(planilla.subList(inicio, fin));
            crearNotificaciones.execute();
            List<NotificacionParametrizadaDTO> resultados = crearNotificaciones.getResult();
            for (NotificacionParametrizadaDTO notificacion : resultados) {
                if (notificacion.getDestinatarioTO() == null) {
                    logger.info("Entro acá.... " + planilla.get(inicio).getEmailAportante());
                    List<String> destinatarios = new ArrayList<>();
                    destinatarios.add(planilla.get(inicio).getEmailAportante());
                    notificacion.setDestinatarioTO(destinatarios);
                }
                result.add(notificacion);
            }
            inicio = fin;
            fin = planilla.size() > (fin + incremento) ? (fin + incremento) : planilla.size();
        }
        return result;
    }

    /**
     * Método encargado de consultar el estado de la planilla que se acaba de
     * procesar, para verificar si se envía o no el comunicado
     *
     * @param idPlanilla el identificador de la planilla
     * @return boolean true si el estado es valido para el proceso, false en
     * caso contrario.
     */
    private ConsultaPlanillaResultDTO consultarPlanilla(Long idPlanilla) {
        ConsultarPlanilla consultarPlanilla = new ConsultarPlanilla(idPlanilla);
        consultarPlanilla.execute();
        return consultarPlanilla.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar estados de un
     * procesamiento de planilla PILA
     *
     * @param idRegistroGeneral <code>Long</code> El identificador del registro
     * de planilla (idRegistroGeneral)
     */
    private Boolean actualizarEstadosPlanilla(Long idRegistroGeneral) {
        Boolean success = false;

        logger.info("**__**Inicia AportesCompositeBusiness.actualizarEstadosPlanilla ( Long: " + idRegistroGeneral + " )");
        ActualizacionEstadosPlanillaDTO actualizacionEstadosPlanillaDTO = new ActualizacionEstadosPlanillaDTO();
        if (idRegistroGeneral == null) {
            logger.info("**__**actualizarEstadosPlanilla viene null idRegistroGeneral");
        }
        actualizacionEstadosPlanillaDTO.setIdRegistroGeneral(idRegistroGeneral);
        actualizacionEstadosPlanillaDTO.setActualizaRegistroGeneral(Boolean.TRUE);
        actualizacionEstadosPlanillaDTO.setEstadoProceso(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO);
        actualizacionEstadosPlanillaDTO.setAccionProceso(AccionProcesoArchivoEnum.PROCESO_FINALIZADO);
        actualizacionEstadosPlanillaDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_10_OI);
        actualizacionEstadosPlanillaDTO.setMarcaHabilitacionGestionManual(Boolean.FALSE);
        ActualizarEstadosRegistroPlanilla actualizarEstadosRegistroPlanillaService = new ActualizarEstadosRegistroPlanilla(
                actualizacionEstadosPlanillaDTO);
        actualizarEstadosRegistroPlanillaService.execute();
        success = actualizarEstadosRegistroPlanillaService.getResult();

        logger.info("**__**Finaliza AportesCompositeBusiness.actualizarEstadosPlanilla ( Long: " + idRegistroGeneral + " )");

        return success;
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar estados de un
     * procesamiento de planilla PILA
     *
     * @param idPlanilla <code>Long</code> El identificador del registro
     * de planilla (idRegistroGeneral)
     */
    private Boolean validarProcesadoNovedades(Long idPlanilla){
        Boolean success = false;
        logger.info("**__**Inicia AportesCompositeBusiness.validarProcesadoNovedades ( Long: " + idPlanilla + " )");
        if (idPlanilla == null) {
            logger.info("**__**validarProcesadoNovedades viene null idPlanilla");
        }
        ValidarProcesadoNovedad validarProcesado = new ValidarProcesadoNovedad(idPlanilla);
        validarProcesado.execute();
        success = validarProcesado.getResult();
        return success;
        
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesCompositeService#
     * registrarNovedadesFuturasIndependientes(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> registrarNovedadesFuturasIndependientes(
            List<NovedadesProcesoAportesDTO> novedadesProcesoAportes, UserDTO userDTO) {
        String firmaMetodo = "AportesCompositeBusiness.registrarNovedadesFuturasIndependientes(List<NovedadesProcesoAportesDTO>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> listadoSalida = new ArrayList<>();

        try {
            Set<Long> ids = new HashSet<>();

            List<NovedadesProcesoAportesDTO> novedadesProcesoAportesTemp = novedadesProcesoAportes;

            /*
             * se buscan novedades asociadas a los registros detallados ya
             * existentes en Core, se inicia con hacer un listado de id de
             * registro detallado
             */
            //ordenamiento de novedades 
            List<Long> idsRegDet = new ArrayList<>();
            for (NovedadesProcesoAportesDTO novedades : novedadesProcesoAportesTemp) {
                if (!idsRegDet.contains(novedades.getIdRegistroDetallado())) {
                    idsRegDet.add(novedades.getIdRegistroDetallado());
                }
            } 

           /* 
           ConsultarNovedadesPorRegistroDetallado consultaNovedades = new ConsultarNovedadesPorRegistroDetallado(
                    idsRegDet);
            consultaNovedades.execute();
            ResultadoConsultaNovedadesExistentesDTO novedadesEncontradas = consultaNovedades.getResult();

            // se eliminan las novedades ya procesadas de la lista de trabajo
            Map<String, Object> limpiezaNovedades = limpiarNovedadesProcesadas(novedadesProcesoAportesTemp,
                    novedadesEncontradas.getNovedadesRegistro());
*/
            // se eliminan las novedades ya procesadas de la lista de trabajo
            
            ConsultarNovedadesYaProcesadasCORE consultaNovedades = new ConsultarNovedadesYaProcesadasCORE(
                    idsRegDet);
            consultaNovedades.execute();
            List<Object[]>  novedadesEncontradas = consultaNovedades.getResult();
            Map<String, Object> limpiezaNovedades = limpiarNovedadesProcesadasporIdRegistroDetalladoNovedad(novedadesProcesoAportesTemp,
            novedadesEncontradas);

            novedadesProcesoAportesTemp = (List<NovedadesProcesoAportesDTO>) limpiezaNovedades.get(NOVEDADES_LIMPIAS);
            listadoSalida.addAll((List<Long>) limpiezaNovedades.get(NOVEDADES_REPETIDAS));

            List<Callable<List<Long>>> tareasParalelas = new LinkedList<>();
            List<Future<List<Long>>> resultadosFuturos;

            HashMap<String, NovedadesProcesoAportesDTO> hashmap = new HashMap<>();
            for (NovedadesProcesoAportesDTO i : novedadesProcesoAportesTemp) {
                if (i.getNovedades() != null && !i.getNovedades().isEmpty()) {
                    if (hashmap.containsKey(i.getNovedades().get(0).getNumeroIdentificacionCotizante())) {
                        NovedadesProcesoAportesDTO novedadesPorIdentificacion = hashmap.get(i.getNovedades().get(0).getNumeroIdentificacionCotizante());
                        novedadesPorIdentificacion.getNovedades().addAll(i.getNovedades());

                        //novedadesPorIdentificacion.getNovedades().sort((o1, o2) -> Long.compare(o1.getIdRegistroDetallado(), o2.getIdRegistroDetallado()));
                        hashmap.put(i.getNovedades().get(0).getNumeroIdentificacionCotizante(), novedadesPorIdentificacion);
                    } else {
                        i.getNovedades().sort((o1, o2) -> Long.compare(o1.getIdRegistroDetallado(), o2.getIdRegistroDetallado()));
                        hashmap.put(i.getNovedades().get(0).getNumeroIdentificacionCotizante(), i);
                    }
                }
            }
            //TODO: Verificar si este ordenamiento final es o no necesario
             novedadesProcesoAportesTemp = new ArrayList<>(hashmap.values());
            novedadesProcesoAportesTemp.sort((o1, o2) -> Long.compare(o1.getIdRegistroDetallado(), o2.getIdRegistroDetallado()));
                try {
                for (NovedadesProcesoAportesDTO novedades : novedadesProcesoAportesTemp) {

                    // ids.addAll(procesarNovedadesAporte(novedades, userDTO));
                    
                     if (novedades.getNovedades() != null && !novedades.getNovedades().isEmpty()) {
                         Callable<List<Long>> parallelTask = () -> {
                             logger.info("**__**ingresa a for procesarNovedadesAporte ");
 
                             return procesarNovedadesAporte(novedades, userDTO);
                         };
                         tareasParalelas.add(parallelTask);
                     }
                     
                }
                try {
                    resultadosFuturos = mesAportes.invokeAll(tareasParalelas);
                } catch (Exception e) {
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                }

            for (Future<List<Long>> future : resultadosFuturos) {
                listadoSalida.addAll(future.get());
            }
            } catch (Exception e) {
                logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + e.getMessage());
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            listadoSalida = Collections.emptyList();
        }

        // se limpian los resultados nulos
        for (int i = 0; i < listadoSalida.size(); i++) {
            if (listadoSalida.get(i) == null) {
                listadoSalida.remove(i);
                i--;
            }
        }
        logger.info("**__**Finaliza a registrarNovedadesFuturasIndependientes listadoSalida:"+listadoSalida.size());
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listadoSalida;
    }

    /**
     * @param novedadesProcesoAportes
     * @param novedadesEncontradas
     * @return
     */
    private Map<String, Object> limpiarNovedadesProcesadas(List<NovedadesProcesoAportesDTO> novedadesProcesoAportes,
            Map<Long, Set<TipoTransaccionEnum>> novedadesEncontradas) {
            //novedades de pila
        Map<String, Object> resultado = new HashMap<>();
        List<NovedadesProcesoAportesDTO> novedadesProcesoAportesTemp = novedadesProcesoAportes;
        List<Long> idsNovedadesProcesadas = new ArrayList<>();

        if (novedadesEncontradas != null && !novedadesEncontradas.isEmpty()) {
            for (NovedadesProcesoAportesDTO novedades : novedadesProcesoAportesTemp) {
                Set<TipoTransaccionEnum> novedadesRegistro = novedadesEncontradas.get(novedades.getIdRegistroDetallado());
                List<NovedadPilaDTO> novedadesALimpiar = new ArrayList<>();

                if (novedadesRegistro != null && !novedadesRegistro.isEmpty()) {
                    for (NovedadPilaDTO novedad : novedades.getNovedades()) {
                        if ((novedad.getTipoTransaccion() == null && novedad.getEsRetiro() && hayRetiro(novedadesRegistro, novedad.getTipoCotizante()))
                                || (novedad.getTipoTransaccion() != null
                                && novedadesRegistro.contains(novedad.getTipoTransaccion()))) {
                            logger.info("**__**>>>]limpiarNovedadesProcesadas getAccionNovedad: " + novedad.getAccionNovedad());
                            logger.info("**__**>>>]limpiarNovedadesProcesadas getNumeroIdentificacionCotizante" + novedad.getNumeroIdentificacionCotizante());
                            logger.info("**__**>>>]limpiarNovedadesProcesadas tTipoTransaccion: " + novedad.getTipoTransaccion());
                            logger.info("**__**>>>]limpiarNovedadesProcesadas tFechaInicioNovedad: " + novedad.getFechaInicioNovedad());
                            novedadesALimpiar.add(novedad);
                            idsNovedadesProcesadas.add(novedad.getIdRegistroDetalladoNovedad());
                        }
                    }
                }
                novedades.getNovedades().removeAll(novedadesALimpiar);
            }
        }
        resultado.put(NOVEDADES_LIMPIAS, novedadesProcesoAportesTemp);
        resultado.put(NOVEDADES_REPETIDAS, idsNovedadesProcesadas);
        return resultado;
    }
    private Map<String, Object> limpiarNovedadesProcesadasporIdRegistroDetalladoNovedad(List<NovedadesProcesoAportesDTO> novedadesProcesoAportes,
    List<Object[]>  novedadesEncontradas) {
            //novedades de pila
        Map<String, Object> resultado = new HashMap<>();
        List<NovedadesProcesoAportesDTO> novedadesProcesoAportesTemp = novedadesProcesoAportes;
        List<Long> idsNovedadesProcesadas = new ArrayList<>();

        if (novedadesEncontradas != null) {
            for (NovedadesProcesoAportesDTO novedades : novedadesProcesoAportesTemp) { 
                List<NovedadPilaDTO> novedadesALimpiar = new ArrayList<>();
                    for (NovedadPilaDTO novedad : novedades.getNovedades()) {

                        if(Boolean.TRUE.equals(novedad.getNovedadexistenteCore()) ){
                            novedadesALimpiar.add(novedad);
                            idsNovedadesProcesadas.add(novedad.getIdRegistroDetalladoNovedad());
                        }

                       /*  for (Object[] linea : novedadesEncontradas) {
                            Long idRegistroDetalladoNovedadCore = Long.valueOf(linea[0].toString());
                            if(novedad.getIdRegistroDetalladoNovedad() == idRegistroDetalladoNovedadCore ){
                              
                            }
                        } */
                    }
                novedades.getNovedades().removeAll(novedadesALimpiar);
            }
        }
        resultado.put(NOVEDADES_LIMPIAS, novedadesProcesoAportesTemp);
        resultado.put(NOVEDADES_REPETIDAS, idsNovedadesProcesadas);
        return resultado;
    }
    /**
     * @param novedadesRegistro
     * @param tipoAfiliado
     * @return
     */
    private Boolean hayRetiro(Set<TipoTransaccionEnum> novedadesRegistro, TipoAfiliadoEnum tipoAfiliado) {
        switch (tipoAfiliado) {
            case PENSIONADO:
                for (TipoTransaccionEnum tipoNovedad : novedadesRegistro) {
                    switch (tipoNovedad) {
                        case RETIRO_PENSIONADO_25ANIOS:
                        case RETIRO_PENSIONADO_MAYOR_1_5SM_0_6:
                        case RETIRO_PENSIONADO_MAYOR_1_5SM_2:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_0:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_0_6:
                        case RETIRO_PENSIONADO_MENOR_1_5SM_2:
                        case RETIRO_PENSIONADO_PENSION_FAMILIAR:
                            return Boolean.TRUE;
                        default:
                            break;
                    }
                }
                break;
            case TRABAJADOR_DEPENDIENTE:
                if (novedadesRegistro.contains(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE)) {
                    return Boolean.TRUE;
                }
                break;
            case TRABAJADOR_INDEPENDIENTE:
                if (novedadesRegistro.contains(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE)) {
                    return Boolean.TRUE;
                }
                break;
            default:
                break;
        }
        return Boolean.FALSE;
    }

    /**
     * Método para el procesamiento de novedades de PILA
     *
     * @param novedades DTO que contiene las novedades de un registro detallado
     * @return <b>List<Long></b> Listado de IDs de novedades procesadas
     */
    @Asynchronous
    private List<Long> procesarNovedadesAporte(NovedadesProcesoAportesDTO novedades, UserDTO userDTO) {
        System.out.println("**__**Inicio procesarNovedadesAporte: ");
        List<Long> listadoSalida = registrarNovedadesPila(novedades.getNovedades(), novedades.getCanal(),
                novedades.getTipoIdentificacion(), novedades.getNumeroIdentificacion(), null, null, null, userDTO);
        System.out.println("**__**FIN procesarNovedadesAporte: ");
        return listadoSalida;
    }

    /**
     * Método encargado de actualizar el rol afiliado.
     *
     * @param rolAfiliadoDTO rol afiliado.
     */
    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoDTO) {
        ActualizarRolAfiliado actualizarRolService = new ActualizarRolAfiliado(rolAfiliadoDTO);
        actualizarRolService.execute();
    }

    @Override
    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesarAportesNovedadesByIdPlanillaSincrono(Long indicePlanilla) {
        procesarAportesNovedadesByIdPlanilla(indicePlanilla);
    }

    @Override
    //@Asynchronous
    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesarAportesNovedadesByIdPlanilla(Long indicePlanilla) {

        String firmaServicio = "Aportes -> AportesCompositeBusiness.procesarAportesNovedadesByIdPlanilla( ) " + indicePlanilla;

        logger.info("Aportes " + ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ControladorCarteraPlanilla controladorCarteraPlanilla = new ControladorCarteraPlanilla();
        controladorCarteraPlanilla.setIndicePlanilla(indicePlanilla);
        controladorCarteraPlanilla.setFechaCreacionRegistro(new Date());
        entityManager.persist(controladorCarteraPlanilla);

        String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
        sTamanoPaginador = sTamanoPaginador == null ? "50" : sTamanoPaginador;
        Integer tamanoPaginador = new Integer(sTamanoPaginador);

        // -------------------------------
        // Aportes datos generados en B8 de PILA
        // -------------------------------
        datosGeneradosB8Pila(indicePlanilla);
        // -------------------------------

        logger.info("Aportes -> " + ConstantesComunes.INICIO_LOGGER + firmaServicio + " B8 ok");
        // -------------------------------
        // Novedades datos generados en B9 de PILA
        // -------------------------------
        // se consultan las novedades por procesar
        datosGeneradosB9Pila(indicePlanilla, tamanoPaginador);
        logger.info("Aportes -> " + ConstantesComunes.INICIO_LOGGER + firmaServicio + " B9 ok");

        // -------------------------------
        // Notificacion de planilla procesada genera estado en B10 Pila
        // -------------------------------
        try {
            notificarAportesPlanilla(indicePlanilla);
            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.NOTIFICAR_PLANILLA, PilaEstadoTransitorioEnum.EXITOSO);
        } catch (Exception e) {
            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.NOTIFICAR_PLANILLA, PilaEstadoTransitorioEnum.FALLIDO);
            throw e;
        }
        // -------------------------------

        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " B10 ok");

        // -------------------------------
        // llamado a cartera asincrono
        // -------------------------------
        try {
            validarPasoPlanilla(indicePlanilla, controladorCarteraPlanilla);
            List<ControladorCarteraPlanilla> cantidad = entityManager
                    .createNamedQuery(
                            NamedQueriesConstants.CONSULTAR_PLANILLA_PEND_PROCESAR_CARTERA)
                    .setParameter("indicePlanilla", indicePlanilla).getResultList();
            if (!cantidad.isEmpty()) {
                validarPasoPlanilla(indicePlanilla, controladorCarteraPlanilla);
            }
            entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_PLANILLAS_PROCESADAS)
                    .setParameter("indicePlanilla", indicePlanilla).executeUpdate();

            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.NOTIFICAR_CARTERA, PilaEstadoTransitorioEnum.EXITOSO);
            logger.info("termina sin errores callbackCarteraAsincrono ---> " + indicePlanilla);
        } catch (Exception e) {
            logger.info("termina con errores callbackCarteraAsincrono ---> " + indicePlanilla + "error ---> " + e.getMessage());
            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.NOTIFICAR_CARTERA, PilaEstadoTransitorioEnum.FALLIDO);
        }
        logger.info("termina callbackCarteraAsincrono ---> " + indicePlanilla);
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " Notificación a cartera ok");
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

    }

    private void validarPasoPlanilla(Long indicePlanilla, ControladorCarteraPlanilla controladorCarteraPlanilla) {
        List<ControladorCarteraAportes> controladorCarteraAportesList = controlarPasoCartera(indicePlanilla, controladorCarteraPlanilla);
        logger.info("controladorCarteraAportesList ---- 1.0 " + controladorCarteraAportesList.size());
        callbackCarteraAsincrono(controladorCarteraAportesList, controladorCarteraPlanilla);
        List<ControladorCarteraAportes> cantidad = entityManager
                .createNamedQuery(
                        NamedQueriesConstants.CONSULTAR_APORTES_PEND_PROCESAR_CARTERA)
                .setParameter("indicePlanilla", indicePlanilla).getResultList();
        logger.info("cantidad ---- 2.0 " + cantidad.size());
        if (!cantidad.isEmpty()) {
            callbackCarteraAsincrono(cantidad, controladorCarteraPlanilla);
        }
        entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_APORTES_PROCESADOS)
                .setParameter("indicePlanilla", indicePlanilla).executeUpdate();
    }

    private List<ControladorCarteraAportes> controlarPasoCartera(Long indicePlanilla, ControladorCarteraPlanilla controladorCarteraPlanilla) {
        ConsultarRegistroGeneralxRegistroControl cr = new ConsultarRegistroGeneralxRegistroControl(indicePlanilla);
        cr.execute();
        RegistroGeneral reg = cr.getResult();

        ConsultarAportesGeneralesPorIdRegGeneral aportesService = new ConsultarAportesGeneralesPorIdRegGeneral(reg.getId());
        aportesService.execute();
        List<AporteGeneralModeloDTO> aportes = aportesService.getResult();
        List<ControladorCarteraAportes> controladorCarteraAportesList = new ArrayList<>();
        for (AporteGeneralModeloDTO aporte : aportes) {
            ControladorCarteraAportes controladorCarteraAportes = new ControladorCarteraAportes();
            TipoIdentificacionEnum tipoIdentificacion;
            String numeroidentificacion;
            if (aporte.getIdEmpresa() != null) {
                ConsultarEmpresaPorId empresaService = new ConsultarEmpresaPorId(aporte.getIdEmpresa());
                empresaService.execute();
                EmpresaModeloDTO empresa = empresaService.getResult();
                tipoIdentificacion = empresa.getTipoIdentificacion();
                numeroidentificacion = empresa.getNumeroIdentificacion();
            } else {
                ConsultarPersona personaService = new ConsultarPersona(aporte.getIdPersona());
                personaService.execute();
                PersonaModeloDTO persona = personaService.getResult();
                tipoIdentificacion = persona.getTipoIdentificacion();
                numeroidentificacion = persona.getNumeroIdentificacion();
            }

            controladorCarteraAportes.setNumeroidentificacion(numeroidentificacion);
            controladorCarteraAportes.setTipoIdentificacion(tipoIdentificacion);
            controladorCarteraAportes.setPeriodoAporte(aporte.getPeriodoAporte());
            controladorCarteraAportes.setTipoSolicitante(aporte.getTipoSolicitante());
            controladorCarteraAportes.setIndicePlanilla(indicePlanilla);
            entityManager.persist(controladorCarteraAportes);

            controladorCarteraAportesList.add(controladorCarteraAportes);
        }

        controladorCarteraPlanilla.setCantidadAportes((long) aportes.size());
        controladorCarteraPlanilla.setRegistroGenral(reg.getId());
        entityManager.merge(controladorCarteraPlanilla);
        logger.info("controladorCarteraAportesList " + controladorCarteraAportesList.size());
        logger.info("aportesList " + aportes.size());

        return controladorCarteraAportesList;
    }

    @Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void procesarPlanillasSinNovedadesASYNC(Long indicePlanilla){
        CopiarDatosAportesPlanilla copiarDatosAportesPlanilla = new CopiarDatosAportesPlanilla(indicePlanilla);
        try {
            logger.info("**__**INICIA CopiarDatosAportesPlanilla ASYNC Liberacion Planillas:" + indicePlanilla);
            copiarDatosAportesPlanilla.execute();
            notificarAportesPlanilla(indicePlanilla);
        } catch (Exception e) {
            logger.info("**__**catch procesarPlanillasSinNovedadesASYNC error motivo: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,e);
        }
    }
    private void datosGeneradosB8Pila(Long indicePlanilla) {

        TechnicalException te = null;
        CopiarDatosAportesPlanilla copiarDatosAportesPlanilla = new CopiarDatosAportesPlanilla(indicePlanilla);
        ContarTemAportesPendientes ctap = new ContarTemAportesPendientes(indicePlanilla);
        // -------------------------------
        // Aportes datos generados en B8 de PILA
        // -------------------------------
        try {
            logger.info("**__**INICIA CopiarDatosAportesPlanilla que finaliza en aportes SP USP_COPIAR_DATOS_TEMPORALES_APORTES:" + indicePlanilla);
            copiarDatosAportesPlanilla.execute();
            ctap.execute();
            //Long cantidad = ctap.getResult();

            logger.info("ctap:" + ctap);
            logger.info("ctap.getResult:" + ctap.getResult());
            if (ctap.getResult().equals(0L)) {
                crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.COPIAR_APORTES, PilaEstadoTransitorioEnum.EXITOSO);
            }
            // else {
            //     logger.info("ctap tiene más de un registro ---------------------");
            //     te = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            // }

        } catch (Exception e) {
            logger.info("**__**catch datosGeneradosB8Pila error motivo: " + e);
            te = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        if (te != null) {

            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.COPIAR_APORTES, PilaEstadoTransitorioEnum.FALLIDO);
            logger.error(ConstantesComunes.FIN_LOGGER, te);
            logger.info(ConstantesComunes.FIN_LOGGER, te);
            throw te;
        }
        logger.info("Pasa datosGeneradosB8Pila ok");
    }

    private void datosGeneradosB9Pila(Long indicePlanilla, Integer tamanoPaginador) {

        TechnicalException te = null;
        // -------------------------------
        // Novedades datos generados en B9 de PILA
        // -------------------------------
        // se consultan las novedades por procesar
        try {
            List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = consultarInformacionNovedadesProcesarByIdPlanilla(false, indicePlanilla);
            if (!infoNovedades.isEmpty()) {
                logger.info("**__**Novedades por procesar: " + indicePlanilla);
                logger.info("infoNovedades getRegistroGeneral" + infoNovedades.get(0).getRegistroGeneral());
                procesarNovedadesPlanilla(infoNovedades.get(0), tamanoPaginador, null);
            } else {
                logger.info("SAP - Sin Novedades por procesar: " + indicePlanilla);
            }

            // una vez se procesan novedades, se actualizan los temAporteProcesados
            ActualizarTemAporteProcesadoByIdPlanilla actualizarTemAporteProcesado = new ActualizarTemAporteProcesadoByIdPlanilla(indicePlanilla);
            actualizarTemAporteProcesado.execute();

            //notificarAportesPila()
            // se procesan los cambios en aportes por análisis integral
            ActualizacionAportesRecalculados actualizacionAportes = new ActualizacionAportesRecalculados(Boolean.FALSE, new ArrayList<Long>());
            actualizacionAportes.execute();

            infoNovedades = consultarInformacionNovedadesProcesarByIdPlanilla(true, indicePlanilla);
            if (infoNovedades.isEmpty()) {
                crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.PROCESAR_NOVEDADES, PilaEstadoTransitorioEnum.EXITOSO);
            }
            // else {
            //     te = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            //}
        } catch (Exception e) {
            te = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        if (te != null) {
            crearPilaEstadoTransitorio(indicePlanilla, PilaAccionTransitorioEnum.PROCESAR_NOVEDADES, PilaEstadoTransitorioEnum.FALLIDO);
            throw te;
        }

        // -------------------------------
        //logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio + " B9 ok");
    }

    private void callbackCarteraAsincrono(List<ControladorCarteraAportes> controladorCarteraAportesList, ControladorCarteraPlanilla controladorCarteraPlanilla) {
        logger.info("callbackCarteraAsincrono -- Total registros a procesar: " + controladorCarteraAportesList.size());

        List<ControladorCarteraAportes> pendientes = new ArrayList<>(controladorCarteraAportesList);
        int intento = 0;

        if (!controladorCarteraAportesList.isEmpty()) {

            while (!pendientes.isEmpty() && intento < MAX_REINTENTOS) {
                logger.info("pendientes " + pendientes.size());
                List<ControladorCarteraAportes> fallidos = new ArrayList<>();

                for (ControladorCarteraAportes contralador : pendientes) {
                    try {
                        actualizarDeudaPresuntaCartera(
                                contralador.getTipoIdentificacion(),
                                contralador.getNumeroidentificacion(),
                                contralador.getPeriodoAporte(),
                                contralador.getTipoSolicitante()
                        );
                        contralador.setPasoCartera(Boolean.TRUE);
                        contralador.setFechaCreacionRegistro(new Date());
                        entityManager.merge(contralador);
                    } catch (Exception e) {
                        logger.error("Error al procesar cartera para: " + contralador.getNumeroidentificacion(), e);
                        fallidos.add(contralador);
                        logger.info("hay fallidos ?? " + fallidos);
                        contralador.setPasoCartera(Boolean.FALSE);
                        contralador.setFechaCreacionRegistro(new Date());
                        entityManager.merge(contralador);
                    }
                }

                pendientes = fallidos;
                intento++;
            }
            controladorCarteraPlanilla.setPasoCartera(Boolean.TRUE);
            entityManager.merge(controladorCarteraPlanilla);
        } else {
            controladorCarteraPlanilla.setPasoCartera(Boolean.FALSE);
            entityManager.merge(controladorCarteraPlanilla);
        }

    }

    /**
     * Método que consulta la información de las novedades a registrar o
     * relacionar por idPlanilla
     *
     * @return
     */
    private List<InformacionPlanillasRegistrarProcesarDTO> consultarInformacionNovedadesProcesarByIdPlanilla(Boolean omitirMarca, Long idPlanilla) {
        String firmaServicio = "AportesCompositeBusiness.consultarInformacionNovedadesProcesarByIdPlanilla()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("DEFINE SI TIENE NOVEDADES" + idPlanilla + " omitirMarca" + omitirMarca);

        //  try {
        ConsultarInformacionNovedadesRegistrarProcesarByIdPlanilla servicio = new ConsultarInformacionNovedadesRegistrarProcesarByIdPlanilla(omitirMarca, idPlanilla);
        servicio.execute();

        // } catch (Exception e) {
//
        //    logger.info( "Error generado consultarInformacionNovedadesProcesarByIdPlanilla "+e.getMessage() );
        //    return null;
        //    throw e;
        //}
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return servicio.getResult();
    }

    private void notificarAportesPlanilla(Long idPlanilla) {
        String firmaServicio = "AportesCompositeBusiness.notificarAportesPlanilla()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        // se consultan las planillas listas para notificar
        List<DatosComunicadoPlanillaDTO> infoNotificaciones = consultarDatosComunicadosPlanilla(idPlanilla);
        logger.info("**__**Procede a enviarComunicadoPila idPlanilla: " + idPlanilla);
        if (!infoNotificaciones.isEmpty()) {
            List<Long> aportesNotificados = new ArrayList<>();
            Boolean success = false;
            ConsultaPlanillaResultDTO datosPlanilla = consultarPlanilla(infoNotificaciones.get(0).getIdPlanilla());
            Long cantidadplanillasN = buscarNotificacionPlanillasNComposite(idPlanilla);
            logger.info("PLANILLA N buscarNotificacionPlanillasNComposite : " +cantidadplanillasN );
            if (cantidadplanillasN > 0L) {
                
                if(consultarPlanillaNNotificar(idPlanilla)){
                logger.info("Se puede notificar SP");
                //ES PLANILLA N si existe en la tabla consultada en el metodo BuscarNotificacionPlanillasN Y SE NOTIFICARA DIFERENTE NO POR EL COMPARATIVO DE APORTES
                success = actualizarEstadosPlanilla(infoNotificaciones.get(0).getIdPlanilla());
                }
                if (success == false) {
                    logger.error("PLANILLA N No se pudo actualizar el estado de la planilla N : " + infoNotificaciones.get(0).getIdPlanilla());
                } else {
                    logger.info("PLANILLA N **__**EXITO NOTIFICACION DE PLANILLA EXITOSO indice: " + infoNotificaciones.get(0).getIdPlanilla());
                }

                //FIN notificacion en planilla actulizara a BLOQUE 10
                try {
                    verificarEmailAportante(datosPlanilla.getListadoAportes());
                } catch (Exception e) {
                    logger.info("**__**PLANILLA N Excepcion verificarEmailAportante error: " + e);
                }

                List<NotificacionParametrizadaDTO> notificaciones = crearNotificacionesParametrizadas(
                        datosPlanilla.getListadoAportes());
                try {
                    for (NotificacionParametrizadaDTO notificacion : notificaciones) {
                        enviarNotificacionComunicado(notificacion);
                    }
                } catch (Exception y) {
                    logger.info("**__** PLANILLA N Excepcion enviarNotificacionComunicado error: " + y);
                }
                aportesNotificados.add(infoNotificaciones.get(0).getIdPlanilla());
            } else {
                logger.info("**__**Ingresa a aportesNotificados.add(enviarComunicadoPila(infoNotificaciones.get(0)))");
                aportesNotificados.add(enviarComunicadoPila(infoNotificaciones.get(0)));
            }

            // Se eliminan los TemAporteProcesados de datos notificados
            eliminarTemAportesProcesadosNotificados(aportesNotificados);

        } else {
            logger.info("**__**Viene vacio consultarDatosComunicadosPlanilla->infoNotificaciones");
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }


    public Boolean consultarPlanillaNNotificar (Long Id){
        ConsultarPlanillaNNotificar consultarPlanillaNNotificar = new ConsultarPlanillaNNotificar(Id);
        consultarPlanillaNNotificar.execute();
        return consultarPlanillaNNotificar.getResult();
    }
  /**
     * Método encargado de consultar el estado de la planilla que se acaba de
     * procesar, para verificar si se envía o no el comunicado
     *
     * @param idPlanilla el identificador de la planilla
     * @return boolean true si el estado es valido para el proceso, false en
     * caso contrario.
     */
    private Long buscarNotificacionPlanillasNComposite(Long idPlanilla) {
        BuscarNotificacionPlanillasN consultarcantidadTablaN = new BuscarNotificacionPlanillasN(idPlanilla);
        consultarcantidadTablaN.execute();
        return consultarcantidadTablaN.getResult();
    }

    /**
     * Método que consult el micro servicio para consultar la información de las
     * notificaciones a procesar
     *
     * @return
     */
    private List<DatosComunicadoPlanillaDTO> consultarDatosComunicadosPlanilla(Long idPlanilla) {
        String firmaServicio = "AportesCompositeBusiness.consultarDatosComunicadosPlanilla()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultarDatosComunicadoByIdPlanilla servicio = new ConsultarDatosComunicadoByIdPlanilla(idPlanilla);
        servicio.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return servicio.getResult();
    }

    @Override
    public List<TasasInteresMoraModeloDTO> consultarTasasInteresMoraAportesComposite() {

        List<TasasInteresMoraModeloDTO> tasas = new ArrayList<>();

        ConsultarTasasInteresMoraAportes consultaTasas = new ConsultarTasasInteresMoraAportes();
        consultaTasas.execute();

        tasas = consultaTasas.getResult();

        return tasas;
    }

    @Override
    public Boolean modificarTasaInteresMoraComposite(ModificarTasaInteresMoraDTO tasaModificada) {

        ResultadoModificarTasaInteresDTO resultadoActualizarTasa = new ResultadoModificarTasaInteresDTO();

        ModificarTasaInteresMoraAportes actualizarTasa = new ModificarTasaInteresMoraAportes(tasaModificada);
        actualizarTasa.execute();
        resultadoActualizarTasa = actualizarTasa.getResult();

        if (resultadoActualizarTasa.getModificaTasa().equals(Boolean.TRUE)) {
            IniciarVariablesGenerales actualizarVariables = new IniciarVariablesGenerales();
            actualizarVariables.execute();
        }

        return resultadoActualizarTasa.getRespuestaServicio();

    }

    @Override
    public Boolean crearTasaInteresInteresMoraComposite(ModificarTasaInteresMoraDTO nuevaTasa) {

        Boolean resultado;

        CrearTasaInteresInteresMora tasa = new CrearTasaInteresInteresMora(nuevaTasa);
        tasa.execute();
        resultado = tasa.getResult();

        if (resultado == Boolean.TRUE) {
            IniciarVariablesGenerales actualizarVariables = new IniciarVariablesGenerales();
            actualizarVariables.execute();
        }

        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.aportes.composite.service.AportesCompositeService#procesarNovedadIngresoAporte(com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO)
     */
    @Override
    public Boolean procesarNovedadIngresoUtilitario(ProcesoIngresoUtilitarioDTO datosProcesoIng) {
        String firmaMetodo = "AportesCompositeBusiness.procesarNovedadIngresoUtilitario(ProcesoIngresoUtilitarioDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean habilitadoEmpleador = true;
        Boolean reintegroProcesado = Boolean.FALSE;

        /*
         * si es reintegrable se activa el afiliado tanto el afiliado como el
         * empleador, Null es válido para independientes
         */
        RegistroDetalladoModeloDTO datosAfiliado = null;
        EmpleadorModeloDTO datosEmpleador = null;
        SucursalEmpresaModeloDTO datosSucursal = null;
        ActivacionAfiliadoDTO datosActivacion = new ActivacionAfiliadoDTO();

        // se consulta el registro detallado para lso datos del cotizante,
        // en caso de requerir la creación del registro
        ConsultarRegistroDetalladoPorId consultarRegistroDetalladoPorId = new ConsultarRegistroDetalladoPorId(
                datosProcesoIng.getIdRegistroDetallado());
        consultarRegistroDetalladoPorId.execute();

        // se consulta la información del empleador (para dependientes)
        if (datosProcesoIng.getTipoIdAportante() != null && datosProcesoIng.getNumeroIdAportante() != null) {
            PersonaModeloDTO personaModeloDTO = null;
            personaModeloDTO = new PersonaModeloDTO();
            personaModeloDTO.setTipoIdentificacion(datosProcesoIng.getTipoIdAportante());
            personaModeloDTO.setNumeroIdentificacion(datosProcesoIng.getNumeroIdAportante());
            VerificarExisteEmpleadorAsociado existeEmpleador = new VerificarExisteEmpleadorAsociado(
                    personaModeloDTO);
            existeEmpleador.execute();
            datosEmpleador = existeEmpleador.getResult();

            if (datosEmpleador != null && EstadoEmpleadorEnum.ACTIVO.equals(datosEmpleador.getEstadoEmpleador())) {

                // se consulta la información de la sucursal sí aplica, para
                // eso, se debe consultar el registro general del aporte
                ConsultarRegistroGeneralId consultarRegistroGeneralId = new ConsultarRegistroGeneralId(
                        datosProcesoIng.getIdRegistroGeneral());
                consultarRegistroGeneralId.execute();
                RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId.getResult();

                if (registroGeneral != null) {
                    SucursalEmpresa sucursal = consultarSucursalEmpresa(
                            registroGeneral.getCodSucursal() != null ? registroGeneral.getCodSucursal()
                            : registroGeneral.getOutCodSucursalPrincipal(),
                            datosProcesoIng.getTipoIdAportante(), datosProcesoIng.getNumeroIdAportante());

                    if (sucursal != null) {
                        datosSucursal = new SucursalEmpresaModeloDTO();
                        datosSucursal.convertToDTO(sucursal);
                    }
                }

                datosAfiliado = consultarRegistroDetalladoPorId.getResult();
                if (datosAfiliado.getFechaIngreso() == null) {
                    datosAfiliado.setFechaIngreso(datosProcesoIng.getFechaIngreso());
                }
                datosActivacion.setDatosAfiliado(datosAfiliado);
                datosActivacion.setEmpleador(datosEmpleador);
                datosActivacion.setSucursal(datosSucursal);
                datosActivacion.setNumeroIdAfiliado(datosProcesoIng.getNumeroIdCotizante());
                datosActivacion.setTipoIdAfiliado(datosProcesoIng.getTipoIdCotizante());
                datosActivacion.setNumeroIdAportante(datosProcesoIng.getNumeroIdAportante());
                datosActivacion.setTipoIdAportante(datosProcesoIng.getTipoIdAportante());
                datosActivacion.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
                datosActivacion.setCanalRecepcion(CanalRecepcionEnum.PILA);

                // se obtiene el estado de afiliación respecto a la caja del
                // afiliado antes de reactivar (para obtener su fecha de retiro)
                ConsultarEstadoAfiliacionRespectoCCF consultaEstado = new ConsultarEstadoAfiliacionRespectoCCF(null,
                        datosProcesoIng.getTipoIdCotizante(), datosProcesoIng.getNumeroIdCotizante());
                consultaEstado.execute();
                ConsultaEstadoAfiliacionDTO estadoAfiliacion = consultaEstado.getResult();

                Date fechaRetiroAfiliado = estadoAfiliacion.getFechaRetiro() != null
                        ? new Date(estadoAfiliacion.getFechaRetiro()) : null;

                try {
                    activarAfiliado(datosActivacion);
                } catch (Exception e) {
                    reintegroProcesado = Boolean.FALSE;
                }

                reintegroProcesado = Boolean.TRUE;

            }

        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return reintegroProcesado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.aportes.composite.service.AportesCompositeService#procesarNovedadIngresoAporte(com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO)
     */
    @Override
    public Boolean procesarNovedadRetiroUtilitario(ProcesoIngresoUtilitarioDTO datosProcesoRet) {
        String firmaMetodo = "AportesCompositeBusiness.procesarNovedadRetiroUtilitario(ProcesoNovedadIngresoDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
        novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);

        // se asigna el valor de la marca de novedad que viene por pila
        novedadAporte.setAplicar(MarcaNovedadEnum.APLICADA);

        // se asigna el resto de valores corrspondientes a la novedad que se
        // desea radicar
        if (datosProcesoRet.getFecharetiro() != null) {
            novedadAporte.setFechaInicio(datosProcesoRet.getFecharetiro());
        }

        novedadAporte.setComentarios("- Se procesó la novedad, el trabajador ha sido retirado de la caja");
        novedadAporte.setTipoNovedad(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);

        novedadAporte.setNumeroIdentificacion(datosProcesoRet.getNumeroIdCotizante());
        novedadAporte.setTipoIdentificacion(datosProcesoRet.getTipoIdCotizante());

        novedadAporte.setNumeroIdentificacionAportante(datosProcesoRet.getNumeroIdAportante());
        novedadAporte.setTipoIdentificacionAportante(datosProcesoRet.getTipoIdAportante());
        novedadAporte.setCanalRecepcion(CanalRecepcionEnum.PILA);
        novedadAporte.setIdRegistroDetallado(datosProcesoRet.getIdRegistroDetalladoRet());

        try {
            RadicarSolicitudNovedadAportes radicarSolicitudNovedad = new RadicarSolicitudNovedadAportes(
                    novedadAporte);
            radicarSolicitudNovedad.execute();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + " :: Ocurrió un error radicando la solicitud de novedad para "
                    + datosProcesoRet.getTipoIdCotizante() + "-"
                    + datosProcesoRet.getNumeroIdCotizante(), e);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.aportes.composite.service.AportesCompositeService#procesarListaIngresoUtilitario()
     */
    @Override
    @Asynchronous
    public void procesarListaIngresoUtilitario(List<ProcesoIngresoUtilitarioDTO> listaPersonasIngresar) {
        String firmaMetodo = "Utilitario lista ingreso ";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (ProcesoIngresoUtilitarioDTO persona : listaPersonasIngresar) {
            Date date1;
            Date date2;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(persona.getFechaIng());
                persona.setFechaIngreso(date1.getTime());

                date2 = new SimpleDateFormat("dd/MM/yyyy").parse(persona.getFechaRet());
                persona.setFecharetiro(date2.getTime());
            } catch (ParseException e) {
                persona.setLog("Formato incorrecto en las fechas de ingreso y/o retiro");
                persona.setEstadoAfiliacion("FALLIDO");
            }
        }

        for (ProcesoIngresoUtilitarioDTO persona : listaPersonasIngresar) {
            try {
                logger.info("Numero Identificacion: " + persona.getNumeroIdCotizante());

                ActivacionAfiliadoDTO datosActivacion = new ActivacionAfiliadoDTO();
                datosActivacion.setNumeroIdAfiliado(persona.getNumeroIdCotizante());
                datosActivacion.setTipoIdAfiliado(persona.getTipoIdCotizante());
                datosActivacion.setNumeroIdAportante(persona.getNumeroIdAportante());
                datosActivacion.setTipoIdAportante(persona.getTipoIdAportante());
                datosActivacion.setTipoAfiliado(persona.getTipoCotizante());

                ConsultarEstadoRolAfiliadoConEmpleador consultarEstado = new ConsultarEstadoRolAfiliadoConEmpleador(
                        datosActivacion);
                consultarEstado.execute();
                EstadoAfiliadoEnum estado = consultarEstado.getResult();

                if (estado == null) {
                    if (persona.getFechaIngreso() != null) {
                        procesarNovedadIngresoUtilitario(persona);
                    }

                    if (persona.getIdRegistroDetalladoRet() != null && persona.getFecharetiro() != null) {
                        procesarNovedadRetiroUtilitario(persona);
                    }

                    persona.setEstadoAfiliacion("EXITOSO");
                } else {
                    persona.setEstadoAfiliacion("YA ACTIVA");
                }
            } catch (Exception e) {
                persona.setLog("Exception " + e.getMessage());
                persona.setEstadoAfiliacion("FALLIDO");
            }
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.aportes.composite.service.AportesCompositeService#procesarNovedadesFuturasProcessSchedule()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    @Asynchronous
    public void procesarNovedadesFuturasProcessSchedule() {

        String sTamanoPaginador = (String) CacheManager.getParametro(ParametrosSistemaConstants.TAMANO_PAGINADOR);
        Integer tamanoPaginador;

        if (sTamanoPaginador != null) {
            tamanoPaginador = new Integer(sTamanoPaginador);
        } else {
            tamanoPaginador = new Integer(250);
        }

        // se consultan las novedades por procesar
        ConsultarInformacionNovedadesRegistrarProcesarFuturas servicio = new ConsultarInformacionNovedadesRegistrarProcesarFuturas();
        servicio.execute();

        List<InformacionPlanillasRegistrarProcesarDTO> infoNovedades = servicio.getResult();
        //List<Callable<Boolean>> parallelTaskListNovedades = new ArrayList<>();

        for (InformacionPlanillasRegistrarProcesarDTO infoNovedad : infoNovedades) {
            try {

                procesarNovedadesPlanilla(infoNovedad, tamanoPaginador, null);
            } catch (Exception e) {
                logger.info("Error al procesar planilla con registro general: " + infoNovedad.getRegistroGeneral() , e);
            }
            // Callable<Boolean> parallelTask = () -> {
            //     return procesarNovedadesPlanilla(infoNovedad, tamanoPaginador, null);
            // };
            // parallelTaskListNovedades.add(parallelTask);
        }

        // try {
        //     mes.invokeAll(parallelTaskListNovedades);
        // } catch (InterruptedException e) {
        //     logger.error(ConstantesComunes.FIN_LOGGER + "afdaadfa", e);
        // }

    }

    /**
     * invocación del servicio que crea el estado de la acción correspondiente
     */
    private void crearPilaEstadoTransitorio(Long pilaIndicePlanilla, PilaAccionTransitorioEnum accion, PilaEstadoTransitorioEnum estado) {

        logger.info("Aportes -> crearPilaEstadoTransitorio ");
        logger.info("Aportes -> pilaIndicePlanilla " + pilaIndicePlanilla);
        logger.info("Aportes -> accion " + accion);
        logger.info("Aportes -> estado " + estado);
        logger.info("Aportes -> fecha:: " + new Date());

        PilaEstadoTransitorio pilaEstadoTransitorio = new PilaEstadoTransitorio();
        pilaEstadoTransitorio.setPilaIndicePlanilla(pilaIndicePlanilla);
        pilaEstadoTransitorio.setAccion(accion);
        pilaEstadoTransitorio.setEstado(estado);

        pilaEstadoTransitorio.setFecha(new Date());
        logger.info("**__**inicia CrearPilaEstadoTransitorio en  crearPilaEstadoTransitorio-");
        CrearPilaEstadoTransitorio c = new CrearPilaEstadoTransitorio(pilaEstadoTransitorio);
        c.execute();
        logger.info("**__**Fin CrearPilaEstadoTransitorio en  crearPilaEstadoTransitorio-");

    }

    @Override
    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    public void procesarPlanillaBandejaTransitoria(Long indicePlanilla, PilaAccionTransitorioEnum accion, UserDTO userDTO) {

        if (PilaAccionTransitorioEnum.COPIAR_APORTES.equals(accion)) {
            // reiniciar en proceso de aportes
            logger.info("reiniciar en proceso de aportes: " + indicePlanilla);
            ActualizarEstadoEnProcesoAportes aeepa = new ActualizarEstadoEnProcesoAportes(indicePlanilla);
            aeepa.execute();
        }

        ActualizarEstadoBandejaTransitoriaGestion aebt = new ActualizarEstadoBandejaTransitoriaGestion(indicePlanilla);
        aebt.execute();
        Boolean result = aebt.getResult();
        if (Boolean.TRUE.equals(result)) {
            logger.info("Aportes - procesarAportesNovedadesByIdPlanilla: " + indicePlanilla);
            procesarAportesNovedadesByIdPlanilla(indicePlanilla);
        }
    }

    @Override
    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    public void procesarTodosPlanillaBandejaTransitoria(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroPlanilla, Long fechaInicio, Long fechaFin, UserDTO userDTO) {
        logger.info("procesarTodosPlanillaBandejaTransitoria: ");
        BandejaTransitoriaGestion btg = new BandejaTransitoriaGestion(numeroPlanilla, numeroIdentificacion, tipoIdentificacion, fechaFin, fechaInicio);
        btg.execute();
        List<DatosBandejaTransitoriaDTO> lista = btg.getResult();
        for (DatosBandejaTransitoriaDTO l : lista) {
            logger.info("procesarTodosPlanillaBandejaTransitoria: " + l.getPilaIndicePlanilla());
            ProcesarPlanillaBandejaTransitoria p = new ProcesarPlanillaBandejaTransitoria(l.getPilaIndicePlanilla(), l.getAccion());
            p.execute();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void transaccionRegistrarNovedadService(CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, NovedadPilaDTO novedadPilaDTO, UserDTO userDTO) throws Exception {

        logger.info("inicia metodo AportesCompositeBussines.transaccionRegistrarNovedadService");
        Boolean radicadoIntentoNovedad = false;

        if (novedadPilaDTO.getEsIngreso() && esTrabajadorReintegrable != null
                && novedadPilaDTO.getIdRegistroDetallado() == null
                && novedadPilaDTO.getIdRegistroGeneral() == null) {
            logger.info("**__**CASO 1");
            radicadoIntentoNovedad = procesarNovedadIngresoCartera(novedadPilaDTO.getTipoCotizante(), tipoIdAportante,
                    numeroIdAportante, novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante(), esTrabajadorReintegrable,
                    novedadPilaDTO.getFechaInicioNovedad().getTime(), novedadPilaDTO.getIdTenNovedad(),
                    novedadPilaDTO.getIdRegistroDetallado());
            logger.info("FIN CASO 1");

        } else if (novedadPilaDTO.getAccionNovedad().equalsIgnoreCase("APLICAR_NOVEDAD")
                && novedadPilaDTO.getEsIngreso() && esTrabajadorReintegrable != null) {
            logger.info("**__**CASO 2");
            radicadoIntentoNovedad = procesarNovedadIngresoAporte(new ProcesoNovedadIngresoDTO(novedadPilaDTO.getTipoCotizante(),
                    tipoIdAportante, numeroIdAportante, novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante(), esTrabajadorReintegrable,
                    novedadPilaDTO.getIdRegistroDetallado(), novedadPilaDTO.getIdRegistroGeneral(),
                    esEmpleadorReintegrable, canal,
                    novedadPilaDTO.getFechaInicioNovedad() != null
                    ? novedadPilaDTO.getFechaInicioNovedad().getTime()
                    : null,
                    novedadPilaDTO.getIdTenNovedad()), userDTO);
            logger.info("FIN CASO 2");

        } else if (novedadPilaDTO.getAccionNovedad().equalsIgnoreCase("APLICAR_NOVEDAD")
                && novedadPilaDTO.getEsIngreso() && novedadPilaDTO.getEsTrabajadorReintegrable() != null) {
            logger.info("**__**CASO 3");
            radicadoIntentoNovedad = procesarNovedadIngresoAporte(new ProcesoNovedadIngresoDTO(novedadPilaDTO.getTipoCotizante(),
                    tipoIdAportante, numeroIdAportante, novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante(),
                    novedadPilaDTO.getEsTrabajadorReintegrable(), novedadPilaDTO.getIdRegistroDetallado(),
                    novedadPilaDTO.getIdRegistroGeneral(), novedadPilaDTO.getEsEmpleadorReintegrable(),
                    canal,
                    novedadPilaDTO.getFechaInicioNovedad() != null
                    ? novedadPilaDTO.getFechaInicioNovedad().getTime()
                    : null,
                    novedadPilaDTO.getIdTenNovedad()), userDTO);
            logger.info("FIN CASO 3");

        } else if (novedadPilaDTO.getAccionNovedad().equalsIgnoreCase("NO_APLICADA")
                || novedadPilaDTO.getAccionNovedad().equalsIgnoreCase("RELACIONAR_NOVEDAD")) {
            logger.info("**__**CASO 4");
            ProcesoNovedadIngresoDTO datosProcesoIng = new ProcesoNovedadIngresoDTO(
                    novedadPilaDTO.getTipoCotizante(), tipoIdAportante, numeroIdAportante,
                    novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante(),
                    novedadPilaDTO.getEsTrabajadorReintegrable(), novedadPilaDTO.getIdRegistroDetallado(),
                    novedadPilaDTO.getIdRegistroGeneral(), novedadPilaDTO.getEsEmpleadorReintegrable(),
                    canal,
                    novedadPilaDTO.getFechaInicioNovedad() != null
                    ? novedadPilaDTO.getFechaInicioNovedad().getTime()
                    : null);

            radicadoIntentoNovedad = procesarIntentoNovedad(datosProcesoIng.getTipoCotizante(), datosProcesoIng.getTipoIdAportante(),
                    datosProcesoIng.getNumeroIdAportante(), datosProcesoIng.getTipoIdCotizante(),
                    datosProcesoIng.getNumeroIdCotizante(), datosProcesoIng.getCanalRecepcion(),
                    datosProcesoIng.getIdRegistroDetallado(), novedadPilaDTO.getIdTenNovedad(),novedadPilaDTO.getIdRegistroDetalladoNovedad());

            logger.info("FIN CASO 4");
        }

        logger.info("antes ConfirmarTransaccionNovedadPilaRutine");
        //comentado ya que llena la tabla TransaccionNovedadPilaCompleta
        // if(novedadPilaDTO.getIdTenNovedad() != null && !radicadoIntentoNovedad){
        //     logger.info("procede ConfirmarTransaccionNovedadPilaRutine");
        //     ConfirmarTransaccionNovedadPilaRutine c = new ConfirmarTransaccionNovedadPilaRutine();
        //     c.confirmarTransaccionNovedadPilaRutine(novedadPilaDTO.getIdTenNovedad(),novedadPilaDTO.getIdRegistroDetallado(), entityManager);
        // } 
        logger.info("Fin metodo AportesCompositeBussines.transaccionRegistrarNovedadService");
    }

    /**
     * Método que registra una novedad de ingreso por PILA, enviada desde la
     * cartera de aportes HU-239
     *
     * @param tipoCotizante Tipo de afiliado
     * @param tipoIdAportante Tipo de identificación del empleador
     * @param numeroIdAportante Número de identificación del empleador
     * @param tipoIdCotizante Tipo de identificación del trabajador
     * @param numeroIdCotizante Número de identificación del trabajador
     * @param esReintegrable Indica si el trabajador es reintegrable
     * @param fechaIngreso Fecha de ingreso
     * @throws Exception
     */
    private Boolean procesarNovedadIngresoCartera(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdAportante,
            String numeroIdAportante, TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante,
            Boolean esReintegrable, Long fechaIngreso, Long temIdNovedad, Long registroDetallado) throws Exception {
        String firmaMetodo = "AportesCompositeBusiness.procesarNovedadIngresoCartera(TipoAfiliadoEnum, TipoIdentificacionEnum, "
                + "String, TipoIdentificacionEnum, String, Boolean, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        logger.info("**__**inicia metodo procesarNovedadIngresoCartera<>");

        if (esReintegrable != null && esReintegrable) {
            RegistroDetalladoModeloDTO datosAfiliado = new RegistroDetalladoModeloDTO();
            datosAfiliado.setFechaIngreso(fechaIngreso);
            ActivacionAfiliadoDTO datosActivacion = new ActivacionAfiliadoDTO();
            datosActivacion.setDatosAfiliado(datosAfiliado);
            datosActivacion.setNumeroIdAfiliado(numeroIdCotizante);
            datosActivacion.setTipoIdAfiliado(tipoIdCotizante);
            datosActivacion.setNumeroIdAportante(numeroIdAportante);
            datosActivacion.setTipoIdAportante(tipoIdAportante);
            datosActivacion.setTipoAfiliado(tipoCotizante);
            datosActivacion.setCanalRecepcion(CanalRecepcionEnum.CARTERA);
            logger.info("**__**inicia metodo procesarNovedadIngresoCartera<> antes de activarAfiliado");
            activarAfiliado(datosActivacion);
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return false;

        } else {
            /*
             * si las validaciones no se aprueban se crea un intento de afiliación.
             */
            logger.info("**__**inicia metodo procesarNovedadIngresoCartera<> antes de procesarIntentoNovedad");
            logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return procesarIntentoNovedad(tipoCotizante, tipoIdAportante, numeroIdAportante, tipoIdCotizante,
                    numeroIdCotizante, CanalRecepcionEnum.CARTERA, null, temIdNovedad,null);
        }

    }

    /**
     * Método que hace el llamado al microservicio que consulta y actualiza el
     * estado de un afiliado a ACTIVO
     *
     * @param tipoIdCotizante tipo de documento de identificación del afiliado
     *
     * @param numeroIdCotizante número de documento de identificación del
     * afiliado
     *
     * @param tipoIdAportante tipo de documento de identificación del empleador
     *
     * @param numeroIdAportante número de documento de identificación del
     * empleador
     */
    private void activarAfiliado(ActivacionAfiliadoDTO datosActivacion) {

        /*
         * ActivarAfiliado activarAfiliado = new ActivarAfiliado(datosActivacion);
         * activarAfiliado.execute();
         */
        try {
            System.out.println("**__**try activarAfiliado aportes composite inicia  ");
            ActivarAfiliado activarAfiliado = new ActivarAfiliado(datosActivacion);
            activarAfiliado.execute();
        } catch (Exception e) {
            System.out.println("**__**catch activarAfiliado aportes composite inicia  " + e);
            ActivarAfiliadoRutine a = new ActivarAfiliadoRutine();
            a.activarAfiliado(datosActivacion, entityManager);
        }

    }

    private Boolean procesarIntentoNovedad(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdAportante,
            String numeroIdAportante, TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante,
            CanalRecepcionEnum canalRecepcion, Long idRegistroDetallado, Long tenNovedadId, Long idRegistroDetalladoNovedad) {
        logger.info("**__**inicia NOVEDAD_REINTEGRO metodo AportesCompositeBussines.procesarIntentoNovedad");
        try {
            NovedadAportesDTO novedadAporte = new NovedadAportesDTO();

            novedadAporte.setTipoNovedad(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
            novedadAporte.setAplicar(MarcaNovedadEnum.NO_APLICADA);
            //novedadAporte.setAplicar(MarcaNovedadEnum.APLICADA);
            novedadAporte.setCanalRecepcion(canalRecepcion);

            novedadAporte.setNumeroIdentificacion(numeroIdCotizante);
            novedadAporte.setTipoIdentificacion(tipoIdCotizante);
            novedadAporte.setNumeroIdentificacionAportante(numeroIdAportante);
            novedadAporte.setTipoIdentificacionAportante(tipoIdAportante);
            novedadAporte.setIdRegistroDetallado(idRegistroDetallado);
            novedadAporte.setTenNovedadId(tenNovedadId);
            novedadAporte.setIdRegistroDetalladoNovedad(idRegistroDetalladoNovedad);
            System.out.println("**__**novedadAporte.setTenNovedadId aportes composite inicia  " + novedadAporte.getTenNovedadId()+" idRegistroDetalladoNovedad: "+idRegistroDetalladoNovedad);
            determinarClasificacionAfiliado(tipoCotizante, tipoIdCotizante, numeroIdCotizante, novedadAporte);

            RadicarSolicitudNovedadAportes radicarSolicitudNovedad = new RadicarSolicitudNovedadAportes(novedadAporte);
            radicarSolicitudNovedad.execute();

            logger.info("Fin metodo AportesCompositeBussines.procesarIntentoNovedad");
            return true;
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando la solicitud de novedad para " + tipoIdCotizante + "-"
                    + numeroIdCotizante, e);
            logger.info("no se pudo procesar la novedad");
        }
        return false;
    }

    /**
     * @param tipoCotizante
     * @param tipoIdCotizante
     * @param numeroIdCotizante
     * @param novedadAporte
     */
    private void determinarClasificacionAfiliado(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdCotizante,
            String numeroIdCotizante, NovedadAportesDTO novedadAporte) {
        // se consultan las clasificaciones del afiliado (si lo está)
        List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(tipoIdCotizante,
                numeroIdCotizante);
        System.out.println("**__**novedadAporte.determinarClasificacionAfiliado aportes composite inicia  ");
        // se determina cual de ellas es la que corresponde a la presente
        // solicitud de novedad
        for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
            if (clasificacion.getSujetoTramite() != null && clasificacion.getSujetoTramite().equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(clasificacion);
                break;
            }
        }

        // si no se logró determinar la clasificación del afiliado, quiere
        // decir que la persona no está afiliada
        // por tanto se asigna un valor cualquiera correspondiente a su
        // clasificación y se asigna el valor de la
        // marca de novedad como NO_APLICADA
        if (novedadAporte.getClasificacionAfiliado() == null) {
            if (TipoAfiliadoEnum.PENSIONADO.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            }
        }
    }

    /**
     * @param datosProcesoIng
     * @param userDTO
     * @return a
     */
    public Boolean procesarNovedadIngresoAporte(ProcesoNovedadIngresoDTO datosProcesoIng, UserDTO userDTO) {
        String firmaMetodo = "AportesCompositeBusiness.procesarNovedadIngresoAporte(ProcesoNovedadIngresoDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("**__**IniciaprocesarNovedadIngresoAporte caso 3  3333");
        Boolean habilitadoEmpleador = datosProcesoIng.getEsEmpleadorReintegrable();
        Boolean reintegroProcesado = Boolean.FALSE;
        Boolean radicadoIntentoNovedad = false;
        /*
         * Mantis 234253: Se eliminan las validaciones que se hacian llamando los
         * bloques de reintegro (como en afiliaciones) y unicamente se tiene en cuenta
         * el valor que llega desde las validaciones de pila indicando si el aportante
         * es reintegrable o no lo es
         */
 /*
         * si es reintegrable se activa el afiliado tanto el afiliado como el empleador,
         * Null es válido para independientes
         */
        if (datosProcesoIng.getEsReintegrable() != null && datosProcesoIng.getEsReintegrable()) {
            RegistroDetalladoModeloDTO datosAfiliado = null;
            EmpleadorModeloDTO datosEmpleador = null;
            SucursalEmpresaModeloDTO datosSucursal = null;
            ActivacionAfiliadoDTO datosActivacion = new ActivacionAfiliadoDTO();

            // se consulta el registro detallado para lso datos del cotizante,
            // en caso de requerir la creación del registro
            ConsultarRegistroDetalladoPorId consultarRegistroDetalladoPorId = new ConsultarRegistroDetalladoPorId(
                    datosProcesoIng.getIdRegistroDetallado());
            consultarRegistroDetalladoPorId.execute();

            // se consulta la información del empleador (para dependientes)
            if (datosProcesoIng.getTipoIdAportante() != null && datosProcesoIng.getNumeroIdAportante() != null) {
                PersonaModeloDTO personaModeloDTO = null;
                personaModeloDTO = new PersonaModeloDTO();
                personaModeloDTO.setTipoIdentificacion(datosProcesoIng.getTipoIdAportante());
                personaModeloDTO.setNumeroIdentificacion(datosProcesoIng.getNumeroIdAportante());
                VerificarExisteEmpleadorAsociado existeEmpleador = new VerificarExisteEmpleadorAsociado(
                        personaModeloDTO);
                existeEmpleador.execute();
                datosEmpleador = existeEmpleador.getResult();

                // se consulta la información de la sucursal sí aplica, para
                // eso, se debe consultar el registro general del aporte
                ConsultarRegistroGeneralId consultarRegistroGeneralId = new ConsultarRegistroGeneralId(
                        datosProcesoIng.getIdRegistroGeneral());
                consultarRegistroGeneralId.execute();
                RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId.getResult();

                if (registroGeneral != null) {
                    SucursalEmpresa sucursal = consultarSucursalEmpresa(
                            registroGeneral.getCodSucursal() != null ? registroGeneral.getCodSucursal()
                            : registroGeneral.getOutCodSucursalPrincipal(),
                            datosProcesoIng.getTipoIdAportante(), datosProcesoIng.getNumeroIdAportante());

                    if (sucursal != null) {
                        datosSucursal = new SucursalEmpresaModeloDTO();
                        datosSucursal.convertToDTO(sucursal);
                    }
                }

                /*
                 * Con base en la información del Registro General, se evalua la habilitación
                 * respecto al empleador. Es decir, sí se trata de un cotizante dependiente, no
                 * se le puede reintegrar sí el empleador aportante está inactivo y/o no es
                 * reintegrable
                 */
                if ((!TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(datosProcesoIng.getTipoCotizante()))
                        || (!habilitadoEmpleador && registroGeneral != null
                        && EstadoEmpleadorEnum.ACTIVO.equals(registroGeneral.getOutEstadoEmpleador()))) {
                    habilitadoEmpleador = Boolean.TRUE;
                }
            }

            if (habilitadoEmpleador) {
                RolAfiliadoModeloDTO rolAfiliadoDTO = new RolAfiliadoModeloDTO();
                datosAfiliado = consultarRegistroDetalladoPorId.getResult();
                if (datosAfiliado.getFechaIngreso() == null) {
                    datosAfiliado.setFechaIngreso(datosProcesoIng.getFechaIngreso());
                }
                datosActivacion.setDatosAfiliado(datosAfiliado);
                datosActivacion.setEmpleador(datosEmpleador);
                datosActivacion.setSucursal(datosSucursal);
                datosActivacion.setNumeroIdAfiliado(datosProcesoIng.getNumeroIdCotizante());
                datosActivacion.setTipoIdAfiliado(datosProcesoIng.getTipoIdCotizante());
                datosActivacion.setNumeroIdAportante(datosProcesoIng.getNumeroIdAportante());
                datosActivacion.setTipoIdAportante(datosProcesoIng.getTipoIdAportante());
                datosActivacion.setTipoAfiliado(datosProcesoIng.getTipoCotizante());
                datosActivacion.setCanalRecepcion(datosProcesoIng.getCanalRecepcion());

                //activarAfiliado(datosActivacion);
                // se obtiene el estado de afiliación respecto a la caja del
                // afiliado antes de reactivar (para obtener su fecha de retiro)
                ConsultarEstadoAfiliacionRespectoCCF consultaEstado = new ConsultarEstadoAfiliacionRespectoCCF(null,
                        datosProcesoIng.getTipoIdCotizante(), datosProcesoIng.getNumeroIdCotizante());
                consultaEstado.execute();
                ConsultaEstadoAfiliacionDTO estadoAfiliacion = consultaEstado.getResult();

                Date fechaRetiroAfiliado = estadoAfiliacion.getFechaRetiro() != null
                        ? new Date(estadoAfiliacion.getFechaRetiro())
                        : null;

                // Si se cumple la condicion se procede a activar el empleador, sabiendo que la
                // variable
                // es distinta a null solo en el caso de los dependientes
                if (datosEmpleador != null) {
                    ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
                    datosReintegro.setCanalReintegro(datosProcesoIng.getCanalRecepcion());
                    datosReintegro.setFechaReintegro(datosProcesoIng.getFechaIngreso());
                    datosReintegro.setNumIdEmpleador(datosEmpleador.getNumeroIdentificacion());
                    datosReintegro.setTipoIdEmpleador(datosEmpleador.getTipoIdentificacion());
                    datosReintegro.setIdAportante(datosEmpleador.getIdEmpresa());
                    datosReintegro.setIdRegistroGeneral(datosProcesoIng.getIdRegistroGeneral());
                    logger.info("**__**procesarNovedadIngresoAporte-->procesarActivacionEmpleador en caso 3 antes de activar afiliado ");
                    procesarActivacionEmpleador(datosReintegro);
                }
                logger.info("**__**procesarNovedadIngresoAporte caso 3 antes de activar afiliado ");
                /**
                 * movieminto activarAfiliado que se trae de la rutina de
                 * activar afiliado aca por error 07/01/2022
                 */
                RolAfiliado rolAfiliado;
                Long idEmpleadorAportes = null;
                BigInteger idEmpleadorAportesBig = null;
                try {
                    if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(datosActivacion.getTipoAfiliado())) {
                        rolAfiliado = (RolAfiliado) entityManager
                                .createNamedQuery(
                                        NamedQueriesConstants.APORTES_COMPOSITE_BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_DE_ID_DEL_AFI_Y_EMPLEDOR)
                                .setParameter("tipoIdAfiliado", datosActivacion.getTipoIdAfiliado())
                                .setParameter("numeroIdAfiliado", datosActivacion.getNumeroIdAfiliado())
                                .setParameter("tipoIdEmpleador", datosActivacion.getTipoIdAportante())
                                .setParameter("numeroIdEmpleador", datosActivacion.getNumeroIdAportante()).getSingleResult();
                    } else {

                        rolAfiliado = (RolAfiliado) entityManager
                                .createNamedQuery(
                                        NamedQueriesConstants.APORTES_COMPOSITE_BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO)
                                .setParameter("tipoIdentificacion", datosActivacion.getTipoIdAfiliado())
                                .setParameter("numeroIdentificacion", datosActivacion.getNumeroIdAfiliado())
                                .setParameter("tipoAfiliado", datosActivacion.getTipoAfiliado()).getSingleResult();
                    }
                    //id emppleador
                    idEmpleadorAportesBig = (BigInteger) entityManager
                            .createNativeQuery("select r.roaEmpleador from RolAfiliado r where roaId = :IdAfiliado")
                            .setParameter("IdAfiliado", rolAfiliado.getIdRolAfiliado()).getSingleResult();
                    logger.info("**__**idEmpleadorAportes  c " + idEmpleadorAportes);
                    // if(idEmpleadorAportes == null){
                    //     idEmpleadorAportes=75608;
                    // }
                } catch (NoResultException e) {
                    rolAfiliado = null;
                    logger.info("**__**procesarNovedadIngresoAporte NoResultException fa ");
                } catch (Exception e) {
                    logger.error(
                            "****Finaliza operación activarAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum, TipoIdentificacionEnum, String)",
                            e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

                if (rolAfiliado != null) {
                    rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);

                    // AJUSTE MANTIS 0255368
                    rolAfiliado.setFechaAfiliacion(new Date(datosActivacion.getDatosAfiliado().getFechaIngreso()));
                    //rolAfiliado.setFechaAfiliacion(new Date());
                    if (datosActivacion.getDatosAfiliado().getFechaIngreso() != null) {
                        rolAfiliado.setFechaIngreso(new Date(datosActivacion.getDatosAfiliado().getFechaIngreso()));
                    }

                    rolAfiliado.setFechaRetiro(null);
                    rolAfiliado.setMotivoDesafiliacion(null);
                    rolAfiliado.setCanalReingreso(datosActivacion.getCanalRecepcion());

                    if (datosActivacion.getCanalRecepcion() != null && (CanalRecepcionEnum.APORTE_MANUAL.equals(datosActivacion.getCanalRecepcion())
                            || CanalRecepcionEnum.PILA.equals(datosActivacion.getCanalRecepcion()))) {
                        rolAfiliado.setReferenciaAporteReingreso(datosActivacion.getDatosAfiliado().getId());
                    }
                    //posible falla aqui
                    logger.info("**__**procesarNovedadIngresoAporte entityManager.merge(rolAfiliado) ");
                    logger.info("**__**------------------------------------- ");

                    logger.info("**__**------------------RRR------------------- ");
                    // rolAfiliadoDTO.convertToDTO(rolAfiliado, null);

                    //RolAfiliado rolAfiliadoConvert = rolAfiliadoDTO.convertToEntity();
                    logger.info("**__**------------------sss------------------- ");
                    /**
                     * solicitud llamado
                     */
                    AfiliadoInDTO afiliadoInDTO = new AfiliadoInDTO();
                    PersonaDetalle personaDetalle = null;
                    try {
                        personaDetalle = entityManager.createQuery("SELECT pdt FROM PersonaDetalle pdt	WHERE pdt.idPersona IN (SELECT per.id FROM Persona per	WHERE per.tipoIdentificacion =:tipoIdentificacion	AND per.numeroIdentificacion =:numeroIdentificacion)", PersonaDetalle.class)
                                .setParameter("tipoIdentificacion", datosActivacion.getTipoIdAfiliado())
                                .setParameter("numeroIdentificacion", datosActivacion.getNumeroIdAfiliado()).getSingleResult();
                    } catch (NoResultException nre) {
                        personaDetalle = null;
                    }
                    // Se asigna la persona dto
                    //inAfiliadoDTO.getPersona().getNumeroIdentificacion()
                    // PersonaDTO personaDTO = new PersonaDTO().convertPersonaToDTO(
                    //         rolAfiliado.getAfiliado().getPersona(), personaDetalle);
                    // afiliadoInDTO.setPersona(personaDTO);
                    afiliadoInDTO.setCanalRecepcion(CanalRecepcionEnum.PILA);
                    // afiliadoInDTO.setIdAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getAfiliado().getIdAfiliado());
                    //afiliadoInDTO.setIdEmpleador(solicitudAfiliacionPersona.getRolAfiliado().getEmpleador().getIdEmpleador());
                    afiliadoInDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
                    afiliadoInDTO.setValorSalarioMesada(datosAfiliado.getSalarioBasico());
                    Long idSolicitud = new Long(0);
                    /**
                     * metodo aparte inicia
                     */
                    logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getNumeroIdentificacion:" + datosActivacion.getNumeroIdAfiliado());
                    logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getTipoIdentificacion:" + datosActivacion.getTipoIdAfiliado());
                    Query qPersona = entityManager.createQuery("SELECT per	FROM Persona per where per.numeroIdentificacion= :numIdentificacion	and per.tipoIdentificacion= :tipoIdentificacion");
                    qPersona.setParameter("numIdentificacion", datosActivacion.getNumeroIdAfiliado());
                    qPersona.setParameter("tipoIdentificacion", datosActivacion.getTipoIdAfiliado());

                    List<Persona> listaPersona = qPersona.getResultList();
                    // Verificacion de que exista la persona
                    //if (listaPersona.size() > 0) {
                    Solicitud solicitud = new Solicitud();
                    // Por especificacion se setea el campo estadoDocumento en null
                    solicitud.setEstadoDocumentacion(null);
                    solicitud.setTipoTransaccion(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
                    solicitud.setCanalRecepcion(afiliadoInDTO.getCanalRecepcion());
                    solicitud.setFechaCreacion(new Date());
                    solicitud.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
                    /* Se agrega validacion para el proceso 122 CargueMultiple */
                    if (afiliadoInDTO.getCodigoCargueMultiple() != null) {
                        solicitud.setCargaAfiliacionMultiple(afiliadoInDTO.getCodigoCargueMultiple());
                    }
                    //entityManager.persist(solicitud);
                    // Consultar rol Afiliado por Id
                    Query qRolAfiliacion = entityManager.createNamedQuery("SELECT rolA FROM RolAfiliado rolA join fetch rolA.afiliado left join fetch	rolA.empleador WHERE rolA.idRolAfiliado= :idRolAfiliado");
                    qRolAfiliacion.setParameter("idRolAfiliado", afiliadoInDTO.getIdRolAfiliado());
                    RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();

                    // Creacion de la solicitudAfiliacionPersona
                    SolicitudAfiliacionPersona solAfiLliacion = new SolicitudAfiliacionPersona();
                    solAfiLliacion.setSolicitudGlobal(solicitud);
                    solAfiLliacion.setRolAfiliado(rolAfiliadoResult);
                    /*
			 * Por especificacion se setea el estado de la solicitud en
			 * PRE_RADICADA
                     */
                    solAfiLliacion.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
                    entityManager.persist(solAfiLliacion);
                    logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP FIN solicitud.getIdSolicitud(): " + solicitud.getIdSolicitud());
                    //	return solicitud.getIdSolicitud();

                    /**
                     * metodo aparte fin
                     */
                    //    idSolicitud = (Long) solicitud.getIdSolicitud();
                    //    if(idSolicitud != null){
                    //          RadicarSolicitudAbreviadaDTO radicarSolicitudAbreviada = new RadicarSolicitudAbreviadaDTO();
                    //        radicarSolicitudAbreviada.setIdSolicitudGlobal(idSolicitud);
                    //        radicarSolicitudAbreviada.setCanal(CanalRecepcionEnum.PILA);
                    //        radicarSolicitudAbreviada.setTipoRadicacion(TipoRadicacionEnum.COMPLETA);
                    //        radicarSolicitudAbreviada.setValorSalarioMesada(datosAfiliado.getSalarioBasico());
                    //        radicarSolicitudAbreviada.setTipoSolicitante(datosProcesoIng.getTipoCotizante());
                    //        radicarSolicitudAbreviada.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
                    //        radicarSolicitudAbreviada.setRegistrarIntentoAfiliacion(false);
                    //     System.out.println(" **__**iniciaAAAAAAAAAA radicarSolicitudAbreviadaAfiliacionPersonaAfiliados");
                    //     try{
                    //     System.out.println(" **__**try inicial radicarSolicitudAbreviadaAfiliacionPersonaAfiliados");
                    //consultasCore.radicarSolicitudAbreviadaAfiliacionPersonaAfiliados(radicarSolicitudAbreviada );
                    //     }catch(Exception e){
                    //    System.out.println(" **__**catch antes de radicarSolicitudAbreviadaAfiliacionPersonaAfiliados error:" +e);
                    //     }
                    //    
                    //           
                    //    }
                    /**
                     * fin solicitud llamado
                     */
                    // entityManager.merge(rolAfiliado); 
                    logger.info("**__**FinalizaprocesarNovedadIngresoAporte entityManager.merge(rolAfiliado) ");
                    //Si previamente el empleador estuvo en bandeja de 0 trabajadores y se gestinó MANTENER AFILICIÓN
                    //se deshace dicha gestión para que no se inactive posteiormente de manera automática
                    // logger.info("**__** rolAfiliado.getEmpleador()"+rolAfiliado.getEmpleador());
                    //  if (rolAfiliado.getEmpleador() != null) {
                    if (idEmpleadorAportesBig != null) {
                        logger.info("**__** ingresa DeshacerGestionCeroTrabajadoresRutine idEmpleadorAportesBig" + idEmpleadorAportesBig);
                        DeshacerGestionCeroTrabajadoresRutine d = new DeshacerGestionCeroTrabajadoresRutine();
                        //rolAfiliado.getEmpleador().getIdEmpleador()
                        idEmpleadorAportes = idEmpleadorAportesBig.longValue();
                        logger.info("**__** ingresa DeshacerGestionCeroTrabajadoresRutine idEmpleadorAportes" + idEmpleadorAportes);
                        d.deshacerGestionCeroTrabajadores(idEmpleadorAportes, entityManager);
                        //  deshacerGestionCeroTrabajadores(rolAfiliado.getEmpleador().getIdEmpleador(),entityManager);
                    }
                } else { //posible error
                    logger.info("**__**procesarNovedadIngresoAporte inicia  entityManager.persist(rolAfiliado)");
                    ActivarAfiliadoRutine a = new ActivarAfiliadoRutine();
                    //a.prepararNuevoRolAfiliadoReingresoAportes(datosActivacion, entityManager);
                    // rolAfiliado = prepararNuevoRolAfiliadoReingresoAportes(datosActivacion, entityManager);
                    rolAfiliado = a.prepararNuevoRolAfiliadoReingresoAportes(datosActivacion, entityManager);
                    rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                    //rolAfiliadoDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                    // rolAfiliadoDTO.convertToDTO(rolAfiliado, null);
                    // RolAfiliado rolAfiliadoConvert = rolAfiliadoDTO.convertToEntity();
                    try {
                        logger.info("**__**try de persist rolafiliado aportes composite inicia   consultasCore.updateRolAfiliadoAportes ");
                        consultasCore.updateRolAfiliadoAportes(rolAfiliado, false);
                        logger.info("**__**try de persist rolafiliado aportes composite finaliza  consultasCore.updateRolAfiliadoAportes");
                    } catch (Exception e) {
                        logger.info("**__**catch de persist rolafiliado aportes composite inicia  vvrolAfiliadoConvert");
                        entityManager.persist(rolAfiliado);
                        logger.info("**__**catch dell persist rolafiliado aportes composite Finaliza  vvrolAfiliadoConvert ");

                    }
                }
                logger.info("**__** datosProcesoIng.getCanalRecepcion()BBBBB " + datosProcesoIng.getCanalRecepcion());
                logger.info("**__** idRolAfiliado BBBB:  " + rolAfiliado.getIdRolAfiliado());
                logger.info("**__** datosProcesoIng.getTipoCotizante() BBBB:  " + datosProcesoIng.getTipoCotizante());
                logger.info("**__** getNumeroIdentificacionCotizante()) BBBB:  " + datosAfiliado.getNumeroIdentificacionCotizante());
                logger.info("**__** datosEmpleador.getNumeroIdentificacion()) BBBB:  " + datosEmpleador.getNumeroIdentificacion());
                logger.info("**__** valorSalarioMesada: :  " + datosAfiliado.getSalarioBasico());

                logger.info(
                        "****___Finaliza rutina activarAfiliado(TipoIdentificacionEnum, String, TipoAfiliadoEnum, TipoIdentificacionEnum, String)");
                logger.info("**__**HACER AJUSTE PARA REINTEGROS AQUI");
                /**
                 * FIN movieminto activarAfiliado que se trae de la rutina de
                 * activar afiliado aca por error 07/01/2022
                 */
                // activarAfiliado(datosActivacion);

                // después de activar al afiliado, se evalua el reintegro de sus
                // grupos familiares
                if (datosAfiliado.getOutGrupoFamiliarReintegrable()) {

                    String numeroIdEmpleador = null;
                    TipoIdentificacionEnum tipoIdEmpleador = null;

                    Date fechaIngresoAfiliado = datosProcesoIng.getFechaIngreso() != null
                            ? new Date(datosProcesoIng.getFechaIngreso())
                            : null;

                    if (datosEmpleador != null) {
                        numeroIdEmpleador = datosEmpleador.getNumeroIdentificacion();
                        tipoIdEmpleador = datosEmpleador.getTipoIdentificacion();
                    }
                    reintegrarGrupoFamiliar(datosProcesoIng.getTipoIdCotizante(),
                            datosProcesoIng.getNumeroIdCotizante(), fechaRetiroAfiliado, fechaIngresoAfiliado,
                            datosProcesoIng.getTipoCotizante(), tipoIdEmpleador, numeroIdEmpleador, userDTO);
                }

                // se solicita la activación de la cuenta del afiliado
                activarCuentaPersona(datosProcesoIng.getTipoIdCotizante(), datosProcesoIng.getNumeroIdCotizante());
                reintegroProcesado = Boolean.TRUE;
            }
        }
        /*
         * si las validaciones no se aprueban se crea un intento de afiliación.
         */
        if (!reintegroProcesado) {
            radicadoIntentoNovedad = procesarIntentoNovedad(datosProcesoIng.getTipoCotizante(), datosProcesoIng.getTipoIdAportante(),
                    datosProcesoIng.getNumeroIdAportante(), datosProcesoIng.getTipoIdCotizante(),
                    datosProcesoIng.getNumeroIdCotizante(), datosProcesoIng.getCanalRecepcion(),
                    datosProcesoIng.getIdRegistroDetallado(), datosProcesoIng.getTenNovedadId(),null);
        }
        logger.info("**__**Fin procesarNovedadIngresoAporte caso 3  ");
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return radicadoIntentoNovedad;
    }

    /**
     * Método encargado de hacer el llamado al microservicio que consulta las
     * clasificaciones de un afiliado
     *
     * @param tipoId el tipo de identificacion del afiliado
     * @param numeroId el numero de identificacion del afiliado
     *
     * @return List<ClasificacionEnum> con las clasificaciones encontradas
     */
    public Long crearSolicitudAfiliacionPersonaAportesC(AfiliadoInDTO inAfiliadoDTO) {
        // Busqueda de la persona el numero de identificación y el tipo de
        // identificación
        logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getNumeroIdentificacion:" + inAfiliadoDTO.getPersona().getNumeroIdentificacion());
        logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP getTipoIdentificacion:" + inAfiliadoDTO.getPersona().getTipoIdentificacion());
        Query qPersona = entityManager.createQuery("SELECT per	FROM Persona per where per.numeroIdentificacion= :numIdentificacion	and per.tipoIdentificacion= :tipoIdentificacion");
        qPersona.setParameter("numIdentificacion", inAfiliadoDTO.getPersona().getNumeroIdentificacion());
        qPersona.setParameter("tipoIdentificacion", inAfiliadoDTO.getPersona().getTipoIdentificacion());
        List<Persona> listaPersona = qPersona.getResultList();
        // Verificacion de que exista la persona
        if (listaPersona.size() > 0) {
            Solicitud solicitud = new Solicitud();
            // Por especificacion se setea el campo estadoDocumento en null
            solicitud.setEstadoDocumentacion(null);
            solicitud.setTipoTransaccion(inAfiliadoDTO.getPersona().getTipoTransaccion());
            solicitud.setCanalRecepcion(inAfiliadoDTO.getCanalRecepcion());
            solicitud.setFechaCreacion(new Date());
            solicitud.setClasificacion(inAfiliadoDTO.getPersona().getClasificacion());
            /* Se agrega validacion para el proceso 122 CargueMultiple */
            if (inAfiliadoDTO.getCodigoCargueMultiple() != null) {
                solicitud.setCargaAfiliacionMultiple(inAfiliadoDTO.getCodigoCargueMultiple());
            }
            entityManager.persist(solicitud);
            // Consultar rol Afiliado por Id
            Query qRolAfiliacion = entityManager.createNamedQuery("SELECT rolA FROM RolAfiliado rolA join fetch rolA.afiliado left join fetch	rolA.empleador WHERE rolA.idRolAfiliado= :idRolAfiliado");
            qRolAfiliacion.setParameter("idRolAfiliado", inAfiliadoDTO.getIdRolAfiliado());
            RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();

            // Creacion de la solicitudAfiliacionPersona
            SolicitudAfiliacionPersona solAfiLliacion = new SolicitudAfiliacionPersona();
            solAfiLliacion.setSolicitudGlobal(solicitud);
            solAfiLliacion.setRolAfiliado(rolAfiliadoResult);
            /*
			 * Por especificacion se setea el estado de la solicitud en
			 * PRE_RADICADA
             */
            solAfiLliacion.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
            entityManager.persist(solAfiLliacion);
            logger.info("**__**-Inicia crearSolicitudAfiliacionPersonaPPPPPPPPP FIN solicitud.getIdSolicitud(): " + solicitud.getIdSolicitud());
            return solicitud.getIdSolicitud();
        } else {
            // Persona no encontrada
            return null;
        }
    }

    private List<ClasificacionEnum> consultarClasificacionesAfiliado(TipoIdentificacionEnum tipoId, String numeroId) {
        try {
            ConsultarClasificacionesAfiliado consultarClasificacionAfiliado = new ConsultarClasificacionesAfiliado(
                    numeroId, tipoId);
            consultarClasificacionAfiliado.execute();
            return consultarClasificacionAfiliado.getResult();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Método encargado de hacer el llamado al microservicio que consulta la
     * sucursal de empresa con su código y el id de empresa.
     *
     * @param codigoSucursal es el codigo que identifica la sucursal para la
     * empresa
     *
     * @param idEmpresa el identificador de la empresa
     *
     * @return SucursalEmpresa con los datos de la sucursal
     */
    private SucursalEmpresa consultarSucursalEmpresa(String codigoSucursal, TipoIdentificacionEnum tipoIdAportante,
            String numeroIdAportante) {

        ObtenerSucursalEmpresa obtenerSucursalEmpresa = new ObtenerSucursalEmpresa(codigoSucursal, numeroIdAportante,
                tipoIdAportante);
        obtenerSucursalEmpresa.execute();
        return obtenerSucursalEmpresa.getResult() != null ? obtenerSucursalEmpresa.getResult().convertToEntity() : null;
    }

    /**
     * @param datosReintegro
     * @return Boolean
     */
    public Boolean procesarActivacionEmpleador(ActivacionEmpleadorDTO datosReintegro) {
        String firmaMetodo = "procesarActivacionEmpleador(ActivacionEmpleadorDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean reintegrado = Boolean.FALSE;

        try {
            logger.info("**___**try procesarActivacionEmpleador");
            actualizarEstadoEmpleador(datosReintegro);

            // Se crea la nueva solicitud de afiliacion para el empleador
            CrearSolicitudAfiliacionEmpleadorAportes solicitud = 
                new CrearSolicitudAfiliacionEmpleadorAportes(datosReintegro);

            solicitud.execute();


            activarCuentaPersona(datosReintegro.getTipoIdEmpleador(), datosReintegro.getNumIdEmpleador());
            reintegrado = Boolean.TRUE;
        } catch (Exception e) {
            logger.info("**___**catch procesarActivacionEmpleador--");
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return reintegrado;
    }

    /**
     * @param tipoIdCotizante
     * @param numeroIdCotizante
     * @param fechaRetiroAfiliado
     * @param fechaIngresoAfiliado
     * @param tipoCotizante
     * @param tipoIdEmpleador
     * @param numeroIdEmpleador
     * @param userDTO
     */
    public void reintegrarGrupoFamiliar(TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante,
            Date fechaRetiroAfiliado, Date fechaIngresoAfiliado, TipoAfiliadoEnum tipoCotizante,
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, UserDTO userDTO) {
        String firmaMetodo = "AportesCompositeBusiness.reintegrarGrupoFamiliar(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Se verifica si es posible hacer el reintegro de acuerdo a la parametrización
        // respectiva
        Long tiempoReintegro = CalendarUtils
                .toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_REINTEGRO_GF));

        Long difereciaFechas = new Long(0L);

        if (fechaRetiroAfiliado != null) {
            difereciaFechas = new Date().getTime() - fechaRetiroAfiliado.getTime();
        }

        if (difereciaFechas > tiempoReintegro) {
            return;
        }

        // se obtiene el ID de afiliado
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(numeroIdCotizante, tipoIdCotizante);
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();

        if (fechaRetiroAfiliado != null && afiliado != null) {
            // se consultan los beneficiarios asociados al afiliado
            ConsultarBeneficiarios consultaBeneficiarios = new ConsultarBeneficiarios(afiliado.getIdAfiliado(), false);
            consultaBeneficiarios.execute();
            List<BeneficiarioDTO> beneficiarios = consultaBeneficiarios.getResult();

            if (beneficiarios != null && !beneficiarios.isEmpty()) {
                // se compara la fecha de retiro del beneficiario con la fecha
                // de retiro del afiliado principal
                LocalDate retiroAfiliado = fechaRetiroAfiliado != null
                        ? fechaRetiroAfiliado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : null;
                for (BeneficiarioDTO beneficiario : beneficiarios) {
                    LocalDate retiroBeneficiario = beneficiario.getFechaRetiro() != null
                            ? beneficiario.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            : null;

                    // sí las fechas de retiro coinciden y pasa la maya de
                    // validación, se sólicita la actualización del beneficiario
                    if (retiroAfiliado != null && retiroBeneficiario != null
                            && retiroBeneficiario.isEqual(retiroAfiliado)
                            && ejecutarMayaValidacionBeneficiario(beneficiario, tipoIdCotizante, numeroIdCotizante,
                                    tipoIdEmpleador, numeroIdEmpleador)) {

                        beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
                        beneficiario.setFechaRetiro(null);
                        beneficiario.setMotivoDesafiliacion(null);
                        beneficiario.setFechaAfiliacion(fechaIngresoAfiliado);

                        String mensajeNovedad = "Activacion de beneficiarios (PILA)";

                        NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
                        novedadAporte.setAplicar(MarcaNovedadEnum.APLICADA);
                        determinarClasificacionAfiliado(tipoCotizante, tipoIdCotizante, numeroIdCotizante,
                                novedadAporte);
                        novedadAporte.setComentarios(mensajeNovedad);
                        novedadAporte.setTipoNovedad(this.calcularTipoTransaccion(beneficiario));
                        novedadAporte.setNumeroIdentificacion(numeroIdCotizante);
                        novedadAporte.setTipoIdentificacion(tipoIdCotizante);
                        novedadAporte.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
                        novedadAporte.setBeneficiario(beneficiario);
                        logger.info("tipoCotizante: " + tipoCotizante);
                        if (tipoCotizante.equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)) {
                            logger.info("tipoIdEmpleador: " + tipoIdEmpleador);
                            logger.info("numeroIdEmpleador: " + numeroIdEmpleador);
                            novedadAporte.setTipoIdentificacionAportante(tipoIdEmpleador);
                            novedadAporte.setNumeroIdentificacionAportante(numeroIdEmpleador);
                        }

                        /*
                         * ProcesarActivacionBeneficiarioPILA activacionSrv = new
                         * ProcesarActivacionBeneficiarioPILA(novedadAporte); activacionSrv.execute();
                         */
                        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
                        p.procesarActivacionBeneficiarioPILA(novedadAporte, userDTO, entityManager);
                    }
                }
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private TipoTransaccionEnum calcularTipoTransaccion(BeneficiarioDTO beneficiario) {
        TipoTransaccionEnum bloque = null;
        switch (beneficiario.getTipoBeneficiario()) {
            case BENEFICIARIO_EN_CUSTODIA:
                bloque = TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL;
                break;
            case CONYUGE:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
                break;
            case HERMANO_HUERFANO_DE_PADRES:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL;
                break;
            case HIJASTRO:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
                break;
            case HIJO_ADOPTIVO:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL;
                break;
            case HIJO_BIOLOGICO:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
                break;
            case MADRE:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL;
                break;
            case PADRE:
                bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL;
                break;
            default:
                break;

        }
        return bloque;
    }

    /**
     * Método para reactivar la cuenta web de un empelador a partie de su ID
     */
    private void activarCuentaPersona(TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "AportesCompositeBusiness.activarCuentaPersona(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoId != null && numId != null) {
            UsuarioCCF usuario = buscarUsuario(tipoId, numId);
            if (usuario != null) {
                usuario.setUsuarioActivo(Boolean.TRUE);
                usuario.setReintegro(Boolean.TRUE);

                ActualizarUsuarioCCF actualizarUsuarioCCF = new ActualizarUsuarioCCF(usuario);
                actualizarUsuarioCCF.execute();
            }
        } else {
            TechnicalException e = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO
                    + " - No se cuenta con el tipo y número de identificación del usuario a activar");
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw e;
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void actualizarEstadoEmpleador(ActivacionEmpleadorDTO datosReintegro) {
        
        ActualizarEstadoEmpleadorPorAportes actualizarEstadoEmpleador;
        actualizarEstadoEmpleador = new ActualizarEstadoEmpleadorPorAportes(datosReintegro);
        actualizarEstadoEmpleador.execute(); 
        
        /*
        logger.info("**__** actualizarEstadoEmpleador a acontinuacion rutina");
        try {
            logger.info("**__** try AportesCompositeactualizarEstadoEmpleadorPorAportes");
            consultasCore.AportesCompositeactualizarEstadoEmpleadorPorAportes(datosReintegro);
            logger.info("**__** FINN try AportesCompositeactualizarEstadoEmpleadorPorAportes");
        } catch (Exception e) {
            logger.info("**__** catch  actualizarEstadoEmpleador iniciara rutina ActualizarEstadoEmpleadorPorAportesRutine error  " + e);
            ActualizarEstadoEmpleadorPorAportesRutine actualizarEstadoEmpleadorPorAportesRutine = new ActualizarEstadoEmpleadorPorAportesRutine();
            actualizarEstadoEmpleadorPorAportesRutine.actualizarEstadoEmpleadorPorAportes(datosReintegro, entityManager);
        }

         */

    }

    /**
     * Método encargado de ejecutar la maya de validaciones para el reintegro de
     * un beneficiario
     *
     * @param beneficiario
     * @param tipoIdAfiliado
     * @param numIdAfiliado
     * @return
     */
    private Boolean ejecutarMayaValidacionBeneficiario(BeneficiarioDTO beneficiario,
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        String firmaMetodo = "AportesCompositeBusiness.ejecutarMayaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoTransaccionEnum bloque = calcularTipoTransaccion(beneficiario);

        if (bloque == null) {
            return Boolean.FALSE;
        }

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_TRANSACCION, bloque.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION, tipoIdAfiliado.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION, numIdAfiliado);
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICA_BENEFI,
                beneficiario.getPersona().getTipoIdentificacion().name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICA_BENEFI,
                beneficiario.getPersona().getNumeroIdentificacion());
        datosValidacion.put(ConstantesMayaValidacion.KEY_PRIMER_NOMBRE, beneficiario.getPersona().getPrimerNombre());
        datosValidacion.put(ConstantesMayaValidacion.KEY_SEGUNDO_NOMBRE, beneficiario.getPersona().getSegundoNombre());
        datosValidacion.put(ConstantesMayaValidacion.KEY_PRIMER_APELLIDO,
                beneficiario.getPersona().getPrimerApellido());
        datosValidacion.put(ConstantesMayaValidacion.KEY_SEGUNDO_APELLIDO,
                beneficiario.getPersona().getSegundoApellido());
        datosValidacion.put(ConstantesMayaValidacion.KEY_FECHA_NACIMIENTO,
                beneficiario.getPersona().getFechaNacimiento().toString());
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION_AFILIADO, tipoIdAfiliado.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION_AFILIADO, numIdAfiliado);
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION_EMPLEADOR_PARAM, tipoIdEmpleador.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION_EMPLEADOR_PARAM, numeroIdEmpleador);

        ValidarReglasNegocio validador = new ValidarReglasNegocio(bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA), bloque.getProceso(),
                beneficiario.getTipoBeneficiario().name(), datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {

                logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: El beneficiario "
                        + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                        + beneficiario.getPersona().getNumeroIdentificacion() + ", no aprobó la maya de validación - "
                        + validacion.getDetalle());
                return Boolean.FALSE;
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }

    private UsuarioCCF buscarUsuario(TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "AportesCompositeBusiness.buscarUsuario(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        UsuarioCCF usuario = null;

        ConsultarUsuarios consultarUsuarios = new ConsultarUsuarios();
        consultarUsuarios.execute();
        List<UsuarioDTO> usuarios = consultarUsuarios.getResult();

        String userName = tipoId.name().toLowerCase() + "_" + numId;

        if (usuarios != null) {
            for (UsuarioDTO usuarioDTO : usuarios) {
                if (usuarioDTO.getNombreUsuario().equalsIgnoreCase(userName)
                        || (tipoId.equals(usuarioDTO.getTipoIdentificacion())
                        && numId.equalsIgnoreCase(usuarioDTO.getNumIdentificacion()))) {
                    usuario = new UsuarioCCF(usuarioDTO);
                }
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return usuario;
    }

    /**
     * @param datosReintegro
     * @return Boolean
     */
    public Long crearAfiliado(String nombreAfiliado, String ti, String ni) {
        String firmaMetodo = "crearAfiliado(nombreAfiliado)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Persona p = new Persona();
        p.setPrimerNombre(nombreAfiliado);
        p.setPrimerApellido("Prueba");
        p.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(ti));
        p.setNumeroIdentificacion(ni);

        entityManager.persist(p);

        Afiliado a = new Afiliado();
        a.setPersona(p);

        entityManager.persist(a);

        RolAfiliado roa = new RolAfiliado();
        roa.setAfiliado(a);
        roa.setFechaAfiliacion(new Date());
        roa.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);

        entityManager.persist(roa);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return roa.getIdRolAfiliado();
    }
    
    @Override
    @Asynchronous
    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void liberarPlanillasBloque9Process() {
        logger.info("Inicio de método liberacion-Bloque 9.run(Timer timer)");
        List<Object[]> idPlanillas = new ArrayList<>();
        
        List<Long> idPlanillasNovedades = new ArrayList<>();
        List<Long> idPlanillasSinNovedades = new ArrayList<>();
        List<EstadoProcesoArchivoEnum> estadosPlanilla = new ArrayList<>();
        estadosPlanilla.add(EstadoProcesoArchivoEnum.PROCESADO_SIN_NOVEDADES);
        estadosPlanilla.add(EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES);
        int procesadoresDisponibles = Runtime.getRuntime().availableProcessors();
        logger.info("liberarPlanillasBloque9Process procesadoresDisponibles: "+procesadoresDisponibles+" Hilos: "+java.lang.Thread.activeCount());
        StoredProcedureQuery query;
        //for (EstadoProcesoArchivoEnum estadoPlanilla : estadosPlanilla) {
            try {
                /* 
                Query q = entityManagerPila.createNamedQuery(NamedQueriesConstants.PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_LIBERACION_BLOQUE9);
                idPlanillas = q.getResultList();
                */
                 query = entityManagerPila.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_PROCESSSCHEDULE_PARAMEJECUCIONPROGRAMADA_CONSULTAR_PLANILLAS_BLOQUE9);
               // query.setParameter("estadoPlanilla", estadoPlanilla.name());
                query.execute();
                idPlanillas = query.getResultList();
                query=null;
               // query.notify();
            } catch (NoResultException e) {
            logger.info("liberarPlanillasBloque9Process -> NoResultException");
                idPlanillas = null;
            }
        //}
                if(idPlanillas !=null || !idPlanillas.isEmpty()) {
                    for (Object[] idPlanilla : idPlanillas) {
                        if (idPlanilla[0] != null) {
                            idPlanillasNovedades.add(Long.valueOf(idPlanilla[0].toString()));
                        }
                        if (idPlanilla[1] != null) {
                            idPlanillasSinNovedades.add(Long.valueOf(idPlanilla[1].toString()));
                        }
                    }
                }
            logger.info("Consulta de idPlanillas realizada, Inicia tareas paralelas");
             if (!idPlanillasSinNovedades.isEmpty() ){
                List<Callable<Void>> tareasParalelas = new LinkedList<>();
                 logger.info("Inicia procesarPlanillasSinNovedadesASYNC"+idPlanillasSinNovedades.size());
                for (Long id : idPlanillasSinNovedades) {
                    Callable<Void> parallelTask = () -> {
                        try {
                            logger.info("Inicia procesarPlanillasSinNovedadesASYNC: "+id.longValue());
                            procesarPlanillasSinNovedadesASYNC(id.longValue());
                        } catch (Exception e) {
                            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + "Planillaproceso: " + id.longValue() + "liberarPlanillasBloque9Process " + e.getMessage());
                        }
                        return null;
                    };
                    tareasParalelas.add(parallelTask);
                }
                try {
                    managedExecutorService.invokeAll(tareasParalelas);
                } catch (InterruptedException e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + " liberarPlanillasBloque9Process: ", e);
                    e.printStackTrace();
                }
                logger.info("Finaliza  ProcesarAportesNovedadesByIdPlanilla Asyn PROCESADO_SIN_NOVEDADES");
            }
             if (!idPlanillasNovedades.isEmpty()) {
                List<Callable<Void>> tareasParalelas = new LinkedList<>();
                for (Long id : idPlanillasNovedades) {

                    Callable<Void> parallelTask = () -> {
                        try {
                            ProcesarAportesNovedadesByIdPlanilla procesarPlanilla = new ProcesarAportesNovedadesByIdPlanilla(id.longValue());
                            procesarPlanilla.execute();
                        } catch (Exception e) {
                            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + "Planillaproceso: " + id.longValue() + "liberarPlanillasBloque9Process " + e.getMessage());
                        }
                        return null;
                    };
                    tareasParalelas.add(parallelTask);
                }
                try {
                    managedExecutorService.invokeAll(tareasParalelas);
                } catch (InterruptedException e) {
                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + " liberarPlanillasBloque9Process: ", e);
                    e.printStackTrace();
                }
                logger.info("Finaliza  ProcesarAportesNovedadesByIdPlanilla Asyn PROCESADO_NOVEDADES");
            }else {
                logger.info("No hay planillas vigentes para pasar- bloue 9 servicio");
            }
        
        logger.info(" Finaliza ProcessSchedule - liberarPlanillasBloque9 End");
    }

}
