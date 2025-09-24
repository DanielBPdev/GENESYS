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
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si el Afiliado principal tiene al menos 
 * un beneficario diferente de cónyuge
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorAfiliadoConBeneficiarioDiferenteConyuge extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio ValidadorAfiliadoConBeneficiarioDiferenteConyuge.execute");
		try{
			// Se recuperan los datos del afiliado para la validacion
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Lista de tipos de beneficiario no permitidos 
	        List<ClasificacionEnum> tiposNoValidos = new ArrayList<ClasificacionEnum>();
	        tiposNoValidos.add(ClasificacionEnum.CONYUGE);
	        
	        // Lista de estado permitido
	        List<EstadoAfiliadoEnum> estadoBeneficiarioAfiliado = new ArrayList<EstadoAfiliadoEnum>();
	        estadoBeneficiarioAfiliado.add(EstadoAfiliadoEnum.ACTIVO);
	        
	        // Consula los beneficiarios  diferentes a tipo CONYUGE en estado ACTIVO que tenga el afiliado principal 
	        List<Beneficiario> beneficiarios = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_DIFERENTE_TIPO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tiposNoValidos)
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoBeneficiarioAfiliado)
					.getResultList();
	        
	        // Si la consulta no arroja resultados la validación es fallida
			if(beneficiarios == null || beneficiarios.isEmpty()){
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoConBeneficiarioDiferenteConyuge.execute");
				// Retorna validación fallida T2
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_SIN_BENEFIARIO_DIFERENTE_CONYUGE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_CON_BENEFICIARIO_DIF_CONYUGE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			} else {
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoConBeneficiarioDiferenteConyuge.execute");
				// Retorna validación exitosa
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_CON_BENEFICIARIO_DIF_CONYUGE);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			// Retorna validacion abortada
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_CON_BENEFICIARIO_DIF_CONYUGE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
