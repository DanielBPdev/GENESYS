package com.asopagos.afiliaciones.personas.web.composite.enums;

/**
 * <b>Descripción:</b> Enum con las posibles acciones de los resultados de la verificacion de solicitud y registro del afiliado
 * independiente o pensionado
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate@heinsohn.com.co</a>
 */
public enum AccionResultadoEnum {

    AFILIAR("Afiliar"),

    ESCALAR("Escalar");

    private String nombre;

    private AccionResultadoEnum(String nombre) {
        this.nombre = nombre;
    }
}
