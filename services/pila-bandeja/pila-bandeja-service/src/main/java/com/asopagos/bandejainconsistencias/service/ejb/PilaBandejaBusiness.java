package com.asopagos.bandejainconsistencias.service.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.asopagos.bandejainconsistencias.dto.ActivosCorreccionIdAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.BandejaEmpleadorCeroTrabajadoresDTO;
import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaRespuestaDTO;
import com.asopagos.bandejainconsistencias.dto.CreacionAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.CriteriosDTO;
import com.asopagos.bandejainconsistencias.dto.DatosBandejaTransitoriaDTO;
import com.asopagos.bandejainconsistencias.dto.IdentificadorDocumentoDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.bandejainconsistencias.dto.ParametrosCreacionAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.PreparacionAprobacion399DTO;
import com.asopagos.bandejainconsistencias.dto.ResultadoAprobacionCambioIdentificacionDTO;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloPILA;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloStaging;
import com.asopagos.bandejainconsistencias.service.PilaBandejaService;
import com.asopagos.bandejainconsistencias.util.FuncionesUtilitarias;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.EmpAporPendientesPorAfiliarDTO;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import com.asopagos.dto.modelo.DepartamentoModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.ExcepcionNovedadPilaModeloDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.dto.pila.DetalleTablaAportanteDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoAPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoARegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionCorreccionPilaEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que posee la implementacion de las HU 392-411-403-404<br/>
 * <b>Módulo:</b> Asopagos - HU 392-411-403-404 <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> Andres F. Buitrago</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */
@Stateless
public class PilaBandejaBusiness implements PilaBandejaService {

    /** Inject del EJB para consultas en modelo PILA */
    @Inject
    private IConsultasModeloPILA consultasPila;

    /** Inject del EJB para consultas en modelo Staging */
    @Inject
    private IConsultasModeloStaging consultasStaging;

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasCore;

    private static final ILogger logger = LogManager.getLogger(PilaBandejaService.class);

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#consultarArchivosInconsistentesResumen(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String,
     *      com.asopagos.enumeraciones.aportes.TipoOperadorEnum,
     *      java.lang.Short, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<InconsistenciaDTO> consultarArchivosInconsistentesResumen(TipoIdentificacionEnum tipoIdentificacion, String numeroPlanilla,
            Long fechaInicio, Long fechaFin, String numeroIdentificacion, TipoOperadorEnum operador, Short digitoVerificacion, String bloqueValidacion, Boolean ocultarBlq5) {
        String firmaMetodo = "consultarArchivosInconsistentesResumen(TipoIdentificacionEnum, String, Long, Long, String, TipoOperadorEnum, Short, String, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        if(ocultarBlq5 == null) {
        	ocultarBlq5 = false;
        }

        List<InconsistenciaDTO> result = consultasPila.consultarArchivosInconsistentesResumen(tipoIdentificacion, numeroPlanilla,
                fechaInicio, fechaFin, numeroIdentificacion, operador, digitoVerificacion, bloqueValidacion, ocultarBlq5);

        // se agregan los datos faltantes de los resultados (tipo y número de ID de aportante y/o tipo de operador)
        result = completarDatosBusquedaInconsistencia(result);

        // Buscar la razon social de cada aportante
        result = consultasPila.consultarRazonSocialAportantes(result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método que agrega los datos faltantes de los resultados (número de ID de aportante y/o tipo de operador)
     * @param result
     *        Listado de resultados
     * @return <b>List<InconsistenciaDTO></b>
     *         Listado de resultados actualizado
     */
    private List<InconsistenciaDTO> completarDatosBusquedaInconsistencia(List<InconsistenciaDTO> result) {
        String firmaMetodo = "PilaBandejaBusiness.completarDatosBusquedaInconsistencia(List<InconsistenciaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaDTO> resultTemp = result;

        for (InconsistenciaDTO inconsistenciaDTO : resultTemp) {
            if (inconsistenciaDTO.getNumeroIdAportante() == null
                    && !PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(inconsistenciaDTO.getTipoArchivo().getPerfilArchivo())) {
                inconsistenciaDTO
                        .setNumeroIdAportante((String) FuncionesUtilitarias.obtenerCampoNombreArchivo(inconsistenciaDTO.getTipoArchivo(),
                                CamposNombreArchivoEnum.IDENTIFICACION_APORTANTE_OI, inconsistenciaDTO.getNombreArchivo()));
            }

            if (inconsistenciaDTO.getTipoIdentificacion() == null
                    && !PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(inconsistenciaDTO.getTipoArchivo().getPerfilArchivo())) {

                String tipoId = (String) FuncionesUtilitarias.obtenerCampoNombreArchivo(inconsistenciaDTO.getTipoArchivo(),
                        CamposNombreArchivoEnum.TIPO_DOCUMENTO_APORTANTE_OI, inconsistenciaDTO.getNombreArchivo());

                inconsistenciaDTO.setTipoIdentificacion(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId));
            }

            if (inconsistenciaDTO.getTipoOperador() == null
                    && !PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(inconsistenciaDTO.getTipoArchivo().getPerfilArchivo())) {
                inconsistenciaDTO.setTipoOperador(TipoOperadorEnum.OPERADOR_INFORMACION);
            }
            else if (inconsistenciaDTO.getTipoOperador() == null) {
                inconsistenciaDTO.setTipoOperador(TipoOperadorEnum.OPERADOR_FINANCIERO);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#accionBandejaInconsistencias(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public List<TipoInconsistenciasEnum> accionBandejaInconsistencias(InconsistenciaDTO inconsistencia) {
        String firmaMetodo = "accionBandejaInconsistencias(InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoInconsistenciasEnum> result = consultasPila.accionBandejaInconsistencias(inconsistencia);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#accionBandejaDetalleInconsistencias(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<InconsistenciaDTO> accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencia,
            TipoInconsistenciasEnum tipoInconsistencia, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "PilaBandejaBusiness.accionBandejaDetalleInconsistencias(InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        // se definen los bloques a consultar por tipo de inconsistencia y tipo de archivo
        List<BloqueValidacionEnum> bloquesConsulta = new ArrayList<>();
        Boolean conciliacion = Boolean.FALSE;
        
        if(TipoArchivoPilaEnum.ARCHIVO_OF.equals(inconsistencia.getTipoArchivo())){
            switch(tipoInconsistencia){
                case ARCHIVO:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_0_OF);
                    break;
                case ESTRUCTURA:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_1_OF);
                    break;
                default:
                    break;
            }
        }else{
            switch(tipoInconsistencia){
                case ARCHIVO:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_0_OI);
                    break;
                case ESTRUCTURA:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_1_OI);
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_2_OI);
                    break;
                case PAREJA_DE_ARCHIVOS:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_3_OI);
                    break;
                case APORTANTE_NO_IDENTIFICADO:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_5_OI);
                    break;
                case CONCILIACION:
                    bloquesConsulta.add(BloqueValidacionEnum.BLOQUE_6_OI);
                    conciliacion = Boolean.TRUE;
                    break;
                default:
                    break;
            }
        }
        
        List<InconsistenciaDTO> result = consultasPila.consultaDetalleInconsistenciasBandeja(inconsistencia, bloquesConsulta, uri, response);
        
        // sí se trata de conciliación, se procesa la diferencia entre registros
        if(conciliacion){
            result = establecerCamposConciliacion(result);
        }

//        List<InconsistenciaDTO> result = consultasPila.accionBandejaDetalleInconsistencias(inconsistencia, tipoInconsistencia);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#anularPlanillaOI(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void anularPlanillaOI(InconsistenciaDTO inconsistencia, UserDTO user) {
        String firmaMetodo = "anularPlanillaOI(InconsistenciaDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.anularPlanillaOI(inconsistencia, user);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Retorna una lista con los bloques a usar en la NamedQuery
     * @param bloque
     * @return
     */
    public static List<BloqueValidacionEnum> prepararListaBloques(BloqueValidacionEnum bloque) {
        String firmaMetodo = "prepararListaBloques(BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BloqueValidacionEnum> bloques = new ArrayList<>();

        switch (bloque) {
            case BLOQUE_0_OF:
            case BLOQUE_1_OF:
            case BLOQUE_0_OI:
                bloques.add(bloque);
                break;
            case BLOQUE_1_OI:
            case BLOQUE_2_OI:
            case BLOQUE_3_OI:
            case BLOQUE_4_OI:
            case BLOQUE_5_OI:
            case BLOQUE_6_OI:
                bloques.add(BloqueValidacionEnum.BLOQUE_1_OI);
                bloques.add(BloqueValidacionEnum.BLOQUE_2_OI);
                bloques.add(BloqueValidacionEnum.BLOQUE_3_OI);
                bloques.add(BloqueValidacionEnum.BLOQUE_4_OI);
                bloques.add(BloqueValidacionEnum.BLOQUE_5_OI);
                bloques.add(BloqueValidacionEnum.BLOQUE_6_OI);
                break;
            default:
                break;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return bloques;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#validarEstructuraPlanilla(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public void validarEstructuraPlanilla(InconsistenciaDTO inconsistencia) {
        String firmaMetodo = "validarEstructuraPlanilla(InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.validarEstructuraPlanilla(inconsistencia);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public void persistirHistoricoBloque2(Long indicePlanilla, UserDTO userDTO) {
        String firmaMetodo = "persistirHistoricoBloque2(Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.persistirHistoricoBloque2(indicePlanilla, userDTO.getNombreUsuario());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#anularPlanillaOF(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public void anularPlanillaOF(InconsistenciaDTO inconsistencia, UserDTO user) {
        String firmaMetodo = "anularPlanillaOF(InconsistenciaDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se anula el registro 6 y el índice OF sí aplica
        consultasPila.anularPlanillaOF(inconsistencia, user);

        // se gestiona la inconsistencia
        List<Long> listaErrores = new ArrayList<>();
        listaErrores.add(inconsistencia.getIdErrorValidacion());

        establecerGestionInconsistencias(listaErrores, EstadoGestionInconsistenciaEnum.INCONSISTENCIA_GESTIONADA,
                BloqueValidacionEnum.BLOQUE_6_OI);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#enviarSolicitudCambioIden(com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO,
     *      java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void enviarSolicitudCambioIdenPila(InconsistenciaDTO inconsistencia, Long numeroIdentificacion, UserDTO user) {
        String firmaMetodo = "enviarSolicitudCambioIden(InconsistenciaDTO, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.enviarSolicitudCambioIden(inconsistencia, numeroIdentificacion, user);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#aprobarSolicitudCambioIden(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoAprobacionCambioIdentificacionDTO aprobarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante> listaSolicitudes,
            UserDTO user) {
        String firmaMetodo = "aprobarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante>, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoAprobacionCambioIdentificacionDTO result = null;
        result = aprobarSolicitudCambioIdenMetodo(listaSolicitudes, user);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    private ResultadoAprobacionCambioIdentificacionDTO aprobarSolicitudCambioIdenMetodo(
            List<SolicitudCambioNumIdentAportante> listaSolicitudes, UserDTO user) {
        String firmaMetodo = "aprobarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante>, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoAprobacionCambioIdentificacionDTO result = new ResultadoAprobacionCambioIdentificacionDTO();

        // se recorre la lista de solicitudes seleccionada por el supervisor de
        // aportes para ser aprobadas
        for (SolicitudCambioNumIdentAportante solicitud : listaSolicitudes) {
            result = aprobarCambioId(result, solicitud, user.getNombreUsuario());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * @param result
     * @param solicitud
     * @return
     */
    private ResultadoAprobacionCambioIdentificacionDTO aprobarCambioId(ResultadoAprobacionCambioIdentificacionDTO result,
            SolicitudCambioNumIdentAportante solicitud, String usuario) {
        String firmaMetodo = "aprobarCambioId(ResultadoAprobacionCambioIdentificacionDTO, SolicitudCambioNumIdentAportante, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ResultadoAprobacionCambioIdentificacionDTO resultTemp = result;

        solicitud.setAccionCorreccion(AccionCorreccionPilaEnum.REGISTRAR_RESPUESTA_CAMBIO_IDENTIFICACION);
        solicitud.setFechaRespuesta(new Date());
        solicitud.setUsuarioAprobador(usuario);

        ActivosCorreccionIdAportanteDTO activos = null;

        activos = buscarIndicesPlanilla(solicitud.getIndicePlanilla());
        activos.setSolicitudCambio(solicitud);

        // modificar nombre archivos
        String idAportanteOriginal = null;
        if (activos.getRegistro1A() instanceof PilaArchivoARegistro1) {
            idAportanteOriginal = ((PilaArchivoARegistro1) activos.getRegistro1A()).getIdAportante();
        }
        else {
            idAportanteOriginal = ((PilaArchivoAPRegistro1) activos.getRegistro1A()).getIdPagador();
        }

        String cambioNombreArchivoA = modificarNombreArchivo(solicitud.getNumeroIdentificacion(), idAportanteOriginal,
                activos.getIndiceA().getNombreArchivo());

        String cambioNombreArchivoI = modificarNombreArchivo(solicitud.getNumeroIdentificacion(), idAportanteOriginal,
                activos.getIndiceI().getNombreArchivo());

        activos.getIndiceA().setNombreArchivo(cambioNombreArchivoA);
        activos.getIndiceI().setNombreArchivo(cambioNombreArchivoI);

        // modificar registro F
        activos = ubicarYActualizarRegistro6FActivo(activos, solicitud.getNumeroIdentificacion().toString());

        // modificar registros tipo 1
        activos = actualizarIdAportanteEnRegistro1(activos, solicitud.getNumeroIdentificacion().toString());

        // primero se actualizan los estados del archivo I

        // se prepara la entrada de histórico de estados
        HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
        historialEstado.setIdIndicePlanilla(activos.getIndiceI().getId());
        historialEstado.setEstado(activos.getEstadosI().getEstadoBloque5());
        historialEstado.setAccion(activos.getEstadosI().getAccionBloque5());
        historialEstado.setFechaEstado(activos.getEstadosI().getFechaBloque5());
        historialEstado.setUsuarioEspecifico(solicitud.getUsuarioSolicitud());
        historialEstado.setClaseUsuario((short) 3);
        historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);

        activos.setHistorialEstadoI(historialEstado);
        
        historialEstado = new HistorialEstadoBloque();
        historialEstado.setIdIndicePlanilla(activos.getIndiceI().getId());
        historialEstado.setEstado(EstadoProcesoArchivoEnum.APROBADO);
        historialEstado.setAccion(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_5);
        historialEstado.setFechaEstado(new Date());
        historialEstado.setUsuarioEspecifico(usuario);
        historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
        
        activos.setHistorialEstadoIAprobacion(historialEstado);
        
        activos.getIndiceI().setEstadoArchivo(EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE);
        activos.getEstadosI().setEstadoBloque5(EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE);
        activos.getEstadosI().setAccionBloque5(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_6);
        activos.getEstadosI().setFechaBloque5(new Date());

        // actualizar solicitud cambio
        solicitud.setIdPlanillaInformacion(activos.getIndiceI().getId());
        solicitud.setIdPlanillaFinanciera(activos.getIndiceF() != null ? activos.getIndiceF().getId() : null);
        solicitud.setEstadoArchivoAfectado(activos.getIndiceI().getEstadoArchivo());
        solicitud.setArchivosCorrecion(PilaBandejaBusiness.generarStringArchivosAsociados(activos));

        // actualizar registros en BD

        consultasPila.actualizarActivosCambioIdAportanteEnBD(activos);
        consultasPila.getionarInconsistenciaPorId(solicitud.getIdErrorValidacionLog());

        resultTemp.agregarIndice(activos.getIndiceA());
        resultTemp.agregarIndice(activos.getIndiceI());
        resultTemp.agregarIndice(activos.getIndiceF());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }

    private ActivosCorreccionIdAportanteDTO ubicarYActualizarRegistro6FActivo(ActivosCorreccionIdAportanteDTO activos, String nuevoId) {
        ActivosCorreccionIdAportanteDTO result = activos;

        /*
         * para ubicar correctamente los registros tipo 6 asociados a la planilla se requieren el # de planilla, el código de OI y
         * el período del aporte
         */
        String numPlanilla = result.getIndiceA().getIdPlanilla().toString();
        String codigoOI = result.getIndiceA().getCodigoOperadorInformacion();
        String periodoAporte = null;

        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(result.getIndiceA().getTipoArchivo().getGrupo())) {
            periodoAporte = ((PilaArchivoARegistro1) result.getRegistro1A()).getPeriodoAporte();
        }
        else {
            periodoAporte = ((PilaArchivoAPRegistro1) result.getRegistro1A()).getPeriodoAporte();
        }

        PilaArchivoFRegistro6 registro6f = consultasPila.obtenerRegistro6Of(numPlanilla, codigoOI,
                periodoAporte != null ? periodoAporte.replace("-", "") : null);

        if (registro6f != null) {
            result.setRegistro6F(registro6f);
            result.setIndiceF(registro6f.getIndicePlanilla());
        }

        // una vez se tiene el registro tipo 6 OF, se actualiza el número de ID de aportante
        if (result.getRegistro6F() != null) {
            result.getRegistro6F().setIdAportante(nuevoId);
        }

        return result;
    }

    private ActivosCorreccionIdAportanteDTO actualizarIdAportanteEnRegistro1(ActivosCorreccionIdAportanteDTO activos, String nuevoId) {
        ActivosCorreccionIdAportanteDTO result = activos;

        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(result.getIndiceA().getTipoArchivo().getGrupo())) {
            ((PilaArchivoARegistro1) activos.getRegistro1A()).setIdAportante(nuevoId);
            ((PilaArchivoIRegistro1) activos.getRegistro1I()).setIdAportante(nuevoId);
        }
        else {
            ((PilaArchivoAPRegistro1) activos.getRegistro1A()).setIdPagador(nuevoId);
            ((PilaArchivoIPRegistro1) activos.getRegistro1I()).setIdPagador(nuevoId);
        }

        return result;
    }

    private ActivosCorreccionIdAportanteDTO buscarIndicesPlanilla(IndicePlanilla indicePlanilla) {
        String firmaMetodo = "buscarIndicesPlanilla(IndicePlanilla)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ActivosCorreccionIdAportanteDTO result = new ActivosCorreccionIdAportanteDTO();

        IndicePlanillaModeloDTO indiceA = null;
        IndicePlanillaModeloDTO indiceI = null;

        TipoArchivoPilaEnum tipoFaltante = obtenerTipoArchivoFaltante(indicePlanilla.getTipoArchivo());

        switch (indicePlanilla.getTipoArchivo()) {
            case ARCHIVO_OI_A:
            case ARCHIVO_OI_AR:
            case ARCHIVO_OI_AP:
            case ARCHIVO_OI_APR:
                indiceA = new IndicePlanillaModeloDTO();
                indiceA.convertToDTO(indicePlanilla);

                indiceI = consultasPila.consultarIndicesOIporNumeroYTipo(indicePlanilla, tipoFaltante);
                break;
            case ARCHIVO_OI_I:
            case ARCHIVO_OI_IR:
            case ARCHIVO_OI_IP:
            case ARCHIVO_OI_IPR:
                indiceI = new IndicePlanillaModeloDTO();
                indiceI.convertToDTO(indicePlanilla);

                indiceA = consultasPila.consultarIndicesOIporNumeroYTipo(indicePlanilla, tipoFaltante);
                break;
            default:
                break;

        }

        result.setIndiceA(indiceA);
        result.setIndiceI(indiceI);

        result.setEstadosI(consultasPila.consultarEstadoBloquePlanillaXId(indiceI != null ? indiceI.getId() : null));

        result = obtenerRegistros1(result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    private ActivosCorreccionIdAportanteDTO obtenerRegistros1(ActivosCorreccionIdAportanteDTO result) {
        String firmaMetodo = "obtenerRegistros1(ActivosCorreccionIdAportanteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ActivosCorreccionIdAportanteDTO resultTemp = result;
        Object registro1A = null;
        Object registro1I = null;

        registro1A = consultasPila.consultarRegistro1PorIdPlanillaYTipoArchivo(resultTemp.getIndiceA().getId(),
                resultTemp.getIndiceA().getTipoArchivo());

        registro1I = consultasPila.consultarRegistro1PorIdPlanillaYTipoArchivo(resultTemp.getIndiceI().getId(),
                resultTemp.getIndiceI().getTipoArchivo());

        resultTemp.setRegistro1A(registro1A);
        resultTemp.setRegistro1I(registro1I);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }

    private TipoArchivoPilaEnum obtenerTipoArchivoFaltante(TipoArchivoPilaEnum tipoArchivo) {
        switch (tipoArchivo) {
            case ARCHIVO_OI_A:
                return TipoArchivoPilaEnum.ARCHIVO_OI_I;
            case ARCHIVO_OI_AP:
                return TipoArchivoPilaEnum.ARCHIVO_OI_IP;
            case ARCHIVO_OI_APR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_IPR;
            case ARCHIVO_OI_AR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_IR;
            case ARCHIVO_OI_I:
                return TipoArchivoPilaEnum.ARCHIVO_OI_A;
            case ARCHIVO_OI_IP:
                return TipoArchivoPilaEnum.ARCHIVO_OI_AP;
            case ARCHIVO_OI_IPR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_APR;
            case ARCHIVO_OI_IR:
                return TipoArchivoPilaEnum.ARCHIVO_OI_AR;
            default:
                break;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#rechazarSolicitudCambioIden(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO,
     *      com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum,
     *      java.lang.String)
     */
    @Override
    public void rechazarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante> listaSolicitudes, UserDTO user,
            RazonRechazoSolicitudCambioIdenEnum razonRechazo, String comentarios) {
        String firmaMetodo = " rechazarSolicitudCambioIden(List<SolicitudCambioNumIdentAportante>, UserDTO, RazonRechazoSolicitudCambioIdenEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasPila.rechazarSolicitudCambioIden(listaSolicitudes, user, razonRechazo, comentarios);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#busquedaSolicitudCambioIden(java.lang.Long,
     *      java.lang.Long, java.lang.Long)
     */
    @Override
    public List<SolicitudCambioNumIdentAportante> busquedaSolicitudCambioIden(Long numeroPlanilla, Long fechaInicio, Long fechaFin) {
        String firmaMetodo = "busquedaSolicitudCambioIden(Long, Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<SolicitudCambioNumIdentAportante> result = consultasPila.busquedaSolicitudCambioIden(numeroPlanilla, fechaInicio, fechaFin);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#crearAportante(java.lang.Long,
     *      com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO)
     */
    @Override
    public CreacionAportanteDTO crearAportante(Long numeroPlanilla, InconsistenciaDTO inconsistencia) {
        String firmaMetodo = "PilaBandejaBusiness.crearAportante(Long, InconsistenciaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CreacionAportanteDTO result = new CreacionAportanteDTO();

        // se establecen los datos de un aportante que aun no existe en la bd y
        // se valida a que registro estaba asociado
        try {
            Persona persona = null;
            PilaArchivoARegistro1 registroA = null;
            PilaArchivoAPRegistro1 registroAP = null;
            IndicePlanilla indiceActual = consultasPila.consultarIndicePlanillaEntidad(inconsistencia.getIndicePlanilla());
            // Buscar la pareja A del archivo I o IR
            if (TipoArchivoPilaEnum.ARCHIVO_OI_I.equals(inconsistencia.getTipoArchivo())
                    || TipoArchivoPilaEnum.ARCHIVO_OI_IR.equals(inconsistencia.getTipoArchivo())) {
                registroA = consultasPila.obtenerRegistroTipoA(numeroPlanilla, inconsistencia.getTipoArchivo(),indiceActual.getCodigoOperadorInformacion());
            }
            else if (TipoArchivoPilaEnum.ARCHIVO_OI_IP.equals(inconsistencia.getTipoArchivo())
                    || TipoArchivoPilaEnum.ARCHIVO_OI_IPR.equals(inconsistencia.getTipoArchivo())) {
                registroAP = consultasPila.obtenerRegistroTipoAP(numeroPlanilla, inconsistencia.getTipoArchivo(),indiceActual.getCodigoOperadorInformacion());
            }

            if (registroA != null) {
                // se establecen todos los datos para la creacion del aportante
                persona = consultasCore.consultarPersona(registroA.getIdAportante(),
                        TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(registroA.getTipoIdAportante()));

                if (persona == null) {
                    result = crearPersonaAportante(new ParametrosCreacionAportanteDTO(
                            TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(registroA.getTipoIdAportante()),
                            registroA.getIdAportante(), registroA.getNombreAportante(), registroA.getDigVerAportante(),
                            registroA.getCodDepartamento() + registroA.getCodCiudad(), registroA.getCodDepartamento(),
                            registroA.getDireccion(), registroA.getEmail(),
                            registroA.getTelefono() != null ? registroA.getTelefono().toString() : null, registroA.getFechaMatricula(),
                            NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(registroA.getNaturalezaJuridica().intValue()),
                            registroA.getCodSucursal(), registroA.getNomSucursal()), inconsistencia, result);
                }
            }

            if (registroAP != null) {
                // se establecen todos los datos para la creacion del aportante
                persona = consultasCore.consultarPersona(registroAP.getIdPagador(),
                        TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(registroAP.getTipoIdPagador()));

                if (persona == null) {
                    result = crearPersonaAportante(new ParametrosCreacionAportanteDTO(
                            TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(registroAP.getTipoIdPagador()),
                            registroAP.getIdPagador(), registroAP.getNombrePagador(), registroAP.getDigVerPagador(),
                            registroAP.getCodDepartamento() + registroAP.getCodCiudad(), registroAP.getCodDepartamento(),
                            registroAP.getDireccion(), registroAP.getEmail(),
                            registroAP.getTelefono() != null ? registroAP.getTelefono().toString() : null, null,
                            NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(registroAP.getNaturalezaJuridica().intValue()),
                            registroAP.getCodSucursal(), registroAP.getNomSucursal()), inconsistencia, result);
                }
            }

        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: Error al crear el empleador por favor revise los datos",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        result.setCreacionExitosa(Boolean.TRUE);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    // inicio metodos de ayuda

    /**
     * Método encargado de la creación de la persona con los datos recopilados de la planilla PILA
     * @param parametros
     * @param inconsistencia
     * @param result
     * @return <b>CreacionAportanteDTO</b>
     */
    private CreacionAportanteDTO crearPersonaAportante(ParametrosCreacionAportanteDTO parametros, InconsistenciaDTO inconsistencia,
            CreacionAportanteDTO result) {
        String firmaMetodo = "PilaBandejaBusiness.crearPersonaAportante(ParametrosCreacionAportanteDTO, InconsistenciaDTO, "
                + "CreacionAportanteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CreacionAportanteDTO resultTemp = result;

        Persona persona = new Persona();
        persona.setCreadoPorPila(true);
        persona.setTipoIdentificacion(parametros.getTipoIdentificacion());
        persona.setNumeroIdentificacion(parametros.getIdAportante());
        if (!inconsistencia.getTipoIdentificacion().equals(TipoIdentificacionEnum.NIT)) {
            persona.setPrimerNombre(inconsistencia.getNombresNuevoAportante().getPrimerNombre());
            persona.setSegundoNombre(inconsistencia.getNombresNuevoAportante().getSegundoNombre());
            persona.setPrimerApellido(inconsistencia.getNombresNuevoAportante().getPrimerApellido());
            persona.setSegundoApellido(inconsistencia.getNombresNuevoAportante().getSegundoApellido());
        }
        else {
            persona.setRazonSocial(parametros.getNombreAportante());
            persona.setDigitoVerificacion(parametros.getDigVerAportante());
        }

        Municipio municipio = consultasCore.consultarMunicipio(parametros.getCodMunicipio());
        if (municipio == null) {
            return null;
        }

        DepartamentoModeloDTO departamento = consultasCore.consultarDepartamento(parametros.getCodDepartamento());
        if (departamento == null) {
            return null;
        }

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccionFisica(parametros.getDireccion());
        ubicacion.setMunicipio(municipio);
        ubicacion.setIndicativoTelFijo(departamento.getIndicativoTelefoniaFija());
        ubicacion.setEmail(parametros.getEmail());
        // el télefono que se diligencia, depende de su longitud, para corresponder con el tamaño definido en BD
        if (parametros.getTelefono() != null && parametros.getTelefono().length() <= 7) {
            ubicacion.setTelefonoFijo(parametros.getTelefono());
        }
        else {
            ubicacion.setTelefonoCelular(parametros.getTelefono());
        }
        consultasCore.persistirUbicacion(ubicacion);

        persona.setUbicacionPrincipal(ubicacion);

        consultasCore.actualizarPersona(persona);

        // la empresa sólo se crea para los casos en los cuales no se trata de un aporte propio
        if (!inconsistencia.getEsAportePropio()) {
            Empresa empresa = new Empresa();
            SucursalEmpresaModeloDTO sucursal = null;
            empresa.setFechaConstitucion(parametros.getFechaMatricula());
            empresa.setNaturalezaJuridica(parametros.getNaturalezaJuridica());

            empresa.setPersona(persona);
            empresa.setCreadoPorPila(true);
            EmpresaModeloDTO empresaModelo = new EmpresaModeloDTO();
            empresaModelo.convertToDTO(empresa);

            if (parametros.getCodSucursal() != null && !parametros.getCodSucursal().isEmpty()) {
                sucursal = new SucursalEmpresaModeloDTO();
                sucursal.setCodigo(parametros.getCodSucursal());
                sucursal.setNombre(parametros.getNomSucursal());

                UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
                ubicacionDTO.convertToDTO(persona.getUbicacionPrincipal());
                sucursal.setUbicacion(ubicacionDTO);
                // la nueva sucursal se crea con estado inactivo
                sucursal.setEstadoSucursal(EstadoActivoInactivoEnum.INACTIVO);
            }

            resultTemp.setEmpresa(empresaModelo);
            resultTemp.setSucursal(sucursal);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultTemp;
    }

    /**
     * Metodo que establece el estado de una inconsistencia como gestionada
     * @param bloque
     * 
     * @param IdErrorValidacion
     * @return Boolean
     */
    public Boolean establecerGestionInconsistencias(List<Long> listaErrores, EstadoGestionInconsistenciaEnum estado,
            BloqueValidacionEnum bloque) {
        String firmaMetodo = "establecerGestionInconsistencias(List<Long>, EstadoGestionInconsistenciaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean result = consultasPila.establecerGestionInconsistencias(listaErrores, estado, bloque);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que establece que tipo de respuesta HTTP se le retorna a pantallas
     * 
     * @param result
     *        Lista que contiene el resultado de algunos de los servicios
     * 
     * @return List<code>InconsistenciaDTO</code> para tener el control sobre
     *         elementos vacios
     */
    public static List<InconsistenciaDTO> evaluarResultado(List<InconsistenciaDTO> inconsistencias) {
        String firmaMetodo = "evaluarResultado(List<InconsistenciaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (inconsistencias.isEmpty()) {
            return null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return inconsistencias;
    }

    /**
     * Metodo que establece que tipo de operador es la inconsistencia
     * 
     * @param errores
     *        las inconsistencias existentes
     * @param tipo
     *        tipo de operador que se establece
     * @return List<TipoInconsistenciasEnum> contiene la lista con el tipo ya
     *         establecido
     */
    public static List<InconsistenciaDTO> establecerOperador(List<InconsistenciaDTO> errores, String tipo) {
        String firmaMetodo = "establecerOperador(List<InconsistenciaDTO>, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipo.equals("I")) {
            for (InconsistenciaDTO inconsistenciaDTO : errores) {
                inconsistenciaDTO.setTipoOperador(TipoOperadorEnum.OPERADOR_INFORMACION);
            }
            return errores;
        }
        for (InconsistenciaDTO inconsistenciaDTO : errores) {
            inconsistenciaDTO.setTipoOperador(TipoOperadorEnum.OPERADOR_FINANCIERO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return errores;
    }

    /**
     * Metodo que establece la identificacion del aportante
     * 
     * @param errores
     *        las inconsistencias existentes
     * @param tipo
     *        tipo de operador que se establece
     * @return List<TipoInconsistenciasEnum> contiene la lista con el tipo ya
     *         establecido
     */
    public static List<InconsistenciaDTO> establecerIdentificacionAportante(List<InconsistenciaDTO> errores) {
        String firmaMetodo = "establecerIdentificacionAportante(List<InconsistenciaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se recorre la lista para establecer el aportante tomando el dato del
        // nombre de archivo
        for (InconsistenciaDTO inconsistencia : errores) {

            inconsistencia.setNumeroIdAportante(inconsistencia.getNombreArchivo().split("_")[4]);
            inconsistencia.setTipoIdentificacion(
                    TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(inconsistencia.getNombreArchivo().split("_")[3]));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return errores;
    }

    /**
     * Metodo que establece que tipo de respuesta HTTP se le retorna a pantallas
     * 
     * @param result
     *        Lista que contiene el resultado de algunos de los servicios
     * 
     * @return List <code>SolicitudCambioNumIdentAportante</code>
     */
    public static List<SolicitudCambioNumIdentAportante> evaluarResultadoSolicitudes(List<SolicitudCambioNumIdentAportante> solicitudes) {
        String firmaMetodo = "evaluarResultadoSolicitudes(List<SolicitudCambioNumIdentAportante>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (solicitudes.isEmpty()) {
            return null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return solicitudes;
    }

    /**
     * Metodo que establece que pestañas va a poseer el detalle de las
     * inconsistencias
     * 
     * @param bloques
     * @return List<TipoInconsistenciasEnum> con las pestañas activas
     */
    public static List<TipoInconsistenciasEnum> generarPestanas(List<BloqueValidacionEnum> bloques) {
        String firmaMetodo = "generarPestanas(List<BloqueValidacionEnum>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TipoInconsistenciasEnum> result = new ArrayList<>();

        for (BloqueValidacionEnum bloqueValidacionEnum : bloques) {
            if (bloqueValidacionEnum.getGrupoInconsistencia() != null) {
                result.add(bloqueValidacionEnum.getGrupoInconsistencia());
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que establece las diferencias entre archivo I y F
     * 
     * @param result
     *        List <code>InconsistenciaDTO</code> que contiene los datos
     *        para realizar la diferencia entre las dos planillas
     * @return List <code>InconsistenciaDTO</code> con los datos de las
     *         diferencias ya establecidos
     */
    private static List<InconsistenciaDTO> establecerCamposConciliacion(List<InconsistenciaDTO> result) {
        String firmaMetodo = "establecerCamposConciliacion(List<InconsistenciaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (InconsistenciaDTO inconsistencia : result) {
            if (inconsistencia.getValorCampo() != null) {
                String[] campos = inconsistencia.getValorCampo().split(":-:");
                if (campos.length == 2) {
                    inconsistencia.setValorCampo(campos[0]);
                    inconsistencia.setValorCampoFinanciero(campos[1]);
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que establece la ubicacion de un bloque de validacion OF luego de
     * haber sido ejecutado la accion de anular
     * 
     * @param bloque
     *        <code>BloqueValidacionEnum</code> Bloque actual en el que se
     *        encuentra el archivo OF
     * @param estado
     *        <code>EstadoArchivoPorBloqueOF</code> Se establece los datos
     *        necesarios dependiendo del bloque
     * @return estado <code>EstadoArchivoPorBloqueOF</code>
     */
    public EstadoArchivoPorBloqueOF establecerUbicacionBloqueAnularOF(BloqueValidacionEnum bloque, EstadoArchivoPorBloqueOF estado) {
        String firmaMetodo = "establecerUbicacionBloqueAnularOF(BloqueValidacionEnum, EstadoArchivoPorBloqueOF)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (bloque == BloqueValidacionEnum.BLOQUE_0_OF) {
            estado.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
            estado.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
            estado.setFechaBloque0(new Date());
            return estado;
        }

        if (bloque == BloqueValidacionEnum.BLOQUE_1_OF) {
            estado.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
            estado.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
            estado.setFechaBloque1(new Date());
            return estado;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Metodo que establece la accion y el estado de un
     * {@link EstadoArchivoPorBloque}
     * 
     * @param bloque
     * @param estado
     * @return {@link EstadoArchivoPorBloque} con los campos establecidos
     */
    public static EstadoArchivoPorBloque establecerUbicacionBloqueSiguienteBloque(BloqueValidacionEnum bloque,
            EstadoArchivoPorBloque estado) {
        String firmaMetodo = "establecerUbicacionBloqueSiguienteBloque(BloqueValidacionEnum, EstadoArchivoPorBloque)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (bloque == BloqueValidacionEnum.BLOQUE_0_OF) {
            estado.setAccionBloque0(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_1);
            estado.setEstadoBloque0(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);
            estado.setFechaBloque0(new Date());
            return estado;
        }

        if (bloque == BloqueValidacionEnum.BLOQUE_1_OF) {
            estado.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
            estado.setEstadoBloque1(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);
            estado.setFechaBloque1(new Date());
            return estado;
        }
        if (bloque == BloqueValidacionEnum.BLOQUE_1_OI) {
            estado.setAccionBloque1(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_2);
            estado.setEstadoBloque1(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);
            estado.setFechaBloque1(new Date());
            return estado;
        }
        if (bloque == BloqueValidacionEnum.BLOQUE_2_OI) {
            estado.setAccionBloque2(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_3);
            estado.setEstadoBloque2(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA);
            estado.setFechaBloque2(new Date());
            return estado;
        }
        if (bloque == BloqueValidacionEnum.BLOQUE_3_OI) {
            estado.setAccionBloque3(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_4);
            estado.setEstadoBloque3(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_CONSISTENTES);
            estado.setFechaBloque3(new Date());
            return estado;
        }

        if (bloque == BloqueValidacionEnum.BLOQUE_5_OI) {
            estado.setAccionBloque5(AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_6);
            estado.setEstadoBloque5(EstadoProcesoArchivoEnum.ARCHIVO_CONSISTENTE);
            estado.setFechaBloque5(new Date());
            return estado;
        }
        if (bloque == BloqueValidacionEnum.BLOQUE_6_OI) {
            estado.setAccionBloque6(AccionProcesoArchivoEnum.PASAR_A_CRUCE_CON_BD);
            estado.setEstadoBloque6(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
            estado.setFechaBloque6(new Date());
            return estado;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Metodo que establece un nombre con la concatenacion de los nombres de los
     * indices
     * 
     * @param activos
     * @return {@link String} con el nombre de los archivos concatenados
     */
    public static String generarStringArchivosAsociados(ActivosCorreccionIdAportanteDTO activos) {
        String firmaMetodo = "generarStringArchivosAsociados(IndicePlanilla, IndicePlanilla, IndicePlanillaOF)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String result = "" + activos.getIndiceI().getNombreArchivo() + ",";
        result = result + " " + activos.getIndiceA().getNombreArchivo() + ",";
        if (activos.getIndiceF() != null) {
            result = result + " " + activos.getIndiceF().getNombreArchivo();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que modifica el nombre de un archivo con los datos nuevos
     * 
     * @param numeroIdentificacionNuevo
     * @param numeroIdentificacion
     * @param nombreArchivo
     * @return {@link String} Con el nuevo nombre del archivo
     */
    public static String modificarNombreArchivo(Long numeroIdentificacionNuevo, String numeroIdentificacion, String nombreArchivo) {
        String firmaMetodo = "modificarNombreArchivo(Long, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String[] partesNombre = nombreArchivo.split("_");

        for (int i = 0; i < partesNombre.length; i++) {
            if (partesNombre[i].equals(numeroIdentificacion)) {
                partesNombre[i] = ("" + numeroIdentificacionNuevo);
            }
        }

        StringJoiner unirPartes = new StringJoiner("_");

        for (String parte : partesNombre) {
            unirPartes.add(parte);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return "" + unirPartes;
    }

    /**
     * Metodo que filtra los datos a mostrar a un usuario en la lista de
     * consulta de solicitudes de cambio de identficacion
     * 
     * @param listaSolicitudes
     * @return una lista de {@link SolicitudCambioNumIdentAportante} con los que no han
     *         sido aprobados o rechazados
     */
    public static List<SolicitudCambioNumIdentAportante> filtrarResultadoConsultaSolicitudes(
            List<SolicitudCambioNumIdentAportante> listaSolicitudes) {
        String firmaMetodo = "filtrarResultadoConsultaSolicitudes(List<SolicitudCambioNumIdentAportante>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (listaSolicitudes != null) {
            List<SolicitudCambioNumIdentAportante> listaResult = new ArrayList<>();
            for (SolicitudCambioNumIdentAportante solicitud : listaSolicitudes) {
                if (solicitud.getUsuarioAprobador() == null) {
                    listaResult.add(solicitud);
                }
            }

            return listaResult;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#veArchivo(java.lang.Long)
     */
    @Override
    public IdentificadorDocumentoDTO veArchivo(Long idPlanilla, TipoArchivoPilaEnum tipoArchivo) {
        String firmaMetodo = "veArchivo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IdentificadorDocumentoDTO result = consultasPila.veArchivo(idPlanilla, tipoArchivo);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que mapea el resultado de una consulta nativa para la HU 392
     * 
     * @param consulta
     *        List<code>Object[]</code> Contiene el resultado de una
     *        consulta nativa
     * @return resultado List<code>InconsistenciaDTO</code> contiene una lista
     *         con los datos ya mapeados en el DTO
     */
    public static List<InconsistenciaDTO> mapeoInconsistenciaDTO(List<Object[]> consulta) {
        String firmaMetodo = "mapeoInconsistenciaDTO(List<Object[]>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaDTO> resultado = new ArrayList<>();

        for (Object[] objeto : consulta) {
            InconsistenciaDTO dato = new InconsistenciaDTO();
            dato.setFechaProcesamiento((Date) objeto[0]);
            dato.setNumeroPlanilla(((BigInteger) objeto[1]).longValueExact());
            dato.setNombreArchivo((String) objeto[2]);
            dato.setTipoArchivo(TipoArchivoPilaEnum.valueOf((String) objeto[3]));
            dato.setCantidadErrores(new Long((Integer) objeto[4]));
            dato.setEstadoArchivo(EstadoProcesoArchivoEnum.valueOf((String) objeto[5]));

            resultado.add(dato);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;

    }

    /**
     * Metodo que mapea los datos de una consulta nativa en un DTO
     * 
     * @param consulta
     *        List<code>Object[]</code> Contiene el resultado de una
     *        consulta nativa
     * @return resultado List<code>RespuestaConsultaEmpleadorDTO</code> Lista
     *         que contiene los datos ya mapeados en un DTO
     */
    public static List<RespuestaConsultaEmpleadorDTO> mapeoAportesEmpresa(List<Object[]> consulta) {
        String firmaMetodo = "mapeoAportesEmpresa(List<Object[]>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaConsultaEmpleadorDTO> resultado = new ArrayList<>();
        List<DetalleTablaAportanteDTO> detalles = new ArrayList<>();

        if (consulta.isEmpty()) {
            return resultado;
        }
        else {
            // se establecen los datos principales del DTO que serian los datos
            // de cabecera
            String nombreAportante = (String) consulta.get(0)[0];
            String numeroIdentificacion = null;
            String periodoAporte = (String) consulta.get(0)[14];
            BigDecimal valorTotalAportes = (BigDecimal) consulta.get(0)[19];
            TipoIdentificacionEnum tipoIdentificacionAportante = TipoIdentificacionEnum
                    .obtnerTiposIdentificacionEnum((String) consulta.get(0)[12]);
            Short digitoVerificacion = (Short) consulta.get(0)[20];

            for (Object[] objeto : consulta) {
                if (((String) objeto[14]).equalsIgnoreCase(periodoAporte) == false) {
                    // se establecen los datos finales de la cabecera
                    RespuestaConsultaEmpleadorDTO result = new RespuestaConsultaEmpleadorDTO();
                    result.setNombreEmpleador(nombreAportante);
                    result.setTipoIdentificacion(tipoIdentificacionAportante);
                    result.setNumeroIdentificacion(numeroIdentificacion);
                    result.setPeriodoAporte(periodoAporte);
                    result.setValorTotalAportes(valorTotalAportes);
                    result.setDigitoVerificacionAportante(digitoVerificacion);
                    result.setRegistros(detalles);
                    resultado.add(result);
                    detalles = new ArrayList<>();

                }
                // se establecen los datos de los cotizantes
                DetalleTablaAportanteDTO detalle = new DetalleTablaAportanteDTO();
                nombreAportante = (String) objeto[0];
                numeroIdentificacion = (String) objeto[13];
                periodoAporte = (String) objeto[14];
                detalle.setIdCotizante((String) objeto[1]);
                detalle.setSecuencia(((BigInteger) objeto[2]).longValueExact());
                detalle.setIdPlanilla(((String) objeto[3]));
                detalle.setTipoArchivo((String) objeto[4]);
                detalle.setFechaProcesamiento((Date) objeto[5]);
                detalle.setAporteObligatorio((BigDecimal) objeto[6]);
                detalle.setTipoCotizante((String) objeto[7]);
                detalle.setV0(objeto[8] != null ? (EstadoValidacionRegistroAporteEnum.valueOf((String) objeto[8])) : null);
                detalle.setV1(objeto[9] != null ? (EstadoValidacionRegistroAporteEnum.valueOf((String) objeto[9])) : null);
                detalle.setV2(objeto[10] != null ? (EstadoValidacionRegistroAporteEnum.valueOf((String) objeto[10])) : null);
                detalle.setV3(objeto[11] != null ? (EstadoValidacionRegistroAporteEnum.valueOf((String) objeto[11])) : null);
                detalle.setTipoIdCotizante(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum((String) objeto[15]));
                detalle.setIndicePlanilla(((BigInteger) objeto[16]).longValueExact());
                detalle.setEstadoRegistro((String) objeto[17]);
                detalle.setEstadoAportante((String) objeto[18]);
                detalles.add(detalle);

            }

            RespuestaConsultaEmpleadorDTO result = new RespuestaConsultaEmpleadorDTO();
            result.setNombreEmpleador(nombreAportante);
            result.setTipoIdentificacion(tipoIdentificacionAportante);
            result.setNumeroIdentificacion(numeroIdentificacion);
            result.setPeriodoAporte(periodoAporte);
            result.setValorTotalAportes(valorTotalAportes);
            result.setDigitoVerificacionAportante(digitoVerificacion);
            result.setRegistros(detalles);
            resultado.add(result);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;

    }

    /**
     * Metodo que mapea los datos de una consulta nativa en un DTO
     * 
     * @param consulta
     *        List<code>Object[]</code> Contiene el resultado de una
     *        consulta nativa
     * @return resultado List<code>RespuestaConsultaEmpleadorDTO</code> Lista
     *         que contiene los datos ya mapeados en un DTO
     */
    public static List<RespuestaConsultaEmpleadorDTO> mapeoAportesEmpresaPensionados(List<Object[]> consulta) {
        String firmaMetodo = "mapeoAportesEmpresaPensionados(List<Object[]>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaConsultaEmpleadorDTO> resultado = new ArrayList<>();
        List<DetalleTablaAportanteDTO> detalles = new ArrayList<>();

        if (consulta.isEmpty()) {
            return resultado;
        }
        else {

            String nombreAportante = (String) consulta.get(0)[0];
            TipoIdentificacionEnum tipoIdentificacion = null;
            String numeroIdentificacion = null;
            String periodoAporte = (String) consulta.get(0)[11];
            for (Object[] objeto : consulta) {
                if (((String) objeto[11]).equalsIgnoreCase(periodoAporte) == false) {
                    RespuestaConsultaEmpleadorDTO result = new RespuestaConsultaEmpleadorDTO();
                    result.setNombreEmpleador(nombreAportante);
                    result.setTipoIdentificacion(tipoIdentificacion);
                    result.setNumeroIdentificacion(numeroIdentificacion);
                    result.setPeriodoAporte(periodoAporte);
                    result.setRegistros(detalles);
                    resultado.add(result);
                    detalles = new ArrayList<>();

                }
                DetalleTablaAportanteDTO detalle = new DetalleTablaAportanteDTO();
                nombreAportante = (String) objeto[0];
                tipoIdentificacion = objeto[9] != null ? (TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum((String) objeto[9]))
                        : null;
                numeroIdentificacion = (String) objeto[10];
                periodoAporte = (String) objeto[11];
                detalle.setIdCotizante((String) objeto[1]);
                detalle.setSecuencia(((BigInteger) objeto[2]).longValueExact());
                detalle.setIdPlanilla(((String) objeto[3]));
                detalle.setTipoArchivo((String) objeto[4]);
                detalle.setFechaProcesamiento((Date) objeto[5]);
                detalle.setAporteObligatorio((BigDecimal) objeto[6]);
                detalle.setTipoCotizante((String) objeto[7]);
                detalle.setV1(objeto[8] != null ? (EstadoValidacionRegistroAporteEnum.valueOf((String) objeto[8])) : null);
                detalle.setTipoIdCotizante(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum((String) objeto[12]));
                detalle.setIndicePlanilla(((BigInteger) objeto[13]).longValueExact());
                detalle.setEstadoRegistro((String) objeto[14]);

            }
            RespuestaConsultaEmpleadorDTO result = new RespuestaConsultaEmpleadorDTO();
            result.setNombreEmpleador(nombreAportante);
            result.setTipoIdentificacion(tipoIdentificacion);
            result.setNumeroIdentificacion(numeroIdentificacion);
            result.setPeriodoAporte(periodoAporte);
            result.setRegistros(detalles);
            resultado.add(result);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#contarPlanillasConInconsistenciasPorGestionar()
     */
    @Override
    public Integer contarPlanillasConInconsistenciasPorGestionar() {
        String firmaMetodo = "contarPlanillasConInconsistenciasPorGestionar()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Integer result = consultasStaging.contarPlanillasConInconsistenciasPorGestionar();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#consultarPlanillasPorGestionarConInconsistenciasValidacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorGestionarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin) {

        List<InconsistenciaRegistroAporteDTO> result = consultasStaging.consultarPlanillasPorGestionarConInconsistenciasValidacion(
                tipoIdentificacionAportante, numeroIdentificacionAportante, digitoVerificacionAportante, fechaInicio, fechaFin);
        return result;

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#consultarPlanillasPorAprobarConInconsistenciasValidacion(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorAprobarConInconsistenciasValidacion(
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante, Short digitoVerificacionAportante,
            Long fechaInicio, Long fechaFin) {

        String firmaMetodo = "consultarPlanillasPorAprobarConInconsistenciasValidacion(TipoIdentificacionEnum, String, Short, Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<InconsistenciaRegistroAporteDTO> result = consultasStaging.consultarPlanillasPorAprobarConInconsistenciasValidacion(
                tipoIdentificacionAportante, numeroIdentificacionAportante, digitoVerificacionAportante, fechaInicio, fechaFin);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#aprobarRegistroAporteConInconsistencia(com.asopagos.dto.InconsistenciaRegistroAporteDTO)
     */
    @Override
    public InconsistenciaRegistroAporteDTO aprobarRegistroAporteConInconsistencia(
            InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO, UserDTO user) {

        String firmaMetodo = "aprobarRegistroAporteConInconsistencia(InconsistenciaRegistroAporteDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroDetallado registroAporte = null;
        boolean esProcesadoVsBd = false;
        if (inconsistenciaRegistroAporteDTO.getIdIndicePlanilla() == null) {
            // error no existe indice planilla para evaluar el estado del archivo
            inconsistenciaRegistroAporteDTO.setRegistroProcesado(false);
        }
        else if (inconsistenciaRegistroAporteDTO.getIdRegistroDetalladoAporte() == null) {
            //Error registro no valido sin identificador para busqueda
            inconsistenciaRegistroAporteDTO.setRegistroProcesado(false);
        }
        else {
            //se realiza proceso de aprobacion del registro 
            try {
                registroAporte = consultasStaging
                        .consultarRegistroAporteConInconsistencia(inconsistenciaRegistroAporteDTO.getIdRegistroDetalladoAporte());
                if (registroAporte != null) {

                    if (EstadoRegistroAportesArchivoEnum.NO_OK.equals(inconsistenciaRegistroAporteDTO.getEstadoAporte())) {
                        registroAporte.setOutEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum.NO_OK_APROBADO);
                        inconsistenciaRegistroAporteDTO.setRegistroProcesado(true);
                    }
                    else if (EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD.equals(inconsistenciaRegistroAporteDTO.getEstadoAporte())) {
                        registroAporte.setOutEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD_APROBADO);
                        inconsistenciaRegistroAporteDTO.setRegistroProcesado(true);
                    }
                    else {
                        inconsistenciaRegistroAporteDTO.setRegistroProcesado(false);
                        inconsistenciaRegistroAporteDTO.setArchivoProcesadoVsBD(false);
                    }

                    if (inconsistenciaRegistroAporteDTO.getRegistroProcesado()) {
                        if (user.getNombreUsuario() != null) {
                            registroAporte.setUsuarioAprobadorAporte(user.getNombreUsuario());
                        }
                        else {
                            registroAporte.setUsuarioAprobadorAporte(ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO);
                        }

                        inconsistenciaRegistroAporteDTO.setRegistroProcesado(true);

                        //Actualizar registro de aporte para evaluar el estado de archivo debido a los registros que quedan pendientes por aprobar o reprocesar
                        logger.info("Mantis 265511 seguimiento ln 1494");
                        registroAporte = actualizarRegistroDetallado(registroAporte);
                        if (registroAporte == null) {
                        	logger.info("Mantis 265511 seguimiento ln 1495");
                            logger.error(
                                    "PilaBandejaBusiness.aprobarRegistroAporteConInconsistencia :: No se logro actualizar el registro detallado de procesamiento de planilla PILA");
                            logger.debug(
                                    "Finaliza PilaBandejaBusiness.aprobarRegistroAporteConInconsistencia ( InconsistenciaRegistroAporteDTO )");
                            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
                        }
                        //verifica estado de archivo con relacion a la aprobacion de los registros de aporte por gestion de inconsistencia
                        if (EstadoRegistroAportesArchivoEnum.NO_OK_APROBADO.equals(registroAporte.getOutEstadoRegistroAporte())
                                || EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD_APROBADO
                                        .equals(registroAporte.getOutEstadoRegistroAporte())) {
                            inconsistenciaRegistroAporteDTO.setBloque(BloqueValidacionEnum.BLOQUE_7_OI);
                            esProcesadoVsBd = evaluarEstadoArchivoPlanilla(inconsistenciaRegistroAporteDTO);
                            inconsistenciaRegistroAporteDTO.setArchivoProcesadoVsBD(esProcesadoVsBd);
                        }
                    }
                }
                else {
                    inconsistenciaRegistroAporteDTO.setRegistroProcesado(false);
                    inconsistenciaRegistroAporteDTO.setArchivoProcesadoVsBD(false);
                }
            } catch (NoResultException e) {
                // No se encuentra el registro de aporte de planilla PILA
                // correspondiente
            	logger.info("Mantis 265511 seguimiento ln 1520");
                inconsistenciaRegistroAporteDTO.setRegistroProcesado(false);
                inconsistenciaRegistroAporteDTO.setArchivoProcesadoVsBD(false);
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return inconsistenciaRegistroAporteDTO;

    }

    // HU-389
    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.composite.service.PilaCompositeService#buscarControlResultadosPersona(com.asopagos.entidades.ccf.personas.Persona,
     *      java.lang.Long, java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosPersona(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO) {
        String firmaMetodo = "buscarControlResultadosPersona(TipoIdentificacionEnum, String, Long, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaConsultaEmpleadorDTO> result = consultasStaging.buscarControlResultadosPersona(tipoDocumento, idAportante,
                numeroPlanilla, periodo, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.composite.service.PilaCompositeService#buscarControlResultadosEmpleador(com.asopagos.entidades.ccf.personas.Empleador,
     *      java.lang.Long, java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosEmpleador(TipoIdentificacionEnum tipoDocumento, String idAportante,
            Long numeroPlanilla, Long periodo, UserDTO userDTO) {
        String firmaMetodo = "buscarControlResultadosEmpleador(TipoIdentificacionEnum, String, Long, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RespuestaConsultaEmpleadorDTO> result = consultasStaging.buscarControlResultadosEmpleador(tipoDocumento, idAportante,
                numeroPlanilla, periodo, userDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que calcula la cantidad y el total de aportes de una lista
     * 
     * @param registrosAportes
     *        List<code>RespuestaConsultaEmpleadorDTO</code> contiene los
     *        registros con la cabecera del aportante y una lista de
     *        cotizantes
     * @return registrosAportes List<code>RespuestaConsultaEmpleadorDTO</code>
     *         Lista con los datos establecidos
     */
    public static List<RespuestaConsultaEmpleadorDTO> calcularCantidadTotalAportes(List<RespuestaConsultaEmpleadorDTO> registrosAportes) {
        String firmaMetodo = "calcularCantidadTotalAportes(List<RespuestaConsultaEmpleadorDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (registrosAportes.isEmpty()) {
            return null;
        }
        else {
            BigDecimal totalAportes = new BigDecimal(0);
            Integer cantidadAportes = 0;
            for (RespuestaConsultaEmpleadorDTO respuesta : registrosAportes) {
                for (DetalleTablaAportanteDTO detalle : respuesta.getRegistros()) {
                    totalAportes.add(detalle.getAporteObligatorio());
                    cantidadAportes += 1;
                }
                respuesta.setCantidadAportes(cantidadAportes);
                respuesta.setTotalAportes(new Long(totalAportes.longValue()));
                totalAportes = new BigDecimal(0);
                cantidadAportes = 0;

            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return registrosAportes;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#
     *      consultarEmpPendientesPorAfiliar(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.Short, java.lang.Long)
     */
    @Override
    public List<EmpAporPendientesPorAfiliarDTO> consultarEmpPendientesPorAfiliar(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, Short digitoVerificacion, Long fechaIngresoBandeja, UriInfo uri,
            HttpServletResponse response) {
        String firmaMetodo = "consultarEmpPendientesPorAfiliar(String, TipoIdentificacionEnum, Short, Long, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CriteriosDTO criterios = new CriteriosDTO();

        criterios.setTipoIdentificacion(tipoIdentificacion);
        criterios.setNumeroIdentificacion(numeroIdentificacion);
        criterios.setDigitoVerificacion(digitoVerificacion);
        criterios.setFechaIngresobandeja(fechaIngresoBandeja);

        List<EmpAporPendientesPorAfiliarDTO> result = consultasCore.consultarEmpPendientesPorAfiliar(criterios, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#
     *      consultarEmpCeroTrabajadoresActivos(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long,
     *      java.lang.Boolean, javax.ws.rs.core.UriInfo,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<BandejaEmpleadorCeroTrabajadoresDTO> consultarEmpCeroTrabajadoresActivos(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, String nombreEmpresa, Short digitoVerificacion, Long fechaInicioIngresoBandeja,
            Long fechaFinIngresoBandeja, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "consultarEmpCeroTrabajadoresActivos(String, TipoIdentificacionEnum , String, Short, Long, Long, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BandejaEmpleadorCeroTrabajadoresDTO> result = consultasCore.consultarEmpCeroTrabajadoresActivos(numeroIdentificacion,
                tipoIdentificacion, nombreEmpresa, digitoVerificacion, fechaInicioIngresoBandeja, fechaFinIngresoBandeja, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#consultarActualizacionDatosEmp(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long)
     */
    @Override
    public List<ActualizacionDatosEmpleadorModeloDTO> consultarActualizacionDatosEmp(TipoIdentificacionEnum tipoDocumento,
            String idAportante, Short digitoVerificacion, Long fechaIngresoBandeja) {
        String firmaMetodo = "consultarActualizacionDatosEmp(TipoIdentificacionEnum, String , Short , Long )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<ActualizacionDatosEmpleadorModeloDTO> result = consultasCore.consultarActualizacionDatosEmp(tipoDocumento, idAportante,
                digitoVerificacion, fechaIngresoBandeja);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Metodo que establece que tipo de respuesta HTTP se le retorna a pantallas
     * 
     * @param result
     *        Lista que contiene el resultado de algunos de los servicios
     * 
     * @return List<code>ActualizacionDatosEmpleador</code> para tener el
     *         control sobre elementos vacios
     */
    public static List<ActualizacionDatosEmpleadorModeloDTO> evaluarResultadoActualizacionDatosEmpleador(
            List<ActualizacionDatosEmpleadorModeloDTO> resultado) {
        String firmaMetodo = "evaluarResultadoActualizacionDatosEmpleador(List<ActualizacionDatosEmpleadorModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (resultado.isEmpty()) {
            return null;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#ActualizarActualizacionDatosEmp(java.util.List)
     */
    @Override
    public void ActualizarActualizacionDatosEmp(List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion) {
        String firmaMetodo = "ActualizarActualizacionDatosEmp(List<ActualizacionDatosEmpleadorModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.ActualizarActualizacionDatosEmp(listaActualizacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#totalInconsistenciasPendientes()
     */
    @Override
    public Long totalInconsistenciasPendientes() {
        String firmaMetodo = "totalInconsistenciasPendientes()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasPila.totalInconsistenciasPendientes();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#totalInconsistenciasPendientesAprobacion()
     */
    @Override
    public Long totalInconsistenciasPendientesAprobacion() {
        String firmaMetodo = "totalInconsistenciasPendientesAprobacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long result = consultasPila.totalInconsistenciasPendientesAprobacion();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;

    }

    /**
     * @param inconsistenciaRegistroAporteDTO
     * @return
     */
    private boolean evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO) {
        String firmaMetodo = "evaluarEstadoArchivoPlanilla(InconsistenciaRegistroAporteDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("Mantis 265511 seguimiento Inicio evaluarEstadoArchivoPlanilla" );
        
        boolean success = false;
        IndicePlanilla indicePlanilla = null;
        RegistroGeneral registroGeneral = null;
        EstadoArchivoPorBloque estadoArchivoPorBloque = null;
        List<RegistroDetallado> lstRegistroDetallado = null;
        //consultar con idRegistroGeneral todos los registros detallados
        indicePlanilla = consultasPila.obtenerIndicePlanilla(inconsistenciaRegistroAporteDTO.getIdIndicePlanilla());

        if (indicePlanilla != null) {
        	logger.info("Mantis 265511 seguimiento ln 1764 -- indice planilla "+ indicePlanilla.getId() );
            lstRegistroDetallado = consultasStaging
                    .consultarRegistroDetalladoxRegistroGeneral(inconsistenciaRegistroAporteDTO.getIdRegistroGeneralAporte());

            if (lstRegistroDetallado != null && !lstRegistroDetallado.isEmpty()) {
            	logger.info("Mantis 265511 seguimiento ln 1769" );
                for (RegistroDetallado registroDetallado : lstRegistroDetallado) {
                    //Si al menos un registro es NO_OK o NO_VALIDADO, no realiza cambio de estado de archivo 
                    //(siendo archivo normal, adición o corrección corrección C)
                    if ((EstadoRegistroAportesArchivoEnum.NO_OK.equals(registroDetallado.getOutEstadoRegistroAporte())
                            || EstadoRegistroAportesArchivoEnum.NO_VALIDADO_BD.equals(registroDetallado.getOutEstadoRegistroAporte()))
                            && (registroDetallado.getCorrecciones() == null || registroDetallado.getCorrecciones().equals("C"))) {
                        success = false;
                        break;
                    }
                    else {
                        success = true;
                    }
                }
                
                logger.info("Mantis 265511 seguimiento sucess "+ success);
                if (success) {
                    //si todos son aprobados entonces cambiar estado de archivo en RegistroGeneral y en IndicePlanilla y Estado por bloque?
                    registroGeneral = consultasStaging
                            .consultarRegistroGeneralxId(inconsistenciaRegistroAporteDTO.getIdRegistroGeneralAporte());

                    if (registroGeneral != null) {
                    	logger.info("Mantis 265511 seguimiento ln 1791");
                        estadoArchivoPorBloque = consultasPila.consultarEstadoBloquePlanillaXId(indicePlanilla.getId());
                        AccionProcesoArchivoEnum accion = null;
                        if (inconsistenciaRegistroAporteDTO.getEsSimulado() == null || !inconsistenciaRegistroAporteDTO.getEsSimulado()) {
                            accion = AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE;
                        }
                        else {
                            accion = AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE_MANUAL;
                            // se rehabilita el proceso manual la planilla
                            indicePlanilla.setHabilitadoProcesoManual(Boolean.TRUE);
                        }
                        
                        logger.info("Mantis 265511 seguimiento accion" + accion);
                        logger.info("Mantis 265511 seguimiento estadoArchivoPorBloque" + estadoArchivoPorBloque);

                        if (estadoArchivoPorBloque != null) {

                            registroGeneral.setOutEstadoArchivo(EstadoProcesoArchivoEnum.PROCESADO_VS_BD);
                            registroGeneral.setFechaActualizacion(new Date());
                            indicePlanilla.setEstadoArchivo(EstadoProcesoArchivoEnum.PROCESADO_VS_BD);

                            // se prepara la entrada en historial de estados
                            HistorialEstadoBloque historialEstado = new HistorialEstadoBloque();
                            historialEstado.setIdIndicePlanilla(indicePlanilla.getId());
                            historialEstado.setEstado(estadoArchivoPorBloque.getEstadoBloque7());
                            historialEstado.setAccion(estadoArchivoPorBloque.getAccionBloque7());
                            historialEstado.setFechaEstado(estadoArchivoPorBloque.getFechaBloque7());
                            historialEstado.setBloque(BloqueValidacionEnum.BLOQUE_7_OI);

                            estadoArchivoPorBloque.setEstadoBloque7(EstadoProcesoArchivoEnum.PROCESADO_VS_BD);
                            estadoArchivoPorBloque.setAccionBloque7(accion);
                            success = true;
                            consultasPila.actualizarIndicePlanilla(indicePlanilla);
                            estadoArchivoPorBloque = agregarFechaActualizacion(estadoArchivoPorBloque,
                                    inconsistenciaRegistroAporteDTO.getBloque());
                            consultasPila.actualizarEstadoBloque(estadoArchivoPorBloque, historialEstado);
                            consultasStaging.actualizarRegistroGeneral(registroGeneral);

                        }
                    }
                    else {
                        success = false;
                    }

                }
            }

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return success;
    }

    /**
     * Método encargado de asignar la fecha actual como fecha de actualización de estado de planilla PILA respecto
     * a un bloque de validación
     * @param estadoArchivoPorBloque
     * @param bloque
     * @return
     */
    private EstadoArchivoPorBloque agregarFechaActualizacion(EstadoArchivoPorBloque estadoArchivoPorBloque, BloqueValidacionEnum bloque) {
        String firmaMetodo = "agregarFechaActualizacion(EstadoArchivoPorBloque, BloqueValidacionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date fechaActual = new Date();
        EstadoArchivoPorBloque estadoTemp = estadoArchivoPorBloque;

        switch (bloque) {
            case BLOQUE_0_OI:
                estadoTemp.setFechaBloque0(fechaActual);
                break;
            case BLOQUE_1_OI:
                estadoTemp.setFechaBloque1(fechaActual);
                break;
            case BLOQUE_2_OI:
                estadoTemp.setFechaBloque2(fechaActual);
                break;
            case BLOQUE_3_OI:
                estadoTemp.setFechaBloque3(fechaActual);
                break;
            case BLOQUE_4_OI:
                estadoTemp.setFechaBloque4(fechaActual);
                break;
            case BLOQUE_5_OI:
                estadoTemp.setFechaBloque5(fechaActual);
                break;
            case BLOQUE_6_OI:
                estadoTemp.setFechaBloque6(fechaActual);
                break;
            case BLOQUE_7_OI:
                estadoTemp.setFechaBloque7(fechaActual);
                break;
            case BLOQUE_8_OI:
                estadoTemp.setFechaBloque8(fechaActual);
                break;
            case BLOQUE_9_OI:
                estadoTemp.setFechaBloque9(fechaActual);
                break;
            case BLOQUE_10_OI:
                estadoTemp.setFechaBloque10(fechaActual);
                break;
            default:
                break;

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return estadoTemp;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#validarRespuestaCambioId()
     */
    @Override
    public InconsistenciaDTO validarRespuestaCambioId(Long idErrorValidacion) {
        String firmaMetodo = "validarRespuestaCambioId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        InconsistenciaDTO inconsistencia = consultasPila.validarRespuestaCambioId(idErrorValidacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return inconsistencia;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService
     *      #actualizarEmpleadoresGestionadosBandejaCero(java.util.List, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void actualizarEmpleadoresGestionadosBandejaCero(List<Long> idEmpleadores, UserDTO user) {
        String firmaMetodo = "actualizarEmpleadoresGestionadosBandejaCero(List<Long>, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.actualizarEmpleadoresGestionadosBandejaCero(idEmpleadores, user);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#mantenerAfiliacionEmpleadoresBandejaCero(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void mantenerAfiliacionEmpleadoresBandejaCero(List<Long> idEmpleadores, UserDTO user) {
        String firmaMetodo = "mantenerAfiliacionEmpleadoresBandejaCero(List<Long>, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasCore.mantenerAfiliacionEmpleadoresBandejaCero(idEmpleadores, user);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para la actualización de un registro detallado
     * @param registroDetallado
     *        <code>RegistroDetallado</code>
     *        Registro detallado a actualizar
     */
    private RegistroDetallado actualizarRegistroDetallado(RegistroDetallado registroDetallado) {
        String firmaMetodo = "actualizarRegistroDetallado(RegistroDetallado)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        consultasStaging.actualizarRegistroDetallado(registroDetallado);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return registroDetallado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#buscarPorPersonaCriterios(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorPersonaCriterios(TipoIdentificacionEnum tipoIdentificacion,
            String numIdentificacion, String numeroPlanilla, String periodo, UserDTO userDTO, UriInfo uri, HttpServletResponse response) {
        String firmaMetodo = "buscarPorPersonaCriterios(TipoIdentificacionEnum tipoIdentificacion, String, String, String, UserDTO, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CriteriosDTO criterios = new CriteriosDTO();

        criterios.setTipoIdentificacion(tipoIdentificacion);
        criterios.setNumeroIdentificacion(numIdentificacion);
        criterios.setNumeroPlanilla(numeroPlanilla);
        criterios.setPeriodo(periodo);

        List<BusquedaPorPersonaRespuestaDTO> result = consultasStaging.buscarPorPersonaCriterios(criterios, uri, response);

        result = adicionarTipoArchivoPila(result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#BusquedaPorAportanteCriterios(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorAportanteCriterios(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentAportante, String numeroPlanilla, String periodo, Long registroControl, UserDTO userDTO, UriInfo uri,
            HttpServletResponse response) {
        String firmaMetodo = "buscarPorAportanteCriterios(TipoIdentificacionEnum, String, String, String, UserDTO, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CriteriosDTO criterios = new CriteriosDTO();

        criterios.setTipoIdentificacion(tipoIdentificacion);
        criterios.setNumeroIdentificacion(numeroIdentAportante);
        criterios.setNumeroPlanilla(numeroPlanilla);
        criterios.setPeriodo(periodo);
        criterios.setRegistroControl(registroControl);

        List<BusquedaPorPersonaRespuestaDTO> result = consultasStaging.buscarPorAportanteCriterios(criterios, uri, response);

        result = adicionarTipoArchivoPila(result);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de asignar el tipo de archivo de planilla PILA para cada uno de los resultados (cuando aplica)
     * @param result
     * @return
     */
    private List<BusquedaPorPersonaRespuestaDTO> adicionarTipoArchivoPila(List<BusquedaPorPersonaRespuestaDTO> result) {
        String firmaMetodo = "adicionarTipoArchivoPila(List<BusquedaPorPersonaRespuestaDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<BusquedaPorPersonaRespuestaDTO> resultAct = result;
        Map<Long, TipoArchivoPilaEnum> listaTiposArchivos = new HashMap<>();

        for (BusquedaPorPersonaRespuestaDTO resp : result) {
            Long idPlanilla = resp.getCabecera() != null && resp.getCabecera().getRegistroControl() != null
                    ? resp.getCabecera().getRegistroControl() : null;

            if (idPlanilla != null && !listaTiposArchivos.containsKey(idPlanilla)) {
                IndicePlanillaModeloDTO indice = consultasPila.consultarIndicesOIporIdPlanilla(resp.getCabecera().getRegistroControl());
                resp.setTipoArchivo(indice.getTipoArchivo());
                listaTiposArchivos.put(idPlanilla, indice.getTipoArchivo());
            }
            else {
                resp.setTipoArchivo(listaTiposArchivos.get(idPlanilla));
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultAct;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#detalleAportanteCriterios(java.lang.Long,
     *      com.asopagos.rest.security.dto.UserDTO, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<RegistroDetalladoModeloDTO> detalleAportanteCriterios(Long registroControl, UserDTO userDTO, UriInfo uri,
            HttpServletResponse response) {
        String firmaMetodo = "PilaBandejaBusiness.detalleAportanteCriterios(Long, UserDTO, UriInfo, HttpServletResponse)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        CriteriosDTO criterios = new CriteriosDTO();
        criterios.setRegistroControl(registroControl);

        List<RegistroDetalladoModeloDTO> result = consultasStaging.detalleAportanteCriterios(criterios, uri, response);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#actualizarEstadoPlanillaNuevoAportante(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    public IndicePlanilla actualizarEstadoPlanillaNuevoAportante(IndicePlanilla indicePlanilla, UserDTO user) {
        String firmaMetodo = "PilaBandejaBusiness.actualizarEstadoPlanillaNuevoAportante(IndicePlanilla, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IndicePlanilla result = consultasPila.modificarEstadosArchivo(indicePlanilla, user.getNombreUsuario());

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    @Override
    public void aprobarDetalles(List<Long> IdsDetallados, Boolean reproceso, String Usuario) {
            List<Long> sublist = null;
            // se actualizan los estados de los registros detallados con la marca de "_APROBADO" en paquetes de 1000 registros
            sublist = new ArrayList<>();
            for (Long idRegDet : IdsDetallados) {
                sublist.add(idRegDet);
                if (sublist.size() == 1000){
                    consultasStaging.aprobarRegistrosDetalladosPorId(sublist, Usuario);
                    sublist.clear();
                }
            }

            if (!sublist.isEmpty()){
                consultasStaging.aprobarRegistrosDetalladosPorId(sublist, Usuario);
            }
    }
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public PreparacionAprobacion399DTO aprobarRegistrosBandeja399(PreparacionAprobacion399DTO datosAprobacion) {
        String firmaServicio = "PilaBandejaBusiness.aprobarRegistrosBandeja399(PreparacionAprobacion399DTO "+datosAprobacion.toString()+")";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info("MANTIS 266511 PilaBandejaBusiness.aprobarRegistrosBandeja399 ln 2089");
        if (datosAprobacion.getIdsRegistrosGenerales() != null && !datosAprobacion.getIdsRegistrosGenerales().isEmpty()
                && datosAprobacion.getUsuarioAprobador() != null && datosAprobacion.getIdTransaccion() != null) {

            BloqueValidacionEnum bloque = BloqueValidacionEnum.BLOQUE_6_OI;

            if (FasePila2Enum.PILA2_FASE_2.equals(datosAprobacion.getFase())) {
                bloque = BloqueValidacionEnum.BLOQUE_7_OI;
            }
            
            List<Long> sublist = null;
            
            
            logger.info("MANTIS 266511 PilaBandejaBusiness.aprobarRegistrosBandeja399 ln 2119 ");
            // en los casos de simulación, se evalua sí se cambia el estado de los registros generales
            if (datosAprobacion.getEsSimulado()) {
            	sublist = new ArrayList<>();
            	for (Long idRegGen : datosAprobacion.getIdsRegistrosGenerales()) {
            		sublist.add(idRegGen);
            		if(sublist.size() == 1000){
    					consultasStaging.asignarIdTransaccionYEstadoBase(sublist, datosAprobacion.getIdTransaccion(),
    							bloque, Boolean.TRUE);
    					sublist.clear();
    				}
            	}
            	
            	if(!sublist.isEmpty()){
            		consultasStaging.asignarIdTransaccionYEstadoBase(sublist, datosAprobacion.getIdTransaccion(),
							bloque, Boolean.TRUE);
            	}
            }else{
            	// se actualiza el ID de transacción en los registros generales en paquetes de 1000 registros
            	logger.info("MANTIS 266511 PilaBandejaBusiness.aprobarRegistrosBandeja399 ingresa hacer simulación ln 2140");
            	sublist = new ArrayList<>();
            	for (Long idRegGen : datosAprobacion.getIdsRegistrosGenerales()) {
            		sublist.add(idRegGen);
            		if(sublist.size() == 1000){
            			consultasStaging.asignarIdTransaccionYEstadoBase(sublist, datosAprobacion.getIdTransaccion(), bloque,
                                Boolean.FALSE);
    					sublist.clear();
    				}
            	}
            	
            	if(!sublist.isEmpty()){
        			consultasStaging.asignarIdTransaccionYEstadoBase(sublist, datosAprobacion.getIdTransaccion(), bloque,
                            Boolean.FALSE);
            	}
            }

            datosAprobacion.setSucess(Boolean.TRUE);
        }
        else {
            String mensaje = "No se cuentan con los datos requeridos para la operación solicitada";
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaServicio + " - " + mensaje);
            throw new TechnicalException(mensaje);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return datosAprobacion;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#consultarDatosActualizacionPlanilla(java.util.List,
     *      java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ActualizacionEstadosPlanillaDTO> consultarDatosActualizacionPlanilla(List<Long> idsRegistroGeneral, Boolean esSimulado,
            Boolean esReproceso) {
        String firmaServicio = "PilaBandejaBusiness.consultarDatosActualizacionPlanilla(List<Long>, Boolean, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ActualizacionEstadosPlanillaDTO> result = new ArrayList<>();

        List<RegistroGeneralModeloDTO> registrosGenerales = consultasStaging.consultarRegistrosGeneralesPorListaId(idsRegistroGeneral,
                esReproceso, esSimulado);

        for (RegistroGeneralModeloDTO regGen : registrosGenerales) {
            ActualizacionEstadosPlanillaDTO actualizacionDTO = new ActualizacionEstadosPlanillaDTO();

            actualizacionDTO.setIdRegistroGeneral(regGen.getId());
            actualizacionDTO.setEstadoProceso(regGen.getOutEstadoArchivo());
            actualizacionDTO.setMarcaHabilitacionGestionManual(esSimulado);
            actualizacionDTO.setActualizaRegistroGeneral(Boolean.FALSE);

            switch (regGen.getOutEstadoArchivo()) {
                case PROCESADO_VS_BD:
                    actualizacionDTO.setAccionProceso(!esSimulado ? AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE
                            : AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE_MANUAL);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_7_OI);
                    break;
                case PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD:
                    actualizacionDTO.setAccionProceso(AccionProcesoArchivoEnum.PASAR_A_BANDEJA);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_7_OI);
                    break;
                case REGISTRADO_O_RELACIONADO_LOS_APORTES:
                    actualizacionDTO.setAccionProceso(!esSimulado ? AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA
                            : AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA_MANUAL);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_8_OI);
                    break;
                case PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES:
                    actualizacionDTO.setAccionProceso(AccionProcesoArchivoEnum.PASAR_A_BANDEJA);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_8_OI);
                    break;
                case PROCESADO_NOVEDADES:
                case PROCESADO_SIN_NOVEDADES:
                    actualizacionDTO.setAccionProceso(
                            !esSimulado ? AccionProcesoArchivoEnum.NOTIFICAR_RECAUDO : AccionProcesoArchivoEnum.NOTIFICAR_RECAUDO_MANUAL);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_9_OI);
                    break;
                case RECAUDO_NOTIFICADO:
                case RECAUDO_NOTIFICADO_MANUAL:
                    actualizacionDTO.setAccionProceso(AccionProcesoArchivoEnum.PROCESO_FINALIZADO);
                    actualizacionDTO.setBloqueValidacion(BloqueValidacionEnum.BLOQUE_10_OI);
                    break;
                default:
                    break;
            }

            result.add(actualizacionDTO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#recalcularEstadoRegistroGeneral(java.lang.Long)
     */
    @Override
    public void recalcularEstadoRegistroGeneral(Long idTransaccion) {
        String firmaServicio = "PilaBandejaBusiness.recalcularEstadoRegistroGeneral(Long "+idTransaccion+")";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        consultasStaging.recalcularEstadoRegistroGeneral(idTransaccion);

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.service.PilaBandejaService#validarExistenciaPersona(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean validarExistenciaPersona(TipoIdentificacionEnum tipoId, String numId){
        String firmaServicio = "PilaBandejaBusiness.validarExistenciaPersona(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        Boolean result = consultasCore.buscarPersona(tipoId, numId);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result != null ? result : Boolean.FALSE;
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DatosBandejaTransitoriaDTO> bandejaTransitoriaGestion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String numeroPlanilla, Long fechaInicio, Long fechaFin, UserDTO userDTO) {	
		List<DatosBandejaTransitoriaDTO> datosBandeja = new ArrayList<DatosBandejaTransitoriaDTO>();
		//List<PilaEstadoTransitorio> accion = consultasCore.bandejaTransitoriaGestion(); 
		
		 List<PilaEstadoTransitorio> accion = consultasCore.bandejaTransitoriaGestionParam( tipoIdentificacion,
                 numeroIdentificacion,  numeroPlanilla,  fechaInicio,  fechaFin); 
		  
		 
		if (!accion.isEmpty()) {
			logger.info("accion " + accion.size());
			List<Long> listaIdPlanilla = new ArrayList<Long>();
			List<IndicePlanilla> indicePlanillas;
			Map<Long, PilaEstadoTransitorio> planillasBandeja = new HashMap<Long, PilaEstadoTransitorio>();
			DatosBandejaTransitoriaDTO tmp;
			int cont = 0;
			for (PilaEstadoTransitorio pet : accion) {
				cont++;
				listaIdPlanilla.add(pet.getPilaIndicePlanilla());
				planillasBandeja.put(pet.getPilaIndicePlanilla(), pet);

				if (cont == 1000) {
					logger.info("pas 1" + listaIdPlanilla.size());
					indicePlanillas = consultasPila.listaIndicePlanilla(listaIdPlanilla);
					for (IndicePlanilla pip : indicePlanillas) {
						if (planillasBandeja.containsKey(pip.getId())) {
							tmp = new DatosBandejaTransitoriaDTO(planillasBandeja.get(pip.getId()),
									new IndicePlanillaModeloDTO(pip));
							datosBandeja.add(tmp);
						}
					}
					cont = 0;
					listaIdPlanilla = new ArrayList<Long>();
				}
			}

			if (cont > 0) {
				logger.info("pas 2" + listaIdPlanilla.size());
				indicePlanillas = consultasPila.listaIndicePlanilla(listaIdPlanilla);
				for (IndicePlanilla pip : indicePlanillas) {
					if (planillasBandeja.containsKey(pip.getId())) {
						tmp = new DatosBandejaTransitoriaDTO(planillasBandeja.get(pip.getId()),
								new IndicePlanillaModeloDTO(pip));
						datosBandeja.add(tmp);
					}
				}
			}
		}
		return datosBandeja;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DatosBandejaTransitoriaDTO detalleBandejaTransitoriaGestion(Long idPilaEstadoTransitorio,
			UserDTO userDTO) {
		PilaEstadoTransitorio pet = consultasCore.consultarBandejaTransitoriaGestion(idPilaEstadoTransitorio);
		if (pet != null) {
			IndicePlanilla pip = consultasPila.obtenerIndicePlanilla(pet.getPilaIndicePlanilla());
			if (pip != null) {
				RegistroGeneral reg = consultasStaging.consultarRegistroGeneralxRegistroControl(pet.getPilaIndicePlanilla());
				if (reg != null) {
					List<TemNovedadModeloDTO> novedades = consultasPila.consultarNovedadesTemporales(reg.getId());
					DatosBandejaTransitoriaDTO dbt = new DatosBandejaTransitoriaDTO(pet, new IndicePlanillaModeloDTO(pip));
					dbt.setNovedades(novedades);
					return dbt;
				}
			}
		}
		return null;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExcepcionNovedadPilaModeloDTO> consultarExcepcionNovedadPila(Long idTempNovedad, UserDTO userDTO) {
		return consultasCore.consultarExcepcionNovedadPila(idTempNovedad);
	}
	
	@Override
	public Boolean actualizarEstadoBandejaTransitoriaGestion(Long indicePlanilla, UserDTO userDTO) {
		return consultasCore.actualizarEstadoBandejaTransitoriaGestion(indicePlanilla);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RegistroGeneral consultarRegistroGeneralxRegistroControl(Long indicePlanilla, UserDTO userDTO) {
		return consultasStaging.consultarRegistroGeneralxRegistroControl(indicePlanilla);
	}
	
	@Override
	public Boolean actualizarEstadoEnProcesoAportes(Long indicePlanilla, UserDTO userDTO) {
		return consultasPila.actualizarEstadoEnProcesoAportes(indicePlanilla);
	}
}
