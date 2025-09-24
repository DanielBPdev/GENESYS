package com.asopagos.clienteanibol.response.novedad;

import com.google.gson.annotations.SerializedName;

public class DatosNovedadMonetaria {

    @SerializedName("ID_PROCESO")
    private String idProceso;
    
    @SerializedName("ID_ENTIDAD")
    private String idEntidad;
    
    @SerializedName("FECHA_HORA_RESPUESTA")
    private String fechaHoraRespuesta;
    
    @SerializedName("TIPO_IDENTIFICACION")
    private String tipoIdentificacion; // CC
    
    @SerializedName("NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;
    
    @SerializedName("NUMERO_TARJETA")
    private String numeroTarjeta;
    
    @SerializedName("CODIGO_MENSAJE_REGISTRO")
    private String codigoMensajeRegistro;
    
    @SerializedName("SALIDA")
    private String salida;
    

    /**
     * 
     */
    public DatosNovedadMonetaria() {
    }
    
    /**
     * @param idProceso
     * @param idEntidad
     * @param fechaHoraRespuesta
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroTarjeta
     * @param codigoMensajeRegistro
     * @param salida
     */
    public DatosNovedadMonetaria(String idProceso, String idEntidad, String fechaHoraRespuesta, String tipoIdentificacion,
            String numeroIdentificacion, String numeroTarjeta, String codigoMensajeRegistro, String salida) {
        this.idProceso = idProceso;
        this.idEntidad = idEntidad;
        this.fechaHoraRespuesta = fechaHoraRespuesta;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.numeroTarjeta = numeroTarjeta;
        this.codigoMensajeRegistro = codigoMensajeRegistro;
        this.salida = salida;
    }
    
    /**
     * @return the idProceso
     */
    public String getIdProceso() {
        return idProceso;
    }
    /**
     * @param idProceso the idProceso to set
     */
    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }
    /**
     * @return the idEntidad
     */
    public String getIdEntidad() {
        return idEntidad;
    }
    /**
     * @param idEntidad the idEntidad to set
     */
    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }
    /**
     * @return the fechaHoraRespuesta
     */
    public String getFechaHoraRespuesta() {
        return fechaHoraRespuesta;
    }
    /**
     * @param fechaHoraRespuesta the fechaHoraRespuesta to set
     */
    public void setFechaHoraRespuesta(String fechaHoraRespuesta) {
        this.fechaHoraRespuesta = fechaHoraRespuesta;
    }
    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    /**
     * @return the codigoMensajeRegistro
     */
    public String getCodigoMensajeRegistro() {
        return codigoMensajeRegistro;
    }
    /**
     * @param codigoMensajeRegistro the codigoMensajeRegistro to set
     */
    public void setCodigoMensajeRegistro(String codigoMensajeRegistro) {
        this.codigoMensajeRegistro = codigoMensajeRegistro;
    }
    /**
     * @return the salida
     */
    public String getSalida() {
        return salida;
    }
    /**
     * @param salida the salida to set
     */
    public void setSalida(String salida) {
        this.salida = salida;
    }
    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
