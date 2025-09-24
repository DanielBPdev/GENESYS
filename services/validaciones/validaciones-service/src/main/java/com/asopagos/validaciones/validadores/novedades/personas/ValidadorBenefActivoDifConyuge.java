package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * Debe tener por lo menos un grupo familiar con beneficiarios diferentes 
 * de cónyuge que en el campo "Estado con respecto al afiliado principal" 
 * tengan el valor "Activo"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorBenefActivoDifConyuge extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorBenefActivoDifConyuge.execute");
		try{
			Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
			
			// se consultan si existe un Beneficiario asociado al Grupo Familiar con Clasificación CONYUGE
			List<Beneficiario> beneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO)
					.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
					.getResultList();
			
			// se verifica si la consulta encontró beneficiarios de Tipo Cónyuge, no Habilita la Novedad.
			if (beneficiarios != null && !beneficiarios.isEmpty()){
				
				//se recorre la lista del beneficiarios verificando si existe alguno del tipo CONYUGE
				for (Beneficiario beneficiario : beneficiarios){
					if (beneficiario.getTipoBeneficiario().equals(ClasificacionEnum.CONYUGE)
					        && beneficiarios.size() <= NumerosEnterosConstants.UNO) {
						// validación fallida
						logger.debug("NO HABILITADA- Fin de método ValidadorBenefActivoDifConyuge.execute");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_ACTIVO_DIF_CONYUGE),ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_DIF_CONYUGE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
				}
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorBenefActivoDifConyuge.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_DIF_CONYUGE);	

			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorBenefActivoDifConyuge.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_ACTIVO_DIF_CONYUGE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_DIF_CONYUGE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_DIF_CONYUGE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
