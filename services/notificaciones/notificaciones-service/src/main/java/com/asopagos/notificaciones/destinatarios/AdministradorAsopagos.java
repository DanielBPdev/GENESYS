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
 * <b>Descripción:</b>Clase que representa al administrador de Asopagos(genesys). para la gestion de los archivos de consumo de manera
 * manual <br/>
 * <b>Módulo:</b> Asopagos - ANEXO-ANIBOL<br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 */
public class AdministradorAsopagos extends DestinatarioNotificacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AdministradorAsopagos.class);

    @Override
    public List<CorreoPrioridadPersonaDTO> obtenerCorreoDestinatario(TipoTransaccionEnum tipoTx, Map<String, Object> parametros, EntityManager em) {
        logger.debug("Inicia obtenerCorreoDestinatario(Map<String, Long>, EntityManager)");
        List<CorreoPrioridadPersonaDTO> correosDestinatarios = new ArrayList<CorreoPrioridadPersonaDTO>();
        
        //TODO se debe verificar que el grupoEnum corresponda al rol Admnistrador Asopagos; o 
        //se puede mantener con el perfil indicado en codigo?; tener encuenta el tema del keycloak;y a que no corresponde con datos de core propiamente
        ObtenerMiembrosGrupo obtenerMiembrosGrupo = new ObtenerMiembrosGrupo(GrupoEnum.ANALISTA_SUBSIDIO_MONETARIO.getNombre(), null,
                EstadoUsuarioEnum.ACTIVO);
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
