package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacionBeneficiarioDTO extends InformacionBeneficiarioAfiliadoDTO implements Serializable {

    private String tipoBeneficiario;

    private TipoIdentificacionEnum tipoIdentifiacion;

    private String numeroIdentifacion;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private String nombreCompleto;

    private String fechaNacimiento;

    private Integer edad;

    private String fechaFallecimiento;

    private EstadoCivilEnum estadoCivil;

    private GeneroEnum genero;

    private String direccionRecidencia;
   
    private Long codigoMunicipio; 
    
    private String nombreMunicipio;

    private Long codigoDepartamento;
    
    private String nombreDepartamento;  

    private String codigoPostal;

    private String indicativoYTelFijo;

    private String celular;

	private String correoElectronico;

    private EstadoAfiliadoEnum estadoAfiliado;

    private String fechaRetiro;

    private String motivoDesafiliacion;

    private String ultimpoPagoCuotaMonetaria;
    
    private String condicionInvalidez;

	private String estudianteTrabajoDesarrolloHumano;

    private String debePresentarEscolaridad;

    private String fechaFinVigencia;

	private String estadoCertificadoEscolaridad;

    private String gradoEscolar;

    private String fechaRecepcion;

    public InformacionBeneficiarioDTO() {
    }

    public InformacionBeneficiarioDTO(String tipoBeneficiario, TipoIdentificacionEnum tipoIdentifiacion, String numeroIdentifacion, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String nombreCompleto, String fechaNacimiento, Integer edad, String fechaFallecimiento, EstadoCivilEnum estadoCivil, GeneroEnum genero, String direccionRecidencia, Long codigoMunicipio, String nombreMunicipio, Long codigoDepartamento, String nombreDepartamento, String codigoPostal, String indicativoYTelFijo, String celular, String correoElectronico, EstadoAfiliadoEnum estadoAfiliado, String fechaRetiro, String motivoDesafiliacion, String ultimpoPagoCuotaMonetaria, String condicionInvalidez, String estudianteTrabajoDesarrolloHumano, String debePresentarEscolaridad, String fechaFinVigencia, String estadoCertificadoEscolaridad, String gradoEscolar, String fechaRecepcion) {
        this.tipoBeneficiario = tipoBeneficiario;
        this.tipoIdentifiacion = tipoIdentifiacion;
        this.numeroIdentifacion = numeroIdentifacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.fechaFallecimiento = fechaFallecimiento;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.direccionRecidencia = direccionRecidencia;
        this.codigoMunicipio = codigoMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.codigoPostal = codigoPostal;
        this.indicativoYTelFijo = indicativoYTelFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.estadoAfiliado = estadoAfiliado;
        this.fechaRetiro = fechaRetiro;
        this.motivoDesafiliacion = motivoDesafiliacion;
        this.ultimpoPagoCuotaMonetaria = ultimpoPagoCuotaMonetaria;
        this.condicionInvalidez = condicionInvalidez;
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
        this.debePresentarEscolaridad = debePresentarEscolaridad;
        this.fechaFinVigencia = fechaFinVigencia;
        this.estadoCertificadoEscolaridad = estadoCertificadoEscolaridad;
        this.gradoEscolar = gradoEscolar;
        this.fechaRecepcion = fechaRecepcion;
    }
   

    public String getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public TipoIdentificacionEnum getTipoIdentifiacion() {
        return this.tipoIdentifiacion;
    }

    public void setTipoIdentifiacion(TipoIdentificacionEnum tipoIdentifiacion) {
        this.tipoIdentifiacion = tipoIdentifiacion;
    }

    public String getNumeroIdentifacion() {
        return this.numeroIdentifacion;
    }

    public void setNumeroIdentifacion(String numeroIdentifacion) {
        this.numeroIdentifacion = numeroIdentifacion;
    }

    public String getPrimerNombre() {
        return this.primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getFechaFallecimiento() {
        return this.fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public GeneroEnum getGenero() {
        return this.genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public String getDireccionRecidencia() {
        return this.direccionRecidencia;
    }

    public void setDireccionRecidencia(String direccionRecidencia) {
        this.direccionRecidencia = direccionRecidencia;
    }

    public Long getCodigoMunicipio() {
        return this.codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNombreMunicipio() {
        return this.nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Long getCodigoDepartamento() {
        return this.codigoDepartamento;
    }

    public void setCodigoDepartamento(Long codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return this.nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getIndicativoYTelFijo() {
        return this.indicativoYTelFijo;
    }

    public void setIndicativoYTelFijo(String indicativoYTelFijo) {
        this.indicativoYTelFijo = indicativoYTelFijo;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return this.estadoAfiliado;
    }

    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    public String getFechaRetiro() {
        return this.fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getMotivoDesafiliacion() {
        return this.motivoDesafiliacion;
    }

    public void setMotivoDesafiliacion(String motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    public String getUltimpoPagoCuotaMonetaria() {
        return this.ultimpoPagoCuotaMonetaria;
    }

    public void setUltimpoPagoCuotaMonetaria(String ultimpoPagoCuotaMonetaria) {
        this.ultimpoPagoCuotaMonetaria = ultimpoPagoCuotaMonetaria;
    }

    public String getCondicionInvalidez() {
        return this.condicionInvalidez;
    }

    public void setCondicionInvalidez(String condicionInvalidez) {
        this.condicionInvalidez = condicionInvalidez;
    }

    public String getEstudianteTrabajoDesarrolloHumano() {
        return this.estudianteTrabajoDesarrolloHumano;
    }

    public void setEstudianteTrabajoDesarrolloHumano(String estudianteTrabajoDesarrolloHumano) {
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
    }

    public String getDebePresentarEscolaridad() {
        return this.debePresentarEscolaridad;
    }

    public void setDebePresentarEscolaridad(String debePresentarEscolaridad) {
        this.debePresentarEscolaridad = debePresentarEscolaridad;
    }

    public String getFechaFinVigencia() {
        return this.fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public String getEstadoCertificadoEscolaridad() {
        return this.estadoCertificadoEscolaridad;
    }

    public void setEstadoCertificadoEscolaridad(String estadoCertificadoEscolaridad) {
        this.estadoCertificadoEscolaridad = estadoCertificadoEscolaridad;
    }

    public String getGradoEscolar() {
        return this.gradoEscolar;
    }

    public void setGradoEscolar(String gradoEscolar) {
        this.gradoEscolar = gradoEscolar;
    }

    public String getFechaRecepcion() {
        return this.fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoBeneficiario='" + getTipoBeneficiario() + "'" +
            ", tipoIdentifiacion='" + getTipoIdentifiacion() + "'" +
            ", numeroIdentifacion='" + getNumeroIdentifacion() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad='" + getEdad() + "'" +
            ", fechaFallecimiento='" + getFechaFallecimiento() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", genero='" + getGenero() + "'" +
            ", direccionRecidencia='" + getDireccionRecidencia() + "'" +
            ", codigoMunicipio='" + getCodigoMunicipio() + "'" +
            ", nombreMunicipio='" + getNombreMunicipio() + "'" +
            ", codigoDepartamento='" + getCodigoDepartamento() + "'" +
            ", nombreDepartamento='" + getNombreDepartamento() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", indicativoYTelFijo='" + getIndicativoYTelFijo() + "'" +
            ", celular='" + getCelular() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", estadoAfiliado='" + getEstadoAfiliado() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
            ", ultimpoPagoCuotaMonetaria='" + getUltimpoPagoCuotaMonetaria() + "'" +
            ", condicionInvalidez='" + getCondicionInvalidez() + "'" +
            ", estudianteTrabajoDesarrolloHumano='" + getEstudianteTrabajoDesarrolloHumano() + "'" +
            ", debePresentarEscolaridad='" + getDebePresentarEscolaridad() + "'" +
            ", fechaFinVigencia='" + getFechaFinVigencia() + "'" +
            ", estadoCertificadoEscolaridad='" + getEstadoCertificadoEscolaridad() + "'" +
            ", gradoEscolar='" + getGradoEscolar() + "'" +
            ", fechaRecepcion='" + getFechaRecepcion() + "'" +
            "}";
    }

}
