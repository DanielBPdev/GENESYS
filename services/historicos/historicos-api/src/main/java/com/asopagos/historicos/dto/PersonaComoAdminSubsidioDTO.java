package com.asopagos.historicos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;


/**
 * DTO que contiene los datos de una persona como administrador de subsidio
 * (TRA - vistas 360 persona - pestaña admnistrador subsidio)
 * 
 * @author Steven Quintero González<squintero@heinsohn.com.co>
 *
 */
public class PersonaComoAdminSubsidioDTO implements Serializable{

    /**
     * Serial de la versión UID
     */
    private static final long serialVersionUID = -4585868133009457346L;
    
    private TipoIdentificacionEnum tipoIdAfiliadoPpal;
    
    private String numeroIdAfiliadoPpal;
    
    private String nombreAfiliadoPpal; //concatenar todo el nombre
    
    private Long fechaInicioRelacionGrupo;
    
    private Long fechaFinRelacionGrupo;
    
    private TipoMedioDePagoEnum medioPago;
    
    private Long idGrupoFamiliar;
    
    private List<BeneficiarioGrupoFamiliarDTO> beneficiarios;
        
    /**
     * 
     */
    public PersonaComoAdminSubsidioDTO() {
        super();
    }

    /**
     * Constructor de información de resultado consulta
     * @param tipoIdAfiliadoPpal
     *        Tipo de identificación de afiliado principal
     * @param numeroIdAfiliadoPpal
     *        Número de identificación de afiliado principal
     * @param nombreAfiliadoPpal
     *        Nombre completo afiliado principal
     * @param fechaInicioRelacionGrupo
     *        Fecha de inicio de relación con el grupo
     * @param fechaFinRelacionGrupo
     *        Fecha fin de relación con el grupo
     * @param medioPago
     *        Tipo de medio de pago del grupo familar
     * @param idGrupoFamiliar
     *        Identificador del grupo familiar
     */
    public PersonaComoAdminSubsidioDTO(String tipoIdAfiliadoPpal, String numeroIdAfiliadoPpal, String nombreAfiliadoPpal,
            Date fechaInicioRelacionGrupo, Date fechaFinRelacionGrupo, String medioPago, Long idGrupoFamiliar) {
        super();
        if (tipoIdAfiliadoPpal != null) {
            this.tipoIdAfiliadoPpal = TipoIdentificacionEnum.valueOf(tipoIdAfiliadoPpal);
        }
        this.numeroIdAfiliadoPpal = numeroIdAfiliadoPpal;
        this.nombreAfiliadoPpal = nombreAfiliadoPpal;
        if (fechaInicioRelacionGrupo != null) {
            this.fechaInicioRelacionGrupo = fechaInicioRelacionGrupo.getTime();
        }
        if (fechaFinRelacionGrupo != null) {
            this.fechaFinRelacionGrupo = fechaFinRelacionGrupo.getTime();
        }
        if (medioPago != null) {
            this.medioPago = TipoMedioDePagoEnum.valueOf(medioPago);
        }
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the tipoIdAfiliadoPpal
     */
    public TipoIdentificacionEnum getTipoIdAfiliadoPpal() {
        return tipoIdAfiliadoPpal;
    }

    /**
     * @param tipoIdAfiliadoPpal the tipoIdAfiliadoPpal to set
     */
    public void setTipoIdAfiliadoPpal(TipoIdentificacionEnum tipoIdAfiliadoPpal) {
        this.tipoIdAfiliadoPpal = tipoIdAfiliadoPpal;
    }

    /**
     * @return the numeroIdAfiliadoPpal
     */
    public String getNumeroIdAfiliadoPpal() {
        return numeroIdAfiliadoPpal;
    }

    /**
     * @param numeroIdAfiliadoPpal the numeroIdAfiliadoPpal to set
     */
    public void setNumeroIdAfiliadoPpal(String numeroIdAfiliadoPpal) {
        this.numeroIdAfiliadoPpal = numeroIdAfiliadoPpal;
    }

    /**
     * @return the nombreAfiliadoPpal
     */
    public String getNombreAfiliadoPpal() {
        return nombreAfiliadoPpal;
    }

    /**
     * @param nombreAfiliadoPpal the nombreAfiliadoPpal to set
     */
    public void setNombreAfiliadoPpal(String nombreAfiliadoPpal) {
        this.nombreAfiliadoPpal = nombreAfiliadoPpal;
    }

    /**
     * @return the fechaInicioRelacionGrupo
     */
    public Long getFechaInicioRelacionGrupo() {
        return fechaInicioRelacionGrupo;
    }

    /**
     * @param fechaInicioRelacionGrupo the fechaInicioRelacionGrupo to set
     */
    public void setFechaInicioRelacionGrupo(Long fechaInicioRelacionGrupo) {
        this.fechaInicioRelacionGrupo = fechaInicioRelacionGrupo;
    }

    /**
     * @return the fechaFinRelacionGrupo
     */
    public Long getFechaFinRelacionGrupo() {
        return fechaFinRelacionGrupo;
    }

    /**
     * @param fechaFinRelacionGrupo the fechaFinRelacionGrupo to set
     */
    public void setFechaFinRelacionGrupo(Long fechaFinRelacionGrupo) {
        this.fechaFinRelacionGrupo = fechaFinRelacionGrupo;
    }

    /**
     * @return the medioPago
     */
    public TipoMedioDePagoEnum getMedioPago() {
        return medioPago;
    }

    /**
     * @param medioPago the medioPago to set
     */
    public void setMedioPago(TipoMedioDePagoEnum medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioGrupoFamiliarDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioGrupoFamiliarDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }
}
