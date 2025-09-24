package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
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
 * si el valor del campo "Estado de afiliación" del afiliado principal es "Activo" 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoActivoDosPorciento extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoActivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        Long cantidadRolesActivos = (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONTAR_TIPO_AFILIADO_ACTIVO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
	        //se realiza la validación
			if(cantidadRolesActivos != null && cantidadRolesActivos > 0){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoActivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_DOS_POR_CIENTO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoActivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_DOS_POR_CIENTO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (NoResultException e) {
		    //validación fallida
            logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoActivo.execute");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
                    ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_DOS_POR_CIENTO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO_DOS_POR_CIENTO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}