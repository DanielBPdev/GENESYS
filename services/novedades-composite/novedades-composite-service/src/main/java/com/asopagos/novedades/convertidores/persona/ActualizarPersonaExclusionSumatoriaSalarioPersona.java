/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import javax.persistence.EntityManager;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.personas.clients.ActualizarExclusionSumatoriaSalario;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.dto.ActualizarExclusionSumatoriaSalarioDTO;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para actualizar la marca de exlusión de una persona
 * en la sumatoria de salarios
 * 
 *
 */
public class ActualizarPersonaExclusionSumatoriaSalarioPersona implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarPersonaExclusionSumatoriaSalarioPersona.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.info("Inicio de método ActualizarPersonaExclusionSumatoriaSalarioPersona.transformarServicio");

		/*se transforma a un objeto de datos la persona y la novedad */
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalarioDTO = new ActualizarExclusionSumatoriaSalarioDTO(datosPersona, novedad);
		
		/*Se instancia el servicio de la novedad que actualizara la marca de exclusión de la persona.*/
		ActualizarExclusionSumatoriaSalario actualizarExclusionSumatoriaSalario = new ActualizarExclusionSumatoriaSalario(actualizarExclusionSumatoriaSalarioDTO);
		
		logger.info("Fin de método ActualizarPersonaExclusionSumatoriaSalarioPersona.transformarServicio");
		return actualizarExclusionSumatoriaSalario;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }
	
}
