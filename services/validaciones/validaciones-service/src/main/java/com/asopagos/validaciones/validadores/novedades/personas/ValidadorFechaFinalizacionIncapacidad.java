package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
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
 * Si el campo "Fecha de finalización de la incapacidad" tiene 
 * una fecha anterior a la fecha del sistema en el momento de 
 * la ejecución de identificación de solicitudes de novedades automáticas 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorFechaFinalizacionIncapacidad extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorFechaFinalizacionIncapacidad.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta la fecha de nacimiento del beneficiario con el tipo y 
	        //número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        List<Date> fechaNacimiento = (List<Date>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_FIN_INCAPACIDAD)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
   
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(fechaNacimiento.get(NumerosEnterosConstants.CERO));

	        //se realiza la validación
			if(calendar.before(Calendar.getInstance())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorFechaFinalizacionIncapacidad.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_FIN_INCAPACIDAD);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorFechaFinalizacionIncapacidad.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_FIN_INCAPACIDAD),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_FECHA_FIN_INCAPACIDAD,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_FECHA_FIN_INCAPACIDAD, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}