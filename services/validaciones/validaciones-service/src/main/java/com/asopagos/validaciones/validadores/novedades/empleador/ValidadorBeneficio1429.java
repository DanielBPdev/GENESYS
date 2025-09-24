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
 * Validador 12	Validar que empleador tiene activo el beneficio de Ley 1429 de 2010
 * Valida:
 * Que el empleador debe tener activo el beneficio de Ley 1429 de 2010
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorBeneficio1429 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorSinBeneficio1429Anio2010.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el beneficio 1429 del empleador
 			Object[] beneficioEmpleador =  (Object[]) entityManager
 					.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIO_EMPLEADOR)
 					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
 					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
 					.setParameter(ConstantesValidaciones.TIPO_BENEFICIO_PARAM, TipoBeneficioEnum.LEY_1429.name())
 					.getSingleResult();
 		
            if (Integer.valueOf(beneficioEmpleador[0].toString()) > NumerosEnterosConstants.CERO) {
                //Object[] beneficioEmpleador = beneficioEmpleadorList.get(NumerosEnterosConstants.CERO);

                logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorSinBeneficio1429Anio2010.execute");
                // Validación exitosa, Validador 12	Validar que empleador tiene activo el beneficio de Ley 1429 de 2010
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIO_1429);	
 				
 			}else{
 				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorSinBeneficio1429Anio2010.execute");
				// Validación no aprobada, Validador 12	Validar que empleador tiene activo el beneficio de Ley 1429 de 2010
				return crearValidacion(myResources.getString(ConstantesValidaciones.EMPLEADOR_HA_TENIDO_BENEFICIO_1429_ANIO_2010),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_BENEFICIO_1429,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
 			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_BENEFICIO_1429, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
