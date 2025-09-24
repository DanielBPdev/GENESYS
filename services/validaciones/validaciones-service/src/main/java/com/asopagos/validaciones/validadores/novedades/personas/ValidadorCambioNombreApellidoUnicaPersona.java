package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Los valores a cambiar de nombres y apellidos no pueden 
 * corresponder a otra persona registrada en el sistema
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorCambioNombreApellidoUnicaPersona extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorCambioNombreApellidoUnicaPersona.execute");
		try{
            String primerNombre = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM);   
            String segundoNombre = datosValidacion.get(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM);   
            String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
            String segundoApellido = datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM);
	        
	        //se consulta el afiliado con el tipo y número de documento
	        BigInteger idPersona = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NOMBRE_Y_APELLIDO)
					.setParameter(ConstantesValidaciones.PRIMER_NOMBRE_PARAM, primerNombre)
					.setParameter(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM, segundoNombre)
					.setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellido)
					.setParameter(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM, segundoApellido)
					.getSingleResult();

	        //se realiza la validación
			if(idPersona != null){
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorCambioNombreApellidoUnicaPersona.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NOMBRE_APELLIDO_UNICA_PERSONA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_NOMBRE_APELLIDO_UNICA_PERSONA,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
			}else{
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorCambioNombreApellidoUnicaPersona.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NOMBRE_APELLIDO_UNICA_PERSONA);	
			}
		} 
		catch (NoResultException nre) {
			//validación exitosa
			logger.debug("HABILITADA- Fin de método ValidadorCambioNombreApellidoUnicaPersona.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NOMBRE_APELLIDO_UNICA_PERSONA);
		}
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_NOMBRE_APELLIDO_UNICA_PERSONA, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}