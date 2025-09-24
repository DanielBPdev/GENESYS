package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
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
 * El afiliado principal debe tener estado "Activo" como Pensionado menos de 1,5 SM y 2%
 *  
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPensionadoMenos2Porciento extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPensionadoMenos2Porciento.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			
			// se listan los estados válidos para aprobar la validación
			List<ClasificacionEnum> estadosValidos = new ArrayList<ClasificacionEnum>();
			estadosValidos.add(ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
			
			// se consulta el afiliado con el tipo y número de documento
			SolicitudAfiliacionPersona solicitudAfiliacionPersona = (SolicitudAfiliacionPersona) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PENSIONADO_ACTIVO_POR_TIPO)
					.setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
					.setParameter(ConstantesValidaciones.CLASIFICACION_PARAM, estadosValidos)
					.getSingleResult();
			
			if (solicitudAfiliacionPersona != null)
			{
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorPensionadoMenos2Porciento.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PENSIONADO_MENOS_2_PORCIENTO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorPensionadoMenos2Porciento.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PENSIONADO_MENOS_2_PORCIENTO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_PENSIONADO_MENOS_2_PORCIENTO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}
		catch(NoResultException nre) {
			//validación fallida
			logger.debug("NO HABILITADA- Fin de método ValidadorPensionadoMenos2Porciento.execute");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PENSIONADO_MENOS_2_PORCIENTO),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_PENSIONADO_MENOS_2_PORCIENTO,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PENSIONADO_MENOS_2_PORCIENTO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}