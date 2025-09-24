package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliaciones.clients.ConsultarUltimaAfiliacionPersona;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosAfiliacion;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarRetiroTrabajadores;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.composite.clients.InsercionMonitoreoLogs;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.composite.service.ejb.NovedadesCompositeBusiness;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliacionpersonasrutines.actualizarsolicitudafiliacionpersona.ActualizarSolicitudAfiliacionPersonaRutine;
import com.asopagos.services.common.ServiceClient;
import com.jcraft.jsch.Logger;
import java.util.concurrent.TimeUnit;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import java.text.SimpleDateFormat;
import com.asopagos.empleadores.clients.ConsultarPersonaEmpleador;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCartera;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.cache.CacheManager;
import java.util.Calendar;
import java.util.Date;
import com.asopagos.constants.ParametrosSistemaConstants;
import java.lang.Exception;
/**
 * Clase que contiene la lógica para actualizar por retiro un trabajador.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarRetiroNovedadPersona implements NovedadCore {
    
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ActualizarRetiroNovedadPersona.class);

    private List<TipoTransaccionEnum> retiro;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.info("[ActualizarRetiroNovedadPersona:transformarServicio]");
        logger.info(solicitudNovedadDTO.toString());
        /* se transforma a un objeto de datos del empleador */
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(datosPersona.getIdRolAfiliado());
       // Date fechaInicioNovedad = new Date(TimeUnit.SECONDS.toMillis(datosPersona.getFechaInicioNovedad())); 
        logger.info("[ActualizarRetiroNovedadPersona:transformarServicio] fechaInicioNovedad:"+datosPersona.getFechaInicioNovedad());
        consultarRolAfiliado.execute();
        RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado.getResult();
        /* Asocia los tipos de Novedad de Persona. */
        this.agregarListaNovedades();
        try{
            if (retiro.contains(novedad) && rolAfiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
        
                rolAfiliadoDTO.setMotivoDesafiliacion(datosPersona.getMotivoDesafiliacionTrabajador());
                System.out.println(rolAfiliadoDTO.getMotivoDesafiliacion());
                rolAfiliadoDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
                /* mantis 229181 cuando el estado de entidad pagadora es diferente de null se cambia
                * a inactivo */
                if (rolAfiliadoDTO.getEstadoEnEntidadPagadora() != null){
                    rolAfiliadoDTO.setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum.INACTIVO);
                }
                // Novedad PILA, APORTES, CARTERA y novedad cascada envian la fecha de retiro
                if(datosPersona.getFechaInicioNovedad()!=null){
                    System.out.println("Retiro - fecha inicio getFechaInicioNovedad"+ datosPersona.getFechaInicioNovedad());
                    rolAfiliadoDTO.setFechaRetiro(datosPersona.getFechaInicioNovedad());
                }else{  
                    rolAfiliadoDTO.setFechaRetiro(datosPersona.getFechaRetiro());
                }

                if(rolAfiliadoDTO.getClaseTrabajador() != null && rolAfiliadoDTO.getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA) ){
                    rolAfiliadoDTO.setFechaFinCondicionVet(new Date());
                }
                // Se borra la información de estos campos para el control de los registros que se hacen por pila en el reingreso
                rolAfiliadoDTO.setCanalReingreso(null);
                rolAfiliadoDTO.setReferenciaAporteReingreso(null);
                rolAfiliadoDTO.setReferenciaSolicitudReingreso(null);
                /* Si el retiro es por fallecimiento se asigna la fecha de fallecimiento */
                if (MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                    System.out.println("Retiro - Fallecimiento");
                    AfiliadoModeloDTO afiliadoModeloDTO = rolAfiliadoDTO.getAfiliado();
                    afiliadoModeloDTO.setFallecido(Boolean.TRUE);
                    afiliadoModeloDTO.setFechaDefuncion(datosPersona.getFechaDefuncion());
                    if (datosPersona.getFechaReporteFallecimientoTrabajador() == null) {
                        System.out.println("Retiro - Reporte");
                        afiliadoModeloDTO.setFechaFallecido(new Date().getTime());
                    } else {
                        afiliadoModeloDTO.setFechaFallecido(datosPersona.getFechaReporteFallecimientoTrabajador());
                    }
                }

                /* Si el retiro es a un dependiente se asigna la fecha de inicio y fin de servicios */
                if(rolAfiliadoDTO.getTipoAfiliado() == TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE){
                    logger.info("Fecha inicio/fin servicios");
                    AfiliadoModeloDTO afiliadoModeloDTO = rolAfiliadoDTO.getAfiliado();
                    Date fechaActual = new Date();
                    afiliadoModeloDTO.setFechaInicioServiciosSinAfiliacion(rolAfiliadoDTO.getFechaRetiro());

                    Long diasReintegro =  new Long((String)CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA));
                    
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date(rolAfiliadoDTO.getFechaRetiro())); 
                    calendar.add(Calendar.DAY_OF_MONTH, diasReintegro.intValue());

                    Date fechaFin = calendar.getTime();
                    afiliadoModeloDTO.setFechaFinServicioSinAfiliacion(fechaFin.getTime());
                }
                
                /* Si el retiro es por anulacion se marca la solicitud de afiliación con que se activo*/
                if (MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                    System.out.println("Retiro - AFILIACION_ANULADA");
                    ConsultarUltimaAfiliacionPersona consultUltimaSolicitud = new ConsultarUltimaAfiliacionPersona(rolAfiliadoDTO.getIdRolAfiliado());
                    consultUltimaSolicitud.execute();
                    SolicitudAfiliacionPersonaDTO ultimaSolicitud = consultUltimaSolicitud.getResult();
                    SolicitudDTO solicitudUpdate = new SolicitudDTO();
                    if (ultimaSolicitud != null) {
                        System.out.println("Retiro - AFILIACION_ANULADA -> ultima afiliacion");
                        solicitudUpdate.setAnulada(true);
                        ActualizarSolicitudAfiliacionPersona actualizarSolicitud = new ActualizarSolicitudAfiliacionPersona(
                                ultimaSolicitud.getIdSolicitudGlobal(), solicitudUpdate);
                        actualizarSolicitud.execute();
                    }

                    //SolicitudDTO solicitudModificar = ultimaSolicitud;
                    //ultimaSolicitud.setAnulada(true);
                    //ActualizarSolicitudAfiliacionPersona actualizarSolicitud = new actualizarSolicitudAfiliacionPersona(ultimaSolicitud.getIdSolicitud(), ultimaSolicitud);
                    //actualizarSolicitud.execute();
                }
                // Si el motivo de desafiliacion es diferente a SUSTITUCIÓN PATRONAL se ejecuta la desafiliación de beneficiarios
                System.out.println("Motivo desafiliacion " + datosPersona.getMotivoDesafiliacionTrabajador());
                if (!MotivoDesafiliacionAfiliadoEnum.SUSTITUCION_PATRONAL.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                    System.out.println("**__**Retiro - retiro de los beneficiarios getIdAfiliado: "+rolAfiliadoDTO.getAfiliado().getIdAfiliado()+" getIdRolAfiliado: "+rolAfiliadoDTO.getIdRolAfiliado());
                    List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiariosAfiliacion(rolAfiliadoDTO.getAfiliado().getIdAfiliado(),
                            rolAfiliadoDTO.getIdRolAfiliado());
                    for(BeneficiarioModeloDTO beneficiario: listaBeneficiarios){
                        System.out.println("**__**Beneficiarios novedad retiro Pila: "+beneficiario.toString());
                    }
                    DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                    datosNovedadConsecutivaDTO.setFechaRetiro(rolAfiliadoDTO.getFechaRetiro());
                    System.out.println("**__Cantidad Listado Bneficiario a inactivar: "+listaBeneficiarios.size());
                    datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                    datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(datosPersona.getMotivoDesafiliacionTrabajador());
                    datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
                    datosNovedadConsecutivaDTO.setRolAfiliadoDTO(rolAfiliadoDTO);
                    datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(novedad);
                    RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                    novedadCascada.execute(); 
                }
                /**Agregado modificacion retiro trabajadores porqque  VerificarPersonaNovedadRegistrarAnalisisFovis genera error de respue4sta */
                EjecutarRetiroTrabajadores ejecutarRetiroTrabajadores = new EjecutarRetiroTrabajadores(rolAfiliadoDTO);
                    System.out.println("FIN transformarServicio");
                    ejecutarRetiroTrabajadores.execute();
                /**FIN Agregado modificacion retiro trabajadores porqque  VerificarPersonaNovedadRegistrarAnalisisFovis genera error */
                    // Se verifica si la persona esta asociada a una postulacion FOVIS
                List<PersonaDTO> listaPersonas = obtenerPersonasNovedad(rolAfiliadoDTO);
                // Se crea la tarea para revisarla en la HU325-77
                System.out.println("**__**VerificarPersonaNovedadRegistrarAnalisisFovis INICIA POSIBLE BLOQUEO"+solicitudNovedadDTO.getIdSolicitudNovedad());
                VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                        solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
                verificarPersonaNovedadRegistrarAnalisisFovis.execute();
                System.out.println("**__**Bloqueo RETIRO ACTIVO VerificarPersonaNovedadRegistrarAnalisisFovis");
            }
            rolAfiliadoDTO.setCanalRecepcion(solicitudNovedadDTO.getCanalRecepcion());

            EjecutarRetiroTrabajadores ejecutarRetiroTrabajadores = new EjecutarRetiroTrabajadores(rolAfiliadoDTO);
            System.out.println("FIN transformarServicio");
            return ejecutarRetiroTrabajadores;
        }catch(Exception e){
            logger.error("Error en la rutina de ActualizarRetiroNovedadPersona", e);
            InsercionMonitoreoLogs monitoreo = new InsercionMonitoreoLogs(e.getMessage(),solicitudNovedadDTO.getIdSolicitudNovedad() + " Error en la rutina de ActualizarRetiroNovedadPersona ");
            monitoreo.execute();
            return null;
        }
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void agregarListaNovedades() {
        /* Novedad 240 - 248 back */
        retiro = new ArrayList<>();
        retiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
        retiro.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR);
        retiro.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS);
    }

    /**
     * Realiza el llamado al servicio que consulta los beneficiarios asociados a la afiliación objeto del retiro
     * @param idAfiliado
     *        Identificador afiliado
     * @param idRolAfiliado
     *        Identificador del rol afiliado
     * @return
     */
    private List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
        ConsultarBeneficiariosAfiliacion consultarBeneficiariosAfiliacionService = new ConsultarBeneficiariosAfiliacion(idAfiliado,
                idRolAfiliado);
        consultarBeneficiariosAfiliacionService.execute();
        return consultarBeneficiariosAfiliacionService.getResult();
    }

    /**
     * Obtiene la lista de personas desafiliadas en la novedad
     * @param listaBe
     * @param listaBeneficiarios
     *        Lista de Beneficiarios retirados
     * @param rolAfiliado
     *        Afiliado retirado
     * @return Lista de personas asociadas a la novedad
     */
    private List<PersonaDTO> obtenerPersonasNovedad(RolAfiliadoModeloDTO rolAfiliado) {
        List<PersonaDTO> listaPersonaConsulta = new ArrayList<>();
        // Se itera los roles y beneficiarios inactivados
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNumeroIdentificacion(rolAfiliado.getAfiliado().getNumeroIdentificacion());
        personaDTO.setTipoIdentificacion(rolAfiliado.getAfiliado().getTipoIdentificacion());
        listaPersonaConsulta.add(personaDTO);
        return listaPersonaConsulta;
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO) {
        logger.info("Inicia convertidor rutina ActualizarRetiroNovedadPersona");
        System.out.println(solicitudNovedadDTO.toString());
        
        NovedadesCompositeUtils novedadesCompositeUtils = new NovedadesCompositeUtils(entityManager);
        
        
        /* se transforma a un objeto de datos del empleador */
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(datosPersona.getIdRolAfiliado());
        consultarRolAfiliado.execute();
        RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado.getResult();
        /* Asocia los tipos de Novedad de Persona. */
        this.agregarListaNovedades();
        if (retiro.contains(novedad) && rolAfiliadoDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
            System.out.println("RETIRO ACTIVO");
            rolAfiliadoDTO.setMotivoDesafiliacion(datosPersona.getMotivoDesafiliacionTrabajador());
            System.out.println(rolAfiliadoDTO.getMotivoDesafiliacion());
            rolAfiliadoDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
            /* mantis 229181 cuando el estado de entidad pagadora es diferente de null se cambia
             * a inactivo */
            if (rolAfiliadoDTO.getEstadoEnEntidadPagadora() != null){
                rolAfiliadoDTO.setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum.INACTIVO);
            }
            // Novedad PILA, APORTES, CARTERA y novedad cascada envian la fecha de retiro
            if(datosPersona.getFechaInicioNovedad()!=null){
                System.out.println("Retiro - fecha inicio");
                rolAfiliadoDTO.setFechaRetiro(datosPersona.getFechaInicioNovedad());
            }else{
                rolAfiliadoDTO.setFechaRetiro(datosPersona.getFechaRetiro());
            }
            // Se borra la información de estos campos para el control de los registros que se hacen por pila en el reingreso
            rolAfiliadoDTO.setCanalReingreso(null);
            rolAfiliadoDTO.setReferenciaAporteReingreso(null);
            rolAfiliadoDTO.setReferenciaSolicitudReingreso(null);
            /* Si el retiro es por fallecimiento se asigna la fecha de fallecimiento */
            if (MotivoDesafiliacionAfiliadoEnum.FALLECIMIENTO.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                System.out.println("Retiro - Fallecimiento");
                AfiliadoModeloDTO afiliadoModeloDTO = rolAfiliadoDTO.getAfiliado();
                afiliadoModeloDTO.setFallecido(Boolean.TRUE);
                afiliadoModeloDTO.setFechaDefuncion(datosPersona.getFechaDefuncion());
                if (datosPersona.getFechaReporteFallecimientoTrabajador() == null) {
                    System.out.println("Retiro - Reporte");
                    afiliadoModeloDTO.setFechaFallecido(new Date().getTime());
                } else {
                    afiliadoModeloDTO.setFechaFallecido(datosPersona.getFechaReporteFallecimientoTrabajador());
                }
            }
            
            /* Si el retiro es por anulacion se marca la solicitud de afiliación con que se activo*/
            if (MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                System.out.println("Retiro - AFILIACION_ANULADA");
                ConsultarUltimaAfiliacionPersona consultUltimaSolicitud = new ConsultarUltimaAfiliacionPersona(rolAfiliadoDTO.getIdRolAfiliado());
                consultUltimaSolicitud.execute();
                SolicitudAfiliacionPersonaDTO ultimaSolicitud = consultUltimaSolicitud.getResult();
                SolicitudDTO solicitudUpdate = new SolicitudDTO();
                if (ultimaSolicitud != null) {
                    System.out.println("Retiro - AFILIACION_ANULADA -> ultima afiliacion");
                    solicitudUpdate.setAnulada(true);
                    
                    /*
                    ActualizarSolicitudAfiliacionPersona actualizarSolicitud = new ActualizarSolicitudAfiliacionPersona(
                            ultimaSolicitud.getIdSolicitudGlobal(), solicitudUpdate);
                    actualizarSolicitud.execute();
                    */
                    
                    ActualizarSolicitudAfiliacionPersonaRutine a = new ActualizarSolicitudAfiliacionPersonaRutine();
                    a.actualizarSolicitudAfiliacionPersona(ultimaSolicitud.getIdSolicitudGlobal(), solicitudUpdate, entityManager);
                }

                //SolicitudDTO solicitudModificar = ultimaSolicitud;
                //ultimaSolicitud.setAnulada(true);
                //ActualizarSolicitudAfiliacionPersona actualizarSolicitud = new actualizarSolicitudAfiliacionPersona(ultimaSolicitud.getIdSolicitud(), ultimaSolicitud);
                //actualizarSolicitud.execute();
            }
            // Si el motivo de desafiliacion es diferente a SUSTITUCIÓN PATRONAL se ejecuta la desafiliación de beneficiarios
            System.out.println("Motivo desafiliacion " + datosPersona.getMotivoDesafiliacionTrabajador());
            if (!MotivoDesafiliacionAfiliadoEnum.SUSTITUCION_PATRONAL.equals(datosPersona.getMotivoDesafiliacionTrabajador())) {
                System.out.println("Retiro - retiro de los beneficiarios");
                List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiariosAfiliacion(rolAfiliadoDTO.getAfiliado().getIdAfiliado(),
                        rolAfiliadoDTO.getIdRolAfiliado()); 
                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                datosNovedadConsecutivaDTO.setFechaRetiro(rolAfiliadoDTO.getFechaRetiro());
                datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(datosPersona.getMotivoDesafiliacionTrabajador());
                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
                datosNovedadConsecutivaDTO.setRolAfiliadoDTO(rolAfiliadoDTO);
                datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(novedad);
                /*
                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                novedadCascada.execute();
                */
                 System.out.println("Retiro -radicarSolicitudNovedadCascada");
                novedadesCompositeUtils.radicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO,userDTO);
                
            }

            // Se verifica si la persona esta asociada a una postulacion FOVIS
            List<PersonaDTO> listaPersonas = obtenerPersonasNovedad(rolAfiliadoDTO);
            // Se crea la tarea para revisarla en la HU325-77
            /*
            VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                    solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
            verificarPersonaNovedadRegistrarAnalisisFovis.execute();
            */
             System.out.println("Retiro -verificarPersonaNovedadRegistrarAnalisisFovis");
            novedadesCompositeUtils.verificarPersonaNovedadRegistrarAnalisisFovis(solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas, userDTO);
        }

        //EjecutarRetiroTrabajadores ejecutarRetiroTrabajadores = new EjecutarRetiroTrabajadores(rolAfiliadoDTO);    
          System.out.println("Retiro -ejecutarRetiroTrabajadores");
        novedadesCompositeUtils.ejecutarRetiroTrabajadores(rolAfiliadoDTO);
        
        System.out.println("FIN transformarServicio");
        logger.info("Fin convertidor rutina ActualizarRetiroNovedadPersona");
        //return ejecutarRetiroTrabajadores;
        
    }
}