package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validador que verifica si el hogar tiene estado:
 * Rechazado
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorEstadoHogarRechazado extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            // Lista con los estados del hogar definidos para la validación
            List<String> estadosHogar = new ArrayList<>();
            estadosHogar.add(EstadoHogarEnum.RECHAZADO.name());

            if (!verificarEstadoHogarPostulacion(datosValidacion, estadosHogar)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_INVALIDO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_RECHAZADO_50_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_RECHAZADO_50_FOVIS);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_RECHAZADO_50_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
