package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ValidadorPersonaFallecidaNoCanalPresencial extends ValidadorAbstract{


    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(ValidadorPersonaFallecidaNoCanalPresencial.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.info("Inicio de método ValidadorPersonaFallecidaNoCanalPresencial.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado- No esta lleno el mapa");
                return crearMensajeNoEvaluado();
            }

            TipoIdentificacionEnum tipoIdentificacion = null;
            String numeroIdentificacion = null;
            // Se verifica si se envio la información de beneficiario
            if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
            }
            else {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            }

            if (tipoIdentificacion == null || numeroIdentificacion == null) {
                logger.debug("No evaluado - no hay parametros");
                return crearMensajeNoEvaluado();
            }
            List<String> listaTransaccionesFallecimiento = new ArrayList<>();
            listaTransaccionesFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS.name());
            listaTransaccionesFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB.name());
            listaTransaccionesFallecimiento.add(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_WEB.name());
            
            List<String> personasConTipoYNumero = (List<String>) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO_FALLECIDO_CANAL_PRESENCIAL)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.FALLECIDO_PARAM, Boolean.TRUE)
                    .setParameter(ConstantesValidaciones.TIPO_TRANSACCION, listaTransaccionesFallecimiento)
                    .setParameter(ConstantesValidaciones.RESULTADO_PROCESO_PARAM, ResultadoProcesoEnum.APROBADA.name()).getResultList();
            
            if (!personasConTipoYNumero.isEmpty() 
                    && !CanalRecepcionEnum.PRESENCIAL.name().equals(personasConTipoYNumero.get(0))) {

                logger.info("No aprobada- Persona registrada como fallecida por un canal distinto a presencial");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA_NO_PRESENCIAL),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_NO_PRESENCIAL,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

            }
            
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_NO_PRESENCIAL);
        } catch (Exception e) {
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA_NO_PRESENCIAL),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA_NO_PRESENCIAL,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
