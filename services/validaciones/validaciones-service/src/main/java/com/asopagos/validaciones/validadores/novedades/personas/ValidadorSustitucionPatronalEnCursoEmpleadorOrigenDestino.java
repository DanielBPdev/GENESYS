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
 * Valida si hay un registro de novedad de sustitución patronal 
 * en curso asociado a los empleadores de origen y destino
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorSustitucionPatronalEnCursoEmpleadorOrigenDestino extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSustitucionPatronalEnCursoEmpleadorOrigenDestino.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacionEmplOrigen = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));
	        String numeroIdentificacionEmplOrigen = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
	        Long idEmplDestino = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_EMPL_DEST_PARAM));
	        
	        List<EstadoSolicitudNovedadEnum> estadosNoValidos = new ArrayList<EstadoSolicitudNovedadEnum>();
	        estadosNoValidos.add(EstadoSolicitudNovedadEnum.CERRADA);
	        
	        //se consultan las novedades de sustitución patronal con estado diferente a CERRADA, asociadas al empleador origen
	        List<SolicitudNovedadEmpleador> novedadesEmpleadorOrigen = (List<SolicitudNovedadEmpleador>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDAD_DESAFILIACION_EN_CURSO)
					.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmplOrigen)
					.setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmplOrigen)
					.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, TipoTransaccionEnum.SUSTITUCION_PATRONAL)
					.setParameter(ConstantesValidaciones.ESTADO_SOLICITUD_NOVEDAD, estadosNoValidos)
					.getResultList();
	        
	      //se consultan las novedades de sustitución patronal con estado diferente a CERRADA, asociadas al empleador destino
	        List<SolicitudNovedadEmpleador> novedadesEmpleadorDestino = (List<SolicitudNovedadEmpleador>)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDAD_DESAFILIACION_EN_CURSO_CON_ID)
					.setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, idEmplDestino)
					.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, TipoTransaccionEnum.SUSTITUCION_PATRONAL)
					.setParameter(ConstantesValidaciones.ESTADO_SOLICITUD_NOVEDAD, estadosNoValidos)
					.getResultList();
	        
			if(novedadesEmpleadorOrigen != null && !novedadesEmpleadorOrigen.isEmpty() && novedadesEmpleadorDestino != null && !novedadesEmpleadorDestino.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorSustitucionPatronalEnCursoEmpleadorOrigenDestino.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SUSTITUCION_PATRONAL_EN_CURSO);	

			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorSustitucionPatronalEnCursoEmpleadorOrigenDestino.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_SUSTITUCION_PATRONAL_EN_CURSO),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_SUSTITUCION_PATRONAL_EN_CURSO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_SUSTITUCION_PATRONAL_EN_CURSO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}