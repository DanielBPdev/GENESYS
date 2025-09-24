package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

import java.util.List;
import java.util.Map;

public class ValidadorPersonaBeneficiarioEnCustodia extends ValidadorFovisAbstract{

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		//try{
			TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
	        
	        List<Beneficiario> beneficiario=(List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
	        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
	        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionBeneficiario)
	        .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA)
	        .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
    		.getResultList();

	        if(!beneficiario.isEmpty() && beneficiario != null){
	        	logger.debug("BENEFICIARIO EN CUSTODIA- Fin de método ValidadorPersonaBeneficiarioEnCustodia.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIARIO_EN_CUSTODIA);
	        }else{
	        	logger.debug("NO HAY BENEFICIARIO EN CUSTODIA- Fin de método ValidadorPersonaBeneficiarioEnCustodia.execute");
	        	return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_EN_CUSTODIA),ResultadoValidacionEnum.NO_APROBADA,
	        			ValidacionCoreEnum.VALIDACION_BENEFICIARIO_EN_CUSTODIA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
	        }
			

		/*}catch(Exception e){
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_BENEFICIARIO_EN_CUSTODIA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}*/
	}

	
}
