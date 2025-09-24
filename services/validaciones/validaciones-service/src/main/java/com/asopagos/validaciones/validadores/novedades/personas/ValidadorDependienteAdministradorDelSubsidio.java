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
 * Valida si el trabajador dependiente es el mismo administrador del subsidio
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorDependienteAdministradorDelSubsidio extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorDependienteAdministradorDelSubsidio.execute");
		try{
	        Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
	        
	        //se consulta si el afiliado principal es el mismo Admon Subsidio para el grupo Familiar.
	        List<AdminSubsidioGrupo> adminSubsidioGrupo = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_ADMIN_DEL_SUBSIDIO)
	        		.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
					.setParameter(ConstantesValidaciones.ESTADO_MEDIO_PAGO_PARAM, Boolean.TRUE)
					.setParameter(ConstantesValidaciones.AFILIADO_ADMON_SUBSIDIO_PARAM, Boolean.TRUE)
					.getResultList();
	  
			if(adminSubsidioGrupo != null && !adminSubsidioGrupo.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorDependienteAdministradorDelSubsidio.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorDependienteAdministradorDelSubsidio.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DEPENDIENTE_ADMIN_SUBSIDIO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}