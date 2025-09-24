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
 * Debe tener por lo menos un grupo familiar con beneficiarios 
 * que en el campo "Estado con respecto al afiliado principal" 
 * tengan el valor "Activo"
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorGrupoFamiliarActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorGrupoFamiliarActivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			
			// se consulta el afiliado con el tipo y número de documento
			List<Beneficiario> beneficiarios = 
					(List<Beneficiario>) entityManager.createNamedQuery
					(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_BENEFICIARIOS_ACTIVOS_AFILIADO)
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
			
			//se verifica que la consulta haya retornado al menos un
			//beneficiario activo pertenenciente a un grupo familiar del 
			//afiliado.
			if (beneficiarios != null && !beneficiarios.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorGrupoFamiliarActivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_ACTIVO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorGrupoFamiliarActivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_GRUPO_FAMILIAR_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_ACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}