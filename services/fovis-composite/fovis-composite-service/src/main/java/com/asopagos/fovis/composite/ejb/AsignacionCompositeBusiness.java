package com.asopagos.fovis.composite.ejb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;

import com.asopagos.afiliaciones.clients.ObtenerNumeroRadicadoCorrespondencia;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.ConsultarAfiliado;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ConvertHTMLtoPDF;
import com.asopagos.archivos.clients.EliminarArchivo;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.comunicados.clients.ResolverPlantillaVariablesComunicadoPorSolicitud;
import com.asopagos.consola.ejecucion.proceso.asincrono.clients.ActualizarEjecucionProcesoAsincrono;
import com.asopagos.consola.ejecucion.proceso.asincrono.clients.ConsultarUltimaEjecucionAsincrona;
import com.asopagos.consola.ejecucion.proceso.asincrono.clients.ConsultarUltimaEjecucionProcesoAsincrono;
import com.asopagos.consola.ejecucion.proceso.asincrono.clients.RegistrarEjecucionProcesoAsincrono;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.*;
import com.asopagos.dto.fovis.EjecucionAsignacionDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.fovis.ResultadoAsignacionDTO;
import com.asopagos.dto.fovis.ResumenAsignacionDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.*;
import com.asopagos.enumeraciones.fovis.*;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.*;
import com.asopagos.fovis.composite.clients.ConsultarPostulacionTemporal;
import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import com.asopagos.fovis.composite.service.AsignacionCompositeService;
import com.asopagos.fovis.composite.service.FovisCompositeService;
import com.asopagos.fovis.dto.AsignacionTurnosDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.ConsultarJefeHogar;
import com.asopagos.personas.clients.ConsultarListaIntegranteHogar;
import com.asopagos.personas.clients.ConsultarPersonaDetalle;
import com.asopagos.personas.clients.ConsultarPersonaJefeHogar;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.Interpolator;
import com.asopagos.util.RangoTopeUtils;
import com.asopagos.utilities.formulador.RulePerformer;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.validaciones.fovis.clients.ValidarReglasNegocioFovis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;



import com.asopagos.fovis.composite.constants.NamedQueriesConstants;

import java.util.Set;

/**
 * <b>Descripción: </b> EJB que representa la lógica de negocio relacionada al
 * proceso FOVIS 3.2.3 <br/>
 * <b>Historia de Usuario: </b> HU-047, HU-048
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 * Benavides</a>
 */
@Stateless
public class AsignacionCompositeBusiness implements AsignacionCompositeService {

    /**
     * Instancia del gestor de registro de eventos
     */
    private static final ILogger logger = LogManager.getLogger(AsignacionCompositeBusiness.class);

    /**
     * Constante con el nombre de variable "puntajeR" para evaluación
     * de fórmula dinámica
     */
    private static final String PUNTAJE_R = "puntajeR";

    /**
     * Constante con el nombre de variable "B1" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B1 = "B1";

    /**
     * Constante con el nombre de variable "B2" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B2 = "B2";

    /**
     * Constante con el nombre de variable "B3" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B3 = "B3";

    /**
     * Constante con el nombre de variable "B4" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B4 = "B4";

    /**
     * Constante con el nombre de variable "B5" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B5 = "B5";

    /**
     * Constante con el nombre de variable "B6" para evaluación de fórmula
     * dinámica
     */
    private static final String STRING_B6 = "B6";

    /**
     * Constante con el nombre de variable "puntaje" para evaluación de fórmula
     * dinámica
     */
    private static final String PUNTAJE = "puntaje";

    /**
     * Constante con el nombre de variable "SFVCalculado" para evaluación de fórmula
     * dinámica
     */
    private static final String SFV_CALCULADO = "SFVCalculado";

    /**
     * Constante con el nombre de variable "SFVSolicitado" para evaluación de fórmula
     * dinámica
     */
    private static final String SFV_SOLICITADO = "SFVSolicitado";

    /**
     * Constante con el nombre de variable "totalIngresosHogar" para evaluación
     * de fórmula dinámica
     */
    private static final String TOTAL_INGRESOS_HOGAR = "totalIngresosHogar";

    /**
     * Constante con el nombre de variable "totalRecursosHogar" para la evaluación de la fórmula dinámica
     */
    private static final String TOTAL_RECURSOS_HOGAR = "totalRecursosHogar";

    /**
     * Constante con el nombre de variable "totalAhorroPrevio" para la evaluación de la fórmula dinámica
     */
    private static final String TOTAL_AHORRO_PREVIO = "totalAhorroPrevio";

    /**
     * Constante que define el nombre del bloque de validacion de la HU323-045
     */
    private static final String BLOQUE_VALIDACION_323_045 = "323-045-1";
    /**
     * Constante con la clave para el número de identificación.
     */
    private static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
    /**
     * Constante con la clave para el tipo de identificación.
     */
    private static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    /**
     * Constante con la clave para el primer apellido.
     */
    private static final String PRIMER_APELLIDO = "primerApellido";
    /**
     * Constante con la clave para la fecha de nacimiento.
     */
    private static final String FECHA_NACIMIENTO = "fechaNacimiento";
    /**
     * Constante con la clave para el numero de identificacion del afiliado
     */
    private static final String NUMERO_IDENTIFICACION_AFILIADO = "numeroIdentificacionAfiliado";
    /**
     * Constante con la clave para el tipo de identificacion del afiliado
     */
    private static final String TIPO_IDENTIFICACION_AFILIADO = "tipoIdentificacionAfiliado";
    /**
     * Constante con la clave para el segundo apellido
     */
    private static final String SEGUNDO_APELLIDO = "segundoApellido";
    /**
     * Constante con la clace para el tipo de beneficiario
     */
    private static final String TIPO_BENEFICIARIO = "tipoBeneficiario";
    /**
     * Constante que indica el resultado de la aprobacion de la solicitud de asignacion
     */
    public static final String RESULTADO_ASIGNACION_CONTROL_INTERNO = "resultadoAsignacionControlInterno";
    /**
     * Constante con el nombre del parámetro BPM que indica número de radicado
     */
    public static final String NUMERO_RADICADO = "numeroRadicado";
    /**
     * Constante con el nombre del parámetro BPM que indica el usuario de control interno
     */
    public static final String USUARIO_CONTROL_INTERNO = "usuarioControlInterno";
    /**
     * Constante con el nombre del parámetro BPM que indica el usuario Coordinador FOVIS
     */
    public static final String USUARIO_COORDINADOR = "usuarioCoordinador";
    /**
     * Constante con el nombre del parámetro BPM que indica el identificador de la solicitud global
     */
    public static final String ID_SOLICITUD = "idSolicitud";
    /**
     * Constante con el nombre del parámetro BPM que indica el tipo de cruce
     */
    public static final String TIPO_CRUCE = "tipoCruce";
    /**
     * Constante que representa un espacio vacío
     */
    private static final String STRING_ESPACIO = " ";

    private int TAMANO_LOTE = 50;
    /**
     * Constante para el Usuario Back.
     */
    private static final String USUARIO_BACK = "usuarioBack";

    @Resource(lookup = "java:jboss/ee/concurrency/executor/fovis")
    private ManagedExecutorService managedExecutorService;

    @Inject
    private FovisCompositeService fovisCompositeService;

    protected EntityManager entityManager;

    private int cores = Runtime.getRuntime().availableProcessors();

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#calcularCalificacionPostulacion(com.asopagos.dto.modelo.
     * PostulacionFOVISModeloDTO)
     */
    @Override
    public PostulacionFOVISModeloDTO calcularCalificacionPostulacion(PostulacionFOVISModeloDTO postulacionFOVISDTO) {

        try {
            logger.debug(
                    Interpolator.interpolate("Inicia calcularCalificacionCicloPostulacion(PostulacionFOVISModeloDTO) - Postulación Id:{0}",
                            postulacionFOVISDTO.getIdPostulacion()));

            // Edad mínima de un integrante, para aumento de puntaje
            Integer edadMinima = Integer
                    .parseInt(String.valueOf(CacheManager.getParametro(ParametrosSistemaConstants.PUNTAJE_FOVIS_EDAD_MINIMA)));

            // Consulta persona jefe de hogar
            PersonaModeloDTO personaJefeHogarDTO = consultarPersonaJefeHogar(postulacionFOVISDTO.getIdJefeHogar());

            // Calcula total de ingresos del hogar
            BigDecimal totalIngresosHogar = obtenerIngresosJefeHogar(personaJefeHogarDTO);
            // Número de integrantes del hogar. Se adiciona el jefe de hogar
            Integer numeroIntegrantesHogar = NumerosEnterosConstants.UNO;

            // Consultar la lista de integrantes de la postulacion
            List<IntegranteHogarModeloDTO> listaIntegranteDTO = consultarListaIntegranteHogar(personaJefeHogarDTO.getTipoIdentificacion(),
                    personaJefeHogarDTO.getNumeroIdentificacion(), postulacionFOVISDTO.getIdPostulacion());

            // Consulta las condiciones especiales asociadas a la persona jefe hogar
            List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialJefeHogarDTO = consultarCondicionEspecialPersona(
                    personaJefeHogarDTO.getTipoIdentificacion(), personaJefeHogarDTO.getNumeroIdentificacion(), postulacionFOVISDTO.getIdPostulacion());
            // Consulta el detalle de persona jefe hogar
            PersonaDetalleModeloDTO personaDetalleJefeHogarDTO = consultarPersonaDetalle(personaJefeHogarDTO.getTipoIdentificacion(),
                    personaJefeHogarDTO.getNumeroIdentificacion());

            // Se verifica si la edad del jefe hogar es 65 años cumplidos o más
            Boolean aplicaEdadJefe = verificarEdadMinima(personaDetalleJefeHogarDTO, edadMinima);

            // Se verifica si el jefe tiene condición de:
            // - Cabeza de familia
            List<NombreCondicionEspecialEnum> listCondicionesEsperadasB3 = new ArrayList<>();
            listCondicionesEsperadasB3.add(NombreCondicionEspecialEnum.CONDICION_INVALIDEZ);
            listCondicionesEsperadasB3.add(NombreCondicionEspecialEnum.MUJER_HOMBRE_CABEZA_FAMILIA);
            Boolean aplicaCondicionJefeB3 = (aplicaEdadJefe || verificarCondicionEspecial(listaCondicionEspecialJefeHogarDTO, listCondicionesEsperadasB3));

            // Se verifica si el jefe tiene alguna condicion de:
            // - Madre comunitaria ICBF
            // - Indígena
            // - Miembro de hogar afrocolombiano
            List<NombreCondicionEspecialEnum> listCondicionesEsperadasB7 = new ArrayList<>();
            listCondicionesEsperadasB7.add(NombreCondicionEspecialEnum.MADRE_COMUNITARIA_ICBF);
            listCondicionesEsperadasB7.add(NombreCondicionEspecialEnum.INDIGENA);
            listCondicionesEsperadasB7.add(NombreCondicionEspecialEnum.MIEMBRO_HOGAR_AFROCOLOMBIANO);
            Boolean aplicaCondicionJefeB7 = verificarCondicionEspecial(listaCondicionEspecialJefeHogarDTO, listCondicionesEsperadasB7);

            // Variables para los integrantes 
            Boolean aplicaCondicionIntegranteB3 = Boolean.FALSE;
            Boolean aplicaCondicionIntegranteB7 = Boolean.FALSE;
            Boolean aplicaEdadIntegrante = Boolean.FALSE;

            if (listaIntegranteDTO != null && !listaIntegranteDTO.isEmpty()) {
                // Condiciones esperadas para B3
                listCondicionesEsperadasB3 = new ArrayList<>();
                listCondicionesEsperadasB3.add(NombreCondicionEspecialEnum.CONDICION_INVALIDEZ);
                for (IntegranteHogarModeloDTO integranteHogarDTO : listaIntegranteDTO) {
                    // Se suma el salario del integrante
                    totalIngresosHogar = totalIngresosHogar.add(
                            integranteHogarDTO.getIngresosMensuales() != null ? integranteHogarDTO.getIngresosMensuales() : BigDecimal.ZERO);

                    // Consulta el detalle de persona integrante
                    PersonaDetalleModeloDTO personaDetalleIntegranteHogarDTO = consultarPersonaDetalle(
                            integranteHogarDTO.getTipoIdentificacion(), integranteHogarDTO.getNumeroIdentificacion());

                    // Consulta las condiciones especiales asociadas a la persona Integrante
                    List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecialIntegranteHogarDTO = consultarCondicionEspecialPersona(
                            integranteHogarDTO.getTipoIdentificacion(), integranteHogarDTO.getNumeroIdentificacion(), postulacionFOVISDTO.getIdPostulacion());

                    // Se verifica si la edad del integrante es 65 años cumplidos o más
                    aplicaEdadIntegrante = (aplicaEdadIntegrante || verificarEdadMinima(personaDetalleIntegranteHogarDTO, edadMinima));

                    // Se verifica si el integrante tiene condicion de:
                    // - condición de invalidez 
                    aplicaCondicionIntegranteB3 = (aplicaCondicionIntegranteB3 || verificarCondicionEspecial(listaCondicionEspecialIntegranteHogarDTO, listCondicionesEsperadasB3));

                    // Se verifica si el Integrante tiene condicion de:
                    // - Madre comunitaria ICBF
                    // - Indígena
                    // - Miembro de hogar afrocolombiano
                    aplicaCondicionIntegranteB7 = (aplicaCondicionIntegranteB7 || verificarCondicionEspecial(listaCondicionEspecialIntegranteHogarDTO, listCondicionesEsperadasB7));
                }
                numeroIntegrantesHogar += listaIntegranteDTO.size();
            }

            // Se consulta el total de recursos del hogar incluye Ahorro previo y Recurso complementario
            BigDecimal totalRecursosHogar = calcularTotalRecursosHogar(postulacionFOVISDTO.getIdPostulacion());

            // Se consulta el total de recursos del hogar incluye Ahorro previo y Recurso complementario
            BigDecimal totalAhorroPrevio = calcularTotalAhorroPrevio(postulacionFOVISDTO.getIdPostulacion());

            // Se consulta el ahorro FNA 
            AhorroPrevioModeloDTO ahorroPrevioDTO = consultarAhorroPrevio(postulacionFOVISDTO.getIdPostulacion(),
                    TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA);
            Boolean aplicaAhorroPrevio = (ahorroPrevioDTO != null && ahorroPrevioDTO.getValor() != null && ahorroPrevioDTO.getValor().compareTo(BigDecimal.ZERO) > 0);

            // Valores iniciales
            Map<String, String> mapTest = new HashMap<String, String>();

            // Parte 1 - 512,89 x (1/B1)
            // B1 - (TotalIngresosHogar/39880) 
            mapTest.put(TOTAL_INGRESOS_HOGAR, String.valueOf(totalIngresosHogar));
            Double B1 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_1_B1, mapTest);

            mapTest.clear();
            mapTest.put(STRING_B1, String.valueOf(B1));
            Double valor1 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_1, mapTest);

            // Parte 2 - 19,09 x B2
            Integer B2 = NumerosEnterosConstants.UNO;
            if (numeroIntegrantesHogar >= NumerosEnterosConstants.CINCO) {
                B2 = NumerosEnterosConstants.CUATRO;
            } else if (numeroIntegrantesHogar > NumerosEnterosConstants.UNO) {
                B2 = numeroIntegrantesHogar - NumerosEnterosConstants.UNO;
            }
            mapTest.clear();
            mapTest.put(STRING_B2, String.valueOf(B2));
            Double valor2 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_2, mapTest);

            // Parte 3 - 40,71 x B3
            Integer B3 = 0;
            if (aplicaCondicionJefeB3 || aplicaCondicionIntegranteB3 || aplicaEdadIntegrante) {
                B3 = 1;
            }

            mapTest.clear();
            mapTest.put(STRING_B3, String.valueOf(B3));
            Double valor3 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_3, mapTest);

            // Parte 4 - 4,24 x (B4/10000)
            // B4 - (TotalRecursosHogar/(TotalIngresosHogar/39980))
            mapTest.clear();
            mapTest.put(TOTAL_RECURSOS_HOGAR, String.valueOf(totalRecursosHogar));
            mapTest.put(TOTAL_INGRESOS_HOGAR, String.valueOf(totalIngresosHogar));
            mapTest.put(TOTAL_AHORRO_PREVIO, String.valueOf(totalAhorroPrevio));
            Double B4 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_4_B4, mapTest);

            mapTest.clear();
            mapTest.put(STRING_B4, String.valueOf(B4));
            Double valor4 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_4, mapTest);

            // Parte 5 - 1,63 x B5
            Integer B5 = calcularTotalMesesAhorroProgramado(postulacionFOVISDTO.getIdPostulacion());

            mapTest.clear();
            mapTest.put(STRING_B5, String.valueOf(B5));
            Double valor5 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_5, mapTest);

            // Parte 6 - 46,93 x B6
            Integer B6 = obtenerCantidadAsignaciones(postulacionFOVISDTO);

            mapTest.clear();
            mapTest.put(STRING_B6, String.valueOf(B6));
            Double valor6 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_6, mapTest);

            Double puntajeFormula = null;
            // Cálculo del puntaje formula
            puntajeFormula = valor1 + valor2 + valor3 + valor4 + valor5 + valor6;

            // Parte 7 - PuntajeFórmula*0,03
            Double valor7 = 0d;

            if (aplicaAhorroPrevio || aplicaCondicionJefeB7 || aplicaCondicionIntegranteB7) {
                mapTest.clear();
                mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                valor7 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_7, mapTest);
            }

            // Parte 8
            Double valor8 = 0d;
            Double puntajeFinal = 0d;
            Double parte8A = 0d;

            // Se obtiene el tope parametrizado para la modalidad
            BigDecimal SFVSolicitado = postulacionFOVISDTO.getValorSFVSolicitado();
            BigDecimal SFVCalculado = postulacionFOVISDTO.getValorCalculadoSFV();
            if (SFVCalculado.compareTo(BigDecimal.ZERO) != 0 && SFVCalculado.compareTo(SFVSolicitado) != 0) {
                // Se calcula el valor de R - PuntajeFórmula*1+(1-(SFVSolicitado/SFVCalculado))
                mapTest.clear();
                mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                mapTest.put(SFV_SOLICITADO, String.valueOf(SFVSolicitado));
                mapTest.put(SFV_CALCULADO, String.valueOf(SFVCalculado));
                Double valorR = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_8_R, mapTest);

                // Se calcula el valor de puntaje formula con 15% adicional - PuntajeFórmula*1.15
                mapTest.clear();
                mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                Double valorPA = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_8_PA, mapTest);

                // Se calcula el valor de parte 8 A - PuntajeFórmula*0.15
                mapTest.clear();
                mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                parte8A = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_8_A, mapTest);

                if (valorR > valorPA) {
                    valor8 = parte8A;
                } else {
                    // Se calcula el valor de parte 8 B - R/PuntajeFórmula
                    mapTest.clear();
                    mapTest.put(PUNTAJE_R, String.valueOf(valorR));
                    mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                    valor8 = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_PARTE_8_B, mapTest);
                }
            }

            // Se defiene el puntaje final como la suma del puntaje formula mas las adiciones de las partes 7 y 8
            if (valor7 > 0 && valor8 > 0) {
                // Se calcula el maximo porcentaje de adición del valor 8 - PuntajeFórmula*0,12
                mapTest.clear();
                mapTest.put(PUNTAJE, String.valueOf(puntajeFormula));
                Double porcentajeAdicional = evaluarFormula(ParametrosSistemaConstants.PUNTAJE_FOVIS_ADICION_PARTES, mapTest);

                if (porcentajeAdicional <= valor8 && valor8 <= parte8A) {
                    valor8 = porcentajeAdicional;
                }
            }
            Integer valorHORZAR = 0;

            if (postulacionFOVISDTO.getCondicionHogar() != null && postulacionFOVISDTO.getCondicionHogar()
                    .equals(CondicionHogarEnum.HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO)) {
                // Se defiene el puntaje final como la suma del puntaje formula mas las adiciones de las partes 7 y 8, y
                // Se adiciona 200 puntos si es HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO, valor proviene del parámetro
                valorHORZAR = Integer.parseInt(String.valueOf(CacheManager.getParametro(ParametrosSistemaConstants.PUNTAJE_REUBICACION_ALTO_RIESGO_NO_MITIGABLE)));
                puntajeFinal = puntajeFormula + valor7 + valor8 + valorHORZAR;
            } else {
                puntajeFinal = puntajeFormula + valor7 + valor8;
            }

            logger.info(
                    Interpolator.interpolate("Puntajes postulación {0} - B1: {1} - B2: {2} - B3: {3} - B4: {4} - B5: {5} - B6: {6}",
                            postulacionFOVISDTO.getIdPostulacion(), B1, B2, B3, B4, B5, B6));
            logger.info(Interpolator.interpolate(
                    "Puntajes postulación {0} - VALOR1: {1} - VALOR2: {2} - VALOR3: {3} - VALOR4: {4} - VALOR5: {5} - VALOR6: {6} - PUNTAJE FORMULA: {7} - VALOR7: {8} - VALOR8: {9}",
                    postulacionFOVISDTO.getIdPostulacion(), valor1, valor2, valor3, valor4, valor5, valor6, puntajeFormula, valor7,
                    valor8));

            // Se registra la información de calificación
            Long fechaCalificacion = new Date().getTime();
            BigDecimal puntaje = new BigDecimal(puntajeFinal);
            BigDecimal valorB1 = new BigDecimal(valor1);
            BigDecimal valorB2 = new BigDecimal(valor2);
            BigDecimal valorB3 = new BigDecimal(valor3);
            BigDecimal valorB4 = new BigDecimal(valor4);
            BigDecimal valorB5 = new BigDecimal(valor5);
            BigDecimal valorB6 = new BigDecimal(valor6);
            BigDecimal valorParte7 = new BigDecimal(valor7);
            BigDecimal valorParte8 = new BigDecimal(valor8);
            BigDecimal valorAdicional = new BigDecimal(valorHORZAR);


            CalificacionPostulacionDTO calificacionPostulacionDTO = new CalificacionPostulacionDTO();
            calificacionPostulacionDTO.setIdCicloAsignacion(postulacionFOVISDTO.getIdCicloAsignacion());
            calificacionPostulacionDTO.setIdPostulacion(postulacionFOVISDTO.getIdPostulacion());
            calificacionPostulacionDTO.setFechaCalificacion(fechaCalificacion);
            calificacionPostulacionDTO.setPuntaje(puntaje.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB1(valorB1.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB2(valorB2.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB3(valorB3.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB4(valorB4.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB5(valorB5.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorB6(valorB6.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorParte7(valorParte7.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorParte8(valorParte8.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setValorAdicional(valorAdicional.setScale(6, BigDecimal.ROUND_DOWN));
            calificacionPostulacionDTO.setEjecutado(Boolean.TRUE);
            calificacionPostulacionDTO = crearActualizarCalificacion(calificacionPostulacionDTO);

            // Se actualiza la postulacion con la calificacion obtenida
            postulacionFOVISDTO.setIdCalificacionPostulacion(calificacionPostulacionDTO.getIdCalificacionPostulacion());
            postulacionFOVISDTO.setPuntaje(puntaje.setScale(2, BigDecimal.ROUND_DOWN));
            postulacionFOVISDTO.setFechaCalificacion(fechaCalificacion);
            crearActualizarPostulacionFOVIS(postulacionFOVISDTO);
            logger.debug(Interpolator.interpolate(
                    "Finaliza calcularCalificacionCicloPostulacion(PostulacionFOVISModeloDTO) - Postulación Id:{0}",
                    postulacionFOVISDTO.getIdPostulacion()));
        } catch (Exception e) {
            // No se propaga la excepción por que el método es llamado de forma asíncrona
            logger.error(
                    Interpolator.interpolate("Error calcularCalificacionCicloPostulacion(PostulacionFOVISModeloDTO) - Postulación Id:{0}",
                            postulacionFOVISDTO.getIdPostulacion()),
                    e);
        }
        return postulacionFOVISDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#calcularGuardarCalificacionHogaresCiclo(java.lang.String)
     */
    @Asynchronous
    @Override
    public void calcularGuardarCalificacionHogaresCiclo(Long idCicloAsignacion, BigDecimal valorDisponible) {
        try {
            logger.debug(Interpolator.interpolate(
                    "Inicia calcularGuardarCalificacionHogaresCiclo(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible));
            //Se consulta el ciclo de asignacion del ciclo
            ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(idCicloAsignacion);
            serviceConsultaCicloAsignacion.execute();
            CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();

            if (valorDisponible == null) {

                cicloAsignacion.setEstadoCalificacion(String.valueOf(EstadoCicloAsignacionEnum.EN_PROCESO_CALIFICACION));
                GuardarActualizarCicloAsignacion serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
                serviceGuardarCiclo.execute();
                cicloAsignacion = serviceGuardarCiclo.getResult();

                //Ingresa el if cuando se ejecuta el parametro EJECUCIÓN_PRE_CALIFICACIÓN_PARA_ASIGNACIÓN_FOVIS

                // Consulta la lista de hogares que aplican para cálculo de puntaje
                List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = consultarHogaresAplicanCalificacionPostulacion(
                        idCicloAsignacion, Boolean.FALSE);

                if (listaPostulacionFOVISDTO != null && !listaPostulacionFOVISDTO.isEmpty()) {

                    // Creación de tareas paralelas
                    List<Callable<PostulacionFOVISModeloDTO>> tareasParalelas = new LinkedList<>();

                    for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPostulacionFOVISDTO) {
                        Callable<PostulacionFOVISModeloDTO> parallelTask = () -> {
                            return calcularCalificacionPostulacion(postulacionFOVISDTO);
                        };
                        tareasParalelas.add(parallelTask);
                    }

                    managedExecutorService.invokeAll(tareasParalelas);
                }

            } else {

                cicloAsignacion.setEstadoCalificacion(String.valueOf(EstadoCicloAsignacionEnum.EN_PROCESO_CALIFICACION));
                GuardarActualizarCicloAsignacion serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
                serviceGuardarCiclo.execute();
                cicloAsignacion = serviceGuardarCiclo.getResult();


                //Ingresa al else cuando se ejecuta la asignacion
                List<PostulacionFOVISModeloDTO> listaFinal = new ArrayList<>();
                Object date = consultarFechaResultadoEjecucionProgramada(cicloAsignacion.getEjecucionProgramada());
                String formattedDate = null;
                if (date != null && !date.equals("")) {
                    long timestamp = ((Number) date).longValue(); // Casteo a Long
                    // Convierte el timestamp a LocalDateTime
                    LocalDateTime dateTime = Instant.ofEpochMilli(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    // Define el formato deseado
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                    // Formatea la fecha
                    formattedDate = dateTime.format(formatter);
                }
                //----------------------------
                LocalDateTime now = LocalDateTime.now();
                // Definir el formato deseado
                DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // Formatear la fecha
                String formattedD = now.format(formatterF);
                listaFinal = consultarNovedadesPostulacionRangoFecha(idCicloAsignacion, formattedDate, formattedD);

                if (listaFinal != null && !listaFinal.isEmpty()) {
                    // Creación de tareas paralelas
                    List<Callable<PostulacionFOVISModeloDTO>> tareasParalelas = new LinkedList<>();

                    for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaFinal) {
                        Callable<PostulacionFOVISModeloDTO> parallelTask = () -> {
                            return calcularCalificacionPostulacion(postulacionFOVISDTO);
                        };
                        tareasParalelas.add(parallelTask);
                    }

                    managedExecutorService.invokeAll(tareasParalelas);
                }

            }

            cicloAsignacion.setEstadoCalificacion(String.valueOf(EstadoCicloAsignacionEnum.CALIFICADO));
            cicloAsignacion.setValorDisponible(valorDisponible);
            GuardarActualizarCicloAsignacion serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
            serviceGuardarCiclo.execute();
            cicloAsignacion = serviceGuardarCiclo.getResult();

            logger.debug(Interpolator.interpolate(
                    "Finaliza calcularGuardarCalificacionHogaresCiclo(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible));
        } catch (Exception e) {
            // No se propaga la excepción por que el método es llamado de forma asíncrona
            logger.error(Interpolator.interpolate(
                    "Finaliza calcularGuardarCalificacionHogaresCiclo(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible), e);
        }
    }

    @Override
    public void calcularGuardarCalificacionHogaresCicloSincrono(Long idCicloAsignacion, BigDecimal valorDisponible) {
        try {
            logger.debug(Interpolator.interpolate(
                    "Inicia calcularGuardarCalificacionHogaresCicloSincrono(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible));
            //Se consulta el ciclo de asignacion del ciclo
            ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(idCicloAsignacion);
            serviceConsultaCicloAsignacion.execute();
            CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();

            cicloAsignacion.setEstadoCalificacion(String.valueOf(EstadoCicloAsignacionEnum.EN_PROCESO_CALIFICACION));
            GuardarActualizarCicloAsignacion serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
            serviceGuardarCiclo.execute();
            cicloAsignacion = serviceGuardarCiclo.getResult();

            //Ingresa al else cuando se ejecuta la asignacion
            List<PostulacionFOVISModeloDTO> listaFinal = new ArrayList<>();
            Object date = consultarFechaResultadoEjecucionProgramada(cicloAsignacion.getEjecucionProgramada());
            String formattedDate = null;
            if (date != null && !date.equals("")) {
                long timestamp = ((Number) date).longValue(); // Casteo a Long

                // Convierte el timestamp a LocalDateTime
                LocalDateTime dateTime = Instant.ofEpochMilli(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                // Define el formato deseado
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                // Formatea la fecha
                formattedDate = dateTime.format(formatter);
            }
            //-----------------------------------
            LocalDateTime now = LocalDateTime.now();
            // Definir el formato deseado
            DateTimeFormatter fechaActual = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Formatear la fecha
            String fecha = now.format(fechaActual);
            listaFinal = consultarNovedadesPostulacionRangoFecha(idCicloAsignacion, formattedDate, fecha);
            // Creación de tareas paralelas
            if (listaFinal != null && !listaFinal.isEmpty()) {
                List<Callable<PostulacionFOVISModeloDTO>> tareasParalelas = new LinkedList<>();

                for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaFinal) {
                    Callable<PostulacionFOVISModeloDTO> parallelTask = () -> {
                        return calcularCalificacionPostulacion(postulacionFOVISDTO);
                    };
                    tareasParalelas.add(parallelTask);
                }

                managedExecutorService.invokeAll(tareasParalelas);
            }

            cicloAsignacion.setEstadoCalificacion(String.valueOf(EstadoCicloAsignacionEnum.CALIFICADO));
            cicloAsignacion.setValorDisponible(valorDisponible);
            serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
            serviceGuardarCiclo.execute();
            cicloAsignacion = serviceGuardarCiclo.getResult();

            logger.debug(Interpolator.interpolate(
                    "Finaliza calcularGuardarCalificacionHogaresCiclo(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible));
        } catch (Exception e) {
            // No se propaga la excepción por que el método es llamado de forma asíncrona
            logger.error(Interpolator.interpolate(
                    "Finaliza calcularGuardarCalificacionHogaresCiclo(Long, BigDecimal) - idCicloAsignacion: {0}  - valorDisponible: {1}",
                    idCicloAsignacion, valorDisponible), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#obtenerResultadosAsignacion(java.lang.String, java.lang.String)
     */
    @Override
    public EjecucionAsignacionDTO obtenerResultadosAsignacion(Long idCicloAsignacion) {
        logger.debug("Inicia obtenerResultadosAsignacion(" + idCicloAsignacion + ")");
        //Se consulta el ciclo de asignacion del ciclo
        ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(idCicloAsignacion);
        serviceConsultaCicloAsignacion.execute();
        CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();

        BigDecimal valorSFVDisponible = BigDecimal.ZERO;
        EjecucionAsignacionDTO ejecucionAsignacionDTO = new EjecucionAsignacionDTO();

        if (cicloAsignacion.getValorDisponible() != null) {
            valorSFVDisponible = cicloAsignacion.getValorDisponible();

            // Consulta la lista de hogares que aplican para cálculo de puntaje
            List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO = consultarHogaresAplicanCalificacionPostulacion(
                    idCicloAsignacion, Boolean.TRUE);

            // Listas iniciales
            List<PostulacionFOVISModeloDTO> listaPrioridad1 = new ArrayList<PostulacionFOVISModeloDTO>();
            List<PostulacionFOVISModeloDTO> listaPrioridad2 = new ArrayList<PostulacionFOVISModeloDTO>();
            List<PostulacionFOVISModeloDTO> listaPrioridad3 = new ArrayList<PostulacionFOVISModeloDTO>();
            List<PostulacionFOVISModeloDTO> listaPrioridad4 = new ArrayList<PostulacionFOVISModeloDTO>();

            // Prioridad 1: Reclamaciones procedentes
            listaPrioridad1 = consultarHogaresAplicanCalificacionConReclamacionProcedente(idCicloAsignacion);

            // Recorre cada hogar y lo asigna a la lista de prioridad respectiva
            for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPostulacionFOVISDTO) {
                // Si ya existe la postulación en la prioridad 1 no se verifican las demas
                //------------------------------------------//
                //PersonaModeloDTO personaJefeHogarDTO = new PersonaModeloDTO();
                PersonaModeloDTO personaJefeHogarDTO = consultarPersonaJefeHogar(postulacionFOVISDTO.getIdJefeHogar());

                List<Integer> idPostulacion = consultarPersonaPerdioViviendaImposibilidadPago(personaJefeHogarDTO.getTipoIdentificacion(),
                        personaJefeHogarDTO.getNumeroIdentificacion());


                //---------------------------------------//
                if (existePostulacionProcedente(listaPrioridad1, postulacionFOVISDTO)) {
                    //listaPrioridad1.add(postulacionFOVISDTO);
                    continue;
                } else if (postulacionFOVISDTO.getHogarPerdioSubsidioNoPago() || (idPostulacion != null && idPostulacion.size() == 1))
//                		|| idPostulacion == null)
                {
                    // Prioridad 2: Hogares beneficiarios de subsidio de vivienda que perdieron por imposibilidad de pago
                    listaPrioridad2.add(postulacionFOVISDTO);
                }
                /* El damnificado no es prioridad, tiene 200 puntos por la condición
                else if (postulacionFOVISDTO.getCondicionHogar() != null && postulacionFOVISDTO.getCondicionHogar()
                        .equals(CondicionHogarEnum.HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO)) {
                    // Prioridad 3: Hogares objeto de reubicación zona de alto riesgo no mitigable
                    listaPrioridad3.add(postulacionFOVISDTO);
                }*/
                else {
                    // Prioridad 3: Hogares calificados que no cumplen con ninguna de las anteriores condiciones
                    listaPrioridad3.add(postulacionFOVISDTO);
                    //listaPrioridad4.add(postulacionFOVISDTO);
                }
            }

            // Realiza ordenamiento por puntaje, en orden descendente
            if (listaPrioridad1 != null && listaPrioridad1.size() > 1) {
                Collections.sort(listaPrioridad1, (o1, o2) -> o2.getPuntaje().compareTo(o1.getPuntaje()));
            }

            if (listaPrioridad2 != null && listaPrioridad2.size() > 1) {
                Collections.sort(listaPrioridad2, (o1, o2) -> o2.getPuntaje().compareTo(o1.getPuntaje()));
            }

            if (listaPrioridad3 != null && listaPrioridad3.size() > 1) {
                Collections.sort(listaPrioridad3, (o1, o2) -> o2.getPuntaje().compareTo(o1.getPuntaje()));
            }

            if (listaPrioridad4 != null && listaPrioridad4.size() > 1) {
                Collections.sort(listaPrioridad4, (o1, o2) -> o2.getPuntaje().compareTo(o1.getPuntaje()));
            }

            // Crea la lista de resultados de asignación
            List<ResultadoAsignacionDTO> listaResultadoAsignacionDTO = crearListaResultadoAsignacion(listaPrioridad1, listaPrioridad2,
                    listaPrioridad3, listaPrioridad4, valorSFVDisponible);

            // Crea la lista de resumen de asignación
            List<ResumenAsignacionDTO> listaResumenAsignacionDTO = crearListaResumenAsignacion(listaResultadoAsignacionDTO);

            // Crea el objeto de retorno

            ejecucionAsignacionDTO.setListaResultadoAsignacionDTO(listaResultadoAsignacionDTO);
            ejecucionAsignacionDTO.setListaResumenAsignacionDTO(listaResumenAsignacionDTO);
        }

        logger.debug("Finaliza obtenerResultadosAsignacion(" + idCicloAsignacion + ")");
        return ejecucionAsignacionDTO;
    }

    /**
     * Verifica si la postulación enviada existe marcada como reclamación procedente
     *
     * @param listaPrioridad1     Lista de postulaciones con reclamación procedente
     * @param postulacionFOVISDTO Postulación que se esta revisando
     * @return <code>true</code> cuando existe la postulación marcada con reclamación procedente o <code>false</code> cuando no existe la
     * postulación marcada
     */
    private Boolean existePostulacionProcedente(List<PostulacionFOVISModeloDTO> listaPrioridad1,
                                                PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        Boolean existePostulacionProcedente = false;
        for (PostulacionFOVISModeloDTO postulacionFOVISModeloDTO : listaPrioridad1) {
            if (postulacionFOVISDTO.getIdPostulacion().equals(postulacionFOVISModeloDTO.getIdPostulacion())) {
                existePostulacionProcedente = true;
                break;
            }
        }
        return existePostulacionProcedente;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#aceptarResultadosEjecucionAsignacion(com.asopagos.dto.fovis.
     * EjecucionAsignacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void aceptarResultadosEjecucionAsignacion(EjecucionAsignacionDTO ejecucionAsignacionDTO, UserDTO userDTO) {
        logger.debug("Inicia servicio aceptarResultadosEjecucionAsignacion");

        // Almacena en Solicitud
        SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
        solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudDTO.setFechaRadicacion(new Date().getTime());
        solicitudDTO.setFechaCreacion(new Date().getTime());
        solicitudDTO.setTipoTransaccion(TipoTransaccionEnum.ASIGNACION_SUBSIDIO_FOVIS);
        solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        Long idSolicitudGlobal = guardarSolicitudGlobal(solicitudDTO);
        generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());
        solicitudDTO = consultarSolicitudGlobal(idSolicitudGlobal);

        // Almacena en SolicitudAsignacion
        BigDecimal totalSubsidioAsignado = BigDecimal.ZERO;

        for (ResumenAsignacionDTO resumenAsignacionDTO : ejecucionAsignacionDTO.getListaResumenAsignacionDTO()) {
            totalSubsidioAsignado = totalSubsidioAsignado.add(resumenAsignacionDTO.getValorSubsidioAsignado());
        }

        SolicitudAsignacionFOVISModeloDTO solicitudAsignacionDTO = new SolicitudAsignacionFOVISModeloDTO();
        solicitudAsignacionDTO.convertToDTO(solicitudDTO.convertToSolicitudEntity());
        solicitudAsignacionDTO.setFechaAceptacion(new Date().getTime());
        solicitudAsignacionDTO.setUsuario(userDTO.getNombreUsuario());
        solicitudAsignacionDTO.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.ASIGNACION_SEGUNDA_APROBACION_PENDIENTE);
        solicitudAsignacionDTO
                .setIdCicloAsignacion(ejecucionAsignacionDTO.getListaResultadoAsignacionDTO().get(0).getIdCicloAsignacion());
        solicitudAsignacionDTO.setComentarios(ejecucionAsignacionDTO.getComentarioCoordinador());
        solicitudAsignacionDTO.setValorSFVAsignado(totalSubsidioAsignado);
        solicitudAsignacionDTO = guardarActualizarSolicitudAsignacion(solicitudAsignacionDTO);

        // Actualiza la información de los hogares/postulación
        List<PostulacionAsignacionDTO> listPostulaciones = new ArrayList<>();
        for (ResultadoAsignacionDTO resultadoAsignacionDTO : ejecucionAsignacionDTO.getListaResultadoAsignacionDTO()) {
            resultadoAsignacionDTO.setIdSolicitudAsignacion(solicitudAsignacionDTO.getIdSolicitudAsignacion());
            //resultadoAsignacionDTO.setRecursoPrioridad("PRIMERA_PRIORDAD");
            PostulacionFOVISModeloDTO postulacion = new PostulacionFOVISModeloDTO(resultadoAsignacionDTO.convertToEntity());
            //postulacion.setRecursoPrioridad("PRIMERA_PRIORDAD");
            postulacion.setRecursoPrioridad(resultadoAsignacionDTO.getRecursoPrioridad());
            crearActualizarPostulacionFOVIS(postulacion);
            // Se crea la relación de postulación asignación
            //postulacion.setRecursoPrioridad("SEGUNDA_PRIORDAD");
            PostulacionAsignacionDTO postulacionAsignacionDTO = new PostulacionAsignacionDTO();
            postulacionAsignacionDTO.convertToDTO(postulacion);
            listPostulaciones.add(postulacionAsignacionDTO);//recursoPrioridad
        }
        crearActualizarListaPostulacionAsignacion(listPostulaciones);

        // Se asigna la tarea al coordinador interno FOVIS
        Map<String, Object> params = new HashMap<>();
        String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                ProcesoEnum.ASIGNACION_FOVIS);
        params.put(ID_SOLICITUD, idSolicitudGlobal.toString());
        params.put(USUARIO_COORDINADOR, userDTO.getNombreUsuario());
        params.put(USUARIO_CONTROL_INTERNO, destinatario);
        params.put(NUMERO_RADICADO, solicitudDTO.getNumeroRadicacion());
        Long idInstanciaProceso = iniciarProceso(solicitudDTO.getTipoTransaccion().getProceso(), params);

        // Actualiza la solicitud con la instancia del proceso y destinatario
        solicitudDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
        solicitudDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
        solicitudDTO.setDestinatario(destinatario);
        guardarSolicitudGlobal(solicitudDTO);


        logger.debug("Finaliza servicio aceptarResultadosEjecucionAsignacion");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#almacenarResultadoAsignacion(com.asopagos.dto.modelo.
     * SolicitudAsignacionFOVISModeloDTO)
     */
    @Override
    public SolicitudAsignacionFOVISModeloDTO almacenarResultadoAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        SolicitudAsignacionFOVISModeloDTO solicitud;
        logger.debug("Inicio servicio almacenarResultadoAsignacion");
        solicitud = consultarSolicitudAsignacionPorId(solicitudAsignacion.getIdSolicitudAsignacion());
        //Se actualiza el estado de la solicitud de asignacion a Asignacion Fovis 
        solicitud.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.ASIGNACION_FOVIS);
        solicitud.setComentarioControlInterno(solicitudAsignacion.getComentarioControlInterno());
        solicitud.setFechaAceptacion(solicitudAsignacion.getFechaAceptacion());
        solicitud.setUsuario(solicitudAsignacion.getUsuario());
        solicitud.setValorSFVAsignado(solicitudAsignacion.getValorSFVAsignado());

        solicitudAsignacion = guardarActualizarSolicitudAsignacion(solicitud);

        solicitud = guardarResultadoAsignacion(solicitudAsignacion);

        //Si ya existe una instancia de Proceso asociada se termina la tarea.            
        if (solicitud.getIdSolicitudAsignacion() != null && solicitudAsignacion.getIdSolicitud() != null) {

            //Se asigna la tarea a la bandeja de gestión del usuario con perfil Coordinador FOVIS.
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_ASIGNACION_CONTROL_INTERNO, 1);
            Long idTarea = consultarTareaActiva(new Long(solicitudAsignacion.getIdInstanciaProceso()));
            terminarTarea(idTarea, params);
        }

        logger.debug("Finaliza servicio almacenarResultadoAsignacion");
        return solicitud;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#aprobarDocumentoAsignacion(com.asopagos.dto.modelo.
     * ActaAsignacionFOVISModeloDTO)
     */
    @Override
    public ActaAsignacionFOVISModeloDTO aprobarDocumentoAsignacion(ActaAsignacionFOVISModeloDTO actaAsignacionFOVISDTO) {
        ActaAsignacionFOVISModeloDTO actaAsignacion;
        logger.debug("Inicio servicio aprobarDocumentoAsignacion");
        // Valor SMMLV parametrizado
        BigDecimal smlmv = BigDecimal
                .valueOf(Double.parseDouble(String.valueOf(CacheManager.getParametro(ParametrosSistemaConstants.SMMLV))));

        actaAsignacion = guardarActualizarActaAsignacion(actaAsignacionFOVISDTO);
        //Cambia el estado de la tarea de asignación a Acta asignación FOVIS con firmas cargada
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacion = consultarSolicitudAsignacionPorId(
                actaAsignacionFOVISDTO.getIdSolicitudAsignacion());

        // Se consultan las postulaciones relacionadas
        List<PostulacionAsignacionDTO> listPostualcionesAsignacion = consultarListaPostulacionAsignacion(
                actaAsignacionFOVISDTO.getIdSolicitudAsignacion());
        // Se actualiza el estado de la solicitud de asignación
        ActualizarEstadoSolicitudAsignacion estadoSolicitudAsignacion = new ActualizarEstadoSolicitudAsignacion(
                solicitudAsignacion.getIdSolicitud(), EstadoSolicitudAsignacionEnum.ACTA_ASIGNACION_FOVIS_CON_FIRMAS_CARGADA);
        estadoSolicitudAsignacion.execute();

        // Si ya existe una instancia de Proceso asociada se termina la tarea.            
        if (actaAsignacionFOVISDTO.getIdActaAsignacion() != null && actaAsignacionFOVISDTO.getIdSolicitudAsignacion() != null) {

            if (solicitudAsignacion != null && solicitudAsignacion.getIdSolicitud() != null) {
                InformacionConvertDTO consolidadoCartasGeneradas = new InformacionConvertDTO();
                StringBuilder htmlContent = new StringBuilder("<br>");
                // Se crean las cartas de las postulaciones asignadas
                ConsultarPostulacionesSolicitudPorResultado servicePostulacionesAsignacion = new ConsultarPostulacionesSolicitudPorResultado(
                        solicitudAsignacion.getIdSolicitudAsignacion(), ResultadoAsignacionEnum.ESTADO_ASIGNADO);
                servicePostulacionesAsignacion.execute();
                List<PostulacionFOVISModeloDTO> listaPostulaciones = servicePostulacionesAsignacion.getResult();
                /*for (PostulacionFOVISModeloDTO postulacion : listaPostulaciones) {
                    //Se genera la carta y se almacena en el ECM
                    Vector<Object> valoresCarta = generarCartaAsignacionPostulacion(postulacion.getIdPostulacion(),
                            solicitudAsignacion.getIdInstanciaProceso());
                    //Se actualiza la postulacion con la carta generada y almacenada
                    postulacion.setIdDocumento((String) valoresCarta.get(1));
                    // Se guarda el valor del salario actual
                    postulacion.setValorSalarioAsignacion(smlmv);
                    CrearActualizarPostulacionFOVIS servicePostulacion = new CrearActualizarPostulacionFOVIS(postulacion);
                    servicePostulacion.execute();
                    InformacionConvertDTO contenidoCarta = (InformacionConvertDTO) valoresCarta.get(0);
                    if (agregarEncabezadoPie) {
                        consolidadoCartasGeneradas.setHtmlHeader(contenidoCarta.getHtmlHeader());
                        consolidadoCartasGeneradas.setHtmlFooter(contenidoCarta.getHtmlFooter());
                        agregarEncabezadoPie = Boolean.FALSE;
                    }

                    htmlContent.append(contenidoCarta.getHtmlHeader());
                    htmlContent.append(contenidoCarta.getHtmlContenido());
                    htmlContent.append(contenidoCarta.getHtmlFooter());
                    htmlContent.append("<br>");

                    procesarAgregarDocumentoPostulacionAsingacion(listPostualcionesAsignacion, postulacion);
                }*/

                procesarPostulaciones(listaPostulaciones, consolidadoCartasGeneradas, htmlContent, smlmv, solicitudAsignacion);

                //Se genera el documento consolidado con todas las cartas
                logger.info("htmlContent 3.0 " +htmlContent);
                consolidadoCartasGeneradas.setHtmlContenido(htmlContent.toString());
                String idDocumentoConsolidado = generarConsolidadoCartasAsignacion(consolidadoCartasGeneradas,
                        actaAsignacionFOVISDTO.getIdSolicitudAsignacion(), solicitudAsignacion.getIdInstanciaProceso());
                //Se actualiza el acta de asignacion con el identificador del documento consolidado y almacenado en el ECM
                actaAsignacionFOVISDTO.setIdDocumentoConsolidado(idDocumentoConsolidado);
                actaAsignacion = guardarActualizarActaAsignacion(actaAsignacionFOVISDTO);

                //Cambia el estado de la tarea de asignación a Cerrada
                ActualizarEstadoSolicitudAsignacion estadoSolicitudCerrada = new ActualizarEstadoSolicitudAsignacion(
                        solicitudAsignacion.getIdSolicitud(), EstadoSolicitudAsignacionEnum.CERRADA);
                estadoSolicitudCerrada.execute();

                // Se actualiza la relacion de postulación asignación
                crearActualizarListaPostulacionAsignacion(listPostualcionesAsignacion);

                // Se asignan los hogares en estado calificado no asignado al siguiente ciclo
                asignarPostulacionesSiguienteCiclo(solicitudAsignacion);

                // Se procesa las postulaciones para almacenar el estado actual y ajustar el ciclo de asignación
                fovisCompositeService.procesarEstadoActualPostulaciones(listaPostulaciones);

                Long idTarea = consultarTareaActiva(new Long(solicitudAsignacion.getIdInstanciaProceso()));
                terminarTarea(idTarea, null);
            }
        }

        logger.debug("Finaliza servicio aprobarDocumentoAsignacion");
        return actaAsignacion;
    }

    private void procesarPostulaciones(List<PostulacionFOVISModeloDTO> postulaciones, InformacionConvertDTO consolidadoCartasGeneradas, StringBuilder htmlContent, BigDecimal smlmv, SolicitudAsignacionFOVISModeloDTO solicitud) {
        List<CompletableFuture<InformacionConvertDTO>> futures = new ArrayList<>();

        for (PostulacionFOVISModeloDTO postulacion : postulaciones) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                Vector<Object> valoresCarta = generarCartaAsignacionPostulacion(postulacion.getIdPostulacion(), solicitud.getIdInstanciaProceso());
                logger.info("valoresCarta " + valoresCarta);
                postulacion.setIdDocumento((String) valoresCarta.get(1));
                postulacion.setValorSalarioAsignacion(smlmv);
                new CrearActualizarPostulacionFOVIS(postulacion).execute();
                return (InformacionConvertDTO) valoresCarta.get(0);
            }));
        }

        // Esperar que todas las tareas completen
        List<InformacionConvertDTO> resultados = futures.stream()
                .map(CompletableFuture::join) // Unir resultados
                .collect(Collectors.toList());

        // Combinar resultados
        boolean agregarEncabezadoPie = true;
        for (InformacionConvertDTO contenidoCarta : resultados) {
            synchronized (htmlContent) {
                htmlContent.append(contenidoCarta.getHtmlHeader())
                        .append(contenidoCarta.getHtmlContenido())
                        .append(contenidoCarta.getHtmlFooter())
                        .append("<br>");

                logger.info("htmlContent " +htmlContent);
                logger.info("contenidoCarta getHtmlHeader " +contenidoCarta.getHtmlHeader());
                logger.info("contenidoCarta.getHtmlContenido() " +contenidoCarta.getHtmlContenido());
                logger.info("contenidoCarta.getHtmlFooter() " +contenidoCarta.getHtmlFooter());
            }

            logger.info("htmlContent 1.0 " +htmlContent);



            // Sincronización para establecer el encabezado y pie
            if (agregarEncabezadoPie) {
                synchronized (consolidadoCartasGeneradas) {
                    consolidadoCartasGeneradas.setHtmlHeader(contenidoCarta.getHtmlHeader());
                    consolidadoCartasGeneradas.setHtmlFooter(contenidoCarta.getHtmlFooter());
                    agregarEncabezadoPie = false;
                    logger.info("consolidadoCartasGeneradas " +consolidadoCartasGeneradas);
                    logger.info("consolidadoCartasGeneradas contenidoCarta.getHtmlHeader() " +contenidoCarta.getHtmlFooter());
                    logger.info("consolidadoCartasGeneradas contenidoCarta.getHtmlFooter()" +contenidoCarta.getHtmlFooter());
                }

                logger.info("consolidadoCartasGeneradas 1.0" +consolidadoCartasGeneradas);
            }
        }
    }

    @Override
    public SolicitudAsignacionFOVISModeloDTO rechazarResultadoSolicitudAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        try {
            // Se actualiza el estado a ASIGNACION_RECHAZADA_CONTROL_INTERNO
            solicitudAsignacion.setEstadoSolicitudAsignacion(EstadoSolicitudAsignacionEnum.ASIGNACION_RECHAZADA_CONTROL_INTERNO);
            guardarActualizarSolicitudAsignacion(solicitudAsignacion);

            // Se actualiza el estado a CERRADA
            ActualizarEstadoSolicitudAsignacion actualizaEstadoCerrada = new ActualizarEstadoSolicitudAsignacion(
                    solicitudAsignacion.getIdSolicitud(), EstadoSolicitudAsignacionEnum.CERRADA);
            actualizaEstadoCerrada.execute();

            //Se asigna la tarea a la bandeja de gestión del usuario con perfil Coordinador FOVIS.
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_ASIGNACION_CONTROL_INTERNO, 2);
            Long idTarea = consultarTareaActiva(new Long(solicitudAsignacion.getIdInstanciaProceso()));
            terminarTarea(idTarea, params);

            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#guardarInformacionYDocumentoActaAsignacion(java.lang.Long,
     * com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO)
     */
    @Override
    public ActaAsignacionFOVISModeloDTO guardarInformacionYDocumentoActaAsignacion(Long idSolicitudGlobal,
                                                                                   ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO) {

        logger.debug("Inicia guardarInformacionYDocumentoActaAsignacion( idSolicitudGlobal )");

        //Se consulta la solicitud de asignacion
        ConsultarSolicitudAsignacionPorSolicitudGlobal serviceConsultaSolicitudAsignacion = new ConsultarSolicitudAsignacionPorSolicitudGlobal(
                idSolicitudGlobal);
        serviceConsultaSolicitudAsignacion.execute();
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacionDTO = serviceConsultaSolicitudAsignacion.getResult();

        // Se almacena la informacion del acta de asignacion con la informacion ingresada por pantalla
        actaAsignacionFOVISModeloDTO.setFechaActaAsignacionFovis(new Date().getTime());
        //Se asocia el identificador de la solicitud de asignacion al acta
        actaAsignacionFOVISModeloDTO.setIdSolicitudAsignacion(solicitudAsignacionDTO.getIdSolicitudAsignacion());
        actaAsignacionFOVISModeloDTO = guardarActualizarActaAsignacion(actaAsignacionFOVISModeloDTO);

        //Se cambia el estado de la solicitud de asignacion a ACTA_ASIGNACION_FOVIS_APROBADA
        ActualizarEstadoSolicitudAsignacion estadoSolicitudAsignacion = new ActualizarEstadoSolicitudAsignacion(idSolicitudGlobal,
                EstadoSolicitudAsignacionEnum.ACTA_ASIGNACION_FOVIS_APROBADA);
        estadoSolicitudAsignacion.execute();

        String idECM = null;
        Map<String, Object> params = new HashMap<String, Object>();
        // Se consulta el comunicado con todas sus variables resueltas
        PlantillaComunicado plaCom = new PlantillaComunicado();
        if (idSolicitudGlobal != null) {
            // de acuerdo al id de la postulacion para la creacion de la tabla
            plaCom = resolverPlantillaVariablesComunicadoPorSolicitud(EtiquetaPlantillaComunicadoEnum.ACT_ASIG_FOVIS, idSolicitudGlobal,
                    params);

            logger.info("plaCom " + plaCom);
        }

        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error la plantilla del comunicado no tiene datos");
            logger.debug("Se asume que no tiene documento adjunto");
        } else {

            List<Float> magenesX = new ArrayList<Float>();
            magenesX.add(56f);
            magenesX.add(56f);
            List<Float> magenesY = new ArrayList<Float>();
            magenesY.add(40f);
            magenesY.add(40f);

            InformacionConvertDTO infoConv = new InformacionConvertDTO();
            infoConv.setHtmlHeader(plaCom.getEncabezado());
            infoConv.setHtmlContenido(plaCom.getCuerpo());
            infoConv.setHtmlFooter(plaCom.getPie());
            // infoConv.setHtmlSello(htmlSello);
            infoConv.setMargenesx(magenesX);
            infoConv.setMargenesy(magenesY);
            infoConv.setAltura(100f);
            infoConv.setRequiereSello(true);

            logger.info("plaCom " + plaCom.getCuerpo());
            logger.info("infoConv " + infoConv);
            // se convierte a pdf la plantilla de comunicado
            byte[] bytes = convertHTMLtoPDF(infoConv);

            logger.info("bytes " + bytes);

            if (bytes == null) {
                logger.error("Error la plantilla del comunicado no fue convertida a PDF");
                logger.debug("Finaliza generarCartaAsignacionPostulacion(NotificacionParametrizadaDTO)");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
            }

            //Se consulta el ciclo de asignacion
            ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(
                    solicitudAsignacionDTO.getIdCicloAsignacion());
            serviceConsultaCicloAsignacion.execute();
            CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();

            InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
            infoArch.setDataFile(bytes);
            infoArch.setDescription(ProcesoEnum.ASIGNACION_FOVIS.getDescripcion());
            infoArch.setDocName("Acta_asignacion_FOVIS_Ciclo_asignacion_" + cicloAsignacion.getNombre().trim() + ".pdf");
            infoArch.setFileName("Acta_asignacion_FOVIS_Ciclo_asignacion_" + cicloAsignacion.getNombre().trim() + ".pdf");
            infoArch.setFileType("application/pdf");
            logger.info("infoArch " + infoArch);
            logger.info("infoArch datefile" + infoArch.getDataFile());
            // validar campos
            infoArch.setProcessName(ProcesoEnum.ASIGNACION_FOVIS.toString());
            infoArch.setIdInstanciaProceso(solicitudAsignacionDTO.getIdInstanciaProceso());
            // TODO se realiza el upload del archivo al ECM
            //eliminarArchivo("2a18a85d-3bd6-4941-a2da-f0ba2ed2d01a_1.4");
            idECM = almacenarArchivo(infoArch);
            logger.info("idECM " +idECM);
            //Se asigna identificador del documento en el ECM
            actaAsignacionFOVISModeloDTO.setIdDocumento(idECM);
            // Se actualiza el acta de asignacion con el identificador del documento generado y almacenado en el ECM           
            guardarActualizarActaAsignacion(actaAsignacionFOVISModeloDTO);

        }
        logger.debug("Finaliza guardarInformacionYDocumentoActaAsignacion( idSolicitudGlobal )");
        return actaAsignacionFOVISModeloDTO;

    }

    // *******************************************************************//
    // ------------------------ Métodos privados -------------------------//
    // *******************************************************************//

    /**
     * Obtiene los ingresos del jefe de hogar
     *
     * @param personaJefeHogarDTO Información persona jefe
     * @return Salario del jefe de hogar
     */
    private BigDecimal obtenerIngresosJefeHogar(PersonaModeloDTO personaJefeHogarDTO) {
        BigDecimal ingresos = BigDecimal.ZERO;

        JefeHogarModeloDTO jefeHogar = this.consultarJefeHogar(personaJefeHogarDTO.getTipoIdentificacion(),
                personaJefeHogarDTO.getNumeroIdentificacion());

        if (jefeHogar != null && jefeHogar.getIngresosMensuales() != null) {
            ingresos = jefeHogar.getIngresosMensuales();
            return ingresos;
        }

        ConsultarAfiliadoOutDTO afiliadoJefeHogar = consultarAfiliado(personaJefeHogarDTO.getTipoIdentificacion(),
                personaJefeHogarDTO.getNumeroIdentificacion());

        if (afiliadoJefeHogar == null || afiliadoJefeHogar.getInformacionLaboralTrabajador() == null
                || afiliadoJefeHogar.getInformacionLaboralTrabajador().isEmpty()) {
            logger.error("No se encuentra información laboral del afiliado - jefe del hogar");
            return ingresos;
        }

        for (InformacionLaboralTrabajadorDTO informacionLaboralDTO : afiliadoJefeHogar.getInformacionLaboralTrabajador()) {
            if (informacionLaboralDTO.getValorSalario() != null) {
                ingresos = ingresos.add(informacionLaboralDTO.getValorSalario());
            }
        }
        return ingresos;
    }

    /**
     * Verifica si dentro de la lista de condiciones especiales se encuentra alguna de las condiciones esperadas
     *
     * @param listaCondicionEspecial Lista condiciones especiales asociadas persona
     * @param condicionesEsperadas   Lista condiciones especiales esperadas que existan asociadas a la persona
     * @return TRUE si se encuentra coincidencia
     */
    private Boolean verificarCondicionEspecial(List<CondicionEspecialPersonaModeloDTO> listaCondicionEspecial,
                                               List<NombreCondicionEspecialEnum> condicionesEsperadas) {
        Boolean result = Boolean.FALSE;
        for (CondicionEspecialPersonaModeloDTO condicionEspecialPersonaModeloDTO : listaCondicionEspecial) {
            if (condicionesEsperadas.contains(condicionEspecialPersonaModeloDTO.getNombreCondicion())) {
                result = Boolean.TRUE;
                break;
            }
        }
        return result;
    }

    /**
     * Verifica si existe fecha de nacimiento y si la edad calculada es superior al parametro de edad minima
     *
     * @param personaDetalle Informacion detalle de persona
     * @param edadMinima     Edad minima parametrizada
     * @return TRUE si la edad es superior a la minima parametrizada
     */
    private Boolean verificarEdadMinima(PersonaDetalleModeloDTO personaDetalle, Integer edadMinima) {
        Boolean result = Boolean.FALSE;
        if (personaDetalle != null && personaDetalle.getFechaNacimiento() != null) {
            Integer edadPersona = CalendarUtils.calcularEdadAnos(new Date(personaDetalle.getFechaNacimiento()));
            if (edadPersona >= edadMinima) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    /**
     * Obtiene el tope de SFV para la modalidad enviada
     *
     * @param ingresosHogar Ingresos del hogar
     * @param modalidad     Modalidad postulacion
     * @param smlmv         Parametrizacion de SMLMV del sistema
     * @return Valor tope de subsidio familiar de vivienda
     */
    private BigDecimal obtenerTopeSFV(BigDecimal ingresosHogar, ModalidadEnum modalidad, BigDecimal smlmv) {
        BigDecimal topeSFV = BigDecimal.ZERO;

        // Se obtiene la informacion de modalidad de la postulacion
        List<ParametrizacionModalidadModeloDTO> listaParametrizacionModalidadDTO = consultarParametrizacionModalidades();
        ParametrizacionModalidadModeloDTO parametrizacionModalidadDTO = buscarParametrizacionModalidad(listaParametrizacionModalidadDTO,
                modalidad);

        if (parametrizacionModalidadDTO.getRangosSVFPorModalidad() == null
                || parametrizacionModalidadDTO.getRangosSVFPorModalidad().isEmpty()) {
            return topeSFV;
        }

        // Se obtiene la cantidad de SMLMV de ingresos 
        BigDecimal cantidadSMLMVIngresosHogar = ingresosHogar.divide(smlmv, 2, RoundingMode.CEILING);
        // Se obtiene el rango
        for (RangoTopeValorSFVModeloDTO rango : parametrizacionModalidadDTO.getRangosSVFPorModalidad()) {
            if (RangoTopeUtils.verificarRangoOperador(rango.getOperadorValorMinimo(), cantidadSMLMVIngresosHogar.doubleValue(),
                    rango.getValorMinimo().doubleValue())
                    && RangoTopeUtils.verificarRangoOperador(rango.getOperadorValorMaximo(), cantidadSMLMVIngresosHogar.doubleValue(),
                    rango.getValorMaximo().doubleValue())) {
                topeSFV = rango.getTopeSMLMV().multiply(smlmv);
                break;
            }
        }

        return topeSFV;
    }

    /**
     * Obtiene la cantidad de participacion en procesos de asignacion y esta en estado Calificado no asignado
     *
     * @param personaJefeHogar Informacion persona jefe
     * @return Numero de participaciones
     */
    private Integer obtenerCantidadAsignaciones(PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        Integer result = null;
        // Se consulta la cantidad de asignaciones previas al hogar
        ConsultarCantidadAsignacionesPreviasHogar consultarCantidadAsignacionesPreviasHogar = new ConsultarCantidadAsignacionesPreviasHogar(
                postulacionFOVISModeloDTO.getIdPostulacion());
        consultarCantidadAsignacionesPreviasHogar.execute();
        result = consultarCantidadAsignacionesPreviasHogar.getResult();

        if (result == null) {
            result = NumerosEnterosConstants.CERO;
        }
        return result;
    }

    /**
     * Servicio para la creación y actualización de la calificación de postulación
     *
     * @param calificacionPostulacionDTO Información de calificación
     * @return Información de calificación registrada
     */
    private CalificacionPostulacionDTO crearActualizarCalificacion(CalificacionPostulacionDTO calificacionPostulacionDTO) {
        logger.debug("Inicio método crearActualizarCalificacion(CalificacionPostulacionDTO)");
        CrearActualizarCalificacion crearActualizarCalificacion = new CrearActualizarCalificacion(calificacionPostulacionDTO);
        crearActualizarCalificacion.execute();
        logger.debug("Fin método crearActualizarCalificacion(CalificacionPostulacionDTO)");
        return crearActualizarCalificacion.getResult();
    }

    /**
     * Servicio para la creación y actualización de la relación de postulación y ciclo de asignación
     *
     * @param listPostulaciones Lista con la información de postulación y asignación
     */
    private void crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO> listPostulaciones) {
        logger.debug("Inicio método crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO)");
        CrearActualizarListaPostulacionAsignacion crearActualizarPostulacionAsignacion = new CrearActualizarListaPostulacionAsignacion(
                listPostulaciones);
        crearActualizarPostulacionAsignacion.execute();
        logger.debug("Fin método crearActualizarListaPostulacionAsignacion(List<PostulacionAsignacionDTO)");
    }

    /**
     * Consulta las postulaciones relacionadas al ciclo de asignación
     *
     * @param idSolicitudAsignacion Identificador solicitud asignación
     * @return Lista de postulaciones en la asignación
     */
    private List<PostulacionAsignacionDTO> consultarListaPostulacionAsignacion(Long idSolicitudAsignacion) {
        logger.debug("Inicio método consultarListaPostulacionAsignacion(Long)");
        ConsultarListaPostulacionAsignacion consultarListaPostulacionAsignacion = new ConsultarListaPostulacionAsignacion(
                idSolicitudAsignacion);
        consultarListaPostulacionAsignacion.execute();
        logger.debug("Fin método consultarListaPostulacionAsignacion(Long)");
        return consultarListaPostulacionAsignacion.getResult();
    }

    private void procesarAgregarDocumentoPostulacionAsingacion(List<PostulacionAsignacionDTO> listPostulacionAsignacion, PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        if (listPostulacionAsignacion == null || listPostulacionAsignacion.isEmpty()) {
            return;
        }
        for (PostulacionAsignacionDTO postulacionAsignacionDTO : listPostulacionAsignacion) {
            if (postulacionAsignacionDTO.getIdPostulacion().equals(postulacionFOVISModeloDTO.getIdPostulacion())) {
                postulacionAsignacionDTO.setIdDocumentoActaAsignacion(postulacionFOVISModeloDTO.getIdDocumento());
            }
        }
    }

    /**
     * Método que crea una lista de resultados de asignación FOVIS, de acuerdo a un conjunto de listas de postulaciones, enviadas como
     * parámetros
     *
     * @param listaPrioridad2    Lista de postulaciones con prioridad 2
     * @param listaPrioridad3    Lista de postulaciones con prioridad 3
     * @param listaPrioridad4    Lista de postulaciones con prioridad 4
     * @param valorSFVDisponible Valor disponible
     * @return La lista de resultados de asignación
     */
    private List<ResultadoAsignacionDTO> crearListaResultadoAsignacion(List<PostulacionFOVISModeloDTO> listaPrioridad1,
                                                                       List<PostulacionFOVISModeloDTO> listaPrioridad2, List<PostulacionFOVISModeloDTO> listaPrioridad3,
                                                                       List<PostulacionFOVISModeloDTO> listaPrioridad4, BigDecimal valorSFVDisponible) {
        logger.debug("Inicio método crearListaResultadoAsignacion");
        List<ResultadoAsignacionDTO> listaResultadoAsignacionDTO = new ArrayList<ResultadoAsignacionDTO>();
        BigDecimal acumulado = BigDecimal.ZERO;
        if (listaPrioridad1 != null && !listaPrioridad1.isEmpty()) {
            for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPrioridad1) {
                acumulado = acumulado.add(postulacionFOVISDTO.getValorSFVSolicitado());
                ResultadoAsignacionDTO resultadoAsignacionDTO = obtenerResultadoAsignacion(postulacionFOVISDTO, valorSFVDisponible, acumulado,
                        PrioridadAsignacionEnum.PRIORIDAD_1);
                listaResultadoAsignacionDTO.add(resultadoAsignacionDTO);
            }
        }
        if (listaPrioridad2 != null && !listaPrioridad2.isEmpty()) {
            for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPrioridad2) {
                acumulado = acumulado.add(postulacionFOVISDTO.getValorSFVSolicitado());
                ResultadoAsignacionDTO resultadoAsignacionDTO = obtenerResultadoAsignacion(postulacionFOVISDTO, valorSFVDisponible, acumulado,
                        PrioridadAsignacionEnum.PRIORIDAD_2);
                listaResultadoAsignacionDTO.add(resultadoAsignacionDTO);
            }
        }
        if (listaPrioridad3 != null && !listaPrioridad3.isEmpty()) {
            for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPrioridad3) {
                acumulado = acumulado.add(postulacionFOVISDTO.getValorSFVSolicitado());
                ResultadoAsignacionDTO resultadoAsignacionDTO = obtenerResultadoAsignacion(postulacionFOVISDTO, valorSFVDisponible, acumulado,
                        PrioridadAsignacionEnum.PRIORIDAD_3);
                listaResultadoAsignacionDTO.add(resultadoAsignacionDTO);
            }
        }
        if (listaPrioridad4 != null && !listaPrioridad4.isEmpty()) {
            for (PostulacionFOVISModeloDTO postulacionFOVISDTO : listaPrioridad4) {
                acumulado = acumulado.add(postulacionFOVISDTO.getValorSFVSolicitado());
                ResultadoAsignacionDTO resultadoAsignacionDTO = obtenerResultadoAsignacion(postulacionFOVISDTO, valorSFVDisponible, acumulado,
                        PrioridadAsignacionEnum.PRIORIDAD_4);
                listaResultadoAsignacionDTO.add(resultadoAsignacionDTO);
            }
        }

        logger.debug("Fin método crearListaResultadoAsignacion");
        return listaResultadoAsignacionDTO;
    }

    /**
     * Método que obtiene un resultado de asignación para una postulación, a partir del valor disponible y acumulado
     *
     * @param postulacionFOVISDTO Datos de la postulación
     * @return El resultado de la asignación
     */
    private ResultadoAsignacionDTO obtenerResultadoAsignacion(PostulacionFOVISModeloDTO postulacionFOVISDTO, BigDecimal valorDisponible,
                                                              BigDecimal acumulado, PrioridadAsignacionEnum prioridad) {
        logger.debug("Inicio método obtenerResultadoAsignacion");

        if (acumulado.compareTo(valorDisponible) <= 0) {
            postulacionFOVISDTO.setResultadoAsignacion(ResultadoAsignacionEnum.ESTADO_ASIGNADO);
            postulacionFOVISDTO.setValorAsignadoSFV(postulacionFOVISDTO.getValorSFVSolicitado());
        } else {
            postulacionFOVISDTO.setResultadoAsignacion(ResultadoAsignacionEnum.ESTADO_CALIFICADO_NO_ASIGNADO);
            postulacionFOVISDTO.setValorAsignadoSFV(BigDecimal.ZERO);
        }

        ResultadoAsignacionDTO resultadoAsignacionDTO = transformarPostulacionFOVIS(postulacionFOVISDTO, prioridad);
        logger.debug("Fin método obtenerResultadoAsignacion");
        return resultadoAsignacionDTO;
    }

    /**
     * Método que crea la lista de resumen de asignación, con base en una lista de resultados de asignación, pasada como parámetro
     *
     * @param listaResultadoAsignacionDTO Lista de resultados de asignación
     * @return La lista de resumen de asignación
     */
    private List<ResumenAsignacionDTO> crearListaResumenAsignacion(List<ResultadoAsignacionDTO> listaResultadoAsignacionDTO) {
        logger.debug("Inicio método crearListaResumenAsignacion");
        List<ResumenAsignacionDTO> listaResumenAsignacionDTO = new ArrayList<ResumenAsignacionDTO>();

        for (ResultadoAsignacionDTO resultadoAsignacionDTO : listaResultadoAsignacionDTO) {
            Boolean existe = Boolean.FALSE;
            for (ResumenAsignacionDTO resumenAsignacionDTO : listaResumenAsignacionDTO) {
                if (resumenAsignacionDTO.getModalidad().equals(resultadoAsignacionDTO.getIdModalidad())) {
                    if (resultadoAsignacionDTO.getResultadoAsignacion().equals(ResultadoAsignacionEnum.ESTADO_ASIGNADO)) {
                        resumenAsignacionDTO.setCantidadHogaresAsignados(resumenAsignacionDTO.getCantidadHogaresAsignados() + 1);
                        resumenAsignacionDTO.setValorSubsidioAsignado(
                                resultadoAsignacionDTO.getValorSFVSolicitado().add(resumenAsignacionDTO.getValorSubsidioAsignado()));
                    }
                    resumenAsignacionDTO.setCantidadHogaresCalificados(resumenAsignacionDTO.getCantidadHogaresCalificados() + 1);
                    existe = Boolean.TRUE;
                }
            }

            if (!existe) {
                ResumenAsignacionDTO resumenAsignacionDTO = new ResumenAsignacionDTO();
                resumenAsignacionDTO.setModalidad(resultadoAsignacionDTO.getIdModalidad());
                if (resultadoAsignacionDTO.getResultadoAsignacion().equals(ResultadoAsignacionEnum.ESTADO_ASIGNADO)) {
                    resumenAsignacionDTO.setCantidadHogaresAsignados(1);
                    resumenAsignacionDTO.setValorSubsidioAsignado(resultadoAsignacionDTO.getValorSFVSolicitado());
                }
                resumenAsignacionDTO.setCantidadHogaresCalificados(1);
                listaResumenAsignacionDTO.add(resumenAsignacionDTO);
            }
        }

        logger.debug("Fin método crearListaResumenAsignacion");
        return listaResumenAsignacionDTO;
    }

    /**
     * Método que transforma un objeto <code>PostulacionFOVISModeloDTO</code> en <code>EjecucionAsignacionFOVISDTO</code>
     *
     * @param postulacionFOVISDTO Objeto a transformar
     * @param prioridad           Prioridad obtenida para el hogar postulado
     * @return El objeto <code>EjecucionAsignacionFOVISDTO</code> equivalente
     */
    private ResultadoAsignacionDTO transformarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO,
                                                               PrioridadAsignacionEnum prioridad) {
        logger.debug("Inicio método consultarHogaresAplicanCalificacionPostulacion");
        PersonaModeloDTO personaJefeHogarDTO = consultarPersonaJefeHogar(postulacionFOVISDTO.getIdJefeHogar());
        ResultadoAsignacionDTO ejecucionAsignacionDTO = new ResultadoAsignacionDTO(postulacionFOVISDTO);

        SolicitudModeloDTO solicitudDTO = consultarSolicitudGlobalPostulacion(postulacionFOVISDTO.getIdPostulacion());
        ejecucionAsignacionDTO.setNumeroRadicacion(solicitudDTO.getNumeroRadicacion());

        ejecucionAsignacionDTO.setTipoIdentificacionJefeHogar(personaJefeHogarDTO.getTipoIdentificacion());
        ejecucionAsignacionDTO.setNumeroIdentificacionJefeHogar(personaJefeHogarDTO.getNumeroIdentificacion());

        StringBuilder nombres = new StringBuilder(personaJefeHogarDTO.getPrimerNombre());

        if (personaJefeHogarDTO.getSegundoNombre() != null) {
            nombres.append(STRING_ESPACIO);
            nombres.append(personaJefeHogarDTO.getSegundoNombre());
        }

        ejecucionAsignacionDTO.setNombreJefeHogar(nombres.toString());

        StringBuilder apellidos = new StringBuilder(personaJefeHogarDTO.getPrimerApellido());

        if (personaJefeHogarDTO.getSegundoApellido() != null) {
            apellidos.append(STRING_ESPACIO);
            apellidos.append(personaJefeHogarDTO.getSegundoApellido());
        }


        String recursoPrioridad = consultarRecursosPrioridadPostulacionAsignacion(postulacionFOVISDTO.getIdPostulacion(), postulacionFOVISDTO.getIdSolicitudAsignacion());

        ejecucionAsignacionDTO.setApellidoJefeHogar(apellidos.toString());
        ejecucionAsignacionDTO.setValorProyectoVivienda(postulacionFOVISDTO.getValorProyectoVivienda());
        ejecucionAsignacionDTO.setPrioridadAsignacion(prioridad);
        ejecucionAsignacionDTO.setResultadoAsignacion(postulacionFOVISDTO.getResultadoAsignacion());
        ejecucionAsignacionDTO.setRecursoPrioridad(recursoPrioridad);
        return ejecucionAsignacionDTO;
    }

    /**
     * Método que consulta una solicitud global de postulación
     *
     * @param idSolicitud Identificador de la postulación
     * @return La información de la solicitud global
     */
    private SolicitudModeloDTO consultarSolicitudGlobalPostulacion(Long idPostulacion) {
        logger.debug("Inicio método consultarSolicitudGlobalPostulacion");
        ConsultarSolicitudGlobalPostulacion service = new ConsultarSolicitudGlobalPostulacion(idPostulacion);
        service.execute();
        logger.debug("Fin método consultarSolicitudGlobalPostulacion");
        return service.getResult();
    }

    /**
     * Método que evalúa una fórmula dinámica
     *
     * @param nombreFormula Nombre de la fórmula dinámica, almacenada en caché
     * @param mapTest       Mapa de variables/valores a ser reemplazados en la fórmula
     * @return El resultado de la evaluación de la fórmula
     */
    private Double evaluarFormula(String nombreFormula, Map<String, String> mapTest) {
        String formula = String.valueOf(CacheManager.getParametro(nombreFormula));
        return RulePerformer.evaluarFormula(formula, mapTest);
    }

    /**
     * Método que invoca el servicio de consulta de hogares que aplican para
     * cálculo de calificación postulación FOVIS, por ciclo de asignación
     *
     * @param idCicloAsignacion Identificador del ciclo de asignación
     * @return La lista de hogares que cumplen con las condiciones para cálculo
     * de puntaje
     */
    private List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionPostulacion(Long idCicloAsignacion, Boolean calificados) {
        ConsultarHogaresAplicanCalificacionPostulacion service = new ConsultarHogaresAplicanCalificacionPostulacion(idCicloAsignacion, calificados);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de hogares que aplican para
     * cálculo de calificación postulación FOVIS, por ciclo de asignación
     *
     * @param idCicloAsignacion Identificador del ciclo de asignación
     * @return La lista de hogares que cumplen con las condiciones para cálculo
     * de puntaje
     */
    private List<PostulacionFOVISModeloDTO> consultarNovedadesPostulacionRangoFecha(Long idCicloAsignacion, String fechaInicial, String fechaFinal) {
        ConsultarNovedadesPostulacionesRango service = new ConsultarNovedadesPostulacionesRango(idCicloAsignacion, fechaInicial, fechaFinal);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de hogares que aplican para
     * cálculo de calificación postulación FOVIS, por ciclo de asignación
     *
     * @param id Identificador del ciclo de asignación
     * @return La lista de hogares que cumplen con las condiciones para cálculo
     * de puntaje
     */
    private Object consultarFechaResultadoEjecucionProgramada(Long id) {
        ConsultarFechaResultadoEjecucionProgramada service = new ConsultarFechaResultadoEjecucionProgramada(id);
        service.execute();
        return service.getResult();
    }


    /**
     * Método que invoca el servicio de consulta de hogares que aplican para
     * cálculo de calificación postulación FOVIS, por ciclo de asignación y que
     * se les registró una novedad de Habilitación de postulación rechazada y
     * en el campo Motivo de habilitación de la postulación tiene el valor Reclamación procedente.
     *
     * @param idCicloAsignacion Identificador del ciclo de asignación
     * @return La lista de hogares que cumplen con las condiciones para cálculo
     * de puntaje
     */
    private List<PostulacionFOVISModeloDTO> consultarHogaresAplicanCalificacionConReclamacionProcedente(Long idCicloAsignacion) {
        ConsultarHogaresAplicanCalificacionConReclamacionProcedente service = new ConsultarHogaresAplicanCalificacionConReclamacionProcedente(
                idCicloAsignacion);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio que consulta a una persona si tuvo
     * una postulacion y perdio la vivienda por imposibilidad de pago
     *
     * @param tipoIdentificacion   Tipo identificación jefe hogar
     * @param numeroIdentificacion Numero identificación jefe Hogar
     * @return Lista de postulaciones que ha tenido la persona
     */
    private List<Integer> consultarPersonaPerdioViviendaImposibilidadPago(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarPersonaPerdioViviendaImposibilidadPago service = new ConsultarPersonaPerdioViviendaImposibilidadPago(
                tipoIdentificacion, numeroIdentificacion);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de información de una persona
     * jefe de hogar, por identificador
     *
     * @param idJefeHogar Identificador único del jefe de hogar
     * @return Objeto <code>PersonaModeloDTO</code> con la información de la
     * persona
     */
    private PersonaModeloDTO consultarPersonaJefeHogar(Long idJefeHogar) {
        logger.debug("Inicio método consultarPersonaJefeHogar");
        ConsultarPersonaJefeHogar service = new ConsultarPersonaJefeHogar(idJefeHogar);
        service.execute();
        logger.debug("Finaliza método consultarPersonaJefeHogar");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de cálculo de número de meses de ahorro
     * programado para un hogar
     *
     * @param idPostulacionFOVIS Identificador único de la postulación
     * @return El número de meses calculado
     */
    private Integer calcularTotalMesesAhorroProgramado(Long idPostulacionFOVIS) {
        logger.debug("Inicio método calcularTotalMesesAhorroProgramado");
        CalcularTotalMesesAhorroProgramado service = new CalcularTotalMesesAhorroProgramado(idPostulacionFOVIS);
        service.execute();
        logger.debug("Finaliza método calcularTotalMesesAhorroProgramado");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de información de un afiliado,
     * por identificación
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @return Objeto <code>ConsultarAfiliadoOutDTO</code> con la información
     * del afiliado
     */
    private ConsultarAfiliadoOutDTO consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia el método consultaAfiliado");
        ConsultarAfiliado service = new ConsultarAfiliado(numeroIdentificacion, tipoIdentificacion, null);
        service.execute();
        logger.debug("Finaliza el método consultaAfiliado");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de la lista de integrantes de
     * un hogar, por jefe de hogar
     *
     * @param tipoIdentificacion   Tipo de identificación del jefe de hogar
     * @param numeroIdentificacion Número de identificación del jefe de hogar
     * @param idPostulacion        Identificador de la postulacion
     * @return La lista de integrantes del hogar
     */
    private List<IntegranteHogarModeloDTO> consultarListaIntegranteHogar(TipoIdentificacionEnum tipoIdentificacion,
                                                                         String numeroIdentificacion, Long idPostulacion) {
        ConsultarListaIntegranteHogar service = new ConsultarListaIntegranteHogar(idPostulacion, numeroIdentificacion, tipoIdentificacion);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de condiciones especiales por
     * persona
     *
     * @param tipoIdentificacion   Tipo de identificación de la persona
     * @param numeroIdentificacion Número de identificación de la persona
     * @param idPostulacion        idPostulacion de la postulacion
     * @return La lista de condiciones especiales
     */
    private List<CondicionEspecialPersonaModeloDTO> consultarCondicionEspecialPersona(TipoIdentificacionEnum tipoIdentificacion,
                                                                                      String numeroIdentificacion, Long idPostulacion) {
        logger.debug("Inicia el método consultarCondicionEspecialPersona");
        ConsultarCondicionEspecialPersona service = new ConsultarCondicionEspecialPersona(numeroIdentificacion, tipoIdentificacion, idPostulacion);
        service.execute();
        logger.debug("Finaliza el método consultarCondicionEspecialPersona");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio que consulta un registro en
     * <code>PersonaDetalle</code>
     *
     * @param tipoIdentificacion   Tipo de identificación de la persona
     * @param numeroIdentificacion Número de identificación de la persona
     * @return Objeto <code>PersonaDetalleModeloDTO</code> con la información
     * del detalle de la persona
     */
    private PersonaDetalleModeloDTO consultarPersonaDetalle(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia el método consultarPersonaDetalle");
        try {
            ConsultarPersonaDetalle service = new ConsultarPersonaDetalle(numeroIdentificacion, tipoIdentificacion);
            service.execute();
            logger.debug("Finaliza el método consultarPersonaDetalle");
            return service.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Invoca el servicio que calcula el total de recursos asociados a la postulación (incluye ahorro previo y recursos complementarios)
     *
     * @param idPostulacion Identificador postulación
     * @return Valor de recursos
     */
    private BigDecimal calcularTotalRecursosHogar(Long idPostulacion) {
        logger.debug("Inicia el método calcularTotalAhorroPrevioHogar");
        CalcularTotalRecursosHogar service = new CalcularTotalRecursosHogar(idPostulacion);
        service.execute();
        logger.debug("Finaliza el método calcularTotalAhorroPrevioHogar");
        return service.getResult();
    }

    /**
     * Invoca el servicio que calcula el total de ahorro previo
     *
     * @param idPostulacion Identificador postulación
     * @return Valor de recursos
     */
    private BigDecimal calcularTotalAhorroPrevio(Long idPostulacion) {
        logger.debug("Inicia el método calcularTotalAhorroPrevio");
        CalcularAhorroPrevio service = new CalcularAhorroPrevio(idPostulacion);
        service.execute();
        logger.debug("Finaliza el método calcularTotalAhorroPrevio");
        return service.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#
     * validarHogarFovis(java.util.List)
     */
    @Asynchronous
    public void validarHogar(Long idCicloAsignacion, Boolean incluyeIngresos, List<SolicitudPostulacionModeloDTO> postulaciones) {
        logger.debug("Inicio servicio validarHogar(List<SolicitudPostulacionModeloDTO>)");
        try {
            List<SolicitudPostulacionFOVISDTO> postulacionesResult = new ArrayList<>();
            for (SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO : postulaciones) {
                ConsultarIntegrantesHogarPostulacion consulatrIntegrantesHogar = new ConsultarIntegrantesHogarPostulacion(
                        solicitudPostulacionModeloDTO.getIdPostulacionFOVIS());
                consulatrIntegrantesHogar.execute();

                List<IntegranteHogarModeloDTO> listaIntegranteHogar = consulatrIntegrantesHogar.getResult();

                SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = new SolicitudPostulacionFOVISDTO();
                solicitudPostulacionFOVISDTO.setNumeroRadicacion(solicitudPostulacionModeloDTO.getNumeroRadicacion());
                solicitudPostulacionFOVISDTO.setTipoTransaccionEnum(solicitudPostulacionModeloDTO.getTipoTransaccion());
                solicitudPostulacionFOVISDTO.setPostulacion(solicitudPostulacionModeloDTO.getPostulacionFOVISModeloDTO());
                solicitudPostulacionFOVISDTO.setIdPostulacion(solicitudPostulacionModeloDTO.getIdPostulacionFOVIS());
                solicitudPostulacionFOVISDTO.setIntegrantesHogar(listaIntegranteHogar);
                solicitudPostulacionFOVISDTO.setIdSolicitud(solicitudPostulacionModeloDTO.getIdSolicitud());
                postulacionesResult.add(solicitudPostulacionFOVISDTO);
            }
            // Se registra el inicio del proceso asincrono
            EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new EjecucionProcesoAsincronoDTO();
            ejecucionProcesoAsincronoDTO.setFechaInicio(Calendar.getInstance().getTime());
            ejecucionProcesoAsincronoDTO.setProcesoRevisado(Boolean.FALSE);
            ejecucionProcesoAsincronoDTO.setTipoProceso(TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
            ejecucionProcesoAsincronoDTO.setIdProceso(idCicloAsignacion);
            Integer totalPostulacion = postulaciones.size();
            ejecucionProcesoAsincronoDTO.setCantidadTotalProceso(totalPostulacion.shortValue());
            ejecucionProcesoAsincronoDTO.setCantidadAvanceProceso((short) NumerosEnterosConstants.CERO);

            RegistrarEjecucionProcesoAsincrono registrarEjecucionProcesoAsincrono = new RegistrarEjecucionProcesoAsincrono(
                    ejecucionProcesoAsincronoDTO);
            registrarEjecucionProcesoAsincrono.execute();
            ejecucionProcesoAsincronoDTO = registrarEjecucionProcesoAsincrono.getResult();

            validarSolicitudesPostulacion(idCicloAsignacion, incluyeIngresos, postulacionesResult, ejecucionProcesoAsincronoDTO.getId());
            logger.debug("Fin servicio validarHogar(List<SolicitudPostulacionModeloDTO>)");
        } catch (Exception e) {
            // No se propaga la excepción por ser un método asíncrono 
            logger.error("Error servicio validarHogar(List<SolicitudPostulacionModeloDTO>)", e);
        }
    }

    /**
     * @param postulacionesResult
     * @throws ExecutionException
     * @throws InterruptedException
     */
    // private void validarSolicitudesPostulacionOriginal(Long idCicloAsignacion, Boolean incluyeIngresos, List<SolicitudPostulacionFOVISDTO> postulacionesResult, Long idEjecucionProcesoAsincrono) {
    //     logger.debug("Inicio servicio validarSolicitudesPostulacion(List<SolicitudPostulacionModeloDTO>)");
    //     // Se verifica si el proceso se cancelo para no actualizar la informacion del ciclo
    //     EjecucionProcesoAsincronoDTO ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
    //             TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
    //     if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
    //             && ejecucionAsincrona.getProcesoCancelado()) {
    //         return;
    //     }
    //     // Creacion tareas paralelas ejecucion validaciones postulacion
    //     List<Callable<List<CruceDetalleDTO>>> tareasParalelas = new LinkedList<>();
    //     for (SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO : postulacionesResult) {
    //         // Se crea la tarea paralela para la ejecucion de validaciones
    //         Callable<List<CruceDetalleDTO>> parallelTask = () -> {
    //             return ejecutarValidacionesSolicitudPostulacion(solicitudPostulacionFOVISDTO, idEjecucionProcesoAsincrono, idCicloAsignacion, incluyeIngresos);
    //         };
    //         tareasParalelas.add(parallelTask);
    //     }
    //     // Lista de cruces resultado validacion
    //     List<CruceDetalleDTO> listCruces = new ArrayList<>();
    //     try {
    //         List<Future<List<CruceDetalleDTO>>> resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);

    //         // Se verifica que el proceso no se haya cancelado
    //         ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
    //                 TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
    //         if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
    //                 && ejecucionAsincrona.getProcesoCancelado()) {
    //             return;
    //         }

    //         // Se revisa las respuestas de las tareas
    //         for (Future<List<CruceDetalleDTO>> future : resultadosFuturos) {
    //             listCruces.addAll(future.get());
    //         }
    //     } catch (Exception e) {
    //     }

    //     List<CruceDetalleDTO> listCrucesRegistro = new ArrayList<>();
    //     List<CruceDetalleDTO> listCrucesSolicitud = new ArrayList<>();
    //     // Se verifica si se registra cruce  o solicitud gestion cruce indicando q no se registro cruce
    //     if (!listCruces.isEmpty()) {
    //         for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
    //             if (cruceDetalleDTO.getCruce().getEstadoCruce().equals(EstadoCruceEnum.NUEVO)) {
    //                 listCrucesRegistro.add(cruceDetalleDTO);
    //             } else {
    //                 listCrucesSolicitud.add(cruceDetalleDTO);
    //             }
    //         }
    //     }

    //     // Se verifica que el proceso no se haya cancelado
    //     ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
    //             TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
    //     if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
    //             && ejecucionAsincrona.getProcesoCancelado()) {
    //         return;
    //     }
    //     // Se registra los cruces
    //     if (!listCrucesRegistro.isEmpty()) {
    //         // Se ejecuta la creacion de los cruces
    //         CrearRegistroCruce crearRegistroCruce = new CrearRegistroCruce(listCrucesRegistro);
    //         crearRegistroCruce.execute();
    //     }
    //     // Se registra la solicitud gestion cruce
    //     if (!listCrucesSolicitud.isEmpty()) {
    //         crearRegistroListaSolicitudGestionCruce(crearListaSolicitudValidacionExitosa(listCrucesSolicitud));
    //     }
    //     // Actualizar finalizacion ejecucion proceso asincrono
    //     ejecucionAsincrona.setFechaFin(Calendar.getInstance().getTime());
    //     ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
    //             ejecucionAsincrona);
    //     actualizarEjecucionProcesoAsincrono.execute();
    //     logger.debug("Fin servicio validarSolicitudesPostulacion(List<SolicitudPostulacionModeloDTO>)");
    // }

    private void validarSolicitudesPostulacion(Long idCicloAsignacion, Boolean incluyeIngresos, List<SolicitudPostulacionFOVISDTO> postulacionesResult, Long idEjecucionProcesoAsincrono) {
        logger.warn("Inicio servicio validarSolicitudesPostulacionV2(List<SolicitudPostulacionModeloDTO>)");
    
        if (postulacionesResult.size() > 500) {
            this.TAMANO_LOTE = 100;
        }
        ExecutorService executor = Executors.newFixedThreadPool(Math.round((float) this.TAMANO_LOTE / 2));
    
        List<CruceDetalleDTO> listCruces = new CopyOnWriteArrayList<>();
        List<Future<List<CruceDetalleDTO>>> futuros = new ArrayList<>();
    
        EjecucionProcesoAsincronoDTO ejecucionAsincrona = consultarUltimaEjecucionAsincrona(
                idCicloAsignacion, TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
        if (ejecucionAsincrona != null && Boolean.TRUE.equals(ejecucionAsincrona.getProcesoCancelado())) {
            return;
        }
    
        try {
            List<Callable<List<CruceDetalleDTO>>> tareitas = postulacionesResult.stream()
                    .map(solicitud -> (Callable<List<CruceDetalleDTO>>) () -> ejecutarValidacionesSolicitudPostulacion(
                            solicitud, idEjecucionProcesoAsincrono, idCicloAsignacion, incluyeIngresos))
                    .collect(Collectors.toList());
    
            for (int i = 0; i < tareitas.size(); i += this.TAMANO_LOTE) {
                List<Callable<List<CruceDetalleDTO>>> lote = tareitas.subList(i, Math.min(i + this.TAMANO_LOTE, tareitas.size()));
                for (Callable<List<CruceDetalleDTO>> tarea : lote) {
                    Future<List<CruceDetalleDTO>> futuro = executor.submit(tarea);
                    futuros.add(futuro);
                }
            }
    
            for (Future<List<CruceDetalleDTO>> resultado : futuros) {
                try {
                    listCruces.addAll(resultado.get());
                } catch (Exception e) {
                    logger.error("Hubo un fallo obteniendo el futuro del procesamiento por lotes", e);
                }
            }
        } catch (Exception e) {
            logger.error("Hubo un fallo en el procesamiento por lotes", e);
        } finally {
            executor.shutdown();
        }

        List<CruceDetalleDTO> listCrucesRegistro = new ArrayList<>();
        List<CruceDetalleDTO> listCrucesSolicitud = new ArrayList<>();
        // Se verifica si se registra cruce  o solicitud gestion cruce indicando q no se registro cruce
        if (!listCruces.isEmpty()) {
            for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
                if (cruceDetalleDTO.getCruce().getEstadoCruce().equals(EstadoCruceEnum.NUEVO)) {
                    listCrucesRegistro.add(cruceDetalleDTO);
                } else {
                    listCrucesSolicitud.add(cruceDetalleDTO);
                }
            }
        }

        // Se verifica que el proceso no se haya cancelado
        ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
                TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
        if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
                && ejecucionAsincrona.getProcesoCancelado()) {
            return;
        }
        // Se registra los cruces
        if (!listCrucesRegistro.isEmpty()) {
            // Se ejecuta la creacion de los cruces
            CrearRegistroCruce crearRegistroCruce = new CrearRegistroCruce(listCrucesRegistro);
            crearRegistroCruce.execute();
        }
        // Se registra la solicitud gestion cruce
        if (!listCrucesSolicitud.isEmpty()) {
            crearRegistroListaSolicitudGestionCruce(crearListaSolicitudValidacionExitosa(listCrucesSolicitud));
        }
        // Actualizar finalizacion ejecucion proceso asincrono
        ejecucionAsincrona.setFechaFin(Calendar.getInstance().getTime());
        ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                ejecucionAsincrona);
        actualizarEjecucionProcesoAsincrono.execute();
        logger.debug("Fin servicio validarSolicitudesPostulacion(List<SolicitudPostulacionModeloDTO>)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#
     * finalizarValidacionesHogarSinCruces()
     */
    @Override
    public void finalizarValidacionesHogarSinCruces() {
        logger.debug("Inicio servicio finalizarValidacionesHogarSinCruces(List<SolicitudPostulacionModeloDTO>)");
        ConsultarSolicitudGestionTipoCruceEstado consultarSolicitudesSinCruces = new ConsultarSolicitudGestionTipoCruceEstado(
                TipoCruceEnum.INTERNO, EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO,
                EstadoSolicitudGestionCruceEnum.CRUCES_PENDIENTE_POR_GESTIONAR);
        consultarSolicitudesSinCruces.execute();
        List<SolicitudGestionCruceDTO> solicitudes = consultarSolicitudesSinCruces.getResult();
        if (solicitudes != null && !solicitudes.isEmpty()) {
            /* Itera para cambiar el estado de las solicitudes Gestión Cruce creadas. */
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : solicitudes) {
                solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CERRADA);
                solicitudGestionCruceDTO.setFechaValidacionCruce(new Date());
            }

            /* Actualiza la solicitud gestion Cruce */
            crearRegistroListaSolicitudGestionCruce(solicitudes);
            /* Actualiza la ejecución del proceso asíncrono a Revisado */
            ConsultarUltimaEjecucionProcesoAsincrono consultarEjecucionActual = new ConsultarUltimaEjecucionProcesoAsincrono(
                    TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
            consultarEjecucionActual.execute();
            EjecucionProcesoAsincronoDTO ejecucionProcesoDTO = consultarEjecucionActual.getResult();
            ejecucionProcesoDTO.setProcesoRevisado(Boolean.TRUE);
            ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                    ejecucionProcesoDTO);
            actualizarEjecucionProcesoAsincrono.execute();
        }
        logger.debug("Fin servicio finalizarValidacionesHogarSinCruces(List<SolicitudPostulacionModeloDTO>)");
    }

    /**
     * Realiza la ejecucion de las validaciones internas a la solicitud de
     * postulacion, validando cada uno de los integrsntes del hogar y el jefe
     * hogar, se realiza el registro del cruce interno
     *
     * @param solicitud Solicitud postulacion a evaluar
     */
    private List<CruceDetalleDTO> ejecutarValidacionesSolicitudPostulacion(SolicitudPostulacionFOVISDTO solicitud,
                                                                           Long idEjecucionProcesoAsincrono, Long idCicloAsignacion, Boolean incluyeIngresos) {
        logger.warn("Inicio servicio ejecutarValidacionesSolicitudPostulacion(SolicitudPostulacionFOVISDTO, Long): List<CruceDetalleDTO>");

        // Se verifica si el proceso se cancelo para no actualizar la informacion del ciclo
        EjecucionProcesoAsincronoDTO ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
                TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
        if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
                && ejecucionAsincrona.getProcesoCancelado()) {
            return null;
        }

        // Lista de cruces internos a crear
        List<CruceDetalleDTO> listCrucesInterno = new ArrayList<>();
        // Map con los datos de validacion
        Map<String, String> datosValidacion = null;
        // Datos identificacion jefe hogar
        TipoIdentificacionEnum tipoIdenJefeHogar = solicitud.getPostulacion().getJefeHogar().getTipoIdentificacion();
        String numeroIdenJefeHogar = solicitud.getPostulacion().getJefeHogar().getNumeroIdentificacion();
        // Numero radicado solicitud - Numero postulacion
        String numeroPostulacion = solicitud.getNumeroRadicacion();
        // Validar jefe Hogar
        datosValidacion = new HashMap<>();
        datosValidacion.put(TIPO_IDENTIFICACION, tipoIdenJefeHogar.name());
        datosValidacion.put(NUMERO_IDENTIFICACION, numeroIdenJefeHogar);
        logger.info("INICIA ejecutarValidacionesSolicitudPostulacion " + listCrucesInterno.size() + "CC " +numeroIdenJefeHogar);
        datosValidacion.put("idPostulacion", solicitud.getIdPostulacion() != null ? String.valueOf(solicitud.getIdPostulacion()) : null);
        logger.warn("ejecutarValidarReglasNegocio JefeHogar");
        // Obtiene el resultado de la validacion
        logger.info("BLOQUE_VALIDACION_323_045 " +BLOQUE_VALIDACION_323_045);
        logger.info("solicitud.getTipoTransaccionEnum().getProceso() " +solicitud.getTipoTransaccionEnum().getProceso());

        List<ValidacionDTO> resultJefeHogar = ejecutarValidarReglasNegocio(BLOQUE_VALIDACION_323_045,
                solicitud.getTipoTransaccionEnum().getProceso(), ClasificacionEnum.JEFE_HOGAR.name(), datosValidacion);
        // Se verifica la validacion de ingresos si se marco en pantalla
        logger.warn("/*/*/*/*/*/ incluyeingrsos"+incluyeIngresos);
        incluyeIngresos = true;
        if (incluyeIngresos) {
            logger.info("ingresa a incluyeIngresos CC " +numeroIdenJefeHogar);
            List<ValidacionDTO> resultIng = ejecutarValidarReglasNegocio("323-045-2",
                    solicitud.getTipoTransaccionEnum().getProceso(), ClasificacionEnum.JEFE_HOGAR.name(), datosValidacion);
            if (resultJefeHogar == null) {
                resultJefeHogar = new ArrayList<>();
            }
            resultJefeHogar.addAll(resultIng);
            logger.info("finaliza resultIng"+ resultIng);
            logger.info("finaliza incluyeIngresos CC "+numeroIdenJefeHogar);
        }
        // Verifica y crea los registros de cruce interno para las validaciones
        // fallidas
        listCrucesInterno.addAll(verificarValidacionCruce(resultJefeHogar, tipoIdenJefeHogar, numeroIdenJefeHogar, numeroPostulacion,
                ClasificacionEnum.JEFE_HOGAR, idEjecucionProcesoAsincrono));
        // Validar integrantes
        for (IntegranteHogarModeloDTO integranteHogar : solicitud.getIntegrantesHogar()) {
            TipoIdentificacionEnum tipoIdenIntegranteHogar = integranteHogar.getTipoIdentificacion();
            String numeroIdenIntegranteHogar = integranteHogar.getNumeroIdentificacion();
            datosValidacion = new HashMap<>();
            datosValidacion.put(TIPO_IDENTIFICACION_AFILIADO, tipoIdenJefeHogar.name());
            datosValidacion.put(NUMERO_IDENTIFICACION_AFILIADO, numeroIdenJefeHogar);
            datosValidacion.put(TIPO_IDENTIFICACION, integranteHogar.getTipoIdentificacion().name());
            datosValidacion.put(NUMERO_IDENTIFICACION, integranteHogar.getNumeroIdentificacion());
            datosValidacion.put(PRIMER_APELLIDO, integranteHogar.getPrimerApellido());
            datosValidacion.put(SEGUNDO_APELLIDO, integranteHogar.getSegundoApellido());
            datosValidacion.put(TIPO_BENEFICIARIO, integranteHogar.getTipoIntegranteHogar().name());
            datosValidacion.put(FECHA_NACIMIENTO, integranteHogar.getFechaNacimiento().toString());
            datosValidacion.put("idPostulacion", solicitud.getIdPostulacion() != null ? String.valueOf(solicitud.getIdPostulacion()) : null);
            logger.warn("ejecutarValidarReglasNegocio IntegranteHogar");
            // Obtiene el resultado de la validacion
            List<ValidacionDTO> result = ejecutarValidarReglasNegocio(BLOQUE_VALIDACION_323_045,
                    solicitud.getTipoTransaccionEnum().getProceso(), integranteHogar.getTipoIntegranteHogar().name(), datosValidacion);
            // Verifica y crea los registros de cruce interno para las
            // validaciones fallidas
            listCrucesInterno.addAll(verificarValidacionCruce(result, tipoIdenIntegranteHogar, numeroIdenIntegranteHogar, numeroPostulacion,
                    integranteHogar.getTipoIntegranteHogar(), idEjecucionProcesoAsincrono));
        }
        // Si la lista de cruces internos es vacia la solicitud de postulacion debe registrar una solicitud gestion cruce con estado sin cruces reportados
        if (listCrucesInterno.isEmpty()) {
            // Se agrega un cruce para control
            CruceDTO cruceDTO = new CruceDTO();
            cruceDTO.setNumeroPostulacion(numeroPostulacion);
            cruceDTO.setEstadoCruce(EstadoCruceEnum.PREVIAMENTE_REPORTADO);
            cruceDTO.setIdEjecucionProcesoAsincrono(idEjecucionProcesoAsincrono);
            cruceDTO.setFechaRegistro(Calendar.getInstance().getTime());
            CruceDetalleDTO cruceDetalleDTO = new CruceDetalleDTO();
            cruceDetalleDTO.setCruce(cruceDTO);
            listCrucesInterno.add(cruceDetalleDTO);
        }
        ejecucionAsincrona = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
                TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
        if (ejecucionAsincrona != null && ejecucionAsincrona.getProcesoCancelado() != null
                && ejecucionAsincrona.getProcesoCancelado()) {
            return null;
        }
        Integer numeroAvance = ejecucionAsincrona.getCantidadAvanceProceso() + NumerosEnterosConstants.UNO;
        ejecucionAsincrona.setCantidadAvanceProceso(numeroAvance.shortValue());
        ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                ejecucionAsincrona);
        actualizarEjecucionProcesoAsincrono.execute();
        logger.warn("Fin servicio ejecutarValidacionesSolicitudPostulacion(SolicitudPostulacionFOVISDTO, Long): List<CruceDetalleDTO>");
        return listCrucesInterno;
    }

    /**
     * Realiza el llamado al servicio de validacion de reglas de negocio
     *
     * @param bloque           Bloque validaciones a ejecutar
     * @param proceso          Proceso de las validaciones
     * @param objetoValidacion Clasificacion a la que aplica la validacion
     * @param datosValidacion  Mapa con los datos necesario para las validaciones
     * @return Lista de validaciones ejecutadas
     */
    private List<ValidacionDTO> ejecutarValidarReglasNegocio(String bloque, ProcesoEnum proceso, String objetoValidacion,
                                                             Map<String, String> datosValidacion) {
        ValidarReglasNegocioFovis validarReglasNegocio = new ValidarReglasNegocioFovis(bloque, proceso, objetoValidacion, datosValidacion);
        validarReglasNegocio.execute();
        return validarReglasNegocio.getResult();
    }

    /**
     * Verifica las validaciones fallidas y realiza la creacion del cruce
     * interno
     *
     * @param resultado  Lista de validaciones ejecutadas
     * @param tipoIden   Tipo identificacion integrante postulacion validado
     * @param numeroIden Numero identificacion integrante postulacion validado
     * @param solicitud  Informacion solicitud postulacion
     */
    private List<CruceDetalleDTO> verificarValidacionCruce(List<ValidacionDTO> resultado, TipoIdentificacionEnum tipoIden,
                                                           String numeroIden, String numeroPostulacion, ClasificacionEnum clasificacion, Long idEjecucionProcesoAsincrono) {
        logger.debug("Inicio Servicio verificarValidacionCruce");
        // Lista de cruces internos para la validacion
        List<CruceDetalleDTO> listCrucesInterno = new ArrayList<>();
        // Obtener las validaciones fallidas
        for (ValidacionDTO validacion : resultado) {
            if (validacion.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA) && validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
                listCrucesInterno.add(crearCruceInterno(tipoIden, numeroIden, validacion.getDetalle(), numeroPostulacion, clasificacion,
                        idEjecucionProcesoAsincrono));
            }
        }
        logger.debug("Fin Servicio verificarValidacionCruce");
        return listCrucesInterno;
    }

    /**
     * Se crea la referencia de los cruces internos
     *
     * @param tipoIden            Tipo identificacion persona cruce
     * @param numeroIden          Numero identificaicon persona cruce
     * @param resultadoValidacion Resultado validacion que origina el cruce
     * @param numeroPostulacion   Numero postulacion cruce
     * @return Cruce detalle con la informacion del cruce
     */
    private CruceDetalleDTO crearCruceInterno(TipoIdentificacionEnum tipoIden, String numeroIden, String resultadoValidacion,
                                              String numeroPostulacion, ClasificacionEnum clasificacion, Long idEjecucionProcesoAsincrono) {
        logger.debug("Inicio Servicio crearCruceInterno");
        // Se consulta la persona objeto del cruce
        PersonaModeloDTO persona = consultarPersona(tipoIden, numeroIden);
        // Se construye el cruce
        CruceDTO cruceDTO = new CruceDTO();
        cruceDTO.setPersona(new PersonaDTO(persona.convertToPersonaEntity()));
        cruceDTO.setNumeroPostulacion(numeroPostulacion);
        cruceDTO.setEstadoCruce(EstadoCruceEnum.NUEVO);
        cruceDTO.setIdEjecucionProcesoAsincrono(idEjecucionProcesoAsincrono);
        cruceDTO.setFechaRegistro(Calendar.getInstance().getTime());

        CruceDetalleDTO cruceDetalleDTO = new CruceDetalleDTO();
        cruceDetalleDTO.setResultadoValidacion(resultadoValidacion);
        cruceDetalleDTO.setClasificacion(clasificacion);
        cruceDetalleDTO.setCruce(cruceDTO);

        logger.debug("Fin Servicio crearCruceInterno");
        return cruceDetalleDTO;
    }

    /**
     * Crea las solicitudes de gestion de cruce para las postulaciones que no generadron cruce por validaciones
     *
     * @param listCruces Lista de cruces de control para la obtencion de los datos de la postulacion
     * @return Lista de solicitudes a registrar
     */
    private List<SolicitudGestionCruceDTO> crearListaSolicitudValidacionExitosa(List<CruceDetalleDTO> listCruces) {
        logger.debug("Inicio servicio crearListaSolicitudValidacionExitosa");
        List<SolicitudGestionCruceDTO> listSolicitudGestion = new ArrayList<>();
        // Se itera los cruces generados para crear la solicitud gestion cruce
        for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
            SolicitudPostulacionModeloDTO solPostu = consultarSolicitudPostulacionNumeroPostulacion(
                    cruceDetalleDTO.getCruce().getNumeroPostulacion());
            SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
            solicitudGestionCruceDTO.setIdSolicitudPostulacion(solPostu.getIdSolicitudPostulacion());
            solicitudGestionCruceDTO.setTipoCruce(TipoCruceEnum.INTERNO);
            solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO);
            solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CERRADA);
            solicitudGestionCruceDTO.setFechaValidacionCruce(new Date());
            listSolicitudGestion.add(solicitudGestionCruceDTO);
        }
        logger.debug("Fin servicio crearListaSolicitudValidacionExitosa");
        return listSolicitudGestion;
    }

    /**
     * Invoca el servicio de consulta de persona
     *
     * @param tipoIdentificacion   Tipo identificacion persona buscada
     * @param numeroIdentificacion Numero identificacion persona buscada
     * @return Informacion persona
     */
    private PersonaModeloDTO consultarPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia método consultarPersona(TipoIdentificacionEnum, String)");
        ConsultarDatosPersona consultarPersona = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarPersona.execute();
        logger.debug("Finaliza método consultarPersona(TipoIdentificacionEnum, String)");
        return consultarPersona.getResult();
    }

    /**
     * Método que realiza el llamado al servicio que guarda el resultado de la asignación
     *
     * @param solicitudAsignacion DTO con la informacion de la asignación a almacenar
     * @return DTO con la información de la solicitud de asignación procesada
     */
    private SolicitudAsignacionFOVISModeloDTO guardarResultadoAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        logger.debug("Inicia servicio guardarResultadoAsignacion");
        GuardarResultadoAsignacion service = new GuardarResultadoAsignacion(solicitudAsignacion);
        service.execute();
        SolicitudAsignacionFOVISModeloDTO solicitud = service.getResult();
        logger.debug("Finaliza servicio guardarResultadoAsignacion");
        return solicitud;
    }

    /**
     * Método que hace la peticion REST al servicio de obtener tarea activa para
     * posteriomente finalizar el proceso de Afiliación personas presencial
     *
     * @param idInstanciaProceso <code>String</code> El identificador de la Instancia Proceso
     *                           Afiliacion de la Persona
     * @return <code>Long</code> El identificador de la tarea Activa
     */
    private Long consultarTareaActiva(Long idInstanciaProceso) {
        logger.debug("Inicia consultarTareaAfiliacionPersonas( idInstanciaProceso )");
        Long idTarea = null;
        Map<String, Object> mapResult = new HashMap<String, Object>();
        ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(idInstanciaProceso);
        obtenerTareaActivaService.execute();
        mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
        logger.debug("Finaliza consultarTareaActiva( idInstanciaProceso )");
        idTarea = ((Integer) mapResult.get("idTarea")).longValue();
        return idTarea;
    }

    /**
     * Método que termina una tarea del BPM
     *
     * @param idTarea es el identificador de la tarea
     * @param params  Son los parámetros de la tarea
     */
    private void terminarTarea(Long idTarea, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
        terminarTarea.execute();
    }

    /**
     * Metodo encargado de generar la carta de asignacion para la postulacion procesada
     *
     * @param idPostulacion identificador de la postulacion para que se creara la carta de asignacion
     * @return <code>String</code> El identificador del documento almacenado para asociar a la postulacion
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     */
    private Vector<Object> generarCartaAsignacionPostulacion(Long idPostulacion, String instanciaProceso) {

        logger.debug("Inicia generarCartaAsignacionPostulacion( idInstanciaProceso )");
        Vector<Object> valoresCarta = new Vector<Object>();

        String idECM = null;
        Map<String, Object> params = new HashMap<String, Object>();
        //        if (notificacion.getParams() != null) {
        //            for (Entry<String, String> entry : notificacion.getParams().entrySet()) {
        //                params.put(entry.getKey(), entry.getValue());
        //            }
        //        }

        // se consulta el comunicado con todas sus variables resueltas tanto en
        // el archivo ajunto como en el mensaje del correo
        PlantillaComunicado plaCom = new PlantillaComunicado();
        if (idPostulacion != null) {
            // de acuerdo al id de la postulacion para la creacion de la tabla
            plaCom = resolverPlantillaVariablesComunicadoPorSolicitud(EtiquetaPlantillaComunicadoEnum.CRT_ASIG_FOVIS, idPostulacion,
                    params);
        }

        logger.info("plaCom " +plaCom);

        if (plaCom.getEncabezado() == null && plaCom.getCuerpo() == null && plaCom.getPie() == null) {
            logger.error("Error la plantilla del comunicado no tiene datos");
            logger.debug("Se asume que no tiene documento adjunto");
        } else {

            List<Float> magenesX = new ArrayList<>();
            magenesX.add(56f);
            magenesX.add(56f);
            List<Float> magenesY = new ArrayList<>();
            magenesY.add(40f);
            magenesY.add(40f);

            InformacionConvertDTO infoConv = new InformacionConvertDTO();
            infoConv.setHtmlHeader(plaCom.getEncabezado());
            infoConv.setHtmlContenido(plaCom.getCuerpo());
            infoConv.setHtmlFooter(plaCom.getPie());
            // infoConv.setHtmlSello(htmlSello);
            infoConv.setMargenesx(magenesX);
            infoConv.setMargenesy(magenesY);
            infoConv.setAltura(100f);
            infoConv.setRequiereSello(true);
            logger.info("cuerpo " +plaCom.getCuerpo());
            logger.info("encabezado " +plaCom.getEncabezado());

            valoresCarta.add(0, infoConv);
            logger.info("valoresCarta " +valoresCarta);
            // se convierte a pdf la plantilla de comunicado
            byte[] bytes = convertHTMLtoPDF(infoConv);

            if (bytes == null) {
                logger.error("Error la plantilla del comunicado no fue convertida a PDF");
                logger.debug("Finaliza generarCartaAsignacionPostulacion(NotificacionParametrizadaDTO)");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
            }

            logger.info("bytes " + bytes);

            InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
            infoArch.setDataFile(bytes);
            infoArch.setDescription(ProcesoEnum.ASIGNACION_FOVIS.getDescripcion());
            infoArch.setDocName("Carta_asignacion_FOVIS_comunicado" + idPostulacion.toString() + ".pdf");
            infoArch.setFileName("Carta_asignacion_FOVIS_comunicado" + idPostulacion.toString() + ".pdf");
            infoArch.setFileType("application/pdf");
            // validar campos           
            infoArch.setProcessName(ProcesoEnum.ASIGNACION_FOVIS.toString());
            infoArch.setIdInstanciaProceso(instanciaProceso);
            // se realiza el upload del archivo al ECM
            idECM = almacenarArchivo(infoArch);
            valoresCarta.add(1, idECM);

        }
        logger.debug("Finaliza generarCartaAsignacionPostulacion( idInstanciaProceso )");
        return valoresCarta;
    }

    /**
     * Metodo encargado de generar el documento consolidado con todas las cartas de asignacion generadas
     *
     * @param InformacionConvertDTO DTO con la informacion para generar el consolidado
     * @return <code>String</code> El identificador del documento almacenado para asociar a la postulacion
     * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
     */
    private String generarConsolidadoCartasAsignacion(InformacionConvertDTO consolidadoCartasGeneradas, Long idSolicitud,
                                                      String instanciaProceso) {

        logger.debug("Inicia generarConsolidadoCartasAsignacion( infoConv )");

        List<Float> magenesX = new ArrayList<Float>();
        magenesX.add(56f);
        magenesX.add(56f);
        List<Float> magenesY = new ArrayList<Float>();
        magenesY.add(40f);
        magenesY.add(40f);
        consolidadoCartasGeneradas.setMargenesx(magenesX);
        consolidadoCartasGeneradas.setMargenesy(magenesY);
        consolidadoCartasGeneradas.setAltura(100f);
        consolidadoCartasGeneradas.setRequiereSello(false);
        // se convierte a pdf la plantilla de comunicado
        byte[] bytes = convertHTMLtoPDF(consolidadoCartasGeneradas);
        String base64Pdf = Base64.getEncoder().encodeToString(bytes);
        System.out.println("PDF codificado en Base64:");
        System.out.println(base64Pdf);
        System.out.println("TERMINA PDF codificado en Base64:");
        if (bytes == null) {
            logger.error("Error generando el consolidado de cartas de asignacion a PDF");
            logger.debug("Finaliza generarConsolidadoCartasAsignacion(InformacionConvertDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CREACION_PDF);
        }
        //Se consulta el ciclo asociado a la solicitud de asignacion
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacion = consultarSolicitudAsignacionPorId(idSolicitud);

        ConsultarCicloAsignacion serviceCicloAsignacion = new ConsultarCicloAsignacion(solicitudAsignacion.getIdCicloAsignacion());
        serviceCicloAsignacion.execute();
        CicloAsignacionModeloDTO cicloAsignacion = serviceCicloAsignacion.getResult();

        InformacionArchivoDTO infoArch = new InformacionArchivoDTO();
        infoArch.setDataFile(bytes);
        infoArch.setDescription(ProcesoEnum.ASIGNACION_FOVIS.getDescripcion());
        infoArch.setDocName("Consolidado_Cartas_Asignacion_FOVIS" + cicloAsignacion.getNombre() + ".pdf");
        infoArch.setFileName("Consolidado_Cartas_Asignacion_FOVIS" + cicloAsignacion.getNombre() + ".pdf");
        infoArch.setFileType("application/pdf");
        //validar campos
        infoArch.setProcessName(ProcesoEnum.ASIGNACION_FOVIS.toString());
        infoArch.setIdInstanciaProceso(instanciaProceso);
        // se realiza el upload del archivo al ECM
        String idDocumentoConsolidado = almacenarArchivo(infoArch);
        logger.debug("Inicia generarConsolidadoCartasAsignacion()");
        return idDocumentoConsolidado;
    }

    /**
     * Método que se encarga de cosumir el cliente que consulta la planilla con
     * las variables resueltas
     *
     * @param etiquetaPlantillaComunicadoEnum
     * @param idInstancia
     * @param map
     */
    private PlantillaComunicado resolverPlantillaVariablesComunicadoPorSolicitud(
            EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolicitud, Map<String, Object> map) {
        ResolverPlantillaVariablesComunicadoPorSolicitud resolverCom = new ResolverPlantillaVariablesComunicadoPorSolicitud(idSolicitud,
                etiquetaPlantillaComunicadoEnum, map);
        PlantillaComunicado plaCom = new PlantillaComunicado();
        resolverCom.execute();
        plaCom = (PlantillaComunicado) resolverCom.getResult();
        return plaCom;
    }

    /**
     * Método que se encarga de cosumir el cliente convierte el html a pdf
     *
     * @param objInformacionConvertDTO
     * @return
     */
    private byte[] convertHTMLtoPDF(InformacionConvertDTO objInformacionConvertDTO) {
        ConvertHTMLtoPDF convertHTMLtoPDF = new ConvertHTMLtoPDF(objInformacionConvertDTO);
        convertHTMLtoPDF.execute();
        byte[] bytes = (byte[]) convertHTMLtoPDF.getResult();
        String resultado = new String(bytes);
        System.out.println("Contenido del byte[]: convertHTMLtoPDF " + resultado);
        return bytes;
    }

    /**
     * Almacena el archivo en el ECM
     *
     * @param infoFile
     * @return String con el ID del archivo creado en el ECM
     */
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();
        // String idECM = (String) almacenarArchivo.getResult();
        String idECM = (String) almacenarArchivo.getResult().getIdentificadorDocumento();
        return idECM;
    }

    /**
     * Elimina el archivo creado en el ECM
     *
     * @param infoFile
     * @return DTO con la informacion del archivo
     */
    private void eliminarArchivo(String identificadorArchivo) {
        EliminarArchivo eliminarArchivo = new EliminarArchivo(identificadorArchivo);
        eliminarArchivo.execute();
    }

    /**
     * Método que realiza el llamado al servicio que consulta la solicitud de asignacion por identificador
     *
     * @param idSolicitud identificador de la solicitud
     * @return DTO con la información de la solicitud de asignación
     */
    private SolicitudAsignacionFOVISModeloDTO consultarSolicitudAsignacionPorId(Long idSolicitud) {
        logger.debug("Inicia servicio consultarSolicitudAsignacionPorId(idSolicitud)");
        ConsultarSolicitudAsignacion serviceSolicitudAsignacion = new ConsultarSolicitudAsignacion(idSolicitud);
        serviceSolicitudAsignacion.execute();
        SolicitudAsignacionFOVISModeloDTO solicitudAsignacion = serviceSolicitudAsignacion.getResult();
        logger.debug("Finaliza servicio consultarSolicitudAsignacionPorId(idSolicitud)");
        return solicitudAsignacion;
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de modalidades FOVIS
     *
     * @return Lista de <code>ParametrizacionModalidadModeloDTO</code> con la parametrización obtenida
     */
    private List<ParametrizacionModalidadModeloDTO> consultarParametrizacionModalidades() {
        logger.debug("Inicia el método consultarParametrizacionModalidades");
        ConsultarParametrizacionModalidades service = new ConsultarParametrizacionModalidades();
        service.execute();
        logger.debug("Finaliza el método consultarParametrizacionModalidades");
        return service.getResult();
    }

    /**
     * Método que busca la parametrización por modalidad FOVIS, dentro de una lista
     *
     * @param listaParametrizacionModalidadDTO La lista a iterar para la búsqueda
     * @param modalidad                        La modalidad a buscar
     * @return Objeto <code>ParametrizacionModalidadModeloDTO</code> con la parametrización de la modalidad
     */
    private ParametrizacionModalidadModeloDTO buscarParametrizacionModalidad(
            List<ParametrizacionModalidadModeloDTO> listaParametrizacionModalidadDTO, ModalidadEnum modalidad) {
        logger.debug("Inicia el método buscarParametrizacionModalidad");

        for (ParametrizacionModalidadModeloDTO parametrizacionModalidadDTO : listaParametrizacionModalidadDTO) {
            if (parametrizacionModalidadDTO.getNombre().equals(modalidad)) {
                logger.debug("Finaliza el método buscarParametrizacionModalidad");
                return parametrizacionModalidadDTO;
            }
        }

        return null;
    }

    /**
     * Método que invoca el servicio de consulta de ahorro previo, por postulación y tipo de ahorro
     *
     * @param idPostulacion Identificador único de la postulación
     * @param tipoAhorro    Tipo de ahorro
     * @return La información del ahorro previo
     */
    private AhorroPrevioModeloDTO consultarAhorroPrevio(Long idPostulacion, TipoAhorroPrevioEnum tipoAhorro) {
        logger.debug("Inicia el método consultarAhorroPrevio");
        ConsultarAhorroPrevio service = new ConsultarAhorroPrevio(tipoAhorro, idPostulacion);
        service.execute();
        logger.debug("Finaliza el método consultarAhorroPrevio");
        return service.getResult();
    }

    /**
     * Metodo encargado de asignar a las postulaciones con estado calificado no asignado al siguiente ciclo de asignación
     *
     * @param solicitudAsignacion solicitud de asignacion procesada
     */
    private void asignarPostulacionesSiguienteCiclo(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        logger.debug("Inicia el método asignarPostulacionesSiguienteCiclo");
        //Se asignan los hogares en estado calificado no asignado al siguiente ciclo
        ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(solicitudAsignacion.getIdCicloAsignacion());
        serviceConsultaCicloAsignacion.execute();
        CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();

        ConsultarCicloSucesor consultarCicloSucesor = new ConsultarCicloSucesor(cicloAsignacion.getCicloPredecesor(),
                cicloAsignacion.getFechaFin(), cicloAsignacion.getIdCicloAsignacion());
        consultarCicloSucesor.execute();
        CicloAsignacionModeloDTO cicloSucesor = consultarCicloSucesor.getResult();

        //Se consultan las postulaciones asociadas a la solicitud de asignacion que quedaron con el  resultado de asignación calificado no asignado
        ConsultarPostulacionesSolicitudPorResultado servicePostulacionesSiguienteCiclo = new ConsultarPostulacionesSolicitudPorResultado(
                solicitudAsignacion.getIdSolicitudAsignacion(), ResultadoAsignacionEnum.ESTADO_CALIFICADO_NO_ASIGNADO);
        servicePostulacionesSiguienteCiclo.execute();

        List<PostulacionFOVISModeloDTO> listaPostulacionesSiguienteCiclo = servicePostulacionesSiguienteCiclo.getResult();
        for (PostulacionFOVISModeloDTO postulacion : listaPostulacionesSiguienteCiclo) {
            //El campo Ciclo de asignación de cada hogar se cambia el valor por el Ciclo de asignación sucesor.
            if (cicloSucesor != null && cicloSucesor.getIdCicloAsignacion() != null) {
                postulacion.setIdCicloAsignacion(cicloSucesor.getIdCicloAsignacion());
                // Mantis:0244896
                // Se ajusta los datos de calificacion y asignación por el cambio de ciclo
                postulacion.setPuntaje(null);
                postulacion.setFechaCalificacion(null);
                postulacion.setPrioridadAsignacion(null);
                postulacion.setValorAsignadoSFV(null);
                postulacion.setIdDocumento(null);
                postulacion.setIdSolicitudAsignacion(null);
            }
            CrearActualizarPostulacionFOVIS servicePostulacion = new CrearActualizarPostulacionFOVIS(postulacion);
            servicePostulacion.execute();
        }

        // Se actualiza el estado del ciclo de asignación a estado Cerrado. 
        cicloAsignacion.setEstadoCicloAsignacion(EstadoCicloAsignacionEnum.CERRADO);
        RegistrarCicloAsignacion serviceCicloAsignacion = new RegistrarCicloAsignacion(cicloAsignacion);
        serviceCicloAsignacion.execute();

        logger.debug("Finaliza el método asignarPostulacionesSiguienteCiclo");
    }

    /* (non-Javadoc)
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#aceptarResultadoCruceInterno(com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void aceptarResultadoCruceInterno(AsignaResultadoCruceDTO asignarResultadoCruce, UserDTO userDTO) {
        logger.debug("Inicia método aceptarResultadoCruceInterno(AsignaResultadoCruceDTO)");
        try {
            // Se obtiene la lista de cruces seleccionados por el usuario
            List<CruceDetalleDTO> listCrucesSeleccionados = asignarResultadoCruce.getListCruces();
            // Se obtienen las solicitudes de gestion cruce y se almacenan
            List<SolicitudGestionCruceDTO> listSolicitudGestionCruce = crearRegistroListaSolicitudGestionCruce(
                    getSolictudesGestionCruce(listCrucesSeleccionados));
            // Se realiza la creacion de las tareas
            dispersarTareasCruceInterno(listSolicitudGestionCruce, userDTO, asignarResultadoCruce.getListUsuarios());
            // Actualizar ejecucion proceso asincrono indicado que fue revisado
            EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new EjecucionProcesoAsincronoDTO();
            ejecucionProcesoAsincronoDTO.setId(asignarResultadoCruce.getIdProcesoAsincrono());
            ejecucionProcesoAsincronoDTO.setProcesoRevisado(Boolean.TRUE);
            ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                    ejecucionProcesoAsincronoDTO);
            actualizarEjecucionProcesoAsincrono.execute();
            finalizarValidacionesHogarSinCruces();
            logger.debug("Finaliza método aceptarResultadoCruceInterno(AsignaResultadoCruceDTO)");
        } catch (Exception e) {
            logger.error("Error servicio aceptarResultadoCruceInterno(AsignaResultadoCruceDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void noAceptarResultadoCruceInterno(AsignaResultadoCruceDTO asignarResultadoCruce) {
        logger.debug("Inicia método noAceptarResultadoCruceInterno(AsignaResultadoCruceDTO)");
        // Actualizar ejecucion proceso asincrono indicado que fue revisado
        EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new EjecucionProcesoAsincronoDTO();
        ejecucionProcesoAsincronoDTO.setId(asignarResultadoCruce.getIdProcesoAsincrono());
        ejecucionProcesoAsincronoDTO.setProcesoRevisado(Boolean.TRUE);
        ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                ejecucionProcesoAsincronoDTO);
        actualizarEjecucionProcesoAsincrono.execute();
        finalizarValidacionesHogarSinCruces();
        logger.debug("Finaliza método noAceptarResultadoCruceInterno(AsignaResultadoCruceDTO)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#generarDocumentoSoporteActaAsignacion(java.lang.Long)
     */
    @Override
    public InformacionDocumentoActaAsignacionDTO consultarInformacionDocumentoSoporteActaAsignacion(Long idSolicitudGlobal) {
        logger.debug("Inicia método generarDocumentoSoporteActaAsignacion(idSolicitudGlobal)");
        ConsultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal service = new ConsultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal(
                idSolicitudGlobal);
        service.execute();
        InformacionDocumentoActaAsignacionDTO informacionActa = service.getResult();
        logger.debug("Finaliza método generarDocumentoSoporteActaAsignacion(idSolicitudGlobal)");
        return informacionActa;
    }

    /**
     * Método que invoca el servicio de almacenamiento de una postulación FOVIS
     *
     * @param postulacionFOVISDTO Información de la postulación a actualizar
     * @return Los datos actualizados de la postulación
     */
    private PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Inicia el método crearActualizarPostulacionFOVIS");
        CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarPostulacionFOVIS");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de generación de número de radicación
     *
     * @param idSolicitud          El identificador de la solicitud
     * @param sedeCajaCompensacion Sede de la caja
     */
    private void generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.debug("Inicia generarNumeroRadicado");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        logger.debug("Finaliza generarNumeroRadicado");
    }

    /**
     * Método que invoca el servicio de creación o actualización de un registro
     * en la tabla <code>Solicitud</code>
     *
     * @param solicitudDTO La información del registro a actualizar
     * @return El identificador del registro actualizado
     */
    private Long guardarSolicitudGlobal(SolicitudModeloDTO solicitudDTO) {
        logger.debug("Inicio de método guardarSolicitudGlobal");
        GuardarSolicitudGlobal service = new GuardarSolicitudGlobal(solicitudDTO);
        service.execute();
        logger.debug("Fin de método guardarSolicitudGlobal");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de una solicitud global
     *
     * @param idSolicitudGlobal Identificador de la solicitud global
     * @return La información de la solicitud
     */
    private SolicitudModeloDTO consultarSolicitudGlobal(Long idSolicitudGlobal) {
        logger.debug("Inicio de método guardarSolicitudGlobal");
        ConsultarSolicitudGlobal service = new ConsultarSolicitudGlobal(idSolicitudGlobal);
        service.execute();
        logger.debug("Fin de método guardarSolicitudGlobal");
        return service.getResult();
    }

    /**
     * Método que hace invoca el servicio de ejecución de asignación de usuarios CCF
     *
     * @param sedeCaja    Identificador de la sede de la caja
     * @param procesoEnum Proceso
     * @return Usuario asignado automáticamente
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion");
        return ejecutarAsignacion.getResult();
    }

    /**
     * Método encargado de invocar el servicio de inicio de proceso BPM
     *
     * @param procesoEnum       Proceso que se desea iniciar
     * @param parametrosProceso Parámetros del proceso
     * @return Identificador de la instancia del proceso.
     */
    private Long iniciarProceso(ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso) {
        logger.debug("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
        IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesoNovedadService.execute();
        Long idInstanciaProcesoNovedad = iniciarProcesoNovedadService.getResult();
        logger.debug("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
        return idInstanciaProcesoNovedad;
    }

    /**
     * Método que realiza el llamado al servicio que guarda la solicitud de asigancion
     *
     * @param solicitudAsignacion DTO con la informacion de la asignación a almacenar
     * @return DTO con la información de la solicitud de asignación procesada
     */
    private SolicitudAsignacionFOVISModeloDTO guardarActualizarSolicitudAsignacion(SolicitudAsignacionFOVISModeloDTO solicitudAsignacion) {
        logger.debug("Inicia servicio guardarSolicitudAsignacion");
        GuardarSolicitudAsignacion service = new GuardarSolicitudAsignacion(solicitudAsignacion);
        service.execute();
        SolicitudAsignacionFOVISModeloDTO solicitud = service.getResult();
        logger.debug("Finaliza servicio guardarSolicitudAsignacion");
        return solicitud;
    }

    /**
     * Obtiene las solicitudes gestion cruce para asignar
     *
     * @param listCruces Lista de cruces realizados
     * @return Lista de solicitudes gestion cruce
     */
    private List<SolicitudGestionCruceDTO> getSolictudesGestionCruce(List<CruceDetalleDTO> listCruces) {
        // Result solicitudes gestion creadas
        List<SolicitudGestionCruceDTO> listResult = new ArrayList<>();
        // Mapa con los cruces por numero de postulacion
        Map<String, List<CruceDetalleDTO>> mapCruce = new HashMap<>();
        // Lista postulaciones con cruces
        List<String> listNumerosPostulacion = new ArrayList<>();
        for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
            // Se verifican los cruces por solicitud
            String nroPostulacion = cruceDetalleDTO.getCruce().getNumeroPostulacion();
            if (nroPostulacion != null && !listNumerosPostulacion.contains(nroPostulacion)) {
                listNumerosPostulacion.add(nroPostulacion);

                List<CruceDetalleDTO> listCrucePost = new ArrayList<>();
                listCrucePost.add(cruceDetalleDTO);
                mapCruce.put(nroPostulacion, listCrucePost);
            } else if (mapCruce.containsKey(nroPostulacion)) {
                mapCruce.get(nroPostulacion).add(cruceDetalleDTO);
            }
        }
        listResult.addAll(getSolicitudGestionCrucePorPostulacion(listNumerosPostulacion, mapCruce));
        return listResult;
    }

    /**
     * Obtiene la lista de solicitudes sde gestion de curces por numeros de postulacion de los cruces nuevos
     *
     * @param idCargue               Identificador cargue
     * @param listNumerosPostulacion Numeros postulacion cruces nuevos
     * @return Lista de solicitudes a crear
     */
    private List<SolicitudGestionCruceDTO> getSolicitudGestionCrucePorPostulacion(List<String> listNumerosPostulacion,
                                                                                  Map<String, List<CruceDetalleDTO>> mapCruce) {
        // Lista de solicitudes
        List<SolicitudGestionCruceDTO> listSolicitudGestion = new ArrayList<>();
        // Se crea la solicitud de gestion de cruce por postulacion y se le asocian los cruces 
        for (String nroPostulacion : listNumerosPostulacion) {
            SolicitudPostulacionModeloDTO solPostu = consultarSolicitudPostulacionNumeroPostulacion(nroPostulacion);
            List<CruceDetalleDTO> listCruces = mapCruce.get(nroPostulacion);
            SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
            solicitudGestionCruceDTO.setIdSolicitudPostulacion(solPostu.getIdSolicitudPostulacion());
            solicitudGestionCruceDTO.setListCrucesAsociados(listCruces);
            solicitudGestionCruceDTO.setTipoCruce(TipoCruceEnum.INTERNO);
            solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CRUCES_PENDIENTE_POR_GESTIONAR);
            solicitudGestionCruceDTO.setFechaValidacionCruce(new Date());
            listSolicitudGestion.add(solicitudGestionCruceDTO);
        }
        return listSolicitudGestion;
    }

    /**
     * Consulta la informacion de la solicitud de postulacion con el numero de postulacion
     *
     * @param nroPostulacion Numero postulacion a buscar
     * @return Numero de postulacion registrado
     */
    private SolicitudPostulacionModeloDTO consultarSolicitudPostulacionNumeroPostulacion(String nroPostulacion) {
        ConsultarSolicitudPostulacionPorNumeroPostulacion consultarSolicitudPostulacionPorNumeroPostulacion = new ConsultarSolicitudPostulacionPorNumeroPostulacion(
                nroPostulacion);
        consultarSolicitudPostulacionPorNumeroPostulacion.execute();
        return consultarSolicitudPostulacionPorNumeroPostulacion.getResult();
    }

    /**
     * Crea el registro de las solicitudes de gestion de cruces
     *
     * @param listSolicitudes Lista solicitudes a crear
     * @return Lista solicitudes creadas
     */
    private List<SolicitudGestionCruceDTO> crearRegistroListaSolicitudGestionCruce(List<SolicitudGestionCruceDTO> listSolicitudes) {
        CrearRegistroListaSolicituGestionCruce crearRegistroSolicituGestionCruce = new CrearRegistroListaSolicituGestionCruce(listSolicitudes);
        crearRegistroSolicituGestionCruce.execute();
        return crearRegistroSolicituGestionCruce.getResult();
    }

    /**
     * Realiza el proceso de asignacion de las solicitudes de posutlacion que tuvieron cruces
     *
     * @param listUsuarios
     * @param listSolicitudes
     */
    private void asignarTareaGestionCruce(List<String> listUsuarios, List<SolicitudGestionCruceDTO> listSolicitudes,
                                          UserDTO userDTO) throws Exception {
        List<UserDTO> usuariosDTO = new ArrayList<>();
        if (listUsuarios != null && !listUsuarios.isEmpty()) {
            for (String user : listUsuarios) {
                UserDTO usuario = new UserDTO();
                usuario.setNombreUsuario(user);
                usuariosDTO.add(usuario);
            }
            String ultimoUsuario = null;
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolicitudes) {
                // Se obtiene el usuario a asignar
                UserDTO usuarioAsignar = this.asignacionConsecutivaPorTurnos(usuariosDTO, ultimoUsuario);
                ultimoUsuario = usuarioAsignar.getNombreUsuario();
                // Almacena en Solicitud
                SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
                solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                solicitudDTO.setFechaRadicacion(new Date().getTime());
                solicitudDTO.setFechaCreacion(new Date().getTime());
                solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                solicitudDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS);
                solicitudDTO.setClasificacion(ClasificacionEnum.HOGAR);
                solicitudDTO.setDestinatario(ultimoUsuario);
                solicitudDTO.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                Long idSolicitudGlobal = guardarSolicitudGlobal(solicitudDTO);
                generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());
                solicitudDTO = consultarSolicitudGlobal(idSolicitudGlobal);
                solicitudGestionCruceDTO.convertToDTO(solicitudDTO.convertToSolicitudEntity());
                // Se actualiza la solicitud gestion cruce con lam solicitud
                // global
                actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
                // Se crea la tarea para realizar la gestion del cruce
                Map<String, Object> parametrosProceso = new HashMap<String, Object>();
                parametrosProceso.put(ID_SOLICITUD, solicitudGestionCruceDTO.getIdSolicitud());
                parametrosProceso.put(USUARIO_BACK, ultimoUsuario);
                parametrosProceso.put(NUMERO_RADICADO, solicitudGestionCruceDTO.getNumeroRadicacion());
                parametrosProceso.put(TIPO_CRUCE, 2);
                Long idInstanciaProceso = iniciarProceso(ProcesoEnum.CRUCES_POSTULACION_FOVIS, parametrosProceso);
                solicitudDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
                guardarSolicitudGlobal(solicitudDTO);

                // Guardar Datos temporales
                SolicitudPostulacionModeloDTO spo = consultarSolicitudPostulacionById(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
                ConsultarPostulacionTemporal consultarPostulacionTemporal = new ConsultarPostulacionTemporal(spo.getIdSolicitud());
                consultarPostulacionTemporal.execute();
                SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal.getResult();
                solicitudGestionCruceDTO.setDatosPostulacionFovis(solicitudTemporal);
                guardarDatosTemporalGestionCruce(solicitudGestionCruceDTO);
            }
        }
    }

    /**
     * Realiza el proceso de asignacion de las solicitudes de posutlacion que tuvieron cruces
     *
     * @param solicitudesPostulacion
     * @param usuarioAsignar
     */
    private List<AsignacionTurnosDTO> dispersarTareaGestionCruce(List<SolicitudGestionCruceDTO> solicitudesPostulacion,
                                                                 UserDTO userDTO, UserDTO usuarioAsignar, Integer tope) {

        Integer cont = 0;
        String radicado = "";
        List<AsignacionTurnosDTO> respuesta = new ArrayList<>();

        try {
            NumeroRadicadoCorrespondenciaDTO numeroRadicado = obtenerNumeroRadicado(solicitudesPostulacion.size());

            for (int j = 0; j < solicitudesPostulacion.size(); j++) {
                SolicitudGestionCruceDTO solicitudGestionCruceDTO = solicitudesPostulacion.get(j);

                if (cont < tope && cont != 0) {
                    //Se crea la tarea hijo
                    cont++;
                    String radicadoHijo = (radicado + "_" + cont);

                    SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
                    solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                    solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                    solicitudDTO.setFechaRadicacion(new Date().getTime());
                    solicitudDTO.setFechaCreacion(new Date().getTime());
                    solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    solicitudDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS);
                    solicitudDTO.setClasificacion(ClasificacionEnum.HOGAR);
                    solicitudDTO.setDestinatario(usuarioAsignar.getNombreUsuario());
                    solicitudDTO.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                    solicitudDTO.setNumeroRadicacion(radicadoHijo);
                    Long idSolicitudGlobal = guardarSolicitudGlobal(solicitudDTO);
                    solicitudDTO = consultarSolicitudGlobal(idSolicitudGlobal);
                    solicitudGestionCruceDTO.convertToDTO(solicitudDTO.convertToSolicitudEntity());
                    actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
                    // Guardar Datos temporales
                    SolicitudPostulacionModeloDTO spo = consultarSolicitudPostulacionById(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
                    ConsultarPostulacionTemporal consultarPostulacionTemporal = new ConsultarPostulacionTemporal(spo.getIdSolicitud());
                    consultarPostulacionTemporal.execute();
                    SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal.getResult();
                    solicitudGestionCruceDTO.setDatosPostulacionFovis(solicitudTemporal);
                    guardarDatosTemporalGestionCruce(solicitudGestionCruceDTO);
                } else {
                    //Se crea la tarea padre
                    radicado = "";
                    cont = 0;
                    SolicitudGestionCruceDTO solicitudGestionCruceDTOPadre = new SolicitudGestionCruceDTO();
                    solicitudGestionCruceDTOPadre.setIdSolicitudPostulacion(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
                    solicitudGestionCruceDTOPadre.setTipoCruce(TipoCruceEnum.INTERNO);
                    SolicitudGestionCruceDTO solicitudPadreDto = crearRegistroSolicituGestionCruce(solicitudGestionCruceDTOPadre);
                    SolicitudModeloDTO solicitudPadre = new SolicitudModeloDTO();
                    solicitudPadre.setUsuarioRadicacion(userDTO.getNombreUsuario());
                    solicitudPadre.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                    solicitudPadre.setFechaRadicacion(new Date().getTime());
                    solicitudPadre.setFechaCreacion(new Date().getTime());
                    solicitudPadre.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    solicitudPadre.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS);
                    solicitudPadre.setClasificacion(ClasificacionEnum.HOGAR);
                    solicitudPadre.setDestinatario(usuarioAsignar.getNombreUsuario());
                    solicitudPadre.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                    solicitudPadre.setNumeroRadicacion(numeroRadicado.nextValue());
                    Long idSolicitudGlobalPadre = guardarSolicitudGlobal(solicitudPadre);
                    solicitudPadre = consultarSolicitudGlobal(idSolicitudGlobalPadre);
                    solicitudPadreDto.convertToDTO(solicitudPadre.convertToSolicitudEntity());
                    actualizarSolicitudGestionCruce(solicitudPadreDto);
                    solicitudPadreDto.setDatosPostulacionFovis(null);
                    guardarDatosTemporalGestionCruce(solicitudPadreDto);
                    radicado = solicitudPadre.getNumeroRadicacion();
                    AsignacionTurnosDTO asignar = new AsignacionTurnosDTO();
                    asignar.setIdSolicitud(solicitudPadreDto.getIdSolicitud());
                    asignar.setUsuarioAsignar(usuarioAsignar);
                    asignar.setUsuarioRadica(userDTO);
                    asignar.setRadicado(radicado);
                    respuesta.add(asignar);

                    //Se crea la tarea hijo

                    cont++;
                    String radicadoHijo = (radicado + "_" + cont);
                    SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
                    solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                    solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                    solicitudDTO.setFechaRadicacion(new Date().getTime());
                    solicitudDTO.setFechaCreacion(new Date().getTime());
                    solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    solicitudDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_INTERNO_FOVIS);
                    solicitudDTO.setClasificacion(ClasificacionEnum.HOGAR);
                    solicitudDTO.setDestinatario(usuarioAsignar.getNombreUsuario());
                    solicitudDTO.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                    solicitudDTO.setNumeroRadicacion(radicadoHijo);
                    Long idSolicitudGlobal = guardarSolicitudGlobal(solicitudDTO);
                    solicitudDTO = consultarSolicitudGlobal(idSolicitudGlobal);
                    solicitudGestionCruceDTO.convertToDTO(solicitudDTO.convertToSolicitudEntity());
                    actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
                    // Guardar Datos temporales
                    SolicitudPostulacionModeloDTO spo = consultarSolicitudPostulacionById(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
                    ConsultarPostulacionTemporal consultarPostulacionTemporal = new ConsultarPostulacionTemporal(spo.getIdSolicitud());
                    consultarPostulacionTemporal.execute();
                    SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal.getResult();
                    solicitudGestionCruceDTO.setDatosPostulacionFovis(solicitudTemporal);
                    guardarDatosTemporalGestionCruce(solicitudGestionCruceDTO);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    private SolicitudGestionCruceDTO crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        CrearRegistroSolicituGestionCruce crearRegistroSolicituGestionCruce = new CrearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);
        crearRegistroSolicituGestionCruce.execute();
        return crearRegistroSolicituGestionCruce.getResult();
    }

    /**
     * Obtiene el numero de radicado
     *
     * @param cantidad Cantidad de numeros de radicados requeridos
     * @return Información numero radicado
     */
    private NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicado(Integer cantidad) {
        ObtenerNumeroRadicadoCorrespondencia obtenerNumeroRadicadoCorrespondencia = new ObtenerNumeroRadicadoCorrespondencia(cantidad, TipoEtiquetaEnum.NUMERO_RADICADO);
        obtenerNumeroRadicadoCorrespondencia.execute();
        return obtenerNumeroRadicadoCorrespondencia.getResult();
    }

    public void dispersarTareasCruceInterno(List<SolicitudGestionCruceDTO> postulaciones,
                                            UserDTO userDTO, List<String> listUsuarios) {
        List<UserDTO> usuariosDTO = new ArrayList<>();
        try {
            Integer cntPostulaciones = postulaciones.size();
            Integer cntPostulacionesAux = 0;
            Integer cntUsuarios = listUsuarios.size();
            Integer tope = consultarTope();

            int modulo = cntPostulaciones % cntUsuarios;
            cntPostulacionesAux = cntPostulaciones - modulo;

            Integer distribucionPost = cntPostulacionesAux / cntUsuarios;
            Integer distribucionPostAux = distribucionPost;

            //cantidad maxima por tope
            Integer cantidadTope = tope * cntUsuarios;

            //se recorre la cantidad de usuarios
            int indexFin = distribucionPost;
            int indexIni = 0;

            for (String user : listUsuarios) {
                UserDTO userAsig = new UserDTO();
                userAsig.setNombreUsuario(user);
                usuariosDTO.add(userAsig);
            }

            ExecutorService executor = Executors.newFixedThreadPool(cntUsuarios);
            List<Callable<List<AsignacionTurnosDTO>>> callables = new ArrayList<>();

            for (int i = 0; i < cntUsuarios; i++) {
                UserDTO user = usuariosDTO.get(i);
                user.setSedeCajaCompensacion("1");
                user.setEmail(user.getNombreUsuario());
                String nombreUsuario = user.getNombreUsuario();
                if (modulo > 0) {
                    indexFin++;
                    modulo--;
                }
                List<SolicitudGestionCruceDTO> postulacionesXUsuario = fragmentoArray(postulaciones, indexIni, indexFin);
                indexIni = indexFin;
                indexFin += distribucionPost;

                callables.add(() -> dispersarTareaGestionCruce(postulacionesXUsuario, userDTO, user, tope));

            }
            List<Future<List<AsignacionTurnosDTO>>> result = new ArrayList<>();
            result = managedExecutorService.invokeAll(callables);
            for (Future<List<AsignacionTurnosDTO>> future : result) {
                List<AsignacionTurnosDTO> outDTO = future.get();
                for (int i = 0; i < outDTO.size(); i++) {
                    AsignacionTurnosDTO asignacionTurnosDTO = outDTO.get(i);
                    SolicitudGestionCruceDTO solicitud = this.consultarSolicitudGestionCrucePorSolicitudGlobal(asignacionTurnosDTO.getIdSolicitud());
                    SolicitudModeloDTO solicitudDTO = consultarSolicitudGlobal(asignacionTurnosDTO.getIdSolicitud());
                    Map<String, Object> parametrosProceso = new HashMap<String, Object>();
                    parametrosProceso.put(ID_SOLICITUD, asignacionTurnosDTO.getIdSolicitud());
                    parametrosProceso.put(USUARIO_BACK, asignacionTurnosDTO.getUsuarioAsignar().getNombreUsuario());
                    parametrosProceso.put(NUMERO_RADICADO, asignacionTurnosDTO.getRadicado());
                    parametrosProceso.put(TIPO_CRUCE, 2);
                    Long idInstanciaProceso = iniciarProceso(ProcesoEnum.CRUCES_POSTULACION_FOVIS, parametrosProceso);
                    solicitudDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
                    guardarSolicitudGlobal(solicitudDTO);
                    // Guardar Datos temporales
                    solicitud.setDatosPostulacionFovis(null);
                    guardarDatosTemporalGestionCruce(solicitud);

                    // Se actualiza la solicitud
                    solicitud.setIdInstanciaProceso(idInstanciaProceso.toString());
                    solicitud.setDestinatario(asignacionTurnosDTO.getUsuarioAsignar().getNombreUsuario());
                    solicitud.setSedeDestinatario(asignacionTurnosDTO.getUsuarioAsignar().getSedeCajaCompensacion());
                    actualizarSolicitudGestionCruce(solicitud);

                    // Se genera la data temporal
                    guardarDatosTemporalGestionCruce(solicitud);
                }

            }
        } catch (Exception e) {
            logger.error("Error - Finaliza servicio dispersarTareas", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private SolicitudGestionCruceDTO consultarSolicitudGestionCrucePorSolicitudGlobal(Long idSolicitudGlobal) {
        SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
        ConsultarSolicitudGestionCrucePorSolicitudGlobal consultarSolicitudGestionCrucePorSolicitudGlobal = new ConsultarSolicitudGestionCrucePorSolicitudGlobal(idSolicitudGlobal);
        consultarSolicitudGestionCrucePorSolicitudGlobal.execute();
        solicitudGestionCruceDTO = consultarSolicitudGestionCrucePorSolicitudGlobal.getResult();
        return solicitudGestionCruceDTO;
    }

    public static List<SolicitudGestionCruceDTO> fragmentoArray(List<SolicitudGestionCruceDTO> array, int ini, int fin) {
        List<SolicitudGestionCruceDTO> newArray = new ArrayList<SolicitudGestionCruceDTO>();
        for (int i = ini; i < fin; i++) {
            newArray.add(array.get(i));
        }
        return newArray;
    }

    public Integer consultarTope() {

        Integer tope = 0;

        ConsultarDatosGeneralesFovis consultarDatosGeneralesFovis = new ConsultarDatosGeneralesFovis();
        consultarDatosGeneralesFovis.execute();
        List<ParametrizacionFOVISModeloDTO> parametrizacionFOVISModeloDTOS = consultarDatosGeneralesFovis.getResult();
        for (ParametrizacionFOVISModeloDTO parametro : parametrizacionFOVISModeloDTOS) {
            if (parametro.getParametro().equals(ParametroFOVISEnum.LIMITE_SOLICITUDES_AGRUPADAS_POR_TAREA)) {
                tope = parametro.getValorNumerico().intValueExact();
            }

        }
        return tope;
    }

    /**
     * Método encargado invocar el servicio que guarda temporalmente los datos de la solicitud gestion cruce fovis.
     *
     * @param solicitudVerificacion dto con los datos a guardar.
     * @throws JsonProcessingException error convirtiendo
     */
    private void guardarDatosTemporalGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) throws JsonProcessingException {
        logger.debug("Inicio de método guardarDatosTemporalGestionCruce");
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload;
        jsonPayload = mapper.writeValueAsString(solicitudGestionCruceDTO);
        GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(solicitudGestionCruceDTO.getIdSolicitud(), jsonPayload);
        datosTemporalService.execute();
        logger.debug("Fin de método guardarDatosTemporalGestionCruce");
    }

    /**
     * Consulta la informacion de la solicitud de postulacion por el identificador de la misma
     *
     * @param idSolicitudPostulacion Identificador solicitud postulacion
     * @return DTO Solicitud postulacion
     */
    private SolicitudPostulacionModeloDTO consultarSolicitudPostulacionById(Long idSolicitudPostulacion) {
        ConsultarSolicitudPostulacionById consultarSolicitudPostulacion = new ConsultarSolicitudPostulacionById(idSolicitudPostulacion);
        consultarSolicitudPostulacion.execute();
        return consultarSolicitudPostulacion.getResult();
    }

    /**
     * Método encargado de obtener el usuario al que debe ser asignada una solicitud de postulación
     * medante modelo de asignación por Turnos.
     *
     * @return Usuario
     */
    private UserDTO asignacionConsecutivaPorTurnos(List<UserDTO> usuarios, String ultimoUsuario) {
        Collections.sort(usuarios, new Comparator<UserDTO>() {
            @Override
            public int compare(UserDTO u1, UserDTO u2) {
                return u1.getNombreUsuario().toUpperCase().compareTo(u2.getNombreUsuario().toUpperCase());
            }
        });

        if (ultimoUsuario == null) {
            UserDTO usuario = usuarios.iterator().next();
            return usuario;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombreUsuario().equals(ultimoUsuario)) {
                if (i != (usuarios.size() - 1)) {
                    return usuarios.get(i + 1);
                } else {
                    return usuarios.get(0);
                }
            }
        }
        if (usuarios != null && !usuarios.isEmpty()) {
            return usuarios.get(0);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.AsignacionCompositeService#rechazarResultadoCruceInterno(com.asopagos.fovis.composite.dto.
     * AsignaResultadoCruceDTO)
     */
    @Override
    public void rechazarResultadoCruceInterno(AsignaResultadoCruceDTO asignarResultadoCruce) {
        logger.debug("Inicia método rechazarResultadoCruceInterno(AsignaResultadoCruceDTO)");
        // Actualizar ejecucion proceso asincrono indicado que fue revisado
        EjecucionProcesoAsincronoDTO ejecucionProcesoAsincronoDTO = new EjecucionProcesoAsincronoDTO();
        ejecucionProcesoAsincronoDTO.setId(asignarResultadoCruce.getIdProcesoAsincrono());
        ejecucionProcesoAsincronoDTO.setProcesoRevisado(Boolean.TRUE);
        ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                ejecucionProcesoAsincronoDTO);
        actualizarEjecucionProcesoAsincrono.execute();
        logger.debug("Finaliza método rechazarResultadoCruceInterno(AsignaResultadoCruceDTO)");
    }

    /**
     * Método que realiza el llamado al servicio que guarda o actualiza el acta de asignacion
     *
     * @param actaAsignacionFOVISModeloDTO DTO con la informacion de la asignación a almacenar
     * @return DTO con la información del acta de asignacion procesada
     */
    private ActaAsignacionFOVISModeloDTO guardarActualizarActaAsignacion(ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO) {
        logger.debug("Inicia servicio guardarActualizarActaAsignacion");
        GuardarActaAsignacion service = new GuardarActaAsignacion(actaAsignacionFOVISModeloDTO);
        service.execute();
        ActaAsignacionFOVISModeloDTO actaAsignacion = service.getResult();
        logger.debug("Finaliza servicio guardarActualizarActaAsignacion");
        return actaAsignacion;
    }

    /**
     * Actualiza la solicitud de gestion cruce
     *
     * @param DTO con la informacion de la solicitud de gestion
     * @return DTO Solicitud de gestion
     */
    private SolicitudGestionCruceDTO actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruce) {
        ActualizarSolicitudGestionCruce actualizarSolicitudGestion = new ActualizarSolicitudGestionCruce(
                solicitudGestionCruce);
        actualizarSolicitudGestion.execute();
        return actualizarSolicitudGestion.getResult();
    }

    @Override
    public void cancelarEjecucionAsignacion(Long idCicloAsignacion) {
        logger.debug("Inicia servicio cancelarEjecucionAsignacion(Long)");
        // Se elimina el valor disponible del ciclo 
        ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(idCicloAsignacion);
        serviceConsultaCicloAsignacion.execute();
        CicloAsignacionModeloDTO cicloAsignacion = serviceConsultaCicloAsignacion.getResult();
        cicloAsignacion.setValorDisponible(null);
        GuardarActualizarCicloAsignacion serviceGuardarCiclo = new GuardarActualizarCicloAsignacion(cicloAsignacion);
        serviceGuardarCiclo.execute();
        cicloAsignacion = serviceGuardarCiclo.getResult();

        logger.debug("Finaliza servicio cancelarEjecucionAsignacion(Long)");
    }

    /**
     * Consulta la información del ultimo proceso asincrono
     *
     * @param idCicloAsignacion Identificador del ciclo de asignacion
     * @param tipoProceso       Tipo de proceso asincrono
     * @return Información ejecucion proceso asincrono
     */
    private EjecucionProcesoAsincronoDTO consultarUltimaEjecucionAsincrona(Long idCicloAsignacion, TipoProcesoAsincronoEnum tipoProceso) {
        ConsultarUltimaEjecucionAsincrona consultarEjecucionActual = new ConsultarUltimaEjecucionAsincrona(idCicloAsignacion, tipoProceso);
        consultarEjecucionActual.execute();
        return consultarEjecucionActual.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de un jefe de hogar, por
     * identificación
     *
     * @param tipoIdentificacion   Tipo de identificación del jefe de hogar
     * @param numeroIdentificacion Número de identificación del jefe de hogar
     * @return Objeto <code>JefeHogarModeloDTO</code> con la información del
     * registro consultado
     */
    private JefeHogarModeloDTO consultarJefeHogar(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicio de método consultarJefeHogar");
        ConsultarJefeHogar service = new ConsultarJefeHogar(numeroIdentificacion, tipoIdentificacion);
        service.execute();
        logger.debug("Fin de método consultarJefeHogar");
        return service.getResult();
    }

    @Override
    public void cancelarValidacionHogar(Long idCicloAsignacion) {
        logger.debug("Inicia servicio cancelarValidacionHogar(Long)");
        /* Actualiza la ejecución del proceso asíncrono a Revisado Cancelado */
        EjecucionProcesoAsincronoDTO ejecucionProcesoDTO = consultarUltimaEjecucionAsincrona(idCicloAsignacion,
                TipoProcesoAsincronoEnum.CRUCE_INTERNO_FOVIS);
        if (ejecucionProcesoDTO == null || ejecucionProcesoDTO.getId() == null) {
            return;
        }
        ejecucionProcesoDTO.setFechaFin(new Date());
        ejecucionProcesoDTO.setProcesoRevisado(Boolean.TRUE);
        ejecucionProcesoDTO.setProcesoCancelado(Boolean.TRUE);
        ActualizarEjecucionProcesoAsincrono actualizarEjecucionProcesoAsincrono = new ActualizarEjecucionProcesoAsincrono(
                ejecucionProcesoDTO);
        actualizarEjecucionProcesoAsincrono.execute();
        logger.debug("Finaliza servicio cancelarValidacionHogar(Long)");
    }

    /**
     * Invoca el servicio que consulta el recurso prioridad con el cual fue asignado el hogar
     *
     * @param idPostulacion        Identificador postulación
     * @param idSolicitudAignacion Identificador de la solicitud de asignacion
     * @return recurso prioridad de la asignacion
     */
    private String consultarRecursosPrioridadPostulacionAsignacion(Long idPostulacion, Long idSolicitudAsignacion) {
        logger.debug("Inicio método consultarRecursosPrioridadPostulacionAsignacion");
        ConsultarRecursosPrioridadPostulacionAsignacion service = new ConsultarRecursosPrioridadPostulacionAsignacion(idPostulacion, idSolicitudAsignacion);
        service.execute();
        logger.debug("Fin método consultarRecursosPrioridadPostulacionAsignacion");
        return service.getResult();
    }
}
