package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * 37 - Afiliado principal tiene un beneficiario padre registrado (activo o inactivo)?
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorEstadoPadre extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEstadoPadre.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            //se listan los estados válidos para aprobar la validación
            List<ClasificacionEnum> clasificacion = new ArrayList<ClasificacionEnum>();
            clasificacion.add(ClasificacionEnum.PADRE);

            // Se consulta si el afiliado tiene registrado un beneficario de tipo MADRE
            List<Beneficiario> beneficiarios = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS, Beneficiario.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, clasificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerEstadosAfiliado())
                    .getResultList();

            if (beneficiarios != null && !beneficiarios.isEmpty()) {
                // Validación fallida
                logger.debug("NO HABILITADA- Fin de método ValidadorEstadoMadre.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_ESTADO_PADRE), ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_ESTADO_PADRE, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }

            // Validación exitosa
            logger.debug("HABILITADA- Fin de método ValidadorEstadoMadre.execute");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_ESTADO_PADRE);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_ESTADO_PADRE, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
