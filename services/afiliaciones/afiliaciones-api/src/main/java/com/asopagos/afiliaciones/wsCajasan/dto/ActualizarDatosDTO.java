package com.asopagos.afiliaciones.wsCajasan.dto;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
public class ActualizarDatosDTO {

    private TipoIdentificacionEnum tipoDcto;
    private Long documento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaNto;
    private String genero;
    private String email;
    private String municipio;
    private String departamento;
    private String direccion;
    private String telefono;
    private String celular;
    private String origen;
    private String usuario;
    private String accion;
    private String pais;
    private OrientacionSexualEnum orientacionSexual;
    private String profesion;
    private String discapacidad;
    private EstadoCivilEnum estadoCivil;
    private FactorVulnerabilidadEnum factorVulnerabilidad;
    private NivelEducativoEnum nivelEscolaridad;
    private PertenenciaEtnicaEnum pertenenciaEtnica;
    private String puebloIndigena;
    private String lugarExpedicion;
    private String resguardo;

    // Constructor vacío
    public ActualizarDatosDTO() {
    }

    // Constructor con todos los campos
    public ActualizarDatosDTO(
            TipoIdentificacionEnum tipoDcto,
            Long documento,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String fechaNto,
            String genero,
            String email,
            String municipio,
            String departamento,
            String direccion,
            String telefono,
            String celular,
            String origen,
            String usuario,
            String accion,
            String pais,
            OrientacionSexualEnum orientacionSexual,
            String profesion,
            String discapacidad,
            EstadoCivilEnum estadoCivil,
            FactorVulnerabilidadEnum factorVulnerabilidad,
            NivelEducativoEnum nivelEscolaridad,
            PertenenciaEtnicaEnum pertenenciaEtnica,
            String puebloIndigena,
            String lugarExpedicion,
            String resguardo
    ) {
        this.tipoDcto = tipoDcto;
        this.documento = documento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNto = fechaNto;
        this.genero = genero;
        this.email = email;
        this.municipio = municipio;
        this.departamento = departamento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.origen = origen;
        this.usuario = usuario;
        this.accion = accion;
        this.pais = pais;
        this.orientacionSexual = orientacionSexual;
        this.profesion = profesion;
        this.discapacidad = discapacidad;
        this.estadoCivil = estadoCivil;
        this.factorVulnerabilidad = factorVulnerabilidad;
        this.nivelEscolaridad = nivelEscolaridad;
        this.pertenenciaEtnica = pertenenciaEtnica;
        this.puebloIndigena = puebloIndigena;
        this.lugarExpedicion = lugarExpedicion;
        this.resguardo = resguardo;
    }

    // Getters y Setters
    public TipoIdentificacionEnum getTipoDcto() {
        return tipoDcto;
    }

    public void setTipoDcto(TipoIdentificacionEnum tipoDcto) {
        this.tipoDcto = tipoDcto;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNto() {
        return fechaNto;
    }

    public void setFechaNto(String fechaNto) {
        this.fechaNto = fechaNto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public OrientacionSexualEnum getOrientacionSexual() {
        return orientacionSexual;
    }

    public void setOrientacionSexual(OrientacionSexualEnum orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public FactorVulnerabilidadEnum getFactorVulnerabilidad() {
        return factorVulnerabilidad;
    }

    public void setFactorVulnerabilidad(FactorVulnerabilidadEnum factorVulnerabilidad) {
        this.factorVulnerabilidad = factorVulnerabilidad;
    }

    public NivelEducativoEnum getNivelEscolaridad() {
        return nivelEscolaridad;
    }

    public void setNivelEscolaridad(NivelEducativoEnum nivelEscolaridad) {
        this.nivelEscolaridad = nivelEscolaridad;
    }

    public PertenenciaEtnicaEnum getPertenenciaEtnica() {
        return pertenenciaEtnica;
    }

    public void setPertenenciaEtnica(PertenenciaEtnicaEnum pertenenciaEtnica) {
        this.pertenenciaEtnica = pertenenciaEtnica;
    }

    public String getPuebloIndigena() {
        return puebloIndigena;
    }

    public void setPuebloIndigena(String puebloIndigena) {
        this.puebloIndigena = puebloIndigena;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getResguardo() {
        return resguardo;
    }

    public void setResguardo(String resguardo) {
        this.resguardo = resguardo;
    }
}
