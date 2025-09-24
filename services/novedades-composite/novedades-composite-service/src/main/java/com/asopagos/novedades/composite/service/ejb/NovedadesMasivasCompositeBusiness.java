package com.asopagos.novedades.composite.service.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.clients.ObtenerInfoAfiliadoRespectoEmpleador;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.Vista360PersonaCabeceraDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmailEmpleadores;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresaByCodigoAndEmpleador;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresaByNombreAndEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.clients.*;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.novedades.dto.SucursalPersonaDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.novedadescompositerutines.procesaractivacionbeneficiarioPILA.ProcesarActivacionBeneficiarioPILARutine;
import com.asopagos.rutine.novedadesrutines.actualizarestadosolicitudnovedad.ActualizarEstadoSolicitudNovedadRutine;
import com.asopagos.rutine.novedadesrutines.crearintentonovedad.CrearIntentoNovedadRutine;
import com.asopagos.util.PersonasUtils;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.historicos.clients.ObtenerGruposFamiliaresAfiPrincipal;
import com.asopagos.historicos.dto.BeneficiarioGrupoFamiliarDTO;
import com.asopagos.historicos.dto.PersonaComoAfiPpalGrupoFamiliarDTO;
import com.asopagos.novedades.clients.VerificarEstructuraArchivoRetiroTrabajadores;
import com.asopagos.novedades.composite.clients.ProcesarArchivoActualizacionSucursal;
import com.asopagos.novedades.composite.clients.ProcesarArchivoConfirmacionAbonosBancario;
import com.asopagos.novedades.composite.clients.ProcesarArchivoReintegroTrabajadorIndividual;
import com.asopagos.novedades.composite.clients.ProcesarArchivoRetiroBenficiarioIndividual;
import com.asopagos.novedades.composite.clients.ProcesarArchivoRetiroTrabajadorIndividual;
import com.asopagos.novedades.composite.clients.ProcesarArchivoSustitucionPatronal;
import com.asopagos.novedades.clients.ObtenerTitularCuentaAdministradorSubsidioByCasId;
import com.asopagos.entidades.subsidiomonetario.pagos.ConfirmacionAbonos;


//import com.asopagos.afiliaciones.personas.composite.clients.RadicarSolicitudAbreviadaAfiliacionPersonaAfiliados;
import com.asopagos.novedades.composite.service.NovedadesMasivasCompositeService;
import com.asopagos.novedades.composite.service.constants.NamedQueriesConstants;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.Asynchronous;
import org.apache.commons.collections.CollectionUtils;

import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import java.math.BigDecimal;
import com.asopagos.subsidiomonetario.pagos.clients.ConfirmarResultadosAbonosBancariosArchivo;
import com.asopagos.subsidiomonetario.pagos.composite.clients.RegistrarCambioMedioDePagoSubsidio;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroCambioMedioDePagoDTO;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.historicos.clients.ConsultarResumenGruposFamiliares;
import com.asopagos.historicos.dto.PersonaComoAdminSubsidioDTO;
import com.asopagos.personas.clients.CabeceraVista360Persona;
import com.asopagos.subsidiomonetario.pagos.composite.clients.RegistrarCambioMedioDePagoSubsidioArchivo;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.entidades.transversal.core.CargueTrasladoMedioPagoTranferencia;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;
import com.asopagos.enumeraciones.core.ErroresConsolaEnum;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarProcesoConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueProcesoMasivo;
import javax.persistence.NoResultException;
import com.asopagos.entidades.transversal.core.Requisito;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.entidades.transversal.core.CargueCertificadosMasivos;
import com.asopagos.empleadores.clients.GenerarCertificadosMasivos;
import com.asopagos.enumeraciones.core.TipoCertificadoMasivoEnum;
import com.asopagos.novedades.composite.clients.VerificarEstructuraArchivoCertificadosMasivos;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import javax.ws.rs.core.Response;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.*;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.EliminarArchivo;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.ControlCertificadosMasivos;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import com.asopagos.archivos.clients.ConsultarArchivoInfoHeader;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.BeneficiarioDTO;
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con las novedades de empleadores o personas Masivas. <b>Historia de Usuario:</b>
 * proceso 1.3
 *
 * @author Maria Cuellar <maria.cuellar@eprocess.com.co>
 */
@Stateless
public class NovedadesMasivasCompositeBusiness implements NovedadesMasivasCompositeService {

    /**
     *
     */
    private static final String RADICAR = "RADICAR";
    /**
     * Constante con la clave para el número de identificación.
     */
    private static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
    /**
     * Constante con la clave para el tipo de identificación.
     */
    private static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    /**
     * Constante con la calve para requeriri inactivación web.
     */
    private static final String REQUIERE_INACTIVACION_WEB = "requiereInactivacionWeb";
    /**
     * Constante con la clave para la fecha de nacimiento.
     */
    private static final String FECHA_NACIMIENTO = "fechaNacimiento";
    /**
     * Constante con la clave para el segundo apellido.
     */
    private static final String SEGUNDO_APELLIDO = "segundoApellido";
    /**
     * Constnate con la clave para el primer apellido.
     */
    private static final String PRIMER_APELLIDO = "primerApellido";
    /**
     * Constante con la clave para el segundo nombre.
     */
    private static final String SEGUNDO_NOMBRE = "segundoNombre";
    /**
     * Constante con la clave para el primero nombre.
     */
    private static final String PRIMER_NOMBRE = "primerNombre";
    /**
     * Constante INTENTO.
     */
    private static final String INTENTO = "INTENTO";
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesMasivasCompositeBusiness.class);

    /**
     * Constante con la clave para la razon social
     */
    private static final String RAZON_SOCIAL = "razonSocial";
    /**
     * Representa el estado sin información de un aportante
     */
    private static final String SIN_INFORMACION = "SIN_INFORMACION";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String NATURALEZA_JURIDICA = "naturalezaJuridica";

    /**
     * Constante con la clave para la actividad economica
     */
    private static final String ACTIVIDAD_ECONOMICA = "actividadEconomica";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String MUNICIPIO = "municipio";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String DIRECCION = "direccion";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String EMAIL = "email";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String GENERO = "genero";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String FECHA_EXPEDICION = "fechaExpedicion";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String NIVEL_EDUCATIVO = "nivelEducativo";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String NUMERO_TRABAJADORES = "numeroEmpleados";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String FECHA_CONSTITUCION = "fechaConstitucion";
    /**
     * Constante con la clave para la naturaleza juridica
     */
    private static final String TELEFONO_REPRE = "telefonoRepresentante";

    /**
     * Constante CERRAR
     */
    private static final String CERRAR = "CERRAR";

    /**
     * Constante para el nombre del bloque de validaciones a aplicar en el
     * cargue multiple de novedades
     */
    private static final String BLOQUE_VALIDACION_CARGUE_MULTIPLE_NOVEDADES = "CARGA_MULTIPLE_NOVEDADES";

    /**
     * Indica si la tarea fue previamente suspendida
     */
    private Boolean tareaSuspendida = Boolean.FALSE;

    @Resource//(lookup="java:jboss/ee/concurrency/executor/novedades")
    private ManagedExecutorService managedExecutorService;

    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;

	    /**
     * Consulta un archivo deacuerdo con el id del ECM
     *
     * @param archivoId
     * @return
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia obtenerArchivo(String)");
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        String[] parts = archivoId.split("_");
        archivoId = parts[0];
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }

    /**
     * Realiza el registro del cargue de archivo de actualizacion de informacion
     *
     * @param cargueArchivoActualizacionDTO Informacion cargue archivo
     * actualizacion
     * @return Identificador del cargue
     */
    private Long crearActualizarCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        CrearCargueArchivoActualizacion crearCargueArchivoActualizacion = new CrearCargueArchivoActualizacion(
                cargueArchivoActualizacionDTO);
        crearCargueArchivoActualizacion.execute();
        return crearCargueArchivoActualizacion.getResult();
    }

    /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple
     *
     * @param consolaEstadoCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
        registroConsola.execute();
    }

    /**
     * Método encargado de realizar el llamado al cliente que actualiza el
     * estado del cargue
     * <p>
     * id del cargue a actualizar datos que seran actualizados
     */
    private void actualizarCargueConsolaEstado(Long idCargue,
            ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        ActualizarCargueConsolaEstado actualizacion = new ActualizarCargueConsolaEstado(idCargue,
                consolaEstadoCargueProcesoDTO);
        actualizacion.execute();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoRetiroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoRetiroTrabajadores(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {
        logger.info("Inicio validarArchivoRetiroTrabajadores (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_RETIRO_TRABAJADORES);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas

        logger.info("ENTRA A VALIDAR");
        VerificarEstructuraArchivoRetiroTrabajadores verificarArchivo = new VerificarEstructuraArchivoRetiroTrabajadores(tipoArchivo, archivo);
        logger.info("sale de validar");
        verificarArchivo.execute();
        logger.info("sale de validar 2");
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
        logger.info("sale de validar 3");

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

        //Si hay registros validos procesamos esos registros validos.
        logger.info("ENTRA EN EL VALIDADOR");
        if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
            List<SolicitudNovedadDTO> list = procesarNovedadRetiroTrabajadores(tipoArchivo,
                    resultDTO.getListActualizacionInfoNovedad(), idCargue, userDTO);

            for (SolicitudNovedadDTO solNovedadDTO : list) {
//                logger.info("Notificar solNovedadDTO.getResultadoValidacion()" + solNovedadDTO.getResultadoValidacion());
                if(solNovedadDTO.getResultadoValidacion() != null){
                    if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
    //                    logger.info("Notificar solNovedadDTO.getIdSolicitud()" + solNovedadDTO.getIdSolicitud());
                        enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);
                    }
                }
            }

            //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                logger.info("ENTRA CON FINALIZAR CON ERRORES");
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                //Solo hay registros validos, entonces debe finalizar sin errores
                logger.info("ENTRA CON FINALIZAR SIN ERRORES");
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }
        } else {
            logger.info("ARCHIVO CON INCONSISTENCIA ESTRUCTURA");
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        }

        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);

        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_RETIRO_TRABAJADORES);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

        logger.info("Fin validarArchivoRetiroTrabajadores (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
//        return resultDTO;

    }

    /**
     * Procesa la novedad de retiro afiliado trabajador
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     */
    private List<SolicitudNovedadDTO> procesarNovedadRetiroTrabajadores(TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadRetiroTrabajadores(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        logger.info("Inicia procesarNovedadRetiroTrabajadores(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        // Se elimina la concurrencia debido a los retiros de afiliados con afiliacion multiple
        // TODO: pendiente las novedades en cascada para beneficiarios de forma concurrente
        // Debido a que los procesos son paralelos no se sabe si los beneficiarios deben retirarse o no. No los retira
        // GLPI 79634
        List<SolicitudNovedadDTO> listResultadoProcesamiento = new ArrayList<>();

        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            informacionActualizacionNovedadDTO.setIdCargue(idCargue);

            if (tipoArchivo.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {
                ProcesarArchivoRetiroTrabajadorIndividual procesarArchivoSrv = new ProcesarArchivoRetiroTrabajadorIndividual(informacionActualizacionNovedadDTO);
                procesarArchivoSrv.execute();
                listResultadoProcesamiento.add(procesarArchivoSrv.getResult());
            } else {//TipoArchivoRespuestaEnum.BENEFICIARIO
                ProcesarArchivoRetiroBenficiarioIndividual procesarArchivoSrv = new ProcesarArchivoRetiroBenficiarioIndividual(informacionActualizacionNovedadDTO);
                procesarArchivoSrv.execute();
                listResultadoProcesamiento.add(procesarArchivoSrv.getResult());
            }
        }
        /**
         * 
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                informacionActualizacionNovedadDTO.setIdCargue(idCargue);

                if (tipoArchivo.equals(TipoArchivoRespuestaEnum.AFILIADO_PRINCIPAL)) {
                    ProcesarArchivoRetiroTrabajadorIndividual procesarArchivoSrv = new ProcesarArchivoRetiroTrabajadorIndividual(informacionActualizacionNovedadDTO);
                    procesarArchivoSrv.execute();
                    return procesarArchivoSrv.getResult();
                } else {//TipoArchivoRespuestaEnum.BENEFICIARIO
                    ProcesarArchivoRetiroBenficiarioIndividual procesarArchivoSrv = new ProcesarArchivoRetiroBenficiarioIndividual(informacionActualizacionNovedadDTO);
                    procesarArchivoSrv.execute();
                    return procesarArchivoSrv.getResult();
                }
            };
            tareasParalelas.add(parallelTask);
        }

        try {
            List<Future<SolicitudNovedadDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<SolicitudNovedadDTO> future : listInfoArchivoFuture) {
                listResultadoProcesamiento.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona procesarNovedadRetiroTrabajadores(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)", e);
            throw new TechnicalException(e);
        }
        
        */
        return listResultadoProcesamiento;
    }

    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de retiro
     * trabajador
     *
     * @param informacionActualizacionNovedadDTO
     * @return
     */
    @Override
    public SolicitudNovedadDTO procesarArchivoRetiroTrabajadorIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO) {

        logger.info("Inicia procesarArchivoRetiroTrabajadorIndividual");

        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
//        printJsonMessage(informacionActualizacionNovedadDTO, "informacionActualizacionNovedadDTO");

        //Set datos
        solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION);
        solNovedadDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE);
        solNovedadDTO.setObservaciones("Registro proveniente de actualización masiva - Retiro trabajador");
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        solNovedadDTO.setCargaMultiple(true);
        solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());

        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
//        datosPersona.setTipoSolicitanteTrabajador(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        datosPersona.setTipoIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
//        datosPersona.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        datosPersona.setMotivoDesafiliacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getMotivoDesafiliacionTrabajador());
        datosPersona.setMotivoDesafiliacionBeneficiario(informacionActualizacionNovedadDTO.getAfiliado().getMotivoDesafiliacionBeneficiario());

        datosPersona.setFechaRetiro(informacionActualizacionNovedadDTO.getAfiliado().getFechaRetiroAfiliado());
        datosPersona.setEstadoAfiliacionTrabajador(EstadoAfiliadoEnum.ACTIVO);
        solNovedadDTO.setDatosPersona(datosPersona);

        List<EmpleadorRelacionadoAfiliadoDTO> empleadorRelacionadoAfiliadoDTO = consultarEmpleadoresRelacionadosAfiliado(
                informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getNumeroIdentificacion(),
                datosPersona.getTipoIdentificacion(),
                datosPersona.getNumeroIdentificacion());

        if (CollectionUtils.isNotEmpty(empleadorRelacionadoAfiliadoDTO)) {
//            logger.info("empleadorRelacionadoAfiliadoDTO: " + empleadorRelacionadoAfiliadoDTO.size());
            Long idRolAFiliado = empleadorRelacionadoAfiliadoDTO.get(0).getIdRolAfiliado();
            solNovedadDTO.getDatosPersona().setIdRolAfiliado(idRolAFiliado);
        }
        solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);

        return solNovedadDTO;
    }

    private List<EmpleadorRelacionadoAfiliadoDTO> consultarEmpleadoresRelacionadosAfiliado(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {

        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresRelacionados = new ObtenerEmpleadoresRelacionadosAfiliado(
                tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, null, numeroIdAfiliado);
        obtenerEmpleadoresRelacionados.execute();
        return obtenerEmpleadoresRelacionados.getResult();
    }

    /**
     * Método que consulta una novedad
     *
     * @param idSolicitud es el identificador de la novedad
     * @return Objeto solicitud novedad
     */
    private ParametrizacionNovedadModeloDTO consultarNovedad(TipoTransaccionEnum tipoTransaccion) {
        logger.info("**__**INICIA consultarNovedad" + tipoTransaccion);
        return NovedadesCompositeUtils.consultarNovedad(tipoTransaccion);
    }

    private void printObjectToJson(Object objeto) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(objeto);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(NovedadesMasivasCompositeBusiness.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.radicarSolicitudNovedadActualizacionesMasivas#
     * GLPI 55721   
     * radicarSolicitudNovedad(com.asopagos.novedades.dto.SolicitudNovedadDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    private SolicitudNovedadDTO radicarSolicitudNovedadActualizacionesMasivas(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO) {
        logger.info("***********Inicia radicarSolicitudNovedadActualizacionesMasivas");
        try {

            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            solNovedadDTO.setNovedadDTO(novedad);
//            logger.info("**__**INICIA EXITOSO EN RESULTADO VALIDACION ");
            if ((solNovedadDTO.getDatosPersona() != null
                    || solNovedadDTO.getDatosEmpleador() != null) && (solNovedadDTO.getResultadoValidacion() == null || solNovedadDTO.getContinuaProceso() == null || !solNovedadDTO.getContinuaProceso())) {
                /* se verifica si cumple con las validaciones de la novedad, se excluye el caso de una afiliacion de
                 * empleador(se hacen las mismas validaciones que en la afiliacion) */
//                logger.info("**__**INICIA EL SERVICIO DE VALIDACIONES ");
//                printObjectToJson(solNovedadDTO);
        //GLPI 55231 SE COMENTA VALIDACION DE REGLAS DE NEGOCIO PORQUE A NIVEL MASIVO NO HAY DONDE MOSTRA LAS VALIDACIONES EN PANTALLAS
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        //  solNovedadDTO.setResultadoValidacion(validarNovedad(solNovedadDTO));//revisar si puedo o debo pasar esto a la clase de validaciones
         
//                logger.info("**__**finaliza EL SERVICIO DE VALIDACIONES " + solNovedadDTO.getResultadoValidacion());              
//                printJsonMessage(solNovedadDTO, "solNovedadDTO.getResultadoValidacion() ln 563: ");
                
            }
            /* si el resultado es exitoso se procede a radicar */
            if (ResultadoRadicacionSolicitudEnum.EXITOSA.equals(solNovedadDTO.getResultadoValidacion()) //GLPI 51688   || (solNovedadDTO.getContinuaProceso()!= null && solNovedadDTO.getContinuaProceso())
                    ) {
                System.out.println("GLPI-45051");
                /*se crea la solicitud de novedad y asigna el nùmero de radicaciòn*/
                solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
//                logger.info("solNovedadDTO****--- Antes de crear la solicitud !!!!" + solNovedadDTO);                
                SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
                
                solNovedadDTO.setIdSolicitud(solicitudNovedad.getIdSolicitud());
                solNovedadDTO.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
                if(solNovedadDTO.getDatosEmpleador() != null){
                    solNovedadDTO.getDatosEmpleador().setListaChequeoNovedad(new ArrayList<>());
                    if(solNovedadDTO.getDatosEmpleador().getSucursalesOrigenSustPatronal() == null){
                        solNovedadDTO.getDatosEmpleador().setSucursalesOrigenSustPatronal(new ArrayList<>());
                    }
                    solNovedadDTO.getDatosEmpleador().setSociosEmpleador(new ArrayList<>());
                }
                if(solNovedadDTO.getDatosPersona() != null){
                    solNovedadDTO.getDatosPersona().setListaChequeoNovedad(new ArrayList<>());
                }
                guardarDatosTemporal(solNovedadDTO);
                
                // Se resuelve la novedad por medio de los convertidores
                resolverNovedad(solNovedadDTO, solicitudNovedad, userDTO);
                // Se envian comunicados masivamente
//                enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);
                // Se cierra la solicitud
                cerrarSolicitudNovedad(solNovedadDTO, true);
                

                // Se almacena los datos de la solicitud en datos temporales 
            }
            // Se registra intento de novedad
            else if (solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ENTIDAD_EXTERNA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.APORTE_MANUAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.CORRECCION_APORTE)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL_INT)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.PRESENCIAL)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION)
                    || solNovedadDTO.getCanalRecepcion().equals(CanalRecepcionEnum.ARCHIVO_CERTI_ESCOLAR)) {
                /*si las validaciones de negocio fallaron y el canal es web se guarda un intento de novedad*/
//                logger.info("**__**RESULTADO VALIDACION DIFERENTE A EXITOSA: " + solNovedadDTO.getResultadoValidacion());
                IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
                intentoNovedadDTO.setCanalRecepcion(solNovedadDTO.getCanalRecepcion());
                intentoNovedadDTO.setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum.VALIDACION_REGLAS_NEGOCIO);
                intentoNovedadDTO.setClasificacion(solNovedadDTO.getClasificacion());
                intentoNovedadDTO.setTipoTransaccion(solNovedadDTO.getTipoTransaccion());
                intentoNovedadDTO.setIdRegistroDetallado(solNovedadDTO.getIdRegistroDetallado());
                if (solNovedadDTO.getDatosEmpleador() != null) {
                    intentoNovedadDTO.setIdEmpleador(solNovedadDTO.getDatosEmpleador().getIdEmpleador());
                } else {
                    intentoNovedadDTO.setTipoIdentificacion(solNovedadDTO.getDatosPersona().getTipoIdentificacion());
                    intentoNovedadDTO.setNumeroIdentificacion(solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
                    intentoNovedadDTO.setIdBeneficiario(solNovedadDTO.getDatosPersona().getIdBeneficiario());
                    intentoNovedadDTO.setIdRolAfiliado(solNovedadDTO.getDatosPersona().getIdRolAfiliado());
                }
                solNovedadDTO.setIdSolicitud(registrarIntentoFallido(intentoNovedadDTO, userDTO));
                solNovedadDTO.setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum.RECHAZADA);
                /*se cierra la solicitud y se envia comunicado*/
//                parametrizarNotificacion(true, solNovedadDTO, userDTO);
                cerrarSolicitudNovedad(solNovedadDTO, true);
            }
            logger.info("Fin de método radicarSolicitudNovedadActualizacionesMasivas((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");
            return solNovedadDTO;
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error(
                    "Ocurrio un error inesperado en radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Obtiene el comunicado de acuerdo al tipo transaccion de la novedad
     * automatica registrada y se realiza el envio del comunicado de acuerdo a
     * la parametrizacion de notificacion
     *
     * @param solicitudNovedadDTO Informacion solicitud novedad
     * @param userDTO Informacion usuario
     */
    @Asynchronous
    private void enviarComunicadoMasivoNovedades(SolicitudNovedadDTO solicitudNovedadDTO, UserDTO userDTO) {
        logger.info("Inicio enviarComunicadoMasivoNovedades");
//        logger.info("Inicio solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name() !!!!" +solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name());
        // Comunicado 47.
        EtiquetaPlantillaComunicadoEnum comunicado = EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS;

        NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
        notificacion.setEtiquetaPlantillaComunicado(comunicado);
        notificacion.setProcesoEvento(solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name());
        notificacion.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
        notificacion.setTipoTx(solicitudNovedadDTO.getNovedadDTO().getNovedad());

        //Se setea el id del empleador en caso de existir
        notificacion.setIdEmpleador(solicitudNovedadDTO.getDatosEmpleador() != null ? solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador() : null);

        List<String> destinatarios = new ArrayList<>();

        if (solicitudNovedadDTO.getDatosPersona() != null
                && solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion() != null
                && solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion() != null) {

            TipoIdentificacionEnum tipoIdentificacion = solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion();
            String numeroIdentificacion = solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion();

            destinatarios.add(consultarEmailPersona(tipoIdentificacion, numeroIdentificacion));
            notificacion.setDestinatarioTO(destinatarios);
            Persona persona = consultarPersonaPorTipoNumeroID(tipoIdentificacion, numeroIdentificacion);
            if (persona != null) {
                notificacion.setIdPersona(persona.getIdPersona());
            }
            enviarComunicadoConstruido(notificacion);

        }
    }

    /**
     * Metodo encargado de consulta una persona por tipo y numero de ID
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private Persona consultarPersonaPorTipoNumeroID(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        List<Persona> personas = (List<Persona>) entityManager
                .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();
        if (!personas.isEmpty()) {
            return personas.get(0);
        } else {
            return null;
        }
    }

    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de retiro
     * beneficiario del trabajador
     *
     * @param informacionActualizacionNovedadDTO
     * @param idCargue
     * @return
     */
    @Override
    public SolicitudNovedadDTO procesarArchivoRetiroBenficiarioIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO) {

        logger.info("Inicia procesarArchivoRetiroBenficiarioIndividual");

        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();

        //Set datos
        solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.ARCHIVO_ACTUALIZACION);

        PersonaComoAfiPpalGrupoFamiliarDTO personaBeneficiario = consultarBeneficiariosTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion(),
                informacionActualizacionNovedadDTO.getBeneficiario().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getBeneficiario().getNumeroIdentificacion());

        BeneficiarioGrupoFamiliarDTO beneficiarioGrupoFamiliarDTO = personaBeneficiario.getBeneficiarios().get(0);

        solNovedadDTO.setClasificacion(beneficiarioGrupoFamiliarDTO.getParentezco());
        solNovedadDTO.setTipoTransaccion(obtenerTipoTransaccionBeneficiario(beneficiarioGrupoFamiliarDTO.getParentezco()));
        solNovedadDTO.setObservaciones("Registro proveniente de actualización masiva - Retiro beneficiario");
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        solNovedadDTO.setCargaMultiple(true);
        solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());

        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
        datosPersona.setTipoIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());

        datosPersona.setIdGrupoFamiliar(personaBeneficiario.getIdGrupoFamiliar());
        datosPersona.setMotivoDesafiliacionBeneficiario(informacionActualizacionNovedadDTO.getAfiliado().getMotivoDesafiliacionBeneficiario());
        datosPersona.setFechaRetiro(informacionActualizacionNovedadDTO.getAfiliado().getFechaRetiroAfiliado());

        datosPersona.setTipoIdentificacionBeneficiario(informacionActualizacionNovedadDTO.getBeneficiario().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacionBeneficiario(informacionActualizacionNovedadDTO.getBeneficiario().getNumeroIdentificacion());
        datosPersona.setPrimerNombreBeneficiario(beneficiarioGrupoFamiliarDTO.getPrimerNombre());
        datosPersona.setSegundoNombreBeneficiario(beneficiarioGrupoFamiliarDTO.getSegundoNombre());
        datosPersona.setPrimerApellidoBeneficiario(beneficiarioGrupoFamiliarDTO.getPrimerApellido());
        datosPersona.setSegundoApellidoBeneficiario(beneficiarioGrupoFamiliarDTO.getSegundoApellido());
        datosPersona.setClasificacion(solNovedadDTO.getClasificacion());
        datosPersona.setParentescoBeneficiarios(solNovedadDTO.getClasificacion());
        datosPersona.setIdBeneficiario(beneficiarioGrupoFamiliarDTO.getIdBeneficiario());

        if (ClasificacionEnum.CONYUGE.equals(solNovedadDTO.getClasificacion())) {
            datosPersona.setFechaFinsociedadConyugal(new Date().getTime());
        }

        solNovedadDTO.setDatosPersona(datosPersona);
        solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);

        return solNovedadDTO;
    }

    /**
     * Método encargado de ejecutar las validaciones de una novedad.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return resultado de las validaciones.
     */
    private ResultadoRadicacionSolicitudEnum validarNovedad(SolicitudNovedadDTO solNovedadDTO) {
        logger.info("Inicio de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();

        if (solNovedadDTO.getDatosEmpleador() != null) {
            Map<String, String> datosValidacion = llenarDatosValidacionEmpleador(solNovedadDTO);
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    solNovedadDTO.getTipoTransaccion().name(), solNovedadDTO.getTipoTransaccion().getProceso(),
                    solNovedadDTO.getClasificacion().name(), datosValidacion);
            validarReglasService.execute();
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
        } else if (solNovedadDTO.getDatosPersona() != null) {
            Map<String, String> datosValidacion = llenarDatosValidacionPersona(solNovedadDTO);
            ValidarReglasNegocio validarReglasService = new ValidarReglasNegocio(
                    solNovedadDTO.getTipoTransaccion().name(), solNovedadDTO.getTipoTransaccion().getProceso(),
                    solNovedadDTO.getClasificacion().name(), datosValidacion);
            validarReglasService.execute();
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
        }

        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        if (!validaciones.isEmpty()) {
            List<ValidacionDTO> listValidacionTipoUno = new ArrayList<>();
            // Se verifica el resultado de las validaciones
            resultado = verificarResultadoValidacion(listValidacionTipoUno, validaciones, solNovedadDTO);
            // Se agrega la lista de validaciones fallidas
            if (!listValidacionTipoUno.isEmpty()) {
                solNovedadDTO.setListResultadoValidacion(listValidacionTipoUno);
            }
        }
        logger.info("Fin de método validarNovedad(SolicitudNovedadDTO solNovedadDTO)");
        return resultado;
    }

    /**
     * Realiza el proceso de verificacion del resultado de las validaciones
     * ejecutadas, para indicar el comportamiento en pantalla
     *
     * @param listaValidacionesFallidas Contiene la lista de validaciones
     * fallidas por tipo de excepcion
     * @param validacionesEjecutadas Lista de validaciones ejecutadas
     * @param solNovedadD TO Informacion de la solicitud de novedad
     * @return Resultado de radicacion de la solicitud a partir del resultado de
     * las validaciones
     */
    private ResultadoRadicacionSolicitudEnum verificarResultadoValidacion(List<ValidacionDTO> listaValidacionesFallidas,
            List<ValidacionDTO> validacionesEjecutadas, SolicitudNovedadDTO solNovedadDTO) {
        // Resultado verificacion validaciones
        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        List<ValidacionDTO> listaValidacionesT2 = new ArrayList<>();
        List<ValidacionDTO> listaValidacionesT1 = new ArrayList<>();
        boolean excepcionUno = false;
        boolean excepcionDos = false;

        for (ValidacionDTO validacionDTO : validacionesEjecutadas) {
            logger.info("**__**validacionDTO.getValidacion(): "+validacionDTO.getValidacion()+" detalle: "+validacionDTO.getDetalle()+" Estado: "+validacionDTO.getResultado());
            // Si la validacion fue NO_APROBADA se verifica el tipo de excepcion para el comportamiento en pantalla
            if (validacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                resultado = ResultadoRadicacionSolicitudEnum.FALLIDA;
                if (TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacionDTO.getTipoExcepcion())) {
                    excepcionDos = true;
                    listaValidacionesT2.add(validacionDTO);
                } else {
                    listaValidacionesT1.add(validacionDTO);
                    excepcionUno = true;
                }
                // Se verifica si la validacion 90 de postulado FOVIS fallo
                if (validacionDTO.getValidacion().equals(ValidacionCoreEnum.VALIDACION_POSTULADO_FOVIS)) {
                    solNovedadDTO.setPostuladoFOVIS(true);
                }
                // Se verifica si la validacion 111 de novedad en proceso fallo
                if (validacionDTO.getValidacion().equals(ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO)) {
                    solNovedadDTO.setNovedadEnProceso(true);
                }
                // Se verifica si la validacion DE PERSONA FALLECIDA falló
                if (validacionDTO.getValidacion().equals(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA)) {
                    solNovedadDTO.setValidacionFallecido(true);
                }
            }
        }
        // Se identifica el error ocurrido y se agrega la lista de validaciones

        if (excepcionDos) {
            solNovedadDTO.setExcepcionTipoDos(true);
            listaValidacionesFallidas.addAll(listaValidacionesT2);
        } else if (excepcionUno) {
            solNovedadDTO.setExcepcionTipoDos(false);
            listaValidacionesFallidas.addAll(listaValidacionesT1);
        }
        return resultado;
    }

    /**
     * Método encargado de llenar los datos de validación segun la novedad.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionEmpleador(SolicitudNovedadDTO solNovedadDTO) {
        logger.debug("Inicio de método llenarDatosValidacion(DatosEmpleadorNovedadDTO datosEmpleadorDTO)");
        Map<String, String> datosValidacion = new HashMap<String, String>();
        /* todas las validaciones piden tipo y numero identificacion */
        if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO)) {
            datosValidacion.put(TIPO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getTipoIdentificacionNuevo().name());
            datosValidacion.put(NUMERO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionNuevo());
        } else {
            datosValidacion.put(TIPO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getTipoIdentificacion().name());
            datosValidacion.put(NUMERO_IDENTIFICACION, solNovedadDTO.getDatosEmpleador().getNumeroIdentificacion());
        }

        /* algunos datos adicionales dependiendo de la novedad */
        if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_RAZON_SOCIAL_NOMBRE)) {
            datosValidacion.put("razonSocial", solNovedadDTO.getDatosEmpleador().getRazonSocial());
            datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosEmpleador().getPrimerNombre());
            datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosEmpleador().getSegundoNombre());
            datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosEmpleador().getPrimerApellido());
            datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosEmpleador().getSegundoApellido());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion()
                        .equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB)) {
            if (solNovedadDTO.getDatosEmpleador().getTipoIdentificacionRepLegalSupl() != null) {
                datosValidacion.put("tipoIdentificacionRLS",
                        solNovedadDTO.getDatosEmpleador().getTipoIdentificacionRepLegalSupl().name());
                datosValidacion.put("numeroIdentificacionRLS",
                        solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionRepLegalSupl());
            }
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.SUSTITUCION_PATRONAL)) {
            StringBuilder idsPersonas = new StringBuilder();
            for (SucursalPersonaDTO sucursalPersona : solNovedadDTO.getDatosEmpleador().getTrabajadoresSustPatronal()) {
                idsPersonas.append(sucursalPersona.getIdPersona().toString());
                idsPersonas.append(",");
            }
            datosValidacion.put("idsPersonas", idsPersonas.substring(0, idsPersonas.length() - 1));
            datosValidacion.put("tipoIdentificacionEmpleadorDestino", solNovedadDTO.getDatosEmpleador().getTipoIdentificacionDestinoSustPatronal().name());
            datosValidacion.put("numeroIdentificacionEmpleadorDestino", solNovedadDTO.getDatosEmpleador().getNumeroIdentificacionDestinoSustPatronal());
            datosValidacion.put("idEmpleadorDestino", solNovedadDTO.getDatosEmpleador().getIdEmpleadorDestinoSustPatronal().toString());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES)) {
            StringBuilder idsPersonas = new StringBuilder();
            for (Long id : solNovedadDTO.getDatosEmpleador().getTrabajadoresTraslado()) {
                idsPersonas.append(id.toString());
                idsPersonas.append(",");
            }
            datosValidacion.put("idsPersonas", idsPersonas.substring(0, idsPersonas.length() - 1));
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB)
                || solNovedadDTO.getTipoTransaccion()
                        .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion()
                        .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB)) {
            datosValidacion.put("beneficioCubierto1429",
                    solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey1429().toString());
            if (solNovedadDTO.getDatosEmpleador().getAnoInicioBeneficioLey1429() != null) {
                datosValidacion.put("anioInicioBeneficio",
                        solNovedadDTO.getDatosEmpleador().getAnoInicioBeneficioLey1429().toString());
            }
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_WEB)
                || solNovedadDTO.getTipoTransaccion()
                        .equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_WEB)) {
            if (solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey590() != null) {
                datosValidacion.put("beneficioCubierto590",
                        solNovedadDTO.getDatosEmpleador().getEmpleadorCubiertoLey590().toString());
            }
            if (solNovedadDTO.getDatosEmpleador().getPeriodoFinBeneficioLey590() != null) {
                datosValidacion.put("anioInicioBeneficio",
                        solNovedadDTO.getDatosEmpleador().getPeriodoFinBeneficioLey590().toString());
            }
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.SUSTITUCION_PATRONAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.DESAFILIACION)) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosEmpleador().getRequiereInactivacionCuentaWeb().toString());
        } else if (solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL)
                || solNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB)) {
            datosValidacion.put("codigoSucursal", solNovedadDTO.getDatosEmpleador().getCodigoSucursal());
            datosValidacion.put("nombreSucursal", solNovedadDTO.getDatosEmpleador().getNombreSucursal());
        }
        if (solNovedadDTO.getDatosEmpleador() != null && solNovedadDTO.getDatosEmpleador().getIdSucursalEmpresa() != null) {
            datosValidacion.put("idSucursal", solNovedadDTO.getDatosEmpleador().getIdSucursalEmpresa().toString());
        }
        // Se agrega el tipo transaccion como dato para el proceso de validacion
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        logger.info("Fin de método llenarDatosValidacion(DatosEmpleadorNovedadDTO datosEmpleadorDTO)");
        return datosValidacion;
    }

    /**
     * Método que hace la peticion REST al servicio que crea una solicitud de
     * novedad.
     *
     * @param novedadDTO novedad a crearse.
     * @param userDTO usuario que radica la solicitud.
     */
    private SolicitudNovedadModeloDTO crearSolicitudNovedad(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
        logger.info("**__** crearSolicitudNovedad::  " + novedadDTO);
        printJsonMessage(novedadDTO.getDatosPersona(), "**__** crearSolicitudNovedad:: novedadDTO.getDatosPersona()");
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.crearSolicitudNovedad(novedadDTO, userDTO, entityManager);
    }

    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     *
     * @param solicitudNovedadDTO datos de la novedad.
     * @param solicitudNovedad solicitud a modificar.
     */
    private void resolverNovedad(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws Exception {
        logger.info("**__**inicio resolverNovedad");
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.resolverNovedad(solicitudNovedadDTO, solicitudNovedad, userDTO);
    }

    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     *
     * @param solicitudNovedadDTO datos de la novedad.
     * @param solicitudNovedad solicitud a modificar.
     */
    private void resolverNovedadReintegro(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedad, UserDTO userDTO) throws Exception {
        logger.info("**__**inicio resolverNovedadReintegro: " + entityManager);
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.resolverNovedadReintegroMismoEmpleador(solicitudNovedadDTO, solicitudNovedad, userDTO);
    }

    /**
     * Método encargado de parametrizar una notificación de un comunicado.
     *
     * @param idSolicitud id de la solicitud global.
     * @param intento intento de novedad.
     * @return notificacion parametrizada.
     */
    private void cerrarSolicitudNovedad(SolicitudNovedadDTO solicitudNovedadDTO, boolean intento) {
        logger.info("**__**inicio cerrarSolicitudNovedad");
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.cerrarSolicitudNovedad(solicitudNovedadDTO, intento);
    }

    public void actualizarResultadoProcesoSolicitud(Long idSolicitud, ResultadoProcesoEnum resultadoProceso) {
        logger.info("**__**inicio actualizarResultadoProcesoSolicitud");
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.actualizarResultadoProcesoSolicitud(idSolicitud, resultadoProceso);
    }

    private void actualizarEstadoSolicitudAfiliacionPersona(Long idSolicitud) {
        logger.info("**__**inicio actualizarEstadoSolicitudAfiliacionPersona");
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        n.actualizarEstadoSolicitudAfiliacionPersona(idSolicitud);
    }

    /**
     * Método encargado de registrar un intento de novedad.
     *
     * @param intentoNovedadDTO intento de novedad.
     * @return id de la solicitud de novedad.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long registrarIntentoFallido(IntentoNovedadDTO intentoNovedadDTO, UserDTO userDTO) {
        System.out.println("GLPI-45052-->registrarIntentoFallido(IntentoNovedadDTO intentoNovedadDTO, UserDTO userDTO)");
        ParametrizacionNovedadModeloDTO novedad = null;
        if (!TipoTransaccionEnum.NOVEDAD_REINTEGRO.equals(intentoNovedadDTO.getTipoTransaccion())) {
            novedad = consultarNovedad(intentoNovedadDTO.getTipoTransaccion());
        }

        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
        solNovedadDTO.setCanalRecepcion(intentoNovedadDTO.getCanalRecepcion());
        solNovedadDTO.setClasificacion(intentoNovedadDTO.getClasificacion());
        solNovedadDTO.setTipoTransaccion(intentoNovedadDTO.getTipoTransaccion());
        solNovedadDTO.setNovedadDTO(novedad);
        solNovedadDTO.setIdRegistroDetallado(intentoNovedadDTO.getIdRegistroDetallado());
        if (intentoNovedadDTO.getIdEmpleador() != null) {
            DatosEmpleadorNovedadDTO empleador = new DatosEmpleadorNovedadDTO();
            empleador.setIdEmpleador(intentoNovedadDTO.getIdEmpleador());
            solNovedadDTO.setDatosEmpleador(empleador);
        } else {
            DatosPersonaNovedadDTO persona = new DatosPersonaNovedadDTO();
            persona.setTipoIdentificacion(intentoNovedadDTO.getTipoIdentificacion());
            persona.setNumeroIdentificacion(intentoNovedadDTO.getNumeroIdentificacion());
            persona.setIdBeneficiario(intentoNovedadDTO.getIdBeneficiario());
            persona.setIdRolAfiliado(intentoNovedadDTO.getIdRolAfiliado());
            solNovedadDTO.setDatosPersona(persona);
        }
        logger.info("45051-registrarIntentoFallido**__**registrarIntentoFallido intentoNovedadDTO.getIdRolAfiliado(): " + intentoNovedadDTO.getIdRolAfiliado());
        /* se crea la solicitud de novedad y asigna el número de radicación */
        SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedad(solNovedadDTO, userDTO);
        intentoNovedadDTO.setIdSolicitud(solicitudNovedad.getIdSolicitud());

        /* se crea el intento de novedad */
        crearIntentoNovedad(intentoNovedadDTO);
        logger.info("**__**registrarIntentoFallido solicitudNovedad.getIdSolicitud(): " + solicitudNovedad.getIdSolicitud());
        /* se rechaza y cierra */
        actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.RECHAZADA);
        if (intentoNovedadDTO.getExcepcionTipoDos() == null) {
            intentoNovedadDTO.setExcepcionTipoDos(Boolean.FALSE);
        }
        if ((CanalRecepcionEnum.WEB.equals(intentoNovedadDTO.getCanalRecepcion()) && !intentoNovedadDTO.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB))
                || (intentoNovedadDTO.getExcepcionTipoDos() && intentoNovedadDTO.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB))) {
            /*se cierra la solicitud y se envia comunicado*/
            parametrizarNotificacion(true, solNovedadDTO, userDTO);
            cerrarSolicitudNovedad(solNovedadDTO, true);
        } else {
            // Se cierra la solicitud deespues de rechazarla
            actualizarEstadoSolicitudNovedad(solicitudNovedad.getIdSolicitud(), EstadoSolicitudNovedadEnum.CERRADA);
        }
        return solicitudNovedad.getIdSolicitud();
    }

    /**
     * Método que hace la peticion REST al servicio que actualiza el estado de
     * una novedad
     */
    private void actualizarEstadoSolicitudNovedad(Long idSolicitud, EstadoSolicitudNovedadEnum estadoSolicitud) {
        logger.info("**__**Inicia actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)idSolicitud: " + idSolicitud + " estadoSolicitud: " + estadoSolicitud);
 
        ActualizarEstadoSolicitudNovedadRutine a = new ActualizarEstadoSolicitudNovedadRutine();
        a.actualizarEstadoSolicitudNovedad(idSolicitud, estadoSolicitud, entityManager);

        logger.info("**__**Finaliza actualizarEstadoSolicitudNovedad(SolicitudNovedad solicitudNovedad)");
    }

    /**
     * Método encargado de parametrizar una notificación para ser enviada
     * (Cuando el proceso es web)
     *
     * @param intento define si se trata de un intento de novedad o no.
     * @param solicitudNovedadDTO dto con los datos necesarios para parametrizar
     * la notificación.
     * @param userDTO usuario autenticado.
     */
    private void parametrizarNotificacion(boolean intento, SolicitudNovedadDTO solicitudNovedadDTO, UserDTO userDTO) {
        /*se envía notificaciones desde servicios, en el caso que:
         * - Sea este radicando y el canal sea WEB
         * - Sea un intento fallido y el canal sea WEB
         */
        Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas = llenarEtiquetas();
        List<EtiquetaPlantillaComunicadoEnum> comunicados = new ArrayList<>();
        String proceso = solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().name();
        if (intento) {
            addListaComunicados(comunicados, etiquetas, proceso + INTENTO);
        } else {
            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB)) {
                addListaComunicados(comunicados, etiquetas, proceso + RADICAR + solicitudNovedadDTO.getNovedadDTO().getPuntoResolucion().name());
            } else {
                if (solicitudNovedadDTO.getCargaMultiple() == null || !solicitudNovedadDTO.getCargaMultiple()) {
                    addListaComunicados(comunicados, etiquetas, proceso + RADICAR);
                }
            }
            if (PuntoResolucionEnum.FRONT.equals(solicitudNovedadDTO.getNovedadDTO().getPuntoResolucion())
                    || solicitudNovedadDTO.getCargaMultiple() != null && solicitudNovedadDTO.getCargaMultiple()) {
                addListaComunicados(comunicados, etiquetas, proceso + CERRAR);
            }
        }
        if (comunicados != null && !comunicados.isEmpty()) {
            List<String> destinatarios = new ArrayList<>();
            if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().equals(ProcesoEnum.NOVEDADES_PERSONAS_WEB)
                    || solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().equals(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB)) {
                /*si se trata de una novedad de personas*/
                destinatarios.add(consultarEmailPersona(solicitudNovedadDTO.getDatosPersona().getTipoIdentificacion(),
                        solicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion()));

            } else if (solicitudNovedadDTO.getNovedadDTO().getNovedad().getProceso().equals(ProcesoEnum.NOVEDADES_EMPRESAS_WEB)) {
                /*si se trata de una novedad de empresas*/
                List<Long> idsEmpleador = new ArrayList<>();
                idsEmpleador.add(solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador());
                destinatarios = consultarEmailEmpleadores(idsEmpleador);
            }
            /*se recorren los comunicados*/
            for (EtiquetaPlantillaComunicadoEnum etiqueta : comunicados) {
                NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
                notificacion.setEtiquetaPlantillaComunicado(etiqueta);
                notificacion.setProcesoEvento(proceso);
                notificacion.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
                notificacion.setIdEmpleador(solicitudNovedadDTO.getDatosEmpleador() != null ? solicitudNovedadDTO.getDatosEmpleador().getIdEmpleador() : null);
                // notificacion.setDestinatarioTO(destinatarios);
                notificacion.setTipoTx(solicitudNovedadDTO.getNovedadDTO().getNovedad());
                for (String email : destinatarios) {
                    if (email != null) {
                        enviarComunicadoConstruido(notificacion);
                    }
                }

            }
        }
    }

    /**
     * Método encargado de enviar un comunicado construido
     *
     * @param notificacion, notificacion a enviar
     */
    private void enviarComunicadoConstruido(NotificacionParametrizadaDTO notificacion) {
        try {
            enviarCorreoParametrizado(notificacion);
        } catch (Exception e) {
            // este es el caso en que el envío del correo del comunicado no debe
            // abortar el proceso de afiliación
            // TODO Mostrar solo el log o persistir el error la bd ?
            logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
        }
    }

    /**
     * Método encargado de llamar el cliente del servicio envio de correo
     * parametrizado
     *
     * @param notificacion, notificación dto que contiene la información del
     * correo
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        logger.info("Inicia enviarCorreoParametrizado(NotificacionParametrizadaDTO) !!!!");
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
        logger.info("Finaliza enviarCorreoParametrizado(NotificacionParametrizadaDTO)");
    }

    /**
     * Me´todo que se encarga de llamar al cliente que consulta los email de los
     * empleadores.
     *
     * @param idEmpleadoresPersona
     * @return lista de los email de los empleadores.
     */
    private List<String> consultarEmailEmpleadores(List<Long> idEmpleadoresPersona) {
        ConsultarEmailEmpleadores consultarEmailEmpleadores = new ConsultarEmailEmpleadores(idEmpleadoresPersona);
        consultarEmailEmpleadores.execute();
        List<String> emailEmpleadores = consultarEmailEmpleadores.getResult();
        return emailEmpleadores;
    }

    /**
     * Metodo que se encarga de llamar al cliente que consulta los email de los
     * empleadores.
     *
     * @param idEmpleadoresPersona
     * @return lista de los email de los empleadores.
     */
    private String consultarEmailPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.info("consultarEmailPersona: tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarDatosPersona.execute();
        PersonaModeloDTO persona = consultarDatosPersona.getResult();
//        logger.info("persona: " + persona.getUbicacionModeloDTO());
//        logger.info("getIdPersona: " + persona.getIdPersona());
        String email = null;
        if (persona.getUbicacionModeloDTO() != null) {
            email = persona.getUbicacionModeloDTO().getEmail();
        }
//        logger.info("email: " + email);
        return email;
    }

    /**
     * Agrega la lista de etiquetas a la lista de comunicados para que se
     * realice el envio, validando que se haya generado para el key una lista
     *
     * @param comunicados Lista de comunicados a enviar
     * @param etiquetas Mapa de etiquetas por llave
     * @param key Llave para la lista de comunicados a agregar
     */
    private void addListaComunicados(List<EtiquetaPlantillaComunicadoEnum> comunicados,
            Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas, String key) {
        if (etiquetas.get(key) != null && !etiquetas.get(key).isEmpty()) {
            comunicados.addAll(etiquetas.get(key));
        }
    }

    /**
     * Método encargado de llenar los datos de validación segun la novedad de
     * persona.
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersona(SolicitudNovedadDTO solNovedadDTO) {
        logger.info("Inicio de método llenarDatosValidacionPersona(DatosPersonaNovedadDTO datosPersonaDTO)");

        Map<String, String> datosValidacion = new HashMap<String, String>();
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        // se obtienen las clasificaciones para afiliado y beneficiario
        List<ClasificacionEnum> afiliado = PersonasUtils.ListarClasificacionAfiliado();
        List<ClasificacionEnum> beneficiario = PersonasUtils.ListarClasificacionBeneficiario();

        // se verifica si la novedad entrante está asociada al afiliado
        // principal, beneficiario o empleador
        // y se procede a invocar el metodo correspondiente para cada caso.
//        logger.info("llenarDatosValidacionPersona afiliado: " + afiliado);
//        logger.info("llenarDatosValidacionPersona beneficiario: " + beneficiario);
//        logger.info("llenarDatosValidacionPersona solNovedadDTO.getClasificacion(): " + solNovedadDTO.getClasificacion());

        if (afiliado.contains(solNovedadDTO.getClasificacion())) {
//            logger.info("llenarDatosValidacionPersona ln1185");
            datosValidacion = llenarDatosValidacionPersonaAfiliado(solNovedadDTO);
        } else if (beneficiario.contains(solNovedadDTO.getClasificacion())) {
//            logger.info("llenarDatosValidacionPersona ln1188");
            datosValidacion = llenarDatosValidacionPersonaBeneficiario(solNovedadDTO);
        }
        // si el campo que contiene el id del grupo familiar no viene vacío, se
        // agrega a los datos de validación
        if (solNovedadDTO.getDatosPersona().getIdGrupoFamiliar() != null
                && !solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().equals("")) {
            datosValidacion.put("idGrupoFamiliar", solNovedadDTO.getDatosPersona().getIdGrupoFamiliar().toString());
        } else if (solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario() != null
                && solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar() != null
                && !solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().equals("")) {
            //Si el idGrupoFamiliar() es vacio consultar el grupo familiar del beneficiario
            datosValidacion.put("idGrupoFamiliar", solNovedadDTO.getDatosPersona().getGrupoFamiliarBeneficiario().getIdGrupoFamiliar().toString());
        }
        // Si el campo idRolAfiliado se envio, se agrega para la ejecucion de validaciones
        if (solNovedadDTO.getDatosPersona().getIdRolAfiliado() != null) {
            datosValidacion.put("idRolAfiliado", solNovedadDTO.getDatosPersona().getIdRolAfiliado().toString());
        }
        // Se agregan las fechas de inicio y fin que se envian
        String fechaInicio = null;
        if (solNovedadDTO.getDatosPersona().getFechaInicioNovedad() != null) {
            fechaInicio = solNovedadDTO.getDatosPersona().getFechaInicioNovedad().toString();
        }
        String fechaFin = null;
        if (solNovedadDTO.getDatosPersona().getFechaFinNovedad() != null) {
            fechaFin = solNovedadDTO.getDatosPersona().getFechaFinNovedad().toString();
        }
        String fechaRetiro = null;
        if (solNovedadDTO.getDatosPersona().getFechaRetiro() != null) {
            fechaRetiro = solNovedadDTO.getDatosPersona().getFechaRetiro().toString();
        }
        //Se agrega el campo condicionInvalidez para su validación (Caso beneficiario tipo Padre)
        if (solNovedadDTO.getDatosPersona().getCondicionInvalidezPadre() != null) {
            datosValidacion.put("condicionInvalidez", solNovedadDTO.getDatosPersona().getCondicionInvalidezPadre().toString());
        }
        datosValidacion.put("fechaInicio", fechaInicio);
        datosValidacion.put("fechaFin", fechaFin);
        datosValidacion.put("fechaRetiro", fechaRetiro);
        // Se agrega el tipo transaccion como dato para el proceso de validacion
        datosValidacion.put("tipoTransaccion", solNovedadDTO.getTipoTransaccion().name());
        logger.info("Fin de método llenarDatosValidacionPersona(DatosEmpleadorNovedadDTO datosEmpleadorDTO): " + datosValidacion);
        return datosValidacion;
    }

    /**
     * Método que crea un intento de novedad
     *
     * @param solNovedadDTO Solicitud para la creación del intento.
     */
    // --CLIENTE CrearIntentoNovedad //
    private Long crearIntentoNovedad(IntentoNovedadDTO intentoNovedadDTO) {
        CrearIntentoNovedadRutine c = new CrearIntentoNovedadRutine();
        return c.crearIntentoNovedad(intentoNovedadDTO, entityManager);
    }

    /**
     * Método que retorna las etiquetas por proceso y flujo
     *
     * @return map con las etiquetas de los comunicados.
     */
    private Map<String, List<EtiquetaPlantillaComunicadoEnum>> llenarEtiquetas() {
        Map<String, List<EtiquetaPlantillaComunicadoEnum>> etiquetas = new HashMap<String, List<EtiquetaPlantillaComunicadoEnum>>();
        /* Comunicados dependientes web */
        List<EtiquetaPlantillaComunicadoEnum> dependienteRadicar = new ArrayList<>();
        dependienteRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_WEB_TRB_EMP);
        // TODO falta la etiqueta 62
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + RADICAR, dependienteRadicar);
        List<EtiquetaPlantillaComunicadoEnum> dependienteIntento = new ArrayList<>();
        // TODO se dejo invocando la 61 pero se debe cambiar por la 62 cuando
        // exista
        dependienteIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_WEB_TRB_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + INTENTO, dependienteIntento);

        List<EtiquetaPlantillaComunicadoEnum> dependienteCerrar = new ArrayList<>();
        // Se envia al cerrar la solicitud dep web por cargue multiple
        // Etiqueta 59.
        dependienteCerrar.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_WEB_TRB_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_DEPENDIENTE_WEB.name() + CERRAR, dependienteCerrar);

        /* Comunicados personas web */
        List<EtiquetaPlantillaComunicadoEnum> personasRadicar = new ArrayList<>();
        personasRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_PER);
        personasRadicar.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS);
        etiquetas.put(ProcesoEnum.NOVEDADES_PERSONAS_WEB.name() + RADICAR, personasRadicar);
        List<EtiquetaPlantillaComunicadoEnum> personasIntento = new ArrayList<>();
        personasIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_PERS);
        etiquetas.put(ProcesoEnum.NOVEDADES_PERSONAS_WEB.name() + INTENTO, personasIntento);

        /* Comunicados empleadores web */
        List<EtiquetaPlantillaComunicadoEnum> empleadoresRadicarFront = new ArrayList<>();
        empleadoresRadicarFront.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_EMP);
        empleadoresRadicarFront.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + RADICAR + PuntoResolucionEnum.FRONT.name(),
                empleadoresRadicarFront);
        List<EtiquetaPlantillaComunicadoEnum> empleadoresRadicaBack = new ArrayList<>();
        empleadoresRadicaBack.add(EtiquetaPlantillaComunicadoEnum.NTF_RAD_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + RADICAR + PuntoResolucionEnum.BACK.name(),
                empleadoresRadicaBack);
        List<EtiquetaPlantillaComunicadoEnum> empleadoresIntento = new ArrayList<>();
        empleadoresIntento.add(EtiquetaPlantillaComunicadoEnum.NTF_NVD_EMP);
        etiquetas.put(ProcesoEnum.NOVEDADES_EMPRESAS_WEB.name() + INTENTO, empleadoresIntento);

        return etiquetas;
    }

    /**
     * Método encargado de llenar los datos de validacion cunado la persona está
     * clasificada como afiliado
     *
     * @param solNovedadDTO datos de la solicitud de novedad
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersonaAfiliado(SolicitudNovedadDTO solNovedadDTO) {
        // se invoca el método común que obtiene los campos de identificación
        Map<String, String> datosValidacion = llenarCamposDeIdentificacion(solNovedadDTO);

        if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL)) {
            datosValidacion.put("mismaDireccionAfiliadoPrincipalGrupoFam",
                    solNovedadDTO.getDatosPersona().getMismaDireccionAfiliadoPrincipalGrupoFam().toString());
        }

        datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosPersona().getPrimerNombreTrabajador());
        datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosPersona().getSegundoNombreTrabajador());
        datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosPersona().getPrimerApellidoTrabajador());
        datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosPersona().getSegundoApellidoTrabajador());
        if (solNovedadDTO.getDatosPersona().getFechaNacimientoTrabajador() != null) {
            datosValidacion.put(FECHA_NACIMIENTO,
                    solNovedadDTO.getDatosPersona().getFechaNacimientoTrabajador().toString());
        }
        if (solNovedadDTO.getDatosPersona().getInactivarCuentaWeb() != null) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString());
        }
        return datosValidacion;
    }

    /**
     * Método encargado de llenar los datos de validacion cunado la persona está
     * clasificada como beneficiario
     *
     * @param solNovedadDTO datos de la solicitud de novedad
     * @return map con los valores a validar.
     */
    private Map<String, String> llenarDatosValidacionPersonaBeneficiario(SolicitudNovedadDTO solNovedadDTO) {
        // se invoca el método común que obtiene los campos de identificación
        Map<String, String> datosValidacion = llenarCamposDeIdentificacion(solNovedadDTO);

        if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS)) {
            datosValidacion.put("mismaDireccionAfiliadoPrincipalGrupoFam",
                    solNovedadDTO.getDatosPersona().getMismaDireccionAfiliadoPrincipalGrupoFam().toString());
        } else if (solNovedadDTO.getTipoTransaccion()
                .equals(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS)) {
            datosValidacion.put("condicionInvalidez", solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo() != null
                    ? solNovedadDTO.getDatosPersona().getCondicionInvalidezHijo().toString() : null);
        }

        datosValidacion.put(PRIMER_NOMBRE, solNovedadDTO.getDatosPersona().getPrimerNombreBeneficiario());
        datosValidacion.put(SEGUNDO_NOMBRE, solNovedadDTO.getDatosPersona().getSegundoNombreBeneficiario());
        datosValidacion.put(PRIMER_APELLIDO, solNovedadDTO.getDatosPersona().getPrimerApellidoBeneficiario());
        datosValidacion.put(SEGUNDO_APELLIDO, solNovedadDTO.getDatosPersona().getSegundoApellidoBeneficiario());
        if (solNovedadDTO.getDatosPersona().getFechaNacimientoBeneficiario() != null) {
            datosValidacion.put(FECHA_NACIMIENTO,
                    solNovedadDTO.getDatosPersona().getFechaNacimientoBeneficiario().toString());
        }
        if (solNovedadDTO.getDatosPersona().getInactivarCuentaWeb() != null
                && !solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString().equals("")) {
            datosValidacion.put(REQUIERE_INACTIVACION_WEB,
                    solNovedadDTO.getDatosPersona().getInactivarCuentaWeb().toString());
        }

        return datosValidacion;
    }

    /**
     * Método que identifica los atributos de identificacion y los asigna en las
     * variables correspondientes
     *
     * @param solNovedadDTO datos de la solicitud de novedad.
     * @return map con los valores asignados de identificacion
     */
    private Map<String, String> llenarCamposDeIdentificacion(SolicitudNovedadDTO solNovedadDTO) {
        Map<String, String> datosValidacion = new HashMap<String, String>();

        // se verifica que los datos de identificación entrantes, asociados al
        // beneficiario,
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiario() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario().equals("")) {
            datosValidacion.put("tipoIdentificacionBeneficiario",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiario().name());
            datosValidacion.put("numeroIdentificacionBeneficiario",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiario());
        }

        // Se verifica si los datos de identificacion del beneficiario cambiaron
        // para tenerlos en cuenta en las validaciones
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiarioAnterior() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior().equals("")) {
            datosValidacion.put("tipoIdentificacionBeneficiarioAnterior",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionBeneficiarioAnterior().name());
            datosValidacion.put("numeroIdentificacionBeneficiarioAnterior",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionBeneficiarioAnterior());
        }
        // se verifica que los datos de identificación entrantes, asociados al
        // afiliado no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacion() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacion() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacion().equals("")) {
            datosValidacion.put(TIPO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getTipoIdentificacion().name());
            datosValidacion.put(NUMERO_IDENTIFICACION,
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacion());
        }

        // se verifica que los datos de identificación entrantes, asociados al
        // afiliado (trabajador),
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionTrabajador() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador().equals("")) {
            datosValidacion.put("tipoIdentificacionTrabajador",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionTrabajador().name());
            datosValidacion.put("numeroIdentificacionTrabajador",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionTrabajador());
        }

        // se verifica que los datos de identificación entrantes, asociados al
        // empleador,
        // no sean nulos ni vacios
        if (solNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador() != null
                && solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador() != null
                && !solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador().equals("")) {
            datosValidacion.put("tipoIdentificacionEmpleador",
                    solNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador().name());
            datosValidacion.put("numeroIdentificacionEmpleador",
                    solNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador());
        }

        // retorna un Map con los valores encontrados
        return datosValidacion;
    }

    /**
     * Metodo que se encarga de llamar al cliente que consulta los beneficiarios
     * de los empleadores.
     *
     * @param tipoIdentificacionTrabajador
     * @param numeroIdentificacionTrabajador
     * @param tipoIdentificacionBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @return Beneficiario que coincide con los filtros
     */
    private PersonaComoAfiPpalGrupoFamiliarDTO consultarBeneficiariosTrabajador(TipoIdentificacionEnum tipoIdentificacionTrabajador, String numeroIdentificacionTrabajador,
            TipoIdentificacionEnum tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {

        BeneficiarioGrupoFamiliarDTO beneficiarioGrupoFamiliarDTO = null;
        PersonaComoAfiPpalGrupoFamiliarDTO personaBeneficiarioRetiro = new PersonaComoAfiPpalGrupoFamiliarDTO();

        ObtenerGruposFamiliaresAfiPrincipal obtenerGruposFamiliaresAfiPrincipal
                = new ObtenerGruposFamiliaresAfiPrincipal(tipoIdentificacionTrabajador.toString(), numeroIdentificacionTrabajador);

        obtenerGruposFamiliaresAfiPrincipal.execute();
        List<PersonaComoAfiPpalGrupoFamiliarDTO> personasAfiliadas = obtenerGruposFamiliaresAfiPrincipal.getResult();

        if (CollectionUtils.isNotEmpty(personasAfiliadas)) {
            ITEGRUPO_FAMILIAR : for (PersonaComoAfiPpalGrupoFamiliarDTO iteGrupoFamiliar : personasAfiliadas) {
                personaBeneficiarioRetiro = iteGrupoFamiliar;

                if (CollectionUtils.isNotEmpty(iteGrupoFamiliar.getBeneficiarios())) {

                    beneficiarioGrupoFamiliarDTO = iteGrupoFamiliar.getBeneficiarios().stream().filter(
                            iteBeneficiario -> iteBeneficiario.getTipoIdentificacion().equals(tipoIdentificacionBeneficiario)
                            && iteBeneficiario.getNumeroIdentificacion().equals(numeroIdentificacionBeneficiario)
                    ).findFirst().orElse(null);
                    
                    if(beneficiarioGrupoFamiliarDTO != null){
                        break ITEGRUPO_FAMILIAR;
                    }
                }
            }
        }
        if (personaBeneficiarioRetiro != null) {
            personaBeneficiarioRetiro.setBeneficiarios(new ArrayList<>());
            personaBeneficiarioRetiro.getBeneficiarios().add(beneficiarioGrupoFamiliarDTO);
        }

        return personaBeneficiarioRetiro;
    }

    /**
     * Entrega el tipo de transaccion para inactivacion del beneficario
     *
     * @param clasificacion
     * @return
     */
    private TipoTransaccionEnum obtenerTipoTransaccionBeneficiario(ClasificacionEnum clasificacion) {
        TipoTransaccionEnum tipoTransaccionResult = null;
        switch (clasificacion) {
            case CONYUGE:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
                break;
            case PADRE:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL;
                break;
            case MADRE:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL;
                break;
            case HIJO_BIOLOGICO:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
                break;
            case HIJASTRO:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
                break;
            case HERMANO_HUERFANO_DE_PADRES:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL;
                break;
            case HIJO_ADOPTIVO:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL;
                break;
            case BENEFICIARIO_EN_CUSTODIA:
                tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL;
                break;
            default:
                break;
        }
        return tipoTransaccionResult;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoReintegroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoReintegroTrabajadores(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {
        logger.info("Inicio validarArchivoReintegroTrabajadores(" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_REINTEGRO_TRABAJADORES);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoReintegroTrabajadores verificarArchivo = new VerificarEstructuraArchivoReintegroTrabajadores(tipoArchivo, archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

        //Si hay registros validos procesamos esos registros validos.
        if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
//            logger.info("Se llama el metodo validarArchivoReintegroTrabajadores ln 1594");

            List<SolicitudNovedadDTO> list = procesarNovedadReintegroTrabajadores(tipoArchivo,
                    resultDTO.getListActualizacionInfoNovedad(), idCargue, userDTO);
//
            for (SolicitudNovedadDTO solNovedadDTO : list) {
//                logger.info("Notificar solNovedadDTO.getResultadoValidacion()" + solNovedadDTO.getResultadoValidacion());
                      if(solNovedadDTO.getResultadoValidacion() != null){
                            if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
            //                    logger.info("Notificar solNovedadDTO.getIdSolicitud()" + solNovedadDTO.getIdSolicitud());
                                persistirItemChequeo(solNovedadDTO);
                                enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);
                            }
                        }
            }
            //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                //Solo hay registros validos, entonces debe finalizar sin errores
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }
        } else {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        }

        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);

        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_REINTEGRO_TRABAJADORES);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

        logger.info("Fin validarArchivoReintegroTrabajadores (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
//        return resultDTO;

    }

    /**
     * Procesa la novedad de reintegro afiliado trabajador
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     */
    private List<SolicitudNovedadDTO> procesarNovedadReintegroTrabajadores(TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
//    private void procesarNovedadReintegroTrabajadores(TipoArchivoRespuestaEnum tipoArchivo,
//            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadReintegroTrabajadores(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                informacionActualizacionNovedadDTO.setIdCargue(idCargue);
                ProcesarArchivoReintegroTrabajadorIndividual procesarArchivoSrv = new ProcesarArchivoReintegroTrabajadorIndividual(informacionActualizacionNovedadDTO);
                procesarArchivoSrv.execute();
                return procesarArchivoSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }

        List<SolicitudNovedadDTO> listResultadoProcesamiento = new ArrayList<>();
        try {
            List<Future<SolicitudNovedadDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<SolicitudNovedadDTO> future : listInfoArchivoFuture) {
                listResultadoProcesamiento.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona identificarNovedadArchivoRespuesta(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)", e);
            throw new TechnicalException(e);
        }

        logger.debug("Finaliza procesarNovedadReintegroTrabajadores(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        return listResultadoProcesamiento;

    }

    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de reintegro
     * trabajador
     *
     * @param informacionActualizacionNovedadDTO
     * @return
     */
    @Override
    public SolicitudNovedadDTO procesarArchivoReintegroTrabajadorIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO, UserDTO userDTO) {

        logger.info("Inicia procesarArchivoReintegroTrabajadorIndividual");
        logger.info("Sucursal juan "+informacionActualizacionNovedadDTO.getAfiliado().getSucursalAfiliado());

        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
//        UserDTO userDTO = new UserDTO();

        //Set datos
        solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solNovedadDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
        solNovedadDTO.setObservaciones("Registro proveniente de actualización masiva - reintegro trabajador");
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        solNovedadDTO.setCargaMultiple(true);
        solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());

        EmpleadorModeloDTO empleadorModeloDTO = informacionActualizacionNovedadDTO.getAfiliado().getEmpleador();
        
        DatosEmpleadorNovedadDTO datosEmpleador = new DatosEmpleadorNovedadDTO();
        datosEmpleador.setTipoIdentificacion(empleadorModeloDTO.getTipoIdentificacion());
        datosEmpleador.setNumeroIdentificacion(empleadorModeloDTO.getNumeroIdentificacion());
        datosEmpleador.setFechaFinLaboresSucursalOrigenTraslado(Calendar.getInstance().getTimeInMillis());
        
        SucursalEmpresaModeloDTO sucursalDestinoDTO = consultarSucursalPorNombre(datosEmpleador.getTipoIdentificacion(),
                datosEmpleador.getNumeroIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getSucursalAfiliado());



        DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        if (sucursalDestinoDTO != null) {
            logger.info("if sucursal");
            SucursalEmpresa sucursal = new SucursalEmpresa();
            sucursal = sucursalDestinoDTO.convertToEntity();
            datosPersona.setSucursalEmpleadorTrabajador(sucursal);
        }
        datosPersona.setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setFechaInicioLaboresConEmpleador(informacionActualizacionNovedadDTO.getAfiliado().getFechaInicioAfiliacion());
        datosPersona.setValorSalarioMensualTrabajador(new BigDecimal(informacionActualizacionNovedadDTO.getAfiliado().getSalarioAfiliado()));
        datosPersona.setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
//        datosPersona.setTipoSolicitanteTrabajador(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
        datosPersona.setTipoIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacionTrabajador(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
//        datosPersona.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        datosPersona.setTipoIdentificacionEmpleador(informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getTipoIdentificacion());
        datosPersona.setNumeroIdentificacionEmpleador(informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getNumeroIdentificacion());

        solNovedadDTO.setDatosPersona(datosPersona);

        List<EmpleadorRelacionadoAfiliadoDTO> empleadorRelacionadoAfiliadoDTO = consultarEmpleadoresRelacionadosAfiliado(
                informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getEmpleador().getNumeroIdentificacion(),
                datosPersona.getTipoIdentificacion(),
                datosPersona.getNumeroIdentificacion());

        if (CollectionUtils.isNotEmpty(empleadorRelacionadoAfiliadoDTO)) {
//            logger.info("empleadorRelacionadoAfiliadoDTO: "+empleadorRelacionadoAfiliadoDTO.size());
            Long idRolAFiliado = empleadorRelacionadoAfiliadoDTO.get(0).getIdRolAfiliado();
            solNovedadDTO.getDatosPersona().setIdRolAfiliado(idRolAFiliado);
        }
        solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivasReintegro(solNovedadDTO, userDTO);

        return solNovedadDTO;
    }

    /*
     * (non-Javadoc)
     * Este metodo no hace nuevas validaciones porque estas se hicieron completas en el cargue del archivo.
     * Si ingresa aquí, es porque cumplió todas las reglas de validación.
     * @see com.asopagos.novedades.composite.service.radicarSolicitudNovedadActualizacionesMasivas#
     * GLPI 55721   
     * radicarSolicitudNovedad(com.asopagos.novedades.dto.SolicitudNovedadDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    private SolicitudNovedadDTO radicarSolicitudNovedadActualizacionesMasivasReintegro(SolicitudNovedadDTO solNovedadDTO, UserDTO userDTO) {
        logger.info("***********Inicia radicarSolicitudNovedadActualizacionesMasivasReintegro**********");
        try {
            /* se busca la novedad seleccionada y se setea en el dto */
//            logger.info("*****solNovedadDTO.getTipoTransaccion(): " + solNovedadDTO.getTipoTransaccion());

            ParametrizacionNovedadModeloDTO novedad = consultarNovedad(solNovedadDTO.getTipoTransaccion());
            solNovedadDTO.setNovedadDTO(novedad);
            /*se crea la solicitud de novedad y asigna el nùmero de radicaciòn*/
            solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());

            /*se crea la solicitud de novedad y asigna el nùmero de radicaciòn*/
            solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
//            logger.info("solNovedadDTO****--- Antes de crear la solicitud" + solNovedadDTO);
            SolicitudNovedadModeloDTO solicitudNovedad = crearSolicitudNovedadReintegro(solNovedadDTO, userDTO);
            
            solNovedadDTO.setIdSolicitud(solicitudNovedad.getIdSolicitud());
            solNovedadDTO.setIdSolicitudNovedad(solicitudNovedad.getIdSolicitudNovedad());
            if(solNovedadDTO.getDatosEmpleador() != null){
                solNovedadDTO.getDatosEmpleador().setListaChequeoNovedad(new ArrayList<>());
                solNovedadDTO.getDatosEmpleador().setSucursalesOrigenSustPatronal(new ArrayList<>());
                solNovedadDTO.getDatosEmpleador().setSociosEmpleador(new ArrayList<>());
            }
            if(solNovedadDTO.getDatosPersona() != null){
                solNovedadDTO.getDatosPersona().setListaChequeoNovedad(new ArrayList<>());
            }
            guardarDatosTemporal(solNovedadDTO);
            
            // Se resuelve la novedad por medio de los convertidores
            resolverNovedadReintegro(solNovedadDTO, solicitudNovedad, userDTO);
            // Se envian comunicados masivamente
//            enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);
            // Se cierra la solicitud
            cerrarSolicitudNovedad(solNovedadDTO, true);
            actualizarResultadoProcesoSolicitud(solNovedadDTO.getIdSolicitud(), ResultadoProcesoEnum.APROBADA);
            actualizarEstadoSolicitudAfiliacionPersona(solNovedadDTO.getIdSolicitud());

//            userTransaction.commit();
            logger.info("Fin de método radicarSolicitudNovedadActualizacionesMasivasReintegro((SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)) ");
            return solNovedadDTO;
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof AsopagosException) {
                throw (AsopagosException) e;
            }
            logger.error(
                    "Ocurrio un error inesperado en radicarSolicitudNovedad(SolicitudNovedadDTO solNovedadDTO,UserDTO userDTO)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que hace la peticion REST al servicio que crea una solicitud de
     * novedad.
     *
     * @param novedadDTO novedad a crearse.
     * @param userDTO usuario que radica la solicitud.
     */
    private SolicitudNovedadModeloDTO crearSolicitudNovedadReintegro(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
        logger.info("**__** crearSolicitudNovedadReintegro::  " + novedadDTO);
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.crearSolicitudNovedadReintegro(novedadDTO, userDTO, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoRetiroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoActualizarSucursal(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {
        logger.info("Inicio validarArchivoActualizarSucursal (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_ACTUALIZACION_SUCURSAL);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoActualizacionSucursal verificarArchivo = new VerificarEstructuraArchivoActualizacionSucursal(tipoArchivo, archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

        //Si hay registros validos procesamos esos registros validos.
        if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
            List<SolicitudNovedadDTO> list = procesarNovedadActualizacionSucursal(tipoArchivo,
                    resultDTO.getListActualizacionInfoNovedad(), idCargue, userDTO);

            for (SolicitudNovedadDTO solNovedadDTO : list) {
//                logger.info("Notificar solNovedadDTO.getResultadoValidacion()" + solNovedadDTO.getResultadoValidacion());
                if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
//                    logger.info("Notificar solNovedadDTO.getIdSolicitud()" + solNovedadDTO.getIdSolicitud());
                    enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);
                }
            }

            //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                //Solo hay registros validos, entonces debe finalizar sin errores
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }
        } else {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        }

        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);

        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_ACTUALIZACION_SUCURSAL);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

        logger.info("Fin validarArchivoActualizarSucursal(" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
    }

    /**
     * Procesa la novedad de actualizacion sucursal
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     */
    private List<SolicitudNovedadDTO> procesarNovedadActualizacionSucursal(TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadActualizacionSucursal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                informacionActualizacionNovedadDTO.setIdCargue(idCargue);
                ProcesarArchivoActualizacionSucursal procesarArchivoSrv = new ProcesarArchivoActualizacionSucursal(informacionActualizacionNovedadDTO);
                procesarArchivoSrv.execute();
                return procesarArchivoSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }

        List<SolicitudNovedadDTO> listResultadoProcesamiento = new ArrayList<>();
        try {
            List<Future<SolicitudNovedadDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<SolicitudNovedadDTO> future : listInfoArchivoFuture) {
                listResultadoProcesamiento.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona procesarNovedadActualizacionSucursal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)", e);
            throw new TechnicalException(e);
        }

        logger.debug("Finaliza procesarNovedadActualizacionSucursal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        return listResultadoProcesamiento;
    }

    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de
     * actualizar sucursal
     *
     * @param informacionActualizacionNovedadDTO
     * @return
     */
    @Override
    public SolicitudNovedadDTO procesarArchivoActualizacionSucursal(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO) {

        logger.info("Inicia procesarArchivoActualizacionSucursal");
        
//        printJsonMessage(informacionActualizacionNovedadDTO, "procesarArchivoActualizacionSucursal: informacionActualizacionNovedadDTO - ");
        
        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();

        //Set datos
        solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solNovedadDTO.setClasificacion(ClasificacionEnum.PERSONA_JURIDICA);
        solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES);
        solNovedadDTO.setObservaciones("Registro proveniente de actualización masiva - Actualizacion Sucursal");
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        solNovedadDTO.setCargaMultiple(true);
        solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());

        EmpleadorModeloDTO empleadorModeloDTO = informacionActualizacionNovedadDTO.getAfiliado().getEmpleador();
        
        DatosEmpleadorNovedadDTO datosEmpleador = new DatosEmpleadorNovedadDTO();
        datosEmpleador.setTipoIdentificacion(empleadorModeloDTO.getTipoIdentificacion());
        datosEmpleador.setNumeroIdentificacion(empleadorModeloDTO.getNumeroIdentificacion());
        datosEmpleador.setFechaFinLaboresSucursalOrigenTraslado(Calendar.getInstance().getTimeInMillis());

//        printJsonMessage(informacionActualizacionNovedadDTO, "Epr informacionActualizacionNovedadDTO");
        
        SucursalEmpresaModeloDTO sucursalDestinoDTO = consultarSucursalPorNombre(datosEmpleador.getTipoIdentificacion(),
                datosEmpleador.getNumeroIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getSucursalAfiliado());
        if (sucursalDestinoDTO != null) {
			datosEmpleador.setIdEmpleador(sucursalDestinoDTO.getIdEmpresa());
            datosEmpleador.setSucursalDestinoTraslado(sucursalDestinoDTO.getIdSucursalEmpresa());
        }

        InfoAfiliadoRespectoEmpleadorDTO afiliadoRespectoEmpleadorDTO = obtenerInfoAfiliadoRespectoEmpleador(
                datosEmpleador.getTipoIdentificacion(),
                datosEmpleador.getNumeroIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());

//        printJsonMessage(afiliadoRespectoEmpleadorDTO, "Epr afiliadoRespectoEmpleadorDTO");
        if (afiliadoRespectoEmpleadorDTO != null) {
            datosEmpleador.setTrabajadoresTraslado(
                    Arrays.asList(afiliadoRespectoEmpleadorDTO.getInfoEstadoAfiliado().getIdPersona()));

            SucursalEmpresaModeloDTO sucursalOrigenDTO = consultarSucursalPorNombre(datosEmpleador.getTipoIdentificacion(),
                    datosEmpleador.getNumeroIdentificacion(),
                    afiliadoRespectoEmpleadorDTO.getInfoEstadoAfiliado().getSucursalAsociada());            
            if (sucursalOrigenDTO != null) {
                datosEmpleador.setSucursalOrigenTraslado(sucursalOrigenDTO.getIdSucursalEmpresa());
            }
//            printJsonMessage(sucursalOrigenDTO, "Epr sucursalOrigenDTO");
        }
//        printJsonMessage(datosEmpleador, "datosEmpleador mcuellar");
        
        List<EmpleadorRelacionadoAfiliadoDTO> empleadoresRelacionadosAfiliadoList = 
                obtenerEmpleadoresRelacionadosAfiliado(
                    datosEmpleador.getTipoIdentificacion(),
                    datosEmpleador.getNumeroIdentificacion(),
                    informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion(),
                    informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion()
                );
        
        if(CollectionUtils.isNotEmpty(empleadoresRelacionadosAfiliadoList)){
            EmpleadorRelacionadoAfiliadoDTO relacionadoAfiliadoDTO = empleadoresRelacionadosAfiliadoList.get(0);
            if(relacionadoAfiliadoDTO != null){
                datosEmpleador.setIdEmpleador(relacionadoAfiliadoDTO.getIdEmpleador());
            }
        }
		
		DatosPersonaNovedadDTO datosPersona = new DatosPersonaNovedadDTO();
        datosPersona.setNumeroIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
        datosPersona.setTipoIdentificacion(informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion());
        
        List<EmpleadorRelacionadoAfiliadoDTO> empleadorRelacionadoAfiliadoDTO = consultarEmpleadoresRelacionadosAfiliado(
                empleadorModeloDTO.getTipoIdentificacion(),
                empleadorModeloDTO.getNumeroIdentificacion(),
                datosPersona.getTipoIdentificacion(),
                datosPersona.getNumeroIdentificacion());
        
        if (CollectionUtils.isNotEmpty(empleadorRelacionadoAfiliadoDTO)) {
            Long idRolAFiliado = empleadorRelacionadoAfiliadoDTO.get(0).getIdRolAfiliado();
            datosPersona.setIdRolAfiliado(idRolAFiliado);
        }

        solNovedadDTO.setDatosEmpleador(datosEmpleador);
		solNovedadDTO.setDatosPersona(datosPersona);

//        printJsonMessage(solNovedadDTO, "procesarArchivoActualizacionSucursal");
        
        //Revisar este metodo !!!!!!!
        solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);

        return solNovedadDTO;
    }

    private SucursalEmpresaModeloDTO consultarSucursalPorCodigo(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            String codigoSucursal) {

        ObtenerSucursalEmpresaByCodigoAndEmpleador sucursalEmpresaByCodigoAndEmpleadorSrv
                = new ObtenerSucursalEmpresaByCodigoAndEmpleador(tipoIdEmpleador, codigoSucursal, numeroIdEmpleador);
        sucursalEmpresaByCodigoAndEmpleadorSrv.execute();
        return sucursalEmpresaByCodigoAndEmpleadorSrv.getResult();
    }
    
    private SucursalEmpresaModeloDTO consultarSucursalPorNombre(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            String nombreSucursal) {

        ObtenerSucursalEmpresaByNombreAndEmpleador sucursalEmpresaByNombreAndEmpleadorSrv
                = new ObtenerSucursalEmpresaByNombreAndEmpleador(tipoIdEmpleador, nombreSucursal, numeroIdEmpleador);
        sucursalEmpresaByNombreAndEmpleadorSrv.execute();
        return sucursalEmpresaByNombreAndEmpleadorSrv.getResult();
    }

    private InfoAfiliadoRespectoEmpleadorDTO obtenerInfoAfiliadoRespectoEmpleador(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {

        ObtenerInfoAfiliadoRespectoEmpleador afiliadoRespectoEmpleadorSrv
                = new ObtenerInfoAfiliadoRespectoEmpleador(tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, numeroIdAfiliado);
        afiliadoRespectoEmpleadorSrv.execute();
        return afiliadoRespectoEmpleadorSrv.getResult();
    }
    
    private List<EmpleadorRelacionadoAfiliadoDTO> obtenerEmpleadoresRelacionadosAfiliado(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador,
            TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {

        ObtenerEmpleadoresRelacionadosAfiliado empleadoresRelacionadosAfiliadoSrv =
                new ObtenerEmpleadoresRelacionadosAfiliado(tipoIdEmpleador, tipoIdAfiliado, numeroIdEmpleador, null, numeroIdAfiliado);
        empleadoresRelacionadosAfiliadoSrv.execute();
        return empleadoresRelacionadosAfiliadoSrv.getResult();
        
    }
    
    private List<Empleador> obtenerInformacionEmpleador(
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {

        BuscarEmpleador buscarEmpleadorSrv = new BuscarEmpleador(true, numeroIdEmpleador, tipoIdEmpleador, "");
        buscarEmpleadorSrv.execute();
        return buscarEmpleadorSrv.getResult();
        
    }

    /**
     * Metodo encargado de invocar el servicio para el almacenamiento de los datos temporales asociandolos a la persona si corresponde con
     * una activacion de beneficiario
     *
     * @param solicitudNovedadDTO Informacion de la solicitud novedad
     * @throws JsonProcessingException Error presentado en la conversion
     */
    private void guardarDatosTemporal(SolicitudNovedadDTO solicitudNovedadDTO) throws JsonProcessingException {
        logger.info("guardarDatosTemporal(SolicitudNovedadDTO solicitudNovedadDTO)");
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        p.guardarDatosTemporal(solicitudNovedadDTO, entityManager);

    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoRetiroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void validarArchivoConfirmacionAbonosBancario(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {
        logger.info("Inicio validarArchivoConfirmacionAbonosBancario (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
            
        long tiempoInicial, tiempoFinal;
        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);
    
        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_CONFIRMACION_AB);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        //printJsonMessage(consolaEstadoCargue, "lina: consolaEstadoCargue: ");
        registrarConsolaEstado(consolaEstadoCargue);
    
        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoConfirmacionAB verificarArchivo = new VerificarEstructuraArchivoConfirmacionAB(tipoArchivo, archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
     
        List<InformacionActualizacionNovedadDTO> informacionActNovedadNoExitosos = null;
    
                
        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;
    
        //Si hay registros validos procesamos esos registros validos.
        if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
            //PART 2 DESARROLLO: metodo que llama al ws de [USP_PG_ConfirmarAbonosMedioPagoBancosArchivo]
            tiempoInicial = System.nanoTime();
            confirmarResultadosAbonosBancariosArchivo(resultDTO.getListActualizacionInfoNovedad(), userDTO);
            tiempoFinal = System.nanoTime();
            logger.info("Calculo tiempo (verificarEstructuraArchivoConfirmacionAB, metodo confirmarResultadosAbonosBancariosArchivo): " + String.valueOf(TimeUnit.MILLISECONDS.convert(tiempoFinal - tiempoInicial, TimeUnit.NANOSECONDS)));
    
    
            // Filtrando la lista
            informacionActNovedadNoExitosos = resultDTO.getListActualizacionInfoNovedad().stream()
            .filter(iteAdmin -> iteAdmin.getConfirmacionAbonoAdminSubsidio().getResultadoAbono().equals("ABONO NO EXITOSO"))
            .collect(Collectors.toList());
            logger.info("Inicio del filtro para 'ABONO NO EXITOSO'.");
            logger.info("Cantidad de elementos con ABONO NO EXITOSO: " + informacionActNovedadNoExitosos.size());
            informacionActNovedadNoExitosos.forEach(iteAdmin -> {
            logger.info("ID de Elemento: , Resultado de Abono: " + iteAdmin.getConfirmacionAbonoAdminSubsidio().getResultadoAbono());
            });

            // Log final
            logger.info("Filtro completado.");
          //  printJsonMessage(informacionActNovedadNoExitosos, "informacionActNovedadNoExitosos: ");
            tiempoInicial = System.nanoTime();
            procesarNovedadConfirmacionAbonosBancarios(tipoArchivo, informacionActNovedadNoExitosos, idCargue, userDTO);
            tiempoFinal = System.nanoTime();
            logger.info("Calculo tiempo (verificarEstructuraArchivoConfirmacionAB, metodo procesarNovedadConfirmacionAbonosBancarios): " + String.valueOf(TimeUnit.MILLISECONDS.convert(tiempoFinal - tiempoInicial, TimeUnit.NANOSECONDS)));
    
    
            //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                //Solo hay registros validos, entonces debe finalizar sin errores
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }
        } else {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        }
    
        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);
    
        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_CONFIRMACION_AB);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
    
        logger.info("Fin validarArchivoConfirmacionAbonosBancario (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
    }
    
    /**
     * Procesa la novedad de confirmacion abonos bancarios
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     * 
     * 
     */
    private void procesarNovedadConfirmacionAbonosBancarios(TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadConfirmacionAbonosBancarios(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        List<SolicitudNovedadDTO> listResultadoProcesamiento = new ArrayList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            informacionActualizacionNovedadDTO.setPrimerIntento(Boolean.TRUE);
            ProcesarArchivoConfirmacionAbonosBancario procesarArchivoSrv = new ProcesarArchivoConfirmacionAbonosBancario(informacionActualizacionNovedadDTO);
            procesarArchivoSrv.execute();
        }

        logger.debug("Finaliza procesarNovedadConfirmacionAbonosBancarios(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
    }
    
    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de
     * confirmacion abonos bancarios #procesarArchivoConfirmacionAbonosBancario
     *
     * @param informacionActualizacionNovedadDTO
     * @return
     */
    @Override
    @Asynchronous
    public void procesarArchivoConfirmacionAbonosBancario(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
                                                          UserDTO userDTO) {

        logger.info("Inicia procesarArchivoConfirmacionAbonosBancario");
        //printJsonMessage(informacionActualizacionNovedadDTO, "lina: parametros ws procesarArchivoConfirmacionAbonosBancario respuesta");

        Boolean aplicarNovedad = controlRegistroNovedad();

//        printJsonMessage(informacionActualizacionNovedadDTO, "procesarArchivoActualizacionSucursal: informacionActualizacionNovedadDTO - ");
        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();

        if (aplicarNovedad) {
            //CAMBIA EL METODO DE PAGO A EFECTIVO
            List<Long> resp = registrarCambioMedioDePagoSubsidio(informacionActualizacionNovedadDTO);
            logger.info("resp registrarCambioMedioDePagoSubsidio " + resp.size());

            if (resp.size() > 0) {

                //Set datos
                solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
                solNovedadDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
                solNovedadDTO.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
                solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE);
                solNovedadDTO.setObservaciones(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getMotivoRechazoAbono() == null ? "" : informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getMotivoRechazoAbono());
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                //solNovedadDTO.setCargaMultiple(true);
                //solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());

                DatosPersonaNovedadDTO datosPersonaNovedadDTO = new DatosPersonaNovedadDTO();
                MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();


                //TIPO IDENTIFICACION 55231 67367

                TipoIdentificacionEnum tipoIdentificacion;
                Object[] obtenerTitualarAdminSubsidioObjectResult = null;
                String tipoIdentificacionNombre = "";
                String numeroIdentificacionAdmin = "";
                Long idGrupoFamiliarObject = new Long(0);

                //  if (informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion() == null){
                ObtenerTitularCuentaAdministradorSubsidioByCasId obtenerTitualarAdminSubsidioObject = new ObtenerTitularCuentaAdministradorSubsidioByCasId((informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getCasId()));
                obtenerTitualarAdminSubsidioObject.execute();
                obtenerTitualarAdminSubsidioObjectResult = obtenerTitualarAdminSubsidioObject.getResult();

                tipoIdentificacion = TipoIdentificacionEnum.valueOf(obtenerTitualarAdminSubsidioObjectResult[0].toString());
                numeroIdentificacionAdmin = obtenerTitualarAdminSubsidioObjectResult[1].toString();
                idGrupoFamiliarObject = Long.valueOf(obtenerTitualarAdminSubsidioObjectResult[2].toString());
                
            /*    }else{
                    tipoIdentificacion = informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion();

                }*/

                logger.info("obtenerTitualarAdminSubsidioObjectResult " + obtenerTitualarAdminSubsidioObjectResult);


                //Consultar el afiliado principal
                ConsultarResumenGruposFamiliares consultarResumenGruposFamiliares = new ConsultarResumenGruposFamiliares(numeroIdentificacionAdmin, tipoIdentificacion);
                consultarResumenGruposFamiliares.execute();
                List<PersonaComoAdminSubsidioDTO> resultadoGrupoFamiliar = consultarResumenGruposFamiliares.getResult();

                logger.info("resultadoGrupoFamiliar " + resultadoGrupoFamiliar.size());

                System.out.println("Impresion de las personas del grupo familiar");

                //            CabeceraVista360Persona cabeceraVista360Persona = new CabeceraVista360Persona(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getNumeroIdentificacion(), informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion());
                //            cabeceraVista360Persona.execute();
                //            Vista360PersonaCabeceraDTO vista360PersonaCabeceraDTO = cabeceraVista360Persona.getResult();

                printJsonMessage(resultadoGrupoFamiliar, "55231 abonos bancarios - resultadoGrupoFamiliar: ");

                if (CollectionUtils.isNotEmpty(resultadoGrupoFamiliar)) {
                    System.out.println("Entro en el if 1");
                    System.out.println("lo esta llenando con idGrupoFamiliarObject: " + idGrupoFamiliarObject);
                    System.out.println(resultadoGrupoFamiliar.get(0).toString());
                    for (PersonaComoAdminSubsidioDTO persona : resultadoGrupoFamiliar) {
                        if (persona.getIdGrupoFamiliar().equals(idGrupoFamiliarObject)) {
                            datosPersonaNovedadDTO.setTipoIdentificacion(persona.getTipoIdAfiliadoPpal());
                            datosPersonaNovedadDTO.setNumeroIdentificacion(persona.getNumeroIdAfiliadoPpal());
                            datosPersonaNovedadDTO.setTipoIdentificacionTrabajador(persona.getTipoIdAfiliadoPpal());
                            datosPersonaNovedadDTO.setNumeroIdentificacionTrabajador(persona.getNumeroIdAfiliadoPpal());
                        }
                        System.out.println(persona.toString());
                    }

                } else {
                    System.out.println("Entro en el else");
                    System.out.println("Documento que esta asignando en este caso: ");
                    System.out.println(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion().toString());

                    datosPersonaNovedadDTO.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion().toString()));
                    datosPersonaNovedadDTO.setNumeroIdentificacion(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getNumeroIdentificacion());
                    datosPersonaNovedadDTO.setTipoIdentificacionTrabajador(TipoIdentificacionEnum.valueOf(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion().toString()));
                    datosPersonaNovedadDTO.setNumeroIdentificacionTrabajador(informacionActualizacionNovedadDTO.getConfirmacionAbonoAdminSubsidio().getNumeroIdentificacion());
                }
                if (datosPersonaNovedadDTO.getTipoIdentificacion().equals(tipoIdentificacion) && datosPersonaNovedadDTO.getNumeroIdentificacion().equals(numeroIdentificacionAdmin)) {
                    medioDePagoModeloDTO.setAfiliadoEsAdministradorSubsidio(true);
                } else {
                    medioDePagoModeloDTO.setAfiliadoEsAdministradorSubsidio(false);
                }
                PersonaModeloDTO personaModeloDTO1 = new PersonaModeloDTO();
                personaModeloDTO1.setTipoIdentificacion(tipoIdentificacion);
                personaModeloDTO1.setNumeroIdentificacion(numeroIdentificacionAdmin);

                medioDePagoModeloDTO.setAdmonSubsidio(personaModeloDTO1);
                medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
                medioDePagoModeloDTO.setSitioPago(parametroSitioPagoConfirmacionAbonos()); //xxx
                datosPersonaNovedadDTO.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
                datosPersonaNovedadDTO.setIdGrupoFamiliar(idGrupoFamiliarObject);

                System.out.println("Impresion de los datos persona novedad");
                System.out.println(datosPersonaNovedadDTO.toString());

                solNovedadDTO.setDatosPersona(datosPersonaNovedadDTO);
                //No es necesario enviar el rolAfiliado
                /*
                RolAfiliado rolAfiliado = getRolAfiliadoNovedadesMasivas(solNovedadDTO, userDTO);
                if(rolAfiliado != null && rolAfiliado.getIdRolAfiliado() != null){
                    datosPersonaNovedadDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
                }
                */

                //printJsonMessage(rolAfiliado, "55231 abonos bancarios - rolAfiliado: ");
                printJsonMessage(solNovedadDTO, "55231 abonos bancarios - solNovedadDTO: ");

                solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);


            }


            if (solNovedadDTO.getResultadoValidacion() != null) {
                if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
                    //                    logger.info("Notificar solNovedadDTO.getIdSolicitud()" + solNovedadDTO.getIdSolicitud());
                    enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);//Revisar si aplica el envío de notificación.
                }

            }
        }

    }
    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoSustitucionPatronal(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Asynchronous
    @Override
    public void validarArchivoSustitucionPatronal(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue,
            UserDTO userDTO) {
        logger.info("Inicio validarArchivoSustitucionPatronal (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        // Se registra el estado inicial del cargue
        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_SUSTITUCION_PATRONAL);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        registrarConsolaEstado(consolaEstadoCargue);

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        VerificarEstructuraArchivoSustitucionPatronal verificarArchivo = new VerificarEstructuraArchivoSustitucionPatronal(tipoArchivo, archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

        //Si hay registros validos procesamos esos registros validos.
        if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
            List<SolicitudNovedadDTO> list = procesarNovedadSustitucionPatronal(tipoArchivo,
                    resultDTO.getListActualizacionInfoNovedad(), idCargue, userDTO);

            for (SolicitudNovedadDTO solNovedadDTO : list) {
//                logger.info("Notificar solNovedadDTO.getResultadoValidacion()" + solNovedadDTO.getResultadoValidacion());
                if(solNovedadDTO.getResultadoValidacion() != null){
                        if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
        //                    logger.info("Notificar solNovedadDTO.getIdSolicitud()" + solNovedadDTO.getIdSolicitud());
                         enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);//Revisar si aplica el envío de notificación.
                        }
                }
            }

            //Hay registros validos e invalidos, entonces debe finalizar con errores
            if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                    || (resultDTO.getListActualizacionInfoNovedad() == null || resultDTO.getListActualizacionInfoNovedad().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            } else {
                //Solo hay registros validos, entonces debe finalizar sin errores
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
            }
        } else {
            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
            estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
        }

        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);

        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_NOVEDAD_SUSTITUCION_PATRONAL);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);

        logger.info("Fin validarArchivoSustitucionPatronal (" + tipoArchivo + ", CargueArchivoActualizacionDTO, UserDTO)");
    }
    
    /**
     * Procesa la novedad de Sustitucion Patronal
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     */
    private List<SolicitudNovedadDTO> procesarNovedadSustitucionPatronal(TipoArchivoRespuestaEnum tipoArchivo,
            List<InformacionActualizacionNovedadDTO> listInformacionArchivo, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadSustitucionPatronal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");

        List<Callable<SolicitudNovedadDTO>> tareasParalelas = new LinkedList<>();
        for (InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO : listInformacionArchivo) {
            Callable<SolicitudNovedadDTO> parallelTask = () -> {
                informacionActualizacionNovedadDTO.setIdCargue(idCargue);
                
                ProcesarArchivoSustitucionPatronal procesarArchivoSrv = new ProcesarArchivoSustitucionPatronal(informacionActualizacionNovedadDTO);
                procesarArchivoSrv.execute();
                return procesarArchivoSrv.getResult();
            };
            tareasParalelas.add(parallelTask);
        }

        List<SolicitudNovedadDTO> listResultadoProcesamiento = new ArrayList<>();
        try {
            List<Future<SolicitudNovedadDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
            for (Future<SolicitudNovedadDTO> future : listInfoArchivoFuture) {
                listResultadoProcesamiento.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error tareas asincrona procesarNovedadSustitucionPatronal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)", e);
            throw new TechnicalException(e);
        }

        logger.debug("Finaliza procesarNovedadSustitucionPatronal(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        return listResultadoProcesamiento;
    }
    
    /**
     * Se reliza el pre-procesamiento para el llamado de la novedad de
     * Sustitucion Patronal #procesarArchivoSustitucionPatronal
     *
     * @param informacionActualizacionNovedadDTO
     * @return
     */
    @Override
    public SolicitudNovedadDTO procesarArchivoSustitucionPatronal(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            UserDTO userDTO) {

        logger.info("Inicia procesarArchivoConfirmacionAbonosBancario");
//        printJsonMessage(informacionActualizacionNovedadDTO, "procesarArchivoActualizacionSucursal: informacionActualizacionNovedadDTO - ");
        
        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();

        //Set datos
        solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solNovedadDTO.setClasificacion(ClasificacionEnum.PERSONA_JURIDICA);
        solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.SUSTITUCION_PATRONAL);
        solNovedadDTO.setObservaciones("Registro proveniente de actualización masiva - Sustitucion Patronal");
        solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
        solNovedadDTO.setCargaMultiple(true);
        solNovedadDTO.setIdCargueMultipleNovedad(informacionActualizacionNovedadDTO.getIdCargue());
        solNovedadDTO.setFechaInicioAfiliacion(informacionActualizacionNovedadDTO.getAfiliado().getFechaInicioLaboresEmpleadorDestino());
        solNovedadDTO.setFechaFinLaboresSucursalOrigenTraslado(informacionActualizacionNovedadDTO.getAfiliado().getFechaFinLaboresEmpleadorOrigen());
        
//        EmpleadorModeloDTO empleadorModeloDTO = informacionActualizacionNovedadDTO.getAfiliado().getEmpleador();
        
        DatosEmpleadorNovedadDTO datosEmpleador = informacionActualizacionNovedadDTO.getDatosEmpleadorNovedadDTO();
        datosEmpleador.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionOrigenSustPatronal());
        datosEmpleador.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionOrigenSustPatronal());
        datosEmpleador.setFechaFinLaboresSucursalOrigenTraslado(informacionActualizacionNovedadDTO.getAfiliado().getFechaFinLaboresEmpleadorOrigen());
        datosEmpleador.setFechaFinLaboresOrigenSustPatronal(informacionActualizacionNovedadDTO.getAfiliado().getFechaFinLaboresEmpleadorOrigen());        
        datosEmpleador.setFechaInicioAfiliacion(informacionActualizacionNovedadDTO.getAfiliado().getFechaInicioLaboresEmpleadorDestino());

        //Confirmar si vamos a recibir el codigo o el nombre de la sucursal!!!
        //Consultamos los datos de la sucursal del empleador destino.
        SucursalEmpresaModeloDTO sucursalDestinoDTO = consultarSucursalPorCodigo(datosEmpleador.getTipoIdentificacionDestinoSustPatronal(),
                datosEmpleador.getNumeroIdentificacionDestinoSustPatronal(),
                informacionActualizacionNovedadDTO.getAfiliado().getSucursalAfiliado());
                
        SucursalPersonaDTO sucursalPersonaDTO = new SucursalPersonaDTO();
        
        InfoAfiliadoRespectoEmpleadorDTO afiliadoRespectoEmpleadorOrigenDTO = obtenerInfoAfiliadoRespectoEmpleador(
                datosEmpleador.getTipoIdentificacion(),
                datosEmpleador.getNumeroIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion(),
                informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion());
        
//        printJsonMessage(afiliadoRespectoEmpleadorOrigenDTO, "afiliadoRespectoEmpleadorOrigenDTO GLPI62260");
        
        if (afiliadoRespectoEmpleadorOrigenDTO != null) {
            
            if (sucursalDestinoDTO != null) {
                List<SucursalPersonaDTO> listSucursalPersona = new ArrayList<>();
                sucursalPersonaDTO.setIdSucursal(sucursalDestinoDTO.getIdSucursalEmpresa());
                sucursalPersonaDTO.setIdPersona(afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getIdPersona());            
                listSucursalPersona.add(sucursalPersonaDTO);
                datosEmpleador.setTrabajadoresSustPatronal(listSucursalPersona);      
                datosEmpleador.setTrabajadoresTraslado(Arrays.asList(sucursalPersonaDTO.getIdPersona()));
            }

//            printJsonMessage(datosEmpleador, "datosEmpleador GLPI62260");
            
            SucursalEmpresaModeloDTO sucursalOrigenDTO = consultarSucursalPorNombre(datosEmpleador.getTipoIdentificacionOrigenSustPatronal(),
                datosEmpleador.getNumeroIdentificacionOrigenSustPatronal(),
                afiliadoRespectoEmpleadorOrigenDTO.getInfoEstadoAfiliado().getSucursalAsociada());
            
            if(sucursalOrigenDTO != null){
//                printJsonMessage(sucursalOrigenDTO, "sucursalOrigenDTO GLPI62260");
                datosEmpleador.setSucursalesOrigenSustPatronal(Arrays.asList(sucursalOrigenDTO.getIdSucursalEmpresa()));                
            }
        
        }
//        printJsonMessage(datosEmpleador, "datosEmpleador GLPI 62260 sustitucion patronal");
        
        List<EmpleadorRelacionadoAfiliadoDTO> empleadoresRelacionadosAfiliadoList = 
                obtenerEmpleadoresRelacionadosAfiliado(
                    datosEmpleador.getTipoIdentificacion(),
                    datosEmpleador.getNumeroIdentificacion(),
                    informacionActualizacionNovedadDTO.getAfiliado().getTipoIdentificacion(),
                    informacionActualizacionNovedadDTO.getAfiliado().getNumeroIdentificacion()
                );
        
//        printJsonMessage(empleadoresRelacionadosAfiliadoList, "empleadoresRelacionadosAfiliadoList GLPI 62260 sustitucion patronal");
        
        if(CollectionUtils.isNotEmpty(empleadoresRelacionadosAfiliadoList)){
            EmpleadorRelacionadoAfiliadoDTO relacionadoAfiliadoDTO = empleadoresRelacionadosAfiliadoList.get(0);
            if(relacionadoAfiliadoDTO != null){
                datosEmpleador.setIdEmpleador(relacionadoAfiliadoDTO.getIdEmpleador());
            }
        }

        List<Empleador> empleadoresList = obtenerInformacionEmpleador(informacionActualizacionNovedadDTO.getDatosEmpleadorNovedadDTO().getTipoIdentificacionDestinoSustPatronal(),
                informacionActualizacionNovedadDTO.getDatosEmpleadorNovedadDTO().getNumeroIdentificacionDestinoSustPatronal());

//        printJsonMessage(empleadoresList, "empleadoresList GLPI 62260 sustitucion patronal");
        
        if(CollectionUtils.isNotEmpty(empleadoresList)){
            Empleador empleadorDestino = empleadoresList.get(0);
            if(empleadorDestino != null){
                datosEmpleador.setIdEmpleadorDestinoSustPatronal(empleadorDestino.getIdEmpleador());
                if(empleadorDestino.getEmpresa() != null ){
                    datosEmpleador.setRazonSocialDestinoSustPatronal(empleadorDestino.getEmpresa().getNombreComercial());
                }
            }
        }
        
        solNovedadDTO.setDatosEmpleador(datosEmpleador);

        printJsonMessage(solNovedadDTO, "solNovedadDTO GLPI 62260 sustitucion patronal");

        //Revisar este metodo !!!!!!!
        solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);
        
        return solNovedadDTO;
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

    @Override
    public RespuestaValidacionArchivoDTO validarNameArchivoConfirmacionAbonosBancario(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        logger.info("Inicia validarNameArchivoConfirmacionAbonosBancario");
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();

        // InformacionArchivoDTO archivo = new InformacionArchivoDTO();
        //archivo.setFileName("CCPCC_F21202201101_NOPAGOSEXTRA.txt");
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("validarNameArchivoConfirmacionAbonosBancario: nombre archivo a validar: " + archivo.getFileName());

        Integer validacionName = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VALIDACION_NAME_ARCHIVO_CONSOLA)
                .setParameter("nombrearchivo", archivo.getFileName())
                .getSingleResult();

       // logger.info("Respesta validacionName: " + validacionName);

        String[] arrOfStr = archivo.getFileName().split("_");
        List<String> errores = new ArrayList<>();
        String prefijo = "";
        String codigoBanco = "";
        String fechaGeneracion = "";
        String consecutivo = "";

        //VALIDACION #1: Archivo ya cargado en el sistema. 
        if (validacionName != null) {
            if (validacionName == 1) {
                logger.info("Val 1: el archivo ya ha sido cargado en el sistema");
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                errores.add("El archivo que desea cargar ya existe en el sistema. Por favor cargar un archivo que no haya sido procesado previamente.");
                solNovedadDTO.setStatus("KO");
            } else {
                logger.info("tamaño nombre: " + arrOfStr.length);
                if (arrOfStr.length == 4) {
                    //se arama el consecutivo 
                    String[] consecutivoArray = arrOfStr[3].split("\\.");
                    prefijo = arrOfStr[0];
                    codigoBanco = arrOfStr[1];
                    fechaGeneracion = arrOfStr[2];
                    consecutivo = consecutivoArray[0];
                } else if (arrOfStr.length == 1) {
                    prefijo = arrOfStr[0];
                    codigoBanco = "";
                    fechaGeneracion = "";
                    consecutivo = "";
                } else if (arrOfStr.length == 2) {
                    prefijo = arrOfStr[0];
                    codigoBanco = arrOfStr[1];
                    fechaGeneracion = "";
                    consecutivo = "";
                } else if (arrOfStr.length == 3) {
                    prefijo = arrOfStr[0];
                    codigoBanco = arrOfStr[1];
                    fechaGeneracion = arrOfStr[2];
                    consecutivo = "";
                } else {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("El nombre del archivo supera los grupos de campos permitidos. Recuerde; prefijo_codigoBanco_fechaGeneracion_consecutivo");
                    solNovedadDTO.setStatus("KO");
                }

             //   printJsonMessage(arrOfStr, "arrOfStr");

                if (prefijo.length() > 2 || !prefijo.equals("CA") || prefijo.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el prefijo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
                if (codigoBanco.length() > 12 || codigoBanco.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el código del banco dispersor.");
                    solNovedadDTO.setStatus("KO");
                }
                if (fechaGeneracion.length() > 8 || fechaGeneracion.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en la fecha del archivo.");
                    solNovedadDTO.setStatus("KO");
                }
                if (consecutivo.length() > 2 || fechaGeneracion.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el consecutivo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
            }

            if (errores.size() == 0) {
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                solNovedadDTO.setMensaje("El archivo esta OK.");
                solNovedadDTO.setStatus("OK");
            } else {
                solNovedadDTO.setListError(errores);
            }

        } else {
            solNovedadDTO.setMensaje("Error, al validar la existencia del archivo en el sistema.");
            solNovedadDTO.setStatus("KO");
        }

        return solNovedadDTO;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarNameArchivoSustitucionPatronal(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public RespuestaValidacionArchivoDTO validarNameArchivoSustitucionPatronal(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        logger.info("Inicia validarNameArchivoSustitucionPatronal");
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();

        printJsonMessage(userDTO, "validarNameArchivoSustitucionPatronal userDTO");
        
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("validarNameArchivoSustitucionPatronal: nombre archivo a validar: " + archivo.getFileName());
        
        Integer validacionName = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VALIDACION_NAME_ARCHIVO_CONSOLA)
                .setParameter("nombrearchivo", archivo.getFileName())
                .getSingleResult();

        //VALIDACION #1: Archivo ya cargado en el sistema. 
        if (validacionName != null) {
            if (validacionName == 1) {
                logger.info("Val 1: el archivo ya ha sido cargado en el sistema");
                solNovedadDTO.setMensaje("El archivo que desea cargar ya existe en el sistema. Por favor cargar un archivo que no haya sido procesado previamente");
                solNovedadDTO.setStatus("KO");
            } else {
                
                String REGEX ="(CCF)[0-9]{3}[_A-Z0-9]{5,13}[0-9]{4}(1[0-2]|0[1-9])(3[01]|[12][0-9]|0[1-9])(.*)";
//                List<String> errores =  new ArrayList<>();
                
                boolean matcher = Pattern.matches(REGEX, archivo.getFileName());
                if (matcher) {
                    solNovedadDTO.setMensaje("El archivo esta OK.");
                    solNovedadDTO.setStatus("OK");
                }
                else {
                    solNovedadDTO.setMensaje("El nombre del archivo no cumple las validaciones.");
//                    errores.add("El nombre del archivo no cumple las validaciones");
                    solNovedadDTO.setStatus("KO");
                }
            }
        }else{
           solNovedadDTO.setMensaje("Error, al validar la existencia del archivo en el sistema.");
           solNovedadDTO.setStatus("KO");
        } 
     
        return solNovedadDTO;
    }
	

    private void confirmarResultadosAbonosBancariosArchivo(List<InformacionActualizacionNovedadDTO> listActualizacion,UserDTO userDTO) {
        logger.info("Inicio confirmarResultadosAbonosBancariosArchivo ");
       // printJsonMessage(listActualizacion,"listActualizacion: ");
        
        List<ConfirmacionAbonoBancarioCargueDTO> listConfAbonosBancarios = new ArrayList<>(); 
                
        for (InformacionActualizacionNovedadDTO info : listActualizacion ) {
            listConfAbonosBancarios.add(info.getConfirmacionAbonoAdminSubsidio());            
        }
        
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            userDTO.setEmail("");
        }
        
        String userEmail = userDTO.getEmail();
    
        // logger.info("userDTO.getEmail(): " + userEmail);
        // printJsonMessage(listConfAbonosBancarios,"listConfAbonosBancarios: ");
    
        // Para no reventar los abonos, se hace una iteracion de a bloques y se realiza de forma asincrona
        int paso = 1000;
        for (int inicio = 0; inicio < listActualizacion.size(); inicio += paso) {
            int fin = Math.min(inicio + paso, listActualizacion.size());
            
            ConfirmarResultadosAbonosBancariosArchivo crearCargueArchivoActualizacion = new ConfirmarResultadosAbonosBancariosArchivo(userEmail, listConfAbonosBancarios.subList(inicio, fin));
            crearCargueArchivoActualizacion.execute(); 
        }
        logger.info("Finalizo confirmarResultadosAbonosBancariosArchivo ");
    
    }

    private List<Long> registrarCambioMedioDePagoSubsidio(InformacionActualizacionNovedadDTO listActualizacion) {
        logger.info("Inicia registrarCambioMedioDePagoSubsidio");
       // printJsonMessage(listActualizacion, "listActualizacion");
        
        RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO = new RegistroCambioMedioDePagoDTO();
        MedioDePagoModeloDTO medioDePagoModelo = new MedioDePagoModeloDTO();
        List<CuentaAdministradorSubsidioDTO> listaRegistrosAbonos = new  ArrayList<>();
        Long idAdminSubsidio = null;
        List<Long> resp = null;
        TipoIdentificacionEnum tipoIdentificacion = listActualizacion.getConfirmacionAbonoAdminSubsidio().getTipoIdentificacion();
        String numeroIdentificacion = listActualizacion.getConfirmacionAbonoAdminSubsidio().getNumeroIdentificacion();
        String casId = listActualizacion.getConfirmacionAbonoAdminSubsidio().getCasId();
        
        String tipoIdentificacionNombre = "";   
               
        // Ajuste GLPI 67367
        
         if(tipoIdentificacion == null){
                Object[] obtenerTitualarAdminSubsidioObjectResult =null;
                ObtenerTitularCuentaAdministradorSubsidioByCasId obtenerTipoIdentificacion = new ObtenerTitularCuentaAdministradorSubsidioByCasId(casId);
                     obtenerTipoIdentificacion.execute(); 
                     obtenerTitualarAdminSubsidioObjectResult = obtenerTipoIdentificacion.getResult(); 
                      tipoIdentificacionNombre = obtenerTitualarAdminSubsidioObjectResult[0].toString();  
                                        
         }else{
                 tipoIdentificacionNombre = tipoIdentificacion.name();
         } 

        
        List<Object[]> infoAdminSub = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_ADMIN_SUBSIDIO)
                    .setParameter("tipoIdenAdminSubsidio", tipoIdentificacionNombre)
                    .setParameter("numeroIdenAdminSubsidio",numeroIdentificacion)
                    .setParameter("casId",casId).getResultList();
            
        printJsonMessage(infoAdminSub, "infoAdminSub");

        if (infoAdminSub != null) {
            
           logger.info("si entro");
            for (Object[] obj : infoAdminSub) {
                CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO();

              //  printJsonMessage(obj, "objjjjjjjj ");

                idAdminSubsidio = Long.valueOf(String.valueOf(obj[20] == null ? "": obj[20].toString()));
                //Date FechaHoraCreacionRegistro = new Date(Long.valueOf(String.valueOf(obj[1] == null ? "": obj[1].toString())));

                
                cuentaAdministradorSubsidioDTO.setIdCuentaAdministradorSubsidio(Long.valueOf(String.valueOf(obj[0] == null ? "": obj[0].toString())));
                cuentaAdministradorSubsidioDTO.setFechaHoraCreacionRegistro(obj[1] == null ? null: (Date) obj[1]);
                cuentaAdministradorSubsidioDTO.setUsuarioCreacionRegistro(String.valueOf(String.valueOf(obj[2] == null ? null: obj[2].toString())));
                cuentaAdministradorSubsidioDTO.setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum.valueOf(obj[3] == null ? null: obj[3].toString()));
                cuentaAdministradorSubsidioDTO.setEstadoTransaccion(EstadoTransaccionSubsidioEnum.valueOf(obj[4] == null ? null: obj[4].toString()));
                cuentaAdministradorSubsidioDTO.setOrigenTransaccion(OrigenTransaccionEnum.valueOf(obj[5] == null ? null: obj[5].toString()));
                cuentaAdministradorSubsidioDTO.setMedioDePago(TipoMedioDePagoEnum.valueOf(obj[6] == null ? null: obj[6].toString()));
                cuentaAdministradorSubsidioDTO.setValorOriginalTransaccion(obj[7] != null ? (BigDecimal) obj[7] : BigDecimal.ZERO);
                cuentaAdministradorSubsidioDTO.setCodigoBancoAdminSubsidio(String.valueOf(String.valueOf(obj[8] == null ? null: obj[8].toString())));
                cuentaAdministradorSubsidioDTO.setNombreBancoAdminSubsidio(String.valueOf(String.valueOf(obj[9] == null ? null: obj[9].toString())));
                cuentaAdministradorSubsidioDTO.setTipoCuentaAdminSubsidio(TipoCuentaEnum.valueOf(obj[10] == null ? null: obj[10].toString()));
                cuentaAdministradorSubsidioDTO.setNumeroCuentaAdminSubsidio(String.valueOf(String.valueOf(obj[11] == null ? null: obj[11].toString())));
                cuentaAdministradorSubsidioDTO.setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum.valueOf(String.valueOf(obj[12] == null ? null: obj[12].toString())));
                cuentaAdministradorSubsidioDTO.setNumeroIdentificacionTitularCuentaAdminSubsidio(String.valueOf(String.valueOf(obj[13] == null ? null: obj[13].toString())));
                cuentaAdministradorSubsidioDTO.setNombreTitularCuentaAdminSubsidio(String.valueOf(String.valueOf(obj[14] == null ? null: obj[14].toString())));
                cuentaAdministradorSubsidioDTO.setValorRealTransaccion(obj[15] != null ? (BigDecimal) obj[15] : BigDecimal.ZERO);
                cuentaAdministradorSubsidioDTO.setFechaHoraTransaccion(obj[16] == null ? null: (Date) obj[16]);
                cuentaAdministradorSubsidioDTO.setUsuarioTransaccionLiquidacion(String.valueOf(String.valueOf(obj[17] == null ? null: obj[17].toString())));
                cuentaAdministradorSubsidioDTO.setFechaHoraUltimaModificacion(obj[18] == null ? null: (Date) obj[18]);
                cuentaAdministradorSubsidioDTO.setUsuarioUltimaModificacion(String.valueOf(String.valueOf(obj[19] == null ? null: obj[19].toString())));
                cuentaAdministradorSubsidioDTO.setIdAdministradorSubsidio(Long.valueOf(String.valueOf(obj[20] == null ? "": obj[20].toString())));
                cuentaAdministradorSubsidioDTO.setNombresApellidosAdminSubsidio(String.valueOf(String.valueOf(obj[21] == null ? null: obj[21].toString())));
                cuentaAdministradorSubsidioDTO.setTipoIdAdminSubsidio(TipoIdentificacionEnum.valueOf(String.valueOf(obj[22] == null ? null: obj[22].toString())));
                cuentaAdministradorSubsidioDTO.setNumeroIdAdminSubsidio(String.valueOf(String.valueOf(obj[23] == null ? null: obj[23].toString())));
                cuentaAdministradorSubsidioDTO.setIdMedioDePago(Long.valueOf(String.valueOf(obj[24] == null ? "": obj[24].toString())));
                cuentaAdministradorSubsidioDTO.setSolicitudLiquidacionSubsidio(Long.valueOf(String.valueOf(obj[25] == null ? "": obj[25].toString())));
                cuentaAdministradorSubsidioDTO.setNumeroTarjetaAdminSubsidio(String.valueOf(String.valueOf(obj[26] == null ? null: obj[26].toString())));
            
                listaRegistrosAbonos.add(cuentaAdministradorSubsidioDTO);
            }
            
            
            medioDePagoModelo.setEfectivo(true);
            medioDePagoModelo.setSitioPago(parametroSitioPagoConfirmacionAbonos());
            medioDePagoModelo.setTipoMedioDePago(TipoMedioDePagoEnum.EFECTIVO);
            

            //Se setea los valores finales 
            registroCambioMedioDePagoDTO.setListaRegistrosAbonos(listaRegistrosAbonos);
            registroCambioMedioDePagoDTO.setMedioDePagoModelo(medioDePagoModelo);
           // printJsonMessage(registroCambioMedioDePagoDTO, "registroCambioMedioDePagoDTO:  ");
           // logger.info("idAdminSubsidio: " + idAdminSubsidio);
            
            
            RegistrarCambioMedioDePagoSubsidioArchivo registrarCambioMedioDePagoSubsidioS = new RegistrarCambioMedioDePagoSubsidioArchivo(idAdminSubsidio,registroCambioMedioDePagoDTO);
            registrarCambioMedioDePagoSubsidioS.execute();
            resp = (List<Long>) registrarCambioMedioDePagoSubsidioS.getResult();       
        }
        logger.info("Finaliza registrarCambioMedioDePagoSubsidio");
        return resp;
        
    }
    
    private Boolean controlRegistroNovedad() {
       logger.info("Inicia controlRegistroNovedad");
        Boolean resp = false;
        String validacion;
        
        try{
            validacion = (String) entityManager.createNamedQuery(NamedQueriesConstants.PARAMETRO_CONTROL_EJECUCION_ABONOS_AUTOMATICOS).getSingleResult();
            logger.info("Resultado:  controlRegistroNovedad : " + validacion);
            if (validacion != null) {
                switch(validacion){
                    case "0" : 
                      resp = false; 
                      break;
                    case "1" :
                        resp = true;
                        break;
                    default:
                        break;
                }
            }
        }catch(Exception e){
         logger.error("Error en activoControlRegistroNovedad", e);
            throw new TechnicalException(e);
        }
        logger.info("Finaliza controlRegistroNovedad");
        return resp;
    }
    
    
    private Long parametroSitioPagoConfirmacionAbonos() {
       logger.info("Inicia parametroSitioPagoConfirmacionAbonos");
        Long resp = Long.valueOf(53);
        BigInteger validacion;
        
        try{
            validacion = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.SITIO_PAGO_CONFIRMACIÓN_CARGUE_MASIVO).getSingleResult();
            logger.info("Resultado:  parametroSitioPagoConfirmacionAbonos : " + validacion);
            
            if (validacion != null) {
               resp = validacion.longValue(); 
            }
        }catch(Exception e){
         logger.error("Error en parametroSitioPagoConfirmacionAbonos", e);
            throw new TechnicalException(e);
        }
        logger.info("Finaliza parametroSitioPagoConfirmacionAbonos");
        return resp;
    }
    
    /**
     * Método que consultar el idRolAfiliado
     *
     * @param novedadDTO novedad a consultarse.
     * @param userDTO usuario que radica la solicitud.
     */
    private RolAfiliado getRolAfiliadoNovedadesMasivas(SolicitudNovedadDTO novedadDTO, UserDTO userDTO) {
        logger.info("**__** getRolAfiliadoNovedadesMasivas::  " + novedadDTO);
        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
        return p.getRolAfiliadoNovedadesMasivas(novedadDTO, userDTO, entityManager);
    }

    public void persistirItemChequeo(SolicitudNovedadDTO SolicitudNovedadDTO){
        logger.info("Inicia persistirItemChequeo");
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(SolicitudNovedadDTO.getIdSolicitud());
        try{
            
            logger.info(SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador());
            Object idSolicitudItemChequeo = (Object) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ITEM_CHEQUEO)
            .setParameter("tipoIdentificacionAfiliado", SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacion().name())
            .setParameter("numeroIdentificacionAfiliado",SolicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion())
            .setParameter("tipoIdentificacionEmpleador", SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacionEmpleador().name())
            .setParameter("numeroIdentificacionEmpleador",SolicitudNovedadDTO.getDatosPersona().getNumeroIdentificacionEmpleador())
            .getSingleResult();

            solicitud.setIdSolicitud(Long.parseLong(idSolicitudItemChequeo.toString()));
            

            logger.info("SolicitudNovedadDTO.getDatosPersona(): " + SolicitudNovedadDTO.getDatosPersona().toString());

            List<ItemChequeo> ItemsChequeo = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ITEM_CHEQUEO_PERSONA,ItemChequeo.class)
                    .setParameter("solicitudGlobal", solicitud)
                    .setParameter("tipoIdentificacion", SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacion())
                    .setParameter("numeroIdentificacion", SolicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion())
                    .getResultList();


            solicitud.setIdSolicitud(SolicitudNovedadDTO.getIdSolicitud());
            for(ItemChequeo itemChequeoDTO : ItemsChequeo){
                ItemChequeo itemChequeoSol = new ItemChequeo();
                itemChequeoSol.setRequisito(itemChequeoDTO.getRequisito());
                itemChequeoSol.setPersona(itemChequeoDTO.getPersona());
                itemChequeoSol.setIdentificadorDocumento(itemChequeoDTO.getIdentificadorDocumento());
                itemChequeoSol.setVersionDocumento(itemChequeoDTO.getVersionDocumento());
                itemChequeoSol.setEstadoRequisito(itemChequeoDTO.getEstadoRequisito());
                itemChequeoSol.setCumpleRequisito(itemChequeoDTO.getCumpleRequisito());
                itemChequeoSol.setFormatoEntregaDocumento(itemChequeoDTO.getFormatoEntregaDocumento());
                itemChequeoSol.setComentarios(itemChequeoDTO.getComentarios());
                itemChequeoSol.setCumpleRequisitoBack(itemChequeoDTO.getCumpleRequisitoBack());
                itemChequeoSol.setComentariosBack(itemChequeoDTO.getComentariosBack());
                itemChequeoSol.setSolicitudGlobal(solicitud);
                itemChequeoSol.setFechaRecepcionDocumentos(new Date(SolicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador()));
                entityManager.persist(itemChequeoSol);
            }
        }
        catch (NoResultException e) {
            
            ItemChequeo itemChequeoSol = new ItemChequeo();
            Requisito requisito = entityManager.find(Requisito.class, 91L);
            BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null,
            null, null, SolicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion(), SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacion(), null);
            buscarPersonas.execute();
            List<PersonaDTO> personas = buscarPersonas.getResult();
            Persona persona = new Persona();
            persona.setIdPersona(personas.get(0).getIdPersona());
            itemChequeoSol.setRequisito(requisito);
            itemChequeoSol.setPersona(persona);
            itemChequeoSol.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO);
            itemChequeoSol.setCumpleRequisito(Boolean.TRUE);
            itemChequeoSol.setCumpleRequisitoBack(Boolean.TRUE);
            itemChequeoSol.setSolicitudGlobal(solicitud);
            itemChequeoSol.setFechaRecepcionDocumentos(new Date(SolicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador()));
            entityManager.persist(itemChequeoSol);
            e.printStackTrace();
        }

            ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(SolicitudNovedadDTO.getDatosPersona().getIdRolAfiliado());
            consultarRolAfiliado.execute();
            RolAfiliadoModeloDTO afiliado = consultarRolAfiliado.getResult();
            ConsultarBeneficiarios consultaBeneficiarios = new ConsultarBeneficiarios(afiliado.getAfiliado().getIdAfiliado(), false);
            consultaBeneficiarios.execute();
            List<BeneficiarioDTO> beneficiarios = consultaBeneficiarios.getResult();
            if(beneficiarios != null && !beneficiarios.isEmpty()){
                for(BeneficiarioDTO beneficiario : beneficiarios){
                    if(beneficiario.getEstadoBeneficiarioAfiliado().equals(EstadoAfiliadoEnum.INACTIVO)){
                        continue;
                    }
                    try{
                    
                    List<TipoTransaccionEnum> listTxActivarBeneficiario = new ArrayList<>();
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_WEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL);
                    listTxActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_WEB);
                    Object idSolicitudItemChequeoBeneficiario = (Object) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ITEM_CHEQUEO_BENEFICIARIO)
                    .setParameter("tipoIdentificacionBeneficiario", beneficiario.getPersona().getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacionBeneficiario",beneficiario.getPersona().getNumeroIdentificacion())
                    .setParameter("tipoIdentificacionAfiliado", SolicitudNovedadDTO.getDatosPersona().getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacionAfiliado",SolicitudNovedadDTO.getDatosPersona().getNumeroIdentificacion())
                    .setParameter("tipoTransaccion", listTxActivarBeneficiario)
                    .getSingleResult();
    
                    solicitud.setIdSolicitud(Long.parseLong(idSolicitudItemChequeoBeneficiario.toString()));
                    List<ItemChequeo> ItemsChequeoBeneficiario = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ITEM_CHEQUEO_PERSONA,ItemChequeo.class)
                    .setParameter("solicitudGlobal", solicitud)
                    .setParameter("tipoIdentificacion", beneficiario.getPersona().getTipoIdentificacion())
                    .setParameter("numeroIdentificacion", beneficiario.getPersona().getNumeroIdentificacion())
                    .getResultList();
    
                    solicitud.setIdSolicitud(SolicitudNovedadDTO.getIdSolicitud());
    
                    for(ItemChequeo itemChequeoDTO : ItemsChequeoBeneficiario){
                        ItemChequeo itemChequeoSol = new ItemChequeo();
                        itemChequeoSol.setRequisito(itemChequeoDTO.getRequisito());
                        itemChequeoSol.setPersona(itemChequeoDTO.getPersona());
                        itemChequeoSol.setIdentificadorDocumento(itemChequeoDTO.getIdentificadorDocumento());
                        itemChequeoSol.setVersionDocumento(itemChequeoDTO.getVersionDocumento());
                        itemChequeoSol.setEstadoRequisito(itemChequeoDTO.getEstadoRequisito());
                        itemChequeoSol.setCumpleRequisito(itemChequeoDTO.getCumpleRequisito());
                        itemChequeoSol.setFormatoEntregaDocumento(itemChequeoDTO.getFormatoEntregaDocumento());
                        itemChequeoSol.setComentarios(itemChequeoDTO.getComentarios());
                        itemChequeoSol.setCumpleRequisitoBack(itemChequeoDTO.getCumpleRequisitoBack());
                        itemChequeoSol.setComentariosBack(itemChequeoDTO.getComentariosBack());
                        itemChequeoSol.setSolicitudGlobal(solicitud);
                        itemChequeoSol.setFechaRecepcionDocumentos(new Date(SolicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador()));
                        entityManager.persist(itemChequeoSol);
                    }
    
                    logger.info("idSolicitudItemChequeoBeneficiario: " + idSolicitudItemChequeoBeneficiario);
                    }
                    catch (Exception e) {
            
                        ItemChequeo itemChequeoSol = new ItemChequeo();
                        Requisito requisito = entityManager.find(Requisito.class, 91L);
                        BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null, null,
                        null, null, beneficiario.getPersona().getNumeroIdentificacion(), beneficiario.getPersona().getTipoIdentificacion(), null);
                        buscarPersonas.execute();
                        List<PersonaDTO> personas = buscarPersonas.getResult();
                        Persona persona = new Persona();
                        persona.setIdPersona(personas.get(0).getIdPersona());
                        itemChequeoSol.setRequisito(requisito);
                        itemChequeoSol.setPersona(persona);
                        itemChequeoSol.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OBLIGATORIO);
                        itemChequeoSol.setCumpleRequisito(Boolean.TRUE);
                        itemChequeoSol.setCumpleRequisitoBack(Boolean.TRUE);
                        itemChequeoSol.setSolicitudGlobal(solicitud);
                        itemChequeoSol.setFechaRecepcionDocumentos(new Date(SolicitudNovedadDTO.getDatosPersona().getFechaInicioLaboresConEmpleador()));
                        entityManager.persist(itemChequeoSol);
                        e.printStackTrace();
                    }

                }
            }       
 
        logger.info("Finaliza persistirItemChequeo");

    }
    // ===================================== inicia masivo transferencia

    @Override
    @Asynchronous
    public void validarArchivoCambioMasivoTransferencia(TipoArchivoRespuestaEnum tipoArchivo,CargueArchivoActualizacionDTO cargue,UserDTO userDTO){
        logger.info("Inicia validarArchivoCambioMasivoTransferencia(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO)");

        ConsolaEstadoProcesoDTO consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
        consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("0"));
        consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CAMBIO_MEDIO_DE_PAGO_MASIVO_TRANSFERENCIA);        

        Long idConsolaEstadoProceso = registrarConsolaEstadoProcesosMasivos(consolaEstadoCargueProcesos);

        consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConsolaEstadoProceso);


        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());

        EstadoCargueMasivoEnum estadoProcesoMasivo;
        EstadoCargueArchivoActualizacionEnum estadoCargue;

        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_ARCHIVOS_NOVEDAD_MASIVA_TRANSFERENCIA);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());

        registrarConsolaEstado(consolaEstadoCargue);
 
        VerificarEstructuraArchvoNovedadMasivaTransferencia verificarArchivo = new VerificarEstructuraArchvoNovedadMasivaTransferencia(tipoArchivo, archivo);
        verificarArchivo.execute();
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
         logger.info("termina VerificarEstructuraArchvoNovedadMasivaTransferencia ");
        if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                 ||  EstadoCargaMultipleEnum.ANULADO.equals(resultDTO.getEstadoCargue()) 
                || (resultDTO.getCargueTrasladoMedioPagoTranferencia() == null || resultDTO.getCargueTrasladoMedioPagoTranferencia().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            logger.info("Estado de cargue CANCELADO,ANULADO o getCargueTrasladoMedioPagoTranferencia Vacio");
            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
            actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);
        } else {

            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
            actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);

            List<CargueTrasladoMedioPagoTranferencia> informacionNovedades = null;    

            if (CollectionUtils.isNotEmpty(resultDTO.getCargueTrasladoMedioPagoTranferencia())) {
                // ACA DEBERIA DE INICIAR PROCESO DE NOVEDAD, SOBRE LOS REGISTROS HABILES
                // (CREAR LA NOVEDAD)
                // Log final
                logger.info("Filtro completado.");
            //  printJsonMessage(informacionNovedades, "informacionNovedades: ");
                informacionNovedades = resultDTO.getCargueTrasladoMedioPagoTranferencia();
                procesarNovedadCargueArchivoTransferenciaASYNC(tipoArchivo, informacionNovedades, idCargue, userDTO,consolaEstadoCargueProcesos);

            
                    //Solo hay registros validos, entonces debe finalizar sin errores
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                    estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;
                
            }else {
                logger.info("empty getCargueTrasladoMedioPagoTranferenciaFIN_ERROR");
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            }
        }
        logger.info("estado del cargue estadoCargue "+estadoCargue);
        // ultimo paso seteo estatus y demas contra la consola de cargue
        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);
    
        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_ARCHIVOS_NOVEDAD_MASIVA_TRANSFERENCIA);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
        // PENDIENTE DE FINALIZAR SE TOMO COMO "PLANTILLA validarArchivoConfirmacionAbonosBancario"
    }
 
    @Override
    public RespuestaValidacionArchivoDTO validarNombreArchivoCambioMasivoTransferencia(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        logger.info("Inicia validarNombreArchivoCambioMasivoTransferencia");
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();

        // InformacionArchivoDTO archivo = new InformacionArchivoDTO();
        //archivo.setFileName("CCPCC_F21202201101_NOPAGOSEXTRA.txt");
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("validarNombreArchivoCambioMasivoTransferencia: nombre archivo a validar: " + archivo.getFileName());

        Integer validacionName = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VALIDACION_NAME_ARCHIVO_CONSOLA)
                .setParameter("nombrearchivo", archivo.getFileName())
                .getSingleResult();

       // logger.info("Respesta validacionName: " + validacionName);

        String[] arrOfStr = archivo.getFileName().split("_");
        List<String> errores = new ArrayList<>();
        String nombrePrefijo = "";
        String fechaGeneracion = "";
        String consecutivo = "";

        //VALIDACION #1: Archivo ya cargado en el sistema. 
        if (validacionName != null) {
            if (validacionName == 1) {
                logger.info("Val 1: el archivo ya ha sido cargado en el sistema");
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                errores.add("El archivo que desea cargar ya existe en el sistema. Por favor cargar un archivo que no haya sido procesado previamente.");
                solNovedadDTO.setStatus("KO");
            } else {
                logger.info("tamaño nombre: " + arrOfStr.length);
                if (arrOfStr.length == 3) {
                    //se arama el consecutivo 
                    String[] consecutivoArray = arrOfStr[2].split("\\.");
                    nombrePrefijo = arrOfStr[0];
                    fechaGeneracion = arrOfStr[1];
                    consecutivo = consecutivoArray[0];
                } else {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("El nombre del archivo supera los grupos de campos permitidos. Recuerde; prefijo_fecha_consevutivo");
                    solNovedadDTO.setStatus("KO");
                }

             //   printJsonMessage(arrOfStr, "arrOfStr");

                if (nombrePrefijo.length() == 0 || nombrePrefijo.length() > 30 || nombrePrefijo.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el prefijo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
                if (fechaGeneracion.length() > 8 || fechaGeneracion.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en la fecha del archivo.");
                    solNovedadDTO.setStatus("KO");
                }
                if (consecutivo.length() > 2 || fechaGeneracion.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el consecutivo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
            }

            if (errores.size() == 0) {
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                solNovedadDTO.setMensaje("El archivo esta OK.");
                solNovedadDTO.setStatus("OK");
            } else {
                solNovedadDTO.setListError(errores);
            }

        } else {
            solNovedadDTO.setMensaje("Error, al validar la existencia del archivo en el sistema.");
            solNovedadDTO.setStatus("KO");
        }

        return solNovedadDTO;
    }

        /**
     * Procesa la novedad de confirmacion abonos bancarios
     *
     * @param tipoArchivo Indica el tipo de archivo
     * @param listInformacionArchivo Informacion del archivo
     * @param userDTO Usuario que realiza el proceso
     */
    @Asynchronous
    private void procesarNovedadCargueArchivoTransferenciaASYNC (TipoArchivoRespuestaEnum tipoArchivo,
        List<CargueTrasladoMedioPagoTranferencia> listInformacionArchivo, Long idCargue, UserDTO userDTO,ConsolaEstadoProcesoDTO consolaEstadoCargueProcesos) {
        logger.info("Inicia procesarNovedadCargueArchivoTransferenciaASYNC(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        int tamanoLista = listInformacionArchivo.size();
        int progreso = Integer.valueOf(consolaEstadoCargueProcesos.getGradoAvance().intValue());
        int registrosPorPaso = tamanoLista >= 10 ? tamanoLista / 10 : 1;
        int incremento = 8;
        int progresoActual = progreso;

        try{
            for(int i = 0 ; i < listInformacionArchivo.size(); i++ ){

                procesarNovedadCargueArchivoTransferencia(tipoArchivo, listInformacionArchivo.get(i), idCargue, userDTO);

                if(i >0  && i % registrosPorPaso == 0 ){
                    progresoActual += incremento;
                    consolaEstadoCargueProcesos.setGradoAvance(BigDecimal.valueOf(progresoActual));
                    actualizarProcesoConsolaEstado(consolaEstadoCargueProcesos.getIdConsolaEstadoProcesoMasivo(), consolaEstadoCargueProcesos);
                }
            }
        }catch(Exception e ){
            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("60"));
            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
            actualizarProcesoConsolaEstado(consolaEstadoCargueProcesos.getIdConsolaEstadoProcesoMasivo(), consolaEstadoCargueProcesos);
        }finally{

            if (!consolaEstadoCargueProcesos.getEstado().equals(EstadoCargueMasivoEnum.FIN_ERROR)) {
                consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
                consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
            }
            actualizarProcesoConsolaEstado(consolaEstadoCargueProcesos.getIdConsolaEstadoProcesoMasivo(), consolaEstadoCargueProcesos);
        }
    }
  
    private void procesarNovedadCargueArchivoTransferencia(TipoArchivoRespuestaEnum tipoArchivo,
            CargueTrasladoMedioPagoTranferencia cargueTrasladoMedioPagoTranferencia, Long idCargue, UserDTO userDTO) {
        logger.info("Inicia procesarNovedadCargueArchivoTransferencia(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
        SolicitudNovedadDTO solNovedadDTO = new SolicitudNovedadDTO();
            try{
                //Set datos
                solNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                solNovedadDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
                solNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_MEDIO_DE_PAGO_MASIVO_TRANSFERENCIA);
                solNovedadDTO.setObservaciones("Cargue Multiple Actualización Medio de pago a Transferencia");
                solNovedadDTO.setResultadoValidacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
                solNovedadDTO.setCargaMultiple(true);
                solNovedadDTO.setIdCargueMultipleNovedad(cargueTrasladoMedioPagoTranferencia.getIdCargueTrasladoMedioPagoTransferencia());

                DatosPersonaNovedadDTO datosPersonaNovedadDTO = new DatosPersonaNovedadDTO();
                MedioDePagoModeloDTO medioDePagoModeloDTO = new MedioDePagoModeloDTO();
        
                //TIPO IDENTIFICACION 55231 67367
                TipoIdentificacionEnum tipoIdentificacion;
                Object[] obtenerTitualarAdminSubsidioObjectResult =null;
                String tipoIdentificacionNombre = "";
                String numeroIdentificacionAdmin = "";
                Long idGrupoFamiliarObject = new Long(0);

                String[] nombreCompleto = consultarNombreTitularCambioMasivoTransferencia(
                    cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal().name(),
                    cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal()
                ).split(" ");
                datosPersonaNovedadDTO.setTipoIdentificacion(cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal());
                datosPersonaNovedadDTO.setTipoIdentificacionTrabajador(cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal());
                datosPersonaNovedadDTO.setNumeroIdentificacion(cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal());
                datosPersonaNovedadDTO.setNumeroIdentificacionTrabajador(cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal());
                datosPersonaNovedadDTO.setPrimerNombreTrabajador((nombreCompleto[0] == null || nombreCompleto[0] == "" ) ? "" : nombreCompleto[0]);
                datosPersonaNovedadDTO.setSegundoNombreTrabajador((nombreCompleto[1] == null || nombreCompleto[1] == "" ) ? "" : nombreCompleto[1]);
                datosPersonaNovedadDTO.setPrimerApellidoTrabajador((nombreCompleto[2] == null || nombreCompleto[2] == "" ) ? "" : nombreCompleto[2]);
                datosPersonaNovedadDTO.setSegundoApellidoTrabajador((nombreCompleto[3] == null || nombreCompleto[3] == "" ) ? "" : nombreCompleto[3]);
                PersonaModeloDTO personaModeloDTO1 = new PersonaModeloDTO();            
                personaModeloDTO1.setTipoIdentificacion(cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal());
                personaModeloDTO1.setNumeroIdentificacion(cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal());
                medioDePagoModeloDTO.setAdmonSubsidio(personaModeloDTO1);
                medioDePagoModeloDTO.setPersona(personaModeloDTO1);
                medioDePagoModeloDTO.setTipoMedioDePago(TipoMedioDePagoEnum.TRANSFERENCIA);
                medioDePagoModeloDTO.setSitioPago(parametroSitioPagoConfirmacionAbonos()); //xxx
               	medioDePagoModeloDTO.setNumeroCuenta(cargueTrasladoMedioPagoTranferencia.getNumeroCuenta().toString());
                medioDePagoModeloDTO.setTipoCuenta(calcularTipoCuenta(cargueTrasladoMedioPagoTranferencia.getTipoCuenta()));
                medioDePagoModeloDTO.setCodigoBanco(cargueTrasladoMedioPagoTranferencia.getCodigoBanco().toString());
                medioDePagoModeloDTO.setTipoIdentificacionTitular(cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal());
                medioDePagoModeloDTO.setNumeroIdentificacionTitular(cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal());
                medioDePagoModeloDTO.setIdBanco(consultarIdBancoTrasladoMasivoTransferencia(cargueTrasladoMedioPagoTranferencia.getCodigoBanco()));
                medioDePagoModeloDTO.setAfiliadoEsAdministradorSubsidio(Boolean.TRUE);
                medioDePagoModeloDTO.setNombreTitularCuenta(
                    consultarNombreTitularCambioMasivoTransferencia(
                        cargueTrasladoMedioPagoTranferencia.getTipoIdentificacionAfiliadoPrincipal().name(),
                        cargueTrasladoMedioPagoTranferencia.getNumeroIdentificacionAfiliadoPrincipal()
                    )
                );
                datosPersonaNovedadDTO.setMedioDePagoModeloDTO(medioDePagoModeloDTO);
                //datosPersonaNovedadDTO.setIdGrupoFamiliar(idGrupoFamiliarObject);

                System.out.println("Impresion de los datos persona novedad");
                System.out.println(datosPersonaNovedadDTO.toString());
                
                solNovedadDTO.setDatosPersona(datosPersonaNovedadDTO);
                solNovedadDTO = radicarSolicitudNovedadActualizacionesMasivas(solNovedadDTO, userDTO);

            if(solNovedadDTO.getResultadoValidacion() != null){
                if (solNovedadDTO.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)) {
                    enviarComunicadoMasivoNovedades(solNovedadDTO, userDTO);//Revisar si aplica el envío de notificación.
                }
                
            }
            // Aqui cuando no fallo

            }catch (Exception e){
                // Aqui cuando fallo

                
            } 
        
        

        logger.debug("Finaliza procesarNovedadCargueArchivoTransferencia(" + tipoArchivo + ", List<InformacionActualizacionNovedadDTO>, UserDTO)");
    }

    private TipoCuentaEnum calcularTipoCuenta(Long tipoCuenta)throws Exception {
        logger.info("Inicia private TipoCuentaEnum calcularTipoCuenta(Long "+tipoCuenta+")");
        switch(tipoCuenta.intValue()){
            case 1 :
                return TipoCuentaEnum.AHORROS;
            case 2 :
                return TipoCuentaEnum.CORRIENTE;
            case 3 :
                return TipoCuentaEnum.DAVIPLATA;
            default :
                throw new Exception("Tipo de cuenta no válido");
        }
    }

    private Long consultarIdBancoTrasladoMasivoTransferencia(Long codigoBanco){
        return (Long) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANCO_CODIGOPILA).setParameter("codigoBanco",codigoBanco).getSingleResult();
    }

    private Long registrarConsolaEstadoProcesosMasivos(ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        logger.info("Entra a registrar procesos");
        RegistrarCargueProcesoMasivo registroConsolaProceso = new RegistrarCargueProcesoMasivo(consolaEstadoProcesoDTO);
        registroConsolaProceso.execute();
        return registroConsolaProceso.getResult();
    }

    private void actualizarProcesoConsolaEstado(Long idConsolaEstadoProcesoMasivo, ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO) {
        ActualizarProcesoConsolaEstado actualizacionProceso = new ActualizarProcesoConsolaEstado(idConsolaEstadoProcesoMasivo,
        consolaEstadoProcesoDTO);
        actualizacionProceso.execute();
    }

    private String consultarNombreTitularCambioMasivoTransferencia(String tipoIdentificacionTitular,String numeroIdentificacionTitular){
        try{
            return (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOMBRE_PERSONA_CAMBIOMASIVO_TRASFERENCIA)
                    .setParameter("tipoIdentificacionTitular",tipoIdentificacionTitular)
                    .setParameter("numeroIdentificacionTitular",numeroIdentificacionTitular)
                    .getSingleResult();
        }catch(Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RespuestaValidacionArchivoDTO validarNombreArchivoCertificadosMasivos(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO) {
        logger.info("Inicia validarArchivoCertificadosMasivos");
        RespuestaValidacionArchivoDTO solNovedadDTO = new RespuestaValidacionArchivoDTO();

        // InformacionArchivoDTO archivo = new InformacionArchivoDTO();
        //archivo.setFileName("CCPCC_F21202201101_NOPAGOSEXTRA.txt");
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
        logger.info("validarArchivoCertificadosMasivos: nombre archivo a validar: " + archivo.getFileName());

        Integer validacionName = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.VALIDACION_NAME_ARCHIVO_CONSOLA)
                .setParameter("nombrearchivo", archivo.getFileName())
                .getSingleResult();

       // logger.info("Respesta validacionName: " + validacionName);

        String[] arrOfStr = archivo.getFileName().split("_");
        List<String> errores = new ArrayList<>();
        String prefijoCaja = "";
        String nombrePrefijo = "";
        String consecutivo = "";
        String fechaGeneracion = "";

        //VALIDACION #1: Archivo ya cargado en el sistema. 
        if (validacionName != null) {
            if (validacionName >= 1) {
                logger.info("Val 1: el archivo ya ha sido cargado en el sistema");
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                errores.add("El archivo que desea cargar ya existe en el sistema. Por favor cargar un archivo que no haya sido procesado previamente.");
                solNovedadDTO.setStatus("KO");
            } else {
                logger.info("tamaño nombre: " + arrOfStr.length);
                if (arrOfStr.length == 4) {
                    //se arama el consecutivo 
                    prefijoCaja = arrOfStr[0];
                    nombrePrefijo  = arrOfStr[1];
                    consecutivo = arrOfStr[2];
                    fechaGeneracion = arrOfStr[3].replace(".txt","");
                } else {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("El nombre del archivo supera los grupos de campos permitidos. Recuerde; prefijoCaja__prefijo_consecutivo_fecha");
                    solNovedadDTO.setStatus("KO");
                }

             //   printJsonMessage(arrOfStr, "arrOfStr");

                if (nombrePrefijo.length() == 0 || nombrePrefijo.length() > 13 || nombrePrefijo.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el prefijo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
                if (fechaGeneracion.length() > 8 || fechaGeneracion.equals("")) {
                    logger.warn("esta es la fecha"+fechaGeneracion);
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en la fecha del archivo.");
                    solNovedadDTO.setStatus("KO");
                }
                if (prefijoCaja.length() == 0 || prefijoCaja.length() > 6 || prefijoCaja.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el prefijo de la caja del archivo");
                    solNovedadDTO.setStatus("KO");
                }
                if (consecutivo.length() > 2 || consecutivo.equals("")) {
                    solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                    errores.add("Se encontró una inconsistencia en el consecutivo del archivo");
                    solNovedadDTO.setStatus("KO");
                }
            }

            if (errores.size() == 0) {
                solNovedadDTO.setMensaje("La validación sobre el nombre del archivo no ha sido superada.");
                solNovedadDTO.setMensaje("El archivo esta OK.");
                solNovedadDTO.setStatus("OK");
            } else {
                solNovedadDTO.setListError(errores);
            }

        } else {
            solNovedadDTO.setMensaje("Error, al validar la existencia del archivo en el sistema.");
            solNovedadDTO.setStatus("KO");
        }

        return solNovedadDTO;
    }

    @Override
    @Asynchronous
    public void validarArchivoCertificadosMasivos(Long idEmpleador,TipoArchivoRespuestaEnum tipoArchivo,CargueArchivoActualizacionDTO cargue,UserDTO userDTO)throws IOException{
        logger.info("Inicia validarArchivoCertificadosMasivos(TipoArchivoRespuestaEnum tipoArchivo, CargueArchivoActualizacionDTO cargue, UserDTO userDTO)");

        ConsolaEstadoProcesoDTO consolaEstadoCargueProcesos = new ConsolaEstadoProcesoDTO();
        consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargueProcesos.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("0"));
        consolaEstadoCargueProcesos.setProceso(TipoProcesosMasivosEnum.CERTIFICADOS_AFILIACION_MASIVOS);        

        Long idConsolaEstadoProceso = registrarConsolaEstadoProcesosMasivos(consolaEstadoCargueProcesos);

        consolaEstadoCargueProcesos.setIdConsolaEstadoProcesoMasivo(idConsolaEstadoProceso);


        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

        InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());

        EstadoCargueMasivoEnum estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
        EstadoCargueArchivoActualizacionEnum estadoCargue = EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO;

        List<Map<String,Object>> mapaInconsistencias = new ArrayList<Map<String,Object>>();

        ControlCertificadosMasivos cetificadoMasivo = new ControlCertificadosMasivos();

        // logger.warn("nombre del archivo cargado "+archivo.getFileName());

        cargue.setNombreArchivo(archivo.getFileName());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_ARCHIVO_CERTIFICADOS_AFILIACION_MASIVOS);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());

        registrarConsolaEstado(consolaEstadoCargue);
        VerificarEstructuraArchivoCertificadosMasivos verificarArchivo = new VerificarEstructuraArchivoCertificadosMasivos(tipoArchivo, archivo,idEmpleador);
        verificarArchivo.execute();
        logger.warn("ya verificamos la estructura");
        ResultadoValidacionArchivoDTO resultDTO = verificarArchivo.getResult();
        resultDTO.setIdCargue(idCargue);
        logger.info("termina VerificarEstructuraArchvoNovedadMasivaTransferencia ");
        logger.warn(resultDTO.toString());
        if (EstadoCargaMultipleEnum.CANCELADO.equals(resultDTO.getEstadoCargue())
                || (resultDTO.getCargueCertificadosMasivos() == null || resultDTO.getCargueCertificadosMasivos().isEmpty())) {
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            logger.warn("Estado de cargue CANCELADO,ANULADO o getCargueCertificadosMasivos Vacio");
            logger.warn(resultDTO.getEstadoCargue());
            logger.warn(resultDTO.getCargueCertificadosMasivos() == null);


            if(resultDTO.getResultadoHallazgosValidacionArchivoDTO().size()>0){

                logger.warn("todos los registros tan malitos");

                for(ResultadoHallazgosValidacionArchivoDTO hallazgo : resultDTO.getResultadoHallazgosValidacionArchivoDTO()){
                    Map<String,Object> mapa = new HashMap<>();
                    mapa.put("tipoDocumento",hallazgo.getTipoDocumento());
                    mapa.put("numeroDocumento",hallazgo.getNumeroDocumento());
                    mapa.put("error",hallazgo.getError());
                    mapaInconsistencias.add(mapa);
                }
                File archivoExcel = new File("Inconsistencias.xlsx");
                crearExcelRegistrosInconsistentesCargue(archivoExcel,mapaInconsistencias);
                
                cetificadoMasivo.setIdArchivoECM(crearArchivoExcel(Files.readAllBytes(archivoExcel.toPath())));
                cetificadoMasivo.setTipoCertificado(TipoCertificadoMasivoEnum.CERTIFICADO_AFILIACION);
                cetificadoMasivo.setIdEmpleador(idEmpleador);
                cetificadoMasivo.setNombreArchivo("Inconsistencias"+idEmpleador+".xlsx");
                cetificadoMasivo.setNombreCargue(archivo.getFileName());
                entityManager.merge(cetificadoMasivo);
                entityManager.flush();

                consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
                consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);
                
            }else{
                consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
                consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);
            }
        } else {
            logger.warn("entra else para procesamiento registros guenos");
            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("20"));
            actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);

            List<CargueTrasladoMedioPagoTranferencia> informacionNovedades = null;    

            if (CollectionUtils.isNotEmpty(resultDTO.getCargueCertificadosMasivos())) {
                logger.warn("Filtro completado.");
                
                    if(!EstadoCargaMultipleEnum.ANULADO.equals(resultDTO.getEstadoCargue()) ){
                        logger.warn("archivo con estado diferente a anulado");
                        crearCertificadosMasivos(resultDTO.getCargueCertificadosMasivos(),idEmpleador,archivo.getFileName());
                        estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                        estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;    
                    }else{
                        logger.warn("archivo con estado anulado, algun registro malo :c");
                        Response rarCreado = crearCertificadosMasivos(resultDTO.getCargueCertificadosMasivos(),idEmpleador,archivo.getFileName());

                        if(rarCreado.getStatus() == 200 || rarCreado.getStatus() == 204){
                            logger.warn("la respuesta del servicio fue 200 o 204");
                            try{

                                logger.warn("Inicia try para manipular rar");
                                // InputStream inputStream = rarCreado.readEntity(InputStream.class);
                                byte[] rarBytes = consultarArchivo(idEmpleador);
                                logger.warn("Tamaño del archivo RAR recibido: " + (rarBytes != null ? rarBytes.length : "null"));
                                File zipFile = new File("temp.zip");
                                Files.write(zipFile.toPath(),rarBytes);
                                File carpetaExtraidaZip = new File("Certificados_generados");
                                carpetaExtraidaZip.mkdir();
                                extraerZip(zipFile,carpetaExtraidaZip);
                                for(ResultadoHallazgosValidacionArchivoDTO hallazgo : resultDTO.getResultadoHallazgosValidacionArchivoDTO()){
                                    Map<String,Object> mapa = new HashMap<>();
                                    mapa.put("tipoDocumento",hallazgo.getTipoDocumento());
                                    mapa.put("numeroDocumento",hallazgo.getNumeroDocumento());
                                    mapa.put("error",hallazgo.getError());
                                    mapaInconsistencias.add(mapa);
                                }
                                File carpetaInconsistencias = new File("Carpeta_inconsitencias");
                                if (!carpetaInconsistencias.exists()) {
                                    carpetaInconsistencias.mkdir();
                                }
                                File excelInconsistencias = new File(carpetaInconsistencias, "Inconsistencias.xlsx");
                                crearExcelRegistrosInconsistentesCargue(excelInconsistencias,mapaInconsistencias);
                                File zipFinal = new File("Certificados_con_inconsistencias.zip");
                                comprimirVariasCarpetas(Arrays.asList(carpetaExtraidaZip, carpetaInconsistencias), zipFinal);

                                try{
                                    cetificadoMasivo = (ControlCertificadosMasivos) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_MASIVO_CERTIFICADOS).setParameter("idEmpleador",idEmpleador).getSingleResult();
                                    cetificadoMasivo.setIdArchivoECM(crearArchivoZip(Files.readAllBytes(zipFinal.toPath()),idEmpleador));
                                    cetificadoMasivo.setNombreArchivo("Certificado_afiliacion_cargue"+idEmpleador+".zip");
                                    cetificadoMasivo.setNombreCargue(archivo.getFileName());
                                    entityManager.merge(cetificadoMasivo);
                                    entityManager.flush();
                                }catch(Exception e){
                                    logger.error("no hay resultados");
                                    cetificadoMasivo.setIdArchivoECM(crearArchivoZip(Files.readAllBytes(zipFinal.toPath()),idEmpleador));
                                    cetificadoMasivo.setTipoCertificado(TipoCertificadoMasivoEnum.CERTIFICADO_AFILIACION);
                                    cetificadoMasivo.setIdEmpleador(idEmpleador);
                                    cetificadoMasivo.setNombreArchivo("Certificado_afiliacion_cargue"+idEmpleador+".zip");
                                    cetificadoMasivo.setNombreCargue(archivo.getFileName());
                                    eliminarArchivoCertificadosMasivos(cetificadoMasivo.getIdArchivoECM());
                                    entityManager.merge(cetificadoMasivo);
                                    entityManager.flush();
                                }

                                logger.warn("ya borramos el arhivo y seguimos");
                                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                                estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;        
                                consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("100"));
                                consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                                actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);
                                
                            }catch(Exception e){
                                logger.error("fallo entro catch 1");
                                e.printStackTrace();
                            }
                        }else{
                            estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                            estadoCargue = EstadoCargueArchivoActualizacionEnum.PROCESADO;        
                            consolaEstadoCargueProcesos.setGradoAvance(new BigDecimal("70"));
                            consolaEstadoCargueProcesos.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                            actualizarProcesoConsolaEstado(idConsolaEstadoProceso, consolaEstadoCargueProcesos);
                        }
                    }                
            }else {
                logger.info("empty getCargueCertificadosMasivos FIN_ERROR");
                estadoProcesoMasivo = EstadoCargueMasivoEnum.FIN_ERROR;
                estadoCargue = EstadoCargueArchivoActualizacionEnum.CON_INCONSISTENCIA_DE_ESTRUCTURA;
            }
        }
        logger.info("estado del cargue estadoCargue "+estadoCargue);
        // ultimo paso seteo estatus y demas contra la consola de cargue
        resultDTO.setEstadoCargueActualizacion(estadoCargue);
        // Registrar estado archivo
        cargue.setEstado(estadoCargue);
        cargue.setFechaProcesamiento(Calendar.getInstance().getTime());
        crearActualizarCargueArchivoActualizacion(cargue);
    
        // Se actualiza el estado en la consola
        ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
        conCargueMasivo.setCargue_id(idCargue);
        conCargueMasivo.setEstado(estadoProcesoMasivo);
        conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
        conCargueMasivo.setFileLoaded_id(resultDTO.getFileLoadedId());
        conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
        conCargueMasivo.setLstErroresArhivo(resultDTO.getResultadoHallazgosValidacionArchivoDTO());
        conCargueMasivo.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
        conCargueMasivo.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroProcesado(resultDTO.getTotalRegistro());
        conCargueMasivo.setNumRegistroValidados(resultDTO.getRegistrosValidos());
        conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_ARCHIVO_CERTIFICADOS_AFILIACION_MASIVOS);
        actualizarCargueConsolaEstado(idCargue, conCargueMasivo);
    }

    public Response crearCertificadosMasivos(List<CargueCertificadosMasivos> cargue,Long idEmpleador,String nombreArchivo){
        logger.warn("Inicia public Response crearCertificadosMasivos(List<CargueCertificadosMasivos> cargue,Long idEmpleador)");
        List<Map<String,Object>> listadeMapas = new ArrayList<>();
        cargue.forEach(c ->{
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("tipoIdentificacion", c.getTipoIdentificacion());
            mapa.put("numeroIdentificacion", c.getNumeroIdentificacion());
            listadeMapas.add(mapa); 
        });
        GenerarCertificadosMasivos generarCertificados = new GenerarCertificadosMasivos(listadeMapas,idEmpleador,"Quien corresponda",TipoCertificadoMasivoEnum.CERTIFICADO_AFILIACION,Boolean.TRUE,nombreArchivo);
            generarCertificados.execute();
        logger.warn("consumimos servicio generar ccertificados");
        return generarCertificados.getResult();
    }

    private static void crearExcelRegistrosInconsistentesCargue(File archivoExcel, List<Map<String, Object>> datos)throws IOException  {
        if (archivoExcel.isDirectory()) {
            throw new IOException("El archivo Excel no puede ser un directorio: " + archivoExcel.getAbsolutePath());
        }
    
        try (FileOutputStream fos = new FileOutputStream(archivoExcel); Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Inconsistencias");
    
            int rowIndex = 0;
            for (Map<String, Object> rowData : datos) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                for (Object value : rowData.values()) {
                    row.createCell(cellIndex++).setCellValue(value.toString());
                }
            }
            workbook.write(fos);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void extraerZip(File zipFile,File outDir)throws IOException{
        logger.warn("Inicia public static void extraerZip(File zipFile,File outDir)throws IOException");
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File nuevoArchivo = new File(outDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    nuevoArchivo.mkdirs();
                } else {
                    nuevoArchivo.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(nuevoArchivo)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
        logger.warn("Finaliza public static void extraerZip(File zipFile,File outDir)throws IOException");
    }

    private static void comprimirVariasCarpetas(List<File> carpetas, File zipSalida) throws IOException {
        logger.warn("Inicia private static void comprimirVariasCarpetas(List<File> carpetas, File zipSalida) throws IOException ");
        try (FileOutputStream fos = new FileOutputStream(zipSalida);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
    
            for (File carpeta : carpetas) {
                comprimirArchivo(carpeta, carpeta.getName(), zos);
            }
        }
        logger.warn("Finaliza private static void comprimirVariasCarpetas(List<File> carpetas, File zipSalida) throws IOException ");
    }    

    private static void comprimirArchivo(File archivo, String nombreBase, ZipOutputStream zos) throws IOException {
        logger.warn("Inicia private static void comprimirArchivo(File archivo, String nombreBase, ZipOutputStream zos) throws IOException");
        if (archivo.isDirectory()) {
            for (File archivoHijo : archivo.listFiles()) {
                comprimirArchivo(archivoHijo, nombreBase + "/" + archivoHijo.getName(), zos);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                ZipEntry zipEntry = new ZipEntry(nombreBase);
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }
        }
        logger.warn("Finaliza private static void comprimirArchivo(File archivo, String nombreBase, ZipOutputStream zos) throws IOException");
    }

    private String crearArchivoZip(byte[] archivo,Long idEmpleador){
        InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
        infoFile.setDataFile(archivo);
        infoFile.setFileType("application/zip");
        infoFile.setProcessName("Certificados masivos de afiliacion");
        infoFile.setDescription("Certificados de afiliacion masivos");
        infoFile.setDocName("Certificado_afiliacion_cargue"+idEmpleador+".zip");
        infoFile.setFileName("Certificado_afiliacion_"+idEmpleador+".zip");
        return almacenarArchivo(infoFile);
    }

    private String crearArchivoExcel(byte[] archivo){
        InformacionArchivoDTO infoFile = new InformacionArchivoDTO();
        infoFile.setDataFile(archivo);
        infoFile.setFileType("application/octet-stream");
        infoFile.setProcessName("Certificados masivos de afiliacion");
        infoFile.setDescription("Certificados de afiliacion masivos");
        infoFile.setDocName("Inconsistencias.xlsx");
        infoFile.setFileName("Inconsistencias.xlsx");
        return almacenarArchivo(infoFile);
    }

    private String almacenarArchivo(InformacionArchivoDTO infoFile) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(infoFile);
        almacenarArchivo.execute();
        InformacionArchivoDTO archivo = almacenarArchivo.getResult();
        StringBuilder idECM = new StringBuilder();
        idECM.append(archivo.getIdentificadorDocumento());
        idECM.append("_");
        idECM.append(archivo.getVersionDocumento());
        return idECM.toString();
    }

    private byte[] consultarArchivo(Long idEmpleador){
        InformacionArchivoDTO archivo = null;
        try {
            String idECM = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ULTIMO_ARCHIVO_MASIVO_CERTIICADOS)
                                                .setParameter("idEmpleador", idEmpleador)
                                                .getSingleResult();
            String[] id = idECM.split("_");
            ObtenerArchivo consultarArchivo = new ObtenerArchivo(id[0]);
            consultarArchivo.execute();
            archivo = consultarArchivo.getResult();
            return archivo.getDataFile();
    
        } catch (Exception e) {
            logger.error("Error al consultar el archivo: " + e.getMessage(), e);
            return null;
        }
    }

    @Asynchronous
    private void eliminarArchivoCertificadosMasivos(String idECM){
        EliminarArchivo eliminarArchivo = new EliminarArchivo(idECM);
        eliminarArchivo.execute();
    }


}
