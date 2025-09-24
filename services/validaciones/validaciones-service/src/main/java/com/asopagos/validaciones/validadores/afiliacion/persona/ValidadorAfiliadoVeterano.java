package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorAfiliadoVeterano extends ValidadorAbstract {
    
    /**
     * Esta función de Java comprueba si una persona es un veterano según su identificación y devuelve
     * un resultado de validación.
     * 
     * @param datosValidacion Un mapa que contiene datos de validación. Se espera que tenga las
     * siguientes claves:
     * @return El método devuelve un objeto ValidacionDTO.
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion){
        boolean postulable ;
        try{
            logger.info("validacion afiliado veterano");
            if(datosValidacion != null && !datosValidacion.isEmpty()){
                if(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) != null
                    && !datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM).toString().equals("")
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) != null 
                    && !datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM).toString().equals("")){
                    Object result = entityManager.createNamedQuery(NamedQueriesConstants.VALIDACION_FOVIS_PERSONA_VETERANO)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM).toString())
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM).toString()).getSingleResult();
                    if(((int) result != 1)){   
                        return crearMensajeNoAprovado();
                    }
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FOVIS_PERSONA_VETERANO);
                }
                return crearMensajeNoEvaluado();
            }else{
                return crearMensajeNoEvaluado();
            }
        }catch(Exception e){
            return crearMensajeNoEvaluado();
        }
    }
/**
 * La función crea un mensaje de validación para una persona que es veterano.
 * 
 * @return El método devuelve un objeto ValidacionDTO.
 */

    private ValidacionDTO crearMensajeNoEvaluado() {
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_PERSONA_VETERANO) ,
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_FOVIS_PERSONA_VETERANO,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
	}

    /**
     * La función crea un mensaje de validación para una persona veterana no aprobada.
     * 
     * @return El método devuelve un objeto ValidacionDTO.
     */
    private ValidacionDTO crearMensajeNoAprovado(){
        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_VETERANO),
            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_FOVIS_PERSONA_VETERANO,
            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
    }
}