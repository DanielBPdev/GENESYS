/**
 *
 */
package com.asopagos.validaciones.validadores.fovis.common;

import com.asopagos.aportes.clients.InvocarCalculoEstadoServiciosIndPen;
import com.asopagos.aportes.dto.EstadoServiciosIndPenDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.*;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Validadador que verifica que el jefe de hogar tenga aportes, cuando es independiente o pensionado,
 * en al menos uno de los empleadores en los que esté activo.
 *
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra Zuluaga</a>
 */
public class ValidadorJefeHogarAlDiaAportes extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */

    String classNameActually = ValidadorJefeHogarAlDiaAportes.class.getSimpleName();

    private boolean dependiente;
    private boolean independiente;
    private boolean multiafiliado;
    private boolean pensionado;

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        try {
            logger.debug("Inicio del método " + methodName);
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

                if (isValidParameters(tipoIdentificacion, numeroIdentificacion)) {
                    return this.validaAfiliacionConAportesAlDia(tipoIdentificacion, numeroIdentificacion);
                } else {
                    logger.debug(methodName + "No evaluado - No se recibieron todos los parámetros necesarios");
                    return crearMensajeNoEvaluado();
                }
            } else {
                logger.debug(methodName + "No evaluado - El mapa de valores está vacío o nulo");
                return crearMensajeNoEvaluado();
            }
        } catch (Exception e) {
            logger.error("No evaluado - Se produjo una excepción durante la ejecución del método " + methodName, e);
            return crearMensajeNoEvaluado();
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }

    private boolean isValidParameters(String tipoIdentificacion, String numeroIdentificacion) {
        return (tipoIdentificacion != null && !tipoIdentificacion.isEmpty())
                && (numeroIdentificacion != null && !numeroIdentificacion.isEmpty());
    }


    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     *
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_NO_HAY_APORTES_DEPENDIENTE),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_AL_DIA_APORTES,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Método encargado de validar, <b>por tipo y número de identificación</b>, si el jefe de hogar está al día en aportes.
     *
     * @param tipoIdentificacion   tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion número de identificación del jefe de hogar.
     * @return true si está al día en aportes o false si no lo está.
     */
    //utilizamos un mapa (contadorAfiliados) para contar la cantidad de cada tipo de afiliado. Al final, construimos la lista tiposAfiliado a partir de las claves del mapa.
    @SuppressWarnings("unchecked")
    private List<TipoAfiliadoEnum> registraAfiliaciones(String tipoIdentificacion, String numeroIdentificacion) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        try {
            logger.debug("Inicio del método " + methodName);
            logger.info(methodName + "tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);

            dependiente = false;
            independiente = false;
            multiafiliado = false;
            pensionado = false;


            List<RolAfiliado> rolesAfiliado = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_APORTES_TIPO_AFILIACION)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacion))
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO)
                    .getResultList();

            if (rolesAfiliado == null || rolesAfiliado.isEmpty()) {
                logger.info(methodName + "Sin rolesAfiliado para tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);
                return Collections.emptyList();
            }

            logger.info(methodName + "rolesAfiliado.size(): " + rolesAfiliado.size());

            Map<TipoAfiliadoEnum, Integer> contadorAfiliados = new HashMap<>();

            for (RolAfiliado rolAfiliado : rolesAfiliado) {
                TipoAfiliadoEnum tipoAfiliado = rolAfiliado.getTipoAfiliado();
                contadorAfiliados.put(tipoAfiliado, contadorAfiliados.getOrDefault(tipoAfiliado, 0) + 1);
                logger.info(methodName + "rolAfiliado.getTipoAfiliado().name(): " + tipoAfiliado.name() + "CC " + numeroIdentificacion);
            }

            logger.info(methodName + "contador de afiliado: " + Collections.singletonList(contadorAfiliados));

            List<TipoAfiliadoEnum> tiposAfiliado = new ArrayList<>(contadorAfiliados.keySet());
            List<TipoAfiliadoEnum> tiposAfiliado2 = new ArrayList<>(contadorAfiliados.keySet());
            tiposAfiliado2.addAll(contadorAfiliados.keySet());
            logger.info("contadorAfiliados.keySet() " + contadorAfiliados.keySet());
            logger.info(methodName + "tipo de afiliado: " + Collections.singletonList(tiposAfiliado) + "CC -> "+numeroIdentificacion);
            logger.info(methodName + "tiposAfiliado.size(): " + tiposAfiliado.size());
            logger.info(methodName + "tiposAfiliado2.size(): " + tiposAfiliado2.size());

            dependiente = tiposAfiliado.size() == 1 && contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE) &&
                    !contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE) &&
                    !contadorAfiliados.containsKey(TipoAfiliadoEnum.PENSIONADO);
            independiente = tiposAfiliado.size() == 1 &&  (contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE) ||
                    !contadorAfiliados.containsKey(TipoAfiliadoEnum.PENSIONADO)) &&
                    !contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            pensionado = tiposAfiliado.size() == 1 &&  (!contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE) ||
                    contadorAfiliados.containsKey(TipoAfiliadoEnum.PENSIONADO)) &&
                    !contadorAfiliados.containsKey(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            multiafiliado = tiposAfiliado.size() > 1;

            logger.info(methodName + "Result dependiente: " + dependiente + ", independiente: " + independiente + ", multiafiliado: " + multiafiliado + "CEDULA-> " + numeroIdentificacion);

            return tiposAfiliado;

        } catch (NoResultException e) {
            logger.error("NoResultException - Se produjo una excepción durante la ejecución del método " + methodName + " " + e);
            return null;
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }


    /**
     * Metodo que retorna el resultado de las validaciones de los tipos de afiliaciones del trabajador
     *
     * @param tipoIdentificacion   tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion número de identificación del jefe de hogar.
     * @return
     */
    private ValidacionDTO validaAfiliacionConAportesAlDia(String tipoIdentificacion, String numeroIdentificacion) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        try {
            logger.debug("Inicio de método " + methodName);
            logger.info(methodName + "tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);

            List<TipoAfiliadoEnum> tiposAfiliado = this.registraAfiliaciones(tipoIdentificacion, numeroIdentificacion);

            if(tiposAfiliado == null || tiposAfiliado.isEmpty()){
                tiposAfiliado = new ArrayList<>();
            }

            logger.info("pensionado--> " + pensionado);

            if (dependiente && !validateRol(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, tipoIdentificacion, numeroIdentificacion)) {
                logger.info(methodName + "Validación dependiente -> No cumple con los requisitos para trabajador dependiente CC " + numeroIdentificacion);
                return createMessageEnCartera(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            } else if (independiente && !validateRol(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, tipoIdentificacion, numeroIdentificacion)) {
                logger.info(methodName + "Validación independiente -> No cumple con los requisitos para trabajador independiente CC " + numeroIdentificacion);
                return createMessageEnCartera(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
            } else if (pensionado && !validateRol(TipoAfiliadoEnum.PENSIONADO, tipoIdentificacion, numeroIdentificacion)) {
                logger.info(methodName + "Validación pensionado -> No cumple con los requisitos para trabajador pensionado CC " + numeroIdentificacion);
                return createMessageEnCartera(TipoAfiliadoEnum.PENSIONADO);
            } else if (multiafiliado) {
                for (TipoAfiliadoEnum tipoAfiliado : tiposAfiliado) {
                    if (validateRol(tipoAfiliado, tipoIdentificacion, numeroIdentificacion)) {
                        logger.info(methodName + "Validación multiafiliado -> Cumple con los requisitos para " + tipoAfiliado.name());
                        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_AL_DIA_APORTES);
                    }
                }

                TipoAfiliadoEnum ultimoTipoAfiliado = tiposAfiliado.get(tiposAfiliado.size() - 1);
                logger.info(methodName + "Validación multiafiliado -> No cumple con los requisitos para " + ultimoTipoAfiliado.name());
                return createMessageEnCartera(ultimoTipoAfiliado);
            }

            logger.info(methodName + "Validación exitosa para trabajador " +
                    (dependiente ? "dependiente" :
                            independiente ? "independiente o pensionado" :
                                    multiafiliado ? "multiafiliado" :
                                            "NO tiene ningún ROL asociado"));
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_AL_DIA_APORTES);
        } catch (Exception e) {
            logger.error("Excepción durante la ejecución del método " + methodName, e);
            return crearMensajeNoEvaluado();
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }


    /**
     * Método encargado de crear el mensaje de error según el TipoAfiliadoEnum
     *
     * @param tipoAfiliado
     * @return ValidacionDTO
     */
    private ValidacionDTO createMessageEnCartera(TipoAfiliadoEnum tipoAfiliado) {
        ValidacionDTO res = new ValidacionDTO();
        switch (tipoAfiliado) {
            case TRABAJADOR_DEPENDIENTE:
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_NO_HAY_APORTES_DEPENDIENTE),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_AL_DIA_APORTES,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            case TRABAJADOR_INDEPENDIENTE:
            case PENSIONADO:
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_NO_HAY_APORTES_INDEPENDIENTE),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_AL_DIA_APORTES,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            default:
                return res;
        }
    }

    /**
     * Método encargado de ejecutar las validaciones según el TipoAfiliadoEnum
     *
     * @param tipoAfiliado
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return TRUE si pasa la validación de lo contrario FALSE
     */
    private boolean validateRol(TipoAfiliadoEnum tipoAfiliado, String tipoIdentificacion, String numeroIdentificacion) {
        logger.info("tipo afiliado "+ tipoAfiliado.name() + "cedula-> " +numeroIdentificacion);
        switch (tipoAfiliado) {
            case TRABAJADOR_DEPENDIENTE:
                return validateTrabajadorDependiente(tipoIdentificacion, numeroIdentificacion);
            case TRABAJADOR_INDEPENDIENTE:
            case PENSIONADO:
                return validateTrabajadorIndependientePensionado(tipoIdentificacion, numeroIdentificacion, tipoAfiliado);
            default:
                logger.info("Tipo de afiliado no válido: " + tipoAfiliado.name());
                return false;
        }
    }

    private boolean validateTrabajadorDependiente(String tipoIdentificacion, String numeroIdentificacion) {
        return this.registraEmpleadorAlDiaAportes(tipoIdentificacion, numeroIdentificacion);
    }

    private boolean validateTrabajadorIndependientePensionado(String tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado) {
        return this.independientePensiondaoAlDiaAportes(tipoIdentificacion, numeroIdentificacion) &&
                this.calculoEstadoServiciosIndPen(TipoIdentificacionEnum.valueOf(tipoIdentificacion), numeroIdentificacion, tipoAfiliado);
    }

    /**
     * Metodo que evaluda si un afiliado dependiente registra por lo menos un empleador activo al dia en aportes
     *
     * @param tipoIdentificacion   tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion número de identificación del jefe de hogar.
     * @return
     */
    @SuppressWarnings("unchecked")
    private Boolean registraEmpleadorAlDiaAportes(String tipoIdentificacion, String numeroIdentificacion) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        try {
            logger.debug("Inicio del método " + methodName);
            logger.info(methodName + "tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);
            Boolean resp = Boolean.FALSE;

            /*List<String> estadosCarteraEmpleadores = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_DEPENDIENTE_CON_EMPLEADOR_AL_DIA_APORTES)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM, EstadoEmpleadorEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_OPERACION_CARTERA_PARAM, EstadoOperacionCarteraEnum.VIGENTE.name())
                    .setParameter(ConstantesValidaciones.TIPO_SOLCITANTE, TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                    .getResultList();*/
            //NUM_ID_EMPLEADOR_PARAM TIPO_ID_EMPLEADOR_PARAM
            List<Object[]> empresas = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_JEFEHOGAR_EMPRESAS_AFILIADAS)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                    .getResultList();

            logger.info("empresas-->" + empresas.size());
            Integer cont = 0;
            if(empresas != null && !empresas.isEmpty()){
                for(Object[] empresa: empresas){
                    cont++;
                    logger.info("contador " + cont);
                    String numeroIdentificacionEmpleador = empresa[0].toString();
                    String tipoIdentificacionEmpleador = empresa[1].toString();
                    logger.info("empresa for 0 --> " + numeroIdentificacionEmpleador);
                    logger.info("empresa for 1 --> " + tipoIdentificacionEmpleador);
                    logger.info("boolean inicial " + resp + " empresaEmpleador -->" + numeroIdentificacionEmpleador);
                    List<String> estadoOperacion = entityManager
                            .createNamedQuery(NamedQueriesConstants.VALIDAR_JEFEHOGAR_DEPENDIENTE_TRABAJADOR_EMPLEADOR_AL_DIA_CARTERA)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador)
                            .setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
                            .getResultList();
                    logger.info("estadoOperacion.size() " +estadoOperacion);
                    String estado = EstadoOperacionCarteraEnum.VIGENTE.name();
                    Boolean existe = estadoOperacion.contains(estado);
                    logger.info(existe);
                    if(estadoOperacion != null && !estadoOperacion.isEmpty() && existe){
                        logger.info("La empresa " + numeroIdentificacionEmpleador + " NO esta al dia con el empleador");
                        resp = Boolean.FALSE;
                    }else {
                        logger.info("La empresa " + numeroIdentificacionEmpleador + " esta al dia con el empleador");
                        return true;
                    }
                }
                logger.info("salio del for " + resp);
            }else{
                logger.info(methodName + "No se encontro al trabajador afiliado a una empresa ***");
            }

            /*List<String> estadosCarteraEmpleadores = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_DEPENDIENTE_CON_EMPLEADOR_AL_DIA_APORTES)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_EMPLEADOR_PARAM, EstadoEmpleadorEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_OPERACION_CARTERA_PARAM, EstadoOperacionCarteraEnum.VIGENTE.name())
                    .setParameter(ConstantesValidaciones.TIPO_SOLCITANTE, TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                    .getResultList();

            if (estadosCarteraEmpleadores != null && !estadosCarteraEmpleadores.isEmpty()) {
                for (String estado : estadosCarteraEmpleadores) {
                    EstadoCarteraEnum estadoCartera = EstadoCarteraEnum.valueOf(estado);

                    if (estadoCartera.equals(EstadoCarteraEnum.AL_DIA)) {
                        logger.info(methodName + "Estado de aportes para empleadores: AL DÍA ***");
                        return true;
                    }
                }
                logger.info(methodName + "Estado de aportes para empleadores: NO AL DÍA ***");
                return false;
            }*/

            logger.info(methodName + "El empleador esta AL DIA? ***" + resp);
            return resp;
        } catch (Exception e) {
            logger.error("No evaluado - Se produjo una excepción durante la ejecución del método " + methodName, e);
            return false;
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }


    /**
     * Metodo que evalua si el jefe de hogar en calidad de independiente o pensionado presenta aportes al dia
     * en al menos una de sus afiliaciones
     *
     * @param tipoIdentificacion   tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion número de identificación del jefe de hogar.
     * @return
     */
    @SuppressWarnings("unchecked")
    private Boolean independientePensiondaoAlDiaAportes(String tipoIdentificacion, String numeroIdentificacion) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        try {
            logger.debug("Inicio del método " + methodName);
            logger.info(methodName + "tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion);

            List<String> estadosCarteraTrabajador = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_INDEPENDIENTE_PENSIONADO_AL_DIA_APORTES)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_OPERACION_CARTERA_PARAM, EstadoOperacionCarteraEnum.VIGENTE.name())
                    .getResultList();

            if (estadosCarteraTrabajador != null && !estadosCarteraTrabajador.isEmpty()) {
                for (String estado : estadosCarteraTrabajador) {
                    EstadoCarteraEnum estadoCartera = EstadoCarteraEnum.valueOf(estado);

                    if (estadoCartera.equals(EstadoCarteraEnum.AL_DIA)) {
                        logger.info(methodName + "Estado de aportes para trabajador independiente o pensionado: AL DÍA ***"+ "cedula-> " + numeroIdentificacion);
                        return true;
                    }
                }
                logger.info(methodName + "Estado de aportes para trabajador independiente o pensionado: NO AL DÍA ***" + "cedula-> " + numeroIdentificacion);
                return false;
            }

            logger.info(methodName + "No se encontraron registros en la cartera para trabajador independiente o pensionado ***" + "cedula-> " + numeroIdentificacion);
            return true;
        } catch (Exception e) {
            logger.error("No evaluado - Se produjo una excepción durante la ejecución del método " + methodName, e);
            return false;
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }


    /**
     * Método que evalúa si el jefe de hogar en calidad de independiente o pensionado está CON SERVICIOS  o SIN SERVICIOS. Devuelve TRUE si están los servicios activos
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private boolean calculoEstadoServiciosIndPen(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado ) {
        String methodName = classNameActually + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";

        try {
            logger.debug("Inicio del método " + methodName);
            logger.info(methodName + "tipoIdentificacion: " + tipoIdentificacion + ", numeroIdentificacion: " + numeroIdentificacion + ", tipoAfiliacion: " + tipoAfiliado);

            EstadoServiciosIndPenDTO estadoServiciosIndPenDTO = new EstadoServiciosIndPenDTO();

            InvocarCalculoEstadoServiciosIndPen invocarCalculoEstadoServiciosIndPen = new InvocarCalculoEstadoServiciosIndPen(
                    null, tipoAfiliado, numeroIdentificacion, tipoIdentificacion);

            invocarCalculoEstadoServiciosIndPen.execute();
            estadoServiciosIndPenDTO = invocarCalculoEstadoServiciosIndPen.getResult();

            if (estadoServiciosIndPenDTO != null && estadoServiciosIndPenDTO.getEstadoServicios() != null) {
                switch (estadoServiciosIndPenDTO.getEstadoServicios()) {
                    case ACTIVOS:
                        logger.info(methodName + "Estado de servicios para trabajador independiente o pensionado: ACTIVOS ***");
                        return true;
                    case INACTIVOS:
                        logger.info(methodName + "Estado de servicios para trabajador independiente o pensionado: INACTIVOS ***");
                        return false;
                    default:
                        logger.info(methodName + "Estado de servicios para trabajador independiente o pensionado desconocido: " +
                                estadoServiciosIndPenDTO.getEstadoServicios());
                        return false;
                }
            } else {
                String mensajeRespuesta = (estadoServiciosIndPenDTO != null && estadoServiciosIndPenDTO.getMensajeRespuesta() != null)
                        ? estadoServiciosIndPenDTO.getMensajeRespuesta()
                        : "persona NO tiene estados de servicios";
                logger.info(methodName + " " + mensajeRespuesta);
                return false;
            }
        } catch (Exception e) {
            logger.error("No evaluado - Se produjo una excepción durante la ejecución del método " + methodName, e);
            return false;
        } finally {
            logger.debug("Fin del método " + methodName);
        }
    }


}
