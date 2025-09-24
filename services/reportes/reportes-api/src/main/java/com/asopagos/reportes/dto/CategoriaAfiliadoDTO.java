package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class CategoriaAfiliadoDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long afiliado;
    private Long persona;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private TipoAfiliadoEnum tipoCotizante;
    private ClasificacionEnum clasificacion;
    private BigDecimal salario;
    private EstadoAfiliadoEnum estadoAfiliacion;
    private Date fechaFinServicioSinAfiliacion;
    private Boolean aporteEmpleadorNoAfiliado;
    private String categoria;
    
    /**
     * 
     */
    public CategoriaAfiliadoDTO() {
    }
    
    /**
     * @param afiliado
     * @param persona
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoCotizante
     * @param clasificacion
     * @param salario
     * @param estadoAfiliacion
     * @param fechaFinServicioSinAfiliacion
     * @param aporteEmpleadorNoAfiliado
     * @param categoria
     */
    public CategoriaAfiliadoDTO(Long afiliado, Long persona, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            TipoAfiliadoEnum tipoCotizante, ClasificacionEnum clasificacion, BigDecimal salario, EstadoAfiliadoEnum estadoAfiliacion,
            Date fechaFinServicioSinAfiliacion, Boolean aporteEmpleadorNoAfiliado, String categoria) {
        this.afiliado = afiliado;
        this.persona = persona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoCotizante = tipoCotizante;
        this.clasificacion = clasificacion;
        this.salario = salario;
        this.estadoAfiliacion = estadoAfiliacion;
        this.fechaFinServicioSinAfiliacion = fechaFinServicioSinAfiliacion;
        this.aporteEmpleadorNoAfiliado = aporteEmpleadorNoAfiliado;
        this.categoria = categoria;
    }
    
    /**
     * @return the afiliado
     */
    public Long getAfiliado() {
        return afiliado;
    }
    /**
     * @param afiliado the afiliado to set
     */
    public void setAfiliado(Long afiliado) {
        this.afiliado = afiliado;
    }
    /**
     * @return the persona
     */
    public Long getPersona() {
        return persona;
    }
    /**
     * @param persona the persona to set
     */
    public void setPersona(Long persona) {
        this.persona = persona;
    }
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
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
     * @return the tipoCotizante
     */
    public TipoAfiliadoEnum getTipoCotizante() {
        return tipoCotizante;
    }
    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }
    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }
    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }
    /**
     * @return the salario
     */
    public BigDecimal getSalario() {
        return salario;
    }
    /**
     * @param salario the salario to set
     */
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }
    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }
    /**
     * @return the fechaFinServicioSinAfiliacion
     */
    public Date getFechaFinServicioSinAfiliacion() {
        return fechaFinServicioSinAfiliacion;
    }
    /**
     * @param fechaFinServicioSinAfiliacion the fechaFinServicioSinAfiliacion to set
     */
    public void setFechaFinServicioSinAfiliacion(Date fechaFinServicioSinAfiliacion) {
        this.fechaFinServicioSinAfiliacion = fechaFinServicioSinAfiliacion;
    }
    /**
     * @return the aporteEmpleadorNoAfiliado
     */
    public Boolean getAporteEmpleadorNoAfiliado() {
        return aporteEmpleadorNoAfiliado;
    }
    /**
     * @param aporteEmpleadorNoAfiliado the aporteEmpleadorNoAfiliado to set
     */
    public void setAporteEmpleadorNoAfiliado(Boolean aporteEmpleadorNoAfiliado) {
        this.aporteEmpleadorNoAfiliado = aporteEmpleadorNoAfiliado;
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
