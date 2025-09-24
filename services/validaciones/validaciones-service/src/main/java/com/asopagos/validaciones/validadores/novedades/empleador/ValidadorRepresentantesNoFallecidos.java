package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

 /**
 *
 * Validador 18	Validar que la(s) persona(s) asociadas a la novedad no estén registradas en base de datos como fallecida(s)
 * Valida:
 * Utilizando los datos básicos de identificación de la(s) persona(s):
 * -Tipo documento de identificación
 * -Número documento de identificación
 * -Primer Nombre y Primer Apellido
 * se valida en el sistema que la(s) persona(s) no estén registradas como fallecida(s)
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorRepresentantesNoFallecidos extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersAsocNovedadRegistNoFallecida.execute");
		
		try{
			TipoIdentificacionEnum tipoIdentificacionRepresentanteLegal 
					= TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacionRepresentanteLegal = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        TipoIdentificacionEnum tipoIdentificacionRepresentanteLegalSuplente 
	        		= TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM_RLS));
	        String numeroIdentificacionRepresentanteLegalSuplente = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM_RLS);

			logger.info("tipoIdentificacionRepresentanteLegal"+ tipoIdentificacionRepresentanteLegal);
			logger.info("numeroIdentificacionRepresentanteLegal"+ numeroIdentificacionRepresentanteLegal);
			logger.info("tipoIdentificacionRepresentanteLegalSuplente"+ tipoIdentificacionRepresentanteLegalSuplente);
			logger.info("numeroIdentificacionRepresentanteLegalSuplente"+ numeroIdentificacionRepresentanteLegalSuplente);
	        //String primerNombreRL = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_RL_PARAM);
	        
	        //String primerApellidoRL = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_RL_PARAM);
	        
	        //String primerNombreRLS = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_RLS_PARAM);

	        //String primerApellidoRLS = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_RLS_PARAM);
	        
	        // Se consulta el representante legal
			List<Object[]> personaRepresentanteLegal 
				= entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_NATIVA)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionRepresentanteLegal.name())
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionRepresentanteLegal)
					//.setParameter(ConstantesValidaciones.PRIMER_NOMBRE_PARAM, primerNombreRL)
					//.setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellidoRL)
					//.setParameter(ConstantesValidaciones.ESTADO_PERSONA_FALLECIDO, Boolean.TRUE)
					.getResultList();
	        
			// Se consulta el representante legal suplente
			logger.info(personaRepresentanteLegal != null ? personaRepresentanteLegal.size() : -1);
			List<Object[]> personaRepresentanteLegalSuplente 
				= (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_NATIVA)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionRepresentanteLegalSuplente.name())
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionRepresentanteLegalSuplente)
					//.setParameter(ConstantesValidaciones.PRIMER_NOMBRE_PARAM, primerNombreRLS)
					//.setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellidoRLS)
					//.setParameter(ConstantesValidaciones.ESTADO_PERSONA_FALLECIDO, Boolean.TRUE)
					.getResultList();
			logger.info(personaRepresentanteLegalSuplente  != null ? personaRepresentanteLegalSuplente.size() : -1);
	        // Se valida la condición
			if((personaRepresentanteLegal == null || personaRepresentanteLegal.isEmpty())
					&& (personaRepresentanteLegalSuplente == null || personaRepresentanteLegalSuplente.isEmpty())){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorPersAsocNovedadRegistNoFallecida.execute");
				// Validación exitosa, Validador 18	Validar que la(s) persona(s) asociadas a la novedad 
				// no estén registradas en base de datos como fallecida(s)
				logger.info("VALIDACION EXITOSA");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_REPRESENTANTES_NO_FALLECIDOS);	
				
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorPersAsocNovedadRegistNoFallecida.execute");
				// Validación no aprobada, Validador 18	Validar que la(s) persona(s) asociadas a la novedad 
				// no estén registradas en base de datos como fallecida(s)
				logger.info("VALIDACION FALLIDA");
				return crearValidacion(myResources.getString(
						ConstantesValidaciones.KEY_PERSONA_REGISTRADA_COMO_FALLECIDA),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_REPRESENTANTES_NO_FALLECIDOS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_REPRESENTANTES_NO_FALLECIDOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
