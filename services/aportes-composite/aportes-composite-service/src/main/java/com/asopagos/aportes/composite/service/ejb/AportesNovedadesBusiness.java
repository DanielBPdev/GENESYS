package com.asopagos.aportes.composite.service.ejb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliado;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.afiliados.clients.ConsultarEstadoAfiliacionRespectoCCF;
import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import com.asopagos.aportes.clients.ConsultarRegistroDetalladoPorId;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralId;
import com.asopagos.aportes.composite.clients.TransaccionRegistrarNovedadService;
import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import com.asopagos.aportes.composite.dto.RegistrarNovedadesPilaServiceDTO;
import com.asopagos.aportes.composite.service.constants.ConstantesMayaValidacion;
import com.asopagos.aportes.composite.service.interfaces.IAporteNovedadLocal;
import com.asopagos.aportes.composite.service.interfaces.IAportesNovedadesLocal;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.ConsultaEstadoAfiliacionDTO;
import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.empleadores.clients.VerificarExisteEmpleadorAsociado;
import com.asopagos.empresas.clients.ObtenerSucursalEmpresa;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.clients.GuardarExcepcionNovedadPila;
import com.asopagos.novedades.clients.TransaccionNovedadPilaCompleta;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAportes;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliadosrutines.activarafiliado.ActivarAfiliadoRutine;
import com.asopagos.rutine.empleadores.ActualizarEstadoEmpleadorPorAportesRutine;
import com.asopagos.rutine.novedadescompositerutines.procesaractivacionbeneficiarioPILA.ProcesarActivacionBeneficiarioPILARutine;
import com.asopagos.usuarios.clients.ActualizarUsuarioCCF;
import com.asopagos.usuarios.clients.ConsultarUsuarios;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;

/**
 * Referencia al logger
 */

public class AportesNovedadesBusiness implements IAportesNovedadesLocal {
    
    private final String CANAL_PILA = "PILA";
    
    private final String CANAL_PRESENCIAL = "PRESENCIAL";

    private ILogger logger = LogManager.getLogger(AportesNovedadesBusiness.class);

    @PersistenceContext(unitName = "core_PU_APORTE")
    private EntityManager entityManager;
    
    @Inject
    IAporteNovedadLocal aporteNovedad;

    /**
     * @param novedades
     * @param canal
     * @param tipoIdAportante
     * @param numeroIdAportante
     * @param personaCotizante
     * @param esTrabajadorReintegrable
     * @param esEmpleadorReintegrable
     * @param userDTO
     * @return a
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    //metodo usado por medio de la interfaz
    public List<Long> registrarNovedadesPila(List<NovedadPilaDTO> novedades, CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO) {
        String firmaMetodo = "AportesNovedadesBusiness.registrarNovedadesPila(List<NovedadPilaDTO>, CanalRecepcionEnum, "
                + "TipoIdentificacionEnum,String, PersonaModeloDTO, Boolean, Boolean)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> idsNovedadesProcesadas = new ArrayList<>();  
        
        System.out.println("**__**INICIO registrarNovedadesPila AportesNovedadesBusiness: " );
        novedades.sort((o1, o2) -> Long.compare(o1.getIdRegistroDetallado(),o2.getIdRegistroDetallado() ));

        for (NovedadPilaDTO novedadPilaDTO : novedades) {
            logger.info("TENID:: " + novedadPilaDTO.getIdTenNovedad());        
             //comentado porque consulta la tabla TransaccionNovedadPilaCompleta en core      
           // TransaccionNovedadPilaCompleta t = new TransaccionNovedadPilaCompleta(novedadPilaDTO.getIdTenNovedad());
           // t.execute();
           // Long transaccionNovedadEjecutada = t.getResult();
           // if (transaccionNovedadEjecutada == null) {
                try {
                    if (novedadPilaDTO.getTipoTransaccion() == null) {
                    logger.info("**__**registrarNovedadesPila if primero " + novedadPilaDTO.getIdTenNovedad()); 
                        registrarNovedadSinTipoTransaccion(novedadPilaDTO, canal, tipoIdAportante, numeroIdAportante, personaCotizante, 
                                esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO, idsNovedadesProcesadas);
                    }else{
                        System.out.println("**__**INICIO registrarNovedadesPila novedadPilaDTO.getTipoTransaccion()!= null: " );
                        registrarNovedadConTipoTransaccion(novedadPilaDTO, canal, tipoIdAportante, numeroIdAportante, personaCotizante, 
                                esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO, idsNovedadesProcesadas);
                    }               
                
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString(); 
    
                    if(novedadPilaDTO.getIdTenNovedad() != null){
                        GuardarExcepcionNovedadPila guardarSrv = new GuardarExcepcionNovedadPila(novedadPilaDTO.getIdTenNovedad(), exceptionAsString);
                        guardarSrv.execute();
                    }
                    throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
                }
                
           // } else {
           //     logger.info("TENID ya existe ya se aplico la novedad ");
           //     idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
           // }
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idsNovedadesProcesadas;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void registrarNovedadSinTipoTransaccion(NovedadPilaDTO novedadPilaDTO,CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO,List<Long> idsNovedadesProcesadas) throws Exception{
        String firmaMetodo = "AportesNovedadesBussines.registrarNovedadSinTipoTransaccion()";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
          logger.info("**__**LLEGA A METODO registrarNovedadSinTipoTransaccion");
        TransaccionRegistrarNovedadService trns =   
                new TransaccionRegistrarNovedadService( canal,                     
                        esTrabajadorReintegrable, 
                        esEmpleadorReintegrable,
                        numeroIdAportante,
                        tipoIdAportante,
                        novedadPilaDTO
                        ); 
                 
        trns.execute();
        
        idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
         logger.info("**__**FIn A METODO registrarNovedadSinTipoTransaccion");
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
    }
    
    /**
     * @param novedadPilaDTO
     * @param canal
     * @param tipoIdAportante
     * @param numeroIdAportante
     * @param personaCotizante
     * @param esTrabajadorReintegrable
     * @param esEmpleadorReintegrable
     * @param idsNovedadesProcesadas
     * @param userDTO
     */
    /*
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Long> transaccionRegistrarNovedadService(NovedadPilaDTO novedadPilaDTO,CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, List<Long> idsNovedadesProcesadas,UserDTO userDTO) {
        String firmaMetodo = "AportesNovedadesBusiness.registrarNovedadesPila(List<NovedadPilaDTO>, CanalRecepcionEnum, "
                + "TipoIdentificacionEnum,String, PersonaModeloDTO, Boolean, Boolean)"; 
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo); 
        
        aporteNovedad.transaccionRegistrarNovedad(novedadPilaDTO, canal, tipoIdAportante, numeroIdAportante, personaCotizante, 
                esTrabajadorReintegrable, esEmpleadorReintegrable, userDTO, idsNovedadesProcesadas);
        
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return idsNovedadesProcesadas;
    }
    */
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void registrarNovedadConTipoTransaccion(NovedadPilaDTO novedadPilaDTO,CanalRecepcionEnum canal,
            TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, PersonaModeloDTO personaCotizante,
            Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable, UserDTO userDTO,List<Long> idsNovedadesProcesadas){
        NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
        List<ClasificacionEnum> clasificacionesAfiliado;

        novedadAporte.setTenNovedadId(novedadPilaDTO.getIdTenNovedad());

        // se consultan las clasificaciones del afiliado (si lo está)
        if (personaCotizante != null) {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(personaCotizante.getTipoIdentificacion(),
                    personaCotizante.getNumeroIdentificacion());
        } else {
            clasificacionesAfiliado = consultarClasificacionesAfiliado(
                    novedadPilaDTO.getTipoIdentificacionCotizante(),
                    novedadPilaDTO.getNumeroIdentificacionCotizante());
        }

        // se asigna el valor de la marca de novedad que viene por pila
        novedadAporte.setAplicar(MarcaAccionNovedadEnum.valueOf(novedadPilaDTO.getAccionNovedad()).getMarca());

        // se determina cual de ellas es la que corresponde a la presente
        // solicitud de novedad
        for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
            if (clasificacion.getSujetoTramite() != null
                    && clasificacion.getSujetoTramite().equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(clasificacion);
                break;
            }
        }

        // si no se logró determinar la clasificación del afiliado, quiere
        // decir que la persona no está afiliada
        // por tanto se asigna un valor cualquiera correspondiente a su
        // clasificación y se asigna el valor de la
        // marca de novedad como NO_APLICADA
        if (novedadAporte.getClasificacionAfiliado() == null) {
            if (TipoAfiliadoEnum.PENSIONADO.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(novedadPilaDTO.getTipoCotizante())) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            }

            // novedadAporte.setAplicar(MarcaAccionNovedadEnum.NO_APLICADA.getMarca());
        }

        // se asigna el resto de valores corrspondientes a la novedad que se
        // desea radicar
        if (novedadPilaDTO.getFechaInicioNovedad() != null) {
            novedadAporte.setFechaInicio(novedadPilaDTO.getFechaInicioNovedad().getTime());
        }

        novedadAporte.setComentarios(novedadPilaDTO.getMensajeNovedad());
        novedadAporte.setTipoNovedad(novedadPilaDTO.getTipoTransaccion());
        if (novedadPilaDTO.getFechaFinNovedad() != null) {
            novedadAporte.setFechaFin(novedadPilaDTO.getFechaFinNovedad().getTime());
        }
        if (personaCotizante != null) {
            novedadAporte.setNumeroIdentificacion(personaCotizante.getNumeroIdentificacion());
            novedadAporte.setTipoIdentificacion(personaCotizante.getTipoIdentificacion());
        } else {
            novedadAporte.setNumeroIdentificacion(novedadPilaDTO.getNumeroIdentificacionCotizante());
            novedadAporte.setTipoIdentificacion(novedadPilaDTO.getTipoIdentificacionCotizante());
        }

        novedadAporte.setNumeroIdentificacionAportante(numeroIdAportante);
        novedadAporte.setTipoIdentificacionAportante(tipoIdAportante);
        novedadAporte.setCanalRecepcion(canal);
        novedadAporte.setIdRegistroDetallado(novedadPilaDTO.getIdRegistroDetallado());

        if (novedadPilaDTO.getTipoTransaccion() != null
                && TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB
                        .equals(novedadPilaDTO.getTipoTransaccion())) {

            SucursalEmpresa sucursal = consultarSucursalEmpresa(novedadPilaDTO.getValor(), tipoIdAportante,
                    numeroIdAportante);
            novedadAporte.setSucursal(sucursal);
        }           
          System.out.println("**__**antes de  RadicarSolicitudNovedadAportes" );
        RadicarSolicitudNovedadAportes radicarSolicitudNovedad = new RadicarSolicitudNovedadAportes(
                novedadAporte);
        radicarSolicitudNovedad.execute();
   System.out.println("**__**FIN de  RadicarSolicitudNovedadAportes" );
        idsNovedadesProcesadas.add(novedadPilaDTO.getIdRegistroDetalladoNovedad());
        
    }

    
    /**
     * Método que hace el llamado al microservicio que consulta y actualiza el
     * estado de un afiliado a ACTIVO
     * 
     * @param tipoIdCotizante   tipo de documento de identificación del afiliado
     *
     * @param numeroIdCotizante número de documento de identificación del afiliado
     * 
     * @param tipoIdAportante   tipo de documento de identificación del empleador
     * 
     * @param numeroIdAportante número de documento de identificación del empleador
     */
    private void activarAfiliado(ActivacionAfiliadoDTO datosActivacion) {

        /*
         * ActivarAfiliado activarAfiliado = new ActivarAfiliado(datosActivacion);
         * activarAfiliado.execute();
         */

        ActivarAfiliadoRutine a = new ActivarAfiliadoRutine();
        a.activarAfiliado(datosActivacion, entityManager);
    }

    private Boolean procesarIntentoNovedad(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdAportante,
            String numeroIdAportante, TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante,
            CanalRecepcionEnum canalRecepcion, Long idRegistroDetallado, Long tenNovedadId) {
        try {
            NovedadAportesDTO novedadAporte = new NovedadAportesDTO();

            novedadAporte.setTipoNovedad(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
            novedadAporte.setAplicar(MarcaNovedadEnum.NO_APLICADA);
            novedadAporte.setCanalRecepcion(canalRecepcion);

            novedadAporte.setNumeroIdentificacion(numeroIdCotizante);
            novedadAporte.setTipoIdentificacion(tipoIdCotizante);
            novedadAporte.setNumeroIdentificacionAportante(numeroIdAportante);
            novedadAporte.setTipoIdentificacionAportante(tipoIdAportante);
            novedadAporte.setIdRegistroDetallado(idRegistroDetallado);
            novedadAporte.setTenNovedadId(tenNovedadId);

            determinarClasificacionAfiliado(tipoCotizante, tipoIdCotizante, numeroIdCotizante, novedadAporte);

            RadicarSolicitudNovedadAportes radicarSolicitudNovedad = new RadicarSolicitudNovedadAportes(novedadAporte);
            radicarSolicitudNovedad.execute();
            return true;
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando la solicitud de novedad para " + tipoIdCotizante + "-"
                    + numeroIdCotizante, e);
            logger.info("no se pudo procesar la novedad");
        }
        return false;
    }

    /**
     * @param tipoCotizante
     * @param tipoIdCotizante
     * @param numeroIdCotizante
     * @param novedadAporte
     */
    private void determinarClasificacionAfiliado(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdCotizante,
            String numeroIdCotizante, NovedadAportesDTO novedadAporte) {
        // se consultan las clasificaciones del afiliado (si lo está)
        List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(tipoIdCotizante,
                numeroIdCotizante);

        // se determina cual de ellas es la que corresponde a la presente
        // solicitud de novedad
        for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
            if (clasificacion.getSujetoTramite() != null && clasificacion.getSujetoTramite().equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(clasificacion);
                break;
            }
        }

        // si no se logró determinar la clasificación del afiliado, quiere
        // decir que la persona no está afiliada
        // por tanto se asigna un valor cualquiera correspondiente a su
        // clasificación y se asigna el valor de la
        // marca de novedad como NO_APLICADA
        if (novedadAporte.getClasificacionAfiliado() == null) {
            if (TipoAfiliadoEnum.PENSIONADO.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO);
            } else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoCotizante)) {
                novedadAporte.setClasificacionAfiliado(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
            }
        }
    }

    /**
     * @param datosProcesoIng
     * @param userDTO
     * @return a
     */
    public Boolean procesarNovedadIngresoAporte(ProcesoNovedadIngresoDTO datosProcesoIng, UserDTO userDTO) {
        String firmaMetodo = "AportesNovedadesBusiness.procesarNovedadIngresoAporte(ProcesoNovedadIngresoDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean habilitadoEmpleador = datosProcesoIng.getEsEmpleadorReintegrable();
        Boolean reintegroProcesado = Boolean.FALSE;
        Boolean radicadoIntentoNovedad = false;
        /*
         * Mantis 234253: Se eliminan las validaciones que se hacian llamando los
         * bloques de reintegro (como en afiliaciones) y unicamente se tiene en cuenta
         * el valor que llega desde las validaciones de pila indicando si el aportante
         * es reintegrable o no lo es
         */
        /*
         * si es reintegrable se activa el afiliado tanto el afiliado como el empleador,
         * Null es válido para independientes
         */
        if (datosProcesoIng.getEsReintegrable() != null && datosProcesoIng.getEsReintegrable()) {
            RegistroDetalladoModeloDTO datosAfiliado = null;
            EmpleadorModeloDTO datosEmpleador = null;
            SucursalEmpresaModeloDTO datosSucursal = null;
            ActivacionAfiliadoDTO datosActivacion = new ActivacionAfiliadoDTO();

            // se consulta el registro detallado para lso datos del cotizante,
            // en caso de requerir la creación del registro
            ConsultarRegistroDetalladoPorId consultarRegistroDetalladoPorId = new ConsultarRegistroDetalladoPorId(
                    datosProcesoIng.getIdRegistroDetallado());
            consultarRegistroDetalladoPorId.execute();

            // se consulta la información del empleador (para dependientes)
            if (datosProcesoIng.getTipoIdAportante() != null && datosProcesoIng.getNumeroIdAportante() != null) {
                PersonaModeloDTO personaModeloDTO = null;
                personaModeloDTO = new PersonaModeloDTO();
                personaModeloDTO.setTipoIdentificacion(datosProcesoIng.getTipoIdAportante());
                personaModeloDTO.setNumeroIdentificacion(datosProcesoIng.getNumeroIdAportante());
                VerificarExisteEmpleadorAsociado existeEmpleador = new VerificarExisteEmpleadorAsociado(
                        personaModeloDTO);
                existeEmpleador.execute();
                datosEmpleador = existeEmpleador.getResult();

                // se consulta la información de la sucursal sí aplica, para
                // eso, se debe consultar el registro general del aporte
                ConsultarRegistroGeneralId consultarRegistroGeneralId = new ConsultarRegistroGeneralId(
                        datosProcesoIng.getIdRegistroGeneral());
                consultarRegistroGeneralId.execute();
                RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId.getResult();

                if (registroGeneral != null) {
                    SucursalEmpresa sucursal = consultarSucursalEmpresa(
                            registroGeneral.getCodSucursal() != null ? registroGeneral.getCodSucursal()
                                    : registroGeneral.getOutCodSucursalPrincipal(),
                            datosProcesoIng.getTipoIdAportante(), datosProcesoIng.getNumeroIdAportante());

                    if (sucursal != null) {
                        datosSucursal = new SucursalEmpresaModeloDTO();
                        datosSucursal.convertToDTO(sucursal);
                    }
                }

                /*
                 * Con base en la información del Registro General, se evalua la habilitación
                 * respecto al empleador. Es decir, sí se trata de un cotizante dependiente, no
                 * se le puede reintegrar sí el empleador aportante está inactivo y/o no es
                 * reintegrable
                 */
                if ((!TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(datosProcesoIng.getTipoCotizante()))
                        || (!habilitadoEmpleador && registroGeneral != null
                                && EstadoEmpleadorEnum.ACTIVO.equals(registroGeneral.getOutEstadoEmpleador()))) {
                    habilitadoEmpleador = Boolean.TRUE;
                }
            }

            if (habilitadoEmpleador) {
                datosAfiliado = consultarRegistroDetalladoPorId.getResult();
                if (datosAfiliado.getFechaIngreso() == null) {
                    datosAfiliado.setFechaIngreso(datosProcesoIng.getFechaIngreso());
                }
                datosActivacion.setDatosAfiliado(datosAfiliado);
                datosActivacion.setEmpleador(datosEmpleador);
                datosActivacion.setSucursal(datosSucursal);
                datosActivacion.setNumeroIdAfiliado(datosProcesoIng.getNumeroIdCotizante());
                datosActivacion.setTipoIdAfiliado(datosProcesoIng.getTipoIdCotizante());
                datosActivacion.setNumeroIdAportante(datosProcesoIng.getNumeroIdAportante());
                datosActivacion.setTipoIdAportante(datosProcesoIng.getTipoIdAportante());
                datosActivacion.setTipoAfiliado(datosProcesoIng.getTipoCotizante());
                datosActivacion.setCanalRecepcion(datosProcesoIng.getCanalRecepcion());

                // se obtiene el estado de afiliación respecto a la caja del
                // afiliado antes de reactivar (para obtener su fecha de retiro)
                ConsultarEstadoAfiliacionRespectoCCF consultaEstado = new ConsultarEstadoAfiliacionRespectoCCF(null,
                        datosProcesoIng.getTipoIdCotizante(), datosProcesoIng.getNumeroIdCotizante());
                consultaEstado.execute();
                ConsultaEstadoAfiliacionDTO estadoAfiliacion = consultaEstado.getResult();

                Date fechaRetiroAfiliado = estadoAfiliacion.getFechaRetiro() != null
                        ? new Date(estadoAfiliacion.getFechaRetiro())
                        : null;

                // Si se cumple la condicion se procede a activar el empleador, sabiendo que la
                // variable
                // es distinta a null solo en el caso de los dependientes
                if (datosEmpleador != null) {
                    ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
                    datosReintegro.setCanalReintegro(datosProcesoIng.getCanalRecepcion());
                    datosReintegro.setFechaReintegro(datosProcesoIng.getFechaIngreso());
                    datosReintegro.setNumIdEmpleador(datosEmpleador.getNumeroIdentificacion());
                    datosReintegro.setTipoIdEmpleador(datosEmpleador.getTipoIdentificacion());
                    datosReintegro.setIdAportante(datosEmpleador.getIdEmpresa());
                    datosReintegro.setIdRegistroGeneral(datosProcesoIng.getIdRegistroGeneral());

                    procesarActivacionEmpleador(datosReintegro);
                }
                activarAfiliado(datosActivacion);

                // después de activar al afiliado, se evalua el reintegro de sus
                // grupos familiares
                if (datosAfiliado.getOutGrupoFamiliarReintegrable()) {

                    String numeroIdEmpleador = null;
                    TipoIdentificacionEnum tipoIdEmpleador = null;

                    Date fechaIngresoAfiliado = datosProcesoIng.getFechaIngreso() != null
                            ? new Date(datosProcesoIng.getFechaIngreso())
                            : null;

                    if (datosEmpleador != null) {
                        numeroIdEmpleador = datosEmpleador.getNumeroIdentificacion();
                        tipoIdEmpleador = datosEmpleador.getTipoIdentificacion();
                    }
                    reintegrarGrupoFamiliar(datosProcesoIng.getTipoIdCotizante(),
                            datosProcesoIng.getNumeroIdCotizante(), fechaRetiroAfiliado, fechaIngresoAfiliado,
                            datosProcesoIng.getTipoCotizante(), tipoIdEmpleador, numeroIdEmpleador, userDTO);
                }

                // se solicita la activación de la cuenta del afiliado
                activarCuentaPersona(datosProcesoIng.getTipoIdCotizante(), datosProcesoIng.getNumeroIdCotizante());
                reintegroProcesado = Boolean.TRUE;
            }
        }
        /*
         * si las validaciones no se aprueban se crea un intento de afiliación.
         */
        if (!reintegroProcesado) {
            radicadoIntentoNovedad = procesarIntentoNovedad(datosProcesoIng.getTipoCotizante(), datosProcesoIng.getTipoIdAportante(),
                    datosProcesoIng.getNumeroIdAportante(), datosProcesoIng.getTipoIdCotizante(),
                    datosProcesoIng.getNumeroIdCotizante(), datosProcesoIng.getCanalRecepcion(),
                    datosProcesoIng.getIdRegistroDetallado(), datosProcesoIng.getTenNovedadId());
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return radicadoIntentoNovedad;
    }

    /**
     * Método encargado de hacer el llamado al microservicio que consulta las
     * clasificaciones de un afiliado
     * 
     * @param tipoId   el tipo de identificacion del afiliado
     * @param numeroId el numero de identificacion del afiliado
     * 
     * @return List<ClasificacionEnum> con las clasificaciones encontradas
     */
    private List<ClasificacionEnum> consultarClasificacionesAfiliado(TipoIdentificacionEnum tipoId, String numeroId) {
        try {
            ConsultarClasificacionesAfiliado consultarClasificacionAfiliado = new ConsultarClasificacionesAfiliado(
                    numeroId, tipoId);
            consultarClasificacionAfiliado.execute();
            return consultarClasificacionAfiliado.getResult();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Método encargado de hacer el llamado al microservicio que consulta la
     * sucursal de empresa con su código y el id de empresa.
     * 
     * @param codigoSucursal es el codigo que identifica la sucursal para la empresa
     * 
     * @param idEmpresa      el identificador de la empresa
     * 
     * @return SucursalEmpresa con los datos de la sucursal
     */
    private SucursalEmpresa consultarSucursalEmpresa(String codigoSucursal, TipoIdentificacionEnum tipoIdAportante,
            String numeroIdAportante) {

        ObtenerSucursalEmpresa obtenerSucursalEmpresa = new ObtenerSucursalEmpresa(codigoSucursal, numeroIdAportante,
                tipoIdAportante);
        obtenerSucursalEmpresa.execute();
        return obtenerSucursalEmpresa.getResult() != null ? obtenerSucursalEmpresa.getResult().convertToEntity() : null;
    }

    /**
     * @param datosReintegro
     * @return Boolean
     */
    public Boolean procesarActivacionEmpleador(ActivacionEmpleadorDTO datosReintegro) {
        String firmaMetodo = "procesarActivacionEmpleador(ActivacionEmpleadorDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Boolean reintegrado = Boolean.FALSE;

        try {
            actualizarEstadoEmpleador(datosReintegro);

            activarCuentaPersona(datosReintegro.getTipoIdEmpleador(), datosReintegro.getNumIdEmpleador());
            reintegrado = Boolean.TRUE;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return reintegrado;
    }

    /**
     * @param tipoIdCotizante
     * @param numeroIdCotizante
     * @param fechaRetiroAfiliado
     * @param fechaIngresoAfiliado
     * @param tipoCotizante
     * @param tipoIdEmpleador
     * @param numeroIdEmpleador
     * @param userDTO
     */
    public void reintegrarGrupoFamiliar(TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante,
            Date fechaRetiroAfiliado, Date fechaIngresoAfiliado, TipoAfiliadoEnum tipoCotizante,
            TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, UserDTO userDTO) {
        String firmaMetodo = "AportesNovedadesBusiness.reintegrarGrupoFamiliar(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Se verifica si es posible hacer el reintegro de acuerdo a la parametrización
        // respectiva
        Long tiempoReintegro = CalendarUtils
                .toMilis((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_REINTEGRO_GF));

        Long difereciaFechas = new Long(0L);

        if (fechaRetiroAfiliado != null) {
            difereciaFechas = new Date().getTime() - fechaRetiroAfiliado.getTime();
        }

        if (difereciaFechas > tiempoReintegro) {
            return;
        }

        // se obtiene el ID de afiliado
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(numeroIdCotizante, tipoIdCotizante);
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliado = consultarDatosAfiliado.getResult();

        if (fechaRetiroAfiliado != null && afiliado != null) {
            // se consultan los beneficiarios asociados al afiliado
            ConsultarBeneficiarios consultaBeneficiarios = new ConsultarBeneficiarios(afiliado.getIdAfiliado(), false);
            consultaBeneficiarios.execute();
            List<BeneficiarioDTO> beneficiarios = consultaBeneficiarios.getResult();

            if (beneficiarios != null && !beneficiarios.isEmpty()) {
                // se compara la fecha de retiro del beneficiario con la fecha
                // de retiro del afiliado principal
                LocalDate retiroAfiliado = fechaRetiroAfiliado != null
                        ? fechaRetiroAfiliado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : null;
                for (BeneficiarioDTO beneficiario : beneficiarios) {
                    LocalDate retiroBeneficiario = beneficiario.getFechaRetiro() != null
                            ? beneficiario.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            : null;

                    // sí las fechas de retiro coinciden y pasa la maya de
                    // validación, se sólicita la actualización del beneficiario
                    if (retiroAfiliado != null && retiroBeneficiario != null
                            && retiroBeneficiario.isEqual(retiroAfiliado)
                            && ejecutarMayaValidacionBeneficiario(beneficiario, tipoIdCotizante, numeroIdCotizante,
                                    tipoIdEmpleador, numeroIdEmpleador)) {

                        beneficiario.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
                        beneficiario.setFechaRetiro(null);
                        beneficiario.setMotivoDesafiliacion(null);
                        beneficiario.setFechaAfiliacion(fechaIngresoAfiliado);

                        String mensajeNovedad = "Activacion de beneficiarios (PILA)";

                        NovedadAportesDTO novedadAporte = new NovedadAportesDTO();
                        novedadAporte.setAplicar(MarcaNovedadEnum.APLICADA);
                        determinarClasificacionAfiliado(tipoCotizante, tipoIdCotizante, numeroIdCotizante,
                                novedadAporte);
                        novedadAporte.setComentarios(mensajeNovedad);
                        novedadAporte.setTipoNovedad(this.calcularTipoTransaccion(beneficiario));
                        novedadAporte.setNumeroIdentificacion(numeroIdCotizante);
                        novedadAporte.setTipoIdentificacion(tipoIdCotizante);
                        novedadAporte.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
                        novedadAporte.setBeneficiario(beneficiario);
                        /*
                         * ProcesarActivacionBeneficiarioPILA activacionSrv = new
                         * ProcesarActivacionBeneficiarioPILA(novedadAporte); activacionSrv.execute();
                         */
                        ProcesarActivacionBeneficiarioPILARutine p = new ProcesarActivacionBeneficiarioPILARutine();
                        p.procesarActivacionBeneficiarioPILA(novedadAporte, userDTO, entityManager);
                    }
                }
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private TipoTransaccionEnum calcularTipoTransaccion(BeneficiarioDTO beneficiario) {
        TipoTransaccionEnum bloque = null;
        switch (beneficiario.getTipoBeneficiario()) {
        case BENEFICIARIO_EN_CUSTODIA:
            bloque = TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL;
            break;
        case CONYUGE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
            break;
        case HERMANO_HUERFANO_DE_PADRES:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL;
            break;
        case HIJASTRO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
            break;
        case HIJO_ADOPTIVO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL;
            break;
        case HIJO_BIOLOGICO:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
            break;
        case MADRE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL;
            break;
        case PADRE:
            bloque = TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL;
            break;
        default:
            break;

        }
        return bloque;
    }

    /**
     * Método para reactivar la cuenta web de un empelador a partie de su ID
     */
    private void activarCuentaPersona(TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "AportesNovedadesBusiness.activarCuentaPersona(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoId != null && numId != null) {
            UsuarioCCF usuario = buscarUsuario(tipoId, numId);
            if (usuario != null) {
                usuario.setUsuarioActivo(Boolean.TRUE);
                usuario.setReintegro(Boolean.TRUE);

                ActualizarUsuarioCCF actualizarUsuarioCCF = new ActualizarUsuarioCCF(usuario);
                actualizarUsuarioCCF.execute();
            }
        } else {
            TechnicalException e = new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO
                    + " - No se cuenta con el tipo y número de identificación del usuario a activar");
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw e;
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    private void actualizarEstadoEmpleador(ActivacionEmpleadorDTO datosReintegro) {
        /*
        ActualizarEstadoEmpleadorPorAportes actualizarEstadoEmpleador;
        actualizarEstadoEmpleador = new ActualizarEstadoEmpleadorPorAportes(datosReintegro);
        actualizarEstadoEmpleador.execute();
        */
        ActualizarEstadoEmpleadorPorAportesRutine actualizarEstadoEmpleadorPorAportesRutine = new ActualizarEstadoEmpleadorPorAportesRutine();
        actualizarEstadoEmpleadorPorAportesRutine.actualizarEstadoEmpleadorPorAportes(datosReintegro, entityManager);
    }
    
    /**
     * Método encargado de ejecutar la maya de validaciones para el reintegro de
     * un beneficiario
     * 
     * @param beneficiario
     * @param tipoIdAfiliado
     * @param numIdAfiliado
     * @return
     */
    private Boolean ejecutarMayaValidacionBeneficiario(BeneficiarioDTO beneficiario,
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador) {
        String firmaMetodo = "AportesNovedadesBusiness.ejecutarMayaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        TipoTransaccionEnum bloque = calcularTipoTransaccion(beneficiario);

        if (bloque == null) {
            return Boolean.FALSE;
        }

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_TRANSACCION, bloque.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION, tipoIdAfiliado.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION, numIdAfiliado);
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICA_BENEFI,
                beneficiario.getPersona().getTipoIdentificacion().name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICA_BENEFI,
                beneficiario.getPersona().getNumeroIdentificacion());
        datosValidacion.put(ConstantesMayaValidacion.KEY_PRIMER_NOMBRE, beneficiario.getPersona().getPrimerNombre());
        datosValidacion.put(ConstantesMayaValidacion.KEY_SEGUNDO_NOMBRE, beneficiario.getPersona().getSegundoNombre());
        datosValidacion.put(ConstantesMayaValidacion.KEY_PRIMER_APELLIDO,
                beneficiario.getPersona().getPrimerApellido());
        datosValidacion.put(ConstantesMayaValidacion.KEY_SEGUNDO_APELLIDO,
                beneficiario.getPersona().getSegundoApellido());
        datosValidacion.put(ConstantesMayaValidacion.KEY_FECHA_NACIMIENTO, 
                beneficiario.getPersona().getFechaNacimiento().toString());
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION_AFILIADO, tipoIdAfiliado.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION_AFILIADO, numIdAfiliado);
        datosValidacion.put(ConstantesMayaValidacion.KEY_TIPO_IDENTIFICACION_EMPLEADOR_PARAM, tipoIdEmpleador.name());
        datosValidacion.put(ConstantesMayaValidacion.KEY_NRO_IDENTIFICACION_EMPLEADOR_PARAM, numeroIdEmpleador);

        ValidarReglasNegocio validador = new ValidarReglasNegocio(bloque.name().replace(CANAL_PRESENCIAL, CANAL_PILA), bloque.getProceso(),
                beneficiario.getTipoBeneficiario().name(), datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {

                logger.warn(ConstantesComunes.FIN_LOGGER + firmaMetodo + " :: El beneficiario "
                        + beneficiario.getPersona().getTipoIdentificacion().getValorEnPILA()
                        + beneficiario.getPersona().getNumeroIdentificacion() + ", no aprobó la maya de validación - "
                        + validacion.getDetalle());
                return Boolean.FALSE;
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }
    
    private UsuarioCCF buscarUsuario(TipoIdentificacionEnum tipoId, String numId) {
        String firmaMetodo = "AportesNovedadesBusiness.buscarUsuario(TipoIdentificacionEnum, String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        UsuarioCCF usuario = null;

        ConsultarUsuarios consultarUsuarios = new ConsultarUsuarios();
        consultarUsuarios.execute();
        List<UsuarioDTO> usuarios = consultarUsuarios.getResult();

        String userName = tipoId.name().toLowerCase() + "_" + numId;

        if (usuarios != null) {
            for (UsuarioDTO usuarioDTO : usuarios) {
                if (usuarioDTO.getNombreUsuario().equalsIgnoreCase(userName)
                        || (tipoId.equals(usuarioDTO.getTipoIdentificacion())
                                && numId.equalsIgnoreCase(usuarioDTO.getNumIdentificacion()))) {
                    usuario = new UsuarioCCF(usuarioDTO);
                }
            }
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return usuario;
    }
}
