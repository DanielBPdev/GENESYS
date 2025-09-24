package com.asopagos.historicos.ejb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.entidades.transversal.core.EstadisticasGenesys;
import com.asopagos.historicos.constants.NamedQueriesConstants;
import com.asopagos.historicos.dto.*;
import com.asopagos.historicos.service.HistoricosService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.persistence.StoredProcedureQuery;
/**
 * <b>Descripción:</b> EJB que implementa los métodos para consultar historicos en
 * el modelo de auditoría de la caja de compensacion<b>
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */

@Stateless
public class HistoricosBusiness implements HistoricosService {

    /**
     * Entity manager.
     */
    @PersistenceContext(unitName = "Aud_PU")
    private EntityManager entityManager;
    
    
    /**
     * Entity manager.
     */
    @PersistenceContext(unitName = "BaseCore_PU")
    private EntityManager entityManagerCore;
    
    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(HistoricosBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.historicos.service.HistoricosService#obtenerTrabajadoresActivosPeriodo(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> obtenerTrabajadoresActivosPeriodo(Long startDate, Long endDate, Long idEmpleador) {
        try {
            logger.debug("Inicio de método obtenerCotizantesActivos");
            List<BigInteger> ids = entityManager.createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_ROL_AFILIADO_PERIODO_ACTIVO)
                    .setParameter("endDate", endDate).setParameter("startDate", startDate).setParameter("idEmpleador", idEmpleador)
                    .setParameter("estadoAfiliado", EstadoAfiliadoEnum.ACTIVO.name()).getResultList();
            List<Long> idRoles = new ArrayList<>();
            for (BigInteger id : ids) {
                Long idRol = new Long(id.toString());
                idRoles.add(idRol);
            }
            logger.debug("Fin de método obtenerCotizantesActivos");
            return idRoles;
        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerCotizantesActivos", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /** 
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#obtenerEstadoTrabajador(java.lang.Long, java.lang.Long, java.lang.Long,
     *      java.lang.Long, com.asopagos.enumeraciones.personas.TipoAfiliadoEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoAfiliadoEnum obtenerEstadoTrabajador(Long startDate, Long endDate, Long idEmpleador, Long idAfiliado,
            TipoAfiliadoEnum tipoAfiliado) {
        String firmaMetodo = "HistoricosBusiness.obtenerEstadoTrabajador(Long, Long, Long, Long, TipoAfiliadoEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " :: startDate = " + startDate + ", endDate = " + endDate
                + ", idEmpleador = " + idEmpleador != null ? idEmpleador
                        : "N/A" + ", idAfiliado = " + idAfiliado + ", tipoAfiliado = " + tipoAfiliado);
        
        EstadoAfiliadoEnum result = null;
        try{
            Object[] resultQuery = null;
            if (idEmpleador != null && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_ROL_AFILIADO_PERIODO_EMPLEADOR_AFILIADO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate)).setParameter("idEmpleador", idEmpleador)
                        .setParameter("idAfiliado", idAfiliado).getSingleResult();
            }
            else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)) {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_INDEPENDIENTE_ESTADO_PERIODO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate))
                        .setParameter("idAportante", idAfiliado).getSingleResult();
            }
            else {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_PENSIONADO_ESTADO_PERIODO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate))
                        .setParameter("idAportante", idAfiliado).getSingleResult();
            }

            if (resultQuery != null && resultQuery[1] != null) {
                result = EstadoAfiliadoEnum.valueOf(resultQuery[1].toString());
            }
        }catch(NoResultException e){
            logger.debug(firmaMetodo + " :: sin estado");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#obtenerEstadoEmpleadorPeriodo(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoEmpleadorEnum obtenerEstadoEmpleadorPeriodo(Long startDate, Long endDate, Long idEmpleador) {
        String firmaServicio = "HistoricosBusiness.obtenerEstadoEmpleadorPeriodo(Long, Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EstadoEmpleadorEnum result = obtenerEstadoAportantePeriodo(startDate, endDate, idEmpleador,
                TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return result;
    }

    /** TODO
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#obtenerEstadoAportantePeriodo(java.lang.Long, java.lang.Long, java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstadoEmpleadorEnum obtenerEstadoAportantePeriodo(Long startDate, Long endDate, Long idAportante,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "HistoricosBusiness.obtenerEstadoAportantePeriodo(Long, Long, Long, TipoSolicitanteMovimientoAporteEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " :: startDate = " + startDate + ", endDate = " + endDate
                + ", idAportante = " + idAportante + ", tipoSolicitante = " + tipoSolicitante);
        
        EstadoEmpleadorEnum result = null;
        try{
            Object[] resultQuery = null;
            
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_EMPLEADOR_ESTADO_PERIODO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate))
                        .setParameter("idEmpleador", idAportante).getSingleResult();
            }
            else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)) {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_INDEPENDIENTE_ESTADO_PERIODO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate))
                        .setParameter("idAportante", idAportante).getSingleResult();
            }
            else {
                resultQuery = (Object[]) entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_PENSIONADO_ESTADO_PERIODO)
                        .setParameter("endDate", new Date(endDate)).setParameter("startDate", new Date(startDate))
                        .setParameter("idAportante", idAportante).getSingleResult();
            }

            if (resultQuery != null && resultQuery[1] != null) {
                result = EstadoEmpleadorEnum.valueOf(resultQuery[1].toString());
            }
        }catch(NoResultException e){
            logger.debug(firmaMetodo + " :: sin estado");
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

   

    /**
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#buscarDireccionRepresentanteLegal(java.lang.Long,
     *      com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public UbicacionDTO buscarDireccion(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            TipoUbicacionEnum tipoUbicacion, Long fechaRevision) {
        try {
            logger.debug("Inicio de método buscarDireccionRepresentanteLegal(String)");
            UbicacionDTO ubicacionDTO = null;
            List<Object[]> datosUbicacion = new ArrayList<>();
            String consulta = NamedQueriesConstants.HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_PERSONA;
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                consulta = NamedQueriesConstants.HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_EMPLEADOR;
                datosUbicacion = entityManager.createNamedQuery(consulta).setParameter("idPersona", idPersona)
                        .setParameter("tipoUbicacion", tipoUbicacion).setParameter("fechaRevision", fechaRevision).setMaxResults(0)
                        .getResultList();
            }
            else {
                datosUbicacion = entityManager.createNamedQuery(consulta).setParameter("idPersona", idPersona)
                        .setParameter("fechaRevision", fechaRevision).setMaxResults(0).getResultList();
            }
            if (!datosUbicacion.isEmpty()) {
                ubicacionDTO = new UbicacionDTO();
                for (Object[] ubicacion : datosUbicacion) {
                    if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                        ubicacionDTO = ubicacionDTO.asignarDatosUbicacionDTO(ubicacion);
                    }
                    else {
                        ubicacionDTO = ubicacionDTO.asignarDatosUbicacionAfiliadoDTO(ubicacion);
                    }
                }
            }
            logger.debug("Finaliza método buscarDireccionRepresentanteLegal(String)");
            return ubicacionDTO;
        } catch (Exception e) {
            logger.error("Ocurrió un error en buscarDireccionRepresentanteLegal", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.historicos.service.HistoricosService#buscarDireccionPorTipoUbicacion(java.lang.Long,
     * com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, java.util.List, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UbicacionDTO> buscarDireccionPorTipoUbicacion(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            List<TipoUbicacionEnum> tiposUbicacion, Long fechaRevision) {
        try {
            logger.debug(
                    "Inicio de método buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)");
            UbicacionDTO ubicacionDTO = null;
            List<UbicacionDTO> lstUbicaciones = new ArrayList<>();
            List<Object[]> datosUbicacion = new ArrayList<>();
            String consulta = NamedQueriesConstants.HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_PERSONA;
            if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(tipoSolicitante)) {
                consulta = NamedQueriesConstants.HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_EMPLEADOR;
                if (tiposUbicacion != null && !tiposUbicacion.isEmpty()) {
                    for (TipoUbicacionEnum ubicacionTipo : tiposUbicacion) {
                        datosUbicacion.addAll(entityManager.createNamedQuery(consulta).setParameter("idPersona", idPersona)
                                .setParameter("tipoUbicacion", ubicacionTipo.name()).setParameter("fechaRevision", fechaRevision)
                                .setMaxResults(0).getResultList());
                    }
                }
            }
            else {
                datosUbicacion = entityManager.createNamedQuery(consulta).setParameter("idPersona", idPersona)
                        .setParameter("fechaRevision", fechaRevision).getResultList();
            }
            if (!datosUbicacion.isEmpty()) {
                lstUbicaciones = new ArrayList<>();
                for (Object[] ubicacion : datosUbicacion) {
                    ubicacionDTO = new UbicacionDTO();
                    ubicacionDTO = ubicacionDTO.asignarDatosUbicacionDTO(ubicacion);
                    lstUbicaciones.add(ubicacionDTO);
                }
            }
            logger.debug(
                    "Finaliza método buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)");
            return !lstUbicaciones.isEmpty() ? lstUbicaciones : null;
        } catch (Exception e) {
            logger.error(
                    "Ocurrió un error en buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.historicos.service.HistoricosService#buscarHistorialUbicacionRolContactoEmpledor(java.lang.Long,
     * com.asopagos.enumeraciones.personas.TipoRolContactoEnum, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UbicacionDTO> buscarHistorialUbicacionRolContactoEmpledor(Long idPersona, TipoRolContactoEnum tipoRolContactoEmpleador,
            Long fechaRevision) {
        try {
            UbicacionDTO ubicacionDTO = null;
            List<UbicacionDTO> lstUbicaciones = new ArrayList<>();
            List<Object[]> datosUbicacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.HISTORICOS_CONSULTAR_UBICACION_ROL_CONTACTO_EMPLEADOR)
                    .setParameter("idPersona", idPersona).setParameter("tipoRolContactoEmpleador", tipoRolContactoEmpleador)
                    .setParameter("fechaRevision", fechaRevision).getResultList();
            if (!datosUbicacion.isEmpty()) {
                lstUbicaciones = new ArrayList<>();
                for (Object[] ubicacion : datosUbicacion) {
                    ubicacionDTO = new UbicacionDTO();
                    ubicacionDTO = ubicacionDTO.asignarDatosUbicacionDTO(ubicacion);
                    lstUbicaciones.add(ubicacionDTO);
                }
            }
            return !lstUbicaciones.isEmpty() ? lstUbicaciones : null;
        } catch (Exception e) {
            logger.error(
                    "Ocurrió un error en buscarDireccionPorTipoUbicacion(Long,TipoSolicitanteMovimientoAporteEnum,List<TipoUbicacionEnum>,Long)",
                    e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

 

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ContactoPersona360DTO obtenerHistoricoContactoPersona(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado) {
        
        logger.debug("Inicio de método obtenerHistoricoContactoPersona(TipoIdentificacionEnum, String)");
        ContactoPersona360DTO contacto = new ContactoPersona360DTO();
        
        try {

            List<String> historialTelFijo = new ArrayList<>();
            List<String> historialCelular = new ArrayList<>();
            List<String> historialCorreo = new ArrayList<>();
            
            List<Object[]> respuestaSP = (List<Object[]>)entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_TELEFONOS_Y_CORREOS)
                    .setParameter("tipoIdentificacion", tipoIdAfiliado.name())
                    .setParameter("numeroIdentificacion", numeroIdAfiliado)
                    .getResultList();
            
            if(respuestaSP != null && !respuestaSP.isEmpty())
            {
                for (Object[] object : respuestaSP) {
                    if(object[0] != null){
                        historialTelFijo.add(object[0].toString());
                    }else{
                        historialTelFijo.add("");
                    }
                    if(object[1] != null){
                        historialCelular.add(object[1].toString());
                    }
                    else{
                        historialCelular.add("");
                    }
                    if(object[2] != null){
                        historialCorreo.add(object[2].toString());
                    }
                    else{
                        historialCorreo.add("");
                    }
                }
            }
            
            contacto.setHistorialTelFijo(historialTelFijo);
            contacto.setHistorialCelular(historialCelular);
            contacto.setHistorialCorreo(historialCorreo);
            
            logger.debug("Fin de método obtenerHistoricoContactoPersona(TipoIdentificacionEnum, String)");
            return contacto;
        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerHistoricoContactoPersona(TipoIdentificacionEnum, String)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#consultarResumenGruposFamiliares(java.lang.String,
     *      com.asopagos.enumeraciones.personas.TipoIdentificacionEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PersonaComoAdminSubsidioDTO> consultarResumenGruposFamiliares(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        String firma = "consultarResumenGruposFamiliares(" + numeroIdentificacion + "," + tipoIdentificacion + ")";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);

        List<PersonaComoAdminSubsidioDTO> listadoGruposFamiliares = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_HISTORICO_ADMINISTRADOR_SUBSIDIO_GRUPO,
                        PersonaComoAdminSubsidioDTO.class)
                .setParameter("tipoIdAdminSubsidio", tipoIdentificacion.name()).setParameter("numeroIdAdminSubsidio", numeroIdentificacion)
                .getResultList();

        for (PersonaComoAdminSubsidioDTO personaComoAdminSubsidioDTO : listadoGruposFamiliares) {
            personaComoAdminSubsidioDTO.setBeneficiarios(entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR, BeneficiarioGrupoFamiliarDTO.class)
                    .setParameter("idGrupoFamiliar", personaComoAdminSubsidioDTO.getIdGrupoFamiliar()).getResultList());
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return listadoGruposFamiliares;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#obtenerGruposFamiliaresAfiPrincipal(java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PersonaComoAfiPpalGrupoFamiliarDTO> obtenerGruposFamiliaresAfiPrincipal(String numeroIdentificacion,
            String tipoIdentificacion) {
        String firmaMetodo = "obtenerGruposFamiliaresAfiPrincipal(" + numeroIdentificacion + ", " + tipoIdentificacion + ")";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
 
        List<PersonaComoAfiPpalGrupoFamiliarDTO> grupos = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.OBTENER_GRUPOS_FAMILIARES_AFI_PPAL, PersonaComoAfiPpalGrupoFamiliarDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion)
                .setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();

        for (PersonaComoAfiPpalGrupoFamiliarDTO grupo : grupos) {
            grupo.setBeneficiarios(entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO_AFILIADO_2, BeneficiarioGrupoFamiliarDTO.class)
                    .setParameter("idGrupoFamiliar", grupo.getIdGrupoFamiliar()).getResultList());
                     try{
                        grupo.setMedioDePagoDTO(entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.OBTENER_MEDIO_DE_PAGO, MedioDePagoModeloDTO.class)
                        .setParameter("idAdminSubsidio", grupo.getIdAdministradorSubsidio())
                        .setParameter("idGrupoFamiliar", grupo.getIdGrupoFamiliar()).getSingleResult());
                    }catch(NoResultException e){
                        logger.error(grupo.getIdAdministradorSubsidio()+" :obtenerGruposFamiliaresAfiPrincipal- Consulta Vacia: "+e); 
                        grupo.setMedioDePagoDTO(null); 
                    }

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return grupos;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.historicos.service.HistoricosService#obtenerGruposFamiliaresAfiPrincipalID(java.lang.String, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<PersonaComoAfiPpalGrupoFamiliarIDDTO> obtenerGruposFamiliaresAfiPrincipalID(String numeroIdentificacion,
            String tipoIdentificacion) {
        String firmaMetodo = "obtenerGruposFamiliaresAfiPrincipalID(" + numeroIdentificacion + ", " + tipoIdentificacion + ")";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<PersonaComoAfiPpalGrupoFamiliarIDDTO> grupos = entityManagerCore
                .createNamedQuery(NamedQueriesConstants.OBTENER_GRUPOS_FAMILIARES_AFI_PPAL, PersonaComoAfiPpalGrupoFamiliarIDDTO.class)
                .setParameter("tipoIdentificacion", tipoIdentificacion).setParameter("numeroIdentificacion", numeroIdentificacion)
                .getResultList();

        for (PersonaComoAfiPpalGrupoFamiliarIDDTO grupo : grupos) {
            grupo.setBeneficiarios(entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO_AFILIADO_3, BeneficiarioIDGrupoFamiliarDTO.class)
                    .setParameter("idGrupoFamiliar", grupo.getIdGrupoFamiliar()).getResultList());
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return grupos;
    }

    @Override
    public void actualizarEstadisticasGenesys(){
        try{
            StoredProcedureQuery query = entityManagerCore.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SCHEDULE_ACTUALIZAR_ESTADISTICAS_GENESYS);
            query.execute();
        }catch(Exception e){
            logger.info("Fallo el servicio de actualizarEstadisticasGenesys "+ e);
        }
    }

    @Override
    public HistoricoEstadisticasGenesysDTO obtenerEstadisticasGenesys() {
        HistoricoEstadisticasGenesysDTO estadisticas = new HistoricoEstadisticasGenesysDTO();
        try {
            EstadisticasGenesys estadisticasGenesys = entityManagerCore.createNamedQuery(NamedQueriesConstants.OBTENER_ESTADISTICAS_GENESYS, EstadisticasGenesys.class)
                    .setMaxResults(1)
                    .getSingleResult();
            estadisticas.convertToDTO(estadisticasGenesys);

        } catch (Exception e) {
            logger.info("Fallo el servicio de obtenerEstadisticasGenesys " + e);
        }
        return estadisticas;
    }
}
