/**
 *
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
import com.asopagos.novedades.composite.clients.EjecutarRetiroTrabajadores;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliadosEmpleador;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosAfiliacion;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliadosEmpleadorMasivo;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorId;
import com.asopagos.empleadores.clients.ConsultarUltimaSolicitudEmpleador;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarDesafiliacion;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.composite.dto.EmpleadorAfiliadosDTO;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.Asynchronous;
import com.asopagos.novedades.clients.EjecutarDesafiliacionTrabajadoresEmpledorMasivo; 
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import java.util.stream.Collectors;

/**
 * Clase que contiene la lógica para desafiliar un empleador y sus trabajadores
 * activos Esto incluye la novedad DESAFILIACION de Empleadores y la automática
 * de personas.
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class DesafiliarEmpleadorAfiliadosNovedad implements NovedadCore {

    private final ILogger logger = LogManager.getLogger(DesafiliarEmpleadorAfiliadosNovedad.class);

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.info("Inicio de método ActualizarEmpleadorNovedad.transformarServicio");

        /* se transforma a un objeto de datos del empleador */
        DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
        logger.info("**__**DesafiliarEmpleadorAfiliadosNovedad2");
        /* se consulta el empleador */
        ConsultarEmpleadorId consultarEmpledorService = new ConsultarEmpleadorId(datosEmpleador.getIdEmpleador());
        logger.info("**__** Empezo a consultar el empleador");
        consultarEmpledorService.execute();
        EmpleadorModeloDTO empleador = consultarEmpledorService.getResult();
        /* se prepara Empelador para ser desafiliado  */
        empleador.setMotivoDesafiliacion(datosEmpleador.getMotivoDesafiliacion());
        empleador.setEstadoEmpleador(EstadoEmpleadorEnum.INACTIVO);
        empleador.setFechaRetiro((new Date()).getTime());
        empleador.setCanalReingreso(null);
        empleador.setReferenciaAporteReingreso(null);
        EmpleadorAfiliadosDTO desafiliacionDTO = new EmpleadorAfiliadosDTO();
        desafiliacionDTO.setEmpleador(empleador);
        desafiliacionDTO.setInactivarCuentaWeb(datosEmpleador.getRequiereInactivacionCuentaWeb());
        logger.info("**__**EjecutarDesafiliacionen convertidores");
        EjecutarDesafiliacion ejecutarDesafiliacionService = new EjecutarDesafiliacion(desafiliacionDTO);
        /*se ejecuta desafiliación*/
        ejecutarDesafiliacionService.execute();
        /*Inicia Proceso en segundo plano*/
        ValidarTimer(datosEmpleador, empleador, solicitudNovedadDTO);
        return ejecutarDesafiliacionService;

    }

    @Asynchronous
    public void ValidacionMasiva(DatosEmpleadorNovedadDTO datosEmpleador, EmpleadorModeloDTO empleador, SolicitudNovedadDTO solicitudNovedadDTO) {
        try {
            /* se consulta los afiliados de los empleadores asociados al afiliado. */
            ConsultarRolesAfiliadosEmpleador consultarRolesService = new ConsultarRolesAfiliadosEmpleador(datosEmpleador.getIdEmpleador(), EstadoAfiliadoEnum.ACTIVO);
            consultarRolesService.execute();
            List<RolAfiliadoModeloDTO> roles = consultarRolesService.getResult();
            List<BeneficiarioModeloDTO> listaBeneficiarios = null;
            /* se recorre datos corespondientes a los roles, y se procede inactivar cada empleado y beneficiario asignado a el empleador*/
            // Se realiza la ejecución de retiro de trabajadores en casacada

            //CONDICIONAL PARA VER SI TIENE MAS DE 10 TRABAJADORES LO HACE EL PROCEDIMIENTO ALMACENADO
            if(roles.size()>=10){
                  logger.info("**__**INGRESA A EJECUTAR AL SP PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS CON roles.size(): "+roles.size());
                 try {
                    EjecutarDesafiliacionTrabajadoresEmpledorMasivo ejecutarDesafiliacionTrabajadoresEmpledorMasivo = 
                    new EjecutarDesafiliacionTrabajadoresEmpledorMasivo(solicitudNovedadDTO.getNumeroRadicacion(),datosEmpleador.getIdEmpleador());
                        ejecutarDesafiliacionTrabajadoresEmpledorMasivo.execute();
                         logger.info("**__**Finaliza EjecutarDesafiliacionTrabajadoresEmpledorMasivo composite");
	                // StoredProcedureQuery query = entityManager
	                // .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_DESAFILIACION_EMPLEADOR_TRABAJADORES_MASIVOS);
                    //query.setParameter("numerRadicacionEmpresa", solicitudNovedadDTO.getNumeroRadicacion());
                    //query.setParameter("idEmpledor", datosEmpleador.getIdEmpleador());
                    //query.execute(); 
                } catch (Exception e) {
                    logger.error("ERROR DESAFILIAION: ", e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                }
            }else{
                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                datosNovedadConsecutivaDTO.setFechaRetiro(empleador.getFechaRetiro());
                datosNovedadConsecutivaDTO.setListaRoles(roles);
                datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.DESAFILIACION_EMPLEADOR);
                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
                datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedadDTO.getNovedadDTO().getNovedad());
                logger.info("**__**DesafiliarEmpleadorAfiliadosNovedadRadicarSolicitudNovedadCascada2");
                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                novedadCascada.execute();
    
                for (RolAfiliadoModeloDTO RolAfiliadoModeloDTO : roles) {
                    logger.info("itero");
                    // se consulta nuevamente le rol espesifico 
                    ConsultarRolAfiliado rolAfiliadoService = new ConsultarRolAfiliado(RolAfiliadoModeloDTO.getIdRolAfiliado());
                    rolAfiliadoService.execute();
                    RolAfiliadoModeloDTO rolAfiliadoModeloDTO = rolAfiliadoService.getResult();
                    listaBeneficiarios = consultarBeneficiariosAfiliacion(RolAfiliadoModeloDTO.getAfiliado().getIdAfiliado(), RolAfiliadoModeloDTO.getIdRolAfiliado());

                    
                    if (rolAfiliadoModeloDTO.getEstadoAfiliado().equals(EstadoAfiliadoEnum.ACTIVO)) {
                        //se modifican los campos correspondientes a la desafiliacion 
                        logger.info("INGRESO AL ESTADO ACTIVO ");
                        rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
                        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
                        //Ajuste GLPI 91359
                        rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.DESAFILIACION_EMPLEADOR);
                        logger.info(rolAfiliadoModeloDTO.getMotivoDesafiliacion());
                        ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
                    }
                    logger.info(RolAfiliadoModeloDTO.getAfiliado().getTipoIdentificacion());
                    logger.info(RolAfiliadoModeloDTO.getAfiliado().getNumeroIdentificacion());
                    ConsultarRolesAfiliado consultarRolesAfiliado = new ConsultarRolesAfiliado(null,RolAfiliadoModeloDTO.getAfiliado().getNumeroIdentificacion(),RolAfiliadoModeloDTO.getAfiliado().getTipoIdentificacion());
                    consultarRolesAfiliado.execute();
                    List<RolAfiliadoEmpleadorDTO> rolesAfiliado = consultarRolesAfiliado.getResult();
                    List<RolAfiliadoEmpleadorDTO> rolesAfiliadoActivo = (List<RolAfiliadoEmpleadorDTO>) rolesAfiliado.stream().filter(e -> e.getRolAfiliado().getEstadoAfiliado().toString().equals("ACTIVO")).collect(Collectors.toList());
                    for (BeneficiarioModeloDTO BeneficiarioModeloDTO : listaBeneficiarios) {
                        if (BeneficiarioModeloDTO.getEstadoBeneficiarioAfiliado().equals(EstadoAfiliadoEnum.ACTIVO) && rolesAfiliadoActivo.isEmpty()) {
                            logger.info("se desafilio por alternativa ya que fallo la novedad " + BeneficiarioModeloDTO.getEstadoBeneficiarioAfiliado().toString());
                            BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
                            beneficiarioModeloDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
                            beneficiarioModeloDTO.setEstadoBeneficiarioCaja(EstadoAfiliadoEnum.INACTIVO);
                            beneficiarioModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum.DESAFILIACION_EMPLEADOR);
                            beneficiarioModeloDTO.setFechaRetiro(new Date().getTime());
                            BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliado = new BeneficiarioGrupoAfiliadoDTO();
                            beneficiarioGrupoAfiliado.setBeneficiario(beneficiarioModeloDTO);
                            NovedadesCompositeUtils n = new NovedadesCompositeUtils();
                            n.ejecutarActualizacionBeneficiario(beneficiarioGrupoAfiliado);
                        }

                    }
                }
                

                logger.info("**__**FinDesafiliarEmpleadorAfiliadosNovedadRadicarSolicitudNovedadCascada2");
                /* se instancia el servicio de la novedad */
                logger.info("Fin de método ActualizarEmpleadorNovedad.transformarServicio desaFILIACION");
                }
                /*continua proceso de desafiliacion normal y envia a creación de la solicitudes */
                if (MotivoDesafiliacionEnum.ANULADO.equals(datosEmpleador.getMotivoDesafiliacion())) {
                    ConsultarUltimaSolicitudEmpleador consultaSolicitud = new ConsultarUltimaSolicitudEmpleador(datosEmpleador.getIdEmpleador());
                    consultaSolicitud.execute();
                    SolicitudDTO ultimaSolicitud = consultaSolicitud.getResult();
                    if (ultimaSolicitud != null) { 
                        ultimaSolicitud.setAnulada(true);
                        ActualizarSolicitudAfiliacionPersona actualizarSolicitud = new ActualizarSolicitudAfiliacionPersona(
                                ultimaSolicitud.getIdSolicitud(), ultimaSolicitud);
                        actualizarSolicitud.execute();
                    }
            }

        } catch (Exception e) {
            logger.info(e);
            logger.info("ERROR metodo aSYC");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }


    synchronized void ValidarTimer(DatosEmpleadorNovedadDTO datosEmpleador, EmpleadorModeloDTO empleador, SolicitudNovedadDTO solicitudNovedadDTO) {
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                try {
                    /*envia datos a proceso asycn para validar  la desafiliacion de cada ROL*/
                    ValidacionMasiva(datosEmpleador, empleador, solicitudNovedadDTO);
                } catch (Exception e) {
                    System.out.println(e);
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                } finally {
                    timer.cancel();
                }
            }
        };
        timer.schedule(tarea, 1);
    }

     private List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
        logger.info("Ingreso a consultar los beneficiarios y esto es el idAfiliado y IdRolAfiliado DESAFILIACION" + idAfiliado + " " + idRolAfiliado);
        ConsultarBeneficiariosAfiliacion consultarBeneficiariosAfiliacionService = new ConsultarBeneficiariosAfiliacion(idAfiliado,
                idRolAfiliado);
        consultarBeneficiariosAfiliacionService.execute();
        return consultarBeneficiariosAfiliacionService.getResult();
    }

    private void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        EjecutarRetiroTrabajadores ejecucionRetiro = new EjecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
        ejecucionRetiro.execute();
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub

    }

}
