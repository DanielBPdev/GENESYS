/**
 * 
 */
package com.asopagos.validaciones.validadores.afiliacion.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorCore;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;


/**
 * @author jcamargo
 *
 */
public class ValidacionEmpleadorIsActivo implements ValidadorCore {
	private final ILogger logger = LogManager.getLogger(ValidacionEmpleadorIsActivo.class);
	

	private ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		if (datosValidacion != null && !datosValidacion.isEmpty()) {
			String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);

			String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			
			if ((tipoIdentificacion != null) && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {
				
				List<Empleador> result = buscarEmpleador(TipoIdentificacionEnum.valueOf(tipoIdentificacion), numeroIdentificacion, null, null);
				if (result != null && !result.isEmpty()) {
					Empleador emp = result.iterator().next();
                    EstadoEmpleadorEnum estadoEmpleador = consultarEstadoEmpleador(TipoIdentificacionEnum.valueOf(tipoIdentificacion),
                            numeroIdentificacion);
					try {
						if (emp != null) {

							if (estadoEmpleador!=null && estadoEmpleador.toString().equals(EstadoEmpleadorEnum.ACTIVO.toString())) {
								logger.info("ValidadorEstadoEmpleador ValidacionEmpleadorIs Activo aprobada");
								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_ACTIVO),
										ResultadoValidacionEnum.APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_IS_ACTIVO);
							} else {
								logger.info("ValidadorEstadoEmpleador ValidacionEmpleadorIsActivo no aprobada");

								return crearValidacion(
										myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_NO_ACTIVO),
										ResultadoValidacionEnum.NO_APROBADA,
										ValidacionCoreEnum.VALIDACION_EMPLEADOR_IS_ACTIVO);
							}
						}
					} catch (NoResultException e) {
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_EMP_NO_ACTIVO),
								ResultadoValidacionEnum.APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_IS_ACTIVO);
					}
				}
			} else {
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_PARAMS),
						ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_IS_ACTIVO);
			}
		}
		return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_IS_ACTIVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#setEntityManager(javax.
	 * persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private List<Empleador> buscarEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			Short digitoVerificacion, String razonSocial) {

		List<Empleador> listEmpleador = new ArrayList<>();

		if (razonSocial != null) {
			// consultar empleador por razon social
			listEmpleador = (List<Empleador>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL)
					.setParameter("razonSocial", "%".concat(razonSocial.concat("%"))).getResultList();

		} else if (digitoVerificacion != null && tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo, numero de identificacion y digito
			// de verificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("digitoVerificacion", digitoVerificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

		} else if (tipoIdentificacion != null && numeroIdentificacion != null) {
			// consultar empleador por tipo y numero de identificacion
			listEmpleador = (List<Empleador>) entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO)
					.setParameter("tipoIdentificacion", tipoIdentificacion)
					.setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();
		}
		
		List<ConsultarEstadoDTO> listConsulta= new ArrayList<ConsultarEstadoDTO>();
		for (Empleador empleador : listEmpleador) {
		    ConsultarEstadoDTO paramsConsulta= new ConsultarEstadoDTO();
		    paramsConsulta.setEntityManager(entityManager);
		    paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
		    paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
		    paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
		    listConsulta.add(paramsConsulta);
        }
        if (!listConsulta.isEmpty() && listConsulta != null) {
            List<EstadoDTO> listEstadod = EstadosUtils.consultarEstadoCaja(listConsulta);
            for (EstadoDTO estadoDTO : listEstadod) {
                for (Empleador empleador : listEmpleador) {
                    if (empleador.getEmpresa().getPersona().getNumeroIdentificacion().equals(estadoDTO.getNumeroIdentificacion())
                            && empleador.getEmpresa().getPersona().getTipoIdentificacion().equals(estadoDTO.getTipoIdentificacion())) {
                    	if(estadoDTO.getEstado() != null){
                    		empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(estadoDTO.getEstado().toString()));
                    	}
                        break;
                    }
                }
            }
        }

		return listEmpleador;
	}

    private EstadoEmpleadorEnum consultarEstadoEmpleador(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        ConsultarEstadoDTO consultEstado = new ConsultarEstadoDTO();
        consultEstado.setEntityManager(entityManager);
        consultEstado.setTipoIdentificacion(tipoIdentificacion);
        consultEstado.setNumeroIdentificacion(numeroIdentificacion);
        consultEstado.setTipoPersona(ConstantesComunes.EMPLEADORES);
        List<ConsultarEstadoDTO> listConsultEstados = new ArrayList<ConsultarEstadoDTO>();
        listConsultEstados.add(consultEstado);
        List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listConsultEstados);
        if(listEstados.get(0).getEstado() != null){
        	return EstadoEmpleadorEnum.valueOf(listEstados.get(0).getEstado().toString());
        }else{
        	return null;
        }
    }
	private ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado,
			ValidacionCoreEnum validacion) {
		ValidacionDTO resultadoValidacion = new ValidacionDTO();
		resultadoValidacion.setDetalle(detalle);
		resultadoValidacion.setResultado(resultado);
		resultadoValidacion.setValidacion(validacion);
		return resultadoValidacion;
	}
}