package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * No aplica para pensionado fidelidad 25 años
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorNoPensionadoFidelidad25Anios extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorNoPensionadoFidelidad25Anios.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			
			// se listan los estados válidos para aprobar la validación
			List<ClasificacionEnum> estadosValidos = new ArrayList<ClasificacionEnum>();
			estadosValidos.add(ClasificacionEnum.FIDELIDAD_25_ANIOS);
			
			// se consulta el afiliado con el tipo y número de documento
			List<SolicitudAfiliacionPersona> solicitudAfiliacionPersona = (List<SolicitudAfiliacionPersona>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_AFI_NUMERO_TIPO_DOCUMENTO)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.CLASIFICACION_PARAM, estadosValidos)
					.getResultList();
			
			if (solicitudAfiliacionPersona == null || solicitudAfiliacionPersona.isEmpty()){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorNoPensionadoFidelidad25Anios.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NO_PENS_FIDELIDAD_25_ANIOS);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorNoPensionadoFidelidad25Anios.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_PENS_FIDELIDAD_25_ANIOS),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_NO_PENS_FIDELIDAD_25_ANIOS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_NO_PENS_FIDELIDAD_25_ANIOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}