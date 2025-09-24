package com.asopagos.dto.afiliaciones;

import java.io.Serializable;
import java.math.BigDecimal;

import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
// import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
// import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.BenefeciarioCuotaMonetariaEnumA;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo una 
 * afiliacion masiva de afiliados <br/>
 * <b>Módulo:</b> Asopagos  <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class AfiliadosMasivosDTO implements Serializable {
    private static final long serialVersionUID = 3020065212560358257L;

    private TipoIdentificacionEnum tipoIdentificacionEmpresa;
    
    private String numeroIdentificacionEmpresa;

    private Long idEmpleador;

    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    
    private String numeroIdentificacionAfiliado;
    
    private String primerNombre;
    
    private String segundoNombre;
    
    private String primerApellido;
     
    private String segundoApellido;
    
    private String fechaDeNacimiento;
    
    private GeneroEnum genero; 
    
    private OrientacionSexualEnum orientacionSexual;
    
    private NivelEducativoEnum nivelEscolaridad;
    
    private String codigoOcupacion;
    
    private FactorVulnerabilidadEnum factorVulnerabilidad;
    
    private EstadoCivilEnum estadoCivil;
    
    private PertenenciaEtnicaEnum pertenenciaEtnica;
    
    private String nombrePaisResidencia;
    
    private Integer codigoMucipio;
    
   private Boolean resideRural;

   private Integer codigoMunicipioLabor;

   private BigDecimal salarioMensual;

   private ClaseTrabajadorEnum claseTrabajador;

   private String direccion;

   private String telefonoCelular;

   private String telefonoFijo;

   private String correoElectronico;

   private String categoria;

   private Boolean beneficiarioCuotaMonetario;

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

    public Long getIdEmpleador() {
        return this.idEmpleador;
    }

    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
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

    public OrientacionSexualEnum getOrientacionSexual() {
        return this.orientacionSexual;
    }

    public void setOrientacionSexual(OrientacionSexualEnum orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public NivelEducativoEnum getNivelEscolaridad() {
        return this.nivelEscolaridad;
    }

    public void setNivelEscolaridad(NivelEducativoEnum nivelEscolaridad) {
        this.nivelEscolaridad = nivelEscolaridad;
    }

    public String getCodigoOcupacion() {
        return this.codigoOcupacion;
    }

    public void setCodigoOcupacion(String codigoOcupacion) {
        this.codigoOcupacion = codigoOcupacion;
    }

    public FactorVulnerabilidadEnum getFactorVulnerabilidad() {
        return this.factorVulnerabilidad;
    }

    public void setFactorVulnerabilidad(FactorVulnerabilidadEnum factorVulnerabilidad) {
        this.factorVulnerabilidad = factorVulnerabilidad;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public PertenenciaEtnicaEnum getPertenenciaEtnica() {
        return this.pertenenciaEtnica;
    }

    public void setPertenenciaEtnica(PertenenciaEtnicaEnum pertenenciaEtnica) {
        this.pertenenciaEtnica = pertenenciaEtnica;
    }

    public String getNombrePaisResidencia() {
        return this.nombrePaisResidencia;
    }

    public void setNombrePaisResidencia(String nombrePaisResidencia) {
        this.nombrePaisResidencia = nombrePaisResidencia;
    }

    public Integer getCodigoMucipio() {
        return this.codigoMucipio;
    }

    public void setCodigoMucipio(Integer codigoMucipio) {
        this.codigoMucipio = codigoMucipio;
    }
    public Boolean getResideRural() {
        return this.resideRural;
    }

    public void setResideRural(Boolean resideRural) {
        this.resideRural = resideRural;
    }

    public Integer getCodigoMunicipioLabor() {
        return this.codigoMunicipioLabor;
    }

    public void setCodigoMunicipioLabor(Integer codigoMunicipioLabor) {
        this.codigoMunicipioLabor = codigoMunicipioLabor;
    }

    public BigDecimal getSalarioMensual() {
        return this.salarioMensual;
    }

    public void setSalarioMensual(BigDecimal salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public ClaseTrabajadorEnum getClaseTrabajador() {
        return this.claseTrabajador;
    }

    public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
        this.claseTrabajador = claseTrabajador;
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

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getBeneficiarioCuotaMonetario() {
        return this.beneficiarioCuotaMonetario;
    }

    public void setBeneficiarioCuotaMonetario(Boolean beneficiarioCuotaMonetario) {
        this.beneficiarioCuotaMonetario = beneficiarioCuotaMonetario;
    }
     public Long getNoLinea() {
        return this.noLinea;
    }

    public void setNoLinea(Long noLinea) {
        this.noLinea = noLinea;
    }


    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacionEmpresa='" + getTipoIdentificacionEmpresa() + "'" +
            ", numeroIdentificacionEmpresa='" + getNumeroIdentificacionEmpresa() + "'" +
            ", idEmpleador='" + getIdEmpleador() + "'" +
            ", tipoIdentificacionAfiliado='" + getTipoIdentificacionAfiliado() + "'" +
            ", numeroIdentificacionAfiliado='" + getNumeroIdentificacionAfiliado() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaDeNacimiento='" + getFechaDeNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", orientacionSexual='" + getOrientacionSexual() + "'" +
            ", nivelEscolaridad='" + getNivelEscolaridad() + "'" +
            ", codigoOcupacion='" + getCodigoOcupacion() + "'" +
            ", factorVulnerabilidad='" + getFactorVulnerabilidad() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", pertenenciaEtnica='" + getPertenenciaEtnica() + "'" +
            ", nombrePaisResidencia='" + getNombrePaisResidencia() + "'" +
            ", codigoMucipio='" + getCodigoMucipio() + "'" +
            ", codigoMunicipioLabor='" + getCodigoMunicipioLabor() + "'" +
            ", salarioMensual='" + getSalarioMensual() + "'" +
            ", claseTrabajador='" + getClaseTrabajador() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefonoCelular='" + getTelefonoCelular() + "'" +
            ", telefonoFijo='" + getTelefonoFijo() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", NoLinea='" + getNoLinea() + "'" +
            "}";
    }

    
}