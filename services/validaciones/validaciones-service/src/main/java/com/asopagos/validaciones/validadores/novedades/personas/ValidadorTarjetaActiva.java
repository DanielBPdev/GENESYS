package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.Tarjeta;
import com.asopagos.enumeraciones.core.EstadoTarjetaEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * La tarjeta para la persona objeto de la novedad debe estar activa (desboqueada)
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTarjetaActiva extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTarjetaActiva.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se busca la tarjeta en cuyo campo (tarjetaHabiente) esté relacionada con la 
	        //persona objeto de la novedad
	        Tarjeta tarjetaAfiliado = (Tarjeta)entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_TARJETA_AFILIADO)
			        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
			        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
			        .getSingleResult();
		    
	        //se listan los estados validos para la tarjeta
	        List<EstadoTarjetaEnum> estadoValido = new ArrayList<EstadoTarjetaEnum>(); 
	        estadoValido.add(EstadoTarjetaEnum.ACTIVA);
	        
	        //valida si la tarjeta fue encontrada y si esta misma tiene como estado ACTIVA
			if(tarjetaAfiliado != null && estadoValido.contains(tarjetaAfiliado.getEstado())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorTarjetaActiva.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TARJETA_ACTIVA);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTarjetaActiva.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TARJETA_ACTIVA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TARJETA_ACTIVA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch(NoResultException nre){
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorTarjetaActiva.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TARJETA_ACTIVA),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_TARJETA_ACTIVA,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		
		
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TARJETA_ACTIVA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}