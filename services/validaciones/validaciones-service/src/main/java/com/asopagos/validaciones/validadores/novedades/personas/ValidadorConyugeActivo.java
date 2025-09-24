package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
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
 * Validar que el afiliado principal tenga un cónyuge activo hasta la fecha del  registro de la novedad.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorConyugeActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorConyugeActivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<ClasificacionEnum> clasificacion = new ArrayList<ClasificacionEnum>();
			clasificacion.add(ClasificacionEnum.CONYUGE);
		
	        //se consulta el beneficiario con el tipo y número de documento del afiliado principal, el tipo de beneficiario y su estado (activo o inactivo)
	        Beneficiario beneficiario = (Beneficiario) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CANTIDAD_BENEFICIARIOS_POR_TIPO)
			.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, clasificacion)
    		.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
			.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			.getSingleResult();
			
			if(beneficiario != null){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorConyugeActivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorConyugeActivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_CONYUGE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} 
		catch (NoResultException nre){
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorConyugeActivo.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_CONYUGE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_CONYUGE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}