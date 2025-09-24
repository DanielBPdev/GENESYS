/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import javax.persistence.EntityManager;
import com.asopagos.empleadores.clients.ActualizarDatosEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar el Empleador por novedad.
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarEmpleadorNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarEmpleadorNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarEmpleadorNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		/*se consulta el empleador*/
		Empleador empleador = new Empleador();
		ConsultarEmpleador consultarEmpledorService = new ConsultarEmpleador(datosEmpleador.getIdEmpleador());
		consultarEmpledorService.execute();
		empleador = (Empleador) consultarEmpledorService.getResult();
				
		switch (novedad) {
		case CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL:
			cambiarMedioPagoEmpleador(empleador, datosEmpleador);
			break;
		case CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB:
			cambiarMedioPagoEmpleador(empleador, datosEmpleador);
			break;
		case ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA:
			empleador.setValidaSucursalPila(datosEmpleador.getCodigoNombreCoincidePILA());
			break;
		case ACTIVAR_RETENCION_SUBSIDIO_EMPLEADOR:
		case INACTIVAR_RETENCION_SUBSIDIO_EMPLEADOR:
			activarInactivarRetencionSubsidio(empleador, datosEmpleador);
			break;
		default:
			break;
		}
		
		/*se instancia el servicio de la novedad*/
		ActualizarDatosEmpleador actualizarEmpleadorService = new ActualizarDatosEmpleador(empleador);
		logger.debug("Fin de método ActualizarEmpleadorNovedad.transformarServicio");
		
		return actualizarEmpleadorService;
	}

	/**
	 * Método que se encarga de modificar los datos del medio de Pago del Empleador.
	 * @param empleador
	 * @param datosEmpleador
	 */
	private void cambiarMedioPagoEmpleador(Empleador empleador, DatosEmpleadorNovedadDTO datosEmpleador){
		logger.debug("Inicio de método cambiarMedioPagoEmpleador(Empleador empleador, DatosEmpleadorNovedadDTO datosEmpleador)");
		empleador.setMedioDePagoSubsidioMonetario(datosEmpleador.getMedioDePagoSubsidioMonetario());
		logger.debug("Fin de método cambiarMedioPagoEmpleador(Empleador empleador, DatosEmpleadorNovedadDTO datosEmpleador)");
	}

    /**
     * Metodo que se encarga de procesar la activación e inactivación de la retencion de subsidio
     * @param empleador
     *        Informacion empleador
     * @param datosEmpleadorNovedadDTO
     *        Informacion empleador pantalla
     */
    private void activarInactivarRetencionSubsidio(Empleador empleador, DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO) {
        if (datosEmpleadorNovedadDTO.getMotivoRetencionSubsidioEmpleador() != null) {
            empleador.setMotivoRetencionSubsidio(datosEmpleadorNovedadDTO.getMotivoRetencionSubsidioEmpleador());
        }
        if (datosEmpleadorNovedadDTO.getMotivoInactivaRetencionSubsidioEmpleador() != null) {
            empleador.setMotivoInactivaRetencionSubsidio(datosEmpleadorNovedadDTO.getMotivoInactivaRetencionSubsidioEmpleador());
        }
        empleador.setRetencionSubsidioActiva(datosEmpleadorNovedadDTO.getRetencionSubsidioActivaEmpleador());
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
	
}
