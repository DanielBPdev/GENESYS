package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo :  Validar estado de afiliación jefe de hogar (caso activo)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorJefeHogarActivo extends ValidadorFovisAbstract {

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorJefeHogarActivo.execute");
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM));
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

			// se listan los estados válidos para aprobar la validación
			List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
			estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);


			// se consulta el afiliado con el tipo, número de documento y los
			// estados validos para el mismo
			Afiliado afiliado = (Afiliado) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_SIN_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();

            if (afiliado != null) {
                // validación exitosa
                ConsultarEstadoDTO consulEstado = new ConsultarEstadoDTO();
                consulEstado.setEntityManager(entityManager);
                consulEstado.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());
                consulEstado.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
                consulEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                List<ConsultarEstadoDTO> listConsulEstado = new ArrayList<ConsultarEstadoDTO>();
                /* Se agrega a la lista el estadoDTO */
                listConsulEstado.add(consulEstado);
                List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsulEstado);
                if (estadosValidos.contains(listEstados.get(0).getEstado())) {
                    logger.debug("HABILITADA- Fin de método ValidadorJefeHogarActivo.execute");
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS);
                }
                else {
                    logger.debug("NO HABILITADA- Fin de método ValidadorJefeHogarActivo.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_NO_ACTIVO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            } else {
				// validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorJefeHogarActivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_NO_ACTIVO),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (NoResultException nre) {
			logger.error("No evaluado - no se encuentra el afiliado con los datos ingresados", nre);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
