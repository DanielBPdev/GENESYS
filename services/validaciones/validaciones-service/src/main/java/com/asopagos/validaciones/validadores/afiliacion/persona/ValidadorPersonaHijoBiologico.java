package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona es un beneficiario tipo hijo biologico. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaHijoBiologico extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaHijoBiologico.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String numeroIdentificacion = "";
				TipoIdentificacionEnum tipoIdentificacion = null;
				if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null &&
						datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
					String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
					tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
					logger.debug("Ingresa a Datos de BEneficiario");
				} else {
					String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
					tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
					logger.debug("No Ingresa a Datos de BEneficiario");
				}
				if (tipoIdentificacion != null && !numeroIdentificacion.equals("")){
					List<ClasificacionEnum> tipoBeneficiarioList = new ArrayList<ClasificacionEnum>();
					tipoBeneficiarioList.add(ClasificacionEnum.HIJO_BIOLOGICO);
					List<EstadoAfiliadoEnum> estadoList = new ArrayList<EstadoAfiliadoEnum>();
					estadoList.add(EstadoAfiliadoEnum.ACTIVO);
					estadoList.add(EstadoAfiliadoEnum.INACTIVO);
					boolean existe = false;
					List<Persona> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<Persona> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
						if(personasConNumero != null && !personasConNumero.isEmpty()){
							existe = true;
						}
					}else{
						existe = true;
					}
					if(existe){
						logger.debug("No aprobada- Existe persona beneficiaria tipo hijo biologico");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_BIOLOGICO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_BIOLOGICO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);	
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_BIOLOGICO );
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_BIOLOGICO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_BIOLOGICO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
