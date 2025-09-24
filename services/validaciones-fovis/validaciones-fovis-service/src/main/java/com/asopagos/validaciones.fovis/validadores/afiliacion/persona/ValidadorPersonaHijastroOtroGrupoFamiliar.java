package com.asopagos.validaciones.fovis.validadores.afiliacion.persona;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase que contiene la lógica para validar cuando la persona es un beneficiario tipo hijastro. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaHijastroOtroGrupoFamiliar extends ValidadorFovisAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaHijastroOtroGrupoFamiliar.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				//String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
				String tipoId = null;
				String numeroIdentificacion = null;
				if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM)!=null){
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

				}else{
					tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
					numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				}
				logger.info("tipo identificacion "+tipoId);
				logger.info("numero identificacion "+numeroIdentificacion);

				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
                Long idGrupoFamiliar = null;
                if(datosValidacion.get(ConstantesValidaciones.ID_GRUPOFAMILIAR)!=null){
                    idGrupoFamiliar = new Long(datosValidacion.get(ConstantesValidaciones.ID_GRUPOFAMILIAR));
                }
				if (tipoIdentificacion != null){
					List<ClasificacionEnum> tipoBeneficiarioList = new ArrayList<ClasificacionEnum>();
					tipoBeneficiarioList.add(ClasificacionEnum.HIJASTRO);
					List<EstadoAfiliadoEnum> estadoList = new ArrayList<EstadoAfiliadoEnum>();
					estadoList.add(EstadoAfiliadoEnum.ACTIVO);
					//estadoList.add(EstadoAfiliadoEnum.INACTIVO);
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
                    if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiarioList)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList).getResultList();
                        if(personasConNumero != null && !personasConNumero.isEmpty()){
						    Long idGrupoFamiliarBD = personasConNumero.get(0).getGrupoFamiliar()!= null ? personasConNumero.get(0).getGrupoFamiliar().getIdGrupoFamiliar(): null;
						    if(idGrupoFamiliar!=null && !idGrupoFamiliar.equals(idGrupoFamiliarBD)
						            || (idGrupoFamiliar == null && idGrupoFamiliarBD != null)){
						        existe = true;						        
						    }
						}
                    }
                    else {
                        if (personasConTipoYNumero != null && !personasConTipoYNumero.isEmpty()) {
                            Long idGrupoFamiliarBD = personasConTipoYNumero.get(0).getGrupoFamiliar() != null
                                    ? personasConTipoYNumero.get(0).getGrupoFamiliar().getIdGrupoFamiliar() : null;
                            if (idGrupoFamiliar!=null && !idGrupoFamiliar.equals(idGrupoFamiliarBD)
                                    || (idGrupoFamiliar == null && idGrupoFamiliarBD != null)) {
                                existe = true;
                            }
                        }
                    }
					if(existe){
						logger.debug("No aprobada- Existe persona beneficiaria tipo hijastro");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJASTRO_GRUPO_FAMILIAR),
								ResultadoValidacionEnum.NO_EVALUADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJASTRO_OTRO_GRUPO_FAMILIAR,
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJASTRO_OTRO_GRUPO_FAMILIAR);
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
            logger.info("Inicio de método Validador 17");
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJASTRO_GRUPO_FAMILIAR),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_HIJASTRO_OTRO_GRUPO_FAMILIAR,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
