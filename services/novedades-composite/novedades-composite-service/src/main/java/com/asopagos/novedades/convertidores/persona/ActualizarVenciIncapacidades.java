/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.InactivarVencimientoIncapacidades;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Actualizar Vencimiento
 * de Incapacidades
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarVenciIncapacidades implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarVenciIncapacidades.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarVenciIncapacidades.transformarServicio");
		/*se transforma a un objeto de datos del empleador*/
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		
		/*Se instancia el servicio masivo de la novedad Inactivar Certificado de Escolaridad.*/
		InactivarVencimientoIncapacidades inactivarVencimientoIncapacidades = new InactivarVencimientoIncapacidades(datosPersona.getIdPersonas());
		logger.debug("Fin de método ActualizarVenciIncapacidades.transformarServicio");
		return inactivarVencimientoIncapacidades;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }
	
}
