package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
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
 * CLASE QUE VALIDA:
 * Valida que el pensionado tiene activo un pagador de aportes.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPensionadoConPagadorAportesActivo extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPensionadoConPagadorAportesActivo.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        //se consulta el afiliado con el tipo y número de documento y verifica el estado en la entidad pagadora de aportes
	        RolAfiliado rolAfiliado = (RolAfiliado) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_PENSIONADO_CON_PAGADOR_APORTES_ACTIVO)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.setParameter(ConstantesValidaciones.ESTADO_EN_ENTIDAD_PAGADORA, EstadoActivoInactivoEnum.ACTIVO)
					.setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.PENSIONADO)
					.getSingleResult();
	        
	        //se listan los estados válidos para aprobar la validación
	        List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<EstadoAfiliadoEnum>();
	        estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
	        //se realiza la validación
			if(rolAfiliado != null){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorPensionadoConPagadorAportesActivo.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PENSIONADO_PAGADOR_APORTES_ACTIVO);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorPensionadoConPagadorAportesActivo.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PENSIONADO_PAGADOR_APORTES_ACTIVO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_PENSIONADO_PAGADOR_APORTES_ACTIVO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_PENSIONADO_PAGADOR_APORTES_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}