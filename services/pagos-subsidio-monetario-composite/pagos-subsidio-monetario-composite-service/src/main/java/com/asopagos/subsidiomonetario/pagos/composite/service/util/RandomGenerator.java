package com.asopagos.subsidiomonetario.pagos.composite.service.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <b>Descripcion:</b> Clase encargada de crear datos aletorios <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class RandomGenerator {
    
    /**
     * Cadena con los caractares alfabeticos
     */
    static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    /**
     * Generador aleatorio de numeros seguro
     */
    static SecureRandom rnd = new SecureRandom();
    
    private RandomGenerator() {}

    /**
     * Metodo que genera un BigDecimal aletorio entre el rango establecido
     * @param min
     * @param max
     * @param escala Numero de decimales del numero requerido
     * @return El numero aleatorio generado
     * @author rarboleda
     */
    public static BigDecimal generarBigDecimalAletorioRango(BigDecimal min, BigDecimal max, Integer escala) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(escala,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * Metodo que genera un BigDecimal aletorio entre el rango establecido
     * @param minimo Entero
     * @param maximo Entero
     * @param escala
     * @return
     */
    public static BigDecimal generateRandomBigDecimalFromRange(int minimo, int maximo, Integer escala) {
        BigDecimal min = new BigDecimal(minimo);
        BigDecimal max = new BigDecimal(maximo);
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));       
        return randomBigDecimal.setScale(escala,BigDecimal.ROUND_HALF_UP);
    }    

    /**
     * Retorna un elemento aletorio de una lista pasada como parametro
     * @param lista: conjunto del cual se va a extraer un elemento 
     * @return Objeto con tipo de retorno extraido de la lista
     * @author rarboleda
     */
    public static Object generarItemAletorioLista(List<Object> lista){        
       return lista.get(new Random().nextInt(lista.size())); 
    }
    
    /**
     * @param numeroItems: Cantidad de elementos del array
     * @param desde: numero de inicio
     * @param hasta: numero fin
     * @return Lista de enteros con los numeros generados
     * @author rarboleda
     */
    public static List<Integer> generarListaAleatoriaNumerosEnteros(int numeroItems, int desde, int hasta){     
        Random r = new Random();        
        List<Integer> numeros = new ArrayList<>(); 
        for(int i = 0;i < numeroItems;i++){
            numeros.add(r.nextInt(hasta-desde) + desde);
        }       
        return numeros;
    }
    
    /**
     * Metodo para obtener un valor aleatorio de una enumeracion
     * @param clazz Enumeracion de la cual se desea obtener el elemento
     * @return Elemento de la enumeracion
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    
    /**
     * Metodo para obtener un numero entero aleatorio entre un rango definido
     * @param desde inicio de rango
     * @param hasta fin de rango
     * @return numero entero en el rango
     * @author rarboleda
     */
    public static Integer generarEnteroAleatorio(int desde, int hasta){
        Random r = new Random();
        return r.nextInt(hasta-desde) + desde;
    } 
    
    /**
     * Metodo para generar una cadena aleatoria
     * @param tamano Tamaño de la cadena que se quiere generar
     * @return Cadena generada aleatoriamente 
     */
    public static String randomString( int tamano ){
       StringBuilder sb = new StringBuilder( tamano );
       for( int i = 0; i < tamano; i++ ) 
          sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
       return sb.toString();
    } 
}
