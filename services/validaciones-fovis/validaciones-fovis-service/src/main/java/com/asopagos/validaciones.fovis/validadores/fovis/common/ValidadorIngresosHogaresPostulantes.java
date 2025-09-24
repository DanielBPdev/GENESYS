package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.math.BigDecimal;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si un hogar postulante que tiene ingresos de mas de 2 SMLV cumple con el requisito
 * de tener un ahorro previo igual o superior al 10% del valor del campo Valor de la solucion de vivienda (v19)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorIngresosHogaresPostulantes extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorIngresosHogaresPostulantes.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            if (tipoIdentificacion == null || tipoIdentificacion.isEmpty() || numeroIdentificacion == null
                    || numeroIdentificacion.isEmpty()) {
                logger.debug("No evaluado - No llegaron todos los parámetros");
                return crearMensajeNoEvaluado();
            }

            if (hogarGanaMasdeDosSmlv(tipoIdentificacion, numeroIdentificacion)
                    && !tieneAhorroPrevioValido(tipoIdentificacion, numeroIdentificacion)) {
                
                return crearValidacion(
                        myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_AHORRO_PREVIO_INFERIOR),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_AHORRO_PREVIO_HOGARES_INGRESOS_SUPERIORES_2_SMLMV,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AHORRO_PREVIO_HOGARES_INGRESOS_SUPERIORES_2_SMLMV);

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
                        + myResources.getString(ConstantesValidaciones.KEY_AHORRO_PREVIO_INFERIOR),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_AHORRO_PREVIO_HOGARES_INGRESOS_SUPERIORES_2_SMLMV,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Metodo que evalua si el hogar postulante gana mas de 2(dos) SMLV
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion
     *        número de identificación del jefe de hogar.
     * @return true el hogar gana mas de 2 SMLV
     */
    private boolean hogarGanaMasdeDosSmlv(String tipoIdentificacion, String numeroIdentificacion) {
        try {
            logger.debug("Inicio de método ValidadorIngresosHogaresPostulantes.hogarGanaMasdeDosSmlv");
            BigDecimal ingresosHogar = (BigDecimal) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_INGRESOS_HOGAR)
                    .setParameter(ConstantesValidaciones.ESTADO_AFILIADO_PARAM, EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_JEFE_HOGAR, EstadoFOVISHogarEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.ESTADO_INTEGRANTE_HOGAR, EstadoFOVISHogarEnum.ACTIVO.name())
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
            
            BigDecimal valorSalario = new BigDecimal((String) CacheManager.getParametro(ParametrosSistemaConstants.SMMLV));

            logger.debug("Fin de método ValidadorIngresosHogaresPostulantes.hogarGanaMasdeDosSmlv");
            if (ingresosHogar.compareTo(valorSalario.multiply(new BigDecimal(2))) > 0) {
                return true;
            }
            return false;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Metodo que evalua si el hogar postulante teine un ahorro previo igual o superior al 10%
     * del campo Valor de solucion de la vivienda
     * @param tipoIdentificacion
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion
     *        número de identificación del jefe de hogar.
     * @return true el hogar tiene un ahorro previo valido
     */
    private boolean tieneAhorroPrevioValido(String tipoIdentificacion, String numeroIdentificacion) {

        try {
            logger.debug("Inicio de método ValidadorIngresosHogaresPostulantes.tieneAhorroPrevioValido");
            entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_AHORRO_PREVIO)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
            logger.debug("Fin de método ValidadorIngresosHogaresPostulantes.tieneAhorroPrevioValido");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
