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

/**
 * CLASE QUE VALIDA:
 * El afiliado principal debe tener al menos un grupo 
 * familiar con al menos un beneficiario activo
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoGrupoFamMin1BenefAct extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoGrupoFamMin1BenefAct.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta(n) el(los) beneficiario(s) asociado(s) al afiliado principal 
	        //con el tipo y número de documento y su estado (activo)
	        List <Beneficiario> beneficiario = (List <Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_Y_AFILIADO_PPAL)
    		.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.getResultList();
			
			if(beneficiario != null && !beneficiario.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoGrupoFamMin1BenefAct.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_GRP_FAM_MIN_1_BEN_ACT);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoGrupoFamMin1BenefAct.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_GRP_FAM_MIN_1_BEN_ACT),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_GRP_FAM_MIN_1_BEN_ACT,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_GRP_FAM_MIN_1_BEN_ACT, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}