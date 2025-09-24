package com.asopagos.historicos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

public class AfiliadoPpalGrupoFamiliarDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String numeroIdentificacionAfiPpal;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private TipoIdentificacionEnum tipoIdentificacionAfiPpal;
    private TipoMedioDePagoEnum medioDePago;
    private Long idGrupoFamiliar;
    
    /**
     * 
     */
    public AfiliadoPpalGrupoFamiliarDTO() {
    }

    /**
     * @param numeroIdentificacionAfiPpal
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoIdentificacionAfiPpal
     * @param medioDePago
     * @param idGrupoFamiliar
     */
    public AfiliadoPpalGrupoFamiliarDTO(String numeroIdentificacionAfiPpal, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, TipoIdentificacionEnum tipoIdentificacionAfiPpal,
            TipoMedioDePagoEnum medioDePago, Long idGrupoFamiliar) {
        this.numeroIdentificacionAfiPpal = numeroIdentificacionAfiPpal;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.tipoIdentificacionAfiPpal = tipoIdentificacionAfiPpal;
        this.medioDePago = medioDePago;
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the numeroIdentificacionAfiPpal
     */
    public String getNumeroIdentificacionAfiPpal() {
        return numeroIdentificacionAfiPpal;
    }

    /**
     * @param numeroIdentificacionAfiPpal the numeroIdentificacionAfiPpal to set
     */
    public void setNumeroIdentificacionAfiPpal(String numeroIdentificacionAfiPpal) {
        this.numeroIdentificacionAfiPpal = numeroIdentificacionAfiPpal;
    }

    /**
     * @return the tipoIdentificacionAfiPpal
     */
    public TipoIdentificacionEnum getTipoIdentificacionAfiPpal() {
        return tipoIdentificacionAfiPpal;
    }

    /**
     * @param tipoIdentificacionAfiPpal the tipoIdentificacionAfiPpal to set
     */
    public void setTipoIdentificacionAfiPpal(TipoIdentificacionEnum tipoIdentificacionAfiPpal) {
        this.tipoIdentificacionAfiPpal = tipoIdentificacionAfiPpal;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
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
}