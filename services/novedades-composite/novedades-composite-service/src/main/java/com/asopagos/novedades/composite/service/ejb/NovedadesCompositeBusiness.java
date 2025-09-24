package com.asopagos.novedades.composite.service.ejb;

import static com.asopagos.util.Interpolator.interpolate;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;

import com.asopagos.afiliaciones.clients.ConsultarTrabajadoresActivosConEmpleadorInactivo;
import com.asopagos.afiliaciones.clients.MarcarNovedadesRetiroNoProcesadasPila;
import com.asopagos.afiliaciones.clients.ObtenerNovedadesRetiroNoProcesadasPila;
import com.asopagos.afiliaciones.clients.ObtenerNumeroRadicadoCorrespondencia;
import com.asopagos.afiliaciones.clients.ActualizarCargueSupervivencia;
import com.asopagos.afiliaciones.dto.RelacionTrabajadorEmpresaDTO;
import com.asopagos.afiliados.clients.*;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import com.asopagos.afiliados.dto.InfoRelacionLaboral360DTO;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosPorIds;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import com.asopagos.dto.BeneficiarioNovedadAutomaticaDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.DiferenciasCargueActualizacionDTO;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.ListaDatoValidacionDTO;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.empleadores.clients.*;
import com.asopagos.empresas.clients.ConsultarUbicacionesEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import com.asopagos.entidades.ccf.novedades.RegistroNovedadFutura;
//new solicitud
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.afiliaciones.*;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.core.ValidadorGeneralEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueSupervivenciaEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.clients.*;
import com.asopagos.novedades.clients.ConsultarRetroactividadNovedad;
import com.asopagos.novedades.clients.VerificarEstructuraArchivoEmpleador;
import com.asopagos.novedades.clients.VerificarEstructuraArchivoPersona;
import com.asopagos.novedades.clients.VerificarEstructuraArchivoCcf;
import com.asopagos.novedades.composite.clients.RegistrarNovedadCertificadoBeneficiario;
import com.asopagos.novedades.composite.clients.RegistrarNovedadInactivarBeneficiario;
import com.asopagos.novedades.composite.clients.RegistrarNovedadCambioTipoNumeroDocumentoAfiliado;
import com.asopagos.novedades.composite.clients.RegistrarNovedadCambioTipoNumeroDocumentoBeneficiario;
import com.asopagos.novedades.composite.clients.RegistrarSupervivenciaPersona;
import com.asopagos.novedades.composite.clients.ValidarArchivoSupervivencia;
import com.asopagos.novedades.composite.dto.AnalizarSolicitudNovedadDTO;
import com.asopagos.novedades.composite.dto.AsignarSolicitudNovedadDTO;
import com.asopagos.novedades.composite.dto.ConsultarAnalisisNovedadDTO;
import com.asopagos.novedades.composite.dto.CorregirInformacionNovedadDTO;
import com.asopagos.novedades.composite.dto.GestionarPNCNovedadDTO;
import com.asopagos.novedades.composite.dto.ProcesarNovedadCargueArchivoDTO;
import com.asopagos.novedades.composite.dto.RegistrarRespuestaConfirmacionDTO;
import com.asopagos.novedades.composite.dto.VerificarGestionPNCNovedadDTO;
import com.asopagos.novedades.composite.dto.VerificarSolicitudNovedadDTO;
import com.asopagos.novedades.composite.service.NovedadAbstractFactory;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.composite.service.NovedadesCompositeService;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.composite.service.factories.ValidacionNovedadMasivaFactory;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.novedades.dto.DatosAfiliadoRetiroDTO;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosExcepcionNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.DatosNovedadRegistradaPersonaDTO;
import com.asopagos.novedades.dto.DatosNovedadVista360DTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.SucursalPersonaDTO;
import com.asopagos.novedades.fovis.clients.CrearActualizarListaSolicitudAnalisisNovedadFOVIS;
import com.asopagos.pagination.PaginationQueryParamsEnum;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.ConsultarUbicacion;
import com.asopagos.personas.clients.ConsultarUbicacionPersona;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.reportes.clients.ConsultarEstadoAportanteFecha;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.listaschequeorutines.guardarlistachequeo.GuardarListaChequeoRutine;
import com.asopagos.rutine.novedadescompositerutines.procesaractivacionbeneficiarioPILA.ProcesarActivacionBeneficiarioPILARutine;
import com.asopagos.rutine.novedadesrutines.actualizarestadosolicitudnovedad.ActualizarEstadoSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.confirmartransaccionnovedadpila.ConfirmarTransaccionNovedadPilaRutine;
import com.asopagos.rutine.novedadesrutines.crearintentonovedad.CrearIntentoNovedadRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedadEmpleador.CrearSolicitudNovedadEmpleadorRutine;
import com.asopagos.rutine.novedadesrutines.guardarexcepcionnovedad.GuardarExcepcionNovedadRutine;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.solicitudes.clients.ActualizarSolicitudEscalada;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.EscalarSolicitud;
import com.asopagos.solicitudes.clients.GuardarDocumentosAdminSolicitudes;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.ObtenerTareaActivaInstancia;
import com.asopagos.tareashumanas.clients.RetomarTarea;
import com.asopagos.tareashumanas.clients.SuspenderTarea;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.GestionMasivosEmpleador;
import com.asopagos.usuarios.clients.GestionMasivosPersona;
import com.asopagos.usuarios.clients.GestionMasivosCcf;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.PersonasUtils;
import com.asopagos.util.ValidacionDesafiliacionUtils;
import com.asopagos.validaciones.clients.ValidarCargaMultipleNovedades;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.novedades.EstadoGestionEnum;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.rutine.afiliadosrutines.deshacergestioncerotrabajadores.DeshacerGestionCeroTrabajadoresRutine;
//import com.asopagos.afiliaciones.personas.composite.clients.RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.MedioPagoPersona;
import com.asopagos.entidades.ccf.personas.MedioTransferencia;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedad.CrearSolicitudNovedadRutine;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.novedades.composite.service.constants.NamedQueriesConstants;

import javax.persistence.StoredProcedureQuery;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCartera;
import com.asopagos.dto.RolafiliadoNovedadAutomaticaDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.pila.clients.ConsultarDatosAfiliacionByRegistroDetallado;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import com.asopagos.usuarios.dto.UsuarioGestionDTO;
import com.asopagos.dto.cargaMultiple.UsuarioGestionDTO;
import com.asopagos.dto.ResultadoValidacionArchivoGestionUsuariosDTO;
import com.asopagos.novedades.composite.dto.ResolverNovedadDTO;
/** 
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con las novedades de empleadores o personas. <b>Historia de Usuario:</b>
 * proceso 1.3
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@Stateless
public class NovedadesCompositeBusiness implements NovedadesCompositeService {

    /**
     *
     */
    private static final String RADICAR = "RADICAR";
    /**
     * Constante con la clave para el número de identificación.
     */
    private static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
    /**
     * Constante con la clave para el tipo de identificación.
     */
    private static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    /**
     * Constante con la calve para requeriri inactivación web.
     */
    private static final String REQUIERE_INACTIVACION_WEB = "requiereInactivacionWeb";
    /**
     * Constante con la clave para la fecha de nacimiento.
     */
    private static final String FECHA_NACIMIENTO = "fechaNacimiento";
    /**
     * Constante con la clave para el segundo apellido.
     */
    private static final String SEGUNDO_APELLIDO = "segundoApellido";
    /**
     * Constnate con la clave para el primer apellido.
     */
    private static final String PRIMER_APELLIDO = "primerApellido";
    /**
     * Constante con la clave para el segundo nombre.
     */
    private static final String SEGUNDO_NOMBRE = "segundoNombre";
    /**
     * Constante con la clave para el primero nombre.
     */
    private static final String PRIMER_NOMBRE = "primerNombre";
    /**
     * Constante que indica el salario del afiliado
     */
    private static final String SALARIO = "salarioAfiliado";
    /**
     * Constante que indica el orientacionSexual del afiliado
     */
    private static final String ORIENTACION_SEXUAL = "orientacionSexual";
    /**
     * Constante que indica el factorDeVulnerabilidad del afiliado
     */
    private static final String FACTOR_DE_VULNERABILIDAD = "factorDeVulnerabilidad";
    /**
     * Constante que indica el estadoCivil del afiliado
     */
    private static final String ESTADO_CIVIL = "estadoCivil";
    /**
     * Constante que indica el/la pertenenciaEtnica del afiliado
     */
    private static final String PERTENENCIA_ETNICA = "pertenenciaEtnica";
    /**
     * Constante que indica el/la pais del afiliado
     */
    private static final String PAIS = "pais";
    /**
     * Constante que indica el celular del afiliado
     */
    private static final String CELULAR_AFILIADO_PRINCIPAL = "celular";
    /**
     * Constante que indica el telefono del afiliado
     */
    private static final String TELEFONO_AFILIADO_PRINCIPAL = "telefono";
    /**
     * Constante INTENTO.
     */
    private static final String INTENTO = "INTENTO";
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesCompositeBusiness.class);

    /**
     * Constante con la clave para la razon social
     */
    private static final String RAZON_SOCIAL = "razonSocial";
    /**
     * Representa el estado sin información de un aportante
     */
    private static final String SIN_INFORMACION = "SIN_INFORMACION";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String NATURALEZA_JURIDICA = "naturalezaJuridica";

    /**
     * Constante con la clave para la actividad economica
     */
    private static final String ACTIVIDAD_ECONOMICA = "actividadEconomica";
    /**
     * Constante con la clave para el departamento del empleador
     */
    private static final String DEPARTAMENTO = "idDepartamento";
    /**
     * Constante con la clave para municipio
     */
    private static final String MUNICIPIO = "municipio";
    /**
     * Constante con la clave para direccion
     */
    private static final String DIRECCION = "direccion";
    /**
     * Constante con la clave para email
     */
    private static final String EMAIL = "email";
    /**
     * Constante con la clave para genero
     */
    private static final String GENERO = "genero";
    /**
     * Constante con la clave para fechaExpedicion
     */
    private static final String FECHA_EXPEDICION = "fechaExpedicion";
    /**
     * Constante con la clave para nivelEducativo
     */
    private static final String NIVEL_EDUCATIVO = "nivelEducativo";
    /**
     * Constante con la clave para numeroEmpleados
     */
    private static final String NUMERO_TRABAJADORES = "numeroEmpleados";
    /**
     * Constante con la clave para fechaConstitucion
     */
    private static final String FECHA_CONSTITUCION = "fechaConstitucion";
    /**
     * Constante con la clave para el tipo de identificación del representante legal
     */
    private static final String TIPO_IDENTIFICACION_REPRESENTANTE = "tipoIdentificacionRepresentante";
    /**
     * Constante con la clave para el número de identificación del representante legal
     */
    private static final String NUMERO_IDENTIFICACION_REPRESENTANTE = "numeroIdentificacionRepresentante";
    /**
     * Constante con la clave para el EMAIL del representante legal
     */
    private static final String EMAIL_REPRESENTANTE = "correoElectronicoRepresentante";
    /**
     * Constante con la clave para la telefonoRepresentante
     */
    private static final String TELEFONO_REPRE = "telefonoRepresentante";

    /**
     * Constante con la clave para la oficina principal
     */
    private static final String EMAIL_1_OFICINA_PRINCIPAL = "email1OficinaPrincipal";
    /**
     * Constante con la clave para email 2 Envío correspondencia
     */
    private static final String EMAIL_2_ENVIO_DE_CORRESPONDENCIA = "email2EnvioDeCorrespondencia";
    /**
     * Constante con la clave para  email 3 notificación judicial
     */
    private static final String EMAIL_3_NOTIFICACION_JUDICIAL = "email3NotificacionJudicial";
    /**
     * Constante con la clave para telefono 1 oficina principal
     */
    private static final String TELEFONO_1_OFICINA_PRINCIPAL = "telefono1OficinaPrincipal";
    /**
     * Constante con la clave para telefono 2 envio de correspondencia
     */
    private static final String TELEFONO_2_ENVIO_DE_CORRESPONDENCIA = "telefono2EnvioDeCorrespondencia";
    /**
     * Constante con la clave para telefono 3 notificación judicial
     */
    private static final String TELEFONO_3_NOTIFICACION_JUDICIAL = "telefono3NotificacionJudicial";
    /**
     * Constante con la clave para celular de oficina principal
     */
    private static final String CELULAR_OFICINA_PRINCIPAL = "celularOficinaPrincipal";
    /**
     * Constante con la clave para responsable1DeLaCajaParaContacto
     */
    private static final String RESPONSABLE_1_DE_LA_CAJA_PARA_CONTACTO = "responsable1DeLaCajaParaContacto";
    /**
     * Constante con la clave para responsable2DeLaCajaParaContacto
     */
    private static final String RESPONSABLE_2_DE_LA_CAJA_PARA_CONTACTO = "responsable2DeLaCajaParaContacto";
    /**
     * Constante con la clave para medioDePagoSubsidioMonetario
     */
    private static final String MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO = "medioDePagoSubsidioMonetario";
    /**
     * Constante con la clave para tipoMedioDePago
     */
    private static final String TIPO_MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO = "tipoMedioDePago";
    /**
     * Constante con la clave para sitioPago
     */
    private static final String SITIO_DE_PAGO = "sitioPago";
    /**
     * Constante con la clave para tipoCuenta
     */
    private static final String TIPO_CUENTA = "tipoCuenta";
    /**
     * Constante con la clave para numeroCuenta
     */
    private static final String NUMERO_CUENTA = "numeroCuenta";
    /**
     * Constante con la clave para tipoIdentificacionTitular
     */
    private static final String TIPO_IDENTIFICACION_TITULAR = "tipoIdentificacionTitular";
    /**
     * Constante con la clave para numeroIdentificacionTitular
     */
    private static final String NUMERO_IDENTIFICACION_TITULAR = "numeroIdentificacionTitular";
    /**
     * Constante con la clave para nombreTitularCuenta
     */
    private static final String NOMBRE_TITULAR_CUENTA = "nombreTitularCuenta";
    /**
     * Constante con la clave para idBanco
     */
    private static final String NIT_BANCO = "nitBanco";

    /**
     * Constante CERRAR
     */
    private static final String CERRAR = "CERRAR";

    /**
     * Constante para el nombre del bloque de validaciones a aplicar en el
     * cargue multiple de novedades
     */
    private static final String BLOQUE_VALIDACION_CARGUE_MULTIPLE_NOVEDADES = "CARGA_MULTIPLE_NOVEDADES";

    /**
     * Indica si la tarea fue previamente suspendida
     */
    private Boolean tareaSuspendida = Boolean.FALSE;

    @Resource // (lookup="java:jboss/ee/concurrency/executor/novedades")
    private ManagedExecutorService managedExecutorService;

    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * radicarSolicitudNovedad(com.asopagos.novedades.dto.SolicitudNovedadDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudNovedadDTO radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO) {
        insercionMonitoreoLogs(  "Solicitud novedad: " + solNovedadDTO.getIdSolicitudNovedad()
        + " Estado solicitud: "+ solNovedadDTO.getEstadoSolicitudNovedad()
        + " ID Solicitud global: " + solNovedadDTO.getIdSolicitud()
        + " Resultado validación: " + solNovedadDTO.getResultadoValidacion(),"NovedadesCompositeBusinees-radicarSolicitudNovedad");
        logger.info("***********Inicia radicarSolicitudNovedad**********_JuanM");
        logger.info("SolicitudNovedadDTO***" + solNovedadDTO.toString());
        logger.info("userDTO***" + userDTO.toString());
        logger.info("TipoTransaccionEnum***" + solNovedadDTO.getTipoTransaccion().name());
        try {
            // Verificar si el tipo de transacción contiene "ACTIVAR_BENEFICIARIOS_MULTIPLES_"
            if (solNovedadDTO.getTipoTransaccion().name().contains("ACTIVAR_BENEFICIARIOS_MULTIPLES_")) {
                logger.info("Entra al if de ACTIVAR_BENEFICIARIOS_MULTIPLES_ " + solNovedadDTO.getTipoTransaccion().name());

                List<DatosPersonaNovedadDTO> datosPersonaList = solNovedadDTO.getDatosPersonaMultiple();
                logger.info("datosPersonaList " +datosPersonaList.toString());

                if (datosPersonaList != null && !datosPersonaList.isEmpty()) {
                    aplicarLogicaExistenteMultiple(solNovedadDTO, userDTO, datosPersonaList);
                    solNovedadDTO.setDatosPersona(null);
                    // for (DatosPersonaNovedadDTO persona : datosPersonaList) {
                    //     aplicarLogicaExistente(solNovedadDTO, userDTO, persona);
                    // }
                }
            } else {
                // Lógica para tipos de transacción existentes (una sola persona)
                aplicarLogicaExistente(solNovedadDTO, userDTO, solNovedadDTO.getDatosPersona());
            }

            logger.info("Fin de método radicarSolicitudNovedad((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");
            return solNovedadDTO;
        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error("Ocurrio un error inesperado en radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    // Método para aplicar la lógica existente a una persona
    private void aplicarLogicaExistente(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO, DatosPersonaNovedadDTO persona) {
        try{
            /* se busca la novedad seleccionada y se setea en el dto */
            logger.info("*****solNovedadDTO.getTipoTransaccion(): " + solNovedadDTO.getTipoTransaccion());
            // if (TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS.equals(solNovedadDTO.getTipoTransaccion()))
            //     actualizarPersonaDetalleFallecido(solNovedadDTO.getDatosPersona());

            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            logger.info("novedad " + novedad);
            logger.info("solNovedadDTO" + solNovedadDTO);
            if(solNovedadDTO.getNovedadDTO() != null && solNovedadDTO.getNovedadDTO().getPuntoResolucion() != null){
                novedad.setPuntoResolucion(solNovedadDTO.getNovedadDTO().getPuntoResolucion());
            }
            solNovedadDTO.setNovedadDTO(novedad);
            logger.info("**__**novedad.getNovedadDTO().getIdNovedad() " + solNovedadDTO.getNovedadDTO().getIdNovedad());
            logger.info("*****edwin toro" + solNovedadDTO.getExcepcionTipoDos());
            logger.info("*****edwin toro resultado validacion */*/*/" + solNovedadDTO.getResultadoValidacion());

            if (solNovedadDTO.getTipoTransaccion() == TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS) {
                solNovedadDTO.setContinuaProceso(true);
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
            }

            logger.info("**__**INICIA EXITOSO EN RESULTADO VALIDACION ");
            if ((persona != null
                    || solNovedadDTO.getDatosEmpleador() != null)
                    && (solNovedadDTO.getResultadoValidacion() == null || solNovedadDTO.getContinuaProceso() == null
                    || !solNovedadDTO.getContinuaProceso())) {
                /*
                * se verifica si cumple con las validaciones de la novedad, se excluye el caso
                * de una afiliacion de
                * empleador(se hacen las mismas validaciones que en la afiliacion)
                */
                solNovedadDTO.setResultadoValidacion(validarNovedad(solNovedadDTO));

                logger.info("**__**finaliza EL SERVICIO DE VALIDACIONES -> solNovedadDTO.getResultadoValidacion() -> " + solNovedadDTO.getResultadoValidacion());
            }
            // Se verifica el resultado de las validaciones es FALLIDA pero con validaciones
            // T1, se activa el paso a registro
            // solo para los registros internos
            if ((solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CARTERA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL)// Se agrega Enum Para
                    // prueba 49829
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR))
                    && !ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())
                    && (solNovedadDTO.getExcepcionTipoDos() != null && !solNovedadDTO.getExcepcionTipoDos())) {
                solNovedadDTO.setContinuaProceso(true);
                logger.info("**__**resultado de verificación de las validaciones => solNovedadDTO.getContinuaProceso() -> " + solNovedadDTO.getContinuaProceso());

            }
            if (consultarRetroactividadNovedades(solNovedadDTO.getIdRegistroDetallado())) {
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
            }
            /* si el resultado es exitoso se procede a radicar */
            if (ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())) {
                // GLPI 51688 ||
                // (solNovedadDTO.getContinuaProceso()!=
                // null &&
                // solNovedadDTO.getContinuaProceso())
                /* se crea la solicitud de novedad y asigna el nùmero de radicaciòn */
                solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                logger.info(
                        "*****_JuanM Antes radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO))"
                                + solNovedadDTO.toString());
                logger.info("***Validación anterior a radicación 1- ResultadoValidacion***"
                        + solNovedadDTO.getResultadoValidacion());
                logger.info("***Validación anterior a radicación 1- Continuar proceso***"
                        + solNovedadDTO.getContinuaProceso());
                logger.info("***Validación anterior a radicación 2***" + solNovedadDTO.getResultadoValidacion());

                logger.info("EDWIN TORO ENTRA SIN IF");

                /* se crea la solicitud de novedad y asigna el nùmero de radicaciòn */
                solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                logger.info("solNovedadDTO****--- Antes de crear la solicitud" + solNovedadDTO);
                logger.info("***Weizman => radicarSolicitudNovedad -> solNovedadDTO.getIdSolicitud() -> " + solNovedadDTO.getIdSolicitud());
                SolicitudNovedadModeloDTO solicitudNovedad;
                if (solNovedadDTO.getIdSolicitudCargueMasivo() != null) {
                    solicitudNovedad = consultarSolicitudNovedad(solNovedadDTO.getIdSolicitudCargueMasivo());
                } else {
                    solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
                }

                // Se verifica si la novedad es activiacion de beneficiario
                Boolean novedadActivaBenficiario = false;
                if (getListTransaccionActivarBeneficiario().contains(solNovedadDTO.getTipoTransaccion())) {
                    novedadActivaBenficiario = true;
                    // Se crea la referencia de la persona beneficiario a activar con los datos
                    // basicos
                    // Se verifica si la persona asociada existe, en caso de no existir se crea con
                    // los datos basicos
                    PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                    personaModeloDTO.setNumeroIdentificacion(persona.getNumeroIdentificacionBeneficiario());
                    personaModeloDTO.setTipoIdentificacion(persona.getTipoIdentificacionBeneficiario());
                    personaModeloDTO.setPrimerApellido(persona.getPrimerApellidoBeneficiario());
                    personaModeloDTO.setSegundoApellido(persona.getSegundoApellidoBeneficiario());
                    personaModeloDTO.setPrimerNombre(persona.getPrimerNombreBeneficiario());
                    personaModeloDTO.setSegundoNombre(persona.getSegundoNombreBeneficiario());
                    CrearPersona crearPersona = new CrearPersona(personaModeloDTO);
                    logger.info("Antes crear persona" + personaModeloDTO.toString());
                    crearPersona.execute();
                    Long idPersona = crearPersona.getResult();
                    if (idPersona != null) {
                        persona.setNumeroIdentificacionBeneficiarioAnterior(persona.getNumeroIdentificacionBeneficiario());
                        persona.setTipoIdentificacionBeneficiarioAnterior(persona.getTipoIdentificacionBeneficiario());
                    }
                }
                /* se guarda la lista de chequeo */
                ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
                listaChequeo.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
                if (persona != null) {
                    listaChequeo.setListaChequeo(persona.getListaChequeoNovedad());
                    // Se verifica si la novedad es asociada a un beneficiario o un afiliado
                    if ((persona.getIdBeneficiario() != null || novedadActivaBenficiario) &&
                            (!solNovedadDTO.getTipoTransaccion().name().equals("TRASLADO_BENEFICIARIO_GRUPO_FAMILIAR_AFILIADO_PERSONAS"))) {
                        listaChequeo.setNumeroIdentificacion(persona.getNumeroIdentificacionBeneficiarioAnterior());
                        listaChequeo.setTipoIdentificacion(persona.getTipoIdentificacionBeneficiarioAnterior());
                    } else {
                        listaChequeo.setNumeroIdentificacion(persona.getNumeroIdentificacion());
                        listaChequeo.setTipoIdentificacion(persona.getTipoIdentificacion());
                    }
                } else {
                    listaChequeo.setNumeroIdentificacion(solNovedadDTO.getDatosEmpleador().getNumeroIdentificacion());
                    listaChequeo.setTipoIdentificacion(solNovedadDTO.getDatosEmpleador().getTipoIdentificacion());
                    listaChequeo.setListaChequeo(solNovedadDTO.getDatosEmpleador().getListaChequeoNovedad());
                }
                if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
                    logger.info("**__**guardarListaChequeo: " + listaChequeo.toString());
                    guardarListaChequeo(listaChequeo);
                }

                if (PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CARTERA)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
                    logger.info("***Weizman => radicarSolicitudNovedad PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion()) TRUE");
                    /* si el punto de resolucion es el front se guarda la informacion */
                    // Verifica si es un reintegro por diferente empleador con el rolAfiliado

                    if (solNovedadDTO != null && persona != null
                            && persona.getIdRolAfiliado() == null
                            && solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO)) {
                        logger.info("Reintegro por diferente empleado" + solNovedadDTO);
                        // Se resuelve la novedad por medio de los convertidores
                        SolicitudNovedadModeloDTO solicitudNovedadRe = new SolicitudNovedadModeloDTO();
                        solicitudNovedadRe.setIdNovedad(solNovedadDTO.getIdSolicitud());
                        solicitudNovedadRe.setEstadoSolicitud(solNovedadDTO.getEstadoSolicitudNovedad());
                        solicitudNovedadRe.setIdSolicitudNovedad(solNovedadDTO.getIdSolicitudNovedad());
                        resolverNovedadReintegro(solNovedadDTO, solicitudNovedadRe, userDTO);
                    } else {
                        if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO)) {
                            SolicitudNovedadModeloDTO solicitudNovedadRe = new SolicitudNovedadModeloDTO();
                            solicitudNovedadRe.setIdNovedad(solNovedadDTO.getIdSolicitud());
                            solicitudNovedadRe.setEstadoSolicitud(solNovedadDTO.getEstadoSolicitudNovedad());
                            solicitudNovedadRe.setIdSolicitudNovedad(solNovedadDTO.getIdSolicitudNovedad());
                            NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
                            n.resolverNovedadReintegroMismoEmpleador(solNovedadDTO, solicitudNovedadRe, userDTO);
                        } else {
                            resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
                        }
                    }
                    if((solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL) ||
                        solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL)) &&
                        ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())) {
                        logger.info("**__**INICIA EL SERVICIO DE VALIDACIONES -> solNovedadDTO.getResultadoValidacion() -> " + solNovedadDTO.getResultadoValidacion());
                        ConsultarBeneficiariosPorIds consultarBeneficiarios = new ConsultarBeneficiariosPorIds(persona.getIdBeneficiarios());
                        consultarBeneficiarios.execute();
                        List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiarios.getResult();
                        logger.info("**__**listaBeneficiarios -> " + listaBeneficiarios.toString());
                        DatosNovedadCascadaDTO datosNovedadCascada = new DatosNovedadCascadaDTO();
                        datosNovedadCascada.setListaBeneficiario(listaBeneficiarios);
                        datosNovedadCascada.setNumeroRadicadoOriginal(solNovedadDTO.getNumeroRadicacion());
                        datosNovedadCascada.setTipoTransaccionOriginal(solNovedadDTO.getTipoTransaccion());
                        radicarSolicitudNovedadCascada(datosNovedadCascada, userDTO);
                    }

                } else if (PuntoResolucionEnum.BACK.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())) {
                    logger.info("LUIS 06 Reintegro por diferente empleado " + solNovedadDTO.getTipoTransaccion());
                    logger.info("***Weizman => radicarSolicitudNovedad PuntoResolucionEnum.BACK.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion()) TRUE");
                    /* si el punto de resolucion es el back se guarda temporalmente */
                    Long instancia = enviarNovedadBack(solNovedadDTO, solicitudNovedad, userDTO);
                    solNovedadDTO.setIdInstancia(instancia);
                }
                /*
                * novedades que se radican y son web o externas se envian el comunicado por
                * servicios
                */
                if (solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.WEB)
                        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                        || (solNovedadDTO.getCargaMultiple() != null && solNovedadDTO.getCargaMultiple())) {
                    parametrizarNotificacion(false, solNovedadDTO, userDTO);
                }
                if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO)) {
                    enviarComunicadoMasivo(solNovedadDTO, userDTO);
                }
                // Las novedades WEB con punto de resolucion FRONT se cierran al registro
                if (PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())
                        && solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.WEB)) {
                    cerrarSolicitudNovedad(solNovedadDTO, false);
                }
                if (solNovedadDTO.getTipoTransaccion() != TipoTransaccionEnum.NOVEDAD_REINTEGRO) {
                    logger.info("solNovedadDTO GUAR::" + new ObjectMapper().writeValueAsString(solNovedadDTO));
                    guardarDatosTemporal(solNovedadDTO);
                }
                // Se almacena los datos de la solicitud en datos temporales

            } // Se registra intento de novedad
            else if (solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
                /*
                * si las validaciones de negocio fallaron y el canal es web se guarda un
                * intento de novedad
                */
                logger.info(
                        "**__**RESULTADO VALIDACION DIFERENTE A EXITOSA: " + solNovedadDTO.getResultadoValidacion());
                IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
                intentoNovedadDTO.setCanalRecepcion(solNovedadDTO.getCanalRecepcion());
                intentoNovedadDTO.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.VALIDACION_REGLAS_NEGOCIO);
                intentoNovedadDTO.setClasificacion(solNovedadDTO.getClasificacion());
                intentoNovedadDTO.setTipoTransaccion(solNovedadDTO.getTipoTransaccion());
                intentoNovedadDTO.setIdRegistroDetallado(solNovedadDTO.getIdRegistroDetallado());
                if (solNovedadDTO.getDatosEmpleador() != null) {
                    intentoNovedadDTO.setIdEmpleador(solNovedadDTO.getDatosEmpleador().getIdEmpleador());
                } else {
                    intentoNovedadDTO.setTipoIdentificacion(persona.getTipoIdentificacion());
                    intentoNovedadDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());
                    intentoNovedadDTO.setIdBeneficiario(persona.getIdBeneficiario());
                    intentoNovedadDTO.setIdRolAfiliado(persona.getIdRolAfiliado());
                }
                solNovedadDTO.setIdSolicitud(registrarIntentoFallido(intentoNovedadDTO, userDTO));
                solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.RECHAZADA);
                /* se cierra la solicitud y se envia comunicado */
                parametrizarNotificacion(true, solNovedadDTO, userDTO);
                cerrarSolicitudNovedad(solNovedadDTO, true);
            }
            logger.info("*--- Validación de método getTarjetaMultiservicio---* " + solNovedadDTO.getTarjetaMultiservicio());
            //Se añade el pago solo si viene la marca TargetaMultiServicio
            if (solNovedadDTO.getTarjetaMultiservicio() != null && solNovedadDTO.getTarjetaMultiservicio()) {
                logger.info("*--- Inicio de método aggTargetaMultiServicio---* ");
                this.aggTargetaMultiServicio(solNovedadDTO);
            }
        }catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error("Ocurrio un error inesperado en aplicarLogicaExistente(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private void aplicarLogicaExistenteMultiple(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO, List<DatosPersonaNovedadDTO> datosPersonaList) {
        try {
            logger.info("*****solNovedadDTO.getTipoTransaccion() en aplicarLogicaExistenteMultiple: " + solNovedadDTO.getTipoTransaccion());

            // Se busca la novedad seleccionada y se setea en el DTO
            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            solNovedadDTO.setNovedadDTO(novedad);

            logger.info("**__**novedad.getNovedadDTO().getIdNovedad() en aplicarLogicaExistenteMultiple " + solNovedadDTO.getNovedadDTO().getIdNovedad());

            // Se verifica si la transacción requiere continuar automáticamente
            if (solNovedadDTO.getTipoTransaccion() == TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS) {
                solNovedadDTO.setContinuaProceso(true);
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
            }

            logger.info("**__**INICIA EXITOSO EN RESULTADO VALIDACION en aplicarLogicaExistenteMultiple");

            // Se procesan individualmente cada una de las personas en la lista


            if (solNovedadDTO.getDatosPersona() != null || solNovedadDTO.getDatosEmpleador() != null || (solNovedadDTO.getDatosPersonaMultiple() != null || !solNovedadDTO.getDatosPersonaMultiple().isEmpty())) {
                logger.info("solNovedadDTO" + solNovedadDTO.toString());
                solNovedadDTO.setResultadoValidacion(validarNovedad(solNovedadDTO));
                logger.info("**__**finaliza EL SERVICIO DE VALIDACIONES -> solNovedadDTO.getResultadoValidacion() -> " + solNovedadDTO.getResultadoValidacion());
            }

            // Se verifica el resultado de las validaciones es FALLIDA pero con validaciones
            // T1, se activa el paso a registro
            // solo para los registros internos
            if ((solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CARTERA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR))
                    && !ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())
                    && (solNovedadDTO.getExcepcionTipoDos() != null && !solNovedadDTO.getExcepcionTipoDos())) {
                solNovedadDTO.setContinuaProceso(true);
                logger.info("**__**resultado de verificación de las validaciones => solNovedadDTO.getContinuaProceso() - 89526 -> " + solNovedadDTO.getContinuaProceso());

            }

            if (consultarRetroactividadNovedades(solNovedadDTO.getIdRegistroDetallado())) {
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
            }


            // Solo se hace una solicitud de novedad después de procesar todos los datos de personas
            if (ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())
                    || (solNovedadDTO.getContinuaProceso() != null && solNovedadDTO.getContinuaProceso())) {
                solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                logger.info("*****Guillermo Antes de radicarSolicitudNovedad -> " + solNovedadDTO.toString());

                SolicitudNovedadModeloDTO solicitudNovedad;
                if (solNovedadDTO.getIdSolicitudCargueMasivo() != null) {
                    solicitudNovedad = consultarSolicitudNovedad(solNovedadDTO.getIdSolicitudCargueMasivo());
                } else {
                    solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
                }
                if (PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CARTERA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
                    logger.info("***Guillermo => radicarSolicitudNovedad PuntoResolucionEnum.FRONT.equals TRUE");
                    try {
                        resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
                    }catch (Exception e){

                    }
                } else if (PuntoResolucionEnum.BACK.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())) {
                    logger.info("***Guillermo => radicarSolicitudNovedad PuntoResolucionEnum.BACK.equals TRUE");
                    try {
                        Long instancia = enviarNovedadBack(solNovedadDTO, solicitudNovedad, userDTO);
                        solNovedadDTO.setIdInstancia(instancia);
                    }catch (Exception e){

                    }
                }
                                /*
                * novedades que se radican y son web o externas se envian el comunicado por
                * servicios
                */
                try {
                    if (solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.WEB)
                            || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                            || (solNovedadDTO.getCargaMultiple() != null && solNovedadDTO.getCargaMultiple())) {
                        parametrizarNotificacion(false, solNovedadDTO, userDTO);
                    }
                    // Las novedades WEB con punto de resolucion FRONT se cierran al registro
                    if (PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())
                            && solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.WEB)) {
                        cerrarSolicitudNovedad(solNovedadDTO, false);
                    }
                    if (solNovedadDTO.getTipoTransaccion() != TipoTransaccionEnum.NOVEDAD_REINTEGRO) {
                        guardarDatosTemporal(solNovedadDTO);
                    }
                }catch (Exception e){

                }
                // Se almacena los datos de la solicitud en datos temporales
            }// Se registra intento de novedad
            else if (solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
                /*
                 * si las validaciones de negocio fallaron y el canal es web se guarda un
                 * intento de novedad
                 */
                logger.info("**__**RESULTADO VALIDACION DIFERENTE A EXITOSA - 89526: " + solNovedadDTO.getResultadoValidacion());
                IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
                intentoNovedadDTO.setCanalRecepcion(solNovedadDTO.getCanalRecepcion());
                intentoNovedadDTO.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.VALIDACION_REGLAS_NEGOCIO);
                intentoNovedadDTO.setClasificacion(solNovedadDTO.getClasificacion());
                intentoNovedadDTO.setTipoTransaccion(solNovedadDTO.getTipoTransaccion());
                intentoNovedadDTO.setIdRegistroDetallado(solNovedadDTO.getIdRegistroDetallado());
                // if (solNovedadDTO.getDatosPersonaMultiple() != null) {
                //     intentoNovedadDTO.setTipoIdentificacion(solNovedadDTO.getTipoIdentificacion());
                //     intentoNovedadDTO.setNumeroIdentificacion(solNovedadDTO.getNumeroIdentificacion());
                //     intentoNovedadDTO.setIdBeneficiario(solNovedadDTO.getIdBeneficiario());
                //     intentoNovedadDTO.setIdRolAfiliado(solNovedadDTO.getIdRolAfiliado());
                // }
                solNovedadDTO.setIdSolicitud(registrarIntentoFallido(intentoNovedadDTO, userDTO));
                solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.RECHAZADA);
                /* se cierra la solicitud y se envia comunicado */
                parametrizarNotificacion(true, solNovedadDTO, userDTO);
                cerrarSolicitudNovedad(solNovedadDTO, true);
            }
            logger.info("datosPersonaList " + datosPersonaList.size());
            for (DatosPersonaNovedadDTO persona : datosPersonaList) {
                // Si la novedad es activación de beneficiario, se maneja la creación de la persona
                Boolean novedadActivaBeneficiario = false;
                if (getListTransaccionActivarBeneficiario().contains(solNovedadDTO.getTipoTransaccion())) {
                    logger.info("Entra a este if GLPI 89526 - Guillermo");
                    novedadActivaBeneficiario = true;

                    PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO();
                    personaModeloDTO.setNumeroIdentificacion(persona.getNumeroIdentificacionBeneficiario());
                    personaModeloDTO.setTipoIdentificacion(persona.getTipoIdentificacionBeneficiario());
                    personaModeloDTO.setPrimerApellido(persona.getPrimerApellidoBeneficiario());
                    personaModeloDTO.setSegundoApellido(persona.getSegundoApellidoBeneficiario());
                    personaModeloDTO.setPrimerNombre(persona.getPrimerNombreBeneficiario());
                    personaModeloDTO.setSegundoNombre(persona.getSegundoNombreBeneficiario());

                    CrearPersona crearPersona = new CrearPersona(personaModeloDTO);
                    logger.info("Antes de crear persona: " + personaModeloDTO.toString());
                    crearPersona.execute();
                    Long idPersona = crearPersona.getResult();
                    if (idPersona != null) {
                        persona.setNumeroIdentificacionBeneficiarioAnterior(persona.getNumeroIdentificacionBeneficiario());
                        persona.setTipoIdentificacionBeneficiarioAnterior(persona.getTipoIdentificacionBeneficiario());
                    }
                }

//                logger.info("datosPersonaList --> n " + persona.getNumeroIdentificacion());
//                // Se guarda la lista de chequeo
//                ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
//                listaChequeo.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
//                if (persona != null) {
//                    logger.info("persona.getListaChequeoNovedad() " + persona.getListaChequeoNovedad().toString());
//                    listaChequeo.setListaChequeo(persona.getListaChequeoNovedad());
//                    listaChequeo.setNumeroIdentificacion(persona.getNumeroIdentificacion());
//                    listaChequeo.setTipoIdentificacion(persona.getTipoIdentificacion());
//                } else {
//                    listaChequeo.setNumeroIdentificacion(solNovedadDTO.getDatosEmpleador().getNumeroIdentificacion());
//                    listaChequeo.setTipoIdentificacion(solNovedadDTO.getDatosEmpleador().getTipoIdentificacion());
//                    listaChequeo.setListaChequeo(solNovedadDTO.getDatosEmpleador().getListaChequeoNovedad());
//                }
//                if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
//                    logger.info("**__**guardarListaChequeo: " + listaChequeo.toString());
//                    guardarListaChequeo(listaChequeo);
//                }
            }

            logger.info("*--- Validación de método getTarjetaMultiservicio---* " + solNovedadDTO.getTarjetaMultiservicio());
            //Se añade el pago solo si viene la marca TargetaMultiServicio
            if (solNovedadDTO.getTarjetaMultiservicio() != null && solNovedadDTO.getTarjetaMultiservicio()) {
                logger.info("*--- Inicio de método aggTargetaMultiServicio---* ");
                this.aggTargetaMultiServicio(solNovedadDTO);
            }

        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error("Ocurrio un error inesperado en aplicarLogicaExistenteMultiple(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public void actualizarPersonaDetalleFallecido(DatosPersonaNovedadDTO datosPersonaNovedadDTO) {
        try {
            logger.info("Inicia metodo actulalizarPersonaDetalle");
            ActualizarPersonaDetalleFallecido service = new ActualizarPersonaDetalleFallecido(datosPersonaNovedadDTO.getNumeroIdentificacion(),
                    datosPersonaNovedadDTO.getTipoIdentificacion(), mapperPersonaDetalle(datosPersonaNovedadDTO));
            service.execute();
            logger.info("Finaliza metodo actulalizarPersonaDetalle");
        } catch (Exception e) {
            logger.error("Error actulalizarPersonaDetalle", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private PersonaDetalleModeloDTO mapperPersonaDetalle(DatosPersonaNovedadDTO datosPersonaNovedadDTO) {
        PersonaDetalleModeloDTO personaDetalleModeloDTO = new PersonaDetalleModeloDTO();
        personaDetalleModeloDTO.setFallecido(datosPersonaNovedadDTO.getPersonaFallecidaTrabajador());
        return personaDetalleModeloDTO;
    }


    private void aggTargetaMultiServicio(SolicitudNovedadDTO solNovedadDTO) {
        logger.info("*--- Inicio de método aggTargetaMultiServicio---* ");
        String idGrupoFamiliar = null;
        if (solNovedadDTO.getDatosPersona().getIdGrupoFamiliar() != null && !solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().equals("")) {
            idGrupoFamiliar = solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().toString();
            logger.info("*--- Inicio de método aggTargetaMultiServicio---* idGrupoFamiliar 1 " + idGrupoFamiliar);
        } else if (solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario() != null
                && solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar() != null
                && !solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().equals("")) {

            idGrupoFamiliar = solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().toString();
            logger.info("*--- Inicio de método aggTargetaMultiServicio---* idGrupoFamiliar 2 " + idGrupoFamiliar);


        }
        logger.info("*--- Inicio de método aggTargetaMultiServicio---* grupoFamiliar 0 " + idGrupoFamiliar);
        if(idGrupoFamiliar != null){
            GrupoFamiliar grupoFamiliar = (GrupoFamiliar) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR)
                    .setParameter("idGrupoFamiliar",  Long.parseLong(idGrupoFamiliar))
                    .getSingleResult();
            logger.info("*--- Inicio de método aggTargetaMultiServicio---* grupoFamiliar 1 ");
            if(grupoFamiliar != null){
                logger.info("*--- Inicio de método aggTargetaMultiServicio---* grupoFamiliar 2 " + grupoFamiliar.getIdGrupoFamiliar());
                grupoFamiliar.setTarjetaMultiservicio(solNovedadDTO.getTarjetaMultiservicio());
                entityManager.merge(grupoFamiliar);
            }
        }else{
            logger.info("*--- Inicio de método aggTargetaMultiServicio - SIN grupoFamiliar  ");
            logger.info("*--- Inicio de método aggTargetaMultiServicio - SIN grupoFamiliar  " + solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
            logger.info("*--- Inicio de método aggTargetaMultiServicio - SIN grupoFamiliar 0  " + solNovedadDTO.getDatosPersona().getTipoIdentificacion());
            Persona persona = (Persona) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION)
                    .setParameter("numeroIdentificacion",  solNovedadDTO.getDatosPersona().getNumeroIdentificacion())
                    .setParameter("tipoIdentificacion", solNovedadDTO.getDatosPersona().getTipoIdentificacion())
                    .getSingleResult();

            logger.info("*--- Inicio de método aggTargetaMultiServicio - SIN grupoFamiliar  id Per " + persona.getIdPersona());
            MedioPagoPersona medioPagoPersona = (MedioPagoPersona) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_MEDIO_PAGO_PERSONA_ID)
                    .setParameter("idPersona",  persona.getIdPersona())
                    .getSingleResult();
            logger.info("*--- Inicio de método aggTargetaMultiServicio - SIN grupoFamiliar  id Medio " + medioPagoPersona.getIdMedioDePago());
            medioPagoPersona.setMppTarjetaMultiservicio(solNovedadDTO.getTarjetaMultiservicio());
            entityManager.merge(medioPagoPersona);

        }

    }

    /**
     * Método encargado de registrar un intento de novedad.
     *
     * @param intentoNovedadDTO intento de novedad.
     * @return id de la solicitud de novedad.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long registrarIntentoFallido(IntentoNovedadDTO intentoNovedadDTO, UserDTO userDTO) {

        ParametrizacionNovedadModeloDTO novedad = null;
        //   if (!TipoTransaccionEnum.NOVEDAD_REINTEGRO.equals(intentoNovedadDTO.getTipoTransaccion())) {
        novedad = consultarNovedad(intentoNovedadDTO.getTipoTransaccion());
        //}

        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
        solNovedadDTO.setCanalRecepcion(intentoNovedadDTO.getCanalRecepcion());
        solNovedadDTO.setClasificacion(intentoNovedadDTO.getClasificacion());
        solNovedadDTO.setTipoTransaccion(intentoNovedadDTO.getTipoTransaccion());
        solNovedadDTO.setNovedadDTO(novedad);
        solNovedadDTO.setIdRegistroDetallado(intentoNovedadDTO.getIdRegistroDetallado());
        // agregado para controlar las novedades reprocesadas de pila
        solNovedadDTO.setIdRegistroDetalladoNovedad(intentoNovedadDTO.getIdRegistroDetalladoNovedad());
        if (intentoNovedadDTO.getIdEmpleador() != null) {
            DatosEmpleadorNovedadDTO empleador = new DatosEmpleadorNovedadDTO();
            empleador.setIdEmpleador(intentoNovedadDTO.getIdEmpleador());
            solNovedadDTO.setDatosEmpleador(empleador);
        } else {
            DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
            persona.setTipoIdentificacion(intentoNovedadDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacion(intentoNovedadDTO.getNumeroIdentificacion());
            persona.setIdBeneficiario(intentoNovedadDTO.getIdBeneficiario());
            persona.setIdRolAfiliado(intentoNovedadDTO.getIdRolAfiliado());
            solNovedadDTO.setDatosPersona(persona);
        }
        logger.info("**__**registrarIntentoFallido intentoNovedadDTO.getIdRolAfiliado(): "
                + intentoNovedadDTO.getIdRolAfiliado());
        /* se crea la solicitud de novedad y asigna el número de radicación */
        solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.RECHAZADA);
        SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
        intentoNovedadDTO.setIdSolicitud(solicitudNovedad.getIdSolicitud());

        /* se crea el intento de novedad */
        crearIntentoNovedad(intentoNovedadDTO);
        logger.info("**__**registrarIntentoFallido solicitudNovedad.getIdSolicitud(): "
                + solicitudNovedad.getIdSolicitud());
        /* se rechaza y cierra */
        actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.RECHAZADA);
        if (intentoNovedadDTO.getExcepcionTipoDos() == null) {
            intentoNovedadDTO.setExcepcionTipoDos(Boolean.FALSE);
        }
        if ((CanalRecepcionEnum.WEB.equals(intentoNovedadDTO.getCanalRecepcion())
                && !intentoNovedadDTO.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB))
                || (intentoNovedadDTO.getExcepcionTipoDos() && intentoNovedadDTO.getTipoTransaccion().getProceso()
                .equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB))) {
            /* se cierra la solicitud y se envia comunicado */
            parametrizarNotificacion(true, solNovedadDTO, userDTO);
            cerrarSolicitudNovedad(solNovedadDTO, true);
        } else {
            // Se cierra la solicitud deespues de rechazarla
            actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
        }
        return solicitudNovedad.getIdSolicitud();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * asignarSolicitudNovedad(com.asopagos.novedades.composite.dto.
     * AsignarSolicitudNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void asignarSolicitudNovedad(AsignarSolicitudNovedadDTO entrada, UserDTO userDTO) {
        String destinatario = null;
        String sedeDestinatario = null;
        String observacion = null;
        Long idTarea;
        UsuarioDTO usuarioDTO = new UsuarioCCF();
        logger.info("<<dmorales>> asignarSolicitudNovedad line 614 - UserDTO:  " + userDTO.toString());
        if (MetodoAsignacionBackEnum.AUTOMATICO.equals(entrada.getMetodoAsignacion())
                || entrada.getTipoTransaccion().getProceso().getWeb()) {
            destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                    entrada.getTipoTransaccion().getProceso());
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
        }else{
            destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                    entrada.getTipoTransaccion().getProceso());
            logger.info("primer if asignarSolicitudNovedad - destinatario:  " + destinatario);
            usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            sedeDestinatario = usuarioDTO.getCodigoSede();
            observacion = null;
        }

        /* se actualiza la solicitud de novedad */
        SolicitudNovedadModeloDTO solicitudNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
        solicitudNovedad.setDestinatario(destinatario);
        solicitudNovedad.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
        solicitudNovedad.setObservacion(observacion);
        if (entrada.getDocumentosFisicos()) {
            solicitudNovedad.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
        }
        actualizarSolicitudNovedad(solicitudNovedad);
        /* se cambia el estado de la soliciutd de acuerdo a los documentos. */
        if (entrada.getDocumentosFisicos()) {
            actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                    EstadoSolicitudNovedadEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS);
        } else {
            actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.ASIGNADA_AL_BACK);
        }

        Map<String, Object> params = null;
        params = new HashMap<>();
        params.put("usuarioBack", destinatario);
        params.put("documentosFisicos", entrada.getDocumentosFisicos());

        try {
            idTarea = entrada.getIdTarea();
            if (idTarea == null) {
                idTarea = consultarTareaActiva(new Long(solicitudNovedad.getIdInstanciaProceso()));
            }
            terminarTarea(idTarea, params);
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * verificarSolicitudNovedad(com.asopagos.novedades.composite.dto.
     * VerificarSolicitudNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("resultadoVerificacionBack", entrada.getResultadoVerificacionBack());
            SolicitudNovedadModeloDTO solicNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
            switch (entrada.getResultadoVerificacionBack()) {
                case 1:
                    /* se realizó un producto no conforme */
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                            EstadoSolicitudNovedadEnum.NO_CONFORME_SUBSANABLE);
                    break;
                case 2:
                    /* se escaló la solicitud */
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.ESCALADA);
                    escalarSolicitud(entrada.getEscalamientoSolicitud().getIdSolicitud(),
                            entrada.getEscalamientoSolicitud(), userDTO);
                    params.put("destinatarioEscalamiento", entrada.getEscalamientoSolicitud().getDestinatario());
                    break;
                case 3:
                    /*
                     * se da como resuelta la solicitud, pero si es una desariliación queda
                     * esperando
                     */
                    if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                        actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                                EstadoSolicitudNovedadEnum.ESPERANDO_CONFIRMACION_RETIRO);
                    } else {
                        logger.info("ingresa a resolver novedad");
                        SolicitudNovedadDTO solicitud = consultarSolicitudNovedadTemporal(entrada.getIdSolicitud());
                        logger.info("solicitud-->>> " + solicitud.toString());
                        logger.info("solicitud ruta-->>> " + solicitud.getNovedadDTO().getRutaCualificada());
                        resolverNovedad(solicitud, solicNovedad, userDTO);
                    }
                    break;
                case 4:
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.RECHAZADA);
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                    break;
            }

            if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                params.put("esNovedadRetiro", Boolean.TRUE);
            } else {
                params.put("esNovedadRetiro", Boolean.FALSE);
            }
            // Evalua si previamente se ha suspendido la tarea, a fin de retomarla para
            // posterior cierre
           if (tareaSuspendida) {
               retomarTarea(entrada.getIdTarea());
           }
           terminarTarea(entrada.getIdTarea(), params);
       } catch (BPMSExecutionException e) {
           logger.error(e);
           throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                   e);
        } catch (Exception e) {
            logger.error(
                    "Ocurrio un error inesperado en verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * gestionarPNCNovedad(com.asopagos.novedades.composite.dto.
     * GestionarPNCNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void gestionarPNCNovedad(GestionarPNCNovedadDTO entrada, UserDTO userDTO) {

        if (entrada.getIdTarea() != null) {
            actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                    EstadoSolicitudNovedadEnum.NO_CONFORME_GESTIONADA);
            terminarTarea(entrada.getIdTarea(), null);
        } else {
            CorregirInformacionNovedadDTO correcionNovedad = new CorregirInformacionNovedadDTO();
            correcionNovedad.setIdSolicitud(entrada.getIdSolicitud());
            correcionNovedad.setTipoTransaccionEnum(entrada.getTipoTransaccion());
            corregirInformacionNovedad(correcionNovedad, userDTO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * verificarGestionPNCNovedad(com.asopagos.novedades.composite.dto.
     * VerificarGestionPNCNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarGestionPNCNovedad(VerificarGestionPNCNovedadDTO entrada, UserDTO userDTO) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("resultadoVerifPNC", entrada.getResultadoVerifPNC());
            SolicitudNovedadModeloDTO solicNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
            switch (entrada.getResultadoVerifPNC()) {
                case 1:
                    if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                        actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                                EstadoSolicitudNovedadEnum.ESPERANDO_CONFIRMACION_RETIRO);
                    } else {
                        SolicitudNovedadDTO solicitud = consultarSolicitudNovedadTemporal(entrada.getIdSolicitud());
                        resolverNovedad(solicitud, solicNovedad, userDTO);
                    }
                    break;
                case 2:
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.RECHAZADA);
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                    break;
                case 3:
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.ESCALADA);
                    escalarSolicitud(entrada.getIdSolicitud(),
                            entrada.getEscalamientoSolicitud(), userDTO);
                    params.put("destinatarioEscalamiento", entrada.getEscalamientoSolicitud().getDestinatario());
                    break;

            }
            if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                params.put("esNovedadRetiro", Boolean.TRUE);
            } else {
                params.put("esNovedadRetiro", Boolean.FALSE);
            }
            terminarTarea(entrada.getIdTarea(), params);

        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error(
                    "Ocurrio un error inesperado en verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * analizarSolicitudNovedad(com.asopagos.novedades.composite.dto.
     * AnalizarSolicitudNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void analizarSolicitudNovedad(AnalizarSolicitudNovedadDTO entrada, UserDTO userDTO) {

        SolicitudNovedadModeloDTO solicNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
        ActualizarSolicitudEscalada actualizarSolicitudEscalada = new ActualizarSolicitudEscalada(
                solicNovedad.getIdSolicitud(), entrada.getEscalamientoSolicitud());
        actualizarSolicitudEscalada.execute();

        actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                EstadoSolicitudNovedadEnum.GESTIONADA_POR_ESPECIALISTA);

        terminarTarea(entrada.getIdTarea(), null);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * consultarAnalisisNovedad(com.asopagos.novedades.composite.dto.
     * ConsultarAnalisisNovedadDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void consultarAnalisisNovedad(ConsultarAnalisisNovedadDTO entrada, UserDTO userDTO) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("resultadoAnalisisEsp", entrada.getResultadoAnalisisEsp());
            SolicitudNovedadModeloDTO solicNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
            switch (entrada.getResultadoAnalisisEsp()) {
                case 1:
                    if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                        actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(),
                                EstadoSolicitudNovedadEnum.ESPERANDO_CONFIRMACION_RETIRO);
                    } else {
                        SolicitudNovedadDTO solicitud = consultarSolicitudNovedadTemporal(entrada.getIdSolicitud());
                        resolverNovedad(solicitud, solicNovedad, userDTO);
                    }
                    break;
                case 2:
                    /* Se rechaza y cierra la solicitud de novedad */
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.RECHAZADA);
                    actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                    break;
            }
            if (obtenerListaRetiros().contains(solicNovedad.getTipoTransaccion())) {
                params.put("esNovedadRetiro", Boolean.TRUE);
            } else {
                params.put("esNovedadRetiro", Boolean.FALSE);
            }
            terminarTarea(entrada.getIdTarea(), params);
        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error(
                    "Ocurrio un error inesperado en verificarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * registrarRespuestaConfirmacion(com.asopagos.novedades.composite.dto.
     * RegistrarRespuestaConfirmacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void registrarRespuestaConfirmacion(RegistrarRespuestaConfirmacionDTO entrada, UserDTO userDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoConfirmacion", entrada.getResultadoConfirmacion());
        SolicitudNovedadModeloDTO solicitudNovedad = consultarSolicitudNovedad(entrada.getIdSolicitud());
        SolicitudNovedadDTO solicitudTemporal = consultarSolicitudNovedadTemporal(entrada.getIdSolicitud());
        if (entrada.getIdDocumento() != null && entrada.getNombreDocumento() != null
                && !entrada.getIdDocumento().equals("") && !entrada.getNombreDocumento().equals("")) {
            DocumentoAdministracionEstadoSolicitudDTO documento = new DocumentoAdministracionEstadoSolicitudDTO();
            documento.setIdentificadorDocumentoSoporteCambioEstado(entrada.getIdDocumento());
            documento.setNombreDocumento(entrada.getNombreDocumento());
            guardarDocumentosAdminSolicitudes(solicitudNovedad.getNumeroRadicacion(), documento);
        }
        switch (entrada.getResultadoConfirmacion()) {
            case 1:
                try {
                    resolverNovedad(solicitudTemporal, solicitudNovedad, userDTO);
                } catch (Exception e) {
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                }
                break;
            case 2:
                actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.DESISTIDA);
                actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                break;
        }
        try {
            terminarTarea(entrada.getIdTarea(), params);
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * registrarRespuestaConfirmacion(com.asopagos.novedades.composite.dto.
     * RegistrarRespuestaConfirmacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void corregirInformacionNovedad(CorregirInformacionNovedadDTO entrada, UserDTO userDTO) {
        SolicitudNovedadModeloDTO sn = consultarSolicitudNovedad(entrada.getIdSolicitud());
        actualizarEstadoSolicitudNovedad(entrada.getIdSolicitud(), EstadoSolicitudNovedadEnum.NO_CONFORME_GESTIONADA);
        try {
            EnviarSenal enviarSenal = new EnviarSenal(entrada.getTipoTransaccionEnum().getProceso(),
                    "informacionCorregida",
                    Long.valueOf(sn.getIdInstanciaProceso()), null);
            enviarSenal.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * consultarSolicitudNovedadTemporal(java.lang.Long)
     */
    @Override
    public SolicitudNovedadDTO consultarSolicitudNovedadTemporal(Long idsolicitud) {
        logger.debug("Inicio de método consultarSolicitudNovedadTemporal");
        try {
            String jsonPayload = new String();
            ConsultarDatosTemporales consultarDatosNovedad = new ConsultarDatosTemporales(idsolicitud);
            consultarDatosNovedad.execute();
            jsonPayload = (String) consultarDatosNovedad.getResult();
            ObjectMapper mapper = new ObjectMapper();
            SolicitudNovedadDTO solicitudNovedadDTO = mapper.readValue(jsonPayload, SolicitudNovedadDTO.class);
            logger.debug("Fin de método consultarSolicitudNovedadTemporal");
            return solicitudNovedadDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error transformando el json en un objeto solicitudNovedadDTO", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONVERTIR_JSON);
        }
    }

    @Override
    public SolicitudNovedadDTO radicarSolicitudNovedadMasiva(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO) {
        try {
            logger.info("radicarSolicitudNovedadMasiva(SolicitudNovedadDTO, UserDTO)");
            // Se busca la novedad seleccionada y se setea en el dto
            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            solNovedadDTO.setNovedadDTO(novedad);
            // Datos de la novedad
            solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
            // Datos de la novedad automatica
            DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO = null;
            // Se verifica si se requiere validación
            if (solNovedadDTO.getDatosNovedadMasiva() != null) {
                datosNovedadAutomaticaDTO = solNovedadDTO.getDatosNovedadMasiva();
            } else {
                // Invoca el componente de validación masiva
                ValidacionMasivaCore validacionMasivaCore = ValidacionNovedadMasivaFactory.getInstance()
                        .obtenerSercioNovedad(solNovedadDTO.getTipoTransaccion());
                

                    if(solNovedadDTO.getObservaciones() != null && solNovedadDTO.getObservaciones().equals("sinCertificado")){
                        datosNovedadAutomaticaDTO = validacionMasivaCore.validar(solNovedadDTO.getObservaciones());
                        solNovedadDTO.setObservaciones(null);
                    }else if (validacionMasivaCore.validar() != null){
                        datosNovedadAutomaticaDTO = validacionMasivaCore.validar();
                    }
                    

            }
            logger.info("Datos para novedades automaticas "+datosNovedadAutomaticaDTO);

            if (datosNovedadAutomaticaDTO.getIdPersonaAfiliados() != null && !datosNovedadAutomaticaDTO.getIdPersonaAfiliados().isEmpty()) {
                logger.info("Lista de ID Persona Afiliados:");
                for (Long idPersona : datosNovedadAutomaticaDTO.getIdPersonaAfiliados()) {
                    logger.info(" - ID Persona: " + idPersona);
                }
            }
            if (datosNovedadAutomaticaDTO.getListaPersonasRetiro() != null && !datosNovedadAutomaticaDTO.getListaPersonasRetiro().isEmpty()) {
                logger.info("Lista de ID Persona Afiliados Retirados:");
                for (PersonaRetiroNovedadAutomaticaDTO retiroautomaticoNovedadDTO : datosNovedadAutomaticaDTO.getListaPersonasRetiro()) {
                    logger.info(" - Persona tipo: " + retiroautomaticoNovedadDTO.getTipoAfiliadoEnum());
                }
            }


            // Se tiene que revisar la lista de personas de retiro en esta se esta trayendo el tipo del trabajador
            // Si se obtuvieron datos de novedad se ejecuta la novedad
            if (datosNovedadAutomaticaDTO != null) {
                // Se realiza el registro de novedad
                SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
                // Se verifica el proceso para el registro de novedad especifico

                Solicitud solicitud = solicitudNovedad.convertToSolicitudEntity();
                if (ProcesoEnum.NOVEDADES_EMPRESAS_PRESENCIAL.equals(solNovedadDTO.getTipoTransaccion().getProceso())) {
                    if (datosNovedadAutomaticaDTO.getIdEmpleadores() != null
                            && !datosNovedadAutomaticaDTO.getIdEmpleadores().isEmpty()) {
                        // Almacena en modo batch los empleadores asociados en la solicitud de novedad
                        logger.info(
                                "Inicia AlmacenarSolicitudNovedadMasiva ->radicarSolicitudNovedadMasiva getIdSolicitudNovedad: "
                                        + solicitudNovedad.getIdSolicitudNovedad()
                                        + " datosNovedadAutomaticaDTO.getIdEmpleadores() "
                                        + datosNovedadAutomaticaDTO.getIdEmpleadores());
                        // AlmacenarSolicitudNovedadMasiva almacenarSolicitudNovedadMasiva = new
                        // AlmacenarSolicitudNovedadMasiva(
                        // solicitudNovedad.getIdSolicitudNovedad(),
                        // datosNovedadAutomaticaDTO.getIdEmpleadores());
                        // almacenarSolicitudNovedadMasiva.execute();

                        for (Long idEmpleador : datosNovedadAutomaticaDTO.getIdEmpleadores()) {
                            SolicitudNovedadEmpleador solicitudNovedadEmpleador = new SolicitudNovedadEmpleador();
                            solicitudNovedadEmpleador.setIdEmpleador(idEmpleador);
                            solicitudNovedadEmpleador.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
                            entityManager.persist(solicitudNovedadEmpleador);
                        }
                        logger.info("Fin AlmacenarSolicitudNovedadMasiva ->radicarSolicitudNovedadMasiva");
                        // Se asignan los identificadores de empleadores a gestionar
                        DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO = new DatosEmpleadorNovedadDTO();
                        datosEmpleadorNovedadDTO.setIdEmpleadoresPersona(datosNovedadAutomaticaDTO.getIdEmpleadores());
                        solNovedadDTO.setDatosEmpleador(datosEmpleadorNovedadDTO);
                    }
                } else {
                    logger.info("**__**else ->radicarSolicitudNovedadMasiva");
                    // Almacena en modo batch los afiliados asociados a la solicitud
                    /*
                     * AlmacenarSolicitudNovedadPersonaMasivo almacenarSolicitudNovedadPersona = new
                     * AlmacenarSolicitudNovedadPersonaMasivo(
                     * solicitudNovedad.getIdSolicitudNovedad(), datosNovedadAutomaticaDTO);
                     * almacenarSolicitudNovedadPersona.execute();
                     */
                    /**
                     * ALMACENARVODEDADPERSONA
                     */
                    try {
                        /*
                         * Se persiste en Batch las solicitudesNovedadAfiliado Configuración
                         * actual: hibernate.jdbc.batch_size value = 500
                         */
                        if (datosNovedadAutomaticaDTO.getIdPersonaAfiliados() != null
                                && !datosNovedadAutomaticaDTO.getIdPersonaAfiliados().isEmpty()) {
                            for (Long idPersona : datosNovedadAutomaticaDTO.getIdPersonaAfiliados()) {
                                SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
                                solicitudNovedadPersona.setIdPersona(idPersona);
                                solicitudNovedadPersona.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
                                logger.info(
                                        "**__** entityManager.persist(solicitudNovedadPersona ->radicarSolicitudNovedadMasiva");
                                entityManager.persist(solicitudNovedadPersona);
                                logger.info(
                                        "**__**finentityManager.persist(solicitudNovedadPersona ->radicarSolicitudNovedadMasiva");
                                // logger.debug("IF 1 persona : " + idPersona + " solicitud : " +
                                // solicitudNovedad.getIdSolicitudNovedad());
                            }
                        } else if (datosNovedadAutomaticaDTO.getListaBeneficiarios() != null
                                && !datosNovedadAutomaticaDTO.getListaBeneficiarios().isEmpty()) {
                            // datosNovedadAutomaticaDTO.setListaBeneficiarios(datosNovedadAutomaticaDTO.getListaBeneficiarios().subList(0,
                            // 15));
                            for (BeneficiarioNovedadAutomaticaDTO beneficiarioNovedadAutomaticaDTO : datosNovedadAutomaticaDTO
                                    .getListaBeneficiarios()) {
                                SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
                                solicitudNovedadPersona
                                        .setIdPersona(beneficiarioNovedadAutomaticaDTO.getIdPersonaAfiliado());
                                solicitudNovedadPersona
                                        .setIdBeneficiario(beneficiarioNovedadAutomaticaDTO.getIdBeneficiario());
                                solicitudNovedadPersona.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
                                // idsBeneficiarioparte1=idsBeneficiario.subList(0, idsBeneficiario.size()/2);
                                // solicitudNovedadPersona=solicitudNovedadPersona.subList(0, 15);
                          
                                entityManager.persist(solicitudNovedadPersona);
                             
                                // logger.info("IF 2 persona : " +
                                // beneficiarioNovedadAutomaticaDTO.getIdPersonaAfiliado() + " solicitud : " +
                                // solicitudNovedad.getIdSolicitudNovedad() + " beneficiario : " +
                                // beneficiarioNovedadAutomaticaDTO.getIdBeneficiario());
                            }
                        } else if (datosNovedadAutomaticaDTO.getListaRolafiliados() != null && !datosNovedadAutomaticaDTO.getListaRolafiliados().isEmpty()) {
                            for (RolafiliadoNovedadAutomaticaDTO rolafiliado : datosNovedadAutomaticaDTO.getListaRolafiliados()) {
                                SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
                                solicitudNovedadPersona.setIdPersona(rolafiliado.getIdPersonaAfiliado());
                                solicitudNovedadPersona.setIdRolAfiliado(rolafiliado.getIdRolafiliado());
                                solicitudNovedadPersona.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
                                entityManager.persist(solicitudNovedadPersona);
                            }
                        }

                        logger.debug(
                                "Finaliza el mètodo almacenarSolicitudNovedadPersonaMasivo( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
                    } catch (Exception e) {
                        logger.error("**Ocurrió un error inesperado", e);
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                    /**
                     * FIN ALMACENAR SOLICITUDNOVEDADPERSONA
                     */
                    // Se asignan las personas asociadas a un retiro automático
                    if (datosNovedadAutomaticaDTO.getListaPersonasRetiro() != null
                            && !datosNovedadAutomaticaDTO.getListaPersonasRetiro().isEmpty()) {
                        solNovedadDTO.setPersonasRetiroAutomatico(datosNovedadAutomaticaDTO.getListaPersonasRetiro());
                    }
                    DatosPersonaNovedadDTO datosPersonaNovedadDTO = new DatosPersonaNovedadDTO();
                    // Se asignan los identificadores de personas a gestionar

                    logger.info("Personas para revisar "+datosNovedadAutomaticaDTO.toString());

                    if (datosNovedadAutomaticaDTO.getIdPersonaAfiliados() != null
                            && !datosNovedadAutomaticaDTO.getIdPersonaAfiliados().isEmpty()) {
                        datosPersonaNovedadDTO.setIdPersonas(datosNovedadAutomaticaDTO.getIdPersonaAfiliados());
                    } // Se asignan los identificadores de beneficiarios a gestionar
                    else if (datosNovedadAutomaticaDTO.getListaBeneficiarios() != null
                            && !datosNovedadAutomaticaDTO.getListaBeneficiarios().isEmpty()) {
                        List<Long> listaIdBeneficiarios = new ArrayList<>();
                        for (BeneficiarioNovedadAutomaticaDTO beneficiarioNovedadDTO : datosNovedadAutomaticaDTO
                                .getListaBeneficiarios()) {
                            listaIdBeneficiarios.add(beneficiarioNovedadDTO.getIdBeneficiario());
                        }
                        datosPersonaNovedadDTO.setIdBeneficiarios(listaIdBeneficiarios);
                    } else if (datosNovedadAutomaticaDTO.getListaRolafiliados() != null && !datosNovedadAutomaticaDTO.getListaRolafiliados().isEmpty()) {
                        List<Long> roles = new ArrayList<>();
                        List<Long> afiliados = new ArrayList<>();
                        for (RolafiliadoNovedadAutomaticaDTO rol : datosNovedadAutomaticaDTO.getListaRolafiliados()) {

                            roles.add(rol.getIdRolafiliado());
                            afiliados.add(rol.getIdPersonaAfiliado());
                        }
                        datosPersonaNovedadDTO.setRolesAfiliado(roles);
                        datosPersonaNovedadDTO.setIdPersonas(afiliados);

                        datosPersonaNovedadDTO.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                    }
                    solNovedadDTO.setDatosPersona(datosPersonaNovedadDTO);
                }
                // Se resuelve la novedad por medio de los convertidores
                resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
                // Se envian comunicados masivamente
                enviarComunicadoMasivo(solNovedadDTO, userDTO);
                // Se cierra la solicitud
                cerrarSolicitudNovedad(solNovedadDTO, true);
                logger.info("finalizarcerrarSolicitudNovedad");
            }
            logger.info(
                    ">>Fin de método radicarSolicitudNovedadMasiva((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");
            return solNovedadDTO;

        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error(
                    "Ocurrio un error inesperado en radicarSolicitudNovedadMasiva(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Obtiene el comunicado de acuerdo al tipo transaccion de la novedad
     * automatica registrada y se realiza el envio del comunicado de acuerdo a
     * la parametrizacion de notificacion
     *
     * @param solicitudNovedadDTO Informacion solicitud novedad
     * @param userDTO             Informacion usuario
     */
    private void enviarComunicadoMasivo(SolicitudNovedadDTO solicitudNovedadDTO, UserDTO userDTO) {
        // Novedad en registro
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        EtiquetaPlantillaComunicadoEnum comunicado = null;

        switch (novedad) {
            case INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010:
            case INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000:
            case INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR:
                // Comunicado 48.
                comunicado = EtiquetaPlantillaComunicadoEnum.NTF_NVD_EMP;
                break;
            case INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR:
            case VENCIMIENTO_AUTOMATICO_INCAPACIDADES:
            case ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE:
            case VENCIMIENTO_AUTOMATICO_CERTIFICADOS:
            case RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA:
            case RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD:
            case CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD:
                // Comunicado 47.
                comunicado = EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS;
                break;
            default:
                break;
        }

        if (comunicado != null) {
            NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
            notificacion.setEtiquetaPlantillaComunicado(comunicado);
            notificacion.setProcesoEvento(solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name());
            notificacion.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
            notificacion.setTipoTx(solicitudNovedadDTO.getNovedadDTO().getNovedad());
            // Se setea el id del empleador en caso de existir
            notificacion.setIdEmpleador(solicitudNovedadDTO.getDatosEmpleador() != null
                    ? solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador()
                    : null);

            enviarComunicadoConstruido(notificacion);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * verificarEstructuraArchivoMultiple(java.lang.Long,
     * com.asopagos.dto.CargueAfiliacionMultipleDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoMultiple(Long idEmpleador, CargueMultipleDTO cargue,
                                                                            UserDTO userDTO) {
        logger.debug("Inicio validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO)");
        if (cargue.getCodigoIdentificacionECM() == null) {
            logger.debug(
                    "Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO): no se especifica id del archivo ECM");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
                    "El empleador no existe");
        }
        if (!idEmpleador.equals(cargue.getIdEmpleador())) {
            logger.debug(
                    "Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO): empleador no existe");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO,
                    "El empleador no existe");
        }
        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        cargue.setIdEmpleador(idEmpleador);
        cargue.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        cargue.setEstado(EstadoCargaMultipleEnum.CARGADO);
        cargue.setFechaCarga(Calendar.getInstance().getTime());
        cargue.setProceso(ProcesoEnum.AFILIACION_DEPENDIENTE_WEB);
        cargue.setTipoSolicitante(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        cargue.setTipoTransaccion(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
        Long idCargueMultiple = registrarCargue(idEmpleador, cargue);

        ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargueProcesoDTO.setCargue_id(idCargueMultiple);
        consolaEstadoCargueProcesoDTO.setCcf(codigoCaja);
        consolaEstadoCargueProcesoDTO.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargueProcesoDTO.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargueProcesoDTO.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargueProcesoDTO.setNombreArchivo(archivo.getFileName());
        consolaEstadoCargueProcesoDTO.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_MULTIPLE_135);
        consolaEstadoCargueProcesoDTO.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargueProcesoDTO);

        ResultadoValidacionArchivoDTO resultDTO = verificarEstructuraArchivo(idEmpleador, idCargueMultiple,
                cargue.getIdSucursalEmpleador(), archivo);
        resultDTO.setIdCargue(idCargueMultiple);

        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargueMultiple);
        EstadoCargueMasivoEnum estadoProcesoMasivo;
        if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())) {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
        } else {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
        }
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargaMultipleEnum.CERRADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_MULTIPLE_135);
        actualizarCargueConsolaEstado(idCargueMultiple, conCargueMasivo);
        /* Actualizo el estado del cargue multiple en la base de datos */
        modificarEstadoCargueMultiple(idCargueMultiple, resultDTO.getEstadoCargue());
        logger.debug("Finaliza validarEstructuraContenidoArchivoMultiple(Long, CargueAfiliacionMultipleDTO, UserDTO)");
        return resultDTO;
    }

    @Override
    public List<TrabajadorCandidatoNovedadDTO> guardarDatosNovedadArchivoMultiple(Long idEmpleador,
                                                                                  Long numeroDiaTemporizador, String nombreArchivo, Long codigoCargue,
                                                                                  List<TrabajadorCandidatoNovedadDTO> lstTrabajadorNovedadDTO, UserDTO userDTO) {
        logger.debug("Inicia guardarDatosNovedadArchivoMultiple(Long, Long,String,Long,List<SolicitudNovedadDTO>)");
        // Se verifican los parametros de entrada
        if (codigoCargue == null) {
            logger.debug(
                    "Finaliza guardarDatosNovedadArchivoMultiple(Long, Long,String,Long,List<SolicitudNovedadDTO>)");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        if (idEmpleador == null || lstTrabajadorNovedadDTO.isEmpty()) {
            modificarEstadoCargueMultiple(codigoCargue, EstadoCargaMultipleEnum.CANCELADO);
            logger.debug(
                    "Finaliza guardarDatosNovedadArchivoMultiple(Long, Long,String,Long,List<SolicitudNovedadDTO>)");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        try {
            List<TrabajadorCandidatoNovedadDTO> respuestaNovedades = new ArrayList<TrabajadorCandidatoNovedadDTO>();
            // Se ejecuta el bloque de validaciones
            ListaDatoValidacionDTO validacionNovedad = validarPersonasNovedadesCargaMultiple(
                    BLOQUE_VALIDACION_CARGUE_MULTIPLE_NOVEDADES, ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB,
                    ValidadorGeneralEnum.GENERAL.name(), lstTrabajadorNovedadDTO);
            List<TrabajadorCandidatoNovedadDTO> validaciones = validacionNovedad.getCandidatoNovedadDTOAprobado();
            List<TrabajadorCandidatoNovedadDTO> validacionesNoAprobadas = validacionNovedad
                    .getCandidatoNovedadDTONoAprobado();
            // Se agrega las personas que no aprobaron
            if (!validacionesNoAprobadas.isEmpty()) {
                respuestaNovedades.addAll(validacionesNoAprobadas);
            }
            // Se verifican las personas que pasaron las validaciones
            if (!validaciones.isEmpty()) {
                // Se consulta la informacion de las personas
                Map<String, RolAfiliadoModeloDTO> mapRolAfiliado = buscarTrabajadoresEmpleador(idEmpleador);
                // Se realiza la verificación de novedades a registrar
                for (TrabajadorCandidatoNovedadDTO candidatoNovedadDTO : validaciones) {

                    List<TipoTransaccionEnum> lstTipoTransaccion = new ArrayList<TipoTransaccionEnum>();
                    List<EstadoSolicitudNovedadEnum> lstEstadoSolicitudNovedad = new ArrayList<EstadoSolicitudNovedadEnum>();
                    // Se obtiene la informacion de la persona asociada al empleador
                    String key = candidatoNovedadDTO.getPersonaDTO().getTipoIdentificacion()
                            + candidatoNovedadDTO.getPersonaDTO().getNumeroIdentificacion();
                    RolAfiliadoModeloDTO infoRolAfiliado = mapRolAfiliado.get(key);
                    if (infoRolAfiliado == null || infoRolAfiliado.getIdRolAfiliado() == null) {
                        continue;
                    }
                    // Se crean los datos para el registro de novedad
                    DatosPersonaNovedadDTO datoPersonaNovedadDTO = convertirTrabajadorADatoPersonaNovedadDTO(
                            candidatoNovedadDTO);
                    // Se relacionan los datos del rolAfiliado
                    datoPersonaNovedadDTO.setIdRolAfiliado(infoRolAfiliado.getIdRolAfiliado());
                    // Verificar si se registra
                    // CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB
                    verificarRegistrarNovedad(infoRolAfiliado, candidatoNovedadDTO,
                            TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB,
                            datoPersonaNovedadDTO, lstTipoTransaccion, lstEstadoSolicitudNovedad, codigoCargue,
                            userDTO);
                    // Verificar si se registra CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB
                    verificarRegistrarNovedad(infoRolAfiliado, candidatoNovedadDTO,
                            TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB,
                            datoPersonaNovedadDTO, lstTipoTransaccion, lstEstadoSolicitudNovedad, codigoCargue,
                            userDTO);
                    // Verificar si se registra
                    // CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB
                    verificarRegistrarNovedad(infoRolAfiliado, candidatoNovedadDTO,
                            TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB,
                            datoPersonaNovedadDTO, lstTipoTransaccion, lstEstadoSolicitudNovedad, codigoCargue,
                            userDTO);
                    // Verificar si se registra
                    // ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB
                    verificarRegistrarNovedad(infoRolAfiliado, candidatoNovedadDTO,
                            TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB,
                            datoPersonaNovedadDTO, lstTipoTransaccion, lstEstadoSolicitudNovedad, codigoCargue,
                            userDTO);
                    // Se agrega los resultados de novedad
                    candidatoNovedadDTO.setLstTipoTransaccion(lstTipoTransaccion);
                    candidatoNovedadDTO.setLstEstadoSolicitudNovedadEnum(lstEstadoSolicitudNovedad);
                    candidatoNovedadDTO.setValidacionNovedad(true);
                }
                respuestaNovedades.addAll(validaciones);
            }

            Long fechaProcesamiento = new Date().getTime();
            for (TrabajadorCandidatoNovedadDTO respuesta : respuestaNovedades) {
                respuesta.setEstadoArhivo(EstadoCargueMasivoEnum.FINALIZADO);
                respuesta.setNombreArchivo(nombreArchivo);
                respuesta.setFechaProcesamiento(fechaProcesamiento);
            }
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
            conCargueMasivo.setFechaFin(fechaProcesamiento);
            conCargueMasivo.setGradoAvance(new BigDecimal(100));
            conCargueMasivo.setCargue_id(codigoCargue);
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_MULTIPLE_135);
            actualizarCargueConsolaEstado(codigoCargue, conCargueMasivo);
            modificarEstadoCargueMultiple(codigoCargue, EstadoCargaMultipleEnum.CERRADO);
            logger.debug(
                    "Finaliza guardarDatosNovedadArchivoMultiple(Long, Long,String,Long,List<SolicitudNovedadDTO>)");
            return respuestaNovedades;
        } catch (Exception e) {
            modificarEstadoCargueMultiple(codigoCargue, EstadoCargaMultipleEnum.CANCELADO);
            logger.debug(
                    "Finaliza guardarDatosNovedadArchivoMultiple(Long, Long,String,Long,List<SolicitudNovedadDTO>)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Verifica respecto a la informacion existente si se debe realiza el
     * registro de la novedad indicada en el tipo transaccion
     *
     * @param rolAfiliado         Informacion rolafiliado
     * @param candidatoNovedadDTO Informacion candidato
     * @param tipoTransaccion     Tipo Transaccion solicitud a registrar
     * @return True si se registra la novedad false en caso contrario
     */
    private void verificarRegistrarNovedad(RolAfiliadoModeloDTO rolAfiliado,
                                           TrabajadorCandidatoNovedadDTO candidatoNovedadDTO,
                                           TipoTransaccionEnum tipoTransaccion, DatosPersonaNovedadDTO datoPersonaNovedadDTO,
                                           List<TipoTransaccionEnum> lstTipoTransaccion, List<EstadoSolicitudNovedadEnum> lstEstadoSolicitudNovedad,
                                           Long codigoCargue, UserDTO userDTO) {
        // Se verifica si se registra la novedad
        Boolean registrar = false;
        switch (tipoTransaccion) {
            case CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB:
                if (verifyDifference(rolAfiliado.getAfiliado().getNivelEducativo(),
                        candidatoNovedadDTO.getPersonaDTO().getNivelEducativo())) {
                    registrar = true;
                }
                break;
            case CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB:
                if (rolAfiliado.getAfiliado().getUbicacionModeloDTO() != null
                        && candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO() != null
                        && (verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getIdMunicipio(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getIdMunicipio())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO()
                                .getDireccionFisica(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getDireccionFisica())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO()
                                .getDescripcionIndicacion(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getDescripcionIndicacion())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getTelefonoFijo(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getTelefonoFijo())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO()
                                .getTelefonoCelular(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getTelefonoCelular())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getEmail(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getEmail())
                        || verifyDifference(
                        candidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getCodigoPostal(),
                        rolAfiliado.getAfiliado().getUbicacionModeloDTO().getCodigoPostal()))) {
                    registrar = true;
                }
                break;
            case CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB:
                if (verifyDifference(candidatoNovedadDTO.getClaseTrabajador(), rolAfiliado.getClaseTrabajador())) {
                    registrar = true;
                }
                break;
            case ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB:
                if (verifyDifference(candidatoNovedadDTO.getTipoSalario(), rolAfiliado.getTipoSalario())
                        || verifyDifference(candidatoNovedadDTO.getValorSalarioMensual(),
                        rolAfiliado.getValorSalarioMesadaIngresos())
                        || verifyDifference(candidatoNovedadDTO.getCargoOficina(), rolAfiliado.getCargo())
                        || verifyDifference(candidatoNovedadDTO.getTipoContratoEnum(), rolAfiliado.getTipoContrato())) {
                    registrar = true;
                }
                break;
            default:
                break;
        }
        if (registrar) {
            SolicitudNovedadDTO solicitudNovedadDTO = radicarSolicitudTrabajador(datoPersonaNovedadDTO, tipoTransaccion,
                    codigoCargue, userDTO);
            if (solicitudNovedadDTO != null && solicitudNovedadDTO.getIdSolicitud() != null) {
                lstEstadoSolicitudNovedad.add(solicitudNovedadDTO.getEstadoSolicitudNovedad());
                lstTipoTransaccion.add(tipoTransaccion);
            }
        }
    }

    /**
     * Consulta la informacion de las personas asociadas al empleador
     *
     * @param idEmpleador Identificaodr del empleador
     * @return Mapa con la informacion de las personas
     */
    private Map<String, RolAfiliadoModeloDTO> buscarTrabajadoresEmpleador(Long idEmpleador) {
        Map<String, RolAfiliadoModeloDTO> mapResult = new HashMap<>();
        ConsultarRolesAfiliadosEmpleador service = new ConsultarRolesAfiliadosEmpleador(idEmpleador, null);
        service.execute();
        List<RolAfiliadoModeloDTO> listAfiliados = service.getResult();
        for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : listAfiliados) {
            String key = rolAfiliadoModeloDTO.getAfiliado().getTipoIdentificacion()
                    + rolAfiliadoModeloDTO.getAfiliado().getNumeroIdentificacion();
            mapResult.put(key, rolAfiliadoModeloDTO);
        }
        return mapResult;
    }

    /**
     * Realiza el registro de información del archivo de supervivencia
     *
     * @param codigoECMArchivo Identificador ECM del archivo cargado
     * @param usuario          Nombre usuario que realizó el cargue
     * @param caja             Codigo de la caja
     * @param fechaActual      Fecha actual
     * @return Información del archivo para el procesamiento
     */
    private ArchivoSupervivenciaDTO registrarArchivoSupervivenciaConsolaCargue(String codigoECMArchivo, String usuario,
                                                                               String caja,
                                                                               Long fechaActual) {
        logger.info("Inicia registrarArchivoSupervivenciaConsolaCargue(" + codigoECMArchivo + ", " + usuario + ", "
                + caja + ", "
                + fechaActual + ")");
        // Se consulta el archivo
        InformacionArchivoDTO archivo = obtenerArchivo(codigoECMArchivo);
        logger.info("****archivo_*****" + archivo.toString());
        // Objeto para el registro de archivo
        ArchivoSupervivenciaDTO archivoSupervivenciaDTO = new ArchivoSupervivenciaDTO();
        archivoSupervivenciaDTO.setFechaIngreso(fechaActual);
        logger.info("FechaIngreso" + archivoSupervivenciaDTO.getFechaIngreso());
        archivoSupervivenciaDTO.setUsuario(usuario);
        logger.info("Usuario" + archivoSupervivenciaDTO.getUsuario());
        archivoSupervivenciaDTO.setIdentificadorECMRegistro(archivo.getIdentificadorDocumento());
        logger.info("IdentificadorECMRegistro" + archivoSupervivenciaDTO.getIdentificadorECMRegistro());
        archivoSupervivenciaDTO.setNombreArhivo(archivo.getFileName());
        logger.info("NombreArhivo" + archivoSupervivenciaDTO.getNombreArhivo());
        archivoSupervivenciaDTO.setEstadoCargue(EstadoCargueSupervivenciaEnum.EN_PROCESAMIENTO);
        logger.info("EstadoCargue" + archivoSupervivenciaDTO.getEstadoCargue());

        logger.info("InformacionArchivoDTO::" + archivo);
        // Se registra el archivo
        Long idCargueSupervivencia = modificarCrearCargueSupervivencia(archivoSupervivenciaDTO);
        // Se registra la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargueProcesoDTO.setCargue_id(idCargueSupervivencia);
        consolaEstadoCargueProcesoDTO.setCcf(caja);
        consolaEstadoCargueProcesoDTO.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargueProcesoDTO.setFechaInicio(fechaActual);
        consolaEstadoCargueProcesoDTO.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargueProcesoDTO.setIdentificacionECM(archivo.getIdentificadorDocumento());
        consolaEstadoCargueProcesoDTO.setNombreArchivo(archivo.getFileName());
        consolaEstadoCargueProcesoDTO.setProceso(TipoProcesoMasivoEnum.CARGUE_SUPERVIVENCIA);
        consolaEstadoCargueProcesoDTO.setUsuario(usuario);
        registrarConsolaEstado(consolaEstadoCargueProcesoDTO);
        archivoSupervivenciaDTO.setInfoArchivoCargado(archivo);
        archivoSupervivenciaDTO.setIdCargue(idCargueSupervivencia);
        archivoSupervivenciaDTO.setIdentificadorCargue(idCargueSupervivencia);
        logger.debug("Finaliza registrarArchivoSupervivenciaConsolaCargue(" + codigoECMArchivo + ", " + usuario + ", "
                + caja + ", "
                + fechaActual + ")");
        logger.info("Finaliza::registrarArchivoSupervivenciaConsolaCargue" + archivoSupervivenciaDTO);
        return archivoSupervivenciaDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     *      com.asopagos.novedades.composite.service.NovedadesCompositeService#verificarEstructuraArchivoSupervivencia(com.asopagos.novedades.dto.ArchivoSupervivenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void verificarEstructuraArchivoSupervivencia(ArchivoSupervivenciaDTO archivoSuperVivenciaDTO,
                                                        UserDTO userDTO) {
        logger.debug("Inicia verificarEstructuraArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
        try {
            if (archivoSuperVivenciaDTO.getCodigoECMRegisEncontrado() == null
                    && archivoSuperVivenciaDTO.getCodigoECMRegisNoEncontrado() == null) {
                logger.debug(
                        "Finaliza verificarEstructuraArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO): no se especifica id del archivo ECM");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

            // Lista de archivos a procesar
            List<ArchivoSupervivenciaDTO> listArchivo = new ArrayList<>();

            Long fechaActual = new Date().getTime();
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO)
                    .toString();
            Long filDefIdEncontrado = new Long(CacheManager
                    .getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC
                            .toString())
                    .toString());
            Long filDefIdNoEncontrado = new Long(CacheManager
                    .getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_NO_ENCONTRADO_RNEC
                            .toString())
                    .toString());
            ArchivoSupervivenciaDTO archivoRegistroNoEncontrado = null;
            ArchivoSupervivenciaDTO archivoRegistroEncontrado = null;
            if (archivoSuperVivenciaDTO.getCodigoECMRegisEncontrado() != null) {
                archivoRegistroEncontrado = registrarArchivoSupervivenciaConsolaCargue(
                        archivoSuperVivenciaDTO.getCodigoECMRegisEncontrado(), userDTO.getNombreUsuario(), codigoCaja,
                        fechaActual);
                archivoRegistroEncontrado.setFileDefinitionId(filDefIdEncontrado);
                listArchivo.add(archivoRegistroEncontrado);
            }
            if (archivoSuperVivenciaDTO.getCodigoECMRegisNoEncontrado() != null) {
                archivoRegistroNoEncontrado = registrarArchivoSupervivenciaConsolaCargue(
                        archivoSuperVivenciaDTO.getCodigoECMRegisNoEncontrado(), userDTO.getNombreUsuario(), codigoCaja,
                        fechaActual);
                archivoRegistroNoEncontrado.setFileDefinitionId(filDefIdNoEncontrado);
                listArchivo.add(archivoRegistroNoEncontrado);
            }
            /*
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso1");
             * if (archivoRegistroNoEncontrado != null) {
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso2");
             * List<String> dataFile =
             * conveterByteToString(archivoRegistroNoEncontrado.getInfoArchivoCargado().
             * getDataFile());
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso3.1 " +
             * dataFile.get(1));
             * Long idPersona = consultarTipoNumeroIdentificacion(dataFile.get(1));
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso3 " + idPersona);
             * if (idPersona != 0) {
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso4 " + idPersona);
             * insertarRegistroPersonaInconsistente(mapperPersonaInconsistenteDTO(
             * archivoRegistroNoEncontrado, dataFile, idPersona));
             * }
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso5 " + idPersona);
             * }
             *
             * if (archivoRegistroEncontrado != null) {
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso2");
             * List<String> dataFile1 =
             * conveterByteToString(archivoRegistroEncontrado.getInfoArchivoCargado().
             * getDataFile());
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso3.1 " +
             * dataFile1.get(1));
             * Long idPersona = consultarTipoNumeroIdentificacion(dataFile1.get(1));
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso3 " + idPersona);
             * if (idPersona != 0) {
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso4 " + idPersona);
             * insertarRegistroPersonaInconsistente(mapperPersonaInconsistenteDTO(
             * archivoRegistroEncontrado, dataFile1, idPersona));
             * }
             * logger.info("verificarEstructuraArchivoSupervivencia-- paso5 " + idPersona);
             * }
             */

            // Creación de tareas paralelas
            List<Callable<ResultadoValidacionArchivoDTO>> tareasParalelas = new LinkedList<>();

            for (ArchivoSupervivenciaDTO archivo : listArchivo) {
                Callable<ResultadoValidacionArchivoDTO> parallelTask = () -> {
                    ValidarArchivoSupervivencia validarSrv = new ValidarArchivoSupervivencia(archivo);
                    validarSrv.execute();
                    return validarSrv.getResult();
                };
                tareasParalelas.add(parallelTask);
            }

            managedExecutorService.invokeAll(tareasParalelas);
            logger.debug("Finaliza verificarEstructuraArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
        } catch (Exception e) {
            // No se propaga la excepción por que el método es llamado de forma asíncrona
            logger.error("Error verificarEstructuraArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)", e);
        }
    }

    /*
     * Método Original
     * public List<String> conveterByteToString(byte[] dataFile) {
     * List<String> responseString = new ArrayList<>();
     * String resultadoConverter = new String(dataFile,
     * java.nio.charset.StandardCharsets.UTF_8);
     * logger.info("resultadoConverter" + resultadoConverter);
     * int posicionObservaciones = resultadoConverter.lastIndexOf("NO");
     * int posicionNumeroIdentificacion = resultadoConverter.lastIndexOf("CC");
     * logger.info("**__**convertebyte posicionObservaciones " +
     * posicionObservaciones);
     * logger.info("**__**convertebyte posicionNumeroIdentificacion " +
     * posicionNumeroIdentificacion + " tamaño " + resultadoConverter.length());
     * String obtenerObservacion =
     * resultadoConverter.substring(posicionObservaciones,
     * resultadoConverter.length());
     * logger.info("**__**convertebyte obtenerObservacion>> " + " tamaño " +
     * resultadoConverter.length() + " - " + obtenerObservacion);
     * logger.info("**__**convertebyte posicionNumeroIdentificacion: " + " tamaño "
     * + posicionNumeroIdentificacion + " - " + posicionObservaciones);
     * String obtenerNumeroIdentificacion =
     * resultadoConverter.substring(posicionNumeroIdentificacion + 3,
     * posicionObservaciones - 1);
     * responseString.add(obtenerObservacion);
     * logger.info("**__**pasa a obtenerObservacion " +obtenerObservacion );
     * responseString.add(obtenerNumeroIdentificacion);
     * logger.info("**__**convertebyte si paso  " + obtenerNumeroIdentificacion);
     * return responseString;
     * }
     */
    // GLPI 46388

    public List<String> conveterByteToString(byte[] dataFile) {
        List<String> responseString = new ArrayList<>();
        String resultadoConverter = new String(dataFile, java.nio.charset.StandardCharsets.UTF_8);
        int posicionObservaciones = resultadoConverter.lastIndexOf("NO ENCONTRADO");
        int posicionNumeroIdentificacion = resultadoConverter.lastIndexOf("2,CC") + 2;
        String obtenerObservacion = resultadoConverter.substring(posicionObservaciones, resultadoConverter.length());
        String obtenerNumeroIdentificacion = resultadoConverter.substring(posicionNumeroIdentificacion + 3,
                posicionObservaciones - 1);
        responseString.add(obtenerObservacion);
        responseString.add(obtenerNumeroIdentificacion);
        return responseString;
    }

    public RegistroPersonaInconsistenteDTO mapperPersonaInconsistenteDTO(
            ArchivoSupervivenciaDTO archivoSupervivenciaDTO, List<String> dataFile, Long idPersona) {
        logger.info("Inicio de método mapperPersonaInconsistenteDTO" + archivoSupervivenciaDTO.toString());
        RegistroPersonaInconsistenteDTO registroPersonaInconsistenteDTO = new RegistroPersonaInconsistenteDTO();
        registroPersonaInconsistenteDTO.setPersona(new PersonaModeloDTO());
        registroPersonaInconsistenteDTO.getPersona().setIdPersona(idPersona);
        registroPersonaInconsistenteDTO.setCanalContacto(CanalRecepcionEnum.ENTIDAD_EXTERNA);
        registroPersonaInconsistenteDTO.setFechaIngreso(archivoSupervivenciaDTO.getFechaIngreso());
        registroPersonaInconsistenteDTO.setEstadoGestion(EstadoGestionEnum.PENDIENTE_GESITONAR);
        registroPersonaInconsistenteDTO.setObservaciones(dataFile.get(0));
        registroPersonaInconsistenteDTO.setNombreCargue(archivoSupervivenciaDTO.getNombreArhivo());
        registroPersonaInconsistenteDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.NO_ENCONTRADO);
        logger.info("finaliza de método mapperPersonaInconsistenteDTO" + registroPersonaInconsistenteDTO);
        return registroPersonaInconsistenteDTO;
    }

    public void insertarRegistroPersonaInconsistente(RegistroPersonaInconsistenteDTO inconsistenteDTO) {
        logger.info("Inicio de método insertarRegistroPersonaInconsistente");
        InsertarRegistroPersonaInconsistente service = new InsertarRegistroPersonaInconsistente(inconsistenteDTO);
        service.execute();
        logger.info("Fin de método insertarRegistroPersonaInconsistente: ");
    }

    public Long consultarTipoNumeroIdentificacion(String numeroIdentificacion) {
        logger.info(
                "Inicio de método consultarTipoNumeroIdentificacion" + "numeroIdentificacion" + numeroIdentificacion);
        ConsultarTipoNumeroIdentificacion service = new ConsultarTipoNumeroIdentificacion(numeroIdentificacion);
        service.execute();
        logger.info("Metodo consultarTipoNumeroIdentificacion" + service.getResult());
        return service.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * radicarSolicitudNovedadAportes(com.asopagos.dto.aportes.NovedadAportesDTO)
     */
    @Override
    //@Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO, UserDTO userDTO) {
        logger.info("Inicio de método radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO)");
        logger.info("Fecha inicio de la Novedad:" + novedadAportesDTO.getFechaInicio());
        System.out.println("**__**INICIO radicarSolicitudNovedadAportes en novedades composite IdRegistroDetalladoNovedad: "+novedadAportesDTO.getIdRegistroDetalladoNovedad());

        if (CanalRecepcionEnum.PILA.equals(novedadAportesDTO.getCanalRecepcion()) && novedadAportesDTO.getIdRegistroDetalladoNovedad() == null) {
            logger.info("**__** radicarSolicitudNovedadAportes IdRegistroDetalladoNovedad viene NULL , no creara solicitudes ");
            return;
        }

        if (TipoTransaccionEnum.SUSPENSION_PENSIONADO_SUS.equals(novedadAportesDTO.getTipoNovedad())) {
            novedadAportesDTO.setAplicar(MarcaNovedadEnum.NO_APLICADA);
        }
        if (MarcaNovedadEnum.APLICADA.equals(novedadAportesDTO.getAplicar())) {
            logger.info("INICIO CASO 1");
            SolicitudNovedadDTO solicitudNovedadDTO = construirSolicitudNovedad(novedadAportesDTO);
            if (CanalRecepcionEnum.PILA.equals(novedadAportesDTO.getCanalRecepcion())) {
                // aca se puede hacer el ajsute de reintegros poner a realizar tareas de
                // afiliaciones
                radicarSolicitudNovedadPILA(solicitudNovedadDTO, userDTO);
            } else {
                System.out.println("**__**INICIO radicarSolicitudNovedadAportes en novedades composite esle pila");
                radicarSolicitudNovedad(solicitudNovedadDTO, userDTO);
            }
        } else if (MarcaNovedadEnum.NO_APLICADA.equals(novedadAportesDTO.getAplicar())) {
            logger.info("INICIO CASO 2");
            logger.info("**__**novedadAportesDTO getTenNovedadId: " + novedadAportesDTO.getTenNovedadId());
            logger.info("**__**novedadAportesDTO getTipoIdentificacionCotizante(): "
                    + novedadAportesDTO.getTipoIdentificacion());
            logger.info("**__**novedadAportesDTO getNumeroIdentificacionCotizante(): "
                    + novedadAportesDTO.getNumeroIdentificacion());
            logger.info("**__**novedadAportesDTO getClasificacionAfiliado(): "
                    + novedadAportesDTO.getClasificacionAfiliado());
            logger.info("**__**novedadAportesDTO TipoNovedad()(): " + novedadAportesDTO.getTipoNovedad());
            logger.info("**__*******************************************************************************");
            logger.info("**__**registrara rechazada la novedad INICIO CASO 2");
            // SE CONSTRUYE PARA DEFINIR EL ROL AFILIADO
            SolicitudNovedadDTO solicitudNovedadDTORolAfiliado = construirSolicitudNovedad(novedadAportesDTO);
            // FIN SE CONSTRUYE PARA DEFINIR EL ROL AFILIADO
            IntentoNovedadDTO intentoNovedadDTO = construirIntentoNovedad(novedadAportesDTO);
            // SE CONSTRUYE PARA DEFINIR EL ROL AFILIADO
            intentoNovedadDTO.setIdRolAfiliado(solicitudNovedadDTORolAfiliado.getDatosPersona().getIdRolAfiliado());
            logger.info("**__**novedadAportesDTO RolIdAfiliado: "
                    + solicitudNovedadDTORolAfiliado.getDatosPersona().getIdRolAfiliado());
            logger.info("**__**novedadAportesDTO intentoNovedadDTO.setIdRegistroDetalladoNovedad( "
                    + intentoNovedadDTO.getIdRegistroDetalladoNovedad());
            // FIN SE CONSTRUYE PARA DEFINIR EL ROL AFILIADO
            registrarIntentoFallido(intentoNovedadDTO, userDTO);
            if (novedadAportesDTO.getTenNovedadId() != null) {
                // posible correccion
                ConfirmarTransaccionNovedadPilaRutine c = new ConfirmarTransaccionNovedadPilaRutine();
                c.confirmarTransaccionNovedadPilaRutine(novedadAportesDTO.getTenNovedadId(),
                        novedadAportesDTO.getIdRegistroDetallado(), entityManager);
            }
        } else if (MarcaNovedadEnum.NOVEDAD_FUTURA.equals(novedadAportesDTO.getAplicar())) {
            logger.info("INICIO CASO 3");
            crearNovedadFutura(construirRegistroNovedadFutura(novedadAportesDTO));
            if (novedadAportesDTO.getTenNovedadId() != null) {
                ConfirmarTransaccionNovedadPilaRutine c = new ConfirmarTransaccionNovedadPilaRutine();
                c.confirmarTransaccionNovedadPilaRutine(novedadAportesDTO.getTenNovedadId(),
                        novedadAportesDTO.getIdRegistroDetallado(), entityManager);
            }
        }

        logger.info("Fin de método radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO)");

    }

    private RegistroNovedadFuturaModeloDTO construirRegistroNovedadFutura(NovedadAportesDTO novedadAportesDTO) {
        logger.debug("Inicio de método construirRegistroNovedadFutura(NovedadAportesDTO novedadAportesDTO)");
        RegistroNovedadFuturaModeloDTO registroNovedadFutura = new RegistroNovedadFuturaModeloDTO();
        registroNovedadFutura.setCanalRecepcion(novedadAportesDTO.getCanalRecepcion());
        registroNovedadFutura.setComentario(novedadAportesDTO.getComentarios());
        registroNovedadFutura.setFechaFin(new Date(novedadAportesDTO.getFechaFin()));
        registroNovedadFutura.setFechaInicio(new Date(novedadAportesDTO.getFechaInicio()));
        registroNovedadFutura.setIdRegistroDetallado(novedadAportesDTO.getIdRegistroDetallado());
        PersonaModeloDTO persona = consultarPersona(novedadAportesDTO.getNumeroIdentificacion(),
                novedadAportesDTO.getTipoIdentificacion());
        registroNovedadFutura.setIdPersona(persona.getIdPersona());
        registroNovedadFutura.setClasificacion(novedadAportesDTO.getClasificacionAfiliado());
        if (novedadAportesDTO.getTipoIdentificacionAportante() != null
                && novedadAportesDTO.getNumeroIdentificacionAportante() != null) {
            EmpleadorModeloDTO empleador = consultarEmpleador(novedadAportesDTO.getTipoIdentificacionAportante(),
                    novedadAportesDTO.getNumeroIdentificacionAportante());
            if (empleador != null) {
                registroNovedadFutura.setIdEmpleador(empleador.getIdEmpleador());
            }
        }
        logger.debug("Fin de método construirRegistroNovedadFutura(NovedadAportesDTO novedadAportesDTO)");
        return registroNovedadFutura;
    }

    /**
     * Método que se encarga de construir los datos de la solicitud de novedad.
     *
     * @param novedadAportesDTO datos de novedad de aportes.
     * @return solicitud de novedad construida.
     */
    private SolicitudNovedadDTO construirSolicitudNovedad(NovedadAportesDTO novedadAportesDTO) {
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.construirSolicitudNovedad(novedadAportesDTO);
    }

    /**
     * Método que se encarga de construir un intento de novedad desde aportes.
     *
     * @param novedadAportesDTO datos de novedad de aportes.
     * @return intento de novedad.
     */
    private IntentoNovedadDTO construirIntentoNovedad(NovedadAportesDTO novedadAportesDTO) {
        logger.debug("Inicio de método construirSolicitudNovedad(NovedadAportesDTO novedadAportesDTO)");
        IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
        intentoNovedadDTO.setCanalRecepcion(novedadAportesDTO.getCanalRecepcion());
        intentoNovedadDTO.setTipoTransaccion(novedadAportesDTO.getTipoNovedad());
        intentoNovedadDTO.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.VALIDACION_REGLAS_NEGOCIO);
        intentoNovedadDTO.setTipoIdentificacion(novedadAportesDTO.getTipoIdentificacion());
        intentoNovedadDTO.setNumeroIdentificacion(novedadAportesDTO.getNumeroIdentificacion());
        intentoNovedadDTO.setIdRegistroDetallado(novedadAportesDTO.getIdRegistroDetallado());
        intentoNovedadDTO.setClasificacion(novedadAportesDTO.getClasificacionAfiliado());
        intentoNovedadDTO.setIdRegistroDetalladoNovedad(novedadAportesDTO.getIdRegistroDetalladoNovedad());

        logger.debug("Fin de método construirSolicitudNovedad(NovedadAportesDTO novedadAportesDTO)");
        return intentoNovedadDTO;
    }

    /**
     * Método encargado de gestionar los archivos de supervivencia
     *
     * @param dto     Información de registro de supervivencia
     * @param userDTO Usuario del contexto
     * @throws Exception
     */
    private void gestionarArchivoSupervivencia(List<ResultadoSupervivenciaDTO> dto, UserDTO userDTO) throws Exception {
        logger.info("Inicio gestionarArchivoSupervivencia(List<ResultadoSupervivenciaDTO>,UserDTO)");
        // Se obtienen los numeros de radicados
        Integer cantidadPersonasMuerte = 0;
        NumeroRadicadoCorrespondenciaDTO numeroRadicadoCorrespondenciaDTO;
        for (ResultadoSupervivenciaDTO resulDTO : dto) {
            if (TipoInconsistenciaANIEnum.CANCELADO_POR_MUERTE.equals(resulDTO.getTipoInconsistencia())) {
            }
            cantidadPersonasMuerte++;
        }
        if (cantidadPersonasMuerte > 0) {
            // Se obtienen los numeros de radicados
            numeroRadicadoCorrespondenciaDTO = obtenerNumeroRadicado(cantidadPersonasMuerte);
            if (numeroRadicadoCorrespondenciaDTO == null) {
                // Si no se obtuvieron radicados no se procesan las novedades
                return;
            }
        } else {
            return;
        }
        // Lista de llamados paralelos
        List<Callable<Void>> tareasParalelas = new LinkedList<>();
        for (ResultadoSupervivenciaDTO resulDTO : dto) {
            String numeroRadicado = numeroRadicadoCorrespondenciaDTO.nextValue();
            Callable<Void> parallelTask = () -> {
                RegistrarSupervivenciaPersona registrarSrv = new RegistrarSupervivenciaPersona(numeroRadicado,
                        resulDTO);
                registrarSrv.execute();
                return null;
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            managedExecutorService.invokeAll(tareasParalelas);
        }
        logger.debug("Fin gestionarArchivoSupervivencia(List<ResultadoSupervivenciaDTO>,UserDTO)");
    }

    /**
     * Metodo encargado de procesar el registro de inconsistencia o novedad
     * asociada al registro de supervivencia
     *
     * @param resulDTO Información reultante del archivo
     * @param userDTO  Usuario del contexto
     * @return Información incosistencia a registrar
     * @throws Exception
     */
    private void procesarSupervivenciaPersona(ResultadoSupervivenciaDTO resulDTO, UserDTO userDTO,
                                              String numeroRadicado)
            throws Exception {
        logger.info("**__**???Inicia procesarSupervivenciaPersona LUIS 1 " + resulDTO.getNumeroIdentificacion());
        if (resulDTO.getTipoInconsistencia() == null) {
            return;
        }
        TipoInconsistenciaANIEnum tipoInconsistencia = null;
        PersonaModeloDTO personaDTO = null;
        if (resulDTO.getNumeroIdentificacion() != null && resulDTO.getTipoIdentificacion() != null) {
            personaDTO = consultarPersona(resulDTO.getNumeroIdentificacion(), resulDTO.getTipoIdentificacion());
        }
        if (personaDTO != null) {
            switch (resulDTO.getTipoInconsistencia()) {
                case CANCELADO_POR_MUERTE:
                    // Se radica la solicitud
                    logger.info("Inicia procesarSupervivenciaPersona 4 CANCELADO_POR_MUERTE 1 - "
                            + personaDTO.getNumeroIdentificacion());
                    registrarSupervivenciaCanceladaPorMuerte(userDTO, resulDTO, numeroRadicado);
                    break;
                case CANCELADO_POR_MUERTE_LEY_1365_2009:
                    // Se radica la solicitud
                    logger.info(
                            "Inicia procesarSupervivenciaPersona 4 CANCELADO_POR_MUERTE CANCELADO_POR_MUERTE_LEY_1365_2009 1 - "
                                    + personaDTO.getNumeroIdentificacion());
                    registrarSupervivenciaCanceladaPorMuerte(userDTO, resulDTO, numeroRadicado);
                    break;
                case VIGENTE:
                    logger.info(
                            "Inicia procesarSupervivenciaPersona VIGENTE - " + personaDTO.getNumeroIdentificacion());
                    if (resulDTO.getFechaDefuncion() != null) {
                        tipoInconsistencia = TipoInconsistenciaANIEnum.VIGENTE;
                    }
                    break;
                default:
                    logger.info(
                            "Inicia procesarSupervivenciaPersona default - " + personaDTO.getNumeroIdentificacion());
                    tipoInconsistencia = resulDTO.getTipoInconsistencia();
                    break;
            }
        }
        // Se registra la inconsistencia
        if (tipoInconsistencia == null) {
            return;
        }
        List<RegistroPersonaInconsistenteDTO> registroPersonaInconsistenteDTOs = new ArrayList<>();
        registroPersonaInconsistenteDTOs.add(crearInconsistencia(resulDTO, personaDTO, tipoInconsistencia));
        registrarInconsistenciasPersonas(registroPersonaInconsistenteDTOs);
        logger.debug("Finaliza procesarSupervivenciaPersona(ResultadoSupervivenciaDTO, UserDTO)");
    }

    /**
     * Crea el registro de inconsistencia para guardar
     *
     * @param resulDTO          Informacion de registrada en el archivo
     * @param personaDTO        Información de la persona
     * @param tipoInconsitencia Tipo de inconsistencia creada
     * @return Registro de inconsistencia a persistir
     */
    private RegistroPersonaInconsistenteDTO crearInconsistencia(ResultadoSupervivenciaDTO resulDTO,
                                                                PersonaModeloDTO personaDTO,
                                                                TipoInconsistenciaANIEnum tipoInconsitencia) {
        RegistroPersonaInconsistenteDTO registroInconsistencia = new RegistroPersonaInconsistenteDTO();
        registroInconsistencia.setCanalContacto(CanalRecepcionEnum.ENTIDAD_EXTERNA);
        registroInconsistencia.setIdCargueMultiple(resulDTO.getIdCargueMultipleSupervivencia());
        registroInconsistencia.setEstadoGestion(EstadoGestionEnum.PENDIENTE_GESITONAR);
        registroInconsistencia.setFechaIngreso(new Date().getTime());
        registroInconsistencia.setPersona(personaDTO);
        registroInconsistencia.setTipoInconsistencia(tipoInconsitencia);
        return registroInconsistencia;
    }

    /**
     * Método encargado de registrar el resultado de la supervivencia cancelada
     * por muerte
     *
     * @param userDTO  Usuario del contexto
     * @param canal    Canal de recepción para el registro de la novedad
     * @param resulDTO Información de resultado de lectura archivo supervivencia
     * @throws Exception
     */
    private void registrarSupervivenciaCanceladaPorMuerte(UserDTO userDTO, ResultadoSupervivenciaDTO resulDTO,
                                                          String numeroRadicado)
            throws Exception {
        logger.debug("Inicia registrarSupervivenciaCanceladaPorMuerte(UserDTO, ResultadoSupervivenciaDTO, "
                + numeroRadicado + ")");
        // Fecha reporte Fallecimiento beneficiario
        Long fechaReporte = Calendar.getInstance().getTimeInMillis();

        // Se crea la solicitud novedad basica
        logger.info("**__**TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS()"
                + TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS);
        SolicitudNovedadDTO nuevaNovedad = new SolicitudNovedadDTO();
        nuevaNovedad.setNovedadCargaArchivoRespuestaSupervivencia(Boolean.TRUE);
        nuevaNovedad.setCanalRecepcion(CanalRecepcionEnum.ENTIDAD_EXTERNA);
        nuevaNovedad.setTipoTransaccion(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS);
        nuevaNovedad.setNumeroRadicacion(numeroRadicado);

        // Se inicializa los datos de persona de la novedad
        DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
        persona.setFechaDefuncion(resulDTO.getFechaDefuncion());
        persona.setFechaReporteFallecimientoTrabajador(resulDTO.getFechaDefuncion());
        persona.setPersonaFallecidaTrabajador(Boolean.TRUE);

        // ConsultarEstadoAfiliacionRespectoCCF
        ConsultaEstadoAfiliacionDTO consultaEstadoAfiliacionDTO = consultarEstadoAfiliacionRespectoCCF(
                resulDTO.getTipoIdentificacion(),
                resulDTO.getNumeroIdentificacion());

        // Se consulta la informacion de la persona como Beneficiario
        List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiarioTipoNroIdentificacion(
                resulDTO.getTipoIdentificacion(),
                resulDTO.getNumeroIdentificacion());
        if (listaBeneficiarios == null || listaBeneficiarios.isEmpty()) {
            // Información afiliado
            persona.setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO);
            persona.setTipoIdentificacion(resulDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacion(resulDTO.getNumeroIdentificacion());
            persona.setTipoIdentificacionTrabajador(resulDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacionTrabajador(resulDTO.getNumeroIdentificacion());

            // Se consultan las clasificaciones del afiliado
            List<ClasificacionEnum> clasificaciones = consultarClasificacion(resulDTO.getTipoIdentificacion(),
                    resulDTO.getNumeroIdentificacion());
            if (clasificaciones != null && !clasificaciones.isEmpty()) {
                nuevaNovedad.setClasificacion(clasificaciones.get(0));
            }
        } else {
            // Se obtienen los datos basicos de beneficiario para el registro de la novedad
            BeneficiarioModeloDTO beneficiarioModeloDTO = listaBeneficiarios.get(0);
            llenarDatosBeneficiarioNovedadFallecimiento(beneficiarioModeloDTO, persona,
                    consultaEstadoAfiliacionDTO.getEstadoAfiliacion());

            persona.setMotivoDesafiliacionBeneficiario(MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO);
            nuevaNovedad.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
        }

        // Se realiza el registro de una novedad de fallecimiento
        if (nuevaNovedad.getClasificacion() != null && nuevaNovedad.getTipoTransaccion() != null) {
            // Se asocia la informacion de la persona
            nuevaNovedad.setDatosPersona(persona);
            // Se registra la novedad
            radicarSolicitudNovedad(nuevaNovedad, userDTO);
        }
        logger.debug("Finaliza registrarSupervivenciaCanceladaPorMuerte(UserDTO, ResultadoSupervivenciaDTO, "
                + numeroRadicado + ")");
    }

    /**
     * Ejecuta la inactivacion del beneficiario si es contrado fallecido en el
     * archivo supervivencia
     *
     * @param nuevaNovedad       Información solicitud novedad
     * @param resulDTO           Información de resultado de lectura archivo
     *                           supervivencia
     * @param listaBeneficiarios Lista de beneficiarios asociadas a la persona
     *                           encontrada
     * @param userDTO            Usuario del contexto
     * @throws Exception
     */
    /*
     * private void tramitarSupervivenviaBeneficiario(CanalRecepcionEnum canal,
     * ResultadoSupervivenciaDTO resulDTO,
     * List<BeneficiarioModeloDTO> listaBeneficiarios, UserDTO userDTO) throws
     * Exception {
     * if (listaBeneficiarios == null || listaBeneficiarios.isEmpty()) {
     * return;
     * }
     * // Fecha reporte Fallecimiento beneficiario
     * Long fechaReporte = Calendar.getInstance().getTimeInMillis();
     *
     * for (BeneficiarioModeloDTO beneficiarioModeloDTO : listaBeneficiarios) {
     * DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
     * persona.setFechaDefuncion(resulDTO.getFechaDefuncion());
     * persona.setFechaReporteFallecimientoTrabajador(fechaReporte);
     * persona.setPersonaFallecidaTrabajador(Boolean.TRUE);
     * persona.setMotivoDesafiliacionBeneficiario(
     * MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO);
     *
     * // Se obtienen los datos basicos de beneficiario para el registro de la
     * novedad
     * llenarDatosBeneficiarioNovedad(beneficiarioModeloDTO, persona, null);
     *
     * // Se crea la solicitud novedad basica
     * SolicitudNovedadDTO nuevaNovedad = new SolicitudNovedadDTO();
     * nuevaNovedad.setCanalRecepcion(canal);
     * nuevaNovedad.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
     * nuevaNovedad.setTipoTransaccion(
     * getTipoTransaccionInactivarBeneficiarioByClasificacion(beneficiarioModeloDTO.
     * getTipoBeneficiario()));
     * if (nuevaNovedad.getClasificacion() != null &&
     * nuevaNovedad.getTipoTransaccion() != null) {
     * //Se asocia la informacion de la persona
     * nuevaNovedad.setDatosPersona(persona);
     * // Se registra la novedad
     * radicarSolicitudNovedad(nuevaNovedad, userDTO);
     * }
     * }
     * }
     */
    /**
     * Transforma la información básica del beneficiario para el registro de la
     * novedad
     *
     * @param beneficiarioModeloDTO Información beneficiario
     * @param persona               Modelo para el registro de novedad
     */
    private void llenarDatosBeneficiarioNovedad(BeneficiarioModeloDTO beneficiarioModeloDTO,
                                                DatosPersonaNovedadDTO persona, EstadoAfiliadoEnum estadoAfiliado) {
        NovedadesCompositeUtils.llenarDatosBeneficiarioNovedad(beneficiarioModeloDTO, persona, estadoAfiliado);
    }

    /**
     * Transforma la información básica del beneficiario para el registro de la
     * novedad
     *
     * @param beneficiarioModeloDTO Información beneficiario
     * @param persona               Modelo para el registro de novedad
     */
    private void llenarDatosBeneficiarioNovedadFallecimiento(BeneficiarioModeloDTO beneficiarioModeloDTO,
                                                             DatosPersonaNovedadDTO persona, EstadoAfiliadoEnum estadoAfiliado) {
        logger.info(
                "Inicia llenarDatosBeneficiarioNovedadFallecimiento(BeneficiarioModeloDTO beneficiarioModeloDTO, DatosPersonaNovedadDTO persona, EstadoAfiliadoEnum estadoAfiliado)");
        // Info Afiliado
        if (estadoAfiliado != null && estadoAfiliado.equals(EstadoAfiliadoEnum.ACTIVO)) {
            logger.info("El estado del afiliado es ACTIVO.");
            persona.setTipoIdentificacion(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacion(beneficiarioModeloDTO.getNumeroIdentificacion());
            persona.setTipoIdentificacionTrabajador(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacionTrabajador(beneficiarioModeloDTO.getNumeroIdentificacion());
            logger.info("Datos del afiliado activo asignados correctamente.");
        } else {
            logger.info("El estado del afiliado no es ACTIVO o es nulo.");
            persona.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
            persona.setTipoIdentificacionBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacionBeneficiario(beneficiarioModeloDTO.getNumeroIdentificacion());
            persona.setTipoIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getNumeroIdentificacion());
            persona.setPrimerApellidoBeneficiario(beneficiarioModeloDTO.getPrimerApellido());
            persona.setSegundoApellidoBeneficiario(beneficiarioModeloDTO.getSegundoApellido());
            persona.setPrimerNombreBeneficiario(beneficiarioModeloDTO.getPrimerNombre());
            persona.setSegundoNombreBeneficiario(beneficiarioModeloDTO.getSegundoNombre());
            logger.info("Datos del beneficiario inactivo o fallecido asignados correctamente.");
            llenarDatosAfiliadoNovedad(beneficiarioModeloDTO.getAfiliado(), persona);
            logger.info("Datos adicionales del afiliado relacionados con la novedad asignados correctamente.");
        }
        logger.info(
                "Finaliza llenarDatosBeneficiarioNovedadFallecimiento(BeneficiarioModeloDTO beneficiarioModeloDTO, DatosPersonaNovedadDTO persona, EstadoAfiliadoEnum estadoAfiliado)");
    }

    /**
     * Transforma la información básica del afiliado para el registro de la
     * novedad
     *
     * @param afiliado Información afiliado
     * @param persona  Modelo para el registro de novedad
     */
    private void llenarDatosAfiliadoNovedad(AfiliadoModeloDTO afiliado, DatosPersonaNovedadDTO persona) {
        logger.info("Inicia llenarDatosAfiliadoNovedad");

        // logger.debug("Datos del afiliado a procesar: {}", afiliado);
        // logger.debug("Datos de la persona a procesar: {}", persona);
        NovedadesCompositeUtils.llenarDatosAfiliadoNovedad(afiliado, persona);
        logger.info("Finaliza llenarDatosAfiliadoNovedad");
    }

    /**
     * Obtiene la información del tipo de transacción para la inactivación de la
     * clasificación enviada
     *
     * @param clasificacion Clasificación de beneficiario a inactivar
     * @return Tipo transaccion enum
     */
    private TipoTransaccionEnum getTipoTransaccionInactivarBeneficiarioByClasificacion(
            ClasificacionEnum clasificacion) {
        // logger.info("Iniciando el método getTipoTransaccionInactivarBeneficiarioByClasificacion con clasificación: {}", clasificacion);
        return NovedadesCompositeUtils.getTipoTransaccionInactivarBeneficiarioByClasificacion(clasificacion);
    }

    /**
     * Ejecuta la inactivacion de todos los roles del afiliado encontrado
     * fallecido en el archivo de supervivencia
     *
     * @param nuevaNovedad Información solicitud novedad
     * @param resulDTO     Información de resultado de lectura archivo supervivencia
     * @param afiliado     Informacion afiliado encontrado
     * @param userDTO      Usuario del contexto
     * @throws Exception
     */
    /*
     * private void tramitarSupervivenciaAfiliado(CanalRecepcionEnum canal,
     * ResultadoSupervivenciaDTO resulDTO,
     * ConsultarAfiliadoOutDTO afiliado, UserDTO userDTO) throws Exception {
     * if (afiliado == null || afiliado.getInformacionLaboralTrabajador() == null
     * || afiliado.getInformacionLaboralTrabajador().isEmpty()) {
     * return;
     * }
     * // Se consultan las clasificaciones del afiliado
     * List<ClasificacionEnum> clasificaciones =
     * consultarClasificacion(resulDTO.getTipoIdentificacion(),
     * resulDTO.getNumeroIdentificacion());
     *
     * // Fecha reporte Fallecimiento beneficiario
     * Long fechaReporte = Calendar.getInstance().getTimeInMillis();
     *
     * for (InformacionLaboralTrabajadorDTO informacionLaboral :
     * afiliado.getInformacionLaboralTrabajador()) {
     * // Se agrega a la novedad toda la información necesario para realizar el
     * retiro
     * DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
     * persona.setFechaDefuncion(resulDTO.getFechaDefuncion());
     * persona.setFechaReporteFallecimientoTrabajador(fechaReporte);
     * persona.setPersonaFallecidaTrabajador(Boolean.TRUE);
     * persona.setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum.
     * FALLECIMIENTO);
     * // Información afiliado
     * persona.setTipoIdentificacion(resulDTO.getTipoIdentificacion());
     * persona.setNumeroIdentificacion(resulDTO.getNumeroIdentificacion());
     * persona.setTipoIdentificacionTrabajador(resulDTO.getTipoIdentificacion());
     * persona.setNumeroIdentificacionTrabajador(resulDTO.getNumeroIdentificacion())
     * ;
     * persona.setIdRolAfiliado(informacionLaboral.getIdRolAfiliado());
     *
     * // Se crea la solicitud novedad basica
     * SolicitudNovedadDTO nuevaNovedad = new SolicitudNovedadDTO();
     * nuevaNovedad.setCanalRecepcion(canal);
     * nuevaNovedad.setClasificacion(obtenerClasificacionTipoAfiliado(
     * clasificaciones, informacionLaboral.getTipoAfiliado()));
     * nuevaNovedad.setTipoTransaccion(
     * obtenerTransaccionRetiroTipoAfiliado(informacionLaboral.getTipoAfiliado(),
     * nuevaNovedad.getClasificacion()));
     *
     * if (nuevaNovedad.getClasificacion() != null &&
     * nuevaNovedad.getTipoTransaccion() != null) {
     * //Se asocia la informacion de la persona
     * nuevaNovedad.setDatosPersona(persona);
     * // Se registra la novedad
     * radicarSolicitudNovedad(nuevaNovedad, userDTO);
     * }
     * }
     * }
     */
    /**
     * Entrega el tipo de transaccion para el retiro de afiliado a partir del
     * tipo de afiliación y la clasificación
     *
     * @param tipoAfiliado  Información tipo afiliado
     * @param clasificacion Información clasificación
     * @return Tipo transacción de retiro
     */
    private TipoTransaccionEnum obtenerTransaccionRetiroTipoAfiliado(TipoAfiliadoEnum tipoAfiliado,
                                                                     ClasificacionEnum clasificacion) {
        TipoTransaccionEnum tipoTransaccion = null;
        switch (tipoAfiliado) {
            case PENSIONADO:
                tipoTransaccion = obtenerTipoTransaccionRetiroPensionado(clasificacion);
                break;
            case TRABAJADOR_DEPENDIENTE:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE;
                break;
            case TRABAJADOR_INDEPENDIENTE:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE;
                break;
            default:
                break;
        }
        return tipoTransaccion;
    }

    @Override
    public void radicarSolicitudNovedadAutomaticaSinValidaciones(SolicitudNovedadDTO nuevaNovedad, UserDTO userDTO) {
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.radicarSolicitudNovedadAutomaticaSinValidaciones(nuevaNovedad, userDTO);
    }

    /**
     * Genera el tipo de trasaccion de retiro de pensionado de acuerdo a la
     * clasificacion del mismo
     *
     * @param clasificacion Clasificacion del pensionado
     * @return Tipo de trasaccion
     */
    private TipoTransaccionEnum obtenerTipoTransaccionRetiroPensionado(ClasificacionEnum clasificacion) {
        TipoTransaccionEnum tipoTransaccion = null;
        if (clasificacion == null) {
            return tipoTransaccion;
        }
        switch (clasificacion) {
            // case PENSION_FAMILIAR:
            //     tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR;
            //     break;
            case MAS_1_5_SM_0_6_POR_CIENTO:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6;
                break;
            case MAS_1_5_SM_2_POR_CIENTO:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2;
                break;
            case MENOS_1_5_SM_0_6_POR_CIENTO:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6;
                break;
            case MENOS_1_5_SM_0_POR_CIENTO:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0;
                break;
            case MENOS_1_5_SM_2_POR_CIENTO:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2;
                break;
            case FIDELIDAD_25_ANIOS:
                tipoTransaccion = TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS;
                break;
            default:
                break;
        }
        return tipoTransaccion;
    }

    /**
     * Obtiene la clasificacion del tipo afiliado para el registro del tramite
     *
     * @param listaClasificacion Lista de clasificaciones por afiliado
     * @param tipoAfiliado       Tipo de afiliado a buscar
     * @return Clasiifcacion aplicada al proceso o nulo si no se encuentra
     */
    private ClasificacionEnum obtenerClasificacionTipoAfiliado(List<ClasificacionEnum> listaClasificacion,
                                                               TipoAfiliadoEnum tipoAfiliado) {
        if (listaClasificacion == null || listaClasificacion.isEmpty() || tipoAfiliado == null) {
            logger.debug("obtenerClasificacionTipoAfiliado(" + listaClasificacion + ", " + tipoAfiliado
                    + ") :: No se enviaron parámetros");
            return null;
        }
        for (ClasificacionEnum clasificacionEnum : listaClasificacion) {
            if (clasificacionEnum.getSujetoTramite().equals(tipoAfiliado)) {
                return clasificacionEnum;
            }
        }
        return null;
    }

    /**
     * Consulta la informacion del afiliado por los datos de identificacion de
     * persona
     *
     * @param tipoIdentificacion   Tipo de documento
     * @param numeroIdentificacion Numero de documento
     * @return Informacion afiliado
     */
    private ConsultarAfiliadoOutDTO consultarAfiliados(TipoIdentificacionEnum tipoIdentificacion,
                                                       String numeroIdentificacion) {
        logger.debug("Inicia consultarAfiliados(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        ConsultarAfiliado consultarAfiliado = new ConsultarAfiliado(numeroIdentificacion, tipoIdentificacion, true);
        consultarAfiliado.execute();
        ConsultarAfiliadoOutDTO resultado = consultarAfiliado.getResult();
        logger.debug("Finaliza consultarAfiliados(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        return resultado;
    }

    /**
     * Consulta la informacion de beneficiario de una persona por los datos de
     * identificacion
     *
     * @param tipoIdentificacion   Tipo de documento
     * @param numeroIdentificacion Numero de documento
     * @return Lista informacion beneficiarios
     */
    private List<BeneficiarioModeloDTO> consultarBeneficiarioTipoNroIdentificacion(
            TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        ConsultarBeneficiarioTipoNroIdentificacion service = new ConsultarBeneficiarioTipoNroIdentificacion(
                numeroIdentificacion,
                tipoIdentificacion);
        service.execute();
        return service.getResult();
    }

    private ConsultaEstadoAfiliacionDTO consultarEstadoAfiliacionRespectoCCF(TipoIdentificacionEnum tipoIdentificacion,
                                                                             String numeroIdentificacion) {
        ConsultarEstadoAfiliacionRespectoCCF consultarEstadoAfiliacionRespectoCCF = new ConsultarEstadoAfiliacionRespectoCCF(
                null, tipoIdentificacion, numeroIdentificacion);
        consultarEstadoAfiliacionRespectoCCF.execute();
        return consultarEstadoAfiliacionRespectoCCF.getResult();
    }

    /**
     * Consulta las clasificaciones por los datos de identificacion de persona
     *
     * @param tipoIdentificacion   Tipo de documento
     * @param numeroIdentificacion Numero de documento
     * @return Lista de clasificaciones asociadas a la persona
     */
    private List<ClasificacionEnum> consultarClasificacion(TipoIdentificacionEnum tipoIdentificacion,
                                                           String numeroIdentificacion) {
        logger.info("**__**Inicia consultarClasificacion(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        ConsultarClasificacionesAfiliado consultarClasificacion = new ConsultarClasificacionesAfiliado(
                numeroIdentificacion,
                tipoIdentificacion);
        consultarClasificacion.execute();
        List<ClasificacionEnum> resultado = consultarClasificacion.getResult();
        logger.info("Finaliza consultarClasificacion(" + tipoIdentificacion + ", " + numeroIdentificacion + ")");
        return resultado;
    }

    /**
     * Método encargado de ejecutar las validaciones de una novedad.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return resultado de las validaciones.
     */
    private ResultadoRadicacionSolicitudEnum validarNovedad(SolicitudNovedadDTO solNovedadDTO) {
        logger.info("Inicio de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();

        if (solNovedadDTO.getDatosEmpleador() != null) {
            Map<String, String> datosValidacion = llenarDatosValidacionEmpleador(solNovedadDTO);
            logger.info("prueba de clasificacion"+ solNovedadDTO.getClasificacion());

            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    solNovedadDTO.getTipoTransaccion().name(), solNovedadDTO.getTipoTransaccion().getProceso(),
                    solNovedadDTO.getClasificacion().name(), datosValidacion);
            validarReglasService.execute();
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
        } else if (solNovedadDTO.getDatosPersona() != null) {
            logger.info("entra555)");
            Map<String, String> datosValidacion = llenarDatosValidacionPersona(solNovedadDTO);
            for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {

                logger.info("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            }
            logger.info("entra777getidsolicitud&&&)" + solNovedadDTO.getIdSolicitud());
            logger.info("entra777getidsolicitudNovedad///)" + solNovedadDTO.getIdSolicitudNovedad());
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    solNovedadDTO.getTipoTransaccion().name(), solNovedadDTO.getTipoTransaccion().getProceso(),
                    solNovedadDTO.getClasificacion().name(), datosValidacion);
            logger.info("entra122111)");
            validarReglasService.execute();
            logger.info("entra111111)");
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
            logger.info("entra3333)");
        } else if (solNovedadDTO.getDatosPersonaMultiple() != null) {
            logger.info("entra a elseif getDatosPersonaMultiple - 89526 ");
            logger.info("solNovedadDTO.getDatosPersonaMultiple() " +solNovedadDTO.getDatosPersonaMultiple().size());
            for (DatosPersonaNovedadDTO datosPersona : solNovedadDTO.getDatosPersonaMultiple()) {
                // Guardar el estado original de DatosPersona
                DatosPersonaNovedadDTO datosPersonaOriginal = datosPersona;

                // Configurar los datos de la persona individualmente
                solNovedadDTO.setDatosPersona(datosPersona);

                // Llamar al método llenarDatosValidacionPersonaBeneficiario existente
                Map<String, String> datosValidacion = llenarDatosValidacionPersonaBeneficiario(solNovedadDTO);



                // Restaurar el estado original de DatosPersona
                solNovedadDTO.setDatosPersona(datosPersona);

                logger.info("solNovedadDTO setDatosPersona " +solNovedadDTO.getDatosPersona());

                for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {
                    logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }

                logger.info("entra getDatosPersonaMultiple getidsolicitud - 89526 " + solNovedadDTO.getIdSolicitud());
                logger.info("entra getDatosPersonaMultiple getidsolicitudNovedad - 89526 " + solNovedadDTO.getIdSolicitudNovedad());

                // Acceder a la clasificación desde datosPersona
                String clasificacion = datosPersona.getClasificacion() != null ? datosPersona.getClasificacion().name() : null;


                if (Boolean.TRUE.equals(datosPersona.getCondicionInvalidezHijo()) && (clasificacion.equals(ClasificacionEnum.HIJO_BIOLOGICO.name()) || clasificacion.equals(ClasificacionEnum.HIJO_ADOPTIVO.name()) || clasificacion.equals(ClasificacionEnum.HIJASTRO.name()) || clasificacion.equals(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name()))) {
                    datosValidacion.put("condicionInvalidez", String.valueOf(Boolean.TRUE));
                }else if (Boolean.TRUE.equals(datosPersona.getCondicionInvalidezPadre()) && (clasificacion.equals(ClasificacionEnum.PADRE.name()) || (clasificacion.equals(ClasificacionEnum.MADRE.name())))){
                    datosValidacion.put("condicionInvalidez", String.valueOf(Boolean.TRUE));
                }

                logger.info("datosValidacion " +datosValidacion);
                logger.info("clasificacion " +clasificacion);
                logger.info("solNovedadDTO.getTipoTransaccion().name() " +solNovedadDTO.getTipoTransaccion().name());
                logger.info("solNovedadDTO.getTipoTransaccion().getProceso() " +solNovedadDTO.getTipoTransaccion().getProceso());

                ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                        solNovedadDTO.getTipoTransaccion().name(), solNovedadDTO.getTipoTransaccion().getProceso(),
                        clasificacion, datosValidacion); // Usar la clasificación de datosPersona
                logger.info("entra getDatosPersonaMultiple validarReglasService - 89526");
                validarReglasService.execute();
                logger.info("entra getDatosPersonaMultiple validaciones - 89526");
                validaciones.addAll((List<ValidacionDTO>) validarReglasService.getResult());
                logger.info("validaciones finales " +validaciones);
            }
        }

        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        if (!validaciones.isEmpty()) {
            List<ValidacionDTO> listValidacionTipoUno = new ArrayList<>();
            // Se verifica el resultado de las validaciones
            resultado = verificarResultadoValidacion(listValidacionTipoUno, validaciones, solNovedadDTO);
            logger.info("Resultado de las validaciones realizadas: " + resultado);
            // Se agrega la lista de validaciones fallidas
            if (!listValidacionTipoUno.isEmpty()) {
                solNovedadDTO.setListResultadoValidacion(listValidacionTipoUno);
                for (ValidacionDTO validacion : listValidacionTipoUno) {
                    logger.info("Validacion: " + validacion.getDetalle() + " de bloque: "+ validacion.getBloque());
                }
            }
        }
        logger.info("Fin de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        return resultado;
    }

    /**
     * Realiza el proceso de verificacion del resultado de las validaciones
     * ejecutadas, para indicar el comportamiento en pantalla
     *
     * @param listaValidacionesFallidas Contiene la lista de validaciones
     *                                  fallidas por tipo de excepcion
     * @param validacionesEjecutadas    Lista de validaciones ejecutadas
     * @param solNovedadD               TO Informacion de la solicitud de novedad
     * @return Resultado de radicacion de la solicitud a partir del resultado de
     *         las validaciones
     */
    private ResultadoRadicacionSolicitudEnum verificarResultadoValidacion(List<ValidacionDTO> listaValidacionesFallidas,
                                                                          List<ValidacionDTO> validacionesEjecutadas, SolicitudNovedadDTO solNovedadDTO) {
        // Resultado verificacion validaciones
        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        List<ValidacionDTO> listaValidacionesT2 = new ArrayList<>();
        List<ValidacionDTO> listaValidacionesT1 = new ArrayList<>();
        boolean excepcionUno = false;
        boolean excepcionDos = false;

        for (ValidacionDTO validacionDTO : validacionesEjecutadas) {
            // Si la validacion fue NO_APROBADA se verifica el tipo de excepcion para el
            // comportamiento en pantalla
            if (validacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                resultado = ResultadoRadicacionSolicitudEnum.FALLIDA;
                if (TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacionDTO.getTipoExcepcion())) {
                    excepcionDos = true;
                    listaValidacionesT2.add(validacionDTO);
                } else {
                    listaValidacionesT1.add(validacionDTO);
                    excepcionUno = true;
                }
                // Se verifica si la validacion 90 de postulado FOVIS fallo
                if (validacionDTO.getValidacion().equals(ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS)) {
                    solNovedadDTO.setPostuladoFOVIS(true);
                }
                // Se verifica si la validacion 111 de novedad en proceso fallo
                if (validacionDTO.getValidacion()
                        .equals(ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO)) {
                    solNovedadDTO.setNovedadEnProceso(true);
                }
                // Se verifica si la validacion DE PERSONA FALLECIDA falló
                if (validacionDTO.getValidacion().equals(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA)) {
                    solNovedadDTO.setValidacionFallecido(true);
                }
            }
        }
        // Se identifica el error ocurrido y se agrega la lista de validaciones

        if (excepcionDos) {
            solNovedadDTO.setExcepcionTipoDos(true);
            listaValidacionesFallidas.addAll(listaValidacionesT2);
        } else if (excepcionUno) {
            solNovedadDTO.setExcepcionTipoDos(false);
            listaValidacionesFallidas.addAll(listaValidacionesT1);
        }
        return resultado;
    }

    /**
     * Método que identifica los atributos de identificacion y los asigna en las
     * variables correspondientes
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores asignados de identificacion
     */
    private Map<String, String> llenarCamposDeIdentificacion(SolicitudNovedadDTO solNovedadDTO) {
        Map<String, String> datosValidacion = new HashMap<String, String>();

        // se verifica que los datos de identificación entrantes, asociados al
        // beneficiario,
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiario() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario().equals("")) {
            datosValidacion.put("tipoIdentificacionBeneficiario",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiario().name());
            datosValidacion.put("numeroIdentificacionBeneficiario",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario());
        }

        // Se verifica si los datos de identificacion del beneficiario cambiaron
        // para tenerlos en cuenta en las validaciones
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiarioAnterior() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior().equals("")) {
            datosValidacion.put("tipoIdentificacionBeneficiarioAnterior",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiarioAnterior().name());
            datosValidacion.put("numeroIdentificacionBeneficiarioAnterior",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior());
        }
        // se verifica que los datos de identificación entrantes, asociados al
        // afiliado no sean nulos ni vacios
        if ((solNovedadDTO.getDatosPersona().getTipoIdentificacion() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacion() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacion().equals("")) && !solNovedadDTO.getTipoTransaccion().equals("ACTIVAR_BENEFICIARIOS_MULTIPLES_PRESENCIAL") ) {
            datosValidacion.put(TIPO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getTipoIdentificacion().name());
            datosValidacion.put(NUMERO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
        }else{
            datosValidacion.put(TIPO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiario().name());
            datosValidacion.put(NUMERO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario());
        }

        // se verifica que los datos de identificación entrantes, asociados al
        // afiliado (trabajador),
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionTrabajador() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador().equals("")) {
            datosValidacion.put("tipoIdentificacionTrabajador",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionTrabajador().name());
            datosValidacion.put("numeroIdentificacionTrabajador",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador());
        }

        // se verifica que los datos de identificación entrantes, asociados al
        // empleador,
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador().equals("")) {
            datosValidacion.put("tipoIdentificacionEmpleador",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador().name());
            datosValidacion.put("numeroIdentificacionEmpleador",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador());
        }

        // retorna un Map con los valores encontrados
        return datosValidacion;
    }

    /**
     * Método encargado de llenar los datos de validación segun la novedad de
     * persona.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersona(SolicitudNovedadDTO solNovedadDTO) {
        logger.info("Inicio de método llenarDatosValidacionPersona(DatosPersonaNovedadDTO datosPersonaDTO)");

        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        // se obtienen las clasificaciones para afiliado y beneficiario
        List<ClasificacionEnum> afiliado = PersonasUtils.ListarClasificacionAfiliado();
        List<ClasificacionEnum> beneficiario = PersonasUtils.ListarClasificacionBeneficiario();

        // se verifica si la novedad entrante está asociada al afiliado
        // principal, beneficiario o empleador
        // y se procede a invocar el metodo correspondiente para cada caso.
        if (afiliado.contains(solNovedadDTO.getClasificacion())) {
            datosValidacion = llenarDatosValidacionPersonaAfiliado(solNovedadDTO);
        } else if (beneficiario.contains(solNovedadDTO.getClasificacion())) {
            datosValidacion = llenarDatosValidacionPersonaBeneficiario(solNovedadDTO);
        }
        // si el campo que contiene el id del grupo familiar no viene vacío, se
        // agrega a los datos de validación
        if (solNovedadDTO.getDatosPersona().getIdGrupoFamiliar() != null
                && !solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().equals("")) {
            datosValidacion.put("idGrupoFamiliar", solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().toString());
        } else if (solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario() != null
                && solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar() != null
                && !solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().equals("")) {
            // Si el idGrupoFamiliar() es vacio consultar el grupo familiar del beneficiario
            datosValidacion.put("idGrupoFamiliar",
                    solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().toString());
        }
        // Si el campo idRolAfiliado se envio, se agrega para la ejecucion de
        // validaciones
        if (solNovedadDTO.getDatosPersona().getIdRolAfiliado() != null) {
            datosValidacion.put("idRolAfiliado", solNovedadDTO.getDatosPersona().getIdRolAfiliado().toString());
        }
        // Se agregan las fechas de inicio y fin que se envian
        String fechaInicio = null;
        if (solNovedadDTO.getDatosPersona().getFechaInicioNovedad() != null) {
            fechaInicio = solNovedadDTO.getDatosPersona().getFechaInicioNovedad().toString();
        }
        String fechaFin = null;
        if (solNovedadDTO.getDatosPersona().getFechaFinNovedad() != null) {
            fechaFin = solNovedadDTO.getDatosPersona().getFechaFinNovedad().toString();
        }
        String fechaRetiro = null;
        if (solNovedadDTO.getDatosPersona().getFechaRetiro() != null) {
            fechaRetiro = solNovedadDTO.getDatosPersona().getFechaRetiro().toString();
        }
        // Se agrega el campo condicionInvalidez para su validación (Caso beneficiario
        // tipo Padre)
        // if (solNovedadDTO.getDatosPersona().getCondicionInvalidezPadre() != null) {
        //     datosValidacion.put("condicionInvalidez",
        //             solNovedadDTO.getDatosPersona().getCondicionInvalidezPadre().toString());
        // }
        datosValidacion.put("fechaInicio", fechaInicio);
        datosValidacion.put("fechaFin", fechaFin);
        datosValidacion.put("fechaRetiro", fechaRetiro);
        // Se agrega el tipo transaccion como dato para el proceso de validacion
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        logger.info("Fin de método llenarDatosValidacionPersona(DatosEmpleadorNovedadDTO datosEmpleadorDTO)");
        return datosValidacion;
    }

    /**
     * Método encargado de llenar los datos de validacion cunado la persona está
     * clasificada como afiliado
     *
     * @param solNovedadDTO datos de la solicitud de novedad
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersonaAfiliado(SolicitudNovedadDTO solNovedadDTO) {
        // se invoca el método común que obtiene los campos de identificación
        Map<String, String> datosValidacion = llenarCamposDeIdentificacion(solNovedadDTO);


        logger.info("Validacion idSolicitudCargueMasivo ::" + solNovedadDTO.getIdSolicitudCargueMasivo());
        if (solNovedadDTO.getIdSolicitudCargueMasivo() != null) {
            datosValidacion.put("idSolicitudCargueMasivo", solNovedadDTO.getIdSolicitudCargueMasivo().toString());
        }

        if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL)) {
            datosValidacion.put("mismaDireccionAfiliadoPrincipalGrupoFam",
                    solNovedadDTO.getDatosPersona().getMismaDireccionAfiliadoPrincipalGrupoFam().toString());
        }

        datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosPersona().getPrimerNombreTrabajador());
        datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosPersona().getSegundoNombreTrabajador());
        datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosPersona().getPrimerApellidoTrabajador());
        datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosPersona().getSegundoApellidoTrabajador());
        if (solNovedadDTO.getDatosPersona().getFechaNacimientoTrabajador() != null) {
            datosValidacion.put(FECHA_NACIMIENTO,
                    solNovedadDTO.getDatosPersona().getFechaNacimientoTrabajador().toString());
        }
        if (solNovedadDTO.getDatosPersona().getInactivarCuentaWeb() != null) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString());
        }
        return datosValidacion;
    }

    /**
     * Método encargado de llenar los datos de validacion cunado la persona está
     * clasificada como beneficiario
     *
     * @param solNovedadDTO datos de la solicitud de novedad
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersonaBeneficiario(SolicitudNovedadDTO solNovedadDTO) {
        // se invoca el método común que obtiene los campos de identificación
        Map<String, String> datosValidacion = llenarCamposDeIdentificacion(solNovedadDTO);

        if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS)) {
            datosValidacion.put("mismaDireccionAfiliadoPrincipalGrupoFam",
                    solNovedadDTO.getDatosPersona().getMismaDireccionAfiliadoPrincipalGrupoFam().toString());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS)) {
            datosValidacion.put("condicionInvalidez",
                    solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo() != null
                            ? solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo().toString()
                            : null);
        } else if (getListTransaccionActivarBeneficiario().contains(solNovedadDTO.getTipoTransaccion())) {
            datosValidacion.put("condicionInvalidez",
                    solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo() != null
                            ? solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo().toString()
                            : null);

            datosValidacion.put("certificadoEscolar",
                    solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo() != null
                            ? solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo().toString()
                            : null);

            datosValidacion.put("estudianteTrabajoDesarrolloHumano",
                    solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo() != null
                            ? solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo().toString()
                            : null);

            datosValidacion.put("fechaRecepcionCertificado",
                    solNovedadDTO.getDatosPersona().getFechaReporteCertEscolarHijo() != null
                            ? solNovedadDTO.getDatosPersona().getFechaReporteCertEscolarHijo().toString()
                            : null);

            datosValidacion.put("fechaVencimientoCertificado",
                    solNovedadDTO.getDatosPersona().getFechaVencimientoCertEscolar() != null
                            ? solNovedadDTO.getDatosPersona().getFechaVencimientoCertEscolar().toString()
                            : null);

        }else if (getListTransaccionActualizarCertificado().contains(solNovedadDTO.getTipoTransaccion())) {
            datosValidacion.put("certificadoEscolar",
                    solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo() != null
                            ? solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo().toString()
                            : null);

            datosValidacion.put("estudianteTrabajoDesarrolloHumano",
                    solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo() != null
                            ? solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo().toString()
                            : null);

            datosValidacion.put("beneficioProgramaTrabajoDesarrollo",
                    solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo() != null
                        ? solNovedadDTO.getDatosPersona().getBeneficioProgramaTrabajoDesarrollo().toString()
                        : null);
            

            datosValidacion.put("certificadoEscolarHijo",
                    solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo() != null
                            ? solNovedadDTO.getDatosPersona().getCertificadoEscolarHijo().toString()
                            : null);

        }

        datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosPersona().getPrimerNombreBeneficiario());
        datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosPersona().getSegundoNombreBeneficiario());
        datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosPersona().getPrimerApellidoBeneficiario());
        datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosPersona().getSegundoApellidoBeneficiario());
        if (solNovedadDTO.getDatosPersona().getFechaNacimientoBeneficiario() != null) {
            datosValidacion.put(FECHA_NACIMIENTO,
                    solNovedadDTO.getDatosPersona().getFechaNacimientoBeneficiario().toString());
        }
        if (solNovedadDTO.getDatosPersona().getInactivarCuentaWeb() != null
                && !solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString().equals("")) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString());
        }

        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        logger.info("llenarDatosValidacionPersonaBeneficiario -- datosValidacion " +datosValidacion);
        return datosValidacion;
    }

    /**
     * Método encargado de llenar los datos de validación segun la novedad.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionEmpleador(SolicitudNovedadDTO solNovedadDTO) {
        logger.debug("Inicio de método llenarDatosValidacion(DatosEmpleadorNovedadDTO datosEmpleadorDTO)");
        Map<String, String> datosValidacion = new HashMap<String, String>();
        /* todas las validaciones piden tipo y numero identificacion */
        if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO)) {
            datosValidacion.put(TIPO_IDENTIFICACION,
                    solNovedadDTO.getDatosEmpleador().getTipoIdentificacionNuevo().name());
            datosValidacion.put(NUMERO_IDENTIFICACION,
                    solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionNuevo());
        } else {
            datosValidacion.put(TIPO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getTipoIdentificacion().name());
            datosValidacion.put(NUMERO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getNumeroIdentificacion());
        }

        /* algunos datos adicionales dependiendo de la novedad */
        if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_RAZON_SOCIAL_NOMBRE)) {
            datosValidacion.put("razonSocial", solNovedadDTO.getDatosEmpleador().getRazonSocial());
            datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosEmpleador().getPrimerNombre());
            datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosEmpleador().getSegundoNombre());
            datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosEmpleador().getPrimerApellido());
            datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosEmpleador().getSegundoApellido());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB)) {
            if (solNovedadDTO.getDatosEmpleador().getTipoIdentificacionRepLegalSupl() != null) {
                datosValidacion.put("tipoIdentificacionRLS",
                        solNovedadDTO.getDatosEmpleador().getTipoIdentificacionRepLegalSupl().name());
                datosValidacion.put("numeroIdentificacionRLS",
                        solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionRepLegalSupl());
            }
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.SUSTITUCION_PATRONAL)) {
            StringBuilder idsPersonas = new StringBuilder();
            for (SucursalPersonaDTO sucursalPersona : solNovedadDTO.getDatosEmpleador().getTrabajadoresSustPatronal()) {
                idsPersonas.append(sucursalPersona.getIdPersona().toString());
                idsPersonas.append(",");
            }
            datosValidacion.put("idsPersonas", idsPersonas.substring(0, idsPersonas.length() - 1));
            datosValidacion.put("tipoIdentificacionEmpleadorDestino",
                    solNovedadDTO.getDatosEmpleador().getTipoIdentificacionDestinoSustPatronal().name());
            datosValidacion.put("numeroIdentificacionEmpleadorDestino",
                    solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionDestinoSustPatronal());
            datosValidacion.put("idEmpleadorDestino",
                    solNovedadDTO.getDatosEmpleador().getIdEmpleadorDestinoSustPatronal().toString());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES)) {
            StringBuilder idsPersonas = new StringBuilder();
            for (Long id : solNovedadDTO.getDatosEmpleador().getTrabajadoresTraslado()) {
                idsPersonas.append(id.toString());
                idsPersonas.append(",");
            }
            datosValidacion.put("idsPersonas", idsPersonas.substring(0, idsPersonas.length() - 1));
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB)
                || solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB)) {
            datosValidacion.put("beneficioCubierto1429",
                    solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey1429().toString());
            if (solNovedadDTO.getDatosEmpleador().getAnoInicioBeneficioLey1429() != null) {
                datosValidacion.put("anioInicioBeneficio",
                        solNovedadDTO.getDatosEmpleador().getAnoInicioBeneficioLey1429().toString());
            }
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_WEB)
                || solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_WEB)) {
            if (solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey590() != null) {
                datosValidacion.put("beneficioCubierto590",
                        solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey590().toString());
            }
            if (solNovedadDTO.getDatosEmpleador().getPeriodoFinBeneficioLey590() != null) {
                datosValidacion.put("anioInicioBeneficio",
                        solNovedadDTO.getDatosEmpleador().getPeriodoFinBeneficioLey590().toString());
            }
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.SUSTITUCION_PATRONAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.DESAFILIACION)) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosEmpleador().getRequiereInactivacionCuentaWeb().toString());
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB)) {
            datosValidacion.put("codigoSucursal", solNovedadDTO.getDatosEmpleador().getCodigoSucursal());
            datosValidacion.put("nombreSucursal", solNovedadDTO.getDatosEmpleador().getNombreSucursal());
        }
        if (solNovedadDTO.getDatosEmpleador() != null
                && solNovedadDTO.getDatosEmpleador().getIdSucursalEmpresa() != null) {
            datosValidacion.put("idSucursal", solNovedadDTO.getDatosEmpleador().getIdSucursalEmpresa().toString());
        }
        // Se agrega el tipo transaccion como dato para el proceso de validacion
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        logger.info("Fin de método llenarDatosValidacion(DatosEmpleadorNovedadDTO datosEmpleadorDTO)");
        return datosValidacion;
    }

    @Override
    public void resolverNovedades(ResolverNovedadDTO resolverNovedad, UserDTO userDTO) {
        try {
            this.resolverNovedad(resolverNovedad.getSolicitudNovedad(), resolverNovedad.getSolicitudNovedadModelo(), userDTO);
        } catch (Exception e) {
            logger.error("Error en resolverNovedad", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    private void resolverNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad,
                                 UserDTO userDTO) throws Exception {
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.resolverNovedad(solicitudNovedadDTO, solicitudNovedad, userDTO);
    }

    /**
     * @param datosExcepcionNovedadDTO
     * @param excepcion
     * @param userDTO
     * @throws Exception
     */
    // --CLIENTE GuardarExcepcionNovedad--
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void guardarExcepcionNovedad(DatosExcepcionNovedadDTO datosExcepcionNovedadDTO,
                                         String excepcion, UserDTO userDTO) throws Exception {
        GuardarExcepcionNovedadRutine g = new GuardarExcepcionNovedadRutine();
        g.guardarExcepcionNovedad(datosExcepcionNovedadDTO, excepcion, userDTO, entityManager);
    }

    /**
     * Método encargado de parametrizar una notificación de un comunicado.
     *
     * @param idSolicitud id de la solicitud global.
     * @param intento     intento de novedad.
     * @return notificacion parametrizada.
     */
    private void cerrarSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO, boolean intento) {
        logger.info("**__**inicio resolverNovedad");
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.cerrarSolicitudNovedad(solicitudNovedadDTO, intento);
    }

    /**
     * Método encargado de parametrizar una notificación para ser enviada
     * (Cuando el proceso es web)
     *
     * @param intento             define si se trata de un intento de novedad o no.
     * @param solicitudNovedadDTO dto con los datos necesarios para parametrizar
     *                            la notificación.
     * @param userDTO             usuario autenticado.
     */
    private void parametrizarNotificacion(boolean intento, SolicitudNovedadDTO solicitudNovedadDTO, UserDTO userDTO) {
        /*
         * se envía notificaciones desde servicios, en el caso que:
         * - Sea este radicando y el canal sea WEB
         * - Sea un intento fallido y el canal sea WEB
         */
        Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas = llenarEtiquetas();
        List<EtiquetaPlantillaComunicadoEnum> comunicados = new ArrayList<>();
        String proceso = solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name();
        if (intento) {
            addListaComunicados(comunicados, etiquetas, proceso + INTENTO);
        } else {
            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso()
                    .equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB)) {
                addListaComunicados(comunicados, etiquetas,
                        proceso + RADICAR + solicitudNovedadDTO.getNovedadDTO().getPuntoResolucion().name());
            } else {
                if (solicitudNovedadDTO.getCargaMultiple() == null || !solicitudNovedadDTO.getCargaMultiple()) {
                    addListaComunicados(comunicados, etiquetas, proceso + RADICAR);
                }
            }
            if (PuntoResolucionEnum.FRONT.equals(solicitudNovedadDTO.getNovedadDTO().getPuntoResolucion())
                    || solicitudNovedadDTO.getCargaMultiple() != null && solicitudNovedadDTO.getCargaMultiple()) {
                addListaComunicados(comunicados, etiquetas, proceso + CERRAR);
            }
        }
        if (comunicados != null && !comunicados.isEmpty()) {
            List<String> destinatarios = new ArrayList<>();
            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().equals(ProcesoEnum.NOVEDADES_PERSONAS_WEB)
                    || solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso()
                    .equals(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB)) {
                String variable = null;
                if(solicitudNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_DEPWEB) || solicitudNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_WEB)){
                    variable = consultarEmailPersona(solicitudNovedadDTO.getDatosPersonaMultiple().get(0).getTipoIdentificacionTrabajador(),
                            solicitudNovedadDTO.getDatosPersonaMultiple().get(0).getNumeroIdentificacionTrabajador());
                }else{
                    variable = consultarEmailPersona(solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion(),
                            solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion());
                }
                logger.info("variable " +variable);
                destinatarios.add(variable);

            } else if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso()
                    .equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB)) {
                /* si se trata de una novedad de empresas */
                List<Long> idsEmpleador = new ArrayList<>();
                idsEmpleador.add(solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador());
                destinatarios = consultarEmailEmpleadores(idsEmpleador);
            }
            /* se recorren los comunicados */
            for (EtiquetaPlantillaComunicadoEnum etiqueta : comunicados) {
                NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
                notificacion.setEtiquetaPlantillaComunicado(etiqueta);
                notificacion.setProcesoEvento(proceso);
                notificacion.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
                notificacion.setIdEmpleador(solicitudNovedadDTO.getDatosEmpleador() != null
                        ? solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador()
                        : null);
                // notificacion.setDestinatarioTO(destinatarios);
                notificacion.setTipoTx(solicitudNovedadDTO.getNovedadDTO().getNovedad());
                for (String email : destinatarios) {
                    if (email != null) {
                        enviarComunicadoConstruido(notificacion);
                    }
                }

            }
        }
    }

    /**
     * Agrega la lista de etiquetas a la lista de comunicados para que se
     * realice el envio, validando que se haya generado para el key una lista
     *
     * @param comunicados Lista de comunicados a enviar
     * @param etiquetas   Mapa de etiquetas por llave
     * @param key         Llave para la lista de comunicados a agregar
     */
    private void addListaComunicados(List<EtiquetaPlantillaComunicadoEnum> comunicados,
                                     Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas, String key) {
        if (etiquetas.get(key) != null && !etiquetas.get(key).isEmpty()) {
            comunicados.addAll(etiquetas.get(key));
        }
    }

    /**
     * Método encargado de ejecutar una novedad.
     *
     * @param solicitudNovedadDTO dto de la novedad, desde pantalla.
     * @param solicitudNovedad    solicitud de la novedad.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void ejecutarNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("############# entra inicia ejecutarNovedad nov composite #############");
        /* se invoca el servicio para dicha novedad */
        NovedadCore servicioNovedad = NovedadAbstractFactory.getInstance()
                .obtenerServicioNovedad(solicitudNovedadDTO.getNovedadDTO());
        // Se instancia el servicio de la novedad
        ServiceClient servicio = servicioNovedad.transformarServicio(solicitudNovedadDTO);
        servicio.execute();
        System.out.println("############# fin ejecutarNovedad nov composite #############");
    }

    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     *
     * @param solicitudNovedadDTO datos de la novedad.
     * @param solicitudNovedad    solicitud a modificar.
     * @throws JsonProcessingException si ocurre un error convirtiendo.
     */
    private Long enviarNovedadBack(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad,
                                   UserDTO userDTO) throws JsonProcessingException {
        logger.debug(
                "Inicio de método enviarNovedadBack(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        /* se inicia el proceso para el back */
        Long idInstanciaProceso;
//        try {
            idInstanciaProceso = iniciarProcesoNovedad(solicitudNovedad.getIdSolicitud(),
                    solicitudNovedadDTO.getTipoTransaccion().getProceso(), userDTO);
/*        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
*/
        logger.info(
                "Fin de método enviarNovedadBack(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        return idInstanciaProceso;
    }

    /**
     * Método que hace la peticion REST al servicio de iniciar el Proceso de
     * novedad en el BPM
     *
     * @param idSolicitud El identificador de la solicitud global de la novedad.
     * @param usuario     DTO para el servicio de autenticación usuario
     * @param procesoEnum proceso para iniciar.
     * @return <code>Long</code> El identificador de la Instancia Proceso
     *         novedad.
     */
    private Long iniciarProcesoNovedad(Long idSolicitud, ProcesoEnum procesoEnum, UserDTO usuario) {
        logger.info("**__**Inicia iniciarProcesoNovedad( idSolicitudGlobal )");
        Map<String, Object> parametrosProceso = new HashMap<String, Object>();
        SolicitudNovedadModeloDTO solicitudNovedad = consultarSolicitudNovedad(idSolicitud);
        String numeroRadicacion = solicitudNovedad.getNumeroRadicacion();

        // se extraen de caché los parametros de tiempo para los timers del proceso dado
        // el valor de procesoEnum
        // **este ajuste se hace para efectos de las alertas por tiempo en los procesos
        // BPM**
        asignarParametrosTimers(procesoEnum, parametrosProceso);

        parametrosProceso.put("idSolicitud", idSolicitud);
        parametrosProceso.put("numeroRadicado", numeroRadicacion);
        if (!procesoEnum.getWeb()) {
            /* es una novead presencial */
            logger.info("**__**antes de usuarioFront");
            parametrosProceso.put("usuarioFront", usuario.getNombreUsuario());
            logger.info("**__**despues de de usuarioFront");

            /* Cambiar estado de "RADICADA" a "PENDIENTE_ENVIO_AL_BACK" */
            actualizarEstadoSolicitudNovedad(idSolicitud, EstadoSolicitudNovedadEnum.PENDIENTE_ENVIO_AL_BACK);
        } else {

            /* es una novedad web se asigna automa´ticamente al back */
            String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(null, procesoEnum);
            UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            String sedeDestinatario = usuarioDTO.getCodigoSede();
            parametrosProceso.put("usuarioBack", usuarioDTO.getNombreUsuario());
            /* se actualiza el estado, destinatario y sede de la solicitud */
            solicitudNovedad.setDestinatario(destinatario);
            solicitudNovedad.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
            solicitudNovedad.setEstadoSolicitud(EstadoSolicitudNovedadEnum.ASIGNADA_AL_BACK);

        }
        IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesoNovedadService.execute();
        Long idInstanciaProcesoNovedad = iniciarProcesoNovedadService.getResult();

        /* se actualiza el id instancia proceso */
        solicitudNovedad.setIdInstanciaProceso(idInstanciaProcesoNovedad.toString());
        /* se invoca el servicio que actualiza la solicitud de novedad */
        actualizarSolicitudNovedad(solicitudNovedad);
        logger.debug("Finaliza iniciarProcesoNovedad( idSolicitudGlobal )");
        return idInstanciaProcesoNovedad;
    }

    /**
     * consulta del cache los parametros de timer y los asigna al mapa de
     * parametros para iniciar el bpm dado el nombre del proceso.
     *
     * @param procesoEnum       es el nombre del proceso de novedad que se está
     *                          iniciando.
     * @param parametrosProceso es el mapa que contiene los parámetros de inicio
     *                          para el proceso dado.
     */
    private void asignarParametrosTimers(ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso) {
        if (procesoEnum.equals(ProcesoEnum.NOVEDADES_EMPRESAS_PRESENCIAL)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NEP_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NEP_TIEMPO_ASIGNACION_BACK);
            String tiempoSolicitudPendienteDocumentos = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NEP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);

            parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
        } else if (procesoEnum.equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NEW_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NEW_TIEMPO_ASIGNACION_BACK);

            parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        } else if (procesoEnum.equals(ProcesoEnum.NOVEDADES_PERSONAS_PRESENCIAL)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NPP_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NPP_TIEMPO_ASIGNACION_BACK);
            String tiempoSolicitudPendienteDocumentos = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NPP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);

            parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
        } else if (procesoEnum.equals(ProcesoEnum.NOVEDADES_PERSONAS_WEB)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NPW_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NPW_TIEMPO_ASIGNACION_BACK);

            parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        } else if (procesoEnum.equals(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NDW_TIEMPO_PROCESO_SOLICITUD);
            String tiempoAsignacionBack = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_NDW_TIEMPO_ASIGNACION_BACK);

            parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        }
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     *
     * @param nombreUsuarioCaja <code>String</code> El nombre de usuario del
     *                          funcionario de la caja que realiza la consulta
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     *         usuario
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
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     *
     * @param sedeCaja    <code>Long</code> el identificador del afiliado
     * @param procesoEnum <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     *         caja
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
     * Este metodo permite la generacion de un nuevo token para invocacion entre
     * servicios externos
     *
     * @return token <code>String</code> token generado
     */
    /*
     * private String generarTokenAccesoCore() {
     * GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
     * accesoCore.execute();
     * TokenDTO token = (TokenDTO) accesoCore.getResult();
     * return token.getToken();
     * }
     */
    /**
     * Método que hace la peticion REST al servicio de generar nuemro de
     * radicado
     *
     * @param idSolicitud          <code>Long</code> El identificador de la
     *                             solicitud
     * @param sedeCajaCompensacion <code>String</code> El usuario del sistema
     */

    /*
     * private synchronized void generarNumeroRadicado(Long idSolicitud, String
     * sedeCajaCompensacion) {
     * logger.
     * debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
     * RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud,
     * sedeCajaCompensacion);
     * radicarSolicitudService.execute();
     * logger.
     * debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
     * }
     */
    /**
     * Método que hace la peticion REST al servicio que crea una solicitud de
     * novedad.
     *
     * @param novedadDTO novedad a crearse.
     * @param userDTO    usuario que radica la solicitud.
     */
    private SolicitudNovedadModeloDTO crearSolicitudNovedad(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
        logger.info("**__** SolicitudNovedadDTO::  " + novedadDTO);
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.crearSolicitudNovedad(novedadDTO, userDTO, entityManager);
    }

    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una novedad
     */
    private void actualizarEstadoSolicitudNovedad(Long idSolicitud, EstadoSolicitudNovedadEnum estadoSolicitud) {
        logger.info("**__**Inicia actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)idSolicitud: "
                + idSolicitud + " estadoSolicitud: " + estadoSolicitud);
        /*
         * ActualizarEstadoSolicitudNovedad actualizarEstadoSolNovedadService = new
         * ActualizarEstadoSolicitudNovedad(
         * idSolicitud, estadoSolicitud);
         * actualizarEstadoSolNovedadService.execute();
         */

        ActualizarEstadoSolicitudNovedadRutine a = new ActualizarEstadoSolicitudNovedadRutine();
        a.actualizarEstadoSolicitudNovedad(idSolicitud, estadoSolicitud, entityManager);

        logger.info("**__**Finaliza actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
    }

    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una novedad
     */
    private void actualizarSolicitudNovedad(SolicitudNovedadModeloDTO solicitudNovedad) {
        logger.debug("Inicia actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
        try {
            logger.debug("Inicia actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
            SolicitudNovedad solicitudNovedadE = solicitudNovedad.convertToEntity();
            SolicitudNovedadModeloDTO s = consultarSolicitudNovedad(solicitudNovedad.getIdSolicitud());
            if (s.getIdSolicitudNovedad() != null) {
                solicitudNovedadE.setIdSolicitudNovedad(s.getIdSolicitudNovedad());
                entityManager.merge(solicitudNovedadE);
            }
        } catch (Exception e) {
            logger.debug("Ocurrió un error inesperado actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza actualizarSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
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
        try {
            TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
            terminarTarea.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
    }

    /**
     * Método que consulta una novedad
     *
     * @param idSolicitud es el identificador de la novedad
     * @return solicitudNovedadDTO solicitud novedad
     */
    // private SolicitudNovedadModeloDTO consultarSolicitudNovedadTemp(Long
    // idSolicitud) {
    // SolicitudNovedadModeloDTO solicitudNovedadDTO = new
    // SolicitudNovedadModeloDTO();
    // ConsultarSolicitudNovedad consultarSolicitudNovedad = new
    // ConsultarSolicitudNovedad(idSolicitud);
    // consultarSolicitudNovedad.execute();
    // solicitudNovedadDTO = consultarSolicitudNovedad.getResult();
    // return solicitudNovedadDTO;
    // }
    private SolicitudNovedadModeloDTO consultarSolicitudNovedad(Long idSolicitud) {
        logger.info("**__**NOVEDADES_COMPOSITEInicia consultarSolicitudNovedad(Long idSolicitud) NOVEDADES COMPOSITE "
                + idSolicitud);
        // try {
        // SolicitudNovedad sol = (SolicitudNovedad) entityManager
        // .createNamedQuery(NamedQueriesConstants.NOVEDADES_COMPOSITE_CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL)
        // .setParameter("idSolicitud", idSolicitud).getSingleResult();
        // SolicitudNovedadModeloDTO solicitudDTO = new SolicitudNovedadModeloDTO();
        // solicitudDTO.convertToDTO(sol);
        // logger.info("**__**Fin consultarSolicitudNovedad(Long idSolicitud) sss" +
        // idSolicitud);
        // return solicitudDTO;
        try {
            logger.info("**__**NOVEDADES_COMPOSITEinicia try consultarSolicitudNovedad ");
            List<SolicitudNovedad> solicitudesNovedad = new ArrayList<SolicitudNovedad>();
            SolicitudNovedadModeloDTO solicitudDTO = null;
            solicitudesNovedad = entityManager
                    .createQuery(
                            "SELECT sn FROM SolicitudNovedad sn LEFT JOIN FETCH sn.solicitudGlobal WHERE sn.solicitudGlobal.idSolicitud = :idSolicitud")
                    .setParameter("idSolicitud", idSolicitud).getResultList();
            logger.info("**__**NOVEDADES_COMPOSITEfinaliza consulta en query ");
            int cont = 0;
            if (solicitudesNovedad != null) {
                logger.info("**__**NOVEDADES_COMPOSITEfantes del for ");
                for (SolicitudNovedad sol : solicitudesNovedad) {
                    logger.info(
                            "**__**NOVEDADES_COMPOSITEentra al for rutina consultarSolicitudNovedad " + idSolicitud);
                    if (cont == 0) {
                        logger.info("**__**NOVEDADES_COMPOSITEdato del arreglo en posicion 0 listo para convert ");
                        solicitudDTO = new SolicitudNovedadModeloDTO();
                        solicitudDTO.convertToDTO(sol);
                        logger.info("**__**NOVEDADES_COMPOSITEDATO del arreglo en posicion 0 exito en el convert ");
                    }
                }
            }
            // SolicitudNovedadModeloDTO solicitudDTO = new SolicitudNovedadModeloDTO();
            // solicitudDTO.convertToDTO(sol);
            logger.debug("NOVEDADES_COMPOSITEFin rutina consultarSolicitudNovedad(Long idSolicitud) " + idSolicitud);
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudNovedadPorNumeroRadicado(String)"
                    + interpolate("No se encontraron resultados con el nro radicación {0} ingresada.", idSolicitud));
            return null;
        } catch (Exception e) {
            logger.info("**__**error consultarSolicitudNovedad en novedades composite error: " + e);
            return null;
        }
        // el metodo en esa rutina es privado
        // ActualizarEstadoSolicitudNovedadRutine a = new
        // ActualizarEstadoSolicitudNovedadRutine();
        // a.consultarSolicitudNovedad(idSolicitud, entityManager);
    }

    /**
     * Método que consulta una novedad
     *
     * @param idSolicitud es el identificador de la novedad
     * @return Objeto solicitud novedad
     */
    private ParametrizacionNovedadModeloDTO consultarNovedad(TipoTransaccionEnum tipoTransaccion) {
        logger.info("**__**INICIA consultarNovedad novedades composite linea 2979" + tipoTransaccion);
        return NovedadesCompositeUtils.consultarNovedad(tipoTransaccion);
    }

    /**
     * Método que crea un intento de novedad
     *
     * @param solNovedadDTO Solicitud para la creación del intento.
     */
    // --CLIENTE CrearIntentoNovedad //
    private Long crearIntentoNovedad(IntentoNovedadDTO intentoNovedadDTO) {
        CrearIntentoNovedadRutine c = new CrearIntentoNovedadRutine();
        return c.crearIntentoNovedad(intentoNovedadDTO, entityManager);
    }

    // --CLIENTE CrearSolicitudNovedadEmpleador--
    /**
     * @param solicitudNovedadEmpleador
     * @return A
     */
    public SolicitudNovedadEmpleador crearSolicitudNovedadEmpleador(
            SolicitudNovedadEmpleador solicitudNovedadEmpleador) {
        CrearSolicitudNovedadEmpleadorRutine c = new CrearSolicitudNovedadEmpleadorRutine();
        return c.crearSolicitudNovedadEmpleador(solicitudNovedadEmpleador, entityManager);
    }

    /**
     * Metodo encargado de invocar el servicio para el almacenamiento de los
     * datos temporales asociandolos a la persona si corresponde con una
     * activacion de beneficiario
     *
     * @param solicitudNovedadDTO Informacion de la solicitud novedad
     * @throws JsonProcessingException Error presentado en la conversion
     */
    private void guardarDatosTemporal(SolicitudNovedadDTO solicitudNovedadDTO) throws JsonProcessingException {

        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        p.guardarDatosTemporal(solicitudNovedadDTO, entityManager);

    }

    /**
     * Obtiene la lista de transacciones asociadas a la activacion de
     * beneficiarios
     *
     * @return Lista de tipos transaccion
     */
    private List<TipoTransaccionEnum> getListTransaccionActivarBeneficiario() {
        return ProcesarActivacionBeneficiarioPILARutine.getListTransaccionActivarBeneficiario();

    }

    /**
     * Obtiene la lista de actualizacion de certificados asociadas a los
     * beneficiarios
     *
     * @return Lista de tipos transaccion
     */
    private List<TipoTransaccionEnum> getListTransaccionActualizarCertificado() {
        return ProcesarActivacionBeneficiarioPILARutine.getListTransaccionActualizarCertificado();

    }

    /**
     * Método encargado de radicar una solicitud de novedad.
     *
     * @param datoPersonaNovedadDTO dto con los datos de la persona.
     * @param tipoTransaccion       tipo de transaccion (es la novedad).
     * @return solicitudNovedadDTO con los datos de retorno.
     */
    private SolicitudNovedadDTO radicarSolicitudTrabajador(DatosPersonaNovedadDTO datoPersonaNovedadDTO,
                                                           TipoTransaccionEnum tipoTransaccion, Long codigoCargue, UserDTO userDTO) {
        SolicitudNovedadDTO solicitudNovedadDTO = new SolicitudNovedadDTO();
        solicitudNovedadDTO.setDatosPersona(datoPersonaNovedadDTO);
        solicitudNovedadDTO.setTipoTransaccion(tipoTransaccion);
        solicitudNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.WEB);
        solicitudNovedadDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        solicitudNovedadDTO.setCargaMultiple(Boolean.TRUE);
        solicitudNovedadDTO.setIdCargueMultipleNovedad(codigoCargue);
        solicitudNovedadDTO = radicarSolicitudNovedad(solicitudNovedadDTO, userDTO);
        return solicitudNovedadDTO;
    }

    /**
     * Método encargado de convertir TrabajadorCandidatoNovedadDTO a
     * DatosPersonaNovedadDTO
     *
     * @param trabajadorCandidatoNovedadDTO
     * @return retorna el dto de DatosPersonaNovedadDTO
     */
    private DatosPersonaNovedadDTO convertirTrabajadorADatoPersonaNovedadDTO(
            TrabajadorCandidatoNovedadDTO trabajadorCandidatoNovedadDTO) {
        DatosPersonaNovedadDTO datoSolNovedadDTO = new DatosPersonaNovedadDTO();
        // Datos relacionados con la personaDTO
        datoSolNovedadDTO.setTipoIdentificacion(trabajadorCandidatoNovedadDTO.getPersonaDTO().getTipoIdentificacion());
        datoSolNovedadDTO
                .setNumeroIdentificacion(trabajadorCandidatoNovedadDTO.getPersonaDTO().getNumeroIdentificacion());
        datoSolNovedadDTO
                .setTipoIdentificacionTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getTipoIdentificacion());
        datoSolNovedadDTO.setNumeroIdentificacionTrabajador(
                trabajadorCandidatoNovedadDTO.getPersonaDTO().getNumeroIdentificacion());
        datoSolNovedadDTO.setPrimerNombreTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getPrimerNombre());
        datoSolNovedadDTO.setSegundoNombreTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getSegundoNombre());
        datoSolNovedadDTO
                .setPrimerApellidoTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getPrimerApellido());
        datoSolNovedadDTO
                .setSegundoApellidoTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getSegundoApellido());
        datoSolNovedadDTO.setResideEnSectorRural(trabajadorCandidatoNovedadDTO.getPersonaDTO().getResideSectorRural());
        datoSolNovedadDTO
                .setNivelEducativoTrabajador(trabajadorCandidatoNovedadDTO.getPersonaDTO().getNivelEducativo());
        datoSolNovedadDTO.setViveEnCasaPropia(trabajadorCandidatoNovedadDTO.getPersonaDTO().getHabitaCasaPropia());
        // Datos relacionados con la ubicacionDTO
        if (trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO() != null) {
            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(
                    trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getIdMunicipio());
            datoSolNovedadDTO.setMunicipioTrabajador(municipio);
            datoSolNovedadDTO.setDireccionResidenciaTrabajador(
                    trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getDireccionFisica());
            datoSolNovedadDTO.setDescripcionIndicacionResidenciaTrabajador(
                    trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getDescripcionIndicacion());
            datoSolNovedadDTO
                    .setTelefonoFijoTrabajador(
                            trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getTelefonoFijo());
            datoSolNovedadDTO
                    .setTelefonoCelularTrabajador(
                            trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getTelefonoCelular());
            datoSolNovedadDTO.setCorreoElectronicoTrabajador(
                    trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getEmail());
            datoSolNovedadDTO
                    .setCodigoPostalTrabajador(
                            trabajadorCandidatoNovedadDTO.getPersonaDTO().getUbicacionModeloDTO().getCodigoPostal());
        }
        // Datos relacionados con TrabajadorCandidatoNovedadDTO
        datoSolNovedadDTO.setClaseTrabajador(trabajadorCandidatoNovedadDTO.getClaseTrabajador());
        if (trabajadorCandidatoNovedadDTO.getFechaInicioContrato() != null) {
            datoSolNovedadDTO.setFechaInicioLaboresConEmpleador(
                    trabajadorCandidatoNovedadDTO.getFechaInicioContrato().getTime());
        }
        datoSolNovedadDTO.setValorSalarioMensualTrabajador(trabajadorCandidatoNovedadDTO.getValorSalarioMensual());
        datoSolNovedadDTO.setTipoSalarioTrabajador(trabajadorCandidatoNovedadDTO.getTipoSalario());
        datoSolNovedadDTO.setCargoOficioDesempeniadoTrabajador(trabajadorCandidatoNovedadDTO.getCargoOficina());
        datoSolNovedadDTO.setTipoContratoLaboralTrabajador(trabajadorCandidatoNovedadDTO.getTipoContratoEnum());
        return datoSolNovedadDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de obtener tarea activa para
     * posteriomente finalizar el proceso de Afiliación personas presencial
     *
     * @param idInstanciaProceso <code>String</code> El identificador de la
     *                           Instancia Proceso Afiliacion de la Persona
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
     * <p>
     * id del cargue a actualizar datos que seran actualizados
     */
    private void actualizarCargueConsolaEstado(Long idCargue,
                                               ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        ActualizarCargueConsolaEstado actualizacion = new ActualizarCargueConsolaEstado(idCargue,
                consolaEstadoCargueProcesoDTO);
        actualizacion.execute();
    }

    /**
     * Método encargado de consultar la lista de las novedades de retiro de
     * empleadores o personas.
     *
     * @return lista de las novedades de retiro.
     */
    private List<TipoTransaccionEnum> obtenerListaRetiros() {

        List<TipoTransaccionEnum> tiposTransaccion = new ArrayList<>();
        tiposTransaccion.add(TipoTransaccionEnum.DESAFILIACION);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR);
        tiposTransaccion.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE);
        return tiposTransaccion;
    }

    /**
     * Método encargado guardar los documentos a causa de una novedad de retiro.
     *
     * @param numeroRadicado número de radicado.
     */
    private void guardarDocumentosAdminSolicitudes(String numeroRadicado,
                                                   DocumentoAdministracionEstadoSolicitudDTO documento) {
        logger.debug(
                "Inicio de método guardar guardarDocumentosAdminSolicitudes(String numeroRadicado,DocumentoAdministracionEstadoSolicitudDTO documento)");
        List<DocumentoAdministracionEstadoSolicitudDTO> documentos = new ArrayList<>();
        documentos.add(documento);
        GuardarDocumentosAdminSolicitudes guardarDocumentosService = new GuardarDocumentosAdminSolicitudes(
                numeroRadicado, documentos);
        guardarDocumentosService.execute();
        logger.debug(
                "Fin de método guardar guardarDocumentosAdminSolicitudes(String numeroRadicado,DocumentoAdministracionEstadoSolicitudDTO documento)");
    }

    /**
     * Método encargado de guardar la lista de chequeo.
     *
     * @param listaChequeo a guardar.
     */
    private void guardarListaChequeo(ListaChequeoDTO listaChequeo) {
        logger.debug("Inicio de método guardarListaChequeo(List<ListaChequeoDTO> listaChequeo)");
        // GuardarListaChequeo guardarListaChequeoService = new
        // GuardarListaChequeo(listaChequeo);
        // guardarListaChequeoService.execute();
        GuardarListaChequeoRutine g = new GuardarListaChequeoRutine();
        g.guardarListaChequeo(listaChequeo, entityManager);

        logger.debug("Fin de método guardarListaChequeo(List<ListaChequeoDTO> listaChequeo)");
    }

    /**
     * Realiza el llamado al cliente que verifica la estructura del archivo y
     * gestiona el procesamiento del archivo y el registro en la consola de
     * cargue
     *
     * @param archivoSuperVivenciaDTO Información del archivo a procesar
     * @param userDTO                 Usuario que realiza la acción
     * @return Información del resultado de procesamiento del archivo
     * @throws Exception Lanzada si ocurre un error gestionado el archivo
     */
    private ResultadoValidacionArchivoDTO verificarArchivoSupervivencia(ArchivoSupervivenciaDTO archivoSuperVivenciaDTO,
                                                                        UserDTO userDTO) throws Exception {
        logger.info("Inicia verificarArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
        VerificarArchivoSupervivencia verificacion = new VerificarArchivoSupervivencia(archivoSuperVivenciaDTO);
        verificacion.execute();
        ResultadoValidacionArchivoDTO resultadoValidacionArchivoDTO = verificacion.getResult();

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueSupervivenciaEnum estadoSupervivencia;
        // Se gestiona el archivo

        if (!EstadoCargaMultipleEnum.CANCELADO.equals(resultadoValidacionArchivoDTO.getEstadoCargue())) {
            gestionarArchivoSupervivencia(resultadoValidacionArchivoDTO.getLstResultadoSupervivenciaDTO(), userDTO);
            estadoSupervivencia = EstadoCargueSupervivenciaEnum.PROCESADO;
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
            logger.info("**__**estadoSupervivencia: " + EstadoCargueSupervivenciaEnum.PROCESADO);
            logger.info("**__**estadoProcesoMasivo: " + EstadoCargueMasivoEnum.FINALIZADO);
        } else {
            logger.info(
                    "**__**estadoProcesoMasivo2: " + EstadoCargueSupervivenciaEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA);
            estadoSupervivencia = EstadoCargueSupervivenciaEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
        }

        Long idCargue = resultadoValidacionArchivoDTO.getIdCargue();
        // Se actualiza el estado del archivo de supervivencia
        ArchivoSupervivenciaDTO archivoActualiza = new ArchivoSupervivenciaDTO();
        archivoActualiza.setEstadoCargue(estadoSupervivencia);
        archivoActualiza.setIdentificadorCargue(idCargue);
        modificarCrearCargueSupervivencia(archivoActualiza);
        logger.info("**__**idCargue: " + idCargue);
        // Se actualiza la consola de cargue
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargueProcesoDTO.setCargue_id(idCargue);
        consolaEstadoCargueProcesoDTO.setEstado(estadoProcesoMasivo);
        consolaEstadoCargueProcesoDTO.setFechaFin(new Date().getTime());
        consolaEstadoCargueProcesoDTO.setFileLoaded_id(resultadoValidacionArchivoDTO.getFileLoadedId());
        consolaEstadoCargueProcesoDTO.setGradoAvance(EstadoCargaMultipleEnum.CERRADO.getGradoAvance());
        consolaEstadoCargueProcesoDTO
                .setLstErroresArhivo(resultadoValidacionArchivoDTO.getResultadoHallazgosValidacionArchivoDTO());
        consolaEstadoCargueProcesoDTO.setNumRegistroConErrores(resultadoValidacionArchivoDTO.getRegistrosConErrores());
        consolaEstadoCargueProcesoDTO.setNumRegistroObjetivo(resultadoValidacionArchivoDTO.getTotalRegistro());
        consolaEstadoCargueProcesoDTO.setNumRegistroProcesado(resultadoValidacionArchivoDTO.getTotalRegistro());
        consolaEstadoCargueProcesoDTO.setNumRegistroValidados(resultadoValidacionArchivoDTO.getRegistrosValidos());
        consolaEstadoCargueProcesoDTO.setProceso(TipoProcesoMasivoEnum.CARGUE_SUPERVIVENCIA);
        logger.info("**__**setProceso: " + TipoProcesoMasivoEnum.CARGUE_SUPERVIVENCIA);
        logger.info("**__**setNumRegistroObjetivo: " + resultadoValidacionArchivoDTO.getTotalRegistro());
        actualizarCargueConsolaEstado(idCargue, consolaEstadoCargueProcesoDTO);
        logger.debug("Finaliza verificarArchivoSupervivencia(ArchivoSupervivenciaDTO, UserDTO)");
        return resultadoValidacionArchivoDTO;
    }

    /**
     * Metodo que se encarga de llamar el cliente que registra el cargue
     * multiple
     *
     * @param idEmpleador
     * @param cargueMultipleDTO
     * @return id con el registro.
     */
    private Long modificarCrearCargueSupervivencia(ArchivoSupervivenciaDTO archivoSupervivenciaDTO) {
        ActualizarCargueSupervivencia cargue = new ActualizarCargueSupervivencia(archivoSupervivenciaDTO);
        cargue.execute();
        Long respuesta = cargue.getResult();
        return respuesta;
    }

    /**
     * Me´todo que se encarga de llamar al cliente que consulta los email de los
     * empleadores.
     *
     * @param idEmpleadoresPersona
     * @return lista de los email de los empleadores.
     */
    private List<String> consultarEmailEmpleadores(List<Long> idEmpleadoresPersona) {
        ConsultarEmailEmpleadores consultarEmailEmpleadores = new ConsultarEmailEmpleadores(idEmpleadoresPersona);
        consultarEmailEmpleadores.execute();
        List<String> emailEmpleadores = consultarEmailEmpleadores.getResult();
        return emailEmpleadores;
    }

    /**
     * Me´todo que se encarga de llamar al cliente que consulta los email de los
     * empleadores.
     *
     * @param idEmpleadoresPersona
     * @return lista de los email de los empleadores.
     */
    private String consultarEmailPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.info("consultarEmailPersona: tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: "
                + numeroIdentificacion);
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(numeroIdentificacion,
                tipoIdentificacion);
        consultarDatosPersona.execute();
        PersonaModeloDTO persona = consultarDatosPersona.getResult();
        logger.info("persona: " + persona.getUbicacionModeloDTO());
        logger.info("getIdPersona: " + persona.getIdPersona());
        String email = null;
        if (persona.getUbicacionModeloDTO() != null) {
            email = persona.getUbicacionModeloDTO().getEmail();
        }
        logger.info("email: " + email);
        return email;
    }

    /**
     * Método que se encarga de llamar el cliente de validacion de novedades
     * múltiples
     * <p>
     * proceso al que pertenece la validacion objeto de validacion
     *
     * @param bloque,                    bloque al que pertenecene el listado de
     *                                   trabajadores
     * @param lstTrabajadorCandidatoDTO, listado de las trabajadores de la
     *                                   novedad a validar
     * @return retorna el DTO
     */
    private ListaDatoValidacionDTO validarPersonasNovedadesCargaMultiple(String bloque, ProcesoEnum proceso,
                                                                     String objetoValidacion, List<TrabajadorCandidatoNovedadDTO> lstTrabajadorCandidatoDTO) {
        logger.debug(
                "Inicia validarPersonasNovedadesCargaMultiple(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
        ValidarCargaMultipleNovedades validarPersona = new ValidarCargaMultipleNovedades(bloque,
                proceso, objetoValidacion, lstTrabajadorCandidatoDTO);
            validarPersona.execute();
        ListaDatoValidacionDTO solicitudAfiliacionPerona = validarPersona.getResult();
        logger.debug(
                "Finaliza validarPersonasNovedadesCargaMultiple(String bloque,ProcesoEnum proceso,String objetoValidacion, List<ListaDatoValidacionDTO> listaDatosValidacion");
        return solicitudAfiliacionPerona;
    }

    /**
     * Método encargado de registrar las inconsistencias del reporte entregado
     * por la registraduría.
     *
     * @param inconsistenciasDTO listado de las inconsistencias.
     */
    private void registrarInconsistenciasPersonas(List<RegistroPersonaInconsistenteDTO> inconsistenciasDTO) {
        logger.debug(
                "Inicio de método registrarInconsistenciasPersonas(List<RegistroPersonaInconsistenteDTO> listaDto)");
        GuardarRegistroPersonaInconsistencia guardarInconsistencias = new GuardarRegistroPersonaInconsistencia(
                inconsistenciasDTO);
        guardarInconsistencias.execute();
        logger.debug("Fin de método registrarInconsistenciasPersonas(List<RegistroPersonaInconsistenteDTO> listaDto)");
    }

    /**
     * Método encargado de invocar el servicio que consutla un empleador por
     * tipo y número de identificación.
     *
     * @param tipoIdentificacion   tipo de identficación.
     * @param numeroIdentificacion número de identificación.
     * @return empleador encontrado.
     */
    private EmpleadorModeloDTO consultarEmpleador(TipoIdentificacionEnum tipoIdentificacion,
                                                  String numeroIdentificacion) {
        logger.debug("Inicio de método consultarEmpleador");
        ConsultarEmpleadorTipoNumero consultarEmpleadorService = new ConsultarEmpleadorTipoNumero(numeroIdentificacion,
                tipoIdentificacion);
        consultarEmpleadorService.execute();
        logger.debug("Fin de método consultarEmpleador");
        return consultarEmpleadorService.getResult();
    }

    /**
     * Método que invoca el servicio de consultar personas.
     *
     * @param numeroIdentificacion número de identificación de la persona.
     * @param tipoIdentificacion   tipo de identificación de la persona.
     * @return
     */
    private PersonaModeloDTO consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        logger.debug("Inicia consultarPersona(" + numeroIdentificacion + ", " + tipoIdentificacion + ")");
        ConsultarDatosPersona consultarPersona = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarPersona.execute();
        PersonaModeloDTO personas = consultarPersona.getResult();
        logger.debug("Finaliza consultarPersona(" + numeroIdentificacion + ", " + tipoIdentificacion + ")");
        return personas;
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
        String[] parts = archivoId.split("_");
        archivoId = parts[0];
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }

    /**
     * Metodo que se encarga de llamar el cliente que registra el cargue
     * multiple
     *
     * @param idEmpleador       id del empleador.
     * @param cargueMultipleDTO DTO que contiene la carga multiple.
     * @return id del cargue.
     */
    private Long registrarCargue(Long idEmpleador, CargueMultipleDTO cargueMultipleDTO) {
        logger.debug("Inicia registrarCargueMultiple(Long, CargueAfiliacionMultipleDTO)");
        RegistrarCargue registrar = new RegistrarCargue(idEmpleador, cargueMultipleDTO);
        registrar.execute();
        Long result = registrar.getResult();
        logger.debug("Finaliza registrarCargueMultiple(Long, CargueAfiliacionMultipleDTO)");
        return result;
    }

    /**
     * Metodo encargado de llamar al cliente de actualizarEstadoCargueMultiple
     * <p>
     * id del cargue a realizar la actualizacion Nuevo estado con el que quedara
     * el cargue
     */
    private void modificarEstadoCargueMultiple(Long idCargue, EstadoCargaMultipleEnum estadoCargueMultiple) {
        logger.debug("Inicia actualizarEstadoCargueMultiple (Long,EstadoCargaMultiplePersonaEnum)");
        Boolean empleadorCargue = false;
        ModificarEstadoCargueMultiple actualizarEstado = new ModificarEstadoCargueMultiple(idCargue, empleadorCargue,
                estadoCargueMultiple);
        actualizarEstado.execute();
        logger.debug("Finaliza actualizarEstadoCargueMultiple (Long,EstadoCargaMultiplePersonaEnum)");
    }

    /**
     * Método encargado de invocar el servicio que se encarga de verificar la
     * estructura de un archivo.
     *
     * @param idEmpleador     id del empleador.
     * @param archivoMultiple dto con la información del archivo.
     * @return resultado de la verificación de la estructura.
     */
    private ResultadoValidacionArchivoDTO verificarEstructuraArchivo(Long idEmpleador, Long idCargueMultiple,
                                                                     Long idSucursalEmpleador, InformacionArchivoDTO archivoMultiple) {
        logger.debug(
                "Inicia verificarEstructuraArchivo(validarEstructuraContenidoArchivo(Long, Long, InformacionGeneralArchivoDTO)");
        VerificarEstructuraArchivo verificarArchivo = new VerificarEstructuraArchivo(idEmpleador, idCargueMultiple,
                idSucursalEmpleador, archivoMultiple);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        logger.debug(
                "Finaliza verificarEstructuraArchivo(validarEstructuraContenidoArchivo(Long, Long, InformacionGeneralArchivoDTO)");
        return resultDTO;
    }

    /**
     * Método que se encarga de invocar el servicio de escalar una solicitud.
     *
     * @param idSolicitudGlobal id de la solicitud global.
     * @param escalamientoDTO   dto con los datos del escalamiento.
     * @param userDTO           usuario DTO.
     */
    private void escalarSolicitud(Long idSolicitudGlobal, EscalamientoSolicitudDTO escalamientoDTO, UserDTO userDTO) {
        logger.debug(
                "Inicio de método escalarSolicitud(Long idSolicitudGlobal, EscalamientoSolicitudDTO escalamientoDTO,UserDTO userDTO)");
        escalamientoDTO.setFechaCreacion(new Date());
        escalamientoDTO.setUsuarioCreacion(userDTO.getNombreUsuario());
        EscalarSolicitud escalarSolicitud = new EscalarSolicitud(
                idSolicitudGlobal, escalamientoDTO);
        escalarSolicitud.execute();
        logger.debug(
                "Fin de método escalarSolicitud(Long idSolicitudGlobal, EscalamientoSolicitudDTO escalamientoDTO) ");
    }

    /**
     * Método que retorna las etiquetas por proceso y flujo
     *
     * @return map con las etiquetas de los comunicados.
     */
    private Map<String, List<EtiquetaPlantillaComunicadoEnum>> llenarEtiquetas() {
        Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas = new HashMap<String, List<EtiquetaPlantillaComunicadoEnum>>();
        /* Comunicados dependientes web */
        List<EtiquetaPlantillaComunicadoEnum> dependienteRadicar = new ArrayList<>();
        dependienteRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_WEB_TRB_EMP);
        // TODO falta la etiqueta 62
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + RADICAR, dependienteRadicar);
        List<EtiquetaPlantillaComunicadoEnum> dependienteIntento = new ArrayList<>();
        // TODO se dejo invocando la 61 pero se debe cambiar por la 62 cuando
        // exista
        dependienteIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_WEB_TRB_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + INTENTO, dependienteIntento);

        List<EtiquetaPlantillaComunicadoEnum> dependienteCerrar = new ArrayList<>();
        // Se envia al cerrar la solicitud dep web por cargue multiple
        // Etiqueta 59.
        dependienteCerrar.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_WEB_TRB_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + CERRAR, dependienteCerrar);

        /* Comunicados personas web */
        List<EtiquetaPlantillaComunicadoEnum> personasRadicar = new ArrayList<>();
        personasRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_PER);
        personasRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS);
        etiquetas.put(ProcesoEnum.NOVEDADES_PERSONAS_WEB.name() + RADICAR, personasRadicar);
        List<EtiquetaPlantillaComunicadoEnum> personasIntento = new ArrayList<>();
        personasIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS);
        etiquetas.put(ProcesoEnum.NOVEDADES_PERSONAS_WEB.name() + INTENTO, personasIntento);

        /* Comunicados empleadores web */
        List<EtiquetaPlantillaComunicadoEnum> empleadoresRadicarFront = new ArrayList<>();
        empleadoresRadicarFront.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_EMP);
        empleadoresRadicarFront.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + RADICAR + PuntoResolucionEnum.FRONT.name(),
                empleadoresRadicarFront);
        List<EtiquetaPlantillaComunicadoEnum> empleadoresRadicaBack = new ArrayList<>();
        empleadoresRadicaBack.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + RADICAR + PuntoResolucionEnum.BACK.name(),
                empleadoresRadicaBack);
        List<EtiquetaPlantillaComunicadoEnum> empleadoresIntento = new ArrayList<>();
        empleadoresIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + INTENTO, empleadoresIntento);

        return etiquetas;
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
     * @param notificacion, notificación dto que contiene la información del
     *                      correo
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        logger.info("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO) !!!!");
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
        logger.info("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
    }

    /**
     * Método encargado de crear un registro de las novedades futuras de los
     * aportes.
     */
    // --CLIENTE CrearNovedadFutura--
    private void crearNovedadFutura(RegistroNovedadFuturaModeloDTO novedadFuturaDTO) {
        try {
            logger.debug("Inicio de método crearNovedadFutura()");
            RegistroNovedadFutura novedadFutura = novedadFuturaDTO.convertToEntity();
            if (novedadFutura.getId() != null) {
                entityManager.merge(novedadFutura);
            } else {
                entityManager.persist(novedadFutura);
            }
            logger.debug("Fin de método crearNovedadFutura()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * validarArchivoRespuesta(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoDTO validarArchivoRespuesta(TipoArchivoRespuestaEnum tipoArchivo,
                                                                 CargueArchivoActualizacionDTO cargue,
                                                                 UserDTO userDTO) {
        logger.info("GLPI45051-->Inicio validarArchivoRespuesta(" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_ACTUALIZA_INFO);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoRespuesta verificarArchivo = new VerificarEstructuraArchivoRespuesta(tipoArchivo,
                archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;
        if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                || (resultDTO.getListActualizacionInfoNovedad() == null
                || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        } else {
            // Se identifica las diferencias entre los registros validos y la base de datos
            List<InformacionActualizacionNovedadDTO> list = identificarNovedadArchivoRespuesta(tipoArchivo,
                    resultDTO.getListActualizacionInfoNovedad(), userDTO);
            resultDTO.setListActualizacionInfoNovedad(list);
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
        }

        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);

        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_ACTUALIZA_INFO);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

        logger.debug("Fin validarArchivoRespuesta(" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
        return resultDTO;
    }

    /**
     * Verifica la informacion registrada en el archivo contra la BD
     *
     * @param tipoArchivo            Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO                Usuario que realiza el proceso
     */
    private List<InformacionActualizacionNovedadDTO> identificarNovedadArchivoRespuesta(
            TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, UserDTO userDTO) {
        logger.info("GLPI45051-->Inicia identificarNovedadArchivoRespuesta(" + tipoArchivo
                + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        // Tareas paralelas
        List<Callable<InformacionActualizacionNovedadDTO>> tareasParalelas = new LinkedList<>();
        if (tipoArchivo.equals(TipoArchivoRespuestaEnum.EMPLEADOR)) {
            for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
                Callable<InformacionActualizacionNovedadDTO> parallelTask = () -> {
                                        return validarArchivoRespuestaEmpleador(informacionActualizacionNovedadDTO);
                };
                tareasParalelas.add(parallelTask);
            }
            // validarArchivoRespuestaEmpleador(listInformacionArchivo);
        } else if (tipoArchivo.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {
            for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println("GLPI-45051-->getAfiliado().getTipoIdentificacion: "+informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
                System.out.println("GLPI-45051-->getAfiliado().getNumeroIdentificacion: "+informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
                System.out.println("-----------------------------------------------------------------------------------");
                Callable<InformacionActualizacionNovedadDTO> parallelTask = () -> {
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println("45051-paralela-->getAfiliado().getTipoIdentificacion: "+informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
                    System.out.println("45051-paralela-->getAfiliado().getNumeroIdentificacion: "+informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
                    System.out.println("-----------------------------------------------------------------------------------");
                    return validarArchivoRespuestaAfiliado(informacionActualizacionNovedadDTO);
                };
                tareasParalelas.add(parallelTask);
            }
            // validarArchivoRespuestaAfiliado(listInformacionArchivo);
        } else if (tipoArchivo.equals(TipoArchivoRespuestaEnum.BENEFICIARIO)) {
            for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
                Callable<InformacionActualizacionNovedadDTO> parallelTask = () -> {
                    return validarArchivoRespuestaBeneficiario(informacionActualizacionNovedadDTO);
                };
                tareasParalelas.add(parallelTask);
            }
            // validarArchivoRespuestaBeneficiario(listInformacionArchivo);
        }
        listInformacionArchivo = new ArrayList<>();
        try {
            List<Future<InformacionActualizacionNovedadDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<InformacionActualizacionNovedadDTO> future : listInfoArchivoFuture) {
                listInformacionArchivo.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona identificarNovedadArchivoRespuesta(" + tipoArchivo
                    + ", List<InformacionActualizacionNovedadDTO>, UserDTO)", e);
            throw new TechnicalException(e);
        }
        logger.debug("Finaliza identificarNovedadArchivoRespuesta(" + tipoArchivo
                + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        return listInformacionArchivo;
    }

    /**
     * Valida y procesa el archivo de respuesta para la actualización de un empleador.
     *
     * @param informacionActualizacionNovedadDTO Información de la actualización de la novedad.
     * @return Información actualizada de la novedad después de validar el archivo de respuesta.
     */
    private InformacionActualizacionNovedadDTO validarArchivoRespuestaEmpleador(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO) {
        // Inicializar variables
        List<String> camposDiferentes = new ArrayList<>();
        Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico = new HashMap<>();
        Map<TipoTransaccionEnum, List<String>> novedadesConEnvioAlBack = new HashMap<>();
        EmpleadorModeloDTO empleadorCargue = informacionActualizacionNovedadDTO.getEmpleador();

        // Obtener el empleador existente
        EmpleadorModeloDTO empleadorModeloDTOToUpdate = obtenerEmpleador(empleadorCargue.getTipoIdentificacion(), empleadorCargue.getNumeroIdentificacion());

        if (empleadorModeloDTOToUpdate != null && empleadorModeloDTOToUpdate.getIdEmpleador() != null) {
            // Realizar validaciones sobre los campos del empleador
            validarCamposDiferentesEmpleador(informacionActualizacionNovedadDTO, empleadorCargue, empleadorModeloDTOToUpdate, camposDiferentes, novedadesConCierreAutomatico, novedadesConEnvioAlBack);
        } else {
            // Crear un nuevo empleador si no existe en la base de datos
            CrearEmpleador crearEmpleador = new CrearEmpleador(empleadorCargue.convertToEntity());
            crearEmpleador.execute();
        }

        // Establecer los resultados de la validación en la información de la novedad
        informacionActualizacionNovedadDTO.setCamposDiferentes(camposDiferentes);
        informacionActualizacionNovedadDTO.setTiposNovedad(novedadesConEnvioAlBack);
        informacionActualizacionNovedadDTO.setTiposNovedadConCierreAutomatico(novedadesConCierreAutomatico);
        informacionActualizacionNovedadDTO.setTipoArchivoRespuesta(TipoArchivoRespuestaEnum.EMPLEADOR);

        return informacionActualizacionNovedadDTO;
    }

    /**
     * Valida y procesa los campos diferentes de un empleador entre la información cargada y la existente en la base de datos.
     *
     * @param informacionActualizacionNovedadDTO Información de la actualización de la novedad.
     * @param empleadorCargue                  Empleador cargado con información actualizada.
     * @param empleadorModeloDTOToUpdate       Empleador existente en la base de datos para actualizar.
     * @param camposDiferentes                 Lista de campos que presentan diferencias.
     * @param novedadesConCierreAutomatico     Mapa de tipos de transacción con campos para cierre automático.
     * @param novedadesConEnvioAlBack          Mapa de tipos de transacción con campos para enviar al backend.
     */
    private void validarCamposDiferentesEmpleador(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
                                                  EmpleadorModeloDTO empleadorCargue,
                                                  EmpleadorModeloDTO empleadorModeloDTOToUpdate,
                                                  List<String> camposDiferentes,
                                                  Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico,
                                                  Map<TipoTransaccionEnum, List<String>> novedadesConEnvioAlBack){
        ConsultarUltimaClasificacion consultarUltimaClasificacion = new ConsultarUltimaClasificacion(
            empleadorModeloDTOToUpdate.getIdEmpleador());
        consultarUltimaClasificacion.execute();
        ClasificacionEnum clasificacion = consultarUltimaClasificacion.getResult();
        informacionActualizacionNovedadDTO.setClasificacion(clasificacion);
        if (clasificacion != null) {
            informacionActualizacionNovedadDTO.setTipoSolicitante(clasificacion.getSujetoTramite().getDescripcion());
        }
        empleadorCargue.setEstadoEmpleador(empleadorModeloDTOToUpdate.getEstadoEmpleador());

        //# 4
        System.out.println("GLPI45051 empleadorModeloDTOToUpdate.getRazonSocial(): "+empleadorModeloDTOToUpdate.getRazonSocial()
                +" empleadorCargue.getRazonSocial(): "+empleadorCargue.getRazonSocial());

        verificarDiferencia(empleadorModeloDTOToUpdate.getRazonSocial(), empleadorCargue.getRazonSocial(), RAZON_SOCIAL, TipoTransaccionEnum.CAMBIO_RAZON_SOCIAL_NOMBRE, camposDiferentes, novedadesConCierreAutomatico);

        //# 5
        verificarDiferencia(empleadorModeloDTOToUpdate.getNaturalezaJuridica(), empleadorCargue.getNaturalezaJuridica(), NATURALEZA_JURIDICA, TipoTransaccionEnum.CAMBIO_NATURALEZA_JURIDICA, camposDiferentes, novedadesConEnvioAlBack);

        //# 6, 7, 8, 9
        validarRepresentanteLegal(informacionActualizacionNovedadDTO, empleadorCargue, empleadorModeloDTOToUpdate, camposDiferentes, novedadesConCierreAutomatico);

        //# 10, 11, 12, 18, 19, 20, 21, 22, 23, 24
        validarUbicaciones(empleadorCargue, empleadorModeloDTOToUpdate, camposDiferentes, novedadesConCierreAutomatico);

        //# 15
        verificarDiferencia(getCodigoCIIU(empleadorModeloDTOToUpdate), getCodigoCIIU(empleadorCargue), ACTIVIDAD_ECONOMICA, TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL, camposDiferentes, novedadesConCierreAutomatico);

        //#25
        if (empleadorModeloDTOToUpdate.getMedioDePagoSubsidioMonetario() != null && empleadorCargue.getMedioDePagoSubsidioMonetario() != null) {
            verificarDiferencia(empleadorModeloDTOToUpdate.getMedioDePagoSubsidioMonetario().name(), empleadorCargue.getMedioDePagoSubsidioMonetario().name(), MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO, TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL, camposDiferentes, novedadesConCierreAutomatico);
        } else if(empleadorModeloDTOToUpdate.getMedioDePagoSubsidioMonetario() == null && empleadorCargue.getMedioDePagoSubsidioMonetario() != null){
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL, MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO, camposDiferentes);
        }

    }

    /**
     * Valida y procesa las ubicaciones de un empleador entre la información cargada y la existente en la base de datos.
     *
     * @param empleadorCargue                Empleador cargado con información actualizada.
     * @param empleadorModeloDTOToUpdate     Empleador existente en la base de datos para actualizar.
     * @param camposDiferentes               Lista de campos que presentan diferencias.
     * @param novedadesConCierreAutomatico   Mapa de tipos de transacción con campos para cierre automático.
     */
    private void validarUbicaciones(EmpleadorModeloDTO empleadorCargue,
                                    EmpleadorModeloDTO empleadorModeloDTOToUpdate,
                                    List<String> camposDiferentes,
                                    Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico) {
        // Valida ubicacion
        List<UbicacionEmpresa> listaUbicacionesEmpresa = new ArrayList<>();

        //Obtengo las direcciones UbicacionEmpresa asociadas con la empresa, y las convierto en UbicacionModeloDTO para modificarlas
        ConsultarUbicacionesEmpresa consultarUbicacionesEmpresa = new ConsultarUbicacionesEmpresa(empleadorModeloDTOToUpdate.getIdEmpresa());
        consultarUbicacionesEmpresa.execute();
        listaUbicacionesEmpresa = consultarUbicacionesEmpresa.getResult();

        // Si no hay ubicaciones asociadas, se registran todos los campos de ubicación como diferentes y se sale del método
        if (listaUbicacionesEmpresa.isEmpty()) {
            // Si no hay ubicaciones, se añaden todas las ubicaciones como campos diferentes y se sale del método
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, TELEFONO_1_OFICINA_PRINCIPAL, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, EMAIL_1_OFICINA_PRINCIPAL, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, CELULAR_OFICINA_PRINCIPAL, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, DEPARTAMENTO, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, MUNICIPIO, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL, DIRECCION, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL, TELEFONO_2_ENVIO_DE_CORRESPONDENCIA, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL, EMAIL_2_ENVIO_DE_CORRESPONDENCIA, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL, TELEFONO_3_NOTIFICACION_JUDICIAL, camposDiferentes);
            addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL, EMAIL_3_NOTIFICACION_JUDICIAL, camposDiferentes);
            return;
        }

        // Asignar el ID de la empresa para realizar consultas relacionadas con las ubicaciones
        empleadorCargue.setIdEmpresa(empleadorModeloDTOToUpdate.getIdEmpresa());
        TipoTransaccionEnum tipoTransaccion = null;

        for (UbicacionEmpresa ubicacionEmpTemp : listaUbicacionesEmpresa) {
            if (ubicacionEmpTemp == null || ubicacionEmpTemp.getUbicacion() == null) {
                continue;
            }

            // Convertir la ubicación en un DTO para su modificación
            UbicacionModeloDTO ubicacionModeloDTOTemp = new UbicacionModeloDTO();
            ubicacionModeloDTOTemp.convertToDTO(ubicacionEmpTemp.getUbicacion());

            if (TipoUbicacionEnum.UBICACION_PRINCIPAL.equals(ubicacionEmpTemp.getTipoUbicacion())) {
                tipoTransaccion = TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL;
                empleadorCargue.setIdUbicacionPrincipal(ubicacionEmpTemp.getUbicacion().getIdUbicacion());

                // Verificar diferencias en la ubicación principal
                //# 10
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getMunicipio().getIdDepartamento(), empleadorCargue.getUbicacionModeloDTO().getIdDepartamento(), DEPARTAMENTO, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 11
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getMunicipio().getIdMunicipio(), empleadorCargue.getUbicacionModeloDTO().getIdMunicipio(), MUNICIPIO, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 12
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getDireccionFisica(), empleadorCargue.getUbicacionModeloDTO().getDireccionFisica(), DIRECCION, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 18
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getEmail(), empleadorCargue.getEmail1OficinaPrincipal(), EMAIL_1_OFICINA_PRINCIPAL, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 21
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getTelefonoFijo(), empleadorCargue.getTelefono1OficinaPrincipal(), TELEFONO_1_OFICINA_PRINCIPAL, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 24
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getTelefonoCelular(), empleadorCargue.getCelularOficinaPrincipal(), CELULAR_OFICINA_PRINCIPAL, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

            } else if (TipoUbicacionEnum.ENVIO_CORRESPONDENCIA.equals(ubicacionEmpTemp.getTipoUbicacion())) {
                tipoTransaccion = TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL;
                //cargamos el id de la ubicación para actualizarla porque si no la cargamos actualiza la ubicación de la persona responsable
                empleadorCargue.setIdUbicacionEnvioDeCorrespondencia(ubicacionEmpTemp.getUbicacion().getIdUbicacion());

                // Verificar diferencias en la ubicación de envío de correspondencia
                //# 19
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getEmail(), empleadorCargue.getEmail2EnvioDeCorrespondencia(), EMAIL_2_ENVIO_DE_CORRESPONDENCIA, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
                //# 22
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getTelefonoFijo(), empleadorCargue.getTelefono2EnvioDeCorrespondencia(), TELEFONO_2_ENVIO_DE_CORRESPONDENCIA, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

            } else if (TipoUbicacionEnum.NOTIFICACION_JUDICIAL.equals(ubicacionEmpTemp.getTipoUbicacion())) {
                tipoTransaccion = TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL;
                empleadorCargue.setIdUbicacionNotificacionJudicial(ubicacionEmpTemp.getUbicacion().getIdUbicacion());

                // Verificar diferencias en la ubicación de notificación judicial
                //# 20
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getEmail(), empleadorCargue.getEmail3NotificacionJudicial(), EMAIL_3_NOTIFICACION_JUDICIAL, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

                //# 23
                verificarDiferencia(ubicacionEmpTemp.getUbicacion().getTelefonoFijo(), empleadorCargue.getTelefono3NotificacionJudicial(), TELEFONO_3_NOTIFICACION_JUDICIAL, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

            }
        }
    }

    /**
     * Valida y procesa la información del representante legal de un empleador comparando con la información existente.
     *
     * @param informacionActualizacionNovedadDTO  Información de actualización y novedades.
     * @param empleadorCargue                     Empleador cargado con información actualizada.
     * @param empleadorModeloDTOToUpdate          Empleador existente en la base de datos para actualizar.
     * @param camposDiferentes                    Lista de campos que presentan diferencias.
     * @param novedadesConCierreAutomatico        Mapa de tipos de transacción con campos para cierre automático.
     */
    private void validarRepresentanteLegal(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
                                           EmpleadorModeloDTO empleadorCargue,
                                           EmpleadorModeloDTO empleadorModeloDTOToUpdate,
                                           List<String> camposDiferentes,
                                           Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico) {

        PersonaModeloDTO repreLegalCargue = informacionActualizacionNovedadDTO.getRepresentanteLegal();
        TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL;

        ConsultarRepresentantesLegalesEmpleador consultarRepresentantesLegalesEmpleador = new ConsultarRepresentantesLegalesEmpleador(empleadorModeloDTOToUpdate.getIdEmpleador(), Boolean.TRUE);
        consultarRepresentantesLegalesEmpleador.execute();
        Persona representanteLegalToUpdate = consultarRepresentantesLegalesEmpleador.getResult();

        // Si no se encuentra un representante legal existente, registrar campos como diferentes y retornar
        if (representanteLegalToUpdate == null || representanteLegalToUpdate.getIdPersona() == null) {
            logger.debug("No se encontró un representante legal existente.");
            agregarCamposDiferentes(novedadesConCierreAutomatico, tipoTransaccion,
                Arrays.asList(TIPO_IDENTIFICACION_REPRESENTANTE,
                    NUMERO_IDENTIFICACION_REPRESENTANTE,
                    EMAIL_REPRESENTANTE,
                    TELEFONO_REPRE), camposDiferentes);
            return;
        }

        // Crear una instancia si el representante legal cargado es nulo
        if (repreLegalCargue == null) {
            repreLegalCargue = new PersonaModeloDTO();
        }

        repreLegalCargue.setIdPersona(representanteLegalToUpdate.getIdPersona());

        //# 6
        verificarDiferencia(repreLegalCargue.getTipoIdentificacion().name(), representanteLegalToUpdate.getTipoIdentificacion().name(), TIPO_IDENTIFICACION_REPRESENTANTE, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
        //# 7
        verificarDiferencia(repreLegalCargue.getNumeroIdentificacion(), representanteLegalToUpdate.getNumeroIdentificacion(), NUMERO_IDENTIFICACION_REPRESENTANTE, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

        UbicacionModeloDTO ubicacionModeloDTO = repreLegalCargue.getUbicacionModeloDTO() != null ? repreLegalCargue.getUbicacionModeloDTO() : new UbicacionModeloDTO();

        PersonaModeloDTO representaEmprePersonaModeloDTO = new PersonaModeloDTO();
        representaEmprePersonaModeloDTO.convertToDTO(representanteLegalToUpdate, null);
        //agregamos la infomación al modelo que devolvemos
        representaEmprePersonaModeloDTO.setTipoIdentificacion(repreLegalCargue.getTipoIdentificacion());
        representaEmprePersonaModeloDTO.setNumeroIdentificacion(repreLegalCargue.getNumeroIdentificacion());

        //# 8
        verificarDiferencia(representanteLegalToUpdate.getUbicacionPrincipal() != null ? representanteLegalToUpdate.getUbicacionPrincipal().getEmail() : null,
            ubicacionModeloDTO.getEmail(), EMAIL_REPRESENTANTE,
            tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

        //# 9
        verificarDiferencia(representanteLegalToUpdate.getUbicacionPrincipal() != null ? representanteLegalToUpdate.getUbicacionPrincipal().getTelefonoCelular() : null,
            ubicacionModeloDTO.getTelefonoCelular(), TELEFONO_REPRE,
            tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);

        //agregamos la infomación al modelo que devolvemos
        ubicacionModeloDTO.setEmail(repreLegalCargue.getUbicacionModeloDTO().getEmail());
        ubicacionModeloDTO.setTelefonoCelular(repreLegalCargue.getUbicacionModeloDTO().getTelefonoCelular());

        representaEmprePersonaModeloDTO.setUbicacionModeloDTO(ubicacionModeloDTO);

        empleadorCargue.setIdEmpleador(empleadorModeloDTOToUpdate.getIdEmpleador());
        informacionActualizacionNovedadDTO.setRepresentanteLegal(representaEmprePersonaModeloDTO);
    }

    /**
     * Para agregar campos que pertenezcan a un solo TipoTransaccionEnum
     * @param tipoDeNovedades mapa con las novedades si es de cierre automático y con envío al back
     * @param tipoTransaccion novedad que se aplicara
     * @param campos
     * @param camposDiferentes
     */
    private void agregarCamposDiferentes(Map<TipoTransaccionEnum, List<String>> tipoDeNovedades,
                                         TipoTransaccionEnum tipoTransaccion, List<String> campos,
                                         List<String> camposDiferentes) {
        campos.forEach(campo -> addCampoDiferente(tipoDeNovedades, tipoTransaccion, campo, camposDiferentes));
    }

    /**
     * Verifica si hay diferencia entre dos valores y agrega el campo a la lista de campos diferentes si se encuentra una diferencia.
     *
     * @param valor1           Primer valor a comparar.
     * @param valor2           Segundo valor a comparar.
     * @param campo            Nombre del campo que se está comparando.
     * @param tipoTransaccion  Tipo de transacción asociada a la diferencia.
     * @param camposDiferentes Lista de campos que presentan diferencias.
     * @param tipoDeNovedades  Mapa de tipos de transacción con campos para cierre automático.
     */
    private void verificarDiferencia(Object valor1, Object valor2, String campo, TipoTransaccionEnum tipoTransaccion,
                                     List<String> camposDiferentes,
                                     Map<TipoTransaccionEnum, List<String>> tipoDeNovedades) {
        // Verificar si hay diferencia entre los valores
        if (verifyDifference(valor1, valor2)) {
            // Agregar el campo a la lista de campos diferentes
            addCampoDiferente(tipoDeNovedades, tipoTransaccion, campo, camposDiferentes);
        }
    }



    private String getCodigoCIIU(EmpleadorModeloDTO empleadorModeloDTO) {
        return empleadorModeloDTO != null && empleadorModeloDTO.getCodigoCIIU() != null ? empleadorModeloDTO.getCodigoCIIU().getCodigo() : null;
    }

    //estamos utilizando findFirst() para obtener el primer elemento de la lista de empleadores (o null si la lista está vacía) en lugar de verificar explícitamente si la lista está vacía y luego acceder al primer elemento si no lo está
    private EmpleadorModeloDTO obtenerEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        BuscarEmpleador buscarEmpleador = new BuscarEmpleador(Boolean.TRUE, numeroIdentificacion, tipoIdentificacion, null);
        buscarEmpleador.execute();
        Empleador empleador = buscarEmpleador.getResult().stream().findFirst().orElse(null);
        return empleador != null ? new EmpleadorModeloDTO(empleador) : null;
    }

    /**
     * Adiciona los campos diferente al mapa de novedaes
     *
     * @param tiposNovedad     Mapa con los tipos de novedades agregados
     * @param tipoTransaccion  Tipo transaccion
     * @param campoDiferente   Campo diferente
     * @param camposDiferentes Lista de los campos diferentes
     */
    private void addCampoDiferente(
        Map<TipoTransaccionEnum, List<String>> tiposNovedad,
        TipoTransaccionEnum tipoTransaccion,
        String campoDiferente,
        List<String> camposDiferentes) {

        // Si el mapa de tipos de novedad es nulo, no se realiza más acción
        if (tiposNovedad == null) {
            return;
        }

        // Agregamos el campo diferente a la lista global
        camposDiferentes.add(campoDiferente);

        // Utilizamos computeIfAbsent para evitar la comprobación manual de si el tipo de transacción ya existe en el mapa tiposNovedad. Esta función nos permite obtener la lista correspondiente para el tipo de transacción dado. Si el tipo de transacción no existe en el mapa, se crea una nueva lista automáticamente y se agrega el campo diferente a esa lista.
        // Si el tipo de transacción ya existe en el mapa, simplemente agregamos el campo diferente a la lista correspondiente
        tiposNovedad.computeIfAbsent(tipoTransaccion, k -> new ArrayList<>()).add(campoDiferente);
    }



    /**
     * Identifica si entre los valores parametros existe alguna diferencia
     *
     * @param valueExist Valor existente
     * @param valueNew   Valor Nuevo
     * @return True si existe diferencia, False en caso contrario
     */
    // elimina la necesidad de la variable isDifferent y reduce la lógica a una estructura más simple y legible.
    // Además, utiliza Objects.equals para comparar los valores, lo que maneja correctamente los casos de nulos
    private boolean verifyDifference(Object valueExist, Object valueNew) {
        if (valueExist == null || valueNew == null) {
            return !Objects.equals(valueExist, valueNew);
        } else {
            if (valueExist instanceof BigDecimal && valueNew instanceof BigDecimal) {
                return ((BigDecimal) valueExist).compareTo((BigDecimal) valueNew) != 0;
            } else {
                return !valueExist.equals(valueNew);
            }
        }
    }

    /**
     * Método encargado de consultar un afiliado por tipo y número de
     * identificación.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private PersonaDTO consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug(
                "Inicio de método consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        BuscarAfiliados buscarAfiliadosServices = new BuscarAfiliados(null, null, null, null, numeroIdentificacion,
                tipoIdentificacion);
        buscarAfiliadosServices.execute();
        List<PersonaDTO> personas = buscarAfiliadosServices.getResult();
        logger.debug(
                "Fin de método consultarAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        PersonaDTO personaDTO = null;
        if (personas != null && !personas.isEmpty()) {
            personaDTO = personas.get(0);
        }
        return personaDTO;
    }

    /**
     * Valida y procesa el archivo de respuesta para la actualización de un afiliado.
     *
     * @param informacionActualizacionNovedadDTO Información de la actualización de la novedad.
     * @return Información actualizada de la novedad después de validar el archivo de respuesta.
     */
    private InformacionActualizacionNovedadDTO validarArchivoRespuestaAfiliado(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO) {
        // Inicializar variables
        List<String> camposDiferentes = new ArrayList<>();
        Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico = new HashMap<>();
        Map<TipoTransaccionEnum, List<String>> novedadesConEnvioAlBack = new HashMap<>();
        AfiliadoModeloDTO afiliadoCargue = informacionActualizacionNovedadDTO.getAfiliado();

        // Obtener el afiliado existente
        PersonaDTO afiliadoPersonaDTOToUpdate = consultarAfiliado(afiliadoCargue.getTipoIdentificacion(), afiliadoCargue.getNumeroIdentificacion());

        if (afiliadoPersonaDTOToUpdate != null && afiliadoPersonaDTOToUpdate.getIdAfiliado() != null) {
            logger.info("GLPI45051--2.validarArchivoRespuestaAfiliado-->\nafiliadoPersonaDTOToUpdate:"
                    + afiliadoPersonaDTOToUpdate + "\n afiliadoPersonaDTOToUpdate.getIdAfiliado(): "+afiliadoPersonaDTOToUpdate.getIdAfiliado());
            // Validar los campos diferentes del afiliado
            validarCamposDiferentesAfiliado(informacionActualizacionNovedadDTO, afiliadoCargue, afiliadoPersonaDTOToUpdate, camposDiferentes, novedadesConCierreAutomatico, novedadesConEnvioAlBack);
        } else {
            // Crear un nuevo afiliado si no existe en la base de datos
            PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO(afiliadoCargue.convertToPersonaEntity(), afiliadoCargue.convertToPersonaDetalleEntity());
            CrearPersona crearPersona = new CrearPersona(personaModeloDTO);
            crearPersona.execute();
        }

        // Establecer los resultados de la validación en la información de la novedad
        informacionActualizacionNovedadDTO.setCamposDiferentes(camposDiferentes);
        logger.info("GLPI45051--2.validarArchivoRespuestaAfiliado-->novedadesConEnvioAlBack");
        logger.info(novedadesConEnvioAlBack);
        informacionActualizacionNovedadDTO.setTiposNovedad(novedadesConEnvioAlBack);
        informacionActualizacionNovedadDTO.setTiposNovedadConCierreAutomatico(novedadesConCierreAutomatico);
        informacionActualizacionNovedadDTO.setTipoArchivoRespuesta(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL);

        return informacionActualizacionNovedadDTO;
    }

    /**
     * Valida y gestiona los campos diferentes de un afiliado durante la actualización de novedades.
     *
     * @param informacionActualizacionNovedadDTO Información de la actualización de la novedad.
     * @param afiliadoCargue                    Datos del afiliado que se está actualizando.
     * @param afiliadoPersonaDTOToUpdate        Datos personales del afiliado a actualizar.
     * @param camposDiferentes                  Lista de campos que presentan diferencias.
     * @param novedadesConCierreAutomatico      Mapa de tipos de transacción con listas de campos diferentes para cierre automático.
     * @param novedadesConEnvioAlBack           Mapa de tipos de transacción con listas de campos diferentes para envío al backend.
     */
    private void validarCamposDiferentesAfiliado(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
                                                 AfiliadoModeloDTO afiliadoCargue,
                                                 PersonaDTO afiliadoPersonaDTOToUpdate,
                                                 List<String> camposDiferentes,
                                                 Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico,
                                                 Map<TipoTransaccionEnum, List<String>> novedadesConEnvioAlBack) {
        try {
            // Consultar la clasificación del afiliado                                            
        List<ClasificacionEnum> listClasificacion = consultarClasificacion(afiliadoCargue.getTipoIdentificacion(), afiliadoCargue.getNumeroIdentificacion());
        ClasificacionEnum clasificacion = listClasificacion.iterator().next();
        informacionActualizacionNovedadDTO.setClasificacion(clasificacion);
        informacionActualizacionNovedadDTO.setTipoSolicitante(clasificacion.getSujetoTramite().getDescripcion());
        } catch (Exception e) {
            logger.error("afiliadoCargue.getTipoIdentificacion():"+afiliadoCargue.getTipoIdentificacion()+" afiliadoCargue.getNumeroIdentificacion():"+afiliadoCargue.getNumeroIdentificacion());
            logger.error("Error al consultar la clasificación del afiliado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        afiliadoCargue.setIdPersona(afiliadoPersonaDTOToUpdate.getIdPersona());
        afiliadoCargue.setIdAfiliado(afiliadoPersonaDTOToUpdate.getIdAfiliado());

        TipoTransaccionEnum tipoCambioNombre = TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS;
        System.out.println("GLPI-45051->afiliadoPersonaDTOToUpdate.getPrimerApellido():"+afiliadoPersonaDTOToUpdate.getPrimerApellido()+" afiliadoCargue.getPrimerApellido():"+afiliadoCargue.getPrimerApellido());
        //# 3
        verificarDiferencia(afiliadoPersonaDTOToUpdate.getPrimerApellido(), afiliadoCargue.getPrimerApellido(), PRIMER_APELLIDO, tipoCambioNombre, camposDiferentes, novedadesConCierreAutomatico);
        System.out.println("GLPI-45051->afiliadoPersonaDTOToUpdate.getSegundoApellido():"+afiliadoPersonaDTOToUpdate.getSegundoApellido()+" afiliadoCargue.getSegundoApellido():"+afiliadoCargue.getSegundoApellido());
        //# 4
        if (afiliadoCargue.getSegundoApellido() != null) {
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getSegundoApellido(), afiliadoCargue.getSegundoApellido(), SEGUNDO_APELLIDO, tipoCambioNombre, camposDiferentes, novedadesConCierreAutomatico);
            System.out.println("GLPI-45051->afiliadoPersonaDTOToUpdate.getPrimerNombre():"+afiliadoPersonaDTOToUpdate.getPrimerNombre()+" afiliadoCargue.getPrimerNombre():"+afiliadoCargue.getPrimerNombre());
        }
        //# 5
        verificarDiferencia(afiliadoPersonaDTOToUpdate.getPrimerNombre(), afiliadoCargue.getPrimerNombre(), PRIMER_NOMBRE, tipoCambioNombre, camposDiferentes, novedadesConCierreAutomatico);
        System.out.println("GLPI-45051->afiliadoPersonaDTOToUpdate.getSegundoNombre():"+afiliadoPersonaDTOToUpdate.getSegundoNombre()+" afiliadoCargue.getSegundoNombre():"+afiliadoCargue.getSegundoNombre());
        //# 6
        if (afiliadoCargue.getSegundoNombre() != null){
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getSegundoNombre(), afiliadoCargue.getSegundoNombre(), SEGUNDO_NOMBRE, tipoCambioNombre, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 7
        if (afiliadoCargue.getFechaNacimiento() != null) {
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getFechaNacimiento(), afiliadoCargue.getFechaNacimiento(), FECHA_NACIMIENTO, TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 8
        if (afiliadoCargue.getGenero() != null) {
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getGenero(), afiliadoCargue.getGenero(), GENERO, TipoTransaccionEnum.CAMBIO_GENERO_PERSONAS, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 9
        //# 10
        //# 11
        //# 12
        //# 13
        //# 14
        if (afiliadoCargue.getFechaExpedicionDocumento() != null) {
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getFechaExpedicionDocumento() != null ? afiliadoPersonaDTOToUpdate.getFechaExpedicionDocumento().getTime() : null, afiliadoCargue.getFechaExpedicionDocumento(), FECHA_EXPEDICION, TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 15
        if(afiliadoCargue.getSalarioAfiliado() !=null){
            validarSalario(afiliadoCargue, afiliadoPersonaDTOToUpdate, camposDiferentes, novedadesConCierreAutomatico);
        }

        TipoTransaccionEnum tipoActualizaDatosCaracterizacionPoblacion = TipoTransaccionEnum.CAMBIAR_DATOS_CARACTERIZACION_POBLACION;

        System.out.println("45051--> afiliadoPersonaDTOToUpdate.getOrientacionSexual().name():"+afiliadoPersonaDTOToUpdate.getOrientacionSexual());
        System.out.println("45051--> afiliadoCargue.getOrientacionSexual().name():"+afiliadoCargue.getOrientacionSexual());
        System.out.println("ORIENTACION_SEXUAL-->"+ORIENTACION_SEXUAL);
        System.out.println("tipoActualizaDatosCaracterizacionPoblacion-->"+tipoActualizaDatosCaracterizacionPoblacion);
        System.out.println("camposDiferentes-->"+camposDiferentes);
        System.out.println("novedadesConCierreAutomatico-->"+novedadesConCierreAutomatico);

        //# 16
        if (afiliadoCargue.getOrientacionSexual() != null) {
            verificarDiferencia((afiliadoPersonaDTOToUpdate.getOrientacionSexual() == null ? null : afiliadoPersonaDTOToUpdate.getOrientacionSexual().name()), afiliadoCargue.getOrientacionSexual().name(), ORIENTACION_SEXUAL, tipoActualizaDatosCaracterizacionPoblacion, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 17
        if(afiliadoCargue.getFactorVulnerabilidad() !=null ){
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getFactorVulnerabilidad().name(), afiliadoCargue.getFactorVulnerabilidad().name(), FACTOR_DE_VULNERABILIDAD, tipoActualizaDatosCaracterizacionPoblacion, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 19
        if(afiliadoCargue.getPertenenciaEtnica() !=null){
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getPertenenciaEtnica().name(), afiliadoCargue.getPertenenciaEtnica().name(), PERTENENCIA_ETNICA, tipoActualizaDatosCaracterizacionPoblacion, camposDiferentes, novedadesConCierreAutomatico);
        }

        //# 18
        if(afiliadoCargue.getEstadoCivil() !=null){
            verificarDiferencia(afiliadoPersonaDTOToUpdate.getEstadoCivil().name(), afiliadoCargue.getEstadoCivil().name(), ESTADO_CIVIL, TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS, camposDiferentes, novedadesConCierreAutomatico);
        }
        //# 20
        if(afiliadoCargue.getIdPaisResidencia() !=null){

            verificarDiferencia(afiliadoPersonaDTOToUpdate.getIdPaisResidencia(), afiliadoCargue.getPais().getIdPais(), PAIS, TipoTransaccionEnum.ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS, camposDiferentes, novedadesConCierreAutomatico);
        }


        //# 21, 22, 23
        validarUbicacionPersona(afiliadoCargue, afiliadoPersonaDTOToUpdate.getUbicacionDTO(), camposDiferentes, novedadesConCierreAutomatico);

        //# 25, 25, 26, 27, 28, 29, 30
        validarMedioDePago(novedadesConCierreAutomatico, camposDiferentes, afiliadoCargue);
    }

    /**
     * Valida y gestiona el medio de pago del afiliado.
     *
     * @param novedadesConCierreAutomatico Mapa de tipos de transacción con listas de campos diferentes.
     * @param camposDiferentes            Lista de campos que presentan diferencias.
     * @param afiliadoCargue              Datos del afiliado.
     */
    private void validarMedioDePago(Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico,
                                    List<String> camposDiferentes,
                                    AfiliadoModeloDTO afiliadoCargue) {
        TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL;

        // Consultar el medio de pago actual
        ConsultarMedioDePago consultarMedioDePago = new ConsultarMedioDePago(null, null, afiliadoCargue.getNumeroIdentificacion(), afiliadoCargue.getTipoIdentificacion());
        consultarMedioDePago.execute();
        MedioDePagoModeloDTO medioDePagoModeloDTOActualActivo = consultarMedioDePago.getResult();

        // Consultar el medio de pago EFECTIVO
        ConsultarMedioDePago consultarMedioDePagoTipoEfectivo = new ConsultarMedioDePago(null, TipoMedioDePagoEnum.EFECTIVO, afiliadoCargue.getNumeroIdentificacion(), afiliadoCargue.getTipoIdentificacion());
        consultarMedioDePagoTipoEfectivo.execute();
        MedioDePagoModeloDTO medioDePagoModeloDTOEfectivo = consultarMedioDePagoTipoEfectivo.getResult();

        // Consultar el medio de pago TRANSFERENCIA
        ConsultarMedioDePago consultarMedioDePagoTipoTransferencia = new ConsultarMedioDePago(null, TipoMedioDePagoEnum.TRANSFERENCIA, afiliadoCargue.getNumeroIdentificacion(),afiliadoCargue.getTipoIdentificacion());
        consultarMedioDePagoTipoTransferencia.execute();
        MedioDePagoModeloDTO medioDePagoModeloDTOTransferencia = consultarMedioDePagoTipoTransferencia.getResult();

        if (afiliadoCargue.getMedioDePagoModeloDTO() != null) {
            verificarDiferencia(medioDePagoModeloDTOActualActivo.getTipoMedioDePago(), afiliadoCargue.getMedioDePagoModeloDTO().getTipoMedioDePago(), TIPO_MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO, tipoTransaccion, camposDiferentes, novedadesConCierreAutomatico);
        }else{
            return;
        }

        // Validar si el tipo de medio de pago es EFECTIVO
        if (afiliadoCargue.getMedioDePagoModeloDTO().getTipoMedioDePago().equals(TipoMedioDePagoEnum.EFECTIVO)) {
            // Si no existe el medio de pago EFECTIVO, lo creamos
            if (medioDePagoModeloDTOEfectivo.getIdMedioDePago() == null) {
                logger.info("No se encontró medioDePagoModeloDTOEfectivo existente.");
                afiliadoCargue.getMedioDePagoModeloDTO().setSitioPago(obtenerIdSitioPagoPredeterminado());
                // Para que se ponga en rojo el medio de pago
                //# 24
                addCampoDiferente(novedadesConCierreAutomatico, tipoTransaccion, TIPO_MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO, camposDiferentes);
                return; // Salir del método después de crear el medio de pago EFECTIVO
            }
        }
        // Validar si el tipo de medio de pago es TRANSFERENCIA
        else if (afiliadoCargue.getMedioDePagoModeloDTO().getTipoMedioDePago().equals(TipoMedioDePagoEnum.TRANSFERENCIA)) {
            // Si no existe el medio de pago TRANSFERENCIA, lo creamos
            if (medioDePagoModeloDTOTransferencia.getIdMedioDePago() == null) {
                logger.info("No se encontró medioDePagoModeloDTOTransferencia existente.");
                //# 24 - 30
                agregarCamposDiferentes(novedadesConCierreAutomatico, tipoTransaccion,
                        Arrays.asList(TIPO_MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO,
                                TIPO_CUENTA,
                                NUMERO_CUENTA,
                                TIPO_IDENTIFICACION_TITULAR,
                                NUMERO_IDENTIFICACION_TITULAR,
                                NOMBRE_TITULAR_CUENTA,
                                NIT_BANCO
                        ), camposDiferentes);
            } else {
                // Comparar y validar cada campo del medio de pago TRANSFERENCIA
                if (medioDePagoModeloDTOTransferencia.getIdMedioDePago() != null) {
                    logger.info("Va a actualizar un medioDePagoModeloDTOTransferencia existente.");
                    boolean isDifferent = verificarDiferenciasTransferencia(novedadesConCierreAutomatico, tipoTransaccion, camposDiferentes, medioDePagoModeloDTOTransferencia, afiliadoCargue);
                    if (isDifferent) {
                        // Para que se ponga en rojo el medio de pago
                        //# 24
                        addCampoDiferente(novedadesConCierreAutomatico, tipoTransaccion, TIPO_MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO, camposDiferentes);
                    }
                }
            }
        }
    }

    private boolean verificarDiferenciasTransferencia(Map<TipoTransaccionEnum, List<String>> tiposNovedad,
                                                      TipoTransaccionEnum tipoTransaccionEnum,
                                                      List<String> camposDiferentes,
                                                      MedioDePagoModeloDTO medioDePagoModeloDTOTransferencia,
                                                      AfiliadoModeloDTO afiliadoCargue) {
        boolean isDifferent = false;
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getTipoCuenta().name():"+medioDePagoModeloDTOTransferencia.getTipoCuenta().name()+" afiliadoCargue.getMedioDePagoModeloDTO().getTipoCuenta().name():"+afiliadoCargue.getMedioDePagoModeloDTO().getTipoCuenta().name());
        if (verifyDifference(medioDePagoModeloDTOTransferencia.getTipoCuenta().name(), afiliadoCargue.getMedioDePagoModeloDTO().getTipoCuenta().name())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, TIPO_CUENTA, camposDiferentes);
            isDifferent = true;
        }
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getNumeroCuenta():"+medioDePagoModeloDTOTransferencia.getNumeroCuenta()+" afiliadoCargue.getMedioDePagoModeloDTO().getNumeroCuenta():"+afiliadoCargue.getMedioDePagoModeloDTO().getNumeroCuenta());
        if (verifyDifference(medioDePagoModeloDTOTransferencia.getNumeroCuenta(), afiliadoCargue.getMedioDePagoModeloDTO().getNumeroCuenta())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, NUMERO_CUENTA, camposDiferentes);
            isDifferent = true;
        }
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getTipoIdentificacionTitular().name():"+medioDePagoModeloDTOTransferencia.getTipoIdentificacionTitular().name()+" afiliadoCargue.getMedioDePagoModeloDTO().getTipoIdentificacionTitular().name():"+afiliadoCargue.getMedioDePagoModeloDTO().getTipoIdentificacionTitular().name());
        if (verifyDifference(medioDePagoModeloDTOTransferencia.getTipoIdentificacionTitular().name(), afiliadoCargue.getMedioDePagoModeloDTO().getTipoIdentificacionTitular().name())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, TIPO_IDENTIFICACION_TITULAR, camposDiferentes);
            isDifferent = true;
        }
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getNumeroIdentificacionTitular():"+medioDePagoModeloDTOTransferencia.getNumeroIdentificacionTitular()+" afiliadoCargue.getMedioDePagoModeloDTO().getNumeroIdentificacionTitular():"+afiliadoCargue.getMedioDePagoModeloDTO().getNumeroIdentificacionTitular());
        if (verifyDifference(medioDePagoModeloDTOTransferencia.getNumeroIdentificacionTitular(), afiliadoCargue.getMedioDePagoModeloDTO().getNumeroIdentificacionTitular())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, NUMERO_IDENTIFICACION_TITULAR, camposDiferentes);
            isDifferent = true;
        }
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getNombreTitularCuenta():"+medioDePagoModeloDTOTransferencia.getNombreTitularCuenta()+" afiliadoCargue.getMedioDePagoModeloDTO().getNombreTitularCuenta():"+afiliadoCargue.getMedioDePagoModeloDTO().getNombreTitularCuenta());
        if (verifyDifference(medioDePagoModeloDTOTransferencia.getNombreTitularCuenta(), afiliadoCargue.getMedioDePagoModeloDTO().getNombreTitularCuenta())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, NOMBRE_TITULAR_CUENTA, camposDiferentes);
            isDifferent = true;
        }
        System.out.println("GLPI-45051-->medioDePagoModeloDTOTransferencia.getIdBanco():"+medioDePagoModeloDTOTransferencia.getIdBanco()+" afiliadoCargue.getMedioDePagoModeloDTO().getBancoModeloDTO().getId():"+afiliadoCargue.getMedioDePagoModeloDTO().getBancoModeloDTO().getId());
        if (!Objects.equals(medioDePagoModeloDTOTransferencia.getIdBanco(), afiliadoCargue.getMedioDePagoModeloDTO().getBancoModeloDTO().getId())) {
            addCampoDiferente(tiposNovedad, tipoTransaccionEnum, NIT_BANCO, camposDiferentes);
            isDifferent = true;
        }
        if (isDifferent) {
            //le cargamos el id para que cuando se ejecute la novedad y realice el merge lo actualice y no lo cree
            afiliadoCargue.getMedioDePagoModeloDTO().setIdMedioDePago(medioDePagoModeloDTOTransferencia.getIdMedioDePago());
        }
        return isDifferent;
    }

    private Long obtenerIdSitioPagoPredeterminado() {
        ConsultarIdSitioPagoPredeterminado consultarIdSitioPagoPredeterminado = new ConsultarIdSitioPagoPredeterminado();
        consultarIdSitioPagoPredeterminado.execute();
        return consultarIdSitioPagoPredeterminado.getResult();
    }

    /**
     * Valida la ubicación de una persona comparando los datos actuales con los datos cargados de un afiliado.
     *
     * @param afiliadoCargue              Datos del afiliado cargado.
     * @param afiliadoPersonaDTOToUpdate  Datos de la persona a actualizar.
     * @param camposDiferentes            Lista de campos que presentan diferencias.
     * @param novedadesConCierreAutomatico Mapa de novedades con cierre automático.
     */
    private void validarUbicacionPersona(AfiliadoModeloDTO afiliadoCargue,
                                         UbicacionDTO ubicacionDTOToUpdate,
                                         List<String> camposDiferentes,
                                         Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico){
        TipoTransaccionEnum tipoCambioDatosCorrespondencia = TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS;
        if(ubicacionDTOToUpdate != null && ubicacionDTOToUpdate.getIdUbicacion() != null){
            //# 21
            if (afiliadoCargue.getEmail() != null) {
                verificarDiferencia(ubicacionDTOToUpdate.getCorreoElectronico(), afiliadoCargue.getEmail(), EMAIL, tipoCambioDatosCorrespondencia, camposDiferentes, novedadesConCierreAutomatico);
            }
            //# 22
            if (afiliadoCargue.getCelular() != null) {
                verificarDiferencia(ubicacionDTOToUpdate.getTelefonoCelular(), afiliadoCargue.getCelular(), CELULAR_AFILIADO_PRINCIPAL, tipoCambioDatosCorrespondencia, camposDiferentes, novedadesConCierreAutomatico);
            }
            //# 23
            if (afiliadoCargue.getTelefono() != null) {
                verificarDiferencia(ubicacionDTOToUpdate.getTelefonoFijo(), afiliadoCargue.getTelefono(), TELEFONO_AFILIADO_PRINCIPAL, tipoCambioDatosCorrespondencia, camposDiferentes, novedadesConCierreAutomatico);
            }
        }
    }

    /**
     * Valida el salario del afiliado comparándolo con su salario registrado en relación laboral activa.
     * Si hay una discrepancia en los salarios, se actualiza el ID del rol afiliado y se registra la diferencia.
     *
     * @param afiliadoCargue             Datos del afiliado cargado que se está procesando.
     * @param afiliadoPersonaDTOToUpdate Datos de la persona del afiliado a actualizar.
     * @param camposDiferentes           Lista de campos que muestran diferencias con el sistema existente.
     * @param novedadesConCierreAutomatico Mapa que almacena las novedades con cierre automático por tipo de transacción.
     */
    private void validarSalario(AfiliadoModeloDTO afiliadoCargue,
                                PersonaDTO afiliadoPersonaDTOToUpdate,
                                List<String> camposDiferentes,
                                Map<TipoTransaccionEnum, List<String>> novedadesConCierreAutomatico){
        //Obtenermos todos los empleadores relacionados con este trabajador
        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresRelacionadosAfiliado = new ObtenerEmpleadoresRelacionadosAfiliado(null, afiliadoPersonaDTOToUpdate.getTipoIdentificacion(), null, null, afiliadoPersonaDTOToUpdate.getNumeroIdentificacion());
        obtenerEmpleadoresRelacionadosAfiliado.execute();
        List<EmpleadorRelacionadoAfiliadoDTO> listaEmpleadoresRelacionadosAfiliado = obtenerEmpleadoresRelacionadosAfiliado.getResult();
        if (listaEmpleadoresRelacionadosAfiliado != null && !listaEmpleadoresRelacionadosAfiliado.isEmpty()) {
            //Filtramos todos los empleadores que esten activos y que además el trabajador esté activo como dependiente.
            List<EmpleadorRelacionadoAfiliadoDTO> listaEmpleadoresRelacionadosAfiliadoNew = listaEmpleadoresRelacionadosAfiliado.stream().filter(v -> v.getEstadoEmpleador().equals(EstadoEmpleadorEnum.ACTIVO) && v.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)).collect(Collectors.toList());
            //como trabajador solo puede estar afiliado como dependiente en 1 sola empresa
            if (listaEmpleadoresRelacionadosAfiliadoNew.size() == 1) {
                EmpleadorRelacionadoAfiliadoDTO empleadorRelacionadoAfiliadoDTO = listaEmpleadoresRelacionadosAfiliadoNew.get(0);
                //Obtenemos el salario del trabajador con respecto a este empleador
                ConsultarInformacionRelacionLaboral consultarInformacionRelacionLaboral = new ConsultarInformacionRelacionLaboral(empleadorRelacionadoAfiliadoDTO.getIdRolAfiliado());
                consultarInformacionRelacionLaboral.execute();
                InfoRelacionLaboral360DTO infoRelacionLaboral360DTO = consultarInformacionRelacionLaboral.getResult();
                if (infoRelacionLaboral360DTO.getSalarioMensual().compareTo(new BigDecimal(afiliadoCargue.getSalarioAfiliado())) != 0) {
                    //le dejamos el idRolAfiliado para que se pueda actualizar el salario del trabajador
                    afiliadoCargue.setIdRolAfiliado(empleadorRelacionadoAfiliadoDTO.getIdRolAfiliado());
                    addCampoDiferente(novedadesConCierreAutomatico, TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL, SALARIO, camposDiferentes);
                }
            }
        }
    }

    /**
     * Se reliza la validacion del los campos del archivo de acuerdo a lo
     * encontrado en la base de datos
     *
     * @param listInformacionArchivo Lista con la informacion del archivo
     */
    private InformacionActualizacionNovedadDTO validarArchivoRespuestaBeneficiario(
            InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO) {
        List<String> camposDiferentes = new ArrayList<>();
        Map<TipoTransaccionEnum, List<String>> tiposNovedad = new HashMap<>();

        BeneficiarioModeloDTO beneficiarioCargue = informacionActualizacionNovedadDTO.getBeneficiario();
        ConsultarBeneficiarioTipoNroIdentificacion consultarBeneficiario = new ConsultarBeneficiarioTipoNroIdentificacion(
                beneficiarioCargue.getNumeroIdentificacion(), beneficiarioCargue.getTipoIdentificacion());
        consultarBeneficiario.execute();
        List<BeneficiarioModeloDTO> listBeneficiarios = consultarBeneficiario.getResult();
        BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        if (listBeneficiarios != null && !listBeneficiarios.isEmpty()) {
            beneficiarioModeloDTO = listBeneficiarios.iterator().next();
        }
        if (beneficiarioModeloDTO != null && beneficiarioModeloDTO.getIdBeneficiario() != null) {
            informacionActualizacionNovedadDTO.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
            informacionActualizacionNovedadDTO.setTipoSolicitante(
                    beneficiarioModeloDTO.getTipoBeneficiario().getSujetoTramite().getDescripcion());
            TipoTransaccionEnum tipoCambioNombre = TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS;
            if (verifyDifference(beneficiarioModeloDTO.getPrimerApellido(), beneficiarioCargue.getPrimerApellido())) {
                addCampoDiferente(tiposNovedad, tipoCambioNombre, PRIMER_APELLIDO, camposDiferentes);
            }
            if (verifyDifference(beneficiarioModeloDTO.getSegundoApellido(), beneficiarioCargue.getSegundoApellido())) {
                addCampoDiferente(tiposNovedad, tipoCambioNombre, SEGUNDO_APELLIDO, camposDiferentes);
            }
            if (verifyDifference(beneficiarioModeloDTO.getPrimerNombre(), beneficiarioCargue.getPrimerNombre())) {
                addCampoDiferente(tiposNovedad, tipoCambioNombre, PRIMER_NOMBRE, camposDiferentes);
            }
            if (verifyDifference(beneficiarioModeloDTO.getSegundoNombre(), beneficiarioCargue.getSegundoNombre())) {
                addCampoDiferente(tiposNovedad, tipoCambioNombre, SEGUNDO_NOMBRE, camposDiferentes);
            }
            if (verifyDifference(beneficiarioModeloDTO.getFechaNacimiento(), beneficiarioCargue.getFechaNacimiento())) {
                camposDiferentes.add(FECHA_NACIMIENTO);
            }
            if (verifyDifference(beneficiarioModeloDTO.getGenero(), beneficiarioCargue.getGenero())) {
                camposDiferentes.add(GENERO);
            }
            if (verifyDifference(beneficiarioModeloDTO.getNivelEducativo(), beneficiarioCargue.getNivelEducativo())) {
                addCampoDiferente(tiposNovedad, TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS,NIVEL_EDUCATIVO, camposDiferentes);
            }
            beneficiarioCargue.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
            beneficiarioCargue.setIdAfiliado(beneficiarioModeloDTO.getIdAfiliado());
            beneficiarioCargue.setIdPersona(beneficiarioModeloDTO.getIdPersona());
            ConsultarAfiliadoPorId consultarAfiliadoPorId = new ConsultarAfiliadoPorId(
                    beneficiarioModeloDTO.getIdAfiliado());
            consultarAfiliadoPorId.execute();
            informacionActualizacionNovedadDTO.setAfiliado(consultarAfiliadoPorId.getResult());
        } else {
            PersonaModeloDTO persona = new PersonaModeloDTO(beneficiarioCargue.convertToPersonaEntity(),
                    beneficiarioCargue.convertToPersonaDetalleEntity());
            CrearPersona crearPersona = new CrearPersona(persona);
            crearPersona.execute();
        }
        informacionActualizacionNovedadDTO.setCamposDiferentes(camposDiferentes);
        logger.info("GLPI45051--3.validarArchivoRespuestaBeneficiario-->novedadesConEnvioAlBack");
        logger.info(tiposNovedad);
        informacionActualizacionNovedadDTO.setTiposNovedad(tiposNovedad);
        informacionActualizacionNovedadDTO.setTipoArchivoRespuesta(TipoArchivoRespuestaEnum.BENEFICIARIO);
        return informacionActualizacionNovedadDTO;
    }

    /**
     * Se obtiene el tipo transaccion de beneficiario de acuerdo a la
     * clasificacion del mismo
     *
     * @param clasificacion          Clasificacion beneficiario
     * @param isCambioNombre         Indica si se busca novedad cambio nombre
     * @param isCambioNivelEducativo Indica si se busca novedad cambio nivel
     *                               educativo
     * @param isInactivar            Indica si se busca la novedad inactivacion
     * @param isCambioCertificado    Indica si se busca la novedad cambio
     *                               certificado
     * @param edadBeneficiario       Indica la edad actual del beneficiario, solo
     *                               aplica si se solicita cambio certificado
     * @return Tipo transaccion
     */
    private TipoTransaccionEnum obtenerTipoTransaccionClasificacionBeneficiario(ClasificacionEnum clasificacion,
                                                                                Boolean isInactivar,
                                                                                Boolean isCambioCertificado, Integer edadBeneficiario) {
        // Contiene resultado resultado
        TipoTransaccionEnum tipoTransaccionEnum = null;

        // Mapa para las transacciones de Cambio certificado escolar Menores de 23 anios
        Map<ClasificacionEnum, TipoTransaccionEnum> mapTransaccionCambioCertificadoEscolar = new HashMap<>();
        mapTransaccionCambioCertificadoEscolar.put(ClasificacionEnum.HIJO_BIOLOGICO,
                TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL);
        mapTransaccionCambioCertificadoEscolar.put(ClasificacionEnum.HIJO_ADOPTIVO,
                TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL);
        mapTransaccionCambioCertificadoEscolar.put(ClasificacionEnum.HIJASTRO,
                TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL);
        mapTransaccionCambioCertificadoEscolar.put(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES,
                TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL);
        mapTransaccionCambioCertificadoEscolar.put(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA,
                TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);

        // Mapa para las transacciones de Cambio escolaridad Menores de 19 anios
        Map<ClasificacionEnum, TipoTransaccionEnum> mapTransaccionCambioEscolaridad = new HashMap<>();
        mapTransaccionCambioEscolaridad.put(ClasificacionEnum.HIJO_BIOLOGICO,
                TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL);
        mapTransaccionCambioEscolaridad.put(ClasificacionEnum.HIJO_ADOPTIVO,
                TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL);
        mapTransaccionCambioEscolaridad.put(ClasificacionEnum.HIJASTRO,
                TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL);
        mapTransaccionCambioEscolaridad.put(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES,
                TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL);
        mapTransaccionCambioEscolaridad.put(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA,
                TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);

        if (isInactivar) {
            tipoTransaccionEnum = getTipoTransaccionInactivarBeneficiarioByClasificacion(clasificacion);
        } else if (isCambioCertificado && edadBeneficiario != null) {
            if (edadBeneficiario < 19) {
                tipoTransaccionEnum = obtenerTipoTransaccion(mapTransaccionCambioEscolaridad, clasificacion);
            } else if (edadBeneficiario < 23) {
                tipoTransaccionEnum = obtenerTipoTransaccion(mapTransaccionCambioCertificadoEscolar, clasificacion);
            }
        }
        return tipoTransaccionEnum;
    }

    /**
     * Obtiene el tipo de transaccion del mapa de relacion por tipo de
     * clasificacion
     *
     * @param mapTransaccion Informacion calsificacion transaccion
     * @param clasificacion  Clasificacion a buscar
     * @return Tipo transaccion
     */
    private TipoTransaccionEnum obtenerTipoTransaccion(Map<ClasificacionEnum, TipoTransaccionEnum> mapTransaccion,
                                                       ClasificacionEnum clasificacion) {
        if (mapTransaccion.containsKey(clasificacion)) {
            return mapTransaccion.get(clasificacion);
        }
        return null;
    }

    /**
     * Realiza el registro del cargue de archivo de actualizacion de informacion
     *
     * @param cargueArchivoActualizacionDTO Informacion cargue archivo
     *                                      actualizacion
     * @return Identificador del cargue
     */
    private Long crearActualizarCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        CrearCargueArchivoActualizacion crearCargueArchivoActualizacion = new CrearCargueArchivoActualizacion(cargueArchivoActualizacionDTO);
        crearCargueArchivoActualizacion.execute();
        return crearCargueArchivoActualizacion.getResult();
    }

    /**
     * Realiza el registro de las diferencias del archivo de actualiacion
     *
     * @param diferenciasCargueActualizacionDTO Informacion diferencia
     * @return Identificador de la diferencia
     */
    private Long crearActualizarDiferenciasCargueArchivoActualizacion(
            DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO) {
        CrearDiferenciaArchivoActualizacion crearDiferenciasCargueArchivoActualizacion = new CrearDiferenciaArchivoActualizacion(
                diferenciasCargueActualizacionDTO);
        crearDiferenciasCargueArchivoActualizacion.execute();
        return crearDiferenciasCargueArchivoActualizacion.getResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * remitirNovedades(java.lang.Long, java.util.List)
     */
    @Override
    public void remitirNovedades(Long codigoCargue, TipoArchivoRespuestaEnum tipoArchivo, String usuarioDestino,
                                 List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad, UserDTO userDTO) {
        try {
            logger.info("GLPI45051-->remitirNovedades-->codigoCargue:"+codigoCargue
                    +" tipoArchivo: "+tipoArchivo.getDescripcion()
                    +" usuarioDestino: "+usuarioDestino
            );
            ConsultarCargueArchivoActualizacion cargueArchivoActualizacion = new ConsultarCargueArchivoActualizacion(
                    codigoCargue);
            cargueArchivoActualizacion.execute();
            CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO = cargueArchivoActualizacion.getResult();
            for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listActualizacionInfoNovedad) {
                registrarNovedadDiferenciaCargue(tipoArchivo, usuarioDestino, informacionActualizacionNovedadDTO,
                        cargueArchivoActualizacionDTO,
                        userDTO);
            }
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Crea los registros de solicitud de novedad para cada diferencia
     * encontrado, realiza la asignacion de la tarea al usuario especialista
     * para que la gestione por el HU244
     *
     * @param tipoArchivo                        Tipo de archivo que contiene las
     *                                           diferencias
     * @param informacionActualizacionNovedadDTO Informacion para la
     *                                           actiualizacion
     * @param cargueArchivoActualizacionDTO      Informacion cargue
     * @param userDTO                            Usuario
     * @throws JsonProcessingException Excepcion lanzada en caso que ocurra un
     *                                 error convirtiendo el dato paylod de
     *                                 diferencia a almacenar
     */
    private void registrarNovedadDiferenciaCargue(TipoArchivoRespuestaEnum tipoArchivo, String usuarioDestino,
                                                  InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
                                                  CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO, UserDTO userDTO)
            throws JsonProcessingException {
        logger.info("GLPI45051 1--> registrarNovedadDiferenciaCargue -----> informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico()-> "
                + (informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico() != null)
                + " !informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico().isEmpty()--> "
                +!informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico().isEmpty());

        //Registro de novedades con cierre automático para empleador. Recorremos los campos diferentes
        if (informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico() != null && !informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico().isEmpty()) {
            logger.info("GLPI45051 2--> registrarNovedadDiferenciaCargue -----> ");

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            for (TipoTransaccionEnum tipoTransaccion : informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico().keySet()) {
                logger.info("GLPI45051 2.1--> registrarNovedadDiferenciaCargue -----> "+tipoTransaccion.getDescripcion());

                // Se ajustan los campos diferentes para cada tipo transascion
                List<String> campos = informacionActualizacionNovedadDTO.getTiposNovedadConCierreAutomatico().get(tipoTransaccion);
                informacionActualizacionNovedadDTO.setCamposDiferentes(campos);
                informacionActualizacionNovedadDTO.setCodigoIdentificacionECM(cargueArchivoActualizacionDTO.getCodigoIdentificacionECM());
                // Se crea la diferencia en BD
                DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO = new DiferenciasCargueActualizacionDTO();
                diferenciasCargueActualizacionDTO.setTipoTransaccion(tipoTransaccion);
                diferenciasCargueActualizacionDTO.setCargueArchivoActualizacion(cargueArchivoActualizacionDTO);
                Long idDiferencia = crearActualizarDiferenciasCargueArchivoActualizacion(diferenciasCargueActualizacionDTO);
                // Start -> Se construye la solicitud de novedad como en el front
                ParametrizacionNovedadModeloDTO novedad = consultarNovedad(tipoTransaccion);
                novedad.setPuntoResolucion(PuntoResolucionEnum.FRONT);
                SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
                solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION);
                solNovedadDTO.setTipoTransaccion(tipoTransaccion);
                logger.info("***Weizman => registrarNovedadDiferenciaCargue -> ParametrizacionNovedadModeloDTO novedad -> " + novedad.toString());
                solNovedadDTO.setNovedadDTO(novedad);
                solNovedadDTO.setIdDiferenciaCargueActualizacion(idDiferencia);
                ClasificacionEnum clasificacion = informacionActualizacionNovedadDTO.getClasificacion();
                solNovedadDTO.setClasificacion(clasificacion);
                // Se construye la solicitud de novedad
                if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS)) {
                    solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                    solNovedadDTO.setContinuaProceso(true);
                }
                SolicitudNovedadDTO solNovedadDTOTemp = construirSolicitudNovedad(tipoArchivo, tipoTransaccion,
                        informacionActualizacionNovedadDTO, idDiferencia, CanalRecepcionEnum.ARCHIVO_ACTUALIZACION,
                        userDTO);
                //como es de cierre front extraemos solo la impormación del obejeto a actualizar
                if(tipoArchivo.equals(TipoArchivoRespuestaEnum.EMPLEADOR)){
                    logger.info("***Weizman => registrarNovedadDiferenciaCargue -> solNovedadDTOTemp.getDatosEmpleador() -> " + solNovedadDTOTemp.getDatosEmpleador().toString());
                    solNovedadDTO.setDatosEmpleador(solNovedadDTOTemp.getDatosEmpleador());
                } else if(tipoArchivo.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)){
                    logger.info("registrarNovedadDiferenciaCargue -----> GLPI45051 AFILIADO_PRINCIPAL ----->" + informacionActualizacionNovedadDTO);
                    logger.info("***Weizman => registrarNovedadDiferenciaCargue -> solNovedadDTOTemp.getDatosPersona() -> " + solNovedadDTOTemp.getDatosPersona().toString());
                    solNovedadDTO.setDatosPersona(solNovedadDTOTemp.getDatosPersona());
                }
                // Se radica la solicitud
                radicarSolicitudNovedad(solNovedadDTO, userDTO);
                // Se actualiza el payload de la diferencia con la informacion de la solicitud
                jsonPayload = mapper.writeValueAsString(solNovedadDTO);
                System.out.println("----------Cierre automatico----------");
                System.out.println("45051 jsonPayload: ");
                System.out.println(jsonPayload);
                System.out.println("---------FIn Cierre automatico-----------");
                diferenciasCargueActualizacionDTO.setIdDiferenciasCargueActualizacion(idDiferencia);
                diferenciasCargueActualizacionDTO.setJsonPayload(jsonPayload);
                crearActualizarDiferenciasCargueArchivoActualizacion(diferenciasCargueActualizacionDTO);

            }
        }

        logger.info("GLPI45051 --> registrarNovedadDiferenciaCargue -----> "+
                " informacionActualizacionNovedadDTO.getTiposNovedad()--> " + informacionActualizacionNovedadDTO.getTiposNovedad() +
        " !informacionActualizacionNovedadDTO.getTiposNovedad().isEmpty()--> " + !informacionActualizacionNovedadDTO.getTiposNovedad().isEmpty());
        //Registro de novedades con envío al back(especialista de novedades)
        if (informacionActualizacionNovedadDTO.getTiposNovedad() != null && !informacionActualizacionNovedadDTO.getTiposNovedad().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            for (TipoTransaccionEnum tipoTransaccion : informacionActualizacionNovedadDTO.getTiposNovedad().keySet()) {
                // Se ajustan los campos diferentes para cada tipo transascion

                List<String> campos = informacionActualizacionNovedadDTO.getTiposNovedad().get(tipoTransaccion);
                informacionActualizacionNovedadDTO.setCamposDiferentes(campos);
                informacionActualizacionNovedadDTO.setCodigoIdentificacionECM(cargueArchivoActualizacionDTO.getCodigoIdentificacionECM());
                // Se crea la diferencia en BD
                DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO = new DiferenciasCargueActualizacionDTO();
                diferenciasCargueActualizacionDTO.setTipoTransaccion(tipoTransaccion);
                diferenciasCargueActualizacionDTO.setCargueArchivoActualizacion(cargueArchivoActualizacionDTO);
                Long idDiferencia = crearActualizarDiferenciasCargueArchivoActualizacion(diferenciasCargueActualizacionDTO);
                // Se construye la solicitud de novedad
                SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(tipoArchivo, tipoTransaccion,informacionActualizacionNovedadDTO, idDiferencia, CanalRecepcionEnum.ARCHIVO_ACTUALIZACION,userDTO);
                // Se radica la solicitud
                SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);

                // Se actualiza el payload de la diferencia con la informacion de la solicitud
                jsonPayload = mapper.writeValueAsString(solNovedadDTO);
                diferenciasCargueActualizacionDTO.setIdDiferenciasCargueActualizacion(idDiferencia);
                diferenciasCargueActualizacionDTO.setJsonPayload(jsonPayload);
                crearActualizarDiferenciasCargueArchivoActualizacion(diferenciasCargueActualizacionDTO);
                System.out.println("45051 jsonPayload: ");
                System.out.println(jsonPayload);
                System.out.println("--------------------");
                // Se inicia el proceso BPM y se asigna al usuario enviado
                iniciarProcesoNovedadCargueActualizacion(solicitudNovedad.getIdSolicitud(), usuarioDestino,ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION);
                logger.info("GLPI45051 --> iniciarProcesoNovedadCargueActualizacion -----> "+
                        " solicitudNovedad.getIdSolicitud()--> " + solicitudNovedad.getIdSolicitud() +
                        " usuarioDestino--> " + usuarioDestino +
                        " ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION --> "+ProcesoEnum.NOVEDADES_ARCHIVOS_ACTUALIZACION.getDescripcion());
            }
        }

    }

    /**
     * Construye el objeto SolicitudNovedadDTO con la informacion necesaria
     *
     * @param tipoArchivo                        Tipo archivo asociado
     * @param tipoTransaccion                    Tipo transaccion de la solicitud
     * @param informacionActualizacionNovedadDTO Informacion para la
     *                                           actualizacion
     * @param idDiferencia                       Identificador de la diferencia
     *                                           guardada
     * @param userDTO                            Usuario
     * @return Objeto SolicitudNovedadDTO
     */
    private SolicitudNovedadDTO construirSolicitudNovedad(TipoArchivoRespuestaEnum tipoArchivo,
                                                          TipoTransaccionEnum tipoTransaccion,
                                                          InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO, Long idDiferencia,
                                                          CanalRecepcionEnum canal, UserDTO userDTO) {
        ParametrizacionNovedadModeloDTO novedad = consultarNovedad(tipoTransaccion);
        SolicitudNovedadDTO solicitudNovedadDTO = new SolicitudNovedadDTO();
        solicitudNovedadDTO.setCanalRecepcion(canal);
        solicitudNovedadDTO.setTipoTransaccion(tipoTransaccion);
        solicitudNovedadDTO.setNovedadDTO(novedad);
        solicitudNovedadDTO.setIdDiferenciaCargueActualizacion(idDiferencia);
        ClasificacionEnum clasificacion = informacionActualizacionNovedadDTO.getClasificacion();
        solicitudNovedadDTO.setClasificacion(clasificacion);

        if (tipoArchivo.equals(TipoArchivoRespuestaEnum.EMPLEADOR)) {

            EmpleadorModeloDTO empleadorModeloDTO = informacionActualizacionNovedadDTO.getEmpleador();
            PersonaModeloDTO representanteLegalModeloDTO = informacionActualizacionNovedadDTO.getRepresentanteLegal();
            DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO = new DatosEmpleadorNovedadDTO();
            datosEmpleadorNovedadDTO.setTipoIdentificacion(empleadorModeloDTO.getTipoIdentificacion());
            datosEmpleadorNovedadDTO.setNumeroIdentificacion(empleadorModeloDTO.getNumeroIdentificacion());
            datosEmpleadorNovedadDTO.setIdEmpleador(empleadorModeloDTO.getIdEmpleador());
            // Datos por cada tipo de transaccion
            //LAS 2 PRIMERAS SON DE ENVÍO AL BACK (Especialista de novedades)
            if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_NATURALEZA_JURIDICA)) {
                datosEmpleadorNovedadDTO.setNaturalezaJuridica(empleadorModeloDTO.getNaturalezaJuridica());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL)) {
                datosEmpleadorNovedadDTO.setMedioDePagoSubsidioMonetario(empleadorModeloDTO.getMedioDePagoSubsidioMonetario());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_RAZON_SOCIAL_NOMBRE)) {
                datosEmpleadorNovedadDTO.setIdEmpleador(empleadorModeloDTO.getIdEmpleador());
                datosEmpleadorNovedadDTO.setRazonSocial(empleadorModeloDTO.getRazonSocial());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL)) {
                Municipio municipio = new Municipio();
                municipio.setIdMunicipio(empleadorModeloDTO.getUbicacionModeloDTO().getIdMunicipio());
                datosEmpleadorNovedadDTO.setMunicipioOficinaPrincipal(municipio);
                datosEmpleadorNovedadDTO.setIdUbicacionPrincipal(empleadorModeloDTO.getIdUbicacionPrincipal());
                datosEmpleadorNovedadDTO.setDireccionFisicaOficinaPrincipal(empleadorModeloDTO.getUbicacionModeloDTO().getDireccionFisica());
                datosEmpleadorNovedadDTO.setTelefonoFijoOficinaPrincipal(empleadorModeloDTO.getTelefono1OficinaPrincipal());
                datosEmpleadorNovedadDTO.setEmail(empleadorModeloDTO.getEmail1OficinaPrincipal());
                datosEmpleadorNovedadDTO.setTelefonoCelularOficinaPrincipal(empleadorModeloDTO.getCelularOficinaPrincipal());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL)) {
                datosEmpleadorNovedadDTO.setIdUbicacion(empleadorModeloDTO.getIdUbicacionEnvioDeCorrespondencia());
                datosEmpleadorNovedadDTO.setEmail(empleadorModeloDTO.getEmail2EnvioDeCorrespondencia());
                datosEmpleadorNovedadDTO.setTelefonoFijo(empleadorModeloDTO.getTelefono2EnvioDeCorrespondencia());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL)) {
                datosEmpleadorNovedadDTO.setIdUbicacionJudicial(empleadorModeloDTO.getIdUbicacionNotificacionJudicial());
                datosEmpleadorNovedadDTO.setEmail(empleadorModeloDTO.getEmail3NotificacionJudicial());
                datosEmpleadorNovedadDTO.setTelefonoFijoJudicial(empleadorModeloDTO.getTelefono3NotificacionJudicial());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL)) {
                datosEmpleadorNovedadDTO.setTipoIdentificacionRepLegal(representanteLegalModeloDTO.getTipoIdentificacion());
                datosEmpleadorNovedadDTO.setNumeroIdentificacionRepLegal(representanteLegalModeloDTO.getNumeroIdentificacion());
                datosEmpleadorNovedadDTO.setPrimerNombreRepLegal(representanteLegalModeloDTO.getPrimerNombre());
                datosEmpleadorNovedadDTO.setSegundoNombreRepLegal(representanteLegalModeloDTO.getSegundoNombre());
                datosEmpleadorNovedadDTO.setPrimerApellidoRepLegal(representanteLegalModeloDTO.getPrimerApellido());
                datosEmpleadorNovedadDTO.setSegundoApellidoRepLegal(representanteLegalModeloDTO.getSegundoApellido());
                datosEmpleadorNovedadDTO.setEmailRepLegal(representanteLegalModeloDTO.getUbicacionModeloDTO().getEmail());
                datosEmpleadorNovedadDTO.setTelefonoFijoRepLegal(representanteLegalModeloDTO.getUbicacionModeloDTO().getTelefonoFijo());
                datosEmpleadorNovedadDTO.setTelefonoCelularRepLegal(representanteLegalModeloDTO.getUbicacionModeloDTO().getTelefonoCelular());
            }  else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL)) {
                datosEmpleadorNovedadDTO.setIdEmpleador(empleadorModeloDTO.getIdEmpleador());
                datosEmpleadorNovedadDTO.setCodigoCIIU(empleadorModeloDTO.getCodigoCIIU());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_RESPONSABLE_CONTACTOS_CFF)) {
                datosEmpleadorNovedadDTO.setIdEmpleador(empleadorModeloDTO.getIdEmpleador());
                datosEmpleadorNovedadDTO.setResponsable1CajaContacto(empleadorModeloDTO.getResponsable1DeLaCajaParaContacto());
                datosEmpleadorNovedadDTO.setResponsable2CajaContacto(empleadorModeloDTO.getResponsable2DeLaCajaParaContacto());
            }
            solicitudNovedadDTO.setDatosEmpleador(datosEmpleadorNovedadDTO);

        } else if (tipoArchivo.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {

            //obtenemos la informarción procesada en el metodo validarArchivoRespuestaAfiliado
            AfiliadoModeloDTO afiliadoModeloDTO = informacionActualizacionNovedadDTO.getAfiliado();
            DatosPersonaNovedadDTO datosPersonaNovedadDTO = new DatosPersonaNovedadDTO();
            datosPersonaNovedadDTO.setTipoIdentificacion(afiliadoModeloDTO.getTipoIdentificacion());
            datosPersonaNovedadDTO.setNumeroIdentificacion(afiliadoModeloDTO.getNumeroIdentificacion());
            //LA PRIMERA ES DE ENVÍO AL BACK (Especialista de novedades)
            if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL)) {
                MedioDePagoModeloDTO medioDePagoModeloDTO = afiliadoModeloDTO.getMedioDePagoModeloDTO();
                if(medioDePagoModeloDTO.getBancoModeloDTO() != null && medioDePagoModeloDTO.getBancoModeloDTO().getId() != null){
                    medioDePagoModeloDTO.setIdBanco(medioDePagoModeloDTO.getBancoModeloDTO().getId());
                }
                datosPersonaNovedadDTO.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS)) {
                datosPersonaNovedadDTO.setPrimerApellidoTrabajador(afiliadoModeloDTO.getPrimerApellido());
                datosPersonaNovedadDTO.setSegundoApellidoTrabajador(afiliadoModeloDTO.getSegundoApellido());
                datosPersonaNovedadDTO.setPrimerNombreTrabajador(afiliadoModeloDTO.getPrimerNombre());
                datosPersonaNovedadDTO.setSegundoNombreTrabajador(afiliadoModeloDTO.getSegundoNombre());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_GENERO_PERSONAS)) {
                datosPersonaNovedadDTO.setGeneroTrabajador(afiliadoModeloDTO.getGenero());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL)) {
                datosPersonaNovedadDTO.setFechaExpedicionDocumentoTrabajador(afiliadoModeloDTO.getFechaExpedicionDocumento());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL)) {
                datosPersonaNovedadDTO.setFechaNacimientoTrabajador(afiliadoModeloDTO.getFechaNacimiento());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIAR_DATOS_CARACTERIZACION_POBLACION)) {
                datosPersonaNovedadDTO.setPertenenciaEtnica(afiliadoModeloDTO.getPertenenciaEtnica());
                datosPersonaNovedadDTO.setFactorVulnerabilidad(afiliadoModeloDTO.getFactorVulnerabilidad());
                datosPersonaNovedadDTO.setOrientacionSexual(afiliadoModeloDTO.getOrientacionSexual());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS)) {
                datosPersonaNovedadDTO.setEstadoCivilTrabajador(afiliadoModeloDTO.getEstadoCivil());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS)) {
                datosPersonaNovedadDTO.setPaisResidencia(afiliadoModeloDTO.getPais().getIdPais());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS)) {
                datosPersonaNovedadDTO.setTelefonoFijoTrabajador(afiliadoModeloDTO.getTelefono());
                datosPersonaNovedadDTO.setTelefonoCelularTrabajador(afiliadoModeloDTO.getCelular());
                datosPersonaNovedadDTO.setCorreoElectronicoTrabajador(afiliadoModeloDTO.getEmail());
            } else if (tipoTransaccion.equals(TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL)) {
                datosPersonaNovedadDTO.setIdRolAfiliado(afiliadoModeloDTO.getIdRolAfiliado());
                datosPersonaNovedadDTO.setValorSalarioMensualTrabajador(new BigDecimal(afiliadoModeloDTO.getSalarioAfiliado()));
            }
            solicitudNovedadDTO.setDatosPersona(datosPersonaNovedadDTO);

        } else if (tipoArchivo.equals(TipoArchivoRespuestaEnum.BENEFICIARIO)) {
            BeneficiarioModeloDTO beneficiarioModeloDTO = informacionActualizacionNovedadDTO.getBeneficiario();

            DatosPersonaNovedadDTO datosPersonaNovedadDTO = new DatosPersonaNovedadDTO();
            datosPersonaNovedadDTO
                    .setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
            datosPersonaNovedadDTO.setNumeroIdentificacion(
                    informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
            datosPersonaNovedadDTO.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
            datosPersonaNovedadDTO.setNumeroIdentificacionBeneficiario(beneficiarioModeloDTO.getNumeroIdentificacion());
            datosPersonaNovedadDTO.setTipoIdentificacionBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion());

            if (tipoTransaccion.equals(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS)) {
                datosPersonaNovedadDTO.setPrimerApellidoBeneficiario(beneficiarioModeloDTO.getPrimerApellido());
                datosPersonaNovedadDTO.setSegundoApellidoBeneficiario(beneficiarioModeloDTO.getSegundoApellido());
                datosPersonaNovedadDTO.setPrimerNombreBeneficiario(beneficiarioModeloDTO.getPrimerNombre());
                datosPersonaNovedadDTO.setSegundoNombreBeneficiario(beneficiarioModeloDTO.getSegundoNombre());
            } else if (tipoTransaccion
                    .equals(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS)) {
                datosPersonaNovedadDTO.setNivelEducativoConyuge(
                        informacionActualizacionNovedadDTO.getBeneficiario().getNivelEducativo());
            }

            solicitudNovedadDTO.setDatosPersona(datosPersonaNovedadDTO);
        }
        // Se agrega la informacion de la diferencia para mostrarla en pantalla
        solicitudNovedadDTO.setInfoNovedadArchivoActualizacion(informacionActualizacionNovedadDTO);
        return solicitudNovedadDTO;
    }

    /**
     * Inicia el proceso BPM para la tarea del especialista de novedades HU 244
     *
     * @param idSolicitud    Identificador solicutd
     * @param usuarioDestino Usuario a quien se le asocia la tarea
     * @param procesoEnum    Tipo de proceso de la tarea
     * @return Identificador instancia de proceso
     */
    private Long iniciarProcesoNovedadCargueActualizacion(Long idSolicitud, String usuarioDestino,
                                                          ProcesoEnum procesoEnum) {

        Long idInstanciaProcesoNovedad = new Long(0);
        Map<String, Object> parametrosProceso = new HashMap<String, Object>();
        SolicitudNovedadModeloDTO solicitudNovedad = consultarSolicitudNovedad(idSolicitud);

        parametrosProceso.put("idSolicitud", idSolicitud);
        // Se consulta el usuario seleccionado
        UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(usuarioDestino);
        String sedeDestinatario = usuarioDTO.getCodigoSede();
        parametrosProceso.put("usuarioEspecialista", usuarioDTO.getNombreUsuario());
        parametrosProceso.put("numeroRadicado", solicitudNovedad.getNumeroRadicacion());
        // se actualiza el estado, destinatario y sede de la solicitud
        solicitudNovedad.setDestinatario(usuarioDestino);
        solicitudNovedad.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
        solicitudNovedad.setEstadoSolicitud(EstadoSolicitudNovedadEnum.ASIGNADA_AL_BACK);

        logger.info("GLPI45051 --> iniciarProcesoNovedadCargueActualizacion -----> idSolicitud="+idSolicitud);

        IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesoNovedadService.execute();
        idInstanciaProcesoNovedad = iniciarProcesoNovedadService.getResult();

        // se actualiza el id instancia proceso
        solicitudNovedad.setIdInstanciaProceso(idInstanciaProcesoNovedad.toString());
        // se invoca el servicio que actualiza la solicitud de novedad
        actualizarSolicitudNovedad(solicitudNovedad);

        return idInstanciaProcesoNovedad;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     *      com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoCertificadoEscolar(com.asopagos.dto.CargueArchivoActualizacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoCertificadoEscolar(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.debug("Inicio validarArchivoCertificadoEscolar(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO)
                    .toString();
            // Se obtiene la informacion del archivo cargado
            InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());

            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(archivo.getFileName());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_CERTIFICADOS_ESCOLARES);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            registrarConsolaEstado(consolaEstadoCargue);

            // Se verifica la estructura y se obtiene las lineas para procesarlas
            // linea de error
            // System.out.println("**__**validarArchivoCertificadoEscolar : "+archivo);
            logger.info("**__**cargue.getCodigoIdentificacionECM() : " + cargue.getCodigoIdentificacionECM());
            logger.info("archivo::" + archivo.toString());
            logger.info("getDataFile::" + archivo.getDataFile());
            String str = new String(archivo.getDataFile(), java.nio.charset.StandardCharsets.UTF_8);
            logger.info("str::" + str);
            VerificarEstructuraArchivoCertificadoEscolar verificarArchivo = new VerificarEstructuraArchivoCertificadoEscolar(
                    archivo);
            verificarArchivo.execute();
            ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
            logger.info("ResultadoValidacionArchivoDTO " + resultDTO.toString());
            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null
                    || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                // Se gestiona la información del archivo
                gestionarArchivoCertificadoEscolar(resultDTO.getListActualizacionInfoNovedad(), userDTO);

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }

            // Se actualiza el cargue
            cargue.setEstado(estadoCargue);
            cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
            crearActualizarCargueArchivoActualizacion(cargue);

            // Se actualiza el estado en la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_CERTIFICADOS_ESCOLARES);
            actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

            logger.debug("Fin validarArchivoCertificadoEscolar(CargueArchivoActualizacionDTO, UserDTO)");
        } catch (Exception e) {
            // No se propaga la excepción por que el llamado es asincrono
            logger.error("Error validarArchivoCertificadoEscolar(CargueArchivoActualizacionDTO, UserDTO)", e);
        }
    }

    /**
     * Gestionar los registros del archivo de certificados escolares
     *
     * @param listInformacionArchivo Lista informacion del archivo
     * @param userDTO                Usuario autenticado
     * @throws Exception Lanzada si ocurre un error en el registro de solicitud
     */
    private void gestionarArchivoCertificadoEscolar(List<InformacionActualizacionNovedadDTO> listInformacionArchivo,
                                                    UserDTO userDTO)
            throws Exception {
        logger.debug("Inicio gestionarArchivoCertificadoEscolar");
        // Se obtienen los numeros de radicados
        NumeroRadicadoCorrespondenciaDTO numeroRadicadoCorrespondenciaDTO = obtenerNumeroRadicado(
                listInformacionArchivo.size());
        if (numeroRadicadoCorrespondenciaDTO == null) {
            // Si no se obtuvieron radicados no se procesan las novedades
            return;
        }
        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        Date fechaActual = Calendar.getInstance().getTime();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            String numeroRadicado = numeroRadicadoCorrespondenciaDTO.nextValue();
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                ProcesarNovedadCargueArchivoDTO procesarNovedadDTO = new ProcesarNovedadCargueArchivoDTO(fechaActual,
                        informacionActualizacionNovedadDTO, numeroRadicado);
                RegistrarNovedadCertificadoBeneficiario registrarNovedadSrv = new RegistrarNovedadCertificadoBeneficiario(
                        procesarNovedadDTO);
                registrarNovedadSrv.execute();
                return registrarNovedadSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            managedExecutorService.invokeAll(tareasParalelas);
        }
        logger.debug("Fin gestionarArchivoCertificadoEscolar");
    }

    /**
     * Realiza el procesamiento de novedad de certificado escolar generada por
     * cargue de archivo
     *
     * @param fechaActual                        Indica la fecha actual para
     *                                           verificación de datos
     * @param informacionActualizacionNovedadDTO Indica la información del
     *                                           beneficiario a ejecutarle la
     *                                           novedad
     * @param userDTO                            Usuario del contexto
     * @return Solicitud novedad registrada
     */
    private SolicitudNovedadDTO procesarNovedadCertificadoBeneficiario(Date fechaActual,
                                                                       InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO, UserDTO userDTO,
                                                                       String numeroRadicado) {
        // Datos del cargue
        BeneficiarioModeloDTO beneficiarioCargue = informacionActualizacionNovedadDTO.getBeneficiario();

        logger.info("EDWIN TORO cuarto CONSOLE LOG : " + beneficiarioCargue.getNumeroIdentificacion());
        // Datos de la BD
        ConsultarBeneficiarioByTipo consultarBeneficiario = new ConsultarBeneficiarioByTipo(new ArrayList<>(), null,
                null, null, null, null,
                beneficiarioCargue.getNumeroIdentificacion(), beneficiarioCargue.getTipoIdentificacion(), null, null,
                Boolean.TRUE);
        consultarBeneficiario.execute();
        List<BeneficiarioModeloDTO> listBeneficiarios = consultarBeneficiario.getResult();

        logger.info("EDWIN TORO Quinto CONSOLE LOG : " + listBeneficiarios);
        BeneficiarioModeloDTO beneficiarioModeloDTO = null;
        if (listBeneficiarios != null && !listBeneficiarios.isEmpty()) {
            beneficiarioModeloDTO = listBeneficiarios.iterator().next();
        }
        if (beneficiarioModeloDTO != null) {
            // Se asigna el identificador del beneficiario encontrado
            beneficiarioCargue.setIdAfiliado(beneficiarioModeloDTO.getIdAfiliado());
            beneficiarioCargue.setIdPersona(beneficiarioModeloDTO.getIdPersona());
            beneficiarioCargue.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());

            boolean registrarNovedad = true;
            // Se verifica que el rango de fechas no es consistente (fecha fin de vigencia
            // es menor a la fecha de inicio de vigencia)
            if (beneficiarioCargue.getFechaRecepcionCertificadoEscolar() > beneficiarioCargue
                    .getFechaVencimientoCertificadoEscolar()) {
                registrarNovedad = false;
            }
            // Se verifica si el rango de fechas ya fue registrado en el sistema previamente
            if (beneficiarioModeloDTO.getFechaRecepcionCertificadoEscolar() != null
                    && beneficiarioModeloDTO.getFechaVencimientoCertificadoEscolar() != null
                    && beneficiarioModeloDTO.getFechaRecepcionCertificadoEscolar()
                    .equals(beneficiarioCargue.getFechaRecepcionCertificadoEscolar())
                    && beneficiarioModeloDTO.getFechaVencimientoCertificadoEscolar()
                    .equals(beneficiarioCargue.getFechaVencimientoCertificadoEscolar())) {
                registrarNovedad = false;
            }
            // Se valida si la fecha de fin de vigencia es anterior o es la misma fecha del
            // sistema
            if (fechaActual.getTime() >= beneficiarioCargue.getFechaVencimientoCertificadoEscolar()) {
                registrarNovedad = false;
            }
            // corrección Mantis 257524 - se valida si el beneficiario tiene registrado un
            // certificado
            // con mayor vigencia que el enviado en el archivo
            if (beneficiarioModeloDTO.getFechaVencimientoCertificadoEscolar() != null
                    && beneficiarioCargue.getFechaVencimientoCertificadoEscolar() != null
                    && beneficiarioModeloDTO.getFechaVencimientoCertificadoEscolar() >= beneficiarioCargue
                    .getFechaVencimientoCertificadoEscolar()) {
                registrarNovedad = false;
            }

            if (registrarNovedad) {
                Integer edadBeneficiario = CalendarUtils
                        .calcularEdadAnos(new Date(beneficiarioModeloDTO.getFechaNacimiento()));

                TipoTransaccionEnum tipoTransaccion = obtenerTipoTransaccionClasificacionBeneficiario(
                        beneficiarioModeloDTO.getTipoBeneficiario(), false, true, edadBeneficiario);
                if (tipoTransaccion == null) {
                    return null;
                }
                ConsultarAfiliadoPorId consultarAfiliadoPorId = new ConsultarAfiliadoPorId(
                        beneficiarioModeloDTO.getIdAfiliado());
                consultarAfiliadoPorId.execute();
                informacionActualizacionNovedadDTO.setAfiliado(consultarAfiliadoPorId.getResult());
                informacionActualizacionNovedadDTO.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
                // Se crea la solicitud
                SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(TipoArchivoRespuestaEnum.BENEFICIARIO,
                        tipoTransaccion,
                        informacionActualizacionNovedadDTO, null, CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR, userDTO);
                solNovedadDTO.setNovedadAsincrona(Boolean.TRUE);
                solNovedadDTO.setNumeroRadicacion(numeroRadicado);
                // Se agrega la informacion de la novedad
                solNovedadDTO.getDatosPersona()
                        .setFechaReporteCertEscolarHijo(beneficiarioCargue.getFechaRecepcionCertificadoEscolar());
                solNovedadDTO.getDatosPersona()
                        .setFechaVencimientoCertEscolar(beneficiarioCargue.getFechaVencimientoCertificadoEscolar());
                solNovedadDTO.getDatosPersona().setCertificadoEscolarHijo(true);
                GradoAcademico grado = new GradoAcademico();
                grado.setIdgradoAcademico(beneficiarioCargue.getGradoAcademicoBeneficiario());
                solNovedadDTO.getDatosPersona().setGradoCursadoHijo(grado);
                solNovedadDTO.getDatosPersona().setNivelEducativoHijo(beneficiarioCargue.getNivelEducativo());
                // Se radica la solicitud
                return radicarSolicitudNovedad(solNovedadDTO, userDTO);
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     *      com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoPensionado(com.asopagos.dto.CargueArchivoActualizacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoPensionado(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.debug("Inicio validarArchivoPensionado(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO)
                    .toString();
            // Se obtiene la informacion del archivo cargado
            InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_PENSIONADOS_BENEFICIARIOS);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            registrarConsolaEstado(consolaEstadoCargue);

            // Se verifica la estructura y se obtiene las lineas para procesarlas
            VerificarEstructuraArchivoPensionado verificarArchivo = new VerificarEstructuraArchivoPensionado(archivo);
            verificarArchivo.execute();
            ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
            logger.info("ResultadoValidacionArchivoDTO" + resultDTO.toString());
            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null
                    || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                // Se gestiona la información del archivo
                gestionarArchivoPensionado(resultDTO.getListActualizacionInfoNovedad(), userDTO);

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }

            // Se actualiza el cargue
            cargue.setEstado(estadoCargue);
            cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
            crearActualizarCargueArchivoActualizacion(cargue);

            // Se actualiza la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_PENSIONADOS_BENEFICIARIOS);
            actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

            logger.debug("Fin validarArchivoPensionado(CargueArchivoActualizacionDTO, UserDTO)");
        } catch (Exception e) {
            // No se propaga la excepción porque el llamado es asincrono
            logger.debug("Error validarArchivoPensionado(CargueArchivoActualizacionDTO, UserDTO)", e);
        }
    }

    /**
     * Gestiona los archivos de beneficiario que pueden ser pensionado y
     * registra las novedades
     *
     * @param listInformacionArchivo Lista de informacion del archivo
     * @param userDTO                Usuario autenticado
     * @throws Exception Lanzada si ocurre error en el registro de solicitudes
     */
    private void gestionarArchivoPensionado(List<InformacionActualizacionNovedadDTO> listInformacionArchivo,
                                            UserDTO userDTO)
            throws Exception {
        logger.debug("Inicio gestionarArchivoPensionado");
        // Se obtienen los numeros de radicados
        NumeroRadicadoCorrespondenciaDTO numeroRadicadoCorrespondenciaDTO = obtenerNumeroRadicado(
                listInformacionArchivo.size());
        if (numeroRadicadoCorrespondenciaDTO == null) {
            // Si no se obtuvieron radicados no se procesan las novedades
            return;
        }
        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            String numeroRadicado = numeroRadicadoCorrespondenciaDTO.nextValue();
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                ProcesarNovedadCargueArchivoDTO procesarNovedadDTO = new ProcesarNovedadCargueArchivoDTO();
                procesarNovedadDTO.setInformacionActualizacionNovedadDTO(informacionActualizacionNovedadDTO);
                procesarNovedadDTO.setNumeroRadicado(numeroRadicado);
                RegistrarNovedadInactivarBeneficiario registrarSrv = new RegistrarNovedadInactivarBeneficiario(
                        procesarNovedadDTO);
                registrarSrv.execute();
                return registrarSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            managedExecutorService.invokeAll(tareasParalelas);
        }
        logger.debug("Fin gestionarArchivoPensionado");
    }

    /**
     * Realiza el procesamiento de la novedad de inactivación de beneficiario
     *
     * @param informacionActualizacionNovedadDTO Información del benefiario a
     *                                           inactivar
     * @param userDTO                            Usuario del contexto
     * @return Solicitud de novedad registrada
     */
    private SolicitudNovedadDTO procesarNovedadInactivarBeneficiario(
            InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO, String numeroRadicado) {
        // Datos del cargue
        BeneficiarioModeloDTO beneficiarioCargue = informacionActualizacionNovedadDTO.getBeneficiario();
        // Datos de la BD
        ConsultarBeneficiarioByTipo consultarBeneficiario = new ConsultarBeneficiarioByTipo(new ArrayList<>(), null,
                null, null, null, null,
                beneficiarioCargue.getNumeroIdentificacion(), beneficiarioCargue.getTipoIdentificacion(), null, null,
                Boolean.FALSE);
        consultarBeneficiario.execute();
        List<BeneficiarioModeloDTO> listBeneficiarios = consultarBeneficiario.getResult();
        BeneficiarioModeloDTO beneficiarioModeloDTO = null;
        if (listBeneficiarios != null && !listBeneficiarios.isEmpty()) {
            beneficiarioModeloDTO = listBeneficiarios.iterator().next();
        }
        if (beneficiarioModeloDTO != null) {
            beneficiarioCargue.setIdAfiliado(beneficiarioModeloDTO.getIdAfiliado());
            beneficiarioCargue.setIdPersona(beneficiarioModeloDTO.getIdPersona());
            beneficiarioCargue.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
            // Valida si la persona sujeto de análisis de cruce es beneficiario activo en la
            // CCF y en el cruce resulta como “Pensionado”
            // Registra la novedad
            if (informacionActualizacionNovedadDTO.isPensionado()
                    && beneficiarioModeloDTO.getEstadoBeneficiarioAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
                // Se registra la novedad
                TipoTransaccionEnum tipoTransaccion = obtenerTipoTransaccionClasificacionBeneficiario(
                        beneficiarioModeloDTO.getTipoBeneficiario(), true, false, null);
                if (tipoTransaccion == null) {
                    return null;
                }
                ConsultarAfiliadoPorId consultarAfiliadoPorId = new ConsultarAfiliadoPorId(
                        beneficiarioModeloDTO.getIdAfiliado());
                consultarAfiliadoPorId.execute();
                informacionActualizacionNovedadDTO.setAfiliado(consultarAfiliadoPorId.getResult());
                informacionActualizacionNovedadDTO.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
                // Se crea la solicitud
                SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(TipoArchivoRespuestaEnum.BENEFICIARIO,
                        tipoTransaccion,
                        informacionActualizacionNovedadDTO, null, CanalRecepcionEnum.ARCHIVO_ACTUALIZACION, userDTO);
                solNovedadDTO.setNovedadAsincrona(Boolean.TRUE);
                solNovedadDTO.setNumeroRadicacion(numeroRadicado);
                // Se agrega la informacion de la novedad
                solNovedadDTO.getDatosPersona()
                        .setMotivoDesafiliacionBeneficiario(
                                MotivoDesafiliacionBeneficiarioEnum.ENCONTRADO_PENSIONADO_CRUCE);
                solNovedadDTO.getDatosPersona().setFechaRetiro(Calendar.getInstance().getTimeInMillis());
                // Se radica la solicitud
                return radicarSolicitudNovedad(solNovedadDTO, userDTO);
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * aceptarResultadoArchivoActualizacion(java.lang.Long)
     */
    @Override
    public void aceptarResultadoArchivoActualizacion(Long codigoCargue) {
        try {
            ConsultarCargueArchivoActualizacion cargueArchivoActualizacion = new ConsultarCargueArchivoActualizacion(
                    codigoCargue);
            cargueArchivoActualizacion.execute();
            CargueArchivoActualizacionDTO dto = cargueArchivoActualizacion.getResult();
            // Se agrga la fecha de aceptacion
            dto.setFechaAceptacion(Calendar.getInstance().getTime());
            // Se actualiza el cargue
            crearActualizarCargueArchivoActualizacion(dto);

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * consultarSolicitudDiferencia(java.lang.Long)
     */
    @Override
    public SolicitudNovedadDTO consultarSolicitudDiferencia(Long idSolicitud) {
        try {
            // Se consulta la solicitud de novedad
            ConsultarSolicitudNovedad consultarSolicitudNovedad = new ConsultarSolicitudNovedad(idSolicitud);
            consultarSolicitudNovedad.execute();
            SolicitudNovedadModeloDTO novedadDTO = consultarSolicitudNovedad.getResult();

            // Se consulta la diferencia asociada
            ConsultarDiferenciaCargueArchivoActualizacion diferenciaCargueArchivoActualizacion = new ConsultarDiferenciaCargueArchivoActualizacion(
                    novedadDTO.getIdDiferenciaCargueActualizacion());
            diferenciaCargueArchivoActualizacion.execute();
            DiferenciasCargueActualizacionDTO diferencia = diferenciaCargueArchivoActualizacion.getResult();
            ObjectMapper mapper = new ObjectMapper();
            SolicitudNovedadDTO solicitudNovedadDTO = mapper.readValue(diferencia.getJsonPayload(),
                    SolicitudNovedadDTO.class);
            solicitudNovedadDTO.setFechaRadicacion(novedadDTO.getFechaRadicacion());
            return solicitudNovedadDTO;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * finalizarNovedadArchivoActualizacion(java.lang.Long,
     * java.lang.Long, java.lang.String)
     */
    @Override
    public void finalizarNovedadArchivoActualizacion(Long idSolicitud, Long idTarea, String observaciones) {
        try {
            // Se actualiza el estado a APROBADA
            actualizarEstadoSolicitudNovedad(idSolicitud, EstadoSolicitudNovedadEnum.APROBADA);
            // Se consulta la solicitud novedad
            SolicitudNovedadModeloDTO solicitudNovedad = consultarSolicitudNovedad(idSolicitud);
            // Se ajusta las observaciones de la solicitud
            if(solicitudNovedad!=null){
                solicitudNovedad.setObservacion(observaciones);
                actualizarSolicitudNovedad(solicitudNovedad);
            }
            // Se cierra la solicitud
            actualizarEstadoSolicitudNovedad(idSolicitud, EstadoSolicitudNovedadEnum.CERRADA);
            logger.info("GLPI45051-->Terminar tarea finalizarNovedadArchivoActualizacion");
            // Termina la tarea
            terminarTarea(idTarea, null);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void radicarSolicitudNovedadFutura(UserDTO userDTO) {
        try {
            // Consulta las novedades que apliquen para el proceso de registro
            ConsultarNovedadesFuturas consultarNovedadesFuturasService = new ConsultarNovedadesFuturas();
            consultarNovedadesFuturasService.execute();
            List<RegistroNovedadFuturaModeloDTO> listFuturas = consultarNovedadesFuturasService.getResult();
            // Si no existe registros se termina la ejecución
            if (listFuturas == null) {
                return;
            }
            // Se genera la radicación de la novedad para los registros encontrados
            for (RegistroNovedadFuturaModeloDTO registroNovedadFuturaModeloDTO : listFuturas) {
                radicarSolicitudNovedadAportes(construirInformacionNovedadRegistro(registroNovedadFuturaModeloDTO),
                        userDTO);
                // Se indica que el registro se proceso
                registroNovedadFuturaModeloDTO.setRegistroProcesado(Boolean.TRUE);
            }
            // Se actualiza a procesado las novedad futuras que se aplico el proceso de
            // registro
            ActualizarRegistroNovedadFuturaMasivo actualizarRegistroNovedadFuturaMasivoService = new ActualizarRegistroNovedadFuturaMasivo(
                    listFuturas);
            actualizarRegistroNovedadFuturaMasivoService.execute();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Se construye la novedad de aportes para el registro apartir de la
     * información de la novedad futura
     *
     * @param registroNovedadFuturaModeloDTO Información novedad futura a
     *                                       registrar
     * @return Novedad de aporte
     */
    private NovedadAportesDTO construirInformacionNovedadRegistro(
            RegistroNovedadFuturaModeloDTO registroNovedadFuturaModeloDTO) {
        NovedadAportesDTO result = new NovedadAportesDTO();
        // Se indica que la novedad se debe aplicar
        result.setAplicar(MarcaNovedadEnum.APLICADA);
        // Se recuperan los datos de la novedad a registrar
        result.setCanalRecepcion(registroNovedadFuturaModeloDTO.getCanalRecepcion());
        result.setClasificacionAfiliado(registroNovedadFuturaModeloDTO.getClasificacion());
        result.setComentarios(registroNovedadFuturaModeloDTO.getComentario());
        if (registroNovedadFuturaModeloDTO.getFechaFin() != null) {
            result.setFechaFin(registroNovedadFuturaModeloDTO.getFechaFin().getTime());
        }
        result.setFechaInicio(registroNovedadFuturaModeloDTO.getFechaInicio().getTime());
        result.setIdRegistroDetallado(registroNovedadFuturaModeloDTO.getIdRegistroDetallado());
        result.setTipoNovedad(registroNovedadFuturaModeloDTO.getTipoTransaccion());
        // Se adiciona la persona
        if (registroNovedadFuturaModeloDTO.getPersonaModeloDTO() != null) {
            result.setTipoIdentificacion(registroNovedadFuturaModeloDTO.getPersonaModeloDTO().getTipoIdentificacion());
            result.setNumeroIdentificacion(
                    registroNovedadFuturaModeloDTO.getPersonaModeloDTO().getNumeroIdentificacion());
        }
        // Se adiciona el empleador
        if (registroNovedadFuturaModeloDTO.getEmpleadorModeloDTO() != null) {
            result.setTipoIdentificacionAportante(
                    registroNovedadFuturaModeloDTO.getEmpleadorModeloDTO().getTipoIdentificacion());
            result.setNumeroIdentificacionAportante(
                    registroNovedadFuturaModeloDTO.getEmpleadorModeloDTO().getNumeroIdentificacion());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * registrarTareaFOVISAnalisisNovedad(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarPersonaNovedadRegistrarAnalisisFovis(Long idSolicitudNovedad,
                                                              List<PersonaDTO> listPersonasVerificar,
                                                              UserDTO userDTO) {
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.verificarPersonaNovedadRegistrarAnalisisFovis(idSolicitudNovedad, listPersonasVerificar, userDTO);
    }

    /**
     * Construye la solicitud de analisis fovis de novedad de personaa
     *
     * @param personaPostulacionDTO Informacion persona postulacion
     * @param idSolicitudNovedad    Identificador solicitud novedad
     * @param userDTO               Usuario autenticado
     * @return DTO informacion registro solicitud novedad
     */
    private SolicitudAnalisisNovedadFOVISModeloDTO construirSolicitudAnalisisFOVISNovedad(
            PersonaPostulacionDTO personaPostulacionDTO,
            Long idSolicitudNovedad, UserDTO userDTO) {
        SolicitudAnalisisNovedadFOVISModeloDTO solicitud = new SolicitudAnalisisNovedadFOVISModeloDTO();
        // Informacion general solicitud
        solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitud.setCiudadSedeRadicacion(userDTO.getSedeCajaCompensacion());
        solicitud.setTipoTransaccion(TipoTransaccionEnum.ANALISIS_NOVEDAD_PERSONA_ASOCIADA_FOVIS);
        solicitud.setFechaCreacion((new Date()).getTime());
        solicitud.setFechaRadicacion((new Date()).getTime());
        // Informacion solicitud analisis
        solicitud.setEstadoSolicitud(EstadoSolicitudAnalisisNovedadFovisEnum.RADICADA);
        solicitud.setIdPostulacionFOVIS(personaPostulacionDTO.getIdPostulacionFovis());
        solicitud.setIdPersonaNovedad(personaPostulacionDTO.getIdPersonaNovedad());
        solicitud.setIdSolicitudNovedad(idSolicitudNovedad);
        return solicitud;
    }

    /**
     * Realiza la radicacion de las solicitudes de analsis de novedad de persona
     * que afecta FOVIS
     *
     * @param listSolAnaModelDTO Lista solicitudes
     * @param userDTO            Informacion usuario loqueado
     * @return Lista que contiene las solicitudes creadas
     */
    private List<SolicitudAnalisisNovedadFOVISModeloDTO> radicarSolicitudAnalisisNovedad(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaModelDTO, UserDTO userDTO) {
        // Se ejecuta el servicion de registro de solicitud analisis
        List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaRegis = crearSolicitudAnalisisNovedadFOVIS(
                listSolAnaModelDTO);
        if (listSolAnaRegis != null && !listSolAnaRegis.isEmpty()) {
            // Se crea la lista de id Solicitud para la asociacion del numero radicado
            List<Long> listIdSolicitud = new ArrayList<>();
            for (SolicitudAnalisisNovedadFOVISModeloDTO dto : listSolAnaRegis) {
                listIdSolicitud.add(dto.getIdSolicitud());
            }
            // Se obtiene el numero de radicacion de cada solicitud
            Map<String, String> mapNumRadicadoSol = radicarListaSolicitudes(listIdSolicitud,
                    userDTO.getSedeCajaCompensacion());
            // Se agrega el numero de radicacion
            String idSol;
            for (SolicitudAnalisisNovedadFOVISModeloDTO analisisNovedadFOVISModeloDTO : listSolAnaRegis) {
                idSol = analisisNovedadFOVISModeloDTO.getIdSolicitud().toString();
                if (mapNumRadicadoSol.containsKey(idSol)) {
                    analisisNovedadFOVISModeloDTO.setNumeroRadicacion(mapNumRadicadoSol.get(idSol));
                }
            }
            return listSolAnaRegis;
        }
        return null;
    }

    /**
     * Realiza el llamado al servicio que crea la solicitud de analisis
     *
     * @param listSolAnaModelDTO Lista de solicitudes a crear
     * @return Lista de solicitudes creadas
     */
    private List<SolicitudAnalisisNovedadFOVISModeloDTO> crearSolicitudAnalisisNovedadFOVIS(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaModelDTO) {
        CrearActualizarListaSolicitudAnalisisNovedadFOVIS crearSolicitudAnalisis = new CrearActualizarListaSolicitudAnalisisNovedadFOVIS(
                listSolAnaModelDTO);
        crearSolicitudAnalisis.execute();
        return crearSolicitudAnalisis.getResult();
    }

    /**
     * Realiza el llamado al servicio que genera el radicado de solicitud para
     * una lista de solicitudes
     *
     * @param sede            Sede de radicacion
     * @param listIdSolicitud Lista de solicitudes radicadas
     * @return Map con la informacion del radicado de cada solicitud
     */
    private Map<String, String> radicarListaSolicitudes(List<Long> listIdSolicitud, String sede) {
        // RadicarListaSolicitudes radicarListaSolicitudes = new
        // RadicarListaSolicitudes(sede, listIdSolicitud);
        // radicarListaSolicitudes.execute();
        // return radicarListaSolicitudes.getResult();
        return null;// Hice esto porque no deja compilar, mcuellar.
    }

    /**
     * Inicia el proceso BPM para la HU 325-77
     *
     * @param solicitudAnalisisNovedadFOVISModeloDTO Informacion solicitud
     *                                               analisis novedad
     */
    private void iniciarProcesoAnalisisNovedad(
            SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO, UserDTO user) {
        try {
            // Mapa de parametros del proceso BPM
            Map<String, Object> parametrosProceso = new HashMap<String, Object>();
            parametrosProceso.put("idSolicitud", solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud());
            parametrosProceso.put("numeroRadicado", solicitudAnalisisNovedadFOVISModeloDTO.getNumeroRadicacion());
            // Se asigna automaticamente la tarea
            ProcesoEnum procesoEnum = solicitudAnalisisNovedadFOVISModeloDTO.getTipoTransaccion().getProceso();
            String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(
                    new Long(user.getSedeCajaCompensacion()), procesoEnum);
            UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            String sedeDestinatario = usuarioDTO.getCodigoSede();
            parametrosProceso.put("usuarioBack", usuarioDTO.getNombreUsuario());
            // se actualiza el estado, destinatario y sede de la solicitud*/
            solicitudAnalisisNovedadFOVISModeloDTO.setDestinatario(destinatario);
            solicitudAnalisisNovedadFOVISModeloDTO
                    .setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
            solicitudAnalisisNovedadFOVISModeloDTO
                    .setEstadoSolicitud(EstadoSolicitudAnalisisNovedadFovisEnum.PENDIENTE);

            IniciarProceso iniciarProcesoService = new IniciarProceso(procesoEnum, parametrosProceso);
            iniciarProcesoService.execute();
            Long idInstanciaProceso = iniciarProcesoService.getResult();
            solicitudAnalisisNovedadFOVISModeloDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
        } catch (Exception e) {
            logger.error(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método encargado de llamar el servicio que consulta la última
     * clasificación de la persona
     *
     * @param numeroIdentificacion,número de identificación de la persona
     * @param tipoIdentificacion,         tipo de identificación de la persona
     * @return retorna la clasificacion de la persona
     */
    /*
     * private ClasificacionEnum consultarUltimaClasificacionPersona(String
     * numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
     * ConsultarUltimaClasificacionPersona clasificacion = new
     * ConsultarUltimaClasificacionPersona(numeroIdentificacion,
     * tipoIdentificacion);
     * clasificacion.execute();
     * return clasificacion.getResult();
     * }
     */
    @Override
    public List<DatosNovedadEmpleadorDTO> consultarNovedadesEmpleador360(TipoIdentificacionEnum tipoIdentificacion,
                                                                         String numeroIdentificacion, UriInfo uri, HttpServletResponse response) {
        logger.debug("Inicia servicio consultarNovedadesEmpleador360(TipoIdentificacionEnum,String)");
        try {
            FiltrosDatosNovedadDTO filtrosParametrizacion = new FiltrosDatosNovedadDTO();
            if (uri != null && uri.getQueryParameters() != null) {
                for (Entry<String, List<String>> e : uri.getQueryParameters().entrySet()) {
                    filtrosParametrizacion.getParams().put(e.getKey(), e.getValue());
                    if (PaginationQueryParamsEnum.DRAW.getValor().equals(e.getKey())) {
                        response.addHeader(PaginationQueryParamsEnum.DRAW.getValor(), e.getValue().get(0).toString());
                    }
                }
            }
            filtrosParametrizacion.setTipoIdentificacion(tipoIdentificacion);
            filtrosParametrizacion.setNumeroIdentificacion(numeroIdentificacion);

            ConsultarNovedadesEmpVista360 consultarNovedadesEmpleador = new ConsultarNovedadesEmpVista360(
                    filtrosParametrizacion);
            consultarNovedadesEmpleador.execute();
            DatosNovedadEmpleadorPaginadoDTO novedadesEmpleador = consultarNovedadesEmpleador.getResult();

            if (!novedadesEmpleador.getDatosNovedadEmpleador().isEmpty()) {
                for (DatosNovedadEmpleadorDTO datosNovedadEmpleadorDTO : novedadesEmpleador
                        .getDatosNovedadEmpleador()) {
                    if (datosNovedadEmpleadorDTO.getFechaRadicacion() != null) {
                        // antes de mantis 256253
                        /*
                         * List<EstadoAfiliadoEnum> listaEstados =
                         * obtenerEstadosAportanteFecha(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR,
                         * tipoIdentificacion, numeroIdentificacion,
                         * datosNovedadEmpleadorDTO.getFechaRadicacion());
                         * datosNovedadEmpleadorDTO.setEstadoEmpleadorAntes(listaEstados.get(0)!=null?
                         * EstadoEmpleadorEnum.valueOf(listaEstados.get(0).name()):null);
                         * datosNovedadEmpleadorDTO.setEstadoEmpleadorDespues(listaEstados.get(1)!=null?
                         * EstadoEmpleadorEnum.valueOf(listaEstados.get(1).name()):null);
                         */

                        // solucion mantis 256253
                        datosNovedadEmpleadorDTO
                                .setEstadoEmpleadorAntes(datosNovedadEmpleadorDTO.getEstadoEmpleadorAntes());
                        datosNovedadEmpleadorDTO
                                .setEstadoEmpleadorDespues(datosNovedadEmpleadorDTO.getEstadoEmpleadorDespues());

                    }
                }
            }
            if (novedadesEmpleador.getTotalRecords() != null && response != null) {
                response.addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(),
                        novedadesEmpleador.getTotalRecords().toString());
            }

            logger.debug("Finaliza servicio consultarNovedadesEmpleador360(TipoIdentificacionEnum,String)");
            return novedadesEmpleador.getDatosNovedadEmpleador();
        } catch (Exception e) {
            logger.error("Error inesperado en consultarNovedadesEmpleador360(TipoIdentificacionEnum,String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * obtenerNovedadesPersonaVista360(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String, java.lang.Boolean)
     */
    @Override
    public DatosNovedadVista360DTO obtenerNovedadesPersonaVista360(TipoIdentificacionEnum tipoIdentificacion,
                                                                   String numeroIdentificacion, Boolean esBeneficiario) {
        try {
            logger.debug("Inicia servicio obtenerNovedadesPersonaVista360");
            DatosNovedadVista360DTO datosNovedadDTO = consultarNovedadesPersonaVista360(tipoIdentificacion,
                    numeroIdentificacion, esBeneficiario);

            if (datosNovedadDTO.getNovedadesRegistradas() != null) {
                for (DatosNovedadRegistradaPersonaDTO datosNovedadPersonaDTO : datosNovedadDTO
                        .getNovedadesRegistradas()) {
                    if (datosNovedadPersonaDTO.getFechaInicioVigencia() != null) {
                        List<EstadoAfiliadoEnum> listaEstados = obtenerEstadosAportanteFecha(null, tipoIdentificacion,
                                numeroIdentificacion, datosNovedadPersonaDTO.getFechaInicioVigencia());
                        datosNovedadPersonaDTO.setEstadoPersonaAntes(listaEstados.get(0));
                        datosNovedadPersonaDTO.setEstadoPersonaDespues(listaEstados.get(1));
                    }
                }
            }

            logger.debug("Finaliza servicio obtenerNovedadesPersonaVista360");
            return datosNovedadDTO;
        } catch (Exception e) {
            logger.error("Error inesperado en obtenerNovedadesPersonaVista360", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que invoca el servicio que consulta las novedades de una persona,
     * para la vista 360
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param esBeneficiario       Indica si es beneficiario
     * @return La lista de novedades
     */
    private DatosNovedadVista360DTO consultarNovedadesPersonaVista360(TipoIdentificacionEnum tipoIdentificacion,
                                                                      String numeroIdentificacion, Boolean esBeneficiario) {
        logger.debug("Inicia servicio consultarNovedadesPersonaVista360");
        ConsultarNovedadesPersonaVista360 service = new ConsultarNovedadesPersonaVista360(numeroIdentificacion,
                tipoIdentificacion, esBeneficiario);
        service.execute();
        logger.debug("Finaliza servicio consultarNovedadesPersonaVista360");
        return service.getResult();
    }

    /**
     * Método que obtiene los estados (antes y después de una fecha dada) de un
     * aportante respecto a la caja
     *
     * @param tipoAportante        Tipo de aportante
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param fechaMillis          Fecha a evaluar
     * @return Los estados del aportante, en el siguiente orden: {estadoAntes,
     *         estadoDespues}
     */
    private List<EstadoAfiliadoEnum> obtenerEstadosAportanteFecha(TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                                                  TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long fechaMillis) {
        logger.debug("Inicia método obtenerEstadosAportanteFecha");
        Calendar calendar = Calendar.getInstance();
        Date fecha = new Date(fechaMillis);
        calendar.setTime(fecha);

        calendar.add(Calendar.MINUTE, -5); // Se resta 1 día a la fecha, para consultar el estado antes de aplicar la
        // novedad
        String estadoAntes = consultarEstadoAportanteFecha(tipoIdentificacion, numeroIdentificacion, tipoAportante,
                calendar.getTimeInMillis());

        calendar.add(Calendar.MINUTE, 10); // Se agrega 1 día a la fecha, para consultar el estado después de aplicada
        // la novedad
        String estadoDespues = consultarEstadoAportanteFecha(tipoIdentificacion, numeroIdentificacion, tipoAportante,
                calendar.getTimeInMillis());

        List<EstadoAfiliadoEnum> lista = new ArrayList<EstadoAfiliadoEnum>();
        lista.add(estadoAntes == null || estadoAntes.equals(SIN_INFORMACION) ? null
                : EstadoAfiliadoEnum.valueOf(estadoAntes));
        lista.add(estadoDespues == null || estadoDespues.equals(SIN_INFORMACION) ? null
                : EstadoAfiliadoEnum.valueOf(estadoDespues));
        logger.debug("Finaliza método obtenerEstadosAportanteFecha");
        return lista;
    }

    /**
     * Método que invoca el servicio que consulta el estado respecto a la CCF
     * que tenía un aportante en una fecha dada
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoAportante        Tipo de aportante
     * @param fecha                Fecha a evaluar
     * @return El estado del aportante. Se generaliza tipo de retorno
     *         <code>String</code> para mapear la enumeración correspondiente,
     *         dependiendo del tipo de aportante: <code>EstadoAfiliadoEnum</code> ó
     *         <code>EstadoEmpleadorEnum</code>
     */
    private String consultarEstadoAportanteFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                 TipoSolicitanteMovimientoAporteEnum tipoAportante, Long fecha) {
        logger.debug("Inicia servicio consultarEstadoAportanteFecha");
        ConsultarEstadoAportanteFecha service = new ConsultarEstadoAportanteFecha(tipoAportante, numeroIdentificacion,
                tipoIdentificacion, fecha);
        service.execute();
        logger.debug("Finaliza servicio consultarEstadoAportanteFecha");
        return service.getResult();
    }

    @Asynchronous
    @Override
    public void registrarRetiroAutomaticoPorFallecimiento(SolicitudNovedadDTO solicitudNovedad, UserDTO userDTO) {
        logger.info("Inicia registrarRetiroAutomaticoPorFallecimiento(SolicitudNovedadDTO, UserDTO)");
        try {
            if (solicitudNovedad == null || solicitudNovedad.getDatosPersona() == null) {
                return;
            }
            // Se registra con el canal INTERNO
            solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
            solicitudNovedad.setMetodoEnvio(null);
            solicitudNovedad.setObservaciones(null);

            TipoIdentificacionEnum tipoIdentificacion = null;
            String numeroIdentificacion = null;
            if (solicitudNovedad.getDatosPersona().getIdBeneficiario() != null) {
                tipoIdentificacion = solicitudNovedad.getDatosPersona().getTipoIdentificacionBeneficiario();
                numeroIdentificacion = solicitudNovedad.getDatosPersona().getNumeroIdentificacionBeneficiario();
            } else {
                tipoIdentificacion = solicitudNovedad.getDatosPersona().getTipoIdentificacion();
                numeroIdentificacion = solicitudNovedad.getDatosPersona().getNumeroIdentificacion();
            }
            solicitudNovedad.getDatosPersona().setFechaRetiro(Calendar.getInstance().getTimeInMillis());
            logger.info("registrarRetiroAutomaticoPorFallecimiento(" + tipoIdentificacion + ", " + numeroIdentificacion
                    + ")");

            // Se consulta la informacion del afiliado por los datos de identificación
            ConsultarAfiliadoOutDTO afiliados = consultarAfiliados(tipoIdentificacion, numeroIdentificacion);

            // Se consulta la informacion de la persona como Beneficiario
            List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiarioTipoNroIdentificacion(
                    tipoIdentificacion,
                    numeroIdentificacion);

            // Se realiza el registro del retiro para cada registro de afiliación
            procesarRetiroAfiliado(afiliados, solicitudNovedad, userDTO);

            // Se realiza el registro de la novedad de inactivación sin la ejecución de
            // validaciones para los beneficiarios
            procesarInactivacionBeneficiario(listaBeneficiarios, solicitudNovedad, userDTO);
            logger.debug("Finaliza registrarRetiroAutomaticoPorFallecimiento(SolicitudNovedadDTO, UserDTO)");
        } catch (Exception e) {
            // No se lanza excepción por que es un llamado asíncrono
            logger.error("Error registrarRetiroAutomaticoPorFallecimiento(SolicitudNovedadDTO, UserDTO)", e);
        }
    }

    /**
     * Realiza el registro de la novedad de retiro para cada afiliacion de la
     * persona que se le registro fallecimiento
     *
     * @param infoAfiliado     Información del afiliado
     * @param solicitudNovedad Información solicitud novedad
     * @param userDTO          Información usuario contexto
     */
    private void procesarRetiroAfiliado(ConsultarAfiliadoOutDTO infoAfiliado, SolicitudNovedadDTO solicitudNovedad,
                                        UserDTO userDTO) {
        logger.info("Inicia procesarRetiroAfiliado(ConsultarAfiliadoOutDTO, SolicitudNovedadDTO, UserDTO)");
        if (infoAfiliado == null || infoAfiliado.getInformacionLaboralTrabajador() == null
                || infoAfiliado.getInformacionLaboralTrabajador().isEmpty()) {
            logger.info("procesarRetiroAfiliado(" + infoAfiliado + ", " + solicitudNovedad + ", " + userDTO
                    + ") :: No se enviaron los parámetros");
            return;
        }
        // Se consulta las clasificaciones de la persona
        List<ClasificacionEnum> clasificaciones = consultarClasificacion(
                solicitudNovedad.getDatosPersona().getTipoIdentificacion(),
                solicitudNovedad.getDatosPersona().getNumeroIdentificacion());
        // Se registra el retiro para las afiliaciones activas
        for (InformacionLaboralTrabajadorDTO informacionLaboral : infoAfiliado.getInformacionLaboralTrabajador()) {
            solicitudNovedad.getDatosPersona().setIdRolAfiliado(informacionLaboral.getIdRolAfiliado());
            solicitudNovedad.getDatosPersona().setIdBeneficiario(null);
            solicitudNovedad.getDatosPersona()
                    .setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO);
            solicitudNovedad.setClasificacion(
                    obtenerClasificacionTipoAfiliado(clasificaciones, informacionLaboral.getTipoAfiliado()));
            solicitudNovedad.setTipoTransaccion(
                    obtenerTransaccionRetiroTipoAfiliado(informacionLaboral.getTipoAfiliado(),
                            solicitudNovedad.getClasificacion()));
            if (solicitudNovedad.getClasificacion() != null && solicitudNovedad.getTipoTransaccion() != null
                    && EstadoAfiliadoEnum.ACTIVO.equals(informacionLaboral.getEstadoAfiliado())) {
                // Se registra la novedad
                radicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedad, userDTO);
            }
        }
        logger.debug("Inicia procesarRetiroAfiliado(ConsultarAfiliadoOutDTO, SolicitudNovedadDTO, UserDTO)");
    }

    /**
     * Realiza el registro de la novedad de inactivación para cada beneficiario
     * de la persona que se le registro fallecimiento
     *
     * @param listaBeneficiarios Lista de beneficiarios encontrados
     * @param solicitudNovedad   Información solicitud novedad
     * @param userDTO            Información usuario contexto
     */
    private void procesarInactivacionBeneficiario(List<BeneficiarioModeloDTO> listaBeneficiarios,
                                                  SolicitudNovedadDTO solicitudNovedad,
                                                  UserDTO userDTO) {
        logger.info("Empieza metodo procesarInactivacionBeneficiario");
        if (listaBeneficiarios == null || listaBeneficiarios.isEmpty()) {
            return;
        }

        for (BeneficiarioModeloDTO beneficiarioModeloDTO : listaBeneficiarios) {
            solicitudNovedad.getDatosPersona().setIdRolAfiliado(null);
            solicitudNovedad.getDatosPersona()
                    .setMotivoDesafiliacionBeneficiario(MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO);
            if (ClasificacionEnum.CONYUGE.equals(beneficiarioModeloDTO.getTipoBeneficiario())) {
                solicitudNovedad.getDatosPersona().setFechaFinsociedadConyugal(new Date().getTime());
            }
            solicitudNovedad.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
            solicitudNovedad.setTipoTransaccion(
                    getTipoTransaccionInactivarBeneficiarioByClasificacion(
                            beneficiarioModeloDTO.getTipoBeneficiario()));
            // Se obtienen los datos basicos de beneficiario para el registro de la novedad

            llenarDatosBeneficiarioNovedad(beneficiarioModeloDTO, solicitudNovedad.getDatosPersona(), null);
            if (solicitudNovedad.getClasificacion() != null && solicitudNovedad.getTipoTransaccion() != null) {
                // Se registra la novedad
                radicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedad, userDTO);
            }
        }
    }

    /**
     * Metodo para el llamado al servicio de generación de numero de radicado
     *
     * @param cantidadSolicitud Cantidad de radicados que se necesitan para el
     *                          proceso
     * @return Información numero de radicado inicial
     */
    private NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicado(Integer cantidadSolicitud) {
        ObtenerNumeroRadicadoCorrespondencia obtenerListaRadicados = new ObtenerNumeroRadicadoCorrespondencia(
                cantidadSolicitud,
                TipoEtiquetaEnum.NUMERO_RADICADO);
        obtenerListaRadicados.execute();
        return obtenerListaRadicados.getResult();
    }

    @Asynchronous
    @Override
    public void radicarSolicitudNovedadCascada(DatosNovedadCascadaDTO datosNovedadConsecutiva, UserDTO userDTO) {

        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.radicarSolicitudNovedadCascada(datosNovedadConsecutiva, userDTO);
    }

    @Override
    public Map<String, Object> validarSolicitudNovedad(VerificarSolicitudNovedadDTO entrada, UserDTO userDTO) {
        logger.debug("Inicia validarSolicitudNovedad(VerificarSolicitudNovedadDTO, UserDTO)");
        Long idTarea = entrada.getIdTarea();
        Map<String, Object> respuesta = new HashMap<String, Object>();

        try {
            ObtenerTareaActivaInstancia tareaActivaSrv = new ObtenerTareaActivaInstancia(entrada.getInstanciaProceso());
            tareaActivaSrv.execute();

            TareaDTO tarea = tareaActivaSrv.getResult();

            if (tarea != null && idTarea.equals(tarea.getId()) && EstadoTareaEnum.RESERVADA.equals(tarea.getEstado())) {
                // Se suspende la tarea a fin de evitar que sea retomada desde la bandela
                // mientras se pprocesa
                suspenderTarea(idTarea);
                tareaSuspendida = Boolean.TRUE;

                verificarSolicitudNovedad(entrada, userDTO);
            } else {
                logger.info("INICIA validarSolicitudNovedad");
                respuesta.put("mensaje", "La solicitud ya se encuentra en proceso");
                logger.info("FINALIZA validarSolicitudNovedad");
            }
        } catch (BPMSExecutionException be) {
            if (tareaSuspendida) {
                retomarTarea(idTarea);
            }
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    be);
        } catch (Exception e) {
            if (tareaSuspendida) {
                retomarTarea(idTarea);
            }
            logger.error("Finaliza validarSolicitudNovedad(VerificarSolicitudNovedadDTO, UserDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza validarSolicitudNovedad(VerificarSolicitudNovedadDTO, UserDTO)");
        return respuesta;
    }

    /**
     * Construye la lista de novedades que se ejecutan en cascada
     *
     * @param datosNovedadConsecutiva Datos de novedad en cascada
     * @return Lista de solicitudes de novedad a registrar
     */
    private List<SolicitudNovedadDTO> construirSolicitudNovedadConsecutiva(
            DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        List<SolicitudNovedadDTO> listaSolicitudes = null;

        switch (datosNovedadConsecutiva.getTipoTransaccionOriginal()) {
            case DESAFILIACION:
            case SUSTITUCION_PATRONAL:
            case DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA:
            case DESAFILIACION_AUTOMATICA_POR_MORA:
                listaSolicitudes = construirDetalleNovedadConsecutivaAfiliado(datosNovedadConsecutiva);
                break;
            case RETIRO_TRABAJADOR_DEPENDIENTE:
            case RETIRO_TRABAJADOR_INDEPENDIENTE:
            case RETIRO_PENSIONADO_25ANIOS:
            case RETIRO_PENSIONADO_MAYOR_1_5SM_0_6:
            case RETIRO_PENSIONADO_MAYOR_1_5SM_2:
            case RETIRO_PENSIONADO_MENOR_1_5SM_0:
            case RETIRO_PENSIONADO_MENOR_1_5SM_0_6:
            case RETIRO_PENSIONADO_MENOR_1_5SM_2:
            case RETIRO_PENSIONADO_PENSION_FAMILIAR:
            case RETIRO_AUTOMATICO_POR_MORA:
                listaSolicitudes = construirDetalleNovedadConsecutivaBeneficiario(datosNovedadConsecutiva);
                break;
            case AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION:
                listaSolicitudes = construirDetalleNovedadConsecutivaAfiliacionBeneficiario(datosNovedadConsecutiva);
                break;
            default:
                break;
        }
        return listaSolicitudes;
    }

    /**
     * Construye la solicitud de novedad detallada para retiro de afiliados
     * dependientes
     *
     * @param datosNovedadConsecutiva Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de afiliado
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaAfiliado(
            DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
        for (int i = 0; i < datosNovedadConsecutiva.getListaRoles().size(); i++) {
            // Se obtiene el rolAfiliado
            RolAfiliadoModeloDTO rolAfiliado = datosNovedadConsecutiva.getListaRoles().get(i);
            // Se genera el numero de radicado con base en el original
            String numeroRadicado = datosNovedadConsecutiva.getNumeroRadicadoOriginal() + "_" + (i + 1);
            // Datos básicos de la novedad
            SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
            solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
            solicitudNovedad.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            solicitudNovedad.setTipoTransaccion(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
            llenarDatosAfiliadoNovedad(rolAfiliado.getAfiliado(), datosPersona);
            datosPersona.setMotivoDesafiliacionTrabajador(datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado());
            datosPersona.setFechaInicioNovedad(datosNovedadConsecutiva.getFechaRetiro());
            datosPersona.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
        }
        return listaSolicitudes;
    }

    /**
     * Construye la solicitud de novedad detallada para inactivar beneficiarios
     *
     * @param datosNovedadConsecutiva Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de beneficiarios
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaBeneficiario(
            DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
        logger.info("INICIA METODO construirDetalleNovedadConsecutivaBeneficiario");
        for (int i = 0; i < datosNovedadConsecutiva.getListaBeneficiario().size(); i++) {
            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario = null;
            // Se obtiene el beneficiario
            BeneficiarioModeloDTO beneficiario = datosNovedadConsecutiva.getListaBeneficiario().get(i);
            // Se genera el numero de radicado con base en el original
            String numeroRadicado = datosNovedadConsecutiva.getNumeroRadicadoOriginal() + "_" + (i + 1);
            // Datos básicos de la novedad
            SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
            solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
            solicitudNovedad.setClasificacion(beneficiario.getTipoBeneficiario());
            solicitudNovedad.setTipoTransaccion(
                    getTipoTransaccionInactivarBeneficiarioByClasificacion(beneficiario.getTipoBeneficiario()));
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            // Se obtiene el motivo de desafiliación beneficiario basado en el del afiliado
            motivoDesafiliacionBeneficiario = ValidacionDesafiliacionUtils
                    .validarMotivoDesafiliacionBeneficiario(datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado());
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
            llenarDatosBeneficiarioNovedad(beneficiario, datosPersona, null);
            datosPersona.setMotivoDesafiliacionBeneficiario(motivoDesafiliacionBeneficiario);
            datosPersona.setFechaInactivacionBeneficiario(datosNovedadConsecutiva.getFechaRetiro());
            datosPersona.setFechaRetiro(datosNovedadConsecutiva.getFechaRetiro());
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
        }
        return listaSolicitudes;
    }

    /**
     * Construye la solicitud de novedad detallada para inactivar beneficiarios
     * producto de afiliciónes como conyuges/padres con registros activos como
     * hijos
     *
     * @param datosNovedadConsecutiva Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de beneficiarios
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaAfiliacionBeneficiario(
            DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
        String radicadoOriginal = datosNovedadConsecutiva.getNumeroRadicadoOriginal();

        // Se obtiene el número de consecutivo actual (Dado que el afiliado puede tener
        // registros como beneficiario que
        // ya generaron solicitudes de novedad de retiro asociados a la solicitud
        ObtenerConsecutivoNovedad consecutivoSrv = new ObtenerConsecutivoNovedad(radicadoOriginal);
        consecutivoSrv.execute();

        Integer consecutivo = consecutivoSrv.getResult();

        for (int i = 0; i < datosNovedadConsecutiva.getListaBeneficiario().size(); i++) {
            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario = null;
            // Se obtiene el beneficiario
            BeneficiarioModeloDTO beneficiario = datosNovedadConsecutiva.getListaBeneficiario().get(i);
            // Se genera el numero de radicado con base en el original
            String numeroRadicado = radicadoOriginal + "_" + (consecutivo++);
            // Datos básicos de la novedad
            SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
            solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
            solicitudNovedad.setClasificacion(beneficiario.getTipoBeneficiario());
            solicitudNovedad.setTipoTransaccion(
                    getTipoTransaccionInactivarBeneficiarioByClasificacion(beneficiario.getTipoBeneficiario()));
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO;
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
            llenarDatosBeneficiarioNovedad(beneficiario, datosPersona, null);
            datosPersona.setMotivoDesafiliacionBeneficiario(motivoDesafiliacionBeneficiario);
            datosPersona.setFechaInactivacionBeneficiario(datosNovedadConsecutiva.getFechaRetiro());
            datosPersona.setFechaRetiro(datosNovedadConsecutiva.getFechaRetiro());
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
        }
        return listaSolicitudes;
    }

    /**
     * Retoma una tarea en BPM previamente suspendida
     *
     * @param idTarea
     */
    private void retomarTarea(Long idTarea) {
        logger.debug("Inicio de retomarTarea(Long)");
        try {
            RetomarTarea retomarTarea = new RetomarTarea(idTarea, new HashMap<>());
            retomarTarea.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
        logger.debug("Fin de retomarTarea(Long)");
    }

    /**
     * Suspende una tarea en BPM
     *
     * @param idTarea
     */
    private void suspenderTarea(Long idTarea) {
        logger.debug("Inicio de suspenderTarea(Long)");
        try {
            SuspenderTarea suspenderTarea = new SuspenderTarea(idTarea, new HashMap<String, Object>());
            suspenderTarea.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,
                    e);
        }
        logger.debug("Fin de suspenderTarea(Long)");
    }

    @Override
    public String inactivarBeneficiariosAfiiadoInactivo(UserDTO usuario) {
        String firmaServicio = "inactivarBeneficiariosAfiiadoInactivo()";
        logger.info("Inicia " + firmaServicio);

        ConsultarAfiliadosInactivosBeneficiariosActivos afiliadosSrv = new ConsultarAfiliadosInactivosBeneficiariosActivos();
        afiliadosSrv.execute();
        List<DatosAfiliadoRetiroDTO> afiliados = afiliadosSrv.getResult();

        int countExitosos = 0;
        int countError = 0;
        int countProgreso = 0;
        int totalRegistros = afiliados.size();

        for (DatosAfiliadoRetiroDTO afiliado : afiliados) {
            logger.info("[inactivarBeneficiariosAfiiadoInactivo:DatosAfiliadoRetiroDTO]");
            logger.info("Fecha de retiro del afiliado--->" + afiliado.getFechaRetiro());
            try {
                procesarRetiroBeneficiarios(afiliado, usuario);
                countExitosos++;
            } catch (Exception e) {
                logger.error("Ocurrió un error procesando los beneficiarios del afiliado " + afiliado.getIdAfiliado(),
                        e);
                countError++;
            }
            logger.info("Procesados " + ++countProgreso + " de " + totalRegistros);
        }

        logger.info("Procesados : " + countExitosos + " | Sin Procesar : " + countError);
        logger.debug("Finaliza " + firmaServicio);
        return "Procesados : " + countExitosos + " | Sin Procesar : " + countError;
    }

    /**
     * Procesa el retiro de benefiiciarios en cascada
     *
     * @param usuario
     * @param afiliado
     */
    private void procesarRetiroBeneficiarios(DatosAfiliadoRetiroDTO afiliado, UserDTO usuario) {
        String firmaServicio = "procesarRetiroBeneficiarios(DatosAfiliadoRetiroDTO, UserDTO)";
        logger.info("Inicia " + firmaServicio);
        List<BeneficiarioModeloDTO> listaBeneficiarios;
        listaBeneficiarios = consultarBeneficiariosAfiliacion(afiliado.getIdAfiliado(), null);

        DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
        datosNovedadConsecutivaDTO.setFechaRetiro(afiliado.getFechaRetiro().getTime());
        datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
        datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.RETIRO_VOLUNTARIO);
        datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(afiliado.getNumeroRadicacion());
        datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(afiliado.getTipoTransaccion());
        radicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO, usuario);

        logger.info("Finaliza " + firmaServicio);
    }

    /**
     * Realiza el llamado al servicio que consulta los beneficiarios asociados a
     * la afiliación objeto del retiro
     *
     * @param idAfiliado    Identificador afiliado
     * @param idRolAfiliado Identificador del rol afiliado
     * @return
     */
    private List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
        String firmaServicio = "consultarBeneficiariosAfiliacion(Long, Long)";
        logger.info("__Inicia " + firmaServicio);
        ConsultarBeneficiariosAfiliacion consultarBeneficiariosAfiliacionService = new ConsultarBeneficiariosAfiliacion(
                idAfiliado,
                idRolAfiliado);
        consultarBeneficiariosAfiliacionService.execute();

        logger.info("Finaliza " + firmaServicio);
        return consultarBeneficiariosAfiliacionService.getResult();
    }

    @Override
    public void procesarActivacionBeneficiarioPILA(NovedadAportesDTO novedadAportesDTO, UserDTO userDTO) {
        logger.info("__Inicia procesarActivacionBeneficiarioPILA IN NOVEDADES COMPS ");
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        p.procesarActivacionBeneficiarioPILA(novedadAportesDTO, userDTO, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#
     * radicarSolicitudNovedadArchivoActualizacion(com.asopagos.novedades.dto.
     * SolicitudNovedadDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudNovedadDTO radicarSolicitudNovedadArchivoActualizacion(SolicitudNovedadDTO solNovedadDTO,
                                                                           UserDTO userDTO, Long idSolicitud) {
        logger.info(
                "**__**radicarSolicitudNovedadArchivoActualizacion(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO, Long idSolicitud)");
        logger.info("***Weizman => radicarSolicitudNovedadArchivoActualizacion -> solNovedadDTO.getContinuaProceso() -> " + solNovedadDTO.getContinuaProceso());
        logger.info("***Weizman => radicarSolicitudNovedadArchivoActualizacion -> idSolicitud -> " + idSolicitud);
        logger.info("***Weizman => radicarSolicitudNovedadArchivoActualizacion -> solNovedadDTO.getIdSolicitud() -> " + solNovedadDTO.getIdSolicitudCargueMasivo());
        solNovedadDTO.setIdSolicitud(idSolicitud);
        try {
            // Se radica la novedad
            solNovedadDTO = radicarSolicitudNovedad(solNovedadDTO, userDTO);
            //if (PuntoResolucionEnum.FRONT.equals(solNovedadDTO.getNovedadDTO().getPuntoResolucion())
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CARTERA)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
            //        || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
            //  actualizarEstadoSolicitudNovedad(idSolicitud, EstadoSolicitudNovedadEnum.APROBADA);
            //}
            // Se actualiza el estado a APROBADA
            //actualizarEstadoSolicitudNovedad(idSolicitud, EstadoSolicitudNovedadEnum.APROBADA);
            logger.info(
                    "**__**Fin de método radicarSolicitudNovedadArchivoActualizacion(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)");
            return solNovedadDTO;
        } catch (Exception e) {
            logger.error(
                    "Ocurrio un error inesperado en radicarSolicitudNovedadArchivoActualizacion(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO, Long idSolicitud)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Radica la solicitud de novedad proveniente del canal PILA
     *
     * @param solNovedadDTO
     * @param userDTO
     * @return a
     */
    public SolicitudNovedadDTO radicarSolicitudNovedadPILA(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO) {
        logger.info("**__** tipotransaccion novedades composite radicarSolicitudNovedadPILA"
                + solNovedadDTO.getTipoTransaccion());
        try {
            logger.info("INICIA radicarSolicitudNovedadPILA(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO))");

            // Bandera que indica que el servicio es consumido desde un utilitario
            Boolean esUtilitario = (solNovedadDTO.getEsUtilitario() != null && solNovedadDTO.getEsUtilitario())
                    ? solNovedadDTO.getEsUtilitario()
                    : Boolean.FALSE;

            /* se busca la novedad seleccionada y se setea en el dto */
            // novedades de reintegro fallara ya que no existe en la tabla
            // ParametrizacionNovedad
            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            solNovedadDTO.setNovedadDTO(novedad);

            if ((solNovedadDTO.getDatosPersona() != null) && (solNovedadDTO.getResultadoValidacion() == null
                    || solNovedadDTO.getContinuaProceso() == null || !solNovedadDTO.getContinuaProceso())
                    && !esUtilitario) {
                /*
                 * se verifica si cumple con las validaciones de la novedad, se excluye el caso
                 * de una afiliacion de
                 * empleador(se hacen las mismas validaciones que en la afiliacion)
                 */
                if (solNovedadDTO
                        .getTipoTransaccion() == TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL
                        || solNovedadDTO
                        .getTipoTransaccion() == TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL) {
                    solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                } else if (consultarRetroactividadNovedades(solNovedadDTO.getIdRegistroDetallado())) {
                    solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                } else if (solNovedadDTO
                        .getTipoTransaccion() == TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB) {
                    solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                } else {
                    solNovedadDTO.setResultadoValidacion(validarNovedadPILA(solNovedadDTO));
                }

            } else if ((solNovedadDTO.getDatosPersona() != null)
                    && (solNovedadDTO.getResultadoValidacion() == null || solNovedadDTO.getContinuaProceso() == null
                    || !solNovedadDTO.getContinuaProceso())
                    && esUtilitario) { // si no entra al if anterior se pregunta si fue porque es utilitario, para que
                // continue con la radicacion de la novedad
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
            }

            // TODO PERSISTIR ERROR EN LA TABLA ExepcionNovedadPila
            // metodo que agrupa la transacción de la novedad
            logger.info("**__** transaccionNovedad -> metodo radicarSolicitudNovedadPILA ");
            solNovedadDTO = transaccionNovedad(solNovedadDTO, userDTO);
            logger.info("**__** FIN transaccionNovedad -> metodo radicarSolicitudNovedadPILA ");
            if (!ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())) {
                parametrizarNotificacion(true, solNovedadDTO, userDTO);
            }

            logger.info(
                    "Fin de método radicarSolicitudNovedadPILA((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");
            return solNovedadDTO;
        } catch (Exception e) {
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.info("**__**catch radicarSolicitudNovedadPILA error: " + e);
            logger.error(
                    "Ocurrio un error inesperado en radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Metodo que agrupa la transacción de la aplicacion de una novedad
     *
     * @param solNovedadDTO
     * @param userDTO
     * @return a
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private SolicitudNovedadDTO transaccionNovedad(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO)
            throws Exception {
        logger.info("INICIA método transaccionNovedad((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");

        /* si el resultado es exitoso se procede a radicar */
        if (ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion())) {
            /* se crea la solicitud de novedad y asigna el nùmero de radicaciòn */
            solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
            SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);

            logger.info("_getRutaCualificada: " + solNovedadDTO.getNovedadDTO().getRutaCualificada());
            /* si el punto de resolucion es el front se guarda la informacion */
            if (solNovedadDTO.getTipoTransaccion() == TipoTransaccionEnum.NOVEDAD_REINTEGRO
                    && solNovedadDTO.getDatosPersona().getIdRolAfiliado() != null) {

                logger.info("Ejecicion de STORED PROCEDURE para activar beneficiarios y afiliado en novedades de ingreso PILA IdSolicitud:"
                        +solNovedadDTO.getIdSolicitud()+" IdRolAfiliado:"+solNovedadDTO.getDatosPersona().getIdRolAfiliado());
                SolicitudNovedadModeloDTO solicitudNovedadRe = new SolicitudNovedadModeloDTO();
                solicitudNovedadRe.setIdNovedad(solNovedadDTO.getIdSolicitud());
                solicitudNovedadRe.setEstadoSolicitud(solNovedadDTO.getEstadoSolicitudNovedad());
                solicitudNovedadRe.setIdSolicitudNovedad(solNovedadDTO.getIdSolicitudNovedad());
                NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
                n.resolverNovedadReintegroMismoEmpleador(solNovedadDTO, solicitudNovedadRe, userDTO);

                actualizarEstadoSolicitudNovedad(solNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                /* 
                StoredProcedureQuery procedimiento = entityManager
                .createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_ACTUALIZAR_NOVEDADES_REINTEGRO_BENEFICIARIOS_AFILIADOS_PILA)
                .setParameter("sapRolAfiliado", solNovedadDTO.getDatosPersona().getIdRolAfiliado())
                .setParameter("sapSolicitudGlobal", solNovedadDTO.getIdSolicitud());

        procedimiento.execute();*/
                logger.info("FINALIZA Ejecicion STORED_PROCEDURE_ACTUALIZAR_NOVEDADES_REINTEGRO_BENEFICIARIOS_AFILIADOS_PILA");
            } else if (solNovedadDTO.getTipoTransaccion() == TipoTransaccionEnum.NOVEDAD_REINTEGRO
                    && solNovedadDTO.getDatosPersona().getIdRolAfiliado() == null) {
                if (solNovedadDTO.getIdSolicitud() != null
                        && solNovedadDTO.getEstadoSolicitudNovedad() != EstadoSolicitudNovedadEnum.RECHAZADA) {
                    solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.APROBADA);
                    logger.info("solNovedadDTO****--- Antes de crear la solicitud" + solNovedadDTO);
                    // Ducplicidaad de novedad - crea inconsistencias en bd con triggers de
                    // soliciutud afiliacion
                    // SolicitudNovedadModeloDTO solicitudNovedadRe =
                    // crearSolicitudNovedadReintegro(solNovedadDTO, userDTO);
                    SolicitudNovedadModeloDTO solicitudNovedadRe = new SolicitudNovedadModeloDTO();
                    solicitudNovedadRe.setIdNovedad(solNovedadDTO.getIdSolicitud());
                    solicitudNovedadRe.setEstadoSolicitud(solNovedadDTO.getEstadoSolicitudNovedad());
                    solicitudNovedadRe.setIdSolicitudNovedad(solNovedadDTO.getIdSolicitudNovedad());
                    // Se resuelve la novedad por medio de los convertidores
                    resolverNovedadReintegro(solNovedadDTO, solicitudNovedadRe, userDTO);
                    return solNovedadDTO;
                }
            } else {
                resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
            }
            /*
             * novedades que se radican y son web o externas se envian el comunicado por
             * servicios
             */
            // Se almacena los datos de la solicitud en datos temporales
            guardarDatosTemporal(solNovedadDTO);

            // se marca la trnsaccion como confirmada
            if (solNovedadDTO.getTenNovedadId() != null) {
                ConfirmarTransaccionNovedadPilaRutine c = new ConfirmarTransaccionNovedadPilaRutine();
                c.confirmarTransaccionNovedadPilaRutine(solNovedadDTO.getTenNovedadId(),
                        solNovedadDTO.getIdRegistroDetallado(), entityManager);
            }
        } else {
            /*
             * si las validaciones de negocio fallaron y el canal es web se guarda un
             * intento de novedad
             */
            IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
            intentoNovedadDTO.setCanalRecepcion(solNovedadDTO.getCanalRecepcion());
            intentoNovedadDTO.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.VALIDACION_REGLAS_NEGOCIO);
            intentoNovedadDTO.setClasificacion(solNovedadDTO.getClasificacion());
            intentoNovedadDTO.setTipoTransaccion(solNovedadDTO.getTipoTransaccion());
            intentoNovedadDTO.setIdRegistroDetallado(solNovedadDTO.getIdRegistroDetallado());
            intentoNovedadDTO.setTipoIdentificacion(solNovedadDTO.getDatosPersona().getTipoIdentificacion());
            intentoNovedadDTO.setNumeroIdentificacion(solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
            intentoNovedadDTO.setIdBeneficiario(solNovedadDTO.getDatosPersona().getIdBeneficiario());
            intentoNovedadDTO.setIdRolAfiliado(solNovedadDTO.getDatosPersona().getIdRolAfiliado());
            intentoNovedadDTO.setIdRegistroDetalladoNovedad(solNovedadDTO.getIdRegistroDetalladoNovedad());
            solNovedadDTO.setIdSolicitud(registrarIntentoFallido(intentoNovedadDTO, userDTO));
            solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.RECHAZADA);
            /* se cierra la solicitud y se envia comunicado */

            cerrarSolicitudNovedad(solNovedadDTO, true);
            // se marca la trnsaccion como
            if (solNovedadDTO.getTenNovedadId() != null) {
                ConfirmarTransaccionNovedadPilaRutine c = new ConfirmarTransaccionNovedadPilaRutine();
                c.confirmarTransaccionNovedadPilaRutine(solNovedadDTO.getTenNovedadId(),
                        solNovedadDTO.getIdRegistroDetallado(), entityManager);
            }

        }
        logger.info("fin método transaccionNovedad((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO))");
        return solNovedadDTO;
    }

    /**
     * Método encargado de ejecutar las validaciones de una novedad PILA.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return resultado de las validaciones.
     */
    private ResultadoRadicacionSolicitudEnum validarNovedadPILA(SolicitudNovedadDTO solNovedadDTO) {
        logger.debug("Inicio de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();

        if (solNovedadDTO.getDatosPersona() != null) {
            Map<String, String> datosValidacion = llenarDatosValidacionPersona(solNovedadDTO);
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    calcularBloqueValidacionPILA(solNovedadDTO.getTipoTransaccion()),
                    solNovedadDTO.getTipoTransaccion().getProceso(), solNovedadDTO.getClasificacion().name(),
                    datosValidacion);
            validarReglasService.execute();
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
        }

        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        if (!validaciones.isEmpty()) {
            // Se verifica el resultado de las validaciones
            resultado = verificarResultadoValidacionPILA(validaciones, solNovedadDTO);
        }
        logger.debug("Fin de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        return resultado;
    }

    /**
     * Calcula el bloque de las validaciones asociadas a novedades PILA
     *
     * @param tipoTransaccion
     * @return
     */
    private String calcularBloqueValidacionPILA(TipoTransaccionEnum tipoTransaccion) {
        logger.info("**__**Inicio de método calcularBloqueValidacionPILA(TipoTransaccionEnum)");
        String bloqueValidacionStr = "";
        switch (tipoTransaccion) {
            case VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PILA";
                break;
            case RETIRO_TRABAJADOR_DEPENDIENTE:
                bloqueValidacionStr = "RETIRO_TRABAJADOR_DEPENDIENTE_PILA";
                break;
            case VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PILA";
                break;
            case VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PILA";
                break;
            case INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL:
                bloqueValidacionStr = "INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PILA";
                break;
            case SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PILA";
                break;
            case LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL:
                bloqueValidacionStr = "LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PILA";
                break;
            case INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL:
                bloqueValidacionStr = "INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PILA";
                break;
            case RETIRO_TRABAJADOR_INDEPENDIENTE:
                bloqueValidacionStr = "RETIRO_TRABAJADOR_INDEPENDIENTE_PILA";
                break;
            case VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PILA";
                break;
            case VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PILA";
                break;
            case SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PILA";
                break;
            case VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL:
                bloqueValidacionStr = "VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PILA";
                break;
            case RETIRO_PENSIONADO_25ANIOS:
                bloqueValidacionStr = "RETIRO_PENSIONADO_25ANIOS_PILA";
                break;
            case RETIRO_PENSIONADO_MAYOR_1_5SM_0_6:
                bloqueValidacionStr = "RETIRO_PENSIONADO_MAYOR_1_5SM_0_6_PILA";
                break;
            case RETIRO_PENSIONADO_MAYOR_1_5SM_2:
                bloqueValidacionStr = "RETIRO_PENSIONADO_MAYOR_1_5SM_2_PILA";
                break;
            case RETIRO_PENSIONADO_MENOR_1_5SM_0:
                bloqueValidacionStr = "RETIRO_PENSIONADO_MENOR_1_5SM_0_PILA";
                break;
            case RETIRO_PENSIONADO_MENOR_1_5SM_0_6:
                bloqueValidacionStr = "RETIRO_PENSIONADO_MENOR_1_5SM_0_6_PILA";
                break;
            case RETIRO_PENSIONADO_MENOR_1_5SM_2:
                bloqueValidacionStr = "RETIRO_PENSIONADO_MENOR_1_5SM_2_PILA";
                break;
            case RETIRO_PENSIONADO_PENSION_FAMILIAR:
                bloqueValidacionStr = "RETIRO_PENSIONADO_PENSION_FAMILIAR_PILA_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PILA";
                break;
            case VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL:
                bloqueValidacionStr = "VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PILA";
                break;
            case SUSPENSION_PENSIONADO_SUS:
                bloqueValidacionStr = "SUSPENSION_PENSIONADO_SUS_PILA";
                break;
            default:
                break;
        }
        System.out.println("TRANSACCION EN CURSO : " + bloqueValidacionStr);
        logger.debug("Fin de método calcularBloqueValidacionPILA(TipoTransaccionEnum)");
        return bloqueValidacionStr;
    }

    /**
     * Realiza el proceso de verificacion del resultado de las validaciones
     * ejecutadas, para indicar el comportamiento en pantalla
     *
     * @param listaValidacionesFallidas Contiene la lista de validaciones
     *                                  fallidas por tipo de excepcion
     * @param validacionesEjecutadas    Lista de validaciones ejecutadas
     * @param solNovedadD               TO Informacion de la solicitud de novedad
     * @return Resultado de radicacion de la solicitud a partir del resultado de
     *         las validaciones
     */
    private ResultadoRadicacionSolicitudEnum verificarResultadoValidacionPILA(
            List<ValidacionDTO> validacionesEjecutadas, SolicitudNovedadDTO solNovedadDTO) {
        logger.debug("Inicio de método verificarResultadoValidacionPILA(List<ValidacionDTO>, SolicitudNovedadDTO)");
        // Resultado verificacion validaciones
        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;

        for (ValidacionDTO validacionDTO : validacionesEjecutadas) {

            if (validacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                logger.debug(
                        "Fin de método verificarResultadoValidacionPILA(List<ValidacionDTO>, SolicitudNovedadDTO)");
                resultado = ResultadoRadicacionSolicitudEnum.FALLIDA;
                return resultado;
            }
        }
        logger.debug("Fin de método verificarResultadoValidacionPILA(List<ValidacionDTO>, SolicitudNovedadDTO)");
        return resultado;
    }

    /**
     *
     */
    @Asynchronous
    @Override
    public void radicarNovedadRetiroPilaUtil(UserDTO userDTO) {
        String nombreServicio = "radicarNovedadRetiroPilaUtil()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + nombreServicio);
        try {
            // Se consulta las personas que aplican para la novedad
            ObtenerNovedadesRetiroNoProcesadasPila retirosNoProcesadosPila = new ObtenerNovedadesRetiroNoProcesadasPila();
            retirosNoProcesadosPila.execute();
            List<AfiliadoNovedadRetiroNoAplicadaDTO> NovedadesRetiroNoAplicadas = retirosNoProcesadosPila.getResult();

            for (AfiliadoNovedadRetiroNoAplicadaDTO afiliadoNovedadRetiroNoAplicadaDto : NovedadesRetiroNoAplicadas) {
                NovedadAportesDTO novedadDTO = new NovedadAportesDTO();

                novedadDTO.setAplicar(MarcaNovedadEnum.APLICADA);
                novedadDTO.setCanalRecepcion(CanalRecepcionEnum.PILA);
                novedadDTO.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
                novedadDTO.setComentarios("");

                novedadDTO.setFechaInicio(afiliadoNovedadRetiroNoAplicadaDto.getFechaInicioNovedad().getTime());
                novedadDTO.setTipoNovedad(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);

                novedadDTO.setTipoIdentificacion(afiliadoNovedadRetiroNoAplicadaDto.getTipoIdentificacionAfiliado());
                novedadDTO
                        .setNumeroIdentificacion(afiliadoNovedadRetiroNoAplicadaDto.getNumeroIdentificacionAfiliado());

                novedadDTO.setTipoIdentificacionAportante(
                        afiliadoNovedadRetiroNoAplicadaDto.getTipoIdentificacionEmpleador());
                novedadDTO.setNumeroIdentificacionAportante(
                        afiliadoNovedadRetiroNoAplicadaDto.getNumeroIdentificacionEmpleador());

                SolicitudNovedadDTO solicitudNovedadDTO = constructorSolicitudNovedad(novedadDTO,
                        afiliadoNovedadRetiroNoAplicadaDto.getIdRolAfiliado());

                solicitudNovedadDTO.setEsUtilitario(Boolean.TRUE);

                radicarSolicitudNovedadPILA(solicitudNovedadDTO, userDTO);

                MarcarNovedadesRetiroNoProcesadasPila marcarNovedadesRetiroNoProcesadasPila = new MarcarNovedadesRetiroNoProcesadasPila(
                        afiliadoNovedadRetiroNoAplicadaDto.getId());

                marcarNovedadesRetiroNoProcesadasPila.execute();
            }

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     *
     */
    @Override
    public Integer radicarSolicitudNovedadRetiroTrabajadorDependienteSinValidacionesUtil(UserDTO userDTO) {
        String nombreServicio = "RadicarSolicitudNovedadRetiroTrabajadorDependienteSinValidacionesUtil()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + nombreServicio);
        Integer registrosProcesados = 0;
        try {

            // Se consulta las personas que aplican para la novedad
            ConsultarTrabajadoresActivosConEmpleadorInactivo consultarTrabajadores = new ConsultarTrabajadoresActivosConEmpleadorInactivo();
            consultarTrabajadores.execute();
            List<RelacionTrabajadorEmpresaDTO> personasAInactivar = consultarTrabajadores.getResult();

            if (personasAInactivar != null && !personasAInactivar.isEmpty()) {

                for (RelacionTrabajadorEmpresaDTO relacionTrabajadorEmpresaDTO : personasAInactivar) {
                    // Se settea el DTO de novedad para su ejecución
                    NovedadAportesDTO novedadDTO = new NovedadAportesDTO();
                    // Se indica que la novedad se debe aplicar
                    novedadDTO.setAplicar(MarcaNovedadEnum.APLICADA);
                    novedadDTO.setCanalRecepcion(CanalRecepcionEnum.PILA);
                    novedadDTO.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
                    novedadDTO.setComentarios("");
                    // if (registroNovedadFuturaModeloDTO.getFechaFin() != null) {
                    // result.setFechaFin(registroNovedadFuturaModeloDTO.getFechaFin().getTime());
                    // }
                    // Se asigna la fecha de retiro como la fecha inicio novedad
                    novedadDTO.setFechaInicio(relacionTrabajadorEmpresaDTO.getFechaRetiro().getTime());
                    // result.setIdRegistroDetallado(registroNovedadFuturaModeloDTO.getIdRegistroDetallado());
                    novedadDTO.setTipoNovedad(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
                    // Se adiciona la persona
                    if (relacionTrabajadorEmpresaDTO.getNumeroIdentificacionAfiliado() != null) {
                        novedadDTO.setTipoIdentificacion(relacionTrabajadorEmpresaDTO.getTipoIdentificacionAfiliado());
                        novedadDTO.setNumeroIdentificacion(
                                relacionTrabajadorEmpresaDTO.getNumeroIdentificacionAfiliado());
                    }
                    // Se adiciona el empleador
                    if (relacionTrabajadorEmpresaDTO.getNumeroIdentificacionEmpleador() != null) {
                        novedadDTO.setTipoIdentificacionAportante(
                                relacionTrabajadorEmpresaDTO.getTipoIdentificacionEmpleador());
                        novedadDTO.setNumeroIdentificacionAportante(
                                relacionTrabajadorEmpresaDTO.getNumeroIdentificacionEmpleador());
                    }

                    /* se construye la solicitud de novedad */
                    SolicitudNovedadDTO solicitudNovedadDTO = construirSolicitudNovedadRetiroTrabajadorActivo(
                            novedadDTO, relacionTrabajadorEmpresaDTO);

                    // Se indica que es utilitario
                    solicitudNovedadDTO.setEsUtilitario(Boolean.TRUE);

                    // Se genera la radicación de la novedad para los registros encontrados
                    radicarSolicitudNovedadPILA(solicitudNovedadDTO, userDTO);

                    registrosProcesados++;

                    // Break para probar son un solo registro
                    // break;
                }

            }

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        return registrosProcesados;
    }

    /**
     * Se construye la novedad para utilitario que retira trabajadores activos
     * respecto a un empleador Inactivo
     *
     * @param novedadAportesDTO
     * @return
     */
    private SolicitudNovedadDTO construirSolicitudNovedadRetiroTrabajadorActivo(NovedadAportesDTO novedadAportesDTO,
                                                                                RelacionTrabajadorEmpresaDTO relacionTrabajadorEmpresaDTO) {
        logger.debug("Inicio de método construirSolicitudNovedad(NovedadAportesDTO novedadAportesDTO)");
        return constructorSolicitudNovedad(novedadAportesDTO, relacionTrabajadorEmpresaDTO.getIdRolAfiliado());
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar estados de un
     * procesamiento de planilla PILA
     *
     * @param idRegistroDetallado
     * @return
     */
    private Boolean consultarRetroactividadNovedades(Long idRegistroDetallado) {
        logger.info("**__**Inicia NovedadesCompositeBusiness.consultarRetroactividadNovedades ( Long: "
                + idRegistroDetallado + " )");
        Boolean success = false;
        if (idRegistroDetallado != null) {
            ConsultarRetroactividadNovedad consultarRetroactivo = new ConsultarRetroactividadNovedad(
                    idRegistroDetallado);
            consultarRetroactivo.execute();
            success = consultarRetroactivo.getResult();
        } else {
            logger.info("**__**consultarRetroactividadNovedades viene null idRegistroDetallado");
        }
        return success;

    }

    private SolicitudNovedadDTO constructorSolicitudNovedad(NovedadAportesDTO novedadAportesDTO,
                                                            Long idRolAfiliado) {
        SolicitudNovedadDTO solicitudNovedadDTO = new SolicitudNovedadDTO();
        solicitudNovedadDTO.setCanalRecepcion(novedadAportesDTO.getCanalRecepcion());
        solicitudNovedadDTO.setTipoTransaccion(novedadAportesDTO.getTipoNovedad());
        solicitudNovedadDTO.setObservaciones(novedadAportesDTO.getComentarios());
        // solicitudNovedadDTO.setIdRegistroDetallado(novedadAportesDTO.getIdRegistroDetallado());
        solicitudNovedadDTO.setClasificacion(novedadAportesDTO.getClasificacionAfiliado());
        DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
        persona.setTipoIdentificacion(novedadAportesDTO.getTipoIdentificacion());
        persona.setNumeroIdentificacion(novedadAportesDTO.getNumeroIdentificacion());
        persona.setFechaInicioNovedad(novedadAportesDTO.getFechaInicio());
        // persona.setFechaFinNovedad(novedadAportesDTO.getFechaFin());
        // persona.setIdBeneficiario(novedadAportesDTO.getBeneficiario() != null ?
        // novedadAportesDTO.getBeneficiario().getIdBeneficiario() : null);

        persona.setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum.DESAFILIACION_EMPLEADOR);

        persona.setIdRolAfiliado(idRolAfiliado);

        // if(novedadAportesDTO.getTipoNovedad()!=null &&
        // TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB.equals(novedadAportesDTO.getTipoNovedad()))
        // {
        // persona.setSucursalEmpleadorTrabajador(novedadAportesDTO.getSucursal());
        // }
        solicitudNovedadDTO.setDatosPersona(persona);
        logger.debug("Fin de método construirSolicitudNovedad(NovedadAportesDTO novedadAportesDTO)");
        return solicitudNovedadDTO;
    }

    @Override
    public SolicitudNovedadDTO obtenerValidacionesSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.debug("obtenerValidacionesSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO");
        /* se busca la novedad seleccionada y se setea en el dto */
        ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solicitudNovedadDTO.getTipoTransaccion());
        logger.info("obtenerValidacionesSolicitudNovedad novedad ---> " + novedad);
        solicitudNovedadDTO.setNovedadDTO(novedad);
        if (solicitudNovedadDTO.getDatosPersona() != null
                || solicitudNovedadDTO.getDatosEmpleador() != null) {
            List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();
            if (solicitudNovedadDTO.getDatosEmpleador() != null) {
                Map<String, String> datosValidacion = llenarDatosValidacionEmpleador(solicitudNovedadDTO);
                ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                        solicitudNovedadDTO.getTipoTransaccion().name(),
                        solicitudNovedadDTO.getTipoTransaccion().getProceso(),
                        solicitudNovedadDTO.getClasificacion().name(), datosValidacion);
                validarReglasService.execute();
                validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
                solicitudNovedadDTO.setListResultadoValidacion(validaciones);
            } else if (solicitudNovedadDTO.getDatosPersona() != null) {
                Map<String, String> datosValidacion = llenarDatosValidacionPersona(solicitudNovedadDTO);
                for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {

                    logger.info("Key = " + entry.getKey() +
                            ", Value = " + entry.getValue());
                }
                ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                        solicitudNovedadDTO.getTipoTransaccion().name(),
                        solicitudNovedadDTO.getTipoTransaccion().getProceso(),
                        solicitudNovedadDTO.getClasificacion().name(), datosValidacion);
                validarReglasService.execute();
                validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
                solicitudNovedadDTO.setListResultadoValidacion(validaciones);
            } else if (solicitudNovedadDTO.getDatosPersonaMultiple() != null) {
                logger.info("entra a elseif getDatosPersonaMultiple2 - 89526 ");
                    SolicitudNovedadDTO solicitudPersona = new SolicitudNovedadDTO();
                for (DatosPersonaNovedadDTO datosPersona : solicitudNovedadDTO.getDatosPersonaMultiple()) {
                    // Crear un nuevo SolicitudNovedadDTO para cada persona
                    solicitudPersona.setDatosPersona(datosPersona); // Asignar la persona individual
                    solicitudPersona.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
                    solicitudPersona.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
                    solicitudPersona.setTipoTransaccion(solicitudNovedadDTO.getTipoTransaccion());
                    solicitudPersona.setIdSolicitudCargueMasivo(solicitudNovedadDTO.getIdSolicitudCargueMasivo());
                    solicitudPersona.setTarjetaMultiservicio(solicitudNovedadDTO.getTarjetaMultiservicio());

                    Map<String, String> datosValidacion = llenarDatosValidacionPersonaBeneficiario(solicitudPersona);
                    for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {
                        logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    }
                    logger.info("entra getDatosPersonaMultiple getidsolicitud2 - 89526 " + solicitudNovedadDTO.getIdSolicitud());
                    logger.info("entra getDatosPersonaMultiple getidsolicitudNovedad2 - 89526 " + solicitudNovedadDTO.getIdSolicitudNovedad());

                    // Acceder a la clasificación desde datosPersona
                    String clasificacion = datosPersona.getClasificacion() != null ? datosPersona.getClasificacion().name() : null;

                    ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                            solicitudNovedadDTO.getTipoTransaccion().name(), solicitudNovedadDTO.getTipoTransaccion().getProceso(),
                            clasificacion, datosValidacion); // Usar la clasificación de datosPersona
                    logger.info("entra getDatosPersonaMultiple validarReglasService2 - 89526");
                    validarReglasService.execute();
                    logger.info("entra getDatosPersonaMultiple validaciones2 - 89526");
                    validaciones.addAll((List<ValidacionDTO>) validarReglasService.getResult());
                    solicitudNovedadDTO.setListResultadoValidacion(validaciones);
                }
            }
        }
        return solicitudNovedadDTO;
    }

    public String gestionarNovedad(Map<String,Object> datos, UserDTO userDTO){


        logger.info("inicia gestionarNovedad ");
        String tipoArchivoA = (String)datos.get("tipoArchivo");
        logger.info("inicia gestionarNovedad "+ tipoArchivoA);

        TipoArchivoRespuestaEnum tipoArchivo = TipoArchivoRespuestaEnum.valueOf(tipoArchivoA);
        String novedadA = (String)datos.get("tipoTransaccion");
        logger.info("inicia gestionarNovedad "+ novedadA);

        TipoTransaccionEnum novedad = TipoTransaccionEnum.valueOf(novedadA);
        String canalA = (String)datos.get("canal");
        logger.info("inicia gestionarNovedad "+ canalA);

        Long idRol = Long.parseLong(datos.get("idRol").toString());
        logger.info("inicia gestionarNovedad "+ idRol);

           String fechaRecepcionDocumento = (String)datos.get("fechaRecepcionDocumentos").toString();
        Date d = new Date();
        try {
            logger.info("MapsAfiladoTrabajador8" + fechaRecepcionDocumento);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            d = originalFormat.parse(fechaRecepcionDocumento);
        } catch (ParseException ex) {

        }



        CanalRecepcionEnum canal = CanalRecepcionEnum.valueOf(canalA);
        InformacionActualizacionNovedadDTO informacion = new InformacionActualizacionNovedadDTO();
                            AfiliadoModeloDTO afiliadoModelo = new AfiliadoModeloDTO();
                            String tipo = (String) datos.get("TipoIdentificacion");
        logger.info("inicia gestionarNovedad "+ tipo);

                            
        afiliadoModelo.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(tipo));

        String numero = (String) datos.get("NumeroIdentificacion");
        logger.info("inicia gestionarNovedad "+ numero);

        String archivo = (String) datos.get("archivo");
        logger.info("inicia archivo "+ archivo);

                            afiliadoModelo.setNumeroIdentificacion((String) datos.get("NumeroIdentificacion"));
                            // afiliadoModelo.setPrimerApellido("prueba");
                            // afiliadoModelo.setSegundoApellido("actualizacion");
                            // afiliadoModelo.setPrimerNombre("novedad");
                            // afiliadoModelo.setSegundoNombre("xd");
                            informacion.setAfiliado(afiliadoModelo);
                            informacion.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
                            SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(tipoArchivo, novedad,
                            informacion, null, canal,
                            userDTO);
                            solNovedadDTO.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);

                        DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
                        long longDate=d.getTime();
                        String nombreRequisito = "Soporte para novedad cambio de tipo de pensionado";
                        String texto = "Se revisa:<br />-Soporte para novedad cambio de tipo de pensionado.<br />-Firma del trabajador";
                        // ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
                        ItemChequeoDTO itemChequeoDto1 = new ItemChequeoDTO();
                        itemChequeoDto1.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
                        itemChequeoDto1.setIdRequisito(91L);
                        itemChequeoDto1.setNombreRequisito(nombreRequisito);
                        itemChequeoDto1.setTextoAyuda(texto);
                        itemChequeoDto1.setTipoRequisito(TipoRequisitoEnum.ESTANDAR);
                        itemChequeoDto1.setFechaRecepcionDocumentos(longDate);
                        itemChequeoDto1.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
                        itemChequeoDto1.setIdentificadorDocumento(archivo != null ? archivo :"1");
                        itemChequeoDto1.setVersionDocumento(Short.MIN_VALUE);
                        itemChequeoDto1.setCumpleRequisito(Boolean.TRUE);
                
                
                        List<ItemChequeoDTO> itemChequeoDto = new ArrayList<ItemChequeoDTO>();
                        itemChequeoDto.add(itemChequeoDto1);
                        persona.setListaChequeoNovedad(itemChequeoDto);
                        
                
                        logger.info("longDate" + longDate);
                        // listaChequeo.setFechaRecepcionDocumentos(longDate);
                        

                        
                        persona.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
                        persona.setTipoIdentificacionTrabajador(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(tipo));
                        persona.setNumeroIdentificacionTrabajador((String) datos.get("NumeroIdentificacion"));
                        persona.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(tipo));
                        persona.setNumeroIdentificacion((String) datos.get("NumeroIdentificacion"));
                        persona.setIdRolAfiliado(idRol);
                        persona.setEstadoAfiliacionTrabajador(EstadoAfiliadoEnum.ACTIVO);
                        persona.setTipoSolicitanteTrabajador(TipoAfiliadoEnum.PENSIONADO);
                        BigDecimal decimal = new BigDecimal("0");
                        persona.setTarifaPagoAportesPensionado(decimal);

                        solNovedadDTO.setDatosPersona(persona);
                        logger.info("novedadDTO.getDatosPersona().getNumeroIdentificacion()" + solNovedadDTO.getDatosPersona().getNumeroIdentificacion() );
                        logger.info("novedadDTO.getDatosPersona().getNumeroIdentificacion()" + solNovedadDTO.getDatosPersona().getTipoIdentificacion() );
        SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);

        ////bloque lista chequeo
        logger.info("2g solicitud id "+solNovedadDTO.getIdSolicitud());
        ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
                        listaChequeo.setIdSolicitudGlobal(solNovedadDTO.getIdSolicitud());
                        if (solNovedadDTO.getDatosPersona() != null) {
                            listaChequeo.setListaChequeo(itemChequeoDto);
                            // Se verifica si la novedad es asociada a un beneficiario o un afiliado
                            if (solNovedadDTO.getDatosPersona().getIdBeneficiario() != null) {
                                listaChequeo.setNumeroIdentificacion(
                                        solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior());
                                listaChequeo.setTipoIdentificacion(
                                        solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiarioAnterior());
                            } else {
                                listaChequeo.setNumeroIdentificacion(solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
                                listaChequeo.setTipoIdentificacion(solNovedadDTO.getDatosPersona().getTipoIdentificacion());
                            }
                        } else {
                            listaChequeo.setNumeroIdentificacion(solNovedadDTO.getDatosEmpleador().getNumeroIdentificacion());
                            listaChequeo.setTipoIdentificacion(solNovedadDTO.getDatosEmpleador().getTipoIdentificacion());
                            listaChequeo.setListaChequeo(solNovedadDTO.getDatosEmpleador().getListaChequeoNovedad());
                        }
                        if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
                            logger.info("**__**guardarListaChequeo: " + listaChequeo.toString());
                            logger.info("**__**guardarListaChequeo: 2g" + listaChequeo.getIdSolicitudGlobal());
                            guardarListaChequeo(listaChequeo);
                        }
        // radicarSolicitudNovedad(solNovedadDTO, userDTO);
        try {
            // Llamada al método que lanza una excepción
            resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
            solNovedadDTO.setResultadoValidacion(validarNovedad(solNovedadDTO));
            guardarDatosTemporal(solNovedadDTO);
        } catch (Exception e) {
            // Manejo de la excepción
            e.printStackTrace(); // O cualquier otra acción para manejar la excepción
        }
        
        // SolicitudNovedadDTO nuevaNovedad = new SolicitudNovedadDTO();
        // nuevaNovedad.setNovedadCargaArchivoRespuestaSupervivencia(Boolean.TRUE);
        // nuevaNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        // nuevaNovedad.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
        // // nuevaNovedad.setNumeroRadicacion(numeroRadicado);
        // nuevaNovedad.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
        // DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
        // persona.setNumeroIdentificacion(afiliado.getNumeroIdentificación());
        // persona.setTipoIdentificacion(afiliado.getTipoIdentificacion());
        // persona.setTipoSolicitanteTrabajador(TipoAfiliadoEnum.PENSIONADO);
        // persona.setPrimerNombreTrabajador(afiliado.getPrimerNombre());
        // persona.setSegundoNombreTrabajador(afiliado.getSegundoNombre());
        // persona.setPrimerApellidoTrabajador(afiliado.getPrimerApellido());
        // persona.setSegundoApellidoTrabajador(afiliado.getSegundoApellido());
        // persona.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
        // persona.setGeneroTrabajador(afiliado.getGenero());
        // persona.setFechaNacimientoTrabajador(afiliado.getFechaNacimiento());
        // nuevaNovedad.setDatosPersona(persona);
        // logger.info("2g idsolicitud "+ nuevaNovedad.getIdSolicitud());
        return null;
    }

    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     *
     * @param solicitudNovedadDTO datos de la novedad.
     * @param solicitudNovedad    solicitud a modificar.
     */
    private void resolverNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO,
                                          SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws Exception {
        logger.info("**__**inicio resolverNovedadReintegro: " + entityManager);
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.resolverNovedadReintegro(solicitudNovedadDTO, solicitudNovedad, userDTO);
    }

    /**
     * Método que hace la peticion REST al servicio que crea una solicitud de
     * novedad.
     *
     * @param novedadDTO novedad a crearse.
     * @param userDTO    usuario que radica la solicitud.
     */
    private SolicitudNovedadModeloDTO crearSolicitudNovedadReintegro(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
        logger.info("**__** SolicitudNovedadDTO Reintegro::  " + novedadDTO);
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.crearSolicitudNovedadReintegro(novedadDTO, userDTO, entityManager);
    }

    @Override
    public SolicitudNovedadDTO registrarNovedadCertificadoBeneficiario(
            ProcesarNovedadCargueArchivoDTO procesarNovedadDTO, UserDTO userDTO) {
        return procesarNovedadCertificadoBeneficiario(procesarNovedadDTO.getFechaActual(),
                procesarNovedadDTO.getInformacionActualizacionNovedadDTO(), userDTO,
                procesarNovedadDTO.getNumeroRadicado());
    }

    @Override
    public ResultadoValidacionArchivoDTO validarArchivoSupervivencia(ArchivoSupervivenciaDTO archivoSuperVivenciaDTO,
                                                                     UserDTO userDTO) throws Exception {
        return verificarArchivoSupervivencia(archivoSuperVivenciaDTO, userDTO);
    }

    @Override
    public void registrarSupervivenciaPersona(ResultadoSupervivenciaDTO resulDTO, UserDTO userDTO,
                                              String numeroRadicado) throws Exception {
        procesarSupervivenciaPersona(resulDTO, userDTO, numeroRadicado);
    }

    @Override
    public SolicitudNovedadDTO registrarNovedadInactivarBeneficiario(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
                                                                     UserDTO userDTO) {
        return procesarNovedadInactivarBeneficiario(procesarNovedadDTO.getInformacionActualizacionNovedadDTO(), userDTO,
                procesarNovedadDTO.getNumeroRadicado());
    }
    @Asynchronous
    @Override
    public void insercionMonitoreoLogs(String puntoEjecucion, String ubicacion ){
        entityManager.createNamedQuery(NamedQueriesConstants.INSERCION_LOG_MONITOREO_NOVEDADES)
                .setParameter("puntoEjecucion",puntoEjecucion)
                .setParameter("ubicacion",ubicacion)
                .executeUpdate();
    }

    /**
     * GLPI 82800 Gestion Crear Usuario Empleador Masivo
     *
     */
    @Asynchronous
    @Override
    public void validarArchivoEmpleador(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.debug("Inicio validarArchivoEmpleador(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

            // Obtener la información del archivo cargado
            InformacionArchivoDTO archivo;
            try {
                archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            } catch (Exception e) {
                logger.error("Error al obtener archivo cargado: ", e);
                throw e;
            }

            // Registrar el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue;
            try {
                idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            } catch (Exception e) {
                logger.error("Error al crear/actualizar el estado inicial del cargue: ", e);
                throw e;
            }
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Registrar el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_EMPLEADORES);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());

            try {
                registrarConsolaEstado(consolaEstadoCargue);
                logger.debug("Estado registrado en consola con éxito");
            } catch (Exception e) {
                logger.error("Error al registrar estado en consola: ", e);
                throw e;
            }

            // Verificar la estructura y obtener las líneas para procesarlas
            ResultadoValidacionArchivoGestionUsuariosDTO resultDTO;
            try {
                VerificarEstructuraArchivoEmpleador verificarArchivo = new VerificarEstructuraArchivoEmpleador(archivo);
                verificarArchivo.execute();
                resultDTO = verificarArchivo.getResult();
                logger.info("ResultadoValidacionArchivoDTO Para Usuarios Empleador: " + resultDTO.toString());
            } catch (Exception e) {
                logger.error("Error al verificar la estructura del archivo: ", e);
                throw e;
            }

            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;

            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListCrearUsuarioGestion() == null
                    || resultDTO.getListCrearUsuarioGestion().isEmpty())) {

                // Gestionar la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios a crear en if: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en if:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                // Ejecutar la gestión masiva de usuarios
                try {
                    GestionMasivosEmpleador gestionMasivosEmpleador = new GestionMasivosEmpleador(usuarios);
                    gestionMasivosEmpleador.setUserDTO(userDTO);
                    gestionMasivosEmpleador.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosEmpleador.getResult();
                    logger.info("Usuario procesado en if: " + usuarioDTO);
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de empleadores en if: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                // Gestionar la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios a crear en else: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en else:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                // Ejecutar la gestión masiva de usuarios
                try {
                    GestionMasivosEmpleador gestionMasivosEmpleador = new GestionMasivosEmpleador(usuarios);
                    gestionMasivosEmpleador.setUserDTO(userDTO);
                    gestionMasivosEmpleador.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosEmpleador.getResult();
                    logger.info("Usuario procesado en else: " + usuarioDTO);
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de empleadores en else: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }

            // Actualizar el estado del cargue
            try {
                cargue.setEstado(estadoCargue);
                cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
                crearActualizarCargueArchivoActualizacion(cargue);
                logger.debug("Estado del cargue actualizado correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar el estado del cargue: ", e);
                throw e;
            }

            // Actualizar la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            logger.info("Registros Totales Objetivos: " + resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            logger.info("Registros Totales Procesados: " + resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_EMPLEADORES);

            try {
                actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
                logger.debug("Consola de estado del cargue masivo actualizada correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar la consola de estado del cargue masivo: ", e);
                throw e;
            }

            logger.debug("Fin validarArchivoEmpleador(CargueArchivoActualizacionDTO, UserDTO)");

        } catch (Exception e) {
            logger.error("Error en validarArchivoEmpleador(CargueArchivoActualizacionDTO, UserDTO): ", e);
        }
    }

    /**
     * GLPI 82800 Gestion Crear Usuario Persona Masivo
     *
     */
    @Asynchronous
    @Override
    public void validarArchivoPersona(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.debug("Inicio validarArchivoPersona(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

            // Se obtiene la información del archivo cargado
            InformacionArchivoDTO archivo;
            try {
                archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            } catch (Exception e) {
                logger.error("Error al obtener archivo cargado: ", e);
                throw e;
            }

            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue;
            try {
                idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            } catch (Exception e) {
                logger.error("Error al crear/actualizar el estado inicial del cargue: ", e);
                throw e;
            }
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_PERSONAS);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            
            try {
                registrarConsolaEstado(consolaEstadoCargue);
                logger.debug("Estado registrado en consola con éxito");
            } catch (Exception e) {
                logger.error("Error al registrar estado en consola: ", e);
                throw e;
            }

            // Verificamos la estructura y obtenemos las líneas para procesarlas
            ResultadoValidacionArchivoGestionUsuariosDTO resultDTO;
            try {
                VerificarEstructuraArchivoPersona verificarArchivo = new VerificarEstructuraArchivoPersona(archivo);
                verificarArchivo.execute();
                resultDTO = verificarArchivo.getResult();
                logger.info("ResultadoValidacionArchivoDTO Para Usuarios Persona: " + resultDTO.toString());
            } catch (Exception e) {
                logger.error("Error al verificar la estructura del archivo: ", e);
                throw e;
            }

            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;

            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListCrearUsuarioGestion() == null
                    || resultDTO.getListCrearUsuarioGestion().isEmpty())) {
                // Gestionamos la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios persona a crear en el if: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en el if:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                try {
                    GestionMasivosPersona gestionMasivosPersona = new GestionMasivosPersona(usuarios);
                    gestionMasivosPersona.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosPersona.getResult();
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de persona en el if: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                // Gestionamos la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios persona a crear en el else: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en el else:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                try {
                    GestionMasivosPersona gestionMasivosPersona = new GestionMasivosPersona(usuarios);
                    gestionMasivosPersona.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosPersona.getResult();
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de persona en el else: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }

            // Actualizamos el estado del cargue
            try {
                cargue.setEstado(estadoCargue);
                cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
                crearActualizarCargueArchivoActualizacion(cargue);
                logger.debug("Estado del cargue actualizado correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar el estado del cargue: ", e);
                throw e;
            }

            // Actualizamos la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_PERSONAS);
            
            try {
                actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
                logger.debug("Consola de estado del cargue masivo actualizada correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar la consola de estado del cargue masivo: ", e);
                throw e;
            }

            logger.debug("Fin validarArchivoPersona(CargueArchivoActualizacionDTO, UserDTO)");

        } catch (Exception e) {
            // No se propaga la excepción porque el llamado es asincrono
            logger.error("Error en validarArchivoPersona(CargueArchivoActualizacionDTO, UserDTO): ", e);
        }
    }

    /**
     * GLPI 82800 Gestion Crear Usuario CCF Masivo
     *
     */
    @Asynchronous
    @Override
    public void validarArchivoCcf(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.debug("Inicio validarArchivoCcf(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

            // Se obtiene la información del archivo cargado
            InformacionArchivoDTO archivo;
            try {
                archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            } catch (Exception e) {
                logger.error("Error al obtener archivo cargado: ", e);
                throw e;
            }

            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue;
            try {
                idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            } catch (Exception e) {
                logger.error("Error al crear/actualizar el estado inicial del cargue: ", e);
                throw e;
            }
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_CCF);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            
            try {
                registrarConsolaEstado(consolaEstadoCargue);
                logger.debug("Estado registrado en consola con éxito");
            } catch (Exception e) {
                logger.error("Error al registrar estado en consola: ", e);
                throw e;
            }

            // Verificamos la estructura y obtenemos las líneas para procesarlas
            ResultadoValidacionArchivoGestionUsuariosDTO resultDTO;
            try {
                VerificarEstructuraArchivoCcf verificarArchivo = new VerificarEstructuraArchivoCcf(archivo);
                verificarArchivo.execute();
                resultDTO = verificarArchivo.getResult();
                logger.info("ResultadoValidacionArchivoDTO Para Usuarios Persona: " + resultDTO.toString());
            } catch (Exception e) {
                logger.error("Error al verificar la estructura del archivo: ", e);
                throw e;
            }

            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;

            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListCrearUsuarioGestion() == null
                    || resultDTO.getListCrearUsuarioGestion().isEmpty())) {
                // Gestionamos la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios ccf a crear en el if: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en el if:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                try {
                    GestionMasivosCcf gestionMasivosCcf = new GestionMasivosCcf(usuarios);
                    gestionMasivosCcf.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosCcf.getResult();
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de ccf en el if: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                // Gestionamos la información del archivo
                List<UsuarioCCF> usuarios = new ArrayList<>();

                // Procesar cada línea del archivo y validar los campos
                logger.info("Lista de usuarios ccf a crear en el else: " + resultDTO.getListCrearUsuarioGestion());
                for (UsuarioGestionDTO usuarioGestion : resultDTO.getListCrearUsuarioGestion()) {
                    String tipoIdentificacion = usuarioGestion.getTipoIdentificacion();
                    String numIdentificacion = usuarioGestion.getNumIdentificacion();

                    if (tipoIdentificacion != null && !tipoIdentificacion.isEmpty()
                            && numIdentificacion != null && !numIdentificacion.isEmpty()) {
                        UsuarioCCF usuario = new UsuarioCCF();
                        usuario.setTipoIdentificacion(tipoIdentificacion.trim());
                        usuario.setNumIdentificacion(numIdentificacion.trim());

                        usuarios.add(usuario);
                    } else {
                        logger.error("Registro inválido encontrado. Detalles en el else:");
                        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty()) {
                            logger.error("- tipoIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                        if (numIdentificacion == null || numIdentificacion.isEmpty()) {
                            logger.error("- numIdentificacion está vacío o nulo para el registro: " + usuarioGestion);
                        }
                    }
                }

                try {
                    GestionMasivosCcf gestionMasivosCcf = new GestionMasivosCcf(usuarios);
                    gestionMasivosCcf.execute();
                    UsuarioDTO usuarioDTO = (UsuarioDTO) gestionMasivosCcf.getResult();
                } catch (Exception e) {
                    logger.error("Error al ejecutar gestión masiva de ccf en el else: ", e);
                    throw e;
                }

                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }

            // Actualizamos el estado del cargue
            try {
                cargue.setEstado(estadoCargue);
                cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
                crearActualizarCargueArchivoActualizacion(cargue);
                logger.debug("Estado del cargue actualizado correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar el estado del cargue: ", e);
                throw e;
            }

            // Actualizamos la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_CCF);
            
            try {
                actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
                logger.debug("Consola de estado del cargue masivo actualizada correctamente");
            } catch (Exception e) {
                logger.error("Error al actualizar la consola de estado del cargue masivo: ", e);
                throw e;
            }

            logger.debug("Fin validarArchivoCcf(CargueArchivoActualizacionDTO, UserDTO)");

        } catch (Exception e) {
            // No se propaga la excepción porque el llamado es asincrono
            logger.error("Error en validarArchivoCcf(CargueArchivoActualizacionDTO, UserDTO): ", e);
        }
    }

    //Inicio 96686
    @Asynchronous
    @Override
    public void validarArchivoAfiliado(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.info("Inicio validarArchivoAfiliado(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO)
                    .toString();
            // Se obtiene la informacion del archivo cargado
            InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_NOV_CAMBIO_TIPO_NUM_ID_AFILIADO_MASIVO);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            registrarConsolaEstado(consolaEstadoCargue);

            // Se verifica la estructura y se obtiene las lineas para procesarlas
            VerificarEstructuraArchivoAfiliado verificarArchivo = new VerificarEstructuraArchivoAfiliado(archivo);
            verificarArchivo.execute();
            ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
            logger.info("ResultadoValidacionArchivoDTO" + resultDTO.toString());
            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;
            // Procesamos si hay registros válidos en la lista
            if (resultDTO.getListActualizacionInfoNovedad() != null && !resultDTO.getListActualizacionInfoNovedad().isEmpty()) {
                // Se gestiona la información del archivo (solo los válidos)
                gestionarArchivoAfiliado(resultDTO.getListActualizacionInfoNovedad(), userDTO);
                if (resultDTO.getRegistrosConErrores() > 0) {
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                    estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
                } else {
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                    estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
                }
            } else {
                // No hay registros válidos para procesar (todos con errores, o archivo vacío/malformado)
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            }

            // Se actualiza el cargue
            cargue.setEstado(estadoCargue);
            cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
            crearActualizarCargueArchivoActualizacion(cargue);

            // Se actualiza la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_NOV_CAMBIO_TIPO_NUM_ID_AFILIADO_MASIVO);
            actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

            logger.info("Fin validarArchivoAfiliado(CargueArchivoActualizacionDTO, UserDTO)");
        } catch (Exception e) {
            // No se propaga la excepción porque el llamado es asincrono
            logger.error("Error validarArchivoAfiliado(CargueArchivoActualizacionDTO, UserDTO)", e);
        }
    }

    private void gestionarArchivoAfiliado(List<InformacionActualizacionNovedadDTO> listInformacionArchivo,
                                            UserDTO userDTO)
            throws Exception {
        logger.info("Inicio gestionarArchivoAfiliado");
        // Se obtienen los numeros de radicados
        NumeroRadicadoCorrespondenciaDTO numeroRadicadoCorrespondenciaDTO = obtenerNumeroRadicado(
                listInformacionArchivo.size());
        if (numeroRadicadoCorrespondenciaDTO == null) {
            // Si no se obtuvieron radicados no se procesan las novedades
            return;
        }
        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            String numeroRadicado = numeroRadicadoCorrespondenciaDTO.nextValue();
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                ProcesarNovedadCargueArchivoDTO procesarNovedadDTO = new ProcesarNovedadCargueArchivoDTO();
                procesarNovedadDTO.setInformacionActualizacionNovedadDTO(informacionActualizacionNovedadDTO);
                procesarNovedadDTO.setNumeroRadicado(numeroRadicado);
                RegistrarNovedadCambioTipoNumeroDocumentoAfiliado registrarSrv = new RegistrarNovedadCambioTipoNumeroDocumentoAfiliado(
                        procesarNovedadDTO);
                registrarSrv.execute();
                return registrarSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            managedExecutorService.invokeAll(tareasParalelas);
        }
        logger.info("Fin gestionarArchivoAfiliado");
    }

    @Override
    public SolicitudNovedadDTO registrarNovedadCambioTipoNumeroDocumentoAfiliado(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
                                                                     UserDTO userDTO) {
        return procesarNovedadCambioTipoNumeroDocumentoAfiliado(procesarNovedadDTO.getInformacionActualizacionNovedadDTO(), userDTO,
                procesarNovedadDTO.getNumeroRadicado());
    }

    private SolicitudNovedadDTO procesarNovedadCambioTipoNumeroDocumentoAfiliado(
            InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO, String numeroRadicado) {
        logger.info("Inicio procesarNovedadCambioTipoNumeroDocumentoAfiliado");
        // Datos del cargue
        AfiliadoModeloDTO afiliadoCargue = informacionActualizacionNovedadDTO.getAfiliado();
        // Datos de la BD
        ConsultarAfiliadoPrincipal consultarAfiliado = new ConsultarAfiliadoPrincipal(null, null, null, afiliadoCargue.getNumeroIdentificacion(), afiliadoCargue.getTipoIdentificacion(), null);
        consultarAfiliado.execute();
        List<AfiliadoModeloDTO> listAfiliados = consultarAfiliado.getResult();
        AfiliadoModeloDTO afiliadoModeloDTO = null;
        if (listAfiliados != null && !listAfiliados.isEmpty()) {
            afiliadoModeloDTO = listAfiliados.iterator().next();
        }
        if (afiliadoModeloDTO != null) {
            afiliadoCargue.setIdAfiliado(afiliadoModeloDTO.getIdAfiliado());
            afiliadoCargue.setIdPersona(afiliadoModeloDTO.getIdPersona());
            // afiliadoCargue.setIdBeneficiario(afiliadoModeloDTO.getIdBeneficiario());

            // Desde aqui hace la novedad - Registra la novedad
            if (afiliadoModeloDTO.getNumeroIdentificacion().equals(afiliadoCargue.getNumeroIdentificacion())
                && afiliadoModeloDTO.getTipoIdentificacion().equals(afiliadoCargue.getTipoIdentificacion())) {
                // Se registra la novedad
                TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO;
                if (tipoTransaccion == null) {
                    return null;
                }
                List<ClasificacionEnum> listClasificacion = consultarClasificacion(afiliadoCargue.getTipoIdentificacion(), afiliadoCargue.getNumeroIdentificacion());
                ClasificacionEnum clasificacion = listClasificacion.iterator().next();
                ConsultarAfiliadoPorId consultarAfiliadoPorId = new ConsultarAfiliadoPorId(
                        afiliadoModeloDTO.getIdAfiliado());
                consultarAfiliadoPorId.execute();
                informacionActualizacionNovedadDTO.setAfiliado(consultarAfiliadoPorId.getResult());
                informacionActualizacionNovedadDTO.setClasificacion(clasificacion);
                // Se crea la solicitud
                SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(
                    TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL,
                    tipoTransaccion,
                    informacionActualizacionNovedadDTO, 
                    null, 
                    CanalRecepcionEnum.ARCHIVO_ACTUALIZACION, 
                    userDTO);
                solNovedadDTO.setNovedadAsincrona(Boolean.TRUE);
                solNovedadDTO.setNumeroRadicacion(numeroRadicado);
                // Se agrega la informacion de la novedad
                solNovedadDTO.getDatosPersona()
                        .setTipoIdentificacionTrabajador(afiliadoCargue.getTipoIdentificacionNuevo());
                solNovedadDTO.getDatosPersona()
                        .setNumeroIdentificacionTrabajador(afiliadoCargue.getNumeroIdentificacionNuevo());
                // Se radica la solicitud
                return radicarSolicitudNovedad(solNovedadDTO, userDTO);
            }
            logger.info("Fin procesarNovedadCambioTipoNumeroDocumentoAfiliado");
        }
        logger.info("Fin2 procesarNovedadCambioTipoNumeroDocumentoAfiliado");
        return null;
    }

    @Asynchronous
    @Override
    public void validarArchivoBeneficiario(CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        try {
            logger.info("Inicio validarArchivoBeneficiario(CargueArchivoActualizacionDTO, UserDTO)");
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO)
                    .toString();
            // Se obtiene la informacion del archivo cargado
            InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            // Se registra el estado inicial del cargue
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
            cargue.setIdCargueArchivoActualizacion(idCargue);

            // Se registra el estado en la consola
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_NOV_CAMBIO_TIPO_NUM_ID_BEN_MASIVO);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            registrarConsolaEstado(consolaEstadoCargue);

            // Se verifica la estructura y se obtiene las lineas para procesarlas
            VerificarEstructuraArchivoBeneficiario verificarArchivo = new VerificarEstructuraArchivoBeneficiario(archivo);
            verificarArchivo.execute();
            ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
            logger.info("ResultadoValidacionArchivoDTO" + resultDTO.toString());
            EstadoCargueMasivoEnum estadoProcesoMasivo;
            EstadoCargueArchivoActualizacionEnum estadoCargue;
            // Procesamos si hay registros válidos en la lista
            if (resultDTO.getListActualizacionInfoNovedad() != null && !resultDTO.getListActualizacionInfoNovedad().isEmpty()) {
                // Se gestiona la información del archivo (solo los válidos)
                gestionarArchivoBeneficiario(resultDTO.getListActualizacionInfoNovedad(), userDTO);
                if (resultDTO.getRegistrosConErrores() > 0) {
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                    estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
                } else {
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                    estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
                }
            } else {
                // No hay registros válidos para procesar (todos con errores, o archivo vacío/malformado)
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            }

            // Se actualiza el cargue
            cargue.setEstado(estadoCargue);
            cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
            crearActualizarCargueArchivoActualizacion(cargue);

            // Se actualiza la consola
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(estadoProcesoMasivo);
            conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
            conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
            conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
            conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
            conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
            conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
            conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_NOV_CAMBIO_TIPO_NUM_ID_BEN_MASIVO);
            actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

            logger.info("Fin validarArchivoBeneficiario(CargueArchivoActualizacionDTO, UserDTO)");
        } catch (Exception e) {
            // No se propaga la excepción porque el llamado es asincrono
            logger.error("Error validarArchivoBeneficiario(CargueArchivoActualizacionDTO, UserDTO)", e);
        }
    }

    private void gestionarArchivoBeneficiario(List<InformacionActualizacionNovedadDTO> listInformacionArchivo,
                                            UserDTO userDTO)
            throws Exception {
        logger.info("Inicio gestionarArchivoBeneficiario");
        // Se obtienen los numeros de radicados
        NumeroRadicadoCorrespondenciaDTO numeroRadicadoCorrespondenciaDTO = obtenerNumeroRadicado(
                listInformacionArchivo.size());
        if (numeroRadicadoCorrespondenciaDTO == null) {
            // Si no se obtuvieron radicados no se procesan las novedades
            return;
        }
        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            String numeroRadicado = numeroRadicadoCorrespondenciaDTO.nextValue();
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                ProcesarNovedadCargueArchivoDTO procesarNovedadDTO = new ProcesarNovedadCargueArchivoDTO();
                procesarNovedadDTO.setInformacionActualizacionNovedadDTO(informacionActualizacionNovedadDTO);
                procesarNovedadDTO.setNumeroRadicado(numeroRadicado);
                RegistrarNovedadCambioTipoNumeroDocumentoBeneficiario registrarSrv = new RegistrarNovedadCambioTipoNumeroDocumentoBeneficiario(
                        procesarNovedadDTO);
                registrarSrv.execute();
                return registrarSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }
        if (!tareasParalelas.isEmpty()) {
            managedExecutorService.invokeAll(tareasParalelas);
        }
        logger.info("Fin gestionarArchivoBeneficiario");
    }

    @Override
    public SolicitudNovedadDTO registrarNovedadCambioTipoNumeroDocumentoBeneficiario(ProcesarNovedadCargueArchivoDTO procesarNovedadDTO,
                                                                     UserDTO userDTO) {
        return procesarNovedadCambioTipoNumeroDocumentoBeneficiario(procesarNovedadDTO.getInformacionActualizacionNovedadDTO(), userDTO,
                procesarNovedadDTO.getNumeroRadicado());
    }

    private SolicitudNovedadDTO procesarNovedadCambioTipoNumeroDocumentoBeneficiario(
            InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO, String numeroRadicado) {
        logger.info("Inicio procesarNovedadCambioTipoNumeroDocumentoBeneficiario");
        // Datos del cargue
        BeneficiarioModeloDTO beneficiarioCargue = informacionActualizacionNovedadDTO.getBeneficiario();
        // Datos de la BD
        ConsultarBeneficiarioByTipo consultarBeneficiario = new ConsultarBeneficiarioByTipo(new ArrayList<>(), null,
                null, null, null, null,
                beneficiarioCargue.getNumeroIdentificacion(), beneficiarioCargue.getTipoIdentificacion(), null, null,
                Boolean.FALSE);
        consultarBeneficiario.execute();
        List<BeneficiarioModeloDTO> listBeneficiarios = consultarBeneficiario.getResult();
        BeneficiarioModeloDTO beneficiarioModeloDTO = null;
        if (listBeneficiarios != null && !listBeneficiarios.isEmpty()) {
            beneficiarioModeloDTO = listBeneficiarios.iterator().next();
        }
        if (beneficiarioModeloDTO != null) {
            beneficiarioCargue.setIdAfiliado(beneficiarioModeloDTO.getIdAfiliado());
            beneficiarioCargue.setIdPersona(beneficiarioModeloDTO.getIdPersona());
            beneficiarioCargue.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
            // Desde aqui hace la novedad - Registra la novedad
            if (beneficiarioModeloDTO.getNumeroIdentificacion().equals(beneficiarioCargue.getNumeroIdentificacion())
                && beneficiarioModeloDTO.getTipoIdentificacion().equals(beneficiarioCargue.getTipoIdentificacion())) {
                // Se registra la novedad
                TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO;
                if (tipoTransaccion == null) {
                    return null;
                }
                ConsultarAfiliadoPorId consultarAfiliadoPorId = new ConsultarAfiliadoPorId(
                        beneficiarioModeloDTO.getIdAfiliado());
                consultarAfiliadoPorId.execute();
                informacionActualizacionNovedadDTO.setAfiliado(consultarAfiliadoPorId.getResult());
                informacionActualizacionNovedadDTO.setClasificacion(beneficiarioModeloDTO.getTipoBeneficiario());
                // Se crea la solicitud
                SolicitudNovedadDTO solNovedadDTO = construirSolicitudNovedad(
                        TipoArchivoRespuestaEnum.BENEFICIARIO,
                        tipoTransaccion,
                        informacionActualizacionNovedadDTO, 
                        null, 
                        CanalRecepcionEnum.ARCHIVO_ACTUALIZACION, 
                        userDTO);
                solNovedadDTO.setNovedadAsincrona(Boolean.TRUE);
                solNovedadDTO.setNumeroRadicacion(numeroRadicado);
                // Se agrega la informacion de la novedad
                solNovedadDTO.getDatosPersona()
                        .setTipoIdentificacionBeneficiario(beneficiarioCargue.getTipoIdentificacionNuevo());
                solNovedadDTO.getDatosPersona()
                        .setNumeroIdentificacionBeneficiario(beneficiarioCargue.getNumeroIdentificacionNuevo());
                solNovedadDTO.getDatosPersona()
                        .setPrimerNombreBeneficiario(beneficiarioModeloDTO.getPrimerNombre());
                solNovedadDTO.getDatosPersona()
                        .setPrimerApellidoBeneficiario(beneficiarioModeloDTO.getPrimerApellido());
                // Se radica la solicitud
                return radicarSolicitudNovedad(solNovedadDTO, userDTO);
            }
            logger.info("Fin procesarNovedadCambioTipoNumeroDocumentoBeneficiario");
        }
        logger.info("Fin2 procesarNovedadCambioTipoNumeroDocumentoBeneficiario");
        return null;
    }
    //Fin 96686
}
