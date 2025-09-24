package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
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

/**
 * CLASE QUE VALIDA:
 * Valor del campo "Estado con respecto al afiliado principal": "Activo"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorActivoRespectoAfiliadoPpal extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorActivoRespectoAfiliadoPpal.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        
	        //se consulta el afiliado con el tipo y número de documento tanto del beneficiario como del afiliado principal
	        Beneficiario beneficiario = (Beneficiario) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_Y_NUMERO_BENEFICIARIO_Y_AFILIADO)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
					.getSingleResult();      
	        
	        //se realiza la validación
			if(beneficiario != null && estadosValidos.contains(beneficiario.getEstadoBeneficiarioAfiliado())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorActivoRespectoAfiliadoPpal.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ACTIVO_RESPECTO_AFILIADO_PPAL);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorActivoRespectoAfiliadoPpal.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ACTIVO_RESPECTO_AFILIADO_PPAL),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ACTIVO_RESPECTO_AFILIADO_PPAL,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ACTIVO_RESPECTO_AFILIADO_PPAL, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
