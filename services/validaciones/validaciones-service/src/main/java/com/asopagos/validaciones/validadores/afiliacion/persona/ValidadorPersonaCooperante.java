package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada para el mismo emleador.
 * ver anexo de validacion en 1.2.1, validador 42 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaCooperante extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaCooperante.execute");
		try{
			
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdentifica = null;
                String numeroIdentificacion = null;
                String tipoIdentificaEmpleador = null;
                String numeroIdentificacionEmpleador = null;
                TipoIdentificacionEnum tipoIdentificacionEmpleador = null;
			    // Se verifica si la validacion aplica por una novedad de beneficiario
			    // Se obtienen los datos de identificacion segun a quien aplica la novedad
			    if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!= null && 
	                    datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM)!= null){
			        tipoIdentifica = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
			        numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			    } else {
                    tipoIdentifica = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                    numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                    tipoIdentificaEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
                    numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
                    tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(tipoIdentificaEmpleador);
                }
			    
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentifica);
				
				if (tipoIdentificacion != null){				    
                    if (tipoIdentificacionEmpleador != null && numeroIdentificacionEmpleador != null) {
                        List<ClasificacionEnum> listClasificaciones = entityManager
                                .createNamedQuery(NamedQueriesConstants.BUSCAR_ULTIMA_CLASIFICACION_EMPLEADOR)
                                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionEmpleador)
                                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionEmpleador)
                                .getResultList();
                        if(listClasificaciones != null && !listClasificaciones.isEmpty()){
                            if(!listClasificaciones.iterator().next().equals(ClasificacionEnum.COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO)){
                                logger.debug("Aprobado");
                                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_COOPERANTE);
                            }
                        }
                    }
					boolean existe = false;
					String numDocEmpleador = tipoIdentificaEmpleador+numeroIdentificacionEmpleador;
					
					// Se ajusta la consulta para que busque afiliaciones en otras cooperativas descartando el empleador actual
					List<RolAfiliado> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_CLASIFICACION_EMPLEADOR)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
							.setParameter(ConstantesValidaciones.CLASIFICACION_PARAM,ClasificacionEnum.COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO)
							.setParameter("numDocEmpleador", numDocEmpleador)
							.getResultList();
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<RolAfiliado> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_NUMERO_CLASIFICACION_EMPLEADOR)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)
								.setParameter(ConstantesValidaciones.CLASIFICACION_PARAM,ClasificacionEnum.COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO)
								.setParameter("numDocEmpleador", numDocEmpleador)
								.getResultList();
						if(personasConNumero != null && !personasConNumero.isEmpty()){
							existe = true;
						}
					}else{
						existe = true;
					}
					if(existe){
						logger.debug("No aprobada- Existe persona como cooperante");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_COOPERANTE),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_COOPERANTE,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);					
					}
					
				}else{
					logger.debug("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_COOPERANTE);
		}catch(Exception e){
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada",e);
			return crearMensajeNoEvaluado();
		}
	}
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  ValidacionDTO crearMensajeNoEvaluado(){
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_COOPERANTE),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_COOPERANTE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
	

}
