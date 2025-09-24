package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si una persona se encuentra fallecida con una novedad de 
 * retiro asociada (Entidades externas)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaFallecidaEntidadesExternas extends ValidadorFovisAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {

		logger.debug("Inicio de método ValidadorPersonaFallecidaEntidadesExternas.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
						&& (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
					if (personaFallecidaConNovedad(tipoIdentificacion, numeroIdentificacion)) {
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA_NOVEDAD_ENTIDADES_EXTERNAS),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_ENTIDADES_EXTERNAS,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}

				} else {
					logger.debug("No evaluado - No llegaron todos los parámetros");
					return crearMensajeNoEvaluado();
				}
			} else {
				logger.debug("No evaluado - No llegó el mapa con valores");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_ENTIDADES_EXTERNAS);

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado();
		}
	}
	
	/**
	 * Mensaje utilizado cuando por alguna razón no se puede evaluar.
	 * 
	 * @return validacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_ENTIDADES_EXTERNAS,
				TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
	}
	
	/**
	 * Valida si una persona se encuentra fallecida con una novedad de retiro asociada (Entidades externas)
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	private boolean personaFallecidaConNovedad(String tipoIdentificacion, 
			String numeroIdentificacion){
		
		logger.debug("Inicio de método ValidadorPersonaFallecidaEntidadesExternas.personaFallecidaConNovedad");
		try {
			List<String> tipoTransaccion = new ArrayList<String>();
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.name());
			tipoTransaccion.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE.name());
			
			
			List<Integer> novedadesAsociadas = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_FALLECIDA_NOVEDAD_ENTIDADES_EXTERNAS)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_TRANSACCION, tipoTransaccion)
			.setParameter(ConstantesValidaciones.FALLECIDO_PARAM,NumerosEnterosConstants.UNO)
			.getResultList();
			
			if (novedadesAsociadas == null || novedadesAsociadas.isEmpty()) {
				return false;
			}
			
			logger.debug("Fin de método ValidadorPersonaFallecidaEntidadesExternas.personaJefeHogarRegistraMalUso");
			return true;
		} catch (NoResultException nre) {
			return false;
		}
	}
}
