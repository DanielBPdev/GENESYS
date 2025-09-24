package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.ExpulsionSubsanada;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Validador 47. Validar que no se ha registrado una novedad de "Registrar
 * subsanación de expulsión" relacionada con la más reciente novedad de
 * desafiliación que tuvo motivo de desafiliación: <br>
 * - Expulsión por mora <br>
 * - Expulsión por uso indebido de servicios <br>
 * - Expulsión por suministro de información incorrecta o no entrega de
 * información
 * 
 * 
 * Verificar que el valor del campo "Expulsión subsanada?", tenga un valor
 * diferente a "S" para aprobar la validación
 * 
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorExisteRegistroSubsanacionEmpleador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorExisteRegistroSubsanacionEmpleador.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

			// Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
			
			// Se valida que el motivo de desafiliacion sea alguno de estos:
			// - Expulsión por mora
			// - Expulsión por uso indebido de servicios
			// - Expulsión por suministro de información incorrecta o no entrega
			// de información
			List<MotivoDesafiliacionEnum> motivosValidos = new ArrayList<>();
			motivosValidos.add(MotivoDesafiliacionEnum.EXPULSION_POR_MOROSIDAD);
			motivosValidos.add(MotivoDesafiliacionEnum.EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS);
			motivosValidos.add(MotivoDesafiliacionEnum.EXPULSION_POR_INFORMACION_INCORRECTA);

			if (motivosValidos.contains(empleador.getMotivoDesafiliacion())) {
				// Se valida si existe el registro de una subsanacion de expulsion
				// El campo "Expulsión subsanada?" debe tener un valor diferente
				// a "S" para aprobar la validación
				ExpulsionSubsanada expulsionSubsanada = null;
				try{
				    expulsionSubsanada = (ExpulsionSubsanada) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_EXPULSION_SUBSANDA_EMPLEADOR)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
				} catch (NonUniqueResultException | NoResultException e) {
		            expulsionSubsanada = null;
		        }
				if (expulsionSubsanada == null 
						|| (expulsionSubsanada.getExpulsionSubsanada() != null
						&& !expulsionSubsanada.getExpulsionSubsanada())) {
					logger.debug("VALIDACION EXITOSA- Fin de método ValidadorExisteRegistroSubsanacionEmpleador.execute");
					// Validación exitosa
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_EMPLEADOR);
				} else { 
					logger.debug("VALIDACION FALLIDA- Fin de método ValidadorExisteRegistroSubsanacionEmpleador.execute");
					// Validación no aprobada
					return crearValidacion(
							myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_EXISTE_REGISTRO_SUBSANACION),
							ResultadoValidacionEnum.NO_APROBADA,
							ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_EMPLEADOR,
							TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
				}
			} else {
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorExisteRegistroSubsanacionEmpleador.execute");
				// Validación no aprobada
				return crearValidacion(
						myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_EXISTE_REGISTRO_SUBSANACION),
						ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_EMPLEADOR,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_EMPLEADOR,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
