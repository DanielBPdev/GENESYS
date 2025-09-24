package com.asopagos.util;

import com.asopagos.enumeraciones.fovis.OperadorComparacionEnum;

/**
 * <b>Descripción:</b> Clase que contiene métodos utilitarios para los rangos de tope smmlv
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class RangoTopeUtils {

    /**
     * Verifica si el valor uno cumple con el operador contra el valor dos
     * @param operador
     *        Operador del rango a revisar
     * @param valorUno
     *        Valor base de comparación
     * @param valorDos
     *        Valor para la comparación
     * @return TRUE si el valor uno cumple el operador contra el valordos
     */
    public static Boolean verificarRangoOperador(OperadorComparacionEnum operador, Double valorUno, Double valorDos) {
        Boolean result = Boolean.FALSE;
        switch (operador) {
            case MAYOR:
                result = (valorUno > valorDos);
                break;
            case MAYOR_O_IGUAL:
                result = (valorUno >= valorDos);
                break;
            case MENOR:
                result = (valorUno < valorDos);
                break;
            case MENOR_O_IGUAL:
                result = (valorUno <= valorDos);
                break;
            default:
                break;
        }
        return result;
    }
}
