package com.asopagos.dto.webservices;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;
import com.asopagos.enumeraciones.fovis.MedioPagoEnum;

import javax.validation.constraints.*;

import com.asopagos.enumeraciones.core.*;
import com.asopagos.enumeraciones.personas.*;

public class AfiliarPersonaACargoDTO implements Serializable{

    @NotNull(message = "El campo tipoTrabajador es obligatorio.")
    private String tipoTrabajador;

    @NotNull(message = "El campo nroTrabajador es obligatorio.")
    private TipoIdentificacionEnum tipoDtoTrabajador;

    @NotNull(message = "El campo nroTrabajador es obligatorio.")
    private Long nroTrabajador;

    @NotNull(message = "El campo tipoId es obligatorio.")
    private TipoIdentificacionEnum tipoId;

    @NotNull(message = "El campo nroId es obligatorio.")
    private Long nroId;

    @NotNull(message = "El campo primerApellido es obligatorio.")
    private String primerApellido;

    private String segundoApellido;

    @NotNull(message = "El campo primerNombre es obligatorio.")
    private String primerNombre;

    String segundoNombre;

    @NotNull(message = "El campo fechaNto es obligatorio.")
    private String fechaNto;

    @NotNull(message = "El campo genero es obligatorio.")
    private GeneroEnum genero;

    private String empresa;

    @NotNull(message = "El campo nitEmpresa es obligatorio.")
    private Long nitEmpresa;

    private String rCuota;
    private String nombreCajas;
    private Long sueldo;

    @NotNull(message = "El campo discapacidad es obligatorio.")
    private Boolean discapacidad;

    private Integer gradoEscolar;
    private String pCud;

    //Obligatorios Genesys
    
    Boolean conyugeLabora;

    @NotNull(message = "El campo parentesco es obligatorio")
    ClasificacionEnum parentesco;

    
    String fechaInicioUnionConElAfiliadoPrincipal;

    String fechaRegistroActivacionDelBeneficiario;

    String estadoTarjeta;

    @NotNull(message = "El campo bancoDondeEstaRegistradaLaCuenta es obligatorio")
    String bancoDondeEstaRegistradaLaCuenta;

    @NotNull(message = "El campo tipoCuenta es obligatorio")
    TipoCuentaEnum tipoCuenta;

    @NotNull(message = "El campo numeroCuenta es obligatorio")
    String numeroCuenta;

    @NotNull(message = "El campo grupoFamiliarInembargable es obligatorio")
    Boolean grupoFamiliarInembargable;

    @NotNull(message = "El campo tipoIdentificacionAdminSubsidio es obligatorio")
    TipoIdentificacionEnum tipoIdentificacionAdminSubsidio;

    @NotNull(message = "El campo numeroIdentificacionAdminSubsidio es obligatorio")
    String numeroIdentificacionAdminSubsidio;

    @NotNull(message = "El campo primerNombreAdminSubsidio es obligatorio")
    String primerNombreAdminSubsidio;

    @NotNull(message = "El campo primerApellidoAdminSubsidio es obligatorio")
    String primerApellidoAdminSubsidio;

    @NotNull(message = "El campo relacionConGrupoFamiliar es obligatorio")
    ClasificacionEnum relacionConGrupoFamiliar;

    @NotNull(message = "El campo sitioPago es obligatorio")
    String sitioPago;

    MedioPagoEnum medioDePagoAsignado;

    String deseaRecibirPagoPorTerceroPagador;

    @NotNull(message = "El campo fechaRecepcionDocumentos es obligatorio")
    String fechaRecepcionDocumentos;

    MedioPagoEnum medioDePagoActual;

    EstadoCivilEnum estadoCivil;

    @NotNull(message = "El campo afiliadoPrincipalEsElMismoAdministradorDelSubsidio es obligatorio")
    Boolean afiliadoPrincipalEsElMismoAdministradorDelSubsidio;

    @NotNull(message = "El campo nombreTitularCuenta es obligatorio")
    String nombreTitularCuenta;

    @NotNull(message = "El campo numeroIdentificacionTitularCuenta es obligatorio")
    String numeroIdentificacionTitularCuenta;

    @NotNull(message = "El campo tipoIdentificacionTitularCuenta es obligatorio")
    TipoIdentificacionEnum tipoIdentificacionTitularCuenta;




    public AfiliarPersonaACargoDTO(){
    }

    public AfiliarPersonaACargoDTO(
            String tipoTrabajador,
            TipoIdentificacionEnum tipoDtoTrabajador,
            Long nroTrabajador,
            TipoIdentificacionEnum tipoId,
            Long nroId,
            String primerApellido,
            String segundoApellido,
            String primerNombre,
            String segundoNombre,
            String fechaNto,
            GeneroEnum genero,
            String empresa,
            Long nitEmpresa,
            String rCuota,
            String nombreCajas,
            Long sueldo,
            Boolean discapacidad,
            Integer gradoEscolar,
            String pCud,
            Boolean conyugeLabora,
            ClasificacionEnum parentesco,
            String fechaInicioUnionConElAfiliadoPrincipal,
            String fechaRegistroActivacionDelBeneficiario,
            String estadoTarjeta,
            String bancoDondeEstaRegistradaLaCuenta,
            TipoCuentaEnum tipoCuenta,
            String numeroCuenta,
            Boolean grupoFamiliarInembargable,
            TipoIdentificacionEnum tipoIdentificacionAdminSubsidio,
            String numeroIdentificacionAdminSubsidio,
            String primerNombreAdminSubsidio,
            String primerApellidoAdminSubsidio,
            ClasificacionEnum relacionConGrupoFamiliar,
            String sitioPago,
            MedioPagoEnum medioDePagoAsignado,
            String deseaRecibirPagoPorTerceroPagador,
            String fechaRecepcionDocumentos,
            MedioPagoEnum medioDePagoActual,
            EstadoCivilEnum estadoCivil,
            Boolean afiliadoPrincipalEsElMismoAdministradorDelSubsidio,
            String nombreTitularCuenta,
            String numeroIdentificacionTitularCuenta,
            TipoIdentificacionEnum tipoIdentificacionTitularCuenta
    ) {
        this.tipoTrabajador = tipoTrabajador;
        this.tipoDtoTrabajador = tipoDtoTrabajador;
        this.nroTrabajador = nroTrabajador;
        this.tipoId = tipoId;
        this.nroId = nroId;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.fechaNto = fechaNto;
        this.genero = genero;
        this.empresa = empresa;
        this.nitEmpresa = nitEmpresa;
        this.rCuota = rCuota;
        this.nombreCajas = nombreCajas;
        this.sueldo = sueldo;
        this.parentesco = parentesco;
        this.discapacidad = discapacidad;
        this.gradoEscolar = gradoEscolar;
        this.pCud = pCud;
        this.conyugeLabora = conyugeLabora;
        this.parentesco = parentesco;
        this.fechaInicioUnionConElAfiliadoPrincipal = fechaInicioUnionConElAfiliadoPrincipal;
        this.fechaRegistroActivacionDelBeneficiario = fechaRegistroActivacionDelBeneficiario;
        this.estadoTarjeta = estadoTarjeta;
        this.bancoDondeEstaRegistradaLaCuenta = bancoDondeEstaRegistradaLaCuenta;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.grupoFamiliarInembargable = grupoFamiliarInembargable;
        this.tipoIdentificacionAdminSubsidio = tipoIdentificacionAdminSubsidio;
        this.numeroIdentificacionAdminSubsidio = numeroIdentificacionAdminSubsidio;
        this.primerNombreAdminSubsidio = primerNombreAdminSubsidio;
        this.primerApellidoAdminSubsidio = primerApellidoAdminSubsidio;
        this.relacionConGrupoFamiliar = relacionConGrupoFamiliar;
        this.sitioPago = sitioPago;
        this.medioDePagoAsignado = medioDePagoAsignado;
        this.deseaRecibirPagoPorTerceroPagador = deseaRecibirPagoPorTerceroPagador;
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
        this.medioDePagoActual = medioDePagoActual;
        this.estadoCivil = estadoCivil;
        this.afiliadoPrincipalEsElMismoAdministradorDelSubsidio = afiliadoPrincipalEsElMismoAdministradorDelSubsidio;
        this.nombreTitularCuenta = nombreTitularCuenta;
        this.numeroIdentificacionTitularCuenta = numeroIdentificacionTitularCuenta;
        this.tipoIdentificacionTitularCuenta = tipoIdentificacionTitularCuenta;
    }

    

    // Getters y Setters
    public String getTipoTrabajador() {
    return tipoTrabajador;
    }

    public void setTipoTrabajador(String tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }
    public TipoIdentificacionEnum getTipoDtoTrabajador() {
    return tipoDtoTrabajador;
    }

    public void setTipoDtoTrabajador(TipoIdentificacionEnum tipoDtoTrabajador) {
        this.tipoDtoTrabajador = tipoDtoTrabajador;
    }

    public Long getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(Long nroTrabajador) {
        this.nroTrabajador = nroTrabajador;
    }

    public TipoIdentificacionEnum getTipoId() {
        return tipoId;
    }

    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    public Long getNroId() {
        return nroId;
    }

    public void setNroId(Long nroId) {
        this.nroId = nroId;
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

    public String getFechaNto() {
        return fechaNto;
    }

    public void setFechaNto(String fechaNto) {
        this.fechaNto = fechaNto;
    }

    public GeneroEnum getGenero() {
        return genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Long getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(Long nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getRCuota() {
        return rCuota;
    }

    public void setRCuota(String rCuota) {
        this.rCuota = rCuota;
    }

    public String getNombreCajas() {
        return nombreCajas;
    }

    public void setNombreCajas(String nombreCajas) {
        this.nombreCajas = nombreCajas;
    }

    public Long getSueldo() {
        return sueldo;
    }

    public void setSueldo(Long sueldo) {
        this.sueldo = sueldo;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public Integer getGradoEscolar() {
        return gradoEscolar;
    }

    public void setGradoEscolar(Integer gradoEscolar) {
        this.gradoEscolar = gradoEscolar;
    }

    public String getPCud() {
        return pCud;
    }

    public void setPCud(String pCud) {
        this.pCud = pCud;
    }

    public Boolean getConyugeLabora() {
        return conyugeLabora;
    }

    public void setConyugeLabora(Boolean conyugeLabora) {
        this.conyugeLabora = conyugeLabora;
    }

    public ClasificacionEnum getParentesco() {
        return parentesco;
    }

    public void setParentesco(ClasificacionEnum parentesco) {
        this.parentesco = parentesco;
    }

    public String getFechaInicioUnionConElAfiliadoPrincipal() {
        return fechaInicioUnionConElAfiliadoPrincipal;
    }

    public void setFechaInicioUnionConElAfiliadoPrincipal(String fechaInicioUnionConElAfiliadoPrincipal) {
        this.fechaInicioUnionConElAfiliadoPrincipal = fechaInicioUnionConElAfiliadoPrincipal;
    }

    public String getFechaRegistroActivacionDelBeneficiario() {
        return fechaRegistroActivacionDelBeneficiario;
    }

    public void setFechaRegistroActivacionDelBeneficiario(String fechaRegistroActivacionDelBeneficiario) {
        this.fechaRegistroActivacionDelBeneficiario = fechaRegistroActivacionDelBeneficiario;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(String estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public String getBancoDondeEstaRegistradaLaCuenta() {
        return bancoDondeEstaRegistradaLaCuenta;
    }

    public void setBancoDondeEstaRegistradaLaCuenta(String bancoDondeEstaRegistradaLaCuenta) {
        this.bancoDondeEstaRegistradaLaCuenta = bancoDondeEstaRegistradaLaCuenta;
    }

    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Boolean getGrupoFamiliarInembargable() {
        return grupoFamiliarInembargable;
    }

    public void setGrupoFamiliarInembargable(Boolean grupoFamiliarInembargable) {
        this.grupoFamiliarInembargable = grupoFamiliarInembargable;
    }

    public TipoIdentificacionEnum getTipoIdentificacionAdminSubsidio() {
        return tipoIdentificacionAdminSubsidio;
    }

    public void setTipoIdentificacionAdminSubsidio(TipoIdentificacionEnum tipoIdentificacionAdminSubsidio) {
        this.tipoIdentificacionAdminSubsidio = tipoIdentificacionAdminSubsidio;
    }

    public String getNumeroIdentificacionAdminSubsidio() {
        return numeroIdentificacionAdminSubsidio;
    }

    public void setNumeroIdentificacionAdminSubsidio(String numeroIdentificacionAdminSubsidio) {
        this.numeroIdentificacionAdminSubsidio = numeroIdentificacionAdminSubsidio;
    }

    public String getPrimerNombreAdminSubsidio() {
        return primerNombreAdminSubsidio;
    }

    public void setPrimerNombreAdminSubsidio(String primerNombreAdminSubsidio) {
        this.primerNombreAdminSubsidio = primerNombreAdminSubsidio;
    }

    public String getPrimerApellidoAdminSubsidio() {
        return primerApellidoAdminSubsidio;
    }

    public void setPrimerApellidoAdminSubsidio(String primerApellidoAdminSubsidio) {
        this.primerApellidoAdminSubsidio = primerApellidoAdminSubsidio;
    }

    public ClasificacionEnum getRelacionConGrupoFamiliar() {
        return relacionConGrupoFamiliar;
    }

    public void setRelacionConGrupoFamiliar(ClasificacionEnum relacionConGrupoFamiliar) {
        this.relacionConGrupoFamiliar = relacionConGrupoFamiliar;
    }

    public String getSitioPago() {
        return sitioPago;
    }

    public void setSitioPago(String sitioPago) {
        this.sitioPago = sitioPago;
    }

    public MedioPagoEnum getMedioDePagoAsignado() {
        return medioDePagoAsignado;
    }

    public void setMedioDePagoAsignado(MedioPagoEnum medioDePagoAsignado) {
        this.medioDePagoAsignado = medioDePagoAsignado;
    }

    public String getDeseaRecibirPagoPorTerceroPagador() {
        return deseaRecibirPagoPorTerceroPagador;
    }

    public void setDeseaRecibirPagoPorTerceroPagador(String deseaRecibirPagoPorTerceroPagador) {
        this.deseaRecibirPagoPorTerceroPagador = deseaRecibirPagoPorTerceroPagador;
    }

    public String getFechaRecepcionDocumentos() {
        return fechaRecepcionDocumentos;
    }

    public void setFechaRecepcionDocumentos(String fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
    }

    public MedioPagoEnum getMedioDePagoActual() {
        return medioDePagoActual;
    }

    public void setMedioDePagoActual(MedioPagoEnum medioDePagoActual) {
        this.medioDePagoActual = medioDePagoActual;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Boolean getAfiliadoPrincipalEsElMismoAdministradorDelSubsidio() {
        return afiliadoPrincipalEsElMismoAdministradorDelSubsidio;
    }

    public void setAfiliadoPrincipalEsElMismoAdministradorDelSubsidio(Boolean afiliadoPrincipalEsElMismoAdministradorDelSubsidio) {
        this.afiliadoPrincipalEsElMismoAdministradorDelSubsidio = afiliadoPrincipalEsElMismoAdministradorDelSubsidio;
    }

    public String getNombreTitularCuenta() {
        return nombreTitularCuenta;
    }

    public void setNombreTitularCuenta(String nombreTitularCuenta) {
        this.nombreTitularCuenta = nombreTitularCuenta;
    }

    public String getNumeroIdentificacionTitularCuenta() {
        return numeroIdentificacionTitularCuenta;
    }

    public void setNumeroIdentificacionTitularCuenta(String numeroIdentificacionTitularCuenta) {
        this.numeroIdentificacionTitularCuenta = numeroIdentificacionTitularCuenta;
    }

    public TipoIdentificacionEnum getTipoIdentificacionTitularCuenta() {
        return tipoIdentificacionTitularCuenta;
    }

    public void setTipoIdentificacionTitularCuenta(TipoIdentificacionEnum tipoIdentificacionTitularCuenta) {
        this.tipoIdentificacionTitularCuenta = tipoIdentificacionTitularCuenta;
    }
}
