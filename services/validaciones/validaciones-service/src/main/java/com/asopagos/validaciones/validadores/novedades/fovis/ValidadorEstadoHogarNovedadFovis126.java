package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si el hogar tiene estado:
 * Postulado, Hábil, Hábil segundo año o Susupendido por cambio de año
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorEstadoHogarNovedadFovis126 extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            // Lista con los estados del hogar definidos para la validación
            List<String> estadosHogar = new ArrayList<>();
            estadosHogar.add(EstadoHogarEnum.POSTULADO.name());
            estadosHogar.add(EstadoHogarEnum.HABIL.name());
            estadosHogar.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
            estadosHogar.add(EstadoHogarEnum.SUSPENDIDO_POR_CAMBIO_DE_ANIO.name());

            if (!verificarEstadoHogarPostulacion(datosValidacion, estadosHogar)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_INVALIDO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_126,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_126);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_126, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

}
