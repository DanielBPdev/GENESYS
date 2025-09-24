package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.HistoricoAfiliacionEmpleador360DTO;
import com.asopagos.comunicados.dto.HistoricoEstadoEmpleador360DTO;
import com.asopagos.comunicados.service.HistoricoAfiliacionesService;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 * con la consulta de registros historicos para afiliaciones<br/>
 * <b>Módulo:</b> Vistas 360 <br/>
 *
 * @author Steven Quintero González <a href="mailto:squintero@heinsohn.com.co"> squintero@heinsohn.com.co</a>
 */
@Stateless
public class HistoricoAfiliacionesBusiness implements HistoricoAfiliacionesService{
    
    /**
     * Inject del EJB para consultas en modelo reportes entityManagerReporte
     */
    @Inject
    private IConsultasModeloReporte consultasReporte;

    @PersistenceContext(unitName = "comunicados_PU")
    private EntityManager entityManagerCore;
    
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(HistoricoAfiliacionesBusiness.class);
    
    
    /* (non-Javadoc)
     * @see com.asopagos.comunicados.service.HistoricosService#consultarHistoricoAfiliacionesEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<HistoricoAfiliacionEmpleador360DTO> consultarHistoricoAfiliacionesEmpleador(TipoIdentificacionEnum tipoIdEmpleador,
            String numeroIdEmpleador) {
        String firma = "consultarHistoricoAfiliacionesEmpleador(TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        List<HistoricoAfiliacionEmpleador360DTO> historicoAfiliaciones = new ArrayList<>();
        try {
            
        	//ha = historicoAfiliacion. se pone una sigla para mejorar la lectura del código
            List<Object[]> ha = (List<Object[]>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFILIACION_EMPLEADOR_CORE)
                    .setParameter("tipoIdEmpleador", tipoIdEmpleador.name())
                    .setParameter("numeroIdEmpleador", numeroIdEmpleador)
                    .getResultList();

            if(ha != null && !ha.isEmpty()){
            	
            	for (int i = 0; i < ha.size(); i++) {
            		HistoricoAfiliacionEmpleador360DTO afiliacion = new HistoricoAfiliacionEmpleador360DTO();
                    
            		afiliacion.setEstadoAnterior(ha.get(i)[1] != null ? ha.get(i)[1].toString() : null);
                    
                    EstadoAfiliadoEnum estadoAfiliacion = afiliacion.getEstadoAnterior() != null ? EstadoAfiliadoEnum.valueOf(afiliacion.getEstadoAnterior()) : null;
                    
                    if(estadoAfiliacion != null){
                    	if(EstadoAfiliadoEnum.INACTIVO.equals(estadoAfiliacion)
                    			|| EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES.equals(estadoAfiliacion)){
                    		//en este caso, el registro histórico de afiliación guarda la fecha en que el afiliado 
                    		//quedó inactivo, por lo tanto la fecha de ingreso es la misma fecha de ingreso del registro anterior(si no existe, 
                    		//sería la misma fehca de ingreso obtenida para este registro) y la de retiro es la obtenida en la consulta.
                    		afiliacion.setFechaIngreso(((i-1) >= 0 && ha.get(i-1)[2] != null) ? ha.get(i-1)[2].toString().substring(0,10) : (ha.get(i)[2] != null ? ha.get(i)[2].toString().substring(0,10) : null));
                    		afiliacion.setFechaRetiro(ha.get(i)[2] != null ? ha.get(i)[2].toString().substring(0,10) : null);
                    	}
                    	else if(EstadoAfiliadoEnum.ACTIVO.equals(estadoAfiliacion)
                    			|| EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION.equals(estadoAfiliacion)){
                    		//en este caso, según nota del mantis 250046, no se debe mostrar la fecha de retiro
                    		afiliacion.setFechaIngreso(ha.get(i)[2] != null ? ha.get(i)[2].toString().substring(0,10) : null);
                    		afiliacion.setFechaRetiro(null);
                    	}
                    }
                    else{
                    	afiliacion.setFechaIngreso(ha.get(i)[2] != null ? ha.get(i)[2].toString().substring(0,10) : null);
                    	afiliacion.setFechaRetiro(null);
                    }
                    
                    afiliacion.setMotivo(ha.get(i)[3] != null ? ha.get(i)[3].toString() : null);
                    // Ajuste glpi 64916 motivo de desafiliacion 0 trabajadores - se ajusta el numero de empleadores para que sea 0
                    afiliacion.setNumeroTrabajadores(ha.get(i)[4] != null ? ha.get(i)[4].toString() : "0");
                    
                    historicoAfiliaciones.add(afiliacion);
    			}
            	
            	if(historicoAfiliaciones != null && !historicoAfiliaciones.isEmpty()){
            		Collections.reverse(historicoAfiliaciones);
            	}
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firma);
            return historicoAfiliaciones;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /* (non-Javadoc)
     * @see com.asopagos.comunicados.service.HistoricoAfiliacionesService#consultarHistoricoEstadosEmpleador(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<HistoricoEstadoEmpleador360DTO> consultarHistoricoEstadosEmpleador(TipoIdentificacionEnum tipoIdEmpleador,
            String numeroIdEmpleador, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        String firma = "consultarHistoricoEstadosEmpleador(TipoIdentificacionEnum, String, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firma);
        List<HistoricoEstadoEmpleador360DTO> historicoEstados = new ArrayList<>();
        try {
            //consulta el estado actual en core para el empleador
            List<Object[]> estadoActualCore = (List<Object[]>)entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_EMPLEADOR_CORE)
                    .setParameter("tipoIdEmpleador", tipoIdEmpleador.name())
                    .setParameter("numeroIdEmpleador", numeroIdEmpleador)
                    .getResultList();
            
            if(estadoActualCore != null && !estadoActualCore.isEmpty()){
                
                
                for (Object[] obj : estadoActualCore) {
                    HistoricoEstadoEmpleador360DTO estado = new HistoricoEstadoEmpleador360DTO();
                    
                    estado.setEstado(obj[0] != null ? obj[0].toString() : null);
                    estado.setFechaMovimiento(obj[1] != null ? obj[1].toString().substring(0,10) : null);
                    
                    historicoEstados.add(estado);
                }
            
            }
            
            if(historicoEstados != null && !historicoEstados.isEmpty()){
            	Collections.reverse(historicoEstados);
            }
            
            logger.debug(ConstantesComunes.FIN_LOGGER + firma);
            return historicoEstados;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
}
