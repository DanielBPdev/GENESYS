package com.asopagos.novedades.convertidores.empleador;
/**
 * 
 */

import javax.persistence.EntityManager;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empresas.clients.ActualizarEmpresa;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para validar 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ActualizarEmpresaNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarEmpresaNovedad.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarEmpresaNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		/*se consulta el empleador*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpleadorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpleadorService.execute();
		empleador = (Empleador) consultarEmpleadorService.getResult();
		Empresa empresa = empleador.getEmpresa();
		
		switch (novedad) {
		case CAMBIO_NATURALEZA_JURIDICA:
			empresa.setNaturalezaJuridica(datosEmpleador.getNaturalezaJuridica());
			break;
		case CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL:
			empresa.setCodigoCIIU(datosEmpleador.getCodigoCIIU());
			break;
		case CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB:
			empresa.setCodigoCIIU(datosEmpleador.getCodigoCIIU());
			break;
		case CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR:
			empresa.setArl(datosEmpleador.getArl().getIdARL() !=null?datosEmpleador.getArl():null);
			empresa.setNombreComercial(datosEmpleador.getNombreComercial());
			empresa.setPaginaWeb(datosEmpleador.getPaginaWeb());
			break;
		default:
			break;
		}

		/*se instancia el servicio de la novedad*/
		ActualizarEmpresa actualizarEmpresaService = new ActualizarEmpresa(empresa);
		
		logger.debug("Fin de método ActualizarEmpresaNovedad.transformarServicio");
		return actualizarEmpresaService;
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
}
