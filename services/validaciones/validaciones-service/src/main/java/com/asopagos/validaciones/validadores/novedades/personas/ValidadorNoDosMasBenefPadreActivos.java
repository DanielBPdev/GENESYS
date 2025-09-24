package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
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
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * Validar que no hay más de dos beneficiarios tipo 
 * "Padre" activos con respecto al afiliado 
 * principal en todos los grupos familiares relacionados.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorNoDosMasBenefPadreActivos extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorNoDosMasBenefPadreActivos.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	      //se listan los estados válidos para aprobar la validación
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        
	        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        List<Beneficiario> beneficiariosPadre = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CANTIDAD_BENEFICIARIOS_POR_TIPO)
	        		.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionPadre())
	        		.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadosValidos)
	    			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
	    			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	    			.getResultList();
	        
			
			if(beneficiariosPadre.size() <= NumerosEnterosConstants.DOS){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorNoDosMasBenefPadreActivos.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NO_DOS_BENEFICIARIO_PADRES_ACT);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorNoDosMasBenefPadreActivos.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_DOS_BENEFICIARIO_PADRES_ACT),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_NO_DOS_BENEFICIARIO_PADRES_ACT,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_NO_DOS_BENEFICIARIO_PADRES_ACT, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}