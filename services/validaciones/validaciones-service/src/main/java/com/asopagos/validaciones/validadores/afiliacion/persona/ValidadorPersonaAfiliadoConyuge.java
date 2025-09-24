package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ParametroConsultaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasFOVISUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada y tiene conyuge. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaAfiliadoConyuge extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaAfiliadoConyuge.execute");
		try{
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				
				TipoIdentificacionEnum tipoIdAfiliado=TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(
 						datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM));
 				String numIdAfiliado=datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				String idPostulacion = datosValidacion.get(ConstantesValidaciones.ID_POSTULACION);

 				if(numIdAfiliado.equals(numeroIdentificacion)) {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_AFILIADO_PRINCIPAL_CONYUGE),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_AFILIADO_CONYUGE_MISMO_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

				}else{
					// Se realiza la consulta por medio del utilitario
					List<ParametroConsultaDTO> listaParametros = new ArrayList<>();
					ParametroConsultaDTO dto = new ParametroConsultaDTO();
					dto.setEntityManager(entityManager);
					dto.setNumeroIdentificacion(numIdAfiliado);
					dto.setTipoIdentificacion(tipoIdAfiliado);
					listaParametros.add(dto);
					List<PersonaPostulacionDTO> listPostulaciones = PersonasFOVISUtils.consultarPostulacionVigente(listaParametros);

					if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
						if(this.verificarPostulacionDiferente(idPostulacion, listPostulaciones)){
							return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS);
						}
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_POSTULADO_FOVIS),
								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
					}
				}

				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")){
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_AFILIADO_TIPO_BENEFICIARIO_ESTADO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.CONYUGE)
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO,EstadoAfiliadoEnum.ACTIVO)
							.getResultList();
					
					//validar si este beneficiario es diferente al afiliado principal que viene en esta validacion
					if (personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) {
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(
										NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_AFILIADO_TIPO_BENEFICIARIO_ESTADO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.CONYUGE)
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
								.getResultList();
						if (personasConNumero != null && !personasConNumero.isEmpty()) {
							if (personasConNumero.iterator().next().getPersona().getNumeroIdentificacion()
									.equals(numIdAfiliado)
									&& personasConNumero.iterator().next().getPersona().getTipoIdentificacion()
											.equals(tipoIdAfiliado)) {
								existe = false;
							} else {
								existe = true;
							}
						}
					} else {
						if (personasConTipoYNumero.iterator().next().getPersona().getNumeroIdentificacion()
								.equals(numIdAfiliado)
								&& personasConTipoYNumero.iterator().next().getPersona().getTipoIdentificacion()
										.equals(tipoIdAfiliado)) {
							existe = false;
						} else {
							existe = true;
						}
					}
					if(existe){
						logger.debug("No aprobada- Existe persona afiliada con un beneficiario tipo conyuge");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_AFILIADO_PRINCIPAL),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_AFILIADO_CONYUGE,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);					
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
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_AFILIADO_CONYUGE);
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_AFILIADO_PRINCIPAL),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_AFILIADO_CONYUGE,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	private Boolean verificarPostulacionDiferente(String idPostulacion, List<PersonaPostulacionDTO> listPostulaciones) {
		Boolean rep = false;
		if (idPostulacion == null || idPostulacion.isEmpty()) {
			return Boolean.TRUE;
		}
		Long idPost = Long.parseLong(idPostulacion);
		for (PersonaPostulacionDTO postulacionFOVIS : listPostulaciones) {
			if ((postulacionFOVIS.getIdPostulacionFovis().equals(idPost)) || postulacionFOVIS.getIdPostulacionFovis() == null) {
				rep = Boolean.TRUE;
				break;
			} else {
				rep =  Boolean.FALSE;
			}
		}
		return rep;
	}

}
