package com.asopagos.subsidiomonetario.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Descripcion:</b> Clase que proporciona algunas utilidades de fecha
 * propias del modulo de subsidio monetario <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */
public class SubsidioDateUtils {
 
    /**
     * Sonar solicita constructor privado para esconder el constructor
     * public por defecto
     */
    private SubsidioDateUtils(){}
    
    /**
     * Metodo que extrae los componentes de una fecha en long,
     * los meses se retornan desde 1
     * @param fecha Fecha en formato long, viene de pantallas
     * @return
     */
    public static Map<String,Integer> extraerComponentesFecha(Long fecha){
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(fecha));

        return SubsidioDateUtils.extraerComponentesFecha(cal);        
    }
    
    /**
     * Metodo que extrae los componentes de una fecha en date,
     * los meses se retornan desde 1
     * @param fecha Fecha en formato date
     * @return
     */
    public static Map<String,Integer> extraerComponentesFecha(Date fecha){        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        
        return SubsidioDateUtils.extraerComponentesFecha(cal);        
    }
    
    /**
     * Metodo que extrae los componentes de una fecha en date,
     * los meses se retornan desde 1
     * @param fecha Fecha en formato Calendar
     * @return
     */
    public static Map<String,Integer> extraerComponentesFecha(Calendar cal){        
        Map<String, Integer> componentesFecha = new HashMap<>();
        
        componentesFecha.put("segundo", 0);
        componentesFecha.put("minuto", cal.get(Calendar.MINUTE));
        componentesFecha.put("hora", cal.get(Calendar.HOUR_OF_DAY));
        componentesFecha.put("dia", cal.get(Calendar.DAY_OF_MONTH));
        //Meses empiezan en 0 en date pero se guardan desde 1
        componentesFecha.put("mes", cal.get(Calendar.MONTH) + 1);
        componentesFecha.put("anio", cal.get(Calendar.YEAR));
        
        return componentesFecha;        
    }  

    /**
     * Convierte los componentes de una fecha a version String es decir si por ejemplo tenemos
     * hora=5, minuto=6, dia=15, mes=4, anio=2017 en el parametro, el metodo retorna
     * hora=05, minuto=06, dia=15, mes=04, anio=2017.
     * @param componentesFecha Mapa con los componentes de la fecha en int
     * @return mapa con los componentes de la fecha en String
     */
    public static Map<String, String> componentesFechaAsString(Map<String, Integer> componentesFecha) {
        Map<String, String> componentesFechaString = new HashMap<>();

        if (componentesFecha.get("segundo") != null) {
            componentesFechaString.put("segundo", componentesFecha.get("segundo") < 10 ? "0" + componentesFecha.get("segundo")
                    : componentesFecha.get("segundo").toString());
        }
        if (componentesFecha.get("hora") != null) {
            componentesFechaString.put("hora", componentesFecha.get("hora") < 10 ? "0" + componentesFecha.get("hora")
                    : componentesFecha.get("hora").toString());
        }
        if (componentesFecha.get("minuto") != null) {
            componentesFechaString.put("minuto", componentesFecha.get("minuto") < 10 ? "0" + componentesFecha.get("minuto")
                    : componentesFecha.get("minuto").toString());
        }
        if (componentesFecha.get("dia") != null) {
            componentesFechaString.put("dia", componentesFecha.get("dia") < 10 ? "0" + componentesFecha.get("dia")
                    : componentesFecha.get("dia").toString());
        }
        if (componentesFecha.get("mes") != null) {
            componentesFechaString.put("mes", componentesFecha.get("mes") < 10 ? "0" + componentesFecha.get("mes")
                    : componentesFecha.get("mes").toString());
        }
        if (componentesFecha.get("anio") != null) {
            componentesFechaString.put("anio", componentesFecha.get("anio").toString());
        }
        
        return componentesFechaString;
    } 
    
    
    /**
     * Toma una fecha en cualquier dia y la setea al primer dia del mes
     * @param fecha Fecha original
     * @return Fecha en el primer dia
     */
    public static Date ponerFechaEnPrimerDia(Date fecha){
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);        
        cal.set(Calendar.DAY_OF_MONTH, 1);        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();        
    }
    
    /**
     * Toma una fecha y retorna el dia anterior a las 23:59:59
     * @param fecha a la cual se le va a calcular el dia anterior
     * @return 
     */
    public static Date ponerFechaEnDiaAnteriorAntesMedianoche(Date fecha){
        Calendar dia = Calendar.getInstance();
        dia.setTime(fecha);
        dia.add(Calendar.DAY_OF_YEAR, -1);
        dia.set(Calendar.HOUR_OF_DAY, 23);
        dia.set(Calendar.MINUTE, 59);
        dia.set(Calendar.SECOND, 59);
        return dia.getTime();
    }
    
}
