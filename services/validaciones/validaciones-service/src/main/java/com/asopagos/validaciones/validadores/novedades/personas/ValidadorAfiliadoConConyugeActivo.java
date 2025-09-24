package com.asopagos.validaciones.validadores.novedades.personas;

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
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Afiliado principal ya tiene registrado un beneficiario tipo cónyuge (activo) (estado válido)
 * 
 *
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoConConyugeActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoConConyugeActivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			Boolean isIngresoRetiro = null;
			String ingresoRetiroStr = datosValidacion.get(ConstantesValidaciones.IS_INGRESO_RETIRO);
			if (ingresoRetiroStr != null && !ingresoRetiroStr.trim().isEmpty()) {
				isIngresoRetiro = Boolean.parseBoolean(ingresoRetiroStr);
			}

			logger.info("ValidadorAfiliadoConConyugeActivo isIngresoRetiro " +isIngresoRetiro);

			logger.info("datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) " +datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) );
			TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
			String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
			Boolean existe =false;


			//se listan los tipos válidos de beneficiario para aprobar la validación
			List<ClasificacionEnum> estadosValidos = new ArrayList<ClasificacionEnum>();
			estadosValidos.add(ClasificacionEnum.CONYUGE);

			//se listan los estados válidos de beneficiario para aprobar la validación
			List<EstadoAfiliadoEnum> estadoBeneficiarioAfiliado = new ArrayList<EstadoAfiliadoEnum>();
			estadoBeneficiarioAfiliado.add(EstadoAfiliadoEnum.ACTIVO);


			//se consulta(n) el(los) beneficiarios tipo CONYUGE con estado ACTIVO que tenga el afiliado principal
			List<Beneficiario> beneficiarios = (List<Beneficiario>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CANTIDAD_BENEFICIARIOS_POR_TIPO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, estadosValidos)
					.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoBeneficiarioAfiliado)
					.getResultList();

			if(isIngresoRetiro != null && isIngresoRetiro){
				for(Beneficiario beneficiario : beneficiarios){
					logger.info("ValidadorAfiliadoConConyugeActivo numeroIdentificacionBeneficiario " +numeroIdentificacionBeneficiario);
					logger.info("ValidadorAfiliadoConConyugeActivo tipoIdentificacionBeneficiario " +numeroIdentificacionBeneficiario);
					logger.info("ValidadorAfiliadoConConyugeActivo beneficiario getNumeroIdentificacion" +beneficiario.getPersona().getNumeroIdentificacion());
					logger.info("ValidadorAfiliadoConConyugeActivo beneficiario getTipoIdentificacion" +beneficiario.getPersona().getTipoIdentificacion());
					if(!beneficiario.getPersona().getNumeroIdentificacion().equals(numeroIdentificacionBeneficiario) && !beneficiario.getPersona().getTipoIdentificacion().equals(tipoIdentificacionBeneficiario)){
						existe = true;
					}
				}
				if(!existe){
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO);
				}
			}


	        //si la busqueda arroja resultados la validación es fallida
			if(beneficiarios != null && !beneficiarios.isEmpty()){
                //validación fallida
                logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoConConyugeActivo.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_CON_CONYUGE_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}else{
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorAfiliadoConConyugeActivo.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_CON_CONYUGE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
