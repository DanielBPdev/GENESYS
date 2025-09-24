package com.asopagos.dto.webservices;

import java.io.Serializable;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import java.math.BigInteger;
import java.util.Date;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;

public class AfiliaTrabajadorDTO {
    
    private Boolean afiliacion;

    private Boolean reingreso;

    private int personas;

    private String municipioUbicacion;

    @NotNull(message = "El campo tipoId es obligatorio.")
    private TipoIdentificacionEnum tipoId;

    @NotNull(message = "El campo numeroId es obligatorio.")
    private String numeroId;

    @NotNull(message = "El campo primerApellido es obligatorio.")
    private String primerApellido;

    private String segundoApellido;

    @NotNull(message = "El campo primerNombre es obligatorio.")
    private String primerNombre;

    private String segundoNombre;

    @NotNull(message = "El campo clasificacion es obligatorio.")
    private ClasificacionEnum clasificacion;

    // @NotNull(message = "El campo medioDePagoAsignado es obligatorio.")
    // private MedioDePagoModeloDTO medioDePagoAsignado;

    // @NotNull(message = "El campo sitioDepago es obligatorio.")
    // private String sitioDepago;

    // @NotNull(message = "El campo tipoCuenta es obligatorio.")
    // private TipoCuentaEnum tipoCuenta;

    // @NotNull(message = "El campo numeroCuenta es obligatorio.")
    // private String numeroCuenta;

    // @NotNull(message = "El campo tipoIdentificacionTitularCuenta es obligatorio.")
    // private TipoIdentificacionEnum tipoIdentificacionTitularCuenta;

    // @NotNull(message = "El campo numeroIdentificacionTitularCuenta es obligatorio.")
    // private String numeroIdentificacionTitularCuenta;

    // @NotNull(message = "El campo nombreTiturlarCuenta es obligatorio.")
    // private String nombreTiturlarCuenta;

    // @NotNull(message = "El campo codigoBanco es obligatorio.")
    // private String codigoBanco;

    @NotNull(message = "El campo fechaNto es obligatorio.")
    private Date fechaNto;

    private GeneroEnum genero;

    @NotNull(message = "El campo municipioDIreccion es obligatorio.")
    private String municipioDIreccion;

    private String barrioDireccion;

    @NotNull(message = "El campo direccion es obligatorio.")
    private String direccion;

    private String telefonoResidencia;

    @NotNull(message = "El campo celularUno es obligatorio.")
    private String celularUno;

    private String celularDos;

    @NotNull(message = "El campo correoPersonal es obligatorio.")
    private String correoPersonal;

    private String correoCorporativo;

    private String profesion;

    private String cargo;

    private String nivel;

    private String civil;

    private String vivienda;

    private String tenencia;

    private String PCud;

    @NotNull(message = "El campo afiliadoPrincipalEsAdmin es obligatorio.")
    private Boolean afiliadoPrincipalEsAdmin;

    @NotNull(message = "El campo fechaRecepcionDocumentos es obligatorio.")
    private Date fechaRecepcionDocumentos;

    public AfiliaTrabajadorDTO() {
    }

    public AfiliaTrabajadorDTO(Boolean afiliacion, Boolean reingreso, int personas, String municipioUbicacion, TipoIdentificacionEnum tipoId, String numeroId, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, ClasificacionEnum clasificacion, Date fechaNto, GeneroEnum genero, String municipioDIreccion, String barrioDireccion, String direccion, String telefonoResidencia, String celularUno, String celularDos, String correoPersonal, String correoCorporativo, BigInteger sueldo, String profesion, String cargo, String nivel, String civil, String vivienda, String tenencia, String PCud, Boolean afiliadoPrincipalEsAdmin, Date fechaRecepcionDocumentos) {
        this.afiliacion = afiliacion;
        this.reingreso = reingreso;
        this.personas = personas;
        this.municipioUbicacion = municipioUbicacion;
        this.tipoId = tipoId;
        this.numeroId = numeroId;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.clasificacion = clasificacion;
        this.fechaNto = fechaNto;
        this.genero = genero;
        this.municipioDIreccion = municipioDIreccion;
        this.barrioDireccion = barrioDireccion;
        this.direccion = direccion;
        this.telefonoResidencia = telefonoResidencia;
        this.celularUno = celularUno;
        this.celularDos = celularDos;
        this.correoPersonal = correoPersonal;
        this.correoCorporativo = correoCorporativo;
        this.profesion = profesion;
        this.cargo = cargo;
        this.nivel = nivel;
        this.civil = civil;
        this.vivienda = vivienda;
        this.tenencia = tenencia;
        this.PCud = PCud;
        this.afiliadoPrincipalEsAdmin = afiliadoPrincipalEsAdmin;
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }

    public Boolean isAfiliacion() {
        return this.afiliacion;
    }

    public Boolean getAfiliacion() {
        return this.afiliacion;
    }

    public void setAfiliacion(Boolean afiliacion) {
        this.afiliacion = afiliacion;
    }

    public Boolean isReingreso() {
        return this.reingreso;
    }

    public Boolean getReingreso() {
        return this.reingreso;
    }

    public void setReingreso(Boolean reingreso) {
        this.reingreso = reingreso;
    }

    public int getPersonas() {
        return this.personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public String getMunicipioUbicacion() {
        return this.municipioUbicacion;
    }

    public void setMunicipioUbicacion(String municipioUbicacion) {
        this.municipioUbicacion = municipioUbicacion;
    }

    public TipoIdentificacionEnum getTipoId() {
        return this.tipoId;
    }

    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumeroId() {
        return this.numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
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

    public ClasificacionEnum getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Date getFechaNto() {
        return this.fechaNto;
    }

    public void setFechaNto(Date fechaNto) {
        this.fechaNto = fechaNto;
    }

    public GeneroEnum getGenero() {
        return this.genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public String getMunicipioDIreccion() {
        return this.municipioDIreccion;
    }

    public void setMunicipioDIreccion(String municipioDIreccion) {
        this.municipioDIreccion = municipioDIreccion;
    }

    public String getBarrioDireccion() {
        return this.barrioDireccion;
    }

    public void setBarrioDireccion(String barrioDireccion) {
        this.barrioDireccion = barrioDireccion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoResidencia() {
        return this.telefonoResidencia;
    }

    public void setTelefonoResidencia(String telefonoResidencia) {
        this.telefonoResidencia = telefonoResidencia;
    }

    public String getCelularUno() {
        return this.celularUno;
    }

    public void setCelularUno(String celularUno) {
        this.celularUno = celularUno;
    }

    public String getCelularDos() {
        return this.celularDos;
    }

    public void setCelularDos(String celularDos) {
        this.celularDos = celularDos;
    }

    public String getCorreoPersonal() {
        return this.correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCorreoCorporativo() {
        return this.correoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        this.correoCorporativo = correoCorporativo;
    }

    public String getProfesion() {
        return this.profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCargo() {
        return this.cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNivel() {
        return this.nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCivil() {
        return this.civil;
    }

    public void setCivil(String civil) {
        this.civil = civil;
    }

    public String getVivienda() {
        return this.vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getTenencia() {
        return this.tenencia;
    }

    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    public String getPCud() {
        return this.PCud;
    }

    public void setPCud(String PCud) {
        this.PCud = PCud;
    }

    public Boolean isAfiliadoPrincipalEsAdmin() {
        return this.afiliadoPrincipalEsAdmin;
    }

    public Boolean getAfiliadoPrincipalEsAdmin() {
        return this.afiliadoPrincipalEsAdmin;
    }

    public void setAfiliadoPrincipalEsAdmin(Boolean afiliadoPrincipalEsAdmin) {
        this.afiliadoPrincipalEsAdmin = afiliadoPrincipalEsAdmin;
    }

    public Date getFechaRecepcionDocumentos() {
        return this.fechaRecepcionDocumentos;
    }

    public void setFechaRecepcionDocumentos(Date fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }

    public AfiliaTrabajadorDTO afiliacion(Boolean afiliacion) {
        setAfiliacion(afiliacion);
        return this;
    }

    public AfiliaTrabajadorDTO reingreso(Boolean reingreso) {
        setReingreso(reingreso);
        return this;
    }

    public AfiliaTrabajadorDTO personas(int personas) {
        setPersonas(personas);
        return this;
    }

    public AfiliaTrabajadorDTO municipioUbicacion(String municipioUbicacion) {
        setMunicipioUbicacion(municipioUbicacion);
        return this;
    }

    public AfiliaTrabajadorDTO tipoId(TipoIdentificacionEnum tipoId) {
        setTipoId(tipoId);
        return this;
    }

    public AfiliaTrabajadorDTO numeroId(String numeroId) {
        setNumeroId(numeroId);
        return this;
    }

    public AfiliaTrabajadorDTO primerApellido(String primerApellido) {
        setPrimerApellido(primerApellido);
        return this;
    }

    public AfiliaTrabajadorDTO segundoApellido(String segundoApellido) {
        setSegundoApellido(segundoApellido);
        return this;
    }

    public AfiliaTrabajadorDTO primerNombre(String primerNombre) {
        setPrimerNombre(primerNombre);
        return this;
    }

    public AfiliaTrabajadorDTO segundoNombre(String segundoNombre) {
        setSegundoNombre(segundoNombre);
        return this;
    }

    public AfiliaTrabajadorDTO clasificacion(ClasificacionEnum clasificacion) {
        setClasificacion(clasificacion);
        return this;
    }

    public AfiliaTrabajadorDTO fechaNto(Date fechaNto) {
        setFechaNto(fechaNto);
        return this;
    }

    public AfiliaTrabajadorDTO genero(GeneroEnum genero) {
        setGenero(genero);
        return this;
    }

    public AfiliaTrabajadorDTO municipioDIreccion(String municipioDIreccion) {
        setMunicipioDIreccion(municipioDIreccion);
        return this;
    }

    public AfiliaTrabajadorDTO barrioDireccion(String barrioDireccion) {
        setBarrioDireccion(barrioDireccion);
        return this;
    }

    public AfiliaTrabajadorDTO direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }

    public AfiliaTrabajadorDTO telefonoResidencia(String telefonoResidencia) {
        setTelefonoResidencia(telefonoResidencia);
        return this;
    }

    public AfiliaTrabajadorDTO celularUno(String celularUno) {
        setCelularUno(celularUno);
        return this;
    }

    public AfiliaTrabajadorDTO celularDos(String celularDos) {
        setCelularDos(celularDos);
        return this;
    }

    public AfiliaTrabajadorDTO correoPersonal(String correoPersonal) {
        setCorreoPersonal(correoPersonal);
        return this;
    }

    public AfiliaTrabajadorDTO correoCorporativo(String correoCorporativo) {
        setCorreoCorporativo(correoCorporativo);
        return this;
    }

    public AfiliaTrabajadorDTO profesion(String profesion) {
        setProfesion(profesion);
        return this;
    }

    public AfiliaTrabajadorDTO cargo(String cargo) {
        setCargo(cargo);
        return this;
    }

    public AfiliaTrabajadorDTO nivel(String nivel) {
        setNivel(nivel);
        return this;
    }

    public AfiliaTrabajadorDTO civil(String civil) {
        setCivil(civil);
        return this;
    }

    public AfiliaTrabajadorDTO vivienda(String vivienda) {
        setVivienda(vivienda);
        return this;
    }

    public AfiliaTrabajadorDTO tenencia(String tenencia) {
        setTenencia(tenencia);
        return this;
    }

    public AfiliaTrabajadorDTO PCud(String PCud) {
        setPCud(PCud);
        return this;
    }

    public AfiliaTrabajadorDTO afiliadoPrincipalEsAdmin(Boolean afiliadoPrincipalEsAdmin) {
        setAfiliadoPrincipalEsAdmin(afiliadoPrincipalEsAdmin);
        return this;
    }

    public AfiliaTrabajadorDTO fechaRecepcionDocumentos(Date fechaRecepcionDocumentos) {
        setFechaRecepcionDocumentos(fechaRecepcionDocumentos);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " afiliacion='" + isAfiliacion() + "'" +
            ", reingreso='" + isReingreso() + "'" +
            ", personas='" + getPersonas() + "'" +
            ", municipioUbicacion='" + getMunicipioUbicacion() + "'" +
            ", tipoId='" + getTipoId() + "'" +
            ", numeroId='" + getNumeroId() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", fechaNto='" + getFechaNto() + "'" +
            ", genero='" + getGenero() + "'" +
            ", municipioDIreccion='" + getMunicipioDIreccion() + "'" +
            ", barrioDireccion='" + getBarrioDireccion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefonoResidencia='" + getTelefonoResidencia() + "'" +
            ", celularUno='" + getCelularUno() + "'" +
            ", celularDos='" + getCelularDos() + "'" +
            ", correoPersonal='" + getCorreoPersonal() + "'" +
            ", correoCorporativo='" + getCorreoCorporativo() + "'" +
            ", profesion='" + getProfesion() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", nivel='" + getNivel() + "'" +
            ", civil='" + getCivil() + "'" +
            ", vivienda='" + getVivienda() + "'" +
            ", tenencia='" + getTenencia() + "'" +
            ", PCud='" + getPCud() + "'" +
            ", afiliadoPrincipalEsAdmin='" + isAfiliadoPrincipalEsAdmin() + "'" +
            ", fechaRecepcionDocumentos='" + getFechaRecepcionDocumentos() + "'" +
            "}";
    }
}
