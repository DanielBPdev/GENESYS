package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class GrupoFamiliarOutDTO implements Serializable{

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
    * Primer Nombre del afiliado
    */
    private String primerNombre;

    /**
    * Segundo nombre del afiliado
    */
    private String segundoNombre;

    /**
    * Primer Apellido del afiliado
    */
    private String primerApellido;

    /**
    * Segundo Apellido del afiliado
    */
    private String segundoApellido;

    /**
    * Categoría Actual del afiliado
    */
    private List<CategoriaPersonaEnum> categoria;
    
    /**
    * bit Indica si el afiliado consultador es o no trabajador de una empresa
    */
    private Boolean afiliadoDependiente;

    /**
    * bit Indica si el afiliado consultador es o no persona independiente
    */
    private Boolean afiliadoIndependiente;

    /**
    * bit Indica si el afiliado consultador es o no persona pensionada
    */
    private Boolean afiliadoPensionado;

    /**
    * Clasificación del afiliado
    */
    private ClasificacionEnum clasificacion;

    /**
    * (Activo -Inactivo)
    */
    private EstadoAfiliadoEnum estadoAfiliacion;

    /**
    * (Si está inactivo)
    */
    private String fechaDesafiliacion;

    /**
    * Arreglo Grupo Familiar
    */
    private List<GrupoFamiliarSTDTO> arregloGrupoFamiliar;

    /**
     * 
     */
    public GrupoFamiliarOutDTO() {
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoAfiliado
     * @param estadoAfiliacion
     * @param fechaDesafiliacion
     */
    public GrupoFamiliarOutDTO(TipoIdentificacionEnum tipoID, String identificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, EstadoAfiliadoEnum estadoAfiliacion,
            Date fechaDesafiliacion) {
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.estadoAfiliacion = estadoAfiliacion;
        
        if(fechaDesafiliacion != null){
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	this.fechaDesafiliacion = df.format(fechaDesafiliacion);
        }
    }

    /**
     * @param tipoID
     * @param identificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoAfiliado
     * @param estadoAfiliacion
     * @param fechaDesafiliacion
     */
    public GrupoFamiliarOutDTO(String tipoID, String identificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String estadoAfiliacion,
            Date fechaDesafiliacion) {
        this.tipoID = TipoIdentificacionEnum.valueOf(tipoID);
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.estadoAfiliacion = EstadoAfiliadoEnum.valueOf(estadoAfiliacion);
        
        if(fechaDesafiliacion != null){
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	this.fechaDesafiliacion = df.format(fechaDesafiliacion);
        }
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
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the categoria
     */
    public List<CategoriaPersonaEnum> getCategoria() {
        return categoria;
    }

    
    /**
     * @param categoria
     */
    public void setCategoria(List<CategoriaPersonaEnum> categoria) {
        this.categoria = categoria;
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
     * @return the fechaDesafiliacion
     */
    public String getFechaDesafiliacion() {
        return fechaDesafiliacion;
    }

    /**
     * @param fechaDesafiliacion the fechaDesafiliacion to set
     */
    public void setFechaDesafiliacion(String fechaDesafiliacion) {
        this.fechaDesafiliacion = fechaDesafiliacion;
    }

    /**
     * @return the arregloGrupoFamiliar
     */
    public List<GrupoFamiliarSTDTO> getArregloGrupoFamiliar() {
        return arregloGrupoFamiliar;
    }

    /**
     * @param arregloGrupoFamiliar the arregloGrupoFamiliar to set
     */
    public void setArregloGrupoFamiliar(List<GrupoFamiliarSTDTO> arregloGrupoFamiliar) {
        this.arregloGrupoFamiliar = arregloGrupoFamiliar;
    }

    /**
     * @return the afiliadoDependiente
     */
    public Boolean getAfiliadoDependiente() {
        return afiliadoDependiente;
    }

    /**
     * @param afiliadoDependiente the afiliadoDependiente to set
     */
    public void setAfiliadoDependiente(Boolean afiliadoDependiente) {
        this.afiliadoDependiente = afiliadoDependiente;
    }

    /**
     * @return the afiliadoIndependiente
     */
    public Boolean getAfiliadoIndependiente() {
        return afiliadoIndependiente;
    }

    /**
     * @param afiliadoIndependiente the afiliadoIndependiente to set
     */
    public void setAfiliadoIndependiente(Boolean afiliadoIndependiente) {
        this.afiliadoIndependiente = afiliadoIndependiente;
    }

    /**
     * @return the afiliadoPensionado
     */
    public Boolean getAfiliadoPensionado() {
        return afiliadoPensionado;
    }

    /**
     * @param afiliadoPensionado the afiliadoPensionado to set
     */
    public void setAfiliadoPensionado(Boolean afiliadoPensionado) {
        this.afiliadoPensionado = afiliadoPensionado;
    }
}
