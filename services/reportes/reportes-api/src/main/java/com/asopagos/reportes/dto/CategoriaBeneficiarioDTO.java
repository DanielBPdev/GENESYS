package com.asopagos.reportes.dto;

import java.io.Serializable;

public class CategoriaBeneficiarioDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String beneficiarioDetalle;
    private String persona;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String tipoBeneficiario;
    private String estadoBeneficiarioAfiliado;
    private String afiliado;
    private String tipoIdentificacionAfiliado;
    private String numeroIdentificacionAfiliado;
    private String tipoCotizante;
    private String clasificacion;
    private String salario;
    private String estadoAfiliacionAfiliado;
    private String fechaFinServicioSinAfiliacion;
    private String categoria;
    
    /**
     * 
     */
    public CategoriaBeneficiarioDTO() {
    }
    
    /**
     * @param beneficiarioDetalle
     * @param persona
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoBeneficiario
     * @param estadoBeneficiarioAfiliado
     * @param afiliado
     * @param tipoIdentificacionAfiliado
     * @param numeroIdentificacionAfiliado
     * @param tipoCotizante
     * @param clasificacion
     * @param salario
     * @param estadoAfiliacionAfiliado
     * @param fechaFinServicioSinAfiliacion
     * @param categoria
     */
    public CategoriaBeneficiarioDTO(String beneficiarioDetalle, String persona, String tipoIdentificacion, String numeroIdentificacion,
            String tipoBeneficiario, String estadoBeneficiarioAfiliado, String afiliado, String tipoIdentificacionAfiliado,
            String numeroIdentificacionAfiliado, String tipoCotizante, String clasificacion, String salario,
            String estadoAfiliacionAfiliado, String fechaFinServicioSinAfiliacion, String categoria) {
        this.beneficiarioDetalle = beneficiarioDetalle;
        this.persona = persona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoBeneficiario = tipoBeneficiario;
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
        this.afiliado = afiliado;
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
        this.tipoCotizante = tipoCotizante;
        this.clasificacion = clasificacion;
        this.salario = salario;
        this.estadoAfiliacionAfiliado = estadoAfiliacionAfiliado;
        this.fechaFinServicioSinAfiliacion = fechaFinServicioSinAfiliacion;
        this.categoria = categoria;
    }
    /**
     * @return the beneficiarioDetalle
     */
    public String getBeneficiarioDetalle() {
        return beneficiarioDetalle;
    }
    /**
     * @param beneficiarioDetalle the beneficiarioDetalle to set
     */
    public void setBeneficiarioDetalle(String beneficiarioDetalle) {
        this.beneficiarioDetalle = beneficiarioDetalle;
    }
    /**
     * @return the persona
     */
    public String getPersona() {
        return persona;
    }
    /**
     * @param persona the persona to set
     */
    public void setPersona(String persona) {
        this.persona = persona;
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
    /**
     * @return the tipoBeneficiario
     */
    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }
    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }
    /**
     * @return the estadoBeneficiarioAfiliado
     */
    public String getEstadoBeneficiarioAfiliado() {
        return estadoBeneficiarioAfiliado;
    }
    /**
     * @param estadoBeneficiarioAfiliado the estadoBeneficiarioAfiliado to set
     */
    public void setEstadoBeneficiarioAfiliado(String estadoBeneficiarioAfiliado) {
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
    }
    /**
     * @return the afiliado
     */
    public String getAfiliado() {
        return afiliado;
    }
    /**
     * @param afiliado the afiliado to set
     */
    public void setAfiliado(String afiliado) {
        this.afiliado = afiliado;
    }
    /**
     * @return the tipoIdentificacionAfiliado
     */
    public String getTipoIdentificacionAfiliado() {
        return tipoIdentificacionAfiliado;
    }
    /**
     * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
     */
    public void setTipoIdentificacionAfiliado(String tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }
    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }
    /**
     * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }
    /**
     * @return the tipoCotizante
     */
    public String getTipoCotizante() {
        return tipoCotizante;
    }
    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(String tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }
    /**
     * @return the clasificacion
     */
    public String getClasificacion() {
        return clasificacion;
    }
    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    /**
     * @return the salario
     */
    public String getSalario() {
        return salario;
    }
    /**
     * @param salario the salario to set
     */
    public void setSalario(String salario) {
        this.salario = salario;
    }
    /**
     * @return the estadoAfiliacionAfiliado
     */
    public String getEstadoAfiliacionAfiliado() {
        return estadoAfiliacionAfiliado;
    }
    /**
     * @param estadoAfiliacionAfiliado the estadoAfiliacionAfiliado to set
     */
    public void setEstadoAfiliacionAfiliado(String estadoAfiliacionAfiliado) {
        this.estadoAfiliacionAfiliado = estadoAfiliacionAfiliado;
    }
    /**
     * @return the fechaFinServicioSinAfiliacion
     */
    public String getFechaFinServicioSinAfiliacion() {
        return fechaFinServicioSinAfiliacion;
    }
    /**
     * @param fechaFinServicioSinAfiliacion the fechaFinServicioSinAfiliacion to set
     */
    public void setFechaFinServicioSinAfiliacion(String fechaFinServicioSinAfiliacion) {
        this.fechaFinServicioSinAfiliacion = fechaFinServicioSinAfiliacion;
    }
    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }
    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
