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
 * Validador que verifica si una persona se encuentra registrada como beneficiario activo de algun afiliado principal
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorBeneficiarioActivo extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> camposValidacion = new ArrayList<>();
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_PARAM);
            verificarDatosValidacion(datosValidacion, camposValidacion);

            String tipoIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            if (beneficiarioActivo(tipoIdentificacionBeneficiario, numeroIdentificacionBeneficiario)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_BENEFICIARIO_ACTIVO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_BENEFICIARIO_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si una persona es beneficiario activo de cualquier afiliado principal
     * 
     * @param tipoIdentificacionBeneficiario
     *        Tipo identificación persona
     * @param numeroIdentificacionBeneficiario
     *        Numero identificacion persona
     * @return <code>true</code> si existe la persona como beneficiario activo
     */
    @SuppressWarnings("unchecked")
    private boolean beneficiarioActivo(String tipoIdentificacionBeneficiario, String numeroIdentificacionBeneficiario) {
        List<Integer> beneficiariosAsociados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_ACTIVO)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionBeneficiario)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionBeneficiario)
                .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name()).getResultList();

        return !beneficiariosAsociados.isEmpty();
    }
}
