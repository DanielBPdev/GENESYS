package com.asopagos.notificaciones.destinatarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import com.asopagos.enumeraciones.core.GrupoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.CorreoPrioridadPersonaDTO;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.UsuarioDTO;

/**
 * Clase que representa al coordinador del proceso responsable de las afiliaciones de persona. <br/>
 *
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class SupervisorAfiliacionPersona extends DestinatarioNotificacion{

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SupervisorAfiliacionPersona.class);
    
    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
        List<CorreoPrioridadPersonaDTO> correosDestinatarios = new ArrayList<CorreoPrioridadPersonaDTO>();
        
        ObtenerMiembrosGrupo obtenerMiembrosGrupo = new ObtenerMiembrosGrupo(GrupoEnum.SUPERVISOR_AFILIACION_PERSONA.getNombre(),null,EstadoUsuarioEnum.ACTIVO);
        obtenerMiembrosGrupo.execute();
        List<UsuarioDTO> miembros = obtenerMiembrosGrupo.getResult();
        for (UsuarioDTO miembro : miembros) {
            if(StringUtils.isNotEmpty(miembro.getEmail())){
                CorreoPrioridadPersonaDTO correoRequest = new CorreoPrioridadPersonaDTO();
                correoRequest.setEmail(miembro.getEmail());
                correosDestinatarios.add(correoRequest);    
            }
        }
        logger.debug("Finaliza obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
        return correosDestinatarios;
    }
}
