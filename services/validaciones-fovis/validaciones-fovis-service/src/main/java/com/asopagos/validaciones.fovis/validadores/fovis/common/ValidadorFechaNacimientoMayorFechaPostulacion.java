package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Date;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;


/**
 * Validadador que verifica si la fecha de nacimiento de una persona es superior a la fecha
 * de postulacion 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorFechaNacimientoMayorFechaPostulacion extends ValidadorFovisAbstract {


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorFechaNacimientoMayorFechaPostulacion");
			
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				Long fechaNacimientoMilis = Long.parseLong(datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM));
				Date fechaNacimientoPersona = new Date(fechaNacimientoMilis);
				
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("") && numeroIdentificacion != null
						&& !numeroIdentificacion.equals("")) {
					ValidacionDTO validacion = fechaNacimientoPreviaPostulacion(numeroIdentificacion, tipoIdentificacion,fechaNacimientoPersona);
					if(validacion!=null){
						return validacion;
					}
					
				} else {
					/* mensaje no evaluado porque falta informacion */
					logger.debug("No evaluado - No llegaron todos los parámetros");
					return crearMensajeNoEvaluado();
				}
			} else {
				/* mensaje no evaluado porque faltan datos */
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_POSTULACION);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			logger.debug("No evaluado - Ocurrió alguna excepción");
			return crearMensajeNoEvaluado();
		}
	}
	
	/**
	 * Metodo que valida si la fecha de nacimiento de una persona es previa a la fecha de la postulacion (Fecha Actual)
	 * @param numeroIdentificacion : Numero de identificacion de la persona a evaluar 
	 * @param tipoIdentificacion : Tipo de identificacion de la persona a evaluar
	 */
	private ValidacionDTO fechaNacimientoPreviaPostulacion(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,Date fechaNacimientoPersona){
		logger.debug("Inicio de método ValidadorFechaNacimientoMayorFechaPostulacion.fechaNacimientoPreviaPostulacion");
		Date fechaActual = new Date();
		try {
			Date fechaNacimiento = (Date)entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_NACIMIENTO_PERSONA)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
			 
			if(fechaNacimiento == null){
				fechaNacimiento = fechaNacimientoPersona;
			}
			
			if (fechaNacimiento.after(fechaActual)) {
				return crearValidacion(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_SUPERIOR_FECHA_POSTULACION,
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_POSTULACION,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
	    
		} catch (Exception e) {
			if (fechaNacimientoPersona.after(fechaActual)) {
				return crearValidacion(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_SUPERIOR_FECHA_POSTULACION,
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_POSTULACION,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}
		
		logger.debug("Fin de método ValidadorFechaNacimientoMayorFechaPostulacion.validarBeneficiario");
		return null;
	}
	
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_SUPERIOR_FECHA_POSTULACION),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_POSTULACION,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
