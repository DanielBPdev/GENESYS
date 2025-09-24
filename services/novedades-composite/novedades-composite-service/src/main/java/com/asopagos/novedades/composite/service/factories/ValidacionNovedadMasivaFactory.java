/**
 * 
 */
package com.asopagos.novedades.composite.service.factories;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.service.ValidacionMasivaCore;
import com.asopagos.novedades.convertidores.empleador.ValidarNovedadDesafiliarEmpCeroTrabajadores;
import com.asopagos.novedades.convertidores.empleador.ValidarNovedadInactivarCuentaWeb;
import com.asopagos.novedades.convertidores.empleador.ValidarNovedadInactivarLey1429;
import com.asopagos.novedades.convertidores.empleador.ValidarNovedadInactivarLey590;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadCambioCategoriaBeneficiarioXEdad;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadDesafiliarPorMoraAportes;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadInactivarCuentaWebPersona;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadRetiroBeneficiarioMayorEdadConTI;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadRetiroBeneficiarioXEdad;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadVencCertificadoEscolaridad;
import com.asopagos.novedades.convertidores.persona.ValidarNovedadVencIncapacidades;
import com.asopagos.novedades.convertidores.persona.ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica;
import com.asopagos.novedades.convertidores.persona.ValidarClaseTrabajadorNovedadAutomatica;


/**
 * <b>Descripción:</b> Fabrica para la construcción de validaciones masivas.
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3 
 * @author Fabian Hernando López Higuera <flopez@heinsohn.com.co>
 */
public class ValidacionNovedadMasivaFactory {

	/**
	 * Instancia Singleton de la clase.
	 */
	private static ValidacionNovedadMasivaFactory instance;

	/**
	 * Método que obtiene la instancia singleton de la clase NovedadAbstractFactory.
	 * 
	 * @return Instancia Singleton
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static ValidacionNovedadMasivaFactory getInstance()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = new ValidacionNovedadMasivaFactory();
		}
		return instance;
	}
	
	/**
	 * Método que se encarga de obtener la validación respectiva.
	 * @param novedadDTO dto de las novedades.
	 * @return novedad determinada.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ValidacionMasivaCore obtenerSercioNovedad(TipoTransaccionEnum tipoTransaccion) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		ValidacionMasivaCore servicio = null;
		
		switch (tipoTransaccion) {
		case INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010:
			servicio = new ValidarNovedadInactivarLey1429();
			break;
		case INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000:
			servicio = new ValidarNovedadInactivarLey590();
			break;
		case INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR:
			servicio = new ValidarNovedadInactivarCuentaWeb();
			break;
			/*Novedad Personas*/
		case VENCIMIENTO_AUTOMATICO_CERTIFICADOS:
			servicio = new ValidarNovedadVencCertificadoEscolaridad();
			break;
		case INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR:
			servicio = new ValidarNovedadInactivarCuentaWebPersona();
			break;
		case VENCIMIENTO_AUTOMATICO_INCAPACIDADES:
			servicio = new ValidarNovedadVencIncapacidades();
			break;
		case RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA:
			servicio = new ValidarNovedadRetiroBeneficiarioMayorEdadConTI();
			break;
		case RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD:
			servicio = new ValidarNovedadRetiroBeneficiarioXEdad();
			break;
		case CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD:
			servicio = new ValidarNovedadCambioCategoriaBeneficiarioXEdad();
			break;
		case CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA:
			servicio = new ValidarCambioAutomaticoCategoriaBeneficiarioCircularUnica();
			break;
		case DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_CERO_TRABAJADORES:
			servicio = new ValidarNovedadDesafiliarEmpCeroTrabajadores();
			break;
		case RETIRO_AUTOMATICO_POR_MORA:
			servicio = new ValidarNovedadDesafiliarPorMoraAportes();
			break;
		case CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL:
			servicio = new ValidarClaseTrabajadorNovedadAutomatica();
			break;
		default:
			break;
		}
		return servicio;
	}
}
