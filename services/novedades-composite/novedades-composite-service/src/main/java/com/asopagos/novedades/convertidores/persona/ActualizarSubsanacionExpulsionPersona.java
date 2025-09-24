/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.Date;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarExpulsionSubsanada;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.modelo.ExpulsionSubsanadaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
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
public class ActualizarSubsanacionExpulsionPersona implements NovedadCore {

	private final ILogger logger = LogManager.getLogger(ActualizarSubsanacionExpulsionPersona.class);

	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarSubsanacionExpulsionEmpleador.transformarServicio");

		/* Se transforma a un objeto de datos del empleador */
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();

		/* Se consulta el ROL afiliado*/
		ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(datosPersona.getIdRolAfiliado());
		consultarRolAfiliado.execute();
		RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado.getResult();
		
		ExpulsionSubsanadaModeloDTO expulsionSubsanadaModeloDTO =  new ExpulsionSubsanadaModeloDTO(); 
		expulsionSubsanadaModeloDTO.setExpulsionSubsanada(Boolean.TRUE);
		expulsionSubsanadaModeloDTO.setFechaSubsancionExpulsion(new Date().getTime());
		expulsionSubsanadaModeloDTO.setMotivoSubsanacionExpulsion(datosPersona.getMotivoSubsanacionExpulsion());
		expulsionSubsanadaModeloDTO.setIdRolAfiliado(rolAfiliadoDTO.getIdRolAfiliado());
		
		ActualizarExpulsionSubsanada actualizarExpulsionSubsanada = new ActualizarExpulsionSubsanada(expulsionSubsanadaModeloDTO);
		return actualizarExpulsionSubsanada;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }

}
