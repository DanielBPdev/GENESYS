package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * El afiliado principal debe tener al beneficiario tipo hijo objeto de la novedad 
 * con estado "Activo" o "Inactivo" en el campo "Estado con respecto al afiliado principal"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoHijo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEstadoHijo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
	        
	        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        Beneficiario beneficiario = (Beneficiario) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_Y_NUMERO_BENEFICIARIO_Y_AFILIADO)
    		.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
    		.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario)
			.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
			.getSingleResult();
			
	        List<EstadoAfiliadoEnum> estadoList = ValidacionPersonaUtils.obtenerEstadosAfiliado();
	        
			if(beneficiario != null && estadoList.contains(beneficiario.getEstadoBeneficiarioAfiliado())
					&& ValidacionPersonaUtils.obtenerClasificacionHijo().contains(beneficiario.getTipoBeneficiario())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorEstadoHijo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HIJO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorEstadoHijo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HIJO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_HIJO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_HIJO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
