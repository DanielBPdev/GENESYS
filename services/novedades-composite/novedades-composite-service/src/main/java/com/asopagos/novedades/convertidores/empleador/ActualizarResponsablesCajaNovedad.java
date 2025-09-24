/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.empleadores.clients.ActualizarResponsablesCajaCompensacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los responsables del empleador por novedad.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ActualizarResponsablesCajaNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarResponsablesCajaNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.DatosNovedadDTO, com.asopagos.enumeraciones.core.TipoTransaccionEnum)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarResponsablesCajaNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		List<String> responsables = new ArrayList<>();
		if(datosEmpleador.getResponsable1CajaContacto() != null){
			responsables.add(datosEmpleador.getResponsable1CajaContacto());
		}
		if(datosEmpleador.getResponsable2CajaContacto() != null){
			responsables.add(datosEmpleador.getResponsable2CajaContacto());
		}

		/*se instancia el servicio de la novedad*/
		ActualizarResponsablesCajaCompensacion actualizarResponsablesService = new ActualizarResponsablesCajaCompensacion(datosEmpleador.getIdEmpleador(),responsables);
		logger.debug("Fin de método ActualizarResponsablesCajaNovedad.transformarServicio");
		
		return actualizarResponsablesService;
	}
    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
