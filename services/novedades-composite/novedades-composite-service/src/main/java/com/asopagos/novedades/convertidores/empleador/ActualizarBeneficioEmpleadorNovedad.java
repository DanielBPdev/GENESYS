/**
 * 
 */
package com.asopagos.novedades.convertidores.empleador;

import javax.persistence.EntityManager;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import com.asopagos.empleadores.clients.ActualizarBeneficioEmpleador;
import com.asopagos.empleadores.clients.ConsultarBeneficioEmpleador;
import com.asopagos.enumeraciones.afiliaciones.MotivoInactivacionBeneficioEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar Beneficios Empleador por novedad.
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarBeneficioEmpleadorNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarBeneficioEmpleadorNovedad.class);
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarBeneficioEmpleadorNovedad.transformarServicio");
		
		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		BeneficioEmpleadorModeloDTO beneficioEmpleador = null;
		
		switch (novedad) {
			case ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL:
				beneficioEmpleador = activarBeneficiosLey14292010(datosEmpleador);
				break;
			case ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB:
				beneficioEmpleador = activarBeneficiosLey14292010(datosEmpleador);
				break;
			case INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL:
				beneficioEmpleador = inactivarBeneficiosLey14292010(datosEmpleador);
				break;
			case INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB:
				beneficioEmpleador = inactivarBeneficiosLey14292010(datosEmpleador);
				break;
			case ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL:
				beneficioEmpleador = activarBeneficiosLey5902000(datosEmpleador);
				break;
			case ACTIVAR_BENEFICIOS_LEY_590_2000_WEB:
				beneficioEmpleador = activarBeneficiosLey5902000(datosEmpleador);
				break;
			case INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL:
				beneficioEmpleador = inactivarBeneficiosLey5902000(datosEmpleador);
				break;
			case INACTIVAR_BENEFICIOS_LEY_590_2000_WEB:
				beneficioEmpleador = inactivarBeneficiosLey5902000(datosEmpleador);
				break;
			default:
				break;
		}
		
		/*se instancia el servicio de la novedad*/
		ActualizarBeneficioEmpleador actualizarBeneficioEmpleador = new ActualizarBeneficioEmpleador(beneficioEmpleador);
		logger.debug("Fin de método ActualizarBeneficioEmpleadorNovedad.transformarServicio");
		
		return actualizarBeneficioEmpleador;
	}


	/**
	 * Método que se encargado de activar beneficios Ley 1429 de 2010.
	 * @param empleador
	 * @param datosEmpleador
	 */
	private BeneficioEmpleadorModeloDTO activarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método activarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador)");
		BeneficioEmpleadorModeloDTO beneficioEmpleador = new BeneficioEmpleadorModeloDTO();
		
		beneficioEmpleador.setFechaBeneficioInicio(datosEmpleador.getAnoInicioBeneficioLey1429());
		beneficioEmpleador.setFechaBeneficioFin(datosEmpleador.getAnoFinBeneficioLey1429());
		beneficioEmpleador.setTipoBeneficio(TipoBeneficioEnum.LEY_1429);
		beneficioEmpleador.setIdEmpleador(datosEmpleador.getIdEmpleador());
		beneficioEmpleador.setBeneficioActivo(Boolean.TRUE);
		beneficioEmpleador.setPerteneceDepartamento(datosEmpleador.getPerteneceDepartamento());
		logger.debug("Fin de método activarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador)");
		return beneficioEmpleador;
	}
	
	/**
	 * Método que se encargado de inactivar beneficios Ley 1429 de 2010.
	 * @param empleador
	 * @param datosEmpleador
	 */
	private BeneficioEmpleadorModeloDTO inactivarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método inactivarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador)");
		ConsultarBeneficioEmpleador consultarBeneficioEmpleador = new ConsultarBeneficioEmpleador(datosEmpleador.getIdEmpleador(),TipoBeneficioEnum.LEY_1429);
		consultarBeneficioEmpleador.execute();
		BeneficioEmpleadorModeloDTO beneficioEmpleador = consultarBeneficioEmpleador.getResult();
		if(beneficioEmpleador == null){
			beneficioEmpleador = new BeneficioEmpleadorModeloDTO();
		}
		/*Se inactiva el beneficio*/
		beneficioEmpleador.setBeneficioActivo(Boolean.FALSE);
		
		beneficioEmpleador.setMotivoInactivacionBeneficio(MotivoInactivacionBeneficioEnum.valueOf(datosEmpleador.getMotivoInactivacionBeneficioLey1429()));
		logger.debug("Fin de método inactivarBeneficiosLey14292010(DatosEmpleadorNovedadDTO datosEmpleador)");
		return beneficioEmpleador;
	}
	
	/**
	 * Método que se encargado de activar beneficios Ley 590 de 2000.
	 * @param empleador
	 * @param datosEmpleador
	 */
	private BeneficioEmpleadorModeloDTO activarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método activarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador)");
		BeneficioEmpleadorModeloDTO beneficioEmpleador = new BeneficioEmpleadorModeloDTO();
		
		beneficioEmpleador.setFechaBeneficioInicio(datosEmpleador.getPeriodoInicioBeneficioLey590());
		beneficioEmpleador.setFechaBeneficioFin(datosEmpleador.getPeriodoFinBeneficioLey590());
		beneficioEmpleador.setBeneficioActivo(Boolean.TRUE);
		beneficioEmpleador.setTipoBeneficio(TipoBeneficioEnum.LEY_590);
		beneficioEmpleador.setIdEmpleador(datosEmpleador.getIdEmpleador());
		logger.debug("Fin de método activarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador)");
		return beneficioEmpleador;
	}
	
	/**
	 * Método que se encargado de inactivar beneficios Ley 590 de 2000.
	 * @param empleador
	 * @param datosEmpleador
	 */
	private BeneficioEmpleadorModeloDTO inactivarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador) {
		logger.debug("Inicio de método inactivarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador)");
		ConsultarBeneficioEmpleador consultarBeneficioEmpleador = new ConsultarBeneficioEmpleador(datosEmpleador.getIdEmpleador(), TipoBeneficioEnum.LEY_590);
		consultarBeneficioEmpleador.execute();
		BeneficioEmpleadorModeloDTO beneficioEmpleador = consultarBeneficioEmpleador.getResult();
		if(beneficioEmpleador == null){
			beneficioEmpleador = new BeneficioEmpleadorModeloDTO();
		}
		/*Se inactiva el beneficio*/
		beneficioEmpleador.setBeneficioActivo(Boolean.FALSE);
		
		beneficioEmpleador.setMotivoInactivacionBeneficio(MotivoInactivacionBeneficioEnum.valueOf(datosEmpleador.getMotivoInactivacionBeneficioLey590()));
		logger.debug("Fin de método inactivarBeneficiosLey5902000(DatosEmpleadorNovedadDTO datosEmpleador)");
		return beneficioEmpleador;
	}


    /**
     * @param datosNovedad
     * @param entityManager
     * @param userDTO
     */
    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
