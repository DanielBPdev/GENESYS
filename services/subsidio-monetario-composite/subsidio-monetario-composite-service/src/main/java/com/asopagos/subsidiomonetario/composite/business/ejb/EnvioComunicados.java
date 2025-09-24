package com.asopagos.subsidiomonetario.composite.business.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarListaNotificacionComunicados;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.clients.ConsultarInformacionComunicadosLiquidacionMasiva;
import com.asopagos.subsidiomonetario.composite.business.interfaces.IEnvioComunicados;
import com.asopagos.subsidiomonetario.dto.DatosComunicadoDTO;

@Stateless
public class EnvioComunicados implements IEnvioComunicados {

	/**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(EnvioComunicados.class);
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.business.interfaces.IEnvioComunicados#enviarComunicadosLiquidacion(java.lang.String, com.asopagos.enumeraciones.core.ProcesoEnum)
     */
    @Asynchronous
	@Override
	public void enviarComunicadosLiquidacion(String numeroSolicitud, ProcesoEnum proceso) {
		String firmaMetodo = "SubsidioMonetarioCompositeBusiness.enviarComunicadosLiquidacion(String, ProcesoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    	try {
    		List<DatosComunicadoDTO> datosComunicados = consultarInformacionComunicadosLiquidacionMasiva(numeroSolicitud);
    		Map<String, String> params;
    		NotificacionParametrizadaDTO notificacion;
    		List<NotificacionParametrizadaDTO> notificaciones = new ArrayList<NotificacionParametrizadaDTO>();
    		
    		
    		for(DatosComunicadoDTO comunicado : datosComunicados) {
    			
    			params = comunicado.getVariables();
    	    	params.put(ParametrosComunicadoDTO.ID_PERSONA, comunicado.getIdPersona().toString());
    	    	params.put(ParametrosComunicadoDTO.NUMERO_RADICACION, numeroSolicitud);
    	    	
    	    	notificacion = new NotificacionParametrizadaDTO();
    	    	notificacion.setParams(params);
    	    	notificacion.setReplantearDestinatarioTO(Boolean.TRUE);
    	    	notificacion.setDestinatarioTO(new ArrayList<String>());
    	    	notificacion.getDestinatarioTO().add(comunicado.getDestinatario());
    	    	notificacion.setEtiquetaPlantillaComunicado(comunicado.getEtiqueta());
    	    	notificacion.setProcesoEvento(proceso.name());
    	    	notificacion.setIdPersona(comunicado.getIdPersona());
    	    	notificacion.setAutorizaEnvio(comunicado.getAutorizaEnvio());
    	    	
    	    	notificaciones.add(notificacion);
    		}
    		if(!notificaciones.isEmpty()) {
    			enviarListaNotificacionComunicados(notificaciones);
    		}
    	} catch (Exception e) {
    		logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
	
	/**
	 * Método encargado de consultar todos los destinatarios de comunicados de una liquidación masiva
	 * 
	 * @param numeroRadicacion
	 * @return
	 */
	private List<DatosComunicadoDTO> consultarInformacionComunicadosLiquidacionMasiva(String numeroRadicacion) {
		ConsultarInformacionComunicadosLiquidacionMasiva consultarInfoCLM = new ConsultarInformacionComunicadosLiquidacionMasiva(numeroRadicacion);
		consultarInfoCLM.execute();
		return consultarInfoCLM.getResult();
	}
	
	/**
	 * Metodo que se encarga de invocar el cliente para envío de email masivos
	 *  
	 * @param notificaciones
	 */
	private void enviarListaNotificacionComunicados(List<NotificacionParametrizadaDTO> notificaciones) {
		logger.debug("Inicia  EnvioComunicado.enviarListaNotificacionComunicados (List<NotificacionParametrizadaDTO>)");
		EnviarListaNotificacionComunicados enviar = new EnviarListaNotificacionComunicados(notificaciones);
		enviar.execute();
		logger.debug("Finaliza  EnvioComunicado.enviarListaNotificacionComunicados (List<NotificacionParametrizadaDTO>)");
	}
    
}
