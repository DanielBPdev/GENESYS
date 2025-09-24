/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.dto.SucursalEmpresaDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.empresas.clients.ActualizarSucursalEmpresa;
import com.asopagos.empresas.clients.ConsultarSucursal;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar una Sucursal por novedad.
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarSucursalNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarSucursalNovedad.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarSucursalNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		/*Se consulta la Sucursal*/
		SucursalEmpresaModeloDTO sucursalEmpresaDTO = new SucursalEmpresaModeloDTO();
		ConsultarSucursal consultarSucursal = new ConsultarSucursal(datosEmpleador.getIdSucursalEmpresa());
		consultarSucursal.execute();
		sucursalEmpresaDTO = consultarSucursal.getResult();
		SucursalEmpresa sucursalEmpresa = SucursalEmpresaDTO.obtenerSucursalEmpresa(sucursalEmpresaDTO);
		
		/*Se consulta el empleador*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpledorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpledorService.execute();
		empleador = (Empleador) consultarEmpledorService.getResult();
		
		switch (novedad) {
		case CAMBIO_CODIGO_NOMBRE_SUCURSAL:
			cambiarCodigoNombreSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB:
			cambiarCodigoNombreSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL:
			cambiarActividadEconomicaSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB:
			cambiarActividadEconomicaSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL:
			cambiarMedioPagoSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case CAMBIO_MEDIO_PAGO_SUCURSAL_WEB:
			cambiarMedioPagoSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case INACTIVAR_SUCURSAL:
			inactivarSucursal(sucursalEmpresa, datosEmpleador);
			break;
		case ACTIVAR_RETENCION_SUBSIDIO_SUCURSAL:
		case INACTIVAR_RETENCION_SUBSIDIO_SUCURSAL:
		    activarInactivarRetencionSubsidio(sucursalEmpresa, datosEmpleador);
		    break;
		default:
			break;
		}
		
		/*Se instancia el servicio de la novedad*/
		List<SucursalEmpresa> sucursalesEmpresa = new ArrayList<>();
		sucursalesEmpresa.add(sucursalEmpresa);
		
		ActualizarSucursalEmpresa actualizarSucursalEmpresa = new ActualizarSucursalEmpresa(empleador.getEmpresa().getIdEmpresa(), sucursalesEmpresa);
		logger.debug("Fin de método ActualizarSucursalNovedad.transformarServicio");
		return actualizarSucursalEmpresa;
	}
	
	/**
	 * Método que se encarga de modificar los datos del código o nombre de la Sucursal
	 * @param empleador
	 * @param datosEmpleador
	 */
	private void cambiarCodigoNombreSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador) {
		
		logger.debug("Inicio de método cambiarCodigoNombreSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		sucursalEmpresa.setCodigo(datosEmpleador.getCodigoSucursal());
		sucursalEmpresa.setNombre(datosEmpleador.getNombreSucursal());
		logger.debug("Fin de método cambiarCodigoNombreSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		
	}
	
	/**
	 * Método que se encarga de modificar los datos de Actividad Económica de la Sucursal
	 * @param empleador
	 * @param datosEmpleador
	 */
	private void cambiarActividadEconomicaSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador) {
		
		logger.debug("Inicio de método cambiarActividadEconomicaSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		sucursalEmpresa.setCodigoCIIU(datosEmpleador.getCodigoCIIUSucursal());
		logger.debug("Fin de método cambiarActividadEconomicaSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		
	}
	
	/**
	 * Método que se encarga de modificar los datos de Medio de Pago de la Sucursal
	 * @param empleador
	 * @param datosEmpleador
	 */
	private void cambiarMedioPagoSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador) {
		
		logger.debug("Inicio de método cambiarMedioPagoSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		sucursalEmpresa.setMedioDePagoSubsidioMonetario(datosEmpleador.getMedioDePagoSubsidioMonetarioSucursal());
		logger.debug("Fin de método cambiarMedioPagoSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		
	}
	
	/**
	 * Método que se encarga de inactivar una sucursal
	 * @param empleador
	 * @param datosEmpleador
	 */
	private void inactivarSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador) {
		
		logger.debug("Inicio de método inactivarSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		if(datosEmpleador.getInactivarSucursal()) {
			sucursalEmpresa.setEstadoSucursal(EstadoActivoInactivoEnum.INACTIVO);
		}
		logger.debug("Fin de método inactivarSucursal(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleador)");
		
	}

    private void activarInactivarRetencionSubsidio(SucursalEmpresa sucursalEmpresa, DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO) {
        if (datosEmpleadorNovedadDTO.getMotivoRetencionSubsidioSucursal() != null) {
            sucursalEmpresa.setMotivoRetencionSubsidio(datosEmpleadorNovedadDTO.getMotivoRetencionSubsidioSucursal());
        }
        if (datosEmpleadorNovedadDTO.getMotivoInactivaRetencionSubsidioSucursal() != null) {
            sucursalEmpresa.setMotivoInactivaRetencionSubsidio(datosEmpleadorNovedadDTO.getMotivoInactivaRetencionSubsidioSucursal());
        }
        sucursalEmpresa.setRetencionSubsidioActiva(datosEmpleadorNovedadDTO.getRetencionSubsidioActivaSucursal());
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
