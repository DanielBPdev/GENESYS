package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * Validar que el afiliado principal tenga un cónyuge activo hasta la fecha del  registro de la novedad.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaConConyugeYBeneficiariosConPadres extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.info("Inicio de método ValidadorPersonaConConyugeYBeneficiariosConPadres.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
			logger.info("Tipo de identificación: " + tipoIdentificacion + ", Número de identificación: " + numeroIdentificacion);
	        //se consulta el beneficiario con el tipo y número de documento del afiliado principal, el tipo de beneficiario y su estado (activo o inactivo)
	        Integer valido = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_TIPO_CONYUGE_HIJOS_COMPARTIDOS)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
                .getSingleResult();
			
			logger.info("Resultado: " + valido);

			if(valido == 1){
				logger.info("Se habilita la novedad");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDAR_PERSONA_CON_BENEFICIARIO_CONYUGE_Y_BENEFICIARIOS_CON_PADRES);
			}else{
				logger.info("No se habilita la novedad");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_COYUGE_EXISTENTE),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDAR_PERSONA_CON_BENEFICIARIO_CONYUGE_Y_BENEFICIARIOS_CON_PADRES,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);	
			}
		} 
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDAR_PERSONA_CON_BENEFICIARIO_CONYUGE_Y_BENEFICIARIOS_CON_PADRES, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}