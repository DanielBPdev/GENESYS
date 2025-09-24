package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar si existe una persona como fallecida.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaFallecida extends ValidadorAbstract {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(ValidadorPersonaFallecida.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.info("Inicio de método ValidadorPersonaFallecida.execute");

        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.info("No evaluado- No esta lleno el mapa");
                return crearMensajeNoEvaluado();
            }
            
            for (Map.Entry<String, String> entry : datosValidacion.entrySet()) {
                logger.info("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
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
                logger.info("No evaludao- no hay parametros");
                return crearMensajeNoEvaluado();
            }

            boolean existe = false;
            List<Persona> personasConTipoYNumero = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO_FALLECIDO, Persona.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.FALLECIDO_PARAM, Boolean.TRUE).getResultList();
            // Si no existe fallecido por tipo y número identificación se verifica por solo número
            if ((personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()) && tipoIdentificacion == null) {
                logger.info("1 log");
                List<Persona> personasConNumero = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NUMERO_FALLECIDO, Persona.class)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                        .setParameter(ConstantesValidaciones.FALLECIDO_PARAM, Boolean.TRUE).getResultList();
                if (personasConNumero != null && !personasConNumero.isEmpty()) {
                    existe = true;
                }
            }
            else if(!personasConTipoYNumero.isEmpty()) {
                logger.info("2 log");
                existe = true;
            }
            if (existe) {
                logger.info("No aprobada- Existe persona afiliada para el mismo empleador de tipo dependiente");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            logger.info("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió un error inesperado", e);
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
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_FALLECIDA),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_FALLECIDA,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }
}
