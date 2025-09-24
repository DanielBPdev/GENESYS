package com.asopagos.empleadores.ejb;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.InformacionContactoDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.Vista360EmpleadorBusquedaParamsDTO;
import com.asopagos.dto.Vista360EmpleadorRespuestaDTO;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.TipoEspecificoSolicitudDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.empleadores.dto.RespuestaDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empleadores.constants.NamedQueriesConstants;
import com.asopagos.empleadores.service.EmpleadoresService;
import com.asopagos.entidades.ccf.afiliaciones.AsesorResponsableEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.core.BeneficioEmpleador;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.comunicados.EstadoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rutine.empleadores.ActualizarEstadoEmpleadorPorAportesRutine;
import com.asopagos.rutine.empleadores.ModificarEmpleadorRutine;
import com.asopagos.rutine.empleadores.VerificarExisteEmpleadorAsociadoRutine;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.EstadosUtils;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ObtenerArchivo;
 //Importaciones para la optimizaciones del json
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.reflect.TypeToken;
import java.lang.ProcessBuilder.Redirect.Type;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Map;
import java.util.logging.Logger;

import java.util.stream.Collectors;
import java.util.HashMap;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.comunicados.clients.ConsultarPlantillaComunicado;
import com.asopagos.comunicados.clients.GenerarCertificado;
import com.asopagos.enumeraciones.core.TipoCertificadoMasivoEnum;
import com.asopagos.comunicados.clients.ResolverVariablesComunicado;
import com.asopagos.comunicados.dto.CertificadoDTO;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.asopagos.constantes.parametros.clients.ConsultarConstantesParametro;
import com.asopagos.constantes.parametros.dto.ConstantesParametroDTO;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import java.util.Arrays;
import com.asopagos.archivos.clients.ConvertHTMLtoPDF;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.core.Response; 
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.asopagos.entidades.transversal.core.ControlCertificadosMasivos;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.dto.ControlCertificadosMasivosDTO;
import javax.ws.rs.core.MediaType;
 //FIN Importaciones para la optimizaciones del json  
/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de empleadores <b>Historia de Usuario:</b> HU 111-066, HU
 * 111-070, TRA 111-329
 *
 * @author Jerson Zambrano <jzambrano@heinsohn.com.co>
 *
 * Modificación
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
@Stateless
public class EmpleadoresBusiness implements EmpleadoresService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "empleadores_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(EmpleadoresBusiness.class);

    /**
     * <b>Descripción</b> Método que se encarga validar si existe una persona en
     * el sistema      <code>numeroIdentificación es el numero de identificacion de la persona,
     * tipoIdentificacion es el tipo de identificacion</code><br/>
     * <br/>
     *
     * @param numeroIdentificación numero de identificacion de la persona
     * @param tipoIdentificacion tipo de documento de identificacion
     * @return true si existe o false si no existe
     */
    private Persona consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        logger.debug("Inicia consultarPersona(String, TipoIdentificacionEnum)");
        Persona persona = new Persona();
        try {
            persona = (Persona) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EMPLEADOR)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.debug("No se encuentra una persona con los filtros suministrados");
        }
        logger.debug("Finaliza consultarPersona(String, TipoIdentificacionEnum)");
        return persona;
    }

    /**
     * Método encargado de actualizar la ubicación de una persona
     *
     * @param ubicacion
     * @param ubicacionOriginal
     * @return
     */
    private Ubicacion guardarUbicacionPersona(Ubicacion ubicacion, Ubicacion ubicacionOriginal) {
        logger.debug("Inicia guardarUbicacionPersona(Ubicacion, Ubicacion)");
        if (ubicacion != null) {
            if (ubicacion.getMunicipio() != null) {
                if (ubicacion.getMunicipio().getIdMunicipio() == null) {
                    logger.debug("Finalizar guardarUbicacionPersona(Ubicacion, Ubicacion): Ubicación sin municipio");
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                } else {
                    // Se verifica que el Municipio exista
                    Municipio municipio = entityManager.find(Municipio.class, ubicacion.getMunicipio().getIdMunicipio());
                    if (municipio == null) {
                        logger.debug("Finalizar guardarUbicacionPersona(Ubicacion, Ubicacion): El municipio de la ubicación no existe");
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                    }
                }
            }
            if (ubicacionOriginal != null) {
                logger.debug("Finalizar guardarUbicacionPersona(Ubicacion, Ubicacion): ubicación ya existente no se modifica");
                return ubicacionOriginal;
                // Si ya existe información de la ubicación principal de la
                // persona se le setea el mismo id para hacer el merge

                // ubicacion.setIdUbicacion(ubicacionOriginal.getIdUbicacion());
                // return entityManager.merge(ubicacion);
            } else {
                // Se asigna explícitamente a null el id para asegurarse que se
                // hace persist de la entidad
                ubicacion.setIdUbicacion(null);
                entityManager.persist(ubicacion);
                logger.debug("Finalizar guardarUbicacionPersona(Ubicacion, Ubicacion)");
                return ubicacion;
            }
        }
        logger.debug("Finalizar guardarUbicacionPersona(Ubicacion, Ubicacion)");
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#crearEmpleador(com.
     * asopagos.entidades.ccf.personas.Empleador)
     */
    @Override
    public Long crearEmpleador(Empleador empleador) {
        logger.debug("Inicia crearEmpleador(Empleador)");
        // valida si esta el objeto persona
        if (empleador != null && empleador.getEmpresa() != null && empleador.getEmpresa().getPersona() != null
                && empleador.getEmpresa().getPersona().getNumeroIdentificacion() != null
                && empleador.getEmpresa().getPersona().getTipoIdentificacion() != null
                && Boolean.TRUE.equals(empleador.getEmpresa().getPersona().getTipoIdentificacion().getIdentificionEmpleador())) {

            // verifica si existe la persona suministrada ya existe como un
            // empleador
            try {
                entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO, Empleador.class)
                        .setParameter("numeroIdentificacion", empleador.getEmpresa().getPersona().getNumeroIdentificacion())
                        .setParameter("tipoIdentificacion", empleador.getEmpresa().getPersona().getTipoIdentificacion()).getSingleResult();
                logger.debug("Finaliza crearEmpleador(Empleador): Empleador ya existe ");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
            } catch (NonUniqueResultException nue) {
                logger.error("Finaliza crearEmpleador(Empleador): Empleador ya existe ");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            } catch (NoResultException nre) {
                logger.debug("Finaliza crearEmpleador(Empleador): Empleador es apto para registarse");
            }

            Persona persona = consultarPersona(empleador.getEmpresa().getPersona().getNumeroIdentificacion(),
                    empleador.getEmpresa().getPersona().getTipoIdentificacion());

            // se valida que si trae identificador y no es igual al consultado
            // se debe ambigüedad de datos
            if (empleador.getEmpresa().getPersona().getIdPersona() != null && persona.getIdPersona() != null
                    && !empleador.getEmpresa().getPersona().getIdPersona().equals(persona.getIdPersona())) {
                logger.debug("Finaliza crearEmpleador(Empleador): No parámetros no válidos");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

            try {
                Empresa emprea = (Empresa) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_TIPODOC_NUMERODOC)
                        .setParameter("numeroIdentificacion", empleador.getEmpresa().getPersona().getNumeroIdentificacion())
                        .setParameter("tipoIdentificacion", empleador.getEmpresa().getPersona().getTipoIdentificacion()).getSingleResult();
                // TODO se debe ajustar cuando se setee los nombres desde pila
                if (empleador.getEmpresa().getPersona().getPrimerNombre() != null) {
                    emprea.getPersona().setPrimerNombre(empleador.getEmpresa().getPersona().getPrimerNombre());
                }

                if (empleador.getEmpresa().getPersona().getSegundoNombre() != null) {
                    emprea.getPersona().setSegundoNombre(empleador.getEmpresa().getPersona().getSegundoNombre());
                }

                if (empleador.getEmpresa().getPersona().getPrimerApellido() != null) {
                    emprea.getPersona().setPrimerApellido(empleador.getEmpresa().getPersona().getPrimerApellido());
                }

                if (empleador.getEmpresa().getPersona().getSegundoApellido() != null) {
                    emprea.getPersona().setSegundoApellido(empleador.getEmpresa().getPersona().getSegundoApellido());
                }
                empleador.setEmpresa(emprea);

                entityManager.merge(emprea);
            } catch (Exception e) {

                // si la persona ya existe se asocia la persona encontrada al
                // nuevo
                // empleador, de lo contrario se crea la persona
                if (persona.getIdPersona() != null) {
                    // asocia la persona manejada al empleador y se persiste
                    empleador.getEmpresa().setPersona(persona);
                } else {
                    // se persiste la persona asocaida al empleador
                    // para este punto la persona no tiene una ubicación
                    entityManager.persist(empleador.getEmpresa().getPersona());
                }
                entityManager.persist(empleador.getEmpresa());
            }

            // empleador.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
            entityManager.persist(empleador);

            logger.debug("Finaliza crearEmpleador(Empleador)");
            return empleador.getIdEmpleador();
        } else {
            logger.debug("Finaliza crearEmpleador(Empleador): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#actualizarEmpleador(
     * java.lang.Long, com.asopagos.entidades.ccf.personas.Empleador)
     */
    @Override
    public void actualizarEmpleador(Long idEmpleador, EmpleadorDTO empleador) {
        logger.debug("Inicia actualizarEmpleador(Long, Empleador)");
        // se valida que los criterios de identificación de una persona no sean
        // nulos
        if (empleador.getEmpresa().getPersona() == null || empleador.getEmpresa().getPersona().getNumeroIdentificacion() == null
                || empleador.getEmpresa().getPersona().getTipoIdentificacion() == null || idEmpleador == null) {
            logger.debug("Finaliza actualizarEmpleador(Long, Empleador): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        try {
            Empleador empleadorOriginal = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_PERSONA)
                    .setParameter("tipoIdentificacion", empleador.getEmpresa().getPersona().getTipoIdentificacion())
                    .setParameter("numeroIdentificacion", empleador.getEmpresa().getPersona().getNumeroIdentificacion()).getSingleResult();

            // se valida que el empleador que se desea modificar si coincida
            if (!idEmpleador.equals(empleadorOriginal.getIdEmpleador())) {
                logger.debug(
                        "Finaliza actualizarEmpleador(Long, Empleador): El empleador a actualizar no coincide con la identificación del empleador");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

            //empleadorOriginal = entityManager.merge(empleadorOriginal);
            //Empresa empresaOriginal = entityManager.merge(empleadorOriginal.getEmpresa());
            //Persona personaOriginal = entityManager.merge(empresaOriginal.getPersona());
            Empresa empresaOriginal = empleadorOriginal.getEmpresa();
            Persona personaOriginal = empleadorOriginal.getEmpresa().getPersona();
            // CodigoCIIU codigoCIIUOriginal =
            // entityManager.merge(empresaOriginal.getCodigoCIIU());
            // ParametrizacionMedioDePago medioOriginal =
            // entityManager.merge(empleadorOriginal.getMedioDePagoSubsidioMonetario());

            if (empleador.getEmpresa() != null) {
                if (empleador.getEmpresa().getPersona() != null) {
                    if (empleador.getEmpresa().getPersona().getTipoIdentificacion() != null) {
                        personaOriginal.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
                    }
                    if (empleador.getEmpresa().getPersona().getNumeroIdentificacion() != null
                            && !empleador.getEmpresa().getPersona().getNumeroIdentificacion().isEmpty()) {
                        personaOriginal.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                    }
                    if (empleador.getEmpresa().getPersona().getDigitoVerificacion() != null) {
                        personaOriginal.setDigitoVerificacion(empleador.getEmpresa().getPersona().getDigitoVerificacion());
                    }
                    if (empleador.getEmpresa().getPersona().getPrimerNombre() != null
                            && !empleador.getEmpresa().getPersona().getPrimerNombre().isEmpty()) {
                        personaOriginal.setPrimerNombre(empleador.getEmpresa().getPersona().getPrimerNombre());
                    }
                    if (empleador.getEmpresa().getPersona().getSegundoNombre() != null
                            && !empleador.getEmpresa().getPersona().getSegundoNombre().isEmpty()) {
                        personaOriginal.setSegundoNombre(empleador.getEmpresa().getPersona().getSegundoNombre());
                    }
                    if (empleador.getEmpresa().getPersona().getPrimerApellido() != null
                            && !empleador.getEmpresa().getPersona().getPrimerApellido().isEmpty()) {
                        personaOriginal.setPrimerApellido(empleador.getEmpresa().getPersona().getPrimerApellido());
                    }
                    if (empleador.getEmpresa().getPersona().getSegundoApellido() != null
                            && !empleador.getEmpresa().getPersona().getSegundoApellido().isEmpty()) {
                        personaOriginal.setSegundoApellido(empleador.getEmpresa().getPersona().getSegundoApellido());
                    }
                    if (empleador.getEmpresa().getPersona().getRazonSocial() != null
                            && !empleador.getEmpresa().getPersona().getRazonSocial().isEmpty()) {
                        personaOriginal.setRazonSocial(empleador.getEmpresa().getPersona().getRazonSocial());
                    }
                }
                if (empleador.getEmpresa().getNombreComercial() != null && !empleador.getEmpresa().getNombreComercial().isEmpty()) {
                    empresaOriginal.setNombreComercial(empleador.getEmpresa().getNombreComercial());
                }
                if (empleador.getEmpresa().getFechaConstitucion() != null) {
                    Calendar cal = Calendar.getInstance(); // creates calendar
                    cal.setTime(empleador.getEmpresa().getFechaConstitucion()); 
                    logger.info("Prueba otra fecha" + cal.getTime());// sets calendar time/date
                    cal.add(Calendar.HOUR_OF_DAY, 12);     // adds one hour
                    empresaOriginal.setFechaConstitucion(cal.getTime());
                    logger.info("Prueba otra fecha 1" + cal.getTime());
                }
                if (empleador.getEmpresa().getNaturalezaJuridica() != null) {
                    empresaOriginal.setNaturalezaJuridica(empleador.getEmpresa().getNaturalezaJuridica());
                }
                if (empleador.getEmpresa().getIdUltimaCajaCompensacion() != null) {
                    empresaOriginal.setIdUltimaCajaCompensacion(empleador.getEmpresa().getIdUltimaCajaCompensacion());
                }
                if (empleador.getEmpresa().getPaginaWeb() != null && !empleador.getEmpresa().getPaginaWeb().isEmpty()) {
                    empresaOriginal.setPaginaWeb(empleador.getEmpresa().getPaginaWeb());
                }
                if (empleador.getEmpresa().getPaginaWeb() != null && !empleador.getEmpresa().getPaginaWeb().isEmpty()) {
                    empresaOriginal.setPaginaWeb(empleador.getEmpresa().getPaginaWeb());
                }
                /*if (empleador.getEmpresa().getIdPersonaRepresentanteLegal() != null) {
                    empresaOriginal.setIdPersonaRepresentanteLegal(empleador.getEmpresa().getIdPersonaRepresentanteLegal());
                }
                if (empleador.getEmpresa().getIdPersonaRepresentanteLegalSuplente() != null) {
                    empresaOriginal.setIdPersonaRepresentanteLegalSuplente(empleador.getEmpresa().getIdPersonaRepresentanteLegalSuplente());
                }*/
                if (empleador.getEmpresa().getEspecialRevision() != null) {
                    empresaOriginal.setEspecialRevision(empleador.getEmpresa().getEspecialRevision());
                }
                if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
                    empresaOriginal.setIdUbicacionRepresentanteLegal(empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                }
                if (empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente() != null) {
                    empresaOriginal
                            .setIdUbicacionRepresentanteLegalSuplente(empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente());
                }
                if (empleador.getEmpresa().getCodigoCIIU() != null) {
                    if (empresaOriginal.getCodigoCIIU() != null) {
                        if (empresaOriginal.getCodigoCIIU().getIdCodigoCIIU() != empleador.getEmpresa().getCodigoCIIU().getIdCodigoCIIU()) {
                            empresaOriginal.setCodigoCIIU(
                                    entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CODIGOCIIU_ID, CodigoCIIU.class)
                                            .setParameter("idCodigoCIIU", empleador.getEmpresa().getCodigoCIIU().getIdCodigoCIIU())
                                            .getSingleResult());
                        }
                    } else {
                        empresaOriginal.setCodigoCIIU(entityManager
                                .createNamedQuery(NamedQueriesConstants.BUSCAR_CODIGOCIIU_ID, CodigoCIIU.class)
                                .setParameter("idCodigoCIIU", empleador.getEmpresa().getCodigoCIIU().getIdCodigoCIIU()).getSingleResult());

                    }

                }
                if (empleador.getEmpresa().getArl() != null) {
                    if (empresaOriginal.getArl() != null) {
                        if (empresaOriginal.getArl().getIdARL() != empleador.getEmpresa().getArl().getIdARL()) {
                            try {
                                ARL arl = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ARL_ID, ARL.class)
                                                        .setParameter("idARL", empleador.getEmpresa().getArl().getIdARL())
                                                        .getSingleResult();
                                empresaOriginal.setArl(arl);
                            } catch (NoResultException e) {
                                empresaOriginal.setArl(null);
                                logger.warn("No se encontró ARL con id: " + empleador.getEmpresa().getArl().getIdARL() + ". Se establece como null.");
                            }
                        }
                    } else {
                        empresaOriginal.setArl(entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ARL_ID, ARL.class)
                                .setParameter("idARL", empleador.getEmpresa().getArl().getIdARL()).getSingleResult());
                    }
                }

            }

            if (empleador.getMotivoDesafiliacion() != null) {
                empleadorOriginal.setMotivoDesafiliacion(empleador.getMotivoDesafiliacion());
            }
            if (empleador.getExpulsionSubsanada() != null) {
                empleadorOriginal.setExpulsionSubsanada(empleador.getExpulsionSubsanada());
            }
            if (empleador.getFechaCambioEstadoAfiliacion() != null && empleador.getEstadoEmpleador() != empleadorOriginal.getEstadoEmpleador()) {
                empleadorOriginal.setFechaCambioEstadoAfiliacion(new Date());
            }
            if (empleador.getNumeroTotalTrabajadores() != null) {
                empleadorOriginal.setNumeroTotalTrabajadores(empleador.getNumeroTotalTrabajadores());
            }
            if (empleador.getValorTotalUltimaNomina() != null) {
                empleadorOriginal.setValorTotalUltimaNomina(empleador.getValorTotalUltimaNomina());
            }
            if (empleador.getValorTotalUltimaNomina() != null) {
                empleadorOriginal.setValorTotalUltimaNomina(empleador.getValorTotalUltimaNomina());
            }
            if (empleador.getMedioDePagoSubsidioMonetario() != null) {
                empleadorOriginal.setMedioDePagoSubsidioMonetario(empleador.getMedioDePagoSubsidioMonetario());
            }
            if (empleador.getEstadoEmpleador() != null) {
                if (empleador.getEstadoEmpleador().equals(EstadoEmpleadorEnum.ACTIVO)
                        || empleador.getEstadoEmpleador().equals(EstadoEmpleadorEnum.INACTIVO)) {
                    empleadorOriginal.setEstadoEmpleador(empleador.getEstadoEmpleador());
                }
            }
            if (empleador.getFechaRetiro() != null) {
                empleadorOriginal.setFechaRetiro(empleador.getFechaRetiro());
            }
            if (empleador.getPeriodoUltimaNomina() != null) {
                empleadorOriginal.setPeriodoUltimaNomina(empleador.getPeriodoUltimaNomina());
            }
            if (empleador.getFechaSubsancionExpulsion() != null) {
                empleadorOriginal.setFechaSubsancionExpulsion(empleador.getFechaSubsancionExpulsion());
            }
            if (empleador.getMotivoSubsanacionExpulsion() != null) {
                empleadorOriginal.setMotivoSubsanacionExpulsion(empleador.getMotivoSubsanacionExpulsion());
            }
            if (empleador.getFechaRetiroTotalTrabajadores() != null) {
                empleadorOriginal.setFechaRetiroTotalTrabajadores(empleador.getFechaRetiroTotalTrabajadores());
            }
            empleadorOriginal = entityManager.merge(empleadorOriginal);
        } 
        
        catch (NoResultException nre) {
            logger.error("No existe el empleador a actualizar", nre);
            logger.debug("Finaliza actualizarEmpleador(Long, Empleador): No existe el empleador a actualizar");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
        }
        logger.debug("Finaliza actualizarEmpleador(Long, Empleador)");
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#crearRolContactoEmpleador(java.lang.Long,
     * java.util.List)
     */
    @Override
    public List<Long> crearRolContactoEmpleador(Long idEmpleador, List<RolContactoEmpleador> rolesContacto) {
        logger.debug("Inicia crearRolContactoEmpleador(Long idEmpleador, List<RolContactoEmpleador> rolesContacto)");
        List<Long> lstRolesContacto = new ArrayList<>();
        for (RolContactoEmpleador rol : rolesContacto) {
            lstRolesContacto.add(actualizarRolContactoEmpleador(idEmpleador, rol));
        }
        logger.debug("Finaliza crearRolContactoEmpleador(Long idEmpleador, List<RolContactoEmpleador> rolesContacto)");
        return lstRolesContacto;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * generarReporteDeEmpleadoresAfiliados(java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmpleadorDTO> generarReporteDeEmpleadoresAfiliados() {

        logger.debug("Inicia generarReporteDeEmpleadoresAfiliados(Date, Date)");

        List<EmpleadorDTO> listEmpleadoresDto = new ArrayList<>();
        List<BeneficioEmpleadorModeloDTO> listBeneficios = new ArrayList<>();
        EstadoEmpleadorEnum estado = EstadoEmpleadorEnum.ACTIVO;

        // se consulta los empleados afiliados en un rango de fechas
        List<Object[]> listEmpleadores = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_EMPLEADORES_EN_RANGO_FECHA)
                .setParameter("estado", estado).getResultList();
        ArrayList<Long> idEmpleadores = new ArrayList<>();
        for (Object[] empleador : listEmpleadores) {
            EmpleadorDTO empleAux = new EmpleadorDTO((Empleador) empleador[0], (Short) empleador[1]);
            listEmpleadoresDto.add(empleAux);
            idEmpleadores.add(((Empleador) empleador[0]).getIdEmpleador());
        }

        try {
            listBeneficios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_BENEFICIOS_EMPLEADORES)
                    .setParameter("idEmpleador", idEmpleadores).setParameter("estado", Boolean.TRUE).getResultList();
        } catch (Exception e) {
        }
        for (BeneficioEmpleadorModeloDTO beneficio : listBeneficios) {
            for (EmpleadorDTO empleador : listEmpleadoresDto) {
                if (beneficio.getIdEmpleador().equals(empleador.getIdEmpleador())) {
                    empleador.setBeneficio(beneficio);
                }
            }
        }

        logger.debug("Finaliza generarReporteDeEmpleadoresAfiliados(Date, Date)");
        return listEmpleadoresDto;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#actualizarRolContactoEmpleador(java.lang.Long,
     * java.lang.Long, com.asopagos.entidades.ccf.personas.RolContactoEmpleador)
     */
    @Override
    public void actualizarRolContactoEmpleador(Long idEmpleador, List<RolContactoEmpleador> rolesContacto) {
        logger.debug("Inicia actualizarRolContactoEmpleador(Long, List<RolContactoEmpleador>)");
        for (RolContactoEmpleador rol : rolesContacto) {
            actualizarRolContactoEmpleador(idEmpleador, rol);
        }
        logger.debug("Finaliza actualizarRolContactoEmpleador(Long, List<RolContactoEmpleador>)");
    }

    /*
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Empleador> buscarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
            Boolean aplicaPre) {
        logger.debug("Inicia buscarEmpleador(TipoIdentificacionEnum, String, Integer, String)");
        List<Empleador> listEmpleador = new ArrayList<>();
        List<Empresa> listEmpresa = new ArrayList<>();
        Empresa empresa;
        if (tipoIdentificacion != null && numeroIdentificacion != null) {
            if (Boolean.TRUE.equals(aplicaPre)) {
                // Consulta Antigua
                // namedQuery =
                // NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_PRE;
                // Refactor consulta
                listEmpleador = (List<Empleador>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION, Empleador.class)
                        .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();

                listEmpresa = (List<Empresa>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_TIPO_DOC_NUMERO_DOC, Empresa.class)
                        .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();
            }
            else {
                // namedQuery =
                // NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO;
                listEmpleador = (List<Empleador>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION_ESTADO_DISCRIMINAR,
                                Empleador.class)
                        .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();

                listEmpresa = (List<Empresa>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_TIPO_Y_NUMERO_IDENTIFICACION_ESTADO_DISCRIMINAR,
                                Empresa.class)
                        .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getResultList();
            }
        }
        else if (razonSocial != null) {
            if (Boolean.TRUE.equals(aplicaPre)) {

                listEmpleador = (List<Empleador>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL_PRE, Empleador.class)
                        .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

                listEmpresa = (List<Empresa>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_RAZON_SOCIAL_PRE, Empresa.class)
                        .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();
            }
            else {
                // consultar empleador por razon social
                listEmpleador = (List<Empleador>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL, Empleador.class)
                        .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

                listEmpresa = (List<Empresa>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_EMPLEADOR_POR_RAZON_SOCIAL, Empresa.class)
                        .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();
            }
        }
        for (Empleador empleador : listEmpleador) {
            empresa = empleador.getEmpresa();
            if (empresa != null) {
                if (empresa.getPersona() != null) {
                    try {
                        empresa = (Empresa) entityManager
                                .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_EMPRESA_POR_ID, Empresa.class)
                                .setParameter("idPersona", empresa.getPersona().getIdPersona()).getSingleResult();
                        if (empresa != null) {
                            empleador.setEmpresa(empresa);
                        }
                    } catch (NoResultException e) {
                        empresa = empleador.getEmpresa();
                    }
                }
            }
        }

        Empleador emple;
        for (Empresa empr : listEmpresa) {
            emple = new Empleador();
            emple.setEmpresa(empr);
            listEmpleador.add(emple);
        }

        // SE CALCULA EL ESTADO PARA LOS EMPLEADORES CONSULTADOS Y SE LES SETEA
        if (listEmpleador != null && !listEmpleador.isEmpty()) {

            List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
            for (Empleador empleador : listEmpleador) {
                ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
                paramsConsulta.setEntityManager(entityManager);
                paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
                paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
                paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
                listConsulta.add(paramsConsulta);
            }
            List<EstadoDTO> listEstadod = EstadosUtils.consultarEstadoCaja(listConsulta);
            for (EstadoDTO estadoDTO : listEstadod) {
                for (Empleador empleador : listEmpleador) {
                    if (empleador.getEmpresa().getPersona().getNumeroIdentificacion().equals(estadoDTO.getNumeroIdentificacion())
                            && empleador.getEmpresa().getPersona().getTipoIdentificacion().equals(estadoDTO.getTipoIdentificacion())) {
                        empleador.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
                        if (estadoDTO.getEstado() != null) {
                            empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().toString()));
                        }
                        break;
                    }
                }
            }
            logger.debug("Finaliza buscarEmpleador(TipoIdentificacionEnum, String, Integer, String)");
            return listEmpleador;
        }
        else {
            logger.debug(
                    "Finaliza buscarEmpleador(TipoIdentificacionEnum, String, Integer, String): No se encuentra información para los filtros de búsqueda");
            return null;
        }
    }
     */
 /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#buscarEmpleador(com.
     * asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String,
     * java.lang.Integer, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Empleador> buscarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial,
            Boolean aplicaPre) {
        logger.debug("Inicia buscarEmpleadorNew(TipoIdentificacionEnum, String, Integer, String)");
        List<Empleador> listEmpleador = new ArrayList<>();
        List<Empresa> listEmpresa = new ArrayList<>();
        List<Object[]> empleadoresObj = new ArrayList<>();
        List<Object[]> empresasObj = new ArrayList<>();
        List<PreRegistroEmpresaDesCentralizada> descentralizada = new ArrayList<>();
        List rescon = new ArrayList<RespuestaDTO>();

        if (tipoIdentificacion != null && numeroIdentificacion != null) {

            empleadoresObj = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION_NATIVA)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getResultList();

            empresasObj = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_TIPO_DOC_NUMERO_DOC_NATIVA)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getResultList();

        } else if (razonSocial != null) {
            // consultar empleador por razon social
            empleadoresObj = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL_NATIVA)
                    .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

            empresasObj = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_EMPLEADOR_POR_RAZON_SOCIAL_NATIVA)
                    .setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

        }

        for (Object[] obj : empleadoresObj) {
            Empleador empl = (Empleador) obj[2];
            empl.setIdEmpleador((Long) obj[6]);
            empl.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
            if (obj[5] != null) {
                empl.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(obj[5].toString()));
            }
            listEmpleador.add(empl);
        }

        for (Object[] obj : empresasObj) {
            Empleador empl = new Empleador();
            Empresa emp = (Empresa) obj[0];
            empl.setEmpresa(emp);
            empl.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
            if (obj[4] != null) {
                empl.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(obj[4].toString()));
            }
            listEmpleador.add(empl);
        }
        if (numeroIdentificacion != null && !numeroIdentificacion.matches("[0-9]+")&& aplicaPre != null && aplicaPre == true  ) {
            if (listEmpleador == null || listEmpleador.isEmpty()) {
                logger.info("Ingreso al campo");
                descentralizada = (List<PreRegistroEmpresaDesCentralizada>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_PREREGISTRO_DESCENTRALIZADA)
                        .setParameter("numeroDocumentoConSerial", numeroIdentificacion)
                        .getResultList();

                if (descentralizada != null && !descentralizada.isEmpty()) {
                    logger.info("Se econtro en dez y debe permitir registro" + descentralizada.get(0).getPrdNombreSucursalPila());
                   RespuestaDTO respuesta = new RespuestaDTO("true");
                    rescon.add(respuesta);
                    return rescon;

                } else {
                     RespuestaDTO respuesta = new RespuestaDTO("false");
                    rescon.add(respuesta);
                    return rescon;
                }

            }

        }

        //  ***************** fin ****************************************************
        if (listEmpleador != null && !listEmpleador.isEmpty()) {
            logger.debug("Finaliza buscarEmpleador(TipoIdentificacionEnum, String, Integer, String)");
            return listEmpleador;
        } else {
            logger.debug(
                    "Finaliza buscarEmpleador(TipoIdentificacionEnum, String, Integer, String): No se encuentra información para los filtros de búsqueda");
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#crearSocioEmpleador(
     * java.lang.Long, java.util.List)
     */
    @Override
    @Deprecated
    public List<Long> crearSocioEmpleador(Long idEmpleador, List<SocioEmpleador> listSocios) {

        logger.debug("Inicia crearSocioEmpleador(Long, List<SocioEmpleador>)");

        // se valida el parametro que identifica el empleador
        if (idEmpleador == null || listSocios == null || listSocios.isEmpty()) {
            logger.debug("Finaliza crearSocioEmpleador(Long, List<SocioEmpleador>): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        List<Long> listIdsSociosEmpleador = new ArrayList<>();

        // se consulta de que el empleador este registrado en el sistema
        Empleador empleador = entityManager.find(Empleador.class, idEmpleador);

        if (empleador != null) {

            // recorre la lista de socios
            for (SocioEmpleador socioEmpleador : listSocios) {

                // validacion de campos obligatorios del objeto socioEmpleador
                if (socioEmpleador != null && socioEmpleador.getPersona() != null
                        && socioEmpleador.getPersona().getNumeroIdentificacion() != null
                        && socioEmpleador.getPersona().getTipoIdentificacion() != null) {

                    // validacion de la existencia de la persona asociado al
                    // objeto
                    Persona persona = consultarPersona(socioEmpleador.getPersona().getNumeroIdentificacion(),
                            socioEmpleador.getPersona().getTipoIdentificacion());
                    if (persona.getIdPersona() != null) {
                        // actualiza las personas
                        entityManager.merge(socioEmpleador.getPersona());
                    } else {
                        entityManager.persist(socioEmpleador.getPersona());
                    }

                    // se valida si el conyugue se define
                    if (socioEmpleador.getConyugue() != null && socioEmpleador.getConyugue().getTipoIdentificacion() != null
                            && socioEmpleador.getConyugue().getNumeroIdentificacion() != null) {
                        // validacion de la existencia de la cónyuge asociado al
                        // objeto
                        Persona conyuge = consultarPersona(socioEmpleador.getConyugue().getNumeroIdentificacion(),
                                socioEmpleador.getConyugue().getTipoIdentificacion());
                        if (conyuge.getIdPersona() != null) {
                            entityManager.merge(socioEmpleador.getConyugue());
                        } else {
                            entityManager.persist(socioEmpleador.getConyugue());
                        }
                    }

                    socioEmpleador.setIdEmpleador(empleador.getIdEmpleador());
                    entityManager.persist(socioEmpleador);
                    // agrega el id del socio a la lista
                    listIdsSociosEmpleador.add(socioEmpleador.getIdSocioEmpleador());

                } else {
                    logger.debug("Finaliza crearSocioEmpleador(Long, List<SocioEmpleador>): No parámetros no válidos");
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
                }
            }
            // retorna listado con los id de los nuevos socios
            logger.debug("Finaliza crearSocioEmpleador(Long, List<SocioEmpleador>)");
            return listIdsSociosEmpleador;
        } else {
            logger.debug("Finaliza crearSocioEmpleador(Long, List<SocioEmpleador>): No se encuentra el empleador");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * actualizarSocioEmpleador(java.lang.Long, java.lang.Long,
     * com.asopagos.entidades.ccf.personas.SocioEmpleador)
     */
    @Override
    @Deprecated
    public void actualizarSocioEmpleador(Long idEmpleador, Long idSocioEmpleador, SocioEmpleador socioEmpleador) {

        logger.debug("Inicia actualizarSocioEmpleador(Long, Long, SocioEmpleador)");

        // se valida el parametro que identifica el empleador
        if (idEmpleador == null || idSocioEmpleador == null || socioEmpleador == null) {
            logger.debug("Finaliza actualizarSocioEmpleador(Long, Long, SocioEmpleador): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
        SocioEmpleador soEmpleador = entityManager.find(SocioEmpleador.class, idSocioEmpleador);

        if (empleador != null && soEmpleador != null) {

            if (!socioEmpleador.getIdEmpleador().equals(empleador.getIdEmpleador())) {
                logger.debug(
                        "Finaliza actualizarSocioEmpleador(Long, Long, SocioEmpleador): No parámetros no válidos: el socio no corresponde al empleador suministrado");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

            // consulta la persona y la actualiza, sino la encuentra la crea
            if (socioEmpleador.getPersona() != null && socioEmpleador.getPersona().getNumeroIdentificacion() != null
                    && socioEmpleador.getPersona().getTipoIdentificacion() != null) {

                Persona persona = consultarPersona(socioEmpleador.getPersona().getNumeroIdentificacion(),
                        socioEmpleador.getPersona().getTipoIdentificacion());
                if (persona.getIdPersona() != null) {
                    socioEmpleador.setPersona(persona);
                    // socioEmpleador.getPersona().setIdPersona(persona.getIdPersona());
                    // entityManager.merge(socioEmpleador.getPersona());
                } else {
                    entityManager.persist(socioEmpleador.getPersona());
                }
            }

            // consulta el conyugue y lo actualiza, sino lo encuentra lo crea
            if (socioEmpleador.getConyugue() != null && socioEmpleador.getConyugue().getNumeroIdentificacion() != null
                    && socioEmpleador.getConyugue().getTipoIdentificacion() != null) {

                Persona conyugue = consultarPersona(socioEmpleador.getConyugue().getNumeroIdentificacion(),
                        socioEmpleador.getConyugue().getTipoIdentificacion());
                if (conyugue.getIdPersona() != null) {
                    socioEmpleador.setConyugue(conyugue);
                    // socioEmpleador.getConyugue().setIdPersona(conyugue.getIdPersona());
                    // entityManager.merge(socioEmpleador.getConyugue());
                } else {
                    entityManager.persist(socioEmpleador.getConyugue());
                }
            }
            entityManager.merge(socioEmpleador);
        } else {
            logger.debug("Finaliza actualizarSocioEmpleador(Long, Long, SocioEmpleador): No se encuentran registros para actualizar");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
        logger.debug("Finaliza actualizarSocioEmpleador(Long, Long, SocioEmpleador)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * gestionarSociosEmpleador(java.lang.Long, java.util.List)
     */
    @Override
    public List<Long> gestionarSociosEmpleador(Long idEmpleador, List<SocioEmpleador> socios) {

        logger.debug("Inicia gestionarSociosEmpleador(Long, List<SocioEmpleador>)");

        // Lista de socios
        List<Long> idsSocios = new ArrayList<>();

        // Se consultan los socios existentes para el empleador
        List<SocioEmpleador> listSociosExistente = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_ELIMINAR_SOCIOS_EMPLEADOR_POR_EMPLEADOR, SocioEmpleador.class)
                .setParameter("idEmpleador", idEmpleador).getResultList();

        // Se verifica si existen socios y si no se enviaron socios se
        // desasocian del empleador los existentes
        if (listSociosExistente != null && !listSociosExistente.isEmpty() && (socios == null || socios.isEmpty())) {
            for (SocioEmpleador socioEmpleador : listSociosExistente) {
                entityManager.merge(socioEmpleador);
                socioEmpleador.setIdEmpleador(null);
            }
            return idsSocios;
        }

        if (socios != null && !socios.isEmpty()) {
            Persona personaOriginal;
            Ubicacion ubicacionSocio;
            Persona personaConyugueOriginal;
            Ubicacion ubicacionConyugue;

            for (SocioEmpleador socioEmpleador : socios) {

                // Se verifica si el socio empleador existe, y tiene relacion
                // con el empleador indicado
                if (socioEmpleador.getIdSocioEmpleador() != null) {

                    SocioEmpleador socioExistente = entityManager.find(SocioEmpleador.class, socioEmpleador.getIdSocioEmpleador());
                    if (socioExistente == null || (socioExistente != null && !socioExistente.getIdEmpleador().equals(idEmpleador))) {
                        logger.debug("Finaliza gestionarSociosEmpleador(Long, List<SocioEmpleador>): No parámetros no válidos");
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                    }
                }

                // Consulta Persona socio
                personaOriginal = consultarPersona(socioEmpleador.getPersona().getNumeroIdentificacion(),
                        socioEmpleador.getPersona().getTipoIdentificacion());
                // si no existe la persona socio se persiste con su ubicación
                if (personaOriginal == null || personaOriginal.getIdPersona() == null) {
                    ubicacionSocio = guardarUbicacionPersona(socioEmpleador.getPersona().getUbicacionPrincipal(), null);
                    socioEmpleador.getPersona().setIdPersona(null);
                    socioEmpleador.getPersona().setUbicacionPrincipal(ubicacionSocio);
                    entityManager.persist(socioEmpleador.getPersona());
                } else {
                    // si existe el socio se toma la información existente y
                    // no se actualiza la persona
                    socioEmpleador.setPersona(personaOriginal);
                }

                if (socioEmpleador.getConyugue() != null) {
                    // Se valida que venga numero y tipo identificación
                    if (socioEmpleador.getConyugue().getNumeroIdentificacion() == null
                            || socioEmpleador.getConyugue().getTipoIdentificacion() == null) {
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
                    }

                    personaConyugueOriginal = consultarPersona(socioEmpleador.getConyugue().getNumeroIdentificacion(),
                            socioEmpleador.getConyugue().getTipoIdentificacion());

                    // si no existe la persona conyuge se persiste con su
                    // ubicación
                    if (personaConyugueOriginal == null || personaConyugueOriginal.getIdPersona() == null) {
                        ubicacionConyugue = guardarUbicacionPersona(socioEmpleador.getConyugue().getUbicacionPrincipal(), null);
                        socioEmpleador.getConyugue().setIdPersona(null);
                        socioEmpleador.getConyugue().setUbicacionPrincipal(ubicacionConyugue);
                        entityManager.persist(socioEmpleador.getConyugue());
                    } else {
                        // si existe el conyugue se toma la información
                        // existente y
                        // no se actualiza la persona
                        socioEmpleador.setConyugue(personaConyugueOriginal);
                    }
                } else {
                    // al llegar el conyugue nulo se asume que nunca existió o
                    // que
                    // se debe eliminar la relación con la persona conyugue
                    socioEmpleador.setConyugue(null);
                }

                socioEmpleador.setIdEmpleador(idEmpleador);
                // se valida si exite la relación de la persona como socio del
                // empleador
                try {
                    SocioEmpleador socioEmpleadorExitente = entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOCIOS_EMPLEADOR_POR_EMPLEADOR_PERSONA, SocioEmpleador.class)
                            .setParameter("idEmpleador", idEmpleador).setParameter("idPersona", socioEmpleador.getPersona().getIdPersona())
                            .getSingleResult();

                    // ya existe un socio para la llave empleador y persona, se
                    // verifa que se esté modificando el mismo socio
                    // Se indica el identificador del socio para actulizarlo
                    if (socioEmpleadorExitente != null) {
                        socioEmpleador.setIdSocioEmpleador(socioEmpleadorExitente.getIdSocioEmpleador());
                    }
                    entityManager.merge(socioEmpleador);
                    idsSocios.add(socioEmpleador.getIdSocioEmpleador());

                } catch (NoResultException e) {
                    // como no existe la relacion de empleador y la persona, se
                    // verifica si se debe modificar o crear el socios
                    if (socioEmpleador.getIdSocioEmpleador() == null) {
                        entityManager.persist(socioEmpleador);
                    } else {
                        entityManager.merge(socioEmpleador);
                    }
                    idsSocios.add(socioEmpleador.getIdSocioEmpleador());
                } catch (NonUniqueResultException e) {
                    logger.error("Existe más de un empleador", e);
                    logger.debug(
                            "Finaliza gestionarSociosEmpleador(Long, List<SocioEmpleador>): Existe más de un registro para el empleador y persona");
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO + "\n" + e.getMessage());
                }
            }
        }

        if (!idsSocios.isEmpty()) {
            // Se eliminan los socios que no estén contenidos en la lista
            // recibida
            // por parámetro borrando la relación para no eliminar datos.
            List<SocioEmpleador> sociosExistentes = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ELIMINAR_SOCIOS_EMPLEADOR_NO_PRESENTES, SocioEmpleador.class)
                    .setParameter("idEmpleador", idEmpleador).setParameter("idsSocios", idsSocios).getResultList();

            for (SocioEmpleador socioEmpleador : sociosExistentes) {
                entityManager.merge(socioEmpleador);
                socioEmpleador.setIdEmpleador(null);
            }
        }

        logger.debug("Finaliza gestionarSociosEmpleador(Long, List<SocioEmpleador>)");
        return idsSocios;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarSocioEmpleador(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SocioEmpleador> consultarSocioEmpleador(Long idEmpleador) {
        return (List<SocioEmpleador>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_SOCIO_EMPLEADOR)
                .setParameter("idEmpleador", idEmpleador).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * crearRepresentanteLegal(java.lang.Long, java.lang.Boolean,
     * com.asopagos.entidades.ccf.personas.Persona)
     */
    @Override
    public Long crearRepresentanteLegal(Long idEmpleador, Boolean titular, Persona representante) {
        logger.debug("Inicia crearRepresentanteLegal(Long, Boolean, Persona)");

        // validación de datos obligatorios
        if (idEmpleador == null || representante == null || representante.getTipoIdentificacion() == null
                || representante.getNumeroIdentificacion() == null) {
            logger.debug("crearRepresentanteLegal(Long, Boolean, Persona): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        if (Boolean.TRUE.equals(titular)) {
            // se validan datos obligatorios adicionales al titular
            if (representante.getPrimerNombre() == null || representante.getPrimerApellido() == null) {
                logger.debug("crearRepresentanteLegal(Long, Boolean, Persona): No parámetros no válidos");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }
        }

        try {

            // se consulta de que el empleador este registrados
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID)
                    .setParameter("idEmpleador", idEmpleador).getSingleResult();

            // la ubicación para una persona con el rol de representante legal
            // debe ser totalmente independiente de la entidad persona por ende
            // se debe usar la ubicación propia de la entidad empresa para el
            // representante legal.
            //
            // Se asegura la integridad de los identificadores de la
            // ubicación y de la persona no queden relacionados, ya que se trata
            // de una identificación del rol representante legal y no de la
            // persona
            Long idUbicacionRepresentante = null;
            if (representante.getUbicacionPrincipal() != null) {
                try {
                    Ubicacion ubicacion = representante.getUbicacionPrincipal();
                    ubicacion.setIdUbicacion(null);
                    if (Boolean.TRUE.equals(titular)) {
                        if (empleador.getEmpresa().getIdUbicacionRepresentanteLegal() != null) {
                            ubicacion.setIdUbicacion(empleador.getEmpresa().getIdUbicacionRepresentanteLegal());
                        }
                    } else {
                        if (empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente() != null) {
                            ubicacion.setIdUbicacion(empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente());
                        }
                    }
                    if (ubicacion.getIdUbicacion() == null) {
                        entityManager.persist(ubicacion);
                    } else {
                        entityManager.merge(ubicacion);
                    }
                    idUbicacionRepresentante = ubicacion.getIdUbicacion();
                } catch (Exception e) {
                    logger.error(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO + " (ubicación del representante)", e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO, e);
                }
            }

            // consulta el representante en el sistema
            Persona persona = consultarPersona(representante.getNumeroIdentificacion(), representante.getTipoIdentificacion());

            // Si la persona existe y es titular actualizar los nombres si
            // cambiaron en el back
            if (persona.getIdPersona() != null && titular) {
                persona.setPrimerNombre(representante.getPrimerNombre());
                persona.setPrimerApellido(representante.getPrimerApellido());
                persona.setSegundoNombre(representante.getSegundoNombre());
                persona.setSegundoApellido(representante.getSegundoApellido());
                entityManager.merge(persona);
            }

            Long idRepresentante;
            // valida si la persona no existe para persistir la información
            if (persona.getIdPersona() == null) {
                // se elimina la referencia a la ubicación de persona, pues,
                // se trata de una ubicación del rol representante legal
                representante.setUbicacionPrincipal(null);
                representante.setIdPersona(null);
                entityManager.persist(representante);
                idRepresentante = representante.getIdPersona();
                logger.info("crearRepresentanteLegal if idRepresentante = " + idRepresentante);
                persona = consultarPersona(representante.getNumeroIdentificacion(), representante.getTipoIdentificacion());
                idRepresentante = persona.getIdPersona();
                
            } else {
                // Si ya existe la persona, asigna la persona
                idRepresentante = persona.getIdPersona();
            }
            // evalua titular para asociar el tipo de representante al
            // empleador
            if (titular) {
                Empresa empresaUpdate = entityManager.getReference(Empresa.class, empleador.getEmpresa().getIdEmpresa());
                empresaUpdate.setIdPersonaRepresentanteLegal(idRepresentante);
                empresaUpdate.setIdUbicacionRepresentanteLegal(idUbicacionRepresentante);
                entityManager.merge(empresaUpdate);
            } else {
                Empresa empresaUpdate = entityManager.getReference(Empresa.class, empleador.getEmpresa().getIdEmpresa());
                empresaUpdate.setIdPersonaRepresentanteLegalSuplente(idRepresentante);
                empresaUpdate.setIdUbicacionRepresentanteLegalSuplente(idUbicacionRepresentante);
                entityManager.merge(empresaUpdate);
            }

            return idRepresentante;

        } catch (NoResultException e) {
            logger.error("No existe el empleador a asociado a la sucursal", e);
            logger.debug("Finaliza crearRepresentanteLegal(Long, Boolean, Persona): No existe el empleador a asociado a la sucursal");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO + "\n" + e.getMessage());
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarRolesContactoEmpleador(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RolContactoEmpleador> consultarRolesContactoEmpleador(Long idEmpleador) {

        logger.debug("Inicia consultarRolesContactoEmpleador(Long)");
        Empleador empleador = entityManager.find(Empleador.class, idEmpleador);

        // verifica la existencia del empleador
        if (empleador != null) {
            // Se consultan las sucursales
            List<RolContactoEmpleador> listRolesContacto = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_ROL_CONTACTO_EMPLEADOR)
                    .setParameter("idEmpleador", empleador.getIdEmpleador()).getResultList();

            logger.debug("Finaliza consultarRolesContactoEmpleador(Long)");
            return listRolesContacto;
        }
        logger.debug("Finaliza consultarRolesContactoEmpleador(Long)");
        return Collections.EMPTY_LIST;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarRepresentantesLegalesEmpleador(java.lang.Long,
     * java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Persona consultarRepresentantesLegalesEmpleador(Long idEmpleador, Boolean titular) {

        logger.debug("Inicia consultarRepresentantesLegalesEmpleador(Long, Boolean)");

        String consulta;
        String consultaUbicacion;
        Persona persona;
        Ubicacion ubicacion = null;

        if (titular == true) {
            consulta = NamedQueriesConstants.CONSULTAR_REPRESENTANTE_LEGAL_EMPLEADOR;
            consultaUbicacion = NamedQueriesConstants.CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_EMPLEADOR;
        } else {
            consulta = NamedQueriesConstants.CONSULTAR_REPRESENTANTE_LEGAL_SUPL_EMPLEADOR;
            consultaUbicacion = NamedQueriesConstants.CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_SUPL_EMPLEADOR;
        }

        try {
            persona = (Persona) entityManager.createNamedQuery(consulta).setParameter("idEmpleador", idEmpleador).getSingleResult();
            entityManager.detach(persona);
        } catch (NoResultException nre) {
            logger.debug(
                    "Finaliza consultarRepresentantesLegalesEmpleador(Long, Boolean): No existe datos segun los criterios de búsqueda");
            return null;
        }

        try {
            ubicacion = (Ubicacion) entityManager.createNamedQuery(consultaUbicacion).setParameter("idEmpleador", idEmpleador)
                    .getSingleResult();
            entityManager.detach(ubicacion);
        } catch (NoResultException nre) {
            logger.warn("El empleador consultado no tiene una ubicación asociada. Id consultado: " + idEmpleador);
        }

        persona.setUbicacionPrincipal(ubicacion);
        logger.debug("Finaliza consultarRepresentantesLegalesEmpleador(Long, Boolean)");
        return persona;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * actualizarRepresentanteLegalEmpleador(java.lang.Long, java.lang.Boolean,
     * com.asopagos.entidades.ccf.personas.Persona)
     */
    @Override
    public void actualizarRepresentanteLegalEmpleador(Long idEmpleador, Boolean titular, Persona representante) {
        logger.debug("Inicia actualizarRepresentanteLegalEmpleador(Long, Boolean, Persona)");
        // se valida escenario donde se quiera eliminar la relación de un
        // empleador con una persona asociada cómo representante legal suplente
        if (idEmpleador != null && representante.getNumeroIdentificacion() == null && representante.getTipoIdentificacion() == null
                && Boolean.FALSE.equals(titular)) {
            try {
                Empleador empleador = entityManager.find(Empleador.class, idEmpleador);
                if (empleador == null) {
                    throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
                }
                empleador.getEmpresa().setIdPersonaRepresentanteLegalSuplente(null);
                if (empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente() != null) {
                    Long idUbicacion = empleador.getEmpresa().getIdUbicacionRepresentanteLegalSuplente();
                    empleador.getEmpresa().setIdUbicacionRepresentanteLegalSuplente(null);
                    Ubicacion ubicacion = entityManager.find(Ubicacion.class, idUbicacion);
                    if (ubicacion != null) {
                        entityManager.remove(ubicacion);
                    }
                } 
            } catch (IllegalArgumentException iae) {
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }
        } else {
            if(Boolean.TRUE.equals(titular)){
                logger.info("Finaliza actualizarRepresentanteLegalEmpleador(Long, Boolean, Persona): crearRepresentanteLegal ");
                crearRepresentanteLegal(idEmpleador, titular, representante);
            }
        }
        logger.debug("Finaliza actualizarRepresentanteLegalEmpleador(Long, Boolean, Persona)");
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * actualizarEstadoEmpleador(java.lang.Long,
     * com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum)
     */
    @Override
    public void actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum nuevoEstado) {

        logger.debug("Inicia actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum)");

        // se valida el parametro que identifica el empleador
        if (idEmpleador == null || nuevoEstado == null) {
            logger.debug("Finaliza actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        // verifica la existencia del empleador
        Empleador empleador = (Empleador) consultarEmpleador(idEmpleador);
        if (empleador != null) {
            empleador.setFechaCambioEstadoAfiliacion(Calendar.getInstance().getTime());
            // si el estado
            if (empleador.getEstadoEmpleador().equals(EstadoEmpleadorEnum.INACTIVO)) {
                if (nuevoEstado.equals(EstadoEmpleadorEnum.ACTIVO)) {
                    // Quitar marcas de fecha de retiro y motivo de
                    // desafiliacion siempre que se active
                    empleador.setFechaRetiro(null);
                    empleador.setMotivoDesafiliacion(null);
                    empleador.setEstadoEmpleador(nuevoEstado);
                    logger.info(empleador.getFechaRetiro());
                    entityManager.merge(empleador);
                }
            } else {
                if (nuevoEstado.equals(EstadoEmpleadorEnum.ACTIVO)) {
                    // Quitar marcas de fecha de retiro y motivo de
                    // desafiliacion siempre que se active
                    logger.info(empleador.getFechaRetiro());
                    empleador.setFechaRetiro(null);
                    empleador.setMotivoDesafiliacion(null);
                }
                empleador.setEstadoEmpleador(nuevoEstado);
                entityManager.merge(empleador);
            }
            logger.debug("Finaliza actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum)");
        } else {
            logger.debug("Finaliza actualizarEstadoEmpleador(Long idEmpleador, EstadoEmpleadorEnum): Empleador no encontrado");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#consultarEmpleador(
     * java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Empleador consultarEmpleador(Long idEmpleador) {
        logger.debug("Inicia consultarEmpleador(Long)");

        // se valida el parametro que identifica el empleador
        if (idEmpleador == null) {
            logger.debug("Finaliza consultarEmpleador(Long): No parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        try {
            // Consulta el empleador por id
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID)
                    .setParameter("idEmpleador", idEmpleador).getSingleResult();

            if(empleador != null) {
                try {
                    Date result = (Date) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ULTIMA_ACTUALIZACION_AFILIACION)
                    .setParameter("idEmp", idEmpleador).getSingleResult();
                    if(result != null) {
                        empleador.setFechaCambioEstadoAfiliacion(result);
                    }
                } catch (NoResultException nre) {
                    logger.debug("No se encuentra la fecha de última actualización de afiliación");
                }
            }

            List<ConsultarEstadoDTO> listConsulta = new ArrayList<>();
            ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
            paramsConsulta.setEntityManager(entityManager);
            paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
            paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listConsulta.add(paramsConsulta);

            List<EstadoDTO> listEstadod = EstadosUtils.consultarEstadoCaja(listConsulta);
            if (listEstadod.get(0).getEstado() != null) {
                empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(listEstadod.get(0).getEstado().toString()));
            } else {
                empleador.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
            }

            logger.debug("Finaliza consultarEmpleador(Long)");
            return empleador;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarEmpleador(Long): No existe el empleador a actualizar");
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#establecerResponsablesCajaCompensacion(java.lang.Long,
     * java.util.List)
     */
    @Override
    public List<Long> establecerResponsablesCajaCompensacion(Long idEmpleador, List<String> usuariosCajaCompensacion) {

        // Validar que el emplador existe
        List<Long> idsResponsablesEmpleador = new ArrayList<Long>();
        boolean primario = true;
        for (String usuario : usuariosCajaCompensacion) {
            AsesorResponsableEmpleador asesorEmpleador = new AsesorResponsableEmpleador();
            asesorEmpleador.setNombreUsuario(usuario);
            asesorEmpleador.setIdEmpleador(idEmpleador);
            asesorEmpleador.setPrimario(primario);
            entityManager.persist(asesorEmpleador);
            idsResponsablesEmpleador.add(asesorEmpleador.getIdAsesorResponsableEmpleador());
            primario = false;
        }
        return idsResponsablesEmpleador;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#actualizarResponsablesCajaCompensacion(java.lang.Long,
     * java.util.List)
     */
    @Override
    public void actualizarResponsablesCajaCompensacion(Long idEmpleador, List<String> usuariosCajaCompensacion) {
        boolean primario = true;
        for (String usuario : usuariosCajaCompensacion) {
            List<AsesorResponsableEmpleador> asesorUpdate = new ArrayList<AsesorResponsableEmpleador>();

            asesorUpdate.addAll(
                    entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTUALIZAR_RESPONSABLE_CAJA_COMPENSACION_EMPLEADOR)
                            .setParameter("idEmpleador", idEmpleador).setParameter("primario", primario).getResultList());

            for (AsesorResponsableEmpleador asesor : asesorUpdate) {
                asesor = entityManager.merge(asesor);
                asesor.setNombreUsuario(usuario);
            }

            // entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_RESPONSABLE_CAJA_COMPENSACION_EMPLEADOR)
            // .setParameter("idEmpleador",
            // idEmpleador).setParameter("nombreUsuario",
            // usuario).setParameter("primario", primario)
            // .executeUpdate();
            primario = false;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#consultarResponsablesCajaCompensacion(java.lang.Long)
     */
    @Override
    public List<String> consultarResponsablesCajaCompensacion(Long idEmpleador) {
        return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESPONSABLES_CAJA_COMPENSACION_EMPLEADOR)
                .setParameter("idEmpleador", idEmpleador).getResultList();
    }

    @Override
    public InformacionContactoDTO consultarInformacionContacto(Long idEmpleador) {
        InformacionContactoDTO icd = null;
        try {
            List<RolContactoEmpleador> ic = (List) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_CONTACTO)
                    .setParameter("idEmpleador", idEmpleador).getResultList();
            RolContactoEmpleador contacto = null;
            for (RolContactoEmpleador rolContactoEmpleador : ic) {
                if (rolContactoEmpleador.getTipoRolContactoEmpleador() == TipoRolContactoEnum.ROL_RESPONSABLE_AFILIACIONES) {
                    contacto = rolContactoEmpleador;
                }
            }
            icd = new InformacionContactoDTO();
            icd.setCargo(null);// TODO el cargo
            icd.setNumeroIdentificacion(contacto.getPersona().getNumeroIdentificacion());
            icd.setPrimerApellido(contacto.getPersona().getPrimerApellido());
            icd.setPrimerNombre(contacto.getPersona().getPrimerNombre());
            icd.setTipoIdentificacion(contacto.getPersona().getTipoIdentificacion());
            icd.setTelefonoFijo(contacto.getPersona().getUbicacionPrincipal().getTelefonoFijo());
            icd.setTelefonoCelular(contacto.getPersona().getUbicacionPrincipal().getTelefonoCelular());
            icd.setCorreoElectronico(contacto.getPersona().getUbicacionPrincipal().getEmail());
            return icd;
        } catch (NoResultException e) {
        }
        return icd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * guardarDatosTemporalesEmpleador(java.lang.Long, java.lang.String)
     */
    @Override
    public Long guardarDatosTemporalesEmpleador(Long idSolicitud, String jsonPayload, String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        if (idSolicitud != null && jsonPayload != null && !jsonPayload.equals("")) {
            DatoTemporalSolicitud datoTemporalSolicitud = buscarTemporales(idSolicitud);
            	 //Desarollo para optimizar el json payload, las variables en null las remueve del json Ajuste en RutinaGurdarDarosTemporalesPersonaRutine,SolicitudesBusiness,empleadoresBusines
            //  java.lang.reflect.Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
            //  Map<String, Object> data = new Gson().fromJson(jsonPayload, type); 
             
            //  for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext();) { 
            //      Map.Entry<String, Object> entry = it.next(); 
            //      if (entry.getValue() == null || entry.getValue() == "") { 
            //       it.remove(); 
            //      } else if (entry.getValue() instanceof ArrayList) { 
            //       if (((ArrayList<?>) entry.getValue()).isEmpty()) { 
            //        it.remove(); 
            //       } 
            //      } 
            //  } 
            //  jsonPayload = new GsonBuilder().create().toJson(data); 
             //Fin optimizacion JSON
            if (datoTemporalSolicitud != null) {
                if (jsonPayload.length() > 65535) {
                    String idArchivo = crearArchivo(jsonPayload);
                    datoTemporalSolicitud.setJsonPayload(idArchivo);
                    datoTemporalSolicitud.setCreoArchivo((short) 1);
                }else{
                    datoTemporalSolicitud.setJsonPayload(jsonPayload);
                    datoTemporalSolicitud.setCreoArchivo((short) 0);
                }
                entityManager.merge(datoTemporalSolicitud);
            } else {
                datoTemporalSolicitud = new DatoTemporalSolicitud();
                datoTemporalSolicitud.setSolicitud(idSolicitud);
                datoTemporalSolicitud.setNumeroIdentificacion(numeroIdentificacion);
                datoTemporalSolicitud.setTipoIdentificacion(tipoIdentificacion);
                if (jsonPayload.length() > 65535) {
                    String idArchivo = crearArchivo(jsonPayload);
                    datoTemporalSolicitud.setJsonPayload(idArchivo);
                    datoTemporalSolicitud.setCreoArchivo((short) 1);
                } else {
                    datoTemporalSolicitud.setJsonPayload(jsonPayload);
                    datoTemporalSolicitud.setCreoArchivo((short) 0);
                }
                entityManager.persist(datoTemporalSolicitud);
            }
            return datoTemporalSolicitud.getIdDatoTemporalSolicitud();
        } else {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
    }
    private String crearArchivo(String jsonPayload) {
        InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
        infoFile.setDataFile(jsonPayload.getBytes());
        infoFile.setFileType("text/plain");
        infoFile.setProcessName("guardarDatosTemporales");
        infoFile.setDescription("Archivo de datos temporales");
        infoFile.setDocName("datosTemporales.txt");
        infoFile.setFileName("datosTemporales.txt");
        String idArchivo = almacenarArchivo(infoFile);
        return idArchivo;
    }
    
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.info("Inicia almacenarArchivo(InformacionArchivoDTO infoFile)");
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();
        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        logger.info("Finaliza almacenarArchivo(InformacionArchivoDTO infoFile)");
        return idECM.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarDatosTemporalesEmpleador(java.lang.Long)
     */
    @Override
    public String consultarDatosTemporalesEmpleador(Long idSolicitud) {
        if (idSolicitud != null) {
            DatoTemporalSolicitud datoTemporalSolicitud = buscarTemporales(idSolicitud);
            if (datoTemporalSolicitud != null) {
                if( datoTemporalSolicitud.getCreoArchivo() != null && datoTemporalSolicitud.getCreoArchivo() == 1 ){
                    InformacionArchivoDTO archivo = obtenerArchivo(datoTemporalSolicitud.getJsonPayload());
                    if (archivo.getDataFile() != null &&  archivo.getDataFile().length > 0) {
                        try {
                            String jsonArchivo = new String(archivo.getDataFile(), "UTF-8");
                            //datoTemporalSolicitud.setJsonPayload(jsonArchivo);
                            return jsonArchivo;
                        } catch (UnsupportedEncodingException e) {
                        datoTemporalSolicitud.setJsonPayload(null); 
                    }
                    }else {
                        logger.warn("El archivo está vacío o es nulo");
                        datoTemporalSolicitud.setJsonPayload(null);
                    }
                }
                return datoTemporalSolicitud.getJsonPayload();
            } else {
                return null;
            }
        } else {
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
    }
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
     * Método que busca los datos temporales de la solicitud
     *
     * @return entidad que contiene los datos temporales
     */
    private DatoTemporalSolicitud buscarTemporales(Long idSolicitud) {
        Query q = entityManager.createNamedQuery("Empleador.consultar.datosTemporales").setParameter("idSolicitud", idSolicitud);
        DatoTemporalSolicitud datoTemporalSolicitud;
        try {
            datoTemporalSolicitud = (DatoTemporalSolicitud) q.getSingleResult();
        } catch (NoResultException e) {
            datoTemporalSolicitud = null;
        }
        return datoTemporalSolicitud;
    }

    /**
     * Método que consulta la clasificación de la última solicitud radicada
     *
     * @param idEmpleador identificador del empleador para realizar la consulta
     * @return ClasificacionEnum la clasificación de la ultima solicitud
     * radicada
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ClasificacionEnum consultarUltimaClasificacion(Long idEmpleador) {
        List<Object[]> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_CLASIFICACION)
                .setParameter("idEmpleador", idEmpleador).getResultList();

        // Se verifica si hay resultados
        if (solicitudes != null && !solicitudes.isEmpty()) {
            Object[] registro = solicitudes.get(NumerosEnterosConstants.CERO);
            Solicitud solicitud = (Solicitud) registro[NumerosEnterosConstants.CERO];

            return solicitud.getClasificacion();
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * actualizarEmpleadorNovedad(com.asopagos.entidades.ccf.personas.Empleador)
     */
    @Override
    public void actualizarDatosEmpleador(Empleador empleador) {
        try {
            logger.debug("Inicio de método actualizarDatosEmpleador(Empleador empleador)");
            entityManager.merge(empleador);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmpleadoresInactivar1429()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> consultarEmpleadoresInactivar1429() {
        try {
            logger.debug("Ingresa al método consultarEmpleadoresInactivar1429( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
            // Se obtiene el año actual.
            Calendar fechaActualSistema = Calendar.getInstance();
            Integer anioActualSistema = fechaActualSistema.get(Calendar.YEAR);

            // Consulta los empleadores que se deben inactivar masivamente para
            // asociarlos a la solicitud.
            List<Long> empleadoresInactivar = entityManager
                    .createNamedQuery(NamedQueriesConstants.NOVEDADES_EMPLEADOR_CONSULTAR_EMPLEADORES_INACTIVAR1429)
                    .setParameter("empBeneficioActivo", Boolean.TRUE).setParameter("anioActualSistema", anioActualSistema)
                    .setParameter("tipoBeneficio", TipoBeneficioEnum.LEY_1429).getResultList();

            logger.debug(
                    "Finaliza el mètodo consultarEmpleadoresInactivar1429( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
            return empleadoresInactivar;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmpleadoresInactivar1429()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> consultarEmpleadoresInactivar590() {
        try {
            logger.debug("Ingresa al método consultarEmpleadoresInactivar1429( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
            // Se obtiene la fecha actual
            Calendar fechaActualSistema = Calendar.getInstance();
            /*
             * Consulta los empleadores que se deben inactivar masivamente para
             * asociarlos a la solicitud.
             */
            List<Long> empleadoresInactivar = entityManager
                    .createNamedQuery(NamedQueriesConstants.NOVEDADES_EMPLEADOR_CONSULTAR_EMPLEADORES_INACTIVAR590)
                    .setParameter("empBeneficioActivo", Boolean.TRUE).setParameter("fechaActual", fechaActualSistema)
                    .setParameter("mesesAnio", NumerosEnterosConstants.UNO).setParameter("tipoBeneficio", TipoBeneficioEnum.LEY_590)
                    .getResultList();

            logger.debug(
                    "Finaliza el mètodo consultarEmpleadoresInactivar1429( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
            return empleadoresInactivar;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * almacenarSolicitudNovedadMasiva(java.lang.Long, java.util.List)
     */
    @Override
    public void almacenarSolicitudNovedadMasiva(Long idSolicitudNovedad, List<Long> idEmpleadores) {
        try {
            logger.debug("Ingresa al mètodo almacenarSolicitudNovedadMasiva( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");

            /*
             * Se persiste en Batch las solicitudesNovedadEmpleador
             * Configuración actual: hibernate.jdbc.batch_size value = 500
             */
            for (Long idEmpleador : idEmpleadores) {
                SolicitudNovedadEmpleador solicitudNovedadEmpleador = new SolicitudNovedadEmpleador();
                solicitudNovedadEmpleador.setIdEmpleador(idEmpleador);
                solicitudNovedadEmpleador.setIdSolicitudNovedad(idSolicitudNovedad);
                entityManager.persist(solicitudNovedadEmpleador);
            }

            logger.debug("Finaliza el mètodo almacenarSolicitudNovedadMasiva( Long idSolicitudNovedad, List<BigInteger> idsEmpleadores )");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * inactivarBeneficiosLey1429(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void inactivarBeneficios(TipoBeneficioEnum tipoBeneficio, List<Long> idEmpleadores) {
        logger.info("Ingresa al mètodo inactivarBeneficiosLey1429()");
        try {
            /*
             * Actualiza los Beneficios asociados a los Empleadores por Ley
             * 1429.
             */
            List<BeneficioEmpleador> beneficiosInactivar = entityManager
                    .createNamedQuery(NamedQueriesConstants.NOVEDADES_EMPLEADOR_CONSULTAR_BENEFICIOS_INACTIVAR)
                    .setParameter("idEmpleadores", idEmpleadores).setParameter("tipoBeneficio", tipoBeneficio).getResultList();
            /*
             * Se actualiza en batch los beneficios Configuración actual:
             * hibernate.jdbc.batch_size value = 500
             */
            for (BeneficioEmpleador beneficio : beneficiosInactivar) {
                beneficio.setBeneficioActivo(Boolean.FALSE);
                entityManager.merge(beneficio);
            }
            logger.debug("Finaliza al mètodo inactivarBeneficiosLey1429()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en crearSolicitudNovedad(NovedadDTO novedadDTO, UserDTO userDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmpleadoresInactivarCuentaWeb()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> consultarEmpleadoresInactivarCuentaWeb(List<UsuarioDTO> usuarios) {
        logger.debug("Ingresa al método consultarEmpleadoresInactivarCuentaWeb()");
        try {
            List<Long> empleadoresInactivar = new ArrayList<>();
            /*
             * Consulta los empleadores que se encuentran inactivos y tienen una
             * fecha de Retiro asociada
             */
            for (UsuarioDTO user : usuarios) {
                List<Empleador> empleado = (List<Empleador>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION)
                        .setParameter("tipoIdentificacion",
                                TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(user.getTipoIdentificacion()))
                        .setParameter("numeroIdentificacion", user.getNumIdentificacion()).getResultList();
                if (empleado != null && !empleado.isEmpty()) {
                    empleadoresInactivar.add(empleado.get(0).getIdEmpleador());
                }
            }
            logger.debug("Finaliza el método consultarEmpleadoresInactivarCuentaWeb()");
            return empleadoresInactivar;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el método consultarEmpleadoresInactivarCuentaWeb()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * inactivarCuentaWebEmpleadores(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<String> consultarNombreUsuarioEmpleadores(List<Long> idEmpleadores) {
        logger.debug("Inicia operación consultarNombreUsuarioEmpleadores(List<Long>)");
        try {
            /*
             * Consulta los datos de tipo y número de Identificación asociados a
             * los Empleadores.
             */
            List<Object[]> empleadoresRetirados = entityManager
                    .createNamedQuery(NamedQueriesConstants.NOVEDADES_EMPLEADOR_CONSULTAR_TIPO_NUMERO_IDENTIFICACION)
                    .setParameter("idEmpleadores", idEmpleadores).getResultList();
            List<String> userNames = new ArrayList<>();
            final String prefijoEmpleador = "emp_";
            if (empleadoresRetirados != null && !empleadoresRetirados.isEmpty()) {
                for (Object[] datosEmpleadores : empleadoresRetirados) {
                    String tipoIdentificacion = (String) datosEmpleadores[0];
                    String numeroIdentificacion = (String) datosEmpleadores[1];
                    /* Se arma el nombre de usuario de cada Empleador. */
                    String userName = prefijoEmpleador + tipoIdentificacion + "_" + numeroIdentificacion;
                    /*
                     * Se agrega a la lista de Nombres de Usuario cada
                     * Empleador.
                     */
                    userNames.add(userName);
                }
            }
            logger.debug("Finaliza operación consultarNombreUsuarioEmpleadores(List<Long>)");
            return userNames;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el método consultarNombreUsuarioEmpleadores(List<Long>)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarBeneficioEmpleador(java.lang.Long,
     * com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BeneficioEmpleadorModeloDTO consultarBeneficioEmpleador(Long idEmpleador, TipoBeneficioEnum tipoBeneficio) {
        logger.debug("Inicia operación consultarBeneficioEmpleador(Long, TipoBeneficioEnum)");
        BeneficioEmpleador beneficioEmpleador = new BeneficioEmpleador();
        try {
            List<Long> idEmpleadores = new ArrayList<>();
            idEmpleadores.add(idEmpleador);
            /* Consulta el BeneficioEmpleador. */
            beneficioEmpleador = (BeneficioEmpleador) entityManager
                    .createNamedQuery(NamedQueriesConstants.NOVEDADES_EMPLEADOR_CONSULTAR_BENEFICIOS_INACTIVAR)
                    .setParameter("idEmpleadores", idEmpleadores).setParameter("tipoBeneficio", tipoBeneficio).getSingleResult();
            BeneficioEmpleadorModeloDTO beneficioEmpleadorDTO = new BeneficioEmpleadorModeloDTO();
            beneficioEmpleadorDTO.convertToDTO(beneficioEmpleador);
            logger.debug("Finaliza operación consultarBeneficioEmpleador(Long, TipoBeneficioEnum)");
            return beneficioEmpleadorDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarBeneficioEmpleador(Long, TipoBeneficioEnum): No existe el BeneficioEmpleador");
            return null;
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el método consultarBeneficioEmpleador(Long, TipoBeneficioEnum)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * actualizarBeneficioEmpleador(com.asopagos.entidades.ccf.core.
     * BeneficioEmpleador)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void actualizarBeneficioEmpleador(BeneficioEmpleadorModeloDTO beneficioEmpleador) {
        logger.info("Inicia operación actualizarBeneficioEmpleador(BeneficioEmpleador)");
        try {
            BeneficioEmpleador beneficioEmpleadorEntity = beneficioEmpleador.convertToEntity();

            if (beneficioEmpleadorEntity.getIdBeneficioEmpleador() != null) {
                entityManager.merge(beneficioEmpleadorEntity);
            } else {
                /* Se consulta el Beneficio asociado. */
                if (beneficioEmpleador.getTipoBeneficio() != null) {
                    List<Beneficio> beneficioPorTipo = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIO_POR_TIPO)
                            .setParameter("tipoBeneficio", beneficioEmpleador.getTipoBeneficio()).getResultList();
                    if (beneficioPorTipo != null && !beneficioPorTipo.isEmpty()) {
                        Beneficio beneficioAsociado = beneficioPorTipo.get(0);
                        /* Se asocia al Beneficio. */
                        beneficioEmpleadorEntity.setIdBeneficio(beneficioAsociado.getIdBeneficio());
                    }
                    entityManager.persist(beneficioEmpleadorEntity);
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en actualizarBeneficioEmpleador(BeneficioEmpleador)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug("Finaliza operación actualizarBeneficioEmpleador(BeneficioEmpleador)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#consultarEmpleadorId(
     * java.lang.Long)
     */
    @Override
    public EmpleadorModeloDTO consultarEmpleadorId(Long idEmpleador) {
        logger.debug("Inicia consultarEmpleador(Long)");
        try {
            // Consulta el empleador por id
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID)
                    .setParameter("idEmpleador", idEmpleador).getSingleResult();

            logger.debug("Finaliza consultarEmpleador(Long)");
            EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();
            empleadorDTO.convertToDTO(empleador);
            return empleadorDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarEmpleador(Long): No existe el empleador a actualizar");
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#modificarEmpleador(
     * com.asopagos.dto.modelo.EmpleadorModeloDTO)
     */
    @Override
    public void modificarEmpleador(EmpleadorModeloDTO empleadorModeloDTO) {
        logger.info("Inicio de método actualizarEmpleadorId(EmpleadorModeloDTO empleador)");
        /*logger.debug("Inicio de método actualizarEmpleadorId(EmpleadorModeloDTO empleador)");
        if (empleadorModeloDTO.getIdEmpleador() != null) {
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID)
                    .setParameter("idEmpleador", empleadorModeloDTO.getIdEmpleador()).getSingleResult();
            empleadorModeloDTO.copyDTOToEntity(empleador);
            entityManager.merge(empleador);
        }
        logger.debug("Inicio de método actualizarEmpleadorId(EmpleadorModeloDTO empleador)");
         */
        ModificarEmpleadorRutine m = new ModificarEmpleadorRutine();
        m.modificarEmpleador(empleadorModeloDTO, entityManager);
        logger.info("FIN de método actualizarEmpleadorId(EmpleadorModeloDTO empleador)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmailEmpleadores(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> consultarEmailEmpleadores(List<Long> idEmpleadores) {
        logger.debug("Inicia operación consultarEmailEmpleadores(List<Long>)");
        List<String> emails = new ArrayList<>();
        try {
            emails = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMAIL_EMPLEADORES)
                    .setParameter("idEmpleadores", idEmpleadores).getResultList();
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarEmailEmpleadores(List<Long>)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug("Finaliza operación consultarEmailEmpleadores(List<Long>)");
        return emails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmpleadorTipoNumero(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    public EmpleadorModeloDTO consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug(
                "Inicio de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
        try {
            Empleador empleador = (Empleador) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
            EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();
            empleadorDTO.convertToDTO(empleador);
            logger.debug(
                    "Fin de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion)");
            return empleadorDTO;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.error("NO se encontró un empleador con ese tipo y número de identificación", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * verificarExisteEmpleadorAsociado(com.asopagos.dto.modelo.
     * PersonaModeloDTO)
     */
    @Override
    public EmpleadorModeloDTO verificarExisteEmpleadorAsociado(PersonaModeloDTO personaModeloDTO) {
        logger.debug("Inicia verificarExisteEmpleadorAsociado(PersonaModeloDTO)");
        try {
            VerificarExisteEmpleadorAsociadoRutine er = new VerificarExisteEmpleadorAsociadoRutine();
            return er.verificarExisteEmpleadorAsociado(personaModeloDTO, entityManager);
        } catch (NoResultException nre) {
            logger.debug("Finaliza verificarExisteEmpleadorAsociado(PersonaModeloDTO): parámetros no válidos.", nre);
            return null;
        } catch (NonUniqueResultException nure) {
            logger.error(
                    "Finaliza verificarExisteEmpleadorAsociado(PersonaModeloDTO): se encontró mas de un empleador asociado a la empresa dada.");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, nure);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarEmpleadoresDesafiliar()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> consultarEmpleadoresCeroTrabajadores() {
        logger.debug("Inicia consultarEmpleadoresDesafiliar()");
        List<Long> idEmpleadores = new ArrayList<>();
        try {
            List<Object[]> datosEmp = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ACTIVO_CERO_TRABAJADORES)
                    .setParameter("estadoEmpleador", EstadoEmpleadorEnum.ACTIVO.name()).getResultList();
            if (datosEmp != null && !datosEmp.isEmpty()) {
                Date fechaActual = new Date();
                /*
                 * Se obtiene el Tiempo de Reintegro asignado como parámetro del
                 * sistema.
                 */
                Long diasInativarEmpleador = new Long(
                        (String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_INACTIVAR_EMPLEADOR)) / (3600 * 24 * 1000);
                for (Object[] datoEmp : datosEmp) {
                    Long idEmpleador = ((BigInteger) datoEmp[0]).longValue();
                    Date fechaGestionDesafiliacion = (Date) datoEmp[1];
                    /*
                     * Se calculan los días transcurridos hasta la fecha actual
                     */
                    Long diasTranscurridos = (fechaActual.getTime() - fechaGestionDesafiliacion.getTime()) / (3600 * 24 * 1000);
                    /*
                     * Si ya cumple con el plazo de reintegro se debe inactivar
                     * la cuenta web
                     */
                    if (diasInativarEmpleador.equals(0L) || diasTranscurridos >= 0) {
                        idEmpleadores.add(idEmpleador);
                    }

                }

            }
            logger.debug("Finaliza consultarEmpleadoresDesafiliar()");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarEmpleadoresDesafiliar()");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return idEmpleadores;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#inactivarEmpleadores(
     * java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void inactivarEmpleadores(List<Long> idEmpleadores, MotivoDesafiliacionEnum motivoDesafiliacionEnum) {
        logger.debug("Inicia inactivarEmpleadores(List<Long>)");
        List<Empleador> empleadores = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_LISTA_ID)
                .setParameter("idEmpleadores", idEmpleadores).getResultList();
        if (empleadores != null && !empleadores.isEmpty()) {
            for (Empleador empleador : empleadores) {
                empleador = entityManager.merge(empleador);
                empleador.setEstadoEmpleador(EstadoEmpleadorEnum.INACTIVO);
                empleador.setFechaRetiro(Calendar.getInstance().getTime());
                if (motivoDesafiliacionEnum != null) {
                    empleador.setMotivoDesafiliacion(motivoDesafiliacionEnum);
                }
            }
        }
        logger.debug("Finaliza inactivarEmpleadores(List<Long>)");

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmpleadorModeloDTO> consultarEmpleadorEstadoCaja(UriInfo uriInfo, HttpServletResponse response,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String razonSocial, EstadoEmpleadorEnum estadoCaja,
            String textoFiltro) {
        logger.debug("Inicia buscarEmpleador(" + tipoIdentificacion + ", " + numeroIdentificacion + ", " + razonSocial + ", "
                + estadoCaja + ", " + textoFiltro + ")");
        // Se ajustan los filtros
        String filterRazonSocial = null;
        if (razonSocial != null) {
            filterRazonSocial = "%".concat(razonSocial).concat("%");
        }
        String filterText = null;
        if (textoFiltro != null) {
            filterText = "%".concat(textoFiltro).concat("%");
        }

        // Se consultan los empleadores
        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        queryBuilder.addParam("tipoIdentificacion", tipoIdentificacion.name());
        queryBuilder.addParam("numeroIdentificacion", numeroIdentificacion);
        queryBuilder.addParam("estadoEmpleadorCaja", estadoCaja.name());
        queryBuilder.addParam("razonSocial", filterRazonSocial);
        queryBuilder.addParam("textoFiltro", filterText);
        queryBuilder.addOrderByDefaultParam("-tipoIdentificacion");

        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_TIPO_NRO_DOC_ESTADO_CAJA_RAZON_SOCIAL,
                null);
        return query.getResultList();
    }

    /**
     * Obtiene la informacion de la empresa por medio del identificador de la
     * persona
     *
     * @param idPersona Identificador persona
     * @return Empresa
     */
    private Empresa obtenerEmpresaPorIdPersona(Long idPersona) {
        Empresa empresa = null;
        try {
            empresa = (Empresa) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_EMPRESA_POR_ID, Empresa.class)
                    .setParameter("idPersona", idPersona).getSingleResult();
        } catch (NoResultException e) {
            return empresa;
        }
        return empresa;
    }

    /**
     * <b>Descripción</b>Metodo que se encarga de actualizar la informacion de
     * un RolContactoEmpleador      <code>rolContactoEmpleador es la información a modificar, 
     * idRolContactoEmpleador es el id del rol a consultar
     * idEmpleador es el id del empleador que se le actualizará el rol</code>
     * <br/>
     *
     * @param rolContactoEmpleador información del rol a actualizar
     * @param idRolContactoEmpleador Id del rol que se actualizar
     * @param idEmpleador Id del empleador
     * @return retorna el identificador del RolContactoEmpleador actualizado
     */
    private Long actualizarRolContactoEmpleador(Long idEmpleador, RolContactoEmpleador rolContactoEmpleador) {
        logger.debug("Inicia actualizarRolContactoEmpleador(Long, RolContactoEmpleador)");
        
        // se consulta de que el empleador y persona esten registrados
        Empleador empleador = null;
        try {
            // Se consulta que el empleador
            empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_ID)
                    .setParameter("idEmpleador", idEmpleador).getSingleResult();
        } catch (NoResultException e) {
            empleador = null;
        }
        if (empleador != null) {
            // Se consulta el rol contacto
            RolContactoEmpleador rolEmpleadorExistente = null;
            if (rolContactoEmpleador.getIdRolContactoEmpleador() != null) {
                try {
                    rolEmpleadorExistente = (RolContactoEmpleador) entityManager
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_ROL_CONTACTO_POR_ID)
                            .setParameter("idRolContacto", rolContactoEmpleador.getIdRolContactoEmpleador()).getSingleResult();
                } catch (NoResultException e) {
                    rolEmpleadorExistente = null;
                }
            }
            RolContactoEmpleador rolContactoModificado = null;
            try {
                // Si existe un ronContacto se actualiza
                if (rolEmpleadorExistente != null) {
                    /*
                     * Se verifica el tipo de rol contacto existente en bd con
                     * el rol Contacto a actualizar
                     */
                    if (rolEmpleadorExistente.getTipoRolContactoEmpleador() != rolContactoEmpleador.getTipoRolContactoEmpleador()) {
                        throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_TIPO_ROL_CONTACTO_NO_COINCIDE);
                    }
                    // Ubicacion ubicacion = rolContactoEmpleador.getUbicacion();

                    // if (ubicacion != null && ubicacion.getIdUbicacion() == null) {
                    //     entityManager.persist(ubicacion);
                    // }
                    rolContactoModificado = modificarRolContacto(idEmpleador, rolContactoEmpleador);
                    rolContactoModificado.getUbicacion().setIdUbicacion(rolEmpleadorExistente.getUbicacion().getIdUbicacion());
                    rolContactoEmpleador.setUbicacion(entityManager.merge(rolContactoModificado.getUbicacion()));
                    entityManager.merge(rolContactoModificado);
                    return rolContactoModificado.getIdRolContactoEmpleador();
                } else {
                    // Crear/Modificar la ubicacion del rol
                    Ubicacion ubicacion = rolContactoEmpleador.getUbicacion();
                    // Si la ubicación no ha sido creada con anterioridad
                    if (ubicacion != null && ubicacion.getIdUbicacion() == null) {
                        entityManager.persist(ubicacion);
                    }
                    rolContactoModificado = modificarRolContacto(idEmpleador, rolContactoEmpleador);
                    rolContactoModificado.setIdEmpleador(empleador.getIdEmpleador());
                    entityManager.persist(rolContactoModificado);
                    logger.debug("Finaliza actualizarRolContactoEmpleador(Long,RolContactoEmpleador): Datos no encontrados");
                    return rolContactoModificado.getIdRolContactoEmpleador();
                }

            } catch (Exception e) {
                logger.debug(e);
                throw new TechnicalException(e);
            }
        } else {
            return null;
        }
    }

    /**
     * Método encargado de modificar un rol contacto
     *
     * @param idEmpleador, id del empleador a modificar
     * @param rolContactoEmpleador, rolContacto del empleador
     */
    @SuppressWarnings("unchecked")
    private RolContactoEmpleador modificarRolContacto(Long idEmpleador, RolContactoEmpleador rolContactoEmpleador) {
        logger.debug("modificarRolContacto 1");
        // consulta la persona asociada al rol
        Persona persona = consultarPersona(rolContactoEmpleador.getPersona().getNumeroIdentificacion(),
                rolContactoEmpleador.getPersona().getTipoIdentificacion());
        if (persona.getIdPersona() == null) {
            persona = rolContactoEmpleador.getPersona();
            persona.setIdPersona(null);
            entityManager.persist(persona);
            rolContactoEmpleador.setPersona(persona);
        } else {
            Persona rolPersona = rolContactoEmpleador.getPersona();
            rolPersona.setUbicacionPrincipal(entityManager.merge(rolContactoEmpleador.getUbicacion()));
            
            persona.setPrimerNombre(rolContactoEmpleador.getPersona().getPrimerNombre());
            persona.setSegundoNombre(rolContactoEmpleador.getPersona().getSegundoNombre());
            persona.setPrimerApellido(rolContactoEmpleador.getPersona().getPrimerApellido());
            persona.setSegundoApellido(rolContactoEmpleador.getPersona().getSegundoApellido());
            
            entityManager.merge(persona);
            rolContactoEmpleador.setPersona(persona);
        }
        // Si el rol viene con sucursales, actualiza
        if (rolContactoEmpleador.getSucursales() != null && !rolContactoEmpleador.getSucursales().isEmpty()) {
            List<SucursalEmpresa> sucursalesEmpresa = new ArrayList<>();
            // Se consultan las sucursales existentes para el empleador
            List<SucursalEmpresa> sucursalEmpresaExistente = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSAL_EMPLEADOR_ID).setParameter("idEmpleador", idEmpleador)
                    .getResultList();
            for (SucursalEmpresa sucursalNueva : rolContactoEmpleador.getSucursales()) {
                for (SucursalEmpresa sucursalExistente : sucursalEmpresaExistente) {
                    if (sucursalExistente.getIdSucursalEmpresa().equals(sucursalNueva.getIdSucursalEmpresa())) {
                        sucursalesEmpresa.add(sucursalExistente);
                        break;
                    }
                }
            }
            rolContactoEmpleador.setSucursales(sucursalesEmpresa);
        }
        return rolContactoEmpleador;
    }

    /**
     * Método encargado de modificar una sucursalEmpresa a partir de una ya
     * existente en base datos y una nueva
     *
     * @param suEmpExistente, sucursal Empresa Existente
     * @param suEmpNueva, sucursal Empresa nueva
     * @return retorna la sucursalEmpresa con la informacion nueva
     */
    private SucursalEmpresa modificarSucursalEmpresa(SucursalEmpresa suEmpExistente, SucursalEmpresa suEmpNueva) {
        if (suEmpNueva.getCodigo() != null) {
            suEmpExistente.setCodigo(suEmpNueva.getCodigo());
        }
        if (suEmpNueva.getNombre() != null) {
            suEmpExistente.setNombre(suEmpNueva.getNombre());
        }
        if (suEmpNueva.getUbicacion() != null) {
            suEmpExistente.setUbicacion(suEmpNueva.getUbicacion());
        }
        if (suEmpNueva.getCodigoCIIU() != null) {
            suEmpExistente.setCodigoCIIU(suEmpNueva.getCodigoCIIU());
        }
        if (suEmpNueva.getMedioDePagoSubsidioMonetario() != null) {
            suEmpExistente.setMedioDePagoSubsidioMonetario(suEmpNueva.getMedioDePagoSubsidioMonetario());
        }
        if (suEmpNueva.getEstadoSucursal() != null) {
            suEmpExistente.setEstadoSucursal(suEmpNueva.getEstadoSucursal());
        }
        return suEmpExistente;
    }

    @Override
    //	public void actualizarEstadoEmpleadorPorAportes(Long idAportante, EstadoEmpleadorEnum nuevoEstado) {
    public void actualizarEstadoEmpleadorPorAportes(ActivacionEmpleadorDTO datosReintegro) {
        String firmaServicio = "EmpleadoresBusiness.actualizarEstadoEmpleadorPorAportes(ActivacionEmpleadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ActualizarEstadoEmpleadorPorAportesRutine actualizarEstadoEmpleadorPorAportesRutine = new ActualizarEstadoEmpleadorPorAportesRutine();
        actualizarEstadoEmpleadorPorAportesRutine.actualizarEstadoEmpleadorPorAportes(datosReintegro, entityManager);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    @Override
    public EstadoDTO consultarEstadoCajaEmpleador(TipoIdentificacionEnum valorTI, String valorNI) throws IOException {
        logger.debug("Inicio servicio consultarEstadoCajaEmpleador(TipoIdentificacionEnum, String)");
        List<EstadoDTO> listEstadoEmpleador = null;
        EstadoDTO estadoAfiliadoCajaResult = null;
        EstadosUtils estado = new EstadosUtils();
        List<ConsultarEstadoDTO> listaEstados = new ArrayList<>();
        ConsultarEstadoDTO estados = new ConsultarEstadoDTO();

        try {
            estados.setEntityManager(entityManager);
            estados.setNumeroIdentificacion(valorNI);
            estados.setTipoIdentificacion(valorTI);
            estados.setTipoPersona(ConstantesComunes.EMPLEADORES);
            listaEstados.add(estados);
            listEstadoEmpleador = estado.consultarEstadoCaja(listaEstados);
            if (listEstadoEmpleador != null) {
                estadoAfiliadoCajaResult = listEstadoEmpleador.get(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Error Al Consultar El Estado: " + e.getMessage());
        }
        return estadoAfiliadoCajaResult;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#consultarUltimaClasificacionEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    public ClasificacionEnum consultarUltimaClasificacionEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicia consultarUltimaClasificacionEmpleador(TipoIdentificacionEnum,String)");
        try {
            Query q = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_CLASIFICACION_EMPLEADOR);
            q.setParameter("tipoIdentificacion", tipoIdentificacion);
            q.setParameter("numeroIdentificacion", numeroIdentificacion);
            q.setMaxResults(0);
            logger.debug("Finaliza consultarUltimaClasificacionEmpleador(TipoIdentificacionEnum,String)");
            return (ClasificacionEnum) q.getSingleResult();
        } catch (NoResultException ex) {
            logger.debug("Finaliza consultarUltimaClasificacionEmpleador(TipoIdentificacionEnum,String): sin resultado");
            return null;
        } catch (Exception e) {
            logger.debug("Finaliza consultarUltimaClasificacionEmpleador(TipoIdentificacionEnum,String)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarUbicacionNotificacionJudicial(java.lang.Long,
     * com.asopagos.enumeraciones.core.TipoUbicacionEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public UbicacionDTO consultarUbicacionEmpresa(Long idPersona, TipoUbicacionEnum tipoUbicacion) {
        logger.debug("Inicia consultarUbicacionRepresentanteLegal(Long)");
        try {
            UbicacionDTO ubicacionDTO = null;
            List<Object[]> datosUbicacion = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA)
                    .setParameter("idPersona", idPersona).setParameter("tipoUbicacion", tipoUbicacion.name()).getResultList();
            if (!datosUbicacion.isEmpty()) {
                ubicacionDTO = new UbicacionDTO();
                for (Object[] ubicacion : datosUbicacion) {
                    ubicacionDTO = ubicacionDTO.asignarDatosUbicacionDTO(ubicacion);
                }
            }
            logger.debug("Finaliza consultarUbicacionRepresentanteLegal(Long)");
            return ubicacionDTO;
        } catch (NoResultException ex) {
            logger.debug("Finaliza consultarUbicacionRepresentanteLegal(Long): sin resultado");
            return null;
        } catch (Exception e) {
            logger.debug("Finaliza consultarUbicacionRepresentanteLegal(Long)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * crearActualizarRepresentanteLegal(java.lang.Long, java.lang.Boolean,
     * com.asopagos.entidades.ccf.personas.Persona)
     */
    public Long crearActualizarRepresentanteLegal(Long idEmpresa, Boolean titular, PersonaModeloDTO representante) {

        try {
            // se consulta la empresa asociada.
            Empresa empresa = (Empresa) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_ID)
                    .setParameter("idEmpresa", idEmpresa).getSingleResult();

            // la ubicación para una persona con el rol de representante legal
            // debe ser totalmente independiente de la entidad persona por ende
            // se debe usar la ubicación propia de la entidad empresa para el
            // representante legal.
            //
            // Se asegura la integridad de los identificadores de la
            // ubicación y de la persona no queden relacionados, ya que se trata
            // de una identificación del rol representante legal y no de la
            // persona
            Long idUbicacionRepresentante = null;
            Persona personaRepresentante = representante.convertToPersonaEntity();
            UbicacionModeloDTO ubicacionRepresentante = representante.getUbicacionModeloDTO();
            if (ubicacionRepresentante != null) {
                try {
                    Ubicacion ubicacion = ubicacionRepresentante.convertToEntity();
                    ubicacion.setIdUbicacion(null);
                    if (Boolean.TRUE.equals(titular)) {
                        if (empresa.getIdUbicacionRepresentanteLegal() != null) {
                            ubicacion.setIdUbicacion(empresa.getIdUbicacionRepresentanteLegal());
                        }
                    } else {
                        if (empresa.getIdUbicacionRepresentanteLegalSuplente() != null) {
                            ubicacion.setIdUbicacion(empresa.getIdUbicacionRepresentanteLegalSuplente());
                        }
                    }
                    if (ubicacion.getIdUbicacion() == null) {
                        entityManager.persist(ubicacion);
                    } else {
                        entityManager.merge(ubicacion);
                    }
                    idUbicacionRepresentante = ubicacion.getIdUbicacion();
                } catch (Exception e) {
                    logger.error(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO + " (ubicación del representante)", e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_ACTUALIZAR_RECURSO, e);
                }
            }

            // consulta el representante en el sistema
            Persona persona = consultarPersona(representante.getNumeroIdentificacion(), representante.getTipoIdentificacion());

            Long idRepresentante;
            // valida si la persona no existe para persistir la información
            if (persona.getIdPersona() == null) {
                // se elimina la referencia a la ubicación de persona, pues,
                // se trata de una ubicación del rol representante legal
                personaRepresentante.setUbicacionPrincipal(null);
                personaRepresentante.setIdPersona(null);
                entityManager.persist(personaRepresentante);
                idRepresentante = personaRepresentante.getIdPersona();
            } else {
                // Si ya existe la persona, asigna la persona
                idRepresentante = persona.getIdPersona();
            }

            // evalua titular para asociar el tipo de representante al
            // empleador
            if (Boolean.TRUE.equals(titular)) {
                Empresa empresaUpdate = entityManager.getReference(Empresa.class, empresa.getIdEmpresa());
                empresaUpdate.setIdPersonaRepresentanteLegal(idRepresentante);
                empresaUpdate.setIdUbicacionRepresentanteLegal(idUbicacionRepresentante);
                entityManager.merge(empresaUpdate);
            } else {
                Empresa empresaUpdate = entityManager.getReference(Empresa.class, empresa.getIdEmpresa());
                empresaUpdate.setIdPersonaRepresentanteLegalSuplente(idRepresentante);
                empresaUpdate.setIdUbicacionRepresentanteLegalSuplente(idUbicacionRepresentante);
                entityManager.merge(empresaUpdate);
            }

            logger.debug("Finaliza crearRepresentanteLegal(Long, Boolean, Persona)");

            return idRepresentante;

        } catch (NoResultException e) {
            logger.error("No existe el empleador a asociado a la sucursal", e);
            logger.debug("Finaliza crearRepresentanteLegal(Long, Boolean, Persona): No existe el empleador a asociado a la sucursal");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO + "\n" + e.getMessage());
        } catch (NonUniqueResultException e) {
            logger.error("Existe más de un empleador", e);
            logger.debug("Finaliza ccrearRepresentanteLegal(Long, Boolean, Persona): Existe más de un empleador");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO + "\n" + e.getMessage());
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#guardarDatosEmpleador(com.asopagos.dto.EmpleadorDTO)
     */
    @Override
    public void guardarDatosEmpleador(EmpleadorModeloDTO empleadorDTO) {
        /* Se convierte a entidad */
        String firmaMetodo = "guardarDatosEmpleador(EmpleadorModeloDTO empleadorDTO)";
        logger.debug("Inicia metodo " + firmaMetodo);
        try {
            Empleador empleador = empleadorDTO.convertToEntity();
            if (empleador.getIdEmpleador() != null) {
                /* Se actualiza */
                entityManager.merge(empleador);
            } else {
                /* Se guarda */
                entityManager.persist(empleador);
            }
        } catch (Exception e) {
            logger.error("Error en metodo " + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza metodo " + firmaMetodo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarUbicacionEmpresaPorTipo(java.lang.Long, java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UbicacionDTO> consultarUbicacionEmpresaPorTipo(Long idPersona, List<TipoUbicacionEnum> tipoUbicaciones) {
        logger.debug("Inicia consultarUbicacionEmpresaPorTipo(Long,List<TipoUbicacionEnum>)");
        try {
            List<UbicacionDTO> lstUbicaciones = new ArrayList<>();
            if (tipoUbicaciones != null && !tipoUbicaciones.isEmpty()) {
                lstUbicaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_EMPRESA_POR_LISTA_TIPO_UBICACION)
                        .setParameter("idPersona", idPersona).setParameter("tipoUbicacion", tipoUbicaciones).getResultList();
            }
            logger.debug("Finaliza consultarUbicacionEmpresaPorTipo(Long,List<TipoUbicacionEnum>)");
            return !lstUbicaciones.isEmpty() ? lstUbicaciones : null;
        } catch (Exception e) {
            logger.debug("Finaliza consultarUbicacionEmpresaPorTipo(Long,List<TipoUbicacionEnum>)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#
     * consultarUbicacionRolContactoEmpleador(java.lang.Long,
     * com.asopagos.enumeraciones.personas.TipoRolContactoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public UbicacionDTO consultarUbicacionRolContactoEmpleador(Long idPersona, TipoRolContactoEnum tipoRolContactoEmpleador) {
        logger.debug("Inicia consultarUbicacionRolContactoEmpleador(Long,TipoRolContactoEnum)");
        UbicacionDTO ubicacionDTO = null;
        try {
            Ubicacion ubicacion = (Ubicacion) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_UBICACION_ROL_CONTACTO_EMPLEADOR).setParameter("idPersona", idPersona)
                    .setParameter("tipoRolContactoEmpleador", tipoRolContactoEmpleador).getSingleResult();
            ubicacionDTO = ubicacionDTO.obtenerUbicacionDTO(ubicacion);
            logger.debug("Finaliza consultarUbicacionRolContactoEmpleador(Long,TipoRolContactoEnum)");
        } catch (NoResultException e) {
            ubicacionDTO = null;
        } catch (Exception e) {
            logger.debug("Finaliza consultarUbicacionRolContactoEmpleador(Long,TipoRolContactoEnum)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return ubicacionDTO;
    }

    /**
     * Método que consulta la clasificación de la última solicitud radicada
     *
     * @param idEmpleador identificador del empleador para realizar la consulta
     * @return ClasificacionEnum la clasificación de la ultima solicitud
     * radicada
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudDTO consultarUltimaSolicitudEmpleador(Long idEmpleador) {
        logger.debug("Inicia consultarUltimaSolicitudEmpleador(Long)");
        try {
            SolicitudDTO solicitudConsulta = null;
            List<Object[]> solicitudes = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMA_CLASIFICACION)
                    .setParameter("idEmpleador", idEmpleador).getResultList();
            // Se verifica si hay resultados
            if (solicitudes != null && !solicitudes.isEmpty()) {
                Object[] registro = solicitudes.get(NumerosEnterosConstants.CERO);
                Solicitud solicitud = (Solicitud) registro[NumerosEnterosConstants.CERO];
                solicitudConsulta = new SolicitudDTO(solicitud);
            }
            return solicitudConsulta;
        } catch (Exception e) {
            logger.debug("Finaliza consultarUltimaSolicitudEmpleador(Long)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que consulta la clasificación de la última solicitud radicada
     *
     * @param idEmpleador identificador del empleador para realizar la consulta
     * @return ClasificacionEnum la clasificación de la ultima solicitud
     * radicada
     */
    @Override
    public void anularSolictudesEmpleador(List<Long> idsEmpleadores) {

        logger.debug("Inicia anularSolictudesEmpleador(List<Long> idsEmpleadores)");
        for (Long idEmpleador : idsEmpleadores) {
            SolicitudDTO ultimaSolicitud = consultarUltimaSolicitudEmpleador(idEmpleador);
            if (ultimaSolicitud != null) {
                ultimaSolicitud.setAnulada(true);
                actualizarSolicitudAfiliacion(ultimaSolicitud.getIdSolicitud(), ultimaSolicitud);
            }
        }
        logger.debug("Finaliza anularSolictudesEmpleador(List<Long> idsEmpleadores)");
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.afiliaciones.personas.service.AfiliacionPersonasService#
     * actualizarSolicitudAfiliacionPersona(java.lang.Long,
     * com.asopagos.dto.SolicitudDTO)
     */
    @Override
    public void actualizarSolicitudAfiliacion(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
        try {
            logger.debug("Inicia actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
            Solicitud solicitud = entityManager.find(Solicitud.class, idSolicitudGlobal);
            if (solicitud != null) {
                if (solicitudDTO.getCanalRecepcion() != null && !solicitudDTO.getCanalRecepcion().equals("")) {
                    solicitud.setCanalRecepcion(solicitudDTO.getCanalRecepcion());
                }
                if (solicitudDTO.getIdInstanciaProceso() != null && !solicitudDTO.getIdInstanciaProceso().isEmpty()) {
                    solicitud.setIdInstanciaProceso(solicitudDTO.getIdInstanciaProceso());
                }
                if (solicitudDTO.getEstadoDocumentacion() != null && !solicitudDTO.getEstadoDocumentacion().equals("")) {
                    solicitud.setEstadoDocumentacion(solicitudDTO.getEstadoDocumentacion());
                }
                if (solicitudDTO.getMetodoEnvio() != null && !solicitudDTO.getMetodoEnvio().equals("")) {
                    solicitud.setMetodoEnvio(solicitudDTO.getMetodoEnvio());
                }
                if (solicitudDTO.getIdCajaCorrespondencia() != null && !solicitudDTO.getIdCajaCorrespondencia().equals("")) {
                    solicitud.setIdCajaCorrespondencia(solicitudDTO.getIdCajaCorrespondencia());
                }
                if (solicitudDTO.getTipoTransaccion() != null && !solicitudDTO.getTipoTransaccion().equals("")) {
                    solicitud.setTipoTransaccion(solicitudDTO.getTipoTransaccion());
                }
                if (solicitudDTO.getClasificacion() != null && !solicitudDTO.getClasificacion().equals("")) {
                    solicitud.setClasificacion(solicitudDTO.getClasificacion());
                }
                if (solicitudDTO.getTipoRadicacion() != null && !solicitudDTO.getTipoRadicacion().equals("")) {
                    solicitud.setTipoRadicacion(solicitudDTO.getTipoRadicacion());
                }
                if (solicitudDTO.getNumeroRadicacion() != null && !solicitudDTO.getNumeroRadicacion().isEmpty()) {
                    solicitud.setNumeroRadicacion(solicitudDTO.getNumeroRadicacion());
                }
                if (solicitudDTO.getUsuarioRadicacion() != null && !solicitudDTO.getUsuarioRadicacion().isEmpty()) {
                    solicitud.setUsuarioRadicacion(solicitudDTO.getUsuarioRadicacion());
                }
                if (solicitudDTO.getFechaRadicacion() != null && !solicitudDTO.getFechaRadicacion().equals("")) {
                    solicitud.setFechaRadicacion(solicitudDTO.getFechaRadicacion());
                }
                if (solicitudDTO.getCiudadSedeRadicacion() != null && !solicitudDTO.getCiudadSedeRadicacion().isEmpty()) {
                    solicitud.setCiudadSedeRadicacion(solicitudDTO.getCiudadSedeRadicacion());
                }
                if (solicitudDTO.getDestinatario() != null && !solicitudDTO.getDestinatario().isEmpty()) {
                    solicitud.setDestinatario(solicitudDTO.getDestinatario());
                }
                if (solicitudDTO.getSedeDestinatario() != null && !solicitudDTO.getSedeDestinatario().isEmpty()) {
                    solicitud.setSedeDestinatario(solicitudDTO.getSedeDestinatario());
                }
                if (solicitudDTO.getFechaCreacion() != null && !solicitudDTO.getFechaCreacion().equals("")) {
                    solicitud.setFechaCreacion(solicitudDTO.getFechaCreacion());
                }
                if (solicitudDTO.getObservacion() != null && !solicitudDTO.getObservacion().isEmpty()) {
                    if (solicitud.getObservacion() != null && solicitud.getObservacion().isEmpty()) {
                        solicitud.setObservacion(solicitud.getObservacion().concat(solicitudDTO.getObservacion()));
                    } else {
                        solicitud.setObservacion(solicitudDTO.getObservacion());
                    }

                }
                if (solicitudDTO.getCargaAfiliacionMultiple() != null && !solicitudDTO.getCargaAfiliacionMultiple().equals("")) {
                    solicitud.setCargaAfiliacionMultiple(solicitudDTO.getCargaAfiliacionMultiple());
                }

                if (solicitudDTO.getAnulada() != null) {
                    solicitud.setAnulada(solicitudDTO.getAnulada());
                }
                entityManager.merge(solicitud);
            }
            logger.debug("Finaliza actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
        } catch (Exception e) {
            logger.error("No es posible actualizar la solicitud de afiliacion persona", e);
            logger.debug("Finaliza actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#obtenerBeneficioEmpleador(java.lang.Long)
     */
    @Override
    public TipoBeneficioEnum obtenerBeneficioEmpleador(Long idEmpleador) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerBeneficioEmpleador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoBeneficioEnum tipoBeneficio = null;
        try {
            tipoBeneficio = (TipoBeneficioEnum) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIO_EMPLEADOR)
                    .setParameter("idEmpleador", idEmpleador).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return tipoBeneficio;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#obtenerBeneficiosEmpleadorTab(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<BeneficioEmpleadorModeloDTO> obtenerBeneficiosEmpleadorTab(Long idEmpleador) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerBeneficioEmpleador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            List<BeneficioEmpleadorModeloDTO> listBeneficios = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIOS_EMPLEADOR, BeneficioEmpleadorModeloDTO.class)
                    .setParameter("idEmpleador", idEmpleador).getResultList();

            for (BeneficioEmpleadorModeloDTO beneficio : listBeneficios) {
                Calendar cal = Calendar.getInstance();
                Date fechaFinBeneficio = new Date(beneficio.getFechaBeneficioFin());
                cal.setTime(fechaFinBeneficio);
                beneficio.setAnioBeneficio(new Short((short) cal.get(Calendar.YEAR)));
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return listBeneficios;

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#obtenerValoresAdicionalesCabeceraVista360(java.lang.Long)
     */
    @Override
    public EmpleadorModeloDTO obtenerValoresAdicionalesCabeceraVista360(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerBeneficioEmpleador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        EmpleadorModeloDTO empleador = null;
        try {
            empleador = (EmpleadorModeloDTO) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLES_ADICIONALES_CABECERA_VISTA360)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();

            if (empleador != null) {
                List<Object[]> datosRestantes = (List<Object[]>) entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLES_ADICIONALES_RESTANTES_CABECERA_VISTA360)
                        .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                        .setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

                if (datosRestantes != null && !datosRestantes.isEmpty()) {
                    empleador.setEstadoSolicitud(datosRestantes.get(0)[0] != null
                            ? EstadoSolicitudAfiliacionEmpleadorEnum.valueOf(datosRestantes.get(0)[0].toString()) : null);
                    empleador.setClasificacion(
                            datosRestantes.get(0)[1] != null ? ClasificacionEnum.valueOf(datosRestantes.get(0)[1].toString()) : null);

                    empleador.setTrasladoCajasCompensacion(datosRestantes.get(0)[2] != null ? Boolean.parseBoolean(datosRestantes.get(0)[2].toString()) : null);
                }
            }

            // Establecer el tipo de pagador
            if (empleador.getEntidadPagadora() != null && empleador.getEntidadPagadora()) {
                empleador.setTipoPagador("Aportante y Pagador");
            } else if (empleador.getEntidadPagadora() != null && !empleador.getEntidadPagadora()) {
                empleador.setTipoPagador("Pagador");
            } else {
                empleador.setTipoPagador("Aportante");
            }

        } catch (NoResultException e) {
            EmpleadorModeloDTO empleadorEntidadPagadora = new EmpleadorModeloDTO();
            try {
                Boolean entidadPagadora = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ES_ENTIDAD_PAGADORA, Boolean.class)
                        .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                        .getSingleResult();

                if (entidadPagadora != null && entidadPagadora) {
                    empleadorEntidadPagadora.setTipoPagador("Aportante y Pagador");
                } else if (entidadPagadora != null && !entidadPagadora) {
                    empleadorEntidadPagadora.setTipoPagador("Pagador");
                } else {
                    empleadorEntidadPagadora.setTipoPagador("Sin relación");
                }
            } catch (NoResultException ex) {
                empleadorEntidadPagadora.setTipoPagador("Sin relación");
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return empleadorEntidadPagadora;
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        return empleador;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#obtenerSolicitudesEmpleador(com.asopagos.dto.Vista360EmpleadorBusquedaParamsDTO)
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Vista360EmpleadorRespuestaDTO> obtenerSolicitudesEmpleador(Vista360EmpleadorBusquedaParamsDTO params) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerSolicitudesEmpleador(Vista360EmpleadorBusquedaParamsDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Vista360EmpleadorRespuestaDTO> listaCompleta = new ArrayList<>();
        Boolean isAfiliacion = params.getTipoTransaccion() != null && params.getEstadoSolicitudAfilEmpleador() != null;
        Boolean isNovedad = params.getTipoTransaccion() != null && params.getEstadoSolicitudNovedad() != null;
        Boolean isAporte = params.getTipoTransaccion() != null && params.getEstadoSolicitudAporte() != null;
        Boolean isSinParametros = params.getTipoTransaccion() == null && params.getEstadoSolicitudAfilEmpleador() == null;
        Boolean isSoloTransaccion = params.getTipoTransaccion() != null && params.getEstadoSolicitudAfilEmpleador() == null;
        Boolean fechasRango = params.getFechaInicioRadicado() != null && params.getFechaFinRadicado() != null;

        // Solicitudes de afiliacion
        if (isAfiliacion || isSinParametros || isSoloTransaccion) {
            List<Object[]> solicitudesEmpleador = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_AFILIACIONES)
                    .setParameter("tipoIdentificacion", params.getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacion", params.getNumeroIdentificacion())
                    .setParameter("tipoTransaccion", params.getTipoTransaccion() != null ? params.getTipoTransaccion().name() : null)
                    .setParameter("estadoAfiliacion",
                            params.getEstadoSolicitudAfilEmpleador() != null ? params.getEstadoSolicitudAfilEmpleador().name() : null)
                    .setParameter("fechaRadicacion", params.getFechaRadicado() != null ? new Date(params.getFechaRadicado()) : null)
                    .setParameter("fechaInicioRadicacion", fechasRango ? new Date(params.getFechaInicioRadicado()) : null)
                    .setParameter("fechaFinRadicacion", fechasRango ? new Date(params.getFechaFinRadicado()) : null).getResultList();

            listaCompleta.addAll(organizarListaSolicitudesEmpleador(solicitudesEmpleador, 1));
        }

        // Solicitudes de novedad
        if (isNovedad || isSinParametros || isSoloTransaccion) {
            List<Object[]> solicitudesEmpleador = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_NOVEDADES)
                    .setParameter("tipoIdentificacion", params.getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacion", params.getNumeroIdentificacion())
                    .setParameter("tipoTransaccion", params.getTipoTransaccion() != null ? params.getTipoTransaccion().name() : null)
                    .setParameter("estadoAfiliacion",
                            params.getEstadoSolicitudNovedad() != null ? params.getEstadoSolicitudNovedad().name() : null)
                    .setParameter("fechaRadicacion", params.getFechaRadicado() != null ? new Date(params.getFechaRadicado()) : null)
                    .setParameter("fechaInicioRadicacion", fechasRango ? new Date(params.getFechaInicioRadicado()) : null)
                    .setParameter("fechaFinRadicacion", fechasRango ? new Date(params.getFechaFinRadicado()) : null).getResultList();

            listaCompleta.addAll(organizarListaSolicitudesEmpleador(solicitudesEmpleador, 2));
        }

        // Solicitud de aportes
        if (isAporte || isSinParametros || isSoloTransaccion) {
            List<Object[]> solicitudesEmpleador = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_APORTES)
                    .setParameter("tipoIdentificacion", params.getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacion", params.getNumeroIdentificacion())
                    .setParameter("tipoTransaccion", params.getTipoTransaccion() != null ? params.getTipoTransaccion().name() : null)
                    .setParameter("estadoAfiliacion",
                            params.getEstadoSolicitudAporte() != null ? params.getEstadoSolicitudAporte().name() : null)
                    .setParameter("fechaRadicacion", params.getFechaRadicado() != null ? new Date(params.getFechaRadicado()) : null)
                    .setParameter("fechaInicioRadicacion", fechasRango ? new Date(params.getFechaInicioRadicado()) : null)
                    .setParameter("fechaFinRadicacion", fechasRango ? new Date(params.getFechaFinRadicado()) : null).getResultList();

            listaCompleta.addAll(organizarListaSolicitudesEmpleador(solicitudesEmpleador, 3));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaCompleta;
    }

    /**
     * Metodo para organizar la lista Object y convertirla en
     * Vista360EmpleadorRespuestaDTO
     *
     * @param listaObject
     * @return
     */
    private List<Vista360EmpleadorRespuestaDTO> organizarListaSolicitudesEmpleador(List<Object[]> listaObject, int tipoSolicitud) {
        String firmaMetodo = "EmpleadoresBusiness.organizarListaSolicitudesEmpleador(List<Object[]>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // 1:Afiliaciones, 2:Novedades, 3:Aportes
        List<Vista360EmpleadorRespuestaDTO> listaCompleta = new ArrayList<>();

        if (!listaObject.isEmpty()) {
            for (Object[] solicitud : listaObject) {
                Vista360EmpleadorRespuestaDTO sol = new Vista360EmpleadorRespuestaDTO();
                sol.setIdSolicitud(solicitud[0] != null ? new Long(solicitud[0].toString()) : null);
                sol.setNumeroRadicado(solicitud[1] != null ? solicitud[1].toString() : null);
                sol.setFechaRadicacion(solicitud[2] != null ? new Date(((Timestamp) solicitud[2]).getTime()) : null);
                // Afiliaciones
                if (tipoSolicitud == 1) {
                    sol.setEstadoSolicitudAfilEmpleador(
                            solicitud[3] != null ? EstadoSolicitudAfiliacionEmpleadorEnum.valueOf(solicitud[3].toString()) : null);
                    // Novedades
                } else if (tipoSolicitud == 2) {
                    sol.setEstadoSolicitudNovedad(
                            solicitud[3] != null ? EstadoSolicitudNovedadEnum.valueOf(solicitud[3].toString()) : null);
                    // Aportes
                } else if (tipoSolicitud == 3) {
                    sol.setEstadoSolicitudAporte(solicitud[3] != null ? EstadoSolicitudAporteEnum.valueOf(solicitud[3].toString()) : null);
                }
                sol.setTipoTransaccion(solicitud[4] != null ? TipoTransaccionEnum.valueOf(solicitud[4].toString()) : null);
                listaCompleta.add(sol);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaCompleta;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.empleadores.service.EmpleadoresService#obtenerSolicitudesEmpleador(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Vista360EmpleadorRespuestaDTO> obtenerComunicadosSolicitudEmpleador(Long idSolicitud) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerSolicitudesEmpleador(Vista360EmpleadorBusquedaParamsDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Vista360EmpleadorRespuestaDTO> listaCompleta = new ArrayList<>();

        List<Object[]> comunicadosSolicitudEmpleador = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADOS_SOLICITUD_EMPLEADOR_VISTA360)
                .setParameter("idSolicitud", idSolicitud).getResultList();

        listaCompleta.addAll(organizarListaComunicadosEmpleador(comunicadosSolicitudEmpleador));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaCompleta;
    }

    /**
     * @param comunicadosSolicitudEmpleador
     * @return
     */
    private List<Vista360EmpleadorRespuestaDTO> organizarListaComunicadosEmpleador(List<Object[]> listaObject) {
        String firmaMetodo = "EmpleadoresBusiness.obtenerSolicitudesEmpleador(Vista360EmpleadorBusquedaParamsDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Vista360EmpleadorRespuestaDTO> listaCompleta = new ArrayList<>();

        if (!listaObject.isEmpty()) {
            for (Object[] comunicado : listaObject) {
                Vista360EmpleadorRespuestaDTO sol = new Vista360EmpleadorRespuestaDTO();
                sol.setNombreComunicado(comunicado[0] != null ? comunicado[0].toString() : null);
                sol.setFechaComunicado(comunicado[1] != null ? new Date(((Timestamp) comunicado[1]).getTime()) : null);
                sol.setEmailDestinatario(comunicado[2] != null ? comunicado[2].toString() : null);
                sol.setEstadoEnvioComunicado(comunicado[3] != null ? EstadoEnvioComunicadoEnum.valueOf(comunicado[3].toString()) : null);
                listaCompleta.add(sol);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaCompleta;
    }

    /**
     * @param idEmpleador
     * @return
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#consultarEmpleador360(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Empleador consultarEmpleador360(Long idEmpleador) {
        Empleador empleador = consultarEmpleador(idEmpleador);
        Integer cantidadTrabActivos = (Integer) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_TRABAJADORES_ACTIVOS_EMPLEADOR)
                .setParameter("idEmpleador", idEmpleador).getSingleResult();
        empleador.setNumeroTotalTrabajadores(cantidadTrabActivos);
        return empleador;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.empleadores.service.EmpleadoresService#consultarInfoTemporalEmpleador(Long)
     */
    @Override
    public DatoTemporalSolicitud consultarInfoTemporalEmpleador(Long idSolicitud) {
        return buscarTemporales(idSolicitud);
    }

    @Override
    public Persona consultarPersonaEmpleador(Long idEmpleador){
        Persona persona = new Persona();
        try{
            persona = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_ID_EMPLEADOR,Persona.class).setParameter("idEmpleador",idEmpleador).getSingleResult();
            return persona;
        }catch(Exception e){
            logger.warn("fallo consulta persona empleador "+ e);
            return persona;
        }
    }

    // comunicados masivos

    @Override
    public List<Map<String,Object>> consultarAfiliadosCertificadosMasivos(Long idEmpleador,TipoCertificadoMasivoEnum tipoCertificado, Long fechaDesde,Long fechaHasta){
        logger.info("Inicia List<PersonaModeloDTO> consultarAfiliadosCertificadosMasivos(Long "
            + idEmpleador+",TipoCertificadoMasivoEnum "+tipoCertificado.name()+", String "
            +fechaDesde+",String "+fechaHasta+")");
        try{
            if(tipoCertificado.equals(TipoCertificadoMasivoEnum.CERTIFICADO_AFILIACION)){
                return construirObjetoConsultaCertificadosMasivosAfiliacion((List<Object[]>) entityManager.createNamedQuery(
                    NamedQueriesConstants.CONSULTAR_AFILIADOS_CERTIFICADO_MASIVO)
                        .setParameter("idEmpleador",idEmpleador)
                        .setParameter("fechaDesde",new SimpleDateFormat("yyyy-MM-dd").format(new Date(fechaDesde)))
                        .setParameter("fechaHasta",new SimpleDateFormat("yyyy-MM-dd").format(new Date(fechaHasta)))
                        .getResultList());
            }
            return null;
        }catch(Exception e){
            logger.error("No se encontraron coincidencias para los parametros proporcionados: "
                +idEmpleador+" "+tipoCertificado.name()+" "+fechaDesde+" "+fechaHasta+" ");
            return null;
        }
        
    }

    public List<Map<String, Object>> construirObjetoConsultaCertificadosMasivosAfiliacion(List<Object[]> objetos) {
        logger.info("Inicia construirObjetoConsultaCertificadosMasivosAfiliacion");

        if (objetos == null || objetos.isEmpty()) {
            logger.warn("La lista de entrada está vacía o es nula");
            return Collections.EMPTY_LIST;
        }

        try {
            return objetos.stream().map(objeto -> {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("tipoIdentificacion", objeto[0]);
                mapa.put("numeroIdentificacion", objeto[1]);
                mapa.put("nombreCompleto", objeto[2]);
                mapa.put("fechaAfiliacion", objeto[3]);
                mapa.put("roaId",objeto[4]);
                return mapa;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Hubo un error al mapear el resultado", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Response generarCertificadosMasivos(List<Map<String, Object>> afiliados, String dirigidoA, Long idEmpleador,
            TipoCertificadoMasivoEnum tipoCertificado,Boolean esCargueArchivo,String nombreArchivo) {

        logger.info("Inicia generarCertificadosMasivos(...)");

        esCargueArchivo = esCargueArchivo != null ? esCargueArchivo : Boolean.FALSE;

        EtiquetaPlantillaComunicadoEnum etiqueta = EtiquetaPlantillaComunicadoEnum.COM_GEN_CER_AFI_DEP;

        ConsultarPlantillaComunicado plantilla = new ConsultarPlantillaComunicado(etiqueta);
        plantilla.execute();
        PlantillaComunicado comunicadoBase = plantilla.getResult();
        logger.info(etiqueta);
        logger.info(comunicadoBase.getEncabezado());
        logger.info(comunicadoBase.getCuerpo());
        List<InformacionArchivoDTO> listaRchivos = afiliados.parallelStream().map(afiliado -> {
            String numeroIdentificacion = afiliado.get("numeroIdentificacion").toString();
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(afiliado.get("tipoIdentificacion").toString());
            GenerarCertificado certificado = new GenerarCertificado(
                    null, TipoCertificadoEnum.valueOf(tipoCertificado.CERTIFICADO_AFILIACION.name()),
                    numeroIdentificacion, dirigidoA, idEmpleador, etiqueta,
                    TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, null, false,
                    tipoIdentificacion, false, null, null);
                certificado.execute();
            CertificadoDTO resultadoCertificado = certificado.getResult();
            logger.info("Certificado generado con ID: " + resultadoCertificado.getIdCertificado());

              ResolverVariablesComunicado resolver = new ResolverVariablesComunicado(etiqueta,
                resultadoCertificado.getIdCertificado(), null, null);
                resolver.execute();

                Map<String,Object> mapa = resolver.getResult();
                if (mapa == null || mapa.isEmpty()) {
                    logger.warn("No se resolvieron variables para el certificado con ID: " + resultadoCertificado.getIdCertificado());
                } else {
                    logger.info("Variables resueltas para certificado: " + resultadoCertificado.getIdCertificado());
                }
                PlantillaComunicado comunicadoPersonalizado = new PlantillaComunicado(comunicadoBase);
                    comunicadoPersonalizado.setEncabezado(reemplazarTexto(mapa, comunicadoPersonalizado.getEncabezado()));
                    comunicadoPersonalizado.setCuerpo(reemplazarTexto(mapa, comunicadoPersonalizado.getCuerpo()));
                    comunicadoPersonalizado.setPie(reemplazarTexto(mapa, comunicadoPersonalizado.getPie()));
                    comunicadoPersonalizado.setMensaje(reemplazarTexto(mapa, comunicadoPersonalizado.getMensaje()));
                    logger.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> archivo con texto reemplazado");
                    logger.info(comunicadoPersonalizado.getEncabezado()+comunicadoPersonalizado.getCuerpo()+comunicadoPersonalizado.getPie());
                        InformacionConvertDTO informacionHtmlDTO = new InformacionConvertDTO();
                        String contenidoCompleto = comunicadoPersonalizado.getEncabezado()
                                                + comunicadoPersonalizado.getCuerpo()
                                                + comunicadoPersonalizado.getPie();
                        informacionHtmlDTO.setHtmlContenido(contenidoCompleto);
                    informacionHtmlDTO.setRequiereSello(false);
                    informacionHtmlDTO.setMargenesx(Arrays.asList(56f,56f));
                    informacionHtmlDTO.setMargenesy(Arrays.asList(0.5f,2f));
                    informacionHtmlDTO.setAltura(78f);
                ConvertHTMLtoPDF convertirHtml = new ConvertHTMLtoPDF(informacionHtmlDTO);
                convertirHtml.execute();
            
                enviarNotifiicacion(resultadoCertificado.getIdCertificado(),etiqueta,crearArchivo(convertirHtml.getResult()));
                InformacionArchivoDTO archivoCertificado = new InformacionArchivoDTO();
                archivoCertificado.setDataFile(convertirHtml.getResult());
                archivoCertificado.setNumeroIdentificacionPropietario(numeroIdentificacion);
                return archivoCertificado;
        }).collect(Collectors.toList());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(baos)) {

            for (InformacionArchivoDTO pdf : listaRchivos) {
                ZipEntry zipEntry = new ZipEntry("Certificado_"+pdf.getNumeroIdentificacionPropietario()+".pdf");
                zipOut.putNextEntry(zipEntry);
                zipOut.write(pdf.getDataFile());
                zipOut.closeEntry();
            }

            zipOut.finish();
            byte[] zipBytes = baos.toByteArray();

            String idECM = crearArchivoZip(zipBytes, idEmpleador,esCargueArchivo);
            
            if(esCargueArchivo){
                ControlCertificadosMasivos controlCertificados = new ControlCertificadosMasivos(idECM,"Certificado_afiliacion_"+idEmpleador+".zip",idEmpleador,tipoCertificado,nombreArchivo);
                controlCertificados.setNombreCargue(nombreArchivo);
                entityManager.persist(controlCertificados);
                entityManager.flush();
            }else{
                ControlCertificadosMasivos controlCertificados = new ControlCertificadosMasivos(idECM,"Certificado_afiliacion_cargue"+idEmpleador+".zip",idEmpleador,tipoCertificado);
                entityManager.persist(controlCertificados);
                entityManager.flush();
            }
            Map<String,String> json = new HashMap<String,String>();
            json.put("idEcm", idECM);

            return Response.ok(json)
                    .header("Content-Disposition", "attachment; filename=certificados.zip")
                    .build();
        } catch (Exception e) {
            logger.error("hubo un fallo al retornar el rar");
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    public String reemplazarTexto(Map<String, Object> mapa, String texto) {
        logger.warn("Inicia reemplazarTexto(Map<String, Object> mapa, String texto) ");
        StringBuilder sb = new StringBuilder(texto);
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            try{
                String clave = entry.getKey();
                String valor = entry.getValue() == null ? "" : entry.getValue().toString();
                if(entry.getValue() !=null && entry.getValue().toString() != null ){
                    int index;
                    while ((index = sb.indexOf(clave)) != -1) {
                        logger.debug("Buscando reemplazo para clave: " + clave + " con valor: " + valor);
                        if(clave.equals("${membretePieDePaginaDeLaCcf}") || clave.equals("${membreteEncabezadoDeLaCcf}") || clave.equals("${logoSupersubsidio}")|| clave.equals("${logoDeLaCcf}")){
                            try{
                                String encoded;
                                ObtenerArchivo obtenerArchivoSrv = new ObtenerArchivo(valor);
                                obtenerArchivoSrv.execute();
                                if(obtenerArchivoSrv.getResult() != null){
                                    InformacionArchivoDTO archivo = obtenerArchivoSrv.getResult();
                                    encoded = Base64.getEncoder().encodeToString(archivo.getDataFile());
                                    if(clave.equals("${membretePieDePaginaDeLaCcf}") || clave.equals("${membreteEncabezadoDeLaCcf}")){
                                        logger.warn("imagenb encoded >>>>>>>>>>>>>>>>>>"+encoded);
                                        logger.warn("valor clave imagen>>>>>>>>>>>>"+valor);
                                        logger.warn("valor del getgdatafile>>>>>>>>>>>>>"+archivo.getDataFile());
                                        valor = "<img id=\"membrete\" src=\"data:image/png;base64," + encoded + "\"/>";
                                    }else{
                                        valor = "<img id=\"Imagen\" style=\"width: 100px; background-color: red;\" src=\"data:image/png;base64," + encoded + "\"/>";
                                    }
                                }
                            }catch(Exception e){
                                logger.error("fallo remplazo de claves"+e);
                            }
                            sb.replace(index, index + clave.length(),valor);

                        }else{
                            sb.replace(index, index + clave.length(), valor);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                return sb.toString();        
            }
            logger.warn("texto reemplazado>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            logger.info(sb.toString());
        }
        logger.warn("Finaliza reemplazarTexto(Map<String, Object> mapa, String texto)");
        return sb.toString();
    }

    private String crearArchivo(byte[] archivo){
        logger.info("Inicia crearArchivo(byte[] archivo)");
        InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
        infoFile.setDataFile(archivo);
        infoFile.setFileType("application/pdf");
        infoFile.setProcessName("Certificados afiliacion masivos");
        infoFile.setDescription("Certificados de afiliacion persona");
        infoFile.setDocName("Certificado_afiliacion.pdf");
        infoFile.setFileName("Certificado_afiliacion.pdf");
        logger.info("Finaliza crearArchivo(byte[] archivo)");
        return almacenarArchivo(infoFile);
    }

    private String crearArchivoZip(byte[] archivo,Long idEmpleador,Boolean esCargueArchivo){
        logger.info("Inicia crearArchivoZip(byte[] archivo,Long idEmpleador,Boolean esCargueArchivo)");
        InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
        infoFile.setDataFile(archivo);
        infoFile.setFileType(MediaType.APPLICATION_OCTET_STREAM);
        infoFile.setProcessName("Certificados masivos de afiliacion");
        infoFile.setDescription("Certificados de afiliacion masivos");
        infoFile.setDocName(esCargueArchivo ? "Certificado_afiliacion_cargue"+idEmpleador+".zip": "Certificado_afiliacion_"+idEmpleador+".zip");
        infoFile.setFileName("Certificado_afiliacion_"+idEmpleador+".zip");
        logger.info("Finaliza crearArchivoZip(byte[] archivo,Long idEmpleador,Boolean esCargueArchivo)");
        return almacenarArchivo(infoFile);
    }

    private void enviarNotifiicacion(Long idSolicitud,EtiquetaPlantillaComunicadoEnum etiqueta,String idArchivos){
        logger.info("Inicia enviarNotifiicacion(Long idSolicitud,EtiquetaPlantillaComunicadoEnum etiqueta,String idArchivos)");

        NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();

        notificacion.setEtiquetaPlantillaComunicado(etiqueta);
        notificacion.setIdInstanciaProceso(String.valueOf(idSolicitud));
        notificacion.setArchivosAdjuntosIds(Arrays.asList(idArchivos));
        notificacion.setComunicadoEditado(true);
        notificacion.setDestinatarioTO(Arrays.asList("a@a.com"));
        EnviarNotificacionComunicado enviarNotificacionComunicado = new EnviarNotificacionComunicado(
            notificacion);
        enviarNotificacionComunicado.execute();
        logger.info("Finaliza enviarNotifiicacion(Long idSolicitud,EtiquetaPlantillaComunicadoEnum etiqueta,String idArchivos)");
    }

    @Override
    public List<ControlCertificadosMasivosDTO> consultarCertificadosMasivos(Long idEmpleador){
        logger.info("Inicia List<ControlCertificadosMasivosDTO> consultarCertificadosMasivos(Long "+idEmpleador+")");
        try{
            logger.info("Finaliza List<ControlCertificadosMasivosDTO> consultarCertificadosMasivos(Long "+idEmpleador+")");
            return (List<ControlCertificadosMasivosDTO>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CERTIFIADOS_MASIVOS)
                    .setParameter("idEmpleador",idEmpleador)
                    .getResultList();
        }catch(Exception e){
            logger.error("Fallo List<ControlCertificadosMasivosDTO> consultarCertificadosMasivos(Long "+idEmpleador+")");
            e.printStackTrace();
            return null;
        }
    }
}