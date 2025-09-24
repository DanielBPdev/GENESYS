package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona tiene 19 años
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorPersonaTiene19 extends ValidadorAbstract {

	/**
	 * Constante que estable la edad a verificar la cual es 19
	 */
	private static final int EDAD_ESTABLECIDA_19 = 19;
	/**
	 * Constante que estable la edad a verificar la cual es 23
	 */
	private static final int EDAD_ESTABLECIDA_23 = 23;

	@Override
	/**
	 * Metodo encargado de verificar Verifica si la persona tiene 19 años
	 * cumplidos mediante cálculo, usando para ello la fecha de nacimiento y
	 * calculando la edad con respecto a la fecha actual del sistema. Caso 1: Se
	 * entiende que la persona tiene 19 años cumplidos cuando el cálculo da como
	 * resultado el valor 19,00000 o alguno superior. Caso 2: Persona registrada
	 * con condición de invalidez Caso 3: Si tiene entre 19,0000 y 22,9999 años:
	 * -Tiene certificado de escolaridad -Diligenciado el campo
	 * "Fecha de vencimiento de certificado escolar" con una fecha vigente
	 * -Diligenciado el campo "Fecha de recepción certificado escolar" con un
	 * valor válido. -El campo
	 * "Beneficiario tipo hijo (entre 19 y 22 años) estudiante de programa en Institución para el trabajo y desarrollo humano"
	 * tiene valor TRUE
	 * 
	 */
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaTiene19");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
			    String tipoIdBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdBeneficiario!=null?tipoIdBeneficiario:tipoId);
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
				String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String numeroIdentificacion = numeroIdentificacionBeneficiario!=null? numeroIdentificacionBeneficiario:numeroIdentificacionAfiliado;
				String fechaNacimientoLong = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				String fechaVencimientoLong = datosValidacion.get(ConstantesValidaciones.FECHA_VENCIMIENTO_CERTIFICADO_PARAM);
				String fechaRecepcionLong = datosValidacion.get(ConstantesValidaciones.FECHA_RECEPCION_CERTIFICADO_PARAM);
				Boolean trabajoDesarrolloHumano = null;
				CondicionInvalidez conInvalidez=null;
				
				if(datosValidacion.get(ConstantesValidaciones.TRABAJO_DESARROLLO_HUMANO_PARAM)!=null || datosValidacion.get(ConstantesValidaciones.TRABAJO_DESARROLLO_HUMANO_PARAM2)!=null){
    				trabajoDesarrolloHumano = new Boolean((datosValidacion.get(ConstantesValidaciones.TRABAJO_DESARROLLO_HUMANO_PARAM) != null) 
						? datosValidacion.get(ConstantesValidaciones.TRABAJO_DESARROLLO_HUMANO_PARAM) 
    					: datosValidacion.get(ConstantesValidaciones.TRABAJO_DESARROLLO_HUMANO_PARAM2));
				}
				Boolean condicionInvalidez = null;
				if(datosValidacion.get(ConstantesValidaciones.CONDICION_INVALIDEZ_PARAM)!=null){
					condicionInvalidez = Boolean.parseBoolean(datosValidacion.get(ConstantesValidaciones.CONDICION_INVALIDEZ_PARAM));
				}
				Boolean certificadoEscolar = null;
				if(datosValidacion.get(ConstantesValidaciones.CERTIFICADO_ESCOLAR_PARAM)!=null || datosValidacion.get(ConstantesValidaciones.CERTIFICADO_ESCOLAR_PARAM2)!=null){
					certificadoEscolar = Boolean.parseBoolean((datosValidacion.get(ConstantesValidaciones.CERTIFICADO_ESCOLAR_PARAM) != null)
					? datosValidacion.get(ConstantesValidaciones.CERTIFICADO_ESCOLAR_PARAM)
					: datosValidacion.get(ConstantesValidaciones.CERTIFICADO_ESCOLAR_PARAM2)); 
				}
				logger.info("**__** condicionInvalidez: "+condicionInvalidez+" certificadoEscolar: "
				+certificadoEscolar+" fechaNacimientoLong: "+fechaNacimientoLong+" trabajoDesarrolloHumano: "+trabajoDesarrolloHumano);
				/* Se verifica si la fecha de nacimiento es null */
				if (fechaNacimientoLong == null || fechaNacimientoLong.equals("")) {
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19);
				} else {
					String fechaNacimiento = ValidacionPersonaUtils.convertirFecha(fechaNacimientoLong);
					if (tipoIdentificacion != null && !tipoIdentificacion.equals("")&& !numeroIdentificacion.equals("")) {
						Beneficiario beneficiario = null;
						List<Beneficiario> beneficiarioTipoIdentificacion = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.getResultList();
						if (beneficiarioTipoIdentificacion != null && !beneficiarioTipoIdentificacion.isEmpty()) {
							beneficiario = beneficiarioTipoIdentificacion.get(0);
						} else {
							List<Beneficiario> beneficiarioIdentificacion = entityManager
									.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_DOCUMENTO)
									.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
									.getResultList();
							if (beneficiarioIdentificacion != null && !beneficiarioIdentificacion.isEmpty()) {
								beneficiario = beneficiarioIdentificacion.get(0);
							}
						}
						
						// Se calcula la edad de la persona en años
						int edadPersona = ValidacionPersonaUtils.calcularEdadAnos(fechaNacimiento);
						
						if (edadPersona >= EDAD_ESTABLECIDA_19 ){
                            try {
                                conInvalidez = entityManager
                                        .createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICIONINVALIDEZ_ID_NUMERO_TIPO_IDENTIFICACION,
                                                CondicionInvalidez.class)
                                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getSingleResult();
                            } catch (NoResultException e) {
                                conInvalidez = null;
                            }
						    
							if(condicionInvalidez==null && conInvalidez!=null){
								condicionInvalidez = conInvalidez.getCondicionInvalidez();
							}
							if(condicionInvalidez==null){
								condicionInvalidez = false;
							}
							if(condicionInvalidez) {
								/*si es mayor de 19 y tiene condicion de invalidez: EXITO*/
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_PERSONA_CON_19_CONDICION_INVALIDEZ),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
							}	
						String cajaAceptaCertificados = (String)CacheManager.getParametro(ParametrosSistemaConstants.ACEPTACION_HIJOS_19_22_CON_ESTUDIO_CERTIFICADO);
						if (edadPersona >= EDAD_ESTABLECIDA_19 && edadPersona < EDAD_ESTABLECIDA_23
									&& cajaAceptaCertificados.equals(DecisionSiNoEnum.SI.name())) {
								/*
								 * Se verifica si el beneficiario es mayor a 19
								 * años y menor a 23 años
								 */
								 	logger.info("*__** ValidadorPersonaTiene19 numeroIdentificacion: "+numeroIdentificacion+" tipoIdentificacion: "+tipoIdentificacion);
								Date fechaActual = new Date();
								BeneficiarioDetalle benDetalle = new BeneficiarioDetalle();
								List<CertificadoEscolarBeneficiario> listCertificados = new ArrayList<>();
	                            try {
	                                benDetalle = entityManager 
	                                        .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIODETALLE_ID_NUMERO_TIPO_IDENTIFICACION,
	                                                BeneficiarioDetalle.class)
	                                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
	                                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getSingleResult();
                                    listCertificados = entityManager
                                            .createNamedQuery(
                                                    NamedQueriesConstants.CONSULTA_CERTIFICADOS_ESCOLARES_POR_TIPO_NUMERO_IDENTIFICACION,
                                                    CertificadoEscolarBeneficiario.class)
                                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getResultList();
	                            } catch (NoResultException e) {
	                                benDetalle = null;
	                                listCertificados = null;
	                            }
								if (trabajoDesarrolloHumano == null) {
									trabajoDesarrolloHumano = beneficiario != null
											? beneficiario.getEstudianteTrabajoDesarrolloHumano() : null;
								}
								if (trabajoDesarrolloHumano == null) {
									trabajoDesarrolloHumano = false;
								}
								if (certificadoEscolar == null) {
									certificadoEscolar = benDetalle != null ? benDetalle.getCertificadoEscolaridad()
											: null;
								}
								if(trabajoDesarrolloHumano) {
									return crearValidacion(
											myResources.getString(ConstantesValidaciones.KEY_PERSONA_CON_19_CERTIFICADO_ESTUDIO),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19,
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
								}
								if (certificadoEscolar == null) {
									certificadoEscolar = false;
								}

								/*
								 * Se verifica si tiene certificado escolar, con
								 * fecha recepcion valida, fecha vencimiento
								 * vigente y sea un estudianet para el trabajo
								 * del desarollo humano siempre y cuando la caja
								 * lo permita
								 */
								 	logger.info("*__** ValidadorPersonaTiene19 KEY_PERSONA_CON_19_CERTIFICADO_ESTUDIO certificadoEscolar: "+certificadoEscolar+" trabajoDesarrolloHumano: "
									+trabajoDesarrolloHumano +"validacion metodo: "+validateVigenciaCertificado(listCertificados, fechaRecepcionLong, fechaVencimientoLong, fechaActual));
									
								if (certificadoEscolar && trabajoDesarrolloHumano && validateVigenciaCertificado(listCertificados, fechaRecepcionLong, fechaVencimientoLong, fechaActual)) {
									
									/*
									 * Tiene entre 19 y 23 años y cumple
									 * condiciones de certificado EXITOSA
									 */
									return crearValidacion(
											myResources.getString(
													ConstantesValidaciones.KEY_PERSONA_CON_19_CERTIFICADO_ESTUDIO),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19,
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
								}
							}
						
							/*persona mayor de 19 y sin invalidez NO APROBADA*/
							return crearValidacion(
											myResources.getString(
													ConstantesValidaciones.KEY_PERSONA_CON_19),
											ResultadoValidacionEnum.NO_APROBADA,
											ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19,
											TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
								
							}
				
							return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19);
					} 
				}
			}
			/* mensaje no evaluado porque faltan datos */
			logger.debug("No evaludao- Faltan datos");
			return crearMensajeNoEvaluado();
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			logger.debug("No evaludao- Ocurrio alguna excepcion");
			return crearMensajeNoEvaluado();
		}
	}

	private Boolean validateVigenciaCertificado(List<CertificadoEscolarBeneficiario> listCertificados, String fechaRecepcionLong, String fechaVencimientoLong, Date fechaActual){
	    Boolean result = Boolean.FALSE; 
	    Date fechaRecepcion = fechaRecepcionLong != null ? new Date(new Long(fechaRecepcionLong)) : null;
        Date fechaVencimiento = fechaVencimientoLong != null ? new Date(new Long(fechaVencimientoLong)) : null;
        
			logger.info("*__** ValidadorPersonaTiene19  KEY_PERSONA_CON_19_CERTIFICADO_ESTUDIO fechaActual.getTime(): "+
			fechaActual.getTime()+"fechaVencimiento "+fechaVencimiento+" fechaVencimiento"+fechaVencimiento);
        if ((fechaVencimiento != null
                        && fechaVencimiento.getTime() >= fechaActual.getTime())
                && (fechaRecepcion != null && fechaRecepcion.getTime() <= fechaActual.getTime())) {
            return Boolean.TRUE;
        }
        
        if (listCertificados != null && !listCertificados.isEmpty()) {
            for (CertificadoEscolarBeneficiario certificadoEscolarBeneficiario : listCertificados) {
                if (certificadoEscolarBeneficiario.getFechaVencimientoCertificadoEscolar().getTime() >= fechaActual.getTime()
                        && certificadoEscolarBeneficiario.getFechaRecepcionCertificadoEscolar().getTime() <= fechaActual.getTime()) {
                    result = Boolean.TRUE;
                    break;
                }
            }
        }
        return result;
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_CON_19),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_19,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
