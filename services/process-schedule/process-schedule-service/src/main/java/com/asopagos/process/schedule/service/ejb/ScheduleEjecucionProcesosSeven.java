package com.asopagos.process.schedule.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.process.schedule.api.ScheduleAbstract;
import com.asopagos.process.schedule.service.constants.NamedQueriesConstants;

@Singleton
@Startup
public class ScheduleEjecucionProcesosSeven extends ScheduleAbstract{

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.EJECUCION_PROCESOS_SEVEN;
    
    /**
     * Entity manager para la clase validador.
     */
    @PersistenceContext(unitName = "process_schedule_PU")
    protected EntityManager emSeven;
    
    /* (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#init()
     */
    @PostConstruct
    @Override
    public void init() {
        start();
    }
    
    /* (non-Javadoc)
     * @see com.asopagos.process.schedule.api.ScheduleAbstract#run(javax.ejb.Timer)
     */
    @Timeout
    @Override
    public void run(Timer timer){
        try {
            logger.debug("Inicio de método ScheduleProcesosSeven.run(Timer timer)");
            initContextUsuarioSistema();
            
            StoredProcedureQuery procedimientoFaClien = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_FACLIEN);
            procedimientoFaClien.execute();
            
            StoredProcedureQuery procedimientoPoPvdor = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_POPVDOR);
            procedimientoPoPvdor.execute();
            
            StoredProcedureQuery procedimientoGnTerceEmpresas = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GNTERCE_EMPRESAS);
            procedimientoGnTerceEmpresas.execute();
            
            StoredProcedureQuery procedimientoGnTercePersonas = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GNTERCE_PERSONAS);
            procedimientoGnTercePersonas.execute();
            
            StoredProcedureQuery procedimientoRcnmContYRpocXPagSubsidios = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_RCNMCONT_Y_RPOCXPAG_SUBSIDIOS);
            procedimientoRcnmContYRpocXPagSubsidios.execute();
            
            StoredProcedureQuery procedimientoGestionarAportesCorreccionesERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_APORTES_CORRECCIONES_ERP);
            procedimientoGestionarAportesCorreccionesERP.execute();
            
            StoredProcedureQuery procedimientoGestionarAportesDevolucionesERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_APORTES_DEVOLUCIONES_ERP);
            procedimientoGestionarAportesDevolucionesERP.execute();
            
            StoredProcedureQuery procedimientoGestionarAportesEmpresasERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_APORTES_EMPRESAS_ERP);
            procedimientoGestionarAportesEmpresasERP.execute();
            
            StoredProcedureQuery procedimientoGestionarAportesERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_APORTES_ERP);
            procedimientoGestionarAportesERP.execute();
            
            StoredProcedureQuery procedimientoGestionarCarteraERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_CARTERA_ERP);
            procedimientoGestionarCarteraERP.execute();
            
            StoredProcedureQuery procedimientoGestionarRcnmContAfiliaPersonas = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_RCNMCONT_AFILIA_PERSONAS);
            procedimientoGestionarRcnmContAfiliaPersonas.execute();
            
            StoredProcedureQuery procedimientoGestionarAsignacionSubsidioFOVISERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_ASIGNACION_SUBSIDIO_FOVIS_ERP);
            procedimientoGestionarAsignacionSubsidioFOVISERP.execute();

            StoredProcedureQuery procedimientoGestionarSubsidioFOVISERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_SUBSIDIO_FOVIS_ERP);
            procedimientoGestionarSubsidioFOVISERP.execute();
            
            StoredProcedureQuery procedimientoGestionarAportesPILARtsConsdERP = emSeven.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_SP_GESTIONAR_APORTES_PILA_RTSCONSD_ERP);
            procedimientoGestionarAportesPILARtsConsdERP.execute();
            
            logger.debug("Fin de método ScheduleProcesosSeven.run(Timer timer)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en ScheduleProcesosSeven.run(Timer timer)", e);
            this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, 
                    getCurrentProcess(),
                    TipoResultadoProcesoEnum.EJECUCION);
        }
    }
    

    /**
     * @return the currentprocess
     */
    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
    
}
