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

public class ValidadorBeneficiariosAfiliado extends ValidadorAbstract{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorBeneficiariosAfiliado.execute");
		try{
			List<Beneficiario> beneficiarios = null;
			if (datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR) != null) {
				Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
				
				// se consultan si existe un Beneficiario asociado al Grupo Familiar con Clasificación CONYUGE
				beneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR)
						.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
						.getResultList();
				
				// se verifica si para el grupo familiar encontró Beneficiarios si encuentra Habilita la Novedad.
				if (beneficiarios == null || beneficiarios.isEmpty()){
					
					logger.debug("NO HABILITADA- Fin de método ValidadorBeneficiariosAfiliado.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_GRUPO_FAMILIAR_SIN_BENEFICIARIOS),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_AFILIADO_GRUPO_FAMILIAR_BENEFICIARIOS,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						
				}
			} else {
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
	                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				// se consultan si existe un Beneficiario asociado al Grupo Familiar con Clasificación CONYUGE
	            List<Object[]> infoGruposFamiliares = (List<Object[]>) entityManager
	                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_AFILIADO_GRUPO_FAMILIAR)
	                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
	                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
	            
	         // se verifica si para el grupo familiar encontró Beneficiarios si encuentra Habilita la Novedad.
				if (infoGruposFamiliares == null || infoGruposFamiliares.isEmpty()){
					
					logger.debug("NO HABILITADA- Fin de método ValidadorBeneficiariosAfiliado.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_GRUPO_FAMILIAR_SIN_BENEFICIARIOS),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_AFILIADO_GRUPO_FAMILIAR_BENEFICIARIOS,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						
				}
			}
		
			//validación exitosa
			logger.debug("HABILITADA- Fin de método ValidadorBeneficiariosAfiliado.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_GRUPO_FAMILIAR_BENEFICIARIOS);	
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_GRUPO_FAMILIAR_BENEFICIARIOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
