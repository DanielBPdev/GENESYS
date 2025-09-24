package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
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
 * CLASE QUE VALIDA:
 * Valida que la persona tenga activa una afiliación como trabajador dependiente
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoComoTrabajadorDependiente extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoComoTrabajadorDependiente.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se agregan los tipos con los cuales se van a filtrar los resultados
	        List<TipoAfiliadoEnum> tipoValido = new ArrayList<TipoAfiliadoEnum>();
	        tipoValido.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
	        
	        //se consulta el afiliado con el tipo  de documento, el número, el tipo de afiliado y el estado del afiliado
			List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_AFILIACION)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM , tipoValido)
					.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
					.getResultList();
	        
	        //se realiza la validación
			if(rolAfiliado != null && !rolAfiliado.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoComoTrabajadorDependiente.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_TRABAJADOR_DEPENDIENTE);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoComoTrabajadorDependiente.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_TRABAJADOR_DEPENDIENTE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_TRABAJADOR_DEPENDIENTE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_TRABAJADOR_DEPENDIENTE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
