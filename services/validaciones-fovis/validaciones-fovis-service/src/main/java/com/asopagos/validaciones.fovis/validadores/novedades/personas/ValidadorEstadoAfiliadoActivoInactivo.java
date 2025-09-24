package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CLASE QUE VALIDA:
 * Valor del campo "Estado de afiliación" del afiliado principal: "Activo", "Inactivo"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoAfiliadoActivoInactivo extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorEstadoAfiliadoActivoInactivo.execute OK OK ");
		try{
			//se obtienen los datos de validacion necesarios
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se listan los estados válidos del afiliado
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList <EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        estadosValidos.add(EstadoAfiliadoEnum.INACTIVO);
	        
	        
	        //se consulta el afiliado con el tipo y número de documento y los estados válidos para el mismo
	        Afiliado afiliado= (Afiliado) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADOS_VALIDOS_AFILIADO_PARAM, estadosValidos)
					.getSingleResult();
	        
	        //se realiza la validación
			if(afiliado != null){
				//validación exitosa
				logger.info("HABILITADA- Fin de método ValidadorEstadoAfiliadoActivoInactivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO);	
			}
			else{
				//validacion fallida
				logger.info("NO HABILITADA- Fin de método ValidadorEstadoAfiliadoActivoInactivo.execute luis");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_AFILIADO_ACTIVO_INACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
			}
			
		}
		catch (NoResultException nre){
			logger.error("No evaluado - no se encuentra el afiliado con los datos ingresados", nre);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_ACTIVO_INACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
