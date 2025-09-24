package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 *  El estado de la persona solicitante con respecto a todas sus afiliaciones 
 *  como trabajador dependiente debe ser "Inactivo".
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoTrabajadorIndependienteInactivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoTrabajadorIndependienteInactivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se crea una lista con el tipo de afiliado con el cual realizar el filtro
	        List<TipoAfiliadoEnum> tiposValidos = new ArrayList<TipoAfiliadoEnum>();
	        tiposValidos.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_ESTADO_AFILIACIONES_AFILIADO_POR_TIPO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
	        
	        //se verifica que la cosulta no haya retornado un valor null
	        if(rolAfiliado == null || rolAfiliado.isEmpty())
	        {
	        	//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoTrabajadorIndependienteInactivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TRABAJADOR_INDEPENDIENTE_INACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TRABAJADOR_INDEPENDIENTE_INACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
	        }
	        
	        //se recorre el arreglo retornado en la consulta 
	        for (RolAfiliado rolAfiliado2 : rolAfiliado) 
	        {	
	        	//se verifica que el estado del afiliado, para todas sus afiliaciones 
	        	//de tipo TRABAJADOR_DEPENDIENTE tengan estado inactivo.
	        	if (tiposValidos.contains(rolAfiliado2.getTipoAfiliado()) && !rolAfiliado2.getEstadoAfiliado().equals(EstadoAfiliadoEnum.INACTIVO)) {
	        		//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoTrabajadorIndependienteInactivo.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TRABAJADOR_INDEPENDIENTE_INACTIVO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_TRABAJADOR_INDEPENDIENTE_INACTIVO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}
	        
			//de llegar a este punto la validación será exitosa
			logger.debug("HABILITADA- Fin de método ValidadorAfiliadoTrabajadorIndependienteInactivo.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADOR_INDEPENDIENTE_INACTIVO);	
			
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_TRABAJADOR_INDEPENDIENTE_INACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}