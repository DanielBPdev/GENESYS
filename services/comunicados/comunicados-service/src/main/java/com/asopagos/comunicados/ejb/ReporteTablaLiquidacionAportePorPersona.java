package com.asopagos.comunicados.ejb;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
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

public class ReporteTablaLiquidacionAportePorPersona extends ConsultaReporteComunicadosAbs {

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(ReporteTablaLiquidacionAportePorPersona.class);

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
	@SuppressWarnings("unchecked")
	public String getReporte(EntityManager em) {
//		try {
			DecimalFormat format = new DecimalFormat("###.##");
			
			Query queryLiquidacion = em.createNamedQuery(NamedQueriesConstants.CONSULTA_CARTERA_PERSONA_COMUNICADO);
			queryLiquidacion.setParameter("tipoIdentificacion",  String.valueOf(params.get(ConstantesComunicado.KEY_MAP_TIPO_IDENTIFICACION)));
            queryLiquidacion.setParameter("numeroIdentificacion", String.valueOf(params.get(ConstantesComunicado.KEY_MAP_NUMERO_IDENTIFICACION)));
			List<Object[]> result = queryLiquidacion.getResultList();
			
			StringBuilder htmlContent = new StringBuilder();
			htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style>")
			.append("<table><tr>")
			.append("<th>Tipo identificación cotizante</th>")
			.append("<th>Número documento cotizante</th>")
			.append("<th>Nombre cotizante</th>")
			.append("<th>Período adeudado</th>")
			.append("<th>Deuda presunta </th>")
			.append("<th>Último Salario reportado </th>")
			.append("</tr>");

			for (Object[] objects : result) {
				BigDecimal valorInteresMora = BigDecimal.ZERO;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				String tipoIdentificacion = (String) objects[2];
				String numeroIdentificacion = (String) objects[3];
				String nombre = (String) objects[4];
				String periodo = null;
				BigDecimal valorDeudaPresunta = BigDecimal.ZERO;
				BigDecimal ultimoSalario = BigDecimal.ZERO;
				try {
					Date fechaPeriodo = dateFormat.parse(objects[0].toString());
					periodo = dateFormat.format(fechaPeriodo);
				} catch (ParseException e) {
					logger.error("Error al parsear la fecha " + objects[0], e);
				}
		
				if (objects[1] != null) {
					valorDeudaPresunta = new BigDecimal(objects[1].toString());
				}
		
				if (objects[5] != null) {
					ultimoSalario = new BigDecimal(objects[5].toString());
				}
				
				htmlContent.append("<tr>")
                   .append("<td>").append(tipoIdentificacion).append("</td>")
                   .append("<td>").append(numeroIdentificacion).append("</td>")
                   .append("<td>").append(nombre).append("</td>")
                   .append("<td>").append(periodo).append("</td>")
                   .append("<td>").append(format.format(valorDeudaPresunta)).append("</td>")
                   .append("<td>").append(format.format(ultimoSalario)).append("</td>")
                   .append("</tr>");
			}
			
			htmlContent.append("</table>");
//			logger.error("Inicia el método getReporte(EntityManager em):Ingreso Obtener table de liquidacion de aportes " + htmlContent.toString());
			logger.debug("Finaliza el método getReporte(EntityManager em) ");
			return htmlContent.toString();
//		} catch (Exception e) {
//			logger.error("Finaliza el método getReporte(EntityManager em) : Error inesperado");
//			logger.debug("Finaliza el método getReporte(EntityManager em) : Error inesperado");
//			throw new TechnicalException(e.getMessage());
//		}
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

			if (empleadorDTO.getNumeroTotalTrabajadores() != null 
			        && empleadorDTO.getNumeroTotalTrabajadores() > 200) {
				claseAportante = ClaseAportanteEnum.CLASE_B;
			}

			fechaVencimiento = calcularFechaVencimiento(periodo, PeriodoPagoPlanillaEnum.MES_VENCIDO, numeroIdentificacion, TipoArchivoPilaEnum.ARCHIVO_OI_I, empleadorDTO.getNumeroTotalTrabajadores(), claseAportante, empleadorDTO.getNaturalezaJuridica());
		}

		logger.debug("Finaliza el método obtenerFechaVencimiento");
		return fechaVencimiento;
	}

	private BigDecimal calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte) {
		logger.debug("Inicia el método calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte)");
		CalcularInteresesDeMora valorMora = new CalcularInteresesDeMora(valorAporte, periodo, fechaVencimiento);
		valorMora.execute();
		logger.debug("Finaliza el método calcularInteresesDeMora(String periodo, Long fechaVencimiento, BigDecimal valorAporte)");
		return valorMora.getResult();
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
