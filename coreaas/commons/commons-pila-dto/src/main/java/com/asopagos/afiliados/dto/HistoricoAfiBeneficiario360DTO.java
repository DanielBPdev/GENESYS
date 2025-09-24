package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class HistoricoAfiBeneficiario360DTO implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = -3057861314164058961L;
    
    private TipoBeneficiarioEnum parentezcoAfiPrincipal;
    private ClasificacionEnum clasificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private Date fechaIngreso;
    private EstadoAfiliadoEnum estadoBeneficiario;
    
    /**
     * CC Vistas 360
     */
    private Date fechaRetiro;
    private String motivoDesafiliacion;
    

    /**
     * 
     */
    public HistoricoAfiBeneficiario360DTO() {
    }
    
    /**
     * @param clasificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param fechaIngreso
     * @param estadoBeneficiario
     */
    public HistoricoAfiBeneficiario360DTO(String clasificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, String tipoIdentificacion,
            String numeroIdentificacion, Date fechaIngreso, String estadoBeneficiario, String motivoDesafiliacion, Date fechaRetiro) {
        this.setClasificacion(ClasificacionEnum.valueOf(clasificacion));
        this.setPrimerNombre(primerNombre);
        this.setSegundoNombre(segundoNombre);
        this.setPrimerApellido(primerApellido);
        this.setSegundoApellido(segundoApellido);
        this.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(tipoIdentificacion));
        this.setNumeroIdentificacion(numeroIdentificacion);
        this.setFechaIngreso(fechaIngreso);
        this.setEstadoBeneficiario(EstadoAfiliadoEnum.valueOf(estadoBeneficiario));
        this.setMotivoDesafiliacion(motivoDesafiliacion);
        this.setFechaRetiro(fechaRetiro);
    }
    
    /**
     * @return the parentezcoAfiPrincipal
     */
    public TipoBeneficiarioEnum getParentezcoAfiPrincipal() {
        return parentezcoAfiPrincipal;
    }
    
    /**
     * @param parentezcoAfiPrincipal the parentezcoAfiPrincipal to set
     */
    public void setParentezcoAfiPrincipal(TipoBeneficiarioEnum parentezcoAfiPrincipal) {
        this.parentezcoAfiPrincipal = parentezcoAfiPrincipal;
    }
    
    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }
    
    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
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
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    /**
     * @return the estadoBeneficiario
     */
    public EstadoAfiliadoEnum getEstadoBeneficiario() {
        return estadoBeneficiario;
    }
    
    /**
     * @param estadoBeneficiario the estadoBeneficiario to set
     */
    public void setEstadoBeneficiario(EstadoAfiliadoEnum estadoBeneficiario) {
        this.estadoBeneficiario = estadoBeneficiario;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public String getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(String motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }
}
