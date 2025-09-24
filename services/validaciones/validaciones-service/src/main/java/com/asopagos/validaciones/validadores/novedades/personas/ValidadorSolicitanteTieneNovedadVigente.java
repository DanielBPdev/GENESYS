package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si El solicitante tiene una novedad personal activa 
 * en los periodos relacionados por las fechas de vigencia de la novedad
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorSolicitanteTieneNovedadVigente extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSolicitanteTieneNovedadVigente.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        Calendar fechaActualSistema = Calendar.getInstance();
	        
	        List<NovedadDetalle> novedadesDetalle =  entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_NOVEDAD_DETALLE_VIGENTE_POR_PERSONA)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter("fechaActual", fechaActualSistema.getTime())
					.getResultList();
	        
	        if (novedadesDetalle != null && !novedadesDetalle.isEmpty()) {
	        	logger.debug("No Aprobada - Fin de método ValidadorSolicitanteTieneNovedadVigente.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_NOVEDAD_VIGENTE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_NOVEDAD_VIGENTE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción ValidadorSolicitanteTieneNovedadVigente.execute", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_NOVEDAD_VIGENTE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
		// Retorna validación exitosa
		return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SOLICITANTE_TIENE_NOVEDAD_VIGENTE);
	}
}
