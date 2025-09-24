package com.asopagos.fovis.composite.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;

import com.asopagos.afiliaciones.clients.ObtenerNumeroRadicadoCorrespondencia;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.ConsultarAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiarioByTipo;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.ConsultarSedesCajaCompensacion;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.clients.ConsultarEstadoCartera;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.CargueArchivoCruceFovisCedulaDTO;
import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import com.asopagos.dto.CargueArchivoCruceFovisFechasCorteDTO;
import com.asopagos.dto.CargueArchivoCruceFovisHojaDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.CruceDTO;
import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.InformacionCruceFovisDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import com.asopagos.dto.fovis.IntentoPostulacionDTO;
import com.asopagos.dto.fovis.OferenteDTO;
import com.asopagos.dto.fovis.ProveedorDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.fovis.SolicitudVerificacionFovisDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.dto.modelo.DepartamentoModeloDTO;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.IntentoPostulacionRequisitoModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionFOVISModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionModalidadModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.ProveedorModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RangoTopeValorSFVModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empresas.clients.ConsultarEmpresa;
import com.asopagos.empresas.clients.ConsultarOferente;
import com.asopagos.empresas.clients.ConsultarProveedor;
import com.asopagos.empresas.clients.CrearActualizarLegalizacionDesembolsoProveedor;
import com.asopagos.empresas.clients.CrearActualizarOferente;
import com.asopagos.empresas.clients.CrearActualizarProveedor;
import com.asopagos.entidades.ccf.core.SedeCajaCompensacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.NombreValidacionEnum;
import com.asopagos.enumeraciones.TipoPostulacionFOVISEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.OrigenEscalamientoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAnalistaEstalamientoFOVISEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.FrecuenciaEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.CausalCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;
import com.asopagos.enumeraciones.fovis.TipoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ActualizarDatosGeneralesFovis;
import com.asopagos.fovis.clients.ActualizarEstadoHogar;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudGestionCruce;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudPostulacion;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudVerificacionFovis;
import com.asopagos.fovis.clients.ActualizarJsonPostulacion;
import com.asopagos.fovis.clients.ActualizarMediosDePago;
import com.asopagos.fovis.clients.ActualizarModalidades;
import com.asopagos.fovis.clients.ActualizarPostulacionesCalificadasSinCambioCiclo;
import com.asopagos.fovis.clients.ActualizarPostulacionesNovedadesAsociadasCicloPredecesor;
import com.asopagos.fovis.clients.ActualizarSolicitudGestionCruce;
import com.asopagos.fovis.clients.ActualizarSolicitudesGestionCruceASubsanadas;
import com.asopagos.fovis.clients.ConsultarCargueArchivoCruce;
import com.asopagos.fovis.clients.ConsultarContenidoArchivoCargueFovis;
import com.asopagos.fovis.clients.ConsultarCruceFiltro;
import com.asopagos.fovis.clients.ConsultarCrucePorSolicitudPostulacion;
import com.asopagos.fovis.clients.ConsultarCruceTodosTiposInformacion;
import com.asopagos.fovis.clients.ConsultarDatosGeneralesFovis;
import com.asopagos.fovis.clients.ConsultarDepartamentoExcepcionFOVIS;
import com.asopagos.fovis.clients.ConsultarInfoPostulacion;
import com.asopagos.fovis.clients.ConsultarMediosDePago;
import com.asopagos.fovis.clients.ConsultarNumeroPostulacionPersona;
import com.asopagos.fovis.clients.ConsultarInformacionArchivoCruces;
import com.asopagos.fovis.clients.ConsultarParametrizacionModalidad;
import com.asopagos.fovis.clients.ConsultarParametrizacionModalidades;
import com.asopagos.fovis.clients.ConsultarPostulacionFOVIS;
import com.asopagos.fovis.clients.ConsultarRangosSVFPorModalidad;
import com.asopagos.fovis.clients.ConsultarSolicitudGestionCruce;
import com.asopagos.fovis.clients.ConsultarSolicitudGestionCrucePorPostulacionTipoCruce;
import com.asopagos.fovis.clients.ConsultarSolicitudGestionCrucePorSolicitudGlobal;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacion;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacionById;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacionPorListaNumeroPostulacion;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacionPorNumeroCedula;
import com.asopagos.fovis.clients.ConsultarSolicitudPostulacionPorNumeroPostulacion;
import com.asopagos.fovis.clients.ConsultarSolicitudVerificacionFovis;
import com.asopagos.fovis.clients.CrearActualizarAhorroPrevio;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarProyectoSolucionVivienda;
import com.asopagos.fovis.clients.CrearActualizarRecursoComplementario;
import com.asopagos.fovis.clients.CrearActualizarSolicitudPostulacion;
import com.asopagos.fovis.clients.CrearActualizarSolicitudVerificacionFovis;
import com.asopagos.fovis.clients.CrearListaCondicionEspecialPersona;
import com.asopagos.fovis.clients.CrearRegistroCruce;
import com.asopagos.fovis.clients.CrearRegistroListaSolicituGestionCruce;
import com.asopagos.fovis.clients.CrearRegistroSolicituGestionCruce;
import com.asopagos.fovis.clients.ExistenEscalamientosSinResultado;
import com.asopagos.fovis.clients.GuardarSolicitudGlobal;
import com.asopagos.fovis.clients.InactivarIntegrantesHogarNoRelacionados;
import com.asopagos.fovis.clients.RegistrarCicloAsignacion;
import com.asopagos.fovis.clients.RegistrarDocumentoSolicitud;
import com.asopagos.fovis.clients.RegistrarIntentoPostulacionFOVIS;
import com.asopagos.fovis.clients.RegistrarIntentoPostulacionRequisito;
import com.asopagos.fovis.clients.RegistrarModalidadesCicloAsignacion;
import com.asopagos.fovis.clients.VerificarEstructuraArchivoCruce;
import com.asopagos.fovis.clients.*;
import com.asopagos.fovis.composite.dto.AnalisisSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.AsignaResultadoCruceDTO;
import com.asopagos.fovis.composite.dto.AsignarSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.CancelacionSolicitudPostulacionDTO;
import com.asopagos.fovis.composite.dto.GestionPNCPostulacionDTO;
import com.asopagos.fovis.composite.dto.RegistrarVerificacionControlInternoDTO;
import com.asopagos.fovis.composite.dto.ResultadoAnalisisPostulacionDTO;
import com.asopagos.fovis.composite.dto.ValidacionOferenteDTO;
import com.asopagos.fovis.composite.dto.VariablesGestionFOVISDTO;
import com.asopagos.fovis.composite.dto.VerificacionCorreccionHallazgos;
import com.asopagos.fovis.composite.dto.VerificacionGestionControlInterno;
import com.asopagos.fovis.composite.dto.VerificacionGestionPNCPostulacionDTO;
import com.asopagos.fovis.composite.service.FovisCompositeService;
import com.asopagos.fovis.dto.AsignacionTurnosDTO;
import com.asopagos.fovis.dto.TareasHeredadasDTO;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteOferentePorIdOferente;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteProveedorPorIdProveedor;
import com.asopagos.legalizacionfovis.clients.ConsultarDocumentosSoporteProyectoPorIdProyecto;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.ConsultarDatosPersonaListNumeroIdentificacion;
import com.asopagos.personas.clients.ConsultarJefeHogar;
import com.asopagos.personas.clients.ConsultarListaIntegranteHogar;
import com.asopagos.personas.clients.CrearActualizarIntegranteHogar;
import com.asopagos.personas.clients.CrearActualizarJefeHogar;
import com.asopagos.personas.clients.CrearActualizarUbicacion;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.processschedule.clients.ConsultarProgramacion;
import com.asopagos.processschedule.clients.RegistrarActualizarProgramacionAutomatico;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.solicitudes.clients.RegistrarEscalamientoSolicitud;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.Interpolator;
import com.asopagos.util.RangoTopeUtils;
import com.asopagos.util.CalendarUtils.TipoDia;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.validaciones.fovis.clients.ValidarReglasNegocioFovis;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con FOVIS. <b>Historia de Usuario:</b> proceso 3.2.
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class FovisCompositeBusiness implements FovisCompositeService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(FovisCompositeBusiness.class);

    /** Constantes BPM */
    /**
     * Constante para el parámetro id de solicitud.
     */
    private static final String ID_SOLICITUD = "idSolicitud";
    /**
     * Constante para el Usuario Front.
     */
    private static final String USUARIO_FRONT = "usuarioFront";
    /**
     * Constante para el Usuario Back.
     */
    private static final String USUARIO_BACK = "usuarioBack";
    /**
     * Constante cuando la solicitud es escalada por el FRONT.
     */
    private static final String ESCALADA_FRONT = "escaladaFront";
    /**
     * Constante para identificar al Analista Especializado FRONT 321-20.
     */
    private static final String ANALISTA_ESPECIALIZADO = "analistaEspecializado";
    /**
     * Constante cuando la solicitud es por Radicación Abreviada.
     */
    private static final String RADICACION_ABREVIADA = "radicacionAbreviada";
    /**
     * Constante cuando la solicitud es escalada a varios analistas.
     */
    private static final String ESCALADA_ANALISTAS = "solicitudEscaladaAnalistas";
    /**
     * Constante cuando la solicitud es escalada al analista técnico
     */
    private static final String ANALISTA_TECNICO = "analistaTecnico";
    /**
     * Constante cuando la solicitud es escalada al analista Jurídico
     */
    private static final String ANALISTA_JURIDICO = "analistaJuridico";
    /**
     * Constante cuando la solicitud es escalada al analista Hogar
     */
    private static final String ANALISTA_HOGAR = "analistaHogar";
    /**
     * Constante que define señal para documentos físicos
     */
    private static final String SENAL_DOCUMENTOS_FISICOS = "documentosFisicos";
    /**
     * Constante que define señal para la asignación de cruces a control interno
     */
    private static final String ASIGNACION_CRUCES_CONTROL_INTERNO = "asignacionCrucesControlInterno";
    /**
     * Constante que indica si la solicitud fue escalada desde el Front
     */
    private static final String PROVIENE_FRONT = "provieneFront";
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
     * Constante con la clave para el primer Nombre.
     */
    private static final String PRIMER_NOMBRE = "primerNombre";
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
     * Constante que define señal para la informacion corregida en la postulación web
     */
    private static final String INFORMACION_CORREGIDA = "informacionCorregida";
    /**
     * Constante con el nombre de la señal de solicitud de gestion cruce
     */
    private static final String SENAL_SOLICITUD_GESTION_CRUCE = "solicitudGestionCruces";

    /**
     * Constante que define el número de radicación para la postulación.
     */
    private static final String NUMERO_RADICACION = "numeroRadicado";
    /**
     * Constante que define si se registró un intento de postulación.
     */
    private static final String INTENTO_POSTULACION = "intentoPostulacion";
    /**
     * Constante que define el tiempo de corrección de la información WEB
     */
    private static final String TIEMPO_CORRECCION = "tiempoExpiracionCorreccion";
    /**
     * Motivo de la inconsistencia por Morosidad
     */
    private static final String MOROSIDAD = "Morosidad";
    /**
     * Motivo de la inconsistencia por Morosidad
     */
    private static final String TIPO_HIJO = "Tipo Hijo";
    /**
     * Motivo de la inconsistencia por Morosidad
     */
    private static final String TIPO_PADRE = "Tipo Padre";
    /**
     * Motivo de la inconsistencia por Morosidad
     */
    private static final String TIPO_VERIFICACION = "tipoVerificacion";
    /**
     * Constante para el Usuario de Control Interno.
     */
    private static final String USUARIO_CONTROL_INTERNO = "usuarioControlInterno";
    /**
     * Constante con el nombre del parámetro BPM que indica el tipo de cruce
     */
    public static final String TIPO_CRUCE = "tipoCruce";

    @Resource(lookup = "java:jboss/ee/concurrency/executor/fovis")
    private ManagedExecutorService managedExecutorService;

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#consultarVariablesGestionFOVIS()
     */
    public VariablesGestionFOVISDTO consultarVariablesGestionFOVIS() {
        logger.debug("Se inicia el servicio de consultarVariablesGestionFOVIS()");
        try {
            VariablesGestionFOVISDTO variablesGestionFOVISDTO = new VariablesGestionFOVISDTO();

            // se consultan los parámetros generales para la gestión de fovis
            ConsultarDatosGeneralesFovis consultarDatosGeneralesFovis = new ConsultarDatosGeneralesFovis();
            consultarDatosGeneralesFovis.execute();
            variablesGestionFOVISDTO.setParametrizacionesFOVIS(consultarDatosGeneralesFovis.getResult());

            // se consulta toda la parametrizacion de las modalidades
            ConsultarParametrizacionModalidades consultarParametrizacionModalidades = new ConsultarParametrizacionModalidades();
            consultarParametrizacionModalidades.execute();
            variablesGestionFOVISDTO.setModalidadesFOVIS(consultarParametrizacionModalidades.getResult());

            // consulta los medios de pago habilitados
            ConsultarMediosDePago consultarMediosDePago = new ConsultarMediosDePago();
            consultarMediosDePago.execute();
            variablesGestionFOVISDTO.setMediosDePago(consultarMediosDePago.getResult());

            // consulta la programación de las novedades para FOVIS
            List<ProcesoAutomaticoEnum> procesos = new ArrayList<ProcesoAutomaticoEnum>();
            procesos.add(ProcesoAutomaticoEnum.LEVANTAR_INHABILIDAD_SANCION_AUTOMATICAMENTE);
            procesos.add(ProcesoAutomaticoEnum.SUSPENSION_AUTOMATICA_POSTULACION_X_CAMBIO_ANIO);
            procesos.add(ProcesoAutomaticoEnum.RECHAZO_AUTOMATICO_SOLICITUDES_SUSPENDIDAS_X_CAMBIO_ANIO);
            ConsultarProgramacion consultarProgramacion = new ConsultarProgramacion(procesos);
            consultarProgramacion.execute();
            List<ParametrizacionEjecucionProgramadaModeloDTO> ejecuciones = consultarProgramacion.getResult();
            for (ParametrizacionEjecucionProgramadaModeloDTO parametrizacionEjecucionProgramadaModeloDTO : ejecuciones) {
                Date fechaInicio = this.obtenerFechaMesDia(parametrizacionEjecucionProgramadaModeloDTO.getMes(),
                        parametrizacionEjecucionProgramadaModeloDTO.getDiaMes());
                if (ProcesoAutomaticoEnum.LEVANTAR_INHABILIDAD_SANCION_AUTOMATICAMENTE
                        .equals(parametrizacionEjecucionProgramadaModeloDTO.getProceso())) {
                    variablesGestionFOVISDTO.setProgramacionLevantarInhabilidadSancion(parametrizacionEjecucionProgramadaModeloDTO);
                } else if (ProcesoAutomaticoEnum.SUSPENSION_AUTOMATICA_POSTULACION_X_CAMBIO_ANIO
                        .equals(parametrizacionEjecucionProgramadaModeloDTO.getProceso())) {
                    parametrizacionEjecucionProgramadaModeloDTO.setFechaInicio(fechaInicio);
                    variablesGestionFOVISDTO.setProgramacionNovedadSuspension(parametrizacionEjecucionProgramadaModeloDTO);
                } else if (ProcesoAutomaticoEnum.RECHAZO_AUTOMATICO_SOLICITUDES_SUSPENDIDAS_X_CAMBIO_ANIO
                        .equals(parametrizacionEjecucionProgramadaModeloDTO.getProceso())) {
                    parametrizacionEjecucionProgramadaModeloDTO.setFechaInicio(fechaInicio);
                    variablesGestionFOVISDTO.setProgramacionNovedadRechazo(parametrizacionEjecucionProgramadaModeloDTO);
                }
            }

            logger.debug("Finaliza el servicio de consultarVariablesGestionFOVIS()");
            return variablesGestionFOVISDTO;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ocurrió un error inesperado en el servicio consultarVariablesGestionFOVIS()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#registrarVariablesGestionFOVIS(com.asopagos.fovis.dto.VariablesGestionFOVISDTO)
     */
    public void registrarVariablesGestionFOVIS(VariablesGestionFOVISDTO variablesGestionFOVISDTO) {
        logger.debug("Se inicia el servicio de registrarVariablesGestionFOVIS(VariablesGestionFOVISDTO)");

        // Actualizar los parámetros generales de FOVIS (sin los plazos de vencimiento)
        ActualizarDatosGeneralesFovis actualizarDatosGeneralesFovis = new ActualizarDatosGeneralesFovis(
                variablesGestionFOVISDTO.getParametrizacionesFOVIS());
        actualizarDatosGeneralesFovis.execute();

        // Actualizar las Modalidades Fovis
        ActualizarModalidades actualizarModalidades = new ActualizarModalidades(variablesGestionFOVISDTO.getModalidadesFOVIS());
        actualizarModalidades.execute();

        // registrar medios de pago disponibles para la CCF
        ActualizarMediosDePago registrarMediosDePago = new ActualizarMediosDePago(variablesGestionFOVISDTO.getMediosDePago());
        registrarMediosDePago.execute();

        logger.debug("Finaliza el servicio de registrarVariablesGestionFOVIS(VariablesGestionFOVISDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#consultarListaOferentes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * String, Short, String, String, String, String, String)
     */
    @Override
    public List<OferenteDTO> consultarListaOferentes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                     Short digitoVerificacion, String razonSocial, String primerNombre, String segundoNombre, String primerApellido,
                                                     String segundoApellido) {
        logger.debug(Interpolator.interpolate("Inicia consultarListaOferentes({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7})", tipoIdentificacion,
                numeroIdentificacion, digitoVerificacion, razonSocial, primerNombre, segundoNombre, primerApellido, segundoApellido));
        List<OferenteDTO> listaOferenteDTO = new ArrayList<OferenteDTO>();

        if (tipoIdentificacion != null && numeroIdentificacion != null) {
            if (digitoVerificacion != null) {
                List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa(tipoIdentificacion, numeroIdentificacion, null);
                listaOferenteDTO = transformarListaEmpleadorOferente(listaEmpresa);
            } else {
                List<PersonaDTO> listaPersonaDTO = consultarPersona(tipoIdentificacion, numeroIdentificacion, null, null);
                listaOferenteDTO = transformarListaPersonaOferente(listaPersonaDTO);
            }
        } else {
            if (razonSocial != null) {
                List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa(null, null, razonSocial);
                listaOferenteDTO = transformarListaEmpleadorOferente(listaEmpresa);
            } else {
                List<PersonaDTO> listaPersonaDTO = consultarPersona(null, null, primerNombre, primerApellido);
                listaOferenteDTO = transformarListaPersonaOferente(listaPersonaDTO);
            }
        }

        logger.debug(Interpolator.interpolate("Finaliza consultarListaOferentes({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7})", tipoIdentificacion,
                numeroIdentificacion, digitoVerificacion, razonSocial, primerNombre, segundoNombre, primerApellido, segundoApellido));
        return listaOferenteDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#consultarListaProveedores(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * String, Short, String, String, String, String, String)
     */
    @Override
    public List<ProveedorDTO> consultarListaProveedores(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                        Short digitoVerificacion, String razonSocial, String primerNombre, String segundoNombre, String primerApellido,
                                                        String segundoApellido) {
        logger.debug(Interpolator.interpolate("Inicia consultarListaProveedores({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7})", tipoIdentificacion,
                numeroIdentificacion, digitoVerificacion, razonSocial, primerNombre, segundoNombre, primerApellido, segundoApellido));
        List<ProveedorDTO> listaProveedorDTO = new ArrayList<ProveedorDTO>();

        if (tipoIdentificacion != null && numeroIdentificacion != null) {
            if (digitoVerificacion != null) {
                List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa(tipoIdentificacion, numeroIdentificacion, null);
                listaProveedorDTO = transformarListaEmpleadorProveedor(listaEmpresa);
            } else {
                List<PersonaDTO> listaPersonaDTO = consultarPersona(tipoIdentificacion, numeroIdentificacion, null, null);
                listaProveedorDTO = transformarListaPersonaProveedor(listaPersonaDTO);
            }
        } else {
            if (razonSocial != null) {
                List<EmpresaModeloDTO> listaEmpresa = consultarEmpresa(null, null, razonSocial);
                listaProveedorDTO = transformarListaEmpleadorProveedor(listaEmpresa);
            } else {
                List<PersonaDTO> listaPersonaDTO = consultarPersona(null, null, primerNombre, primerApellido);
                listaProveedorDTO = transformarListaPersonaProveedor(listaPersonaDTO);
            }
        }

        logger.debug(Interpolator.interpolate("Finaliza consultarListaProveedor({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7})", tipoIdentificacion,
                numeroIdentificacion, digitoVerificacion, razonSocial, primerNombre, segundoNombre, primerApellido, segundoApellido));
        return listaProveedorDTO;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#validarOferente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * String)
     */
    @Override
    public ValidacionOferenteDTO validarOferente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug(Interpolator.interpolate("Inicia validarOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        List<Empleador> listaEmpleador = consultarEmpleador(tipoIdentificacion, numeroIdentificacion, null);

        if (listaEmpleador != null && !listaEmpleador.isEmpty()) {
            EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();
            empleadorDTO.convertToDTO(listaEmpleador.get(0));

            if (EstadoEmpleadorEnum.INACTIVO.equals(empleadorDTO.getEstadoEmpleador())
                    && (MotivoDesafiliacionEnum.CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO
                    .equals(empleadorDTO.getMotivoDesafiliacion())
                    || MotivoDesafiliacionEnum.FUSION_ADQUISICION.equals(empleadorDTO.getMotivoDesafiliacion()))) {
                return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_MOTIVO_DESAFILIACION_EMPLEADOR_INACTIVO.toString(),
                        empleadorDTO.getMotivoDesafiliacion().getDescripcion(), Boolean.FALSE, Boolean.FALSE);
            }

            if (EstadoEmpleadorEnum.INACTIVO.equals(empleadorDTO.getEstadoEmpleador())
                    && (MotivoDesafiliacionEnum.EXPULSION_POR_MOROSIDAD.equals(empleadorDTO.getMotivoDesafiliacion())
                    || MotivoDesafiliacionEnum.EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS
                    .equals(empleadorDTO.getMotivoDesafiliacion())
                    || MotivoDesafiliacionEnum.EXPULSION_POR_INFORMACION_INCORRECTA
                    .equals(empleadorDTO.getMotivoDesafiliacion()))) {
                return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_MOTIVO_DESAFILIACION_INACTIVO.toString(),
                        empleadorDTO.getMotivoDesafiliacion().getDescripcion(), Boolean.TRUE, Boolean.TRUE);
            }
        } else {
            List<RolAfiliadoEmpleadorDTO> listaRolAfiliadoEmpleadorDTO = consultarRolesAfiliado(tipoIdentificacion,
                    numeroIdentificacion);

            for (RolAfiliadoEmpleadorDTO rolAfiliadoEmpleadorDTO : listaRolAfiliadoEmpleadorDTO) {
                if (EstadoAfiliadoEnum.INACTIVO.equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getEstadoAfiliado())
                        && MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO
                        .equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion())) {
                    return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_MOTIVO_DESAFILIACION_PERSONA_INACTIVO.toString(),
                            rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion().getDescripcion(), Boolean.FALSE,
                            Boolean.FALSE);
                }

                if (EstadoAfiliadoEnum.INACTIVO.equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getEstadoAfiliado())
                        && (MotivoDesafiliacionAfiliadoEnum.MAL_USO_DE_SERVICIOS_CCF
                        .equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion())
                        || MotivoDesafiliacionAfiliadoEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF
                        .equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion())
                        || MotivoDesafiliacionAfiliadoEnum.RETIRO_POR_MORA_APORTES
                        .equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion()))) {
                    return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_MOTIVO_DESAFILIACION_INACTIVO.toString(),
                            rolAfiliadoEmpleadorDTO.getRolAfiliado().getMotivoDesafiliacion().getDescripcion(), Boolean.TRUE,
                            Boolean.TRUE);
                }
                /*
                 * En caso que el afiliado sea independiente o pensionado, y tenga asociado un estado de cartera que indique morosidad
                 * se presenta mensaje de alerta.
                 */
                if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getTipoAfiliado())
                        || TipoAfiliadoEnum.PENSIONADO.equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getTipoAfiliado())) {
                    TipoSolicitanteMovimientoAporteEnum tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
                    if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(rolAfiliadoEmpleadorDTO.getRolAfiliado().getTipoAfiliado())) {
                        tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
                    }
                    ConsultarEstadoCartera consultarEstadoCartera = new ConsultarEstadoCartera(tipoSolicitante, numeroIdentificacion,
                            tipoIdentificacion);
                    consultarEstadoCartera.execute();
                    EstadoCarteraEnum estadoCartera = consultarEstadoCartera.getResult();
                    /* Si se encuentra Moroso */
                    if (estadoCartera != null && EstadoCarteraEnum.MOROSO.equals(estadoCartera)) {
                        return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_ESTADO_MOROSIDAD.toString(), MOROSIDAD,
                                Boolean.TRUE, Boolean.TRUE);
                    }
                }
            }
        }
        List<ClasificacionEnum> clasificaciones = Arrays.asList(ClasificacionEnum.HIJO_BIOLOGICO, ClasificacionEnum.HIJO_ADOPTIVO,
                ClasificacionEnum.HIJASTRO, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA,
                ClasificacionEnum.PADRE, ClasificacionEnum.MADRE);
        ConsultarBeneficiarioByTipo consultarBeneficiario = new ConsultarBeneficiarioByTipo(clasificaciones, null, null, null, null, null,
                numeroIdentificacion, tipoIdentificacion, null, EstadoAfiliadoEnum.ACTIVO, false);
        consultarBeneficiario.execute();
        List<BeneficiarioModeloDTO> beneficiarios = consultarBeneficiario.getResult();
        /* Si existe como Beneficiario Padre o Hijo se retorna error de validación. */
        if (beneficiarios != null && !beneficiarios.isEmpty()) {
            for (BeneficiarioModeloDTO beneficiarioModeloDTO : beneficiarios) {
                if (TipoBeneficiarioEnum.HIJO.equals(beneficiarioModeloDTO.getTipoBeneficiario())) {
                    return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_REGISTRADO_BENEFICIARIO.toString(), TIPO_HIJO,
                            Boolean.TRUE, Boolean.TRUE);
                } else if (TipoBeneficiarioEnum.PADRES.equals(beneficiarioModeloDTO.getTipoBeneficiario())) {
                    return new ValidacionOferenteDTO(NombreValidacionEnum.VALIDACION_REGISTRADO_BENEFICIARIO.toString(), TIPO_PADRE,
                            Boolean.TRUE, Boolean.TRUE);
                }
            }
        }

        logger.debug(Interpolator.interpolate("Finaliza validarOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        return new ValidacionOferenteDTO(null, null, Boolean.TRUE, Boolean.FALSE);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#cambiarEstadoOferente(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * String, com.asopagos.enumeraciones.fovis.EstadoOferenteEnum)
     */
    @Override
    public EstadoOferenteEnum cambiarEstadoOferente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                    EstadoOferenteEnum nuevoEstado) {
        logger.debug(Interpolator.interpolate("Inicia cambiarEstadoOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        OferenteModeloDTO oferenteDTO = consultarOferente(tipoIdentificacion, numeroIdentificacion);

        if (oferenteDTO != null) {
            oferenteDTO.setEstado(nuevoEstado);
            crearActualizarOferente(oferenteDTO);
        }

        logger.debug(Interpolator.interpolate("Finaliza cambiarEstadoOferente({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        return oferenteDTO.getEstado();
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#cambiarEstadoProveedor(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * String, com.asopagos.enumeraciones.fovis.EstadoOferenteEnum)
     */
    @Override
    public EstadoOferenteEnum cambiarEstadoProveedor(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                     EstadoOferenteEnum nuevoEstado) {
        logger.debug(Interpolator.interpolate("Inicia cambiarEstadoProveedor({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        ProveedorModeloDTO proveedorDTO = consultarProveedor(tipoIdentificacion, numeroIdentificacion);

        if (proveedorDTO != null) {
            proveedorDTO.setEstado(nuevoEstado);
            crearActualizarProveedor(proveedorDTO);
        }

        logger.debug(Interpolator.interpolate("Finaliza cambiarEstadoProveedor({0}, {1})", tipoIdentificacion, numeroIdentificacion));
        return proveedorDTO.getEstado();
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#radicarPostulacion(
     *com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO, Long, Boolean, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudPostulacionFOVISDTO radicarPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, Long idSolicitudGlobal,
                                                           Boolean terminarTarea, UserDTO userDTO) {
        logger.debug(Interpolator.interpolate("Inicia radicarPostulacion(SolicitudPostulacionFOVISDTO, Long, Boolean, UserDTO) - idSolicitudGlobal:{0}", idSolicitudGlobal));

        // Consulta la solicitud temporal
        if (idSolicitudGlobal != null) {
            solicitudPostulacionDTO = consultarPostulacionTemporal(idSolicitudGlobal);
        }
        // Almacena en PostulacionFOVIS 3.1.1.1
        PostulacionFOVISModeloDTO postulacionFOVISDTO = solicitudPostulacionDTO.getPostulacion();
        // Cambiar estado del hogar
        postulacionFOVISDTO.setEstadoHogar(EstadoHogarEnum.POSTULADO);

        // Almacena IntegranteHogar con estado Activo
        List<IntegranteHogarModeloDTO> listaIntegranteHogar = solicitudPostulacionDTO.getIntegrantesHogar();

        for (IntegranteHogarModeloDTO integranteHogarDTO : listaIntegranteHogar) {
            integranteHogarDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
        }
        // Agrega JefeHogar a la lista de integrantes activos
        postulacionFOVISDTO.getJefeHogar().setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);

        /* Almacena los datos de la postulación */
        solicitudPostulacionDTO = this.guardarDatosInicialesPostulacion(solicitudPostulacionDTO, TipoPostulacionFOVISEnum.COMPLETA,
                userDTO);

        /* Se consulta si ya tiene un numero de radicación asociado. */
        SolicitudPostulacionModeloDTO solicitud = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());

        /* Si no tiene se radica la solicitud. */
        if (solicitud.getNumeroRadicacion() == null) {
            /* Se radica la solicitud de postulación. */
            this.radicarSolicitud(solicitudPostulacionDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
            /* Se consulta el número de radicación. */
            solicitud = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());
        }
        solicitudPostulacionDTO.setNumeroRadicacion(solicitud.getNumeroRadicacion());

        /* Se actualiza la postulación en estado radicada */
        solicitud.setIdPostulacionFOVIS(solicitudPostulacionDTO.getPostulacion().getIdPostulacion());
        solicitud.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_RADICADA);
        solicitudPostulacionDTO.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_RADICADA);
        solicitud.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
        this.crearActualizarSolicitudPostulacion(solicitud);

        /* Se guardan los datos actualizados temporalmente. */
        // Se eliminan los datos de la postulación del temporal para que se consulten
        solicitudPostulacionDTO.setPostulacion(null);
        this.guardarDatosTemporal(solicitudPostulacionDTO);

        // Termina la tarea
        if (terminarTarea == null || terminarTarea) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put(ESCALADA_ANALISTAS, Boolean.FALSE);
            parametros.put(PROVIENE_FRONT, Boolean.FALSE);
            parametros.put(USUARIO_FRONT, userDTO.getNombreUsuario());
            parametros.put(NUMERO_RADICACION, solicitudPostulacionDTO.getNumeroRadicacion());
            // Si ya existe una instancia de proceso asociada, se termina la tarea
            Long idTarea = solicitudPostulacionDTO.getIdTarea();

            if (idTarea == null) {
                idTarea = consultarTareaActiva(new Long(solicitudPostulacionDTO.getIdInstanciaProceso()));
            }
            terminarTarea(idTarea, parametros);
        }

        return solicitudPostulacionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#cancelarPostulacion(Long)
     */
    @Override
    public void cancelarPostulacion(Long idSolicitudGlobal) {
        logger.debug(Interpolator.interpolate("Inicia cancelarPostulacion(Long) - idSolicitudGlobal:{0}", idSolicitudGlobal));
        SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = consultarPostulacionTemporal(idSolicitudGlobal);
        // Se cambia el estado a cancelada
        this.actualizarEstadoSolicitudPostulacion(idSolicitudGlobal, EstadoSolicitudPostulacionEnum.CANCELADA);
        // Se cierra la postulacion
        this.actualizarEstadoSolicitudPostulacion(idSolicitudGlobal, EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        // Se terminan las tareas del BPM
        AbortarProceso abortarProceso = new AbortarProceso(solicitudPostulacionFOVISDTO.getTipoTransaccionEnum().getProceso(),
                solicitudPostulacionFOVISDTO.getIdInstanciaProceso());
        abortarProceso.execute();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#registrarEscalamientoMultipleFront(
     *com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudPostulacionFOVISDTO registrarEscalamientoMultipleFront(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO,
                                                                           UserDTO userDTO) {
        logger.debug("Inicia servicio registrarEscalamientoPostulacionCompleta");
        Map<String, Object> parametros = new HashMap<String, Object>();

        // Radica la solicitud
        solicitudPostulacionFOVISDTO = radicarPostulacion(solicitudPostulacionFOVISDTO, null, Boolean.FALSE, userDTO);
        actualizarEstadoSolicitudPostulacion(solicitudPostulacionFOVISDTO.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_RADICADA);

        Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = this.registrarEscalamientoAnalistas(parametros,
                solicitudPostulacionFOVISDTO.getEscalamientoMiembrosHogar(),
                solicitudPostulacionFOVISDTO.getEscalamientoTecnicoConstruccion(),
                solicitudPostulacionFOVISDTO.getEscalamientoJuridico(), solicitudPostulacionFOVISDTO.getIdSolicitud(),
                OrigenEscalamientoEnum.FRONT, userDTO);
        solicitudPostulacionFOVISDTO.setEscalamientoJuridico(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
        solicitudPostulacionFOVISDTO.setEscalamientoMiembrosHogar(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
        solicitudPostulacionFOVISDTO
                .setEscalamientoTecnicoConstruccion(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));
        parametros.put(ESCALADA_ANALISTAS, Boolean.TRUE);
        parametros.put(PROVIENE_FRONT, Boolean.TRUE);
        parametros.put(USUARIO_FRONT, userDTO.getNombreUsuario());
        parametros.put(NUMERO_RADICACION, solicitudPostulacionFOVISDTO.getNumeroRadicacion());

        // Si ya existe una instancia de proceso asociada, se termina la tarea
        Long idTarea = solicitudPostulacionFOVISDTO.getIdTarea();

        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudPostulacionFOVISDTO.getIdInstanciaProceso()));
        }

        terminarTarea(idTarea, parametros);
        actualizarEstadoSolicitudPostulacion(solicitudPostulacionFOVISDTO.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA);
        solicitudPostulacionFOVISDTO.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA);
        guardarDatosTemporal(solicitudPostulacionFOVISDTO);
        logger.debug("Finaliza servicio registrarEscalamientoPostulacionCompleta");
        return solicitudPostulacionFOVISDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#consultarPostulacionTemporal(Long)
     */
    @Override
    public SolicitudPostulacionFOVISDTO consultarPostulacionTemporal(Long idSolicitudGlobal) {
        try {
            logger.debug(Interpolator.interpolate("Inicia consultarPostulacionTemporal(Long) - idSolicitudGlobal:{0}", idSolicitudGlobal));
            String jsonPayload = consultarDatosTemporales(idSolicitudGlobal);
            ObjectMapper mapper = new ObjectMapper();
            SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = mapper.readValue(jsonPayload, SolicitudPostulacionFOVISDTO.class);
            logger.debug("Fin de servicio consultarPostulacionTemporal");
            return solicitudPostulacionFOVISDTO;
        } catch (IOException e) {
            logger.error("Ocurrio un error de conversión", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#guardarPostulacionTemporal(
     *com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudPostulacionFOVISDTO guardarPostulacionTemporal(SolicitudPostulacionFOVISDTO solicitudPostulacion, UserDTO userDTO) {
        logger.debug("Inicio de servicio guardarPostulacionTemporal(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISModelDTO)");
        /* Si no existe la solicitud se crea. */
        if (solicitudPostulacion.getIdSolicitud() == null) {
            SolicitudPostulacionModeloDTO solicitud = this.crearSolicitudPostulacionInicial(solicitudPostulacion, userDTO);
            /* Se inicia el Proceso BPM Postulación */
            Long idInstanciaProceso = this.iniciarProcesoPostulacion(solicitud.getIdSolicitud(),
                    solicitudPostulacion.getTipoTransaccionEnum().getProceso(), userDTO);
            solicitudPostulacion.setIdInstanciaProceso(idInstanciaProceso);
            solicitudPostulacion.setIdSolicitud(solicitud.getIdSolicitud());
            solicitudPostulacion.setIdSolicitudPostulacion(solicitud.getIdSolicitudPostulacion());
            solicitudPostulacion.setNumeroRadicacion(solicitud.getNumeroRadicacion());
        }
        guardarDatosTemporal(solicitudPostulacion);

        logger.debug("Fin de servicio guardarPostulacionTemporal(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISModelDTO)");
        return solicitudPostulacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#calcularValorSFV(com.asopagos.enumeraciones.fovis.ModalidadEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, String, Boolean, String, Double, Double, Long, com.asopagos.enumeraciones.fovis.CondicionHogarEnum)
     */
    @Override
    public Double calcularValorSFV(ModalidadEnum modalidad, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                   Boolean beneficiarioViviendaMejoramientoSaludable, String departamentoSolucionVivienda, Double valorSolucionVivienda,
                                   Double ingresosHogar, Long idPostulacion, CondicionHogarEnum condicionHogar, NombreCondicionEspecialEnum condicionEspecial) {
        Double valorSFV = 0d;
        Double smlmv = obtenerParametroDouble(ParametrosSistemaConstants.SMMLV);
        Double totalIngresosHogar = ingresosHogar;

        // Calcula total de ingresos del hogar
        if (totalIngresosHogar == null) {
            totalIngresosHogar = 0d;

            JefeHogarModeloDTO jefeHogar = this.consultarJefeHogar(tipoIdentificacion, numeroIdentificacion);
            if (jefeHogar != null && jefeHogar.getIngresosMensuales() != null) {
                totalIngresosHogar += jefeHogar.getIngresosMensuales().doubleValue();
            } else {
                ConsultarAfiliadoOutDTO afiliadoJefeHogar = consultarAfiliado(tipoIdentificacion, numeroIdentificacion);

                if (afiliadoJefeHogar != null && !afiliadoJefeHogar.getInformacionLaboralTrabajador().isEmpty()
                        && afiliadoJefeHogar.getInformacionLaboralTrabajador().get(0).getValorSalario() != null) {
                    totalIngresosHogar += afiliadoJefeHogar.getInformacionLaboralTrabajador().get(0).getValorSalario().doubleValue();
                } else {
                    logger.error("No se encuentra información laboral del afiliado - jefe del hogar");
                }

            }


            List<IntegranteHogarModeloDTO> listaIntegranteDTO = consultarListaIntegranteHogar(tipoIdentificacion, numeroIdentificacion, idPostulacion);

            for (IntegranteHogarModeloDTO integranteHogarDTO : listaIntegranteDTO) {
                if (integranteHogarDTO.getIngresosMensuales() != null) {
                    totalIngresosHogar += integranteHogarDTO.getIngresosMensuales().doubleValue();
                }
            }
        }

        Double cantidadSMLMVIngresosHogar = totalIngresosHogar / smlmv;

        // Consulta los parametros fovis
        ConsultarDatosGeneralesFovis consultarDatosGeneralesFovis = new ConsultarDatosGeneralesFovis();
        consultarDatosGeneralesFovis.execute();
        List<ParametrizacionFOVISModeloDTO> listParametros = consultarDatosGeneralesFovis.getResult();
        // Se obtiene el parámetro de LIMITE_CUANTIA_SUBSIDIO
        BigDecimal parametroLimiteCuantiaSubsidio = null;
        for (ParametrizacionFOVISModeloDTO parametrizacionFOVISModeloDTO : listParametros) {
            if (ParametroFOVISEnum.LIMITE_CUANTIA_SUBSIDIO.equals(parametrizacionFOVISModeloDTO.getParametro())) {
                parametroLimiteCuantiaSubsidio = parametrizacionFOVISModeloDTO.getValorNumerico();
                break;
            }
        }
        // Consulta departamento con marca excepción FOVIS
        DepartamentoModeloDTO departamentoExcepcionFOVIS = consultarDepartamentoExcepcionFOVIS();

        // Si el valor del campo "Departamento ubicación del proyecto o solución de vivienda" es "Archipiélago de San Andrés, Providencia y Santa Catalina"
        // y la modalidad es URBANA: “Adquisición de vivienda nueva o usada, Mejoramiento de vivienda, Mejoramiento para vivienda saludable y Construcción en sitio propio”
        if ((ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA.equals(modalidad)
                || ModalidadEnum.ADQUISICION_VIVIENDA_USADA_URBANA.equals(modalidad)
                || ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.equals(modalidad)
                || ModalidadEnum.MEJORAMIENTO_VIVIENDA_URBANA.equals(modalidad)
                || ModalidadEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE.equals(modalidad))
                && (departamentoSolucionVivienda != null && !departamentoSolucionVivienda.isEmpty()
                && departamentoSolucionVivienda.equals(departamentoExcepcionFOVIS.getIdDepartamento().toString()))) {
            Double cantidadSMLMVSanAndresHogar = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_HOGAR);

            if (totalIngresosHogar < (smlmv * cantidadSMLMVSanAndresHogar)) {
                Double cantidadSMLMVSanAndresSFV = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_SFV);
                valorSFV = smlmv * cantidadSMLMVSanAndresSFV;
            }
        } else if ((ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.equals(modalidad)
                || ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA.equals(modalidad)
                || ModalidadEnum.ADQUISICION_VIVIENDA_USADA_URBANA.equals(modalidad))
                && (CondicionHogarEnum.HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO.equals(condicionHogar)
                || NombreCondicionEspecialEnum.DAMNIFICADO_DESASTRE_NATURAL.equals(condicionEspecial))
        ) {

            Double cantidadSMLMVSanAndresHogar = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_HOGAR);

            if (totalIngresosHogar < (smlmv * cantidadSMLMVSanAndresHogar)) {
                Double cantidadSMLMVSanAndresSFV = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_SFV);
                valorSFV = smlmv * cantidadSMLMVSanAndresSFV;
            }

        } else if ((ModalidadEnum.MEJORAMIENTO_VIVIENDA_URBANA.equals(modalidad)
                || ModalidadEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE.equals(modalidad))
                && (CondicionHogarEnum.HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO.equals(condicionHogar)
                || NombreCondicionEspecialEnum.DAMNIFICADO_DESASTRE_NATURAL.equals(condicionEspecial))
        ) {

            Double cantidadSMLMVSanAndresHogar = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_HOGAR);

            if (totalIngresosHogar < (smlmv * cantidadSMLMVSanAndresHogar)) {
                Double cantidadSMLMVMejoramientoViviendaSFV = obtenerParametroDouble(ParametrosSistemaConstants.CANT_SMLMV_MEJORAMIENTO_SFV);
                valorSFV = smlmv * cantidadSMLMVMejoramientoViviendaSFV;
            }

        } else {
            // Realiza el cálculo del subsidio, de acuerdo a la modalidad
            // Se consultan los rangos de la modalidad
            List<RangoTopeValorSFVModeloDTO> listRangoTopeModalidad = consultarRangosSVFPorModalidad(modalidad);
            BigDecimal topeSFV = obtenerTopeSFV(listRangoTopeModalidad, cantidadSMLMVIngresosHogar);

            if (topeSFV.compareTo(BigDecimal.ZERO) > 0) {
                valorSFV = (smlmv * topeSFV.doubleValue());
            }
            // Si la modalidad es 6 o 7 y algun miembro del hogar ha sido beneficiario de vivienda mejoramiento saludable
            // Se resta 8 SMMLV Ver. HU321-023 Sección 3.1.2.7
            if ((ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.equals(modalidad)
                    || ModalidadEnum.MEJORAMIENTO_VIVIENDA_URBANA.equals(modalidad))
                    && beneficiarioViviendaMejoramientoSaludable
                    && valorSFV > 0) {
                valorSFV -= (smlmv * NumerosEnterosConstants.OCHO);
            }
        }

        // Se obtiene el porcentaje limite de cuantia del subisidio
        Double porcentajeMaximoSolucionViviendaSFV = parametroLimiteCuantiaSubsidio.doubleValue() / 100d;
        // Se calcula el valor porcentual maximo de solución de vivienda
        Double valorPorcentualSolucionVivienda = valorSolucionVivienda * porcentajeMaximoSolucionViviendaSFV;
        if (valorSFV > valorPorcentualSolucionVivienda) {
            return valorPorcentualSolucionVivienda;
        }

        return valorSFV;
    }

    /**
     * Obtiene el tope de SFV de la lista de rangos y por el salario enviado
     *
     * @param listRangoTopeModalidad     Lista de rangos tope
     * @param cantidadSMLMVIngresosHogar Ingresos del hogar
     * @return Valor tope de subsidio familiar de vivienda
     */
    private BigDecimal obtenerTopeSFV(List<RangoTopeValorSFVModeloDTO> listRangoTopeModalidad, Double cantidadSMLMVIngresosHogar) {
        BigDecimal topeSVF = BigDecimal.ZERO;
        if (listRangoTopeModalidad == null || listRangoTopeModalidad.isEmpty()) {
            return topeSVF;
        }
        for (RangoTopeValorSFVModeloDTO rangoTope : listRangoTopeModalidad) {
            if (RangoTopeUtils.verificarRangoOperador(rangoTope.getOperadorValorMinimo(), cantidadSMLMVIngresosHogar,
                    rangoTope.getValorMinimo().doubleValue())
                    && RangoTopeUtils.verificarRangoOperador(rangoTope.getOperadorValorMaximo(), cantidadSMLMVIngresosHogar,
                    rangoTope.getValorMaximo().doubleValue())) {
                topeSVF = rangoTope.getTopeSMLMV();
                break;
            }
        }
        return topeSVF;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#radicarPostulacionInicial(com.asopagos.dto.fovis .
     * SolicitudPostulacionFOVISDTO, com.asopagos.enumeraciones.TipoPostulacionFOVISEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudPostulacionFOVISDTO radicarPostulacionInicial(SolicitudPostulacionFOVISDTO solicitudPostulacion,
                                                                  TipoPostulacionFOVISEnum tipoPostulacion, UserDTO userDTO) {
        logger.debug("Inicio de servicio radicarPostulacionInicial(SolicitudPostulacionFOVISDTO, TipoPostulacionFOVISEnum)");
        Map<String, Object> parametrosBPM = new HashMap<>();

        if (TipoPostulacionFOVISEnum.COMPLETA.equals(tipoPostulacion)) {

            // Mantis 0246930
            for (IntegranteHogarModeloDTO integranteHogarModeloDTO : solicitudPostulacion.getIntegrantesHogar()) {
                if (integranteHogarModeloDTO.getIdPersona() == null) {
                    integranteHogarModeloDTO.setIdPersona(crearPersona(integranteHogarModeloDTO));
                }
            }

            this.guardarPostulacionTemporal(solicitudPostulacion, userDTO);
            parametrosBPM.put(RADICACION_ABREVIADA, Boolean.FALSE);
        } else {
            /* Almacena la información de la postulación. */
            solicitudPostulacion = guardarDatosInicialesPostulacion(solicitudPostulacion, tipoPostulacion, userDTO);
            /* Si no existe una instancia de proceso asociada */
            if (solicitudPostulacion.getIdInstanciaProceso() == null) {
                /* Se inicia el Proceso BPM Postulación */
                Long idInstanciaProceso = this.iniciarProcesoPostulacion(solicitudPostulacion.getIdSolicitud(),
                        solicitudPostulacion.getTipoTransaccionEnum().getProceso(), userDTO);
                solicitudPostulacion.setIdInstanciaProceso(idInstanciaProceso);
            }
            if (TipoPostulacionFOVISEnum.ESCALADA.equals(tipoPostulacion)) {
                /* Escalamiento realizado desde el Front HU-020 */
                EscalamientoSolicitudDTO escalamientoEspecialDTO = solicitudPostulacion.getEscalamientoEspecial();
                escalamientoEspecialDTO.setFechaCreacion(new Date());
                escalamientoEspecialDTO.setUsuarioCreacion(userDTO.getNombreUsuario());
                RegistrarEscalamientoSolicitud escalarSolicitud = new RegistrarEscalamientoSolicitud(
                        solicitudPostulacion.getIdSolicitud(), escalamientoEspecialDTO);
                escalarSolicitud.execute();
                EscalamientoSolicitudDTO escalamientoDTO = escalarSolicitud.getResult();
                solicitudPostulacion.setEscalamientoEspecial(escalamientoDTO);
                parametrosBPM.put(ESCALADA_FRONT, Boolean.TRUE);
                parametrosBPM.put(ANALISTA_ESPECIALIZADO, escalamientoDTO.getDestinatario());
            } else if (TipoPostulacionFOVISEnum.ABREVIADA.equals(tipoPostulacion)
                    && CanalRecepcionEnum.PRESENCIAL.equals(solicitudPostulacion.getCanalRecepcion())) {
                parametrosBPM.put(RADICACION_ABREVIADA, Boolean.TRUE);
            } else if (TipoPostulacionFOVISEnum.ABREVIADA.equals(tipoPostulacion)
                    && CanalRecepcionEnum.WEB.equals(solicitudPostulacion.getCanalRecepcion())) {
                /* Se asgina automáticamente al back y se termina la tarea. */
                AsignarSolicitudPostulacionDTO asignarSolPostulacionDTO = new AsignarSolicitudPostulacionDTO();
                asignarSolPostulacionDTO.setDocumentosFisicos(Boolean.FALSE);
                asignarSolPostulacionDTO.setIdSolicitud(solicitudPostulacion.getIdSolicitud());
                asignarSolPostulacionDTO.setMetodoAsignacion(MetodoAsignacionBackEnum.AUTOMATICO);
                asignarSolPostulacionDTO.setTipoTransaccion(solicitudPostulacion.getTipoTransaccionEnum());
                this.asignarSolicitudPostulacion(asignarSolPostulacionDTO, userDTO);
                /* Envía comunicado 91 del proceso web. */
                this.parametrizarNotificacion(false, solicitudPostulacion, userDTO);
            }

        }
        parametrosBPM.put(NUMERO_RADICACION, solicitudPostulacion.getNumeroRadicacion());
        parametrosBPM.put(USUARIO_FRONT, userDTO.getNombreUsuario());
        this.guardarDatosTemporal(solicitudPostulacion);

        if (CanalRecepcionEnum.PRESENCIAL.equals(solicitudPostulacion.getCanalRecepcion())) {
            //Si ya existe una instancia de Proceso asociada se termina la tarea.
            Long idTarea = solicitudPostulacion.getIdTarea();
            if (idTarea == null) {
                idTarea = consultarTareaActiva(new Long(solicitudPostulacion.getIdInstanciaProceso()));
            }
            terminarTarea(idTarea, parametrosBPM);

        }
        logger.debug("Fin de servicio radicarPostulacionInicial(SolicitudPostulacionFOVISDTO, TipoPostulacionFOVISEnum)");
        return solicitudPostulacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#registrarIntentoPostulacion(
     *com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long registrarIntentoPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, UserDTO userDTO) {
        logger.debug("Inicio de servicio registrarIntentoPostulacion");
        Map<String, Object> parametrosBPM = new HashMap<>();
        /* Se almacena la postulación temporal */
        guardarPostulacionTemporal(solicitudPostulacionDTO, userDTO);
        SolicitudModeloDTO solicitudDTO = transformarSolicitudPostulacion(solicitudPostulacionDTO, userDTO);
        guardarSolicitudGlobal(solicitudDTO);

        // Crea el intento de postulación
        IntentoPostulacionDTO intentoPostulacionDTO = new IntentoPostulacionDTO();
        intentoPostulacionDTO.setCausaIntentoFallido(solicitudPostulacionDTO.getCausaIntentoFallido());
        intentoPostulacionDTO.setFechaInicioProceso(Calendar.getInstance().getTime());
        intentoPostulacionDTO.setFechaCreacion(Calendar.getInstance().getTime());
        intentoPostulacionDTO.setIdSolicitud(solicitudPostulacionDTO.getIdSolicitud());
        intentoPostulacionDTO.setSedeCajaCompensacion(userDTO.getSedeCajaCompensacion());
        intentoPostulacionDTO.setTipoTransaccion(solicitudPostulacionDTO.getTipoTransaccionEnum());
        intentoPostulacionDTO.setUsuarioCreacion(userDTO.getNombreUsuario());
        intentoPostulacionDTO.setProceso(ProcesoEnum.POSTULACION_FOVIS_WEB);

        if (solicitudPostulacionDTO.getCanalRecepcion() != null
                && solicitudPostulacionDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL)) {
            intentoPostulacionDTO.setProceso(ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL);
        }

        intentoPostulacionDTO.setModalidad(solicitudPostulacionDTO.getPostulacion().getIdModalidad());
        intentoPostulacionDTO.setTipoSolicitante(ClasificacionEnum.HOGAR.name());

        RegistrarIntentoPostulacionFOVIS registrarIntentoPostulacion = new RegistrarIntentoPostulacionFOVIS(intentoPostulacionDTO);
        registrarIntentoPostulacion.execute();
        intentoPostulacionDTO.setIdIntentoPostulacion(registrarIntentoPostulacion.getResult());

        this.actualizarEstadoSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
        this.actualizarEstadoSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

        if (CausaIntentoFallidoPostulacionEnum.VALIDACION_REQUISITOS_DOCUMENTALES
                .equals(intentoPostulacionDTO.getCausaIntentoFallido())) {

            List<IntentoPostulacionRequisitoModeloDTO> listaRequisitosSinCumplir = new ArrayList<>();
            if (solicitudPostulacionDTO.getListaChequeo() != null
                    && solicitudPostulacionDTO.getListaChequeo().getListaChequeo() != null) {
                for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getListaChequeo().getListaChequeo()) {
                    if ((itemChequeoDTO.getCumpleRequisito() != null && !itemChequeoDTO.getCumpleRequisito())
                            || (itemChequeoDTO.getCumpleRequisitoBack() != null && !itemChequeoDTO.getCumpleRequisitoBack())) {
                        listaRequisitosSinCumplir.add(new IntentoPostulacionRequisitoModeloDTO(
                                intentoPostulacionDTO.getIdIntentoPostulacion(), itemChequeoDTO.getIdRequisito()));
                    }
                }
            }

            if (solicitudPostulacionDTO.getPostulacion() != null
                    && solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar() != null
                    && solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar().getListaChequeo() != null) {
                for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar()
                        .getListaChequeo()) {
                    if ((itemChequeoDTO.getCumpleRequisito() != null && !itemChequeoDTO.getCumpleRequisito())
                            || (itemChequeoDTO.getCumpleRequisitoBack() != null && !itemChequeoDTO.getCumpleRequisitoBack())) {
                        listaRequisitosSinCumplir.add(new IntentoPostulacionRequisitoModeloDTO(
                                intentoPostulacionDTO.getIdIntentoPostulacion(), itemChequeoDTO.getIdRequisito()));
                    }
                }
            }
            if (solicitudPostulacionDTO.getIntegrantesHogar() != null) {
                for (IntegranteHogarModeloDTO integranteHogarModeloDTO : solicitudPostulacionDTO.getIntegrantesHogar()) {
                    if (integranteHogarModeloDTO.getListaChequeo() != null
                            && integranteHogarModeloDTO.getListaChequeo().getListaChequeo() != null) {
                        for (ItemChequeoDTO itemChequeoDTO : integranteHogarModeloDTO.getListaChequeo().getListaChequeo()) {
                            if ((itemChequeoDTO.getCumpleRequisito() != null && !itemChequeoDTO.getCumpleRequisito())
                                    || (itemChequeoDTO.getCumpleRequisitoBack() != null && !itemChequeoDTO.getCumpleRequisitoBack())) {
                                listaRequisitosSinCumplir.add(new IntentoPostulacionRequisitoModeloDTO(
                                        intentoPostulacionDTO.getIdIntentoPostulacion(), itemChequeoDTO.getIdRequisito()));
                            }
                        }
                    }
                }
            }
            RegistrarIntentoPostulacionRequisito registrarIntentoPostulacionRequisito = new RegistrarIntentoPostulacionRequisito(
                    listaRequisitosSinCumplir);
            registrarIntentoPostulacionRequisito.execute();
        }

        if (ProcesoEnum.POSTULACION_FOVIS_WEB.equals(solicitudPostulacionDTO.getTipoTransaccionEnum().getProceso())) {
            /* Se envía el Comunicado #92 por intento de postulación WEB */
            parametrizarNotificacion(true, solicitudPostulacionDTO, userDTO);
            if (solicitudPostulacionDTO.getIdInstanciaProceso() != null) {
                AbortarProceso aborProceso = new AbortarProceso(solicitudPostulacionDTO.getTipoTransaccionEnum().getProceso(),
                        solicitudPostulacionDTO.getIdInstanciaProceso());
                aborProceso.execute();
            }
        } else {
            parametrosBPM.put(INTENTO_POSTULACION, Boolean.TRUE);
            parametrosBPM.put(USUARIO_FRONT, userDTO.getNombreUsuario());
            Long idTarea = solicitudPostulacionDTO.getIdTarea();
            if (idTarea == null) {
                idTarea = consultarTareaActiva(new Long(solicitudPostulacionDTO.getIdInstanciaProceso()));
            }
            terminarTarea(idTarea, parametrosBPM);
        }
        logger.debug("Fin de servicio registrarIntentoPostulacion");
        return registrarIntentoPostulacion.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService
     * #asignarSolicitudPostulacion(com.asopagos.fovis.composite.dto.AsignarSolicitudPostulacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void asignarSolicitudPostulacion(AsignarSolicitudPostulacionDTO entrada, UserDTO userDTO) {
        String destinatario = null;
        String sedeDestinatario = null;
        String observacion = null;
        Long idTarea;
        UsuarioDTO usuarioDTO = new UsuarioCCF();
        if (MetodoAsignacionBackEnum.AUTOMATICO.equals(entrada.getMetodoAsignacion())
                || entrada.getTipoTransaccion().getProceso().getWeb()) {
            if (entrada.getTipoTransaccion().getProceso().getWeb()) {
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(null, entrada.getTipoTransaccion().getProceso());
            } else {
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                        entrada.getTipoTransaccion().getProceso());
            }
            usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            sedeDestinatario = usuarioDTO.getCodigoSede();
            observacion = null;
        } else if (MetodoAsignacionBackEnum.MANUAL.equals(entrada.getMetodoAsignacion())) {
            // se busca el usuario a quien se le asigna la tarea, por su nombe
            // de usuario
            usuarioDTO = consultarUsuarioCajaCompensacion(entrada.getUsuarioBack());
            sedeDestinatario = usuarioDTO.getCodigoSede();
            destinatario = usuarioDTO.getNombreUsuario();
            observacion = entrada.getObservacion();
        }

        /* se actualiza la solicitud de postulación */
        SolicitudPostulacionModeloDTO solicitudPostulacion = consultarSolicitudPostulacion(entrada.getIdSolicitud());
        solicitudPostulacion.setDestinatario(destinatario);
        solicitudPostulacion.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
        solicitudPostulacion.setObservacion(observacion);

        /* se cambia el estado de la soliciutd de acuerdo a los documentos. */
        if (entrada.getDocumentosFisicos()) {
            solicitudPostulacion.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS);
        } else {
            if (entrada.getTipoTransaccion() != null
                    && ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL.equals(entrada.getTipoTransaccion().getProceso())) {
                solicitudPostulacion.setEstadoDocumentacion(EstadoDocumentacionEnum.ENVIADA_AL_BACK);
            }
            solicitudPostulacion.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.ASIGNADA_AL_BACK);
        }
        this.crearActualizarSolicitudPostulacion(solicitudPostulacion);

        Map<String, Object> params = null;
        params = new HashMap<>();
        params.put(USUARIO_BACK, destinatario);
        params.put(SENAL_DOCUMENTOS_FISICOS, entrada.getDocumentosFisicos());
        params.put(NUMERO_RADICACION, solicitudPostulacion.getNumeroRadicacion());

        idTarea = entrada.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudPostulacion.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService
     * #validarAfiliadosPostulacion(com.asopagos.dto.modelo.PersonaModeloDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<PersonaModeloDTO> validarAfiliadosPostulacion(PersonaModeloDTO personaDTO, UserDTO userDTO) {
        List<PersonaModeloDTO> personasReturn = new ArrayList<>();
        logger.debug("Se inicia el servicio de validarAfiliadosPostulacion(PersonaModeloDTO)");
        /* Se consulta las personas con los filtros ingresados */
        BuscarPersonas buscarPersonas = new BuscarPersonas(personaDTO.getFechaNacimiento(), personaDTO.getPrimerApellido(), null, null,
                personaDTO.getPrimerNombre(), null, personaDTO.getNumeroIdentificacion(), personaDTO.getTipoIdentificacion(), null);
        buscarPersonas.execute();
        List<PersonaDTO> personasResult = buscarPersonas.getResult();
        Map<String, String> datosValidacion = new HashMap<>();
        if (personasResult != null && !personasResult.isEmpty()) {
            for (PersonaDTO personaResult : personasResult) {
                PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                personaModeloDTO.convertFromPersonaDTO(personaResult);
                datosValidacion.put(NUMERO_IDENTIFICACION, personaResult.getNumeroIdentificacion());
                datosValidacion.put(TIPO_IDENTIFICACION, personaResult.getTipoIdentificacion().name());
                datosValidacion.put(PRIMER_NOMBRE, personaResult.getPrimerNombre());
                datosValidacion.put(PRIMER_APELLIDO, personaResult.getPrimerApellido());
                if (personaResult.getFechaNacimiento() != null) {
                    datosValidacion.put(FECHA_NACIMIENTO, personaResult.getFechaNacimiento().toString());
                }
                /* Se valida cada persona para saber si es postulable a FOVIS. */
                ValidarReglasNegocioFovis validarReglasNegocio = new ValidarReglasNegocioFovis("321-020-1",
                        ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL, ClasificacionEnum.JEFE_HOGAR.name(), datosValidacion);
                validarReglasNegocio.execute();
                List<ValidacionDTO> resultadoValidaciones = validarReglasNegocio.getResult();
                personaModeloDTO.setPostulableFOVIS(Boolean.TRUE);
                for (ValidacionDTO validacionDTO : resultadoValidaciones) {
                    if (ResultadoValidacionEnum.NO_APROBADA.equals(validacionDTO.getResultado())
                            && validacionDTO.getTipoExcepcion() != null
                            && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacionDTO.getTipoExcepcion())) {
                        personaModeloDTO.setPostulableFOVIS(Boolean.FALSE);
                        break;
                    }
                }
                personasReturn.add(personaModeloDTO);
            }
        }
        logger.debug("Finaliza el servicio de validarAfiliadosPostulacion(PersonaModeloDTO personaDTO)");
        return personasReturn;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#analizarSolicitudPostulacion(
     *com.asopagos.fovis.composite.dto.AnalisisSolicitudPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void analizarSolicitudPostulacion(AnalisisSolicitudPostulacionDTO analisisSolicitud, UserDTO userDTO) {
        logger.debug("Inicia servicio analizarSolicitudPostulacion(AnalisisSolicitudPostulacionDTO, UserDTO)");
        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(analisisSolicitud.getIdSolicitud());

        RegistrarEscalamientoSolicitud registrarEscalamientoSolicitud = new RegistrarEscalamientoSolicitud(
                analisisSolicitud.getIdSolicitud(), analisisSolicitud.getEscalamientoSolicitud());
        registrarEscalamientoSolicitud.execute();
        EscalamientoSolicitudDTO escalamiento = registrarEscalamientoSolicitud.getResult();
        if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR.equals(escalamiento.getTipoAnalistaFOVIS())) {
            if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                solicitudTemporal.setEscalamientoMiembrosHogar(escalamiento);
            } else {
                solicitudTemporal.setEscalamientoMiembrosHogarBack(escalamiento);
            }
        } else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO.equals(escalamiento.getTipoAnalistaFOVIS())) {
            if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                solicitudTemporal.setEscalamientoJuridico(escalamiento);
            } else {
                solicitudTemporal.setEscalamientoJuridicoBack(escalamiento);
            }
        } else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO.equals(escalamiento.getTipoAnalistaFOVIS())) {
            if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                solicitudTemporal.setEscalamientoTecnicoConstruccion(escalamiento);
            } else {
                solicitudTemporal.setEscalamientoTecnicoConstruccionBack(escalamiento);
            }
        } else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_ESPECIALIZADO.equals(escalamiento.getTipoAnalistaFOVIS())) {
            solicitudTemporal.setEscalamientoEspecial(escalamiento);
        }

        if (!existenEscalamientosSinResultado(analisisSolicitud.getIdSolicitud())) {

            actualizarEstadoSolicitudPostulacion(analisisSolicitud.getIdSolicitud(),
                    EstadoSolicitudPostulacionEnum.GESTIONADA_POR_ESPECIALISTA);
            solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.GESTIONADA_POR_ESPECIALISTA);
        }
        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = analisisSolicitud.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, null);
        // Se eliminan los datos de la postulación del temporal para que se consulten
        solicitudTemporal.setPostulacion(null);
        guardarPostulacionTemporal(solicitudTemporal, userDTO);
        logger.debug("Finaliza servicio analizarSolicitudPostulacion(AnalisisSolicitudPostulacionDTO, UserDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#finalizarAnalisisPostulacion(
     *com.asopagos.fovis.composite.dto.ResultadoAnalisisPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    public void finalizarAnalisisPostulacion(ResultadoAnalisisPostulacionDTO resultadoAnalisis, UserDTO userDTO) {
        logger.debug("Se inicia el servicio de finalizarAnalisisPostulacion(ResultadoAnalisisPostulacionDTO, UserDTO)");
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoAnalisisEsp", resultadoAnalisis.getResultadoAnalisisEsp());
        params.put("provieneFront", resultadoAnalisis.getProvieneFront());

        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(resultadoAnalisis.getIdSolicitud());

        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(
                resultadoAnalisis.getIdSolicitud());

        // caso 1: solicitud procedente, caso 2: solicitud no procedente
        switch (resultadoAnalisis.getResultadoAnalisisEsp()) {
            case 1:
                // actualizar el estado de la solicitud dependiendo si viene del front o no
                if (resultadoAnalisis.getProvieneFront() == null || resultadoAnalisis.getProvieneFront()) {

                    actualizarEstadoSolicitudPostulacion(resultadoAnalisis.getIdSolicitud(),
                            EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_AL_BACK);

                    solicitudPostulacionDTO = consultarSolicitudPostulacion(resultadoAnalisis.getIdSolicitud());

                    // cambiar estado del Hogar (postulacionFovis)
                    cambiarEstadoHogar(solicitudPostulacionDTO.getIdPostulacionFOVIS(), EstadoHogarEnum.POSTULADO);

                    // cambiar estado con respecto al hogar
                    for (IntegranteHogarModeloDTO integranteHogarDTO : solicitudTemporal.getIntegrantesHogar()) {
                        integranteHogarDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
                        integranteHogarDTO = crearActualizarIntegranteHogar(integranteHogarDTO);
                    }

                    solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_AL_BACK);
                    solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.POSTULADO);
                    actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                    // Se eliminan los datos de la postulación del temporal para que se consulten
                    solicitudTemporal.setPostulacion(null);
                } else {

                    solicitudPostulacionDTO = consultarSolicitudPostulacion(resultadoAnalisis.getIdSolicitud());

                    // cambiar estado del Hogar (postulacionFovis)
                    cambiarEstadoHogar(solicitudPostulacionDTO.getIdPostulacionFOVIS(), EstadoHogarEnum.POSTULADO);

                    actualizarParametrizacionModalidadPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS());

                    actualizarEstadoSolicitudPostulacion(resultadoAnalisis.getIdSolicitud(),
                            EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);
                    solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);
                    solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.POSTULADO);
                    actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                }
                break;
            case 2:
                // Se rechaza solicitud de postulación
                actualizarEstadoSolicitudPostulacion(resultadoAnalisis.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);

                // Se cierra la solicitud de postulación
                actualizarEstadoSolicitudPostulacion(resultadoAnalisis.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

                cambiarEstadoHogar(solicitudPostulacionDTO.getIdPostulacionFOVIS(), EstadoHogarEnum.RECHAZADO);
                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
                solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.RECHAZADO);
                actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                break;
            default:
                break;
        }
        guardarPostulacionTemporal(solicitudTemporal, userDTO);
        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = resultadoAnalisis.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);
        logger.debug("Finaliza el servicio de finalizarAnalisisPostulacion(ResultadoAnalisisPostulacionDTO, UserDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#verificarGestionPNCPostulacion(
     *com.asopagos.fovis.composite.dto.VerificacionGestionPNCPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarGestionPNCPostulacion(VerificacionGestionPNCPostulacionDTO datosVerificacion, UserDTO userDTO) {
        logger.debug("Se inicia el servicio de verificarGestionPNCPostulacion(VerificacionGestionPNCPostulacionDTO, UserDTO)");
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoVerifPNC", datosVerificacion.getResultadoVerifPNC());

        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(datosVerificacion.getIdSolicitud());

        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(datosVerificacion.getIdSolicitud());

        ConsultarPostulacionFOVIS consultarPostulacion = new ConsultarPostulacionFOVIS(solicitudPostulacionDTO.getIdPostulacionFOVIS());
        consultarPostulacion.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModelo = consultarPostulacion.getResult();

        solicitudTemporal.getPostulacion().setEstadoHogar(postulacionFOVISModelo.getEstadoHogar());
        solicitudTemporal.getPostulacion().setIdPostulacion(postulacionFOVISModelo.getIdPostulacion());

        // se guarda la información de la postulación
        guardarDatosInicialesPostulacion(solicitudTemporal, null, userDTO);
        // caso 1: Remitir postulación para cruces, caso 2: rechazada, caso 3: escalada
        switch (datosVerificacion.getResultadoVerifPNC()) {
            case 1:
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);
                cambiarEstadoHogar(postulacionFOVISModelo.getIdPostulacion(), EstadoHogarEnum.POSTULADO);

                solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.POSTULADO);
                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);
                actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                break;
            case 2:
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
                cambiarEstadoHogar(postulacionFOVISModelo.getIdPostulacion(), EstadoHogarEnum.RECHAZADO);

                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
                solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.RECHAZADO);
                actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                break;
            case 3:
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA_POR_BACK);
                Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = registrarEscalamientoAnalistas(params,
                        datosVerificacion.getEscalamientoMiembroHogar(), datosVerificacion.getEscalamientoTecnico(),
                        datosVerificacion.getEscalamientoJuridico(), datosVerificacion.getIdSolicitud(),
                        OrigenEscalamientoEnum.BACK, userDTO);
                solicitudTemporal.setEscalamientoJuridicoBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
                solicitudTemporal.setEscalamientoMiembrosHogarBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
                solicitudTemporal
                        .setEscalamientoTecnicoConstruccionBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));
                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA_POR_BACK);
                actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                // Se eliminan los datos de la postulación del temporal para que se consulten
                solicitudTemporal.setPostulacion(null);
                break;
        }
        guardarPostulacionTemporal(solicitudTemporal, userDTO);
        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = datosVerificacion.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);
        logger.debug("Finaliza el servicio de verificarGestionPNCPostulacion(VerificacionGestionPNCPostulacionDTO, UserDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisCompositeService#registrarVerificacionAControlInterno(List, List)
     */
    @Override
    public void registrarVerificacionAControlInterno(RegistrarVerificacionControlInternoDTO registroVerificacionDTO, UserDTO userDTO) {
        logger.debug("Inicia registrarVerificacionAControlInterno(RegistrarVerificacionControlInternoDTO, UserDTO)");
        List<UserDTO> usuariosControlInterno = registroVerificacionDTO.getUsuariosControlInterno();
        List<SolicitudPostulacionModeloDTO> solicitudesPostulacion = registroVerificacionDTO.getSolicitudesPostulacion();

        try {

            Integer cntPostulaciones = solicitudesPostulacion.size();
            Integer cntPostulacionesAux = 0;
            Integer cntUsuarios = usuariosControlInterno.size();
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

            ExecutorService executor = Executors.newFixedThreadPool(cntUsuarios);
            List<Callable<List<AsignacionTurnosDTO>>> callables = new ArrayList<>();

            for (int i = 0; i < cntUsuarios; i++) {
                UserDTO user = usuariosControlInterno.get(i);
                user.setSedeCajaCompensacion("1");
                user.setEmail(user.getNombreUsuario());
                String nombreUsuario = user.getNombreUsuario();
                if (modulo > 0) {
                    indexFin++;
                    modulo--;
                }
                List<SolicitudPostulacionModeloDTO> postulacionesXUsuario = fragmentoArrayVerificacion(solicitudesPostulacion, indexIni, indexFin);
                indexIni = indexFin;
                indexFin += distribucionPost;

                callables.add(() -> registrarSolicitudEIniciarProcesoVerificacionFovis(postulacionesXUsuario, userDTO, user, tope));

            }

            List<Future<List<AsignacionTurnosDTO>>> result = new ArrayList<>();
            result = managedExecutorService.invokeAll(callables);
            for (Future<List<AsignacionTurnosDTO>> future : result) {
                List<AsignacionTurnosDTO> outDTO = future.get();
                for (int i = 0; i < outDTO.size(); i++) {
                    AsignacionTurnosDTO asignacionTurnosDTO = outDTO.get(i);
                    SolicitudVerificacionFovisModeloDTO solicitud = this.consultarSolicitudVerificacionFovis(asignacionTurnosDTO.getIdSolicitud());
                    Long idInstancia = this.iniciarProcesoVerificacionPostulacionFovis(solicitud.getIdSolicitud(), solicitud.getNumeroRadicacion(), TipoSolicitudVerificacionFovisEnum.VERIFICACION, userDTO, asignacionTurnosDTO.getUsuarioAsignar());
                    solicitud.setIdInstanciaProceso(idInstancia.toString());
                    solicitud.setIdPostulacionFOVIS(null);
                    solicitud.setTipoVerificacion(null);
                    solicitud.setDestinatario(asignacionTurnosDTO.getUsuarioAsignar().getNombreUsuario());
                    solicitud.setSedeDestinatario(asignacionTurnosDTO.getUsuarioAsignar().getSedeCajaCompensacion());
                    solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);
                    // guardar temporal
                    SolicitudVerificacionFovisDTO solicitudVerificacion = new SolicitudVerificacionFovisDTO();
                    // Se elimina la información de la postulación para que se consulte la mas actualizada
                    solicitudVerificacion.setDatosPostulacionFovis(null);
                    solicitudVerificacion.setSolicitudVerificacionFovisModeloDTO(solicitud);
                    solicitudVerificacion.setIdSolicitud(solicitud.getIdSolicitud());
                    solicitudVerificacion.setIdInstanciaProceso(idInstancia);
                    guardarDatosTemporalVerificacion(solicitudVerificacion);
                }

            }
        } catch (Exception e) {
            logger.error("Error - Finaliza servicio registrarVerificacionAControlInterno", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug("Finaliza registrarVerificacionAControlInterno(RegistrarVerificacionControlInternoDTO, UserDTO)");
    }

    public static List<SolicitudPostulacionModeloDTO> fragmentoArrayVerificacion(List<SolicitudPostulacionModeloDTO> array, int ini, int fin) {
        List<SolicitudPostulacionModeloDTO> newArray = new ArrayList<SolicitudPostulacionModeloDTO>();
        for (int i = ini; i < fin; i++) {
            newArray.add(array.get(i));
        }
        return newArray;
    }

    /**
     * Método que registra una solicitud de verificacion Fovis y se inicia el proceso de legalizacion y desembolso.
     *
     * @param solicitudVerificacionFovis Datos de la solicitud a actualizar
     * @param userDTO                    Usuario del contexto de seguridad
     * @param radicado
     */
    private List<AsignacionTurnosDTO> registrarSolicitudEIniciarProcesoVerificacionFovis(List<SolicitudPostulacionModeloDTO> solicitudesPostulacion,
                                                                                         UserDTO userDTO, UserDTO usuarioAsignar, Integer tope) {

        Integer cont = 0;
        String radicado = "";
        List<AsignacionTurnosDTO> respuesta = new ArrayList<>();

        try {

            for (int j = 0; j < solicitudesPostulacion.size(); j++) {

                SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = solicitudesPostulacion.get(j);
                SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(
                        solicitudPostulacionModeloDTO.getIdSolicitud());

                TipoSolicitudVerificacionFovisEnum tipoVerificacion = null;

                if (EstadoHogarEnum.POSTULADO.equals(solicitudPostulacionModeloDTO.getPostulacionFOVISModeloDTO().getEstadoHogar())) {
                    solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.ASIGNADA_A_CONTROL_INTERNO);
                    actualizarEstadoSolicitudPostulacion(solicitudPostulacionModeloDTO.getIdSolicitud(), EstadoSolicitudPostulacionEnum.ASIGNADA_A_CONTROL_INTERNO);
                    tipoVerificacion = TipoSolicitudVerificacionFovisEnum.VERIFICACION;
                } else if (EstadoHogarEnum.HABIL.equals(solicitudPostulacionModeloDTO.getPostulacionFOVISModeloDTO().getEstadoHogar())
                        || EstadoHogarEnum.HABIL_SEGUNDO_ANIO.equals(solicitudPostulacionModeloDTO.getPostulacionFOVISModeloDTO().getEstadoHogar())) {
                    tipoVerificacion = TipoSolicitudVerificacionFovisEnum.RE_VERIFICACION;
                }

                if (cont < tope && cont != 0) {
                    cont++;
                    String radicadoHijo = (radicado + "_" + cont);
                    SolicitudVerificacionFovisModeloDTO solicitud = this.crearSolicitudVerificacionFovisInicial(userDTO);
                    //Se radica la solicitud de Verificación.
                    solicitud = this.consultarSolicitudVerificacionFovis(solicitud.getIdSolicitud());
                    solicitud.setIdInstanciaProceso(null);
                    solicitud.setIdPostulacionFOVIS(solicitudTemporal.getPostulacion().getIdPostulacion());
                    solicitud.setTipoVerificacion(tipoVerificacion);
                    solicitud.setDestinatario(usuarioAsignar.getNombreUsuario());
                    solicitud.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                    solicitud.setNumeroRadicacion(radicadoHijo);
                    solicitud.setFechaRadicacion(new Date().getTime());
                    solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);
                    // guardar temporal
                    SolicitudVerificacionFovisDTO solicitudVerificacion = new SolicitudVerificacionFovisDTO();
                    // Se elimina la información de la postulación para que se consulte la mas actualizada
                    solicitudTemporal.setPostulacion(null);
                    solicitudVerificacion.setDatosPostulacionFovis(solicitudTemporal);
                    solicitudVerificacion.setSolicitudVerificacionFovisModeloDTO(solicitud);
                    solicitudVerificacion.setIdSolicitud(solicitud.getIdSolicitud());
                    solicitudVerificacion.setIdInstanciaProceso(null);
                    guardarDatosTemporalVerificacion(solicitudVerificacion);

                } else {
                    radicado = "";
                    cont = 0;
                    //Se vuelve a crear un radicado padre y su proceso
                    SolicitudVerificacionFovisModeloDTO solicitudPadre = this.crearSolicitudVerificacionFovisInicial(userDTO);
                    //Se genera el radicado y se asocia a la solicitud
                    this.radicarSolicitud(solicitudPadre.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
                    solicitudPadre = this.consultarSolicitudVerificacionFovis(solicitudPadre.getIdSolicitud());
                    radicado = solicitudPadre.getNumeroRadicacion();
                    AsignacionTurnosDTO asignar = new AsignacionTurnosDTO();
                    asignar.setIdSolicitud(solicitudPadre.getIdSolicitud());
                    asignar.setUsuarioAsignar(usuarioAsignar);
                    asignar.setUsuarioRadica(userDTO);
                    asignar.setRadicado(radicado);
                    respuesta.add(asignar);

                    cont++;
                    String radicadoHijo = (radicado + "_" + cont);
                    SolicitudVerificacionFovisModeloDTO solicitud = this.crearSolicitudVerificacionFovisInicial(userDTO);
                    //Se radica la solicitud de Verificación.
                    solicitud = this.consultarSolicitudVerificacionFovis(solicitud.getIdSolicitud());
                    solicitud.setIdInstanciaProceso(null);
                    solicitud.setIdPostulacionFOVIS(solicitudTemporal.getPostulacion().getIdPostulacion());
                    solicitud.setTipoVerificacion(tipoVerificacion);
                    solicitud.setDestinatario(usuarioAsignar.getNombreUsuario());
                    solicitud.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                    solicitud.setNumeroRadicacion(radicadoHijo);
                    solicitud.setFechaRadicacion(new Date().getTime());
                    solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);
                    // guardar temporal
                    SolicitudVerificacionFovisDTO solicitudVerificacion = new SolicitudVerificacionFovisDTO();
                    // Se elimina la información de la postulación para que se consulte la mas actualizada
                    solicitudTemporal.setPostulacion(null);
                    solicitudVerificacion.setDatosPostulacionFovis(solicitudTemporal);
                    solicitudVerificacion.setSolicitudVerificacionFovisModeloDTO(solicitud);
                    solicitudVerificacion.setIdSolicitud(solicitud.getIdSolicitud());
                    solicitudVerificacion.setIdInstanciaProceso(null);
                    guardarDatosTemporalVerificacion(solicitudVerificacion);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see FovisCompositeService#registrarResultadoControlInterno(
     *VerificacionGestionControlInterno, UserDTO)
     */
    public void registrarResultadoControlInterno(VerificacionGestionControlInterno gestionControlInterno, UserDTO userDTO) {
        logger.debug("Se inicia el servicio de registrarResultadoControlInterno(VerificacionGestionControlInterno, UserDTO)");

        Map<String, Object> params = new HashMap<>();
        params.put("resultadoControlInterno", gestionControlInterno.getResultadoControlInterno());

        //Se consulta la solicitud hija
        SolicitudVerificacionFovisModeloDTO solicitud = this.consultarSolicitudVerificacionFovis(gestionControlInterno.getIdSolicitud());

        //Se obtiene el usuario destinatario
        UserDTO user = new UserDTO();
        user.setNombreUsuario(solicitud.getUsuarioRadicacion());
        user.setSedeCajaCompensacion(solicitud.getSedeDestinatario());
        user.setSedeCajaCompensacion(solicitud.getCiudadSedeRadicacion());

        //Se consulta los datos de la solicitud de veirificacion
        SolicitudVerificacionFovisDTO solicitudVerificacionTemporal = consultarVerificacionFovisTemporal(
                gestionControlInterno.getIdSolicitud());
        guardarDatosControlInterno(gestionControlInterno, solicitudVerificacionTemporal);

        //se crea la solicitud en el bpm
        Long idInstancia = this.iniciarProcesoVerificacionPostulacionFovis(gestionControlInterno.getIdSolicitud(), solicitud.getNumeroRadicacion(), TipoSolicitudVerificacionFovisEnum.VERIFICACION, user, userDTO);
        //Se setea la instancia proceso y se guarda
        solicitud.setIdInstanciaProceso(idInstancia.toString());
        solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);

        // actualiza la informacion
        solicitudVerificacionTemporal.setDatosPostulacionFovis(solicitudVerificacionTemporal.getDatosPostulacionFovis());
        solicitudVerificacionTemporal.setSolicitudVerificacionFovisModeloDTO(solicitud);
        solicitudVerificacionTemporal.setIdSolicitud(solicitud.getIdSolicitud());
        solicitudVerificacionTemporal.setIdInstanciaProceso(idInstancia);
        guardarDatosTemporalVerificacion(solicitudVerificacionTemporal);

        PostulacionFOVISModeloDTO postulacionFOVISModelo = new PostulacionFOVISModeloDTO();
        // caso1: con hallazgos, caso2: sin hallazgos, caso3: rechazar postulación, caso4: con hallazgos re-verificacion
        switch (gestionControlInterno.getResultadoControlInterno()) {
            case 1:
                devolverSolicitudHallallazgos(solicitudVerificacionTemporal);

                solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().setResultado(EstadoSolicitudVerificacionFovisEnum.CON_HALLAZGOS.getDescripcion());
                guardarDatosControlInterno(gestionControlInterno, solicitudVerificacionTemporal);
                // Se eliminan los datos de la postulación para que se consulten
                solicitudVerificacionTemporal.getDatosPostulacionFovis().setPostulacion(null);
                break;
            case 2:
                registrarPostulacionHabil(solicitudVerificacionTemporal, EstadoSolicitudVerificacionFovisEnum.SIN_HALLAZGOS);
                solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().setResultado(EstadoSolicitudVerificacionFovisEnum.SIN_HALLAZGOS.getDescripcion());
                guardarDatosControlInterno(gestionControlInterno, solicitudVerificacionTemporal);
                postulacionFOVISModelo = crearActualizarPostulacionFOVIS(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion());
                actualizarJsonPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                        solicitudVerificacionTemporal.getDatosPostulacionFovis());
                break;
            case 3:
                actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis().getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_INHABIL_POR_CRUCES);
                solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().setResultado(EstadoSolicitudVerificacionFovisEnum.CON_HALLAZGOS.getDescripcion());
                guardarDatosControlInterno(gestionControlInterno, solicitudVerificacionTemporal);
                rechazarPostulacionControlInterno(solicitudVerificacionTemporal, true);
                postulacionFOVISModelo = crearActualizarPostulacionFOVIS(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion());
                actualizarJsonPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                        solicitudVerificacionTemporal.getDatosPostulacionFovis());
                break;
            case 4:
                devolverSolicitudHallallazgos(solicitudVerificacionTemporal);
                solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().setResultado(EstadoSolicitudVerificacionFovisEnum.CON_HALLAZGOS.getDescripcion());
                guardarDatosControlInterno(gestionControlInterno, solicitudVerificacionTemporal);
                // Se eliminan los datos de la postulación para que se consulten
                solicitudVerificacionTemporal.getDatosPostulacionFovis().setPostulacion(null);
                break;
        }


        guardarDatosTemporalVerificacion(solicitudVerificacionTemporal);

        //Termina la tarea hija
        Long idTarea = consultarTareaActiva(new Long(solicitudVerificacionTemporal.getIdInstanciaProceso()));
        terminarTarea(idTarea, params);

        logger.debug("Finaliza el servicio de registrarResultadoControlInterno(VerificacionGestionControlInterno, UserDTO)");
    }

    /**
     * Método que registra el documento y las observaciones de control interno.
     *
     * @param gestionControlInterno
     * @param solicitudVerificacionTemporal
     */
    private void guardarDatosControlInterno(VerificacionGestionControlInterno gestionControlInterno,
                                            SolicitudVerificacionFovisDTO solicitudVerificacionTemporal) {

        solicitudVerificacionTemporal.setIdDocumentoControlInterno(gestionControlInterno.getIdDocumentoAdjunto());
        registrarDocumentoControlInterno(solicitudVerificacionTemporal.getIdSolicitud(), gestionControlInterno.getIdDocumentoAdjunto());

        // se guardan las observaciones
        SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO = consultarSolicitudVerificacionFovis(
                gestionControlInterno.getIdSolicitud());
        solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().setObservaciones(gestionControlInterno.getObservaciones());
        solicitudVerificacionFovisModeloDTO.setObservaciones(gestionControlInterno.getObservaciones());
        solicitudVerificacionFovisModeloDTO.setResultado(solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO().getResultado());
        crearActualizarSolicitudVerificacionFovis(solicitudVerificacionFovisModeloDTO);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#enviarCorreccionesAControlInterno(
     *com.asopagos.fovis.composite.dto.VerificacionCorreccionHallazgos, com.asopagos.rest.security.dto.UserDTO)
     */
    public void enviarCorreccionesAControlInterno(VerificacionCorreccionHallazgos correccionHallazgos, UserDTO userDTO) {
        logger.debug("Inicia servicio enviarCorreccionesAControlInterno()");

        SolicitudVerificacionFovisDTO solicitudVerificacionTemporal = consultarVerificacionFovisTemporal(
                correccionHallazgos.getIdSolicitud());
        SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO = consultarSolicitudVerificacionFovis(solicitudVerificacionTemporal.getIdSolicitud());

        // se guarda la información de la postulación
        guardarDatosInicialesPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis(), null, userDTO);

        actualizarEstadoSolicitudVerificacion(solicitudVerificacionTemporal.getIdSolicitud(),
                EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_GESTIONADOS);
        solicitudVerificacionTemporal.getSolicitudVerificacionFovisModeloDTO()
                .setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_GESTIONADOS);
        actualizarJsonPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                solicitudVerificacionTemporal.getDatosPostulacionFovis());

        // Se eliminan los datos de la postulación para que se consulten
        solicitudVerificacionTemporal.getDatosPostulacionFovis().setPostulacion(null);
        guardarDatosTemporalVerificacion(solicitudVerificacionTemporal);

        //Se actualizan el estado cruce hogar de la postulacion CC Fovis ID 156
        actualizarEstadoCrucesHogarSolicitudGestionCruce(solicitudVerificacionTemporal.getDatosPostulacionFovis().getIdSolicitud());

        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = correccionHallazgos.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudVerificacionTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, null);


        logger.debug("Finaliza servicio enviarCorreccionesAControlInterno()");
    }

    /**
     * Metodo que verifica el estado de cruces de hogar
     *
     * @param idSolicitud
     */
    public void actualizarEstadoCrucesHogarSolicitudGestionCruce(Long idSolicitudPostulacion) {
        logger.debug("Inicia actualizarEstadoCrucesHogarSolicitudGestionCruce(Long idSolicitudPostulacion)");
        //Se consulta la solicitud postulación por el id solicitud global de la postulacion
        SolicitudPostulacionModeloDTO solicitudPostulacion = consultarSolicitudPostulacion(idSolicitudPostulacion);

        //Se consultan los cruces por el id solicitud Postulacion
        List<CruceDTO> cruces = consultarCrucePorIdSolicitudPostulacion(solicitudPostulacion.getIdSolicitudPostulacion());
        Map<Long, SolicitudGestionCruceDTO> solicitudesCrucesMap = new HashMap<>();
        Map<Long, SolicitudGestionCruceDTO> solicitudesRatificadas = new HashMap<>();
        Map<Long, SolicitudGestionCruceDTO> solicitudesSubsanadas = new HashMap<>();
        for (CruceDTO cruce : cruces) {
            SolicitudGestionCruceDTO solicitudGestionCruce = null;
            if (!solicitudesCrucesMap.containsKey(cruce.getIdCruce())) {
                //Se consulta la Solicitud Gestion Cruce por Id
                solicitudGestionCruce = consultarSolicitudGestionCrucePorId(cruce.getIdSolicitudGestionCruce());
                solicitudesCrucesMap.put(cruce.getIdCruce(), solicitudGestionCruce);
            } else {
                solicitudGestionCruce = solicitudesCrucesMap.get(cruce.getIdCruce());
                //si ya esta ratificado continua con el siguiente cruce.
                if (solicitudesRatificadas.containsKey(solicitudGestionCruce.getIdSolicitudGestionCruce())) {
                    continue;
                }
            }
            if (cruce.getEstadoCruce().equals(EstadoCruceEnum.CRUCE_RATIFICADO)) {
                solicitudGestionCruce.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION);
                this.actualizarSolicitudGestionCruce(solicitudGestionCruce);
                solicitudesRatificadas.put(solicitudGestionCruce.getIdSolicitudGestionCruce(), solicitudGestionCruce);
                if (solicitudesSubsanadas.containsKey(solicitudGestionCruce.getIdSolicitudGestionCruce())) {
                    solicitudesSubsanadas.remove(solicitudGestionCruce.getIdSolicitudGestionCruce());
                }
            } else if (cruce.getEstadoCruce().equals(EstadoCruceEnum.CRUCE_SUBSANADO)) {
                solicitudesSubsanadas.put(solicitudGestionCruce.getIdSolicitudGestionCruce(), solicitudGestionCruce);
            }
        }
        if (!solicitudesSubsanadas.isEmpty()) {
            //Actualiza las solicitudes subsanadas
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : solicitudesSubsanadas.values()) {
                solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCE_SUBSANADO_PENDIENTE_VERIFICACION);
                this.actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
            }
        }
        logger.debug("Finaliza actualizarEstadoCrucesHogarSolicitudGestionCruce(Long idSolicitudPostulacion)");
    }

    public List<CruceDTO> consultarCrucePorIdSolicitudPostulacion(Long idSolicitudPostulacion) {
        logger.debug("Inicia servicio consultarCrucePorIdSolicitudGestionCruce(Long idSolicitudGestionCruce)");
        ConsultarCrucePorSolicitudPostulacion consultaCruces = new ConsultarCrucePorSolicitudPostulacion(idSolicitudPostulacion);
        consultaCruces.execute();
        List<CruceDTO> cruces = consultaCruces.getResult();
        logger.debug("Finaliza servicio consultarCrucePorIdSolicitudGestionCruce(Long idSolicitudGestionCruce)");
        return cruces;
    }

    public SolicitudGestionCruceDTO consultarSolicitudGestionCrucePorId(Long idSolicitudGestionCruce) {
        logger.debug("Inicia servicio consultarSolicitudGestionCrucePorId(Long idSolicitudPostulacion)");
        ConsultarSolicitudGestionCruce solicitudesGestionCruce = new ConsultarSolicitudGestionCruce(idSolicitudGestionCruce);
        solicitudesGestionCruce.execute();
        SolicitudGestionCruceDTO solicitudGestionCruceResult = solicitudesGestionCruce.getResult();
        logger.debug("Finaliza servicio consultarSolicitudGestionCrucePorId(Long idSolicitudPostulacion)");
        return solicitudGestionCruceResult;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#verificarCorreccionesControlInterno(
     *com.asopagos.fovis.composite.dto.VerificacionGestionControlInterno, com.asopagos.rest.security.dto.UserDTO)
     */
    public void verificarCorreccionesControlInterno(VerificacionGestionControlInterno correccionHallazgos, UserDTO userDTO) {
        logger.debug("Inicia servicio verificarCorreccionesControlInterno(VerificacionCorreccionHallazgos, UserDTO)");

        Map<String, Object> params = new HashMap<>();
        params.put("resultadoCorreccionCruces", correccionHallazgos.getResultadoCorreccionCruces());

        SolicitudVerificacionFovisDTO solicitudVerificacionTemporal = consultarVerificacionFovisTemporal(
                correccionHallazgos.getIdSolicitud());

        guardarDatosControlInterno(correccionHallazgos, solicitudVerificacionTemporal);


        // se guarda la información de la postulación
        guardarDatosInicialesPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis(), null, userDTO);


        // caso1: postulación cerrada, caso2: rechazada
        switch (correccionHallazgos.getResultadoCorreccionCruces()) {
            case 1:
                registrarPostulacionHabil(solicitudVerificacionTemporal, EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_SUBSANADOS);
                break;
            case 2:
                actualizarEstadoSolicitudVerificacion(correccionHallazgos.getIdSolicitud(),
                        EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_NO_SUBSANADOS);
                rechazarPostulacionControlInterno(solicitudVerificacionTemporal, false);
                break;
        }

        actualizarJsonPostulacion(solicitudVerificacionTemporal.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                solicitudVerificacionTemporal.getDatosPostulacionFovis());
        guardarDatosTemporalVerificacion(solicitudVerificacionTemporal);
        guardarPostulacionTemporal(solicitudVerificacionTemporal.getDatosPostulacionFovis(), userDTO);

        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = correccionHallazgos.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudVerificacionTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);

        logger.debug("Finaliza servicio verificarCorreccionesControlInterno(VerificacionCorreccionHallazgos, UserDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisService#registrarDatosCicloAsignacion(com.asopagos.dto.modelo.CicloAsignacionModeloDTO)
     */
    public void registrarDatosCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacionModelDTO) {
        logger.debug("Se inicia el servicio de registrarDatosCicloAsignacion(CicloAsignacionModeloDTO)");
        try {
            //se registra el ciclo de asignación
            RegistrarCicloAsignacion registrarCicloAsignacion = new RegistrarCicloAsignacion(cicloAsignacionModelDTO);
            registrarCicloAsignacion.execute();
            cicloAsignacionModelDTO = registrarCicloAsignacion.getResult();
            //se registran las modalidades para el ciclo de asignación
            RegistrarModalidadesCicloAsignacion registrarModalidadesCicloAsignacion = new RegistrarModalidadesCicloAsignacion(
                    cicloAsignacionModelDTO);
            registrarModalidadesCicloAsignacion.execute();

            //Actualizar las postulaciones calificadas sin asignar a un nuevo ciclo
            ActualizarPostulacionesCalificadasSinCambioCiclo actualizarPostulacionesCalificadasSinCambioCiclo = new ActualizarPostulacionesCalificadasSinCambioCiclo();
            actualizarPostulacionesCalificadasSinCambioCiclo.execute();

            //Se comento bajo el glpi 89765
            // Actualizar las postulaciones predecesoras asociadas a novedades
//            ActualizarPostulacionesNovedadesAsociadasCicloPredecesor actualizarPostulacionesNovedadesAsociadasCicloPredecesor = new ActualizarPostulacionesNovedadesAsociadasCicloPredecesor();
//            actualizarPostulacionesNovedadesAsociadasCicloPredecesor.execute();
            logger.debug("Finaliza el servicio de registrarDatosCicloAsignacion(CicloAsignacionModeloDTO)");
        } catch (FunctionalConstraintException e) {
            logger.error("Ocurrió un error en registrarDatosCicloAsignacion(CicloAsignacionModeloDTO)", e);
            throw new FunctionalConstraintException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#registrarParametrizacionNovedades(com.asopagos.fovis.composite.dto.
     * VariablesGestionFOVISDTO)
     */
    @Override
    public void registrarParametrizacionNovedades(VariablesGestionFOVISDTO variables) {
        logger.debug("Inicia servicio registrarParametrizacionNovedades(VariablesGestionFOVISDTO)");
        try {
            List<ParametrizacionEjecucionProgramadaModeloDTO> paramNovedad = new ArrayList<ParametrizacionEjecucionProgramadaModeloDTO>();
            if (variables.getProgramacionLevantarInhabilidadSancion() != null
                    && variables.getProgramacionLevantarInhabilidadSancion().getFrecuenciaEjecucionProceso() != null) {
                variables.getProgramacionLevantarInhabilidadSancion().setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                paramNovedad.add(variables.getProgramacionLevantarInhabilidadSancion());
            }
            if (variables.getProgramacionNovedadRechazo() != null && variables.getProgramacionNovedadRechazo().getFechaInicio() != null) {
                Calendar fechaInicio = Calendar.getInstance();
                fechaInicio.setTime(variables.getProgramacionNovedadRechazo().getFechaInicio());
                Integer mes = fechaInicio.get(Calendar.MONTH) + 1;
                Integer diaMes = fechaInicio.get(Calendar.DATE);
                variables.getProgramacionNovedadRechazo().setMes(String.valueOf(mes));
                variables.getProgramacionNovedadRechazo().setDiaMes(String.valueOf(diaMes));
                variables.getProgramacionNovedadRechazo().setFechaInicio(null);
                variables.getProgramacionNovedadRechazo().setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.ANUAL);
                variables.getProgramacionNovedadRechazo().setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                paramNovedad.add(variables.getProgramacionNovedadRechazo());
            }
            if (variables.getProgramacionNovedadSuspension() != null
                    && variables.getProgramacionNovedadSuspension().getFechaInicio() != null) {
                Calendar fechaInicio = Calendar.getInstance();
                fechaInicio.setTime(variables.getProgramacionNovedadSuspension().getFechaInicio());
                Integer mes = fechaInicio.get(Calendar.MONTH) + 1;
                Integer diaMes = fechaInicio.get(Calendar.DATE);
                variables.getProgramacionNovedadSuspension().setMes(String.valueOf(mes));
                variables.getProgramacionNovedadSuspension().setDiaMes(String.valueOf(diaMes));
                variables.getProgramacionNovedadSuspension().setFechaInicio(null);
                variables.getProgramacionNovedadSuspension().setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.ANUAL);
                variables.getProgramacionNovedadSuspension().setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                paramNovedad.add(variables.getProgramacionNovedadSuspension());
            }

            //Se realiza la programacion para la novedad automatica de vencimiento de subsidios asignados
            if (variables.getParametrizacionesFOVIS() != null && !variables.getParametrizacionesFOVIS().isEmpty()) {
                for (ParametrizacionFOVISModeloDTO parametrizacion : variables.getParametrizacionesFOVIS()) {
                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA.equals(parametrizacion.getParametro())) {
                        ParametrizacionEjecucionProgramadaModeloDTO paramVencimientoSinProrroga = new ParametrizacionEjecucionProgramadaModeloDTO();
                        paramVencimientoSinProrroga.setProceso(ProcesoAutomaticoEnum.VENCIMIENTO_SUBSIDIO_FOVIS_ASIGNADO_SIN_PRORROGA);
                        Calendar cal = getCalendarByString(parametrizacion.getValorString());
                        Integer hora = cal.get(Calendar.HOUR_OF_DAY);
                        Integer minutos = cal.get(Calendar.MINUTE);
                        paramVencimientoSinProrroga.setHoras(hora.toString());
                        paramVencimientoSinProrroga.setMinutos(minutos.toString());
                        paramVencimientoSinProrroga.setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.DIARIO);
                        paramVencimientoSinProrroga.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                        paramNovedad.add(paramVencimientoSinProrroga);
                    }

                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_PRIMERA_PRORROGA.equals(parametrizacion.getParametro())) {
                        ParametrizacionEjecucionProgramadaModeloDTO paramVencimientoPrimeraProrroga = new ParametrizacionEjecucionProgramadaModeloDTO();
                        paramVencimientoPrimeraProrroga.setProceso(ProcesoAutomaticoEnum.VENCIMIENTO_SUBSIDIO_FOVIS_ASIGNADO_PRIMERA_PRORROGA);
                        Calendar cal = getCalendarByString(parametrizacion.getValorString());
                        Integer hora = cal.get(Calendar.HOUR_OF_DAY);
                        Integer minutos = cal.get(Calendar.MINUTE);
                        paramVencimientoPrimeraProrroga.setHoras(hora.toString());
                        paramVencimientoPrimeraProrroga.setMinutos(minutos.toString());
                        paramVencimientoPrimeraProrroga.setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.DIARIO);
                        paramVencimientoPrimeraProrroga.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                        paramNovedad.add(paramVencimientoPrimeraProrroga);
                    }

                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SEGUNDA_PRORROGA.equals(parametrizacion.getParametro())) {
                        ParametrizacionEjecucionProgramadaModeloDTO paramVencimientoSegundaProrroga = new ParametrizacionEjecucionProgramadaModeloDTO();
                        paramVencimientoSegundaProrroga.setProceso(ProcesoAutomaticoEnum.VENCIMIENTO_SUBSIDIO_FOVIS_ASIGNADO_SEGUNDA_PRORROGA);
                        Calendar cal = getCalendarByString(parametrizacion.getValorString());
                        Integer hora = cal.get(Calendar.HOUR_OF_DAY);
                        Integer minutos = cal.get(Calendar.MINUTE);
                        paramVencimientoSegundaProrroga.setHoras(hora.toString());
                        paramVencimientoSegundaProrroga.setMinutos(minutos.toString());
                        paramVencimientoSegundaProrroga.setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum.DIARIO);
                        paramVencimientoSegundaProrroga.setEstadoEjecucionProceso(EstadoActivoInactivoEnum.ACTIVO);
                        paramNovedad.add(paramVencimientoSegundaProrroga);
                    }
                }
            }

            RegistrarActualizarProgramacionAutomatico registrarProgramacionNovedades = new RegistrarActualizarProgramacionAutomatico(paramNovedad);
            registrarProgramacionNovedades.execute();

            // Actualizar los parametros de plazos de vencimiento
            ActualizarDatosGeneralesFovis actualizarDatosGeneralesFovis = new ActualizarDatosGeneralesFovis(
                    variables.getParametrizacionesFOVIS());
            actualizarDatosGeneralesFovis.execute();

            // Actualizar las postulaciones calificadas sin asignar a un nuevo ciclo
//            ActualizarPostulacionesCalificadasSinCambioCiclo actualizarPostulacionesCalificadasSinCambioCiclo =  new ActualizarPostulacionesCalificadasSinCambioCiclo();
//            actualizarPostulacionesCalificadasSinCambioCiclo.execute();

            logger.debug("Finaliza servicio registrarParametrizacionNovedades(VariablesGestionFOVISDTO)");
        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha de parametrización", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Se obtiene instancia de Calendar con la hora escrita en pantalla
     *
     * @param hour Hora en formato hh:mm aa
     * @return Instancia calendar para el procesamiento de los datos
     * @throws ParseException
     */
    private Calendar getCalendarByString(String hour) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        Date date = df.parse(hour);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#verificarSolicitudPostulacion(
     *com.asopagos.fovis.composite.dto.VerificacionGestionPNCPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarSolicitudPostulacion(VerificacionGestionPNCPostulacionDTO datosVerificacion, UserDTO userDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoVerificacionBack", datosVerificacion.getResultadoVerificacionBack());
        params.put("provieneFront", Boolean.FALSE);
        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(datosVerificacion.getIdSolicitud());

        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(datosVerificacion.getIdSolicitud());

        ConsultarPostulacionFOVIS consultarPostulacion = new ConsultarPostulacionFOVIS(solicitudPostulacionDTO.getIdPostulacionFOVIS());
        consultarPostulacion.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModelo = consultarPostulacion.getResult();

        solicitudTemporal.getPostulacion().setEstadoHogar(postulacionFOVISModelo.getEstadoHogar());
        solicitudTemporal.getPostulacion().setIdPostulacion(postulacionFOVISModelo.getIdPostulacion());

        // Guarda los datos de la postulación
        guardarDatosInicialesPostulacion(solicitudTemporal, null, userDTO);
        // Caso1: PNC Subsanable, Caso2: Escalada, Caso3: Remitir para cruces, Caso4: Rechazada
        switch (datosVerificacion.getResultadoVerificacionBack()) {
            case 1:
                /* Si es proceso web se envía el tiempo para corregir la solicitud por parte del usuario web. */
                if (ProcesoEnum.POSTULACION_FOVIS_WEB.equals(solicitudTemporal.getTipoTransaccionEnum().getProceso())) {
                    String tiempoCorreccion = (String) CacheManager
                            .getParametro(ParametrosSistemaConstants.PARAM_TIEMPO_CORRECCION_FOVIS_WEB);
                    params.put(TIEMPO_CORRECCION, tiempoCorreccion);
                }
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.NO_CONFORME_SUBSANABLE);

                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.NO_CONFORME_SUBSANABLE);
                actualizarJsonPostulacion(postulacionFOVISModelo.getIdPostulacion(), solicitudTemporal);
                // Se eliminan los datos de la postulación del temporal para que se consulten
                solicitudTemporal.setPostulacion(null);
                break;
            case 2:
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA_POR_BACK);
                Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = registrarEscalamientoAnalistas(params,
                        datosVerificacion.getEscalamientoMiembroHogar(), datosVerificacion.getEscalamientoTecnico(),
                        datosVerificacion.getEscalamientoJuridico(), datosVerificacion.getIdSolicitud(),
                        OrigenEscalamientoEnum.BACK, userDTO);
                solicitudTemporal.setEscalamientoJuridicoBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
                solicitudTemporal.setEscalamientoMiembrosHogarBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
                solicitudTemporal
                        .setEscalamientoTecnicoConstruccionBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));
                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA_POR_BACK);
                actualizarJsonPostulacion(solicitudPostulacionDTO.getIdPostulacionFOVIS(), solicitudTemporal);
                // Se eliminan los datos de la postulación del temporal para que se consulten
                solicitudTemporal.setPostulacion(null);
                break;
            case 3:
                // Para los integrantes cambiar estado con respecto al hogar a Activo
                for (IntegranteHogarModeloDTO integranteHogarModeloDTO : solicitudTemporal.getIntegrantesHogar()) {
                    integranteHogarModeloDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
                }
                cambiarEstadoHogar(postulacionFOVISModelo.getIdPostulacion(), EstadoHogarEnum.POSTULADO);
                solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.POSTULADO);

                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);

                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_CONTROL_INTERNO);
                actualizarJsonPostulacion(postulacionFOVISModelo.getIdPostulacion(), solicitudTemporal);
                break;
            case 4:
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
                actualizarEstadoSolicitudPostulacion(datosVerificacion.getIdSolicitud(),
                        EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

                cambiarEstadoHogar(postulacionFOVISModelo.getIdPostulacion(), EstadoHogarEnum.RECHAZADO);
                solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
                solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.RECHAZADO);
                actualizarJsonPostulacion(postulacionFOVISModelo.getIdPostulacion(), solicitudTemporal);
                break;
        }
        guardarDatosTemporal(solicitudTemporal);
        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = datosVerificacion.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#gestionarPNCPostulacion(
     *com.asopagos.fovis.composite.dto.GestionPNCPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void gestionarPNCPostulacion(GestionPNCPostulacionDTO entrada, UserDTO userDTO) {
        logger.debug("Inicia servicio gestionarPNCPostulacion(GestionPNCPostulacionDTO entrada, UserDTO userDTO)");
        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(entrada.getIdSolicitud());
        actualizarEstadoSolicitudPostulacion(entrada.getIdSolicitud(), EstadoSolicitudPostulacionEnum.NO_CONFORME_EN_GESTION);
        actualizarEstadoSolicitudPostulacion(entrada.getIdSolicitud(), EstadoSolicitudPostulacionEnum.NO_CONFORME_GESTIONADA);
        solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.NO_CONFORME_GESTIONADA);
        // Se elimina la información de postulación para que sea consultada nuevamente
        solicitudTemporal.setPostulacion(null);
        guardarDatosTemporal(solicitudTemporal);
        //Si ya existe una instancia de Proceso asociada se termina la tarea.
        Long idTarea = entrada.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudTemporal.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, null);
        logger.debug("Finaliza servicio gestionarPNCPostulacion(GestionPNCPostulacionDTO entrada, UserDTO userDTO)");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#gestionarPNCPostulacionWeb(
     *com.asopagos.fovis.composite.dto.GestionPNCPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void gestionarPNCPostulacionWeb(GestionPNCPostulacionDTO entrada, UserDTO userDTO) {
        logger.debug("Inicia servicio gestionarPNCPostulacionWeb(GestionPNCPostulacionDTO entrada, UserDTO userDTO)");
        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(entrada.getIdSolicitud());
        actualizarEstadoSolicitudPostulacion(entrada.getIdSolicitud(), EstadoSolicitudPostulacionEnum.NO_CONFORME_EN_GESTION);
        actualizarEstadoSolicitudPostulacion(entrada.getIdSolicitud(), EstadoSolicitudPostulacionEnum.NO_CONFORME_GESTIONADA);
        solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.NO_CONFORME_GESTIONADA);
        guardarDatosTemporal(solicitudTemporal);

        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(entrada.getIdSolicitud());
        solicitudPostulacionDTO.setObservacionesWeb(solicitudTemporal.getObservacionesWeb());
        solicitudPostulacionDTO = crearActualizarSolicitudPostulacion(solicitudPostulacionDTO);

        EnviarSenal enviarSenal = new EnviarSenal(solicitudPostulacionDTO.getTipoTransaccion().getProceso(), INFORMACION_CORREGIDA,
                Long.valueOf(solicitudPostulacionDTO.getIdInstanciaProceso()), null);
        enviarSenal.execute();
        logger.debug("Finaliza servicio gestionarPNCPostulacionWeb(GestionPNCPostulacionDTO entrada, UserDTO userDTO)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService
     * #cancelarSolicitudPostulacionTimeout(com.asopagos.fovis.composite.dto.CancelacionSolicitudPostulacionDTO)
     */
    @Override
    public void cancelarSolicitudPostulacionTimeout(CancelacionSolicitudPostulacionDTO cancelacion) {

        try {
            ActualizarEstadoSolicitudPostulacion estadoSolicitud = new ActualizarEstadoSolicitudPostulacion(cancelacion.getIdSolicitud(),
                    EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
            estadoSolicitud.execute();
        } catch (AsopagosException e) {
            logger.error(Interpolator.interpolate("No se pudo actualizar el estado de la solicitud {} de postulación a {}",
                    cancelacion.getIdSolicitud(), EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA), e);
            throw e;
        }

        try {
            ActualizarEstadoSolicitudPostulacion estadoSolicitudCerrada = new ActualizarEstadoSolicitudPostulacion(
                    cancelacion.getIdSolicitud(), EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
            estadoSolicitudCerrada.execute();
        } catch (AsopagosException e) {
            logger.error(Interpolator.interpolate("No se pudo actualizar el estado de la solicitud {} de postulación a {}",
                    cancelacion.getIdSolicitud(), EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA), e);
            throw e;
        }
    }

    /***************************************************************************************************/
    /**********************************************
     * Métodos privados
     **********************************************/
    /***************************************************************************************************/

    /**
     * Método que invoca el servicio de consulta de rangos de valores para cálculo de SFV, de acuerdo a una modalidad enviada como parámetro
     *
     * @param modalidad Modalidad de vivienda
     * @return La lista de rangos parametrizados
     */
    private List<RangoTopeValorSFVModeloDTO> consultarRangosSVFPorModalidad(ModalidadEnum modalidad) {
        logger.debug("Inicia el método consultarRangosSVFPorModalidad");
        ConsultarRangosSVFPorModalidad service = new ConsultarRangosSVFPorModalidad(modalidad);
        service.execute();
        logger.debug("Finaliza el método consultarRangosSVFPorModalidad");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de información de un afiliado, por identificación
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @return Objeto <code>ConsultarAfiliadoOutDTO</code> con la información del afiliado
     */
    private ConsultarAfiliadoOutDTO consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia el método consultaAfiliado");
        ConsultarAfiliado service = new ConsultarAfiliado(numeroIdentificacion, tipoIdentificacion, null);
        service.execute();
        logger.debug("Finaliza el método consultaAfiliado");
        return service.getResult();
    }

    /**
     * Método que obtiene un parámetro almacenado en caché, y lo retorna como <code>Double</code>
     *
     * @param nombreParametro Nombre del parámetro
     * @return Objeto <code>Double</code> con el valor del parámetro
     */
    private Double obtenerParametroDouble(String nombreParametro) {
        if (CacheManager.getParametro(nombreParametro) != null) {
            return new Double(CacheManager.getParametro(nombreParametro).toString());
        }

        return null;
    }

    /**
     * Método que transforma un <code>SolicitudPostulacionFOVISDTO</code> en <code>SolicitudModeloDTO</code>
     *
     * @param solicitudPostulacionDTO DTO a transformar
     * @param userDTO                 Información del usuario, tomado del contexto del servicio
     * @return Obejto <code>SolicitudModeloDTO</code> equivalente
     */
    private SolicitudModeloDTO transformarSolicitudPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, UserDTO userDTO) {
        logger.debug("Inicia el método transformarSolicitudPostulacion");
        SolicitudModeloDTO solicitudDTO = new SolicitudModeloDTO();
        solicitudDTO.setIdInstanciaProceso(solicitudPostulacionDTO.getIdInstanciaProceso() != null
                ? solicitudPostulacionDTO.getIdInstanciaProceso().toString() : null);
        solicitudDTO.setCanalRecepcion(solicitudPostulacionDTO.getCanalRecepcion());
        solicitudDTO.setEstadoDocumentacion(null);
        solicitudDTO.setMetodoEnvio(solicitudPostulacionDTO.getMetodoEnvio());
        solicitudDTO.setIdCajaCorrespondencia(
                userDTO.getSedeCajaCompensacion() != null ? consultarIdSedeCajaCompensacion(userDTO.getSedeCajaCompensacion()) : null);
        solicitudDTO.setTipoTransaccion(solicitudPostulacionDTO.getTipoTransaccionEnum());
        solicitudDTO.setClasificacion(ClasificacionEnum.HOGAR);

        // Se radica la solicitud y se consulta la información de la radicación
        radicarSolicitud(solicitudPostulacionDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
        SolicitudPostulacionModeloDTO solicitud = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());

        solicitudDTO.setNumeroRadicacion(solicitud.getNumeroRadicacion());
        solicitudDTO.setTipoRadicacion(solicitud.getTipoRadicacion());
        solicitudDTO.setFechaRadicacion(solicitud.getFechaRadicacion());
        solicitudDTO.setUsuarioRadicacion(solicitud.getUsuarioRadicacion());
        solicitudDTO.setCiudadSedeRadicacion(solicitud.getCiudadSedeRadicacion());
        solicitudDTO.setDestinatario(solicitud.getDestinatario());
        solicitudDTO.setSedeDestinatario(solicitud.getSedeDestinatario());
        solicitudDTO.setFechaCreacion(new Date().getTime());
        solicitudDTO.setObservacion(null);
        solicitudDTO.setIdSolicitud(solicitudPostulacionDTO.getIdSolicitud());

        // Mantis 0247015
        // Almacenar la información de la postulación
        almacenarInformacionPostulacion(solicitud, solicitudPostulacionDTO);
        logger.debug("Finaliza el método transformarSolicitudPostulacion");
        return solicitudDTO;
    }

    /**
     * Crea la información basica de la postulación para el rregitro del intento de postulación
     *
     * @param solicitud               Informacion solicitud registrada
     * @param solicitudPostulacionDTO Informacion solicitud  en pantalla
     */
    private void almacenarInformacionPostulacion(SolicitudPostulacionModeloDTO solicitud,
                                                 SolicitudPostulacionFOVISDTO solicitudPostulacionDTO) {

        /* Se almacena el Jefe de Hogar */
        JefeHogarModeloDTO jefeHogarModeloDTO = solicitudPostulacionDTO.getPostulacion().getJefeHogar();
        JefeHogarModeloDTO jefeHogarReturn = consultarJefeHogar(jefeHogarModeloDTO.getTipoIdentificacion(),
                jefeHogarModeloDTO.getNumeroIdentificacion());

        /* Se consultan los datos del afiliado asociado al jefe de hogar. */
        AfiliadoModeloDTO afiliadoJefeHogar = buscarAfiliado(jefeHogarModeloDTO.getTipoIdentificacion(),
                jefeHogarModeloDTO.getNumeroIdentificacion());
        /* Si no existe Jefe de Hogar se asocia el afiliado */
        if (jefeHogarReturn == null) {
            jefeHogarModeloDTO.setIdAfiliado(afiliadoJefeHogar.getIdAfiliado());
            jefeHogarModeloDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
        } else {
            jefeHogarModeloDTO.setIdJefeHogar(jefeHogarReturn.getIdJefeHogar());
            jefeHogarModeloDTO.setIdAfiliado(jefeHogarReturn.getIdAfiliado());

            if (jefeHogarModeloDTO.getIdPersona() == null) {
                jefeHogarModeloDTO.setIdPersona(jefeHogarReturn.getIdPersona());
            }
            if (jefeHogarModeloDTO.getEstadoHogar() == null) {
                jefeHogarModeloDTO.setEstadoHogar(jefeHogarReturn.getEstadoHogar());
            }
        }
        jefeHogarModeloDTO = crearActualizarJefeHogar(jefeHogarModeloDTO);
        solicitudPostulacionDTO.getPostulacion().setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());

        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = solicitudPostulacionDTO.getPostulacion();
        postulacionFOVISModeloDTO.setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());
        postulacionFOVISModeloDTO.setEstadoHogar(EstadoHogarEnum.RECHAZADO);
        /* Se almacenan los datos de la postulación. */
        postulacionFOVISModeloDTO = crearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);

        solicitud.setIdPostulacionFOVIS(postulacionFOVISModeloDTO.getIdPostulacion());
        crearActualizarSolicitudPostulacion(solicitud);
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
     * Método que invoca el servicio de consulta de la lista de integrantes de un hogar, por jefe de hogar
     *
     * @param tipoIdentificacion   Tipo de identificación del jefe de hogar
     * @param numeroIdentificacion Número de identificación del jefe de hogar
     * @param idPostulacion        Identificador postulacion
     * @return La lista de integrantes del hogar
     */
    private List<IntegranteHogarModeloDTO> consultarListaIntegranteHogar(TipoIdentificacionEnum tipoIdentificacion,
                                                                         String numeroIdentificacion, Long idPostulacion) {
        ConsultarListaIntegranteHogar service = new ConsultarListaIntegranteHogar(idPostulacion, numeroIdentificacion, tipoIdentificacion);
        service.execute();
        return service.getResult();
    }

    /**
     * 321-020 Almacena los datos iniciales de la postulación.
     *
     * @param solicitudPostulacionDTO
     * @param estadoSolicitud
     * @return
     */
    private SolicitudPostulacionFOVISDTO guardarDatosInicialesPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionDTO,
                                                                          TipoPostulacionFOVISEnum tipoPostulacion, UserDTO userDTO) {

        // Se agrga el valor del tipo de postulación para manejos posteriores
        if (solicitudPostulacionDTO.getTipoPostulacion() == null) {
            solicitudPostulacionDTO.setTipoPostulacion(tipoPostulacion);
        }
        /* Se almacena el Jefe de Hogar */
        JefeHogarModeloDTO jefeHogarModeloDTO = solicitudPostulacionDTO.getPostulacion().getJefeHogar();
        JefeHogarModeloDTO jefeHogarReturn = consultarJefeHogar(jefeHogarModeloDTO.getTipoIdentificacion(),
                jefeHogarModeloDTO.getNumeroIdentificacion());

        /* Se consultan los datos del afiliado asociado al jefe de hogar. */
        AfiliadoModeloDTO afiliadoJefeHogar = buscarAfiliado(jefeHogarModeloDTO.getTipoIdentificacion(),
                jefeHogarModeloDTO.getNumeroIdentificacion());
        /* Si no existe Jefe de Hogar se asocia el afiliado */
        if (jefeHogarReturn == null) {
            jefeHogarModeloDTO.setIdAfiliado(afiliadoJefeHogar.getIdAfiliado());
            jefeHogarModeloDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
        } else {
            jefeHogarModeloDTO.setIdJefeHogar(jefeHogarReturn.getIdJefeHogar());
            jefeHogarModeloDTO.setIdAfiliado(jefeHogarReturn.getIdAfiliado());

            if (jefeHogarModeloDTO.getIdPersona() == null) {
                jefeHogarModeloDTO.setIdPersona(jefeHogarReturn.getIdPersona());
            }
            if (jefeHogarModeloDTO.getEstadoHogar() == null) {
                jefeHogarModeloDTO.setEstadoHogar(jefeHogarReturn.getEstadoHogar());
            }
        }
        jefeHogarModeloDTO = crearActualizarJefeHogar(jefeHogarModeloDTO);
        solicitudPostulacionDTO.getPostulacion().setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());


        // Almacena en Oferente 
        OferenteModeloDTO oferente = null;
        if (solicitudPostulacionDTO.getOferente() != null && solicitudPostulacionDTO.getOferente().getOferente() != null) {
            oferente = crearActualizarOferente(solicitudPostulacionDTO.getOferente().getOferente());
        }

        // Almacena en proveeor GLPI 49270
        List<LegalizacionDesembolosoProveedorModeloDTO> LyDproveedor = null;
        if (solicitudPostulacionDTO.getLegalizacionProveedor() != null) {
            for (LegalizacionDesembolosoProveedorModeloDTO proveedor : solicitudPostulacionDTO.getLegalizacionProveedor()) {
                LegalizacionDesembolosoProveedorModeloDTO proveedorLyD = crearActualizarLegalizacionDesembolsoProveedor(proveedor);
                LyDproveedor.add(proveedorLyD);
            }
        }


        // Almacena en ProyectoSolucionVivienda 
        ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO = solicitudPostulacionDTO.getPostulacion()
                .getProyectoSolucionVivienda();
        if (proyectoSolucionViviendaDTO != null && proyectoSolucionViviendaDTO.getNombreProyecto() != null) {
            proyectoSolucionViviendaDTO.setOferente(oferente);
            proyectoSolucionViviendaDTO = crearActualizarProyectoSolucionVivienda(
                    solicitudPostulacionDTO.getPostulacion().getProyectoSolucionVivienda());
            solicitudPostulacionDTO.getPostulacion().setProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        }

        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = solicitudPostulacionDTO.getPostulacion();
        postulacionFOVISModeloDTO.setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());
        postulacionFOVISModeloDTO.setProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        postulacionFOVISModeloDTO.setOferente(oferente);
        postulacionFOVISModeloDTO.setLegalizacionProveedor(LyDproveedor);
        //Se agrega el guardado de la parametrización de modalidad
        if (postulacionFOVISModeloDTO.getIdModalidad() != null) {
            ParametrizacionModalidadModeloDTO parametrizacionModalidad = consultarParametrizacionModalidad(postulacionFOVISModeloDTO.getIdModalidad());
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            try {
                jsonPayload = mapper.writeValueAsString(parametrizacionModalidad);
            } catch (JsonProcessingException e) {
                jsonPayload = postulacionFOVISModeloDTO.getInformacionParametrizacion();
            }
            postulacionFOVISModeloDTO.setInformacionParametrizacion(jsonPayload);
        }
        /* Se almacenan los datos de la vivienda asociada al proyecto
         * Si la ubicación de la vivienda es la misma del proyecto se asocia el mismo Id.
         */
        if (proyectoSolucionViviendaDTO != null
                && solicitudPostulacionDTO.getPostulacion().getUbicacionViviendaMismaProyecto() != null
                && solicitudPostulacionDTO.getPostulacion().getUbicacionViviendaMismaProyecto()) {
            postulacionFOVISModeloDTO.setUbicacionViviendaMismaProyecto(Boolean.TRUE);
            postulacionFOVISModeloDTO.setUbicacionVivienda(proyectoSolucionViviendaDTO.getUbicacionProyecto());
            solicitudPostulacionDTO.getPostulacion().setUbicacionVivienda(proyectoSolucionViviendaDTO.getUbicacionProyecto());
        } else if (solicitudPostulacionDTO.getPostulacion().getUbicacionVivienda() != null) {
            /* Se crea la Ubicación de la Vivienda */
            CrearActualizarUbicacion ubicacion = new CrearActualizarUbicacion(solicitudPostulacionDTO.getPostulacion().getUbicacionVivienda());
            ubicacion.execute();
            UbicacionModeloDTO ubicacionResult = ubicacion.getResult();
            postulacionFOVISModeloDTO.setUbicacionViviendaMismaProyecto(Boolean.FALSE);
            postulacionFOVISModeloDTO.setUbicacionVivienda(ubicacionResult);
            solicitudPostulacionDTO.getPostulacion().setUbicacionVivienda(ubicacionResult);
        }
        /* Se almacenan los datos de la postulación. */
        postulacionFOVISModeloDTO = crearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);


        solicitudPostulacionDTO.setPostulacion(postulacionFOVISModeloDTO);


        /* Modifica la postulación. */
        if (solicitudPostulacionDTO.getIdSolicitud() != null) {
            SolicitudPostulacionModeloDTO solicitudModeloDTO = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());
            if (tipoPostulacion != null && (TipoPostulacionFOVISEnum.ABREVIADA.equals(tipoPostulacion)
                    || TipoPostulacionFOVISEnum.ESCALADA.equals(tipoPostulacion))) {
                /* Se consulta si ya tiene un numero de radicación asociado. */
                solicitudModeloDTO = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());
                /* Si no tiene se radica la solicitud. */
                if (solicitudModeloDTO.getNumeroRadicacion() == null) {
                    /* Se radica la solicitud. */
                    this.radicarSolicitud(solicitudPostulacionDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
                    /* Se consulta el número de radicación. */
                    solicitudModeloDTO = consultarSolicitudPostulacion(solicitudPostulacionDTO.getIdSolicitud());
                    solicitudPostulacionDTO.setNumeroRadicacion(solicitudModeloDTO.getNumeroRadicacion());
                }
                /* Se actualiza el estado de la solicitud de postulación */
                if (TipoPostulacionFOVISEnum.ABREVIADA.equals(tipoPostulacion)) {
                    solicitudModeloDTO.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_RADICADA);
                } else if (TipoPostulacionFOVISEnum.ESCALADA.equals(tipoPostulacion)) {
                    solicitudModeloDTO.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA);
                }
                /* Se asigna el estado de la documentación a PENDIENTE_POR_ENVIAR */
                solicitudModeloDTO.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                solicitudModeloDTO.setFechaRadicacion((new Date()).getTime());
                solicitudModeloDTO.setTipoRadicacion(TipoRadicacionEnum.ABREVIADA);
            } else {
                if (solicitudModeloDTO.getTipoRadicacion() == null) {
                    solicitudModeloDTO.setTipoRadicacion(TipoRadicacionEnum.COMPLETA);
                }
            }
            solicitudModeloDTO.setIdPostulacionFOVIS(postulacionFOVISModeloDTO.getIdPostulacion());
            this.crearActualizarSolicitudPostulacion(solicitudModeloDTO);
        }

        ListaChequeoDTO listaChequeoPostulacion = solicitudPostulacionDTO.getListaChequeo();
        listaChequeoPostulacion.setNumeroIdentificacion(jefeHogarModeloDTO.getNumeroIdentificacion());
        listaChequeoPostulacion.setTipoIdentificacion(jefeHogarModeloDTO.getTipoIdentificacion());
        listaChequeoPostulacion.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
        listaChequeoPostulacion.setListaFOVIS(Boolean.TRUE);
        // Almacena lista de chequeo documental de postulación.
        guardarListaChequeo(listaChequeoPostulacion);

        ListaChequeoDTO listaChequeoJefeHogar = solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar();
        listaChequeoJefeHogar.setNumeroIdentificacion(jefeHogarModeloDTO.getNumeroIdentificacion());
        listaChequeoJefeHogar.setTipoIdentificacion(jefeHogarModeloDTO.getTipoIdentificacion());
        listaChequeoJefeHogar.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
        listaChequeoJefeHogar.setListaFOVIS(Boolean.TRUE);
        // Almacena lista de chequeo documental de JefeHogar.
        guardarListaChequeo(listaChequeoJefeHogar);


        /* Se crean condiciones especiales Jefe de Hogar */
        if (solicitudPostulacionDTO.getCondicionesEspeciales() != null) {
            this.crearListaCondicionEspecialPersona(jefeHogarModeloDTO.getTipoIdentificacion(),
                    jefeHogarModeloDTO.getNumeroIdentificacion(), solicitudPostulacionDTO.getCondicionesEspeciales(),
                    postulacionFOVISModeloDTO.getIdPostulacion());
        }

        List<Long> integrantesActivos = new ArrayList<>();
        for (IntegranteHogarModeloDTO integranteHogarModeloDTO : solicitudPostulacionDTO.getIntegrantesHogar()) {
            integranteHogarModeloDTO.setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());
            integranteHogarModeloDTO.setIdPostulacion(postulacionFOVISModeloDTO.getIdPostulacion());
            if (integranteHogarModeloDTO.getIdIntegranteHogar() == null) {
                integranteHogarModeloDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
            }
            integranteHogarModeloDTO = crearActualizarIntegranteHogar(integranteHogarModeloDTO);
            integrantesActivos.add(integranteHogarModeloDTO.getIdIntegranteHogar());

            /* Se crean condiciones especiales de cada Integrante del Hogar */
            if (integranteHogarModeloDTO.getCondicionesEspeciales() != null) {
                this.crearListaCondicionEspecialPersona(integranteHogarModeloDTO.getTipoIdentificacion(),
                        integranteHogarModeloDTO.getNumeroIdentificacion(), integranteHogarModeloDTO.getCondicionesEspeciales(),
                        postulacionFOVISModeloDTO.getIdPostulacion());
            }

            ListaChequeoDTO listaChequeoIntegranteHogar = integranteHogarModeloDTO.getListaChequeo();
            listaChequeoIntegranteHogar.setNumeroIdentificacion(integranteHogarModeloDTO.getNumeroIdentificacion());
            listaChequeoIntegranteHogar.setTipoIdentificacion(integranteHogarModeloDTO.getTipoIdentificacion());
            listaChequeoIntegranteHogar.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
            listaChequeoIntegranteHogar.setListaFOVIS(Boolean.TRUE);
            // Almacena lista de chequeo documental de cada integrante de Hogar
            guardarListaChequeo(listaChequeoIntegranteHogar);
        }
        InactivarIntegrantesHogarNoRelacionados inactivarIntegrantes = new InactivarIntegrantesHogarNoRelacionados(
                postulacionFOVISModeloDTO.getIdPostulacion(), jefeHogarModeloDTO.getIdJefeHogar(), integrantesActivos);
        inactivarIntegrantes.execute();

        // Almacena en AhorroPrevio 3.1.2.5
        crearActualizarAhorroPrevio(solicitudPostulacionDTO.getPostulacion());

        // Almacena en RecursoComplementario 3.1.2.6
        crearActualizarRecursoComplementario(solicitudPostulacionDTO.getPostulacion());

        return solicitudPostulacionDTO;
    }

    /**
     * Se encarga de guardar los datos de la solicitud de postulación
     *
     * @param tipoPostulacion
     */
    private SolicitudPostulacionModeloDTO crearSolicitudPostulacionInicial(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISModeloDTO,
                                                                           UserDTO userDTO) {

        SolicitudPostulacionModeloDTO solicitud = new SolicitudPostulacionModeloDTO();
        solicitud.setCanalRecepcion(solicitudPostulacionFOVISModeloDTO.getCanalRecepcion());
        solicitud.setClasificacion(ClasificacionEnum.HOGAR);
        solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitud.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitud.setTipoTransaccion(solicitudPostulacionFOVISModeloDTO.getTipoTransaccionEnum());
        solicitud.setMetodoEnvio(solicitudPostulacionFOVISModeloDTO.getMetodoEnvio());
        solicitud.setFechaCreacion((new Date()).getTime());
        if (solicitudPostulacionFOVISModeloDTO.getPostulacion() != null
                && solicitudPostulacionFOVISModeloDTO.getPostulacion().getIdPostulacion() != null) {
            solicitud.setIdPostulacionFOVIS(solicitudPostulacionFOVISModeloDTO.getPostulacion().getIdPostulacion());
        }
        CrearActualizarSolicitudPostulacion crearActualizarSolicitudPostulacion = new CrearActualizarSolicitudPostulacion(solicitud);
        crearActualizarSolicitudPostulacion.execute();
        solicitud = crearActualizarSolicitudPostulacion.getResult();
        return solicitud;
    }

    /**
     * Método que invoca el servicio de actualización de una solicitud de
     * postulación
     *
     * @param solicitudPostulacionDTO La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private SolicitudPostulacionModeloDTO crearActualizarSolicitudPostulacion(SolicitudPostulacionModeloDTO solicitudPostulacionDTO) {
        logger.debug("Inicia el método crearActualizarSolicitudPostulacion");
        CrearActualizarSolicitudPostulacion service = new CrearActualizarSolicitudPostulacion(solicitudPostulacionDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarSolicitudPostulacion");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización de una postulación FOVIS
     *
     * @param postulacionFOVISDTO La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Inicia el método crearActualizarPostulacionFOVIS");
        CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarPostulacionFOVIS");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de un oferente
     *
     * @param tipoIdentificacion   Tipo de identificación del oferente
     * @param numeroIdentificacion Número de identificación del oferente
     * @return Objeo <code>OferenteModeloDTO</code> con la información del
     * oferente
     */
    private OferenteModeloDTO consultarOferente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarOferente service = new ConsultarOferente(numeroIdentificacion, tipoIdentificacion);
        service.execute();
        return service.getResult();
    }


    /**
     * Método que invoca el servicio de consulta de un oferente
     *
     * @param tipoIdentificacion   Tipo de identificación del oferente
     * @param numeroIdentificacion Número de identificación del oferente
     * @return Objeo <code>OferenteModeloDTO</code> con la información del
     * oferente
     */
    private ProveedorModeloDTO consultarProveedor(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarProveedor service = new ConsultarProveedor(numeroIdentificacion, tipoIdentificacion);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que crea una lista de oferentes a partir de una lista de
     * empleadores
     *
     * @param listaEmpleador La lista de empleadores
     * @return La lista de oferentes
     */
    private List<OferenteDTO> transformarListaEmpleadorOferente(List<EmpresaModeloDTO> listaEmpleador) {
        logger.debug("Inicia transformarListaEmpleadorOferente(List<EmpresaModeloDTO>)");
        List<OferenteDTO> listaOferenteDTO = new ArrayList<OferenteDTO>();

        if (listaEmpleador != null) {
            for (EmpresaModeloDTO empresa : listaEmpleador) {
                OferenteDTO oferenteDTO = new OferenteDTO();
                OferenteModeloDTO oferenteModeloDTO = new OferenteModeloDTO();
                oferenteModeloDTO.setEmpresa(empresa);

                OferenteModeloDTO oferenteConsultadoDTO = consultarOferente(empresa.getTipoIdentificacion(),
                        empresa.getNumeroIdentificacion());
                oferenteDTO.setEsOferente(Boolean.FALSE);

                if (oferenteConsultadoDTO != null) {
                    oferenteModeloDTO.setIdOferente(oferenteConsultadoDTO.getIdOferente());
                    if (oferenteConsultadoDTO.getRepresentanteLegal() != null) {
                        oferenteModeloDTO.setRepresentanteLegal(oferenteConsultadoDTO.getRepresentanteLegal());
                    }
                    oferenteDTO.setEsOferente(Boolean.TRUE);
                    //Consultar documentos soporte asociados al oferente
                    if (oferenteConsultadoDTO.getIdOferente() != null) {
                        List<DocumentoSoporteModeloDTO> listaDocumentosSoporte = consultarDocumentosSoportePorIdOferente(
                                oferenteConsultadoDTO.getIdOferente());
                        oferenteModeloDTO.setListaDocumentosSoporte(listaDocumentosSoporte);
                    }
                }

                oferenteDTO.setOferente(oferenteModeloDTO);
                listaOferenteDTO.add(oferenteDTO);
            }
        }

        logger.debug("Finaliza transformarListaEmpleadorOferente(List<EmpresaModeloDTO>)");
        return listaOferenteDTO;
    }


    /**
     * Método que crea una lista de proveedor a partir de una lista de
     * empleadores
     *
     * @param listaEmpleador La lista de empleadores
     * @return La lista de proveedor
     */
    private List<ProveedorDTO> transformarListaEmpleadorProveedor(List<EmpresaModeloDTO> listaEmpleador) {
        logger.debug("Inicia transformarListaEmpleadorProveedor(List<EmpresaModeloDTO>)");
        List<ProveedorDTO> listaProveedorDTO = new ArrayList<ProveedorDTO>();

        if (listaEmpleador != null) {
            for (EmpresaModeloDTO empresa : listaEmpleador) {
                ProveedorDTO proveedorDTO = new ProveedorDTO();
                ProveedorModeloDTO proveedorModeloDTO = new ProveedorModeloDTO();
                proveedorModeloDTO.setEmpresa(empresa);

                ProveedorModeloDTO proveedorConsultadoDTO = consultarProveedor(empresa.getTipoIdentificacion(),
                        empresa.getNumeroIdentificacion());
                proveedorDTO.setEsProveedor(Boolean.FALSE);

                if (proveedorConsultadoDTO != null) {
                    proveedorModeloDTO.setIdOferente(proveedorConsultadoDTO.getIdOferente());
                    if (proveedorConsultadoDTO.getRepresentanteLegal() != null) {
                        proveedorModeloDTO.setRepresentanteLegal(proveedorConsultadoDTO.getRepresentanteLegal());
                    }
                    proveedorDTO.setEsProveedor(Boolean.TRUE);
                    //Consultar documentos soporte asociados al oferente
                    if (proveedorConsultadoDTO.getIdOferente() != null) {
                        List<DocumentoSoporteModeloDTO> listaDocumentosSoporte = consultarDocumentosSoportePorIdProveedor(
                                proveedorConsultadoDTO.getIdOferente());
                        proveedorModeloDTO.setListaDocumentosSoporte(listaDocumentosSoporte);
                    }
                }

                proveedorDTO.setProveedor(proveedorModeloDTO);
                listaProveedorDTO.add(proveedorDTO);
            }
        }

        logger.debug("Finaliza transformarListaEmpleadorOferente(List<EmpresaModeloDTO>)");
        return listaProveedorDTO;
    }

    /**
     * Método que crea una lista de oferentes a partir de una lista de personas
     *
     * @param listaPersonaDTO La lista de personas
     * @return La lista de oferentes
     */
    private List<OferenteDTO> transformarListaPersonaOferente(List<PersonaDTO> listaPersonaDTO) {
        logger.debug("Inicia transformarListaPersonaOferente(List<PersonaDTO>)");
        List<OferenteDTO> listaOferenteDTO = new ArrayList<OferenteDTO>();

        if (listaPersonaDTO != null) {
            for (PersonaDTO personaDTO : listaPersonaDTO) {
                OferenteDTO oferenteDTO = new OferenteDTO();
                OferenteModeloDTO oferenteModeloDTO = new OferenteModeloDTO();

                PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                personaModeloDTO.convertFromPersonaDTO(personaDTO);
                oferenteModeloDTO.setPersona(personaModeloDTO);

                OferenteModeloDTO oferenteConsultadoDTO = consultarOferente(personaModeloDTO.getTipoIdentificacion(),
                        personaModeloDTO.getNumeroIdentificacion());
                oferenteDTO.setEsOferente(Boolean.FALSE);

                if (oferenteConsultadoDTO != null) {
                    oferenteModeloDTO = oferenteConsultadoDTO;
                    oferenteModeloDTO.setPersona(personaModeloDTO);
                    oferenteDTO.setEsOferente(Boolean.TRUE);
                    //Consultar documentos soporte asociados al oferente
                    if (oferenteConsultadoDTO.getIdOferente() != null) {
                        List<DocumentoSoporteModeloDTO> listaDocumentosSoporte = consultarDocumentosSoportePorIdOferente(
                                oferenteConsultadoDTO.getIdOferente());
                        oferenteModeloDTO.setListaDocumentosSoporte(listaDocumentosSoporte);
                    }
                }

                oferenteDTO.setOferente(oferenteModeloDTO);
                listaOferenteDTO.add(oferenteDTO);
            }
        }

        logger.debug("Finaliza transformarListaPersonaOferente(List<PersonaDTO>)");
        return listaOferenteDTO;
    }


    /**
     * Método que crea una lista de proveedor a partir de una lista de personas
     *
     * @param listaPersonaDTO La lista de personas
     * @return La lista de proveedor
     */
    private List<ProveedorDTO> transformarListaPersonaProveedor(List<PersonaDTO> listaPersonaDTO) {
        List<ProveedorDTO> listaProveedorDTO = new ArrayList<ProveedorDTO>();

        if (listaPersonaDTO != null) {
            for (PersonaDTO personaDTO : listaPersonaDTO) {
                ProveedorDTO proveedorDTO = new ProveedorDTO();
                ProveedorModeloDTO proveedorModeloDTO = new ProveedorModeloDTO();

                PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                personaModeloDTO.convertFromPersonaDTO(personaDTO);
                proveedorModeloDTO.setPersona(personaModeloDTO);

                ProveedorModeloDTO proveedorConsultadoDTO = consultarProveedor(personaModeloDTO.getTipoIdentificacion(),
                        personaModeloDTO.getNumeroIdentificacion());
                proveedorDTO.setEsProveedor(Boolean.FALSE);

                if (proveedorConsultadoDTO != null) {
                    proveedorModeloDTO = proveedorConsultadoDTO;
                    proveedorModeloDTO.setPersona(personaModeloDTO);
                    proveedorDTO.setEsProveedor(Boolean.TRUE);
                    //Consultar documentos soporte asociados al oferente
                    if (proveedorConsultadoDTO.getIdOferente() != null) {
                        List<DocumentoSoporteModeloDTO> listaDocumentosSoporte = consultarDocumentosSoportePorIdProveedor(
                                proveedorConsultadoDTO.getIdOferente());
                        proveedorModeloDTO.setListaDocumentosSoporte(listaDocumentosSoporte);
                    }
                }

                proveedorDTO.setProveedor(proveedorModeloDTO);
                listaProveedorDTO.add(proveedorDTO);
            }
        }

        return listaProveedorDTO;
    }


    /**
     * Método que invoca el servicio de actualización de un oferente
     *
     * @param oferenteDTO Información del oferente
     * @return El registro actualizado
     */
    private OferenteModeloDTO crearActualizarOferente(OferenteModeloDTO oferenteDTO) {
        CrearActualizarOferente service = new CrearActualizarOferente(oferenteDTO);
        service.execute();
        return service.getResult();
    }


    /**
     * Método que invoca el servicio de actualización de un LyD proveedor
     *
     * @param LegalizacionDesembolosoProveedorModeloDTO Información del lyD
     * @return El registro actualizado
     */
    private LegalizacionDesembolosoProveedorModeloDTO crearActualizarLegalizacionDesembolsoProveedor(LegalizacionDesembolosoProveedorModeloDTO LyDproveedor) {
        CrearActualizarLegalizacionDesembolsoProveedor service = new CrearActualizarLegalizacionDesembolsoProveedor(LyDproveedor);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización de un Proveedor
     *
     * @param ProveedorDTO Información del Proveedor
     * @return El registro actualizado
     */
    private ProveedorModeloDTO crearActualizarProveedor(ProveedorModeloDTO proveedorDTO) {
        CrearActualizarProveedor service = new CrearActualizarProveedor(proveedorDTO);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de datos en la tabla
     * <code>Empresa</code>
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param razonSocial          Razón social
     * @return Lista de empleadores que cumplen con los criterios de consulta
     */
    private List<EmpresaModeloDTO> consultarEmpresa(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                    String razonSocial) {
        ConsultarEmpresa buscarEmpresa = new ConsultarEmpresa(numeroIdentificacion, tipoIdentificacion, razonSocial);
        buscarEmpresa.execute();
        return buscarEmpresa.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de datos en la tabla
     * <code>Empleador</code>
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param razonSocial          Razón social
     * @return Lista de empleadores que cumplen con los criterios de consulta
     */
    private List<Empleador> consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial) {
        logger.debug("Inicia el método consultarEmpleador");
        BuscarEmpleador buscarEmpleador = new BuscarEmpleador(null, numeroIdentificacion, tipoIdentificacion, razonSocial);
        buscarEmpleador.execute();
        logger.debug("Finaliza el método consultarEmpleador");
        return buscarEmpleador.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de roles de un afiliado
     *
     * @param tipoIdentificacion   Tipo de identificación del afiliado
     * @param numeroIdentificacion Número de identificación del afiliado
     * @return Lista de roles del afiliado
     */
    private List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia el método consultarRolesAfiliado");
        ConsultarRolesAfiliado consultarRolesAfiliado = new ConsultarRolesAfiliado(null, numeroIdentificacion, tipoIdentificacion);
        consultarRolesAfiliado.execute();
        logger.debug("Finaliza el método consultarRolesAfiliado");
        return consultarRolesAfiliado.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de departamento excepción FOVIS
     *
     * @return Objeto <code>DepartamentoModeloDTO</code> con la información del apartamento
     */
    private DepartamentoModeloDTO consultarDepartamentoExcepcionFOVIS() {
        logger.debug("Inicia el método consultarDepartamentoExcepcionFOVIS");
        ConsultarDepartamentoExcepcionFOVIS service = new ConsultarDepartamentoExcepcionFOVIS();
        service.execute();
        logger.debug("Finaliza el método consultarDepartamentoExcepcionFOVIS");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de datos en la tabla
     * <code>Persona</code>
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @return Objeto <code>PersonaModeloDTO</code> con la información de la
     * persona
     */
    private List<PersonaDTO> consultarPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
                                              String primerApellido) {
        logger.debug("Inicia el método consultarPersona");
        BuscarPersonas service = new BuscarPersonas(null, primerApellido, null, null, primerNombre, null, numeroIdentificacion,
                tipoIdentificacion, null);
        service.execute();
        logger.debug("Finaliza el método consultarPersona");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>JefeHogar</code>
     *
     * @param jefeHogarDTO Información del jefe de hogar a actualizar
     * @return El registro actualizado
     */
    private JefeHogarModeloDTO crearActualizarJefeHogar(JefeHogarModeloDTO jefeHogarDTO) {
        logger.debug("Inicia el método crearActualizarJefeHogar");
        CrearActualizarJefeHogar service = new CrearActualizarJefeHogar(jefeHogarDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarJefeHogar");
        return service.getResult();
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
     * Método que invoca el servicio de actualización en la tabla
     * <code>IntegranteHogar</code>
     *
     * @param integranteHogarDTO Información del integrante hogar a actualizar
     * @return El registro actualizado
     */
    private IntegranteHogarModeloDTO crearActualizarIntegranteHogar(IntegranteHogarModeloDTO integranteHogarDTO) {
        logger.debug("Inicia el método crearActualizarIntegranteHogar");
        CrearActualizarIntegranteHogar service = new CrearActualizarIntegranteHogar(integranteHogarDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarIntegranteHogar");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>RecursoComplementario</code>
     *
     * @param recursoComplementarioDTO Información del recurso complementario a actualizar
     * @return El registro actualizado
     */
    private void crearActualizarRecursoComplementario(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Inicia el método crearActualizarRecursoComplementario");
        List<RecursoComplementarioModeloDTO> recursosComplementariosDTO = new ArrayList<>();

        if (postulacionFOVISDTO.getAhorroOtrasModalidades() != null && postulacionFOVISDTO.getAhorroOtrasModalidades().getValor() != null) {
            postulacionFOVISDTO.getAhorroOtrasModalidades().setNombre(TipoRecursoComplementarioEnum.AHORRO_OTRAS_MODALIDADES);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAhorroOtrasModalidades());
        }
        if (postulacionFOVISDTO.getAportesEnteTerritorial() != null && postulacionFOVISDTO.getAportesEnteTerritorial().getValor() != null) {
            postulacionFOVISDTO.getAportesEnteTerritorial().setNombre(TipoRecursoComplementarioEnum.APORTES_ENTE_TERRITORIAL);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAportesEnteTerritorial());
        }
        if (postulacionFOVISDTO.getAportesSolidarios() != null && postulacionFOVISDTO.getAportesSolidarios().getValor() != null) {
            postulacionFOVISDTO.getAportesSolidarios().setNombre(TipoRecursoComplementarioEnum.APORTES_SOLIDARIOS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAportesSolidarios());

        }
        if (postulacionFOVISDTO.getCesantiasNoInmovilizadas() != null
                && postulacionFOVISDTO.getCesantiasNoInmovilizadas().getValor() != null) {
            postulacionFOVISDTO.getCesantiasNoInmovilizadas().setNombre(TipoRecursoComplementarioEnum.CESANTIAS_NO_INMOVILIZADAS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getCesantiasNoInmovilizadas());
        }
        if (postulacionFOVISDTO.getCreditoAprobado() != null && postulacionFOVISDTO.getCreditoAprobado().getValor() != null) {
            postulacionFOVISDTO.getCreditoAprobado().setNombre(TipoRecursoComplementarioEnum.CREDITO_APROBADO);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getCreditoAprobado());
        }
        if (postulacionFOVISDTO.getDonacionOtrasEntidades() != null && postulacionFOVISDTO.getDonacionOtrasEntidades().getValor() != null) {
            postulacionFOVISDTO.getDonacionOtrasEntidades().setNombre(TipoRecursoComplementarioEnum.DONACION_OTRAS_ENTIDADES);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getDonacionOtrasEntidades());
        }
        if (postulacionFOVISDTO.getEvaluacionCrediticia() != null && postulacionFOVISDTO.getEvaluacionCrediticia().getValor() != null) {
            postulacionFOVISDTO.getEvaluacionCrediticia().setNombre(TipoRecursoComplementarioEnum.EVALUACION_CREDITICIA);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getEvaluacionCrediticia());
        }
        if (postulacionFOVISDTO.getOtrosRecursos() != null && postulacionFOVISDTO.getOtrosRecursos().getValor() != null) {
            postulacionFOVISDTO.getOtrosRecursos().setNombre(TipoRecursoComplementarioEnum.OTROS_RECURSOS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getOtrosRecursos());
        }
        if (postulacionFOVISDTO.getValorAvanceObra() != null && postulacionFOVISDTO.getValorAvanceObra().getValor() != null) {
            postulacionFOVISDTO.getValorAvanceObra().setNombre(TipoRecursoComplementarioEnum.VALOR_AVANCE_OBRA);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getValorAvanceObra());
        }
        if (!recursosComplementariosDTO.isEmpty()) {
            CrearActualizarRecursoComplementario service = new CrearActualizarRecursoComplementario(postulacionFOVISDTO.getIdPostulacion(),
                    recursosComplementariosDTO);
            service.execute();
        }
        logger.debug("Finaliza el método crearActualizarRecursoComplementario");
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>AhorroPrevio</code>
     *
     * @param ahorroPrevioDTO Información del ahorro previo a actualizar
     * @return El registro actualizado
     */
    private void crearActualizarAhorroPrevio(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Inicia el método crearActualizarAhorroPrevio");
        List<AhorroPrevioModeloDTO> ahorrosPreviosDTO = new ArrayList<>();

        if (postulacionFOVISDTO.getAhorroProgramado() != null && postulacionFOVISDTO.getAhorroProgramado().getValor() != null) {
            postulacionFOVISDTO.getAhorroProgramado().setNombreAhorro(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAhorroProgramado());
        }
        if (postulacionFOVISDTO.getAhorroProgramadoContractual() != null
                && postulacionFOVISDTO.getAhorroProgramadoContractual().getValor() != null) {
            postulacionFOVISDTO.getAhorroProgramadoContractual()
                    .setNombreAhorro(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAhorroProgramadoContractual());
        }
        if (postulacionFOVISDTO.getAportesPeriodicos() != null && postulacionFOVISDTO.getAportesPeriodicos().getValor() != null) {
            postulacionFOVISDTO.getAportesPeriodicos().setNombreAhorro(TipoAhorroPrevioEnum.APORTES_PERIODICOS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAportesPeriodicos());
        }
        if (postulacionFOVISDTO.getCesantiasInmovilizadas() != null && postulacionFOVISDTO.getCesantiasInmovilizadas().getValor() != null) {
            postulacionFOVISDTO.getCesantiasInmovilizadas().setNombreAhorro(TipoAhorroPrevioEnum.CESANTIAS_INMOVILIZADAS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCesantiasInmovilizadas());
        }
        if (postulacionFOVISDTO.getCuotaInicial() != null && postulacionFOVISDTO.getCuotaInicial().getValor() != null) {
            postulacionFOVISDTO.getCuotaInicial().setNombreAhorro(TipoAhorroPrevioEnum.CUOTA_INICIAL);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCuotaInicial());
        }
        if (postulacionFOVISDTO.getCuotasPagadas() != null && postulacionFOVISDTO.getCuotasPagadas().getValor() != null) {
            postulacionFOVISDTO.getCuotasPagadas().setNombreAhorro(TipoAhorroPrevioEnum.CUOTAS_PAGADAS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCuotasPagadas());
        }
        if (postulacionFOVISDTO.getValorLoteTerreno() != null && postulacionFOVISDTO.getValorLoteTerreno().getValor() != null) {
            postulacionFOVISDTO.getValorLoteTerreno().setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_O_TERRENO_PROPIO);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteTerreno());
        }
        if (postulacionFOVISDTO.getValorLoteOPV() != null && postulacionFOVISDTO.getValorLoteOPV().getValor() != null) {
            postulacionFOVISDTO.getValorLoteOPV().setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_OPV);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteOPV());
        }
        if (postulacionFOVISDTO.getValorLoteSubsidioMunicipal() != null
                && postulacionFOVISDTO.getValorLoteSubsidioMunicipal().getValor() != null) {
            postulacionFOVISDTO.getValorLoteSubsidioMunicipal()
                    .setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteSubsidioMunicipal());
        }
        if (!ahorrosPreviosDTO.isEmpty()) {
            CrearActualizarAhorroPrevio service = new CrearActualizarAhorroPrevio(postulacionFOVISDTO.getIdPostulacion(),
                    ahorrosPreviosDTO);
            service.execute();
        }
        logger.debug("Finaliza el método crearActualizarAhorroPrevio");
    }

    /**
     * Método que invoca el servicio de almacenamiento de una lista de chequeo
     * de requisitos documentales
     *
     * @param listaChequeo La información de la lista de chequeo a almacenar
     */
    private void guardarListaChequeo(ListaChequeoDTO listaChequeo) {
        logger.debug("Inicio de método guardarListaChequeo");
        GuardarListaChequeo service = new GuardarListaChequeo(listaChequeo);
        service.execute();
        logger.debug("Fin de método guardarListaChequeo");
    }

    /**
     * Método que obtiene el identificador de una caja de compensación, por nombre
     *
     * @param nombre Nombre de la sede
     * @return El identificador
     */
    private Long consultarIdSedeCajaCompensacion(String nombre) {
        logger.debug("Inicia método consultarIdSedeCajaCompensacion");
        ConsultarSedesCajaCompensacion consultarSedesCajaCompensacion = new ConsultarSedesCajaCompensacion();
        consultarSedesCajaCompensacion.execute();
        List<SedeCajaCompensacion> listaSedes = consultarSedesCajaCompensacion.getResult();

        for (SedeCajaCompensacion sede : listaSedes) {
            if (sede.getNombre().equals(nombre)) {
                logger.debug("Finaliza método consultarIdSedeCajaCompensacion");
                return sede.getIdSedeCajaCompensacion();
            }
        }

        logger.debug("Finaliza método consultarIdSedeCajaCompensacion. Sede no encontrada.");
        return null;
    }

    /**
     * Método que invoca el servicio de consulta de un afiliado
     *
     * @param tipoIdentificacion   Tipo de identificación del afiliado
     * @param numeroIdentificacion Número de identificación del afiliado
     * @param primerNombre         Primer nombre del afiliado
     * @param primerApellido       Primer apellido del afiliado
     * @return Objeto <code>PersonaDTO</code> con la información del afiliado
     */
    private AfiliadoModeloDTO buscarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicio de método buscarAfiliado");
        ConsultarDatosAfiliado service = new ConsultarDatosAfiliado(numeroIdentificacion, tipoIdentificacion);
        service.execute();
        AfiliadoModeloDTO afiliadoModeloDTO = service.getResult();
        logger.debug("Fin de método buscarAfiliado");
        return afiliadoModeloDTO;
    }

    /**
     * Método que invoca el servicio de creación o actualización de un registro
     * en la tabla <code>Ubicacion</code>
     *
     * @param ubicacionDTO La información del registro a actualizar
     * @return El registro actualizado
     */
    private UbicacionModeloDTO crearActualizarUbicacion(UbicacionModeloDTO ubicacionDTO) {
        logger.debug("Inicio de método crearActualizarUbicacion");
        CrearActualizarUbicacion service = new CrearActualizarUbicacion(ubicacionDTO);
        service.execute();
        logger.debug("Fin de método crearActualizarUbicacion");
        return service.getResult();
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

    /**
     * Método que invoca el servicio de creación de un conjunto de condiciones
     * especiales por persona
     *
     * @param idPersona                  Identificador único de la persona
     * @param listaCondicionesEspeciales Lista de identificadores de las condiciones especiales
     * @param idPostulacion              idPostulacion de la postulacion
     */
    private void crearListaCondicionEspecialPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                    List<NombreCondicionEspecialEnum> listaCondicionesEspeciales, Long idPostulacion) {
        logger.debug("Inicio de método crearListaCondicionEspecialPersona");
        if (listaCondicionesEspeciales != null && !listaCondicionesEspeciales.isEmpty()) {
            CrearListaCondicionEspecialPersona service = new CrearListaCondicionEspecialPersona(numeroIdentificacion, tipoIdentificacion,
                    listaCondicionesEspeciales, idPostulacion);
            service.execute();
        }
        logger.debug("Fin de método crearListaCondicionEspecialPersona");
    }

    /**
     * Método que invoca el servicio de actualización de un proyecto de vivienda
     *
     * @param proyectoSolucionViviendaDTO La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private ProyectoSolucionViviendaModeloDTO crearActualizarProyectoSolucionVivienda(
            ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO) {
        logger.debug("Inicia el método crearActualizarProyectoSolucionVivienda");
        CrearActualizarProyectoSolucionVivienda service = new CrearActualizarProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarProyectoSolucionVivienda");
        return service.getResult();
    }

    /**
     * Método encargado invocar el servicio que guarda temporalmente los datos de la solicitud.
     *
     * @param solicitudPostulacionFOVISModelDTO dto con los datos a guardar.
     */
    private void guardarDatosTemporal(SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISModelDTO) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(solicitudPostulacionFOVISModelDTO);
            GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(solicitudPostulacionFOVISModelDTO.getIdSolicitud(),
                    jsonPayload);
            datosTemporalService.execute();
        } catch (JsonProcessingException e) {
            logger.error("Error convirtiendo la solicitud de postulación a JSON", e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Método que invoca el servicio de radicación de una solicitud
     *
     * @param idSolicitudGlobal    Identificador único de la solicitud global
     * @param sedeCajaCompensacion Sede de la CCF del usuario autenticado
     */
    private void radicarSolicitud(Long idSolicitudGlobal, String sedeCajaCompensacion) {
        logger.debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud service = new RadicarSolicitud(idSolicitudGlobal, sedeCajaCompensacion);
        service.execute();
        logger.debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
    }

    /**
     * Método que invoca el servicio que consulta los datos temporales
     *
     * @param idSolicitudGlobal Identificador de la solicitud global.
     * @return <code>jsonPayload</code> con los datos temporales.
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        logger.debug("Inicio de método consultarDatosTemporales");
        String jsonPayload = new String();
        ConsultarDatosTemporales service = new ConsultarDatosTemporales(idSolicitud);
        service.execute();
        jsonPayload = (String) service.getResult();
        logger.debug("Fin de método consultarDatosTemporales");
        return jsonPayload;
    }

    /**
     * Método que consulta una solicitud de postulación.
     *
     * @param idSolicitud Es el identificador de la solicitud global.
     * @return solicitudNovedadDTO Solicitud de postulación consultada.
     */
    private SolicitudPostulacionModeloDTO consultarSolicitudPostulacion(Long idSolicitud) {
        logger.debug("Inicia servicio consultarSolicitudPostulacion(Long)");
        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = new SolicitudPostulacionModeloDTO();
        ConsultarSolicitudPostulacion consultarSolicitudPostulacion = new ConsultarSolicitudPostulacion(idSolicitud);
        consultarSolicitudPostulacion.execute();
        solicitudPostulacionDTO = consultarSolicitudPostulacion.getResult();
        logger.debug("Finaliza servicio consultarSolicitudPostulacion(Long)");
        return solicitudPostulacionDTO;
    }

    /**
     * Método que usa un servicio para verificar si existen escalamientos sin resultado para una solicitud de postulación.
     *
     * @param idSolicitud Identificador de la solicitud global.
     * @return Resultado de la existencia o no de escalamientos sin resultado.
     */
    public boolean existenEscalamientosSinResultado(Long idSolicitud) {
        ExistenEscalamientosSinResultado servicio = new ExistenEscalamientosSinResultado(idSolicitud);
        servicio.execute();
        return servicio.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una solicitud de postulación
     */
    private void actualizarEstadoSolicitudPostulacion(Long idSolicitud, EstadoSolicitudPostulacionEnum estadoSolicitud) {
        logger.debug("Inicia actualizarEstadoSolicitudPostulacion(Long, EstadoSolicitudPostulacionEnum)");
        ActualizarEstadoSolicitudPostulacion actualizarEstadoSolPostulService = new ActualizarEstadoSolicitudPostulacion(idSolicitud,
                estadoSolicitud);
        actualizarEstadoSolPostulService.execute();
        logger.debug("Finaliza actualizarEstadoSolicitudPostulacion(Long, EstadoSolicitudPostulacionEnum)");
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
     * Método que actualiza el estado del hogar de una postulación FOVIS.
     *
     * @param idPostulacionFOVIS identificador de la postulación.
     * @param estadoHogar        Nuevo estado a asignar.
     */
    private void cambiarEstadoHogar(Long idPostulacionFOVIS, EstadoHogarEnum estadoHogar) {
        ActualizarEstadoHogar actualizarEstadoHogar = new ActualizarEstadoHogar(idPostulacionFOVIS, estadoHogar);
        actualizarEstadoHogar.execute();
    }

    /**
     * Inicia Proceso BPM de Postulaciones WEB o Presencial
     *
     * @param idSolicitud
     * @param procesoEnum
     * @param usuario
     * @return
     */
    private Long iniciarProcesoPostulacion(Long idSolicitud, ProcesoEnum procesoEnum, UserDTO usuario) {
        logger.debug("Inicia iniciarProcesoPostulacion( idSolicitudGlobal, procesoEnum )");
        Long idInstanciaProcesoPostulacion = new Long(0);
        Map<String, Object> parametrosProceso = new HashMap<String, Object>();

        if (ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL.equals(procesoEnum)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_PFP_TIEMPO_PROCESO_SOLICITUD);
            String tiempoGestionCruces = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_PFP_TIEMPO_PEND_GESTION_CRUCES);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoGestionCruces", tiempoGestionCruces);
        } else if (ProcesoEnum.POSTULACION_FOVIS_WEB.equals(procesoEnum)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_PFW_TIEMPO_PROCESO_SOLICITUD);
            String tiempoGestionCruces = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_PFW_TIEMPO_PEND_GESTION_CRUCES);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoGestionCruces", tiempoGestionCruces);
        }

        SolicitudPostulacionModeloDTO solicitudPostulacion = consultarSolicitudPostulacion(idSolicitud);

        parametrosProceso.put(ID_SOLICITUD, idSolicitud);
        parametrosProceso.put(USUARIO_FRONT, usuario.getNombreUsuario());
        IniciarProceso iniciarProcesPostulacionService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesPostulacionService.execute();
        idInstanciaProcesoPostulacion = iniciarProcesPostulacionService.getResult();

        /* se actualiza el id instancia proceso */
        solicitudPostulacion.setIdInstanciaProceso(idInstanciaProcesoPostulacion.toString());
        /* se invoca el servicio que actualiza la solicitud de postulacion */
        this.crearActualizarSolicitudPostulacion(solicitudPostulacion);
        logger.debug("Finaliza iniciarProcesoNovedad( idSolicitudGlobal )");
        return idInstanciaProcesoPostulacion;
    }

    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     *
     * @param sedeCaja    <code>Long</code> el identificador del afiliado
     * @param procesoEnum <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     * caja
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        String nombreUsuarioCaja = "";
        ejecutarAsignacion.execute();
        logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        nombreUsuarioCaja = (String) ejecutarAsignacion.getResult();
        return nombreUsuarioCaja;
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     *
     * @param nombreUsuarioCaja <code>String</code> El nombre de usuario del funcionario de la
     *                          caja que realiza la consulta
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     * usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.debug("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        usuarioDTO = (UsuarioDTO) obtenerDatosUsuariosCajaCompensacionService.getResult();
        logger.debug("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return usuarioDTO;
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
     * Método que usa el servicio para escalar una solicitud.
     *
     * @param idSolicitud     identificador global de la solicitud de postulación.
     * @param escalamientoDTO Datos del escalammiento.
     */
    private EscalamientoSolicitudDTO escalarSolicitud(Long idSolicitud, EscalamientoSolicitudDTO escalamientoDTO, UserDTO usuario) {
        logger.debug("Se inicia el método de radicarSolicitud(Long, EscalamientoSolicitudDTO)");
        escalamientoDTO.setFechaCreacion(new Date());
        escalamientoDTO.setUsuarioCreacion(usuario != null ? usuario.getNombreUsuario() : null);
        RegistrarEscalamientoSolicitud escalarSolicitud = new RegistrarEscalamientoSolicitud(idSolicitud, escalamientoDTO);
        escalarSolicitud.execute();
        logger.debug("Finaliza el método de radicarSolicitud(Long, EscalamientoSolicitudDTO)");
        return escalarSolicitud.getResult();
    }

    /**
     * Método que registra el resultado de la revisión por parte de control interno cuando la postulación es hábil.
     *
     * @param solicitudVerificacionTemporalDTO Datos de la solicitud a registrar.
     * @param estadoInicial                    estado por el que se cambiará la solicitud de verificación inicialmente.
     */
    private void registrarPostulacionHabil(SolicitudVerificacionFovisDTO solicitudVerificacionTemporalDTO,
                                           EstadoSolicitudVerificacionFovisEnum estadoInicial) {
        logger.debug("Se inicia registrarPostulacionHabil(SolicitudPostulacionFOVISDTO, EstadoSolicitudPostulacionEnum, UserDTO)");

        actualizarEstadoSolicitudVerificacion(solicitudVerificacionTemporalDTO.getIdSolicitud(), estadoInicial);

        if (TipoSolicitudVerificacionFovisEnum.VERIFICACION
                .equals(solicitudVerificacionTemporalDTO.getSolicitudVerificacionFovisModeloDTO().getTipoVerificacion())) {
            actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud(),
                    EstadoSolicitudPostulacionEnum.POSTULACION_HABIL);
            cambiarEstadoHogar(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                    EstadoHogarEnum.HABIL);
            solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getPostulacion().setEstadoHogar(EstadoHogarEnum.HABIL);

        }
        actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

        actualizarEstadoSolicitudVerificacion(solicitudVerificacionTemporalDTO.getIdSolicitud(),
                EstadoSolicitudVerificacionFovisEnum.CERRADA);
        solicitudVerificacionTemporalDTO.getSolicitudVerificacionFovisModeloDTO()
                .setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.CERRADA);

        // CC155, CC157: Si en el estado de cruces del hogar tiene “Postulación con cruce subsanado pendiente envío a control interno”, 
        //el sistema cambia el valor a “Cruces Subsanados”
        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = this.consultarSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud());
        ActualizarSolicitudesGestionCruceASubsanadas actualizar = new ActualizarSolicitudesGestionCruceASubsanadas(solicitudPostulacionDTO.getIdSolicitudPostulacion());
        actualizar.execute();

        logger.debug("Finaliza registrarPostulacionHabil(SolicitudPostulacionFOVISDTO, EstadoSolicitudPostulacionEnum, UserDTO)");
    }

    /**
     * Método que registra el resultado de la revisión por parte de control interno cuando se encontraron hallazgos,
     * devolviendola al back para revisión.
     *
     * @param solicitudVerificacionTemporalDTO Datos de la solicitud a registrar.
     */
    private void devolverSolicitudHallallazgos(SolicitudVerificacionFovisDTO solicitudVerificacionTemporalDTO) {
        logger.debug("Se inicia devolverSolicitudHallallazgos(SolicitudPostulacionModeloDTO, UserDTO)");

        if (TipoSolicitudVerificacionFovisEnum.RE_VERIFICACION
                .equals(solicitudVerificacionTemporalDTO.getSolicitudVerificacionFovisModeloDTO().getTipoVerificacion())) {
            actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud(),
                    EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_AL_BACK);
            solicitudVerificacionTemporalDTO.getDatosPostulacionFovis()
                    .setEstadoSolicitud(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_AL_BACK);
        }

        actualizarEstadoSolicitudVerificacion(solicitudVerificacionTemporalDTO.getIdSolicitud(),
                EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_POR_GESTIONAR);
        solicitudVerificacionTemporalDTO.getSolicitudVerificacionFovisModeloDTO()
                .setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.HALLAZGOS_POR_GESTIONAR);

        logger.debug("Finaliza devolverSolicitudHallallazgos(SolicitudPostulacionModeloDTO, UserDTO)");
    }

    /**
     * Método que registra el resultado de la revisión por parte de control interno cuando no se han superado las validaciones,
     * rechazando la solicitud.
     *
     * @param solicitudVerificacionTemporalDTO Datos de la solicitud a registrar.
     * @param estado                           estado por el que se cambiará la solicitud de verificación
     */
    private void rechazarPostulacionControlInterno(SolicitudVerificacionFovisDTO solicitudVerificacionTemporalDTO, boolean inhabil) {
        logger.debug("Se inicia rechazarPostulacionControlInterno(SolicitudPostulacionFOVISDTO, EstadoSolicitudPostulacionEnum, UserDTO)");

        // Se actualiza el estado de solicitud de postulación
        actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
        actualizarEstadoSolicitudPostulacion(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);

        ConsultarSolicitudGestionCrucePorPostulacionTipoCruce consultarSolicitudCruce = new ConsultarSolicitudGestionCrucePorPostulacionTipoCruce(
                TipoCruceEnum.EXTERNO, solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getIdSolicitudPostulacion());
        consultarSolicitudCruce.execute();
        List<SolicitudGestionCruceDTO> listSolGestion = consultarSolicitudCruce.getResult();

        if (listSolGestion != null) {
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolGestion) {
                // Proviene de HU 321-036
                if (inhabil) {
                    if (EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())) {
                        solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCES_RATIFICADOS);
                    }
                }
                // Proviene de HU 321-038
                else {
                    // Si los cruces fueron subsanados o sin cruces reportados se cambia el estado de cruces hogar a CRUCES_SUBSANADOS
                    if (EstadoCruceHogarEnum.CRUCE_SUBSANADO_PENDIENTE_VERIFICACION.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())
                            || EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())) {
                        solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCES_SUBSANADOS);
                    }
                    // Si los cruces fueron ratificados se cambia el estado de cruces hogar a CRUCES_RATIFICADOS
                    else if (EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())) {
                        solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCES_RATIFICADOS);
                    }
                }
                // Se actualiza el estado de la solicitud de gestion cruce de acuerdo a la lógica de las HU 036 y 038
                actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
            }
        }
        // Se cambia el estado de hogar a rechazado
        cambiarEstadoHogar(solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getPostulacion().getIdPostulacion(),
                EstadoHogarEnum.RECHAZADO);
        // Se actualiza el estado de la solicitud de verificación
        actualizarEstadoSolicitudVerificacion(solicitudVerificacionTemporalDTO.getIdSolicitud(),
                EstadoSolicitudVerificacionFovisEnum.CERRADA);

        solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        solicitudVerificacionTemporalDTO.getDatosPostulacionFovis().getPostulacion().setEstadoHogar(EstadoHogarEnum.RECHAZADO);
        solicitudVerificacionTemporalDTO.getSolicitudVerificacionFovisModeloDTO()
                .setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.CERRADA);

        logger.debug("Finaliza rechazarPostulacionControlInterno(SolicitudPostulacionFOVISDTO, EstadoSolicitudPostulacionEnum, UserDTO)");
    }

    /**
     * Método que relaciona el documento adjuntado por control interno a la solicitud.
     *
     * @param IdSolicitud Identificador de la solicitud global.
     * @param idDocumento identificador del documento adjunto.
     */
    private void registrarDocumentoControlInterno(Long IdSolicitud, String idDocumento) {

        logger.debug("Inicia guardarDocumentoControlInterno(Long IdSolicitud, String idDocumento)");
        if (idDocumento != null) {
            DocumentoAdministracionEstadoSolicitudDTO docAdminiEstadoSolicitudDTO = new DocumentoAdministracionEstadoSolicitudDTO();
            docAdminiEstadoSolicitudDTO.setIdSolicitudGlobal(IdSolicitud);
            docAdminiEstadoSolicitudDTO.setIdentificadorDocumentoSoporteCambioEstado(idDocumento);
            docAdminiEstadoSolicitudDTO.setTipoDocumentoAdjunto(TipoDocumentoAdjuntoEnum.CONTROL_INTERNO_FOVIS);
            RegistrarDocumentoSolicitud guardarDocumentoSolicitud = new RegistrarDocumentoSolicitud(docAdminiEstadoSolicitudDTO);
            guardarDocumentoSolicitud.execute();
        }
        logger.debug("Finaliza guardarDocumentoControlInterno(Long IdSolicitud, String idDocumento)");
    }

    /**
     * Método que registra el escalamiento al analista registrado en las propiedades del objeto <code>datosEscalamientoAnalistas</code> y
     * coloca el destinatario en el map según aplique.
     */
    private Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> registrarEscalamientoAnalistas(Map<String, Object> parametros,
                                                                                                            EscalamientoSolicitudDTO hogar, EscalamientoSolicitudDTO tecnico, EscalamientoSolicitudDTO juridico, Long idSolicitud,
                                                                                                            OrigenEscalamientoEnum origen, UserDTO userDTO) {
        logger.debug("Inicia método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
        Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = new HashMap<>();
        if (hogar != null && hogar.getDestinatario() != null) {
            hogar.setOrigen(origen);
            hogar = escalarSolicitud(idSolicitud, hogar, userDTO);
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR, hogar);
            parametros.put(ANALISTA_HOGAR, hogar.getDestinatario());
        }
        if (juridico != null && juridico.getDestinatario() != null) {
            juridico.setOrigen(origen);
            juridico = escalarSolicitud(idSolicitud, juridico, userDTO);
            parametros.put(ANALISTA_JURIDICO, juridico.getDestinatario());
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO, juridico);
        }
        if (tecnico != null && tecnico.getDestinatario() != null) {
            tecnico.setOrigen(origen);
            tecnico = escalarSolicitud(idSolicitud, tecnico, userDTO);
            parametros.put(ANALISTA_TECNICO, tecnico.getDestinatario());
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO, tecnico);
        }
        logger.debug("Finaliza método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
        return escalamientos;
    }

    /**
     * Consulta un archivo deacuerdo con el id del ECM
     *
     * @param archivoId
     * @return
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia obtenerArchivo(String)");
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }

    /**
     * Obtiene Date a partir de mes y dia.
     *
     * @param mesStr
     * @param diaMesStr
     * @return Date.
     */
    private Date obtenerFechaMesDia(String mesStr, String diaMesStr) {
        if (mesStr != null && !mesStr.isEmpty() && diaMesStr != null && !diaMesStr.isEmpty()) {
            Integer mes = Integer.valueOf(mesStr);
            Integer dia = Integer.valueOf(diaMesStr);
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.set(Calendar.MONTH, mes - 1);
            fechaInicio.set(Calendar.DATE, dia);
            return fechaInicio.getTime();
        }
        return null;
    }

    /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple
     *
     * @param consolaEstadoCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
        registroConsola.execute();
    }

    /**
     * Método encargado de realizar el llamado al cliente que actualiza el
     * estado del cargue
     *
     * @param idCargue,                      id del cargue a actualizar
     * @param consolaEstadoCargueProcesoDTO, datos que seran actualizados
     */
    private void actualizarCargueConsolaEstado(Long idCargue, ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        ActualizarCargueConsolaEstado actualizacion = new ActualizarCargueConsolaEstado(idCargue, consolaEstadoCargueProcesoDTO);
        actualizacion.execute();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#validarArchivoCruce(com.asopagos.dto.CargueArchivoCruceFovisDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoDTO validarArchivoCruce(CargueArchivoCruceFovisDTO cargue, UserDTO userDTO) {
        logger.debug("Inicia servicio validarArchivoCruce(CargueArchivoCruceFovisDTO): cargue.getCodigoIdentificacionECM()" + cargue.getCodigoIdentificacionECM());
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoCruce verificarArchivo = new VerificarEstructuraArchivoCruce(archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();

        // Se identifica si el resultado es un excepcion
        if (resultDTO.getResultadoCruceFOVISDTO().getExcepcion() == null || !resultDTO.getResultadoCruceFOVISDTO().getExcepcion()) {
            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            String codigoCaja;
            try {
                codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
            } catch (Exception e) {
                codigoCaja = null;
            }
            consolaEstadoCargue.setCcf(codigoCaja);
            EstadoCargueMasivoEnum estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
            if (resultDTO.getEstadoCargue().equals(EstadoCargaMultipleEnum.CANCELADO)) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
            }
            consolaEstadoCargue.setEstado(estadoProcesoMasivo);
            consolaEstadoCargue.setFileLoaded_id(resultDTO.getFileDefinitionId());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_CRUCE_FOVIS);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            consolaEstadoCargue.setCargue_id(resultDTO.getIdCargue());
            consolaEstadoCargue.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            consolaEstadoCargue.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            consolaEstadoCargue.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            consolaEstadoCargue.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            registrarConsolaEstado(consolaEstadoCargue);

            // Registrar estado procesado
            resultDTO.setEstadoCargue(EstadoCargaMultipleEnum.CERRADO);

            // Se actualiza el estado en la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setCargue_id(resultDTO.getIdCargue());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_CRUCE_FOVIS);
            actualizarCargueConsolaEstado(resultDTO.getIdCargue(), conCargueMasivo);
        }
        logger.debug("Finaliza servicio validarArchivoCruce(CargueArchivoCruceFovisDTO)");
        return resultDTO;
    }

    /**
     * Consulta el cargue de archivo cruce por su identificador
     *
     * @param idCargue Identificador cargue
     * @return Informacion archivo
     */
    private CargueArchivoCruceFovisDTO consultarCargueCruce(Long idCargue) {
        logger.debug("Inicia servicio consultarCargueCruce(Long)");
        ConsultarCargueArchivoCruce consultarCargueArchivoCruce = new ConsultarCargueArchivoCruce(idCargue);
        consultarCargueArchivoCruce.execute();
        logger.debug("Finaliza servicio consultarCargueCruce(Long)");
        return consultarCargueArchivoCruce.getResult();
    }

    /**
     * Consulta el contenido del archivo cruce por su identificador
     *
     * @param idCargue Identificador cargue
     * @return Informacion archivo
     */
    private InformacionCruceFovisDTO consultarContenidoArchivoCruceFovis(Long idCargue) {
        logger.debug("Inicia servicio consultarContenidoArchivoCruceFovis(Long)");
        ConsultarContenidoArchivoCargueFovis consultarCargueArchivoCruce = new ConsultarContenidoArchivoCargueFovis(idCargue);
        consultarCargueArchivoCruce.execute();
        logger.debug("Finaliza servicio consultarContenidoArchivoCruceFovis(Long)");
        return consultarCargueArchivoCruce.getResult();
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

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#consolidarCruce(java.lang.Long)
     */
    @Override
    public List<CruceDetalleDTO> consolidarCruce(Long idCargue) {
        logger.debug(Interpolator.interpolate("Inicia consolidarCruce(Long) - idCargue: {0}", idCargue));
        try {

            List<Object[]> obj = consultarInformacionArchivoCruces(idCargue);
            List<CruceDetalleDTO> cruceDetalleDTOs = new ArrayList<>();


            for (Object[] novedadObj : obj) {
                CruceDetalleDTO cruceDetalle = new CruceDetalleDTO();
                cruceDetalle.setIdCruceDetalle(null);
                cruceDetalle.setCausalCruce(CausalCruceEnum.valueOf((String) novedadObj[5]));
                cruceDetalle.setNitEntidad((String) novedadObj[6]);
                cruceDetalle.setNombreEntidad((String) novedadObj[7]);
                cruceDetalle.setNumeroIdentificacion((String) novedadObj[8]);
                cruceDetalle.setApellidos((String) novedadObj[9]);
                cruceDetalle.setNombres((String) novedadObj[10]);
                cruceDetalle.setCedulaCatastral((String) novedadObj[11]);
                cruceDetalle.setTipoDocumento((String) novedadObj[19]);
                cruceDetalle.setTipo(TipoInformacionCruceEnum.valueOf((String) novedadObj[20]));
                cruceDetalle.setFechaActualizacionMinisterio(convertirFecha((Long) novedadObj[16]));
                cruceDetalle.setFechaCorteEntidad(convertirFecha((Long) novedadObj[17]));
                cruceDetalle.setDireccionInmueble((String) novedadObj[12]);
                cruceDetalle.setMatriculaInmobiliaria((String) novedadObj[13]);
                cruceDetalle.setDepartamento((String) novedadObj[14]);
                cruceDetalle.setMunicipio((String) novedadObj[15]);
                cruceDetalle.setApellidosNombres((String) novedadObj[18]);
                CruceDTO cruce = new CruceDTO();
                cruce.setIdCargueArchivoCruceFovis(Long.parseLong(String.valueOf(novedadObj[0])));
                cruce.setNumeroPostulacion((String) novedadObj[1]);
                cruce.setEstadoCruce(EstadoCruceEnum.valueOf((String) novedadObj[3]));
                cruce.setFechaRegistro(convertirFecha((Long) novedadObj[4]));
                PersonaDTO persona = new PersonaDTO();
                persona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf((String) novedadObj[21]));
                persona.setNumeroIdentificacion((String) novedadObj[22]);
                persona.setPrimerNombre((String) novedadObj[23]);
                persona.setSegundoNombre((String) novedadObj[24]);
                persona.setPrimerApellido((String) novedadObj[25]);
                persona.setSegundoApellido((String) novedadObj[26]);
                persona.setNombreCompleto((String) novedadObj[27]);
                persona.setIdPersona(Long.parseLong(String.valueOf(novedadObj[28])));
                persona.setRazonSocial((String) novedadObj[29]);
                persona.setAutorizacionEnvioEmail((Boolean) novedadObj[30]);
                persona.setCreadoPorPila((Boolean) novedadObj[31]);
                UbicacionDTO ubicacionDTO = new UbicacionDTO();
                ubicacionDTO.setIdUbicacion(Long.parseLong(String.valueOf(novedadObj[32])));
                ubicacionDTO.setIdMunicipio(novedadObj[33] != null ? Short.parseShort(String.valueOf(novedadObj[33])) : 0);
                ubicacionDTO.setDireccion((String) novedadObj[34]);
                ubicacionDTO.setIndicativoTelefonoFijo((String) novedadObj[35]);
                ubicacionDTO.setTelefonoFijo((String) novedadObj[36]);
                ubicacionDTO.setTelefonoCelular((String) novedadObj[37]);
                ubicacionDTO.setCorreoElectronico((String) novedadObj[38]);
                persona.setUbicacionDTO(ubicacionDTO);
                cruce.setPersona(persona);
                cruceDetalle.setCruce(cruce);
                cruceDetalleDTOs.add(cruceDetalle);
            }


            return cruceDetalleDTOs;


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ocurrió un error inesperado en el servicio ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Convierte un long a fecha
     *
     * @param Data Data
     * @return Fecha convertida
     */
    private Date convertirFecha(Long data) {
        Date fecha = new Date();
        Long aLong = data;
        String s = String.valueOf(aLong);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = dateFormat.parse(s);
        } catch (ParseException e) {
            logger.info("Casteo fecha de anio actual");
        }
        return fecha;
    }

    /**
     * Consulta la informacion del archivo de cruces
     *
     * @param idCargue Identificador cargue
     * @return Informacion archivo
     */
    private List<Object[]> consultarInformacionArchivoCruces(Long idCargue) {
        logger.debug("Inicia servicio consultarInformacionCruce(Long)");
        ConsultarInformacionArchivoCruces consultarInformacionCruce = new ConsultarInformacionArchivoCruces(idCargue);
        consultarInformacionCruce.execute();
        logger.debug("Finaliza servicio consultarInformacionCruce(Long)");
        return consultarInformacionCruce.getResult();
    }

    /**
     * Obtiene los numeros de cedula de las enviadas en el cargue
     *
     * @param listCedulasCargue Lista cedulas cargadas
     * @return Lista numeros de cedula
     */
    private List<String> getListCedulasCruce(List<CargueArchivoCruceFovisCedulaDTO> listCedulasCargue) {
        List<String> listCedulas = new ArrayList<>();
        if (listCedulasCargue != null && !listCedulasCargue.isEmpty()) {
            for (CargueArchivoCruceFovisCedulaDTO cargueArchivoCruceFovisCedulaDTO : listCedulasCargue) {
                if (cargueArchivoCruceFovisCedulaDTO.getNroCedula() != null
                        && !cargueArchivoCruceFovisCedulaDTO.getNroCedula().isEmpty()
                        && !listCedulas.contains(cargueArchivoCruceFovisCedulaDTO.getNroCedula())) {
                    listCedulas.add(cargueArchivoCruceFovisCedulaDTO.getNroCedula());
                }
            }
        }
        return listCedulas;
    }

    /**
     * Consulta las personas asocidas a las cedulas
     *
     * @param cedulas Lista numeros de cedula
     * @return Lista personas
     */
    private List<PersonaModeloDTO> consultarListaPersonaPorListaCedulas(List<String> cedulas) {
        ConsultarDatosPersonaListNumeroIdentificacion service = new ConsultarDatosPersonaListNumeroIdentificacion(cedulas,
                TipoIdentificacionEnum.CEDULA_CIUDADANIA);
        service.execute();
        return service.getResult();
    }

    /**
     * Obtiene la persona por numero de cedula de la lista enviada
     *
     * @param personas  Lista personas
     * @param nroCedula Numero de cedula
     * @return Informacion persona
     */
    private PersonaModeloDTO getPersonaLista(List<PersonaModeloDTO> personas, String nroCedula) {
        if (personas != null && !personas.isEmpty()) {
            for (PersonaModeloDTO personaModeloDTO : personas) {
                if (personaModeloDTO.getNumeroIdentificacion().equals(nroCedula)) {
                    return personaModeloDTO;
                }
            }
        }
        return null;
    }

    /**
     * Verifica si la cedula enviada contiene cruce en alguna de las paginas del archivo cruce
     *
     * @param nroCedula        Cedula a revisar
     * @param infoArchivoCruce Informacion archivo cruce
     * @return Lista de cruces encontrados
     */
    private List<CruceDetalleDTO> generarCruceInformacionCedula(Long idCargue, String nroCedula,
                                                                InformacionCruceFovisDTO infoArchivoCruce, String numeroPostulacion, PersonaModeloDTO persona) {
        // Lista de cruce a registar
        List<CruceDetalleDTO> listCrucesCedula = new ArrayList<>();
        if (persona == null || numeroPostulacion == null) {
            return listCrucesCedula;
        }
        PersonaDTO peersonaDTO = new PersonaDTO(persona.convertToPersonaEntity());
        // Se consultan los cruces existentes por el numero de documento y se
        // agrupan por tipo de informacion
        List<CruceDetalleDTO> listCrucesExistentes = consultarRegistroCruceExistente(nroCedula);
        Map<TipoInformacionCruceEnum, List<CruceDetalleDTO>> listaCrucesTipoInformacion = new HashMap<TipoInformacionCruceEnum, List<CruceDetalleDTO>>();
        for (CruceDetalleDTO cruceDetalleExistente : listCrucesExistentes) {
            if (listaCrucesTipoInformacion.containsKey(cruceDetalleExistente.getTipo())) {
                listaCrucesTipoInformacion.get(cruceDetalleExistente.getTipo()).add(cruceDetalleExistente);
            } else {
                List<CruceDetalleDTO> listCruces = new ArrayList<CruceDetalleDTO>();
                listCruces.add(cruceDetalleExistente);
                listaCrucesTipoInformacion.put(cruceDetalleExistente.getTipo(), listCruces);
            }
        }
        // Generar cruces detalle por hoja
        for (TipoInformacionCruceEnum tipoInformacion : TipoInformacionCruceEnum.values()) {
            List<CruceDetalleDTO> crucesDetalle = generarCruceHoja(nroCedula, tipoInformacion, infoArchivoCruce,
                    idCargue, numeroPostulacion, peersonaDTO, listaCrucesTipoInformacion);
            // Verificar si se genero cruce
            if (crucesDetalle != null && !crucesDetalle.isEmpty()) {
                // Lista de cruce a registar
                listCrucesCedula.addAll(crucesDetalle);
            }
        }
        return listCrucesCedula;
    }

    /**
     * Genera el cruce por cada hoja de validacion si se determina que se debe crear
     *
     * @param nroCedula        Numero de cedula a validar
     * @param tipo             Tipo de informacion de la hoja
     * @param infoArchivoCruce Informacion del archivo cruce
     * @return
     */
    private List<CruceDetalleDTO> generarCruceHoja(String nroCedula, TipoInformacionCruceEnum tipo,
                                                   InformacionCruceFovisDTO infoArchivoCruce, Long idCargue, String numeroPostulacion, PersonaDTO persona,
                                                   Map<TipoInformacionCruceEnum, List<CruceDetalleDTO>> listaCrucesTipoInformacion) {
        // Lista de cruces por hoja
        List<CruceDetalleDTO> listCrucesPorHoja = new ArrayList<>();
        // Lista de informacion de la hoja
        List<CargueArchivoCruceFovisHojaDTO> listInfoHoja = new ArrayList<>();
        // Contiene el nit de la entidad para la fecha
        String nitEntidadFecha = null;
        // Se obtiene la lista de cruces por hoja
        // Se consulta si la cedula existe un cruce
        List<CruceDetalleDTO> listCrucesExistentes = new ArrayList<CruceDetalleDTO>();
        if (listaCrucesTipoInformacion != null && listaCrucesTipoInformacion.containsKey(tipo)) {
            listCrucesExistentes = listaCrucesTipoInformacion.get(tipo);
        }
        // Se verifica por cada hoja
        switch (tipo) {
            case AFILIADOS:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getAfiliados());

                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.AFILIACION_CAJA, nitEntidadFecha, Boolean.TRUE, listCrucesExistentes);
                break;
            case BENEFICIARIOS:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getBeneficiarios());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.BENEFICIO_SUBSIDIO_RECIBIDO, nitEntidadFecha, Boolean.TRUE, listCrucesExistentes);
                break;
            case CATASTRO_ANT:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getCatAnt());
                nitEntidadFecha = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_CATASTRO_ANTIOQUIA);
                nitEntidadFecha = nitEntidadFecha != null ? nitEntidadFecha.replaceAll("-", "") : null;
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD_ANT, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case CATASTRO_BOG:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getCatBog());
                nitEntidadFecha = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_CATASTRO_BOGOTA);
                nitEntidadFecha = nitEntidadFecha != null ? nitEntidadFecha.replaceAll("-", "") : null;
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD_BOG, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case CATASTRO_CALI:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getCatCali());
                nitEntidadFecha = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_CATASTRO_CALI);
                nitEntidadFecha = nitEntidadFecha != null ? nitEntidadFecha.replaceAll("-", "") : null;
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD_CALI, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case CATASTRO_MED:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getCatMed());
                nitEntidadFecha = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_CATASTRO_MEDELLIN);
                nitEntidadFecha = nitEntidadFecha != null ? nitEntidadFecha.replaceAll("-", "") : null;
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD_MED, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case IGAC:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getIgac());
                nitEntidadFecha = (String) CacheManager.getParametro(ParametrosSistemaConstants.NIT_IGAC);
                nitEntidadFecha = nitEntidadFecha != null ? nitEntidadFecha.replaceAll("-", "") : null;
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD_IGAC, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case BENEFICIARIO_AR:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getBeneficiariosArriendo());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.BENEFICIO_SUBSIDIO_RECIBIDO_AR, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case CATASTROS:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getCatastros());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REGISTRO_PROPIEDAD, nitEntidadFecha, Boolean.TRUE, listCrucesExistentes);
                break;
            case NUEVO_HOGAR:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getNuevoHogar());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.CONFORMO_NUEVO_HOGAR, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case SISBEN:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getSisben());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REPORTE_SISBEN, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case UNIDOS:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getUnidos());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REPORTE_UNIDOS, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            case REUNIDOS:
                listInfoHoja = new ArrayList<>();
                listInfoHoja.addAll(infoArchivoCruce.getReunidos());
                // Se obtiene la lista de cruces por hoja
                listCrucesPorHoja = obtenerCrucesPorCedula(infoArchivoCruce, listInfoHoja, nroCedula, tipo, idCargue, numeroPostulacion,
                        persona, CausalCruceEnum.REPORTE_REUNIDOS, nitEntidadFecha, Boolean.FALSE, listCrucesExistentes);
                break;
            default:
                break;
        }
        return listCrucesPorHoja;
    }

    /**
     * Obtiene el NIT sin dígito de verificación.
     *
     * @param nit
     * @return
     */
    private String obtenerNitSinDigitoVerificacion(String nit) {
        String nitSinDigito = "";
        if (nit != null && nit.contains("-")) {
            String[] nitList = nit.split("-");
            nitSinDigito = nitList[0] + nitList[1];
        } else {
            nitSinDigito = nit;
        }
        return nitSinDigito;
    }

    /**
     * Realiza la obtención de los cruces reportados para la cedula por hoja, recorriendo la hoja genera los cruces a que haya lugar
     *
     * @param infoArchivoCruce  Informacion del archivo cruce
     * @param listInfoHoja      Lista con la informacion de la hoja
     * @param nroCedula         Numero de cedula a validar
     * @param tipo              Tipo de informacion de la hoja
     * @param idCargue          Identificador cargue procesado
     * @param numeroPostulacion Numero de postulacion asociado
     * @param persona           Información persona en el sistema
     * @param causalCruce       Causal que origina el cruce
     * @param nitEntidadFecha   Nit entidad para el calculo de las fechas
     * @return Lista de cruces detalles por hoja
     */
    private List<CruceDetalleDTO> obtenerCrucesPorCedula(InformacionCruceFovisDTO infoArchivoCruce,
                                                         List<CargueArchivoCruceFovisHojaDTO> listInfoHoja, String nroCedula, TipoInformacionCruceEnum tipo, Long idCargue,
                                                         String numeroPostulacion, PersonaDTO persona, CausalCruceEnum causalCruce, String nitEntidadFecha, Boolean fechaCruce,
                                                         List<CruceDetalleDTO> listCrucesExistentes) {
        List<CruceDetalleDTO> cruces = new ArrayList<>();

        for (CargueArchivoCruceFovisHojaDTO infoHoja : listInfoHoja) {
            // Generar cruce postulacion
            CruceDTO cruceDTO = new CruceDTO();
            cruceDTO.setIdCargueArchivoCruceFovis(idCargue);
            cruceDTO.setNumeroPostulacion(numeroPostulacion);
            cruceDTO.setPersona(persona);
            cruceDTO.setFechaRegistro(Calendar.getInstance().getTime());
            CruceDetalleDTO cruceDetalleDTO = validarInformacionHoja(infoHoja, nroCedula, tipo, cruceDTO, listCrucesExistentes);
            if (cruceDetalleDTO != null) {
                cruceDetalleDTO.setCausalCruce(causalCruce);
                cruceDetalleDTO.setTipo(tipo);
                // Se obtienen las fechas de actualizacion y Fecha corte
                if (fechaCruce) {
                    nitEntidadFecha = cruceDetalleDTO.getNitEntidad();
                }
                if (nitEntidadFecha != null && !nitEntidadFecha.equals("")) {
                    setFechasCruce(nitEntidadFecha, cruceDetalleDTO, infoArchivoCruce.getFechaCorte());
                }
                // Asociar estado y postulacion cruce
                cruceDetalleDTO.setCruce(cruceDTO);
                cruces.add(cruceDetalleDTO);
            }
        }
        return cruces;
    }

    /**
     * Valida la informacion de la hoja respecto a cruces existentes
     *
     * @param infoHoja  Informacion fila de la hoja
     * @param nroCedula Numero de cedula a validar
     * @param tipo      Tipo de informacion de la hoja
     * @return Cruce detalle
     */
    private CruceDetalleDTO validarInformacionHoja(CargueArchivoCruceFovisHojaDTO infoHoja, String nroCedula, TipoInformacionCruceEnum tipo,
                                                   CruceDTO cruceDTO, List<CruceDetalleDTO> listCrucesExistentes) {

        // Contiene el cruce nuevo si es que se genera
        CruceDetalleDTO cruceDetalleNuevo = null;
        // Se consulta el nit de la caja
        String nitCaja = null;
        if (tipo.equals(TipoInformacionCruceEnum.AFILIADOS) || tipo.equals(TipoInformacionCruceEnum.BENEFICIARIOS)) {
            try {
                nitCaja = CacheManager.getParametro(ParametrosSistemaConstants.NUMERO_ID_CCF).toString();
            } catch (Exception e) {
                nitCaja = null;
            }
        }
        // Se verifica la existencia de un cruce asociado a la cedula
        if (infoHoja.getIdentificacion() != null && infoHoja.getIdentificacion().equals(nroCedula)) {
            if (nitCaja != null) {
                nitCaja = this.obtenerNitSinDigitoVerificacion(nitCaja);
            }
            // Se verifica si la CCf es la misma que registra el cargue
            if ((tipo.equals(TipoInformacionCruceEnum.AFILIADOS) || tipo.equals(TipoInformacionCruceEnum.BENEFICIARIOS))
                    && infoHoja.getNitEntidad().equals(nitCaja)) {
                return cruceDetalleNuevo;
            }
            Boolean previoReporte = false;
            // Se verifica si el cruce fue previamente reportado
            for (CruceDetalleDTO cruceDetalleExistente : listCrucesExistentes) {
                if (!existeDiferenciaInfoCruceHoja(cruceDetalleExistente, infoHoja, tipo)) {
                    previoReporte = true;
                    break;
                }
            }
            // Se verifica si existe un cruce asociado a la cedula
            if (tipo.equals(TipoInformacionCruceEnum.SISBEN) || tipo.equals(TipoInformacionCruceEnum.UNIDOS)
                    || tipo.equals(TipoInformacionCruceEnum.REUNIDOS)) {
                cruceDetalleNuevo = new CruceDetalleDTO(infoHoja);
                cruceDTO.setEstadoCruce(EstadoCruceEnum.NUEVO);
            } else if (previoReporte) {
                cruceDetalleNuevo = new CruceDetalleDTO(infoHoja);
                cruceDTO.setEstadoCruce(EstadoCruceEnum.PREVIAMENTE_REPORTADO);
            } else {
                cruceDetalleNuevo = new CruceDetalleDTO(infoHoja);
                cruceDTO.setEstadoCruce(EstadoCruceEnum.NUEVO);
            }
        }
        return cruceDetalleNuevo;
    }

    /**
     * Setea la fecha de actualizacion ministerio y la fecha de corte para el cruce
     *
     * @param nitEntidadFecha   Nit a validar contra la fecha
     * @param cruceDetalleNuevo Cruce creado
     * @param infoFechaCruce    Informacion hoja fechas cruce
     */
    private static void setFechasCruce(String nitEntidadFecha, CruceDetalleDTO cruceDetalleNuevo,
                                       List<CargueArchivoCruceFovisFechasCorteDTO> infoFechaCruce) {
        // Fecha Actualizacion Ministerio
        Date fechaActualizacion = null;
        // Fecha corte
        Date fechaCorte = null;
        // Se itera la hoja fecha de corte para setear las fechas del cruce
        for (CargueArchivoCruceFovisFechasCorteDTO infoHoja : infoFechaCruce) {
            if (nitEntidadFecha != null && infoHoja.getNitEntidad().equals(nitEntidadFecha)) {
                // Se obtiene la fecha de actualizacion ministerio
                fechaActualizacion = infoHoja.getFechaActualizacion();
                // Se obtiene la fecha de corte
                fechaCorte = infoHoja.getFechaCorte();
                break;
            }
        }
        cruceDetalleNuevo.setFechaActualizacionMinisterio(fechaActualizacion);
        cruceDetalleNuevo.setFechaCorteEntidad(fechaCorte);
    }

    /**
     * Verifica si existe einformacion entre el cruce existente y la nueva informacion de la hoja
     *
     * @param cruceExistente Informacion cruce existente
     * @param infoHoja       Informacion hoja validada
     * @param tipo           Tipo informacion validada
     * @return True si existe diferencia, false en caso contrario
     */
    private static Boolean existeDiferenciaInfoCruceHoja(CruceDetalleDTO cruceExistente, CargueArchivoCruceFovisHojaDTO infoHoja,
                                                         TipoInformacionCruceEnum tipo) {
        boolean result = false;
        switch (tipo) {
            case AFILIADOS:
            case BENEFICIARIOS:
                result = esValorDiferente(cruceExistente.getNitEntidad(), infoHoja.getNitEntidad())
                        || esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidos(), infoHoja.getApellidos())
                        || esValorDiferente(cruceExistente.getNombres(), infoHoja.getNombres());
                break;
            case CATASTRO_ANT:
                result = esValorDiferente(cruceExistente.getNitEntidad(), infoHoja.getNitEntidad())
                        || esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidos(), infoHoja.getApellidos())
                        || esValorDiferente(cruceExistente.getNombres(), infoHoja.getNombres())
                        || esValorDiferente(cruceExistente.getCedulaCatastral(), infoHoja.getCedulaCatastral())
                        || esValorDiferente(cruceExistente.getDireccionInmueble(), infoHoja.getDireccionInmueble())
                        || esValorDiferente(cruceExistente.getMatriculaInmobiliaria(), infoHoja.getMatriculaInmobiliaria())
                        || esValorDiferente(cruceExistente.getDepartamento(), infoHoja.getDepartamento())
                        || esValorDiferente(cruceExistente.getMunicipio(), infoHoja.getMunicipio());
                break;
            case CATASTRO_BOG:
                result = esValorDiferente(cruceExistente.getNitEntidad(), infoHoja.getNitEntidad())
                        || esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidosNombres(), infoHoja.getApellidosNombres())
                        || esValorDiferente(cruceExistente.getCedulaCatastral(), infoHoja.getCedulaCatastral())
                        || esValorDiferente(cruceExistente.getDireccionInmueble(), infoHoja.getDireccionInmueble())
                        || esValorDiferente(cruceExistente.getMatriculaInmobiliaria(), infoHoja.getMatriculaInmobiliaria())
                        || esValorDiferente(cruceExistente.getDepartamento(), infoHoja.getDepartamento())
                        || esValorDiferente(cruceExistente.getMunicipio(), infoHoja.getMunicipio());
                break;
            case CATASTRO_CALI:
                result = esValorDiferente(cruceExistente.getNitEntidad(), infoHoja.getNitEntidad())
                        || esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidosNombres(), infoHoja.getApellidosNombres())
                        || esValorDiferente(cruceExistente.getMatriculaInmobiliaria(), infoHoja.getMatriculaInmobiliaria())
                        || esValorDiferente(cruceExistente.getDepartamento(), infoHoja.getDepartamento())
                        || esValorDiferente(cruceExistente.getMunicipio(), infoHoja.getMunicipio());
                break;
            case CATASTRO_MED:
                result = esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidosNombres(), infoHoja.getApellidosNombres())
                        || esValorDiferente(cruceExistente.getCedulaCatastral(), infoHoja.getCedulaCatastral())
                        || esValorDiferente(cruceExistente.getDireccionInmueble(), infoHoja.getDireccionInmueble())
                        || esValorDiferente(cruceExistente.getMatriculaInmobiliaria(), infoHoja.getMatriculaInmobiliaria())
                        || esValorDiferente(cruceExistente.getDepartamento(), infoHoja.getDepartamento())
                        || esValorDiferente(cruceExistente.getMunicipio(), infoHoja.getMunicipio());
                break;
            case IGAC:
                result = esValorDiferente(cruceExistente.getNitEntidad(), infoHoja.getNitEntidad())
                        || esValorDiferente(cruceExistente.getNombreEntidad(), infoHoja.getNombreEntidad())
                        || esValorDiferente(cruceExistente.getApellidosNombres(), infoHoja.getApellidosNombres())
                        || esValorDiferente(cruceExistente.getCedulaCatastral(), infoHoja.getCedulaCatastral())
                        || esValorDiferente(cruceExistente.getDireccionInmueble(), infoHoja.getDireccionInmueble())
                        || esValorDiferente(cruceExistente.getMatriculaInmobiliaria(), infoHoja.getMatriculaInmobiliaria())
                        || esValorDiferente(cruceExistente.getDepartamento(), infoHoja.getDepartamento())
                        || esValorDiferente(cruceExistente.getMunicipio(), infoHoja.getMunicipio());
                break;
            case NUEVO_HOGAR:
                result = esValorDiferente(cruceExistente.getApellidosNombres(), infoHoja.getApellidosNombres())
                        || esValorDiferente(cruceExistente.getEntidadOtorgante(), infoHoja.getEntidadOtorgante())
                        || esValorDiferente(cruceExistente.getCajaCompensacion(), infoHoja.getCajaCompensacion())
                        || esValorDiferente(cruceExistente.getAsignadoPosterior(), infoHoja.getAsignadoPosteriorReporte())
                        || esValorDiferente(cruceExistente.getFechaSolicitud(), infoHoja.getFechaSolicitud());
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Determina si el valor A y el valor B son diferentes
     *
     * @param valorA Valor a revisar
     * @param valorB Valor a revisar
     * @return True si los valores son diferentes false en caso contrario
     */
    private static Boolean esValorDiferente(Object valorA, Object valorB) {
        boolean result = false;
        if (valorA != null && valorB != null) {
            result = !valorA.equals(valorB);
        } else if (valorA == null && valorB != null) {
            result = true;
        } else if (valorA != null && valorB == null) {
            result = true;
        }
        return result;
    }

    /**
     * Consulta el cruce en estado NUEVO existente para el tipo de informacion y la cedula enviada
     *
     * @param nroCedula Nro cedula a consultar
     * @param tipo      Tipo de informacion de cruce a buscar
     * @return Cruce detallado existente
     */
    private List<CruceDetalleDTO> consultarRegistroCruceExistente(String nroCedula, TipoInformacionCruceEnum tipo) {
        // Se consulta el cruce existente en estado NUEVO para el documento y el tipo informacion enviados
        ConsultarCruceFiltro consultarCruceFiltro = new ConsultarCruceFiltro(tipo, nroCedula);
        consultarCruceFiltro.execute();
        return consultarCruceFiltro.getResult();
    }


    /**
     * Consulta la informacion del numero de postulacion asociado al numero de cedula evaluado
     *
     * @param nroCedula Numero de cedula a buscar
     * @return Numero de postulacion registrado
     */
    private String consultarNumeroPostulacion(String nroCedula) {
        ConsultarNumeroPostulacionPersona consultarNumeroPostulacionPersona = new ConsultarNumeroPostulacionPersona(nroCedula);
        consultarNumeroPostulacionPersona.execute();
        return consultarNumeroPostulacionPersona.getResult();
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
     * Consulta la solicitud de postulacion asociada a una cedula, se busca por varias cedulas
     *
     * @param listNroCedulas Lusta de cedulas a buscar
     * @return Map con solicitud postulacion por cedula
     */
    private Map<String, SolicitudPostulacionModeloDTO> consultarSolicitudPostulacionCedulas(List<String> listNroCedulas) {
        logger.debug("consume el cliente consultarSolicitudPostulacionCedulas");
        ConsultarSolicitudPostulacionPorNumeroCedula consultarSolicitudPostulacionPorNumeroCedula = new ConsultarSolicitudPostulacionPorNumeroCedula(
                listNroCedulas);
        consultarSolicitudPostulacionPorNumeroCedula.execute();
        return consultarSolicitudPostulacionPorNumeroCedula.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#aceptarResultadoCruce(com.asopagos.fovis.composite.dto.
     * AsignaResultadoCruceDTO)
     */
    @Override
    public void aceptarResultadoCruce(AsignaResultadoCruceDTO asignaResultadosCruceDTO, UserDTO userDTO) {
        logger.debug("Inicia servicio aceptarResultadoCruce(AsignaResultadoCruceDTO)");
        // Se consulta la informacion del archivo por el identificador enviado
        InformacionCruceFovisDTO informacionCruceFovisDTO = consultarContenidoArchivoCruceFovis(asignaResultadosCruceDTO.getIdCargue());
        List<CargueArchivoCruceFovisCedulaDTO> listCedulas = informacionCruceFovisDTO.getCedulas();
        // Se consultan los cruces hechos para el cargue
        List<CruceDetalleDTO> listCruces = asignaResultadosCruceDTO.getListCruces();
        // Guarda los cruces generados
        // Guarda las solicitudes de gestioInteger cantidad,TipoEtiquetaEnum tipoEtiqueta
        List<SolicitudGestionCruceDTO> listSolicituGestionCruce =
                getSolictudesCruce(asignaResultadosCruceDTO.getIdCargue(), listCedulas, listCruces);
        // Se asocia el numero de radicado

        if (listSolicituGestionCruce != null && listSolicituGestionCruce.size() > 0) {
            dispersarTareas(listSolicituGestionCruce, userDTO, asignaResultadosCruceDTO.getListUsuarios());
        } else {

        }
        logger.debug("Finaliza servicio aceptarResultadoCruce(AsignaResultadoCruceDTO)");
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

    public void dispersarTareas(List<SolicitudGestionCruceDTO> postulaciones,
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
            //List<List<AsignacionTurnosDTO>> turnosAdd  = new ArrayList<>();

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

                callables.add(() -> asignarTareaGestionCruce2(postulacionesXUsuario, userDTO, user, tope));
            }
            List<Future<List<AsignacionTurnosDTO>>> result = new ArrayList<>();
            result = managedExecutorService.invokeAll(callables);
            String tiempoGestionCruces = (String) CacheManager.getParametro(ParametrosSistemaConstants.BPM_PFP_TIEMPO_PEND_GESTION_CRUCES);
            for (Future<List<AsignacionTurnosDTO>> future : result) {
                List<AsignacionTurnosDTO> outDTO = future.get();
                for (int i = 0; i < outDTO.size(); i++) {
                    AsignacionTurnosDTO asignacionTurnosDTO = outDTO.get(i);
                    SolicitudGestionCruceDTO solicitud = this.consultarSolicitudGestionCrucePorSolicitudGlobal(asignacionTurnosDTO.getIdSolicitud());
                    Map<String, Object> parametrosProceso = new HashMap<String, Object>();
                    parametrosProceso.put(ID_SOLICITUD, solicitud.getIdSolicitud());
                    parametrosProceso.put(USUARIO_BACK, asignacionTurnosDTO.getUsuarioAsignar().getNombreUsuario());
                    parametrosProceso.put(NUMERO_RADICACION, solicitud.getNumeroRadicacion());
                    parametrosProceso.put(TIPO_CRUCE, 1);
                    parametrosProceso.put("tiempoGestionCruces", tiempoGestionCruces);
                    Long idInstanciaProceso = iniciarProceso(ProcesoEnum.CRUCES_POSTULACION_FOVIS, parametrosProceso);
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

    public static List<SolicitudGestionCruceDTO> fragmentoArray(List<SolicitudGestionCruceDTO> array, int ini, int fin) {
        List<SolicitudGestionCruceDTO> newArray = new ArrayList<SolicitudGestionCruceDTO>();
        for (int i = ini; i < fin; i++) {
            newArray.add(array.get(i));
        }
        return newArray;
    }

    private List<AsignacionTurnosDTO> asignarTareaGestionCruce2(List<SolicitudGestionCruceDTO> solicitudesPostulacion,
                                                                UserDTO userDTO, UserDTO usuarioAsignar, Integer tope) {

        Integer cont = 0;
        String radicado = "";
        List<AsignacionTurnosDTO> respuesta = new ArrayList<>();

        try {

            NumeroRadicadoCorrespondenciaDTO numeroRadicado = obtenerNumeroRadicado(solicitudesPostulacion.size());
            for (int j = 0; j < solicitudesPostulacion.size(); j++) {

                SolicitudGestionCruceDTO solicitudGestionCruceDTO = solicitudesPostulacion.get(j);
                if (!EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())) {

                    if (cont < tope && cont != 0) {
                        cont++;
                        String radicadoHijo = (radicado + "_" + cont);
                        solicitudGestionCruceDTO.setNumeroRadicacion(radicadoHijo);
                        solicitudGestionCruceDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                        solicitudGestionCruceDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                        solicitudGestionCruceDTO.setFechaRadicacion(new Date().getTime());
                        SolicitudGestionCruceDTO solicitudCruce = crearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);

                        // Se actualiza la solicitud
                        solicitudCruce.setIdInstanciaProceso(null);
                        solicitudCruce.setDestinatario(usuarioAsignar.getNombreUsuario());
                        solicitudCruce.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                        actualizarSolicitudGestionCruce(solicitudCruce);

                        // Se genera la data temporal
                        guardarDatosTemporalGestionCruce(solicitudCruce);


                    } else {
                        radicado = "";
                        cont = 0;
                        //Se vuelve a crear un radicado padre y su proceso
                        SolicitudGestionCruceDTO solicitudGestionCruceDTOPadre = new SolicitudGestionCruceDTO();
                        solicitudGestionCruceDTOPadre.setNumeroRadicacion(numeroRadicado.nextValue());
                        solicitudGestionCruceDTOPadre.setUsuarioRadicacion(userDTO.getNombreUsuario());
                        solicitudGestionCruceDTOPadre.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                        solicitudGestionCruceDTOPadre.setFechaRadicacion(new Date().getTime());
                        solicitudGestionCruceDTOPadre.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS);
                        solicitudGestionCruceDTOPadre.setIdSolicitudPostulacion(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
                        SolicitudGestionCruceDTO solicitudPadre = crearRegistroSolicituGestionCruce(solicitudGestionCruceDTOPadre);
                        radicado = solicitudPadre.getNumeroRadicacion();
                        AsignacionTurnosDTO asignar = new AsignacionTurnosDTO();
                        asignar.setIdSolicitud(solicitudPadre.getIdSolicitud());
                        asignar.setUsuarioAsignar(usuarioAsignar);
                        asignar.setUsuarioRadica(userDTO);
                        asignar.setRadicado(radicado);
                        respuesta.add(asignar);

                        cont++;
                        String radicadoHijo = (radicado + "_" + cont);
                        solicitudGestionCruceDTO.setNumeroRadicacion(radicadoHijo);
                        solicitudGestionCruceDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                        solicitudGestionCruceDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                        solicitudGestionCruceDTO.setFechaRadicacion(new Date().getTime());
                        SolicitudGestionCruceDTO solicitudCruce = crearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);
                        // Se actualiza la solicitud
                        solicitudCruce.setIdInstanciaProceso(null);
                        solicitudCruce.setDestinatario(usuarioAsignar.getNombreUsuario());
                        solicitudCruce.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                        actualizarSolicitudGestionCruce(solicitudCruce);

                        // Se genera la data temporal
                        guardarDatosTemporalGestionCruce(solicitudCruce);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
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

    /**
     * Crea el registro de las solicitudes de gestion de cruces
     *
     * @param solicitudGestionCruceDTO Lista solicitudes a crear
     * @return Lista solicitudes creadas
     */
    private List<SolicitudGestionCruceDTO> crearRegistroSolicitudGestionCruce(List<SolicitudGestionCruceDTO> solicitudGestionCruceDTO) {
        CrearRegistroListaSolicituGestionCruce crearRegistroSolicituGestionCruce = new CrearRegistroListaSolicituGestionCruce(solicitudGestionCruceDTO);
        crearRegistroSolicituGestionCruce.execute();
        return crearRegistroSolicituGestionCruce.getResult();
    }

    /**
     * Obtiene las solicitudes gestion cruce para asignar
     *
     * @param idCargue    Identificador cargue
     * @param listCedulas Lista de cedulas evaluadas
     * @param listCruces  Lista de cruces realizados
     * @return Lista de solicitudes gestion cruce
     */
    private List<SolicitudGestionCruceDTO> getSolictudesCruce(Long idCargue, List<CargueArchivoCruceFovisCedulaDTO> listCedulas,
                                                              List<CruceDetalleDTO> listCruces) {
        //List<SolicitudGestionCruceDTO> listResult = new ArrayList<>();
        // Lista postulaciones con cruces NUEVO
        List<String> listNumerosPostulacionNuevo = new ArrayList<>();
        // Lista postulaciones con cruces
        List<String> listNumerosPostulacion = new ArrayList<>();
        // Mapa con los cruces por numero de postulacion
        Map<String, List<CruceDetalleDTO>> mapPostulacionCruces = new HashMap<>();
        // Mapa con los cruces NUEVOS por numero postulacion 
        Map<String, List<CruceDetalleDTO>> mapPostulacionCrucesNuevos = new HashMap<>();
        // Lista cedulas con Cruce
        List<String> listCedulasCruce = new ArrayList<>();
        // Lista cedulas sin cruce
        List<String> listCedulasSinCruce = new ArrayList<>();
        // Cruces con solicitud gestion
        for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
            // Las cedulas con cruce
            listCedulasCruce.add(cruceDetalleDTO.getNumeroIdentificacion());
            // Se obtiene el nro de postulacion
            String nroPostulacion = cruceDetalleDTO.getCruce().getNumeroPostulacion();
            // Mapa con los cruces encontrados por postulacion
            if (nroPostulacion != null && !listNumerosPostulacion.contains(nroPostulacion)) {
                listNumerosPostulacion.add(nroPostulacion);
                List<CruceDetalleDTO> listCrucePost = new ArrayList<>();
                listCrucePost.add(cruceDetalleDTO);
                mapPostulacionCruces.put(nroPostulacion, listCrucePost);
            } else if (mapPostulacionCrucesNuevos.containsKey(nroPostulacion)) {
                mapPostulacionCruces.get(nroPostulacion).add(cruceDetalleDTO);
            }
            //Agrupar cruces en estado NUEVO y diferentes al tipo SISBEN, UNIDOS y REUNIDOS por nro postulacion
            if (cruceDetalleDTO.getCruce().getEstadoCruce().equals(EstadoCruceEnum.NUEVO)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.SISBEN)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.UNIDOS)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.REUNIDOS)) {
                if (nroPostulacion != null && !listNumerosPostulacionNuevo.contains(nroPostulacion)) {
                    listNumerosPostulacionNuevo.add(nroPostulacion);
                    List<CruceDetalleDTO> listCrucePost = new ArrayList<>();
                    listCrucePost.add(cruceDetalleDTO);
                    mapPostulacionCrucesNuevos.put(nroPostulacion, listCrucePost);
                } else if (mapPostulacionCrucesNuevos.containsKey(nroPostulacion)) {
                    mapPostulacionCrucesNuevos.get(nroPostulacion).add(cruceDetalleDTO);
                }
            }
        }
        // Solicitud gestion cedulas sin cruce
        for (CargueArchivoCruceFovisHojaDTO informacionHojaCruceFovisDTO : listCedulas) {
            if (!listCedulasCruce.contains(informacionHojaCruceFovisDTO.getNroCedula())) {
                listCedulasSinCruce.add(informacionHojaCruceFovisDTO.getNroCedula());
            }
        }
        // Obtener cedulas con cruces previamente reportados o de tipo SISBEN, UNIDOS y REUNIDOS
        for (String nroPostulacion : mapPostulacionCruces.keySet()) {
            listCedulasSinCruce.addAll(obtenerCedulasCrucesPreviamenteReportados(mapPostulacionCruces, nroPostulacion));
        }
        // Se consultan las solicitudes de postulacion por numero de postulacion
        List<SolicitudPostulacionModeloDTO> listSolicitudesPostulacion = null;
        if (listNumerosPostulacion != null && !listNumerosPostulacion.isEmpty()) {
            ConsultarSolicitudPostulacionPorListaNumeroPostulacion consultarSolPostNroService = new ConsultarSolicitudPostulacionPorListaNumeroPostulacion(
                    listNumerosPostulacion);
            consultarSolPostNroService.execute();
            listSolicitudesPostulacion = consultarSolPostNroService.getResult();
        }
        List<Long> listIdSolPostulacion = new ArrayList<>();
        if (listSolicitudesPostulacion != null) {
            for (SolicitudPostulacionModeloDTO solPost : listSolicitudesPostulacion) {
                listIdSolPostulacion.add(solPost.getIdSolicitudPostulacion());
            }
        }
        // Se consulta las solicitud de postulacion por numero de cedula (las sin cruces reportados)}
        Map<String, SolicitudPostulacionModeloDTO> mapCedulaPostulacion = null;
        if (listCedulasSinCruce != null && !listCedulasSinCruce.isEmpty()) {
            mapCedulaPostulacion = consultarSolicitudPostulacionCedulas(listCedulasSinCruce);
        }
        if (mapCedulaPostulacion != null && !mapCedulaPostulacion.isEmpty()) {
            for (String cedula : mapCedulaPostulacion.keySet()) {
                ObjectMapper mapper = new ObjectMapper();
                SolicitudPostulacionModeloDTO solPost = mapper.convertValue(mapCedulaPostulacion.get(cedula),
                        SolicitudPostulacionModeloDTO.class);
                if (solPost != null && solPost.getIdSolicitudPostulacion() != null) {
                    listIdSolPostulacion.add(solPost.getIdSolicitudPostulacion());
                }
            }
        }
        // Se consultan las solicitudes de gestion cruce por identificacion solicitud postulacion
        //List<SolicitudGestionCruceDTO> listSolicitudesGestionCruceExistentes = null;
        //if (listIdSolPostulacion != null && !listIdSolPostulacion.isEmpty()) {
        //    ConsultarSolicitudGestionCrucePorListPostulacionTipoCruce consultarSolGestCrucePostulacionService = new ConsultarSolicitudGestionCrucePorListPostulacionTipoCruce(
        //            TipoCruceEnum.EXTERNO, listIdSolPostulacion);
        //    consultarSolGestCrucePostulacionService.execute();
        //    listSolicitudesGestionCruceExistentes = consultarSolGestCrucePostulacionService.getResult();
        //}
        // Se obtiene la lista de solicitudes calculada
        List<SolicitudGestionCruceDTO> listResultSolGestion = getSolicitudGestionCruce(idCargue, listNumerosPostulacionNuevo,
                mapPostulacionCrucesNuevos, listCedulasSinCruce, listSolicitudesPostulacion, mapCedulaPostulacion);
        // Se verifica las solicitudes de gestion de cruce para identificar que ya fue procesado y no duplicar la tarea de gestion
        //for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listResultSolGestion) {
        //    if (!esSolicitudGestionCruceDuplicado(solicitudGestionCruceDTO, listSolicitudesGestionCruceExistentes)) {
        //        listResult.add(solicitudGestionCruceDTO);
        //    }
        //}
        return listResultSolGestion;
    }

    /**
     * Se identifica si el registro de solicitud de gestion de cruce para la solicitud de postulacion ya existe, pues no se puede registrar
     * nuevamente por que la tarea ya paso
     *
     * @param solicitudGestionNuevo              Solicitud gestion cruce nuevo a registrar
     * @param listSolicitudGestionCruceExistente Lista de solicitudes de gestion cruce existentes
     * @return True Si la solicitud de gestion existe y False en caso contrario
     */
    private Boolean esSolicitudGestionCruceDuplicado(SolicitudGestionCruceDTO solicitudGestionNuevo,
                                                     List<SolicitudGestionCruceDTO> listSolicitudGestionCruceExistente) {
        Boolean result = false;
        if (listSolicitudGestionCruceExistente == null || listSolicitudGestionCruceExistente.isEmpty()) {
            return result;
        }
        for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolicitudGestionCruceExistente) {
            if (solicitudGestionNuevo.getIdSolicitudPostulacion().equals(solicitudGestionCruceDTO.getIdSolicitudPostulacion())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Obtiene la lista de cedulas por numero de postulacion de los cruces reportados
     *
     * @param mapPostulacionCruces Mapa postulaciones cruces
     * @param nroPostulacion       Numero de postulacion
     * @return Lista de cedulas con cruces diferentes a nuevos por postulacion
     */
    private List<String> obtenerCedulasCrucesPreviamenteReportados(Map<String, List<CruceDetalleDTO>> mapPostulacionCruces,
                                                                   String nroPostulacion) {
        List<String> listCedulas = new ArrayList<>();
        for (CruceDetalleDTO cruceDetalleDTO : mapPostulacionCruces.get(nroPostulacion)) {
            if (cruceDetalleDTO.getCruce().getEstadoCruce().equals(EstadoCruceEnum.NUEVO)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.SISBEN)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.UNIDOS)
                    && !cruceDetalleDTO.getTipo().equals(TipoInformacionCruceEnum.REUNIDOS)) {
                listCedulas = new ArrayList<>();
                break;
            }
            listCedulas.add(cruceDetalleDTO.getNumeroIdentificacion());
        }
        return listCedulas;
    }

    /**
     * Obtiene las solicitudes gestion de cruce con el estado sincruce reportado para las cedulas que no se registraron cruces
     *
     * @param listCedulasSinCruce Lista cedulas sin cruce
     * @return Lista de solicitudes gestion a registrar
     */
    private List<SolicitudGestionCruceDTO> getSolicitudGestionCruceSinCruce(List<String> listCedulasSinCruce,
                                                                            List<Long> listSolicitudPostulacionAdd, Map<String, SolicitudPostulacionModeloDTO> mapCedulaPostulacion) {
        List<SolicitudGestionCruceDTO> listSolicitudes = new ArrayList<>();
        // Lista se solicitudes de postulacion a las que se les va creando una solicitud de gestion
        List<Long> listSolPost = new ArrayList<>();
        listSolPost.addAll(listSolicitudPostulacionAdd);

        if (mapCedulaPostulacion != null && !mapCedulaPostulacion.isEmpty()) {
            for (String cedula : listCedulasSinCruce) {
                ObjectMapper mapper = new ObjectMapper();
                SolicitudPostulacionModeloDTO solPost = mapper.convertValue(mapCedulaPostulacion.get(cedula),
                        SolicitudPostulacionModeloDTO.class);
                if (solPost != null && solPost.getIdSolicitudPostulacion() != null
                        && !listSolPost.contains(solPost.getIdSolicitudPostulacion())) {
                    SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
                    solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO);
                    solicitudGestionCruceDTO.setIdSolicitudPostulacion(solPost.getIdSolicitudPostulacion());
                    solicitudGestionCruceDTO.setTipoCruce(TipoCruceEnum.EXTERNO);
                    solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CERRADA);
                    solicitudGestionCruceDTO.setFechaCreacion(new Date().getTime());
                    solicitudGestionCruceDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    solicitudGestionCruceDTO.setClasificacion(ClasificacionEnum.HOGAR);
                    solicitudGestionCruceDTO.setResultadoProceso(ResultadoProcesoEnum.APROBADA);
                    solicitudGestionCruceDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS);
                    crearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);
                    listSolicitudes.add(solicitudGestionCruceDTO);
                    listSolPost.add(solPost.getIdSolicitudPostulacion());
                }
            }
        }
        return listSolicitudes;
    }

    /**
     * Obtiene la lista de solicitudes sde gestion de curces por numeros de postulacion de los cruces nuevos
     *
     * @param idCargue               Identificador cargue
     * @param listNumerosPostulacion Numeros postulacion cruces nuevos
     * @return Lista de solicitudes a crear
     */
    private List<SolicitudGestionCruceDTO> getSolicitudGestionCruce(Long idCargue, List<String> listNumerosPostulacion,
                                                                    Map<String, List<CruceDetalleDTO>> listCruces, List<String> listCedulasSinCruce,
                                                                    List<SolicitudPostulacionModeloDTO> listPostulacion, Map<String, SolicitudPostulacionModeloDTO> mapCedulaPostulacion) {
        // Lista de solicitudes de postulacion agregadas
        List<Long> listSolPost = new ArrayList<>();
        // Lista de solicitudes
        List<SolicitudGestionCruceDTO> listSolicitudGestion = new ArrayList<>();
        if (!listNumerosPostulacion.isEmpty()) {
            // Se crea la solicitud de gestion de cruce por postulacion y se le asocian los cruces 
            for (String nroPostulacion : listNumerosPostulacion) {
                // Se obtiene la solicitud de postulacion
                SolicitudPostulacionModeloDTO solPostu = obtenerSolicitudPostulacion(listPostulacion, nroPostulacion);
                if (solPostu != null && !listSolPost.contains(solPostu.getIdSolicitudPostulacion())) {
                    SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
                    solicitudGestionCruceDTO.setIdSolicitudPostulacion(solPostu.getIdSolicitudPostulacion());
                    solicitudGestionCruceDTO.setListCrucesAsociados(listCruces.get(nroPostulacion));
                    solicitudGestionCruceDTO.setTipoCruce(TipoCruceEnum.EXTERNO);
                    solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CRUCES_PENDIENTE_POR_GESTIONAR);
                    solicitudGestionCruceDTO.setFechaCreacion(new Date().getTime());
                    solicitudGestionCruceDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    solicitudGestionCruceDTO.setClasificacion(ClasificacionEnum.HOGAR);
                    solicitudGestionCruceDTO.setTipoTransaccion(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS);
                    solicitudGestionCruceDTO.setNumeroRadicadoPostulacion(solPostu.getNumeroRadicacion());

                    listSolicitudGestion.add(solicitudGestionCruceDTO);

                    listSolPost.add(solPostu.getIdSolicitudPostulacion());
                }
            }
        }
        // Se crea la solicitud de gestion cruce para cedulas sin cruce
        if (!listCedulasSinCruce.isEmpty()) {
            List<SolicitudGestionCruceDTO> solSinCruces = getSolicitudGestionCruceSinCruce(listCedulasSinCruce, listSolPost, mapCedulaPostulacion);
        }
        return listSolicitudGestion;
    }

    /**
     * Se obtiene la solicitud de postulacion de la lista enviada por el numero de postulacion
     *
     * @param listPostulacion Lista de posutlaciones existentes
     * @param nroPostulacion  Numero de radicado que identifica una postulacion
     * @return Solicitud de postulacion relacionada con el numero enviado
     */
    private SolicitudPostulacionModeloDTO obtenerSolicitudPostulacion(List<SolicitudPostulacionModeloDTO> listPostulacion,
                                                                      String nroPostulacion) {
        for (SolicitudPostulacionModeloDTO solicitudPostulacion : listPostulacion) {
            if (solicitudPostulacion.getNumeroRadicacion().equals(nroPostulacion)) {
                return solicitudPostulacion;
            }
        }
        return null;
    }

    /**
     * Realiza el proceso de asignacion de las solicitudes de posutlacion que tuvieron cruces
     *
     * @param listUsuarios
     * @param listSolicitudes
     * @throws JsonProcessingException
     */
    private void asignarTareaGestionCruce2(List<String> listUsuarios, List<SolicitudGestionCruceDTO> listSolicitudes) {
        List<UserDTO> usuariosDTO = new ArrayList<>();
        if (listUsuarios != null && !listUsuarios.isEmpty()) {
            String tiempoGestionCruces = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_PFP_TIEMPO_PEND_GESTION_CRUCES);

            for (String user : listUsuarios) {
                UserDTO userDTO = new UserDTO();
                userDTO.setNombreUsuario(user);
                usuariosDTO.add(userDTO);
            }
            String ultimoUsuario = null;
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolicitudes) {
                if (EstadoCruceHogarEnum.SIN_CRUCE_REPORTADO.equals(solicitudGestionCruceDTO.getEstadoCruceHogar())) {
                    continue;
                }
                // Se obtiene el usuario a asignar
                UserDTO usuarioAsignar = this.asignacionConsecutivaPorTurnos(usuariosDTO, ultimoUsuario);
                ultimoUsuario = usuarioAsignar.getNombreUsuario();
                // Se inicia el proceso BPM
                Map<String, Object> parametrosProceso = new HashMap<String, Object>();
                parametrosProceso.put(ID_SOLICITUD, solicitudGestionCruceDTO.getIdSolicitud());
                parametrosProceso.put(USUARIO_BACK, ultimoUsuario);
                parametrosProceso.put(NUMERO_RADICACION, solicitudGestionCruceDTO.getNumeroRadicacion());
                parametrosProceso.put(TIPO_CRUCE, 1);
                parametrosProceso.put("tiempoGestionCruces", tiempoGestionCruces);
                Long idInstanciaProceso = iniciarProceso(ProcesoEnum.CRUCES_POSTULACION_FOVIS, parametrosProceso);
                // Se actualiza la solicitud
                solicitudGestionCruceDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
                solicitudGestionCruceDTO.setDestinatario(ultimoUsuario);
                solicitudGestionCruceDTO.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
                actualizarSolicitudGestionCruce(solicitudGestionCruceDTO);

                // Se genera la data temporal
                guardarDatosTemporalGestionCruce(solicitudGestionCruceDTO);
            }
        }
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
     * Método encargado invocar el servicio que guarda temporalmente los datos de la solicitud gestion cruce fovis.
     *
     * @param solicitudVerificacion dto con los datos a guardar.
     */
    private void guardarDatosTemporalGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        logger.debug("Inicio de método guardarDatosTemporalGestionCruce");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(solicitudGestionCruceDTO);
            GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(solicitudGestionCruceDTO.getIdSolicitud(),
                    jsonPayload);
            datosTemporalService.execute();
            logger.debug("Fin de método guardarDatosTemporalGestionCruce");
        } catch (JsonProcessingException e) {
            logger.error("Ocurrio un error en la conversión a JSON", e);
            throw new TechnicalException(e);
        }
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

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#guardarParcialCruces(java.util.List)
     */
    @Override
    public void guardarParcialCruces(List<CruceDetalleDTO> listCruces) {
        logger.debug("Inicia servicio guardarParcialCruces(List<CruceDetalleDTO>)");
        // Se realiza el registro de la informacion enviada
        CrearRegistroCruce crearRegistroCruce = new CrearRegistroCruce(listCruces);
        crearRegistroCruce.execute();
        logger.debug("Finaliza servicio guardarParcialCruces(List<CruceDetalleDTO>)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#registrarResultadoCruces(java.util.List)
     */
    @Override
    public void registrarResultadoCruces(AsignaResultadoCruceDTO asignaResultadoCruce) {
        logger.debug("Inicia servicio registrarResultadoCruces(List<CruceDetalleDTO>)");
        List<CruceDetalleDTO> listCruces = asignaResultadoCruce.getListCruces();
        // Se actualiza la informacion del cruce
        CrearRegistroCruce crearRegistroCruce = new CrearRegistroCruce(listCruces);
        crearRegistroCruce.execute();

        // Se obtiene la solicitud de gestion cruce del primer cruce
        SolicitudGestionCruceDTO solicitudGestionCruceDTO = null;

        CruceDetalleDTO cruceDetalle = listCruces.iterator().next();

        if (cruceDetalle.getCruce() != null && cruceDetalle.getCruce().getIdSolicitudGestionCruce() != null) {
            ConsultarSolicitudGestionCruce consultarSolicitudGestionCruce = new ConsultarSolicitudGestionCruce(
                    cruceDetalle.getCruce().getIdSolicitudGestionCruce());
            consultarSolicitudGestionCruce.execute();
            solicitudGestionCruceDTO = consultarSolicitudGestionCruce.getResult();
        }

        // Se verifica si hay al menos un cruce ratificado
        Boolean cruceRatificado = null;
        if (solicitudGestionCruceDTO != null && solicitudGestionCruceDTO.getTipoCruce().equals(TipoCruceEnum.EXTERNO)) {
            cruceRatificado = isCrucesRatificados(listCruces, EstadoCruceEnum.CRUCE_RATIFICADO);
        } else if (solicitudGestionCruceDTO.getTipoCruce().equals(TipoCruceEnum.INTERNO)) {
            cruceRatificado = isCrucesRatificados(listCruces, EstadoCruceEnum.CRUCE_INT_RATIFICADO);
        }
        // Se actualiza el estado cruce hogar de acuerdo al resultado de los cruces
        if (cruceRatificado != null && cruceRatificado) {
            solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCE_RATIFICADO_PENDIENTE_VERIFICACION);
        } else if (cruceRatificado != null && !cruceRatificado) {
            solicitudGestionCruceDTO.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCE_SUBSANADO_PENDIENTE_VERIFICACION);
        }
        // Si se registro una solicitud global de gestion de cruce se actualiza el resultado de proceso
        solicitudGestionCruceDTO.setEstadoSolicitudGestionCruce(EstadoSolicitudGestionCruceEnum.CERRADA);
        if (solicitudGestionCruceDTO.getIdSolicitud() != null) {
            ActualizarEstadoSolicitudGestionCruce actualizarEstadoSolicitudGestionCruce = new ActualizarEstadoSolicitudGestionCruce(
                    solicitudGestionCruceDTO.getIdSolicitud(), EstadoSolicitudGestionCruceEnum.CERRADA);
            actualizarEstadoSolicitudGestionCruce.execute();
        }
        ActualizarSolicitudGestionCruce actualizarSolicitudGestionCruce = new ActualizarSolicitudGestionCruce(solicitudGestionCruceDTO);
        actualizarSolicitudGestionCruce.execute();

        //terminarTarea(asignaResultadoCruce.getIdTarea(), null);

        logger.debug("Finaliza servicio registrarResultadoCruces(List<CruceDetalleDTO>)");
    }


    /**
     * Indica si los cruces registrados hay almenos uno ratificado
     *
     * @param listCruces       Lista de cruces
     * @param estadoRatificado Estado de ratificacion EXTERNO e INTERNO
     * @return
     */
    private Boolean isCrucesRatificados(List<CruceDetalleDTO> listCruces, EstadoCruceEnum estadoRatificado) {
        boolean cruceRatificado = false;
        for (CruceDetalleDTO cruceDetalleDTO : listCruces) {
            if (cruceDetalleDTO.getCruce() != null && cruceDetalleDTO.getCruce().getEstadoCruce() != null
                    && cruceDetalleDTO.getCruce().getEstadoCruce().equals(estadoRatificado)) {
                cruceRatificado = true;
                if (estadoRatificado.equals(EstadoCruceEnum.CRUCE_INT_RATIFICADO)) {
                    rechazarPostulacion(cruceDetalleDTO);
                }
                break;
            }
        }
        return cruceRatificado;
    }

    public void rechazarPostulacion(CruceDetalleDTO detalle) {
        SolicitudGestionCruceDTO solicitudGestionCruceDTO = null;
        ConsultarSolicitudGestionCruce consultarSolicitudGestionCruce = new ConsultarSolicitudGestionCruce(
                detalle.getCruce().getIdSolicitudGestionCruce());
        consultarSolicitudGestionCruce.execute();
        solicitudGestionCruceDTO = consultarSolicitudGestionCruce.getResult();
        SolicitudPostulacionModeloDTO spo = consultarSolicitudPostulacionById(solicitudGestionCruceDTO.getIdSolicitudPostulacion());
        SolicitudPostulacionFOVISDTO solicitudTemporal = consultarPostulacionTemporal(spo.getIdSolicitud());
        cambiarEstadoHogar(spo.getIdPostulacionFOVIS(), EstadoHogarEnum.RECHAZADO);
        actualizarEstadoSolicitudPostulacion(spo.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
        // Se cierra la solicitud de postulación
        actualizarEstadoSolicitudPostulacion(spo.getIdSolicitud(),
                EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        //cambiarEstadoHogar(spo.getIdPostulacionFOVIS(), EstadoHogarEnum.RECHAZADO);
        solicitudTemporal.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        solicitudTemporal.getPostulacion().setEstadoHogar(EstadoHogarEnum.RECHAZADO);
        actualizarJsonPostulacion(spo.getIdPostulacionFOVIS(), solicitudTemporal);
        guardarDatosTemporal(solicitudTemporal);

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

    /**
     * Método encargado de parametrizar una notificación para ser enviada (Cuando el proceso es web)
     *
     * @param intento             define si se trata de un intento de novedad o no.
     * @param solicitudNovedadDTO dto con los datos necesarios para parametrizar la notificación.
     * @param userDTO             usuario autenticado.
     */
    private void parametrizarNotificacion(boolean intento, SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, UserDTO userDTO) {
        /*
         * se envía notificaciones desde servicios, en el caso que:
         * - Sea este radicando y el canal sea WEB
         * - Sea un intento fallido y el canal sea WEB
         */
        NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
        if (intento) {
            /* Comunicado #92 por intento de postulación WEB */
            notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_VAL_NEXT_RAD_POS_FOVIS_WEB);
            notificacion.setProcesoEvento(solicitudPostulacionDTO.getTipoTransaccionEnum().getProceso().name());
            notificacion.setIdSolicitud(solicitudPostulacionDTO.getIdSolicitud());
            notificacion.setTipoTx(solicitudPostulacionDTO.getTipoTransaccionEnum());
        } else {
            /* Comunicado #91 por radicación de postulación WEB */
            notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_POS_FOVIS_WEB);
            notificacion.setProcesoEvento(solicitudPostulacionDTO.getTipoTransaccionEnum().getProceso().name());
            notificacion.setIdSolicitud(solicitudPostulacionDTO.getIdSolicitud());
            notificacion.setTipoTx(solicitudPostulacionDTO.getTipoTransaccionEnum());
        }

        enviarComunicadoConstruido(notificacion);

    }

    /**
     * Método encargado de enviar un comunicado construido
     *
     * @param notificacion, notificacion a enviar
     */
    private void enviarComunicadoConstruido(NotificacionParametrizadaDTO notificacion) {
        try {
            enviarCorreoParametrizado(notificacion);
        } catch (Exception e) {
            // este es el caso en que el envío del correo del comunicado no debe
            // abortar el proceso de afiliación
            // TODO Mostrar solo el log o persistir el error la bd ?
            logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
        }
    }

    /**
     * Método encargado de llamar el cliente del servicio envio de correo
     * parametrizado
     *
     * @param notificacion, notificación dto que contiene la información del correo
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
        logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
    }

    /**
     * Metodo que verifica si la solicitud de postulación posee una solicitud de gestión de cruce con cruces gestionados, es decir en estado
     * Cerrado
     *
     * @param solicitudPostulacionModeloDTO objeto con el id de la solicitud.
     * @return indicador de si posee o no los cruces gestionados.
     */
    private boolean solicitudPoseeCrucesGestionados(SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO) {

        logger.debug("Inicia solicitudPoseeCrucesGestionados(SolicitudPostulacionModeloDTO)");
        boolean valido = false;
        SolicitudPostulacionModeloDTO solicitudPostulacionDTO = consultarSolicitudPostulacion(
                solicitudPostulacionModeloDTO.getIdSolicitud());
        ConsultarSolicitudGestionCrucePorPostulacionTipoCruce consultarSolicitudGestionCrucePorPostulacionTipoCruce = new ConsultarSolicitudGestionCrucePorPostulacionTipoCruce(
                TipoCruceEnum.EXTERNO, solicitudPostulacionDTO.getIdSolicitudPostulacion());
        consultarSolicitudGestionCrucePorPostulacionTipoCruce.execute();
        List<SolicitudGestionCruceDTO> listSolGestion = consultarSolicitudGestionCrucePorPostulacionTipoCruce.getResult();
        if (listSolGestion != null) {
            for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolGestion) {
                if (EstadoSolicitudGestionCruceEnum.CERRADA.equals(solicitudGestionCruceDTO.getEstadoSolicitudGestionCruce())) {
                    valido = true;
                    break;
                }
            }
        }
        logger.debug("Inicia solicitudPoseeCrucesGestionados(SolicitudPostulacionModeloDTO)");
        return valido;
    }

    /**
     * Método que invoca el servicio de consulta los documentos de soporte de un oferente
     *
     * @param idOferente identificador del oferente
     * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la información de los documentos del
     * oferente
     */
    private List<DocumentoSoporteModeloDTO> consultarDocumentosSoportePorIdOferente(Long idOferente) {
        logger.debug("Inicia el método consultarDocumentosSoportePorIdOferente");
        ConsultarDocumentosSoporteOferentePorIdOferente service = new ConsultarDocumentosSoporteOferentePorIdOferente(idOferente);
        service.execute();
        logger.debug("Finaliza el método consultarDocumentosSoportePorIdOferente");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta los documentos de soporte de un proveedor
     *
     * @param idProveedor identificador del proveedor
     * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la información de los documentos del
     * proveedor
     */
    private List<DocumentoSoporteModeloDTO> consultarDocumentosSoportePorIdProveedor(Long idProveedor) {
        logger.debug("Inicia el método consultarDocumentosSoportePorIdProveedor");
        ConsultarDocumentosSoporteProveedorPorIdProveedor service = new ConsultarDocumentosSoporteProveedorPorIdProveedor(idProveedor);
        service.execute();
        logger.debug("Finaliza el método consultarDocumentosSoportePorIdProveedor");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta los documentos de soporte de un proyecto de vivienda
     *
     * @param idOferente identificador del oferente
     * @return Objeo <code>List<DocumentoSoporteModeloDTO> </code> con la información de los documentos
     * del proyecto de vivienda
     */
    private List<DocumentoSoporteModeloDTO> consultarDocumentosSoportePorIdProyecto(Long idProyectoVivienda) {
        logger.debug("Inicia el método consultarDocumentosSoportePorIdProyecto");
        ConsultarDocumentosSoporteProyectoPorIdProyecto service = new ConsultarDocumentosSoporteProyectoPorIdProyecto(idProyectoVivienda);
        service.execute();
        logger.debug("Finaliza el método consultarDocumentosSoportePorIdProyecto");
        return service.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#asignarSolicitudVerificacionFovis(
     *com.asopagos.fovis.composite.dto.AsignarSolicitudPostulacionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void asignarSolicitudVerificacionFovis(AsignarSolicitudPostulacionDTO entrada, UserDTO userDTO) {
        String destinatario = null;
        String sedeDestinatario = null;
        String observacion = null;
        Long idTarea;
        UsuarioDTO usuarioDTO = new UsuarioCCF();
        if (MetodoAsignacionBackEnum.AUTOMATICO.equals(entrada.getMetodoAsignacion())
                || entrada.getTipoTransaccion().getProceso().getWeb()) {
            if (entrada.getTipoTransaccion().getProceso().getWeb()) {
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(null, entrada.getTipoTransaccion().getProceso());
            } else {
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                        entrada.getTipoTransaccion().getProceso());
            }
            usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            sedeDestinatario = usuarioDTO.getCodigoSede();
            observacion = null;
        } else if (MetodoAsignacionBackEnum.MANUAL.equals(entrada.getMetodoAsignacion())) {
            // se busca el usuario a quien se le asigna la tarea, por su nombe
            // de usuario
            usuarioDTO = consultarUsuarioCajaCompensacion(entrada.getUsuarioBack());
            sedeDestinatario = usuarioDTO.getCodigoSede();
            destinatario = usuarioDTO.getNombreUsuario();
            observacion = entrada.getObservacion();
        }

        /* se actualiza la solicitud de verificación Fovis */
        SolicitudVerificacionFovisModeloDTO solicitudPostulacion = consultarSolicitudVerificacionFovis(entrada.getIdSolicitud());
        solicitudPostulacion.setDestinatario(destinatario);
        solicitudPostulacion.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
        solicitudPostulacion.setObservacion(observacion);

        Map<String, Object> params = null;
        params = new HashMap<>();
        params.put(USUARIO_BACK, destinatario);
        params.put(SENAL_DOCUMENTOS_FISICOS, entrada.getDocumentosFisicos());
        params.put(NUMERO_RADICACION, solicitudPostulacion.getNumeroRadicacion());

        idTarea = entrada.getIdTarea();
        if (idTarea == null) {
            idTarea = consultarTareaActiva(new Long(solicitudPostulacion.getIdInstanciaProceso()));
        }
        terminarTarea(idTarea, params);
    }

    /**
     * Método que consulta una solicitud de postulación.
     *
     * @param idSolicitud Es el identificador de la solicitud global.
     * @return solicitudNovedadDTO Solicitud de postulación consultada.
     */
    private SolicitudVerificacionFovisModeloDTO consultarSolicitudVerificacionFovis(Long idSolicitud) {
        SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO = new SolicitudVerificacionFovisModeloDTO();
        ConsultarSolicitudVerificacionFovis consultarSolicitudVerificacionFovis = new ConsultarSolicitudVerificacionFovis(idSolicitud);
        consultarSolicitudVerificacionFovis.execute();
        solicitudVerificacionFovisDTO = consultarSolicitudVerificacionFovis.getResult();
        return solicitudVerificacionFovisDTO;
    }

    /**
     * Método que registra una solicitud de verificacion Fovis y se inicia el proceso de legalizacion y desembolso.
     *
     * @param solicitudVerificacionFovis Datos de la solicitud a actualizar
     * @param userDTO                    Usuario del contexto de seguridad
     */
    private void registrarSolicitudEIniciarProcesoVerificacionFovis2(SolicitudPostulacionFOVISDTO solicitudPostulacionFovis,
                                                                     TipoSolicitudVerificacionFovisEnum tipoVerificacion, UserDTO userDTO, UserDTO usuarioAsignar) {

        SolicitudVerificacionFovisModeloDTO solicitud = this.crearSolicitudVerificacionFovisInicial(userDTO);
        //Se radica la solicitud de Verificación.
        this.radicarSolicitud(solicitud.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
        solicitud = this.consultarSolicitudVerificacionFovis(solicitud.getIdSolicitud());
        /* Se inicia el Proceso BPM Legalización y Desembolso */
        Long idInstancia = this.iniciarProcesoVerificacionPostulacionFovis(solicitud.getIdSolicitud(), solicitud.getNumeroRadicacion(),
                tipoVerificacion, userDTO, usuarioAsignar);
        solicitud.setIdInstanciaProceso(idInstancia.toString());
        solicitud.setIdPostulacionFOVIS(solicitudPostulacionFovis.getPostulacion().getIdPostulacion());
        solicitud.setTipoVerificacion(tipoVerificacion);
        solicitud.setDestinatario(usuarioAsignar.getNombreUsuario());
        solicitud.setSedeDestinatario(usuarioAsignar.getSedeCajaCompensacion());
        solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);
        // guardar temporal
        SolicitudVerificacionFovisDTO solicitudVerificacion = new SolicitudVerificacionFovisDTO();
        // Se elimina la información de la postulación para que se consulte la mas actualizada
        solicitudPostulacionFovis.setPostulacion(null);
        solicitudVerificacion.setDatosPostulacionFovis(solicitudPostulacionFovis);
        solicitudVerificacion.setSolicitudVerificacionFovisModeloDTO(solicitud);
        solicitudVerificacion.setIdSolicitud(solicitud.getIdSolicitud());
        solicitudVerificacion.setIdInstanciaProceso(idInstancia);
        guardarDatosTemporalVerificacion(solicitudVerificacion);
    }

    /**
     * Se encarga de guardar los datos de la solicitud de legalización y desembolso
     *
     * @param tipoVerificacionFovis
     */
    private SolicitudVerificacionFovisModeloDTO crearSolicitudVerificacionFovisInicial(UserDTO userDTO) {

        SolicitudVerificacionFovisModeloDTO solicitud = new SolicitudVerificacionFovisModeloDTO();
        solicitud.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitud.setClasificacion(ClasificacionEnum.HOGAR);
        solicitud.setTipoTransaccion(TipoTransaccionEnum.VERIFICACION_CONTROL_INTERNO_POSTULACION_FOVIS);
        solicitud.setFechaCreacion((new Date()).getTime());
        solicitud.setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.ASIGNADA_A_CONTROL_INTERNO);
        solicitud = this.crearActualizarSolicitudVerificacionFovis(solicitud);
        return solicitud;
    }

    /**
     * Método que invoca el servicio de actualización de una solicitud de
     * postulación
     *
     * @param solicitudVerificacionFovisDTO La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private SolicitudVerificacionFovisModeloDTO crearActualizarSolicitudVerificacionFovis(
            SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisDTO) {
        CrearActualizarSolicitudVerificacionFovis service = new CrearActualizarSolicitudVerificacionFovis(solicitudVerificacionFovisDTO);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una solicitud de verificación Fovis
     */
    private void actualizarEstadoSolicitudVerificacion(Long idSolicitud, EstadoSolicitudVerificacionFovisEnum estadoSolicitud) {
        ActualizarEstadoSolicitudVerificacionFovis actualizarEstadoSolPostulService = new ActualizarEstadoSolicitudVerificacionFovis(
                idSolicitud, estadoSolicitud);
        actualizarEstadoSolPostulService.execute();
    }

    /**
     * Inicia Proceso BPM de la Verificación de la Postulación Fovis
     *
     * @param idSolicitud
     * @param numeroRadicado
     * @param tipoVerificacion
     * @param usuarioActual
     * @param usuarioAsignar
     * @return identificador de la instancia del proceso
     */
    private Long iniciarProcesoVerificacionPostulacionFovis(Long idSolicitud, String numeroRadicado, TipoSolicitudVerificacionFovisEnum tipoVerificacion,
                                                            UserDTO usuarioActual, UserDTO usuarioAsignar) {
        logger.debug("Inicia iniciarProcesoVerificacionPostulacionFovis");
        Map<String, Object> parametrosProceso = new HashMap<String, Object>();
        parametrosProceso.put(ID_SOLICITUD, idSolicitud);
        parametrosProceso.put(NUMERO_RADICACION, numeroRadicado);
        parametrosProceso.put(USUARIO_BACK, usuarioActual.getNombreUsuario());
        parametrosProceso.put(USUARIO_CONTROL_INTERNO, usuarioAsignar.getNombreUsuario());
        parametrosProceso.put(TIPO_VERIFICACION, tipoVerificacion.name());
        IniciarProceso iniciarProcesVerificacionPostulacionFovisService = new IniciarProceso(ProcesoEnum.VERIFICACION_POSTULACION_FOVIS,
                parametrosProceso);
        iniciarProcesVerificacionPostulacionFovisService.execute();
        logger.debug("Finaliza iniciarProcesoVerificacionPostulacionFovis");
        return iniciarProcesVerificacionPostulacionFovisService.getResult();
    }

    /**
     * Método encargado invocar el servicio que guarda temporalmente los datos de la solicitud ve verifiación fovis.
     *
     * @param solicitudVerificacion dto con los datos a guardar.
     * @throws JsonProcessingException error convirtiendo
     */
    private void guardarDatosTemporalVerificacion(SolicitudVerificacionFovisDTO solicitudVerificacion) {
        logger.debug("Inicio de método guardarDatosTemporalVerificacion");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(solicitudVerificacion);
            GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(solicitudVerificacion.getIdSolicitud(), jsonPayload);
            datosTemporalService.execute();
            logger.debug("Fin de método guardarDatosTemporalVerificacion");
        } catch (JsonProcessingException e) {
            logger.error("Ocurrio un error en la conversión a JSON", e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Método que consulta los datos temporales guardados para la solicitud de verificación Fovis.
     *
     * @param idSolicitudGlobal
     * @return
     */
    private SolicitudVerificacionFovisDTO consultarVerificacionFovisTemporal(Long idSolicitudGlobal) {
        try {
            logger.debug(Interpolator.interpolate("Inicio consultarVerificacionFovisTemporal(Long) - idSolicitudGlobal:{0}", idSolicitudGlobal));
            String jsonPayload = consultarDatosTemporales(idSolicitudGlobal);
            ObjectMapper mapper = new ObjectMapper();
            SolicitudVerificacionFovisDTO solicitudVerificacionFovisDTO = mapper.readValue(jsonPayload,
                    SolicitudVerificacionFovisDTO.class);
            return solicitudVerificacionFovisDTO;
        } catch (IOException e) {
            logger.error("Ocurrio un error en la conversión", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Consulta el cruce existente para todos los tipos de informacion y la
     * cedula enviada
     *
     * @param nroCedula Nro cedula a consultar
     * @return Cruce detallado existente
     */
    private List<CruceDetalleDTO> consultarRegistroCruceExistente(String nroCedula) {
        // Se consulta el cruce existente en estado NUEVO para el documento
        // y el tipo informacion enviados
        ConsultarCruceTodosTiposInformacion consultarCruce = new ConsultarCruceTodosTiposInformacion(nroCedula);
        consultarCruce.execute();
        return consultarCruce.getResult();
    }

    @Override
    public InformacionDocumentoActaAsignacionDTO consultarFechaVigenciaAsignacion(Long fechaPublicacion) {
        logger.debug(Interpolator.interpolate("Inicio consultarFechaVigenciaAsignacion(Long) - fechaPublicacion:{0}", fechaPublicacion));
        // Objeto resultado
        InformacionDocumentoActaAsignacionDTO informacion = new InformacionDocumentoActaAsignacionDTO();
        // Se calcula la fecha inicio
        // Primer día calendario del mes siguiente a la fecha de publicación 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(fechaPublicacion));
        calendar.add(Calendar.MONTH, 1);
        Date fechaInicio = CalendarUtils.obtenerPrimerDiaMesTruncarHora(calendar.getTime());
        informacion.setFechaInicioVigenciaSubsidios(fechaInicio.getTime());

        // Se calcula la fecha fin
        // Se tiene en cuenta la parametrizacion
        ParametrizacionFOVISModeloDTO param = null;
        ConsultarDatosGeneralesFovis consultarDatosGeneralesFovis = new ConsultarDatosGeneralesFovis();
        consultarDatosGeneralesFovis.execute();
        List<ParametrizacionFOVISModeloDTO> listParametrizacion = consultarDatosGeneralesFovis.getResult();
        for (ParametrizacionFOVISModeloDTO parametrizacion : listParametrizacion) {
            if (parametrizacion.getParametro().equals(ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA)) {
                param = parametrizacion;
                break;
            }
        }
        if (param == null || param.getPlazoVencimiento() == null || param.getValorNumerico() == null) {
            return informacion;
        }
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.setTime(fechaInicio);

        switch (param.getPlazoVencimiento()) {
            case ANIOS:
                fechaFin.add(Calendar.YEAR, param.getValorNumerico().intValue());
                break;
            case MESES:
                fechaFin.add(Calendar.MONTH, param.getValorNumerico().intValue());
                break;
            case DIAS_CALENDARIO:
                fechaFin.add(Calendar.DAY_OF_YEAR, param.getValorNumerico().intValue());
                break;
            case DIAS_HABILES:
                ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
                consultarListafestivos.execute();
                List<ElementoListaDTO> festivos = consultarListafestivos.getResult();
                Date fechaF = CalendarUtils.calcularFecha(fechaInicio, param.getValorNumerico().intValue(), TipoDia.HABIL, festivos);
                fechaFin.setTime(fechaF);
                break;
            default:
                break;
        }
        informacion.setFechaVencimientoVigenciaSubsidios(fechaFin.getTime().getTime());

        return informacion;
    }

    @Asynchronous
    @Override
    public void procesarEstadoActualPostulaciones(List<PostulacionFOVISModeloDTO> listaPostulaciones) {
        logger.debug("Inicio servicio procesarEstadoActualPostulaciones(List<PostulacionFOVISModeloDTO>)");
        try {
            // Creación de tareas paralelas 
            List<Callable<SolicitudPostulacionFOVISDTO>> tareasParalelas = new LinkedList<>();
            // Se itera la lista de postulaciones
            for (PostulacionFOVISModeloDTO postulacionFOVISModeloDTO : listaPostulaciones) {
                Callable<SolicitudPostulacionFOVISDTO> parallelTask = () -> {
                    return consultarInformacionPostulacion(postulacionFOVISModeloDTO);
                };
                tareasParalelas.add(parallelTask);
            }
            managedExecutorService.invokeAll(tareasParalelas);

            logger.debug("Fin servicio procesarEstadoActualPostulaciones(List<PostulacionFOVISModeloDTO>)");
        } catch (InterruptedException e) {
            // No se propaga la excepción porque es un llamado asincrono
            logger.error("ERROR servicio procesarEstadoActualPostulaciones(List<PostulacionFOVISModeloDTO>)", e);
        }
    }

    /**
     * Consulta la información actual de la postulación y la asocia a la postulación asignada
     *
     * @param postulacionFOVISModeloDTO Información postulacion asingada
     * @return Solicitud postulación
     */
    private SolicitudPostulacionFOVISDTO consultarInformacionPostulacion(PostulacionFOVISModeloDTO postulacionFOVISModeloDTO) {
        logger.debug("Inicia servicio consultarInformacionPostulacion(PostulacionFOVISModeloDTO) ");
        try {
            SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO = new SolicitudPostulacionFOVISDTO();

            // Consultar la información básica de la postulación
            ConsultarInfoPostulacion consultarInfoPostulacion = new ConsultarInfoPostulacion(postulacionFOVISModeloDTO.getIdPostulacion());
            consultarInfoPostulacion.execute();
            solicitudPostulacionFOVISDTO = consultarInfoPostulacion.getResult();
            // Se convierte el resultado de consulta
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            solicitudPostulacionFOVISDTO.getPostulacion().setInformacionPostulacion(null);
            String jsonPayload = mapper.writeValueAsString(solicitudPostulacionFOVISDTO);
            postulacionFOVISModeloDTO.setInformacionAsignacion(jsonPayload);
            // Se actualiza la postulacion con los datos actuales
            CrearActualizarPostulacionFOVIS crearActualizarPostulacionFOVIS = new CrearActualizarPostulacionFOVIS(
                    postulacionFOVISModeloDTO);
            crearActualizarPostulacionFOVIS.execute();
            logger.debug("Finaliza servicio consultarInformacionPostulacion(PostulacionFOVISModeloDTO) ");
            return solicitudPostulacionFOVISDTO;
        } catch (JsonProcessingException e) {
            logger.error("Error en la conversión a JSON", e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Método que invoca el servicio que crea una persona.
     *
     * @param integranteHogarDTO Información del integrante hogar a actualizar
     * @return El identificador de la persona creada
     */
    private Long crearPersona(IntegranteHogarModeloDTO integranteHogarDTO) {
        logger.debug("Inicia el método crearPersona");
        CrearPersona service = new CrearPersona(new PersonaModeloDTO(integranteHogarDTO.convertToPersonaEntity()));
        service.execute();
        logger.debug("Finaliza el método crearPersona");
        return service.getResult();
    }

    /**
     * Actualiza la solicitud de gestion cruce
     *
     * @param solicitudGestionCruce DTO con la informacion de la solicitud de gestion
     * @return Solicitud de gestion
     */
    private SolicitudGestionCruceDTO actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruce) {
        ActualizarSolicitudGestionCruce actualizarSolicitudGestion = new ActualizarSolicitudGestionCruce(solicitudGestionCruce);
        actualizarSolicitudGestion.execute();
        return actualizarSolicitudGestion.getResult();
    }

    /**
     * Servicio que realiza el llamado a la consulta de la parametrización existente por modalidad
     *
     * @param modalidad Nombre modalidad a buscarle parametrización
     * @return Información de la parametrización
     */
    private ParametrizacionModalidadModeloDTO consultarParametrizacionModalidad(ModalidadEnum modalidad) {
        ConsultarParametrizacionModalidad consultarParametrizacionModalidad = new ConsultarParametrizacionModalidad(modalidad);
        consultarParametrizacionModalidad.execute();
        return consultarParametrizacionModalidad.getResult();
    }

    /**
     * Actualiza la información de la parametrización de modalidad actual asociada a la postulación
     *
     * @param idPostulacionFovis Identificador de la postulacion
     */
    private void actualizarParametrizacionModalidadPostulacion(Long idPostulacionFovis) {
        ConsultarPostulacionFOVIS consultarPostulacionFOVIS = new ConsultarPostulacionFOVIS(idPostulacionFovis);
        consultarPostulacionFOVIS.execute();
        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = consultarPostulacionFOVIS.getResult();
        ParametrizacionModalidadModeloDTO parametrizacionModalidad = consultarParametrizacionModalidad(
                postulacionFOVISModeloDTO.getIdModalidad());
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload;
        try {
            jsonPayload = mapper.writeValueAsString(parametrizacionModalidad);
        } catch (JsonProcessingException e) {
            jsonPayload = postulacionFOVISModeloDTO.getInformacionParametrizacion();
        }
        postulacionFOVISModeloDTO.setInformacionParametrizacion(jsonPayload);

        CrearActualizarPostulacionFOVIS crearActualizarPostulacionFOVIS = new CrearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);
        crearActualizarPostulacionFOVIS.execute();
    }

    /**
     * Llama al servicio que realiza la actualización de la información de json postulacion
     *
     * @param idPostulacion        Identificador postulacion
     * @param solicitudPostulacion Información de la solicitud de postulacion
     */
    private void actualizarJsonPostulacion(Long idPostulacion, SolicitudPostulacionFOVISDTO solicitudPostulacion) {
        ActualizarJsonPostulacion actualizarJsonPostulacion = new ActualizarJsonPostulacion(idPostulacion, solicitudPostulacion);
        actualizarJsonPostulacion.execute();
    }

    private SolicitudGestionCruceDTO consultarSolicitudGestionCrucePorSolicitudGlobal(Long idSolicitudGlobal) {
        SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO();
        ConsultarSolicitudGestionCrucePorSolicitudGlobal consultarSolicitudGestionCrucePorSolicitudGlobal = new ConsultarSolicitudGestionCrucePorSolicitudGlobal(idSolicitudGlobal);
        consultarSolicitudGestionCrucePorSolicitudGlobal.execute();
        solicitudGestionCruceDTO = consultarSolicitudGestionCrucePorSolicitudGlobal.getResult();
        return solicitudGestionCruceDTO;
    }

    private SolicitudGestionCruceDTO crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        CrearRegistroSolicituGestionCruce crearRegistroSolicituGestionCruce = new CrearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);
        crearRegistroSolicituGestionCruce.execute();
        return crearRegistroSolicituGestionCruce.getResult();
    }


    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#terminarTarea(java.lang,java.util)
     */
    @Override
    public void terminarTareaPadre(Long idTarea, String tipoTransaccionEnum, Long instanciaProceso, UserDTO userDTO) {
        Map<String, Object> params = new HashMap<String, Object>();
        ConsultarSolicitudFovis actualizarSolicitud = new ConsultarSolicitudFovis(instanciaProceso);
        actualizarSolicitud.execute();
        Solicitud solicitud = actualizarSolicitud.getResult();
        if (tipoTransaccionEnum.equals(TipoTransaccionEnum.GESTION_CRUCE_EXTERNO_FOVIS.name())) {
            ActualizarEstadoSolicitudGestionCruce actualizarEstadoSolicitudGestionCruce = new ActualizarEstadoSolicitudGestionCruce(
                    solicitud.getIdSolicitud(), EstadoSolicitudGestionCruceEnum.CERRADA);
            actualizarEstadoSolicitudGestionCruce.execute();
        } else if (tipoTransaccionEnum.equals(TipoTransaccionEnum.VERIFICACION_CONTROL_INTERNO_POSTULACION_FOVIS.name())) {
            params.put("resultadoControlInterno", 2);
            SolicitudVerificacionFovisDTO solicitudVerificacionTemporalPadre =
                    consultarVerificacionFovisTemporal(solicitud.getIdSolicitud());
            actualizarEstadoSolicitudVerificacion(solicitud.getIdSolicitud(), EstadoSolicitudVerificacionFovisEnum.CERRADA);
            solicitudVerificacionTemporalPadre.getSolicitudVerificacionFovisModeloDTO()
                    .setEstadoSolicitud(EstadoSolicitudVerificacionFovisEnum.CERRADA);
        } else {
            logger.info("No hay transaccion");
        }
        logger.info("termina tarea padre ");
        terminarTarea(idTarea, params);
    }

    public Solicitud consultarSolicitudGlobal(Long instanciaProceso) {
        logger.debug("ingresa a consultar solicitud");
        ConsultarSolicitudFovis actualizarSolicitud = new ConsultarSolicitudFovis(instanciaProceso);
        actualizarSolicitud.execute();
        Solicitud solicitud = actualizarSolicitud.getResult();
        return solicitud;
    }

    /**
     * Método que consulta una solicitud de postulación.
     *
     * @param numeroRadicacion Numero de radicado del tarea padre.
     * @param usuario          Usuario al cual le quedaron asignadas las tareas.
     * @return List<Solicitud> lista de las solicitudes encontradas
     */
    private List<TareasHeredadasDTO> consultarTareasHeredadas(String numeroRadicacion, String usuario, String tipoTransaccion) {
        List<TareasHeredadasDTO> solicitud = new ArrayList<TareasHeredadasDTO>();
        ConsultarTareasHeredadas tareas = new ConsultarTareasHeredadas(numeroRadicacion, usuario, tipoTransaccion);
        tareas.execute();
        solicitud = tareas.getResult();
        return solicitud;
    }


}
