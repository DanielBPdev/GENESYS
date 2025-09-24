package com.asopagos.novedades.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.business.interfaces.IConsultasModeloReportes;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorDTO;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.DatosNovedadRegistradaPersonaDTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;
import com.asopagos.pagination.PaginationQueryParamsEnum;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Reportes <br/>
 *
 * @author Jose Arley Correa Salamanca <jocorrea>
 */
@Stateless
public class ConsultasModeloReportes implements IConsultasModeloReportes, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger LOGGER = LogManager.getLogger(ConsultasModeloReportes.class);

    @PersistenceContext(unitName = "novedadesReportes_PU")
    private EntityManager entityManagerReportes;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersona(UriInfo uriInfo, HttpServletResponse response,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String path = "consultarNovedadesRegistradasPersona(UriInfo, HttpServletResponse, TipoIdentificacionEnum, String):List<DatosNovedadRegistradaPersonaDTO>";
        try {
            LOGGER.debug(ConstantesComunes.INICIO_LOGGER + path);
            QueryBuilder queryBuilder = new QueryBuilder(entityManagerReportes, uriInfo, response);
            //Se consultan las novedades registradas a la persona.
            queryBuilder.addParam("tipoIdentificacion", tipoIdentificacion.name());
            queryBuilder.addParam("numeroIdentificacion", numeroIdentificacion);
            queryBuilder.addOrderByDefaultParam("-fechaRegistroNovedad");
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_REGISTRADAS_PERSONA, null);
            List<Object[]> novedadesRegistradas = query.getResultList();
            List<DatosNovedadRegistradaPersonaDTO> datosNovedadRegistrada = new ArrayList<>();

            for (Object[] novedadObj : novedadesRegistradas) {
                DatosNovedadRegistradaPersonaDTO datosNovedadReg = new DatosNovedadRegistradaPersonaDTO();
                datosNovedadReg.setNombreNovedad(TipoTransaccionEnum.valueOf((String) novedadObj[0]));
                datosNovedadReg.setFechaRegistroNovedad(novedadObj[1] != null ? (((Date) novedadObj[1]).getTime()) : null);
                datosNovedadReg.setEstadoNovedad((String) novedadObj[2]);
                datosNovedadReg.setFechaInicioVigencia(novedadObj[3] != null ? (((Date) novedadObj[3]).getTime()) : null);
                datosNovedadReg.setFechaFinVigencia(novedadObj[4] != null ? (((Date) novedadObj[4]).getTime()) : null);
                datosNovedadReg.setNumeroOperacion((String) novedadObj[5]);
                datosNovedadReg.setCanal((String) novedadObj[6]);
                datosNovedadReg.setEmpleador((String) novedadObj[7]);
                datosNovedadReg.setEstadoPersonaAntes(novedadObj[8] == null ? null : EstadoAfiliadoEnum.valueOf((String) novedadObj[8]));
                datosNovedadReg.setEstadoPersonaDespues(novedadObj[9] == null ? null : EstadoAfiliadoEnum.valueOf((String) novedadObj[9]));
                datosNovedadReg.setIdSolicitudGlobal(((BigInteger) novedadObj[10]).longValue());
                datosNovedadReg.setNivelNovedad((String) novedadObj[11]);
                datosNovedadReg.setIdEmpleador(novedadObj[12] == null ? null : ((BigInteger) novedadObj[12]).longValue());
                datosNovedadReg.setIdPersona(novedadObj[13] == null ? null : ((BigInteger) novedadObj[13]).longValue());
                datosNovedadRegistrada.add(datosNovedadReg);
            }
            LOGGER.debug(ConstantesComunes.FIN_LOGGER + path);
            return datosNovedadRegistrada;
        } catch (Exception e) {
            LOGGER.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    @Override
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpleador(FiltrosDatosNovedadDTO filtrosDatosNovedad, UriInfo uri,
            HttpServletResponse response) {
    	LOGGER.debug("Inicia servicio consultarNovedadesEmpleador(TipoIdentificacionEnum,String)");
        try {
            
            MultivaluedMap<String, String> parametros = new MultivaluedHashMap<>();
            if (filtrosDatosNovedad.getParams() != null) {
                for (Entry<String, List<String>> e : filtrosDatosNovedad.getParams().entrySet()) {
                    parametros.put(e.getKey(), e.getValue());
                }
            }
           
            QueryBuilder queryBuilder = new QueryBuilder(entityManagerReportes, parametros, response);
            queryBuilder.addParam("tipoIdentificacion", filtrosDatosNovedad.getTipoIdentificacion().name());
            queryBuilder.addParam("numeroIdentificacion", filtrosDatosNovedad.getNumeroIdentificacion());
            queryBuilder.addOrderByDefaultParam("-fechaRadicacion");
            Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_EMPLEADOR, null);

            List<DatosNovedadEmpleadorDTO> listaNovedadesEmpleador = new ArrayList<>();
            List<Object[]> datos = query.getResultList();
            if (datos != null && !datos.isEmpty()) {
                for (Object[] datosNovedad : datos) {
                    DatosNovedadEmpleadorDTO datosNovedadEmpleadorDTO = new DatosNovedadEmpleadorDTO((String) datosNovedad[0],
                            (Date) datosNovedad[1], (String) datosNovedad[2], (String) datosNovedad[3], (String) datosNovedad[4],
                            (Date) datosNovedad[5], (Date) datosNovedad[6], (String) datosNovedad[7], (String) datosNovedad[8],
                            (BigInteger) datosNovedad[9]);
                    listaNovedadesEmpleador.add(datosNovedadEmpleadorDTO);
                }
            }

            DatosNovedadEmpleadorPaginadoDTO datosNovedadEmpleadorPaginadoDTO = new DatosNovedadEmpleadorPaginadoDTO();
            datosNovedadEmpleadorPaginadoDTO.setDatosNovedadEmpleador(listaNovedadesEmpleador);
            if (response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor()) != null) {
                datosNovedadEmpleadorPaginadoDTO
                        .setTotalRecords(new Integer(response.getHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor())));
            }
            LOGGER.debug("Finaliza servicio consultarNovedadesEmpleador(TipoIdentificacionEnum,String)");
            return datosNovedadEmpleadorPaginadoDTO;
        } catch (Exception e) {
        	LOGGER.error("Error inesperado en consultarNovedadesEmpleador(TipoIdentificacionEnum,String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
}
