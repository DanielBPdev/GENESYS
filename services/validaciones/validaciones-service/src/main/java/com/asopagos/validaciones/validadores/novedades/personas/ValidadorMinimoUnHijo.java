package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA: Validar que al menos uno de los beneficiarios del afiliado
 * principal sea tipo Hijo
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorMinimoUnHijo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorMinimoUnHijo.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

			// se consultan los beneficiarios tipo hijo del afiliado.
			List<Beneficiario> beneficiarios =  entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_POR_AFILIADO)
					.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM,
							ValidacionPersonaUtils.obtenerClasificacionHijo())
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getResultList();

			if ( !beneficiarios.isEmpty()) {
				// validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorMinimoUnHijo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MINIMO_UN_HIJO);
			} else {
				// validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorMinimoUnHijo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MINIMO_UN_HIJO),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_MINIMO_UN_HIJO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_MINIMO_UN_HIJO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}