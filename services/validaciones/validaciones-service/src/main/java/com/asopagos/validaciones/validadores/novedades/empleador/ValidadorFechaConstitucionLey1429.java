package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Date;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Validador 21	Validar que la fecha de constitución del empleador se ubica dentro de la vigencia de la Ley 1429 de 2010
 * Valida:
 * "Validar que el valor del campo ""Fecha de constitución"" asociada al empleador sea:
 * - Igual o posterior al 29 de diciembre de 2010 y 
 * - Anterior o igual al 31 de diciembre de 2014"
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorFechaConstitucionLey1429 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		
		logger.debug("Inicio de método ValidadorVigenciaFechaConstitEmpl.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        
	        // Se consulta el empleador con el tipo y nro documento
			Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
					.getSingleResult();
			// Se consulta el beneficio LEY 1429
	        Beneficio beneficio = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIOS, Beneficio.class)
	                .setParameter("tipoBeneficio",TipoBeneficioEnum.LEY_1429).getSingleResult();
	        if (beneficio == null) {
	            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_VALOR_FECHA_CONSTITUCION_INVALIDO),ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_1429,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
			
	        // Fecha 29 de Diciembre de 2010
	        Date fechaDiciembre29Anio2010  = beneficio.getFechaVigenciaInicio();
	        
	        // Fecha 31 de Diciembre de 2014
	        Date fechaDiciembre31Anio2014  = beneficio.getFechaVigenciaFin();
	        
	        boolean condicionFechaDiciembre29Anio2010 = false;
	        boolean condicionFechaDiciembre31Anio2014 = false;
	        
	        Date fechaConstitucion = CalendarUtils.truncarHora(empleador.getEmpresa().getFechaConstitucion()) ;
	        
	        condicionFechaDiciembre29Anio2010 = CalendarUtils.esFechaMayorIgual(fechaConstitucion, fechaDiciembre29Anio2010);
	        condicionFechaDiciembre31Anio2014 = CalendarUtils.esFechaMenorIgual(fechaConstitucion, fechaDiciembre31Anio2014);
	       
	        // Se validan las condiciones de habilitación
			if(condicionFechaDiciembre29Anio2010 && condicionFechaDiciembre31Anio2014){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorVigenciaFechaConstitEmpl.execute");
				// Validación exitosa, Validador 21	Validar que la fecha de constitución del empleador se ubica dentro de la vigencia de la Ley 1429 de 2010
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_1429);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorVigenciaFechaConstitEmpl.execute");
				// Validación no aprobada, Validador 21	Validar que la fecha de constitución del empleador se ubica dentro de la vigencia de la Ley 1429 de 2010
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_VALOR_FECHA_CONSTITUCION_INVALIDO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_1429,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_1429, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}