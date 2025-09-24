package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Calendar;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

 /**
 *
 * Validador 20	Validar Año de inicio del beneficio de Ley 1429 de 2010 sea igual o mayor que el año de la fecha de constitución
 * Valida:
 * Validar que el valor del campo "Año inicio del beneficio" es igual o mayor al valor del año del campo "Fecha de constitución"
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorAnioInicioBeneficio1429 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorFechaInicBenefLey1429Anio2010.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        Long anioInicio = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ANIO_INICIO_BENEFICIO_PARAM));

			Calendar fechaInicioBeneficioCalendar = Calendar.getInstance();
			fechaInicioBeneficioCalendar.setTimeInMillis(anioInicio);
			Integer anioInicioBeneficio = fechaInicioBeneficioCalendar.get(Calendar.YEAR);

			Empleador empleador = (Empleador) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();

			Calendar fechaConstitucion = Calendar.getInstance();
			fechaConstitucion.setTime(empleador.getEmpresa().getFechaConstitucion());
			Integer anioConstitucion = fechaConstitucion.get(Calendar.YEAR);

			// Se valida la condición
			if (anioInicioBeneficio >= anioConstitucion) {
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorFechaInicBenefLey1429Anio2010.execute");
				// Validación exitosa, Validador 20 Validar Año de inicio del
				// beneficio de Ley 1429 de 2010 sea igual o mayor que el año de
				// la fecha de constitución
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_1429);
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorFechaInicBenefLey1429Anio2010.execute");
				// Validación no aprobada, Validador 20 Validar Año de inicio
				// del beneficio de Ley 1429 de 2010 sea igual o mayor que el
				// año de la fecha de constitución
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.KEY_VALOR_ANIO_BENEFICIO_1429_2010_MENOR),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_1429,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_1429, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
