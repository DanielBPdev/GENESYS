package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si el jefe de hogar registra en el formulario FOVIS a su padre
 * y este es distinto al que se encuentra registrado en base de datos (v31)
 * 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPadreDistintoFormulario extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {

        logger.debug("Inicio ValidadorPadreDistintoFormulario.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("NO EVALUADO - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }

            String tipoIdentificaBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
            String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

            if (tipoIdentificaBeneficiario == null || tipoIdentificaBeneficiario.isEmpty() || numeroIdentificacionBeneficiario == null
                    || numeroIdentificacionBeneficiario.isEmpty() || tipoIdentificacionJefeHogar == null
                    || tipoIdentificacionJefeHogar.isEmpty() || numeroIdentificacionJefeHogar == null
                    || numeroIdentificacionJefeHogar.isEmpty()) {
                logger.debug("NO EVALUADO - No llegaron todos los parámetros");
                return crearMensajeNoEvaluado();
            }

            TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoIdentificaBeneficiario);
            TipoIdentificacionEnum tipoIdentificacionJefe = TipoIdentificacionEnum.valueOf(tipoIdentificacionJefeHogar);

            // Se verifica si el Jefe Hogar tiene informacion de Beneficiario Madre diferente a la enviada en la postulacion
            if (!beneficiarioIgualRegistrado(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario, tipoIdentificacionJefe,
                    numeroIdentificacionJefeHogar)) {
                logger.debug("NO APROBADA - Fin ValidadorPadreDistintoFormulario.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PADRE_FORMULARIO_DISTINTO_BD),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PADRE_DISTINTO_FORMULARIO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            logger.debug("APROBADA - Fin ValidadorPadreDistintoFormulario.execute");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PADRE_DISTINTO_FORMULARIO);
        } catch (Exception e) {
            logger.error("NO EVALUADO - Ocurrió una excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     * 
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        logger.debug("Fin ValidadorPadreDistintoFormulario.execute");
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PADRE_FORMULARIO_DISTINTO_BD),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PADRE_DISTINTO_FORMULARIO,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Verifica si el Jefe Hogar tiene registrado un beneficiario padre diferente al indicado en el registro de postulacion
     * @param tipoIdentificacionBeneficiario
     *        Tipo identificacion beneficiario a agregar
     * @param numeroIdentificacionBeneficiario
     *        Numero identicacion beneficiario a agregar
     * @param tipoIdentificacionJefeHogar
     *        Tipo identificacion jefe hogar
     * @param numeroIdentificacionJefeHogar
     *        Numero identificacion jefe hogar
     * @return TRUE Si el beneficiario enviado es igual al registrado o no existe registrado, FALSE en caso contrario
     */
    private boolean beneficiarioIgualRegistrado(TipoIdentificacionEnum tipoIdentificacionBeneficiario,
            String numeroIdentificacionBeneficiario, TipoIdentificacionEnum tipoIdentificacionJefeHogar,
            String numeroIdentificacionJefeHogar) {
        // Se consulta el beneficiario
        Beneficiario beneficiario = consultarBeneficiarioPadreAsociadoJefe(numeroIdentificacionJefeHogar, tipoIdentificacionJefeHogar);

        // Si no existe beneficiario, se cataloga como igual al registrado en la postulacion
        boolean beneficiarioIgual = true;
        if (beneficiario != null && (!beneficiario.getPersona().getNumeroIdentificacion().equals(numeroIdentificacionBeneficiario)
                || !beneficiario.getPersona().getTipoIdentificacion().equals(tipoIdentificacionBeneficiario))) {
            beneficiarioIgual = false;
        }
        return beneficiarioIgual;
    }

    /**
     * Consulta si existe el beneficiario Madre asociado al jefe hogar
     * @param numeroIdentificacionJefe
     *        Numero identificacion Jefe Hogar
     * @param tipoIdentificacionJefe
     *        Tipo identificacion Jefe Hogar
     * @return Informacion Beneficiario madre asociado
     */
    private Beneficiario consultarBeneficiarioPadreAsociadoJefe(String numeroIdentificacionJefe,
            TipoIdentificacionEnum tipoIdentificacionJefe) {
        try {
            return entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_BY_TIPO_ASOCIADO_JEFE_FOVIS, Beneficiario.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM, tipoIdentificacionJefe)
                    .setParameter(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM, numeroIdentificacionJefe)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ClasificacionEnum.PADRE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
