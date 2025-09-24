package com.asopagos.pila.service.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.service.IProcesosDescarga;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.ConexionServidorFTPUtil;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */
@Stateless
public class ProcesosDescarga implements IProcesosDescarga {

    /**
     * Instancia del Excecutor Manager
     */
    @Resource(lookup = "java:jboss/ee/concurrency/executor/pila")
    private ManagedExecutorService mes;

    /**
     * Instancia del EJB para operaciones en BD con índices de planillas
     */
    @Inject
    private IPersistenciaDatosValidadores persistencia;

    /** Referencia al logger */
    private final ILogger logger = LogManager.getLogger(ProcesosDescarga.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IProcesosDescarga#descargarYguardarListadoFTPAsincrono(com.asopagos.util.ConexionServidorFTPUtil,
     *      java.util.List)
     */
    @Override
//    @Asynchronous
    public List<IndicePlanilla> descargarYguardarListadoFTPAsincrono(ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP,
            List<ArchivoPilaDTO> archivosDescargados, String usuario, Long idProceso) {
    	long timeStart = System.nanoTime();
        String firmaMetodo = "ProcesosDescarga.descargarYguardarListadoFTPAsincrono(ConexionServidorFTPUtil, List<ArchivoPilaDTO> )";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        if(archivosDescargados != null) {
        	logger.info("archivosDescargados.size()" + archivosDescargados.size());
        }

        List<ArchivoPilaDTO> archivos = descargarYguardarListadoFTP(conexionFTP, archivosDescargados);

        // se lleva a cabo la actualización de índices de planilla

        Map<Long, ArchivoPilaDTO> mapaOI = new HashMap<>();
        Map<Long, ArchivoPilaDTO> mapaOF = new HashMap<>();

        // se listan los índices recibidos por parámetro
        Set<Long> idsPorParametroOI = new HashSet<>();
        Set<Long> idsPorParametroOF = new HashSet<>();

        // índices a actualizar
        List<IndicePlanilla> indicesOICarga = new ArrayList<>();
        List<IndicePlanillaOF> indicesOFCarga = new ArrayList<>();

        for (ArchivoPilaDTO archivo : archivos) {
            if (archivo.getIdIndicePlanillaOI() != null && !mapaOI.containsKey(archivo.getIdIndicePlanillaOI())) {
                mapaOI.put(archivo.getIdIndicePlanillaOI(), archivo);
            }
            if (archivo.getIdIndicePlanillaOF() != null && !mapaOF.containsKey(archivo.getIdIndicePlanillaOF())) {
                mapaOF.put(archivo.getIdIndicePlanillaOF(), archivo);
            }

            if (archivo.getIndicePlanilla() == null && archivo.getIdIndicePlanillaOI() != null) {
                idsPorParametroOI.add(archivo.getIdIndicePlanillaOI());
            }
            else if (archivo.getIndicePlanilla() != null) {
                indicesOICarga.add(archivo.getIndicePlanilla());
            }

            if (archivo.getIndicePlanillaOF() == null && archivo.getIdIndicePlanillaOF() != null) {
                idsPorParametroOF.add(archivo.getIdIndicePlanillaOF());
            }
            else if (archivo.getIndicePlanillaOF() != null) {
                indicesOFCarga.add(archivo.getIndicePlanillaOF());
            }
        }

        // se consultan los índices
        if (!idsPorParametroOI.isEmpty()) {
            indicesOICarga.addAll(persistencia.consultarIndicesPorListaIds(new ArrayList<>(idsPorParametroOI)));
        }

        for (IndicePlanilla indiceOI : indicesOICarga) {
            indiceOI.setIdDocumento(mapaOI.get(indiceOI.getId()).getIdentificadorDocumento());
            indiceOI.setVersionDocumento(mapaOI.get(indiceOI.getId()).getVersionDocumento());
            indiceOI.setTamanoArchivo(mapaOI.get(indiceOI.getId()).getSize());
        }

        if (!idsPorParametroOF.isEmpty()) {
            indicesOFCarga.addAll(persistencia.consultarIndicesPorListaIdsOF(new ArrayList<>(idsPorParametroOF)));
        }

        for (IndicePlanillaOF indiceOF : indicesOFCarga) {
            indiceOF.setIdDocumento(mapaOF.get(indiceOF.getId()).getIdentificadorDocumento());
            indiceOF.setVersionDocumento(mapaOF.get(indiceOF.getId()).getVersionDocumento());
            indiceOF.setTamanoArchivo(mapaOF.get(indiceOF.getId()).getSize());
        }

        // se actualizan los índices
        persistencia.actualizarListadoIndicePlanillas(indicesOICarga);
        persistencia.actualizarListadoIndicePlanillasOF(indicesOFCarga);
        
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        long timeEnd = System.nanoTime();
        logger.info(firmaMetodo + " tiempo ejecución en : " + TimeUnit.SECONDS.convert((timeEnd - timeStart), TimeUnit.NANOSECONDS));
        
        return indicesOICarga;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.service.IProcesosDescarga#descargarYguardarListadoFTPSincrono(com.asopagos.util.ConexionServidorFTPUtil,
     *      java.util.List)
     */
    @Override
	public List<ArchivoPilaDTO> descargarYguardarListadoFTPSincrono(ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP,
            List<ArchivoPilaDTO> archivosDescargados) {
        String firmaMetodo = "ProcesosDescarga.descargarYguardarListadoFTPSincrono(ConexionServidorFTPUtil, List<ArchivoPilaDTO>)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ArchivoPilaDTO> result = descargarYguardarListadoFTP(conexionFTP, archivosDescargados);

        logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de la descarga de un conjunto de documentos de FTP
     * @param archivosDescargados
     * @param conexionFTP
     * @return
     */
    private List<ArchivoPilaDTO> descargarYguardarListadoFTP(ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP,
            List<ArchivoPilaDTO> archivosDescargados) {
    	long timeStart = System.nanoTime();
        String firmaMetodo = "ProcesosDescarga.descargarYguardarListadoFTP(ConexionServidorFTPUtil, List<ArchivoPilaDTO>)";
        logger.warn(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se actualiza el listado en el DTO de la conexión y se procede a descargar el contenido de los archivos
        conexionFTP.setArchivosDescargados(archivosDescargados);
        conexionFTP.conectarYDescargar();

        // se cargan los archivos en el ECM
        List<ArchivoPilaDTO> resultado = new ArrayList<>();

        try {
            // se prepara la carga paralela en el servicio de archivos
            List<Callable<ArchivoPilaDTO>> tareasParalelas = new LinkedList<>();
            List<Future<ArchivoPilaDTO>> resultadosFuturos;

            for (ArchivoPilaDTO archivoPilaDTO : conexionFTP.getArchivosDescargados()) {
                Callable<ArchivoPilaDTO> parallelTaskArchivos = () -> {
                    return almacenarArchivo(archivoPilaDTO);
                };

                tareasParalelas.add(parallelTaskArchivos);
            }

            resultadosFuturos = mes.invokeAll(tareasParalelas);

            for (Future<ArchivoPilaDTO> future : resultadosFuturos) {
                resultado.add(future.get());
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        long timeEnd = System.nanoTime();
        logger.info("descargarYguardarListadoFTP tiempo ejecución en : " + TimeUnit.SECONDS.convert((timeEnd - timeStart), TimeUnit.NANOSECONDS));
        return resultado;
    }

    /**
     * Método para almacenar un archivo PILA en el ECM
     * 
     * @param archivoCarga
     *        DTO que contiene la información del archivo a almacenar
     * @return ArchivoPilaDTO
     *         DTO de entrada con el ID de documento de ECM
     */
    private ArchivoPilaDTO almacenarArchivo(ArchivoPilaDTO archivoCarga) {
        String firmaMetodo = "ProcesosDescarga.almacenarArchivo(ArchivoPilaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se inicializa el DTO de entrada
        InformacionArchivoDTO archivoDTOEntrada = new InformacionArchivoDTO();
        archivoDTOEntrada.setDocName(archivoCarga.getDocName());
        archivoDTOEntrada.setFileName(archivoCarga.getFileName());
        archivoDTOEntrada.setDataFile(archivoCarga.getDataFile());
        archivoDTOEntrada.setProcessName(archivoCarga.getProcessName());
        archivoDTOEntrada.setFileType(archivoCarga.getFileType());

        AlmacenarArchivo almacenarArchivoPilaService;
        almacenarArchivoPilaService = new AlmacenarArchivo(archivoDTOEntrada);

        // se define la salida
        InformacionArchivoDTO salida = null;

        almacenarArchivoPilaService.execute();

        salida = almacenarArchivoPilaService.getResult();

        if (salida != null) {
            archivoCarga.setIdentificadorDocumento(salida.getIdentificadorDocumento());
            archivoCarga.setVersionDocumento(salida.getVersionDocumento());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return archivoCarga;
    }
}
