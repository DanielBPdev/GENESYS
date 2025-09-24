/**
 * 
 */
package com.asopagos.util;

/**
 * Clase que contiene métodos utilitarios para trabajar con las tarjetas
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class TarjetaUtils {

    /**
     * Representa la cantidad de numero visibles que se deben presentar del numero de tarjeta
     */
    public static final Integer CANTIDAD_NUMEROS_VISIBLES = 4;

    /**
     * Representa el caracter que se debe presentar en lugar de los numero de tarjeta
     */
    public static final Character CARACTER_REEMPLAZO = '*';

    /**
     * Metodo que permite truncar el número de tarjeta visible al usuario final
     * @param nroTarjeta
     *        Número de la tarjeta completo
     * @return Numero de tarjeta truncado con caracteres de reemplazo
     */
    public static String truncarNumeroTarjeta(String nroTarjeta) {
        Integer cantReemplazo = nroTarjeta.length() - CANTIDAD_NUMEROS_VISIBLES;
        String nroVisibles = nroTarjeta.substring(cantReemplazo, nroTarjeta.length());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cantReemplazo; i++) {
            builder.append(CARACTER_REEMPLAZO);
        }
        builder.append(nroVisibles);
        return builder.toString();
    }
}
