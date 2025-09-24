package com.asopagos.usuarios.constants;

/**
 * Enumeracion que representa los grupos de usuarios fijos en la aplicacion
 * @author Jorge Leonardo Camargo <jcamargo@heinsohn.com.co>
 */
public enum GruposUsuariosEnum {

    /**
     * Representa el grupo de usuarios administradores del empleador
     */
    ADMINISTRADOR_EMPLEADOR("AdminEmp"),//Antes administrador_empleador

    /**
     * Representa el grupo de usuarios funcionario del empleador
     */
    FUNCIONARIO_EMPLEADOR("funcionario_empleador"),

    /**
     * Representa el grupo de usuarios de la caja de compensacion familiar
     */
    FUNCIONARIO_CCF("funcionario_caja"),

    /**
     * Representa el grupo de usuarios de personas afiliadas
     */
    PERSONA_AFILIADA("afiliado_persona"),

    /**
     * Representa el grupo de usuarios temporales de la caja de compensacion familiar
     */
    TEMPORAL("temporal"),
    
    /**
     * Representa el grupo de usuario que se encuentran retirados de la caja de compensación familiar
     */
    USUARIOS_RETIRADOS("usuarios_retirados"),
    
    /**
     * 82800 Representa el grupo de usuario que se encuentran los usuarios terceros
     */
    TERCERO_PAGADOR("tercero_pagador");

    /**
     * Nombre del atributo
     */
    private String nombre;

    /**
     * Metodo encargado de obtener el valor de la variable nombre
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo encargado de asignar el valor del parametro a la variable nombre
     * @param nombre, parametro de entrada
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo contructor
     * @param nombre, nombre del grupo
     */
    private GruposUsuariosEnum(String nombre) {
        this.nombre = nombre;
    }

}
