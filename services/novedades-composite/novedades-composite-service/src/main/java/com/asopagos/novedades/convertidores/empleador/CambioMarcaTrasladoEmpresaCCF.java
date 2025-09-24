package com.asopagos.novedades.convertidores.empleador;
// com.asopagos.novedades.convertidores.empleador.CambioMarcaTrasladoEmpresaCCF

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.CambiarMarcaEmpresaTrasladadaCCF;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

public class CambioMarcaTrasladoEmpresaCCF implements NovedadCore{
    private final ILogger logger = LogManager.getLogger(CambioMarcaTrasladoEmpresaCCF.class);

    
   	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(
	 * com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.info("Inicio de método CambioMarcaTrasladoEmpresaCCF.transformarServicio");
		/* Se transforma a un objeto de datos del empleador */
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador() ;
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		UbicacionDTO ubicacionDTO = new UbicacionDTO();

		logger.info("Aca traslado"+datosEmpleador.getTrasladoCajasCompensacion());

		/*
		 * Se instancia el servicio masivo de la novedad
		 */
		CambiarMarcaEmpresaTrasladadaCCF CambiarMarcaEmpresaTrasladadaCCF = new CambiarMarcaEmpresaTrasladadaCCF(datosEmpleador);
		logger.info("Fin de método CambioMarcaTrasladoEmpresaCCF.transformarServicio");
		return CambiarMarcaEmpresaTrasladadaCCF;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
    
}