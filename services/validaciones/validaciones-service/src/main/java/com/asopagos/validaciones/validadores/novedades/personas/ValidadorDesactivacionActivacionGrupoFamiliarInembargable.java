package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * 
 * Condiciones para activar condición de grupo familiar inembargable
 * El grupo familiar debe estar percibiendo subsidio monetario para el periodo 
 * en el que se solicita la activación de grupo familiar inembargable
 * El grupo familiar (adminstrador del subsidio) debe tener activa una 
 * cesión o retención del subsidio.
 * 
 * Condiciones para desactivar condicion de grupo familiar inembargable:
 * Que el grupo familiar en el momento de la solicitud tenga en el campo 
 * "Condición de grupo familiar inembargable" el valor "activo".
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorDesactivacionActivacionGrupoFamiliarInembargable extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorDesactivacionActivacionGrupoFamiliarInembargable.execute");
		try{
			Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
	        
	        //se verifica la condicion del grupo familiar en su campo grupoFamiliarInembargable
	        Boolean isGrupoFamiliarInembargable = (Boolean) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_GRUPO_FAMILIAR_INEMBARGABLE_POR_ID)
	        		.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
	        		.getSingleResult();

			if(isGrupoFamiliarInembargable){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorDesactivacionActivacionGrupoFamiliarInembargable.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE);	
			}
			else{
				//se verifica que el administrador del subsidio del grupo familiar tenga activa una cesión o una retención del subsidio
				boolean activaCesionORetencion = (Boolean) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CESION_RETENCION_SUBSIDIO_GRUPO_FAMILIAR)
		        		.setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
		        		.getSingleResult();
				
				// TODO validacion de si el grupo familiar percibe subsidio monetario
				boolean percibeSubsidioMonetario = true;
				
				if(activaCesionORetencion && percibeSubsidioMonetario){
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorDesactivacionActivacionGrupoFamiliarInembargable.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE);
				}
				else{
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorDesactivacionActivacionGrupoFamiliarInembargable.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_DESACTIVACION),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}