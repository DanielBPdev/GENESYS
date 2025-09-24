package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarTiposAfiliacionAfiliado;
import com.asopagos.aportes.clients.ConsultarCotizantesVista360Persona;
import com.asopagos.aportes.clients.ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.CertificadoDTO;
import com.asopagos.dto.AportePeriodoCertificadoDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.rest.exception.TechnicalException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ObtenerSumatoriaAportes extends ConsultaReporteComunicadosAbs {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ObtenerSumatoriaAportes.class);

    /**
     * Mapa con los parametros adicionales para resolver el comunicado
     */
    private Map<String, Object> params = null;

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params
     *        the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#init(java.util.Map)
     */
    @Override
    public void init(Map<String, Object> params) {
        setParams(params);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
     */
    @Override
    public String getReporte(EntityManager em) {
        return null;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getCertificado(EntityManager ...em){
    	StringBuilder htmlContent;
		try {
			logger.debug("Inicia el método getReporte(EntityManager) para ConsultaTablaComunicado145");
			BigDecimal result = BigDecimal.ZERO;

			DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
			symbols.setGroupingSeparator('.');

			DecimalFormat format = new DecimalFormat("#,###", symbols);
			
			CertificadoDTO certificadoDTO = em[0]
					.createNamedQuery(NamedQueriesConstants.CONSULTA_CERTIFICADO_POR_ID, CertificadoDTO.class)
					.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD,
							params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD))
					.getSingleResult();
			if (certificadoDTO != null && !certificadoDTO.getGeneradoComoEmpleador()) {
				PersonaModeloDTO per = null;
				ConsultarPersona buscarPersonas = new ConsultarPersona(certificadoDTO.getIdPersona());
				buscarPersonas.execute();
				per = buscarPersonas.getResult();
				if(per!=null)
					result=consultaHistoricoSumAporte(em[0], per.getTipoIdentificacion(),per.getNumeroIdentificacion(), false, Short.valueOf(certificadoDTO.getAnio()), per.getIdPersona());
			} else {
				Empleador emp = null;		
				ConsultarEmpleador buscarEmpleador = new ConsultarEmpleador(certificadoDTO.getIdEmpleador());
				buscarEmpleador.execute();
				emp = buscarEmpleador.getResult();
				if(emp!=null)
					result=consultaHistoricoSumAporte(em[0], emp.getEmpresa().getPersona().getTipoIdentificacion(), emp.getEmpresa().getPersona().getNumeroIdentificacion(), true, Short.valueOf(certificadoDTO.getAnio()),null);
			}
			htmlContent = new StringBuilder();
			if(result!=null){
            	htmlContent.append("$").append(format.format(result)).append("");
			}
		}catch (Exception e) {
			logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
			throw new TechnicalException(e.getMessage());
		}
		return htmlContent.toString();
    }
    
	private BigDecimal consultaHistoricoSumAporte(EntityManager em,TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Boolean empleador, Short anio, Long idPersona){
		BigDecimal sumAporte = BigDecimal.ZERO;
		String query;
		if(empleador){
			query=NamedQueriesConstants.CONSULTA_TOTAL_APORTES_EMPLEADOR;sumAporte = (BigDecimal) em
	                .createNamedQuery(query)
	                .setParameter("numeroIdentificacion", numeroIdentificacion)
	                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
	                .setParameter("anio", anio).getSingleResult();
		}else{
			//query=NamedQueriesConstants.CONSULTA_TOTAL_APORTES_PERSONA;
			
			ConsultarRecaudoDTO recaudo = new ConsultarRecaudoDTO();
	        List<TipoAfiliadoEnum> afiliaciones = new ArrayList<>();
	        List<Long> cotizantesRslt = new ArrayList<>();
	        List<BigDecimal> aportes = new ArrayList<>();

	        ConsultarTiposAfiliacionAfiliado afiliacionesSrv = new ConsultarTiposAfiliacionAfiliado(numeroIdentificacion, tipoIdentificacion);
	        afiliacionesSrv.execute();
	        afiliaciones = afiliacionesSrv.getResult();

	        ConsultarCotizantesVista360Persona cotizantesSrv = new ConsultarCotizantesVista360Persona(idPersona);
	        cotizantesSrv.execute();
	        cotizantesRslt = cotizantesSrv.getResult();

	        Calendar fechaInicio = new GregorianCalendar(anio, 0, 1);
	        Calendar fechaFin = new GregorianCalendar(anio, 11, 31);
	        recaudo.setFechaInicio(fechaInicio.getTimeInMillis());
	        recaudo.setFechaFin(fechaFin.getTimeInMillis());
	        recaudo.setTipoIdentificacion(tipoIdentificacion);
	        recaudo.setNumeroIdentificacion(numeroIdentificacion);
	        recaudo.setListaIdsAporteGeneral(cotizantesRslt);
	        
	        List<CuentaAporteDTO> aportesTotales = new ArrayList<>();
	        

	        for (TipoAfiliadoEnum tipoAfiliado : afiliaciones) {
	            switch (tipoAfiliado) {
	                case TRABAJADOR_DEPENDIENTE:
	                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
	                    break;
	                case TRABAJADOR_INDEPENDIENTE:
	                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE);
	                    break;
	                case PENSIONADO:
	                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
	                    break;
	            }
	            ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal aportesSrv = new ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal(
	                    TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL, recaudo);
	            aportesSrv.execute();
	            aportesTotales.addAll(aportesSrv.getResult());
	            
	        }
	        for (CuentaAporteDTO cuentaAporteDTO : aportesTotales) {
                sumAporte = sumAporte.add(cuentaAporteDTO.getTotalAporte());
            }
		}
		return sumAporte;
	}
}
