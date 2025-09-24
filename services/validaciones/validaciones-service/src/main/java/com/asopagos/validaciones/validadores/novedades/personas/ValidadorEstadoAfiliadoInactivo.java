package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

 /**
 *
 * Validar estado de afiliación del afiliado igual a "Inactivo"
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorEstadoAfiliadoInactivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute");
		try{
                    Afiliado afiliado;
                    logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 1 " + datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                        logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 2 " + tipoIdentificacion.getDescripcion());
                        logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 3 " + datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se listan los estados válidos del afiliado
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList <EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.INACTIVO);
	        logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 4.1 " + tipoIdentificacion.name());

	        
	        // Se consulta el afiliado con el tipo y número de documento y los estados válidos para el mismo
                try{
                    afiliado= (Afiliado) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADOS_VALIDOS_AFILIADO_PARAM, estadosValidos)
					.getSingleResult();
                }catch(Exception e){
                    logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 4.2 ");
                     afiliado = null;
                }
	         logger.info("Inicio de método ValidadorEmpleadorDiferenteActivo.execute 5 ");
	        // Se valida la condición
			if(afiliado != null){
				logger.info("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorDiferenteActivo.execute 6");
				// Validación exitosa
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_INACTIVO);
			}else{
				logger.info("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorDiferenteActivo.execute 7");
				// Validación no aprobada
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_AFILIADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_INACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO_INACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
