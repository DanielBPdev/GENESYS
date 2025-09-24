/**
 * 
 */
package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica que el jefe de hogar es dependiente y el empleador es beneficiario
 *  de la ley 1429.
 * @author <a href="mailto:anvalbuena@heinsohn.com.co">Andrés Felipe Valbuena</a>
 */
public class ValidadorFechaNaciemientoPersonaPosteriorFechaPostulacion extends ValidadorAbstract{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorFechaNaciemientoPersonaPosteriorFechaPostulacion.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))
						&& (fechaNacimiento != null && !fechaNacimiento.equals(""))) {
					if (!validarBeneficiario(numeroIdentificacion, tipoIdentificacion, fechaNacimiento)) {
						logger.debug("No aprobada- No existe persona con número y tipo documento");
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429, null);
					} 
				} else {
					/* mensaje no evaluado porque falta informacion */
					logger.debug("No evaludao- Falta informacion");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}

	private boolean validarBeneficiario(String numeroIdentificacion, String tipoIdentificacion,String fechaNacimiento){
		logger.debug("Inicio de método validarBeneficiario");
		Beneficiario beneficiarioEncontrado = null;
		List<Beneficiario> beneficiarioTipoNumero = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
		if (!beneficiarioTipoNumero.isEmpty()) {
			beneficiarioEncontrado = beneficiarioTipoNumero.get(0);
		} else {
			List<Beneficiario> beneficiarioNumero = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_DOCUMENTO)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
			if (!beneficiarioNumero.isEmpty()) {
				beneficiarioEncontrado = beneficiarioNumero.get(0);
			}
		}
		/*si se encuentra afiliado se compara el valor de la fecha de afiliacion con la fecha de nacimiento*/
		if (beneficiarioEncontrado != null) {
			if (beneficiarioEncontrado.getFechaAfiliacion() != null) {
				Date fechaNacimientoDate = new Date(new Long(fechaNacimiento));

				if (beneficiarioEncontrado.getFechaAfiliacion().getTime() < fechaNacimientoDate.getTime()) {
					return false;
				}
			}
		}
		logger.debug("Fin de método validarBeneficiario");
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
						+ myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
}
