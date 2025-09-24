package com.asopagos.process.schedule.service.ejb;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.enumeraciones.core.ResultadoEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.TipoResultadoProcesoEnum;
import com.asopagos.pila.clients.ConciliarArchivosOIyOF;
import com.asopagos.pila.composite.clients.ReprocesarPlanillasPendientesConciliacion;
import com.asopagos.pila.composite.clients.ConciliarOIOFyProcesarPilaManual;
import com.asopagos.pila.composite.clients.ReprocesarB3Aut;
import com.asopagos.pila.composite.clients.ProcesarAutomaticoPlanillaManual;
import com.asopagos.process.schedule.api.ScheduleAbstract;

/**
 * <b>Descripción:</b> EJB Singleton que ejecuta el proceso automático para preparar y procesar planillas pila.
 * @author Jorge Roa <jroa@heinsohn.com.co>
 */
@Singleton
@Startup
public class ScheduleConciliarPlanillasPila extends ScheduleAbstract {

    /**
     * Proceso actual. 
     */
    private static final ProcesoAutomaticoEnum CURRENT_PROCESS = ProcesoAutomaticoEnum.CONCILIAR_PLANILLAS_PILA;
    
	
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
		    logger.info("Inicio de método ConciliarArchivosOIyOF.run(Timer timer)");
		    // Se indica el usuario al contexto
		    initContextUsuarioSistema();

		    
		    // invocación del servicio que ejecuta de forma asíncrona el servicio ArchivosPilaService.conciliarArchivosOIyOF
		    // y posteriormente el servicio de PilaCompositeService.procesarAutomaticoPlanillaManual
		    ConciliarOIOFyProcesarPilaManual service2 = new ConciliarOIOFyProcesarPilaManual();
		    service2.execute();


			// Invoca servicio que reprocesa las planillas en B3 por inconsistencia
			logger.info("Inicio procesamiento B3aut");
		    ReprocesarB3Aut service3 = new ReprocesarB3Aut();
		    service3.execute();
			// Invoca servicio que reprocesa las planillas en B3 por inconsistencia
			logger.info("finaliza procesamiento B3aut");

			
			// Invocacion del servicio que ejecuta el procesamiendo para mundo 2 de las planillas 
						
			logger.info("Fin de método ReprocesarPlanillasPendientesConciliacion.run(Timer timer)");
			ReprocesarPlanillasPendientesConciliacion service = new ReprocesarPlanillasPendientesConciliacion();	
			service.execute();

			

//		    ConciliarArchivosOIyOF conciliar = new ConciliarArchivosOIyOF();
//		    conciliar.execute();
			
			logger.debug("Fin de método ConciliarArchivosOIyOF.run(Timer timer)");
		} catch (Exception e) {
			logger.error("Ocurrió un error inesperado en ConciliarArchivosOIyOF.run(Timer timer)", e);
			this.crearLogEjecucionProceso(ResultadoEjecucionProcesoEnum.FALLIDO, 
			        getCurrentProcess(),
					TipoResultadoProcesoEnum.EJECUCION);
		}
	}

    /**
     * @return the currentprocess
     */    @Override
    public ProcesoAutomaticoEnum getCurrentProcess() {
        return CURRENT_PROCESS;
    }
	
}
