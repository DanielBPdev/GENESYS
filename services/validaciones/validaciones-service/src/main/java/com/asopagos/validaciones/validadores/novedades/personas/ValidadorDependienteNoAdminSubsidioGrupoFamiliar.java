package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.AdminSubsidioGrupo;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * si hay un administrador del subsidio diferente del trabajador 
 * dependiente en el grupo familiar objeto de la novedad
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorDependienteNoAdminSubsidioGrupoFamiliar extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorDependienteNoAdminSubsidioGrupoFamiliar.execute");
		try{
	        Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
	        
	        //se consulta si existe un administrador del subsidio diferente al afiliado para el grupo Familiar.
	        List<AdminSubsidioGrupo> adminSubsidioGrupo = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ADMIN_DEL_SUBSIDIO)
	        		.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
					.setParameter(ConstantesValidaciones.ESTADO_MEDIO_PAGO_PARAM, Boolean.TRUE)
					.setParameter(ConstantesValidaciones.AFILIADO_ADMON_SUBSIDIO_PARAM, Boolean.FALSE)
					.getResultList();
	        
	        //se realiza la validación
			if(adminSubsidioGrupo != null && !adminSubsidioGrupo.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorDependienteNoAdminSubsidioGrupoFamiliar.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorDependienteNoAdminSubsidioGrupoFamiliar.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}