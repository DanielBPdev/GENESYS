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
 * CLASE QUE VALIDA:
 * Validar que no hay por lo menos un beneficiario Hijo - Hermano 
 * huérfano de padres en todos los grupos familiares relacionados.
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorNingunBeneficiarioHijo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorNingunBeneficiarioHijo.execute");
		try{
	        TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        List <Beneficiario> beneficiario = (List <Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_POR_AFILIADO)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerListHijoHuerfano())
    		.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.getResultList();
	        
	        //verificamos que no se haya encontrado ningún beneficiario 
			if(beneficiario == null || beneficiario.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorNingunBeneficiarioHijo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NINGUN_BENEFICIARIO_HIJO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorNingunBeneficiarioHijo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NINGUN_BENEFICIARIO_HIJO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_NINGUN_BENEFICIARIO_HIJO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_NINGUN_BENEFICIARIO_HIJO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}