package com.asopagos.reportes.ejb;

import java.io.ByteArrayInputStream;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.ReporteGiassEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.giass.ReporteGiassAbstract;
import com.asopagos.reportes.giass.ReporteGiassFactory;
import com.asopagos.reportes.service.ReportesGiassService;

@Stateless
public class ReportesGiassBusiness implements ReportesGiassService {
	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ReportesNormativosBusiness.class);

	/** Entity Manager */
	@PersistenceContext(unitName = "core_PU")
	private EntityManager entityManagerCore;
	
    /** Entity Manager */
    @PersistenceContext(unitName = "reportes_PU")
    private EntityManager entityManagerReportes;

	@Override
	public Response generarReporteTabla(ReporteGiassEnum reporteGiassEnum) {
		EntityManager entityManager;
    	
    	switch (reporteGiassEnum) {
		case AFILIACIONES_EMPLEADORES :  
			entityManager = entityManagerReportes;
			break;			
		default:
			entityManager = entityManagerCore;
			break;
		}
    	
		ReporteGiassAbstract reporteGiass;
		try {
			reporteGiass = ReporteGiassFactory.getReporteGiass(reporteGiassEnum, entityManager);
		} catch (Exception ex) {
			return Response.serverError().entity(ex).build();
		}
		Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(reporteGiass.generarReporte()));
		response.header("Content-Type", FormatoReporteEnum.TXT.getMimeType() + ";charset=utf-8");
		response.header("Content-Disposition", "attachment; filename=" + reporteGiass.fileName + "." +  FormatoReporteEnum.TXT);
		return response.build();
	}
}