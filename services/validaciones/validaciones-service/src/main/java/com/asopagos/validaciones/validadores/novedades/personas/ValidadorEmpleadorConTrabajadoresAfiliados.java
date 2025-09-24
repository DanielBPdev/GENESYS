package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorEmpleadorConTrabajadoresAfiliados extends ValidadorAbstract{

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEmpleadorConTrabajadoresAfiliados.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);

	        
	        List<RolAfiliado> rolAfiliado=(List<RolAfiliado>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR_CON_TRABAJADORES_AFILIADOS)
	        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
	    	.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	    	.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_CAJA, EstadoAfiliadoEnum.ACTIVO).getResultList();
	        
	        if(rolAfiliado!=null && !rolAfiliado.isEmpty()){
	        	logger.debug("HABILITADA- Fin de método ValidadorEmpleadorConTrabajadoresAfiliados.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_AFILIADOS);
	        }else{
	        	logger.debug("NO HABILITADA- Fin de método ValidadorEmpleadorConTrabajadoresAfiliados.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_TRABAJADORES_AFILIADOS),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_AFILIADOS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
	        }

		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_TRABAJADORES_AFILIADOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}

}
