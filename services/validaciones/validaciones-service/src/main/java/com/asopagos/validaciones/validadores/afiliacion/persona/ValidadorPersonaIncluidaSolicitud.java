package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona esta afiliada
 * para el mismo emleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaIncluidaSolicitud extends ValidadorAbstract {

	/**
	 * Se verifica mediante tipo y número de documento de identificación, si la
	 * persona ya está incluida en la misma solicitud.
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try {
			logger.debug("Inicio ValidadorPersonaIncluidaSolicitud");
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				/*datos afiliado si viene por novedades, datos del beneficiario si viene por afiliaciones*/
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				/*datos beneficiario si es novedad*/
				String tipoIdB = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
			    String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			    /*datos afiliado si es por afiliaciones*/
			    String tipoIdA = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
				String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				Long idBeneficiario=null;
				if (datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO) != null) {
					idBeneficiario = new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
				}
				TipoIdentificacionEnum tipoIdentificacionBeneficiario = null;
				if(tipoIdB!=null && numeroIdentificacionBeneficiario!=null){
					tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoIdB);
				}else if(tipoIdA !=null && numeroIdentificacionAfiliado!=null){
					tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoId);
					numeroIdentificacionBeneficiario = numeroIdentificacion;
					tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdA);
					numeroIdentificacion = numeroIdentificacionAfiliado;
				}
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("") && !numeroIdentificacion.equals("")) {
			        //se consulta el beneficiario con el tipo y número de documento, el tipo de beneficiario y su estado (activo o inactivo)
					try{
			        Beneficiario beneficiario = (Beneficiario) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_Y_NUMERO_BENEFICIARIO_Y_AFILIADO)
		    		.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
		    		.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.getSingleResult();
			        
			        if(beneficiario.getIdBeneficiario().equals(idBeneficiario)){
			        	return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INCLUIDA_SOLICITUD);
			        }
					/*Se valida si existe el beneficiario*/
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INCLUIDA_SOLICITUD),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_PERSONA_INCLUIDA_SOLICITUD,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
					}catch(NoResultException nre){
						if (numeroIdentificacion.equals(numeroIdentificacionBeneficiario)
								&& tipoIdentificacion.equals(tipoIdentificacionBeneficiario)){
							/*Se valida de que el beneficiario no sea el mismo afiliado principal*/
							return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INCLUIDA_SOLICITUD),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_PERSONA_INCLUIDA_SOLICITUD,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						}
						return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INCLUIDA_SOLICITUD);
					}
				}else{
					/* mensaje no evaluado porque faltan datos */
					logger.debug("No evaludao- Faltan datos");
					return crearMensajeNoEvaluado();
				}
			}else {
				/* mensaje no evaluado porque faltan datos */
				logger.debug("No evaludao- Faltan datos");
				return crearMensajeNoEvaluado();
			} 
		
		} catch (Exception e) {
			/* No evaluado ocurrió alguna excepción */
			logger.debug("No evaludao- Ocurrio alguna excepcion");
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
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_INCLUIDA_SOLICITUD),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_INCLUIDA_SOLICITUD,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

}
