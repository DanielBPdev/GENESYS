package com.asopagos.cartera.composite.service.factories;

import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.cartera.clients.*;
import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.cartera.composite.util.ParametrizarDestinatarios;
import com.asopagos.cartera.dto.AportanteAccionCobroDTO;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.cartera.dto.DatosIdEntidadComunicadoDTO;
import com.asopagos.comunicados.clients.GuardarObtenerInfoArchivoComunicado;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.empleadores.clients.GuardarDatosEmpleador;
import com.asopagos.entidades.ccf.cartera.DocumentoCartera;
import com.asopagos.entidades.ccf.cartera.TiempoProcesoCartera;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.*;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ExpulsionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnvioExitosoComunicados;
import com.asopagos.notificaciones.archivos.composite.clients.EnvioExitosoComunicadosCartera;
import com.asopagos.notificaciones.clients.EnviarMultiplesCorreosPorConexion;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.asopagos.cartera.clients.GuardarRelacionComunicadoBitacoraCartera;
import com.asopagos.dto.modelo.GuardarRelacionComunicadoBitacoraCarteraDTO;
import com.asopagos.dto.modelo.ResultadoEnvioComunicadoCarteraDTO;

/**
 * <b>Descripción: </b> Clase que representa los atributos y acciones generales
 * relacionados a la asignación de solicitudes de acciones de cobro. Ver
 * <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> Req-223
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 * Benavides</a>
 */
public abstract class AsignacionSolicitudGestionCobro {

    List<Long> idPersonasAProcesar;

    /**
     * Tipo de acción de cobro
     */
    private TipoAccionCobroEnum accionCobro;

    /**
     * Tipo de parametrización
     */
    private TipoParametrizacionGestionCobroEnum tipoParametrizacion;

    /**
     * Plantilla de los comunicados respectivos a la acción de cobro
     */
    private List<EtiquetaPlantillaComunicadoEnum> plantillasComunicado;

    /**
     * Tipo de transacción medio electrónico
     */
    private TipoTransaccionEnum tipoTransaccionElectronica;

    /**
     * Tipo de transacción medio físico
     */
    private TipoTransaccionEnum tipoTransaccionFisica;

    /**
     * Información del usuario
     */
    private UserDTO userDTO;

    /**
     * Documento asociado al comunicado generado y almacenado en el ECM, para una acción de cobro particular
     */
    private DocumentoCarteraModeloDTO documentoCarteraDTO;

    /**
     * Lista de usuarios con perfil Back-Empleador
     */
    private List<UsuarioDTO> usuariosBackEmpleador;

    /**
     * Lista de usuarios con perfil Back-Persona
     */
    private List<UsuarioDTO> usuariosBackPersona;

    /**
     * Lista de usuarios con perfil Analista-Aportes
     */
    private List<UsuarioDTO> usuariosAnalistaAportes;

    /**
     * Parametrización de la acción de cobro
     */
    protected Map<String, Object> parametrizacion;

    /**
     * Logger
     */
    private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobro.class);

    /**
     * Constructor
     *
     * @param userDTO                     Información del usuario que realiza la asignación
     * @param radicarSolicitudElectronica Marca que indica si debe radicarse una solicitud de gestión de
     *                                    cobro electrónico
     */
    public AsignacionSolicitudGestionCobro(UserDTO userDTO) {
        this.userDTO = userDTO;
        this.plantillasComunicado = new ArrayList<>();
        this.usuariosBackEmpleador = obtenerMiembrosGrupo(ConstanteCartera.BAC_ACT_EMP, userDTO.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);
        this.usuariosBackPersona = obtenerMiembrosGrupo(ConstanteCartera.BAC_ACT_PER, userDTO.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);
        this.usuariosAnalistaAportes = obtenerMiembrosGrupo(ConstanteCartera.ANA_CAR_APO, userDTO.getSedeCajaCompensacion(),
            EstadoUsuarioEnum.ACTIVO);
    }

    /**
     * Método que define la implementación para asignar los comunicados
     * respectivos a cada acción de cobro
     */
    public abstract void asignarComunicados();

    /**
     * Método que consulta la parametrización para la acción de cobro a asignar
     *
     * @param lista Lista de parametrización
     */
    public abstract void consultarParametrizacion(List<Object> lista);

    /**
     * Método que genera y envía los comunicados asociados a una acción de cobro
     * y, de ser necesario, crea solicitudes de gestión de cobro (físico y/o
     * electrónico)
     */
    public void asignar() {

        StringBuilder info = new StringBuilder();
        info.append(" @acción de cobro: ");
        info.append(accionCobro.name());
        info.append(" @tipo parametrizacion: ");
        info.append(tipoParametrizacion);
        info.append(" @parametrizacion: ");
        info.append(parametrizacion);
        info.append(" @timeId: ");
        info.append(System.nanoTime());

        logger.info("Inicia método asignar para la " + info.toString());

        Calendar fechaInicio = Calendar.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<AportanteAccionCobroDTO> listaAportanteAccionCobroDTO;
            // Ejecuta la asignación de acciones de cobro desde el procedimiento almacenado
            if (TipoAccionCobroEnum.H2_C6_EX.equals(accionCobro) || TipoAccionCobroEnum.F1_C6_EX.equals(accionCobro)) {
                listaAportanteAccionCobroDTO = idPersonasAProcesar != null ? obtenerAportantesParaExpulsionPorIds(accionCobro, idPersonasAProcesar) : enviarComunicadoExpulsionH2C2ToF1C6(accionCobro);
            } else if (TipoAccionCobroEnum.H2_C6.equals(accionCobro) || TipoAccionCobroEnum.F1_C6.equals(accionCobro)) {
                listaAportanteAccionCobroDTO = enviarComunicadoH2C2ToF1C6(accionCobro);
            } else {
                listaAportanteAccionCobroDTO = asignarAccionCobro(accionCobro);
            }
            Boolean esFisico = Boolean.TRUE;

            info.append(" @aportantes: ");
            info.append(listaAportanteAccionCobroDTO.size());
            logger.info("Inicia método asignar para la " + info.toString());

            List<AportanteAccionCobroDTO> listaAportanteAccionCobroFisicoDTO = new ArrayList<>();
            //List<AportanteAccionCobroDTO> listaAportantesElectronico = new ArrayList<>();
            //logger.info("listaAportanteAccionCobroDTO:::" + listaAportanteAccionCobroDTO);
            //logger.info("tipoParametrizacion:::" + objectMapper.writeValueAsString(tipoParametrizacion));
            //logger.debug("listaAportanteAccionCobroDTO::: N" + listaAportanteAccionCobroDTO);
            //logger.debug("tipoParametrizacion::: N" + objectMapper.writeValueAsString(tipoParametrizacion));
            if (!listaAportanteAccionCobroDTO.isEmpty()) {
                // Consulta la parametrización
                if (tipoParametrizacion != null) {
                    logger.info("tipoParametrizacion::tipoParametrizacion:::" + tipoParametrizacion);
                    List<Object> lista = consultarParametrizacionGestionCobro(tipoParametrizacion);
                    consultarParametrizacion(lista);
                    logger.info("tipoParametrizacion::consultarParametrizacionGestionCobro:::" + objectMapper.writeValueAsString(lista));
                    logger.debug("tipoParametrizacion::consultarParametrizacionGestionCobro::: N" + objectMapper.writeValueAsString(lista));
                    if (parametrizacion != null) {
                        logger.info("parametrizacion entra:::" + objectMapper.writeValueAsString(parametrizacion));
                        esFisico = MetodoEnvioComunicadoEnum.FISICO.name().equals(parametrizacion.get(ConstanteCartera.METODO_ENVIO_COMUNICADO).toString());
                    }
                }
                if (TipoAccionCobroEnum.F1_C6.equals(accionCobro) || TipoAccionCobroEnum.F1_C6_EX.equals(accionCobro)) {
                    accionCobro = TipoAccionCobroEnum.F1;
                    esFisico = Boolean.FALSE;
                } else if (TipoAccionCobroEnum.H2_C6.equals(accionCobro) || TipoAccionCobroEnum.H2_C6_EX.equals(accionCobro)) {
                    accionCobro = TipoAccionCobroEnum.H2;
                    esFisico = Boolean.FALSE;
                }
                // Asignación del comunicado a ser enviado en esta acción de cobro
                asignarComunicados();
                
                if (TipoAccionCobroEnum.E2.equals(accionCobro)) {
                    esFisico = Boolean.TRUE;
                } else if (TipoAccionCobroEnum.LC4A.equals(accionCobro) || TipoAccionCobroEnum.LC5A.equals(accionCobro)) {
                    LineaCobroPersonaModeloDTO lineaCobroPersona = consultarLineaCobroPersona(accionCobro.getLineaCobro());
                    esFisico = MetodoEnvioComunicadoEnum.FISICO.name().equals(lineaCobroPersona.getMetodoEnvioComunicado().toString());
                }
            }

            List<EtiquetaPlantillaComunicadoEnum> plantila = new ArrayList<>();
            //plantila.add(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR);
            for (EtiquetaPlantillaComunicadoEnum plantilla : plantillasComunicado) {
                    logger.info("Etiqueta de asignar comunicados: " + plantilla);
                    plantila.add(plantilla);
            }
            logger.info("Inicia método asignar para la " + info.toString());
            info.append(" @esFisico: ");
            info.append(esFisico);
            logger.info(" fisico::" + objectMapper.writeValueAsString(esFisico));

            if (esFisico) {
                logger.info("**__**Procesamiento para comunicados físicos");
                listaAportanteAccionCobroFisicoDTO.addAll(listaAportanteAccionCobroDTO);
            } else {
                Set<String> identificacionesUnicas = new HashSet<>();

                listaAportanteAccionCobroDTO.parallelStream().forEach(aportanteAccionCobroDTO -> {
                      logger.info("**__** Ciclo APORTANTE autoriza envio:: "+aportanteAccionCobroDTO.getAutorizaEnvioCorreo());
                      logger.info("**__** Ciclo APORTANTE autoriza id:: "+aportanteAccionCobroDTO.getIdCartera());
                      logger.info("**__** Ciclo APORTANTE autoriza tipo Aportante:: "+aportanteAccionCobroDTO.getTipoAportante());
                    if (aportanteAccionCobroDTO.getAutorizaEnvioCorreo()) {
                        // Si la identificación se agregó al conjunto (es decir, es única), procesa el aportante electrónicamente.
                        if (identificacionesUnicas.add(aportanteAccionCobroDTO.getNumeroIdentificacion())) {
                            logger.info("Entro al IF ------------- de aportante por la cedula");
                             for (EtiquetaPlantillaComunicadoEnum plantillaComunicado : plantila) {
                                   logger.info("**__** procesarAportanteElectronico etiqueta :: "+plantillaComunicado);
                             }

                            if(aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE) || aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO) ){
                                plantillasComunicado = asignacionComunicadosP_I(aportanteAccionCobroDTO.getTipoAportante(),accionCobro);
                                logger.info("Entro al IF ------------- de aportante por la cedula INDEPENDIENTE o PENSIONADO");
                                logger.info("**__** procesarAportanteElectronico etiqueta INDEPENDIENTE o PENSIONADO:: "+plantillasComunicado);
                                for (EtiquetaPlantillaComunicadoEnum plantilla : plantillasComunicado) {
                                    logger.info("Etiqueta del FOR DE INDEPENDIENTES Y PENSIONADOS: " + plantilla);
                                    logger.info("**__** Ciclo APORTANTE autoriza tipo Aportante numero de identificación:: "+aportanteAccionCobroDTO.getNumeroIdentificacion());
                                    procesarAportanteElectronico(aportanteAccionCobroDTO, plantillasComunicado);

                                }
                            }else{
                            procesarAportanteElectronico(aportanteAccionCobroDTO, plantila);
                            }
                        }
                    } else {
                        // Agrega a la lista de envío físico si no autoriza el envío por correo.
                        listaAportanteAccionCobroFisicoDTO.add(aportanteAccionCobroDTO);
                    }
                });
            }

            logger.debug("Finiliza caer en for plantilla electronica->");
            logger.info("Esta es la lista antes " + info.toString());
            info.append(" @listaAportanteAccionCobroFisicoDTO: ");
            info.append(listaAportanteAccionCobroFisicoDTO.size());
            logger.info("Inicia método asignar para la metodo fisico " + info.toString());
            //AtomicReference<Integer> count = new AtomicReference<>(0);
            //el bloque synchronized asegura que la lista listaDetalleGC no se modifique simultáneamente por diferentes hilos, lo que es crucial dado que estás modificando esta lista dentro del forEach de un stream paralelo.
            AtomicInteger count = new AtomicInteger(0);
            if (!listaAportanteAccionCobroFisicoDTO.isEmpty()) {
                logger.info("listaAportanteAccionCobroFisicoDTO ::  " + objectMapper.writeValueAsString(listaAportanteAccionCobroFisicoDTO));
                // Crea la solicitud de gestión de cobro físico
                SolicitudGestionCobroFisicoModeloDTO solicitudDTO = asignarSolicitudFisica();

                logger.debug("tipoTransaccionFisica" + objectMapper.writeValueAsString(tipoTransaccionFisica));
                logger.info("tipoTransaccionFisica" + objectMapper.writeValueAsString(tipoTransaccionFisica));

                List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleGC = new ArrayList<>();

                // Combina la generación/envío de comunicados y el registro de detalles en un solo bucle
                //for (AportanteAccionCobroDTO e : listaAportanteAccionCobroFisicoDTO) {
                listaAportanteAccionCobroFisicoDTO.parallelStream().forEach(e -> {

                    logger.debug("accionCobro::  " + accionCobro);
                    logger.info("accionCobro::  " + accionCobro);
                    if (!TipoAccionCobroEnum.E2.equals(accionCobro)) {
                        // Genera y guarda el comunicado
                        if (TipoAccionCobroEnum.D2.equals(accionCobro)) {
                            Boolean anexoLiquidacion = consultaParametrizacionAnexoLiquidacion(accionCobro);
                            if (anexoLiquidacion) {
                                enviarComunicadoAsignacion(e, plantila, tipoTransaccionFisica);
                            }
                        } else {
                            logger.info(e.getTipoAportante());
                             if(e.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE) || e.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO) ){
                                   plantillasComunicado = asignacionComunicadosP_I(e.getTipoAportante(),accionCobro);
                                   logger.info("hiolllll");
                                   logger.info(e.getTipoAportante());
                                   logger.info(plantillasComunicado.toString());
                             }
                            for (EtiquetaPlantillaComunicadoEnum plantillaComunicado : plantillasComunicado) {
                                logger.info("!=!=! Inicio imprime  plantillaComunicado:=!=!=! " + plantillaComunicado);
                                AsignacionTemporalComunicado asignacionTemporal = almacenarComunicado(plantillaComunicado, tipoTransaccionFisica, e);
                                logger.info("esta es la plantilla comunicado" + plantillaComunicado);
                                logger.info("este es el tipoTransaccionFisica " + tipoTransaccionFisica);
                                logger.info("esto equivale e " + e);
                                List<DocumentoSoporteModeloDTO> documentosSoporte = new ArrayList<>();
                                documentosSoporte.add(asignacionTemporal.getDocumentoSoporteDTO());
                                almacenarBitacora(e, tipoTransaccionFisica, documentosSoporte);
                            }
                        }
                    }

                    // Guarda el detalle de la solicitud física
                    synchronized (listaDetalleGC) {
                        // Guarda el detalle de la solicitud física
                        DetalleSolicitudGestionCobroModeloDTO detalleGC = guardarDetalleSolicitudGestionCobro(e, solicitudDTO);
                        listaDetalleGC.add(detalleGC);
                        // Guardar los detalles en lotes

                        if (listaDetalleGC.size() == 1000) {
                            count.addAndGet(listaDetalleGC.size());
                            guardarListaDetalleSolicitudGestionCobro(listaDetalleGC, solicitudDTO.getIdSolicitud());
                            listaDetalleGC.clear();
                        }
                    }
                });

                // Guardar cualquier detalle restante que no haya alcanzado el tamaño del lote
                // Después de completar el stream, verifica si hay elementos restantes en listaDetalleGC
                synchronized (listaDetalleGC) {
                    if (!listaDetalleGC.isEmpty()) {
                        count.addAndGet(listaDetalleGC.size());
                        guardarListaDetalleSolicitudGestionCobro(listaDetalleGC, solicitudDTO.getIdSolicitud());
                    }
                }
            }

            TiempoProcesoCartera procesoCartera = new TiempoProcesoCartera();
            procesoCartera.setNombreProceso("AsignacionSolicitudGestionCobro");
            procesoCartera.setFechaInicio(fechaInicio.getTime());
            procesoCartera.setFechaFin(Calendar.getInstance().getTime());
            procesoCartera.setRegistros(Long.valueOf(listaAportanteAccionCobroDTO.size()));
            procesoCartera.setMensaje(info.toString());
            guardarTiempoProcesoCartera(procesoCartera);

            info.append(" @cantidadDetalleSolicitudes: ");
            info.append(count);
            logger.debug("Datos para asignar para la" + info);
        } catch (Exception e) {
            logger.error("Error en método asignar para la acción de cobro " + accionCobro.name(), e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug("Finaliza método asignar para la acción de cobro " + accionCobro.name());
    }
    private  List<EtiquetaPlantillaComunicadoEnum> asignacionComunicadosP_I(TipoSolicitanteMovimientoAporteEnum topoAportante,TipoAccionCobroEnum accionCobro){
        List<EtiquetaPlantillaComunicadoEnum> plantila = new ArrayList<>();
        try {
            logger.debug("Inicio de método consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro)");
            logger.debug("Inicio de método asignacionComunicadosP_I con tipoAportante: " + topoAportante + " y accionCobro: " + accionCobro);
            switch (accionCobro) {

                case A1:
                if (topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
                    List<Object> accionCobroA1 = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
                    for (Object object : accionCobroA1) {
                        if (object instanceof LinkedHashMap) {
                            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
                            AccionCobroAModeloDTO accion = new AccionCobroAModeloDTO();

                            // Convertir el String a MetodoAccionCobroEnum
                            String metodoStr = (String) map.get("metodo");
                            MetodoAccionCobroEnum metodo = MetodoAccionCobroEnum.valueOf(metodoStr);
                            accion.setMetodo(metodo);

                            accion.setSuspensionAutomatica((Boolean) map.get("suspensionAutomatica"));

                            if (MetodoAccionCobroEnum.METODO_1.equals(accion.getMetodo())) {
                                plantila.add(accion.getSuspensionAutomatica()
                                    ? EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO_PER
                                    : EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG_PER);
                            }
                        }
                    }
                } else if (topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)) {
                    List<Object> accionCobroA1 = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
                    for (Object object : accionCobroA1) {
                        if (object instanceof LinkedHashMap) {
                            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
                            AccionCobroAModeloDTO accion = new AccionCobroAModeloDTO();
                            String metodoStr = (String) map.get("metodo");
                            MetodoAccionCobroEnum metodo = MetodoAccionCobroEnum.valueOf(metodoStr);
                            accion.setMetodo(metodo);

                            accion.setSuspensionAutomatica((Boolean) map.get("suspensionAutomatica"));

                            if (MetodoAccionCobroEnum.METODO_1.equals(accion.getMetodo())) {
                                plantila.add(accion.getSuspensionAutomatica()
                                    ? EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO_PER
                                    : EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG_PER);
                            }
                        }
                    }
                }
                break;

                case A2:
                    logger.info("------------Entre a caso A2------------");
                    List<Object> accionCobroA2 = consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum.ACCION_COBRO_A);
                    List<Map<String, Object>> mapList2 = accionCobroA2.stream()
                            .map(obj -> (Map<String, Object>) obj) // Cast de Object a Map<String, Object>
                            .collect(Collectors.toList());
                    logger.info("Tipo de aportante:   " + topoAportante);
                    logger.info("Cantidad de elementos en parametrización:" + mapList2.size());


                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        for (Map<String, Object> object : mapList2) {
                            logger.info("Evaluando objeto: " + object);
                            logger.info("Método encontrado: " + object.get("metodo"));
                            logger.info("Suspensión automática: " + object.get("suspensionAutomatica"));
                            logger.info("Método esperado: " + MetodoAccionCobroEnum.METODO_2.name());
                            if (MetodoAccionCobroEnum.METODO_2.name().equals(object.get("metodo"))) {
                                EtiquetaPlantillaComunicadoEnum etiqueta = object.get("suspensionAutomatica") != null
                                    ? EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO_PER
                                    : EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG_PER;
                                plantila.add(etiqueta);
                                logger.info("Etiqueta agregada (INDEPENDIENTE): " + etiqueta);
                            }
                        }
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        for (Map<String, Object> object : mapList2) {
                            logger.info("Evaluando objeto: " + object);
                            logger.info("Método encontrado: " + object.get("metodo"));
                            logger.info("Suspensión automática: " + object.get("suspensionAutomatica"));
                            if (MetodoAccionCobroEnum.METODO_1.name().equals(object.get("metodo"))) {
                                EtiquetaPlantillaComunicadoEnum etiqueta = object.get("suspensionAutomatica") != null
                                    ? EtiquetaPlantillaComunicadoEnum.NTF_NO_REC_APO_PER
                                    : EtiquetaPlantillaComunicadoEnum.SUS_NTF_NO_PAG_PER;
                                plantila.add(etiqueta);
                                logger.info("Etiqueta agregada (PENSIONADO): " + etiqueta);
                            }
                        }
                    }
                    break;

                case B1:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.AVI_INC_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.AVI_INC_PER);
                    }
                    break;

                case B2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.AVI_INC_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.AVI_INC_PER);
                    }
                    break;

                case C1:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR);
                    }
                    break;

                case C2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CIT_NTF_PER_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CIT_NTF_PER_PER);
                    }
                    break;

                case D1:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS_PER);
                    }
                    break;

                case D2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.NTF_AVI_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.NTF_AVI_PER);
                    }
                    break;

                case E1:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS_PER);
                    }
                    break;


                case F1:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CAR_EXP_INP);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CAR_EXP_PEN);
                    }
                    break;

                case F2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.PRI_AVI_COB_PRS_PER);
                    }
                    break;
                case G2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS_PER);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.SEG_AVI_COB_PRS_PER);
                    }
                    break;
                case H2:
                    if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CAR_EXP_INP);
                    }else if(topoAportante.equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO)){
                        plantila.add(EtiquetaPlantillaComunicadoEnum.CAR_EXP_PEN);
                    }

                    break;
                default:
                    return null;
                }
                return plantila;
        }  catch (Exception e) {
            logger.error("Ocurrió un error en consultarEtiquetaPorAccion(TipoAccionCobroEnum accionCobro)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }


    private void procesarAportanteElectronico(AportanteAccionCobroDTO aportanteAccionCobroDTO, List<EtiquetaPlantillaComunicadoEnum> plantila){
        if (plantila == null || plantila.isEmpty()) {
            logger.warn("La lista de plantillasComunicado está vacía para el aportante: " + aportanteAccionCobroDTO.getNumeroIdentificacion());
            return; // O manejar según negocio
        }
        logger.info("Entre a aportante con Procesar Aportante Electronico"+plantila);
        String actividad = consultarActividadCarIdNumeroIdentificacion(aportanteAccionCobroDTO.getIdCartera(), aportanteAccionCobroDTO.getNumeroIdentificacion());

        if (EtiquetaPlantillaComunicadoEnum.CARTA_APOR_EXPUL.equals(plantila.get(0)) && actividad.equals("0")) {
            enviarComunicadoAsignacion(aportanteAccionCobroDTO, plantila, tipoTransaccionElectronica);
        } else if (!plantila.get(0).equals(EtiquetaPlantillaComunicadoEnum.CARTA_APOR_EXPUL)) {
            enviarComunicadoAsignacion(aportanteAccionCobroDTO, plantila, tipoTransaccionElectronica);
        }

        Boolean anexoLiquidacion = consultaParametrizacionAnexoLiquidacion(accionCobro);
        if (anexoLiquidacion) {
            if (TipoAccionCobroEnum.D2.equals(accionCobro) || TipoAccionCobroEnum.E2.equals(accionCobro) || TipoAccionCobroEnum.C2.equals(accionCobro)) {
                enviarComunicadoAsignacion(aportanteAccionCobroDTO, plantila, tipoTransaccionElectronica);
            }
        }
    }


    /**
     * Método que verifica si un aportante es candidato a expulsión por mora
     *
     * @param idCartera Identificar de cartera del aportante
     */
    private void validarCandidatoExpulsion(Long idCartera,AportanteAccionCobroDTO aportanteAccionCobroDTO ) {
        try {
            TipoLineaCobroEnum lineaCobro = accionCobro.getLineaCobro();
            logger.debug("Inicio de método validarCandidatoExpulsion "+lineaCobro);
            //TipoLineaCobroEnum lineaCobro=this.accionCobro.getLineaCobro();
                if(aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
                    lineaCobro = TipoLineaCobroEnum.LC4;
                    }else if(aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO) ){
                        lineaCobro = TipoLineaCobroEnum.LC5;
                    }

            if (TipoLineaCobroEnum.LC1.equals(lineaCobro) || TipoLineaCobroEnum.LC2.equals(lineaCobro)
                || TipoLineaCobroEnum.LC3.equals(lineaCobro)) {
                ValidarEmpleadorExpulsion service = new ValidarEmpleadorExpulsion(idCartera);
                service.execute();
                Boolean esCandidatoExpulsion = service.getResult();

                if (esCandidatoExpulsion) {
                    EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorCartera(idCartera);
                    empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                    guardarDatosEmpleador(empleadorModeloDTO);
                }
            } else {
                ValidarPersonaExpulsion service = new ValidarPersonaExpulsion(idCartera);
                service.execute();
                Boolean esCandidatoExpulsion = service.getResult();

                if (esCandidatoExpulsion) {
                    RolAfiliadoModeloDTO rolAfiliadoModeloDTO = consultarRolAfiliadoCartera(idCartera);
                    rolAfiliadoModeloDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                    actualizarRolAfiliado(rolAfiliadoModeloDTO);
                }
            }

            logger.debug("Fin de método validarCandidatoExpulsion");
        } catch (Exception e) {
            logger.error("Ocurrió un error al tratar de actualizar la información del afiliado reportado en cartera", e);
        }
    }

    /**
     * Metodo que invoca al servicio de guardar datos del empleador
     *
     * @param empleadorModeloDTO recibe la informacion a guardar del empleador
     */
    private void guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO) {
        String firmaMetodo = "guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        GuardarDatosEmpleador guardarDatosEmpleador = new GuardarDatosEmpleador(empleadorModeloDTO);
        logger.debug("Finaliza metodo" + firmaMetodo);
        guardarDatosEmpleador.execute();
    }

    /**
     * Método que invoca el servicio de actualización de datos del rol afiliado
     *
     * @param rolAfiliadoDTO Información a persistir
     */
    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoDTO) {
        logger.debug("Inicio de método actualizarRolAfiliado");
        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoDTO);
        actualizarRolAfiliado.execute();
        logger.debug("Fin de método actualizarRolAfiliado");
    }

    /**
     * Método que invoca el servicio de consulta de un empleador por identificador de cartera
     *
     * @param idCartera Identificador de cartera
     * @return La información del empleador
     */
    private EmpleadorModeloDTO consultarEmpleadorCartera(Long idCartera) {
        logger.debug("Inicio de método consultarEmpleadorCartera");
        ConsultarEmpleadorCartera service = new ConsultarEmpleadorCartera(idCartera);
        service.execute();
        logger.debug("Fin de método consultarEmpleadorCartera");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de una persona por identificador de cartera
     *
     * @param idCartera Identificador de cartera
     * @return La información de la persona
     */
    private RolAfiliadoModeloDTO consultarRolAfiliadoCartera(Long idCartera) {
        logger.debug("Inicio de método consultarRolAfiliadoCartera");
        ConsultarRolAfiliadoCartera service = new ConsultarRolAfiliadoCartera(idCartera);
        service.execute();
        logger.debug("Fin de método consultarRolAfiliadoCartera");
        return service.getResult();
    }

    /**
     * Método que realiza la asignación de una solciitud de cobro electrónico
     *
     * @param aportanteAccionCobroDTO Información del aportante
     * @param usuariosBackEmpleador   Lista de usuarios con perfil Back-Empleador
     * @param usuariosBackPersona     Lista de usuarios con perfil Back-Persona
     * @return La información de la solicitud generada
     */
    private SolicitudGestionCobroElectronicoModeloDTO asignarSolicitudElectronica(AportanteAccionCobroDTO aportanteAccionCobroDTO) {
        logger.debug("Inicio de método asignarSolicitudElectronica");

        // Obtiene el ultimo "Back de actualización" asignado, para realizar por
        // turnos la asignación
        String ultimoUsuario = obtenerUltimoBackAsignado(aportanteAccionCobroDTO.getTipoAportante(), tipoTransaccionElectronica);
        UsuarioDTO usuarioBackDTO = null;

        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportanteAccionCobroDTO.getTipoAportante())) {
            usuarioBackDTO = obtenerUsuarioConsecutivo(usuariosBackEmpleador, ultimoUsuario);
        } else {
            usuarioBackDTO = obtenerUsuarioConsecutivo(usuariosBackPersona, ultimoUsuario);
        }

        // Construye la solicitud de gestión de cobro electrónico
        SolicitudGestionCobroElectronicoModeloDTO solicitudDTO = construirSolicitudGestionCobroElectronico(userDTO,
            aportanteAccionCobroDTO);
        solicitudDTO = guardarSolicitudGestionCobroElectronico(solicitudDTO);
        solicitudDTO.setIdSolicitud(solicitudDTO.getIdSolicitud());
        String numeroRadicacion = generarNumeroRadicado(solicitudDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());

        // Inicia el proceso BPM de actualización de datos del aportante
        Map<String, Object> params = new HashMap<>();
        params.put(ConstanteCartera.ID_SOLICITUD, solicitudDTO.getIdSolicitud());
        params.put(ConstanteCartera.NUMERO_RADICADO, numeroRadicacion);
        params.put(ConstanteCartera.ANALISTA_APORTES, tipoTransaccionElectronica.getProceso().equals(ProcesoEnum.GESTION_COBRO_ELECTRONICO) ?  ultimoUsuario : usuarioBackDTO.getNombreUsuario());
        String[] split = accionCobro.getDescripcion().split(" ");
        params.put(ConstanteCartera.ACCION_COBRO, split[3]);
        Long idInstancia = iniciarProceso(ProcesoEnum.GESTION_COBRO_ELECTRONICO, params, Boolean.TRUE);

        // Actualiza la solicitud de cobro electrónico
        solicitudDTO.setNumeroRadicacion(numeroRadicacion);
        solicitudDTO.setDestinatario(tipoTransaccionElectronica.getProceso().equals(ProcesoEnum.GESTION_COBRO_ELECTRONICO) ?  ultimoUsuario : usuarioBackDTO.getNombreUsuario());
        solicitudDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
        solicitudDTO.setIdInstanciaProceso(idInstancia.toString());
        guardarSolicitudGestionCobroElectronico(solicitudDTO);

        logger.debug("Fin de método asignarSolicitudElectronica");
        return solicitudDTO;
    }

    /**
     * Método que realiza la asignación de una solciitud de cobro físico
     *
     * @param usuariosAnalistaAportes Lista de usuarios con perfil Analista-Aportes
     * @return La información de la solicitud radicada
     */
    private SolicitudGestionCobroFisicoModeloDTO asignarSolicitudFisica() {
        logger.debug("Inicio de método asignarSolicitudFisica");

        // Se busca el usuario para asignarle la solicitud
        String usuarioAnalista = asignarAutomaticamenteUsuarioCajaCompensacion(
            Long.parseLong((String) CacheManager.getConstante(ConstantesSistemaConstants.SEDE_USUARIO_SYSTEM)),
            ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL);
        if (TipoAccionCobroEnum.H2_C6.equals(accionCobro) || TipoAccionCobroEnum.F1_C6.equals(accionCobro) ||
            TipoAccionCobroEnum.H2_C6_EX.equals(accionCobro) || TipoAccionCobroEnum.F1_C6_EX.equals(accionCobro)) {
            if (TipoAccionCobroEnum.F1_C6.equals(accionCobro) || TipoAccionCobroEnum.F1_C6_EX.equals(accionCobro)) {
                accionCobro = TipoAccionCobroEnum.F1;
            } else if (TipoAccionCobroEnum.H2_C6.equals(accionCobro) || TipoAccionCobroEnum.H2_C6_EX.equals(accionCobro)) {
                accionCobro = TipoAccionCobroEnum.H2;
            }
        }
        // Construye la solicitud de gestión de cobro físico
        logger.info("Inicio construirSolicitudGestionCobroFisico accionCobro: " + accionCobro);
        SolicitudGestionCobroFisicoModeloDTO solicitudDTO = construirSolicitudGestionCobroFisico(userDTO, accionCobro);
        solicitudDTO = guardarSolicitudGestionCobro(solicitudDTO);
        String numeroRadicacion = generarNumeroRadicado(solicitudDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion());

        // Inicia el proceso BPM para registrar la remisión del comunicado
        Map<String, Object> params = new HashMap<>();
        params.put(ConstanteCartera.ID_SOLICITUD, solicitudDTO.getIdSolicitud());
        params.put(ConstanteCartera.NUMERO_RADICADO, numeroRadicacion);
        Long idInstancia = null;

        if (TipoAccionCobroEnum.E2.equals(accionCobro)) {
            params.put(ConstanteCartera.ANALISTA_CARTERA, usuarioAnalista);
            idInstancia = iniciarProceso(ProcesoEnum.GESTION_COBRO_2E, params, Boolean.TRUE);
        } else {
            params.put(ConstanteCartera.ANALISTA, usuarioAnalista);
            String[] split = accionCobro.getDescripcion().split(" ");
            params.put(ConstanteCartera.ACCION_COBRO, split[3]);
            idInstancia = iniciarProceso(ProcesoEnum.GESTION_CARTERA_FISICA_GENERAL, params, Boolean.TRUE);
        }

        // Actualiza la solicitud
        solicitudDTO.setNumeroRadicacion(numeroRadicacion);
        solicitudDTO.setDestinatario(usuarioAnalista);
        solicitudDTO.setSedeDestinatario(userDTO.getSedeCajaCompensacion());
        solicitudDTO.setIdInstanciaProceso(idInstancia.toString());
        guardarSolicitudGestionCobro(solicitudDTO);

        logger.debug("Fin de método asignarSolicitudFisica");
        return solicitudDTO;
    }

    /**
     * Método que invoca el servicio de consulta de usuarios para asignación de procesos
     *
     * @param sedeCaja    Identificador de la sede
     * @param procesoEnum Proceso a ejecutar
     * @return El nombre de usuario
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        return ejecutarAsignacion.getResult();
    }

    /**
     * Método que almacena el detalle de una solicitud de gestión de cobro
     * físico
     *
     * @param aportanteAccionCobroDTO Información del aportante
     * @param solicitudDTO            Información de la solicitud padre de gestión de cobro físico
     */
    private DetalleSolicitudGestionCobroModeloDTO guardarDetalleSolicitudGestionCobro(AportanteAccionCobroDTO aportanteAccionCobroDTO,
                                                                                      SolicitudGestionCobroFisicoModeloDTO solicitudDTO) {
        logger.debug("Inicio de método guardarDetalleSolicitudGestionCobro");
        DetalleSolicitudGestionCobroModeloDTO detalleSolicitudDTO = new DetalleSolicitudGestionCobroModeloDTO();
        detalleSolicitudDTO.setIdCartera(aportanteAccionCobroDTO.getIdCartera());
        detalleSolicitudDTO.setIdPrimeraSolicitudRemision(solicitudDTO.getIdSolicitudGestionCobroFisico());
        logger.debug("Fin de método guardarDetalleSolicitudGestionCobro");
        return detalleSolicitudDTO;
    }

    /**
     * Método que almacena un comunicado en el ECM, y luego en la tabla
     * <code>DocumentoCartera</code>
     *
     * @param plantillaComunicado     Plantilla del comunicado
     * @param tipoTransaccion         Tipo de transacción
     * @param aportanteAccionCobroDTO Información del aportante
     * @return La información del comunicado generado y almacenado
     */
    private AsignacionTemporalComunicado almacenarComunicado(EtiquetaPlantillaComunicadoEnum plantillaComunicado, TipoTransaccionEnum tipoTransaccion,
                                                             AportanteAccionCobroDTO aportanteAccionCobroDTO) {
        logger.info("Inicio de método almacenarComunicado");

        DocumentoCarteraModeloDTO documentoCarteraDTOC1 = new DocumentoCarteraModeloDTO();
        DocumentoCartera documentoCarteraC1 = new DocumentoCartera();
        if (TipoAccionCobroEnum.C1.equals(accionCobro) || TipoAccionCobroEnum.C2.equals(accionCobro)) {
            documentoCarteraDTOC1 = transformarArchivoDocumentoCarteraC1Inicio(plantillaComunicado, aportanteAccionCobroDTO.getIdCartera());
            documentoCarteraC1 = guardarDocumentoCarteraEnt(documentoCarteraDTOC1);
        }
        logger.info("**__**almacenarComunicado plantillaComunicado "+plantillaComunicado);
        // Genera y guarda el comunicado en el ECM
        NotificacionParametrizadaDTO notificacionParametrizadaDTO = construirComunicado(plantillaComunicado, tipoTransaccion,
            aportanteAccionCobroDTO);
        InformacionArchivoDTO informacionArchivo = guardarObtenerInfoArchivoComunicado(tipoTransaccion, plantillaComunicado, notificacionParametrizadaDTO.getParametros());

        if (TipoAccionCobroEnum.C1.equals(accionCobro) || TipoAccionCobroEnum.C2.equals(accionCobro)) {
            documentoCarteraDTO = transformarArchivoDocumentoCarteraC1Fin(informacionArchivo, documentoCarteraC1);
            //documentoCarteraDTO = guardarDocumentoCartera(documentoCarteraDTOC1);
        } else {
            documentoCarteraDTO = transformarArchivoDocumentoCartera(plantillaComunicado, informacionArchivo, aportanteAccionCobroDTO.getIdCartera());
            documentoCarteraDTO = guardarDocumentoCartera(documentoCarteraDTO);
        }

        DocumentoSoporteModeloDTO documentoSoporteLocal = new DocumentoSoporteModeloDTO();
        documentoSoporteLocal = documentoCarteraDTO.obtenerDocumentoSoporteDTO();

        List<String> idsAdjuntos = new ArrayList<>();
        idsAdjuntos.add(informacionArchivo.getIdentificadorDocumento());
        notificacionParametrizadaDTO.setArchivosAdjuntosIds(idsAdjuntos);
        notificacionParametrizadaDTO.setEnvioExitoso(Boolean.TRUE);

        logger.info("Fin de método almacenarComunicado");
        return new AsignacionTemporalComunicado(notificacionParametrizadaDTO, documentoSoporteLocal);
    }


    /**
     * Método que almacena la bitácora de una acción de cobro
     *
     * @param aportanteAccionCobroDTO     Información del aportante
     * @param tipoTransaccion             Tipo de transacción
     * @param resultado                   Resultado de la bitácora
     * @param liquidacionAportanteBandera
     */
    private void almacenarBitacora(AportanteAccionCobroDTO aportanteAccionCobroDTO, TipoTransaccionEnum tipoTransaccion,
                                   ResultadoBitacoraCarteraEnum resultado, List<DocumentoSoporteModeloDTO> documentosSoporte, Boolean liquidacionAportanteBandera, Long idComunicado) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("Inicia cae siempre :almacenarBitacora:: ->");
            logger.info("tipoTransaccion::" + objectMapper.writeValueAsString(tipoTransaccion));
            logger.info("documentoSoporte::" + objectMapper.writeValueAsString(documentosSoporte));
            //Se consulta la persona a la cual se le registra la bitácora
            PersonaModeloDTO personaModeloDTO = consultarDatosPersona(aportanteAccionCobroDTO.getTipoIdentificacion(),
                aportanteAccionCobroDTO.getNumeroIdentificacion());
            // Consulta el número de operación de cartera
            Long numeroOperacion = consultarNumeroOperacionCartera(aportanteAccionCobroDTO.getIdCartera());
            // Almacena bitácora
            BitacoraCarteraDTO bitacoraCartera = new BitacoraCarteraDTO();
            if (liquidacionAportanteBandera) {
                logger.info("liquidacionAportanteBandera entra...." + liquidacionAportanteBandera);
                bitacoraCartera.setActividad(TipoActividadBitacoraEnum.GENERAR_LIQUIDACION);
                //bitacoraCartera.setActividad(TipoActividadBitacoraEnum.valueOf(accionCobro.name()));
                logger.info("Hice el if y Estoy con datos de liquidacionAportanteBandera::" + liquidacionAportanteBandera);
            } else if (documentosSoporte != null && !documentosSoporte.isEmpty() && documentosSoporte.get(0).getNombre().equalsIgnoreCase(EtiquetaPlantillaComunicadoEnum.NOTIFI_MORA_DESAF.name())) {
                logger.info("documentosSoporte NOTIFI_MORA_DESAF");
                bitacoraCartera.setActividad(TipoActividadBitacoraEnum.NOTIFICACION_MORA_DESAFILIACION);
            } else if (documentosSoporte != null && !documentosSoporte.isEmpty() && documentosSoporte.get(0).getNombre().equalsIgnoreCase(EtiquetaPlantillaComunicadoEnum.CARTA_APOR_EXPUL.name())) {
                logger.info("documentosSoporte CARTA_APOR_EXPUL");
                bitacoraCartera.setActividad(TipoActividadBitacoraEnum.CARTA_APOR_EXPUL);
            } else if (documentosSoporte != null && !documentosSoporte.isEmpty() &&
                documentosSoporte.get(0).getNombre().equalsIgnoreCase("NOTI_IN_RE_APORTE")) {
                logger.info("documentosSoporte NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE");
                bitacoraCartera.setActividad(TipoActividadBitacoraEnum.NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE);
            } else {
                bitacoraCartera.setActividad(TipoActividadBitacoraEnum.valueOf(accionCobro.name()));
            }
            bitacoraCartera.setResultado(resultado == null ? ResultadoBitacoraCarteraEnum.EN_PROCESO : resultado);
            bitacoraCartera.setIdPersona(personaModeloDTO.getIdPersona());
            bitacoraCartera.setTipoSolicitante(aportanteAccionCobroDTO.getTipoAportante());
            bitacoraCartera.setNumeroOperacion(numeroOperacion != null ? numeroOperacion.toString() : null);
            bitacoraCartera.setMedio(ProcesoEnum.GESTION_COBRO_ELECTRONICO.equals(tipoTransaccion.getProceso()) ? MedioCarteraEnum.ELECTRONICO : MedioCarteraEnum.DOCUMENTO_FISICO);

            bitacoraCartera.setDocumentosSoporte(documentosSoporte);
            Long idBitacoraCartera = guardarBitacoraCartera(bitacoraCartera);

            logger.info("ENTRA ACA EN GuardarRelacion");
            logger.info(idComunicado);
            logger.info(idBitacoraCartera);
            logger.info("=========");
            if (idComunicado != null) {
                guardarRelacionComunicadoBitacoraCartera(idBitacoraCartera, idComunicado);
            }

            logger.info("bitacoraCartera" + objectMapper.writeValueAsString(bitacoraCartera));
            logger.info(resultado);
            //Siempre que el resultado sea Enviado se debe registrar también un resultado Exitoso
            if (ResultadoBitacoraCarteraEnum.ENVIADO.equals(resultado)) {
                logger.info("ResultadoBitacoraCarteraEnum.ENVIADO");
                bitacoraCartera.setResultado(ResultadoBitacoraCarteraEnum.EXITOSO);
                guardarBitacoraCartera(bitacoraCartera);
            } else if (ResultadoBitacoraCarteraEnum.NO_ENVIADO.equals(resultado)) {
                bitacoraCartera.setResultado(ResultadoBitacoraCarteraEnum.NO_EXITOSO);
                guardarBitacoraCartera(bitacoraCartera);
            }
        } catch (Exception e) {
            logger.error("No se pudo almacenar la bitácora de la acción " + accionCobro.name(), e);
        }
    }

    private void almacenarBitacora(AportanteAccionCobroDTO aportanteAccionCobroDTO, TipoTransaccionEnum tipoTransaccion,
                                   List<DocumentoSoporteModeloDTO> documentosSoporte) {
        logger.info("Entra a bitacora otra");
        almacenarBitacora(aportanteAccionCobroDTO, tipoTransaccion, null, documentosSoporte, false, null);
    }

    /**
     * Método que obtiene el número de operación de cartera a partir de un identificador del periodo en mora
     *
     * @param idCartera Identificador de cartera / periodo en mora
     * @return El número de operación
     */
    private Long consultarNumeroOperacionCartera(Long idCartera) {
        logger.debug("Inicia método consultarNumeroOperacionCartera");
        ConsultarNumeroOperacionCartera service = new ConsultarNumeroOperacionCartera(idCartera);
        service.execute();
        logger.debug("Finaliza método consultarNumeroOperacionCartera");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de desafiliación
     *
     * @param lineaCobro Línea de cobro
     * @return La parametrización de desafiliación para la línea de cobro
     */
    private ParametrizacionDesafiliacionModeloDTO consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro) {
        logger.debug("Inicia método consultarParametrizacionDesafiliacion");
        ConsultarParametrizacionDesafiliacion service = new ConsultarParametrizacionDesafiliacion(lineaCobro);
        service.execute();
        logger.debug("Finaliza método consultarParametrizacionDesafiliacion");
        return service.getResult();
    }

    /**
     * Método que construye un comunicado de gestión de cobro
     *
     * @param etiquetaPlantilla       Plantilla del comunicado
     * @param tipoTransaccion         Tipo de transacción
     * @param aportanteAccionCobroDTO Información del aportante en gestión de cobro
     * @return La información del comunicado a ser enviado
     */
    private NotificacionParametrizadaDTO construirComunicado(EtiquetaPlantillaComunicadoEnum plantillaComunicado,
                                                             TipoTransaccionEnum tipoTransaccion, AportanteAccionCobroDTO aportanteAccionCobroDTO) {
        logger.info("**__** Inicio de método construirComunicado "+plantillaComunicado);
        logger.info("**__** aportanteAccionCobroDTO "+aportanteAccionCobroDTO.toString());

        NotificacionParametrizadaDTO notificacionDTO = new NotificacionParametrizadaDTO();
        notificacionDTO.setEtiquetaPlantillaComunicado(plantillaComunicado);
        notificacionDTO.setProcesoEvento(tipoTransaccion.getProceso().name());
        notificacionDTO.setTipoTx(tipoTransaccion);

        //Se consulta el id de la entidad objeto del comunicado
        ConsultarIdEntidadComunicado entidadSrv = new ConsultarIdEntidadComunicado(aportanteAccionCobroDTO.getNumeroIdentificacion(), aportanteAccionCobroDTO.getTipoIdentificacion());
        entidadSrv.execute();
        DatosIdEntidadComunicadoDTO result = entidadSrv.getResult();

        if (result != null && TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportanteAccionCobroDTO.getTipoAportante())) {
            notificacionDTO.setIdEmpleador(result.getIdEmpleador());
        } else if (result != null) {
            notificacionDTO.setIdPersona(result.getIdPersona());
        }

        ParametrosComunicadoDTO parametrosDTO = new ParametrosComunicadoDTO();
        Map<String, String> params = new HashMap<>();

        if (!plantillaComunicado.equals(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR)) {
            parametrosDTO.setNumeroIdentificacion(aportanteAccionCobroDTO.getNumeroIdentificacion());
            parametrosDTO.setTipoIdentificacion(aportanteAccionCobroDTO.getTipoIdentificacion());
            params.put(ConstanteCartera.TIPO_IDENTIFICACION, aportanteAccionCobroDTO.getTipoIdentificacion().name());
            params.put(ConstanteCartera.NUMERO_IDENTIFICACION, aportanteAccionCobroDTO.getNumeroIdentificacion());
        } else {
            parametrosDTO.setIdCartera(aportanteAccionCobroDTO.getIdCartera());
            params.put(ConstanteCartera.ID_CARTERA, aportanteAccionCobroDTO.getIdCartera().toString());
        }

        params.put(ConstanteCartera.IDENTIFICADOR, aportanteAccionCobroDTO.getIdCartera().toString());
        notificacionDTO.setParams(params);
        Map<String, Object> parametros = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            parametros.put(entry.getKey(), entry.getValue());
        }

        parametrosDTO.setParams(parametros);
        notificacionDTO.setParametros(parametrosDTO);

        if (tipoTransaccion.equals(tipoTransaccionElectronica)) {
            ParametrizacionGestionCobroModeloDTO parametrizacionGestion = null;
        logger.info("**__** Inicio de método this.accionCobro "+this.accionCobro);
            if (TipoAccionCobroEnum.LC4A.equals(this.accionCobro) || TipoAccionCobroEnum.LC5A.equals(this.accionCobro)) {
                parametrizacionGestion = new ParametrizacionGestionCobroModeloDTO();
                parametrizacionGestion.setTipoParametrizacion(TipoParametrizacionGestionCobroEnum.LINEA_COBRO);
            } else if (TipoAccionCobroEnum.LC4C.equals(this.accionCobro) || TipoAccionCobroEnum.LC5C.equals(this.accionCobro)) {
                ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacion = consultarParametrizacionDesafiliacion(
                    this.accionCobro.getLineaCobro());
                parametrizacionGestion = new ParametrizacionGestionCobroModeloDTO();
                parametrizacionGestion.setTipoParametrizacion(tipoParametrizacion);
                parametrizacionGestion.setCorrespondenciaFisico(parametrizacionDesafiliacion.getCorrespondenciaFisico());
                parametrizacionGestion.setNotificacionJudicialFisico(parametrizacionDesafiliacion.getNotificacionJudicialFisico());
                parametrizacionGestion.setOficinaPrincipalFisico(parametrizacionDesafiliacion.getOficinaPrincipalFisico());
                parametrizacionGestion.setOficinaPrincipalElectronico(parametrizacionDesafiliacion.getOficinaPrincipalElectronico());
                parametrizacionGestion.setRepresentanteLegalElectronico(parametrizacionDesafiliacion.getRepresentanteLegalElectronico());
                parametrizacionGestion.setResponsableAportesElectronico(parametrizacionDesafiliacion.getResponsableAportesElectronico());
            } else {
                      logger.info("Fin de método construirComunicado tipoTransaccion"+tipoTransaccion);
                ParametrizarDestinatarios parametrizarDestinatarios = new ParametrizarDestinatarios();
                parametrizacionGestion = parametrizarDestinatarios.consultarParametrizacion(tipoTransaccion);
            }

              TipoLineaCobroEnum lineaCobro=this.accionCobro.getLineaCobro();
                if(aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE)) {
                    lineaCobro = TipoLineaCobroEnum.LC4;
                    }else if(aportanteAccionCobroDTO.getTipoAportante().equals(TipoSolicitanteMovimientoAporteEnum.PENSIONADO) ){
                        lineaCobro = TipoLineaCobroEnum.LC5;
                    }

            List<AutorizacionEnvioComunicadoDTO> correos = obtenerRolesDestinatarios(parametrizacionGestion, aportanteAccionCobroDTO.getTipoIdentificacion(),
                aportanteAccionCobroDTO.getNumeroIdentificacion(), lineaCobro);
            List<String> correosDestinatarios = new ArrayList<>();
            for (AutorizacionEnvioComunicadoDTO autorizacionEnvioComunicadoDTO : correos) {
                logger.info("Fin de método construirComunicado autorizacionEnvioComunicadoDTO.getDestinatario()"+autorizacionEnvioComunicadoDTO.getDestinatario());
                correosDestinatarios.add(autorizacionEnvioComunicadoDTO.getDestinatario());
            }
            notificacionDTO.setDestinatarioTO(correosDestinatarios);
            notificacionDTO.setReplantearDestinatarioTO(true);
            notificacionDTO.setAutorizaEnvio(correos.get(0) != null ? correos.get(0).getAutorizaEnvio() : Boolean.FALSE);

        }

        logger.info("Fin de método construirComunicado");
        return notificacionDTO;
    }

    /**
     * Servicio que consulta el último back asignado
     *
     * @param tipoSolicitante Tipo de solicitante
     * @param tipoTransaccion Tipo de transacción
     * @return Último usuario asignado
     */
    private String obtenerUltimoBackAsignado(TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoTransaccionEnum tipoTransaccion) {
        logger.debug("Inicio de método obtenerUltimoBackAsignado");
        ObtenerUltimoBackAsignado obtenerUltimoBackService = new ObtenerUltimoBackAsignado(tipoSolicitante, tipoTransaccion);
        obtenerUltimoBackService.execute();
        logger.debug("Fin de método obtenerUltimoBackAsignado");
        return obtenerUltimoBackService.getResult();
    }

    /**
     * Método que se encarga de obtener un usuario utilizando el método
     * consecutivo por turnos
     *
     * @param usuarios      Lista de usuarios
     * @param ultimoUsuario Último usuario asignado
     * @return El usuario a asignar
     */
    private UsuarioDTO obtenerUsuarioConsecutivo(List<UsuarioDTO> usuarios, String ultimoUsuario) {
        logger.debug("Inicio método obtenerUsuarioConsecutivo(String perfil, String sede, String ultimoUsuario)");

        Collections.sort(usuarios, new Comparator<UsuarioDTO>() {
            @Override
            public int compare(UsuarioDTO u1, UsuarioDTO u2) {
                return u1.getNombreUsuario().toUpperCase().compareTo(u2.getNombreUsuario().toUpperCase());
            }
        });

        if (ultimoUsuario == null) {
            UsuarioDTO usuario = usuarios.iterator().next();
            return usuario;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombreUsuario().equals(ultimoUsuario)) {
                if (i != (usuarios.size() - 1)) {
                    return usuarios.get(i + 1);
                } else {
                    return usuarios.get(0);
                }
            }
        }

        if (usuarios != null && !usuarios.isEmpty()) {
            return usuarios.get(0);
        }

        return null;
    }

    /**
     * Método que construye una solicitud de gestión de cobro electrónico
     *
     * @param userDTO                 Información del usuario
     * @param aportanteAccionCobroDTO Información del aportante
     * @return Objeto <code>SolicitudGestionCobroElectronicoModeloDTO</code> con
     * la información de la solicitud
     */
    private SolicitudGestionCobroElectronicoModeloDTO construirSolicitudGestionCobroElectronico(UserDTO userDTO,
                                                                                                AportanteAccionCobroDTO aportanteAccionCobroDTO) {
        logger.debug("Inicio de método construirSolicitudGestionCobroElectronico");
        SolicitudGestionCobroElectronicoModeloDTO solicitudDTO = new SolicitudGestionCobroElectronicoModeloDTO();
        solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.WEB);
        solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudDTO.setFechaCreacion(new Date().getTime());
        solicitudDTO.setFechaRadicacion(new Date().getTime());
        solicitudDTO.setTipoTransaccion(tipoTransaccionElectronica);
        solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());

        solicitudDTO.setEstado(EstadoSolicitudGestionCobroEnum.PENDIENTE_ACTUALIZACION_DATOS);
        solicitudDTO.setIdCartera(aportanteAccionCobroDTO.getIdCartera());
        solicitudDTO.setTipoAccionCobro(aportanteAccionCobroDTO.getAccionCobro());
        logger.debug("Fin de método construirSolicitudGestionCobroElectronico");
        return solicitudDTO;
    }

    /**
     * Método que invoca el servicio de almacenamiento de una solicitud de
     * gestión de cobro electrónico
     *
     * @param solicitudDTO Información de la solicitud
     * @return La solicitud actualizada
     */
    private SolicitudGestionCobroElectronicoModeloDTO guardarSolicitudGestionCobroElectronico(
        SolicitudGestionCobroElectronicoModeloDTO solicitudDTO) {
        logger.debug("Inicio de método guardarSolicitudGestionCobroElectronico");
        GuardarSolicitudGestionCobroElectronico service = new GuardarSolicitudGestionCobroElectronico(solicitudDTO);
        service.execute();
        logger.debug("Fin de método guardarSolicitudGestionCobroElectronico");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de generación de número de radicado
     *
     * @param idSolicitud          Identificador de la solicitud
     * @param sedeCajaCompensacion Usuario del sistema
     * @return Número de radicación
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.debug("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        String numeroRadicacion = radicarSolicitudService.getResult();
        logger.debug("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        return numeroRadicacion;
    }

    /**
     * Método encargado de llamar el servicio de BPM que inicia un proceso
     *
     * @param proceso Proceso el cual se va a iniciar
     * @param params  Listado de parámetros correspondientes al proceso
     * @return El id de la instancia proceso
     */
    private Long iniciarProceso(ProcesoEnum proceso, Map<String, Object> params, Boolean automatico) {
        logger.debug("Inicia iniciarProceso");
        IniciarProceso iniciarProceso = new IniciarProceso(proceso, params);

        if (automatico) {
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            iniciarProceso.setToken(token.getToken());
        }

        iniciarProceso.execute();
        logger.debug("Finaliza iniciarProceso");
        return iniciarProceso.getResult();
    }

    /**
     * Método que construye una solicitud general de gestión de cobro físico
     *
     * @param userDTO     Información del usuario
     * @param accionCobro Acción de cobro
     * @return Objeto <code>SolicitudGestionCobroFisicoModeloDTO</code> con la
     * información de la solicitud
     */
    private SolicitudGestionCobroFisicoModeloDTO construirSolicitudGestionCobroFisico(UserDTO userDTO, TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método construirSolicitudGestionCobroFisico");
        SolicitudGestionCobroFisicoModeloDTO solicitudDTO = new SolicitudGestionCobroFisicoModeloDTO();
        solicitudDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudDTO.setFechaCreacion(new Date().getTime());
        solicitudDTO.setFechaRadicacion(new Date().getTime());
        solicitudDTO.setTipoTransaccion(tipoTransaccionFisica);
        solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudDTO.setEstado(EstadoSolicitudGestionCobroEnum.PENDIENTE_REGISTRAR_REMISION);

        if (TipoAccionCobroEnum.E2.equals(accionCobro)) {
            solicitudDTO.setEstado(EstadoSolicitudGestionCobroEnum.PENDIENTE_REGISTRAR_EDICTO_PUBLICADO);
        }
        logger.info("Adentro construirSolicitudGestionCobroFisico accionCobro: " + accionCobro);
        solicitudDTO.setTipoAccionCobro(accionCobro);
        logger.debug("Fin de método construirSolicitudGestionCobroFisico");
        return solicitudDTO;
    }

    /**
     * Método encargado de invocar el servicio que guarda o actualiza una
     * solicitud de gestión de cobro.
     *
     * @param solicitudGestionDTO gestión de cobro a guardar.
     * @return Solicitud guardada o actualizada.
     */
    private SolicitudGestionCobroFisicoModeloDTO guardarSolicitudGestionCobro(SolicitudGestionCobroFisicoModeloDTO solicitudGestionDTO) {
        logger.debug("Inicio de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        GuardarSolicitudGestionCobro guardarSolicitudService = new GuardarSolicitudGestionCobro(solicitudGestionDTO);
        guardarSolicitudService.execute();
        logger.debug("Fin de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        return guardarSolicitudService.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de un comunicado en el
     * ECM
     *
     * @param tipoTransaccion        Tipo de transacción
     * @param plantilla              Plantilla del comunicado
     * @param parametroComunicadoDTO Parámetros del comunicado
     * @return Identificador del archivo almacenado en el ECM
     */
    private InformacionArchivoDTO guardarObtenerInfoArchivoComunicado(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla,
                                                                      ParametrosComunicadoDTO parametroComunicadoDTO) {
        logger.debug("Inicio de método guardarObtenerComunicadoECM");
        logger.info("tipo de transacción" + tipoTransaccion.name());
        logger.info("tipo de transacción 2" + plantilla.name());
        logger.info("tipo de transacción 2" + parametroComunicadoDTO.toString());

        GuardarObtenerInfoArchivoComunicado service = new GuardarObtenerInfoArchivoComunicado(tipoTransaccion, plantilla, parametroComunicadoDTO);
        service.execute();
        logger.debug("Fin de método guardarObtenerComunicadoECM");
        return service.getResult();
    }

    /**
     * Método que obtiene un objeto <code>DocumentoCarteraModeloDTO</code> a
     * partir de un objeto <code>InformacionArchivoDTO</code> y un identificador
     * de registro en cartera
     *
     * @param plantilla             Plantilla del comunicado a ser enviado
     * @param informacionArchivoDTO Información del archivo en el ECM
     * @param idCartera             Identificador del registro en cartera
     * @return EL objeto <code>DocumentoCarteraModeloDTO</code> equivalente
     */
    private DocumentoCarteraModeloDTO transformarArchivoDocumentoCartera(EtiquetaPlantillaComunicadoEnum plantilla,
                                                                         InformacionArchivoDTO informacionArchivoDTO, Long idCartera) {
        logger.debug("Inicio de método transformarArchivoDocumentoCartera");
        DocumentoCarteraModeloDTO documentoCartera = new DocumentoCarteraModeloDTO();
        documentoCartera.setDescripcionComentarios(plantilla.getDescripcion());
        documentoCartera.setFechaHoraCargue(new Date().getTime());
        documentoCartera.setIdCartera(idCartera);
        documentoCartera.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
        documentoCartera.setNombre(plantilla.name());
        documentoCartera.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
        documentoCartera.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
        documentoCartera.setAccionCobro(accionCobro);

        if (TipoAccionCobroEnum.C1.equals(accionCobro) || TipoAccionCobroEnum.C2.equals(accionCobro)) {
            String consecutivo = obtenerConsecutivoLiquidacion();
            documentoCartera.setConsecutivoLiquidacion(consecutivo);
        }

        logger.debug("Fin de método transformarArchivoDocumentoCartera");
        return documentoCartera;
    }

    private DocumentoCarteraModeloDTO transformarArchivoDocumentoCarteraC1Inicio(EtiquetaPlantillaComunicadoEnum plantilla, Long idCartera) {
        logger.debug("Inicio de método transformarArchivoDocumentoCarteraC1Inicio");
        DocumentoCarteraModeloDTO documentoCartera = new DocumentoCarteraModeloDTO();
        documentoCartera.setDescripcionComentarios(plantilla.getDescripcion());
        documentoCartera.setFechaHoraCargue(new Date().getTime());
        documentoCartera.setIdCartera(idCartera);
        documentoCartera.setIdentificacionDocumento("");
        documentoCartera.setNombre(plantilla.name());
        documentoCartera.setVersionDocumento("");
        documentoCartera.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
        documentoCartera.setAccionCobro(accionCobro);

        if (TipoAccionCobroEnum.C1.equals(accionCobro) || TipoAccionCobroEnum.C2.equals(accionCobro)) {
            String consecutivo;
            if (TipoAccionCobroEnum.C2.equals(accionCobro)) {
                String consecutivoExistente;
                consecutivoExistente = obtenerConsecutivoLiquidacionExistenteCartera(idCartera);
                logger.info("ConsecutivoExistente:" + consecutivoExistente);
                if (consecutivoExistente.equals("")) {
                    consecutivo = obtenerConsecutivoLiquidacion();
                } else {
                    consecutivo = consecutivoExistente;
                }
            } else {
                consecutivo = obtenerConsecutivoLiquidacion();
            }
            logger.info("Consecutivo:" + consecutivo);
            documentoCartera.setConsecutivoLiquidacion(consecutivo);
        }

        logger.debug("Fin de método transformarArchivoDocumentoCarteraC1Inicio");
        return documentoCartera;
    }

    private DocumentoCarteraModeloDTO transformarArchivoDocumentoCarteraC1Fin(InformacionArchivoDTO informacionArchivoDTO, DocumentoCartera documentoCarteraC1) {
        logger.debug("Inicio de método transformarArchivoDocumentoCarteraC1Fin");
        DocumentoSoporte documentoSoporte = documentoCarteraC1.getDocumentoSoporte();
        documentoSoporte.setIdentificacionDocumento(informacionArchivoDTO.getIdentificadorDocumento());
        documentoSoporte.setVersionDocumento(informacionArchivoDTO.getVersionDocumento());
        //documentoCarteraC1.setDocumentoSoporte(documentoSoporte);

        DocumentoCarteraModeloDTO documentoCartera = new DocumentoCarteraModeloDTO();
        documentoCartera.convertToDTO(documentoCarteraC1);

        logger.debug("Fin de método transformarArchivoDocumentoCarteraC1Fin");
        return documentoCartera;
    }

    /**
     * Método que invoca el servicio de consulta del consecutivo de liquidación
     * a ser generado
     *
     * @return El consecutivo de liquidación
     */
    private String obtenerConsecutivoLiquidacion() {
        logger.debug("Inicio de método obtenerConsecutivoLiquidacion");
        ObtenerConsecutivoLiquidacion service = new ObtenerConsecutivoLiquidacion();
        service.execute();
        logger.debug("Fin de método obtenerConsecutivoLiquidacion");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consulta del consecutivo de liquidación existente para una cartera
     *
     * @return El consecutivo de liquidación
     */
    private String obtenerConsecutivoLiquidacionExistenteCartera(Long idCartera) {
        logger.debug("Inicio de método obtenerConsecutivoLiquidacionExistenteCartera");
        ObtenerConsecutivoLiquidacionExistenteCartera service = new ObtenerConsecutivoLiquidacionExistenteCartera(idCartera);
        service.execute();
        logger.debug("Fin de método obtenerConsecutivoLiquidacionExistenteCartera");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de documentos cartera
     *
     * @param documentoCarteraDTO Información del documento
     * @return Información actualizada del documento almacenado
     */
    private DocumentoCarteraModeloDTO guardarDocumentoCartera(DocumentoCarteraModeloDTO documentoCarteraDTO) {
        logger.debug("Inicio de método guardarDocumentoCartera");
        GuardarDocumentoCartera service = new GuardarDocumentoCartera(documentoCarteraDTO);
        service.execute();
        logger.debug("Fin de método guardarDocumentoCartera");
        return service.getResult();
    }

    private DocumentoCartera guardarDocumentoCarteraEnt(DocumentoCarteraModeloDTO documentoCarteraDTOC1) {
        logger.debug("Inicio de método guardarDocumentoCarteraEnt");
        GuardarDocumentoCarteraEnt service = new GuardarDocumentoCarteraEnt(documentoCarteraDTOC1);
        service.execute();
        logger.debug("Fin de método guardarDocumentoCarteraEnt");
        return service.getResult();
    }

    /**
     * Metodo que invoca servicio de guardar el DetalleSolicitudGestionCobro
     *
     * @param detalleSolicitudGestionCobroModeloDTO DTO con la informacion relacionado al detalle
     */
    private void guardarListaDetalleSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO> detalles, Long idSolicitudGlobal) {
        logger.debug("Inicio de método guardarListaDetalleSolicitudGestionCobro");
        GuardarListaDetalleSolicitudGestionCobroFisico guardar = new GuardarListaDetalleSolicitudGestionCobroFisico(idSolicitudGlobal,
            detalles);
        guardar.execute();
        logger.debug("Fin de método guardarListaDetalleSolicitudGestionCobro");
    }

    /**
     * Método que invoca el servicio de consulta de integrantes de un grupo de
     * usuarios
     *
     * @param idGrupo Identificador del grupo
     * @param sede    Sede de la CCF
     * @param estado  Estado de los usuarios a consultar
     * @return La lista de usuarios correspondiente
     */
    private List<UsuarioDTO> obtenerMiembrosGrupo(String idGrupo, String sede, EstadoUsuarioEnum estado) {
        logger.debug("Inicia obtenerMiembrosGrupo(String idGrupo, String sede, EstadoUsuarioEnum estado)");
        ObtenerMiembrosGrupo obtenerMiembrosGrupoService = new ObtenerMiembrosGrupo(idGrupo, sede, estado);
        obtenerMiembrosGrupoService.execute();
        List<UsuarioDTO> usuarios = obtenerMiembrosGrupoService.getResult();
        logger.debug("Finaliza distribuirAnalistas(UserDTO user)");
        return usuarios;
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de acciones
     * de cobro
     *
     * @param tipoParametrizacion Tipo de parametrización
     * @return Lista de registros de parametrización de la acción de cobro
     */
    private List<Object> consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        logger.debug("Inicio de método consultarParametrizacionGestionCobro");
        ConsultarParametrizacionGestionCobro service = new ConsultarParametrizacionGestionCobro(tipoParametrizacion);
        service.execute();
        logger.debug("Fin de método consultarParametrizacionGestionCobro");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de asignación de las acciones de cobro
     *
     * @param accionCobro Tipo de acción de cobro
     * @return La lista de aportantes asignados a acciones de cobro
     */
    private List<AportanteAccionCobroDTO> asignarAccionCobro(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método asignarAccionCobro");
        AsignarAccionCobro service = new AsignarAccionCobro(accionCobro);
        service.execute();
        logger.debug("Fin de método asignarAccionCobro");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de asignación de las acciones de cobro
     *
     * @param accionCobro Tipo de acción de cobro
     * @return La lista de aportantes asignados a acciones de cobro
     */
    private List<AportanteAccionCobroDTO> enviarComunicadoH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método asignarAccionCobro");
        EnviarComunicadoH2C2ToF1C6 service = new EnviarComunicadoH2C2ToF1C6(accionCobro);
        service.execute();
        logger.debug("Fin de método asignarAccionCobro");
        return service.getResult();
    }

    private List<AportanteAccionCobroDTO> enviarComunicadoExpulsionH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método asignarAccionCobro Expulsion");
        EnviarComunicadoExpulsionH2C2ToF1C6 service = new EnviarComunicadoExpulsionH2C2ToF1C6(accionCobro);
        service.execute();
        logger.debug("Fin de método asignarAccionCobro Expulsion");
        return service.getResult();
    }

    private List<AportanteAccionCobroDTO> obtenerAportantesParaExpulsionPorIds(TipoAccionCobroEnum accionCobro, List<Long> idPersonasAProcesar){
        logger.debug("Inicio de método obtenerAportantesParaExpulsionPorIds, idPersonasAProcesar.size(): " + idPersonasAProcesar.size());
        ObtenerAportantesParaExpulsionPorIds service = new ObtenerAportantesParaExpulsionPorIds(accionCobro, idPersonasAProcesar);
        service.execute();
        logger.debug("Fin de método obtenerAportantesParaExpulsionPorIds");
        return service.getResult();
    }


    private boolean consultaParametrizacionAnexoLiquidacion(TipoAccionCobroEnum accionCobro) {
        logger.debug("Inicio de método consultaParametrizacionAnexoLiquidacion");
        logger.info("Inicio de método consultaParametrizacionAnexoLiquidacion" + accionCobro);
        ConsultaParametrizacionAnexoLiquidacion service = new ConsultaParametrizacionAnexoLiquidacion(accionCobro);
        service.execute();
        logger.debug("Fin de método consultaParametrizacionAnexoLiquidacion");
        logger.info("Fin de método consultaParametrizacionAnexoLiquidacion: " + service.getResult());
        return service.getResult();
    }

    /**
     * Método que obtiene una lista de correos recibiendo como parametro la
     * parametrización de gestión de cobro y datos identifidores de la persona
     *
     * @param parametrizacion      Parametrización de la gestión de cobro
     * @param tipoIdentificacion   Tipo de identificación de la persona
     * @param numeroIdentificacion Número de identificación de la persona
     * @param lineaCobro           Tipo de línea de cobro
     * @return Retorna la lista de correos a quienes se les debe enviar el
     * comunicado
     */
    private List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatarios(ParametrizacionGestionCobroModeloDTO parametrizacion,
                                                                           TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoLineaCobroEnum lineaCobro) {
        try {
            logger.info("Inicio de método obtenerRolesDestinatarios lineaCobro: "+lineaCobro+"tipoIdentificacion: "+tipoIdentificacion
            + " numeroIdentificacion: "+numeroIdentificacion +" parametrizacion.getTipoParametrizacion: "+parametrizacion.getTipoParametrizacion());
            ObtenerRolesDestinatarios correosService = new ObtenerRolesDestinatarios(lineaCobro, numeroIdentificacion, tipoIdentificacion,
                parametrizacion);
            correosService.execute();
            List<AutorizacionEnvioComunicadoDTO> resultado = correosService.getResult();

            logger.debug("Fin de método obtenerRolesDestinatarios");
            return resultado;
        } catch (Exception e) {
            logger.error("Fin de método obtenerRolesDestinatarios:Error técnico inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Servicio encargado de consultar a una persona por tipo y número de identificación.
     *
     * @param tipoIdentificacion   tipo de identificación.
     * @param numeroIdentificacion número de identificación.
     * @return persona encontrada.
     */
    private PersonaModeloDTO consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicio de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarDatosPersona consultarDatosService = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarDatosService.execute();
        PersonaModeloDTO personaModeloDTO = consultarDatosService.getResult();
        logger.debug("Fin de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return personaModeloDTO;

    }

    /**
     * Método encargado de llamar el cliente del servicio que guarda la bitacora de cartera
     *
     * @param bitacoraCartera, bitacora de cartera a guardar
     */
    private Long guardarBitacoraCartera(BitacoraCarteraDTO bitacoraCartera) {
        logger.debug("Inicio de método guardarBitacoraCartera(BitacoraCarteraDTO)");
        GuardarBitacoraCartera bitacora = new GuardarBitacoraCartera(bitacoraCartera);
        bitacora.execute();
        Long idBitacoraCartera = bitacora.getResult();
        logger.debug("Finaliza método guardarBitacoraCartera(BitacoraCarteraDTO)");
        return idBitacoraCartera;
    }
    
    /**
     * Método encargado de llamar el cliente del servicio que guarda la relacion entre el comunicado y la bitacora cartera
     *
     */
    private void guardarRelacionComunicadoBitacoraCartera(Long idBitacoraCartera, Long idComunicado) {
        logger.info("Inicio de método guardarRelacionComunicadoBitacoraCartera(BitacoraCarteraDTO)");

        GuardarRelacionComunicadoBitacoraCarteraDTO guardarRelacionComunicadoBitacoraCarteraDTO = new GuardarRelacionComunicadoBitacoraCarteraDTO();
        guardarRelacionComunicadoBitacoraCarteraDTO.setIdComunicado(idComunicado);
        guardarRelacionComunicadoBitacoraCarteraDTO.setIdBitacoraCartera(idBitacoraCartera);

        GuardarRelacionComunicadoBitacoraCartera guardarRelacion = new GuardarRelacionComunicadoBitacoraCartera(guardarRelacionComunicadoBitacoraCarteraDTO);
        guardarRelacion.execute();

        logger.info("Finaliza método guardarRelacionComunicadoBitacoraCartera(BitacoraCarteraDTO)");
    }

    /**
     * Obtiene el valor de accionCobro
     *
     * @return El valor de accionCobro
     */
    public TipoAccionCobroEnum getAccionCobro() {
        return accionCobro;
    }

    /**
     * Establece el valor de accionCobro
     *
     * @param accionCobro El valor de accionCobro por asignar
     */
    public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
        this.accionCobro = accionCobro;
    }

    /**
     * Obtiene el valor de tipoParametrizacion
     *
     * @return El valor de tipoParametrizacion
     */
    public TipoParametrizacionGestionCobroEnum getTipoParametrizacion() {
        return tipoParametrizacion;
    }

    /**
     * Establece el valor de tipoParametrizacion
     *
     * @param tipoParametrizacion El valor de tipoParametrizacion por asignar
     */
    public void setTipoParametrizacion(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        this.tipoParametrizacion = tipoParametrizacion;
    }

    /**
     * Obtiene el valor de tipoTransaccionElectronica
     *
     * @return El valor de tipoTransaccionElectronica
     */
    public TipoTransaccionEnum getTipoTransaccionElectronica() {
        return tipoTransaccionElectronica;
    }

    /**
     * Establece el valor de tipoTransaccionElectronica
     *
     * @param tipoTransaccionElectronica El valor de tipoTransaccionElectronica por asignar
     */
    public void setTipoTransaccionElectronica(TipoTransaccionEnum tipoTransaccionElectronica) {
        this.tipoTransaccionElectronica = tipoTransaccionElectronica;
    }

    /**
     * Obtiene el valor de tipoTransaccionFisica
     *
     * @return El valor de tipoTransaccionFisica
     */
    public TipoTransaccionEnum getTipoTransaccionFisica() {
        return tipoTransaccionFisica;
    }

    /**
     * Establece el valor de tipoTransaccionFisica
     *
     * @param tipoTransaccionFisica El valor de tipoTransaccionFisica por asignar
     */
    public void setTipoTransaccionFisica(TipoTransaccionEnum tipoTransaccionFisica) {
        this.tipoTransaccionFisica = tipoTransaccionFisica;
    }

    /**
     * Obtiene el valor de parametrizacion
     *
     * @return El valor de parametrizacion
     */
    public Map<String, Object> getParametrizacion() {
        return parametrizacion;
    }

    /**
     * Establece el valor de parametrizacion
     *
     * @param parametrizacion El valor de parametrizacion por asignar
     */
    public void setParametrizacion(Map<String, Object> parametrizacion) {
        this.parametrizacion = parametrizacion;
    }

    /**
     * Obtiene el valor de userDTO
     *
     * @return El valor de userDTO
     */
    public UserDTO getUserDTO() {
        return userDTO;
    }

    /**
     * Establece el valor de userDTO
     *
     * @param userDTO El valor de userDTO por asignar
     */
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * Obtiene el valor de plantillasComunicado
     *
     * @return El valor de plantillasComunicado
     */
    public List<EtiquetaPlantillaComunicadoEnum> getPlantillasComunicado() {
        return plantillasComunicado;
    }

    /**
     * Establece el valor de plantillasComunicado
     *
     * @param plantillasComunicado El valor de plantillasComunicado por asignar
     */
    public void setPlantillasComunicado(List<EtiquetaPlantillaComunicadoEnum> plantillasComunicado) {
        this.plantillasComunicado = plantillasComunicado;
    }

    /**
     * Obtiene el valor de documentoCarteraDTO
     *
     * @return El valor de documentoCarteraDTO
     */
    public DocumentoCarteraModeloDTO getDocumentoCarteraDTO() {
        return documentoCarteraDTO;
    }

    /**
     * Establece el valor de documentoCarteraDTO
     *
     * @param documentoCarteraDTO El valor de documentoCarteraDTO por asignar
     */
    public void setDocumentoCarteraDTO(DocumentoCarteraModeloDTO documentoCarteraDTO) {
        this.documentoCarteraDTO = documentoCarteraDTO;
    }

    /**
     * Obtiene el valor de usuariosBackEmpleador
     *
     * @return El valor de usuariosBackEmpleador
     */
    public List<UsuarioDTO> getUsuariosBackEmpleador() {
        return usuariosBackEmpleador;
    }

    /**
     * Establece el valor de usuariosBackEmpleador
     *
     * @param usuariosBackEmpleador El valor de usuariosBackEmpleador por asignar
     */
    public void setUsuariosBackEmpleador(List<UsuarioDTO> usuariosBackEmpleador) {
        this.usuariosBackEmpleador = usuariosBackEmpleador;
    }

    /**
     * Obtiene el valor de usuariosBackPersona
     *
     * @return El valor de usuariosBackPersona
     */
    public List<UsuarioDTO> getUsuariosBackPersona() {
        return usuariosBackPersona;
    }

    /**
     * Establece el valor de usuariosBackPersona
     *
     * @param usuariosBackPersona El valor de usuariosBackPersona por asignar
     */
    public void setUsuariosBackPersona(List<UsuarioDTO> usuariosBackPersona) {
        this.usuariosBackPersona = usuariosBackPersona;
    }

    /**
     * Obtiene el valor de usuariosAnalistaAportes
     *
     * @return El valor de usuariosAnalistaAportes
     */
    public List<UsuarioDTO> getUsuariosAnalistaAportes() {
        return usuariosAnalistaAportes;
    }

    /**
     * Establece el valor de usuariosAnalistaAportes
     *
     * @param usuariosAnalistaAportes El valor de usuariosAnalistaAportes por asignar
     */
    public void setUsuariosAnalistaAportes(List<UsuarioDTO> usuariosAnalistaAportes) {
        this.usuariosAnalistaAportes = usuariosAnalistaAportes;
    }

    /**
     * Método que envia un comunicado
     *
     * @param aportanteAccionCobroDTO
     */
    private void enviarComunicadoAsignacion(AportanteAccionCobroDTO aportanteAccionCobroDTO,
                                            List<EtiquetaPlantillaComunicadoEnum> plantillasComunicado,
                                            TipoTransaccionEnum tipoTransaccionEnum) {
        logger.debug("Inicio de método enviarComunicadoAsignacion");
        logger.info("Inicio de método enviarComunicadoAsignacion");
        Long idComunicado = null;
        Boolean liquidacionAportanteBandera = Boolean.FALSE;
        List<DocumentoSoporteModeloDTO> documentosSoporte = new ArrayList<>();
        ResultadoEnvioComunicadoCarteraDTO resultadoEnvioComunicadoCartera = new ResultadoEnvioComunicadoCarteraDTO();
        
        try {
            List<NotificacionParametrizadaDTO> notificaciones = new ArrayList<>();

            for (EtiquetaPlantillaComunicadoEnum plantillaComunicado : plantillasComunicado) { // Genera y envía los comunicados por correo electrónico
                // Genera y guarda el comunicado
                logger.info(plantillaComunicado);
                if (plantillaComunicado.equals(EtiquetaPlantillaComunicadoEnum.LIQ_APO_MOR))
                    liquidacionAportanteBandera = true;
                    logger.info("obtengo Datos : liquidacionAportanteBandera" + liquidacionAportanteBandera);

                logger.debug("Inicia cae siempre :liquidacionAportanteBandera enviarComunicadoAsignacion :: " + liquidacionAportanteBandera);
                AsignacionTemporalComunicado asignacionTemporal = almacenarComunicado(plantillaComunicado, tipoTransaccionEnum,
                    aportanteAccionCobroDTO);
                notificaciones.add(asignacionTemporal.getNotificacionParametrizadaDTO());
                DocumentoSoporteModeloDTO documento = asignacionTemporal.getDocumentoSoporteDTO();
                documentosSoporte.add(documento);
            }

            // Asigna la plantilla a cada notificación en la lista
            for (int i = 0; i < notificaciones.size(); i++) {
                logger.info("=!=!=!=!=!=!=!=");
                logger.info(plantillasComunicado.get(i));
                notificaciones.get(i).setEtiquetaPlantilla(plantillasComunicado.get(i));
            }

            if (!notificaciones.isEmpty()) {
                EnvioExitosoComunicadosCartera envioExitosoComunicadosCartera = new EnvioExitosoComunicadosCartera(notificaciones);
                logger.info("Entra if notificaciones diferente de vacio:: " + notificaciones);
                envioExitosoComunicadosCartera.execute();
                resultadoEnvioComunicadoCartera = envioExitosoComunicadosCartera.getResult();
                logger.info(resultadoEnvioComunicadoCartera.toString());
            }

        } catch (Exception e) {
            logger.error("No se pudo completar correctamente el envío de comunicados, parametros faltantes para el componente de comunicados.", e);
        } finally {
            logger.info("finally aportanteAccionCobroDTO :: " + aportanteAccionCobroDTO.toString() + "tipoTransaccionEnum " + tipoTransaccionEnum + "documentosSoporte " + documentosSoporte + "liquidacionAportanteBandera " + liquidacionAportanteBandera);
            if(resultadoEnvioComunicadoCartera != null){
                idComunicado = resultadoEnvioComunicadoCartera.getIdComunicado() == null ? null : resultadoEnvioComunicadoCartera.getIdComunicado();
            }
            if (resultadoEnvioComunicadoCartera.getEnvioComunicadosResultado()) { // Registro de bitácora: para envío exitoso por medio electrónico, se registra envío y entrega
                logger.info("***____*** entra en if");
                logger.info(aportanteAccionCobroDTO.toString());
                almacenarBitacora(aportanteAccionCobroDTO, tipoTransaccionEnum, ResultadoBitacoraCarteraEnum.ENVIADO,
                    documentosSoporte, liquidacionAportanteBandera, idComunicado);
            } else {
                logger.info("***____*** entra en else");
                logger.info(idComunicado);
                logger.info("finally else aportanteAccionCobroDTO else :: " + aportanteAccionCobroDTO + "tipoTransaccionEnum " + tipoTransaccionEnum + "documentosSoporte " + documentosSoporte + "liquidacionAportanteBandera " + liquidacionAportanteBandera);
                asignarSolicitudElectronica(aportanteAccionCobroDTO);
                almacenarBitacora(aportanteAccionCobroDTO, tipoTransaccionEnum, ResultadoBitacoraCarteraEnum.NO_ENVIADO,
                    documentosSoporte, liquidacionAportanteBandera, idComunicado);
            }

            // Valida si el aportante es candidato a expulsión
            validarCandidatoExpulsion(aportanteAccionCobroDTO.getIdCartera(), aportanteAccionCobroDTO);
        }
        logger.debug("Fin de método enviarComunicadoAsignacion");
    }

    /**
     * Método encargado de realizar el envio del comunicado devolviendo el idComunicado
     * Tener en cuenta, este es un reemplazo del metodo envioExitosoComunicados
     * @param notificaciones
     * @return ResultadoEnvioComunicadoCarteraDTO
     */
    private ResultadoEnvioComunicadoCarteraDTO envioExitosoComunicadosCartera(List<NotificacionParametrizadaDTO> notificaciones) {
        EnvioExitosoComunicadosCartera envioExitosoComunicadosCartera = new EnvioExitosoComunicadosCartera(notificaciones);
        envioExitosoComunicadosCartera.execute();
        return envioExitosoComunicadosCartera.getResult();
    }

    /**
     * Método encargado de invocar el cliente del servicio que realiza el envio de los comunicados
     * ( DEPRECIADO )
     * @param notificaciones
     * @return Si fue exitoso el envío de todos los comunicados
     */
    private Boolean envioExitosoComunicados(List<NotificacionParametrizadaDTO> notificaciones) {
        EnvioExitosoComunicados envioComunicados = new EnvioExitosoComunicados(notificaciones);
        envioComunicados.execute();
        return envioComunicados.getResult();
    }

    /**
     * Metodo que se encarga de invocar el cliente para envío de email masivos
     *
     * @param notificaciones
     */
    private void enviarMultiplesCorreosPorConexion(List<NotificacionParametrizadaDTO> notificaciones) {
        EnviarMultiplesCorreosPorConexion enviar = new EnviarMultiplesCorreosPorConexion(notificaciones);
        enviar.execute();
    }

    /**
     * Método que se encarga de consultar la parametrización definida para la linea de cobro del independiente o pensionado
     *
     * @param tipoLinea
     * @return
     */
    private LineaCobroPersonaModeloDTO consultarLineaCobroPersona(TipoLineaCobroEnum tipoLinea) {
        logger.debug("Inicio método consultarLineaCobroPersona");
        ConsultarLineaCobroPersona lineaCorbroPersona = new ConsultarLineaCobroPersona(tipoLinea);
        lineaCorbroPersona.execute();
        logger.debug("Fin de método consultarLineaCobroPersona");
        return lineaCorbroPersona.getResult();
    }

    private void guardarTiempoProcesoCartera(TiempoProcesoCartera procesoCartera) {
        logger.debug("Inicio método guardarTiempoProcesoCartera");
        GuardarTiempoProcesoCartera guardarTiempoProceso = new GuardarTiempoProcesoCartera(procesoCartera);
        guardarTiempoProceso.execute();
        logger.debug("Fin de método guardarTiempoProcesoCartera");
    }

    public String consultarActividadCarIdNumeroIdentificacion(Long carId, String perNumeroIdentificacion) {
        ConsultarActividadCarIdNumeroIdentificacion service = new ConsultarActividadCarIdNumeroIdentificacion(carId, perNumeroIdentificacion);
        service.execute();
        return service.getResult();
    }

    public List<Long> getIdPersonasAProcesar() {
        return idPersonasAProcesar;
    }

    public void setIdPersonasAProcesar(List<Long> idPersonasAProcesar) {
        this.idPersonasAProcesar = idPersonasAProcesar;
    }
}

class AsignacionTemporalComunicado {

    /**
     * Contiene la notificacion construida para su envío
     */
    private NotificacionParametrizadaDTO notificacionParametrizadaDTO;

    /**
     * Datos del documento soporte
     */
    private DocumentoSoporteModeloDTO documentoSoporteDTO;

    /**
     * @param notificacionParametrizadaDTO
     * @param documentoSoporteDTO
     */
    public AsignacionTemporalComunicado(NotificacionParametrizadaDTO notificacionParametrizadaDTO,
                                        DocumentoSoporteModeloDTO documentoSoporteDTO) {
        super();
        this.notificacionParametrizadaDTO = notificacionParametrizadaDTO;
        this.documentoSoporteDTO = documentoSoporteDTO;
    }

    /**
     * @return the notificacionParametrizadaDTO
     */
    public NotificacionParametrizadaDTO getNotificacionParametrizadaDTO() {
        return notificacionParametrizadaDTO;
    }

    /**
     * @param notificacionParametrizadaDTO the notificacionParametrizadaDTO to set
     */
    public void setNotificacionParametrizadaDTO(NotificacionParametrizadaDTO notificacionParametrizadaDTO) {
        this.notificacionParametrizadaDTO = notificacionParametrizadaDTO;
    }

    /**
     * @return the documentoSoporteDTO
     */
    public DocumentoSoporteModeloDTO getDocumentoSoporteDTO() {
        return documentoSoporteDTO;
    }

    /**
     * @param documentoSoporteDTO the documentoSoporteDTO to set
     */
    public void setDocumentoSoporteDTO(DocumentoSoporteModeloDTO documentoSoporteDTO) {
        this.documentoSoporteDTO = documentoSoporteDTO;
    }


}
