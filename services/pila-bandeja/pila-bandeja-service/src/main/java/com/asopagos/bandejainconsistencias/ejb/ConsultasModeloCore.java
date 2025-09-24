package com.asopagos.bandejainconsistencias.ejb;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.bandejainconsistencias.constants.ConstantesPilaBandeja;
import com.asopagos.bandejainconsistencias.constants.NamedQueriesConstants;
import com.asopagos.bandejainconsistencias.dto.BandejaEmpleadorCeroTrabajadoresDTO;
import com.asopagos.bandejainconsistencias.dto.ConsultaCanalDeRetiroEmpleadorDTO;
import com.asopagos.bandejainconsistencias.dto.CriteriosDTO;
import com.asopagos.bandejainconsistencias.dto.EmpCeroTrabajadoresActivosDTO;
import com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore;
import com.asopagos.bandejainconsistencias.service.ejb.PilaBandejaBusiness;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.EmpAporPendientesPorAfiliarDTO;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import com.asopagos.dto.modelo.DepartamentoModeloDTO;
import com.asopagos.dto.modelo.ExcepcionNovedadPilaModeloDTO;
import com.asopagos.entidades.ccf.aportes.ActualizacionDatosEmpleador;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.enumeraciones.aportes.PilaEstadoTransitorioEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Robinson A. Arboleda </a>
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> Andres Felipe Buitrago </a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;
    //Constantes de argumentos de namedQueries
    private String argNumeroIdentificacion = "numeroIdentificacion";
    private String argTipoIdentificacion = "tipoIdentificacion";
    private String argDigitoVerificacion = "digitoVerificacion";
    private String argTipoDocumento = "tipoDocumento";
    private String argFechaIngresoInicio = "fechaIngresoInicio";
    private String argFechaIngresoFin = "fechaIngresoFin";

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarPersona(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum)
     */
    @Override
    public Persona consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        Persona persona = null;
        try {
            //se realiza la busqueda de una persona por medio de su numero y tipo de identificacion
            persona = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA, Persona.class)
                    .setParameter(argNumeroIdentificacion, numeroIdentificacion).setParameter(argTipoIdentificacion, tipoIdentificacion)
                    .getSingleResult();
            return persona;

        } catch (NoResultException nrex) {
            return null;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarMunicipio(java.lang.String)
     */
    @Override
    public Municipio consultarMunicipio(String codigoMunicipio) {
        Municipio municipio = null;
        try {
            //se realiza la busqueda por codigo de municipio
            municipio = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_MUNICIPIO, Municipio.class)
                    .setParameter("codCiudad", codigoMunicipio).getSingleResult();
            return municipio;
        } catch (NoResultException nre) {
            logger.debug("Finaliza debido a que el municipio no se encuentra en la BD");
            logger.error(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.error("Se presento un error al buscar un municipio");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarMunicipio(java.lang.String)
     */
    @Override
    public DepartamentoModeloDTO consultarDepartamento(String codigoDepartamento) {
        DepartamentoModeloDTO departamentoDTO = null;
        try {
            //se realiza la busqueda por codigo de municipio
            Departamento departamento = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEPARTAMENTO, Departamento.class)
                    .setParameter("codDpto", codigoDepartamento).getSingleResult();
            
            departamentoDTO = new DepartamentoModeloDTO();
            departamentoDTO.convertToDTO(departamento);
            
            return departamentoDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza debido a que el municipio no se encuentra en la BD");
            logger.error(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (Exception e) {
            logger.error("Se presento un error al buscar un municipio");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#persistirUbicacion(com.asopagos.entidades.ccf.core.Ubicacion)
     */
    @Override
    public void persistirUbicacion(Ubicacion ubicacion) {
        try {
            //se persiste la ubicacion
            entityManagerCore.persist(ubicacion);
        } catch (Exception e) {
            logger.debug("No se pudo persistir ubicacion");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#persistirPersona(com.asopagos.entidades.ccf.personas.Persona)
     */
    @Override
    public void persistirPersona(Persona persona) {
        try {
            //se persiste la entidad persona
            entityManagerCore.persist(persona);
        } catch (Exception e) {
            logger.debug("No se pudo persistir persona");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#actualizarPersona(com.asopagos.entidades.ccf.personas.Persona)
     */
    @Override
    public void actualizarPersona(Persona persona) {
        try {
            //se actualiza una entidad persona
            if(persona.getIdPersona() != null){
                entityManagerCore.merge(persona);
            }else{
                entityManagerCore.persist(persona);
            }
        } catch (Exception e) {
            logger.debug("No se pudo actualizar la persona ");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarEmpPendientesPorAfiliar(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.Short, java.lang.Long, javax.ws.rs.core.UriInfo,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<EmpAporPendientesPorAfiliarDTO> consultarEmpPendientesPorAfiliar(CriteriosDTO criterios, UriInfo uri,
            HttpServletResponse response) {

        String firmaMetodo = "Inicia consultarEmpPendientesPorAfiliar(CriteriosDTO, UriInfo, HttpServletResponse response)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Date fecEntradaBandeja = null;
        List<EmpAporPendientesPorAfiliarDTO> empleadoresPorAfiliar = null;

        if (criterios.getFechaIngresobandeja() != null) {
            fecEntradaBandeja = new Date(criterios.getFechaIngresobandeja());
        }else{
            fecEntradaBandeja = new Date(1L);
        }
        
        try{
            // Buscar los empleadores retirados que aun reciben aportes
            List<Object[]> queryResult = entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSQUEDA_APORTANTES_RETIRADOS_CON_APORTES)
                    .setParameter("fechaEntradaBandeja", fecEntradaBandeja)
                    .setParameter(argNumeroIdentificacion, criterios.getNumeroIdentificacion())
                    .setParameter(argTipoIdentificacion,
                            criterios.getTipoIdentificacion() != null ? criterios.getTipoIdentificacion().name() : null)
                    .setParameter(argDigitoVerificacion, criterios.getDigitoVerificacion()).getResultList();
            
            empleadoresPorAfiliar = new ArrayList<>();
            
            for (Object[] fila : queryResult) {
                EmpAporPendientesPorAfiliarDTO empleador = new EmpAporPendientesPorAfiliarDTO(fila);
                empleadoresPorAfiliar.add(empleador);
            }
        }catch(NoResultException e){
            empleadoresPorAfiliar = Collections.emptyList();
        }catch(Exception e){
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return empleadoresPorAfiliar;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarEmpCeroTrabajadoresActivos(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.Short, java.lang.Long, java.lang.Long,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<BandejaEmpleadorCeroTrabajadoresDTO> consultarEmpCeroTrabajadoresActivos(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion, String nombreEmpresa, Short digitoVerificacion, Long fechaInicioIngresoBandeja,
            Long fechaFinIngresoBandeja, UriInfo uri, HttpServletResponse response) {

        logger.debug("Inicia consultarEmpCeroTrabajadoresActivos(String, TipoIdentificacionEnum, String, Short, "
                + "Long, Long, UriInfo, HttpServletResponse)");

        Calendar fechaActual = Calendar.getInstance();
        Calendar calGestionDesafiliacion = Calendar.getInstance();
        Long numDias = null;

        List<EmpCeroTrabajadoresActivosDTO> empleadoresCero = new ArrayList<EmpCeroTrabajadoresActivosDTO>();
        List<ConsultaCanalDeRetiroEmpleadorDTO> trabajadorCanalRetiro = new ArrayList<ConsultaCanalDeRetiroEmpleadorDTO>();
        List<BandejaEmpleadorCeroTrabajadoresDTO> bandejaRegistros = new ArrayList<BandejaEmpleadorCeroTrabajadoresDTO>();

        Date fecIniEntradaBandeja = null;
        Date fecFinEntradaBandeja = null;

        // Preparar fecha inicio fecha fin
        if (fechaInicioIngresoBandeja != null || fechaFinIngresoBandeja != null) {
            // Llegan ambas fechas
            if (fechaInicioIngresoBandeja != null && fechaFinIngresoBandeja != null) {
                fecIniEntradaBandeja = CalendarUtils.truncarHora(new Date(fechaInicioIngresoBandeja));
                fecFinEntradaBandeja = CalendarUtils.truncarHora(new Date(fechaFinIngresoBandeja));
            }
            // Llega inicio sola (tomar fecha actual como fecha fin) 
            else if (fechaInicioIngresoBandeja != null && fechaFinIngresoBandeja == null) {
                fecIniEntradaBandeja = CalendarUtils.truncarHora(new Date(fechaInicioIngresoBandeja));
                fecFinEntradaBandeja = CalendarUtils.truncarHora(new Date());
            }
            // Llega fecha fin sola (consultar todos los registros hasta fecha fin)
            else if (fechaInicioIngresoBandeja == null && fechaFinIngresoBandeja != null) {
                fecIniEntradaBandeja = CalendarUtils.truncarHora(new GregorianCalendar(1900, 1, 1).getTime());
                fecFinEntradaBandeja = CalendarUtils.truncarHora(new Date(fechaFinIngresoBandeja));
            }
        }

        //Consulta 
        empleadoresCero = entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSQUEDA_EMPLEADOR_CERO_TRABAJADORES_ACTIVOS)
                .setParameter("nombreEmpresa", nombreEmpresa).setParameter(argNumeroIdentificacion, numeroIdentificacion)
                .setParameter(argTipoIdentificacion, tipoIdentificacion).setParameter(argDigitoVerificacion, digitoVerificacion)
                .setParameter("fechaInicioIngresoBandeja", fecIniEntradaBandeja)
                .setParameter("fechaFinIngresoBandeja", fecFinEntradaBandeja)
                .setParameter("estado", EstadoEmpleadorEnum.ACTIVO)
                .getResultList();

        if (!empleadoresCero.isEmpty()) {
            for (EmpCeroTrabajadoresActivosDTO empleador : empleadoresCero) {
                // Se llena la bandeja para retornar                
                BandejaEmpleadorCeroTrabajadoresDTO bandeja = new BandejaEmpleadorCeroTrabajadoresDTO();
                bandeja.setIdEmpleador(empleador.getIdEmpleador());
                bandeja.setTipoIdentificacion(empleador.getTipoIdentificacion());
                bandeja.setNumeroIdentificacion(empleador.getNumeroIdentificacion());
                bandeja.setDigitoVerificacion(empleador.getDigitoVerificacion());
                bandeja.setRazonSocial(empleador.getRazonSocial());
                bandeja.setFechaAfiliacion(CalendarUtils.truncarHora(empleador.getFechaCambioEstadoAfiliacion()));
                bandeja.setFechaIngresoBandeja(empleador.getFechaRetiroTotalTrabajadores());
                if(empleador.getCantIngresoBandejaCeroTrabajadores() != null){
                    bandeja.setCantIngresoBandeja(empleador.getCantIngresoBandejaCeroTrabajadores());
                }else{
                    bandeja.setCantIngresoBandeja(Short.valueOf("0"));
                }
                if (empleador.getEstadoAportesEmpleador() != null) {
                    bandeja.setEstadoMorosidad(empleador.getEstadoAportesEmpleador().getDescripcion());
                }
                if (empleador.getPeridoUltimoRecaudo() != null) {
                    bandeja.setUltimoPeriodoPagado(empleador.getPeridoUltimoRecaudo());
                }
                else {
                    bandeja.setUltimoPeriodoPagado(ConstantesPilaBandeja.SIN_PAGO_APORTES);
                }
                if (empleador.getFechaUltimorecaudo() != null) {
                    bandeja.setFechaUltimoPeriodoPagado(CalendarUtils.darFormatoYYYYMMDD(empleador.getFechaUltimorecaudo()));
                }
                else {
                    bandeja.setFechaUltimoPeriodoPagado(ConstantesPilaBandeja.SIN_PAGO_APORTES);
                }

                empleador.setTieneTodosLosTrabajadoresRetirados(Boolean.FALSE);
                // Si la empresa tiene trabajadores asociados entonces buscar si el ultimo retirado fue por PILA
                if (empleador.getCantidadRolafiliados() > 0) {
                    trabajadorCanalRetiro = entityManagerCore
                            .createNamedQuery(NamedQueriesConstants.BUSQUEDA_ROL_AFILIADO_RETIRADO_POR_PILA)
                            .setParameter("idEmpleador", empleador.getIdEmpleador()).getResultList();
                    for (ConsultaCanalDeRetiroEmpleadorDTO trabajador : trabajadorCanalRetiro) {
                        if (trabajador.getCanalRecepcion().equals(CanalRecepcionEnum.PILA)) {
                            empleador.setTieneTodosLosTrabajadoresRetirados(Boolean.TRUE);
                        }
                    }
                }

                bandeja.setTieneReportadoRetiroTotalTrabajadores(empleador.getTieneTodosLosTrabajadoresRetirados());
                bandeja.setTieneHistoricoAportes((empleador.getHistoricoAportes() > 0) ? Boolean.TRUE : Boolean.FALSE);
                bandeja.setTieneHistoricoAfiliaciones((empleador.getHistoricoAfiliaciones() > 0) ? Boolean.TRUE : Boolean.FALSE);
                bandeja.setEsGestionado((empleador.getFechaGestionDesafiliacion() != null) ? Boolean.TRUE : Boolean.FALSE);

                // Calcular el numero de dias en los cuales el empleador se desafiliará
                if (empleador.getFechaGestionDesafiliacion() != null) {
                    calGestionDesafiliacion.setTime(empleador.getFechaGestionDesafiliacion());
                    numDias = CalendarUtils.obtenerDiferenciaEntreFechas(fechaActual, calGestionDesafiliacion, Calendar.DATE);
                    if (numDias < 0) {
                        numDias = 0L;
                    }
                    bandeja.setMantenerAfiliacionPor(numDias);
                }

                bandejaRegistros.add(bandeja);
            }
        }

        logger.debug("Inicia consultarEmpCeroTrabajadoresActivos(String, TipoIdentificacionEnum, String, Short, "
                + "Long, Long, UriInfo, HttpServletResponse)");

        return bandejaRegistros;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#consultarActualizacionDatosEmp(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
     *      java.lang.String, java.lang.Short, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ActualizacionDatosEmpleadorModeloDTO> consultarActualizacionDatosEmp(TipoIdentificacionEnum tipoDocumento,
            String idAportante, Short digitoVerificacion, Long fechaIngresoBandeja) {
        String firmaMetodo = "ConsultasModeloCore.consultarActualizacionDatosEmp(TipoIdentificacionEnum, String, Short, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        List<ActualizacionDatosEmpleadorModeloDTO> resultado = null;
        Long inicioDia = null;
        Long finDia = null;
        
		if (fechaIngresoBandeja != null) {
			LocalDate fechaIngreso = Instant.ofEpochMilli(fechaIngresoBandeja).atZone(ZoneId.systemDefault())
					.toLocalDate();
			inicioDia = fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			fechaIngreso = fechaIngreso.plusDays(1);
			finDia = fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1;
		}
        
        resultado = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.BUSQUEDA_ACTUALIZACION_EMPLEADOR_DV_FECHAS,
                        ActualizacionDatosEmpleadorModeloDTO.class)
                .setParameter(argTipoDocumento, tipoDocumento).setParameter(argNumeroIdentificacion, idAportante)
                .setParameter(argDigitoVerificacion, digitoVerificacion)
                .setParameter(argFechaIngresoInicio, (inicioDia != null) ? new Date(inicioDia) : null)
                .setParameter(argFechaIngresoFin, (finDia != null) ? new Date(finDia) : null).getResultList();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return PilaBandejaBusiness.evaluarResultadoActualizacionDatosEmpleador(resultado);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#ActualizarActualizacionDatosEmp(java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void ActualizarActualizacionDatosEmp(List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion) {
        //se recorren los datos de la lista de actualizacion y se actualizan en bd
        for (ActualizacionDatosEmpleadorModeloDTO registro : listaActualizacion) {
            // con el DTO recibido, se tienen 2 alternativas
            ActualizacionDatosEmpleador ads = registro.convertToEntity();
            ads.setFechaRespuesta(new Date());
            
            // el DTO presenta un ID, con lo cual se hace una actualización
            if(ads.getId() != null){
                entityManagerCore.merge(ads);
            }
            // el DTO no presenta un ID, con lo cual se persiste un registro nuevo
            else{
                entityManagerCore.persist(ads);
            }
            
            /*
            ActualizacionDatosEmpleador ads = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSQUEDA_ACTUALIZACION_EMPLEADOR_ID, ActualizacionDatosEmpleador.class)
                    .setParameter("id", registro.getId()).getSingleResult();
            
            
            ads.setObservaciones(registro.getObservaciones());
            ads.setFechaRespuesta(new Date());
            ads.setEstadoInconsistencia(registro.getEstadoInconsistencia());
            try {
                entityManagerCore.merge(ads);
            } catch (Exception e) {
                logger.error("No se pudo actualizar el archivo");
            }*/
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#actualizarEmpleadoresGestionadosBandejaCero(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void actualizarEmpleadoresGestionadosBandejaCero(List<Long> idEmpleadores, UserDTO user) {
        logger.debug("Inicia actualizarEmpleadoresGestionadosBandejaCero(List<Long>, UserDTO ");

        if (!idEmpleadores.isEmpty()) {
            entityManagerCore.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_FECHA_GESTION_EMPLEADOR)
                    .setParameter("idEmpleadores", idEmpleadores).setParameter("fechaGestion", null).executeUpdate();
        }

        logger.debug("Finaliza actualizarEmpleadoresGestionadosBandejaCero(List<Long>, UserDTO ");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.bandejainconsistencias.interfaces.IConsultasModeloCore#mantenerAfiliacionEmpleadoresBandejaCero(java.util.List,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void mantenerAfiliacionEmpleadoresBandejaCero(List<Long> idEmpleadores, UserDTO user) {
        logger.debug("Inicia mantenerAfiliacionEmpleadoresBandejaCero(List<Long>, UserDTO ");

        List<Empleador> empleadores = null;
        Calendar fechaActual = Calendar.getInstance();

        // Se obtienen dias de inactivacion parametrizados en la caja
        Long diasInactivacion = new Long((String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_INACTIVAR_EMPLEADOR))
                / (3600 * 24 * 1000);

        /* Se suman los dias parametrizados a la fecha actual */
        fechaActual.add(Calendar.DATE, diasInactivacion.intValue());

        if (idEmpleadores != null && !idEmpleadores.isEmpty()) {
            // Se traen los empleadores que correspondan a la lista de id's  
            empleadores = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_LISTA_BANDEJA_GESTIONADA, Empleador.class)
                    .setParameter("idEmpleadores", idEmpleadores).getResultList();
            if (empleadores != null && !empleadores.isEmpty()) {
                for (Empleador empleador : empleadores) {
                    empleador = entityManagerCore.merge(empleador);
                    // Esta pendiente (No tiene fecha de gestion afiliacion)
                    if (empleador.getFechaGestionDesafiliacion() == null) {
                        // Se suma el numero de dias parametrizado por caja a la fecha actual de gestion
                        empleador.setFechaGestionDesafiliacion(fechaActual.getTime());
                    }
                }
            }
        }
        logger.debug("Finaliza mantenerAfiliacionEmpleadoresBandejaCero(List<Long>, UserDTO ");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean buscarPersona(TipoIdentificacionEnum tipoId, String numId) {
        Boolean result = null;
        
        try{
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.BUSCAR_EXISTENCIA_PERSONA, Boolean.class)
                    .setParameter("tipoId", tipoId.name()).setParameter("numId", numId).getSingleResult();
        }catch(NoResultException e){
            result = Boolean.FALSE;
        }
        
        return result;
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PilaEstadoTransitorio> bandejaTransitoriaGestion() {
		List<PilaEstadoTransitorio> acciones = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.BUSCAR_BANDEJA_TRANSITORIA_GESTION, PilaEstadoTransitorio.class)
				.getResultList();
		return acciones;
	}
	
	/**
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param numeroPlanilla
	 * @param fechaInicio
	 * @param fechaFin
	 * @return a
	 */
	@SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PilaEstadoTransitorio> bandejaTransitoriaGestionParam(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, String numeroPlanilla, Long fechaInicio, Long fechaFin) {
	    
	    List<Object[]> resultado = null;
	    List<PilaEstadoTransitorio> result = new ArrayList<>();
	    
	    StoredProcedureQuery s = entityManagerCore.createStoredProcedureQuery(NamedQueriesConstants.BUSCAR_BANDEJA_TRANSITORIA_GESTION_PARAM)
                .registerStoredProcedureParameter("tipoIdentificacion", String.class, ParameterMode.IN)
                .setParameter("tipoIdentificacion", tipoIdentificacion!=null?tipoIdentificacion.toString():"-1")
                .registerStoredProcedureParameter("numeroIdentificacion", String.class, ParameterMode.IN)
                .setParameter("numeroIdentificacion", numeroIdentificacion!=null?numeroIdentificacion:"-1")
                .registerStoredProcedureParameter("numeroPlanilla", String.class, ParameterMode.IN)
                .setParameter("numeroPlanilla", numeroPlanilla!=null?numeroPlanilla:"-1")
                .registerStoredProcedureParameter("fechaInicio", Long.class, ParameterMode.IN)
                .setParameter("fechaInicio", fechaInicio!=null?fechaInicio:-1L)
                .registerStoredProcedureParameter("fechaFin", Long.class, ParameterMode.IN)
                .setParameter("fechaFin", fechaFin!=null?fechaFin:-1L);
                
                
        resultado = s.getResultList();
	    
	    for (Object[] objects : resultado) {
	        PilaEstadoTransitorio pet = new PilaEstadoTransitorio();
	        pet.setAccion(PilaAccionTransitorioEnum.valueOf(objects[1].toString()));
	        pet.setEstado(PilaEstadoTransitorioEnum.valueOf(objects[2].toString()));
	        DateTimeFormatter a = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        pet.setFecha(Date.from(LocalDateTime.parse(objects[3].toString(), a).toInstant(ZoneOffset.UTC)));
	        if(objects[4]!=null){
	            pet.setFechaReanudado(Date.from(LocalDateTime.parse(objects[4].toString(), a).toInstant(ZoneOffset.UTC)));
	        }
	        pet.setPilaIndicePlanilla(Long.valueOf(objects[0].toString()));
	        result.add(pet);
        }
	    
        return result;
	     
    }
	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PilaEstadoTransitorio consultarBandejaTransitoriaGestion(Long idPilaEstadoTransitorio) {
		try {
			return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BANDEJA_TRANSITORIA_GESTION,
					PilaEstadoTransitorio.class).setParameter("id", idPilaEstadoTransitorio).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ExcepcionNovedadPilaModeloDTO> consultarExcepcionNovedadPila(Long idTempNovedad) {
		return entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXCEPCION_NOVEDAD_PILA,
				ExcepcionNovedadPilaModeloDTO.class).setParameter("idTempNovedad", idTempNovedad).getResultList();
	}
	
	@Override
	public Boolean actualizarEstadoBandejaTransitoriaGestion(Long indicePlanilla) {
		int totalAfectados = entityManagerCore
				.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ESTADO_BANDEJA_TRANSITORIA)
				.setParameter("indicePlanilla", indicePlanilla).executeUpdate();
		return (totalAfectados > 0);
	}
	
}
