package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorFechaVencimientoCertificadoColegioYSuperior extends ValidadorAbstract{

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorFechaVencimientoCertificadoEscolar.execute");
		try{
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
	        
	        Date fecha = (Date) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_FECHA_VENCIMIENTO_CERTIFICADO_ESCOLAR)
	        		.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacion)
					.setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacion)
					.getResultList();	
	        
	        Calendar calendar = null;

	        if (fecha != null){
	        	calendar = Calendar.getInstance();
		        calendar.setTime(fecha);
			}
	        	
			if(calendar != null && calendar.before(Calendar.getInstance())){
				//validación exitosa
				logger.debug("HABILITADA- Fin de método ValidadorFechaVencimientoCertificadoEscolar.execute");
				return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_VENCIMIENTO_CERT_ESCOLAR);	
			}else{
				//validación fallida
				logger.debug("NO HABILITADA- Fin de método ValidadorFechaVencimientoCertificadoEscolar.execute");
				return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_VENCIMIENTO_CERT_ESCOLAR),ResultadoValidacionEnum.NO_APROBADA,
						ValidacionCoreEnum.VALIDACION_FECHA_VENCIMIENTO_CERT_ESCOLAR,
						TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
			}
		} catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_FECHA_VENCIMIENTO_CERT_ESCOLAR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}

}
