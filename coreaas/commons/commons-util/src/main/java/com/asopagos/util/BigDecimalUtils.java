package com.asopagos.util;

import java.math.BigDecimal;

/**
 * <b>Descripción:</b> Clase que contiene métodos utilitarios para trabajar con
 * valores BigDecimal
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class BigDecimalUtils {

    /**
     * Método que permite validar si el valor de un dato de tipo BigDecimal es nulo o vacio
     * @param value,
     *        valor a validar
     * @return true en caso de ser nulo o vacio, false caso contrario
     */
    public static boolean isNullOrEmpty(BigDecimal value) {
        return value == null || value.equals("");
    }

    /**
     * Método que compara los valores BigDecimal pasados por parametro
     * @param firts,
     *        primer valor
     * @param second,
     *        segundo valor
     * @return 0 en caso de ser iguales, -1 en caso de que el primero sea menor al segundo, 1 en caso que el primero sea mayor al segundo
     */
    public static int compare(BigDecimal firts, BigDecimal second) {
        return firts.compareTo(second);
    }

}
