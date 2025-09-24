package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class AfiliadoSTDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
    * Dependiente, Independiente, Pensionado, Beneficiario, No afiliado
    */
    private String tipo;
    
    /**
     * Clase del trabajador
     */
    private ClaseTrabajadorEnum claseTrabajador;
    
    /**
     * Clase del independiente
     */
    private ClaseIndependienteEnum claseIndependiente;
    
    /**
    * Tipo de identificación de la persona
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación de la persona
    */
    private String identificacion;
    
    /**
    * Primer nombre de la persona
    */
    private String primerNombre;
    
    /**
    * Segundo nombre de la persona
    */
    private String segundoNombre;
    
    /**
    * Primer apellido de la persona
    */
    private String primerApellido;
    
    /**
    * Segundo apellido de la persona
    */
    private String segundoApellido;
    
    /**
    * Estado de afiliación de la persona (Activo - Inactivo)
    */
    private EstadoAfiliadoEnum estadoAfiliado;
    
    /**
     * listado de categorias
     */
    private List<CategoriaSTDTO> arregloCategorias;
    
    /**
     * 
     */
    public AfiliadoSTDTO() {
    }

    /**
     * @param tipo
     * @param tipoID
     * @param identificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param estadoAfiliado
     * @param arregloCategorias
     */
    public AfiliadoSTDTO(String tipo, TipoIdentificacionEnum tipoID, String identificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, EstadoAfiliadoEnum estadoAfiliado,
            List<CategoriaSTDTO> arregloCategorias) {
        this.tipo = tipo;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.estadoAfiliado = estadoAfiliado;
        this.arregloCategorias = arregloCategorias;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @return the claseTrabajador
     */
    public ClaseTrabajadorEnum getClaseTrabajador() {
        return claseTrabajador;
    }

    /**
     * @param claseTrabajador the claseTrabajador to set
     */
    public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
        this.claseTrabajador = claseTrabajador;
    }

    /**
     * @return the claseIndependiente
     */
    public ClaseIndependienteEnum getClaseIndependiente() {
        return claseIndependiente;
    }

    /**
     * @param claseIndependiente the claseIndependiente to set
     */
    public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
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
     * @return the estadoAfiliado
     */
    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return estadoAfiliado;
    }

    /**
     * @param estadoAfiliado the estadoAfiliado to set
     */
    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    /**
     * @return the arregloCategorias
     */
    public List<CategoriaSTDTO> getArregloCategorias() {
        return arregloCategorias;
    }

    /**
     * @param arregloCategorias the arregloCategorias to set
     */
    public void setArregloCategorias(List<CategoriaSTDTO> arregloCategorias) {
        this.arregloCategorias = arregloCategorias;
    }
}
