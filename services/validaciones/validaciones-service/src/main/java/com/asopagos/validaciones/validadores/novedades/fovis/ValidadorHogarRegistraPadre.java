package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si un hogar presenta integrantes tipo PADRE
 * en cualquier estado con respecto al hogar
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorHogarRegistraPadre extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> integrantesValidos = new ArrayList<>();
            integrantesValidos.add(ClasificacionEnum.PADRE_HOGAR.name());

            if (verificarRegistroIntegrantePostulacion(datosValidacion, integrantesValidos)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HOGAR_REGISTRA_PADRE),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_HOGAR_REGISTRA_PADRE,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HOGAR_REGISTRA_PADRE);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_HOGAR_REGISTRA_PADRE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

}
