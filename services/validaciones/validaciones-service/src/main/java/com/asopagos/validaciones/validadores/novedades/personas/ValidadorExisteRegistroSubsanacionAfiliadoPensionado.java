package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.ExpulsionSubsanada;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 *
 * Validar que no se ha registrado una novedad de "Registrar
 * subsanación de expulsión" relacionada con la más reciente novedad de
 * desafiliación que tuvo motivo de desafiliación: <br>
 * - Expulsión por mora (independientes y pensionados)<br>
 * - Expulsión por uso indebido de servicios <br>
 * - Expulsión por suministro de información incorrecta o no entrega de
 * información
 * 
 * 
 * Verificar que el valor del campo "Expulsión subsanada?", tenga un valor
 * diferente a "S" para aprobar la validación
 * 
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorExisteRegistroSubsanacionAfiliadoPensionado extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            // Se valida que el motivo de desafiliacion sea alguno de estos:
            // - Expulsión por mora NO APLICA PARA DEPENDIENTES
            // - Expulsión por uso indebido de servicios
            // - Expulsión por suministro de información incorrecta o no entrega
            // de información
            List<MotivoDesafiliacionAfiliadoEnum> motivosValidos = new ArrayList<>();
            motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.MAL_USO_DE_SERVICIOS_CCF);
            motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF);
            motivosValidos.add(MotivoDesafiliacionAfiliadoEnum.RETIRO_POR_MORA_APORTES);

            // Se consultan los roles afiliados asociados a la persona que se encuentren inactivos
            List<RolAfiliado> listRolAfiliado = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_ESTADO_FECHA_RETIRO_MOTIVODESA, RolAfiliado.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.INACTIVO)
                    .setParameter(ConstantesValidaciones.TIPO_AFILIADO_PARAM, TipoAfiliadoEnum.PENSIONADO)
                    .setParameter(ConstantesValidaciones.MOTIVO_DESAFILIACION, motivosValidos).getResultList();

            // Se consulta las expulsiones subsanadas asociadas a la persona
            List<ExpulsionSubsanada> listExpulsionSubsanada = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTA_EXPULSION_SUBSANDA_ROL_AFILIADO, ExpulsionSubsanada.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

            if (noExisteSubsanacion(listRolAfiliado, listExpulsionSubsanada)) {
                logger.debug("VALIDACION EXITOSA- Fin de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
                // Validación exitosa
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_PENSIONADO);
            }
            else {
                logger.debug("VALIDACION FALLIDA- Fin de método ValidadorExisteRegistroSubsanacionAfiliado.execute");
                // Validación no aprobada
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_EXISTE_REGISTRO_SUBSANACION),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_PENSIONADO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                    ValidacionCoreEnum.VALIDACION_EXISTE_REGISTRO_SUBSANACION_AFILIADO_PENSIONADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Verifica que no exista subsnación a la expulsión para algún rol afiliado
     * @param listRolAfiliado
     *        Lista de roles afiliados asociados a la persona objeto de validación
     * @param listExpulsionSubsanada
     *        Lista de explusiones subsanadas asociadas a la persona objeto de la validación
     * @return TRUE Permite el registro de subsanción FALSE Indica que los roles afiliados ya fueron subsandos
     */
    private Boolean noExisteSubsanacion(List<RolAfiliado> listRolAfiliado, List<ExpulsionSubsanada> listExpulsionSubsanada) {
        if (listRolAfiliado == null || listRolAfiliado.isEmpty()) {
            return Boolean.FALSE;
        }
        else if ((listRolAfiliado != null && !listRolAfiliado.isEmpty())
                && (listExpulsionSubsanada == null || listExpulsionSubsanada.isEmpty())) {
            return Boolean.TRUE;
        }
        Boolean result = Boolean.FALSE;
        for (RolAfiliado rolAfiliado : listRolAfiliado) {
            if (!verificarSubsnacionRol(rolAfiliado, listExpulsionSubsanada)) {
                result = Boolean.TRUE;
                break;
            }
        }
        return result;
    }

    /**
     * Verifica si el rol afiliado ya fue subsando
     * @param rolAfiliado
     *        Información de rol afiliado
     * @param listExpulsionSubsanada
     *        Lista de explusiones subsanadas asociadas a la persona objeto de la validación
     * @return TRUE Si el rol tiene registrada una subsanación FALSE en caso contrario
     */
    private Boolean verificarSubsnacionRol(RolAfiliado rolAfiliado, List<ExpulsionSubsanada> listExpulsionSubsanada) {
        Boolean rolSubsanado = Boolean.FALSE;
        for (ExpulsionSubsanada expulsionSubsanada : listExpulsionSubsanada) {
            if (rolAfiliado.getIdRolAfiliado().equals(expulsionSubsanada.getIdRolAfiliado())
                    && (expulsionSubsanada.getExpulsionSubsanada() != null && expulsionSubsanada.getExpulsionSubsanada())) {
                rolSubsanado = Boolean.TRUE;
                break;
            }
        }
        return rolSubsanado;
    }

}
