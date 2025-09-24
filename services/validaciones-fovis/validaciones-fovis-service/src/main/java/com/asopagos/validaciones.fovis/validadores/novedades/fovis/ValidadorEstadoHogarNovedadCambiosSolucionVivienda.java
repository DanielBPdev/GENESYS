package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validador que verifica si el hogar tiene estado:
 * Postulado, Hábil o Hábil segundo año
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorEstadoHogarNovedadCambiosSolucionVivienda extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            // Lista con los estados del hogar definidos para la validación
            List<String> estadosHogar = new ArrayList<>();
            estadosHogar.add(EstadoHogarEnum.ASIGNADO_SIN_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.PENDIENTE_APROBACION_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_LEGALIZADO.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO.name());

            if (!verificarEstadoHogarPostulacion(datosValidacion, estadosHogar)) {
                logger.info("fallo en la consulta de los hogares ");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_INVALIDO_FOVIS_132),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_132,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_132);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_132,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
