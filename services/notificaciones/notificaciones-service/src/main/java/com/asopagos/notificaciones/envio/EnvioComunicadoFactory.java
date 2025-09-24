package com.asopagos.notificaciones.envio;

import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.enums.EnvioComunicadoProcesoEnum;


public class EnvioComunicadoFactory {

	/**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(EnvioComunicadoFactory.class);
	
	protected static EnvioComunicado instance;
	
	private EnvioComunicadoFactory() {
		
	}
	
	public static EnvioComunicado getInstance(String procesoEvento, EstadoEnvioNotificacionEnum estadoEnvio){
		String clase = null;
		if(EstadoEnvioNotificacionEnum.ENVIADA.equals(estadoEnvio) || EstadoEnvioNotificacionEnum.ENVIO_INACTIVO.equals(estadoEnvio)){

		    clase = EnvioComunicadoProcesoEnum.obtenerClaseProcesoExitoso(procesoEvento);
		}
		else
		{
		    clase = EnvioComunicadoProcesoEnum.obtenerClaseProcesoFallido(procesoEvento);
		}
		if(clase != null){
			instanciarImplementacion(clase);
		}
		else{
		    instance = null;
		}
		return instance;
	}

	/**
	 * @param clase
	 */
	private static void instanciarImplementacion(String clase) {
		try {
			instance = (EnvioComunicado) Class.forName(clase).newInstance();
		} catch (Exception e) {
			logger.info("no se pudo instanciar la clase "+clase, e);
		}
	}
}
