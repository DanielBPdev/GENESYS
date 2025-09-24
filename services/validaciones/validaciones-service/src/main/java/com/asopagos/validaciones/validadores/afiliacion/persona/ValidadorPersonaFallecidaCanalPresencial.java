package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.*;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si la persona se encuentra registrada como fallecida por canal
 * diferente a presecial.
 * @author Mario Andrés Monroy Monroy <mamonroy@heinsohn.com.co>
 */
public class ValidadorPersonaFallecidaCanalPresencial extends ValidadorAbstract {

    private static final ILogger logger = LogManager.getLogger(ValidadorPersonaFallecidaCanalPresencial.class);

    //Tipo de transacción que se deja para poder realizar la novedad una vez fallecido el administrador de subsidio
    private static final TipoTransaccionEnum tipoTransaccionT1 = TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE;

    private static final List<String> LISTA_TRANSACCIONES_FALLECIMIENTO = Arrays.asList(
        TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS.name(),
        TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB.name(),
        TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_WEB.name()
    );

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPersonaFallecidaCanalPresencial.execute");

        Objects.requireNonNull(datosValidacion, "Datos de validación no proporcionados");

        TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.getOrDefault(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM)));
        String numeroIdentificacion = datosValidacion.getOrDefault(ConstantesValidaciones.NUM_ID_BENEF_PARAM, datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM));

        if (tipoIdentificacion == null || numeroIdentificacion == null) {
            logger.info("Tipo de identificación o número de identificación no proporcionados.");
            return crearMensajeNoEvaluado();
        }

        List<String> personasConTipoYNumero = entityManager
            .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO_FALLECIDO_CANAL_PRESENCIAL)
            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
            .setParameter(ConstantesValidaciones.FALLECIDO_PARAM, Boolean.TRUE)
            .setParameter(ConstantesValidaciones.TIPO_TRANSACCION, LISTA_TRANSACCIONES_FALLECIMIENTO)
            .setParameter(ConstantesValidaciones.RESULTADO_PROCESO_PARAM, ResultadoProcesoEnum.APROBADA.name())
            .getResultList();

        if (!personasConTipoYNumero.isEmpty() && CanalRecepcionEnum.PRESENCIAL.name().equals(personasConTipoYNumero.get(0))) {
            String tipoTransaccionStr = datosValidacion.getOrDefault(ConstantesValidaciones.TIPO_TRANSACCION, datosValidacion.get(ConstantesValidaciones.TIPO_TRANSACCION));
            TipoTransaccionEnum tipoTransaccion = tipoTransaccionStr != null ? TipoTransaccionEnum.valueOf(tipoTransaccionStr) : null;
            logger.info("tipo de transaccion dentro del validadorPersonaFallecidaCanalPresencial "+ tipoTransaccion);
            if( tipoTransaccion !=null && tipoTransaccion.equals(tipoTransaccionT1)){
                crearMensajeNoEvaluado();
            }else{
                return crearValidacion(
                    myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA_PRESENCIAL),
                    ResultadoValidacionEnum.NO_APROBADA,
                    ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_PRESENCIAL,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2
                );
            }
        }
        logger.debug("Finaliza método ValidadorPersonaFallecidaCanalPresencial.execute");
        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_PRESENCIAL);
    }

    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
            myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR + myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA_PRESENCIAL),
            ResultadoValidacionEnum.NO_EVALUADA,
            ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_PRESENCIAL,
            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1
        );
    }
}
