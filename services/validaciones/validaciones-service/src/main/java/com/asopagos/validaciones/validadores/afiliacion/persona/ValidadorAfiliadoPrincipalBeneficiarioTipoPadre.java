package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorAfiliadoPrincipalBeneficiarioTipoPadre extends ValidadorAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.info("Inicio ValidadorAfiliadoPrincipalBeneficiarioTipoPadre");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = "";
				String numeroIdentificacionAfiliado = "";
				TipoIdentificacionEnum tipoIdentificacionAfiliado = null;
				if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM)!=null&&datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM)!=null) {
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
					tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				}else {
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
					tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				}
				logger.info("tipoIdentificacionAfiliado " +tipoIdentificacionAfiliado);
				logger.info("numeroIdentificacionAfiliado " +numeroIdentificacionAfiliado);
				if (tipoIdentificacionAfiliado != null && !tipoIdentificacionAfiliado.equals("")
						&& numeroIdentificacionAfiliado != null && !numeroIdentificacionAfiliado.equals("")) {
					boolean existe = false;

					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADO_MOTIVO_DESAFILIACION)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO_ACTIVO, ValidacionPersonaUtils.obtenerEstadoActivoBeneficiario())
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO_INACTIVO, ValidacionPersonaUtils.obtenerEstadoInactivoBeneficiario())
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionPadre())
							.setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, ValidacionPersonaUtils.obtenerMotivoDesafiliacionFallecimientoBeneficiario())
							.getResultList();
					logger.info("personasConTipoYNumero " +personasConTipoYNumero.size());
					if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADO_MOTIVO_DESAFILIACION)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO_ACTIVO, ValidacionPersonaUtils.obtenerEstadoActivoBeneficiario())
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO_INACTIVO, ValidacionPersonaUtils.obtenerEstadoInactivoBeneficiario())
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionPadre())
								.setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, ValidacionPersonaUtils.obtenerMotivoDesafiliacionFallecimientoBeneficiario())
								.getResultList();
						logger.info("personasConNumero " +personasConNumero.size());
						if (personasConNumero != null && !personasConNumero.isEmpty()) {
							existe = true;
						}
					}else{
						existe = true;
					}
					logger.info("existe " +existe);
					if (existe) {
						/*El afiliado principal ya tiene registrado al menos un beneficiario tipo padre/madre (activo)*/
						return crearValidacion(
								myResources.getString(
										ConstantesValidaciones.KEY_AFILIADO_PRINCIPAL_BENEFICIARIO_PADRES),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
				}else {
					/* Faltan datos para avaluar */
					return crearMensajeNoEvaluado();
				}
			} else {
				/* mensaje no evaluado porque no llegaron datos */
				return crearMensajeNoEvaluado();
			}
			/* exitoso */
			logger.info("crea mensaje exitoso");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE);
		} catch (Exception e) {
			logger.info("ingresa al catch??");
			/* No evaluado ocurrió alguna excepción */
			return crearMensajeNoEvaluado();
		}
	}

	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_AFILIADO_PRINCIPAL_BENEFICIARIO_PADRES) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_AFILIADO_PRINCIPAL_BENEFICIARIO_PADRES),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	/**
	 * Método que obtiene las clasificaciones por sujeto tramite.
	 * 
	 * @return listado de las clasificaciones.
	 */
	public List obtenerClasificacionPadre() {
		List<ClasificacionEnum> clasificacionList = new ArrayList();
		clasificacionList.add(ClasificacionEnum.PADRE);
		clasificacionList.add(ClasificacionEnum.MADRE);
		return clasificacionList;
	}
}
