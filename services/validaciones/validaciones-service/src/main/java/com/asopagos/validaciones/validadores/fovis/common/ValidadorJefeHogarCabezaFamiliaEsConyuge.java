package com.asopagos.validaciones.validadores.fovis.common;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si el jefe de hogar en condicion especial de mujer o cabeza de familia
 * esta marcado en uno de sus grupos familiares como conyugue activo. (V5)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */

public class ValidadorJefeHogarCabezaFamiliaEsConyuge extends ValidadorAbstract{

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorJefeHogarCabezaFamiliaEsConyuge.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				/*
				 * Evalua si los datos de tipoIdentificacion y numeroIdentificacion no estan nulos o vacios
				 */
				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))){

					if (esConyugeActivo(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_INVALIDO_CABEZA_FAMILIA),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_CABEZA_FAMILIA_CONYUGE_ACTIVO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
				}else {
					logger.debug("No evaluado - No llegaron todos los parámetros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("No evaluado - No llegaron todos los parámetros");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_CABEZA_FAMILIA_CONYUGE_ACTIVO);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}
	
	/**
	 * Metodo que evalua si un jefe de hogar es un conyuge activo en alguno de sus grupos familiares
	 * 
	 * @param tipoIdentificacion Tipo de identificacion del jefe de hogar
	 * @param numeroIdentificacion Numero de identificacion del jefe de hogar
	 * @return true si el jefe de hogar es conyuge activo en uno de sus grupos familiares
	 */
	@SuppressWarnings("unchecked")
    private boolean esConyugeActivo(String tipoIdentificacion, String numeroIdentificacion) {
		logger.debug("Inicio de método ValidadorJefeHogarCabezaFamiliaEsConyuge.esConyugeActivo");
		List<BigInteger> conyugeActivos = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_CONYUGE_ACTIVO)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
				.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
				.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name())
				.getResultList();
		/*Si tiene Cónyuge Activos */
		if (conyugeActivos != null && !conyugeActivos.isEmpty()) {
		    //No puede seleccionarse
		    return false;
		}else {
		    List<BigInteger> conyugeDeOtroAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_CONYUGE_OTRO_GRUPO)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name())
                    .getResultList();
		    if (conyugeDeOtroAfiliado != null && !conyugeDeOtroAfiliado.isEmpty()) {
		        //No puede seleccionarse
                return false;
		    }
		}
		logger.debug("Fin de método ValidadorJefeHogarCabezaFamiliaEsConyuge.esConyugeActivo");
		 return true;
	}

	
	
	
	/**
	 * Mensaje utilizado cuando por alguna razón no se puede evaluar.
	 * 
	 * @return validacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_CABEZA_FAMILIA_NO_CONYUGUE_ACTIVO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_CABEZA_FAMILIA_CONYUGE_ACTIVO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}
