/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.CambiarCategoriaBeneficiarioXEdad;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Cambiar automáticamente
 * la categoría “Z” para beneficiarios que cumplan “X” años de edad <br>
 * 
 * <b>Historia de Usuario:</b> HU 496
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ActualizarCategoriaBeneficiarioEdadX implements NovedadCore {

	private final ILogger logger = LogManager.getLogger(ActualizarCategoriaBeneficiarioEdadX.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(
	 * com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarCategoriaBeneficiarioEdadX.transformarServicio");
		/* Se transforma a un objeto de datos del empleador */
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		/*
		 * Se instancia el servicio masivo de la novedad
		 */
		CambiarCategoriaBeneficiarioXEdad cambiarCategoriaBeneficiario = new CambiarCategoriaBeneficiarioXEdad(
				datosPersona.getIdBeneficiarios());
		logger.debug("Fin de método ActualizarCategoriaBeneficiarioEdadX.transformarServicio");
		return cambiarCategoriaBeneficiario;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
