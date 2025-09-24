package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si el solicitante tiene una licencia de maternidad o
 * paternidad activa
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorSolicitanteTieneLicenciaMaternidadPaternidadActiva extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSolicitanteTieneLicenciaMaternidadPaternidadActiva.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<TipoTransaccionEnum> tiposDeNovedadBuscados = new ArrayList<TipoTransaccionEnum>();
	        tiposDeNovedadBuscados.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB);
	        tiposDeNovedadBuscados.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL);
	        tiposDeNovedadBuscados.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB);
	        
	        Calendar fechaActualSistema = Calendar.getInstance();
	        
	        List<NovedadDetalle> novedadesDetalle =  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_NOVEDAD_DETALLE_VIGENTE_POR_PERSONA_POR_TRANSACCIONES)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, tiposDeNovedadBuscados)
					.setParameter("fechaActual", fechaActualSistema.getTime())
					.getResultList();
	        
	        if (novedadesDetalle != null && !novedadesDetalle.isEmpty()) {
	        	logger.debug("No Aprobada - Fin de método ValidadorSolicitanteTieneLicenciaMaternidadPaternidadActiva.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_LMA_VIGENTE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_LICENCIA_MATERNIDAD_PATERNIDAD_ACTIVA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción ValidadorSolicitanteTieneLicenciaMaternidadPaternidadActiva.execute", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_LICENCIA_MATERNIDAD_PATERNIDAD_ACTIVA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
		// Retorna validación exitosa
		return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_LICENCIA_MATERNIDAD_PATERNIDAD_ACTIVA);
	}
}
