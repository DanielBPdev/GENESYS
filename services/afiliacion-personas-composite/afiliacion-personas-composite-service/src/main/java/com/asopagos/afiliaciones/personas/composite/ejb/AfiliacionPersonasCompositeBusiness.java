package com.asopagos.afiliaciones.personas.composite.ejb;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;
import com.asopagos.afiliaciones.clients.ActualizarEstadoDocumentacionAfiliacion;
import com.asopagos.afiliaciones.clients.BuscarSolicitud;
import com.asopagos.afiliaciones.clients.BuscarSolicitudPorId;
import com.asopagos.afiliaciones.clients.ConsultarProductosNoConforme;
import com.asopagos.afiliaciones.clients.GuardarInstanciaProceso;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.clients.ConsultarUltimaClasificacionCategoria;
import com.asopagos.afiliaciones.clients.ConsultarClasificacionPensionado;

import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersonaMasivas;
import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAfiliacionPersona;
import com.asopagos.afiliaciones.clients.VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO;
import com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService;
import com.asopagos.afiliaciones.clients.ConsultarDatosExistenteNoPensionado;
import com.asopagos.afiliados.clients.ActualizarAfiliado;
import com.asopagos.afiliados.clients.ActualizarEstadoBeneficiario;
import com.asopagos.afiliados.clients.ActualizarEstadoRolAfiliado;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.AsociarBeneficiarioAGrupoFamiliar;
import com.asopagos.afiliados.clients.BuscarAfiliados;
import com.asopagos.afiliados.clients.CalcularCategoriasAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosMismaFecha;
import com.asopagos.afiliados.clients.ConsultarGrupoFamiliar;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.clients.CrearAfiliado;
import com.asopagos.afiliados.clients.ActualizarBeneficiariomasivamente;
import com.asopagos.afiliados.clients.ActualizarBeneficiarioMasivo;
import com.asopagos.afiliados.clients.CrearGrupoFamiliar;
import com.asopagos.afiliados.clients.GuardarDatosIdentificacionYUbicacion;
import com.asopagos.afiliados.clients.GuardarInformacionLaboral;
import com.asopagos.afiliados.clients.RegistrarBeneficiario;
import com.asopagos.afiliados.clients.RegistrarInformacionBeneficiarioConyugue;
import com.asopagos.afiliados.clients.RegistrarInformacionBeneficiarioHijoPadre;
import com.asopagos.afiliados.clients.RegistrarPersonasBeneficiariosAbreviada;
import com.asopagos.dto.afiliaciones.AfiliadosACargoMasivosDTO;
import com.asopagos.dto.afiliaciones.AfiliadosMasivosDTO;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.novedades.composite.clients.GestionarNovedad;
import com.asopagos.comunicados.clients.CrearComunicado;
import com.asopagos.comunicados.clients.GuardarObtenerComunicadoECM;
import com.asopagos.comunicados.clients.ResolverPlantillaConstantesComunicado;
import com.asopagos.comunicados.clients.ResolverVariablesComunicado;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueProcesoMasivo;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarProcesoConsolaEstado;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliarBeneficiarioDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.BeneficiarioHijoPadreDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.CategoriaDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.empleadorDatosDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ProductoNoConformeDTO;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.dto.ResultadoGeneralProductoNoConformeBeneficiarioDTO;
import com.asopagos.dto.ResultadoValidacionArchivoTrasladoDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.VerificarProductoNoConformeDTO;
import com.asopagos.dto.VerificarRequisitosDocumentalesDTO;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaDetalleModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.empleadores.clients.ConsultarDatosTemporalesEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.pagadoras.clients.ActualizarGestionSolicitudesAsociacion;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.AreaGeograficaEnumA;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGestionProductoNoConformeSubsanableEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.comunicados.MedioComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.novedades.clients.*;
import com.asopagos.novedades.clients.CrearCargueArchivoActualizacion;
import com.asopagos.novedades.composite.clients.EjecutarRetiroTrabajadores;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAutomaticaSinValidaciones;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ConsultarPersonaRazonSocial;
import com.asopagos.personas.clients.CrearPersona;
import com.asopagos.personas.clients.RegistrarPersonasDetalle;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.exception.ValidacionFallidaException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
///imports 64732
import com.asopagos.afiliados.clients.ActualizarMedioDePagoPersona;
import com.asopagos.afiliados.clients.ConsultarIdSitioPagoPredeterminado;
import com.asopagos.dto.ParametrizacionNovedadDTO;
import com.asopagos.validaciones.clients.ValidadorNovedadesHabilitadas;
import com.asopagos.afiliaciones.personas.composite.dto.ListasPensionadosDTO;
import com.asopagos.afiliaciones.clients.VerificarEstructuraArchivoPensionado25Anios;
import com.asopagos.afiliaciones.clients.ConsultarDatosExistenteNoPensionado;
import com.asopagos.novedades.composite.clients.GestionarNovedad;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;

///
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.asopagos.validaciones.clients.VerificarSolicitudesEnCurso;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections.CollectionUtils;
import com.asopagos.empresas.clients.ConsultarSucursalesEmpresa;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.afiliaciones.clients.ConsultarDepartamentoPorIdMunicipio;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.personas.clients.ConsultarUbicacionPersona;
import java.util.stream.Collectors;
import com.asopagos.afiliaciones.clients.ObtenerCarguePensionados25Anios;
import com.asopagos.afiliaciones.clients.ObtenerCargueTrasladosMasivos;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import java.time.format.DateTimeFormatter;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import java.time.LocalDate;
import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersonaAfiliada;
import com.asopagos.dto.ListadoSolicitudesAfiliacionDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.afiliados.clients.ConsultarBeneficiarioTipoNroIdentificacion;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedad;
import com.asopagos.afiliados.clients.ConsultarRolAfiliadoEmpresa;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedad;
import com.asopagos.historicos.clients.ObtenerGruposFamiliaresAfiPrincipal;
import com.asopagos.historicos.dto.PersonaComoAfiPpalGrupoFamiliarDTO;
import com.asopagos.historicos.dto.BeneficiarioGrupoFamiliarDTO;
import javax.ejb.Asynchronous;
import com.asopagos.afiliaciones.clients.CrearArchivoTrasladosCFF;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;
import com.asopagos.enumeraciones.core.ErroresConsolaEnum;
/**
/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la composicón de afiliación de personas <b>Historia de Usuario:</b>
 * HU-121-104, HU-121-107, HU-121-108, HU-121-115, HU-121-116, HU-121-122
 * 
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
 
@Stateless
public class AfiliacionPersonasCompositeBusiness implements AfiliacionPersonasCompositeService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AfiliacionPersonasCompositeBusiness.class);
    /**
     * Llave perteneciente al número de radicado perteneciente a la solicitud 
     */
    private static final String NUMERO_RADICADO_SOLICITUD="numeroRadicado";

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#digitarDatosIdentificacionPersona(com.asopagos.dto.DatosBasicosIdentificacionDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, Object> digitarDatosIdentificacionPersona(AfiliadoInDTO inDTO, UserDTO userDTO) {
        logger.debug("Inicia AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona( AfiliadoInDTO, UserDTO )");
        /*
         * Este servicio de composición debe crear el afiliado, crear la
         * solicitud de afiliación de persona y finalmente iniciar el proceso en
         * el BPM y retornar las variables de contexto definidas en el BPM
         */
        Long idSolicitudGlobal = null;
        Long idAfiliado = null;
        Long idInstanciaProcesoAfiliacionPersona = null;

        AfiliadoInDTO afiliadoDTO = null;

        Map<String, Object> parametrosProceso = null;

        if (inDTO != null && (inDTO.getPersona() != null) && inDTO.getPersona().getTipoIdentificacion() != null
                && inDTO.getPersona().getNumeroIdentificacion() != null && (inDTO.getTipoAfiliado() != null)
                && (inDTO.getCanalRecepcion() != null)) {

            // se Crea el afiliado
            logger.debug("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Crear el afiliado");
            afiliadoDTO = crearAfiliado(inDTO);
            idAfiliado = afiliadoDTO.getIdAfiliado();
            if (idAfiliado != null && idAfiliado > 0) {
                // Se crea la solicitud de afiliacion de la persona
                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Se crea la solicitud de afiliacion de la persona");
                inDTO.setIdAfiliado(idAfiliado);
                inDTO.setIdRolAfiliado(afiliadoDTO.getIdRolAfiliado());
                idSolicitudGlobal = crearSolicitudAfiliacionPersona(inDTO);

                if (idSolicitudGlobal != null && idSolicitudGlobal > 0) {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Valido que el usuario que envian en la peticion sea el registrado para la caja de compensacion");

                    logger.debug("clave " + userDTO.getNombreUsuario());

                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Inicio proceso de afiliacion de persona en el BPM");

                    // genera el numero de radicado correspondiente y lo
                    // actualiza en relacion en la solicitud
                    String numeroRadicado = generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());

                    if (inDTO.getIdBeneficiario() != null && !inDTO.getIdBeneficiario().isEmpty()) {
                        // Si tiene esta como algun beneficiario se inactiva

                        //actualizarEstadoBeneficiario(inDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO,
                        //MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
                        List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<BeneficiarioModeloDTO>();
                        for (Long idBeneficiarioInactivar : inDTO.getIdBeneficiario()) {
                            //actualizarEstadoBeneficiario(idBeneficiarioInactivar, EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
                            ConsultarBeneficiario beneficiarioSrv = new ConsultarBeneficiario(idBeneficiarioInactivar);
                            beneficiarioSrv.execute();

                            listaBeneficiarios.add(beneficiarioSrv.getResult());
                        }

                        DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                        datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
                        datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                        datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(numeroRadicado);
                        datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                        RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                        novedadCascada.execute();
                        
                    }

                    try {
                        // Inicio proceso de afiliacion de persona en el BPM
                        idInstanciaProcesoAfiliacionPersona = iniciarProcesoAfiliacionPersona(idSolicitudGlobal,numeroRadicado, userDTO);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
                    }

                    if (idInstanciaProcesoAfiliacionPersona != null && idInstanciaProcesoAfiliacionPersona > 0) {

                        actualizarIdInstanciaProcesoSolicitudDePersona(idSolicitudGlobal, idInstanciaProcesoAfiliacionPersona);

                        parametrosProceso = new HashMap<>();
                        parametrosProceso.put("idSolicitudGlobal", idSolicitudGlobal);
                        parametrosProceso.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
                    }
                    else {
                        logger.debug(
                                "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro incial el proceso de afiliacion persona en el BPM");
                        throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE);
                    }

                }
                else {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro radicar la solicitud");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                }
            }
            else {
                logger.debug("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro crear el afiliado");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            }

        }
        else {
            logger.debug("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No existe la Persona");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        logger.debug("Finaliza AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona( AfiliadoInDTO, UserDTO )");
        return parametrosProceso;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#digitarDatosIdentificacionPersona(com.asopagos.dto.DatosBasicosIdentificacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoTrasladoDTO validacionAfiliacionPersonasMasivas(TipoArchivoRespuestaEnum tipoArchivo, String idEmpleador,CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        logger.info("tipoArchivo" + tipoArchivo);
        // logger.info("empleadorDatos" + empleadorDatos.size());
        // empleadorDatosDTO emp = empleadorDatos.get(0);
        logger.info("empleadorDatos" + idEmpleador);
        logger.info("codigoCaja" + codigoCaja);
        logger.info("codigoCaja" + cargue.getCodigoIdentificacionECM());
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("codigoCaja" + archivo.getFileName());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        logger.info(idCargue);
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
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_TRASLADO_EMPRESAS_CCF);//FaltaENUM
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF verificarArchivo = new VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF(tipoArchivo, idEmpleador,archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoTrasladoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

         //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getRegistrosConErrores() > 0 )) {
                        logger.info("2G entra con errores" );
                
                        estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                logger.info("2G entra sin errores" );

                //Solo hay registros validos, entonces debe finalizar sin errores
                estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO;
            }

        //Si hay registros validos procesamos esos registros validos.
        logger.info(resultDTO.getTotalRegistro());
        logger.info("es limpio" + CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosCargoAfiliar()));
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
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_TRASLADO_EMPRESAS_CCF);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
        // TODO 01: Creat conficion if, tipoArchivo es igual a TipoArchivoRespuestaEnum.TrasladoMasivosEmpresasCCFCargo
        String[] datos = idEmpleador.split("-");
        String docEmpleador = "", tipoDocumneto = "";
        docEmpleador = datos[0];
        tipoDocumneto = datos[1];
        TipoIdentificacionEnum tipoIdEmpleador = TipoIdentificacionEnum.valueOf(tipoDocumneto);
        BuscarEmpleador buscarEmpleador = new BuscarEmpleador(null,docEmpleador,tipoIdEmpleador, null);
        buscarEmpleador.execute();
        List<Empleador> result = buscarEmpleador.getResult();
        Empleador emp = result.get(0);

        resultDTO.setIdEmpleador(emp.getIdEmpleador());

        // TODO 01.1. Invocar servicio CrearArchivoTrasladosCFF con los siguientes parametros CrearArchivoTrasladosCFF(idCargue, Long.parseLong(idEmpleador))
        
        logger.info("Fin validarArchivoRetiroTrabajadores (, CargueArchivoActualizacionDTO, UserDTO)");
        return resultDTO;

        //  private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        //     logger.info("entra metodo obtenerArchivo ****" + archivoId);
        //     ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        //     consultarArchivo.execute();
        //     logger.info("Finaliza obtenerArchivo ^^^^" + consultarArchivo.getResult());
        // return consultarArchivo.getResult();
    }
     @Override
     public void validacionAfiliacionPersonasMasivasNegocio(ResultadoValidacionArchivoTrasladoDTO resultDTO,UserDTO userDTO){

        ObtenerCargueTrasladosMasivos obtenerCargueTrasladosMasivos = new ObtenerCargueTrasladosMasivos(resultDTO.getIdCargue());
        obtenerCargueTrasladosMasivos.execute();
        ConsolaEstadoCargueProcesoDTO result = obtenerCargueTrasladosMasivos.getResult();
        Long idSolicitudTrabajador = null;

            
        if (CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosAfiliar())) {
            for (AfiliadosMasivosDTO solAfiliacionDTO : resultDTO.getListaCandidatosAfiliar()) {
                ConsultarRolAfiliadoEmpresa consultarRolAfiliadoEmpresa = new ConsultarRolAfiliadoEmpresa(solAfiliacionDTO.getTipoIdentificacionAfiliado(),solAfiliacionDTO.getNumeroIdentificacionAfiliado(),solAfiliacionDTO.getTipoIdentificacionEmpresa(),solAfiliacionDTO.getNumeroIdentificacionEmpresa());
                consultarRolAfiliadoEmpresa.execute();
                Long rolAfiliado = consultarRolAfiliadoEmpresa.getResult();

            if(rolAfiliado == null){
                idSolicitudTrabajador = MapsAfiladoTrabajador(solAfiliacionDTO, userDTO, result);
            }else{
                ReintegroMasivoTraslados(solAfiliacionDTO, rolAfiliado);
            }
            }
        }

        result.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
        result.setFechaFin(Calendar.getInstance().getTimeInMillis());
        actualizarCargueConsolaEstado(resultDTO.getIdCargue(), result);

        validacionAfiliacionPersonasMasivasNegocioCargo(resultDTO, idSolicitudTrabajador, userDTO);
   
        
        logger.info("Fin validarArchivoRetiroTrabajadores (, CargueArchivoActualizacionDTO, UserDTO)");
        //return resultDTO;

    }

    public void ReintegroMasivoTraslados(AfiliadosMasivosDTO solAfiliacionDTO, Long idRolAfiliado){
    
    SolicitudNovedadDTO reintegrar = new SolicitudNovedadDTO();
    DatosPersonaNovedadDTO datosPersonas = new DatosPersonaNovedadDTO();

    datosPersonas.setTipoIdentificacion(solAfiliacionDTO.getTipoIdentificacionAfiliado());
    datosPersonas.setNumeroIdentificacion(solAfiliacionDTO.getNumeroIdentificacionAfiliado());
    datosPersonas.setFechaInicioNovedad(new Date().getTime());
    datosPersonas.setIdRolAfiliado(idRolAfiliado);
    datosPersonas.setTipoIdentificacionEmpleador(solAfiliacionDTO.getTipoIdentificacionEmpresa());
    datosPersonas.setNumeroIdentificacionEmpleador(solAfiliacionDTO.getNumeroIdentificacionEmpresa());
    reintegrar.setTipoTransaccion(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
    reintegrar.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
    reintegrar.setDatosPersona(datosPersonas);
    reintegrar.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);

    RadicarSolicitudNovedad radicarSolicitudNovedad = new RadicarSolicitudNovedad(reintegrar);
    radicarSolicitudNovedad.execute();



    }

    public void validacionAfiliacionPersonasMasivasNegocioCargo(ResultadoValidacionArchivoTrasladoDTO resultDTO, Long idSolicitudTrabajador,UserDTO userDTO){

        ObtenerCargueTrasladosMasivos obtenerCargueTrasladosMasivos = new ObtenerCargueTrasladosMasivos(resultDTO.getIdCargueCargo());
        obtenerCargueTrasladosMasivos.execute();
        ConsolaEstadoCargueProcesoDTO result = obtenerCargueTrasladosMasivos.getResult();
            
         if (CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosCargoAfiliar())) {
            logger.info("getListaCandidatosCargoAfiliar");
            for (AfiliadosACargoMasivosDTO solAfiliacionDTO : resultDTO.getListaCandidatosCargoAfiliar()) {
                logger.info("solAfiliacionDTO.getParentezco()" + solAfiliacionDTO.getParentezco());

                if (solAfiliacionDTO.getParentezco().name().equals("CONYUGE")) {

                    MapsBeneficiarioConyugue(solAfiliacionDTO, idSolicitudTrabajador, userDTO, result);
                } else {

                    MapsBeneficiarioHijoPadre(solAfiliacionDTO, idSolicitudTrabajador, userDTO, result);
                }

            }
        }

        // Se actualiza el estado en la consola
        result.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
        result.setFechaFin(Calendar.getInstance().getTimeInMillis());
        actualizarCargueConsolaEstado(resultDTO.getIdCargueCargo(), result);

        logger.info("Fin validarArchivoRetiroTrabajadores (, CargueArchivoActualizacionDTO, UserDTO)");
    //    return resultDTO;
    }




    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#digitarDatosIdentificacionPersona(com.asopagos.dto.DatosBasicosIdentificacionDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoArchivo25AniosDTO cargueArchivoPensionados25Anios(TipoArchivoRespuestaEnum tipoArchivo,CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        logger.info("tipoArchivo" + tipoArchivo);
        // logger.info("empleadorDatos" + empleadorDatos.size());
        // empleadorDatosDTO emp = empleadorDatos.get(0);
        // logger.info("empleadorDatos" + idEmpleador);
        logger.info("codigoCaja" + codigoCaja);
        logger.info("codigoCaja" + cargue.getCodigoIdentificacionECM());
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("codigoCaja" + archivo.getFileName());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        logger.info("idCargue" + idCargue);

        logger.info(idCargue);
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
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_PENSIONADOS_25_ANIOS);//FaltaENUM
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoPensionado25Anios verificarArchivo = new VerificarEstructuraArchivoPensionado25Anios(tipoArchivo, archivo);
        verificarArchivo.execute();

        ResultadoArchivo25AniosDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;
        //Si hay registros validos procesamos esos registros validos.
        logger.info("total registros "+resultDTO.getTotalRegistro());
        logger.info("total registros getAfiliarTrabajadorCandidatoDTO "+resultDTO.getAfiliarTrabajadorCandidatoDTO());
        logger.info("total registros getTrabajadorCandidatoNovedadDTO "+resultDTO.getTrabajadorCandidatoNovedadDTO());
        // logger.info("es limpio" + CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosCargoAfiliar()));
        // if (CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosAfiliar())) {
        //     for (AfiliadosMasivosDTO solAfiliacionDTO : resultDTO.getListaCandidatosAfiliar()) {
        //         MapsAfiladoTrabajador(solAfiliacionDTO, userDTO);
        //     }
        // }
        // if (CollectionUtils.isNotEmpty(resultDTO.getListaCandidatosCargoAfiliar())) {
        //     logger.info("getListaCandidatosCargoAfiliar");
        //     for (AfiliadosACargoMasivosDTO solAfiliacionDTO : resultDTO.getListaCandidatosCargoAfiliar()) {
        //         logger.info("solAfiliacionDTO.getParentezco()" + solAfiliacionDTO.getParentezco());

        //         if (solAfiliacionDTO.getParentezco().equals("CONYUGUE")) {
        //             MapsBeneficiarioConyugue(solAfiliacionDTO, userDTO);
        //         } else {
        //             MapsBeneficiarioHijoPadre(solAfiliacionDTO, userDTO);
        //         }

        //     }
        // }
        //     //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getRegistrosConErrores() > 0 )) {
                        logger.info("2G entra con errores" );
                
                        estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                logger.info("2G entra sin errores" );

                //Solo hay registros validos, entonces debe finalizar sin errores
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
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_PENSIONADOS_25_ANIOS);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
        logger.info("Fin validarArchivoRetiroTrabajadores (, CargueArchivoActualizacionDTO, UserDTO)");
       return resultDTO;
    }

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
     * Realiza el registro del cargue de archivo de actualizacion de informacion
     *
     * @param cargueArchivoActualizacionDTO Informacion cargue archivo
     * actualizacion
     * @return Identificador del cargue
     */
    private Long crearActualizarCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        CrearCargueArchivoActualizacion crearCargueArchivoActualizacion = new CrearCargueArchivoActualizacion(
                cargueArchivoActualizacionDTO);
        crearCargueArchivoActualizacion.execute();
        return crearCargueArchivoActualizacion.getResult();
    }

    private void actualizarCargueConsolaEstado(Long idCargue,
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        ActualizarCargueConsolaEstado actualizacion = new ActualizarCargueConsolaEstado(idCargue,
                consolaEstadoCargueProcesoDTO);
        actualizacion.execute();
    }
    private void actualizarProcesoConsolaEstado(Long idConsolaEstadoProcesoMasivo, ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        ActualizarProcesoConsolaEstado actualizacionProceso = new ActualizarProcesoConsolaEstado(idConsolaEstadoProcesoMasivo,
        consolaEstadoProcesoDTO);
        actualizacionProceso.execute();
    }

    /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple TO DO

     * @param consolaEstadoCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        logger.info("Entra a registrar----");
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
        registroConsola.execute();
    }
   /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple TO DO

     * @param consolaEstadoProcesoDTO
     */
    private Long registrarConsolaEstadoProcesosMasivos(ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        logger.info("Entra a registrar procesos");
        RegistrarCargueProcesoMasivo registroConsolaProceso = new RegistrarCargueProcesoMasivo(consolaEstadoProcesoDTO);
        registroConsolaProceso.execute();
        return registroConsolaProceso.getResult();
    }

    @Override

    public void afiliacionPensionados25AniosMasivo(ListasPensionadosDTO listas, UserDTO userDTO, Long idCargue){
        ConsolaEstadoProcesoDTO consolaEstadoCargueProcesos;
        List<TipoTransaccionEnum> cambioTipoPensionado = new ArrayList<>();;
        List<Afiliado25AniosDTO> ListaNuevos = listas.getListaNuevos();
        List<Afiliado25AniosExistenteDTO> ListaExistentes = listas.getListaExistetes();
        llenarListaNovedades(cambioTipoPensionado);
        consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
        consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("0"));
        consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
        Long idConProceso = registrarConsolaEstadoProcesosMasivos(consolaEstadoCargueProcesos);
        try {
            
            // 1. Validar reglas de negocio
            // 2. Enviar comunicado si falla alguna validacion
            // 3. Actualizar consola de cargue con nuevos registros
            if(ListaNuevos != null && !ListaNuevos.isEmpty()){
            logger.info("afiliacionPensionados25AniosMasivo listaNuevos" + ListaNuevos.size());

                // Map<String, Object> digitarDatosIdentificacionPersona
                for (Afiliado25AniosDTO afiliado : ListaNuevos) {
                logger.info("afiliacionPensionados25AniosMasivo archivo" + afiliado.toString());

                    if(validarValidacionesPensionado25anios(afiliado.getTipoIdentificacion(), afiliado.getNumeroIdentificacion(),
                        afiliado.getCorreoElectronico(), afiliado.getNombreCompleto(), afiliado.getDireccion(), afiliado.getTelefono().toString(),
                        idCargue, afiliado.getNoLinea()) == false){

                            consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
                            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
                            consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
                            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
                            consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
                            consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConProceso);
                            actualizarProcesoConsolaEstado(idConProceso, consolaEstadoCargueProcesos);
                            
                    AfiliadoInDTO afiliadoDTO = mapeoPensionado25AniosNuevo(afiliado, userDTO);
        
                    logger.info("2G pagador despues de mapeo "+ afiliadoDTO.getIdPagadorPension());
                    Map<String, Object> afiliadoMapeado = afilarPersonaMasivamente(afiliadoDTO,userDTO, afiliado.getArchivo());
        
                    for (Map.Entry<String, Object> entry : afiliadoMapeado.entrySet()) {
                        String clave = entry.getKey();
                        Object valor = entry.getValue();
                        System.out.println("Clave: " + clave + ", Valor: " + valor);
                    }
                    consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
                    consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
                    consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
                    consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
                    consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
                    consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConProceso);
                    actualizarProcesoConsolaEstado(idConProceso, consolaEstadoCargueProcesos);
                    
        
                    }
                }

                
            }else if(ListaExistentes != null && !ListaExistentes.isEmpty()){
            logger.info("afiliacionPensionados25AniosMasivo AfiliadoInDTO" + ListaExistentes.size());
            consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
            consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
            consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConProceso);
            actualizarProcesoConsolaEstado(idConProceso, consolaEstadoCargueProcesos);
            
                for (Afiliado25AniosExistenteDTO afiliado : ListaExistentes) {

                    if(validarValidacionesPensionado25anios(afiliado.getTipoIdentificacion(), afiliado.getNumeroIdentificación(),
                    "melissa.bernal@asopagos.com", afiliado.getNombreCompleto(),"123", "1672377",
                    idCargue, afiliado.getNoLinea()) == false){
                        if(afiliado.getEstadoPensionado().equals(EstadoAfiliadoEnum.ACTIVO)){
                            ConsultarClasificacionPensionado consultaClasificacion = new ConsultarClasificacionPensionado(afiliado.getNumeroIdentificación(),afiliado.getTipoIdentificacion());
                            consultaClasificacion.execute();
                            Object[] resultado = consultaClasificacion.getResult();
                            
                            long idrol = Long.parseLong(resultado[1].toString());
                            
                            InformacionActualizacionNovedadDTO info = new InformacionActualizacionNovedadDTO();
                            logger.info("afiliado.getTipoIdentificacion()"+afiliado.getTipoIdentificacion().name());
                            logger.info("afiliado.getNumeroIdentificación()"+afiliado.getNumeroIdentificación());
                            Map<String, Object> datos = new HashMap<>();
                            datos.put("tipoArchivo",TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL);
                            datos.put("tipoTransaccion",TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_25_ANIOS);
                            datos.put("canal", CanalRecepcionEnum.PRESENCIAL);
                            datos.put("TipoIdentificacion", afiliado.getTipoIdentificacion());
                            datos.put("NumeroIdentificacion", afiliado.getNumeroIdentificación());
                            datos.put("archivo", afiliado.getArchivo());
                            datos.put("idRol", idrol);
                            datos.put("fechaRecepcionDocumentos",afiliado.getFechaRecepcionDocumentos());
                            GestionarNovedad gestionNovedad = new GestionarNovedad(datos);
                            gestionNovedad.execute();    
        
                            }
                        
        
                        else{
                            ///afiliacion como pensionado en caso de tener otra clasificacion distinta a Pensionado
        
                            ConsultarDatosExistenteNoPensionado consulta = new ConsultarDatosExistenteNoPensionado(afiliado.getNumeroIdentificación(),afiliado.getTipoIdentificacion());
                            consulta.execute();
                            Afiliado25AniosDTO afiliacionPensionado = consulta.getResult();
                            afiliacionPensionado.setFechaRecepcionDocumentos(afiliado.getFechaRecepcionDocumentos());
                            afiliacionPensionado.setPagadorPension(afiliado.getPagadorPension());
                            afiliacionPensionado.setValorMesadaPensional(afiliado.getValorMesadaPensional());
        
                            logger.info("afiliado nueva afiliacion pensionado 2g "+ afiliacionPensionado.toString());
        
                            AfiliadoInDTO afiliadoDTO = mapeoPensionado25AniosNuevo(afiliacionPensionado, userDTO);
        
                            logger.info("2G pagador despues de mapeo "+ afiliadoDTO.getIdPagadorPension());
                            Map<String, Object> afiliadoMapeado = afilarPersonaMasivamente(afiliadoDTO,userDTO, afiliado.getArchivo());
        
                            for (Map.Entry<String, Object> entry : afiliadoMapeado.entrySet()) {
                                String clave = entry.getKey();
                                Object valor = entry.getValue();
                                System.out.println("Clave: " + clave + ", Valor: " + valor);
                            }
        
                        }
                    }

                }

                consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
                consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
                consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
                consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
                consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
                consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConProceso);
                actualizarProcesoConsolaEstado(idConProceso, consolaEstadoCargueProcesos);
                
            }
        } catch (Exception e) {
            consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
            consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
            consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CARGUE_PENSIONADOS_25_ANIOS);
            consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConProceso);
            consolaEstadoCargueProcesos.setError(ErroresConsolaEnum.ERROR_TECNICO);
            actualizarProcesoConsolaEstado(idConProceso, consolaEstadoCargueProcesos);
        }
    }

    public void llenarListaNovedades(List<TipoTransaccionEnum> cambioTipoPensionado){
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
		// cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_WEB);
	}

    public AfiliadoInDTO mapeoPensionado25AniosNuevo(Afiliado25AniosDTO Afiliado25AniosDTO, UserDTO userDTO){
        
        logger.info("MapsAfiladoTrabajador1:{}" + Afiliado25AniosDTO);
        PersonaDTO persona = new PersonaDTO();
        logger.info("MapsAfiladoTrabajador41");
        UbicacionDTO UbicacionDTO = new UbicacionDTO();
        logger.info("MapsAfiladoTrabajador42");
        // MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
        logger.info("MapsAfiladoTrabajador43");
        String telefonoFijo = null;
        String celular = null;
        Date d = null;
        Date fechainicioContrato = new Date();
        Long fechaNacimento = null;
        Long idUbicacion = null;
        int idDepartamento = 0;
        int idMunicipio = 0;
        String s3 = null;
        Long date = null;
        logger.info("MapsAfiladoTrabajador44");
        // String bloqueValidacion = "121-108-4";
        TipoIdentificacionEnum numeroIdentificacion = null;
        logger.info("MapsAfiladoTrabajador45");
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyyMMdd");

        /// afiliado trabajadors
        
        persona.setTipoIdentificacion(Afiliado25AniosDTO.getTipoIdentificacion());
        logger.info("Afiliado25AniosDTO.getTipoIdentificacion()"+Afiliado25AniosDTO.getTipoIdentificacion());
        persona.setNumeroIdentificacion(Afiliado25AniosDTO.getNumeroIdentificacion());
        logger.info("Afiliado25AniosDTO.getNumeroIdentificacion()"+Afiliado25AniosDTO.getNumeroIdentificacion());
        persona.setPrimerNombre(Afiliado25AniosDTO.getPrimerNombre());
        // logger.info("MapsAfiladoTrabajador4");
        persona.setSegundoNombre(Afiliado25AniosDTO.getSegundoNombre());
        // logger.info("MapsAfiladoTrabajador5");
        persona.setPrimerApellido(Afiliado25AniosDTO.getPrimerApellido());
        // logger.info("MapsAfiladoTrabajador6");
        persona.setSegundoApellido(Afiliado25AniosDTO.getSegundoApellido());
        logger.info("MapsAfiliado25AniosDTO" + Afiliado25AniosDTO.getFechaNacimiento());
        Integer number = Integer.parseInt(Afiliado25AniosDTO.getFechaNacimiento());
        try {
            logger.info("MapsAfiladoTrabajador8" + Afiliado25AniosDTO.getFechaNacimiento());
            Integer value = number;
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            d = originalFormat.parse(value.toString());

            logger.info("MapsAfiladoTrabajador81" + d);
        } catch (ParseException ex) {
            logger.info("MapsAfiladoTrabajador9");
            Logger.getLogger(AfiliacionPersonasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.info("MapsAfiladoTrabajador10");
        s3 = s2.format(d);
        logger.info("MapsAfiladoTrabajador11");
        fechaNacimento = Long.parseLong(s3);
        logger.info("MapsAfiladoTrabajador12");
        persona.setFechaNacimiento(d.getTime());
        logger.info("MapsAfiladoTrabajador14");
        persona.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
        persona.setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
        persona.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
        logger.info("MapsAfiladoTrabajador15");
        EstadoCivilEnum estadoCivil = null;
						for (EstadoCivilEnum civil : EstadoCivilEnum.values()) {
							if (civil.getDescripcion().equals(Afiliado25AniosDTO.getEstadoCivil())) {
								estadoCivil = civil;
								break; // Se ha encontrado el valor, se sale del bucle
							}
						}
      
        
        persona.setEstadoCivil(estadoCivil);

        GeneroEnum generoP = null;
						for (GeneroEnum genero : GeneroEnum.values()) {
							if (genero.getCodigo().equals(Afiliado25AniosDTO.getGenero())) {
								generoP = genero;
								break; // Se ha encontrado el valor, se sale del bucle
							}
						}

        // GeneroEnum genero = GeneroEnum.valueOf(generoP);
        persona.setGenero(generoP);
        logger.info("MapsAfiladoTrabajador17");
        // medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
        // medioDePagoModeloDTO.setEfectivo(Boolean.TRUE);
        // sitioPago
        // tarjeta ------------------faltan
        logger.info("MapsAfiladoTrabajador18");
        // persona.setMedioDePagoPersona(medioDePagoModeloDTO);
        UbicacionDTO.setIdDepartamento(retornarId(Afiliado25AniosDTO.getDepartamento())); 
        UbicacionDTO.setIdMunicipio(retornarId(Afiliado25AniosDTO.getMunicipio()));
        UbicacionDTO.setDireccion(Afiliado25AniosDTO.getDireccion());
        if(Afiliado25AniosDTO.getCorreoElectronico()!=null){
            UbicacionDTO.setCorreoElectronico(Afiliado25AniosDTO.getCorreoElectronico());
            UbicacionDTO.setAutorizacionEnvioEmail(true);

        }else{
            UbicacionDTO.setAutorizacionEnvioEmail(false);

        }
        if(Afiliado25AniosDTO.getTelefono()!=null){
            telefonoFijo = String.valueOf(Afiliado25AniosDTO.getTelefono());
            UbicacionDTO.setTelefonoFijo(telefonoFijo);

        }
        if(Afiliado25AniosDTO.getCelular()!=null){
            celular = String.valueOf(Afiliado25AniosDTO.getCelular());
            UbicacionDTO.setTelefonoCelular(celular);

        }
        
        UbicacionDTO.setTipoUbicacion(TipoUbicacionEnum.UBICACION_PRINCIPAL);
        persona.setUbicacionDTO(UbicacionDTO);
        persona.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
        persona.setClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
        MedioDePagoModeloDTO pago= new MedioDePagoModeloDTO();
        pago.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
        pago.setEfectivo(true);
        pago.setDisponeTarjeta(false);
        pago.setTarjetaMultiservicio(false);
        ConsultarIdSitioPagoPredeterminado consultarIdSitioPagoPredeterminado = new ConsultarIdSitioPagoPredeterminado();
        consultarIdSitioPagoPredeterminado.execute();
        Long idSitioDePagoPredeterminado = consultarIdSitioPagoPredeterminado.getResult();
        pago.setSitioPago(idSitioDePagoPredeterminado);
        PersonaModeloDTO personaModelo = new PersonaModeloDTO();
        personaModelo.setTipoIdentificacion(Afiliado25AniosDTO.getTipoIdentificacion());
        personaModelo.setNumeroIdentificacion(Afiliado25AniosDTO.getNumeroIdentificacion());
        pago.setPersona(personaModelo);
        persona.setMedioDePagoPersona(pago);
        persona.setCabezaHogar(false);
        persona.setAutorizacionEnvioEmail(Afiliado25AniosDTO.getCorreoElectronico()!= null?  true:false);
        persona.setAutorizaUsoDatosPersonales(true);
        persona.setResideSectorRural(false);
         try {
            logger.info("MapsAfiladoTrabajador8" + Afiliado25AniosDTO.getFechaRecepcionDocumentos());
            Integer value = number;
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            d = originalFormat.parse(Afiliado25AniosDTO.getFechaRecepcionDocumentos());
            persona.setFechaExpedicionDocumento(d);   
        } catch (ParseException ex) {

        }
        AfiliadoInDTO afiliadoIn = new AfiliadoInDTO();
        logger.info("MapsAfiladoTrabajador20");
        afiliadoIn.setPersona(persona);
        afiliadoIn.setTipoAfiliado(TipoAfiliadoEnum.PENSIONADO);
        afiliadoIn.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        afiliadoIn.setFechaInicioContrato(fechainicioContrato);
        afiliadoIn.setFechaAfiliacion(fechainicioContrato);
        afiliadoIn.setValorSalarioMesada(BigDecimal.valueOf(Afiliado25AniosDTO.getValorMesadaPensional()));
        afiliadoIn.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);

        ///////////pagador pension
        
        ConsultarListaValores consultarEntidades = new ConsultarListaValores(46, null, null);
        consultarEntidades.execute();
        List<ElementoListaDTO> entidades = consultarEntidades.getResult();
        logger.info("entidades.getIdLista() "+entidades.size());
        boolean entidadEncontrada = false;

        for (ElementoListaDTO entidad : entidades) {
        logger.info("entidad.getAtributos() "+entidad.getValor());

            if(entidad.getValor().equals(Afiliado25AniosDTO.getPagadorPension())){
            logger.info("entidad.getAtributos() entra ifffffffffffff");
            // if (entidad.getIdentificador() instanceof Short){
            logger.info("entidad.getAtributos() entra ifffffffffffff2222"+ entidad.getIdentificador());

                afiliadoIn.setIdPagadorPension(((Integer)entidad.getIdentificador()).shortValue());

                break;
            // }
                

            }
        }
        

        // logger.info("MapsAfiladoTrabajador22" + afiliadoInDTO);
        // logger.info("MapsAfiladoTrabajador22" + afiliadoIn.toString());
        // AfiliadoInDTO afiliadoDTO = crearAfiliado(afiliadoIn); 
        logger.info("2g Paso crear afiliado" + afiliadoIn.toString());

        return afiliadoIn;
        // Map<String, Object> afiliado = afilarPersonaMasivamente(afiliadoInDTO, userDTO);
        // afiliado.get("idSolicitudGlobal");
        // logger.info("MapsAfiladoTrabajador23");
        // logger.info("MapsAfiladoTrabajador23" + afiliado.get("idSolicitudGlobal"));
    }

    public short retornarId(String cadena){
        String[]  partes= cadena.split("/");
        if (partes.length >= 2) {
            return Short.parseShort(partes[1]);
        } 
        return 0;
    }

    @Override
    public Map<String, Object> afilarPersonaMasivamente(AfiliadoInDTO afiliado,
            UserDTO userDTO, String archivo) {
        logger.info("afiliado???? : { }" + afiliado);
        logger.info("afiliado???? : { }" + afiliado.getIdPagadorPension());
        logger.info("2g afiliado archivo" + archivo);
        Map<String, Object> parametrosProceso = null;
        ///inicio  para poder  afiliar///
        logger.info("afiliado ingresa y sse setea " + afiliado);
        String nuevoAfiliado = null;
        Long idSolicitudGlobal = null;
        Long idAfiliado = null;
        Long idInstanciaProcesoAfiliacionPersona = null;
        String numeroRadicado = null;
        Long comunicadoCreado = null;

        AfiliadoInDTO afiliadoDTO = null;
        logger.info("antes de if afiliado " + afiliado.getPersona());
        logger.info("antes de if tipoidentificacion " + afiliado.getPersona().getTipoIdentificacion());
        logger.info("antes de if numeroidentificacion " + afiliado.getPersona().getNumeroIdentificacion());
        logger.info("antes de if tipoafiliado " + afiliado.getTipoAfiliado());
        logger.info("antes de if canalrecepscion " + afiliado.getCanalRecepcion());
        logger.info("antes de if getIdEmpleador " + afiliado.getIdEmpleador());
        Date fechaRecDocumentos = afiliado.getPersona().getFechaExpedicionDocumento();
        if (fechaRecDocumentos == null) {
            fechaRecDocumentos = new Date();
        }
        afiliado.getPersona().setFechaExpedicionDocumento(null);


        if (afiliado != null && (afiliado.getPersona() != null) && afiliado.getPersona().getTipoIdentificacion() != null
                && afiliado.getPersona().getNumeroIdentificacion() != null && (afiliado.getTipoAfiliado() != null)
                && (afiliado.getCanalRecepcion() != null) ) {

            // se Crea el afiliado
            logger.info("afilarPersonaMasivamente :: Crear el afiliado");
            logger.info("afiliado???? : { }" + afiliado.getIdPagadorPension());


            // if(afiliado.getIdRolAfiliado() == null){
                afiliadoDTO = crearAfiliado(afiliado);
            // }
            // InformacionLaboralTrabajadorDTO infoLaboral = new InformacionLaboralTrabajadorDTO();
            // infoLaboral.setMunicipioDesempenioLabores(afiliado.getIdMunicipioDesempenioLabores());
            // infoLaboral.setIdRolAfiliado(afiliadoDTO.getIdRolAfiliado());
            // infoLaboral.setIdSucursalEmpleador(afiliado.getSucursalEmpleadorId());
            // infoLaboral.setIdEmpleador(afiliado.getIdEmpleador());
            // infoLaboral.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
            // infoLaboral.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());



            // if(infoLaboral.getMunicipioDesempenioLabores()!= null && infoLaboral.getIdRolAfiliado()!= null &&
            // infoLaboral.getIdSucursalEmpleador()!= null &&infoLaboral.getIdEmpleador()!= null
            // && infoLaboral.getTipoIdentificacion()!= null && infoLaboral.getNumeroIdentificacion()!= null ){
            //     GuardarInformacionLaboral guardarInformacionLaboral = new GuardarInformacionLaboral(infoLaboral);
            //     guardarInformacionLaboral.execute();
            // }
          

            logger.info("afiliadoDTO^^2g" + afiliadoDTO.getPersona().getCabezaHogar());
            logger.info("afiliadoDTO^^2g" + afiliadoDTO.getPersona().getMedioDePagoPersona().getTarjetaMultiservicio());
            idAfiliado = afiliadoDTO.getIdAfiliado();
            logger.info("idAfiliado^^" + idAfiliado);
            if (idAfiliado != null && idAfiliado > 0) {
                // Se crea la solicitud de afiliacion de la persona
                logger.info(
                        "afilarPersonaMasivamente :: Se crea la solicitud de afiliacion de la persona");
                afiliado.setIdAfiliado(idAfiliado);
                afiliado.setIdRolAfiliado(afiliadoDTO.getIdRolAfiliado());
                logger.info("afiliado id YYY^^" + idAfiliado);
                logger.info("rol afiliado^^" + afiliadoDTO.getIdRolAfiliado());
                idSolicitudGlobal = crearSolicitudAfiliacionPersona(afiliado);

            }

            logger.info("idSolicitudGlobal" + idSolicitudGlobal);
            VerificarSolicitudesEnCurso verificarSolicitudesEnCurso = new VerificarSolicitudesEnCurso(true, afiliado.getPersona().getNumeroIdentificacion(), idSolicitudGlobal, afiliado.getPersona().getTipoIdentificacion(), null, null, null);
            verificarSolicitudesEnCurso.execute();
            logger.info("idSolicitudGlobal 2g" + afiliado.getPersona().getMedioDePagoPersona().getTarjetaMultiservicio());

            ActualizarMedioDePagoPersona actualizarMedioDePagoPersona = new ActualizarMedioDePagoPersona(afiliado.getPersona().getMedioDePagoPersona());
            actualizarMedioDePagoPersona.execute();
            ValidacionDTO list = verificarSolicitudesEnCurso.getResult();
            logger.info("resultado : { }" + list.getResultado());
            String aprobada = "APROBADA";
            logger.info("list.getResultado()" + aprobada);
            // list.setResultado(ResultadoValidacionEnum.APROBADA);
            //  if (list.getResultado().equals(aprobada)) {

            if (idSolicitudGlobal != null && idSolicitudGlobal > 0) {
                logger.info(
                        "afilarPersonaMasivamente :: Valido que el usuario que envian en la peticion sea el registrado para la caja de compensacion");

                // logger.debug("clave " + userDTO.getNombreUsuario());
                logger.info(
                        "afilarPersonaMasivamente :: se genera numero de radicado");

                // genera el numero de radicado correspondiente y lo
                // actualiza en relacion en la solicitud
                numeroRadicado = generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());

                parametrosProceso = new HashMap<>();
                parametrosProceso.put("idSolicitudGlobal", idSolicitudGlobal);
                parametrosProceso.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);

            }
            // } else {
            logger.info("detalle de verificar solicitud ****" + list.getDetalle());
            // return null;

        }

        logger.info("parametrosproceso***" + parametrosProceso);
        Long idInstanciaProceso = iniciarProcesoAfiliacionPersona(idSolicitudGlobal, numeroRadicado, userDTO);

        logger.info("idInstanciaProceso***" + idInstanciaProceso);
        RadicarSolicitudAbreviadaDTO inDTO = new RadicarSolicitudAbreviadaDTO();
        inDTO.setIdSolicitudGlobal(idSolicitudGlobal);
        inDTO.setCanal(afiliado.getCanalRecepcion());
        inDTO.setMetodoEnvio(FormatoEntregaDocumentoEnum.FISICO);
        inDTO.setTipoRadicacion(TipoRadicacionEnum.ABREVIADA);
        inDTO.setValorSalarioMesada(afiliado.getValorSalarioMesada());
        inDTO.setTipoSolicitante(afiliado.getTipoAfiliado());
        inDTO.setIdRolAfiliado(afiliadoDTO.getIdRolAfiliado());
        inDTO.setIdEntidadPagadora(1L);
        inDTO.setRegistrarIntentoAfiliacion(true);


        logger.info("va a radicar***:{}" + inDTO.getValorSalarioMesada());

        radicarSolicitudAbreviadaAfiliacionPersonasMasivas(inDTO, userDTO, idInstanciaProceso, numeroRadicado);
        logger.info("salio de radicar&&&");

        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
        logger.info("quedo radicada");
        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
        logger.info("actualizaEstadoAfiliado");
        actualizarEstadoRolAfiliado(afiliadoDTO.getIdRolAfiliado(), EstadoAfiliadoEnum.ACTIVO);
        
        logger.info("variablesComunicado98--");
        Map<String, Object> variablesComunicado = resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, idSolicitudGlobal, idInstanciaProceso);
        logger.info("variablesComunicado99:--" + variablesComunicado);

        // solicitudAfiliacionPersona
        // Integer datosT = datosTemporales(idSolicitudGlobal,);
        //TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,ParametrosComunicadoDTO parametroComunicadoDTO);
        ParametrosComunicadoDTO parametroComunicadoDTO = new ParametrosComunicadoDTO();
        parametroComunicadoDTO.setIdPlantillaComunicado(2L);
        ArrayList<Long> listaIdSolicitud = new ArrayList<Long>(Arrays.asList(idSolicitudGlobal));
        logger.info("listaIdSolicitud" + listaIdSolicitud);
        parametroComunicadoDTO.setIdsSolicitud(listaIdSolicitud);
        // parametroComunicadoDTO.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());
        // parametroComunicadoDTO.setNumeroRadicacion(numeroRadicado);
        logger.info("afiliadoDTO.getPersona().getIdPersona()" + afiliadoDTO.getPersona().getIdPersona());
        //parametroComunicadoDTO.setIdPersona(afiliadoDTO.getPersona().getIdPersona());
        // parametroComunicadoDTO.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
        parametroComunicadoDTO.setTipoSolicitante(TipoTipoSolicitanteEnum.PERSONA);
        /*Map<String, Object> params = new HashMap<>();
        params.put("tipoIdentificacion", afiliado.getPersona().getTipoIdentificacion());
        params.put("numeroIdentificacion", afiliado.getPersona().getNumeroIdentificacion());
        params.put("idCartera", null);*/
        logger.info("variablesCouminicado");
        parametroComunicadoDTO.setParams(variablesComunicado);
        logger.info("guardarObtenerComunicadoECM1");
        InformacionArchivoDTO informacionArchivoDTO = guardarObtenerComunicadoECM(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION, EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER,
                parametroComunicadoDTO);

        logger.info("guardarObtenerComunicadoECM2:{ } " + informacionArchivoDTO.getDataFile().toString());
        logger.info("informacionArchivoDTO.getIdentificadorDocumento():{ } " + informacionArchivoDTO.getIdentificadorDocumento());
        logger.info("informacionArchivoDTO.getDescription()():{ } " + informacionArchivoDTO.getDescription());

        // plantillas inicia
        Map<String, Object> params = parametroComunicadoDTO.getParams();
        String identificador = "";

        logger.info("antes de if tipoIdentificacion");

        if (params.containsKey("tipoIdentificacion")) {
            logger.info("entro1");
            parametroComunicadoDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(params.get("tipoIdentificacion").toString()));
        }

        if (params.containsKey("numeroIdentificacion")) {
            parametroComunicadoDTO.setNumeroIdentificacion(params.get("numeroIdentificacion").toString());
            identificador = params.get("numeroIdentificacion").toString();
        }
        logger.info("antes de if1//");
        if (params.containsKey("idCartera") && params.get("idCartera") != null) {
            parametroComunicadoDTO.setIdCartera(new Long(params.get("idCartera").toString()));
            identificador = params.get("idCartera").toString();
        }

        logger.info("antes plaCom1::");
        PlantillaComunicado plaCom = resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, parametroComunicadoDTO);
        logger.info("antes plaCom2::" + plaCom.getCuerpo());
        String comunicadoCompleto = plaCom.getEncabezado() + plaCom.getCuerpo() + plaCom.getPie();
        logger.info("comunicadoCompleto::" + comunicadoCompleto);
        // plantillas termina

        Comunicado comunicado = new Comunicado();
        PlantillaComunicado plantillaComunicado = new PlantillaComunicado();
        plantillaComunicado.setIdPlantillaComunicado(2L);
        plantillaComunicado.setEtiqueta(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER);

        comunicado.setIdSolicitud(idSolicitudGlobal);
        comunicado.setEmail("comunicadospruebasmasivas@gmail.com");
        comunicado.setDestinatario(afiliadoDTO.getPersona().getUbicacionDTO().getCorreoElectronico());
        comunicado.setPlantillaComunicado(plantillaComunicado);
        Date fechaComunicado = new Date();
        comunicado.setFechaComunicado(fechaComunicado);
        comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
        comunicado.setMedioComunicado(MedioComunicadoEnum.ENVIADO);
        comunicado.setEmpleador(afiliado.getIdEmpleador());
        comunicado.setTextoAdicionar("adicionar");
        comunicado.setIdentificadorArchivoComunicado(informacionArchivoDTO.getIdentificadorDocumento());
        comunicado.setMensajeEnvio(comunicadoCompleto);
        comunicadoCreado = crearComunicado(comunicado);
        logger.info("comunicadoCreado" + comunicadoCreado);
        logger.info("afilioTrabajador" + parametrosProceso);
        logger.info("afilioTrabajador" + archivo);
        logger.info("afilioTrabajador 2g" + afiliado.getPersona().getClasificacion().equals(ClasificacionEnum.FIDELIDAD_25_ANIOS));
        /// inicia fechaRecepcionDocumentos////
         List<Long> lstResult = null;
         Date date = new Date();
        long longDate=date.getTime();
         String nombreRequisito = null;
         String texto = null;
         Long idReq = 91L;
         if(afiliado.getPersona().getClasificacion().equals(ClasificacionEnum.FIDELIDAD_25_ANIOS)){
         nombreRequisito = "Soporte afiliacion";
         texto = "Se revisa:<br />-Soporte afiliacion.<br />-Firma del trabajador";
         idReq = 90L;
         }else{
         nombreRequisito = "Formulario solicitud afiliación o reintegro trabajador dependiente";
         texto = "Se revisa:<br />-Formulario completamente diligenciado sin enmendaduras ni tachones<br />-Firma de representante legal o funcionario designado (del empleador) y sello.<br />-Firma del trabajador";
         archivo = "1";
         
        }
         
        ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
        ItemChequeoDTO itemChequeoDto1 = new ItemChequeoDTO();
        itemChequeoDto1.setIdSolicitudGlobal(idSolicitudGlobal);
        itemChequeoDto1.setIdRequisito(idReq);
        itemChequeoDto1.setNombreRequisito(nombreRequisito);
        itemChequeoDto1.setTextoAyuda(texto);
        itemChequeoDto1.setTipoRequisito(TipoRequisitoEnum.ESTANDAR);
        itemChequeoDto1.setFechaRecepcionDocumentos(fechaRecDocumentos.getTime());
        itemChequeoDto1.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
        itemChequeoDto1.setIdentificadorDocumento(archivo);
        itemChequeoDto1.setVersionDocumento(Short.MIN_VALUE);
        itemChequeoDto1.setCumpleRequisito(Boolean.TRUE);
        afiliado.getPersona().setFechaExpedicionDocumento(null);

        logger.info("itemChequeoDto1.getIdentificadorDocumento" + itemChequeoDto1.getIdentificadorDocumento());


        List<ItemChequeoDTO> itemChequeoDto = new ArrayList<ItemChequeoDTO>();
        itemChequeoDto.add(itemChequeoDto1);


        logger.info("longDate" + longDate);
        listaChequeo.setFechaRecepcionDocumentos(fechaRecDocumentos.getTime());
        listaChequeo.setIdSolicitudGlobal(idSolicitudGlobal);
        listaChequeo.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());
        listaChequeo.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
        listaChequeo.setListaChequeo(itemChequeoDto);
        VerificarRequisitosDocumentalesDTO verificarRequisitos = new VerificarRequisitosDocumentalesDTO();
        verificarRequisitos.setCumpleRequisitosDocumentales(Boolean.TRUE);
        verificarRequisitos.setDocumentosFisicos(FormatoEntregaDocumentoEnum.FISICO);
        verificarRequisitos.setIdSolicitudGlobal(idSolicitudGlobal);
        verificarRequisitos.setTipoClasificacion(ClasificacionEnum.FIDELIDAD_25_ANIOS);
        verificarRequisitos.setListaChequeo(listaChequeo);
        // verificarRequisitosDocumentalesPersona(verificarRequisitos, userDTO);
        lstResult = crearListaChequeoAfiliacionPersona(verificarRequisitos.getListaChequeo());
        ///// fin fechaRecepcion documentos
        return parametrosProceso;

    }

    /* @see
     * com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#radicarSolicitudAbreviadaAfiliacionPersona(com.asopagos.afiliaciones.personas.composite.dto.RadicarSolicitudAbreviadaDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoRegistroContactoEnum radicarSolicitudAbreviadaAfiliacionPersonasMasivas(RadicarSolicitudAbreviadaDTO inDTO, UserDTO userDTO, Long idInstanciaProcesos, String numeroRadicado) {
        logger.info(
                "radicarSolicitudAbreviadaAfiliacionPersonasMasivas " + inDTO.getCanal());

        boolean incluyenBeneficiarios = false;

        Long idSolicitudGlobal = null;
        Long idRolAfiliado = null;
        Long idTarea = null;
        boolean docFisicos = true;
        boolean registroIntentoAfiliacion = false;
        Long idEmpleadorAfi = null;
        String idInstanciaProceso = null;
        idInstanciaProceso = Long.toString(idInstanciaProcesos);
        idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
        logger.info("idAtrea" + idTarea);
        TipoRadicacionEnum tipoRadicacionSolcitud = null;

        SolicitudDTO solicitudDTO = null;
        AfiliadoInDTO afiliadoInDTO = null;
        IntentoAfiliacionInDTO intentoAfiliacion = null;
        SolicitudAfiliacionPersonaDTO solAfiliacionPersonaDTO = null;
        // Representa el salario registrado en pantalla, especial caso Reintegro
        BigDecimal salario = null;

        List<BeneficiarioDTO> lstBeneficiariosDTO = inDTO.getBeneficiarios();

        if ((inDTO.getIdSolicitudGlobal() == null) || (inDTO.getTipoSolicitante() == null) || (inDTO.getTipoRadicacion() == null)) {
            logger.info(
                    "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No viene parametros validos para radicar la solicitud de afilacion de persona");
            // No viene parametros validos para radicar la solicitud de
            // afilacion de persona
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        } else {
            idSolicitudGlobal = inDTO.getIdSolicitudGlobal();
            tipoRadicacionSolcitud = inDTO.getTipoRadicacion();

            if (inDTO.getRegistrarIntentoAfiliacion()) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: se registra el intento de afiliación, si se solicita desde pantalla.");
                // se registra el intento de afiliación, si se solicita desde
                // pantalla
                /*   registroIntentoAfiliacion = true;
                intentoAfiliacion = new IntentoAfiliacionInDTO();
                intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.DECLINACION_VOLUNTARIA_USUARIO);
                intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
                intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                intentoAfiliacion.setFechaInicioProceso(new Date());
                registrarIntentoAfiliacion(intentoAfiliacion);*/

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a Registrar intento de afiliación");
                // Se actualiza el estado de la solicitud a Registrar intento de
                // afiliación
                //  actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION);

                // Se realiza la terminacion de tarea en el BPM para el proceso
                // de
                // Afiliación personas presencial
                logger.info("idTarea" + idTarea);
                logger.info("tipoRadicacionSolcitud" + tipoRadicacionSolcitud);
                logger.info("incluyenBeneficiarios" + incluyenBeneficiarios);
                logger.info("registroIntentoAfiliacion" + registroIntentoAfiliacion);
                logger.info("docFisicos" + docFisicos);
                logger.info("numeroRadicado" + numeroRadicado);
                terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
                        registroIntentoAfiliacion, docFisicos, numeroRadicado);
                logger.info("hace solictud%%%");
                solicitudDTO = new SolicitudDTO();
                solicitudDTO.setFechaRadicacion(new Date());
                solicitudDTO.setCanalRecepcion(inDTO.getCanal());
                solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                solicitudDTO.setTipoRadicacion(tipoRadicacionSolcitud);
                solicitudDTO.setMetodoEnvio((inDTO.getMetodoEnvio() != null) ? inDTO.getMetodoEnvio() : null);
                solicitudDTO.setIdInstanciaProceso(idInstanciaProceso);
                logger.info("idsolicitudTTTTT" + idSolicitudGlobal);
                logger.info("solicitudDTO" + solicitudDTO.toString());
                logger.info("solicitudDTO" + solicitudDTO);
                solicitudDTO.setEstadoSolicitud("APROBADA");
                solicitudDTO.setResultadoProceso(ResultadoProcesoEnum.APROBADA);
                logger.info("solicitudDTO" + solicitudDTO.getEstadoSolicitud());
                logger.info("ACTUALIZA LA SOLITUD INICIA");
                actualizarSolicitudAfiliacionPersonaMasivas(idSolicitudGlobal, solicitudDTO);
                logger.info("ACTUALIZA LA SOLITUD FIN");
                actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                if (salario != null) {
                    afiliadoInDTO.setValorSalarioMesada(salario);
                }
                logger.info(
                        "--AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza al trabajador en estado “Activo”");
                // Se actualiza al trabajador
                // actualizarAfiliado(afiliadoInDTO);

            } else {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Valida que todos los beneficiarios tengan el estado AFIALIABLE para poder radicar");

                solAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
                logger.info("solAfiliacionPersonaDTO :{} " + solAfiliacionPersonaDTO);
                logger.info("solAfiliacionPersonaDTO544 :{} " + solAfiliacionPersonaDTO.getIdInstanciaProceso());
                //Consultar temporal para guardarlo
                ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(idSolicitudGlobal);
                consultTemporal.execute();
                String dataTemporal = consultTemporal.getResult();

                if (dataTemporal != null && !dataTemporal.isEmpty()) {
                    GuardarTemporalAfiliacionPersona guardarTemporal = new GuardarTemporalAfiliacionPersona();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        guardarTemporal = mapper.readValue(dataTemporal, GuardarTemporalAfiliacionPersona.class);
                    } catch (IOException e) {
                        logger.error(
                                "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Ocurrio un error consultar los datos temporales",
                                e);
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                    }

                    if (guardarTemporal.getInfoLaboral() != null) {
                        guardarTemporal.getInfoLaboral().setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                        GuardarInformacionLaboral guardarInfoLaboral = new GuardarInformacionLaboral(guardarTemporal.getInfoLaboral());
                        guardarInfoLaboral.execute();
                        // Se ajusta que siempre sea el mismo salario CASO Reintegro
                        if (guardarTemporal.getInfoLaboral().getValorSalario() != null) {
                            salario = guardarTemporal.getInfoLaboral().getValorSalario();
                        }
                    }

                    if (guardarTemporal.getModeloInformacion() != null) {
                        GuardarDatosIdentificacionYUbicacion guardarInforUbicacion = new GuardarDatosIdentificacionYUbicacion(
                                guardarTemporal.getModeloInformacion());
                        guardarInforUbicacion.execute();
                        if (guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos() != null) {
                            salario = guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos();
                        }
                    }

                    if (guardarTemporal.getBeneficiarios() != null && !guardarTemporal.getBeneficiarios().isEmpty()
                            && inDTO.getTipoRadicacion().equals(TipoRadicacionEnum.ABREVIADA)) {
                        lstBeneficiariosDTO = new ArrayList<BeneficiarioDTO>();
                        for (BeneficiarioDTO beneficiarioDTO : guardarTemporal.getBeneficiarios()) {
                            if (beneficiarioDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.AFILIABLE)) {
                                lstBeneficiariosDTO.add(beneficiarioDTO);
                            }
                        }
                        if (!lstBeneficiariosDTO.isEmpty() && lstBeneficiariosDTO.size() > 0) {
                            RegistrarPersonasBeneficiariosAbreviada registrarPersonasSrv = new RegistrarPersonasBeneficiariosAbreviada(
                                    solAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado(), lstBeneficiariosDTO);
                            registrarPersonasSrv.execute();
                        }
                    }
                }

                afiliadoInDTO = solAfiliacionPersonaDTO.getAfiliadoInDTO();
                idEmpleadorAfi = afiliadoInDTO.getIdEmpleador();
                Empleador empleador = null;
                if (afiliadoInDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)) {
                    empleador = buscarEmpleador(afiliadoInDTO.getIdEmpleador());
                }

                ResultadoRegistroContactoEnum resultadoServ = null;

                // Se realizan las validaciones pertinentes a solicitudes ya
                // existentes en proceso
                Map<String, String> datosValidacion = new HashMap<String, String>();
                datosValidacion.put("tipoIdentificacion", afiliadoInDTO.getPersona().getTipoIdentificacion().toString());
                datosValidacion.put("numeroIdentificacion", afiliadoInDTO.getPersona().getNumeroIdentificacion());

                if (empleador != null) {

                    datosValidacion.put("tipoIdentificacionEmpleador",
                            empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                    datosValidacion.put("numeroIdentificacionEmpleador", empleador.getEmpresa().getPersona().getNumeroIdentificacion());

                }
                datosValidacion.put("tipoAfiliado", afiliadoInDTO.getTipoAfiliado().getName());
                // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación
                datosValidacion.put("idSolicitud", idSolicitudGlobal.toString());
                ValidarPersonas validarPersona = new ValidarPersonas("121-107-1", ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        afiliadoInDTO.getTipoAfiliado().toString(), datosValidacion);
                validarPersona.execute();
                List<ValidacionDTO> list = validarPersona.getResult();
                ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, list);
                logger.info("validacionExistenciaSolicitud.getResultado(): " + validacionExistenciaSolicitud.getResultado());

                logger.info("entra if no aprobada resultado");
                resultadoServ = ResultadoRegistroContactoEnum.AFILIACION_EN_PROCESO;

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a radicada");
                // Se actualiza el estado de la solicitud a radicada

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se registra la información de la solicitud con los campos indicados");
                // Se registra la información de la solicitud con los campos
                // indicados

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza la fecha de afiliación del afiliadoa la caja de compensación");
                // Se actualiza el rol Afiliado
                // Se actualiza la fecha de afiliación del afiliadoa la caja de compensación
                // Se actualiza el estado de afiliación a ACTIVO
                idRolAfiliado = inDTO.getIdRolAfiliado();
                RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
                rolAfiliadoModeloDTO.setIdRolAfiliado(inDTO.getIdRolAfiliado());
                rolAfiliadoModeloDTO.setFechaAfiliacion(new Date().getTime());
                rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                actualizarRolAfiliado(rolAfiliadoModeloDTO);

                logger.info("metodoenvio???" + solAfiliacionPersonaDTO.getMetodoEnvio());
                if (solAfiliacionPersonaDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
                    docFisicos = true;
                }
                if (inDTO.getMetodoEnvio() != null) {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la documentación dependiendo de lo indicado al servicio");
                    // actualizo el estado de la documentación dependiendo de lo
                    // indicado al servicio
                    switch (inDTO.getMetodoEnvio()) {
                        case ELECTRONICO:
                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.ENVIADA_AL_BACK);
                            break;
                        case FISICO:
                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                            docFisicos = true;
                            break;
                    }
                }

                /*
                 * TODO esta parte esta por realizarse posteriormente ya que aun
                 * no se encuentra definida: "Almacena y registra la información
                 * de la solicitud. Nota: En caso de que el afiliado principal
                 * y/o beneficiarios, sea reintegrado (estuvo afiliado
                 * previamente en la Caja de Compensación), el sistema debe
                 * guardar la información histórica (no sobreescribirla con los
                 * datos nuevos)."
                 *
                 */
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el valor del campo salario/mesada del afiliado principal");
                // Actualizo el valor del campo salario/mesada del afiliado
                // principal
                if (salario != null) {
                    afiliadoInDTO.setValorSalarioMesada(salario);
                }
                logger.info(
                        "--AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza al trabajador en estado “Activo”");
                // Se actualiza al trabajador
                actualizarAfiliado(afiliadoInDTO);

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se calcula la categoría del afiliado deacuerdo a las condiciones establecidas en HU-121-341 Definir estructura persona/aportante");
                // Se calcula la categoría del afiliado deacuerdo a las
                // condiciones
                // establecidas en HU-121-341 Definir estructura
                // persona/aportante
                calcularCategoriaAfiliado(afiliadoInDTO);

                logger.info(
                        "xxxAfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se realiza la terminacion de tarea en el BPM para el proceso de Afiliación personas presencial");
                // Se realiza la terminacion de tarea en el BPM para el proceso
                // de
                // Afiliación personas presencial
                // idInstanciaProceso = solAfiliacionPersonaDTO.getIdInstanciaProceso();
                logger.info("idInstanciaProceso" + idInstanciaProceso);

                if (idTarea != null) {

                    terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
                            registroIntentoAfiliacion, docFisicos, numeroRadicado);

                } else {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No se logro obtener el idTarea");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                }

            }
        }
        logger.info(
                "Finaliza AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona( RadicarSolicitudAbreviadaDTO, UserDTO )");
        return null;
    }

    
    public Long afiliarBeneficiarioMasivamente(AfiliarBeneficiarioDTO inDTO, Long idSolicitudTrabajador, TipoIdentificacionEnum tipoIdentificacion, UserDTO userDTO, AfiliadosACargoMasivosDTO afiliadosACargoMasivosDTO) {
        logger.info("Inicia AfiliacionPersonasCompositeBusiness.afiliarBeneficiario(AfiliarBeneficiarioDTO, UserDTO)");
        ClasificacionEnum clasificacion ;
        Long persona = null;
        PersonaModeloDTO personaDTO = new PersonaModeloDTO();
         logger.info("es HijoPadre1:{}" + inDTO);
        logger.info("es HijoPadre2:{}" + inDTO.getNumeroIdentificacionAfiliado());
        logger.info("es HijoPadre3:{}" + inDTO.getBeneficiarioHijoPadre());
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        Ubicacion ubicacion = new Ubicacion();
        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() != null)) {
            logger.info("entra if PerronsaHijoPadre:{}");
            personaDTO.setTipoIdentificacion(inDTO.getBeneficiarioHijoPadre().getPersona().getTipoIdentificacion());
            personaDTO.setNumeroIdentificacion(inDTO.getBeneficiarioHijoPadre().getPersona().getNumeroIdentificacion());
            personaDTO.setGenero(inDTO.getBeneficiarioHijoPadre().getPersona().getGenero());
            personaDTO.setPrimerApellido(inDTO.getBeneficiarioHijoPadre().getPersona().getPrimerApellido());
            personaDTO.setSegundoApellido(inDTO.getBeneficiarioHijoPadre().getPersona().getSegundoApellido());
            personaDTO.setPrimerNombre(inDTO.getBeneficiarioHijoPadre().getPersona().getPrimerNombre());
            personaDTO.setSegundoNombre(inDTO.getBeneficiarioHijoPadre().getPersona().getSegundoNombre());
            personaDTO.setNivelEducativo(inDTO.getBeneficiarioHijoPadre().getPersona().getNivelEducativo());
            personaDTO.setFechaNacimiento(inDTO.getBeneficiarioHijoPadre().getPersona().getFechaNacimiento());
            ubicacion = inDTO.getBeneficiarioHijoPadre().getUbicacion().convertToEntity();
            ubicacionModeloDTO.convertToDTO(ubicacion);
            personaDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
            clasificacion = ClasificacionEnum.CONYUGE;
            logger.info("antesdeCrearPerronsaHijoPadre//// " + personaDTO.toString());
            logger.info("antesdeCrearPerronsaHijoPadre:{}" + personaDTO);
        }
        logger.info("es conyugue///" + inDTO.getBeneficiarioConyuge());

        if ((inDTO.getBeneficiarioConyuge() != null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
            logger.info("entra if PersonaConyugue:{}");
            personaDTO.setTipoIdentificacion(inDTO.getBeneficiarioConyuge().getPersona().getTipoIdentificacion());
            personaDTO.setNumeroIdentificacion(inDTO.getBeneficiarioConyuge().getPersona().getNumeroIdentificacion());
            personaDTO.setGenero(inDTO.getBeneficiarioConyuge().getPersona().getGenero());
            personaDTO.setPrimerApellido(inDTO.getBeneficiarioConyuge().getPersona().getPrimerApellido());
            personaDTO.setSegundoApellido(inDTO.getBeneficiarioConyuge().getPersona().getSegundoApellido());
            personaDTO.setPrimerNombre(inDTO.getBeneficiarioConyuge().getPersona().getPrimerNombre());
            personaDTO.setSegundoNombre(inDTO.getBeneficiarioConyuge().getPersona().getSegundoNombre());
            personaDTO.setNivelEducativo(inDTO.getBeneficiarioConyuge().getPersona().getNivelEducativo());
            personaDTO.setFechaNacimiento(inDTO.getBeneficiarioConyuge().getPersona().getFechaNacimiento());
            ubicacion = inDTO.getBeneficiarioConyuge().getUbicacion().convertToEntity();
            ubicacionModeloDTO.convertToDTO(ubicacion);
            personaDTO.setUbicacionModeloDTO(ubicacionModeloDTO);
            clasificacion = ClasificacionEnum.CONYUGE;
            logger.info("antesdeCrearPersonaConyugue//// " + personaDTO.toString());
            logger.info("antesdeCrearPersonaConyugue:{}" + personaDTO);
        }
        logger.info("personacero " + persona);
        persona = crearPersona(personaDTO);
        logger.info("creoPersona//// " + persona);
        AfiliadoInDTO afiliadoDTO = null;
        AfiliadoInDTO afiliado = new AfiliadoInDTO();
        afiliado.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        PersonaDTO personaDTO1 = new PersonaDTO();
        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() != null)) {
            personaDTO1.setNumeroIdentificacion(inDTO.getBeneficiarioHijoPadre().getPersona().getNumeroIdentificacion());
            personaDTO1.setPrimerNombre(inDTO.getBeneficiarioHijoPadre().getPersona().getPrimerNombre());
            personaDTO1.setSegundoNombre(inDTO.getBeneficiarioHijoPadre().getPersona().getSegundoNombre());
            personaDTO1.setPrimerApellido(inDTO.getBeneficiarioHijoPadre().getPersona().getPrimerApellido());
            personaDTO1.setFechaNacimiento(inDTO.getBeneficiarioHijoPadre().getPersona().getFechaNacimiento());
            personaDTO1.setTipoIdentificacion(inDTO.getBeneficiarioHijoPadre().getPersona().getTipoIdentificacion());
        }

        if ((inDTO.getBeneficiarioConyuge() != null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
            personaDTO1.setNumeroIdentificacion(inDTO.getBeneficiarioConyuge().getPersona().getNumeroIdentificacion());
            personaDTO1.setPrimerNombre(inDTO.getBeneficiarioConyuge().getPersona().getPrimerNombre());
            personaDTO1.setSegundoNombre(inDTO.getBeneficiarioConyuge().getPersona().getSegundoNombre());
            personaDTO1.setPrimerApellido(inDTO.getBeneficiarioConyuge().getPersona().getPrimerApellido());
            personaDTO1.setFechaNacimiento(inDTO.getBeneficiarioConyuge().getPersona().getFechaNacimiento());
            personaDTO1.setTipoIdentificacion(inDTO.getBeneficiarioConyuge().getPersona().getTipoIdentificacion());
        }

        afiliado.setPersona(personaDTO1);
        afiliado.setTipoAfiliado(inDTO.getTipoAfiliado());
        logger.info("antes de crear idafiliado: ");
        //afiliadoDTO = crearAfiliado(afiliado);
        // logger.info("crear idafiliado: " + afiliadoDTO.getIdRolAfiliado() );
        logger.info("Los datos del beneficiario a procesar son: " + inDTO.toString());
        logger.info("TipoIdentificacionEnum:**//// " + tipoIdentificacion);
        List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliadov = consultarRolesAfiliado(tipoIdentificacion, inDTO.getNumeroIdentificacionAfiliado());

        logger.info("RolAfiliadoEmpleadorDTO:**//// " + consultarRolesAfiliadov.toString());
        Long idRoles = null;
        Long idPersona = null;
        for (RolAfiliadoEmpleadorDTO idRol : consultarRolesAfiliadov) {
            idRoles = idRol.getRolAfiliado().getAfiliado().getIdAfiliado();
            idPersona = idRol.getRolAfiliado().getAfiliado().getIdPersona();
        }

        logger.info("idRolAfiliado:???//// " + idRoles);

        AfiliadoInDTO afiliadoInDTO = new AfiliadoInDTO();
        logger.info("seteaAfiliado---- ");
        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() != null)) {
            afiliadoInDTO.setPersona(inDTO.getBeneficiarioHijoPadre().getPersona());
        }

        if ((inDTO.getBeneficiarioConyuge() != null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
            afiliadoInDTO.setPersona(inDTO.getBeneficiarioConyuge().getPersona());
        }

        logger.info("afiliadoInDTO.setPersona " + afiliadoInDTO.getPersona());
        afiliadoInDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
        afiliadoInDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        afiliadoInDTO.setIdRolAfiliado(idRoles);
        logger.info("antes de ObtenerelidSolicitud---- ");
        Long idSolicitudBeneficiario = crearSolicitudAfiliacionPersona(afiliadoInDTO);

        logger.info("idSolicitudBeneficiario------- " + idSolicitudBeneficiario);

        boolean success = false;
        Long idRolAfiliado = null;
        Long idAfiliado = null;
        Long idBeneficiario = null;
        Long idSolcitudGlobal = null;
        Long idGrupoFamiliar = null;
        Long comunicadoCreado = null;
        String numIdentAfiliado = null;
        String numeroRadicado = null;

        PersonaDTO personaAfiliado = null;
        PersonaDTO personaBeneficiario = null;
        SolicitudDTO solicitudGlobalDTO = null;
        SolicitudAfiliacionPersonaDTO solicitudAfiliacion = null;
        IdentificacionUbicacionPersonaDTO conyugeBeneficiario = null;
        BeneficiarioHijoPadreDTO hijoPadreBeneficiario = null;
        DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO = null;
        GrupoFamiliarDTO miembrogrupoFamiliarAfiliado = null;

        List<PersonaDTO> lstPersonaDTO = null;
        List<GrupoFamiliarDTO> lstGrupoFamiliarDTO = null;

        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
            logger.debug(
                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para tramitar la solicitud de registro de beneficiario");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        } else {
            // si es true el beneficiario es nuevo
            boolean auxBeneficiarioNuevo = false;
            if (inDTO.getBeneficiarioConyuge() == null) {
                auxBeneficiarioNuevo = true;
                hijoPadreBeneficiario = inDTO.getBeneficiarioHijoPadre();
                hijoPadreBeneficiario.setFechaAfiliacion(new Date());
                hijoPadreBeneficiario.setListaChequeo(
                        ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), hijoPadreBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
                personaBeneficiario = hijoPadreBeneficiario.getPersona();
            } else if (inDTO.getBeneficiarioHijoPadre() == null) {
                auxBeneficiarioNuevo = true;
                inDTO.setResultadoGeneralValidacion(ResultadoGeneralValidacionEnum.AFILIABLE);
                conyugeBeneficiario = inDTO.getBeneficiarioConyuge();

                conyugeBeneficiario.setFechaAfiliacion(new Date());
                conyugeBeneficiario
                        .setListaChequeo(ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), conyugeBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
                personaBeneficiario = conyugeBeneficiario.getPersona();
            }
            logger.info("beneficiarioNuevo" + auxBeneficiarioNuevo);

            //consultar si ya existe en base de datos 
            ConsultarBeneficiarioTipoNroIdentificacion consultarBeneficiarioTipoNroIdentificacion = new ConsultarBeneficiarioTipoNroIdentificacion(personaDTO.getNumeroIdentificacion(),personaDTO.getTipoIdentificacion());
            consultarBeneficiarioTipoNroIdentificacion.execute();
            List<BeneficiarioModeloDTO> resultado = consultarBeneficiarioTipoNroIdentificacion.getResult();
 //melissa
            if(resultado != null && !resultado.isEmpty()){

                DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
                datosPersona.setIdPersona(resultado.get(0).getIdPersona());
                datosPersona.setIdBeneficiario(resultado.get(0).getIdBeneficiario());
                datosPersona.setTipoIdentificacionBeneficiario(personaDTO.getTipoIdentificacion());
                datosPersona.setNumeroIdentificacionBeneficiario(personaDTO.getNumeroIdentificacion());
                datosPersona.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
                datosPersona.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
                datosPersona.setPrimerNombreBeneficiario(personaDTO.getPrimerNombre());
                datosPersona.setSegundoNombreBeneficiario(personaDTO.getSegundoNombre());
                datosPersona.setPrimerApellidoBeneficiario(personaDTO.getPrimerApellido());
                datosPersona.setSegundoApellidoBeneficiario(personaDTO.getSegundoApellido());
                datosPersona.setEstadoCivilBeneficiario(resultado.get(0).getEstadoCivil());
                datosPersona.setGeneroBeneficiario(personaDTO.getGenero());
                datosPersona.setTipoIdentificacionTrabajador(tipoIdentificacion);
                datosPersona.setNumeroIdentificacionTrabajador(inDTO.getNumeroIdentificacionAfiliado());
                datosPersona.setTipoIdentificacion(tipoIdentificacion);
                datosPersona.setNumeroIdentificacion(inDTO.getNumeroIdentificacionAfiliado());

                SolicitudNovedadDTO solNovedadDTO  = new SolicitudNovedadDTO();
                solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
                solNovedadDTO.setClasificacion(ClasificacionEnum.valueOf(afiliadosACargoMasivosDTO.getParentezco().toString()));
                logger.info("MELISSA"+ afiliadosACargoMasivosDTO.getParentezco());
                if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("CONYUGE")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL);
                  }
                if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_BIOLOGICO")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL);
                  }
                  if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("PADRE")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL);
                  }
           
                  if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("MADRE")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL);
                  }
           
                  if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_ADOPTIVO")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL);
                  }
           
                  if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJASTRO")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL);
                  }
           
                  if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HERMANO_HUERFANO_DE_PADRES")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL);
                  }
           
                   if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("BENEFICIARIO_EN_CUSTODIA")){
                    solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
                  }
                  solNovedadDTO.setDatosPersona(datosPersona);
                RadicarSolicitudNovedad radicarSolicitudNovedad = new RadicarSolicitudNovedad(solNovedadDTO);
                radicarSolicitudNovedad.execute();
                return 1L;
            }
 
            // idAfiliado = personaBeneficiario.getIdAfiliado();
            // idSolcitudGlobal = inDTO.getIdSolicitudGlobal();
            idAfiliado = idRoles;
            idSolcitudGlobal = idSolicitudBeneficiario;
            numIdentAfiliado = inDTO.getNumeroIdentificacionAfiliado();
            logger.info("idAfiliado+++++" + idAfiliado);
            logger.info("idSolcitudGlobal____" + idSolcitudGlobal);
            solicitudAfiliacion = consultarSolicitudAfiliacionPersona(idSolcitudGlobal);
            logger.info("solicitudAfiliacion---" + solicitudAfiliacion.toString());
            if (solicitudAfiliacion == null) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se encuentra la solicitud de afiliacion relacionada ");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            //Se obtiene el id del Rol Afiliado
            logger.info("idRolAfiliado---" + solicitudAfiliacion.getAfiliadoInDTO().getIdRolAfiliado());
            logger.info("numeroRadicado---" + solicitudAfiliacion.getNumeroRadicacion());
            idRolAfiliado = solicitudAfiliacion.getAfiliadoInDTO().getIdRolAfiliado();
            numeroRadicado = solicitudAfiliacion.getNumeroRadicacion();
            logger.info("tipoBeneficiario" + inDTO.getTipoBeneficiario());
            if (TipoBeneficiarioEnum.CONYUGE.equals(inDTO.getTipoBeneficiario())) {
                logger.info("Entra CONYUGE%%%");
                // Especificacion 3.1.3 Completar información del beneficiario
                // tipo cónyuge
                logger.info("numeroIdentifiAfiliado;;;;" + numIdentAfiliado);
                lstPersonaDTO = buscarAfiliado(numIdentAfiliado);
                logger.info("lstPersonaDTO;;;:{}" + lstPersonaDTO);
                if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
                    logger.info("Entra CONYUGE1%%%");
                    personaAfiliado = lstPersonaDTO.iterator().next();
                }
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion ");
                // Se realiza la evaluacion del resultado general de validacion
                logger.info("Entra CONYUGE125%%%" + inDTO.getResultadoGeneralValidacion());
                if (inDTO.getResultadoGeneralValidacion() != null) {
                    logger.info("Entra CONYUGE2%%%");
                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info("Entra CONYUGE21%%%:{}" + conyugeBeneficiario.getPersona().toString());
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
                        logger.info("Entra CONYUGE22%%%");
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
                        logger.info("Entra CONYUGE23%%%");
                        conyugeBeneficiario.setFechaAfiliacion(new Date());
                    } else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
                        // Si el Resultado validación del beneficiario es No
                        // afiliable, se debe inactivar
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
                        if (auxBeneficiarioNuevo) {
                            conyugeBeneficiario.setFechaAfiliacion(null);
                        }
                    } else {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                    /*
                     * Calcular categoría del beneficiario y habilitar la
                     * utilización de servicios de la caja, como se especifica
                     * en la “HU-TRN-341Definir estructura de personas –
                     * aportantes”.
                     */
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Almacena los datos del beneficiario asociado al afiliado principal de la solicitud");
                    // Se Almacena los datos del beneficiario asociado al
                    // afiliado principal de la solicitud, con la fecha y hora
                    // del sistema como “Fecha y Hora de afiliación”
                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    datosBasicosIdentificacionDTO.setPersona(conyugeBeneficiario.getPersona());
                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
                    conyugeBeneficiario.setFechaAfiliacion(new Date());

                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Actualiza con el valor “Presencial” en el dato de “Canal de recepción” de la solicitud");
                    // Se Registra el valor “Presencial” en el dato de “Canal de
                    // recepción” de la solicitud.
                    solicitudGlobalDTO = new SolicitudDTO();
                    solicitudGlobalDTO.setIdSolicitud(idSolcitudGlobal);
                    solicitudGlobalDTO.setNumeroRadicacion(numeroRadicado);
                    logger.info("Entra CONYUGE4%%%");
                    //solicitudGlobalDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    actualizarSolicitudAfiliacionPersona(idSolcitudGlobal, solicitudGlobalDTO);
                    logger.info("Entra CONYUGE5%%%");
                    conyugeBeneficiario.setIdRolAfiliado(idRolAfiliado);
                    logger.info("Entra CONYUGE6%%%");
                    idBeneficiario = registrarInformacionBeneficiarioConyuge(idAfiliado, conyugeBeneficiario);
                    logger.info("Entra CONYUGE7%%%");
                    if (idBeneficiario == null) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario conyuge");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                    }
                    logger.info("Entra CONYUGE8%%%" + conyugeBeneficiario.getIdGrupoFamiliar());
                    if (conyugeBeneficiario.getIdGrupoFamiliar() != null) {
                        logger.info("idAfiliado%%%" + idAfiliado);
                        logger.info("idgrupofamiliar%%%" + inDTO.getBeneficiarioConyuge().getIdGrupoFamiliar());
                        logger.info("datosBasicosIdentificacionDTO%%%" + datosBasicosIdentificacionDTO.toString());
                        logger.info("datosBasicosIdentificacionDTO%%%:{}" + datosBasicosIdentificacionDTO);

                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioConyuge().getIdGrupoFamiliar(),
                                datosBeneficiarios(conyugeBeneficiario.getPersona().getTipoIdentificacion(), conyugeBeneficiario.getPersona().getNumeroIdentificacion()));
                    } else {

                        logger.info("Entra CONYUGE9%%%" + idAfiliado);
                         MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
                        medioDePagoModeloDTO.setEfectivo(Boolean.TRUE);
                        medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
                        medioDePagoModeloDTO.setCobroJudicial(Boolean.FALSE);
                        ConsultarIdSitioPagoPredeterminado consultarIdSitioPagoPredeterminado = new ConsultarIdSitioPagoPredeterminado();
                        consultarIdSitioPagoPredeterminado.execute();
                        Long idSitioDePagoPredeterminado = consultarIdSitioPagoPredeterminado.getResult();
                        medioDePagoModeloDTO.setSitioPago(idSitioDePagoPredeterminado);
                        logger.info("id Persona "+ idPersona);
                        ConsultarUbicacionPersona consultarUbicacionPersona = new ConsultarUbicacionPersona(idPersona);
                        consultarUbicacionPersona.execute();
                        GrupoFamiliarDTO grupoFamiliar = new GrupoFamiliarDTO();
                        grupoFamiliar.setNumero(Byte.MIN_VALUE);
                        grupoFamiliar.setAfiliadoInDTO(afiliadoInDTO);
                        grupoFamiliar.setUbicacion(consultarUbicacionPersona.getResult());
                        //logger.info("datosBasicosIdentificacionDTO--CONYUGE:{}" + datosBasicosIdentificacionDTO.getPersona().getPrimerNombre());
                       // grupoFamiliar.setAdministradorSubsidio(datosBasicosIdentificacionDTO);
                          List<PersonaDTO> personaAfiliada = consultarPersonaRazonSocial( tipoIdentificacion, inDTO.getNumeroIdentificacionAfiliado(), null);
                           logger.info(" personaAfiliadaCONYUGE" +  personaAfiliada.get(0).getPrimerNombre());
                         for(PersonaDTO personaAfi: personaAfiliada){
                             personaAfi.getPrimerNombre();
                             logger.info(" personaAfi.getPrimerNombre()CONYUGE" +  personaAfi.getPrimerNombre());
                             datosBasicosIdentificacionDTO.setPersona(personaAfi);
                             logger.info(" personaAfi.getPrimerNombre///CONYUGE()" +  datosBasicosIdentificacionDTO.getPersona().getPrimerNombre());

                         }



                        logger.info("datosBasicosIdentificacionDTONombre--:CONYUGE{}" + datosBasicosIdentificacionDTO.getPersona().getPrimerNombre());
                        grupoFamiliar.setAdministradorSubsidio(datosBasicosIdentificacionDTO);
                        grupoFamiliar.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
                       // GrupoFamiliarDTO grupoFamiliar = new GrupoFamiliarDTO(); Edwin toro
                       // grupoFamiliar.setNumero(Byte.MIN_VALUE);  Edwin toro
                       // grupoFamiliar.setAfiliadoInDTO(afiliadoInDTO);
                        logger.info("Entra CONYUGE10%%%" + idAfiliado);
                        grupoFamiliar = crearGrupoFamiliar(idAfiliado, grupoFamiliar);
                        logger.info("CONYUGE11%%%" + grupoFamiliar.getIdGrupoFamiliar());
                        lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
                        logger.info("CONYUGE12%%%" + lstGrupoFamiliarDTO);
                        // ActualizarBeneficiariomasivamente(grupoFamiliar.getIdGrupoFamiliar(), idAfiliado);
                        logger.info("CONYUGE13>>>>" + grupoFamiliar.getIdGrupoFamiliar());
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, grupoFamiliar.getIdGrupoFamiliar(),datosBeneficiarios(conyugeBeneficiario.getPersona().getTipoIdentificacion(), conyugeBeneficiario.getPersona().getNumeroIdentificacion()));
                        logger.info("success>>>>" + success);

                        //Consultar temporal para guardarlo
                        ConsultarDatosTemporalesEmpleador consultTemporal1 = new ConsultarDatosTemporalesEmpleador(idSolicitudTrabajador);
                        consultTemporal1.execute();
                        String dataTemporal1 = consultTemporal1.getResult();

                        if (dataTemporal1 != null && !dataTemporal1.isEmpty()) {
                            GuardarTemporalAfiliacionPersona guardarTemporal1 = new GuardarTemporalAfiliacionPersona();

                            ObjectMapper mapper1 = new ObjectMapper();
                            try {
                                guardarTemporal1 = mapper1.readValue(dataTemporal1, GuardarTemporalAfiliacionPersona.class);
                                List<BeneficiarioDTO> beneficiarios1 = consultarBeneficiariosDeAfiliadoMismaFecha(idAfiliado);
                                for(BeneficiarioDTO ben:beneficiarios1 ) {
                                    ben.setFechaRecepcionDocumento(new Date().getTime());
                                }
                                List<GrupoFamiliarDTO> grupoFamiliar1 = consultarGrupoFamiliar(idAfiliado);
                                guardarTemporal1.setGruposFamiliares(grupoFamiliar1);
                                guardarTemporal1.setBeneficiarios(beneficiarios1);
                                try{
                                    String jsonPayload1 = mapper1.writeValueAsString(guardarTemporal1);
                                    GuardarDatosTemporales guardar1 = new GuardarDatosTemporales(idSolicitudTrabajador, jsonPayload1);
                                    guardar1.execute();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                                
                            } catch (IOException e) {
                                logger.error(
                                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Ocurrio un error consultar los datos temporales",
                                        e);
                                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                            }
                        }

                        // genera comunicado para el trabajador del beneficiario ///
                        logger.info("variablesComunicadoCon1--");
                        Map<String, Object> variablesComunicado = resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, idSolcitudGlobal, null);
                        logger.info("variablesComunicadoCon2:--" + variablesComunicado);

                        ParametrosComunicadoDTO parametroComunicadoDTO = new ParametrosComunicadoDTO();
                        parametroComunicadoDTO.setIdPlantillaComunicado(2L);
                        ArrayList<Long> listaIdSolicitud = new ArrayList<Long>(Arrays.asList(idSolcitudGlobal));
                        logger.info("listaIdSolicitudCon///" + listaIdSolicitud);
                        parametroComunicadoDTO.setIdsSolicitud(listaIdSolicitud);
                        parametroComunicadoDTO.setTipoSolicitante(TipoTipoSolicitanteEnum.PERSONA);
                        logger.info("variablesComunicadoCon----");
                        parametroComunicadoDTO.setParams(variablesComunicado);
                        logger.info("guardarObtenerComunicadoECMCon----");
                        InformacionArchivoDTO informacionArchivoDTO = guardarObtenerComunicadoECM(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION, EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER,
                                parametroComunicadoDTO);

                        // plantillas inicia
                        Map<String, Object> params = parametroComunicadoDTO.getParams();
                        String identificador = "";

                        logger.info("antes de if tipoIdentificacionCon--");

                        if (params.containsKey("tipoIdentificacion")) {
                            logger.info("entroCon1");
                            parametroComunicadoDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(params.get("tipoIdentificacion").toString()));
                        }

                        if (params.containsKey("numeroIdentificacion")) {
                            parametroComunicadoDTO.setNumeroIdentificacion(params.get("numeroIdentificacion").toString());
                            identificador = params.get("numeroIdentificacion").toString();
                        }
                        logger.info("antes de if--- CON");
                        if (params.containsKey("idCartera") && params.get("idCartera") != null) {
                            parametroComunicadoDTO.setIdCartera(new Long(params.get("idCartera").toString()));
                            identificador = params.get("idCartera").toString();
                        }

                        logger.info("antes plaComCon//");
                        PlantillaComunicado plaCom = resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, parametroComunicadoDTO);
                        logger.info("antes plaComCON--" + plaCom.getCuerpo());
                        String comunicadoCompleto = plaCom.getEncabezado() + plaCom.getCuerpo() + plaCom.getPie();
                        logger.info("comunicadoCompletoCon--" + comunicadoCompleto);
                        // plantillas termina

                        Comunicado comunicado = new Comunicado();
                        PlantillaComunicado plantillaComunicado = new PlantillaComunicado();
                        plantillaComunicado.setIdPlantillaComunicado(2L);
                        plantillaComunicado.setEtiqueta(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER);

                        comunicado.setIdSolicitud(idSolcitudGlobal);
                        comunicado.setEmail("comunicadospruebasmasivas@gmail.com");
                        comunicado.setPlantillaComunicado(plantillaComunicado);
                        Date fechaComunicado = new Date();
                        comunicado.setFechaComunicado(fechaComunicado);
                        comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
                        comunicado.setMedioComunicado(MedioComunicadoEnum.ENVIADO);
                        comunicado.setEmpleador(afiliado.getIdEmpleador());
                        comunicado.setTextoAdicionar("adicionar");
                        comunicado.setIdentificadorArchivoComunicado(informacionArchivoDTO.getIdentificadorDocumento());
                        comunicado.setMensajeEnvio(comunicadoCompleto);
                        comunicadoCreado = crearComunicado(comunicado);
                        logger.info("comunicadoCreadoConyugue" + comunicadoCreado);
                    }
                } else {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

            } else if ((TipoBeneficiarioEnum.PADRES.equals(inDTO.getTipoBeneficiario())
                    || TipoBeneficiarioEnum.HIJO.equals(inDTO.getTipoBeneficiario()))) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realizan las validaciones del beneficiario hijo o padre “HU-121-339 Validar condiciones de persona en BD Core ");

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion del hijo o padre ");
                // Se realiza la evaluacion del resultado general de validacion
                // del hijo o padre
                if (inDTO.getResultadoGeneralValidacion() != null) {
                    logger.info("el resultado general de las validaciones, contenido en inDTO.getResultadoGeneralValidacion() fue: " + inDTO.getResultadoGeneralValidacion());

                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es afiliable, ponerlo en estado activo ");
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
                        hijoPadreBeneficiario.setFechaAfiliacion(new Date());
                    } else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
                        // Si el Resultado validación del beneficiario es No
                        // afiliable, se debe inactivar
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
                        if (auxBeneficiarioNuevo) {
                            hijoPadreBeneficiario.setFechaAfiliacion(null);
                        }
                    } else {
                        logger.debug(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario hijo o padre");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Persisten los datos del beneficiario asociado al afiliado principal de la solicitud");
                    // Se Almacena los datos del beneficiario asociado al
                    // afiliado principal de la solicitud, con la fecha y hora
                    // del sistema como “Fecha y Hora de afiliación”
                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    datosBasicosIdentificacionDTO.setPersona(hijoPadreBeneficiario.getPersona());
                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
                    hijoPadreBeneficiario.setFechaAfiliacion(new Date());

                    if (hijoPadreBeneficiario.getIdGrupoFamiliar() != null) {
                        hijoPadreBeneficiario.getPersona().setGradoAcademico(inDTO.getBeneficiarioHijoPadre().getIdGradoAcademico());
                    }
                    hijoPadreBeneficiario.setIdRolAfiliado(idRolAfiliado);
                    logger.info("idAfiliado" + idAfiliado);
                    logger.info("hijoPadreBeneficiario to String:{}" + hijoPadreBeneficiario.toString());
                    logger.info("hijoPadreBeneficiario--:{}" + hijoPadreBeneficiario);
                    idBeneficiario = registrarInformacionBeneficiarioHijoOPadre(idAfiliado, hijoPadreBeneficiario);
                    logger.info("idBeneficiario<<<" + idBeneficiario);
                    if (idBeneficiario == null) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario hijo o padre");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                    }
                    logger.info("inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar()///" + inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar());
                    if (inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar() != null) {
                        logger.info("entra idgrupoFamiliar" + inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar());
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar(),
                                datosBeneficiarios(hijoPadreBeneficiario.getPersona().getTipoIdentificacion(), hijoPadreBeneficiario.getPersona().getNumeroIdentificacion()));
                    } else {
                        logger.info("antes consultarGrupoFamiliar" + idAfiliado);
                        MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
                        medioDePagoModeloDTO.setEfectivo(Boolean.TRUE);
                        medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
                        medioDePagoModeloDTO.setCobroJudicial(Boolean.FALSE);
                        ConsultarIdSitioPagoPredeterminado consultarIdSitioPagoPredeterminado = new ConsultarIdSitioPagoPredeterminado();
                        consultarIdSitioPagoPredeterminado.execute();
                        Long idSitioDePagoPredeterminado = consultarIdSitioPagoPredeterminado.getResult();
                        medioDePagoModeloDTO.setSitioPago(idSitioDePagoPredeterminado);
                        logger.info("id Persona "+ idPersona);
                        ConsultarUbicacionPersona consultarUbicacionPersona = new ConsultarUbicacionPersona(idPersona);
                        consultarUbicacionPersona.execute();
                        GrupoFamiliarDTO grupoFamiliar = new GrupoFamiliarDTO();
                        grupoFamiliar.setNumero(Byte.MIN_VALUE);
                        grupoFamiliar.setAfiliadoInDTO(afiliadoInDTO);
                        grupoFamiliar.setUbicacion(consultarUbicacionPersona.getResult());

                         List<PersonaDTO> personaAfiliada = consultarPersonaRazonSocial( tipoIdentificacion, inDTO.getNumeroIdentificacionAfiliado(), null);
                           logger.info(" personaAfiliada)" +  personaAfiliada.get(0).getPrimerNombre());
                         for(PersonaDTO personaAfi: personaAfiliada){
                             personaAfi.getPrimerNombre();
                             logger.info(" personaAfi.getPrimerNombre()" +  personaAfi.getPrimerNombre());
                             datosBasicosIdentificacionDTO.setPersona(personaAfi);
                             logger.info(" personaAfi.getPrimerNombre///()" +  datosBasicosIdentificacionDTO.getPersona().getPrimerNombre());

                         }

                        grupoFamiliar.setAdministradorSubsidio(datosBasicosIdentificacionDTO);
                        grupoFamiliar.setMedioDePagoModeloDTO(medioDePagoModeloDTO);

                        grupoFamiliar = crearGrupoFamiliar(idAfiliado, grupoFamiliar);

                        lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
                        // ActualizarBeneficiariomasivamente(grupoFamiliar.getIdGrupoFamiliar(), idAfiliado);
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, grupoFamiliar.getIdGrupoFamiliar(),datosBeneficiarios(hijoPadreBeneficiario.getPersona().getTipoIdentificacion(), hijoPadreBeneficiario.getPersona().getNumeroIdentificacion()));
                        ConsultarDatosTemporalesEmpleador consultTemporal2 = new ConsultarDatosTemporalesEmpleador(idSolicitudTrabajador);
                        consultTemporal2.execute();
                        String dataTemporal2 = consultTemporal2.getResult();

                        if (dataTemporal2 != null && !dataTemporal2.isEmpty()) {
                            GuardarTemporalAfiliacionPersona guardarTemporal2 = new GuardarTemporalAfiliacionPersona();

                            ObjectMapper mapper2 = new ObjectMapper();
                            try {
                                guardarTemporal2 = mapper2.readValue(dataTemporal2, GuardarTemporalAfiliacionPersona.class);
                                List<BeneficiarioDTO> beneficiarios2 = consultarBeneficiariosDeAfiliadoMismaFecha(idAfiliado);
                                for(BeneficiarioDTO ben1:beneficiarios2 ) {
                                    ben1.setFechaRecepcionDocumento(new Date().getTime());
                                }
                                List<GrupoFamiliarDTO> grupoFamiliar2 = consultarGrupoFamiliar(idAfiliado);
                                guardarTemporal2.setGruposFamiliares(grupoFamiliar2);
                                guardarTemporal2.setBeneficiarios(beneficiarios2);
                                try{
                                    String jsonPayload2 = mapper2.writeValueAsString(guardarTemporal2);
                                    GuardarDatosTemporales guardar2 = new GuardarDatosTemporales(idSolicitudTrabajador, jsonPayload2);
                                    guardar2.execute();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                                
                            } catch (IOException e) {
                                logger.error(
                                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Ocurrio un error consultar los datos temporales",
                                        e);
                                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                            }
                        }

                        // genera comunicado para el trabajador del beneficiario ///
                        logger.info("variablesComunicadoBen1--");
                        Map<String, Object> variablesComunicado = resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, idSolcitudGlobal, null);
                        logger.info("variablesComunicadoBen2:--" + variablesComunicado);

                        ParametrosComunicadoDTO parametroComunicadoDTO = new ParametrosComunicadoDTO();
                        parametroComunicadoDTO.setIdPlantillaComunicado(2L);
                        ArrayList<Long> listaIdSolicitud = new ArrayList<Long>(Arrays.asList(idSolcitudGlobal));
                        logger.info("listaIdSolicitudben///" + listaIdSolicitud);
                        parametroComunicadoDTO.setIdsSolicitud(listaIdSolicitud);
                        parametroComunicadoDTO.setTipoSolicitante(TipoTipoSolicitanteEnum.PERSONA);
                        logger.info("variablesCouminicado----");
                        parametroComunicadoDTO.setParams(variablesComunicado);
                        logger.info("guardarObtenerComunicadoECM1----");
                        InformacionArchivoDTO informacionArchivoDTO = guardarObtenerComunicadoECM(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION, EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER,
                                parametroComunicadoDTO);

                        // plantillas inicia
                        Map<String, Object> params = parametroComunicadoDTO.getParams();
                        String identificador = "";

                        logger.info("antes de if tipoIdentificacion--");

                        if (params.containsKey("tipoIdentificacion")) {
                            logger.info("entro1");
                            parametroComunicadoDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(params.get("tipoIdentificacion").toString()));
                        }

                        if (params.containsKey("numeroIdentificacion")) {
                            parametroComunicadoDTO.setNumeroIdentificacion(params.get("numeroIdentificacion").toString());
                            identificador = params.get("numeroIdentificacion").toString();
                        }
                        logger.info("antes de if---");
                        if (params.containsKey("idCartera") && params.get("idCartera") != null) {
                            parametroComunicadoDTO.setIdCartera(new Long(params.get("idCartera").toString()));
                            identificador = params.get("idCartera").toString();
                        }

                        logger.info("antes plaComBen//");
                        PlantillaComunicado plaCom = resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER, parametroComunicadoDTO);
                        logger.info("antes plaComBen--" + plaCom.getCuerpo());
                        String comunicadoCompleto = plaCom.getEncabezado() + plaCom.getCuerpo() + plaCom.getPie();
                        logger.info("comunicadoCompletoBen--" + comunicadoCompleto);
                        // plantillas termina

                        Comunicado comunicado = new Comunicado();
                        PlantillaComunicado plantillaComunicado = new PlantillaComunicado();
                        plantillaComunicado.setIdPlantillaComunicado(2L);
                        plantillaComunicado.setEtiqueta(EtiquetaPlantillaComunicadoEnum.NTF_RAD_AFL_PER);

                        comunicado.setIdSolicitud(idSolcitudGlobal);
                        comunicado.setEmail("comunicadospruebasmasivas@gmail.com");
                        comunicado.setPlantillaComunicado(plantillaComunicado);
                        Date fechaComunicado = new Date();
                        comunicado.setFechaComunicado(fechaComunicado);
                        comunicado.setEstadoEnvio(EstadoEnvioComunicadoEnum.FALLIDO);
                        comunicado.setMedioComunicado(MedioComunicadoEnum.ENVIADO);
                        comunicado.setEmpleador(afiliado.getIdEmpleador());
                        comunicado.setTextoAdicionar("adicionar");
                        comunicado.setIdentificadorArchivoComunicado(informacionArchivoDTO.getIdentificadorDocumento());
                        comunicado.setMensajeEnvio(comunicadoCompleto);
                        comunicadoCreado = crearComunicado(comunicado);
                        logger.info("comunicadoCreado" + comunicadoCreado);

                        ///fin comunicado//////////
                    }
                } else {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No vienen datos completos para verificar las validaciones de las Condiciones del Beneficiario hijo o padre");
                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

            }

        }
        logger.info("Finaliza afiliarBeneficiarioMasivamente" + idBeneficiario);
        return idBeneficiario;
    }

    public Integer datosTemporales() {

        Integer datosTemp = null;

        return datosTemp;

    }


    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#verificarRequisitosDocumentalesPersona(com.asopagos.dto.VerificarRequisitosDocumentalesDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarRequisitosDocumentalesPersona(VerificarRequisitosDocumentalesDTO inDTO, UserDTO userDTO) {
        logger.debug(
                "Inicia AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona( VerificarRequisitosDocumentalesDTO,UserDTO )");
        List<Long> lstResult = null;
        List<Long> lstIdsRequisitos = null;
        List<PersonaDTO> lstPersonaDTO = null;

        Long idTarea = null;
        String idInstanciaProceso = null;
        Boolean esDocumentoFisico = null;

        PersonaDTO personaDTO = null;
        SolicitudDTO solicitudDTO = null;
        IntentoAfiliacionInDTO intentoAfiliacion = null;
        SolicitudAfiliacionPersonaDTO soliAfiPersonaDTO = null;

        if (inDTO != null && (inDTO.getIdSolicitudGlobal() != null) && !inDTO.getListaChequeo().getListaChequeo().isEmpty()
                && (inDTO.getListaChequeo().getNumeroIdentificacion() != null) && (inDTO.getDocumentosFisicos() != null)
                || (inDTO.getTipoClasificacion() != null) || (inDTO.getCumpleRequisitosDocumentales() != null)) {

            logger.debug(
                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Se guarda la información de la lista de chequeo");
            // Se guarda la información de la lista de chequeo
            lstResult = crearListaChequeoAfiliacionPersona(inDTO.getListaChequeo());
            if (lstResult != null && !lstResult.isEmpty()) {

                soliAfiPersonaDTO = consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
                if (soliAfiPersonaDTO != null && soliAfiPersonaDTO.getIdInstanciaProceso() != null) {
                    idInstanciaProceso = soliAfiPersonaDTO.getIdInstanciaProceso();
                    idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
                    if (idTarea != null) {
                        if (inDTO.getCumpleRequisitosDocumentales()) {
                            logger.debug(
                                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Si todos los requisitos son satisfechos, actualizar el estado del afiliado como preafiliado");
                            // Si todos los requisitos son satisfechos,
                            // actualizar el estado del afiliado como
                            // preafiliado
                            lstPersonaDTO = buscarAfiliado(inDTO.getListaChequeo().getNumeroIdentificacion());
                            logger.info("****numeroIdentificacion*****"+inDTO.getListaChequeo().getNumeroIdentificacion());
                            if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
                                logger.info("****Entra bien****");
                                personaDTO = lstPersonaDTO.iterator().next();
                            }
                            else {
                                logger.info("***Entra a error***");
                                logger.debug(
                                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No existe el afiliado");
                                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                            }
                        }
                        else {
                            lstIdsRequisitos = new ArrayList<Long>();
                            for (ItemChequeoDTO requisito : inDTO.getListaChequeo().getListaChequeo()) {
                                lstIdsRequisitos.add(requisito.getIdRequisito());
                            }
                            logger.debug(
                                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: En caso de requisitos no satisfechos, registrar el intento de afiliación.");
                            // en caso de requisitos no satisfechos, registrar
                            // el intento de afiliación.
                            intentoAfiliacion = new IntentoAfiliacionInDTO();
                            intentoAfiliacion
                                    .setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_REQUISITOS_DOCUMENTALES);
                            intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
                            intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                            intentoAfiliacion.setFechaInicioProceso(new Date());
                            intentoAfiliacion.setIdsRequsitos(lstIdsRequisitos);
                            registrarIntentoAfiliacion(intentoAfiliacion);
                        }
                        logger.debug(
                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Se termina la tarea en el BPM");
                        // Finalmente terminar la tarea en el BPM
                        switch (inDTO.getDocumentosFisicos()) {
                            case FISICO:
                                esDocumentoFisico = Boolean.TRUE;
                                actualizarEstadoDocumentacionAfiliacion(soliAfiPersonaDTO.getIdSolicitudGlobal(),
                                        EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                                break;
                            case ELECTRONICO:
                                esDocumentoFisico = Boolean.FALSE;
                                actualizarEstadoDocumentacionAfiliacion(soliAfiPersonaDTO.getIdSolicitudGlobal(),
                                        EstadoDocumentacionEnum.ENVIADA_AL_BACK);
                                break;

                        }

                        logger.debug(
                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener el idTarea");
                        // Se registra la información de la solicitud con los
                        // campos indicados
                        solicitudDTO = new SolicitudDTO();
                        solicitudDTO.setIdSolicitud(soliAfiPersonaDTO.getIdSolicitudGlobal());
                        solicitudDTO.setMetodoEnvio((inDTO.getDocumentosFisicos() != null) ? inDTO.getDocumentosFisicos() : null);
                        solicitudDTO.setClasificacion(inDTO.getTipoClasificacion());
                        actualizarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal(), solicitudDTO);

                        terminarTareaAfiliacionPersonas(idTarea, inDTO.getCumpleRequisitosDocumentales(), esDocumentoFisico);

                    }
                    else {
                        logger.debug(
                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener el idTarea");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                    }
                }
                else {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener la Solicitud de Afiliacion de la Persona");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                }
            }
            else {
                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro realiza la creacion de las listas de chequeo");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            }
        }
        else {
            logger.debug(
                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No llegan los parametros para la verificacion de los requisitos documentales");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        logger.debug(
                "Finaliza AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona( VerificarRequisitosDocumentalesDTO,UserDTO )");
    }

public void radicarSolicitudAbreviadaAfiliacionPersonaAfiliados(RadicarSolicitudAbreviadaDTO inDTO, UserDTO userDTO) {
  ResultadoRegistroContactoEnum resultadoServ = null;
   logger.info("**__**Inicicia radicarSolicitudAbreviadaAfiliacionPersona");
    resultadoServ=radicarSolicitudAbreviadaAfiliacionPersona(inDTO,userDTO);
    if(resultadoServ != null){
         logger.info("**__**Exito resultadoServ viene diferente a null radicarSolicitudAbreviadaAfiliacionPersona");
    }else{
      logger.info("**__**Exito resultadoServ viene diferente a null radicarSolicitudAbreviadaAfiliacionPersona");  
    }
}
    /**
     * (non-Javadoc)
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#radicarSolicitudAbreviadaAfiliacionPersona(com.asopagos.afiliaciones.personas.composite.dto.RadicarSolicitudAbreviadaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoRegistroContactoEnum radicarSolicitudAbreviadaAfiliacionPersona(RadicarSolicitudAbreviadaDTO inDTO, UserDTO userDTO) {
        logger.info(
                "Inicia AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona( RadicarSolicitudAbreviadaDTO, UserDTO ) inDTO.getCanal() "+ inDTO.getCanal());

        boolean incluyenBeneficiarios = false;

        Long idSolicitudGlobal = null;
        Long idRolAfiliado = null;
        Long idTarea = null;
        boolean docFisicos = false;
        boolean registroIntentoAfiliacion = false;
        Long idEmpleadorAfi = null;
        String idInstanciaProceso = null;

        TipoRadicacionEnum tipoRadicacionSolcitud = null;

        SolicitudDTO solicitudDTO = null;
        AfiliadoInDTO afiliadoInDTO = null;
        IntentoAfiliacionInDTO intentoAfiliacion = null;
        SolicitudAfiliacionPersonaDTO solAfiliacionPersonaDTO = null;
        // Representa el salario registrado en pantalla, especial caso Reintegro
        BigDecimal salario = null;
        
        List<BeneficiarioDTO> lstBeneficiariosDTO = inDTO.getBeneficiarios();
        
        if ((inDTO.getIdSolicitudGlobal() == null) || (inDTO.getTipoSolicitante() == null) || (inDTO.getTipoRadicacion() == null)) {
            logger.info(
                    "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No viene parametros validos para radicar la solicitud de afilacion de persona");
            // No viene parametros validos para radicar la solicitud de
            // afilacion de persona
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        else {
            idSolicitudGlobal = inDTO.getIdSolicitudGlobal();
            tipoRadicacionSolcitud = inDTO.getTipoRadicacion();

            if (inDTO.getRegistrarIntentoAfiliacion()) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: se registra el intento de afiliación, si se solicita desde pantalla.");
                // se registra el intento de afiliación, si se solicita desde
                // pantalla
                registroIntentoAfiliacion = true;
                intentoAfiliacion = new IntentoAfiliacionInDTO();
                intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.DECLINACION_VOLUNTARIA_USUARIO);
                intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
                intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                intentoAfiliacion.setFechaInicioProceso(new Date());
                registrarIntentoAfiliacion(intentoAfiliacion);

                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a Registrar intento de afiliación");
                // Se actualiza el estado de la solicitud a Registrar intento de
                // afiliación
                actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION);

                if (lstBeneficiariosDTO != null && !lstBeneficiariosDTO.isEmpty()) {
                    incluyenBeneficiarios = true;
                }
                // Se realiza la terminacion de tarea en el BPM para el proceso
                // de
                // Afiliación personas presencial
                solAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
                idInstanciaProceso = solAfiliacionPersonaDTO.getIdInstanciaProceso();
                idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);

                if (solAfiliacionPersonaDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
                    docFisicos = true;
                }
                if (inDTO.getMetodoEnvio() != null && inDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
                    docFisicos = true;
                }
                if (idTarea != null) {
                    String numeroRadicado = solAfiliacionPersonaDTO.getNumeroRadicacion();
                    terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
                            registroIntentoAfiliacion, docFisicos, numeroRadicado);
                }
                else {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No se logro obtener el idTarea");

                }
            }
            else {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Valida que todos los beneficiarios tengan el estado AFIALIABLE para poder radicar");
                // Validar que todos los beneficiarios tengan el estado
                // AFIALIABLE
                // para poder radicar
                if (lstBeneficiariosDTO != null && !lstBeneficiariosDTO.isEmpty()) {
                    for (BeneficiarioDTO beneficiarioDTO : lstBeneficiariosDTO) {
                        if (beneficiarioDTO.getResultadoValidacion() == null) {
                            logger.info(
                                    "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No es posible radicar debido a que al menos un afiliado es NO_AFILIABLE");
                            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                        }
                    }
                    incluyenBeneficiarios = true;
                }

                solAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
                //Consultar temporal para guardarlo
                ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(idSolicitudGlobal);
                consultTemporal.execute();
                String dataTemporal = consultTemporal.getResult();
                
                if (dataTemporal != null && !dataTemporal.isEmpty()) {
                    GuardarTemporalAfiliacionPersona guardarTemporal = new GuardarTemporalAfiliacionPersona();

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        guardarTemporal = mapper.readValue(dataTemporal, GuardarTemporalAfiliacionPersona.class);
                    } catch (IOException e) {
                        logger.error(
                                "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Ocurrio un error consultar los datos temporales",
                                e);
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                    }

                    if (guardarTemporal.getInfoLaboral() != null) {
                        guardarTemporal.getInfoLaboral().setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                        GuardarInformacionLaboral guardarInfoLaboral = new GuardarInformacionLaboral(guardarTemporal.getInfoLaboral());
                        guardarInfoLaboral.execute();
                        // Se ajusta que siempre sea el mismo salario CASO Reintegro
                        if (guardarTemporal.getInfoLaboral().getValorSalario() != null) {
                            salario = guardarTemporal.getInfoLaboral().getValorSalario();
                        }
                    }

                    if (guardarTemporal.getModeloInformacion() != null) {
                        GuardarDatosIdentificacionYUbicacion guardarInforUbicacion = new GuardarDatosIdentificacionYUbicacion(
                                guardarTemporal.getModeloInformacion());
                        guardarInforUbicacion.execute();
                        if(guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos() != null){
                            salario = guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos();
                        }
                    }

                    if (guardarTemporal.getBeneficiarios() != null && !guardarTemporal.getBeneficiarios().isEmpty()
                            && inDTO.getTipoRadicacion().equals(TipoRadicacionEnum.ABREVIADA)) {
                        lstBeneficiariosDTO = new ArrayList<BeneficiarioDTO>();
                        for (BeneficiarioDTO beneficiarioDTO : guardarTemporal.getBeneficiarios()) {
                            if (beneficiarioDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.AFILIABLE)) {
                                lstBeneficiariosDTO.add(beneficiarioDTO);
                            }
                        }
                        if (!lstBeneficiariosDTO.isEmpty() && lstBeneficiariosDTO.size() > 0) {
                            RegistrarPersonasBeneficiariosAbreviada registrarPersonasSrv = new RegistrarPersonasBeneficiariosAbreviada(
                                    solAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado(), lstBeneficiariosDTO);
                            registrarPersonasSrv.execute();
                        }
                    }
                }
                
                afiliadoInDTO = solAfiliacionPersonaDTO.getAfiliadoInDTO();
                idEmpleadorAfi = afiliadoInDTO.getIdEmpleador();
                Empleador empleador = null;
                if (afiliadoInDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)) {
                    empleador = buscarEmpleador(afiliadoInDTO.getIdEmpleador());
                }

                ResultadoRegistroContactoEnum resultadoServ = null;

                // Se realizan las validaciones pertinentes a solicitudes ya
                // existentes en proceso

                Map<String, String> datosValidacion = new HashMap<String, String>();
                datosValidacion.put("tipoIdentificacion", afiliadoInDTO.getPersona().getTipoIdentificacion().toString());
                datosValidacion.put("numeroIdentificacion", afiliadoInDTO.getPersona().getNumeroIdentificacion());

                if (empleador != null) {
                    
                    datosValidacion.put("tipoIdentificacionEmpleador",
                            empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
                    datosValidacion.put("numeroIdentificacionEmpleador", empleador.getEmpresa().getPersona().getNumeroIdentificacion());

                }
                datosValidacion.put("tipoAfiliado", afiliadoInDTO.getTipoAfiliado().getName());
                // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación
                datosValidacion.put("idSolicitud", idSolicitudGlobal.toString());
                ValidarPersonas validarPersona = new ValidarPersonas("121-107-1", ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        afiliadoInDTO.getTipoAfiliado().toString(), datosValidacion);
                validarPersona.execute();
                List<ValidacionDTO> list = validarPersona.getResult();
                ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, list);

                if (validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                    resultadoServ = ResultadoRegistroContactoEnum.AFILIACION_EN_PROCESO;

                    /*
                     * se procede a cambiar el estado de la solciitud a cerrada
                     * y a terminar la instancia del proceso en el BPM
                     */
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

                    /**
                     * Se registra intento de afiliación para la solicitud que estaba en proceso y
                     * llevar la traza de la inconsistencía
                     */
                    intentoAfiliacion = new IntentoAfiliacionInDTO();
                    intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA);
                    intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
                    intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                    intentoAfiliacion.setFechaInicioProceso(new Date());
                    registrarIntentoAfiliacion(intentoAfiliacion);

                    AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                            new Long(solAfiliacionPersonaDTO.getIdInstanciaProceso()));
                    aborProceso.execute();

                    return resultadoServ;
                }

                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a radicada");
                // Se actualiza el estado de la solicitud a radicada
                actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.RADICADA);

                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se registra la información de la solicitud con los campos indicados");
                // Se registra la información de la solicitud con los campos
                // indicados
                solicitudDTO = new SolicitudDTO();
                solicitudDTO.setFechaRadicacion(new Date());
                solicitudDTO.setCanalRecepcion(inDTO.getCanal());
                solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
                solicitudDTO.setTipoRadicacion(tipoRadicacionSolcitud);
                solicitudDTO.setMetodoEnvio((inDTO.getMetodoEnvio() != null) ? inDTO.getMetodoEnvio() : null);
                actualizarSolicitudAfiliacionPersona(idSolicitudGlobal, solicitudDTO);

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza la fecha de afiliación del afiliadoa la caja de compensación");
                // Se actualiza el rol Afiliado
                // Se actualiza la fecha de afiliación del afiliadoa la caja de compensación
                // Se actualiza el estado de afiliación a ACTIVO
                idRolAfiliado = inDTO.getIdRolAfiliado();
                RolAfiliadoModeloDTO rolAfiliadoModeloDTO =  new RolAfiliadoModeloDTO();
                rolAfiliadoModeloDTO.setIdRolAfiliado(inDTO.getIdRolAfiliado());
                rolAfiliadoModeloDTO.setFechaAfiliacion(new Date().getTime());
                rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
                actualizarRolAfiliado(rolAfiliadoModeloDTO);

                if (solAfiliacionPersonaDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
                    docFisicos = true;
                }
                if (inDTO.getMetodoEnvio() != null) {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la documentación dependiendo de lo indicado al servicio");
                    // actualizo el estado de la documentación dependiendo de lo
                    // indicado al servicio
                    switch (inDTO.getMetodoEnvio()) {
                        case ELECTRONICO:
                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.ENVIADA_AL_BACK);
                            break;
                        case FISICO:
                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                            docFisicos = true;
                            break;
                    }
                }

                /*
                 * TODO esta parte esta por realizarse posteriormente ya que aun
                 * no se encuentra definida: "Almacena y registra la información
                 * de la solicitud. Nota: En caso de que el afiliado principal
                 * y/o beneficiarios, sea reintegrado (estuvo afiliado
                 * previamente en la Caja de Compensación), el sistema debe
                 * guardar la información histórica (no sobreescribirla con los
                 * datos nuevos)."
                 * 
                 */
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el valor del campo salario/mesada del afiliado principal");
                // Actualizo el valor del campo salario/mesada del afiliado
                // principal
                if (salario != null) {
                    afiliadoInDTO.setValorSalarioMesada(salario);
                }
                logger.info(
                        "--AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza al trabajador en estado “Activo”");
                // Se actualiza al trabajador
                actualizarAfiliado(afiliadoInDTO);

                logger.debug(
                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se calcula la categoría del afiliado deacuerdo a las condiciones establecidas en HU-121-341 Definir estructura persona/aportante");
                // Se calcula la categoría del afiliado deacuerdo a las
                // condiciones
                // establecidas en HU-121-341 Definir estructura
                // persona/aportante
               calcularCategoriaAfiliado(afiliadoInDTO);

                logger.info(
                        "xxxAfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se realiza la terminacion de tarea en el BPM para el proceso de Afiliación personas presencial");
                // Se realiza la terminacion de tarea en el BPM para el proceso
                // de
                // Afiliación personas presencial
                idInstanciaProceso = solAfiliacionPersonaDTO.getIdInstanciaProceso();
                idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
                if (idTarea != null) {
                    logger.warn("VAMOS A TERMINAR LA TEREA"+idTarea);
                    String numeroRadicado = solAfiliacionPersonaDTO.getNumeroRadicacion();
                    // terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
                    //         registroIntentoAfiliacion, docFisicos, numeroRadicado);
                            /**ajuste para pila  14/01/2022*/
                    if(inDTO.getCanal() == CanalRecepcionEnum.PILA){
                        logger.info("**__** INICIA VerificarSolicitud PARA PILA  ");
                        logger.info("**__** idEmpleadorAfi VerificarSolicitud PARA PILA  "+idEmpleadorAfi);
                        logger.info("**__** idTarea VerificarSolicitud PARA PILA  "+idTarea);
                        logger.info("**__** numeroRadicado VerificarSolicitud PARA PILA  "+numeroRadicado);
                        logger.info("**__** afiliadoInDTO.getPersona().getNumeroIdentificacion() VerificarSolicitud PARA PILA  "+afiliadoInDTO.getPersona().getNumeroIdentificacion());
                        VerificarSolicitudAfiliacionPersonaDTO verficarSolicitudDTO = new VerificarSolicitudAfiliacionPersonaDTO();
                            verficarSolicitudDTO.setIdEmpleador(idEmpleadorAfi);
                            verficarSolicitudDTO.setIdTarea(idTarea);
                            verficarSolicitudDTO.setNumeroIdentificacionAfiliado(afiliadoInDTO.getPersona().getNumeroIdentificacion());
                            verficarSolicitudDTO.setNumeroRadicado(numeroRadicado);
                            verficarSolicitudDTO.setResultadoGeneralAfiliado(ResultadoGeneralProductoNoConformeEnum.APROBADA);
                        verificarInformacionSolicitud(verficarSolicitudDTO,userDTO);                
                        //  VerificarSolicitud verficarSolicitud = new VerificarSolicitud(verficarSolicitud);
                        //                  verficarSolicitud.execute();
                    }

                    //VerificarSolicitudDTO resul = verficarSolicitud.getResult();
                    /**Fin ajuste para pila */
                }
                else {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No se logro obtener el idTarea");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                }
            }
        }
        logger.debug(
                "Finaliza AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona( RadicarSolicitudAbreviadaDTO, UserDTO )");
        return null;
    }

    private List<ItemChequeoDTO> ingresarSolcitudItemChequeo(Long idSolicitudGlobal, List<ItemChequeoDTO> items, Long fechaRecepcionDocumentos) {
        if (items != null && !items.isEmpty() && idSolicitudGlobal != null) {
            for (ItemChequeoDTO itemChequeoDTO : items) {
                itemChequeoDTO.setIdSolicitudGlobal(idSolicitudGlobal);
                itemChequeoDTO.setFechaRecepcionDocumentos(fechaRecepcionDocumentos);
                if (itemChequeoDTO != null && itemChequeoDTO.getCumpleRequisito() != null
                && itemChequeoDTO.getCumpleRequisito().equals(Boolean.TRUE)
                && itemChequeoDTO.getIdentificadorDocumento() != null) {
                    itemChequeoDTO.setCumpleRequisitoBack(Boolean.TRUE);
                }
            }
        }
        return items;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#afiliarBeneficiario(com.asopagos.afiliaciones.personas.composite.dto.AfiliarBeneficiarioDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long afiliarBeneficiario(AfiliarBeneficiarioDTO inDTO, UserDTO userDTO) {
        logger.info("Inicia AfiliacionPersonasCompositeBusiness.afiliarBeneficiario(AfiliarBeneficiarioDTO, UserDTO)");

        logger.info("Los datos del beneficiario a procesar son: " + inDTO.toString());
        
        
        boolean success = false;
        Long idRolAfiliado = null;
        Long idAfiliado = null;
        Long idBeneficiario = null;
        Long idSolcitudGlobal = null;
        Long idGrupoFamiliar = null;

        String numIdentAfiliado = null;
        String numeroRadicado = null;

        PersonaDTO personaAfiliado = null;
        PersonaDTO personaBeneficiario = null;
        SolicitudDTO solicitudGlobalDTO = null;
        SolicitudAfiliacionPersonaDTO solicitudAfiliacion = null;
        IdentificacionUbicacionPersonaDTO conyugeBeneficiario = null;
        BeneficiarioHijoPadreDTO hijoPadreBeneficiario = null;
        DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO = null;
        GrupoFamiliarDTO miembrogrupoFamiliarAfiliado = null;

        List<PersonaDTO> lstPersonaDTO = null;
        List<GrupoFamiliarDTO> lstGrupoFamiliarDTO = null;

        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
            logger.info(
                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para tramitar la solicitud de registro de beneficiario");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        else {
            // si es true el beneficiario es nuevo
            boolean auxBeneficiarioNuevo = false;
            idSolcitudGlobal = inDTO.getIdSolicitudGlobal();
            numIdentAfiliado = inDTO.getNumeroIdentificacionAfiliado();

            solicitudAfiliacion = consultarSolicitudAfiliacionPersona(idSolcitudGlobal);
    
            if (solicitudAfiliacion.getTipoTransaccion().name().contains("AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION")) {
                if (solicitudAfiliacion.getFechaRadicacion() != null) {
                    Long fechaRadicacionMillis = solicitudAfiliacion.getFechaRadicacion().getTime();
                    inDTO.setFechaRecepcionDocumento(fechaRadicacionMillis);
                } else {
                    logger.warn("solicitudAfiliacion.getFechaRadicacion() es nulo.");
                }
            }
            if (inDTO.getBeneficiarioConyuge() == null) {
                auxBeneficiarioNuevo = true;
                hijoPadreBeneficiario = inDTO.getBeneficiarioHijoPadre();
                hijoPadreBeneficiario.setFechaAfiliacion(new Date());
                hijoPadreBeneficiario.setListaChequeo(
                        ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), hijoPadreBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
                personaBeneficiario = hijoPadreBeneficiario.getPersona();
            }
            else if (inDTO.getBeneficiarioHijoPadre() == null) {
                auxBeneficiarioNuevo = true;
                conyugeBeneficiario = inDTO.getBeneficiarioConyuge();
                conyugeBeneficiario.setFechaAfiliacion(new Date());
                conyugeBeneficiario
                        .setListaChequeo(ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), conyugeBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
                personaBeneficiario = conyugeBeneficiario.getPersona();
            }
            idAfiliado = personaBeneficiario.getIdAfiliado();
            
            if (solicitudAfiliacion == null) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se encuentra la solicitud de afiliacion relacionada ");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
            //Se obtiene el id del Rol Afiliado
            idRolAfiliado = solicitudAfiliacion.getAfiliadoInDTO().getIdRolAfiliado();
            numeroRadicado = solicitudAfiliacion.getNumeroRadicacion();

            if (TipoBeneficiarioEnum.CONYUGE.equals(inDTO.getTipoBeneficiario())) {

                // Especificacion 3.1.3 Completar información del beneficiario
                // tipo cónyuge
                lstPersonaDTO = buscarAfiliado(numIdentAfiliado);
                if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
                    personaAfiliado = lstPersonaDTO.iterator().next();
                    // En caso de que se active un cónyuge, el sistema debe
                    // cambiar automáticamente el valor del campo “Estado Civil”
                    // a “Casado/Unión Libre”.
                    /*CCREPNORMATIVOS*/
                    //conyugeBeneficiario.getPersona().setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
                }
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion ");
                // Se realiza la evaluacion del resultado general de validacion
                if (inDTO.getResultadoGeneralValidacion() != null) {
                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
                        conyugeBeneficiario.setFechaAfiliacion(new Date());
                    }
                    else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
                        // Si el Resultado validación del beneficiario es No
                        // afiliable, se debe inactivar
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
                        if(auxBeneficiarioNuevo){
                            conyugeBeneficiario.setFechaAfiliacion(null);
                        }
                    }
                    else {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                    /*
                     * Calcular categoría del beneficiario y habilitar la
                     * utilización de servicios de la caja, como se especifica
                     * en la “HU-TRN-341Definir estructura de personas –
                     * aportantes”.
                     */

                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Almacena los datos del beneficiario asociado al afiliado principal de la solicitud");
                    // Se Almacena los datos del beneficiario asociado al
                    // afiliado principal de la solicitud, con la fecha y hora
                    // del sistema como “Fecha y Hora de afiliación”
                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    datosBasicosIdentificacionDTO.setPersona(conyugeBeneficiario.getPersona());
                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
                    conyugeBeneficiario.setFechaAfiliacion(new Date());

                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Actualiza con el valor “Presencial” en el dato de “Canal de recepción” de la solicitud");
                    // Se Registra el valor “Presencial” en el dato de “Canal de
                    // recepción” de la solicitud.
                    solicitudGlobalDTO = new SolicitudDTO();
                    solicitudGlobalDTO.setIdSolicitud(idSolcitudGlobal);
                    solicitudGlobalDTO.setNumeroRadicacion(numeroRadicado);
                    //solicitudGlobalDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    actualizarSolicitudAfiliacionPersona(idSolcitudGlobal, solicitudGlobalDTO);
                    conyugeBeneficiario.setIdRolAfiliado(idRolAfiliado);
                    idBeneficiario = registrarInformacionBeneficiarioConyuge(idAfiliado, conyugeBeneficiario);
                    if (idBeneficiario == null) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario conyuge");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                    }

                    if (conyugeBeneficiario.getIdGrupoFamiliar() != null) {
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioConyuge().getIdGrupoFamiliar(),
                        datosBasicosIdentificacionDTO);
                    }

                    lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
                    if (lstGrupoFamiliarDTO != null && !lstGrupoFamiliarDTO.isEmpty()) {
                        miembrogrupoFamiliarAfiliado = lstGrupoFamiliarDTO.iterator().next();
                        idGrupoFamiliar = miembrogrupoFamiliarAfiliado.getIdGrupoFamiliar();
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar,datosBasicosIdentificacionDTO);
                        if (!success) {
                            logger.info(
                                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro asociar el beneficiario conyuge al Grupo Familiar");
                            throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                        }
                    }
                    else {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se encontro el Grupo Familiar del afiliado");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }
                }
                else {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

            }
            else if ((TipoBeneficiarioEnum.PADRES.equals(inDTO.getTipoBeneficiario())
                    || TipoBeneficiarioEnum.HIJO.equals(inDTO.getTipoBeneficiario()))) {
                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realizan las validaciones del beneficiario hijo o padre “HU-121-339 Validar condiciones de persona en BD Core ");

                logger.info(
                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion del hijo o padre ");
                // Se realiza la evaluacion del resultado general de validacion
                // del hijo o padre
                if (inDTO.getResultadoGeneralValidacion() != null) {
                    logger.info("el resultado general de las validaciones, contenido en inDTO.getResultadoGeneralValidacion() fue: ");
                    
                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es afiliable, ponerlo en estado activo ");
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
                        hijoPadreBeneficiario.setFechaAfiliacion(new Date());
                    }
                    else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
                        // Si el Resultado validación del beneficiario es No
                        // afiliable, se debe inactivar
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
                        if(auxBeneficiarioNuevo){
                            hijoPadreBeneficiario.setFechaAfiliacion(null);
                        }
                    }
                    else {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario hijo o padre");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Persisten los datos del beneficiario asociado al afiliado principal de la solicitud");
                    // Se Almacena los datos del beneficiario asociado al
                    // afiliado principal de la solicitud, con la fecha y hora
                    // del sistema como “Fecha y Hora de afiliación”
                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                    datosBasicosIdentificacionDTO.setPersona(hijoPadreBeneficiario.getPersona());
                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());

                    // Se Almacenan los datos del beneficiario asociado al
                    // afiliado principal de la solicitud
                    // TODO en el modelo de datos actual no existe el concepto
                    // categoria en entidades
                    // hijoPadreBeneficiario.setCategoriaBeneficiario(categoriaBeneficiario);
                    hijoPadreBeneficiario.setFechaAfiliacion(new Date());

                    if (hijoPadreBeneficiario.getIdGrupoFamiliar() != null) {
                        hijoPadreBeneficiario.getPersona().setGradoAcademico(inDTO.getBeneficiarioHijoPadre().getIdGradoAcademico());
                    }
                    hijoPadreBeneficiario.setIdRolAfiliado(idRolAfiliado);
                    
                    idBeneficiario = registrarInformacionBeneficiarioHijoOPadre(idAfiliado, hijoPadreBeneficiario);
                    if (idBeneficiario == null) {
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario hijo o padre");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                    }
                    if (inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar() != null) {
                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar(),datosBasicosIdentificacionDTO);
                    }
                    else {
                        lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
                        if (lstGrupoFamiliarDTO != null && !lstGrupoFamiliarDTO.isEmpty()) {
                            miembrogrupoFamiliarAfiliado = lstGrupoFamiliarDTO.iterator().next();
                            idGrupoFamiliar = miembrogrupoFamiliarAfiliado.getIdGrupoFamiliar();
                            success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar,datosBasicosIdentificacionDTO);
                            if (!success) {
                                logger.info(
                                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro asociar el beneficiario hijo o padre al grupo familiar del afiliado");
                                throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                            }
                        }
                        else {
                            logger.info(
                                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro encontrar el grupo familiar del afiliado");
                            throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                        }
                    }
                }
                else {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No vienen datos completos para verificar las validaciones de las Condiciones del Beneficiario hijo o padre");
                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }

            }
//            if(inDTO.getIdBeneficiario()!=null){
//               actualizarEstadoBeneficiario(inDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
//            }
            if (inDTO.getIdsBeneficiariosInactivar() != null) {
                List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<BeneficiarioModeloDTO>();
                for (Long idBeneficiarioInactivar : inDTO.getIdsBeneficiariosInactivar()) {
                    //actualizarEstadoBeneficiario(idBeneficiarioInactivar, EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
                    ConsultarBeneficiario beneficiarioSrv = new ConsultarBeneficiario(idBeneficiarioInactivar);
                    beneficiarioSrv.execute();

                    listaBeneficiarios.add(beneficiarioSrv.getResult());
                }

                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
                datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(numeroRadicado);
                datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                novedadCascada.execute();
            }
                
        }
        logger.info("Finaliza AfiliacionPersonasCompositeBusiness.afiliarBeneficiario(AfiliarBeneficiarioDTO, UserDTO)");
        return idBeneficiario;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#asignarSolicitudAfiliacionPersona(com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void asignarSolicitudAfiliacionPersona(AsignarSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) {
        String destinatario = null;
        String sedeDestinatario = null;
        String observacion = null;
        Long idTare = null;
        UsuarioCCF usuarioCCF = new UsuarioCCF();
        switch (inDTO.getMetodoAsignacion()) {
            case AUTOMATICO: {
                // TODO: verificar que el servicio consumido en
                // asignarAutomaticamenteUsuarioCajaCompensacion
                // solo debe retornar usuarios de la misma sede.
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                        ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL);
                usuarioCCF = consultarUsuarioCajaCompensacion(destinatario);
                sedeDestinatario = usuarioCCF.getCodigoSede();
                observacion = null;
                break;
            }
            case MANUAL: {
                // se busca el usuario a quien se le asigna la tarea, por su nombe
                // de usuario
                usuarioCCF = consultarUsuarioCajaCompensacion(inDTO.getDestinatario());
                sedeDestinatario = usuarioCCF.getCodigoSede();
                destinatario = usuarioCCF.getNombreUsuario();
                observacion = inDTO.getObservacion();
                break;
            }
        }
        SolicitudAfiliacionPersonaDTO sol = consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
        // se actualiza la solicitud
        SolicitudDTO solDTO = new SolicitudDTO();
        solDTO.setDestinatario(destinatario);
        solDTO.setSedeDestinatario(sedeDestinatario);
        solDTO.setObservacion(observacion);
        if (FormatoEntregaDocumentoEnum.FISICO.equals(sol.getMetodoEnvio())) {
            solDTO.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
        }
        actualizarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal(), solDTO);

        // Se actualiza el estado de la solicitud de afiliación de la persona
        // según el estado de los documentos

        EstadoSolicitudAfiliacionPersonaEnum estadoSol = EstadoSolicitudAfiliacionPersonaEnum.ASIGNADA_AL_BACK;
        if (FormatoEntregaDocumentoEnum.FISICO.equals(sol.getMetodoEnvio())) {
            estadoSol = EstadoSolicitudAfiliacionPersonaEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS;
        }

        // se actualiza el estado de la solicitud de afiliación
        actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), estadoSol);

        if (inDTO.getIdTarea() != null) {
            idTare = inDTO.getIdTarea();
        }
        else {
            idTare = consultarTareaAfiliacionPersonas(inDTO.getIdInstanciaProceso());
        }
        // se invoca la terminación de la tarea enviandole el destinatario.
        Map<String, Object> params = new HashMap<>();
        params.put("usuarioBack", destinatario);
        params.put("fechaAsignacionBack", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        try {
            TerminarTarea iniciarTareaService = new TerminarTarea(idTare, params);
            iniciarTareaService.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#verificarInformacionSolicitud(com.asopagos.afiliaciones.personas.composite.dto.ResultadoGeneralProductoNoConformeBeneficiarioDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) {
        logger.debug(
                "Inicia AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
        // HU 121-122 3.1.4. Cerrar solicitud de afiliación de persona
        // (verificación sin producto no conforme no resuelto)
        String numeroRadicado = null;
        String numeroIdentAfiliado = null;

        Long idSolicitudGlobal = null;
        Long idAfiliado = null;
        Long idRolAfiliado = null;
        List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> lstResultadoGeneralBeneficiarios;

        List<PersonaDTO> lstPersonaDTO = null;
        List<SolicitudDTO> lstSolicitudDTO = null;
        List<BeneficiarioDTO> lstBeneficiarioDTO = null;

        AfiliadoInDTO afiliadoInDTO = null;
        PersonaDTO personaDTO = null;
        SolicitudDTO solicitudDTO = null;

        Integer resultado = null;

        if ((inDTO.getNumeroRadicado() == null) || (inDTO.getNumeroIdentificacionAfiliado() == null)) {
            logger.debug(
                    "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No viene datos completos para tramitar la solicitud de registro de beneficiario");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
        else {
            numeroRadicado = inDTO.getNumeroRadicado();
            lstSolicitudDTO = buscarSolicitud(numeroRadicado);
            numeroIdentAfiliado = inDTO.getNumeroIdentificacionAfiliado();

            if (lstSolicitudDTO != null && !lstSolicitudDTO.isEmpty()) {
                    // for(SolicitudDTO solicitud :lstSolicitudDTO){
                    //     if (solicitud.getTipoTransaccion().name().contains("BENEFICIARIO")
                    //      || solicitud.getTipoTransaccion().name().contains("BENEFICIOS")) {
                    //          lstSolicitudDTO.remove(solicitud);
                    //     }
             
                    // }
                    
                lstSolicitudDTO.removeIf(solicitud -> 
                solicitud.getTipoTransaccion().name().contains("BENEFICIARIO") || 
                solicitud.getTipoTransaccion().name().contains("BENEFICIOS"));
                solicitudDTO = lstSolicitudDTO.iterator().next();
                idSolicitudGlobal = solicitudDTO.getIdSolicitud();

                ConsultarSolicitudAfiliacionPersona consultaSolAfi = new ConsultarSolicitudAfiliacionPersona(idSolicitudGlobal);
                consultaSolAfi.execute();
                SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = consultaSolAfi.getResult();
                if(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO() != null && 
                solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado()!= null){
                    idAfiliado = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado();
                }else{
                    if(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO() != null 
                    && solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getPersona() !=null){
                          idAfiliado = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getPersona().getIdAfiliado();
                    }
                }
              
                idRolAfiliado = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado();

                if (inDTO.getResultadoGeneralAfiliado() == null && inDTO.getResultadoGeneralBeneficiarios() == null) {
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se cambia el estado de la solicitud a “Aprobada” si no vienen resultados a nivel de validaciones y posteriormente a CERRADA");
                    // Se cambia el estado de la solicitud a “Aprobada” si no
                    // vienen resultados a nivel de validaciones
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
                    procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
                }
                else if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(inDTO.getResultadoGeneralAfiliado()) || ResultadoGeneralProductoNoConformeEnum.RECHAZADA.equals(inDTO.getResultadoGeneralAfiliado())) {
                    resultado = 3;
                    logger.info(
                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se cambia el estado de la solicitud a “Rechazada” si el resultado de las validaciones fueron NO_SUBSANABLES y posteriormente a CERRADA");
                    // Se cambia el estado de la solicitud a “Rechazada” si el
                    // resultado de las validaciones fueron SUBSANABLES
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

                    actualizarEstadoRolAfiliado(idRolAfiliado, EstadoAfiliadoEnum.INACTIVO);

                    logger.info("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se inactiva del afiliado principal");
                    // Se inactiva del afiliado principal
                    lstPersonaDTO = buscarAfiliado(numeroIdentAfiliado);
                    if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
                        personaDTO = lstPersonaDTO.iterator().next();
                        afiliadoInDTO = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO();
                        afiliadoInDTO.setPersona(personaDTO);
                        actualizarAfiliado(afiliadoInDTO);
 
                        // TODO
                        /*
                         * -Desafiliar al afiliado principal de la solicitud (en
                         * esta instancia de afiliación). -Cambiar de estado la
                         * solicitud a “Cerrada” <<Es en otro accion desde el
                         * frontend?>> ejecuta el comunicado “003” “Notificación de
                         * radicación de solicitud de afiliación de persona” -
                         * HU-TRA-331 Enviar comunicado).
                         */
                        ConsultarRolAfiliado rolAfiliadoService = new ConsultarRolAfiliado(idRolAfiliado);
                        rolAfiliadoService.execute();
                        RolAfiliadoModeloDTO rolAfiliadoModeloDTO = rolAfiliadoService.getResult();
                        rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
                        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                        afiliadoModeloDTO.setIdAfiliado(idAfiliado);
                        rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
                        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
                        rolAfiliadoModeloDTO.setIdRolAfiliado(idRolAfiliado);
                        rolAfiliadoModeloDTO.setIdEmpleador(inDTO.getIdEmpleador());
                        rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getTipoAfiliado());
                        rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
                        ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
                        
                        if (inDTO.getResultadoGeneralBeneficiarios() != null) {
                            lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
                            for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
                                Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
                                logger.info(
                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
                                // Se efectúa la inactivación del beneficiario
                                // incluido en la solicitud que haya sido
                                // activado durante el proceso
                                if (idBeneficiario!=null){
                                    actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
                           
                                }
                            }
                        }
                        logger.info(
                                "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación de todos los beneficiarios incluidos en la solicitud  que hayan sido activados durante el proceso");
                    }
                    else {
                        logger.debug("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No se encontro el afiliado");
                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }
                }
                else if (ResultadoGeneralProductoNoConformeEnum.SUBSANABLE.equals(inDTO.getResultadoGeneralAfiliado())) {
                    resultado = 1;
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se Cambia el estado de la solicitud a “No conforme en gestión” si el resultado general del afiliado es SUBSANABLE independiente de sus beneficiarios..");
                    // Se Cambia el estado de la solicitud a “No conforme en
                    // gestión” si el resultado general del afiliado es
                    // SUBSANABLE independiente de sus beneficiarios.
                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);

                    lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
                    if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {

                        // mapResultadoGeneralBeneficiarios.forEach((k,v) ->
                        // ...);
                        for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
                            Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
                            ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
                                    .getResultadoGeneralBeneficiario();
                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
                                logger.debug(
                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
                                // Se efectúa la inactivación del beneficiario
                                // incluido en la solicitud que haya sido
                                // activado durante el proceso
                                
                                // Si el valor del campo es “No subsanable”, se
                                // efectúa la inactivación del
                                // beneficiario (si está activo)
                                // (se debe registrar automáticamente novedad de
                                // inactivación de personas con
                                // motivo/causal: “anulación de afiliación”
                                if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE
                                        .equals(resultadoBeneficiarioDTO
                                                .getResultadoGeneralBeneficiario())) {
                                    registrarNovedadInactivacion(
                                            resultadoBeneficiarioDTO,
                                            EstadoAfiliadoEnum.INACTIVO,
                                            MotivoDesafiliacionBeneficiarioEnum.AFILIACION_ANULADA);
                                } else {
                                    actualizarEstadoBeneficiario(
                                            idBeneficiario,
                                            EstadoAfiliadoEnum.INACTIVO, null);
                                }

                            }
                        }
                    }
                }
                else if (ResultadoGeneralProductoNoConformeEnum.APROBADA.equals(inDTO.getResultadoGeneralAfiliado())) {

                    resultado = 2;
                    lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
                    if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {
                    
                        Integer todosNoSubsanables = new Integer(0);
                    

                        for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
                            Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
                            ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
                                    .getResultadoGeneralBeneficiario();
                            
                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE
                                    .equals(resultadoBeneficiarioDTO
                                            .getResultadoGeneralBeneficiario())) {
                                todosNoSubsanables++;

                            }
                            
                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
                                logger.debug(
                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
                                // Se efectúa la inactivación del beneficiario
                                // incluido en la solicitud que haya sido
                                // activado durante el proceso
                                actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
                            }
                            if (!ResultadoGeneralProductoNoConformeEnum.APROBADA.equals(resultadoBeneficiario)) {
                                resultado = 1;
                            }
                        }          
                        if(todosNoSubsanables.equals(lstResultadoGeneralBeneficiarios.size())){
                            resultado= 2;
                        }

                    }
                    

                    
                    if (resultado == 2) {
                        // Se Cambia el estado de la solicitud a “aprobada y luego a cerrada” si el resultado general del afiliado es
                        // Aprobado independiente de sus beneficiarios.
                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
                        actualizarEstadoRolAfiliado(idRolAfiliado, EstadoAfiliadoEnum.ACTIVO);
                        procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
                    }
                    else {
                        // Se Cambia el estado de la solicitud a “No conforme en
                        // gestión” si el resultado general del afiliado es
                        // SUBSANABLE independiente de sus beneficiarios.
                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
                    }
                }
            }
            else {
                logger.debug("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No se encontro la solcitud.");
                throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }

        // resultadoVerificacion:Integer(1-subsanable,2-aprobada,3-rechazada)
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoVerificacion", resultado);
        try {
            TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
            service2.execute();
        } catch (Exception e) {
            logger.error(
                    "Finaliza AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
        logger.debug(
                "Finaliza AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#validarBeneficiario(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Deprecated
    public Object validarBeneficiario(UserDTO userDTO) {
        /*
         * Se debe implementar la logica descrita desde la página 24 para el
         * botón validar. Sin embargo aun no es claro que acciones se van a
         * hacer desde la pantall y que acciones debe implementar el servicio
         * compuesto
         * 
         * <<Se realizo la verificacion de esta capacidad con Sergio y Jerson y
         * la logica a implementar ya se esta realizando en el consumo de otros
         * servicios pro tanto no se hace necesario la creacion de esta
         * capacidad [20161016:11:31::hhernandez]>>
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#afiliarAfiliadoPrincipal(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Deprecated
    public Object afiliarAfiliadoPrincipal(UserDTO userDTO) {
        // TODO: eliminar
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#gestionarProductoNoConformeSubsanable(com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void gestionarProductoNoConformeSubsanable(GestionarProductoNoConformeSubsanableDTO inDTO, UserDTO userDTO) {
        // Se actualiza el estado de la solicitud
        SolicitudAfiliacionPersonaDTO solicitud = consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
        if (solicitud != null) {
            actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_GESTIONADA);
            Map<String, Object> params = new HashMap<String, Object>();
            // el bpm no espera parametros, por ende se envía nulo
            Long idTarea = consultarTareaAfiliacionPersonas(solicitud.getIdInstanciaProceso());
            try {
                TerminarTarea service2 = new TerminarTarea(idTarea, params);
                service2.execute();
            } catch (Exception e) {
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#verificarResultadosProductoNoConforme(com.asopagos.afiliaciones.personas.composite.dto.VerificarProductoNoConformeDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO inDTO, UserDTO userDTO) {

        Integer resultadoBack = null;
        boolean notificacionFallida = false;

        ConsultarSolicitudAfiliacionPersona consultaSolAfi = new ConsultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
        consultaSolAfi.execute();
        SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = consultaSolAfi.getResult();

        switch (inDTO.getResultadoAfiliado()) {
            case NO_SUBSANABLE: {
                // CASO A: Afiliado principal “No subsanado” y
                // Beneficiarios “Subsanado” o “No subsanado”
                gestionarSolicitudRechazada(inDTO, solicitudAfiliacionPersonaDTO);
                resultadoBack = 1;
                break;
            }
            case SUBSANABLE: {
                //En caso de que el PNC sea subsanable pero el empleador esté inactivo se rechaza la solicitud
                //Y se inactiva a afiliado y beneficiarios
                if(Boolean.TRUE.equals(inDTO.getEmpleadorInactivo())) {
                    gestionarSolicitudRechazada(inDTO, solicitudAfiliacionPersonaDTO);
                    resultadoBack = 2;
                    break;
                }
                
                // CASO B: Afiliado principal “Subsanable” – Beneficiarios
                // “Subsanable” o “No subsanable”

                // Cambiar el estado de la solicitud a “No conforme en gestión”.
                actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.APROBADA);

                // TODO esta logica no se hace necesaria debido a que en los
                // parametros de la invocacion viene ya el resultado de cada
                // beneficiario
                // notificacionFallida = guardarAfiliacionSubsanable(inDTO);

                List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> lstResultadoGeneralBeneficiarios = inDTO
                        .getResultadoBeneficiarios();
                if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {
                    for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
                        Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
                        ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
                                .getResultadoGeneralBeneficiario();
                        if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
                            logger.debug(
                                    "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
                            // Se efectúa la inactivación del beneficiario incluido
                            // en la solicitud que haya sido activado durante el
                            // proceso
                            actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
                            notificacionFallida = true;

                        }
                    }
                }

                if (notificacionFallida) {
                    // enviarNotificacionRadicacionSolicitudAfiliacion();
                }

                // Cambiar de estado la solicitud a “Cerrada”.
                actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
                actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.ACTIVO);
                procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
                resultadoBack = 2;
                break;
            }
            default: {
                throw new IllegalArgumentException("Dato invalido");
            }
        }

        // estadoSolicitud:Integer(1-Rechazada,2-Cerrado)
        Map<String, Object> params = new HashMap<>();
        params.put("estadoSolicitud", resultadoBack);
        try {
            TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
            service2.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
    }

    /**
     * Procesa la inactivación de afiliado y beneficiarios.
     * Rechaza la solicitud de afiliación 
     * @param inDTO
     * @param solicitudAfiliacionPersonaDTO
     */
    private void gestionarSolicitudRechazada(VerificarProductoNoConformeDTO inDTO,
            SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO) {
        guardarAfiliacionNoSubsanable(inDTO);

        // (se debe registrar automáticamente novedad de inactivación de
        // personas con motivo/causal: “anulación de afiliación” – Ver HU
        // novedades personas – inactivación de beneficiarios).
        // registrarNovedadInactivacionPersonas();

        actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(),
                EstadoAfiliadoEnum.INACTIVO);

        RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
        rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
        afiliadoModeloDTO.setIdAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado());
        rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
        rolAfiliadoModeloDTO.setIdRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado());
        rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getTipoAfiliado());
        rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
        ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
    }

    /**
     * Método que hace la peticion REST al servicio de crear un afiliado a la
     * caja de compenscion familiar indicado en <code>AfiliadoInDTO</code>
     * 
     * @param afiliadoInDTO
     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
     *        de un Afiliado
     * 
     * @return <code>AfiliadoInDTO</code> el DTO con los datos del afiliado
     */
    private AfiliadoInDTO crearAfiliado(AfiliadoInDTO afiliadoInDTO) {
        logger.debug("Inicia crearAfiliado( AfiliadoInDTO )");
        AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
        CrearAfiliado crearAfiliadoService;
        crearAfiliadoService = new CrearAfiliado(afiliadoInDTO);
        crearAfiliadoService.execute();
        afiliadoDTO = (AfiliadoInDTO) crearAfiliadoService.getResult();
        logger.debug("Finaliza crearAfiliado( AfiliadoInDTO )");
        return afiliadoDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de crear una Solicitud de
     * Afiliacion de Persona indicado en <code>AfiliadoInDTO</code>
     * 
     * @param afiliadoInDTO
     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
     *        de un Afiliado
     * 
     * @return <code>Long</code> El identificador de la solicitud de afiliacion
     *         de persona
     */
    private Long crearSolicitudAfiliacionPersona(AfiliadoInDTO afiliadoInDTO) {
        logger.debug("Inicia crearSolicitudAfiliacionPersona( AfiliadoInDTO )");
        Long idSolicitud = new Long(0);
        CrearSolicitudAfiliacionPersona crearSolicitudAfiliacionPersonaService;
        crearSolicitudAfiliacionPersonaService = new CrearSolicitudAfiliacionPersona(afiliadoInDTO);
        crearSolicitudAfiliacionPersonaService.execute();
        idSolicitud = (Long) crearSolicitudAfiliacionPersonaService.getResult();
        logger.debug("Finaliza crearSolicitudAfiliacionPersona( AfiliadoInDTO )");
        return idSolicitud;
    }

    /**
     * Método que hace la peticion REST al servicio de iniciar el Proceso de
     * Afiliacion de Persona en el BPM
     * 
     * @param idSolicitudGlobal
     *        El identificador de la solicitud global de afiliacion de
     *        persona
     * @param numeroRadicado
     *        Número de radicación de la solicitud
     * @param usuarioDTO
     *        DTO para el servicio de autenticación usuario
     * 
     * @return <code>Long</code> El identificador de la Instancia Proceso
     *         Afiliacion de la Persona
     */
    private Long iniciarProcesoAfiliacionPersona(Long idSolicitudGlobal,String numeroRadicado, UserDTO userInDTO) {
        logger.debug("Inicia iniciarProcesoAfiliacionPersona( idSolicitudGlobal )");
        Long idInstanciaProcesoAfiliacionPersona = new Long(0);
        // String tokenAccesoCore = generarTokenAccesoCore();
        Map<String, Object> parametrosProceso = new HashMap<String, Object>();
        
        String tiempoProcesoSolicitud = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_PROCESO_SOLICITUD);
        String tiempoAsignacionBack = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_ASIGNACION_BACK);
        String tiempoSolicitudPendienteDocumentos = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);
        
        parametrosProceso.put("idSolicitud", idSolicitudGlobal);
        parametrosProceso.put("usuarioFront", userInDTO.getNombreUsuario());
        parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
        parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        parametrosProceso.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
        parametrosProceso.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
        IniciarProceso iniciarProcesoPersonaService = new IniciarProceso(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL, parametrosProceso);
        // iniciarProcesoPersonaService.setToken(tokenAccesoCore);
        iniciarProcesoPersonaService.execute();
        idInstanciaProcesoAfiliacionPersona = (Long) iniciarProcesoPersonaService.getResult();
        logger.debug("Finaliza iniciarProcesoAfiliacionPersona( idSolicitudGlobal )");
        return idInstanciaProcesoAfiliacionPersona;
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar el
     * Identificador Instancia del Proceso de Solicitud de afiliacion De Persona
     * *
     * 
     * @param idSolicitudGlobal
     *        <code>Long</code> El identificador de la solicitud global de
     *        afiliacion de persona
     * 
     * @param idInstanciaProceso
     *        <code>Long</code> El identificador de la Instancia Proceso
     *        Afiliacion de la Persona
     */
    private void actualizarIdInstanciaProcesoSolicitudDePersona(Long idSolicitudGlobal, Long idInstanciaProceso) {
        logger.debug("Inicia actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
        GuardarInstanciaProceso guardarInstanciaProcesoService = new GuardarInstanciaProceso(idSolicitudGlobal,
                String.valueOf(idInstanciaProceso));
        guardarInstanciaProcesoService.execute();
        logger.debug("Finaliza actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar el estado de
     * una Solicitud de Afiliacion de Persona indicado en
     * <code>EstadoSolicitudAfiliacionPersonaEnum</code>
     * 
     * @param idSolicitudGlobal
     *        <code>Long</code> El identificador de la solicitud global de
     *        afiliacion de persona
     * @param estadoSolcitudAfiliacionPersona
     *        <code>EstadoSolicitudAfiliacionPersonaEnum</code> Enumeración
     *        que representa los estados de la solicitud de afiliación de
     *        una persona
     */
    private void actualizarEstadoSolicitudPersona(Long idSolicitudGlobal,
            EstadoSolicitudAfiliacionPersonaEnum estadoSolcitudAfiliacionPersona) {
        logger.debug("Inicia actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
        ActualizarEstadoSolicitudAfiliacionPersona actualizarSoliticutdAfilPersonaService = new ActualizarEstadoSolicitudAfiliacionPersona(
                idSolicitudGlobal, estadoSolcitudAfiliacionPersona);
        actualizarSoliticutdAfilPersonaService.execute();
        logger.debug("Finaliza actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
    }

    /**
     * Método que hace la peticion REST al servicio de crear una Lista de
     * Chequeo (requisitos) para una benficiario o afiliado indicado en
     * <code>ListaChequeoDTO</code>
     * 
     * @param listaChequeo
     *        <code>ListaChequeoDTO</code> DTO que transporta los datos la
     *        lista de chequeo (requisitos documentales)
     * 
     * @return List<code>Long</code> una lista con los identificadores de cada
     *         item de chequeo (requisito) creado
     */
    private List<Long> crearListaChequeoAfiliacionPersona(ListaChequeoDTO listaChequeo) {
        logger.debug("Inicia crearListaChequeoAfiliacionPersona( ListaChequeoDTO )");
        List<Long> lstResult = new ArrayList<Long>();
        GuardarListaChequeo crearListaChequeoService = new GuardarListaChequeo(listaChequeo);
        crearListaChequeoService.execute();
        lstResult = crearListaChequeoService.getResult();
        logger.debug("Finaliza crearListaChequeoAfiliacionPersona( ListaChequeoDTO )");
        return lstResult;

    }

    /**
     * Método que hace la peticion REST al servicio de buscar un afiliado
     * 
     * @param numeroIdentificacion
     *        <code>String</code> Numero de identiticacion de un Afiliado
     * 
     * @return List <code>PersonaDTO</code> Lista de DTO's que transporta los
     *         datos básicos de una persona
     */
    private List<PersonaDTO> buscarAfiliado(String numeroIdentificacion) {
        logger.debug("Inicia buscarAfiliado( numeroIdentificacion )");
        List<PersonaDTO> lstPersonaDTO = new ArrayList<PersonaDTO>();
        logger.info("***numeroIdentificacion***"+numeroIdentificacion);
        BuscarAfiliados buscarAfiliadosService = new BuscarAfiliados(null, null, null, null, numeroIdentificacion, null);
        buscarAfiliadosService.execute();
        lstPersonaDTO = buscarAfiliadosService.getResult();
        logger.debug("Finaliza buscarAfiliado( numeroIdentificacion )");
        return lstPersonaDTO;
    }

    /**
     * Método que hace la peticion REST al servicio que permite buscar una
     * Solicitud
     * 
     * @param numeroRadicado
     *        <code>String</code> El numero de radicado de la solicitud
     * 
     * @return List <code>SolicitudDTO</code> Lista de DTO's que transporta los
     *         datos de una solcitud
     */
    private List<SolicitudDTO> buscarSolicitud(String numeroRadicado) {
        logger.debug("Inicia buscarSolicitud( numeroRadicado )");
        List<SolicitudDTO> lstSolicitudDTO = new ArrayList<SolicitudDTO>();
        BuscarSolicitud buscarSolicitudService = new BuscarSolicitud(numeroRadicado);
        buscarSolicitudService.execute();
        lstSolicitudDTO = buscarSolicitudService.getResult();
        logger.debug("Finaliza buscarSolicitud( numeroRadicado )");
        return lstSolicitudDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de consultar una Solicitud
     * de Afiliacion de Persona
     * 
     * @param idSolicitudGlobal
     *        <code>Long</code> El identificador de la solicitud global de
     *        afiliacion de persona
     * 
     * @return <code>SolicitudAfiliacionPersonaDTO</code> DTOs que transporta
     *         los datos de una solcitud de Afiliacion de Persona
     */
    private SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersona(Long idSolicitudGlobal) {
        logger.debug("Inicia consultarSolicitudAfiliacionPersona( idSolicitudGlobal )");
        SolicitudAfiliacionPersonaDTO solicitudAfiliDTO = new SolicitudAfiliacionPersonaDTO();
        ConsultarSolicitudAfiliacionPersona consultarSolAfilPersonaService = new ConsultarSolicitudAfiliacionPersona(idSolicitudGlobal);
        consultarSolAfilPersonaService.execute();
        solicitudAfiliDTO = (SolicitudAfiliacionPersonaDTO) consultarSolAfilPersonaService.getResult();
        logger.debug("Finaliza consultarSolicitudAfiliacionPersona( idSolicitudGlobal )");
        return solicitudAfiliDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar un Afiliado
     * indicado en <code>AfiliadoInDTO</code>
     * 
     * @param afiliadoInDTO
     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
     *        de un Afiliado
     */
    private void actualizarAfiliado(AfiliadoInDTO afiliadoInDTO) {
        logger.debug("Inicia actualizarAfiliado( PersonaDTO )");
        ActualizarAfiliado actualizarAfiliadoService = new ActualizarAfiliado(afiliadoInDTO.getPersona().getIdAfiliado(), afiliadoInDTO);
        actualizarAfiliadoService.execute();
        logger.debug("Finaliza actualizarAfiliado( PersonaDTO )");
    }

    /**
     * Método que hace la peticion REST al servicio de obtener tarea activa para
     * posteriomente finalizar el proceso de Afiliación personas presencial
     * 
     * @param idInstanciaProceso
     *        <code>String</code> El identificador de la Instancia Proceso
     *        Afiliacion de la Persona
     * 
     * @return <code>Long</code> El identificador de la tarea Activa
     */
    private Long consultarTareaAfiliacionPersonas(String idInstanciaProceso) {
        logger.debug("Inicia consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
        String idTarea = null;
        try {
            Map<String, Object> mapResult = new HashMap<String, Object>();
            ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
            obtenerTareaActivaService.execute();
            mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
            logger.debug("Finaliza consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
            idTarea = ((Integer) mapResult.get("idTarea")).toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
        return new Long(idTarea);
    }

    /**
     * Método que hace la peticion REST al servicio de registrar intento de
     * afiliación indicado en <code>IntentoAfiliacionInDTO</code>
     * 
     * @param intentoAfiliacionInDTO
     *        <code>IntentoAfiliacionInDTO</code> DTO para el registro de
     *        intentos de afiliación
     */
    private void registrarIntentoAfiliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO) {
        logger.debug("Inicia registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
        RegistrarIntentoAfliliacion registrarIntentoAfliliacion = new RegistrarIntentoAfliliacion(intentoAfiliacionInDTO);
        registrarIntentoAfliliacion.execute();
        logger.debug("Finaliza registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
    }

    /**
     * /** Método que hace la peticion REST al servicio de terminar tarea para
     * finalizar el proceso de Afiliación personas presencial
     * 
     * @param idTarea
     *        <code>Long</code> El identificador de la tarea Activa del
     *        proceso de Afiliación
     * @param cumpleRequisitosDocumentales
     *        <code>Boolean</code> Si cumple o no los Requisitos
     *        Documentales
     * @param documentosFisicos
     *        <code>Boolean</code> Si cumple o no los documentos Fisicos
     */
    private void terminarTareaAfiliacionPersonas(Long idTarea, Boolean cumpleRequisitosDocumentales, Boolean documentosFisicos) {
        logger.debug("Inicia terminarTareaAfiliacionPersonas( idTarea, cumpleRequisitosDocumentales, documentosFisicos)");
        Map<String, Object> params = new HashMap<>();
        params.put("cumpleRequisitosDocumentales", cumpleRequisitosDocumentales);
        params.put("documentosFisicos", documentosFisicos);
        try {
            TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
            terminarTareaService.execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
        logger.debug("Finaliza terminarTareaAfiliacionPersonas( idTarea, cumpleRequisitosDocumentales, documentosFisicos)");
    }

    /**
     * Método que hace la peticion REST al servicio de terminar tarea para
     * finalizar el proceso de Radicacion Abreviada Afiliación personas
     * presencial
     * 
     * @param idTarea
     *        <code>Long</code> El identificador de la tarea Activa del
     *        proceso de Afiliación
     * @param tipoRadicacionSolcitud
     *        <code>TipoRadicacionEnum</code> Enumeración que representa los
     *        estados de documentación del afiliación
     * @param incluyenBeneficiarios
     *        <code>boolean</code> Si se incluyen o no beneficiarios
     */
    private void terminarTareaRadicacionAbreviadaAfiliacionPersonas(Long idTarea, TipoRadicacionEnum tipoRadicacionSolcitud,
            boolean incluyenBeneficiarios, boolean intentoAfiliacion, boolean metodoEnvio, String numeroRadicado) {
        logger.debug("Inicia terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
        Map<String, Object> params = new HashMap<>();
        int tipoRadicacion = 0;
        if (TipoRadicacionEnum.COMPLETA.equals(tipoRadicacionSolcitud)) {
            tipoRadicacion = 1;
        }
        else if (TipoRadicacionEnum.ABREVIADA.equals(tipoRadicacionSolcitud)) {
            tipoRadicacion = 2;
        }
        params.put("radicacionCompleta", tipoRadicacion);
        params.put("incluyeBeneficiarios", incluyenBeneficiarios);
        params.put("registroIntento", intentoAfiliacion);
        params.put("documentosFisicos", metodoEnvio);
        params.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
        try {
            logger.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> que ves lisa ???!"+idTarea);
            TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
            terminarTareaService.execute();
        } catch (Exception e) {
            logger.error(
                    "Finaliza terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }
        logger.debug(
                "Finaliza terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
    }

    /**
     * Método que hace la peticion REST al servicio de generar nuemro de
     * radicado
     * 
     * @param idSolicitud
     *        <code>Long</code> El identificador de la solicitud
     * @param sedeCajaCompensacion
     *        <code>String</code> El usuario del sistema
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        logger.debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        return radicarSolicitudService.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar una Solicitud
     * de Afiliacion de una Persona
     *
     * @param idSolicitudGlobal <code>Long</code> El identificador de la
     * solicitud global de afiliacion de persona
     * @param solicitudDTO <code>SolicitudDTO</code> DTO's que transporta los
     * datos de una solcitud
     */
    private void actualizarSolicitudAfiliacionPersona(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
        logger.info("Inicia actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO {})" + solicitudDTO);
        logger.info("solicitudDTO.getEstadoSolicitud()" + solicitudDTO.getEstadoSolicitud());
        ActualizarSolicitudAfiliacionPersona actualizarSolicitudAfiliacionPersonaService = new ActualizarSolicitudAfiliacionPersona(
                idSolicitudGlobal, solicitudDTO);
        actualizarSolicitudAfiliacionPersonaService.execute();
        logger.info("Finaliza actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO)");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar una Solicitud
     * de Afiliacion de una Persona masivamente
     *
     * @param idSolicitudGlobal <code>Long</code> El identificador de la
     * solicitud global de afiliacion de persona
     * @param solicitudDTO <code>SolicitudDTO</code> DTO's que transporta los
     * datos de una solcitud
     */
    private void actualizarSolicitudAfiliacionPersonaMasivas(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
        logger.info("Inicia actualizarSolicitudAfiliacionPersonaMasivas( idSolicitudGlobal, SolicitudDTO {})" + solicitudDTO);
        logger.info("solicitudDTO.getEstadoSolicitud()" + solicitudDTO.getEstadoSolicitud());
        logger.info("solicitudDTO.getEstadoSolicitud()" + solicitudDTO.getResultadoProceso());
        ActualizarSolicitudAfiliacionPersonaMasivas actualizarSolicitudAfiliacionPersonaMasivasService = new ActualizarSolicitudAfiliacionPersonaMasivas(
                idSolicitudGlobal, solicitudDTO);
        actualizarSolicitudAfiliacionPersonaMasivasService.execute();
        logger.info("Finalizassss actualizarSolicitudAfiliacionPersonaMasivas( idSolicitudGlobal, SolicitudDTO)");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar el estado de
     * la documentacion Afiliacion
     *
     * @param idSolicitudGlobal <code>Long</code> El identificador de la
     * solicitud global de afiliacion de persona
     * @param estado <code>EstadoDocumentacionEnum</code> Enumeración que
     * representa los estados de documentación del afiliación
     */
    private void actualizarEstadoDocumentacionAfiliacion(Long idSolicitudGlobal, EstadoDocumentacionEnum estadoDocumentacion) {
        logger.debug("Inicia actualizarEstadoDocumentacionAfiliacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
        ActualizarEstadoDocumentacionAfiliacion actualizarEstDocAfilService = new ActualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal,
                estadoDocumentacion);
        actualizarEstDocAfilService.execute();
        logger.debug("Finaliza actualizarEstadoDocumentacionAfiliacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar la Gestion de
     * Solicitud de Asociacion de la afiliado con la entidad pagadora
     *
     * @param idEntidadPagadora <code>Long</code> El identificador de la entidad
     * pagadora asociada
     * @param solicitudes List
     * <code>SolicitudAsociacionPersonaEntidadPagadoraDTO</code> Lista de DTO's
     * que trasporta las Solicitud de Asociacion de la afiliado con la entidad
     * pagadora
     *
     * @return <code>Long</code> El identificador de la Solicutd de Gestion del
     * afiliado con la entidad pagadora
     */
    private String actualizarGestionSolicitudesAsociacion(Long idEntidadPagadora,
            List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes) {
        logger.debug("Inicia actualizarGestionSolicitudesAsociacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
        String soaConsecutivo = "";
        ActualizarGestionSolicitudesAsociacion actGestionSolAsociacionService = new ActualizarGestionSolicitudesAsociacion(
                idEntidadPagadora, solicitudes);
        actGestionSolAsociacionService.execute();
        soaConsecutivo = (String) actGestionSolAsociacionService.getResult();
        logger.debug("Finaliza actualizarGestionSolicitudesAsociacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
        return soaConsecutivo;

    }

    /**
     * Método que hace la peticion REST al servicio de calcular la Categoria del
     * Afiliado indicado en <code>DatosBasicosIdentificacionDTO</code>
     * 
     * @param inDTO
     *        <code>DatosBasicosIdentificacionDTO</code> DTO que trasnporta
     *        los datos básicos de identificación de una persona
     * 
     * @return <code>CategoriaPersonaEnum</code> Enumeración que representa la
     *         categoria de una persona
     */
    private void calcularCategoriaAfiliado(AfiliadoInDTO inDTO) {
        logger.debug("Inicia calcularCategoriaAfiliado ( DatosBasicosIdentificacionDTO )");
        
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setTipoIdentificacion(inDTO.getPersona().getTipoIdentificacion());
        categoriaDTO.setNumeroIdentificacion(inDTO.getPersona().getNumeroIdentificacion());
        categoriaDTO.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NUEVA_AFILIACION);
        CalcularCategoriasAfiliado calcularCategoria = new CalcularCategoriasAfiliado(categoriaDTO);
    }

    /**
     * Método que hace la peticion REST al servicio de crear asociacion del
     * Afiliado con la Entidad Pagadora indicado en <code>AfiliadoInDTO</code>
     * 
     * @param inAfiliadoDTO
     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
     *        de un Afiliado
     * 
     * @return <code>Long</code> El identificador de la Solicitud de Asociacion
     *         del Afiliado con la Entidad Pagadora
     */
    private Long crearAsociacionAfiliadoEntidadPagadora(AfiliadoInDTO inAfiliadoDTO) {
        logger.debug("Inicia crearAsociacionAfiliadoEntidadPagadora( inAfiliadoDTO )");
        Long idSolicutdAsociacionAfiliadoEntidadPagadora = new Long(0);
        CrearSolicitudAsociacionPersonaEntidadPagadora asociacionAfiliadoEntidadPagadoraService = new CrearSolicitudAsociacionPersonaEntidadPagadora(
                inAfiliadoDTO);
        asociacionAfiliadoEntidadPagadoraService.execute();
        idSolicutdAsociacionAfiliadoEntidadPagadora = (Long) asociacionAfiliadoEntidadPagadoraService.getResult();
        logger.debug("Finaliza crearAsociacionAfiliadoEntidadPagadora( inAfiliadoDTO )");
        return idSolicutdAsociacionAfiliadoEntidadPagadora;
    }

    /**
     * Método que hace la peticion REST al servicio de obtener la lista de
     * beneficiarios asociados a un afiliado
     * 
     * @param idAfiliado
     *        <code>Long</code> El identificador del afiliado
     * 
     * @return List<BeneficiarioDTO> La lista de DTO's que transporta los datos
     *         básicos de beneficiarios.
     */
    private List<BeneficiarioDTO> consultarBeneficiariosDeAfiliado(Long idAfiliado) {
        logger.debug("Inicia consultarBeneficiariosDeAfiliado( idAfiliado )");
        List<BeneficiarioDTO> lstBeneficiariosDTO;
        ConsultarBeneficiarios consultarBeneficiariosService = new ConsultarBeneficiarios(idAfiliado, false);
        consultarBeneficiariosService.execute();
        lstBeneficiariosDTO = consultarBeneficiariosService.getResult();
        if (lstBeneficiariosDTO == null) {
            lstBeneficiariosDTO = new ArrayList<>();
        }
        logger.debug("Finaliza consultarBeneficiariosDeAfiliado( idAfiliado )");
        return lstBeneficiariosDTO;
    }
    
    private List<BeneficiarioDTO> consultarBeneficiariosDeAfiliadoMismaFecha(Long idAfiliado) {
        logger.debug("Inicia ConsultarBeneficiariosMismaFecha( idAfiliado )");
        List<BeneficiarioDTO> lstBeneficiariosDTO;
        ConsultarBeneficiariosMismaFecha consultarBeneficiariosService = new ConsultarBeneficiariosMismaFecha(idAfiliado, false);
        consultarBeneficiariosService.execute();
        lstBeneficiariosDTO = consultarBeneficiariosService.getResult();
        if (lstBeneficiariosDTO == null) {
            lstBeneficiariosDTO = new ArrayList<>();
        }
        logger.debug("Finaliza ConsultarBeneficiariosMismaFecha( idAfiliado )");
        return lstBeneficiariosDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de registrar la informacion
     * de un benenficiario tip conyuge asociado a un afiliado
     * 
     * @param idAfiliado
     *        <code>Long</code> El identificador del afiliado
     * @param inDTO
     *        <code>IdentificacionUbicacionPersonaDTO</code> DTO que
     *        transporta los datos de identificacion ubicacion persona
     * 
     * @return <code>Long</code> El identificador del beneficiario
     */
    private Long registrarInformacionBeneficiarioConyuge(Long idAfiliado, IdentificacionUbicacionPersonaDTO inDTO) {
        logger.debug("Inicia registrarInformacionBeneficiarioConyuge(Long, IdentificacionUbicacionPersonaDTO )");
        logger.info("paso por aquii 1");
        Long idBeneficiario = new Long(0);
        RegistrarInformacionBeneficiarioConyugue registrarInfoBeneConyugeService = new RegistrarInformacionBeneficiarioConyugue(idAfiliado,
                inDTO);
        logger.info("paso por aquii 2");
        registrarInfoBeneConyugeService.execute();
        idBeneficiario = (Long) registrarInfoBeneConyugeService.getResult();
        logger.info("paso por aquii 3");
        logger.debug("Finaliza registrarInformacionBeneficiarioConyuge(Long, IdentificacionUbicacionPersonaDTO )");
        return idBeneficiario;
    }

    /**
     * Método que hace la peticion REST al servicio de registrar la informacion
     * de un benenficiario tip hijo o Padre asociado a un afiliado
     * 
     * @param idAfiliado
     *        <code>Long</code> El identificador del afiliado
     * @param inDTO
     *        <code>BeneficiarioHijoPadreDTO</code> DTO que transporta los
     *        datos básicos de un beneficiario hijo o padre
     * 
     * @return <code>Long</code> El identificador del beneficiario
     */
    private Long registrarInformacionBeneficiarioHijoOPadre(Long idAfiliado, BeneficiarioHijoPadreDTO inDTO) {
        logger.info("Inicia registrarInformacionBeneficiarioHijoOPadre(Long, BeneficiarioHijoPadreDTO )");
        
        Long idBeneficiario = new Long(0);
        RegistrarInformacionBeneficiarioHijoPadre registrarInforBeneHijoPadreService = new RegistrarInformacionBeneficiarioHijoPadre(
                idAfiliado, inDTO);
        registrarInforBeneHijoPadreService.execute();
        idBeneficiario = (Long) registrarInforBeneHijoPadreService.getResult();
        logger.info("Finaliza registrarInformacionBeneficiarioHijoOPadre(Long, BeneficiarioHijoPadreDTO )");
        return idBeneficiario;
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     * 
     * @param nombreUsuarioCaja
     *        <code>String</code> El nombre de usuario del funcionario de la
     *        caja que realiza la consulta
     * 
     * @return <code>UsuarioCCF</code> DTO para el servicio de autenticación
     *         usuario
     */
    private UsuarioCCF consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.debug("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        UsuarioCCF usuarioCCF = new UsuarioCCF();
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        usuarioCCF = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacionService.getResult();
        logger.debug("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return usuarioCCF;
    }

    /**
     * @param nombreUsuarioCaja
     * @return
     */
    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     * 
     * @param sedeCaja
     *        <code>Long</code> el identificador del afiliado
     * @param procesoEnum
     *        <code>ProcesoEnum</code> el identificador del afiliado
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
     * Método que hace la peticion REST al servicio de obtener el grupo familiar
     * asociados a un afiliado
     *
     * @param idAfiliado <code>Long</code> el identificador del afiliado
     *
     * @return List <code>GrupoFamiliarDTO</code> DTO que transporta los datos
     * de un grupo familiar
     */
    private List<GrupoFamiliarDTO> consultarGrupoFamiliar(Long idAfiliado) {
        logger.info("Inicia consultarGrupoFamiliar( idAfiliado )" + idAfiliado);
        List<GrupoFamiliarDTO> lstGrupoFamiliarDTO = null;
        ConsultarGrupoFamiliar consultarGrupoFamiliarService = new ConsultarGrupoFamiliar(idAfiliado);
        consultarGrupoFamiliarService.execute();
        lstGrupoFamiliarDTO = consultarGrupoFamiliarService.getResult();
        logger.debug("Finaliza consultarGrupoFamiliar( idAfiliado )" + consultarGrupoFamiliarService.getResult());
        return lstGrupoFamiliarDTO;
    }

    /**
     * Método que hace la peticion REST al servicio de asociar un beneficiario
     * al grupo familiar de un afiliado a la caja
     *
     * @param idAfiliado <code>Long</code> El identificador del afiliado
     * @param idGrupoFamiliar <code>Long</code> El Identificador del grupo
     * familiar
     * @param inDTO <code>DatosBasicosIdentificacionDTO</code> DTO para los
     * datos básicos de identificación de una persona
     *
     */
    private boolean asociarBeneficiarioAGrupoFamiliar(Long idAfiliado, Long idGrupoFamiliar, DatosBasicosIdentificacionDTO inDTO) {
        logger.debug("Inicia asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, DatosBasicosIdentificacionDTO )");
        logger.info("beneficiario 1: "+ idAfiliado + ": " + idGrupoFamiliar );
        boolean success = true;
        try {
            AsociarBeneficiarioAGrupoFamiliar consultarGrupoFamiliarService = new AsociarBeneficiarioAGrupoFamiliar(idGrupoFamiliar,
                    idAfiliado, inDTO);
            consultarGrupoFamiliarService.execute();
            logger.info("beneficiario 2");
        } catch (Exception e) {
            success = false;
            logger.error("beneficiario 3", e);
        }
        logger.debug("Finaliza asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, DatosBasicosIdentificacionDTO )");
        logger.info("beneficiario 4");
        return success;
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar un
     * beneficiario
     *
     * @param idAfiliado <code>Long</code> El identificador del afiliado
     * @param beneficiarioDTO <code>BeneficiarioDTO</code> DTO que transporta
     * los datos básicos de un beneficiario
     *
     * @return <code>Long</code> El identificador del Beneficiario
     */
    private Long actualizarBeneficiario(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
        logger.debug("Inicia actualizarBeneficiario( idAfiliado ,BeneficiarioDTO )");
        Long idBeneficiario = new Long(0);
        RegistrarBeneficiario registrarBeneficiarioService = new RegistrarBeneficiario(idAfiliado, beneficiarioDTO);
        registrarBeneficiarioService.execute();
        idBeneficiario = (Long) registrarBeneficiarioService.getResult();
        logger.debug("Finaliza actualizarBeneficiario( idAfiliado ,BeneficiarioDTO ) ");
        return idBeneficiario;
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar el estado de
     * un rol afiliado
     *
     * @param idAfiliado <code>Long</code> El identificador del rol afiliado
     * @param estadoAfiliadoEnum
     * @param beneficiarioDTO <code>EstadoAfiliadoEnum</code> enumeracion que
     * indica el nuevo estado del rol afiliado
     */
    private void actualizarEstadoRolAfiliado(Long idRolAfiliado, EstadoAfiliadoEnum estadoAfiliadoEnum) {
        logger.debug("Inicia actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
        ActualizarEstadoRolAfiliado actualizarEstadoRolAfiliado = new ActualizarEstadoRolAfiliado(idRolAfiliado, estadoAfiliadoEnum);
        actualizarEstadoRolAfiliado.execute();
        logger.debug("Finaliza actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
    }

    /**
     * Método que hace la peticion REST al servicio de actualizar el estado de
     * un beneficiario
     *
     * @param idAfiliado <code>Long</code> El identificador del afiliado
     * @param estado <code>EstadoAfiliadoEnum</code> enumeracion que indica el
     * nuevo estado del beneficiario
     */
    private void actualizarEstadoBeneficiario(Long idBeneficario, EstadoAfiliadoEnum estado,
            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        logger.debug("Inicia actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
        ActualizarEstadoBeneficiario actualizarEstadoBeneficario = new ActualizarEstadoBeneficiario(idBeneficario, motivoDesafiliacion,
                estado);
        actualizarEstadoBeneficario.execute();
        logger.debug("Finaliza actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
    }

    /**
     * Método que consulta los productos no conformes
     * 
     * @param idSolicitudAfiliacion
     * @param resuelto
     * @return
     */
    private List<ProductoNoConformeDTO> consultarProductosNoConforme(Long idSolicitudAfiliacion, Boolean resuelto) {
        logger.debug("Inicia consultarProductosNoConforme(Long, Boolean)");
        ConsultarProductosNoConforme consultarProductosNoConforme = new ConsultarProductosNoConforme(idSolicitudAfiliacion, resuelto);
        consultarProductosNoConforme.execute();
        List<ProductoNoConformeDTO> productos = consultarProductosNoConforme.getResult();
        logger.debug("Finaliza consultarProductosNoConforme(Long, Boolean)");
        return productos;
    }

    /**
     * Método con la lógica para afiliar un afilido subsanble con sus
     * beneficiarios subsanados para el <strong>CASO B: Afiliado principal
     * “Subsanable” – Beneficiarios “Subsanable” o “No subsanable”</strong><br>
     * usado por los métodos:
     * <ul>
     * <li>
     * {@link #afiliarAfiliadoPrincipal(VerificarProductoNoConformeDTO, UserDTO)}
     * (HU-122 primera revisión back)</li>
     * <li>
     * {@link #verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO, UserDTO)}
     * (HU-116 segunga revisión back)</li>
     * </ul>
     * 
     * @param inDTO
     * @return true si hubo algún beneficiario no subsanado, de lo contrario
     *         false
     */
    private Boolean guardarAfiliacionSubsanable(VerificarProductoNoConformeDTO inDTO) {
        // CASO B: Afiliado principal “Subsanable” – Beneficiarios “Subsanable”
        // o “No subsanable”
        // TODO verificar de que depende este valor
        Boolean resultado = false;
        // Para los beneficiarios asociados a la solicitud radicada que presenta
        // producto no conforme no subsanable, se les debe cambiar el valor del
        // campo “Estado con respecto al afiliado principal” a “Inactivo”
        List<ProductoNoConformeDTO> productosNoConformes = consultarProductosNoConforme(inDTO.getIdSolicitudGlobal(), resultado);
        Map<Long, ProductoNoConformeDTO> beneficiarioProductoNoConforme = new HashMap<Long, ProductoNoConformeDTO>();
        for (ProductoNoConformeDTO pNoConforme : productosNoConformes) {
            beneficiarioProductoNoConforme.put(pNoConforme.getIdBeneficiario(), pNoConforme);
        }

        boolean result = false;
        ProductoNoConformeDTO productoNoConformeDTO;
        ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevision;

        List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(inDTO.getIdAfiliado());
        for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
            if (beneficiarioProductoNoConforme.containsKey(beneficiarioDTO.getIdBeneficiario())) {
                productoNoConformeDTO = beneficiarioProductoNoConforme.get(beneficiarioDTO.getIdBeneficiario());
                if (ResultadoGestionProductoNoConformeSubsanableEnum.NO_SUBSANABLE
                        .equals(productoNoConformeDTO.getResultadoRevisionBack2())) {
                    actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, null);
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Método con la lógica para continua el proceso de un afilido no subsanble
     * con sus beneficiarios para el <strong>CASO A: Afiliado principal “No
     * subsanable” – Beneficiarios “Subsanable” o “No subsanable”</strong><br>
     * usado por los métodos:
     * <ul>
     * <li>
     * {@link #afiliarAfiliadoPrincipal(VerificarProductoNoConformeDTO, UserDTO)}
     * (HU-122 primera revisión back)</li>
     * <li>
     * {@link #verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO, UserDTO)}
     * (HU-116 segunga revisión back)</li>
     * </ul>
     * 
     * @param inDTO
     */
    private void guardarAfiliacionNoSubsanable(VerificarProductoNoConformeDTO inDTO) {
        // CASO A: Afiliado principal “No subsanable” – Beneficiarios
        // “Subsanable” o “No subsanable”

        // Cambiar el estado de la solicitud a “Rechazada”.
        actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);

        // Desafiliar al afiliado principal de la solicitud (en esta
        // instancia de afiliación).
        actualizarEstadoRolAfiliado(inDTO.getIdRolAfiliado(), EstadoAfiliadoEnum.INACTIVO);

        // Para los beneficiarios asociados a la solicitud radicada se debe
        // cambiar el valor del campo “Estado con respecto al afiliado
        // principal” a “Inactivo”.
        List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(inDTO.getIdAfiliado());
        for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
            
            //Solo se deben inactivar los benefiarios asociado a ese rolAfiliado
            if(inDTO.getIdRolAfiliado()!= null && beneficiarioDTO.getIdRolAfiliado() != null
                    && inDTO.getIdRolAfiliado().equals(beneficiarioDTO.getIdRolAfiliado())) {

                actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, null);
            }
            
        }

        actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);

        // Enviar notificación de rechazo de solicitud al front (en caso de que
        // la caja decida enviar el comunicado, ejecutar el comunicado “003”
        // “Notificación de radicación de solicitud de afiliación de personas” -
        // HU-TRA-331 Editar comunicados).
        // enviarNotificacionRadicacionSolicitudAfiliacion();

    }

    /**
     * Realiza la petición REST al servicio que actualiza el rol afiliado
     * @param rolAfiliadoModeloDTO
     *        Información del rolAfiliado a actualizar
     */
    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
        actualizarRolAfiliado.execute();
    }

    /**
     * Este metodo permite la generacion de un nuevo token para invocacion entre
     * servicios externos
     * 
     * @return token <code>String</code> token generado
     */
    private String generarTokenAccesoCore() {
        GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
        accesoCore.execute();
        TokenDTO token = accesoCore.getResult();
        return token.getToken();
    }

    /**
     * Este método permite consultar una solicitud global por id
     */
    private Solicitud buscarSolicitudPorId(Long idSolicitudAsociacionAfiliadoEntidadPagadora) {
        logger.debug("Inicia buscarSolicitudPorId(Long)");
        Solicitud solGlobal = new Solicitud();
        BuscarSolicitudPorId buscarSolicitudPorId = new BuscarSolicitudPorId(idSolicitudAsociacionAfiliadoEntidadPagadora);
        buscarSolicitudPorId.execute();
        solGlobal = (Solicitud) buscarSolicitudPorId.getResult();
        logger.debug("Finaliza buscarSolicitudPorId(Long)");
        return solGlobal;
    }

    /**
     * Este método permite consultar un empleador por el id del empleador
     */
    private Empleador buscarEmpleador(Long idEmpleador) {
        logger.debug("Inicia buscarEmpleador(Long idEmpleador)");
        Empleador empleador = new Empleador();
        ConsultarEmpleador consultarEmple = new ConsultarEmpleador(idEmpleador);
        consultarEmple.execute();
        empleador = (Empleador) consultarEmple.getResult();
        logger.debug("Finaliza buscarEmpleador(Long idEmpleador)");
        return empleador;
    }

    /**
     * Metodo que se encarga de filtrar las validaciones
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

    /**
     * Método encargado de llamar el cliente que genera el retiro de trabajadores y sus beneficiarios mediante novedades
     * @param rolAfiliadoModeloDTO,
     *        rol Afiliado Modelo DTO
     */
    private void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        logger.debug("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
        EjecutarRetiroTrabajadores ejecucionRetiro = new EjecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
        ejecucionRetiro.execute();
        logger.debug("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
    }
    
    /**
     * Ejecuta el proceso de creacion de la solicitud de asociación de persona en entidad pagadora
     * @param afiliadoInDTO
     *        Informacion Afiliado asociar
     * @param userDTO
     *        Informacion usuario registro
     */
    private void procesarAsociacionEntidadPagadora(AfiliadoInDTO afiliadoInDTO, UserDTO userDTO) {
        Long idSolicitudAsociacionAfiliadoEntidadPagadora = null;
        SolicitudAsociacionPersonaEntidadPagadoraDTO solAsoPerEntPagadoraDTO = null;
        List<SolicitudAsociacionPersonaEntidadPagadoraDTO> lstSolAsoPerEntPagadoraDTO = null;

        ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(afiliadoInDTO.getIdRolAfiliado());
        consultarRolAfiliado.execute();
        RolAfiliadoModeloDTO rolAfiliadoModeloDTO = consultarRolAfiliado.getResult();

        if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(afiliadoInDTO.getTipoAfiliado())
                || TipoAfiliadoEnum.PENSIONADO.equals(afiliadoInDTO.getTipoAfiliado())) {
            /*
             * si se encuentra que existe información en el campo
             * “Entidad pagadora de aportes de pensionado” es
             * necesario que el sistema realice la asociación con la
             * entidad pagadora correspondiente (Ver HU-121-109
             * Gestionar personas en entidad pagadora)
             */
            if (rolAfiliadoModeloDTO != null && rolAfiliadoModeloDTO.getPagadorAportes() != null
                    && rolAfiliadoModeloDTO.getPagadorAportes().getIdEntidadPagadora() != null) {
                idSolicitudAsociacionAfiliadoEntidadPagadora = crearAsociacionAfiliadoEntidadPagadora(afiliadoInDTO);
                if (idSolicitudAsociacionAfiliadoEntidadPagadora != null) {
                    logger.debug(
                            "AfiliacionPersonasCompositeBusiness.procesarAsociacionEntidadPagadora :: genera el numero de radicado correspondiente y lo actualiza en relacion en la solicitud de asociacion del afiliado y la entidad pagadora");
                    // Ggenera el numero de radicado correspondiente y lo actualiza en
                    // relacion en la solicitud de asociacion del afiliado y la entidad pagadora
                    generarNumeroRadicado(idSolicitudAsociacionAfiliadoEntidadPagadora, userDTO.getSedeCajaCompensacion());

                    Solicitud solGlobal = buscarSolicitudPorId(idSolicitudAsociacionAfiliadoEntidadPagadora);

                    solAsoPerEntPagadoraDTO = new SolicitudAsociacionPersonaEntidadPagadoraDTO();
                    solAsoPerEntPagadoraDTO.setEstadoSolicitud(EstadoSolicitudPersonaEntidadPagadoraEnum.PENDIENTE_SOLICITAR_ALTA);
                    solAsoPerEntPagadoraDTO.setNumeroRadicado(solGlobal.getNumeroRadicacion());
                    solAsoPerEntPagadoraDTO.setIdSolicitudGlobal(solGlobal.getIdSolicitud());
                    solAsoPerEntPagadoraDTO.setTipoGestion(TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_ALTA);

                    lstSolAsoPerEntPagadoraDTO = new ArrayList<>();
                    lstSolAsoPerEntPagadoraDTO.add(solAsoPerEntPagadoraDTO);
                    actualizarGestionSolicitudesAsociacion(rolAfiliadoModeloDTO.getPagadorAportes().getIdEntidadPagadora(),
                            lstSolAsoPerEntPagadoraDTO);
                }
            }
            /*
             * Se reconoce aportes y novedades del afiliado, se verifica
             * si se han registrado aportes para el periodo de
             * afiliación y se habilita la prestación de servicios, si
             * aplica. Ver HU-xxx-119 Reconocer aportes y novedades de
             * afiliado
             * 
             * <<TODO 121-119 SE DESARROLLARA SPRINT 5>>
             */
        } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(afiliadoInDTO.getTipoAfiliado())) {
            /*
             * o Se reconoce aportes y novedades del afiliado y se
             * activan los servicios, si aplica HU-xxx-119 Reconocer
             * aportes y novedades de afiliado
             * 
             * <<TODO 121-119 SE DESARROLLARA SPRINT 5>>
             */
        }
    }
    

    /**
     * Método que hace la peticion REST al servicio
     * procesarInactivacionBeneficiario de NovedadesCompositeBusiness
     *
     * @param resultadoBeneficiarioDTO
     * @param estado
     * @param motivoDesafiliacion
     */
    private void registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO, EstadoAfiliadoEnum estado,
            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        logger.debug("Inicia registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO, EstadoAfiliadoEnum,MotivoDesafiliacionBeneficiarioEnum)");
       ;
        
       ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(resultadoBeneficiarioDTO.getIdBeneficiario());
       consultarBeneficiario.execute();
       
        BeneficiarioModeloDTO beneficiarioDto = consultarBeneficiario.getResult();

        SolicitudNovedadDTO inactivacionNovedadDTO = new SolicitudNovedadDTO();

        // Datos del beneficiario a inactivar
        DatosPersonaNovedadDTO personaDTO = new DatosPersonaNovedadDTO();
        
        personaDTO.setMotivoDesafiliacionBeneficiario(motivoDesafiliacion);
        llenarDatosBeneficiarioNovedad(beneficiarioDto, personaDTO);

        inactivacionNovedadDTO.setDatosPersona(personaDTO);
        inactivacionNovedadDTO.setClasificacion(beneficiarioDto.getTipoBeneficiario());
        
        TipoTransaccionEnum tipoTransaccionBeneficiario= 
                obtenerTipoTransaccionBeneficiario(beneficiarioDto.getTipoBeneficiario());
        inactivacionNovedadDTO.setTipoTransaccion(tipoTransaccionBeneficiario);
        inactivacionNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
        RadicarSolicitudNovedadAutomaticaSinValidaciones registrarNovedadAutomatica = new RadicarSolicitudNovedadAutomaticaSinValidaciones(inactivacionNovedadDTO);
        registrarNovedadAutomatica.execute();
        
        logger.debug("Finaliza registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO, EstadoAfiliadoEnum,MotivoDesafiliacionBeneficiarioEnum)");
    }
    

    /**
     * Transforma la información básica del beneficiario para el registro de la novedad por supervivencia
     * @param beneficiarioModeloDTO
     *        Información beneficiario
     * @param persona
     *        Modelo para el registro de novedad
     */
    private void llenarDatosBeneficiarioNovedad(BeneficiarioModeloDTO beneficiarioModeloDTO, DatosPersonaNovedadDTO persona) {
        // Info Beneficiario
        persona.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
        persona.setTipoIdentificacionBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion());
        persona.setNumeroIdentificacionBeneficiario(beneficiarioModeloDTO.getNumeroIdentificacion());
        persona.setTipoIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getTipoIdentificacion());
        persona.setNumeroIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getNumeroIdentificacion());
        persona.setPrimerApellidoBeneficiario(beneficiarioModeloDTO.getPrimerApellido());
        persona.setSegundoApellidoBeneficiario(beneficiarioModeloDTO.getSegundoApellido());
        persona.setPrimerNombreBeneficiario(beneficiarioModeloDTO.getPrimerNombre());
        persona.setSegundoNombreBeneficiario(beneficiarioModeloDTO.getSegundoNombre());
        // Info Afiliado
        persona.setTipoIdentificacion(beneficiarioModeloDTO.getAfiliado().getTipoIdentificacion());
        persona.setNumeroIdentificacion(beneficiarioModeloDTO.getAfiliado().getNumeroIdentificacion());
        persona.setTipoIdentificacionTrabajador(beneficiarioModeloDTO.getAfiliado().getTipoIdentificacion());
        persona.setNumeroIdentificacionTrabajador(beneficiarioModeloDTO.getAfiliado().getNumeroIdentificacion());
    }
    
    
    /**
     * Entrega el tipo de transaccion para inactivacion del beneficario
     * @param clasificacion
     * @return
     */
    private TipoTransaccionEnum obtenerTipoTransaccionBeneficiario(ClasificacionEnum clasificacion) {
        TipoTransaccionEnum tipoTransaccionResult = null;
        switch (clasificacion) {
        case CONYUGE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
            break;
        case PADRE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL;
            break;
        case MADRE:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL;
            break;
        case HIJO_BIOLOGICO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
            break;
        case HIJASTRO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
            break;
        case HERMANO_HUERFANO_DE_PADRES:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL;
            break;
        case HIJO_ADOPTIVO:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL;
            break;
        case BENEFICIARIO_EN_CUSTODIA:
            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL;
            break;
        default:
            break;
        }
        return tipoTransaccionResult;
    }

    @Override
    public void terminarTareaAfiliacionPersonasIntento(Long idTarea, IntentoAfiliacionInDTO intentoAfiliacionInDTO, UserDTO userDTO) {
        logger.debug("Inicia terminarTareaAfiliacionPersonasIntento( idTarea, userDTO)");              
        
        intentoAfiliacionInDTO.setFechaInicioProceso(new Date());
        registrarIntentoAfiliacion(intentoAfiliacionInDTO);
        
        Map<String, Object> params = new HashMap<>();
        params.put("registroIntento", true);
        TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
        terminarTareaService.execute();
        logger.debug("Finaliza terminarTareaAfiliacionPersonas( idTarea, userDTO)");
        
    }

    private List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia el método consultarRolesAfiliado");
        ConsultarRolesAfiliado consultarRolesAfiliado = new ConsultarRolesAfiliado(null, numeroIdentificacion, tipoIdentificacion);
        consultarRolesAfiliado.execute();
        logger.debug("Finaliza el método consultarRolesAfiliado");
        return consultarRolesAfiliado.getResult();
    }

    private Long crearPersona(PersonaModeloDTO personaDTO) {
        logger.info("Inicio de método crearPersona:{}" + personaDTO);
        logger.info("Inicio de método crearPersona:{}" + personaDTO.toString());
        CrearPersona crearPersona = new CrearPersona(personaDTO);
        crearPersona.execute();
        logger.info("Fin de método crearPersona" + crearPersona.getResult());
        return crearPersona.getResult();
    }

    private void registrarPersonasDetalle(List<PersonaDetalleModeloDTO> personasDetalle) {
        logger.info("Inicio personasDetalle:{}" + personasDetalle);
        logger.info("Inicio personasDetalle:{}" + personasDetalle.toString());
        RegistrarPersonasDetalle registrarPersonasDetalle = new RegistrarPersonasDetalle(personasDetalle);
        registrarPersonasDetalle.execute();
        logger.info("regsitra peronsa detalle");
    }

    private GrupoFamiliarDTO crearGrupoFamiliar(Long idAfiliado, GrupoFamiliarDTO grupoFamiliarDTO) {
        logger.info("Inicio idAfiliado&&&" + idAfiliado);
        logger.info("Inicio grupoFamiliarDTO:{}" + grupoFamiliarDTO.toString());
        grupoFamiliarDTO.setAfiliadoAdministradorSubsidio(Boolean.TRUE);
        CrearGrupoFamiliar crearGrupo = new CrearGrupoFamiliar(idAfiliado, grupoFamiliarDTO);
        crearGrupo.execute();
        logger.info("paso grupo familiar:{}" + crearGrupo.getResult());
        return crearGrupo.getResult();
    }

    private void ActualizarBeneficiariomasivamente1(Long idGrupoFamiliar, Long idAfiliado) {

        logger.info("ActualizarBeneficiariomasivamente" + idAfiliado);
        logger.info("idGrupoFamiliar:{}" + idGrupoFamiliar);

        ActualizarBeneficiariomasivamente actualizaBeneficiario = new ActualizarBeneficiariomasivamente(idGrupoFamiliar, idAfiliado);
        actualizaBeneficiario.execute();

        /* List<Beneficiario>  beneficiario = (List<Beneficiario>) entityManager
                                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_EN_BENEFICIARIO)
                                                        .setParameter("idAfiliado", idAfiliado)
                                                        .getResultList();

           /* GrupoFamiliar grupoFamiliar = (GrupoFamiliar) entityManager
                                    .createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR)
                                    .setParameter("idGrupoFamiliar",  Long.parseLong(idGrupoFamiliar))
                    .getSingleResult();

            beneficiario.setGrupoFamiliar(grupoFamiliar);
            entityManager.merge(beneficiario);

           logger.info("beneficiario:{}" + beneficiario.get(0).getIdBeneficiario());*/
        // ActualizarBeneficiariomasivamente actualizaBeneficiario = new ActualizarBeneficiariomasivamente(idGrupoFamiliar,idAfiliado);
        // actualizaBeneficiario.execute();
    }

    private void ActualizarBeneficiariomasivamente(Long idGrupoFamiliar, Long idAfiliado) {
        logger.info("ActualizarBeneficiariomasivamenteF" + idGrupoFamiliar);
        logger.info("ActualizarBeneficiariomasivamenteA" + idAfiliado);
        Afiliado afiliado = new Afiliado();
        afiliado.setIdAfiliado(idAfiliado);
        GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
        grupoFamiliar.setIdGrupoFamiliar(idGrupoFamiliar);
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setAfiliado(afiliado);
        beneficiario.setGrupoFamiliar(grupoFamiliar);

        BeneficiarioDTO beneficiario1 = new BeneficiarioDTO();
        beneficiario1.setIdGrupoFamiliar(idGrupoFamiliar);
        beneficiario1.setIdRolAfiliado(idAfiliado);
        ActualizarBeneficiarioMasivo actualizarBeneficiarioSimple = new ActualizarBeneficiarioMasivo(beneficiario1);
        actualizarBeneficiarioSimple.execute();

    }

    private Long MapsAfiladoTrabajador(AfiliadosMasivosDTO afiliadosMasivosDTO, UserDTO userDTO, ConsolaEstadoCargueProcesoDTO cargue) {
         logger.info("afiliadosMasivosDTO.getIdEmpleador():{}" + afiliadosMasivosDTO.getIdEmpleador());

        logger.info("MapsAfiladoTrabajador1:{}" + afiliadosMasivosDTO);
        PersonaDTO persona = new PersonaDTO();
        PersonaModeloDTO personaModelo = new PersonaModeloDTO();
        logger.info("MapsAfiladoTrabajador41");
        UbicacionDTO UbicacionDTO = new UbicacionDTO();
        logger.info("MapsAfiladoTrabajador42");
        MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
        logger.info("MapsAfiladoTrabajador43");
        Date d = null;
        Date fechainicioContrato = new Date();
        Long fechaNacimento = null;
        Long idUbicacion = null;
        Short idDepartamento = null;
        Short idMunicipio = null;
        Long idSolicitudGlobalTrabajador = null;
        String s3 = null;
        Long date = null;
        logger.info("MapsAfiladoTrabajador44");
        String bloqueValidacion = "121-108-4";
        TipoIdentificacionEnum numeroIdentificacion = null;
        logger.info("MapsAfiladoTrabajador45");
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyyMMdd");

        /// afiliado trabajadors
        logger.info("MapsAfiladoTrabajador2");
        persona.setNumeroIdentificacion(afiliadosMasivosDTO.getNumeroIdentificacionAfiliado());
        logger.info("MapsAfiladoTrabajador3");
        persona.setPrimerNombre(afiliadosMasivosDTO.getPrimerNombre());
        logger.info("MapsAfiladoTrabajador4");
        persona.setSegundoNombre(afiliadosMasivosDTO.getSegundoNombre());
        logger.info("MapsAfiladoTrabajador5");
        persona.setPrimerApellido(afiliadosMasivosDTO.getPrimerApellido());
        logger.info("MapsAfiladoTrabajador6");
        persona.setSegundoApellido(afiliadosMasivosDTO.getSegundoApellido());
        logger.info("MapsAfiladoTrabajador7" + afiliadosMasivosDTO.getFechaDeNacimiento());
        Integer number = Integer.parseInt(afiliadosMasivosDTO.getFechaDeNacimiento());
        try {
            logger.info("MapsAfiladoTrabajador8" + afiliadosMasivosDTO.getFechaDeNacimiento());
            Integer value = number;
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            d = originalFormat.parse(value.toString());

            logger.info("MapsAfiladoTrabajador81" + d);
        } catch (ParseException ex) {
            logger.info("MapsAfiladoTrabajador9");
            Logger.getLogger(AfiliacionPersonasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.info("MapsAfiladoTrabajador10");
        logger.info("MapsAfiladoTrabajador11");
        fechaNacimento = d.getTime();
        logger.info("MapsAfiladoTrabajador12");
        persona.setFechaNacimiento(fechaNacimento);
        logger.info("MapsAfiladoTrabajador13");
        persona.setTipoIdentificacion(afiliadosMasivosDTO.getTipoIdentificacionAfiliado());
        logger.info("MapsAfiladoTrabajador14");
        persona.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        persona.setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
        persona.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
        logger.info("MapsAfiladoTrabajador15");
        persona.setEstadoCivil(afiliadosMasivosDTO.getEstadoCivil());
        persona.setGenero(afiliadosMasivosDTO.getGenero());
        logger.info("MapsAfiladoTrabajador16");
        persona.setNivelEducativo(afiliadosMasivosDTO.getNivelEscolaridad());
        logger.info("MapsAfiladoTrabajador17");
        personaModelo.setTipoIdentificacion(afiliadosMasivosDTO.getTipoIdentificacionAfiliado());
        personaModelo.setNumeroIdentificacion(afiliadosMasivosDTO.getNumeroIdentificacionAfiliado());
        medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
        medioDePagoModeloDTO.setEfectivo(Boolean.TRUE);
        medioDePagoModeloDTO.setPersona(personaModelo);
        //medioDePagoModeloDTO.setTarjetaMultiservicio(Boolean.FALSE);
        ConsultarIdSitioPagoPredeterminado consultarIdSitioPagoPredeterminado = new ConsultarIdSitioPagoPredeterminado();
        consultarIdSitioPagoPredeterminado.execute();
        Long idSitioDePagoPredeterminado = consultarIdSitioPagoPredeterminado.getResult();
        medioDePagoModeloDTO.setSitioPago(idSitioDePagoPredeterminado);
        persona.setMedioDePagoPersona(medioDePagoModeloDTO);
        logger.info("MapsAfiladoTrabajador18");
        persona.setResideSectorRural(afiliadosMasivosDTO.getResideRural());
        persona.setFactorVulnerabilidad(afiliadosMasivosDTO.getFactorVulnerabilidad());
        persona.setIdOcupacionProfesion(Integer.parseInt(afiliadosMasivosDTO.getCodigoOcupacion()));
        persona.setPertenenciaEtnica(afiliadosMasivosDTO.getPertenenciaEtnica());
        persona.setIdPaisResidencia(Long.parseLong(afiliadosMasivosDTO.getNombrePaisResidencia()));
        persona.setAutorizaUsoDatosPersonales(Boolean.FALSE);
        persona.setCabezaHogar(Boolean.FALSE);
        
        UbicacionDTO.setIdUbicacion(3L);
        idMunicipio = afiliadosMasivosDTO.getCodigoMucipio().shortValue();
        UbicacionDTO.setIdMunicipio(idMunicipio);
        UbicacionDTO.setTelefonoCelular(afiliadosMasivosDTO.getTelefonoCelular());
        UbicacionDTO.setTelefonoFijo(afiliadosMasivosDTO.getTelefonoFijo());
        UbicacionDTO.setTipoUbicacion(TipoUbicacionEnum.UBICACION_PRINCIPAL);
        UbicacionDTO.setDireccion(afiliadosMasivosDTO.getDireccion());
        UbicacionDTO.setCorreoElectronico(afiliadosMasivosDTO.getCorreoElectronico());
        UbicacionDTO.setAutorizacionEnvioEmail(Boolean.FALSE);
        logger.info("municipio "+idMunicipio);
        ConsultarDepartamentoPorIdMunicipio consultarDepartamentoPorIdMunicipio = new ConsultarDepartamentoPorIdMunicipio(idMunicipio);
        consultarDepartamentoPorIdMunicipio.execute();
        Departamento departamento = consultarDepartamentoPorIdMunicipio.getResult();
        logger.info("indicativo "+departamento.getIndicativoTelefoniaFija());
        UbicacionDTO.setIndicativoTelefonoFijo(departamento != null ? departamento.getIndicativoTelefoniaFija(): null);
        persona.setUbicacionDTO(UbicacionDTO);
        persona.setOrientacionSexual(afiliadosMasivosDTO.getOrientacionSexual());
        logger.info("MapsAfiladoTrabajador19");
        AfiliadoInDTO afiliadoInDTO = new AfiliadoInDTO();
        ConsultarEmpleador consultarEmpleador = new ConsultarEmpleador(afiliadosMasivosDTO.getIdEmpleador());
        consultarEmpleador.execute();
        Empleador empleador = consultarEmpleador.getResult();
        ConsultarSucursalesEmpresa consultarSucursalesEmpresa = new ConsultarSucursalesEmpresa(empleador.getEmpresa().getIdEmpresa());
        consultarSucursalesEmpresa.execute();
        List<SucursalEmpresa> sucursales = consultarSucursalesEmpresa.getResult();
        for(SucursalEmpresa sucursal: sucursales){
            if(sucursal.getSucursalPrincipal().equals(Boolean.TRUE)){
                afiliadoInDTO.setSucursalEmpleadorId(sucursal.getIdSucursalEmpresa());
            }
        }
        logger.info("MapsAfiladoTrabajador20");
        afiliadoInDTO.setPersona(persona);
        afiliadoInDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        logger.info("MapsAfiladoTrabajador21///" + afiliadosMasivosDTO.getIdEmpleador());
        afiliadoInDTO.setIdEmpleador(afiliadosMasivosDTO.getIdEmpleador());
        afiliadoInDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        afiliadoInDTO.setFechaInicioContrato(fechainicioContrato);
        afiliadoInDTO.setFechaAfiliacion(fechainicioContrato);
        afiliadoInDTO.setValorSalarioMesada(afiliadosMasivosDTO.getSalarioMensual());
        afiliadoInDTO.setClaseTrabajador(afiliadosMasivosDTO.getClaseTrabajador());
        afiliadoInDTO.setIdMunicipioDesempenioLabores(afiliadosMasivosDTO.getCodigoMunicipioLabor().shortValue());
        logger.info("MapsAfiladoTrabajador22" + afiliadoInDTO);
        logger.info("MapsAfiladoTrabajador22" + afiliadoInDTO.toString());

        Map<String, String> datosValidacion = new HashMap<>();
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", afiliadosMasivosDTO.getTipoIdentificacionAfiliado().name());
        datosValidacion.put("numeroIdentificacion", afiliadosMasivosDTO.getNumeroIdentificacionAfiliado());
        datosValidacion.put("tipoIdentificacionAfiliado", afiliadosMasivosDTO.getTipoIdentificacionAfiliado().name());
        datosValidacion.put("numeroIdentificacionAfiliado", afiliadosMasivosDTO.getNumeroIdentificacionAfiliado());
		datosValidacion.put("tipoIdentificacionEmpleador",empleador.getEmpresa().getPersona().getTipoIdentificacion().name());
		datosValidacion.put("numeroIdentificacionEmpleador",empleador.getEmpresa().getPersona().getNumeroIdentificacion());
		datosValidacion.put("idEmpleador",afiliadosMasivosDTO.getIdEmpleador().toString());

		datosValidacion.put("tipoAfiliado", "TRABAJADOR_DEPENDIENTE");

        ValidarReglasNegocio validador = new ValidarReglasNegocio("121-104-5", bloque.getProceso(),
                "TRABAJADOR_DEPENDIENTE", datosValidacion);
        validador.execute();

        List<ValidacionDTO> list = validador.getResult();
            
            if(CollectionUtils.isNotEmpty(list)){
                List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                        equals(ResultadoValidacionEnum.NO_APROBADA) && iteValidacion.getTipoExcepcion().equals(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2)).
                        collect(Collectors.toList());
                
                if(CollectionUtils.isEmpty(resultadoFilterValidacionList)){
                    Map<String, Object> afiliado = afilarPersonaMasivamente(afiliadoInDTO, userDTO, "");
                    afiliado.get("idSolicitudGlobal");
                    logger.info("MapsAfiladoTrabajador23");
                    logger.info("MapsAfiladoTrabajador23" + afiliado.get("idSolicitudGlobal"));
                    idSolicitudGlobalTrabajador = Long.valueOf(afiliado.get("idSolicitudGlobal").toString());


                    InformacionLaboralTrabajadorDTO infoLaboralTrabajador = new InformacionLaboralTrabajadorDTO();

                    infoLaboralTrabajador.setValorSalario(afiliadosMasivosDTO.getSalarioMensual());
                    infoLaboralTrabajador.setClaseTrabajador(afiliadosMasivosDTO.getClaseTrabajador());
                    for(SucursalEmpresa sucursal: sucursales){
                        if(sucursal.getSucursalPrincipal().equals(Boolean.TRUE)){
                            infoLaboralTrabajador.setIdSucursalEmpleador(sucursal.getIdSucursalEmpresa());
                        }
                    }
                    infoLaboralTrabajador.setFechaInicioContrato(fechainicioContrato);

                    GuardarTemporalAfiliacionPersona guardarTemporalAfiliacion = new GuardarTemporalAfiliacionPersona();
                    guardarTemporalAfiliacion.setInfoLaboral(infoLaboralTrabajador);
                    try{
                        ObjectMapper mapper = new ObjectMapper();
                        String jsonPayload = mapper.writeValueAsString(guardarTemporalAfiliacion);
                        GuardarDatosTemporales guardar = new GuardarDatosTemporales(Long.valueOf(afiliado.get("idSolicitudGlobal").toString()), jsonPayload);
                        guardar.execute();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else{
                    List<String> mensajesErrores = resultadoFilterValidacionList.stream().map(ValidacionDTO::getDetalle).collect(Collectors.toList());                         
                    agregarInconsistenciasHallazgosValidacion(cargue, mensajesErrores, afiliadosMasivosDTO.getNoLinea());
                    }

                }


        return idSolicitudGlobalTrabajador;
        

        /// fin afiliado trabajador
    }

    private void MapsBeneficiarioHijoPadre(AfiliadosACargoMasivosDTO afiliadosACargoMasivosDTO, Long idSolicitudTrabajador, UserDTO userDTO, ConsolaEstadoCargueProcesoDTO cargue) {
        logger.info("Entra metodo MapsBeneficiarioHijoPadre:--" + afiliadosACargoMasivosDTO);
        logger.info("Entra metodo MapsBeneficiarioHijoPadre:--" + afiliadosACargoMasivosDTO.toString());
        logger.info("MapsBeneficiarioHijoPadre10:--" + afiliadosACargoMasivosDTO.getFechaDeNacimiento());
        PersonaDTO persona = new PersonaDTO();
        logger.info("MapsBeneficiarioHijoPadre11:--" + afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado());
        UbicacionDTO UbicacionDTO = new UbicacionDTO();
        MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
        String telefono = null;
        Date d = null;
        Long fechaNacimento = null;
        Long idUbicacion = null;
        Short idDepartamento = null;
        Short idMunicipio = null;
        String s3 = null;
        Long date = null;
        logger.info("MapsBeneficiarioHijoPadre12:--");
        String bloqueValidacion = "121-108-4";
        TipoIdentificacionEnum numeroIdentificacion = null;
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyyMMdd");

        ////Beneficiario HijoPadre
        AfiliarBeneficiarioDTO afiliarBeneficiarioDTO = new AfiliarBeneficiarioDTO();
        afiliarBeneficiarioDTO.setNumeroIdentificacionAfiliado(afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
        afiliarBeneficiarioDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);


        if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_BIOLOGICO") || afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_ADOPTIVO")
            || afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJASTRO")|| afiliadosACargoMasivosDTO.getParentezco().toString().equals("HERMANO_HUERFANO_DE_PADRES")
            || afiliadosACargoMasivosDTO.getParentezco().toString().equals("BENEFICIARIO_EN_CUSTODIA")){
          afiliarBeneficiarioDTO.setTipoBeneficiario(TipoBeneficiarioEnum.HIJO);
        }

        if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("PADRE")|| afiliadosACargoMasivosDTO.getParentezco().toString().equals("MADRE") ){
           afiliarBeneficiarioDTO.setTipoBeneficiario(TipoBeneficiarioEnum.PADRES);
        }
        afiliarBeneficiarioDTO.setResultadoGeneralValidacion(ResultadoGeneralValidacionEnum.AFILIABLE);
        LocalDateTime datetime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(datetime, ZoneId.systemDefault());
        date = zdt.toInstant().toEpochMilli();
        afiliarBeneficiarioDTO.setFechaRecepcionDocumento(date);
        afiliarBeneficiarioDTO.setBloqueValidacion(bloqueValidacion);
        BeneficiarioHijoPadreDTO beneficiarioHijoPadre = new BeneficiarioHijoPadreDTO();
        persona.setPrimerNombre(afiliadosACargoMasivosDTO.getPrimerNombre());
        persona.setSegundoNombre(afiliadosACargoMasivosDTO.getSegundoNombre());
        persona.setPrimerApellido(afiliadosACargoMasivosDTO.getPrimerApellido());
        persona.setSegundoApellido(afiliadosACargoMasivosDTO.getSegundoApellido());
        int number = Integer.parseInt(afiliadosACargoMasivosDTO.getFechaDeNacimiento());

        try {
            logger.info("antes de parseo fecha:--" + afiliadosACargoMasivosDTO.getFechaDeNacimiento());
            Integer value = number;
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            d = originalFormat.parse(value.toString());

            logger.info("despues de parseo fecha91:--" + d);
        } catch (ParseException ex) {
            Logger.getLogger(AfiliacionPersonasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.info("MapsBeneficiarioHijoPadre11:--");
        logger.info("MapsBeneficiarioHijoPadre12:--");
        fechaNacimento = d.getTime();
        logger.info("MapsBeneficiarioHijoPadre13:--");
        persona.setFechaNacimiento(fechaNacimento);
        logger.info("MapsBeneficiarioHijoPadre14:--" + afiliadosACargoMasivosDTO.getTipoIdentificacionPersonaCargo());
        logger.info("MapsBeneficiarioHijoPadre1445:--" + afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado());
        persona.setTipoIdentificacion(afiliadosACargoMasivosDTO.getTipoIdentificacionPersonaCargo());
        logger.info("MapsBeneficiarioHijoPadre15:--" + afiliadosACargoMasivosDTO.getNumeroIdentificacionPersonaCargo());
        persona.setNumeroIdentificacion(afiliadosACargoMasivosDTO.getNumeroIdentificacionPersonaCargo());
        persona.setGenero(afiliadosACargoMasivosDTO.getGenero());
        logger.info("afiliadosACargoMasivosDTO.getParentezco():--" + afiliadosACargoMasivosDTO.getParentezco());
        persona.setNivelEducativo(NivelEducativoEnum.NINGUNO);
        
       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_BIOLOGICO")){
         persona.setClasificacion(ClasificacionEnum.HIJO_BIOLOGICO);
       }
       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("PADRE")){
         persona.setClasificacion(ClasificacionEnum.PADRE);
       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("MADRE")){
         persona.setClasificacion(ClasificacionEnum.MADRE);
       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_ADOPTIVO")){
         persona.setClasificacion(ClasificacionEnum.HIJO_ADOPTIVO);
       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJASTRO")){
         persona.setClasificacion(ClasificacionEnum.HIJASTRO);
       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HERMANO_HUERFANO_DE_PADRES")){
         persona.setClasificacion(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
       }

        if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("BENEFICIARIO_EN_CUSTODIA")){
         persona.setClasificacion(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
       }

        beneficiarioHijoPadre.setPersona(persona);
        if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_BIOLOGICO")){
          beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.HIJO_BIOLOGICO);
          bloqueValidacion = "121-108-4";

       }
       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("PADRE")){
          beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.PADRE);
          bloqueValidacion = "121-108-5";

       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("MADRE")){
          beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.MADRE);
          bloqueValidacion = "121-108-5";

       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJO_ADOPTIVO")){
          beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.HIJO_ADOPTIVO);
        bloqueValidacion = "121-108-4";

       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HIJASTRO")){
          beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.HIJASTRO);
          bloqueValidacion = "121-108-4";

       }

       if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("HERMANO_HUERFANO_DE_PADRES")){
         beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
         bloqueValidacion = "121-108-4";

       }

        if(afiliadosACargoMasivosDTO.getParentezco().toString().equals("BENEFICIARIO_EN_CUSTODIA")){
         beneficiarioHijoPadre.setTipoBeneficiario(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        bloqueValidacion = "121-108-4";

       }

        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setTelefonoCelular(afiliadosACargoMasivosDTO.getTelefonoCelular());
        ubicacionDTO.setTelefonoFijo(afiliadosACargoMasivosDTO.getTelefonoFijo());
        ubicacionDTO.setDireccion(afiliadosACargoMasivosDTO.getDireccion());
        ubicacionDTO.setIdMunicipio(Short.parseShort(afiliadosACargoMasivosDTO.getCodigoMunicipio()));
        ConsultarDepartamentoPorIdMunicipio consultarDepartamentoPorIdMunicipio = new ConsultarDepartamentoPorIdMunicipio(Short.parseShort(afiliadosACargoMasivosDTO.getCodigoMunicipio()));
        consultarDepartamentoPorIdMunicipio.execute();
        Departamento departamento = consultarDepartamentoPorIdMunicipio.getResult();
        ubicacionDTO.setIndicativoTelefonoFijo(departamento != null ? departamento.getIndicativoTelefoniaFija(): null);
        beneficiarioHijoPadre.setUbicacion(ubicacionDTO);
        ObtenerGruposFamiliaresAfiPrincipal obtenerGruposFamiliaresAfiPrincipal = new ObtenerGruposFamiliaresAfiPrincipal(afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado().name(), afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
        obtenerGruposFamiliaresAfiPrincipal.execute();
        List<PersonaComoAfiPpalGrupoFamiliarDTO> listaGrupos = obtenerGruposFamiliaresAfiPrincipal.getResult();
        logger.info("Lista de grupos familiares obtenidos: " + listaGrupos);

        Long idGrupoFam = null;
        logger.info("Variable idGrupoFam inicializada en null");

        for (PersonaComoAfiPpalGrupoFamiliarDTO grupo: listaGrupos){
            logger.info("Iterando grupo con ID: " + grupo.getIdGrupoFamiliar());

             if (grupo.getBeneficiarios() != null && !grupo.getBeneficiarios().isEmpty()){
                  logger.info("Beneficiarios encontrados para el grupo con ID: " + grupo.getIdGrupoFamiliar() +
            ". Beneficiarios: " + grupo.getBeneficiarios());
            for (BeneficiarioGrupoFamiliarDTO ben : grupo.getBeneficiarios()) {
                logger.info("Iterando beneficiario con tipo: " + ben.getTipoBeneficiario());
                if ((beneficiarioHijoPadre.getTipoBeneficiario().equals("HIJO_BIOLOGICO") || 
                 beneficiarioHijoPadre.getTipoBeneficiario().equals("HIJO_ADOPTIVO") || 
                 beneficiarioHijoPadre.getTipoBeneficiario().equals("HIJASTRO") || 
                 beneficiarioHijoPadre.getTipoBeneficiario().equals("BENEFICIARIO_EN_CUSTODIA")) && 
                (ben.getParentezco().equals("CONYUGE") || 
                 ben.getParentezco().equals("HIJO_BIOLOGICO") || 
                 ben.getParentezco().equals("HIJASTRO") || 
                 ben.getParentezco().equals("HIJO_ADOPTIVO") || 
                 ben.getParentezco().equals("BENEFICIARIO_EN_CUSTODIA"))) {
                idGrupoFam = grupo.getIdGrupoFamiliar();
                logger.info("ID del grupo familiar asignado a idGrupoFam: " + idGrupoFam);

            }
             // TODO 09.2.2 : Si beneficiarioHijoPadre.getTipoBeneficiario().name() es PARE O MADRE (validar con equals) y ben.getTipoBeneficiario() es PADRE O MADRE  entonces asignar a la variable idGrupoFam el valor de grupo.getIdGrupoFamiliar()

            if ((beneficiarioHijoPadre.getTipoBeneficiario().equals("PADRE") || 
                 beneficiarioHijoPadre.getTipoBeneficiario().equals("MADRE")) && 
                (ben.getParentezco().equals("PADRE") || 
                 ben.getParentezco().equals("MADRE"))) {
                idGrupoFam = grupo.getIdGrupoFamiliar();
                logger.info("ID del grupo familiar asignado a idGrupoFam: " + idGrupoFam);

            }
        // TODO 09.2.3 : Si beneficiarioHijoPadre.getTipoBeneficiario().name() es HERMANO_HUERFANO_DE_PADRES (validar con equals) y ben.getTipoBeneficiario() es HERMANO_HUERFANO_DE_PADRES  entonces asignar a la variable idGrupoFam el valor de grupo.getIdGrupoFamiliar()
            if ((beneficiarioHijoPadre.getTipoBeneficiario().equals("HERMANO_HUERFANO_DE_PADRES")) && 
                (ben.getTipoBeneficiario().equals("HERMANO_HUERFANO_DE_PADRES "))) {
                idGrupoFam = grupo.getIdGrupoFamiliar();
                logger.info("ID del grupo familiar asignado a idGrupoFam: " + idGrupoFam);

            }
            }
             
        }
    }
        // TODO 10: despues del for del todo 04 beneficiarioHijoPadre.setIdGrupoFamiliar(idGrupoFam)
        beneficiarioHijoPadre.setIdGrupoFamiliar(idGrupoFam);
        logger.info("ID de grupo familiar asignado al beneficiarioHijoPadre: " + idGrupoFam);


        // TODO 11: Revisar imports
        afiliarBeneficiarioDTO.setBeneficiarioHijoPadre(beneficiarioHijoPadre);
        logger.info("MapsBeneficiarioHijoPadre16:--");
        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacionAfiliado", afiliadosACargoMasivosDTO.getTipoIdentificacionPersonaCargo().name());
        datosValidacion.put("numeroIdentificacion", afiliadosACargoMasivosDTO.getNumeroIdentificacionPersonaCargo());
        datosValidacion.put("tipoIdentificacion",afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado().name());
        datosValidacion.put("numeroIdentificacionAfiliado", afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
        datosValidacion.put("tipoBeneficiario", beneficiarioHijoPadre.getTipoBeneficiario().name());
        try {
            
            ValidarPersonas validarPersona = new ValidarPersonas(bloqueValidacion, ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        beneficiarioHijoPadre.getTipoBeneficiario().name(), datosValidacion);
            validarPersona.execute();
            List<ValidacionDTO> list = validarPersona.getResult();
            
            if(CollectionUtils.isNotEmpty(list)){
                List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                        equals(ResultadoValidacionEnum.NO_APROBADA) && iteValidacion.getTipoExcepcion().equals(TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2)).
                        collect(Collectors.toList());
                
                if(CollectionUtils.isEmpty(resultadoFilterValidacionList)){
                    afiliarBeneficiarioMasivamente(afiliarBeneficiarioDTO, idSolicitudTrabajador, afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado(), userDTO, afiliadosACargoMasivosDTO);

                }else{
                    List<String> mensajesErrores = resultadoFilterValidacionList.stream().map(ValidacionDTO::getDetalle).collect(Collectors.toList());                         
                    agregarInconsistenciasHallazgosValidacion(cargue, mensajesErrores, afiliadosACargoMasivosDTO.getNoLinea());
                    }

                } else {
                    afiliarBeneficiarioMasivamente(afiliarBeneficiarioDTO, idSolicitudTrabajador, afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado(), userDTO, afiliadosACargoMasivosDTO);
                }

            
        } catch (Exception e) {
            logger.error(e.getMessage()+" Cause: "+e.getCause());
        }

    }

    private void MapsBeneficiarioConyugue(AfiliadosACargoMasivosDTO afiliadosACargoMasivosDTO, Long idSolicitudTrabajador, UserDTO userDTO, ConsolaEstadoCargueProcesoDTO cargue) {
        logger.info("Entra metodo MapsBeneficiarioConyugue:--");
        PersonaDTO persona = new PersonaDTO();
        UbicacionDTO UbicacionDTO = new UbicacionDTO();
        MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
        String telefono = null;
        Date d = null;
        Long fechaNacimento = null;
        Long idUbicacion = null;
        Short idDepartamento = null;
        Short idMunicipio = null;
        String s3 = null;
        Long date = null;
        logger.info("MapsBeneficiarioConyugue10:--");
        String bloqueValidacion = "121-108-1";
        TipoIdentificacionEnum numeroIdentificacion = null;
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyyMMdd");

        logger.info("MapsBeneficiarioConyugue11:--");
        AfiliarBeneficiarioDTO afiliarBeneficiarioDTO = new AfiliarBeneficiarioDTO();
        afiliarBeneficiarioDTO.setNumeroIdentificacionAfiliado(afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
        afiliarBeneficiarioDTO.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        afiliarBeneficiarioDTO.setTipoBeneficiario(TipoBeneficiarioEnum.CONYUGE);
        afiliarBeneficiarioDTO.setResultadoGeneralValidacion(ResultadoGeneralValidacionEnum.AFILIABLE);
        LocalDateTime datetime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(datetime, ZoneId.systemDefault());
        date = zdt.toInstant().toEpochMilli();
        afiliarBeneficiarioDTO.setFechaRecepcionDocumento(date);
        afiliarBeneficiarioDTO.setBloqueValidacion(bloqueValidacion);
        logger.info("MapsBeneficiarioConyugue12:--");
        IdentificacionUbicacionPersonaDTO beneficiarioConyugue = new IdentificacionUbicacionPersonaDTO();
        persona.setPrimerNombre(afiliadosACargoMasivosDTO.getPrimerNombre());
        persona.setSegundoNombre(afiliadosACargoMasivosDTO.getSegundoNombre());
        persona.setPrimerApellido(afiliadosACargoMasivosDTO.getPrimerApellido());
        persona.setSegundoApellido(afiliadosACargoMasivosDTO.getSegundoApellido());
        Integer number = Integer.parseInt(afiliadosACargoMasivosDTO.getFechaDeNacimiento());
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            logger.info("MapsBeneficiarioConyugue13:--" + afiliadosACargoMasivosDTO.getFechaDeNacimiento());
                Integer value = number;
            d = originalFormat.parse(value.toString());
            logger.info("MapsBeneficiarioConyugue14:--" + d);
        } catch (ParseException ex) {
            Logger.getLogger(AfiliacionPersonasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
        fechaNacimento = d.getTime();
        persona.setFechaNacimiento(fechaNacimento);
        logger.info("MapsBeneficiarioConyugue15:--");
        persona.setTipoIdentificacion(afiliadosACargoMasivosDTO.getTipoIdentificacionPersonaCargo());
        logger.info("MapsBeneficiarioConyugue16:--");
        persona.setNumeroIdentificacion(afiliadosACargoMasivosDTO.getNumeroIdentificacionPersonaCargo());
        persona.setGenero(afiliadosACargoMasivosDTO.getGenero());
        persona.setNivelEducativo(NivelEducativoEnum.NINGUNO);
        persona.setClasificacion(ClasificacionEnum.CONYUGE);
        beneficiarioConyugue.setPersona(persona);
        beneficiarioConyugue.setTipoBeneficiario(ClasificacionEnum.CONYUGE);
        beneficiarioConyugue.setLabora(afiliadosACargoMasivosDTO.getConyugeLabora());
        beneficiarioConyugue.setSalarioMensualBeneficiario(afiliadosACargoMasivosDTO.getIngresoMensual());
        Integer fechaInicioSociedadConyugal = Integer.parseInt(afiliadosACargoMasivosDTO.getFechaInicioUnionAfiliado());
         try {
            Integer value = fechaInicioSociedadConyugal;
            d = originalFormat.parse(value.toString());
        } catch (ParseException ex) {
            Logger.getLogger(AfiliacionPersonasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
        beneficiarioConyugue.setFechaInicioSociedadConyugal(d);
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setTelefonoCelular(afiliadosACargoMasivosDTO.getTelefonoCelular());
        ubicacionDTO.setTelefonoFijo(afiliadosACargoMasivosDTO.getTelefonoFijo());
        ubicacionDTO.setDireccion(afiliadosACargoMasivosDTO.getDireccion());
        ubicacionDTO.setIdMunicipio(Short.parseShort(afiliadosACargoMasivosDTO.getCodigoMunicipio()));
        logger.info("municipio "+afiliadosACargoMasivosDTO.getCodigoMunicipio());
        ConsultarDepartamentoPorIdMunicipio consultarDepartamentoPorIdMunicipio = new ConsultarDepartamentoPorIdMunicipio(Short.parseShort(afiliadosACargoMasivosDTO.getCodigoMunicipio()));
        consultarDepartamentoPorIdMunicipio.execute();
        Departamento departamento = consultarDepartamentoPorIdMunicipio.getResult();
        logger.info("indicativo "+departamento.getIndicativoTelefoniaFija());
        ubicacionDTO.setIndicativoTelefonoFijo(departamento != null ? departamento.getIndicativoTelefoniaFija(): null);
        beneficiarioConyugue.setUbicacion(ubicacionDTO);
        
          // Todo 01: Invocar servicio ObtenerGruposFamiliaresAfiPrincipal (afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado(), afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado())
          ObtenerGruposFamiliaresAfiPrincipal obtenerGruposFamiliaresAfiPrincipal = new ObtenerGruposFamiliaresAfiPrincipal(afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado().name(), afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
          logger.info("Servicio ObtenerGruposFamiliaresAfiPrincipal inicializado con tipo de identificación: " + afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado().name() + " y número de identificación: " + afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
          obtenerGruposFamiliaresAfiPrincipal.execute(); 
          logger.info("Servicio ObtenerGruposFamiliaresAfiPrincipal ejecutado");

          // Todo 02: Guardar resultado servicio en variable listaGrupos de tipo List<PersonaComoAfiPpalGrupoFamiliarDTO> 
          List<PersonaComoAfiPpalGrupoFamiliarDTO> listaGrupos = obtenerGruposFamiliaresAfiPrincipal.getResult();
          logger.info("Lista de grupos familiares obtenidos: " + listaGrupos);
          // Todo 03: crear variable tipo Long llamada idGrupoFam e igualarla a null
          Long idGrupoFam = null;
          logger.info("Variable idGrupoFam inicializada en null");
          // TOdo 04: iterar listaGrupos for (PersonaComoAfiPpalGrupoFamiliarDTO grupo: listaGrupos)
          for (PersonaComoAfiPpalGrupoFamiliarDTO grupo : listaGrupos) {
            logger.info("Iterando grupo familiar con ID: " + grupo.getIdGrupoFamiliar());

              // TODO 04.1: validar con if si grupo.getBeneficiarios no es null y  no es vacio (isEmpty)
              if (grupo.getBeneficiarios() != null && !grupo.getBeneficiarios().isEmpty()){
                logger.info("Beneficiarios encontrados para el grupo con ID: " + grupo.getIdGrupoFamiliar() + ". Beneficiarios: " + grupo.getBeneficiarios());
                  for (BeneficiarioGrupoFamiliarDTO ben : grupo.getBeneficiarios()) {
                    logger.info("Iterando beneficiario con tipo: " + ben.getParentezco());

              
              // TODO 04.2.1 : Si ben.getTipoBeneficiario() es CONYUGE o HIJO_BIOLOGICO o HIJASTRO o HIJO_ADOPTIVO o BENEFICIARIO_EN_CUSTODIA entonces asignar a la variable idGrupoFam el valor de grupo.getIdGrupoFamiliar()
                      if (ClasificacionEnum.CONYUGE.name().equals(ben.getParentezco().name()) ||
                          ClasificacionEnum.HIJO_BIOLOGICO.name().equals(ben.getParentezco().name()) ||
                          ClasificacionEnum.HIJASTRO.name().equals(ben.getParentezco().name()) ||
                          ClasificacionEnum.HIJO_ADOPTIVO.name().equals(ben.getParentezco().name()) ||
                          ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA.name().equals(ben.getParentezco().name())) {
                              
                          idGrupoFam = grupo.getIdGrupoFamiliar();
                          logger.info("ID del grupo familiar asignado a idGrupoFam: " + idGrupoFam);

            }
          }
        }
    }
        // TODO 05: despues del for del todo 04 afiliarBeneficiarioDTO.setIdGrupoFamiliar(idGrupoFam)
        beneficiarioConyugue.setIdGrupoFamiliar(idGrupoFam);
        logger.info("ID de grupo familiar asignado al beneficiario: " + idGrupoFam);

        afiliarBeneficiarioDTO.setBeneficiarioConyuge(beneficiarioConyugue);
        
        logger.info("MapsBeneficiarioConyugue17:--");
        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoIdentificacionAfiliado", afiliadosACargoMasivosDTO.getTipoIdentificacionPersonaCargo().name());
        datosValidacion.put("numeroIdentificacion", afiliadosACargoMasivosDTO.getNumeroIdentificacionPersonaCargo());
        datosValidacion.put("tipoIdentificacion",afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado().name());
        datosValidacion.put("numeroIdentificacionAfiliado", afiliadosACargoMasivosDTO.getNumeroIdentificacionAfiliado());
        datosValidacion.put("tipoBeneficiario", ClasificacionEnum.CONYUGE.name());
        try {
            
            ValidarPersonas validarPersona = new ValidarPersonas(bloqueValidacion, ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
                        ClasificacionEnum.CONYUGE.name(), datosValidacion);
            validarPersona.execute();
            List<ValidacionDTO> list = validarPersona.getResult();
            
            if(CollectionUtils.isNotEmpty(list)){
                List<ValidacionDTO> resultadoFilterValidacionList = list.stream().filter(iteValidacion -> iteValidacion.getResultado().
                        equals(ResultadoValidacionEnum.NO_APROBADA)).
                        collect(Collectors.toList());
                
                if(CollectionUtils.isEmpty(resultadoFilterValidacionList)){
                    afiliarBeneficiarioMasivamente(afiliarBeneficiarioDTO, idSolicitudTrabajador, afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado(), userDTO,afiliadosACargoMasivosDTO );

                }else{
                    List<String> mensajesErrores = resultadoFilterValidacionList.stream().map(ValidacionDTO::getDetalle).collect(Collectors.toList());                         
                    agregarInconsistenciasHallazgosValidacion(cargue, mensajesErrores, afiliadosACargoMasivosDTO.getNoLinea());

                }

                

            } else {
                afiliarBeneficiarioMasivamente(afiliarBeneficiarioDTO, idSolicitudTrabajador, afiliadosACargoMasivosDTO.getTipoIdentificacionAfiliado(), userDTO,afiliadosACargoMasivosDTO );

            }
        } catch (Exception e) {
            logger.error(e.getMessage()+" Cause: "+e.getCause());
            logger.error("Error",e);
        }
        logger.info("MapsBeneficiarioConyugue18:--");

    }

    private InformacionArchivoDTO guardarObtenerComunicadoECM(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla, ParametrosComunicadoDTO parametroComunicadoDTO) {

        logger.info("tipoTransaccion" + tipoTransaccion);
        logger.info("plantilla:{}" + plantilla);
        logger.info("parametroComunicadoDTO:{ }" + parametroComunicadoDTO.toString());
        parametroComunicadoDTO.getIdsSolicitud();
        parametroComunicadoDTO.getIdPlantillaComunicado();

        GuardarObtenerComunicadoECM guardarObtenerComunicadoECM = new GuardarObtenerComunicadoECM(tipoTransaccion, plantilla, parametroComunicadoDTO);
        guardarObtenerComunicadoECM.execute();

        return guardarObtenerComunicadoECM.getResult();
    }

    private Long crearComunicado(Comunicado comunicado) {
        logger.info("crearComunicado9");
        Long idComunicado = null;
        logger.info("crearComunicado91:{ }" + comunicado.toString());
        logger.info("crearComunicado91:{ }" + comunicado);
        CrearComunicado crearComunicado = new CrearComunicado(comunicado);
        crearComunicado.execute();
        idComunicado = crearComunicado.getResult();
        logger.info("idComunicado^^" + idComunicado);

        return idComunicado;

    }

    private Map<String, Object> resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, Long idSolicitud, Long idInstanciaProceso) {

        Map<String, Object> valoresVariable = new HashMap<String, Object>();
        logger.info("etiquetaPlantillaComunicadoEnum101//" + etiquetaPlantillaComunicadoEnum);
        logger.info("idSolicitud:***" + idSolicitud);
        logger.info("idInstanciaProceso:***" + idInstanciaProceso);

        ResolverVariablesComunicado resolverVariablesComunicado = new ResolverVariablesComunicado(etiquetaPlantillaComunicadoEnum, idSolicitud, idInstanciaProceso, null);
        resolverVariablesComunicado.execute();
        logger.info("valoresVariable" + resolverVariablesComunicado.getResult());
        valoresVariable = resolverVariablesComunicado.getResult();

        return valoresVariable;
    }

    private PlantillaComunicado resolverPlantillaConstantesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
            ParametrosComunicadoDTO parametrosComunicadoDTO) {
        logger.info("resolverPlantillaConstantesComunicado: { }" + parametrosComunicadoDTO.getIdsSolicitud());
        ResolverPlantillaConstantesComunicado resolverPlantillaConstantesComunicado = new ResolverPlantillaConstantesComunicado(etiquetaPlantillaComunicadoEnum, parametrosComunicadoDTO);
        resolverPlantillaConstantesComunicado.execute();

        return resolverPlantillaConstantesComunicado.getResult();
    }


     private List<PersonaDTO> consultarPersonaRazonSocial(TipoIdentificacionEnum tipoIdentificacion,
                                                        String numeroIdentificacion, String razonSocial) {
         logger.info(" personaAfiliada111)" );
         List<PersonaDTO> persona = new ArrayList<PersonaDTO>();


        ConsultarPersonaRazonSocial consultarPersonaRazonSocial = new ConsultarPersonaRazonSocial(numeroIdentificacion,tipoIdentificacion,null );

        consultarPersonaRazonSocial.execute();

       logger.info(" personaAfiliada111)" + consultarPersonaRazonSocial.getResult());

        return  consultarPersonaRazonSocial.getResult();
     }
  
     private Boolean validarValidacionesPensionado25anios(TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado,
        String correo, String nombreCompleto,String direccion, String telefono, Long idCargue, Long noLinea){
        List<String> listaValidaciones = ejecutarMallaValidacionAfiliado( tipoIdAfiliado, numIdAfiliado);

        if(listaValidaciones != null && !listaValidaciones.isEmpty()){
            ObtenerCarguePensionados25Anios obtenerCarguePensionados25Anios = new ObtenerCarguePensionados25Anios(idCargue);
            obtenerCarguePensionados25Anios.execute();
            ConsolaEstadoCargueProcesoDTO result = obtenerCarguePensionados25Anios.getResult();
            agregarInconsistenciasHallazgosValidacion(result, listaValidaciones, noLinea);
            try {
    
                ConsultarSolicitudAfiliacionPersonaAfiliada consultarSolicitudAfiliacionPersonaAfiliada = new ConsultarSolicitudAfiliacionPersonaAfiliada(null,null,null,null,numIdAfiliado,tipoIdAfiliado);
                consultarSolicitudAfiliacionPersonaAfiliada.execute();
                ListadoSolicitudesAfiliacionDTO res = consultarSolicitudAfiliacionPersonaAfiliada.getResult();
                if(res.getLstSolicitudes() !=null && !res.getLstSolicitudes().isEmpty()){
                    Long idSolicitud = res.getLstSolicitudes().get(0).getIdSolicitudGlobal();
                    NotificacionParametrizadaDTO rechazoComunicado = rechazoComunicado(correo, nombreCompleto, direccion, telefono, idSolicitud);
                    enviarNotificacionComunicado(rechazoComunicado);

                }
                
            } catch (Exception e) {
                logger.error("e", e);
            }

            
            return true;
        }
        return false;
     }
        public void agregarInconsistenciasHallazgosValidacion(ConsolaEstadoCargueProcesoDTO cargue, List<String> validaciones, Long noLinea){
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = cargue.getLstErroresArhivo();
            Long cntErr = cargue.getNumRegistroConErrores();
            if(hallazgos == null){
                hallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
            }

            for (String error: validaciones) {
                ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                hallazgo.setNumeroLinea(noLinea);
                hallazgo.setNombreCampo("Tipo de identificacion");
                hallazgo.setError(error);
                hallazgos.add(hallazgo);
            }
            cargue.setLstErroresArhivo(hallazgos);
            cargue.setNumRegistroConErrores(cntErr + 1L);
            actualizarCargueConsolaEstado(cargue.getIdConsolaEstadoCargueMasivo(), cargue);

        }


        private List<String> ejecutarMallaValidacionAfiliado(
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado) {
        String firmaMetodo = "NovedadesBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;
        List<String> validaciones = new ArrayList();
        logger.info(firmaMetodo + ": " + tipoIdAfiliado+ " : "+ numIdAfiliado);
    
        

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
        datosValidacion.put("tipoAfiliado", "PENSIONADO");

        ValidarReglasNegocio validador = new ValidarReglasNegocio("25LEY", bloque.getProceso(),
                "PENSIONADO", datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
                logger.info("Validaciones no existosas");
                logger.info(validacion.getDetalle());
                if(validacion.getDetalle().equals("El solicitante ya tiene una solicitud de afiliación en proceso.")){
                    validacion.setDetalle("No es posible comenzar un proceso de afiliación porque la persona ya tiene otra solicitud de afiliación sin finalizar.");
                }
                validaciones.add(validacion.getDetalle());
                
            }
        }
        // se diligencia el mapa de datos
        datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
        datosValidacion.put("tipoAfiliado", "TRABAJADOR_DEPENDIENTE");

         validador = new ValidarReglasNegocio("25LEY", bloque.getProceso(),
                "TRABAJADOR_DEPENDIENTE", datosValidacion);
        validador.execute();
        resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
                logger.info("Validaciones no existosas");
                logger.info(validacion.getDetalle());
                if(validacion.getDetalle().equals("El solicitante ya tiene una solicitud de afiliación en proceso.")){
                    validacion.setDetalle("No es posible comenzar un proceso de afiliación porque la persona ya tiene otra solicitud de afiliación sin finalizar.");
                }
                validaciones.add(validacion.getDetalle());
                
            }
        }
        // se diligencia el mapa de datos
        datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
        datosValidacion.put("tipoAfiliado", "TRABAJADOR_INDEPENDIENTE");

        validador = new ValidarReglasNegocio("25LEY", bloque.getProceso(),
                "TRABAJADOR_INDEPENDIENTE", datosValidacion);
        validador.execute();
        resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
                logger.info("Validaciones no existosas");
                logger.info(validacion.getDetalle());
                if(validacion.getDetalle().equals("El solicitante ya tiene una solicitud de afiliación en proceso.")){
                    validacion.setDetalle("No es posible comenzar un proceso de afiliación porque la persona ya tiene otra solicitud de afiliación sin finalizar.");
                }
                validaciones.add(validacion.getDetalle());
                
            }
        }
        ConsultarUltimaClasificacionCategoria consultar = new ConsultarUltimaClasificacionCategoria(numIdAfiliado, tipoIdAfiliado);
        consultar.execute();
        if (consultar.getResult() != null && !consultar.getResult().isEmpty()) {
            logger.info("Paso 1 antes de clasificacion");
            if(consultar.getResult().toString().equals("FIDELIDAD_25_ANIOS")){
                logger.info("Paso 2 despues de clasificacion");
                validaciones.add("tipo de clasificación FIDELIDAD_25_AÑOS ya presente");
            }
        }
        return validaciones;
    }
    private NotificacionParametrizadaDTO rechazoComunicado(String correoDestinatario, String nombres, String direccionResidencia, String telefono, Long idSolicitud) {
		NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
	

		notificacion.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.RCHZ_AFL_PER_INC_VAL);

		List<String> destinatarioTO = new ArrayList<>();
        destinatarioTO.add(correoDestinatario);

		notificacion.setDestinatarioTO(destinatarioTO);

		notificacion.setReplantearDestinatarioTO(true);

		notificacion.setProcesoEvento("AFILIACION_PERSONAS_PRESENCIAL");

        notificacion.setIdSolicitud(idSolicitud);

		

		Map<String, String> parametrosPlanilla = new HashMap<String, String>();

		// se calcula la fecha del sistema
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate ahora = LocalDate.now();
		parametrosPlanilla.put("fechaDelSistema", timeFormat.format(ahora));
		parametrosPlanilla.put("nombresYApellidosDelAfiliadoPrincipal", nombres);
        parametrosPlanilla.put("direccionResidencia", direccionResidencia);
        parametrosPlanilla.put("telefono", telefono);
         parametrosPlanilla.put("idSolicitud", idSolicitud.toString());
      


		ParametrosComunicadoDTO parametros = new ParametrosComunicadoDTO();
		Map<String, Object> parametrosMapa = new HashMap<String, Object>();
		parametros.setParams(parametrosMapa);
		notificacion.setParams(parametrosPlanilla);
		notificacion.setParametros(parametros);

		return notificacion;
	}
    private void enviarNotificacionComunicado(NotificacionParametrizadaDTO notificacion) {
  
        logger.info("Entra por aqui enviarNotificacionComunicado"+ String.join(",", notificacion.getDestinatarioTO()));
        EnviarNotificacionComunicado enviarNotificacion = new EnviarNotificacionComunicado(notificacion);
        enviarNotificacion.execute();
    }
    public DatosBasicosIdentificacionDTO datosBeneficiarios( TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        PersonaDTO personas = new PersonaDTO();
        personas.setTipoIdentificacion(tipoIdentificacion);
        personas.setNumeroIdentificacion(numeroIdentificacion);

        DatosBasicosIdentificacionDTO datosBasicos = new DatosBasicosIdentificacionDTO();
        datosBasicos.setPersona(personas);
        return datosBasicos;
    }

    private String conversionAString(ResultadoValidacionArchivoTrasladoDTO datosTemporales) {
        try {
            logger.info(
                    "Inicio de método conversionAString(AporteManualDTO aporteManualDTO)");
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(datosTemporales);
            return jsonPayload;
        } catch (Exception e) {
            logger.error("Ocurrio un error ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

}
