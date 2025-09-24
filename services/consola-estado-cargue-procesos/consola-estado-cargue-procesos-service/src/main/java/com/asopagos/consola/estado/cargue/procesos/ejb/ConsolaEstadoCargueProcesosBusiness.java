
package com.asopagos.consola.estado.cargue.procesos.ejb;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.constants.NamedQueriesConstants;
import com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.database.SecuenciaUtil;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.*;
import com.asopagos.entidades.transversal.core.ConsolaEstadoCargueMasivo;
import com.asopagos.entidades.transversal.core.ResultadoHallazgoValidacionArchivo;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;
import com.asopagos.entidades.transversal.core.ConsolaEstadoProcesoMasivo;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.common.util.DateUtil;
import co.com.heinsohn.lion.fileprocessing.fileloader.FileLoadedLog;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.archivos.util.ExcelUtil;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.List;



/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la afiliación de personas <b>Historia de Usuario:</b> HU-121-104
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@Stateless
public class ConsolaEstadoCargueProcesosBusiness implements ConsolaEstadoCargueProcesosService {

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "consolaestadocarguemasivo_PU")
    private EntityManager entityManager;
    
    /**
     * Indica el nombre de la secuencia usada para el registro de hallazgos de validación de archivo
     */
    private final static String SECUENCIA_HALLAZGO= "SEC_consecutivoRHV";
    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(ConsolaEstadoCargueProcesosBusiness.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#registrarCargueConsolaEstado(com.asopagos.dto.ConsolaEstadoCargueProcesoDTO)
     */
    @Override
    public void registrarCargueConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        logger.debug("Inicia registrarCargueConsolaEstado(ConsolaEstadoCargueProcesoDTO)");
        ConsolaEstadoCargueMasivo conCargueMasivo;
        try {
            conCargueMasivo = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_ESTADO_CARGUE_POR_CARGA_ID_TIPO_PROCESO,
                            ConsolaEstadoCargueMasivo.class)
                    .setParameter("idCargue", consolaEstadoCargueProcesoDTO.getCargue_id())
                    .setParameter("tipoProceso", consolaEstadoCargueProcesoDTO.getProceso()).getSingleResult();
            logger.debug(
                    "Finaliza registrarCargueConsolaEstado(ConsolaEstadoCargueProcesoDTO): Recurso ya se encuentra registrado - idCargue:"
                            + consolaEstadoCargueProcesoDTO.getCargue_id() + " -tipoProceso:" + consolaEstadoCargueProcesoDTO.getProceso());
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);

        } catch (NoResultException nre) {
            conCargueMasivo = null;
        } catch (NonUniqueResultException nur) {
            logger.debug(
                    "Finaliza registrarCargueConsolaEstado(ConsolaEstadoCargueProcesoDTO): Recurso ya se encuentra registrado - idCargue:"
                            + consolaEstadoCargueProcesoDTO.getCargue_id() + " - tipoProceso:"
                            + consolaEstadoCargueProcesoDTO.getProceso());
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        }
        if (conCargueMasivo == null) {
            Long fechaActual = Calendar.getInstance().getTimeInMillis();
            if (!EstadoCargueMasivoEnum.EN_PROCESO.equals(consolaEstadoCargueProcesoDTO.getEstado())
                    && consolaEstadoCargueProcesoDTO.getFechaFin() == null) {
                consolaEstadoCargueProcesoDTO.setFechaFin(fechaActual);
            }
            if (consolaEstadoCargueProcesoDTO.getFechaInicio() == null) {
                consolaEstadoCargueProcesoDTO.setFechaInicio(fechaActual);
            }
            conCargueMasivo = consolaEstadoCargueProcesoDTO.convertToEntity();
            entityManager.persist(conCargueMasivo);
            // Se realiza el registro de hallazgos si la lista contiene hallazgos
            registrarHallazgoValidacionArchivo(consolaEstadoCargueProcesoDTO, conCargueMasivo.getIdConsolaEstadoCargueMasivo());
            logger.debug("Finaliza registrarCargueConsolaEstado(ConsolaEstadoCargueProcesoDTO): Se crea exitosamente el recurso");
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#registrarCargueConsolaEstado(com.asopagos.dto.ConsolaEstadoCargueProcesoDTO)
     */
    @Override
    public Long registrarCargueProcesoMasivo(ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        logger.debug("Inicia registrarCargueConsolaEstado(ConsolaEstadoProcesoMasivoDTO)");
        ConsolaEstadoProcesoMasivo conCargueMasivo;
        
        Long fechaActual = Calendar.getInstance().getTimeInMillis();
        if (!EstadoCargueMasivoEnum.EN_PROCESO.equals(consolaEstadoProcesoDTO.getEstado())
                && consolaEstadoProcesoDTO.getFechaFin() == null) {
                    consolaEstadoProcesoDTO.setFechaFin(fechaActual);
        }
        if (consolaEstadoProcesoDTO.getFechaInicio() == null) {
            consolaEstadoProcesoDTO.setFechaInicio(fechaActual);
        }
        conCargueMasivo = consolaEstadoProcesoDTO.convertToEntity();
        entityManager.persist(conCargueMasivo);
        logger.debug("Finaliza registrarCargueConsolaEstado(ConsolaEstadoProcesoMasivoDTO): Se crea exitosamente el recurso");
        return conCargueMasivo.getIdConsolaEstadoProcesoMasivo();
    }
    /////melisssa
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConsolaEstadoProcesoDTO> consultarProcesoConsolaEstado(TipoProcesosMasivosEnum proceso,
            EstadoCargueMasivoEnum estado, Long fechaInicio, Long fechaFin, String error, UriInfo uriInfo, HttpServletResponse response) {
        logger.debug("ConsolaEstadoProcesoDTO(" + proceso + ", " + estado + ", " + fechaInicio + ", " + fechaFin +  ")");

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        // Se agregan los parametros de consulta
        queryBuilder.addParam("tipoProceso", proceso.name());
        if (estado != null) {
            queryBuilder.addParam("estado", estado.name());
        }
        else {
            queryBuilder.addParam("estado", null);
        }
        if (fechaInicio != null) {
            queryBuilder.addParam("fechaInicio", new Date(fechaInicio));
        }
        else {
            queryBuilder.addParam("fechaInicio", null);
        }
        if (fechaFin != null) {
            queryBuilder.addParam("fechaFin", new Date(fechaFin));
        }
        else {
            queryBuilder.addParam("fechaFin", null);
        }
        queryBuilder.addOrderByDefaultParam("-fechaInicio");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_PROCESO_POR_FILTROS_PAGINADA, null);
        return query.getResultList();
    }


    @Override
    public void actualizarProcesoConsolaEstado(Long IdConsolaEstadoProcesoMasivo, ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        ConsolaEstadoProcesoMasivo conCargueMasivo = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_ESTADO_PROCESO_POR_CARGA_ID_TIPO_PROCESO,
                ConsolaEstadoProcesoMasivo.class)
                .setParameter("idConsolaEstadoProcesoMasivo", consolaEstadoProcesoDTO.getIdConsolaEstadoProcesoMasivo())
                .setParameter("tipoProce", consolaEstadoProcesoDTO.getProceso()).getSingleResult();

        if (!EstadoCargueMasivoEnum.EN_PROCESO.equals(consolaEstadoProcesoDTO.getEstado())
                && consolaEstadoProcesoDTO.getFechaFin() == null) {
                    consolaEstadoProcesoDTO.setFechaFin(Calendar.getInstance().getTimeInMillis());
        }
        consolaEstadoProcesoDTO.copyDTOToEntity(conCargueMasivo);
    }

    @Override
    public Response exportarArchivProcesosConsolaEstado(DatosArchivoProcesosProcesosReporteDTO datosArchivoProcesosProcesosReporte, UriInfo uriInfo, HttpServletResponse response) throws IOException {

        List<ConsolaEstadoProcesoDTO> result = new ArrayList<>();

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);

        queryBuilder.addParam("tipoProceso", datosArchivoProcesosProcesosReporte.getTipoProceso().name());
        if (datosArchivoProcesosProcesosReporte.getEstado() != null) {
            queryBuilder.addParam("estado", datosArchivoProcesosProcesosReporte.getEstado().name());
        }
        else {
            queryBuilder.addParam("estado", null);
        }
        if (datosArchivoProcesosProcesosReporte.getFechaInicio() != null) {
            queryBuilder.addParam("fechaInicio", new Date(datosArchivoProcesosProcesosReporte.getFechaInicio()));
        }
        else {
            queryBuilder.addParam("fechaInicio", null);
        }
        if (datosArchivoProcesosProcesosReporte.getFechaFin() != null) {
            queryBuilder.addParam("fechaFin", new Date(datosArchivoProcesosProcesosReporte.getFechaFin()));
        }
        else {
            queryBuilder.addParam("fechaFin", null);
        }

        queryBuilder.addOrderByDefaultParam("-fechaInicio");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_PROCESO_POR_FILTROS_PAGINADA, null);
     
        result = query.getResultList();

        String[] cabeceras = new String[0];
        if (datosArchivoProcesosProcesosReporte.getCabeceras() != null) {
            cabeceras = new String[datosArchivoProcesosProcesosReporte.getCabeceras().size()];
            datosArchivoProcesosProcesosReporte.getCabeceras().toArray(cabeceras);
        }
        List<String[]> datosCuerpoExcel = result.stream().map(ConsolaEstadoProcesoDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("archivos", datosCuerpoExcel, datosArchivoProcesosProcesosReporte.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
		inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
		res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
    }
    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#consultarCargueConsolaEstado(com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum,
     *      com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum, java.lang.Long, java.lang.Long, java.lang.String,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConsolaEstadoCargueProcesoDTO> consultarCargueConsolaEstado(TipoProcesoMasivoEnum tipoProceso,
            EstadoCargueMasivoEnum estado, Long fechaInicio, Long fechaFin, UriInfo uriInfo, HttpServletResponse response) {
        logger.debug("consultarCargueConsolaEstado(" + tipoProceso + ", " + estado + ", " + fechaInicio + ", " + fechaFin +  ")");

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        // Se agregan los parametros de consulta
        queryBuilder.addParam("tipoProceso", tipoProceso.name());
        if (estado != null) {
            queryBuilder.addParam("estado", estado.name());
        }
        else {
            queryBuilder.addParam("estado", null);
        }
        if (fechaInicio != null) {
            queryBuilder.addParam("fechaInicio", new Date(fechaInicio));
        }
        else {
            queryBuilder.addParam("fechaInicio", null);
        }
        if (fechaFin != null) {
            queryBuilder.addParam("fechaFin", new Date(fechaFin));
        }
        else {
            queryBuilder.addParam("fechaFin", null);
        }
        queryBuilder.addOrderByDefaultParam("-fechaInicio");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_POR_FILTROS_PAGINADA, null);
        return query.getResultList();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#consultarCargueConsolaEstado(com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum,
     *      com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum, java.lang.Long, java.lang.Long, java.lang.String,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConsolaEstadoCargueProcesoDTO consultarUltimoCargueConsolaEstado(TipoProcesoMasivoEnum tipoProceso){
       ConsolaEstadoCargueProcesoDTO estadoCargueProcesoDTO = null;
        try {
            estadoCargueProcesoDTO = (ConsolaEstadoCargueProcesoDTO) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_ESTADO_CARGUE_UNICO_TIPO_PROCESO)
                .setParameter("tipoProceso", tipoProceso.name()).getSingleResult();
        } catch (NoResultException e) {
        logger.error("Finaliza consultarUltimoCargueConsolaEstado, No hay regitros en la tabla ConsolaEstadoCargueProceso por ese proceso");
        }
        return estadoCargueProcesoDTO;
    }
    @Override
    public Response exportarArchivoCargueConsolaEstado(DatosArchivoCargueProcesosReporteDTO datosArchivoCargueProcesosReporte, UriInfo uriInfo, HttpServletResponse response) throws IOException {

        List<ConsolaEstadoCargueProcesoDTO> result = new ArrayList<>();

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);

        queryBuilder.addParam("tipoProceso", datosArchivoCargueProcesosReporte.getTipoProceso().name());
        if (datosArchivoCargueProcesosReporte.getEstado() != null) {
            queryBuilder.addParam("estado", datosArchivoCargueProcesosReporte.getEstado().name());
        }
        else {
            queryBuilder.addParam("estado", null);
        }
        if (datosArchivoCargueProcesosReporte.getFechaInicio() != null) {
            queryBuilder.addParam("fechaInicio", new Date(datosArchivoCargueProcesosReporte.getFechaInicio()));
        }
        else {
            queryBuilder.addParam("fechaInicio", null);
        }
        if (datosArchivoCargueProcesosReporte.getFechaFin() != null) {
            queryBuilder.addParam("fechaFin", new Date(datosArchivoCargueProcesosReporte.getFechaFin()));
        }
        else {
            queryBuilder.addParam("fechaFin", null);
        }

        queryBuilder.addOrderByDefaultParam("-fechaInicio");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_POR_FILTROS_PAGINADA, null);
     
        result = query.getResultList();

        String[] cabeceras = new String[0];
        if (datosArchivoCargueProcesosReporte.getCabeceras() != null) {
            cabeceras = new String[datosArchivoCargueProcesosReporte.getCabeceras().size()];
            datosArchivoCargueProcesosReporte.getCabeceras().toArray(cabeceras);
        }
        List<String[]> datosCuerpoExcel = result.stream().map(ConsolaEstadoCargueProcesoDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("archivos", datosCuerpoExcel, datosArchivoCargueProcesosReporte.getCabeceras());

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
		inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
		res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
    }


    @Override
    public Response exportarRegistroErroresArchivo(RegistroErroresArchivoDTO registroErroresArchivoDTO,
                                                   UriInfo uriInfo, HttpServletResponse response) throws IOException {

        List<ResultadoHallazgosValidacionArchivoDTO> result;
        String nombreArchivo;

        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        if (registroErroresArchivoDTO.getIdConsola() != null) {
            queryBuilder.addParam("idConsola", registroErroresArchivoDTO.getIdConsola());
        }
        else {
            queryBuilder.addParam("idConsola", null);
        }
        queryBuilder.addOrderByDefaultParam("numeroLinea");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LOG_CONSOLA_ID, null);

        result = query.getResultList();

        String[] cabeceras;
        if (registroErroresArchivoDTO.getCabeceras() != null) {
            cabeceras = new String[registroErroresArchivoDTO.getCabeceras().size()];
            registroErroresArchivoDTO.getCabeceras().toArray(cabeceras);
        }
        final long[] item = {0L};
        List<String[]> datosCuerpoExcel = result.stream()
                .peek(data -> {
                    item[0] += 1L;
                    data.setItem(item[0]);
                }).map(ResultadoHallazgosValidacionArchivoDTO::toListString).collect(Collectors.toList());
        byte[] dataExcel = ExcelUtil.generarArchivoExcel("archivos", datosCuerpoExcel, registroErroresArchivoDTO.getCabeceras());

        nombreArchivo = result.get(0).getNombreArchivo();
        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
        inputStream = new BufferedInputStream(new ByteArrayInputStream(dataExcel));
        res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename="+this.obtenerNombreArchivoSinExtencion(nombreArchivo)+".xlsx");
        return res.build();
    }

    public static String obtenerNombreArchivoSinExtencion(String nombreArchivo){
        File archivo = new File(nombreArchivo);
        // Obtener el nombre del archivo completo
        String nombreCompleto = archivo.getName();
        // Encontrar la última posición del punto
        int puntoIndex = nombreCompleto.lastIndexOf(".");
        // Si no hay punto, el nombre es el mismo
        String nombreSinExtension;
        if (puntoIndex == -1) {
            nombreSinExtension = nombreCompleto; // No tiene extensión
        } else {
            nombreSinExtension = nombreCompleto.substring(0, puntoIndex); // Obtener nombre sin extensión
        }
        return nombreSinExtension;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#actualizarCargueConsolaEstado(java.lang.Long,
     *      com.asopagos.dto.ConsolaEstadoCargueProcesoDTO)
     */
    @Override
    public void actualizarCargueConsolaEstado(Long idCargue, ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        logger.debug("Inicia actualizarCargueConsolaEstado(" + idCargue + ", ConsolaEstadoCargueProcesoDTO)");

        ConsolaEstadoCargueMasivo conCargueMasivo = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_ESTADO_CARGUE_POR_CARGA_ID_TIPO_PROCESO,
                        ConsolaEstadoCargueMasivo.class)
                .setParameter("idCargue", consolaEstadoCargueProcesoDTO.getCargue_id())
                .setParameter("tipoProceso", consolaEstadoCargueProcesoDTO.getProceso()).getSingleResult();

        if (!EstadoCargueMasivoEnum.EN_PROCESO.equals(consolaEstadoCargueProcesoDTO.getEstado())
                && consolaEstadoCargueProcesoDTO.getFechaFin() == null) {
            consolaEstadoCargueProcesoDTO.setFechaFin(Calendar.getInstance().getTimeInMillis());
        }
        consolaEstadoCargueProcesoDTO.copyDTOToEntity(conCargueMasivo);
        // Se realiza el registro de hallazgos si la lista contiene hallazgos
        registrarHallazgoValidacionArchivo(consolaEstadoCargueProcesoDTO, conCargueMasivo.getIdConsolaEstadoCargueMasivo());
        logger.debug("Finaliza actualizarCargueConsolaEstado(" + idCargue + ", ConsolaEstadoCargueProcesoDTO)");
    }

    /**
     * Realiza el registro de hallazgos de validación de archivo en batch
     * @param consolaEstadoCargueProcesoDTO
     *        Información del registro de consola que contiene los hallazgos
     * @param idConsolaCargueMasivo
     *        Identificador de la consola
     */
    private void registrarHallazgoValidacionArchivo(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO,
            Long idConsolaCargueMasivo) {
        // Se registra el log de errro si existe
        if (consolaEstadoCargueProcesoDTO.getLstErroresArhivo() != null && !consolaEstadoCargueProcesoDTO.getLstErroresArhivo().isEmpty()) {
            Long valorInicial = SecuenciaUtil.getNextValue(entityManager, SECUENCIA_HALLAZGO,
                    consolaEstadoCargueProcesoDTO.getLstErroresArhivo().size());
            ResultadoHallazgoValidacionArchivo resultadoHallazgoValidacionArchivo;
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : consolaEstadoCargueProcesoDTO.getLstErroresArhivo()) {
                resultadoHallazgoValidacionArchivo = hallazgo.convertToEntity();
                resultadoHallazgoValidacionArchivo.setIdConsolaEstadoCargueMasivo(idConsolaCargueMasivo);
                resultadoHallazgoValidacionArchivo.setIdResultadoHallazgoValidacionArchivo(valorInicial);
                entityManager.persist(resultadoHallazgoValidacionArchivo);
                valorInicial++;
            }
        }
    }

    private List<ConsolaEstadoCargueProcesoDTO> consultarCargueConsolaEstado(String caja, TipoProcesoMasivoEnum tipoProceso,
            EstadoCargueMasivoEnum estado, Long fechaInicio, Long fechaFin, String usuario) {

        logger.debug(
                "Inicia consultarCargueConsolaEstado(String caja, String tipoProceso,String estado, Long fechaInicio, Long fechaFin, String usuario)");
        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();
        Long fileDefinitionId = null;

        List<ConsolaEstadoCargueProcesoDTO> lisCargueProcesoDTOs = new ArrayList<ConsolaEstadoCargueProcesoDTO>();
        if (caja != null) {
            fields.put("ccf", ConsultasDinamicasConstants.IGUAL);
            values.put("ccf", caja);
        }
        if (tipoProceso != null) {
            fields.put("tipoProcesoMasivo", ConsultasDinamicasConstants.IGUAL);
            values.put("tipoProcesoMasivo", tipoProceso);
        }
        if (estado != null) {
            fields.put("estadoCargueMasivo", ConsultasDinamicasConstants.IGUAL);
            values.put("estadoCargueMasivo", estado);
        }
        if (fechaInicio != null) {
            fields.put("fechaInicio", ConsultasDinamicasConstants.MAYOR_IGUAL);
            values.put("fechaInicio", new Date(fechaInicio));
        }
        if (fechaFin != null) {
            fields.put("fechaFin", ConsultasDinamicasConstants.MENOR);
            values.put("fechaFin", DateUtil.getInstance().addDays(new Date(fechaFin), 1));
        }
        if (usuario != null) {
            fields.put("usuario", ConsultasDinamicasConstants.IGUAL);
            values.put("usuario", usuario);
        }
        List<ConsolaEstadoCargueMasivo> conCargueMasivo = (List<ConsolaEstadoCargueMasivo>) JPAUtils.consultaEntidad(entityManager,
                ConsolaEstadoCargueMasivo.class, fields, values);

        for (ConsolaEstadoCargueMasivo consolaEstadoCargueMasivo : conCargueMasivo) {

            ConsolaEstadoCargueProcesoDTO estadoCargueProcesoDTO = new ConsolaEstadoCargueProcesoDTO(consolaEstadoCargueMasivo);
            try {
                if (consolaEstadoCargueMasivo.getTipoProcesoMasivo().equals(TipoProcesoMasivoEnum.CARGUE_DE_AFILIACION_MULTIPLE_122)) {
                    fileDefinitionId = new Long(
                            CacheManager.getParametro(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_MULTIPLES).toString());
                }
                else if (consolaEstadoCargueMasivo.getTipoProcesoMasivo().equals(TipoProcesoMasivoEnum.CARGUE_DE_PILA)) {

                    fileDefinitionId = new Long(
                            CacheManager.getParametro(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_MULTIPLES).toString());
                }
            } catch (Exception e) {
                throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
            }

            List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
            List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();

            Query qQuery = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_FILE_LOAD_LOG_POR_LOAD_ID);
            qQuery.setParameter("fileLoadedId", consolaEstadoCargueMasivo.getFileLoadedId());
            List<FileLoadedLog> lstErrores = qQuery.getResultList();
            String mensaje;
            String campoMensaje = "";
            String[] arregloMensaje;

            for (FileLoadedLog fileLoadedLog : lstErrores) {
                mensaje = fileLoadedLog.getMessage();
                arregloMensaje = mensaje.split(";");
                for (DefinicionCamposCargaDTO campo : campos) {
                    for (int i = 0; i < arregloMensaje.length; i++) {
                        if (arregloMensaje[i].contains(campo.getName())) {
                            mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
                            campoMensaje = campo.getLabel();
                            ResultadoHallazgosValidacionArchivoDTO hallazgo = crearHallazgo(fileLoadedLog.getLineNumber(), campoMensaje,
                                    mensaje);
                            listaHallazgos.add(hallazgo);
                            mensaje = "";
                            break;
                        }
                    }
                }
            }
            estadoCargueProcesoDTO.setLstErroresArhivo(listaHallazgos);
            lisCargueProcesoDTOs.add(estadoCargueProcesoDTO);
        }
        return lisCargueProcesoDTOs;
    }

    /**
     * 
     * @param fileLoadedId
     * @return
     */
    private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
        try {
            List<DefinicionCamposCargaDTO> campos = (List<DefinicionCamposCargaDTO>) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_CAMPOS_ARCHIVO).setParameter("idFileDefinition", fileLoadedId)
                    .getResultList();
            return campos;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Crea el registro de hallazago
     * @param lineNumber
     *        Numero de la linea
     * @param campo
     *        Campo del error
     * @param errorMessage
     *        Mensaje de error
     * @return Objeto con la información del hallazago de validación
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(errorMessage);
        return hallazgo;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.consola.estado.cargue.procesos.service.ConsolaEstadoCargueProcesosService#consultarLogErrorArchivo(java.lang.Long,
     *      java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ResultadoHallazgosValidacionArchivoDTO> consultarLogErrorArchivo(Long idConsola, UriInfo uriInfo, HttpServletResponse response) {
        logger.debug("consultarLogErrorArchivo(" + idConsola + ")");
        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uriInfo, response);
        queryBuilder.addParam("idConsola", idConsola);
        queryBuilder.addOrderByDefaultParam("numeroLinea");
        Query query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LOG_CONSOLA_ID, null);
        return query.getResultList();
    }

    @Override
    public Boolean existeArchivoPorNombre(TipoProcesoMasivoEnum tipoProceso,
            String nombreArchivo) {
        logger.debug("Inicia existeArchivoPorNombre(" + nombreArchivo + ", existeArchivoPorNombre)");

        List<Object> archivosSubidos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CONSOLA_POR_NOMBRE)
                .setParameter("tipoProceso", tipoProceso.name())
                .setParameter("nombreArchivo", nombreArchivo).getResultList();

        return archivosSubidos.size() != 0 ? Boolean.TRUE:Boolean.FALSE;
    }

}
