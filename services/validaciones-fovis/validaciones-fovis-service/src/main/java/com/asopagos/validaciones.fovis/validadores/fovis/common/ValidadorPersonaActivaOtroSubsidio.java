package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.TiempoSancionPostulacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si una persona se encuentra activa en otra
 * postulación (v24)
 *
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy
 *         Monroy</a>
 */
public class ValidadorPersonaActivaOtroSubsidio extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPersonaActivaOtroSubsidio.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String idPostulacion = datosValidacion.get(ConstantesValidaciones.ID_POSTULACION);
            String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);

            if (numeroIdentificacion == null || numeroIdentificacion.isEmpty() || tipoIdentificacion == null
                    || tipoIdentificacion.isEmpty() || objetoValidacion == null || objetoValidacion.isEmpty()) {
                logger.debug("No evaluado - No llegaron todos los parámetros");
                return crearMensajeNoEvaluado();
            }
            if (personaActivaOtraPostulacion(tipoIdentificacion, numeroIdentificacion, idPostulacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_SUBSIDIO_OTRO_GRUPO_FAMILIAR),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_SUBSIDIO_OTRO_GRUPO_FAMILIAR,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }

            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_SUBSIDIO_OTRO_GRUPO_FAMILIAR);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     *
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_SUBSIDIO_OTRO_GRUPO_FAMILIAR),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_ACTIVA_SUBSIDIO_OTRO_GRUPO_FAMILIAR,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    private List<PostulacionFOVIS> consultarPostulacionVigentePersonaActiva(String tipoIdentificacion, String numeroIdentificacion) {
        List<String> listEstadosNoValidos = new ArrayList<>();
        listEstadosNoValidos.add(EstadoHogarEnum.POSTULADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.HABIL.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUSPENDIDO_POR_CAMBIO_DE_ANIO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_SIN_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_LEGALIZADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_PENDIENTE_REEMBOLSAR.name());
        listEstadosNoValidos.add(EstadoHogarEnum.RESTITUIDO_CON_SANCION.name());
        //listEstadosNoValidos.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA.name()); //GLPI 60578
        //listEstadosNoValidos.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA.name()); //GLPI 60578
        listEstadosNoValidos.add(EstadoHogarEnum.PENDIENTE_APROBACION_PRORROGA.name());

        logger.info("tipoIdentificacion -----------" + tipoIdentificacion);
        logger.info("numeroIdentificacion -----------" + numeroIdentificacion);

        return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_ASOCIADA_POSTULACION, PostulacionFOVIS.class)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.ESTADO_HOGAR_PARAM, EstadoFOVISHogarEnum.ACTIVO.name())
                .setParameter(ConstantesValidaciones.ESTADO_HOGAR_POS_PARAM, listEstadosNoValidos).getResultList();


    }

    /**
     * Verifica si la persona enviada esta asociada a una postulación vigente
     * @param tipoIdentificacion
     *        Tipo de identificacion de la persona jefe o integrante
     * @param numeroIdentificacion
     *        Numero de identiicacion de la persona jefe o integrante
     * @param idPostulacion
     *        Identificación de la postulación
     * @return True si la persona esta activa, false en caso contrario
     */
    private Boolean personaActivaOtraPostulacion(String tipoIdentificacion, String numeroIdentificacion, String idPostulacion) {

        // Consulta la información de la postulación vigente donde la persona esta activa
        List<PostulacionFOVIS> listPostulaciones = consultarPostulacionVigentePersonaActiva(tipoIdentificacion, numeroIdentificacion);

        logger.info("listPostulaciones " + listPostulaciones);

        if (listPostulaciones != null || !listPostulaciones.isEmpty()) {
            logger.info("INGRESO 1 -----------");
            return Boolean.FALSE;
        }
        logger.info("idPostulacion " + idPostulacion);

        if (idPostulacion == null || idPostulacion.isEmpty()) {
            logger.info("INGRESO 2 -----------");
            return Boolean.TRUE;
        }
        Long idPost = Long.parseLong(idPostulacion);
        for (PostulacionFOVIS postulacionFOVIS : listPostulaciones) {
            if (EstadoHogarEnum.RESTITUIDO_CON_SANCION.equals(postulacionFOVIS.getEstadoHogar())) {
                if (restitucionEsVigente(postulacionFOVIS) && !postulacionFOVIS.getIdPostulacion().equals(idPost)) {
                    // Sanción vigente
                    logger.info("INGRESO 3 -----------");
                    return Boolean.TRUE;
                }
            }
            if (postulacionFOVIS.getIdPostulacion().equals(idPost)) {
                logger.info("INGRESO 4 -----------");
                return Boolean.FALSE;
            }
        }
        logger.info("INGRESO 5 ----------");
        return Boolean.TRUE;
    }

    private Boolean restitucionEsVigente(PostulacionFOVIS postulacionFOVIS){
        try {
            Calendar fechaActual = Calendar.getInstance();

            // Consultar registro novedad Restitución de susbsidio por incumplimiento
            List<String> listTipoTransaccion = new ArrayList<>();
            listTipoTransaccion.add(TipoTransaccionEnum.RESTITUCION_SUBSIDIO_INCUMPLIMIENTO.name());
            Date fechaRegistroNovedad = (Date) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_FOVIS_BY_TIPO_TRANSACCION_POSTULACION)
                    .setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, listTipoTransaccion)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, postulacionFOVIS.getIdPostulacion()).getSingleResult();

            if (fechaRegistroNovedad != null && postulacionFOVIS.getTiempoSancion() != null) {
                TiempoSancionPostulacionEnum tiempoSancion = postulacionFOVIS.getTiempoSancion();
                Calendar fechaNovedad = Calendar.getInstance();
                fechaNovedad.setTime(fechaRegistroNovedad);
                // Se adiciona el tiempo de sancion a la fecha de registro de novedad
                fechaNovedad.add(tiempoSancion.getUnidadTiempo(), tiempoSancion.getValorNumerico());

                return CalendarUtils.esFechaMayor(fechaNovedad, fechaActual);
            }
            return Boolean.TRUE;
        } catch (NoResultException | NonUniqueResultException e) {
            return Boolean.TRUE;
        }
    }

}
