package com.asopagos.comunicados.ejb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Query;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarTiposAfiliacionAfiliado;
import com.asopagos.afiliados.clients.ObtenerEmpleadoresRelacionadosAfiliado;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
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
import com.asopagos.pila.clients.ConsultarAportePeriodo;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que contiene la consulta del comunicado 145 y
 * consulta la tabla del histórico de afiliaciones<br/>
 * <b>Módulo:</b> Asopagos - HU Vista 360<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero D.</a>
 */

public class ConsultaTablaComunicado145 extends ConsultaReporteComunicadosAbs {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(ConsultaTablaComunicado145.class);
	
	/**
	 * Claves de referencia para el reporte del historico
	 */
	private final int FECHA_AFILIACION = 0;
	
	private final int FECHA_RETIRO = 1;
	
	private final int TIPO_AFILIADO = 2;
	
	/**
	 * Clave de referencia para el estado ACTIVO de un aportante o cotizante
	 */
	private final String ESTADO_ACTIVO = "ACTIVO";
	
	/**
	 * Clave de referencia para el estado INACTIVO de un aportante o cotizante
	 */
	private final String ESTADO_INACTIVO = "INACTIVO";
	

	/**
	 * Mapa con los parametros de consulta
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
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, Object> params) {
		setParams(params);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.ejb.ConsultaReporteComunicadosAbs#getReporte(javax.persistence.EntityManager)
	 */
	@Override
	public String getReporte(EntityManager em) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<List<Object[]>>consultaHistoricoPersona(EntityManager em, CertificadoDTO certificadoDTO){
		PersonaModeloDTO per = null;
		ConsultarPersona buscarPersonas = new ConsultarPersona(certificadoDTO.getIdPersona());
		buscarPersonas.execute();
		per = buscarPersonas.getResult();
		List<List<Object[]>> afiliaciones = new ArrayList<>();
		String tipoIdentificacionEmp="";
		String numeroIdentificacionEmp="";
		if(per!=null){
			Empleador emp = new Empleador();
			if(certificadoDTO.getIdEmpleador()!=null){	
				ConsultarEmpleador buscarEmpleador = new ConsultarEmpleador(certificadoDTO.getIdEmpleador());
				buscarEmpleador.execute();
				emp = buscarEmpleador.getResult();
				tipoIdentificacionEmp = emp.getEmpresa().getPersona().getTipoIdentificacion().name();
				numeroIdentificacionEmp = emp.getEmpresa().getPersona().getNumeroIdentificacion();
			}
			ConsultarTiposAfiliacionAfiliado consultarAfiliacionesSrv = new ConsultarTiposAfiliacionAfiliado(per.getNumeroIdentificacion(), per.getTipoIdentificacion());
			consultarAfiliacionesSrv.execute();
			List<TipoAfiliadoEnum> listAfiliaciones = consultarAfiliacionesSrv.getResult();
			
			afiliaciones = (consultarHistoricoAfiliaciones(em, listAfiliaciones, per));
			
			
		}
		return afiliaciones;
	}
	@SuppressWarnings("unchecked")
	private List<Object[]>consultaHistoricoEmpleador(EntityManager em, CertificadoDTO certificadoDTO){
		Empleador emp = null;		
		ConsultarEmpleador buscarEmpleador = new ConsultarEmpleador(certificadoDTO.getIdEmpleador());
		buscarEmpleador.execute();
		emp = buscarEmpleador.getResult();
		List<Object[]> afiliaciones = null;
		if(emp!=null){
			// consultar historico empleador o persona (procedimiento)
			afiliaciones = (List<Object[]>) em
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_AFILIACION_EMPLEADOR)
					.setParameter("idPersona", emp.getEmpresa().getPersona().getIdPersona().toString())
					.getResultList();
		}
		return afiliaciones;
	}
		
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getCertificado(EntityManager ...em){
		try {
			logger.debug("Inicia el método getReporte(EntityManager) para ConsultaTablaComunicado145");
			CertificadoDTO certificadoDTO = em[0]
					.createNamedQuery(NamedQueriesConstants.CONSULTA_CERTIFICADO_POR_ID, CertificadoDTO.class)
					.setParameter(ConstantesComunicado.KEY_MAP_ID_SOLICITUD,
							params.get(ConstantesComunicado.KEY_MAP_ID_SOLICITUD))
					.getSingleResult();
			StringBuilder htmlContent = new StringBuilder();
			
			switch (certificadoDTO.getTipoCertificado()) {
			case CERTIFICADO_HISTORICO_AFILIACIONES:
				return consultaHistoricos(em[0], certificadoDTO, htmlContent);
			case CERTIFICADO_APORTES_POR_ANIO:
				return consultaAportesPorAnio(certificadoDTO, htmlContent);
			default:
				break;
			}
		
			return htmlContent.toString();
		} catch (Exception e) {
			logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
			throw new TechnicalException(e.getMessage());
		}
    }
	private String consultaHistoricos(EntityManager em, CertificadoDTO certificadoDTO, StringBuilder htmlContent){

		if (certificadoDTO != null && certificadoDTO.getGeneradoComoEmpleador()) {	
			htmlContent = construirTablaHistoricoAfiliacionEmpleador(htmlContent,consultaHistoricoEmpleador(em, certificadoDTO),consultaEstadoEmpleador(em, certificadoDTO));
		} else {
			htmlContent = construirTablaHistoricoAfiliacionPersona(htmlContent,consultaHistoricoPersona(em,certificadoDTO));
		}
		logger.debug("Finaliza el método getReporte(EntityManager em) ");
		return htmlContent.toString();
	}
	
	private String consultaAportesPorAnio(CertificadoDTO certificadoDTO, StringBuilder htmlContent){
		PersonaModeloDTO per = new PersonaModeloDTO();
		boolean isEmpleador=false;
		List<AportePeriodoCertificadoDTO> reporte = null;
		try {
			if (certificadoDTO != null && certificadoDTO.getGeneradoComoEmpleador()) {
				htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>No.</th><th>Fecha Recaudo</th><th>Periodo</th><th>N° Planilla</th><th>Monto De Aporte</th><th>Valor Interes</th></tr>");
				Empleador emp = null;
				ConsultarEmpleador buscarEmpleador = new ConsultarEmpleador(certificadoDTO.getIdEmpleador());
				buscarEmpleador.execute();
				emp = buscarEmpleador.getResult();
				per.setNumeroIdentificacion(emp.getEmpresa().getPersona().getNumeroIdentificacion());
				per.setTipoIdentificacion(emp.getEmpresa().getPersona().getTipoIdentificacion());
				isEmpleador=true;
				ConsultarAportePeriodo consultaAporte = new ConsultarAportePeriodo(per.getNumeroIdentificacion(),
				        per.getTipoIdentificacion(), certificadoDTO.getAnio());
				consultaAporte.execute();
				reporte = consultaAporte.getResult();
			} else {
				htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>No.</th><th>Fecha Recaudo</th><th>Empleador</th><th>N° Planilla</th><th>Monto De Aporte</th><th>Tipo de Afiliación</th></tr>");
				ConsultarPersona buscarPersonas = new ConsultarPersona(certificadoDTO.getIdPersona());
				buscarPersonas.execute();
				per = buscarPersonas.getResult();
				
                reporte = consultarAportes(per.getTipoIdentificacion(), per.getNumeroIdentificacion(), per.getIdPersona(), certificadoDTO.getAnio());
			}

			int numRegistro = 1;
			for (AportePeriodoCertificadoDTO aportePeriodoCertificadoDTO : reporte) {
				htmlContent.append("<tr>");
				htmlContent.append("<td>" + numRegistro++ + "</td>");
				if (aportePeriodoCertificadoDTO != null) {
					htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getFechaRecaudo() + "</td>");
					if(isEmpleador)
						htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getPeriodoAporte() + "</td>");
					else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.name().equals(aportePeriodoCertificadoDTO.getTipoAfiliacion()))
						htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getEmp().getEmpresa().getPersona().getRazonSocial() + "</td>");
					else 
					    htmlContent.append("<td> </td>");
					htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getNumeroPlanilla() + "</td>");
					htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getValorTotalAporte().substring(0,aportePeriodoCertificadoDTO.getValorTotalAporte().indexOf("."))  + "</td>");
					htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getValorInteres().substring(0,aportePeriodoCertificadoDTO.getValorInteres().indexOf("."))  + "</td>");
                                        if(!isEmpleador)
						htmlContent.append("<td>" + aportePeriodoCertificadoDTO.getTipoAfiliacion() + "</td>");
				} else
					htmlContent.append("<td> - </td>");
				htmlContent.append("</tr>");
			}
			htmlContent.append("</table>");
		} catch (Exception e) {
			logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
			throw new TechnicalException(e.getMessage());
		}
		return htmlContent.toString();
	}
	
	private List<List<Object[]>> consultarHistoricoAfiliaciones(EntityManager em, List<TipoAfiliadoEnum> tiposAfiliacion, PersonaModeloDTO persona){
	    
	    logger.info("Inicia historicoAfiliaciones(EntityManager, List<TipoAfiliadoEnum>, PersonaModeloDTO)");    
	    List<List<Object[]>> afiliaciones = new ArrayList<>();
	    List<Object[]> listResult =  new ArrayList<>();    
	    if (tiposAfiliacion.contains(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)) {
	        ObtenerEmpleadoresRelacionadosAfiliado obtenerEmpleadoresSrv = new ObtenerEmpleadoresRelacionadosAfiliado(null, persona.getTipoIdentificacion(), null, null, persona.getNumeroIdentificacion());
	        obtenerEmpleadoresSrv.execute();
	        
	        List<EmpleadorRelacionadoAfiliadoDTO> empleadores = obtenerEmpleadoresSrv.getResult();
	        
	        for (EmpleadorRelacionadoAfiliadoDTO empleadorRelacionadoAfiliadoDTO : empleadores) {
                listResult = consultarDatosTipoAfiliacion(em, persona, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, empleadorRelacionadoAfiliadoDTO);
                if (listResult != null && !listResult.isEmpty()){
                   afiliaciones.add(ordenarHistoricoAfiliacion(listResult,consultaEstadoAfiliado(em,persona.getIdPersona(),empleadorRelacionadoAfiliadoDTO.getIdEmpleador(),TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)));
                }
            }
        }
	    if (tiposAfiliacion.contains(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)) {
	        listResult = consultarDatosTipoAfiliacion(em, persona, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, null);
	        if (listResult != null && !listResult.isEmpty()){
	        	afiliaciones.add(ordenarHistoricoAfiliacion(listResult,consultaEstadoAfiliado(em,persona.getIdPersona(),null,TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE)));
            }
	    }
	    if (tiposAfiliacion.contains(TipoAfiliadoEnum.PENSIONADO)) {
	        listResult = consultarDatosTipoAfiliacion(em, persona, TipoAfiliadoEnum.PENSIONADO, null);
	        if (listResult != null && !listResult.isEmpty()){
	        	afiliaciones.add(ordenarHistoricoAfiliacion(listResult,consultaEstadoAfiliado(em,persona.getIdPersona(),null,TipoAfiliadoEnum.PENSIONADO)));
            }
        }
	    logger.info("Finaliza historicoAfiliaciones(EntityManager, List<TipoAfiliadoEnum>, PersonaModeloDTO)");
        return afiliaciones;
	}
	
	@SuppressWarnings("unchecked")
    private List<Object[]> consultarDatosTipoAfiliacion(EntityManager em, PersonaModeloDTO per, TipoAfiliadoEnum tipoAfiliado, EmpleadorRelacionadoAfiliadoDTO empleador){
        logger.info("Inicia consultarDatosTipoAfiliacion(EntityManager, PersonaModeloDTO, TipoAfiliadoEnum, EmpleadorRelacionadoAfiliadoDTO)");
        Set<Object[]> afiliaciones = new HashSet<>();
        switch (tipoAfiliado) {
			case TRABAJADOR_DEPENDIENTE: {			
				afiliaciones = new HashSet<>((List<Object[]>) em
					.createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_HISTORICO_AFILIADO)
					.setParameter("idPersona", String.valueOf(per.getIdPersona()))
					.setParameter("idEmpleador", String.valueOf(empleador.getIdEmpleador()))
					.setParameter("tipoAfiliado", 1)
					.getResultList());
				break;
			}
			case TRABAJADOR_INDEPENDIENTE: {
				Query query = em.createNativeQuery(
					"EXEC [dbo].[SP_HistoricoAfiliacionPersona] :idPersona, :idEmpleador, :tipoAfiliado"
				);
					query.setParameter("idPersona", String.valueOf(per.getIdPersona()));
					query.setParameter("idEmpleador", null);
					query.setParameter("tipoAfiliado", 2);
				afiliaciones = new HashSet<>((List<Object[]>) query.getResultList());
				break;
			}
			case PENSIONADO: {
				Query query = em.createNativeQuery(
					"EXEC [dbo].[SP_HistoricoAfiliacionPersona] :idPersona, :idEmpleador, :tipoAfiliado"
				);
					query.setParameter("idPersona", String.valueOf(per.getIdPersona()));
					query.setParameter("idEmpleador", null);
					query.setParameter("tipoAfiliado", 3);
				afiliaciones = new HashSet<>((List<Object[]>) query.getResultList());
				break;
			}
		}
    	List<Object[]> resultado = new ArrayList<>(afiliaciones);

        /*
	    afiliaciones = (List<Object[]>) em
                .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTA_HISTORICO_AFILIACIONES_PERSONA)
                .setParameter("tipoIdentificacion", per.getTipoIdentificacion().name())
                .setParameter("numeroIdentificacion", per.getNumeroIdentificacion())
                .setParameter("tipoAfiliado", tipoAfiliado.name())
                .setParameter("tipoIdentificacionEmpleador", empleador != null ? empleador.getTipoIdentificacion().name() : "")
                .setParameter("numeroIdentificacionEmpleador", empleador != null ? empleador.getNumeroIdentificacion() : "")
                .getResultList();*/
	    
	    logger.info("Inicia consultarDatosTipoAfiliacion(EntityManager, PersonaModeloDTO, TipoAfiliadoEnum, EmpleadorRelacionadoAfiliadoDTO)");
        return resultado;
	}
    
    private List<AportePeriodoCertificadoDTO> consultarAportes(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long idPersona,
            int anio) {

        ConsultarRecaudoDTO recaudo = new ConsultarRecaudoDTO();
        List<TipoAfiliadoEnum> afiliaciones = new ArrayList<>();
        List<Long> cotizantesRslt = new ArrayList<>();
        List<AportePeriodoCertificadoDTO> aportes = new ArrayList<>();

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
            //List<CuentaAporteDTO> aportesRslt = aportesSrv.getResult();
            //aportesTotales.addAll(aportesSrv.getResult());
            aportes.addAll(consultarAportesAfiliado(aportesSrv.getResult(), tipoAfiliado));
        }
        return aportes;
    }
    
    private List<AportePeriodoCertificadoDTO> consultarAportesAfiliado(List<CuentaAporteDTO> aportesTotales, TipoAfiliadoEnum tipoAfiliado){
        
        List<AportePeriodoCertificadoDTO> aportes = new ArrayList<>();
        AportePeriodoCertificadoDTO aporte = null;
        Empleador empleador = null;
        Empresa empresa = null;
        Persona persona = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatPeriodo = new SimpleDateFormat("yyyy/MM");
        for (CuentaAporteDTO cuentaAporteDTO : aportesTotales) {
            aporte = new AportePeriodoCertificadoDTO();
            if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
                empleador = new Empleador();
                empresa = new Empresa();
                persona = new Persona();
                
                empleador.setEmpresa(empresa);
                empresa.setPersona(persona);
                empleador.getEmpresa().getPersona().setRazonSocial(cuentaAporteDTO.getNombreCompletoAportante());
                aporte.setEmp(empleador);
            }
            aporte.setFechaRecaudo(format.format(new Date(cuentaAporteDTO.getFechaRegistro())));
            aporte.setNumeroPlanilla(cuentaAporteDTO.getNumeroPlanilla() != null ? cuentaAporteDTO.getNumeroPlanilla():"");
            aporte.setPeriodoAporte(formatPeriodo.format(new Date(cuentaAporteDTO.getPeriodoPago())));
            aporte.setTipoAfiliacion(tipoAfiliado.name());
            aporte.setValorTotalAporte(cuentaAporteDTO.getTotalAporte().toString());
            aporte.setValorInteres(cuentaAporteDTO.getInteresesAporte().toString());
            logger.info("retorna aporterrfffvvv***" + aporte);
            aportes.add(aporte);
        }
        return aportes;
    }
    
    /**
     * Método encargado de construir la tabla de historicos de afiliación para un trabajador dependiente, independiente y pensionado
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param htmlContent tabla html en la cual se van a agregar los registros del historico de afiliaciones de un trabajador dependiente, independiente o pensionado
     * @param historicoAfiliacion historico de afiliación de un trabajador dependiente, independiente o pensionado
     * @return htmlContent tabla html con el historico de afiliaciones
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private StringBuilder construirTablaHistoricoAfiliacionPersona(StringBuilder htmlContent, List<List<Object[]>>historicosAfiliacion) {
    	
    	Date fechaCambioEstado;
		String estadoAfiliacion;
		String tipoAfiliacion;
		int numFila = 1;
		Object[] afiliacion;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		htmlContent.append(
				"<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>No.</th><th>Fecha Ingreso</th><th>Fecha Retiro</th><th>Tipo de afiliación</th></tr>");
    	if(!historicosAfiliacion.isEmpty()) {
    		for(List<Object[]> historicoAfiliacion: historicosAfiliacion) {
    			int numRegistro=1;
    			int nuevaFila=0;
    			for(int i=0; i<historicoAfiliacion.size();i++) {
    				afiliacion = historicoAfiliacion.get(i);
    				estadoAfiliacion = (String) afiliacion[0];
    				fechaCambioEstado = afiliacion[1] !=null ? (Date) afiliacion[1] : null;
    				tipoAfiliacion = (String) afiliacion[2];
    				if(numRegistro%2!=0 && estadoAfiliacion.equals(ESTADO_ACTIVO)) {
    					if(fechaCambioEstado!=null) {
    						htmlContent.append("<tr>");
    						htmlContent.append("<td align=\"center\">" + numFila++ + "</td>");
    						htmlContent.append("<td>" + format.format(fechaCambioEstado) + "</td>");
    					}
    					if(i+1>=historicoAfiliacion.size()){
    						htmlContent.append("<td> - </td>");
       						htmlContent.append("<td>" + TipoAfiliadoEnum.valueOf(tipoAfiliacion).getDescripcion() + "</td>");
    						htmlContent.append("</tr>");
    					}
    				}else{
    					if(fechaCambioEstado!=null) {
    						htmlContent.append("<td>" + format.format(fechaCambioEstado) + "</td>");
       						htmlContent.append("<td>" + TipoAfiliadoEnum.valueOf(tipoAfiliacion).getDescripcion() + "</td>");
    					}else{
    						htmlContent.append("<td> - </td>");
       						htmlContent.append("<td>" + TipoAfiliadoEnum.valueOf(tipoAfiliacion).getDescripcion() + "</td>");
    					}
    				}
    				if(nuevaFila==numRegistro) {
    					htmlContent.append("</tr>");
    					numRegistro=numRegistro+2;
    				}
    				nuevaFila++;
    			}
    		}
			htmlContent.append("</table>");
    	}else{
    		htmlContent.append("</table>");
		}
    	return htmlContent;
    }
    
    /**
     * Método encargado de construir la tabla de historicos de afiliación para un empleador
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param htmlContent tabla html en la cual se van a agregar los registros del historico de afiliaciones de un empleador
     * @param historicoAfiliacion historico de afiliación de un empleador
     * @param estadoEmpleador Estado actual del empleador
     * @return htmlContent tabla html con el historico de afiliaciones
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private StringBuilder construirTablaHistoricoAfiliacionEmpleador(StringBuilder htmlContent, List<Object[]>historicoAfiliacion, String estadoEmpleador) {
    	
    	Date fechaCambioEstado;
		String estadoAfiliacion;
		int numRegistro=1;
		int numFila = 1;
		int nuevaFila=0;
		Object[] afiliacion;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		htmlContent.append(
				"<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style><table><tr><th>No.</th><th>Fecha Ingreso</th><th>Fecha Retiro</th></tr>");
    	if(!historicoAfiliacion.isEmpty()) {
    		historicoAfiliacion = ordenarHistoricoAfiliacion(historicoAfiliacion,estadoEmpleador);
			for(int i=0; i<historicoAfiliacion.size();i++) {
				afiliacion = historicoAfiliacion.get(i);
				estadoAfiliacion = (String) afiliacion[0];
				fechaCambioEstado = afiliacion[1] !=null ? (Date) afiliacion[1] : null;
	            if(numRegistro%2!=0 && estadoAfiliacion.equals(ESTADO_ACTIVO)) {
	            	if(fechaCambioEstado!=null) {
	    				htmlContent.append("<tr>");
	    	            htmlContent.append("<td align=\"center\">" + numFila++ + "</td>");
	                    htmlContent.append("<td>" + format.format(fechaCambioEstado) + "</td>");
	            	}
	            	if(i+1>=historicoAfiliacion.size()){
	            		htmlContent.append("<td> - </td>");
	            		htmlContent.append("</tr>");
	            	}
	            }else{
	            	if(fechaCambioEstado!=null) {
	                    htmlContent.append("<td>" + format.format(fechaCambioEstado) + "</td>");
	            	}else {
	            		htmlContent.append("<td> - </td>");
	            	}
	            }
	            if(nuevaFila==numRegistro) {
	            	htmlContent.append("</tr>");
	            	numRegistro=numRegistro+2;
	            }
	            nuevaFila++;
			}
			htmlContent.append("</table>");
			}else {
			htmlContent.append("</table>");
			}
    	return htmlContent;
    }
    
    /**
     * Método encargado de ordenar el historico de afiliación de un afiliado
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param historicoAfiliacion historico de afiliación que va a ser ordenado
     * @param estadoAfiliado estado de afiliación del afiliado
     * @return historicoAfiliacion historicoAfiliacion ordenado
     */
    private List<Object[]> ordenarHistoricoAfiliacion(List<Object[]>historicoAfiliacion, String estadoAfiliado){
		String estadoAfiliacion;
		String estadoAfiliacionTem = ESTADO_ACTIVO;
    	Object[] afiliacion;
    	//Si solo se tienen dos registros en el historico se verifica el orden de los registros, si esta inactivo y los registros se encuentran inactivo y activo se invierten
		if(historicoAfiliacion.size()==2) {
			if(((String)historicoAfiliacion.get(0)[0]).equals(ESTADO_INACTIVO) && ((String)historicoAfiliacion.get(1)[0]).equals(ESTADO_ACTIVO) && estadoAfiliado.equals(ESTADO_INACTIVO) ) {
				afiliacion = historicoAfiliacion.get(0);
				historicoAfiliacion.remove(afiliacion);
				historicoAfiliacion.add(afiliacion);
			}
		}
		for(int j=0; j<historicoAfiliacion.size();j++) {
			afiliacion = historicoAfiliacion.get(j);
			estadoAfiliacion = (String) afiliacion[0];
			if(!estadoAfiliacion.equals(estadoAfiliacionTem)) {
				historicoAfiliacion.remove(afiliacion);
				j--;
			}else {
				if(estadoAfiliacionTem.equals(ESTADO_ACTIVO)) {
					// Si el siguiente registro es ACTIVO se elimina el registro actual y se continua con la revisión con el siguiente
					if(j+1<historicoAfiliacion.size()) {
						if(((String)historicoAfiliacion.get(j+1)[0]).equals(ESTADO_ACTIVO)) {
							historicoAfiliacion.remove(afiliacion);
							j--;
						}else {
							estadoAfiliacionTem = ESTADO_INACTIVO;
						}
					}
				}else {
					estadoAfiliacionTem = ESTADO_ACTIVO;
				}
			}
		}
		return historicoAfiliacion;
    }
    
	/**
	 * Método encargado de consultar el estado de un empleador
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @param em
	 * @param certificadoDTO Certificado del cual se va a obtener el empleador
	 * @return estado estado actual de un empleador
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private String consultaEstadoEmpleador(EntityManager em, CertificadoDTO certificadoDTO){
		try {
			logger.debug("Inicia el método consultaEstadoEmpleador(EntityManager em, CertificadoDTO certificadoDTO) para ConsultaTablaComunicado145");
			Empleador emp = null;		
			ConsultarEmpleador buscarEmpleador = new ConsultarEmpleador(certificadoDTO.getIdEmpleador());
			buscarEmpleador.execute();
			emp = buscarEmpleador.getResult();
			String estadoEmpleador=null;
			if(emp!=null){
				estadoEmpleador = (String) em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_EMPLEADOR)
						.setParameter("idPersona", emp.getEmpresa().getPersona().getIdPersona().toString()).getResultList().get(0);
			}
			return estadoEmpleador;
			} catch (Exception e) {
				logger.debug("Finaliza el método consultaEstadoEmpleador(EntityManager em, CertificadoDTO certificadoDTO)) : Error inesperado");
				throw new TechnicalException(e.getMessage());
			}
	}
	
	/**
	 * Método encargado de consultar el estado de un afiliado (Trabajador dependiente, Trabajador Independiente, Pensionado)
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @param em
	 * @param idPersona identificador de la persona
	 * @param idEmpleador identificador del empleador
	 * @param tipoAfiliado tipo de afiliación de la persona
	 * @return estadoAfiliado estado actual del afiliado
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private String consultaEstadoAfiliado(EntityManager em, Long idPersona, Long idEmpleador, TipoAfiliadoEnum tipoAfiliado){
		try {
			logger.debug("Inicia el método consultaEstadoAfiliado(EntityManager em, Long idPersona, Long idEmpleador, String tipoAfiliado) para ConsultaTablaComunicado145");
			String estadoAfiliado=null;
			if(idEmpleador!=null) {
			estadoAfiliado = (String) em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_DEPENDIENTE)
					.setParameter("idPersona",idPersona)
					.setParameter("idEmpleador", idEmpleador)
					.setParameter("tipoAfiliado", tipoAfiliado.name())
					.getResultList().get(0);
			}else {
			estadoAfiliado = (String) em.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_INDEPENDIENTE_PENSIONADO)
					.setParameter("idPersona",idPersona)
					.setParameter("tipoAfiliado", tipoAfiliado.name())
					.getResultList().get(0);
			}
			return estadoAfiliado;
		} catch (Exception e) {
			logger.debug("Finaliza el método consultaEstadoAfiliado(EntityManager em, Long idPersona, Long idEmpleador, String tipoAfiliado) : Error inesperado");
			throw new TechnicalException(e.getMessage());
		}
	}
	
}
