package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 43
 * - Valida que el campo "Requiere inactivación de cuenta web?" tiene el valor "si"
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorRequiereInactivarCuentaWeb extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorRequiereInactivarCuentaWeb.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {

                boolean requiereInactivacion = Boolean.parseBoolean(
                		datosValidacion.get(ConstantesValidaciones.REQUIERE_INACTIVIACION_WEB));

                if(requiereInactivacion){
                	logger.debug("Aprobado");
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB);
                }else{
                	logger.debug("No aprobada - El empleador no está cubierto por beneficios de Ley 1429 de 2010 y/o el beneficio ha caducado");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_VALOR_CAMPO_REQUIERE_INACTIVACION_CUENTA_WEB),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }else {
                logger.debug("No evaluado- No llegó el mapa con valores");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALOR_CAMPO_REQUIERE_INACTIVACION_CUENTA_WEB, ValidacionCoreEnum.VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB,
                        TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALOR_CAMPO_REQUIERE_INACTIVACION_CUENTA_WEB, ValidacionCoreEnum.VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
