package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 35	Validar que no hay una sucursal  asociada al empleador con el mismo código y/o nombre
 * Valida:
 * Validar que no hay una sucursal  asociada al empleador con el mismo código y/o nombre
 *  
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorCodigoPila extends ValidadorAbstract {
	/*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorSucursalNoAsociadaEmpleador.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

			String codigoSucursal = datosValidacion.get(ConstantesValidaciones.CODIGO_SUCURSAL_PARAM);
			String nombreSucursal = datosValidacion.get(ConstantesValidaciones.NOMBRE_SUCURSAL_PARAM);
			Long idSucursal = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_SUCURSAL_PARAM));

			try {
				SucursalEmpresa sucursalesEmpresa = (SucursalEmpresa) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_SUCURSAL_ASOCIADA_EMPLEADOR)
						.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
						.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
						.setParameter(ConstantesValidaciones.CODIGO_SUCURSAL_PARAM, codigoSucursal)
						.setParameter(ConstantesValidaciones.NOMBRE_SUCURSAL_PARAM, nombreSucursal).getSingleResult();
				/*si la sucursal existe pero tiene el mismo id de la sucrusal a
				 * modificar se lanza noresult para que quede exitosa*/
				if(sucursalesEmpresa.getIdSucursalEmpresa().equals(idSucursal)){
					throw new NoResultException();
				}
			} catch (NoResultException nre) {
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorSucursalNoAsociadaEmpleador.execute");
				// Validación exitosa, Validador 35 Validar que no hay una sucursal...
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_SUCURSAL_NO_ASOCIADA_EMPLEADOR);
			}

			logger.debug("VALIDACION FALLIDA- Fin de método ValidadorSucursalNoAsociadaEmpleador.execute");
			// Validación no aprobada, Validador 35 Validar que no hay una sucursal asociada al empleador con el mismo código y/o nombre
			return crearValidacion(
					myResources.getString(
							ConstantesValidaciones.ESTADO_AFILIACION_DIFERENTE_ACTIVO_INACTIVO_NO_FORMALIZADO),
					ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_SUCURSAL_NO_ASOCIADA_EMPLEADOR,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_SUCURSAL_NO_ASOCIADA_EMPLEADOR,
					TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
