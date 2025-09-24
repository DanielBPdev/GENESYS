package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class InfoBasicaPersonaOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de afiliado (Dependiente, Independiente, Pensionado, No Afiliado)
    */
    private TipoAfiliadoEnum tipoAfiliado;

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
     *Nombre completo del afiliado 
     */
    private String nombreCompleto;
    
    /**
    * Fecha de nacimiento del afiliado
    */
    private String fechaNacimiento;

    /**
     * Género de la persona consultada
     */
    private GeneroEnum genero;
    
    /**
    * Código DANE del Departamento de ubicación del afiliado
    */
    private String departamentoCodigo;

    /**
     * Nombre DANE del Departamento de ubicación de la persona
     */
    private String departamentoNombre;
    
    /**
    * Código DANE del Municipio de ubicación del afiliado
    */
    private String municipioCodigo;

    /**
     * Nombre DANE del Municipio de ubicación de la persona
     */
    private String municipioNombre;
    
    /**
    * Dirección física principal del afiliado
    */
    private String direccionResidencia;

    /**
    * Indicativo del teléfono fijo + número fijo del afiliado
    */
    private String telefonoFijo;

    /**
    * Número telefónico del celular del afiliado
    */
    private String celular;

    /**
    * Correo electrónico del afiliado
    */
    private String correoElectronico;

    /**
    * Estado del afiliado (Estado actual solo para el tipo de afiliado consultado) (ACTIVO - INACTIVO)
    */
    private EstadoAfiliadoEnum estado;

    /**
     * 
     */
    public InfoBasicaPersonaOutDTO() {
    }
    
    /**
     * @param tipoAfiliado
     * @param tipoID
     * @param identificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param fechaNacimiento
     * @param genero
     * @param departamentoCodigo
     * @param departamentoNombre
     * @param municipioCodigo
     * @param municipioNombre
     * @param direccionResidencia
     * @param telefonoFijo
     * @param celular
     * @param correoElectronico
     * @param estado
     */
    public InfoBasicaPersonaOutDTO(TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoID, String identificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, String fechaNacimiento, GeneroEnum genero,
            String departamentoCodigo, String departamentoNombre, String municipioCodigo, String municipioNombre,
            String direccionResidencia, String telefonoFijo, String celular, String correoElectronico, EstadoAfiliadoEnum estado) {
        this.tipoAfiliado = tipoAfiliado;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.departamentoCodigo = departamentoCodigo;
        this.departamentoNombre = departamentoNombre;
        this.municipioCodigo = municipioCodigo;
        this.municipioNombre = municipioNombre;
        this.direccionResidencia = direccionResidencia;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.estado = estado;
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
     * @return the genero
     */
    public GeneroEnum getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    /**
     * @return the departamentoCodigo
     */
    public String getDepartamentoCodigo() {
        return departamentoCodigo;
    }

    /**
     * @param departamentoCodigo the departamentoCodigo to set
     */
    public void setDepartamentoCodigo(String departamentoCodigo) {
        this.departamentoCodigo = departamentoCodigo;
    }

    /**
     * @return the departamentoNombre
     */
    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    /**
     * @param departamentoNombre the departamentoNombre to set
     */
    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    /**
     * @return the municipioCodigo
     */
    public String getMunicipioCodigo() {
        return municipioCodigo;
    }

    /**
     * @param municipioCodigo the municipioCodigo to set
     */
    public void setMunicipioCodigo(String municipioCodigo) {
        this.municipioCodigo = municipioCodigo;
    }

    /**
     * @return the municipioNombre
     */
    public String getMunicipioNombre() {
        return municipioNombre;
    }

    /**
     * @param municipioNombre the municipioNombre to set
     */
    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }

    /**
     * @return the direccionResidencia
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * @param direccionResidencia the direccionResidencia to set
     */
    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    /**
     * @return the telefonoFijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * @param telefonoFijo the telefonoFijo to set
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the estado
     */
    public EstadoAfiliadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoAfiliadoEnum estado) {
        this.estado = estado;
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

	public void obtenerNombreCompleto(){
		StringBuilder nombreCompleto = new StringBuilder();
		if(this.primerNombre != null && !this.primerNombre.equals(""))
		{
			nombreCompleto.append(this.primerNombre);
		}
		if(this.segundoNombre != null && !this.segundoNombre.equals(""))
		{
			if(nombreCompleto.toString().equals("")){
				nombreCompleto.append(this.segundoNombre);
			}else{
				nombreCompleto.append(" ");
				nombreCompleto.append(this.segundoNombre);
			}
		}
		if(this.primerApellido != null && !this.primerApellido.equals(""))
		{
			nombreCompleto.append(" ");
			nombreCompleto.append(this.primerApellido);
		}
		if(this.segundoApellido != null && !this.segundoApellido.equals(""))
		{
			nombreCompleto.append(" ");
			nombreCompleto.append(this.segundoApellido);
		}
		this.nombreCompleto = nombreCompleto.toString();
	}
}
