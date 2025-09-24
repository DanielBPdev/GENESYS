package com.asopagos.subsidiomonetario.pagos.service.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.persistence.NoResultException;

import com.asopagos.entidades.subsidiomonetario.pagos.*;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoConvenioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.*;
import com.asopagos.rest.exception.ErrorExcepcion;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ErroresServiciosEnum;
import com.asopagos.subsidiomonetario.pagos.clients.*;
import com.asopagos.subsidiomonetario.pagos.dto.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.util.TextReaderUtil;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobradoModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioYDetallesDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoAbonoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoRetiroTercerPagadorEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoArchivoTransDetaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenRegistroSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoValidacionNombreArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoLiquidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoOperacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ValidacionNombreArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCoreLiquidacion;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.subsidiomonetario.pagos.constants.PagosSubsidioMonetarioConstants;
import com.asopagos.subsidiomonetario.pagos.constants.TipoArchivoPagoEnum;
import com.asopagos.subsidiomonetario.pagos.enums.ComparacionSaldoTarjetaEnum;
import com.asopagos.subsidiomonetario.pagos.load.source.ArchivoPagoBancoFilterDTO;
import com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService;

import com.asopagos.subsidiomonetario.pagos.util.ArchivosPagosUtil;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;
import com.asopagos.util.PersonasUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import co.com.heinsohn.lion.common.enums.Protocolo;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.enums.FileGeneratedState;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import com.asopagos.subsidiomonetario.pagos.util.ValidatorUtil;
import com.asopagos.usuarios.clients.ValidarCredencialesUsuario;
import com.asopagos.dto.modelo.AdministradorSubsidioModeloDTO;
import com.asopagos.personas.clients.ConsultarAdministradorSubsidioGeneral;
import com.asopagos.dto.MediosPagoYGrupoTrasladoDTO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.NovedadTrasladoDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAutomaticaSinValidaciones;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.clienteanibol.clients.DescontarSaldoTarjetas;
import com.asopagos.clienteanibol.clients.AbonarSaldoTarjetas;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamiento;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleBandejaTransaccionesDTO;
import com.asopagos.subsidiomonetario.pagos.dto.GestionDeTransaccionesDTO;
import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamientoV2;
import com.asopagos.pagination.PaginationQueryParamsEnum;

import com.asopagos.entidades.subsidiomonetario.pagos.anibol.RegistroArchivoConsumosAnibol;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Stateless
public class PagosSubsidioMonetarioBusiness implements PagosSubsidioMonetarioService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PagosSubsidioMonetarioBusiness.class);

    /**
     * Inject del EJB para consultas en modelo Core
     */
    @Inject
    private IConsultasModeloCore consultasCore;

    /**
     * Inject del EJB para consultas en modelo Core - proceso liquidación
     */
    @Inject
    private IConsultasModeloCoreLiquidacion consultasCoreLiquidacion;

    /**
     * Inject del EJB para consultas en modelo Core
     */
    @Inject
    private IConsultasModeloSubsidio consultasSubsidio;

    /**
     * Interfaz de validación para archivos mediante Lion Framework
     */
    @Inject
    private FileLoaderInterface fileLoader;

    /**
     * Interfaz de generación de archivos mediante Lion Framework
     */
    @Inject
    private FileGenerator fileGenerator;

    private static final String SEPARADOR = ",";

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#validarEstructuraArchivoRetiros(com.asopagos.dto.InformacionArchivoDTO,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ResultadoValidacionArchivoDTO validarEstructuraArchivoRetiros(InformacionArchivoDTO informacionArchivoDTO, String nombreUsuario,
                                                                         String nombreTerceroPagador) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarEstructuraArchivoConvenios(InformacionArchivoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoDTO result = new ResultadoValidacionArchivoDTO();
        Long idCargueArchivo = null;

        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(CacheManager
                    .getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_RETIRO_PAGOS_SUBSIDIO_MONETARIO).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat fileFormat;
        if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)
                || informacionArchivoDTO.getFileName().toLowerCase()
                .endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN.toLowerCase())) {
            fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
        } else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();
        List<Long> totalRegistroValidos = new ArrayList<Long>();
        List<RetiroCandidatoDTO> listaCandidatos = new ArrayList<>();
        context.put(CamposArchivoConstants.LISTA_CANDIDATOS, listaCandidatos);
        context.put(CamposArchivoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO, totalRegistroValidos);
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            logger.info("Inicio fileLoader.validateAndLoad validarEstructuraArchivoRetiros");
            outDTO = fileLoader.validateAndLoad(context, fileFormat, informacionArchivoDTO.getDataFile(), fileDefinitionId);
            logger.info("Finaliza fileLoader.validateAndLoad validarEstructuraArchivoRetiros");
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES);
            listaCandidatos = (List<RetiroCandidatoDTO>) outDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);

            //se valida que no hayan error de estructura encontrados por lion y se valida que haya registros candidatos para almacenar la información del archivo.
            if ((outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty()) || listaCandidatos.isEmpty()) {
                //logger.info("outDTO.getDetailedErrors().isEmpty(): "+outDTO.getDetailedErrors().isEmpty()+
                //" listaCandidatos.isEmpty(): "+listaCandidatos.isEmpty()+" outDTO.getDetailedErrors():"+outDTO.getDetailedErrors().size());
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio
                        + "Ningun registro del archivo cumplen con la validación de estructura de los campos");
                result.setEstadoCargue(EstadoCargaMultipleEnum.CARGADO);
                idCargueArchivo = registrarCargueArchivoTerceroPagador(informacionArchivoDTO.getIdentificadorDocumento(),
                        informacionArchivoDTO.getFileName(), informacionArchivoDTO.getVersionDocumento(), nombreUsuario);

                result.setIdCargue(idCargueArchivo);
                return result;
            }

            totalRegistroValidos = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO);
            result.setTotalRegistro((long) totalRegistro.size());
            result.setRegistrosValidos((long) totalRegistroValidos.size());
            result.setRegistrosConErrores((long) totalRegistroError.size());
            result.setFechaCargue(new Date().getTime());
            result.setFileDefinitionId(fileDefinitionId);
            result.setNombreArchivo(informacionArchivoDTO.getFileName());
            result.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);

            //se registra si la validación de la estructura del archivo fue exitosa o fallo.
            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                result.setEstadoCargue(EstadoCargaMultipleEnum.EN_COLA);
            } else {
                result.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if ((result.getEstadoCargue().equals(EstadoCargaMultipleEnum.EN_COLA)) && (!listaHallazgos.isEmpty())) {
                result.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }
            if (!listaHallazgos.isEmpty()) {
                result.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);
            }

            //se obtiene el id del cargue del archivo de retiro
            idCargueArchivo = registrarCargueArchivoTerceroPagador(informacionArchivoDTO.getIdentificadorDocumento(),
                    informacionArchivoDTO.getFileName(), informacionArchivoDTO.getVersionDocumento(), nombreUsuario);

            result.setIdCargue(idCargueArchivo);

            //se insertan los registros y campos del archivo
            crearRegistrosCamposArchivoTerceroPagador(listaCandidatos, idCargueArchivo);

            //se realiza la comparación en un proceso almacenado
            //se llama el SP para comparar los archivos
            consultasCore.compararRegistrosCamposArchivoTerceroPagadorSP(informacionArchivoDTO.getIdentificadorDocumento(),
                    informacionArchivoDTO.getVersionDocumento(), nombreTerceroPagador);
            logger.info("Se ejecuto el SP para comparar los archivos");

            //Se obtiene si el estado esta PROCESADO o PROCESADO_CON_INCOSISTENCIA
            EstadoArchivoRetiroTercerPagadorEnum estadoArchivo = consultasCore.consultarEstadoArchivoRetiroTerceroPagador(
                    informacionArchivoDTO.getIdentificadorDocumento(), informacionArchivoDTO.getVersionDocumento());

            //despues de hacer el procesamiento almacenado se verifica si el estado de cargue es procesado
            //o procesado con inconsistencias
            logger.info("Estado archivo conciliacion " + estadoArchivo);
            if (EstadoArchivoRetiroTercerPagadorEnum.PROCESADO.equals(estadoArchivo)) {
                result.setEstadoCargue(EstadoCargaMultipleEnum.CERRADO); //la conciliación no tuvo problemas
            } else if (EstadoArchivoRetiroTercerPagadorEnum.PROCESADO_CON_INCONSISTENCIA.equals(estadoArchivo)) {
                result.setEstadoCargue(EstadoCargaMultipleEnum.CANCELADO);// no fue conciliado
            } else {
                result.setEstadoCargue(EstadoCargaMultipleEnum.EN_PROCESO);
            }

        } catch (FileProcessingException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long validarArchivoConsumoTerceroPagadorEfectivo(InformacionArchivoDTO informacionArchivoDTO, Long idConvenio, String nombreUsuario, Long idArchivoTerceroPagadorEfectivo) {
        ResultadoValidacionArchivoRetiroDTO resultado = new ResultadoValidacionArchivoRetiroDTO();
        ArrayList<String[]> lineas;
        try {
            lineas = TextReaderUtil.fileToListString(informacionArchivoDTO.getDataFile(), "\\|");

            // PERSISTIR TABLA TEMPORAL
            consultasCore.persistirTempArchivoRetiroTerceroPagadorEfectivo(lineas, nombreUsuario, idConvenio, idArchivoTerceroPagadorEfectivo);

            // VALIDACIONES CONTRA DB
            consultasCore.ejecutarSPValidarContenidoArchivoTerceroPagadorEfectivo(idConvenio, idArchivoTerceroPagadorEfectivo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new TechnicalException(MensajesGeneralConstants.ERROR_EJECUCION_PROCEDIMIENTO_ALMACENADO, e);
        }
        return 1L;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long persistirTempArchivoRetiroTerceroPagador(TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO) {
        return consultasCore.persistirTempArchivoRetiroTerceroPagadorEfectivo(TempArchivoRetiroTerceroPagadorEfectivoDTO);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long actualizarTempArchivoRetiroTerceroPagador(TempArchivoRetiroTerceroPagadorEfectivoDTO TempArchivoRetiroTerceroPagadorEfectivoDTO) {
        return consultasCore.actualizarTempArchivoRetiroTerceroPagadorEfectivo(TempArchivoRetiroTerceroPagadorEfectivoDTO);
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long registrarConvenioTercerPagador(ConvenioTercerPagadorDTO convenio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarConvenioTercerPagador(ConvenioTercerPagadorDTO,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long idConvenio = consultasCore.crearSolicitudRegistroConvenio(convenio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        return idConvenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
     */
    @Override
    public Long registrarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO, UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + " con parámetros de entrada: " + cuentaAdministradorSubsidioDTO.toString());

        //Creación de una cuenta de administrador de subsidio cuando es por una liquidación de subsidio
        if (cuentaAdministradorSubsidioDTO.getTipoTransaccion().equals(TipoTransaccionSubsidioMonetarioEnum.ABONO)
                && (cuentaAdministradorSubsidioDTO.getOrigenTransaccion().equals(OrigenTransaccionEnum.LIQUIDACION_SUBSIDIO_MONETARIO)
                || cuentaAdministradorSubsidioDTO.getOrigenTransaccion().equals(OrigenTransaccionEnum.ANULACION))) {

            BigDecimal valorTotal = null;
            double valorTotalCuenta = 0;
            BigDecimal valorOriginalTransaccion = null;
            double valorOriginalCuenta = 0;

            //se suman todos los valores del detalle del subsidio asignado para asignar las sumatorias a la cuenta
            for (DetalleSubsidioAsignadoDTO detalle : cuentaAdministradorSubsidioDTO.getListaDetallesSubsidioAsignadoDTO()) {
                //valorTotalCuenta += detalle.getValorOriginalAbonado().doubleValue();
                valorTotalCuenta += detalle.getValorTotal().doubleValue();
                valorOriginalCuenta += detalle.getValorOriginalAbonado().doubleValue();
            }

            if (cuentaAdministradorSubsidioDTO.getOrigenTransaccion().equals(OrigenTransaccionEnum.LIQUIDACION_SUBSIDIO_MONETARIO)) {
                valorOriginalTransaccion = BigDecimal.valueOf(valorOriginalCuenta);
                cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(valorOriginalTransaccion);
            } else {
                valorTotal = BigDecimal.valueOf(valorTotalCuenta);
                cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(valorTotal);
            }
            //el valor real de la transacción es igual al valor original de la transacción
            cuentaAdministradorSubsidioDTO.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
            cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion((userDTO != null && userDTO.getNombreUsuario() != null) ? userDTO.getNombreUsuario() : "Genesys");

            //esto sucede si al momento de crear el abono el valor original abonado es igual a cero
            if (cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion().intValue() == 0) {
                cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
            }

        } else if (cuentaAdministradorSubsidioDTO.getTipoTransaccion().equals(TipoTransaccionSubsidioMonetarioEnum.ANULACION)
                && cuentaAdministradorSubsidioDTO.getOrigenTransaccion().equals(OrigenTransaccionEnum.ANULACION)) {

            //Creación de una cuenta del administrador de subsidio para anulación

            BigDecimal valorOrigTransaccionNegativo = BigDecimal
                    .valueOf(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion().doubleValue());
            BigDecimal valorRealTransaccionNegativo = BigDecimal
                    .valueOf(cuentaAdministradorSubsidioDTO.getValorRealTransaccion().doubleValue());

            cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(valorOrigTransaccionNegativo.negate());
            cuentaAdministradorSubsidioDTO.setValorRealTransaccion(valorRealTransaccionNegativo.negate());
        }

        cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro((userDTO != null && userDTO.getNombreUsuario() != null) ? userDTO.getNombreUsuario() : "");

        Long cuentaAdminSubsidio = consultasCore.crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAdminSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#anularSubsidioMonetarioSinReemplazo(java.util.List,
     * com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO, java.lang.Boolean,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long anularSubsidioMonetarioSinReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO, Boolean resultadoValidacion,
                                                    UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.anularSubsidioMonetarioSinReemplazo(List<DetalleSubsidioAsignadoDTO>,CuentaAdministradorSubsidioDTO,Boolean,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO = subsidioAnulacionDTO.getListaAnularDetallesDTO();
        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO();

        //se realizan los Pasos 1 y 2.
        //si se retorna un false, es porque la validación con anibol no fue procedente.
        if (!crearAbonoAnulado(cuentaAdministradorSubsidioOrigDTO, userDTO, resultadoValidacion)) {
            //si la validación con ANIBOL no es procedente  se ejecuta el numeral 3.3.1 de la HU-31-208 cuando es medio de pago por TARJETA
            cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
            cuentaAdministradorSubsidioOrigDTO.setFechaHoraUltimaModificacion(new Date());
            cuentaAdministradorSubsidioOrigDTO.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);
            return null;
        }

        //lista en donde se almacenaran los nuevos detalles de subsidio asignado
        //se realizan los Pasos 3 y 4.
        List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignadosReemplazados = separarDetallesSubsidiosAsignadosReemplazados(
                listaAnularDetallesDTO, cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio(), userDTO);

        //Paso 5: Se actualizan los registro del Detalle Subsidio Asignado asociados al abono (Cuenta) que si fueron seleccionados para anulación
        for (DetalleSubsidioAsignadoDTO detalle : listaAnularDetallesDTO) {

            detalle.setEstado(EstadoSubsidioAsignadoEnum.ANULADO);
            detalle.setFechaTransaccionAnulacion(new Date());
            detalle.setUsuarioTransaccionAnulacion(userDTO.getNombreUsuario());
            detalle.setFechaHoraUltimaModificacion(new Date());
            detalle.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());

            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
        }

        //Se realizan los Pasos 6 y 7.
        Long idNuevaCuentaAdminSubsidio = 1L;
        if (detallesSubsidiosAsignadosReemplazados != null && !detallesSubsidiosAsignadosReemplazados.isEmpty()) {
            idNuevaCuentaAdminSubsidio = crearNuevoAbonoActualizarDetallesRelacionados(cuentaAdministradorSubsidioOrigDTO,
                    detallesSubsidiosAsignadosReemplazados, userDTO, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idNuevaCuentaAdminSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#anularSubsidioMonetarioSinReemplazo(java.util.List,
     * com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO, java.lang.Boolean,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long anularSubsidioMonetarioSinReemplazoH(SubsidioAnulacionDTO subsidioAnulacionDTO, Boolean resultadoValidacion) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.anularSubsidioMonetarioSinReemplazo(List<DetalleSubsidioAsignadoDTO>,CuentaAdministradorSubsidioDTO,Boolean,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        UserDTO usuarioDTO = subsidioAnulacionDTO.getUsuarioDTO();

        List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO = subsidioAnulacionDTO.getListaAnularDetallesDTO();
        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO();

        //se realizan los Pasos 1 y 2.
        //si se retorna un false, es porque la validación con anibol no fue procedente.
        if (!crearAbonoAnulado(cuentaAdministradorSubsidioOrigDTO, usuarioDTO, resultadoValidacion)) {
            //si la validación con ANIBOL no es procedente  se ejecuta el numeral 3.3.1 de la HU-31-208 cuando es medio de pago por TARJETA
            cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
            cuentaAdministradorSubsidioOrigDTO.setFechaHoraUltimaModificacion(new Date());
            cuentaAdministradorSubsidioOrigDTO.setUsuarioUltimaModificacion(usuarioDTO.getNombreUsuario());
            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);
            return null;
        }

        //lista en donde se almacenaran los nuevos detalles de subsidio asignado
        //se realizan los Pasos 3 y 4.
        List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignadosReemplazados = separarDetallesSubsidiosAsignadosReemplazados(
                listaAnularDetallesDTO, cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio(), usuarioDTO);

        //Paso 5: Se actualizan los registro del Detalle Subsidio Asignado asociados al abono (Cuenta) que si fueron seleccionados para anulación
        for (DetalleSubsidioAsignadoDTO detalle : listaAnularDetallesDTO) {

            detalle.setEstado(EstadoSubsidioAsignadoEnum.ANULADO);
            if (detalle.getMotivoAnulacion() == null) {
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.PRESCRIPCION);
            }
            detalle.setFechaTransaccionAnulacion(new Date());
            //detalle.setFechaHoraCreacion(new Date());
            if (usuarioDTO != null) {
                detalle.setUsuarioTransaccionAnulacion(usuarioDTO.getNombreUsuario());
            }
            detalle.setFechaHoraUltimaModificacion(new Date());
            detalle.setUsuarioUltimaModificacion(usuarioDTO != null ? usuarioDTO.getNombreUsuario() : "Genesys");

            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
        }

        //Se realizan los Pasos 6 y 7.
        Long idNuevaCuentaAdminSubsidio = -1L;
        if (detallesSubsidiosAsignadosReemplazados != null && !detallesSubsidiosAsignadosReemplazados.isEmpty()) {
            idNuevaCuentaAdminSubsidio = crearNuevoAbonoActualizarDetallesRelacionados(cuentaAdministradorSubsidioOrigDTO,
                    detallesSubsidiosAsignadosReemplazados, usuarioDTO, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idNuevaCuentaAdminSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#anularSubsidioMonetarioConReemplazo(java.util.List,
     * com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO, com.asopagos.rest.security.dto.UserDTO,
     * com.asopagos.subsidiomonetario.pagos.dto.MedioDePagoCambioDTO, java.lang.Boolean)
     */
    @Context
    public Long anularSubsidioMonetarioConReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO, UserDTO userDTO,
                                                    Boolean resultadoValidacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.anularSubsidioMonetarioConReemplazo(List<DetalleSubsidioAsignadoDTO>,Long,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO = subsidioAnulacionDTO.getListaAnularDetallesDTO();
        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO();
        MedioDePagoModeloDTO medioDePagoModelo = subsidioAnulacionDTO.getMedioDePagoModelo();
        //se realiza el Paso 1 y Paso 2
        //si se retorna un false, es porque la validación con anibol no fue procedente.
        if (!crearAbonoAnulado(cuentaAdministradorSubsidioOrigDTO, userDTO, resultadoValidacion)) {
            logger.warn("entro al if: crearAbonoAnulado");
            //si la validación con ANIBOL no es procedente  se ejecuta el numeral 3.3.1 de la HU-31-208 cuando es medio de pago por TARJETA
            cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
            cuentaAdministradorSubsidioOrigDTO.setFechaHoraUltimaModificacion(new Date());
            cuentaAdministradorSubsidioOrigDTO.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
            cuentaAdministradorSubsidioOrigDTO.setIdAdministradorSubsidio(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getIdAdministradorSubsidio());
            cuentaAdministradorSubsidioOrigDTO.setIdEmpleador(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getIdEmpleador());
            cuentaAdministradorSubsidioOrigDTO.setIdAfiliadoPrincipal(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getIdAfiliadoPrincipal());
            cuentaAdministradorSubsidioOrigDTO.setIdBeneficiarioDetalle(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getIdBeneficiarioDetalle());
            cuentaAdministradorSubsidioOrigDTO.setIdGrupoFamiliar(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getIdGrupoFamiliar());
            cuentaAdministradorSubsidioOrigDTO.setSolicitudLiquidacionSubsidio(subsidioAnulacionDTO.getCuentaAdministradorSubsidioOrigDTO().getSolicitudLiquidacionSubsidio());

            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);
            return null;
        }

        logger.warn("estado de la cuenta original 2" + String.valueOf(cuentaAdministradorSubsidioOrigDTO.getEstadoTransaccion()));
        //lista en donde se almacenaran los nuevos detalles de subsidio asignado.
        //se realizan los Pasos 3 y 4.
        List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignadosReemplazados = separarDetallesSubsidiosAsignadosReemplazados(
                listaAnularDetallesDTO, cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio(), userDTO);

        if (cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado() != null && cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado()) {
            //se obtiene los detalles de subsidio asignados del abono que no serán anulados
            List<DetalleSubsidioAsignadoDTO> listaDetallesNoAnulados = consultasCore.consultarDetallesSubsidioAsinadosNoAnulados(
                    consultasCore.obtenerListaIdsDetallesSubsidioAsignado(listaAnularDetallesDTO), cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio());
            for(DetalleSubsidioAsignadoDTO detalle : listaDetallesNoAnulados){
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.TRASLADO_DE_SALDO);
                consultasCore.actualizarDetalleSubsidioAsignado(detalle);
            }
        }

        for (DetalleSubsidioAsignadoDTO detalle : listaAnularDetallesDTO) {
            logger.info("cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado() for --->" +cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado());
            logger.info("detalle for --->" +detalle.getIdDetalleSubsidioAsignado());

            //Paso 5: Se actualizan los registro del Detalle Subsidio Asignado asociados al abono (Cuenta) que si fueron seleccionados para anulación
            detalle.setEstado(EstadoSubsidioAsignadoEnum.ANULADO_REEMPLAZADO);
            detalle.setFechaTransaccionAnulacion(new Date());
            detalle.setUsuarioTransaccionAnulacion(userDTO.getNombreUsuario());
            detalle.setFechaHoraUltimaModificacion(new Date());
            detalle.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
            //glpi 88759
            if (cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado() != null && cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado()) {
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.TRASLADO_DE_SALDO);
            }
            consultasCore.actualizarDetalleSubsidioAsignado(detalle);

            //Paso 6: Se reemplazan los registros de Detalle Subsidio Asignado que fueron anulados mediante creación de nuevos registros.
            DetalleSubsidioAsignadoDTO detalleNuevoDeAnulado = detalle.clone();

            detalleNuevoDeAnulado.setIdDetalleSubsidioAsignado(null);
            detalleNuevoDeAnulado.setFechaHoraCreacion(new Date());
            detalleNuevoDeAnulado.setUsuarioCreador(userDTO.getNombreUsuario());
            detalleNuevoDeAnulado.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
            if (cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado() != null && cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado()) {
                cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ANULADO);
                detalleNuevoDeAnulado.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.TRASLADO_DE_SALDO);
                detalleNuevoDeAnulado.setMotivoAnulacion(null);

                //se comenta bajo glpi 88759
                //detalleNuevoDeAnulado.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.TRASLADO_DE_SALDO);
            }else{
                detalleNuevoDeAnulado.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.ANULACION);
                detalleNuevoDeAnulado.setMotivoAnulacion(null);
            }
            detalleNuevoDeAnulado.setIdRegistroOriginalRelacionado(detalle.getIdDetalleSubsidioAsignado());
            detalleNuevoDeAnulado.setDetalleAnulacion(null);
            detalleNuevoDeAnulado.setFechaTransaccionAnulacion(null);
            detalleNuevoDeAnulado.setUsuarioTransaccionAnulacion(null);

            detallesSubsidiosAsignadosReemplazados.add(detalleNuevoDeAnulado);
        }



        //Se realizan los Pasos 7 y 8.
        Long idNuevaCuentaAdminSubsidio = crearNuevoAbonoActualizarDetallesRelacionados(cuentaAdministradorSubsidioOrigDTO,
                detallesSubsidiosAsignadosReemplazados, userDTO, medioDePagoModelo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idNuevaCuentaAdminSubsidio;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarDetalleSubsidioAsignado(java.util.List)
     */
    @Override
    public void registrarDetalleSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> detallesSubsidioAsignadoDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarDetalleSubsidioAsignado(List<DetalleSubsidioAsignadoDTO>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        consultasCore.crearDetallesSubsidiosAsignados(detallesSubsidioAsignadoDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#gestionarTransaccionesFallidas(com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO)
     */
    @Override
    public Long gestionarTransaccionesFallidas(TransaccionFallidaDTO transaccionFallidaDTO) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.gestionarTransaccionesFallidas(TransaccionFallidaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long idTransaccion = null;

        //si no contiene un id de transaccion, se crea la transacción fallida
        if (transaccionFallidaDTO.getIdTransaccionesFallidasSubsidio() == null) {

            idTransaccion = consultasCore.crearTransaccionFallida(transaccionFallidaDTO);

        } else {

            idTransaccion = consultasCore.actualizarTransaccionFallida(transaccionFallidaDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idTransaccion;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarTransaccionesFallidasSubsidioPorFechas(Long,
     * Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TransaccionFallidaDTO> consultarTransaccionesFallidasSubsidioPorFechas(Long fechaInicial, Long fechaFinal) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarTransaccionesFallidasPorFechas(Date,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TransaccionFallidaDTO> listaTransaccioneFallidasDTO;

        if (fechaInicial != null && fechaFinal != null) {
            listaTransaccioneFallidasDTO = consultasCore.consultarTransaccionesFallidasPorFechas(fechaInicial, fechaFinal);
        } else {
            listaTransaccioneFallidasDTO = consultasCore.consultarTransaccionesFallidasPorFechas(null, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaTransaccioneFallidasDTO;
    }

    private List<TransaccionFallidaDTO> consultarTransaccionesFallidasSubsidioPorFechas1(Long fechaInicial, Long fechaFinal) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarTransaccionesFallidasPorFechas(Date,Date)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TransaccionFallidaDTO> listaTransaccioneFallidasDTO = null;

        //si las fechas son diferentes de null, se realiza la consulta por rango de fechas inicial y final.
        if (fechaInicial != null && fechaFinal != null) {

            listaTransaccioneFallidasDTO = consultasCore.consultarTransaccionesFallidasPorFechas(fechaInicial, fechaFinal);

        } else {
            listaTransaccioneFallidasDTO = consultasCore.consultarTransaccionesFallidasPorFechas(null, null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaTransaccioneFallidasDTO;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#verDetallesSubsidioAsignado(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> verDetallesSubsidioAsignado(Long idCuentaAdminSubsidio) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.verDetallesSubsidioAsignado(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultasCore
                .consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAdminSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioTotalAbonoDTO consultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos(UriInfo uriInfo,
                                                                                                                        HttpServletResponse response, String textoFiltro) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidios;
        BigDecimal valorTotalAbono = new BigDecimal(0);
        CuentaAdministradorSubsidioTotalAbonoDTO listaConTotal = new CuentaAdministradorSubsidioTotalAbonoDTO();

        if (textoFiltro != null && !textoFiltro.isEmpty()) {
            listaCuentaAdminSubsidios = consultasCore.consultarAbonosEnviadosMedioDePagoBancosPaginadoConFiltro(uriInfo, response, textoFiltro);
        } else {
            listaCuentaAdminSubsidios = consultasCore.consultarAbonosEnviadosMedioDePagoBancosPaginado(uriInfo, response);
        }

        //List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidios = new ArrayList<>();

        //por defecto todos los abono mostrados  estan en estado EXITOSO
        for (CuentaAdministradorSubsidioDTO abono : listaCuentaAdminSubsidios) {
            //CuentaAdministradorSubsidioDTO abono = new CuentaAdministradorSubsidioDTO();
            abono.setEstadoAbono(EstadoAbonoEnum.ABONO_EXITOSO);
            //valorTotalAbono = valorTotalAbono.add(abono.getValorRealTransaccion());

        }

        valorTotalAbono = consultasCore.consultarSumatoriaAbonosEnviadosMedioDePagoBancos();

        listaConTotal.setListaCuentaAdminSubsidios(listaCuentaAdminSubsidios);
        listaConTotal.setValorTotalAbono(valorTotalAbono);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaConTotal;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#confirmarResultadosAbonosBancarios(java.util.List)
     */
    @Override
    public void confirmarResultadosAbonosBancarios(List<CuentaAdministradorSubsidioDTO> listaAbonos, UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.confirmarResultadosAbonosBancarios(List<CuentaAdministradorSubsidioDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> idCuentaAdmonSubsidioNoExitosas = new ArrayList<>();
        if (listaAbonos != null && !listaAbonos.isEmpty()) {
            for (CuentaAdministradorSubsidioDTO abono : listaAbonos) {
                idCuentaAdmonSubsidioNoExitosas.add(abono.getIdCuentaAdministradorSubsidio());
            }
        }

        consultasCore.ejecutarAbonosMedioPagoBancos(idCuentaAdmonSubsidioNoExitosas, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarTransaccionesSubsidio(com.asopagos.subsidiomonetario.pagos.dto.TransaccionConsultadaDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesSubsidio(UriInfo uriInfo,
                                                                               HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) {

        String firmaServicio = "**__**PagosSubsidioMonetarioBusiness.consultarTransaccionesSubsidio(UriInfo, HttpServletResponse, DetalleTransaccionAsignadoConsultadoDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = consultasCore.consultarTransacciones(transaccionConsultada,null);
        //List<CuentaAdministradorSubsidioDTO> listaCuentas = consultasCore.consultarCuentasConFiltros(transaccionConsultada);
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = null;
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidiosone = new ArrayList<CuentaAdministradorSubsidioDTO>();
        logger.warn("info: " + uriInfo + response + transaccionConsultada);
        listaCuentasAdminSubsidios = consultasCore.consultarTransaccionesPaginada(uriInfo, response, transaccionConsultada);
        // solucion dos glpi  57988
        String establecimiewntoCodigo = null;                   
        if (listaCuentasAdminSubsidios != null) {
            response.addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(), listaCuentasAdminSubsidios.get(0).getTotalRegistro());    
            for (CuentaAdministradorSubsidioDTO cas : listaCuentasAdminSubsidios) {
                cas.setEstablecimientoCodigo(null);
                cas.setTotalRegistro(null);
                listaCuentasAdminSubsidiosone.add(cas);
            }
            listaCuentasAdminSubsidios = listaCuentasAdminSubsidiosone;
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCuentasAdminSubsidios;
    }


    @Override
    public Map<String, String> generarArchivoTransaccionesSubsidio(@Context UriInfo uriInfo,
                                                                   @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            throws FileNotFoundException, IOException {


        Map<String, String> respuestaProceso = new HashMap<String, String>();

        List<ArchivoTransDetaSubsidio> archivoTransDetaSubsidioList =
                consultasCore.consultarArchivoTransDetaSubsidioEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO);


        if (archivoTransDetaSubsidioList == null || archivoTransDetaSubsidioList.isEmpty()) {
            ArchivoTransDetaSubsidio archivoTransDetaSubsidio = new ArchivoTransDetaSubsidio();

            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO.toString());
            archivoTransDetaSubsidio.setFechaGeneracion(new Date());
            archivoTransDetaSubsidio.setPorcentajeAvance(0);
            archivoTransDetaSubsidio.setTipoArchivo("TRANSACCIONES");

            Long idArchivo = consultasCore.persistirArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            archivoTransDetaSubsidio.setIdArchivoTransDetaSubsidio(idArchivo);

            transaccionConsultada.setIdArchivoTransacciones(idArchivo);
            transaccionConsultada.setArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            ExportarArchivoTransaccionesSubsidioAsync exportarArchivoTransaccionesSubsidioAsync =
                    new ExportarArchivoTransaccionesSubsidioAsync(transaccionConsultada);

            exportarArchivoTransaccionesSubsidioAsync.execute();
            respuestaProceso.put("resultado", "Se inicia proceso de generación, por favor verificar en la pestaña \"Exportar\"");
            return respuestaProceso;

        }
        respuestaProceso.put("resultado", "Ya existe un archivo en procesamiento, esperar finalización");
        return respuestaProceso;
    }


    @Override
    public Map<String, String> generarArchivoDetallesSubsidio(@Context UriInfo uriInfo,
                                                              @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            throws FileNotFoundException, IOException {


        Map<String, String> respuestaProceso = new HashMap<String, String>();

        List<ArchivoTransDetaSubsidio> archivoTransDetaSubsidioList =
                consultasCore.consultarArchivoTransDetaSubsidioEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO);


        if (archivoTransDetaSubsidioList == null || archivoTransDetaSubsidioList.isEmpty()) {
            ArchivoTransDetaSubsidio archivoTransDetaSubsidio = new ArchivoTransDetaSubsidio();


            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO.toString());
            archivoTransDetaSubsidio.setFechaGeneracion(new Date());
            archivoTransDetaSubsidio.setPorcentajeAvance(0);
            archivoTransDetaSubsidio.setTipoArchivo("DETALLES");


            Long idArchivo = consultasCore.persistirArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            archivoTransDetaSubsidio.setIdArchivoTransDetaSubsidio(idArchivo);

            transaccionConsultada.setArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

            ExportarArchivoDetallesSubsidioAsync exportarArchivoDetallesSubsidioAsync =
                    new ExportarArchivoDetallesSubsidioAsync(transaccionConsultada);


            exportarArchivoDetallesSubsidioAsync.execute();


            respuestaProceso.put("resultado", "Se inicia proceso de generación, por favor verificar en la pestaña \"Exportar\"");
            return respuestaProceso;

        }

        respuestaProceso.put("resultado", "Ya existe un archivo en procesamiento, esperar finalización");
        return respuestaProceso;
    }


    @Override
    public Map<String, String> generarArchivoTranDetaSubsidio(@Context UriInfo uriInfo,
                                                              @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            throws FileNotFoundException, IOException {


        Map<String, String> respuestaProceso = new HashMap<String, String>();

        List<ArchivoTransDetaSubsidio> archivoTransDetaSubsidioList =
                consultasCore.consultarArchivoTransDetaSubsidioEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO);


        if (archivoTransDetaSubsidioList == null || archivoTransDetaSubsidioList.isEmpty()) {
            ArchivoTransDetaSubsidio archivoTransDetaSubsidio = new ArchivoTransDetaSubsidio();


            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EN_PROCESO.toString());
            archivoTransDetaSubsidio.setFechaGeneracion(new Date());
            archivoTransDetaSubsidio.setPorcentajeAvance(0);
            archivoTransDetaSubsidio.setTipoArchivo("TRANSACCIONES-DETALLES");


            Long idArchivo = consultasCore.persistirArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            archivoTransDetaSubsidio.setIdArchivoTransDetaSubsidio(idArchivo);

            transaccionConsultada.setArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

            ExportarArchivoTransDetallesSubsidioAsync exportarArchivoTransDetallesSubsidioAsync = new ExportarArchivoTransDetallesSubsidioAsync(transaccionConsultada);
            exportarArchivoTransDetallesSubsidioAsync.execute();


            respuestaProceso.put("resultado", "Se inicia proceso de generación, por favor verificar en la pestaña \"Exportar\"");
            return respuestaProceso;

        }

        respuestaProceso.put("resultado", "Ya existe un archivo en procesamiento, esperar finalización");
        return respuestaProceso;
    }


    @Override
    @Asynchronous
    public void exportarArchivoTransaccionesSubsidioAsync(@Context UriInfo uriInfo,
                                                          @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada)
            throws FileNotFoundException, IOException {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.exportarArchivoTransaccionesSubsidio(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoTransDetaSubsidio archivoTransDetaSubsidio = transaccionConsultada.getArchivoTransDetaSubsidio();


        try {
            // De deja de limpiar el buffer dado el GLPI 57373
            //consultasCore.limpiarBufferArchivoTransDetaSubsidio();
            // Se debe consultar de base de datos
            Integer tamanoArchivo = 700000;

            Integer offSetPaginado = 0;

            List<Object[]> transaccionesList = consultasCore.consultarTransaccionesSP(uriInfo, response,
                    transaccionConsultada, offSetPaginado);

            if (transaccionesList == null) {
                transaccionesList = new ArrayList<Object[]>();
            }

            Integer cantidadRegistrosTotal = transaccionesList.size();
            Integer numArchivosGeneradosnumArchivosGenerados = 0;
            Integer porcentajeAvance = 0;

            if (cantidadRegistrosTotal > tamanoArchivo) {
                numArchivosGeneradosnumArchivosGenerados = cantidadRegistrosTotal / tamanoArchivo;

                if ((cantidadRegistrosTotal % tamanoArchivo) > 0) {
                    numArchivosGeneradosnumArchivosGenerados++;
                    porcentajeAvance = 100 / numArchivosGeneradosnumArchivosGenerados;
                }

            } else {
                porcentajeAvance = 80;
            }

            porcentajeAvance = 5;

            File tempZipFile = File.createTempFile("test-data" + 1, ".zip");
            tempZipFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(1);
            int numeroArchivo = 1;
            int cont = 1;
            int cont2 = 1;
            while (transaccionesList != null && !transaccionesList.isEmpty()) {

                Iterator<Object[]> transaccionesIt = transaccionesList.iterator();


                ByteArrayOutputStream baos = null;
                PrintWriter writer = null;

                String ENCABEZADOSTR = "No|Identificador transacción en cuenta de admin subsidio monetario|Fecha y hora de la creacion del registro|Usuario creador del registro|Tipo de transacción|Estado de la transacción|Origen de transacción|Tipo de identificación del Administrador de Subsidios|"
                        + "Numero de identificación del Administrador de Subsidios|Nombres y apellidos del administrador del subsidio|Medio de pago|Número de tarjeta del administrador del subsidio|código del banco del administrador del subsidio|Nombre del banco del administrador del subsidio|"
                        + "Tipo de cuenta del administrador del subsidio|Número de cuenta del administrador del subsidio|Tipo de identificación del titular de la cuenta del administrador del subsidio|Número de identificación del titular de la cuenta del administrador del subsidio|Nombre del titular de la cuenta del administrador del subsidio|"
                        + "Sitio de cobro|Identificación del punto de cobro|Sitio de pago|Nombre tercero pagador|Identificador remision datos tercero pagador|Identificador transacción tercero pagador|Valor original transacción|Valor real transacción|Fecha y hora de la transacción|Usuario que registro transacción|"
                        + "Estado conciliación|Persona autorizada por Admin Subsidio para cobro|Fecha y hora última modificación del registro|Usuario que realizó última modificación|Id relación con otra transacción de la cuenta|Identificador transacción original";

                if (cont2 == 1) {
                    porcentajeAvance = 10;
                } else if (cont2 == 2) {
                    porcentajeAvance = 20;
                } else if (cont2 == 3) {
                    porcentajeAvance = 30;
                } else if (cont2 == 4) {
                    porcentajeAvance = 40;
                } else if (cont2 == 5) {
                    porcentajeAvance = 50;
                } else if (cont2 == 6) {
                    porcentajeAvance = 60;
                } else if (cont2 == 7) {
                    porcentajeAvance = 70;
                } else if (cont2 == 8) {
                    porcentajeAvance = 80;
                } else if (cont2 == 9) {
                    porcentajeAvance = 90;
                } else {
                    porcentajeAvance = 95;
                }
                archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance);
                //archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance * numeroArchivo);
                consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

                baos = new ByteArrayOutputStream();
                OutputStreamWriter baoss = null;

                baoss = new OutputStreamWriter(baos, "UTF-8");
                writer = new PrintWriter(baoss);

                writer.print(ENCABEZADOSTR);
                writer.print('\r');
                writer.print('\n');


                while (transaccionesIt.hasNext()) {

                    String transaccionStr = convertObjectArrToStr(transaccionesIt.next(), cont++);
                    writer.print(transaccionStr);
                    writer.print('\r');
                    writer.print('\n');
                    transaccionesIt.remove();
                }

                writer.flush();
                writer.close();

                String nombreArchivo = "ResultadoConsultaTransacciones" + numeroArchivo + ".csv";
                numeroArchivo++;
                ZipEntry entry = new ZipEntry(nombreArchivo);
                zos.putNextEntry(entry);

                zos.write(baos.toByteArray());
                zos.closeEntry();
                // se libera memoria
                baos = null;
                writer = null;

                java.lang.System.gc();


                offSetPaginado = offSetPaginado + tamanoArchivo;

                transaccionesList = consultasCore.consultarTransaccionesSP(uriInfo, response, transaccionConsultada,
                        offSetPaginado);
                cont2++;

            }

            zos.close();
            byte[] fileContent = null;
            fileContent = Files.readAllBytes(tempZipFile.toPath());

            // Se sube el archivo a cloud
            InformacionArchivoDTO info = new InformacionArchivoDTO();
            info.setDataFile(fileContent);
            info.setFileType("application/zip");
            info.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name());
            info.setDescription("ResultadoConsultaTransacciones");
            info.setDocName("ResultadoConsultaTransacciones" + new Date() + ".zip");
            info.setFileName("ResultadoConsultaTransacciones" + new Date() + ".zip");
            String idECM = almacenarArchivo(info);

            // Se actualiza la tabla de resultado
            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EXITOSO.toString());
            archivoTransDetaSubsidio.setPorcentajeAvance(100);
            archivoTransDetaSubsidio.setIdentificadorECMTranDetalles(idECM);
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

        } catch (Exception e) {

            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.FALLIDO.toString());
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            throw e;
        }

    }


    private ByteArrayOutputStream escribirAArchivo(List<String> listaCuentasAdminSubsidioList) throws UnsupportedEncodingException {


        String ENCABEZADOSTR = "No,Identificador transacción en cuenta de admin subsidio monetario,Fecha y hora de la creacion del registro,Usuario creador del registro,Tipo de transacción,Estado de la transacción,Origen de transacción,Tipo de identificación del Administrador de Subsidios,"
                + "Numero de identificación del Administrador de Subsidios,Nombres y apellidos del administrador del subsidio,Medio de pago,Número de tarjeta del administrador del subsidio,código del banco del administrador del subsidio,Nombre del banco del administrador del subsidio,"
                + "Tipo de cuenta del administrador del subsidio,Número de cuenta del administrador del subsidio,Tipo de identificación del titular de la cuenta del administrador del subsidio,Número de identificación del titular de la cuenta del administrador del subsidio,Nombre del titular de la cuenta del administrador del subsidio,"
                + "Sitio de cobro,Identificación del punto de cobro,Sitio de pago,Nombre tercero pagador,Identificador remision datos tercero pagador,Identificador transacción tercero pagador,Valor original transacción,Valor real transacción,Fecha y hora de la transacción,Usuario que registro transacción,"
                + "Estado conciliación,Persona autorizada por Admin Subsidio para cobro,Fecha y hora última modificación del registro,Usuario que realizó última modificación,Id relación con otra transacción de la cuenta,Identificador transacción original";


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter baoss = null;

        baoss = new OutputStreamWriter(baos, "UTF-8");

        PrintWriter writer = new PrintWriter(baoss);


        writer.print(ENCABEZADOSTR);
        writer.print('\r');
        writer.print('\n');

        for (String registroTmp2 : listaCuentasAdminSubsidioList) {
            writer.print(registroTmp2);
            writer.print('\r');
            writer.print('\n');

        }

        writer.flush();
        return baos;

    }

    private String convertObjectArrToStr(Object[] result, Integer cont) {
        DateFormat dateFormatFecha = new SimpleDateFormat("dd/MM/yyyy");

        String SEPARADOR = "|";
        String EMPTYSTR = "";

        String registroStr = cont.toString();
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(Long.valueOf(result[0].toString()).toString());
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[1] != null ? dateFormatFecha.format((Date) result[1]) : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[2] != null ? result[2].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[3] != null ? TipoTransaccionSubsidioMonetarioEnum.valueOf(result[3].toString()).getDescripcion()
                        : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[4] != null ? EstadoTransaccionSubsidioEnum.valueOf(result[4].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr
                .concat(result[5] != null ? OrigenTransaccionEnum.valueOf(result[5].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[31] != null ? TipoIdentificacionEnum.valueOf(result[31].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[32] != null ? result[32].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[29] != null ? result[29].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr
                .concat(result[6] != null ? TipoMedioDePagoEnum.valueOf(result[6].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[7] != null ? result[7].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[8] != null ? result[8].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[9] != null ? result[9].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr
                .concat(result[10] != null ? TipoCuentaEnum.valueOf(result[10].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[11] != null ? result[11].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[12] != null ? TipoIdentificacionEnum.valueOf(result[12].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[13] != null ? result[13].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[14] != null ? result[14].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[27] != null && result[28] != null ? result[27].toString() + "-" + result[28].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[33] != null ? result[33].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[26] != null ? result[26].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[22] != null ? result[22].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[20] != null ? result[20].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[21] != null ? result[21].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[17] != null ? BigDecimal.valueOf(Double.parseDouble(result[17].toString())).toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(
                result[18] != null ? BigDecimal.valueOf(Double.parseDouble(result[18].toString())).toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[15] != null ? dateFormatFecha.format((Date) result[15]) : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[16] != null ? result[16].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(EMPTYSTR);
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[30] != null ? result[30].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[24] != null ? dateFormatFecha.format((Date) result[24]) : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[25] != null ? result[25].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[23] != null ? result[23].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[19] != null ? result[19].toString() : "");

        return registroStr;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesSubsidio(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidio(UriInfo uriInfo,
                                                                      HttpServletResponse response, DetalleTransaccionAsignadoConsultadoDTO detalleConsultado) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDetallesSubsidio(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultasCore.consultarDetallesPaginado(detalleConsultado, uriInfo, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }


    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDescuentosSubsidioAsignado(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DescuentoSubsidioAsignadoDTO> consultarDescuentosSubsidioAsignado(Long idDetalleSubsidioAsignado) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDescuentosSubsidioAsignado(idDetalleSubsidioAsignado)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DescuentoSubsidioAsignadoDTO> listaDescuentos = consultasCore.consultarDescuentosSubsidio(idDetalleSubsidioAsignado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDescuentos;
    }


    @Override
    @Asynchronous
    public void exportarArchivoDetallesSubsidioAsync(@Context UriInfo uriInfo,
                                                     @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) throws IOException {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.exportarArchivoDetallesSubsidioAsync(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.debug("inicio consulta: " + Calendar.getInstance().getTime());


        ArchivoTransDetaSubsidio archivoTransDetaSubsidio = transaccionConsultada.getArchivoTransDetaSubsidio();


        try {

            //consultasCore.limpiarBufferArchivoTransDetaSubsidio();
            // Se debe consultar de base de datos
            Integer tamanoArchivo = 700000;
            Integer offSetPaginado = 0;


            List<Object[]> listaDetalles = consultasCore.consultarDetallesSP(uriInfo, response, transaccionConsultada, offSetPaginado);

            if (listaDetalles == null) {
                listaDetalles = new ArrayList<Object[]>();
            }

            Integer cantidadRegistrosTotal = listaDetalles.size();
            Integer numArchivosGeneradosnumArchivosGenerados = 0;
            Integer porcentajeAvance = 0;

            if (cantidadRegistrosTotal > tamanoArchivo) {
                numArchivosGeneradosnumArchivosGenerados = cantidadRegistrosTotal / tamanoArchivo;

                if ((cantidadRegistrosTotal % tamanoArchivo) > 0) {
                    numArchivosGeneradosnumArchivosGenerados++;
                    porcentajeAvance = 100 / numArchivosGeneradosnumArchivosGenerados;
                }

            } else {
                porcentajeAvance = 80;
            }

            porcentajeAvance = 5;
            //   int porcentaje=0;
            File tempZipFile = File.createTempFile("test-data" + 1, ".zip");
            tempZipFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(1);
            int numeroArchivo = 1;
            int cont = 1;

            int cont2 = 1;
            while (listaDetalles != null && !listaDetalles.isEmpty()) {

                Iterator<Object[]> detallesIt = listaDetalles.iterator();
                ByteArrayOutputStream baos = null;
                PrintWriter writer = null;


                String ENCABEZADOSTR = "No|Identificador subsidio asignado|Fecha y hora creación registro|Usuario creador del registro|Periodo liquidado|"
                        + "Estado subsidio asignado|Motivo anulación|Tipo Identificación Empleador relacionado|Número Identificación Empleador relacionado|"
                        + "Empleador relacionado|Tipo de identificación Afiliado principal relacionado|Número de identificación Afiliado principal relacionado|"
                        + "Afiliado principal relacionado|Grupo familiar relacionado|Tipo de identificación Beneficiario del subsidio|"
                        + "Número de identificación Beneficiario del subsidio|Beneficiario del subsidio|Tipo de identificación administrador subsidio relacionado|"
                        + "Número de identificación administrador subsidio relacionado|Administrador del subsidio relacionado|"
                        + "Medio de pago|Número de tarjeta del administrador del subsidio|codigo del banco del administrador del subsidio|"
                        + "Nombre del banco del administrador del subsidio|Tipo de cuenta del administrador del subsidio|Número de cuenta del administrador del subsidio|"
                        + "Tipo de identificación del titular de la cuenta del administrador del subsidio|Número de identificación del titular de la cuenta del administrador del subsidio|"
                        + "Nombre del titular de la cuenta del administrador del subsidio|Origen del registro|Id proceso liquidación asociada|"
                        + "Fecha de liquidación asociada|Tipo liquidación|Tipo cuota|Valor subsidio asignado|Valor descuentos|"
                        + "Valor original abonado|Valor total|Fecha transacción retiro relacionada|Usuario que registró transacción de retiro|"
                        + "Fecha transacción anulación|Usuario transacción anulación|Fecha y hora última modificación del registro|"
                        + "Usuario que realizó última modificación|Identificador registro original relacionado|Identificador transacción en cuenta de admin subsidio monetario";

                // porcentaje= (numeroArchivo * 100/ cantidadRegistrosTotal);
                if (cont2 == 1) {
                    porcentajeAvance = 10;
                } else if (cont2 == 2) {
                    porcentajeAvance = 20;
                } else if (cont2 == 3) {
                    porcentajeAvance = 30;
                } else if (cont2 == 4) {
                    porcentajeAvance = 40;
                } else if (cont2 == 5) {
                    porcentajeAvance = 50;
                } else if (cont2 == 6) {
                    porcentajeAvance = 60;
                } else if (cont2 == 7) {
                    porcentajeAvance = 70;
                } else if (cont2 == 8) {
                    porcentajeAvance = 80;
                } else if (cont2 == 9) {
                    porcentajeAvance = 90;
                } else {
                    porcentajeAvance = 95;
                }
                archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance);
                //archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance * numeroArchivo);
                //   archivoTransDetaSubsidio.setPorcentajeAvance(porcentaje);
                consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

                baos = new ByteArrayOutputStream();
                OutputStreamWriter baoss = null;

                baoss = new OutputStreamWriter(baos, "UTF-8");
                writer = new PrintWriter(baoss);

                writer.print(ENCABEZADOSTR);
                writer.print('\r');
                writer.print('\n');

                while (detallesIt.hasNext()) {

                    String detalleStr = convertObjectArrToDetalleStr(detallesIt.next(), cont++);
                    writer.print(detalleStr);
                    writer.print('\r');
                    writer.print('\n');
                    detallesIt.remove();
                }

                writer.flush();
                writer.close();

                String nombreArchivo = "ResultadoConsultaDetalles" + numeroArchivo + ".csv";
                numeroArchivo++;
                ZipEntry entry = new ZipEntry(nombreArchivo);
                zos.putNextEntry(entry);
                zos.write(baos.toByteArray());
                zos.closeEntry();

                // se libera memoria
                baos = null;
                writer = null;

                java.lang.System.gc();
                offSetPaginado = offSetPaginado + tamanoArchivo;
                listaDetalles = consultasCore.consultarDetallesSP(uriInfo, response, transaccionConsultada, offSetPaginado);
                cont2++;
            }

            zos.close();
            byte[] fileContent = null;
            fileContent = Files.readAllBytes(tempZipFile.toPath());

            //Se sube el archivo a cloud
            InformacionArchivoDTO info = new InformacionArchivoDTO();
            info.setDataFile(fileContent);
            info.setFileType("application/zip");
            info.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name());
            info.setDescription("ResultadoConsultaDetalles");
            info.setDocName("ResultadoConsultaDetalles" + new Date() + ".zip");
            info.setFileName("ResultadoConsultaDetalles" + new Date() + ".zip");
            String idECM = almacenarArchivo(info);

            // Se actualiza la tabla de resultado
            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EXITOSO.toString());
            archivoTransDetaSubsidio.setPorcentajeAvance(100);
            archivoTransDetaSubsidio.setIdentificadorECMTranDetalles(idECM);
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

        } catch (Exception e) {

            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.FALLIDO.toString());
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            throw e;
        }

        logger.debug("fin procesamiento: " + Calendar.getInstance().getTime());
    }


    private String convertObjectArrToDetalleStr(Object[] result, Integer consecutivo) {
        DateFormat dateFormatFecha = new SimpleDateFormat("dd/MM/yyyy");

        String SEPARADOR = "|";
        String registroStr = consecutivo.toString();
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(Long.valueOf(result[0].toString()).toString());
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[1] != null ? dateFormatFecha.format((Date) result[1]) : "");//fecha creación
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[2] != null ? result[2].toString() : ""); //usuarioCreacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[3] != null ? dateFormatFecha.format((Date) result[3]) : "");//Periodo liquidado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[4] != null ? EstadoSubsidioAsignadoEnum.valueOf(result[4].toString()).getDescripcion() : "");//Estado subsidio asignado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[5] != null ? MotivoAnulacionSubsidioAsignadoEnum.valueOf(result[5].toString()).getDescripcion() : ""); // Motivo anulación
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[6] != null ? TipoIdentificacionEnum.valueOf(result[6].toString()).getDescripcion() : ""); // tipo id empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[7] != null ? result[7].toString() : ""); // numero id empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[8] != null ? result[8].toString().replaceAll(SEPARADOR, "") : ""); // nombre empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[9] != null ? TipoIdentificacionEnum.valueOf(result[9].toString()).getDescripcion() : ""); // tipo id afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[10] != null ? result[10].toString() : ""); // numero id afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[11] != null ? result[11].toString() : ""); // nombre afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[12] != null ? result[12].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[13] != null ? TipoIdentificacionEnum.valueOf(result[13].toString()).getDescripcion() : ""); // tipo id beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[14] != null ? result[14].toString() : ""); // numero id beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[15] != null ? result[15].toString() : ""); // nombre beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[16] != null ? TipoIdentificacionEnum.valueOf(result[16].toString()).getDescripcion() : ""); // tipo id admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[17] != null ? result[17].toString() : ""); // numero id admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[18] != null ? result[18].toString() : ""); // nombre admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[19] != null ? TipoMedioDePagoEnum.valueOf(result[19].toString()).getDescripcion() : "");//medio de pago
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[20] != null ? result[20].toString() : ""); //num tarjeta admin subsidio


        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[21] != null ? result[21].toString() : ""); //codigo banco admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[22] != null ? result[22].toString() : ""); //nombre del banco
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[23] != null ? TipoCuentaEnum.valueOf(result[23].toString()).getDescripcion() : ""); //Tipo cuenta admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[24] != null ? result[24].toString() : ""); //numero cuenta admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[25] != null ? TipoIdentificacionEnum.valueOf(result[25].toString()).getDescripcion() : "");//tipoId titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[26] != null ? result[26].toString() : ""); // num id titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[27] != null ? result[27].toString() : ""); // nombre id titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[28] != null ? OrigenRegistroSubsidioAsignadoEnum.valueOf(result[28].toString()).getDescripcion() : ""); //origen registro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[29] != null ? result[29].toString() : ""); //id proceso liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[30] != null ? dateFormatFecha.format((Date) result[30]) : ""); //fecha liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[31] != null ? TipoLiquidacionSubsidioEnum.valueOf(result[31].toString()).getDescripcion() : ""); //tipo liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[32] != null ? TipoCuotaSubsidioEnum.valueOf(result[32].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[33] != null ? BigDecimal.valueOf(Double.parseDouble(result[33].toString())).toString() : ""); //valor subsidio asignado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[34] != null ? BigDecimal.valueOf(Double.parseDouble(result[34].toString())).toString() : ""); //valor descuento
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[35] != null ? BigDecimal.valueOf(Double.parseDouble(result[35].toString())).toString() : ""); //valor original abono
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[36] != null ? BigDecimal.valueOf(Double.parseDouble(result[36].toString())).toString() : ""); //valor total
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[37] != null ? dateFormatFecha.format((Date) result[37]) : ""); //fecha transaccion retiro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[38] != null ? result[38].toString() : "");//usuario transaccion retiro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[39] != null ? dateFormatFecha.format((Date) result[39]) : ""); //fecha transaccion anulacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[40] != null ? result[40].toString() : "");//usuario transaccion anulacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[41] != null ? dateFormatFecha.format((Date) result[41]) : ""); //fecha ultima modificacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[42] != null ? result[42].toString() : "");//usuario ultima modificacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[43] != null ? result[43].toString() : "");//id registro relacionado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[44] != null ? result[44].toString() : "");//id cuenta admin

        return registroStr;
    }

    private String convertObjectArrToTransDetalleStr(Object[] result, Integer consecutivo) {
        DateFormat dateFormatFecha = new SimpleDateFormat("dd/MM/yyyy");
        String SEPARADOR = "|";
        String registroStr = consecutivo.toString();
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[45] != null ? result[45].toString() : ""); //idCuentaAdministradorSubsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[0] != null ? result[0].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[1] != null ? dateFormatFecha.format((Date) result[1]) : "");//fecha creación
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[2] != null ? result[2].toString() : ""); //usuarioCreacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[46] != null ? result[46].toString() : ""); //Tipo de transacción
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[47] != null ? result[47].toString() : ""); //Estado de la transacción
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[48] != null ? result[48].toString() : ""); //Sitio de cobro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[49] != null ? result[49].toString() : ""); //Sitio de pago
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[50] != null ? result[50].toString() : ""); //nombreTerceroPagado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[51] != null ? result[51].toString() : ""); //Identificador remision datos tercero pagador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[52] != null ? result[52].toString() : ""); //Identificador transacción tercero pagador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[53] != null ? BigDecimal.valueOf(Double.parseDouble(result[53].toString())).toString() : "");//Valor original transacción
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[54] != null ? BigDecimal.valueOf(Double.parseDouble(result[54].toString())).toString() : ""); //Valor real transacción
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[3] != null ? dateFormatFecha.format((Date) result[3]) : "");//Periodo liquidado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[4] != null ? EstadoSubsidioAsignadoEnum.valueOf(result[4].toString()).getDescripcion() : "");//Estado subsidio asignado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[5] != null ? MotivoAnulacionSubsidioAsignadoEnum.valueOf(result[5].toString()).getDescripcion() : ""); // Motivo anulación
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[6] != null ? TipoIdentificacionEnum.valueOf(result[6].toString()).getDescripcion() : ""); // tipo id empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[7] != null ? result[7].toString() : ""); // numero id empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[8] != null ? result[8].toString().replaceAll(SEPARADOR, "") : ""); // nombre empleador
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[9] != null ? TipoIdentificacionEnum.valueOf(result[9].toString()).getDescripcion() : ""); // tipo id afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[10] != null ? result[10].toString() : ""); // numero id afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[11] != null ? result[11].toString() : ""); // nombre afiliado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[12] != null ? result[12].toString() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[13] != null ? TipoIdentificacionEnum.valueOf(result[13].toString()).getDescripcion() : ""); // tipo id beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[14] != null ? result[14].toString() : ""); // numero id beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[15] != null ? result[15].toString() : ""); // nombre beneficiario
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[16] != null ? TipoIdentificacionEnum.valueOf(result[16].toString()).getDescripcion() : ""); // tipo id admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[17] != null ? result[17].toString() : ""); // numero id admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[18] != null ? result[18].toString() : ""); // nombre admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[19] != null ? TipoMedioDePagoEnum.valueOf(result[19].toString()).getDescripcion() : "");//medio de pago
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[20] != null ? result[20].toString() : ""); //num tarjeta admin subsidio
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[21] != null ? result[21].toString() : ""); //codigo banco admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[22] != null ? result[22].toString() : ""); //nombre del banco
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[23] != null ? TipoCuentaEnum.valueOf(result[23].toString()).getDescripcion() : ""); //Tipo cuenta admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[24] != null ? result[24].toString() : ""); //numero cuenta admin
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[25] != null ? TipoIdentificacionEnum.valueOf(result[25].toString()).getDescripcion() : "");//tipoId titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[26] != null ? result[26].toString() : ""); // num id titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[27] != null ? result[27].toString() : ""); // nombre id titular cuenta
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[28] != null ? OrigenTransaccionEnum.valueOf(result[28].toString()).getDescripcion() : ""); //origen registro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[29] != null ? result[29].toString() : ""); //id proceso liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[30] != null ? dateFormatFecha.format((Date) result[30]) : ""); //fecha liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[31] != null ? TipoLiquidacionSubsidioEnum.valueOf(result[31].toString()).getDescripcion() : ""); //tipo liquidacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[32] != null ? TipoCuotaSubsidioEnum.valueOf(result[32].toString()).getDescripcion() : "");
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[33] != null ? BigDecimal.valueOf(Double.parseDouble(result[33].toString())).toString() : ""); //valor subsidio asignado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[34] != null ? BigDecimal.valueOf(Double.parseDouble(result[34].toString())).toString() : ""); //valor descuento
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[35] != null ? BigDecimal.valueOf(Double.parseDouble(result[35].toString())).toString() : ""); //valor original abono
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[36] != null ? BigDecimal.valueOf(Double.parseDouble(result[36].toString())).toString() : ""); //valor total
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[37] != null ? dateFormatFecha.format((Date) result[37]) : ""); //fecha transaccion retiro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[38] != null ? result[38].toString() : "");//usuario transaccion retiro
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[39] != null ? dateFormatFecha.format((Date) result[39]) : ""); //fecha transaccion anulacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[40] != null ? result[40].toString() : "");//usuario transaccion anulacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[41] != null ? dateFormatFecha.format((Date) result[41]) : ""); //fecha ultima modificacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[42] != null ? result[42].toString() : "");//usuario ultima modificacion
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[43] != null ? result[43].toString() : "");//id registro relacionado
        registroStr = registroStr.concat(SEPARADOR);
        registroStr = registroStr.concat(result[44] != null ? result[44].toString() : "");//id cuenta admin
        return registroStr;
    }

    @Override
    @Asynchronous
    public void exportarArchivoTransDetallesSubsidioAsync(@Context UriInfo uriInfo,
                                                          @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) throws IOException {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.exportarArchivoTransDetallesSubsidioAsync(TransaccionConsultada)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.debug("inicio consulta: " + Calendar.getInstance().getTime());

        logger.info("transaccionConsultada " +transaccionConsultada.toString());


        ArchivoTransDetaSubsidio archivoTransDetaSubsidio = transaccionConsultada.getArchivoTransDetaSubsidio();


        try {

            // GLPI 57373 ajuste de consulta detalle subsidio

            // consultasCore.limpiarBufferArchivoTransDetaSubsidio();
            // Se debe consultar de base de datos
            Integer tamanoArchivo = 700000;
            Integer offSetPaginado = 0;


            List<Object[]> listaDetalles = consultasCore.consultarTransDetallesSP(uriInfo, response, transaccionConsultada, offSetPaginado);

            if (listaDetalles == null) {

                listaDetalles = new ArrayList<Object[]>();

            }

            Integer cantidadRegistrosTotal = listaDetalles.size();
            Integer numArchivosGeneradosnumArchivosGenerados = 0;
            Integer porcentajeAvance = 0;

            if (cantidadRegistrosTotal > tamanoArchivo) {
                numArchivosGeneradosnumArchivosGenerados = cantidadRegistrosTotal / tamanoArchivo;

                if ((cantidadRegistrosTotal % tamanoArchivo) > 0) {
                    numArchivosGeneradosnumArchivosGenerados++;
                    porcentajeAvance = 100 / numArchivosGeneradosnumArchivosGenerados;
                }

            } else {
                porcentajeAvance = 80;
            }

            porcentajeAvance = 5;

            File tempZipFile = File.createTempFile("test-data" + 1, ".zip");
            tempZipFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(1);
            int numeroArchivo = 1;
            int cont = 1;
            int cont2 = 1;

            while (listaDetalles != null && !listaDetalles.isEmpty()) {

                Iterator<Object[]> detallesIt = listaDetalles.iterator();
                ByteArrayOutputStream baos = null;
                PrintWriter writer = null;


                String ENCABEZADOSTR = "No|Identificador transacción en cuenta de admin subsidio monetario|Identificador subsidio asignado|Fecha y hora creación registro|Usuario creador del registro|Tipo de transacción|"
                        + "Estado de la transacción|Sitio de cobro|Sitio de pago|Nombre tercero pagador|"
                        + "Identificador remision datos tercero pagador|Identificador transacción tercero pagador|"
                        + "Valor original transacción|Valor real transacción|"
                        + "Periodo liquidado|"
                        + "Estado subsidio asignado|Motivo anulación|Tipo Identificación Empleador relacionado|Número Identificación Empleador relacionado|"
                        + "Empleador relacionado|Tipo de identificación Afiliado principal relacionado|Número de identificación Afiliado principal relacionado|"
                        + "Afiliado principal relacionado|Grupo familiar relacionado|Tipo de identificación Beneficiario del subsidio|"
                        + "Número de identificación Beneficiario del subsidio|Beneficiario del subsidio|Tipo de identificación administrador subsidio relacionado|"
                        + "Número de identificación administrador subsidio relacionado|Administrador del subsidio relacionado|"
                        + "Medio de pago|Número de tarjeta del administrador del subsidio|codigo del banco del administrador del subsidio|"
                        + "Nombre del banco del administrador del subsidio|Tipo de cuenta del administrador del subsidio|Número de cuenta del administrador del subsidio|"
                        + "Tipo de identificación del titular de la cuenta del administrador del subsidio|Número de identificación del titular de la cuenta del administrador del subsidio|"
                        + "Nombre del titular de la cuenta del administrador del subsidio|Origen del registro|Id proceso liquidación asociada|"
                        + "Fecha de liquidación asociada|Tipo liquidación|Tipo cuota|Valor subsidio asignado|Valor descuentos|"
                        + "Valor original abonado|Valor total|Fecha transacción retiro relacionada|Usuario que registró transacción de retiro|"
                        + "Fecha transacción anulación|Usuario transacción anulación|Fecha y hora última modificación del registro|"
                        + "Usuario que realizó última modificación|Identificador registro original relacionado|Identificador transacción en cuenta de admin subsidio monetario";

                if (cont2 == 1) {
                    porcentajeAvance = 10;
                } else if (cont2 == 2) {
                    porcentajeAvance = 20;
                } else if (cont2 == 3) {
                    porcentajeAvance = 30;
                } else if (cont2 == 4) {
                    porcentajeAvance = 40;
                } else if (cont2 == 5) {
                    porcentajeAvance = 50;
                } else if (cont2 == 6) {
                    porcentajeAvance = 60;
                } else if (cont2 == 7) {
                    porcentajeAvance = 70;
                } else if (cont2 == 8) {
                    porcentajeAvance = 80;
                } else if (cont2 == 9) {
                    porcentajeAvance = 90;
                } else {
                    porcentajeAvance = 95;
                }
                logger.info("**__**porcentajeAvance: " + porcentajeAvance);
                // archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance * numeroArchivo);
                archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance);
                consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

                baos = new ByteArrayOutputStream();
                OutputStreamWriter baoss = null;

                baoss = new OutputStreamWriter(baos, "UTF-8");
                writer = new PrintWriter(baoss);

                writer.print(ENCABEZADOSTR);
                writer.print('\r');
                writer.print('\n');

                while (detallesIt.hasNext()) {

                    String detalleStr = convertObjectArrToTransDetalleStr(detallesIt.next(), cont++);
                    writer.print(detalleStr);
                    writer.print('\r');
                    writer.print('\n');
                    detallesIt.remove();
                }

                writer.flush();
                writer.close();

                String nombreArchivo = "ResultadoTransaccionesDetalles" + numeroArchivo + ".csv";
                numeroArchivo++;
                ZipEntry entry = new ZipEntry(nombreArchivo);
                zos.putNextEntry(entry);
                zos.write(baos.toByteArray());
                zos.closeEntry();

                // se libera memoria
                baos = null;
                writer = null;

                java.lang.System.gc();
                offSetPaginado = offSetPaginado + tamanoArchivo;
                listaDetalles = consultasCore.consultarTransDetallesSP(uriInfo, response, transaccionConsultada, offSetPaginado);
                cont2++;
            }

            zos.close();
            byte[] fileContent = null;
            fileContent = Files.readAllBytes(tempZipFile.toPath());

            //Se sube el archivo a cloud
            InformacionArchivoDTO info = new InformacionArchivoDTO();
            info.setDataFile(fileContent);
            info.setFileType("application/zip");
            info.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name());
            info.setDescription("ResultadoTransaccioneDetalles");
            info.setDocName("ResultadoTransaccioneDetalles" + new Date() + ".zip");
            info.setFileName("ResultadoTransaccioneDetalles" + new Date() + ".zip");
            String idECM = almacenarArchivo(info);

            // Se actualiza la tabla de resultado
            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EXITOSO.toString());
            archivoTransDetaSubsidio.setPorcentajeAvance(100);
            archivoTransDetaSubsidio.setIdentificadorECMTranDetalles(idECM);
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);

        } catch (Exception e) {

            archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.FALLIDO.toString());
            consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
            throw e;
        }

        logger.debug("fin procesamiento: " + Calendar.getInstance().getTime());
    }

//   @Override
//   @Asynchronous
//   public void exportarArchivoTransDetallesSubsidioAsync(@Context UriInfo uriInfo,
//           @Context HttpServletResponse response, @Valid DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada) throws IOException {
//   	
//   	String firmaServicio = "PagosSubsidioMonetarioBusiness.exportarArchivoTransDetallesSubsidioAsync(TransaccionConsultada)";
//       logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
//       logger.debug("inicio consulta: " + Calendar.getInstance().getTime());
//       
//       
//       ArchivoTransDetaSubsidio archivoTransDetaSubsidio =transaccionConsultada.getArchivoTransDetaSubsidio();
//       try {
//       	
//       	consultasCore.limpiarBufferArchivoTransDetaSubsidio();
//       	// Se debe consultar de base de datos
//       	Integer tamanoArchivo = 500000;
//       	Integer offSetPaginado = 0;			
//       	List<Object[]> listaDetalles = consultasCore.consultarTransDetallesSP(uriInfo, response, transaccionConsultada,offSetPaginado);
//
//       	if(listaDetalles== null) {
//       		
//       		listaDetalles =  new ArrayList<Object[]>();
//       	}
//           logger.info("**__**listaDetalles.size()1: " + listaDetalles.size());
//       	Integer cantidadRegistrosTotal = listaDetalles.size();
//			Integer numArchivosGeneradosnumArchivosGenerados = 0;
//			Integer porcentajeAvance = 0;
//			if (cantidadRegistrosTotal > tamanoArchivo) {
//				numArchivosGeneradosnumArchivosGenerados = cantidadRegistrosTotal / tamanoArchivo;
//
//				if ((cantidadRegistrosTotal % tamanoArchivo) > 0) {
//					numArchivosGeneradosnumArchivosGenerados++;
//					porcentajeAvance = 100 / numArchivosGeneradosnumArchivosGenerados;
//				}
//
//			} else {
//				porcentajeAvance = 80;
//			}
//			
//			porcentajeAvance= 2;
//			File tempZipFile = File.createTempFile("test-data" + 1, ".zip");
//			tempZipFile.deleteOnExit();
//			FileOutputStream fos = new FileOutputStream(tempZipFile);
//			ZipOutputStream zos = new ZipOutputStream(fos);
//			zos.setLevel(1);
//			int numeroArchivo = 1;
//			int cont = 1;
//           Integer cont2 = 1;
//           Integer cantidadTotal= listaDetalles.size();
//       	while(listaDetalles !=null && !listaDetalles.isEmpty()) {          	
//           	Iterator<Object[]> detallesIt = listaDetalles.iterator();
//           	ByteArrayOutputStream baos = null;
//           	PrintWriter writer = null;
//           
//           	
//           	String ENCABEZADOSTR = "No,Identificador transacción en cuenta de admin subsidio monetario,Identificador subsidio asignado,Fecha y hora creación registro,Usuario creador del registro,Tipo de transacción,"
//           			+ "Estado de la transacción,Sitio de cobro,Sitio de pago,Nombre tercero pagador,"
//           			+ "Identificador remision datos tercero pagador,Identificador transacción tercero pagador,"
//           			+ "Valor original transacción,Valor real transacción,"
//           			+ "Periodo liquidado,"
//       				+ "Estado subsidio asignado,Motivo anulación,Tipo Identificación Empleador relacionado,Número Identificación Empleador relacionado,"
//       				+ "Empleador relacionado,Tipo de identificación Afiliado principal relacionado,Número de identificación Afiliado principal relacionado,"
//       				+ "Afiliado principal relacionado,Grupo familiar relacionado,Tipo de identificación Beneficiario del subsidio,"
//       				+ "Número de identificación Beneficiario del subsidio,Beneficiario del subsidio,Tipo de identificación administrador subsidio relacionado,"
//       				+ "Número de identificación administrador subsidio relacionado,Administrador del subsidio relacionado,"
//       				+ "Medio de pago,Número de tarjeta del administrador del subsidio,codigo del banco del administrador del subsidio,"
//       				+ "Nombre del banco del administrador del subsidio,Tipo de cuenta del administrador del subsidio,Número de cuenta del administrador del subsidio,"
//       				+ "Tipo de identificación del titular de la cuenta del administrador del subsidio,Número de identificación del titular de la cuenta del administrador del subsidio,"
//       				+ "Nombre del titular de la cuenta del administrador del subsidio,Origen del registro,Id proceso liquidación asociada,"
//       				+ "Fecha de liquidación asociada,Tipo liquidación,Tipo cuota,Valor subsidio asignado,Valor descuentos,"
//       				+ "Valor original abonado,Valor total,Fecha transacción retiro relacionada,Usuario que registró transacción de retiro,"
//       				+ "Fecha transacción anulación,Usuario transacción anulación,Fecha y hora última modificación del registro,"
//       				+ "Usuario que realizó última modificación,Identificador registro original relacionado,Identificador transacción en cuenta de admin subsidio monetario";
//
//               logger.info("**__**calculo : cont2: "+cont2 + "listaDetalles.size()" +listaDetalles.size());
//               porcentajeAvance= (cont2 *  cantidadTotal)/1000;
//               if(porcentajeAvance>97){
//                   porcentajeAvance=porcentajeAvance+1;
//               }
//           	archivoTransDetaSubsidio.setPorcentajeAvance(porcentajeAvance);
//          //     logger.info("**__**listaDetalles--inicio while: "+contar+" porcentaje:"+(porcentajeAvance * numeroArchivo));
//				consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
//				baos = new ByteArrayOutputStream();
//				OutputStreamWriter baoss = null;
//				baoss = new OutputStreamWriter(baos, "UTF-8");
//				writer = new PrintWriter(baoss);
//				writer.print(ENCABEZADOSTR);
//				writer.print('\r');
//				writer.print('\n');
//               logger.info("**__**porcentaje: " + porcentajeAvance);
//				while (detallesIt.hasNext()) {
//                     String detalleStr = convertObjectArrToTransDetalleStr(detallesIt.next(), cont++);
//                       writer.print(detalleStr);
//                       writer.print('\r'); 
//                       writer.print('\n');
//                       detallesIt.remove();
//                      
//				}
//           	  	
//				writer.flush();
//				writer.close();
//           	     	
//				String nombreArchivo = "ResultadoTransaccionesDetalles" + numeroArchivo + ".csv";
//				numeroArchivo++;
//				ZipEntry entry = new ZipEntry(nombreArchivo);
//				zos.putNextEntry(entry);
//				zos.write(baos.toByteArray());
//				zos.closeEntry();
//
//				// se libera memoria
//				baos = null;
//				writer = null;
//				
//				java.lang.System.gc();	
//				offSetPaginado = offSetPaginado + tamanoArchivo;
//				listaDetalles = consultasCore.consultarTransDetallesSP(uriInfo, response, transaccionConsultada,offSetPaginado);				
//               logger.info("**__**listaDetalles.size()2: " + listaDetalles.size());
//               cantidadTotal += listaDetalles.size();
//               cont2++;
//           }
//       	
//       	zos.close();
//			byte[] fileContent = null;
//			fileContent = Files.readAllBytes(tempZipFile.toPath());
//
//			//Se sube el archivo a cloud
//			InformacionArchivoDTO info = new InformacionArchivoDTO();
//			info.setDataFile(fileContent);
//			info.setFileType("application/zip");
//			info.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name());
//			info.setDescription("ResultadoTransaccioneDetalles");
//			info.setDocName("ResultadoTransaccioneDetalles"+new Date()+".zip");
//			info.setFileName("ResultadoTransaccioneDetalles"+new Date()+".zip");
//			String idECM = almacenarArchivo(info);
//
//			// Se actualiza la tabla de resultado
//			archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.EXITOSO.toString());
//			archivoTransDetaSubsidio.setPorcentajeAvance(100);
//			archivoTransDetaSubsidio.setIdentificadorECMTranDetalles(idECM);
//			consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
//
//		} catch (Exception e) {
//			archivoTransDetaSubsidio.setEstado(EstadoArchivoTransDetaSubsidioEnum.FALLIDO.toString());
//			consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
//			throw e;
//		}
//           	
//       logger.debug("fin procesamiento: " + Calendar.getInstance().getTime());
//   }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarTransaccionesDetallesSubsidios(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesDetallesSubsidios(@Context UriInfo uriInfo,
                                                                                        @Context HttpServletResponse response,
                                                                                        DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarTransaccionesDetallesSubsidios(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> listaTransaccionDetalle =
                consultasCore.consultarTransaccionesTodosFiltrosSP(uriInfo, response,
                        transaccionDetalleSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaTransaccionDetalle;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#
     * (com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> consultarSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                      TipoMedioDePagoEnum medioDePago, UserDTO userDTO, String user) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarSaldoSubsidio(TipoIdentificacionEnum,String,TipoMedioDePagoEnum,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Date fechaHoraInicio = new Date();
        String url = "consultarSaldoSubsidio";
        Long identificadorRespuesta = null;

        if (userDTO.getNombreUsuario() == null) {
            logger.info("Entra");
            logger.info("Entra user" + user);
            userDTO.setNombreUsuario(user);
        }
        if (userDTO.getNombreUsuario() != null) {
            logger.info("Entra diferente");
            logger.info("Entra diferente" + userDTO.getEmail());
        }
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();

        Map<String, String> respuesta = new HashMap<>();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdAdmin);
        parametrosEntrada.put("medioDePago", medioDePago);
        parametrosEntrada.put("usuario", user);

        salida.append(gson.toJson(parametrosEntrada));

        String parametrosIN = salida.toString();
        String parametrosOUT = null;


        Integer valor = consultasCore.consultarTransaccionProceso(String.valueOf(tipoIdAdmin), numeroIdAdmin);
        logger.info("valor consultar saldo " + valor);
        if (valor != null && valor == 0) {
            Map<String, Object> saldoFavorNombreAdmin = consultasCore.consultarSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);

            if (saldoFavorNombreAdmin != null) {

                BigDecimal saldoAbonos = (BigDecimal) saldoFavorNombreAdmin.get("valorSaldo");
                String nombreAdmin = (String) saldoFavorNombreAdmin.get("nombreAdmin");
                Long idAdminSubsidio = (Long) saldoFavorNombreAdmin.get("idAdminSubsidio");

                //se registra la operación
                identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONSULTAR_SALDO,
                        parametrosIN, user == null ? userDTO.getEmail() : user, idAdminSubsidio);

                respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));

                if (saldoAbonos.doubleValue() != 0) {

                    respuesta.put("nombreAdminSubsidio", nombreAdmin);
                    respuesta.put("saldoAdminSubsidio", String.valueOf(saldoAbonos.doubleValue()));

                } else {
                    respuesta.put("resultado", String.valueOf(false));
                    respuesta.put("saldoAdminSubsidio", String.valueOf("0"));
                }


            } else {

                //SE REGISTRA TRANSACCIÓN FALLIDA SI OCURRE ALGUN ERROR

                //se registra la operación
                identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONSULTAR_SALDO,
                        parametrosIN, user != null ? user : userDTO.getNombreUsuario(), null);

//            //se registra la transacción fallida de cada abono que se solicito para el retiro anterior.
//            TransaccionFallidaDTO transaccionFallidaAbono = new TransaccionFallidaDTO();
//            transaccionFallidaAbono.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
//
//            //se crea la transacción fallida
//            Long idTransaccionFallida = gestionarTransaccionesFallidas(transaccionFallidaAbono);
//            //se asocia la transacción fallida con el registro de operación
//            consultasCore.registrarTransaccionesFallidasRegistroOperacionesSubsidio(identificadorRespuesta, idTransaccionFallida);

                respuesta.put("resultado", String.valueOf(false));
                respuesta.put("saldoAdminSubsidio", String.valueOf("0"));
                respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));

            }
        } else {
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONSULTAR_SALDO,
                    parametrosIN, user != null ? user : userDTO.getNombreUsuario(), null);

            respuesta.put("resultado", String.valueOf(false));
            respuesta.put("saldoAdminSubsidio", String.valueOf("-1"));
            respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            respuesta.put("error", "Aun se esta haciendo una transaccion");
        }

        logger.info("respuesta " + respuesta);

        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Double consultarSaldoSubsidioSinOperacion(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            TipoMedioDePagoEnum medioDePago) { 

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarSaldoSubsidio(TipoIdentificacionEnum,String,TipoMedioDePagoEnum,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);        
     
        Double respuesta = new Double(0);

        Map<String, Object> saldoFavorNombreAdmin = consultasCore.consultarSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);

        if (saldoFavorNombreAdmin != null) {
            BigDecimal saldoAbonos = (BigDecimal) saldoFavorNombreAdmin.get("valorSaldo");            
            if (saldoAbonos.doubleValue() != 0) {
              return saldoAbonos.doubleValue();
           }           
        }    

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Boolean validarExistenciaIdentificadorTransaccion(String idTransaccionTercerPagador,Long idConvenio) {  

        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarExistenciaIdentificadorTransaccion(String idTransaccionTercerPagador,Long idConvenio)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);     
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return consultasCore.existeIdentificadorTransaccionTerceroPagador(idTransaccionTercerPagador, idConvenio);       
   
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarSaldoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Double consultarSaldoSubsidioSinOperacionAbono(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            TipoMedioDePagoEnum medioDePago) {  

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarSaldoSubsidio(TipoIdentificacionEnum,String,TipoMedioDePagoEnum,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);        
     
        Double respuesta = new Double(0);

        Map<String, Object> saldoFavorNombreAdmin = consultasCore.consultarSaldoSubsidioAbono(tipoIdAdmin, numeroIdAdmin, medioDePago);
        logger.debug("saldoFavorNombreAdmin: " + saldoFavorNombreAdmin);

        if (saldoFavorNombreAdmin != null) {
            BigDecimal saldoAbonos = (BigDecimal) saldoFavorNombreAdmin.get("valorSaldo");     
            logger.debug("saldoAbonos: " + saldoAbonos);
            if (saldoAbonos.doubleValue() != 0) {
              logger.debug("saldoAbonos.doubleValue() != 0");
              return saldoAbonos.doubleValue();
           }           
        }    

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#solicitarRetiroSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, java.math.BigDecimal, java.math.BigDecimal,
     *      java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.asopagos.rest.security.dto.UserDTO,
     *      java.lang.String)
     */
    @Override
    public Map<String, String> solicitarRetiroSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            TipoMedioDePagoEnum medioDePago, BigDecimal saldoActualSubsidio, BigDecimal valorSolicitado, Long fecha,
            String idTransaccionTercerPagador, String departamento, String municipio, String usuario, UserDTO userDTO, String user, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroSubsidio(TipoIdentificacionEnum,"
                + " String, TipoMedioDePagoEnum, BigDecimal, BigDecimal," + " Long, String, String, String, String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaHoraInicio = new Date();
        String url = "servicio-solicitarRetiroSubsidio";

        if(userDTO.getNombreUsuario() == null){
            userDTO.setNombreUsuario(user);
        }
        final String detalleResultado = "detalleResultado";
        final String resultado = "resultado";
        final String error = "error";
        final String idRespuesta = "identificadorRespuesta";
        Map<String, String> respuesta = new HashMap<String, String>();
        Long identificadorRespuesta = null;
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdAdmin);
        parametrosEntrada.put("medioDePago", medioDePago);
        parametrosEntrada.put("saldoActualSubsidio", saldoActualSubsidio);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("fecha", fecha);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("departamento", departamento);
        parametrosEntrada.put("municipio", municipio);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("nombreUserDTO", userDTO.getNombreUsuario());
        parametrosEntrada.put("idPuntoCobro", idPuntoCobro);
        if(userDTO.getEmail()!=null)
            parametrosEntrada.put("emailUserDTO", (user != null)? user: userDTO.getEmail());

        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();
        String parametrosOUT = null;

        //Se valida usuario tercero pagador
        ConvenioTercerPagadorDTO convenioTercerPagadorDTO = consultasCore.consultarConvenioTerceroPagadorPorNombrePagos(usuario);
        if(convenioTercerPagadorDTO == null){
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(detalleResultado, "Transacción no exitosa");
            respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO);
            return respuesta;
        } else if (convenioTercerPagadorDTO.getEstadoConvenio().equals(EstadoConvenioEnum.INACTIVO)){
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(detalleResultado, "Transacción no exitosa");
            respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO_INACTIVO);
            return respuesta;
        }


        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore
                .consultarRegistrosAbonosParaCalcularSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);

        respuesta = validarSolicitudRetiroSubsidio(saldoActualSubsidio, valorSolicitado, idTransaccionTercerPagador, medioDePago, user,
                listaCuentaAdminSubsidio, resultado, error, idRespuesta, parametrosIN, TipoOperacionSubsidioEnum.SOLICITAR_RETIRO, usuario, idPuntoCobro, null);

        //si el objeto es diferente de null es porque ha ocurrido un error y se retorna dicho resultado.
        if (respuesta != null) {
            return respuesta;
        }

        respuesta = new HashMap<>();

        //se registra la operación
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.SOLICITAR_RETIRO, parametrosIN,
            user != null ? user : userDTO.getEmail(), listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio());

        String codDepartamento = consultasCore.consultarDepartamentoPorMunicipio(municipio);

        if(codDepartamento.isEmpty() || !departamento.equals(codDepartamento)){
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "No se encontró un sitio de pago relacionado");
            return respuesta;
        }

        String userModificationUltimate = userEmail(user);
        logger.info("userModificationUltimate" + userModificationUltimate);
        //se realiza el proceso de retiro
        Long idRetiro = registrarTransaccionRetiro(listaCuentaAdminSubsidio, saldoActualSubsidio, valorSolicitado, fecha,
                idTransaccionTercerPagador, municipio, usuario, String.valueOf(identificadorRespuesta), EstadoTransaccionSubsidioEnum.SOLICITADO, idPuntoCobro,user);

        //se agrega el identificador de respuesta al map que se enviara a pantalla


        if (idRetiro == -1) {
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(detalleResultado, "Transacción no exitosa");
            respuesta.put(error, "No se encontro un Sitio de pago asociado");
        }
        else {
            //respuesta existosa
            respuesta.put(resultado, String.valueOf(true));
            respuesta.put(detalleResultado, "Transacción exitosa");
            respuesta.put(idRespuesta, String.valueOf(idRetiro));
            consultasCore.actualizarEstadoTransaccionRetiro(EstadoTransaccionSubsidioEnum.FINALIZADO.name(), idRetiro);
        }

        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarConveniosTerceroPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConvenioTercerPagadorDTO> consultarConveniosTerceroPagador() {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarConveniosTerceroPagador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ConvenioTercerPagadorDTO> listaConvenios = consultasCore.consultarConveniosTercerPagador();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaConvenios;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarConveniosTerceroPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConvenioTercerPagadorDTO consultarConvenioTercero(Long idConvenio) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarConveniosTerceroPagador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConvenioTercerPagadorDTO convenio = consultasCore.consultarConvenioTercerPagador(idConvenio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return convenio;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarConvenioTerceroPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioTercerPagadorDTO) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.actualizarConvenioTerceroPagador(ConvenioTercerPagadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long idConvenio = consultasCore.actualizarConvenioTerceroPagador(convenioTercerPagadorDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idConvenio;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#generarInformesRetirosSubsidioMonetario(com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> generarInformesRetirosSubsidioMonetario(
            SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO) {
                logger.info("**__** ingresa al metodo suconsumo"+suConsumoDTO);
        String firmaServicio = "$*PagosSubsidioMonetarioBusiness.generarInformesRetirosSubsidioMonetario(SubsidioPerdidaDerechoInformesConsumoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("**__** suconsumo"+suConsumoDTO);
        List<CuentaAdministradorSubsidioDTO> listaCuentas = consultasCore.consultarInformesRetiros(suConsumoDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaCuentas;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#generarListadoSubsidiosAnularPorPerdidaDerecho(com.asopagos.subsidiomonetario.pagos.dto.SubsidioPerdidaDerechoInformesConsumoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> generarListadoSubsidiosAnularPorPerdidaDerecho(
            SubsidioPerdidaDerechoInformesConsumoDTO suConsumoDTO) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.generarListadoSubsidiosAnularPorPerdidaDerecho(SubsidioPerdidaDerechoInformesConsumoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosMonetarios = consultasCore
                .generarListadoSubsidiosAnularPorPerdidaDeDerecho(suConsumoDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return subsidiosMonetarios;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#verIncosistenciasArchivoRetiros(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<IncosistenciaConciliacionConvenioDTO> verIncosistenciasArchivoRetiros(Long idArchivoRetiroTerceroPagador) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.verIncosistenciasArchivoRetiros(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<IncosistenciaConciliacionConvenioDTO> listaInconsistencias = consultasCore
                .consultarInconsistenciasArchivoRetiroTerceroPagador(idArchivoRetiroTerceroPagador);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaInconsistencias;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarEstadoRetirosConciliadosTerceroPagador(java.lang.Long)
     */
    @Override
    public void actualizarEstadoRetirosConciliadosTerceroPagador(Long idArchivoRetiroTerceroPagador) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.actualizarEstadoRetirosConciliadosTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        consultasCore.actualizarEstadoRetirosConciliadosTerceroPagador(idArchivoRetiroTerceroPagador);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarSubsidiosMonetariosCambioMedioDePago(com.asopagos.subsidiomonetario.pagos.dto.SubsidioMonetarioConsultaCambioPagosDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarSubsidiosMonetariosCambioMedioDePago(
            SubsidioMonetarioConsultaCambioPagosDTO cambioPagosDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarSubsidiosMonetariosCambioMedioDePago(SubsidioMonetarioConsultaCambioPagosDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> listaSubsidios = consultasCore.consultarSubsidiosCambioMedioDePago(cambioPagosDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return listaSubsidios;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarTransaccionesAbonoCobrados(com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarTransaccionesAbonoCobrados(
            DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarTransaccionesAbonoCobrados(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> lstCuentaAdministradorSubsidioDTO = consultasCore
                .consultarTransaccionesAbonoCobrados(transaccionDetalleSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstCuentaAdministradorSubsidioDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarAnulacionSubsidioCobrado(com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudAnulacionSubsidioCobradoDTO registrarAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.registrarAnulacionSubsidioCobrado(SolicitudAnulacionSubsidioCobradoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (solicitudAnulacionSubsidioCobrado.getAbonosAnulacionSubsidioCobradoDTO() != null
                && !solicitudAnulacionSubsidioCobrado.getAbonosAnulacionSubsidioCobradoDTO().isEmpty()) {
            consultasCore.registrarAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobrado, userDTO.getNombreUsuario());
        }
        else {
            solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
            solicitudAnulacionSubsidioCobrado.setCausaError(PagosSubsidioMonetarioConstants.ERROR_MESSAGE_SOLICITUD_ANULACION_SIN_ABONOS);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobrado;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#mostrarNombreConveniosTerceroPagador()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ConvenioTercerPagadorDTO> mostrarNombreConveniosTerceroPagador() {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.mostrarNombreConveniosTerceroPagador()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ConvenioTercerPagadorDTO> listaConvenios = consultasCore.mostrarNombreConveniosTerceroPagador();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaConvenios;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarMediosDePagosRelacionadosAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDePagosRelacionadosAdminSubsidio(Long idAdminSubsidio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarMediosDePagosRelacionadosAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TipoMedioDePagoEnum> medioDePago = consultasCore.consultarMediosDePagoRelacionadosAdminSubsidio(idAdminSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return medioDePago;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarMediosDePagosRelacionadosAdminSubsidio(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDePagosInactivosRelacionadosAdminSubsidio(Long idAdminSubsidio,List<Long> cuentas) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarMediosDePagosRelacionadosAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio); 
 
        List<TipoMedioDePagoEnum> medioDePago = consultasCore.consultarMediosDePagosInactivosRelacionadosAdminSubsidio(idAdminSubsidio, cuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return medioDePago;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarMedioDePagoAsignarAdminSubsidio(com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<MedioDePagoModeloDTO> consultarMedioDePagoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio,List<Long>lstMediosDePago) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarMedioDePagoAsignar(TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<MedioDePagoModeloDTO> listaMediosDePago = consultasCore.consultarMedioDePagoAsignarAdminSubsidio(medioDePago, idAdminSubsidio,lstMediosDePago);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaMediosDePago;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarMedioDePagoAsignarAdminSubsidio(com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<MedioDePagoModeloDTO> consultarMedioDePagoInactivoAsignarAdminSubsidio(TipoMedioDePagoEnum medioDePago, Long idAdminSubsidio,List<Long>lstIdsCuentas) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarMedioDePagoInactivoAsignar(TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<MedioDePagoModeloDTO> listaMediosDePago = consultasCore.consultarMedioDePagoAsignarAdminSubsidio(medioDePago, idAdminSubsidio,lstIdsCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaMediosDePago;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(TipoIdentificacionEnum tipoIdAdmin,
            String numeroIdAdmin, TipoMedioDePagoEnum medioDePago) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(TipoIdentificacionEnum,String,TipoMedioDePagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //se obtienen las cuentas
        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore
                .consultarRegistrosAbonosParaCalcularSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = null;

        if (listaCuentaAdminSubsidio != null) {
            listaDetalles = consultasCore.consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(listaCuentaAdminSubsidio);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesSubsidioAsignadosSaldoAdminSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
	public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidioAsignadosPorCuentaAdmin(Long idCuentaAdminSub) {
		String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDetallesSubsidioAsignadosPorCuentaAdmin(Long idCuentaAdminSub )";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

		if (idCuentaAdminSub != null) {
			
			CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultasCore
					.consultarCuentaAdministradorSubsidio(idCuentaAdminSub);

			if (cuentaAdministradorSubsidioDTO != null) {
				if (TipoTransaccionSubsidioMonetarioEnum.RETIRO
						.equals(cuentaAdministradorSubsidioDTO.getTipoTransaccion())
						|| TipoTransaccionSubsidioMonetarioEnum.ANULACION
								.equals(cuentaAdministradorSubsidioDTO.getTipoTransaccion())) {

					List<DetalleSubsidioAsignadoDTO> listaDetallesSubsidio = consultasCore
							.consultaDetallesRetirosAnulacionCuenta(idCuentaAdminSub,
									cuentaAdministradorSubsidioDTO.getTipoTransaccion());
					return listaDetallesSubsidio;
				}

				// se obtienen las cuentas
				List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = new ArrayList<CuentaAdministradorSubsidioDTO>();
				listaCuentaAdminSubsidio.add(cuentaAdministradorSubsidioDTO);

				List<DetalleSubsidioAsignadoDTO> listaDetalles = null;

				listaDetalles = consultasCore
							.consultarDetallesPorCuenta(idCuentaAdminSub);
				
				return listaDetalles;
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return null;

	}
    
    
    
    

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#confirmarValorEntregadoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.Long, java.lang.String, java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO, java.lang.String)
     */
    @Override
    public Map<String, String> confirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
    BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario, UserDTO userDTO, String user, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.confirmarValorEntregadoSubsidio(TipoIdentificacionEnum, String,"
                + " BigDecimal, BigDecimal, Long, String, String,UserDTO,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //userDTO.setEmail(user);
        if(userDTO.getNombreUsuario() == null){
            userDTO.setNombreUsuario(user);
        }
        
        Map<String, String> respuesta = new HashMap<>();
        
        respuesta = procesoConfirmarValorEntregadoSubsidio(tipoIdAdmin,numeroIdAdmin,valorSolicitado,valorEntregado,fecha,idTransaccionTercerPagador,
                usuario,user, userDTO,idPuntoCobro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#confirmarValorEntregadoSubsidioCasoB(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.Long, java.lang.String, java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO, java.lang.String)
     */
    @Override
    public Map<String, String> confirmarValorEntregadoSubsidioCasoB(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario,
            UserDTO userDTO, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.confirmarValorEntregadoSubsidioCasoB(TipoIdentificacionEnum, String,"
                + " BigDecimal, BigDecimal, Long, String, String,UserDTO, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> respuesta = new HashMap<>();

        respuesta = procesoConfirmarValorEntregadoSubsidio(tipoIdAdmin,numeroIdAdmin,valorSolicitado,valorEntregado,fecha,idTransaccionTercerPagador,
        usuario,userDTO.getNombreUsuario() != null ? userDTO.getNombreUsuario() : usuario, userDTO,idPuntoCobro);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * Método encargado de llevar acabo el proceso de confirmar valor entregado,  llamado por los dos casos A y B HU_31_203
     * @param tipoIdAdmin
     * @param numeroIdAdmin
     * @param valorSolicitado
     * @param valorEntregado
     * @param fecha
     * @param idTransaccionTercerPagador
     * @param usuario
     * @param userDTO
     * @param idPuntoCobro
     * @return
     */
    private Map<String, String> procesoConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
                                                                       BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario, String user,
                                                                        UserDTO userDTO, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.confirmarValorEntregadoSubsidio(TipoIdentificacionEnum, String,"
                + " BigDecimal, BigDecimal, Long, String, String,UserDTO,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("fecha actual: " + new Date()); 
        Date fechaHoraInicio = new Date();
        String url = "metodo-procesoConfirmarValorEntregadoSubsidio";
        Map<String, String> respuesta = new HashMap<String, String>();

        final String resultado = "resultado";
        final String detalleTransaccion = "detalleTransaccion";
        final String error = "error";
        final String idRespuesta = "identificadorRespuesta";

        logger.info("**Incicio** -> PagosSubsidioMonetarioService.PagosSubsidioMonetarioBusiness.procesoConfirmarValorEntregadoSubsidio");
        logger.info("Usuario: " + usuario);

            //Se valida usuario tercero pagador
            ConvenioTercerPagadorDTO convenioTercerPagadorDTO = consultasCore.consultarConvenioTerceroPagadorPorNombrePagos(usuario);
            if(convenioTercerPagadorDTO == null){
                
                logger.info("Entra if(convenioTercerPagadorDTO == null){");

                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO);
                return respuesta;
            } else if (convenioTercerPagadorDTO.getEstadoConvenio().equals(EstadoConvenioEnum.INACTIVO)){
                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO_INACTIVO);
                return respuesta;
            }

            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidio = consultasCore.buscarRetiroPorIdTransaccionTerceroPagadorRetiro(idTransaccionTercerPagador, usuario, idPuntoCobro);
            if (cuentaAdministradorSubsidio == null){
                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_IDENTIFICADOR_TERCERO_PAGADOR);
                return respuesta;
            } else if (cuentaAdministradorSubsidio.getValorOriginalTransaccion().intValue() != valorSolicitado.intValue()){
                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_VALOR_ENTREGADO_VALOR_SOLICITADO);
                return respuesta;
            }





        Long identificadorRespuesta = null;

        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();

        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdAdmin);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("valorEntregado", valorEntregado);
        parametrosEntrada.put("fecha", fecha);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("nombreUserDTO", userDTO.getNombreUsuario());
        parametrosEntrada.put("idPuntoCobro", idPuntoCobro);
        parametrosEntrada.put("emailUserDTO", userDTO.getEmail() != null ? userDTO.getEmail() : user);

        salida.append(gson.toJson(parametrosEntrada));

        String parametrosIN = salida.toString();

        String parametrosOUT = null;

        //Se valida que el valor solicitado sea como minimo cero (0)
        if (valorSolicitado.compareTo(BigDecimal.ZERO) < 0) {
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONFIRMAR_VALOR_ENTREGADO,
                    parametrosIN, user != null ? user : userDTO.getEmail(), null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "El valor solicitado es: " + valorSolicitado + ", el valor minimo permitido es cero (0).");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            respuesta.put(detalleTransaccion, "Transacción no exitosa");
            return respuesta;
        }

        //Se valida que el valor entregado sea como minimo cero (0)
        if (valorEntregado.compareTo(BigDecimal.ZERO) < 0) {
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONFIRMAR_VALOR_ENTREGADO,
                    parametrosIN, user != null ? user : userDTO.getEmail(), null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "El valor entregado es: " + valorEntregado + ", el valor minimo permitido es cero (0).");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            respuesta.put(detalleTransaccion, "Transacción no exitosa");
            return respuesta;
        }

        //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidio = consultasCore
                .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(tipoIdAdmin, numeroIdAdmin, idTransaccionTercerPagador, usuario, idPuntoCobro);

        //Se valida que si hayan abonos con estado 'SOLICITADO' de una solicitud de retiro previo.
        if (listaCuentasAdminSubsidio == null) {
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONFIRMAR_VALOR_ENTREGADO,
                    parametrosIN, (userDTO.getEmail() != null)? userDTO.getEmail(): userDTO.getNombreUsuario(), null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "No se puede realizar la confirmación del valor entregado. El administrador no ha realizado un retiro previo ó el identificador de transacción del tercero pagador es incorrecto");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            respuesta.put(detalleTransaccion, "Transacción no exitosa");
            return respuesta;
        }

        if (valorEntregado.compareTo(valorSolicitado) > 0) {
            //si el valor entregado es mayor al valor solicitado

            //SE DEBE REGISTRAR UNA TRANSACCIÓN FALLIDA DE TODOS LOS ABONOS QUE QUEDARON EN ESTADO SOLICITADO DE LA TRANSACCIÓN DE RETIRO EFECTUADA
            //DICHA TRANSACCIÓN FALLIDA, TENDRA UNA RELACIÓN CON EL REGISTRO DE OPERACIONES, ESTA RELACIÓN SE REALIZARA EN UNA TABLA NUEVA (TransaccionFallidaRegistroOperacionesSubsidio)

            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONFIRMAR_VALOR_ENTREGADO,
                    parametrosIN, user != null ? user : userDTO.getEmail(), listaCuentasAdminSubsidio.get(0).getIdAdministradorSubsidio());
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "El valor entregado es mayor al solicitado");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                //se registra la transacción fallida de cada abono que se solicito para el retiro anterior.
                TransaccionFallidaDTO transaccionFallidaAbono = new TransaccionFallidaDTO();
                transaccionFallidaAbono.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
                transaccionFallidaAbono.setIdCuentaAdmonSubsidio(cuenta.getIdCuentaAdministradorSubsidio());

                //se crea la transacción fallida
                Long idTransaccionFallida = gestionarTransaccionesFallidas(transaccionFallidaAbono);

                consultasCore.registrarTransaccionesFallidasRegistroOperacionesSubsidio(identificadorRespuesta, idTransaccionFallida);
            }

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            respuesta.put(detalleTransaccion, "Transacción no exitosa");

            return respuesta;

        }
        else if (valorSolicitado.compareTo(valorEntregado) == 0) {
            //si el valor solicitado es igual al valor entregado,se realizo un retiro completo,
            //se ejecuta  HU-31-218 3.1.3.1  Caso: Retiro confirmado completo

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                //3.1.3.1 Confirmación retiro completo
                cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                cuenta.setFechaHoraUltimaModificacion(new Date());
                cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                //traer detalles y actualizarlos
                List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());
                for (int e = 0; e < listaDetallesAbono.size(); e++) {
                    listaDetallesAbono.get(e).setFechaHoraUltimaModificacion(new Date());
                    listaDetallesAbono.get(e).setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                    consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(e));
                }
            }

        }
        else if (valorEntregado.compareTo(valorSolicitado) < 0) {
            //si el valor entregado es menor al valor solicitado, se realizo un retiro incompleto, se ejecuta HU-31-218 3.1.3.2

            //se consulta el retiro que esta siendo confirmado
            CuentaAdministradorSubsidioDTO retiroAsociadoIdTransaccionTerceroPagador = consultasCore
                    .buscarRetiroPorIdTransaccionTerceroPagadorRetiro(idTransaccionTercerPagador, usuario, idPuntoCobro);

            //3.1.3.2 paso 1: crear un registro de transacción
            CuentaAdministradorSubsidioDTO cuentaAjusteTransaccionRetiro = new CuentaAdministradorSubsidioDTO();

            //valor solicitado - valor entregado
            BigDecimal valorAjuste = valorSolicitado.subtract(valorEntregado);

            cuentaAjusteTransaccionRetiro.setUsuarioCreacionRegistro(user != null ? user : userDTO.getEmail());
            cuentaAjusteTransaccionRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.AJUSTE_TRANSACCION_RETIRO_INCOMPLETA);
            cuentaAjusteTransaccionRetiro.setMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
            cuentaAjusteTransaccionRetiro.setIdMedioDePago(retiroAsociadoIdTransaccionTerceroPagador.getIdMedioDePago());
            cuentaAjusteTransaccionRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
            cuentaAjusteTransaccionRetiro.setValorOriginalTransaccion(valorAjuste);
            cuentaAjusteTransaccionRetiro.setIdAdministradorSubsidio(listaCuentasAdminSubsidio.get(0).getIdAdministradorSubsidio());
            cuentaAjusteTransaccionRetiro.setIdEmpleador(listaCuentasAdminSubsidio.get(0).getIdEmpleador());
            cuentaAjusteTransaccionRetiro.setIdAfiliadoPrincipal(listaCuentasAdminSubsidio.get(0).getIdAfiliadoPrincipal());
            cuentaAjusteTransaccionRetiro.setIdBeneficiarioDetalle(listaCuentasAdminSubsidio.get(0).getIdBeneficiarioDetalle());
            cuentaAjusteTransaccionRetiro.setIdGrupoFamiliar(listaCuentasAdminSubsidio.get(0).getIdGrupoFamiliar());
            cuentaAjusteTransaccionRetiro.setSolicitudLiquidacionSubsidio(listaCuentasAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
            cuentaAjusteTransaccionRetiro.setFechaHoraTransaccion(new Date());
            cuentaAjusteTransaccionRetiro.setUsuarioTransaccionLiquidacion(user != null ? user : userDTO.getEmail()); //Ajuste por GLPI 71488
            cuentaAjusteTransaccionRetiro.setValorRealTransaccion(BigDecimal.ZERO); //Ajuste por GLPI 71488
            cuentaAjusteTransaccionRetiro.setFechaHoraUltimaModificacion(new Date());
            cuentaAjusteTransaccionRetiro.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
            cuentaAjusteTransaccionRetiro.setNombreTerceroPagador(retiroAsociadoIdTransaccionTerceroPagador.getNombreTerceroPagador());
            cuentaAjusteTransaccionRetiro.setIdTransaccionOriginal(retiroAsociadoIdTransaccionTerceroPagador.getIdCuentaAdministradorSubsidio());

            //se crea la cuenta de Retiro - ajuste transaccion retiro incompleto
            Long idCuentaAjusteRetiro = consultasCore.crearCuentaAdministradorSubsidio(cuentaAjusteTransaccionRetiro);

            //variable C = valor del retiro que está pendiente por aplicar(inicialmente es igual a la variable a(valor solicitado pero en este caso sera valor entregado) pero con signo negativo)
            double valorC = valorEntregado.negate().doubleValue();
            List<CuentaAdministradorSubsidioDTO> abonosSolicitados = listaCuentasAdminSubsidio;

            boolean isCuentasSolicitadas = false;
            int i = 0;

            //Inicia ajuste GLPI 71488
            if (valorEntregado.compareTo(BigDecimal.ZERO) == 0) {
                logger.info("Entra a caso A - confimacion valor entregado menor al solicitado - Valor entregado igual a cero (0)");
                for (; i < abonosSolicitados.size(); i++) {
                    CuentaAdministradorSubsidioDTO cuenta = abonosSolicitados.get(i);
                    if(TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                        cuenta.setIdCuentaAdminSubsidioRelacionado(null);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                        logger.info("casId abono iterado y actualizado: " + cuenta.getIdCuentaAdministradorSubsidio());
                        
                        List<DetalleSubsidioAsignadoDTO> detallesAbonoList = consultasCore
                            .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());
                        if(detallesAbonoList != null && !detallesAbonoList.isEmpty()) {
                            for (int j=0; j<detallesAbonoList.size(); j++) {
                                DetalleSubsidioAsignadoDTO detalle = detallesAbonoList.get(j);
                                detalle.setFechaTransaccionRetiro(null);
                                detalle.setUsuarioTransaccionRetiro(null);
                                detalle.setFechaHoraUltimaModificacion(new Date());
                                detalle.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                                consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                                logger.info("dsaId detalle iterado y actualizado: " + detalle.getIdDetalleSubsidioAsignado());
                            }
                        }
                    }   
                }
                /** 
                Se modifica el valor real de la transacci;ón de retiro por el valor entregado enviado al servicio
                Este caso solo aplica para la confirmaci;ón de retiro en el escenario donde se confirma un valor entregado menor al valor solicitado
                Ajuste realizado por el GLPI 71488
                */
                if (retiroAsociadoIdTransaccionTerceroPagador != null) {
                    CuentaAdministradorSubsidioDTO retiro = retiroAsociadoIdTransaccionTerceroPagador;
                    retiro.setValorRealTransaccion(valorEntregado);
                    retiro.setIdCuentaAdminSubsidioRelacionado(idCuentaAjusteRetiro);
                    retiro.setFechaHoraUltimaModificacion(new Date());
                    retiro.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                    //pte agregar fecha y usuario ultima modificacion
                    consultasCore.actualizarCuentaAdministradorSubsidio(retiro);
                    logger.info("casId retiro actualizado: " + retiro.getIdCuentaAdministradorSubsidio());
                }
            } else if (valorEntregado.compareTo(BigDecimal.ZERO) > 0 && valorEntregado.compareTo(valorSolicitado) < 0) {
                logger.info("Entra a caso B - confimacion valor entregado menor al solicitado - Valor entregado mayor a cero (0), menor al solicitado");
                for (; i < abonosSolicitados.size(); i++) {

                    CuentaAdministradorSubsidioDTO cuenta = abonosSolicitados.get(i);
    
                    if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {
    
                        //C = C+B
                        valorC += cuenta.getValorRealTransaccion().doubleValue();
    
                        //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                        //se sigue asociando el id del retiro con la cuenta a la que se asocia
                        cuenta.setIdCuentaAdminSubsidioRelacionado(
                                retiroAsociadoIdTransaccionTerceroPagador.getIdCuentaAdministradorSubsidio());
    
                        //Casos:  C<0
                        if (valorC < 0) {
                            //3.1.3.1 Confirmación retiro completo
                            cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                            cuenta.setFechaHoraUltimaModificacion(new Date());
                            cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                            
                            List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());
                            for (int e = 0; e < listaDetallesAbono.size(); e++) {
                                listaDetallesAbono.get(e).setFechaHoraUltimaModificacion(new Date());
                                listaDetallesAbono.get(e).setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(e));
                            }
    
                        } else if (valorC == 0) { //Caso C=0
                            //3.1.3.1 Confirmación retiro completo
                            cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                            cuenta.setFechaHoraUltimaModificacion(new Date());
                            cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                            List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());
                             for (int e = 0; e < listaDetallesAbono.size(); e++) {
                                listaDetallesAbono.get(e).setFechaHoraUltimaModificacion(new Date());
                                listaDetallesAbono.get(e).setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(e));
                            }
    
                            //si hay mas cuentas que no se estan tendiendo en cuenta para el retiro.
                            if (i < abonosSolicitados.size() - 1) {
                                isCuentasSolicitadas = true;//
                            }
                            break;
                        } else {
    
                            double valorReal = cuenta.getValorRealTransaccion().doubleValue() - valorC;
    
                            cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                            //El valor real de la transacción sería lo que sobro
                            cuenta.setValorRealTransaccion(BigDecimal.valueOf(Math.abs(valorReal))); //antes estaba solo valor real
                            cuenta.setFechaHoraUltimaModificacion(new Date());
                            cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
    
                            //Se crea el registro “Nuevo abono originado por fraccionamiento”
                            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();
                            System.out.println("*****Abono por fraccionamiento*****");
                            cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
                            cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(user != null ? user : userDTO.getEmail());
                            cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                            cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
                            cuentaAdministradorSubsidioDTO.setMedioDePago(cuenta.getMedioDePago());
                            cuentaAdministradorSubsidioDTO.setIdMedioDePago(cuenta.getIdMedioDePago());
                            cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO_PARCIAL);
                            cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(BigDecimal.valueOf(valorC));
                            cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(abonosSolicitados.get(i).getIdAdministradorSubsidio());
                            cuentaAdministradorSubsidioDTO.setIdEmpleador(abonosSolicitados.get(i).getIdEmpleador());
                            cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(abonosSolicitados.get(i).getIdAfiliadoPrincipal());
                            cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(abonosSolicitados.get(i).getIdBeneficiarioDetalle());
                            cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(abonosSolicitados.get(i).getIdGrupoFamiliar());
                            cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(abonosSolicitados.get(i).getSolicitudLiquidacionSubsidio());
                            cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(abonosSolicitados.get(i).getFechaHoraTransaccion());
                            cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(abonosSolicitados.get(i).getUsuarioTransaccionLiquidacion());
                            cuentaAdministradorSubsidioDTO.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
                            cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                            cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                            cuentaAdministradorSubsidioDTO.setIdSitioDePago(cuenta.getIdSitioDePago());
                            cuentaAdministradorSubsidioDTO.setIdTransaccionOriginal(cuenta.getIdCuentaAdministradorSubsidio());
    
                            //se crea el abono originado por fraccionamiento
                            Long idAbonoOriginadoFraccionamiento = consultasCore
                                    .crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);
    
                            //Paso 4: se ajustan los detalles del "abono fraccionado"
                            fraccionarRegistrosDetallesAbonoRetiroIncompleto(cuenta.getIdCuentaAdministradorSubsidio(),
                                    BigDecimal.valueOf(valorReal), user != null ? user : userDTO.getEmail(), idAbonoOriginadoFraccionamiento);
    
                            //se actualiza el nuevo abono como enviado
                            cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                            cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);
    
                            //se actualiza el nuevo abono como aplicado
                            cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);
    
                            //si hay mas cuentas que no se estan tendiendo en cuenta para el retiro.
                            if (i < abonosSolicitados.size() - 1) {
                                isCuentasSolicitadas = true;//
                            }
                            break;
                        }
                    }
                }
                /** 
                Se modifica el valor real de la transacci;ón de retiro por el valor entregado enviado al servicio
                Este caso solo aplica para la confirmaci;ón de retiro en el escenario donde se confirma un valor entregado menor al valor solicitado
                Ajuste realizado por el GLPI 71488
                */
                if (retiroAsociadoIdTransaccionTerceroPagador != null) {
                    CuentaAdministradorSubsidioDTO retiro = retiroAsociadoIdTransaccionTerceroPagador;
                    retiro.setValorRealTransaccion(valorEntregado.negate());
                    retiro.setIdCuentaAdminSubsidioRelacionado(idCuentaAjusteRetiro);
                    retiro.setFechaHoraUltimaModificacion(new Date());
                    retiro.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                    consultasCore.actualizarCuentaAdministradorSubsidio(retiro);
                    logger.info("casId retiro actualizado: " + retiro.getIdCuentaAdministradorSubsidio());
                }
            }
            
            //si hay otras cuentas que se tienen encuenta cuando es un retiro incompleto que
            // que deja un abono en estado solicitado para el retiro, se realiza el respectivo ajuste
            if (isCuentasSolicitadas) {
                logger.info("Entra a actualizar abonos que no se vieron afectados por la transaccion de retiro: " + retiroAsociadoIdTransaccionTerceroPagador.getIdCuentaAdministradorSubsidio());
                for (int j = i + 1; j < abonosSolicitados.size(); j++) {

                    CuentaAdministradorSubsidioDTO cuenta = abonosSolicitados.get(j);

                    cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                    cuenta.setFechaHoraUltimaModificacion(new Date());
                    cuenta.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                    cuenta.setIdCuentaAdminSubsidioRelacionado(null);
                    consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                    logger.info("casId abono iterado y actualizado: " + cuenta.getIdCuentaAdministradorSubsidio());

                    //se modifican los detalles relacionados a la cuenta que se ajusta
                    List<DetalleSubsidioAsignadoDTO> listaDetalles = consultarDetallesSubsidiosAsignadosAsociadosAbono(
                            cuenta.getIdCuentaAdministradorSubsidio());
                    for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
                        detalle.setFechaTransaccionRetiro(null);
                        detalle.setUsuarioTransaccionRetiro(null);
                        detalle.setFechaHoraUltimaModificacion(new Date());
                        detalle.setUsuarioUltimaModificacion(user != null ? user : userDTO.getEmail());
                        consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                        logger.info("dsa detalle iterado y actualizado: " + detalle.getIdDetalleSubsidioAsignado());
                    }

                }
            }
        }

        //se registra la operación
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.CONFIRMAR_VALOR_ENTREGADO,
                parametrosIN, user != null ? user : userDTO.getEmail(), listaCuentasAdminSubsidio.get(0).getIdAdministradorSubsidio());

        respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
        respuesta.put(resultado, String.valueOf(true));

        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        respuesta.put(detalleTransaccion, "Transacción exitosa");
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#solicitarRetiroConfirmarValorEntregadoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, java.math.BigDecimal, java.math.BigDecimal,
     *      java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.asopagos.rest.security.dto.UserDTO,
     *      java.lang.String)
     */
    @Override
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            TipoMedioDePagoEnum medioDePago, BigDecimal saldoActualSubsidio, BigDecimal valorSolicitado, Long fecha,
            String idTransaccionTercerPagador, String departamento, String municipio, String usuario, Boolean isVentanilla, UserDTO userDTO, 
            String user, String idPuntoCobro, String check) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum,"
                + " String, TipoMedioDePagoEnum, BigDecimal, BigDecimal," + " Long, String, String, String, String, UserDTO, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("Zona horaria actual: " + TimeZone.getDefault());
        logger.info("Zona horaria actual: " + new Date());
        Date fechaHoraInicio = new Date();
        String url = "servicio-solicitarRetiro/confirmarValorEntregadoSubsidio";

        logger.info("NombreUsuarioDTO: " + userDTO.getNombreUsuario());
        logger.info("EmailDTO: " + userDTO.getEmail());
        logger.info("User: " + user);
        logger.info("Check: " + check);

        if(userDTO.getNombreUsuario() == null){
            userDTO.setNombreUsuario(user);
        }
        if(user == null){
            user = userDTO.getNombreUsuario();
        }
        final String detalleTransaccion = "detalleTransaccion";
        final String resultado = "resultado";
        final String error = "error";
        final String idRespuesta = "identificadorRespuesta";
        Map<String, String> respuesta = new HashMap<String, String>();
        Long identificadorRespuesta = null;
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();
        String codigoMunicpioCCF = null;
        Long idSitioDePago = null;

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdAdmin);
        parametrosEntrada.put("medioDePago", medioDePago);
        parametrosEntrada.put("saldoActualSubsidio", saldoActualSubsidio);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("fecha", fecha);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("departamento", departamento);
        parametrosEntrada.put("municipio", municipio);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("nombreUserDTO", userDTO.getNombreUsuario());
        if (userDTO.getEmail() != null){
            parametrosEntrada.put("emailUserDTO", userDTO.getEmail());
        }else{
            parametrosEntrada.put("emailUserDTO", user);
        }
        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();
        String parametrosOUT = null;

        logger.info("Paramentros entrada - solicitarRetiroConfirmarValorEntregadoSubsidio: " + parametrosIN);

        if(check != null && check.equals("true")){
            //Se valida usuario tercero pagador
            ConvenioTercerPagadorDTO convenioTercerPagadorDTO = consultasCore.consultarConvenioTerceroPagadorPorNombrePagos(usuario);
            if(convenioTercerPagadorDTO == null){
                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO);
                return respuesta;
            } else if (convenioTercerPagadorDTO.getEstadoConvenio().equals(EstadoConvenioEnum.INACTIVO)){
                respuesta.put(resultado, String.valueOf(false));
                respuesta.put(detalleTransaccion, "Transacción no exitosa");
                respuesta.put(error, MensajesGeneralConstants.ERROR_USUARIO_CONVENIO_INACTIVO);
                return respuesta;
            }
        }


        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore
                .consultarRegistrosAbonosParaCalcularSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);
        
        //CuentaAdministradorSubsidioDTO ultimaCuenta = listaCuentaAdminSubsidio.get(listaCuentaAdminSubsidio.size()-1);
        
        respuesta = validarSolicitudRetiroSubsidio(saldoActualSubsidio, valorSolicitado, idTransaccionTercerPagador, medioDePago, user,
                listaCuentaAdminSubsidio, resultado, error, idRespuesta, parametrosIN,
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO,
                usuario,
                idPuntoCobro, null);

        //si el objeto es diferente de null es porque ha ocurrido un error y se retorna dicho resultado.
        if (respuesta != null) {
            logger.info("**LOGGER Respuesta: " + respuesta.toString());
            return respuesta;
        }

        respuesta = new HashMap<>();

        //se registra la operación
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO, parametrosIN, user != null ? user : userDTO.getEmail(),
                listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio());

        if (municipio == null || municipio.isEmpty()) { //se agrega municipio.isEmpty()
            //Se establece por defecto el municpío de la CCF por parametro del sistema
            codigoMunicpioCCF = (String) CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID);
            municipio = consultasCore.buscarMunicipioPorCodigo(codigoMunicpioCCF);
        } else {
            idSitioDePago = consultasCore.consultarSitioDePago(municipio);
        }        

        String usuarioRetiro = (usuario != null && !usuario.equals("")) ? usuario : userDTO.getNombreUsuario();
 
        //se realiza el proceso de retiro 
//        Long idRetiro = registrarTransaccionRetiro(listaCuentaAdminSubsidio, saldoActualSubsidio, valorSolicitado, fecha,
//                idTransaccionTercerPagador, ultimaCuenta.getIdSitioDePago(), usuarioRetiro, isVentanilla ? null : String.valueOf(identificadorRespuesta), null, idPuntoCobro);
        String codDepartamento = consultasCore.consultarDepartamentoPorMunicipio(municipio);
        logger.info("**LOGGER codDepartamento: " +codDepartamento);
        if(codDepartamento != null && departamento!=null){
                if(!departamento.equals(codDepartamento)){
                    //respuesta no existosa, no se encontro id sitio de pago asociado
                    respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
                    respuesta.put(resultado, String.valueOf(false));
                    respuesta.put(error, "No se encontró un sitio de pago relacionado");
                    return respuesta;
                }
        }

        //Si el retiro es por ventanilla, el valor por defecto es cero (0) para la variable idPuntoCobro. Se cambia el valor para el registro de la transacción
        if(isVentanilla && idPuntoCobro.equals("0")){
            idPuntoCobro = null;
        }
        logger.info("Parametros de entrada antes de persistir: " + parametrosIN);
        Long idRetiro = registrarTransaccionRetiro(listaCuentaAdminSubsidio, saldoActualSubsidio, valorSolicitado, fecha,
                idTransaccionTercerPagador, municipio, usuarioRetiro, isVentanilla ? null : String.valueOf(identificadorRespuesta), EstadoTransaccionSubsidioEnum.SOLICITADO, idPuntoCobro,user);
                logger.info("idRetiro después de persistir: " + idRetiro);
        if (idRetiro == -1) {
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(detalleTransaccion, "Transacción no exitosa");
            respuesta.put(error, "No se encontro un Sitio de pago asociado");
            return respuesta;
        }
        consultasCore.actualizarEstadoTransaccionRetiro(EstadoTransaccionSubsidioEnum.FINALIZADO.name(), idRetiro);

        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidio = null;
        //PASOS PARA LA CONFIRMACIÓN DEL RETIRO
        if (idPuntoCobro != null && !idPuntoCobro.equals("0")) {
        	 //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
            listaCuentasAdminSubsidio = consultasCore
                    .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(tipoIdAdmin, numeroIdAdmin, idTransaccionTercerPagador, usuario, idPuntoCobro);
        } else {
        	 //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
            listaCuentasAdminSubsidio = consultasCore
                    .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(tipoIdAdmin, numeroIdAdmin, idTransaccionTercerPagador);
        }
       
        //Se valida que si hayan abonos con estado 'SOLICITADO' de una solicitud de retiro previo.
        if (listaCuentasAdminSubsidio == null) {
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                    TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_PERSONA_AUTORIZADA_CONFIRMAR_VALOR_ENTREGADO, parametrosIN,
                    user != null ? user : userDTO.getEmail(), null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "No se puede realizar la confirmación del valor entregado. El administrador no ha realizado un retiro previo ó el identificador de transacción del tercero pagador es incorrecto");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            respuesta.put(detalleTransaccion, "Transacción no exitosa");
            return respuesta;
        }

        /*
        BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(tipoIdAdmin,
                numeroIdAdmin, idTransaccionTercerPagador);
        */
        BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(idRetiro);

        //si el valor solicitado es igual al valor entregado,se realizo un retiro completo,
        //se ejecuta  HU-31-218 3.1.3.1  Caso: Retiro confirmado completo
        //no se da el caso de que haya un retiro incompleto
        if (valorSolicitado.compareTo(valorEntregadoRetiro) == 0) {

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                //3.1.3.1 Confirmación retiro completo
                cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                cuenta.setFechaHoraUltimaModificacion(new Date());
                cuenta.setUsuarioUltimaModificacion(user != null ? user : usuarioRetiro);

                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
            }
        }

        respuesta.put(idRespuesta,String.valueOf(idRetiro));//String.valueOf(identificadorRespuesta));
        respuesta.put(resultado, String.valueOf(true));
        //se anexa el identificador del tercero pagador.
        respuesta.put("idTransaccionTerceroPagador", idTransaccionTercerPagador);

        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        respuesta.put(detalleTransaccion, "Transacción exitosa");
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#solicitarRetiroPersonaAutorizadaConfirmarValorEntregado(com.asopagos.subsidiomonetario.pagos.dto.SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> solicitarRetiroPersonaAutorizadaConfirmarValorEntregado(
            SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO confirmacionEntregaPersonaAutorizadaDTO, UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroPersonaAutorizadaConfirmarValorEntregado(SolicitudRetiroConfirmacionEntregaPersonaAutorizadaDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("userDTO.getCiudadSedeCajaCompensacion() "+userDTO.getCiudadSedeCajaCompensacion());
        Date fechaHoraInicio = new Date();
        String url = "servicio-solicitarRetiroPersonaAutorizada/confirmarValorEntregado";
        final String resultado = "resultado";
        final String error = "error";
        final String idRespuesta = "identificadorRespuesta";
        Map<String, String> respuesta = null;
        Long identificadorRespuesta = null;
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", confirmacionEntregaPersonaAutorizadaDTO.getTipoIdAdmin());
        parametrosEntrada.put("numeroIdentificadorAdmon", confirmacionEntregaPersonaAutorizadaDTO.getNumeroIdAdmin());
        parametrosEntrada.put("medioDePago", confirmacionEntregaPersonaAutorizadaDTO.getMedioDePago());
        parametrosEntrada.put("saldoActualSubsidio", confirmacionEntregaPersonaAutorizadaDTO.getSaldoActualSubsidio());
        parametrosEntrada.put("valorSolicitado", confirmacionEntregaPersonaAutorizadaDTO.getValorSolicitado());
        parametrosEntrada.put("fecha", confirmacionEntregaPersonaAutorizadaDTO.getFecha());
        parametrosEntrada.put("idTransaccionTercerPagador", confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador());
        parametrosEntrada.put("departamento", confirmacionEntregaPersonaAutorizadaDTO.getDepartamento());
        parametrosEntrada.put("municipio", confirmacionEntregaPersonaAutorizadaDTO.getMunicipio());
        parametrosEntrada.put("usuario", confirmacionEntregaPersonaAutorizadaDTO.getUsuario());
        parametrosEntrada.put("idPersonaAutorizada", confirmacionEntregaPersonaAutorizadaDTO.getIdPersonaAutorizada());
        if(confirmacionEntregaPersonaAutorizadaDTO.getDocumentoSoporte() != null){
            parametrosEntrada.put("IdDocumentoSoporte", confirmacionEntregaPersonaAutorizadaDTO.getDocumentoSoporte().getIdDocumentoSoporte());
        }
        parametrosEntrada.put("nombreUserDTO", userDTO.getNombreUsuario());
        parametrosEntrada.put("emailUserDTO", userDTO.getEmail());

        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();
        String parametrosOUT = null;

        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore.consultarRegistrosAbonosParaCalcularSaldoSubsidio(
                confirmacionEntregaPersonaAutorizadaDTO.getTipoIdAdmin(), confirmacionEntregaPersonaAutorizadaDTO.getNumeroIdAdmin(),
                confirmacionEntregaPersonaAutorizadaDTO.getMedioDePago());

        respuesta = validarSolicitudRetiroSubsidio(confirmacionEntregaPersonaAutorizadaDTO.getSaldoActualSubsidio(),
                confirmacionEntregaPersonaAutorizadaDTO.getValorSolicitado(),
                confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador(),
                confirmacionEntregaPersonaAutorizadaDTO.getMedioDePago(), userDTO.getEmail(), listaCuentaAdminSubsidio, resultado, error, idRespuesta,
                parametrosIN, TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_PERSONA_AUTORIZADA_CONFIRMAR_VALOR_ENTREGADO,
                confirmacionEntregaPersonaAutorizadaDTO.getUsuario(),
                confirmacionEntregaPersonaAutorizadaDTO.getIdPuntoCobro(), null);

        //si el objeto es diferente de null es porque ha ocurrido un error y se retorna dicho resultado.
        if (respuesta != null) {
            return respuesta;
        }

        respuesta = new HashMap<>();

        //se registra la operación
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_PERSONA_AUTORIZADA_CONFIRMAR_VALOR_ENTREGADO, parametrosIN,
                userDTO.getNombreUsuario(), listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio());

        String usuarioRetiro = (confirmacionEntregaPersonaAutorizadaDTO.getUsuario() != null
                && !confirmacionEntregaPersonaAutorizadaDTO.getUsuario().equals("")) ? confirmacionEntregaPersonaAutorizadaDTO.getUsuario()
                        : userDTO.getNombreUsuario();

        if (confirmacionEntregaPersonaAutorizadaDTO.getMunicipio() == null || confirmacionEntregaPersonaAutorizadaDTO.getMunicipio().isEmpty()) { //se agrega municipio.isEmpty()
            if(userDTO.getCiudadSedeCajaCompensacion()!= null){
                confirmacionEntregaPersonaAutorizadaDTO.setMunicipio(consultasCore.buscarMunicipioPorCodigo(userDTO.getCiudadSedeCajaCompensacion()));
            }else{
                String codigoMunicpioCCF = (String) CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID);
                confirmacionEntregaPersonaAutorizadaDTO.setMunicipio(consultasCore.buscarMunicipioPorCodigo(codigoMunicpioCCF));
            }
        }

        //se realiza el proceso de retiro 
        Long idRetiroCuenta = registrarTransaccionRetiro(listaCuentaAdminSubsidio,
                confirmacionEntregaPersonaAutorizadaDTO.getSaldoActualSubsidio(),
                confirmacionEntregaPersonaAutorizadaDTO.getValorSolicitado(), confirmacionEntregaPersonaAutorizadaDTO.getFecha(),
                confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador(),
                confirmacionEntregaPersonaAutorizadaDTO.getMunicipio(), usuarioRetiro, null, EstadoTransaccionSubsidioEnum.SOLICITADO,
                confirmacionEntregaPersonaAutorizadaDTO.getIdPuntoCobro(),null);

        if (idRetiroCuenta == -1) {
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "No se encontro un Sitio de pago asociado");
            return respuesta;
        }

        consultasCore.actualizarEstadoTransaccionRetiro(EstadoTransaccionSubsidioEnum.FINALIZADO.name(), idRetiroCuenta);
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidio = null;
        //PASOS PARA LA CONFIRMACIÓN DEL RETIRO
        if (confirmacionEntregaPersonaAutorizadaDTO.getIdPuntoCobro() != null) {
       	 //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
           listaCuentasAdminSubsidio = consultasCore
                   .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(confirmacionEntregaPersonaAutorizadaDTO.getTipoIdAdmin(), confirmacionEntregaPersonaAutorizadaDTO.getNumeroIdAdmin(),
                           confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador(),
                           confirmacionEntregaPersonaAutorizadaDTO.getUsuario(),
                           confirmacionEntregaPersonaAutorizadaDTO.getIdPuntoCobro());
       } else {
       	 //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
           listaCuentasAdminSubsidio = consultasCore
                   .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(confirmacionEntregaPersonaAutorizadaDTO.getTipoIdAdmin(), confirmacionEntregaPersonaAutorizadaDTO.getNumeroIdAdmin(),
                           confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador());
       }

        //Se valida que si hayan abonos con estado 'SOLICITADO' de una solicitud de retiro previo.
        if (listaCuentasAdminSubsidio == null) {
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                    TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_PERSONA_AUTORIZADA_CONFIRMAR_VALOR_ENTREGADO, parametrosIN,
                    userDTO.getNombreUsuario(), null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "No se puede realizar la confirmación del valor entregado de la persona autorizada. El administrador no ha realizado un retiro previo ó el identificador de transacción del tercero pagador es incorrecto");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);

            return respuesta;
        }

        BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(
                confirmacionEntregaPersonaAutorizadaDTO.getTipoIdAdmin(), confirmacionEntregaPersonaAutorizadaDTO.getNumeroIdAdmin(),
                confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador());

        //si el valor solicitado es igual al valor entregado,se realizo un retiro completo,
        //se ejecuta  HU-31-218 3.1.3.1  Caso: Retiro confirmado completo
        //no se da el caso de un retiro incompleto
        if (confirmacionEntregaPersonaAutorizadaDTO.getValorSolicitado().compareTo(valorEntregadoRetiro) == 0) {

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                //3.1.3.1 Confirmación retiro completo
                cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                cuenta.setFechaHoraUltimaModificacion(new Date());
                cuenta.setUsuarioUltimaModificacion(usuarioRetiro);

                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
            }
        }

        //SE REALIZA EL REGISTRO DE LA PERSONA AUTORIZADA CONFIRMADA PARA EL RETIRO.
        consultasCore.registrarPersonaAutorizadaParaRealizarRetiro(confirmacionEntregaPersonaAutorizadaDTO.getIdPersonaAutorizada(),
                idRetiroCuenta, confirmacionEntregaPersonaAutorizadaDTO.getDocumentoSoporte());

        respuesta.put(idRespuesta, String.valueOf(idRetiroCuenta));//String.valueOf(identificadorRespuesta));
        respuesta.put(resultado, String.valueOf(true));
        //se anexa el identificador del tercero pagador
        respuesta.put("idTransaccionTerceroPagador", confirmacionEntregaPersonaAutorizadaDTO.getIdTransaccionTercerPagador());

        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidacion(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionMontoLiquidadoDTO consultarDispersionMontoLiquidacion(String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionMontoLiquidadoDTO dispersionMonto = consultasCore.consultarDispersionMontoLiquidacion(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionMonto;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoPagoTarjeta()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoPagoTarjeta(String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResultadoPagoTarjetaDTO dispersionTarjeta = consultasCore.consultarDispersionMontoLiquidadoPagoTarjeta(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionTarjeta;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoPagoEfectivo()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoPagoEfectivo(String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResultadoPagoEfectivoDTO dispersionEfectivo = consultasCore
                .consultarDispersionMontoLiquidadoPagoEfectivo(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionEfectivo;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoPagoBanco()
     */
    @Override
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoPagoBanco(String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoPagoBanco(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResultadoPagoBancoDTO dispersionBanco = consultasCore.consultarDispersionMontoLiquidadoPagoBanco(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionBanco;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersonMontoDescuentosPorEntidad()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoDescuentosPorEntidad(String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDispersonMontoDescuentosPorEntidad(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        DispersionResultadoEntidadDescuentoDTO dispersionEntidadesDescuento = consultasCore
                .consultarDispersionMontoDescuentosPorEntidad(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return dispersionEntidadesDescuento;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentaAdmonSubsidioDTO(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioDTO consultarCuentaAdmonSubsidioDTO(Long idCuentaAdmonSubsidio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarCuentaAdmonSubsidioDTO(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = consultasCore
                .consultarCuentaAdministradorSubsidio(idCuentaAdmonSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAdministradorSubsidioOrigDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesDTOPorIDs(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesDTOPorIDs(List<Long> listaIdsDetalles) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDetallesDTOPorIDs(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultasCore.consultarDetallesDTOPorIDs(listaIdsDetalles);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesSubsidiosAsignadosAsociadosAbono(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidiosAsignadosAsociadosAbono(Long idCuentaAdmin) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarDetallesSubsidiosAsignadosAsociadosAbono(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAdmin);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaDetalles;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarConvenioTerceroPagadorPorIdEmpresa(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorIdEmpresa(Long idEmpresa) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarConvenioTerceroPagadorPorIdEmpresa(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConvenioTercerPagadorDTO convenioTerceroPagador = consultasCore.consultarConvenioTerceroPagadorPorIdEmpresa(idEmpresa);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return convenioTerceroPagador;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#realizarReversionRetiros(java.util.List)
     */
    @Override
    public void realizarReversionRetiros(List<CuentaAdministradorSubsidioDTO> retirosReversion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.realizarReversionRetiros(List<CuentaAdministradorSubsidioDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        final String usuarioGenesys = "Genesys";

        for (CuentaAdministradorSubsidioDTO retiro : retirosReversion) {

            CuentaAdministradorSubsidioDTO cuentaRetiro = consultasCore
                    .consultarRetiroTarjetaParaReversion(retiro.getIdTransaccionTerceroPagador(), retiro.getNumeroTarjetaAdminSubsidio());

            //se registra como transacción fallida si no se encuentra el retiro asociado al identificador de transacción del tercero pagador
            //y el número de tarjeta
            if (cuentaRetiro == null) {
                TransaccionFallidaDTO transaccionFallidaDTO = new TransaccionFallidaDTO();
                transaccionFallidaDTO.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
                gestionarTransaccionesFallidas(transaccionFallidaDTO);
            }
            else {
                //se realiza la reversión
                //paso 2: se obtienen los abonos asociados al retiro que se hará la reversión que estan en estado COBRADO
                List<CuentaAdministradorSubsidioDTO> abonosAsociadosRetiro = consultasCore
                        .consultarAbonosCobradosAsociadosRetiroParaReversion(retiro.getIdTransaccionTerceroPagador());

                for (CuentaAdministradorSubsidioDTO abonoCobrado : abonosAsociadosRetiro) {
                    //se modifica cada registro asociado al retiro
                    abonoCobrado.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                    abonoCobrado.setIdCuentaAdminSubsidioRelacionado(null);
                    abonoCobrado.setFechaHoraUltimaModificacion(new Date());
                    abonoCobrado.setUsuarioUltimaModificacion(usuarioGenesys);
                    consultasCore.actualizarCuentaAdministradorSubsidio(abonoCobrado);

                    //paso 3:Identificar registros en el “Detalle de subsidio asignado” que están relacionados con los abonos modificados en el paso 2.
                    actualizarDetallesDeAbonosCobradosRetiroParaReversar(abonoCobrado.getIdCuentaAdministradorSubsidio(), usuarioGenesys);
                }
                //paso 1: se actualiza los valores del retiro a ser revertido.
                cuentaRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.REVERSION);
                cuentaRetiro.setOrigenTransaccion(OrigenTransaccionEnum.REVERSION);
                cuentaRetiro.setFechaHoraUltimaModificacion(new Date());
                cuentaRetiro.setUsuarioUltimaModificacion(usuarioGenesys);
                consultasCore.actualizarCuentaAdministradorSubsidio(cuentaRetiro);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#buscarNombreArchivoConsumoTarjetaANIBOL(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Boolean buscarNombreArchivoConsumoTarjetaANIBOL(String nombreArchivo) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.buscarNombreArchivoConsumoTarjetaANIBOL(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Boolean validacion = consultasCore.consultarNombreArchivoConsumoTarjetaANIBOL(nombreArchivo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return validacion;
    }   

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#guardarRegistroArchivoConsumosAnibol(com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO)
     */
    @Override
    public Long guardarRegistroArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.guardarRegistroArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Long idarchivoConsumo = consultasCore.registrarArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idarchivoConsumo;
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#guardarRegistroArchivoConsumosAnibol(com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO)
     */
    @Override
    public Long guardarRegistroArchivoConsumosTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.guardarRegistroArchivoConsumosAnibol(ArchivoConsumosAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio); 

        Long idarchivoConsumo = consultasCore.registrarArchivoConsumosTerceroPagadorEfectivo(archivoConsumosDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idarchivoConsumo;
    }
    

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#validarCargarArchivoRetiroTarjetaAnibol(com.asopagos.dto.InformacionArchivoDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultadoValidacionArchivoRetiroDTO validarCargarArchivoRetiroTarjetaAnibol(InformacionArchivoDTO informacionArchivoDTO,
            UserDTO userDTO) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarCargarArchivoRetiroTarjetaAnibol(InformacionArchivoDTO,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoRetiroDTO resultado = new ResultadoValidacionArchivoRetiroDTO();
        resultado.setNombreArchivoRetiro(informacionArchivoDTO.getFileName());

        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(CacheManager
                    .getConstante(
                            ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_CONSUMO_RETIRO_TARJETAS_ANIBOL_PAGOS_SUBSIDIO_MONETARIO)
                    .toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat fileFormat;
        if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)
                || informacionArchivoDTO.getFileName().toLowerCase()
                        .endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN.toLowerCase())) {
            fileFormat = FileFormat.FIXED_TEXT_PLAIN;
        }
        else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }

        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<Long> totalRegistro = new ArrayList<>();
        List<Long> totalRegistroError = new ArrayList<>();
        List<Long> totalRegistroValidos = new ArrayList<>();
        List<Long> totalRegistroOtrasTransacciones = new ArrayList<>();
        List<TarjetaRetiroCandidatoDTO> listaCandidatos = new ArrayList<>();
        List<TarjetaRetiroCandidatoDTO> listaReversos = new ArrayList<>();
        List<TarjetaRetiroCandidatoDTO> listaOtrasTransacciones = new ArrayList<>();
        List<TarjetaRetiroCandidatoDTO> listaErroresHallazgos = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> camposErroresPorLinea = new ArrayList<>();
        context.put(CamposArchivoConstants.LISTA_CANDIDATOS, listaCandidatos);
        context.put(CamposArchivoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposArchivoConstants.LISTA_REVERSOS, listaReversos);
        context.put(CamposArchivoConstants.LISTA_OTRAS_TRANSACCIONES, listaOtrasTransacciones);
        context.put(CamposArchivoConstants.LISTA_ERRORES_HALLAZGOS, listaErroresHallazgos);
        context.put(CamposArchivoConstants.LISTA_CAMPOS_ERRORES_POR_LINEA, camposErroresPorLinea);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO, totalRegistroValidos);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_OTRAS_TRANSACCIONES, totalRegistroOtrasTransacciones);
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, informacionArchivoDTO.getDataFile(), fileDefinitionId,
                    informacionArchivoDTO.getFileName());
        } catch (FileProcessingException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext().get(CamposArchivoConstants.LISTA_HALLAZGOS);
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES);

            listaCandidatos = (List<TarjetaRetiroCandidatoDTO>) outDTO.getContext().get(CamposArchivoConstants.LISTA_CANDIDATOS);
            listaReversos = (List<TarjetaRetiroCandidatoDTO>) outDTO.getContext().get(CamposArchivoConstants.LISTA_REVERSOS);
            listaOtrasTransacciones = (List<TarjetaRetiroCandidatoDTO>) outDTO.getContext()
                    .get(CamposArchivoConstants.LISTA_OTRAS_TRANSACCIONES);
            listaErroresHallazgos = (List<TarjetaRetiroCandidatoDTO>) outDTO.getContext()
                    .get(CamposArchivoConstants.LISTA_ERRORES_HALLAZGOS);
            camposErroresPorLinea = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(CamposArchivoConstants.LISTA_CAMPOS_ERRORES_POR_LINEA);

            //se crea el dto del archivo de consumo
            ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO = new ArchivoConsumosAnibolModeloDTO();
            archivoConsumosAnibolModeloDTO.setTipoCargue(informacionArchivoDTO.getTipoCarga().equals(TipoCargueArchivoConsumoAnibolEnum.MANUAL.name())
                                                        ?TipoCargueArchivoConsumoAnibolEnum.MANUAL:TipoCargueArchivoConsumoAnibolEnum.AUTOMATICO); // TipoCargueArchivoConsumoAnibolEnum.MANUAL);
            archivoConsumosAnibolModeloDTO.setUsuarioProcesamiento(userDTO.getNombreUsuario());
            archivoConsumosAnibolModeloDTO.setFechaHoraProcesamiento(new Date());
            archivoConsumosAnibolModeloDTO.setNombreArchivo(informacionArchivoDTO.getFileName());
            archivoConsumosAnibolModeloDTO.setIdDocumento(informacionArchivoDTO.getIdentificadorDocumento());
            archivoConsumosAnibolModeloDTO.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());

            //se valida que no hayan error de estructura encontrados por lion y se valida que haya registros candidatos para almacenar la información del archivo.
            if ((outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty())) {
                System.out.println("ERRORES LION");
                for (DetailedErrorDTO detError : outDTO.getDetailedErrors()) {                  
                    System.out.println(detError.getMessage());
                }
                
                //se registran errores de estructura
                archivoConsumosAnibolModeloDTO
                        .setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.ARCHIVO_CON_REGISTROS_LONGITUD_INVALIDA);
                archivoConsumosAnibolModeloDTO.setArchivoNotificado((byte) 1);
                archivoConsumosAnibolModeloDTO.setResultadoValidacionEstructura(ResultadoValidacionArchivoConsumoAnibolEnum.NO_APROBADO);

                Long idArchivoConsumoTarjeta = guardarRegistroArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);

                resultado.setIdArchivoConsumoAnibol(idArchivoConsumoTarjeta);
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio
                        + "Ningun registro del archivo cumplen con la validación de estructura de los campos");
                resultado.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.VALIDACIONES_NO_EXISTOSAS);
                return resultado;
            }
            //si pasa las validaciones de estructura
            archivoConsumosAnibolModeloDTO.setEstadoArchivo(EstadoArchivoConsumoAnibolEnum.EN_PROCESO);
            archivoConsumosAnibolModeloDTO
                    .setResultadoValidacionEstructura(ResultadoValidacionArchivoConsumoAnibolEnum.ARCHIVO_CONSISTENTE);

            //si hay registros con error, se deben guardar y notificar para el comunicado
            if (!listaHallazgos.isEmpty()) {
                //se registran los errores que hubo en la validación de cada campo
                archivoConsumosAnibolModeloDTO
                        .setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.ARCHIVO_CON_REGISTROS_LONGITUD_INVALIDA);
                archivoConsumosAnibolModeloDTO.setArchivoNotificado((byte) 1);
                archivoConsumosAnibolModeloDTO.setResultadoValidacionContenido(ResultadoValidacionArchivoConsumoAnibolEnum.NO_APROBADO);

                archivoConsumosAnibolModeloDTO.setEstadoArchivo(EstadoArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
                Long idArchivoConsumoTarjeta = guardarRegistroArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);
                //se registra cada linea del archivo y sus respectivos errores     
                consultasCore.crearCamposInconsistenciasArchivoConsumoTarjetaANIBOL(idArchivoConsumoTarjeta, listaErroresHallazgos,
                        listaHallazgos, camposErroresPorLinea);

                resultado.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.PROCESADO_CON_INCONSISTENCIA);
                resultado.setIdArchivoConsumoAnibol(idArchivoConsumoTarjeta);
                return resultado;
            }
            //se registra el archivo
            Long idArchivoConsumoTarjeta = guardarRegistroArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);
            //se almacena el id del archivo
            resultado.setIdArchivoConsumoAnibol(idArchivoConsumoTarjeta);
            //se registran las otras transacciones
            consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, listaOtrasTransacciones);

            //se realiza la comparación de los retiros y los reversos por el código de autorización (id transacción tercero pagador) 
            //si existe dicho codigo en retiros y reversos, no se procede a realizar cada uno de estos y se eliminan de sus respectiva listas.
            compararRegistrosTarjetas(idArchivoConsumoTarjeta, listaCandidatos, listaReversos);

            //se obtienen los registros de tarjetas que no estan asociadas con ninguna cuenta del administrador del subsidio
            //y las que no tienen saldo insuficiente en las cuentas. Las que pasan estos filtros se procede a realizar el retiro
            //Crear cuenta administrador subsidio 
            final List<TarjetaRetiroCandidatoDTO> tarjetasNoAsociadasSaldoInsuficiente = compararRegistrosTarjetasCuentaAdminSubsidio(
                    listaCandidatos);

            //si hay tarjetas con saldo insuficientes o no registradas, se quitan de las que son candidatas
            if (!tarjetasNoAsociadasSaldoInsuficiente.isEmpty()) {
                //se almacenan los registros que tuvieron alguna inconsistencia (no asociadas alguna cuenta o saldo insuficiente en la cuenta)
                consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, tarjetasNoAsociadasSaldoInsuficiente);

                Predicate<TarjetaRetiroCandidatoDTO> predicado = n -> tarjetasNoAsociadasSaldoInsuficiente.contains(n);
                listaCandidatos.removeIf(predicado);
            }
            //se procede a guardar los registros de tarjetas de retiro y las de reverso
            if (!listaCandidatos.isEmpty())
                consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, listaCandidatos);
            if (!listaReversos.isEmpty())
                consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, listaReversos);
            //se actualizan los campos del archivo de consumo
            archivoConsumosAnibolModeloDTO.setIdArchivoConsumosSubsidioMonetario(idArchivoConsumoTarjeta);
            archivoConsumosAnibolModeloDTO.setEstadoArchivo(EstadoArchivoConsumoAnibolEnum.PROCESADO);
            archivoConsumosAnibolModeloDTO.setResultadoValidacionContenido(ResultadoValidacionArchivoConsumoAnibolEnum.ARCHIVO_CONSISTENTE);
            //actualización del archivo de consumo
            consultasCore.actualizarArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);
            //si el estado del resultado sigue vacío es debido a que no hay incosistencias en el proceso
            if (resultado.getEstadoValidacion() == null)
                resultado.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.PROCESADO);

        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#obtenerArchivoConsumoTarjetaANIBOLFTP()
     */
    @Override
    public List<InformacionArchivoDTO> obtenerArchivoConsumoTarjetaANIBOLFTP() {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.obtenerArchivoConsumoTarjetaANIBOLFTP()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        InformacionArchivoDTO informacionArchivoDTO = null;
        List<InformacionArchivoDTO> lstInformacionArchivoDTO = null;

        try {
            lstInformacionArchivoDTO = obtenerArchivosFTP();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstInformacionArchivoDTO;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarSolicitudAnibol(com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO)
     */
    @Override
    public Long registrarSolicitudAnibol(RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.registrarSolicitudAnibol(RegistroSolicitudAnibolModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("entró al metodo de registrar la solicitud anibol con el siguiente payload: "+ reAnibolModeloDTO.toString());
        Long idRegistroSolicitudAnibol = consultasCore.registrarSolicitudAnibol(reAnibolModeloDTO);
        // si se devuelve null es debido al parametro de entrada que es mayor alo estipulado
        if(idRegistroSolicitudAnibol == null){
            reAnibolModeloDTO.setParametrosIN("LONGITUD_DE_PARAMETROS_NO_PERMITIDA_MAYOR_A_500");
            idRegistroSolicitudAnibol = consultasCore.registrarSolicitudAnibol(reAnibolModeloDTO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);       
        return idRegistroSolicitudAnibol;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarRegistroSolicitudAnibol(java.lang.Long, java.lang.String)
     */
    @Override
    public void actualizarRegistroSolicitudAnibol(Long idRegistroSolicitudAnibol, String parametrosOUT) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.actualizarRegistroSolicitudAnibol(Long, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if(consultasCore.actualizarRegistroSolicitudAnibol(idRegistroSolicitudAnibol,parametrosOUT))
            consultasCore.actualizarRegistroSolicitudAnibolError(idRegistroSolicitudAnibol);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo); 
    }
    
    /**
     * Metodo encargado de registrar la carga del archivo de retiro del tercero pagador.
     * 
     * @param idDocumento
     *        identificador del documento por parte del CM
     * @param nombreDocumento
     *        nombre del documento almacenado en el CM
     * @param versionDocumento
     *        versión del documento almacenado en el CM
     * @param nombreUsuario
     *        nombre del usuario que realizo la carga del archivo
     * @return id de la carga del archivo.
     */
    @Override
    public Long registrarArchivoTerceroPagadorEfectivo(String idDocumento, String nombreDocumento, String versionDocumento,
            String nombreUsuario) { 

        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarCargueArchivoTerceroPagador(String idDocumento, String nombreDocumento, String versionDocumento, String nombreUsuario)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoRetiroTerceroPagadorEfectivoDTO cargueArchivoRetiro = new ArchivoRetiroTerceroPagadorEfectivoDTO(); 

        cargueArchivoRetiro.setIdDocumento(idDocumento);
        cargueArchivoRetiro.setNombreArchivo(nombreDocumento);
        cargueArchivoRetiro.setVersionDocumento(versionDocumento);
        cargueArchivoRetiro.setFechaHoraProcesamiento(new Date());
        cargueArchivoRetiro.setUsuarioProcesamiento(nombreUsuario);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return consultasCore.registrarArchivoConsumosTerceroPagadorEfectivo(cargueArchivoRetiro);
    }

    /**
     * Metodo encargado de registrar la carga del archivo de retiro del tercero pagador.
     * 
     * @param idDocumento
     *        identificador del documento por parte del CM
     * @param nombreDocumento
     *        nombre del documento almacenado en el CM
     * @param versionDocumento
     *        versión del documento almacenado en el CM
     * @param nombreUsuario
     *        nombre del usuario que realizo la carga del archivo
     * @return id de la carga del archivo.
     */
    private Long registrarCargueArchivoTerceroPagador(String idDocumento, String nombreDocumento, String versionDocumento,
            String nombreUsuario) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarCargueArchivoTerceroPagador(String idDocumento, String nombreDocumento, String versionDocumento, String nombreUsuario)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoRetiroTerceroPagador cargueArchivoRetiro = new ArchivoRetiroTerceroPagador();

        cargueArchivoRetiro.setIdDocumento(idDocumento);
        cargueArchivoRetiro.setNombreDocumento(nombreDocumento);
        cargueArchivoRetiro.setVersionDocumento(versionDocumento);
        cargueArchivoRetiro.setFechaHoraProcesamiento(new Date());
        cargueArchivoRetiro.setUsuarioProcesamiento(nombreUsuario);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return consultasCore.registrarCargueArchivoTerceroPagador(cargueArchivoRetiro);
    }

    /**
     * Metodo encargado de crear el registro de cada archivo de retiro del tercer pagador
     * y cada campo
     * @param retirosCandidatos
     *        registros de retiro que son candidatos a ser conciliados despues de la validación en base de datos
     * @param idCargueArchivoRetiro
     *        identificador que tiene registrado en base de datos el cargue del archivo de retiro
     */
    private void crearRegistrosCamposArchivoTerceroPagador(List<RetiroCandidatoDTO> retirosCandidatos, Long idCargueArchivoRetiro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.crearRegistrosCamposArchivoTerceroPagador(List<RetiroCandidatoDTO> registrosCandidatos, Long idCargueArchivoRetiro)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        consultasCore.crearRegistrosCamposArchivoTerceroPagador(retirosCandidatos, idCargueArchivoRetiro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Metodo que se encarga de anular un abono tanto para las HUs 208, 221 y 207,220.
     * Retorna el id de la cuenta (abono) que se creo con la anulación del anterior.
     * 
     * @param cuentaAdministradorSubsidioOrigDTO
     *        <code>CuentaAdministradorSubsidioDTO</code>
     *        cuenta de administrador del subsidio (abono) que sera anulado
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param resultadoValidacion
     * @return true si el proceso se realizo satisfactoriamente, false si por el contrario hubo un error al validar con anibol cuando
     *         el medio de pago es tarjeta
     */
    private boolean crearAbonoAnulado(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO, UserDTO userDTO,
            Boolean resultadoValidacion) {

        String firma = "crearAbonoAnulado(CuentaAdministradorSubsidioDTO, UserDTO, Boolean";
        
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma + " con parámetros: " + cuentaAdministradorSubsidioOrigDTO.toString() + "resultadoValidacion" + resultadoValidacion);
        //se clona la cuenta que se va anular para crear un nuevo registro de anulación
        CuentaAdministradorSubsidioDTO cuentaAdmonSubsidioAnulada = cuentaAdministradorSubsidioOrigDTO.clone();

        //al clonarse, se debe poner en null el id de la base de datos que referencia a la cuenta (al abono)
        cuentaAdmonSubsidioAnulada.setIdCuentaAdministradorSubsidio(null);

        //si el medio de pago es tarjeta lo unico diferente a la anulación en efectivo
        //es que se verifica la validez de la tarjeta por medio de ANIBOL
        if (TipoMedioDePagoEnum.TARJETA.equals(cuentaAdministradorSubsidioOrigDTO.getMedioDePago())) {
            //Se realiza el paso 3.1 de la HU-31-208 cuando es por tarjeta
            cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.EN_DEVOLUCION);
            cuentaAdministradorSubsidioOrigDTO.setFechaHoraUltimaModificacion(new Date());
            
            cuentaAdministradorSubsidioOrigDTO.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");

            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);

            if (!resultadoValidacion) {
                //SI LA VALIDACIÓN CON ANIBOL NO ES EXITOSA SE DEVUELVE UN FALS PARE
                logger.debug(ConstantesComunes.FIN_LOGGER + firma + " la validación con anibol no fue exitosa.");
                return false;
            }
        }
        //se realizan las modificaciones pertinentes en el registro antiguo para la creación del nuevo abono (CuentaAdminSubsidio) 
        cuentaAdmonSubsidioAnulada.setOrigenTransaccion(OrigenTransaccionEnum.ANULACION);
        cuentaAdmonSubsidioAnulada.setIdTransaccionOriginal(cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio());
        cuentaAdmonSubsidioAnulada.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ANULACION);
        cuentaAdmonSubsidioAnulada.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
        cuentaAdmonSubsidioAnulada.setFechaHoraTransaccion(new Date());
        cuentaAdmonSubsidioAnulada.setFechaHoraCreacionRegistro(new Date());
        cuentaAdmonSubsidioAnulada.setFechaHoraUltimaModificacion(new Date());
        if(userDTO != null && userDTO.getNombreUsuario() != null){
            cuentaAdmonSubsidioAnulada.setUsuarioTransaccionLiquidacion(userDTO.getNombreUsuario());
        }
        cuentaAdmonSubsidioAnulada.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");
        cuentaAdmonSubsidioAnulada.setMedioDePago(cuentaAdministradorSubsidioOrigDTO.getMedioDePago());

        //Paso 1: se crea la nueva cuenta de administrador de subsidio 
        Long idNuevaCuentaAdmonSubsidio = registrarCuentaAdministradorSubsidio(cuentaAdmonSubsidioAnulada, userDTO);
        logger.info("idNuevaCuentaAdmonSubsidio " +idNuevaCuentaAdmonSubsidio);

        //Paso 2: se modifica el registro de abono original
        cuentaAdministradorSubsidioOrigDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ANULADO);
        cuentaAdministradorSubsidioOrigDTO.setFechaHoraUltimaModificacion(new Date());
        cuentaAdministradorSubsidioOrigDTO.setUsuarioUltimaModificacion((userDTO != null && userDTO.getNombreUsuario() != null)? userDTO.getNombreUsuario() : "Genesys");
        cuentaAdministradorSubsidioOrigDTO.setIdCuentaAdminSubsidioRelacionado(idNuevaCuentaAdmonSubsidio);
        cuentaAdministradorSubsidioOrigDTO.setFechaHoraCreacionRegistro(new Date());
        //se realiza la actualización del registro original
        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return true;
    }

    /**
     * Metodo encargado de reemplazar los detalles de subsidios asignados relacionados a la
     * cuenta del administrador del subsidio (abono) que sera anulado. Sirve las HUs 208,221 y 207,220.
     * 
     * @param listaAnularDetallesDTO
     *        Lista de los detalles de subsidios asignados que serán anulados.
     * @param idCuentaAdmonSubsidio
     *        Identificador de la base de datos que tiene la cuenta de administrador del subsidio (abono) que sera anulado
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @return lista de detalles de subsidio asignados que no seran anulados con la nueva información para ser registrados de nuevo en otro
     *         abono
     */
    private List<DetalleSubsidioAsignadoDTO> separarDetallesSubsidiosAsignadosReemplazados(
            List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO, Long idCuentaAdmonSubsidio, UserDTO userDTO) {

        //se obtiene los detalles de subsidio asignados del abono que no serán anulados
        List<DetalleSubsidioAsignadoDTO> listaDetallesNoAnulados = consultasCore.consultarDetallesSubsidioAsinadosNoAnulados(
                consultasCore.obtenerListaIdsDetallesSubsidioAsignado(listaAnularDetallesDTO), idCuentaAdmonSubsidio);

        //lista en donde se almacenaran los nuevos detalles de subsidio asignado.
        List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignadosReemplazados = new ArrayList<>();

        //Paso 3: se actualiza cada detalle de subsidio asignado que no va a ser anulado
        for (DetalleSubsidioAsignadoDTO detalle : listaDetallesNoAnulados) {

            detalle.setEstado(EstadoSubsidioAsignadoEnum.ANULADO_REEMPLAZADO);
            detalle.setFechaTransaccionAnulacion(new Date());
            detalle.setFechaHoraUltimaModificacion(new Date());
            detalle.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");

            consultasCore.actualizarDetalleSubsidioAsignado(detalle);

            //Paso 4: creación de los detalles de subsidio asignados para reemplazar los anulados reemplazados
            DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO = detalle.clone();
            detalleSubsidioAsignadoDTO.setIdCuentaAdministradorSubsidio(null);
            detalleSubsidioAsignadoDTO.setIdDetalleSubsidioAsignado(null);
            detalleSubsidioAsignadoDTO.setFechaHoraCreacion(new Date());
            if(userDTO != null){
                detalleSubsidioAsignadoDTO.setUsuarioCreador(userDTO.getNombreUsuario());
            }
            detalleSubsidioAsignadoDTO.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
            detalleSubsidioAsignadoDTO.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.ANULACION);
            detalleSubsidioAsignadoDTO.setIdRegistroOriginalRelacionado(detalle.getIdDetalleSubsidioAsignado());
            detalleSubsidioAsignadoDTO.setFechaHoraUltimaModificacion(new Date());
            detalleSubsidioAsignadoDTO.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");

            detallesSubsidiosAsignadosReemplazados.add(detalleSubsidioAsignadoDTO);
        }

        return detallesSubsidiosAsignadosReemplazados;
    }

    /**
     * Metodo que se encarga de crear un nuevo abono (Cuenta de administrador del subsidio) a partir del abono que
     * se esta anulado, para asociar lo con los nuevos detalles de subsidios asignados que se crearón a partir de
     * los anulados. Sirve para las HUs 208, 221 y 207,220
     * 
     * @param cuentaAdministradorSubsidioOrigDTO
     *        Cuenta de administrador del subsidio que sera anulada.
     * @param detallesSubsidiosAsignadosReemplazados
     *        Lista de los detalles de los subsidios asignados que seran asociados al nuevo abono.
     * @param userDTO
     *        Información de usuario que registra la transacción en el sistema.
     * @param medioDePagoModelo
     *        medio de pago por el cual se cambiara los valores de la cuenta (solo aplica cuando se ejecuta la hu-31-219)
     * @return Identificador de la base de datos con el que quedo guardado el nuevo abono.
     */
    private Long crearNuevoAbonoActualizarDetallesRelacionados(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO,
        List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignadosReemplazados, UserDTO userDTO,
        MedioDePagoModeloDTO medioDePagoModelo) {

        //Se crea un nuevo abono (Cuenta) para registrar los nuevos detalles creados en el paso 4 y/o 7
        CuentaAdministradorSubsidioDTO nuevaCuentaAdminSubsidio = cuentaAdministradorSubsidioOrigDTO.clone();

        nuevaCuentaAdminSubsidio.setIdTransaccionOriginal(cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio());
        nuevaCuentaAdminSubsidio.setIdCuentaAdministradorSubsidio(null);
        nuevaCuentaAdminSubsidio.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
        if(cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado() != null && cuentaAdministradorSubsidioOrigDTO.getEsFlujoTraslado()){
            // validar estados correctos
            // TODO la linea siguiente se comenta por solicitud funcional
            nuevaCuentaAdminSubsidio.setOrigenTransaccion(OrigenTransaccionEnum.TRASLADO_DE_SALDO);
            nuevaCuentaAdminSubsidio.setEstadoLiquidacionSubsidio(EstadoTransaccionSubsidioEnum.GENERADO);
            switch(String.valueOf(medioDePagoModelo.getTipoMedioDePago())){
                case "EFECTIVO":
                nuevaCuentaAdminSubsidio.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                break;
                case "TRANSFERENCIA":
                nuevaCuentaAdminSubsidio.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                break;
                case "TARJETA":
                nuevaCuentaAdminSubsidio.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                break;
            }
        }else{
            nuevaCuentaAdminSubsidio.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
            nuevaCuentaAdminSubsidio.setOrigenTransaccion(OrigenTransaccionEnum.ANULACION);
        }
        nuevaCuentaAdminSubsidio.setFechaHoraUltimaModificacion(new Date());
        nuevaCuentaAdminSubsidio.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");
        nuevaCuentaAdminSubsidio.setListaDetallesSubsidioAsignadoDTO(detallesSubsidiosAsignadosReemplazados);
        nuevaCuentaAdminSubsidio.setIdCuentaAdminSubsidioRelacionado(null);
        logger.warn("estado de la cuenta original 4"+ String.valueOf(cuentaAdministradorSubsidioOrigDTO.getEstadoTransaccion()));

        logger.warn("estado de la cuenta nueva 1"+ String.valueOf(nuevaCuentaAdminSubsidio.getEstadoTransaccion()));

        if (medioDePagoModelo != null) {

            limpiarCamposCuentaCambioMedioDePago(nuevaCuentaAdminSubsidio);

            if (TipoMedioDePagoEnum.TARJETA.equals(medioDePagoModelo.getTipoMedioDePago())) {
                nuevaCuentaAdminSubsidio.setNumeroTarjetaAdminSubsidio(medioDePagoModelo.getNumeroTarjeta());
                nuevaCuentaAdminSubsidio.setMedioDePago(TipoMedioDePagoEnum.TARJETA);
                nuevaCuentaAdminSubsidio.setIdMedioDePago(medioDePagoModelo.getIdMedioDePago());
            }
            else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioDePagoModelo.getTipoMedioDePago())) {
                nuevaCuentaAdminSubsidio.setNombreBancoAdminSubsidio(medioDePagoModelo.getNombreBanco());
                nuevaCuentaAdminSubsidio.setCodigoBancoAdminSubsidio(medioDePagoModelo.getCodigoBanco());
                //nuevaCuentaAdminSubsidio.setNombreTitularCuentaAdminSubsidio(medioDePagoModelo.getNombreTitularCuenta());
                nuevaCuentaAdminSubsidio.setNombreTitularCuentaAdminSubsidio(consultasCore.consultarMediosDePagoPorId(medioDePagoModelo.getIdMedioDePago()).getNombreTitularCuenta());
                nuevaCuentaAdminSubsidio
                        .setNumeroIdentificacionTitularCuentaAdminSubsidio(medioDePagoModelo.getNumeroIdentificacionTitular());
                nuevaCuentaAdminSubsidio.setTipoCuentaAdminSubsidio(medioDePagoModelo.getTipoCuenta());
                nuevaCuentaAdminSubsidio.setNumeroCuentaAdminSubsidio(medioDePagoModelo.getNumeroCuenta());
                nuevaCuentaAdminSubsidio.setTipoIdentificacionTitularCuentaAdminSubsidio(medioDePagoModelo.getTipoIdentificacionTitular());
                nuevaCuentaAdminSubsidio.setMedioDePago(TipoMedioDePagoEnum.TRANSFERENCIA);
                nuevaCuentaAdminSubsidio.setIdMedioDePago(medioDePagoModelo.getIdMedioDePago());
            }
            else {
                nuevaCuentaAdminSubsidio.setMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
                nuevaCuentaAdminSubsidio.setIdSitioDePago(medioDePagoModelo.getSitioPago());
                nuevaCuentaAdminSubsidio.setIdMedioDePago(medioDePagoModelo.getIdMedioDePago());
            }
        }

        //se crea la cuenta del administrador del subsidio (nuevo abono con los detalles)        
        Long idNuevaCuentaAdminSubsidio = registrarCuentaAdministradorSubsidio(nuevaCuentaAdminSubsidio, userDTO);

        // se actualizan los datos de los nuevos detalles relacionando el abono creado anteriormente.
        for (DetalleSubsidioAsignadoDTO detalle : detallesSubsidiosAsignadosReemplazados) {

            detalle.setIdCuentaAdministradorSubsidio(idNuevaCuentaAdminSubsidio);
            detalle.setUsuarioUltimaModificacion(userDTO != null ? userDTO.getNombreUsuario() : "Genesys");
            detalle.setFechaHoraUltimaModificacion(new Date());
        }
        //se guardan los nuevos registros de los detalles de subsidios asignados que se crearon por los anulados reemplazados
        consultasCore.crearDetallesSubsidiosAsignados(detallesSubsidiosAsignadosReemplazados);

        logger.warn("estado de la cuenta original 5"+ String.valueOf(cuentaAdministradorSubsidioOrigDTO.getEstadoTransaccion()));

        logger.warn("estado de la cuenta nueva 2"+ String.valueOf(nuevaCuentaAdminSubsidio.getEstadoTransaccion()));

        return idNuevaCuentaAdminSubsidio;
        }

    /**
     * Metodo que se encarga de limpiar los valores relacionados al medio de pago al cual
     * tiene relación la cuenta del administrador del subsidio.
     * @param cuentaAdministradorSubsidioDTO
     *        DTO de la cuenta del administrador del subsidio.
     */
    private void limpiarCamposCuentaCambioMedioDePago(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {
        cuentaAdministradorSubsidioDTO.setCodigoBancoAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setNombreBancoAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setNombreTitularCuentaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setNumeroIdentificacionTitularCuentaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setNumeroCuentaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setNumeroTarjetaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setTipoIdentificacionTitularCuentaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setTipoCuentaAdminSubsidio(null);
        cuentaAdministradorSubsidioDTO.setIdSitioDePago(null);
        cuentaAdministradorSubsidioDTO.setIdSitioDeCobro(null);
    }

    /**
     * Metodo encargado de realizar el resgitro de la transacción de retiro, sea porque es un retiro
     * completo o uno parcial.
     * HU-31-218
     * 
     * @param listaCuentaAdminSubsidio
     *        lista de cuentas del administrador del subsidio por los cuales se realizo el retiro.
     * @param saldoActualSubsidio
     *        valor del saldo actual que tiene el administrador de subsidios.
     * @param valorSolicitado
     *        valor solicitado a retirar.
     * @param fecha
     *        fecha por la cual ocurre
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercer pagador que envían por el servicio previo a este llamado.
     * @param municipio
     *        código indicado por el DANE del municipio del sitio de pago
     * @param usuario
     *        usuario que realizo el retiro
     * @param identificadorRespuesta
     *        identificador de respuesta de la creación del registro de operaciones de transacciones.
     * @param idPersonaAutorizada
     *        identificador de la base de datos de la persona autorizada para cobrar el subsidio por parte del administrador del subsidio
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long registrarTransaccionRetiro(List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio, BigDecimal saldoActualSubsidio,
                                            BigDecimal valorSolicitado, Long fecha, String idTransaccionTercerPagador, String municipio, String usuario,
                                            String identificadorRespuesta, EstadoTransaccionSubsidioEnum estadoTransaccionSubsidioEnum, String idPuntoCobro,String user) {
        //IMPORTANTE - estadoTransaccionSubsidioEnum
        //COBRADO - En el caso que sea una sola transaccion donde se haga el retiro sin necesidad de hacer la confirmacion del valor entregado
        //SOLICITADO - En el caso que se deban hacer dos transaccion, el retiro y confirmar el valor entregado
        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarTransaccionRetiro()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaParametro = new Date(fecha);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fec = formato.format(fechaParametro);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(fec, formatter);

        Date fechaActual = java.sql.Timestamp.valueOf(formatDateTime);

        Long idRetiro = null;
        Integer cont = 0;

        if (listaCuentaAdminSubsidio.isEmpty()) {

            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error al registrar la transacción. No hay abonos por parte del administrador de subsidio.s");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        //identificador del administrador de subsidio del abono
        Long idAdminSubsidio = listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio();
        //sitio de pago consultado por el codigo del municipio
        Long idSitioDePago = consultasCore.consultarSitioDePago(municipio);
        //se valida que este asociado un id de sitio de pago
        if (idSitioDePago == null) {
            //si es nulo, se retorna un menos uno para proceder y enviar el mensaje de error.
            return -1L;
        }
        List<Long> listId = new ArrayList<>();
        List<CuentaAdministradorSubsidio> cuentasPrevias = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio){
            listId.add(cuenta.getIdCuentaAdministradorSubsidio());
            cuentasPrevias.add(cuenta.convertToEntity());
        }
        String nombreConvenio = consultasCore.consultarNombreTercerPagadorConvenio(usuario);


            cont++;
            logger.info("no debe imprimir :c ------");
            //si el valor solicitado es igual al actual se realiza un retiro completo 3.1.1
            if (saldoActualSubsidio.compareTo(valorSolicitado) == 0) {

                //Paso 2: se crea una cuenta de administrador subsidio referente al retiro
                Long idNuevaCuenta = registrarCuentarRetiro(user != null ? user : usuario, TipoMedioDePagoEnum.EFECTIVO,
                        listaCuentaAdminSubsidio.get(0).getIdMedioDePago(), valorSolicitado, idAdminSubsidio, fechaActual, idSitioDePago,
                        identificadorRespuesta, nombreConvenio, "", idTransaccionTercerPagador, null,
                        listaCuentaAdminSubsidio.get(0).getIdEmpleador(), listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal(), listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle(),
                        listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar(), listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio(), idPuntoCobro);

                logger.info("idNuevaCuenta -----> " + idNuevaCuenta);

                if(idNuevaCuenta !=0){

                    for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {

                        if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                            //Paso 2.1: se asocian los abono modificados con el creado del tipo "Retiro"
                            cuenta.setFechaHoraUltimaModificacion(new Date());
                            cuenta.setUsuarioUltimaModificacion(user != null ? user : usuario);
                            cuenta.setIdCuentaAdminSubsidioRelacionado(idNuevaCuenta);

                            //Paso 1: se actualiza cada registro de tipo transaccion abono de estado aplicado a Solicitado.
                            cuenta.setEstadoTransaccion(estadoTransaccionSubsidioEnum);

                            consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                            List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                    .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                            //Paso 3: se modifican los registros de los detalles del abono asociado
                            for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                                detalle.setFechaTransaccionRetiro(new Date());
                                detalle.setUsuarioTransaccionRetiro(user != null ? user : usuario);
                                detalle.setFechaHoraUltimaModificacion(new Date());
                                detalle.setUsuarioUltimaModificacion(user != null ? user : usuario);

                                consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                            }

                        }
                    }
                    //se obtiene la cuenta del retiro para actualizarse
                    CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmonSubsidioDTO(idNuevaCuenta);
                    //Se actualiza la transacción del retiro que se registro
                    cuentaAdministradorSubsidioDTO.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
                    cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                    cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(user != null ? user : usuario);
                    cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf("EN_PROCESO_RETIRO"));
                    consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                }
                //se asocia el id del retiro
                idRetiro = idNuevaCuenta;

            } else if (saldoActualSubsidio.compareTo(valorSolicitado) > 0) {

                //se realiza un retiro parcial si el valor solicitado es menor al saldo actual
                //Paso 2: Se crea el registro de transacción "Retiro"
                CuentaAdministradorSubsidioDTO cuentaTipoRetiro = new CuentaAdministradorSubsidioDTO();

                cuentaTipoRetiro.setUsuarioCreacionRegistro(user != null ? user : usuario);
                cuentaTipoRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.RETIRO);
                cuentaTipoRetiro.setMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
                cuentaTipoRetiro.setIdMedioDePago(listaCuentaAdminSubsidio.get(0).getIdMedioDePago());
                cuentaTipoRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
                cuentaTipoRetiro.setValorOriginalTransaccion(valorSolicitado.negate());
                cuentaTipoRetiro.setIdAdministradorSubsidio(idAdminSubsidio);
                cuentaTipoRetiro.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
                cuentaTipoRetiro.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
                cuentaTipoRetiro.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
                cuentaTipoRetiro.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
                cuentaTipoRetiro.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
                cuentaTipoRetiro.setFechaHoraTransaccion(fechaActual);
                cuentaTipoRetiro.setUsuarioTransaccionLiquidacion(user != null ? user : usuario);
                cuentaTipoRetiro.setValorRealTransaccion(cuentaTipoRetiro.getValorOriginalTransaccion());
                cuentaTipoRetiro.setFechaHoraUltimaModificacion(new Date());
                cuentaTipoRetiro.setUsuarioUltimaModificacion(user != null ? user : usuario);
                cuentaTipoRetiro.setIdSitioDeCobro(idSitioDePago);
                cuentaTipoRetiro.setIdRemisionDatosTerceroPagador(identificadorRespuesta);
                cuentaTipoRetiro.setNombreTerceroPagador(nombreConvenio);
                cuentaTipoRetiro.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
                cuentaTipoRetiro.setIdPuntoDeCobro(idPuntoCobro);
                cuentaTipoRetiro.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf("EN_PROCESO_RETIRO"));
                //se crea la cuenta de Retiro
                Long idCuentaRetiro = consultasCore.crearCuentaAdministradorSubsidio(cuentaTipoRetiro);
                logger.info("idCuentaRetiro -----> " + idCuentaRetiro);

                if(idCuentaRetiro !=0) {

                    //variable C = valor del retiro que está pendiente por aplicar( inicialmente es igual a la variable a(valor solicitado) pero con signo negativo)
                    double valorC = valorSolicitado.negate().doubleValue();

                    //Paso 1: Se actualizan los registros con tipo transacción "Abono" que fueron utilizados para calcular el saldo.
                    for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {
                        if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                            //C = C+B
                            valorC += cuenta.getValorRealTransaccion().doubleValue();

                            //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                            cuenta.setFechaHoraUltimaModificacion(new Date());
                            cuenta.setUsuarioUltimaModificacion(user != null ? user : usuario);
                            cuenta.setIdCuentaAdminSubsidioRelacionado(idCuentaRetiro);

                            //Casos:  C<0
                            if (valorC < 0) {

                                cuenta.setEstadoTransaccion(estadoTransaccionSubsidioEnum);
                                cuenta.setFechaHoraUltimaModificacion(new Date());
                                cuenta.setUsuarioUltimaModificacion(user != null ? user : usuario);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                                List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                        .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                                //se modifican los registros de los detalles del abono asociado
                                for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                                    detalle.setFechaTransaccionRetiro(new Date());
                                    detalle.setUsuarioTransaccionRetiro(user != null ? user : usuario);
                                    detalle.setFechaHoraUltimaModificacion(new Date());
                                    detalle.setUsuarioUltimaModificacion(user != null ? user : usuario);

                                    consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                                }

                            } else if (valorC == 0) { //Caso C=0

                                cuenta.setEstadoTransaccion(estadoTransaccionSubsidioEnum);
                                cuenta.setFechaHoraUltimaModificacion(new Date());
                                cuenta.setUsuarioUltimaModificacion(user != null ? user : usuario);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                                List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                        .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                                //se modifican los registros de los detalles del abono asociado
                                for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                                    detalle.setFechaTransaccionRetiro(new Date());
                                    detalle.setUsuarioTransaccionRetiro(user != null ? user : usuario);
                                    detalle.setFechaHoraUltimaModificacion(new Date());
                                    detalle.setUsuarioUltimaModificacion(user != null ? user : usuario);

                                    consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                                }

                                break;
                            } else { //Caso: C>0

                                logger.info("ingresa al retiro parcial----->");
                                double valorReal = cuenta.getValorOriginalTransaccion().doubleValue() - valorC;
                                //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                                cuenta.setEstadoTransaccion(estadoTransaccionSubsidioEnum);
                                cuenta.setValorRealTransaccion(BigDecimal.valueOf(valorReal));
                                cuenta.setIdSitioDeCobro(idSitioDePago);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                                //Se crea el registro “Nuevo abono originado por fraccionamiento”
                                CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();

                                cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
                                cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(user != null ? user : usuario);
                                cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                                cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
                                cuentaAdministradorSubsidioDTO.setMedioDePago(cuenta.getMedioDePago());
                                cuentaAdministradorSubsidioDTO.setIdMedioDePago(cuenta.getIdMedioDePago());
                                cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO_PARCIAL);
                                cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(BigDecimal.valueOf(valorC));
                                cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(idAdminSubsidio);
                                cuentaAdministradorSubsidioDTO.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
                                cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
                                cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
                                cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
                                cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
                                cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(cuenta.getFechaHoraTransaccion());
                                cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(cuenta.getUsuarioTransaccionLiquidacion());
                                cuentaAdministradorSubsidioDTO.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
                                cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                                cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(user != null ? user : usuario);
                                cuentaAdministradorSubsidioDTO.setIdSitioDePago(cuenta.getIdSitioDePago());
                                cuentaAdministradorSubsidioDTO.setIdTransaccionOriginal(cuenta.getIdCuentaAdministradorSubsidio());
                                cuentaAdministradorSubsidioDTO.setIdPuntoDeCobro(cuenta.getIdPuntoDeCobro());

                                //se crea el abono originado por fraccionamiento
                                Long idAbonoOriginadoFraccionamiento = consultasCore
                                        .crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                                //Paso 4: se ajustan los detalles del "abono fraccionado"
                                fraccionarRegistrosDetallesAbono(cuenta.getIdCuentaAdministradorSubsidio(), cuenta.getValorRealTransaccion(),
                                        user != null ? user : usuario, idAbonoOriginadoFraccionamiento);
                                //se actualiza el nuevo abono como enviado
                                cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                                cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                                //se actualiza el nuevo abono como aplicado
                                cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                                break;
                            }

                        }
                    }
                }
                //se asocia el id del retiro.
                idRetiro = idCuentaRetiro;

            }

        return idRetiro;

    }
    
    
    
    /**
     * Metodo encargado de realizar el resgitro de la transacción de retiro, sea porque es un retiro
     * completo o uno parcial.
     * HU-31-218
     * 
     * @param listaCuentaAdminSubsidio
     *        lista de cuentas del administrador del subsidio por los cuales se realizo el retiro.
     * @param saldoActualSubsidio
     *        valor del saldo actual que tiene el administrador de subsidios.
     * @param valorSolicitado
     *        valor solicitado a retirar.
     * @param fecha
     *        fecha por la cual ocurre
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercer pagador que envían por el servicio previo a este llamado.
     * @param municipio
     *        código indicado por el DANE del municipio del sitio de pago
     * @param usuario
     *        usuario que realizo el retiro
     * @param identificadorRespuesta
     *        identificador de respuesta de la creación del registro de operaciones de transacciones.
     * @param idPersonaAutorizada
     *        identificador de la base de datos de la persona autorizada para cobrar el subsidio por parte del administrador del subsidio
     */
    private Long registrarTransaccionRetiro(List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio, BigDecimal saldoActualSubsidio,
    BigDecimal valorSolicitado, Long fecha, String idTransaccionTercerPagador, Long idSitioPago, String usuario,
    String identificadorRespuesta, Long idPersonaAutorizada, String idPuntoCobro) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarTransaccionRetiro()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaParametro = new Date(fecha);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fec = formato.format(fechaParametro);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(fec, formatter);

        Date fechaActual = java.sql.Timestamp.valueOf(formatDateTime);

        Long idRetiro = null;

        if (listaCuentaAdminSubsidio.isEmpty()) {

            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error al registrar la transacción. No hay abonos por parte del administrador de subsidio.s");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        //identificador del administrador de subsidio del abono
        Long idAdminSubsidio = listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio();
        //sitio de pago consultado por el id del administrador y el municipio
        Long idSitioDePago = idSitioPago;
        //se valida que este asociado un id de sitio de pago
        if (idSitioDePago == null) {
            //si es nulo, se retorna un menos uno para proceder y enviar el mensaje de error.
            return -1L;
        }

        String nombreConvenio = consultasCore.consultarNombreTercerPagadorConvenio(usuario);

        //si el valor solicitado es igual al actual se realiza un retiro completo 3.1.1
        if (saldoActualSubsidio.compareTo(valorSolicitado) == 0) {

            //Paso 2: se crea una cuenta de administrador subsidio referente al retiro
            Long idNuevaCuenta = registrarCuentarRetiro(usuario, TipoMedioDePagoEnum.EFECTIVO,
                    listaCuentaAdminSubsidio.get(0).getIdMedioDePago(), valorSolicitado, idAdminSubsidio, fechaActual, idSitioDePago,
                    identificadorRespuesta, nombreConvenio, "", idTransaccionTercerPagador, null,
                    listaCuentaAdminSubsidio.get(0).getIdEmpleador(),listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal(),listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle(),
                    listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar(),listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio(), !idPuntoCobro.equals("0") ? idPuntoCobro : null);

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {

                if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                    //Paso 2.1: se asocian los abono modificados con el creado del tipo "Retiro"
                    cuenta.setFechaHoraUltimaModificacion(new Date());
                    cuenta.setUsuarioUltimaModificacion(usuario);
                    cuenta.setIdCuentaAdminSubsidioRelacionado(idNuevaCuenta);

                    //Paso 1: se actualiza cada registro de tipo transaccion abono de estado aplicado a Solicitado.
                    cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);

                    consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                    List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                            .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                    //Paso 3: se modifican los registros de los detalles del abono asociado
                    for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                        detalle.setFechaTransaccionRetiro(new Date());
                        detalle.setUsuarioTransaccionRetiro(usuario);
                        detalle.setFechaHoraUltimaModificacion(new Date());
                        detalle.setUsuarioUltimaModificacion(usuario);

                        consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                    }

                }
            }
            //se obtiene la cuenta del retiro para actualizarse
            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmonSubsidioDTO(idNuevaCuenta);
            //Se actualiza la transacción del retiro que se registro
            cuentaAdministradorSubsidioDTO.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
            cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
            cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(usuario);

            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

            //se asocia el id del retiro
            idRetiro = idNuevaCuenta;

        }
        else if (saldoActualSubsidio.compareTo(valorSolicitado) > 0) {
            //se realiza un retiro parcial si el valor solicitado es menor al saldo actual

            //Paso 2: Se crea el registro de transacción "Retiro"
            CuentaAdministradorSubsidioDTO cuentaTipoRetiro = new CuentaAdministradorSubsidioDTO();

            cuentaTipoRetiro.setUsuarioCreacionRegistro(usuario);
            cuentaTipoRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.RETIRO);
            cuentaTipoRetiro.setMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
            cuentaTipoRetiro.setIdMedioDePago(listaCuentaAdminSubsidio.get(0).getIdMedioDePago());
            cuentaTipoRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
            cuentaTipoRetiro.setValorOriginalTransaccion(valorSolicitado.negate());
            cuentaTipoRetiro.setIdAdministradorSubsidio(idAdminSubsidio);
            cuentaTipoRetiro.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
            cuentaTipoRetiro.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
            cuentaTipoRetiro.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
            cuentaTipoRetiro.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
            cuentaTipoRetiro.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
            cuentaTipoRetiro.setFechaHoraTransaccion(fechaActual);
            cuentaTipoRetiro.setUsuarioTransaccionLiquidacion(usuario);
            cuentaTipoRetiro.setValorRealTransaccion(cuentaTipoRetiro.getValorOriginalTransaccion());
            cuentaTipoRetiro.setFechaHoraUltimaModificacion(new Date());
            cuentaTipoRetiro.setUsuarioUltimaModificacion(usuario);
            cuentaTipoRetiro.setIdSitioDeCobro(idSitioDePago);
            cuentaTipoRetiro.setIdRemisionDatosTerceroPagador(identificadorRespuesta);
            cuentaTipoRetiro.setNombreTerceroPagador(nombreConvenio);
            cuentaTipoRetiro.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
            cuentaTipoRetiro.setIdPuntoDeCobro(!idPuntoCobro.equals("0") ? idPuntoCobro : null);

            //se crea la cuenta de Retiro
            Long idCuentaRetiro = consultasCore.crearCuentaAdministradorSubsidio(cuentaTipoRetiro);

            //variable C = valor del retiro que está pendiente por aplicar( inicialmente es igual a la variable a(valor solicitado) pero con signo negativo)
            double valorC = valorSolicitado.negate().doubleValue();

            //Paso 1: Se actualizan los registros con tipo transacción "Abono" que fueron utilizados para calcular el saldo.
            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {

                if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                    //C = C+B
                    valorC += cuenta.getValorRealTransaccion().doubleValue();

                    //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                    cuenta.setFechaHoraUltimaModificacion(new Date());
                    cuenta.setUsuarioUltimaModificacion(usuario);
                    cuenta.setIdCuentaAdminSubsidioRelacionado(idCuentaRetiro);

                    //Casos:  C<0  
                    if (valorC < 0) {

                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(usuario);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                        
                        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                        //se modifican los registros de los detalles del abono asociado
                        for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                            detalle.setFechaTransaccionRetiro(new Date());
                            detalle.setUsuarioTransaccionRetiro(usuario);
                            detalle.setFechaHoraUltimaModificacion(new Date());
                            detalle.setUsuarioUltimaModificacion(usuario);

                            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                        }

                    }
                    else if (valorC == 0) { //Caso C=0
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(usuario);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                        
                        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                        //se modifican los registros de los detalles del abono asociado
                        for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                            detalle.setFechaTransaccionRetiro(new Date());
                            detalle.setUsuarioTransaccionRetiro(usuario);
                            detalle.setFechaHoraUltimaModificacion(new Date());
                            detalle.setUsuarioUltimaModificacion(usuario);

                            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                        }

                        break;
                    }
                    else { //Caso: C>0

                        double valorReal = cuenta.getValorOriginalTransaccion().doubleValue() - valorC;
                        //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setValorRealTransaccion(BigDecimal.valueOf(valorReal));
                        cuenta.setIdSitioDeCobro(idSitioDePago);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                        //Se crea el registro “Nuevo abono originado por fraccionamiento”
                        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();

                        cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
                        cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(usuario);
                        cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
                        cuentaAdministradorSubsidioDTO.setMedioDePago(cuenta.getMedioDePago());
                        cuentaAdministradorSubsidioDTO.setIdMedioDePago(cuenta.getIdMedioDePago());
                        cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO_PARCIAL);
                        cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(BigDecimal.valueOf(valorC));
                        cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(idAdminSubsidio);
                        cuentaAdministradorSubsidioDTO.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
                        cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
                        cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
                        cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
                        cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
                        cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(cuenta.getFechaHoraTransaccion());
                        cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(usuario);
                        cuentaAdministradorSubsidioDTO
                                .setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
                        cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                        cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(usuario);
                        cuentaAdministradorSubsidioDTO.setIdSitioDePago(cuenta.getIdSitioDePago());
                        cuentaAdministradorSubsidioDTO.setIdTransaccionOriginal(cuenta.getIdCuentaAdministradorSubsidio());
                        cuentaAdministradorSubsidioDTO.setIdPuntoDeCobro(!idPuntoCobro.equals("0") ? idPuntoCobro : null);

                        //se crea el abono originado por fraccionamiento
                        Long idAbonoOriginadoFraccionamiento = consultasCore
                                .crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        //Paso 4: se ajustan los detalles del "abono fraccionado"
                        fraccionarRegistrosDetallesAbono(cuenta.getIdCuentaAdministradorSubsidio(), cuenta.getValorRealTransaccion(),
                                usuario, idAbonoOriginadoFraccionamiento);

                        //se actualiza el nuevo abono como enviado
                        cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        //se actualiza el nuevo abono como aplicado
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        break;
                    }

                }
            }
            //se asocia el id del retiro.
            idRetiro = idCuentaRetiro;

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idRetiro;
    }

    /**
     * Metodo encargado de fraccionar los detalles de subsidios asignados que no son necesarios para el cobro
     * del abono y relacionado con el remante de el mismo.
     * 
     * @param idCuentaAbono
     *        <code>Long</code>
     *        Identificador principal del abono fraccionado.
     * @param valorRealTransaccion
     *        <code>BigDecimal</code>
     *        Valor real de la transacción del abono fraccionado
     * @param usuario
     *        <code>String</code>
     *        Usuario que realiza la transacción
     * @param idAbonoOriginadoFraccionamiento
     *        <code>Long</code>
     *        Identificador del abono que se creo por el fraccionamiento (cuenta del dinero remanente)
     */
    private void fraccionarRegistrosDetallesAbonoRetiroIncompleto(Long idCuentaAbono, BigDecimal valorRealTransaccion, String usuario,
            Long idAbonoOriginadoFraccionamiento) {

        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                .consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAbono);

        //F: valor del abono fraccionado, sera igual al de la variable D (valorRealTransaccion)
        double valorF = valorRealTransaccion.doubleValue();

        int i = 0;
        boolean bandera = false;

        for (; i < listaDetallesAbono.size(); i++) {

            //F = F-E siendo E=valorTotal del detalle
            valorF -= listaDetallesAbono.get(i).getValorTotal().doubleValue();

            listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
            listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
            consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

            if (valorF == 0) { //Caso F=0

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }
            else if (valorF < 0) {// Caso F<0

                BigDecimal valorTotal = listaDetallesAbono.get(i).getValorTotal();
                listaDetallesAbono.get(i).setValorTotal(valorTotal.add(BigDecimal.valueOf(valorF)));
                listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
                listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

                DetalleSubsidioAsignadoDTO nuevoDetalleOriginadoFraccionamiento = listaDetallesAbono.get(i).clone();

                nuevoDetalleOriginadoFraccionamiento.setIdDetalleSubsidioAsignado(null);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraCreacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioCreador(usuario);
                nuevoDetalleOriginadoFraccionamiento.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
                nuevoDetalleOriginadoFraccionamiento.setValorTotal(BigDecimal.valueOf(Math.abs(valorF)));
                nuevoDetalleOriginadoFraccionamiento.setIdRegistroOriginalRelacionado(listaDetallesAbono.get(i).getIdDetalleSubsidioAsignado());
                nuevoDetalleOriginadoFraccionamiento.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraUltimaModificacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioUltimaModificacion(usuario);
                nuevoDetalleOriginadoFraccionamiento.setFechaTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setUsuarioTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.RETIRO_PARCIAL);

                List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();
                detalles.add(nuevoDetalleOriginadoFraccionamiento);

                consultasCore.crearDetallesSubsidiosAsignados(detalles);

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }

        }

        if (bandera) {

            for (int j = i + 1; j < listaDetallesAbono.size(); j++) {
                //se asocia al nuevo abono y se actualiza
                listaDetallesAbono.get(j).setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                listaDetallesAbono.get(j).setUsuarioTransaccionRetiro(null);
                listaDetallesAbono.get(j).setFechaTransaccionRetiro(null);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(j));
            }
        }
    }

    /**
     * 
     * Metodo encargado de analizar los registros de detalle subsidio asignado de un abono fraccionado para determinar
     * si es necesario fraccionar.
     * 
     * @param idCuentaAbono
     *        identificador de la llave primaria de la base de datos del abono (cuenta) fraccionado.
     * @param valorRealTransaccion
     *        valor real de la transacción del abono fraccionado.
     * @param usuario
     *        usuario que realizo el retiro.
     * @param idAbonoOriginadoFraccionamiento
     *        identificador de la llave primaria de la base de datos del abono fraccionado que se creo.
     */
    private void fraccionarRegistrosDetallesAbono(Long idCuentaAbono, BigDecimal valorRealTransaccion, String usuario,
            Long idAbonoOriginadoFraccionamiento) {

        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                .consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAbono);

        //F: valor del abono fraccionado, sera igual al de la variable D (valorRealTransaccion)
        double valorF = valorRealTransaccion.doubleValue();

        int i = 0;
        boolean bandera = false;

        for (; i < listaDetallesAbono.size(); i++) {

            //F = F-E siendo E=valorTotal del detalle
            valorF -= listaDetallesAbono.get(i).getValorTotal().doubleValue();

            listaDetallesAbono.get(i).setFechaTransaccionRetiro(new Date());
            listaDetallesAbono.get(i).setUsuarioTransaccionRetiro(usuario);
            listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
            listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
            consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

            if (valorF == 0) { //Caso F=0

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }
            else if (valorF < 0) {// Caso F<0

                BigDecimal valorTotal = listaDetallesAbono.get(i).getValorTotal();
                listaDetallesAbono.get(i).setFechaTransaccionRetiro(new Date());
                listaDetallesAbono.get(i).setValorTotal(valorTotal.add(BigDecimal.valueOf(valorF)));
                listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
                listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
                listaDetallesAbono.get(i).setUsuarioTransaccionRetiro(usuario);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

                DetalleSubsidioAsignadoDTO nuevoDetalleOriginadoFraccionamiento = listaDetallesAbono.get(i).clone();

                nuevoDetalleOriginadoFraccionamiento.setIdDetalleSubsidioAsignado(null);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraCreacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioCreador(usuario);
                nuevoDetalleOriginadoFraccionamiento.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
                nuevoDetalleOriginadoFraccionamiento.setValorTotal(BigDecimal.valueOf(Math.abs(valorF)));
                nuevoDetalleOriginadoFraccionamiento
                        .setIdRegistroOriginalRelacionado(listaDetallesAbono.get(i).getIdDetalleSubsidioAsignado());
                nuevoDetalleOriginadoFraccionamiento.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraUltimaModificacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioUltimaModificacion(usuario);
                nuevoDetalleOriginadoFraccionamiento.setFechaTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setUsuarioTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.RETIRO_PARCIAL);
                nuevoDetalleOriginadoFraccionamiento.setNombreTerceroPagador(listaDetallesAbono.get(i).getNombreTerceroPagador());

                //Ajuste GLPI 93890
                nuevoDetalleOriginadoFraccionamiento.setValorDescuento(BigDecimal.ZERO);
                
                List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();
                detalles.add(nuevoDetalleOriginadoFraccionamiento);

                consultasCore.crearDetallesSubsidiosAsignados(detalles);

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }

        }

        if (bandera) {
        
            for (int j = i + 1; j < listaDetallesAbono.size(); j++) {
                //se asocia al nuevo abono y se actualiza
                listaDetallesAbono.get(j).setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(j));
            }
        }
    }
    /**
     * 
     * Metodo encargado de analizar los registros de detalle subsidio asignado de un abono fraccionado para determinar
     * si es necesario fraccionar.
     * 
     * @param idCuentaAbono
     *        identificador de la llave primaria de la base de datos del abono (cuenta) fraccionado.
     * @param valorRealTransaccion
     *        valor real de la transacción del abono fraccionado.
     * @param usuario
     *        usuario que realizo el retiro.
     * @param idAbonoOriginadoFraccionamiento
     *        identificador de la llave primaria de la base de datos del abono fraccionado que se creo.
     */
    private void fraccionarRegistrosDetallesAbonoEfectivo(Long idCuentaAbono, BigDecimal valorRealTransaccion, String usuario,
            Long idAbonoOriginadoFraccionamiento,Date fechaArchivo,String usuarioConvenio) {

        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                .consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAbono);

        //F: valor del abono fraccionado, sera igual al de la variable D (valorRealTransaccion)
        double valorF = valorRealTransaccion.doubleValue();

        int i = 0;
        boolean bandera = false;

        for (; i < listaDetallesAbono.size(); i++) {

            //F = F-E siendo E=valorTotal del detalle
            valorF -= listaDetallesAbono.get(i).getValorTotal().doubleValue();

            listaDetallesAbono.get(i).setFechaTransaccionRetiro(fechaArchivo);
            listaDetallesAbono.get(i).setUsuarioTransaccionRetiro(usuarioConvenio);
            listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
            listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
            consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

            if (valorF == 0) { //Caso F=0

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }
            else if (valorF < 0) {// Caso F<0

                BigDecimal valorTotal = listaDetallesAbono.get(i).getValorTotal();
                listaDetallesAbono.get(i).setFechaTransaccionRetiro(fechaArchivo);
                listaDetallesAbono.get(i).setValorTotal(valorTotal.add(BigDecimal.valueOf(valorF)));
                listaDetallesAbono.get(i).setFechaHoraUltimaModificacion(new Date());
                listaDetallesAbono.get(i).setUsuarioUltimaModificacion(usuario);
                listaDetallesAbono.get(i).setUsuarioTransaccionRetiro(usuarioConvenio);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(i));

                DetalleSubsidioAsignadoDTO nuevoDetalleOriginadoFraccionamiento = listaDetallesAbono.get(i).clone();

                nuevoDetalleOriginadoFraccionamiento.setIdDetalleSubsidioAsignado(null);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraCreacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioCreador(usuario);
                nuevoDetalleOriginadoFraccionamiento.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
                nuevoDetalleOriginadoFraccionamiento.setValorTotal(BigDecimal.valueOf(Math.abs(valorF)));
                nuevoDetalleOriginadoFraccionamiento
                        .setIdRegistroOriginalRelacionado(listaDetallesAbono.get(i).getIdDetalleSubsidioAsignado());
                nuevoDetalleOriginadoFraccionamiento.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                nuevoDetalleOriginadoFraccionamiento.setFechaHoraUltimaModificacion(new Date());
                nuevoDetalleOriginadoFraccionamiento.setUsuarioUltimaModificacion(usuario);
                nuevoDetalleOriginadoFraccionamiento.setFechaTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setUsuarioTransaccionRetiro(null);
                nuevoDetalleOriginadoFraccionamiento.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.RETIRO_PARCIAL);

                //Ajuste GLPI 93890
                nuevoDetalleOriginadoFraccionamiento.setValorDescuento(BigDecimal.ZERO); 
                
                List<DetalleSubsidioAsignadoDTO> detalles = new ArrayList<>();
                detalles.add(nuevoDetalleOriginadoFraccionamiento);

                consultasCore.crearDetallesSubsidiosAsignados(detalles);

                //si hay mas detalles asociados a la cuenta que no se estan tendiendo en cuenta, se notifica.
                if (i < listaDetallesAbono.size() - 1) {
                    bandera = true;
                }

                break;
            }

        }
        
        if (bandera) {
            
            for (int j = i + 1; j < listaDetallesAbono.size(); j++) {
                //se asocia al nuevo abono y se actualiza
                listaDetallesAbono.get(j).setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                consultasCore.actualizarDetalleSubsidioAsignado(listaDetallesAbono.get(j));
            }
        }
    }

    /**
     * Metodo que obtiene el número de días parametrizados para la anulación de un subsidio monetarios
     * por fecha de vencimiento en una CCF.
     * @return número de días
     */
    private String obtenerNumeroDiasParametrizadosFechaVencimiento() {

        String diasParametrizados = null;
        try {
            diasParametrizados = (CacheManager.getParametro(ParametrosSistemaConstants.PARAM_DIAS_VENCIMIENTO_SUBSIDIO_MONETARIO_CCF)
                    .toString());
        } catch (Exception e) {
            logger.debug("Error al obtener el número de días parametrizados para la anulación por fecha de vencimiento");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        return diasParametrizados;
    }

    /**
     * Metodo que obtiene el número de días parametrizados para la anulación de un subsidio monetarios
     * por prescripción en una CCF.
     * @return número de días
     */
    private String obtenerNumeroDiasParametrizadosPrescripcion() {

        String diasParametrizados = null;
        try {
            diasParametrizados = (CacheManager.getParametro(ParametrosSistemaConstants.PARAM_DIAS_PRESCRIPCION_SUBSIDIO_MONETARIO_CCF)
                    .toString());
        } catch (Exception e) {
            logger.debug("Error al obtener el número de días parametrizados para la anulación por prescripción");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        return diasParametrizados;
    }

    /**
     * Metodo encargado de realizar las validaciones al momento de solicitar el retiro de la HU-31-203
     * @param saldoActualSubsidio
     *        Saldo disponible que tiene el administrador del subsidio.
     * @param valorSolicitado
     *        Valor solicitado por parte del administrador del subsidio para el retiro.
     * @param idTransaccionTercerPagador
     *        Identificador del tercero pagador único para solicitar el retiro.
     * @param medioDePago
     *        Medio de pago respectivo por el cual solicita el retiro.
     * @param userDTO
     *        Usuario del sistema que ingresa la solicitud de retiro.
     * @param resultado
     *        Constante para la representación de la llave de los parametros de salida.
     * @param error
     *        Constante para la representación de la llave de los parametros de salida.
     * @param idRespuesta
     *        Constante para la representación de la llave de los parametros de salida.
     * @param parametrosIN
     *        Cadena que representa los parametros de entrada de la transacción
     * @return Un map con la información correspondiente si algo ocurrio mal, o un null si no hay errores.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Map<String, String> validarSolicitudRetiroSubsidio(BigDecimal saldoActualSubsidio, BigDecimal valorSolicitado,
                                                               String idTransaccionTercerPagador, TipoMedioDePagoEnum medioDePago, String user,
                                                               List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio, String resultado, String error, String idRespuesta,
                                                               String parametrosIN, TipoOperacionSubsidioEnum tipoOperacionSubsidio, String usuario, String idPuntoCobro, Integer transaccion)
    {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarSolicitudRetiroSubsidio()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        String url = "metodo-validarSolicitudRetiroSubsidio";
        Date fechaHoraInicio = new Date();

        logger.info("parametrosIN " +parametrosIN);

        Gson gsonConvert = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        logger.info("type " +type);
        Map<String, Object> resultMap = gsonConvert.fromJson(parametrosIN, type);
        logger.info("resultMap " +resultMap);
        String numeroIdentificacion = (String) resultMap.get("numeroIdentificadorAdmin");

        Map<String, String> respuesta = null;

        String parametrosOUT = null;
        Long identificadorRespuesta = null;
        StringBuilder salida = null;
        Gson gson = new GsonBuilder().create();

        //Se valida que el valor a retirar como minimo cero (0)
        if (valorSolicitado.compareTo(BigDecimal.ZERO) < 0) {
            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN,
                    user, null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "El valor solicitado es: " + valorSolicitado + ", el valor minimo permitido es cero (0).");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion valor a retirar como minimo cero (0): " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }
        //Se valida que solo se pueda realizar la operación de retiro por medio de pago efectivo
        else if (!TipoMedioDePagoEnum.EFECTIVO.equals(medioDePago)) {

            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN, user, null);

            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "Solo se puede realizar la operación de " + tipoOperacionSubsidio + " por medio del medio de pago tipo EFECTIVO");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion solo se pueda realizar la operación de retiro por medio de pago efectivo: " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }
        else if (consultasCore.buscarExistenciaRetiroPorIdTransaccionTerceroPagadorRetiro(idTransaccionTercerPagador, usuario, idPuntoCobro)) {
            //se valida que el identificador de transacción del tercero pagador sea unico.
            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN, user, null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "El identificador transacción tercero pagador ya se encuentra registrado");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion identificador de transacción del tercero pagador sea unico: " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }
        else if (listaCuentaAdminSubsidio == null) {
            //se valida que si hayan cuentas asociadas al administrador de subsidio.
            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN, user, null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "No hay abonos relacionados para realizar la transacción de " + tipoOperacionSubsidio);
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion cuentas asociadas al administrador de subsidio: " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }else if(saldoActualSubsidio.compareTo(BigDecimal.valueOf(-1)) == 0){
            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN,
                    user, null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(error, "Se esta realizando una transaccion para el administrador con numero de Identificacion " +numeroIdentificacion+", por favor verifique en unos minutos");

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion hay una transaccion en proceso " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }
        else if (saldoActualSubsidio.compareTo(valorSolicitado) < 0) {
            //si el saldo actual (disponible) es menor al solicitado no se prosigue.
            respuesta = new HashMap<>();
            //se registra la operación
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(tipoOperacionSubsidio, parametrosIN,
                    user, null);
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "El valor solicitado supera el saldo que actualmente tiene el administrador");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos), url);

            logger.debug("Salto validacion saldo actual (disponible) es menor al solicitado: " + respuesta.toString());
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return respuesta;
        }


        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * Metodo encargado de actualizar la información de los detalles de subsidios asignados
     * que estan asociados a una cuenta de administrador del subsidio la cual será
     * @param idCuentaAdministradorSubsidio
     *        <code>Long</code>
     *        Identificador principal de la cuenta del administrador del subsidio.
     */
    private void actualizarDetallesDeAbonosCobradosRetiroParaReversar(Long idCuentaAdministradorSubsidio, String usuarioGenesys) {

        List<DetalleSubsidioAsignadoDTO> listaDetalles = consultasCore
                .consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAdministradorSubsidio);

        for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
            detalle.setFechaTransaccionRetiro(null);
            detalle.setUsuarioTransaccionRetiro(null);
            detalle.setFechaHoraUltimaModificacion(new Date());
            detalle.setUsuarioUltimaModificacion(usuarioGenesys);
            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
        }
    }

    /**
     * Método encargado de buscar la cuenta del administrador del subsidio que tiene relación
     * con el retiro de tarjeta registrado en el archivo de consumo de ANIBOL y comparar los valores
     * si el saldo de la cuenta es igual o mayor del registro de tarjeta, se realiza el retiro; sino,
     * la cuenta o cuentas serán gestionadas a transacciones fallidas
     * HU-31-211
     * @param listaCandidatos
     *        <code>List<TarjetaRetiroCandidatoDTO></code>
     *        Lista de candidatos de registros de tarjetas a ser consiliadas con
     *        los registros de las cuentas.
     * @return Lista de registros de tarjetas que no cumplen con la condición: saldo mayor o igual a los asociados en la cuenta.
     */
    private List<TarjetaRetiroCandidatoDTO> compararRegistrosTarjetasCuentaAdminSubsidio(List<TarjetaRetiroCandidatoDTO> listaCandidatos) {

        List<TarjetaRetiroCandidatoDTO> tarjetasSaldoInsuficiente = new ArrayList<>();

        for (TarjetaRetiroCandidatoDTO taRetiroCandidatoDTO : listaCandidatos) {

            //se buscan las cuentas asociadas al registro de retiro de tarjeta, desde el registro más antiguo  DESDE EL REGISTRO MÁS ANTIGUO AL MÁS NUEVO
            List<CuentaAdministradorSubsidioDTO> listaCuentasTarjetas = consultasCore
                    .consultarCuentasAdminSubsidioAsociadasRegistroTarjeta(taRetiroCandidatoDTO.getNumeroTarjeta());

            if (listaCuentasTarjetas != null) {
                //se calcula el saldo disponible que se tiene asociada para esa tarjeta en los registros de la cuenta
                double valorDisponible = 0;
                for (CuentaAdministradorSubsidioDTO cuentaAsociada : listaCuentasTarjetas) {
                    valorDisponible += cuentaAsociada.getValorRealTransaccion().doubleValue();
                }
                //si el saldo de la cuenta es menor se registra una transacción fallida y se guarda el registro de la tarjeta
                if (BigDecimal.valueOf(valorDisponible).compareTo(taRetiroCandidatoDTO.getValorTransaccion()) < 0) {

                    for (CuentaAdministradorSubsidioDTO cuentaAsociada : listaCuentasTarjetas) {

                        TransaccionFallidaDTO transaccionFallidaDTO = new TransaccionFallidaDTO();
                        transaccionFallidaDTO.setIdCuentaAdmonSubsidio(cuentaAsociada.getIdCuentaAdministradorSubsidio());
                        transaccionFallidaDTO.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.REGISTRO_TRANSACCION_RETIRO);
                        //se agrega la transacción fallida de cada cuenta relacionada para el calculo del saldo
                        gestionarTransaccionesFallidas(transaccionFallidaDTO);
                    }
                    //se almacena el registro de tarjeta que no será procesada por saldo insuficiente en la cuenta
                    taRetiroCandidatoDTO.setTipoInconsistenciaResultadoValidacion(
                            InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum.NO_CONCILIADO_SALDO_INSUFICIENTE);
                    taRetiroCandidatoDTO.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
                    tarjetasSaldoInsuficiente.add(taRetiroCandidatoDTO);
                }
                else {
                    //Si el saldo es mayor o igual, se realiza el retiro y se cambia el estado del registro de la tarjeta como conciliado
                    taRetiroCandidatoDTO.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.CONCILIADO);
                    registrarRetiroTarjetaANIBOL(taRetiroCandidatoDTO, BigDecimal.valueOf(valorDisponible), listaCuentasTarjetas);
                }
            }
            else {
                //se almacenan los registros que no tienen cuentas de admininistradores de subsidio asociadas con el número de tarjeta
                taRetiroCandidatoDTO.setTipoInconsistenciaResultadoValidacion(
                        InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum.TARJETA_NO_REGISTRADA);
                taRetiroCandidatoDTO.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
                tarjetasSaldoInsuficiente.add(taRetiroCandidatoDTO);
            }
        }
        return tarjetasSaldoInsuficiente;
    }

    /**
     * Método encargado de realizar el retiro sea completo o incompleto de tarjetas, a partir de los registros
     * almacenados y son candidatos en el archivo de consumo entregado por ANIBOL.
     * HU-31-ANEXO.
     * @param tarjetaRetiro
     *        <code>TarjetaRetiroCandidatoDTO</code>
     *        Tarjeta a la cual se le realiza el proceso de retiro
     * @param valorDisponible
     *        <code>BigDecimal</code>
     *        Valor disponible que tiene la tarjeta asociada en las cuentas de administradores de subsidio.
     * @param listaCuentasTarjetas
     *        <code>List<CuentaAdministradorSubsidioDTO></code>
     *        Lista de las cuentas en genesys relacionadas a la tarjeta registrada en el archivo de consumo
     */
        private void registrarRetiroTarjetaANIBOL(TarjetaRetiroCandidatoDTO tarjetaRetiro, BigDecimal valorDisponible,
            List<CuentaAdministradorSubsidioDTO> listaCuentasTarjetas) {
        final String USUARIO_REGISTRO = "Genesys";

        //TODO: SE DEBE REALIZAR LA BUSQUEDA DEL SITIO DE PAGO PARAMETRIZADO POR LA CAJA DE COMPENSACIÓN
        Long idSitioDePago = null;//obtenerIdSitioDePagoParametrizadoCCF();
        logger.info("**__** registrarRetiroTarjetaANIBOL CodigoEstablecimiento: " + tarjetaRetiro.getCodigoEstablecimiento());
        tarjetaRetiro.setCodigoEstablecimiento(tarjetaRetiro.getCodigoEstablecimiento().replaceFirst("^0+", ""));
        logger.info("**__** registrarRetiroTarjetaANIBOL CodigoEstablecimiento replace: " + tarjetaRetiro.getCodigoEstablecimiento());

        RegistroArchivoConsumosAnibol registroConsumo = tarjetaRetiro.convertToEntity();

        Date date = null;
        String fechaTransaccion = null;
        String horaTransaccion = null;

        try {
                /*
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
            date = formatter.parse(tarjetaRetiro.getFechaTransaccion());
            logger.info("fecha transaccion  " +date);*/
            horaTransaccion = registroConsumo.getHoraAutorizador();
            fechaTransaccion = registroConsumo.getFechaTransaccion();
            String fechaHoraCompleta = fechaTransaccion + " "+ horaTransaccion;
            SimpleDateFormat formatterFechaHora  = new SimpleDateFormat("yyyyMMdd HHmmssSSS", Locale.ENGLISH);
            date = formatterFechaHora.parse(fechaHoraCompleta);
            logger.info("Fecha y hora transacción: " + date);   

        }catch (ParseException e){
            date = new Date();
        }


        //el valor disponible es igual al solicitado, es un retiro completo
        if (valorDisponible.compareTo(tarjetaRetiro.getValorTransaccion()) == 0) {
            Long idNuevaCuenta = 0L;
            //Paso 2: se crea una cuenta de administrador subsidio referente al retiro
            try{
                  idNuevaCuenta = registrarCuentarRetiro(USUARIO_REGISTRO, TipoMedioDePagoEnum.TARJETA,
                    listaCuentasTarjetas.get(0).getIdMedioDePago(), tarjetaRetiro.getValorTransaccion(),
                    listaCuentasTarjetas.get(0).getIdAdministradorSubsidio(), date, idSitioDePago, null,
                    tarjetaRetiro.getCodigoEstablecimiento(), "", tarjetaRetiro.getCodigoAutorizacion(), tarjetaRetiro.getNumeroTarjeta(),
                    listaCuentasTarjetas.get(0).getIdEmpleador(),listaCuentasTarjetas.get(0).getIdAfiliadoPrincipal(),listaCuentasTarjetas.get(0).getIdBeneficiarioDetalle(),
                    listaCuentasTarjetas.get(0).getIdGrupoFamiliar(),listaCuentasTarjetas.get(0).getSolicitudLiquidacionSubsidio(), null);
            }catch(Exception e ){
                  logger.error("**__** Error al crear La cuenta :"+e );
            }
            if(idNuevaCuenta==0L){
                logger.info("**__** Ingreso idNuevaCuenta en 0" );
                tarjetaRetiro.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
                                    
            }else{
                CuentaAdministradorSubsidioDTO cuentaRetiro = consultasCore.consultarCuentaAdministradorSubsidio(idNuevaCuenta);
                cuentaRetiro.setFechaHoraTransaccion(date);
                consultasCore.actualizarCuentaAdministradorSubsidio(cuentaRetiro);
                for (CuentaAdministradorSubsidioDTO cuentaAsociadoTarjeta : listaCuentasTarjetas) {
                    //paso1 y paso 3: se pasa el estado de la cuenta a COBRADO y se relaciona con la transacción de retiro efectuada.
                    cuentaAsociadoTarjeta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                    cuentaAsociadoTarjeta.setIdCuentaAdminSubsidioRelacionado(idNuevaCuenta);
                    cuentaAsociadoTarjeta.setFechaHoraUltimaModificacion(new Date());
                    cuentaAsociadoTarjeta.setUsuarioUltimaModificacion(USUARIO_REGISTRO);
                    //se actualiza la cuenta
                    consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAsociadoTarjeta);
                    //se obtienen los detalles asociados a la cuenta 
                    List<DetalleSubsidioAsignadoDTO> detalles = consultarDetallesSubsidiosAsignadosAsociadosAbono(
                            cuentaAsociadoTarjeta.getIdCuentaAdministradorSubsidio());

                    for (DetalleSubsidioAsignadoDTO detalle : detalles) {
                        detalle.setFechaTransaccionRetiro(date);
                        detalle.setUsuarioTransaccionRetiro(USUARIO_REGISTRO);
                        detalle.setFechaHoraUltimaModificacion(new Date());
                        detalle.setUsuarioUltimaModificacion(USUARIO_REGISTRO);
                        //se actualiza el detalle
                        consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                    }
                }
            }
          
        }
        else { // el valor disponible es mayor al solicitado : retiro parcial
            try{
                //Paso 2:se crea la cuenta de Retiro
                CuentaAdministradorSubsidio cuentaRetiro = registrarCuentarRetiroCuenta(USUARIO_REGISTRO, TipoMedioDePagoEnum.TARJETA,
                        listaCuentasTarjetas.get(0).getIdMedioDePago(), tarjetaRetiro.getValorTransaccion(),
                        listaCuentasTarjetas.get(0).getIdAdministradorSubsidio(), date, idSitioDePago, null,
                        tarjetaRetiro.getCodigoEstablecimiento(), tarjetaRetiro.getCodigoAutorizacion(), tarjetaRetiro.getNumeroTarjeta(),
                        listaCuentasTarjetas.get(0).getIdEmpleador(),listaCuentasTarjetas.get(0).getIdAfiliadoPrincipal(),listaCuentasTarjetas.get(0).getIdBeneficiarioDetalle(),
                        listaCuentasTarjetas.get(0).getIdGrupoFamiliar(),listaCuentasTarjetas.get(0).getSolicitudLiquidacionSubsidio());

                //variable C = valor del retiro que está pendiente por aplicar( inicialmente es igual a la variable a(valor solicitado) pero con signo negativo)
                double valorC = tarjetaRetiro.getValorTransaccion().negate().doubleValue();

                //Paso 1: Se actualizan los registros con tipo transacción "Abono" que fueron utilizados para calcular el saldo.
                for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasTarjetas) {

                    if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                        //C = C+B
                        valorC += cuenta.getValorRealTransaccion().doubleValue();

                        //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(USUARIO_REGISTRO);
                        cuenta.setIdCuentaAdminSubsidioRelacionado(cuentaRetiro.getIdCuentaAdministradorSubsidio());
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);                    
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);                   
                        if (valorC == 0) { //Caso C=0
                            break;
                        }
                        else if (valorC > 0) { //Caso: C>0

                            double valorReal = cuenta.getValorOriginalTransaccion().doubleValue() - valorC;
                            //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                            cuenta.setValorRealTransaccion(BigDecimal.valueOf(valorReal));                        
                            cuenta.setIdSitioDeCobro(idSitioDePago);
                            //cuenta.setValorRealTransaccion(cuentaRetiro.getValorOriginalTransaccion().abs());                        
                            consultasCore.actualizarCuentaAdministradorSubsidioValor(cuenta); 

                            //Se crea el registro “Nuevo abono originado por fraccionamiento”
                            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();
                            cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
                            cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(USUARIO_REGISTRO);
                            cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                            cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                            cuentaAdministradorSubsidioDTO.setMedioDePago(cuenta.getMedioDePago());
                            cuentaAdministradorSubsidioDTO.setIdMedioDePago(cuenta.getIdMedioDePago());
                            cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO_PARCIAL);
                            cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(BigDecimal.valueOf(valorC));
                            cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(cuenta.getIdAdministradorSubsidio());
                            cuentaAdministradorSubsidioDTO.setIdEmpleador(cuenta.getIdEmpleador());
                            cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(cuenta.getIdAfiliadoPrincipal());
                            cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(cuenta.getIdBeneficiarioDetalle());
                            cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(cuenta.getIdGrupoFamiliar());
                            cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(cuenta.getSolicitudLiquidacionSubsidio());
                            cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(cuenta.getFechaHoraTransaccion());
                            cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(cuenta.getUsuarioTransaccionLiquidacion());
                            cuentaAdministradorSubsidioDTO
                                    .setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
                            cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                            cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(USUARIO_REGISTRO);
                            cuentaAdministradorSubsidioDTO.setIdSitioDePago(cuenta.getIdSitioDePago());
                            cuentaAdministradorSubsidioDTO.setIdTransaccionOriginal(cuenta.getIdCuentaAdministradorSubsidio());
                            cuentaAdministradorSubsidioDTO.setNumeroTarjetaAdminSubsidio(tarjetaRetiro.getNumeroTarjeta());
                            //se crea el abono originado por fraccionamiento
                            Long idAbonoOriginadoFraccionamiento = consultasCore
                                    .crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                            //Paso 4: se ajustan los detalles del "abono fraccionado"
                            fraccionarRegistrosDetallesAbono(cuenta.getIdCuentaAdministradorSubsidio(), cuenta.getValorRealTransaccion(),
                                    USUARIO_REGISTRO, idAbonoOriginadoFraccionamiento);

                            break;
                        }
                    }
                }
            }catch(Exception ex){
                 logger.error("**__** Error al crear La cuenta ELSE :"+ex );
                 tarjetaRetiro.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.PROCESADO_CON_INCONSISTENCIAS);
            }
        }
    }

    /**
     * Método encargado de registrar una transacción de retiro, sea por el proceso
     * de la HU-31-218 en la cual son retiros en EFECTIVO o de la HU-31-ANEXO, donde son
     * registros de retiros de las tarjetas.
     * @param usuario
     *        <code>String</code>
     *        Usuario que realiza el registro de retiro
     * @param medioDePago
     *        <code>TipoMedioDePagoEnum</code>
     *        Tipo de medio de pago por el cual se genera el retiro.
     * @param idMedioDePago
     *        <code>Long</code>
     *        Identificador principal de la base de datos del medio de pago relacionado.
     * @param valorSolicitado
     *        <code>BigDecimal</code>
     *        Valor solicitado parar retirar.
     * @param idAdminSubsidio
     *        <code>Long</code>
     *        Identificador principal de la base de datos del administrador del subsidio relacionado.
     * @param fechaActual
     *        <code>Date</code>
     *        Fecha en la cual se realiza el registro de retiro.
     * @param idSitioDePago
     *        <code>Long</code>
     *        Sitio de pago asociado al retiro.
     * @param identificadorRespuesta
     *        <code>String</code>
     *        Identificador de respuesta asociado al retiro
     * @param nombreConvenio
     *        <code>String</code>
     *        Registro del convenio referente al retiro.
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercer pagador, registro único por retiro.
     * @param numeroTarjeta
     *        <code>String</code>
     * @param idPuntoCobro
     *        <code>String</code>
     *        Identificador punto de cobro
     * @return Identificador de la transacción de retiro registrada en la base de datos
     */
    private Long registrarCuentarRetiro(String usuario, TipoMedioDePagoEnum medioDePago, Long idMedioDePago, BigDecimal valorSolicitado,
            Long idAdminSubsidio, Date fechaActual, Long idSitioDePago, String identificadorRespuesta, String nombreConvenio, String nombreUsuarioConvenio,
            String idTransaccionTercerPagador, String numeroTarjeta, Long idEmpleador, Long idAfiliado, Long idBeneficiarioDetalle, Long idGrupoFamiliar
            ,Long idSolicitudLiquidacionSubsidio, String idPuntoCobro) {

        logger.info("Ingresa a registrarCuentarRetiro");

        CuentaAdministradorSubsidioDTO cuentaRetiro = new CuentaAdministradorSubsidioDTO();
        cuentaRetiro.setUsuarioCreacionRegistro(usuario);
        cuentaRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.RETIRO);
        cuentaRetiro.setMedioDePago(medioDePago);
        cuentaRetiro.setIdMedioDePago(idMedioDePago);
        cuentaRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
        cuentaRetiro.setValorOriginalTransaccion(valorSolicitado.negate());
        cuentaRetiro.setIdAdministradorSubsidio(idAdminSubsidio);
        cuentaRetiro.setIdEmpleador(idEmpleador);
        cuentaRetiro.setIdAfiliadoPrincipal(idAfiliado);
        cuentaRetiro.setIdBeneficiarioDetalle(idBeneficiarioDetalle);
        cuentaRetiro.setIdGrupoFamiliar(idGrupoFamiliar);
        cuentaRetiro.setSolicitudLiquidacionSubsidio(idSolicitudLiquidacionSubsidio);
        cuentaRetiro.setFechaHoraTransaccion(fechaActual);
        cuentaRetiro.setUsuarioTransaccionLiquidacion(nombreUsuarioConvenio != "" ? nombreUsuarioConvenio : usuario);
        cuentaRetiro.setValorRealTransaccion(cuentaRetiro.getValorOriginalTransaccion());
        cuentaRetiro.setFechaHoraUltimaModificacion(new Date());
        cuentaRetiro.setUsuarioUltimaModificacion(usuario);
        cuentaRetiro.setIdSitioDeCobro(idSitioDePago);
        cuentaRetiro.setIdRemisionDatosTerceroPagador(identificadorRespuesta);
        cuentaRetiro.setNombreTerceroPagador(nombreConvenio);
        cuentaRetiro.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
        cuentaRetiro.setIdPuntoDeCobro(idPuntoCobro);
        //aplica cuando es un retiro por TARJETA
        cuentaRetiro.setNumeroTarjetaAdminSubsidio(numeroTarjeta);
        //se obtiene el id generado en base de datos de la transacción de retiro
        return consultasCore.crearCuentaAdministradorSubsidio(cuentaRetiro);
    }
    
    
    /**
     * Método encargado de registrar una transacción de retiro, sea por el proceso
     * de la HU-31-218 en la cual son retiros en EFECTIVO o de la HU-31-ANEXO, donde son
     * registros de retiros de las tarjetas.
     * @param usuario
     *        <code>String</code>
     *        Usuario que realiza el registro de retiro
     * @param medioDePago
     *        <code>TipoMedioDePagoEnum</code>
     *        Tipo de medio de pago por el cual se genera el retiro.
     * @param idMedioDePago
     *        <code>Long</code>
     *        Identificador principal de la base de datos del medio de pago relacionado.
     * @param valorSolicitado
     *        <code>BigDecimal</code>
     *        Valor solicitado parar retirar.
     * @param idAdminSubsidio
     *        <code>Long</code>
     *        Identificador principal de la base de datos del administrador del subsidio relacionado.
     * @param fechaActual
     *        <code>Date</code>
     *        Fecha en la cual se realiza el registro de retiro.
     * @param idSitioDePago
     *        <code>Long</code>
     *        Sitio de pago asociado al retiro.
     * @param identificadorRespuesta
     *        <code>String</code>
     *        Identificador de respuesta asociado al retiro
     * @param nombreConvenio
     *        <code>String</code>
     *        Registro del convenio referente al retiro.
     * @param idTransaccionTercerPagador
     *        <code>String</code>
     *        identificador de transacción del tercer pagador, registro único por retiro.
     * @param numeroTarjeta
     *        <code>String</code>
     * @return Identificador de la transacción de retiro registrada en la base de datos
     */
    private CuentaAdministradorSubsidio registrarCuentarRetiroCuenta(String usuario, TipoMedioDePagoEnum medioDePago, Long idMedioDePago, BigDecimal valorSolicitado,
            Long idAdminSubsidio, Date fechaActual, Long idSitioDePago, String identificadorRespuesta, String nombreConvenio,
            String idTransaccionTercerPagador, String numeroTarjeta, Long idEmpleador, Long idAfiliado, Long idBeneficiarioDetalle, Long idGrupoFamiliar,
            Long idSolicitudLiquidacionSubsidio) {

        CuentaAdministradorSubsidioDTO cuentaRetiro = new CuentaAdministradorSubsidioDTO();
        cuentaRetiro.setUsuarioCreacionRegistro(usuario);
        cuentaRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.RETIRO);
        cuentaRetiro.setMedioDePago(medioDePago);
        cuentaRetiro.setIdMedioDePago(idMedioDePago);
        cuentaRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
        cuentaRetiro.setValorOriginalTransaccion(valorSolicitado.negate());
        cuentaRetiro.setIdAdministradorSubsidio(idAdminSubsidio);
        cuentaRetiro.setIdEmpleador(idEmpleador);
        cuentaRetiro.setIdAfiliadoPrincipal(idAfiliado);
        cuentaRetiro.setIdBeneficiarioDetalle(idBeneficiarioDetalle);
        cuentaRetiro.setIdGrupoFamiliar(idGrupoFamiliar);
        cuentaRetiro.setSolicitudLiquidacionSubsidio(idSolicitudLiquidacionSubsidio);
        cuentaRetiro.setFechaHoraTransaccion(fechaActual);
        cuentaRetiro.setUsuarioTransaccionLiquidacion(usuario);
        cuentaRetiro.setValorRealTransaccion(cuentaRetiro.getValorOriginalTransaccion());
        cuentaRetiro.setFechaHoraUltimaModificacion(new Date());
        cuentaRetiro.setUsuarioUltimaModificacion(usuario);
        cuentaRetiro.setIdSitioDeCobro(idSitioDePago);
        cuentaRetiro.setIdRemisionDatosTerceroPagador(identificadorRespuesta);
        cuentaRetiro.setNombreTerceroPagador(nombreConvenio);
        cuentaRetiro.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
        //aplica cuando es un retiro por TARJETA
        cuentaRetiro.setNumeroTarjetaAdminSubsidio(numeroTarjeta);
        //se obtiene el id generado en base de datos de la transacción de retiro
        return consultasCore.crearCuentaAdministradorSubsidioCuenta(cuentaRetiro);
    }

    /**
     * Método que obtiene el id del sitio de pago que se encuentra parametrizado por CCF, para
     * el registro del retiro realizado por el archivo de consumo de ANIBOL para las tarjetas
     * @return id sitio de pago
     */
    private Long obtenerIdSitioDePagoParametrizadoCCF() {
        Long sitioDePago = null;
        try {
            sitioDePago = Long.parseLong(
                    (CacheManager.getParametro(ParametrosSistemaConstants.PARAM_DIAS_VENCIMIENTO_SUBSIDIO_MONETARIO_CCF).toString()));
        } catch (Exception e) {
            logger.debug("Error al obtener el sitio de pago parametrizado por CCF");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }
        return sitioDePago;
    }

    /**
     * Método que permite obtener la lista de archivos entregados por la entidad de descuento
     * @param archivos
     *        arreglo para almacenar la información de los archivos
     * @return DTO con la información del archivo de consumo de tarjeta
     */
    private List<InformacionArchivoDTO> obtenerArchivosFTP() {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.obtenerArchivosFTP()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConexionServidorFTPUtil<InformacionArchivoDTO> conexionFTP = new ConexionServidorFTPUtil<InformacionArchivoDTO>(
                ProcesoEnum.PAGOS_SUBSIDIO_MONETARIO.name(), InformacionArchivoDTO.class);
        conexionFTP.setNombreHost(
                (String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVO_CONSUMO_TARJETA_ANIBOL_NOMBRE_HOST));
        conexionFTP.setPuerto((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CONSUMO_TARJETA_ANIBOL_PUERTO));
        conexionFTP.setUrlArchivos((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CONSUMO_TARJETA_URL));
        conexionFTP.setProtocolo(
                Protocolo.valueOf((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CONSUMO_TARJETA_PROTOCOLO)));
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVO_CONSUMO_TARJETA_ANIBOL_NOMBRE_USUARIO)));
        conexionFTP.setContrasena(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CONSUMO_TARJETA_CONTRASENA)));

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
     * Método encargado de obtener los registros repetidos en los candidatos a reversión y a retirar por el medio de pago
     * Tarjeta. Estos registros serán guardados como no procesados y no se tomaran en cuenta para sus respectivos procesos.
     * @param idArchivoConsumoTarjeta
     *        <code>Long</code>
     *        Identificador principal del archivo de consumo de tarjeta que se almaceno.
     * @param listaCandidatos
     *        <code>List<TarjetaRetiroCandidatoDTO></code>
     *        Lista de tarjetas candidatas para realizar el proceso de retiro.
     * @param listaReversos
     *        <code>List<TarjetaRetiroCandidatoDTO></code>
     *        Lista de tarjetas candidatas para realizar el proceso de reversión.
     */
    private void compararRegistrosTarjetas(Long idArchivoConsumoTarjeta, List<TarjetaRetiroCandidatoDTO> listaCandidatos,
            List<TarjetaRetiroCandidatoDTO> listaReversos) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.compararRegistrosTarjetas(Long, List<TarjetaRetiroCandidatoDTO>, List<TarjetaRetiroCandidatoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TarjetaRetiroCandidatoDTO> retirosNoProcesados = new ArrayList<>();
        List<TarjetaRetiroCandidatoDTO> reversosNoProcesados = new ArrayList<>();

        for (TarjetaRetiroCandidatoDTO reverso : listaReversos) {
            for (TarjetaRetiroCandidatoDTO retiro : listaCandidatos) {

                if (reverso.getCodigoAutorizacion().equals(retiro.getCodigoAutorizacion())) {
                    //se agregan los reversos y retiros que tienen el mismo codigo a listas auxiliares
                    retiro.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.NO_PROCESADO);
                    retirosNoProcesados.add(retiro);
                    reverso.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.NO_PROCESADO);
                    reversosNoProcesados.add(reverso);
                    //se quitan los retiros y reversos de las correspondientes listas
                    listaCandidatos.remove(retiro);
                    listaReversos.remove(reverso);
                }
                if (listaReversos.isEmpty())
                    break;
            }
            if (listaReversos.isEmpty())
                break;
        }
        //se almacenan los retiros no procesados
        consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, retirosNoProcesados);
        //se almacenan los reversos no procesados
        consultasCore.guardarRegistrosTarjetasArchivoConsumoANIBOL(idArchivoConsumoTarjeta, reversosNoProcesados);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#generarArchivoResultadoConsignacionesBancos(java.lang.String)
     */
    @Override
    public InformacionArchivoDTO generarArchivoResultadoConsignacionesBancos(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.generarArchivoResultadoConsignacionesBancos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO archivoConsignaciones = generarArchivoConsignacionesPagosJudiciales(numeroRadicacion,
                TipoArchivoPagoEnum.CONSIGNACIONES_BANCOS);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoConsignaciones;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.service.SubsidioMonetarioService#generarArchivoResultadoPagosJudiciales(java.lang.String)
     */
    @Override
    public InformacionArchivoDTO generarArchivoResultadoPagosJudiciales(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.generarArchivoResultadoPagosJudiciales(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO archivoPagosJudiciales = generarArchivoConsignacionesPagosJudiciales(numeroRadicacion,
                TipoArchivoPagoEnum.PAGOS_JUDICIALES);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoPagosJudiciales;
    }

    /**
     * Método que permite generar el archivo con el formato definido por sudameris para consignaciones o pagos judiciales
     * @param numeroRadicacion
     *        valor del número de radicación asociado a la liquidación
     * @param tipoArchivo
     *        tipo de archivo a generar
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO generarArchivoConsignacionesPagosJudiciales(String numeroRadicacion, TipoArchivoPagoEnum tipoArchivo) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.generarArchivoConsignacionesPagosJudiciales(String,TipoArchivoPagoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO archivoPagosConsignaciones = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_DISPERSION_BANCOS).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.FIXED_TEXT_PLAIN };
        
        ArchivoPagoBancoFilterDTO filtro = new ArchivoPagoBancoFilterDTO();
        filtro.setNumeroRadicacion(numeroRadicacion);
        filtro.setTipoArchivoPago(tipoArchivo);

        FileGeneratorOutDTO outDTO = null;
        try {
            outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);
        } catch (FileGeneratorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (FileGeneratedState.SUCCESFUL.equals(outDTO.getState())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (tipoArchivo.equals(TipoArchivoPagoEnum.CONSIGNACIONES_BANCOS)) {
                outDTO.setFixedLengthTxtFilename("CB_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".txt");
                archivoPagosConsignaciones = convertirOutDTOFixedTextType(outDTO);
            }
            else {
                outDTO.setFixedLengthTxtFilename("PJ_LIQUIDACION_" + numeroRadicacion + "_" + format.format(new Date()) + ".txt");
                archivoPagosConsignaciones = convertirOutDTOFixedTextType(outDTO);
            }
        }
        

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoPagosConsignaciones;
    }

    /**
     * Método que se encarga de convertir un FileGeneratorOutDTO a un InformacionArchivoDTO
     * @param outDTO
     *        información del archivo generado por lion
     * @return DTO con la información del archivo para enviar al ECM
     */
    private InformacionArchivoDTO convertirOutDTOFixedTextType(FileGeneratorOutDTO outDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.convertirOutDTO(FileGeneratorOutDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();

        informacionArchivoDTO.setDataFile(outDTO.getFixedLengthTxt());
        informacionArchivoDTO.setFileName(outDTO.getFixedLengthTxtFilename());
        informacionArchivoDTO.setDocName(outDTO.getFixedLengthTxtFilename());
        informacionArchivoDTO.setFileType(MediaType.TEXT_PLAIN);
        informacionArchivoDTO.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.toString());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return informacionArchivoDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoEnviado(java.lang.String,
     *      com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum, java.util.List)
     */
    @Override
    public void dispersarPagosEstadoEnviado(String numeroRadicacion, EstadoTransaccionSubsidioEnum estadoTransaccion,
            List<TipoMedioDePagoEnum> mediosDePago, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoEnviado(String,EstadoTransaccionSubsidioEnum,List<TipoMedioDePagoEnum>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + "numeroRadicacion: "+numeroRadicacion + "-estadoTransaccion: "+estadoTransaccion + "-mediosDePago: "+ mediosDePago);

        consultasCore.dispersarPagosEstadoEnviado(numeroRadicacion, estadoTransaccion, mediosDePago, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentasAdministradorMedioTarjeta(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjeta(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarCuentasAdministradorMedioTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> cuentasAdministrador = consultasCore
                .consultarCuentasAdministradorMedioTarjeta(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cuentasAdministrador;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public CuentaAdministradorSubsidioDTO consultarCuentaAdminMedioTarjeta(Long idCuentaAdminSubsidio){
         
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarCuentaAdminMedioTarjeta(Long)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
         CuentaAdministradorSubsidioDTO cuentaAdministrador = consultasCore
                 .consultarCuentaAdministradorMedioTarjeta(idCuentaAdminSubsidio);
        
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         return cuentaAdministrador;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoAplicado(java.lang.String,
     *      java.util.List)
     */
    @Override
    public void dispersarPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoAplicado(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoAplicado(numeroRadicacion, abonosExitosos, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    

    @Override
    public void dispersarMasivoPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoAplicado(String,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoAplicado(numeroRadicacion, abonosExitosos, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoEnviadoOrigenAnulacion(java.util.List)
     */
    @Override
    public void dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoEnviadoOrigenAnulacion(identificadoresCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoEnviadoOrigenAnulacion(java.util.List)
     */
    @Override
    public void dispersarPagosEstadoEnviadoOrigenAnulacionH(List<Long> identificadoresCuentas) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoEnviadoOrigenAnulacion(identificadoresCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
            List<Long> identificadoresCuentas) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<CuentaAdministradorSubsidioDTO> cuentasAdministrador = consultasCore
                .consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(identificadoresCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return cuentasAdministrador;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoAplicadoOrigenAnulacion(java.util.List)
     */
    @Override
    public void dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoAplicadoOrigenAnulacion(identificadoresCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarSolicitudAnulacionSubsidioCobrado(com.asopagos.dto.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobradoModeloDTO)
     */
    @Override
    public Boolean actualizarSolicitudAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.actualizarSolicitudAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobradoModeloDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean success = null;
        Long idSolicitudAnulacionSubsidioCobrado = null;
        SolicitudAnulacionSubsidioCobrado solicitudAnulacionSubsidioCobrado = null;

        try {
            idSolicitudAnulacionSubsidioCobrado = solicitudAnulacionSubsidioCobradoModeloDTO.getIdSolicitudAnulacionSubsidioCobrado();
            solicitudAnulacionSubsidioCobrado = consultasCore
                    .consultarSolicitudAnulacionSubsidioCobrado(idSolicitudAnulacionSubsidioCobrado);
            if (solicitudAnulacionSubsidioCobrado != null) {
                solicitudAnulacionSubsidioCobrado.setEstadoSolicitud(solicitudAnulacionSubsidioCobradoModeloDTO.getEstadoSolicitud());
                solicitudAnulacionSubsidioCobrado.setObservaciones(solicitudAnulacionSubsidioCobradoModeloDTO.getObservaciones());
                success = consultasCore.actualizarSolicitudAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobrado);
            }
            else {
                logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, "No se encontro el registro solicitado");
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetalleSolicitudAnulacionSubsidioCobrado(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DetalleSolicitudAnulacionSubsidioCobradoDTO consultarDetalleSolicitudAnulacionSubsidioCobrado(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDetalleSolicitudAnulacionSubsidioCobrado( String )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudAnulacionSubsidioCobrado = null;
        SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO = null;
        DetalleSolicitudAnulacionSubsidioCobradoDTO detalleSolicitudAnulacionSubsidioCobradoDTO = null;
        List<AbonosSolicitudAnulacionSubsidioCobradoDTO> lstAbonosSolicitudAnulacionSubsidioCobradoDTO = null;
        AbonosSolicitudAnulacionSubsidioCobradoDTO abonosSolicitudAnulacionSubsidioCobradoDTO = null;

        solicitudAnulacionSubsidioCobradoDTO = new SolicitudAnulacionSubsidioCobradoDTO();
        solicitudAnulacionSubsidioCobradoDTO.setNumeroRadicado(numeroRadicacion);
        lstAbonosSolicitudAnulacionSubsidioCobradoDTO = consultasCore
                .consultarDetalleSolicitudAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobradoDTO);
        if (lstAbonosSolicitudAnulacionSubsidioCobradoDTO != null && !lstAbonosSolicitudAnulacionSubsidioCobradoDTO.isEmpty()) {
            detalleSolicitudAnulacionSubsidioCobradoDTO = new DetalleSolicitudAnulacionSubsidioCobradoDTO();
            abonosSolicitudAnulacionSubsidioCobradoDTO = lstAbonosSolicitudAnulacionSubsidioCobradoDTO.iterator().next();
            idSolicitudAnulacionSubsidioCobrado = abonosSolicitudAnulacionSubsidioCobradoDTO.getIdSolicitudAnulacionSubsidio();
            detalleSolicitudAnulacionSubsidioCobradoDTO.setIdSolicitudAnulacionSubsidioCobrado(idSolicitudAnulacionSubsidioCobrado);
            detalleSolicitudAnulacionSubsidioCobradoDTO
                    .setAbonosSolicitudAnulacionSubsidioCobradoDTO(lstAbonosSolicitudAnulacionSubsidioCobradoDTO);
        }
        else {
            logger.error(firmaMetodo + ": " + PagosSubsidioMonetarioConstants.ERROR_MESSAGE_SOLICITUD_ANULACION_SIN_ABONOS);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
                    PagosSubsidioMonetarioConstants.ERROR_MESSAGE_SOLICITUD_ANULACION_SIN_ABONOS);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleSolicitudAnulacionSubsidioCobradoDTO;

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidacionFallecimiento(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionMontoLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidacionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionMontoLiquidadoFallecimientoDTO dispersionDTO = consultasCoreLiquidacion
                .consultarDispersionMontoLiquidacionFallecimiento(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO dispersionEfectivoDTO = consultasCoreLiquidacion
                .consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(numeroRadicacion, identificadorCondicion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionEfectivoDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO dispersionTarjetaDTO = consultasCoreLiquidacion
                .consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(numeroRadicacion, identificadorCondicion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionTarjetaDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO dispersionBancoDTO = consultasCoreLiquidacion
                .consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(numeroRadicacion, identificadorCondicion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionBancoDTO;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDispersionMontoLiquidadoFallecimientoDescuentos(java.lang.String,
     *      java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoDescuentos(String numeroRadicacion,
            Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.consultarDispersionMontoLiquidadoFallecimientoDescuentos(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoDescuentosFallecimientoDTO dispersionDescuentosDTO = consultasCoreLiquidacion
                .consultarDispersionMontoLiquidadoFallecimientoDescuentos(numeroRadicacion, identificadorCondicion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionDescuentosDTO;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarLiquidacionFallecimientoPorAdmin(com.asopagos.rest.security.dto.UserDTO, java.util.List)
     */
    @Override
    public List<Long> dispersarLiquidacionFallecimientoPorAdmin(UserDTO userDTO, List<Long> lstIdsCondicionesBeneficiarios) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarEfectivoLiquidacionFallecimiento(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> lstIdNuevosAbonos = new ArrayList<>();
        //se obtienen los detalles programados asociados a los identificadores de condiciones de beneficiarios
        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados = consultasCore
                .consultarDetallesProgramadosPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
        //se obtiene la lista de id de las cuentas relacionadas de los detalles.
        List<Long> lstIdCuenta = lstDetallesProgramados.stream()
                .filter(distinctByValue(detalle -> detalle.getIdCuentaAdministradorSubsidio().longValue()))
                .map(e -> e.getIdCuentaAdministradorSubsidio()).collect(Collectors.toList());
        
        //se obtienen los ids de los registros para posteriormente cambiar el estado de la tabla de los derechos programados 
        //para que ya no se tengan en cuenta para proximas liquidaciones para dicho administrador.
//        List<Long> idsDetallesProgramados = lstDetallesProgramados.stream().map(e -> e.getIdDetalleSubsidioAsignado())
//                .collect(Collectors.toList());

        for (Long idCuentaRelacionadoDetalleProgramado : lstIdCuenta) {
            //Cuenta asociada a los detalles programados
            CuentaAdministradorSubsidioDTO nuevaCuentaBasadadAbonoDetalleProgramado = consultarCuentaAdmonSubsidioDTO(
                    idCuentaRelacionadoDetalleProgramado);
            //Mantis 0260937: Si el estado de la cuenta es diferente de Cobrado
            if (!EstadoTransaccionSubsidioEnum.COBRADO.equals(nuevaCuentaBasadadAbonoDetalleProgramado.getEstadoTransaccion())) {
                nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraCreacionRegistro(new Date());
                nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraTransaccion(new Date());
                nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraUltimaModificacion(new Date());
                nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioCreacionRegistro(userDTO.getNombreUsuario());
                nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
                nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioTransaccionLiquidacion(userDTO.getNombreUsuario());
                nuevaCuentaBasadadAbonoDetalleProgramado.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                //se obtienen los detalles asociados a la cuenta
//                List<DetalleSubsidioAsignadoDTO> detallesRelacionados = lstDetallesProgramados.stream()
//                        .filter(detalle -> detalle.getIdCuentaAdministradorSubsidio().compareTo(idCuentaRelacionadoDetalleProgramado) == 0)
//                        .collect(Collectors.toList());
//                nuevaCuentaBasadadAbonoDetalleProgramado.setListaDetallesSubsidioAsignadoDTO(detallesRelacionados);
                //se crea la nueva cuenta
//                Long idNuevoAbono = registrarCuentaAdministradorSubsidio(nuevaCuentaBasadadAbonoDetalleProgramado, userDTO);
//                CuentaAdministradorSubsidioDTO cuentaAbono = consultarCuentaAdmonSubsidioDTO(idNuevoAbono);
                //se pasa a APLICADO
                nuevaCuentaBasadadAbonoDetalleProgramado.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                consultasCore.actualizarCuentaAdministradorSubsidio(nuevaCuentaBasadadAbonoDetalleProgramado);
                
                //se actualiza la cuenta asociada con anterioridad a los detalles programados
                
                //se modifican los valores de los detalles para poder agregarlo
                lstDetallesProgramados.stream().forEach(element -> { 
                    //element.setIdDetalleSubsidioAsignado(null);
                    //se asocia el id de la nueva cuenta creada
//                    element.setIdCuentaAdministradorSubsidio(idNuevoAbono);
                    element.setUsuarioCreador(userDTO.getNombreUsuario());
                    element.setFechaHoraCreacion(new Date());
                    element.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
                    element.setFechaHoraUltimaModificacion(new Date());
                    consultasCore.actualizarDetalleSubsidioAsignado(element);
                });
                   
                //se crean los detalles de subsidios asignados que estaban en la tabla de detalles programados asociados a la nueva cuenta.
                //registrarDetalleSubsidioAsignado(detallesRelacionados);
                lstIdNuevosAbonos.add(nuevaCuentaBasadadAbonoDetalleProgramado.getIdCuentaAdministradorSubsidio());
            }
        }
        //se actualizan los detalles programados
        //consultasCore.actualizarEstadoDetalleADerechoProgramado(idsDetallesProgramados);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdNuevosAbonos.isEmpty() ? null : lstIdNuevosAbonos;
    }
    
    /**
     * Metódo utilitario para obtener una lista de de datos sin repetición de los mismos.
     * @param keyExtractor
     *        dato que se requiere sea unico.
     * @return lista sin datos repetidos
     */
    private <T> Predicate<T> distinctByValue(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Long> buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(List<Long> lstIdsCondicionesBeneficiarios) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //se obtienen los detalles programados asociados a los identificadores de condiciones de beneficiarios
        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados = consultasCore
                .consultarDetallesProgramadosPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
        //se obtiene la lista de id de las cuentas relacionadas de los detalles.
        List<Long> lstIdCuenta = lstDetallesProgramados.stream()
                .filter(distinctByValue(detalle -> detalle.getIdCuentaAdministradorSubsidio().longValue()))
                .map(e -> e.getIdCuentaAdministradorSubsidio()).collect(Collectors.toList());
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdCuenta;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Long> buscarCasPorIdCondicionesBeneficiariosYsolicitud(List<Long> lstIdsCondicionesBeneficiarios, String numeroRadicado) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.buscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //se obtienen los detalles programados asociados a los identificadores de condiciones de beneficiarios
        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados = consultasCore
                .consultarDetallesProgramadosPorIdCondicionesBeneficiariosYRadicado(lstIdsCondicionesBeneficiarios, numeroRadicado);
        //se obtiene la lista de id de las cuentas relacionadas de los detalles.
        List<Long> lstIdCuenta = lstDetallesProgramados.stream()
                .filter(distinctByValue(detalle -> detalle.getIdCuentaAdministradorSubsidio().longValue()))
                .map(e -> e.getIdCuentaAdministradorSubsidio()).collect(Collectors.toList());
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdCuenta;
    }

    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarTransaccionesSubsidioPorResultadoLiquidacion(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public VistaRetirosSubsidioDTO consultarTransaccionesSubsidioPorResultadoLiquidacion(Long idResultadoValidacionLiquidacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarTransaccionesSubsidioPorResultadoLiquidacion(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        int dsaId = 0;
        int dsaCuentaAdministradorSubsidio = 1;
        int dsaEstado = 2;
        int dsaOrigenRegistroSubsidio = 3;
        int dsaValorTotal = 4;
        int casEstadoTransaccionSubsidio = 5;
        int casIdCuentaAdmonSubsidioRelacionado = 6;
        
        double valorPendiente=0; 
        
        List<Object[]> detalles = consultasCore.consultarDetallesSubsidioPorResultadoLiquidacion(idResultadoValidacionLiquidacion);
        
        VistaRetirosSubsidioDTO vistaRetirosSubsidioDTO = new VistaRetirosSubsidioDTO();
        List<Long> listaidSCuentasAdminSubsidios = new ArrayList<Long>();
        for(Object[] detalle : detalles) {
            listaidSCuentasAdminSubsidios.add(Long.valueOf(detalle[dsaCuentaAdministradorSubsidio].toString()));
            if(detalle[casIdCuentaAdmonSubsidioRelacionado]!=null){
               listaidSCuentasAdminSubsidios.add(Long.valueOf(detalle[casIdCuentaAdmonSubsidioRelacionado].toString()));
            }
            if (EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO.name().equals(detalle[dsaEstado].toString())
                    && OrigenRegistroSubsidioAsignadoEnum.LIQUIDACION_SUBSIDIO_MONETARIO.name().equals(detalle[dsaOrigenRegistroSubsidio].toString())) {
                vistaRetirosSubsidioDTO.setIdSubsidioAsignado(Long.valueOf(detalle[dsaId].toString()));
            }
            if (EstadoSubsidioAsignadoEnum.ANULADO_REEMPLAZADO.name().equals(detalle[dsaEstado].toString()) &&
                OrigenRegistroSubsidioAsignadoEnum.LIQUIDACION_SUBSIDIO_MONETARIO.name().equals(detalle[dsaOrigenRegistroSubsidio].toString())) {
                vistaRetirosSubsidioDTO.setIdSubsidioAsignado(Long.valueOf(detalle[dsaId].toString()));
            }      
            if (EstadoTransaccionSubsidioEnum.APLICADO.name().equals(detalle[casEstadoTransaccionSubsidio].toString())
                    || EstadoTransaccionSubsidioEnum.ENVIADO.name().equals(detalle[casEstadoTransaccionSubsidio].toString())) {
                valorPendiente += Double.valueOf(detalle[dsaValorTotal] == null ? "0" : detalle[dsaValorTotal].toString());
            } else {
                if(vistaRetirosSubsidioDTO.getValorPendiente() == null) {
                    vistaRetirosSubsidioDTO.setValorPendiente(BigDecimal.ZERO);
                }
            }
        }
        vistaRetirosSubsidioDTO.setValorPendiente(BigDecimal.valueOf(valorPendiente));
        
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidios = consultasCore.consultarTransaccionesPorRVL(idResultadoValidacionLiquidacion);
        vistaRetirosSubsidioDTO.setCuentasAdministradorSubsidio(listaCuentasAdminSubsidios);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return vistaRetirosSubsidioDTO; 
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#obtenerPagosSubsidioDTO(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidio(Long idAdminSubsidio,String numeroRadicacion) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.obtenerPagosSubsidioDTO(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<PagoSubsidioProgramadoDTO> result = consultasCore.obtenerPagosSubsidiosProgramados(idAdminSubsidio,numeroRadicacion);
        
        List<DetalleSubsidioAsignadoProgramadoDTO> fechasProgramadasPagos = consultasSubsidio.consultarFechasProgramadasSubsidioFallecimiento(numeroRadicacion);
        
        for(PagoSubsidioProgramadoDTO resultado:result){
        	if(resultado.getFechaProgramadaPago() == null){
        		for(DetalleSubsidioAsignadoProgramadoDTO fecha:fechasProgramadasPagos){
        			if (resultado.getPeriodoLiquidacion().equals(fecha.getPeriodoLiquidado())){
        				resultado.setFechaParametrizadaPago(fecha.getFechaProgramadaPago());
        				resultado.setFechaProgramadaPago(fecha.getFechaProgramadaPago());
        				break;
        			}
        		}
        	}
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#dispersarPagosEstadoEnviadoOrigenAnulacionTrasnferencia(java.util.List)
     */
    @Override
    public void dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(List<Long> identificadoresCuentas) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoEnviadoOrigenAnulacionTrasnferencia(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.dispersarPagosEstadoEnviadoOrigenAnulacionTransferencia(identificadoresCuentas);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#generarArchivoCambioMedioPagoBancos(java.lang.Boolean, java.util.List)
     */
    @Override
    public InformacionArchivoDTO generarArchivoCambioMedioPagoBancos(Boolean esPagoJudicial ,List<Long>lstIdCuentasBancos) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarPagosEstadoEnviadoOrigenAnulacionTrasnferencia(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InformacionArchivoDTO archivoPagosConsignaciones = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_DISPERSION_BANCOS).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.FIXED_TEXT_PLAIN };
        try {
            ArchivoPagoBancoFilterDTO filtro = new ArchivoPagoBancoFilterDTO();
            filtro.setTipoArchivoPago(esPagoJudicial?TipoArchivoPagoEnum.PAGOS_JUDICIALES:TipoArchivoPagoEnum.CONSIGNACIONES_BANCOS);
            filtro.setLstIdCuentasNuevas(lstIdCuentasBancos);

            FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, filtro, formats);
            if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (filtro.getTipoArchivoPago().equals(TipoArchivoPagoEnum.CONSIGNACIONES_BANCOS)) {
                    outDTO.setFixedLengthTxtFilename("CB_LIQUIDACION_"+ format.format(new Date()) + ".txt");
                    archivoPagosConsignaciones = convertirOutDTOFixedTextType(outDTO);
                }
                else {
                    outDTO.setFixedLengthTxtFilename("PJ_LIQUIDACION_"+ format.format(new Date()) + ".txt");
                    archivoPagosConsignaciones = convertirOutDTOFixedTextType(outDTO);
                }
            }
        } catch (AsopagosException e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw e;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return archivoPagosConsignaciones;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarIdsProcesoAnibol()
     */
    @Override
    public List<Long> consultarIdsProcesoAnibol(){
        return consultasCore.consultarIdsProcesoAnibol();
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRadicadosProcesoAnibol()
     */
    @Override
    public List<String> consultarRadicadosProcesoAnibol(){
        return consultasCore.consultarRadicadosProcesoAnibol();
    }


    @Override
    public ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorPorNombrePagos(String nombreTerceroPagador){
        return consultasCore.consultarConvenioTerceroPagadorPorNombrePagos(nombreTerceroPagador);
    }


    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRegistroSolicitudAnibol()
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnibol(){
        return consultasCore.consultarRegistroSolicitudAnibol();
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRegistroSolicitudDescuentoAnibol()
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoAnibol(){
        return consultasCore.consultarRegistroSolicitudDescuentoAnibol();
    }
    
    
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDescuentoPrescripcionAnibol(Long idSolicitud){
        return consultasCore.consultarRegistroSolicitudDescuentoPrescripcionAnibol(idSolicitud);
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRegistroDispersionAnibol()
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroDispersionAnibol() {
        return consultasCore.consultarRegistroDispersionAnibol();
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentaAdminSubsidioPorSolicitud(java.lang.Long)
     */
    @Override
    public List<CuentaAdministradorSubsidio> consultarCuentaAdminSubsidioPorSolicitud(Long solicitudLiquidacionSubsidio){
        return consultasCore.consultarCuentaAdminSubsidioPorSolicitud(solicitudLiquidacionSubsidio);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarEstadoSolicitudAnibol(java.lang.Long, com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum)
     */
    @Override
    public void actualizarEstadoSolicitudAnibol(Long idRegistroSolicitudAnibol,
            EstadoSolicitudAnibolEnum estadoSolicitudAnibol) {
        consultasCore.actualizarEstadoSolicitudAnibol(idRegistroSolicitudAnibol, estadoSolicitudAnibol);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#obtenerListadoDetallesSubsidioAsingnado(java.util.List)
     */
    @Override
    public List<DetalleSubsidioAsignadoDTO> obtenerListadoDetallesSubsidioAsingnado(List<Long> listaIdsDetalle){
        return consultasCore.obtenerListaDetallesSubsidioAsingnado(listaIdsDetalle);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentasAdminSubsidio(java.util.List)
     */
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidio(List<Long> listaIds) {
        return consultasCore.consultarCuentasAdminSubsidio(listaIds);
    }

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarDetallesProgramadosPorIdCondicionesBeneficiarios(java.util.List)
	 */
	@Override
	public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorIdCondicionesBeneficiarios(
			List<Long> lstIdsCondicionesBeneficiarios) {
		return consultasCore.consultarDetallesProgramadosPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
	}
	
	@Override
	public List<DetalleSubsidioAsignadoDTO> consultarDetallesProgramadosPorSolicitud(String numeroRadicado,
			List<Long> lstIdsCondicionesBeneficiarios) {
		return consultasCore.consultarDetallesProgramadosPorIdCondicionesBeneficiariosYRadicado(lstIdsCondicionesBeneficiarios, numeroRadicado);
	}
	

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarCuentaAdministradorSubsidio(com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO)
	 */
	@Override
	public void actualizarCuentaAdministradorSubsidio(CuentaAdministradorSubsidioDTO cuenta) {
		consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
	}

	/**
	 * (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarDetalleSubsidioAsignado(com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO)
	 */
	@Override
	public void actualizarDetalleSubsidioAsignado(DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO) {
		consultasCore.actualizarDetalleSubsidioAsignado(detalleSubsidioAsignadoDTO);
	}
	
	/**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRegistroDispersionAnibol()
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarLiquidacionFallecimientoAnibol() {
        return consultasCore.consultarLiquidacionFallecimientoAnibol();
    }

	@Override
	public void marcarAplicadoCuentasLiqFallecimiento(List<Long> listaIdsAdminSubsidio) {
		consultasCore.aplicarCuentasLiqFallecimiento(listaIdsAdminSubsidio);
	}

	/** (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#procesarCapturaResultadoReexpedicionBloqueo(java.util.List)
	 */
	@Override
	@Asynchronous
	public void procesarCapturaResultadoReexpedicionBloqueo(List<ResultadoReexpedicionBloqueoInDTO> listaConsulta) {		
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#persistirRegistroInconsistenciaTarjeta(com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta)
	 */
	@Override
	public void persistirRegistroInconsistenciaTarjeta(RegistroInconsistenciaTarjeta registroInconsistencia){
		consultasCore.persistirRegistroInconsistencia(registroInconsistencia);
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#realizarComparacionSaldoTarjetas(java.lang.String, java.lang.Long, java.math.BigDecimal)
	 */
	@Override
	public ComparacionSaldoTarjetaEnum realizarComparacionSaldoTarjetas(String numeroTarjeta, Long idPersona, BigDecimal saldoNuevaTarjeta){
		BigDecimal saldoTarjetaGenesys = consultasCore.consultarSaldoTarjetaGenesys(numeroTarjeta, idPersona);
		
		if (saldoTarjetaGenesys == null || saldoNuevaTarjeta == null) {
        return ComparacionSaldoTarjetaEnum.IGUALES_CERO;
        } else if (BigDecimal.ZERO.compareTo(saldoTarjetaGenesys) == 0 && BigDecimal.ZERO.compareTo(saldoNuevaTarjeta) == 0) {
            return ComparacionSaldoTarjetaEnum.IGUALES_CERO;
        } else if (saldoTarjetaGenesys.compareTo(saldoNuevaTarjeta) == 0) {
            return ComparacionSaldoTarjetaEnum.IGUAL;
        } else if (saldoTarjetaGenesys.compareTo(saldoNuevaTarjeta) > 0) {
            return ComparacionSaldoTarjetaEnum.MAYOR;
        } else {
            return ComparacionSaldoTarjetaEnum.MENOR;
        }

        /* 
		if(BigDecimal.ZERO.compareTo(saldoTarjetaGenesys) == 0 && BigDecimal.ZERO.compareTo(saldoNuevaTarjeta) == 0){
			return ComparacionSaldoTarjetaEnum.IGUALES_CERO;
		}
		else{
			if(saldoTarjetaGenesys.compareTo(saldoNuevaTarjeta) == 0){
				return ComparacionSaldoTarjetaEnum.IGUAL;
			}
			else if(saldoTarjetaGenesys.compareTo(saldoNuevaTarjeta) > 0){
				return ComparacionSaldoTarjetaEnum.MAYOR;
			}
			else{
				return ComparacionSaldoTarjetaEnum.MENOR;
			}
		}*/
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarCuentasAdministradorSubAbono(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<CuentaAdministradorSubsidio> consultarCuentasAdministradorSubAbono(String numeroTarjeta, Long idPersona){
		return consultasCore.consultarCuentasAdministradorSubAbono(numeroTarjeta, idPersona);
	} 
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarInfoPersonaReexpedicion(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public InfoPersonaReexpedicionDTO consultarInfoPersonaReexpedicion(String tipoIdentificacion, String identificacion, String numeroTarjeta){
		return consultasCore.consultarInfoPersonaReexpedicion(tipoIdentificacion, identificacion, numeroTarjeta);
	}

    @Override
    public InfoPersonaExpedicionDTO consultarInfoPersonaExpedicion(String tipoIdentificacion, String identificacion) {
        return consultasCore.consultarInfoPersonaExpedicion(tipoIdentificacion, identificacion);
    }

    /* (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarInfoPersonaExpedicionValidaciones(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<InfoPersonaExpedicionValidacionesDTO> consultarInfoPersonaExpedicionValidaciones(String tipoIdentificacion, String identificacion) {
        return consultasCore.consultarInfoPersonaExpedicionValidaciones(tipoIdentificacion, identificacion);
    }

	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarGruposTrabajadorMedioTarjeta(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String)
	 */
	@Override
	public List<GruposMedioTarjetaDTO> consultarGruposTrabajadorMedioTarjeta(TipoIdentificacionEnum tipoIdentificacion, String identificacion, String numeroTarjeta){
		return consultasCore.consultarGruposTrabajadorMedioTarjeta(tipoIdentificacion, identificacion, numeroTarjeta);
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#bloquearTarjeta(java.lang.String)
	 */
	@Override
	public void bloquearTarjeta(String numeroTarjeta){
		consultasCore.bloquearTarjeta(numeroTarjeta);
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarMovimientosCasoUno(java.lang.Long, com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void registrarMovimientosCasoUno(Long idPersona, ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, UserDTO userDTO){
		List<CuentaAdministradorSubsidio> cuentas = consultasCore.consultarCuentasAdministradorSubAbono(resultadoReexpedicion.getNumeroTarjeta(), idPersona);
		procesarMovimientosCasoUno(userDTO, cuentas);
	}

	private void procesarMovimientosCasoUno(UserDTO userDTO, List<CuentaAdministradorSubsidio> cuentas) {
        logger.info("Brandon---- procesarMovimientosCasoUno(UserDTO userDTO, List<CuentaAdministradorSubsidio> cuentas)");
        logger.info("Brandon---- Cuentas: "+cuentas.toString());
        cuentas.stream().forEach(cuentaOriginal -> {
            //pasos 1 y 2
            CuentaAdministradorSubsidio cuentaAnulacion = crearCuentaAnulacionYModificarCuentaOriginal(cuentaOriginal, userDTO != null ? userDTO.getNombreUsuario() : null);
            //pasos 3 y 4
            List<DetalleSubsidioAsignadoDTO> nuevosDetallesSubsidioAsignado = crearDetallesSubsidioAsignadosYModificarDetallesOriginales(cuentaOriginal, cuentaAnulacion, userDTO != null ? userDTO.getNombreUsuario() : null);
            //paso 5
            List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados = crearRegistroTransaccionAbonoAgrupado(cuentaOriginal, cuentaAnulacion, nuevosDetallesSubsidioAsignado, userDTO != null ? userDTO.getNombreUsuario() : null);
            //paso 6
            actualizarDatosDetallesSubsidioAsignado(nuevosDetallesSubsidioAsignado, infoDetallesSubsidioAgrupados, userDTO != null ? userDTO.getNombreUsuario() : null);
        });
	}
	
	private void actualizarDatosDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> nuevosDetallesSubsidioAsignado,
			List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados, String nombreUsuario) {
        logger.info("actualizarDatosDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> nuevosDetallesSubsidioAsignado, List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados, String nombreUsuario)");
        logger.info("Brandon---- nuevosDetallesSubsidioAsignado ");
        nuevosDetallesSubsidioAsignado.stream().forEach(System.out::println);
        logger.info("Brandon---- infoDetallesSubsidioAgrupados ");
        infoDetallesSubsidioAgrupados.stream().forEach(System.out::println);
        consultasCore.actualizarDatosDetallesSubsidioAsignado(nuevosDetallesSubsidioAsignado, infoDetallesSubsidioAgrupados, nombreUsuario);
	}

	private List<InfoDetallesSubsidioAgrupadosDTO> crearRegistroTransaccionAbonoAgrupado(CuentaAdministradorSubsidio cuentaOriginal, CuentaAdministradorSubsidio cuentaAnulacion,
			List<DetalleSubsidioAsignadoDTO> detallesSubsidioReemplazo, String nombreUsuario) {
        logger.info("Brandon---- crearRegistroTransaccionAbonoAgrupado(CuentaAdministradorSubsidio cuentaOriginal, CuentaAdministradorSubsidio cuentaAnulacion, List<DetalleSubsidioAsignadoDTO> detallesSubsidioReemplazo, String nombreUsuario)");
        List<Long> idsDetallesSubsidioAsignado = new ArrayList<>();
        logger.info("Brandon---- detallesSubsidioReemplazo "+ detallesSubsidioReemplazo.toString());
        detallesSubsidioReemplazo.stream().forEach(detalle -> idsDetallesSubsidioAsignado.add(detalle.getIdDetalleSubsidioAsignado()));
        logger.info("Brandon---- idsDetallesSubsidioAsignado: "+idsDetallesSubsidioAsignado);
        List<InfoDetallesSubsidioAgrupadosDTO> infoDetallesSubsidioAgrupados = consultasCore.obtenerInfoDetallesAgrupados(idsDetallesSubsidioAsignado);
        logger.info("Brandon---- CuentaAdministradorSubsidio cuenta original: "+cuentaOriginal.getValorOriginalTransaccion());
        logger.info("Brandon---- CuentaAdministradorSubsidio cuenta anulacion: "+cuentaAnulacion.getValorOriginalTransaccion());
        infoDetallesSubsidioAgrupados.stream().forEach( detalleAgrupado -> {
            System.out.println("Valor total "+ detalleAgrupado.getValorTotal());
            System.out.println("getIdAdministradorSubsidio " + detalleAgrupado.getIdAdministradorSubsidio());
            System.out.println("getIdCuentaAdministradorSubsidio " + detalleAgrupado.getIdCuentaAdministradorSubsidio());
            System.out.println("getIdGrupoFamiliar " + detalleAgrupado.getIdGrupoFamiliar());
            detalleAgrupado.setIdCuentaAdministradorSubsidio(registrarCuentaContrapartida(detalleAgrupado, cuentaOriginal, cuentaAnulacion, nombreUsuario, detallesSubsidioReemplazo.get(0)));
        });

        return infoDetallesSubsidioAgrupados;
	}

	private Long registrarCuentaContrapartida(InfoDetallesSubsidioAgrupadosDTO detalleAgrupado, CuentaAdministradorSubsidio cuentaOriginal,
			CuentaAdministradorSubsidio cuentaAnulacion, String nombreUsuario, DetalleSubsidioAsignadoDTO detalleReemplazo) {

        Date fechaActual = Calendar.getInstance().getTime();

        CuentaAdministradorSubsidio cuentaContrapartida = new CuentaAdministradorSubsidio();
        cuentaContrapartida.setFechaHoraCreacionRegistro(fechaActual);
        cuentaContrapartida.setUsuarioCreacionRegistro(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaContrapartida.setTipoTransaccionSubsidio(TipoTransaccionSubsidioMonetarioEnum.ABONO);
        cuentaContrapartida.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.APLICADO);
        cuentaContrapartida.setMedioDePagoTransaccion(detalleReemplazo.getMedioDePago());
        cuentaContrapartida.setOrigenTransaccion(OrigenTransaccionEnum.ANULACION);
        cuentaContrapartida.setValorOriginalTransaccion(cuentaOriginal.getValorOriginalTransaccion());
        cuentaContrapartida.setIdAdministradorSubsidio(detalleAgrupado.getIdAdministradorSubsidio());
        cuentaContrapartida.setNumeroTarjetaAdmonSubsidio(detalleReemplazo.getNumeroTarjetaAdminSubsidio());
        cuentaContrapartida.setCodigoBancoAdmonSubsidio(detalleReemplazo.getCodigoBancoAdminSubsidio());
        cuentaContrapartida.setTipoCuentaAdmonSubsidio(detalleReemplazo.getTipoCuentaAdminSubsidio());
        cuentaContrapartida.setNumeroCuentaAdmonSubsidio(detalleReemplazo.getNumeroCuentaAdminSubsidio());

        cuentaContrapartida.setTipoIdentificacionTitularCuentaAdmonSubsidio(null);
        cuentaContrapartida.setNumeroIdentificacionTitularCuentaAdmonSubsidio(null);
        cuentaContrapartida.setNombreTitularCuentaAdmonSubsidio(null);

        cuentaContrapartida.setFechaHoraTransaccion(cuentaOriginal.getFechaHoraTransaccion());
        cuentaContrapartida.setUsuarioTransaccion(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaContrapartida.setValorRealTransaccion(cuentaOriginal.getValorRealTransaccion());
        cuentaContrapartida.setFechaHoraUltimaModificacion(fechaActual);
        cuentaContrapartida.setUsuarioUltimaModificacion(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaContrapartida.setIdTransaccionOriginal(cuentaOriginal.getIdTransaccionOriginal());
        cuentaContrapartida.setIdSitioDePago(cuentaOriginal.getIdSitioDePago());

        cuentaContrapartida.setIdMedioDePago(cuentaOriginal.getIdMedioDePago());
        logger.info("Brandon---- cuentaContrapartida: "+cuentaContrapartida);
        CuentaAdministradorSubsidioDTO cuentaContrapartidaDTO = new CuentaAdministradorSubsidioDTO(cuentaContrapartida);
        logger.info("Brandon---- cuentaContrapartidaDTO "+cuentaContrapartidaDTO);
        return consultasCore.crearCuentaAdministradorSubsidio(cuentaContrapartidaDTO);
		
	}

	private CuentaAdministradorSubsidio crearCuentaAnulacionYModificarCuentaOriginal(CuentaAdministradorSubsidio cuentaOriginal, String nombreUsuario) {
        logger.info("Brandon---- crearCuentaAnulacionYModificarCuentaOriginal(CuentaAdministradorSubsidio cuentaOriginal, String nombreUsuario)");
        Date fechaActual = Calendar.getInstance().getTime();

        CuentaAdministradorSubsidio cuentaAnulacion = new CuentaAdministradorSubsidio();
        cuentaAnulacion.setFechaHoraCreacionRegistro(fechaActual);
        cuentaAnulacion.setUsuarioCreacionRegistro(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaAnulacion.setTipoTransaccionSubsidio(TipoTransaccionSubsidioMonetarioEnum.ANULACION);
        cuentaAnulacion.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.GENERADO);
        cuentaAnulacion.setMedioDePagoTransaccion(TipoMedioDePagoEnum.TARJETA);
        cuentaAnulacion.setOrigenTransaccion(OrigenTransaccionEnum.ANULACION);
        cuentaAnulacion.setValorOriginalTransaccion(cuentaOriginal.getValorOriginalTransaccion());
        cuentaAnulacion.setIdAdministradorSubsidio(cuentaOriginal.getIdAdministradorSubsidio());
        cuentaAnulacion.setNumeroTarjetaAdmonSubsidio(cuentaOriginal.getNumeroTarjetaAdmonSubsidio());
        cuentaAnulacion.setFechaHoraTransaccion(fechaActual);
        cuentaAnulacion.setUsuarioTransaccion(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaAnulacion.setValorRealTransaccion(cuentaOriginal.getValorOriginalTransaccion().negate());
        cuentaAnulacion.setFechaHoraUltimaModificacion(fechaActual);
        cuentaAnulacion.setUsuarioUltimaModificacion(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaAnulacion.setIdTransaccionOriginal(cuentaOriginal.getIdTransaccionOriginal());
        cuentaAnulacion.setIdSitioDePago(cuentaOriginal.getIdSitioDePago());
        cuentaAnulacion.setIdMedioDePago(cuentaOriginal.getIdMedioDePago());

        CuentaAdministradorSubsidioDTO cuentaAnulacionDTO = new CuentaAdministradorSubsidioDTO(cuentaAnulacion);

        Long idCuentaAnulacion = consultasCore.crearCuentaAdministradorSubsidio(cuentaAnulacionDTO);

        cuentaOriginal.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.ANULADO);
        cuentaOriginal.setFechaHoraUltimaModificacion(fechaActual);
        cuentaOriginal.setUsuarioUltimaModificacion(nombreUsuario != null ? nombreUsuario : "Sistema");
        cuentaOriginal.setIdTransaccionOriginal(idCuentaAnulacion);

        CuentaAdministradorSubsidioDTO cuentaOriginalDTO = new CuentaAdministradorSubsidioDTO(cuentaOriginal);

        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaOriginalDTO);

        if(cuentaAnulacion.getIdCuentaAdministradorSubsidio() == null){
            cuentaAnulacion.setIdCuentaAdministradorSubsidio(idCuentaAnulacion);
        }

        return cuentaAnulacion;
	}
	
	private List<DetalleSubsidioAsignadoDTO> crearDetallesSubsidioAsignadosYModificarDetallesOriginales(CuentaAdministradorSubsidio cuenta, CuentaAdministradorSubsidio cuentaAnulacion, String nombreUsuario) {

        logger.info("Brandon---- crearDetallesSubsidioAsignadosYModificarDetallesOriginales(CuentaAdministradorSubsidio cuenta, CuentaAdministradorSubsidio cuentaAnulacion, String nombreUsuario)");
        logger.info("Cuenta: "+ cuenta.toString());
        logger.info("Cuenta Anulacion: "+cuentaAnulacion);
        logger.info("Nombre usuario: "+nombreUsuario);
        List<DetalleSubsidioAsignadoDTO> detallesSubsidio = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());
        logger.info("Brandon---- List<DetalleSubsidioAsignadoDTO> detallesSubsidio: ");
        detallesSubsidio.stream().forEach(System.out::println);
        List<DetalleSubsidioAsignadoDTO> listaDetallesReemplazo = new ArrayList<>();

        detallesSubsidio.stream().forEach( detalleOriginal -> {

            Date fechaActual = Calendar.getInstance().getTime();

            detalleOriginal.setEstado(EstadoSubsidioAsignadoEnum.ANULADO_REEMPLAZADO);
            detalleOriginal.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.TRASLADO_DE_SALDO);
            detalleOriginal.setFechaTransaccionAnulacion(fechaActual);
            detalleOriginal.setUsuarioTransaccionAnulacion(nombreUsuario != null ? nombreUsuario : "Sistema");
            detalleOriginal.setFechaHoraUltimaModificacion(fechaActual);
            detalleOriginal.setUsuarioUltimaModificacion(nombreUsuario != null ? nombreUsuario : "Sistema");

            consultasCore.actualizarDetalleSubsidioAsignado(detalleOriginal);

            DetalleSubsidioAsignadoDTO detalleReemplazo = new DetalleSubsidioAsignadoDTO();

            detalleReemplazo.setFechaHoraCreacion(fechaActual);
            detalleReemplazo.setUsuarioCreador(nombreUsuario != null ? nombreUsuario : "Sistema");
            detalleReemplazo.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
            detalleReemplazo.setPeriodoLiquidado(detalleOriginal.getPeriodoLiquidado());
            detalleReemplazo.setIdEmpleador(detalleOriginal.getIdEmpleador());
            detalleReemplazo.setIdAfiliadoPrincipal(detalleOriginal.getIdAfiliadoPrincipal());
            detalleReemplazo.setIdGrupoFamiliar(detalleOriginal.getIdGrupoFamiliar());
            detalleReemplazo.setIdBeneficiarioDetalle(detalleOriginal.getIdBeneficiarioDetalle());
            detalleReemplazo.setIdAdministradorSubsidio(detalleOriginal.getIdAdministradorSubsidio());
            detalleReemplazo.setMedioDePago(detalleOriginal.getMedioDePago());
            detalleReemplazo.setNumeroTarjetaAdminSubsidio(detalleOriginal.getNumeroTarjetaAdminSubsidio());
            detalleReemplazo.setCodigoBancoAdminSubsidio(detalleOriginal.getCodigoBancoAdminSubsidio());
            detalleReemplazo.setTipoCuentaAdminSubsidio(detalleOriginal.getTipoCuentaAdminSubsidio());
            detalleReemplazo.setNumeroCuentaAdminSubsidio(detalleOriginal.getNumeroCuentaAdminSubsidio());
            detalleReemplazo.setTipoIdentificacionTitularCuentaAdminSubsidio(detalleOriginal.getTipoIdentificacionTitularCuentaAdminSubsidio());
            detalleReemplazo.setNumeroIdentificacionTitularCuentaAdminSubsidio(detalleOriginal.getNumeroIdentificacionTitularCuentaAdminSubsidio());
            detalleReemplazo.setNombreTitularCuentaAdminSubsidio(detalleOriginal.getNombreTitularCuentaAdminSubsidio());
            detalleReemplazo.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.ANULACION);
            detalleReemplazo.setIdSolicitudLiquidacionSubsidio(detalleOriginal.getIdSolicitudLiquidacionSubsidio());
            detalleReemplazo.setFechaLiquidacionAsociada(detalleOriginal.getFechaLiquidacionAsociada());
            detalleReemplazo.setTipoLiquidacionSubsidio(detalleOriginal.getTipoLiquidacionSubsidio());
            detalleReemplazo.setTipoCuotaSubsidio(detalleOriginal.getTipoCuotaSubsidio());
            detalleReemplazo.setValorSubsidioMonetario(detalleOriginal.getValorSubsidioMonetario());
            detalleReemplazo.setTipoDescuento(detalleOriginal.getTipoDescuento());
            detalleReemplazo.setValorDescuento(detalleOriginal.getValorDescuento());
            detalleReemplazo.setValorOriginalAbonado(detalleOriginal.getValorOriginalAbonado());
            detalleReemplazo.setValorTotal(detalleOriginal.getValorTotal());
            detalleReemplazo.setIdRegistroOriginalRelacionado(detalleOriginal.getIdRegistroOriginalRelacionado());
            detalleReemplazo.setFechaHoraUltimaModificacion(detalleOriginal.getFechaHoraUltimaModificacion());
            detalleReemplazo.setUsuarioUltimaModificacion(nombreUsuario != null ? nombreUsuario : "Sistema");
            detalleReemplazo.setIdCuentaAdministradorSubsidio(cuentaAnulacion.getIdCuentaAdministradorSubsidio());
            logger.info("Brandon---- crearDetallesSubsidioAsignadosYModificarDetallesOriginales detalleOriginal.getIdResultadoValidacionLiquidacion(): "+detalleOriginal.getIdResultadoValidacionLiquidacion());
            detalleReemplazo.setIdResultadoValidacionLiquidacion(detalleOriginal.getIdResultadoValidacionLiquidacion());


            Long idDetalleSubsidioReemplazoCreado = consultasCore.persistirDetalleSubsidioAsignadoObtenerId(detalleReemplazo);

            detalleReemplazo.setIdDetalleSubsidioAsignado(idDetalleSubsidioReemplazoCreado);

            listaDetallesReemplazo.add(detalleReemplazo);
        });

        return listaDetallesReemplazo;
	}

	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarMovimientosCasoDos(java.lang.Long, com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO, com.asopagos.rest.security.dto.UserDTO)
	 */
	@Override
	public void registrarMovimientosCasoDos(Long idPersona, ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, UserDTO userDTO){
        List<CuentaAdministradorSubsidio> cuentas = consultasCore.consultarCuentasAdministradorSubAbono(resultadoReexpedicion.getNumeroTarjeta(), idPersona);
        logger.info("Brandon---- registrarMovimientosCasoDos" + "idPersona: "+idPersona+" resultadoReexpedicion"+" userDTO ");
        System.out.println(userDTO.toString());
        logger.info("Brandon---- Cuentas: "+cuentas);
        if(cuentas != null && !cuentas.isEmpty()){

            BigDecimal A = consultasCore.consultarSaldoTarjetaGenesys(resultadoReexpedicion.getNumeroTarjeta(), idPersona);
            BigDecimal B = resultadoReexpedicion.getSaldoNuevaTarjeta();
            BigDecimal C = A.subtract(B);
            BigDecimal D;
            BigDecimal E = C.negate();

            List<CuentaAbonoMarcadoDTO> listaAbonosMarcados = new ArrayList<>();
            List<CuentaAdministradorSubsidio> cuentasNoMarcadas = new ArrayList<>();

            //Paso 1: Actualizar cronológicamente los registros con tipo de transacción “Abono”
            //que fueron utilizados para calcular el saldo.
            logger.info("Brandon---- Cuentas.size: "+cuentas.size());
            CuentaAdministradorSubsidio cuentaAbono = new CuentaAdministradorSubsidio();
            DetalleSubsidioAsignadoDTO detalleFraccionado = new DetalleSubsidioAsignadoDTO();
            for (int i = 0; i < cuentas.size(); i++) {
                logger.info("Brandon---- iteracion numero: "+i);
                cuentaAbono = cuentas.get(i);
                logger.info("Brandon---- cuentas.get ");
                cuentas.forEach(System.out::println);

                D = cuentaAbono.getValorOriginalTransaccion();
                logger.info("Brandon---- "+"A: "+A+" B: "+B+" C: "+C+" D: "+D+" E: "+E);
                if(E.compareTo(BigDecimal.ZERO) < 0){
                    logger.info("Brandon---- E.compareTo(BigDecimal.ZERO) < 0");
                    logger.info("Brandon---- Iteracion: "+i+ " E: "+E);
                    listaAbonosMarcados.add(new CuentaAbonoMarcadoDTO(cuentaAbono.getIdCuentaAdministradorSubsidio(), cuentaAbono, "excluido"));
                    E = E.add(D);
                }
                if(E.compareTo(BigDecimal.ZERO) == 0){
                    logger.info("Brandon---- E.compareTo(BigDecimal.ZERO) == 0");
                    logger.info("Brandon---- Iteracion: "+i+ " E: "+E);
                    listaAbonosMarcados.add(new CuentaAbonoMarcadoDTO(cuentaAbono.getIdCuentaAdministradorSubsidio(), cuentaAbono, "excluido"));

                    //se procede al paso 4 del caso 2
                    if(i < cuentas.size()-1){
                        logger.info("Brandon----cuentas.size()-1");
                        logger.info("Brandon---- Iteracion: "+i+ " E: "+E);
                        List<CuentaAdministradorSubsidio> cuentasCasoUno = cuentas.subList(i, cuentas.size());
                        procesarMovimientosCasoUno(userDTO, cuentasCasoUno);
                        break;
                    }
                }
                if(E.compareTo(BigDecimal.ZERO) > 0){
                    logger.info("Brandon---- E.compareTo(BigDecimal.ZERO) > 0");
                    Date fechaActual = Calendar.getInstance().getTime();
                    logger.info("Brandon---- Fecha: "+fechaActual);
                    String nombreUsuario = userDTO.getNombreUsuario();
                    cuentaAbono.setValorRealTransaccion(cuentaAbono.getValorOriginalTransaccion().subtract(E));
                    cuentaAbono.setFechaHoraUltimaModificacion(fechaActual);
                    cuentaAbono.setUsuarioUltimaModificacion(nombreUsuario != null ? userDTO.getNombreUsuario() : "Sistema");

                    CuentaAdministradorSubsidioDTO cuentaAbonoDTO = new CuentaAdministradorSubsidioDTO(cuentaAbono);
                    consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAbonoDTO);

                    listaAbonosMarcados.add(new CuentaAbonoMarcadoDTO(cuentaAbono.getIdCuentaAdministradorSubsidio(), cuentaAbono, "excluido"));


                    CuentaAdministradorSubsidio abonoOrigenFraccionamiento = new CuentaAdministradorSubsidio();
                    abonoOrigenFraccionamiento.setFechaHoraCreacionRegistro(fechaActual);
                    abonoOrigenFraccionamiento.setUsuarioCreacionRegistro(nombreUsuario != null ? userDTO.getNombreUsuario() : "Sistema");
                    logger.info("Brandon---- "+abonoOrigenFraccionamiento.getUsuarioCreacionRegistro());
                    abonoOrigenFraccionamiento.setTipoTransaccionSubsidio(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                    abonoOrigenFraccionamiento.setEstadoTransaccionSubsidio(EstadoTransaccionSubsidioEnum.GENERADO);
                    abonoOrigenFraccionamiento.setMedioDePagoTransaccion(cuentaAbono.getMedioDePagoTransaccion());
                    abonoOrigenFraccionamiento.setOrigenTransaccion(OrigenTransaccionEnum.AJUSTE_TRASLADO_SALDO);
                    abonoOrigenFraccionamiento.setValorOriginalTransaccion(E);
                    abonoOrigenFraccionamiento.setIdAdministradorSubsidio(cuentaAbono.getIdAdministradorSubsidio());
                    abonoOrigenFraccionamiento.setNumeroTarjetaAdmonSubsidio(cuentaAbono.getNumeroTarjetaAdmonSubsidio());
                    abonoOrigenFraccionamiento.setFechaHoraTransaccion(cuentaAbono.getFechaHoraTransaccion());
                    abonoOrigenFraccionamiento.setUsuarioTransaccion(cuentaAbono.getUsuarioTransaccion());
                    abonoOrigenFraccionamiento.setValorRealTransaccion(E);
                    abonoOrigenFraccionamiento.setFechaHoraUltimaModificacion(fechaActual);
                    String usuarioUltimaModificacion = userDTO.getNombreUsuario();
                    abonoOrigenFraccionamiento.setUsuarioUltimaModificacion(usuarioUltimaModificacion != null ? userDTO.getNombreUsuario() : "Sistema");
                    abonoOrigenFraccionamiento.setIdTransaccionOriginal(cuentaAbono.getIdTransaccionOriginal());
                    abonoOrigenFraccionamiento.setIdSitioDePago(cuentaAbono.getIdSitioDePago());
                    logger.info("Brandon---- "+cuentaAbono.getIdMedioDePago());
                    abonoOrigenFraccionamiento.setIdMedioDePago(cuentaAbono.getIdMedioDePago());
                    logger.info("Brandon---- "+abonoOrigenFraccionamiento.getIdMedioDePago());

                    CuentaAdministradorSubsidioDTO abonoOrigenFraccionamientoDTO = new CuentaAdministradorSubsidioDTO(abonoOrigenFraccionamiento);

                    Long idCuentaAbonoOrigenFraccionamiento = consultasCore.crearCuentaAdministradorSubsidio(abonoOrigenFraccionamientoDTO);
                    logger.info("Brandon---- idCuentaAbonoOrigenFraccionamiento: "+idCuentaAbonoOrigenFraccionamiento);
                    abonoOrigenFraccionamientoDTO.setIdCuentaAdministradorSubsidio(idCuentaAbonoOrigenFraccionamiento);
                    abonoOrigenFraccionamientoDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);

                    consultasCore.actualizarCuentaAdministradorSubsidio(abonoOrigenFraccionamientoDTO);

                    abonoOrigenFraccionamientoDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);

                    consultasCore.actualizarCuentaAdministradorSubsidio(abonoOrigenFraccionamientoDTO);

                    cuentasNoMarcadas.add(cuentaAbono);

                    List<DetalleSubsidioAsignadoDTO> detallesSubsidio = consultasCore.consultarDetallesSubsidiosAsignadosAsociadosAbonoOrdenados(cuentaAbono.getIdCuentaAdministradorSubsidio());
                    logger.info("Brandon---- detalleSubsidio "+detallesSubsidio);
                    logger.info("Brandon---- cuentaAbono.getValorRealTransaccion() "+cuentaAbono.toString());
                    BigDecimal F = cuentaAbono.getValorRealTransaccion();
                    BigDecimal G;
                    BigDecimal H = F;
                    //DetalleSubsidioAsignadoDTO detalleFraccionado = new DetalleSubsidioAsignadoDTO();
                    for (int j = 0; j < detallesSubsidio.size(); j++) {
                        detalleFraccionado = detallesSubsidio.get(i);
                        logger.info("Brandon---- cuentas.get "+cuentas.get(i));
                        G = detalleFraccionado.getValorTotal();

                        H = H.subtract(G);
                        logger.info("Brandon---- F: "+F+" G: "+G+" H: "+H);
                        if(F.compareTo(BigDecimal.ZERO) > 0){
                            logger.info("Brandon---- F.compareTo(BigDecimal.ZERO) > 0");
                            continue;
                        }
                        if(F.compareTo(BigDecimal.ZERO) == 0){
                            logger.info("Brandon---- F.compareTo(BigDecimal.ZERO) == 0");
                            logger.info("Brandon cuentas.size()-1: "+(cuentas.size()-1));
                            //se procede al paso 4 del caso 2
                            if(i < cuentas.size()-1){
                                List<CuentaAdministradorSubsidio> cuentasCasoUno = cuentas.subList(i, cuentas.size());
                                procesarMovimientosCasoUno(userDTO, cuentasCasoUno);
                                break;
                            }
                        }
                        if(F.compareTo(BigDecimal.ZERO) < 0){
                            logger.info("Brandon---- F.compareTo(BigDecimal.ZERO) < 0");
                            detalleFraccionado.setValorTotal(detalleFraccionado.getValorTotal().add(H));
                            detalleFraccionado.setFechaHoraUltimaModificacion(fechaActual);
                            detalleFraccionado.setUsuarioUltimaModificacion(nombreUsuario != null ? userDTO.getNombreUsuario() : "Sistema");

                            consultasCore.actualizarDetalleSubsidioAsignado(detalleFraccionado);

                            DetalleSubsidioAsignadoDTO detalleOrigenFraccionamiento = new DetalleSubsidioAsignadoDTO();

                            detalleOrigenFraccionamiento.setFechaHoraCreacion(fechaActual);
                            detalleOrigenFraccionamiento.setUsuarioCreador(detalleFraccionado.getUsuarioCreador());
                            detalleOrigenFraccionamiento.setEstado(EstadoSubsidioAsignadoEnum.DERECHO_ASIGNADO);
                            detalleOrigenFraccionamiento.setPeriodoLiquidado(detalleFraccionado.getPeriodoLiquidado());
                            detalleOrigenFraccionamiento.setIdEmpleador(detalleFraccionado.getIdEmpleador());
                            detalleOrigenFraccionamiento.setIdAfiliadoPrincipal(detalleFraccionado.getIdAfiliadoPrincipal());
                            detalleOrigenFraccionamiento.setIdGrupoFamiliar(detalleFraccionado.getIdGrupoFamiliar());
                            detalleOrigenFraccionamiento.setIdBeneficiarioDetalle(detalleFraccionado.getIdBeneficiarioDetalle());
                            detalleOrigenFraccionamiento.setIdAdministradorSubsidio(detalleFraccionado.getIdAdministradorSubsidio());
                            detalleOrigenFraccionamiento.setMedioDePago(detalleFraccionado.getMedioDePago());
                            detalleOrigenFraccionamiento.setNumeroTarjetaAdminSubsidio(detalleFraccionado.getNumeroTarjetaAdminSubsidio());
                            detalleOrigenFraccionamiento.setCodigoBancoAdminSubsidio(detalleFraccionado.getCodigoBancoAdminSubsidio());
                            detalleOrigenFraccionamiento.setTipoCuentaAdminSubsidio(detalleFraccionado.getTipoCuentaAdminSubsidio());
                            detalleOrigenFraccionamiento.setNumeroCuentaAdminSubsidio(detalleFraccionado.getNumeroCuentaAdminSubsidio());
                            detalleOrigenFraccionamiento.setTipoIdentificacionTitularCuentaAdminSubsidio(detalleFraccionado.getTipoIdentificacionTitularCuentaAdminSubsidio());
                            detalleOrigenFraccionamiento.setNumeroIdentificacionTitularCuentaAdminSubsidio(detalleFraccionado.getNumeroIdentificacionTitularCuentaAdminSubsidio());
                            detalleOrigenFraccionamiento.setNombreTitularCuentaAdminSubsidio(detalleFraccionado.getNombreTitularCuentaAdminSubsidio());
                            detalleOrigenFraccionamiento.setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum.AJUSTE_TRASLADO_SALDO);
                            detalleOrigenFraccionamiento.setIdSolicitudLiquidacionSubsidio(detalleFraccionado.getIdSolicitudLiquidacionSubsidio());
                            detalleOrigenFraccionamiento.setFechaLiquidacionAsociada(detalleFraccionado.getFechaLiquidacionAsociada());
                            detalleOrigenFraccionamiento.setTipoLiquidacionSubsidio(detalleFraccionado.getTipoLiquidacionSubsidio());
                            detalleOrigenFraccionamiento.setTipoCuotaSubsidio(detalleFraccionado.getTipoCuotaSubsidio());
                            detalleOrigenFraccionamiento.setValorSubsidioMonetario(detalleFraccionado.getValorSubsidioMonetario());
                            detalleOrigenFraccionamiento.setTipoDescuento(detalleFraccionado.getTipoDescuento());
                            detalleOrigenFraccionamiento.setValorDescuento(detalleFraccionado.getValorDescuento());
                            detalleOrigenFraccionamiento.setValorOriginalAbonado(detalleFraccionado.getValorOriginalAbonado());
                            detalleOrigenFraccionamiento.setValorTotal(H.abs());
                            detalleOrigenFraccionamiento.setIdRegistroOriginalRelacionado(detalleFraccionado.getIdRegistroOriginalRelacionado());
                            detalleOrigenFraccionamiento.setIdCuentaAdministradorSubsidio(idCuentaAbonoOrigenFraccionamiento);
                            detalleOrigenFraccionamiento.setFechaHoraUltimaModificacion(fechaActual);
                            detalleOrigenFraccionamiento.setUsuarioUltimaModificacion(nombreUsuario != null ? userDTO.getNombreUsuario() : "Sistema");
                            logger.info("Brandon---- detalleFraccionado.getIdResultadoValidacionLiquidacion(): "+detalleFraccionado.getIdResultadoValidacionLiquidacion());
                            detalleOrigenFraccionamiento.setIdResultadoValidacionLiquidacion(detalleFraccionado.getIdResultadoValidacionLiquidacion());

                            Long idDetalleSubsidioReemplazoCreado = consultasCore.persistirDetalleSubsidioAsignadoObtenerId(detalleOrigenFraccionamiento);

                            detalleOrigenFraccionamiento.setIdDetalleSubsidioAsignado(idDetalleSubsidioReemplazoCreado);

                            detalleFraccionado.setIdCuentaAdministradorSubsidio(idCuentaAbonoOrigenFraccionamiento);
                            detalleFraccionado.setFechaHoraUltimaModificacion(Calendar.getInstance().getTime());
                            detalleFraccionado.setUsuarioUltimaModificacion(nombreUsuario != null ? userDTO.getNombreUsuario() : "Sistema");

                            consultasCore.actualizarDetalleSubsidioAsignado(detalleFraccionado);
                        }
                    }
                }
            }
            logger.info("Brandon---- Fuera del bucle");
            logger.info("Brandon---- "+"A: "+A+" B: "+B+" C: "+C+" E: "+E);
            logger.info("Brandon---- listaabonosmarcados");
            listaAbonosMarcados.forEach(System.out::println);
            if(cuentasNoMarcadas.size() > 0){
                logger.info("Brandon---- cuentasNoMarcadas.size() > 0 && !cuentasNoMarcadas.isEmpty()");
                procesarMovimientosCasoUno(userDTO, cuentasNoMarcadas);
            }
        }
	}

	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarRegistroInconsistencia(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<RegistroInconsistenciaTarjeta> consultarRegistroInconsistencia(Long fechaInicio, Long fechaFin){
		return consultasCore.consultarRegistroInconsistencias(fechaInicio, fechaFin);
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#cerrarCasoInconsistenciaTarjeta(java.lang.Long, java.lang.String)
	 */
	@Override
	public void cerrarCasoInconsistenciaTarjeta(Long idRegistroInconsistencia, ResultadoGestionInconsistenciaEnum resultadoGestion, String detalleResolucion){
		consultasCore.cerrarCasoInconsistencia(idRegistroInconsistencia, resultadoGestion, detalleResolucion);
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#consultarHistoricoRegistroInconsistencia(java.lang.Long, java.lang.Long, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum, com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum)
	 */
	@Override
	public List<RegistroInconsistenciaTarjeta> consultarHistoricoRegistroInconsistencia(Long fechaInicial, Long fechaFinal, 
			TipoIdentificacionEnum tipoId, String numeroId, EstadoResolucionInconsistenciaEnum estadoResolucion, TipoNovedadInconsistenciaEnum tipoNovedad){
		return consultasCore.consultarHistoricoRegistroInconsistenciaTarjeta(fechaInicial, fechaFinal, tipoId, numeroId, estadoResolucion, tipoNovedad);
	}

	@Override
	public List<DetalleSubsidioAsignadoDTO> consultarDetallesRetirosAnulacion(Long idCuentaAdmin,
			TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
		return consultasCore.consultaDetallesRetirosAnulacionCuenta(idCuentaAdmin, tipoTransaccion);
	}
	
	   /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#buscarNombreArchivoConsumoTarjetaANIBOL(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
    @Override
    public Boolean consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(String nombreArchivo) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.buscarNombreArchivoConsumoTarjetaANIBOL(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Boolean validacion = consultasCore.consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(nombreArchivo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return validacion;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long InsertRestuladosValidacionCargaManualRetiroTerceroPag(InformacionArchivoDTO informacionArchivoDTO,Long idConvenio,String nombreUsuario,Long idArchivoTerceroPagadorEfectivo){
    	ResultadoValidacionArchivoRetiroDTO resultado = new ResultadoValidacionArchivoRetiroDTO(); 
    	 ArrayList<String[]> lineas;
 		try {
 			
 			consultasCore.InsertRestuladosValidacionCargaManualRetiroTerceroPag(informacionArchivoDTO,idConvenio,nombreUsuario,idArchivoTerceroPagadorEfectivo);
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			throw new TechnicalException(MensajesGeneralConstants.ERROR_EJECUCION_PROCEDIMIENTO_ALMACENADO, e);
 			
 		}
    	return 1L;
    }
	
	 /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#solicitarRetiroConfirmarValorEntregadoSubsidio(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum, java.math.BigDecimal, java.math.BigDecimal,
     *      java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.asopagos.rest.security.dto.UserDTO,
     *      java.lang.String)
     */
    @Override
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTerceroPag(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            TipoMedioDePagoEnum medioDePago, BigDecimal saldoActualSubsidio, BigDecimal valorSolicitado, Long fecha, 
            String idTransaccionTercerPagador, String departamento, String municipio, String usuario, Boolean isVentanilla, String user,
            Long idSitioPago, Long idConvenio) {  
 
        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum,"
                + " String, TipoMedioDePagoEnum, BigDecimal, BigDecimal," + " Long, String, String, String, String, UserDTO, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

       
        final String resultado = "resultado";
        final String error = "error";
        final String idRespuesta = "identificadorRespuesta";
        Map<String, String> respuesta = null;
        Long identificadorRespuesta = null;
        //StringBuilder salida = new StringBuilder();
        //Gson gson = new GsonBuilder().create();
        //Map<String, Object> parametrosEntrada = new HashMap<>();
        //String codigoMunicpioCCF = null;

        //se almacenan los parametros de entrada en un Map
        /*
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdAdmin);
        parametrosEntrada.put("medioDePago", medioDePago);
        parametrosEntrada.put("saldoActualSubsidio", saldoActualSubsidio);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("fecha", fecha);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("departamento", departamento);
        parametrosEntrada.put("municipio", municipio);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("nombreUserDTO", userDTO.getNombreUsuario());
        if (userDTO.getEmail() != null)
            parametrosEntrada.put("emailUserDTO", userDTO.getEmail());

        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();
        String parametrosOUT = null;
        */

        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore
                .consultarRegistrosAbonosParaCalcularSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);
        
        //CuentaAdministradorSubsidioDTO ultimaCuenta = listaCuentaAdminSubsidio.get(listaCuentaAdminSubsidio.size()-1);
        /*
         * SE VALIDA EN MASIVO
        respuesta = validarSolicitudRetiroSubsidio(saldoActualSubsidio, valorSolicitado, idTransaccionTercerPagador, medioDePago, userDTO,
                listaCuentaAdminSubsidio, resultado, error, idRespuesta, parametrosIN,
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO);

        //si el objeto es diferente de null es porque ha ocurrido un error y se retorna dicho resultado.
        if (respuesta != null) {
            return respuesta;
        }
	
		*/
        respuesta = new HashMap<>();

        //se registra la operación
        /*
         PENDIENTE SABER SI SE NECESITA REGISTRAR OPERACION
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO, parametrosIN, userDTO.getNombreUsuario(),
                listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio());
		
		*/
        
        /*
         * PENDIENTE DEFINIR SI ASÍ SE BUSCA EL SITIO DE PAGO DE LA CAJA
        if (municipio == null) {
            //Se establece por defecto el municpío de la CCF por parametro del sistema
            codigoMunicpioCCF = (String) CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID);
            municipio = consultasCore.buscarMunicipioPorCodigo(codigoMunicpioCCF);
        }
        */

        String usuarioRetiro = usuario;



        //se realiza el proceso de retiro 
        Long idRetiro = registrarTransaccionRetiroTercPagador(listaCuentaAdminSubsidio, saldoActualSubsidio, valorSolicitado, fecha,
                idTransaccionTercerPagador, idSitioPago, usuarioRetiro, isVentanilla ? null : null, null,"", idConvenio);

        if (idRetiro == null || idRetiro == -1) {
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "No se encontro un Sitio de pago asociado");
            return respuesta;
        }

        String codDepartamento = consultasCore.consultarDepartamentoPorMunicipio(municipio);

        if(codDepartamento.isEmpty() || !departamento.equals(codDepartamento)){
            //respuesta no existosa, no se encontro id sitio de pago asociado
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error, "No se encontró un sitio de pago relacionado");
            return respuesta;
        }
        //PASOS PARA LA CONFIRMACIÓN DEL RETIRO

        //se obtiene la lista de los abonos que fueron solicitados para el retiro por un administrador de subsidio con un identificador de transacción de tercero pagador
        List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidio = consultasCore
                .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(tipoIdAdmin, numeroIdAdmin, idTransaccionTercerPagador);

        //Se valida que si hayan abonos con estado 'SOLICITADO' de una solicitud de retiro previo.
        if (listaCuentasAdminSubsidio == null) {
            //se registra la operación
        	/*
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                    TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_PERSONA_AUTORIZADA_CONFIRMAR_VALOR_ENTREGADO, parametrosIN,
                    userDTO.getNombreUsuario(), null);
                    */
            respuesta.put(resultado, String.valueOf(false));
            respuesta.put(error,
                    "No se puede realizar la confirmación del valor entregado. El administrador no ha realizado un retiro previo ó el identificador de transacción del tercero pagador es incorrecto");
            respuesta.put(idRespuesta, String.valueOf(identificadorRespuesta));

            /*
            salida = new StringBuilder();
            salida.append(gson.toJson(respuesta));
            parametrosOUT = salida.toString();
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT);
            */

            return respuesta;
        }

        /*BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorAdminSubsidioIdTransaccionTerceroPagador(tipoIdAdmin,
                numeroIdAdmin, idTransaccionTercerPagador);
        */
        BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(idRetiro);

        //si el valor solicitado es igual al valor entregado,se realizo un retiro completo,
        //se ejecuta  HU-31-218 3.1.3.1  Caso: Retiro confirmado completo
        //no se da el caso de que haya un retiro incompleto
        if (valorSolicitado.compareTo(valorEntregadoRetiro) == 0) {

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                //3.1.3.1 Confirmación retiro completo
                cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                cuenta.setFechaHoraUltimaModificacion(new Date());
                cuenta.setUsuarioUltimaModificacion(usuarioRetiro);

                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
            }
        }

        respuesta.put(idRespuesta,String.valueOf(idRetiro));//String.valueOf(identificadorRespuesta));
        respuesta.put(resultado, String.valueOf(true));
        //se anexa el identificador del tercero pagador.
        respuesta.put("idTransaccionTerceroPagador", idTransaccionTercerPagador);
        /*
        salida = new StringBuilder();
        salida.append(gson.toJson(respuesta));
        parametrosOUT = salida.toString();
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT);
        */
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }
    
    /**
     * Metodo encargado de realizar el resgitro de la transacción de retiro, sea porque es un retiro
     * completo o uno parcial.
     * HU-31-218
     * 
     * @param listaCuentaAdminSubsidio
     *        lista de cuentas del administrador del subsidio por los cuales se realizo el retiro.
     * @param saldoActualSubsidio
     *        valor del saldo actual que tiene el administrador de subsidios.
     * @param valorSolicitado
     *        valor solicitado a retirar.
     * @param fecha
     *        fecha por la cual ocurre
     * @param idTransaccionTercerPagador
     *        identificador de transacción del tercer pagador que envían por el servicio previo a este llamado.
     * @param municipio
     *        código indicado por el DANE del municipio del sitio de pago
     * @param usuario
     *        usuario que realizo el retiro
     * @param identificadorRespuesta
     *        identificador de respuesta de la creación del registro de operaciones de transacciones.
     * @param idPersonaAutorizada
     *        identificador de la base de datos de la persona autorizada para cobrar el subsidio por parte del administrador del subsidio
     */
    private Long registrarTransaccionRetiroTercPagador(List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio, BigDecimal saldoActualSubsidio,
            BigDecimal valorSolicitado, Long fecha, String idTransaccionTercerPagador, Long idSitioPago, String usuario,
            String identificadorRespuesta, Long idPersonaAutorizad, String usuarioConvenio, Long idConvenio) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.registrarTransaccionRetiro()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaParametro = new Date(fecha);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fec = formato.format(fechaParametro);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(fec, formatter);

        Date fechaActual = java.sql.Timestamp.valueOf(formatDateTime);

        Long idRetiro = null;

        if (listaCuentaAdminSubsidio == null || listaCuentaAdminSubsidio.isEmpty()) {

            logger.error(ConstantesComunes.FIN_LOGGER + firmaServicio
                    + " Ocurrió un error al registrar la transacción. No hay abonos por parte del administrador de subsidio.s");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        //identificador del administrador de subsidio del abono
        Long idAdminSubsidio = listaCuentaAdminSubsidio.get(0).getIdAdministradorSubsidio();
        //sitio de pago consultado por el id del administrador y el municipio
        Long idSitioDePago = idSitioPago;
        //se valida que este asociado un id de sitio de pago
        if (idSitioDePago == null) {
            //si es nulo, se retorna un menos uno para proceder y enviar el mensaje de error.
            return -1L;
        }
        String nombreConvenio = null;
        String nombreUsuarioConvenio = null;
        //String nombreConvenio = consultasCore.consultarNombreTercerPagadorConvenio(usuario);
        //si el valor solicitado es igual al actual se realiza un retiro completo 3.1.1
        ConvenioTerceroPagador convenio = consultasCore.consultarConvenioTerceroPagador(idConvenio);
        if(convenio!=null){
        	nombreConvenio = convenio.getNombre();
        	nombreUsuarioConvenio = convenio.getUsuarioAsignadoConvenio();
        	
        }
        if (saldoActualSubsidio.compareTo(valorSolicitado) == 0) {

            //Paso 2: se crea una cuenta de administrador subsidio referente al retiro
            Long idNuevaCuenta = registrarCuentarRetiro(usuario, TipoMedioDePagoEnum.EFECTIVO,
                    listaCuentaAdminSubsidio.get(0).getIdMedioDePago(), valorSolicitado, idAdminSubsidio, fechaActual, idSitioDePago,
                    identificadorRespuesta, nombreConvenio, nombreUsuarioConvenio, idTransaccionTercerPagador, null,
                    listaCuentaAdminSubsidio.get(0).getIdEmpleador(),listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal(),listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle(),
                    listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar(),listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio(), null);

            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {

                if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                    //Paso 2.1: se asocian los abono modificados con el creado del tipo "Retiro"
                    cuenta.setFechaHoraUltimaModificacion(new Date());
                    cuenta.setUsuarioUltimaModificacion(usuario);
                    cuenta.setIdCuentaAdminSubsidioRelacionado(idNuevaCuenta);

                    //Paso 1: se actualiza cada registro de tipo transaccion abono de estado aplicado a Solicitado.
                    cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);

                    consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                    List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                            .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                    //Paso 3: se modifican los registros de los detalles del abono asociado
                    for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                        detalle.setFechaTransaccionRetiro(fechaActual);
                        detalle.setUsuarioTransaccionRetiro(nombreUsuarioConvenio);
                        detalle.setFechaHoraUltimaModificacion(new Date());
                        detalle.setUsuarioUltimaModificacion(usuario);

                        consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                    }

                }
            }
            //se obtiene la cuenta del retiro para actualizarse
            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmonSubsidioDTO(idNuevaCuenta);
            //Se actualiza la transacción del retiro que se registro
            cuentaAdministradorSubsidioDTO.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
            cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
            cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(usuario);

            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

            //se asocia el id del retiro
            idRetiro = idNuevaCuenta;

        }
        else if (saldoActualSubsidio.compareTo(valorSolicitado) > 0) {
            //se realiza un retiro parcial si el valor solicitado es menor al saldo actual

            //Paso 2: Se crea el registro de transacción "Retiro"
            CuentaAdministradorSubsidioDTO cuentaTipoRetiro = new CuentaAdministradorSubsidioDTO();

            cuentaTipoRetiro.setUsuarioCreacionRegistro(usuario);
            cuentaTipoRetiro.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.RETIRO);
            cuentaTipoRetiro.setMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
            cuentaTipoRetiro.setIdMedioDePago(listaCuentaAdminSubsidio.get(0).getIdMedioDePago());
            cuentaTipoRetiro.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO);
            cuentaTipoRetiro.setValorOriginalTransaccion(valorSolicitado.negate());
            cuentaTipoRetiro.setIdAdministradorSubsidio(idAdminSubsidio);
            cuentaTipoRetiro.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
            cuentaTipoRetiro.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
            cuentaTipoRetiro.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
            cuentaTipoRetiro.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
            cuentaTipoRetiro.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
            cuentaTipoRetiro.setFechaHoraTransaccion(fechaActual);
            cuentaTipoRetiro.setUsuarioTransaccionLiquidacion(nombreUsuarioConvenio != null ? nombreUsuarioConvenio : usuario);
            cuentaTipoRetiro.setValorRealTransaccion(cuentaTipoRetiro.getValorOriginalTransaccion());
            cuentaTipoRetiro.setFechaHoraUltimaModificacion(new Date());
            cuentaTipoRetiro.setUsuarioUltimaModificacion(usuario);
            cuentaTipoRetiro.setIdSitioDeCobro(idSitioDePago);
            cuentaTipoRetiro.setNombreTerceroPagador(nombreConvenio);
            cuentaTipoRetiro.setIdRemisionDatosTerceroPagador(identificadorRespuesta);
            cuentaTipoRetiro.setIdTransaccionTerceroPagador(idTransaccionTercerPagador);
            //se crea la cuenta de Retiro
            Long idCuentaRetiro = consultasCore.crearCuentaAdministradorSubsidio(cuentaTipoRetiro);

            //variable C = valor del retiro que está pendiente por aplicar( inicialmente es igual a la variable a(valor solicitado) pero con signo negativo)
            double valorC = valorSolicitado.negate().doubleValue();

            //Paso 1: Se actualizan los registros con tipo transacción "Abono" que fueron utilizados para calcular el saldo.
            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio) {

                if (TipoTransaccionSubsidioMonetarioEnum.ABONO.equals(cuenta.getTipoTransaccion())) {

                    //C = C+B
                    valorC += cuenta.getValorRealTransaccion().doubleValue();

                    //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                    cuenta.setFechaHoraUltimaModificacion(new Date());
                    cuenta.setUsuarioUltimaModificacion(usuario);
                    cuenta.setIdCuentaAdminSubsidioRelacionado(idCuentaRetiro);

                    //Casos:  C<0  
                    if (valorC < 0) {

                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(usuario);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                        
                        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                        //se modifican los registros de los detalles del abono asociado
                        for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                            detalle.setFechaTransaccionRetiro(fechaActual);
                            detalle.setUsuarioTransaccionRetiro(nombreUsuarioConvenio);
                            detalle.setFechaHoraUltimaModificacion(new Date());
                            detalle.setUsuarioUltimaModificacion(usuario);

                            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                        }

                    }
                    else if (valorC == 0) { //Caso C=0
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setFechaHoraUltimaModificacion(new Date());
                        cuenta.setUsuarioUltimaModificacion(usuario);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                        
                        List<DetalleSubsidioAsignadoDTO> listaDetallesAbono = consultasCore
                                .consultarDetallesSubsidiosAsignadosAsociadosAbono(cuenta.getIdCuentaAdministradorSubsidio());

                        //se modifican los registros de los detalles del abono asociado
                        for (DetalleSubsidioAsignadoDTO detalle : listaDetallesAbono) {

                            detalle.setFechaTransaccionRetiro(fechaActual);
                            detalle.setUsuarioTransaccionRetiro(nombreUsuarioConvenio);
                            detalle.setFechaHoraUltimaModificacion(new Date());
                            detalle.setUsuarioUltimaModificacion(usuario);

                            consultasCore.actualizarDetalleSubsidioAsignado(detalle);
                        }

                        break;
                    }
                    else { //Caso: C>0

                        double valorReal = cuenta.getValorOriginalTransaccion().doubleValue() - valorC;
                        //Paso 3: se asocian los abono modificados con el creado del tipo "Retiro"
                        cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.SOLICITADO);
                        cuenta.setValorRealTransaccion(BigDecimal.valueOf(valorReal));
                        cuenta.setIdSitioDeCobro(idSitioDePago);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);

                        //Se crea el registro “Nuevo abono originado por fraccionamiento”
                        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();

                        cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
                        cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(usuario);
                        cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
                        cuentaAdministradorSubsidioDTO.setMedioDePago(cuenta.getMedioDePago());
                        cuentaAdministradorSubsidioDTO.setIdMedioDePago(cuenta.getIdMedioDePago());
                        cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.RETIRO_PARCIAL);
                        cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(BigDecimal.valueOf(valorC));
                        cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(idAdminSubsidio);
                        cuentaAdministradorSubsidioDTO.setIdEmpleador(listaCuentaAdminSubsidio.get(0).getIdEmpleador());
                        cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(listaCuentaAdminSubsidio.get(0).getIdAfiliadoPrincipal());
                        cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(listaCuentaAdminSubsidio.get(0).getIdBeneficiarioDetalle());
                        cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(listaCuentaAdminSubsidio.get(0).getIdGrupoFamiliar());
                        cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(listaCuentaAdminSubsidio.get(0).getSolicitudLiquidacionSubsidio());
                        cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(cuenta.getFechaHoraTransaccion());
                        cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(usuario);
                        cuentaAdministradorSubsidioDTO.setValorRealTransaccion(cuentaAdministradorSubsidioDTO.getValorOriginalTransaccion());
                        cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
                        cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(usuario);
                        cuentaAdministradorSubsidioDTO.setIdSitioDePago(cuenta.getIdSitioDePago());
                        cuentaAdministradorSubsidioDTO.setIdTransaccionOriginal(cuenta.getIdCuentaAdministradorSubsidio());
                        if(cuenta.getIdCuentaOriginal()!=null){
                        	cuentaAdministradorSubsidioDTO.setIdCuentaOriginal(cuenta.getIdCuentaOriginal());
                        }else{
                        	cuentaAdministradorSubsidioDTO.setIdCuentaOriginal(cuenta.getIdCuentaAdministradorSubsidio());
                        }

                        //se crea el abono originado por fraccionamiento
                        Long idAbonoOriginadoFraccionamiento = consultasCore
                                .crearCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        //Paso 4: se ajustan los detalles del "abono fraccionado"
                        fraccionarRegistrosDetallesAbonoEfectivo(cuenta.getIdCuentaAdministradorSubsidio(), cuenta.getValorRealTransaccion(),
                                usuario, idAbonoOriginadoFraccionamiento,fechaActual,nombreUsuarioConvenio);
                        
                        //se actualiza el nuevo abono como enviado
                        cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(idAbonoOriginadoFraccionamiento);
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        //se actualiza el nuevo abono como aplicado
                        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                        consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);

                        break;
                    }

                }
            }
            //se asocia el id del retiro.
            idRetiro = idCuentaRetiro;

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idRetiro;
    }
    
    /**
     * (non-Javadoc)
     * @see 
     */
    @Override
    public List<TempArchivoRetiroTerceroPagadorEfectivoDTO> consultarTempArchivoRetiroTerceroPagadorEfectivo(Long idConvenio, Long idArchivoRetiroTerceroPagadorEfectivo){
    	return consultasCore.consultarTempArchivoRetiroTerceroPagadorEfectivo(idConvenio,idArchivoRetiroTerceroPagadorEfectivo);   
    }
    		
    /**
     * (non-Javadoc)
     * @see 
     */
    @Override
    public Long actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(Long archivoTerceroPagadorEfectivo, EstadoArchivoConsumoTerceroPagadorEfectivo estado){ 
    	return consultasCore.actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(archivoTerceroPagadorEfectivo,estado);
    }
    
    @Override
	public void persistirValidacionesNombreArchivoTerceroPagador(Map<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> validaciones,
			Long idArchivoRetiroTerceroPagadorEfectivo){ 
    	consultasCore.persistirValidacionesNombreArchivoTerceroPagador(validaciones,idArchivoRetiroTerceroPagadorEfectivo);
    }
    
    /** 
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#generarArchivoAbonoBancos()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public String generarArchivoAbonoBancos() {
        
        List<CuentaAdministradorSubsidioDTO> data = consultasCore.consultarAbonosEnviadosMedioDePagoBancosArchivo();
        
        XSSFWorkbook libro = new XSSFWorkbook();
        Sheet pagina = libro.createSheet("reporte");

        int indiceRow = 0;
        int indiceColumn = 0;
        List<String[]> encabezado = generarEncabezado();
        
        //0,first row (0-based); 0,last row  (0-based) ; 0, first column (0-based);  last column  (0-based)
        CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn, encabezado.get(0).length);
        pagina.addMergedRegion(mergedCell);
        indiceRow++;
       
        //Generación del encabezado del reporte
        Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
        for (int i = 0; i < encabezado.size(); i++) {
            for (String encAportante : encabezado.get(i)) {
                Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
                celdaIterEncabezadoAportante.setCellValue(encAportante);
                indiceColumn++;
            }
            indiceRow++;
        }
        
        //Generación de cada registro por fila
        for (CuentaAdministradorSubsidioDTO dato : data) {
            Row filaIter = pagina.createRow(indiceRow);
            Cell celda1 = filaIter.createCell(0);
            celda1.setCellType(CellType.NUMERIC);
            celda1.setCellValue(dato.getIdCuentaAdministradorSubsidio());
            Cell celda2 = filaIter.createCell(1);
            celda2.setCellValue(dato.getTipoIdAdminSubsidio() != null ? 
                    dato.getTipoIdAdminSubsidio().getDescripcion() : null);
            Cell celda3 = filaIter.createCell(2);
            celda3.setCellValue(dato.getNumeroIdAdminSubsidio());
            Cell celda4 = filaIter.createCell(3);
            celda4.setCellValue(dato.getNombresApellidosAdminSubsidio());
            Cell celda5 = filaIter.createCell(4);
            celda5.setCellValue(dato.getCodigoBancoAdminSubsidio());
            Cell celda6 = filaIter.createCell(5);
            celda6.setCellValue(dato.getNombreBancoAdminSubsidio());
            Cell celda7 = filaIter.createCell(6);
            celda7.setCellValue(dato.getTipoCuentaAdminSubsidio() != null ? 
                    dato.getTipoCuentaAdminSubsidio().getDescripcion() : "");
            Cell celda8 = filaIter.createCell(7);
            celda8.setCellValue(dato.getNumeroCuentaAdminSubsidio());
            Cell celda9 = filaIter.createCell(8);
            celda9.setCellValue(dato.getTipoIdentificacionTitularCuentaAdminSubsidio() != null ? 
                    dato.getTipoIdentificacionTitularCuentaAdminSubsidio().getDescripcion() : "");
            Cell celda10 = filaIter.createCell(9);
            celda10.setCellValue(dato.getNumeroIdentificacionTitularCuentaAdminSubsidio());
            Cell celda11 = filaIter.createCell(10);
            celda11.setCellValue(dato.getNombreTitularCuentaAdminSubsidio());
            Cell celda12 = filaIter.createCell(11);
            celda12.setCellType(CellType.NUMERIC);
            celda12.setCellValue(dato.getValorRealTransaccion() != null ? dato.getValorRealTransaccion().longValue() : 0l);
            Cell celda13 = filaIter.createCell(12);
            celda13.setCellValue(dato.getEstadoAbono().getDescripcion());
            indiceRow++;
        }

        ByteArrayOutputStream archivo = new ByteArrayOutputStream();
        try {
            // Almacenamos el libro de Excel via ese flujo de datos
            libro.write(archivo);
            libro.close();
            
        } catch (IOException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
                    e + " En la generación del Excel Abono Bancos");
        }
        InformacionArchivoDTO info = new InformacionArchivoDTO();
        info.setDataFile(archivo.toByteArray());
        info.setFileType(MediaType.APPLICATION_OCTET_STREAM);
        info.setProcessName(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO.name());
        info.setDescription("CuentasAdmonSubsidioBancos");
        info.setDocName("CuentasAdmonSubsidioBancos.xls");
        info.setFileName("CuentasAdmonSubsidioBancos.xls");
        String idECM = almacenarArchivo(info);
        StringBuilder result = new StringBuilder();
        result.append("\"");
        result.append(idECM);
        result.append("\"");
        return result.toString();
    }
    
    /**
     * Genera el detalle del encabezado del reporte.
     * @return Lista con el Encabezado
     */
    private List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList();
        String[] encabezado = {"Identificador transacción", "Tipo identificación", "No identificación",
            "Administrador del subsidio", "Código del banco admin", "Nombre del banco del admin", "Tipo de cuenta admin",
            "Número de cuenta admin", "Tipo identificación titular cuenta", "Número identificación titular cuenta", "Nombre titular cuenta", "Valor abono",
            "Resultado"};
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    /**
     * Almacena en el ecm excel que se genera.
     * 
     * @param infoFile
     * @return String con el identificador para el ecm
     */
    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.debug("Inicia almacenarArchivo(InformacionArchivoDTO infoFile)");
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();

        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        logger.debug("Finaliza almacenarArchivo(InformacionArchivoDTO infoFile)");
        return idECM.toString();
    }

    /**
     * Método encargado de consultar los retiros con estado Solicitado
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarRetirosAbonosEstadoSolicitado(Long fechaInicial, Long fechaFinal) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarRetirosAbonosEstadoSolicitado(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<CuentaAdministradorSubsidioDTO> resultadoConsulta = new ArrayList<>();

        resultadoConsulta = consultasCore.consultarRetirosConEstadoSolicitado(fechaInicial, fechaFinal);
        
        for (CuentaAdministradorSubsidioDTO registro : resultadoConsulta) {
            registro.setValorOriginalTransaccion(registro.getValorOriginalTransaccion().abs());
            registro.setValorRealTransaccion(registro.getValorRealTransaccion().abs());
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoConsulta;
    }
    
    /**
     * Exporta a xls la data de los retiros con estado solicitado
     */
    @Override
    public Response exportarRetirosAbonosEstadoSolicitado(Long fechaInicial, Long fechaFinal) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarRetirosAbonosEstadoSolicitado(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<CuentaAdministradorSubsidioDTO> dataRetiros = new ArrayList<>();
        List<String[]> dataArchivo = new ArrayList<>();
        String[] lineaEncabezado = {"ID",
                "IDENTIFICADOR TRANSACCION",
                "USUARIO REGISTRO TRANSACCION",
                "FECHA HORA CREACION REGISTRO",
                "TIPO TRANSACCION",
                "MEDIO DE PAGO",
                "FECHA HORA TRANSACCION",
                "VALOR ORIGINAL TRANSACCION",
                "VALOR REAL TRANSACCION",
                "TIPO ID ADMIN SUBSIDIO",
                "NUMERO ID ADMIN SUBSIDIO",
                "NOMBRE ADMIN SUBSIDIO",
                "SITIO DE COBRO",
                "NOMBRE TERCERO PAGADOR",
                "IDENTIFICADOR PUNTO COBRO",
                "ID TRANSACCION TERCERO PAGADOR"};
        List<String[]> encabezadoArchivo = new ArrayList<>();
        String extension = ".xls";
        byte[] data;
        String nombreArchivo;
        
        Integer consecutivo = 0;
        
        dataRetiros = consultasCore.consultarRetirosConEstadoSolicitado(fechaInicial, fechaFinal);
        
        for (CuentaAdministradorSubsidioDTO registro : dataRetiros) {
            String[] linea = new String[16];
            
            consecutivo++;
            //consecutivo
            linea[0] = consecutivo.toString();
            //id transaccion
            //linea[1] = registro.getIdTransaccionOriginal().toString(); Cuando tiene estado solicitado este valor siempre es vacio
            linea[1] = (registro.getIdTransaccionOriginal() == null) ? "" : registro.getIdTransaccionOriginal().toString();
            
            //Usuario que registro la transaccion
            linea[2] = (registro.getUsuarioTransaccionLiquidacion() == null) ? "" : registro.getUsuarioTransaccionLiquidacion().toString();
            //Fecha y hora
            linea[3] = (registro.getFechaHoraCreacionRegistro() == null) ? "" : registro.getFechaHoraCreacionRegistro().toString();
            //Tipo transaccion
            linea[4] = (registro.getTipoTransaccion() == null) ? "" : registro.getTipoTransaccion().toString();
            //Medios de pago
            linea[5] = (registro.getMedioDePago() == null) ? "" : registro.getMedioDePago().toString();
            //Fecha y hora transaccion
            linea[6] = (registro.getFechaHoraTransaccion() == null) ? "" : registro.getFechaHoraTransaccion().toString();
            //Valor original transaccion
            linea[7] = (registro.getValorOriginalTransaccion() == null) ? "" : registro.getValorOriginalTransaccion().abs().toString();
            //Valor real transaccion
            linea[8] = (registro.getValorRealTransaccion() == null) ? "" : registro.getValorRealTransaccion().abs().toString();
            //Tipo identificacion administrador de subsidio
            linea[9] = (registro.getTipoIdAdminSubsidio() == null) ? "" : registro.getTipoIdAdminSubsidio().toString();
            //Numero identificacion administrador de subsidio
            linea[10] = (registro.getNumeroIdAdminSubsidio() == null) ? "" : registro.getNumeroIdAdminSubsidio().toString();
            //Nombres administrador subsidio
            linea[11] = (registro.getNombresApellidosAdminSubsidio() == null) ? "" : registro.getNombresApellidosAdminSubsidio();
            //Sitio de cobro
            linea[12] = (registro.getNombreSitioCobro() == null) ? "" : registro.getNombreSitioCobro().toString();
            //Nombre tercero pagador
            linea[13] = (registro.getNombreTerceroPagador() == null) ? "" : registro.getNombreTerceroPagador().toString();
            //Identificador punto de cobro
            linea[14] = (registro.getIdPuntoDeCobro() == null) ? "" : registro.getIdPuntoDeCobro().toString();
            //Identificador tercero pagador
            linea[15] = (registro.getIdTransaccionTerceroPagador() == null) ? "" : registro.getIdTransaccionTerceroPagador().toString();
            
            dataArchivo.add(linea);
        }
        
        //Se construye el excel para la respuesta
        encabezadoArchivo.add(lineaEncabezado);
    
        data = ArchivosPagosUtil.generarNuevoArchivoExcel(encabezadoArchivo, dataArchivo);

        nombreArchivo = "Resultado_transacciones_para_confirmar_valor_entregado" + extension;
        
        Response.ResponseBuilder response = null;
        
        response = Response.ok(new ByteArrayInputStream(data));
        response.header("Content-Type", FormatoReporteEnum.EXCEL.getMimeType() + ";charset=utf-8");
        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return response.build();
    }

    @Override
    public Response exportarCuotasDispersadasPorTerceroPagador(Long idConvenio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.exportarCuotasDispersadasPorTerceroPagador(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        //inicio Prueba para archivo
        //data de consulta
        List<Object[]> dataCuotas = new ArrayList<>();
        //data consulta para encabezado
        Object[] dataEncabezado;
        //data que se convierte a bytes
        List<String[]> dataArchivo = new ArrayList<>();
        //data que trae la consulta del encabezado
        String[] lineaEncabezado;
        //data de encabezado que se transforma a bytes
        List<String[]> encabezadoArchivo = new ArrayList<>();
        //Extension del archivo
        String extension = ".csv";
        //Arreglo de bytes que se envia al encabezado
        byte[] data;
        //nombre del archivo
        String nombreArchivo = "Ejemplo" + extension;
        //Caracter separador del csv
        String caracterSeparador = ",";
        
        
        dataEncabezado = consultasCore.consultarEncabezadoCuotasDispersadasPorTerceroPagador(idConvenio);
        dataCuotas = consultasCore.consultarCuotasDispersadasPorTerceroPagador(idConvenio);
        
        
        lineaEncabezado = new String[3];
        
        //nombre tercero pagador
        if (dataEncabezado != null) {
            lineaEncabezado[0] = (dataEncabezado[0] == null) ? "" : dataEncabezado[0].toString();
            lineaEncabezado[1] = (dataEncabezado[1] == null) ? "" : dataEncabezado[1].toString();
            lineaEncabezado[2] = (dataEncabezado[2] == null) ? "" : dataEncabezado[2].toString();
        }
        
        
        encabezadoArchivo.add(lineaEncabezado);

        if (dataCuotas != null) {
            for (Object[] registro : dataCuotas) {
                String[] linea = new String[7];
                
                //id transaccion
                
                linea[0] = (registro[0] == null) ? "" : registro[0].toString();
                
                //tipo id admin subsidio
                linea[1] = (registro[1] == null) ? "" : registro[1].toString();
                //id admin subsidio
                linea[2] = (registro[2] == null) ? "" : registro[2].toString();
                //Nombre admin subsidio
                linea[3] = (registro[3] == null) ? "" : registro[3].toString();
                //Monto por dispersar
                linea[4] = (registro[4] == null) ? "" : registro[4].toString();
                //Convenio tercero Pagador
                linea[5] = (registro[5] == null) ? "" : registro[5].toString();
                //nombre tercero pagador
                linea[6] = (registro[6] == null) ? "" : registro[6].toString();
                
                dataArchivo.add(linea);
            }
        }
        
        //fin prueba para archivo
        
        data = ArchivosPagosUtil.generarArchivoCSV(encabezadoArchivo, dataArchivo, caracterSeparador);
        
        //Se contrsuye el archivo con la consulta
        Response.ResponseBuilder response = null;
        
        response = Response.ok(new ByteArrayInputStream(data));
        response.header("Content-Type", FormatoReporteEnum.CSV.getMimeType() + ";charset=utf-8");
        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo);
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return response.build();
    }
    

	/** (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#persistirArchivoTransDetaSubsidio(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio)
	 */
    @Override
	public Long persistirArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio){
		return consultasCore.persistirArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
	}
    
	/** (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#actualizarArchivoTransDetaSubsidio(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio)
	 */
    @Override
	public void actualizarArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio) {
    	consultasCore.actualizarArchivoTransDetaSubsidio(archivoTransDetaSubsidio);
		
	}
    
       
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoTransDetaSubsidio> consultarArchivoTransDetaSubsidioTodos() {
        return consultasCore.consultarArchivoTransDetaSubsidioTodos();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SubsidiosConsultaAnularPerdidaDerechoDTO> consultarCuentasPorAnularMantis266382() { 
        return consultasCore.consultarCuentasPorAnularMantis266382();   
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal consultarValorTotalSubsidiosAnular(String tipo) {
        String firma = "consultarValorTotalSubsidiosAnular(String tipo)";
        logger.debug("Inicio método " + firma);
        List<String> listaMediosDePago = obtenerMediosPagosSubsidiosAnular(tipo);
        String diasParametrizados = obtenerDiasParametrizadosSubsidiosAnular(tipo);
        logger.debug("Finaliza método " + firma);
        return consultasCore.consultarValorTotalSubsidiosAnular(listaMediosDePago, diasParametrizados, new Date());
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResumenListadoSubsidiosAnularDTO generarResumenListadoSubsidiosAnular(Boolean firstRequest, Integer offset, Integer filter, String orderBy, Integer limit, String tipo) {
        String firma = "generarResumenListadoSubsidiosAnular(Boolean firstRequest, Integer offset, Integer filter, String orderBy, Integer limit, String tipo)";
        logger.debug("Inicio método " + firma);
        List<String> listaMediosDePago = obtenerMediosPagosSubsidiosAnular(tipo);
        String diasParametrizados = obtenerDiasParametrizadosSubsidiosAnular(tipo);
        logger.debug("Finaliza método " + firma);
        return consultasCore.generarResumenListadoSubsidiosAnular(listaMediosDePago, diasParametrizados, firstRequest, offset, filter, orderBy, limit);
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ListadoSubsidiosAnularDTO generarlistadoSubsidiosAnular(Boolean firstRequest, Integer offset, String orderBy, Integer limit, String tipo,String numeroIdentificacionAdminSub) {
        String firma = "generarlistadoSubsidiosAnular(Boolean firstRequest, Integer offset, String orderBy, Integer limit, String tipo)";
        logger.debug("Inicio método " + firma);
        List<String> listaMediosDePago = obtenerMediosPagosSubsidiosAnular(tipo);
        String diasParametrizados = obtenerDiasParametrizadosSubsidiosAnular(tipo);
        logger.debug("Finaliza método " + firma);
        return consultasCore.generarlistadoSubsidiosAnular(listaMediosDePago, diasParametrizados, firstRequest, offset, orderBy, limit,numeroIdentificacionAdminSub);
    }
    
    @Override
    public byte[] generarExcelListadoSubsidiosAnular(Integer limit, String tipo) {
        String firma = "generarExcelListadoSubsidiosAnular(Integer limit, String tipo)";
        logger.debug("Inicio método " + firma); 
        byte[] dataReporte = null;
        try {
            ListadoSubsidiosAnularDTO listadoSubsidiosAnularDTO = generarlistadoSubsidiosAnular(Boolean.FALSE,0,"",limit,tipo,"null");
            List<String[]> dataList = new ArrayList<String[]>();
            String[] encabezado = {"#","Periodo liquidado","Liquidacion asociada","Fecha liquidación asociada","Tipo liquidación","Tipo identificación empleador", "No. identificación empleador","Razón social / Nombre empleador","Tipo identificación afiliado principal","Afiliado principal","Grupo familiar","Parentesco beneficiario", "Tipo identificación beneficiario", "No. identificación beneficiario", "Beneficiario", "Tipo cuota", "Tipo identificación Administrador del subsidio", "No. identificación Administrador del subsidio", "Administrador del subsidio", "Medio de pago", "Sitio de pago", "Valor total"};
            List<String[]> encabezadoList = new ArrayList<String[]>();
            encabezadoList.add(encabezado);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            for(SubsidioMonetarioPrescribirAnularFechaDTO subsidioAnular : listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO()) {
                String sitioDePago = "";
                if(subsidioAnular.getNombreDepartamento() != null && !subsidioAnular.getNombreDepartamento().isEmpty() && subsidioAnular.getNombreSitioDePago() != null && !subsidioAnular.getNombreDepartamento().isEmpty()) {
                    sitioDePago = subsidioAnular.getNombreDepartamento() +", "+ subsidioAnular.getNombreMunicipio()+", "+subsidioAnular.getNombreSitioDePago();
                }
                String[] data = {
                        subsidioAnular.getNumeroRegistro().toString(),
                        CalendarUtils.darFormatoYYYYMM(subsidioAnular.getPeriodoLiquidado()),
                        subsidioAnular.getIdLiquidacionAsociada().toString(),
                        formatoFecha.format(subsidioAnular.getFechaLiquidacionAsociada()),   
                        subsidioAnular.getEmpleador().getTipoIdentificacion().getDescripcion(),
                        subsidioAnular.getEmpleador().getNumeroIdentificacion(),
                        subsidioAnular.getEmpleador().getRazonSocial(),
                        subsidioAnular.getAfiliadoPrincipal().getTipoIdentificacion().getDescripcion(),
                        subsidioAnular.getAfiliadoPrincipal().getNumeroIdentificacion(),
                        PersonasUtils.obtenerNombrePersona(subsidioAnular.getAfiliadoPrincipal()),
                        subsidioAnular.getCodigoGrupoFamiliar().toString(),
                        subsidioAnular.getParentescoBeneficiario().name(),
                        subsidioAnular.getBeneficiario().getTipoIdentificacion().getDescripcion(),
                        subsidioAnular.getBeneficiario().getNumeroIdentificacion(),
                        PersonasUtils.obtenerNombrePersona(subsidioAnular.getBeneficiario()),
                        subsidioAnular.getTipoCuota().getDescripcion(),
                        subsidioAnular.getAdministradorSubsidio().getTipoIdentificacion().getDescripcion(),
                        subsidioAnular.getAdministradorSubsidio().getNumeroIdentificacion(),
                        PersonasUtils.obtenerNombrePersona(subsidioAnular.getAdministradorSubsidio()),
                        subsidioAnular.getMedioDePago().getDescripcion(),
                        sitioDePago,
                        "$" + subsidioAnular.getValorTotal().toString()
                };
                dataList.add(data);
            }
            dataReporte = ArchivosPagosUtil.generarNuevoArchivoExcel(encabezadoList, dataList);
        }catch(Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return dataReporte;
    }
    
    /**
     * Método que se encarga de obtener el número de dias parametrizados por la CCF para anular un subsidio monetario
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param tipo
     *        Si se va a anular por prescripción o por vencimiento
     * @return días parametrizados por la CCF
     */
    private String obtenerDiasParametrizadosSubsidiosAnular(String tipo) {
        String firma = "obtenerDiasParametrizadosSubsidiosAnular(String tipo)";
        logger.debug("Inicio método " + firma); 
        if(tipo.equals("VENCIMIENTO")){
            logger.debug("Finaliza método " + firma);
            return obtenerNumeroDiasParametrizadosFechaVencimiento();
         }else {
            logger.debug("Finaliza método " + firma);
            return obtenerNumeroDiasParametrizadosPrescripcion();
         }
    }
    
    /**
     * Método que se encarga de obtener la lista de medios de pago 
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param tipo
     *        Si se va a anular por prescripción o por vencimiento
     * @return lista medios de pago
     */
    private List<String> obtenerMediosPagosSubsidiosAnular(String tipo ){
        String firma = "obtenerMediosPagosSubsidiosAnular(String tipo)";
        logger.debug("Inicio método " + firma); 
        List<String> listaMediosDePago = new ArrayList<>();
        listaMediosDePago.add(TipoMedioDePagoEnum.EFECTIVO.toString());
        if(tipo.equals("PRESCRIPCION")){
          //la consulta de anulación por prescripción selecciona cualquier medio de pago
            listaMediosDePago.add(TipoMedioDePagoEnum.TARJETA.toString());
            listaMediosDePago.add(TipoMedioDePagoEnum.TRANSFERENCIA.toString());//transferencia hace alusión al medio de pago bancos
        }
        logger.debug("Finaliza método " + firma);
        return listaMediosDePago;
    }

    /**
     * Método que se encarga de obtener la lista información de la solicitud de la dispersion de subsidio monetario
     *
     * @author <a href="mailto:camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @return Lista con la información de la solicitud de la dispersion
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudDispersionSubsidioMonetario(Long idSolicitud){
        return consultasCore.consultarRegistroSolicitudDispersionSubsidioMonetario(idSolicitud);
    }

    /**
     * Método que se encarga de obtener la lista información de la solicitud de la dispersion de subsidio monetario
     *
     * @author <a href="mailto:camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @return Lista con la información de la solicitud de la dispersion
     */
    @Override
    public List<RegistroSolicitudAnibol> consultarRegistroSolicitudAnulacionSubsidioMonetario(Long idSolicitud){
        return consultasCore.consultarRegistroSolicitudAnulacionSubsidioMonetario(idSolicitud);
    }

    /**
     * Método que se encarga de actualizar el estado de las transacciones de la dispersion de subsidio monetario
     * que se procesaron correctamente
     *
     * @author <a href="mailto:camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     */
    @Override
    public void actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long idSolicitud){
        consultasCore.actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(idSolicitud);
    }

    /**
     * Método que se encarga de obtener el id de la cuenta admin subsidio
     *
     * @author <a href="camilo.sierra@asopagos.com"> Camilo Sierra</a>
     * @param idSolicitud
     *        Identificador de la solicitud a nivel de Anibol
     * @param numeroTarjetaAdmonSubsidio
     *        Identificador de la solicitud a nivel de Anibol
     */
    @Override
    public Long consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(Long idSolicitud, String numeroTarjetaAdmonSubsidio){
        return consultasCore.consultaCuentaAdmonSubsidioDispersionSubsidioMonetario(idSolicitud, numeroTarjetaAdmonSubsidio);
    }

    @Override
    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatariosPrescripcion(Long idCuentaAdmonSubsidio){
       return  consultasCore.obtenerRolesDestinatariosPrescripcion(idCuentaAdmonSubsidio);

    }
  @Override
  public List<Long> obtenerIdsAbonosPrescripcion(String parametro){
    return  consultasCore.obtenerIdsAbonosPrescripcion(parametro);
  }
  
    /**
     * (non-Javadoc)
     *
     * @param userEmail
     * @see
     * com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#confirmarResultadosAbonosBancarios(java.util.List)
     */
    @Override
    public void confirmarResultadosAbonosBancariosArchivo(List<ConfirmacionAbonoBancarioCargueDTO> listaAbonos, String userEmail) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.confirmarResultadosAbonosBancariosArchivo(List<ConfirmacionAbonoBancarioCargueDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<String> idCuentaAdmonSubsidioNoExitosas = new ArrayList<>();
        List<String> idCuentaAdmonSubsidioExitosas = new ArrayList<>();

        if (listaAbonos != null && !listaAbonos.isEmpty()) {
            for (ConfirmacionAbonoBancarioCargueDTO abono : listaAbonos) {
                if(abono.getResultadoAbono().equals("ABONO NO EXITOSO")){
                    idCuentaAdmonSubsidioNoExitosas.add(abono.getCasId());
                }else{
                    idCuentaAdmonSubsidioExitosas.add(abono.getCasId());
                }
                
            }
        }
        
        
        //la forma de strem, probar si realmente funciona 
        //informacionActNovedadNoExitosos = resultDTO.getListActualizacionInfoNovedad().stream().filter(
          //        iteAdmin -> iteAdmin.getConfirmacionAbonoAdminSubsidio().getResultadoAbono().equals("ABONO NO EXITOSO")
         // ).findFirst().orElse(null);

        
        

        consultasCore.ejecutarAbonosMedioPagoBancosArchivo(idCuentaAdmonSubsidioNoExitosas,idCuentaAdmonSubsidioExitosas, userEmail);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    private void printJsonMessage(Object object,String message){
        try{
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(object);
            logger.info(message + jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String userEmail(String user){
        String userEmail = user;
        return userEmail;
    }
 
    @Override
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV2(
            String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado, 
            String usuario, String idTransaccionTercerPagador, String departamento, 
            String municipio, String user, String password, String idPuntoCobro, UserDTO userDTO) {
        
        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV2(String,String,BigDecimal,String,String,String,String,String,String,String, userDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        parametrosEntrada.put("tipoIdAdmin", !StringUtils.isEmpty(tipoIdAdmin) ? tipoIdAdmin : "null");
        parametrosEntrada.put("numeroIdAdmin", !StringUtils.isEmpty(numeroIdAdmin) ? numeroIdAdmin : "null");
        parametrosEntrada.put("valorSolicitado", Objects.nonNull(valorSolicitado) ? valorSolicitado : BigDecimal.ZERO);
        parametrosEntrada.put("usuario", !StringUtils.isEmpty(usuario) ? usuario : "null");
        parametrosEntrada.put("idTransaccionTercerPagador", !StringUtils.isEmpty(idTransaccionTercerPagador) ? idTransaccionTercerPagador : "null");
        parametrosEntrada.put("departamento", !StringUtils.isEmpty(departamento) ? departamento : "null");
        parametrosEntrada.put("municipio", !StringUtils.isEmpty(municipio) ? municipio : "null");
        parametrosEntrada.put("user", !StringUtils.isEmpty(user) ? user : "null");
        parametrosEntrada.put("idPuntoCobro", !StringUtils.isEmpty(idPuntoCobro) ? idPuntoCobro : "null");

        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();
        logger.info("Paramentros de entrada - solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidaciones: " + parametrosIN);

        //se valida la obligatoriedad de los campos
        if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || StringUtils.isEmpty(usuario)
                || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(departamento) || StringUtils.isEmpty(municipio)
                || StringUtils.isEmpty(user) || StringUtils.isEmpty(password) || StringUtils.isEmpty(idPuntoCobro)) {
            logger.debug("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALIDACION_CAMPOS);
        }
        Date fecha = new Date();
        ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
        ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
        String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
        String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);

        ValidatorUtil.validarValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));

        Map<String, String> respuesta = null;
        Map<String, String> saldoSubsidio = consultarSaldoSubsidioTransaccionValidaciones(tipoIdAdmin, numeroIdAdmin, user, password);
        if (saldoSubsidio.containsKey("error")) {
            //si encuentra un error en la consulta del saldo, se propaga el error a la interfaz.
            return saldoSubsidio;
        } else {
            //si la consulta del saldo es exitosa, se obtiene el valor y se procede a realizarse el retiro.
            BigDecimal saldoActualSubsidio = new BigDecimal(saldoSubsidio.get("saldoAdminSubsidio"));
            respuesta = solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum.valueOf(tipoIdAdmin), numeroIdAdmin,
                                                TipoMedioDePagoEnum.EFECTIVO , saldoActualSubsidio, valorSolicitado, fecha.getTime(),
                                                idTransaccionTercerPagador, departamento, municipio, usuario, Boolean.FALSE, userDTO,
                                                user, idPuntoCobro, Boolean.TRUE.toString());
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    public Map<String, String> validacionesSolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(
            String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
            String usuario, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, String password, String idPuntoCobro,
            UserDTO userDTO, Long identificadorRespuesta,String parametrosIN) throws InterruptedException {

        String url = "solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3";

        Date fechaHoraInicio = new Date();
        logger.info("fechaHoraInicio " + fechaHoraInicio);

        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(String,String,String,String,String,String,String,String,String,String, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();
        Map<String, String> salidaValidaciones;
        Map<String, String> validacionesRetiro;
        Map<String, String> respuesta = new HashMap<>();

        final String httpError = "CODIGO_ERROR";

        Date fecha = new Date();


        logger.info("Paramentros de entrada - solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidaciones: " + parametrosIN);



            salidaValidaciones = validacionSolicitarRetiroConfirmarValorEntregadoV3(tipoIdAdmin, numeroIdAdmin, valorSolicitado,
                    usuario, idTransaccionTercerPagador, departamento,
                    municipio, user, password, idPuntoCobro, userDTO);

            if(salidaValidaciones.containsKey("error"))
            {
                logger.debug("OCURRIO UN ERROR DE VALIDACIONES: " + salidaValidaciones.get("error") + " - CODIGO: " + salidaValidaciones.get("codigoError"));
                logger.info("salidaValidaciones v2 " + salidaValidaciones);
                return salidaValidaciones;
            }
            else {
                BigDecimal saldoActualSubsidio = new BigDecimal(salidaValidaciones.get("saldoAdminSubsidio").toString());
                TipoMedioDePagoEnum medioDePago = TipoMedioDePagoEnum.EFECTIVO;

                parametrosEntrada.put("medioDePago", medioDePago);
                parametrosEntrada.put("saldoActualSubsidio", saldoActualSubsidio);
                parametrosEntrada.put("numeroIdentificadorAdmin", numeroIdAdmin);
                parametrosEntrada.put("fecha", fecha.getTime());
                salida = new StringBuilder();
                salida.append(gson.toJson(parametrosEntrada));
                parametrosIN = salida.toString();

                List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio = consultasCore.consultarRegistrosAbonosParaCalcularSaldoSubsidio(TipoIdentificacionEnum.valueOf(tipoIdAdmin), numeroIdAdmin, medioDePago);

                /*if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("El proceso fue interrumpido.");
                }*/
                logger.info("saldoActualSubsidio " +saldoActualSubsidio);
                logger.info("valorSolicitado " +valorSolicitado);
                validacionesRetiro = validarSolicitudRetiroSubsidio(saldoActualSubsidio, valorSolicitado, idTransaccionTercerPagador, TipoMedioDePagoEnum.EFECTIVO, user,
                        listaCuentaAdminSubsidio, "resultado", "error", "identificadorRespuesta", parametrosIN,
                        TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO, usuario, idPuntoCobro, null);

                /*if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("El proceso fue interrumpido.");
                }*/
                if (validacionesRetiro != null) {
                    logger.debug("Salida validacionesRetiro: " + validacionesRetiro.toString());

                    logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

                    validacionesRetiro.put(httpError, "BAD_REQUEST");


                    return validacionesRetiro;
                } else {
                    logger.debug("INICIA TRANSACCION...");
                    // Iniciamos la transaccion
                    final BigDecimal saldoAdminSubsidio = new BigDecimal(salidaValidaciones.get("saldoAdminSubsidio"));
                    final long fechaActual = fecha.getTime();

                    logger.debug("Parametros de entrada antes de persistir: " + parametrosIN);

//                    if (Thread.currentThread().isInterrupted()) {
//                        throw new InterruptedException("El proceso fue interrumpido.");
//                    }
                    Thread.sleep(0);
//                    if (Thread.currentThread().isInterrupted()) {
//                        throw new InterruptedException("El proceso fue interrumpido.");
//                    }


                    Long idRetiro = registrarTransaccionRetiro(
                            listaCuentaAdminSubsidio, saldoAdminSubsidio, valorSolicitado, fechaActual,
                            idTransaccionTercerPagador, municipio, usuario, String.valueOf(identificadorRespuesta), EstadoTransaccionSubsidioEnum.SOLICITADO, idPuntoCobro, user);
                    respuesta.put("idRetiro", String.valueOf(idRetiro));
                    logger.info("idRetiro " + idRetiro);
                    /*if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException("El proceso fue interrumpido.");
                    }
                    Thread.sleep(0);
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException("El proceso fue interrumpido.");
                    }*/
                    logger.info("NO IMPRIMIRRRRRRRRR---------------------------------------- ");

                    if (idRetiro == -1) {
                        Date fechaHoraFinal = new Date();
                        logger.info("fechaHoraFinal " +fechaHoraFinal);
                        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L;
                        respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                        respuesta.put("resultado", String.valueOf(false));
                        respuesta.put("detalleTransaccion", "Transacción no exitosa");
                        respuesta.put("error", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getDescripcion());
                        respuesta.put("codigoError", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoError());
                        respuesta.put(httpError, ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoHttp());
                        respuesta.put("idRetiro", String.valueOf(idRetiro));
                        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
                        return respuesta;
                    }

                    List<CuentaAdministradorSubsidio> cuentas = new ArrayList<>();
                    cuentas = consultasCore.consultarAbonosRelacionadosRetiro(idRetiro);

                    if (idRetiro == 0) {
                        Date fechaHoraFinal = new Date();
                        logger.info("fechaHoraFinal " +fechaHoraFinal);
                        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L;
                        respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                        respuesta.put("resultado", String.valueOf(false));
                        respuesta.put("detalleTransaccion", "Transacción no exitosa");
                        respuesta.put("error", "Ya hay un retiro en proceso, por favor verifique en unos minutos");
                        respuesta.put("codigoError", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoError());
                        respuesta.put(httpError, ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoHttp());
                        respuesta.put("idRetiro", String.valueOf(idRetiro));
                        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
                        return respuesta;
                    }

                    if (cuentas.isEmpty()) {
                        Date fechaHoraFinal = new Date();
                        logger.info("fechaHoraFinal " +fechaHoraFinal);
                        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L;
                        respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                        respuesta.put("resultado", String.valueOf(false));
                        respuesta.put("detalleTransaccion", "Transacción no exitosa");
                        respuesta.put("error", "Ya hay un retiro en proceso, por favor verifique en unos minutos");
                        respuesta.put("codigoError", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoError());
                        respuesta.put(httpError, ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoHttp());
                        respuesta.put("cuentasVacias", String.valueOf(cuentas.size()));
                        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
                        return respuesta;
                    }

                    List<CuentaAdministradorSubsidioDTO> listaCuentasAdminSubsidio = consultasCore
                            .consultarAbonosEstadoSolicitadoPorSolicitudRetiro(TipoIdentificacionEnum.valueOf(tipoIdAdmin), numeroIdAdmin, idTransaccionTercerPagador);

                    logger.info("listaCuentasAdminSubsidio final " +listaCuentasAdminSubsidio);

                    BigDecimal valorEntregadoRetiro = consultasCore.obtenerValorRetiroPorIdCuentaAdminstradorSubsidio(idRetiro);

                    //si el valor solicitado es igual al valor entregado,se realizo un retiro completo,
                    //se ejecuta  HU-31-218 3.1.3.1  Caso: Retiro confirmado completo
                    //no se da el caso de que haya un retiro incompleto
                    if(listaCuentasAdminSubsidio != null && !listaCuentasAdminSubsidio.isEmpty()) {
                        logger.info("listaCuentasAdminSubsidio " +listaCuentasAdminSubsidio.size());
                        if (valorSolicitado.compareTo(valorEntregadoRetiro) == 0) {
                            logger.info("ingresa al if " +listaCuentasAdminSubsidio.size());
                            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentasAdminSubsidio) {
                                logger.info("hace el for" +listaCuentasAdminSubsidio.size());
                                //3.1.3.1 Confirmación retiro completo
                                cuenta.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.COBRADO);
                                cuenta.setFechaHoraUltimaModificacion(new Date());
                                cuenta.setUsuarioUltimaModificacion(user);
                                consultasCore.actualizarCuentaAdministradorSubsidio(cuenta);
                            }
                        }
                    }

                    consultasCore.actualizarEstadoTransaccionRetiro(EstadoTransaccionSubsidioEnum.FINALIZADO.name(), idRetiro);

                    Date fechaHoraFinal = new Date();
                    logger.info("fechaHoraFinal " +fechaHoraFinal);
                    long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L;
                    logger.info("diferencia milisegundos " + diferenciaMilisegundos);
                    respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                    respuesta.put("resultado", String.valueOf(true));
                    respuesta.put("detalleTransaccion", "Transacción exitosa");
                    respuesta.put("idTransaccionTerceroPagador", idTransaccionTercerPagador);
                    respuesta.put(httpError, "OK");
                    respuesta.put("idRetiro", String.valueOf(idRetiro));
                    respuesta.put("tiempo", String.valueOf(diferenciaMilisegundos));
                    respuesta.put("url", "solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3");
                    logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
                    return respuesta;
                }
            }


    }

    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(
            String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
            String usuario, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, String password, String idPuntoCobro,
            UserDTO userDTO) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,\n" +
                "            String usuario, String idTransaccionTercerPagador, String departamento,\n" +
                "            String municipio, String user, String password, String idPuntoCobro,\n" +
                "            UserDTO userDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Date fechaHoraInicio = new Date();
        Date fechaHoraFinal = null;
        String url = "servicio-solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3";
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        Date fechaInico = new Date();
        // Se almacenan los parámetros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmin", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmin", numeroIdAdmin);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("departamento", departamento);
        parametrosEntrada.put("municipio", municipio);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("nombreUserDTO", user);
        parametrosEntrada.put("emailUserDTO", user);
        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();

        CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO = consultarInfoRetiro(tipoIdAdmin, numeroIdAdmin, TipoMedioDePagoEnum.EFECTIVO);

        Map<String, String> respuesta = new HashMap<>();
        Integer tiempo = 55;
        logger.info("ingresa aqui a consumir");
        String idRetiro = String.valueOf(0);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Long identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(
                TipoOperacionSubsidioEnum.SOLICITAR_RETIRO_CONFIRMAR_VALOR_ENTREGADO, parametrosIN, user, cuentaAdministradorSubsidioYDetallesDTO.getListaCuentaAdministradorSubsidio().get(0).getIdAdministradorSubsidio());

        Future<Map<String, String>> futureTransaccion = null;
        try {

            String finalMunicipio = municipio;
            Callable<Map<String, String>> transaccion = () -> {
                return validacionesSolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(tipoIdAdmin, numeroIdAdmin, valorSolicitado,
                        usuario, idTransaccionTercerPagador, departamento, finalMunicipio, user, password, idPuntoCobro,
                        userDTO, identificadorRespuesta, parametrosIN);
            };

            futureTransaccion = executor.submit(transaccion);
            respuesta = futureTransaccion.get(tiempo, TimeUnit.SECONDS);
            //futureTransaccion.cancel(true);

            //respuesta.put("CODIGO_ERROR", "BAD_REQUEST");

            logger.info("respuesta " + respuesta);
            idRetiro = respuesta.get("idRetiro");
            String cuentasVacias = respuesta.get("cuentasVacias");
            logger.info("idRetiro FINAL" + idRetiro);
            if ((respuesta.get("CODIGO_ERROR") != null && !respuesta.get("CODIGO_ERROR").equals("OK")) &&
                    ((idRetiro != null && !idRetiro.equals("0")) || "0".equals(cuentasVacias))) {
                logger.info("ingresa a respuesta exitosa");
                logger.info("idRetiro " + idRetiro);
                RollbackRetiro rollbackRetiro = new RollbackRetiro(idPuntoCobro, idTransaccionTercerPagador, usuario, cuentaAdministradorSubsidioYDetallesDTO, idRetiro);
                rollbackRetiro.execute();
                logger.info("salio del rollback");
                respuesta.put("error", "Error, hubo un error al hacer el retiro.");
                respuesta.put("CODIGO_ERROR", "BAD_REQUEST");
                respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                return respuesta;
            }


            fechaHoraFinal = new Date();
            long diferenciaMilisegundos = ((fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L);
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, respuesta.toString(), String.valueOf(diferenciaMilisegundos), url);
            return respuesta;

        } catch (RuntimeException | TimeoutException e) {
            salida = new StringBuilder();
            fechaHoraFinal = new Date();
            long diferenciaMilisegundos = ((fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L);
            logger.error("Una o más validaciones tardaron más de lo esperado", e);
            RollbackRetiro rollbackRetiro = new RollbackRetiro(idPuntoCobro, idTransaccionTercerPagador, usuario, cuentaAdministradorSubsidioYDetallesDTO, idRetiro);
            rollbackRetiro.execute();
            futureTransaccion.cancel(true); // Cancela la tarea y permite la interrupción
            respuesta.put("error", "Error, el sistema demoro mas tiempo del esperado al hacer la peticion.");
            respuesta.put("CODIGO_ERROR", "GATEWAY_TIMEOUT");
            respuesta.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            respuesta.put("tiempo", String.valueOf(diferenciaMilisegundos));
            salida.append(gson.toJson(respuesta));
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, salida.toString(), String.valueOf(diferenciaMilisegundos), url);
            return respuesta;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Sucedio un error en el proceso, volver a intentar", e);
            salida = new StringBuilder();
            //futureTransaccion.cancel(true);
            respuesta.put("error", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getDescripcion());
            respuesta.put("codigoError", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoError());
            respuesta.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoHttp());
            salida.append(gson.toJson(respuesta));
            RollbackRetiro rollbackRetiro = new RollbackRetiro(idPuntoCobro, idTransaccionTercerPagador, usuario, cuentaAdministradorSubsidioYDetallesDTO, idRetiro);
            rollbackRetiro.execute();
            fechaHoraFinal = new Date();
            long diferenciaMilisegundos = ((fechaHoraFinal.getTime() - fechaHoraInicio.getTime()) + 2000L);
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, salida.toString(), String.valueOf(diferenciaMilisegundos), url);
            return respuesta;
        } finally {
            executor.shutdownNow();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Map<String, String> validacionSolicitarRetiroConfirmarValorEntregadoV3(
            String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
            String usuario, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, String password, String idPuntoCobro, UserDTO userDTO)
    {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validacionSolicitarRetiroConfirmarValorEntregadoV3(String,String,BigDecimal,String,String,String,String,String,String,String,UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);


        Map<String, String> salidaValidacion = null;
        Long idSitioDePago = null;
        String codigoMunicpioCCF = null;
        TipoIdentificacionEnum tipoIdentificacionEnum = null;

        try
        {
            salidaValidacion = new HashMap<>();

            // ########## se valida la obligatoriedad de los campos ##########
            logger.info("Validaciones obligatoriedad de campos");
            if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || StringUtils.isEmpty(usuario)
                    || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(departamento) || StringUtils.isEmpty(municipio)
                    || StringUtils.isEmpty(user) || StringUtils.isEmpty(password) || StringUtils.isEmpty(idPuntoCobro)) {

                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoHttp());

                return salidaValidacion;
            }

            //BigDecimal valorSolicitado = new BigDecimal(valorSolicitadoStr);

            // ########## validacion de expresiones regulares ##########
            logger.info("Validaciones expresiones regulares");

            try
            {
                ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
            }
            catch(ErrorExcepcion e)
            {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoHttp());

                return salidaValidacion;
            }

            try
            {
                ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
            }
            catch(ErrorExcepcion e)
            {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getCodigoHttp());

                return salidaValidacion;
            }


            String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
            String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);

            ErroresServiciosEnum validacionesValorSolicitado = ValidatorUtil.validaValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));

            if(validacionesValorSolicitado != null)
            {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", validacionesValorSolicitado.getDescripcion());
                salidaValidacion.put("codigoError", validacionesValorSolicitado.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", validacionesValorSolicitado.getCodigoHttp());

                return salidaValidacion;
            }


            // ########## se validan las credenciales del usuario ##########
            logger.info("Validaciones credenciales de usuario");
            Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

            Map<String, String> respuesta = null;

            if (usuarioValidado != null && usuarioValidado)
            {
                try {
                    // ########## Se valida el tipo de identificacion ##########
                    logger.info("Validaciones tipo de identificacion");
                    tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdAdmin);
                } catch (Exception e) {
                    logger.info("Entrando a Error de Validacion tipoIdentificacionEnum");

                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getDescripcion());
                    salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoHttp());

                    return salidaValidacion;
                }

                // ########## Se valida el numero de identificacion ##########
                logger.info("Validaciones numero de identificacion");
                ErroresServiciosEnum validacionesIdentificacion = ValidatorUtil.validarNumeroDocumento(numeroIdAdmin, tipoIdentificacionEnum);
                if(validacionesIdentificacion != null)
                {
                    logger.info("Entrando a Error de Validacion numeroIdentificacion");

                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", validacionesIdentificacion.getDescripcion());
                    salidaValidacion.put("codigoError", validacionesIdentificacion.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", validacionesIdentificacion.getCodigoHttp());

                    return salidaValidacion;
                }

                try
                {
                    // ########## Se valida el administrador de subsidio ###############
                    logger.info("Validaciones administrador de subsidio");
                    AdministradorSubsidioModeloDTO admin = validarAdministrador(tipoIdentificacionEnum, numeroIdAdmin);
                }
                catch(Exception e)
                {
                    logger.info("Entrando a Error de Validacion del administrador de subsidio");

                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getDescripcion());
                    salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getCodigoHttp());

                    return salidaValidacion;
                }

            } else {
                logger.info("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoHttp());

                return salidaValidacion;
            }

            // ########## Validamos el sitio de pago ##########
            logger.info("Validaciones sitio de pago");
            if (municipio == null || municipio.isEmpty())
            {
                //Se establece por defecto el municpío de la CCF por parametro del sistema
                codigoMunicpioCCF = (String) CacheManager.getParametro(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID);
                municipio = consultasCore.buscarMunicipioPorCodigo(codigoMunicpioCCF);
            } else {
                idSitioDePago = consultasCore.consultarSitioDePago(municipio);
            }

            String codDepartamento = consultasCore.consultarDepartamentoPorMunicipio(municipio);

            if(codDepartamento != null && departamento!=null){
                if(!departamento.equals(codDepartamento)){
                    //respuesta no existosa, no se encontro id sitio de pago asociado
                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getDescripcion());
                    salidaValidacion.put("codigoError", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.SITIO_PAGO_NO_ENCONTRADO.getCodigoHttp());
                    return salidaValidacion;
                }
            }

            // ########## Se valida usuario tercero pagador ##########
            logger.info("Validaciones usuario tercero pagador");
            ConvenioTercerPagadorDTO convenioTercerPagadorDTO = consultasCore.consultarConvenioTerceroPagadorPorNombrePagos(usuario);
            if(convenioTercerPagadorDTO == null)
            {
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getCodigoHttp());
                return salidaValidacion;
            }
            else if (convenioTercerPagadorDTO.getEstadoConvenio().equals(EstadoConvenioEnum.INACTIVO))
            {
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getCodigoHttp());
                return salidaValidacion;
            }

            logger.info("Consulta saldo del administrador de subsidio");
            respuesta = consultarSaldoSubsidio(tipoIdentificacionEnum, numeroIdAdmin, TipoMedioDePagoEnum.EFECTIVO, userDTO, user);
            String saldoSubsidio = respuesta.get("saldoAdminSubsidio");
            logger.info("saldoSubsidio " +saldoSubsidio);
            /*for (Map.Entry<String, String> entry : respuesta.entrySet()) {
                //logger.info("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
                salidaValidacion.put(entry.getKey(), entry.getValue());
            }*/

            salidaValidacion.put("tipoIdentificacionAdministradorSubsidio", tipoIdAdmin);
            salidaValidacion.put("identificacionAdministradorSubsidio", numeroIdAdmin);
            salidaValidacion.put("saldoAdminSubsidio", saldoSubsidio);
        }
        catch(Exception e)
        {
            logger.info("Ocurrio un error inesperado");
            salidaValidacion = new HashMap<>();
            salidaValidacion.put("resultado", String.valueOf(false));
            salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
            salidaValidacion.put("error", "Ocurrio un error inesperado");
            salidaValidacion.put("codigoError", "00");
            salidaValidacion.put("CODIGO_ERROR", "INTERNAL_SERVER_ERROR");
            return salidaValidacion;
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        logger.info("SALIDA VALIDACIONES VALOR 3 " + salidaValidacion);
        return salidaValidacion;
    }

    public Map<String, String> consultarSaldoSubsidioTransaccionValidaciones(String tipoIdAdmin, String numeroIdAdmin, String user, String password) {
    
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.consultarSaldoSubsidioTransaccion(TipoIdentificacionEnum,String,String,String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("tipoIdAdmin"+tipoIdAdmin);
        logger.info("numeroIdAdmin"+numeroIdAdmin);
        logger.info("user"+user);
  
        //se validan las credenciales del usuario
        Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

        Map<String, String> respuesta = null;
        if (usuarioValidado != null && usuarioValidado) {
            TipoIdentificacionEnum tipoIdentificacionEnum = null;
            try {
                tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdAdmin);
            } catch (Exception e) {
                logger.info("Entrando a Error de Validacion tipoIdentificacionEnum");
                throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_TIPO_IDENTIFICACION);
            }
            ValidatorUtil.validatNumeroDocumento(numeroIdAdmin, tipoIdentificacionEnum);
            AdministradorSubsidioModeloDTO admin = validarAdministrador(tipoIdentificacionEnum, numeroIdAdmin);
            respuesta = consultarSaldoSubsidio(tipoIdentificacionEnum, numeroIdAdmin, user);
            respuesta.put("tipoIdentificacionAdministradorSubsidio", tipoIdAdmin);
            respuesta.put("identificacionAdministradorSubsidio", numeroIdAdmin);

        } else {
            logger.info("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_USUARIO_CONTRASENNA);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    private Boolean validarCredencialesUsuarios(String userName, String password) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarCredencialesUsuarios(String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ValidarCredencialesUsuario validacionUsuario = null;
        try {
            validacionUsuario = new ValidarCredencialesUsuario(userName, password);
            validacionUsuario.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return validacionUsuario.getResult();
    }

    private AdministradorSubsidioModeloDTO validarAdministrador(TipoIdentificacionEnum tipoIdentificacionEnum, String numeroIdAdmin) {
        try {
            return consultarAdministradorSubsidioGeneral(numeroIdAdmin, tipoIdentificacionEnum);
        } catch (Exception e) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_ADMINISTRADOR_SUBSIDIO);
        }
    }

    private AdministradorSubsidioModeloDTO consultarAdministradorSubsidioGeneral(String numeroIdAdmin, TipoIdentificacionEnum tipoIdAdmin) {
        ConsultarAdministradorSubsidioGeneral admin = new ConsultarAdministradorSubsidioGeneral(null, null, null, numeroIdAdmin, tipoIdAdmin, null);
        admin.execute();
        return admin.getResult().get(0);
    }

    private Map<String, String> consultarSaldoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin, String user) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validarCredencialesUsuarios(String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConsultarSaldoSubsidio saldo = null;
        try {
            saldo = new ConsultarSaldoSubsidio(numeroIdAdmin, user, TipoMedioDePagoEnum.EFECTIVO, tipoIdAdmin);
            saldo.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return saldo.getResult();
    }
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response reversionRetiro(String tipoIdentificacion, String numeroIdentificacion, String valorTransaccion, String codigoMotivo,
                                    String usuarioTerceroPagador, String idTransaccion, String idPuntoCobro, String lineaNegocio, String usuario,
                                    String contrasena) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.reversionRetiro(String ,String, BigDecimal, String, String, String, String, String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Date fechaHoraInicio = new Date();
        String url = "reversionRetiro";
        String parametrosOUT = null;
        StringBuilder salidaGson = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        salidaGson = new StringBuilder();
        Long identificadorRespuesta;
        Map<String, String> salida = new HashMap<>();
        Map<String, Object> parametrosEntrada = new HashMap<>();
        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdentificacion);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdentificacion);
        parametrosEntrada.put("valorTransaccion", valorTransaccion);
        parametrosEntrada.put("codigoMotivo", codigoMotivo);
        parametrosEntrada.put("idTransaccion", idTransaccion);
        parametrosEntrada.put("idPuntoCobro", idPuntoCobro);
        parametrosEntrada.put("usuarioTerceroPagador", usuarioTerceroPagador);
        parametrosEntrada.put("lineaNegocio", lineaNegocio);
        parametrosEntrada.put("usuario", usuario);
        salidaGson.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salidaGson.toString();
        List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = null;
        Response rep = validacionesReverso(tipoIdentificacion,numeroIdentificacion, valorTransaccion, codigoMotivo,
                usuarioTerceroPagador, idTransaccion, idPuntoCobro, lineaNegocio, usuario,contrasena);
        Object salida1 = rep.getEntity();
        //logger.info("respuestaMap--> " +salida1.toString().get("idCuentaAdministradorRetiro"));
        logger.info("respuestaMapSolito--> " +salida1);
        logger.info("rep.getStatus()" + rep.getStatus());
        String respuesta = salida1.toString();
        JsonReader reader = new JsonReader(new StringReader(respuesta));
        reader.setLenient(true);
        JsonObject convertedObject = new Gson().fromJson(reader, JsonObject.class);
        MotivoAnulacionSubsidioAsignadoEnum motivoAnulacionSubsidioAsignadoEnum = MotivoAnulacionSubsidioAsignadoEnum.valueOf(codigoMotivo);
        logger.info("convertedObject " + convertedObject);
        if (rep.getStatus() == 200) {
            Long adminRetiro = null;
            Long cuentaAdministradorSubsidioRetiro = null;
            List<CuentaAdministradorSubsidioDTO> listadoRetiros = consultasCore.listadoValoresRetiros(idTransaccion, usuarioTerceroPagador, idPuntoCobro);
            cuentaAdministradorSubsidioRetiro = listadoRetiros.get(0).getIdCuentaAdministradorSubsidio();
            logger.info("cuentaAdministradorSubsidioRetiro " + cuentaAdministradorSubsidioRetiro);
            adminRetiro = listadoRetiros.get(0).getIdAdministradorSubsidio();
            logger.info("adminRetiro reversion " + adminRetiro);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, adminRetiro);
            try{
                UserDTO user = new UserDTO();
                user.setNombreUsuario(usuario);
                user.setEmail(usuario);

                AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = prepararDataReverso(usuarioTerceroPagador, idTransaccion, idPuntoCobro);
                if (abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO() != null && !abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO().isEmpty()) {
                    logger.info("abonoAnuladoDetalleAnuladoDTO " + abonoAnuladoDetalleAnuladoDTO);
                    logger.info("cuentas " + abonoAnuladoDetalleAnuladoDTO.getListaIdsCuentasAdmonSubsidios().size());
                    logger.info("detalles " + abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO().size());
                    for (Long idCuentaAdmonSubsidio : abonoAnuladoDetalleAnuladoDTO.getListaIdsCuentasAdmonSubsidios()) {
                        logger.info("idCuentaAdmonSubsidio " + idCuentaAdmonSubsidio);
                        listaDetallesDTOAnular = new ArrayList<>();
                        for (DetalleSubsidioAsignadoDTO detalle : abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO()) {
                            logger.info("detalle " + detalle);
                            if (detalle.getIdCuentaAdministradorSubsidio().longValue() == idCuentaAdmonSubsidio.longValue()) {
                                detalle.setFechaTransaccionRetiro(null);
                                detalle.setUsuarioTransaccionRetiro(null);
                                detalle.setMotivoAnulacion(motivoAnulacionSubsidioAsignadoEnum);
                                logger.info("detalle 2.0 -->" + detalle);
                                listaDetallesDTOAnular.add(detalle);
                            }
                        }

                        if (!listaDetallesDTOAnular.isEmpty()) {
                            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = consultarCuentaAdmonSubsidioDTO(idCuentaAdmonSubsidio);
                            boolean validacionCuenta = true;
                            SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioOrigDTO, listaDetallesDTOAnular);
                            Long nuevoAbono = anularSubsidioMonetarioConReemplazo(subsidioAnulacionDTO, user, validacionCuenta);
                            logger.info("nuevoAbono -->  " + nuevoAbono);
                            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioNuevoDTO = consultarCuentaAdmonSubsidioDTO(nuevoAbono);
                            cuentaAdministradorSubsidioNuevoDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                            consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioNuevoDTO);
                        }

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

                    }
                    CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = consultarCuentaAdmonSubsidioDTO(cuentaAdministradorSubsidioRetiro);
                    cuentaAdministradorSubsidioOrigDTO.setValorRealTransaccion(BigDecimal.ZERO);
                    logger.info("actualiza a ZERO");
                    consultasCore.actualizarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioOrigDTO);
                } else {
                    salida.put("error", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRARON_ABONOS.getDescripcion());
                    salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRARON_ABONOS.getCodigoError());
                    salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                    logger.info("No se encontraron abonos--> " + salida);
                    salidaGson.append(gson.toJson(salida));
                    parametrosOUT = salidaGson.toString();
                    Date fechaHoraFinal = new Date();
                    long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                    consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                    return Response.status(Response.Status.BAD_REQUEST)
                            .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                            .build();
                }
            } catch (Exception e) {
                salida.put("error", ErroresServiciosEnum.ERROR_SERVICIO_ANULACION.getDescripcion());
                salida.put("codigoError", ErroresServiciosEnum.ERROR_SERVICIO_ANULACION.getDescripcion() + e.getMessage());
                salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                logger.info("error interno en el servicio de anulacion--> " + salida);
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                Date fechaHoraFinal = new Date();
                long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                        .build();
            }
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(rep.getEntity())
                    .build();
        }
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return rep;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private AbonoAnuladoDetalleAnuladoDTO prepararDataReverso (String usuarioTerceroPagador, String idTransaccion, String idPuntoCobro){
        List<CuentaAdministradorSubsidioDTO> listadoAbonosRetiro = consultasCore.listadoAbonosRetiro(idTransaccion, usuarioTerceroPagador, idPuntoCobro);
        List<DetalleSubsidioAsignadoDTO> lstDetalleSubsidioAsignadoResultService = null;
        Long idCuentaAdministradorSubsidio = null;
        List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO = new ArrayList<>();
        List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = null;
        SubsidioAnulacionDTO subsidioAnulacionDTO = null;
        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();
        List<Long> listaIdsCuentasAdmonSubsidios = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : listadoAbonosRetiro) {
            idCuentaAdministradorSubsidio = cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio().longValue();
            logger.info("idCuentaAdministradorSubsidio" + idCuentaAdministradorSubsidio);
            listaIdsCuentasAdmonSubsidios.add(idCuentaAdministradorSubsidio);
            logger.info("cuentaAdministradorSubsidioDTO" + listaIdsCuentasAdmonSubsidios.size());
            lstDetalleSubsidioAsignadoResultService = consultarDetallesSubsidiosAsignadosAsociadosAbono(idCuentaAdministradorSubsidio);
            logger.info("lstDetalleSubsidioAsignadoResultService" + lstDetalleSubsidioAsignadoResultService.size());

            if (lstDetalleSubsidioAsignadoResultService != null && !lstDetalleSubsidioAsignadoResultService.isEmpty()) {
                listaAnularDetallesDTO.addAll(lstDetalleSubsidioAsignadoResultService);
            }
        }
        logger.info("lstDetalleSubsidioAsignadoResultService " +lstDetalleSubsidioAsignadoResultService.size());
        logger.info("listaIdsCuentasAdmonSubsidios " +listaIdsCuentasAdmonSubsidios.size());
        abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(listaAnularDetallesDTO);
        abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(listaIdsCuentasAdmonSubsidios);
        return abonoAnuladoDetalleAnuladoDTO;

    }

    private  Response validacionesReverso(String tipoIdentificacion, String numeroIdentificacion, String valorTransaccionStr, String codigoMotivo,
                                          String usuarioTerceroPagador, String idTransaccion, String idPuntoCobro, String lineaNegocio, String usuario,
                                          String contrasena) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validacionesReverso(String ,String, BigDecimal, String, String, String, String, String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, String> salida = new HashMap<>();
        String mensaje = null;
        Date fechaHoraInicio = new Date();
        String url = "metodo-validacionesReverso";

        StringBuilder salidaGson = new StringBuilder();
        Gson gson = new GsonBuilder().create();

        Map<String, String> respuesta = new HashMap<>();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdentificacion);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdentificacion);
        parametrosEntrada.put("valorTransaccion", valorTransaccionStr);
        parametrosEntrada.put("codigoMotivo", codigoMotivo);
        parametrosEntrada.put("idTransaccion", idTransaccion);
        parametrosEntrada.put("idPuntoCobro", idPuntoCobro);
        parametrosEntrada.put("usuarioTerceroPagador", usuarioTerceroPagador);
        parametrosEntrada.put("lineaNegocio", lineaNegocio);
        parametrosEntrada.put("usuario", usuario);
        salidaGson.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salidaGson.toString();
        logger.info("parametrosIN-> " +parametrosIN);
        Long identificadorRespuesta = 1L;
        String parametrosOUT = null;
        salidaGson = new StringBuilder();

        //Obligatoriedad de los campos
        logger.info("inicia validacion obligatoriedad campos");
        if ((tipoIdentificacion == null || tipoIdentificacion.isEmpty()) || (numeroIdentificacion == null || numeroIdentificacion.isEmpty() ) ||
                (valorTransaccionStr == null || valorTransaccionStr.isEmpty()) || (codigoMotivo == null || codigoMotivo.isEmpty()) || (usuarioTerceroPagador == null || usuarioTerceroPagador.isEmpty() ) ||
                (idTransaccion == null || idTransaccion.isEmpty()) || (idPuntoCobro == null || idPuntoCobro.isEmpty()) || (lineaNegocio == null || lineaNegocio.isEmpty()) ||
                (usuario == null || usuario.isEmpty()) || (contrasena == null || contrasena.isEmpty())) {
            logger.debug("Entrando a Error de Validacion");
            salida.put("error", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoError());
            logger.info("validacion no exitosa--> " +salida);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        BigDecimal valorTransaccion = new BigDecimal(valorTransaccionStr);

        //Validación de usuario y contrasena para autenticidad
        logger.info("inicia validacion de usuario y contraseña");
        try {
            Boolean usuarioValidado = validarCredencialesUsuarios(usuario, contrasena);
            if (usuarioValidado == null || Boolean.FALSE.equals(usuarioValidado)) {
                salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getDescripcion());
                salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoError());
                logger.info("validacion no exitosa--> " + salida);
                identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
                salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                Date fechaHoraFinal = new Date();
                long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                        .build();
            }
        }catch (Exception e){
            salida.put("error", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoError());
            logger.info("validacion no exitosa--> " + salida);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        //Validar Tipo y número de identificación
        TipoIdentificacionEnum tipoIdentificacionEnum = null;
        //Se valida el tipo de identificacion
        logger.info("inicia validacion tipo de identificacion");
        try {
            logger.info("Validaciones tipo de identificacion");
            tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        } catch (Exception e) {
            logger.info("Entrando a Error de Validacion tipoIdentificacionEnum");
            salida.put("error", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoError());
            logger.info("validacion no exitosa--> " +salida);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        //Se valida el numero de identificacion
        logger.info("Validaciones numero de identificacion");
        ErroresServiciosEnum validacionesIdentificacion = ValidatorUtil.validarNumeroDocumento(numeroIdentificacion, tipoIdentificacionEnum);
        if(validacionesIdentificacion != null){
            salida.put("error", validacionesIdentificacion.getDescripcion());
            salida.put("codigoError", validacionesIdentificacion.getCodigoError());
            logger.info("validacion no exitosa--> " +salida);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        // Se valida el administrador de subsidio
        AdministradorSubsidioModeloDTO admin = new AdministradorSubsidioModeloDTO();
        try {
            logger.info("Validaciones administrador de subsidio");
            //se registra la operación
            admin = validarAdministrador(tipoIdentificacionEnum, numeroIdentificacion);
            logger.info("admin " + admin.getIdAdministradorSubsidio());
        }
        catch(Exception e){
            logger.info("Entrando a Error de Validacion del administrador de subsidio");
            salida.put("error", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getCodigoError());
            //Se registra la opercion
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,parametrosIN, usuario, admin.getIdAdministradorSubsidio());
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        //Se valida el idTransaccionTercerPagador
        try {
            logger.info("Validaciones idTransaccionTercerPagador");
            ValidatorUtil.validarTerceroPagador(idTransaccion);
        } catch (Exception e) {
            logger.info("Entrando a Error de Validacion idTransaccionTercerPagador");
            salida.put("error", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoError());
            //Se registra la opercion
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            logger.info("validacion no exitosa--> " + salida);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        //ValidarUsuarioPagador
        Object usuarioPagador = consultasCore.consultarUsuarioTerceroPagador(usuarioTerceroPagador);
        if (usuarioPagador == null || usuarioPagador.equals("")) {
            logger.info("Entrando a: usuario convenio no existe");
            salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getDescripcion());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getCodigoError());
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        } else {
            if (usuarioPagador.equals(EstadoConvenioEnum.INACTIVO.toString())) {
                logger.info("Entrando a: usuario convenio esta inactivo");
                salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getDescripcion());
                salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getCodigoError());
                identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
                salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                Date fechaHoraFinal = new Date();
                long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                        .build();
            }
        }

        Integer retirosEnProceso = consultasCore.consultarTransaccionProceso(tipoIdentificacion,numeroIdentificacion);
        if(retirosEnProceso != null && retirosEnProceso > 0){
            logger.info("Entrando a: Validacion retiros en proceso");
            salida.put("error", "Aun se esta realizando una transaccion, por favor verifique en unos minutos");
            salida.put("codigoError", ErroresServiciosEnum.ADMINISTRADOR_NO_COINCIDE.getCodigoError());
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        List<CuentaAdministradorSubsidioDTO> listadoRetiros = consultasCore.listadoValoresRetiros(idTransaccion, usuarioTerceroPagador, idPuntoCobro);
        if (listadoRetiros != null && !listadoRetiros.isEmpty()) {
            Long adminRetiro = listadoRetiros.get(0).getIdAdministradorSubsidio();
            logger.info("adminRetiro" + adminRetiro);
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,
                    parametrosIN, usuario, adminRetiro);

            //Se valida que el administrador de susbsidio del retiro sea el mismo que haga el reverso
            if (!admin.getIdAdministradorSubsidio().equals(adminRetiro)) {
                logger.info("Entrando a: Validacion administrador de susbsidio del retiro sea el mismo que haga el reverso");
                salida.put("error", ErroresServiciosEnum.ADMINISTRADOR_NO_COINCIDE.getDescripcion());
                salida.put("codigoError", ErroresServiciosEnum.ADMINISTRADOR_NO_COINCIDE.getCodigoError());
                salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                Date fechaHoraFinal = new Date();
                long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                        .build();
            }

            //Validar Valor transacción
            if (listadoRetiros.size() > 1) {
                logger.info("La lista trae mas de un registro");
                salida.put("error", ErroresServiciosEnum.ERROR_TRAE_MAS_DE_UN_VALOR.getDescripcion());
                salida.put("codigoError", ErroresServiciosEnum.ERROR_TRAE_MAS_DE_UN_VALOR.getCodigoError());
                salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                Date fechaHoraFinal = new Date();
                long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                        .build();
            } else {
                logger.info("La lista no está vacía" + listadoRetiros.size());
                BigDecimal valorTransaccionReal = listadoRetiros.get(0).getValorRealTransaccion();
                logger.info("valorTransaccionReal " + valorTransaccionReal);
                BigDecimal valorAbsolutoTransaccionReal = new BigDecimal(String.valueOf(valorTransaccionReal.abs()));
                logger.info("valorAbsolutoTransaccionReal " + valorAbsolutoTransaccionReal);
                Integer diferencia = valorTransaccion.compareTo(valorAbsolutoTransaccionReal);
                switch (diferencia) {
                    case 1:
                        logger.info("El valor transaccion es mayor al valor real transaccion");
                        salida.put("error", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MAYOR_VALOR_REAL.getDescripcion());
                        salida.put("codigoError", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MAYOR_VALOR_REAL.getCodigoError());
                        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                        salidaGson.append(gson.toJson(salida));
                        parametrosOUT = salidaGson.toString();
                        Date fechaHoraFinal = new Date();
                        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                        return Response.status(Response.Status.BAD_REQUEST)
                                .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                                .build();
                    case -1:
                        logger.info("El valor transaccion es menor al valor real transaccion");
                        salida.put("error", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MENOR_VALOR_REAL.getDescripcion());
                        salida.put("codigoError", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MENOR_VALOR_REAL.getCodigoError());
                        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
                        salidaGson.append(gson.toJson(salida));
                        parametrosOUT = salidaGson.toString();
                        fechaHoraFinal = new Date();
                        diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
                        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
                        return Response.status(Response.Status.BAD_REQUEST)
                                .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                                .build();
                }

            }
        }else{
            logger.info("La lista esta vacia");
            //Se registra la opercion
            identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,
                    parametrosIN, usuario, null);
            salida.put("error", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getDescripcion().toString());
            salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getCodigoError().toString());
            salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
            salidaGson.append(gson.toJson(salida));
            parametrosOUT = salidaGson.toString();
            Date fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                    .build();
        }

        salida.put("codigo", "OK");
        salida.put("mensaje", "La transaccion fue exitosa");
        salida.put("resultado", String.valueOf(true));
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));

        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        return Response.ok(parametrosOUT,MediaType.APPLICATION_JSON).build();


    }

    @Override
    public Boolean validarExistenciaTarjeta(String numeroExpedido){
        return consultasCore.validarExistenciaTarjeta(numeroExpedido);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Map<String, String> consultarEstadoDispercion(String numeroRadicacion){
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarEstadoDispercion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        String estadoDispercion = consultasCore.consultarEstadoDispercion(numeroRadicacion);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", estadoDispercion);
        return respuesta;
    }
    @Override
    public List<Long> consultarGruposFamiliaresConMarcaYAdmin(String tipoIdentificacion,String numeroIdentificacion, String numeroTarjeta, Boolean expedicion){
        try{
            return consultasCore.consultarGruposFamiliaresConMarcaYAdmin(tipoIdentificacion,numeroIdentificacion, numeroTarjeta, expedicion);
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    @Override
    public CuentaAdministradorSubsidioYDetallesDTO consultarInfoRetiro(String tipoIdAdmin, String numeroIdAdmin, TipoMedioDePagoEnum medioDePago) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarInfoRetiro(String ,String, TipoMedioDePagoEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<CuentaAdministradorSubsidioDTO> listaCuentaAdminSubsidio;
        List<DetalleSubsidioAsignado> listaDetalleSubsidioAsignado;
        CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO = new CuentaAdministradorSubsidioYDetallesDTO();
        try {
            listaCuentaAdminSubsidio = consultasCore.consultarRegistrosAbonosParaCalcularSaldoSubsidio(TipoIdentificacionEnum.valueOf(tipoIdAdmin), numeroIdAdmin, medioDePago);
            List<Long> listId = new ArrayList<>();
            for (CuentaAdministradorSubsidioDTO cuenta : listaCuentaAdminSubsidio){
                listId.add(cuenta.getIdCuentaAdministradorSubsidio());
            }
            listaDetalleSubsidioAsignado = consultasCore.consultarDetallesRestaurar(listId);
            //Se itera para agregar una hora a los periodos
            for(DetalleSubsidioAsignado detalleSubsidioAsignado :listaDetalleSubsidioAsignado){
                Date fecha = detalleSubsidioAsignado.getPeriodoLiquidado();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date fechaSinDesfase = calendar.getTime();
                detalleSubsidioAsignado.setPeriodoLiquidado(fechaSinDesfase);
            }
            cuentaAdministradorSubsidioYDetallesDTO.setListaDetalleSubsidioAsignado(listaDetalleSubsidioAsignado);
            cuentaAdministradorSubsidioYDetallesDTO.setListaCuentaAdministradorSubsidio(listaCuentaAdminSubsidio);

        } catch (Exception e) {
            cuentaAdministradorSubsidioYDetallesDTO.setListaCuentaAdministradorSubsidio(new ArrayList<>());
            cuentaAdministradorSubsidioYDetallesDTO.setListaDetalleSubsidioAsignado(new ArrayList<>());
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentaAdministradorSubsidioYDetallesDTO;
    }


    @Override
    @Asynchronous
    public void rollbackRetiro(String idPuntoCobro, String idTransaccionTercerPagador,
                               String usuario, CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO, String idRetiro) {
        logger.info("ingresa a rollback retiro "+ idTransaccionTercerPagador+"----------------------> ");
        Long retiro = 0L;
        List<Long> listId = new ArrayList<>();
        List<CuentaAdministradorSubsidio> cuentasPrevias = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuenta : cuentaAdministradorSubsidioYDetallesDTO.getListaCuentaAdministradorSubsidio()) {
            listId.add(cuenta.getIdCuentaAdministradorSubsidio());
            cuentasPrevias.add(cuenta.convertToEntity());
        }

        List<CuentaAdministradorSubsidioDTO> listadoRetiros = consultasCore.listadoValoresRetiros(idTransaccionTercerPagador, usuario, idPuntoCobro);
        Long idRetiroExitoso = null;
        for (CuentaAdministradorSubsidioDTO cuentaRetiro : listadoRetiros) {
            List<CuentaAdministradorSubsidio> cuentas = new ArrayList<>();
            cuentas = consultasCore.consultarAbonosRelacionadosRetiro(cuentaRetiro.getIdCuentaAdministradorSubsidio());
            if (!cuentas.isEmpty()) {
                String codigo = null;
                RegistroOperacionTransaccionSubsidio registroOperacion = null;
                if (cuentaRetiro.getIdRemisionDatosTerceroPagador() != null) {
                    registroOperacion = consultasCore.buscarRegistroOperacionSubsidio(Long.valueOf(cuentaRetiro.getIdRemisionDatosTerceroPagador()));
                    //logger.info("parametrosOUT " + registroOperacion.getParametrosOUT());
                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Object>>() {
                    }.getType();
                    Map<String, Object> resultMap = gson.fromJson(registroOperacion.getParametrosOUT(), type);
                    codigo = (String) resultMap.get("CODIGO_ERROR");
                    if (resultMap == null || !codigo.equals("OK")) {
                        List<DetalleSubsidioAsignadoDTO> detalles = consultasCore.consultarDetallesPorCuenta(cuentaRetiro.getIdCuentaAdministradorSubsidio());
                        if (detalles == null || detalles.isEmpty()) {
                            retiro = cuentaRetiro.getIdCuentaAdministradorSubsidio();
                            consultasCore.consultarRetirosParciales(retiro);
                        }
                        consultasCore.restaurarDetallesSubsidioAsignado(cuentaAdministradorSubsidioYDetallesDTO.getListaDetalleSubsidioAsignado());
                        consultasCore.restaurarCuentaSubsidioAsignado(cuentasPrevias);
                        consultasCore.eliminarCuentAdministradorSubsidio(cuentaRetiro.convertToEntity());
                    }
                }else{
                }

            } else {
                consultasCore.consultarRetirosParciales(cuentaRetiro.getIdCuentaAdministradorSubsidio());
                consultasCore.eliminarCuentAdministradorSubsidio(cuentaRetiro.convertToEntity());
            }
        }


        logger.info("finaliza rollback retiro "+ idTransaccionTercerPagador+"----------------------> ");
    }

    @Override
    public String consultaTrasladoMedioDePago(String numeroDocumento,TipoIdentificacionEnum tipoDocumento,List<TipoMedioDePagoEnum> medioDePago,String numeroTarjeta){
        logger.info("inicia metodo: consultaTrasladoMedioDePago(String numeroDocumento,TipoIdentificacionEnum tipoDocumento,List<TipoMedioDePagoEnum> medioDePago)");
        String json = consultasCore.consultarMedioYgruposParaTraslado(numeroDocumento,tipoDocumento,medioDePago,numeroTarjeta);
        logger.warn("Json que devuelve" + json );
        return json;
    }

    @Override
    public List<MedioDePagoModeloDTO> consultarInfoMedioDePagoTraslado(String idAdmin, TipoMedioDePagoEnum tipoMedioDePago) {
        logger.info("Inicia consultarInfoMedioDePagoTraslado(String idAdmin, TipoMedioDePagoEnum tipoMedioDePago)");

        String medioPago = String.valueOf(tipoMedioDePago);
        switch (medioPago) {
            case "TARJETA":
                return consultasCore.consultarInfoMedioTarjetaTraslado(idAdmin);
            case "TRANSFERENCIA":
                return consultasCore.consultarInfoMedioTransferenciaTraslado(idAdmin);
            default:
                return consultasCore.consultarInfoMedioEfectivoTraslado(idAdmin);
        }
    }

    @Override
    public void procesarTrasladoMedioDePago(Long idAdmin, Long idMedioDePagoDestino, MediosPagoYGrupoTrasladoDTO mediosPagoYGrupoTrasladoDTO, UserDTO user) {
        logger.info("Inicio de procesarTrasladoMedioDePago(Long idAdmin, Long idMedioDePagoDestino, MediosPagoYGrupoTrasladoDTO mediosPagoYGrupoTrasladoDTO, UserDTO user)");
        logger.info("mediosPagoYGrupoTrasladoDTO grupo familiar " +mediosPagoYGrupoTrasladoDTO.getIdsGrupoFamiliar());
        logger.info("mediosPagoYGrupoTrasladoDTO getIdsMediosDePagoPrevios " +mediosPagoYGrupoTrasladoDTO.getIdsMediosDePagoPrevios());

        List<CuentaAdministradorSubsidio> cuentasAdmin = consultasCore.consultarCuentasAdministradorTraslado(
                idAdmin,
                mediosPagoYGrupoTrasladoDTO.getIdsGrupoFamiliar(),
                mediosPagoYGrupoTrasladoDTO.getIdsMediosDePagoPrevios()
        );

        logger.warn("id admin " + idAdmin);
        Object[] infoAdmin = consultarDatosYRegistroAdmin(idAdmin);
        MedioDePagoModeloDTO medioDePagoDestino = consultasCore.consultarMedioDePagoTraslado(idMedioDePagoDestino);
        medioDePagoDestino.setTipoIdentificacionTitular(TipoIdentificacionEnum.valueOf(String.valueOf(infoAdmin[0])));
        medioDePagoDestino.setNumeroIdentificacionTitular(String.valueOf(String.valueOf(infoAdmin[1])));

        if (medioDePagoDestino.getTipoMedioDePago() == TipoMedioDePagoEnum.TARJETA) {
            procesarMedioPagoTarjeta(cuentasAdmin, medioDePagoDestino,mediosPagoYGrupoTrasladoDTO.getIdsGrupoFamiliar(),user,idAdmin);
        } else {
            procesarMedioPagoOtro(cuentasAdmin, medioDePagoDestino, idAdmin, user, mediosPagoYGrupoTrasladoDTO.getIdsGrupoFamiliar());
        }

        logger.info("Finaliza procesarTrasladoMedioDePago");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void procesarMedioPagoTarjeta(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado,List<Long> idsGrupoFamiliar, UserDTO user, Long idAdmin) {
        logger.info("Inicio de procesarMedioPagoTarjeta(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado)");
        List<Long> idsCuentaAdminAmibolDescuento = new ArrayList<>();
        List<Long> idsCuentaAdminAnibolAbono = new ArrayList<>();
        List<Long> idsDetalleAnibol = new ArrayList<>();
        Set<Long> idMediosDePagoOrigen = new HashSet<>();
        String idProcesoAnibol = null;
        BigDecimal totalAbonar = BigDecimal.ZERO;

        bloquearAbonosATrasladar(cuentasAdmin,user);
        for (CuentaAdministradorSubsidio cuentaAdmin : cuentasAdmin) {
            idMediosDePagoOrigen.add(cuentaAdmin.getIdMedioDePago());
            idsCuentaAdminAnibolAbono.add(cuentaAdmin.getIdCuentaAdministradorSubsidio());
            List<DetalleSubsidioAsignado> detallesCuenta = consultasCore.consultarDetallesCuentaTraslado(cuentaAdmin.getIdCuentaAdministradorSubsidio(),idsGrupoFamiliar);
            for(DetalleSubsidioAsignado detalle : detallesCuenta){
                idsDetalleAnibol.add(detalle.getIdDetalleSubsidioAsignado());
                totalAbonar = totalAbonar.add(detalle.getValorTotal());
            }
        }
        
        idProcesoAnibol = ejecutarAbonoSaldo(medioTraslado, totalAbonar);
        if(idProcesoAnibol != null){
            registrarSolicitudAnibolTrasladoSaldos(armarDtoSolicitudAnibol(idsCuentaAdminAnibolAbono,idProcesoAnibol,true,medioTraslado.getIdMedioDePago(),idsDetalleAnibol));
            if(idMediosDePagoOrigen.size()>0){
                List<Long> idsMedioDePagoList = new ArrayList<>(idMediosDePagoOrigen);
                for(Long idMedioDePagoOrigen : idsMedioDePagoList){
                    consultarDatosYRegistroAdmin(idAdmin,EstadoBandejaTransaccionEnum.EN_PROCESO,ProcesoBandejaTransaccionEnum.TRASLADO_SALDOS,idMedioDePagoOrigen,medioTraslado.getIdMedioDePago(),Long.valueOf(idProcesoAnibol));
                }
            }
        }else{
            // aca se debe restaurar el estado de las cuentas
            for (CuentaAdministradorSubsidio cuentaAdmin : cuentasAdmin){
                idMediosDePagoOrigen.add(cuentaAdmin.getIdMedioDePago());
                restablecerEstadoCuentasTraslado(cuentaAdmin.getIdCuentaAdministradorSubsidio(),user);
            }
            if(idMediosDePagoOrigen.size()>0){
                List<Long> idsMedioDePagoList = new ArrayList<>(idMediosDePagoOrigen);
                for(Long idMedioDePagoOrigen : idsMedioDePagoList){
                    consultarDatosYRegistroAdmin(idAdmin,EstadoBandejaTransaccionEnum.RECHAZADA,ProcesoBandejaTransaccionEnum.TRASLADO_SALDOS,idMedioDePagoOrigen,medioTraslado.getIdMedioDePago(),null);
                }
            }
        }
        logger.info("fin de procesarMedioPagoTarjeta(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado)");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private String ejecutarDescuentoSaldo(MedioDePagoModeloDTO medioTraslado, BigDecimal totalDescontar) {
        logger.info("Inicio de ejecutarDescuentoSaldo(CuentaAdministradorSubsidio cuentaAdmin)");
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<SaldoTarjetaDTO>();
        SaldoTarjetaDTO saldoTarjetaDTO = crearSaldoTarjetaDTO(medioTraslado,totalDescontar);
        saldosTarjetaDTO.add(saldoTarjetaDTO);

        DescontarSaldoTarjetas descontarSaldoTarjetas = new DescontarSaldoTarjetas(saldosTarjetaDTO);
        descontarSaldoTarjetas.execute();

        ResultadoAnibolDTO resultadoAnibolDTO = descontarSaldoTarjetas.getResult();
        if (resultadoAnibolDTO.isExitoso()) {
            return resultadoAnibolDTO.getIdProceso();
        }else{
            logger.info("din de ejecutarDescuentoSaldo(CuentaAdministradorSubsidio cuentaAdmin)");
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private String ejecutarAbonoSaldo(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar) {
        logger.info("Inicio ejecutarAbonoSaldo(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar)");
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<SaldoTarjetaDTO>();
        SaldoTarjetaDTO saldoTarjetaDTO = crearSaldoTarjetaDTO(medioTraslado, totalAbonar);
        saldosTarjetaDTO.add(saldoTarjetaDTO);

        AbonarSaldoTarjetas abonarSaldoTarjetas = new AbonarSaldoTarjetas(saldosTarjetaDTO);
        abonarSaldoTarjetas.execute();

        ResultadoAnibolDTO resultadoAnibolDTO = abonarSaldoTarjetas.getResult();
        if (resultadoAnibolDTO.isExitoso()) {
            return resultadoAnibolDTO.getIdProceso();
        }else{
            logger.error("cual es la respuesta de esta monda: "+resultadoAnibolDTO.toString());
            logger.error("cual es la respuesta de esta monda 2 "+resultadoAnibolDTO.getExitoso());
            logger.info("Fin ejecutarAbonoSaldo(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar)");
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private SaldoTarjetaDTO crearSaldoTarjetaDTO(CuentaAdministradorSubsidio cuentaAdmin,BigDecimal totalDescontar) {
        logger.info("Inicio crearSaldoTarjetaDTO(CuentaAdministradorSubsidio cuentaAdmin)");
        SaldoTarjetaDTO saldoTarjetaDTO = new SaldoTarjetaDTO();
        saldoTarjetaDTO.setTipoIdentificacion("CEDULA_CIUDADANIA");
        saldoTarjetaDTO.setNumeroIdentificacion("65630739");
        saldoTarjetaDTO.setNumeroTarjeta(cuentaAdmin.getNumeroTarjetaAdmonSubsidio());
        saldoTarjetaDTO.setSaldo(String.format("%.2f", totalDescontar));
        logger.info("fin crearSaldoTarjetaDTO(CuentaAdministradorSubsidio cuentaAdmin)");
        return saldoTarjetaDTO;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private SaldoTarjetaDTO crearSaldoTarjetaDTO(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar) {
        logger.info("Inicio crearSaldoTarjetaDTO(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar)");
        SaldoTarjetaDTO saldoTarjetaDTO = new SaldoTarjetaDTO();
        logger.error(medioTraslado.getTipoIdentificacionTitular());
        saldoTarjetaDTO.setTipoIdentificacion(
                com.asopagos.clienteanibol.enums.TipoIdentificacionEnum
                        .valueOf(medioTraslado.getTipoIdentificacionTitular().name())
                        .getTipoIdentificacion()
        );
        logger.warn("Medio traslado: " +medioTraslado.toString());
        saldoTarjetaDTO.setNumeroIdentificacion(medioTraslado.getNumeroIdentificacionTitular());
        saldoTarjetaDTO.setNumeroTarjeta(medioTraslado.getNumeroTarjeta());
        saldoTarjetaDTO.setSaldo(String.format("%.2f", totalAbonar));
        logger.info("fin crearSaldoTarjetaDTO(MedioDePagoModeloDTO medioTraslado, BigDecimal totalAbonar)");
        return saldoTarjetaDTO;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void procesarMedioPagoOtro(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado, Long idAdmin, UserDTO user, List<Long> idsGrupoFamiliar) {
        logger.info("Inicio procesarMedioPagoOtro(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado, Long idAdmin, UserDTO user, List<Long> idsGrupoFamiliar)");
        List<Long> idsCuentasAnuladas = new ArrayList<>();
        List<Long> idsAdminAnibolDescuento = new ArrayList<>();
        List<Long> idsDetalleAnibolDescuento = new ArrayList<>();
        Set<Long> idMediosDePagoOrigen = new HashSet<>();

        BigDecimal totalDescuento = BigDecimal.ZERO;
        CuentaAdministradorSubsidio cuentaParaDescuento = null;

        //bloquearAbonosATrasladar(cuentasAdmin,user);
        for (CuentaAdministradorSubsidio cuentaAdmin : cuentasAdmin) {
            if (cuentaAdmin.getMedioDePagoTransaccion() == TipoMedioDePagoEnum.TARJETA) {
                idMediosDePagoOrigen.add(cuentaAdmin.getIdMedioDePago());
                if(cuentaParaDescuento == null){
                    cuentaParaDescuento = cuentaAdmin;
                }
                idsAdminAnibolDescuento.add(cuentaAdmin.getIdCuentaAdministradorSubsidio());
                List<DetalleSubsidioAsignado> detallesCuenta = consultasCore.consultarDetallesCuentaTraslado(cuentaAdmin.getIdCuentaAdministradorSubsidio(),idsGrupoFamiliar);
                for(DetalleSubsidioAsignado detalle : detallesCuenta){
                    idsDetalleAnibolDescuento.add(detalle.getIdDetalleSubsidioAsignado());
                    totalDescuento = totalDescuento.add(detalle.getValorTotal());
                }
            } else {
                idMediosDePagoOrigen.add(cuentaAdmin.getIdMedioDePago());
                idsCuentasAnuladas.add(anularCuentaSubsidio(cuentaAdmin, medioTraslado,idsGrupoFamiliar, user));
            }
        }
        if(cuentaParaDescuento != null){
            String idProcesoAnibol = null;
            if(medioTraslado.getTipoMedioDePago().name() == "TARJETA"){
                idProcesoAnibol = ejecutarDescuentoSaldo(medioTraslado,totalDescuento);
            }else{
                idProcesoAnibol = ejecutarDescuentoSaldo(consultasCore.consultarMedioDePagoTraslado(idMediosDePagoOrigen.iterator().next()),totalDescuento);
            }
            if(idProcesoAnibol != null){
                registrarSolicitudAnibol(armarDtoSolicitudAnibol(idsAdminAnibolDescuento, idProcesoAnibol, false,medioTraslado.getIdMedioDePago(),idsDetalleAnibolDescuento));
                if(!idMediosDePagoOrigen.isEmpty() && idMediosDePagoOrigen.size() > 0){
                    List<Long> idsMedioDePagoOrigenList = new ArrayList<>(idMediosDePagoOrigen);
                    for(Long idMedioDePagoOrigen : idsMedioDePagoOrigenList){
                        consultarDatosYRegistroAdmin(idAdmin,EstadoBandejaTransaccionEnum.EN_PROCESO,ProcesoBandejaTransaccionEnum.TRASLADO_SALDOS,idMedioDePagoOrigen,medioTraslado.getIdMedioDePago(),Long.valueOf(idProcesoAnibol));
                    }
                }
                return;
            }else{
                
                for (CuentaAdministradorSubsidio cuentaAdmin : cuentasAdmin){
                    restablecerEstadoCuentasTraslado(cuentaAdmin.getIdCuentaAdministradorSubsidio(),user);
                    idMediosDePagoOrigen.add(cuentaAdmin.getIdMedioDePago());
                }
                if(!idMediosDePagoOrigen.isEmpty() && idMediosDePagoOrigen.size()>0){
                    List<Long> idsMedioDePagoList = new ArrayList<>(idMediosDePagoOrigen);
                    for(Long idMedioDePagoOrigen : idsMedioDePagoList){
                        consultarDatosYRegistroAdmin(idAdmin,EstadoBandejaTransaccionEnum.RECHAZADA,ProcesoBandejaTransaccionEnum.TRASLADO_SALDOS,idMedioDePagoOrigen,medioTraslado.getIdMedioDePago(),null);
                    }
                }
            }
        }
        
        if(idsCuentasAnuladas != null && idsCuentasAnuladas.size()>0){
            if(!idMediosDePagoOrigen.isEmpty() && idMediosDePagoOrigen.size()>0){
                List<Long> idsMedioDePagoOrigenList = new ArrayList<>(idMediosDePagoOrigen);
                for(Long idMedioOrigen : idsMedioDePagoOrigenList){
                    Long idBandeja = consultarDatosYRegistroAdmin(idAdmin,EstadoBandejaTransaccionEnum.EN_PROCESO,ProcesoBandejaTransaccionEnum.TRASLADO_SALDOS,idMedioOrigen,medioTraslado.getIdMedioDePago(),null);
                    try{
                        ejecutarTareasParalelas(idsGrupoFamiliar, medioTraslado, idAdmin, user, idBandeja);
                    }catch(IOException e){
                        logger.error("Ocurrio un error al realizar el proceso de novedades");
                    }
                    Long idSolicitud = consultarUltimaSolicitud(idAdmin);
                    actualizarProcesoBandeja(idBandeja,null,idSolicitud);
                }
            }
        }
        logger.info("Fin procesarMedioPagoOtro(List<CuentaAdministradorSubsidio> cuentasAdmin, MedioDePagoModeloDTO medioTraslado, Long idAdmin, UserDTO user, List<Long> idsGrupoFamiliar)");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long anularCuentaSubsidio(CuentaAdministradorSubsidio cuentaAdmin, MedioDePagoModeloDTO medioTraslado,List<Long> idsGrupoFamiliar, UserDTO user) {
        logger.info("Inicio anularCuentaSubsidio(CuentaAdministradorSubsidio cuentaAdmin, MedioDePagoModeloDTO medioTraslado, UserDTO user)");
        List<DetalleSubsidioAsignado> detallesCuenta = consultasCore.consultarDetallesCuentaTraslado(cuentaAdmin.getIdCuentaAdministradorSubsidio(),idsGrupoFamiliar);
        List<DetalleSubsidioAsignadoDTO> detallesDTOs = new ArrayList<DetalleSubsidioAsignadoDTO>();

        for (DetalleSubsidioAsignado detalleCuenta : detallesCuenta) {
            detallesDTOs.add(new DetalleSubsidioAsignadoDTO(detalleCuenta));
        }

        CuentaAdministradorSubsidioDTO cuentaAdminDTO = new CuentaAdministradorSubsidioDTO(cuentaAdmin);
        cuentaAdminDTO.setEsFlujoTraslado(true);

        SubsidioAnulacionDTO anulacionDTO = new SubsidioAnulacionDTO(cuentaAdminDTO, detallesDTOs);
        anulacionDTO.setMedioDePagoModelo(medioTraslado);

        logger.info("fin anularCuentaSubsidio(CuentaAdministradorSubsidio cuentaAdmin, MedioDePagoModeloDTO medioTraslado, UserDTO user)");
        return anularSubsidioMonetarioConReemplazo(anulacionDTO, user, true);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private Long anularCuentaSubsidio(Long idCuenta, Long idMedioDePagoDestino,List<Long> idsDetalle, UserDTO user) {
        logger.info("Inicio anularCuentaSubsidio(CuentaAdministradorSubsidio cuentaAdmin, MedioDePagoModeloDTO medioTraslado, UserDTO user)");
        CuentaAdministradorSubsidio cuentaAdmin = consultasCore.consultarCuentasAdministradorTraslado(idCuenta);
        List<DetalleSubsidioAsignado> detallesCuenta = consultasCore.consultarDetallesTraslado(idCuenta,idsDetalle);
        List<DetalleSubsidioAsignadoDTO> detallesDTOs = new ArrayList<DetalleSubsidioAsignadoDTO>();
        MedioDePagoModeloDTO medioTraslado = consultasCore.consultarMedioDePagoTraslado(idMedioDePagoDestino);

        for (DetalleSubsidioAsignado detalleCuenta : detallesCuenta) {
            detallesDTOs.add(new DetalleSubsidioAsignadoDTO(detalleCuenta));
        }

        CuentaAdministradorSubsidioDTO cuentaAdminDTO = new CuentaAdministradorSubsidioDTO(cuentaAdmin);
        cuentaAdminDTO.setEsFlujoTraslado(true);

        SubsidioAnulacionDTO anulacionDTO = new SubsidioAnulacionDTO(cuentaAdminDTO, detallesDTOs);
        anulacionDTO.setMedioDePagoModelo(medioTraslado);

        logger.info("fin anularCuentaSubsidio(CuentaAdministradorSubsidio cuentaAdmin, MedioDePagoModeloDTO medioTraslado, UserDTO user)");
        return anularSubsidioMonetarioConReemplazo(anulacionDTO, user, true);
    }

    private void ejecutarTareasParalelas(List<Long> idsGrupoFamiliar, MedioDePagoModeloDTO medioTraslado, Long idAdmin, UserDTO user, Long idBandeja)throws IOException {
        logger.info("Inicio ejecutarTareasParalelas(List<Long> idsGrupoFamiliar, MedioDePagoModeloDTO medioTraslado, Long idAdmin, UserDTO user)");
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        PersonaModeloDTO personaAdmin = consultasCore.consultarPersonaAdmin(idAdmin);
        NovedadTrasladoDTO dataNovedad = new NovedadTrasladoDTO(medioTraslado, personaAdmin);

        List<Callable<Void>> tareasParalelas = new ArrayList<Callable<Void>>();
        for (final Long idGrupo : idsGrupoFamiliar) {
            tareasParalelas.add(new Callable<Void>() {
                @Override
                public Void call() {
                    try {
                        registrarNovedadCambioMedioPagoAdminSubsidioTraslado(dataNovedad, idGrupo, (short) 1, user);
                    } catch (Exception e) {
                        logger.error("Error procesando traslado de medio de pago", e);
                    }
                    return null;
                }
            });
        }
        try {
            executorService.invokeAll(tareasParalelas);
        } catch (InterruptedException e) {
            logger.error("Error en la ejecución de tareas paralelas", e);
            if(idBandeja != null){
                actualizarProcesoBandeja(idBandeja,EstadoBandejaTransaccionEnum.RECHAZADA,null);
            }
            throw new IOException();
        } finally {
            executorService.shutdown();
        }
    }

    private void registrarNovedadCambioMedioPagoAdminSubsidioTraslado(NovedadTrasladoDTO dataNovedad, Long idGrupoFamiliar, Short relacionGrupo, UserDTO user) {
        String firma = "registrarNovedadCambioMedioPagoAdminSubsidioTraslado(ResultadoReexpedicionBloqueoInDTO, Long, Short)";
        logger.info("Inicio método " + firma);

        PersonaModeloDTO persona = dataNovedad.getPersonaAdmin();
        MedioDePagoModeloDTO medioDePagoTraslado = dataNovedad.getMedioTraslado();

        medioDePagoTraslado.setAdmonSubsidio(persona);
        medioDePagoTraslado.setIdGrupoFamiliar(idGrupoFamiliar);
        medioDePagoTraslado.setIdRelacionGrupoFamiliar(relacionGrupo);
        medioDePagoTraslado.setAfiliadoEsAdministradorSubsidio(Boolean.TRUE);

        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setTipoIdentificacion(persona.getTipoIdentificacion());
        datosPersona.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        datosPersona.setIdGrupoFamiliar(idGrupoFamiliar);

        SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
        datosPersona.setMedioDePagoModeloDTO(medioDePagoTraslado);
        solicitudNovedad.setDatosPersona(datosPersona);
        solicitudNovedad.setUsuarioRadicacion(user.getEmail());
        solicitudNovedad.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudNovedad.setTipoTransaccion(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL);

        RadicarSolicitudNovedadAutomaticaSinValidaciones radicar = new RadicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedad);
        radicar.execute();
        logger.info("Finaliza método " + firma);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void registrarSolicitudAnibolTrasladoSaldos(RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO){
        logger.info("Inicio registrarSolicitudAnibolTrasladoSaldos(String idProceso, Boolean abono)");
        Long idRegistroSolicitudAnibol = consultasCore.registrarSolicitudAnibol(reAnibolModeloDTO);
        logger.info("fin registrarSolicitudAnibolTrasladoSaldos(String idProceso, Boolean abono)");    
    }

    private RegistroSolicitudAnibolModeloDTO armarDtoSolicitudAnibol(List<Long> idsCuentasAdmin,String idProceso, Boolean abono,Long idMEdioDePagoDestino,List<Long> idsDetalle){
        Gson gson = new GsonBuilder().create();

        RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
        reAnibolModeloDTO.setFechaHoraRegistro(new Date());
        if(abono){
            reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.TRASLADO_DE_SALDO_ABONO);
        }else{
            reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.TRASLADO_DE_SALDO_DESCUENTO);
        }
        reAnibolModeloDTO.setIdProceso(idProceso);
        reAnibolModeloDTO.setParametrosIN(gson.toJson(idsCuentasAdmin));
        reAnibolModeloDTO.setParametrosTraslado(gson.toJson(idsDetalle));
        reAnibolModeloDTO.setIdMedioDePagoDestino(idMEdioDePagoDestino);
        reAnibolModeloDTO.setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.EN_ESPERA);
        return reAnibolModeloDTO;
    }

    public void bloquearAbonosATrasladar(List<CuentaAdministradorSubsidio> cuentas,UserDTO user){
        consultasCore.bloquearAbonosATrasladar(cuentas,user);
    }

    @Override
    public void retomarTrasladoDeSaldos(UserDTO user) {
        String firmaMetodo = "retomarTrasladoDeSaldos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroSolicitudAnibol> registrosAnibol = consultasCore.consultarRegistrosAnibolTraslado();
        Long idBandeja = null;
        
        for (RegistroSolicitudAnibol registro : registrosAnibol) {
            logger.warn("registro anibol");
            logger.warn(registro.toString());
            StringBuilder respuestaAnibolJSON = new StringBuilder();
            Gson gson = new GsonBuilder().create();

            ResultadoDispersionAnibolDTO resultadoAnibol = new ResultadoDispersionAnibolDTO();

            try {
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                if(conexionAnibol.equals("TRUE")){
                ConsultarEstadoProcesamientoV2 consultarEstadoProcesamiento = new ConsultarEstadoProcesamientoV2(registro.getIdProceso());
                consultarEstadoProcesamiento.execute();
                resultadoAnibol = consultarEstadoProcesamiento.getResult();
                respuestaAnibolJSON.append(gson.toJson(resultadoAnibol));
                String respuestaRegistroAnibol = respuestaAnibolJSON.toString();
                consultasCore.guardarJsonRespuestaAnibol(respuestaRegistroAnibol, Long.valueOf(registro.getIdProceso()));
                }

            } catch (TechnicalException e) {
                logger.warn("No se pudo realizar la conexión con Anibol", e);
                return;
            }

            List<Long> cuentasTarjetaInactivaError = new ArrayList<>();

            for (ResultadoProcesamientoDTO resultadoProcesamiento : resultadoAnibol.getListadoResultadosProcesamiento()) {
                logger.warn("resultado procesamiento");
                logger.warn(resultadoProcesamiento.toString());

                Type listType = new TypeToken<ArrayList<Long>>() {
                }.getType();
                List<Long> listaIdCuentas = gson.fromJson(registro.getParametrosIN(), listType);
                List<Long> listaIdDetalles = gson.fromJson(registro.getParametrosTraslado(), listType);

                if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoProcesamiento.getEstado()) || ResultadoProcesoAnibolEnum.RTA_ANIBOL_VALIDACION.name().equals(resultadoProcesamiento.getEstado())) {

                    Long idMedioDePago = registro.getIdMedioDePagoDestino();

                    // Eliminar duplicados en listaIdCuentas
                    Set<Long> uniqueIdCuentasSet = new HashSet<Long>(listaIdCuentas);
                    List<Long> uniqueIdCuentasList = new ArrayList<Long>(uniqueIdCuentasSet);

                    // Eliminar duplicados en listaIdDetalles
                    Set<Long> uniqueIdDetallesSet = new HashSet<Long>(listaIdDetalles);
                    List<Long> uniqueIdDetallesList = new ArrayList<Long>(uniqueIdDetallesSet);

                    List<Long> idCuentasAnuladas = new ArrayList<>();
                    if (resultadoProcesamiento.isExitoso()) {

                        MedioDePagoModeloDTO medioTraslado = consultasCore.consultarMedioDePagoTraslado(idMedioDePago);
                        Set<Long> gruposFamiliares = new HashSet<Long>();
                        Long idAdmin = null;
                        for(Long idCuenta :uniqueIdCuentasList){
                            if(idAdmin == null){
                                idAdmin = consultasCore.consultarCuentasAdministradorTraslado(idCuenta).getIdAdministradorSubsidio();
                            }
                            List<DetalleSubsidioAsignado> detalles = consultasCore.consultarDetallesTraslado(idCuenta,uniqueIdDetallesList);
                            
                            gruposFamiliares = detalles.stream().map(DetalleSubsidioAsignado :: getIdGrupoFamiliar ).collect(Collectors.toSet());

                            idCuentasAnuladas.add(anularCuentaSubsidio(idCuenta,idMedioDePago,detalles.stream().map(DetalleSubsidioAsignado :: getIdDetalleSubsidioAsignado ).collect(Collectors.toList()),user));
                        }
                        if(!idCuentasAnuladas.isEmpty() && idCuentasAnuladas.size() > 0){

                            idBandeja = consultarBandejaTransacciones(Long.valueOf(registro.getIdProceso()));
                            try{
                                ejecutarTareasParalelas( new ArrayList<Long>(gruposFamiliares), medioTraslado, idAdmin, user, null);
                                if(idBandeja != null){
                                    logger.warn("id bandeja actualizar>>>>>>>>>>>>>" +idBandeja);
                                    actualizarProcesoBandeja(idBandeja,EstadoBandejaTransaccionEnum.FINALIZADA,consultarUltimaSolicitud(idAdmin));
                                }
                                actualizarEstadoSolicitudAnibol(registro.getIdRegistroSolicitudAnibol(),EstadoSolicitudAnibolEnum.PROCESADA);
                            }catch(IOException e){
                                logger.error("Ocurrio un error al finalizar el traslado de saldos");
                                actualizarProcesoBandeja(idBandeja,EstadoBandejaTransaccionEnum.PROCESADO_CON_ERRORES,consultarUltimaSolicitud(idAdmin));
                                actualizarEstadoSolicitudAnibol(registro.getIdRegistroSolicitudAnibol(),EstadoSolicitudAnibolEnum.EN_ESPERA);
                                return;
                            }
                        }
                    } else {
                        cuentasTarjetaInactivaError.addAll(uniqueIdCuentasList);
                    }
                }
                if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoProcesamiento.getEstado())) {
                        if (!resultadoProcesamiento.isExitoso()) {
                            cuentasTarjetaInactivaError.addAll(listaIdCuentas);
                        }
                }
            }

            if(cuentasTarjetaInactivaError != null && cuentasTarjetaInactivaError.size() > 0){
                for (Long cuenta : cuentasTarjetaInactivaError) {
                    restablecerEstadoCuentasTraslado(cuenta,user);
                }
                idBandeja = consultarBandejaTransacciones(Long.valueOf(registro.getIdProceso()));
                if(idBandeja != null){
                    logger.warn("id bandeja actualizar 2 >>>>>>>>>>>>>" +idBandeja);
                    actualizarProcesoBandeja(idBandeja,EstadoBandejaTransaccionEnum.RECHAZADA,null);
                }
                actualizarEstadoSolicitudAnibol(registro.getIdRegistroSolicitudAnibol(),EstadoSolicitudAnibolEnum.PROCESADA);
            }
            //actualizarEstadoSolicitudAnibol(registro.getIdRegistroSolicitudAnibol(),EstadoSolicitudAnibolEnum.PROCESADA);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    public void restablecerEstadoCuentasTraslado(Long idCuenta, UserDTO user){
        consultasCore.restablecerEstadoCuenta(idCuenta,user);
    }

    @Override
    public void crearMedioDePagoParaTraslado(Long idSitioPago,Long idAdmin,MediosPagoYGrupoTrasladoDTO mediosPagoYGrupoTrasladoDTO, UserDTO user){
        Long idMedioDePagoDestino = consultasCore.crearMedioDePagoParaTraslado(idSitioPago);
        procesarTrasladoMedioDePago(idAdmin,idMedioDePagoDestino,mediosPagoYGrupoTrasladoDTO,user);
    }

    @Override
    public Long registrarProcesoBandeja(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso,Long idMedioDePagoOrigen, Long idMedioDePagoDestino){
        BandejaDeTransacciones bandeja = new BandejaDeTransacciones(tipoIdentificacion,numeroIdentificacion,estado,proceso,idMedioDePagoDestino,idMedioDePagoOrigen);
        return consultasCore.registrarProcesoBandeja(bandeja);
    }

    public Long registrarProcesoBandeja(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso,Long idMedioDePagoOrigen, Long idMedioDePagoDestino,Long idProcesoAnibol){
        BandejaDeTransacciones bandeja = new BandejaDeTransacciones(tipoIdentificacion,numeroIdentificacion,estado,proceso,idMedioDePagoDestino,idMedioDePagoOrigen);
        if(idProcesoAnibol != null){
            bandeja.setIdProcesoAnibol(idProcesoAnibol);
        }
        return consultasCore.registrarProcesoBandeja(bandeja);
    }

    @Override
    public void actualizarProcesoBandeja(Long idBandeja,EstadoBandejaTransaccionEnum estado, Long idSolicitud){
        consultasCore.actualizarProcesoBandeja(idBandeja, estado, idSolicitud);
    }

    public Long consultarDatosYRegistroAdmin(Long idAdmin,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso,Long idMedioDePagoOrigen,Long idMedioDePagoDestino,Long idProcesoAnibol){
        logger.warn("consultarDatosYRegistroAdmin(Long idAdmin,EstadoBandejaTransaccionEnum estado,ProcesoBandejaTransaccionEnum proceso)"+idAdmin);
        Object[] datosAdmin = consultasCore.consultarDatosAdminRegistro(idAdmin);
        return registrarProcesoBandeja(TipoIdentificacionEnum.valueOf(String.valueOf(datosAdmin[0])),String.valueOf(datosAdmin[1]),estado,proceso,idMedioDePagoOrigen,idMedioDePagoDestino,idProcesoAnibol);
    }

    public Object[] consultarDatosYRegistroAdmin(Long idAdmin){
        return (Object[]) consultasCore.consultarDatosAdminRegistro(idAdmin);
    }

    public List<Long> consultarBandejaTransacciones(List<Long> idsCuenta,EstadoBandejaTransaccionEnum estado,
        ProcesoBandejaTransaccionEnum proceso){
            return consultasCore.consultarBandejaTransacciones(idsCuenta,estado,proceso);
    }

    public Long consultarBandejaTransacciones(Long idProceso){
        return consultasCore.consultarBandejaTransacciones(idProceso);
    }

    public Long consultarUltimaSolicitud(Long idAdmin){
        return consultasCore.consultarUltimaSolicitud(idAdmin);
    }

    @Override
    public Long consultarUltimaSolicitud(TipoIdentificacionEnum tipoIdentificacionAdmin, String numeroIdentificacionAdmin){
        return consultasCore.consultarUltimaSolicitud(tipoIdentificacionAdmin,numeroIdentificacionAdmin);
    }

    @Override
    public List<BandejaDeTransacciones> consultarBandejaTransaccionesPorPersona(String proceso, PersonaModeloDTO persona){
        logger.warn("inicia List<BandejaDeTransacciones> consultarBandejaTransaccionesPorPersona(ProcesoBandejaTransaccionEnum proceso, PersonaModeloDTO persona)");
        return consultasCore.consultarBandejaTransaccionesPorPersona(proceso,persona);
    }

    @Override
    public List<PersonaModeloDTO> consultarPersonasBandejaTransacciones(String proceso,PersonaModeloDTO persona){
        return consultasCore.consultarPersonasBandejaTransacciones(proceso,persona);
    }

    @Override
    public DetalleBandejaTransaccionesDTO consultarDetalleBandejaTransacciones(@QueryParam("idBandeja")Long idBandeja){
        return consultasCore.consultarDetalleBandejaTransacciones(idBandeja);
    }

    @Override
    public Long consultarIdMedioDePagoTarjeta(String tipoIdentificacion, String numeroIdentificacion){
        return consultasCore.consultarIdMedioDePagoTarjeta(tipoIdentificacion,numeroIdentificacion);
    }

    @Override
    public List<GestionDeTransaccionesDTO> gestionBandejaTransacciones(){
        return consultasCore.consultarGestionDeTransacciones();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<TipoMedioDePagoEnum> consultarMediosDePagosTrasladoAdminSubsidio(Long idAdminSubsidio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarMediosDePagosRelacionadosAdminSubsidio(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<TipoMedioDePagoEnum> medioDePago = consultasCore.consultarMediosDeTrasladoAdminSubsidio(idAdminSubsidio);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return medioDePago;
    }
    
    @Override
    public Response consultarRetirosIntermedios(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
            String usuario, String idTransaccionTercerPagador,String user, String password, String idPuntoCobro,
            UserDTO userDTO){
        logger.info("inicia consultarRetirosIntermedios");
        Response response = validacionesReversoIntermedio(tipoIdAdmin,numeroIdAdmin,String.valueOf(valorSolicitado),usuario,idTransaccionTercerPagador,idPuntoCobro,user,password);

        if(response.getStatus()==200){
            List<List<CuentaAdministradorSubsidioDTO>> listadoRetiros = new ArrayList<>();

            listadoRetiros.add(consultasCore.listadoValoresRetirosIntermedios(tipoIdAdmin,numeroIdAdmin,usuario,idTransaccionTercerPagador,idPuntoCobro));
            listadoRetiros.add(consultasCore.listadoAbonosIntermedios(idTransaccionTercerPagador, usuario, idPuntoCobro));
            if(listadoRetiros.get(0).size() == 0){
                Map<String, String> salida = new HashMap<>();
                StringBuilder salidaGson = new StringBuilder();
                Gson gson = new GsonBuilder().create();
                String parametrosOUT = null;

                salida.put("error", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getDescripcion().toString());
                salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getCodigoError().toString());
                salidaGson.append(gson.toJson(salida));
                parametrosOUT = salidaGson.toString();
                return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
                .build();
            }
            return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON).entity(listadoRetiros)
            .build();
        }
        return response;
    }

    private  Response validacionesReversoIntermedio(String tipoIdentificacion, String numeroIdentificacion, String valorTransaccionStr,
            String usuarioTerceroPagador, String idTransaccion, String idPuntoCobro, String usuario,
            String contrasena) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validacionesReverso(String ,String, BigDecimal, String, String, String, String, String, String, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Map<String, String> salida = new HashMap<>();
        String mensaje = null;
        Date fechaHoraInicio = new Date();
        String url = "metodo-validacionesReverso";

        StringBuilder salidaGson = new StringBuilder();
        Gson gson = new GsonBuilder().create();

        Map<String, String> respuesta = new HashMap<>();
        Map<String, Object> parametrosEntrada = new HashMap<>();

        //se almacenan los parametros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmon", tipoIdentificacion);
        parametrosEntrada.put("numeroIdentificadorAdmon", numeroIdentificacion);
        parametrosEntrada.put("valorTransaccion", valorTransaccionStr);
        parametrosEntrada.put("idTransaccion", idTransaccion);
        parametrosEntrada.put("idPuntoCobro", idPuntoCobro);
        parametrosEntrada.put("usuarioTerceroPagador", usuarioTerceroPagador);
        parametrosEntrada.put("usuario", usuario);
        salidaGson.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salidaGson.toString();
        logger.info("parametrosIN-> " +parametrosIN);
        Long identificadorRespuesta = 1L;
        String parametrosOUT = null;
        salidaGson = new StringBuilder();

        //Obligatoriedad de los campos
        logger.info("inicia validacion obligatoriedad campos");
        if ((tipoIdentificacion == null || tipoIdentificacion.isEmpty()) || (numeroIdentificacion == null || numeroIdentificacion.isEmpty() ) ||
        (valorTransaccionStr == null || valorTransaccionStr.isEmpty()) || (usuarioTerceroPagador == null || usuarioTerceroPagador.isEmpty() ) ||
        (idTransaccion == null || idTransaccion.isEmpty()) || (idPuntoCobro == null || idPuntoCobro.isEmpty())||
        (usuario == null || usuario.isEmpty()) || (contrasena == null || contrasena.isEmpty())) {
        logger.debug("Entrando a Error de Validacion");
        salida.put("error", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoError());
        logger.info("validacion no exitosa--> " +salida);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        BigDecimal valorTransaccion = new BigDecimal(valorTransaccionStr);

        //Validación de usuario y contrasena para autenticidad
        logger.info("inicia validacion de usuario y contraseña");
        try {
        Boolean usuarioValidado = validarCredencialesUsuarios(usuario, contrasena);
        if (usuarioValidado == null || Boolean.FALSE.equals(usuarioValidado)) {
        salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoError());
        logger.info("validacion no exitosa--> " + salida);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }
        }catch (Exception e){
        salida.put("error", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoError());
        logger.info("validacion no exitosa--> " + salida);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        //Validar Tipo y número de identificación
        TipoIdentificacionEnum tipoIdentificacionEnum = null;
        //Se valida el tipo de identificacion
        logger.info("inicia validacion tipo de identificacion");
        try {
        logger.info("Validaciones tipo de identificacion");
        tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        } catch (Exception e) {
        logger.info("Entrando a Error de Validacion tipoIdentificacionEnum");
        salida.put("error", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoError());
        logger.info("validacion no exitosa--> " +salida);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        //Se valida el numero de identificacion
        logger.info("Validaciones numero de identificacion");
        ErroresServiciosEnum validacionesIdentificacion = ValidatorUtil.validarNumeroDocumento(numeroIdentificacion, tipoIdentificacionEnum);
        if(validacionesIdentificacion != null){
        salida.put("error", validacionesIdentificacion.getDescripcion());
        salida.put("codigoError", validacionesIdentificacion.getCodigoError());
        logger.info("validacion no exitosa--> " +salida);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, null);
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        // Se valida el administrador de subsidio
        AdministradorSubsidioModeloDTO admin = new AdministradorSubsidioModeloDTO();
        try {
        logger.info("Validaciones administrador de subsidio");
        //se registra la operación
        admin = validarAdministrador(tipoIdentificacionEnum, numeroIdentificacion);
        logger.info("admin " + admin.getIdAdministradorSubsidio());
        }
        catch(Exception e){
        logger.info("Entrando a Error de Validacion del administrador de subsidio");
        salida.put("error", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_ADMINISTRADOR_SUBSIDIO.getCodigoError());
        //Se registra la opercion
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,parametrosIN, usuario, admin.getIdAdministradorSubsidio());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        //Se valida el idTransaccionTercerPagador
        try {
        logger.info("Validaciones idTransaccionTercerPagador");
        ValidatorUtil.validarTerceroPagador(idTransaccion);
        } catch (Exception e) {
        logger.info("Entrando a Error de Validacion idTransaccionTercerPagador");
        salida.put("error", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoError());
        //Se registra la opercion
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        logger.info("validacion no exitosa--> " + salida);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        //ValidarUsuarioPagador
        Object usuarioPagador = consultasCore.consultarUsuarioTerceroPagador(usuarioTerceroPagador);
        if (usuarioPagador == null || usuarioPagador.equals("")) {
        logger.info("Entrando a: usuario convenio no existe");
        salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO.getCodigoError());
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        } else {
        if (usuarioPagador.equals(EstadoConvenioEnum.INACTIVO.toString())) {
        logger.info("Entrando a: usuario convenio esta inactivo");
        salida.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONVENIO_INACTIVO.getCodigoError());
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO, parametrosIN, usuario, admin.getIdAdministradorSubsidio());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }
        }

        List<CuentaAdministradorSubsidioDTO> listadoRetiros = consultasCore.listadoValoresRetiros(idTransaccion, usuarioTerceroPagador, idPuntoCobro);
        if (listadoRetiros != null && !listadoRetiros.isEmpty()) {
        Long adminRetiro = listadoRetiros.get(0).getIdAdministradorSubsidio();
        logger.info("adminRetiro" + adminRetiro);
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,
        parametrosIN, usuario, adminRetiro);

        //Se valida que el administrador de susbsidio del retiro sea el mismo que haga el reverso
        if (!admin.getIdAdministradorSubsidio().equals(adminRetiro)) {
        logger.info("Entrando a: Validacion administrador de susbsidio del retiro sea el mismo que haga el reverso");
        salida.put("error", ErroresServiciosEnum.ADMINISTRADOR_NO_COINCIDE.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ADMINISTRADOR_NO_COINCIDE.getCodigoError());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        //Validar Valor transacción
        if (listadoRetiros.size() > 1) {
        logger.info("La lista trae mas de un registro");
        salida.put("error", ErroresServiciosEnum.ERROR_TRAE_MAS_DE_UN_VALOR.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_TRAE_MAS_DE_UN_VALOR.getCodigoError());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        } else {
        logger.info("La lista no está vacía" + listadoRetiros.size());
        BigDecimal valorTransaccionReal = listadoRetiros.get(0).getValorRealTransaccion();
        logger.info("valorTransaccionReal " + valorTransaccionReal);
        BigDecimal valorAbsolutoTransaccionReal = new BigDecimal(String.valueOf(valorTransaccionReal.abs()));
        logger.info("valorAbsolutoTransaccionReal " + valorAbsolutoTransaccionReal);
        Integer diferencia = valorTransaccion.compareTo(valorAbsolutoTransaccionReal);
        switch (diferencia) {
        case 1:
        logger.info("El valor transaccion es mayor al valor real transaccion");
        salida.put("error", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MAYOR_VALOR_REAL.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MAYOR_VALOR_REAL.getCodigoError());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        case -1:
        logger.info("El valor transaccion es menor al valor real transaccion");
        salida.put("error", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MENOR_VALOR_REAL.getDescripcion());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_VALOR_TRANSACCION_MENOR_VALOR_REAL.getCodigoError());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        fechaHoraFinal = new Date();
        diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        }
        }else{
        logger.info("La lista esta vacia");
        //Se registra la opercion
        identificadorRespuesta = consultasCore.registrarOperacionesSubsidio(TipoOperacionSubsidioEnum.REVERSO_RETIRO,
        parametrosIN, usuario, null);
        salida.put("error", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getDescripcion().toString());
        salida.put("codigoError", ErroresServiciosEnum.ERROR_NO_SE_ENCONTRO_TRANSACCION.getCodigoError().toString());
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));
        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        Date fechaHoraFinal = new Date();
        long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
        consultasCore.actualizarRegistroOperacionSubsidio(identificadorRespuesta, parametrosOUT, String.valueOf(diferenciaMilisegundos),url);
        return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON).entity(parametrosOUT)
        .build();
        }

        salida.put("codigo", "OK");
        salida.put("mensaje", "La transaccion fue exitosa");
        salida.put("resultado", String.valueOf(true));
        salida.put("identificadorRespuesta", String.valueOf(identificadorRespuesta));

        salidaGson.append(gson.toJson(salida));
        parametrosOUT = salidaGson.toString();
        return Response.ok(parametrosOUT,MediaType.APPLICATION_JSON).build();
    }


    @Override
    public Response solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV4(
            String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
            String usuario, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, String password, String idPuntoCobro,
            UserDTO userDTO) {

        logger.info("INGRESAAAA " + numeroIdAdmin + "PAGOS SERVICIO NUEVO");
        Date fechaHoraInicio = new Date();
        Date fechaHoraFinal = null;
        String url = "servicio-solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV4";
        StringBuilder salida = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        Map<String, Object> parametrosEntrada = new HashMap<>();
        String httpStatus = null;

        // Se almacenan los parámetros de entrada en un Map
        parametrosEntrada.put("tipoIdentificadorAdmin", tipoIdAdmin);
        parametrosEntrada.put("numeroIdentificadorAdmin", numeroIdAdmin);
        parametrosEntrada.put("valorSolicitado", valorSolicitado);
        parametrosEntrada.put("idTransaccionTercerPagador", idTransaccionTercerPagador);
        parametrosEntrada.put("departamento", departamento);
        parametrosEntrada.put("municipio", municipio);
        parametrosEntrada.put("usuario", usuario);
        parametrosEntrada.put("usuarioTransaccion", user);
        salida.append(gson.toJson(parametrosEntrada));
        String parametrosIN = salida.toString();

        Map<String, String> respuesta = new HashMap<>();
        Integer tiempo = 25;
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Map<String, String>> futureTransaccion = null;
        try {

            Callable<Map<String, String>> transaccion = () -> {
                return validacionesSolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV4(tipoIdAdmin, numeroIdAdmin, valorSolicitado,
                        usuario, idTransaccionTercerPagador, departamento, municipio, user, password, idPuntoCobro);
            };

            futureTransaccion = executor.submit(transaccion);
            respuesta = futureTransaccion.get(tiempo, TimeUnit.SECONDS);
            logger.info("Retorna la respuesta" + respuesta);
            fechaHoraFinal = new Date();
            long diferenciaMilisegundos = (fechaHoraFinal.getTime() - fechaHoraInicio.getTime());
            logger.info(respuesta.toString());
            httpStatus = respuesta.remove("CODIGO_ERROR");
            logger.info("httpStatus -> " + httpStatus);
            return Response.status(Response.Status.valueOf(httpStatus))
                    .type(MediaType.APPLICATION_JSON).entity(respuesta)
                    .build();

        } catch (RuntimeException | TimeoutException e) {
            salida = new StringBuilder();
            fechaHoraFinal = new Date();
            logger.error("El sistema genero un error por tiempo", e);
            futureTransaccion.cancel(true);// Cancela la tarea y permite la interrupción
            respuesta.put("error", ErroresServiciosEnum.ERROR_DE_TIEMPO.getDescripcion());
            respuesta.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_DE_TIEMPO.getCodigoHttp());
            respuesta.put("codigoError", ErroresServiciosEnum.ERROR_DE_TIEMPO.getCodigoError());
            salida.append(gson.toJson(respuesta));
            return Response.status(Response.Status.valueOf(ErroresServiciosEnum.ERROR_DE_TIEMPO.getCodigoHttp()))
                    .type(MediaType.APPLICATION_JSON).entity(respuesta)
                    .build();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Sucedio un error en el proceso", e);
            salida = new StringBuilder();
            futureTransaccion.cancel(true);
            respuesta.put("error", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getDescripcion());
            respuesta.put("codigoError", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoError());
            respuesta.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoHttp());
            return Response.status(Response.Status.valueOf(ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoHttp()))
                    .type(MediaType.APPLICATION_JSON).entity(respuesta)
                    .build();
        } finally {
            executor.shutdownNow();
        }
    }

    public Map<String, String> validacionesSolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV4(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado, String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.validacionesSolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV4(String,String,BigDecimal,String,String,String,String,String,String,String,UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Date fechaHoraFinal = null;
        Map<String, String> salidaValidacion = null;
        TipoIdentificacionEnum tipoIdentificacionEnum = null;

        logger.info("validaciones v4");

        try {
            salidaValidacion = new HashMap<>();

            // ########## se valida la obligatoriedad de los campos ##########
            logger.info("Validaciones obligatoriedad de campos");
            if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || StringUtils.isEmpty(usuario) || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(departamento) || StringUtils.isEmpty(municipio) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password) || StringUtils.isEmpty(idPuntoCobro)) {

                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_VALIDACION_CAMPOS.getCodigoHttp());

                return salidaValidacion;
            }

            // ########## validacion de expresiones regulares ##########
            logger.info("Validaciones expresiones regulares");

            try {
                logger.info("Validaciones --- validarTerceroPagador");
                ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
            } catch (ErrorExcepcion e) {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_TERCERO_PAGADOR.getCodigoHttp());

                return salidaValidacion;
            }

            try {
                logger.info("Validaciones --- validarIdentificadorPuntoCobro");
                ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
            } catch (ErrorExcepcion e) {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_PUNTO_COBRO.getCodigoHttp());

                return salidaValidacion;
            }

            String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
            String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);

            logger.info("validacion --- validacionesValorSolicitado");
            ErroresServiciosEnum validacionesValorSolicitado = ValidatorUtil.validaValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));

            if (validacionesValorSolicitado != null) {
                logger.debug("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", validacionesValorSolicitado.getDescripcion());
                salidaValidacion.put("codigoError", validacionesValorSolicitado.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", validacionesValorSolicitado.getCodigoHttp());

                return salidaValidacion;
            }

            // ########## se validan las credenciales del usuario ##########
            logger.info("Validaciones credenciales de usuario");
            Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

            Map<String, String> respuesta = null;

            if (usuarioValidado != null && usuarioValidado) {
                try {
                    // ########## Se valida el tipo de identificacion ##########
                    logger.info("Validaciones tipo de identificacion");
                    tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdAdmin);
                } catch (Exception e) {
                    logger.info("Entrando a Error de Validacion tipoIdentificacionEnum");

                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getDescripcion());
                    salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_TIPO_IDENTIFICACION.getCodigoHttp());

                    return salidaValidacion;
                }

                // ########## Se valida el numero de identificacion ##########
                logger.info("Validaciones numero de identificacion");
                ErroresServiciosEnum validacionesIdentificacion = ValidatorUtil.validarNumeroDocumento(numeroIdAdmin, tipoIdentificacionEnum);
                if (validacionesIdentificacion != null) {
                    logger.info("Entrando a Error de Validacion numeroIdentificacion");

                    salidaValidacion.put("resultado", String.valueOf(false));
                    salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                    salidaValidacion.put("error", validacionesIdentificacion.getDescripcion());
                    salidaValidacion.put("codigoError", validacionesIdentificacion.getCodigoError());
                    salidaValidacion.put("CODIGO_ERROR", validacionesIdentificacion.getCodigoHttp());

                    return salidaValidacion;
                }

            } else {
                logger.info("Entrando a Error de Validacion");
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("error", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_USUARIO_CONTRASENNA.getCodigoHttp());

                return salidaValidacion;
            }

        } catch (Exception e) {
            logger.info("Ocurrio un error inesperado");
            salidaValidacion = new HashMap<>();
            salidaValidacion.put("resultado", String.valueOf(false));
            salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
            salidaValidacion.put("error", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getDescripcion());
            salidaValidacion.put("codigoError", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoError());
            salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.ERROR_NO_REGISTRADO.getCodigoHttp());
            return salidaValidacion;
        }

        if (valorSolicitado.compareTo(BigDecimal.ZERO) < 0) {
            salidaValidacion = new HashMap<>();
            salidaValidacion.put("resultado", String.valueOf(false));
            salidaValidacion.put("detalleTransaccion", "Transacción no exitosa, no se pudieron obtener datos del sp");
            salidaValidacion.put("error", ErroresServiciosEnum.VALOR_SOLICITADO_MIN_ZERO.getDescripcion());
            salidaValidacion.put("codigoError", ErroresServiciosEnum.VALOR_SOLICITADO_MIN_ZERO.getCodigoError());
            salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.VALOR_SOLICITADO_MIN_ZERO.getCodigoHttp());
            return salidaValidacion;
        }

        logger.info("ingresa sp validaciones v4");
        Object idResp = consultasCore.registrarRetiroSP(tipoIdAdmin, numeroIdAdmin, valorSolicitado.longValue(), usuario, idTransaccionTercerPagador, departamento, municipio, idPuntoCobro, user);
        logger.info("respuesta " + idResp.toString());
        Object idValor = null;
        Object codigoError = null;
        Object idRegistroOperacionTransaccion = null;
        if (idResp instanceof Object[]) {
            // Si es un array simple (Object[])
            Object[] array1D = (Object[]) idResp;
            System.out.println(Arrays.toString(array1D));
            idRegistroOperacionTransaccion = array1D[0];
            idValor = array1D[1];
            System.out.println("idValor " + idValor);
            if (idValor == null || idValor.equals(0)) {
                codigoError = array1D[2];
                logger.info("segundoValor " + codigoError);
                salidaValidacion = new HashMap<>();
                salidaValidacion.put("resultado", String.valueOf(false));
                salidaValidacion.put("detalleTransaccion", "Transacción no exitosa");
                salidaValidacion.put("identificadorRespuesta", String.valueOf(idRegistroOperacionTransaccion));
                salidaValidacion.put("error", ErroresServiciosEnum.valueOf(codigoError.toString()).getDescripcion());
                salidaValidacion.put("codigoError", ErroresServiciosEnum.valueOf(codigoError.toString()).getCodigoError());
                salidaValidacion.put("CODIGO_ERROR", ErroresServiciosEnum.valueOf(codigoError.toString()).getCodigoHttp());

            } else {
                salidaValidacion.put("identificadorRespuesta", String.valueOf(idRegistroOperacionTransaccion));
                salidaValidacion.put("resultado", String.valueOf(true));
                salidaValidacion.put("detalleTransaccion", "Transacción exitosa");
                salidaValidacion.put("idTransaccionTerceroPagador", idTransaccionTercerPagador);
                salidaValidacion.put("CODIGO_ERROR", "OK");
                salidaValidacion.put("idRetiro", String.valueOf(idValor));
                salidaValidacion.put("url", "solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV3");

            }

        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return salidaValidacion;


    }

    public void cambioEstadoCuentasAdminSubsidio (List<Long> idCuentaList, String usuarioNombre){
        String firmaServicio = "PagosSubsidioMonetarioBusiness.cambioEstadoCuentasAdminSubsidio (List<CuentaAdministradorSubsidioDTO>, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        consultasCore.cambioEstadoCuentasAdminSubsidio(idCuentaList, usuarioNombre);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Long consultarTransaccionesDetallesSubsidiosCount(@Context UriInfo uriInfo,
                                                             @Context HttpServletResponse response,
                                                             DetalleTransaccionAsignadoConsultadoDTO transaccionDetalleSubsidio) {

        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarTransaccionesDetallesSubsidiosCount(DetalleTransaccionAsignadoConsultadoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        return consultasCore.consultarTransaccionesTodosFiltrosSPCount(uriInfo, response,
                transaccionDetalleSubsidio);
    }

    @Override
    public List<CuentaAdministradorSubsidioDTO> consultarCuentasAdminSubsidioPorIds(List<Long> ltsIdsCuentaAdministradorSubsidio) {
        String firmaServicio = "PagosSubsidioMonetarioBusiness.consultarCuentasAdminSubsidioPorIds(List<Long>)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<CuentaAdministradorSubsidioDTO> cuentasAdminSubsidio = consultasCore.consultarCuentasAdminSubsidio(ltsIdsCuentaAdministradorSubsidio);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return cuentasAdminSubsidio;
    }

}
