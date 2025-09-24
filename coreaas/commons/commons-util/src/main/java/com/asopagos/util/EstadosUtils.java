package com.asopagos.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase Encargada De Retornar Los Diferentes Estados De Las Personas O
 * Empleadores
 *
 * @author jvelandia
 *
 */
public class EstadosUtils {

    /**
     * Referencia al logger
     */
    private final static ILogger logger = LogManager.getLogger(EstadosUtils.class);
    /**
     * Cargar Archivo De Configuración
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle("consultaEstados");

    /**
     * Método Encargado De Consultar Los EstadosCaja De Las Diferentes Personas
     * o Empleadores, Pueden Ser 1 ó Muchas Personas
     *
     * @param paramsConsulta, Lista De ConsultarEstadoDto
     * @return retorna un listado con los diferentes estados de caja de las
     * persona o empleadores
     */
    public static List<EstadoDTO> consultarEstadoCaja(List<ConsultarEstadoDTO> paramsConsulta) {
        logger.debug("Ingresando Al Método consultarEstadoCaja");
        List<EstadoDTO> listaEstados = new ArrayList<>();
        ConsultarEstadoDTO estad = new ConsultarEstadoDTO();
//		StringBuilder bf = new StringBuilder();
        try {
//			String valorNI;
            EntityManager entityManager;
            String tipoPersona;
//			TipoIdentificacionEnum valorTI;
//			Long idEmpleador;
//			String tipoRol;
            List<ConsultarEstadoDTO> paramsConsultaToJason = new ArrayList<ConsultarEstadoDTO>();
            String jsonPayload;
            ObjectMapper mapper = new ObjectMapper();
            //if agregado por IndexOutOfBoundsException cuando viene vacio el arreglo
            if (paramsConsulta.size() > 0) {
                entityManager = paramsConsulta.get(0).getEntityManager();
            } else {
                entityManager = null;
            }
            //if(paramsConsulta.size()>1){
            int cont = 0;
            ConsultarEstadoDTO cond;
            for (ConsultarEstadoDTO param : paramsConsulta) {
                cond = new ConsultarEstadoDTO();
                cond.setTipoIdentificacion(param.getTipoIdentificacion());
                cond.setNumeroIdentificacion(param.getNumeroIdentificacion());
                cond.setTipoRol(param.getTipoRol());
                cond.setIdEmpleador(param.getIdEmpleador());
                paramsConsultaToJason.add(cond);
                if ((cont + 1) % 100 == 0 || (cont + 1) == paramsConsulta.size()) {
                    if (paramsConsulta.get(0).getIdEmpleador() != null) {
                        tipoPersona = ConstantesComunes.EMPLEADORES_ID;
                    } else if (paramsConsulta.get(0).getTipoRol() != null) {
                        tipoPersona = ConstantesComunes.TIPO_ROL;
                    } else {
                        tipoPersona = paramsConsulta.get(0).getTipoPersona();
                    }

                    try {
                        jsonPayload = mapper.writeValueAsString(paramsConsultaToJason);
                        logger.debug("ejecutaConsultas");
                        listaEstados = ejecutaConsultas(entityManager, tipoPersona, jsonPayload);
                        paramsConsultaToJason = new ArrayList<ConsultarEstadoDTO>();
                    } catch (JsonProcessingException e) {
                        logger.error(ConstantesComunes.FIN_LOGGER + "consultarEstadoCaja");
                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                    }

                }
                cont++;
            }
            /*for(int x=0; x<paramsConsulta.size(); x++){
					estad = new ConsultarEstadoDTO();
					estad.setEntityManager(paramsConsulta.get(x).getEntityManager());
		            estad.setNumeroIdentificacion(paramsConsulta.get(x).getNumeroIdentificacion());
		            estad.setTipoPersona(paramsConsulta.get(x).getTipoPersona());
		            estad.setTipoIdentificacion(paramsConsulta.get(x).getTipoIdentificacion());
		            
		           //bf.append(estad.getTipoIdentificacion()+","+estad.getNumeroIdentificacion());
		            if(paramsConsulta.get(0).getIdEmpleador()!=null){
		            	tipoPersona = ConstantesComunes.EMPLEADORES_ID;
		            	estad.setIdEmpleador(idEmpleador);
		            	//bf.append(","+idEmpleador);
		            }else if (paramsConsulta.get(0).getTipoRol()!=null) {
		            	tipoPersona = ConstantesComunes.TIPO_ROL;
		            	estad.setTipoRol(tipoRol);
		            	//bf.append(","+tipoRol);
					}
		            
		            paramsConsultaToJason.add(estad);
		            if((x+1)%100==0){
		        		try {
		        			jsonPayload = mapper.writeValueAsString(paramsConsultaToJason);
		        			//Invocamos Al Procedimiento Con Los 100 Registros, Por El Momento Se Ejecuta Una Consulta
			            	listaEstados.addAll(ejecutaConsultas(entityManager, tipoPersona, jsonPayload));
		        		} catch (JsonProcessingException e) {
		        			logger.error(ConstantesComunes.FIN_LOGGER + "consultarEstadoCaja");
		                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		        		}
		            	//bf = new StringBuilder();
						estad = new ConsultarEstadoDTO();
						paramsConsultaToJason = new ArrayList<ConsultarEstadoDTO>();
					}
					if(paramsConsulta.size()-1==x && estad!=null){
						try {
		        			jsonPayload = mapper.writeValueAsString(paramsConsultaToJason);
		        			//Invocamos Al Procedimiento Con Los 100 Registros, Por El Momento Se Ejecuta Una Consulta
			            	listaEstados.addAll(ejecutaConsultas(entityManager, tipoPersona, jsonPayload));
		        		} catch (JsonProcessingException e) {
		        			logger.error(ConstantesComunes.FIN_LOGGER + "consultarEstadoCaja");
		                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		        		}
					}
				}*/
 /*}else if(paramsConsulta.size()==1){
	            valorTI = paramsConsulta.get(0).getTipoIdentificacion();
	            valorNI = paramsConsulta.get(0).getNumeroIdentificacion();
	            entityManager = paramsConsulta.get(0).getEntityManager();
	            tipoPersona = paramsConsulta.get(0).getTipoPersona();
	            idEmpleador = paramsConsulta.get(0).getIdEmpleador();
	            tipoRol = paramsConsulta.get(0).getTipoRol();
	            try {
        			jsonPayload = mapper.writeValueAsString(paramsConsulta);
        			if (valorTI != null && valorNI != null && !valorNI.isEmpty() && entityManager!=null) {
    	            	if(idEmpleador==null && tipoRol==null){
    	                	listaEstados = ejecutaConsultas(entityManager, tipoPersona, jsonPayload);
    	                }else if(idEmpleador==null && tipoRol!=null){
    	                	listaEstados = ejecutaConsultas(entityManager, ConstantesComunes.TIPO_ROL, jsonPayload);
    	                }else{
    	                	listaEstados = ejecutaConsultas(entityManager, ConstantesComunes.EMPLEADORES_ID, jsonPayload);
    	                }
    	            }
        		} catch (JsonProcessingException e) {
        			logger.error(ConstantesComunes.FIN_LOGGER + "consultarEstadoCaja");
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        		}
	        }*/
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return listaEstados;
    }

    @SuppressWarnings("unchecked")
    private static List<EstadoDTO> ejecutaConsultas(EntityManager entityManager, String tipoPersona, String parametros) {
        List<EstadoDTO> listaEstados = new ArrayList<>();
        EstadoDTO estadoPersona = new EstadoDTO();
        List<Object[]> estadoAfiliadoCajaResult = (List<Object[]>) entityManager
                .createNativeQuery(bundle.getString(tipoPersona))
                .setParameter("parametros", parametros).getResultList();
        if (estadoAfiliadoCajaResult != null) {
            for (Object[] objects : estadoAfiliadoCajaResult) {
                estadoPersona = new EstadoDTO();
                estadoPersona.setEstado(objects[2] == null ? null : EstadoAfiliadoEnum.valueOf(objects[2].toString()));
                estadoPersona.setNumeroIdentificacion(objects[1].toString());
                estadoPersona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(objects[0].toString()));

                listaEstados.add(estadoPersona);
            }

        }
        return listaEstados;
    }
}
