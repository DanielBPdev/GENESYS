package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Calendar;
import java.util.Date;
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
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * La novedad se habilita siempre que el beneficiario sea mayor de 18 años
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaMayor18 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaMayor18.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion;
			String numeroIdentificacion;
			
			if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null &&
					datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null){
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
		        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			}else{
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
		        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				
			}

	        //se consulta la fecha de nacimiento de la persona dados su tipo y número de identificación
	        Date fechaNacimiento = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_NACIMIENTO_PERSONA)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();

	        int edadEnAnios = NumerosEnterosConstants.UNO_NEGATIVO;
	        
	        //se realiza el calculo de la edad en años de la persona
	        if(fechaNacimiento != null)
	        {
	        	Calendar calendar = Calendar.getInstance();
		        calendar.setTime(fechaNacimiento);
		        String fecha = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
		        edadEnAnios = ValidacionPersonaUtils.calcularEdadAnos(fecha);
	        }

	        //se realiza la validación
			if(edadEnAnios != NumerosEnterosConstants.UNO_NEGATIVO && edadEnAnios >= NumerosEnterosConstants.DIECIOCHO){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorPersonaMayor18.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MAYOR_18);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorPersonaMayor18.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MAYOR_18),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_MAYOR_18,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_MAYOR_18, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
