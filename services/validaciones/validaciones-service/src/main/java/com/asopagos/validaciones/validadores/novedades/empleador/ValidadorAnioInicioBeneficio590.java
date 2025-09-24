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
 * Validador 23	Validar Año de inicio del beneficio de Ley 590 de 2000 sea igual o mayor que el año de la fecha de constitución
 * Valida:
 * Validar que el valor del campo "Año inicio del beneficio" es igual o mayor al valor del año del campo "Fecha de constitución"
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorAnioInicioBeneficio590 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAnioInicioBenefLey590Anio200.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			Long anioInicio = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ANIO_INICIO_BENEFICIO_PARAM));

			Calendar fechaInicioBeneficioLey590Anio2000 = Calendar.getInstance();
			fechaInicioBeneficioLey590Anio2000.setTimeInMillis(anioInicio);

			Integer anioInicioBeneficio = fechaInicioBeneficioLey590Anio2000.get(Calendar.YEAR);

			Empleador empleador = (Empleador) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();

			Calendar fechaConstitucion = Calendar.getInstance();
			fechaConstitucion.setTime(empleador.getEmpresa().getFechaConstitucion());

			Integer anioFechaConstitucion = fechaConstitucion.get(Calendar.YEAR);

			// Se valida la condición
			if (anioInicioBeneficio >= anioFechaConstitucion) {
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorAnioInicioBenefLey590Anio200.execute");
				// Validación exitosa, Validador 23 Validar Año de inicio del
				// beneficio de Ley 590
				// de 2000 sea igual o mayor que el año de la fecha de
				// constitución
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_590);
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorAnioInicioBenefLey590Anio200.execute");
				// Validación no aprobada, Validador 23 Validar Año de inicio
				// del beneficio de Ley 590
				// de 2000 sea igual o mayor que el año de la fecha de
				// constitución
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ANIO_INICIO_BENEFICIO_590),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_590,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
			
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_ANIO_INICIO_BENEFICIO_590, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
