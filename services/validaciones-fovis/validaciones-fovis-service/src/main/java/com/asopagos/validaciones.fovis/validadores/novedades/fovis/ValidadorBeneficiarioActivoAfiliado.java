package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo : Validar si una persona esta registrada como beneficiario activo de afiliado principal
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorBeneficiarioActivoAfiliado extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> camposValidacion = new ArrayList<>();
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
            verificarDatosValidacion(datosValidacion, camposValidacion);

            String tipoIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
            String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
            if (beneficiarioActivoAfiliadoDistinto(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario,
                    tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_ACTIVO_AFILIADO_DISTINTO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_AFILIADO_47_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_AFILIADO_47_FOVIS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO_AFILIADO_47_FOVIS,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si una persona es beneficiario activo de un afiliado
     * 
     * @param tipoIdentificacionBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @param tipoIdentificacionJefeHogar
     * @param numeroIdentificacionJefeHogar
     * @return <code>true</code> si existe beneficiario activo afiliado
     */
    @SuppressWarnings("unchecked")
    private boolean beneficiarioActivoAfiliadoDistinto(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario,
            String tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar) {
        List<Integer> afiliadosAsociados = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_BENFICIARIO_ACTIVO_AFILIADO)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionBeneficiario)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
                .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name()).getResultList();

        return !afiliadosAsociados.isEmpty();
    }
}
