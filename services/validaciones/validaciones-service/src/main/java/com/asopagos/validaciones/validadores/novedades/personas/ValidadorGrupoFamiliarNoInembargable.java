package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Se debe validar que el valor del campo 
 * "grupo familiar inembargable" tenga el valor "No"
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorGrupoFamiliarNoInembargable extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorGrupoFamiliarNoInembargable.execute");
		try{
			Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
		
	        GrupoFamiliar grupoFamiliar = (GrupoFamiliar) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_POR_ID)
	        		.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
	        		.getSingleResult();

	        //se realiza la validación
			if(grupoFamiliar != null && !grupoFamiliar.getInembargable()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorGrupoFamiliarNoInembargable.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_NO_INEMBARGABLE);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorGrupoFamiliarNoInembargable.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_GRUPO_FAMILIAR_NO_INEMBARGABLE),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_NO_INEMBARGABLE,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_NO_INEMBARGABLE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}