package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 48 Validar que el tipo de documento de identificación sea NIT
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorTipoDocumentoNIT extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio ValidadorTipoDocumentoNIT.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty() || datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) == null) {
                logger.debug("VALIDACION NO EVALUDA - Fin método ValidadorTipoDocumentoNIT.execute");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV, ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_NIT,
                        TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));

            List<TipoIdentificacionEnum> documentosValidos = new ArrayList<TipoIdentificacionEnum>();
            documentosValidos.add(TipoIdentificacionEnum.NIT);

            // Se valida la condición
            if (documentosValidos.contains(tipoIdentificacion)) {
                logger.debug("VALIDACION EXITOSA - Fin de método ValidadorTipoDocumentoNIT.execute");
                // Validación exitosa, Validador 48 
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_NIT);
            }
            else {
                logger.debug("VALIDACION FALLIDA- Fin de método ValidadorTipoDocumentoNIT.execute");
                // Validación no aprobada, Validador 48 
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NOVEDADES_EMPLEADOR_VALIDADOR_48),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_NIT,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV, ValidacionCoreEnum.VALIDACION_TIPO_DOCUMENTO_NIT,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
