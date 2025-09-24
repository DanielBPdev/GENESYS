package com.asopagos.afiliaciones.empleadores.web.composite.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.component.UISelectBoolean;

import org.codehaus.jackson.map.ObjectMapper;
import com.asopagos.afiliaciones.clients.ActualizarSolicitud;
import com.asopagos.afiliaciones.clients.BuscarSolicitudPorId;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarEstadoSolicitudAfiliacion;
import com.asopagos.afiliaciones.empleadores.clients.ActualizarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitud;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarRegistrodes;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudesEnProceso;
import com.asopagos.afiliaciones.empleadores.clients.CrearSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.dto.RespuestaConsultaSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.AnalizarSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.CancelacionSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.ConsultarConceptoEscalamientoDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.CorregirInformacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.DiligenciarFormularioAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.GestionarPNCSDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.RadicarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.ReintegroEmpleadorDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.VerificarPNCSDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.VerificarSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.service.AfiliacionEmpleadoresWebCompositeService;
import com.asopagos.aportes.clients.ConsultarAporteDetalladoPorIdsGeneral;
import com.asopagos.aportes.clients.ConsultarAporteGeneralEmpleador;
import com.asopagos.aportes.clients.CrearActualizarAporteGeneral;
import com.asopagos.aportes.clients.CrearAporteDetallado;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.constantes.parametros.clients.ConsultarConstantesCaja;
import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ARLDTO;
import com.asopagos.dto.CodigoCIIUDTO;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.empleadores.clients.ActualizarEmpleador;
import com.asopagos.empleadores.clients.ActualizarEstadoEmpleador;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empleadores.clients.ConsultarUltimaClasificacion;
import com.asopagos.empleadores.clients.CrearEmpleador;
import com.asopagos.empleadores.clients.GuardarDatosTemporalesEmpleador;
import com.asopagos.empleadores.clients.ConsultarInfoTemporalEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.personas.clients.ConsultarUbicacion;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.ActualizarSolicitudEscalada;
import com.asopagos.solicitudes.clients.ConsultarDatosTempPorPersona;
import com.asopagos.solicitudes.clients.ConsultarSolicitudEscalada;
import com.asopagos.solicitudes.clients.EscalarSolicitud;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.EnviarSenal;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ActualizarUsuarioCCF;
import com.asopagos.usuarios.clients.CrearUsuarioAdminEmpleador;
import com.asopagos.usuarios.clients.EliminarTokenAcceso;
import com.asopagos.usuarios.clients.EstaUsuarioActivo;
import com.asopagos.usuarios.clients.GenerarTokenAcceso;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.clients.ReenviarCorreoEnrolamiento;
import com.asopagos.usuarios.dto.InformacionReenvioDTO;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import com.asopagos.validaciones.clients.ValidarEmpleadores;
import com.asopagos.dto.webservices.AfiliacionEmpleadorDTO;
import com.asopagos.dto.infoTemporalEmpleadorDTO;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.dto.InformacionContactoDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.dto.EmpresaDTO;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import java.util.Optional;
import com.google.gson.Gson;
import java.util.Arrays;
/**
 * <b>Descripcion:</b> EJB que implementa los servicios de composición del
 * proceso de afiliación de empleadores WEB
 *
 * @author Jorge Camargo
 * <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 * </a>
 */
@Stateless
public class AfiliacionEmpleadoresWebCompositeBusiness implements AfiliacionEmpleadoresWebCompositeService {

    private static final String INFORMACION_CORREGIDA = "informacionCorregida";

    private static final String FORMULARIO_RADICADO = "formularioRadicado";

    private static final String FORMULARIO_DILIGENCIADO = "formularioDiligenciado";

    private static final String ID_SOLICITUD = "idSolicitud";

    private static final String ID_EMPLEADOR = "idEmpleador";

    private static final String PREFIJO_EMPLEADOR = "emp_";

    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(AfiliacionEmpleadoresWebCompositeBusiness.class);

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#digitarInformacionContacto(com.
	 * asopagos.afiliaciones.empleadores.web.composite.dto.
	 * DigitarInformacionContactoDTO)
     */
    @Override
    public ResultadoRegistroContactoEnum digitarInformacionContacto(DigitarInformacionContactoDTO entrada,
            UserDTO usuarioDTO) {
        String token = generarTokenAccesoCore();
        Empleador empleador = null;
        boolean reintegro = false;
        ResultadoRegistroContactoEnum resultadoServ = null;
        if (!entrada.getEmpleador().getPersona().getNumeroIdentificacion().matches("[0-9]+")) {
            logger.info("ingreso por el if de validacion desentralizada");

            try {
                ConsultarRegistrodes consultades = new ConsultarRegistrodes(entrada.getEmpleador().getPersona().getNumeroIdentificacion());
                // consultades.setToken(token);
                consultades.execute();
                List<PreRegistroEmpresaDesCentralizada> des = consultades.getResult();
                logger.info("esto es lo que retorno la consulta" + des);

                if (des == null || des.isEmpty()) {
                    logger.info("ingreso por el if de validacion ");
                    resultadoServ = ResultadoRegistroContactoEnum.NO_AFILIABLE;

                    return resultadoServ;
                }

            } catch (Exception e) {
                logger.info("ingreso por el catch y esta es la e " + e);
            }

        }
        List<ValidacionDTO> list = validarEmpleador(entrada, token);
        ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
                list);
        if (validacionExistenciaSolicitud != null
                && validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
            resultadoServ = ResultadoRegistroContactoEnum.AFILIACION_EN_PROCESO;
            registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA,
                    TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion());
            return resultadoServ;
        }

        ValidacionDTO validacionExistenciaSolicitudWeb = getValidacion(
                ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_EMPLEADOR, list);
        if (validacionExistenciaSolicitudWeb != null
                && ResultadoValidacionEnum.NO_APROBADA.equals(validacionExistenciaSolicitudWeb.getResultado())) {
            resultadoServ = ResultadoRegistroContactoEnum.AFILIACION_WEB_EN_PROCESO;
            return resultadoServ;
        }

        ValidacionDTO validacionReintegro = getValidacion(ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO, list);
        if (ResultadoValidacionEnum.APROBADA
                .equals(validacionReintegro != null ? validacionReintegro.getResultado() : validacionReintegro)) {
            reintegro = true;
        }

        ValidacionDTO validacionEmpleador = getValidacion(ValidacionCoreEnum.VALIDACION_EMPLEADOR_ACTIVO, list);
        if (validacionEmpleador != null
                && validacionEmpleador.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
            registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
                    TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion());
            resultadoServ = ResultadoRegistroContactoEnum.EMPLEADOR_AFILIADO;
            return resultadoServ;
        } else {

            ValidacionDTO validacionBDCore = getValidacion(ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE, list);
            if (validacionBDCore != null && validacionBDCore.getResultado().equals(ResultadoValidacionEnum.APROBADA)) {
                BuscarEmpleador buscarEmpleador = new BuscarEmpleador(false,
                        entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                        entrada.getEmpleador().getPersona().getTipoIdentificacion(), null);
                buscarEmpleador.setToken(token);

                List<Empleador> result = null;
                buscarEmpleador.execute();
                result = buscarEmpleador.getResult();

                if (result != null && !result.isEmpty()) {
                    empleador = result.get(0);
                    if (reintegro && MotivoDesafiliacionEnum.ANULADO.equals(empleador.getMotivoDesafiliacion())) {
                        reintegro = false;
                    }
                    if (TipoIdentificacionEnum.NIT.equals(empleador.getEmpresa().getPersona().getTipoIdentificacion())
                            && (empleador.getIdEmpleador() != null)) {
                        ConsultarUltimaClasificacion ultimaClasificacion = new ConsultarUltimaClasificacion(
                                empleador.getIdEmpleador());
                        ultimaClasificacion.setToken(token);
                        ultimaClasificacion.execute();
                        ClasificacionEnum clasificacion = ultimaClasificacion.getResult();
                        if (clasificacion != null && !entrada.getTipoEmpleador().equals(clasificacion)) {
                            resultadoServ = ResultadoRegistroContactoEnum.CLASIFICACION_INCORRECTA;
                            return resultadoServ;
                        }
                    }
                }
            } else if (validacionBDCore != null
                    && validacionBDCore.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
                        TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                        entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                        entrada.getEmpleador().getPersona().getTipoIdentificacion());
                resultadoServ = ResultadoRegistroContactoEnum.NO_AFILIABLE;
                return resultadoServ;
            }
            /* Valida si tiene una Cuenta Web Activa */
            String nombreUsuario = PREFIJO_EMPLEADOR + entrada.getEmpleador().getPersona().getTipoIdentificacion().name() + "_"
                    + entrada.getEmpleador().getPersona().getNumeroIdentificacion();
            EstaUsuarioActivo activo = new EstaUsuarioActivo(nombreUsuario);
            activo.setToken(token);
            activo.execute();
            Boolean cuentaActiva = activo.getResult();
            Long idEmpleador = null;
            if (cuentaActiva && reintegro) {
                resultadoServ = ResultadoRegistroContactoEnum.REINTEGRO_ACTIVA;
                return resultadoServ;
            } else if (reintegro) {
                resultadoServ = ResultadoRegistroContactoEnum.REINTEGRO_INACTIVA;
            } else {
                resultadoServ = ResultadoRegistroContactoEnum.NUEVA_AFILIACION;
                if (empleador != null) {
                    idEmpleador = empleador.getIdEmpleador();
                }
            }

            SolicitudAfiliacionEmpleador sae = initSolicitud(idEmpleador, entrada.getTipoEmpleador(), resultadoServ,
                    usuarioDTO);
            Long idSolicitud = null;
            sae.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
            CrearSolicitudAfiliacionEmpleador crearSolic = new CrearSolicitudAfiliacionEmpleador(sae);
            crearSolic.setToken(token);
            crearSolic.execute();
            idSolicitud = crearSolic.getResult();

            if (entrada.getInformacionContacto() != null
                    && entrada.getInformacionContacto().getCorreoElectronico() != null) {
                entrada.setCorreoEmpleadorNuevo(true);
            }

            try {
                enrolar(entrada, usuarioDTO, idEmpleador, idSolicitud, token);
            } catch (Exception e) {
                throw new TechnicalException(e);
            }
            return resultadoServ;
        }
    }

    private List<ValidacionDTO> validarEmpleador(DigitarInformacionContactoDTO entrada, String token) {
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoIdentificacion",
                entrada.getEmpleador().getPersona().getTipoIdentificacion().toString());
        datosValidacion.put("numeroIdentificacion", entrada.getEmpleador().getPersona().getNumeroIdentificacion());
        Short dv = entrada.getEmpleador().getPersona().getDigitoVerificacion();
        datosValidacion.put("digitoVerificacion", dv != null ? String.valueOf(dv) : null);

        ValidarEmpleadores validar = new ValidarEmpleadores("112-110-1", ProcesoEnum.AFILIACION_EMPRESAS_WEB,
                datosValidacion);
        validar.setToken(token);
        validar.execute();
        List<ValidacionDTO> list = validar.getResult();
        return list;
    }

    private void enrolar(DigitarInformacionContactoDTO entrada, UserDTO usuarioDTO, Long idEmpleador, Long idSolicitud,
            String token) throws IOException {

        ConsultarSolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador = new ConsultarSolicitudAfiliacionEmpleador(
                idSolicitud);
        consultarSolicitudAfiliacionEmpleador.setToken(token);
        consultarSolicitudAfiliacionEmpleador.execute();
        SolicitudAfiliacionEmpleador sae = consultarSolicitudAfiliacionEmpleador.getResult();
        RadicarSolicitud r = new RadicarSolicitud(sae.getSolicitudGlobal().getIdSolicitud(), "");
        r.setToken(token);
        r.execute();
        BuscarSolicitudPorId buscarSolicitudPorId = new BuscarSolicitudPorId(sae.getSolicitudGlobal().getIdSolicitud());
        buscarSolicitudPorId.setToken(token);
        buscarSolicitudPorId.execute();
        Solicitud solicitud = buscarSolicitudPorId.getResult();

        TokenDTO tokenG = generarTokenAcesso(entrada, token);
        Long idInstancia = iniciarProceso(idEmpleador, idSolicitud, token, solicitud);
        solicitud.setIdInstanciaProceso(String.valueOf(idInstancia));
        ActualizarSolicitud actualizarSolicitud = new ActualizarSolicitud(solicitud.getIdSolicitud(), solicitud);
        actualizarSolicitud.setToken(token);
        actualizarSolicitud.execute();

        String tokenCodificado = convertirDatosUrlABase64(idSolicitud, idInstancia, tokenG.getToken());
        String url = entrada.getDominio().replace("{token}", tokenCodificado);
        String numeroIdentificacionEmpleador = entrada.getEmpleador().getPersona().getDigitoVerificacion() != null ? entrada.getEmpleador().getPersona().getNumeroIdentificacion() + "-" + entrada.getEmpleador().getPersona().getDigitoVerificacion().toString() : entrada.getEmpleador().getPersona().getNumeroIdentificacion();
        entrada.setDominio(url);
        Map<String, Object> map = new HashMap<>();
        map.put("dto", entrada);
        map.put("token", tokenG.getToken());
        ObjectMapper mapper = new ObjectMapper();
        GuardarDatosTemporalesEmpleador datosTemporalesEmpleador = new GuardarDatosTemporalesEmpleador(
                sae.getSolicitudGlobal().getIdSolicitud(),
                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                entrada.getEmpleador().getPersona().getTipoIdentificacion(), mapper.writeValueAsString(map));
        datosTemporalesEmpleador.setToken(token);
        datosTemporalesEmpleador.execute();
        ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
        consultarConstantesCaja.setToken(token);
        consultarConstantesCaja.execute();
        ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();
        NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
        notificacionParametrizadaDTO
                .setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_ENRL_AFL_EMP_WEB);
        notificacionParametrizadaDTO.setParams(new HashMap<String, String>());
        notificacionParametrizadaDTO.getParams().put("linkRegistro", url);
        notificacionParametrizadaDTO.getParams().put("enlaceDeEnrolamiento", url);
        notificacionParametrizadaDTO.getParams().put("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador);
        notificacionParametrizadaDTO.getParams().put("tipoIdentificacionEmpleador", entrada.getEmpleador().getPersona().getTipoIdentificacion().name());
        notificacionParametrizadaDTO.getParams().put("razonSocial/Nombre",
                entrada.getEmpleador().getPersona().getRazonSocial());
        notificacionParametrizadaDTO.getParams().put("nombreSedeCCF", constantes.getNombre());
        notificacionParametrizadaDTO.getParams().put("ciudadSolicitud", constantes.getIdMunicipio());
        //Se crea la variable que almacena la fecha actual del sistema
        Date fechaDelSistema = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistemaStr = simpleDateFormat.format(fechaDelSistema.getTime());
        notificacionParametrizadaDTO.getParams().put("fechaDelSistema", fechaSistemaStr);
        if(entrada.getEmpleador()!=null && entrada.getEmpleador().getPersona() != null && entrada.getEmpleador().getPersona().getUbicacionDTO() != null && entrada.getEmpleador().getPersona().getUbicacionDTO().getIdMunicipio()!= null){
            notificacionParametrizadaDTO.getParams().put("municipio", entrada.getEmpleador().getPersona().getUbicacionDTO().getIdMunicipio().toString());
        } else {
            notificacionParametrizadaDTO.getParams().put("municipio", constantes.getIdMunicipio());
        }
        notificacionParametrizadaDTO.setIdSolicitud(sae.getSolicitudGlobal().getIdSolicitud());
        notificacionParametrizadaDTO
                .setProcesoEvento(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION.getProceso().name());
        notificacionParametrizadaDTO.setTipoTx(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION);
        notificacionParametrizadaDTO.setIdEmpleador(idEmpleador);
        if (entrada.isCorreoEmpleadorNuevo()) {
            notificacionParametrizadaDTO.setReplantearDestinatarioTO(entrada.isCorreoEmpleadorNuevo());
            if (entrada.getInformacionContacto() != null
                    && entrada.getInformacionContacto().getCorreoElectronico() != null) {
                List<String> lstDestinatario = new ArrayList<>();
                lstDestinatario.add(entrada.getInformacionContacto().getCorreoElectronico());
                notificacionParametrizadaDTO.setDestinatarioTO(lstDestinatario);
            }
        }

        try {
            enviarCorreoParametrizado(notificacionParametrizadaDTO);
        } catch (Exception e) {
            // este es el caso en que el envío del correo del comunicado no debe
            // abortar el proceso de afiliación
            logger.warn("No fue posible enviar el correo con el comunicado, el proceso continuará normalmente");
        }
    }

    private Long iniciarProceso(Long idEmpleador, Long idSolicitud, String token, Solicitud solicitud) {
        String caducidadLink = (String) CacheManager.getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK);
        String caducidadFormulario = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.PARAM_CADUCIDAD_FORMULARIO);
        String plazoRadFormulario = (String) CacheManager
                .getParametro(ParametrosSistemaConstants._112_IMPRIMIR_FORMULARIO_TIMER);
        String tiempoProcesoSolicitud = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AEW_TIEMPO_PROCESO_SOLICITUD);
        String tiempoAsignacionBack = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_AEW_TIEMPO_ASIGNACION_BACK);
        Map<String, Object> datosProceso = new HashMap<>();
        datosProceso.put("caducidadLink", caducidadLink);
        datosProceso.put("numeroRadicado", solicitud.getNumeroRadicacion());
        datosProceso.put("caducidadFormulario", caducidadFormulario);
        datosProceso.put("plazoRadFormulario", plazoRadFormulario);
        datosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        datosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
        datosProceso.put(ID_SOLICITUD, idSolicitud);
        datosProceso.put(ID_EMPLEADOR, idEmpleador);

        IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.AFILIACION_EMPRESAS_WEB, datosProceso);
        iniciarProceso.setToken(token);
        iniciarProceso.execute();
        return iniciarProceso.getResult();
    }

    private void registrarIntentoAfiliacion(Long idSolicitud, CausaIntentoFallidoAfiliacionEnum causa,
            TipoTransaccionEnum tipoTransaccion, String token, String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        IntentoAfiliacionInDTO intentoInput = new IntentoAfiliacionInDTO();
        intentoInput.setCausaIntentoFallido(causa);
        intentoInput.setTipoTransaccion(tipoTransaccion);
        intentoInput.setIdSolicitud(idSolicitud);
        intentoInput.setNumeroIdentificacion(numeroIdentificacion != null ? numeroIdentificacion : null);
        intentoInput.setTipoIdentificacion(tipoIdentificacion != null ? tipoIdentificacion : null);
        RegistrarIntentoAfliliacion r = new RegistrarIntentoAfliliacion(intentoInput);
        r.setToken(token);
        r.execute();
    }

    private void registrarIntentoAfiliacion(Long idSolicitud, CausaIntentoFallidoAfiliacionEnum causa,
            TipoTransaccionEnum tipoTransaccion, String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        IntentoAfiliacionInDTO intentoInput = new IntentoAfiliacionInDTO();
        intentoInput.setCausaIntentoFallido(causa);
        intentoInput.setTipoTransaccion(tipoTransaccion);
        intentoInput.setIdSolicitud(idSolicitud);
        intentoInput.setNumeroIdentificacion(numeroIdentificacion != null ? numeroIdentificacion : null);
        intentoInput.setTipoIdentificacion(tipoIdentificacion != null ? tipoIdentificacion : null);
        RegistrarIntentoAfliliacion r = new RegistrarIntentoAfliliacion(intentoInput);
        r.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#diligenciarFormularioAfiliacion(
	 * DiligenciarFormularioAfiliacionDTO, UserDTO)
     */
    @Override
    public ResultadoRegistroContactoEnum diligenciarFormularioAfiliacion(DiligenciarFormularioAfiliacionDTO entrada, UserDTO usuarioDTO) {
        String token = generarTokenAccesoCore();
        // Se consulta la solicitud de afiliación de empleador
        ConsultarSolicitudAfiliacionEmpleador consultarSolicitud = new ConsultarSolicitudAfiliacionEmpleador(
                entrada.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador());
        consultarSolicitud.setToken(token);
        consultarSolicitud.execute();
        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = consultarSolicitud.getResult();
        // Se verifica si la solicitud sigue aún vigente
        if (EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA.equals(solicitudAfiliacionEmpleador.getEstadoSolicitud())
                || ResultadoProcesoEnum.RECHAZADA.equals(solicitudAfiliacionEmpleador.getSolicitudGlobal().getResultadoProceso())) {
            return ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM;
        }

        ActualizarSolicitudAfiliacionEmpleador actualizar = new ActualizarSolicitudAfiliacionEmpleador(
                entrada.getSolicitudAfiliacion().getIdSolicitudAfiliacionEmpleador(), entrada.getSolicitudAfiliacion());
        actualizar.setToken(token);
        actualizar.execute();

        Long idInstanciaProceso = Long
                .parseLong(entrada.getSolicitudAfiliacion().getSolicitudGlobal().getIdInstanciaProceso());
        EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_EMPRESAS_WEB, FORMULARIO_DILIGENCIADO,
                idInstanciaProceso, entrada.getDatosCorrectos().toString());
        enviarSenal.setToken(token);
        enviarSenal.execute();
        return null;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#radicarSolicitudAfiliacion(com.
	 * asopagos.afiliaciones.empleadores.web.composite.dto.
	 * RadicarSolicitudAfiliacionDTO)
     */
    /**
     * @param entrada
     * @param usuarioDTO
     */
    @Override
    public Map<String, Object> radicarSolicitudAfiliacion(RadicarSolicitudAfiliacionDTO entrada, UserDTO usuarioDTO) {
        String token = generarTokenAccesoCore();

        Map<String, Object> variablesRetorno = new HashMap<>();
        // Se realizan validaciones de negocio
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoIdentificacion",
                entrada.getEmpleador().getPersona().getTipoIdentificacion().toString());
        datosValidacion.put("numeroIdentificacion", entrada.getEmpleador().getPersona().getNumeroIdentificacion());
        Short dv = entrada.getEmpleador().getPersona().getDigitoVerificacion();
        datosValidacion.put("digitoVerificacion", dv != null ? String.valueOf(dv) : null);

        // Se consulta la solicitud de afiliación de empleador
        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = buscarSolicitudAfiliacionEmpleador(entrada.getNumeroRadicado(), token);
        // Se verifica si la solicitud sigue aún vigente
        if (EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA.equals(solicitudAfiliacionEmpleador.getEstadoSolicitud())
                || ResultadoProcesoEnum.RECHAZADA.equals(solicitudAfiliacionEmpleador.getSolicitudGlobal().getResultadoProceso())) {
            variablesRetorno.put(ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.name(), ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.getNombre());
            return variablesRetorno;
        }

        ValidarEmpleadores validar = new ValidarEmpleadores("112-121-1", ProcesoEnum.AFILIACION_EMPRESAS_WEB,
                datosValidacion);
        validar.setToken(token);
        validar.execute();
        List<ValidacionDTO> list = validar.getResult();
        ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
                list);
        if (validacionExistenciaSolicitud != null
                && validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {

            ConsultarSolicitudesEnProceso consultaSolicitud = new ConsultarSolicitudesEnProceso(null,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion());
            consultaSolicitud.setToken(token);
            consultaSolicitud.execute();

            List<SolicitudAfiliacionEmpleador> solicitudes = consultaSolicitud.getResult();
            if (solicitudes != null && !solicitudes.isEmpty()) {

                variablesRetorno.put(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR.name(),
                        ResultadoValidacionEnum.NO_APROBADA.name());
                if (!solicitudes.get(0).getSolicitudGlobal().getNumeroRadicacion()
                        .equals(entrada.getNumeroRadicado())) {

                    SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(
                            entrada.getNumeroRadicado(), EstadoSolicitudAfiliacionEmpleadorEnum.CANCELADA, token);

                    if (solicitud != null) {
                        cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(),
                                EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA, token);

                        // se registra intento de afiliación
                        registrarIntentoAfiliacion(solicitud.getSolicitudGlobal().getIdSolicitud(),
                                CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA,
                                TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                                entrada.getEmpleador().getPersona().getTipoIdentificacion());
                    }
                    // se finaliza la solicitud debido a que tiene mas
                    // solicitudes en proceso
                    abortarProceso(entrada.getIdInstanciaProceso(), token);
                }
                return variablesRetorno;
            }
        }

        SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleador(entrada.getNumeroRadicado(), token);
        if (EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA.equals(solicitud.getEstadoSolicitud())
                || ResultadoProcesoEnum.RECHAZADA.equals(solicitud.getSolicitudGlobal().getResultadoProceso())) {
            variablesRetorno.put(ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.name(), ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.getNombre());
            return variablesRetorno;
        }
        if (solicitud != null) {
            String usuarioBack = null;
            try {
                EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(ProcesoEnum.AFILIACION_EMPRESAS_WEB, null);
                ejecutarAsignacion.setToken(token);
                ejecutarAsignacion.execute();
                usuarioBack = ejecutarAsignacion.getResult();
            } catch (Exception e) {
                usuarioBack = null;

            }
            // Si no se recupero el usuario back para asignar
            if (usuarioBack == null) {
                variablesRetorno.put(ResultadoRegistroContactoEnum.USUARIO_NO_EXISTE.name(), ResultadoRegistroContactoEnum.USUARIO_NO_EXISTE.getNombre());
                return variablesRetorno;
            }
            boolean debeCrearEmpleador = true;
            Long idEmpleador = null;
            BuscarEmpleador buscarEmpleador = new BuscarEmpleador(false,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion(), null);
            buscarEmpleador.setToken(token);
            List<Empleador> result = null;
            buscarEmpleador.execute();
            result = buscarEmpleador.getResult();

            if (result != null && !result.isEmpty()) {
                Empleador empleador = result.get(0);
                if (empleador.getIdEmpleador() != null) {
                    debeCrearEmpleador = false;
                    idEmpleador = result.iterator().next().getIdEmpleador();
                }
            }

            if (debeCrearEmpleador) {
                CrearEmpleador crearEmpleador = new CrearEmpleador(convertEmpleadorDTOToEntity(entrada.getEmpleador()));
                crearEmpleador.setToken(token);
                crearEmpleador.execute();
                idEmpleador = crearEmpleador.getResult();
            }

            solicitud.setIdEmpleador(idEmpleador);
            solicitud.getSolicitudGlobal().setDestinatario(usuarioBack);
            solicitud.getSolicitudGlobal().setSedeDestinatario(usuarioDTO.getSedeCajaCompensacion());
            solicitud.getSolicitudGlobal().setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
            ActualizarSolicitudAfiliacionEmpleador actSolic = new ActualizarSolicitudAfiliacionEmpleador(
                    solicitud.getIdSolicitudAfiliacionEmpleador(), solicitud);
            actSolic.setToken(token);
            actSolic.execute();

            ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(idEmpleador);
            consultarEmpleador.setToken(token);
            consultarEmpleador.execute();
            Empleador e = consultarEmpleador.getResult();
            NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
            notificacionParametrizadaDTO
                    .setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_EMP_WEB);
            ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
            consultarConstantesCaja.execute();
            ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();
            notificacionParametrizadaDTO.setParams(new HashMap<String, String>());
            notificacionParametrizadaDTO.getParams().put("razonSocial", e.getEmpresa().getPersona().getRazonSocial());
            notificacionParametrizadaDTO.getParams().put("numero radicado", entrada.getNumeroRadicado());
            notificacionParametrizadaDTO.getParams().put("nombreCaja", constantes.getNombre());
            notificacionParametrizadaDTO.setIdEmpleador(idEmpleador);
            try {
                enviarCorreoParametrizado(notificacionParametrizadaDTO);
            } catch (Exception ex) {
                // este es el caso en que el envío del correo del comunicado no
                // debe abortar el proceso de afiliación
                logger.warn("No fue posible enviar el correo con el comunicado, el proceso continuará normalmente");
            }

            // Se consulta la solicitud de afiliación de empleador
            solicitudAfiliacionEmpleador = buscarSolicitudAfiliacionEmpleador(entrada.getNumeroRadicado(), token);
            // Se verifica si la solicitud sigue aún vigente
            if (EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA.equals(solicitudAfiliacionEmpleador.getEstadoSolicitud())
                    || ResultadoProcesoEnum.RECHAZADA.equals(solicitudAfiliacionEmpleador.getSolicitudGlobal().getResultadoProceso())) {
                variablesRetorno.put(ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.name(), ResultadoRegistroContactoEnum.TIEMPO_DILIGENCIA_FORM.getNombre());
                return variablesRetorno;
            }

            EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_EMPRESAS_WEB, FORMULARIO_RADICADO,
                    entrada.getIdInstanciaProceso(), usuarioBack);
            enviarSenal.setToken(token);
            enviarSenal.execute();
            cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.RADICADA, token);
            // Se actualiza el estado de la solicitud de afiliación
            cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.ASIGNADA_AL_BACK, token);

            return variablesRetorno;
        } else {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#verificarSolicitud(com.asopagos.
	 * afiliaciones.empleadores.web.composite.dto.VerificarSolicitudDTO)
     */
    @Override
    public void verificarSolicitud(VerificarSolicitudDTO entrada, UserDTO usuarioDTO) {
        switch (entrada.getResultadoVerifBack()) {
            case 1:
                if (entrada.getRequiereAnalisisEsp() == null || !entrada.getRequiereAnalisisEsp()) {
                    SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                            EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA, null);

                    ActualizarEstadoEmpleador actualizarEmpleador = new ActualizarEstadoEmpleador(solicitud.getIdEmpleador(),
                            EstadoEmpleadorEnum.ACTIVO);
                    actualizarEmpleador.execute();

                    // Se realiza el reconocimiento de aportes "Retroactivo
                    // Automático" cuando se activa el empleador -> HU-262
                    ejecutarRetroactivoAutomaticoEmpleador(solicitud.getIdEmpleador());

                    ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(solicitud.getIdEmpleador());
                    consultarEmpleador.execute();
                    Empleador empleador = consultarEmpleador.getResult();
                    if (empleador.getFechaRetiroTotalTrabajadores() == null) {
                        empleador.setFechaRetiroTotalTrabajadores(new Date());
                    }

                    crearUsuarioRepresentanteLegal(empleador, solicitud.getSolicitudGlobal().getIdSolicitud());
                    /* Se modifica el estado a cerrada despues de aprobarse */
                    cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA,
                            null);
                    if (entrada.getSolicitudEscalamiento() != null) {
                        entrada.getSolicitudEscalamiento().setIdSolicitud(solicitud.getSolicitudGlobal().getIdSolicitud());
                    }
                }
                break;
            case 2:
                buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.NO_CONFORME_SUBSANABLE, null);
                break;
            case 3:
                SolicitudAfiliacionEmpleador solicitudRechazada = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(
                        entrada.getNumeroRadicado(), EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA, null);
                /* Se modifica el estado a cerrada despues de rechazarse */
                if (solicitudRechazada != null) {
                    cambiarEstadoSolicitud(solicitudRechazada.getIdSolicitudAfiliacionEmpleador(),
                            EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA, null);
                }
                break;
            default:
                throw new TechnicalException("Resultado verificación Back " + entrada.getResultadoVerifBack() + "no implementado");
        }

        if (entrada.getRequiereAnalisisEsp()) {
            EscalarSolicitud escalarSolicitud = new EscalarSolicitud(entrada.getSolicitudAfiliacion().getSolicitudGlobal().getIdSolicitud(),
                    entrada.getSolicitudEscalamiento());
            escalarSolicitud.execute();
            if (entrada.getResultadoVerifBack() == 1) {
                buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.EN_ANALISIS_ESPECIALIZADO, null);
            }
        }
        Map<String, Object> datosTarea = new HashMap<>();
        datosTarea.put("resultadoVerif1Back", entrada.getResultadoVerifBack());
        if (entrada.getSolicitudEscalamiento() != null) {
            datosTarea.put("analista", entrada.getSolicitudEscalamiento().getDestinatario());
        }
        datosTarea.put("requiereAnalisisEsp", entrada.getRequiereAnalisisEsp());
        datosTarea.put("usuarioBackNoConf", usuarioDTO.getNombreUsuario());
        TerminarTarea terminarTarea = new TerminarTarea(entrada.getIdTarea(), datosTarea);
        terminarTarea.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#gestionarPNCS(com.asopagos.
	 * afiliaciones.empleadores.web.composite.dto.GestionarPNCSDTO)
     */
    @Override
    public void gestionarPNCS(GestionarPNCSDTO entrada, UserDTO usuarioDTO) {
        buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                EstadoSolicitudAfiliacionEmpleadorEnum.NO_CONFORME_GESTIONADA, null);

        Map<String, Object> datosTarea = new HashMap<>();
        datosTarea.put("resultadoGestion", entrada.getResultadoGestion());
        TerminarTarea terminarTarea = new TerminarTarea(entrada.getIdTarea(), datosTarea);
        terminarTarea.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#verificarPNCS(com.asopagos.
	 * afiliaciones.empleadores.web.composite.dto.VerificarPNCSDTO)
     */
    @Override
    public void verificarPNCS(VerificarPNCSDTO entrada, UserDTO usuarioDTO) {
        EscalamientoSolicitudDTO escalamiento = null;
        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = buscarSolicitudAfiliacionEmpleador(
                entrada.getNumeroRadicado(), null);

        if (solicitudAfiliacionEmpleador != null) {
            ConsultarSolicitudEscalada consultarSolicitudEscalada = new ConsultarSolicitudEscalada(
                    solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdSolicitud());
            consultarSolicitudEscalada.execute();
            escalamiento = consultarSolicitudEscalada.getResult();
        }

        switch (entrada.getResultadoVerifGestion()) {
            case 1:
                if (escalamiento == null) {
                    SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                            EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA, null);
                    if (solicitud != null) {
                        ActualizarEstadoEmpleador actualizarEstadoEmpleador = new ActualizarEstadoEmpleador(solicitud.getIdEmpleador(),
                                EstadoEmpleadorEnum.ACTIVO);
                        actualizarEstadoEmpleador.execute();
                        ejecutarRetroactivoAutomaticoEmpleador(solicitud.getIdEmpleador());
                        cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(),
                                EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA, null);
                        ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(solicitud.getIdEmpleador());
                        consultarEmpleador.execute();
                        Empleador empleador = consultarEmpleador.getResult();
                        crearUsuarioRepresentanteLegal(empleador);
                    }
                } else {
                    buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                            EstadoSolicitudAfiliacionEmpleadorEnum.EN_ANALISIS_ESPECIALIZADO, null);
                }
                break;
            case 2:
                SolicitudAfiliacionEmpleador afiliacionEmpleador = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(
                        entrada.getNumeroRadicado(), EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA, null);
                if (afiliacionEmpleador != null) {
                    cambiarEstadoSolicitud(afiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                            EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA, null);
                }
                break;
        }
        Map<String, Object> datosTarea = new HashMap<>();
        datosTarea.put("resultadoVerifGestion", entrada.getResultadoVerifGestion());
        TerminarTarea terminarTarea = new TerminarTarea(entrada.getIdTarea(), datosTarea);
        terminarTarea.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#analizarSolicitud(com.asopagos.
	 * afiliaciones.empleadores.web.composite.dto.AnalizarSolicitudDTO)
     */
    @Override
    public void analizarSolicitud(AnalizarSolicitudDTO entrada, UserDTO usuarioDTO) {
        SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleadorYCambiarEstado(
                entrada.getNumeroRadicado(), EstadoSolicitudAfiliacionEmpleadorEnum.GESTIONADA_POR_ESPECIALISTA, null);
        if (solicitud != null) {
            ActualizarSolicitudEscalada actualizarSolicitudAfiliacionEscalada = new ActualizarSolicitudEscalada(
                    solicitud.getSolicitudGlobal().getIdSolicitud(), entrada.getSolicitudEscalamiento());
            actualizarSolicitudAfiliacionEscalada.execute();
        }
        TerminarTarea terminarTarea = new TerminarTarea(entrada.getIdTarea(), new HashMap<String, Object>());
        terminarTarea.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#consultarConceptoEscalamiento(
	 * com.asopagos.afiliaciones.empleadores.web.composite.dto.
	 * ConsultarConceptoEscalamientoDTO)
     */
    @Override
    public void consultarConceptoEscalamiento(ConsultarConceptoEscalamientoDTO entrada, UserDTO usuarioDTO) {
        SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleador(entrada.getNumeroRadicado(), null);
        if (solicitud == null) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
        }

        switch (entrada.getResultadoEscalamiento()) {
            case 1:
                actualizarEstadoEmpleador(solicitud.getIdEmpleador(), EstadoEmpleadorEnum.ACTIVO);

                ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(solicitud.getIdEmpleador());
                consultarEmpleador.execute();
                Empleador empleador = consultarEmpleador.getResult();
                if (empleador.getFechaRetiroTotalTrabajadores() == null) {
                    empleador.setFechaRetiroTotalTrabajadores(new Date());
                }

                crearUsuarioRepresentanteLegal(empleador);
                cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.APROBADA,
                        null);
                break;
            case 2:
                cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA,
                        null);
                break;
        }

        cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA, null);

        Map<String, Object> datosTarea = new HashMap<>();
        datosTarea.put("resultadoEscalamiento", entrada.getResultadoEscalamiento());
        TerminarTarea terminarTarea = new TerminarTarea(entrada.getIdTarea(), datosTarea);
        terminarTarea.execute();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.
	 * AfiliacionEmpleadoresWebCompositeService#corregirInformacion(com.asopagos
	 * .afiliaciones.empleadores.web.composite.dto.CorregirInformacionDTO)
     */
    @Override
    public void corregirInformacion(CorregirInformacionDTO entrada, UserDTO usuarioDTO) {
        String token = generarTokenAccesoCore();
        switch (entrada.getResultadoGestion()) {
            case 1:
                buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.NO_CONFORME_GESTIONADA, token);
                break;
            case 2:
                buscarSolicitudAfiliacionEmpleadorYCambiarEstado(entrada.getNumeroRadicado(),
                        EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA, token);
                break;

        }

        EnviarSenal enviarSenal = new EnviarSenal(ProcesoEnum.AFILIACION_EMPRESAS_WEB, INFORMACION_CORREGIDA,
                entrada.getIdInstanciaProceso(), entrada.getResultadoGestion().toString() + "i");
        enviarSenal.setToken(token);
        enviarSenal.execute();
    }

    @Override
    public void reenviarCorreoEnrolamiento(DigitarInformacionContactoDTO entrada, UserDTO usuarioDTO) {

        EliminarTokenAcceso eliminarTokenAcceso = new EliminarTokenAcceso(
                entrada.getEmpleador().getPersona().getDigitoVerificacion(), true,
                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                entrada.getEmpleador().getPersona().getTipoIdentificacion());
        eliminarTokenAcceso.execute();

        String token = generarTokenAccesoCore();

        TokenDTO tokenDTO = generarTokenAcesso(entrada, token);

        ConsultarDatosTempPorPersona consultarSolicitudTemp = new ConsultarDatosTempPorPersona(
                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                entrada.getEmpleador().getPersona().getTipoIdentificacion());
        consultarSolicitudTemp.setToken(token);
        consultarSolicitudTemp.execute();
        List<SolicitudAfiliacionEmpleador> resp = consultarSolicitudTemp.getResult();

        if (resp != null && !resp.isEmpty()) {
            InformacionReenvioDTO info = crearInformacionReenvio(entrada, usuarioDTO, resp.get(0), tokenDTO.getToken());
            ReenviarCorreoEnrolamiento correoEnrolamiento = new ReenviarCorreoEnrolamiento(info);
            correoEnrolamiento.setToken(token);
            correoEnrolamiento.execute();
        }
    }

    private TokenDTO generarTokenAcesso(DigitarInformacionContactoDTO entrada, String token) {
        GenerarTokenAcceso generarTokenAcceso = new GenerarTokenAcceso(
                entrada.getEmpleador().getPersona().getDigitoVerificacion(),
                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                entrada.getEmpleador().getPersona().getTipoIdentificacion());
        generarTokenAcceso.setToken(token);
        generarTokenAcceso.execute();
        return generarTokenAcceso.getResult();
    }

    private InformacionReenvioDTO crearInformacionReenvio(DigitarInformacionContactoDTO entrada, UserDTO usuarioDTO,
            SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador, String token) {
        Map<String, String> paramsNotificacion = new HashMap<>();
        SolicitudAfiliacionEmpleador solAfiliacionEmpleador = solicitudAfiliacionEmpleador;
        String tokenCodificado = convertirDatosUrlABase64(solicitudAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador(),
                new Long(solicitudAfiliacionEmpleador.getSolicitudGlobal().getIdInstanciaProceso()), token);
        Long idSolicitud = solAfiliacionEmpleador.getIdSolicitudAfiliacionEmpleador();
        String url = entrada.getDominio().replace("{token}", tokenCodificado);
        ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
        consultarConstantesCaja.setToken(token);
        consultarConstantesCaja.execute();
        ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();
        paramsNotificacion.put("linkRegistro", url);
        paramsNotificacion.put("enlaceDeEnrolamiento", url);
        paramsNotificacion.put("razonSocialEmpleador", entrada.getEmpleador().getPersona().getRazonSocial());
        paramsNotificacion.put("nombreSedeCCF", constantes.getNombre());
        InformacionReenvioDTO info = new InformacionReenvioDTO();
        info.setCorreoDestinatario(entrada.getInformacionContacto().getCorreoElectronico());
        info.setNotificacion(EtiquetaPlantillaComunicadoEnum.NTF_ENRL_AFL_EMP_WEB);
        info.setParametrosNotificacion(paramsNotificacion);
        info.setTipoIdentificacion(entrada.getEmpleador().getPersona().getTipoIdentificacion());
        info.setNumeroIdentificacion(entrada.getEmpleador().getPersona().getNumeroIdentificacion());
        info.setDigitoVerificacion(entrada.getEmpleador().getPersona().getDigitoVerificacion());
        if (idSolicitud != null) {
            info.setIdSolicitud(idSolicitud);
        }
        return info;
    }

    /**
     * @param numeroRadicado
     * @param estado
     * @return
     */
    private SolicitudAfiliacionEmpleador buscarSolicitudAfiliacionEmpleadorYCambiarEstado(String numeroRadicado,
            EstadoSolicitudAfiliacionEmpleadorEnum estado, String token) {

        SolicitudAfiliacionEmpleador solicitud = buscarSolicitudAfiliacionEmpleador(numeroRadicado, token);
        if (solicitud != null) {
            cambiarEstadoSolicitud(solicitud.getIdSolicitudAfiliacionEmpleador(), estado, token);
        }
        return solicitud;

    }

    /**
     * @param numeroRadicado
     * @return
     */
    private SolicitudAfiliacionEmpleador buscarSolicitudAfiliacionEmpleador(String numeroRadicado, String token) {
        ConsultarSolicitud consultarSolicitud = new ConsultarSolicitud(null, null, numeroRadicado, null);
        consultarSolicitud.setToken(token);
        consultarSolicitud.execute();
        List<RespuestaConsultaSolicitudDTO> resp = consultarSolicitud.getResult();
        if (resp != null && !resp.isEmpty()) {
            ConsultarSolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador = new ConsultarSolicitudAfiliacionEmpleador(
                    resp.get(0).getIdSolicitud());
            consultarSolicitudAfiliacionEmpleador.setToken(token);
            consultarSolicitudAfiliacionEmpleador.execute();
            return consultarSolicitudAfiliacionEmpleador.getResult();
        }
        return null;
    }

    /**
     * @param idSolicitud
     * @param estado
     */
    private void cambiarEstadoSolicitud(Long idSolicitud, EstadoSolicitudAfiliacionEmpleadorEnum estado, String token) {
        ActualizarEstadoSolicitudAfiliacion actualizarSolicitud = new ActualizarEstadoSolicitudAfiliacion(idSolicitud,
                estado);
        actualizarSolicitud.setToken(token);
        actualizarSolicitud.execute();

    }

    /**
     * @param idEmpleador
     * @return
     */
    private SolicitudAfiliacionEmpleador initSolicitud(Long idEmpleador, ClasificacionEnum clasificacion,
            ResultadoRegistroContactoEnum resultado, UserDTO user) {
        Solicitud solicitudGlobal = new Solicitud();
        solicitudGlobal.setCanalRecepcion(CanalRecepcionEnum.WEB);
        solicitudGlobal.setClasificacion(clasificacion);
        solicitudGlobal.setUsuarioRadicacion(user.getNombreUsuario());
        solicitudGlobal.setCiudadSedeRadicacion(user.getCiudadSedeCajaCompensacion());
        if (resultado != null && resultado.equals(ResultadoRegistroContactoEnum.NUEVA_AFILIACION)) {
            solicitudGlobal.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION);
        } else {
            solicitudGlobal.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_REINTEGRO);
        }
        SolicitudAfiliacionEmpleador sae = new SolicitudAfiliacionEmpleador();
        sae.setSolicitudGlobal(solicitudGlobal);
        sae.setIdEmpleador(idEmpleador);
        sae.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
        return sae;
    }

    private Empleador convertEmpleadorDTOToEntity(EmpleadorDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setPersona(convertPersonaDTOToEntity(dto.getPersona()));
        empresa.setNombreComercial(dto.getNombreComercial());
        empresa.setFechaConstitucion(dto.getFechaConstitucion());
        empresa.setNaturalezaJuridica(dto.getNaturalezaJuridica());
        empresa.setCodigoCIIU(convertCodigoCIIUDTOToEntity(dto.getCodigoCIIU()));
        empresa.setArl(convertARLDTOToEntity(dto.getArl()));
        empresa.setIdUltimaCajaCompensacion(dto.getIdUltimaCajaCompensacion());
        empresa.setPaginaWeb(dto.getPaginaWeb());
        empresa.setIdPersonaRepresentanteLegal(dto.getIdPersonaRepresentanteLegal());
        empresa.setIdPersonaRepresentanteLegalSuplente(dto.getIdPersonaRepresentanteLegalSuplente());
        empresa.setEspecialRevision(dto.getEspecialRevision());

        Empleador empleador = new Empleador();
        empleador.setIdEmpleador(dto.getIdEmpleador());
        empleador.setMotivoDesafiliacion(dto.getMotivoDesafiliacion());
        empleador.setExpulsionSubsanada(dto.getExpulsionSubsanada());
        empleador.setFechaCambioEstadoAfiliacion(dto.getFechaCambioEstadoAfiliacion());
        empleador.setNumeroTotalTrabajadores(dto.getNumeroTotalTrabajadores());
        empleador.setValorTotalUltimaNomina(dto.getValorTotalUltimaNomina());
        empleador.setMedioDePagoSubsidioMonetario(dto.getMedioDePagoSubsidioMonetario());
        empleador.setEstadoEmpleador(dto.getEstadoEmpleador());
        empleador.setEmpresa(empresa);

        return empleador;
    }

    private Persona convertPersonaDTOToEntity(PersonaDTO dto) {
        if (dto != null) {
            Persona persona = new Persona();
            persona.setPrimerApellido(dto.getPrimerApellido());
            persona.setSegundoApellido(dto.getSegundoApellido());
            persona.setPrimerNombre(dto.getPrimerNombre());
            persona.setSegundoNombre(dto.getSegundoNombre());
            persona.setTipoIdentificacion(dto.getTipoIdentificacion());
            persona.setNumeroIdentificacion(dto.getNumeroIdentificacion());
            persona.setDigitoVerificacion(dto.getDigitoVerificacion());
            persona.setRazonSocial(dto.getRazonSocial());
            return persona;
        }
        return null;
    }

    private CodigoCIIU convertCodigoCIIUDTOToEntity(CodigoCIIUDTO dto) {
        if (dto != null) {
            CodigoCIIU codiguCIIU = new CodigoCIIU();
            codiguCIIU.setCodigo(dto.getCodigo());
            codiguCIIU.setDescripcion(dto.getDescripcion());
            codiguCIIU.setCodigoSeccion(dto.getCodigoSeccion());
            codiguCIIU.setDescripcionSeccion(dto.getDescripcionSeccion());
            codiguCIIU.setCodigoDivision(dto.getCodigoDivision());
            codiguCIIU.setDescripcionDivision(dto.getDescripcionDivision());
            codiguCIIU.setCodigoGrupo(dto.getCodigoGrupo());
            codiguCIIU.setDescripcionGrupo(dto.getCodigoGrupo());
            codiguCIIU.setIdCodigoCIIU(dto.getIdCodigoCIIU());
            return codiguCIIU;
        }
        return null;
    }

    private ARL convertARLDTOToEntity(ARLDTO dto) {
        if (dto != null) {
            ARL arl = new ARL();
            arl.setIdARL(dto.getIdARL());
            arl.setNombre(dto.getNombre());
            return arl;
        }
        return null;
    }

    /**
     * Metodo que se encarga de filtrar las validaciones
     *
     * @param validacion
     * @param lista
     * @return
     */
    private ValidacionDTO getValidacion(ValidacionCoreEnum validacion, List<ValidacionDTO> lista) {
        for (ValidacionDTO validacionAfiliacionDTO : lista) {
            if (validacionAfiliacionDTO.getValidacion().equals(validacion)) {
                return validacionAfiliacionDTO;
            }
        }
        return null;
    }

    private String convertirDatosUrlABase64(Long idSolicitud, Long idInstanciaProceso, String token) {
        String json = "{\"idSolicitud\":" + idSolicitud + ",\"idInstanciaProceso\":" + idInstanciaProceso
                + ",\"token\":\"" + token + "\"}";
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    private String generarTokenAccesoCore() {
        GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
        accesoCore.execute();
        TokenDTO token = accesoCore.getResult();
        return token.getToken();
    }

    @Override
    public Map<String, Object> reintegrarEmpleador(ReintegroEmpleadorDTO entrada, UserDTO userDTO) {
        String token = generarTokenAccesoCore();
        ResultadoRegistroContactoEnum contactoEnum = entrada.getDecision()
                ? ResultadoRegistroContactoEnum.NUEVA_AFILIACION : ResultadoRegistroContactoEnum.REINTEGRO_ACTIVA;
        SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = initSolicitud(entrada.getIdEmpleador(),
                entrada.getTipoEmpleador(), contactoEnum, userDTO);
        CrearSolicitudAfiliacionEmpleador crearSolicitudAfiliacionEmpleador = new CrearSolicitudAfiliacionEmpleador(
                solicitudAfiliacionEmpleador);
        crearSolicitudAfiliacionEmpleador.setToken(token);
        crearSolicitudAfiliacionEmpleador.execute();
        Long idSolicitud = crearSolicitudAfiliacionEmpleador.getResult();
        ConsultarSolicitudAfiliacionEmpleador consultarSolicitud = new ConsultarSolicitudAfiliacionEmpleador(
                idSolicitud);
        consultarSolicitud.setToken(token);
        consultarSolicitud.execute();
        SolicitudAfiliacionEmpleador sol = consultarSolicitud.getResult();
        RadicarSolicitud radicarSolicitud = new RadicarSolicitud(sol.getSolicitudGlobal().getIdSolicitud(),
                userDTO.getSedeCajaCompensacion());
        radicarSolicitud.setToken(token);
        radicarSolicitud.execute();
        consultarSolicitud.execute();
        sol = consultarSolicitud.getResult();
        sol.getSolicitudGlobal().setCanalRecepcion(CanalRecepcionEnum.WEB);
        ActualizarSolicitudAfiliacionEmpleador act = new ActualizarSolicitudAfiliacionEmpleador(
                sol.getIdSolicitudAfiliacionEmpleador(), sol);
        act.execute();
        ConsultarEmpleador empl = new ConsultarEmpleador(sol.getIdEmpleador());
        empl.execute();
        EmpleadorDTO empleador = EmpleadorDTO.convertEmpleadorToDTO(empl.getResult());
        empleador.setFechaCambioEstadoAfiliacion(new Date());
        ActualizarEmpleador actualizarEmpleador = new ActualizarEmpleador(sol.getIdEmpleador(), empleador);
        actualizarEmpleador.execute();

        if (entrada.getDecision()) {
            GuardarDatosTemporalesEmpleador datosTemporalesEmpleador = new GuardarDatosTemporalesEmpleador(
                    sol.getSolicitudGlobal().getIdSolicitud(),
                    empleador.getEmpresa().getPersona().getNumeroIdentificacion(),
                    empleador.getEmpresa().getPersona().getTipoIdentificacion(), entrada.getDatosTemporales());
            datosTemporalesEmpleador.setToken(token);
            datosTemporalesEmpleador.execute();
            Long idInstancia = iniciarProceso(entrada.getIdEmpleador(), idSolicitud, token, sol.getSolicitudGlobal());
            sol.getSolicitudGlobal().setIdInstanciaProceso(idInstancia.toString());

            ActualizarSolicitudAfiliacionEmpleador actualizarSolicitudAfiliacionEmpleador = new ActualizarSolicitudAfiliacionEmpleador(
                    sol.getIdSolicitudAfiliacionEmpleador(), sol);
            actualizarSolicitudAfiliacionEmpleador.setToken(token);
            actualizarSolicitudAfiliacionEmpleador.execute();
        } else {

            ActualizarEstadoEmpleador actualizarEstadoEmpleador = new ActualizarEstadoEmpleador(
                    entrada.getIdEmpleador(), EstadoEmpleadorEnum.ACTIVO);
            actualizarEstadoEmpleador.setToken(token);
            actualizarEstadoEmpleador.execute();

            sol.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.APROBADA);
            ActualizarSolicitudAfiliacionEmpleador actualizarSolicitudAfiliacionEmpleador = new ActualizarSolicitudAfiliacionEmpleador(
                    sol.getIdSolicitudAfiliacionEmpleador(), sol);
            actualizarSolicitudAfiliacionEmpleador.setToken(token);
            actualizarSolicitudAfiliacionEmpleador.execute();

            ActualizarEstadoSolicitudAfiliacion actualizarEstadoSolicitudAfiliacion = new ActualizarEstadoSolicitudAfiliacion(
                    idSolicitud, EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
            actualizarEstadoSolicitudAfiliacion.setToken(token);
            actualizarEstadoSolicitudAfiliacion.execute();

            notificar(entrada, userDTO, token, sol.getSolicitudGlobal().getIdSolicitud());

        }
        Map<String, Object> retorno = new HashMap<>();
        retorno.put("numeroRadicado", sol.getSolicitudGlobal().getNumeroRadicacion());
        retorno.put(ID_SOLICITUD, idSolicitud);
        return retorno;
    }

    private void notificar(ReintegroEmpleadorDTO entrada, UserDTO userDTO, String token, Long idSolicitud) {
        ConsultarConstantesCaja consultarConstantesCaja = new ConsultarConstantesCaja();
        consultarConstantesCaja.setToken(token);
        ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(entrada.getIdEmpleador());
        consultarEmpleador.setToken(token);
        consultarEmpleador.execute();
        Empleador emp = consultarEmpleador.getResult();
        consultarConstantesCaja.execute();
        ConstantesCajaCompensacionDTO constantes = consultarConstantesCaja.getResult();
        Map<String, String> paramsNotif = new HashMap<>();
        String razonSocial = "";
        Persona p = emp.getEmpresa().getPersona();
        if (p.getRazonSocial() == null) {
            razonSocial = p.getPrimerNombre() + " " + p.getSegundoNombre() + " " + p.getPrimerApellido() + " "
                    + p.getSegundoApellido();
        } else {
            razonSocial = p.getRazonSocial();
        }
        paramsNotif.put("nombreCCF", constantes.getNombre());
        paramsNotif.put("razonSocial", razonSocial);

        enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum.CRT_ACP_EMP, paramsNotif,
                TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_REINTEGRO, null, idSolicitud, p.getIdPersona(), entrada.getIdEmpleador());
        enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum.CRT_BVD_EMP, paramsNotif,
                TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_REINTEGRO, null, idSolicitud, p.getIdPersona(), entrada.getIdEmpleador());

        String nombreUsuario = PREFIJO_EMPLEADOR + emp.getEmpresa().getPersona().getTipoIdentificacion().toString() + "_"
                + emp.getEmpresa().getPersona().getNumeroIdentificacion();
        ObtenerDatosUsuarioCajaCompensacion oducc = new ObtenerDatosUsuarioCajaCompensacion(nombreUsuario, null, null,
                true);
        oducc.execute();
        UsuarioCCF usuarioCCF = oducc.getResult();
        usuarioCCF.setReintegro(true);
        usuarioCCF.setUsuarioActivo(true);
        ActualizarUsuarioCCF actualizarUsuarioCCF = new ActualizarUsuarioCCF(usuarioCCF);
        actualizarUsuarioCCF.execute();
    }

    /**
     * Método encargado de centralizar la invocación al micro servicio de
     * ActualizarEstadoEmpleador
     *
     * @param idEmpleador
     * @param estadoEmpleador
     */
    private void actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum estadoEmpleador) {
        ActualizarEstadoEmpleador actualizarEstadoEmpleador = new ActualizarEstadoEmpleador(idEmpleador,
                estadoEmpleador);
        actualizarEstadoEmpleador.execute();
    }

    /**
     * Método encargado de llamar el cliente del servicio envio de correo
     * parametrizado
     *
     * @param notificacion, notificación dto que contiene la información del
     * correo
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        logger.debug("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
        logger.debug("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
    }

    /**
     * Método encargado de construir el envio de un comunicado
     *
     * @param etiquetaPlantillaComunicado, Etiqueta que sera enviada en el
     * comunicado
     * @param paramsComunicado, lista de parametros del comunicado
     * @param procesoEvento, Tipo de transaccion que se realiza
     * @param idInstanciaProceso, id Instancia del proceso
     * @param idSolicitud, id de la solicitud
     */
    private void enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado,
            Map<String, String> paramsComunicado, TipoTransaccionEnum tipoTransaccion, String idInstanciaProceso,
            Long idSolicitud, Long idPersona, Long idEmpleador) {
        logger.debug(
                "Inicia enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum,Map<String, String>,TipoTransaccionEnum,String,Long)");
        try {
            NotificacionParametrizadaDTO notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
            notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(etiquetaPlantillaComunicado);
            notificacionParametrizadaDTO.setParams(paramsComunicado);
            notificacionParametrizadaDTO.setIdInstanciaProceso(idInstanciaProceso);
            notificacionParametrizadaDTO.setIdSolicitud(idSolicitud);
            notificacionParametrizadaDTO.setProcesoEvento(tipoTransaccion.getProceso().name());
            notificacionParametrizadaDTO.setTipoTx(tipoTransaccion);
            notificacionParametrizadaDTO.setIdPersona(idPersona);
            notificacionParametrizadaDTO.setIdEmpleador(idEmpleador);
            enviarCorreoParametrizado(notificacionParametrizadaDTO);
        } catch (Exception e) {
            // este es el caso en que el envío del correo del comunicado no debe
            // abortar el proceso de afiliación
            // TODO Mostrar solo el log o persistir el error la bd ?
            logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
        }
        logger.debug(
                "Finaliza enviarComunicadoConstruido(EtiquetaPlantillaComunicadoEnum,Map<String, String>,TipoTransaccionEnum,String,Long)");
    }

    /**
     * Metodo que se encarga de abortar un proceso de afiliación en el bpm
     *
     * @param idInstanciaProceso
     */
    private void abortarProceso(Long idInstanciaProceso, String token) {
        logger.debug("Inicia abortarProceso(Long idInstanciaProceso)");
        AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL, idInstanciaProceso);
        aborProceso.setToken(token);
        aborProceso.execute();
        logger.debug("Finaliza abortarProceso(Long idInstanciaProceso)");
    }

    /*
	 * (non-Javadoc)
	 * @see com.asopagos.afiliaciones.empleadores.web.composite.service.AfiliacionEmpleadoresWebCompositeService#cancelarSolicitudEmpleadoresWebTimeout(com.asopagos.afiliaciones.empleadores.web.composite.dto.CancelacionSolicitudDTO)
     */
    @Override
    public void cancelarSolicitudEmpleadoresWebTimeout(CancelacionSolicitudDTO cancelacion) {
        logger.debug("Inicio el método cancelacionEmpresasWeb");

        ActualizarEstadoSolicitudAfiliacion estadoSolicitud = new ActualizarEstadoSolicitudAfiliacion(
                cancelacion.getIdSolicitud(), EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);
        estadoSolicitud.execute();

        ActualizarEstadoSolicitudAfiliacion estadoSolicitudCerrada = new ActualizarEstadoSolicitudAfiliacion(
                cancelacion.getIdSolicitud(), EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
        estadoSolicitudCerrada.execute();

        ConsultarSolicitudAfiliacionEmpleador consultarSolicitud = new ConsultarSolicitudAfiliacionEmpleador(
                cancelacion.getIdSolicitud());
        consultarSolicitud.execute();
        Long idSolicitudGlobal = consultarSolicitud.getResult().getSolicitudGlobal().getIdSolicitud();

        String numeroIdentificacion = null;
        TipoIdentificacionEnum tipoIdentificacion = null;
        ConsultarInfoTemporalEmpleador consultTemporal = new ConsultarInfoTemporalEmpleador(idSolicitudGlobal);
        consultTemporal.execute();
        DatoTemporalSolicitud dataTemporal = consultTemporal.getResult();
        // DataTemporalDTO guardarTemporal = new DataTemporalDTO();
        if (dataTemporal != null && dataTemporal.getTipoIdentificacion() != null
                && dataTemporal.getNumeroIdentificacion() != null) {
            tipoIdentificacion = dataTemporal.getTipoIdentificacion();
            numeroIdentificacion = dataTemporal.getNumeroIdentificacion();
        }

        //Se consultan los datos del empleador
        /*try {
	        	ObjectMapper mapper = new ObjectMapper();
	            guardarTemporal = mapper.readValue(dataTemporal, DataTemporalDTO.class);
	            
	            if (guardarTemporal.getDto().getEmpleador() != null) {
	            	tipoIdentificacion = guardarTemporal.getDto().getEmpleador().getPersona().getTipoIdentificacion();
	    			numeroIdentificacion = guardarTemporal.getDto().getEmpleador().getPersona().getNumeroIdentificacion();
				}
			} catch (JsonProcessingException e) {
				logger.error("Finaliza el método cancelacionEmpresasWeb");
	            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			} catch (JsonParseException e) {
				logger.error("Finaliza el método cancelacionEmpresasWeb");
	            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			} catch (JsonMappingException e) {
				logger.error("Finaliza el método cancelacionEmpresasWeb");
	            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			} catch (IOException e) {
				logger.error("Finaliza el método cancelacionEmpresasWeb");
	            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}*/
        registrarIntentoAfiliacion(idSolicitudGlobal,
                CausaIntentoFallidoAfiliacionEnum.DESISTIMIENTO_AUTOMATICO_EMPLEADOR,
                TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, numeroIdentificacion, tipoIdentificacion);
        logger.debug("Finaliza el método cancelacionEmpresasWeb");
    }

    /**
     * Método que realiza la ejecución del retroactivo automático para un
     * empleador
     *
     * @param idEmpleador Identificador del empleador
     */
    private void ejecutarRetroactivoAutomaticoEmpleador(Long idEmpleador) {
        logger.debug("Inicia método ejecutarRetroactivoAutomaticoEmpleador");

        // Consulta y actualiza "AporteGeneral" del empleador, a la fecha actual
        List<Long> listaIdsAporteGeneral = new ArrayList<>();
        List<AporteGeneralModeloDTO> listaAporteGeneralDTO = consultarAporteGeneralEmpleador(idEmpleador,
                EstadoAporteEnum.VIGENTE, EstadoRegistroAporteEnum.RELACIONADO);

        for (AporteGeneralModeloDTO aporteGeneralDTO : listaAporteGeneralDTO) {
            listaIdsAporteGeneral.add(aporteGeneralDTO.getId());
            aporteGeneralDTO.setFechaReconocimiento(new Date().getTime());
            //Ajuste pruebas Mauricio GLPI 59039
            if (aporteGeneralDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO")) {
                aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.RELACIONADO);
            } else {
                aporteGeneralDTO.setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum.REGISTRADO);
            }
            aporteGeneralDTO
                    .setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
            crearActualizarAporteGeneral(aporteGeneralDTO);
        }

        // Consulta y actualiza "AporteDetallado" de los trabajadores del
        // empleador, a la fecha actual
        List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO = consultarAporteDetalladoPorIdsGeneral(
                listaIdsAporteGeneral, EstadoAporteEnum.VIGENTE, EstadoRegistroAporteEnum.RELACIONADO);

        for (AporteDetalladoModeloDTO aporteDetalladoDTO : listaAporteDetalladoDTO) {
            //Ajuste pruebas Mauricio GLPI 59039
            if (aporteDetalladoDTO.getMarcaPeriodo().name().equals("PERIODO_FUTURO")) {
                aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.RELACIONADO);
            } else {
                aporteDetalladoDTO.setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum.REGISTRADO);
            }
            aporteDetalladoDTO.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO);
            aporteDetalladoDTO.setFechaMovimiento(new Date().getTime());
            crearActualizarAporteDetallado(aporteDetalladoDTO);
        }

        logger.debug("Finaliza método ejecutarRetroactivoAutomaticoEmpleador");
    }

    /**
     * Método que consulta la lista de aportes generales por empleador
     *
     * @param idEmpleador Identificador del empleador
     * @param estadoAporteAportante Estado del aporte
     * @param estadoRegistroAporte Estado del registro del aporte
     * @return La lista de aportes nivel 1 del empleador
     */
    private List<AporteGeneralModeloDTO> consultarAporteGeneralEmpleador(Long idEmpleador,
            EstadoAporteEnum estadoAporteAportante, EstadoRegistroAporteEnum estadoRegistroAporte) {
        logger.debug("Inicia método consultarAporteGeneralEmpleador");
        ConsultarAporteGeneralEmpleador service = new ConsultarAporteGeneralEmpleador(idEmpleador,
                estadoAporteAportante, estadoRegistroAporte);
        service.execute();
        logger.debug("Inicia método consultarAporteGeneralEmpleador");
        return service.getResult();
    }

    /**
     * Método que consulta la lista de aportes detallados por ids de aportes
     * generales
     *
     * @param listaIdAporteGeneral Lista de ids de <code>AporteGeneral</code>
     * @param estadoAporteAportante Estado del aporte
     * @param estadoRegistroAporte Estado del registro del aporte
     * @return La lista de aportes nivel 2 asociados
     */
    private List<AporteDetalladoModeloDTO> consultarAporteDetalladoPorIdsGeneral(List<Long> listaIdAporteGeneral,
            EstadoAporteEnum estadoAporteAportante, EstadoRegistroAporteEnum estadoRegistroAporte) {
        logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
        ConsultarAporteDetalladoPorIdsGeneral service = new ConsultarAporteDetalladoPorIdsGeneral(estadoAporteAportante,
                estadoRegistroAporte, listaIdAporteGeneral);
        service.execute();
        logger.debug("Inicia método consultarAporteDetalladoPorIdsGeneral");
        return service.getResult();
    }

    /**
     * Método que crea o actualiza un registro en la tabla
     * <code>AporteGeneral</code>
     *
     * @param aporteGeneralDTO Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
        logger.debug("Inicia método crearActualizarAporteGeneral");
        CrearActualizarAporteGeneral service = new CrearActualizarAporteGeneral(aporteGeneralDTO);
        service.execute();
        Long id = service.getResult();
        logger.debug("Inicia método crearActualizarAporteGeneral");
        return id;
    }

    /**
     * Método que invoca el servicio de creación o actualización de un registro
     * en <code>AporteDetallado</code>
     *
     * @param aporteDetalladoDTO Datos del registro a modificar
     * @return El identificador del registro actualizado
     */
    private Long crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
        logger.debug("Inicia método crearActualizarDevolucionAporte");
        CrearAporteDetallado service = new CrearAporteDetallado(aporteDetalladoDTO);
        service.execute();
        Long id = service.getResult();
        logger.debug("Inicia método crearActualizarDevolucionAporte");
        return id;
    }

    private void crearUsuarioRepresentanteLegal(Empleador empleador) {
        Long idRepresentaLegal = empleador.getEmpresa().getIdPersonaRepresentanteLegal();
        if (idRepresentaLegal != null) {
            ConsultarPersona consultarPersonaService = new ConsultarPersona(idRepresentaLegal);
            consultarPersonaService.execute();
            PersonaModeloDTO persona = consultarPersonaService.getResult();
            UsuarioEmpleadorDTO usuarioEmpleadorDTO = new UsuarioEmpleadorDTO();
            if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
                ConsultarUbicacion consultarUbicacion = new ConsultarUbicacion(
                        empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                consultarUbicacion.execute();
                UbicacionDTO ubicacion = consultarUbicacion.getResult();
                if (ubicacion.getCorreoElectronico() != null) {
                    usuarioEmpleadorDTO.setEmail(ubicacion.getCorreoElectronico());
                    usuarioEmpleadorDTO.setPrimerApellido(persona.getPrimerApellido());
                    usuarioEmpleadorDTO.setPrimerNombre(persona.getPrimerNombre());
                    usuarioEmpleadorDTO
                            .setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                    usuarioEmpleadorDTO.setNumIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                    CrearUsuarioAdminEmpleador crearUsuarioAdminEmpleador = new CrearUsuarioAdminEmpleador(usuarioEmpleadorDTO);
                    crearUsuarioAdminEmpleador.execute();
                } else {
                    logger.warn("No se ingreso el email del representate legal, no se crea cuenta para empleador");
                }
            }
        }
    }

    private void crearUsuarioRepresentanteLegal(Empleador empleador, Long idSolicitud) {
        Long idRepresentaLegal = empleador.getEmpresa().getIdPersonaRepresentanteLegal();
        if (idRepresentaLegal != null) {
            ConsultarPersona consultarPersonaService = new ConsultarPersona(idRepresentaLegal);
            consultarPersonaService.execute();
            PersonaModeloDTO persona = consultarPersonaService.getResult();
            UsuarioEmpleadorDTO usuarioEmpleadorDTO = new UsuarioEmpleadorDTO();
            if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
                ConsultarUbicacion consultarUbicacion = new ConsultarUbicacion(
                        empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                consultarUbicacion.execute();
                UbicacionDTO ubicacion = consultarUbicacion.getResult();
                if (ubicacion.getCorreoElectronico() != null) {
                    usuarioEmpleadorDTO.setEmail(ubicacion.getCorreoElectronico());
                    usuarioEmpleadorDTO.setPrimerApellido(persona.getPrimerApellido());
                    usuarioEmpleadorDTO.setPrimerNombre(persona.getPrimerNombre());
                    usuarioEmpleadorDTO
                            .setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                    usuarioEmpleadorDTO.setNumIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                    usuarioEmpleadorDTO.setIdSolicitudGlobal(idSolicitud);
                    CrearUsuarioAdminEmpleador crearUsuarioAdminEmpleador = new CrearUsuarioAdminEmpleador(usuarioEmpleadorDTO);
                    crearUsuarioAdminEmpleador.execute();
                } else {
                    logger.warn("No se ingreso el email del representate legal, no se crea cuenta para empleador");
                }
            }
        }
    }

    @Override
    public Map<String,Object> digitarYRadicarSolicitudAfiliacionWS(AfiliacionEmpleadorDTO empleadorDTO,UserDTO userDTO){
        String token = generarTokenAccesoCore();
        Empleador empleador = null;
        boolean reintegro = false;
        DigitarInformacionContactoDTO entrada = construirInformacionContactoDTO(empleadorDTO);
        Map<String,Object> resultadoServ = new HashMap<String,Object>();
        if (!entrada.getEmpleador().getPersona().getNumeroIdentificacion().matches("[0-9]+")) {
            logger.info("ingreso por el if de validacion desentralizada");

            try {
                ConsultarRegistrodes consultades = new ConsultarRegistrodes(entrada.getEmpleador().getPersona().getNumeroIdentificacion());
                // consultades.setToken(token);
                consultades.execute();
                List<PreRegistroEmpresaDesCentralizada> des = consultades.getResult();
                logger.info("esto es lo que retorno la consulta" + des);

                if (des == null || des.isEmpty()) {
                    logger.info("ingreso por el if de validacion ");
                    resultadoServ.put("validacion", ResultadoRegistroContactoEnum.NO_AFILIABLE);
                    return resultadoServ;
                }

            } catch (Exception e) {
                logger.info("ingreso por el catch y esta es la e " + e);
            }

        }
        List<ValidacionDTO> list = validarEmpleador(entrada, token);
        ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
                list);
        if (validacionExistenciaSolicitud != null
                && validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
            resultadoServ.put("validacion", ResultadoRegistroContactoEnum.AFILIACION_EN_PROCESO);
            registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA,
                    TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion());
            return resultadoServ;
        }

        ValidacionDTO validacionExistenciaSolicitudWeb = getValidacion(
                ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_EMPLEADOR, list);
        if (validacionExistenciaSolicitudWeb != null
                && ResultadoValidacionEnum.NO_APROBADA.equals(validacionExistenciaSolicitudWeb.getResultado())) {
            resultadoServ.put("validacion", ResultadoRegistroContactoEnum.AFILIACION_WEB_EN_PROCESO);
            return resultadoServ;
        }

        ValidacionDTO validacionReintegro = getValidacion(ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO, list);
        if (ResultadoValidacionEnum.APROBADA
                .equals(validacionReintegro != null ? validacionReintegro.getResultado() : validacionReintegro)) {
            reintegro = true;
        }

        ValidacionDTO validacionEmpleador = getValidacion(ValidacionCoreEnum.VALIDACION_EMPLEADOR_ACTIVO, list);
        if (validacionEmpleador != null
                && validacionEmpleador.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
            registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
                    TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                    entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                    entrada.getEmpleador().getPersona().getTipoIdentificacion());
            resultadoServ.put("validacion", ResultadoRegistroContactoEnum.EMPLEADOR_AFILIADO);
            return resultadoServ;
        } else {

            ValidacionDTO validacionBDCore = getValidacion(ValidacionCoreEnum.VALIDACION_EMPLEADOR_BD_CORE, list);
            if (validacionBDCore != null && validacionBDCore.getResultado().equals(ResultadoValidacionEnum.APROBADA)) {
                BuscarEmpleador buscarEmpleador = new BuscarEmpleador(false,
                        entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                        entrada.getEmpleador().getPersona().getTipoIdentificacion(), null);
                buscarEmpleador.setToken(token);

                List<Empleador> result = null;
                buscarEmpleador.execute();
                result = buscarEmpleador.getResult();

                if (result != null && !result.isEmpty()) {
                    empleador = result.get(0);
                    if (reintegro && MotivoDesafiliacionEnum.ANULADO.equals(empleador.getMotivoDesafiliacion())) {
                        reintegro = false;
                    }
                    if (TipoIdentificacionEnum.NIT.equals(empleador.getEmpresa().getPersona().getTipoIdentificacion())
                            && (empleador.getIdEmpleador() != null)) {
                        ConsultarUltimaClasificacion ultimaClasificacion = new ConsultarUltimaClasificacion(
                                empleador.getIdEmpleador());
                        ultimaClasificacion.setToken(token);
                        ultimaClasificacion.execute();
                        ClasificacionEnum clasificacion = ultimaClasificacion.getResult();
                        if (clasificacion != null && !entrada.getTipoEmpleador().equals(clasificacion)) {
                            resultadoServ.put("validacion", ResultadoRegistroContactoEnum.CLASIFICACION_INCORRECTA);
                            return resultadoServ;
                        }
                    }
                }
            } else if (validacionBDCore != null
                    && validacionBDCore.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                registrarIntentoAfiliacion(null, CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES,
                        TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, token,
                        entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                        entrada.getEmpleador().getPersona().getTipoIdentificacion());
                resultadoServ.put("validacion", ResultadoRegistroContactoEnum.NO_AFILIABLE);
                return resultadoServ;
            }
            /* Valida si tiene una Cuenta Web Activa */
            String nombreUsuario = PREFIJO_EMPLEADOR + entrada.getEmpleador().getPersona().getTipoIdentificacion().name() + "_"
                    + entrada.getEmpleador().getPersona().getNumeroIdentificacion();
            EstaUsuarioActivo activo = new EstaUsuarioActivo(nombreUsuario);
            activo.setToken(token);
            activo.execute();
            Boolean cuentaActiva = activo.getResult();
            Long idEmpleador = null;
            if (cuentaActiva && reintegro) {
                resultadoServ.put("validacion", ResultadoRegistroContactoEnum.REINTEGRO_ACTIVA);
                return resultadoServ;
            } else if (reintegro) {
                resultadoServ.put("validacion", ResultadoRegistroContactoEnum.REINTEGRO_INACTIVA);
            } else {
                resultadoServ.put("validacion", ResultadoRegistroContactoEnum.NUEVA_AFILIACION);
                if (empleador != null) {
                    idEmpleador = empleador.getIdEmpleador();
                }
            }

            SolicitudAfiliacionEmpleador sae = initSolicitud(idEmpleador, entrada.getTipoEmpleador(), ResultadoRegistroContactoEnum.valueOf(resultadoServ.get("validacion").toString()),
                    userDTO);
            Long idSolicitud = null;
            sae.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
            CrearSolicitudAfiliacionEmpleador crearSolic = new CrearSolicitudAfiliacionEmpleador(sae);
            crearSolic.setToken(token);
            crearSolic.execute();
            idSolicitud = crearSolic.getResult();
            if (entrada.getInformacionContacto() != null
                    && entrada.getInformacionContacto().getCorreoElectronico() != null) {
                entrada.setCorreoEmpleadorNuevo(true);
            }

            try {
                enrolarWs(entrada, userDTO, idEmpleador, idSolicitud, token);
                ConsultarSolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador = new ConsultarSolicitudAfiliacionEmpleador(
                    idSolicitud);
                consultarSolicitudAfiliacionEmpleador.execute();

                sae = consultarSolicitudAfiliacionEmpleador.getResult();

                logger.warn("Solicitud afiliacion >>>>>>>"+sae.toString());

                DiligenciarFormularioAfiliacionDTO formulario = new DiligenciarFormularioAfiliacionDTO();
                formulario.setSolicitudAfiliacion(sae);
                formulario.setDatosCorrectos(Boolean.TRUE);
                diligenciarFormularioAfiliacion(formulario,userDTO);
                GuardarDatosTemporalesEmpleador datoTemporalEmpleador = new GuardarDatosTemporalesEmpleador(idSolicitud,null,null,estructurarJsonDatoTemporal(empleadorDTO));
                datoTemporalEmpleador.execute();
                radicarSolicitudAfiliacion(construirSolicitudAfiliaicon(empleadorDTO,sae),userDTO);
                resultadoServ.put("numeroRadicado", sae.getSolicitudGlobal().getNumeroRadicacion());
                return resultadoServ;
            } catch (Exception e) {
                e.printStackTrace();
                resultadoServ.put("validacion", ResultadoRegistroContactoEnum.NO_AFILIABLE);
                return resultadoServ;
            }
            // return resultadoServ;
        }        
    }

    private void enrolarWs(DigitarInformacionContactoDTO entrada, UserDTO usuarioDTO, Long idEmpleador, Long idSolicitud,
        String token) throws IOException {

        ConsultarSolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador = new ConsultarSolicitudAfiliacionEmpleador(
                idSolicitud);
        consultarSolicitudAfiliacionEmpleador.setToken(token);
        consultarSolicitudAfiliacionEmpleador.execute();
        SolicitudAfiliacionEmpleador sae = consultarSolicitudAfiliacionEmpleador.getResult();
        RadicarSolicitud r = new RadicarSolicitud(sae.getSolicitudGlobal().getIdSolicitud(), "");
        r.setToken(token);
        r.execute();
        BuscarSolicitudPorId buscarSolicitudPorId = new BuscarSolicitudPorId(sae.getSolicitudGlobal().getIdSolicitud());
        buscarSolicitudPorId.setToken(token);
        buscarSolicitudPorId.execute();
        Solicitud solicitud = buscarSolicitudPorId.getResult();

        TokenDTO tokenG = generarTokenAcesso(entrada, token);
        Long idInstancia = iniciarProceso(idEmpleador, idSolicitud, token, solicitud);
        solicitud.setIdInstanciaProceso(String.valueOf(idInstancia));
        ActualizarSolicitud actualizarSolicitud = new ActualizarSolicitud(solicitud.getIdSolicitud(), solicitud);
        actualizarSolicitud.setToken(token);
        actualizarSolicitud.execute();

        String tokenCodificado = convertirDatosUrlABase64(idSolicitud, idInstancia, tokenG.getToken());
        String url = "";
        String numeroIdentificacionEmpleador = entrada.getEmpleador().getPersona().getDigitoVerificacion() != null ? entrada.getEmpleador().getPersona().getNumeroIdentificacion() + "-" + entrada.getEmpleador().getPersona().getDigitoVerificacion().toString() : entrada.getEmpleador().getPersona().getNumeroIdentificacion();
        entrada.setDominio(url);
        Map<String, Object> map = new HashMap<>();
        map.put("dto", entrada);
        map.put("token", tokenG.getToken());
        ObjectMapper mapper = new ObjectMapper();
        GuardarDatosTemporalesEmpleador datosTemporalesEmpleador = new GuardarDatosTemporalesEmpleador(
                sae.getSolicitudGlobal().getIdSolicitud(),
                entrada.getEmpleador().getPersona().getNumeroIdentificacion(),
                entrada.getEmpleador().getPersona().getTipoIdentificacion(), mapper.writeValueAsString(map));
        datosTemporalesEmpleador.setToken(token);
        datosTemporalesEmpleador.execute();
    }

    private DigitarInformacionContactoDTO construirInformacionContactoDTO(AfiliacionEmpleadorDTO empleador){
        DigitarInformacionContactoDTO result = new DigitarInformacionContactoDTO();
        EmpleadorDTO empleadorDTO = new EmpleadorDTO();
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNumeroIdentificacion(empleador.getNumeroNit());
        personaDTO.setTipoIdentificacion(empleador.getTipoNit());
        personaDTO.setRazonSocial(empleador.getRazon());
        personaDTO.setClasificacion(empleador.getClasificacion());
        // NOTA PENDIENTE DE CALCULAR EL DIGITO DE VERIFICACION
        // persona.setDigitoVerificacion()
        empleadorDTO.setPersona(personaDTO);
        InformacionContactoDTO informacionContacto = new InformacionContactoDTO();
        informacionContacto.setPrimerApellido(empleador.getPriape());
        informacionContacto.setSegundoApellido(empleador.getSegape() == null ? "" : empleador.getSegape());
        informacionContacto.setSegundoNombre(empleador.getSegnom() == null ? "" : empleador.getSegnom());
        informacionContacto.setPrimerNombre(empleador.getPrinom());
        informacionContacto.setTipoIdentificacion(empleador.getTipoRep());
        informacionContacto.setNumeroIdentificacion(empleador.getNumeroRep());
        // informacionContacto.setTelefonoFijo(empleador.getTelRep() == null ? "" : empleador.getTelRep());
        informacionContacto.setTelefonoCelular(empleador.getCelRep());
        // informacionContacto.setCargo(empleador.getPrestacion());
        informacionContacto.setCorreoElectronico(empleador.getCorrep());
        result.setTipoEmpleador(empleador.getClasificacion());
        result.setEmpleador(empleadorDTO);
        result.setInformacionContacto(informacionContacto);
        return result;
    }

    private String estructurarJsonDatoTemporal(AfiliacionEmpleadorDTO empleador) {
        Map<String, Object> retorno = new HashMap<>();

        retorno.put("dto", construirInfoTemporal(empleador));
        EmpleadorDTO empleadorDTO = construirEmpleador(empleador);
        retorno.put("empleador", empleadorDTO);
        retorno.put("ubicaciones", construirUbicaciones(empleador));
        retorno.put("representante1", construirRepresentante(empleador));
        retorno.put("rolafiliaciones", construirRolesContacto(empleador));

        // Serializa el mapa a JSON si aplica
        return new Gson().toJson(retorno);
    }

    private infoTemporalEmpleadorDTO construirInfoTemporal(AfiliacionEmpleadorDTO empleador) {
        infoTemporalEmpleadorDTO dto = new infoTemporalEmpleadorDTO();

        EmpleadorDTO empleadorDTO = new EmpleadorDTO();
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNumeroIdentificacion(empleador.getNumeroNit());
        personaDTO.setTipoIdentificacion(empleador.getTipoNit());
        personaDTO.setRazonSocial(empleador.getRazon());
        personaDTO.setClasificacion(empleador.getClasificacion());

        empleadorDTO.setPersona(personaDTO);
        dto.setEmpleador(empleadorDTO);

        InformacionContactoDTO contacto = new InformacionContactoDTO();
        contacto.setPrimerApellido(empleador.getPriape());
        contacto.setSegundoApellido(empleador.getSegape());
        contacto.setSegundoNombre(empleador.getSegnom());
        contacto.setPrimerNombre(empleador.getPrinom());
        contacto.setTipoIdentificacion(empleador.getTipoRep());
        contacto.setNumeroIdentificacion(empleador.getNumeroRep());
        // contacto.setTelefonoFijo(empleador.getTelRep());
        contacto.setTelefonoCelular(empleador.getCelRep());
        // contacto.setCargo(empleador.getPrestacion());
        contacto.setCorreoElectronico(empleador.getCorrep());

        dto.setInformacionContacto(contacto);
        dto.setTipoEmpleador("PERSONA_JURIDICA");
        dto.setCorreoEmpleadorNuevo(Boolean.TRUE);

        return dto;
    }

    private EmpleadorDTO construirEmpleador(AfiliacionEmpleadorDTO empleador) {
        EmpleadorDTO empleadorDTO = new EmpleadorDTO();
        PersonaDTO persona = new PersonaDTO();

        persona.setNumeroIdentificacion(empleador.getNumeroNit());
        persona.setTipoIdentificacion(empleador.getTipoNit());
        persona.setRazonSocial(empleador.getRazon());
        persona.setClasificacion(empleador.getClasificacion());
        persona.setAutorizacionEnvioEmail(Boolean.TRUE);
        persona.setAutorizaUsoDatosPersonales(Boolean.TRUE);

        UbicacionDTO ubicacion = new UbicacionDTO();
        ubicacion.setDireccion(empleador.getDireccion());
        ubicacion.setTelefonoCelular(empleador.getCelular());
        ubicacion.setAutorizacionEnvioEmail(Boolean.TRUE);
        persona.setUbicacionDTO(ubicacion);

        EmpresaDTO empresa = new EmpresaDTO();
        empresa.setPersona(persona);
        empresa.setFechaConstitucion(empleador.getFechaConstitucion());
        empresa.setNaturalezaJuridica(empleador.getTipoNat());
        empresa.setCodigoCIIU(new CodigoCIIU(empleador.getCiiu()));

        empleadorDTO.setNumeroTotalTrabajadores( empleador.getNumeroTotalTrabajadores());
        empleadorDTO.setValorTotalUltimaNomina(BigDecimal.ZERO);
        empleadorDTO.setPeriodoUltimaNomina(Date.from(LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        persona.setAutorizacionEnvioEmail(Boolean.TRUE);
        persona.setAutorizaUsoDatosPersonales(Boolean.TRUE);
        
        empleadorDTO.setPersona(persona);
        empleadorDTO.setEmpresa(empresa);
        return empleadorDTO;
    }

    private List<UbicacionDTO> construirUbicaciones(AfiliacionEmpleadorDTO empleador) {
        UbicacionDTO base = new UbicacionDTO();
        base.setDireccion(empleador.getDireccion());
        base.setTelefonoCelular(empleador.getCelular());
        base.setAutorizacionEnvioEmail(Boolean.TRUE);

        List<UbicacionDTO> ubicaciones = new ArrayList<>();
        for (TipoUbicacionEnum tipo : Arrays.asList(TipoUbicacionEnum.ENVIO_CORRESPONDENCIA,
                                            TipoUbicacionEnum.NOTIFICACION_JUDICIAL,
                                            TipoUbicacionEnum.UBICACION_PRINCIPAL)) {
            UbicacionDTO ubicacion = base.clone();
            ubicacion.setTipoUbicacion(tipo);
            ubicaciones.add(ubicacion);
        }
        return ubicaciones;
    }

    private Persona construirRepresentante(AfiliacionEmpleadorDTO empleador) {
        Persona rep = new Persona();
        rep.setTipoIdentificacion(empleador.getTipoRep());
        rep.setNumeroIdentificacion(empleador.getNumeroRep());
        rep.setPrimerNombre(empleador.getPriape());
        rep.setSegundoNombre(empleador.getSegape());
        rep.setPrimerApellido(empleador.getPriape());
        rep.setSegundoApellido(empleador.getSegape());

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setEmail(empleador.getCorrep());
        ubicacion.setTelefonoCelular(empleador.getCelRep());
        rep.setUbicacionPrincipal(ubicacion);

        return rep;
    }

    private List<RolContactoEmpleador> construirRolesContacto(AfiliacionEmpleadorDTO empleador) {
        List<RolContactoEmpleador> roles = new ArrayList<>();

        for (TipoRolContactoEnum tipo : Arrays.asList(
            TipoRolContactoEnum.ROL_RESPONSABLE_SUBSIDIOS,
            TipoRolContactoEnum.ROL_RESPONSABLE_APORTES,
            TipoRolContactoEnum.ROL_RESPONSABLE_AFILIACIONES)) {

            Persona persona = new Persona();
            Ubicacion ubicacion = new Ubicacion();
            RolContactoEmpleador rol = new RolContactoEmpleador();
            if(tipo.equals(TipoRolContactoEnum.ROL_RESPONSABLE_APORTES)){

                persona.setTipoIdentificacion(empleador.getTipoAportes());
                persona.setNumeroIdentificacion(empleador.getNumeroAportes());
                persona.setPrimerNombre(empleador.getPrinomAportes());
                persona.setPrimerApellido(empleador.getPriapeAportes());
    
                ubicacion.setEmail(empleador.getCorAportes());
                ubicacion.setTelefonoCelular(empleador.getCelAportes());
                persona.setUbicacionPrincipal(ubicacion);
    
                rol.setTipoRolContactoEmpleador(tipo);
                rol.setPersona(persona);
                rol.setUbicacion(ubicacion);
            }else{
                persona.setTipoIdentificacion(empleador.getTipoSubsidio());
                persona.setNumeroIdentificacion(empleador.getNumeroSubsidio());
                persona.setPrimerNombre(empleador.getPrinomSubsidio());
                persona.setPrimerApellido(empleador.getPriapeSubsidio());
    
                ubicacion.setEmail(empleador.getCorSubsidio());
                ubicacion.setTelefonoCelular(empleador.getCelSubsidio());
                persona.setUbicacionPrincipal(ubicacion);
    
                rol.setTipoRolContactoEmpleador(tipo);
                rol.setPersona(persona);
                rol.setUbicacion(ubicacion);
            }
            
            roles.add(rol);
        }
        return roles;
    }

    private RadicarSolicitudAfiliacionDTO construirSolicitudAfiliaicon(AfiliacionEmpleadorDTO empleador,SolicitudAfiliacionEmpleador sae){
        logger.warn(">>>>>>>>>INICIA construirSolicitudAfiliaicon");
        RadicarSolicitudAfiliacionDTO solicitud = new RadicarSolicitudAfiliacionDTO();
        logger.warn("parametros numero y instancia \n"+sae.getSolicitudGlobal().getNumeroRadicacion()+"\n"+sae.getSolicitudGlobal().getIdInstanciaProceso());
        solicitud.setNumeroRadicado(sae.getSolicitudGlobal().getNumeroRadicacion());
        solicitud.setIdInstanciaProceso(Long.valueOf(sae.getSolicitudGlobal().getIdInstanciaProceso()));
        EmpleadorDTO empleadorDTO = new EmpleadorDTO();
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNumeroIdentificacion(empleador.getNumeroNit());
        personaDTO.setTipoIdentificacion(empleador.getTipoNit());
        personaDTO.setRazonSocial(empleador.getRazon());
        personaDTO.setClasificacion(empleador.getClasificacion());

        empleadorDTO.setPersona(personaDTO);

        solicitud.setEmpleador(empleadorDTO);

        return solicitud;
    }

}
