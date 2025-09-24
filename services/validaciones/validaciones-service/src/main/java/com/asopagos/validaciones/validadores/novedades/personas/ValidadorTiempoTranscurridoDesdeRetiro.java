package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Date;
import java.util.Map;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Se valida que la fecha de solicitud de la novedad esté 
 * dentro del tiempo parametrizado que ha transcurrido desde el retiro del trabajador  
 *  
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTiempoTranscurridoDesdeRetiro extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTiempoTranscurridoDesdeRetiro.execute");
		try{
			String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
			
			Date fechaRetiro = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_RETIRO_AFILIADO_TIPO_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			
			Date fechaActual = new Date();
			Long dias = (fechaActual.getTime()- fechaRetiro.getTime())/(3600*24*1000);
//			Long diasReintegro =  new Long((String)CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA))/(3600*24*1000);
			Long diasReintegro =  new Long((String)CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_ADICIONAL_SERVICIOS_CAJA));
			
			if(dias > diasReintegro){
				logger.debug("NO_APROBADO-Fin de método ValidadorTiempoTranscurridoDesdeRetiro.execute");
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.KEY_FECHA_REINTEGRO_SUPERADA),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TIEMPO_TRANSC_DESDE_RETIRO, null);
			}
			
			logger.debug("Fin de método ValidadorTiempoTranscurridoDesdeRetiro.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIEMPO_TRANSC_DESDE_RETIRO);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TIEMPO_TRANSC_DESDE_RETIRO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}