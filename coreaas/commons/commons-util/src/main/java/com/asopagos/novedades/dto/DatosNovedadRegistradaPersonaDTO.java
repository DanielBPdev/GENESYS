package com.asopagos.novedades.dto;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos asociados a las Novedades registradas de una Persona
 * para la Vista 360.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class DatosNovedadRegistradaPersonaDTO {

    /**
     * Relación como Beneficiario.
     */
    private String relacionBeneficiario;

    /**
     * Clasificación del Beneficiario
     */
    private String clasificacionBeneficiario;

    /**
     * Nivel de la Novedad
     */
    private String nivelNovedad;

    /**
     * Nombre de la Novedad
     */
    private TipoTransaccionEnum nombreNovedad;

    /**
     * Fecha de Registro de la Novedad
     */
    private Long fechaRegistroNovedad;

    /**
     * Nombre del afiliado principal.
     */
    private String afiliado;

    /**
     * Estado de la Novedad
     */
    private String estadoNovedad;

    /**
     * Estado Persona antes de la Novedad
     */
    private EstadoAfiliadoEnum estadoPersonaAntes;

    /**
     * Estado Persona después de la Novedad
     */
    private EstadoAfiliadoEnum estadoPersonaDespues;

    /**
     * Fecha de Inicio de Vigencia
     */
    private Long fechaInicioVigencia;

    /**
     * Fecha Fin de Vigencia
     */
    private Long fechaFinVigencia;

    /**
     * Número de Operación de la Novedad
     */
    private String numeroOperacion;

    /**
     * Canal por el que se registró la Novedad
     */
    private String canal;

    /**
     * Eppleador asociado a la novedad de Trabajador Dependiente.
     */
    private String empleador;

    /**
     * Identificador de la solicitud global
     */
    private Long idSolicitudGlobal;

    /**
     * Identificador del empleador asociada a la novedad de TrabajadorDependiente
     */
    private Long idEmpleador;

    /**
     * Tipo de identificación del afiliado
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Numero de identificación del afiliado
     */
    private String numeroIdentificacion;

    /**
     * Identificador de la persona
     */
    private Long idPersona;

    /**
     * Identificador de la empresa asociada a la novedad de TrabajadorDependiente
     */
    private Long idEmpresa;

    /**
     * Tipo de identificación del beneficiario
     */
    private TipoIdentificacionEnum tipoIdentificacionBeneficiario;

    /**
     * Numero de identificación del beneficiario
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Nombres y apellidos del beneficiario
     */
    private String nombresApellidosBeneficiario;
    
    /**
     * @return the relacionBeneficiario
     */
    public String getRelacionBeneficiario() {
        return relacionBeneficiario;
    }

    /**
     * @param relacionBeneficiario
     *        the relacionBeneficiario to set
     */
    public void setRelacionBeneficiario(String relacionBeneficiario) {
        this.relacionBeneficiario = relacionBeneficiario;
    }

    /**
     * @return the clasificacionBeneficiario
     */
    public String getClasificacionBeneficiario() {
        return clasificacionBeneficiario;
    }

    /**
     * @param clasificacionBeneficiario
     *        the clasificacionBeneficiario to set
     */
    public void setClasificacionBeneficiario(String clasificacionBeneficiario) {
        this.clasificacionBeneficiario = clasificacionBeneficiario;
    }

    /**
     * @return the nivelNovedad
     */
    public String getNivelNovedad() {
        return nivelNovedad;
    }

    /**
     * @param nivelNovedad
     *        the nivelNovedad to set
     */
    public void setNivelNovedad(String nivelNovedad) {
        this.nivelNovedad = nivelNovedad;
    }

    /**
     * @return the nombreNovedad
     */
    public TipoTransaccionEnum getNombreNovedad() {
        return nombreNovedad;
    }

    /**
     * @param nombreNovedad
     *        the nombreNovedad to set
     */
    public void setNombreNovedad(TipoTransaccionEnum nombreNovedad) {
        this.nombreNovedad = nombreNovedad;
    }

    /**
     * @return the fechaRegistroNovedad
     */
    public Long getFechaRegistroNovedad() {
        return fechaRegistroNovedad;
    }

    /**
     * @param fechaRegistroNovedad
     *        the fechaRegistroNovedad to set
     */
    public void setFechaRegistroNovedad(Long fechaRegistroNovedad) {
        this.fechaRegistroNovedad = fechaRegistroNovedad;
    }

    /**
     * @return the estadoNovedad
     */
    public String getEstadoNovedad() {
        return estadoNovedad;
    }

    /**
     * @param estadoNovedad
     *        the estadoNovedad to set
     */
    public void setEstadoNovedad(String estadoNovedad) {
        this.estadoNovedad = estadoNovedad;
    }

    /**
     * @return the estadoPersonaAntes
     */
    public EstadoAfiliadoEnum getEstadoPersonaAntes() {
        return estadoPersonaAntes;
    }

    /**
     * @param estadoPersonaAntes
     *        the estadoPersonaAntes to set
     */
    public void setEstadoPersonaAntes(EstadoAfiliadoEnum estadoPersonaAntes) {
        this.estadoPersonaAntes = estadoPersonaAntes;
    }

    /**
     * @return the estadoPersonaDespues
     */
    public EstadoAfiliadoEnum getEstadoPersonaDespues() {
        return estadoPersonaDespues;
    }

    /**
     * @param estadoPersonaDespues
     *        the estadoPersonaDespues to set
     */
    public void setEstadoPersonaDespues(EstadoAfiliadoEnum estadoPersonaDespues) {
        this.estadoPersonaDespues = estadoPersonaDespues;
    }

    /**
     * @return the fechaInicioVigencia
     */
    public Long getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    /**
     * @param fechaInicioVigencia
     *        the fechaInicioVigencia to set
     */
    public void setFechaInicioVigencia(Long fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    /**
     * @return the fechaFinVigencia
     */
    public Long getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    /**
     * @param fechaFinVigencia
     *        the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Long fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    /**
     * @return the numeroOperacion
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion
     *        the numeroOperacion to set
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the canal
     */
    public String getCanal() {
        return canal;
    }

    /**
     * @param canal
     *        the canal to set
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * @return the empleador
     */
    public String getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador
     *        the empleador to set
     */
    public void setEmpleador(String empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the afiliado
     */
    public String getAfiliado() {
        return afiliado;
    }

    /**
     * @param afiliado
     *        the afiliado to set
     */
    public void setAfiliado(String afiliado) {
        this.afiliado = afiliado;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
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
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public TipoIdentificacionEnum getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario the tipoIdentificacionBeneficiario to set
     */
    public void setTipoIdentificacionBeneficiario(TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario the numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @return the nombresApellidosBeneficiario
     */
    public String getNombresApellidosBeneficiario() {
        return nombresApellidosBeneficiario;
    }

    /**
     * @param nombresApellidosBeneficiario the nombresApellidosBeneficiario to set
     */
    public void setNombresApellidosBeneficiario(String nombresApellidosBeneficiario) {
        this.nombresApellidosBeneficiario = nombresApellidosBeneficiario;
    }
}
