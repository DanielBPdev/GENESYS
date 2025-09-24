package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asopagos.cartera.clients.ConsultarEmpleadorCartera;
import com.asopagos.cartera.clients.ConsultarRolAfiliadoCartera;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.clients.CalcularFechaVencimiento;
import com.asopagos.pila.clients.CalcularInteresesDeMora;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class ReporteTotalLiquidacion extends ConsultaReporteComunicadosAbs {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(ReporteTotalLiquidacion.class);

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
	@SuppressWarnings("unchecked")
	@Override
	public String getReporte(EntityManager em) {
		try {
			logger.debug("Inicia el método getReporte(EntityManager em) ");
			Query queryCartera = em.createNamedQuery(NamedQueriesConstants.CONSULTA_PERSONA_CARTERA);
			queryCartera.setParameter(ConstantesComunicado.KEY_MAP_ID_CARTERA, params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA));
			
			DecimalFormat format = new DecimalFormat("###.##");

			Object[] cartera = (Object[]) queryCartera.getSingleResult();

			Query queryLiquidacion = em.createNamedQuery(NamedQueriesConstants.CONSULTA_DEUDA_CARTERA_IDENTIFICADOR);
			queryLiquidacion.setParameter(ConstantesComunicado.KEY_MAP_ID_CARTERA, params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA));
			List<Object[]> result = queryLiquidacion.getResultList();

			StringBuilder htmlContent = new StringBuilder();
			BigDecimal valorAporte = BigDecimal.ZERO;

			for (Object[] objects : result) {
				for (int i = 0; i < objects.length; i++) {
					if (i == 1 && objects[i] != null) {
						valorAporte = valorAporte.add(new BigDecimal(objects[i].toString()));
					}
				}
				BigDecimal valorInteresMora = BigDecimal.ZERO;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				Date fechaPeriodo = dateFormat.parse(objects[0].toString());
				String periodo = dateFormat.format(fechaPeriodo);
				Long fechaVencimiento = obtenerFechaVencimiento(Long.parseLong(params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA).toString()), cartera[1].toString(), periodo);
				BigDecimal valorAporteCalculo = BigDecimal.ZERO;
				if (objects[1] != null) {
					valorAporteCalculo = new BigDecimal(objects[1].toString());
				}
				if (fechaVencimiento!=null){
					valorInteresMora = calcularInteresesDeMora(periodo, fechaVencimiento, valorAporteCalculo);	
				}
				valorAporte = valorAporte.add(valorInteresMora);
			}
			htmlContent.append(format.format(valorAporte));
			logger.debug("Finaliza el método getReporte(EntityManager em) ");
			return htmlContent.toString();
		} catch (Exception e) {
			logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado identificador cartera: "
					+ params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA) + "; Mensaje del error: " + e.getMessage());
			logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado identificador cartera: "
					+ params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA) + "; Mensaje del error: " + e.getMessage());
			throw new TechnicalException(e.getMessage());
		}
	}

	private BigDecimal calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte) {
		logger.debug("Inicia el método calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte)");
		CalcularInteresesDeMora valorMora = new CalcularInteresesDeMora(valorAporte, periodo, fechaVencimiento);
		valorMora.execute();
		logger.debug("Finaliza el método calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte)");
		return valorMora.getResult();
	}

	/**
	 * Método que obtiene la fecha de vencimiento para un aportante
	 * 
	 * @param idCartera
	 *            Identificador de cartera
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @param periodo
	 *            Periodo de evaluación
	 * @return La fecha de vencimiento
	 */
	private Long obtenerFechaVencimiento(Long idCartera, String numeroIdentificacion, String periodo) {
		logger.debug("Inicia el método obtenerFechaVencimiento");
		Long fechaVencimiento = null;
		EmpleadorModeloDTO empleadorDTO = consultarEmpleadorCartera(idCartera);

		if (empleadorDTO == null) { // Personas
			RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliadoCartera(Long.parseLong(params.get(ConstantesComunicado.KEY_MAP_ID_CARTERA).toString()));
			PeriodoPagoPlanillaEnum oportunidad = PeriodoPagoPlanillaEnum.MES_VENCIDO;
			TipoArchivoPilaEnum tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_I;

			if (rolAfiliadoDTO.getOportunidadPago() != null) {
				oportunidad = rolAfiliadoDTO.getOportunidadPago();
			}

			if (rolAfiliadoDTO.getPagadorAportes() == null) {
				tipoArchivo = TipoArchivoPilaEnum.ARCHIVO_OI_IP;
			}

			fechaVencimiento = calcularFechaVencimiento(periodo, oportunidad, numeroIdentificacion, tipoArchivo, 1, ClaseAportanteEnum.CLASE_I, NaturalezaJuridicaEnum.PRIVADA);
		} else { // Empleadores
			ClaseAportanteEnum claseAportante = ClaseAportanteEnum.CLASE_A;

			if (empleadorDTO.getNumeroTotalTrabajadores() > 200) {
				claseAportante = ClaseAportanteEnum.CLASE_B;
			}

			fechaVencimiento = calcularFechaVencimiento(periodo, PeriodoPagoPlanillaEnum.MES_VENCIDO, numeroIdentificacion, TipoArchivoPilaEnum.ARCHIVO_OI_I, empleadorDTO.getNumeroTotalTrabajadores(), claseAportante, empleadorDTO.getNaturalezaJuridica());
		}

		logger.debug("Finaliza el método obtenerFechaVencimiento");
		return fechaVencimiento;
	}

	/**
	 * Método que invoca el servicio de cálculo de fecha de vencimiento de un
	 * aportante para un periodo determinado
	 * 
	 * @param periodo
	 *            Periodo para el cual se consulta la fecha de vencimiento
	 * @param oportunidad
	 *            Indicador de oportunidad en para el pago (Mes vencido o mes
	 *            actual)
	 * @param numeroDocumentoAportante
	 *            Número de identificación del aportante
	 * @param tipoArchivo
	 *            Tipo de archivo evaluado (I para dependientes independientes -
	 *            IP para pensionados)
	 * @param cantidadPersonas
	 *            Cantidad de trabajadores / independientes / pensionados
	 *            relacionados
	 * @param claseAportante
	 *            Clase de aportante de acuerdo a PILA para el aportante
	 * @param naturalezaJuridica
	 *            Naturaleza jurídica del aportante
	 * @return Tiempo en milisegundos correspondientes a la fecha de vencimiento
	 *         del aporte
	 */
	private Long calcularFechaVencimiento(String periodo, PeriodoPagoPlanillaEnum oportunidad, String numeroDocumentoAportante, TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, ClaseAportanteEnum claseAportante, NaturalezaJuridicaEnum naturalezaJuridica) {
		logger.debug("Inicia el método calcularFechaVencimiento");
		CalcularFechaVencimiento service = new CalcularFechaVencimiento(naturalezaJuridica, cantidadPersonas, tipoArchivo, oportunidad, claseAportante, periodo, numeroDocumentoAportante);
		service.execute();
		logger.debug("Finaliza el método calcularFechaVencimiento");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio que consulta la información de un empleador
	 * de acuerdo a un identificador de cartera
	 * 
	 * @param idCartera
	 *            Identificador único de cartera
	 * @return La información del empleador
	 */
	private EmpleadorModeloDTO consultarEmpleadorCartera(Long idCartera) {
		logger.info("Inicia el método consultarEmpleadorCartera");
		ConsultarEmpleadorCartera service = new ConsultarEmpleadorCartera(idCartera);
		service.execute();
		logger.info("Finaliza el método consultarEmpleadorCartera");
		return service.getResult();
	}

	/**
	 * Método que invoca el servicio que consulta la información de un rol por
	 * persona, de acuerdo a un identificador de cartera
	 * 
	 * @param idCartera
	 *            Identificador de cartera
	 * @return La información del rol afiliado
	 */
	private RolAfiliadoModeloDTO consultarRolAfiliadoCartera(Long idCartera) {
		logger.info("Inicia el método consultarRolAfiliadoCartera");
		ConsultarRolAfiliadoCartera service = new ConsultarRolAfiliadoCartera(idCartera);
		service.execute();
		logger.info("Finaliza el método consultarRolAfiliadoCartera");
		return service.getResult();
	}
}
