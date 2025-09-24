package com.asopagos.validaciones.validadores.fovis.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.NoResultException;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si el jefe de hogar ha sido beneficiario de algun tipo de subsidio de vivienda (v11)
 *
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaBeneficiarioOtrosSubsidios extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {

        logger.debug("Inicio de método ValidadorPersonaBeneficiarioOtrosSubsidios.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            // Se obtienen los datos para la validar
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String modalidad = datosValidacion.get(ConstantesValidaciones.MODALIDAD_POSTULACION);
            String idPostulacion = datosValidacion.get(ConstantesValidaciones.ID_POSTULACION);
            //String hogarPerdioSubsidioNoPago = datosValidacion.get(ConstantesValidaciones.HOGAR_PERDIO_SUBSIDIO_NO_PAGO);
            String hogarPerdioSubsidioNoPago = String.valueOf(datosValidacion.get(ConstantesValidaciones.HOGAR_PERDIO_SUBSIDIO_NO_PAGO));

            logger.info("MODALIDAD " + modalidad);

            logger.info("HOGAR_PERDIO_SUBSIDIO_NO_PAGO " + hogarPerdioSubsidioNoPago);

            if (tipoIdentificacion == null || tipoIdentificacion.isEmpty() || numeroIdentificacion == null || numeroIdentificacion.isEmpty()) {
                logger.debug("No evaluado - No llegaron todos los parámetros");
                return crearMensajeNoEvaluado();
            }

            // Se consulta las postulaciones asociadas a la persona
            List<PostulacionFOVIS> listPostulaciones = this.consultarPostulacionVigentePersonaActiva(tipoIdentificacion, numeroIdentificacion);
            logger.info("listPostulaciones " + listPostulaciones);
            if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
                return this.evaluarExcepciones(tipoIdentificacion, numeroIdentificacion, modalidad, listPostulaciones, idPostulacion, hogarPerdioSubsidioNoPago);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA);

        } catch (Exception e) {
            logger.info("No evaluado - Ocurrió alguna excepción----- ");
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
        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR + myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_PRESENTA_EXCEPCIONES_AFILIACION), ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
    }

    /**
     * Mensaje utilizado tiene una postulacion en curso abierta.
     *
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeExcepcion() {
        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIA_MEJORAMIENTO), ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA, TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
    }

    /**
     * Consulta si la persona enviada esta activa como jefe o integrante en una postulacion dentro de los estados
     *
     * @param tipoIdentificacion   Tipo identificacion jefe o integrante
     * @param numeroIdentificacion Numero identificacion jefe o integrante
     * @return Lista de postulaiones en las que esta asociada  la persona
     */
    private List<PostulacionFOVIS> consultarPostulacionVigentePersonaActiva(String tipoIdentificacion, String numeroIdentificacion) {
        List<String> listEstadosNoValidos = new ArrayList<>();
        /*listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_SIN_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.RESTITUIDO_CON_SANCION.name());
        listEstadosNoValidos.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.PENDIENTE_APROBACION_PRORROGA.name());
        listEstadosNoValidos.add(EstadoHogarEnum.HABIL.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_PENDIENTE_REEMBOLSAR.name());
        listEstadosNoValidos.add(EstadoHogarEnum.HABIL_SEGUNDO_ANIO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.POSTULADO.name());*/

        logger.info("tipoIdentificacion " + tipoIdentificacion);
        logger.info("numeroIdentificacion " + numeroIdentificacion);
        logger.info("listEstadosNoValidos " + listEstadosNoValidos);
        logger.info("estado" + EstadoFOVISHogarEnum.ACTIVO.name());

        return entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR__POSTULACION, PostulacionFOVIS.class)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.ESTADO_HOGAR_PARAM, EstadoFOVISHogarEnum.ACTIVO.name())
                /*.setParameter(ConstantesValidaciones.ESTADO_HOGAR_POS_PARAM, listEstadosNoValidos)*/
                .getResultList();
    }

    /**
     * Consulta el estado hogar de la persona
     *
     * @param tipoIdentificacion   Tipo identificacion jefe o integrante
     * @param numeroIdentificacion Numero identificacion jefe o integrante
     * @return Lista de postulaiones en las que esta asociada  la persona
     */
    private String consultarEstadoHogar(String tipoIdentificacion, String numeroIdentificacion) {
        List<String> listEstadosNoValidos = new ArrayList<>();
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_LEGALIZADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name());

        logger.info("tipoIdentificacion estado hogar" + tipoIdentificacion);
        logger.info("numeroIdentificacion estado hogar" + numeroIdentificacion);
        logger.info("listEstadosNoValidos estado hogar" + listEstadosNoValidos);

        String estadoHogar = (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_ESTADO_HOGAR_JEFE_HOGAR)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.ESTADO_HOGAR_POS_PARAM, listEstadosNoValidos)
                .getSingleResult();

        System.out.println("estadoHogar ----" + estadoHogar);

        return estadoHogar;
    }

    /**
     * Consulta el estado hogar de la persona
     *
     * @param tipoIdentificacion   Tipo identificacion jefe o integrante
     * @param numeroIdentificacion Numero identificacion jefe o integrante
     * @return Lista de postulaiones en las que esta asociada  la persona
     */
    private String consultarEstadoHogarIntegrante(String tipoIdentificacion, String numeroIdentificacion) {
        List<String> listEstadosNoValidos = new ArrayList<>();
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_LEGALIZADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO.name());
        listEstadosNoValidos.add(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name());

        logger.info("tipoIdentificacion estado hogar integrante" + tipoIdentificacion);
        logger.info("numeroIdentificacion estado hogar integrante" + numeroIdentificacion);
        logger.info("listEstadosNoValidos estado hogar integrante" + listEstadosNoValidos);

        String estadoHogar = (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_ESTADO_HOGAR_INTEGRANTE)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.ESTADO_HOGAR_POS_PARAM, listEstadosNoValidos)
                .getSingleResult();


        System.out.println("estadoHogarIntegrante ----" + estadoHogar);

        return estadoHogar;
    }

    /**
     * Identifica si la postulación encontrada es la misma que esta haciendo la validación
     *
     * @param idPostulacion     Identificador postulación
     * @param listPostulaciones Lista de postulaciones asociadas persona
     * @return True si es la misma, false en caso contrario
     */
    private Boolean verificarPostulacionDiferente(String idPostulacion, List<PostulacionFOVIS> listPostulaciones) {
        logger.info("verificarPostulacionDiferente-------");
        logger.info("idPostulacion-->" + idPostulacion);
        Boolean rep = false;
        if (idPostulacion == null || idPostulacion.isEmpty()) {
            logger.info("true 1");
            return Boolean.TRUE;
        }
        Long idPost = Long.parseLong(idPostulacion);
        logger.info("idPost--->" +  idPost);
        for (PostulacionFOVIS postulacionFOVIS : listPostulaciones) {
            logger.info("postulacionFOVIS.getIdPostulacion()--->" + postulacionFOVIS.getIdPostulacion());
            if ((postulacionFOVIS.getIdPostulacion().equals(idPost)) || postulacionFOVIS.getIdPostulacion() == null) {
                logger.info("TRUE 2");
                rep = Boolean.TRUE;
                break;
            } else {
                logger.info("FALSE 2");
                rep =  Boolean.FALSE;
            }
        }
        logger.info("respuesta--->" + rep);
        return rep;
    }

    /**
     * Metodo que evalua si la persona ha sido beneficiario de subsidio de viveinda
     * en la modalidad Mejora de Vivienda Saluble
     *
     * @param tipoIdentificacion   tipo de identificación de la persona.
     * @param numeroIdentificacion número de identificación de la persona.
     * @param modalidad            modalidad de la postulacion a ingresar
     * @return true si ha sido beneficiario de Mejora de Vivienda Saludable
     */
    @SuppressWarnings("unchecked")
    private boolean mejoraViviendaSualudableConModalidadValida(String tipoIdentificacion, String numeroIdentificacion, String modalidad, String hogarPerdioSubsidioNoPago) {
        try {
            logger.debug("Inicio de método ValidadorPersonaBeneficiarioOtrosSubsidios.haSidoBeneficiarioMejoraViviendaSualudable");

            logger.info("---------------------mejoramiento-------" + tipoIdentificacion + numeroIdentificacion + modalidad);

            List<String> modalidadPostulacion = new ArrayList<>();
            modalidadPostulacion.add(ModalidadEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE.name());
            modalidadPostulacion.add(ModalidadEnum.MEJORAMIENTO_VIVIENDA_RURAL.name());
            modalidadPostulacion.add(ModalidadEnum.MEJORAMIENTO_VIVIENDA_URBANA.name());

            logger.info("--------------------");

            logger.info("tipoIdentificacionIntegrante " + tipoIdentificacion);
            logger.info("numeroIdentificacionIntegrante " + numeroIdentificacion);
            logger.info("modalidadPostulacionIntegrante " + modalidadPostulacion);

            //Se consultan registros como inegrante de hogar
            List<Integer> registrosIntegranteHogar = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_MODALIDAD_POSTULACION_PERSONA)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.MODALIDAD_POSTULACION, modalidadPostulacion)
                    .getResultList();

            logger.info("registrosIntegranteHogar " + registrosIntegranteHogar);

            logger.info("tipoIdentificacion " + tipoIdentificacion);
            logger.info("numeroIdentificacion " + numeroIdentificacion);
            logger.info("modalidadPostulacion " + modalidadPostulacion);

            //Se consultan registros como jefe de hogar
            List<Integer> registrosJefeHogar = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_MODALIDAD_POSTULACION_JEFE_HOGAR)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.MODALIDAD_POSTULACION, modalidadPostulacion)
                    .getResultList();

            logger.info("registrosJefeHogar " + registrosJefeHogar);

            logger.info("ingresa ");

            String estadoHogar = "";

            System.out.println("estadoHogar --- " + estadoHogar);

            System.out.println("hogarPerdioSubsidioNoPago --- " + hogarPerdioSubsidioNoPago);

            logger.info("ingresa estado " + estadoHogar);

            logger.info("hogarPerdioSubsidioNoPago " + hogarPerdioSubsidioNoPago);

            if (hogarPerdioSubsidioNoPago == "false" || hogarPerdioSubsidioNoPago == null) {
                if (registrosJefeHogar != null && !registrosJefeHogar.isEmpty()) {
                    estadoHogar = this.consultarEstadoHogar(tipoIdentificacion, numeroIdentificacion);
                    System.out.println("ingreso estado hogar jefe " + estadoHogar);
                } else if (registrosIntegranteHogar != null && !registrosIntegranteHogar.isEmpty()) {
                    estadoHogar = this.consultarEstadoHogarIntegrante(tipoIdentificacion, numeroIdentificacion);
                    System.out.println("ingreso estado hogar integrante " + estadoHogar);
                } else {
                    System.out.println("ingreso true estado hogar ----- ");
                    return false;
                }

                System.out.println("ESTADOHOGAR " + estadoHogar);

                logger.info("Ingresa primer if mejoramiento");
                logger.info("modalidad mejoramiento" + modalidad);

                String contruccionUrbano = ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.name();
                String contruccionRural = ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_RURAL.name();

                logger.info("contruccionUrbano: " + contruccionUrbano);
                logger.info("contruccionRural: " + contruccionRural);

                if (modalidad != null && !(modalidad.equals(ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.name()) || modalidad.equals(ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_RURAL.name()))) {
                    logger.info("ingreso estado hogar : " + estadoHogar);
                    if (estadoHogar.equals(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name())) {
                        logger.info("ingresa al true desembolsado");
                        return true;
                    }
                    logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.mejoraViviendaSualudableConModalidadValida");
                    logger.info("Se cumple la condicion mejoramiento");
                    return false;
                } else {
                    logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.mejoraViviendaSualudableConModalidadValida");
                    logger.info("ingresa al else mejoramiento");
                    return false;
                }
	            /*}
	            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.mejoraViviendaSualudableConModalidadValida");
	            logger.info("ingresa al return else mejoramiento" );
	            return true;*/
            }
            return true;
        } catch (NoResultException e) {
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.mejoraViviendaSualudableConModalidadValida");
            logger.info("ingresa al else final mejoramiento");
            return false;
        }
    }

    public static String formatearCalendar(Calendar c) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return df.format(c.getTime());
    }


    /**
     * Metodo que evalua si la persona ha sido beneficiario de subsidio de viveinda
     * en la modalidad Adquisicion de Vivienda
     *
     * @param tipoIdentificacion   tipo de identificación de la persona.
     * @param numeroIdentificacion número de identificación de la persona.
     * @param modalidad            modalidad de la postulacion a ingresar
     * @return true si ha sido beneficiario de Adquisicion de Vivienda
     */
    @SuppressWarnings("unchecked")
    private boolean adquicisionViviendaConModalidadValida(String tipoIdentificacion, String numeroIdentificacion, String modalidad, String hogarPerdioSubsidioNoPago) {
        try {
            logger.debug("Inicio de método ValidadorPersonaBeneficiarioOtrosSubsidios.haSidoBeneficiarioAdquisicionVivienda");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("yyyy-MM-dd HH:mm:ss-> " + dtf.format(LocalDateTime.now()));

            Calendar c = Calendar.getInstance();
            logger.info("fecha calendar" + formatearCalendar(c));

            c.add(Calendar.YEAR, -10);
			/*String Date = formatearCalendar(c);
			System.out.println("10 años: " + Date);*/
            Date date = c.getTime();
            System.out.println("10 años: " + date);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
            String date1 = format1.format(date);
            System.out.println("date1-----: " + date1);

            List<String> modalidadPostulacion = new ArrayList<>();
            modalidadPostulacion.add(ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL.name());
            modalidadPostulacion.add(ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA.name());
            modalidadPostulacion.add(ModalidadEnum.ADQUISICION_VIVIENDA_USADA_RURAL.name());
            modalidadPostulacion.add(ModalidadEnum.ADQUISICION_VIVIENDA_USADA_URBANA.name());

            logger.info("--------------------");

            logger.info("tipoIdentificacionIntegrante ADQUISICION" + tipoIdentificacion);
            logger.info("numeroIdentificacionIntegrante ADQUISICION" + numeroIdentificacion);
            logger.info("modalidadPostulacionIntegrante ADQUISICION" + modalidadPostulacion);

            //Se consultan registros como inegrante de hogar
            List<Date> registrosIntegranteHogar = (List<Date>) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_MODALIDAD_POSTULACION_PERSONA)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.MODALIDAD_POSTULACION, modalidadPostulacion)
                    .getResultList();

            logger.info("registrosIntegranteHogar " + registrosIntegranteHogar);

            logger.info("tipoIdentificacion " + tipoIdentificacion);
            logger.info("numeroIdentificacion " + numeroIdentificacion);
            logger.info("modalidadPostulacion " + modalidadPostulacion);

            //Se consultan registros como jefe de hogar
            List<Date> registrosJefeHogar = (List<Date>) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_MODALIDAD_POSTULACION_JEFE_HOGAR)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.MODALIDAD_POSTULACION, modalidadPostulacion)
                    .getResultList();

            logger.info("registrosJefeHogar Adquisicion " + registrosJefeHogar);


            String estadoHogar = "";


            System.out.println("ESTADOHOGAR ADQUISICION " + estadoHogar);

            System.out.println("hogarPerdioSubsidioNoPago Adquisicion --- " + hogarPerdioSubsidioNoPago);

            if (hogarPerdioSubsidioNoPago == "false" || hogarPerdioSubsidioNoPago.equals(null)) {
                logger.info("Ingreso null perdiosub------>");
                if (registrosJefeHogar.size() > 0 && !registrosJefeHogar.isEmpty()) {
                    estadoHogar = this.consultarEstadoHogar(tipoIdentificacion, numeroIdentificacion);
                    System.out.println("ingreso estado hogar jefe " + estadoHogar);
                } else if (registrosIntegranteHogar != null && !registrosIntegranteHogar.isEmpty()) {
                    estadoHogar = this.consultarEstadoHogarIntegrante(tipoIdentificacion, numeroIdentificacion);
                    System.out.println("ingreso estado hogar integrante " + estadoHogar);
                } else {
                    System.out.println("ingreso false estado hogar ----- ");
                    return false;
                }

                System.out.println("ESTADOHOGAR " + estadoHogar);
                logger.info("modalidad adquisicion" + modalidad);


                System.out.println("registrosJefeHogar.get(0) " + registrosJefeHogar.get(0));

                boolean validacion = registrosJefeHogar.get(0).toString().compareTo(date1) < 0;

                boolean aprobada = true;

                logger.info("validacion ----" + validacion);
                if (modalidad != null && !(modalidad.equals(ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.name()) || modalidad.equals(ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_RURAL.name()) || modalidad.equals(ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL.name()) || modalidad.equals(ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA.name()) || modalidad.equals(ModalidadEnum.ADQUISICION_VIVIENDA_USADA_URBANA.name())) && validacion == aprobada) {
                    logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.adquicisionViviendaConModalidadValida");
                    logger.info("Se cumple la condicion mejoramiento");

                    if (estadoHogar.equals(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO.name())) {
                        logger.info("ingresa al true desembolsado adquisicion");
                        return true;
                    }
                    logger.info("ingresa al else desembolsado");
                    return false;
                } else {
                    logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.adquicisionViviendaConModalidadValida");
                    logger.info("ingresa al else");
                    return false;
                }
	            /*}
	            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.adquicisionViviendaConModalidadValida");
	            logger.info("ingresa al return else" );
	            return true; */
            }
            return true;
        } catch (NoResultException e) {
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.adquicisionViviendaConModalidadValida");
            logger.info("ingresa al else final");
            return false;
        }
    }

    /**
     * Metodo que evalua si la persona ha registrado una novedad de Conformacion de nuevo hogar
     *
     * @param tipoIdentificacion   tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion número de identificación del jefe de hogar.
     * @return true si el solicitante ha registrado la novedad de Mejora de Conformacion de nuevo hogar
     */
    private boolean registraConformacionNuevoHogar(String tipoIdentificacion, String numeroIdentificacion) {
        try {
            logger.debug("Inicio de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haRegistradoMejoraConformacionNuevoHogar");
            /* se verifica si está al día con el tipo y número de documento */

            List<TipoTransaccionEnum> tipoNovedad = new ArrayList<>();

            tipoNovedad.add(TipoTransaccionEnum.CONFORMACION_NUEVO_HOGAR);

            List<Integer> conformacionNuevoHogar = entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_PERSONA_REGISTRA_NOVEDAD_HOGAR).setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, tipoNovedad).getResultList();
            logger.debug("Fin de método ValidadorJefeHogarBeneficiarioOtrosSubsidios.haRegistradoMejoraConformacionNuevoHogar");
            logger.info("conformacionNuevoHogar: " + conformacionNuevoHogar);

            if (conformacionNuevoHogar != null && !conformacionNuevoHogar.isEmpty()) {
                logger.info("ingreso al if---------: ");
                return true;
            }
            return false;
        } catch (NoResultException e) {
            return false;
        }
    }


    /**
     * @param tipoIdentificacion   tipo de identificación de la persona.
     * @param numeroIdentificacion número de identificación de la persona.
     * @param modalidad            modalidad de la postulacion a ingresar
     * @param postulaciones        Lista de postulaciones a las que ha sido asociada la persona
     * @return
     */
    private ValidacionDTO evaluarExcepciones(String tipoIdentificacion, String numeroIdentificacion, String modalidad, List<PostulacionFOVIS> listPostulaciones, String idPostulacion, String hogarPerdioSubsidioNoPago) {
        logger.debug("Inicio de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
        if (this.verificarPostulacionDiferente(idPostulacion, listPostulaciones)) {
            logger.info("Ingreso verificarPostulacionDiferente");
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA);
        }
        if (!this.mejoraViviendaSualudableConModalidadValida(tipoIdentificacion, numeroIdentificacion, modalidad, hogarPerdioSubsidioNoPago)) {
            logger.info("Ingreso mejoraViviendaSualudableConModalidadValida");
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIA_MEJORAMIENTO),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

        }else if (!this.adquicisionViviendaConModalidadValida(tipoIdentificacion, numeroIdentificacion, modalidad, hogarPerdioSubsidioNoPago)) {
            logger.info("Ingreso adquicisionViviendaConModalidadValida");
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_BENEFICIARIA_ADQUISICION),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        }

        /*else if (!this.registraConformacionNuevoHogar(tipoIdentificacion, numeroIdentificacion)) {
            logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_PRESENTA_EXCEPCIONES_AFILIACION),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        } */
        logger.info("Ingreso final");
        logger.debug("Fin de método ValidadorPersonaBeneficiarioOtrosSubsidios.evaluarExcepciones");
        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA);
    }


}
