/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.Date;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarExpulsionSubsanada;
import com.asopagos.dto.modelo.ExpulsionSubsanadaModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Realiza la actualizacion del empleador con el registro de la subsanacion de
 * expulsion
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ActualizarSubsanacionExpulsionEmpleador implements NovedadCore {

	private final ILogger logger = LogManager.getLogger(ActualizarSubsanacionExpulsionEmpleador.class);

	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarSubsanacionExpulsionEmpleador.transformarServicio");

		/* Se transforma a un objeto de datos del empleador */
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();

		/* Se consulta el empleador*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpledorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpledorService.execute();
		empleador = (Empleador) consultarEmpledorService.getResult();
		
		ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO =  new ExpulsionSubsanadaModeloDTO(); 
		expulsionSubsanadaModeloDTO.setExpulsionSubsanada(Boolean.TRUE);
		expulsionSubsanadaModeloDTO.setFechaSubsancionExpulsion(new Date().getTime());
		expulsionSubsanadaModeloDTO.setMotivoSubsanacionExpulsion(datosEmpleador.getMotivoSubsanacionExpulsion());
		expulsionSubsanadaModeloDTO.setIdEmpleador(empleador.getIdEmpleador());
		
		ActualizarExpulsionSubsanada actualizarExpulsionSubsanada = new ActualizarExpulsionSubsanada(expulsionSubsanadaModeloDTO);
		return actualizarExpulsionSubsanada;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
