package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class InfoAfiliadoOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de identificación del afiliado
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación del afiliado
    */
    private String identificacion;
    
    /**
    * Nombre completo del afiliado
    */
    private String nombreCompleto;
    
    /**
    * Fecha de nacimiento del afiliado
    */
    private String fechaNacimiento;
    
    /**
    * Categoría del afiliado
    */
    private CategoriaPersonaEnum categoria;
    
    /**
    * Estado de afiliación del afiliado (Activo - Inactivo)
    */
    private EstadoAfiliadoEnum estadoAfiliacion;
    
    /**
    * Tipo de afiliado (Dependiente, Independiente, Pensionado)
    */
    private TipoAfiliadoEnum tipoAfiliado;
    
    /**
     * 
     */
    public InfoAfiliadoOutDTO() {
    }

    
    
    public InfoAfiliadoOutDTO(String tipoID, String identificacion, String nombreCompleto,
			String fechaNacimiento, String categoria, String estadoAfiliacion,
			String tipoAfiliado) {
		this.tipoID = tipoID != null ? TipoIdentificacionEnum.valueOf(tipoID) : null;
		this.identificacion = identificacion;
		this.nombreCompleto = nombreCompleto;
		this.fechaNacimiento = fechaNacimiento;
		this.categoria = categoria != null ? CategoriaPersonaEnum.valueOf(categoria) : null;
		this.estadoAfiliacion = estadoAfiliacion != null ? EstadoAfiliadoEnum.valueOf(estadoAfiliacion) : null;
		this.tipoAfiliado = tipoAfiliado != null ? TipoAfiliadoEnum.valueOf(tipoAfiliado) : null;
	}



	/**
     * @param tipoID
     * @param identificacion
     * @param nombreCompleto
     * @param fechaNacimiento
     * @param estadoAfiliacion
     */
    public InfoAfiliadoOutDTO(TipoIdentificacionEnum tipoID, String identificacion, String nombreCompleto, Date fechaNacimiento,
            EstadoAfiliadoEnum estadoAfiliacion) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        
        if(fechaNacimiento != null){
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	this.fechaNacimiento = df.format(fechaNacimiento);
        }
        
        this.estadoAfiliacion = estadoAfiliacion;
    }
    
    /**
     * @param tipoID
     * @param identificacion
     * @param nombreCompleto
     * @param fechaNacimiento
     * @param estadoAfiliacion
     * @param tipoAfiliado
     */
    public InfoAfiliadoOutDTO(TipoIdentificacionEnum tipoID, String identificacion, String nombreCompleto, Date fechaNacimiento,
            EstadoAfiliadoEnum estadoAfiliacion, TipoAfiliadoEnum tipoAfiliado) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        
        if(fechaNacimiento != null){
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	this.fechaNacimiento = df.format(fechaNacimiento);
        }
        
        this.estadoAfiliacion = estadoAfiliacion;
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the categoria
     */
    public CategoriaPersonaEnum getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaPersonaEnum categoria) {
        this.categoria = categoria;
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
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }
}
