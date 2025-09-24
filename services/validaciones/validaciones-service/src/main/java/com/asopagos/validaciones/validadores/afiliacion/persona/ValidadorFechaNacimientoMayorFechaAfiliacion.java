package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.converter.ObjetoValidacionUtils;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.transversal.personas.IValidable;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la logica para verificar cuando la fecha de nacimiento
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorFechaNacimientoMayorFechaAfiliacion extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorFechaNacimientoMayorFechaAfiliacion");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {

				IValidable usuario = ObjetoValidacionUtils.toObjetoValidacion(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
				String tipoId = null;
				TipoIdentificacionEnum  tipoIdentificacion = null;
				String numeroIdentificacion = null;
				String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);
				String tipoBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM);
				
				usuario = ObjetoValidacionUtils.toObjetoValidacion(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
				if(usuario instanceof TipoAfiliadoEnum){
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM) : datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				}else {
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) : datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				}
				tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				if(usuario instanceof TipoAfiliadoEnum){
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM) : datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				}else {
				numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) : datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				}

				if (tipoIdentificacion != null && !tipoIdentificacion.equals("") && numeroIdentificacion != null
						&& !numeroIdentificacion.equals("") && fechaNacimiento != null && !fechaNacimiento.equals("") ) {
					ValidacionDTO validacion = null;
					/*se verifica si se trata de una afiliado o un beneficiario*/
					if(usuario instanceof TipoAfiliadoEnum){
						validacion = validarAfiliado(numeroIdentificacion, tipoIdentificacion, fechaNacimiento);
					}else if(tipoBeneficiario != null){
						validacion =  validarBeneficiario(numeroIdentificacion, tipoIdentificacion, fechaNacimiento);
					}
					if(validacion!=null){
						return validacion;
					}
					
				} else {
					/* mensaje no evaluado porque falta informacion */
					logger.info("No evaludao-Falta Informacion");
					return crearMensajeNoEvaluado();
				}
			} else {
				/* mensaje no evaluado porque faltan datos */
				logger.info("No evaludao-Faltan datos");
				return crearMensajeNoEvaluado();
			}
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION);
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			e.printStackTrace();
			return crearMensajeNoEvaluado();
		}
	}
	
	private ValidacionDTO validarBeneficiario(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,String fechaNacimiento){
		logger.info("Inicio de método validarBeneficiario");
		Beneficiario beneficiarioEncontrado = null;
		List<Beneficiario> beneficiarioTipoNumero = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
		if (!beneficiarioTipoNumero.isEmpty()) {
			beneficiarioEncontrado = beneficiarioTipoNumero.get(0);
		} else {
			List<Beneficiario> beneficiarioNumero = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_DOCUMENTO)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
			if (!beneficiarioNumero.isEmpty()) {
				beneficiarioEncontrado = beneficiarioNumero.get(0);
			}
		}
		/*si se encuentra afiliado se compara el valor de la fecha de afiliacion con la fecha de nacimiento*/
		if (beneficiarioEncontrado != null) {
			if (beneficiarioEncontrado.getFechaAfiliacion() != null) {
				Date fechaNacimientoDate = new Date(new Long(fechaNacimiento));

				if (beneficiarioEncontrado.getFechaAfiliacion().getTime() < fechaNacimientoDate.getTime()) {
					return crearValidacion(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_POSTERIOR_FECHA_AFILIACION,
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}else{
				return crearMensajeNoEvaluado();
			}
		}
		logger.info("Fin de método validarBeneficiario");
		return null;
	}
	
	private ValidacionDTO validarAfiliado(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion,String fechaNacimiento){
		logger.info("Inicio de método validarAfiliado");
		RolAfiliado rolAfiliadoEncontrado = null;
		List<RolAfiliado> rolAfiliadoTipoIdentificacion = entityManager
				.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
		if (!rolAfiliadoTipoIdentificacion.isEmpty()) {
			rolAfiliadoEncontrado = rolAfiliadoTipoIdentificacion.get(0);
		} else {
			List<RolAfiliado> rolAfiliadoIdentificacion = entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_NUMERO_IDENTIFICACION)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getResultList();
			if (!rolAfiliadoIdentificacion.isEmpty()) {
				rolAfiliadoEncontrado = rolAfiliadoIdentificacion.get(0);
			}
		}
		/*si se encuentra afiliado se compara el valor de la fecha de afiliacion con la fecha de nacimiento*/
		if (rolAfiliadoEncontrado != null) {
			if(rolAfiliadoEncontrado.getFechaAfiliacion()!= null){
				Date fechaNacimientoDate = new Date(new Long(fechaNacimiento));
				if (rolAfiliadoEncontrado.getFechaAfiliacion().getTime() < fechaNacimientoDate.getTime()) {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_POSTERIOR_FECHA_AFILIACION),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}else{
				crearMensajeNoEvaluado();
			}
		}
		logger.info("Fin de método validarAfiliado");
		return null;
	}
	
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_FECHA_NACIMIENTO_POSTERIOR_FECHA_AFILIACION),
				ResultadoValidacionEnum.NO_EVALUADA,
				ValidacionCoreEnum.VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
