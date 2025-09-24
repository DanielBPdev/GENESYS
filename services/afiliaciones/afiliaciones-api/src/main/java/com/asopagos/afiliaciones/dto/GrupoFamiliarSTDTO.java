package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class GrupoFamiliarSTDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Número interno de identificación para el grupo familiar
    */
    private Long idGrupoFamiliar;
    
    /**
    * Código DANE del departamento de ubicación del afiliado
    */
    private String departamento;
    
    /**
    * Código DANE del municipio de ubicación del afiliado
    */
    private String municipio;
    
    /**
    * Dirección física principal del afiliado
    */
    private String direccionResidencia;
    
    /**
    * Tipo de identificación del administrador del subsidio
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación del administrador del subsidio
    */
    private String identificacion;
    
    /**
    * Nombre completo del administrador del subsidio
    */
    private String nombreCompleto;
    
    /**
     * Sitio de pago asignado para el afiliado
     */
     private String sitioDePago;
     
    /**
     * Arreglo de beneficiarios
     */
    private List<BeneficiarioSTDTO> beneficiarios;

    /**
     * 
     */
    public GrupoFamiliarSTDTO() {
    }

    /**
     * @param idGrupoFamiliar
     * @param departamento
     * @param municipio
     * @param direccionResidencia
     * @param tipoID
     * @param identificacion
     * @param nombreCompleto
     * @param sitioDePago
     */
    public GrupoFamiliarSTDTO(Long idGrupoFamiliar, String departamento, String municipio, String direccionResidencia,
            TipoIdentificacionEnum tipoID, String identificacion, String nombreCompleto, String sitioDePago) {
        this.idGrupoFamiliar = idGrupoFamiliar;
        this.departamento = departamento;
        this.municipio = municipio;
        this.direccionResidencia = direccionResidencia;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.sitioDePago = sitioDePago;
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
     * @return the direccionResidencia
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * @param direccionResidencia the direccionResidencia to set
     */
    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
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
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the sitioDePago
     */
    public String getSitioDePago() {
        return sitioDePago;
    }

    /**
     * @param sitioDePago the sitioDePago to set
     */
    public void setSitioDePago(String sitioDePago) {
        this.sitioDePago = sitioDePago;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioSTDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioSTDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }
}
