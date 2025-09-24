package com.asopagos.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ParametroConsultaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase utilitaria que contiene validaciones sobre las personas en postulaciones FOVIS
 * 
 * @author Jose Arley Correa Salamanca<jocorrea@heinsohn.com.co>
 *
 */
public class PersonasFOVISUtils {

    /**
     * Referencia al logger
     */
    private static final ILogger LOGGER = LogManager.getLogger(PersonasFOVISUtils.class);

    /**
     * Indica el nombre de la propiedad que contiene la consulta
     */
    private static final String QUERY_POSTULACION_PERSONA = "persona.postulacion";

    /**
     * Carga archivo con las consultas
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle("consultaPersonaFOVIS");

    /**
     * Constructor por defecto
     */
    private PersonasFOVISUtils() {
        super();
    }

    /**
     * Consulta la postulacion vigente en la que se encuentra la persona enviada por parametro
     * @param paramsConsulta
     *        Parametros de la consulta
     * @return Lista de personas con postulacion asociada
     */
    public static List<PersonaPostulacionDTO> consultarPostulacionVigente(List<ParametroConsultaDTO> paramsConsulta) {
        String firma = " consultarPostulacionVigente(List<ParametroConsultaDTO>)";
        LOGGER.debug(ConstantesComunes.INICIO_LOGGER + firma);
        List<PersonaPostulacionDTO> listResult = new ArrayList<>();
        listResult.addAll(obtenerListaPostulacionByListaParametros(paramsConsulta));
        LOGGER.debug(ConstantesComunes.FIN_LOGGER + firma);
        return listResult;
    }

    /**
     * Realiza la consulta de varias parametros garantizando grupos de 100 registros para evitar sobre carga
     * @param paramsConsulta
     *        Parametros de consulta
     * @return Lista de personas con postulacion asociada
     */
    private static List<PersonaPostulacionDTO> obtenerListaPostulacionByListaParametros(List<ParametroConsultaDTO> paramsConsulta) {
    	String firma = " obtenerListaPostulacionByListaParametros(List<ParametroConsultaDTO>)";
        List<PersonaPostulacionDTO> listResult = new ArrayList<>();
        EntityManager entityManager = paramsConsulta.get(0).getEntityManager();
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload;
        List<ParametroConsultaDTO> paramsConsultaToJason = new ArrayList<ParametroConsultaDTO>();
        ParametroConsultaDTO cond;
		try {
			for(ParametroConsultaDTO param : paramsConsulta) {
				cond = new ParametroConsultaDTO();
				cond.setTipoIdentificacion(param.getTipoIdentificacion());
				cond.setNumeroIdentificacion(param.getNumeroIdentificacion());
				paramsConsultaToJason.add(cond);
			}
			jsonPayload = mapper.writeValueAsString(paramsConsultaToJason);
			listResult.addAll(ejecutarConsulta(entityManager, jsonPayload));
		} catch (JsonProcessingException e) {
			LOGGER.error(ConstantesComunes.FIN_LOGGER + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
        return listResult;
    }

    /**
     * Ejecuta la consulta con los parametros construidos a partir de la cantidad de persona parametro
     * @param entityManager
     *        Referencia conexion BD
     * @param parametros
     *        Parametros formateados
     * @return Lista de personas con postulacion asociada
     */
    @SuppressWarnings("unchecked")
    private static List<PersonaPostulacionDTO> ejecutarConsulta(EntityManager entityManager, String parametros) {
        List<PersonaPostulacionDTO> listResult = new ArrayList<>();
        // Lista de estados Hogar no permitido para postulacion vigente
        List<String> estadosHogar = new ArrayList<>();
        estadosHogar.add(EstadoHogarEnum.RECHAZADO.name());

        // Lista de postulacion persona
        List<Object[]> postulacionPersona = (List<Object[]>) entityManager.createNativeQuery(bundle.getString(QUERY_POSTULACION_PERSONA))
                .setParameter("parametros", parametros).setParameter("estadoHogar", estadosHogar)
                .setParameter("estadoPersonaPos", EstadoActivoInactivoEnum.ACTIVO.name()).getResultList();
        if (postulacionPersona != null && !postulacionPersona.isEmpty()) {
            PersonaPostulacionDTO dto = null;
            for (Object[] objects : postulacionPersona) {
                dto = new PersonaPostulacionDTO();
                BigInteger idPostulacion = (BigInteger) objects[0];
                dto.setIdPostulacionFovis(idPostulacion.longValue());
                if (objects[1] != null) {
                    dto.setEstadoHogar(EstadoHogarEnum.valueOf(objects[1].toString()));
                }
                dto.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(objects[2].toString()));
                dto.setNumeroIdentificacion(objects[3].toString());
                BigInteger idPersona = (BigInteger) objects[4];
                dto.setIdPersonaNovedad(idPersona.longValue());
                listResult.add(dto);
            }
        }
        return listResult;
    }

    /**
     * Obtiene la lista de clasificacion para integrante hogar
     * 
     * @return Lista de clasificacion
     */
    public static List<ClasificacionEnum> listarClasificacionIntegrante() {
        List<ClasificacionEnum> integrante = new ArrayList<>();
        integrante.add(ClasificacionEnum.CONYUGE_HOGAR);
        integrante.add(ClasificacionEnum.ABUELO_HOGAR);
        integrante.add(ClasificacionEnum.BISABUELO_HOGAR);
        integrante.add(ClasificacionEnum.CUNIADO_HOGAR);
        integrante.add(ClasificacionEnum.BISNIETO_HOGAR);
        integrante.add(ClasificacionEnum.HIJASTRO_HOGAR);
        integrante.add(ClasificacionEnum.HIJO_BIOLOGICO_HOGAR);
        integrante.add(ClasificacionEnum.HIJO_ADOPTIVO_HOGAR);
        integrante.add(ClasificacionEnum.HERMANO_HOGAR);
        integrante.add(ClasificacionEnum.PADRE_HOGAR);
        integrante.add(ClasificacionEnum.MADRE_HOGAR);
        integrante.add(ClasificacionEnum.PADRE_MADRE_ADOPTANTE_HOGAR);
        integrante.add(ClasificacionEnum.NIETO_HOGAR);
        integrante.add(ClasificacionEnum.SOBRINO_HOGAR);
        integrante.add(ClasificacionEnum.SUEGRO_HOGAR);
        integrante.add(ClasificacionEnum.TIO_HOGAR);
        integrante.add(ClasificacionEnum.YERNO_HOGAR);
        return integrante;
    }
}
