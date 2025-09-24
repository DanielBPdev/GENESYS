package com.asopagos.comunicados.ejb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import com.asopagos.cache.CacheManager;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.service.PlantillasService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.fovis.clients.ConsultarCicloAsignacion;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.campos.encabezadosEnum.EnumEncabezados;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de plantillas de comunicados<br/>
 * <b>Módulo:</b> Asopagos - HU
 *
 * @author <a href="mailto:jerodriguez@heinsohn.com.co"> jerodriguez</a>
 */
@Stateless
public class PlantillasBusiness implements PlantillasService {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PlantillasBusiness.class);

    @PersistenceContext(unitName = "comunicados_PU")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "certificados_PU")
    private EntityManager entityManagerReporte;

    @Resource
    private ManagedExecutorService managedExecutorService;

    /**
     * <b>Descripción</b>Método que se encarga de actualizar la informacion de una plantilla<br/>
     * <code>plantillaComunicado contiene la información que se va a actualizar, idPlantillaComunicado es
     * el id de la PlantillaComunicado que se va a Actualizar</code>
     *
     * @param plantillaComunicado Es la infomracion de la plantilla que se va a actualizar
     * @param etiquetaPlantilla   Etiqueta de la PlantillaComunicado a Actualizar
     */
    @Override
    public void actualizarPlantillaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantilla, PlantillaComunicado plantillaComunicado) {
        logger.debug("Inicializa actualizarPlantillaComunicado(Long, PlantillaComunicado)");
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO).setParameter("etiqueta", etiquetaPlantilla)
                    .getSingleResult();

            plantillaComunicado.setEtiqueta(etiquetaPlantilla);
            entityManager.merge(plantillaComunicado);

        } catch (NoResultException nre) {
            logger.debug("Finaliza actualizarPlantillaComunicado(Long, PlantillaComunicado)");
            throw null;
        }
        logger.debug("Finaliza actualizarPlantillaComunicado(Long, PlantillaComunicado)");
    }

    /**
     * <b>Descripción</b>Método encargado de consultar una plantilla de comunicado<br/>
     * <code>idPlantillaComunicado es el id de la plantilla</code>
     *
     * @param etiquetaPlantilla Itiqueta de la plantilla a consultar
     * @return plantilla de tipo PlantillaComunicado
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PlantillaComunicado consultarPlantillaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantilla) {
        // Se consulta sobre la tabla PlantillaComunicado con etiquetaPlantillaComunicadoEnum 
        // y se recupera idPlantillaComunicado y nombreQuery
        return (PlantillaComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO)
                .setParameter("etiqueta", etiquetaPlantilla).getSingleResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.comunicados.service.PlantillasService#resolverVariablesComunicado(com.asopagos.enumeraciones.comunicados.
     * EtiquetaPlantillaComunicadoEnum, java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, Object> resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                           Long idInstancia, Long idSolicitud, String ordenamiento) {
        return resolverVariablesComunicadoLogica(etiquetaPlantillaComunicadoEnum, null, idInstancia, idSolicitud, null, ordenamiento);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, Object> resolverVariablesComunicadoIdPlantilla(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                      Long idPlantillaComunicado, Long idInstancia, Long idSolicitud, String ordenamiento) {
        return resolverVariablesComunicadoLogica(etiquetaPlantillaComunicadoEnum, idPlantillaComunicado, idInstancia, idSolicitud, null, ordenamiento);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlantillasService#resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum,
     * Long, ParametrosComunicadoDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, Object> resolverVariablesComunicadoParametros(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                     Long idSolicitud, ParametrosComunicadoDTO parametrosComunicadoDTO) {
        return resolverVariablesComunicadoLogica(etiquetaPlantillaComunicadoEnum, null, null, idSolicitud, parametrosComunicadoDTO, null);
    }

    /**
     * <b>Descripción</b>Método encargado de resolver las variables del comunicado<br/>
     *
     * @param etiquetaPlantillaComunicadoEnum, Id de la plantilla a consultar
     * @param valor,                           valor para consultar sobre la tabla varibleComunicado
     * @return Map<String, Object> objeto Map con clave por objecto(registro)
     */
    private Map<String, Object>     resolverVariablesComunicadoLogica(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                  Long idPlantillaComunicado, Long idInstancia, Long idSolicitud, ParametrosComunicadoDTO parametrosComunicadoDTO, String ordenamiento) {

        String nombreMetodo = "resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum, Long, Long, Long, ParametrosComunicadoDTO, String)";
        logger.info("Inicia " + nombreMetodo);

        // Se valida que los valores idPlantillaComunicado y valorIdInstancia no sean nulos
        if (etiquetaPlantillaComunicadoEnum == null) {
            logger.info("Finaliza resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum, Long, Long, Long, ParametrosComunicadoDTO, String): Parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        logger.info("al servicio resolverVariablesComunicado llegaron los siguientes datos (EtiquetaPlantillaComunicadoEnum: " + etiquetaPlantillaComunicadoEnum.name() + ", idPlantillaComunicado: " + idPlantillaComunicado + ", idInstancia: " + idInstancia + ", idSolicitud: " + idSolicitud + ", parametrosComunicado: " + (parametrosComunicadoDTO != null ? "llegaron parametros" : "no llegaron parametros") + ", ordenamiento: " + ordenamiento + ")");

        DecimalFormat format = new DecimalFormat("###.##");

        // Mapa que contendrá los resultados a retornar 
        Map<String, Object> valoresVariable = new HashMap<String, Object>();

        // Mapa que contendrá los valores resueltos de las variables consultadas por idSolicitud o por idInstanciaProceso
        Map<String, Object> valorVariableComunicadoMap = new HashMap<String, Object>();

        if (parametrosComunicadoDTO != null && parametrosComunicadoDTO.getIdPlantillaComunicado() != null) {
            idPlantillaComunicado = parametrosComunicadoDTO.getIdPlantillaComunicado();
        }

        // plantilla del comunicado que se desea resolver
        if (idPlantillaComunicado == null) {
            PlantillaComunicado plantilla = null;
            try {
                // Se consulta sobre la tabla PlantillaComunicado con etiquetaPlantillaComunicadoEnum 
                // y se recupera idPlantillaComunicado y nombreQuery
                plantilla = (PlantillaComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO)
                        .setParameter("etiqueta", etiquetaPlantillaComunicadoEnum).getSingleResult();
            } catch (NoResultException e) {
                logger.info("Finaliza " + nombreMetodo + " : error no existe el comunicado");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
            } catch (NonUniqueResultException nure) {
                logger.info(
                        "Finaliza " + nombreMetodo + " : Existe más de un comunicado para la etiqueta");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
            }
            idPlantillaComunicado = plantilla.getIdPlantillaComunicado();
        }

        // Se consulta las variables sobre la tabla VariableComunicado
        AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteConsolidadoCarteraSingleton = AuxiliarReporteConsolidadoCarteraSingleton.getSingletonInstance();
        List<VariableComunicado> variablesComunicado = auxiliarReporteConsolidadoCarteraSingleton.obtenerVariablesComunicado(idPlantillaComunicado, entityManager);

        logger.info("variablesComunicado " +variablesComunicado);
        for(VariableComunicado variableComunicado :  variablesComunicado){
            logger.info("variableComunicado inicial --> " +variableComunicado.toString());
        }

        if (variablesComunicado != null && !variablesComunicado.isEmpty()) {

            String nombreVariable;
            String nombreUsuario;
            UsuarioCCF usuarioDTO;

            // se inicializan el valor de todas las variableComunicado que sean constantes, 
            // en caso de ser de tipo variable se inicializa en nulo
            for (VariableComunicado variableComunicado : variablesComunicado) {
                nombreVariable = variableComunicado.getClave();
                switch (variableComunicado.getTipoVariableComunicado()) {
                    case CONSTANTE:
                        valoresVariable.put(nombreVariable, obtenerValorConstante(variableComunicado.getNombreConstante()));
                        break;
                    case USUARIO_CONSTANTE:
                        nombreUsuario = (String) obtenerValorConstante(variableComunicado.getNombreConstante());
                        if (nombreUsuario != null) {
                            usuarioDTO = obtenerDatoUsuario(nombreVariable, nombreUsuario);
                            nombreUsuario = serializarValorUsuaurio(variableComunicado, usuarioDTO);
                        }
                        valoresVariable.put(nombreVariable, nombreUsuario);
                        break;
                    default:
                        valoresVariable.put(nombreVariable, null);
                }
            }

            // se inicializan los datos
            List<Object[]> resultadoValoresVariables = new ArrayList<Object[]>();
            if (idInstancia != null || idSolicitud != null && (
            etiquetaPlantillaComunicadoEnum != EtiquetaPlantillaComunicadoEnum.COM_SUB_PRE_PAG_TRA 
            || etiquetaPlantillaComunicadoEnum != EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_UNO_PRE_PAG_TRA 
            || etiquetaPlantillaComunicadoEnum != EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_DOS_PRE_PAG_TRA  
            || etiquetaPlantillaComunicadoEnum != EtiquetaPlantillaComunicadoEnum.COM_SUB_AVISO_TRES_PRE_PAG_TRA)) {


                if (idInstancia != null) {
                    // El id solicitud solo llega en caso de novedades por que no se tiene proceso BPM
                    // por ende al recibir una instancia de proceso se busca el id de la solicitud por 
                    // la instancia de proceso, para normalizar la resolución de las variables únicamente
                    // por id de la solicitud
                    try {
                        Solicitud solicitud = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD, Solicitud.class)
                                .setParameter("idInstancia", idInstancia.toString()).getSingleResult();
                        idSolicitud = solicitud.getIdSolicitud();
                    } catch (NoResultException e) {
                        logger.info("Finaliza " + nombreMetodo + " : error no existe uan solicitud asociada al id de instancia proceso");
                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
                    }
                }
            }
            Boolean noObtenerConsulta = false;
            if (parametrosComunicadoDTO != null && parametrosComunicadoDTO.getParams() != null
                    && parametrosComunicadoDTO.getParams().containsKey("obtenerConsulta")) {
                noObtenerConsulta = true;
            }

            if ((idInstancia != null || idSolicitud != null || (parametrosComunicadoDTO != null && !noObtenerConsulta))) {
                resultadoValoresVariables = obtenerResultadoVariables(parametrosComunicadoDTO,
                        etiquetaPlantillaComunicadoEnum.getNombreQuery(), idSolicitud);
            }

            Object valorVariable;
            StringBuilder valorLista = new StringBuilder();
            StringBuilder key = new StringBuilder();

            if (resultadoValoresVariables == null || resultadoValoresVariables.isEmpty()) {
                logger.info("Finaliza " + nombreMetodo);
                return valoresVariable;
            }

            try {

                for (Object[] fila : resultadoValoresVariables) {

                    for (int i = 0; i < fila.length; i = i + 2) {
                        key.setLength(0);
                        key.append(ConstantesComunicado.VARIABLE_ABRIR);
                        key.append((String) fila[i]);
                        key.append(ConstantesComunicado.VARIABLE_CERRAR);
                        valorVariableComunicadoMap.put(key.toString(), fila[i + 1]);
                    }

                    // se resuelven las variables de la plantilla con la información disponible 
                    for (VariableComunicado variableComunicado : variablesComunicado) {
                        nombreVariable = variableComunicado.getClave();
                        valorVariable = null;
                        if (valorVariableComunicadoMap.containsKey(nombreVariable)) {
                            valorVariable = valorVariableComunicadoMap.get(nombreVariable);
                        }

                        // en caso de tratarse de una constante, se omite pues ya fue inicializado 
                        switch (variableComunicado.getTipoVariableComunicado()) {
                            case CONSTANTE:
                                //valoresVariable.put(nombreVariable, obtenerValorConstante(variableComunicado.getNombreConstante()));
                                String valorConstante = (String) obtenerValorConstante(variableComunicado.getNombreConstante());
    
                                if (nombreVariable.equals("${nombreCcf}") || nombreVariable.equals("${ciudadSolicitud}")) {
                                    valorConstante = capitalizar(valorConstante); 
                                }
                                
                                valoresVariable.put(nombreVariable, valorConstante);
                                break;
                            case USUARIO_CONSTANTE:
                                nombreUsuario = (String) obtenerValorConstante(variableComunicado.getNombreConstante());
                                if (nombreUsuario != null) {
                                    usuarioDTO = obtenerDatoUsuario(nombreVariable, nombreUsuario);
                                    nombreUsuario = serializarValorUsuaurio(variableComunicado, usuarioDTO);
                                }
                                valoresVariable.put(nombreVariable, nombreUsuario);
                                break;
                            case VARIABLE:
                                Set<String> clavesACapitalizar = new HashSet<>(Arrays.asList(
                                    "${cotizantes}", "${nombreArchivo}", "${nombreBanco}", "${nombreBeneficiarioOTrabajador}", 
                                    "${nombreCompleto}", "${nombreCompletoAdmin}", "${nombreComunicado}", "${nombreDelAdministradorDelSubsidio}", 
                                    "${nombreDelComunicado}", "${nombreDelTrabajador/Conyuge}", "${nombreDelTrabajador}", 
                                    "${nombreEntidadPagadora}", "${nombreFallecido}", "${nombreRazonSocial}", 
                                    "${nombreRazonSocialEmpleador}", "${nombresYApellidosDelAfiliadoPrincipal}", 
                                    "${nombresYApellidosDelJefeDelHogar}", "${nombreUsuario}", "${nombreUsuarioNotificacion}", 
                                    "${nombreUsuarioRemitente}", "${nombreYApellidosAfiliadoPrincipal}", "${nombreYApellidosRepresentanteLegal}", 
                                    "${razonSocial/Nombre}", "${razonSocial/NombreEntidadPagadora}", "${representanteLegal}", 
                                    "${sede}", "${nombreCcf}", "${cargoResponsable1}", "${cargoResponsable2}", "${cargoResponsable3}", 
                                    "${nombresYApellidosResponsable1}", "${nombresYApellidosResponsable2}", "${nombresYApellidosResponsable3}"
                                ));
                                if (valorVariable instanceof Number) {
                                    valorVariable = format.format(valorVariable);
                                }
                                if (clavesACapitalizar.contains(nombreVariable)) {
                                    valorVariable = capitalizar(valorVariable != null ? valorVariable.toString() : null);
                                }
                                valoresVariable.put(nombreVariable, valorVariable);
                                break;
                            case LISTA_VARIABLE:
                                valorLista.setLength(0);
                                
                                // Capitalizar claves específicas
                                if (valoresVariable.containsKey(nombreVariable)) {
                                    Object valorObj = valoresVariable.get(nombreVariable);
                                    if (valorObj != null) {
                                        // Convertir a String usando toString() si no es una instancia de String
                                        String valorAnterior = valorObj instanceof String 
                                            ? (String) valorObj 
                                            : valorObj.toString();
                                        
                                        // Capitalizar solo si la clave es una de las específicas
                                        if (nombreVariable.equals("${nombreDelBeneficiario}") || 
                                            nombreVariable.equals("${nombresYApellidosDelBeneficiario}")) {
                                            valorAnterior = capitalizar(valorAnterior);
                                        }
                                        
                                        valorLista.append(valorAnterior);
                                        valorLista.append(ConstantesComunicado.SALTO_LINEA);
                                    }
                                }
                                
                                // Asegurarse de que valorVariable es un String antes de capitalizar
                                if (valorVariable != null) {
                                    // Convertir valorVariable a String usando toString() si es necesario
                                    String valorVariableString = valorVariable instanceof String
                                        ? (String) valorVariable
                                        : valorVariable.toString();
                                    
                                    // Capitalizar solo si la clave es una de las específicas
                                    if (nombreVariable.equals("${nombreDelBeneficiario}") || 
                                        nombreVariable.equals("${nombresYApellidosDelBeneficiario}")) {
                                        valorVariableString = capitalizar(valorVariableString);
                                    }
                                    
                                    valorLista.append(valorVariableString);
                                }
                                
                                valoresVariable.put(nombreVariable, valorLista.toString());
                                break;
                            case USUARIO_VARIABLE:
                                nombreUsuario = (String) valorVariable;
                                if (nombreUsuario != null) {
                                    usuarioDTO = obtenerDatoUsuario(nombreVariable, nombreUsuario);
                                    nombreUsuario = serializarValorUsuaurio(variableComunicado, usuarioDTO);
                                    
                                    // Capitalizar solo si la clave es una de las específicas
                                    if (nombreVariable.equals("${nombreDelAdministradorDelSubsidio}") || 
                                        nombreVariable.equals("${nombreDelUsuario}")) {
                                        nombreUsuario = capitalizar(nombreUsuario);
                                    }
                                }
                                valoresVariable.put(nombreVariable, nombreUsuario);
                                break;

                            case REPORTE_VARIABLE:
                                //logger.info("KEY_MAP_ID_CARTERA");
                                //logger.info(parametrosComunicadoDTO.getIdCartera());
                                ConsultaReporteComunicadosAbs claseReporte = (ConsultaReporteComunicadosAbs) Class
                                        .forName((String) valorVariable).newInstance();
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, idSolicitud);
                                if (parametrosComunicadoDTO != null) {
                                    if (parametrosComunicadoDTO.getIdsSolicitud() != null
                                            && !parametrosComunicadoDTO.getIdsSolicitud().isEmpty()) {
                                        params.put(ConstantesComunicado.KEY_MAP_IDES_SOLICITUD,
                                                parametrosComunicadoDTO.getIdsSolicitud());
                                    }
                                    if (parametrosComunicadoDTO.getNumeroIdentificacion() != null
                                            && parametrosComunicadoDTO.getTipoIdentificacion() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION,
                                                parametrosComunicadoDTO.getNumeroIdentificacion());
                                        params.put(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION,
                                                parametrosComunicadoDTO.getTipoIdentificacion());
                                    }
                                    if (parametrosComunicadoDTO.getIdCartera() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_ID_CARTERA, parametrosComunicadoDTO.getIdCartera());
                                    }
                                    if (parametrosComunicadoDTO.getIdentificadorRespuesta() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_ID_RESPUESTA,
                                                parametrosComunicadoDTO.getIdentificadorRespuesta());
                                    }
                                    if (parametrosComunicadoDTO.getIdTransaccionTerceroPagador() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR,
                                                parametrosComunicadoDTO.getIdTransaccionTerceroPagador());
                                    }
                                    if (valorVariableComunicadoMap
                                            .containsKey(ConstantesComunicado.KEY_MAP_USUARIO_TRANSACCION_PAGOS)) {
                                        params.put(ConstantesComunicado.KEY_MAP_USUARIO_TRANSACCION_PAGOS,
                                                valorVariableComunicadoMap.get(ConstantesComunicado.KEY_MAP_USUARIO_TRANSACCION_PAGOS));
                                    }
                                    if (parametrosComunicadoDTO.getIdArchivoConsumosAnibol() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL,
                                                parametrosComunicadoDTO.getIdArchivoConsumosAnibol());
                                    }
                                    if (parametrosComunicadoDTO.getNumeroRadicacion() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_NUMERO_RADICACION,
                                                parametrosComunicadoDTO.getNumeroRadicacion());
                                    }
                                    if (parametrosComunicadoDTO.getIdPersona() != null) {
                                        params.put(ConstantesComunicado.KEY_MAP_ID_PERSONA,
                                                parametrosComunicadoDTO.getIdPersona());
                                    }
                                }
                                if (ordenamiento != null) {
                                    params.put("ordenamiento", ordenamiento);
                                }
                                claseReporte.init(params);
                                String reporte;
                                if (claseReporte instanceof ConsultaTablaComunicado145
                                        || claseReporte instanceof ObtenerSumatoriaAportes) {
                                    reporte = claseReporte.getCertificado(entityManager, entityManagerReporte);
                                } else if (claseReporte instanceof ReporteComunicadoConsolidadoCartera) {
                                    reporte = claseReporte.getReporte(entityManager, managedExecutorService);
                                } else {
                                    reporte = claseReporte.getReporte(entityManager);
                                }
                                // Capitalización si corresponde
                                Set<String> clavesNuevas = new HashSet<>(Arrays.asList(
                                    "${tablaHogar}", "${tabla1}", "${tabla2}", "${tabla3}"
                                ));

                                if (clavesNuevas.contains(nombreVariable)) {
                                    reporte = capitalizar(reporte);
                                }
                                valoresVariable.put(nombreVariable, reporte);
                                break;
                            case FECHA_LARGA:
                                if (valorVariable instanceof Date) {
                                    Date fecha = (Date) valorVariable;
                                    if (fecha != null) {
                                        try {
                                            // Convertir la fecha a un formato específico
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                                            LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                            valorVariable = localDate.format(formatter);
                                        } catch (Exception e) {
                                            valorVariable = "Error al convertir la fecha";
                                        }
                                    } else {
                                        valorVariable = "Fecha no disponible";
                                    }
                                } else if (valorVariable instanceof String) {
                                    String fechaStr = (String) valorVariable;
                                    /*
                                    logger.info("valorVariable 1.0 " + valorVariable);
                                    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es")); // Patrón para el formato
                                    LocalDate fecha = LocalDate.parse(fechaStr, formatoFecha); // Convierte String a LocalDate
                                    System.out.println("Fecha: " + fecha); // Muestra la fecha
                                    */
                                } else {
                                    valorVariable = "Formato de fecha no válido";
                                }
                                valoresVariable.put(nombreVariable, valorVariable);
                                break;
                            case LUGAR_MAYUS:
                                if (valorVariable instanceof String) {
                                    String lugar = (String) valorVariable;
                                    if (lugar != null && !lugar.isEmpty()) {
                                        // Convertir el primer carácter a mayúscula y los siguientes a minúscula
                                        valorVariable = lugar.substring(0, 1).toUpperCase() + lugar.substring(1).toLowerCase();
                                    } else {
                                        // Si el lugar es null o vacío, podrías asignar un valor por defecto o manejarlo de alguna otra manera
                                        valorVariable = "Lugar no disponible";
                                    }
                                }
                                valoresVariable.put(nombreVariable, valorVariable);
                                break;
                            default:
                                break;
                        }
                    }
                }

            } catch (IndexOutOfBoundsException iobe) {
                logger.debug("Finaliza " + nombreMetodo + " : la consulta de las variables no cumple con las columnas esperadas");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            
        }

        auxiliarReporteConsolidadoCarteraSingleton.destruir();

        logger.debug("Finaliza " + nombreMetodo);
        return valoresVariable;
    }

    /**
     * Capitaliza la primera letra de una cadena, haciendo que el resto de las letras estén en minúscula.
     *
     * @param texto El texto a formatear.
     * @return El texto con la primera letra en mayúscula y el resto en minúscula.
     */
    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;  // Retorna el texto tal cual si está vacío o es null
        }
        logger.info("Texto antes de capitalizar: " + texto);

        // Dividir el texto en palabras usando espacios como delimitador
        String[] palabras = texto.toLowerCase().split(" ");
        StringBuilder textoCapitalizado = new StringBuilder();

        // Capitalizar la primera letra de cada palabra
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                textoCapitalizado.append(palabra.substring(0, 1).toUpperCase())
                                .append(palabra.substring(1)).append(" ");
            }
        }

        // Eliminar el último espacio extra al final y retornar el texto capitalizado
        String resultado = textoCapitalizado.toString().trim();
        logger.info("Texto después de capitalizar: " + resultado); 
        return resultado;
    }

    /**
     * <b>Descripción</b>Método encargado de generar el map agregando el orden para los encabezados<br/>
     *
     * @param valoresVariable
     * @param variablesComunicado
     * @return
     */
    private Map<String, Object> obtenerOrdenEncabezados(Map<String, Object> valoresVariable, List<VariableComunicado> variablesComunicado,
                                                        long idSolicitud, String etiqueta) {
        Map<String, Object> responseOrdenEncabezados = new HashMap<String, Object>();
        Map<String, Object> responseOrdenEncabezadosTemp = new HashMap<String, Object>();
        VariableComunicado variables;
        Object encabezadosDtoTemp = null;
        boolean consultaDatosTemporales = false;
        try {
            if (EnumEncabezados.fovis.valueOf(etiqueta) != null) {
                consultaDatosTemporales = true;
                encabezadosDtoTemp = EnumEncabezados.fovis.valueOf(etiqueta).getDto();
            }
        } catch (Exception e) {
            logger.debug("No Cosulta EN Datos Temporales");
        }
        if (consultaDatosTemporales) {
            valoresVariable = consultaTablaTemporal(valoresVariable, variablesComunicado, idSolicitud, encabezadosDtoTemp);
        }
        for (int x = 0; x < valoresVariable.size(); x++) {
            responseOrdenEncabezadosTemp = new HashMap<String, Object>();
            variables = variablesComunicado.get(x);
            String nombreCampo = variables.getClave().replace(ConstantesComunicado.VARIABLE_ABRIR, ConstantesComunicado.VACIO)
                    .replace(ConstantesComunicado.VARIABLE_CERRAR, ConstantesComunicado.VACIO);
            responseOrdenEncabezadosTemp.put(ConstantesComunicado.VALUE, valoresVariable.get(variables.getClave()));
            responseOrdenEncabezadosTemp.put(ConstantesComunicado.ORDER, variables.getOrden());
            responseOrdenEncabezadosTemp.put(ConstantesComunicado.NAME, nombreCampo);
            responseOrdenEncabezados.put(nombreCampo, responseOrdenEncabezadosTemp);
        }
        return responseOrdenEncabezados;
    }

    /**
     * <b> Método encargado de agregar los valores al map de los encabezados apartir
     * del mapa retornado por la invocacion a la tabla temporal</b>
     *
     * @param valoresVariable
     * @param variablesComunicado
     * @param idSolicitud
     * @return
     */
    private Map<String, Object> consultaTablaTemporal(Map<String, Object> valoresVariable, List<VariableComunicado> variablesComunicado,
                                                      long idSolicitud, Object dto) {
        try {
            int contValoresEncabezadosNull = 0;
            VariableComunicado variables;
            for (int x = 0; x < valoresVariable.size(); x++) {
                variables = variablesComunicado.get(x);
                if (valoresVariable.get(variables.getClave()) == null) {
                    contValoresEncabezadosNull++;
                }
            }
            if (valoresVariable.size() == contValoresEncabezadosNull) {
                Map<String, Object> mapResponseFovis = encabezadoTempFovis(idSolicitud, dto);
                Iterator<Entry<String, Object>> it = mapResponseFovis.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, Object> e = it.next();
                    if (valoresVariable.containsKey(e.getKey())) {
                        valoresVariable.put(e.getKey(), e.getValue());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrio un errro en consultarDevolucionTemporal(Long idSolicitud)", e);
        }
        return valoresVariable;
    }

    /**
     * <b>Descripción</b>Método encargado de recuperar los datos del usuario<br/>
     *
     * @param clave,         clave que indica que dato del usuario recuperar
     * @param nombreUsuario, nombre del usuario al que se le recuperarán datos
     * @return String, dato del usuario a retornar
     */
    private UsuarioCCF obtenerDatoUsuario(String clave, String nombreUsuario) {
        logger.debug("Inicia obtenerDatoUsuario(String, String)");
        try {
            UsuarioCCF usuarioDTO = new UsuarioCCF();
            ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacion = new ObtenerDatosUsuarioCajaCompensacion(
                    nombreUsuario, null, null, false);
            obtenerDatosUsuariosCajaCompensacion.execute();
            usuarioDTO = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacion.getResult();
            logger.debug("Finaliza obtenerDatoUsuario(String, String)");
            return usuarioDTO;
        } catch (TechnicalException te) {
            logger.debug("Finaliza obtenerDatoUsuario(String, String): error inesperado");
        }
        return null;
    }

    /**
     * Método que retorna el valor de una constante ya sea que se encuentre en la tabla
     * de constantes o de parametros
     *
     * @param nombreConstante
     * @return
     */
    private Object obtenerValorConstante(String nombreConstante) {
        logger.debug("Inicia obtenerValorConstante(String)");
        Object valor = CacheManager.getParametro(nombreConstante);
        if (valor == null) {
            valor = CacheManager.getConstante(nombreConstante);
        }
        logger.debug("Finaliza obtenerValorConstante(String)");
        return valor;
    }

    /**
     * Método que tiene la lógica que determina el valor que debe ser obtenido del usuario para resolver la variable
     *
     * @param variableComunicado
     * @param usuarioDTO
     * @return
     */
    private String serializarValorUsuaurio(VariableComunicado variableComunicado, UsuarioCCF usuarioDTO) {
        logger.debug("Inicia serializarValorUsuaurio(VariableComunicado, UsuarioCCF)");
        if (usuarioDTO != null) {
            StringBuilder nombreUsuario = new StringBuilder();
            if (usuarioDTO.getPrimerNombre() != null) {
                nombreUsuario.append(usuarioDTO.getPrimerNombre());
            }
            if (usuarioDTO.getSegundoNombre() != null) {
                if (nombreUsuario.length() > 0) {
                    nombreUsuario.append(ConstantesComunicado.SPACE);
                }
                nombreUsuario.append(usuarioDTO.getSegundoNombre());
            }
            if (usuarioDTO.getPrimerApellido() != null) {
                if (nombreUsuario.length() > 0) {
                    nombreUsuario.append(ConstantesComunicado.SPACE);
                }
                nombreUsuario.append(usuarioDTO.getPrimerApellido());
            }
            if (usuarioDTO.getSegundoApellido() != null) {
                if (nombreUsuario.length() > 0) {
                    nombreUsuario.append(ConstantesComunicado.SPACE);
                }
                nombreUsuario.append(usuarioDTO.getSegundoApellido());
            }

            if (usuarioDTO.getDependencia() != null) {
                if (nombreUsuario.length() > 0) {
                    nombreUsuario.append(ConstantesComunicado.SALTO_LINEA);
                }
                //nombreUsuario.append(usuarioDTO.getDependencia());
            }

            if (nombreUsuario.length() > 0) {
                logger.debug("Inicia serializarValorUsuaurio(VariableComunicado, UsuarioCCF)");
                return nombreUsuario.toString();
            }
        }
        logger.debug("Inicia serializarValorUsuaurio(VariableComunicado, UsuarioCCF)");
        return "";
    }

    /**
     * <b>Descripción</b>Método encargado de resolver las variables del encabezado<br/>
     *
     * @param etiquetaPlantillaComunicadoEnum, Id de la plantilla a consultar
     * @param idSolicitud,                     valor para consultar sobre la tabla varibleComunicado
     * @return Map<String, Object> objeto Map con clave por objecto(registro)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, Object> resolverVariablesEncabezado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                           Long idSolicitud) {
        //Obtenemos Los Valores De Las Variables De Encabezados
        Map<String, Object> valoresVariable = resolverVariablesComunicado(etiquetaPlantillaComunicadoEnum, null, idSolicitud, null);
        Map<String, Object> responseVariablesEncabezado = new HashMap<>();
        // plantilla del encabezado que se desea resolver
        PlantillaComunicado plantilla = null;
        try {
            // Se obtiene la plantilla de encabezados que se quiere resolver
            plantilla = (PlantillaComunicado) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PLANTILLA_COMUNICADO)
                    .setParameter(ConstantesComunicado.ETIQUETA, etiquetaPlantillaComunicadoEnum).getSingleResult();
        } catch (Exception e) {
            logger.debug("Error Al Aconsultar La Plantilla: " + e.getMessage());
            e.printStackTrace();
        }
        //Se obtiene el idPlantillaEnzabeado
        if (plantilla != null) {
            Long idPlantillaEnzabeado = plantilla.getIdPlantillaComunicado();

            //Se obtiene las variables del encabezado
            List<VariableComunicado> variablesEncabezado = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLES_COMUNICADO, VariableComunicado.class)
                    .setParameter(ConstantesComunicado.ID_PLANTILLA_COMUNICADO, idPlantillaEnzabeado).getResultList();
            logger.debug("Finaliza resolverVariablesEncabezado(EtiquetaPlantillaComunicadoEnum, Long)");
            //Obtenemos el orden de los encabezados 
            responseVariablesEncabezado = obtenerOrdenEncabezados(valoresVariable, variablesEncabezado, idSolicitud,
                    etiquetaPlantillaComunicadoEnum.name());
        }
        return responseVariablesEncabezado;
    }

    /**
     * Método encargado de generar el dto de postulaiconFovis a partir de json
     *
     * @param idSolicitud
     * @return
     */
    public Object consultaTablaTemporal(Long idSolicitud, Object dto) {
        logger.debug("Inicio de método consultarDevolucionTemporal(Long idSolicitud)");
        Object response;
        try {
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            response = mapper.readValue(jsonPayload, dto.getClass());
            logger.debug("Fin de método consultarDevolucionTemporal(Long idSolicitud)");
        } catch (Exception e) {
            logger.error("Ocurrio un error en consultarDevolucionTemporal(Long idSolicitud)", e);
            response = null;
        }
        return response;
    }

    /**
     * Método que invoca el servicio que consulta los datos temporales.
     *
     * @param idSolicitud id de la solicitud global.
     * @return jsonPayload con los datos temporales.
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        logger.debug("Inicio de método consultarDatosTemporales(Long idSolicitud)");
        ConsultarDatosTemporales consultarDatosNovedad = new ConsultarDatosTemporales(idSolicitud);
        consultarDatosNovedad.execute();
        logger.debug("Fin de método consultarDatosTemporales(Long idSolicitud)");
        return consultarDatosNovedad.getResult();
    }

    /**
     * Método encargado de generar un map apartir de un dto para los encabezados de postulacion fovis
     *
     * @param idSolicitud
     * @return
     */
    private Map<String, Object> encabezadoTempFovis(long idSolicitud, Object dto) {
        Map<String, Object> valoresTempFovis = new HashMap<>();
        CicloAsignacionModeloDTO cicloAsignacion = null;
        SolicitudPostulacionFOVISDTO asignacionFovisTempDto = null;
        SolicitudLegalizacionDesembolsoDTO solicitudLegalTempDto = null;
        if (dto instanceof SolicitudPostulacionFOVISDTO) {
            asignacionFovisTempDto = (SolicitudPostulacionFOVISDTO) consultaTablaTemporal(idSolicitud, dto);
        } else if (dto instanceof SolicitudLegalizacionDesembolsoDTO) {
            solicitudLegalTempDto = (SolicitudLegalizacionDesembolsoDTO) consultaTablaTemporal(idSolicitud, dto);
        }

        if (asignacionFovisTempDto != null) {
            valoresTempFovis.put(EnumEncabezados.CamposEnum.MODALIDAD.getKey(), asignacionFovisTempDto.getPostulacion().getIdModalidad());
            if (asignacionFovisTempDto.getPostulacion().getIdCicloAsignacion() != null) {
                //Se consulta el ciclo de asignacion del ciclo
                ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(
                        asignacionFovisTempDto.getPostulacion().getIdCicloAsignacion());
                serviceConsultaCicloAsignacion.execute();
                cicloAsignacion = serviceConsultaCicloAsignacion.getResult();
            }
            valoresTempFovis.put(EnumEncabezados.CamposEnum.CICLOASIGNACION.getKey(), cicloAsignacion.getNombre());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.TIPOTRANSACCION.getKey(), asignacionFovisTempDto.getTipoTransaccionEnum());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.NUMEROIDENTIFICACION.getKey(),
                    asignacionFovisTempDto.getPostulacion().getJefeHogar().getNumeroIdentificacion());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.TIPOIDENTIFICACION.getKey(),
                    asignacionFovisTempDto.getPostulacion().getJefeHogar().getTipoIdentificacion());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.JEFEHOGAR.getKey(),
                    (asignacionFovisTempDto.getPostulacion().getJefeHogar().getPrimerNombre() + ConstantesComunicado.SPACE
                            + asignacionFovisTempDto.getPostulacion().getJefeHogar().getSegundoNombre() + ConstantesComunicado.SPACE
                            + asignacionFovisTempDto.getPostulacion().getJefeHogar().getPrimerApellido() + ConstantesComunicado.SPACE
                            + asignacionFovisTempDto.getPostulacion().getJefeHogar().getSegundoApellido()).replaceAll("null",
                            ConstantesComunicado.VACIO));
        } else if (solicitudLegalTempDto != null) {
            valoresTempFovis.put(EnumEncabezados.CamposEnum.MODALIDAD.getKey(),
                    solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getIdModalidad());
            if (solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getIdCicloAsignacion() != null) {
                //Se consulta el ciclo de asignacion del ciclo
                ConsultarCicloAsignacion serviceConsultaCicloAsignacion = new ConsultarCicloAsignacion(
                        solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getIdCicloAsignacion());
                serviceConsultaCicloAsignacion.execute();
                cicloAsignacion = serviceConsultaCicloAsignacion.getResult();
            }
            valoresTempFovis.put(EnumEncabezados.CamposEnum.CICLOASIGNACION.getKey(), cicloAsignacion.getNombre());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.TIPOTRANSACCION.getKey(), solicitudLegalTempDto.getTipoTransaccionEnum());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.NUMEROIDENTIFICACION.getKey(),
                    solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getNumeroIdentificacion());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.TIPOIDENTIFICACION.getKey(),
                    solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getTipoIdentificacion());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.NUMEROSOLICITUD.getKey(), solicitudLegalTempDto.getNumeroRadicacion());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.METODOENVIO.getKey(), solicitudLegalTempDto.getMetodoEnvio());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.ESTADOSOLICITUD.getKey(), solicitudLegalTempDto.getEstadoSolicitud());
            valoresTempFovis.put(EnumEncabezados.CamposEnum.JEFEHOGAR.getKey(),
                    (solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getPrimerNombre()
                            + ConstantesComunicado.SPACE
                            + solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getSegundoNombre()
                            + ConstantesComunicado.SPACE
                            + solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getPrimerApellido()
                            + ConstantesComunicado.SPACE
                            + solicitudLegalTempDto.getDatosPostulacionFovis().getPostulacion().getJefeHogar().getSegundoApellido())
                            .replaceAll("null", ConstantesComunicado.VACIO));
        } else {
            logger.debug("No Se Encontraron Datos En La Tabla Temporal");
        }
        return valoresTempFovis;
    }

    /**
     * Método que consulta los valores de las variables
     *
     * @param parametrosComunicadoDTO DTO que representa los valores que sirven como parametros de consulta
     * @param nombreQuery             Nombre del query para la ejecución de la consulta
     * @param idSolicitud             Identificador de la solicitud global
     * @return Lista con los valores obtenidos en la consulta
     */
    private List<Object[]> obtenerResultadoVariables(ParametrosComunicadoDTO parametrosComunicadoDTO, String nombreQuery, Long idSolicitud) {
        List<Object[]> resultadoValoresVariables = new ArrayList<Object[]>();
        if (nombreQuery != null && nombreQuery != "") {
            Query query = entityManager.createNamedQuery(nombreQuery);
            if (idSolicitud != null) {
                query.setParameter("idSolicitud", idSolicitud);
            }
            if (parametrosComunicadoDTO != null) {
                if (parametrosComunicadoDTO.getTipoIdentificacion() != null) {
                    query.setParameter("tipoIdentificacion", parametrosComunicadoDTO.getTipoIdentificacion().name());
                }
                if (parametrosComunicadoDTO.getNumeroIdentificacion() != null) {
                    query.setParameter("numeroIdentificacion", parametrosComunicadoDTO.getNumeroIdentificacion());
                }
                if (parametrosComunicadoDTO.getIdCartera() != null) {
                    query.setParameter("idCartera", parametrosComunicadoDTO.getIdCartera());
                }
                if (parametrosComunicadoDTO.getIdTransaccionTerceroPagador() != null) {
                    query.setParameter(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR,
                            parametrosComunicadoDTO.getIdTransaccionTerceroPagador());
                }
                if (parametrosComunicadoDTO.getIdentificadorRespuesta() != null) {
                    query.setParameter(ConstantesComunicado.KEY_MAP_ID_RESPUESTA, parametrosComunicadoDTO.getIdentificadorRespuesta());
                }
                if (parametrosComunicadoDTO.getIdArchivoConsumosAnibol() != null) {
                    query.setParameter(ConstantesComunicado.KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL,
                            parametrosComunicadoDTO.getIdArchivoConsumosAnibol());
                }
                if (parametrosComunicadoDTO.getNumeroRadicacion() != null) {
                    query.setParameter(ConstantesComunicado.KEY_MAP_NUMERO_RADICACION,
                            parametrosComunicadoDTO.getNumeroRadicacion());
                }
                if (parametrosComunicadoDTO.getIdPersona() != null) {
                    query.setParameter(ConstantesComunicado.KEY_MAP_ID_PERSONA,
                            parametrosComunicadoDTO.getIdPersona());
                }
            }
            List<Object[]> resultList = (List<Object[]>) query.getResultList();
            resultadoValoresVariables = resultList;

            //valida valores nulos en respuesta en la columna tipo_documento, si es null el campo afiliado es null
            /*if (resultadoValoresVariables.get(0)[1] == null){
                List<Object[]> resultadoValoresVariablesAfiliado = new ArrayList<Object[]>();
                nombreQuery = "plantilla.encabezado.liquidacion.dispersion.afiliado";
                Query queryAfiliado = entityManager.createNamedQuery(nombreQuery);
                if (idSolicitud != null) {
                    queryAfiliado.setParameter("idSolicitud", idSolicitud);
                }
                List<Object[]> resultListAfiliado = (List<Object[]>) queryAfiliado.getResultList();
                resultadoValoresVariablesAfiliado = resultListAfiliado;
                
                return resultListAfiliado;
            }*/
        }
        return resultadoValoresVariables;
    }


    /******************************************************************************************************************************************************/
    /******************************************************************************************************************************************************/
    /******************************************************************************************************************************************************/


    public Map<String, Object> resolverVariablesConsolidadoCartera(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum,
                                                                   Long idPlantillaComunicado, ParametrosComunicadoDTO parametrosComunicadoDTO, Long idSolicitud, String ordenamiento) {

        String nombreMetodo = "resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum, Long, Long, Long, ParametrosComunicadoDTO, String)";
        logger.debug("Inicia " + nombreMetodo);

        // Se valida que los valores idPlantillaComunicado y valorIdInstancia no sean nulos
        if (etiquetaPlantillaComunicadoEnum == null) {
            logger.debug("Finaliza resolverVariablesComunicado(EtiquetaPlantillaComunicadoEnum, Long, Long, Long, ParametrosComunicadoDTO, String): Parámetros no válidos");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

        logger.debug("al servicio resolverVariablesComunicado llegaron los siguientes datos (EtiquetaPlantillaComunicadoEnum: " + etiquetaPlantillaComunicadoEnum.name() + ", idPlantillaComunicado: " + idPlantillaComunicado + ", idSolicitud: " + idSolicitud + ", ordenamiento: " + ordenamiento + ")");

        // Se consulta las variables sobre la tabla VariableComunicado
        AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteConsolidadoCarteraSingleton = AuxiliarReporteConsolidadoCarteraSingleton.getSingletonInstance();
        List<Object[]> variablesComunicado = auxiliarReporteConsolidadoCarteraSingleton.obtenerVariablesComunicado(etiquetaPlantillaComunicadoEnum.getNombreQueryPlantillaConsolidadoCartera(), idPlantillaComunicado, entityManager);

        if (variablesComunicado == null || variablesComunicado.isEmpty()) {
            auxiliarReporteConsolidadoCarteraSingleton.destruir();
            logger.debug("Finaliza " + nombreMetodo);
            return null;
        }

        Map<String, Object> variablesComunicadoMap = new HashMap<>();

        try {

            for (Object[] variableComunicado : variablesComunicado) {

                //Si es constante es porque se trata de una imagen
                if (variableComunicado[2].equals("CONSTANTE")) {
                    variablesComunicadoMap.put(variableComunicado[0].toString(), variableComunicado[1]);
                } else if (variableComunicado[2].equals("REPORTE_VARIABLE")) {

                    ConsultaReporteComunicadosAbs claseReporte = (ConsultaReporteComunicadosAbs) Class.forName(variableComunicado[1].toString()).newInstance();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put(ConstantesComunicado.KEY_MAP_ID_SOLICITUD, idSolicitud);

                    if (parametrosComunicadoDTO != null) {
                        if (parametrosComunicadoDTO.getIdsSolicitud() != null
                                && !parametrosComunicadoDTO.getIdsSolicitud().isEmpty()) {
                            params.put(ConstantesComunicado.KEY_MAP_IDES_SOLICITUD,
                                    parametrosComunicadoDTO.getIdsSolicitud());
                        }
                        if (parametrosComunicadoDTO.getNumeroIdentificacion() != null
                                && parametrosComunicadoDTO.getTipoIdentificacion() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION,
                                    parametrosComunicadoDTO.getNumeroIdentificacion());
                            params.put(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION,
                                    parametrosComunicadoDTO.getTipoIdentificacion());
                        }
                        if (parametrosComunicadoDTO.getIdCartera() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_ID_CARTERA, parametrosComunicadoDTO.getIdCartera());
                        }
                        if (parametrosComunicadoDTO.getIdentificadorRespuesta() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_ID_RESPUESTA,
                                    parametrosComunicadoDTO.getIdentificadorRespuesta());
                        }
                        if (parametrosComunicadoDTO.getIdTransaccionTerceroPagador() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_ID_TRANSACCION_TERCERO_PAGADOR,
                                    parametrosComunicadoDTO.getIdTransaccionTerceroPagador());
                        }
                        if (parametrosComunicadoDTO.getIdArchivoConsumosAnibol() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_ID_ARCHIVO_CONSUMO_ANIBOL,
                                    parametrosComunicadoDTO.getIdArchivoConsumosAnibol());
                        }
                        if (parametrosComunicadoDTO.getNumeroRadicacion() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_NUMERO_RADICACION,
                                    parametrosComunicadoDTO.getNumeroRadicacion());
                        }
                        if (parametrosComunicadoDTO.getIdPersona() != null) {
                            params.put(ConstantesComunicado.KEY_MAP_ID_PERSONA,
                                    parametrosComunicadoDTO.getIdPersona());
                        }
                    }
                    if (ordenamiento != null) {
                        params.put("ordenamiento", ordenamiento);
                    }
                    claseReporte.init(params);
                    String reporte;
                    if (claseReporte instanceof ConsultaTablaComunicado145
                            || claseReporte instanceof ObtenerSumatoriaAportes) {
                        reporte = claseReporte.getCertificado(entityManager, entityManagerReporte);
                    } else if (claseReporte instanceof ReporteComunicadoConsolidadoCartera) {
                        reporte = claseReporte.getReporte(entityManager, managedExecutorService);
                    } else {
                        reporte = claseReporte.getReporte(entityManager);
                    }
                    variablesComunicadoMap.put(variableComunicado[0].toString(), reporte);
                }

            }

        } catch (Exception e) {
            logger.debug("Finaliza " + nombreMetodo + " : la consulta de las variables no cumple con las columnas esperadas");
            e.printStackTrace();
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_DATAS_CONFLICT);
        }

        auxiliarReporteConsolidadoCarteraSingleton.destruir();

        logger.debug("Finaliza " + nombreMetodo);
        return variablesComunicadoMap;
    }

}
