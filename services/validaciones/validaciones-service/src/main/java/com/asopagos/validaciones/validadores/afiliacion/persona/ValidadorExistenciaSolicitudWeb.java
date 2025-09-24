package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
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
public class ValidadorExistenciaSolicitudWeb extends ValidadorAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
 		if (datosValidacion != null && !datosValidacion.isEmpty()) {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.obtnerTiposIdentificacionEnum(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM));

			if (tipoIdentificacion != null && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()) {

				List<SolicitudAfiliacionPersona> solicitudes = consultarSolicitudesAfilPersona(tipoIdentificacion,
						numeroIdentificacion, tipoAfiliado);

				if (solicitudes != null && !solicitudes.isEmpty()) {
					// TODO Pendiente definir como saber si se envio correo
					// electronico
					Boolean envioCorreo = true;
					if (envioCorreo) {
						// Si el solicitante tiene otra solicitud de afiliación
						// en proceso
						// y se ha enviado un correo para continuar el trámite
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EXIS_SOLIC_WEB_EN_PROCESO),
								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_PERSONA,
								null);
					} else {
						// Si el solicitante tiene otra solicitud de afiliación
						// en proceso
						return crearValidacion(
								myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EXIS_SOLIC_WEB_EN_PROCESO),
								ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA,
								null);
					}
				} else {
					return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_AFIL_NO_ACTIVO),
							ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_PERSONA, null);
				}
			}
		}
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_SOLICITUD_WEB_PERSONA,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}

	private List<SolicitudAfiliacionPersona> consultarSolicitudesAfilPersona(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {
		List<EstadoSolicitudAfiliacionPersonaEnum> estados = new ArrayList<>();
		estados.add(EstadoSolicitudAfiliacionPersonaEnum.PRE_RADICADA);
		return entityManager
				.createNamedQuery(ConstantesValidaciones.NAMED_QUERY_SOLICITUD_PERSONA_WEB,
						SolicitudAfiliacionPersona.class)
				.setParameter("tipoIdentificacion", tipoIdentificacion)
				.setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoAfiliado", tipoAfiliado)
				.setParameter("estadosSolicitud", estados).getResultList();
	}
}
