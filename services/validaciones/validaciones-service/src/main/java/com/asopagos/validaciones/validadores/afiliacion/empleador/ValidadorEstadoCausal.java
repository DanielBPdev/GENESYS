/**
 * 
 */
package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;



/**
 * Clase que contiene la lógica para validar el estado y la causal al momento de retirarse.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class ValidadorEstadoCausal extends ValidadorAbstract{

	/* (non-Javadoc)
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorTiempoReintegro.execute");
		try{
			String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
			
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			
			 List<ConsultarEstadoDTO> listConsulta = new ArrayList<ConsultarEstadoDTO>();
	            ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
	            paramsConsulta.setEntityManager(entityManager);
	            paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
	            paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
	            paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
	            listConsulta.add(paramsConsulta);

	            List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
	            empleador.setEstadoEmpleador(EstadoEmpleadorEnum.NO_FORMALIZADO_CON_INFORMACION);
	            if(listEstado.get(0).getEstado() != null){
	                empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(listEstado.get(0).getEstado().toString()));	                
	            }
	            
			if(empleador.getEstadoEmpleador()!=null){
				List<EstadoEmpleadorEnum> estadoEmpleadorList = new ArrayList<EstadoEmpleadorEnum>();
				estadoEmpleadorList.add(EstadoEmpleadorEnum.INACTIVO);
				estadoEmpleadorList.add(EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
				if(estadoEmpleadorList.contains(empleador.getEstadoEmpleador())){
					return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_CAUSAL);				
				}else{
					return crearValidacion(
							myResources.getString(ConstantesValidaciones.KEY_ESTADO_CAUSAL),
							ResultadoValidacionEnum.NO_APROBADA,ValidacionCoreEnum.VALIDACION_ESTADO_CAUSAL, null);
				}
			}
			return crearMensajeNoEvaluado();
		}catch (Exception e){
			logger.error("Ocurrio un error en el método ValidadorTiempoReintegro.execute");
			return crearMensajeNoEvaluado();
		
		}
	}
	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * 
	 * @return validacion afiliaacion instanciada.
	 */
	private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_ESTADO_CAUSAL),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_ESTADO_CAUSAL,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1 );
	}
}

