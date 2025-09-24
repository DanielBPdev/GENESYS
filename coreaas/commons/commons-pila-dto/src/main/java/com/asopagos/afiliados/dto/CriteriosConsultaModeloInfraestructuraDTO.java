/**
 * 
 */
package com.asopagos.afiliados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los criterios de búsqueda para paremetrización de infraestructuras y sitios de pago <br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CriteriosConsultaModeloInfraestructuraDTO implements Serializable{
    private static final long serialVersionUID = -7957253196629593534L;

    /** Identificador único del registro de infraestructura */
    private Long idInfraestructura;

    /** Código de la infraestructura definido por la CCF de acuerdo a lineamiento de la SSF (código CCF 6 dígitos) */
    private String codigoInfraestructura;

    /** Indicador de registro activo */
    private Boolean estadoActivo;

    /** Nombre de la infraestructura */
    private String nombreInfraestructura;

    /** Referencia al tipo de Infraestructura */
    private Long tipoInfraestructura;

    /** Tipo de tenencia de la infraestructura */
    private Long tenencia;

    /** Departamento en el que se encuentra ubicada la infraestructura */
    private Short departamentoInfraestructura;

    /** Municipio en el que se encuentra ubicada la infraestructura */
    private Long municipioInfraestructura;

    /** Descripción de la zona en la que se encuentra la infraestructura */
    private String zona;
    
    /** Identificador único del registro de sitio de pago */
    private Long idSitioPago;

    /** Código del sitio de pago definido por la CCF */
    private String codigoSitioPago;

    /** Nombre del sitio de pago */
    private String nombreSitioPago;

    /** Descripción del área geográfica en la que se encuentra ubicada la infraestructura */
    private AreaGeograficaEnum areaGeografica;
    
    /** Indicador de que el sitio pago es principal */
    private Boolean sitioPagoPrincipal;

    /**
     * @return the idInfraestructura
     */
    public Long getIdInfraestructura() {
        return idInfraestructura;
    }

    /**
     * @param idInfraestructura the idInfraestructura to set
     */
    public void setIdInfraestructura(Long idInfraestructura) {
        this.idInfraestructura = idInfraestructura;
    }

    /**
     * @return the codigoInfraestructura
     */
    public String getCodigoInfraestructura() {
        return codigoInfraestructura;
    }

    /**
     * @param codigoInfraestructura the codigoInfraestructura to set
     */
    public void setCodigoInfraestructura(String codigoInfraestructura) {
        this.codigoInfraestructura = codigoInfraestructura;
    }

    /**
     * @return the estadoActivo
     */
    public Boolean getEstadoActivo() {
        return estadoActivo;
    }

    /**
     * @param estadoActivo the estadoActivo to set
     */
    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    /**
     * @return the nombreInfraestructura
     */
    public String getNombreInfraestructura() {
        return nombreInfraestructura;
    }

    /**
     * @param nombreInfraestructura the nombreInfraestructura to set
     */
    public void setNombreInfraestructura(String nombreInfraestructura) {
        this.nombreInfraestructura = nombreInfraestructura;
    }

    /**
     * @return the tipoInfraestructura
     */
    public Long getTipoInfraestructura() {
        return tipoInfraestructura;
    }

    /**
     * @param tipoInfraestructura the tipoInfraestructura to set
     */
    public void setTipoInfraestructura(Long tipoInfraestructura) {
        this.tipoInfraestructura = tipoInfraestructura;
    }

    /**
     * @return the tenencia
     */
    public Long getTenencia() {
        return tenencia;
    }

    /**
     * @param tenencia the tenencia to set
     */
    public void setTenencia(Long tenencia) {
        this.tenencia = tenencia;
    }

    /**
     * @return the departamentoInfraestructura
     */
    public Short getDepartamentoInfraestructura() {
        return departamentoInfraestructura;
    }

    /**
     * @param departamentoInfraestructura the departamentoInfraestructura to set
     */
    public void setDepartamentoInfraestructura(Short departamentoInfraestructura) {
        this.departamentoInfraestructura = departamentoInfraestructura;
    }

    /**
     * @return the municipioInfraestructura
     */
    public Long getMunicipioInfraestructura() {
        return municipioInfraestructura;
    }

    /**
     * @param municipioInfraestructura the municipioInfraestructura to set
     */
    public void setMunicipioInfraestructura(Long municipioInfraestructura) {
        this.municipioInfraestructura = municipioInfraestructura;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the idSitioPago
     */
    public Long getIdSitioPago() {
        return idSitioPago;
    }

    /**
     * @param idSitioPago the idSitioPago to set
     */
    public void setIdSitioPago(Long idSitioPago) {
        this.idSitioPago = idSitioPago;
    }

    /**
     * @return the codigoSitioPago
     */
    public String getCodigoSitioPago() {
        return codigoSitioPago;
    }

    /**
     * @param codigoSitioPago the codigoSitioPago to set
     */
    public void setCodigoSitioPago(String codigoSitioPago) {
        this.codigoSitioPago = codigoSitioPago;
    }

    /**
     * @return the nombreSitioPago
     */
    public String getNombreSitioPago() {
        return nombreSitioPago;
    }

    /**
     * @param nombreSitioPago the nombreSitioPago to set
     */
    public void setNombreSitioPago(String nombreSitioPago) {
        this.nombreSitioPago = nombreSitioPago;
    }

    /**
     * @return the areaGeografica
     */
    public AreaGeograficaEnum getAreaGeografica() {
        return areaGeografica;
    }

    /**
     * @param areaGeografica the areaGeografica to set
     */
    public void setAreaGeografica(AreaGeograficaEnum areaGeografica) {
        this.areaGeografica = areaGeografica;
    }

    /**
     * @return the sitioPagoPrincipal
     */
    public Boolean getSitioPagoPrincipal() {
        return sitioPagoPrincipal;
    }

    /**
     * @param sitioPagoPrincipal the sitioPagoPrincipal to set
     */
    public void setSitioPagoPrincipal(Boolean sitioPagoPrincipal) {
        this.sitioPagoPrincipal = sitioPagoPrincipal;
    }
}
