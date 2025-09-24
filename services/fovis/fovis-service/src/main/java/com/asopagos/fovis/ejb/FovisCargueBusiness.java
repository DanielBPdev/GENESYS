package com.asopagos.fovis.ejb;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.dto.CargueArchivoCruceFovisCatastrosDTO;
import org.apache.poi.ss.usermodel.Workbook;
import com.asopagos.archivos.util.ExcelReaderUtil;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.database.DatabaseUtil;
import com.asopagos.dto.CargueArchivoCruceFovisAfiliadoDTO;
import com.asopagos.dto.CargueArchivoCruceFovisBeneficiarioDTO;
import com.asopagos.dto.CargueArchivoCruceFovisBeneficiarioArriendoDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCatAntDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCatBogDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCatCaliDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCatMedDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCedulaDTO;
import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import com.asopagos.dto.CargueArchivoCruceFovisFechasCorteDTO;
import com.asopagos.dto.CargueArchivoCruceFovisHojaDTO;
import com.asopagos.dto.CargueArchivoCruceFovisIGACDTO;
import com.asopagos.dto.CargueArchivoCruceFovisNuevoHogarDTO;
import com.asopagos.dto.CargueArchivoCruceFovisReunidosDTO;
import com.asopagos.dto.CargueArchivoCruceFovisSisbenDTO;
import com.asopagos.dto.CargueArchivoCruceFovisUnidosDTO;
import com.asopagos.dto.CruceDTO;
import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.InformacionCruceFovisDTO;
import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.dto.ResultadoCruceFOVISDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import com.asopagos.dto.CargueArchivoCruceFovisBeneficiarioArriendoDTO;
import com.asopagos.dto.CargueArchivoCruceFovisCatastrosDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovis;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisAfiliado;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisBeneficiario;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatAnt;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatBog;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatCali;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatMed;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCedula;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisFechasCorte;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisIGAC;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisNuevoHogar;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisReunidos;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisSisben;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisUnidos;
import com.asopagos.entidades.ccf.fovis.Cruce;
import com.asopagos.entidades.ccf.fovis.CruceDetalle;
import com.asopagos.entidades.ccf.fovis.SolicitudGestionCruce;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisBeneficiarioArriendo;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatastros;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.HojaArchivoCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.constants.FileFieldConstants;
import com.asopagos.fovis.constants.NamedQueriesConstants;
import com.asopagos.fovis.service.FovisCargueService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.ExcelUtils;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;

import javax.persistence.StoredProcedureQuery;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de cargue de FOVIS<br/>
 * <b>Módulo:</b> Asopagos - FOVIS 3.2.1 - 3.2.5
 *
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@Stateless
public class FovisCargueBusiness implements FovisCargueService {

    /**
     * Referencia al logger
     */
    private static final ILogger LOGGER = LogManager.getLogger(FovisCargueBusiness.class);

    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName = "fovis_PU")
    private EntityManager entityManager;

    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * Método encargado de dar el fileDefinitionId que se consulta por medio de
     * la constante
     *
     * @param parametroConstante, parametro a consultar el fileDefinitionId
     * @return retorna el id perteneciente a la constante
     */
    private Long getFileDefinitionId(String parametroConstante) {
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(CacheManager.getConstante(parametroConstante).toString());
            return fileDefinitionId;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }
    }

    /**
     * Método encargado de dar FileFormat de un archivo
     *
     * @param informacionArchivoDTO, archivo al cual se le dara el fileFormat
     * @return retorna el fileFormat encontrado
     */
    private FileFormat getFileFormat(InformacionArchivoDTO informacionArchivoDTO) {
        FileFormat fileFormat;
        if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLSX)) {
            fileFormat = FileFormat.EXCEL_XLSX;
            return fileFormat;
        } else if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLS)) {
            fileFormat = FileFormat.EXCEL_XLS;
            return fileFormat;
        } else {
            return null;
        }

    }

    /**
     * Metodo encargado retornar un DTO que se construye con los datos que
     * llegan por parametro
     *
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return retorna el resultado hallazgo validacion
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(errorMessage);
        return hallazgo;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#verificarEstructuraArchivoCruce(com.asopagos.dto.InformacionArchivoDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCruce(InformacionArchivoDTO archivo) {
        LOGGER.debug("Inicio servicio verificarEstructuraArchivoCruce(InformacionArchivoDTO)");
        try {
            // Resultado evaluacion archivo cruce
            ResultadoValidacionArchivoDTO resultaDTO = new ResultadoValidacionArchivoDTO();
            ResultadoCruceFOVISDTO resultadoCruceFOVISDTO = new ResultadoCruceFOVISDTO();

            // Se obtiene la referencia del libro
            Workbook workbook = ExcelUtils.getWorkbook(archivo.getDataFile());
            // Lista de nombres de hojas permitidas en el archivo
            List<String> sheetNames = HojaArchivoCruceEnum.getListNames();

            // Se valida que el archivo tenga el formato de archivo valido
            Boolean excepcion = false;
            FileFormat fileFormat = getFileFormat(archivo);
            if (fileFormat == null) {
                excepcion = true;
                resultadoCruceFOVISDTO.setExtensionFallo(true);
            } else {
                // Se valida la cantidad de hojas del archivo
                Long numberOfSheets = ExcelUtils.getNumberOfSheets(workbook);
                if (numberOfSheets != NumerosEnterosConstants.OCHO || !ExcelUtils.validateSheetNames(workbook, sheetNames)) {
                    excepcion = true;
                    resultadoCruceFOVISDTO.setNombresHojasFallo(true);
                }
            }
            // Se verifica si se presento una excepcion T3, lo que impide que se realice la lectura del archivo
            if (excepcion) {
                resultadoCruceFOVISDTO.setExcepcion(excepcion);
                resultaDTO.setResultadoCruceFOVISDTO(resultadoCruceFOVISDTO);
                return resultaDTO;
            }
            // Agregar registro cargue
            CargueArchivoCruceFovisDTO cargue = new CargueArchivoCruceFovisDTO();
            cargue.setCodigoIdentificacionECM(archivo.getIdentificadorDocumento());
            cargue.setFechaCargue(Calendar.getInstance().getTime());
            cargue.setNombreArchivo(archivo.getFileName());
            // Registro de archivo cruce
            Long idCargue = crearCargueArchivoCruce(cargue);
            resultaDTO.setIdCargue(idCargue);
            resultaDTO.setNombreArchivo(archivo.getFileName());
            // Lista de tareas paralelas para la lectura de archivo
            List<Callable<Map<String, Object>>> tareasParalelas = new LinkedList<>();
            List<Callable<Map<String, Object>>> tareasParalelasDos = new LinkedList<>();
            int cont = 0;
            for (HojaArchivoCruceEnum sheet : HojaArchivoCruceEnum.values()) {
                Callable<Map<String, Object>> parallelTask = () -> {
                    //  Lee cada hoja del archivo y la registra en base de datos
                    return readSheetFile(workbook, sheet);
                };
                if (cont > 5) {
                    tareasParalelas.add(parallelTask);
                } else {
                    tareasParalelasDos.add(parallelTask);
                }
                cont++;
            }
            // Lista con lo errores de todas las hojas leidas
            List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgosGeneral = new ArrayList<>();
            // Mapa hojas con errores
            Map<HojaArchivoCruceEnum, List<ResultadoHallazgosValidacionArchivoDTO>> listSheetsFail = new HashMap<>();
            // Indica que el cargue fue correcto
            Boolean estadoCargue = true;
            // Indica el total de registros
            Long totalRegistro = 0L;
            // Indica el total de registros erroneos
            Long totalRegistroError = 0L;
            // Lista de hojas leidas
            List<Future<Map<String, Object>>> resultadosFuturosGenerales = new ArrayList<>();
            List<Future<Map<String, Object>>> resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);
            List<Future<Map<String, Object>>> resultadosFuturosDos = managedExecutorService.invokeAll(tareasParalelasDos);
            resultadosFuturosGenerales.addAll(resultadosFuturos);
            resultadosFuturosGenerales.addAll(resultadosFuturosDos);
            for (Future<Map<String, Object>> future : resultadosFuturosGenerales) {
                Map<String, Object> outDTO = future.get();
                //67306-Inicio
                HojaArchivoCruceEnum sheetName = (HojaArchivoCruceEnum) outDTO.get(ArchivoMultipleCampoConstants.NOMBRE_HOJA);

                List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO
                        .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
                String nombreHoja = "";

                if (!outDTO.containsKey("nombreHoja") || Objects.equals(sheetName.getDescripcion(), "NULL")) {
                    outDTO.put("nombreHoja", "BENEFICIARIOS ARRIENDO");
                    //nombreHoja = "BENEFICIARIOS ARRIENDO";
                    sheetName = HojaArchivoCruceEnum.valueOf("BENEFICIARIO_AR");
                    nombreHoja = sheetName.getDescripcion();
                } else {
                    nombreHoja = sheetName.getDescripcion();
                }

                if (listaHallazgos.size() > 0 && Objects.equals(nombreHoja, "BENEFICIARIOS ARRIENDO")) {
                    //sheetName.getDescripcion().equals("BENEFICIARIOS ARRIENDO")){
                    int i = 0;
                    while (i < listaHallazgos.size()) {
                        if (!listaHallazgos.get(i).getNombreCampo().isEmpty()) {
                            listaHallazgos.remove(listaHallazgos.get(i));
                        }
                    }
                    List<String> candidatos = new ArrayList<>();
                    outDTO.put("listaCandidatos", candidatos);
                }

                if (listaHallazgos.size() > 0 && Objects.equals(nombreHoja, "SISBEN")) {
                    //sheetName.getDescripcion().equals("SISBEN")){
                    int i = 0;
                    while (i < listaHallazgos.size()) {

                        if (!listaHallazgos.get(i).getNombreCampo().isEmpty()) {
                            listaHallazgos.remove(listaHallazgos.get(i));
                        }
                    }

                }
                listaHallazgosGeneral.addAll(listaHallazgos);

                List<InformacionHojaCruceFovisDTO> contenidoHoja = (List<InformacionHojaCruceFovisDTO>) outDTO
                        .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
                // Se verifica si la lectura de la hoja no fue exitosa el cargue seria invalido
                if (!listaHallazgos.isEmpty()) {
                    totalRegistroError += listaHallazgos.size();
                    if (!Objects.equals(nombreHoja, "SISBEN") || !Objects.equals(nombreHoja, "BENEFICIARIOS ARRIENDO")) {
                        //if(!sheetName.getDescripcion().equals("SISBEN")){
                        listSheetsFail.put(sheetName, listaHallazgos);
                        estadoCargue = false;
                    }
                    //67306-Fin
                } else {
                    totalRegistro += contenidoHoja.size();
                    // Almacena en base de datos los registros encontrados
                    //TODO Cruces cambio
                    writeInfoSheet(contenidoHoja, sheetName, idCargue);
                }
            }
            // Se verifica si el cargue paso las validacionesS
            if (estadoCargue) {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                resultaDTO.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
                resultadoCruceFOVISDTO.setListHojasFallidas(listSheetsFail);
                resultadoCruceFOVISDTO.setExcepcion(true);
            }
            resultaDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgosGeneral);
            resultaDTO.setFechaCargue(Calendar.getInstance().getTimeInMillis());
            resultaDTO.setTotalRegistro(totalRegistro);
            resultaDTO.setRegistrosConErrores(totalRegistroError);
            resultaDTO.setRegistrosValidos(totalRegistro - totalRegistroError);
            resultaDTO.setResultadoCruceFOVISDTO(resultadoCruceFOVISDTO);
            resultaDTO.setFileDefinitionId(getFileDefinitionId(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CEDULAS.toString()));
            LOGGER.debug("Finaliza servicio verificarEstructuraArchivoCruce(InformacionArchivoDTO)");
            return resultaDTO;
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio verificarEstructuraArchivoCruce(InformacionArchivoDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Realiza la lectura de un archivo dependiendo de la definicion que se envia
     *
     * @param workbook Referencia archivo excel
     * @param sheet    Informacion hoja
     * @return Resultado de la lectura del archivo
     */
    private Map<String, Object> readSheetFile(Workbook workbook, HojaArchivoCruceEnum sheet) {
        Map<String, Object> outDTO = null;
        try {
            outDTO = ExcelReaderUtil.readSheet(workbook, sheet);
        } catch (Exception e) {
            outDTO = new HashMap<>();
            List<ResultadoHallazgosValidacionArchivoDTO> listError = new ArrayList<>();
            listError.add(crearHallazgo(0L, sheet.getDescripcion(), FileFieldConstants.MENSAJE_ERROR_FILE_PROCESSING + e.getMessage()));
            outDTO.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listError);
        }
        return outDTO;
    }

    /**
     * Escribe el contenido de cada hoja en el dto
     *
     * @param contenidoHoja    Datos salida de la lectura
     * @param sheetName        Nombre hoja a escribir
     * @param informacionCruce Objeto con la informacion del cruce
     * @throws Exception
     */
    //TODO Cambio en validaciones OV
    private void writeInfoSheet(List<InformacionHojaCruceFovisDTO> contenidoHoja, HojaArchivoCruceEnum sheetName, Long idCargue) throws Exception {
        switch (sheetName) {
            case CEDULAS:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisCedulaDTO.class, idCargue);
                break;
            case AFILIADOS:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisAfiliadoDTO.class, idCargue);
                break;
            case BENEFICIARIOS:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisBeneficiarioDTO.class, idCargue);
                break;
            case BENEFICIARIO_AR:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisBeneficiarioArriendoDTO.class, idCargue);
                break;
            case CATASTROS:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisCatastrosDTO.class, idCargue);
                break;
            case NUEVO_HOGAR:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisNuevoHogarDTO.class, idCargue);
                break;
            case FECHAS_DE_CORTE:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisFechasCorteDTO.class, idCargue);
                break;
            case SISBEN:
                createContentSheet(contenidoHoja, CargueArchivoCruceFovisSisbenDTO.class, idCargue);
                break;
            default:
                break;
        }
    }

    /**
     * Crea el contenido de la hoja en el formato del DTO que envia como class y lo registra en base de datos
     *
     * @param contenidoHoja Contenido hoja leida
     * @param clazz         Clase que representa la hoja
     * @throws Exception
     */
    private void createContentSheet(List<InformacionHojaCruceFovisDTO> contenidoHoja, Class<?> clazz, Long idCargue) throws Exception {
        LOGGER.debug("Inicia servicio createContentSheet(List<InformacionHojaCruceFovisDTO>, Class, Long) ");
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            // Se obtiene la conexion al data source
            conn = DatabaseUtil.getCoreConnection();
            // Se construye la clase que representa la hoja
            Constructor<?> constructor = clazz.getConstructor();
            CargueArchivoCruceFovisHojaDTO instanciaHoja = (CargueArchivoCruceFovisHojaDTO) constructor.newInstance();
            // Se contruye la sentencia con el insert generado
            statement = DatabaseUtil.getStatement(conn, instanciaHoja.getInsertStructure());
            // Lista con registros por hoja
            List<CargueArchivoCruceFovisHojaDTO> listPersist = new ArrayList<>();
            // Se itera la lista de informacion de la hoja
            for (InformacionHojaCruceFovisDTO informacionHojaCruceFovisDTO : contenidoHoja) {
                // Se instancia la clase que representa la hoja
                CargueArchivoCruceFovisHojaDTO instancia = (CargueArchivoCruceFovisHojaDTO) constructor.newInstance();
                instancia.convertHojaToDTO(informacionHojaCruceFovisDTO);
                instancia.setIdCargueArchivoCruceFovis(idCargue);
                // Se verifica que la hoja tenga contenido para registralo
                Boolean registrar = false;
                if ((instancia.getNitEntidad() != null && !instancia.getNitEntidad().equals(FileFieldConstants.VALUE_DEFAULT_NIT_ENTIDAD))
                        || (instancia.getNombreEntidad() != null && !instancia.getNombreEntidad().equals(FileFieldConstants.VALUE_DEFAULT_NIT_ENTIDAD))
                        || (instancia.getIdentificacion() != null && !instancia.getIdentificacion().equals(FileFieldConstants.VALUE_DEFAULT_NIT_ENTIDAD))
                        || (instancia.getDocumento() != null && !instancia.getDocumento().equals(FileFieldConstants.VALUE_DEFAULT_NIT_ENTIDAD))
                        || instancia.getNroCedula() != null) {
                    registrar = true;
                }
                if (registrar) {
                    // Se agrega a la lista para persistir
                    listPersist.add(instancia);
                    // Se agregan los valores al statement
                    instancia.processStatement(statement);
                    // Se agrega para la ejecución en batch
                    statement.addBatch();
                }
            }
            if (!listPersist.isEmpty()) {
                statement.executeBatch();
            }
        } catch (Exception e) {
            LOGGER.error("Error - Finaliza servicio createContentSheet(List<InformacionHojaCruceFovisDTO>, Class, Long)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
            DatabaseUtil.closeStatement(statement);
            DatabaseUtil.closeConnection(conn);
            LOGGER.debug("Finaliza servicio createContentSheet(List<InformacionHojaCruceFovisDTO>, Class, Long)");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#crearCargueArchivoCruce(com.asopagos.dto.CargueArchivoCruceFovisDTO)
     */
    @Override
    public Long crearCargueArchivoCruce(CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO) {
        LOGGER.debug("Inicia servicio crearCargueArchivoCruce(CargueArchivoCruceFovisDTO)");
        CargueArchivoCruceFovis cargueArchivoCruceFovis = cargueArchivoCruceFovisDTO.convertToEntity();
        if (cargueArchivoCruceFovis.getIdCargueArchivoCruceFovis() != null) {
            entityManager.merge(cargueArchivoCruceFovis);
        } else {
            entityManager.persist(cargueArchivoCruceFovis);
        }
        LOGGER.debug("Finaliza servicio crearCargueArchivoCruce(CargueArchivoCruceFovisDTO)");
        return cargueArchivoCruceFovis.getIdCargueArchivoCruceFovis();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCargueArchivoCruce(java.lang.Long)
     */
    @Override
    public CargueArchivoCruceFovisDTO consultarCargueArchivoCruce(Long idCargue) {
        LOGGER.debug("Inicia servicio consultarCargueArchivoCruce(Long)");
        try {
            // Consulta la informacion basica del archivo
            CargueArchivoCruceFovis cargue = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_CRUCE_POR_ID, CargueArchivoCruceFovis.class)
                    .setParameter("idCargue", idCargue).getSingleResult();
            LOGGER.debug("Finaliza servicio consultarCargueArchivoCruce(Long)");
            return CargueArchivoCruceFovisDTO.convertEntityToDTO(cargue);
        } catch (NonUniqueResultException | NoResultException e) {
            LOGGER.debug("Registro no encontrado - Finaliza servicio consultarCargueArchivoCruce(Long)");
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarInformacionArchivoCruces(java.lang.Long)
     */
    @Override
    public List<Object[]> consultarInformacionArchivoCruces(Long idCargue) {
        LOGGER.debug("Inicia servicio consultarInformacionArchivoCruces(Long)");

        List<Object[]> informacionCruce = null;
        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(
                            NamedQueriesConstants.STORED_PROCEDURE_FOVIS_CONSULTAR_INFORMACION_CRUCES);
            query.setParameter("idCargue", BigInteger.valueOf(idCargue));
            informacionCruce = query.getResultList();
            return informacionCruce;
        } catch (Exception e) {
            LOGGER.info(" :: Hubo un error en el SP: " + e);
        }
        LOGGER.debug("Finaliza servicio consultarInformacionArchivoCruces(Long)");
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarContenidoArchivoCargueFovis(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InformacionCruceFovisDTO consultarContenidoArchivoCargueFovis(Long idCargue) {
        LOGGER.debug("Inicia servicio consultarContenidoArchivoCargueFovis(Long)");
        InformacionCruceFovisDTO cruceFovisDTO = new InformacionCruceFovisDTO();

        // Consulta la informacion de la hoja Afiliado
        List<CargueArchivoCruceFovisAfiliado> afiliado = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_AFILIADO, CargueArchivoCruceFovisAfiliado.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisAfiliadoDTO> listAfiliados = new ArrayList<>();
        for (CargueArchivoCruceFovisAfiliado entity : afiliado) {
            listAfiliados.add(CargueArchivoCruceFovisAfiliadoDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setAfiliados(listAfiliados);

        // Consulta la informacion de la hoja Beneficiario
        List<CargueArchivoCruceFovisBeneficiario> beneficiario = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_BENEFICIARIO, CargueArchivoCruceFovisBeneficiario.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisBeneficiarioDTO> listBeneficiario = new ArrayList<>();
        for (CargueArchivoCruceFovisBeneficiario entity : beneficiario) {
            listBeneficiario.add(CargueArchivoCruceFovisBeneficiarioDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setBeneficiarios(listBeneficiario);

        // Consulta la informacion de la hoja Beneficiario Arriendo
        List<CargueArchivoCruceFovisBeneficiarioArriendo> beneficiarioArriendo = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_BENEFICIARIO_ARRIENDO, CargueArchivoCruceFovisBeneficiarioArriendo.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisBeneficiarioArriendoDTO> listBeneficiarioArriendo = new ArrayList<>();
        for (CargueArchivoCruceFovisBeneficiarioArriendo entity : beneficiarioArriendo) {
            listBeneficiarioArriendo.add(CargueArchivoCruceFovisBeneficiarioArriendoDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setBeneficiariosArriendo(listBeneficiarioArriendo);

        // Consulta la informacion de la hoja Catastros
        List<CargueArchivoCruceFovisCatastros> catastros = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CATASTROS, CargueArchivoCruceFovisCatastros.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCatastrosDTO> listCatastros = new ArrayList<>();
        for (CargueArchivoCruceFovisCatastros entity : catastros) {
            listCatastros.add(CargueArchivoCruceFovisCatastrosDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCatastros(listCatastros);

        // Consulta la informacion de la hoja CatAnt
        List<CargueArchivoCruceFovisCatAnt> catAnt = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CAT_ANT, CargueArchivoCruceFovisCatAnt.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCatAntDTO> listCatAnt = new ArrayList<>();
        for (CargueArchivoCruceFovisCatAnt entity : catAnt) {
            listCatAnt.add(CargueArchivoCruceFovisCatAntDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCatAnt(listCatAnt);

        // Consulta la informacion de la hoja CatBog
        List<CargueArchivoCruceFovisCatBog> catBog = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CAT_BOG, CargueArchivoCruceFovisCatBog.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCatBogDTO> listCatBog = new ArrayList<>();
        for (CargueArchivoCruceFovisCatBog entity : catBog) {
            listCatBog.add(CargueArchivoCruceFovisCatBogDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCatBog(listCatBog);

        // Consulta la informacion de la hoja CatCali
        List<CargueArchivoCruceFovisCatCali> catCali = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CAT_CALI, CargueArchivoCruceFovisCatCali.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCatCaliDTO> listCatCali = new ArrayList<>();
        for (CargueArchivoCruceFovisCatCali entity : catCali) {
            listCatCali.add(CargueArchivoCruceFovisCatCaliDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCatCali(listCatCali);

        // Consulta la informacion de la hoja CatMed
        List<CargueArchivoCruceFovisCatMed> catMed = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CAT_MED, CargueArchivoCruceFovisCatMed.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCatMedDTO> listCatMed = new ArrayList<>();
        for (CargueArchivoCruceFovisCatMed entity : catMed) {
            listCatMed.add(CargueArchivoCruceFovisCatMedDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCatMed(listCatMed);

        // Consulta la informacion de la hoja Cedula
        List<CargueArchivoCruceFovisCedula> cedula = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_CEDULA, CargueArchivoCruceFovisCedula.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisCedulaDTO> listCedulas = new ArrayList<>();
        for (CargueArchivoCruceFovisCedula entity : cedula) {
            listCedulas.add(CargueArchivoCruceFovisCedulaDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setCedulas(listCedulas);

        // Consulta la informacion de la hoja FechasCorte
        List<CargueArchivoCruceFovisFechasCorte> fechasCorte = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_FECHAS_CORTE, CargueArchivoCruceFovisFechasCorte.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisFechasCorteDTO> listFechasCorte = new ArrayList<>();
        for (CargueArchivoCruceFovisFechasCorte entity : fechasCorte) {
            listFechasCorte.add(CargueArchivoCruceFovisFechasCorteDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setFechaCorte(listFechasCorte);

        // Consulta la informacion de la hoja IGAC
        List<CargueArchivoCruceFovisIGAC> igac = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_IGAC, CargueArchivoCruceFovisIGAC.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisIGACDTO> listIgac = new ArrayList<>();
        for (CargueArchivoCruceFovisIGAC entity : igac) {
            listIgac.add(CargueArchivoCruceFovisIGACDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setIgac(listIgac);

        // Consulta la informacion de la hoja NuevoHogar
        List<CargueArchivoCruceFovisNuevoHogar> nuevoHogar = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_NUEVO_HOGAR, CargueArchivoCruceFovisNuevoHogar.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisNuevoHogarDTO> listNuevoHogar = new ArrayList<>();
        for (CargueArchivoCruceFovisNuevoHogar entity : nuevoHogar) {
            listNuevoHogar.add(CargueArchivoCruceFovisNuevoHogarDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setNuevoHogar(listNuevoHogar);

        // Consulta la informacion de la hoja Reunidos
        List<CargueArchivoCruceFovisReunidos> reunidos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_REUNIDOS, CargueArchivoCruceFovisReunidos.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisReunidosDTO> listReunidos = new ArrayList<>();
        for (CargueArchivoCruceFovisReunidos entity : reunidos) {
            listReunidos.add(CargueArchivoCruceFovisReunidosDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setReunidos(listReunidos);

        // Consulta la informacion de la hoja Sisben
        List<CargueArchivoCruceFovisSisben> sisben = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_SISBEN, CargueArchivoCruceFovisSisben.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisSisbenDTO> listSisben = new ArrayList<>();
        for (CargueArchivoCruceFovisSisben entity : sisben) {
            listSisben.add(CargueArchivoCruceFovisSisbenDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setSisben(listSisben);

        // Consulta la informacion de la hoja Unidos
        List<CargueArchivoCruceFovisUnidos> unidos = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTA_INFO_HOJA_UNIDOS, CargueArchivoCruceFovisUnidos.class)
                .setParameter("idCargue", idCargue).getResultList();
        List<CargueArchivoCruceFovisUnidosDTO> listUnidos = new ArrayList<>();
        for (CargueArchivoCruceFovisUnidos entity : unidos) {
            listUnidos.add(CargueArchivoCruceFovisUnidosDTO.convertEntityToDTO(entity));
        }
        cruceFovisDTO.setUnidos(listUnidos);

        LOGGER.debug("Finaliza servicio consultarContenidoArchivoCargueFovis(Long)");
        return cruceFovisDTO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#crearRegistroCruce(java.util.List)
     */
    @Override
    public void crearRegistroCruce(List<CruceDetalleDTO> listCruceDetalle) {
        LOGGER.debug("Inicia servicio crearRegistroCruce(List<CruceDetalleDTO>)");
        // Se itera la lista de cruces detalle enviada
        for (CruceDetalleDTO cruceDetalleDTO : listCruceDetalle) {
            if (cruceDetalleDTO.getCruce() != null) {
                // Registrar informacion de cruce
                Long idCruce = registrarActualizarCruce(cruceDetalleDTO.getCruce());
                cruceDetalleDTO.getCruce().setIdCruce(idCruce);
                // Registrar infromacion detalle cruce
                registrarCruceDetalle(cruceDetalleDTO);
            }
        }
    }

    /**
     * Registrar informacion de cruce
     *
     * @param cruceDTO Informacion de cruce
     * @return Identificador de cruce
     */
    private Long registrarActualizarCruce(CruceDTO cruceDTO) {
        Cruce cruce = cruceDTO.convertToEntity();
        if (cruce.getIdCruce() != null) {
            entityManager.merge(cruce);
        } else {
            entityManager.persist(cruce);
        }
        return cruce.getIdCruce();
    }

    /**
     * Registrar informacion detalle cruce
     *
     * @param cruceDetalleDTO Informacion detalle cruce
     */
    private void registrarCruceDetalle(CruceDetalleDTO cruceDetalleDTO) {
        CruceDetalle cruceDetalle = cruceDetalleDTO.convertToEntity();
        if (cruceDetalle.getIdCruceDetalle() != null) {
            entityManager.merge(cruceDetalle);
        } else {
            entityManager.persist(cruceDetalle);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCruceFiltro(com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum,
     * java.lang.String)
     */
    @Override
    public List<CruceDetalleDTO> consultarCruceFiltro(TipoInformacionCruceEnum tipo, String identificacion) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLE_CRUCE_POR_TIPO_INFORMACION_IDENTIFICACION,
                        CruceDetalleDTO.class)
                .setParameter("tipo", tipo).setParameter("identificacion", identificacion).getResultList();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCruces(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CruceDetalleDTO> consultarCruces(Long idSolicitud, TipoCruceEnum tipoCruce) {
        LOGGER.debug("Inicia servicio consultarCruces(Long)");
        // Se consulta la soliciutd de postulacion por el id solicitud de verificacion
        Long idSolGestionCruce = null;
        List<Object[]> listSolicitudes = (List<Object[]>) entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POSTULACION_BY_ID_SOL_GLOBAL_SOL_GESTION_CRUCE_O_VERIFICA)
                .setParameter("idSolicitud", idSolicitud).getResultList();
        if (listSolicitudes != null && !listSolicitudes.isEmpty()) {
            Object[] infoSolicitud = listSolicitudes.get(0);
            SolicitudPostulacion solPost = (SolicitudPostulacion) infoSolicitud[0];
            String tipoCruceResult = (String) infoSolicitud[1];
            BigInteger idSolicitudGestion = (BigInteger) infoSolicitud[2];
            if (TipoCruceEnum.INTERNO.name().equals(tipoCruceResult)
                    || TipoCruceEnum.EXTERNO.name().equals(tipoCruceResult)) {
                idSolGestionCruce = idSolicitudGestion.longValue();
            }
            idSolicitud = solPost.getSolicitudGlobal().getIdSolicitud();
        }
        List<CruceDetalleDTO> listCruces = new ArrayList<>();
        if (idSolGestionCruce != null) {
            listCruces = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CRUCE_POR_SOLICITUD_GESTION_ID, CruceDetalleDTO.class)
                    .setParameter("idSolicitudGestion", idSolGestionCruce).getResultList();
        } else {
            listCruces = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CRUCE_POR_SOLICITUD_ID, CruceDetalleDTO.class)
                    .setParameter("idSolicitud", idSolicitud).setParameter("tipoCruce", tipoCruce).getResultList();
        }
        LOGGER.debug("Finaliza servicio consultarCruces(Long)");
        return listCruces;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarNumeroPostulacionPersona(java.lang.String)
     */
    @Override
    public String consultarNumeroPostulacionPersona(String identificacion) {
        LOGGER.debug("Inicia servicio consultarNumeroPostulacionPersona(String)");
        // Contiene el numero de postualacion 
        String numeroPostulacion = null;
        // Lista de estado de solicitud no permitidos
        List<EstadoSolicitudPostulacionEnum> estadosSolicitudPostulacion = new ArrayList<>();
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA);
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
        // Estado Hogar no permitido
        EstadoHogarEnum estadoHogar = EstadoHogarEnum.RECHAZADO;
        // Tipo identificacion permitido
        TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.CEDULA_CIUDADANIA;

        // Se consulta la persona como jefe hogar asociado a una postulacion, si no se encuentra se busca como integrante de hogar
        List<String> listPostulaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NRO_POSTULACION_PERSONA_JEFE_HOGAR, String.class)
                .setParameter("estadoHogar", estadoHogar).setParameter("estadosSolicitudPostulacion", estadosSolicitudPostulacion)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", identificacion)
                .getResultList();
        if (listPostulaciones == null || listPostulaciones.isEmpty()) {
            listPostulaciones = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_NRO_POSTULACION_PERSONA_INTEGRANTE_HOGAR, String.class)
                    .setParameter("estadoHogar", estadoHogar).setParameter("estadosSolicitudPostulacion", estadosSolicitudPostulacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", identificacion)
                    .getResultList();
        }
        if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
            numeroPostulacion = listPostulaciones.iterator().next();
        }
        LOGGER.debug("Finaliza servicio consultarNumeroPostulacionPersona(String)");
        return numeroPostulacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCrucesPorNumeroPostulacionArchivo(java.lang.Long, java.lang.String)
     */
    @Override
    public List<CruceDetalleDTO> consultarCrucesNuevosPorNumeroPostulacionIdCargueArchivo(Long idCargue, String numeroPostulacion) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CRUCE_POR_NRO_POSTULACION_ID_CARGUE, CruceDetalleDTO.class)
                .setParameter("estadoCruce", EstadoCruceEnum.NUEVO).setParameter("numeroPostulacion", numeroPostulacion)
                .setParameter("idCargue", idCargue).getResultList();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarSolicitudPostulacionPorNumeroPostulacion(java.lang.String)
     */
    @Override
    public SolicitudPostulacionModeloDTO consultarSolicitudPostulacionPorNumeroPostulacion(String numeroPostulacion) {
        LOGGER.debug("Inicia servicio consultarSolicitudPostulacionPorNumeroPostulacion(String)");
        try {
            SolicitudPostulacion solicitudPostulacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POSTULACION_BY_NRO_POSTULACION, SolicitudPostulacion.class)
                    .setParameter("numeroPostulacion", numeroPostulacion).getSingleResult();
            LOGGER.debug("Finaliza servicio consultarSolicitudPostulacionPorNumeroPostulacion(String)");
            SolicitudPostulacionModeloDTO solicitudPostulacionModeloDTO = new SolicitudPostulacionModeloDTO(solicitudPostulacion);
            return solicitudPostulacionModeloDTO;
        } catch (NoResultException | NonUniqueResultException e) {
            LOGGER.debug("No Result - Finaliza servicio consultarSolicitudPostulacionPorNumeroPostulacion(String)");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, SolicitudPostulacionModeloDTO> consultarSolicitudPostulacionPorNumeroCedula(List<String> listNumeroCedula) {
        LOGGER.debug("Inicia servicio consultarSolicitudPostulacionPorNumeroPostulacion(List)");

        Map<String, SolicitudPostulacionModeloDTO> mapPersonaPostulacion = new HashMap<>();
        List<String> listEstadoHogarPermitido = new ArrayList<>();
        listEstadoHogarPermitido.add(EstadoHogarEnum.HABIL.name());
        listEstadoHogarPermitido.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        List<String> listEstadoHogarRestricion = new ArrayList<>();
        listEstadoHogarRestricion.add(EstadoHogarEnum.POSTULADO.name());
        // Estado de solicitud de postulacion no permitidos
        List<String> estadosSolicitudPostulacion = new ArrayList<String>();
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_RECHAZADA.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.ASIGNADA_AL_BACK.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_ESCALADA_POR_BACK.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.EN_ANALISIS_ESPECIALIZADO.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.GESTIONADA_POR_ESPECIALISTA.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.PENDIENTE_ENVIO_AL_BACK.name());
        estadosSolicitudPostulacion.add(EstadoSolicitudPostulacionEnum.POSTULACION_RADICADA.name());

        int registrosPag = 1000;
        int contRegistros = 0;
        int cantCedulas = listNumeroCedula.size();

        ArrayList<String> paginaCed = new ArrayList<>();

        do {
            for (int i = 0; i < registrosPag && contRegistros < cantCedulas; i++) {
                paginaCed.add(listNumeroCedula.get(contRegistros));
                contRegistros++;
            }

            List<Object[]> listResult = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_SOLICITUD_POSTULACION_BY_NUMERO_IDENTIFICACION)
                    .setParameter("estadoHogar", listEstadoHogarPermitido).setParameter("estadoHogarRestriccion", listEstadoHogarRestricion)
                    .setParameter("tipoIdentificacion", TipoIdentificacionEnum.CEDULA_CIUDADANIA.name()).setParameter("listNumeroIdentificacion", paginaCed)
                    .setParameter("estadosSolicitudPostulacion", estadosSolicitudPostulacion)
                    .setParameter("estadoPersonaHogar", EstadoFOVISHogarEnum.ACTIVO.name()).getResultList();

            for (Object[] objects : listResult) {
                SolicitudPostulacionModeloDTO solicitudPostulacion = new SolicitudPostulacionModeloDTO();
                BigInteger idSol = (BigInteger) objects[0];
                solicitudPostulacion.setIdSolicitudPostulacion(idSol.longValue());
                BigInteger idPos = (BigInteger) objects[2];
                solicitudPostulacion.setIdPostulacionFOVIS(idPos.longValue());
                String estado = (String) objects[3];
                solicitudPostulacion.setEstadoSolicitud(EstadoSolicitudPostulacionEnum.valueOf(estado));
                String nroCedula = (String) objects[5];
                String numeroRadicacion = (String) objects[6];
                solicitudPostulacion.setNumeroRadicacion(numeroRadicacion);
                mapPersonaPostulacion.put(nroCedula, solicitudPostulacion);
            }

            paginaCed.clear();

        } while (contRegistros < cantCedulas);


        LOGGER.debug("Finaliza servicio consultarSolicitudPostulacionPorNumeroPostulacion(List)");
        return mapPersonaPostulacion;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#crearRegistroSolicituGestionCruce(java.util.List)
     */
    @Override
    public List<SolicitudGestionCruceDTO> crearRegistroListaSolicituGestionCruce(List<SolicitudGestionCruceDTO> listSolicitudGestionCruce) {
        LOGGER.debug("Inicia servicio crearRegistroListaSolicituGestionCruce(List<SolicitudGestionCruceDTO>)");
        // Se itera la lista de solicitud gestion cruce enviada
        for (SolicitudGestionCruceDTO solicitudGestionCruceDTO : listSolicitudGestionCruce) {
            crearRegistroSolicituGestionCruce(solicitudGestionCruceDTO);
        }
        LOGGER.debug("Finaliza servicio crearRegistroListaSolicituGestionCruce(List<SolicitudGestionCruceDTO>)");
        return listSolicitudGestionCruce;
    }

    @Override
    public SolicitudGestionCruceDTO crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        LOGGER.debug("Inicia servicio crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO)");
        // Se registra la solicitud gestion cruce enviada
        SolicitudGestionCruce solicitud = registrarSolicitudGestionCruce(solicitudGestionCruceDTO);
        solicitudGestionCruceDTO.setIdSolicitudGestionCruce(solicitud.getIdSolicitudGestionCruce());
        // Se asocia los cruces a la solicitud de gestion
        if (solicitudGestionCruceDTO.getListCrucesAsociados() != null
                && !solicitudGestionCruceDTO.getListCrucesAsociados().isEmpty()) {
            asociarSolicitudGestionCruce(solicitudGestionCruceDTO.getListCrucesAsociados(), solicitudGestionCruceDTO);
        }
        solicitudGestionCruceDTO.convertEntityToDTO(solicitud);
        LOGGER.debug("Finaliza servicio crearRegistroSolicituGestionCruce(SolicitudGestionCruceDTO)");
        return solicitudGestionCruceDTO;
    }

    /**
     * Registra la solicitud de gestion de cruce
     *
     * @param solicitudGestionCruceDTO Informacion de solicitud a registrar
     * @return solicitud guardada
     */
    private SolicitudGestionCruce registrarSolicitudGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        SolicitudGestionCruce solicitudGestionCruce = solicitudGestionCruceDTO.convertToEntity();
        if (solicitudGestionCruce.getIdSolicitudGestionCruce() != null) {
            entityManager.merge(solicitudGestionCruce);
        } else {
            entityManager.persist(solicitudGestionCruce);
        }

        return solicitudGestionCruce;
    }

    /**
     * Asocia los cruces a la solicitud gestion de cruces creada
     *
     * @param listCruceDetalle         Lista de cruces asociar
     * @param solicitudGestionCruceDTO Solicitud de gestion de cruces
     */
    private void asociarSolicitudGestionCruce(List<CruceDetalleDTO> listCruceDetalle, SolicitudGestionCruceDTO solicitudGestionCruceDTO) {
        // Itera los cruces para asignar la solicitud gestion cruce
        for (CruceDetalleDTO cruceDetalleDTO : listCruceDetalle) {
            cruceDetalleDTO.getCruce().setIdSolicitudGestionCruce(solicitudGestionCruceDTO.getIdSolicitudGestionCruce());
        }
        // Se crean los cruces
        crearRegistroCruce(listCruceDetalle);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarSolicitudGestionCruce(java.lang.Long)
     */
    @Override
    public SolicitudGestionCruceDTO consultarSolicitudGestionCruce(Long idSolicitud) {
        LOGGER.debug("Inicia servicio consultarSolicitudGestionCruce(Long)");
        try {
            SolicitudGestionCruce solicitudGestion = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_CRUCE_BY_ID, SolicitudGestionCruce.class)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            LOGGER.debug("Finaliza servicio consultarSolicitudGestionCruce(Long)");
            return new SolicitudGestionCruceDTO(solicitudGestion);
        } catch (NonUniqueResultException | NoResultException e) {
            LOGGER.debug("Registro no encontrado - Finaliza servicio consultarCargueArchivoCruce(Long)");
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#actualizarSolicitudGestionCruce(com.asopagos.dto.SolicitudGestionCruceDTO)
     */
    @Override
    public SolicitudGestionCruceDTO actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO solicitudGestionCruce) {
        LOGGER.debug("Inicia servicio actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO)");
        SolicitudGestionCruce solicitud = registrarSolicitudGestionCruce(solicitudGestionCruce);
        LOGGER.debug("Finaliza servicio actualizarSolicitudGestionCruce(SolicitudGestionCruceDTO)");
        return new SolicitudGestionCruceDTO(solicitud);
    }

    @Override
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorPostulacionTipoCruce(Long idSolicitudPostulacion,
                                                                                                TipoCruceEnum tipoCruce) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION_TIPO_CRUCE,
                        SolicitudGestionCruceDTO.class)
                .setParameter("idSolicitudPostulacion", idSolicitudPostulacion).setParameter("tipoCruce", tipoCruce).getResultList();
    }

    @Override
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorSolicitudPostulacion(Long idSolicitudPostulacion) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION,
                        SolicitudGestionCruceDTO.class)
                .setParameter("idSolicitudPostulacion", idSolicitudPostulacion).getResultList();
    }

    @Override
    public void actualizarSolicitudesGestionCruceASubsanadas(Long idSolicitudPostulacion) {
        LOGGER.debug("Inicia servicio actualizarSolicitudesGestionCruceASubsanadas(Long)");
        List<SolicitudGestionCruce> solicitudesGestion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION_ESTADO_CRUCEHOGAR,
                        SolicitudGestionCruce.class)
                .setParameter("idSolicitudPostulacion", idSolicitudPostulacion)
                .setParameter("estadoCruceHogar", EstadoCruceHogarEnum.CRUCE_SUBSANADO_PENDIENTE_VERIFICACION).getResultList();
        if (solicitudesGestion != null && !solicitudesGestion.isEmpty()) {
            for (SolicitudGestionCruce solicitudGestionCruce : solicitudesGestion) {
                solicitudGestionCruce.setEstadoCruceHogar(EstadoCruceHogarEnum.CRUCES_SUBSANADOS);
                entityManager.merge(solicitudGestionCruce);
            }
        }
        LOGGER.debug("Finaliza servicio actualizarSolicitudesGestionCruceASubsanadas(Long)");
    }

    /* (non-Javadoc)
     * @see com.asopagos.fovis.service.FovisCargueService#
     * consultarSolicitudGestionTipoCruceEstado
     * (TipoCruceEnum, EstadoSolicitudGestionCruceEnum, EstadoCruceHogarEnum)
     */
    @Override
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionTipoCruceEstado(TipoCruceEnum tipoCruce,
                                                                                   EstadoSolicitudGestionCruceEnum estadoSolicitudGestion, EstadoCruceHogarEnum estadoCruceHogar) {
        LOGGER.debug("Inicia servicio consultarSolicitudGestionCrucePorPostulacionTipoCruce(Long)");
        List<SolicitudGestionCruceDTO> solicitudes = new ArrayList<>();
        List<SolicitudGestionCruce> solicitudesGestion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOL_GESTION_CRUCE_BY_TIPO_CRUCE_ESTADO, SolicitudGestionCruce.class)
                .setParameter("tipoCruce", tipoCruce)
                .setParameter("estadoSolicitudGestionCruce", estadoSolicitudGestion)
                .setParameter("estadoCruceHogar", estadoCruceHogar).getResultList();
        if (solicitudesGestion != null && !solicitudesGestion.isEmpty()) {
            for (SolicitudGestionCruce solicitudGestionCruce : solicitudesGestion) {
                SolicitudGestionCruceDTO solicitudGestionCruceDTO = new SolicitudGestionCruceDTO(solicitudGestionCruce);
                solicitudes.add(solicitudGestionCruceDTO);
            }
        }
        LOGGER.debug("Finaliza servicio consultarSolicitudGestionCrucePorPostulacionTipoCruce(Long)");
        return solicitudes;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCrucesPorSolicitudPostulacion(Long)
     */
    @Override
    public List<CruceDTO> consultarCrucePorSolicitudPostulacion(Long idSolicitudPostulacion) {
        LOGGER.debug("Inicia servicio consultarCrucePorSolicitudGestionCruce(Long)");
        List<CruceDTO> crucesDTO = new ArrayList<>();
        List<Cruce> cruces = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CRUCE_BY_SOLICITUD_GESTION_CRUCE, Cruce.class)
                .setParameter("idSolicitudPostulacion", idSolicitudPostulacion).getResultList();
        if (cruces != null && !cruces.isEmpty()) {
            for (Cruce cruce : cruces) {
                crucesDTO.add(CruceDTO.convertEntityToDTO(cruce));
            }
        }
        LOGGER.debug("Finaliza servicio consultarCrucePorSolicitudGestionCruce(Long)");
        return crucesDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#registrarActualizarCruces(List)
     */
    @Override
    public void registrarActualizarCruces(List<CruceDTO> crucesDTO) {
        LOGGER.debug("Inicia servicio registrarActualizarCruce(CruceDTO)");
        for (CruceDTO cruceDTO : crucesDTO) {
            registrarActualizarCruce(cruceDTO);
        }
        LOGGER.debug("Finaliza servicio registrarActualizarCruce(CruceDTO)");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCrucesPorProcesoAsincrono(java.lang.Long)
     */
    @Override
    public List<CruceDetalleDTO> consultarCrucesPorProcesoAsincrono(Long idProcesoAsincrono) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_CRUCES_POR_EJECUCION, CruceDetalleDTO.class)
                .setParameter("idProcesoEjecucionAsincrono", idProcesoAsincrono)
                .getResultList();
    }

    @Override
    public List<SolicitudGestionCruceDTO> consultarSolicitudGestionCrucePorListPostulacionTipoCruce(List<Long> listIdSolicitudPostulacion,
                                                                                                    TipoCruceEnum tipoCruce) {
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_CRUCE_BY_LIST_ID_SOL_POST_TIPO_CRUCE,
                        SolicitudGestionCruceDTO.class)
                .setParameter("idSolicitudPostulacion", listIdSolicitudPostulacion).setParameter("tipoCruce", tipoCruce)
                .getResultList();
    }

    @Override
    public List<SolicitudPostulacionModeloDTO> consultarSolicitudPostulacionPorListaNumeroPostulacion(List<String> listNumeroPostulacion) {

        List<SolicitudPostulacionModeloDTO> solicitudPostulacionModeloDTOS = new ArrayList<>();

        int registrosPag = 2000;
        int contRegistros = 0;
        int cantCedulas = listNumeroPostulacion.size();

        ArrayList<String> paginaCed = new ArrayList<>();

        do {
            for (int i = 0; i < registrosPag && contRegistros < cantCedulas; i++) {
                paginaCed.add(listNumeroPostulacion.get(contRegistros));
                contRegistros++;
            }
            List<SolicitudPostulacionModeloDTO> postulacionModeloDTOS = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_POSTULACION_BY_LIST_NUMERO_POSTULACION, SolicitudPostulacionModeloDTO.class)
                    .setParameter("numeroPostulacion", paginaCed).getResultList();

            solicitudPostulacionModeloDTOS.addAll(postulacionModeloDTOS);

            paginaCed.clear();

        } while (contRegistros < cantCedulas);

        return solicitudPostulacionModeloDTOS;
    }


    /* (non-Javadoc)
     * @see com.asopagos.fovis.service.FovisCargueService#consultarCruceTodosTiposInformacion(java.lang.String)
     */
    @Override
    public List<CruceDetalleDTO> consultarCruceTodosTiposInformacion(String identificacion) {
        return entityManager.createNamedQuery(
                NamedQueriesConstants.CONSULTAR_DETALLE_CRUCE_POR_TODOS_TIPOS_INFORMACION_IDENTIFICACION,
                CruceDetalleDTO.class).setParameter("identificacion", identificacion).getResultList();
    }

    @Override
    public SolicitudGestionCruceDTO consultarSolicitudGestionCrucePorSolicitudGlobal(Long idSolicitudGlobal) {
        LOGGER.debug("Inicia servicio consultarSolicitudGestionCrucePorSolicitudGlobal(Long)");
        try {
            SolicitudGestionCruce sol = (SolicitudGestionCruce) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_GESTION_CRUCE_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
            return new SolicitudGestionCruceDTO(sol);
        } catch (NonUniqueResultException | NoResultException e) {
            LOGGER.debug("Registro no encontrado - Finaliza servicio consultarSolicitudGestionCrucePorSolicitudGlobal(Long)");
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.fovis.service.FovisCargueService#actualizarEstadoSolicitudGestionCruce(Long, EstadoSolicitudGestionCruceEnum)
     */
    @Override
    public void actualizarEstadoSolicitudGestionCruce(Long idSolicitudGlobal, EstadoSolicitudGestionCruceEnum estadoSolicitud) {
        LOGGER.debug("Se inicia el servicio de actualizarEstadoSolicitudGestionCruce(Long, EstadoSolicitudAsignacionEnum)");
        SolicitudGestionCruceDTO solicitudGestionCruceFovisModeloDTO = consultarSolicitudGestionCrucePorSolicitudGlobal(
                idSolicitudGlobal);
        SolicitudGestionCruce solicitudGestionCruce = solicitudGestionCruceFovisModeloDTO.convertToEntity();
        entityManager.merge(solicitudGestionCruce);
        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudGestionCruceEnum.CERRADA.equals(estadoSolicitud)) {
            ResultadoProcesoEnum resultadoProceso = ResultadoProcesoEnum.APROBADA;
            if (EstadoSolicitudGestionCruceEnum.DESISTIDA.equals(solicitudGestionCruce.getEstadoSolicitudGestionCruce())
                    || EstadoSolicitudGestionCruceEnum.CANCELADA.equals(solicitudGestionCruce.getEstadoSolicitudGestionCruce())) {
                resultadoProceso = ResultadoProcesoEnum.valueOf(solicitudGestionCruce.getEstadoSolicitudGestionCruce().name());
            }
            solicitudGestionCruce.getSolicitudGlobal().setResultadoProceso(resultadoProceso);
        }
        solicitudGestionCruce.setEstadoSolicitudGestionCruce(estadoSolicitud);
        entityManager.merge(solicitudGestionCruce);
        LOGGER.debug("Finaliza actualizarEstadoSolicitudGestionCruce(Long, EstadoSolicitudAsignacionEnum)");
    }

}
