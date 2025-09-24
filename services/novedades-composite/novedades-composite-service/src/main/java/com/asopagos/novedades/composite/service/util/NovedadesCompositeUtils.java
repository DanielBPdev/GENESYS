package com.asopagos.novedades.composite.service.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import com.asopagos.afiliaciones.clients.ObtenerNumeroRadicadoCorrespondencia;
import com.asopagos.afiliaciones.clients.RadicarListaSolicitudes;
import com.asopagos.afiliados.clients.ActualizarBeneficiario;
import com.asopagos.afiliados.clients.ActualizarDatosAfiliado;
import com.asopagos.afiliados.clients.ActualizarGrupoFamiliarPersona;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.clients.ValidarEmpleadorCeroTrabajadores;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCarteraAsincrono;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.dto.SucursalEmpresaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadPilaModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorId;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.fovis.SolicitudAnalisisNovedadFovis;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPila;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.ActualizarSolicitudNovedadPersona;
import com.asopagos.novedades.clients.ConsultarNovedadPorNombre;
import com.asopagos.novedades.clients.GuardarExcepcionNovedad;
import com.asopagos.novedades.clients.ObtenerConsecutivoNovedad;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.novedades.composite.service.NovedadAbstractFactory;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosExcepcionNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.fovis.clients.ConsultarPostulacionVigentePersonas;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliacionesrutines.radicarlistasolicitudes.RadicarListaSolicitudesRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarbeneficiario.ActualizarBeneficiarioRutine;
import com.asopagos.rutine.afiliadosrutines.actualizardatosafiliado.ActualizarDatosAfiliadoRutine;
import com.asopagos.rutine.afiliadosrutines.actualizargrupofamiliarpersona.ActualizarGrupoFamiliarPersonaRutine;
import com.asopagos.rutine.afiliadosrutines.actualizarrolafiliado.ActualizarRolAfiliadoRutine;
import com.asopagos.rutine.empleadores.ModificarEmpleadorRutine;
import com.asopagos.rutine.novedadescompositerutines.procesaractivacionbeneficiarioPILA.ProcesarActivacionBeneficiarioPILARutine;
import com.asopagos.rutine.novedadesrutines.actualizarestadosolicitudnovedad.ActualizarEstadoSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.actualizarsolicitudnovedadpersona.ActualizarSolicitudNovedadPersonaRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedad.CrearSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedadEmpleador.CrearSolicitudNovedadEmpleadorRutine;
import com.asopagos.rutine.novedadesrutines.crearsolicitudnovedadPersona.CrearSolicitudNovedadPersonaRutine;
import com.asopagos.rutine.novedadesrutines.guardarexcepcionnovedad.GuardarExcepcionNovedadRutine;
import com.asopagos.rutine.personasrutines.actualizardatospersonarutine.ActualizarDatosPersonaRutine;
import com.asopagos.rutine.solicitudesrutines.guardardatostemporalespersona.GuardarDatosTemporalesPersonaRutine;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.ValidacionDesafiliacionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import java.util.concurrent.TimeUnit;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.novedades.composite.service.constants.NamedQueriesConstants;
import com.asopagos.novedades.composite.service.business.interfaces.IConsultasModeloCoreNovedadesComposite;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.database.NumeroRadicadoUtil;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.rutine.afiliadosrutines.actualizarbeneficiariosimple.ActualizarBeneficiarioSimpleRutine;
import javax.persistence.*;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPila;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.pila.clients.ConsultarDatosEmpleadorByRegistroDetallado;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.afiliados.clients.ActualizarRolesAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.empleadores.clients.ConsultarPersonaEmpleador;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.afiliaciones.empleadores.composite.clients.CrearSolicitudAfiliacionEmpleadorAportes;
import javax.persistence.PersistenceException;
/**
 * <b>Descripcion:</b> Contiene funcionalidades relacionadas con la generación de archivos<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:mamonroy@heinsohn.com.co"></a>
 */
public class NovedadesCompositeUtils {
    
    //@PersistenceContext(unitName = "core_PU")

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesCompositeUtils.class);
    
    @Resource//(lookup="java:jboss/ee/concurrency/executor/novedades")
    private ManagedExecutorService managedExecutorService;
        @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;
    /**
     * @param entityManager
     */
         	/**
	 * Inject del EJB para consultas en modelo Core entityManager
	 */
	@Inject
	private IConsultasModeloCoreNovedadesComposite consultasCore;

    public NovedadesCompositeUtils(){        
    }
    
    /**
     * @param entityManager
     */
    public NovedadesCompositeUtils(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    
    /**
     * @param entityManager
     * @param managedExecutorService 
     */
    public NovedadesCompositeUtils(EntityManager entityManager, ManagedExecutorService managedExecutorService){
        this.entityManager = entityManager;
        this.managedExecutorService = managedExecutorService;
    }
    
      
     /**
     * @param datosNovedadConsecutiva
     * @param userDTO
     */
    @Asynchronous
    public void radicarSolicitudNovedadCascada(DatosNovedadCascadaDTO datosNovedadConsecutiva, UserDTO userDTO) {
        logger.info("Inicia radicarSolicitudNovedadCascada (DatosNovedadCascadaDTO, UserDTO)");
        if (datosNovedadConsecutiva.getNumeroRadicadoOriginal() == null || datosNovedadConsecutiva.getTipoTransaccionOriginal() == null) {
            logger.info("Finaliza radicarSolicitudNovedadCascada (DatosNovedadCascadaDTO, UserDTO) - RadicadoTransaccion NULL");
            return;
        }
        //Se obtiene las novedades cascada
        List<SolicitudNovedadDTO> listaSolicitudes = construirSolicitudNovedadConsecutiva(datosNovedadConsecutiva);
        logger.info("***->Datos consecutiva<-****" + listaSolicitudes);
        logger.info("***->Tamaños solicitudes <-****" + listaSolicitudes.size());
        List<Callable<Void>> listParallelTask = new LinkedList<>();
        
            for (SolicitudNovedadDTO solicitudNovedadDTO : listaSolicitudes) {
                logger.info("***-Iteracion--***");
                logger.info("*** Solicitudes DTO***" + solicitudNovedadDTO);
                logger.info("*** Lista***" + listaSolicitudes);
                //    Callable<Void> parallelTask = () -> {
                logger.info("DatosNovedadCascadaDTO ->  " + datosNovedadConsecutiva.toString());

                radicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedadDTO, userDTO);
                //      return null;
                //};
                //listParallelTask.add(parallelTask);
            }
       

        logger.info("***->Finaliza ciclo<-****");
        /*if (!listParallelTask.isEmpty()) {
            try {
                // Se ejecuta el registro de novedad
                logger.info("radicarSolicitudNovedadCascada (DatosNovedadCascadaDTO, UserDTO) - Ejecutando MES");
                managedExecutorService.invokeAll(listParallelTask);
            } catch (InterruptedException e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
        }
         */
        logger.debug("Finaliza radicarSolicitudNovedadCascada (DatosNovedadCascadaDTO, UserDTO)");
    }
    /**
     *
     * @param userDTO
     */
    @Asynchronous
    public void radicarSolicitudesMasivas(List<SolicitudNovedadDTO> ListaSolicitude, UserDTO userDTO) {
        logger.info("Esta ingresaddo a las listas de solicitudes ");
        logger.info(ListaSolicitude);

        List<SolicitudNovedadDTO> PrimerLista = new ArrayList();
        List<SolicitudNovedadDTO> SegundaLista = new ArrayList();

        int size = ListaSolicitude.size();
        int cont = 0;
        int ValidarCount = 0;

        for (int i = 0; i < size; i++) {
            if (i < (size + 1) / 2) {
                PrimerLista.add(ListaSolicitude.get(i));
                ValidarCount = PrimerLista.size();
            } else {
                SegundaLista.add(ListaSolicitude.get(i));
            }
        }
        for (SolicitudNovedadDTO solicitudNovedadDTO : PrimerLista) {
            radicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedadDTO, userDTO);
            logger.info("*****-> iteracion For MAsivos uno " + solicitudNovedadDTO);
            cont++;
        }

        logger.info("este es el cout " + cont);
         logger.info("este es la novedad " + ValidarCount);
        if (cont == ValidarCount) {
            for (SolicitudNovedadDTO solicitudNovedadDTO : SegundaLista) {
                radicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedadDTO, userDTO);
                logger.info("*****-> iteracion For MAsivos dos " + solicitudNovedadDTO);
            }
        }
    }
    
    /**
     * Construye la lista de novedades que se ejecutan en cascada
     * @param datosNovedadConsecutiva
     *        Datos de novedad en cascada
     * @return Lista de solicitudes de novedad a registrar
     */
    private List<SolicitudNovedadDTO> construirSolicitudNovedadConsecutiva(DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        String firmaServicio = "construirSolicitudNovedadConsecutiva (DatosNovedadCascadaDTO datosNovedadConsecutiva)";
        logger.info("Inicia"+firmaServicio);
        logger.info("datosNovedadConsecutiva.getTipoTransaccionOriginal():"+datosNovedadConsecutiva.getTipoTransaccionOriginal());
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
            case ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_CONYUGE_WEB:
            case ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB: 
            case ACTIVAR_BENEFICIARIOS_MULTIPLES_PRESENCIAL:
            case ACTIVAR_BENEFICIARIOS_MULTIPLES_WEB:
            case ACTIVAR_BENEFICIARIOS_MULTIPLES_DEPWEB:
            case ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB:
            case ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB:
            case ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_HIJASTRO_WEB:
            case ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB:
            case ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB:
            case ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB:
            case ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL:
            case ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB:
            case ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB:
            case ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL:
            case ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB:
            case ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB:
            case ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_PADRE_WEB:
            case ACTIVAR_BENEFICIARIO_PADRE_DEPWEB:
            case ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL:
            case ACTIVAR_BENEFICIARIO_MADRE_WEB:
            case ACTIVAR_BENEFICIARIO_MADRE_DEPWEB:
                listaSolicitudes = construirDetalleNovedadConsecutivaAfiliacionBeneficiario(datosNovedadConsecutiva);
                break;
            case EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL:
            case INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL:
                listaSolicitudes = construirDetalleNovedadConsecutivaBeneficiarios(datosNovedadConsecutiva);
            default:
                break;
        }
        return listaSolicitudes;
    }
    
    /**
     * Construye la solicitud de novedad detallada para inactivar beneficiarios producto de afiliciónes como conyuges/padres
     * con registros activos como hijos
     * @param datosNovedadConsecutiva
     *        Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de beneficiarios
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaAfiliacionBeneficiario(DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        String firmaServicio = "construirDetalleNovedadConsecutivaAfiliacionBeneficiario(DatosNovedadCascadaDTO datosNovedadConsecutiva)";
        logger.info("Inicia" + firmaServicio);
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
        String radicadoOriginal = datosNovedadConsecutiva.getNumeroRadicadoOriginal();
        
        //Se obtiene el número de consecutivo actual (Dado que el afiliado puede tener registros como beneficiario que
        //ya generaron solicitudes de novedad de retiro asociados a la solicitud
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
            solicitudNovedad.setTipoTransaccion(getTipoTransaccionInactivarBeneficiarioByClasificacion(beneficiario.getTipoBeneficiario()));
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO;
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();  
            llenarDatosBeneficiarioNovedad(beneficiario, datosPersona, null);
            datosPersona.setMotivoDesafiliacionBeneficiario(
                datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado() == null
                    ? MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO
                    : MotivoDesafiliacionBeneficiarioEnum.valueOf(datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado().name())
            );            
            datosPersona.setFechaInactivacionBeneficiario(datosNovedadConsecutiva.getFechaRetiro());
            datosPersona.setFechaRetiro(datosNovedadConsecutiva.getFechaRetiro());
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
        }
        return listaSolicitudes;
    }  

    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaBeneficiarios(DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        String firmaServicio = "construirDetalleNovedadConsecutivaAfiliacionBeneficiario(DatosNovedadCascadaDTO datosNovedadConsecutiva)";
        logger.info("Inicia" + firmaServicio);
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
        String radicadoOriginal = datosNovedadConsecutiva.getNumeroRadicadoOriginal();
        
        //Se obtiene el número de consecutivo actual (Dado que el afiliado puede tener registros como beneficiario que
        //ya generaron solicitudes de novedad de retiro asociados a la solicitud
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
            solicitudNovedad.setTipoTransaccion(datosNovedadConsecutiva.getTipoTransaccionOriginal());
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();  
            llenarDatosBeneficiarioNovedad(beneficiario, datosPersona, null);
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
        }
        return listaSolicitudes;
    }
  
    
    /**
     * @param nuevaNovedad
     * @param userDTO
     */
    public void radicarSolicitudNovedadAutomaticaSinValidaciones(SolicitudNovedadDTO nuevaNovedad, UserDTO userDTO){
        String firmaServicio = "radicarSolicitudNovedadAutomaticaSinValidaciones(SolicitudNovedadDTO nuevaNovedad, UserDTO userDTO)";
        logger.info("Inicia" + firmaServicio);
        try {
            // Se consulta la información de la novedad
            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(nuevaNovedad.getTipoTransaccion());
            nuevaNovedad.setNovedadDTO(novedad);
            logger.info("--Ncespedes-- RutaCualificada: " + novedad.getRutaCualificada());
            logger.info("--Ncespedes-- radicarSolicitudNovedadAutomaticaSinValidaciones SolicitudNovedadDTO: " + nuevaNovedad.toString());
            logger.info("--Ncespedes-- fechaInicioNovedad: " + nuevaNovedad.getDatosPersona().getFechaInicioNovedad());
            // Se crea la solicitud
            SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(nuevaNovedad, userDTO);
            // Se resuelve la novedad
            resolverNovedad(nuevaNovedad, solicitudNovedad, userDTO);
            // Se cierra la solicitud
            cerrarSolicitudNovedad(nuevaNovedad, true);
            // Se guarda la información temporal
              logger.info("**__**radicarSolicitudNovedadAutomaticaSinValidaciones --inicia guardarDatosTemporal RutaCualificada: " + novedad.getRutaCualificada());
           if(nuevaNovedad.getTipoTransaccion() != TipoTransaccionEnum.NOVEDAD_REINTEGRO) {
               guardarDatosTemporal(nuevaNovedad);
           }
               logger.info("**__**radicarSolicitudNovedadAutomaticaSinValidaciones --Finaliza guardarDatosTemporal RutaCualificada: " );
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info("Finaliza radicarSolicitudNovedadAutomaticaSinValidaciones (SolicitudNovedadDTO, UserDTO)");
    }
    
    /**
     * Metodo encargado de invocar el servicio para el almacenamiento de los datos temporales asociandolos a la persona si corresponde con
     * una activacion de beneficiario
     * @param solicitudNovedadDTO
     *        Informacion de la solicitud novedad
     * @throws JsonProcessingException
     *         Error presentado en la conversion
     */
    public void guardarDatosTemporal(SolicitudNovedadDTO solicitudNovedadDTO) throws JsonProcessingException {
        System.out.println("**__**procesarActivacionBeneficiarioPILA guardarDatosTemporal inicia en novedades " );
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        p.guardarDatosTemporal(solicitudNovedadDTO, entityManager);   
           System.out.println("**__**procesarActivacionBeneficiarioPILA guardarDatosTemporal finaliza en novedades " );    
    }     
   
    
    /**
     * Obtiene la lista de transacciones asociadas a la activacion de beneficiarios
     * @return Lista de tipos transaccion
     */
    public static List<TipoTransaccionEnum> getListTransaccionActivarBeneficiario() {
         System.out.println("**__**procesarActivacionBeneficiarioPILA getListTransaccionActivarBeneficiario inicia en novedades " );
        return ProcesarActivacionBeneficiarioPILARutine.getListTransaccionActivarBeneficiario();     
    }
    
    /**
     * Método encargado de parametrizar una notificación de un comunicado.
     * @param solicitudNovedadDTO 
     * @param idSolicitud id de la solicitud global.
     * @param intento intento de novedad.     
     */
    public void cerrarSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO, boolean intento){
        logger.info("**__**-<Inicio de método cerrarSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO) ");
        
        /* La solicitud de novedad queda en estado cerrada cuando:
         * -El punto de resolucion es FRONT y el canal es WEB
         * -Se trata de un cargue multiple
         * para los demás escenarios se cierra desde el comunicado invocando el servicio de actualizarEStadoSolicitud de NOvedadesService */
        if (PuntoResolucionEnum.FRONT.equals(solicitudNovedadDTO.getNovedadDTO().getPuntoResolucion())
                || CanalRecepcionEnum.PILA.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.PRESENCIAL_INT.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.APORTE_MANUAL.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.CORRECCION_APORTE.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.WEB.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.ENTIDAD_EXTERNA.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.ARCHIVO_ACTUALIZACION.equals(solicitudNovedadDTO.getCanalRecepcion())
                || CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR.equals(solicitudNovedadDTO.getCanalRecepcion())
                || solicitudNovedadDTO.getIdCargueMultipleNovedad()!=null || intento){
            actualizarEstadoSolicitudNovedad(solicitudNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
            solicitudNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.CERRADA);
        }else if(CanalRecepcionEnum.PILA.equals(solicitudNovedadDTO.getCanalRecepcion()) && solicitudNovedadDTO.getTipoTransaccion() == TipoTransaccionEnum.NOVEDAD_REINTEGRO){
        actualizarEstadoSolicitudNovedad(solicitudNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
            solicitudNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.CERRADA);
        }
    }
    
    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     * 
     * @param solicitudNovedadDTO
     *            datos de la novedad.
     * @param solicitudNovedad
     *            solicitud a modificar.
     * @param userDTO 
     * @throws Exception 
     */
    public void resolverNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad,UserDTO userDTO)throws Exception{
        logger.info("Inicio de método resolverNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        try {
            actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.APROBADA);
            solicitudNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.APROBADA);
            // Se establece que la actualizacion de documentacion del afiliado no
            // ejecuta convertidor ya que esta novedad solo actualiza los documentos
            // de la lista de chequeo 
            logger.info("Transaccion en curso : " + solicitudNovedadDTO.getNovedadDTO().getNovedad());
            logger.info("NcompositeUtils ejecutarNovedad-> SolicitudNovedadDTO: ");

            if (!solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTACION_AFILIADO_PRESENCIAL)
                    && !solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTACION_AFILIADO_WEB)
                    && !solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB)
                    && !solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL)
                    && !solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB)
                    && !solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO)) {
                /* se ejecuta la novedad */
                logger.info("GLPI-45051->**__**entra al if resolver novedad} solicitudNovedadDTO.getNovedadDTO().getNovedad()"+solicitudNovedadDTO.getNovedadDTO().getNovedad());
                ejecutarNovedad(solicitudNovedadDTO, solicitudNovedad, userDTO);
                logger.info("SE EJECUTÓ LA NOVEDAD " + solicitudNovedadDTO.getNovedadDTO().getNovedad());

                
                
                // Actualiza la cartera del aportante -> HU-169
                actualizarCarteraAportante(solicitudNovedadDTO);
            }
            logger.info("**__**{[entra al if resolver novedad}]} solicitudNovedad.getIdSolicitud(): "+solicitudNovedad.getIdSolicitud());
            //se debe actualizar a cerrada la solicitud
            actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
            solicitudNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.CERRADA);
        } catch (Exception e) {
            DatosExcepcionNovedadDTO data = new DatosExcepcionNovedadDTO(solicitudNovedadDTO, solicitudNovedad);
            
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            
          //--CLIENTE GuardarExcepcionNovedad--
            
            GuardarExcepcionNovedad guardarSrv = new GuardarExcepcionNovedad(exceptionAsString,data);
            guardarSrv.execute();
            
            
            //guardarExcepcionNovedad(data,exceptionAsString,userDTO);
            
            e.printStackTrace();
            
            //throw new Exception(e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info(
                "Fin de método resolverNovedadFront(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
    }
    
      
    
    /**
     * Método que actualiza la cartera de un aportante al que se le registraron novedades
     * @param solicitudNovedadDTO Información de la novedad registrada
     */
    public void actualizarCarteraAportante(SolicitudNovedadDTO solicitudNovedadDTO){
         logger.info("**__**actualizarCarteraAportante inicia ProcesarActivacionBeneficiarioPILARutine");
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        p.actualizarCarteraAportante(solicitudNovedadDTO);        
    }   
 
    
    /**
     * Método encargado de ejecutar una novedad.
     * @param solicitudNovedadDTO dto de la novedad, desde pantalla.
     * @param solicitudNovedad solicitud de la novedad.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void ejecutarNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        logger.info("Inicio de método ejecutarNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
   
        /* se invoca el servicio para dicha novedad */
        try{
             NovedadCore servicioNovedad = NovedadAbstractFactory.getInstance()                
                .obtenerServicioNovedad(solicitudNovedadDTO.getNovedadDTO());
                logger.info("solicitudNovedadDTO.getNovedadDTO():"+solicitudNovedadDTO.getNovedadDTO());
                logger.info("servicioNovedad:"+servicioNovedad);
            logger.info("_getRutaCualificada: " + solicitudNovedadDTO.getNovedadDTO().getRutaCualificada());
            // Se instancia el servicio de la novedad
            // TODO: implementar transformarEjecutarRutinaNovedad para todos los convertidores
            if("com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    || "com.asopagos.novedades.convertidores.persona.ActualizarNovedadPilaPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    || "com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersonaMultiple".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    //|| "com.asopagos.novedades.convertidores.persona.ActualizarRetiroNovedadPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    ){
                logger.info("_ES NOVEDAD PILA_");
                logger.debug("--Ncespedes-- ejecutarNovedad SolicitudNovedadDTO: " );
                servicioNovedad.transformarEjecutarRutinaNovedad(solicitudNovedadDTO, entityManager, userDTO);
            }else if ("com.asopagos.novedades.convertidores.persona.ActualizarRolAfiliadoNovedadPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada()) && solicitudNovedadDTO.getDatosPersona().getRolesAfiliado()!=null && !solicitudNovedadDTO.getDatosPersona().getRolesAfiliado().isEmpty()){
                List<RolAfiliadoModeloDTO> rolafiliados = new ArrayList<>();
                for(Long rol : solicitudNovedadDTO.getDatosPersona().getRolesAfiliado()){
                    ConsultarRolAfiliado rolafiliadoC = new ConsultarRolAfiliado(rol);
                    RolAfiliadoModeloDTO rolafiliado = new RolAfiliadoModeloDTO();
                    rolafiliadoC.execute();
                    rolafiliado = rolafiliadoC.getResult();
                    rolafiliado.setClaseTrabajador(ClaseTrabajadorEnum.REGULAR);
                    rolafiliados.add(rolafiliado);
                }
                ActualizarRolesAfiliado accRoles = new ActualizarRolesAfiliado(rolafiliados);
                accRoles.execute();
            }
            else{
                try{
                    System.out.println("GLPI-45051->ejecutarNovedad->servicioNovedad.transformarServicio");
                ServiceClient servicio = servicioNovedad.transformarServicio(solicitudNovedadDTO);
                servicio.execute();
                }catch(Exception e){
                    logger.error("Error respuesta ServiceClient -> ejecutarNovedad:", e);
                }
                
            }  
            logger.info("fin de método ejecutarNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        } catch (Exception e) {
            logger.info("Error ejecutarNovedad: "+e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una novedad
     */
    private void actualizarEstadoSolicitudNovedad(Long idSolicitud, EstadoSolicitudNovedadEnum estadoSolicitud) {
        logger.info("**__**Inicia actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
        /*
        ActualizarEstadoSolicitudNovedad actualizarEstadoSolNovedadService = new ActualizarEstadoSolicitudNovedad(
                idSolicitud, estadoSolicitud);
        actualizarEstadoSolNovedadService.execute();
        */
        ActualizarEstadoSolicitudNovedadRutine a = new ActualizarEstadoSolicitudNovedadRutine();
        a.actualizarEstadoSolicitudNovedad(idSolicitud, estadoSolicitud, entityManager);       
        
        logger.info("**__**Finaliza actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
    }


    public void actualizarResultadoProcesoSolicitud(Long idSolicitud, ResultadoProcesoEnum resultadoProceso) {

        ActualizarEstadoSolicitudNovedadRutine a = new ActualizarEstadoSolicitudNovedadRutine();
        a.actualizarResultadoProcesoSolicitud(idSolicitud, resultadoProceso, entityManager);  
    }

    
    /**
     * Método que hace la peticion REST al servicio que crea una solicitud de
     * novedad.
     * 
     * @param novedadDTO
     *            novedad a crearse.
     * @param userDTO
     *            usuario que radica la solicitud.
     * @return  s
     */
    public SolicitudNovedadModeloDTO crearSolicitudNovedad(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
         logger.info("**__**inicia crearSolicitudNovedad novedadesutil   ");  
       ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
       return p.crearSolicitudNovedad(novedadDTO, userDTO, entityManager);
    }   
   
     
   
    
 // *************************************************************************************************************************************
    // **************************************************** CLIENTES A METODOS PRIVADOS ****************************************************
    // *************************************************************************************************************************************
    // *************************************************************************************************************************************
       
   
    
    /**
     * Método que consulta una novedad
     * @param tipoTransaccion 
     * 
     * @param idSolicitud
     *            es el identificador de la novedad
     * @return Objeto solicitud novedad
     */
    public static ParametrizacionNovedadModeloDTO consultarNovedad(TipoTransaccionEnum tipoTransaccion) {
        logger.info("Inicia consultarNovedad(TipoTransaccionEnum) " + tipoTransaccion);
        ParametrizacionNovedadModeloDTO novedad = new ParametrizacionNovedadModeloDTO();
        ConsultarNovedadPorNombre consultarNovedadService = new ConsultarNovedadPorNombre(tipoTransaccion);
        consultarNovedadService.execute();
        novedad = consultarNovedadService.getResult();
        if (novedad == null) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
        logger.info("Finaliza consultarNovedad(TipoTransaccionEnum) " + tipoTransaccion);
        return novedad;
    }
    
    /**
     * Construye la solicitud de novedad detallada para retiro de afiliados dependientes
     * @param datosNovedadConsecutiva
     *        Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de afiliado
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaAfiliado(DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        String firmaServicio = "construirDetalleNovedadConsecutivaAfiliado(DatosNovedadCascadaDTO datosNovedadConsecutiva)";
        logger.info("Inicia" + firmaServicio);
   
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
     * Transforma la información básica del afiliado para el registro de la novedad
     * @param afiliado
     *        Información afiliado
     * @param persona
     *        Modelo para el registro de novedad
     */
    public static void llenarDatosAfiliadoNovedad(AfiliadoModeloDTO afiliado, DatosPersonaNovedadDTO persona) {
        // Info Afiliado
        persona.setTipoIdentificacion(afiliado.getTipoIdentificacion());
        persona.setNumeroIdentificacion(afiliado.getNumeroIdentificacion());
        persona.setTipoIdentificacionTrabajador(afiliado.getTipoIdentificacion());
        persona.setNumeroIdentificacionTrabajador(afiliado.getNumeroIdentificacion());
    }
    
    
    /**
     * Construye la solicitud de novedad detallada para inactivar beneficiarios
     * @param datosNovedadConsecutiva
     *        Datos novedad consecutiva
     * @return Lista de solicitudes de novedad de beneficiarios
     */
    private List<SolicitudNovedadDTO> construirDetalleNovedadConsecutivaBeneficiario(DatosNovedadCascadaDTO datosNovedadConsecutiva) {
        String firmaServicio = "construirDetalleNovedadConsecutivaBeneficiario(DatosNovedadCascadaDTO datosNovedadConsecutiva)";
        logger.info("Inicia" + firmaServicio);
         
        List<SolicitudNovedadDTO> listaSolicitudes = new ArrayList<>();
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
            solicitudNovedad.setTipoTransaccion(getTipoTransaccionInactivarBeneficiarioByClasificacion(beneficiario.getTipoBeneficiario()));
            solicitudNovedad.setNovedadAsincrona(Boolean.TRUE);
            solicitudNovedad.setNumeroRadicacion(numeroRadicado);
            // Se obtiene el motivo de desafiliación beneficiario basado en el del afiliado
            motivoDesafiliacionBeneficiario = ValidacionDesafiliacionUtils
                        .validarMotivoDesafiliacionBeneficiario(datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado());
            DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
            llenarDatosBeneficiarioNovedad(beneficiario, datosPersona, null);
            datosPersona.setMotivoDesafiliacionBeneficiario(motivoDesafiliacionBeneficiario);
            datosPersona.setFechaInactivacionBeneficiario(datosNovedadConsecutiva.getFechaRetiro());
            solicitudNovedad.setDatosPersona(datosPersona);
            listaSolicitudes.add(solicitudNovedad);
            logger.info("**__**Ciclo de beneficiarioa a inactivar" + beneficiario.getTipoBeneficiario()+ " getMotivoDesafiliacionAfiliado"+datosNovedadConsecutiva.getMotivoDesafiliacionAfiliado());
        }
        return listaSolicitudes;
    }
    
    /**
     * Transforma la información básica del beneficiario para el registro de la novedad
     * @param beneficiarioModeloDTO
     *        Información beneficiario
     * @param persona
     *        Modelo para el registro de novedad
     * @param estadoAfiliado 
     */
    public static void llenarDatosBeneficiarioNovedad(BeneficiarioModeloDTO beneficiarioModeloDTO, DatosPersonaNovedadDTO persona, EstadoAfiliadoEnum estadoAfiliado) {
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
        if(estadoAfiliado!=null&&estadoAfiliado.equals(EstadoAfiliadoEnum.ACTIVO)){
            persona.setTipoIdentificacion(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacion(beneficiarioModeloDTO.getNumeroIdentificacion());
            persona.setTipoIdentificacionTrabajador(beneficiarioModeloDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacionTrabajador(beneficiarioModeloDTO.getNumeroIdentificacion());
        }else {
            llenarDatosAfiliadoNovedad(beneficiarioModeloDTO.getAfiliado(),persona);
        }
    }
    
    /**
     * Obtiene la información del tipo de transacción para la inactivación de la clasificación enviada
     * @param clasificacion
     *        Clasificación de beneficiario a inactivar
     * @return Tipo transaccion enum
     */
    public static TipoTransaccionEnum getTipoTransaccionInactivarBeneficiarioByClasificacion(ClasificacionEnum clasificacion) {
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
    
    
    /**
     * @param rolAfiliadoModeloDTO
     */
    public void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        logger.info("Inicio ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO)");
        AfiliadoModeloDTO afiliadoModeloDTO = rolAfiliadoModeloDTO.getAfiliado();
        /* Si el motivo de desafiliación es por fallecimiento se actualizan los datos de la persona. */
        if (MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO.equals(rolAfiliadoModeloDTO.getMotivoDesafiliacion())) {
            ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(afiliadoModeloDTO.getNumeroIdentificacion(),
                    afiliadoModeloDTO.getTipoIdentificacion());
            consultarDatosPersona.execute();
            PersonaModeloDTO personaModeloDTO = consultarDatosPersona.getResult();
            personaModeloDTO.setFallecido(afiliadoModeloDTO.getFallecido());
            personaModeloDTO.setFechaFallecido(afiliadoModeloDTO.getFechaFallecido());
            personaModeloDTO.setFechaDefuncion(afiliadoModeloDTO.getFechaDefuncion());

            /* Se llama el servicio para actualizar la persona */
            /*
            ActualizarDatosPersona actualizarDatosPersona = new ActualizarDatosPersona(personaModeloDTO);
            actualizarDatosPersona.execute();
            */
             
            ActualizarDatosPersonaRutine a = new ActualizarDatosPersonaRutine();
            a.actualizarDatosPersona(personaModeloDTO, entityManager);
        }
        /* Se actualiza el rol afiliado a Inactivo, con el motivo de desafiliación */
        /*
        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
        actualizarRolAfiliado.execute();
        */
         
        ActualizarRolAfiliadoRutine ac = new ActualizarRolAfiliadoRutine();
        ac.actualizarRolAfiliado(rolAfiliadoModeloDTO, entityManager);
        
        
        if (rolAfiliadoModeloDTO.getIdEmpleador() != null) {
            /* Se valida si el Empleador queda con Cero trabajadores */
            ValidarEmpleadorCeroTrabajadores validarEmpleadorCeroTrabajadores = new ValidarEmpleadorCeroTrabajadores(
                    rolAfiliadoModeloDTO.getIdEmpleador());
            validarEmpleadorCeroTrabajadores.execute();
            Boolean empleadorCeroTrabajadores = validarEmpleadorCeroTrabajadores.getResult();
 
            if (empleadorCeroTrabajadores) {
                 logger.info("verdadero empleador con todos los trabajadores inacivos");
                ConsultarEmpleadorId consultarEmpleadorId = new ConsultarEmpleadorId(rolAfiliadoModeloDTO.getIdEmpleador());
                consultarEmpleadorId.execute();
                EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorId.getResult();
                /* Si queda con cero trabajadores se actualiza el valor de la fecha y el conteo con cero trabajadores */
                Calendar fechaActual = Calendar.getInstance();
                empleadorModeloDTO.setFechaRetiroTotalTrabajadores(fechaActual.getTimeInMillis());
                Short cantidadCeroTrabajadores = 0;
                if (empleadorModeloDTO.getCantIngresoBandejaCeroTrabajadores() != null) {
                    cantidadCeroTrabajadores = empleadorModeloDTO.getCantIngresoBandejaCeroTrabajadores();
                }
                cantidadCeroTrabajadores++;
                empleadorModeloDTO.setCantIngresoBandejaCeroTrabajadores(cantidadCeroTrabajadores);
                /*
                ModificarEmpleador modificarEmpleador = new ModificarEmpleador(empleadorModeloDTO);
                modificarEmpleador.execute();
                */
                
                ModificarEmpleadorRutine m = new ModificarEmpleadorRutine();
                m.modificarEmpleador(empleadorModeloDTO, entityManager);
            }
        }
        if(rolAfiliadoModeloDTO.getTipoAfiliado() == TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE && 
         (rolAfiliadoModeloDTO.getCanalRecepcion() != CanalRecepcionEnum.APORTE_MANUAL && 
            rolAfiliadoModeloDTO.getCanalRecepcion() != CanalRecepcionEnum.PILA)
        ){
            logger.info("Inicio Actualizacion cartera");
            ConsultarPersonaEmpleador c = new ConsultarPersonaEmpleador(rolAfiliadoModeloDTO.getIdEmpleador());
            c.execute();
            Persona dataPersonaEmpresa = c.getResult();
            Date fecha = new Date(rolAfiliadoModeloDTO.getFechaRetiro());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
            String periodo = formato.format(fecha);
            ActualizarDeudaPresuntaCarteraAsincrono actualizarDeuda = new ActualizarDeudaPresuntaCarteraAsincrono(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR,dataPersonaEmpresa.getNumeroIdentificacion(),periodo,dataPersonaEmpresa.getTipoIdentificacion());
            actualizarDeuda.execute();
        }
        logger.info("Fin ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO)");
    }
    
    
    
    /**
     * @param idSolicitudNovedad
     * @param listPersonasVerificar
     * @param userDTO
     */    
    //@Override
    public void verificarPersonaNovedadRegistrarAnalisisFovis(Long idSolicitudNovedad, List<PersonaDTO> listPersonasVerificar,
            UserDTO userDTO) {
        try { 
            // Lista de solicitudes tareas a registrar
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaModelDTO = new ArrayList<>();
            // Se realiza la busqueda de las personas identificando si se enceuntran asociadas a pstuilaciones
            ConsultarPostulacionVigentePersonas consultarPostulacionVigentePersonas = new ConsultarPostulacionVigentePersonas(
                    listPersonasVerificar);
            consultarPostulacionVigentePersonas.execute();
            // Se obtiene la lista personas con postulacion de la lista de personas enviada
            List<PersonaPostulacionDTO> listPersonasPostulacion = consultarPostulacionVigentePersonas.getResult();
            if (listPersonasPostulacion != null && !listPersonasPostulacion.isEmpty()) {
                for (PersonaPostulacionDTO personaPostulacionDTO : listPersonasPostulacion) {
                     System.out.println("anexando lista: " );
                    listSolAnaModelDTO.add(construirSolicitudAnalisisFOVISNovedad(personaPostulacionDTO, idSolicitudNovedad, userDTO));
                }
            }
            // Se realiza la radicacion de la(s) solicitud(s) de analisis
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolRadica = null;
            if (!listSolAnaModelDTO.isEmpty()) {
                System.out.println("**__**radicarSolicitudAnalisisNovedad verificarPersonaNovedadRegistrarAnalisisFovis: " + idSolicitudNovedad);
                listSolRadica = radicarSolicitudAnalisisNovedad(listSolAnaModelDTO, userDTO);
                System.out.println("**__**FinradicarSolicitudAnalisisNovedad verificarPersonaNovedadRegistrarAnalisisFovis: " + idSolicitudNovedad);
            }
            if (listSolRadica != null && !listSolRadica.isEmpty()) {
                System.out.println("**__**inicia for listSolRadica: "+listSolRadica);
                for (SolicitudAnalisisNovedadFOVISModeloDTO fovisModeloDTO : listSolRadica) {
                    // Se inicia la tarea BPM 
                    //if( userDTO.getSedeCajaCompensacion() != null ){
                        userDTO.setSedeCajaCompensacion("1");
                        System.out.println("**__**inicia for listSolRadica: userDTO.getSedeCajaCompensacion() "+userDTO.getSedeCajaCompensacion());
                    iniciarProcesoAnalisisNovedad(fovisModeloDTO, userDTO);
                   // }
                }
                // Se actualiza el estado de la(s) solicitud(s)
                crearSolicitudAnalisisNovedadFOVIS(listSolRadica);
            }else{
                System.out.println("**__**FinradicarSolicitudAnalisisNovedad verificarPersonaNovedadRegistrarAnalisisFovis Vacio: " + listSolRadica);
            }
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    /**
     * Inicia el proceso BPM para la HU 325-77 
     * @param solicitudAnalisisNovedadFOVISModeloDTO
     *        Informacion solicitud analisis novedad
     */
    private void iniciarProcesoAnalisisNovedad(SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO, UserDTO user) {
        try {
           if( user.getSedeCajaCompensacion() != null ){
            // Mapa de parametros del proceso BPM
            Map<String, Object> parametrosProceso = new HashMap<String, Object>();
            parametrosProceso.put("idSolicitud", solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud());
            parametrosProceso.put("numeroRadicado", solicitudAnalisisNovedadFOVISModeloDTO.getNumeroRadicacion());
            // Se asigna automaticamente la tarea
            ProcesoEnum procesoEnum = solicitudAnalisisNovedadFOVISModeloDTO.getTipoTransaccion().getProceso();
            Long sedeLong=Long.valueOf(user.getSedeCajaCompensacion());
            String destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(sedeLong, procesoEnum);
            UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
            String sedeDestinatario = usuarioDTO.getCodigoSede();
            parametrosProceso.put("usuarioBack", usuarioDTO.getNombreUsuario());
            // se actualiza el estado, destinatario y sede de la solicitud*/
            solicitudAnalisisNovedadFOVISModeloDTO.setDestinatario(destinatario);
            solicitudAnalisisNovedadFOVISModeloDTO.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
            solicitudAnalisisNovedadFOVISModeloDTO.setEstadoSolicitud(EstadoSolicitudAnalisisNovedadFovisEnum.PENDIENTE);
            System.out.println("**__**iniciarProcesoAnalisisNovedad "+solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud());
            System.out.println("**__**iniciarProcesoAnalisisNovedad procesoEnum"+procesoEnum);
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            IniciarProceso iniciarProcesoService = new IniciarProceso(procesoEnum, parametrosProceso);
            iniciarProcesoService.setToken(token.getToken());
            iniciarProcesoService.execute();
            Long idInstanciaProceso = iniciarProcesoService.getResult();
            System.out.println("**__**iniciarProcesoAnalisisNovedad idInstanciaProceso" + idInstanciaProceso);
            solicitudAnalisisNovedadFOVISModeloDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
           }
        } catch (Exception e) {
            logger.error(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,e);
        }
    }
    
    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     * 
     * @param nombreUsuarioCaja
     *            <code>String</code> El nombre de usuario del funcionario de la
     *            caja que realiza la consulta
     * 
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     *         usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.info("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        usuarioDTO = (UsuarioDTO) obtenerDatosUsuariosCajaCompensacionService.getResult();
        logger.info("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return usuarioDTO;
    }
    
    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     * 
     * @param sedeCaja
     *            <code>Long</code> el identificador del afiliado
     * @param procesoEnum
     *            <code>ProcesoEnum</code> el identificador del afiliado
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
     * Realiza la radicacion de las solicitudes de analsis de novedad de persona que afecta FOVIS
     * @param listSolAnaModelDTO
     *        Lista solicitudes
     * @param userDTO
     *        Informacion usuario loqueado
     * @return Lista que contiene las solicitudes creadas
     */
    private List<SolicitudAnalisisNovedadFOVISModeloDTO> radicarSolicitudAnalisisNovedad(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaModelDTO, UserDTO userDTO) {
                    logger.info("Inicia radicarSolicitudAnalisisNovedad>>");
        // Se ejecuta el servicion de registro de solicitud analisis
        List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaRegis = crearSolicitudAnalisisNovedadFOVIS(listSolAnaModelDTO);
        if (listSolAnaRegis != null && !listSolAnaRegis.isEmpty()) {
            // Se crea la lista de id Solicitud para la asociacion del numero radicado
            List<Long> listIdSolicitud = new ArrayList<>();
            for (SolicitudAnalisisNovedadFOVISModeloDTO dto : listSolAnaRegis) {
                listIdSolicitud.add(dto.getIdSolicitud());
            }
            // Se obtiene el numero de radicacion de cada solicitud
            Map<String, String> mapNumRadicadoSol = radicarListaSolicitudes(listIdSolicitud,userDTO.getSedeCajaCompensacion(), userDTO);
            // Se agrega el numero de radicacion
            String idSol;
            for (SolicitudAnalisisNovedadFOVISModeloDTO analisisNovedadFOVISModeloDTO : listSolAnaRegis) {
                idSol = analisisNovedadFOVISModeloDTO.getIdSolicitud().toString();
              //  if (mapNumRadicadoSol.containsKey(idSol)) {
              //      analisisNovedadFOVISModeloDTO.setNumeroRadicacion(mapNumRadicadoSol.get(idSol));
              //  }

            if(idSol == null || idSol == ""){
                                logger.info("**__**idSol vacio ");  
            }else{
                try{
                    if (mapNumRadicadoSol.containsKey(idSol)) {
                        logger.info("**__**mapNumRadicadoSol.get(idSol) "+mapNumRadicadoSol.get(idSol));  
                        analisisNovedadFOVISModeloDTO.setNumeroRadicacion(mapNumRadicadoSol.get(idSol));
                    }
                }catch(Exception m){
                logger.info("**__**catch error radicarSolicitudAnalisisNovedad "+m);  
                }
            
            }
            }
             logger.info("Fin radicarSolicitudAnalisisNovedad>>1");
            return listSolAnaRegis;
        }
         logger.info("Fin radicarSolicitudAnalisisNovedad>>2");
        return null;
    }
    
    /**
     * Realiza el llamado al servicio que genera el radicado de solicitud para una lista de solicitudes
     * @param sede
     *        Sede de radicacion
     * @param listIdSolicitud
     *        Lista de solicitudes radicadas
     * @return Map con la informacion del radicado de cada solicitud
     */
    private Map<String, String> radicarListaSolicitudes(List<Long> listIdSolicitud, String sede, UserDTO userDTO) {
        logger.info("Inicia radicarSolicitudAnalisisNovedad{}");
        System.out.println(" inicia radicarListaSolicitudes ");
        try {

            Map<String, String> mapResult = new HashMap<>();
            // Se consulta la informacion de las solicitudes enviadas
            // List<Solicitud> listaSolicitud = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_POR_IDS, Solicitud.class)
            System.out.println("**__** cambio createQuery en radicarListaSolicitudesNovedades");
            List<Solicitud> listaSolicitud = entityManager.createQuery("SELECT sol FROM Solicitud sol WHERE sol.idSolicitud IN (:idsSolicitud)", Solicitud.class)
                    .setParameter("idsSolicitud", listIdSolicitud).getResultList();
            if (listaSolicitud == null || listaSolicitud.isEmpty()) {
                System.out.println("- No existe solicitud");
                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
            }
            for (Solicitud solicitud : listaSolicitud) {
                if (solicitud != null) {
                    try {
                        // Se verifica que la solicitud no se haya radicado previamente
                        if (solicitud.getNumeroRadicacion() != null) {
                            System.out.println("- La solicitud ya fué radicada previamente");
                            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
                        }
                        System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina  radicarListaSolicitudes");
                        // Se calcula el numero de radicado
                        // Se actualiza la informacion de la solicitud
                        System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina entityManagerCore.merge(solicitud)");
                        NumeroRadicadoCorrespondenciaDTO numeroRadicadoDTO = obtenerNumeroRadicadoCorrespondencia(
                            TipoEtiquetaEnum.NUMERO_RADICADO, 1, userDTO, entityManager);
                    Calendar fecha = Calendar.getInstance();
                    String numeroRadicado = numeroRadicadoDTO.nextValue();
                        solicitud.setNumeroRadicacion(numeroRadicado);
                        solicitud.setFechaRadicacion(fecha.getTime());
                        entityManager.merge(solicitud);
                        System.out.println(" inicia NumeroRadicadoCorrespondenciaDTO en rutina entityManagerCore.merge(solicitud)numeroRadicado: "+numeroRadicado);
                        // Se marca el codigo como asignado
                        //asignarCodigoPreImpreso(numeroRadicado);
                        // Se agrega a la respuesta del servicio
                        System.out.println("**__**idsolicitu RadicarListaSolicitudesRutine " + solicitud.getIdSolicitud().toString()
                                + " solicitud.getNumeroRadicacion(): " + solicitud.getNumeroRadicacion());
                        mapResult.put(solicitud.getIdSolicitud().toString(), solicitud.getNumeroRadicacion());

                    } catch (Exception e) {
                        System.out.println(" Problema con la solicitud " + solicitud);
                    }
                }
            }
            System.out.println("finalizo..");
            return mapResult;

            //return consultasCore.radicarListaSolicitudesNovedades(listIdSolicitud,sede,userDTO);
        } catch (Exception e) {
            logger.info("**__**INICIA catch:RadicarListaSolicitudesRutine ");
            RadicarListaSolicitudesRutine r = new RadicarListaSolicitudesRutine();
            logger.info("Fin radicarListaSolicitudes{}1" + sede);

            logger.info("Fin radicarListaSolicitudes{}1 catch: sede catch:" + sede + " error: " + e);
            logger.info("Fin radicarListaSolicitudes{}1" + sede);
            return r.radicarListaSolicitudes(listIdSolicitud, sede, userDTO, entityManager);
        }

    } 
    	  private NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(TipoEtiquetaEnum tipoEtiqueta, Integer cantidad, UserDTO userDTO, 
	          EntityManager entityManager) {
	        String firmaServicio = "consultarRegistroListaEspecialRevision(TipoEtiquetaEnum, Integer, UserDTO)";
	        System.out.println("Inicia "+ firmaServicio);
	        
	        NumeroRadicadoUtil util = new NumeroRadicadoUtil();
            System.out.println(" obtenerNumeroRadicadoCorrespondencia: tipoEtiqueta: "+tipoEtiqueta+" cantidad: "+cantidad+" userDTO: "+userDTO);
	        NumeroRadicadoCorrespondenciaDTO num = util.obtenerNumeroRadicadoCorrespondencia(entityManager, tipoEtiqueta, cantidad, userDTO);
	        System.out.println("Finaliza "+ firmaServicio);
	        return num;
	    }
    /**
     * Realiza el llamado al servicio que crea la solicitud de analisis
     * @param listSolAnaModelDTO
     *        Lista de solicitudes a crear
     * @return Lista de solicitudes creadas
     */
    private List<SolicitudAnalisisNovedadFOVISModeloDTO> crearSolicitudAnalisisNovedadFOVIS(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolAnaModelDTO) {
       /*CrearActualizarListaSolicitudAnalisisNovedadFOVIS crearSolicitudAnalisis = new CrearActualizarListaSolicitudAnalisisNovedadFOVIS(listSolAnaModelDTO);
        crearSolicitudAnalisis.execute();
        return crearSolicitudAnalisis.getResult();
        */
        return crearActualizarListaSolicitudAnalisisNovedadFOVIS(listSolAnaModelDTO);
    }
    
    private List<SolicitudAnalisisNovedadFOVISModeloDTO> crearActualizarListaSolicitudAnalisisNovedadFOVIS(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes) {
        String firma = "crearSolicitudAnalisisNovedadFOVIS(List<SolicitudAnalisisNovedadFOVISModeloDTO>****):List<SolicitudAnalisisNovedadFOVISModeloDTO>";
        logger.info(ConstantesComunes.INICIO_LOGGER + firma);
        // Se realiza la iteracion y agregacion de los registros
        for (SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO : listSolicitudes) {
            crearActualizarSolicitudAnalisisNovedadFOVIS(solicitudAnalisisNovedadFOVISModeloDTO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return listSolicitudes;
    }
    
    private SolicitudAnalisisNovedadFOVISModeloDTO crearActualizarSolicitudAnalisisNovedadFOVIS(
            SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO) {
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = solicitudAnalisisNovedadFOVISModeloDTO.convertToEntity();
        if (solicitudAnalisisNovedadFovis.getIdSolicitudAnalisisNovedadFovis() == null) {
            entityManager.persist(solicitudAnalisisNovedadFovis);
        }
        else {
            entityManager.merge(solicitudAnalisisNovedadFovis);
        }
        solicitudAnalisisNovedadFOVISModeloDTO.convertToDTO(solicitudAnalisisNovedadFovis);
        return solicitudAnalisisNovedadFOVISModeloDTO;
    }

    /*
    private SolicitudAnalisisNovedadFOVISModeloDTO crearActualizarSolicitudAnalisisNovedadFOVIS(
            SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO) {
      
        if (solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitudAnalisisNovedadFovis() == null) {
                SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisFovis = null;
                logger.info("**__**solicitudAnalisisNovedadFovis- EstadoSolicitud: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitudAnalisisNovedadFovis());
            logger.info("**__**solicitudAnalisisNovedadFovis- EstadoSolicitud: "+ solicitudAnalisisNovedadFOVISModeloDTO.getEstadoSolicitud());
            logger.info("**__**solicitudAnalisisNovedadFovis- IdPostulacionFovis: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdPostulacionFOVIS());
            logger.info("**__**solicitudAnalisisNovedadFovis- IdSolicitudNovedad: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitudNovedad());
            logger.info("**__**solicitudAnalisisNovedadFovis- Observaciones: "+ solicitudAnalisisNovedadFOVISModeloDTO.getObservaciones());
            logger.info("**__**solicitudAnalisisNovedadFovis- IdSolicitudGlobal: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud());
            logger.info("**__**solicitudAnalisisNovedadFovis- IdPersonaNovedad: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitudNovedad());
            logger.info("**__**solicitudAnalisisNovedadFovis- getIdPersonaNovedad: "+ solicitudAnalisisNovedadFOVISModeloDTO.getIdPersonaNovedad());
		       solicitudAnalisisNovedadFOVISModeloDTO.setObservaciones("");
                solicitudAnalisisNovedadFOVISModeloDTO.setIdSolicitud(0l);
        try {
      // if(solicitudAnalisisNovedadFOVISModeloDTO.getObservaciones() == null){
      // solicitudAnalisisNovedadFOVISModeloDTO.setObservaciones("");
      // }
      // if(solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud() == null){
      // solicitudAnalisisNovedadFOVISModeloDTO.setIdSolicitud(0l);
      // }

	StoredProcedureQuery query = entityManager
		.createNamedStoredProcedureQuery(NamedQueriesConstants.STORED_PROCEDURE_INSERTAR_SOLICITUD_ANALISIS_NOVEDAD_FOVIS);

	    query.setParameter("EstadoSolicitud", solicitudAnalisisNovedadFOVISModeloDTO.getEstadoSolicitud().toString());
            query.setParameter("IdPostulacionFovis", BigInteger.valueOf(Long.parseLong(solicitudAnalisisNovedadFOVISModeloDTO.getIdPostulacionFOVIS().toString())));
            query.setParameter("IdSolicitudNovedad", BigInteger.valueOf(Long.parseLong(solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitudNovedad().toString())));
            query.setParameter("Observaciones", solicitudAnalisisNovedadFOVISModeloDTO.getObservaciones());
            query.setParameter("IdSolicitudGlobal", solicitudAnalisisNovedadFOVISModeloDTO.getIdSolicitud());
            query.setParameter("IdPersonaNovedad", BigInteger.valueOf(Long.parseLong(solicitudAnalisisNovedadFOVISModeloDTO.getIdPersonaNovedad().toString())));

		//	query.execute();
		logger.info(" **__**Finaliza la ejecucion ahora realizara la asignacion del resultado ");
			//result = (Date) query.getOutputParameterValue("dFechaHabil");
      
                 solicitudAnalisisNovedadFOVISModeloDTO.convertToDTO((SolicitudAnalisisNovedadFovis)query.getSingleResult());
		} catch (Exception e) {
			logger.info(" :: Hubo un error en el SP: "+e);
		}
           // entityManager.persist(solicitudAnalisisNovedadFovis);
        }
        else {
              SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = solicitudAnalisisNovedadFOVISModeloDTO.convertToEntity();
            entityManager.merge(solicitudAnalisisNovedadFovis);
          solicitudAnalisisNovedadFOVISModeloDTO.convertToDTO(solicitudAnalisisNovedadFovis);
        } 
        // entityManager.flush();
        logger.info("**__** Innsert a solicitudAnalisisNovedadFovis finalizo cierre de conexion  ");

        return solicitudAnalisisNovedadFOVISModeloDTO;
    }
*/
    
    /**
     * Construye la solicitud de analisis fovis de novedad de personaa
     * @param personaPostulacionDTO
     *        Informacion persona postulacion
     * @param idSolicitudNovedad
     *        Identificador solicitud novedad
     * @param userDTO
     *        Usuario autenticado
     * @return DTO informacion registro solicitud novedad
     */
    private SolicitudAnalisisNovedadFOVISModeloDTO construirSolicitudAnalisisFOVISNovedad(PersonaPostulacionDTO personaPostulacionDTO,
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
     * @param beneficiarioGrupoAfiliadoDTO
     */
    public void ejecutarActualizacionBeneficiario(BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliadoDTO) {
        logger.info("Inicio ejecutarActualizacionBeneficiario(BeneficiarioGrupoAfiliadoDTO)");

        /* se verifica si llega grupo familiar y se actualizar o crea */
        Long idGrupoFamiliar = null;
        if (beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar() != null) {
            beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar().setIdAfiliado(beneficiarioGrupoAfiliadoDTO.getAfiliado().getIdAfiliado());
            /* 
            ActualizarGrupoFamiliarPersona actualizarGrupoFamiliarService = new ActualizarGrupoFamiliarPersona(
                    beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar());
            actualizarGrupoFamiliarService.execute();
            idGrupoFamiliar = actualizarGrupoFamiliarService.getResult();
            */
            ActualizarGrupoFamiliarPersonaRutine a = new ActualizarGrupoFamiliarPersonaRutine();
            idGrupoFamiliar = a.actualizarGrupoFamiliarPersona(beneficiarioGrupoAfiliadoDTO.getGrupoFamiliar(), entityManager);
            
        }
        if (idGrupoFamiliar != null) {
            beneficiarioGrupoAfiliadoDTO.getBeneficiario().setIdGrupoFamiliar(idGrupoFamiliar);
        }
        /*
        ActualizarBeneficiario actualizarBeneficiarioService = new ActualizarBeneficiario(beneficiarioGrupoAfiliadoDTO.getBeneficiario());
        actualizarBeneficiarioService.execute();
        */ 
        
        ActualizarBeneficiarioRutine a =  new ActualizarBeneficiarioRutine();
        BeneficiarioModeloDTO beneficiario = beneficiarioGrupoAfiliadoDTO.getBeneficiario();
        if(beneficiario.getIdPersona() == null){
            List<Object> identificacionBeneficiario = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION)
                    .setParameter("tipoIdentificacion", beneficiario.getTipoIdentificacion())
                    .setParameter("numeroIdentificacion", beneficiario.getNumeroIdentificacion()).getResultList();
            
            if (identificacionBeneficiario != null && identificacionBeneficiario.size() > 0) {
                beneficiario.setIdPersona(Long.valueOf(identificacionBeneficiario.get(0).toString()));
            }
        }

        
      // logger.info("**__**ejecutarActualizacionBeneficiario->beneficiarioGrupoAfiliadoDTO.getFechaRetiro(): "+ beneficiarioGrupoAfiliadoDTO.getFechaRetiro());
      //  beneficiarioGrupoAfiliadoDTO.getBeneficiario().setFechaAfiliacion(beneficiarioGrupoAfiliadoDTO.getFechaRetiro());
        Long result = a.actualizarBeneficiario(beneficiario, entityManager);
         
        
        logger.info("**__**-ActualizarBeneficiarioRutine desde novedades compositeutil result : "+result);

        // Se realiza la actualizacion de la solicitud novedad persona, asociando el id beneficiario creado
        if (beneficiarioGrupoAfiliadoDTO.getSolicitudNovedadDTO() != null) {
            actualizarInfoSolicitudNovedad(beneficiarioGrupoAfiliadoDTO.getSolicitudNovedadDTO(),
                    result);
        }

        /*
        ActualizarDatosAfiliado actualizarAfiliadoService = new ActualizarDatosAfiliado(beneficiarioGrupoAfiliadoDTO.getAfiliado());
        actualizarAfiliadoService.execute();
        */


        ActualizarDatosAfiliadoRutine ada = new ActualizarDatosAfiliadoRutine();
        ada.actualizarDatosAfiliado(beneficiarioGrupoAfiliadoDTO.getAfiliado(), entityManager);

        logger.info("**__**-Fin ejecutarActualizacionBeneficiario(BeneficiarioGrupoAfiliadoDTO)");
    }

     public void actualizarEstadoSolicitudAfiliacionPersona(Long idSolicitud) {
    
        SolicitudAfiliacionPersona solicitudAfiliacionPersona = (SolicitudAfiliacionPersona) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_AFILIACION)
                .setParameter("idSolicitud", idSolicitud)
                .getSingleResult();
        solicitudAfiliacionPersona.setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
        entityManager.merge(solicitudAfiliacionPersona);            
    }
    
    /**
     * Actualiza la informacion de la solicitud novedad persona previamente registrada, agregandole el id Beneficiario, esto solo aplica
     * para las novedades de activar beneficiario
     * @param solicitudNovedadDTO
     *        Informacion solicitud novedad registrada
     * @param idBeneficiario
     *        Identificador beneficiario creado
     */
    private void actualizarInfoSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO, Long idBeneficiario) {
        // Se actualiza el id beneficiario en la solicitud de persona
            SolicitudNovedadPersona solicitudNovedadPersona = new SolicitudNovedadPersona();
        solicitudNovedadPersona.setIdBeneficiario(idBeneficiario);
        solicitudNovedadPersona.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
        /*
        ActualizarSolicitudNovedadPersona actualizarSolicitudNovedadPersona = new ActualizarSolicitudNovedadPersona(
                solicitudNovedadPersona);
        actualizarSolicitudNovedadPersona.execute();
        */
        ActualizarSolicitudNovedadPersonaRutine a = new ActualizarSolicitudNovedadPersonaRutine();
        a.actualizarSolicitudNovedadPersona(solicitudNovedadPersona, entityManager);
    }
    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     *
     * @param solicitudNovedadDTO datos de la novedad.
     * @param solicitudNovedad solicitud a modificar.
     * @param userDTO
     * @throws Exception
     */
    public void resolverNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws Exception {
        logger.info("Inicio de método resolverNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        try {
            // Se establece que la actualizacion de documentacion del afiliado no
            // ejecuta convertidor ya que esta novedad solo actualiza los documentos
            // de la lista de chequeo 
            logger.info("Transaccion en curso : " + solicitudNovedadDTO.getNovedadDTO().getNovedad());
            logger.info("NcompositeUtils ejecutarNovedad-> SolicitudNovedadDTO: ");

            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO)) {
                /* se ejecuta la novedad */
                actualizarEstadoSolicitudNovedad(solicitudNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
                logger.info("**__**entra al if resolver novedad} solicitudNovedadDTO.getNovedadDTO().getNovedad()" + solicitudNovedadDTO.getNovedadDTO().getNovedad());
                ejecutarNovedadReintegro(solicitudNovedadDTO, solicitudNovedad, userDTO);
                logger.info("SE EJECUTÓ LA NOVEDAD " + solicitudNovedadDTO.getNovedadDTO().getNovedad());
                
                // Una vez actualizado el RolAfiliado se debe crear una solicitud de novedad persona y novedad pila
                resolverSolicitudNovedadPersona(solicitudNovedadDTO, entityManager, false);
                // Actualiza la cartera del aportante -> HU-169
                //cerrarSolicitudNovedad(solicitudNovedadDTO, false);
                //actualizarCarteraAportante(solicitudNovedadDTO);
            }
            logger.info("**__**{[entra al if resolver novedad}]} solicitudNovedad.getIdSolicitud(): " + solicitudNovedad.getIdSolicitud());
            //se debe actualizar a cerrada la solicitud
        } catch (Exception e) {
            DatosExcepcionNovedadDTO data = new DatosExcepcionNovedadDTO(solicitudNovedadDTO, solicitudNovedad);

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            //--CLIENTE GuardarExcepcionNovedad--
            GuardarExcepcionNovedad guardarSrv = new GuardarExcepcionNovedad(exceptionAsString, data);
            guardarSrv.execute();

            //guardarExcepcionNovedad(data,exceptionAsString,userDTO);
            e.printStackTrace();

            //throw new Exception(e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info(
                "Fin de método resolverNovedadFront(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
    }

    public void resolverNovedadReintegroMismoEmpleador(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws Exception {
        logger.info("Inicio de método resolverNovedadReintegroMismoEmpleador(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        try {
            // Se establece que la actualizacion de documentacion del afiliado no
            // ejecuta convertidor ya que esta novedad solo actualiza los documentos
            // de la lista de chequeo 
            logger.info("resolverNovedadReintegroMismoEmpleador Transaccion en curso : " + solicitudNovedadDTO.getNovedadDTO().getNovedad());
            logger.info("resolverNovedadReintegroMismoEmpleador NcompositeUtils ejecutarNovedad-> SolicitudNovedadDTO: ");

            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().equals(TipoTransaccionEnum.NOVEDAD_REINTEGRO)) {
                /* se ejecuta la novedad */
               // actualizarEstadoSolicitudNovedad(solicitudNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadEnum.APROBADA);
                logger.info("resolverNovedadReintegroMismoEmpleador **__**entra al if resolver novedad} solicitudNovedadDTO.getNovedadDTO().getNovedad()" + solicitudNovedadDTO.getNovedadDTO().getNovedad());
                ejecutarNovedadReintegro(solicitudNovedadDTO, solicitudNovedad, userDTO);
                logger.info("resolverNovedadReintegroMismoEmpleador SE EJECUTÓ LA NOVEDAD " + solicitudNovedadDTO.getNovedadDTO().getNovedad());
                
                // Una vez actualizado el RolAfiliado se debe crear una solicitud de novedad persona y novedad pila
               resolverSolicitudNovedadPersona(solicitudNovedadDTO, entityManager, true);
                // Actualiza la cartera del aportante -> HU-169
                //cerrarSolicitudNovedad(solicitudNovedadDTO, false);
                //actualizarCarteraAportante(solicitudNovedadDTO);
            }
            //se debe actualizar a cerrada la solicitud
        } catch (Exception e) {
            DatosExcepcionNovedadDTO data = new DatosExcepcionNovedadDTO(solicitudNovedadDTO, solicitudNovedad);

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            //--CLIENTE GuardarExcepcionNovedad--
            GuardarExcepcionNovedad guardarSrv = new GuardarExcepcionNovedad(exceptionAsString, data);
            guardarSrv.execute();

            //guardarExcepcionNovedad(data,exceptionAsString,userDTO);
            e.printStackTrace();

            //throw new Exception(e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.info(
                "Fin de método resolverNovedadFront(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
    }
    
    /**
     * Consulta la informacion de RolAfiliado
     *
     * @param sede Sede de radicacion
     * @param listIdSolicitud Lista de solicitudes radicadas
     * @return Map con la informacion del radicado de cada solicitud
     */
    public Date getFechaRetiroByEmpleadorTrabajador(TipoIdentificacionEnum tipoIdenEmpleador, String numeroIdenEmpleador,
            TipoIdentificacionEnum tipoIdenTrabajador, String numeroIdenTrabajador, EntityManager entityManager) {
        logger.info("Inicia getRolAfiliadoByEmpleadorTrabajador{}");

        try {

            logger.info("Entity Manager en getFechaRetiroByEmpleadorTrabajador:" + entityManager);
            Date fechaRetiro = (Date) entityManager.createNativeQuery("SELECT roa.roaFechaRetiro\n"
                    + "            FROM RolAfiliado roa\n"
                    + "            JOIN Afiliado afi ON (roa.roaAfiliado = afi.afiId)\n"
                    + "            JOIN Persona per ON (afi.afiPersona = per.perId)\n"
                    + "            JOIN Empleador empl ON (roa.roaEmpleador = empl.empId)\n"
                    + "            JOIN Empresa emp ON (empl.empEmpresa = emp.empId)\n"
                    + "            JOIN Persona peremp ON (emp.empPersona = peremp.perId)            \n"
                    + "            WHERE \n"
                    + "			peremp.perTipoIdentificacion = :tipoIdEmpleador"
                    + "            AND peremp.perNumeroIdentificacion = :numeroIdEmpleador"
                    + "            AND per.perTipoIdentificacion = :tipoIdAfiliado"
                    + "            AND per.perNumeroIdentificacion = :perIdAfiliado")
                    .setParameter("tipoIdEmpleador", tipoIdenEmpleador.toString())
                    .setParameter("numeroIdEmpleador", numeroIdenEmpleador)
                    .setParameter("tipoIdAfiliado", tipoIdenTrabajador.toString())
                    .setParameter("perIdAfiliado", numeroIdenTrabajador)
                    .getSingleResult();
            return fechaRetiro;
        } catch (Exception e) {
            logger.info("**__**INICIA catch:getFechaRetiroByEmpleadorTrabajador :" + e);
            return null;
        }
    }

    /**
     * Consulta la fecha de RolAfiliado
     *
     * @param sede Sede de radicacion
     * @param listIdSolicitud Lista de solicitudes radicadas
     * @return Map con la informacion del radicado de cada solicitud
     */
    public List<Date> getFechaRolAfiliado(Long idSolicitudNovedad,Long idRolAfiliado,Long idAfiliado,EntityManager entityManager) {
        logger.info("Inicia getFechaRolAfiliado idSolicitudNovedad " + idSolicitudNovedad+" idRolAfiliado:"+idRolAfiliado+" idAfiliado:"+idAfiliado);
        List<Date> fechaRetiroList = new ArrayList<Date>();
        try {
            if(idRolAfiliado != null || idAfiliado != null){
                 fechaRetiroList = (List<Date>)  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_RETIRO_ROL_AFILIDO_REINTEGROS)
                .setParameter("idAfiliado", idAfiliado)
                .getResultList();
                if(fechaRetiroList == null)
                {
                    fechaRetiroList = (List<Date>)  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ULTIMO_RETIRO_POR_HISTORICO_ROLAFILIADO)
                    .setParameter("idRolAfiliado", idRolAfiliado)
                   .getResultList();
                }
            }else {
                 fechaRetiroList = (List<Date>)  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ULTIMO_RETIRO_POR_SOLICITUD)
                .setParameter("idSolicitudNovedad", idSolicitudNovedad)
                .getResultList();
            }
            logger.info("Entity Manager en getFechaRolAfiliado:" + entityManager);
           // return new Date(fechaRetiro.getTime());
            return fechaRetiroList;

        } catch (Exception e) {
            logger.info("**__**INICIA catch:getFechaRolAfiliado :" + e);
            return null;
        }
    }
    public Long getCantidadBenenficiariosAfiliado(Long idAfiliado,EntityManager entityManager) {
        logger.info("Inicia getCantidadBenenficiariosAfiliado idSolicitudNovedad  idAfiliado:"+idAfiliado);
        Object resultado = new Object();
        Long cantidadBeneficiarios = 0L;
        try {
            resultado = (Object)  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CANTIDAD_BENEFICIARIOS_AFILIADO)
                .setParameter("idAfiliado", idAfiliado)
                .getSingleResult();
                cantidadBeneficiarios =  Long.valueOf(resultado.toString());
            return cantidadBeneficiarios;

        } catch (Exception e) {
            logger.info("**__**INICIA catch:getFechaRolAfiliado :" + e);
            return null;
        }
    }
    public void actualizarBeneficiario(BeneficiarioDTO beneficiario) {
        ActualizarBeneficiarioSimpleRutine a = new ActualizarBeneficiarioSimpleRutine();
        a.actualizarBeneficiarioSimple(beneficiario, entityManager);
    }

	/**
     * Método encargado de ejecutar una novedad.
     *
     * @param solicitudNovedadDTO dto de la novedad, desde pantalla.
     * @param solicitudNovedad solicitud de la novedad.
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void ejecutarNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) /*throws ClassNotFoundException, InstantiationException, IllegalAccessException */{
        logger.info("Inicio de método ejecutarNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");

        /* se invoca el servicio para dicha novedad */
        try {
            NovedadCore servicioNovedad = NovedadAbstractFactory.getInstance()
                    .obtenerServicioNovedad(solicitudNovedadDTO.getNovedadDTO());
            logger.info("solicitudNovedadDTO.getNovedadDTO():" + solicitudNovedadDTO.getNovedadDTO());
            logger.info("servicioNovedad:" + servicioNovedad);
            logger.info("_getRutaCualificada: " + solicitudNovedadDTO.getNovedadDTO().getRutaCualificada());
            // Se instancia el servicio de la novedad
            // TODO: implementar transformarEjecutarRutinaNovedad para todos los convertidores
            if ("com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    || "com.asopagos.novedades.convertidores.persona.ActualizarNovedadPilaPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())
                    || "com.asopagos.novedades.convertidores.persona.ActualizarReintegroNovedadPersona".equals(solicitudNovedadDTO.getNovedadDTO().getRutaCualificada())) {
                logger.info("_ES NOVEDAD PILA_ O REINTEGRO");
                logger.debug("--Ncespedes-- ejecutarNovedad SolicitudNovedadDTO: ");
                servicioNovedad.transformarEjecutarRutinaNovedad(solicitudNovedadDTO, entityManager, userDTO);
            } else {
                logger.info("NcompositeUtils ejecutarNovedad-> SolicitudNovedadDTO: ");
                try {
                    servicioNovedad.transformarServicio(solicitudNovedadDTO);
                } catch (Exception e) {
                    logger.error("Error respuesta ServiceClient -> ejecutarNovedad:", e);
                }

            }
            logger.info("fin de método ejecutarNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
        } catch (Exception e) {
            logger.info("Error ejecutarNovedad: " + e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public SucursalEmpresaDTO getDatosAportanteYSucursalByCedula(String cedula, String tipoIdentificacion, EntityManager entity) {
        logger.info("Inicia getDatosAportanteYSucursalByCedula{} " + cedula + " " +  tipoIdentificacion);
        SucursalEmpresaDTO sucursalEmpresa = new SucursalEmpresaDTO();
        try {
            // Ajuste de empresas descentralizadas
            String[] checkEmpresas = cedula.split("S");
            
            Object[] datos = (Object[]) entity.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_Y_SUCURSAL)
                    .setParameter("cedula", cedula)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .setParameter("sucursal", checkEmpresas.length > 1 ? null : 1)
                    .getSingleResult();
            if (datos != null && datos[0] != null && datos[1] != null){

                sucursalEmpresa.setIdEmpleador(Long.valueOf(datos[0].toString()));
                sucursalEmpresa.setIdSucursalEmpresa(Long.valueOf(datos[1].toString()));
                logger.info("sucursalEmpresa.getIdSucursalEmpresa():" + sucursalEmpresa.getIdSucursalEmpresa());
                logger.info("sucursalEmpresa.getIdEmpleador():" + sucursalEmpresa.getIdEmpleador());
            }
        } catch (Exception e) {
            logger.info("Error: " + e);
        }
        return sucursalEmpresa;
    }

    public Object[] getDatosEmpleadorCedula(String cedula, String tipoIdentificacion, EntityManager entity) {
        logger.info("Inicia getDatosAportanteSoloByCedula{} " + cedula + " " +  tipoIdentificacion);
        Long idEmpleador = 0L;
        try {
            Object[] datos = (Object[]) entity.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_SOLO)
                    .setParameter("cedula", cedula)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .getSingleResult();
            if (datos != null && datos[0] != null ){
                return datos;
            }
        } catch (Exception e) {
            logger.info("Error: " + e);
        }
        return null;
    }

    public Empleador buscarEmpleadorbyRolAfliado(Long idRolAfiliado, EntityManager entity) {
        Empleador emp = null;
        logger.info("id rolAfiliado: " + idRolAfiliado);
        try {
            Object[] datosEmpleador = 
                (Object[]) entity.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMEPLADOR_POR_ROL_AFILIADO)
                .setParameter("rolAfiliado", idRolAfiliado)
                .getSingleResult();
            if (datosEmpleador != null) {
                emp.setIdEmpleador(Long.valueOf(datosEmpleador[0].toString()));
                emp.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(datosEmpleador[1].toString()));
                emp.setMotivoDesafiliacion(MotivoDesafiliacionEnum.valueOf(datosEmpleador[2].toString()));
            }
        } catch (Exception e) {
            logger.info("Error: " + e);
        }
        return emp;
    }

    public Long getDatosAportanteByCedula(String cedula, String tipoIdentificacion, EntityManager entity) {
        logger.info("Inicia getDatosAportanteByCedula{} " + cedula + " " +  tipoIdentificacion);
        try {
            Object datos = (Object) entity.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION)
                    .setParameter("numeroIdentificacion", cedula)
                    .setParameter("tipoIdentificacion", tipoIdentificacion)
                    .getSingleResult();
            if (datos != null){
                return Long.valueOf(datos.toString());
            }
        } catch (Exception e) {
            logger.error("Error en el metodo getDatosAportanteByCedula: "+ e.toString());
        }
        return 0L;
    }

    // Resolver SolicitudNovedadPersona
    public void resolverSolicitudNovedadPersona(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager eManager, Boolean mismoEmpleador) {
        logger.info("Inicio resolverSolicitudNovedadPersona: " + solicitudNovedadDTO.getIdSolicitud());

        // para este punto la solicitud novedad persona no pudo haber sido creada antes
        // Esto causaria que los triggers fueran lanzados para la solicitud afiliacion persona no tuvieran los estados adecuados
        Boolean cumpleCondicion = false;
        Empleador empleador = null;
        try {
            empleador = entityManager.find(Empleador.class, solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador());
        } catch (Exception e) {

            logger.error("No existe empleador" + e.getMessage());
        }
        
        if (empleador != null && empleador.getMotivoDesafiliacion() != null && empleador.getEstadoEmpleador().equals(EstadoEmpleadorEnum.INACTIVO)
                && (empleador.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.CERO_TRABAJADORES_NOVEDAD_INTERNA)
                || empleador.getMotivoDesafiliacion().equals(MotivoDesafiliacionEnum.CERO_TRABAJADORES_SOLICITUD_EMPLEADOR))) {
            cumpleCondicion = true;
        }

        if (!mismoEmpleador || cumpleCondicion) {

            if (solicitudNovedadDTO.getIdRegistroDetallado() != null) {

                Long idExistente = consultarSolicitudNovedadPila(solicitudNovedadDTO.getIdRegistroDetallado(), solicitudNovedadDTO.getIdRegistroDetalladoNovedad(), eManager);
                try {

                    SolicitudNovedadPila solNovPila = new SolicitudNovedadPila();
                    solNovPila.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
                    solNovPila.setIdRegistroDetallado(solicitudNovedadDTO.getIdRegistroDetallado());
                    if (solicitudNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)) {
                        solNovPila.setOriginadoEnAporteManual(solicitudNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL));
                    } else {
                        if (solicitudNovedadDTO.getIdRegistroDetalladoNovedad() != null) {
                            solNovPila.setIdRegistroDetalladoNovedad(solicitudNovedadDTO.getIdRegistroDetalladoNovedad());
                        }
                    }
                    if (idExistente == null) {
                        eManager.persist(solNovPila);
                    }

                } catch (PersistenceException ex) {
                    logger.info("Ya existe un registro con la misma combinación de registroDetallado y registroDetalladoNovedad. Se continúa sin persistir duplicado. : " + ex);
                } catch (Exception e) {
                    logger.info("Excepcion. fallo en consulta resolverSolicitudNovedadPila : " + e);
                }

            }
            Long idPersona = getDatosAportanteByCedula(solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion(),
                    solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion().name(), eManager);

            try {
                logger.info("Creando la solicitud novedad persona");
                SolicitudNovedadPersona solNovPersona = new SolicitudNovedadPersona();
                solNovPersona.setIdPersona(idPersona);
                solNovPersona.setIdRolAfiliado(solicitudNovedadDTO.getDatosPersona().getIdRolAfiliado());
                solNovPersona.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
                eManager.persist(solNovPersona);

                // Ajuste de 0 trabajadores novedad interna por pila o recaudo Manual
                if (solicitudNovedadDTO.getIdRegistroDetallado() != null && solicitudNovedadDTO.getDatosEmpleador() != null) {

                    empleador = entityManager.find(Empleador.class, solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador());
                    if (empleador != null && empleador.getEstadoEmpleador().equals(EstadoEmpleadorEnum.INACTIVO)
                            && cumpleCondicion) {
                        logger.info("**__** Deshacer gestion 0 trabajadores reintegro diferente empleador");
                        empleador.setMotivoDesafiliacion(null);
                        empleador.setFechaRetiro(null);
                        empleador.setFechaCambioEstadoAfiliacion(new Date());
                        empleador.setFechaGestionDesafiliacion(null);
                        empleador.setEstadoEmpleador(EstadoEmpleadorEnum.ACTIVO);
                        empleador.setNumeroTotalTrabajadores(1);
                        eManager.merge(empleador);


                        if (CanalRecepcionEnum.PILA.equals(solicitudNovedadDTO.getCanalRecepcion())) {
                            ConsultarDatosEmpleadorByRegistroDetallado consultarDatosEmpleadorByRegistroDetallado
                                    = new ConsultarDatosEmpleadorByRegistroDetallado(solicitudNovedadDTO.getIdRegistroDetallado());
                            consultarDatosEmpleadorByRegistroDetallado.execute();
                            Object[] datosEmpleador = consultarDatosEmpleadorByRegistroDetallado.getResult();
                            TipoIdentificacionEnum tipoIdEmpleador = TipoIdentificacionEnum.valueOf(datosEmpleador[0].toString());
                            String numeroIdEmpleador = datosEmpleador[1].toString();
                            ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
                            datosReintegro.setTipoIdEmpleador(tipoIdEmpleador);
                            datosReintegro.setNumIdEmpleador(numeroIdEmpleador);
                            datosReintegro.setCanalReintegro(CanalRecepcionEnum.PILA);
                            CrearSolicitudAfiliacionEmpleadorAportes servicio =
                                    new CrearSolicitudAfiliacionEmpleadorAportes(datosReintegro);
                            servicio.execute();
                        }

                    }
                }


            } catch (Exception e) {
                logger.info("Excepcion. fallo en consulta resolverSolicitudNovedadPersona : " + e);
            }
        }
    }

    public Long consultarSolicitudNovedadPila(Long idRegistroDetallado, Long idRegistroDetalladoNovedad, EntityManager entity) {
        logger.info("Inicia consultarSolicitudNovedadPila{} " + idRegistroDetallado + " " +  idRegistroDetalladoNovedad);
        Object id;
        try {
            id = (Object) entity.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_NOVEDAD_PILA)
                    .setParameter("idRegistroDetallado", idRegistroDetallado)
                    .setParameter("idRegistroDetalladoNovedad", idRegistroDetalladoNovedad)
                    .getSingleResult();
            if(id != null){
                return Long.valueOf(id.toString());
            }
        } catch (Exception ex){
            id =  null;
            logger.error("Error en el metodo consultarSolicitudNovedadPila: "+ ex.toString());
        }
        return (Long) id;
    }
}
