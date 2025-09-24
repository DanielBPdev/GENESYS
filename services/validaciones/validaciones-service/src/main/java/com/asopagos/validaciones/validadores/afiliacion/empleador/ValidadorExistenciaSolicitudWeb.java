package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorCore;

/**
 * @author jzambrano Validación que se encarga de validar si el empleador tiene
 *         alguna solicitud de afiliación a traves del canal web que aun no se
 *         haya radicado
 *
 */

 public class ValidadorExistenciaSolicitudWeb implements ValidadorCore {

	private EntityManager entityManager;
	private ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);

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
			if ((tipoIdentificacion != null) && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
				try {
					List<EstadoSolicitudAfiliacionEmpleadorEnum> estados = new ArrayList<>();
					estados.add(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);

					Query consultarSolTemporal = entityManager
							.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_SOLIC_EMPL_ESTADO_TEMPORAL)
							.setParameter("numeroIdentificacion", numeroIdentificacion)
							.setParameter("tipoIdentificacion", tipoIdentificacion)
							.setParameter("estadosSolicitud", estados);
					DatoTemporalSolicitud sate = (DatoTemporalSolicitud) consultarSolTemporal.getSingleResult();

					if (sate != null) {
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EX_SOLIC_WEB),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_EMPLEADOR);
					} else {
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EX_SOLIC),
								ResultadoValidacionEnum.APROBADA,
								ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_EMPLEADOR);
					}

				} catch (NoResultException e) {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EX_SOLIC),
							ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_EMPLEADOR);
				}
			} else {
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR);
			}
		}
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_EMPLEADOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#setEntityManager(javax.
	 * persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * 
	 * @param detalle
	 * @param resultado
	 * @param validacion
	 * @return
	 */
	private ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado,
			ValidacionCoreEnum validacion) {
		ValidacionDTO resultadoValidacion = new ValidacionDTO();
		resultadoValidacion.setDetalle(detalle);
		resultadoValidacion.setResultado(resultado);
		resultadoValidacion.setValidacion(validacion);
		return resultadoValidacion;
	}
}
