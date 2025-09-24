package com.asopagos.validaciones.fovis.validadores.common;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * CLASE QUE VALIDA:
 * si el afiliado existe 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoExiste extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoExiste.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        Afiliado afiliado = (Afiliado) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
	        //se realiza la validación
			if(afiliado != null){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoExiste.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoExiste.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} 
		catch(NoResultException nre) {
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoExiste.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}