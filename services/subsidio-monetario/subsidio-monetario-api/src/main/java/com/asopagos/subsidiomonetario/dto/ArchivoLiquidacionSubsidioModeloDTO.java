package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ArchivoLiquidacionSubsidio;

/**
 * <b>Descripcion:</b> DTO que contiene la información relacionada con los identificadores de los archivos del proceso de liquidación<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-442, HU-311-441, HU-317-266<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoLiquidacionSubsidioModeloDTO implements Serializable {

    private static final long serialVersionUID = 3757548458801261852L;

    /**
     * Código identificador para el registro de los archivos asociados a la liquidación
     */
    private Long idArchivoLiquidacionSubsidio;

    /**
     * Identificador de la solicitud de subsidio
     */
    @NotNull
    private Long idSolicitudLiquidacionSubsidio;

    /**
     * Identificador en el Enterprise Cloud Management para el archivo de liquidacion
     */
    private String identificadorECMLiquidacion;

    /**
     * Identificador en el Enterprise Cloud Management para el archivo de personas sin derecho
     */
    private String identificadorECMPersonasSinDerecho;

    /**
     * Identificador en el Enterprise Cloud Management para el archivo de consignaciones en bancos
     */
    private String identificadorECMConsignacionesBancos;

    /**
     * Identificador en el Enterprise Cloud Management para el archvio de pagos judiciales
     */
    private String identificadorECMPagosJudiciales;

    /**
     * Método que realiza la conversión de entidad a DTO
     * @param archivoLiquidacion
     *        entidad con la información del archivo de liquidacion
     */
    public void convertToDTO(ArchivoLiquidacionSubsidio archivoLiquidacion) {
        this.setIdArchivoLiquidacionSubsidio(archivoLiquidacion.getIdArchivoLiquidacionSubsidio());
        this.setIdSolicitudLiquidacionSubsidio(archivoLiquidacion.getIdSolicitudLiquidacionSubsidio());
        this.setIdentificadorECMLiquidacion(archivoLiquidacion.getIdentificadorECMLiquidacion());
        this.setIdentificadorECMPersonasSinDerecho(archivoLiquidacion.getIdentificadorECMPersonasSinDerecho());
        this.setIdentificadorECMConsignacionesBancos(archivoLiquidacion.getIdentificadorECMConsignacionesBancos());
        this.setIdentificadorECMPagosJudiciales(archivoLiquidacion.getIdentificadorECMPagosJudiciales());
    }

    /**
     * Método que realiza la conversión de DTO a entidad
     * @return
     */
    public ArchivoLiquidacionSubsidio convertToEntity() {
        ArchivoLiquidacionSubsidio archivoLiquidacionSubsidio = new ArchivoLiquidacionSubsidio();

        archivoLiquidacionSubsidio.setIdArchivoLiquidacionSubsidio(this.getIdArchivoLiquidacionSubsidio());
        archivoLiquidacionSubsidio.setIdSolicitudLiquidacionSubsidio(this.getIdSolicitudLiquidacionSubsidio());
        archivoLiquidacionSubsidio.setIdentificadorECMLiquidacion(this.getIdentificadorECMLiquidacion());
        archivoLiquidacionSubsidio.setIdentificadorECMPersonasSinDerecho(this.getIdentificadorECMPersonasSinDerecho());
        archivoLiquidacionSubsidio.setIdentificadorECMConsignacionesBancos(this.getIdentificadorECMConsignacionesBancos());
        archivoLiquidacionSubsidio.setIdentificadorECMPagosJudiciales(this.getIdentificadorECMPagosJudiciales());

        return archivoLiquidacionSubsidio;
    }

    public ArchivoLiquidacionSubsidio convertToEntity(ArchivoLiquidacionSubsidioModeloDTO model) {
        ArchivoLiquidacionSubsidio archivoLiquidacionSubsidio = new ArchivoLiquidacionSubsidio();
        String idECMLiquidacion = this.getIdentificadorECMLiquidacion();
        String idECMSinDerecho = this.getIdentificadorECMPersonasSinDerecho();
        if (model.getIdentificadorECMLiquidacion() != null && !model.getIdentificadorECMLiquidacion().equals("EN_PROCESO")) {
            idECMLiquidacion = model.getIdentificadorECMLiquidacion();
        }
        if (model.getIdentificadorECMPersonasSinDerecho() != null && !model.getIdentificadorECMPersonasSinDerecho().equals("EN_PROCESO")) {
            idECMSinDerecho = model.getIdentificadorECMPersonasSinDerecho();
        }

        archivoLiquidacionSubsidio.setIdArchivoLiquidacionSubsidio(model.getIdArchivoLiquidacionSubsidio());
        archivoLiquidacionSubsidio.setIdSolicitudLiquidacionSubsidio(model.getIdSolicitudLiquidacionSubsidio());
        archivoLiquidacionSubsidio.setIdentificadorECMLiquidacion(idECMLiquidacion);
        archivoLiquidacionSubsidio.setIdentificadorECMPersonasSinDerecho(idECMSinDerecho);
        archivoLiquidacionSubsidio.setIdentificadorECMConsignacionesBancos(model.getIdentificadorECMConsignacionesBancos() != null ? model.getIdentificadorECMConsignacionesBancos() : this.getIdentificadorECMConsignacionesBancos() );
        archivoLiquidacionSubsidio.setIdentificadorECMPagosJudiciales(model.getIdentificadorECMPagosJudiciales() != null ? model.getIdentificadorECMPagosJudiciales() : this.getIdentificadorECMPagosJudiciales() );

        return archivoLiquidacionSubsidio;
    }

    /**
     * @return the idArchivoLiquidacionSubsidio
     */
    public Long getIdArchivoLiquidacionSubsidio() {
        return idArchivoLiquidacionSubsidio;
    }

    /**
     * @param idArchivoLiquidacionSubsidio
     *        the idArchivoLiquidacionSubsidio to set
     */
    public void setIdArchivoLiquidacionSubsidio(Long idArchivoLiquidacionSubsidio) {
        this.idArchivoLiquidacionSubsidio = idArchivoLiquidacionSubsidio;
    }

    /**
     * @return the idSolicitudLiquidacionSubsidio
     */
    public Long getIdSolicitudLiquidacionSubsidio() {
        return idSolicitudLiquidacionSubsidio;
    }

    /**
     * @param idSolicitudLiquidacionSubsidio
     *        the idSolicitudLiquidacionSubsidio to set
     */
    public void setIdSolicitudLiquidacionSubsidio(Long idSolicitudLiquidacionSubsidio) {
        this.idSolicitudLiquidacionSubsidio = idSolicitudLiquidacionSubsidio;
    }

    /**
     * @return the identificadorECMLiquidacion
     */
    public String getIdentificadorECMLiquidacion() {
        return identificadorECMLiquidacion;
    }

    /**
     * @param identificadorECMLiquidacion
     *        the identificadorECMLiquidacion to set
     */
    public void setIdentificadorECMLiquidacion(String identificadorECMLiquidacion) {
        this.identificadorECMLiquidacion = identificadorECMLiquidacion;
    }

    /**
     * @return the identificadorECMPersonasSinDerecho
     */
    public String getIdentificadorECMPersonasSinDerecho() {
        return identificadorECMPersonasSinDerecho;
    }

    /**
     * @param identificadorECMPersonasSinDerecho
     *        the identificadorECMPersonasSinDerecho to set
     */
    public void setIdentificadorECMPersonasSinDerecho(String identificadorECMPersonasSinDerecho) {
        this.identificadorECMPersonasSinDerecho = identificadorECMPersonasSinDerecho;
    }

    /**
     * @return the identificadorECMConsignacionesBancos
     */
    public String getIdentificadorECMConsignacionesBancos() {
        return identificadorECMConsignacionesBancos;
    }

    /**
     * @param identificadorECMConsignacionesBancos
     *        the identificadorECMConsignacionesBancos to set
     */
    public void setIdentificadorECMConsignacionesBancos(String identificadorECMConsignacionesBancos) {
        this.identificadorECMConsignacionesBancos = identificadorECMConsignacionesBancos;
    }

    /**
     * @return the identificadorECMPagosJudiciales
     */
    public String getIdentificadorECMPagosJudiciales() {
        return identificadorECMPagosJudiciales;
    }

    /**
     * @param identificadorECMPagosJudiciales
     *        the identificadorECMPagosJudiciales to set
     */
    public void setIdentificadorECMPagosJudiciales(String identificadorECMPagosJudiciales) {
        this.identificadorECMPagosJudiciales = identificadorECMPagosJudiciales;
    }


    @Override
    public String toString() {
        return "{" +
            " idArchivoLiquidacionSubsidio='" + getIdArchivoLiquidacionSubsidio() + "'" +
            ", idSolicitudLiquidacionSubsidio='" + getIdSolicitudLiquidacionSubsidio() + "'" +
            ", identificadorECMLiquidacion='" + getIdentificadorECMLiquidacion() + "'" +
            ", identificadorECMPersonasSinDerecho='" + getIdentificadorECMPersonasSinDerecho() + "'" +
            ", identificadorECMConsignacionesBancos='" + getIdentificadorECMConsignacionesBancos() + "'" +
            ", identificadorECMPagosJudiciales='" + getIdentificadorECMPagosJudiciales() + "'" +
            "}";
    }


}
