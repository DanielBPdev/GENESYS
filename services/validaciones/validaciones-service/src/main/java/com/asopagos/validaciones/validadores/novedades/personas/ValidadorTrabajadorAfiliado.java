package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorTrabajadorAfiliado extends ValidadorAbstract{
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTrabajadorAfiliado.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        
	        //se consulta el afiliado con el tipo y número de documento
	        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_AFILIACIONES_AFILIADO_POR_TIPO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
	        
	        //se verifica que la cosulta no haya retornado un valor null
	        if(rolAfiliado == null || rolAfiliado.isEmpty()) {
	        	//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTrabajadorAfiliado.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TRABAJADOR_NO_AFILIADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TRABAJADOR_AFILIADO_CUALQUIER_ESTADO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
	        }
	        //de llegar a este punto la validación será exitosa
			logger.debug("HABILITADA- Fin de método ValidadorTrabajadorAfiliado.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADOR_AFILIADO_CUALQUIER_ESTADO);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TRABAJADOR_AFILIADO_CUALQUIER_ESTADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
