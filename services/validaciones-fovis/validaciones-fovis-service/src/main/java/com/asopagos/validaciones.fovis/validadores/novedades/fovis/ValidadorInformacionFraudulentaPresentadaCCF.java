package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo : Validar si se ha reportado que la información entregada por la persona es fraudulenta o
 * tiene inconsistencias
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorInformacionFraudulentaPresentadaCCF extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> camposValidacion = new ArrayList<>();
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_PARAM);
            verificarDatosValidacion(datosValidacion, camposValidacion);

            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            if (personaBeneficiarioRegistraInformacionFraudulenta(tipoIdentificacion, numeroIdentificacion)
                    || personaJefeHogarRegistraInformacionFraudulenta(tipoIdentificacion, numeroIdentificacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_REGISTRA_INFORMACION_FRAUDULENTA),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_PERSONA_HA_REPORTADO_INFORMACION_FRAUDULENTA_44_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HA_REPORTADO_INFORMACION_FRAUDULENTA_44_FOVIS);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_PERSONA_HA_REPORTADO_INFORMACION_FRAUDULENTA_44_FOVIS,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si una persona jefe hogar ha presentado informacion fraudulenta o inconsistente
     * a la caja de compensacion
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private boolean personaJefeHogarRegistraInformacionFraudulenta(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        List<MotivoDesafiliacionAfiliadoEnum> motivosDesfiliacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MOTIVOS_DESAFILIACION_JEFE_HOGAR, MotivoDesafiliacionAfiliadoEnum.class)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION,
                        MotivoDesafiliacionAfiliadoEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF)
                .getResultList();

        return !motivosDesfiliacion.isEmpty();
    }

    /**
     * Valida si una persona beneficiario ha presentado informacion fraudulenta o inconsistente
     * a la caja de compensación
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private boolean personaBeneficiarioRegistraInformacionFraudulenta(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        List<MotivoDesafiliacionBeneficiarioEnum> motivosDesfiliacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_MOTIVOS_DESAFILIACION_BENEFICIARIO,
                        MotivoDesafiliacionBeneficiarioEnum.class)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION,
                        MotivoDesafiliacionBeneficiarioEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF)
                .getResultList();

        return !motivosDesfiliacion.isEmpty();
    }
}
