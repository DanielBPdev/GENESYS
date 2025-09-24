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
 * Validador 32	Validar que en empleador destino no esté asociado previamente 
 * alguno de los trabajadores seleccionados para la sustitución patronal
 * Valida:
 * Usando los mismos criterios de búsqueda de personas (HU-Consultar persona), 
 * se debe poder identificar si en el empleador destino ya está asociado un 
 * trabajador activo de los que fueron seleccionados en el empleador origen.
 * (Validar que en el empleador destino no existe un trabajador activo con el 
 * mismo Tipo de identificación, Número de identificación, Primer nombre, 
 * primer apellido, fecha de nacimiento igual a alguno de los trabajadores seleccionados)
 *  
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorDestinoNoAsociado extends ValidadorAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEmpleadorDestinoNoAsociado.execute");
		try{
			// Tipo identificación empleador destino
			TipoIdentificacionEnum tipoIdentificacion 
				= TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPL_DEST_PARAM));
			
			// Nro identificación empleador destino
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPL_DEST_PARAM);
	        
	        // Ids de los trabajadores seleccionados en el empleador origen
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
			if(rolAfiliado == null || rolAfiliado.isEmpty()){
				logger.debug("VALIDACION EXITOSA- Fin de método ValidadorEmpleadorDestinoNoAsociado.execute");
				// Validación exitosa, Validador 32	Validar que en empleador destino no esté asociado...
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_NO_ASOCIADO);	
			}else{
				logger.debug("VALIDACION FALLIDA- Fin de método ValidadorEmpleadorDestinoNoAsociado.execute");
				// Validación no aprobada, Validador 32	Validar que en empleador destino no esté asociado...
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_EMPLEADOR_DESTINO_ASOCIADO),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_NO_ASOCIADO,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
					ValidacionCoreEnum.VALIDACION_EMPLEADOR_DESTINO_NO_ASOCIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}
