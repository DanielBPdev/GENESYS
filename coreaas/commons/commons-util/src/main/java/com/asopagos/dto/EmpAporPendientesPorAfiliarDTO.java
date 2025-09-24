/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO para recibir las consultas JPQL
 * de las empresas retiradas con aportes y sin afiliar con aportes
 * <br/>
 * <b>Módulo:</b> Asopagos - HU-403 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda </a>
 */
public class EmpAporPendientesPorAfiliarDTO implements Serializable {
    private static final long serialVersionUID = 7340649521163306437L;

    /** Número de registro del empleador */
    private Long numeroRegistro;

    /** Tipo de identificacion del empleador */
    private String tipoIdentificacion;

    /** Número de identificación del aportante */
    private String numIdentificacion;

    /** Nombre de la empresa */
    private String nombreEmpresa;

    /** Fecha del ultimo recaudo de aportes realizado para este empleador */
    private Date fechaUltimoRecaudo;

    /** Estado del empleador */
    private String estadoActual;

    /** Fecha de ingreso a la bandeja de empleador con aportes */
    private Date fechaIngresoBandeja;

    /** Tipo de identificacion de la persona */
    private TipoIdentificacionEnum tipoId;

    /** Digito de verificacion */
    private Short digitoVerificacion;

    /** Id de la empresa */
    private Long idEmpresa;
    
    /** Nombre de la empresa */
    private String primerNombre;
    
    /** Nombre de la empresa */
    private String segundoNombre;
    
    /** Nombre de la empresa */
    private String primerApellido;
    
    /** Nombre de la empresa */
    private String segundoApellido;

    /**
     * Constructor por defecto
     */
    public EmpAporPendientesPorAfiliarDTO() {
    }

    /**
     * @param tipoIdentificacion
     * @param numIdentificacion
     * @param nombreEmpresa
     * @param estadoActual
     * @param fechaUltimoRecaudo
     * @param fechaIngresoBandeja
     * @param digitoVerificacion
     */
    public EmpAporPendientesPorAfiliarDTO(TipoIdentificacionEnum tipoIdentificacion, String numIdentificacion,
            String nombreEmpresa, String estadoActual, Date fechaUltimoRecaudo, Date fechaIngresoBandeja,
            Short digitoVerificacion) {
        this.tipoId = tipoIdentificacion;
        this.numIdentificacion = numIdentificacion;
        this.nombreEmpresa = nombreEmpresa;
        this.estadoActual = estadoActual;
        this.fechaUltimoRecaudo = fechaUltimoRecaudo;
        this.fechaIngresoBandeja = fechaIngresoBandeja;
        this.digitoVerificacion = digitoVerificacion;
    }
    
    /**
     * @param tipoIdentificacion
     * @param numIdentificacion
     * @param nombreEmpresa
     * @param estadoActual
     * @param fechaUltimoRecaudo
     * @param fechaIngresoBandeja
     * @param digitoVerificacion
     */
    public EmpAporPendientesPorAfiliarDTO(TipoIdentificacionEnum tipoIdentificacion, String numIdentificacion,
            String nombreEmpresa, String estadoActual, Date fechaUltimoRecaudo, Date fechaIngresoBandeja,
            Short digitoVerificacion, String primerNombre,String segundoNombre,String primerApellido,String segundoApellido) {
        this.tipoId = tipoIdentificacion;
        this.numIdentificacion = numIdentificacion;
        this.nombreEmpresa = nombreEmpresa;
        this.estadoActual = estadoActual;
        this.fechaUltimoRecaudo = fechaUltimoRecaudo;
        this.fechaIngresoBandeja = fechaIngresoBandeja;
        this.digitoVerificacion = digitoVerificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }
    
    /**
     * @param fila
     */
    public EmpAporPendientesPorAfiliarDTO(Object[] fila) {
        this.tipoId = TipoIdentificacionEnum.valueOf((String) fila[0]);
        this.numIdentificacion = (String) fila[1];
        this.nombreEmpresa = (String) fila[2];
        this.estadoActual = (String) fila[3];
        this.fechaUltimoRecaudo = fila[4] != null ? (Date) fila[4] : null;
        this.fechaIngresoBandeja = fila[5] != null ? (Date) fila[5] : null;
        this.digitoVerificacion = (Short) fila[6];
        this.primerNombre = (String) fila[7];
        this.segundoNombre = (String) fila[8];
        this.primerApellido = (String) fila[9];
        this.segundoApellido = (String) fila[10];
    }

    /**
     * @return the numeroRegistro
     */
    public Long getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @param numeroRegistro
     *        the numeroRegistro to set
     */
    public void setNumeroRegistro(Long numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numIdentificacion
     */
    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * @param numIdentificacion
     *        the numIdentificacion to set
     */
    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    /**
     * @return the nombreEmpresa
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * @param nombreEmpresa
     *        the nombreEmpresa to set
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * @return the fechaUltimoRecaudo
     */
    public Date getFechaUltimoRecaudo() {
        return fechaUltimoRecaudo;
    }

    /**
     * @param fechaUltimoRecaudo
     *        the fechaUltimoRecaudo to set
     */
    public void setFechaUltimoRecaudo(Date fechaUltimoRecaudo) {
        this.fechaUltimoRecaudo = fechaUltimoRecaudo;
    }

    /**
     * @return the estadoActual
     */
    public String getEstadoActual() {
        return estadoActual;
    }

    /**
     * @param estadoActual
     *        the estadoActual to set
     */
    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    /**
     * @return the fechaIngresoBandeja
     */
    public Date getFechaIngresoBandeja() {
        return fechaIngresoBandeja;
    }

    /**
     * @param fechaIngresoBandeja
     *        the fechaIngresoBandeja to set
     */
    public void setFechaIngresoBandeja(Date fechaIngresoBandeja) {
        this.fechaIngresoBandeja = fechaIngresoBandeja;
    }

    /**
     * @return the tipoId
     */
    public TipoIdentificacionEnum getTipoId() {
        return tipoId;
    }

    /**
     * @param tipoId
     *        the tipoId to set
     */
    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * @return
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa
     *        the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
    
    
}
