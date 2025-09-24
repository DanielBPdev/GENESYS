package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si existe mas de 3 (tres) integrantes tipo BISABUELO_HOGAR
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCantidadBisabuelosHogar extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (consultarCantidadIntegrantesPostulacion(getIdPostulacion(datosValidacion),
                    ClasificacionEnum.BISABUELO_HOGAR) > NumerosEnterosConstants.TRES) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_CANTIDAD_BISABUELOS_HOGAR_INVALIDA),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_CANTIDAD_BISABUELOS_HOGAR,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CANTIDAD_BISABUELOS_HOGAR);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_CANTIDAD_BISABUELOS_HOGAR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

}
