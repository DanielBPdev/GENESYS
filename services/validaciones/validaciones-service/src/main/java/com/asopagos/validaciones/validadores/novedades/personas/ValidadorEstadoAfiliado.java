package com.asopagos.validaciones.validadores.novedades.personas;

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
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Valor del campo "Estado de afiliación" del afiliado principal: "Activo", "Inactivo", "No formalizado"
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoAfiliado extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorEstadoAfiliado.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        estadosValidos.add(EstadoAfiliadoEnum.INACTIVO);
	        //estadosValidos.add(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);
	        //estadosValidos.add(EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
	        estadosValidos.add(null);
	        
	        //se consulta el afiliado con el tipo, número de documento y los estados validos para el mismo
	        Afiliado afiliado= (Afiliado) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_SIN_ESTADO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					//.setParameter(ConstantesValidaciones.ESTADOS_VALIDOS_AFILIADO_PARAM, estadosValidos)
					.getSingleResult();
			
            if (afiliado != null) {
                ConsultarEstadoDTO consulEstado = new ConsultarEstadoDTO();
                consulEstado.setEntityManager(entityManager);
                consulEstado.setNumeroIdentificacion(afiliado.getPersona().getNumeroIdentificacion());
                consulEstado.setTipoIdentificacion(afiliado.getPersona().getTipoIdentificacion());
                consulEstado.setTipoPersona(ConstantesComunes.PERSONAS);
                List<ConsultarEstadoDTO> listConsulEstado = new ArrayList<ConsultarEstadoDTO>();
                /* Se agrega a la lista el estadoDTO */
                listConsulEstado.add(consulEstado);
                List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsulEstado);
                //validación exitosa
                if (estadosValidos.contains(listEstados.get(0).getEstado())) {
                    logger.debug("HABILITADA- Fin de método ValidadorEstadoAfiliado.execute");
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO);
                }
                else {
                    //validación fallida
                    logger.debug("NO HABILITADA- Fin de método ValidadorEstadoAfiliado.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_AFILIADO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }

            }else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorEstadoAfiliado.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_AFILIADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		}catch (NoResultException nre){
			logger.error("No evaluado - no se encuentra el afiliado con los datos ingresados", nre);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		} 
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_ESTADO_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
