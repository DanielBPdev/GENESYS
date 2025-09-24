package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolContactoEmpleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorCore;


public class ValidadorCuentaWEB implements ValidadorCore {

	private EntityManager entityManager;

	private ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
		String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

		if (numeroIdentificacion != null && !numeroIdentificacion.equals("") && tipoIdentificacion != null
				&& !tipoIdentificacion.equals("")) {
			TipoIdentificacionEnum identificacionEnum = null;
			if (!tipoIdentificacion.equals("")) {
				identificacionEnum = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
			}
			try {
				Query q = entityManager.createNamedQuery(NamedQueriesConstants.NAMED_QUERY_CUENTA_ACTIVA)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, identificacionEnum)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion);
				List<RolContactoEmpleador> ic = q.getResultList();
				if (ic != null && !ic.isEmpty()) {
					Boolean correoEnviado=ic.get(0).getCorreoEnviado();
					if (correoEnviado){
						String detalle = myResources
								.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_ACTIVO_CUENTA_WEB_ACTIVA);
						return crearValidacion(detalle, ResultadoValidacionEnum.APROBADA,
								ValidacionCoreEnum.VALIDACION_CUENTA_WEB);
					}
				} else {
					String detalle = myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_CUENTA_WEB_ACTIVA);
					return crearValidacion(detalle, ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_CUENTA_WEB);
				}
			} catch (NoResultException e) {
				String detalle = myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_CUENTA_WEB_ACTIVA);
				return crearValidacion(detalle, ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_CUENTA_WEB);
			}
		}

		return crearValidacion(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS, ResultadoValidacionEnum.NO_APROBADA,
				ValidacionCoreEnum.VALIDACION_CUENTA_WEB);
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado,
			ValidacionCoreEnum validacion) {
		ValidacionDTO resultadoValidacion = new ValidacionDTO();
		resultadoValidacion.setDetalle(detalle);
		resultadoValidacion.setResultado(resultado);
		resultadoValidacion.setValidacion(validacion);
		return resultadoValidacion;
	}

}
