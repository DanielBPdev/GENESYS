package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * El afiliado principal debe tener a su cónyuge con estado "Activo" o "Inactivo" 
 * en el campo "Estado con respecto al afiliado principal"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoConyuge extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorEstadoConyuge.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

	        //se crea una lista clasificacionEnum con la constante CONYUGE que se le pasará a la query por parámetro com tipo de beneficiario
	        List<ClasificacionEnum> clasificacionConyuge = new ArrayList<ClasificacionEnum>();
	        clasificacionConyuge.add(ClasificacionEnum.CONYUGE);
	        
	        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        List<Beneficiario> beneficiarios = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_AFILIADO)
	        		.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, clasificacionConyuge)
	        		.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerEstadosAfiliado())
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.getResultList();
	        
			if(beneficiarios != null){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorEstadoConyuge.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CONYUGE);	
			}else{
				//validación fallida
				logger.info("NO HABILITADA- Fin de método ValidadorEstadoConyuge.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_CONYUGE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_CONYUGE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} 
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_CONYUGE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}