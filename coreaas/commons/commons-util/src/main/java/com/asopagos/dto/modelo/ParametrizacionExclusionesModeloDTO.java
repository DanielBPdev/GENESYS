package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.cartera.ParametrizacionExclusiones;

import java.io.Serializable;

/**
 * Modelo DTO para la Parametrización de exclusiones en cartera proceso 2.2
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ParametrizacionExclusionesModeloDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2285829248674979457L;
    /**
     * Identificador de la entidad Parametrización de Exclusiones
     */
    private Long idParametrizacionExclusiones;
    /**
     * Activo/Inactivo para exclusión por negocio
     */
    private Boolean exclusionNegocios;
    /**
     * Activo/Inactivo para exclusión por Imposición de Recurso
     */
    private Boolean imposicionRecurso;
    /**
     * Activo/Inactivo para exclusión por Convenio de pago
     */
    private Boolean convenioPago;
    /**
     * Activo/Inactivo para exclusión por Aclaración de mora
     */
    private Boolean aclaracionMora;
    /**
     * Activo/Inactivo para exclusión por Riesgo de Incobrabilidad
     */
    private Boolean riesgoIncobrabilidad;

    /**
     * Activo/Inactivo para exclusión por moraParcial
     */
    private Boolean exclusionMora;

    /**
     * Método constructor
     */
    public ParametrizacionExclusionesModeloDTO() {
    }

    /**
     * @return the idParametrizacionExclusiones
     */
    public Long getIdParametrizacionExclusiones() {
        return idParametrizacionExclusiones;
    }

    /**
     * @param idParametrizacionExclusiones the idParametrizacionExclusiones to set
     */
    public void setIdParametrizacionExclusiones(Long idParametrizacionExclusiones) {
        this.idParametrizacionExclusiones = idParametrizacionExclusiones;
    }

    /**
     * @return the exclusionNegocios
     */
    public Boolean getExclusionNegocios() {
        return exclusionNegocios;
    }

    /**
     * @param exclusionNegocios the exclusionNegocios to set
     */
    public void setExclusionNegocios(Boolean exclusionNegocios) {
        this.exclusionNegocios = exclusionNegocios;
    }

    /**
     * @return the imposicionRecurso
     */
    public Boolean getImposicionRecurso() {
        return imposicionRecurso;
    }

    /**
     * @param imposicionRecurso the imposicionRecurso to set
     */
    public void setImposicionRecurso(Boolean imposicionRecurso) {
        this.imposicionRecurso = imposicionRecurso;
    }

    /**
     * @return the convenioPago
     */
    public Boolean getConvenioPago() {
        return convenioPago;
    }

    /**
     * @param convenioPago the convenioPago to set
     */
    public void setConvenioPago(Boolean convenioPago) {
        this.convenioPago = convenioPago;
    }

    /**
     * @return the aclaracionMora
     */
    public Boolean getAclaracionMora() {
        return aclaracionMora;
    }

    /**
     * @param aclaracionMora the aclaracionMora to set
     */
    public void setAclaracionMora(Boolean aclaracionMora) {
        this.aclaracionMora = aclaracionMora;
    }

    /**
     * @return the riesgoIncobrabilidad
     */
    public Boolean getRiesgoIncobrabilidad() {
        return riesgoIncobrabilidad;
    }

    /**
     * @param riesgoIncobrabilidad the riesgoIncobrabilidad to set
     */
    public void setRiesgoIncobrabilidad(Boolean riesgoIncobrabilidad) {
        this.riesgoIncobrabilidad = riesgoIncobrabilidad;
    }

    public Boolean getExclusionMora() {
        return exclusionMora;
    }

    public void setExclusionMora(Boolean exclusionMora) {
        this.exclusionMora = exclusionMora;
    }

    /**
     * Método encargado de convertir una entidad a DTO
     *
     * @param parametrizacionExclusiones parametrización de exclusiones
     */
    public void convertToDTO(ParametrizacionExclusiones parametrizacionExclusiones) {
        this.setIdParametrizacionExclusiones(parametrizacionExclusiones.getIdParametrizacionExclusiones());
        this.setAclaracionMora(parametrizacionExclusiones.getAclaracionMora());
        this.setConvenioPago(parametrizacionExclusiones.getConvenioPago());
        this.setExclusionNegocios(parametrizacionExclusiones.getExclusionNegocios());
        this.setImposicionRecurso(parametrizacionExclusiones.getImposicionRecurso());
        this.setRiesgoIncobrabilidad(parametrizacionExclusiones.getRiesgoIncobrabilidad());
        this.setExclusionMora(parametrizacionExclusiones.getExclusionMora());
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     *
     * @return parametrizacion de exclusiones.
     */
    public ParametrizacionExclusiones convertToEntity() {
        ParametrizacionExclusiones parametrizacionExclusiones = new ParametrizacionExclusiones();
        parametrizacionExclusiones.setIdParametrizacionExclusiones(this.getIdParametrizacionExclusiones());
        parametrizacionExclusiones.setAclaracionMora(this.getAclaracionMora());
        parametrizacionExclusiones.setConvenioPago(this.getConvenioPago());
        parametrizacionExclusiones.setExclusionNegocios(this.getExclusionNegocios());
        parametrizacionExclusiones.setImposicionRecurso(this.getImposicionRecurso());
        parametrizacionExclusiones.setRiesgoIncobrabilidad(this.getRiesgoIncobrabilidad());
        parametrizacionExclusiones.setExclusionMora(this.exclusionMora);
        return parametrizacionExclusiones;
    }
}
