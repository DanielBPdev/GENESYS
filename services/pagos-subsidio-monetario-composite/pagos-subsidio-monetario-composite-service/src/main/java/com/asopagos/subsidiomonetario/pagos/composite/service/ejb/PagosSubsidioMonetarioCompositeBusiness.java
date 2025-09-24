package com.asopagos.subsidiomonetario.pagos.composite.service.ejb;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.ejb.TimerConfig;
import javax.transaction.UserTransaction;

import javax.ejb.Asynchronous;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.persistence.NoResultException;

import com.asopagos.afiliaciones.clients.ActualizarSolicitud;
import com.asopagos.afiliaciones.clients.BuscarSolicitudPorId;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.clienteanibol.clients.AbonarSaldoTarjetas;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamiento;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamientoAnulacion;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamientoPrescripcion;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamientoV2;
import com.asopagos.clienteanibol.clients.ConsultarTarjetaActiva;
import com.asopagos.clienteanibol.clients.DescontarSaldoTarjetas;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import com.asopagos.clienteanibol.dto.TarjetaDTO;
import com.asopagos.comunicados.clients.GuardarObtenerComunicadoPrescripcion;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.modelo.AdministradorSubsidioModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionMontoLiquidadoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoDescuentosFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemDispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ItemResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoPorAdministradorLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobradoModeloDTO;
import com.asopagos.empresas.clients.ConsultarEmpresa;
import com.asopagos.empresas.clients.CrearEmpresa;
import com.asopagos.entidaddescuento.clients.ObtenerArchivosSalidaDescuentos;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ReultadoValidacionCampoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.SolicitudTarjetaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.*;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.ResultadoCargueArchivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.ResultadoProcesoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.ResultadoValidacionArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoCargueArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoOperacionAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoProcesoAnibolEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.archivos.composite.clients.EnvioExitosoComunicados;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAutomaticaSinValidaciones;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.pagination.PaginationQueryParamsEnum;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.personas.clients.ConsultarAdministradorSubsidioGeneral;
import com.asopagos.personas.clients.GuardarMedioDePago;
import com.asopagos.rest.exception.ErrorExcepcion;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rest.security.filter.AccessToken;
import com.asopagos.subsidiomonetario.clients.ConsultarArchivosLiquidacion;
import com.asopagos.subsidiomonetario.clients.ConsultarCondicionesPersonas;
import com.asopagos.subsidiomonetario.clients.ConsultarDetalleBeneficiarioLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConsultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas;
import com.asopagos.subsidiomonetario.clients.ConsultarIdSolicitud;
import com.asopagos.subsidiomonetario.clients.GestionarArchivosLiquidacion;
import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.pagos.clients.*;
import com.asopagos.subsidiomonetario.pagos.composite.clients.ProcesarResultadoReexpedicionBloqueoAnibol;
import com.asopagos.subsidiomonetario.pagos.composite.constants.PagosSubsidioMonetarioCompositeConstants;
import com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService;
import com.asopagos.personas.clients.RegistrarActualizacionTarjetaGrupoFamiliar;
import com.asopagos.personas.clients.ReemplazarMedioDePagoGrupoFamiliar;
import com.asopagos.personas.clients.RegistrarTarjetaExpedicion;
//import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
//import com.asopagos.subsidiomonetario.pagos.constants.TipoErrorArchivoTerceroPagadorEfectivo;
import com.asopagos.subsidiomonetario.pagos.composite.service.util.ValidatorUtil;
import com.asopagos.subsidiomonetario.pagos.dto.*;
import com.asopagos.subsidiomonetario.pagos.enums.CasoMovimientoSubsidioEnum;
import com.asopagos.subsidiomonetario.pagos.enums.ComparacionSaldoTarjetaEnum;
import com.asopagos.subsidiomonetario.pagos.enums.EstadoRecepcionResultadoEnum;
//import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.GenerarTokenAccesoSystem;
import com.asopagos.usuarios.clients.ValidarCredencialesUsuario;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.util.ContextUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.subsidiomonetario.pagos.clients.BuscarNombreArchivoConsumoTarjetaANIBOL;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.asopagos.subsidiomonetario.pagos.clients.ConsultarCuentasAdminSubsidioPorIds;

/**
 * <b>Descripción: Clase que implementa los servicios de composicion para
 * Subsidio Monetario</b>
 * <b>Historia de Usuario: HU-31-XXX</b>
 *
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez
 * Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co> Miguel Angel Osorio</a>
 */
@Stateless
public class PagosSubsidioMonetarioCompositeBusiness implements PagosSubsidioMonetarioCompositeService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PagosSubsidioMonetarioCompositeBusiness.class);

    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * Identificador en base de datos del archivo de consumo ANIBOL (Distinto al
     * ID del ECM)
     */
    private Long idArchivoAnibol;

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#validarArchivoRetiro(java.lang.String,
     * com.asopagos.rest.security.dto.UserDTO, java.lang.String)
     */
    @Override
    public ResultadoValidacionArchivoRetiroDTO validarArchivoRetiro(String idArchivoRetiro, UserDTO userDTO, String nombreTerceroPagador) {

        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.validarArchivoRetiro(String idArchivoRetiro)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoRetiroDTO resultadoValidacion = new ResultadoValidacionArchivoRetiroDTO();

        //se obtiene la información del archivo de retiro por medio de su id
        InformacionArchivoDTO informacionArchivoDTO = obtenerArchivoECM(idArchivoRetiro);
        logger.info("FileName: " + informacionArchivoDTO.getFileName());
        ResultadoValidacionArchivoDTO resultadoDTO = validarArchivorRetiroService(informacionArchivoDTO, userDTO.getNombreUsuario(),
                nombreTerceroPagador);

        //si el estado es cargado, quiere decir que las validaciones por medio de lion no fueron exitosas y se retorna a pantalla.
        if (EstadoCargaMultipleEnum.CARGADO.equals(resultadoDTO.getEstadoCargue())) {

            resultadoValidacion.setIdArchivoRetiroTerceroPagador(String.valueOf(resultadoDTO.getIdCargue()));
            resultadoValidacion.setNombreArchivoRetiro(informacionArchivoDTO.getFileName());
            resultadoValidacion.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.VALIDACIONES_NO_EXISTOSAS);

            return resultadoValidacion;
        }

        ConsolaEstadoCargueProcesoDTO consolaEstadoArchivo = new ConsolaEstadoCargueProcesoDTO();

        String codigoCaja;
        try {
            codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        } catch (Exception e) {
            codigoCaja = null;
        }

        consolaEstadoArchivo.setCcf(codigoCaja);
        consolaEstadoArchivo.setCargue_id(resultadoDTO.getIdCargue());

        EstadoCargueMasivoEnum estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
        if (EstadoCargaMultipleEnum.CANCELADO.equals(resultadoDTO.getEstadoCargue())) {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
            resultadoValidacion.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.PROCESADO_CON_INCONSISTENCIA);
        } else if (EstadoCargaMultipleEnum.CERRADO.equals(resultadoDTO.getEstadoCargue())) {
            resultadoValidacion.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.PROCESADO);
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
        } else if (EstadoCargaMultipleEnum.EN_PROCESO.equals(resultadoDTO.getEstadoCargue())) {
            resultadoValidacion.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.PREVIAMENTE_PROCESADO);
        }

        consolaEstadoArchivo.setEstado(estadoProcesoMasivo);
        consolaEstadoArchivo.setFileLoaded_id(resultadoDTO.getFileDefinitionId());
        consolaEstadoArchivo.setGradoAvance(resultadoDTO.getEstadoCargue().getGradoAvance());
        consolaEstadoArchivo.setIdentificacionECM(informacionArchivoDTO.getIdentificadorDocumento());
        consolaEstadoArchivo.setNumRegistroConErrores(resultadoDTO.getRegistrosConErrores());
        consolaEstadoArchivo.setNumRegistroObjetivo(resultadoDTO.getTotalRegistro());
        consolaEstadoArchivo.setNumRegistroProcesado(resultadoDTO.getTotalRegistro());
        consolaEstadoArchivo.setNumRegistroValidados(resultadoDTO.getRegistrosValidos());
        consolaEstadoArchivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_ARCHIVO_RETIRO_TERCER_PAGADOR); //Dato para la consola de estados
        consolaEstadoArchivo.setUsuario(userDTO.getNombreUsuario());
        consolaEstadoArchivo.setLstErroresArhivo(resultadoDTO.getResultadoHallazgosValidacionArchivoDTO());

        registrarConsolaEstado(consolaEstadoArchivo);

        resultadoValidacion.setIdArchivoRetiroTerceroPagador(String.valueOf(resultadoDTO.getIdCargue()));
        resultadoValidacion.setNombreArchivoRetiro(informacionArchivoDTO.getFileName());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoValidacion;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#crearConvenioTerceroPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long crearConvenioTerceroPagador(ConvenioTercerPagadorDTO convenioTercerPagadorDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.crearConvenioTerceroPagador(ConvenioTercerPagadorDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EmpresaModeloDTO empresaModeloDTO = new EmpresaModeloDTO();

        empresaModeloDTO.setTipoIdentificacion(convenioTercerPagadorDTO.getTipoIdentificacion());
        empresaModeloDTO.setNumeroIdentificacion(convenioTercerPagadorDTO.getNumeroIdentificacion());
        empresaModeloDTO.setRazonSocial(convenioTercerPagadorDTO.getRazonSocial());
        empresaModeloDTO.setDigitoVerificacion(convenioTercerPagadorDTO.getDigitoVerificacion());
        empresaModeloDTO.setPrimerNombre(convenioTercerPagadorDTO.getPrimerNombre());
        empresaModeloDTO.setSegundoNombre(convenioTercerPagadorDTO.getSegundoNombre());
        empresaModeloDTO.setPrimerApellido(convenioTercerPagadorDTO.getPrimerApellido());
        empresaModeloDTO.setSegundoApellido(convenioTercerPagadorDTO.getSegundoApellido());

        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        ubicacionModeloDTO.setTelefonoCelular(convenioTercerPagadorDTO.getUbicacionModeloDTO().getTelefonoCelular());
        ubicacionModeloDTO.setTelefonoFijo(convenioTercerPagadorDTO.getUbicacionModeloDTO().getTelefonoFijo());
        ubicacionModeloDTO.setIndicativoTelFijo(convenioTercerPagadorDTO.getUbicacionModeloDTO().getIndicativoTelFijo());
        ubicacionModeloDTO.setDireccionFisica(convenioTercerPagadorDTO.getUbicacionModeloDTO().getDireccionFisica());
        ubicacionModeloDTO.setCodigoPostal(convenioTercerPagadorDTO.getUbicacionModeloDTO().getCodigoPostal());
        ubicacionModeloDTO.setEmail(convenioTercerPagadorDTO.getUbicacionModeloDTO().getEmail());
        ubicacionModeloDTO.setAutorizacionEnvioEmail(convenioTercerPagadorDTO.getUbicacionModeloDTO().getAutorizacionEnvioEmail());
        ubicacionModeloDTO.setIdMunicipio(convenioTercerPagadorDTO.getUbicacionModeloDTO().getIdMunicipio());

        empresaModeloDTO.setUbicacionModeloDTO(ubicacionModeloDTO);

        //se crea o se busca la empresa
        Long idEmpresa = crearEmpresaConvenio(empresaModeloDTO);

        //se asocia el id de la empresa al convenioDTO
        convenioTercerPagadorDTO.setIdEmpresa(idEmpresa);

        //se crea el convenio con la relación al documento de soporte
        Long idConvenio = crearSolicitudRegistroConvenio(convenioTercerPagadorDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idConvenio;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#crearDetallesSubsidiosAsignadosPagos(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Long crearDetallesSubsidiosAsignadosPagos(List<DetalleSubsidioAsignadoDTO> detallesSubsidiosAsignados, UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.crearDetallesSubsidiosAsignados(List<DetalleSubsidioAsignadoDTO>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();

        // TODO se debe buscar el tipo de medio de pago que se va a registrar en el (cuentaAdminSubsidio)
        TipoMedioDePagoEnum tipoMedioDePago = null;

        // TODO se debe buscar el sitio de pago al que estará asociado con el abono (cuentaAdminSubsidio)
        Long idSitioDePago = null;

        // TODO en caso de que el medio de pago sea banco se debe buscar la información de banco para registrarla a la cuenta
        if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(tipoMedioDePago)) {

        }

        cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(new Date());
        cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.ABONO);
        cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.GENERADO);
        cuentaAdministradorSubsidioDTO.setMedioDePago(tipoMedioDePago);
        cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.LIQUIDACION_SUBSIDIO_MONETARIO);
        cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(detallesSubsidiosAsignados.get(0).getIdAdministradorSubsidio());
        cuentaAdministradorSubsidioDTO.setIdEmpleador(detallesSubsidiosAsignados.get(0).getIdEmpleador());
        cuentaAdministradorSubsidioDTO.setIdAfiliadoPrincipal(detallesSubsidiosAsignados.get(0).getIdAfiliadoPrincipal());
        cuentaAdministradorSubsidioDTO.setIdBeneficiarioDetalle(detallesSubsidiosAsignados.get(0).getIdBeneficiarioDetalle());
        cuentaAdministradorSubsidioDTO.setIdGrupoFamiliar(detallesSubsidiosAsignados.get(0).getIdGrupoFamiliar());
        cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(detallesSubsidiosAsignados.get(0).getIdSolicitudLiquidacionSubsidio());
        cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(new Date());
        cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(userDTO.getNombreUsuario());
        cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(new Date());
        cuentaAdministradorSubsidioDTO.setIdSitioDePago(idSitioDePago);

        cuentaAdministradorSubsidioDTO.setListaDetallesSubsidioAsignadoDTO(detallesSubsidiosAsignados);

        Long idAbono = null;
        try {
            //se crea el abono y se obtiene el id.
            idAbono = crearAbonoDetallesSubsidiosAsignado(cuentaAdministradorSubsidioDTO);

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en la creación del abono", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        //se relaciona cada detalle con el abono creado.
        for (DetalleSubsidioAsignadoDTO detalle : detallesSubsidiosAsignados) {

            detalle.setIdCuentaAdministradorSubsidio(idAbono);
        }

        try {
            //se crea el abono y se obtiene el id
            crearDetallesSubsidioAsignado(detallesSubsidiosAsignados);

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en la creación de los detalles de subsidios asignadoss", e);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idAbono;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#anularSubsidiosMonetariosSinReemplazo(com.asopagos.subsidiomonetario.pagos.dto.AbonoAnuladoDetalleAnuladoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<Long> anularSubsidiosMonetariosSinReemplazo(AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO, UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.anularSubsidiosMonetariosSinReemplazo(List<DetalleSubsidioAsignadoDTO>,List<Long>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> listaIdsNuevosAbonos = new ArrayList<>();
        List<Callable<Long>> tareasParalelas = new LinkedList<>();

        for (Long idCuentaAdmonSubsidio : abonoAnuladoDetalleAnuladoDTO.getListaIdsCuentasAdmonSubsidios()) {

            List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = new ArrayList<>();

            //se asocia cada detalle con su respectivo abono
            for (DetalleSubsidioAsignadoDTO detalle : abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO()) {

                if (detalle.getIdCuentaAdministradorSubsidio().longValue() == idCuentaAdmonSubsidio.longValue()) {
                    listaDetallesDTOAnular.add(detalle);
                }
            }

            Callable<Long> parallelTask = () -> {
                return anularSubsidiosMonetariosSinReemplazoInter(listaDetallesDTOAnular, idCuentaAdmonSubsidio, abonoAnuladoDetalleAnuladoDTO, userDTO);
            };
            tareasParalelas.add(parallelTask);
        }

        try {
            List<Future<Long>> resultadosFuturos = managedExecutorService.invokeAll(tareasParalelas);

            for (Future<Long> future : resultadosFuturos) {
                listaIdsNuevosAbonos.add(future.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            logger.error(e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaIdsNuevosAbonos;
    }

    private Long anularSubsidiosMonetariosSinReemplazoInter(List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular,
            Long idCuentaAdmonSubsidio, AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO, UserDTO userDTO) {

        String firmaServicio = "anularSubsidiosMonetariosSinReemplazoInter(List<DetalleSubsidioAsignadoDTO>, Long, AbonoAnuladoDetalleAnuladoDTO, UserDTO)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Long nuevoAbono = null;
        if (!listaDetallesDTOAnular.isEmpty()) {

            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = obtenerCuentaAdministradorSubsidioDTO(
                    idCuentaAdmonSubsidio);

            boolean validacionCuenta = true;
            //si el medio de pago es tarjeta , a diferencia a la anulación en efectivo,
            //se verifica la validez de la tarjeta por medio de ANIBOL
            if (TipoMedioDePagoEnum.TARJETA.equals(cuentaAdministradorSubsidioOrigDTO.getMedioDePago())) {

                ConsultarCuentaAdminMedioTarjeta cuentaAdmin = new ConsultarCuentaAdminMedioTarjeta(idCuentaAdmonSubsidio);
                cuentaAdmin.execute();
                CuentaAdministradorSubsidioDTO cuentaAdminTarjeta = cuentaAdmin.getResult();

                List<CuentaAdministradorSubsidioDTO> cuenta = new ArrayList<>();
                cuenta.add(cuentaAdminTarjeta);

                String idSolAnibol = dispersarDescuentosMedioTarjetaAnibol(cuenta, abonoAnuladoDetalleAnuladoDTO != null ? abonoAnuladoDetalleAnuladoDTO.getTipoProceso() : null);

                Gson gson = new GsonBuilder().create();

                List<Long> listaIdsDetalle = new ArrayList<>();
                List<String> motivosAnulacion = new ArrayList<>();

                for (DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO : listaDetallesDTOAnular) {

                    listaIdsDetalle.add(detalleSubsidioAsignadoDTO.getIdDetalleSubsidioAsignado());
                }

                RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
                reAnibolModeloDTO.setFechaHoraRegistro(new Date());

                if (abonoAnuladoDetalleAnuladoDTO != null
                        && abonoAnuladoDetalleAnuladoDTO.getTipoProceso() != null
                        && abonoAnuladoDetalleAnuladoDTO.getTipoProceso().equals(TipoProcesoAnibolEnum.PRESCRIPCION)) {

                    reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.ANULACION_PRESCRIPCION);
                    //reAnibolModeloDTO.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.PRESCRIPCION);
                } else {
                    reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.ANULACION);
                    for (DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO : listaDetallesDTOAnular) {

                        motivosAnulacion.add(detalleSubsidioAsignadoDTO.getMotivoAnulacion().name());
                    }
                    
                }
                reAnibolModeloDTO.setParametrosIN(gson.toJson(listaIdsDetalle));
                reAnibolModeloDTO.setMotivoAnulacion(gson.toJson(motivosAnulacion));
                reAnibolModeloDTO.setIdProceso(idSolAnibol);
                reAnibolModeloDTO.setSolicitudLiquidacionSubsidio(cuentaAdministradorSubsidioOrigDTO.getIdCuentaAdministradorSubsidio());
                reAnibolModeloDTO.setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.EN_ESPERA);

                RegistrarSolicitudAnibol registroSolicitud = new RegistrarSolicitudAnibol(reAnibolModeloDTO);
                registroSolicitud.execute();
                registroSolicitud.getResult();
                logger.info("**__** RegistrarSolicitudAnibol " + registroSolicitud.toString());
                //se retorna null para detener el proceso en este punto y no se haga el descuento hasta no tener respuesta de ANIBOL
                return null;
            } else {
                SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioOrigDTO,
                        listaDetallesDTOAnular);
                //se realiza el proceso de anulación sin reemplazo del detalle con su respectivo abono
                subsidioAnulacionDTO.setUsuarioDTO(userDTO);
                nuevoAbono = anularSubsidioMonetarioSinReemplazoH(subsidioAnulacionDTO, validacionCuenta);
            }
            //ENVIO COMUNICADO
            //   List<EtiquetaPlantillaComunicadoEnum> plantila = new ArrayList<>();
            //           plantila.add(EtiquetaPlantillaComunicadoEnum.COM_SUB_PRE_PAG_TRA);
            enviarComunicadoPrescripcion(EtiquetaPlantillaComunicadoEnum.COM_SUB_PRE_PAG_TRA, idCuentaAdmonSubsidio);
            //FIN  ENVIO COMUNICADO
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return nuevoAbono;
    }

    @Override
    public void dispersarPagosCambioMedioPago() {
        String firmaMetodo = "dispersarPagosCambioMedioPago()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarRegistroDispersionAnibol registrosAnibol = new ConsultarRegistroDispersionAnibol();
        registrosAnibol.execute();
        List<RegistroSolicitudAnibol> registros = registrosAnibol.getResult();

        for (RegistroSolicitudAnibol registroAnibol : registros) {

            List<ResultadoProcesamientoDTO> resultadosAnibol;
            resultadosAnibol = new ArrayList();

            try {
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                if(conexionAnibol.equals("TRUE")){
                ConsultarEstadoProcesamiento consultarEstadoProcesamiento = new ConsultarEstadoProcesamiento(registroAnibol.getIdProceso());
                consultarEstadoProcesamiento.execute();
                resultadosAnibol = consultarEstadoProcesamiento.getResult();
                }

            } catch (TechnicalException e) {
                logger.warn("No se pudo realizar la conexión con Anibol", e);
                return;
            }

            List<Long> cuentasTarjetaInactivaError = new ArrayList<>();

            for (ResultadoProcesamientoDTO resultadoAnibol : resultadosAnibol) {

                if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
                    Gson gson = new GsonBuilder().create();

                    Type listType = new TypeToken<ArrayList<Long>>() {
                    }.getType();
                    List<Long> listaIdsAdminSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);

                    if (resultadoAnibol.isExitoso()) {

                        dispersarPagosEstadoAplicadoOrigenAnulacion(listaIdsAdminSubsidio);

                        actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());

                    } else {
                        cuentasTarjetaInactivaError.addAll(listaIdsAdminSubsidio);
                    }
                }
                if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {

                    Gson gson = new GsonBuilder().create();
                    Type listType = new TypeToken<ArrayList<Long>>() {
                    }.getType();
                    List<Long> listaIdsAdminSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);

                    if (!resultadoAnibol.isExitoso()) {

                        cuentasTarjetaInactivaError.addAll(listaIdsAdminSubsidio);
                    }
                }
            }

            for (Long cuenta : cuentasTarjetaInactivaError) {
                registrarTransaccionFallida(cuenta);
                actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    @Asynchronous
    public void dispersarDescuentos() {
        String firmaMetodo = "dispersarDescuentos()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarRegistroSolicitudDescuentoAnibol registrosAnibol = new ConsultarRegistroSolicitudDescuentoAnibol();
        registrosAnibol.execute();
        List<RegistroSolicitudAnibol> registros = registrosAnibol.getResult();

        logger.info("ANULACIONES POR PROCESAR: " + registros.size());

        for (RegistroSolicitudAnibol registroAnibol : registros) {

            logger.info("Dispersar DESCUENTOS - anulaciones respuestaAnibol: " + registroAnibol.toString());


            List<ResultadoProcesamientoDTO> resultadosAnibol;

            try {
                ConsultarEstadoProcesamientoAnulacion consultarEstadoProcesamiento = new ConsultarEstadoProcesamientoAnulacion(registroAnibol.getIdProceso());
                consultarEstadoProcesamiento.execute();
                resultadosAnibol = consultarEstadoProcesamiento.getResult();
            } catch (TechnicalException e) {
                logger.warn("No se pudo realizar la conexión con Anibol", e);
                return;
            }

            //TODO se creará el campo en la tabla de registroSolicitudAnibol para el id de la cuenta de admin Subsidio
            // Long idCuentaAdminSubsidio = registroAnibol.getSolicitudLiquidacionSubsidio();

            // ConsultarCuentaAdmonSubsidioDTO consultarCuentaAdmin = new ConsultarCuentaAdmonSubsidioDTO(idCuentaAdminSubsidio);
            // consultarCuentaAdmin.execute();
            // CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmin.getResult();

            // List<CuentaAdministradorSubsidio> cuentas = new ArrayList<>();
            // cuentas.add(cuentaAdministradorSubsidioDTO.convertToEntity());
            // Map<String, CuentaAdministradorSubsidio> tarjetasIdCuentaAdministradorSubsidio = cuentas.stream()
            //         .collect(Collectors.toMap(CuentaAdministradorSubsidio::getNumeroTarjetaAdmonSubsidio, Function.identity()));

            // List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
            // if(resultadosAnibol!=null && !resultadosAnibol.isEmpty())
            // {
            //     for (ResultadoProcesamientoDTO resultadoAnibol : resultadosAnibol) {

            //         if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
            //             if (resultadoAnibol.isExitoso()) {
            //                 StringBuilder salida = new StringBuilder();
            //                 Gson gson = new GsonBuilder().create();
    
            //                 Type listType = new TypeToken<ArrayList<Long>>() {
            //                 }.getType();
            //                 List<Long> listaIdsDetallesSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);
    
            //                 List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = consultarDetallesSubsidio(listaIdsDetallesSubsidio);
    
            //                 SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioDTO, listaDetallesDTOAnular);
            //                 //se realiza el proceso de anulación sin reemplazo del detalle con su respectivo abono
            //                 //subsidioAnulacionDTO.setUsuarioDTO(userDTO);
    
            //                 Long nuevoAbono = anularSubsidioMonetarioSinReemplazoH(subsidioAnulacionDTO, true);
    
            //                 List<Long> listaNuevosAbonos = new ArrayList<>();
            //                 listaNuevosAbonos.add(nuevoAbono);
            //                 dispersarAbonosOrigenAnulacion(listaNuevosAbonos);
            //                 actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
    
            //             } else {
            //                 cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
            //                         tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
            //                 );
            //                 cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
            //             }
            //         }
            //         if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {
            //             if (!resultadoAnibol.isExitoso()) {
            //                 cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
            //                         tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
            //                 );
            //                 cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
    
            //             }
            //         }
            //     }
            // }
            // else{
            //     logger.info("No hay solicitudes por procesar");
            // }
            
            // for (CuentaAdministradorSubsidioDTO cuenta : cuentasTarjetaInactivaError) {
            //     registrarTransaccionFallida(cuenta);
            //     actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            // }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Asynchronous
    @Override
    public void dispersarAnulacionesPrescripcionPaso1() {
        String firmaMetodo = "dispersarAnulacionesPrescripcionPaso1()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarRegistroSolicitudDescuentoPrescripcionAnibol registrosAnibol = new ConsultarRegistroSolicitudDescuentoPrescripcionAnibol(null);
        registrosAnibol.execute();
        List<RegistroSolicitudAnibol> salida = registrosAnibol.getResult();

        salida.stream()
                .map(registro -> registro.getIdProceso())
                .distinct()
                .forEach(idSolicitud -> {
                    try {
                        String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                        if(conexionAnibol.equals("TRUE")){
                        ConsultarEstadoProcesamientoPrescripcion consultarEstadoProcesamiento = new ConsultarEstadoProcesamientoPrescripcion(idSolicitud);
                        consultarEstadoProcesamiento.execute();
                        }
                    } catch (TechnicalException e) {
                        logger.warn("No se pudo realizar la conexión con Anibol", e);
                    }
                });

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Asynchronous
    @Override
    public void dispersarAnulacionesPrescripcionPaso2(ResultadoDispersionAnibolDTO respuestaAnibol) {
        String firmaMetodo = "dispersarAnulacionesPrescripcionPaso2(ResultadoDispersionAnibolDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConsultarRegistroSolicitudDescuentoPrescripcionAnibol registrosAnibol = new ConsultarRegistroSolicitudDescuentoPrescripcionAnibol(Long.parseLong(respuestaAnibol.getIdSolicitud()));
        registrosAnibol.execute();

        //instancias de clases utilitarias para transformar los datos que vienen del bus de anibol
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<Long>>() {
        }.getType();

        logger.info("ResultadoDispersionAnibolDTO respuestaAnibol: " + respuestaAnibol.getIdSolicitud() + " - cantidad de registros: " + respuestaAnibol.getListadoResultadosProcesamiento().size());

        for (RegistroSolicitudAnibol registroAnibol : registrosAnibol.getResult()) {

            ConsultarCuentaAdmonSubsidioDTO consultarCuentaAdmin = new ConsultarCuentaAdmonSubsidioDTO(registroAnibol.getSolicitudLiquidacionSubsidio());
            consultarCuentaAdmin.execute();
            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmin.getResult();

            List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();

            for (ResultadoProcesamientoDTO resultadoAnibol : respuestaAnibol.getListadoResultadosProcesamiento()) {

                if (resultadoAnibol.getNumeroTarjeta().equals(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio())) {

                    if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
                        if (resultadoAnibol.isExitoso()) {

                            List<Long> listaIdsDetallesSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);

                            List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = consultarDetallesSubsidio(listaIdsDetallesSubsidio);

                            // Actualizamos el motivo de anulacion de los detalles antes de enviarlos a actualizar en BD a partir del motivo de anulacion asignado
                            for(DetalleSubsidioAsignadoDTO detalle : listaDetallesDTOAnular)
                            {
                                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.PRESCRIPCION);
                            }

                            SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioDTO, listaDetallesDTOAnular);

                            List<Long> listaNuevosAbonos = new ArrayList<>();
                            listaNuevosAbonos.add(anularSubsidioMonetarioSinReemplazoH(subsidioAnulacionDTO, true));
                            dispersarAbonosOrigenAnulacion(listaNuevosAbonos);
                            actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());

                        } else {
                            cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
                        }
                    }
                    if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {
                        if (!resultadoAnibol.isExitoso()) {
                            cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
                        }
                    }
                    break;
                }
            }

            for (CuentaAdministradorSubsidioDTO cuenta : cuentasTarjetaInactivaError) {
                registrarTransaccionFallida(cuenta);
                actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param listaIdsDetallesSubsidio
     * @return
     */
    private List<DetalleSubsidioAsignadoDTO> consultarDetallesSubsidio(List<Long> listaIdsDetallesSubsidio) {

        ObtenerListadoDetallesSubsidioAsingnado lista = new ObtenerListadoDetallesSubsidioAsingnado(listaIdsDetallesSubsidio);
        lista.execute();
        return lista.getResult();
    }

    /**
     * Método encargado de actualizar el estado de la solcitud de anibol cuando
     * ya fue procesada
     *
     * @param idSolicitudAnibol identificador de la solicitud que se va a
     * actualizar
     */
    private void actualizarEstadoRegistroSolicitudAnibol(Long idSolicitudAnibol) {
        ActualizarEstadoSolicitudAnibol actualizarEstadoSolicitudAnibol = new ActualizarEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.PROCESADA, idSolicitudAnibol);
        actualizarEstadoSolicitudAnibol.execute();
    }

    /**
     * Método encargado de actualizar el estado de las novedades procesadas
     * correctamente
     *
     * @param idSolicitudAnibol identificador de la solicitud que se va a
     * actualizar
     */
    private void actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long idSolicitudAnibol) {
        ActualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario actualizarEstadoSolicitudAnibol = new ActualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(idSolicitudAnibol);
        actualizarEstadoSolicitudAnibol.execute();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#anularSubsidiosMonetariosConReemplazo(com.asopagos.subsidiomonetario.pagos.dto.AbonoAnuladoDetalleAnuladoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<Long> anularSubsidiosMonetariosConReemplazo(AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO, UserDTO userDTO) {

        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.anularSubsidiosMonetariosConReemplazo(List<DetalleSubsidioAsignadoDTO>,List<Long>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> listaIdsNuevosAbonos = null;

        listaIdsNuevosAbonos = ejecutarAnulacionSubsidioMonetarioConReemplazo(abonoAnuladoDetalleAnuladoDTO, null);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return listaIdsNuevosAbonos;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#ejecutarAnulacionPorFechaDeVencimiento(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void ejecutarAnulacionPorFechaDeVencimiento(UserDTO userDTO, Integer limit) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.ejecutarAnulacionPorFechaDeVencimiento(List<SubsidioMonetarioPrescribirAnularFechaDTO>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        GenerarlistadoSubsidiosAnular generarlistadoSubsidiosAnular = new GenerarlistadoSubsidiosAnular(0, "VENCIMIENTO", limit, "", Boolean.FALSE,"null");
        generarlistadoSubsidiosAnular.execute();
        ListadoSubsidiosAnularDTO listadoSubsidiosAnularDTO = generarlistadoSubsidiosAnular.getResult();
        if (listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO() != null && !listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO().isEmpty()) {
            List<Long> listaNuevosAbonos = ejecutarAnulacionPorFechaVencimientoPrescripcion(listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO(),
                    MotivoAnulacionSubsidioAsignadoEnum.VENCIMIENTO, userDTO);
            listaNuevosAbonos.removeAll(Collections.singletonList(null));
            //se realiza la dispersión
            dispersarAbonosOrigenAnulacion(listaNuevosAbonos);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#ejecutarAnulacionPorPrescripcion(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void ejecutarAnulacionPorPrescripcion(UserDTO userDTO, Integer limit) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.ejecutarAnulacionPorPrescripcion(List<SubsidioMonetarioPrescribirAnularFechaDTO>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        GenerarlistadoSubsidiosAnular generarlistadoSubsidiosAnular = new GenerarlistadoSubsidiosAnular(0, "PRESCRIPCION", limit, "", Boolean.FALSE,"null");
        generarlistadoSubsidiosAnular.execute();
        ListadoSubsidiosAnularDTO listadoSubsidiosAnularDTO = generarlistadoSubsidiosAnular.getResult();
        if (listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO() != null && !listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO().isEmpty()) {
            List<Long> listaNuevosAbonos = ejecutarAnulacionPrescripcion(listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO(),
                    MotivoAnulacionSubsidioAsignadoEnum.PRESCRIPCION, userDTO);

            listaNuevosAbonos.removeAll(Collections.singletonList(null));
            //se realiza la dispersión
            dispersarAbonosOrigenAnulacion(listaNuevosAbonos);
            //CompletableFuture<Long> completableFuture = new CompletableFuture<>();
            //completableFuture.complete(1L);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        //return lista.isEmpty()?null:lista;
        //return completableFuture;
    }

    /**
     * Metodo encargado de ejecutar la anulación sea por fecha de
     * vencimiento(HU-31-223) o prescripción(HU-31-224).
     *
     * @param listaSubsidiosAnular lista de subsidios seleccionados para
     * anulación
     * @param motivoAnulacion motivo de anulación de los subsidios
     * @param userDTO usuario que registra la anulación.
     * @return lista de los nuevos abonos generados por los detalles que no
     * fueron anulados de los anteriores abonos.
     */
    private List<Long> ejecutarAnulacionPrescripcion(
            List<SubsidioMonetarioPrescribirAnularFechaDTO> listaSubsidiosAnular, MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion,
            UserDTO userDTO) {

        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = consultarDetallesSubsidioParaAnular(
                listaSubsidiosAnular, motivoAnulacion);

        abonoAnuladoDetalleAnuladoDTO.setTipoProceso(TipoProcesoAnibolEnum.PRESCRIPCION);

        //se ejecuta la anulación sin reemplazo con el motivo de anulación por vencimiento
        return anularSubsidiosMonetariosSinReemplazo(abonoAnuladoDetalleAnuladoDTO, userDTO);
    }

    private AbonoAnuladoDetalleAnuladoDTO consultarDetallesSubsidioParaAnular(
            List<SubsidioMonetarioPrescribirAnularFechaDTO> listaSubsidiosAnular,
            MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion) {
        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();

        List<Long> listaIdsCuentas = new ArrayList<>();
        List<Long> listaIdsDetalles = new ArrayList<>();

        for (SubsidioMonetarioPrescribirAnularFechaDTO subsidioAnular : listaSubsidiosAnular) {

            //se agregan los identificadores de las cuentas que no esten agregadas.
            if (!listaIdsCuentas.contains(subsidioAnular.getIdCuentaAdminSubsidio())) {
                listaIdsCuentas.add(subsidioAnular.getIdCuentaAdminSubsidio());
            }
            //se agregan los identificadores de los detalles que no esten agregados.
            if (!listaIdsDetalles.contains(subsidioAnular.getIdDetalleSubsidioAsignado())) {
                listaIdsDetalles.add(subsidioAnular.getIdDetalleSubsidioAsignado());
            }
        }

        List<DetalleSubsidioAsignadoDTO> listaDetalles = obtenerDetallesDTOPorIDs(listaIdsDetalles);

        //a cada detalle se le pone el motivo de anulación
        for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
            detalle.setMotivoAnulacion(motivoAnulacion);
        }

        abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(listaIdsCuentas);
        abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(listaDetalles);
        return abonoAnuladoDetalleAnuladoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#ejecutarAnulacionPorPerdidaDeDerecho(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> ejecutarAnulacionPorPerdidaDeDerecho(List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular,
            UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.ejecutarAnulacionPorPerdidaDeDerecho(List<SubsidiosConsultaAnularPerdidaDerechoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> listaNuevosAbonos = ejecutarAnulacionPorPerdidaDerecho(subsidiosCandidatosAnular, userDTO);

        listaNuevosAbonos.removeAll(Collections.singletonList(null));

        Object[] listaAux = listaNuevosAbonos.toArray().clone();
        @SuppressWarnings("rawtypes")
        List lista = Arrays.asList(listaAux);

        //se realiza la dispersión
        dispersarAbonosOrigenAnulacion(listaNuevosAbonos);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lista;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#ejecutarAnulacionPorPerdidaDeDerecho(java.util.List,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean utilitarioAnularCuota20200211(UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.utilitarioMantis0266382(List<SubsidiosConsultaAnularPerdidaDerechoDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Boolean termino = Boolean.FALSE;

        ConsultarCuentasPorAnularMantis266382 mantis = new ConsultarCuentasPorAnularMantis266382();
        mantis.execute();
        List<SubsidiosConsultaAnularPerdidaDerechoDTO> detalles = mantis.getResult();

        List<SubsidiosConsultaAnularPerdidaDerechoDTO> subsidiosCandidatosAnular = new ArrayList<SubsidiosConsultaAnularPerdidaDerechoDTO>();

        Long cuentaAdministradorSubsidio = detalles.get(0).getIdCuentaAdminSubsidio();

        subsidiosCandidatosAnular.add(detalles.get(0));

        for (int i = 1; i < detalles.size(); i++) {
            if (detalles.get(i).getIdCuentaAdminSubsidio().equals(cuentaAdministradorSubsidio)) {
                subsidiosCandidatosAnular.add(detalles.get(i));
                logger.info("subsidiosCandidatosAnular.add: ");
                logger.info("cuentaAdministradorSubsidio: " + cuentaAdministradorSubsidio);
                logger.info("getIdDetalleSubsidioAsignado: " + detalles.get(i).getIdDetalleSubsidioAsignado());
            } else {
                logger.info("ejecutarAnulacionPorPerdidaDeDerecho: ");
                logger.info("cuentaAdministradorSubsidio: " + cuentaAdministradorSubsidio);
                logger.info("getIdDetalleSubsidioAsignado: " + detalles.get(i).getIdDetalleSubsidioAsignado());
                ejecutarAnulacionPorPerdidaDeDerecho(subsidiosCandidatosAnular, userDTO);
                subsidiosCandidatosAnular = new ArrayList<SubsidiosConsultaAnularPerdidaDerechoDTO>();
                subsidiosCandidatosAnular.add(detalles.get(i));
            }

            cuentaAdministradorSubsidio = detalles.get(i).getIdCuentaAdminSubsidio();
        }
        ejecutarAnulacionPorPerdidaDeDerecho(subsidiosCandidatosAnular, userDTO);

        termino = Boolean.TRUE;

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return termino;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#registrarCambioMedioDePagoSubsidio(com.asopagos.subsidiomonetario.pagos.dto.RegistroCambioMedioDePagoDTO,
     * java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> registrarCambioMedioDePagoSubsidio(RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO, Long idAdminSubsidio,
            UserDTO userDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.registrarCambioMedioDePagoSubsidio(RegistroCambioMedioDePagoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> listaIdNuevosAbonos = new ArrayList<>();
        //abonos que serán anulados
        List<Long> lstIdCuentasAnulacion = new ArrayList<>();
        //detalles que serán anulados
        List<DetalleSubsidioAsignadoDTO> lstDetallesAnulacion = new ArrayList<>();

        //si el medio de pago es EFECTIVO y el id es nulo, es porque el medio de pago EFECTIVO para ese administrador
        //no existe y se procede a crearlo.
        if (TipoMedioDePagoEnum.EFECTIVO.equals(registroCambioMedioDePagoDTO.getMedioDePagoModelo().getTipoMedioDePago())
                && registroCambioMedioDePagoDTO.getMedioDePagoModelo().getIdMedioDePago() == null) {

            Long idMedioDePagoEfectivo = registrarMedioDePago(registroCambioMedioDePagoDTO.getMedioDePagoModelo());
            registroCambioMedioDePagoDTO.getMedioDePagoModelo().setIdMedioDePago(idMedioDePagoEfectivo);
        }

        for (CuentaAdministradorSubsidioDTO cuenta : registroCambioMedioDePagoDTO.getListaRegistrosAbonos()) {
            //se agrega el id de la cuenta a ser anulada.
            lstIdCuentasAnulacion.add(cuenta.getIdCuentaAdministradorSubsidio());

            List<DetalleSubsidioAsignadoDTO> listaDetalles = obtenerDetallesSubsidiosAsignadosAsociadosAbono(
                    cuenta.getIdCuentaAdministradorSubsidio());
            //a cada detalle se le pone el motivo de anulación
            for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.CAMBIO_MEDIO_DE_PAGO);
                lstDetallesAnulacion.add(detalle);
            }
        }

        Map<Long, MedioDePagoModeloDTO> mediosDePagos = new HashMap<>();
        for (Long idCuenta : lstIdCuentasAnulacion) {
            mediosDePagos.put(idCuenta, registroCambioMedioDePagoDTO.getMedioDePagoModelo());
        }
        //se crea el objeto para anular.
        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();
        abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(lstIdCuentasAnulacion);
        abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(lstDetallesAnulacion);
        //se ejecuta la anulación con reemplazo y se envia el medio de pago por el cual se cambiarán los abonos
        listaIdNuevosAbonos = ejecutarAnulacionSubsidioMonetarioConReemplazo(abonoAnuladoDetalleAnuladoDTO, mediosDePagos);

        Object[] listaAux = listaIdNuevosAbonos.toArray().clone();
        @SuppressWarnings("rawtypes")
        List lista = Arrays.asList(listaAux);
        //se realiza la dispersión

        dispersarAbonosOrigenCambioMedioPago(listaIdNuevosAbonos);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lista;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarEmpresaConvenioTerceroPagador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String)
     */
    @Override
    public EmpresaModeloDTO consultarEmpresaConvenioTerceroPagador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.ejecutarAnulacionSubsidioCobrado(List<CuentaAdministradorSubsidioDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<EmpresaModeloDTO> empresasModeloDTO = consultarEmpresaDTO(tipoIdentificacion, numeroIdentificacion);

        if (empresasModeloDTO != null) {
            ConvenioTercerPagadorDTO convenio = consultarConvenioTerceroPagadorEmpresaId(empresasModeloDTO.get(0).getIdEmpresa());

            if (convenio != null) {
                //Se comenta para solucion de mantis 0267209
                /*logger.debug(
                        ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, el convenio ya se encuentra registrado.");
                //si la empresa ya esta registrada en un convenio, se envia el DTO con el idEmpresa en cero para que la pantalla
                //lance la excepción
                EmpresaModeloDTO empresaDTO = new EmpresaModeloDTO();
                empresaDTO.setIdEmpresa(0L);
                return empresaDTO;*/
                empresasModeloDTO.get(0).setExisteConvenio(Boolean.TRUE);
            } else {
                empresasModeloDTO.get(0).setExisteConvenio(Boolean.FALSE);
            }
            return empresasModeloDTO.get(0);
        } else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            EmpresaModeloDTO empresa = new EmpresaModeloDTO();
            PersonaDTO persona = consultarPersonaConvenio(numeroIdentificacion, tipoIdentificacion);
            if (persona != null) {
                empresa.convertFromPersonaDTO(persona);
                empresa.setUbicacionModeloDTO(null);
                empresa.setExisteConvenio(Boolean.FALSE);
            }

            return persona != null ? empresa : null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#cargarArchivoConsumoTarjetaAnibol()
     */
    @Override
    public void cargarArchivoConsumoTarjetaAnibol() {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.cargarArchivoConsumoTarjetaAnibol()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        String idArchivoConsumoAnibol = null;

        System.out.println("paso cargarArchivoConsumoTarjetaAnibol");

        //se obtiene el archivo desde el FTP
        List<InformacionArchivoDTO> lstArchivoConsumoTarjeta = null;
        ResultadoValidacionArchivoRetiroDTO resultadoValidacionArchivoRetiroDTO = null;

        try {
            lstArchivoConsumoTarjeta = obtenerArchivoTarjetasAnibol();
            System.out.println("paso obtenerArchivoTarjetasAnibol");
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        if (lstArchivoConsumoTarjeta != null && !lstArchivoConsumoTarjeta.isEmpty()) {
            for (InformacionArchivoDTO archivoConsumoTarjeta : lstArchivoConsumoTarjeta) {
                //Se valida el formato del nombre del archivo y si dicho archivo ya existe previamente y esta procesado.
                Boolean isValidado = validarNombreArchivoConsumoTarjetaAnibol(archivoConsumoTarjeta.getFileName(), false);
                System.out.println("paso validarNombreArchivoConsumoTarjetaAnibol");

                if (isValidado) {
                    System.out.println("paso isValidado");
                    
                    String nombreArchivo = archivoConsumoTarjeta.getDocName();
                    nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));

                    archivoConsumoTarjeta.setDocName(nombreArchivo);

                    //Se almacena el archivo en el ECM 
                    InformacionArchivoDTO informacionArchivo = new InformacionArchivoDTO();
                    //try {
                    informacionArchivo = almacenarArchivo(archivoConsumoTarjeta);
                    System.out.println("paso almacenarArchivo");
                    informacionArchivo = obtenerArchivoECM(informacionArchivo.getIdentificadorDocumento());
                    System.out.println("paso obtenerArchivoECM");
                    /*} catch (Exception e) {
                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                    }*/

                    try {
                        informacionArchivo.setTipoCarga(TipoCargueArchivoConsumoAnibolEnum.AUTOMATICO.name());
                        informacionArchivo.setFileName(informacionArchivo.getFileName()+".TXT");
                        resultadoValidacionArchivoRetiroDTO = validarAlmacenarArchivoDeConsumoTarjeta(informacionArchivo);
                        System.out.println("paso validarAlmacenarArchivoDeConsumoTarjeta");
                        if (resultadoValidacionArchivoRetiroDTO != null) {
                            System.out.println("paso resultadoValidacionArchivoRetiroDTO != null");
                            if (EstadoArchivoRetiroTercerPagadorEnum.VALIDACIONES_NO_EXISTOSAS
                                    .equals(resultadoValidacionArchivoRetiroDTO.getEstadoValidacion())
                                    || EstadoArchivoRetiroTercerPagadorEnum.PROCESADO_CON_INCONSISTENCIA
                                            .equals(resultadoValidacionArchivoRetiroDTO.getEstadoValidacion())) {
                                //Se realiza el envio de la notificacion con las inconsistencias detectadas
                                idArchivoConsumoAnibol = resultadoValidacionArchivoRetiroDTO.getIdArchivoConsumoAnibol().toString();
                                enviarNotificacionConInconsistencias(idArchivoConsumoAnibol);
                                System.out.println("paso enviarNotificacionConInconsistencias(idArchivoConsumoAnibol);");
                            }
                        } else {
                            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                            System.out.println("paso exc 1");
                            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, "");
                        }
                    } catch (Exception e) {
                        System.out.println("paso exc 2");
                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                    }
                }
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        System.out.println("paso fin cargarArchivoConsumoTarjetaAnibol");
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#cargarManualArchivoConsumoTarjetaAnibol(java.lang.String)
     */
    @Override
    public ResultadoValidacionArchivoRetiroDTO cargarManualArchivoConsumoTarjetaAnibol(String idArchivoConsumo) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.cargarManualArchivoConsumoTarjetaAnibol(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoRetiroDTO resultado = new ResultadoValidacionArchivoRetiroDTO();
        idArchivoAnibol = null;

        //try {
        //se obtiene el archivo almacenado en el ECM
        InformacionArchivoDTO informacionArchivo = obtenerArchivoECM(idArchivoConsumo);

        informacionArchivo.setFileName(informacionArchivo.getFileName()+".TXT");
        //Se valida el formato del nombre del archivo y si dicho archivo ya existe previamente y esta procesado.
        Boolean isValidado = validarNombreArchivoConsumoTarjetaAnibol(informacionArchivo.getFileName(), true);
        String idArchivoConsumoAnibol;

        if (isValidado) {
            informacionArchivo.setTipoCarga(TipoCargueArchivoConsumoAnibolEnum.MANUAL.name());
            resultado = validarAlmacenarArchivoDeConsumoTarjeta(informacionArchivo);

            if (resultado != null) {
                if (EstadoArchivoRetiroTercerPagadorEnum.VALIDACIONES_NO_EXISTOSAS
                        .equals(resultado.getEstadoValidacion())
                        || EstadoArchivoRetiroTercerPagadorEnum.PROCESADO_CON_INCONSISTENCIA
                                .equals(resultado.getEstadoValidacion())) {
                    //Se realiza el envio de la notificacion con las inconsistencias detectadas
                    idArchivoConsumoAnibol = resultado.getIdArchivoConsumoAnibol().toString();
                    enviarNotificacionConInconsistencias(idArchivoConsumoAnibol);
                }
            }
        } else {
            resultado.setEstadoValidacion(EstadoArchivoRetiroTercerPagadorEnum.VALIDACIONES_NO_EXISTOSAS);
            resultado.setIdArchivoRetiroTerceroPagador(idArchivoConsumo);
            resultado.setNombreArchivoRetiro(informacionArchivo.getFileName());
            if (idArchivoAnibol != null) {
                enviarNotificacionConInconsistencias(idArchivoAnibol.toString());
            }
        }
        /*
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
         */
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarSaldoSubsidioTransaccion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, String> consultarSaldoSubsidioTransaccion(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin, String user,
            String password) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.consultarSaldoSubsidioTransaccion(TipoIdentificacionEnum,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        //se validan las credenciales del usuario
        Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

        Map<String, String> respuesta = null;

        if (usuarioValidado != null && usuarioValidado) {
            respuesta = consultarSaldoSubsidio(tipoIdAdmin, numeroIdAdmin, user);
        } else {
            respuesta = new HashMap<>();
            respuesta.put("resultado", String.valueOf(false));
            respuesta.put("saldoAdminSubsidio", String.valueOf("0"));
            respuesta.put("error", "autenticación fallida");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#solicitarRetiroSubsidioTransaccion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.math.BigDecimal, java.lang.String, java.lang.Long,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Map<String, String> solicitarRetiroSubsidioTransaccion(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, String usuario, Long fecha, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("solicitarRetiroSubsidioTransaccion"+user);
         
        Map<String, String> respuesta = null;

        Map<String, String> saldoSubsidio = consultarSaldoSubsidioTransaccion(tipoIdAdmin, numeroIdAdmin, user, password);
        if (saldoSubsidio.containsKey("error")) {
            //si encuentra un error en la consulta del saldo, se propaga el error a la interfaz.
            return saldoSubsidio;
        } else {
            //si la consulta del saldo es exitosa, se obtiene el valor y se procede a realizarse el retiro.
            BigDecimal saldoActualSubsidio = new BigDecimal(saldoSubsidio.get("saldoAdminSubsidio"));

            respuesta = solicitarRetiroSubsidio(tipoIdAdmin, numeroIdAdmin, valorSolicitado, usuario, fecha, idTransaccionTercerPagador,
                    departamento, municipio, user, saldoActualSubsidio, idPuntoCobro);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#confirmarValorEntregadoSubsidioTransaccion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.math.BigDecimal, java.math.BigDecimal,
     * java.lang.Long, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario,
            String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        //se validan las credenciales del usuario
        Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

        Map<String, String> respuesta = null;
        if (usuarioValidado != null && usuarioValidado) {
            respuesta = confirmarValorEntregadoSubsidio(tipoIdAdmin, numeroIdAdmin, valorSolicitado, valorEntregado, fecha,
                    idTransaccionTercerPagador, usuario, user, idPuntoCobro);
        } else {
            respuesta = new HashMap<>();
            respuesta.put("resultado", String.valueOf(false));
            respuesta.put("error", "autenticación fallida");
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#confirmarValorEntregadoSubsidioTransaccionCasoB(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.math.BigDecimal, java.math.BigDecimal,
     * java.lang.Long, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionCasoB(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario,
            String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> respuesta = null;

        respuesta = confirmarValorEntregadoSubsidioCasoB(tipoIdAdmin, numeroIdAdmin, valorSolicitado, valorEntregado, fecha,
                idTransaccionTercerPagador, usuario, idPuntoCobro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.math.BigDecimal, java.lang.String, java.lang.Long,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum tipoIdAdmin,
            String numeroIdAdmin, BigDecimal valorSolicitado, String usuario, Long fecha, String idTransaccionTercerPagador,
            String departamento, String municipio, String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> respuesta = null;
        Map<String, String> saldoSubsidio = consultarSaldoSubsidioTransaccion(tipoIdAdmin, numeroIdAdmin, user, password);
        if (saldoSubsidio.containsKey("error")) {
            //si encuentra un error en la consulta del saldo, se propaga el error a la interfaz.
            return saldoSubsidio;
        } else {
            //si la consulta del saldo es exitosa, se obtiene el valor y se procede a realizarse el retiro.
            BigDecimal saldoActualSubsidio = new BigDecimal(saldoSubsidio.get("saldoAdminSubsidio"));
            respuesta = solicitarRetiroConfirmarValorEntregadoSubsidio(tipoIdAdmin, numeroIdAdmin, saldoActualSubsidio, valorSolicitado, fecha, idTransaccionTercerPagador, departamento, municipio, usuario, user, idPuntoCobro);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    @Override
    public Map<String, String> consultarSaldoSubsidioTransaccionValidaciones(String tipoIdAdmin, String numeroIdAdmin, String user, String password) {
    
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.consultarSaldoSubsidioTransaccion(TipoIdentificacionEnum,String,String,String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("tipoIdAdmin"+tipoIdAdmin);
        logger.info("numeroIdAdmin"+numeroIdAdmin);
        logger.info("user"+user);
    

        if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password)) {
            logger.info("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALIDACION_CAMPOS);
        }
        
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

    private AdministradorSubsidioModeloDTO validarAdministrador(TipoIdentificacionEnum tipoIdentificacionEnum, String numeroIdAdmin) {
        try {
            return consultarAdministradorSubsidioGeneral(numeroIdAdmin, tipoIdentificacionEnum);
        } catch (Exception e) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_ADMINISTRADOR_SUBSIDIO);
        }
    }

    @Override
    public Map<String, String> solicitarRetiroSubsidioTransaccionValidaciones(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado, String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String user, String password, String idPuntoCobro) {
        logger.info("solicitarRetiroSubsidioTransaccionValidaciones"+user);
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Date fecha = new Date();
        if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || StringUtils.isEmpty(usuario)
                || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(departamento) || StringUtils.isEmpty(municipio) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password) || StringUtils.isEmpty(idPuntoCobro)) {
            logger.debug("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALIDACION_CAMPOS);
        }

        Map<String, String> respuesta = null;
        ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
        ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
        System.out.println("Llegue a Consultar Valor Minimo y Maximo");
        String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
        String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);
        System.out.println("valorMinimo: " + valorMinimo);
        System.out.println("valorMaximo: " + valorMaximo);
        ValidatorUtil.validarValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));

        Map<String, String> saldoSubsidio = consultarSaldoSubsidioTransaccionValidaciones(tipoIdAdmin, numeroIdAdmin, user, password);

        if (saldoSubsidio.containsKey("error")) {
            //si encuentra un error en la consulta del saldo, se propaga el error a la interfaz.
            return saldoSubsidio;
        } else {

            //si la consulta del saldo es exitosa, se obtiene el valor y se procede a realizarse el retiro.
            BigDecimal saldoActualSubsidio = new BigDecimal(saldoSubsidio.get("saldoAdminSubsidio"));
            ValidatorUtil.compareValorSolicitado(valorSolicitado, saldoActualSubsidio);
            respuesta = solicitarRetiroSubsidio(TipoIdentificacionEnum.valueOf(tipoIdAdmin), numeroIdAdmin, valorSolicitado, usuario, fecha.getTime(), idTransaccionTercerPagador,
                    departamento, municipio, user, saldoActualSubsidio, idPuntoCobro);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    @Override
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionValidaciones(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado, BigDecimal valorEntregado, String idTransaccionTercerPagador, String usuario, String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        //se valida la obligatoriedad de los campos
        if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || !Objects.nonNull(valorEntregado)
                || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(usuario) || StringUtils.isEmpty(user) || StringUtils.isEmpty(password) || StringUtils.isEmpty(idPuntoCobro)) {
            logger.debug("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALIDACION_CAMPOS);
        }
        Date fecha = new Date();

        //se validan las credenciales del usuario
        Boolean usuarioValidado = validarCredencialesUsuarios(user, password);

        Map<String, String> respuesta = null;
        if (usuarioValidado != null && usuarioValidado) {
            TipoIdentificacionEnum tipoIdentificacionEnum = null;
            try {
                tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdAdmin);
            } catch (Exception e) {
                logger.debug("Entrando a Error de Validacion tipoIdentificacionEnum");
                throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_TIPO_IDENTIFICACION);
            }
            ValidatorUtil.validatNumeroDocumento(numeroIdAdmin, tipoIdentificacionEnum);
            ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
            ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
            validarAdministrador(tipoIdentificacionEnum, numeroIdAdmin);
            String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
            String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);
            System.out.println("valorMinimo: " + valorMinimo);
            System.out.println("valorMaximo: " + valorMaximo);
            ValidatorUtil.validarValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));
            respuesta = confirmarValorEntregadoSubsidio(tipoIdentificacionEnum, numeroIdAdmin, valorSolicitado, valorEntregado, fecha.getTime(),
                    idTransaccionTercerPagador, usuario, user, idPuntoCobro);
        } else {
            logger.debug("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_USUARIO_CONTRASENNA);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    @Override
    public Map<String, String> confirmarValorEntregadoSubsidioTransaccionCasoBValidaciones(String tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, String idTransaccionTercerPagador, String usuario, String idPuntoCobro) {
        //se valida la obligatoriedad de los campos
        if (StringUtils.isEmpty(tipoIdAdmin) || StringUtils.isEmpty(numeroIdAdmin) || !Objects.nonNull(valorSolicitado) || !Objects.nonNull(valorEntregado)
                || StringUtils.isEmpty(idTransaccionTercerPagador) || StringUtils.isEmpty(usuario) || StringUtils.isEmpty(idPuntoCobro)) {
            logger.debug("Entrando a Error de Validacion");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALIDACION_CAMPOS);
        }
        Date fecha = new Date();

        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> respuesta = null;
        TipoIdentificacionEnum tipoIdentificacionEnum = null;
        try {
            tipoIdentificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdAdmin);
        } catch (Exception e) {
            logger.debug("Entrando a Error de Validacion tipoIdentificacionEnum");
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_TIPO_IDENTIFICACION);
        }
        ValidatorUtil.validatNumeroDocumento(numeroIdAdmin, tipoIdentificacionEnum);
        ValidatorUtil.validarTerceroPagador(idTransaccionTercerPagador);
        ValidatorUtil.validarIdentificadorPuntoCobro(idPuntoCobro);
        String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
        String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);
        System.out.println("valorMinimo: " + valorMinimo);
        System.out.println("valorMaximo: " + valorMaximo);
        ValidatorUtil.validarValorSolicitado(valorSolicitado, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));
        if (!valorEntregado.equals(valorSolicitado)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALOR_ENTREGADO_VALOR_SOLICITADO);
        }
        ValidatorUtil.compareValorSolicitadoValorEntregado(valorSolicitado, valorEntregado);
        validarAdministrador(tipoIdentificacionEnum, numeroIdAdmin);
        respuesta = confirmarValorEntregadoSubsidioCasoB(tipoIdentificacionEnum, numeroIdAdmin, valorSolicitado, valorEntregado, fecha.getTime(),
                idTransaccionTercerPagador, usuario, idPuntoCobro);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    @Override
    public Response solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
                                                                                            String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String user, String password, String idPuntoCobro) {

        //CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO = consultarInfoRetiro(tipoIdAdmin, numeroIdAdmin, TipoMedioDePagoEnum.EFECTIVO);

        Map<String, String> respuesta = new HashMap<>();
        String httpStatus = null;
        try {
            String firmaServicio = "###############NUEVO PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
            logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

            logger.info("Ingresa al composite de RETIRO");

            respuesta = solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionV3(
                    tipoIdAdmin, numeroIdAdmin, valorSolicitado, usuario, idTransaccionTercerPagador,
                    departamento, municipio, user, password, idPuntoCobro);

            logger.info("respode "+ respuesta);


            logger.info("Finalizo la ejecucion del servicio");

            logger.info("Trajo el resultado:");

            logger.info(respuesta.toString());

            httpStatus = respuesta.remove("CODIGO_ERROR");

            logger.info("httpStatus -> " + httpStatus);

            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);

            return Response.status(Response.Status.valueOf(httpStatus))
                    .type(MediaType.APPLICATION_JSON).entity(respuesta)
                    .build();


        } catch (Exception e) {
            //(idPuntoCobro, idTransaccionTercerPagador, usuario, cuentaAdministradorSubsidioYDetallesDTO);
            logger.error("Sucedio un error en el composite ", e);
            return Response.status(Response.Status.valueOf("BAD_REQUEST"))
                    .type(MediaType.APPLICATION_JSON).entity("Sucedio un error en la transaccion")
                    .build();
        }
    }

    private Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionV3(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado,
                                                                                                        String usuario, String idTransaccionTercerPagador, String departamento,
                                                                                                        String municipio, String user, String password, String idPuntoCobro) {
        SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3 solicitarRetiro = new SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3(tipoIdAdmin, numeroIdAdmin, valorSolicitado, usuario, idTransaccionTercerPagador, departamento, municipio, user, password, idPuntoCobro);
        solicitarRetiro.execute();
        return solicitarRetiro.getResult();
    }
    private CuentaAdministradorSubsidioYDetallesDTO consultarInfoRetiro (String tipoIdAdmin, String numeroIdAdmin, TipoMedioDePagoEnum medioDePago) {

        ConsultarCuentaAdminSubsidio consultarCuentaAdminSubsidio = new ConsultarCuentaAdminSubsidio(tipoIdAdmin, numeroIdAdmin, medioDePago);
        consultarCuentaAdminSubsidio.execute();
        return consultarCuentaAdminSubsidio.getResult();
    }

    public void rollbackRetiro(String idPuntoCobro, String idTransaccionTercerPagador,
                               String usuario, CuentaAdministradorSubsidioYDetallesDTO cuentaAdministradorSubsidioYDetallesDTO) {

        logger.info("rollbackRetiro cuentaAdministradorSubsidioYDetallesDTO " + cuentaAdministradorSubsidioYDetallesDTO);
        logger.info("rollbackRetiro cuentas " + cuentaAdministradorSubsidioYDetallesDTO.getListaCuentaAdministradorSubsidio().size());
        logger.info("rollbackRetiro detalles " + cuentaAdministradorSubsidioYDetallesDTO.getListaDetalleSubsidioAsignado().size());
        RollbackRetiro rollbackRetiro = new RollbackRetiro(idPuntoCobro,idTransaccionTercerPagador, usuario, cuentaAdministradorSubsidioYDetallesDTO, null);
        rollbackRetiro.execute();
    }

    @Override
    public Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidaciones(String tipoIdAdmin, String numeroIdAdmin, BigDecimal valorSolicitado, 
                            String usuario, String idTransaccionTercerPagador, String departamento, String municipio, String user, String password, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroConfirmarValorEntregadoSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
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
        String valorMinimo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MINIMO_SOLICITADO);
        String valorMaximo = (String) CacheManager.getParametro(PagosSubsidioMonetarioCompositeConstants.PAGOS_VALOR_MAXIMO_SOLICITADO);

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
                                                saldoActualSubsidio, valorSolicitado, fecha.getTime(), idTransaccionTercerPagador, 
                                                departamento, municipio, usuario, user, idPuntoCobro);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * Metodo que permite obtener un archivo a partir de su identificador en el
     * Enterprise Content Management
     *
     * @param identificadorArchivo <code>String</code> Identificador del archivo
     * dentro del ECM
     * @return DTO con información del archivo
     */
    private InformacionArchivoDTO obtenerArchivoECM(String identificadorArchivo) {
        ObtenerArchivo archivo = new ObtenerArchivo(identificadorArchivo);
        archivo.execute();
        return archivo.getResult();
    }

    /**
     * Metodo que valida el archivo de retiro tanto en estructura como en
     * incosistencia con los campos de la base de datos.
     *
     * @param archivoDTO información del archivo.
     * @param nombreUsuario usuario que registro la información en el sistema.
     * @param nombreTerceroPagador nombre del tercero pagador asociado (nombre
     * del convenio)
     * @return DTO que contiene la información de la validación.
     */
    private ResultadoValidacionArchivoDTO validarArchivorRetiroService(InformacionArchivoDTO archivoDTO, String nombreUsuario,
            String nombreTerceroPagador) {

        ValidarEstructuraArchivoRetiros validacionArchivo = new ValidarEstructuraArchivoRetiros(nombreTerceroPagador, nombreUsuario,
                archivoDTO);
        validacionArchivo.execute();
        return validacionArchivo.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente que registra la consola de estado
     * de la carga de un cliente.
     *
     * @param consolaCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaCargueProcesoDTO) {
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaCargueProcesoDTO);
        registroConsola.execute();
    }

    /**
     *
     * Metodo encargado de llamar el servicio de creación de una empresa.
     *
     * @param empresaModeloDTO variable que contiene los valores necesarios para
     * crear una empresa.
     * @return el id de la empresa.
     */
    private Long crearEmpresaConvenio(EmpresaModeloDTO empresaModeloDTO) {

        CrearEmpresa empresa = new CrearEmpresa(empresaModeloDTO);
        empresa.execute();
        return empresa.getResult();
    }

    /**
     * Metodo encargado de llamar el servicio de creación del convenio y su
     * respectiva solicitud de registro.
     *
     * @param convenioTercerPagadorDTO variable que contiene los valores
     * necesarios para crear un convenio y la solicitud respectiva.
     * @return id del convenio del tercero pagador registrado.
     */
    private Long crearSolicitudRegistroConvenio(ConvenioTercerPagadorDTO convenioTercerPagadorDTO) {

        RegistrarConvenioTercerPagador convenio = new RegistrarConvenioTercerPagador(convenioTercerPagadorDTO);
        convenio.execute();
        return convenio.getResult();
    }

    /**
     * Metodo encargado de llamar el servicio que almacena un abono (cuenta de
     * administrador del subsidio) en el proceso de pagos de subsidio monetario.
     *
     * @param cuentaAdministradorSubsidioDTO información del abono para ser
     * almacenado.
     * @return identificador del abono creado.
     */
    private Long crearAbonoDetallesSubsidiosAsignado(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {

        RegistrarCuentaAdministradorSubsidio abono = new RegistrarCuentaAdministradorSubsidio(cuentaAdministradorSubsidioDTO);
        abono.execute();
        return abono.getResult();
    }

    /**
     * Metodo encargado de llamar el servicio que almacena los detalles de
     * subsidios asignados.
     *
     * @param listaDetallesDTO lista que contiene los detalles de subsidios
     * asignados que serán registrados.
     */
    private void crearDetallesSubsidioAsignado(List<DetalleSubsidioAsignadoDTO> listaDetallesDTO) {

        RegistrarDetalleSubsidioAsignado detallesSubsidiosAsignados = new RegistrarDetalleSubsidioAsignado(listaDetallesDTO);
        detallesSubsidiosAsignados.execute();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#registrarSolicitudAnulacionSubsidioCobrado(com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudAnulacionSubsidioCobradoDTO registrarSolicitudAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.registrarSolicitudAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobradoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO = null;
        SolicitudModeloDTO solicitudModeloDTO = null;
        Long idSolicitud = null;
        Long idInstanciaProceso = null;
        String numeroRadicado = null;
        String tokenAccesoCore = null;
        boolean success = false;
        if (solicitudAnulacionSubsidioCobrado.getAbonosAnulacionSubsidioCobradoDTO() != null
                && !solicitudAnulacionSubsidioCobrado.getAbonosAnulacionSubsidioCobradoDTO().isEmpty()) {
            //se realiza el registro de la solicitud de anulacion de subsidio cobrado
            solicitudAnulacionSubsidioCobradoDTO = registrarAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobrado);
            if (solicitudAnulacionSubsidioCobradoDTO != null && solicitudAnulacionSubsidioCobradoDTO.getRegistroExitoso()) {
                //Se obtiene el token para llamado a servicio del BPM para la solicitud de anulacion de subsidio cobrado
                tokenAccesoCore = generarTokenAccesoCore();
                if (tokenAccesoCore != null) {
                    // Servicio composite para generar el numero de radicado
                    idSolicitud = solicitudAnulacionSubsidioCobradoDTO.getIdSolicitud();
                    numeroRadicado = generarNumeroRadicado(idSolicitud, userDTO.getSedeCajaCompensacion());
                    if (numeroRadicado != null) {
                        // Iniciar proceso dsie solicitud de subbsudio cobrado en BPM
                        idInstanciaProceso = iniciarProcesoSolicitudAnulacionSubsidioCobrado(idSolicitud, numeroRadicado, tokenAccesoCore,
                                userDTO.getNombreUsuario());
                        if (idInstanciaProceso != null) {
                            //Actualiza la solicitud global con el numero de intancia del BPM
                            solicitudModeloDTO = new SolicitudModeloDTO();
                            solicitudModeloDTO.setIdSolicitud(idSolicitud);
                            solicitudModeloDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
                            solicitudModeloDTO.setNumeroRadicacion(numeroRadicado);
                            success = actualizarSolicitudGlobalAnulacionSubsidioCobrado(solicitudModeloDTO);
                            if (success) {
                                solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.TRUE);
                                solicitudAnulacionSubsidioCobrado.setNumeroRadicado(numeroRadicado);
                            } else {
                                solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
                                solicitudAnulacionSubsidioCobrado.setCausaError(
                                        PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ACTUALIZACION_SOLICTUD_NO_EXITOSO);
                            }
                        } else {
                            solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
                            solicitudAnulacionSubsidioCobrado
                                    .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_INICIO_PROCESO_BPM_NO_EXITOSO);
                        }
                    } else {
                        solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
                        solicitudAnulacionSubsidioCobrado
                                .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ERROR_GENERACION_NUM_RADICADO);
                    }
                } else {
                    solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
                    solicitudAnulacionSubsidioCobrado
                            .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_GENERACION_TOKEN_ACCESO);
                }
            } else {
                solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
                solicitudAnulacionSubsidioCobrado
                        .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_REGISTRO_SOLICITUD_ANULACION_NO_EXITOSO);
            }
        } else {
            solicitudAnulacionSubsidioCobrado.setRegistroExitoso(Boolean.FALSE);
            solicitudAnulacionSubsidioCobrado
                    .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ANULACION_SIN_ABONOS_ASOCIADOS);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobrado;
    }

    /**
     * <b>Descripción:</b>Método que hace la peticion REST al servicio de
     * generar nuemro de radicado
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param idSolicitud <code>Long</code> El identificador de la solicitud
     * @param sedeCajaCompensacion <code>String</code> El usuario del sistema
     * @return <code>String</code> El numero de radicado para la solicitud de
     * anulacion de subsidio cobrado
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String numeroRadicado = null;
        try {
            RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
            radicarSolicitudService.execute();
            numeroRadicado = radicarSolicitudService.getResult();
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": Ocurrió un error al llamar al servicio que genera el numero de radicado para la solicitud de anulacion de subsidio cobrado",
                    e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return numeroRadicado;
    }

    /**
     * <b>Descripción:</b>Método encargado de registrar anulación de subsidios
     * asignados cobrados.
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param solicitudAnulacionSubsidioCobradoDTO
     * <code>SolicitudAnulacionSubsidioCobradoDTO</code> representa la
     * información que representa los abonos cobrados asociados a una solicitud
     * de anulación de subsidio cobrado
     *
     * @return <code>SolicitudAnulacionSubsidioCobradoDTO</code> Datos que
     * representan el registro de anulacion de subsido cobrado
     */
    private SolicitudAnulacionSubsidioCobradoDTO registrarAnulacionSubsidioCobrado(
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.registrarAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobradoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO = null;
        try {
            RegistrarAnulacionSubsidioCobrado registrarAnulacionSubsidioCobradoService = new RegistrarAnulacionSubsidioCobrado(
                    solicitudAnulacionSubsidioCobrado);
            registrarAnulacionSubsidioCobradoService.execute();
            solicitudAnulacionSubsidioCobradoDTO = registrarAnulacionSubsidioCobradoService.getResult();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": Ocurrió un error al registrar la solicitud de anulacion de subsidio cobrado", e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * <b>Descripción:</b>Método que hace la peticion REST al servicio de
     * generar token de acceso core para asociar a la solicitud global
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @return <code>String</code> El token de acceso CORE
     */
    private String generarTokenAccesoCore() {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarTokenAccesoCore( )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        TokenDTO tokenDTO = null;
        String tokenGenerado = null;
        try {
            GenerarTokenAccesoCore generarTokenAccesoCoreService = new GenerarTokenAccesoCore();
            generarTokenAccesoCoreService.execute();
            tokenDTO = generarTokenAccesoCoreService.getResult();
            tokenGenerado = tokenDTO.getToken();
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": Ocurrió un error al generar el token de acceso a core para registrar en la solicitud global asociada a la solicitud de anulacion de subsidio cobrado",
                    e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return tokenGenerado;

    }

    /**
     * <b>Descripción:</b>Método que hace la peticion REST al servicio de
     * iniciarProceso para la Solicitud de Anulacion de Subsidio Cobrado
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @return <code>Long</code> El id instancia de proceso BPM asociado a la
     * solicitud de Anulacion de Subsidio Cobrado
     */
    private Long iniciarProcesoSolicitudAnulacionSubsidioCobrado(Long idSolicitud, String numeroRadicado, String tokenAccesoCore,
            String nombreUsuario) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.iniciarProcesoSolicitudAnulacionSubsidioCobrado( Long, String, String, String )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Map<String, Object> params = null;
        Long idInstanciaProceso = null;
        try {
            // Iniciar proceso de liquidacion BPM
            params = new HashMap<>();
            params.put("idSolicitud", idSolicitud.toString());
            params.put("numeroRadicado", numeroRadicado);
            params.put("supervisorSubsidio", nombreUsuario);

            IniciarProceso iniciarProcesoService = new IniciarProceso(ProcesoEnum.SOLICITUD_ANULACION_SUBSIDIO_COBRADO, params);
            iniciarProcesoService.setToken(tokenAccesoCore);
            iniciarProcesoService.execute();
            idInstanciaProceso = iniciarProcesoService.getResult();
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": Ocurrió un error al realizar el llamado del servicio que inicia el proceso en BPM; para la solicitud global asociada a la solicitud de anulacion de subsidio cobrado",
                    e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idInstanciaProceso;
    }

    /**
     * <b>Descripción:</b>Método que hace la peticion REST al servicio de
     * actualizar El número de la instancia del proceso BPM de la solicitud
     * global indicada por id
     * <b>Módulo:</b> Asopagos - HU-31-222<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param solicitudModeloDTO <code>SolicitudModeloDTO</code> DTO que
     * contiene los datos de un solicitud a actualizar
     *
     * @return <code>boolean</code> Si se realizo la actualizacion de la
     * solicitud global asociada a la solicicutd de anulacion de subsidio
     * cobrado
     */
    private boolean actualizarSolicitudGlobalAnulacionSubsidioCobrado(SolicitudModeloDTO solicitudModeloDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.actualizarSolicitudGlobalAnulacionSubsidioCobrado( Long, Long, String, ResultadoProcesoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Solicitud solicitud = null;
        Long idSolicitud = null;
        boolean success = false;
        try {
            idSolicitud = solicitudModeloDTO.getIdSolicitud();
            BuscarSolicitudPorId buscarSolicitudPorIdService = new BuscarSolicitudPorId(idSolicitud);
            buscarSolicitudPorIdService.execute();
            solicitud = buscarSolicitudPorIdService.getResult();
            if (solicitud != null) {
                if (solicitudModeloDTO.getNumeroRadicacion() != null) {
                    solicitud.setNumeroRadicacion(solicitudModeloDTO.getNumeroRadicacion());
                }
                if (solicitudModeloDTO.getIdInstanciaProceso() != null) {
                    solicitud.setIdInstanciaProceso(solicitudModeloDTO.getIdInstanciaProceso());
                }
                if (solicitudModeloDTO.getResultadoProceso() != null) {
                    solicitud.setResultadoProceso(solicitudModeloDTO.getResultadoProceso());
                }
                ActualizarSolicitud actualizarSolicitud = new ActualizarSolicitud(idSolicitud, solicitud);
                actualizarSolicitud.execute();
                success = true;
            } else {
                throw new ParametroInvalidoExcepcion(
                        "No se encontro la solicitud global indicada con el identificador: '" + idSolicitud + "'");
            }
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaMetodo
                    + ": Ocurrió un error al llamar al servicio que genera el numero de radicado para la solicitud de anulacion de subsidio cobrado",
                    e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;
    }

    /**
     * Metodo encargado de llamar el cliente de ConsultarCuentaAdmonSubsidioDTO
     * del microservicio PagosSubsidioMonetario para obtener la cuenta del
     * administrador del subsidio que tiene asociado el id.
     *
     * @author mosorio
     *
     * @param idCuentaAdminSubsidio <code>Long</code> Identificador de la cuenta
     * del administrador del subsidio
     * @return CuentaAdministradorSubsidioDTO
     */
    private CuentaAdministradorSubsidioDTO obtenerCuentaAdministradorSubsidioDTO(Long idCuentaAdminSubsidio) {
        ConsultarCuentaAdmonSubsidioDTO cuentaAdminSubsidio = new ConsultarCuentaAdmonSubsidioDTO(idCuentaAdminSubsidio);
        cuentaAdminSubsidio.execute();
        return cuentaAdminSubsidio.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente AnularSubsidioMonetarioSinReemplazo
     * del microservicio PagosSubsidioMonetario para realizar el proceso de
     * anulación de una cuenta del administrador de del subsidio con sus
     * detalles de subsidios asignados correspondientes.
     *
     * @author mosorio
     *
     * @param subsidioAnulacionDTO <code>SubsidioAnulacionDTO</code> DTO que
     * contiene la Lista de los detalles DTO que se van anular con reemplazo y
     * Cuenta del administrador del subsidio al cual se anulara a partir de sus
     * detalles.
     * @param resultadoValidacion <code>Boolean</code> Resultado de la
     * validación de la devolución de la tarjeta (Si el medio de pago es
     * tarjeta) por parte de ANIBOL.
     * @return Identificador del registro de anulación.
     */
    private Long anularSubsidioMonetarioSinReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO, Boolean resultadoValidacion) {
        AnularSubsidioMonetarioSinReemplazo monetarioSinReemplazo = new AnularSubsidioMonetarioSinReemplazo(resultadoValidacion,
                subsidioAnulacionDTO);
        monetarioSinReemplazo.execute();
        return monetarioSinReemplazo.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente AnularSubsidioMonetarioSinReemplazo
     * del microservicio PagosSubsidioMonetario para realizar el proceso de
     * anulación de una cuenta del administrador de del subsidio con sus
     * detalles de subsidios asignados correspondientes.
     *
     * @author mosorio
     *
     * @param subsidioAnulacionDTO <code>SubsidioAnulacionDTO</code> DTO que
     * contiene la Lista de los detalles DTO que se van anular con reemplazo y
     * Cuenta del administrador del subsidio al cual se anulara a partir de sus
     * detalles.
     * @param resultadoValidacion <code>Boolean</code> Resultado de la
     * validación de la devolución de la tarjeta (Si el medio de pago es
     * tarjeta) por parte de ANIBOL.
     * @return Identificador del registro de anulación.
     */
    private Long anularSubsidioMonetarioSinReemplazoH(SubsidioAnulacionDTO subsidioAnulacionDTO, Boolean resultadoValidacion) {
        AnularSubsidioMonetarioSinReemplazoH monetarioSinReemplazo = new AnularSubsidioMonetarioSinReemplazoH(resultadoValidacion,
                subsidioAnulacionDTO);
        monetarioSinReemplazo.execute();
        return monetarioSinReemplazo.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente AnularSubsidioMonetarioConReemplazo
     * del microservicio PagosSubsbdioMonetario para realizar el proceso de
     * anulación con reemplazo de una cuenta del administrador del subsidio con
     * sus detalles de subsidios asignados correspondientes.
     *
     * @author mosorio
     *
     * @param subsidioAnulacionDTO <code>SubsidioAnulacionDTO</code> DTO que
     * contiene la Lista de los detalles DTO que se van anular con reemplazo y
     * Cuenta del administrador del subsidio al cual se anulara a partir de sus
     * detalles.
     * @param medioDePagoModelo <code>MedioDePagoCambioDTO</code> Medio de pago
     * seleccionado
     * @param resultadoValidacion <code>Boolean</code> Resultado de la
     * validación de la devolución de la tarjeta (Si el medio de pago es
     * tarjeta) por parte de ANIBOL.
     * @return
     */
    private Long anularSubsidioMonetarioConReemplazo(SubsidioAnulacionDTO subsidioAnulacionDTO, Boolean resultadoValidacion) {
        AnularSubsidioMonetarioConReemplazo monetarioConReemplazo = new AnularSubsidioMonetarioConReemplazo(resultadoValidacion,
                subsidioAnulacionDTO);
        monetarioConReemplazo.execute();
        return monetarioConReemplazo.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente ConsultarDetallesDTOPorIDs del
     * microservicio PagosSubsidioMonetario permitiendo obtener los detalles de
     * subsidios asignados en forma de DTO a partir de sus identificadores
     *
     * @param listaIdsDetalles <code>List<Long></code> Lista de identificadores
     * de los detalles de subsidios asignados
     * @return Lista de detalles de subsidios asignados en forma de DTO.
     */
    private List<DetalleSubsidioAsignadoDTO> obtenerDetallesDTOPorIDs(List<Long> listaIdsDetalles) {
        ConsultarDetallesDTOPorIDs detallesAsignados = new ConsultarDetallesDTOPorIDs(listaIdsDetalles);
        detallesAsignados.execute();
        return detallesAsignados.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente
     * obtenerDetallesSubsidiosAsignadosAsociadosAbono del microservicio
     * PagosSubsidioMonetario permitiendo obtener los detalles de subsidios
     * asignados en forma de DTO a partir del identificador principal de la
     * cuenta del administrador del subsidio a la cual estan asociadas.
     *
     * @param idCuentaAdmin <code>Long</code> Identificador principal de la
     * cuenta del administrador del subsidio.
     * @return
     */
    private List<DetalleSubsidioAsignadoDTO> obtenerDetallesSubsidiosAsignadosAsociadosAbono(Long idCuentaAdmin) {
        ConsultarDetallesSubsidiosAsignadosAsociadosAbono asignadosAsociadosAbono = new ConsultarDetallesSubsidiosAsignadosAsociadosAbono(
                idCuentaAdmin);
        asignadosAsociadosAbono.execute();
        return asignadosAsociadosAbono.getResult();
    }

    /**
     * Metodo encargado de ejecutar la anulación sea por fecha de
     * vencimiento(HU-31-223) o prescripción(HU-31-224).
     *
     * @param listaSubsidiosAnular lista de subsidios seleccionados para
     * anulación
     * @param motivoAnulacion motivo de anulación de los subsidios
     * @param userDTO usuario que registra la anulación.
     * @return lista de los nuevos abonos generados por los detalles que no
     * fueron anulados de los anteriores abonos.
     */
    private List<Long> ejecutarAnulacionPorFechaVencimientoPrescripcion(
            List<SubsidioMonetarioPrescribirAnularFechaDTO> listaSubsidiosAnular, MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion,
            UserDTO userDTO) {

        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = consultarDetallesSubsidioParaAnular(
                listaSubsidiosAnular, motivoAnulacion);

        //se ejecuta la anulación sin reemplazo con el motivo de anulación por vencimiento
        return anularSubsidiosMonetariosSinReemplazo(abonoAnuladoDetalleAnuladoDTO, userDTO);

    }

    /**
     * Metodo encargado de ejecutar la anulación por perdida de derecho
     * (HU-31-225).
     *
     * @param listaSubsidiosAnular lista de subsidios seleccionados para
     * anulación
     * @param userDTO usuario que registra la anulación.
     * @return lista de los nuevos abonos generados por los detalles que no
     * fueron anulados de los anteriores abonos.
     */
    private List<Long> ejecutarAnulacionPorPerdidaDerecho(List<SubsidiosConsultaAnularPerdidaDerechoDTO> listaSubsidiosAnular,
            UserDTO userDTO) {

        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();

        List<Long> listaIdsCuentas = new ArrayList<>();
        List<Long> listaIdsDetalles = new ArrayList<>();

        for (SubsidiosConsultaAnularPerdidaDerechoDTO subsidioAnular : listaSubsidiosAnular) {

            //se agregan los identificadores de las cuentas que no esten agregadas.
            if (!listaIdsCuentas.contains(subsidioAnular.getIdCuentaAdminSubsidio())) {
                listaIdsCuentas.add(subsidioAnular.getIdCuentaAdminSubsidio());
            }
            //se agregan los identificadores de los detalles que no esten agregados.
            if (!listaIdsDetalles.contains(subsidioAnular.getIdDetalleSubsidioAsignado())) {
                listaIdsDetalles.add(subsidioAnular.getIdDetalleSubsidioAsignado());
            }
        }

        List<DetalleSubsidioAsignadoDTO> listaDetalles = obtenerDetallesDTOPorIDs(listaIdsDetalles);

        //a cada detalle se le pone el motivo de anulación
        for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {

            for (SubsidiosConsultaAnularPerdidaDerechoDTO subsidioAnular : listaSubsidiosAnular) {

                if (detalle.getIdDetalleSubsidioAsignado().equals(subsidioAnular.getIdDetalleSubsidioAsignado())) {

                    //se actualiza el motivo de anulación y sus detalles adicionales a cada registro de detalle de subsidio asignado.
                    detalle.setMotivoAnulacion(subsidioAnular.getMotivoAnulacion());
                    detalle.setDetalleAnulacion(subsidioAnular.getDetalleAnulacion());
                }
            }
        }

        abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(listaIdsCuentas);
        abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(listaDetalles);

        //se ejecuta la anulación sin reemplazo 
        return anularSubsidiosMonetariosSinReemplazo(abonoAnuladoDetalleAnuladoDTO, userDTO);
    }

    /**
     * Metodo encargado de llamar el cliente ConsultarEmpresa del microservicio
     * EmpresaService y buscar la empresa que se quiere.
     *
     * @param tipoIdentificacion <code>TipoIdentificacionEnum</code> Tipo de
     * identificación asociada a la empresa.
     * @param numeroIdentificacion <code>String</code> Número de identificación
     * asociado a la empresa
     * @return <code>List<EmpresaModeloDTO></code> Lista con la información de
     * empresas
     */
    private List<EmpresaModeloDTO> consultarEmpresaDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarEmpresa empresa = new ConsultarEmpresa(numeroIdentificacion, tipoIdentificacion, null);
        empresa.execute();
        return empresa.getResult();
    }

    /**
     * Metodo encargado de llamar el cliente
     * ConsultarConvenioTerceroPagadorPorIdEmpresa del microservicio
     * PagosSubsidioMonetarioService para consultar si una empresa esta
     * relacionada con un convenio del tercero pagador por medio del
     * identificador de la empresa.
     *
     * @param idEmpresa <code>Long</code> Identificador principal de la empresa
     * @return
     */
    private ConvenioTercerPagadorDTO consultarConvenioTerceroPagadorEmpresaId(Long idEmpresa) {
        ConsultarConvenioTerceroPagadorPorIdEmpresa convenio = new ConsultarConvenioTerceroPagadorPorIdEmpresa(idEmpresa);
        convenio.execute();
        return convenio.getResult();
    }

    /**
     * Método encargado de llamar el servicio del microservicio Usuarios que
     * permite validar las credenciales del usuario.
     *
     * @param userName <code>String</code> Nombre del usuario que se validara.
     * @param password <code>String</code> Constraseña asociada al usuario a
     * validar.
     * @return True si las credenciales del usuario son validas; False de lo
     * contrario.
     */
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

    /**
     * Método encargado de llamar al microservicio de pagos para consultar el
     * saldo de subsidio monetario que tiene un administrador de la cuenta.
     *
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code> tipo de
     * identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code> numero de identificación del
     * administrador del subsidio
     * @param user <code>String</code> Nombre del usuario que realiza la
     * consulta del saldo
     * @return Map que contiene el identificador de respuesta del registro de la
     * operación, el nombre del administrador del subsidio y el saldo que tiene
     * en ese medio de pago.
     */
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

    /**
     * Método encargado de llamar el microservicio de pagos para realizar la
     * solicitud de retiro del subsidio por parte del administrador de la
     * cuenta.
     *
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code> tipo de
     * identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code> numero de identificación del
     * administrador del subsidio
     * @param valorSolicitado <code>BigDecimal</code> valor que se solicita
     * retirar del saldo actual.
     * @param fecha <code>Long</code> fecha en la cual ocurre la transacción de
     * retiro
     * @param usuario <code>String</code> usuario que realiza la transacción de
     * retiro.
     * @param idTransaccionTercerPagador <code>String</code> identificador de
     * transacción del tercero pagador.
     * @param departamento <code>String</code> código del DANE del departamento.
     * @param municipio <code>String</code> código del DANE del municipio.
     * @param user <code>String</code> Nombre del usuario que realiza la
     * consulta del saldo
     * @param saldoActualSubsidio <code>BigDecimal</code> saldo actual del
     * subsidio (valor entregado por el servicio:
     * consultarSaldoSubsidioTransaccion)
     * @param idPuntoCobro <code>String</code> identificador punto de cobro
     * @return Map que contiene el identificador de respuesta del registro de la
     * operación y un booleano.
     */
    private Map<String, String> solicitarRetiroSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, String usuario, Long fecha, String idTransaccionTercerPagador, String departamento,
            String municipio, String user, BigDecimal saldoActualSubsidio, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroSubsidioTransaccion(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        SolicitarRetiroSubsidio retiroSubsidio = null;
        try {
            retiroSubsidio = new SolicitarRetiroSubsidio(saldoActualSubsidio, departamento, valorSolicitado, municipio, numeroIdAdmin, user,
                    TipoMedioDePagoEnum.EFECTIVO, fecha, idTransaccionTercerPagador, usuario, idPuntoCobro, tipoIdAdmin);
            retiroSubsidio.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return retiroSubsidio.getResult();
    }

    /**
     * Método encargado de llamar al microservicio de pagos para confirmar el
     * valor entregado generado por el retiro del subsidio, en un canal de pago
     * determinado
     *
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code> tipo de
     * identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code> numero de identificación del
     * administrador del subsidio
     * @param valorSolicitado <code>BigDecimal</code> valor que se solicita
     * retirar del saldo actual.
     * @param valorEntregado <code>BigDecimal</code> valor que fue entregado en
     * la solicitud de retiro previa
     * @param fecha <code>Long</code> fecha en la cual ocurre la transacción de
     * retiro
     * @param idTransaccionTercerPagador <code>String</code> identificador de
     * transacción del tercero pagador.
     * @param usuario <code>String</code> usuario que realiza la transacción de
     * retiro.
     * @param user <code>String</code> Nombre del usuario que realiza la
     * consulta del saldo
     * @return map que contiene el identificador de respuesta del registro de la
     * operación y un booleano.
     */
    private Map<String, String> confirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario,
            String user, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidio(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConfirmarValorEntregadoSubsidio confirmacion = null;
        try {
            System.out.println("****** SE HACE RETIRO PARCIAL *****");
            confirmacion = new ConfirmarValorEntregadoSubsidio(valorSolicitado, numeroIdAdmin, user, fecha, idTransaccionTercerPagador,
                    usuario, idPuntoCobro, tipoIdAdmin, valorEntregado, "true");
            confirmacion.execute();
        } catch (ErrorExcepcion ex) {
            throw ex;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return confirmacion.getResult();
    }

    /**
     * Método encargado de llamar al microservicio de pagos para confirmar el
     * valor entregado generado por el retiro del subsidio, en un canal de pago
     * determinado
     *
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code> tipo de
     * identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code> numero de identificación del
     * administrador del subsidio
     * @param valorSolicitado <code>BigDecimal</code> valor que se solicita
     * retirar del saldo actual.
     * @param valorEntregado <code>BigDecimal</code> valor que fue entregado en
     * la solicitud de retiro previa
     * @param fecha <code>Long</code> fecha en la cual ocurre la transacción de
     * retiro
     * @param idTransaccionTercerPagador <code>String</code> identificador de
     * transacción del tercero pagador.
     * @param usuario <code>String</code> usuario que realiza la transacción de
     * retiro.
     * @return map que contiene el identificador de respuesta del registro de la
     * operación y un booleano.
     */
    private Map<String, String> confirmarValorEntregadoSubsidioCasoB(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal valorSolicitado, BigDecimal valorEntregado, Long fecha, String idTransaccionTercerPagador, String usuario,
            String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.confirmarValorEntregadoSubsidio(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConfirmarValorEntregadoSubsidioCasoB confirmacion = null;
        try {
            confirmacion = new ConfirmarValorEntregadoSubsidioCasoB(valorSolicitado, numeroIdAdmin, fecha, idTransaccionTercerPagador,
                    usuario, idPuntoCobro, tipoIdAdmin, valorEntregado);
            confirmacion.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return confirmacion.getResult();
    }

    /**
     * Método encargado de llamar el microservicio de pagos para realizar la
     * solicitud de retiro y confirmación del valor entregado en una misma
     * transacción en linea.
     *
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code> tipo de
     * identificación que tiene el administrador del subsidio
     * @param numeroIdAdmin <code>String</code> numero de identificación del
     * administrador del subsidio
     * @param saldoActualSubsidio <code>BigDecimal</code> valor del subsidio que
     * tiene actualmente el administrador
     * @param valorSolicitado <code>BigDecimal</code> valor solicitado por el
     * administrador.
     * @param fecha <code>Long</code> fecha en la cual ocurre la transacción de
     * retiro
     * @param idTransaccionTercerPagador <code>String</code> identificador de
     * transacción del tercero pagador.
     * @param departamento <code>String</code> código del DANE del departamento.
     * @param municipio <code>String</code> código del DANE del municipio.
     * @param usuario <code>String</code> usuario que realiza la transacción de
     * retiro.
     * @param user <code>String</code> Nombre del usuario que realiza la
     * consulta del saldo
     * @return map que contiene el identificador de respuesta del registro de la
     * operación y un booleano.
     */
    private Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin,
            BigDecimal saldoActualSubsidio, BigDecimal valorSolicitado, Long fecha, String idTransaccionTercerPagador, String departamento,
            String municipio, String usuario, String user, String idPuntoCobro) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum,String,BigDecimal,BigDecimal,Long,String,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        SolicitarRetiroConfirmarValorEntregadoSubsidio solicitudRetiroConfirmacion = null;
        try {
            solicitudRetiroConfirmacion = new SolicitarRetiroConfirmarValorEntregadoSubsidio(departamento, valorSolicitado, 
                        idTransaccionTercerPagador, saldoActualSubsidio, municipio, numeroIdAdmin, user, 
                        TipoMedioDePagoEnum.EFECTIVO, Boolean.TRUE.toString(), Boolean.FALSE, fecha, usuario, idPuntoCobro, tipoIdAdmin);

            solicitudRetiroConfirmacion.execute();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return solicitudRetiroConfirmacion.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#generarArchivoConsignacionesBancos(java.lang.String)
     */
    @Override
    public String generarArchivoConsignacionesBancos(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarArchivoConsignacionesBancos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String comilla = "\"";
        Boolean indicadorRegistro = Boolean.FALSE;
        try {
            //En caso de que se tenga registrada la información y ya se haya generado el archivo de consignaciones a banco, se retorna su id ECM
            ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultarArchivosLiquidacion(numeroRadicacion);
            if (archivosLiquidacionDTO != null) {
                indicadorRegistro = Boolean.TRUE;
                if (archivosLiquidacionDTO.getIdentificadorECMConsignacionesBancos() != null) {
                    return comilla + archivosLiquidacionDTO.getIdentificadorECMConsignacionesBancos() + comilla;
                }
            }

            InformacionArchivoDTO archivoConsignaciones = generarResultadoArchivoConsignacionesBancos(numeroRadicacion);
            if (archivoConsignaciones != null) {
                archivoConsignaciones = almacenarArchivoLiquidacion(archivoConsignaciones);
                //En caso de que el registro de identificadores no exista
                if (!indicadorRegistro) {
                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);

                    archivosLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();
                    archivosLiquidacionDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                }
                archivosLiquidacionDTO.setIdentificadorECMConsignacionesBancos(archivoConsignaciones.getIdentificadorDocumento());
                gestionarArchivosLiquidacion(archivosLiquidacionDTO);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return comilla + archivoConsignaciones.getIdentificadorDocumento() + comilla;
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método que permite obtener el archivo de consignaciones de bancos para
     * una liquidación
     *
     * @param numeroRadicacion valor del número de radicación
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO generarResultadoArchivoConsignacionesBancos(String numeroRadicacion) {
        GenerarArchivoResultadoConsignacionesBancos archivoConsignacionesBancos = new GenerarArchivoResultadoConsignacionesBancos(
                numeroRadicacion);
        archivoConsignacionesBancos.execute();
        return archivoConsignacionesBancos.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#generarArchivoPagosJudiciales(java.lang.String)
     */
    @Override
    public String generarArchivoPagosJudiciales(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarArchivoPagosJudiciales(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String comilla = "\"";
        Boolean indicadorRegistro = Boolean.FALSE;
        try {
            //En caso de que se tenga registrada la información y ya se haya generado el archivo de pagos judiciales, se retorna su id ECM
            ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultarArchivosLiquidacion(numeroRadicacion);
            if (archivosLiquidacionDTO != null) {
                indicadorRegistro = Boolean.TRUE;
                if (archivosLiquidacionDTO.getIdentificadorECMPagosJudiciales() != null) {
                    return comilla + archivosLiquidacionDTO.getIdentificadorECMPagosJudiciales() + comilla;
                }
            }

            InformacionArchivoDTO archivoPagosJudiciales = generarResultadoArchivoPagosJudiciales(numeroRadicacion);
            if (archivoPagosJudiciales != null) {
                archivoPagosJudiciales = almacenarArchivoLiquidacion(archivoPagosJudiciales);
                //En caso de que el registro de identificadores no exista
                if (!indicadorRegistro) {
                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);

                    archivosLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();
                    archivosLiquidacionDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                }
                archivosLiquidacionDTO.setIdentificadorECMPagosJudiciales(archivoPagosJudiciales.getIdentificadorDocumento());
                gestionarArchivosLiquidacion(archivosLiquidacionDTO);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return comilla + archivoPagosJudiciales.getIdentificadorDocumento() + comilla;
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#aprobarSolicitudAnulacionSubsidioCobrado(java.lang.String,
     * java.lang.String,
     * com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudAnulacionSubsidioCobradoDTO aprobarSolicitudAnulacionSubsidioCobrado(String numeroSolicitudAnulacion, String idTarea,
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.aprobarSolicitudAnulacionSubsidioCobrado( String , String ,SolicitudAnulacionSubsidioCobradoDTO, UserDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitud = null;
        Long idCuentaAdministradorSubsidio = null;
        List<Long> listaIdsCuentasAdmonSubsidios = null;
        List<Long> listaIdsNuevosAbonos = null;
        Map<String, Object> parametrosTarea = null;
        List<AbonosAnulacionSubsidioCobradoDTO> lstAbonosAnulacionSubsidioCobradoDTO = null;
        List<DetalleSubsidioAsignadoDTO> lstDetalleSubsidioAsignadoResultService = null;
        List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO = null;
        SolicitudModeloDTO solicitudModeloDTO = null;
        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = null;
        boolean success = false;
        //variable que tiene los medios de pagos EFECTIVOS que reemplazarán los de TRANFERENCIA asociados a los abonos.
        Map<Long, MedioDePagoModeloDTO> mediosDePagosPorCuenta = new HashMap<>();

        try {
            if (solicitudAnulacionSubsidioCobradoDTO.getIdSolicitudAnulacionSubsidioCobrado() != null
                    && solicitudAnulacionSubsidioCobradoDTO.getObservaciones() != null) {

                listaAnularDetallesDTO = new ArrayList<>();
                listaIdsCuentasAdmonSubsidios = new ArrayList<>();
                abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();

                lstAbonosAnulacionSubsidioCobradoDTO = solicitudAnulacionSubsidioCobradoDTO.getAbonosAnulacionSubsidioCobradoDTO();
                for (AbonosAnulacionSubsidioCobradoDTO abonoAnulacionSubsidioCobradoDTO : lstAbonosAnulacionSubsidioCobradoDTO) {

                    idCuentaAdministradorSubsidio = abonoAnulacionSubsidioCobradoDTO.getIdCuentaAdministradorSubsidio();
                    listaIdsCuentasAdmonSubsidios.add(idCuentaAdministradorSubsidio);

                    logger.info("idCuentaAdministradorSubsidio: " + idCuentaAdministradorSubsidio);
                    //se obtienen los derechos asignados asociados a cada registro de abono 
                    //para poder realizar la anulacion con reemplazo por llamado al servicio
                    lstDetalleSubsidioAsignadoResultService = obtenerDetallesSubsidiosAsignadosAsociadosAbono(
                            idCuentaAdministradorSubsidio);

                    for(DetalleSubsidioAsignadoDTO detalles : lstDetalleSubsidioAsignadoResultService){
                        logger.info("Identificador detalle subsidio asignado: " + detalles.getIdDetalleSubsidioAsignado());
                    }

                    logger.info("Medio de pago modelo: " + abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo());
                    //si ingresa a la condición es porque la cuenta tiene asociado el medio de pago TRANSFERENCIA
                    //y se cambiara por el medio de pago EFECTIVO
                    if (abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo() != null) {
                        logger.info("Entra if (abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo() != null)");
                        //si el medio de pago es EFECTIVO y el id es nulo, es porque el medio de pago EFECTIVO para ese administrador
                        //no existe y se procede a crearlo.
                        if (abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo().getIdMedioDePago() == null) {
                            Long idMedioDePagoEfectivo = registrarMedioDePago(abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo());
                            abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo().setIdMedioDePago(idMedioDePagoEfectivo);
                        }

                        if (mediosDePagosPorCuenta.isEmpty()) {
                            mediosDePagosPorCuenta.put(idCuentaAdministradorSubsidio, abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo());
                        } else {
                            //se evalua si son cuentas diferentes
                            if (!mediosDePagosPorCuenta.containsKey(idCuentaAdministradorSubsidio)) {
                                mediosDePagosPorCuenta.put(idCuentaAdministradorSubsidio, abonoAnulacionSubsidioCobradoDTO.getMedioDePagoModelo());
                            }
                        }
                    }

                    if (lstDetalleSubsidioAsignadoResultService != null && !lstDetalleSubsidioAsignadoResultService.isEmpty()) {
                        listaAnularDetallesDTO.addAll(lstDetalleSubsidioAsignadoResultService);
                    } else {
                        throw new ParametroInvalidoExcepcion(
                                " No se encontro derechos asignados al registro de abono solicitado: " + idCuentaAdministradorSubsidio);
                    }
                }
                abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(listaIdsCuentasAdmonSubsidios);
                abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(listaAnularDetallesDTO);

                //Se ejecuta Anulacion de Subsidio Monetario Con Reemplazo para los abonos relacionados en la solicitud de anulacion de subsidio cobrado
                listaIdsNuevosAbonos = ejecutarAnulacionSubsidioMonetarioConReemplazo(abonoAnuladoDetalleAnuladoDTO, mediosDePagosPorCuenta);
                logger.info("Finaliza ejecutarAnulacionSubsidioMonetarioConReemplazo(abonoAnuladoDetalleAnuladoDTO, mediosDePagosPorCuenta)");
                if (listaIdsNuevosAbonos != null && !listaIdsNuevosAbonos.isEmpty()) {
                    logger.info("Entra if (listaIdsNuevosAbonos != null && !listaIdsNuevosAbonos.isEmpty())");
                    //Se aprueba la solicitud de anulacion de subsidio cobrado
                    success = actualizarSolicitudAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobradoDTO,
                            EstadoSolicitudAnulacionSubsidioCobradoEnum.ANULACION_EJECUTADA);

                    if (success) {
                        parametrosTarea = new HashMap<>();
                        //se termina la tarea BPM asociada a la solicitud de anulacion de subsidio monetario cobrado
                        terminarTarea(idTarea, parametrosTarea);

                        //Se actualiza la solicitud global por aprobación de la anulación de subsidio cobrado
                        idSolicitud = solicitudAnulacionSubsidioCobradoDTO.getIdSolicitud();
                        solicitudModeloDTO = new SolicitudModeloDTO();
                        solicitudModeloDTO.setIdSolicitud(idSolicitud);
                        solicitudModeloDTO.setResultadoProceso(ResultadoProcesoEnum.APROBADA);
                        success = actualizarSolicitudGlobalAnulacionSubsidioCobrado(solicitudModeloDTO);
                        if (success) {
                            solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.TRUE);
                            //se realiza la dispersión
                            dispersarAbonosOrigenAnulacion(listaIdsNuevosAbonos);
                        } else {
                            solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                            solicitudAnulacionSubsidioCobradoDTO.setCausaError(
                                    PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ACTUALIZACION_SOLICTUD_NO_EXITOSO);
                        }
                    } else {
                        solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                        solicitudAnulacionSubsidioCobradoDTO
                                .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ACTUALIZACION_SOLICITUD_ANULACION);
                    }
                } else {
                    solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                    solicitudAnulacionSubsidioCobradoDTO
                            .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_TRANSACCION_ANULACION_NO_REALIZADA);
                }
            } else {
                solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                solicitudAnulacionSubsidioCobradoDTO
                        .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_FALTAN_PARAMETROS);
            }
        } catch (Exception e) {
            logger.error(firmaMetodo + ":: Ocurrió un error en el procesamiento de la anulación de subsidio monetario cobrado: ", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#rechazarSolicitudAnulacionSubsidioCobrado(java.lang.String,
     * java.lang.String,
     * com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public SolicitudAnulacionSubsidioCobradoDTO rechazarSolicitudAnulacionSubsidioCobrado(String numeroSolicitudAnulacion, String idTarea,
            SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO, UserDTO userDTO) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.rechazarSolicitudAnulacionSubsidioCobrado( String , String ,SolicitudAnulacionSubsidioCobradoDTO, UserDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Map<String, Object> parametrosTarea = null;
        Long idSolicitud = null;
        SolicitudModeloDTO solicitudModeloDTO = null;
        boolean success = false;

        try {
            if (solicitudAnulacionSubsidioCobradoDTO.getIdSolicitudAnulacionSubsidioCobrado() != null
                    && solicitudAnulacionSubsidioCobradoDTO.getObservaciones() != null) {

                //Se rechaza la solicitud de anulacion de subsidio cobrado
                success = actualizarSolicitudAnulacionSubsidioCobrado(solicitudAnulacionSubsidioCobradoDTO,
                        EstadoSolicitudAnulacionSubsidioCobradoEnum.ANULACION_RECHAZADA);

                if (success) {
                    parametrosTarea = new HashMap<>();
                    //se termina la tarea BPM asociada a la solicitud de anulacion de subsidio monetario cobrado
                    terminarTarea(idTarea, parametrosTarea);

                    //Se actualiza la solicitud global por rechazo de la anulación de subsidio cobrado
                    idSolicitud = solicitudAnulacionSubsidioCobradoDTO.getIdSolicitud();
                    solicitudModeloDTO = new SolicitudModeloDTO();
                    solicitudModeloDTO.setIdSolicitud(idSolicitud);
                    solicitudModeloDTO.setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
                    success = actualizarSolicitudGlobalAnulacionSubsidioCobrado(solicitudModeloDTO);
                    if (success) {
                        solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.TRUE);
                    } else {
                        solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                        solicitudAnulacionSubsidioCobradoDTO
                                .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ACTUALIZACION_SOLICTUD_NO_EXITOSO);
                    }
                } else {
                    solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                    solicitudAnulacionSubsidioCobradoDTO
                            .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_ACTUALIZACION_SOLICITUD_ANULACION);
                }
            } else {
                solicitudAnulacionSubsidioCobradoDTO.setRegistroExitoso(Boolean.FALSE);
                solicitudAnulacionSubsidioCobradoDTO
                        .setCausaError(PagosSubsidioMonetarioCompositeConstants.ERROR_MESSAGE_FALTAN_PARAMETROS);
            }
        } catch (Exception e) {
            logger.error(
                    firmaMetodo
                    + ":: Ocurrió un error en el procesamiento de rechazo de la solicitud anulación de subsidio monetario cobrado: ",
                    e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * Método que permite obtener el archivo de consignaciones de bancos por
     * concepto de pagos judiciales para una liquidación
     *
     * @param numeroRadicacion valor del número de radicación
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO generarResultadoArchivoPagosJudiciales(String numeroRadicacion) {
        GenerarArchivoResultadoPagosJudiciales archivoPagosJudiciales = new GenerarArchivoResultadoPagosJudiciales(numeroRadicacion);
        archivoPagosJudiciales.execute();
        return archivoPagosJudiciales.getResult();
    }

    /**
     * Método que permite consultar la información referente a los
     * identificadores de los archvivos generados para una liquidación
     *
     * @param numeroRadicacion valor del número de radicación
     * @return DTO con la información de los archivos
     */
    private ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(String numeroRadicacion) {
        ConsultarArchivosLiquidacion archivosLiquidacion = new ConsultarArchivosLiquidacion(numeroRadicacion);
        archivosLiquidacion.execute();
        return archivosLiquidacion.getResult();
    }

    /**
     * Método que se encarga de almacenar el archivo de liquidacion en el ECM
     *
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivoLiquidacion(InformacionArchivoDTO informacionArchivo) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();
    }

    /**
     * Método que permite consultar el id de solicitud de liquidacion
     * correspondiente al radicado
     *
     * @param numeroRadicado valor del radicado
     * @return identificador de la solicitud
     */
    private Long consultarIdSolicitudLiquidacion(String numeroRadicado) {
        ConsultarIdSolicitud consultarSolicitud = new ConsultarIdSolicitud(numeroRadicado);
        consultarSolicitud.execute();
        return consultarSolicitud.getResult();
    }

    /**
     * Método que permite realizar la gestión para el registro de
     * identificadores de los archivos para una liquidación
     *
     * @param archivoLiquidacionDTO DTO con la información de los archivos a
     * gestionar
     * @return identificador del registro de los archivos
     */
    private Long gestionarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO) {
        GestionarArchivosLiquidacion gestionarArchivos = new GestionarArchivosLiquidacion(archivoLiquidacionDTO);
        gestionarArchivos.execute();
        return gestionarArchivos.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#generarArchivoDescuentosPorEntidad(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public String generarArchivoDescuentosPorEntidad(String numeroRadicacion, Long idEntidadDescuento) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarArchivoPagosJudiciales(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        logger.info("**LOGGER numeroRadicacion: " + numeroRadicacion + " idEntidadDescuento: " + idEntidadDescuento);
        final String comilla = "\"";
        String codigoIdentificacionECMSalida = obtenerArchivoSalidaDescuento(numeroRadicacion, idEntidadDescuento);
        logger.info("**LOGGER codigoIdentificacionECMSalida: " + codigoIdentificacionECMSalida);
        if (codigoIdentificacionECMSalida != null && !codigoIdentificacionECMSalida.isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return comilla + codigoIdentificacionECMSalida + comilla;
        } else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método que permite obtener la información de trazabilidad de los archivos
     * de descuento para una solicitud de liquidación
     *
     * @param numeroRadicacion valor del número de radicación
     * @return información de trazabilidad
     */
    private String obtenerArchivoSalidaDescuento(String numeroRadicacion, Long idEntidadDescuento) {
        ObtenerArchivosSalidaDescuentos archivoSalida = new ObtenerArchivosSalidaDescuentos(numeroRadicacion, idEntidadDescuento);
        archivoSalida.execute();
        return archivoSalida.getResult();
    }

    /**
     * Método que se encarga de orquestar el proceso de dispersión de pagos con
     * origen en la anulación de un pago provio, como producto de un cambio en
     * el medio de pago de la cuenta del administrador de subsidio
     *
     * @author rlopez
     *
     * @param identificadoresCuentasAdministradoresSubsidio
     * <code>List<Long></code> lista de identificadores de las nuevas cuentas
     * relacionadas a los cambios de medio de pago
     */
    private void dispersarAbonosOrigenAnulacion(List<Long> identificadoresCuentasAdministradoresSubsidio) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.dispersarAbonosOrigenAnulacion(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (!identificadoresCuentasAdministradoresSubsidio.isEmpty()) {
            dispersarPagosEstadoEnviadoOrigenAnulacionH(identificadoresCuentasAdministradoresSubsidio);
            List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
                    identificadoresCuentasAdministradoresSubsidio);

            if (!cuentasMedioTarjeta.isEmpty()) {

//                final List<Long> pagosTarjetaNoExitosos = dispersarPagosMedioTarjetaAnibolOrigenAnulacion(cuentasMedioTarjeta);
//                Predicate<Long> predicado = n -> pagosTarjetaNoExitosos.contains(n);
//                identificadoresCuentasAdministradoresSubsidio.removeIf(predicado);
            }
            if (!identificadoresCuentasAdministradoresSubsidio.isEmpty()) {
                dispersarPagosEstadoAplicadoOrigenAnulacion(identificadoresCuentasAdministradoresSubsidio);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void dispersarAbonosOrigenCambioMedioPago(List<Long> identificadoresCuentasAdministradoresSubsidio) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.dispersarAbonosOrigenAnulacionCambioMedioPago(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (!identificadoresCuentasAdministradoresSubsidio.isEmpty()) {
            dispersarPagosEstadoEnviadoOrigenAnulacionH(identificadoresCuentasAdministradoresSubsidio);
            List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
                    identificadoresCuentasAdministradoresSubsidio);

            if (!cuentasMedioTarjeta.isEmpty()) {

                String idProcesoAnibol = dispersarPagosOrigenCambioMedioPagoAnibol(cuentasMedioTarjeta);

                List<Long> listaIdsCuentaAdminSubsidio = new ArrayList<>();

                for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {
                    listaIdsCuentaAdminSubsidio.add(cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio());
                }

                registrarSolicitudAnibol(idProcesoAnibol, listaIdsCuentaAdminSubsidio);

                identificadoresCuentasAdministradoresSubsidio.removeAll(listaIdsCuentaAdminSubsidio);
            }
            if (!identificadoresCuentasAdministradoresSubsidio.isEmpty()) {
                dispersarPagosEstadoAplicadoOrigenAnulacion(identificadoresCuentasAdministradoresSubsidio);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void registrarSolicitudAnibol(String idProcesoAnibol, List<Long> listaIdsCuentaAdminSubsidio) {

        Gson gson = new GsonBuilder().create();

        RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
        reAnibolModeloDTO.setFechaHoraRegistro(new Date());
        reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.DISPERSION_CAMBIO_MEDIO_PAGO);
        reAnibolModeloDTO.setParametrosIN(gson.toJson(listaIdsCuentaAdminSubsidio));
        reAnibolModeloDTO.setIdProceso(idProcesoAnibol);
        reAnibolModeloDTO.setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.EN_ESPERA);

        RegistrarSolicitudAnibol registroSolicitud = new RegistrarSolicitudAnibol(reAnibolModeloDTO);
        registroSolicitud.execute();
        registroSolicitud.getResult();
    }

    /**
     * Método que se encarga de gestionar (consultar estado tarjeta, abonar y
     * registrar transacciones fallidas) la dispersión de pagos al medio tarjeta
     * con origen de cambio medio de pago
     *
     * @author squintero
     *
     * @param cuentasMedioTarjeta información de las cuentas asociadas al medio
     * de pago tarjeta
     * @return lista de identificadores de las cuentas con dispersión exitosa
     */
    private String dispersarPagosOrigenCambioMedioPagoAnibol(List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta) {

        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaActiva = new ArrayList<>();
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<>();
        SaldoTarjetaDTO saldoTarjetaDTO;
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {

            String tipoIdentificacionAnibol
                    = com.asopagos.clienteanibol.enums.TipoIdentificacionEnum
                            .valueOf(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name())
                            .getTipoIdentificacion();

            String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
            TarjetaDTO tarjeta = null;
			if(conexionAnibol.equals("TRUE")){
            ConsultarTarjetaActiva consultaTarjetaActiva = new ConsultarTarjetaActiva(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio(), tipoIdentificacionAnibol);
            consultaTarjetaActiva.execute();
            tarjeta = consultaTarjetaActiva.getResult();
			}

            if (tarjeta != null && tarjeta.getNumeroTarjeta() != null && tarjeta.getNumeroTarjeta().equals(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio())) {
                cuentasTarjetaActiva.add(cuentaAdministradorSubsidioDTO);
                saldoTarjetaDTO = new SaldoTarjetaDTO();
                saldoTarjetaDTO.setTipoIdentificacion(tipoIdentificacionAnibol);
                saldoTarjetaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
                saldoTarjetaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
                saldoTarjetaDTO.setSaldo(String.format("%.2f", cuentaAdministradorSubsidioDTO.getValorRealTransaccion()));
                logger.debug("Tarjeta para abonar: " + saldoTarjetaDTO.toString());
                saldosTarjetaDTO.add(saldoTarjetaDTO);
            } else {
                cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
            }
        }

        String idProceso = null;

        if (!saldosTarjetaDTO.isEmpty()) {
            try {
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                ResultadoAnibolDTO resultadoAnibolDTO = null;
                if(conexionAnibol.equals("TRUE")){
                AbonarSaldoTarjetas abonarSaldoTarjetas = new AbonarSaldoTarjetas(saldosTarjetaDTO);
                abonarSaldoTarjetas.execute();
                resultadoAnibolDTO = abonarSaldoTarjetas.getResult();
                }
                if (resultadoAnibolDTO.isExitoso()) {
                    idProceso = resultadoAnibolDTO.getIdProceso();
                } else {
                    cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
                }
            } catch (TechnicalException e) {
                logger.error("No se pudo realizar la conexión con Anibol", e);
                cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
            }
        }

        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
        }

        return idProceso;
    }

    /**
     * Método que permite realizar la actualización de las nuevas cuentas al
     * estado ENVIADO tras una operación de anulación
     *
     * @author rlopez
     *
     * @param identificadoresCuentas <code>List<Long></code> lista de
     * identificadores de las nuevas cuentas relacionadas a los cambios de medio
     * de pago
     */
    private void dispersarPagosEstadoEnviadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        DispersarPagosEstadoEnviadoOrigenAnulacion dispersarPagos = new DispersarPagosEstadoEnviadoOrigenAnulacion(identificadoresCuentas);
        dispersarPagos.execute();
    }

    /**
     * Método que permite realizar la actualización de las nuevas cuentas al
     * estado ENVIADO tras una operación de anulación
     *
     * @author rlopez
     *
     * @param identificadoresCuentas <code>List<Long></code> lista de
     * identificadores de las nuevas cuentas relacionadas a los cambios de medio
     * de pago
     */
    private void dispersarPagosEstadoEnviadoOrigenAnulacionH(List<Long> identificadoresCuentas) {
        DispersarPagosEstadoEnviadoOrigenAnulacionH dispersarPagos = new DispersarPagosEstadoEnviadoOrigenAnulacionH(identificadoresCuentas);
        dispersarPagos.execute();
    }

    /**
     * Método que permite obtener las cuentas asociadas al medio de pago tarjeta
     * dentro del conjunto de anulación
     *
     * @author rlopez
     *
     * @param identificadoresCuentas <code>List<Long></code> lista de
     * identificadores de las nuevas cuentas relacionadas a los cambios de medio
     * de pago
     *
     * @return información de las cuentas de administrador
     */
    private List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
            List<Long> identificadoresCuentas) {
        ConsultarCuentasAdministradorMedioTarjetaOrigenAnulacion consultar = new ConsultarCuentasAdministradorMedioTarjetaOrigenAnulacion(
                identificadoresCuentas);
        consultar.execute();
        return consultar.getResult();
    }

    /**
     * Método que permite realizar la acutalización de las nuevas cuentas al
     * estado APLICADO tras una operación de anulación
     *
     * @author rlopez
     *
     * @param abonosExitosos <code>List<Long></code> Lista de identificadores de
     * las cuentas asociadas a abonos exitosos al medio tarjeta
     */
    private void dispersarPagosEstadoAplicadoOrigenAnulacion(List<Long> identificadoresCuentas) {
        DispersarPagosEstadoAplicadoOrigenAnulacion dispersarPagos = new DispersarPagosEstadoAplicadoOrigenAnulacion(
                identificadoresCuentas);
        dispersarPagos.execute();
    }

    /**
     * Método que permite realizar la dispersión a los medios de pago tarjeta,
     * haciendo uso de los servicios expuestos por anibol
     *
     * @author rlopez
     *
     * @param cuentasMedioTarjeta
     * <code>List<CuentaAdministradorSubsidioDTO></code> Información de las
     * cuentas asociadas al medio de pago tarjeta
     *
     * @return lista de identificadores de las cuentas con dispersión no exitosa
     */
//    private List<Long> dispersarPagosMedioTarjetaAnibolOrigenAnulacion(List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta) {
//        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarArchivoPagosJudiciales(String)";
//        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
//
//        //Se crea la lista de DTO´s para enviar la servicio de anibol que permite consultar el estado de la tarjeta
//        Map<Long, Integer> posicionesCuentas = new HashMap<>();
//        Integer indicador = 0;
//
//        String idCajaCompensacion = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString();
//
//        List<TarjetaDTO> consultasTarjetaDTO = new ArrayList<>();
//        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {
//            if (!cuentaAdministradorSubsidioDTO.getValorRealTransaccion().equals(0)) {
//                TarjetaDTO consultaTarjetaDTO = new ConsultaTarjetaDTO();
//
//                consultaTarjetaDTO.setIdProceso(cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio().toString());
//                consultaTarjetaDTO.setIdEntidad(idCajaCompensacion);
//                consultaTarjetaDTO.setTipoIdentificacion(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name());
//                consultaTarjetaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
//                consultaTarjetaDTO.setTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
//
//                consultasTarjetaDTO.add(consultaTarjetaDTO);
//
//                posicionesCuentas.put(cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio(), indicador++);
//            }
//        }
//        //Se comprueba el estado de las tarjetas mediante el servicio expuesto por Anibol
//        List<EstadoTarjeta> estadosTarjetas = consultarEstadoTarjeta(consultasTarjetaDTO);
//
//        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
//        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaActiva = new ArrayList<>();
//        for (EstadoTarjeta estadoTarjeta : estadosTarjetas) {
//            if (estadoTarjeta.getEsOperacionExitosa().equals("OK") && estadoTarjeta.getMensajeRespuesta() != null
//                    && estadoTarjeta.getMensajeRespuesta().getMensajeOK() != null) {
//                String indicadorEstadoTarjeta = estadoTarjeta.getMensajeRespuesta().getMensajeOK().getEstado();
//                if (indicadorEstadoTarjeta.equals(EstadoTarjetaEnum.ACTIVA.name())) {
//                    //Se evalua el caso en el que la tarjeta está activa y se añade al arreglo correspondiente
//                    cuentasTarjetaActiva.add(cuentasMedioTarjeta.get(posicionesCuentas.get(Long.parseLong(estadoTarjeta.getIdProceso()))));
//                    continue;
//                }
//            }
//            cuentasTarjetaInactivaError.add(cuentasMedioTarjeta.get(posicionesCuentas.get(Long.parseLong(estadoTarjeta.getIdProceso()))));
//        }
//        
//        StringBuilder salida = new StringBuilder();
//        Gson gson = new GsonBuilder().create();
//        Long idRegistroSolicitudAnibol = null;
//        //Se crea la lista de DTO´s para enviar al servicio de anibol que permite registrar novedades monetarias
//        List<SaldoTarjetaDTO> novedadesMonetariasDTO = new ArrayList<>();
//        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaActiva) {
//            SaldoTarjetaDTO novedadMonetariaDTO = new SaldoTarjetaDTO();
//
//            novedadMonetariaDTO.setTipoIdentificacion(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name());
//            novedadMonetariaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
//            novedadMonetariaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
//            
//            List<SaldoTarjetaDTO> lista = new ArrayList<>();
//            lista.add(novedadMonetariaDTO);
//
//            //se realiza el registro de solicitud de ANIBOL
//            RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
//            reAnibolModeloDTO.setFechaHoraRegistro(new Date());
//            reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.DISPERSION);
//            reAnibolModeloDTO.setParametrosIN(salida.append(gson.toJson(novedadMonetariaDTO)).toString());
//
//            try {
//                idRegistroSolicitudAnibol = crearRegistroSolicitudAnibol(reAnibolModeloDTO);
//            } catch (Exception e) {
//                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//            }
//        }
//
//        //Se realizan los abonos a las tarjetas por medio del servicio novedad monetaria, y en caso de error se registra como transaccion fallida
//        if (!novedadesMonetariasDTO.isEmpty()) {
//            ResultadoAnibolDTO datoNovedadMonetaria = novedadMonetaria(novedadesMonetariasDTO);
////            if (!datoNovedadMonetaria.getExitoso()) {
////                cuentasTarjetaInactivaError
////                    .add(cuentasMedioTarjeta.get(posicionesCuentas.get(Long.parseLong(datoNovedadMonetaria.getIdProceso()))));
////            }
//            //se actualiza el registro de solicitud de anibol
//            //actualizarSolicitudRegistroAnibol(idRegistroSolicitudAnibol, salida.append(gson.toJson(datosNovedadMonetaria)).toString());
//        }
//
//        List<Long> identificadoresAbonosNoExitosos = new ArrayList<>();
//
//        //Se registran las transacciones fallidas para el caso de las tarjetas inactivas o por errores en los abonos
//        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
//            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
//            identificadoresAbonosNoExitosos.add(cuentaAdministradorSubsidioDTO.getIdCuentaAdministradorSubsidio());
//        }
//
//        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//        return identificadoresAbonosNoExitosos;
//    }
    /**
     * Método que se encarga de invocar el cliente de anibol para realizar la
     * consulta del estado de las tarjetas
     *
     * @author rlopez
     *
     * @param consultasTarjetaDTO <code>List<ConsultaTarjetaDTO></code> lista de
     * DTO´s con la información de la tarjeta
     *
     * @return información del estado de la tarjeta
     */
//    private List<EstadoTarjeta> consultarEstadoTarjeta(List<ConsultaTarjetaDTO> consultasTarjetaDTO) {
//        ConsultarEstadoTarjeta consultaTarjetas = new ConsultarEstadoTarjeta(consultasTarjetaDTO);
//        consultaTarjetas.execute();
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(consultaTarjetas.getResult(), new TypeToken<List<EstadoTarjeta>>() {
//        }.getType());
//    }
    /**
     * Método que se encarga de invocar el cliente de anibol para realizar las
     * novedades monetaria por abono de tarjeta
     *
     * @author rlopez
     *
     * @param novedadesMonetariasDTO <code>List<NovedadMonetariaDTO></code>
     * lista de DTO´s con la información de las tarjetas
     *
     * @return información de la novedad monetaria
     */
    private ResultadoAnibolDTO novedadMonetaria(List<SaldoTarjetaDTO> novedadesMonetariasDTO) {
        String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                if(conexionAnibol.equals("TRUE")){
                AbonarSaldoTarjetas novedadMonetaria = new AbonarSaldoTarjetas(novedadesMonetariasDTO);
                novedadMonetaria.execute();
                return novedadMonetaria.getResult();
                }
                return null;
    }

    /**
     * Método que permite realizar el registro de una transacción fallida por
     * concepto de abono al medio tarjeta
     *
     * @author rlopez
     *
     * @param cuentaAdministrador
     * <code>CuentaAdministradorSubsidioDTO<NovedadMonetariaDTO></code>
     * información de la cuenta de administrador para pago al medio tarjeta
     */
    private void registrarTransaccionFallida(CuentaAdministradorSubsidioDTO cuentaAdministrador) {
        TransaccionFallidaDTO transaccionFallida = new TransaccionFallidaDTO();

        transaccionFallida.setFechaHoraRegistro(new Date());
        transaccionFallida.setCanal(CanalRecepcionEnum.WEB);
        transaccionFallida.setEstadoResolucion(EstadoTransaccionFallidaEnum.PENDIENTE);
        transaccionFallida.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
        transaccionFallida.setIdCuentaAdmonSubsidio(cuentaAdministrador.getIdCuentaAdministradorSubsidio());

        GestionarTransaccionesFallidas gestionar = new GestionarTransaccionesFallidas(transaccionFallida);
        gestionar.execute();
    }

    /**
     * Método que permite realizar el registro de una transacción fallida por
     * concepto de abono al medio tarjeta
     *
     * @author Squintero
     *
     * @param cuentaAdministrador
     * <code>CuentaAdministradorSubsidioDTO<NovedadMonetariaDTO></code>
     * información de la cuenta de administrador para pago al medio tarjeta
     */
    private void registrarTransaccionFallida(Long idCuentaAdministradorSubsidio) {
        TransaccionFallidaDTO transaccionFallida = new TransaccionFallidaDTO();

        transaccionFallida.setFechaHoraRegistro(new Date());
        transaccionFallida.setCanal(CanalRecepcionEnum.PRESENCIAL_INT);
        transaccionFallida.setEstadoResolucion(EstadoTransaccionFallidaEnum.PENDIENTE);
        transaccionFallida.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
        transaccionFallida.setIdCuentaAdmonSubsidio(idCuentaAdministradorSubsidio);

        GestionarTransaccionesFallidas gestionar = new GestionarTransaccionesFallidas(transaccionFallida);
        gestionar.execute();
    }

    /**
     * <b>Descripción:</b>Método compartido para realizar la nulacion de
     * subsidio monetario con reemplazo directo y por llamado desde composición
     * <b>Módulo:</b> Asopagos - HU-31-220, HU-31-227<br/>
     *
     * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel
     * Sosorio</a>
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param abonoAnuladoDetalleAnuladoDTO
     * <code>AbonoAnuladoDetalleAnuladoDTO</code> Contiene la informacion de los
     * abonos para realizar la(s) anulación de subsidio cobrado
     * @param mediosDePagosPorCuenta
     * <code>Map<Long, MedioDePagoModeloDTO></code> variable que tiene los
     * medios de pagos EFECTIVOS que reemplazarán los de TRANFERENCIA asociados
     * a los abonos.
     *
     * @return <code>List<Long></code> La lista con los identificadores de los
     * nuevos abonos por la transaccion de anulación realizada
     */
    private List<Long> ejecutarAnulacionSubsidioMonetarioConReemplazo(AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO,
            Map<Long, MedioDePagoModeloDTO> mediosDePagosPorCuenta) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.ejecutarAnulacionSubsidioMonetarioConReemplazo( AbonoAnuladoDetalleAnuladoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> lstIdCuentaAdministradorSubsidio = new ArrayList<>();
        List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = null;

        for (Long idCuentaAdmonSubsidio : abonoAnuladoDetalleAnuladoDTO.getListaIdsCuentasAdmonSubsidios()) {

            listaDetallesDTOAnular = new ArrayList<>();
            //se asocia cada detalle con su respectivo abono
            for (DetalleSubsidioAsignadoDTO detalle : abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO()) {

                if (detalle.getIdCuentaAdministradorSubsidio().longValue() == idCuentaAdmonSubsidio.longValue()) {
                    detalle.setFechaTransaccionRetiro(null);
                    detalle.setUsuarioTransaccionRetiro(null);
                    listaDetallesDTOAnular.add(detalle);
                }
            }

            if (!listaDetallesDTOAnular.isEmpty()) {

                CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO = obtenerCuentaAdministradorSubsidioDTO(
                        idCuentaAdmonSubsidio);
                cuentaAdministradorSubsidioOrigDTO.setFechaHoraCreacionRegistro(new Date());
                boolean validacionCuenta = true;
                //si el medio de pago es tarjeta , a diferencia a la anulación en efectivo,
                //se verifica la validez de la tarjeta por medio de ANIBOL
//                if (TipoMedioDePagoEnum.TARJETA.equals(cuentaAdministradorSubsidioOrigDTO.getMedioDePago())) {
//
//                    double valorDevolucion = 0;
//
//                    for (DetalleSubsidioAsignadoDTO detalle : abonoAnuladoDetalleAnuladoDTO.getListaAnularDetallesDTO()) {
//                        //se suman los valores totales de los detalles que seran el valor de devolución
//                        valorDevolucion += detalle.getValorTotal().doubleValue();
//                    }

//                    NovedadMonetariaDTO novedadMonetariaDTO = new NovedadMonetariaDTO();
//                    novedadMonetariaDTO.setTipoProceso("M");
//                    novedadMonetariaDTO.setNumRegistros("1");
//                    novedadMonetariaDTO.setFechaHoraSolicitud(new Date().toString());
//                    novedadMonetariaDTO.setTipoIdentificacion(cuentaAdministradorSubsidioOrigDTO.getTipoIdAdminSubsidio().toString());
//                    novedadMonetariaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioOrigDTO.getNumeroIdAdminSubsidio());
//                    novedadMonetariaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioOrigDTO.getNumeroTarjetaAdminSubsidio());
//                    novedadMonetariaDTO.setTipoNovedadMonet(TipoNovedadMonetariaEnum.DESCUENTO);
//                    novedadMonetariaDTO.setValorNovedad(BigDecimal.valueOf(valorDevolucion));
//                    novedadMonetariaDTO.setCodigoBolsillo("01");
//
//                    List<NovedadMonetariaDTO> lista = new ArrayList<>();
//                    lista.add(novedadMonetariaDTO);
//                    StringBuilder salida = new StringBuilder();
//                    Gson gson = new GsonBuilder().create();
//
//                    RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
//                    reAnibolModeloDTO.setFechaHoraRegistro(new Date());
//                    reAnibolModeloDTO.setTipoOperacionAnibol(TipoOperacionAnibolEnum.ANULACION);
//                    reAnibolModeloDTO.setParametrosIN(salida.append(gson.toJson(novedadMonetariaDTO)).toString());
//                    Long idRegistroSolicitudAnibol = null;
//                    try {
//                        idRegistroSolicitudAnibol = crearRegistroSolicitudAnibol(reAnibolModeloDTO);
//                    } catch (Exception e) {
//                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
                //TODO: ESPERAR RESPUESTA DEL CLIENTE PARA SABER QUE ES EL ID PROCESO
//                    novedadMonetariaDTO.setIdProceso(idRegistroSolicitudAnibol.toString());
//
//                    //se hace llamado al servicio de novedad monetaria de ANIBOL.
//                    List<DatosNovedadMonetaria> listaResultados = null;
//                    try {
//                        listaResultados = novedadMonetaria(lista);
//                    } catch (Exception e) {
//                        //si ocurre un error en el proceso, se gestiona a las transacciones fallidas
//                        //TODO: ¿Se debe crear el tipo de transacción fallida transacción no exitosas con tarjeta?
//                        //TODO: Debido a que en la HU no se especifica que se envía a la bandeja de la HU 200, sino a una
//                        //bandeja de transacciones no exitosas de tarjetas
//                        registrarTransaccionFallida(cuentaAdministradorSubsidioOrigDTO);
//                        return lstIdCuentaAdministradorSubsidio;
//                    }
//                    for (DatosNovedadMonetaria novedad : listaResultados) {
//
//                        if (!novedad.getSalida().equals("0")) {
//                            //SI LA VALIDACIÓN CON ANIBOL NO ES EXITOSA SE DEVUELVE UN FALSE PARE
//                            validacionCuenta = false;
//                            break;
//                        }
//                    }
//                    //TODO: se obtiene el primer elemento de la lista porque solo se devuelve un objeto de la validación con ANIBOL... por el momento.
//                    actualizarSolicitudRegistroAnibol(idRegistroSolicitudAnibol,
//                            salida.append(gson.toJson(listaResultados.get(0))).toString());
//               }
                SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioOrigDTO,
                        listaDetallesDTOAnular);
                //validación usada para la HU-31-219 cambio medio de pago
                //tambien se valida que no sea null y que el map tenga ese abono relacionado con medio de pago TRANSFERENCIA (HU-31-227)
                if (mediosDePagosPorCuenta != null && mediosDePagosPorCuenta.containsKey(idCuentaAdmonSubsidio)) {
                    //se setea el medio de pago EFECTIVO por el cual se va a realizar el reemplazo
                    subsidioAnulacionDTO.setMedioDePagoModelo(mediosDePagosPorCuenta.get(idCuentaAdmonSubsidio));
                }
                //se realiza el proceso de anulación con reemplazo del detalle con su respectivo abono
                Long nuevoAbono = anularSubsidioMonetarioConReemplazo(subsidioAnulacionDTO, validacionCuenta);
                if (nuevoAbono != null) {
                    lstIdCuentaAdministradorSubsidio.add(nuevoAbono);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdCuentaAdministradorSubsidio;
    }

    /**
     * <b>Descripción:</b>Método que permite terminar una tarea de por BPM
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param idTarea <code>String</code> El identificador de la tarea a
     * finalizar
     *
     * @param params <code>Map<String, Object> </code> Los parametros
     * solicitados por la tarea
     */
    private void terminarTarea(String idTarea, Map<String, Object> params) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.terminarTarea ( String, Map<String, Object> )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        TerminarTarea terminarTarea = null;
        try {
            terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
        } catch (Exception e) {
            logger.error(firmaMetodo + ":: No se logro finalizar la tarea por BPM", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     *
     * <b>Descripción:</b>Método que permite actualizar una Solicitud de
     * Anulacion de Subsidio Cobrado y su estado
     * <b>Módulo:</b> Asopagos - HU-31-227<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cediel</a>
     *
     * @param solicitudAnulacionSubsidioCobradoDTO
     * @return
     */
    private Boolean actualizarSolicitudAnulacionSubsidioCobrado(SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO,
            EstadoSolicitudAnulacionSubsidioCobradoEnum estadoSolicitud) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.actualizarSolicitudAnulacionSubsidioCobrado( SolicitudAnulacionSubsidioCobradoDTO )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean success = Boolean.FALSE;
        SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO = null;

        try {
            solicitudAnulacionSubsidioCobradoModeloDTO = new SolicitudAnulacionSubsidioCobradoModeloDTO();
            solicitudAnulacionSubsidioCobradoModeloDTO
                    .setIdSolicitudAnulacionCobradoSubsidio(solicitudAnulacionSubsidioCobradoDTO.getIdSolicitudAnulacionSubsidioCobrado());
            solicitudAnulacionSubsidioCobradoModeloDTO.setObservaciones(solicitudAnulacionSubsidioCobradoDTO.getObservaciones());
            solicitudAnulacionSubsidioCobradoModeloDTO.setEstadoSolicitud(estadoSolicitud);

            ActualizarSolicitudAnulacionSubsidioCobrado actualizarSolicitudAnulacionSubsidioCobradoService = new ActualizarSolicitudAnulacionSubsidioCobrado(
                    solicitudAnulacionSubsidioCobradoModeloDTO);
            actualizarSolicitudAnulacionSubsidioCobradoService.execute();
            success = actualizarSolicitudAnulacionSubsidioCobradoService.getResult();

        } catch (Exception e) {
            logger.error(firmaMetodo + ":: No se logro realizar la actualizacion a la solicitud de anulacion de subsidio cobrado", e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;
    }

    /**
     * Método que se encarga de llamar al cliente de ... del microservicio
     * PagosSubsidioMonetario, el cual obtiene el archivo de consumo de tarjetas
     * que se encuentra en el FTP designado.
     *
     * @return DTO con la información del archivo de consumo.
     */
    private List<InformacionArchivoDTO> obtenerArchivoTarjetasAnibol() {
        ObtenerArchivoConsumoTarjetaANIBOLFTP archivoConsumoFTP = new ObtenerArchivoConsumoTarjetaANIBOLFTP();
        archivoConsumoFTP.execute();
        return archivoConsumoFTP.getResult();
    }

    /**
     * Método encargado de validar el nombre del archivo de retiro de las
     * tarjetas por parte de ANIBOL. Se valida que cumpla con la estructura del
     * nombre definida en el documento 'Servicios Web ANIBOL para CORE v2.6.4' y
     * que el archivo no haya sido validado previamente.
     *
     * @param fileName <code>String</code> Nombre del archivo cargado por el FTP
     * @return True si el nombre del archivo cumple con la esctructura indicada
     * y no ha sido procesado con anterioridad.
     */
    private Boolean validarNombreArchivoConsumoTarjetaAnibol(String fileName, Boolean cargaManual) {
        //try {
        ArchivoConsumosAnibolModeloDTO archivoConsumoDTO = new ArchivoConsumosAnibolModeloDTO();

        if (!cargaManual) {
            archivoConsumoDTO.setTipoCargue(TipoCargueArchivoConsumoAnibolEnum.AUTOMATICO);
        } else {
            archivoConsumoDTO.setTipoCargue(TipoCargueArchivoConsumoAnibolEnum.MANUAL);
        }
        archivoConsumoDTO.setNombreArchivo(fileName);
        //se valida si el nombre cumple con la estructura indicada en el documento; tambíen se valida que el nombre del archivo
        //no exista, si existe y esta procesado, ingresa a la condición.
        if (!fileName.matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_CONSUMO_TARJETA_ANIBOL)) {

            if (fileName.length() != 14) {
                archivoConsumoDTO
                        .setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.LONGITUD_NOMBRE_ARCHIVO_INVALIDA);
            } else if (!fileName.substring(0, 1).matches("T")) {
                archivoConsumoDTO
                        .setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.PREFIJO_FIJO_NOMBRE_ARCHIVO_NO_VALIDO);
            } else if (!fileName.substring(1, 7).matches(ExpresionesRegularesConstants.VALOR_NUMERICO)) {
                archivoConsumoDTO.setTipoInconsistenciaArchivo(
                        TipoInconsistenciaArchivoConsumoAnibolEnum.PREFIJO_ID_CAJA_NOMBRE_ARCHIVO_NO_VALIDO);
            } else if (!fileName.substring(7, 8).matches("[1-9]||[A-B-C]")) {
                archivoConsumoDTO.setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.MES_NOMBRE_ARCHIVO_NO_VALIDO);
            } else if (!fileName.substring(8, 10).matches("([0-2][1-9]|[1-3][0-1])")) {
                archivoConsumoDTO.setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.DIA_NOMBRE_ARCHIVO_NO_VALIDO);
            } else if (!fileName.substring(10, 14).matches(".TXT|.txt")) {
                archivoConsumoDTO.setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.EXTENSION_ARCHIVO_INVALIDA);
            }

            archivoConsumoDTO.setArchivoNotificado((byte) 1);
            archivoConsumoDTO.setEstadoArchivo(EstadoArchivoConsumoAnibolEnum.ANULADO);
            archivoConsumoDTO.setResultadoValidacionEstructura(ResultadoValidacionArchivoConsumoAnibolEnum.NO_APROBADO);
            //se agrega el registro del archivo
            registrarArchivoConsumoTarjetaANIBOL(archivoConsumoDTO);
            return false;
        } else if (buscarNombreArchivoTarjetaConsumoANIBOL(fileName)) {
            archivoConsumoDTO.setTipoInconsistenciaArchivo(TipoInconsistenciaArchivoConsumoAnibolEnum.ARCHIVO_PREVIAMENTE_PROCESADO);
            archivoConsumoDTO.setArchivoNotificado((byte) 1);
            archivoConsumoDTO.setEstadoArchivo(EstadoArchivoConsumoAnibolEnum.ANULADO);
            archivoConsumoDTO.setResultadoValidacionEstructura(ResultadoValidacionArchivoConsumoAnibolEnum.NO_APROBADO);
            //se agrega el registro del archivo
            registrarArchivoConsumoTarjetaANIBOL(archivoConsumoDTO);
            return false;
        }
        /*
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
         */
        //si retorna true es porque el archivo no esta procesado y el nombre es valido
        return true;
    }

    /**
     * Método encargado de llamar al cliente
     * ValidarCargarArchivoRetiroTarjetaAnibol del micro servicio
     * PagosSubsidioMonetario, encargado de realizar la validación del archivo y
     * si es valida, realizar el respestivo retiro de cada uno de los registros
     * de tarjeta frente a los asociados en las cuentas de administradores de
     * subsidios.
     *
     * @param archivoConsumoTarjeta <code>InformacionArchivoDTO</code> DTO con
     * la información almacenada del archivo de consumo de retiros de tarjeta
     * entregado por ANIBOL.
     * @return DTO con la información del resultado de la validación y proceso
     * de retiro.
     */
    private ResultadoValidacionArchivoRetiroDTO validarAlmacenarArchivoDeConsumoTarjeta(InformacionArchivoDTO archivoConsumoTarjeta) {
        ValidarCargarArchivoRetiroTarjetaAnibol archivoRetiroTarjetaAnibol = new ValidarCargarArchivoRetiroTarjetaAnibol(
                archivoConsumoTarjeta);
        archivoRetiroTarjetaAnibol.execute();
        return archivoRetiroTarjetaAnibol.getResult();
    }

    /**
     * Método que se encarga de almacenar el archivo en el Enterprise Content
     * Management
     *
     * @param informacionArchivo <code>InformacionArchivoDTO</code> DTO con la
     * información del archivo para ser almacenado en el ECM
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO informacionArchivo) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();
    }

    /**
     * Método que se encarga de buscar el nombre del archivo de consumo que se
     * requiere validar, guardar y ejecutar las operaciones pertinentes. Si
     * existe y se encuentra procesado retorna un true; si no retorna un false
     *
     * @param nombreArchivo <code>String</code> Nombre del archivo que se
     * requiere validar si existe.
     * @return TRUE: si se encuentra el archivo y esta procesado, FALSE de lo
     * contrario.
     */
    private Boolean buscarNombreArchivoTarjetaConsumoANIBOL(String nombreArchivo) {
        BuscarNombreArchivoConsumoTarjetaANIBOL validarNombreArchivo = new BuscarNombreArchivoConsumoTarjetaANIBOL(nombreArchivo);
        validarNombreArchivo.execute();
        return validarNombreArchivo.getResult();
    }

    /**
     * Método que se encarga de buscar el nombre del archivo de consumo que se
     * requiere validar, guardar y ejecutar las operaciones pertinentes. Si
     * existe y se encuentra procesado retorna un true; si no retorna un false
     *
     * @param nombreArchivo <code>String</code> Nombre del archivo que se
     * requiere validar si existe.
     * @return TRUE: si se encuentra el archivo y esta procesado, FALSE de lo
     * contrario.
     */
    private Boolean consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(String nombreArchivo) {
        ConsultarNombreArchivoConsumoTerceroPagadorEfectivoNombre validarNombreArchivo = new ConsultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(nombreArchivo);
        validarNombreArchivo.execute();
        return validarNombreArchivo.getResult();
    }

    /**
     * Método encargado de registrar el archivo de consumo de tarjeta que
     * proviene desde ANIBOL por medio del cliente que se llama desde el micro
     * servicio PagosSubsidioMonetario
     *
     * @param archivoConsumosAnibolModeloDTO
     * <code>ArchivoConsumosAnibolModeloDTO</code> DTO que contiene la
     * información global del archivo de consumo
     */
    private void registrarArchivoConsumoTarjetaANIBOL(ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO) {
        GuardarRegistroArchivoConsumosAnibol archivoConsumo = new GuardarRegistroArchivoConsumosAnibol(archivoConsumosAnibolModeloDTO);
        archivoConsumo.execute();
        idArchivoAnibol = archivoConsumo.getResult();
    }

    /**
     * Método encargado de registrar el archivo de consumo de tarjeta que
     * proviene desde ANIBOL por medio del cliente que se llama desde el micro
     * servicio PagosSubsidioMonetario
     *
     * @param archivoConsumosAnibolModeloDTO
     * <code>ArchivoConsumosAnibolModeloDTO</code> DTO que contiene la
     * información global del archivo de consumo
     */
    private Long registrarArchivoConsumoTerceroPagadorEfectivo(ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumosDTO) {
        GuardarRegistroArchivoConsumosTerceroPagadorEfectivo archivoConsumo = new GuardarRegistroArchivoConsumosTerceroPagadorEfectivo(archivoConsumosDTO);
        archivoConsumo.execute();
        idArchivoAnibol = archivoConsumo.getResult();
        return idArchivoAnibol;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoResultadoPagoTarjeta(java.lang.String)
     */
    @Override
    public DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoResultadoPagoTarjeta(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoResultadoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoPagoTarjetaDTO dispersionTarjetaDTO = consultarDispersionMontoLiquidadoPagoTarjeta(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER);
        return dispersionTarjetaDTO;
    }

    /**
     * Método que permite consultar la dispersión al medio de pago tarjeta en un
     * proceso de liquidación
     *
     * @param numeroRadicacion <code>String</code> Valor del número de
     * radicación
     *
     * @return DTO con la información de la dispersión
     */
    private DispersionResultadoPagoTarjetaDTO consultarDispersionMontoLiquidadoPagoTarjeta(String numeroRadicacion) {
        ConsultarDispersionMontoLiquidadoPagoTarjeta dispersion = new ConsultarDispersionMontoLiquidadoPagoTarjeta(numeroRadicacion);
        dispersion.execute();
        return dispersion.getResult();
    }

    /**
     * Método que permite consultar la información de las personas dentro de un
     * proceso de liquidación
     *
     * @param numeroRadicacion <code>String</code> Valor del número de
     * radicación
     *
     * @param identificadoresCondiciones <code>List<Long></code> Lista de
     * identificadores de condición
     *
     * @return Información de la condición de las personas
     */
    private Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        logger.debug("Inicio de metodo consultarCondicionesPersonas");
        if (identificadoresCondiciones.size() < 1000) {
            ConsultarCondicionesPersonas consultar = new ConsultarCondicionesPersonas(numeroRadicacion, identificadoresCondiciones);
            consultar.execute();
            return consultar.getResult();
        }
        // Añadir a un mapa de a 1000 registros
        Map<String, CondicionPersonaLiquidacionDTO> mapaFinal = new HashMap<String, CondicionPersonaLiquidacionDTO>();
        List<Long> subListaIdentificacion;
        int inicio = 0;
        int fin = 0;
        for (int i = 0 ; i < identificadoresCondiciones.size() ; i += 1000) {
            inicio = i;
            fin = i+1000 > identificadoresCondiciones.size() ? identificadoresCondiciones.size() : i+1000; 
            subListaIdentificacion = identificadoresCondiciones.subList(inicio, fin);
            ConsultarCondicionesPersonas consultar = new ConsultarCondicionesPersonas(numeroRadicacion, subListaIdentificacion);
            consultar.execute();
            mapaFinal.putAll(consultar.getResult());
        }
        return mapaFinal;
    }

    /**
     * Método que se encarga de emparejar los datos de las condiciones de
     * personas en la liquidación
     *
     * @param dispersionTarjetaDTO
     * <code>DispersionResultadoPagoTarjetaDTO</code> DTO con la información de
     * la dispersión
     *
     * @param condicionesPersonas
     * <code>Map<Long,CondicionPersonaLiquidacionDTO></code> Información de
     * condiciones en la liquidación
     *
     * @return DTO con la información de dispersión emparejada
     */
    private DispersionResultadoPagoTarjetaDTO emparejarDatosCondicionesPagoTarjeta(DispersionResultadoPagoTarjetaDTO dispersionTarjetaDTO,
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        List<DetalleResultadoPagoTarjetaDTO> detallesResultadoPagoTarjetaDTO = dispersionTarjetaDTO.getListaDetalleAbonoTarjeta();

        Map<String, CondicionPersonaLiquidacionDTO> condiciones = convertirMapa(condicionesPersonas);

        for (DetalleResultadoPagoTarjetaDTO detalleResultadoPagoTarjetaDTO : detallesResultadoPagoTarjetaDTO) {
            if (condiciones.containsKey(detalleResultadoPagoTarjetaDTO.getIdCondicionAdministrador().toString())) {
                //Define la información del administrador al momento de la liquidación
                CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                        .get(detalleResultadoPagoTarjetaDTO.getIdCondicionAdministrador().toString());

                detalleResultadoPagoTarjetaDTO.setTipoIdentificacionAdministradorSubsidio(condicionPersonaDTO.getTipoIdentificacion());
                detalleResultadoPagoTarjetaDTO.setNumeroIdentificacionAdministradorSubsidio(condicionPersonaDTO.getNumeroIdentificacion());
                detalleResultadoPagoTarjetaDTO.setNombreAdministradorSubsidio(condicionPersonaDTO.getRazonSocial());
            }
            if (condiciones.containsKey(detalleResultadoPagoTarjetaDTO.getIdCondicionTrabajador().toString())) {
                //Define la información del  trabajador al momento de l aliquidación
                CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                        .get(detalleResultadoPagoTarjetaDTO.getIdCondicionTrabajador().toString());

                detalleResultadoPagoTarjetaDTO.setTipoIndentificacionTrabajador(condicionPersonaDTO.getTipoIdentificacion());
                detalleResultadoPagoTarjetaDTO.setNumeroIdentificacionTrabajador(condicionPersonaDTO.getNumeroIdentificacion());
                detalleResultadoPagoTarjetaDTO.setNombreTrabajador(condicionPersonaDTO.getRazonSocial());
            }
        }
        dispersionTarjetaDTO.setListaDetalleAbonoTarjeta(detallesResultadoPagoTarjetaDTO);
        return dispersionTarjetaDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoPagoEfectivo(java.lang.String)
     */
    @Override
    public DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoResultadoPagoEfectivo(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoPagoEfectivoDTO dispersionEfectivoDTO = consultarDispersionMontoLiquidadoPagoEfectivo(numeroRadicacion);
        if (!dispersionEfectivoDTO.getIdentificadoresCondiciones().isEmpty()) {

            List<Long> idsTmp = new ArrayList<Long>();
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = new HashMap<String, CondicionPersonaLiquidacionDTO>();
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonasTemp;
            int cont = 0;
            for (Long id : dispersionEfectivoDTO.getIdentificadoresCondiciones()) {
                cont++;
                idsTmp.add(id);
                if (cont == 1000) {
                    condicionesPersonasTemp = consultarCondicionesPersonas(numeroRadicacion, idsTmp);
                    for (Entry<String, CondicionPersonaLiquidacionDTO> e : condicionesPersonasTemp.entrySet()) {
                        condicionesPersonas.put(e.getKey(), e.getValue());
                    }
                    idsTmp = new ArrayList<Long>();
                    cont = 0;
                }
            }

            if (cont > 0) {
                condicionesPersonasTemp = consultarCondicionesPersonas(numeroRadicacion, idsTmp);
                for (Entry<String, CondicionPersonaLiquidacionDTO> e : condicionesPersonasTemp.entrySet()) {
                    condicionesPersonas.put(e.getKey(), e.getValue());
                }
            }

            dispersionEfectivoDTO = emparejarDatosCondicionesPagoEfectivo(dispersionEfectivoDTO, condicionesPersonas);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER);
        return dispersionEfectivoDTO;
    }

    /**
     * Método que permite consultar la dispersión al medio de pago efectivo en
     * un proceso de liquidación
     *
     * @param numeroRadicacion <code>String</code> Valor del número de
     * radicación
     *
     * @return DTO con la información de la dispersión
     */
    private DispersionResultadoPagoEfectivoDTO consultarDispersionMontoLiquidadoPagoEfectivo(String numeroRadicacion) {
        ConsultarDispersionMontoLiquidadoPagoEfectivo dispersion = new ConsultarDispersionMontoLiquidadoPagoEfectivo(numeroRadicacion);
        dispersion.execute();
        return dispersion.getResult();
    }

    /**
     * Método que se encarga de emparejar los datos de las condiciones de
     * personas en la liquidación
     *
     * @param dispersionEfectivoDTO
     * <code>DispersionResultadoPagoEfectivoDTO</code> DTO con la información de
     * la dispersión
     *
     * @param condicionesPersonas
     * <code>Map<Long,CondicionPersonaLiquidacionDTO></code> Información de
     * condiciones en la liquidación
     *
     * @return DTO con la información de dispersión emparejada
     */
    private DispersionResultadoPagoEfectivoDTO emparejarDatosCondicionesPagoEfectivo(
            DispersionResultadoPagoEfectivoDTO dispersionEfectivoDTO, Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        List<DetalleResultadoPagoEfectivoDTO> detallesResultadoPagoEfectivoDTO = dispersionEfectivoDTO.getListaDetallePagoEfectivo();

        Map<String, CondicionPersonaLiquidacionDTO> condiciones = convertirMapa(condicionesPersonas);

        for (DetalleResultadoPagoEfectivoDTO detalleResultadoPagoEfectivoDTO : detallesResultadoPagoEfectivoDTO) {
            if (condiciones.containsKey(detalleResultadoPagoEfectivoDTO.getIdCondicionAdministrador().toString())) {
                //Define la información del administrador al momento de la liquidación
                CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                        .get(detalleResultadoPagoEfectivoDTO.getIdCondicionAdministrador().toString());

                detalleResultadoPagoEfectivoDTO.setTipoIdentificacionAdministradorSubsidio(condicionPersonaDTO.getTipoIdentificacion());
                detalleResultadoPagoEfectivoDTO.setNumeroIdentificacionAdministradorSubsidio(condicionPersonaDTO.getNumeroIdentificacion());
                detalleResultadoPagoEfectivoDTO.setNombreAdministradorSubsidio(condicionPersonaDTO.getRazonSocial());
            }
        }
        dispersionEfectivoDTO.setListaDetallePagoEfectivo(detallesResultadoPagoEfectivoDTO);
        return dispersionEfectivoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoPagoBanco(java.lang.String)
     */
    @Override
    public DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoResultadoPagoBanco(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoPagoBanco(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoPagoBancoDTO dispersionBancoDTO = consultarDispersionMontoLiquidadoPagoBanco(numeroRadicacion);
        Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = new LinkedHashMap<>();

        if (!dispersionBancoDTO.getIdentificadoresCondiciones().isEmpty()) {
            List<List<Long>> identificadoresPart = particionarLista(dispersionBancoDTO.getIdentificadoresCondiciones(), 1000L);

            for (List<Long> list : identificadoresPart) {
                condicionesPersonas.putAll(consultarCondicionesPersonas(numeroRadicacion, list));
            }

            dispersionBancoDTO = emparejarDatosCondicionesPagoBanco(dispersionBancoDTO, condicionesPersonas);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER);
        return dispersionBancoDTO;
    }

    /**
     * Método que permite consultar la dispersión al medio de pago banco en un
     * proceso de liquidación
     *
     * @param numeroRadicacion <code>String</code> Valor del número de
     * radicación
     *
     * @return DTO con la información de la dispersión
     */
    private DispersionResultadoPagoBancoDTO consultarDispersionMontoLiquidadoPagoBanco(String numeroRadicacion) {
        ConsultarDispersionMontoLiquidadoPagoBanco dispersion = new ConsultarDispersionMontoLiquidadoPagoBanco(numeroRadicacion);
        dispersion.execute();
        return dispersion.getResult();
    }

    /**
     * Método que se encarga de emparejar los datos de las condiciones de
     * personas en la liquidación
     *
     * @param dispersionEfectivoDTO <code>DispersionResultadoPagoBancoDTO</code>
     * DTO con la información de la dispersión
     *
     * @param condicionesPersonas
     * <code>Map<Long,CondicionPersonaLiquidacionDTO></code> Información de
     * condiciones en la liquidación
     *
     * @return DTO con la información de dispersión emparejada
     */
    private DispersionResultadoPagoBancoDTO emparejarDatosCondicionesPagoBanco(DispersionResultadoPagoBancoDTO dispersionBancoDTO,
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        //Se procesa la información de las consignaciones bancarias
        List<DispersionResultadoPagoBancoConsignacionesDTO> listaConsignaciones = dispersionBancoDTO.getLstConsignaciones();

        Map<String, CondicionPersonaLiquidacionDTO> condiciones = convertirMapa(condicionesPersonas);

        if (listaConsignaciones != null) {
            for (DispersionResultadoPagoBancoConsignacionesDTO pagoConsignacion : listaConsignaciones) {
                List<ItemResultadoPagoBancoDTO> itemsPagoConsignaciones = pagoConsignacion.getLstConsignaciones();

                for (ItemResultadoPagoBancoDTO itemResultadoPagoBancoDTO : itemsPagoConsignaciones) {
                    if (condiciones.containsKey(itemResultadoPagoBancoDTO.getIdCondicionAdministrador().toString())) {
                        CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                                .get(itemResultadoPagoBancoDTO.getIdCondicionAdministrador().toString());

                        itemResultadoPagoBancoDTO.setTipoIdentificacionAdministradorSubsidio(condicionPersonaDTO.getTipoIdentificacion());
                        itemResultadoPagoBancoDTO
                                .setNumeroIdentificacionAdministradorSubsidio(condicionPersonaDTO.getNumeroIdentificacion());
                        itemResultadoPagoBancoDTO.setNombreAdministradorSubsidio(condicionPersonaDTO.getRazonSocial());
                    }
                }
                pagoConsignacion.setLstConsignaciones(itemsPagoConsignaciones);
            }
            dispersionBancoDTO.setLstConsignaciones(listaConsignaciones);
        }

        //Se procesa la información de los pagos judiciales
        List<DispersionResultadoPagoBancoPagoJuducialDTO> listaPagosJudiciales = dispersionBancoDTO.getLstPagosJudiciales();

        if (listaPagosJudiciales != null) {
            for (DispersionResultadoPagoBancoPagoJuducialDTO pagoJudicial : listaPagosJudiciales) {
                List<ItemResultadoPagoBancoDTO> itemsPagoJudiciales = pagoJudicial.getLstConsignaciones();

                for (ItemResultadoPagoBancoDTO itemResultadoPagoBancoDTO : itemsPagoJudiciales) {
                    if (condiciones.containsKey(itemResultadoPagoBancoDTO.getIdCondicionAdministrador().toString())) {
                        CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                                .get(itemResultadoPagoBancoDTO.getIdCondicionAdministrador().toString());

                        itemResultadoPagoBancoDTO.setTipoIdentificacionAdministradorSubsidio(condicionPersonaDTO.getTipoIdentificacion());
                        itemResultadoPagoBancoDTO
                                .setNumeroIdentificacionAdministradorSubsidio(condicionPersonaDTO.getNumeroIdentificacion());
                        itemResultadoPagoBancoDTO.setNombreAdministradorSubsidio(condicionPersonaDTO.getRazonSocial());
                    }
                }
                pagoJudicial.setLstConsignaciones(itemsPagoJudiciales);
            }
            dispersionBancoDTO.setLstPagosJudiciales(listaPagosJudiciales);
        }
        return dispersionBancoDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoResultadoDescuentosPorEntidad(java.lang.String)
     */
    @Override
    public DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoResultadoDescuentosPorEntidad(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoPagoBanco(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoEntidadDescuentoDTO dispersionEntidadDescuentoDTO = consultarDispersionMontoDescuentosPorEntidad(
                numeroRadicacion);


        if (!dispersionEntidadDescuentoDTO.getIdentificadoresCondiciones().isEmpty()) {
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
                    dispersionEntidadDescuentoDTO.getIdentificadoresCondiciones());
            dispersionEntidadDescuentoDTO = emparejarDatosCondicionesEntidadesDescuento(dispersionEntidadDescuentoDTO, condicionesPersonas);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER);
        return dispersionEntidadDescuentoDTO;
    }

    /**
     * Método que permite consultar la dispersión por entidad de descuento en un
     * proceso de liquidación
     *
     * @param numeroRadicacion <code>String</code> Valor del número de
     * radicación
     *
     * @return DTO con la información de la dispersión
     */
    private DispersionResultadoEntidadDescuentoDTO consultarDispersionMontoDescuentosPorEntidad(String numeroRadicacion) {
        ConsultarDispersionMontoDescuentosPorEntidad dispersion = new ConsultarDispersionMontoDescuentosPorEntidad(numeroRadicacion);
        dispersion.execute();
        return dispersion.getResult();
    }

    /**
     * Método que se encarga de emparejar los datos de las condiciones de
     * personas en la liquidación
     *
     * @param dispersionEfectivoDTO
     * <code>DispersionResultadoEntidadDescuentoDTO</code> DTO con la
     * información de la dispersión
     *
     * @param condicionesPersonas
     * <code>Map<Long,CondicionPersonaLiquidacionDTO></code> Información de
     * condiciones en la liquidación
     *
     * @return DTO con la información de dispersión emparejada
     */
    private DispersionResultadoEntidadDescuentoDTO emparejarDatosCondicionesEntidadesDescuento(
            DispersionResultadoEntidadDescuentoDTO dispersionEntidadDescuentoDTO,
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        List<DetalleResultadoEntidadDescuentoDTO> resultadosEntidadesDTO = dispersionEntidadDescuentoDTO.getLstDescuentos();

        Map<String, CondicionPersonaLiquidacionDTO> condiciones = convertirMapa(condicionesPersonas);

        for (DetalleResultadoEntidadDescuentoDTO detalleResultadoEntidadDescuentoDTO : resultadosEntidadesDTO) {
            List<ItemResultadoEntidadDescuentoDTO> itemsResultadoEntidadDTO = detalleResultadoEntidadDescuentoDTO.getLstItemsDescuentos();

            for (ItemResultadoEntidadDescuentoDTO itemResultadoEntidadDescuentoDTO : itemsResultadoEntidadDTO) {
                if (condiciones.containsKey(itemResultadoEntidadDescuentoDTO.getIdCondicionAdministrador().toString())) {
                    CondicionPersonaLiquidacionDTO condicionPersonaDTO = condiciones
                            .get(itemResultadoEntidadDescuentoDTO.getIdCondicionAdministrador().toString());

                    itemResultadoEntidadDescuentoDTO
                            .setTipoIdentificacionAdministradorSubsidio(condicionPersonaDTO.getTipoIdentificacion());
                    itemResultadoEntidadDescuentoDTO
                            .setNumeroIdentificacionAdministradorSubsidio(condicionPersonaDTO.getNumeroIdentificacion());
                    itemResultadoEntidadDescuentoDTO.setNombreAdministradorSubsidio(condicionPersonaDTO.getRazonSocial());
                }
            }
            detalleResultadoEntidadDescuentoDTO.setLstItemsDescuentos(itemsResultadoEntidadDTO);
        }
        dispersionEntidadDescuentoDTO.setLstDescuentos(resultadosEntidadesDTO);
        return dispersionEntidadDescuentoDTO;
    }

    /**
     * <b>Descripción:</b>Método encargado de enviar Notificación con
     * Inconsistencias detectadas en el procesamiento del archivo de consumos.
     * <b>Módulo:</b> Asopagos - ANEXO-ANIBOL<br/>
     *
     * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez
     * Cedie
     *
     * @param idArchivoConsumoAnibol <code>String</code> El identificador del
     * archivo de consumos ANIBOL previamente cargado y procesado
     *
     * @return <code>Boolean</code> si el envio de la Notificación con
     * Inconsistencias fue satisfactorio o no
     */
    private Boolean enviarNotificacionConInconsistencias(String idArchivoConsumoAnibol) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.enviarNotificacionConInconsistencias(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean success = Boolean.FALSE;
        NotificacionParametrizadaDTO notificacionParametrizadaDTO = null;
        ParametrosComunicadoDTO parametrosComunicadoDTO = null;

        List<String> destinatarios = new ArrayList<>();
        String correoAdminstrador = (String) CacheManager.getParametro("CORREO_ADMINISTRADOR_ANIBOL");
        destinatarios.add(correoAdminstrador);

        //try {
        notificacionParametrizadaDTO = new NotificacionParametrizadaDTO();
        parametrosComunicadoDTO = new ParametrosComunicadoDTO();
        parametrosComunicadoDTO.setIdArchivoConsumosAnibol(idArchivoConsumoAnibol);
        notificacionParametrizadaDTO.setParametros(parametrosComunicadoDTO);
        notificacionParametrizadaDTO.setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum.COM_PAG_SUB_INC_ARC_CON);
        notificacionParametrizadaDTO.setProcesoEvento(ProcesoEnum.PAGOS_SUBSIDIO_MONETARIO.toString());
        notificacionParametrizadaDTO.setDestinatarioTO(destinatarios);
        notificacionParametrizadaDTO.setReplantearDestinatarioTO(true);
        EnviarNotificacionComunicado enviarNotificacionComunicadoService = new EnviarNotificacionComunicado(
                notificacionParametrizadaDTO);
        enviarNotificacionComunicadoService.execute();
        success = Boolean.TRUE;
        /* 
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
         */
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;

    }

    /**
     * Método que se encarga de realizar la conversión del mapa obtenido del
     * microservicio ya que el cliente convierte el valor en un LinkedHashMap
     * con los atributos del objeto
     *
     * @param condiciones mapa de condiciones de personas
     * @return mapa con formato requerido
     */
    @SuppressWarnings("unchecked")
    private Map<String, CondicionPersonaLiquidacionDTO> convertirMapa(Map<String, CondicionPersonaLiquidacionDTO> condiciones) {
        Map<String, CondicionPersonaLiquidacionDTO> mapa = new HashMap<>();
        for (Map.Entry<String, CondicionPersonaLiquidacionDTO> entry : condiciones.entrySet()) {
            CondicionPersonaLiquidacionDTO condicion = new CondicionPersonaLiquidacionDTO();
            Object entrada = entry.getValue();
            HashMap<Object, Object> valoresEntrada = (HashMap<Object, Object>) entrada;

            condicion.setIdCondicionPersona(Long.valueOf(valoresEntrada.get("idCondicionPersona").toString()));
            condicion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(valoresEntrada.get("tipoIdentificacion").toString()));
            condicion.setNumeroIdentificacion((String) valoresEntrada.get("numeroIdentificacion"));
            condicion.setRazonSocial((String) valoresEntrada.get("razonSocial"));

            mapa.put(condicion.getIdCondicionPersona().toString(), condicion);
        }
        return mapa;
    }

    /**
     * Metodo encargado de llamar el servicio que permite realizar un registro
     * de una solicitud que se realiza desde un servicio expuesto a ANIBOL.
     *
     * @param registroSolicitudAnibolModeloDTO
     * <code>RegistroSolicitudAnibolModeloDTO</code> DTO que contiene la
     * información para realizar el registro de solicitud.
     * @return <code>Long</code> Identificador del registro de solicitud de
     * ANIBOL.
     */
    private Long crearRegistroSolicitudAnibol(RegistroSolicitudAnibolModeloDTO registroSolicitudAnibolModeloDTO) {
        RegistrarSolicitudAnibol registroSolicitud = new RegistrarSolicitudAnibol(registroSolicitudAnibolModeloDTO);
        registroSolicitud.execute();
        return registroSolicitud.getResult();
    }

    /**
     * Método encargado de actualizar el registro de solicitud de ANIBOL
     * llamando el servicio correspondiente de PagosSubsidioMonetario
     *
     * @param idRegistroSolicitudAnibol <code>Long</code> Identificador del
     * registro de solicitud de ANIBOL registrado.
     * @param parametrosOUT <code>String</code> Variable que contiene los
     * parametros de salida por parte de ANIBOl en formato Json
     */
    private void actualizarSolicitudRegistroAnibol(Long idRegistroSolicitudAnibol, String parametrosOUT) {
        ActualizarRegistroSolicitudAnibol actualizacionSolicitudAnibol = new ActualizarRegistroSolicitudAnibol(idRegistroSolicitudAnibol, parametrosOUT);
        actualizacionSolicitudAnibol.execute();
    }

    /**
     * @param medioDePagoModelo <code>MedioDePagoModeloDTO</code> DTO que
     * contiene la información necesaría para crear el tipo de medio de pago
     * @return <code>Long</code> Identificador del medio de pago creado
     */
    private Long registrarMedioDePago(MedioDePagoModeloDTO medioDePagoModelo) {
        logger.warn("entra a regstrar medio de pago: "+ medioDePagoModelo.toString());
        GuardarMedioDePago medioDePago = new GuardarMedioDePago(medioDePagoModelo);
        medioDePago.execute();
        return medioDePago.getResult().getIdMedioDePago();
    }

    private Long registrarTarjetaExpedicion(MedioDePagoModeloDTO medioDePagoModelo){
        logger.warn("entra registrarTarjetaExpedicion "+ medioDePagoModelo.toString());
        RegistrarTarjetaExpedicion registrarTarjetaService = new RegistrarTarjetaExpedicion(medioDePagoModelo);
        registrarTarjetaService.execute();
        return registrarTarjetaService.getResult().getIdMedioDePago();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionResultadoMontoLiquidacionFallecimiento(java.lang.String)
     */
    @Override
    public DispersionMontoLiquidadoFallecimientoDTO consultarDispersionResultadoMontoLiquidacionFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.enviarNotificacionConInconsistencias(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionMontoLiquidadoFallecimientoDTO dispersionDTO = consultarDispersionMontoLiquidacionFallecimiento(numeroRadicacion);
        if (dispersionDTO.getIdentificadoresCondiciones() != null && !dispersionDTO.getIdentificadoresCondiciones().isEmpty()) {
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
                    dispersionDTO.getIdentificadoresCondiciones());
            condicionesPersonas = convertirMapa(condicionesPersonas);
            dispersionDTO = emparejarCondicionesPersonasFallecimiento(dispersionDTO, condicionesPersonas);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return dispersionDTO;
    }

    /**
     * <b>Descripción:</b>Método encargado obtener los resultados de dispersión
     * para la liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicación
     * @return DTO con la información de la dispersión
     */
    private DispersionMontoLiquidadoFallecimientoDTO consultarDispersionMontoLiquidacionFallecimiento(String numeroRadicacion) {
        ConsultarDispersionMontoLiquidacionFallecimiento consultar = new ConsultarDispersionMontoLiquidacionFallecimiento(numeroRadicacion);
        consultar.execute();
        return consultar.getResult();
    }

    /**
     * Método que se encarga de emparejar los datos de resultado para las
     * personas en el proceso de liquidación de fallecimiento
     *
     * @param dispersionDTO DTO con la información de dispersión
     * @param condicionesPersonas Mapa con las condiciones de personas
     * @return DTO con la información de dispersión
     */
    private DispersionMontoLiquidadoFallecimientoDTO emparejarCondicionesPersonasFallecimiento(
            DispersionMontoLiquidadoFallecimientoDTO dispersionDTO, Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {

        List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdminTarjetaDTO = emparejarDatosPersonasFallecimiento(
                dispersionDTO.getResumenPagosTarjeta().getResultadosPorAdministrador(), condicionesPersonas);
        List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdminEfectivoDTO = emparejarDatosPersonasFallecimiento(
                dispersionDTO.getResumenPagosEfectivo().getResultadosPorAdministrador(), condicionesPersonas);
        List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadoPorAdminBancoConsignacionDTO = emparejarDatosPersonasFallecimiento(
                dispersionDTO.getResumenPagosBancoConsignacion().getResultadosPorAdministrador(), condicionesPersonas);

        List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadoPorAdminDescuentosDTO = emparejarDatosPersonasFallecimiento(
                dispersionDTO.getResumenDescuentos().getResultadosPorAdministrador(), condicionesPersonas);

        //TODO emparejar los datos de los medios de pago restantes
        dispersionDTO.getResumenPagosTarjeta().setResultadosPorAdministrador(resultadosPorAdminTarjetaDTO);
        dispersionDTO.getResumenPagosEfectivo().setResultadosPorAdministrador(resultadosPorAdminEfectivoDTO);
        dispersionDTO.getResumenPagosBancoConsignacion().setResultadosPorAdministrador(resultadoPorAdminBancoConsignacionDTO);
        dispersionDTO.getResumenDescuentos().setResultadosPorAdministrador(resultadoPorAdminDescuentosDTO);

        dispersionDTO.getResumenPagosTarjeta().setIdentificadoresCondiciones(null);
        dispersionDTO.getResumenPagosEfectivo().setIdentificadoresCondiciones(null);
        dispersionDTO.getResumenPagosBancoConsignacion().setIdentificadoresCondiciones(null);

        dispersionDTO.setIdentificadoresCondiciones(null);
        return dispersionDTO;
    }

    /**
     * Método que se encarga de emparejar los datos para las personas
     * relacionadas al medio de pago elegido.
     *
     * @param resultadosPorAdminDTO DTO con la información de resultados por
     * administrador
     * @param condicionesPersonas Información de las personas en el proceso de
     * liquidación
     * @return DTO con la información emparejada
     * @author rlopez
     */
    private List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> emparejarDatosPersonasFallecimiento(
            List<ResultadoPorAdministradorLiquidacionFallecimientoDTO> resultadosPorAdminDTO,
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        for (ResultadoPorAdministradorLiquidacionFallecimientoDTO resultadoAdminDTO : resultadosPorAdminDTO) {
            if (condicionesPersonas.containsKey(resultadoAdminDTO.getIdentificadorCondicion().toString())) {
                CondicionPersonaLiquidacionDTO condicionPersona = condicionesPersonas
                        .get(resultadoAdminDTO.getIdentificadorCondicion().toString());

                resultadoAdminDTO.setTipoIdentificacionAdministrador(condicionPersona.getTipoIdentificacion());
                resultadoAdminDTO.setNumeroIdentificacionAdministrador(condicionPersona.getNumeroIdentificacion());
                resultadoAdminDTO.setNombreAdministrador(condicionPersona.getRazonSocial());
            }
            for (ItemResultadoLiquidacionFallecimientoDTO itemBeneficiario : resultadoAdminDTO.getItemsBeneficiarios()) {
                if (condicionesPersonas.containsKey(itemBeneficiario.getIdCondicionBeneficiarioAfiliado().toString())) {
                    CondicionPersonaLiquidacionDTO condicionPersona = condicionesPersonas
                            .get(itemBeneficiario.getIdCondicionBeneficiarioAfiliado().toString());

                    itemBeneficiario.setTipoIdentificacionBeneficiarioAfiliado(condicionPersona.getTipoIdentificacion());
                    itemBeneficiario.setNumeroIdentificacionBeneficiarioAfiliado(condicionPersona.getNumeroIdentificacion());
                    itemBeneficiario.setNombreBeneficiarioAfiliado(condicionPersona.getRazonSocial());
                }
            }
        }
        return resultadosPorAdminDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionResultadoMontoLiquidadoFallecimientoPagoEfectivo(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoEfectivo(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionResultadoMontoLiquidadoFallecimientoPagoEfectivo(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //Detalles de los pagos a dispersar
        /* DispersionResultadoMedioPagoFallecimientoDTO detalleEfectivo = consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(
                numeroRadicacion, identificadorCondicion);*/
        //Detalles de los pagos a dispersar
        DispersionResultadoMedioPagoFallecimientoDTO detalleEfectivo = consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(numeroRadicacion, identificadorCondicion);

        //Si los pagos a dispersar no son 12 cuotas, se reorganizan los valores a dispersar
//        if (detalleEfectivo.getItemsDetalle().size() != 12) {     
//            //Consulta los periodos proyectados para la liquidacion
//            DetalleLiquidacionBeneficiarioFallecimientoDTO proyeccionPagosSubsidio = consultarProyeccionLiquidacionFallecimiento(numeroRadicacion,identificadorCondicion);
//            List<ItemDetalleLiquidacionBeneficiarioFallecimientoDTO> detallesProyeccion;
//            detallesProyeccion = proyeccionPagosSubsidio.getItemsDetalle();
//
//            //Se separan los pagos para compararlos con su respectivo perido
//            List<ItemDispersionResultadoMedioPagoFallecimientoDTO> itemsPagos;
//            itemsPagos = detalleEfectivo.getItemsDetalle();
//            
//            //Contador que avanza en las posiciones de la proyeccion de pagos
//            int contPosProyeccion = 0;
//            //Contador que avanza en las posiciones de los valores de los pagos
//            int contPosValores = 0;
//            //Variable que guarda los valores a dispersar en las 12 cuotas en el orden correcto
//            List<BigDecimal> nuevosValoresDispersion = new ArrayList<>();
//            
//            //Se recorren los item de pagos a organizar
//            for (ItemDispersionResultadoMedioPagoFallecimientoDTO item : itemsPagos) {
//                //Se recorren las fechas de los pagos para reorganizar los valores en las 12 cuotas
//                for (Date fecha : item.getFechasDispersion()) {
//                    //Se compara la fecha del pago con la fecha proyectada del pago 
//                    if (fecha.equals(detallesProyeccion.get(contPosProyeccion).getPeriodo())) {
//                        nuevosValoresDispersion.add(item.getValoresDispersion().get(contPosValores));
//                        contPosValores++;
//                        contPosProyeccion++;
//                    } else {
//                        nuevosValoresDispersion.add(BigDecimal.ZERO);
//                        contPosProyeccion++;
//                    }
//                }
//                item.setValoresDispersion(nuevosValoresDispersion);
//            }
//            //Si los valores a dispersar se organizaron en las 12 cuotas se actualiza el item detalle
//            if (itemsPagos.size() == 12) {
//                detalleEfectivo.setItemsDetalle(itemsPagos);
//            }
//        }
//        if (detalleEfectivo.getIdentificadoresCondiciones() != null && !detalleEfectivo.getIdentificadoresCondiciones().isEmpty()) {
//            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
//                    detalleEfectivo.getIdentificadoresCondiciones());
//            condicionesPersonas = convertirMapa(condicionesPersonas);
//            detalleEfectivo = emparejarDatosDetallePersonasFallecimiento(detalleEfectivo, condicionesPersonas);
//
//            detalleEfectivo.setIdentificadoresCondiciones(null);
//        }
        detalleEfectivo.setIdentificadoresCondiciones(null);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleEfectivo;
    }

    private DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(String numeroRadicacion, Long identificadorCondicion) {

        ConsultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas dispersionProyeccionCuotas = new ConsultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(numeroRadicacion, identificadorCondicion);
        dispersionProyeccionCuotas.execute();
        return dispersionProyeccionCuotas.getResult();
    }

    private DetalleLiquidacionBeneficiarioFallecimientoDTO consultarProyeccionLiquidacionFallecimiento(String numeroRadicacion, Long identificadorCondicion) {
        ConsultarDetalleBeneficiarioLiquidacionFallecimiento detalleBenLiquidacion = new ConsultarDetalleBeneficiarioLiquidacionFallecimiento(numeroRadicacion, identificadorCondicion);
        detalleBenLiquidacion.execute();
        return detalleBenLiquidacion.getResult();
    }

    /**
     * Método que se encarga de obtener los resultados detallados de dispersión
     * al medio de pago efectivo en una liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicado
     * @param identificadorCondicion Valor del identificador de condición para
     * el administradorconsultado
     * @return DTO con la información de dispersión
     */
    private DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(String numeroRadicacion,
            Long identificadorCondicion) {
        ConsultarDispersionMontoLiquidadoFallecimientoPagoEfectivo detalleEfectivo = new ConsultarDispersionMontoLiquidadoFallecimientoPagoEfectivo(
                numeroRadicacion, identificadorCondicion);
        detalleEfectivo.execute();
        return detalleEfectivo.getResult();
    }

    /**
     * Método que se encarga de emparejar los datos para las personas
     * relacionadas al medio de pago elegido.
     *
     * @param resultadosPorAdminDTO DTO con la información de resultados por
     * administrador
     * @param condicionesPersonas Información de las personas en el proceso de
     * liquidación
     * @return DTO con la información emparejada
     * @author rlopez
     */
    private DispersionResultadoMedioPagoFallecimientoDTO emparejarDatosDetallePersonasFallecimiento(
            DispersionResultadoMedioPagoFallecimientoDTO detalleDispersionDTO,
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas) {
        if (condicionesPersonas.containsKey(detalleDispersionDTO.getIdentificadorCondicionAdministrador().toString())) {
            CondicionPersonaLiquidacionDTO condicionPersona = condicionesPersonas
                    .get(detalleDispersionDTO.getIdentificadorCondicionAdministrador().toString());

            detalleDispersionDTO.setTipoIdentificacionAdministrador(condicionPersona.getTipoIdentificacion());
            detalleDispersionDTO.setNumeroIdentificacionAdministrador(condicionPersona.getNumeroIdentificacion());
            detalleDispersionDTO.setNombreAdministrador(condicionPersona.getRazonSocial());
        }
        for (ItemDispersionResultadoMedioPagoFallecimientoDTO itemBeneficiario : detalleDispersionDTO.getItemsDetalle()) {
            if (condicionesPersonas.containsKey(itemBeneficiario.getIdentificadorCondicion().toString())) {
                CondicionPersonaLiquidacionDTO condicionPersona = condicionesPersonas
                        .get(itemBeneficiario.getIdentificadorCondicion().toString());

                itemBeneficiario.setTipoIdentificacionBeneficiario(condicionPersona.getTipoIdentificacion());
                itemBeneficiario.setNumeroIdentificacionBeneficiario(condicionPersona.getNumeroIdentificacion());
                itemBeneficiario.setNombreBeneficiario(condicionPersona.getRazonSocial());
            }
        }
        return detalleDispersionDTO;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoTarjeta(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO detalleTarjeta = consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(numeroRadicacion, identificadorCondicion);
//        if (detalleTarjeta.getIdentificadoresCondiciones() != null && !detalleTarjeta.getIdentificadoresCondiciones().isEmpty()) {
//            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
//                    detalleTarjeta.getIdentificadoresCondiciones());
//            condicionesPersonas = convertirMapa(condicionesPersonas);
//            detalleTarjeta = emparejarDatosDetallePersonasFallecimiento(detalleTarjeta, condicionesPersonas);
//
//            
//        }
        detalleTarjeta.setIdentificadoresCondiciones(null);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleTarjeta;
    }

    /**
     * Método encargado de consultar la dispersión del monto liquidado para el
     * medio tarjeta en una liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicado
     * @param identificadorCondicion Identificador de condición del
     * administrador
     * @return DTO con la información de dispersión
     */
    private DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String numeroRadicacion,
            Long identificadorCondicion) {
        ConsultarDispersionMontoLiquidadoFallecimientoPagoTarjeta detalleTarjeta = new ConsultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(
                numeroRadicacion, identificadorCondicion);
        detalleTarjeta.execute();
        return detalleTarjeta.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoFallecimientoPagoTarjeta(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoMedioPagoFallecimientoDTO detalleBanco = consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(numeroRadicacion, identificadorCondicion);
//        if (detalleBanco.getIdentificadoresCondiciones() != null && !detalleBanco.getIdentificadoresCondiciones().isEmpty()) {
//            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
//                    detalleBanco.getIdentificadoresCondiciones());
//            condicionesPersonas = convertirMapa(condicionesPersonas);
//            detalleBanco = emparejarDatosDetallePersonasFallecimiento(detalleBanco, condicionesPersonas);
//
//            detalleBanco.setIdentificadoresCondiciones(null);
//        }
        detalleBanco.setIdentificadoresCondiciones(null);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleBanco;
    }

    /**
     * Método encargado de consultar la dispersión del monto liquidado para el
     * medio banco - consignaciones en una liquidación de fallecimiento
     *
     * @param numeroRadicacion Valor del número de radicado
     * @param identificadorCondicion Identificador de condición del
     * administrador
     * @return DTO con la información de dispersión
     */
    private DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
            String numeroRadicacion, Long identificadorCondicion) {
        ConsultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones detalleBanco = new ConsultarDispersionMontoLiquidadoFallecimientoPagoBancoConsignaciones(
                numeroRadicacion, identificadorCondicion);
        detalleBanco.execute();
        return detalleBanco.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#consultarDispersionMontoLiquidadoFallecimientoDescuentos(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public DispersionResultadoDescuentosFallecimientoDTO consultarDispersionResultadoMontoLiquidadoFallecimientoDescuentos(
            String numeroRadicacion, Long identificadorCondicion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.consultarDispersionMontoLiquidadoFallecimientoDescuentos(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        DispersionResultadoDescuentosFallecimientoDTO detalleDescuentos = consultarDispersionMontoLiquidadoFallecimientoDescuentos(
                numeroRadicacion, identificadorCondicion);
        if (detalleDescuentos.getIdentificadoresCondiciones() != null && !detalleDescuentos.getIdentificadoresCondiciones().isEmpty()) {
            Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
                    detalleDescuentos.getIdentificadoresCondiciones());
            condicionesPersonas = convertirMapa(condicionesPersonas);

            Integer indiceEmparejamiento = 0;
            for (DispersionResultadoMedioPagoFallecimientoDTO detalleDTO : detalleDescuentos.getDetallesDescuentosDTO()) {
                detalleDTO = emparejarDatosDetallePersonasFallecimiento(detalleDTO, condicionesPersonas);
                detalleDescuentos.getDetallesDescuentosDTO().set(indiceEmparejamiento++, detalleDTO);
            }
            detalleDescuentos.setIdentificadoresCondiciones(null);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return detalleDescuentos;
    }

    /**
     * Método que permite obtener los resultados de dispersión por monto
     * liquidado para un administrador
     *
     * @param numeroRadicacion Valor del número de radicación
     * @param identificadorCondicion Identificador de condición del
     * administrador
     * @return DTO con la información de dispersión
     */
    private DispersionResultadoDescuentosFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoDescuentos(String numeroRadicacion,
            Long identificadorCondicion) {
        ConsultarDispersionMontoLiquidadoFallecimientoDescuentos detalleDescuentos = new ConsultarDispersionMontoLiquidadoFallecimientoDescuentos(
                numeroRadicacion, identificadorCondicion);
        detalleDescuentos.execute();
        return detalleDescuentos.getResult();
    }

    /**
     * Método que se encarga de invocar el cliente de anibol para realizar la
     * busqueda de estado de tarjeta
     *
     * @author mosorio
     *
     * @param consultaTarjetaDTO <code>List<consultaTarjetaDTO></code> lista de
     * DTO´s con la información de las tarjetas
     *
     * @return información del estado de tarjeta
     */
//    private List<EstadoTarjeta> estadoTarjetaAnibol(List<ConsultaTarjetaDTO> consultaTarjetaDTO) {
//        ConsultarEstadoTarjeta estadoTarjeta = new ConsultarEstadoTarjeta(consultaTarjetaDTO);
//        estadoTarjeta.execute();
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(estadoTarjeta.getResult(), new TypeToken<List<EstadoTarjeta>>() {
//        }.getType());
//    }
    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#dispersarLiquidacionFallecimientoTarjeta(com.asopagos.rest.security.dto.UserDTO,
     * java.util.List)
     */
    @Override
    public Long dispersarLiquidacionFallecimientoTarjeta(UserDTO userDTO, String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroRadicado, List<Long> lstIdsCondicionesBeneficiarios) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.dispersarLiquidacionFallecimientoTarjeta(UserDTO,String,TipoIdentificacionEnum,List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> lstCuentasAsociadasDetallesProgramados = null;
        try {
            lstCuentasAsociadasDetallesProgramados = buscarCuentasPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + firmaMetodo);
        }

        CuentaAdministradorSubsidioDTO cuenta = obtenerCuentaAdministradorSubsidioDTO(lstCuentasAsociadasDetallesProgramados.get(0));
        String idCajaCompensacion = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString();

        //TODO: Actualizar el código con los nuevos servicios de ANIBOL
        Boolean esValido = Boolean.TRUE;
        //si la respuesta no es exitosa para validar que el 
        if (!esValido) {
            for (Long idCuenta : lstCuentasAsociadasDetallesProgramados) {
                CuentaAdministradorSubsidioDTO abonoFallido = obtenerCuentaAdministradorSubsidioDTO(idCuenta);
                //se registra la transacción fallida
                registrarTransaccionFallida(abonoFallido);
            }
            return 0L;
        }
        //se realiza la dispersión respectiva
        try {
            //dispersarLiquidacionFallecimientoAdmin(lstIdsCondicionesBeneficiarios);
            dispersarLiquidacionFallecimiento(userDTO, numeroRadicado, lstIdsCondicionesBeneficiarios);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + firmaMetodo);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return 1L;
    }

    /**
     * Metodo encargada de llamar el cliente de pagos para obtener los
     * identificadores de los abonos por medio de los identificadores de
     * condiciones de los beneficiarios asociados a sus respectivos detalles
     * asignados.
     *
     * @param lstIdsCondicionesBeneficiarios <code>List<Long></code> Lista de
     * los identificadores asociados a las condiciones de los beneficiarios
     * relacionados a los detalles.
     * @return lista de identificadores de abonos.
     */
    private List<Long> buscarCuentasPorIdCondicionesBeneficiarios(List<Long> lstIdsCondicionesBeneficiarios) {
        BuscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios abonos = new BuscarCuentasAdministradorSubsidioPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
        abonos.execute();
        return abonos.getResult();
    }

    private List<Long> buscarCuentasPorIdCondicionesBeneficiarios(List<Long> lstIdsCondicionesBeneficiarios, String numeroRadicado) {
        BuscarCasPorIdCondicionesBeneficiariosYsolicitud abonos = new BuscarCasPorIdCondicionesBeneficiariosYsolicitud(numeroRadicado, lstIdsCondicionesBeneficiarios);
        abonos.execute();
        return abonos.getResult();
    }

    /**
     * Método encargado de llamar el cliente de pagos para realizar la
     * dispersión de por fallecimiento de un administrador de subsidio.
     *
     * @param lstIdsCondicionesBeneficiarios <code>List<Long></code> Lista de
     * los identificadores asociados a las condiciones de los beneficiarios
     * relacionados a los detalles.
     * @return Lista con los abonos creados para realizar la aprobación de la
     * liquidación y terminar el proceso de la HU.
     */
    private List<Long> dispersarLiquidacionFallecimientoAdmin(List<Long> lstIdsCondicionesBeneficiarios) {
        DispersarLiquidacionFallecimientoPorAdmin fallecimientoPorAdmin = new DispersarLiquidacionFallecimientoPorAdmin(
                lstIdsCondicionesBeneficiarios);
        fallecimientoPorAdmin.execute();
        return fallecimientoPorAdmin.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#obtenerPagosSubsidioPendientes(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     * java.lang.String, java.lang.String)
     */
    @Override
    public List<PagoSubsidioProgramadoDTO> obtenerPagosSubsidioPendientes(TipoIdentificacionEnum tipoIdAdmin, String numeroIdAdmin, String numeroRadicacion) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.obtenerPagosSubsidioPendientes(TipoIdentificacionEnum,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        AdministradorSubsidioModeloDTO admin = null;
        try {
            admin = consultarAdministradorSubsidioGeneral(numeroIdAdmin, tipoIdAdmin);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " error al buscar el administrador del subsidio ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + firmaMetodo);
        }

        List<PagoSubsidioProgramadoDTO> lstPagosSubsidiosProgramados = null;

        //try {
        lstPagosSubsidiosProgramados = obtenerPagosPendientes(admin.getIdAdministradorSubsidio(), numeroRadicacion);
        /* } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " error al obtener los pagos de subsdios programados composite");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + firmaMetodo,e);
        }
         */

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstPagosSubsidiosProgramados;
    }

    /**
     * Metodo que llama el cliente de personaService para obtener el
     * administrador de subsidio.
     *
     * @param numeroIdAdmin <code>String</code> Número de identificación del
     * administrador del subsidio
     * @param tipoIdAdmin <code>TipoIdentificacionEnum</code>
     * @return
     */
    private AdministradorSubsidioModeloDTO consultarAdministradorSubsidioGeneral(String numeroIdAdmin, TipoIdentificacionEnum tipoIdAdmin) {
        ConsultarAdministradorSubsidioGeneral admin = new ConsultarAdministradorSubsidioGeneral(null, null, null, numeroIdAdmin, tipoIdAdmin, null);
        admin.execute();
        return admin.getResult().get(0);
    }

    /**
     * Metodo que llama el cliente de pagosSubsidioMonetario para obtener los
     * pagos de subsidios pendientes (detalles programados) que faltan por
     * programar por un administrador de subsidio.
     *
     * @param idAdmin <code>Long</code> Identificador principal del
     * administrador del subsidio
     * @param numeroRadicacion <code>String</code> Número de radicado al cual
     * pertenece los pagos programados
     * @return lista de los pagos pendientes por administrador
     */
    private List<PagoSubsidioProgramadoDTO> obtenerPagosPendientes(Long idAdmin, String numeroRadicacion) {
        ObtenerPagosSubsidio pagosPendientes = new ObtenerPagosSubsidio(idAdmin, numeroRadicacion);
        pagosPendientes.execute();
        return pagosPendientes.getResult();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#generarArchivoBancos219(java.lang.Boolean,
     * java.util.List)
     */
    @Override
    public String generarArchivoBancos219(Boolean esPagoJudicial, List<Long> lstIdCuentasBancos) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.generarArchivoBancos219(Boolean,List<Long> )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        final String comilla = "\"";
        try {

            InformacionArchivoDTO archivoConsignaciones = generarArchivoTransacciones219(esPagoJudicial, lstIdCuentasBancos);
            if (archivoConsignaciones != null) {
                archivoConsignaciones = almacenarArchivoLiquidacion(archivoConsignaciones);
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return comilla + archivoConsignaciones.getIdentificadorDocumento() + comilla;
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Metódo utilitario para obtener una lista de de datos sin repetición de
     * los mismos.
     *
     * @param keyExtractor dato que se requiere sea unico.
     * @return lista sin datos repetidos
     */
    private <T> Predicate<T> distinctByValue(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * @param esPagoJudicial
     * @param lstIdCuentasBancos
     * @return
     */
    private InformacionArchivoDTO generarArchivoTransacciones219(Boolean esPagoJudicial, List<Long> lstIdCuentasBancos) {
        GenerarArchivoCambioMedioPagoBancos resultado = new GenerarArchivoCambioMedioPagoBancos(esPagoJudicial, lstIdCuentasBancos);
        resultado.execute();
        return resultado.getResult();
    }

    /**
     * Metodo encargado de buscar los datos de la persona a partir del número y
     * tipo de identificación
     *
     * @param numeroIdentificacion <code>String</code> Número de identificación
     * de la persona
     * @param tipoIdentificacion <code>TipoIdentificacionEnum</code> Tipo de
     * identifación de la persona
     * @return DTO con la información de la persona.
     */
    private PersonaDTO consultarPersonaConvenio(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        BuscarPersonas persona = new BuscarPersonas(null, null, null, null, null, null, numeroIdentificacion, tipoIdentificacion, null);
        persona.execute();
        return persona.getResult() != null && !persona.getResult().isEmpty() ? persona.getResult().get(0) : null;
    }

    /**
     * @param cuentasMedioTarjeta
     * @return
     */
    private String dispersarDescuentosMedioTarjetaAnibol(List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta, TipoProcesoAnibolEnum tipoProcesoAnibol) {
        String firmaServicio = "dispersarDescuentosMedioTarjetaAnibol(List<CuentaAdministradorSubsidioDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio + " con un arreglo de datos de tamaño " + cuentasMedioTarjeta.size());
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaActiva = new ArrayList<>();
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<>();
        SaldoTarjetaDTO saldoTarjetaDTO;
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();

        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {

            if (EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(cuentaAdministradorSubsidioDTO.getEstadoTarjeta())) {
                cuentasTarjetaActiva.add(cuentaAdministradorSubsidioDTO);
                saldoTarjetaDTO = new SaldoTarjetaDTO();
                String tipoIdentificacionAnibol
                        = com.asopagos.clienteanibol.enums.TipoIdentificacionEnum
                                .valueOf(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name())
                                .getTipoIdentificacion();
                saldoTarjetaDTO.setTipoIdentificacion(tipoIdentificacionAnibol);
                saldoTarjetaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
                saldoTarjetaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
                if (tipoProcesoAnibol != null) {

                    saldoTarjetaDTO.setTipoProceso(tipoProcesoAnibol.getProceso());
                }
                saldoTarjetaDTO.setSaldo(String.format("%.2f", cuentaAdministradorSubsidioDTO.getValorRealTransaccion()));
                saldosTarjetaDTO.add(saldoTarjetaDTO);
            } else {
                cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
            }
        }

        String idProceso = null;

        if (!saldosTarjetaDTO.isEmpty()) {
            try {

                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                ResultadoAnibolDTO resultadoAnibolDTO = null;
                if(conexionAnibol.equals("TRUE")){  
                DescontarSaldoTarjetas descontarSaldoTarjetas = new DescontarSaldoTarjetas(saldosTarjetaDTO);
                descontarSaldoTarjetas.execute();
                resultadoAnibolDTO = descontarSaldoTarjetas.getResult();
                }
                if (resultadoAnibolDTO.isExitoso()) {
                    idProceso = resultadoAnibolDTO.getIdProceso();
                } else {
                    cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
                }
            } catch (TechnicalException e) {
                logger.error("No se pudo realizar la conexión con Anibol", e);
                cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
            }
        }

        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idProceso;
    }

    /**
     * Método que se encarga de gestionar (consultar estado tarjeta, abonar y
     * registrar transacciones fallidas) la dispersión de pagos al medio tarjeta
     *
     * @author rlopez
     *
     * @param cuentasMedioTarjeta información de las cuentas asociadas al medio
     * de pago tarjeta
     * @return lista de identificadores de las cuentas con dispersión exitosa
     */
    private String dispersarPagosMedioTarjetaAnibol(List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta) {

        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaActiva = new ArrayList<>();
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<>();
        SaldoTarjetaDTO saldoTarjetaDTO;
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {

            String tipoIdentificacionAnibol
                    = com.asopagos.clienteanibol.enums.TipoIdentificacionEnum
                            .valueOf(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name())
                            .getTipoIdentificacion();

            String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
            TarjetaDTO tarjeta = null;
			if(conexionAnibol.equals("TRUE")){
            ConsultarTarjetaActiva consultaTarjetaActiva = new ConsultarTarjetaActiva(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio(), tipoIdentificacionAnibol);
            consultaTarjetaActiva.execute();
            tarjeta = consultaTarjetaActiva.getResult();
			}

            if (tarjeta != null && tarjeta.getNumeroTarjeta() != null && tarjeta.getNumeroTarjeta().equals(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio())) {
                //if(EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(cuentaAdministradorSubsidioDTO.getEstadoTarjeta())) {
                cuentasTarjetaActiva.add(cuentaAdministradorSubsidioDTO);
                saldoTarjetaDTO = new SaldoTarjetaDTO();
                saldoTarjetaDTO.setTipoIdentificacion(tipoIdentificacionAnibol);
                saldoTarjetaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
                saldoTarjetaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
                saldoTarjetaDTO.setSaldo(String.format("%.2f", cuentaAdministradorSubsidioDTO.getValorRealTransaccion()));
                logger.debug("Tarjeta para abonar: " + saldoTarjetaDTO.toString());
                saldosTarjetaDTO.add(saldoTarjetaDTO);
            } else {
                cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
            }
        }

        String idProceso = null;
        if (!saldosTarjetaDTO.isEmpty()) {
            try {

                System.out.println("al servicio de anibol se le envía el siguiente dto para la dispersión: " + saldosTarjetaDTO.toString());
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                ResultadoAnibolDTO resultadoAnibolDTO = null;
                if(conexionAnibol.equals("TRUE")){
                AbonarSaldoTarjetas abonarSaldoTarjetas = new AbonarSaldoTarjetas(saldosTarjetaDTO);
                abonarSaldoTarjetas.execute();
                resultadoAnibolDTO = abonarSaldoTarjetas.getResult();
                }
                if (resultadoAnibolDTO.isExitoso()) {
                    System.out.println("el resultado es exitoso y devolvió el valor de id proceso = " + resultadoAnibolDTO.getIdProceso());
                    idProceso = resultadoAnibolDTO.getIdProceso();
                } else {
                    cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
                }
            } catch (TechnicalException e) {
                logger.error("No se pudo realizar la conexión con Anibol", e);
                cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
            }
        }

        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
        }

        return idProceso;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#dispersarLiquidacionFallecimiento(com.asopagos.rest.security.dto.UserDTO,
     * java.util.List)
     */
    @Override
    public List<Long> dispersarLiquidacionFallecimiento(UserDTO userDTO, String numeroRadicado, List<Long> lstIdsCondicionesBeneficiarios) {
        String firmaMetodo = "PagosSubsidioMonetarioBusiness.dispersarEfectivoLiquidacionFallecimiento(List<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<Long> lstIdNuevosAbonos = new ArrayList<>();
        //se obtienen los detalles programados asociados a los identificadores de condiciones de beneficiarios

        List<DetalleSubsidioAsignadoDTO> lstDetallesProgramados;
        if (numeroRadicado != null) {
            ConsultarDetallesProgramadosPorSolicitud consultarDetallesProgramados = new ConsultarDetallesProgramadosPorSolicitud(numeroRadicado, lstIdsCondicionesBeneficiarios);
            consultarDetallesProgramados.execute();
            lstDetallesProgramados = consultarDetallesProgramados.getResult();

        } else {
            ConsultarDetallesProgramadosPorIdCondicionesBeneficiarios consultarDetallesProgramados = new ConsultarDetallesProgramadosPorIdCondicionesBeneficiarios(lstIdsCondicionesBeneficiarios);
            consultarDetallesProgramados.execute();
            lstDetallesProgramados = consultarDetallesProgramados.getResult();
        }

        List<CuentaAdministradorSubsidioDTO> listadoAdminMedioTarjeta = new ArrayList<>();

        List<Long> idsCuentasAdministradorSubsidio = new ArrayList<>();

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
            ConsultarCuentaAdmonSubsidioDTO consultarCuentaAdmonSubsidioDTO = new ConsultarCuentaAdmonSubsidioDTO(idCuentaRelacionadoDetalleProgramado);
            consultarCuentaAdmonSubsidioDTO.execute();
            CuentaAdministradorSubsidioDTO nuevaCuentaBasadadAbonoDetalleProgramado = consultarCuentaAdmonSubsidioDTO.getResult();

            nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraCreacionRegistro(new Date());
            nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraTransaccion(new Date());
            nuevaCuentaBasadadAbonoDetalleProgramado.setFechaHoraUltimaModificacion(new Date());
            nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioCreacionRegistro(userDTO.getNombreUsuario());
            nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
            nuevaCuentaBasadadAbonoDetalleProgramado.setUsuarioTransaccionLiquidacion(userDTO.getNombreUsuario());
            nuevaCuentaBasadadAbonoDetalleProgramado.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.ENVIADO);
            //se obtienen los detalles asociados a la cuenta
//            List<DetalleSubsidioAsignadoDTO> detallesRelacionados = lstDetallesProgramados.stream()
//                    .filter(detalle -> detalle.getIdCuentaAdministradorSubsidio().compareTo(idCuentaRelacionadoDetalleProgramado) == 0)
//                    .collect(Collectors.toList());
//            nuevaCuentaBasadadAbonoDetalleProgramado.setListaDetallesSubsidioAsignadoDTO(detallesRelacionados);
            //se crea la nueva cuenta
//            Long idNuevoAbono = registrarCuentaAdministradorSubsidio(nuevaCuentaBasadadAbonoDetalleProgramado, userDTO);
//            CuentaAdministradorSubsidioDTO cuentaAbono = consultarCuentaAdmonSubsidioDTO(idNuevoAbono);
            //se pasa a APLICADO

            if (nuevaCuentaBasadadAbonoDetalleProgramado.getMedioDePago() != null) {
                if (TipoMedioDePagoEnum.EFECTIVO.equals(nuevaCuentaBasadadAbonoDetalleProgramado.getMedioDePago())) {
                    nuevaCuentaBasadadAbonoDetalleProgramado.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.APLICADO);
                } else if (TipoMedioDePagoEnum.TARJETA.equals(nuevaCuentaBasadadAbonoDetalleProgramado.getMedioDePago())) {
                    listadoAdminMedioTarjeta.add(nuevaCuentaBasadadAbonoDetalleProgramado);
                    idsCuentasAdministradorSubsidio.add(idCuentaRelacionadoDetalleProgramado);
                }
            }

            ActualizarCuentaAdministradorSubsidio actualizarCuenta = new ActualizarCuentaAdministradorSubsidio(nuevaCuentaBasadadAbonoDetalleProgramado);
            actualizarCuenta.execute();

            //se actualiza la cuenta asociada con anterioridad a los detalles programados
            //se modifican los valores de los detalles para poder agregarlo
            lstDetallesProgramados.stream().forEach(element -> {
                //element.setIdDetalleSubsidioAsignado(null);
                //se asocia el id de la nueva cuenta creada
//                element.setIdCuentaAdministradorSubsidio(idNuevoAbono);
                element.setUsuarioCreador(userDTO.getNombreUsuario());
                element.setFechaHoraCreacion(new Date());
                element.setUsuarioUltimaModificacion(userDTO.getNombreUsuario());
                element.setFechaHoraUltimaModificacion(new Date());

                ActualizarDetalleSubsidioAsignado actualizarDetalle = new ActualizarDetalleSubsidioAsignado(element);
                actualizarDetalle.execute();
            });

            //se crean los detalles de subsidios asignados que estaban en la tabla de detalles programados asociados a la nueva cuenta.
            //registrarDetalleSubsidioAsignado(detallesRelacionados);
            lstIdNuevosAbonos.add(nuevaCuentaBasadadAbonoDetalleProgramado.getIdCuentaAdministradorSubsidio());
        }

        if (listadoAdminMedioTarjeta != null && !listadoAdminMedioTarjeta.isEmpty()) {
            String idProceso = dispersarPagosMedioTarjetaAnibol(listadoAdminMedioTarjeta);
            if (idProceso != null) {
                registrarSolicitudAnibol(idProceso, idsCuentasAdministradorSubsidio, TipoOperacionAnibolEnum.DISPERSION_LIQUIDACION_FALLECIMIENTO);
            }
        }

        //se actualizan los detalles programados
        //consultasCore.actualizarEstadoDetalleADerechoProgramado(idsDetallesProgramados);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return lstIdNuevosAbonos.isEmpty() ? null : lstIdNuevosAbonos;
    }

    private void registrarSolicitudAnibol(String idProcesoAnibol, List<Long> listaIdsCuentaAdminSubsidio, TipoOperacionAnibolEnum tipoOperacion) {

        Gson gson = new GsonBuilder().create();

        RegistroSolicitudAnibolModeloDTO reAnibolModeloDTO = new RegistroSolicitudAnibolModeloDTO();
        reAnibolModeloDTO.setFechaHoraRegistro(new Date());
        reAnibolModeloDTO.setTipoOperacionAnibol(tipoOperacion);
        reAnibolModeloDTO.setParametrosIN(gson.toJson(listaIdsCuentaAdminSubsidio));
        reAnibolModeloDTO.setIdProceso(idProcesoAnibol);
        reAnibolModeloDTO.setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.EN_ESPERA);

        RegistrarSolicitudAnibol registroSolicitud = new RegistrarSolicitudAnibol(reAnibolModeloDTO);
        registroSolicitud.execute();
        registroSolicitud.getResult();
    }

    @Override
    public void dispersarPagosLiquidacionFallecimiento() {
        String firmaMetodo = "dispersarPagosLiquidacionFallecimiento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // ConsultarLiquidacionFallecimientoAnibol registrosAnibol = new ConsultarLiquidacionFallecimientoAnibol();
        // registrosAnibol.execute();
        // List<RegistroSolicitudAnibol> registros = registrosAnibol.getResult();

        // for (RegistroSolicitudAnibol registroAnibol : registros) {

        //     List<ResultadoProcesamientoDTO> resultadosAnibol;
        //     resultadosAnibol = new ArrayList();

        //     try {
        //         ConsultarEstadoProcesamiento consultarEstadoProcesamiento = new ConsultarEstadoProcesamiento(registroAnibol.getIdProceso());
        //         consultarEstadoProcesamiento.execute();
        //         resultadosAnibol = consultarEstadoProcesamiento.getResult();

        //     } catch (TechnicalException e) {
        //         logger.warn("No se pudo realizar la conexión con Anibol", e);
        //         return;
        //     }

        //     List<Long> cuentasTarjetaInactivaError = new ArrayList<>();

        //     for (ResultadoProcesamientoDTO resultadoAnibol : resultadosAnibol) {

        //         if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
        //             Gson gson = new GsonBuilder().create();

        //             Type listType = new TypeToken<ArrayList<Long>>() {
        //             }.getType();
        //             List<Long> listaIdsAdminSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);

        //             if (resultadoAnibol.isExitoso()) {

        //                 marcarAplicadoCuentasLiquidacionFallecimiento(listaIdsAdminSubsidio);

        //                 actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());

        //             } else {
        //                 cuentasTarjetaInactivaError.addAll(listaIdsAdminSubsidio);
        //             }
        //         }
        //         if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {

        //             Gson gson = new GsonBuilder().create();
        //             Type listType = new TypeToken<ArrayList<Long>>() {
        //             }.getType();
        //             List<Long> listaIdsAdminSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);

        //             if (!resultadoAnibol.isExitoso()) {

        //                 cuentasTarjetaInactivaError.addAll(listaIdsAdminSubsidio);
        //             }
        //         }
        //     }

        //     for (Long cuenta : cuentasTarjetaInactivaError) {
        //         registrarTransaccionFallida(cuenta);
        //         actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
        //     }
        // }
        resultadoDispersionLiquidacionFallecimiento();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void resultadoDispersionLiquidacionFallecimiento() {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.resultadoDispersionLiquidacionFallecimiento()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        //Consulta los registros EN_ESPERA de una liquidación por fallecimiento en la tabla RegistroSolicitudAnibol
        logger.info("Inicia consulta de registros en espera de una liquidación por fallecimiento");
        ConsultarLiquidacionFallecimientoAnibol registrosIdProcesoAnibol = new ConsultarLiquidacionFallecimientoAnibol();
        registrosIdProcesoAnibol.execute();
        List<RegistroSolicitudAnibol> resultadosIdProcesosAnibol = registrosIdProcesoAnibol.getResult();
        logger.info("Finaliza consulta de registros en espera de una liquidación por fallecimiento");

        logger.info("Inicia la iteración de los registros Anibol: " + resultadosIdProcesosAnibol.size());
        for (RegistroSolicitudAnibol registroIdProcesoAnibol : resultadosIdProcesosAnibol) {
            logger.info("idProceso Anibol -> " + registroIdProcesoAnibol.getIdProceso());
            
            boolean actualizarRegistro = false;
            ResultadoDispersionAnibolDTO resultadoConsultaEstadopProcesamiento;

            //Consulta el estado de la solicitud en ANIBOL
            try {
                logger.info("Inicia la consulta del estado de la solicitud en ANIBOL");
                ConsultarEstadoProcesamientoV2 consultarEstadoProcesamientoV2 = new ConsultarEstadoProcesamientoV2(registroIdProcesoAnibol.getIdProceso());
                consultarEstadoProcesamientoV2.execute();
                resultadoConsultaEstadopProcesamiento = consultarEstadoProcesamientoV2.getResult();
                logger.info("Finaliza la consulta del estado de la solicitud en ANIBOL");
            } catch (TechnicalException e) {
                logger.warn("No se pudo realizar la conexión con Anibol " + e);
                return;
            }

            //Evalúa el resultado de la consulta a ANIBOL
            if (resultadoConsultaEstadopProcesamiento != null && !resultadoConsultaEstadopProcesamiento.getListadoResultadosProcesamiento().isEmpty()) {
                logger.info("El resultado de la consulta no es nula ni vacía");
                //List<ResultadoProcesamientoDTO> resultadosProcesamiento = resultadoConsultaEstadopProcesamiento.getListadoResultadosProcesamiento();

                logger.info("Se obtienen los ids de las cuentas de subsidio que se deben actualizar ->" + registroIdProcesoAnibol.getParametrosIN());
                Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<ArrayList<Long>>() {
                        }.getType();
                        List<Long> listaIdsAdminSubsidio = gson.fromJson(registroIdProcesoAnibol.getParametrosIN(), listType);
                
                if(listaIdsAdminSubsidio != null && !listaIdsAdminSubsidio.isEmpty()) {
                    for (Long idCuenta : listaIdsAdminSubsidio) {
                        logger.info("Id cuenta de subsidio a consultar -> " + idCuenta);
                    }
                }   

                logger.info("Se consultan las cuentas de administrador de subsidio asociadas al idProceso Anibol -> " + registroIdProcesoAnibol.getIdProceso());
                ConsultarCuentasAdminSubsidioPorIds  consultarCuentasAdminSubsidio = new ConsultarCuentasAdminSubsidioPorIds (listaIdsAdminSubsidio);
                consultarCuentasAdminSubsidio.execute();
                List<CuentaAdministradorSubsidioDTO> cuentasAdminSubsidio = consultarCuentasAdminSubsidio.getResult();
                
                Map<String, ResultadoProcesamientoDTO> resultadosMap = resultadoConsultaEstadopProcesamiento.getListadoResultadosProcesamiento().stream()
                        .collect(Collectors.toMap(
                            r -> generarClave(r.getTipoIdentificacion(), r.getNumeroIdentificacion(), r.getNumeroTarjeta()),
                            Function.identity(),
                            (a, b) -> a
                        ));
                
                List<Long> exitosos = new ArrayList<>();
                List<Long> noExitosos = new ArrayList<>();
                List<Long> noCoincidentes = new ArrayList<>();
                
                if (cuentasAdminSubsidio != null && !cuentasAdminSubsidio.isEmpty()) {
                    for (CuentaAdministradorSubsidioDTO cuenta : cuentasAdminSubsidio) {
                        String clave = generarClave(cuenta.getTipoIdAdminSubsidio().name(), cuenta.getNumeroIdAdminSubsidio(), cuenta.getNumeroTarjetaAdminSubsidio());
                        ResultadoProcesamientoDTO resultado = resultadosMap.get(clave);
                        
                        if (resultado != null && (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultado.getEstado()) ||
                                                    ResultadoProcesoAnibolEnum.RTA_ANIBOL_VALIDACION.name().equals(resultado.getEstado()))) {
                            if (resultado.isExitoso()) {
                                exitosos.add(cuenta.getIdCuentaAdministradorSubsidio());
                            } else {
                                noExitosos.add(cuenta.getIdCuentaAdministradorSubsidio());
                            }

                            if (!actualizarRegistro) {
                                actualizarRegistro = true;
                            }

                        } else {
                            noCoincidentes.add(cuenta.getIdCuentaAdministradorSubsidio());
                        }
                    }
                }         
                
                //Se evalúan las listas de resultados para marcar las cuentas como aplicadas o registrar transacciones fallidas
                if (!exitosos.isEmpty()) {
                    marcarAplicadoCuentasLiquidacionFallecimiento(exitosos);
                }
                if (!noExitosos.isEmpty()) {
                    logger.info("Se encontraron cuentas con estado no exitoso: " + noExitosos);
                        for (Long idCuenta : noExitosos) {
                            registrarTransaccionFallida(idCuenta);
                        }
                }
                if (!noCoincidentes.isEmpty()) {
                    logger.info("Se encontraron cuentas sin coincidencia en la consulta: " + noCoincidentes);
                        for (Long idCuenta : noCoincidentes) {
                            registrarTransaccionFallida(idCuenta);
                        }
                }
            } else {
                logger.info("No hay resultado en la consulta con ANIBOL");
            }
            //Actualiza el estado del estado de solicitud ANIBOL solo si encontró al menos un registro con respuesta RTA_REDEBAN o RTA_ANIBOL_VALIDACION
            //Esto significa que ANIBOL y REDEBAN ya procesaron la solicitud
            if (actualizarRegistro) {
                logger.info("Se cambia el estado de procesamiento del idProceso");
                actualizarEstadoRegistroSolicitudAnibol(registroIdProcesoAnibol.getIdRegistroSolicitudAnibol());
            }
            logger.info("Finaliza la iteración de los registros idProceso Anibol -> " + registroIdProcesoAnibol.getIdProceso());
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    private String generarClave(String tipoIdentificacion, String numeroIdentificacion, String numeroTarjeta) {
        return (tipoIdentificacion != null ? tipoIdentificacion.trim() : "") + "|"
            + (numeroIdentificacion != null ? numeroIdentificacion.trim() : "") + "|"
            + (numeroTarjeta != null ? numeroTarjeta.trim() : "");
    }

    private void marcarAplicadoCuentasLiquidacionFallecimiento(List<Long> listaIdsAdminSubsidio) {
        MarcarAplicadoCuentasLiqFallecimiento marcar = new MarcarAplicadoCuentasLiqFallecimiento(listaIdsAdminSubsidio);
        marcar.execute();
    }

    /* (non-Javadoc)
	 * @see com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#capturarResultadoReexpedicionBloqueo(java.util.List)
     */
    @Override
    public List<ResultadoReexpedicionBloqueoOutDTO> capturarResultadoReexpedicionBloqueo(List<ResultadoReexpedicionBloqueoInDTO> listaConsulta) {
        String firma = "capturarResultadoReexpedicionBloqueo(List<ResultadoReexpedicionBloqueoInDTO>)";

        logger.info("Inicia método " + firma);
        List<ResultadoReexpedicionBloqueoOutDTO> lista = new ArrayList<>();

        if (listaConsulta.isEmpty()) {
            lista.add(new ResultadoReexpedicionBloqueoOutDTO(null, EstadoRecepcionResultadoEnum.NO_OK.getDescripcion()));
            logger.info("Finaliza método " + firma + " - el arreglo de entrada vacío");
            return lista;
        } else {
            try {
                logger.info("listaConsulta " +listaConsulta);
                logger.info("listaConsulta size " +listaConsulta.size());
                ProcesarResultadoReexpedicionBloqueoAnibol procesar = new ProcesarResultadoReexpedicionBloqueoAnibol(listaConsulta);
                procesar.execute();
 
            } catch (Exception e) {

                listaConsulta.stream().forEach(resultado -> lista.add(new ResultadoReexpedicionBloqueoOutDTO(resultado.getIdProceso(), EstadoRecepcionResultadoEnum.NO_OK.getDescripcion())));

                logger.info("Finaliza con error método " + firma);
                return lista;
            }

            listaConsulta.stream().forEach(resultado -> lista.add(new ResultadoReexpedicionBloqueoOutDTO(resultado.getIdProceso(), EstadoRecepcionResultadoEnum.OK.getDescripcion())));

            logger.info("Finaliza método " + firma);
            return lista;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#procesarCapturaResultadoReexpedicionBloqueo(java.util.List)
     */
    @Override
    @Asynchronous
    public void procesarResultadoReexpedicionBloqueoAnibol(List<ResultadoReexpedicionBloqueoInDTO> listaConsulta) {
        String firma = "procesarResultadoReexpedicionBloqueoAnibol(List<ResultadoReexpedicionBloqueoInDTO>)";
        logger.info("Inicio método " + firma);

//		listaConsulta.parallelStream().forEach(resultadoReexpedicion -> {
//			if(resultadoReexpedicion.getTipoNovedad() == 1){
//				procesarRegistroReexpedicion(resultadoReexpedicion);
//			}
//			else if(resultadoReexpedicion.getTipoNovedad() == 2){
//				procesarRegistroBloqueo(resultadoReexpedicion);
//			}
//		});
        for (ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion : listaConsulta) {
            logger.info("resultadoReexpedicion get tipo novedad " +resultadoReexpedicion.getTipoNovedad());
            if (resultadoReexpedicion.getTipoNovedad() == 1) {
                procesarRegistroReexpedicionTarjeta(resultadoReexpedicion);
            } else if (resultadoReexpedicion.getTipoNovedad() == 2) {
                // se deja comentado por glpi 81785
                /*el dia de hoy (03/07/2024) se acordo con ronaly freile y diego morales
                que el tema de los bloqueos de la tarjeta puede cosacionar fallos en la liquidacion
                esto debido a que si un administrador de subsidio presenta 
                un solo medio de pago, en este caso tarjeta y esta inactivo
                al momento de la dispercion de aportes va a fallar debido a que
                el administrador de subsidio no posee ningun medio de pago activo */
                // procesarRegistroBloqueo(resultadoReexpedicion);
            }else if (resultadoReexpedicion.getTipoNovedad() == 3){
                procesarRegistroExpedicionSobreTarjeta(resultadoReexpedicion);
            }
        }
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método que realiza la implementación del proceso sobre un objeto de la
     * clase ResultadoReexpedicionBloqueoInDTO cuando el tipo de novedades es
     * reexpedición.
     *
     * @param resultadoReexpedicion DTO con la información a procesar.
     */
    private void procesarRegistroReexpedicion(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {

        String firma = "procesarRegistroReexpedicion(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);

        ConsultarInfoPersonaReexpedicion infoPersonaReexpedicion = new ConsultarInfoPersonaReexpedicion(resultadoReexpedicion.getIdentificacion(),
                resultadoReexpedicion.getTipoIdentificacion(), resultadoReexpedicion.getNumeroTarjeta());

        infoPersonaReexpedicion.execute();

        InfoPersonaReexpedicionDTO infoPersona = infoPersonaReexpedicion.getResult();
        if (infoPersona == null || infoPersona.getIdPersona() == null) {
            // Se envía un registro de inconsistencia a la HU-31-357 Consultar bandeja de inconsistencias Tarjeta,
            // con el motivo: “Persona no está registrada en el sistema”.
            persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_REGISTRADA,
                    EstadoResolucionInconsistenciaEnum.PENDIENTE, null));
        } else {
            if (infoPersona.getNumeroTarjeta() != null && infoPersona.getNumeroTarjeta().equals(resultadoReexpedicion.getNumeroTarjeta())) {

                RealizarComparacionSaldoTarjetas compararSaldos = new RealizarComparacionSaldoTarjetas(infoPersona.getIdPersona(), resultadoReexpedicion.getSaldoNuevaTarjeta(), infoPersona.getNumeroTarjeta());
                compararSaldos.execute();
                ComparacionSaldoTarjetaEnum resultadoComparacionSaldos = compararSaldos.getResult();

                if (ComparacionSaldoTarjetaEnum.MENOR.equals(resultadoComparacionSaldos)) {
                    // Saldo tarjeta (objeto bloqueo) < saldo tarjeta nueva (expedida)
                    persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.INTENTO_TRASLADO_SALDO_MAYOR,
                            EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                } else if (ComparacionSaldoTarjetaEnum.IGUAL.equals(resultadoComparacionSaldos)) {
                    // Saldo tarjeta (objeto bloqueo) = saldo tarjeta nueva (expedida), saldo mayor a cero
                    if (EstadoTarjetaMultiserviciosEnum.BLOQUEADA.equals(infoPersona.getEstadoTarjeta())) {
                        persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA,
                                EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));

                        registrarNovedades(resultadoReexpedicion);

                        registrarMovimientosCuentaAdminSubsidio(resultadoReexpedicion, infoPersona.getIdPersona(), CasoMovimientoSubsidioEnum.CASO_1);
                    } else if (EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(infoPersona.getEstadoTarjeta())) {
                        bloquearTarjetaYRegistrarNovedades(resultadoReexpedicion);
                        registrarMovimientosCuentaAdminSubsidio(resultadoReexpedicion, infoPersona.getIdPersona(), CasoMovimientoSubsidioEnum.CASO_1);
                    }
                } else if (ComparacionSaldoTarjetaEnum.IGUALES_CERO.equals(resultadoComparacionSaldos)) {
                    // Saldo tarjeta (objeto bloqueo) = saldo tarjeta nueva (expedida), saldo igual a cero
                    if (EstadoTarjetaMultiserviciosEnum.BLOQUEADA.equals(infoPersona.getEstadoTarjeta())) {
                        persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA,
                                EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));

                        registrarNovedades(resultadoReexpedicion);
                    } else if (EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(infoPersona.getEstadoTarjeta())) {
                        bloquearTarjetaYRegistrarNovedades(resultadoReexpedicion);
                    }
                } else if (ComparacionSaldoTarjetaEnum.MAYOR.equals(resultadoComparacionSaldos)) {
                    // Saldo tarjeta (objeto bloqueo) > saldo tarjeta nueva (expedida)
                    if (EstadoTarjetaMultiserviciosEnum.BLOQUEADA.equals(infoPersona.getEstadoTarjeta())) {
                        persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA,
                                EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));

                        registrarNovedades(resultadoReexpedicion);

                        registrarMovimientosCuentaAdminSubsidio(resultadoReexpedicion, infoPersona.getIdPersona(), CasoMovimientoSubsidioEnum.CASO_2);

                        persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA_SALDO_PENDIENTE,
                                EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                    } else if (EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(infoPersona.getEstadoTarjeta())) {
                        bloquearTarjetaYRegistrarNovedades(resultadoReexpedicion);
                        registrarMovimientosCuentaAdminSubsidio(resultadoReexpedicion, infoPersona.getIdPersona(), CasoMovimientoSubsidioEnum.CASO_2);

                        persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA_SALDO_PENDIENTE,
                                EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                    }
                }
            } else {
                // Se envía un registro de inconsistencia a la HU-31-357 Consultar bandeja de inconsistencias Tarjeta,
                // con el motivo: “Tarjeta que se solicita bloquear no se encuentra asociada a la persona consultada”.
                PersistirRegistroInconsistenciaTarjeta persistirRegistroInconsistencia = new PersistirRegistroInconsistenciaTarjeta(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEAR_NO_ASOCIADA_A_PERSONA,
                        EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                persistirRegistroInconsistencia.execute();
            }
        }
        logger.info("Finaliza método " + firma);
    }

    // private void procesarRegistroReexpedicionTarjeta(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion){
    //     String firma = "procesarRegistroReexpedicionTarjeta(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion)";
    //     logger.info("Inicia método " + firma);

    // }
    /**
     * Método que realiza la implementación del proceso sobre un objeto de la
     * clase ResultadoReexpedicionBloqueoInDTO cuando el tipo de novedades es
     * reexpedición. Versión 2
     *
     * @param resultadoReexpedicion DTO con la información a procesar.
     */
    private void procesarRegistroReexpedicionTarjeta(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "procesarRegistroExpedicionDos(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion)";
        logger.info("Inicia método " + firma);

        ValidarExistenciaTarjeta existenciaTarjeta = new ValidarExistenciaTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
            existenciaTarjeta.execute();
        Boolean existeTarjeta = existenciaTarjeta.getResult();
        logger.info("procesarRegistroReexpedicionTarjeta existeTarjeta ???? "+existeTarjeta);
        if (existeTarjeta) {
            persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_EXPEDIR_YA_EXPEDIDA,
                    EstadoResolucionInconsistenciaEnum.PENDIENTE, null));        
            return;
        }else{
            // este servicio debe de obtener la id de grupo familiar a afectar y la id del medio de pago a hacer merg
            ConsultarGruposFamiliaresConMarcaYAdmin servicioGruposFamiliaresMarcaYAdmin = new ConsultarGruposFamiliaresConMarcaYAdmin(resultadoReexpedicion.getTipoIdentificacion(),resultadoReexpedicion.getIdentificacion(),String.valueOf(resultadoReexpedicion.getNumeroTarjeta()), Boolean.FALSE);
            servicioGruposFamiliaresMarcaYAdmin.execute();
            List<Long> idsGruposFamiliares = servicioGruposFamiliaresMarcaYAdmin.getResult();
            logger.info("idsGruposFamiliares " +idsGruposFamiliares.size());
    
            if(!idsGruposFamiliares.isEmpty() && idsGruposFamiliares.size() > 0){
                short idRelacion = 1;

                MedioDePagoModeloDTO medioDePagoModelo = new MedioDePagoModeloDTO();
                medioDePagoModelo.setTipoMedioDePago(TipoMedioDePagoEnum.TARJETA);
                medioDePagoModelo.setNumeroTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
                medioDePagoModelo.setDisponeTarjeta(true);
                medioDePagoModelo.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
                medioDePagoModelo.setSolicitudTarjeta(SolicitudTarjetaEnum.REEXPEDICION);
                medioDePagoModelo.setTarjetaMultiservicio(Boolean.TRUE);
                resultadoReexpedicion.setTipoSolicitud("FLUJO_B");
                Long idMedioDePagoTarjetaCreado = registrarTarjetaExpedicion(medioDePagoModelo);
                medioDePagoModelo.setIdMEdioDePagoActualizar(idMedioDePagoTarjetaCreado);
                for(Long idGrupo : idsGruposFamiliares){
                    registrarActualizacionTarjetaGrupoFamiliar(idGrupo, medioDePagoModelo);
                    registrarNovedadAutoCambioMedioPagoAdminSubsidio(resultadoReexpedicion, idGrupo, idRelacion);
                }
                bloquearTarjeta(resultadoReexpedicion.getNumeroTarjeta());
            }
        }

    } 

    private void procesarRegistroExpedicionSobreTarjeta(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "procesarRegistroExpedicionSobreTarjeta(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion)";
        logger.info("Inicia método " + firma);
        Long idMedioTarjeta = null;
        try {
            ValidarExistenciaTarjeta existenciaTarjeta = new ValidarExistenciaTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
            existenciaTarjeta.execute();
            Boolean existeTarjeta = existenciaTarjeta.getResult();
            logger.info("existeTarjeta??? " +existeTarjeta);
            
            if (existeTarjeta) {
                persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_EXPEDIR_YA_EXPEDIDA,
                        EstadoResolucionInconsistenciaEnum.PENDIENTE, null));        
                return;
            } else {
                ConsultarGruposFamiliaresConMarcaYAdmin servicioGruposFamiliaresMarcaYAdmin = new ConsultarGruposFamiliaresConMarcaYAdmin(resultadoReexpedicion.getTipoIdentificacion(),resultadoReexpedicion.getIdentificacion(),null, Boolean.TRUE);
                servicioGruposFamiliaresMarcaYAdmin.execute();
                List<Long> idsGruposFamiliares = servicioGruposFamiliaresMarcaYAdmin.getResult();

                logger.info("idsGruposFamiliares " +idsGruposFamiliares.size());

                if(!idsGruposFamiliares.isEmpty() || idsGruposFamiliares.size() < 0){
                    logger.warn("entra if gruposfamiliares !=null ");
                    short idRelacion = 1;
                    // logger.warn("recorremos los grpos familiares, la id del grupo procesado es "+ idGrupo);
                    MedioDePagoModeloDTO medioDePagoModelo = new MedioDePagoModeloDTO();
                    medioDePagoModelo.setTipoMedioDePago(TipoMedioDePagoEnum.TARJETA);
                    medioDePagoModelo.setNumeroTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
                    medioDePagoModelo.setDisponeTarjeta(true);
                    medioDePagoModelo.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
                    medioDePagoModelo.setSolicitudTarjeta(SolicitudTarjetaEnum.EXPEDICION);
                    medioDePagoModelo.setTarjetaMultiservicio(Boolean.TRUE);
                    resultadoReexpedicion.setTipoSolicitud("FLUJO_B");
                    
                    Long idMedioDePagoTarjetaCreado = registrarTarjetaExpedicion(medioDePagoModelo);
                    
                    reemplazarMedioDePagoGrupoFamiliar(idsGruposFamiliares,idMedioDePagoTarjetaCreado);

                    for(Long idGrupo : idsGruposFamiliares){
                        registrarNovedadAutoCambioMedioPagoAdminSubsidio(resultadoReexpedicion, idGrupo, idRelacion);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("ha ocurrido un fallo en: " + firma + " excepcion registrada : " + e);
        }
    }
    

    /**
     * Método que realiza la implementación del proceso sobre un objeto de la
     * clase ResultadoReexpedicionBloqueoInDTO cuando el tipo de novedades es
     * Expedición.
     *
     * @param resultadoExpedicion DTO con la información a procesar.
     */
    private void procesarRegistroExpedicion(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "procesarRegistroExpedicion(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);
        ObjectMapper objectMapper = new ObjectMapper();

        logger.info("Ingresan los valores de tipo identificacion " + resultadoReexpedicion.getTipoIdentificacion() + " Ingresa el valor de numero de identificacion es: " + resultadoReexpedicion.getIdentificacion());
        // Consulta en la Base de datos para saber si la persona consultada existe en la Data Base Genesys.
        ConsultarInfoPersonaExpedicion infoPersonaExpedicion = new ConsultarInfoPersonaExpedicion(resultadoReexpedicion.getIdentificacion(),
                resultadoReexpedicion.getTipoIdentificacion());

        infoPersonaExpedicion.execute();

        InfoPersonaExpedicionDTO infoPersona = infoPersonaExpedicion.getResult();
        logger.info("Respuesta de base de datos Expedicion--- " + firma + " Resultado es" + infoPersona);

        if (infoPersona == null || infoPersona.getIdPersona() == null) {
            logger.info("Expedicion--- Persona no está registrada en el sistema");
            persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_REGISTRADA,
                    EstadoResolucionInconsistenciaEnum.PENDIENTE, null));
        } else {
            logger.info("Expedicion--- Persona está registrada en el sistema siguiente validacion estado afiliacion");

            if(!infoPersona.getEstado().equals(EstadoAfiliadoEnum.ACTIVO)){
                logger.info("Expedicion--- Persona tiene un estado afiliacion diferente a ACTIVO en la CCF");

                persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_TIENE_ESTADO_ACTIVO,
                        EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
            }else {
                logger.info("Expedicion--- Persona tiene un estado afiliacion ACTIVO en la CCF se procede a crear medio de pago y asignarlo a la persona ");

                logger.info("Empieza el proceso de creacion de medio de pago y medio pago persona ");
                //Metodo encargado de crear el medio de pago
                try {
                    MedioDePagoModeloDTO medioDePagoModelo = new MedioDePagoModeloDTO();
                    medioDePagoModelo.setTipoMedioDePago(TipoMedioDePagoEnum.TARJETA);
                    medioDePagoModelo.setNumeroTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
                    medioDePagoModelo.setDisponeTarjeta(true);
                    medioDePagoModelo.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
                    medioDePagoModelo.setSolicitudTarjeta(SolicitudTarjetaEnum.EXPEDICION);
                    medioDePagoModelo.setTarjetaMultiservicio(Boolean.TRUE);
                    medioDePagoModelo.setNumeroIdentificacionTitular(String.valueOf(infoPersona.getIdPersona()));

                    //medioDePagoModelo.setTarjetaMultiservicio(Boolean.TRUE);

                    logger.info("Expedicion--- Arreglo creado para el registro de medio de pago:  " + medioDePagoModelo);

                    Long idMedioTarjeta = registrarMedioDePago(medioDePagoModelo);

                    logger.info("Expedicion--- ID para el registro de medio de pago resultante:  " + idMedioTarjeta);
                    logger.info("Se en este punto se genera tambien el registro de medioPagoPersona con el id de medioPago "+ idMedioTarjeta);
                }catch (Exception e){
                    logger.info("Finaliza con error método " + firma + ", tiene un retorno null");
                }

                ConsultarInfoPersonaExpedicionValidaciones infoPersonaExpedicionValidaciones = new ConsultarInfoPersonaExpedicionValidaciones(resultadoReexpedicion.getIdentificacion(),
                            resultadoReexpedicion.getTipoIdentificacion());

                    infoPersonaExpedicionValidaciones.execute();


                    List<InfoPersonaExpedicionValidacionesDTO> infoPersonaExpedicionValidacionesT;


                if (infoPersonaExpedicionValidaciones != null) {
                    infoPersonaExpedicionValidacionesT = infoPersonaExpedicionValidaciones.getResult();
                    logger.info("Expedicion--- resultado de la peticion de la validacion N 2:  " + infoPersonaExpedicionValidacionesT);

                    if (!infoPersonaExpedicionValidacionesT.isEmpty()){
                        logger.info("Expedicion--- Datos Resultantes de la consulta a la DB son, getIdPersona -->  "+ infoPersonaExpedicionValidacionesT.get(0).getIdPersona() + "y el valor de getAdministradorSubsidio -->  " + infoPersonaExpedicionValidacionesT.get(0).getAdministradorSubsidio());

                        short idRelacion = 1;

                        for (InfoPersonaExpedicionValidacionesDTO infoPersonaIterar : infoPersonaExpedicionValidacionesT) {
                            if (infoPersonaIterar.getIdPersona().equals(infoPersonaIterar.getAdministradorSubsidio())) {
                                logger.info("Expedicion--- Persona es Administrador de Subsidio");

                                logger.info("Expedicion--- Si hay data de vuelta con marca en 1");

                                logger.info("Expedicion--- grupo familiar tiene la marca para tarjeta multiservicio");


                                //Se ejecuta el numeral 3.1.4 Registrar novedad automática “Cambio de medio de pago - Administrador de subsidio”
                                //registrarMovimientosCuentaAdminSubsidio(resultadoReexpedicion, infoPersona.getIdPersona(), CasoMovimientoSubsidioEnum.CASO_1);
                                registrarNovedadAutoCambioMedioPagoAdminSubsidio(resultadoReexpedicion, infoPersonaIterar.getGrupoFamiliar(), idRelacion);
                                //Registro de novedades
                                //registrarNovedades(resultadoReexpedicion);

                            } else {
                                logger.info("Expedicion--- La persona no es Administrador de Subsidio -- Finaliza el proceso");
                                //persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_ES_ADMIN_SUBSIDIO,
                                        //EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                            }

                        }

                    }else{
                        infoPersonaExpedicionValidacionesT = new ArrayList<>();
                        logger.info("Expedicion--- No se encuentra data con el id de la parsona que tenga la marca -- Finaliza el proceso");
                        //persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_TIENE_LA_MARCA,
                                //EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));

                    }
                    

                } else {
                    infoPersonaExpedicionValidacionesT = new ArrayList<>();
                    logger.info("Expedicion--- Persona no se encuantra en la segunda validacion --- Finaliza proceso");
                    
              }


            }

        }

        logger.info("Fin método " + firma);

    }

    /**
     * Método que realiza la implementación del proceso sobre un objeto de la
     * clase ResultadoReexpedicionBloqueoInDTO cuando el tipo de novedades es
     * bloqueo.
     *
     * @param resultadoReexpedicion DTO con la información a procesar.
     */
    private void procesarRegistroBloqueo(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "procesarRegistroBloqueo(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);

        ConsultarInfoPersonaReexpedicion infoPersonaReexpedicion = new ConsultarInfoPersonaReexpedicion(resultadoReexpedicion.getIdentificacion(),
                resultadoReexpedicion.getTipoIdentificacion(), resultadoReexpedicion.getNumeroTarjeta());
        infoPersonaReexpedicion.execute();
        InfoPersonaReexpedicionDTO infoPersona = infoPersonaReexpedicion.getResult();

        if (infoPersona == null || infoPersona.getIdPersona() == null) {
            // Se envía un registro de inconsistencia a la HU-31-357 Consultar bandeja de inconsistencias Tarjeta,
            // con el motivo: “Persona no está registrada en el sistema”.
            /*PersistirRegistroInconsistenciaTarjeta persistirRegistroInconsistencia = new PersistirRegistroInconsistenciaTarjeta(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_REGISTRADA,
                    EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
            persistirRegistroInconsistencia.execute();*/
            persistirRegistroInconsistencia(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.PERSONA_NO_REGISTRADA,
                    EstadoResolucionInconsistenciaEnum.PENDIENTE, null));
        } else {
            if (infoPersona.getNumeroTarjeta() != null && infoPersona.getNumeroTarjeta().equals(resultadoReexpedicion.getNumeroTarjeta())) {

                if (EstadoTarjetaMultiserviciosEnum.BLOQUEADA.equals(infoPersona.getEstadoTarjeta())) {
                    PersistirRegistroInconsistenciaTarjeta persistirRegistroInconsistencia = new PersistirRegistroInconsistenciaTarjeta(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEADA,
                            EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                    persistirRegistroInconsistencia.execute();
                } else {
                    // se ejecuta el numeral 3.1.2 - Bloqueo tarjeta
                    bloquearTarjeta(resultadoReexpedicion.getNumeroTarjeta());
                }
            } else {
                // Se envía un registro de inconsistencia a la HU-31-357 Consultar bandeja de inconsistencias Tarjeta,
                // con el motivo: “Tarjeta que se solicita bloquear no se encuentra asociada a la persona consultada”.
                PersistirRegistroInconsistenciaTarjeta persistirRegistroInconsistencia = new PersistirRegistroInconsistenciaTarjeta(mapearRegistroInconsistencia(resultadoReexpedicion, MotivoInconsistenciaEnum.TARJETA_BLOQUEAR_NO_ASOCIADA_A_PERSONA,
                        EstadoResolucionInconsistenciaEnum.PENDIENTE, infoPersona.getNombreCompleto()));
                persistirRegistroInconsistencia.execute();
            }
        }
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método que implementa los métodos de bloqueo de tarjeta y registro de
     * novedades
     *
     * @param resultadoReexpedicion DTO con la información básica de la tarjeta
     * a bloquear y las novedades a persistir
     */
    private void bloquearTarjetaYRegistrarNovedades(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "bloquearTarjetaYRegistrarNovedades(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);

        bloquearTarjeta(resultadoReexpedicion.getNumeroTarjeta());
        registrarNovedades(resultadoReexpedicion);
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de ejecutar los registros de novedades automáticas para
     * un afiliado dado
     *
     * @param resultadoReexpedicion DTO con la información básica de las
     * novedades a ejecutar.
     */
    private void registrarNovedades(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {
        String firma = "registrarNovedades(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);
        registrarNovedadAutoCambioMedioPagoTrabajador(resultadoReexpedicion);

        List<GruposMedioTarjetaDTO> gruposFamiliares = consultarGruposFamiliaresTrabajadorMedioTarjeta(TipoIdentificacionEnum.valueOf(resultadoReexpedicion.getTipoIdentificacion()),
                resultadoReexpedicion.getIdentificacion(), resultadoReexpedicion.getNumeroTarjeta());

        gruposFamiliares.stream().forEach(grupo -> registrarNovedadAutoCambioMedioPagoAdminSubsidio(resultadoReexpedicion, grupo.getIdGrupo(), grupo.getRelacionGrupo()));
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de invocar el servicio que realiza el cambio de estado
     * de una tarjeta a BLOQUEADA
     *
     * @param numeroTarjeta El número de la tarjeta que se quiere bloquear.
     */
    private void bloquearTarjeta(String numeroTarjeta) {
        String firma = "bloquearTarjeta(String)";
        logger.info("Inicio método " + firma);
        BloquearTarjeta bloquearTarjeta = new BloquearTarjeta(numeroTarjeta);
        bloquearTarjeta.execute();
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de realizar la invocación del servicio que consulta los
     * grupos familiares de los cuales el afiliado es administrador de subsidio
     * activo y que tienen medio de pago igual al enviado por parámetro.
     *
     * @param tipoIdentificacion tipo de identificación del afiliado.
     *
     * @param identificacion número de identificación del afiliado.
     *
     * @param numeroTarjeta número de la tarjeta asociada al afiliado.
     *
     * @return List<GruposMedioTarjetaDTO> con la información de los grupos
     * encontrados.
     */
    private List<GruposMedioTarjetaDTO> consultarGruposFamiliaresTrabajadorMedioTarjeta(TipoIdentificacionEnum tipoIdentificacion,
            String identificacion, String numeroTarjeta) {
        String firma = "consultarGruposFamiliaresTrabajadorMedioTarjeta(TipoIdentificacionEnum, String, String)";
        logger.info("Inicio método " + firma);
        ConsultarGruposTrabajadorMedioTarjeta consultarGrupos = new ConsultarGruposTrabajadorMedioTarjeta(numeroTarjeta, tipoIdentificacion, identificacion);
        consultarGrupos.execute();
        logger.info("Finaliza método " + firma);
        return consultarGrupos.getResult();
    }

    /**
     * Método encargado de realizar la invocación al servicio que radica la
     * novedad automática de cambio medio de pago para el trabajador
     *
     * @param resultadoReexpedicion DTO con la información básica de la novedad.
     */
    private void registrarNovedadAutoCambioMedioPagoTrabajador(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion) {

        String firma = "registrarNovedadAutoCambioMedioPagoTrabajador(ResultadoReexpedicionBloqueoInDTO)";
        logger.info("Inicio método " + firma);
        MedioDePagoModeloDTO medioPago = new MedioDePagoModeloDTO();
        medioPago.setNumeroTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
        medioPago.setTipoMedioDePago(TipoMedioDePagoEnum.TARJETA);
        medioPago.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
        medioPago.setDisponeTarjeta(true);

        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(resultadoReexpedicion.getTipoIdentificacion()));
        datosPersona.setNumeroIdentificacion(resultadoReexpedicion.getIdentificacion());

        SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
        datosPersona.setMedioDePagoModeloDTO(medioPago);
        solicitudNovedad.setDatosPersona(datosPersona);
        solicitudNovedad.setTipoTransaccion(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL);
        RadicarSolicitudNovedadAutomaticaSinValidaciones radicar = new RadicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedad);
        radicar.execute();
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de realizar la invocación al servicio que radica la
     * novedad automática de cambio medio de pago para el administrador de
     * subsidio
     *
     * @param resultadoReexpedicion DTO con la información básica de la novedad.
     *
     * @param idGrupoFamiliar id del grupo familiar del cual es administrador de
     * subsidio el afiliado.
     *
     * @param relacionGrupo id de la relación con el grupo familiar.
     */
    private void registrarNovedadAutoCambioMedioPagoAdminSubsidio(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, Long idGrupoFamiliar, Short relacionGrupo) {

        String firma = "registrarNovedadAutoCambioMedioPagoAdminSubsidio(ResultadoReexpedicionBloqueoInDTO, Long, Short)";
        logger.info("Inicio método " + firma);
        PersonaModeloDTO admonSubsidio = new PersonaModeloDTO();
        admonSubsidio.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(resultadoReexpedicion.getTipoIdentificacion()));
        admonSubsidio.setNumeroIdentificacion(resultadoReexpedicion.getIdentificacion());

        MedioDePagoModeloDTO medioPago = new MedioDePagoModeloDTO();
        medioPago.setNumeroTarjeta(resultadoReexpedicion.getNumeroTarjetaExpedida());
        medioPago.setTipoMedioDePago(TipoMedioDePagoEnum.TARJETA);
        medioPago.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.ACTIVA);
        medioPago.setAdmonSubsidio(admonSubsidio);
        medioPago.setIdGrupoFamiliar(idGrupoFamiliar);
        medioPago.setAfiliadoEsAdministradorSubsidio(Boolean.TRUE);
        medioPago.setIdRelacionGrupoFamiliar(relacionGrupo);
        medioPago.setDisponeTarjeta(true);
        if(resultadoReexpedicion.getTipoSolicitud() != null){
            medioPago.setTipoSolicitud(resultadoReexpedicion.getTipoSolicitud());
        }
        logger.warn("registramos la novedad automatica cambio medio de pago admin conn el medio de pago"+ medioPago.toString());

        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(resultadoReexpedicion.getTipoIdentificacion()));
        datosPersona.setNumeroIdentificacion(resultadoReexpedicion.getIdentificacion());
        datosPersona.setIdGrupoFamiliar(idGrupoFamiliar);

        SolicitudNovedadDTO solicitudNovedad = new SolicitudNovedadDTO();
        datosPersona.setMedioDePagoModeloDTO(medioPago);
        solicitudNovedad.setDatosPersona(datosPersona);

        solicitudNovedad.setTipoTransaccion(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL);

        RadicarSolicitudNovedadAutomaticaSinValidaciones radicar = new RadicarSolicitudNovedadAutomaticaSinValidaciones(solicitudNovedad);
        radicar.execute();
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de mapear el registro de inconsistencia en base a la
     * información pasada por parámetro
     *
     * @param resultadoReexpedicion DTO con la información enviada desde anibol.
     *
     * @param motivoInconsistencia representa el motivo de la inconsistencia que
     * se va a mandar a la bandeja.
     *
     * @param estadoResolucion estado de resolución de la inconsistencia. por
     * defecto se persiste como en espera.
     *
     * @return RegistroInconsistenciaTarjeta creado con la información
     * suministrada.
     */
    private RegistroInconsistenciaTarjeta mapearRegistroInconsistencia(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion,
            MotivoInconsistenciaEnum motivoInconsistencia,
            EstadoResolucionInconsistenciaEnum estadoResolucion, String nombreCompleto) {
        String firma = "mapearRegistroInconsistencia(ResultadoReexpedicionBloqueoInDTO, MotivoInconsistenciaEnum, EstadoResolucionInconsistenciaEnum, String)";
        logger.info("Inicio método " + firma);
        /*
        SimpleDateFormat fechaConTiempo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fechaSinTiempo = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //Date fechaRegistro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultadoReexpedicion.getFechaTransaccion()); //Línea de código antes del siguiente ajuste
            //Ajuste para que acepte cualquiera de los formatos de fecha: yyyy-MM-dd HH:mm:ss o yyyy-MM-dd
            try {
                Date fechaRegistro = null;
                try {
                    fechaRegistro = fechaConTiempo.parse(resultadoReexpedicion.getFechaTransaccion());
                } catch (java.text.ParseException e) {
                    try {
                        fechaRegistro = fechaSinTiempo.parse(resultadoReexpedicion.getFechaTransaccion());
                    } catch (java.text.ParseException ex) {
                        logger.info("Finaliza con error método " + firma + ", retornará null");
                        return null;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        */

        try {
            Date fechaRegistro = new SimpleDateFormat("yyyy-MM-dd").parse(resultadoReexpedicion.getFechaTransaccion());

            RegistroInconsistenciaTarjeta registro = new RegistroInconsistenciaTarjeta();
            registro.setIdProceso(resultadoReexpedicion.getIdProceso());
            //registro.setTipoNovedad(TipoNovedadInconsistenciaEnum.REEXPEDICION.getIdentificador() == resultadoReexpedicion.getTipoNovedad().intValue() ? TipoNovedadInconsistenciaEnum.REEXPEDICION : TipoNovedadInconsistenciaEnum.BLOQUEO);
            if (TipoNovedadInconsistenciaEnum.REEXPEDICION.getIdentificador() == resultadoReexpedicion.getTipoNovedad().intValue()) {
                registro.setTipoNovedad(TipoNovedadInconsistenciaEnum.REEXPEDICION);
                logger.info("Registro la inconsistencia con el tipo de novedad  " + TipoNovedadInconsistenciaEnum.REEXPEDICION);
            } else if (TipoNovedadInconsistenciaEnum.BLOQUEO.getIdentificador() == resultadoReexpedicion.getTipoNovedad().intValue()) {
                registro.setTipoNovedad(TipoNovedadInconsistenciaEnum.BLOQUEO);
                logger.info("Registro la inconsistencia con el tipo de novedad  " + TipoNovedadInconsistenciaEnum.BLOQUEO);
            } else {
                registro.setTipoNovedad(TipoNovedadInconsistenciaEnum.EXPEDICION);
                logger.info("Registro la inconsistencia con el tipo de novedad  " + TipoNovedadInconsistenciaEnum.EXPEDICION);
            }
            registro.setTipoIdentificacion(resultadoReexpedicion.getTipoIdentificacion());
            registro.setNumeroIdentificacion(resultadoReexpedicion.getIdentificacion());
            registro.setTarjetaBloqueo(resultadoReexpedicion.getNumeroTarjeta());
            registro.setTarjetaExpedida(resultadoReexpedicion.getNumeroTarjetaExpedida());
            registro.setSaldoTraslado(resultadoReexpedicion.getSaldoNuevaTarjeta());
            registro.setFechaTransaccion(fechaRegistro);
            registro.setUsuario(resultadoReexpedicion.getUsuario());
            registro.setMotivoInconsistencia(motivoInconsistencia);
            registro.setEstadoResolucion(estadoResolucion);
            registro.setNombreCompleto(nombreCompleto);
            registro.setResultadoGestion(ResultadoGestionInconsistenciaEnum.NO_RESUELTA);

            logger.info("Finaliza método " + firma);
            return registro;

        } catch (Exception e) {

            logger.info("Finaliza con error método " + firma + ", retornará null");
            logger.info(e);
            return null;
        }
    }

    private Map<String, String> solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum tipoIdAdmin,
            String numeroIdAdmin, BigDecimal valorSolicitado, String usuario, Long fecha, String idTransaccionTercerPagador,
            String departamento, String municipio, String user, Long idSitioPago, Long idConvenio) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum,String,BigDecimal,String,Long,String,String,String,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Map<String, String> respuesta = new HashMap<String, String>();
        Double saldoD = new Double(0);
        ValidarExistenciaIdentificadorTransaccion validarIdTran = new ValidarExistenciaIdentificadorTransaccion(idConvenio, idTransaccionTercerPagador);
        validarIdTran.execute();
        Boolean existeTran = validarIdTran.getResult();
        if (existeTran) {
            respuesta.put("error", "Identificador de transacción ya está registrado en el sistema");
        } else if (valorSolicitado.compareTo(BigDecimal.ZERO) < 0) {
            respuesta.put("error", "El valor solicitado es menor a cero (0)");
        } else {
            ConsultarSaldoSubsidioSinOperacionAbono saldo = new ConsultarSaldoSubsidioSinOperacionAbono(numeroIdAdmin, TipoMedioDePagoEnum.EFECTIVO, tipoIdAdmin);
            saldo.execute();
            saldoD = saldo.getResult();
            logger.debug("saldoD: " + saldoD);
            logger.debug("valorSolicitado: " + valorSolicitado);
            if (saldoD == 0) {
                respuesta.put("error", "No se encontró saldo");
            } else if (BigDecimal.valueOf(saldoD).compareTo(valorSolicitado) == -1) {
                respuesta.put("error", "Saldo inferior al valor registrado en la transacción");
            }
        }

        if (!respuesta.containsKey("error")) {
            //si las validaciones son exitosas
            BigDecimal saldoActualSubsidio = new BigDecimal(saldoD);
            SolicitarRetiroConfirmarValorEntregadoSubsidioTerceroPag solicitudRetiroConfirmacion
                    = new SolicitarRetiroConfirmarValorEntregadoSubsidioTerceroPag(departamento, valorSolicitado, idConvenio, idSitioPago,
                            idTransaccionTercerPagador, saldoActualSubsidio, municipio, numeroIdAdmin, user, TipoMedioDePagoEnum.EFECTIVO,
                            Boolean.FALSE, fecha, user, tipoIdAdmin);

            solicitudRetiroConfirmacion.execute();
            respuesta = solicitudRetiroConfirmacion.getResult();

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return respuesta;
    }

    /**
     * Método encargado de obtener el usuario de sistema y adicionarlo al
     * contexto
     */
    public void initContextUsuarioSistema() {
        String firma = "initContextUsuarioSistema()";
        logger.info("Inicio método " + firma);
        // Se genera el token de conexion
        GenerarTokenAccesoSystem tokenAcceso = new GenerarTokenAccesoSystem();
        tokenAcceso.execute();
        TokenDTO token = tokenAcceso.getResult();
        // Se agrega al contexto el usuario y el token
        ContextUtil.addValueContext(AccessToken.class, new AccessToken(token.getToken()));
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de realizar la invocación al servicio que persiste el
     * registro en la bandeja de inconsistencias
     *
     * @param registroInconsistencia registro que se quiere perssitir en bdat.
     */
    private void persistirRegistroInconsistencia(RegistroInconsistenciaTarjeta registroInconsistencia) {
        String firma = "persistirRegistroInconsistencia(RegistroInconsistenciaTarjeta registroInconsistencia)";
        logger.info("Inicio método " + firma);
        PersistirRegistroInconsistenciaTarjeta persistirRegistroInconsistencia = new PersistirRegistroInconsistenciaTarjeta(registroInconsistencia);
        persistirRegistroInconsistencia.execute();
        logger.info("Finaliza método " + firma);
    }

    private void registrarMovimientosCuentaAdminSubsidio(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion,
            Long idPersona, CasoMovimientoSubsidioEnum caso) {
        String firma = "registrarMovimientosCuentaAdminSubsidio(ResultadoReexpedicionBloqueoInDTO, Long, CasoMovimientoSubsidioEnum)";
        logger.info("Inicio método " + firma);

        if (CasoMovimientoSubsidioEnum.CASO_1.equals(caso)) {
            registrarMovimientosCaso1(resultadoReexpedicion, idPersona);
        } else if (CasoMovimientoSubsidioEnum.CASO_2.equals(caso)) {
            registrarMovimientosCaso2(resultadoReexpedicion, idPersona);
        }
        logger.info("Finaliza método " + firma);
    }

    private void registrarMovimientosCaso1(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, Long idPersona) {
        String firma = "registrarMovimientosCaso1(ResultadoReexpedicionBloqueoInDTO, Long)";
        logger.info("Inicio método " + firma);
        RegistrarMovimientosCasoUno registrarMovimientosCasoUno = new RegistrarMovimientosCasoUno(idPersona, resultadoReexpedicion);
        registrarMovimientosCasoUno.execute();
        logger.info("Finaliza método " + firma);
    }

    private void registrarMovimientosCaso2(ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion, Long idPersona) {
        String firma = "registrarMovimientosCaso2(ResultadoReexpedicionBloqueoInDTO, Long)";
        logger.info("Inicio método " + firma);
        System.out.println(resultadoReexpedicion.toString());
        RegistrarMovimientosCasoDos registrarMovimientosCasoDos = new RegistrarMovimientosCasoDos(idPersona, resultadoReexpedicion);
        registrarMovimientosCasoDos.execute();
        logger.info("Finaliza método " + firma);
    }

    /**
     * Método encargado de validar el nombre del archivo de retiro de las
     * tarjetas por parte de ANIBOL. Se valida que cumpla con la estructura del
     * nombre definida en el documento 'Servicios Web ANIBOL para CORE v2.6.4' y
     * que el archivo no haya sido validado previamente.
     *
     * @param fileName <code>String</code> Nombre del archivo cargado por el FTP
     * @return True si el nombre del archivo cumple con la esctructura indicada
     * y no ha sido procesado con anterioridad.
     */
    private ResultadoValidacionArchivoRetiroTerceroPagDTO validarNombreArchivoConsumoTerceroPagadorEfectivo(InformacionArchivoDTO informacionArchivoDTO, Long idConvenio,
            ResultadoValidacionArchivoRetiroTerceroPagDTO resultado, String nombreUsuario) {
        String firmaMetodo = "PagosSubsidioMonetarioCompositeBusiness.validarNombreArchivoConsumoTerceroPagadorEfectivo()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        ArchivoRetiroTerceroPagadorEfectivoDTO archivoConsumoDTO = new ArchivoRetiroTerceroPagadorEfectivoDTO();
        StringBuilder mensaje = new StringBuilder("");

        HashMap<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum> resultadoValidacion = new HashMap<ValidacionNombreArchivoEnum, ResultadoValidacionNombreArchivoEnum>();
        resultadoValidacion.put(ValidacionNombreArchivoEnum.PREFIJO, ResultadoValidacionNombreArchivoEnum.SUPERADA);
        resultadoValidacion.put(ValidacionNombreArchivoEnum.CODIGO_CONVENIO, ResultadoValidacionNombreArchivoEnum.SUPERADA);
        resultadoValidacion.put(ValidacionNombreArchivoEnum.FECHA_GENERACION_ARCHIVO, ResultadoValidacionNombreArchivoEnum.SUPERADA);
        resultadoValidacion.put(ValidacionNombreArchivoEnum.CONSECUTIVO, ResultadoValidacionNombreArchivoEnum.SUPERADA);
        resultadoValidacion.put(ValidacionNombreArchivoEnum.LONGITUD_NOMBRE_ARCHIVO, ResultadoValidacionNombreArchivoEnum.SUPERADA);

        ConvenioTercerPagadorDTO convenio = null;
        String fileName = informacionArchivoDTO.getFileName();
        logger.info("fileName: " + fileName);

        try {  	// valida codigo de convenio en pantallas con codigo en nombre de archivo
            String convenioStr = String.format("%03d", idConvenio);

            ConsultarConvenioTercero consultarConvenioTercero = new ConsultarConvenioTercero(idConvenio);
            consultarConvenioTercero.execute();
            convenio = consultarConvenioTercero.getResult();

            if (convenio == null) {
                mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.INCONSISTENCIA_CODIGO_CONVENIO.getDescripcion());
                mensaje.append("<br>");
                resultadoValidacion.put(ValidacionNombreArchivoEnum.CODIGO_CONVENIO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
            }
            if (fileName.length() != 22) {
                mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.LONGITUD_NOMBRE_ARCHIVO_INVALIDA.getDescripcion());
                mensaje.append("<br>");
                resultadoValidacion.put(ValidacionNombreArchivoEnum.LONGITUD_NOMBRE_ARCHIVO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
            } else {
                if (!fileName.substring(3, 6).equals(convenioStr)) {
                    mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.CONVENIO_NO_COINCIDE_NOMBRE_ARCHIVO.getDescripcion());
                    mensaje.append("<br>");
                    resultadoValidacion.put(ValidacionNombreArchivoEnum.CODIGO_CONVENIO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                }
                if (!fileName.substring(0, 2).matches("AC")) {
                    mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.PREFIJO_FIJO_NOMBRE_ARCHIVO_NO_VALIDO.getDescripcion());
                    mensaje.append("<br>");
                    resultadoValidacion.put(ValidacionNombreArchivoEnum.PREFIJO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                }
                if (!fileName.substring(3, 6).matches(ExpresionesRegularesConstants.VALOR_NUMERICO)) {
                    mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.INCONSISTENCIA_CODIGO_CONVENIO.getDescripcion());
                    mensaje.append("<br>");
                    resultadoValidacion.put(ValidacionNombreArchivoEnum.CODIGO_CONVENIO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                }
                logger.debug("fecha str: " + fileName.substring(7, 15));
                if (!fileName.substring(7, 15).matches("[0-9]{8}")) {
                    mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.FECHA_GENERACION_ARCHIVO_NO_VALIDO.getDescripcion());
                    mensaje.append("<br>");
                    resultadoValidacion.put(ValidacionNombreArchivoEnum.FECHA_GENERACION_ARCHIVO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                } else {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        format.setLenient(false);
                        Date fechaValida = format.parse(fileName.substring(7, 15));
                        logger.debug("fecha date: " + fechaValida.toString());
                    } catch (Exception ex) {
                        mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.FECHA_GENERACION_ARCHIVO_NO_VALIDO.getDescripcion());
                        mensaje.append("<br>");
                        resultadoValidacion.put(ValidacionNombreArchivoEnum.FECHA_GENERACION_ARCHIVO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                    }
                }
                logger.debug("fecha str: " + fileName.substring(16, 18));
                if (!fileName.substring(16, 18).matches(ExpresionesRegularesConstants.ALFANUMERICO2)) {

                    mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.INCONSISTENCIA_CONSECUTIVO_ARCHIVO.getDescripcion());
                    mensaje.append("<br>");
                    resultadoValidacion.put(ValidacionNombreArchivoEnum.CONSECUTIVO, ResultadoValidacionNombreArchivoEnum.NO_SUPERADA);
                }
            }
        } catch (Exception ex) {
            mensaje.append(TipoInconsistenciaArchivoConsumoTerceroPagEfectivoEnum.NOMBRE_ARCHIVO_NO_CONSISTENTE.getDescripcion());
            mensaje.append("<br>");

            archivoConsumoDTO.setEstadoArchivo(EstadoArchivoConsumoTerceroPagadorEfectivo.ANULADO);
        }

        // PERSISTE EL CARGUE DEL ARCHIVO
        archivoConsumoDTO.setFechaHoraCargue(new Date());
        archivoConsumoDTO.setEstadoArchivo(EstadoArchivoConsumoTerceroPagadorEfectivo.CARGADO);
        archivoConsumoDTO.setTipoCargue(TipoCargueArchivoConsumoAnibolEnum.MANUAL);
        archivoConsumoDTO.setNombreArchivo(fileName);
        archivoConsumoDTO.setIdDocumento(informacionArchivoDTO.getIdentificadorDocumento());
        archivoConsumoDTO.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
        archivoConsumoDTO.setUsuarioCargue(nombreUsuario);

        if (mensaje.length() == 0) {
            archivoConsumoDTO.setResultadoCargueArchivo(ResultadoCargueArchivoEnum.EXISTOSO);
            archivoConsumoDTO.setMotivo(MotivoCargueProcesoArchivoTerceroPagadorEnum.NOMBRE_ARCHIVO_CONSISTENTE);
            resultado.setMensaje(ResultadoCargueArchivoEnum.EXISTOSO.getDescripcion().replaceAll("Convenio", convenio.getNombreConvenio()));
            resultado.setEstadoValidacion(EstadoArchivoConsumoTerceroPagadorEfectivo.PROCESADO);
        } else {
            archivoConsumoDTO.setResultadoCargueArchivo(ResultadoCargueArchivoEnum.NO_EXISTOSO);
            archivoConsumoDTO.setMotivo(MotivoCargueProcesoArchivoTerceroPagadorEnum.NOMBRE_ARCHIVO_NO_CONSISTENTE);
            resultado.setMensaje(ResultadoCargueArchivoEnum.NO_EXISTOSO.getDescripcion() + "<br>" + mensaje.toString());
            resultado.setEstadoValidacion(EstadoArchivoConsumoTerceroPagadorEfectivo.ANULADO);
        }

        resultado.setIdArchivoRetiroTerceroPagadorEfectivo(registrarArchivoConsumoTerceroPagadorEfectivo(archivoConsumoDTO));
        PersistirValidacionesNombreArchivoTerceroPagador persisitirValidacionesNombre = new PersistirValidacionesNombreArchivoTerceroPagador(resultado.getIdArchivoRetiroTerceroPagadorEfectivo(),
                resultadoValidacion);
        persisitirValidacionesNombre.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.composite.service.PagosSubsidioMonetarioCompositeService#validarArchivoRetiro(java.lang.String,
     * com.asopagos.rest.security.dto.UserDTO, java.lang.String)
     */
    @Override
    public ResultadoValidacionArchivoRetiroTerceroPagDTO cargarArchivoConsumoTerceroPagadorEfectivo(String idArchivoRetiro, Long idConvenio, UserDTO userDTO) {

        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.validarArchivoRetiro(String idArchivoRetiro)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoRetiroTerceroPagDTO resultadoValidacion = new ResultadoValidacionArchivoRetiroTerceroPagDTO();

        resultadoValidacion.setIdArchivoRetiroTerceroPagador(idArchivoRetiro);
        resultadoValidacion.setIdConvenio(idConvenio);

        //se obtiene la información del archivo de retiro por medio de su id
        InformacionArchivoDTO informacionArchivoDTO = obtenerArchivoECM(idArchivoRetiro);
        /*
    	********************** MOCK PARA PROBAR ARCHIVO DE BLOQUEO *******************************************     
        
    	 File file = new File("D:\\AC_001_20200120_01.txt");        	 
    	 byte[] bArray = new byte[(int) file.length()];
    	 FileInputStream fis = null;
         try{
             fis = new FileInputStream(file);
             fis.read(bArray);
             fis.close();        
             
         }catch(IOException ioExp){
             ioExp.printStackTrace();
         }
    	 
    	InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO(); 
    	informacionArchivoDTO.setDataFile(bArray);    	
    	informacionArchivoDTO.setFileName("AC_001_20200120_01.txt");
    	informacionArchivoDTO.setIdentificadorDocumento("50708a75-0a99-41c6-8e85-7b9c8e6ff5a5.txt");
    	informacionArchivoDTO.setVersionDocumento("prueba");
    	
    	******************************* FIN MOCK *************************************************************
         */

        resultadoValidacion.setNombreArchivoRetiro(informacionArchivoDTO.getFileName());
        resultadoValidacion.setIdArchivoRetiroTerceroPagador(informacionArchivoDTO.getIdentificadorDocumento());
        // se valida existencia de archivo ya procesado
        if (consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre(informacionArchivoDTO.getFileName())) {
            resultadoValidacion.setMensaje("El archivo que desea cargar ya existe en el sistema. Por favor cargar un archivo que no haya sido procesado previamente");
            resultadoValidacion.setEstadoValidacion(EstadoArchivoConsumoTerceroPagadorEfectivo.ANULADO);

            return resultadoValidacion;
        };

        validarNombreArchivoConsumoTerceroPagadorEfectivo(informacionArchivoDTO, idConvenio, resultadoValidacion, userDTO.getNombreUsuario());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoValidacion;

        /*      

        String codigoCaja;
        try {
            codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        } catch (Exception e) {
            codigoCaja = null;
        }

         */
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * com.asopagos.subsidiomonetario.pagos.service.PagosSubsidioMonetarioService#registrarConvenioTercerPagador(com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO)
     */
    @Override
    public Long validarArchivoConsumoTerceroPagadorEfectivo(Long idConvenio, String idArchivoRetiro, Long IdArchivoRetiroTerceroPagadorEfectivo, UserDTO userDTO) {
        String firma = "PagosSubsidioMonetarioCompositeBusiness.validarArchivoConsumoTerceroPagadorEfectivo(ResultadoReexpedicionBloqueoInDTO, MotivoInconsistenciaEnum, EstadoResolucionInconsistenciaEnum, String)";
        logger.debug("Inicio método " + firma);
        //se obtiene la información del archivo de retiro por medio de su id
        try {
            InformacionArchivoDTO informacionArchivoDTO = obtenerArchivoECM(idArchivoRetiro);
            if (userDTO == null || userDTO.getNombreUsuario() == null) {
                userDTO = new UserDTO();
                userDTO.setNombreUsuario("prueba");
            }

            //********************** MOCK PARA PROBAR ARCHIVO DE BLOQUEO *******************************************
            /*
	       
			
	    	 File file = new File("D:\\AC_001_20200120_01.txt");        	 
	    	 byte[] bArray = new byte[(int) file.length()];
	    	 FileInputStream fis = null;
	         try{
	             fis = new FileInputStream(file);
	             fis.read(bArray);
	             fis.close();        
	             
	         }catch(IOException ioExp){
	             ioExp.printStackTrace();
	         }
	    	 
	    	InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO(); 
	    	informacionArchivoDTO.setDataFile(bArray);    	
	    	informacionArchivoDTO.setFileName("AC_001_20200120_01.txt");
	    	informacionArchivoDTO.setIdentificadorDocumento("50708a75-0a99-41c6-8e85-7b9c8e6ff5a5.txt");
	    	informacionArchivoDTO.setVersionDocumento("prueba");
             */
            //******************************* FIN MOCK *************************************************************
            ValidarArchivoConsumoTerceroPagadorEfectivo validarArchivoConsumoTerceroPagadorEfectivo = new ValidarArchivoConsumoTerceroPagadorEfectivo(idConvenio, userDTO.getNombreUsuario(), IdArchivoRetiroTerceroPagadorEfectivo, informacionArchivoDTO);
            validarArchivoConsumoTerceroPagadorEfectivo.execute();
            validarArchivoConsumoTerceroPagadorEfectivo.getResult();

            // Se efectuan los retiros de acuerdo a los resultados de la tabla TempArchivoRetiroTerceroPagadorEfectivo
            ConsultarTempArchivoRetiroTerceroPagadorEfectivo tempArchivoRetiroTerceros = new ConsultarTempArchivoRetiroTerceroPagadorEfectivo(IdArchivoRetiroTerceroPagadorEfectivo, idConvenio);
            tempArchivoRetiroTerceros.execute();
            List<TempArchivoRetiroTerceroPagadorEfectivoDTO> listaRegistrosArchivoCandidatos = tempArchivoRetiroTerceros.getResult();

            for (TempArchivoRetiroTerceroPagadorEfectivoDTO registroCandidatoRetiro : listaRegistrosArchivoCandidatos) {
                Map<String, String> respuesta = solicitarRetiroConfirmarValorEntregadoSubsidio(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(registroCandidatoRetiro.getTipoIdentificacionAdmin()),
                        registroCandidatoRetiro.getNumeroIdentificacionAdmin(), new BigDecimal(registroCandidatoRetiro.getValorTransaccion()),
                        userDTO.getNombreUsuario(), registroCandidatoRetiro.getFechaHoraTransaccion().getTime(), registroCandidatoRetiro.getIdTransaccion(),
                        registroCandidatoRetiro.getCodigoDepartamento(), registroCandidatoRetiro.getCodigoMunicipio(), userDTO.getNombreUsuario(),
                        registroCandidatoRetiro.getSitioCobro(), idConvenio);

                if (respuesta.containsKey("error")) {
                    //registroCandidatoRetiro.setNombreCampo(CamposArchivoConstants.VALOR_TRANSACCION);
                    if (respuesta.get("error").equals("Identificador de transacción ya está registrado en el sistema")) {
                        registroCandidatoRetiro.setNombreCampo("Id transacción");
                    } else {
                        registroCandidatoRetiro.setNombreCampo("valorTransaccion");
                    }

                    registroCandidatoRetiro.setResultado(ReultadoValidacionCampoEnum.NO_EXITOSO);
                    registroCandidatoRetiro.setMotivo(respuesta.get("error"));
                    ActualizarTempArchivoRetiroTerceroPagador act = new ActualizarTempArchivoRetiroTerceroPagador(registroCandidatoRetiro);
                    act.execute();
                }
            }

            // se ejecuta la creación de campos lion para consola
            InsertRestuladosValidacionCargaManualRetiroTerceroPag in = new InsertRestuladosValidacionCargaManualRetiroTerceroPag(idConvenio,
                    userDTO.getNombreUsuario(), IdArchivoRetiroTerceroPagadorEfectivo, informacionArchivoDTO);
            in.execute();

            // Se pasa el estado del ArchivoTerceroPagadorEfectivo a Procesado
            ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo actualizarEstadoProcesado = new ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(IdArchivoRetiroTerceroPagadorEfectivo, EstadoArchivoConsumoTerceroPagadorEfectivo.PROCESADO);
            actualizarEstadoProcesado.execute();

        } catch (Exception e) {
            ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo actualizarEstadoProcesado = new ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo(IdArchivoRetiroTerceroPagadorEfectivo, EstadoArchivoConsumoTerceroPagadorEfectivo.PROCESADO_CON_INCONSISTENCIAS);
            actualizarEstadoProcesado.execute();
            //throw new TechnicalException(MensajesGeneralConstants.ERROR_EJECUCION_PROCEDIMIENTO_ALMACENADO, e);
            e.printStackTrace();
        }

        return 1L;
    }

    private static List<List<Long>> particionarLista(List<Long> list, final long l) {
        List<List<Long>> parts = new ArrayList<List<Long>>();
        final int N = list.size();
        for (int i = 0; i < N; i += l) {
            parts.add(new ArrayList<Long>(
                    list.subList(i, (int) Math.min(N, i + l)))
            );
        }
        return parts;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public BigDecimal obtenerValorTotalSubsidiosAnular(String tipo) {
        String firma = "obtenerValorTotalSubsidiosAnular(String tipo)";
        logger.debug("Inicio método " + firma);
        BigDecimal valorTotalSubsidiosAnular = null;
        try {
            ConsultarValorTotalSubsidiosAnular consultarValorTotalSubsidiosAnular = new ConsultarValorTotalSubsidiosAnular(tipo);
            consultarValorTotalSubsidiosAnular.execute();
            valorTotalSubsidiosAnular = consultarValorTotalSubsidiosAnular.getResult();
        } catch (Exception e) {

        }
        logger.debug("Finaliza método " + firma);
        return valorTotalSubsidiosAnular;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<SubsidioAnularDTO> resumenListadoSubsidiosAnular(UriInfo uriInfo, HttpServletResponse response, String tipo) {
        String firma = "resumenListadoSubsidiosAnular(UriInfo uriInfo, HttpServletResponse response, String tipo)";
        logger.debug("Inicio método " + firma);
        List<SubsidioAnularDTO> subsidiosAnularDTO = null;
        try {
            Boolean firstRequest = Boolean.parseBoolean(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.FIRST_REQUEST.getValor()).get(0));
            Integer offset = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.OFFSET.getValor()).get(0));
            Integer filter = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.FILTER.getValor()).get(0));
            String orderBy = uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()) != null ? uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()).get(0) : "";
            Integer limit = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.LIMIT.getValor()).get(0));

            GenerarResumenListadoSubsidiosAnular generarResumenListadoSubsidiosAnular = new GenerarResumenListadoSubsidiosAnular(offset, tipo, limit, orderBy, filter, firstRequest);
            generarResumenListadoSubsidiosAnular.execute();
            ResumenListadoSubsidiosAnularDTO resumenListadoSubsidiosAnularDTO = generarResumenListadoSubsidiosAnular.getResult();
            subsidiosAnularDTO = resumenListadoSubsidiosAnularDTO.getSubsidiosAnularDTO();
            response.addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(), resumenListadoSubsidiosAnularDTO.getTotalRegistros());
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return subsidiosAnularDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<SubsidioMonetarioPrescribirAnularFechaDTO> listadoSubsidiosAnular(UriInfo uriInfo, HttpServletResponse response, String tipo,String numeroIdentificacionAdminSub) {
        String firma = "listadoSubsidiosAnular(UriInfo uriInfo, HttpServletResponse response, String tipo)";
        logger.debug("Inicio método " + firma);
        List<SubsidioMonetarioPrescribirAnularFechaDTO> subsidioMonetarioPrescribirAnularFechaDTO = null;
        try {
            Boolean firstRequest = Boolean.parseBoolean(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.FIRST_REQUEST.getValor()).get(0));
            Integer offset = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.OFFSET.getValor()).get(0));
            String orderBy = uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()) != null ? uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.ORDER_BY_QPARAM.getValor()).get(0) : "";
            Integer limit = Integer.parseInt(uriInfo.getQueryParameters().get(PaginationQueryParamsEnum.LIMIT.getValor()).get(0));
            GenerarlistadoSubsidiosAnular generarlistadoSubsidiosAnular = new GenerarlistadoSubsidiosAnular(offset, tipo, limit, orderBy, firstRequest,numeroIdentificacionAdminSub);
            generarlistadoSubsidiosAnular.execute();
            ListadoSubsidiosAnularDTO listadoSubsidiosAnularDTO = generarlistadoSubsidiosAnular.getResult();
            subsidioMonetarioPrescribirAnularFechaDTO = listadoSubsidiosAnularDTO.getSubsidiosMonetariosPrescribirAnularFechaDTO();
            response.addHeader(PaginationQueryParamsEnum.TOTAL_RECORDS.getValor(), listadoSubsidiosAnularDTO.getTotalRegistros());
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return subsidioMonetarioPrescribirAnularFechaDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Response exportarListadoSubsidiosAnular(UriInfo uri, HttpServletResponse response, Integer limit, String tipo) {
        String firma = "exportarListadoSubsidiosAnular (UriInfo uri, HttpServletResponse response, Integer limit, String tipo)";
        logger.debug("Inicio método " + firma);
        Response.ResponseBuilder responseBld = null;
        try {
            GenerarExcelListadoSubsidiosAnular generarExcelListadoSubsidiosAnular = new GenerarExcelListadoSubsidiosAnular(tipo, limit);
            generarExcelListadoSubsidiosAnular.execute();
            byte[] dataReporte = generarExcelListadoSubsidiosAnular.getResult();
            responseBld = Response.ok(new ByteArrayInputStream(dataReporte));
            responseBld.header("Content-Type", "application/vnd.ms-excel" + ";charset=utf-8");
            responseBld.header("Content-Disposition", "attachment; filename=" + "Anulación por vencimiento");
        } catch (Exception e) {
            logger.error("Error en el servicio " + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza método " + firma);
        return responseBld.build();
    }

    @Asynchronous
    @Override
    public void procesarResultadoDispersionSubsidioMonetario(ResultadoDispersionAnibolDTO respuestaAnibol) {
        String firmaMetodo = "procesarResultadoDispersionSubsidioMonetario(ResultadoDispersionAnibolDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //Valida si alguno de los registros tiene como estado 'RTA_REDEBAN', lo que significaría que se generó el archivo de pagos y redeban generó una respuesta a partir de este
        boolean estadoRespuestaAnibol = false;

        //instancias de clases utilitarias para transformar los datos que vienen del bus de anibol
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<Long>>() {
        }.getType();

        logger.info("ResultadoDispersionAnibolDTO respuestaAnibol: " + respuestaAnibol.getIdSolicitud() + " - cantidad de registros: " + respuestaAnibol.getListadoResultadosProcesamiento().size());

        //Obtenemos el registro de la solicitud de dispersion
        ConsultarRegistroSolicitudDispersionSubsidioMonetario registrosAnibol = new ConsultarRegistroSolicitudDispersionSubsidioMonetario(Long.parseLong(respuestaAnibol.getIdSolicitud()));
        registrosAnibol.execute();

        for (RegistroSolicitudAnibol registroAnibol : registrosAnibol.getResult()) {
            //GLPI:56847, 56849
            if (respuestaAnibol.getListadoResultadosProcesamiento().size() > 0) {
                for (ResultadoProcesamientoDTO resultadoAnibol : respuestaAnibol.getListadoResultadosProcesamiento()) {
                    if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado()) || ResultadoProcesoAnibolEnum.RTA_ANIBOL_VALIDACION.name().equals(resultadoAnibol.getEstado())) { 
                        estadoRespuestaAnibol = true;
                        if (!resultadoAnibol.isExitoso()) {
                            //Obtenemos la informacion del admin subsidio
                            ConsultaCuentaAdmonSubsidioDispersionSubsidioMonetario consultaCuentaAdmonSubsidio = new ConsultaCuentaAdmonSubsidioDispersionSubsidioMonetario(Long.parseLong(respuestaAnibol.getIdSolicitud()), resultadoAnibol.getNumeroTarjeta());
                            consultaCuentaAdmonSubsidio.execute();

                            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();
                            cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(consultaCuentaAdmonSubsidio.getResult());

                            //Guarda el registro en la bandeja de errores
                            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
                        }
                    }
                }
            } else {
                estadoRespuestaAnibol = true;
            }

            if (estadoRespuestaAnibol) {
                //Actualizamos los registros procesados exitoso
                actualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario(Long.parseLong(respuestaAnibol.getIdSolicitud()));

                //Actualizamos el estado de la solicitud de la dispersion de subsidio monetario
                actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Asynchronous
    @Override
    public void procesarResultadoAnulacionSubsidioMonetario(ResultadoDispersionAnibolDTO respuestaAnibol) {
        String firmaMetodo = "procesarResultadoAnulacionSubsidioMonetario(ResultadoDispersionAnibolDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        //Valida si alguno de los registros tiene como estado 'RTA_REDEBAN', lo que significaría que se generó el archivo de pagos y redeban generó una respuesta a partir de este
        boolean estadoRespuestaAnibol = false;

        //instancias de clases utilitarias para transformar los datos que vienen del bus de anibol
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<Long>>() {
        }.getType();

        Type listType1 = new TypeToken<ArrayList<String>>() {
        }.getType();

        logger.info("ResultadoDispersionAnibolDTO ANULACION respuestaAnibol: " + respuestaAnibol.getIdSolicitud() + " - cantidad de registros: " + respuestaAnibol.getListadoResultadosProcesamiento().size());

        //Obtenemos el registro de la solicitud de dispersion
        ConsultarRegistroSolicitudAnulacionSubsidioMonetario registrosAnibol = new ConsultarRegistroSolicitudAnulacionSubsidioMonetario(Long.parseLong(respuestaAnibol.getIdSolicitud()));
        registrosAnibol.execute();

        for (RegistroSolicitudAnibol registroAnibol : registrosAnibol.getResult()) {
            //TODO se creará el campo en la tabla de registroSolicitudAnibol para el id de la cuenta de admin Subsidio
            Long idCuentaAdminSubsidio = registroAnibol.getSolicitudLiquidacionSubsidio();

            ConsultarCuentaAdmonSubsidioDTO consultarCuentaAdmin = new ConsultarCuentaAdmonSubsidioDTO(idCuentaAdminSubsidio);
            consultarCuentaAdmin.execute();
            CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = consultarCuentaAdmin.getResult();

            List<CuentaAdministradorSubsidio> cuentas = new ArrayList<>();
            cuentas.add(cuentaAdministradorSubsidioDTO.convertToEntity());
            Map<String, CuentaAdministradorSubsidio> tarjetasIdCuentaAdministradorSubsidio = cuentas.stream()
                    .collect(Collectors.toMap(CuentaAdministradorSubsidio::getNumeroTarjetaAdmonSubsidio, Function.identity()));

            List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
            //GLPI:56847, 56849
            if (respuestaAnibol.getListadoResultadosProcesamiento().size() > 0) {
                for (ResultadoProcesamientoDTO resultadoAnibol : respuestaAnibol.getListadoResultadosProcesamiento()) {
                    if (ResultadoProcesoAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
                        if (resultadoAnibol.isExitoso()) {
                            StringBuilder salida = new StringBuilder();
                            
                            List<Long> listaIdsDetallesSubsidio = gson.fromJson(registroAnibol.getParametrosIN(), listType);
                            List<String> listaMotivosAnulacion = gson.fromJson(registroAnibol.getMotivoAnulacion(), listType1);
    
                            List<DetalleSubsidioAsignadoDTO> listaDetallesDTOAnular = consultarDetallesSubsidio(listaIdsDetallesSubsidio);

                            int pos = 0;
                            // Actualizamos el motivo de anulacion de los detalles antes de enviarlos a actualizar en BD a partir del motivo de anulacion asignado
                            for(DetalleSubsidioAsignadoDTO detalle : listaDetallesDTOAnular)
                            {
                                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.obtenerMotivoAnulacionSubsidioAsignadoEnum(listaMotivosAnulacion.get(pos)));
                                pos++;
                            }
    
                            SubsidioAnulacionDTO subsidioAnulacionDTO = new SubsidioAnulacionDTO(cuentaAdministradorSubsidioDTO, listaDetallesDTOAnular);

                            //se realiza el proceso de anulación sin reemplazo del detalle con su respectivo abono
                            //subsidioAnulacionDTO.setUsuarioDTO(userDTO);
    
                            Long nuevoAbono = anularSubsidioMonetarioSinReemplazoH(subsidioAnulacionDTO, true);
    
                            List<Long> listaNuevosAbonos = new ArrayList<>();
                            listaNuevosAbonos.add(nuevoAbono);
                            dispersarAbonosOrigenAnulacion(listaNuevosAbonos);
                            actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
    
                        } else {
                            cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
                                    tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
                            );
                            cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
                        }
                    }
                    if (ResultadoProcesoAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {
                        if (!resultadoAnibol.isExitoso()) {
                            cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
                                    tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
                            );
                            cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
    
                        }
                    }
                }
                
                for (CuentaAdministradorSubsidioDTO cuenta : cuentasTarjetaInactivaError) {
                    registrarTransaccionFallida(cuenta);
                    actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
                }

            } else{
                logger.info("No hay solicitudes por procesar");
            }

        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    //COMUNICADOS PRESCRIPCION CUOTA MONETARIO GLPI 45388
    private void enviarComunicadoPrescripcion(EtiquetaPlantillaComunicadoEnum plantillaComunicado, Long idCuentaAdmonSubsidio) {
        logger.debug("Inicio de método enviarComunicadoPrescripcion");
        Boolean envioExitoso = Boolean.FALSE;
        Boolean prescripcionBandera = Boolean.FALSE;

        try {
            List<NotificacionParametrizadaDTO> notificaciones = new ArrayList<>();
            // Genera y guarda el comunicado
            //   if (plantillaComunicado.equals(EtiquetaPlantillaComunicadoEnum.COM_SUB_PRE_PAG_TRA))
            //       prescripcionBandera = true;
            logger.debug("Inicia cae siempre :prescripcionBandera enviarComunicadoPrescripcion :: " + prescripcionBandera);
            NotificacionParametrizadaDTO asignacionTemporal = almacenarComunicado(plantillaComunicado, idCuentaAdmonSubsidio);
            //esta es la respuesta -> asignacionTemporal setIdSolicitud el campo se utiliza para guardar el idCuentaAdmonSubsidio
            asignacionTemporal.setIdSolicitud(idCuentaAdmonSubsidio);
            notificaciones.add(asignacionTemporal);
            //NOTIFICACION
            if (!notificaciones.isEmpty()) {
                logger.info("Entra if notificaciones diferente de vacio:: " + notificaciones);
                envioExitoso = envioExitosoComunicados(notificaciones);
            }
        } catch (Exception e) {
            logger.error("No se pudo completar correctamente el envío de comunicados, parametros faltantes para el componente de comunicados.", e);
            envioExitoso = Boolean.FALSE;
        } finally {
            logger.info("finally aportanteAccionCobroDTO :: ");
            if (envioExitoso) { // Registro de bitácora: para envío exitoso por medio electrónico, se registra envío y entrega
                logger.info("finally exitoso ::");
            } else {
                logger.info("finally no exitoso ::");

            }

        }
        logger.debug("Fin de método enviarComunicadoAsignacion");
    }

    private NotificacionParametrizadaDTO almacenarComunicado(EtiquetaPlantillaComunicadoEnum plantillaComunicado, Long idCuentaAdmonSubsidio) {
        logger.debug("Inicio de método almacenarComunicado");

        // Genera y guarda el comunicado en el ECM
        NotificacionParametrizadaDTO notificacionParametrizadaDTO = construirComunicado(plantillaComunicado, idCuentaAdmonSubsidio);
        InformacionArchivoDTO informacionArchivo = guardarObtenerInfoArchivoComunicado(plantillaComunicado, idCuentaAdmonSubsidio);

        List<String> idsAdjuntos = new ArrayList<>();
        idsAdjuntos.add(informacionArchivo.getIdentificadorDocumento());
        notificacionParametrizadaDTO.setArchivosAdjuntosIds(idsAdjuntos);
        notificacionParametrizadaDTO.setEnvioExitoso(Boolean.TRUE);

        return notificacionParametrizadaDTO;
    }

    private NotificacionParametrizadaDTO construirComunicado(EtiquetaPlantillaComunicadoEnum plantillaComunicado, Long idCuentaAdmonSubsidio) {
        logger.debug("Inicio de método construirComunicado");
        NotificacionParametrizadaDTO notificacionDTO = new NotificacionParametrizadaDTO();
        notificacionDTO.setEtiquetaPlantillaComunicado(plantillaComunicado);
        //notificacionDTO.setProcesoEvento(tipoTransaccion.getProceso().name());
        //notificacionDTO.setTipoTx(tipoTransaccion);

        ParametrosComunicadoDTO parametrosDTO = new ParametrosComunicadoDTO();
        Map<String, String> params = new HashMap<>();

        Map<String, Object> parametros = new HashMap<>();

        List<AutorizacionEnvioComunicadoDTO> correos = obtenerRolesDestinatarios(idCuentaAdmonSubsidio);
        List<String> correosDestinatarios = new ArrayList<>();
        for (AutorizacionEnvioComunicadoDTO autorizacionEnvioComunicadoDTO : correos) {
            correosDestinatarios.add(autorizacionEnvioComunicadoDTO.getDestinatario());
        }
        notificacionDTO.setIdPersona(correos.get(0) != null ? correos.get(0).getIdPersona() : null);
        notificacionDTO.setDestinatarioTO(correosDestinatarios);
        notificacionDTO.setReplantearDestinatarioTO(true);
        notificacionDTO.setAutorizaEnvio(correos.get(0) != null ? correos.get(0).getAutorizaEnvio() : Boolean.FALSE);

        logger.debug("Fin de método construirComunicado");
        return notificacionDTO;
    }

    private List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatarios(Long idCuentaAdmonSubsidio) {
        try {
            logger.debug("Inicio de método obtenerRolesDestinatarios");
            ObtenerRolesDestinatariosPrescripcion correosService = new ObtenerRolesDestinatariosPrescripcion(idCuentaAdmonSubsidio);
            correosService.execute();
            List<AutorizacionEnvioComunicadoDTO> resultado = correosService.getResult();

            for (AutorizacionEnvioComunicadoDTO ciclo : resultado) {
                logger.info("**__**posicion email: " + ciclo.getDestinatario());
                logger.info("**__**posicion autorizaenvio : " + ciclo.getAutorizaEnvio());
            }

            logger.debug("Fin de método obtenerRolesDestinatarios");
            return resultado;
        } catch (Exception e) {
            logger.error("Fin de método obtenerRolesDestinatarios:Error técnico inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    private InformacionArchivoDTO guardarObtenerInfoArchivoComunicado(EtiquetaPlantillaComunicadoEnum plantilla,
            Long idCuentaAdmonSubsidio) {
        logger.debug("Inicio de método guardarObtenerComunicadoECM");
        GuardarObtenerComunicadoPrescripcion service = new GuardarObtenerComunicadoPrescripcion(idCuentaAdmonSubsidio, plantilla);
        service.execute();
        logger.debug("Fin de método guardarObtenerComunicadoECM");
        return service.getResult();
    }

    private Boolean envioExitosoComunicados(List<NotificacionParametrizadaDTO> notificaciones) {
        EnvioExitosoComunicados envioComunicados = new EnvioExitosoComunicados(notificaciones);
        envioComunicados.execute(); 
        return envioComunicados.getResult();
    }

    @Override
    public void ejecucionAvisoPrescripcionSubsidio() {

        List<Long> listadoAviso =  new ArrayList<>();
        //primer aviso
        listadoAviso = consultarIdsAdministradoresSubsidio("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_PRIMER_AVISO");
        if(!listadoAviso.isEmpty())
        enviarComunicadoPrescricionAvisos(EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA, listadoAviso);
        //segundo aviso 
        listadoAviso.removeAll(listadoAviso);
        listadoAviso = consultarIdsAdministradoresSubsidio("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_SEGUNDO_AVISO");
        if(!listadoAviso.isEmpty())
        enviarComunicadoPrescricionAvisos(EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA, listadoAviso);
        //tercer aviso 
        listadoAviso.removeAll(listadoAviso); 
        listadoAviso = consultarIdsAdministradoresSubsidio("ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_TERCER_AVISO");
        if(!listadoAviso.isEmpty())
        enviarComunicadoPrescricionAvisos(EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA, listadoAviso);

    }

    public void enviarComunicadoPrescricionAvisos(EtiquetaPlantillaComunicadoEnum etiqueta, List<Long> arrayIds) {
        for (Long idadmin : arrayIds) {
            enviarComunicadoPrescripcion(etiqueta, idadmin);
        }
    }

    public List<Long> consultarIdsAdministradoresSubsidio(String parametro) {
        logger.info("**__**CORTE DEVELOP  CON QA/TEST "+parametro);
        ObtenerIdsAbonosPrescripcion listado = new ObtenerIdsAbonosPrescripcion(parametro);
        listado.execute();
        logger.info("Fin de método guardarObtenerComunicadoECM");
        return listado.getResult();
    }
        //FIN COMUNICADOS PRESCRIPCION CUOTA MONETARIO GLPI 45388
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> registrarCambioMedioDePagoSubsidioArchivo(Long idAdminSubsidio,RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO) {
        String firmaServicio = "PagosSubsidioMonetarioCompositeBusiness.registrarCambioMedioDePagoSubsidioArchivo(RegistroCambioMedioDePagoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> listaIdNuevosAbonos = new ArrayList<>();
        //abonos que serán anulados
        List<Long> lstIdCuentasAnulacion = new ArrayList<>();
        //detalles que serán anulados
        List<DetalleSubsidioAsignadoDTO> lstDetallesAnulacion = new ArrayList<>();

        //si el medio de pago es EFECTIVO y el id es nulo, es porque el medio de pago EFECTIVO para ese administrador
        //no existe y se procede a crearlo.
        if (TipoMedioDePagoEnum.EFECTIVO.equals(registroCambioMedioDePagoDTO.getMedioDePagoModelo().getTipoMedioDePago())
                && registroCambioMedioDePagoDTO.getMedioDePagoModelo().getIdMedioDePago() == null) {
            Long idMedioDePagoEfectivo = registrarMedioDePago(registroCambioMedioDePagoDTO.getMedioDePagoModelo());
            registroCambioMedioDePagoDTO.getMedioDePagoModelo().setIdMedioDePago(idMedioDePagoEfectivo);
        }

                    
        for (CuentaAdministradorSubsidioDTO cuenta : registroCambioMedioDePagoDTO.getListaRegistrosAbonos()) {
            //se agrega el id de la cuenta a ser anulada.
            lstIdCuentasAnulacion.add(cuenta.getIdCuentaAdministradorSubsidio());
            
            List<DetalleSubsidioAsignadoDTO> listaDetalles = obtenerDetallesSubsidiosAsignadosAsociadosAbono(
                    cuenta.getIdCuentaAdministradorSubsidio());
            
            //a cada detalle se le pone el motivo de anulación
            for (DetalleSubsidioAsignadoDTO detalle : listaDetalles) {
                logger.info("Entro al for: " + detalle.getNumeroIdentificacionTitularCuentaAdminSubsidio());
                detalle.setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum.CAMBIO_MEDIO_DE_PAGO);
                lstDetallesAnulacion.add(detalle);
            }
        }
        
        Map<Long, MedioDePagoModeloDTO> mediosDePagos = new HashMap<>();
        for (Long idCuenta : lstIdCuentasAnulacion) {
            mediosDePagos.put(idCuenta, registroCambioMedioDePagoDTO.getMedioDePagoModelo());
        }
        //se crea el objeto para anular.
        AbonoAnuladoDetalleAnuladoDTO abonoAnuladoDetalleAnuladoDTO = new AbonoAnuladoDetalleAnuladoDTO();
        abonoAnuladoDetalleAnuladoDTO.setListaIdsCuentasAdmonSubsidios(lstIdCuentasAnulacion);
        abonoAnuladoDetalleAnuladoDTO.setListaAnularDetallesDTO(lstDetallesAnulacion);
        
        //se ejecuta la anulación con reemplazo y se envia el medio de pago por el cual se cambiarán los abonos
        listaIdNuevosAbonos = ejecutarAnulacionSubsidioMonetarioConReemplazo(abonoAnuladoDetalleAnuladoDTO, mediosDePagos);
        
        Object[] listaAux = listaIdNuevosAbonos.toArray().clone();
        @SuppressWarnings("rawtypes")
        List lista = Arrays.asList(listaAux);
        //se realiza la dispersión

        dispersarAbonosOrigenCambioMedioPago(listaIdNuevosAbonos);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        logger.info("finaliza " + firmaServicio );

        return lista;
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

    private void reemplazarMedioDePagoGrupoFamiliar(List<Long> idsGruposFamiliar, Long idMedioDePagoTarjeta){
        ReemplazarMedioDePagoGrupoFamiliar reemplazarMedioDePagoGrupoFamiliarService = new ReemplazarMedioDePagoGrupoFamiliar(idsGruposFamiliar,idMedioDePagoTarjeta);
        reemplazarMedioDePagoGrupoFamiliarService.execute();
    }

    private void registrarActualizacionTarjetaGrupoFamiliar(Long idsGruposFamiliar, MedioDePagoModeloDTO medioDePagoModeloDTO){
        RegistrarActualizacionTarjetaGrupoFamiliar registrarActualizacionTarjetaGrupoFamiliarService = new RegistrarActualizacionTarjetaGrupoFamiliar(idsGruposFamiliar,medioDePagoModeloDTO);
        registrarActualizacionTarjetaGrupoFamiliarService.execute();
    }

}
