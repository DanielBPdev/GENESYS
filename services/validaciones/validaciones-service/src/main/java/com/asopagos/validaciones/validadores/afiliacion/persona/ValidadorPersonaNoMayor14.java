package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la logica para verificar cuando una persona no es mayor de
 * 14 años
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorPersonaNoMayor14 extends ValidadorAbstract {

	/**
	 * Constante que estable la edad a verificar
	 */
	private static final int EDAD_ESTABLECIDA = 14;

	/**
	 * Metodo encargado de validar si una persona no es mayor de 14 años
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaNoMayor14");
			boolean existe = false;
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
			    String tipoIdentificacion = null;
			    String numeroIdentificacion = null;
			    // Se verifica si la validacion aplica por una novedad de beneficiario
			    // Se obtienen los datos de identificacion segun a quien aplica la novedad
			    if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
	                    datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
			        tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
			        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			    } else {
			        tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
			        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			    }
				TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
				String fechaNacimientoLong = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				
				/* Se verifica si la fecha de nacimiento enviada es null */
				if (fechaNacimientoLong == null || fechaNacimientoLong.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_14);
				} else {
					String fechaNacimiento = ValidacionPersonaUtils.convertirFecha(fechaNacimientoLong);
					if (tipoIdentificacionEnum != null && numeroIdentificacion != null && !numeroIdentificacion.equals("")) {
						PersonaDetalle persona=null;
						List<PersonaDetalle> personaTipoIdentificacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION, PersonaDetalle.class)
                                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM,tipoIdentificacionEnum )
                                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

						if (!personaTipoIdentificacion.isEmpty()) {
							persona = personaTipoIdentificacion.get(0);
							existe = true;
						} else {
							List<PersonaDetalle> personaIdentificacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_NUMERO_IDENTIFICACION, PersonaDetalle.class)
                                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

							if (!personaIdentificacion.isEmpty()) {
								persona = personaIdentificacion.get(0);
								existe = true;
							}
						}
						if (existe) {
							/*
							 * si existe la persona se toma la fecha de
							 * nacimiento guardada
							 */
							fechaNacimiento = ValidacionPersonaUtils
									.convertirFecha(persona.getFechaNacimiento().getTime() + "");
						}
						int edadPersona = ValidacionPersonaUtils.calcularEdadAnos(fechaNacimiento);
						if (edadPersona < EDAD_ESTABLECIDA) {
							return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_MAYOR_14),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_14,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
						}
					} else {
						/* mensaje no evaluado porque falta informacion */
						logger.debug("No evaludao- Falta informacion");
						return crearMensajeNoEvaluado();
					}
				}
			} else {
				/* mensaje no evaluado porque faltan datos */
				logger.debug("No evaludao- Faltan datos");
				return crearMensajeNoEvaluado();
			}
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_14);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			logger.debug("No evaludao- Ocurrio alguna excepcion");
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_MAYOR_14),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_NO_MAYOR_14,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
