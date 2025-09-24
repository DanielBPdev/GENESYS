package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si que no exista otra solicitud de novedad de personas en curso con
 * estado de la solicitud de registro de novedad diferente a "Cerrada"
 *
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
public class ValidadorSolicitanteTieneNovedadPersonaEnCurso extends ValidadorAbstract {

    /**
     * Lista del tipo de transaccion a procesar de activación/inactivación de
     * beneficiarios
     */
    private static final List<TipoTransaccionEnum> txActivarBeneficiario = new ArrayList<>();

    static{
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_WEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL);
        txActivarBeneficiario.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_WEB);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.info("Inicio de método ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            Long idRolAfiliado = null;
            Long idBeneficiario = null;
            if (datosValidacion.get(ConstantesValidaciones.KEY_ID_ROL_AFILIADO) != null) {
                idRolAfiliado = new Long(datosValidacion.get(ConstantesValidaciones.KEY_ID_ROL_AFILIADO));
            }
            if (datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO) != null) {
                idBeneficiario = new Long(datosValidacion.get(ConstantesValidaciones.ID_BENEFICIARIO));
            }
            TipoTransaccionEnum tipoTransaccion = TipoTransaccionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_TRANSACCION));

            // Se valida si el tipo de transaccion de la novedad es activación de beneficiarios
            if (txActivarBeneficiario.contains(tipoTransaccion)) {
                TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum
                        .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
                String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

                List<SolicitudNovedadPersona> novedadesActivacion = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_NOVEDAD_PERSONA_EN_CURSO_POR_PERSONA_BENEFICICIARIO)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                        .setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario)
                        .setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
                        .setParameter(ConstantesValidaciones.TIPO_TRANSACCION, txActivarBeneficiario).getResultList();

                // Se valida caso de la activación de beneficiarios, se pueden ejecutar varios procesos a la vez,
                // pero verificar que las solicitudes sean para beneficiarios diferentes
                if (novedadesActivacion != null && !novedadesActivacion.isEmpty()) {
                    logger.info("No Aprobada - Fin de método ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute 2");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
            else {
                // Se valida si la persona tiene una novedad en proceso para el tipo de transaccion enviado
                List<SolicitudNovedadPersona> novedadesDetalle = entityManager
                        .createNamedQuery(NamedQueriesConstants.CONSULTA_NOVEDAD_PERSONA_EN_CURSO_POR_PERSONA)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                        .setParameter(ConstantesValidaciones.TIPO_TRANSACCION, tipoTransaccion).getResultList();

                // validar si novedadesDetalle corresponde a la misma solicitud traida por parametro

                if (novedadesDetalle != null && !novedadesDetalle.isEmpty() && datosValidacion.containsKey("idSolicitudCargueMasivo")) {
                    Long idSolicitudParametro = new Long(datosValidacion.get("idSolicitudCargueMasivo"));
                    for (SolicitudNovedadPersona snp : novedadesDetalle) {
                        Long idSolicitud = entityManager.find(SolicitudNovedad.class, snp.getIdSolicitudNovedad()).getSolicitudGlobal().getIdSolicitud();
                        if (idSolicitudParametro.equals(idSolicitud)) {
                            novedadesDetalle.remove(snp);
                            break;
                        }
                    }
                }


                // Se valida si llega el tipo rol afiliado o beneficiario y que
                // esta no corresponda al resultado de la consulta
                if (novedadesDetalle != null && !novedadesDetalle.isEmpty()
                        && ((idRolAfiliado != null) || (idBeneficiario != null))) {
                    logger.debug("No Aprobada - Fin de método ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute");

                    Boolean afiliadoCorresponde = Boolean.FALSE;
                    Boolean beneficiarioCorresponde = Boolean.FALSE;

                    for (SolicitudNovedadPersona snp : novedadesDetalle) {
                        if (snp.getIdBeneficiario() != null && snp.getIdBeneficiario().equals(idBeneficiario)) {
                            beneficiarioCorresponde = Boolean.TRUE;
                            break;
                        }
                        else if (snp.getIdRolAfiliado() != null && snp.getIdRolAfiliado().equals(idRolAfiliado)) {
                            afiliadoCorresponde = Boolean.TRUE;
                            break;
                        }
                    }

                    if (afiliadoCorresponde || beneficiarioCorresponde) {
                    logger.info("No Aprobada - Fin de método ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute 2");
                        
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }

                }
                else if (novedadesDetalle != null && !novedadesDetalle.isEmpty()) {
                    logger.info("No Aprobada - Fin de método ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute 3");

                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción ValidadorSolicitanteTieneNovedadPersonaEnCurso.execute", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
        // Retorna validación exitosa
        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO);
    }

}
