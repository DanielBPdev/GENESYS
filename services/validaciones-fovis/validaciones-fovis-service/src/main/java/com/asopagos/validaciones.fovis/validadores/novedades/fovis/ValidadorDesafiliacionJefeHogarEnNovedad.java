package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Objetivo : Validar si hay una solicitud de novedad de desafiliación del jefe de hogar
 * relacionado a la novedad FOVIS en curso
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorDesafiliacionJefeHogarEnNovedad extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> camposValidacion = new ArrayList<>();
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_PARAM);
            verificarDatosValidacion(datosValidacion, camposValidacion);

            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            if (jefeHogarPresentaDesafiliacionEnNovedad(tipoIdentificacion, numeroIdentificacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_DESAFILIACION_EN_NOVEDAD_EN_CURSO),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DESAFILIACION_EN_NOVEDAD_EN_CURSO_45_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DESAFILIACION_EN_NOVEDAD_EN_CURSO_45_FOVIS);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DESAFILIACION_EN_NOVEDAD_EN_CURSO_45_FOVIS,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si el jefe de hogar presenta una solicitud de desafiliacion en una determinada novedad
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return <code>true</code> Si existe novedad asociadas
     */
    @SuppressWarnings("unchecked")
    private boolean jefeHogarPresentaDesafiliacionEnNovedad(String tipoIdentificacion, String numeroIdentificacion) {
        List<String> retirosValidos = new ArrayList<>();
        retirosValidos.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2.name());
        retirosValidos.add(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR.name());

        List<Integer> novedadesAsociadas = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_DESAFILIACION_JEFE_HOGAR)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).setParameter("retirosValidos", retirosValidos)
                .getResultList();

        return !novedadesAsociadas.isEmpty();
    }
}
