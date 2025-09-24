package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la logica para verificar que para el solicitante no haya
 * otra solicitud de afiliación en proceso (en cualquier canal)
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class ValidadorExistenciaSolicitud extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
 		if (datosValidacion != null && !datosValidacion.isEmpty()) {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

			TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum
					.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));

			String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);

			TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM));

            // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación en proceso para no tenerla en cuenta en la validación
            Long idSolicitud = null;
            if (datosValidacion.containsKey("idSolicitud")) {
                idSolicitud = Long.valueOf(datosValidacion.get("idSolicitud"));
            }
            logger.debug("ValidadorExistenciaSolicitud - IdSolicitud: "+ idSolicitud);

			if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {

				List<SolicitudAfiliacionPersona> solicitudes = consultarSolicitudesAfilPersona(tipoIdentificacion,
						numeroIdentificacion, tipoAfiliado, tipoIdentificacionEmpleador, numeroIdentificacionEmpleador, idSolicitud);

				if (solicitudes != null && !solicitudes.isEmpty()) {
					// TODO Pendiente definir como saber si se envio correo
					// electronico
//					Boolean envioCorreo = true;
//					if (envioCorreo) {
						// Si el solicitante tiene otra solicitud de afiliación
						// en proceso
						// y se ha enviado un correo para continuar el trámite
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EXIS_SOLIC_EN_PROCESO),
								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA,
								null);
//					} else {
//						// Si el solicitante tiene otra solicitud de afiliación
//						// en proceso
//						return crearValidacion(
//								myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EXIS_SOLIC_PERS),
//								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA,
//								null);
//					}
				} else {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_AFIL_NO_ACTIVO),
							ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, null);
				}
			}
		}
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	private RolAfiliado consultarRolAfiliado(TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion) {

		List<RolAfiliado> rolesAfiliado = entityManager
				.createNamedQuery(ConstantesValidaciones.NAMED_QUERY_ROL_AFILIADO, RolAfiliado.class)
				.setParameter("tipoAfiliado", tipoAfiliado).setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		if (rolesAfiliado != null && !rolesAfiliado.isEmpty()) {
			return rolesAfiliado.iterator().next();
		}
		return null;
	}

	private List<SolicitudAfiliacionPersona> consultarSolicitudesAfilPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado,
			TipoIdentificacionEnum tipoIdenficiacionEmpleador, String numeroIdentificacionEmpleador, Long idSolicitud) {
		List<EstadoSolicitudAfiliacionPersonaEnum> estados = new ArrayList<>();
		estados.add(EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
		estados.add(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
		estados.add(EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION);
		if (tipoIdenficiacionEmpleador != null && numeroIdentificacionEmpleador != null) {
			return entityManager
					.createNamedQuery(ConstantesValidaciones.NAMED_QUERY_SOLICITUD_PERSONA_DEPENDIENTES,
							SolicitudAfiliacionPersona.class)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion)
					.setParameter("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador)
					.setParameter("tipoIdenficiacionEmpleador", tipoIdenficiacionEmpleador)
					.setParameter("tipoAfiliado", tipoAfiliado)
					.setParameter("estadosSolicitud", estados)
					.setParameter("idSolicitud", idSolicitud)
					.getResultList();
		}
		return entityManager
				.createNamedQuery(ConstantesValidaciones.NAMED_QUERY_SOLICITUD_PERSONA,
						SolicitudAfiliacionPersona.class)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoAfiliado", tipoAfiliado)
				.setParameter("estadosSolicitud", estados).setParameter("idSolicitud", idSolicitud).getResultList();
	}
}
