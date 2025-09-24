package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar que la fecha de constitución del empleador se ubica dentro de la vigencia de la Ley 590 de 2000
 * 
 * @author Julián Andrés Muñoz Cardozo <jmunoz@heinsohn.com.co>
 */
public class ValidadorFechaConstitucionLey590 extends ValidadorAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorVigenciaFechaConstitucion.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {

                String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

                /* consultar el empleador por tipo y numero de id */
                Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacion))
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();

                // Se consulta el beneficio LEY 1429
                Beneficio beneficio = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIOS, Beneficio.class)
                        .setParameter("tipoBeneficio",TipoBeneficioEnum.LEY_590).getSingleResult();
                if (beneficio == null) {
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_590,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }

                //fecha de consitucion del empleador
                Date fechaConstitucionEmpleador = empleador.getEmpresa().getFechaConstitucion();
                
                //estado de validación
                Boolean validarFechas = false;
                
                //si no esta vacia
                if(fechaConstitucionEmpleador != null){

                    //fecha de constitucion convertida a formato calendar
                    Calendar fechaConstitucionEmp = Calendar.getInstance();
                    fechaConstitucionEmp.setTime(fechaConstitucionEmpleador);
                    
                    //Obtener consntante con la fecha de ley año 10 de julio de 2000
                    if(beneficio.getFechaVigenciaInicio()!=null){
                        //conversion a formato calendar
                        Calendar fechaLey = Calendar.getInstance();
                        fechaLey.setTime(beneficio.getFechaVigenciaInicio());
                        
                        //fecha actual en formato calendar
                        Calendar fechaActual = Calendar.getInstance();
                        fechaActual = CalendarUtils.formatearFechaSinHora(fechaActual);

                        //si la fecha de constitución del empleador es igual a la fecha de ley
                        //si la fecha de constitucion del empleador esta entre la fecha de ley y la fecha actual    
                        //Si la fecha de constitucion es igual a la fecha actual
                        if(fechaConstitucionEmp.compareTo(fechaLey) >= 0 && fechaConstitucionEmp.compareTo(fechaActual) <= 0){
                            validarFechas = true;
                        }
                    }
                    else{
                        //se setea true porque la FECHA_FIN_LEY_590 no está definida aún. 
                        validarFechas = true;
                        logger.debug("se setea true porque la FECHA_FIN_LEY_590 aún no está definida en la base de datos.");
                    }
                }
                
                // Si la fecha de constitucion esta dentro del rango
                if(validarFechas){
                 
                    logger.debug("Aprobado");
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_590);
                    
                }else{

                    logger.debug("No aprobada- La fecha de constitución del empleador no se ubica dentro de la vigencia de la Ley 590 de 2000");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_590,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);

                }
            }
            else {
                logger.debug("No evaluado- No llegó el mapa con valores");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE, ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_590,
                        TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE, ValidacionCoreEnum.VALIDACION_FECHA_CONSTITUCION_LEY_590,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }



}