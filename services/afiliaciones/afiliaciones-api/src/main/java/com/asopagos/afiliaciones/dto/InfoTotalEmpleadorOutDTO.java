package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class InfoTotalEmpleadorOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idEmpleador;
    private Long idEmpresa;
    /**
    * Tipo de identificación del empleador
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del empleador
    */
    private String identificacion;

    /**
     * Dígito de verificación del empleador
     */
    private Short digitoVerificacion;
    
    /**
    * Razón social del empleador
    */
    private String razonSocial;
    
    /**
     * 
     */
    private String nombreComercial;
    
    /**
     * Naturaleza jurídica del empleador
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;
    
    /**
     * Fecha de constitución de la empresa
     */
    private String fechaConstitucion;

    /**
    * Código DANE del departamento relacionado a la ubicación del empleador.
    */
    private String departamentoCodigo;

    /**
     * Nombre DANE del Departamento relacionado a la ubicación del empleador.
     */
    private String departamento;
    
    /**
    * Código DANE del municipio relacionado a la ubicación del empleador.
    */
    private String municipioCodigo;

    /**
     * Nombre DANE del municipio relacionado a la ubicación del empleador.
     */
     private String municipio;
    
    /**
    * Dirección física principal del empleador
    */
    private String direccionPrincipal;

    /**
    * Indicativo del teléfono + número fijo del empleador
    */
    private String telefonoFijo;

    /**
    * Número telefónico del celular del empleador
    */
    private String celular;

    /**
     * Código postal de la ubicación de la empresa
     */
    private String codigoPostal;
    
    /**
    * (Activo - Inactivo)
    */
    private EstadoEmpleadorEnum estadoAfiliacion;

    /**
    * (Al día - Moroso)
    */
    private EstadoCarteraEnum estadoCartera;

    /**
     * Código de actividad económica de la empresa
     */
    private String actividadEconomica;
    
    /**
     * Descripcion del codigo CIIU de la empresa
     */
    private String descripcionActividadEconomica;
    
    /**
     * Correo electrónico de la empresa principal
     */
    private String correoElectronico;
    
    /**
     * Fecha de afiliación de la empresa a la CCF
     */
    private String fechaAfiliacion;
    
    /**
     * Fecha de desafiliación de la empresa a la CCF
     */
    private String fechaDesafiliacion;
    
    /**
     * Fecha de entrega del comunicado(relacionado a la solicitud de expulsión) de expulsión-empresa.
     */
    private String fechaEntregaExpulsion;
    
    /**
     * Número total de trabajadores del empleador
     */
    private Integer numeroTotalTrabajadores;
    
    /**
     * Último periodo de aportes del empleador
     */
    private String ultimoPeriodoAportes;
    
    /**
     * Día hábil de la fecha límite del pago de aportes para el empleador
     */
    private Short diaHabilVencimientoPagoAportes;
    
    /**
     * información básica del representante legal del empleador
     */
    private RepresentanteLegalOutDTO representanteLegal;

    /**
     * listado de sucursales de la empresa
     */
    private List<SucursalEmpresaOutDTO> arregloSucursales; 

    /**
     * 
     */
    public InfoTotalEmpleadorOutDTO() {
    }

    /**
     * @param idEmpleador
     * @param idEmpresa
     * @param tipoID
     * @param identificacion
     * @param razonSocial
     * @param nombreComercial
     * @param naturalezaJuridica
     * @param fechaConstitucion
     * @param departamentoCodigo
     * @param departamento
     * @param municipioCodigo
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param codigoPostal
     * @param estadoAfiliacion
     * @param estadoCartera
     * @param actividadEconomica
     * @param descripcionActividadEconomica
     * @param correoElectronico
     * @param fechaAfiliacion
     * @param fechaDesafiliacion
     * @param fechaEntregaExpulsion
     * @param numeroTotalTrabajadores
     * @param ultimoPeriodoAportes
     * @param diaHabilVencimientoPagoAportes
     */
    public InfoTotalEmpleadorOutDTO(Long idEmpleador, Long idEmpresa, TipoIdentificacionEnum tipoID, String identificacion, String razonSocial,
            String nombreComercial, NaturalezaJuridicaEnum naturalezaJuridica, String fechaConstitucion, String departamentoCodigo,
            String departamento, String municipioCodigo, String municipio, String direccionPrincipal, String telefonoFijo, String celular,
            String codigoPostal, EstadoEmpleadorEnum estadoAfiliacion, String actividadEconomica,
            String descripcionActividadEconomica, String correoElectronico, String fechaAfiliacion, String fechaDesafiliacion,
            String fechaEntregaExpulsion, Integer numeroTotalTrabajadores, String ultimoPeriodoAportes,
            Short diaHabilVencimientoPagoAportes) {
        this.idEmpleador = idEmpleador;
        this.idEmpresa = idEmpresa;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.naturalezaJuridica = naturalezaJuridica;
        this.fechaConstitucion = fechaConstitucion;
        this.departamentoCodigo = departamentoCodigo;
        this.departamento = departamento;
        this.municipioCodigo = municipioCodigo;
        this.municipio = municipio;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.codigoPostal = codigoPostal;
        this.estadoAfiliacion = estadoAfiliacion;
//        this.estadoCartera = estadoCartera;
        this.actividadEconomica = actividadEconomica;
        this.descripcionActividadEconomica = descripcionActividadEconomica;
        this.correoElectronico = correoElectronico;
        this.fechaAfiliacion = fechaAfiliacion;
        this.fechaDesafiliacion = fechaDesafiliacion;
        this.fechaEntregaExpulsion = fechaEntregaExpulsion;
        this.numeroTotalTrabajadores = numeroTotalTrabajadores;
        this.ultimoPeriodoAportes = ultimoPeriodoAportes;
        this.diaHabilVencimientoPagoAportes = diaHabilVencimientoPagoAportes;
    }



    /**
     * @param idEmpleador
     * @param tipoID
     * @param identificacion
     * @param razonSocial
     * @param departamento
     * @param municipio
     * @param direccionPrincipal
     * @param telefonoFijo
     * @param celular
     * @param estadoAfiliacion
     */
    public InfoTotalEmpleadorOutDTO(Long idEmpleador, TipoIdentificacionEnum tipoID, String identificacion, String razonSocial,
            String departamentoCodigo, String municipioCodigo, String direccionPrincipal, String telefonoFijo, String celular,
            EstadoEmpleadorEnum estadoAfiliacion) {
        this.idEmpleador = idEmpleador;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.departamentoCodigo = departamentoCodigo;
        this.municipioCodigo = municipioCodigo;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * @return the naturalezaJuridica
     */
    public NaturalezaJuridicaEnum getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    /**
     * @param naturalezaJuridica the naturalezaJuridica to set
     */
    public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    /**
     * @return the fechaConstitucion
     */
    public String getFechaConstitucion() {
        return fechaConstitucion;
    }

    /**
     * @param fechaConstitucion the fechaConstitucion to set
     */
    public void setFechaConstitucion(String fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
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
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the direccionPrincipal
     */
    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    /**
     * @param direccionPrincipal the direccionPrincipal to set
     */
    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
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
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the estadoAfiliacion
     */
    public EstadoEmpleadorEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoEmpleadorEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * @param estadoCartera the estadoCartera to set
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * @return the actividadEconomica
     */
    public String getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * @param actividadEconomica the actividadEconomica to set
     */
    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    /**
     * @return the descripcionActividadEconomica
     */
    public String getDescripcionActividadEconomica() {
        return descripcionActividadEconomica;
    }

    /**
     * @param descripcionActividadEconomica the descripcionActividadEconomica to set
     */
    public void setDescripcionActividadEconomica(String descripcionActividadEconomica) {
        this.descripcionActividadEconomica = descripcionActividadEconomica;
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
     * @return the fechaAfiliacion
     */
    public String getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    /**
     * @param fechaAfiliacion the fechaAfiliacion to set
     */
    public void setFechaAfiliacion(String fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    /**
     * @return the fechaDesafiliacion
     */
    public String getFechaDesafiliacion() {
        return fechaDesafiliacion;
    }

    /**
     * @param fechaDesafiliacion the fechaDesafiliacion to set
     */
    public void setFechaDesafiliacion(String fechaDesafiliacion) {
        this.fechaDesafiliacion = fechaDesafiliacion;
    }

    /**
     * @return the fechaEntregaExpulsion
     */
    public String getFechaEntregaExpulsion() {
        return fechaEntregaExpulsion;
    }

    /**
     * @param fechaEntregaExpulsion the fechaEntregaExpulsion to set
     */
    public void setFechaEntregaExpulsion(String fechaEntregaExpulsion) {
        this.fechaEntregaExpulsion = fechaEntregaExpulsion;
    }

    /**
     * @return the numeroTotalTrabajadores
     */
    public Integer getNumeroTotalTrabajadores() {
        return numeroTotalTrabajadores;
    }

    /**
     * @param numeroTotalTrabajadores the numeroTotalTrabajadores to set
     */
    public void setNumeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
        this.numeroTotalTrabajadores = numeroTotalTrabajadores;
    }

    /**
     * @return the ultimoPeriodoAportes
     */
    public String getUltimoPeriodoAportes() {
        return ultimoPeriodoAportes;
    }

    /**
     * @param ultimoPeriodoAportes the ultimoPeriodoAportes to set
     */
    public void setUltimoPeriodoAportes(String ultimoPeriodoAportes) {
        this.ultimoPeriodoAportes = ultimoPeriodoAportes;
    }

    /**
     * @return the diaHabilVencimientoPagoAportes
     */
    public Short getDiaHabilVencimientoPagoAportes() {
        return diaHabilVencimientoPagoAportes;
    }

    /**
     * @param diaHabilVencimientoPagoAportes the diaHabilVencimientoPagoAportes to set
     */
    public void setDiaHabilVencimientoPagoAportes(Short diaHabilVencimientoPagoAportes) {
        this.diaHabilVencimientoPagoAportes = diaHabilVencimientoPagoAportes;
    }

    /**
     * @return the representanteLegal
     */
    public RepresentanteLegalOutDTO getRepresentanteLegal() {
        return representanteLegal;
    }

    /**
     * @param representanteLegal the representanteLegal to set
     */
    public void setRepresentanteLegal(RepresentanteLegalOutDTO representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    /**
     * @return the arregloSucursales
     */
    public List<SucursalEmpresaOutDTO> getArregloSucursales() {
        return arregloSucursales;
    }

    /**
     * @param arregloSucursales the arregloSucursales to set
     */
    public void setArregloSucursales(List<SucursalEmpresaOutDTO> arregloSucursales) {
        this.arregloSucursales = arregloSucursales;
    }

	/**
	 * @return the digitoVerificacion
	 */
	public Short getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * @param digitoVerificacion the digitoVerificacion to set
	 */
	public void setDigitoVerificacion(Short digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}
    
    
}