package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * clase que valida que el afiliado principal debe tener 
 * estado "Activo" en el campo "Estado con respecto al empleador" 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoActivoRespectoEmpleador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoActivoRespectoEmpleador.execute");
		try{
            if (datosValidacion == null || datosValidacion.isEmpty() || datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM).isEmpty()
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM).isEmpty()
                    || datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM).isEmpty()
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM).isEmpty()) {
                crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS,
                        ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }
            
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));
	        String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        
	        //se consulta el estado del afiliado con el tipo y número de documento del mismo y de su empleador
	        EstadoAfiliadoEnum estadoAfiliado = (EstadoAfiliadoEnum) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_AFILIADO_CAJA_RESPECTO_EMPLEADOR)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
					.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador)
					.getSingleResult();
	        
	        //se realiza la validación
			if(estadoAfiliado != null && estadosValidos.contains(estadoAfiliado)){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoActivoRespectoEmpleador.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoActivoRespectoEmpleador.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (NoResultException e){
		    logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
