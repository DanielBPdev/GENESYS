package com.asopagos.comunicados.ejb;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.comunicados.constants.ConstantesComunicado;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase que representa el proceso de generacion del cuerpo
 * para el comunicado: <br/>
 * 64_Notificacion de dispersion de pagos al trabajador <b>Módulo:</b> Asopagos
 * - HU-311-438 <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */

public class ConsultaReporteComunicado64 extends ConsultaReporteComunicadosAbs {

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

		Query query = em.createNamedQuery(
				NamedQueriesConstants.CONSULTAR_BENEFICIARIOS_NOTIFICACION_DISPERSION_PAGOS_TRABAJADOR);
		query.setParameter(ConstantesComunicado.KEY_MAP_NUMERO_RADICACION,
				params.get(ConstantesComunicado.KEY_MAP_NUMERO_RADICACION));
		query.setParameter(ConstantesComunicado.KEY_MAP_ID_PERSONA,
				params.get(ConstantesComunicado.KEY_MAP_ID_PERSONA));

		List<Object[]> result = query.getResultList();

		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<style>table{border-collapse:collapse;width:100%;}table,th,td{border:1px solid black;}</style>");
		htmlContent.append("<table><tr>");
		htmlContent.append("<th>Tipo identificación beneficiario</th>");
		htmlContent.append("<th># Identificación beneficiario</th>");
		htmlContent.append("<th>Nombre completo beneficiario</th>");
		htmlContent.append("<th>Tipo cuota</th>");
		htmlContent.append("<th>Valor cuota</th>");
		htmlContent.append("<th>Descuentos</th>");
		htmlContent.append("<th>Total a pagar</th>");
		htmlContent.append("<th>Tipo identificación Admin subsidio</th>");
		htmlContent.append("<th># Identificación admin subsidio</th>");
		htmlContent.append("<th>Administrador subsidio</th>");
		htmlContent.append("</tr>");

		for (Object[] objects : result) {
			htmlContent.append("<tr>");
			for (int i = 0; i < objects.length; i++) {
				htmlContent.append("<td>");
				try {
					switch (i) {
					case 0:
					case 7:
						TipoIdentificacionEnum ti;
						ti = TipoIdentificacionEnum.valueOf(objects[i].toString());
						htmlContent.append(ti.getDescripcion());
						break;
					case 3:
						TipoCuotaSubsidioEnum tc;
						tc = TipoCuotaSubsidioEnum.valueOf(objects[i].toString());
						htmlContent.append(tc.getDescripcion());
						break;
					default:
						htmlContent.append(objects[i].toString());
					}
				} catch (Exception e) {
					htmlContent.append("&nbsp;");
				}
				htmlContent.append("</td>");
			}
			htmlContent.append("</tr>");
		}
		
		htmlContent.append("</table>");
		return htmlContent.toString();
	}
}
