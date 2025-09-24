package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada como pensionado. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaPensionado extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaPensionado.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")){
					boolean existe = false;
					List<RolAfiliado> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.PENSIONADO)
							.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO).getResultList();
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<RolAfiliado> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_NUMERO_TIPO_AFILIACION_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.PENSIONADO)
								.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO).getResultList();
						if(personasConNumero != null && !personasConNumero.isEmpty()){
							existe = true;
						}
					}else{
						existe = true;
					}
					if(existe){
						logger.debug("No aprobada- Existe persona afiliada como pensionado");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_PENSIONADO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_PENSIONADO,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);					
					}
				}else{
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_PENSIONADO);
		}catch(Exception e){
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada",e);
			return crearMensajeNoEvaluado();
		}
	}


	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  ValidacionDTO crearMensajeNoEvaluado(){
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_PENSIONADO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_PENSIONADO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}
