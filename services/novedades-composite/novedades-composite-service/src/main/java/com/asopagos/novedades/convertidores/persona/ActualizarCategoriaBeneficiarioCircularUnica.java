package com.asopagos.novedades.convertidores.persona;

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.CambiarCategoriaBeneficiarioHijosCertificadoEscolar;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Cambiar automáticamente
 * la categoría para beneficiarios que cumplan  <br>
 * 
 * <b>Historia de Usuario:</b> HU 496
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ActualizarCategoriaBeneficiarioCircularUnica implements NovedadCore{

    private final ILogger logger = LogManager.getLogger(ActualizarCategoriaBeneficiarioCircularUnica.class);

    
   	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(
	 * com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.info("Inicio de método ActualizarCategoriaBeneficiarioCircularUnica.transformarServicio");
		/* Se transforma a un objeto de datos del empleador */
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		/*
		 * Se instancia el servicio masivo de la novedad
		 */
		CambiarCategoriaBeneficiarioHijosCertificadoEscolar cambiarCategoriaBeneficiarioHijosCertificadoEscolar = new CambiarCategoriaBeneficiarioHijosCertificadoEscolar(
				datosPersona.getIdBeneficiarios());
		logger.info("Fin de método ActualizarCategoriaBeneficiarioCircularUnica.transformarServicio");
		return cambiarCategoriaBeneficiarioHijosCertificadoEscolar;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
