package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * 
 * Validador 9	Validar que empleador nunca ha tenido activo el beneficio de Ley 590 de 2000
 * Valida:
 * El empleador no tuvo o tiene activo el beneficio de Ley 590 de 2000
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorSinBeneficio590 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadSinBeneficio590Anio2000.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	     // Se consulta el beneficio 590 del empleador
	        Object[] beneficioEmpleador = (Object[]) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIO_EMPLEADOR)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
				.setParameter(ConstantesValidaciones.TIPO_BENEFICIO_PARAM, TipoBeneficioEnum.LEY_590.name())
				.getSingleResult();
		
			if (Integer.valueOf(beneficioEmpleador[0].toString()) == NumerosEnterosConstants.CERO
                    && Integer.valueOf(beneficioEmpleador[1].toString()) >= NumerosEnterosConstants.CERO) {
				/*si la lista esta vacía no hay beneficio de ley 590*/
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadSinBeneficio590Anio2000.execute");
				// Validación exitosa, Validador 9 Validar que empleador nunca
				// ha tenido activo el beneficio de Ley 590 de 2000
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SIN_BENEFICIO_590);
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadSinBeneficio590Anio2000.execute");
				// Validación no aprobada, Validador 9 Validar que empleador
				// nunca ha tenido activo el beneficio de Ley 590 de 2000
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.EMPLEADOR_HA_TENIDO_BENEFICIO_590_ANIO_2000),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SIN_BENEFICIO_590,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_SIN_BENEFICIO_590, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
