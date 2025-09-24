package com.asopagos.historicos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class BeneficiarioGrupoFamiliarDTO implements Serializable{

    /**
     * SERIAL
     */
    private static final long serialVersionUID = -1426004104407850434L;

    private String tipoBeneficiario;
    private ClasificacionEnum parentezco;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificacion;
    private EstadoAfiliadoEnum estado;
    private String fechaIngresoBenef;
    private String afiliadoSecundario;
    private TipoIdentificacionEnum tipoIdAfiSecundario;
    private String numeroIdAfiSecundario;
    Long idBeneficiario;
    
    /**
     * CC vistas 360
     */
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
    
    /**
     * CC Vistas 360
     */
    private String fechaRetiroBeneficiario;
    
    /**
     * 
     */
    public BeneficiarioGrupoFamiliarDTO() {
        super();
    }

    /**
     * @param parentezco
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param estado
     */
    public BeneficiarioGrupoFamiliarDTO(ClasificacionEnum parentezco, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, EstadoAfiliadoEnum estado, String fechaIngresoBenef) {
        this.parentezco = parentezco;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estado = estado;
        this.fechaIngresoBenef = fechaIngresoBenef;
    }

    /**
     * Constructor para consulta de beneficiarios asociados a grupo familiar
     * @param parentezco
     *        Clasificación del beneficiario
     * @param primerNombre
     *        Primer nombre beneficiario
     * @param segundoNombre
     *        Segundo nombre beneficiario
     * @param primerApellido
     *        Primer apellido beneficiario
     * @param segundoApellido
     *        Segundo apellido beneficiario
     * @param tipoIdentificacion
     *        Tipo identificación beneficiario
     * @param numeroIdentificacion
     *        Numero de identificación beneficiario
     * @param estado
     *        Estado beneficiario
     * @param fechaIngresoBenef
     *        Fecha de ingreso beneficiario
     * @param afiliadoSecundario
     *        Nombres afiliado asociado
     * @param tipoIdAfiSecundario
     *        Tipo identificación afiliado secundario
     * @param numeroIdAfiSecundario
     *        Número identificación afiliado secundario
     * @param motivoDesafiliacion
     *        Motivo desafiliacion beneficiario
     */
    public BeneficiarioGrupoFamiliarDTO(String parentezco, String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String tipoIdentificacion, String numeroIdentificacion, String estado, Date fechaIngresoBenef,
            String afiliadoSecundario, String tipoIdAfiSecundario, String numeroIdAfiSecundario, String motivoDesafiliacion,
            Date fechaRetiroBeneficiario, Long idBeneficiario) {
        super();
        if (parentezco != null) {
            this.parentezco = ClasificacionEnum.valueOf(parentezco);
            this.tipoBeneficiario = this.parentezco.getSujetoTramite().getDescripcion();
        }
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        if (tipoIdentificacion != null) {
            this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        }
        this.numeroIdentificacion = numeroIdentificacion;
        if (estado != null) {
            this.estado = EstadoAfiliadoEnum.valueOf(estado);
        }
        if (fechaIngresoBenef != null) {
            this.fechaIngresoBenef = fechaIngresoBenef.toString();
        }
        this.afiliadoSecundario = afiliadoSecundario;
        if (tipoIdAfiSecundario != null) {
            this.tipoIdAfiSecundario = TipoIdentificacionEnum.valueOf(tipoIdAfiSecundario);
        }
        this.numeroIdAfiSecundario = numeroIdAfiSecundario;
        if (motivoDesafiliacion != null) {
            this.motivoDesafiliacion = MotivoDesafiliacionBeneficiarioEnum.valueOf(motivoDesafiliacion);
        }
        if (fechaRetiroBeneficiario != null) {
            this.fechaRetiroBeneficiario = fechaRetiroBeneficiario.toString();
        }
        if (idBeneficiario != null) {
            this.idBeneficiario = idBeneficiario;
        }
    }

    /**
     * @return the parentezco
     */
    public ClasificacionEnum getParentezco() {
        return parentezco;
    }

    /**
     * @param parentezco the parentezco to set
     */
    public void setParentezco(ClasificacionEnum parentezco) {
        this.parentezco = parentezco;
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
     * @return the estado
     */
    public EstadoAfiliadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoAfiliadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoBeneficiario
     */
    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the fechaIngresoBenef
     */
    public String getFechaIngresoBenef() {
        return fechaIngresoBenef;
    }

    /**
     * @param fechaIngresoBenef the fechaIngresoBenef to set
     */
    public void setFechaIngresoBenef(String fechaIngresoBenef) {
        this.fechaIngresoBenef = fechaIngresoBenef;
    }
    
    

    /**
     * @return the afiliadoSecundario
     */
    public String getAfiliadoSecundario() {
        return afiliadoSecundario;
    }

    /**
     * @param afiliadoSecundario the afiliadoSecundario to set
     */
    public void setAfiliadoSecundario(String afiliadoSecundario) {
        this.afiliadoSecundario = afiliadoSecundario;
    }

    /**
     * @return the tipoIdAfiSecundario
     */
    public TipoIdentificacionEnum getTipoIdAfiSecundario() {
        return tipoIdAfiSecundario;
    }

    /**
     * @param tipoIdAfiSecundario the tipoIdAfiSecundario to set
     */
    public void setTipoIdAfiSecundario(TipoIdentificacionEnum tipoIdAfiSecundario) {
        this.tipoIdAfiSecundario = tipoIdAfiSecundario;
    }

    /**
     * @return the numeroIdAfiSecundario
     */
    public String getNumeroIdAfiSecundario() {
        return numeroIdAfiSecundario;
    }

    /**
     * @param numeroIdAfiSecundario the numeroIdAfiSecundario to set
     */
    public void setNumeroIdAfiSecundario(String numeroIdAfiSecundario) {
        this.numeroIdAfiSecundario = numeroIdAfiSecundario;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the fechaRetiroBeneficiario
     */
    public String getFechaRetiroBeneficiario() {
        return fechaRetiroBeneficiario;
    }

    /**
     * @param fechaRetiroBeneficiario the fechaRetiroBeneficiario to set
     */
    public void setFechaRetiroBeneficiario(String fechaRetiroBeneficiario) {
        this.fechaRetiroBeneficiario = fechaRetiroBeneficiario;
    }

    public String obtenerNombreCompleto(){
        StringBuilder nombreCompleto = new StringBuilder();
        
        if(primerNombre != null){
            nombreCompleto.append(primerNombre+" ");
        }
        if(segundoNombre != null){
            nombreCompleto.append(segundoNombre+" ");
        }
        if(primerApellido != null){
           nombreCompleto.append(primerApellido+" ");
        }
        if(segundoApellido != null){
            nombreCompleto.append(segundoApellido);
         }
        return nombreCompleto.toString();
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }
    
    
}