package com.asopagos.novedades.convertidores.empleador;
/**
 * 
 */

import javax.persistence.EntityManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ActualizarPersona;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos basicos de una persona empleador.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ActualizarPersonaNovedad implements NovedadCore{

	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		/*se consulta la persona*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpledorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpledorService.execute();
		empleador = (Empleador) consultarEmpledorService.getResult();
		Persona persona = empleador.getEmpresa().getPersona();
		
		switch (novedad) {
		case CAMBIO_TIPO_NUMERO_DOCUMENTO:
			if(datosEmpleador.getTipoIdentificacionNuevo()!= null){
				persona.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionNuevo());
			}
			if(datosEmpleador.getNumeroIdentificacionNuevo()!=null){
				persona.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionNuevo());	
			}
			if(datosEmpleador.getDigitoVerificacionNuevo()!=null){
				persona.setDigitoVerificacion(datosEmpleador.getDigitoVerificacionNuevo());
			}
			break;
		case CAMBIO_RAZON_SOCIAL_NOMBRE:
			if(datosEmpleador.getRazonSocial()!=null){
				persona.setRazonSocial(datosEmpleador.getRazonSocial());
			}
			if(datosEmpleador.getPrimerNombre()!=null){
				persona.setPrimerNombre(datosEmpleador.getPrimerNombre());
			}
			if(datosEmpleador.getSegundoNombre()!=null){
				persona.setSegundoNombre(datosEmpleador.getSegundoNombre());
			}
			if(datosEmpleador.getPrimerApellido()!=null){
				persona.setPrimerApellido(datosEmpleador.getPrimerApellido());
			}
			if(datosEmpleador.getSegundoApellido()!=null){
				persona.setSegundoApellido(datosEmpleador.getSegundoApellido());
			}
			break;
		default:
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
		
		ActualizarPersona actualizarPersonaService = new ActualizarPersona(persona);
		return actualizarPersonaService;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
