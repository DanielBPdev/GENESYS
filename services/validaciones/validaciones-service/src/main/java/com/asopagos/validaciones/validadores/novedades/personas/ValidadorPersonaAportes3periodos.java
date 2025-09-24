package com.asopagos.validaciones.validadores.novedades.personas;


import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;



/**
 * 
 * @author abernal
 * Valida si la persona tiene asociados pagos de aportes para al menos 
 * uno de los últimos 3 periodos más recientes
 */
public class ValidadorPersonaAportes3periodos extends ValidadorAbstract{

	// Atributo donde se almacenaran los aportes del usuario a buscar en caso de que tenga
	private List<String> periodosAporteAfiliado;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
	    
	    
		logger.info("Inicio de método ValidadorPersonaAportes3periodos.execute");
		try{
		    
			Boolean exito = null;

			if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado- No esta lleno el mapa");
                return null;
            }

            
			// Se verifica si se envio la información de beneficiario
			 
            if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {

				setPeriodosAporteAfiliado(TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)), datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM));

			}
			else if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) != null) {

				setPeriodosAporteAfiliado(TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM)), datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM));

			}
			else if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM) != null) {
						
				setPeriodosAporteAfiliado(TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)), datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM));
				
				
            } else {
				logger.error("No evaluado - Ocurrió alguna excepción");
				return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
				ValidacionCoreEnum.VALIDACION_PERSONA_APORTES_3_PERIODOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
			}

			if (periodosAporteAfiliado != null && !periodosAporteAfiliado.isEmpty()) {
				/*Se restan 3 meses a la fecha actual*/
				Calendar fechaActual = Calendar.getInstance();
				fechaActual.setTime(new Date());
				fechaActual.add(Calendar.MONTH, -3);
				for (String periodoAporte : periodosAporteAfiliado) {
					Calendar fechaAporteCal = Calendar.getInstance();
					/*fechaAporteCal.setTime(simpleDateFormat.parse(periodoAporte));*/
					Date date1 = new SimpleDateFormat("yyyy-MM").parse(periodoAporte);
					fechaAporteCal.setTime(date1);
					
					if(fechaActual.compareTo(fechaAporteCal) > 0) {
						exito = Boolean.TRUE;
						
						
					} else{
						exito = Boolean.FALSE;
						break;
					}
				}
				if(exito){
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorPersonaAportes3periodos.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_APORTES_3_PERIODOS);	
				}else{
					
					//validación fallida
					logger.info("NO HABILITADA- Fin de método ValidadorPersonaAportes3periodos.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_APORTES_3_PERIODOS),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_APORTES_3_PERIODOS,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
				}
			} else{
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorPersonaAportes3periodos.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_APORTES_3_PERIODOS);
			}

	        
		} 
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PERSONA_APORTES_3_PERIODOS, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
		}
	}

	// método para settear los datos de aportes del usuario a buscar
	private void setPeriodosAporteAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion){

		this.periodosAporteAfiliado = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERIODO_APORTES_AFILIADO)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
	}
	
}

