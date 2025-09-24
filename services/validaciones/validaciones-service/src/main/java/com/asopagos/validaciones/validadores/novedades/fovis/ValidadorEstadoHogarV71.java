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
 * Asignado con primera prórroga, Asignado con segunda prórroga, Pendiente de aprobación prórroga,
 * Subsidio legalizado, Subsidio desembolsado, Hogar renunció a subsidio asignado, Restituido sin sanción,
 * Rechazado, Hogar desistió de la postulación, Subsidio con anticipo desembolsado o Subsidio reembolsado
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorEstadoHogarV71 extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            // Lista con los estados del hogar definidos para la validación
            List<String> estadosHogar = new ArrayList<>();
            estadosHogar.add(EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.PENDIENTE_APROBACION_PRORROGA.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_LEGALIZADO.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name());
            estadosHogar.add(EstadoHogarEnum.SUBSIDIO_REEMBOLSADO.name());
            estadosHogar.add(EstadoHogarEnum.RECHAZADO.name());
            estadosHogar.add(EstadoHogarEnum.HOGAR_DESISTIO_POSTULACION.name());
            estadosHogar.add(EstadoHogarEnum.RESTITUIDO_SIN_SANCION.name());
            estadosHogar.add(EstadoHogarEnum.RENUNCIO_A_SUBSIDIO_ASIGNADO.name());

            if (!verificarEstadoHogarPostulacion(datosValidacion, estadosHogar)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_HOGAR_INVALIDO_FOVIS_71),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_ASIG_PEND_SUBSI_REN_RESTI_RECH_DESIST_FOVIS_71,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_ASIG_PEND_SUBSI_REN_RESTI_RECH_DESIST_FOVIS_71);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_HOGAR_ASIG_PEND_SUBSI_REN_RESTI_RECH_DESIST_FOVIS_71,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
