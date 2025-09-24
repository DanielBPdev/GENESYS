/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import javax.persistence.EntityManager;
import com.asopagos.empleadores.clients.InactivarBeneficios;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para Inactivar Beneficios Ley 1429 Año 2010
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarInactivarBeneficiosLey590 implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarInactivarBeneficiosLey590.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método InactivarBeneficiosLey1429Anio2010.transformarServicio");
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		
		/*Se instancia el servicio de la novedad Inactivar Beneficios Ley 590 de 2000*/
		InactivarBeneficios inactivarBeneficiosLey590 = new InactivarBeneficios(TipoBeneficioEnum.LEY_590, datosEmpleador.getIdEmpleadoresPersona());

		return inactivarBeneficiosLey590;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
