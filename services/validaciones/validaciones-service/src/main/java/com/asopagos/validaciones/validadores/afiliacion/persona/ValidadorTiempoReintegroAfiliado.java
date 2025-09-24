/**
 * 
 */
package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Date;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar si un Afiliado cumple con el tiempo
 * desde su ultimo retiro para reintegrarse.
 * 
 * @author Jorge Leonardo Camargo Cuervo <jcamargo@heinsohn.com.co>
 *
 */
public class ValidadorTiempoReintegroAfiliado extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTiempoReintegroAfiliado.execute");
		try {
			String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
			TipoAfiliadoEnum tipoAfiliado = null;
			Long idEmpleador = null;
			if(datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM) != null){
			    tipoAfiliado = TipoAfiliadoEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM));
			}
			if(datosValidacion.get(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPLEADOR) != null){
			    idEmpleador = new Long(datosValidacion.get(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPLEADOR));
			}
			Date fechaRetiro = null;
            if (idEmpleador != null) {
                fechaRetiro = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_RETIRO_AFILIADO_DEPENDIENTE)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                        .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, tipoAfiliado)
                        .setParameter(ConstantesValidaciones.NAMED_QUERY_PARAM_ID_EMPLEADOR, idEmpleador)
                        .getSingleResult();
            }
            else {
                fechaRetiro = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_RETIRO_AFILIADO)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                        .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, tipoAfiliado).getSingleResult();
            }
            
            if(fechaRetiro != null){
				Date fechaActual = new Date();
				Long dias = (fechaActual.getTime() - fechaRetiro.getTime());
				Long diasReintegro = CalendarUtils.toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_REINTEGRO_AFILIADO));
				if (dias > diasReintegro) {
					logger.debug("NO_APROBADO-Fin de método ValidadorTiempoReintegroAfiliado.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_REINTEGRO_SUPERADA),
							ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO_AFILIADO, null);
				}
            }
            
			logger.debug("Fin de método ValidadorTiempoReintegroAfiliado.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO_AFILIADO);
		} catch (NoResultException e) {
            logger.debug("Fin de método ValidadorTiempoReintegroAfiliado.execute");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO_AFILIADO);
        }
		catch (Exception e) {
			logger.error("Ocurrio un error en el método ValidadorTiempoReintegroAfiliado  "+e.getMessage());
			return crearMensajeNoEvaluado(e.getMessage());
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado(String message) {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_FECHA_REINTEGRO_SUPERADA) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE)+"\n"+message,
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_TIEMPO_REINTEGRO_AFILIADO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
