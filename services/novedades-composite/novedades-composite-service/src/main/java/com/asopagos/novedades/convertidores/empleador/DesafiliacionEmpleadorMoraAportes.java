package com.asopagos.novedades.convertidores.empleador;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliadosEmpleadorMasivo;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.DesafiliarEmpleadoresAutomatico;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.clients.EjecutarDesafiliacionTrabajadoresEmpledorMasivo;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.clients.EmpleadoresProcesar;
import java.util.concurrent.Callable;
import java.util.LinkedList;
import com.asopagos.enumeraciones.core.EstadoDesafiliacionMasivaEnum;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase que contiene la lógica para desafiliar un empleador y sus trabajadores activos
 * por mora en los aportes del empleador
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class DesafiliacionEmpleadorMoraAportes implements NovedadCore {

    private ExecutorService executorService;

    private int numCores = Runtime.getRuntime().availableProcessors();

    private final ILogger logger = LogManager.getLogger(DesafiliacionEmpleadorMoraAportes.class);

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.debug("Inicio de método DesafiliacionEmpleadorMoraAportes.transformarServicio");
        /* se transforma a un objeto de datos del empleador */
        DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();

        // Se consultan los roles asociados a los empleadores.
        ConsultarRolesAfiliadosEmpleadorMasivo consultarRolesAfiliadosEmpleadorMasivo = new ConsultarRolesAfiliadosEmpleadorMasivo(
                EstadoAfiliadoEnum.ACTIVO, datosEmpleador.getIdEmpleadoresPersona());
        consultarRolesAfiliadosEmpleadorMasivo.execute();
        List<RolAfiliadoModeloDTO> roles = consultarRolesAfiliadosEmpleadorMasivo.getResult();

        // Se realiza la ejecución de retiro de trabajadores en casacada
        DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
        datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
        datosNovedadConsecutivaDTO.setListaRoles(roles);
        datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum.DESAFILIACION_EMPLEADOR);
        datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
        datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedadDTO.getNovedadDTO().getNovedad());
        if(datosNovedadConsecutivaDTO.getTipoTransaccionOriginal() == TipoTransaccionEnum.DESAFILIACION_AUTOMATICA_POR_MORA && datosEmpleador.getIdEmpleadoresPersona().size() > 10 ){

            executorService = Executors.newFixedThreadPool(numCores);
            
            // try{
                // EmpleadoresProcesar empleadoresAproccesar = new EmpleadoresProcesar(datosEmpleador.getIdEmpleadoresPersona(),datosNovedadConsecutivaDTO.getNumeroRadicadoOriginal(),EstadoDesafiliacionMasivaEnum.ACTIVADO,0);
                // empleadoresAproccesar.execute();
                List<Callable<Void>> tareasParalelas = new LinkedList<>();
                for (Long idEmp : datosEmpleador.getIdEmpleadoresPersona()) {
                    logger.info("novedad " + datosNovedadConsecutivaDTO.getNumeroRadicadoOriginal().toString());
                    logger.info("empleador " + idEmp.toString());

                    Callable<Void> parallelTask = () -> {
                        try {
                            logger.info("inicia objeto en base de datos");
                            EjecutarDesafiliacionTrabajadoresEmpledorMasivo ejecutarDesafiliacion = new EjecutarDesafiliacionTrabajadoresEmpledorMasivo(datosNovedadConsecutivaDTO.getNumeroRadicadoOriginal(), idEmp);
                            ejecutarDesafiliacion.execute();
                        } catch (Exception e) {
                            logger.error( "desafiliacionEmpleador: " + idEmp + "desafiliarEmpleador novedad masiva cartera " + e.getMessage());
                        }
                        return null;
                    };
                    tareasParalelas.add(parallelTask);
                }
                // Imprimir las tareas antes de ejecutarlas
                for (Callable<Void> tarea : tareasParalelas) {
                    logger.info("Tarea preparada: " + tarea.toString());
                }
                try {
                    executorService.invokeAll(tareasParalelas);
                } catch (InterruptedException e) {
                    logger.error( " desafiliar empleadores anovedad masiva: ", e);
                    e.printStackTrace();
                }
            // }catch(Exception e){
            //     logger.warn("ocurrio un error al procesar la novedad de desafiliacion masiva para el empleador");
            // }
        }else{
            RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
            novedadCascada.execute();
        }

        DatosNovedadAutomaticaDTO datosEmpleadorNovedad = new DatosNovedadAutomaticaDTO();
        datosEmpleadorNovedad.setMotivoDesafiliacion(MotivoDesafiliacionEnum.EXPULSION_POR_MOROSIDAD);
        datosEmpleadorNovedad.setIdEmpleadores(datosEmpleador.getIdEmpleadoresPersona());

        DesafiliarEmpleadoresAutomatico desafiliarEmpleadoresAutomatico = new DesafiliarEmpleadoresAutomatico(datosEmpleadorNovedad);
        logger.info("Fin de método DesafiliacionEmpleadorMoraAportes.transformarServicio");
        return desafiliarEmpleadoresAutomatico;
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}