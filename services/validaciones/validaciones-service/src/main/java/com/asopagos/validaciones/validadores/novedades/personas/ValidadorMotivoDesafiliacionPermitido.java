package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.ExpulsionSubsanada;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
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
 *
 * Validar que se ha registrado una novedad desafiliación con motivo de
 * desafiliación: <br>
 * - Expulsión por mora (independientes y pensionados)<br>
 * - Expulsión por uso indebido de servicios <br>
 * - Expulsión por suministro de información incorrecta o no entrega de
 * información
 * 
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorMotivoDesafiliacionPermitido extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
		try {
			String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);
			String idRolAfiliado = datosValidacion.get(ConstantesValidaciones.KEY_ID_ROL_AFILIADO);
			ClasificacionEnum tipoAfiliado = ClasificacionEnum.valueOf(objetoValidacion);

			// Se valida que el motivo de desafiliacion sea alguno de estos:
			// - Expulsión por mora NO APLICA PARA DEPENDIENTES
			// - Expulsión por uso indebido de servicios
			// - Expulsión por suministro de información incorrecta o no entrega
			// de información
			List<MotivoDesafiliacionAfiliadoEnum> motivosValidos = new ArrayList<>();
			motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.MAL_USO_DE_SERVICIOS_CCF);
			motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF);

			if (!tipoAfiliado.equals(ClasificacionEnum.TRABAJADOR_DEPENDIENTE)) {
				motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.RETIRO_POR_MORA_APORTES);
			}

			// Se consulta el rol afiliado del afiliado principal
			RolAfiliado rolAfiliado = (RolAfiliado) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTA_ROL_AFILIADO_POR_ID)
					.setParameter(ConstantesValidaciones.KEY_ID_ROL_AFILIADO, idRolAfiliado).getSingleResult();

			if (rolAfiliado != null && motivosValidos.contains(rolAfiliado.getMotivoDesafiliacion())) {
					logger.debug(
							"VALIDACION EXITOSA- Fin de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
					// Validación exitosa
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MOTIVO_DESAFILIACION_PERMITIDO);
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
				// Validación no aprobada
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE_REGISTRO_SUBSANACION),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_MOTIVO_DESAFILIACION_PERMITIDO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_MOTIVO_DESAFILIACION_PERMITIDO,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
