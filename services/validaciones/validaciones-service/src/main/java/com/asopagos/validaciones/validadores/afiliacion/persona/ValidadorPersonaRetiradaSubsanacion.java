package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Validador 47.Persona ha sido retirada y tiene alguno de los siguientes motivos de desafiliación: <br>
 * - Expulsión por mora(independientes y pensionados) <br>
 * - Expulsión por uso indebido de servicios <br>
 * - Expulsión por suministro de información incorrecta o no entrega de
 * información
 * 
 * 
 * También tiene un registro de una novedad del tipo "Registrar subsanación de expulsión" (relacionada con la afiliación por la cual resultó expulsado/retirado). 
 * Si la persona tiene la subsanación registrada, se aprueba la validación, si no hay este registro sería no aprobada.
 * 
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public class ValidadorPersonaRetiradaSubsanacion extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaRetiradaSubsanacion.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			if (datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM) == null) {
				throw new Exception();
			}
			TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_AFILIADO_PARAM));

			List<MotivoDesafiliacionAfiliadoEnum> motivosDesafiliacion = new ArrayList<>();
			motivosDesafiliacion.add(MotivoDesafiliacionAfiliadoEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF);
			motivosDesafiliacion.add(MotivoDesafiliacionAfiliadoEnum.MAL_USO_DE_SERVICIOS_CCF);
			if (!TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
				motivosDesafiliacion.add(MotivoDesafiliacionAfiliadoEnum.RETIRO_POR_MORA_APORTES);
			}
			
			// Se consulta el afiliado Inactivo por los motivos de Desafiliación
			List<RolAfiliado> rolAfiliados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_ESTADO_FECHA_RETIRO_MOTIVODESA)
				.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
				.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
				.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.INACTIVO)
				.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, tipoAfiliado)
				.setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, motivosDesafiliacion).getResultList();
			List<BigInteger> idSolicitud = null;
			if (rolAfiliados != null && !rolAfiliados.isEmpty()) {
				List<String> tipoTransaccion = new ArrayList<>();
				if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)) {
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_DEPENDIENTE.name());
				}
				else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(tipoAfiliado)) {
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_INDEPENDIENTE.name());
				}
				else {
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_25ANIOS.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_0_6.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_2.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0_6.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_2.name());
					tipoTransaccion.add(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_PENSION_FAMILIAR.name());
				}
				/*Si existe una solicitud de novedad de Subsanación de expulsión aprobada para el afiliado retirado pasa la validación.*/
				idSolicitud = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_NOVEDAD_ESTADO_TIPOS)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.TIPO_TRANSACCION, tipoTransaccion)
					.setParameter(ConstantesValidaciones.RESULTADO_PROCESO_PARAM, ResultadoProcesoEnum.APROBADA.name()).getResultList();
			} else {
				/*Si no encuentra roles afiliado inactivos, asociados a algun motivo de desafiliación pasa la validación.*/
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorPersonaRetiradaSubsanacion.execute");
				// Validación exitosa
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION);
			}
			
			if (idSolicitud != null && !idSolicitud.isEmpty()) {
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorPersonaRetiradaSubsanacion.execute");
				// Validación exitosa
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION);
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorPersonaRetiradaSubsanacion.execute");
				// Validación no aprobada
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE_REGISTRO_SUBSANACION),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
