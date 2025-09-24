/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliadosEmpleadorMasivo;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.DesafiliarEmpleadoresAutomatico;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para Desafiliar Empleador
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarDesafiliarEmpAutomaticamente implements NovedadCore {

    private final ILogger logger = LogManager.getLogger(ActualizarDesafiliarEmpAutomaticamente.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.debug("Inicio de método ActualizarDesafiliarEmpAutomaticamente.transformarServicio");
        /* se transforma a un objeto de datos del empleador */
        DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
        TipoTransaccionEnum tipoTransaccionEnum = solicitudNovedadDTO.getTipoTransaccion();

        DatosNovedadAutomaticaDTO datosEmpleadorNovedad = new DatosNovedadAutomaticaDTO();
        datosEmpleadorNovedad.setMotivoDesafiliacion(MotivoDesafiliacionEnum.CERO_TRABAJADORES_NOVEDAD_INTERNA);
        datosEmpleadorNovedad.setIdEmpleadores(datosEmpleador.getIdEmpleadoresPersona());

        if (TipoTransaccionEnum.DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA.equals(tipoTransaccionEnum)) {
            datosEmpleadorNovedad.setMotivoDesafiliacion(MotivoDesafiliacionEnum.ANULADO);
            // Se consultan e inactivan los Roles asociados a los empleadores.
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
            RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
            novedadCascada.execute();
        }
        DesafiliarEmpleadoresAutomatico desafiliarEmpleadoresAutomatico = new DesafiliarEmpleadoresAutomatico(datosEmpleadorNovedad);
        return desafiliarEmpleadoresAutomatico;
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
