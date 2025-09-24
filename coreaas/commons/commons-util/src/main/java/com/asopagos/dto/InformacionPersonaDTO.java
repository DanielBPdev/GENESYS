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
public class InformacionPersonaDTO extends InformacionPersonaEmpleadorDTO implements Serializable {

    private String tipoAfiliacion;

    private ClaseIndependienteEnum claseIndependiente;

    private ClaseTrabajadorEnum claseTrabajador;

    private String afiliadoPrincipal;

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String nombreCompleto;

    private String fechaNacimiento;

    private Integer edad;

    private String fechaFallecimiento;

    private EstadoCivilEnum estadoCivil;

    private GeneroEnum genero;

    private Integer cantidadHijos;

    private String condicionInvalidez;
    
    private String direccionRecidencia;

    private String habitaCasaPropia;

    private Long codigoMunicipio; 
    
    private String nombreMunicipio;

    private Long codigoDepartamento;
    
    private String nombreDepartamento;

    private String codigoPostal;

	private String indicativoYTelFijo;

	private String celular;

	private String correoElectronico;

	private String autorizacionEnvioEmail;

    private String autorizaUsoDatosPersonales;

    private String codigoCCF;

    private CategoriaPersonaEnum categoria;

    private ClasificacionEnum clasificacion;

    private EstadoAfiliadoEnum estadoAfiliado;

	private String fechaAfiliacionCCF;

    private String ultimoPagoCuotaMonetaria;
    
    private BigDecimal SaldoDescuentoPorCuotaMonetaria;

    private String codigoEntidadDescuento;

    private String nombreEntidadDescuento;

    private String fechaExpedicionDocumento;

    private NivelEducativoEnum nivelEducativo;

    private String gradoAcademico;

    private String nitEmpresaAnterior;

    private String nombreEmpresaAnterior;

    private String fechaIngresoEmpresaAnterior;

    private String fechaRetiroEmpresaAnterior;

    public InformacionPersonaDTO() {
    }

    public InformacionPersonaDTO(String tipoAfiliacion, ClaseIndependienteEnum claseIndependiente, ClaseTrabajadorEnum claseTrabajador, String afiliadoPrincipal, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String nombreCompleto, String fechaNacimiento, Integer edad, String fechaFallecimiento, EstadoCivilEnum estadoCivil, GeneroEnum genero, Integer cantidadHijos, String condicionInvalidez, String direccionRecidencia, String habitaCasaPropia, Long codigoMunicipio, String nombreMunicipio, Long codigoDepartamento, String nombreDepartamento, String codigoPostal, String indicativoYTelFijo, String celular, String correoElectronico, String autorizacionEnvioEmail, String autorizaUsoDatosPersonales, String codigoCCF, CategoriaPersonaEnum categoria, ClasificacionEnum clasificacion, EstadoAfiliadoEnum estadoAfiliado, String fechaAfiliacionCCF, String ultimoPagoCuotaMonetaria, BigDecimal SaldoDescuentoPorCuotaMonetaria, String codigoEntidadDescuento, String nombreEntidadDescuento, String fechaExpedicionDocumento, NivelEducativoEnum nivelEducativo, String gradoAcademico, String nitEmpresaAnterior, String nombreEmpresaAnterior, String fechaIngresoEmpresaAnterior, String fechaRetiroEmpresaAnterior) {
        this.tipoAfiliacion = tipoAfiliacion;
        this.claseIndependiente = claseIndependiente;
        this.claseTrabajador = claseTrabajador;
        this.afiliadoPrincipal = afiliadoPrincipal;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.fechaFallecimiento = fechaFallecimiento;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.cantidadHijos = cantidadHijos;
        this.condicionInvalidez = condicionInvalidez;
        this.direccionRecidencia = direccionRecidencia;
        this.habitaCasaPropia = habitaCasaPropia;
        this.codigoMunicipio = codigoMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.codigoPostal = codigoPostal;
        this.indicativoYTelFijo = indicativoYTelFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
        this.autorizaUsoDatosPersonales = autorizaUsoDatosPersonales;
        this.codigoCCF = codigoCCF;
        this.categoria = categoria;
        this.clasificacion = clasificacion;
        this.estadoAfiliado = estadoAfiliado;
        this.fechaAfiliacionCCF = fechaAfiliacionCCF;
        this.ultimoPagoCuotaMonetaria = ultimoPagoCuotaMonetaria;
        this.SaldoDescuentoPorCuotaMonetaria = SaldoDescuentoPorCuotaMonetaria;
        this.codigoEntidadDescuento = codigoEntidadDescuento;
        this.nombreEntidadDescuento = nombreEntidadDescuento;
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
        this.nivelEducativo = nivelEducativo;
        this.gradoAcademico = gradoAcademico;
        this.nitEmpresaAnterior = nitEmpresaAnterior;
        this.nombreEmpresaAnterior = nombreEmpresaAnterior;
        this.fechaIngresoEmpresaAnterior = fechaIngresoEmpresaAnterior;
        this.fechaRetiroEmpresaAnterior = fechaRetiroEmpresaAnterior;
    }
   

    public String getTipoAfiliacion() {
        return this.tipoAfiliacion;
    }

    public void setTipoAfiliacion(String tipoAfiliacion) {
        this.tipoAfiliacion = tipoAfiliacion;
    }

    public ClaseIndependienteEnum getClaseIndependiente() {
        return this.claseIndependiente;
    }

    public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
    }

    public ClaseTrabajadorEnum getClaseTrabajador() {
        return this.claseTrabajador;
    }

    public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
        this.claseTrabajador = claseTrabajador;
    }

    public String getAfiliadoPrincipal() {
        return this.afiliadoPrincipal;
    }

    public void setAfiliadoPrincipal(String afiliadoPrincipal) {
        this.afiliadoPrincipal = afiliadoPrincipal;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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

    public Integer getCantidadHijos() {
        return this.cantidadHijos;
    }

    public void setCantidadHijos(Integer cantidadHijos) {
        this.cantidadHijos = cantidadHijos;
    }

    public String getCondicionInvalidez() {
        return this.condicionInvalidez;
    }

    public void setCondicionInvalidez(String condicionInvalidez) {
        this.condicionInvalidez = condicionInvalidez;
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

    public String getAutorizacionEnvioEmail() {
        return this.autorizacionEnvioEmail;
    }

    public void setAutorizacionEnvioEmail(String autorizacionEnvioEmail) {
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
    }

    public String getAutorizaUsoDatosPersonales() {
        return this.autorizaUsoDatosPersonales;
    }

    public void setAutorizaUsoDatosPersonales(String autorizaUsoDatosPersonales) {
        this.autorizaUsoDatosPersonales = autorizaUsoDatosPersonales;
    }

    public String getCodigoCCF() {
        return this.codigoCCF;
    }

    public void setCodigoCCF(String codigoCCF) {
        this.codigoCCF = codigoCCF;
    }

    public CategoriaPersonaEnum getCategoria() {
        return this.categoria;
    }

    public void setCategoria(CategoriaPersonaEnum categoria) {
        this.categoria = categoria;
    }

    public ClasificacionEnum getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return this.estadoAfiliado;
    }

    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    public String getFechaAfiliacionCCF() {
        return this.fechaAfiliacionCCF;
    }

    public void setFechaAfiliacionCCF(String fechaAfiliacionCCF) {
        this.fechaAfiliacionCCF = fechaAfiliacionCCF;
    }

    public String getUltimoPagoCuotaMonetaria() {
        return this.ultimoPagoCuotaMonetaria;
    }

    public void setUltimoPagoCuotaMonetaria(String ultimoPagoCuotaMonetaria) {
        this.ultimoPagoCuotaMonetaria = ultimoPagoCuotaMonetaria;
    }

    public BigDecimal getSaldoDescuentoPorCuotaMonetaria() {
        return this.SaldoDescuentoPorCuotaMonetaria;
    }

    public void setSaldoDescuentoPorCuotaMonetaria(BigDecimal SaldoDescuentoPorCuotaMonetaria) {
        this.SaldoDescuentoPorCuotaMonetaria = SaldoDescuentoPorCuotaMonetaria;
    }

    public String getCodigoEntidadDescuento() {
        return this.codigoEntidadDescuento;
    }

    public void setCodigoEntidadDescuento(String codigoEntidadDescuento) {
        this.codigoEntidadDescuento = codigoEntidadDescuento;
    }

    public String getNombreEntidadDescuento() {
        return this.nombreEntidadDescuento;
    }

    public void setNombreEntidadDescuento(String nombreEntidadDescuento) {
        this.nombreEntidadDescuento = nombreEntidadDescuento;
    }

    public String getFechaExpedicionDocumento() {
        return this.fechaExpedicionDocumento;
    }

    public void setFechaExpedicionDocumento(String fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    public NivelEducativoEnum getNivelEducativo() {
        return this.nivelEducativo;
    }

    public void setNivelEducativo(NivelEducativoEnum nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getGradoAcademico() {
        return this.gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }
    public String getHabitaCasaPropia() {
        return this.habitaCasaPropia;
    }

    public void setHabitaCasaPropia(String habitaCasaPropia) {
        this.habitaCasaPropia = habitaCasaPropia;
    }

    public String getNitEmpresaAnterior() {
        return this.nitEmpresaAnterior;
    }

    public void setNitEmpresaAnterior(String nitEmpresaAnterior) {
        this.nitEmpresaAnterior = nitEmpresaAnterior;
    }

    public String getNombreEmpresaAnterior() {
        return this.nombreEmpresaAnterior;
    }

    public void setNombreEmpresaAnterior(String nombreEmpresaAnterior) {
        this.nombreEmpresaAnterior = nombreEmpresaAnterior;
    }

    public String getFechaIngresoEmpresaAnterior() {
        return this.fechaIngresoEmpresaAnterior;
    }

    public void setFechaIngresoEmpresaAnterior(String fechaIngresoEmpresaAnterior) {
        this.fechaIngresoEmpresaAnterior = fechaIngresoEmpresaAnterior;
    }

    public String getFechaRetiroEmpresaAnterior() {
        return this.fechaRetiroEmpresaAnterior;
    }

    public void setFechaRetiroEmpresaAnterior(String fechaRetiroEmpresaAnterior) {
        this.fechaRetiroEmpresaAnterior = fechaRetiroEmpresaAnterior;
    }


    @Override
    public String toString() {
        return "{" +
            " tipoAfiliacion='" + getTipoAfiliacion() + "'" +
            ", claseIndependiente='" + getClaseIndependiente() + "'" +
            ", claseTrabajador='" + getClaseTrabajador() + "'" +
            ", afiliadoPrincipal='" + getAfiliadoPrincipal() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad='" + getEdad() + "'" +
            ", fechaFallecimiento='" + getFechaFallecimiento() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", genero='" + getGenero() + "'" +
            ", cantidadHijos='" + getCantidadHijos() + "'" +
            ", condicionInvalidez='" + getCondicionInvalidez() + "'" +
            ", direccionRecidencia='" + getDireccionRecidencia() + "'" +
            ", habitaCasaPropia='" + getHabitaCasaPropia() + "'" +
            ", codigoMunicipio='" + getCodigoMunicipio() + "'" +
            ", nombreMunicipio='" + getNombreMunicipio() + "'" +
            ", codigoDepartamento='" + getCodigoDepartamento() + "'" +
            ", nombreDepartamento='" + getNombreDepartamento() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", indicativoYTelFijo='" + getIndicativoYTelFijo() + "'" +
            ", celular='" + getCelular() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", autorizacionEnvioEmail='" + getAutorizacionEnvioEmail() + "'" +
            ", autorizaUsoDatosPersonales='" + getAutorizaUsoDatosPersonales() + "'" +
            ", codigoCCF='" + getCodigoCCF() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", estadoAfiliado='" + getEstadoAfiliado() + "'" +
            ", fechaAfiliacionCCF='" + getFechaAfiliacionCCF() + "'" +
            ", ultimoPagoCuotaMonetaria='" + getUltimoPagoCuotaMonetaria() + "'" +
            ", SaldoDescuentoPorCuotaMonetaria='" + getSaldoDescuentoPorCuotaMonetaria() + "'" +
            ", codigoEntidadDescuento='" + getCodigoEntidadDescuento() + "'" +
            ", nombreEntidadDescuento='" + getNombreEntidadDescuento() + "'" +
            ", fechaExpedicionDocumento='" + getFechaExpedicionDocumento() + "'" +
            ", nivelEducativo='" + getNivelEducativo() + "'" +
            ", gradoAcademico='" + getGradoAcademico() + "'" +
            ", nitEmpresaAnterior='" + getNitEmpresaAnterior() + "'" +
            ", nombreEmpresaAnterior='" + getNombreEmpresaAnterior() + "'" +
            ", fechaIngresoEmpresaAnterior='" + getFechaIngresoEmpresaAnterior() + "'" +
            ", fechaRetiroEmpresaAnterior='" + getFechaRetiroEmpresaAnterior() + "'" +
            "}";
    }

}
