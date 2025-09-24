package com.asopagos.dto.afiliaciones;

import java.io.Serializable;
import java.lang.Long;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;


/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo
 * una
 * afiliacion masiva de benficiarios <br/>
 * <b>Módulo:</b> Asopagos <br/>
 *
 * @author Diego Nicolas Gonzalez
 */

public class Afiliado25AniosExistenteDTO implements Serializable {
    private static final long serialVersionUID = 3020065212560358257L;

    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificación; 
    private String nombreCompleto;
    private String fechaNacimiento;
    private int edad;
    private EstadoAfiliadoEnum estadoDependiente;
    private EstadoAfiliadoEnum estadoIndependiente; 
    private EstadoAfiliadoEnum estadoPensionado;
    private EstadoAfiliadoEnum estadoBeneficiario; 
    private Long tiempoAfiliacion;
    private int hogarFovis;
    private EstadoHogarEnum estadoHogar;
    private String ultimoPeriodoAfiliadoPrincipal;
    private String ultimoPeriodoBeneficiario;
    private int aportes;
    private String archivo;
    private String pagadorPension;
    private Long valorMesadaPensional;
    private String fechaRecepcionDocumentos;
    private Long noLinea;

    // TOD 08: Crear nuevo atributo tipo Long noLinea
    // TOD 09}. Crear getters y setters



    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificación() {
        return this.numeroIdentificación;
    }

    public void setNumeroIdentificación(String numeroIdentificación) {
        this.numeroIdentificación = numeroIdentificación;
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

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public EstadoAfiliadoEnum getEstadoDependiente() {
        return this.estadoDependiente;
    }

    public void setEstadoDependiente(EstadoAfiliadoEnum estadoDependiente) {
        this.estadoDependiente = estadoDependiente;
    }

    public EstadoAfiliadoEnum getEstadoIndependiente() {
        return this.estadoIndependiente;
    }

    public void setEstadoIndependiente(EstadoAfiliadoEnum estadoIndependiente) {
        this.estadoIndependiente = estadoIndependiente;
    }

    public EstadoAfiliadoEnum getEstadoPensionado() {
        return this.estadoPensionado;
    }

    public void setEstadoPensionado(EstadoAfiliadoEnum estadoPensionado) {
        this.estadoPensionado = estadoPensionado;
    }

    public EstadoAfiliadoEnum getEstadoBeneficiario() {
        return this.estadoBeneficiario;
    }

    public void setEstadoBeneficiario(EstadoAfiliadoEnum estadoBeneficiario) {
        this.estadoBeneficiario = estadoBeneficiario;
    }

    public Long getTiempoAfiliacion() {
        return this.tiempoAfiliacion;
    }

    public void setTiempoAfiliacion(Long tiempoAfiliacion) {
        this.tiempoAfiliacion = tiempoAfiliacion;
    }

    public int getHogarFovis() {
        return this.hogarFovis;
    }

    public void setHogarFovis(int hogarFovis) {
        this.hogarFovis = hogarFovis;
    }

    public EstadoHogarEnum getEstadoHogar() {
        return this.estadoHogar;
    }

    public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    public String getUltimoPeriodoAfiliadoPrincipal() {
        return this.ultimoPeriodoAfiliadoPrincipal;
    }

    public void setUltimoPeriodoAfiliadoPrincipal(String ultimoPeriodoAfiliadoPrincipal) {
        this.ultimoPeriodoAfiliadoPrincipal = ultimoPeriodoAfiliadoPrincipal;
    }

    public String getUltimoPeriodoBeneficiario() {
        return this.ultimoPeriodoBeneficiario;
    }

    public void setUltimoPeriodoBeneficiario(String ultimoPeriodoBeneficiario) {
        this.ultimoPeriodoBeneficiario = ultimoPeriodoBeneficiario;
    }

    public int getAportes() {
        return this.aportes;
    }

    public void setAportes(int aportes) {
        this.aportes = aportes;
    }

    public String getArchivo() {
        return this.archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getPagadorPension() {
        return this.pagadorPension;
    }

    public void setPagadorPension(String pagadorPension) {
        this.pagadorPension = pagadorPension;
    }

    public Long getValorMesadaPensional() {
        return this.valorMesadaPensional;
    }

    public void setValorMesadaPensional(Long valorMesadaPensional) {
        this.valorMesadaPensional = valorMesadaPensional;
    }

    public String getFechaRecepcionDocumentos() {
        return this.fechaRecepcionDocumentos;
    }

    public void setFechaRecepcionDocumentos(String fechaRecepcionDocumentos) {
        this.fechaRecepcionDocumentos = fechaRecepcionDocumentos;
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
            " tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificación='" + getNumeroIdentificación() + "'" +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad='" + getEdad() + "'" +
            ", estadoDependiente='" + getEstadoDependiente() + "'" +
            ", estadoIndependiente='" + getEstadoIndependiente() + "'" +
            ", estadoPensionado='" + getEstadoPensionado() + "'" +
            ", estadoBeneficiario='" + getEstadoBeneficiario() + "'" +
            ", tiempoAfiliacion='" + getTiempoAfiliacion() + "'" +
            ", hogarFovis='" + getHogarFovis() + "'" +
            ", estadoHogar='" + getEstadoHogar() + "'" +
            ", ultimoPeriodoAfiliadoPrincipal='" + getUltimoPeriodoAfiliadoPrincipal() + "'" +
            ", ultimoPeriodoBeneficiario='" + getUltimoPeriodoBeneficiario() + "'" +
            ", aportes='" + getAportes() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", pagadorPension='" + getPagadorPension() + "'" +
            ", valorMesadaPensional='" + getValorMesadaPensional() + "'" +
            ", fechaRecepcionDocumentos='" + getFechaRecepcionDocumentos() + "'" +
            "}";
    }
    
}