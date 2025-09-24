package com.asopagos.validaciones.validadores.fovis.common;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si una persona tiene coincidencia en sus apellidos con el jefe de hogar o el conyuge activo
 * del grupo de hogar, en caso de existir (v20)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCoincidenciaApellidosPersona extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorCoincidenciaApellidosPersona.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
                String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
                String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
                String segundoApellido = datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM);

                if ((tipoIdentificacionAfiliado != null && !tipoIdentificacionAfiliado.equals(""))
                        && (numeroIdentificacionAfiliado != null && !numeroIdentificacionAfiliado.equals(""))) {

                    if ((primerApellido != null && !primerApellido.isEmpty())) {
                        primerApellido = primerApellido.toUpperCase();
                    }
                    if ((segundoApellido != null && !segundoApellido.isEmpty())) {
                        segundoApellido = segundoApellido.toUpperCase();
                    }
                    if (!apellidosCoincidenConJefeHogar(tipoIdentificacionAfiliado, numeroIdentificacionAfiliado, primerApellido,
                            segundoApellido)) {

                        if (poseeConyugue(tipoIdentificacionAfiliado, numeroIdentificacionAfiliado)) {

                            if (!apellidosCoincidenConConyuge(tipoIdentificacionAfiliado, numeroIdentificacionAfiliado, primerApellido,
                                    segundoApellido)) {
                                return crearMensajeNoCoincidencia();
                            }
                        }
                        else {
                            return crearMensajeNoCoincidencia();
                        }
                    }

                }
                else {
                    logger.debug("No evaluado - No llegaron todos los parámetros");
                    return crearMensajeNoEvaluado();
                }
            }
            else {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CONCIDENCIA_APELLIDOS_PERSONA);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     * 
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_APELLIDOS_NO_COINCIDEN),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_CONCIDENCIA_APELLIDOS_PERSONA,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Mensaje utilizado cuando se presenta una no coincidencia en la validación.
     * 
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoCoincidencia() {
        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_APELLIDOS_NO_COINCIDEN),
                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_CONCIDENCIA_APELLIDOS_PERSONA,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Metodo que evalua si una persona tiene coincidencia en sus apellidos con el jefe de hogar
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion
     *        número de identificación del jefe de hogar.
     * @return true si hay coincidencia de apellidos
     */
    private boolean apellidosCoincidenConJefeHogar(String tipoIdentificacion, String numeroIdentificacion, String primerApellido,
            String segundoApellido) {
        try {
            logger.debug("Inicio de método ValidadorCoincidenciaApellidosPersona.apellidosCoincidenConJefeHogar");
            entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_COINCIDENCIA_APELLIDOS_CON_JEFE_HOGAR)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellido)
                    .setParameter(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM, segundoApellido).getSingleResult();
            logger.debug("Fin de método ValidadorCoincidenciaApellidosPersona.apellidosCoincidenConJefeHogar");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Método que evalua si una persona posee conyuge activo en el grupo de hogar.
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @return
     */
    private boolean poseeConyugue(String tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado) {
        try {
            logger.debug("Inicio de método ValidadorCoincidenciaApellidosPersona.poseeConyugue");
            entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_EXISTENCIA_CONYUGE)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacionAfiliado))
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name()).getSingleResult();
            logger.debug("Fin de método ValidadorCoincidenciaApellidosPersona.poseeConyugue");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Metodo que evalua si una persona tiene coincidencia en sus apellidos con el
     * conyuge activo en el grupo de hogar
     * 
     * @param tipoIdentificacionAfiliado
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacionAfiliado
     *        número de identificación del jefe de hogar.
     * @return true si hay coincidencia de apellidos
     */
    private boolean apellidosCoincidenConConyuge(String tipoIdentificacionAfiliado, String numeroIdentificacionAfiliado,
            String primerApellido, String segundoApellido) {
        try {
            logger.debug("Inicio de método ValidadorCoincidenciaApellidosPersona.apellidosCoincidenConConyuge");
            entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_COINCIDENCIA_APELLIDOS_CON_CONYUGE)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacionAfiliado))
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
                    .setParameter(ConstantesValidaciones.PRIMER_APELLIDO_PARAM, primerApellido)
                    .setParameter(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM, segundoApellido)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, TipoBeneficiarioEnum.CONYUGE.name())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name()).getSingleResult();
            logger.debug("Fin de método ValidadorCoincidenciaApellidosPersona.apellidosCoincidenConConyuge");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
