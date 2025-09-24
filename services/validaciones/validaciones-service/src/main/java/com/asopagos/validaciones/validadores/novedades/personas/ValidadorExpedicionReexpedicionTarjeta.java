package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * La persona ya tiene una solicitud de expedición/reexpedición de tarjeta 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorExpedicionReexpedicionTarjeta extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorExpedicionReexpedicionTarjeta.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<TipoTransaccionEnum> tiposValidosTransaccion = new ArrayList<TipoTransaccionEnum>();
	        tiposValidosTransaccion.add(TipoTransaccionEnum.EXPEDICION_PRIMERA_VEZ_TARJETA_ADMINISTRADOR_SUBSIDIO);
	        tiposValidosTransaccion.add(TipoTransaccionEnum.EXPEDICION_PRIMERA_VEZ_TARJETA_TRABAJADOR_DEPENDIENTE);
	        tiposValidosTransaccion.add(TipoTransaccionEnum.RE_EXPEDICION_TARJETA_ADMINISTRADOR_SUBSIDIO);
	        tiposValidosTransaccion.add(TipoTransaccionEnum.RE_EXPEDICION_TARJETA_TRABAJADOR_DEPENDIENTE);
	        

	        //se consultan las novedades de expedicion y/o reexpedicion de tarjetas, asociadas al empleador origen
	        List<SolicitudNovedadPersona> novedadesPersona = (List<SolicitudNovedadPersona>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_EXPEDICION_REEXPEDICION_TARJETA_PERSONA)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, tiposValidosTransaccion)
					.getResultList();
	        
	        //se realiza la validación
			if(novedadesPersona != null && !novedadesPersona.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorExpedicionReexpedicionTarjeta.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EXPEDICION_REEXPEDICION_TARJETA);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorExpedicionReexpedicionTarjeta.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EXPEDICION_REEXPEDICION_TARJETA),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EXPEDICION_REEXPEDICION_TARJETA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_EXPEDICION_REEXPEDICION_TARJETA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}