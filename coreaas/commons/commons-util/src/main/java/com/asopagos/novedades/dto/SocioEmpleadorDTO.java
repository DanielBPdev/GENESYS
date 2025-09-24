package com.asopagos.novedades.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los campos de un empleador socio.
 * 
 * @author Julián Andrés Muñoz Cardozo <jmunoz@heinsohn.com.co>
 *
 */
public class SocioEmpleadorDTO {

    /**
     * Variable tipoIdentificacionSocio
     */
    private TipoIdentificacionEnum tipoIdentificacionSocio;
    /**
     * Variable numeroIdentificacionSocio
     */
    private String numeroIdentificacionSocio;
    /**
     * Variable primerNombreSocio
     */
    private String primerNombreSocio;
    /**
     * Variable segundoNombreSocio
     */
    private String segundoNombreSocio;
    /**
     * Variable primerApellidoSocio
     */
    private String primerApellidoSocio;
    /**
     * Variable segundoApellidoSocio
     */
    private String segundoApellidoSocio;
    /**
     * Variable tipoIdentificacionConyugeSocio
     */
    private TipoIdentificacionEnum tipoIdentificacionConyugeSocio;
    /**
     * Variable numeroIdentificacionConyugeSocio
     */
    private String numeroIdentificacionConyugeSocio;
    /**
     * Variable primerNombreConyugeSocio
     */
    private String primerNombreConyugeSocio;
    /**
     * Variable segundoNombreConyugeSocio
     */
    private String segundoNombreConyugeSocio;
    /**
     * Variable primerApellidoConyugeSocio
     */
    private String primerApellidoConyugeSocio;
    /**
     * Variable segundoApellidoConyugeSocio
     */
    private String segundoApellidoConyugeSocio;
    /**
     * Variable existenCapitulaciones
     */
    private Boolean existenCapitulaciones;
    
    /**
     * Variable identificadorDocumentoCapitulaciones
     */
    private String identificadorDocumentoCapitulaciones;
    
    /**
     * Identificador del socio
     */
    private Long idSocioEmpleador;
    
    /**
     * Método encargado de retornar el valor del campo tipoIdentificacionSocio
     * 
     * @return el campo tipoIdentificacionSocio
     */
    public TipoIdentificacionEnum getTipoIdentificacionSocio() {
        return tipoIdentificacionSocio;
    }

    /**
     * Método encargado de asignar el valor al campo tipoIdentificacionSocio
     * 
     * @param tipoIdentificacionSocio
     *        tipoIdentificacionSocio a asignar
     */
    public void setTipoIdentificacionSocio(TipoIdentificacionEnum tipoIdentificacionSocio) {
        this.tipoIdentificacionSocio = tipoIdentificacionSocio;
    }

    /**
     * Método encargado de retornar el valor del campo numeroIdentificacionSocio
     * 
     * @return el campo numeroIdentificacionSocio
     */
    public String getNumeroIdentificacionSocio() {
        return numeroIdentificacionSocio;
    }

    /**
     * Método encargado de asignar el valor al campo numeroIdentificacionSocio
     * 
     * @param numeroIdentificacionSocio
     *        numeroIdentificacionSocio a asignar
     */
    public void setNumeroIdentificacionSocio(String numeroIdentificacionSocio) {
        this.numeroIdentificacionSocio = numeroIdentificacionSocio;
    }

    /**
     * Método encargado de retornar el valor del campo primerNombreSocio
     * 
     * @return el campo primerNombreSocio
     */
    public String getPrimerNombreSocio() {
        return primerNombreSocio;
    }

    /**
     * Método encargado de asignar el valor al campo primerNombreSocio
     * 
     * @param primerNombreSocio
     *        primerNombreSocio a asignar
     */
    public void setPrimerNombreSocio(String primerNombreSocio) {
        this.primerNombreSocio = primerNombreSocio;
    }

    /**
     * Método encargado de retornar el valor del campo segundoNombreSocio
     * 
     * @return el campo segundoNombreSocio
     */
    public String getSegundoNombreSocio() {
        return segundoNombreSocio;
    }

    /**
     * Método encargado de asignar el valor al campo segundoNombreSocio
     * 
     * @param segundoNombreSocio
     *        segundoNombreSocio a asignar
     */
    public void setSegundoNombreSocio(String segundoNombreSocio) {
        this.segundoNombreSocio = segundoNombreSocio;
    }

    /**
     * Método encargado de retornar el valor del campo primerApellidoSocio
     * 
     * @return el campo primerApellidoSocio
     */
    public String getPrimerApellidoSocio() {
        return primerApellidoSocio;
    }

    /**
     * Método encargado de asignar el valor al campo primerApellidoSocio
     * 
     * @param primerApellidoSocio
     *        primerApellidoSocio a asignar
     */
    public void setPrimerApellidoSocio(String primerApellidoSocio) {
        this.primerApellidoSocio = primerApellidoSocio;
    }

    /**
     * Método encargado de retornar el valor del campo segundoApellidoSocio
     * 
     * @return el campo segundoApellidoSocio
     */
    public String getSegundoApellidoSocio() {
        return segundoApellidoSocio;
    }

    /**
     * Método encargado de asignar el valor al campo segundoApellidoSocio
     * 
     * @param segundoApellidoSocio
     *        segundoApellidoSocio a asignar
     */
    public void setSegundoApellidoSocio(String segundoApellidoSocio) {
        this.segundoApellidoSocio = segundoApellidoSocio;
    }

    /**
     * Método encargado de retornar el valor del campo
     * tipoIdentificacionConyugeSocio
     * 
     * @return el campo tipoIdentificacionConyugeSocio
     */
    public TipoIdentificacionEnum getTipoIdentificacionConyugeSocio() {
        return tipoIdentificacionConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo
     * tipoIdentificacionConyugeSocio
     * 
     * @param tipoIdentificacionConyugeSocio
     *        tipoIdentificacionConyugeSocio a asignar
     */
    public void setTipoIdentificacionConyugeSocio(TipoIdentificacionEnum tipoIdentificacionConyugeSocio) {
        this.tipoIdentificacionConyugeSocio = tipoIdentificacionConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo
     * numeroIdentificacionConyugeSocio
     * 
     * @return el campo numeroIdentificacionConyugeSocio
     */
    public String getNumeroIdentificacionConyugeSocio() {
        return numeroIdentificacionConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo
     * numeroIdentificacionConyugeSocio
     * 
     * @param numeroIdentificacionConyugeSocio
     *        numeroIdentificacionConyugeSocio a asignar
     */
    public void setNumeroIdentificacionConyugeSocio(String numeroIdentificacionConyugeSocio) {
        this.numeroIdentificacionConyugeSocio = numeroIdentificacionConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo primerNombreConyugeSocio
     * 
     * @return el campo primerNombreConyugeSocio
     */
    public String getPrimerNombreConyugeSocio() {
        return primerNombreConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo primerNombreConyugeSocio
     * 
     * @param primerNombreConyugeSocio
     *        primerNombreConyugeSocio a asignar
     */
    public void setPrimerNombreConyugeSocio(String primerNombreConyugeSocio) {
        this.primerNombreConyugeSocio = primerNombreConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo segundoNombreConyugeSocio
     * 
     * @return el campo segundoNombreConyugeSocio
     */
    public String getSegundoNombreConyugeSocio() {
        return segundoNombreConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo segundoNombreConyugeSocio
     * 
     * @param segundoNombreConyugeSocio
     *        segundoNombreConyugeSocio a asignar
     */
    public void setSegundoNombreConyugeSocio(String segundoNombreConyugeSocio) {
        this.segundoNombreConyugeSocio = segundoNombreConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo
     * primerApellidoConyugeSocio
     * 
     * @return el campo primerApellidoConyugeSocio
     */
    public String getPrimerApellidoConyugeSocio() {
        return primerApellidoConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo primerApellidoConyugeSocio
     * 
     * @param primerApellidoConyugeSocio
     *        primerApellidoConyugeSocio a asignar
     */
    public void setPrimerApellidoConyugeSocio(String primerApellidoConyugeSocio) {
        this.primerApellidoConyugeSocio = primerApellidoConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo
     * segundoApellidoConyugeSocio
     * 
     * @return el campo segundoApellidoConyugeSocio
     */
    public String getSegundoApellidoConyugeSocio() {
        return segundoApellidoConyugeSocio;
    }

    /**
     * Método encargado de asignar el valor al campo segundoApellidoConyugeSocio
     * 
     * @param segundoApellidoConyugeSocio
     *        segundoApellidoConyugeSocio a asignar
     */
    public void setSegundoApellidoConyugeSocio(String segundoApellidoConyugeSocio) {
        this.segundoApellidoConyugeSocio = segundoApellidoConyugeSocio;
    }

    /**
     * Método encargado de retornar el valor del campo existenCapitulaciones
     * 
     * @return el campo existenCapitulaciones
     */
    public Boolean getExistenCapitulaciones() {
        return existenCapitulaciones;
    }

    /**
     * Método encargado de asignar el valor al campo existenCapitulaciones
     * 
     * @param existenCapitulaciones
     *        existenCapitulaciones a asignar
     */
    public void setExistenCapitulaciones(Boolean existenCapitulaciones) {
        this.existenCapitulaciones = existenCapitulaciones;
    }

    /**
     * Método que retorna el valor de la variable identificadorDocumentoCapitulaciones
     * @return the identificadorDocumentoCapitulaciones
     */
    public String getIdentificadorDocumentoCapitulaciones() {
        return identificadorDocumentoCapitulaciones;
    }

    /**
     * Método encargado de asgnar el valor de la variable identificadorDocumentoCapitulaciones
     * @param identificadorDocumentoCapitulaciones identificadorDocumentoCapitulaciones para asignar
     */
    public void setIdentificadorDocumentoCapitulaciones(String identificadorDocumentoCapitulaciones) {
        this.identificadorDocumentoCapitulaciones = identificadorDocumentoCapitulaciones;
    }

    /**
     * @return the idSocioEmpleador
     */
    public Long getIdSocioEmpleador() {
        return idSocioEmpleador;
    }

    /**
     * @param idSocioEmpleador the idSocioEmpleador to set
     */
    public void setIdSocioEmpleador(Long idSocioEmpleador) {
        this.idSocioEmpleador = idSocioEmpleador;
    }

}
