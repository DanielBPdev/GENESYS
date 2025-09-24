package com.asopagos.validaciones.fovis.validadores.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorAfiliadoPrincipalBeneficiarioMadre extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorAfiliadoPrincipalBeneficiarioMadre.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				TipoIdentificacionEnum tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoIdA);
				String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

				String tipoIdB = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoIdB);
				String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				Long idBeneficiario=null;
				logger.info("ValidadorAfiliadoPrincipalBeneficiarioMadre numeroIdentificacionAfiliado: "+numeroIdentificacionAfiliado);
				logger.info("ValidadorAfiliadoPrincipalBeneficiarioMadre tipoIdentificacionAfiliado: "+tipoIdentificacionAfiliado);
				
				if (datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO) != null) {
					idBeneficiario = new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
				}
				logger.info("ValidadorAfiliadoPrincipalBeneficiarioMadre idBeneficiario: "+idBeneficiario);
				if (tipoIdentificacionAfiliado != null && !tipoIdentificacionAfiliado.equals("")
						&& numeroIdentificacionAfiliado != null && !numeroIdentificacionAfiliado.equals("")) {
					List<ClasificacionEnum> tipoBeneficiarioList = new ArrayList<ClasificacionEnum>();
					tipoBeneficiarioList.add(ClasificacionEnum.MADRE);
					List<EstadoAfiliadoEnum> estadoList = new ArrayList<EstadoAfiliadoEnum>();
					estadoList.add(EstadoAfiliadoEnum.ACTIVO);
					estadoList.add(EstadoAfiliadoEnum.INACTIVO);
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
					if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
						if (personasConNumero != null && !personasConNumero.isEmpty()) {
							existe = true;
							if (personasConNumero.iterator().next().getIdBeneficiario().equals(idBeneficiario)) {
								existe = false;
							}
						}
                    } else {
                        existe = true;
                        if (personasConTipoYNumero.iterator().next().getIdBeneficiario().equals(idBeneficiario)) {
                            existe = false;
                        } else if (numeroIdentificacionBeneficiario != null 
                                && tipoIdentificacionBeneficiario != null
                                && personasConTipoYNumero.iterator().next().getPersona().getNumeroIdentificacion().equals(numeroIdentificacionBeneficiario)
                                && personasConTipoYNumero.iterator().next().getPersona().getTipoIdentificacion().equals(tipoIdentificacionBeneficiario)) {
                            existe = false;
                        }
                    }
					if(existe){
						logger.info("No aprobada- Existe persona beneficiario tipo madre activo o inactivo");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_MADRE_REGISTRADO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_MADRE,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}
				}else{
					logger.info("NO EVALUADO - No hay parametros");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.info("NO EVALUADO- no hay valores en el map");
				return crearMensajeNoEvaluado();
			}
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_MADRE);
		} catch (Exception e) {
			logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
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
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIO_MADRE_REGISTRADO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AFILIADO_BENEFICIARIO_MADRE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
}