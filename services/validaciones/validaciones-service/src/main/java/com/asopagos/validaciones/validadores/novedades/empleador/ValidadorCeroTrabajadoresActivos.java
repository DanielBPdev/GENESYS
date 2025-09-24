package com.asopagos.validaciones.validadores.novedades.empleador;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorCeroTrabajadoresActivos extends ValidadorAbstract{

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorCeroTrabajadoresActivos.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			logger.info("2G numeroIdentificacion "+numeroIdentificacion);
			
			
			
			
			int total = (int) entityManager
					.createNamedQuery(NamedQueriesConstants.TOTAL_TRABAJADORES_CERO)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)	
					.getSingleResult();

			int totalInactivos = (int) entityManager
					.createNamedQuery(NamedQueriesConstants.TOTAL_TRABAJADORES_INACTIVOS_CERO)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)	
					.getSingleResult();
			logger.info("2G total "+total);
			logger.info("2G totalInactivos "+totalInactivos);
			// logger.info("2G "+ );
            
	        // Se valida la condición
			if(total == totalInactivos){
				logger.info("VALIDACION EXITOSA- Fin de método ValidadorTrabajsSeleccionadosTraslSucur.execute");
				// Validación exitosa, Validador 30	Trabajadores seleccionados para traslado entre sucursales
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTrabajsSeleccionadosTraslSucur.execute");
				// Validación no aprobada, Validador 30	Trabajadores seleccionados para traslado entre sucursales
				return crearValidacion(myResources.getString(
						ConstantesValidaciones.KEY_TRABAJADOR_SELECCIONADO_NO_ACTIVO)
						,ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_CERO_TRABAJADORES_ACTIVOS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
    
}