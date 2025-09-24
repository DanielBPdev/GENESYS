package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
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
 * Validar que el beneficiario tipo hijo objeto de la novedad es menor de 23 años
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorHijoMenor23 extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorHijoMenor23.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
	        
	        //se listan los estados válidos para aprobar la validación
	        List<ClasificacionEnum> tiposBeneficiarioHijo = new ArrayList<ClasificacionEnum>();
	        tiposBeneficiarioHijo.add(ClasificacionEnum.HIJO_BIOLOGICO);
	        tiposBeneficiarioHijo.add(ClasificacionEnum.HIJO_ADOPTIVO);
	        tiposBeneficiarioHijo.add(ClasificacionEnum.HIJASTRO);
	        tiposBeneficiarioHijo.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
	        tiposBeneficiarioHijo.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
			
	        //se consulta la fecha de nacimiento del beneficiario con el tipo y 
	        //número de documento, el tipo de beneficiario y su estado (activo o inactivo)
	        Date fechaNacimiento = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_NACIMIENTO_BENEFICIARIO)
	        		.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tiposBeneficiarioHijo)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
					.getSingleResult();
	        
	        int edadEnAnios = NumerosEnterosConstants.UNO_NEGATIVO;
	        
	        //se realiza el calculo de la edad en años del beneficiario tipo hijo
	        if(fechaNacimiento != null)
	        {
	        	Calendar calendar = Calendar.getInstance();
		        calendar.setTime(fechaNacimiento);
		        String fecha = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
		        edadEnAnios = ValidacionPersonaUtils.calcularEdadAnos(fecha);
	        }
		        
	        //se realiza la validación
			if(edadEnAnios != NumerosEnterosConstants.UNO_NEGATIVO && edadEnAnios < NumerosEnterosConstants.VEINTITRES){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorHijoMenor23.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_MENOR_23);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorHijoMenor23.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HIJO_MENOR_23),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_HIJO_MENOR_23,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_HIJO_MENOR_23, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
}