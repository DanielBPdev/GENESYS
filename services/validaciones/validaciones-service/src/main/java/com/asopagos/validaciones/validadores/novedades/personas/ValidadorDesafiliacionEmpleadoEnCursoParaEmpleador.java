package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Valida si hay una solicitud de novedad de desafiliación de empleado 
 * en curso para el empelador objeto de la solicitud
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorDesafiliacionEmpleadoEnCursoParaEmpleador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorDesafiliacionEmpleadoEnCursoParaEmpleador.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
	        
	        List<EstadoSolicitudNovedadEnum> estadosNoValidos = new ArrayList<EstadoSolicitudNovedadEnum>();
	        estadosNoValidos.add(EstadoSolicitudNovedadEnum.CERRADA);
	        
	        //se consultan las novedades de desafiliación con estado diferente a CERRADA, asociadas al empleador
	        List<SolicitudNovedadEmpleador> novedades = (List<SolicitudNovedadEmpleador>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDAD_DESAFILIACION_EN_CURSO)
					.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, TipoTransaccionEnum.DESAFILIACION)
					.setParameter(ConstantesValidaciones.ESTADO_SOLICITUD_NOVEDAD, estadosNoValidos)
					.getResultList();
	        
	        
			if(novedades != null && !novedades.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorDesafiliacionEmpleadoEnCursoParaEmpleador.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DESAFILIACION_EMPLEADO_EN_CURSO);	

			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorDesafiliacionEmpleadoEnCursoParaEmpleador.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DESAFILIACION_EMPLEADO_EN_CURSO),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_DESAFILIACION_EMPLEADO_EN_CURSO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DESAFILIACION_EMPLEADO_EN_CURSO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}