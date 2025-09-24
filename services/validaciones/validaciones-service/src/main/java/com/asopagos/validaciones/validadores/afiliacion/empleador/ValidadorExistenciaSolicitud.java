/**
 * 
 */
package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * @author jcamargo
 *
 */
public class ValidadorExistenciaSolicitud extends ValidadorAbstract {

	public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL = "Empleador.razonSocial.buscarTodos";
	public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO = "Empleador.tipoIdentificacion.numIdentificacion.buscarTodos";
	public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV = "Empleador.tipoIdentificacion.numIdentificacion.DV.buscarTodos";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		if (datosValidacion != null && !datosValidacion.isEmpty()) {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			String digVer = datosValidacion.get("digitoVerificacion");
			Short dv = null;
			if (digVer != null) {
				dv = Short.parseShort(datosValidacion.get("digitoVerificacion"));
			}
            // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación en proceso para no tenerla en cuenta en la validación
            Long idSolicitud = null;
            if (datosValidacion.containsKey("idSolicitud")) {
                idSolicitud = Long.valueOf(datosValidacion.get("idSolicitud"));
            }
            logger.info("ValidadorExistenciaSolicitud - IdSolicitud: "+ idSolicitud);
			if ((tipoIdentificacion != null) && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
				try {
					List<EstadoSolicitudAfiliacionEmpleadorEnum> estados = new ArrayList<>();
					estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.CERRADA);
					estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
//					estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.RECHAZADA);
					List<Empleador> result = buscarEmpleador(tipoIdentificacion, numeroIdentificacion, dv, null);
					logger.info("result -->" +result);
					if (result != null && !result.isEmpty()) {
						logger.info("ingresa al primer if");
						Empleador em = result.get(0);
						Query q = entityManager.createNamedQuery(ConstantesValidaciones.NAMED_QUERY_SOLIC_EMPL)
								.setParameter("idEmpleador", em.getIdEmpleador())
								.setParameter("estadosSolicitud", estados)
								.setParameter("idSolicitud", idSolicitud);
						SolicitudAfiliacionEmpleador sae = (SolicitudAfiliacionEmpleador) q.getSingleResult();
						logger.info("sae " +sae);
						if (sae != null) {
							logger.info("sae no ingresa");
							return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EX_SOLIC_EN_PROCESO),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						} else {
							logger.info("sae ingresa");
							Query consultarSolTemporal = entityManager
									.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL_TEMPORAL)
									.setParameter("numeroIdentificacion", numeroIdentificacion)
									.setParameter("tipoIdentificacion", tipoIdentificacion)
									.setParameter("estadosSolicitud", estados)
									.setParameter("idSolicitud", idSolicitud);
							DatoTemporalSolicitud sate = (DatoTemporalSolicitud) consultarSolTemporal.getSingleResult();
							logger.info("sate " +sate);
							if (sate != null) {
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EX_SOLIC_EN_PROCESO),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
										TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
							} else {
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EX_SOLIC),
										ResultadoValidacionEnum.APROBADA,
										ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR, null);
							}
						}
					} else {
						logger.info("ingresa segundo if");
						Query consultarSolTemporal = entityManager
								.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL_TEMPORAL)
								.setParameter("numeroIdentificacion", numeroIdentificacion)
								.setParameter("tipoIdentificacion", tipoIdentificacion)
								.setParameter("estadosSolicitud", estados)
								.setParameter("idSolicitud", idSolicitud);
						logger.info("consultarSolTemporal " + consultarSolTemporal);
						DatoTemporalSolicitud sae = (DatoTemporalSolicitud) consultarSolTemporal.getSingleResult();
						logger.info("sae " + sae);

						if (sae != null) {
							return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EX_SOLIC_EN_PROCESO),
									ResultadoValidacionEnum.NO_APROBADA,
									ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
									TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
						} else {
							return crearValidacion(
									myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EX_SOLIC),
									ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
									null);
						}
					}
				} catch (NoResultException e) {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EX_SOLIC),
							ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR, null);
				}
			} else {
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR, null);
	}

	private List<Empleador> buscarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion, String razonSocial) {

		List<Empleador> listEmpleador = new ArrayList<>();

		if (razonSocial != null) {
			// consultar empleador por razon social
			listEmpleador = (List<Empleador>) entityManager.createNamedQuery(CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL)
					.setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

		} else if (digitoVerificacion != null && tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo, numero de identificacion y digito
			// de verificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("digitoVerificacion", digitoVerificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		} else if (tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo y numero de identificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		}
		return listEmpleador;
	}

}
