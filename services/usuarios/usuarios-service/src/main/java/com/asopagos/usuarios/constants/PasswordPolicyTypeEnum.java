package com.asopagos.usuarios.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

public enum PasswordPolicyTypeEnum {

    FORCE_EXPIRED_PASSWORD_CHANGE("Expire password","forceExpiredPasswordChange"),
    HASH_ITERATIONS("Hash iterations","hashIterations"),
    SPECIAL_CHARS("Special chars","specialChars"),
    PASSWORD_HISTORY("Password history","passwordHistory"),
    UPPER_CASE("Upper case","upperCase"),
    LOWER_CASE ("Lowe case","lowerCase "),
    LENGTH("length","length"),
    REGEX_PATTERN("Regex pattern","regexPattern"),
    DIGITS("Digits","digits"),
    NOT_USERNAME("Not username","notUsername"),
    HASH_ALGORITHM("Hash algorithm","hashAlgorithm");
    
    
    
    /**
     * Descripción del atributo
     */
    private String descripcion;

    private String nombre;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *        the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private PasswordPolicyTypeEnum(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
}
