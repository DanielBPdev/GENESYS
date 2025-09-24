package com.asopagos.entidaddescuento.service.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constants.*;
import com.asopagos.dto.*;
import com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore;
import com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.entidaddescuento.constants.CamposArchivoConstants;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import com.asopagos.entidaddescuento.load.source.ArchivoEntidadDescuentoFilterDTO;
import com.asopagos.entidaddescuento.service.EntidadDescuentoService;
import com.asopagos.enumeraciones.core.ErroresConsolaEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import co.com.heinsohn.lion.common.enums.Protocolo;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.enums.FileGeneratedState;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;

/**
 * <b>Descripcion:</b> Clase que contiene la implementacion del microservicio de Entidades de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU-311-440 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@Stateless
public class EntidadDescuentoBusiness implements EntidadDescuentoService {
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(EntidadDescuentoBusiness.class);

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasEntidadDescuento;
    
    /** Inject del EJB para consultas en modelo Subsidio */
    @Inject
    private IConsultasModeloSubsidio consultasModeloSubsidio;
    
    /**Interfaz de validación para archivos mediante Lion Framework*/
    @Inject
    private FileLoaderInterface fileLoader;
    
    /**Interfaz de generación de archivos mediante Lion Framework*/
    @Inject
    private FileGenerator fileGenerator;
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#consultarEntidadDescuentoId(java.lang.Long)
     */
    @Override
    public EntidadDescuentoModeloDTO consultarEntidadDescuento(String codigoEntidadDescuento) {
        
        String firmaServicio = "EntidadDescuentoBusiness.consultarEntidadDescuentoId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        EntidadDescuentoModeloDTO entidadDescuentoModeloDTO = null;
        
        Long codigoConvenio = null;
        if (codigoEntidadDescuento != null && !codigoEntidadDescuento.equals("")) {

            if (codigoEntidadDescuento.length() == 4) {
                try {
                    codigoConvenio = Long.parseLong(codigoEntidadDescuento);
                } catch (NumberFormatException e) {
                    return entidadDescuentoModeloDTO;
                }
            }
            else {
                //se devuelve un null ya que el código no es de cuatro caracteres
                return entidadDescuentoModeloDTO;
            }
        }
        //se busca la entidad de descuento
        //al metodo se le envia dos parametros, al nombre se le envia un objeto vacio para poder realizar la consulta
        List<EntidadDescuentoModeloDTO>entidadesDescuento = consultasEntidadDescuento.consultarEntidadDescuento(codigoConvenio, null);
                
        if(entidadesDescuento != null && entidadesDescuento.size() == 1) entidadDescuentoModeloDTO = entidadesDescuento.get(0);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return entidadDescuentoModeloDTO;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#consultarEntidadDescuentoId(java.lang.Long)
     */
    @Override
    public EntidadDescuentoModeloDTO consultarEntidadDescuentoId(Long idEntidadDescuento) {
        String firmaServicio = "EntidadDescuentoBusiness.consultarEntidadDescuentoId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EntidadDescuentoModeloDTO entidadDescuentoModeloDTO = consultasEntidadDescuento.consultarEntidadDescuentoId(idEntidadDescuento);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return entidadDescuentoModeloDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#consutlarEntidadDescuentoNombreCodigo(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public List<EntidadDescuentoModeloDTO> consultarEntidadDescuentoPorNombreCodigo(String nombre, String codigo) {

        String firmaServicio = "EntidadDescuentoBusiness.consultarEntidadDescuentoNombreCodigo(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<EntidadDescuentoModeloDTO> lstEntidadesDescuentoDTO = null;
        Long codigoConvenio = null;
        if (codigo != null && !codigo.equals("")) {

            if (codigo.length() == 4) {
                try {
                    codigoConvenio = Long.parseLong(codigo);
                } catch (NumberFormatException e) {
                    return lstEntidadesDescuentoDTO;
                }
            }
            else {
                //se devuelve un null ya que el código no es de cuatro caracteres
                return lstEntidadesDescuentoDTO;
            }
        }

        lstEntidadesDescuentoDTO = consultasEntidadDescuento.consultarEntidadesDescuento(codigoConvenio, nombre);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstEntidadesDescuentoDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#consultarPrioridadesDisponibles()
     */
    @Override
    public List<String> consultarPrioridadesDisponibles() {

        String firmaServicio = "EntidadDescuentoBusiness.consultarPrioridadesDisponibles()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<String> lstPrioridadesaAsignar = new ArrayList<>();
        
        //se crean los valores que se pueden asignar a la prioridad de una entidad de descuento.
        for (int i = 1; i <= 99; i++) {
            
            Long prioridad = (long) i;  
            
            lstPrioridadesaAsignar.add(String.format("%02d", prioridad));
        }        
        
        //se listan las prioridades que ya estan registradas en las entidades de descuento guardadas.
        List<Long> lstPrioridadesExistentes = consultasEntidadDescuento.consultarPrioriodadesEntidadesDescuento();
        
        if(lstPrioridadesExistentes != null){
        
            for (int i = 0; i < lstPrioridadesaAsignar.size(); i++) {

                for (Long prioridadExistente : lstPrioridadesExistentes) {
                   
                    Long prioridadAsignar = Long.parseLong(lstPrioridadesaAsignar.get(i));
                    //se compara el valor a ser asignado con el valor de la prioridad existente
                    if (prioridadAsignar.compareTo(prioridadExistente) == 0) {
                        //se remueve la prioridad que ya existe en la lista que sera enviada
                        lstPrioridadesaAsignar.remove(i);
                    }
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        
        return lstPrioridadesaAsignar;
    }

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#gestionarEntidadDescuento(com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO)
     */
    @Override
    public String gestionarEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.gestionarEntidadDescuento(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Long idEntidadDescuento = null;
        
        if(entidadDescuentoModeloDTO.getIdEntidadDescuento() == null){
            
            idEntidadDescuento =consultasEntidadDescuento.crearEntidadDescuento(entidadDescuentoModeloDTO);
            
        }else{
            
            idEntidadDescuento = consultasEntidadDescuento.actualizarEntidadDescuento(entidadDescuentoModeloDTO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return String.format("%04d", idEntidadDescuento);
        
    }

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#buscarProximoCodigoEntidadDescuento()
     */
    @Override
    public Long buscarProximoCodigoEntidadDescuento() {
        
        String firmaServicio = "EntidadDescuentoBusiness.buscarProximoCodigoEntidadDescuento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Long codigo = consultasEntidadDescuento.buscarProximoCodigoEntidadDescuento();
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return codigo;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#validarEstructuraArchivoDescuentos()
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResultadoValidacionArchivoDTO validarEstructuraArchivoDescuentos(InformacionArchivoDTO informacionArchivoDTO,
            Long idTrazabilidad, UserDTO userDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.validarEstructuraArchivoDescuentos(InformacionArchivoDTO,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoDTO resultadoDTO = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ENTIDADES_DESCUENTO).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }
        FileFormat fileFormat;
        if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)) {
            fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
        }
        else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();
        List<Long> totalRegistroValidos = new ArrayList<Long>();

        context.put(CamposArchivoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO, totalRegistroValidos);
        context.put(CamposArchivoConstants.ID_TRAZABILIDAD_ARCHIVO_DESCUENTO, idTrazabilidad);
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, informacionArchivoDTO.getDataFile(), fileDefinitionId,
                    informacionArchivoDTO.getFileName());

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            resultadoDTO = new ResultadoValidacionArchivoDTO();
            resultadoDTO.setNombreArchivo(informacionArchivoDTO.getFileName());

            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));

            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultadoDTO.setEstadoCargueArchivoDescuento(EstadoCargaArchivoDescuentoEnum.CARGADO);
            }
            else {
                resultadoDTO.setEstadoCargueArchivoDescuento(EstadoCargaArchivoDescuentoEnum.ANULADO);
            }
            
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }

            totalRegistro = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext()
                    .get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES);
            totalRegistroValidos = (List<Long>) outDTO.getContext()
                    .get(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO);
            
            Long cantidadLinea = 0L;
            if (totalRegistro.isEmpty()) {
                cantidadLinea = (long) numLinea.size();
            }
            Long sumTotalRegistro = (long) totalRegistro.size() + cantidadLinea;
            Long registrosError = (long) totalRegistroError.size();
            
            if (registrosError == sumTotalRegistro) {
                resultadoDTO.setEstadoCargueArchivoDescuento(EstadoCargaArchivoDescuentoEnum.ANULADO);
            }
            
            resultadoDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
            resultadoDTO.setTotalRegistro(sumTotalRegistro);
            resultadoDTO.setRegistrosConErrores(((long) totalRegistroError.size())+cantidadLinea);
            resultadoDTO.setRegistrosValidos(sumTotalRegistro - resultadoDTO.getRegistrosConErrores());
            resultadoDTO.setFechaCargue(new Date().getTime());
            resultadoDTO.setFileDefinitionId(fileDefinitionId);

        } catch (FileProcessingException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoDTO;
    }

    /**
     * Método encargado de consultar la lista hallazgos
     * 
     * @param fileDefinitionId
     *        Identificador del archivo
     * @param outDTO
     *        Objeto con el procesamiento del archivo
     * @return Lista de hallazgos del procesamiento del archivo
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId, FileLoaderOutDTO outDTO) {
        // Lista de errores
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        // Campos asociados al archivo
        List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
        // Se verifica si se registraron errores en la tabla FileLoadedLog
        if (outDTO.getFileLoadedId() != null && outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty()) {
            // Se recorren los errores y se crean los respectivos hallazgos
            for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
                listaHallazgos.add(obtenerHallazgo(campos, detalleError.getMessage(), detalleError.getLineNumber()));
            }
        }
        return listaHallazgos; 
    }
    
    /**
     * Metodo encargado de consultar los campos del archivo
     * 
     * @param fileLoadedId
     *            identificador del fileLoadedId
     * @return lista de definiciones de campos
     */
    private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
        try {
            List<DefinicionCamposCargaDTO> campos =  consultasEntidadDescuento.consultarCamposDelArchivo(fileLoadedId);
            return campos;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /**
     * Obtiene el hallazgo a partir de la informacion del mensaje y los campos
     * @param campos
     *        Lista de campos del archivo
     * @param mensaje
     *        Mensaje de error obtenido
     * @param lineNumber
     *        Linea donde se encontro el error
     * @return Hallazgo creado en respectivo formato
     */
    private ResultadoHallazgosValidacionArchivoDTO obtenerHallazgo(List<DefinicionCamposCargaDTO> campos, String mensaje, Long lineNumber) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
        // Indica si el mensaje contiene el nombre del campo
        Boolean encontroCampo = Boolean.FALSE;
        // Se separa el mensaje por caracter ;
        String[] arregloMensaje = mensaje.split(";");
        // Se verifica si el mensaje contiene algún campo
        for (DefinicionCamposCargaDTO campo : campos) {
            for (int i = 0; i < arregloMensaje.length; i++) {
                if (arregloMensaje[i].contains(campo.getName())) {
                    mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
                    hallazgo = crearHallazgo(lineNumber, campo.getLabel(), mensaje);
                    encontroCampo = Boolean.TRUE;
                    break;
                }
            }
        }
        // Si no se encontro campo se crea el hallazgo sin campo
        if (!encontroCampo) {
            hallazgo = crearHallazgo(lineNumber, "", mensaje);
        }
        return hallazgo;
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
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#registrarTrazabilidadArchivoDescuentos(com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO)
     */
    @Override
    public Map<String, Object> gestionarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.registrarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String,Object> result = new HashMap<>();
        result.put("idTrazabilidad", null);
        result.put("duplicado", false);

        if(!informarcionTrazabilidadDTO.getNombre().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_DESCUENTOS))
            return result;

        if (informarcionTrazabilidadDTO.getIdArchivoEntidadDescuentoSubsidioMonetario() == null) {

            String nombreBase = informarcionTrazabilidadDTO.getNombre().substring(0, 21);

            String nombreExiste = consultasModeloSubsidio.consultarArchivosDescuentoPorNombre(nombreBase);

            Long id = consultasModeloSubsidio.registrarTrazabilidadArchivoDescuentos(informarcionTrazabilidadDTO);
            result.put("idTrazabilidad", id);
            if (nombreExiste != null && !nombreExiste.isEmpty()) {
                result.put("duplicado", true);
                ConsolaEstadoCargueProcesoDTO consolaCarga = new ConsolaEstadoCargueProcesoDTO();
                String codigoCaja;
                try {
                    codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
                } catch (Exception e) {
                    codigoCaja = null;
                }

                String extension = informarcionTrazabilidadDTO.getNombre().substring(21);
                if (nombreExiste.charAt(18) == '.') {
                    informarcionTrazabilidadDTO.setNombre(nombreBase + "_02" + extension);
                } else if (nombreExiste.charAt(18) == '_') {
                    int posRaya = nombreExiste.lastIndexOf('_');
                    int posPunto = nombreExiste.lastIndexOf('.');
                    String numeroStr = nombreExiste.substring(posRaya + 1, posPunto);
                    try {
                        int numero = Integer.parseInt(numeroStr);
                        numero++; // Incrementa el número
                        String nuevoNombre = nombreBase.substring(17) + "_" + String.format("%02d", numero) + extension;
                        informarcionTrazabilidadDTO.setNombre(nuevoNombre);
                    } catch (NumberFormatException e) {
                        return result;
                    }
                }

                consolaCarga.setCcf(codigoCaja);
                consolaCarga.setCargue_id(id);
                consolaCarga.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                consolaCarga.setFileLoaded_id(0L);
                consolaCarga.setGradoAvance(new BigDecimal(0));
                consolaCarga.setNumRegistroConErrores(1L);
                List<ResultadoHallazgosValidacionArchivoDTO> lista = new ArrayList<>();
                ResultadoHallazgosValidacionArchivoDTO duplicado = new ResultadoHallazgosValidacionArchivoDTO(
                        0L, id,
                        1L, nombreBase+extension,
                        "El archivo que desea cargar ya existe en el sistema.",
                        TipoProcesoMasivoEnum.CARGUE_DE_ARCHIVO_DESCUENTOS.toString(), nombreBase+extension
                );
                lista.add(duplicado);
                consolaCarga.setLstErroresArhivo(lista);
                consolaCarga.setIdentificacionECM(informarcionTrazabilidadDTO.getNombre());
                consolaCarga.setNumRegistroObjetivo(0L);
                consolaCarga.setNumRegistroProcesado(0L);
                consolaCarga.setNumRegistroValidados(0L);
                consolaCarga.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_ARCHIVO_DESCUENTOS);
                consolaCarga.setUsuario(null);
                consolaCarga.setNombreArchivo(nombreBase+extension);
                registrarConsolaEstado(consolaCarga);
            }
        } else {
            Long id = consultasModeloSubsidio.actualizarTrazabilidadArchivoDescuentos(informarcionTrazabilidadDTO);
            result.put("idTrazabilidad", id);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#obtenerArchivos()
     */
    @Override
    public List<ArchivoEntidadDescuentoDTO> obtenerArchivosDescuento() {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerArchivos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<ArchivoEntidadDescuentoDTO> archivos = new ArrayList<>();
        try {
            archivos = obtenerArchivosFTP();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return archivos;
    }
    
    /**
     * Método que permite obtener la lista de archivos entregados por la entidad de descuento
     * @param archivos
     *        arreglo para almacenar la información de los archivos
     * @return lista de DTO´s con la información de los archivos
     */
    private List<ArchivoEntidadDescuentoDTO> obtenerArchivosFTP() {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerArchivosFTP(List<ArchivoEntidadDescuentoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConexionServidorFTPUtil<ArchivoEntidadDescuentoDTO> conexionFTP = new ConexionServidorFTPUtil<ArchivoEntidadDescuentoDTO>(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name(), ArchivoEntidadDescuentoDTO.class);
        conexionFTP.setNombreHost((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST));
        conexionFTP.setPuerto((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_PUERTO));
        conexionFTP.setUrlArchivos((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS));
        conexionFTP.setProtocolo(
                Protocolo.valueOf((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_PROTOCOLO)));
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO)));
        conexionFTP.setContrasena(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_CONTRASENA)));

        conexionFTP.conectarYRecorrer();

        if (!conexionFTP.getArchivosDescargados().isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return conexionFTP.getArchivosDescargados();
        }
        else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#obtenerInformacionTrazabilidad()
     */
    @Override
    public List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> obtenerInformacionTrazabilidad(List<String> nombresArchivos) {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerInformacionTrazabilidad()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> identificadoresEntidades = consultasEntidadDescuento.obtenerEntidadesDescuentoActivas();
        List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> result = new ArrayList<>();

        if (!identificadoresEntidades.isEmpty()) {
            result = consultasModeloSubsidio.obtenerInformacionTrazabilidad(nombresArchivos, identificadoresEntidades);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#generarResultadosArchivosDescuento(java.lang.Long)
     */
    @Override
    public InformacionArchivoDTO generarResultadosArchivoDescuento(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.generarResultadosArchivosDescuento(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        InformacionArchivoDTO archivoXLSX = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_RESULTADOS_ENTIDAD_DESCUENTO).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = {  FileFormat.DELIMITED_TEXT_PLAIN };
        try {
            ArchivoEntidadDescuentoFilterDTO filtro = new ArchivoEntidadDescuentoFilterDTO();
            filtro.setNumeroRadicacion(trazabilidadDTO.getNumeroRadicacion());
            filtro.setIdEntidadDescuento(trazabilidadDTO.getIdEntidadDescuento());

            FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);

            if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                Date fechaEnvio = new Date();
                EntidadDescuentoModeloDTO entidadDescuentoDTO = consultarEntidadDescuentoId(trazabilidadDTO.getIdEntidadDescuento());
                outDTO.setDelimitedTxtFilename(generarNombreArchivo(Long.parseLong(entidadDescuentoDTO.getCodigo()), ".txt", fechaEnvio));
                //outDTO.setXslxFilename(generarNombreArchivo(trazabilidadDTO.getIdEntidadDescuento(), ".xlsx", fechaEnvio));

                enviarArchivoServidorFTP(outDTO);
                outDTO.setDelimitedTxtFilename(generarNombreArchivo(trazabilidadDTO.getIdEntidadDescuento(), ".csv", fechaEnvio));
                archivoXLSX = convertirOutDTO(outDTO);
            }
        } catch (AsopagosException e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            throw e;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return archivoXLSX;
    }
    
    /**
     * Método que se encarga de cargar el archivo generado mediante Lion en el servidor FTP en la ruta definida
     * @param outDTO
     */
    private void enviarArchivoServidorFTP(FileGeneratorOutDTO outDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.enviarArchivoServidorFTP(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConexionServidorFTPUtil<ArchivoEntidadDescuentoDTO> conexionFTP = new ConexionServidorFTPUtil<ArchivoEntidadDescuentoDTO>(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name(), ArchivoEntidadDescuentoDTO.class);
        conexionFTP.setNombreHost((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST));
        conexionFTP.setPuerto((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_PUERTO));
        String urlArchivos = (String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS);
        conexionFTP.setUrlArchivos(urlArchivos);
        conexionFTP.setProtocolo(
                Protocolo.valueOf((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_PROTOCOLO)));
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO)));
        conexionFTP.setContrasena(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_CONTRASENA)));

        conexionFTP.subirArchivoFTP(outDTO.getDelimitedTxtFilename(), outDTO.getDelimitedTxt());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.service.EntidadDescuentoService#obtenerIdentificadoresTrazabilidadRadicacion(java.lang.String)
     */
    @Override
    public List<Long> obtenerEntidadesDescuentoRadicacion(String numeroRadicacion) {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerIdentificadoresTrazabilidadRadicacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> result = consultasModeloSubsidio.obtenerEntidadesDescuentoRadicacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
   
    @Override
    public String obtenerArchivosSalidaDescuentos(String numeroRadicacion, Long idEntidadDescuento) {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerArchivosSalidaDescuentos(String, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String codigoIdentificacionECMSalida = consultasEntidadDescuento.obtenerArchivosSalidaDescuentos(numeroRadicacion, idEntidadDescuento);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return codigoIdentificacionECMSalida;
    }
    
    /**
     * Método encargado de generar el nombre para el archivo de descuento
     * @param codigoEntidad codigo de la entidad de descuento
     * @param extension extension del archivo
     */
    private String generarNombreArchivo(Long codigoEntidad, String extension, Date fechaEnvio){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return "OUT_" + String.format("%04d", codigoEntidad) + "_" + format.format(fechaEnvio) + "_" + formatHora.format(fechaEnvio) + extension;
    }
    
    /**
     * Método que se encarga de convertir un FileGeneratorOutDTO a un InformacionArchivoDTO
     * @param outDTO información del archivo generado por lion
     * @return DTO con la información del archivo para enviar al ECM
     */
    private InformacionArchivoDTO convertirOutDTO(FileGeneratorOutDTO outDTO){
        InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();
        
        informacionArchivoDTO.setDataFile(outDTO.getDelimitedTxt());
        informacionArchivoDTO.setFileName(outDTO.getDelimitedTxtFilename());
        informacionArchivoDTO.setDocName(outDTO.getDelimitedTxtFilename());
        informacionArchivoDTO.setFileType(MediaType.TEXT_PLAIN);
        informacionArchivoDTO.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());
        
        return informacionArchivoDTO;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#actualizarArchivosDescuentoLiquidacionCancelada(java.lang.String)
     */
    @Override
    public void actualizarArchivosDescuentoLiquidacionCancelada(String numeroRadicacion) {
        String firmaMetodo = "EntidadDescuentoBusiness.actualizarArchivosDescuentoLiquidacionCancelada(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        consultasModeloSubsidio.actualizarArchivosDescuentoLiquidacionCancelada(numeroRadicacion);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#crearRegistroArchivoSalidaDescuento(java.lang.String)
     */
    @Override
    public void crearRegistroArchivoSalidaDescuento(ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo) {
        String firmaMetodo = "EntidadDescuentoBusiness.actualizarArchivosDescuentoLiquidacionCancelada(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        consultasEntidadDescuento.crearRegistroArchivoSalidaDescuento(infoArchivo);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#ejecutarActualizacionArchivosDescuento(java.lang.long,java.lang.int)
     */
    public Long ejecutarActualizacionArchivosDescuento(Long idArchivo, Long codigoEntidad) {
        String firmaMetodo = "EntidadDescuentoBusiness.ejecutarActualizacionArchivosDescuento(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result =  consultasModeloSubsidio.ejecutarActualizacionArchivosDescuento(idArchivo, codigoEntidad);

        logger.info("Resultado ejecución PROCEDURE_ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO " + result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return result;
    }

    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        logger.info("----registrarConsolaEstado---");
            RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
            registroConsola.execute();
    }
}