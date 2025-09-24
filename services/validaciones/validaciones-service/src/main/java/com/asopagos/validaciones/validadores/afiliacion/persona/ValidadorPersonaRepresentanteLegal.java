package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;import com.asopagos.validaciones.clients.ExisteEnListasNegras;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada para el mismo emleador. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaRepresentanteLegal extends ValidadorAbstract{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaRepresentanteLegal.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

				String tipoIdEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
				TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(tipoIdEmpleador);
				String numeroIdentificacionEmpleador = datosValidacion
						.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
				
				if (tipoIdentificacion != null && tipoIdentificacionEmpleador != null && numeroIdentificacion != null
						&& numeroIdentificacionEmpleador != null) {
					boolean existe = false;
					if (validarExistencia(tipoIdentificacion, numeroIdentificacion, tipoIdentificacionEmpleador,
							numeroIdentificacionEmpleador)) {
						existe = true;
					}
					if (existe) {
						logger.debug("No aprobada- La persona es representante legal del empleador");
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_PERSONA_REPRESENTANTE_LEGAL),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_REPRESENTANTE_LEGAL_EMPLEADOR,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_REPRESENTANTE_LEGAL_EMPLEADOR);
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_REPRESENTANTE_LEGAL),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_REPRESENTANTE_LEGAL_EMPLEADOR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  boolean validarExistencia(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacionEmpleador, String numeroIdentificacionEmpleador ){
		
		try {
			entityManager
			.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_AFILIADO_REPRESENTANTE_LEGAL)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador)
			.setParameter("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador)
			.setParameter(ConstantesValidaciones.CLASIFICACION_PARAM,ValidacionPersonaUtils.obtenerClasificacionNaturalYDomestico())
			.getSingleResult();
			return true;
			
		} catch (NoResultException e) {
			return false;
		}
		
	}
	
	

}
