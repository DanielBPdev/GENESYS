package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Asociado al trabajador, el valor del campo 
 * "Medio de pago asignado" debe tener el valor "tarjeta"
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTarjetaMedioDePago extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTarjetaMedioDePago.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        List<TipoAfiliadoEnum> afiliadoTipos = new ArrayList<TipoAfiliadoEnum>();
	        afiliadoTipos.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
	        afiliadoTipos.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
	        
	        //TODO Modificar Novedad Medio de Pago
	        String medioDePago = "";
	        //se consulta el afiliado con el tipo y número de documento
//	        String medioDePago = (String) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_MEDIO_DE_PAGO_AFILIADO)
//					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
//					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
//					.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, afiliadoTipos)
//					.getSingleResult();
	        
	        //se crea una variable con el valor esperado para la persona en el medio de pago
	        String medioDePagoValido = ConstantesValidaciones.TARJETA;
	        
	        //se realiza la validación
			if(medioDePago != null  && medioDePagoValido.equals(medioDePago)){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorTarjetaMedioDePago.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TARJETA_MEDIO_DE_PAGO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorTarjetaMedioDePago.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TARJETA_MEDIO_DE_PAGO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TARJETA_MEDIO_DE_PAGO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}
		catch (NoResultException nre) {
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorTarjetaMedioDePago.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TARJETA_MEDIO_DE_PAGO),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_TARJETA_MEDIO_DE_PAGO,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TARJETA_MEDIO_DE_PAGO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}