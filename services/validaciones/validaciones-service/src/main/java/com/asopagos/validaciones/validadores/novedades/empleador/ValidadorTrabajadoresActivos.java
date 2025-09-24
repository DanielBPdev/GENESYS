
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

/**
 * * Validador 30 Trabajadores seleccionados para traslado entre sucursales
 * 	 Valida:
 * 		- Debe haber al menos 1 trabajador seleccionado
 * 		- Los trabajadores seleccionados deben estar activos con el empleador
 * 		- Los trabajadores seleccionados serán objeto de la novedad
 * 
 * * Validador 31	Trabajadores seleccionados para sustitución patronal
 * 	 Valida:
 * 		- Debe haber al menos 1 trabajador seleccionado
 * 		- Los trabajadores seleccionados deben estar Activos con el empleador origen
 * 		- Los trabajadores seleccionados son los que serán objeto de la novedad"
 *  
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorTrabajadoresActivos extends ValidadorAbstract {
	/*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorTrabajsSeleccionadosTraslSucur.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			String idsPersonaRecibidos = datosValidacion.get(ConstantesValidaciones.IDS_PERSONAS);
			String [] idsPersonas = idsPersonaRecibidos.split(","); 
			
			Long [] idsPersonasLong = new Long [idsPersonas.length];
			int i = 0;
			for (String string : idsPersonas) {
				idsPersonasLong[i] = new Long(string.trim()).longValue();
				i++;
			}
			
			List<RolAfiliado> rolAfiliado = (List<RolAfiliado>) entityManager
					.createNamedQuery(NamedQueriesConstants.BUSCAR_TRABAJADORES_ACTIVOS)
					.setParameter(ConstantesValidaciones.IDS_PERSONAS, Arrays.asList(idsPersonasLong))
					.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)	
					.setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
					.getResultList();
			
	        // Se valida la condición
			if(rolAfiliado != null
					&& !rolAfiliado.isEmpty()
					&& rolAfiliado.size() == idsPersonas.length){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorTrabajsSeleccionadosTraslSucur.execute");
				// Validación exitosa, Validador 30	Trabajadores seleccionados para traslado entre sucursales
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTrabajsSeleccionadosTraslSucur.execute");
				// Validación no aprobada, Validador 30	Trabajadores seleccionados para traslado entre sucursales
				return crearValidacion(myResources.getString(
						ConstantesValidaciones.KEY_TRABAJADOR_SELECCIONADO_NO_ACTIVO)
						,ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_TRABAJADORES_ACTIVOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
