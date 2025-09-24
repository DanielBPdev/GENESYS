/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ConsultarNombreUsuarioPersonas;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.usuarios.clients.ActualizarEstadoUsuarioMasivo;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Inactivar Cuenta Web Masivamente.
 * Novedad 30.
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarInactivarCuentaWebPersona implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarInactivarCuentaWebPersona.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarInactivarCuentaWeb.transformarServicio");
		/*se transforma a un objeto de datos del empleador*/
		DatosPersonaNovedadDTO datosPersonaNovedadDTO = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		
		/*Se consultan los nombres de usuario de los Empleadores asociados a la solicitud.*/
		ConsultarNombreUsuarioPersonas consultarNombreUsuarioPersonas = new ConsultarNombreUsuarioPersonas(datosPersonaNovedadDTO.getIdPersonas());
		consultarNombreUsuarioPersonas.execute();
		List<String> nombreUsuarios = consultarNombreUsuarioPersonas.getResult();
		
		/*Se instancia el servicio masivo de la novedad Inactivar Cuentas Web Personas.*/
		ActualizarEstadoUsuarioMasivo actualizarEstadoUsuarioMasivo = new ActualizarEstadoUsuarioMasivo(Boolean.FALSE, nombreUsuarios);
		logger.debug("Fin de método ActualizarInactivarCuentaWeb.transformarServicio");
		return actualizarEstadoUsuarioMasivo;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
