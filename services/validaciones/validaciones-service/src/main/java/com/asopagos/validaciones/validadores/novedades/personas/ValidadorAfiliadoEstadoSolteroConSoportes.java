package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
/**
 * CLASE QUE VALIDA:
 * Validar que el afiliado principal haya tenido hasta el 
 * momento de la novedad en el campo "estado civil" como soltero; 
 * y si no es así, validar que se haya registrado el divorcio, 
 * separación o fallecimiento del cónyuge, con la documentación 
 * de soporte (disolución y liquidación de la sociedad conyugal, 
 * divorcio, declaración de no convivencia, registro civil de defunción)
 *  
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoEstadoSolteroConSoportes extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorAfiliadoEstadoSolteroConSoportes.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			String canalRecepcion = datosValidacion.get(ConstantesValidaciones.CANAL_RECEPCION);
			
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoCivilEnum> estadoValido = new ArrayList<EstadoCivilEnum>();
	        estadoValido.add(EstadoCivilEnum.SOLTERO);


			for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {
				logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
	        
	        
	        //se consulta el afiliado con el tipo y número de documento
	        PersonaDTO Afiliado= (PersonaDTO) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			
			logger.info("Afiliado: " + Afiliado.getTipoIdentificacion() + " " + Afiliado.getNumeroIdentificacion() + " " + Afiliado.getEstadoCivil());
	        
	        //se verifica si el estado civil del afiliado es SOLTERO
			if(estadoValido.contains(Afiliado.getEstadoCivil()) || canalRecepcion.equals("PILA")){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorAfiliadoEstadoSolteroConSoportes.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_ESTADO_SOLTERO);	
			}
			else{

				//si el estado civil no es soltero, se verifica que exista registro de por lo menos una 
				//solicitud de novedad de algún tipo incluido en la siguiente lista.
				List<String> tiposDeNovedadBuscados = new ArrayList<String>();
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS_WEB.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_WEB.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL.name());
		        tiposDeNovedadBuscados.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_WEB.name());
		        	

		        //se consultan las solicitudes de novedad por usuario filtrando los resultados con la lista creada arriba
		        List<Object[]> solicitudesNovedad = (List<Object[]>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_SOLICITUDES_NOVEDAD_POR_TIPO_ASOCIADAS_AFILIADO)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, tiposDeNovedadBuscados)
						.getResultList();

				logger.info("Solicitudes de novedad: " + solicitudesNovedad != null ? solicitudesNovedad.size() : 0);
		        
		        
		        //si la lista no viene vacía es porque encontró algun registro que aprueba la validacion
				if(solicitudesNovedad != null && !solicitudesNovedad.isEmpty()){
					//validación exitosa
					logger.debug("HABILITADA- Fin de método ValidadorAfiliadoEstadoSolteroConSoportes.execute");
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_ESTADO_SOLTERO);	
				}else{
					//validación fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoEstadoSolteroConSoportes.execute");
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_ESTADO_SOLTERO),ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_AFILIADO_ESTADO_SOLTERO,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}  
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ESTADO_SOLTERO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
