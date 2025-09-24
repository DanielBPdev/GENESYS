package com.asopagos.validaciones.validadores.fovis.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import javax.persistence.StoredProcedureQuery;

/**
 * Validadador que verifica si el solictante cumple con los ingresos maximos permitidos para aspirar a ser beneficiario
 * del susbsidio de vivienda (v18)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorIngresosTotalesPermitidos extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorIngresosTotalesPermitidos.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                logger.info("datosValidacion---> " +datosValidacion);
                String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                Long idPostulacion = Long.valueOf(datosValidacion.get(ConstantesValidaciones.ID_POSTULACION));
                ClasificacionEnum clasificacion = ClasificacionEnum
                        .valueOf(datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM));
                String bloque = datosValidacion.get(ConstantesValidaciones.NAMED_QUERY_PARAM_BLOQUE);

                if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
                        && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {

                    if (!cumpleMaximoIngresos(tipoIdentificacion, numeroIdentificacion, clasificacion, idPostulacion, bloque)) {
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_INGRESOS_MAXIMOS_NO_PERMITIDOS),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_INGRESOS_TOTALES_PERMITIDOS,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }

                }
                else {
                    logger.debug("No evaluado - No llegaron todos los parámetros");
                    return crearMensajeNoEvaluado();
                }
            }
            else {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_INGRESOS_TOTALES_PERMITIDOS);

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
                        + myResources.getString(ConstantesValidaciones.KEY_INGRESOS_MAXIMOS_NO_PERMITIDOS),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_INGRESOS_TOTALES_PERMITIDOS,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Metodo que evalua si el solictante cumple con los ingresos maximos permitidos para aspirar a ser beneficiario
     * del susbsidio de vivienda
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion
     *        número de identificación del jefe de hogar.
     * @return true si los ingresos con iguales o inferiores a los establecidos para aspirar al subsidio
     */
    private boolean cumpleMaximoIngresos(String tipoIdentificacion, String numeroIdentificacion, ClasificacionEnum clasificacion, Long idPostulacion, String bloque) {
        try {
            logger.debug("Inicio de método ValidadorIngresosTotalesPermitidos.cumpleMaximoIngresos");
            BigDecimal obtenerIngresosHogar = null;
            logger.info("bloque " + bloque);
            logger.info("clasificacion " + clasificacion);
            if(bloque.equals("323-045-2") && clasificacion.equals(ClasificacionEnum.JEFE_HOGAR)) {
                obtenerIngresosHogar = obtenerIngresosHogar(tipoIdentificacion, numeroIdentificacion, idPostulacion);
            }else{
                logger.info("ingresa aqui cumpleMaximoIngresos");
                return true;
            }

            BigDecimal topevalorIngresosHogar = (BigDecimal) entityManager
                    .createNamedQuery(NamedQueriesConstants.OBTENER_TOPE_INGRESOS_HOGAR)
                    .setParameter(ConstantesValidaciones.NOMBRE_PARAMETRO_FOVIS, ParametroFOVISEnum.TOPE_VALOR_INGRESOS_HOGAR.name())
                    .getSingleResult();

            logger.debug("Fin de método ValidadorIngresosTotalesPermitidos.cumpleMaximoIngresos");
            if(obtenerIngresosHogar != null && obtenerIngresosHogar.compareTo(BigDecimal.ZERO) >= 0) {
                if (topevalorIngresosHogar.compareTo(obtenerIngresosHogar) <= 0) {
                    return false;
                } else {
                    return true;
                }
            }else{
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private BigDecimal obtenerIngresosHogar (String tipoIdentificacion, String numeroIdentificacion, Long idPostulacion ){
            logger.info("ingresa a consumir el sp obtenerIngresosHogar" );
        Object ingresosHogar = null;
        try {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_INGRESOS_HOGAR_FOVIS);
            query.setParameter("idPostulacion", BigInteger.valueOf(idPostulacion));
            query.setParameter("tipoIdentificacion", tipoIdentificacion);
            query.setParameter("numeroIdentificacion", numeroIdentificacion);
            ingresosHogar =  query.getSingleResult();
            logger.info("ingresosHogar " + ingresosHogar);

        } catch (Exception e) {
            ingresosHogar = BigDecimal.valueOf(0);
            logger.info(" :: Hubo un error en el SP: " + e);
        }
        logger.info("finaliza el consumo del sp obtenerIngresosHogar" );
        return (BigDecimal) ingresosHogar;
    }

}
