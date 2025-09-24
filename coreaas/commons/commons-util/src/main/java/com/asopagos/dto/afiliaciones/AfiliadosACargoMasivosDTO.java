package com.asopagos.dto.afiliaciones;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.CondicionDiscapacidadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.ParentezcoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoCuotaModeradoraEnumA;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import java.math.BigDecimal;



/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo una 
 * afiliacion masiva de benficiarios <br/>
 * <b>Módulo:</b> Asopagos  <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class AfiliadosACargoMasivosDTO implements Serializable {
    private static final long serialVersionUID = 3020065212560358257L;

    private TipoIdentificacionEnum tipoIdentificacionEmpresa;
    
    private String numeroIdentificacionEmpresa;

    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    
    private String numeroIdentificacionAfiliado;
    
     private TipoIdentificacionEnum tipoIdentificacionPersonaCargo;
    
    private String numeroIdentificacionPersonaCargo;
    
    private String primerNombre;
    
    private String segundoNombre;
    
    private String primerApellido;
     
    private String segundoApellido;
    
    private String fechaDeNacimiento;
    
    private GeneroEnum genero; 
    
    private ParentezcoEnumA parentezco;
    
    private String codigoMunicipio;
    
    private String direccion;

    private String telefonoCelular;

    private String telefonoFijo;

    private Boolean conyugeLabora;
    
    private BigDecimal ingresoMensual;
    
    private String fechaInicioUnionAfiliado;

    private Long noLinea;

    

    public TipoIdentificacionEnum getTipoIdentificacionEmpresa() {
        return this.tipoIdentificacionEmpresa;
    }

    public void setTipoIdentificacionEmpresa(TipoIdentificacionEnum tipoIdentificacionEmpresa) {
        this.tipoIdentificacionEmpresa = tipoIdentificacionEmpresa;
    }

    public String getNumeroIdentificacionEmpresa() {
        return this.numeroIdentificacionEmpresa;
    }

    public void setNumeroIdentificacionEmpresa(String numeroIdentificacionEmpresa) {
        this.numeroIdentificacionEmpresa = numeroIdentificacionEmpresa;
    }

    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return this.tipoIdentificacionAfiliado;
    }

    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    public String getNumeroIdentificacionAfiliado() {
        return this.numeroIdentificacionAfiliado;
    }

    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    public TipoIdentificacionEnum getTipoIdentificacionPersonaCargo() {
        return this.tipoIdentificacionPersonaCargo;
    }

    public void setTipoIdentificacionPersonaCargo(TipoIdentificacionEnum tipoIdentificacionPersonaCargo) {
        this.tipoIdentificacionPersonaCargo = tipoIdentificacionPersonaCargo;
    }

    public String getNumeroIdentificacionPersonaCargo() {
        return this.numeroIdentificacionPersonaCargo;
    }

    public void setNumeroIdentificacionPersonaCargo(String numeroIdentificacionPersonaCargo) {
        this.numeroIdentificacionPersonaCargo = numeroIdentificacionPersonaCargo;
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

    public String getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public GeneroEnum getGenero() {
        return this.genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public ParentezcoEnumA getParentezco() {
        return this.parentezco;
    }

    public void setParentezco(ParentezcoEnumA parentezco) {
        this.parentezco = parentezco;
    }

    public String getCodigoMunicipio() {
        return this.codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoCelular() {
        return this.telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }
    public Boolean getConyugeLabora() {
        return this.conyugeLabora;
    }

    public void setConyugeLabora(Boolean conyugeLabora) {
        this.conyugeLabora = conyugeLabora;
    }

    public BigDecimal getIngresoMensual() {
        return this.ingresoMensual;
    }

    public void setIngresoMensual(BigDecimal ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public String getFechaInicioUnionAfiliado() {
        return this.fechaInicioUnionAfiliado;
    }

    public void setFechaInicioUnionAfiliado(String fechaInicioUnionAfiliado) {
        this.fechaInicioUnionAfiliado = fechaInicioUnionAfiliado;
    }
    public Long getNoLinea() {
        return this.noLinea;
    }

    public void setNoLinea(Long noLinea) {
        this.noLinea = noLinea;
    }


   
}
