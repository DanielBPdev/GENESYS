package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA: <br>
 * Si el afiliado pensionado tiene asociada una entidad pagadora activa
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorEntidadPagadoraPensionadoActiva extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEntidadPagadoraPensionadoActiva.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

			// se consulta el rol afiliado con el tipo y número de documento, el
			// tipo de afiliado y su estado
			List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.PENSIONADO)
					.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
					.getResultList();

			if (rolAfiliado != null && !rolAfiliado.isEmpty()) {
				RolAfiliado afiliado = rolAfiliado.iterator().next();
				if (afiliado.getPagadorPension() != null
						&& afiliado.getPagadorAportes().getIdEntidadPagadora() != null) {
					// Si esta asociado la entidad pagadora aportes al afiliado
					// la validacion es fallida
					logger.debug("NO HABILITADA- Fin de método ValidadorEntidadPagadoraPensionadoActiva.execute");
					return crearValidacion(
							myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_ENTIDAD_PAGADORA_ASOCIADA),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_ENTIDAD_PAGADORA_PENSIONADO_ACTIVA,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			}
			// Si el afiliado NO tiene asociada una entidad pagadora la
			// validacion es exitosa
			logger.debug("HABILITADA - Fin de método ValidadorEntidadPagadoraPensionadoActiva.execute");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ENTIDAD_PAGADORA_PENSIONADO_ACTIVA);

		} catch (NoResultException nre) {
			logger.error("No evaluado - no se encuentra el afiliado con los datos ingresados", nre);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ENTIDAD_PAGADORA_PENSIONADO_ACTIVA,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ENTIDAD_PAGADORA_PENSIONADO_ACTIVA,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
