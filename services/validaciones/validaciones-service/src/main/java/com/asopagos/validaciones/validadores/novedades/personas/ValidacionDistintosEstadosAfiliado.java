package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si el estado del afiliado es activo,inactivo, no formalizado (retirado oon aportes / con información)
 * @author mamonroy
 *
 */
public class ValidacionDistintosEstadosAfiliado extends ValidadorAbstract{

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            logger.debug("Inicio de método ValidacionDistintosEstadosAfiliado.execute");

            EstadoAfiliadoEnum estadoAfiliado = null;
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String estadoAfiliadoObj = null;
            List<EstadoAfiliadoEnum> estadosValidos = new ArrayList<>();

            estadoAfiliadoObj = (String) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_AFILIADO_CCF)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
            if (estadoAfiliadoObj == null) {
                logger.debug("Fin de método ValidacionEstadoGeneralPersonaCCF.execute");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                        ValidacionCoreEnum.VALIDACION_DISTINTOS_ESTADOS_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }

            estadosValidos.add(EstadoAfiliadoEnum.ACTIVO);
            estadosValidos.add(EstadoAfiliadoEnum.INACTIVO);
            estadosValidos.add(EstadoAfiliadoEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES);
            estadosValidos.add(EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION);

            estadoAfiliado = EstadoAfiliadoEnum.valueOf(estadoAfiliadoObj);

            if (estadosValidos.contains(estadoAfiliado)) {
                logger.debug("Fin de método ValidacionDistintosEstadosAfiliado.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_DISTINTOS_ESTADOS_AFILIADO);
            }

            logger.debug("Fin de método ValidacionDistintosEstadosAfiliado.execute");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_DISTINTOS_ESTADOS_AFILIADO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        } catch (Exception e) {
            logger.error("Fin de método ValidacionEstadoGeneralPersonaCCF.execute", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF,
                    ValidacionCoreEnum.VALIDACION_DISTINTOS_ESTADOS_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

}
