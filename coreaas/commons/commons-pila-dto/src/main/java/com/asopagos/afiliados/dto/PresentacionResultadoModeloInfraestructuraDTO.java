package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import com.asopagos.dto.modelo.InfraestructuraModeloDTO;
import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.enumeraciones.core.MedidaCapacidadInfraestructuraEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos a presentar en un resultado de consulta de sitios de pago o infraestructuras<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class PresentacionResultadoModeloInfraestructuraDTO implements Serializable {
    private static final long serialVersionUID = -3208739246541937043L;

    /** Identificador único del registro de infraestructura */
    private Long idInfraestructura;

    /** Código de la infraestructura definido por la CCF de acuerdo a lineamiento de la SSF (código CCF 6 dígitos) */
    private String codigoInfraestructura;

    /** Nombre de la infraestructura */
    private String nombreInfraestructura;

    /** Indicador de registro activo */
    private Boolean estado;

    /** Referencia al tipo de Infraestructura */
    private Long idTipoInfraestructura;

    /** Referencia al tipo de Infraestructura */
    private String tipoInfraestructura;

    /** Unidad de capacidad instalada de la Infraestructura */
    private String unidadCapacidadInstalada;

    /** Tipo de tenencia de la infraestructura */
    private Long idTenencia;

    /** Tipo de tenencia de la infraestructura */
    private String tenencia;

    /** Departamento en el que se encuentra ubicada la infraestructura */
    private String departamentoInfraestructura;

    /** ID del Departamento en el que se encuentra ubicada la infraestructura */
    private Short idDepartamentoInfraestructura;

    /** Municipio en el que se encuentra ubicada la infraestructura */
    private String municipioInfraestructura;

    /** ID del Municipio en el que se encuentra ubicada la infraestructura */
    private Short idMunicipioInfraestructura;

    /** Descripción de la zona en la que se encuentra la infraestructura */
    private String zona;

    /** Dirección física de la infraestructura */
    private String direccionInfraestructura;

    /** Latitud de ubicación geográfica de la infraestructura */
    private String latitudInfraestructura;

    /** longitud de ubicación geográfica de la infraestructura */
    private String longitudInfraestructura;
    
    /** Capacidad estimada para la infraestructura (de acuerdo al tipo de infraestructura seleccionado)*/
    private String capacidadEstimada;

    /** Identificador único del registro de sitio de pago */
    private Long idSitioPago;

    /** Código del sitio de pago definido por la CCF */
    private String codigoSitioPago;

    /** Nombre del sitio de pago */
    private String nombreSitioPago;

    /** Descripción del área geográfica en la que se encuentra ubicada la infraestructura */
    private AreaGeograficaEnum areaGeografica;
    
    /**
     * Indicador de que la infraestructura tiene sitio pago
     */
    private Boolean tieneSitioPago;
    
    /**
     * Indicador de que la infraestructura tiene sitio pago principal
     */
    private Boolean tieneSitioPagoPrincipal;
    
    /**
     * Sitios de pagos relacionados a la infraestructura
     */
    private List<SitioPagoModeloDTO> sitiosPagos;

    /**
     * Constructor por defecto para JSON converter
     */
    public PresentacionResultadoModeloInfraestructuraDTO() {
    }

    /**
     * Constructor para consulta Afiliado.Infraestructura.ConsultarInfraestructuraParaPantalla
     */
    public PresentacionResultadoModeloInfraestructuraDTO(Long idInfraestructura, String codigoInfraestructura, String nombreInfraestructura,
            Boolean estadoInfraestructura, Long idTipoInfraestructura, String tipoInfraestructura,
            MedidaCapacidadInfraestructuraEnum unidadCapacidadInstalada, Long idTenencia, String tenencia,
            Short idDepartamentoInfraestructura, String departamentoInfraestructura, Short idMunicipioInfraestructura,
            String municipioInfraestructura, String zona, String direccionInfraestructura, BigDecimal latitudInfraestructura,
            BigDecimal longitudInfraestructura, BigDecimal capacidadEstimada, AreaGeograficaEnum areaGeografica) {

        this.idInfraestructura = idInfraestructura;
        this.codigoInfraestructura = codigoInfraestructura;
        this.nombreInfraestructura = nombreInfraestructura;
        this.estado = estadoInfraestructura;
        this.idTipoInfraestructura = idTipoInfraestructura;
        this.tipoInfraestructura = tipoInfraestructura;
        this.unidadCapacidadInstalada = unidadCapacidadInstalada.getDescripcion();
        this.idTenencia = idTenencia;
        this.tenencia = tenencia;
        this.idDepartamentoInfraestructura = idDepartamentoInfraestructura;
        this.departamentoInfraestructura = departamentoInfraestructura;
        this.idMunicipioInfraestructura = idMunicipioInfraestructura;
        this.municipioInfraestructura = municipioInfraestructura;
        this.zona = zona;
        this.direccionInfraestructura = direccionInfraestructura;
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumIntegerDigits(3);
        formatter.setMinimumFractionDigits(6);
        formatter.setMaximumFractionDigits(6);

        this.latitudInfraestructura = formatter.format(latitudInfraestructura);
        this.longitudInfraestructura = formatter.format(longitudInfraestructura);
        
        if(capacidadEstimada != null){
            formatter.setMaximumIntegerDigits(5);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            this.capacidadEstimada = formatter.format(capacidadEstimada);
        }

        this.areaGeografica = areaGeografica;
    }

    /**
     * Constructor para consulta Afiliado.SitioPago.ConsultarSitioPagoParaPantalla
     */
    public PresentacionResultadoModeloInfraestructuraDTO(Long idSitioPago, String codigoSitio, String nombreSitio, Boolean activo,
            String codigoInfraestructura, Long idInfraestructura, Boolean tieneSitioPagoPrincipal) {
        this.idSitioPago = idSitioPago;
        this.codigoSitioPago = codigoSitio;
        this.nombreSitioPago = nombreSitio;
        this.estado = activo;
        this.codigoInfraestructura = codigoInfraestructura;
        this.idInfraestructura = idInfraestructura;
        this.tieneSitioPagoPrincipal = tieneSitioPagoPrincipal;
    }

    /** Resultado Infraestructura como InfraestructuraModeloDTO */
    private InfraestructuraModeloDTO resultadoInfraestructuraDTO;

    /**
     * @return the idInfraestructura
     */
    public Long getIdInfraestructura() {
        return idInfraestructura;
    }

    /**
     * @param idInfraestructura
     *        the idInfraestructura to set
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
     * @param codigoInfraestructura
     *        the codigoInfraestructura to set
     */
    public void setCodigoInfraestructura(String codigoInfraestructura) {
        this.codigoInfraestructura = codigoInfraestructura;
    }

    /**
     * @return the nombreInfraestructura
     */
    public String getNombreInfraestructura() {
        return nombreInfraestructura;
    }

    /**
     * @param nombreInfraestructura
     *        the nombreInfraestructura to set
     */
    public void setNombreInfraestructura(String nombreInfraestructura) {
        this.nombreInfraestructura = nombreInfraestructura;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoInfraestructura
     */
    public String getTipoInfraestructura() {
        return tipoInfraestructura;
    }

    /**
     * @param tipoInfraestructura
     *        the tipoInfraestructura to set
     */
    public void setTipoInfraestructura(String tipoInfraestructura) {
        this.tipoInfraestructura = tipoInfraestructura;
    }

    /**
     * @return the unidadCapacidadInstalada
     */
    public String getUnidadCapacidadInstalada() {
        return unidadCapacidadInstalada;
    }

    /**
     * @param unidadCapacidadInstalada
     *        the unidadCapacidadInstalada to set
     */
    public void setUnidadCapacidadInstalada(String unidadCapacidadInstalada) {
        this.unidadCapacidadInstalada = unidadCapacidadInstalada;
    }

    /**
     * @return the tenencia
     */
    public String getTenencia() {
        return tenencia;
    }

    /**
     * @param tenencia
     *        the tenencia to set
     */
    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    /**
     * @return the departamentoInfraestructura
     */
    public String getDepartamentoInfraestructura() {
        return departamentoInfraestructura;
    }

    /**
     * @param departamentoInfraestructura
     *        the departamentoInfraestructura to set
     */
    public void setDepartamentoInfraestructura(String departamentoInfraestructura) {
        this.departamentoInfraestructura = departamentoInfraestructura;
    }

    /**
     * @return the municipioInfraestructura
     */
    public String getMunicipioInfraestructura() {
        return municipioInfraestructura;
    }

    /**
     * @param municipioInfraestructura
     *        the municipioInfraestructura to set
     */
    public void setMunicipioInfraestructura(String municipioInfraestructura) {
        this.municipioInfraestructura = municipioInfraestructura;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona
     *        the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the direccionInfraestructura
     */
    public String getDireccionInfraestructura() {
        return direccionInfraestructura;
    }

    /**
     * @param direccionInfraestructura
     *        the direccionInfraestructura to set
     */
    public void setDireccionInfraestructura(String direccionInfraestructura) {
        this.direccionInfraestructura = direccionInfraestructura;
    }

    /**
     * @return the latitudInfraestructura
     */
    public String getLatitudInfraestructura() {
        return latitudInfraestructura;
    }

    /**
     * @param latitudInfraestructura
     *        the latitudInfraestructura to set
     */
    public void setLatitudInfraestructura(String latitudInfraestructura) {
        this.latitudInfraestructura = latitudInfraestructura;
    }

    /**
     * @return the longitudInfraestructura
     */
    public String getLongitudInfraestructura() {
        return longitudInfraestructura;
    }

    /**
     * @param longitudInfraestructura
     *        the longitudInfraestructura to set
     */
    public void setLongitudInfraestructura(String longitudInfraestructura) {
        this.longitudInfraestructura = longitudInfraestructura;
    }

    /**
     * @return the resultadoInfraestructuraDTO
     */
    public InfraestructuraModeloDTO getResultadoInfraestructuraDTO() {
        return resultadoInfraestructuraDTO;
    }

    /**
     * @param resultadoInfraestructuraDTO
     *        the resultadoInfraestructuraDTO to set
     */
    public void setResultadoInfraestructuraDTO(InfraestructuraModeloDTO resultadoInfraestructuraDTO) {
        this.resultadoInfraestructuraDTO = resultadoInfraestructuraDTO;
    }

    /**
     * @return the idSitioPago
     */
    public Long getIdSitioPago() {
        return idSitioPago;
    }

    /**
     * @param idSitioPago
     *        the idSitioPago to set
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
     * @param codigoSitioPago
     *        the codigoSitioPago to set
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
     * @param nombreSitioPago
     *        the nombreSitioPago to set
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
     * @param areaGeografica
     *        the areaGeografica to set
     */
    public void setAreaGeografica(AreaGeograficaEnum areaGeografica) {
        this.areaGeografica = areaGeografica;
    }

    /**
     * @return the idDepartamentoInfraestructura
     */
    public Short getIdDepartamentoInfraestructura() {
        return idDepartamentoInfraestructura;
    }

    /**
     * @param idDepartamentoInfraestructura
     *        the idDepartamentoInfraestructura to set
     */
    public void setIdDepartamentoInfraestructura(Short idDepartamentoInfraestructura) {
        this.idDepartamentoInfraestructura = idDepartamentoInfraestructura;
    }

    /**
     * @return the idMunicipioInfraestructura
     */
    public Short getIdMunicipioInfraestructura() {
        return idMunicipioInfraestructura;
    }

    /**
     * @param idMunicipioInfraestructura
     *        the idMunicipioInfraestructura to set
     */
    public void setIdMunicipioInfraestructura(Short idMunicipioInfraestructura) {
        this.idMunicipioInfraestructura = idMunicipioInfraestructura;
    }

    /**
     * @return the idTipoInfraestructura
     */
    public Long getIdTipoInfraestructura() {
        return idTipoInfraestructura;
    }

    /**
     * @param idTipoInfraestructura
     *        the idTipoInfraestructura to set
     */
    public void setIdTipoInfraestructura(Long idTipoInfraestructura) {
        this.idTipoInfraestructura = idTipoInfraestructura;
    }

    /**
     * @return the idTenencia
     */
    public Long getIdTenencia() {
        return idTenencia;
    }

    /**
     * @param idTenencia
     *        the idTenencia to set
     */
    public void setIdTenencia(Long idTenencia) {
        this.idTenencia = idTenencia;
    }

    /**
     * @return the capacidadEstimada
     */
    public String getCapacidadEstimada() {
        return capacidadEstimada;
    }

    /**
     * @param capacidadEstimada the capacidadEstimada to set
     */
    public void setCapacidadEstimada(String capacidadEstimada) {
        this.capacidadEstimada = capacidadEstimada;
    }

    /**
     * @return the tieneSitioPago
     */
    public Boolean getTieneSitioPago() {
        return tieneSitioPago;
    }

    /**
     * @param tieneSitioPago the tieneSitioPago to set
     */
    public void setTieneSitioPago(Boolean tieneSitioPago) {
        this.tieneSitioPago = tieneSitioPago;
    }

    /**
     * @return the tieneSitioPagoPrincipal
     */
    public Boolean getTieneSitioPagoPrincipal() {
        return tieneSitioPagoPrincipal;
    }

    /**
     * @param tieneSitioPagoPrincipal the tieneSitioPagoPrincipal to set
     */
    public void setTieneSitioPagoPrincipal(Boolean tieneSitioPagoPrincipal) {
        this.tieneSitioPagoPrincipal = tieneSitioPagoPrincipal;
    }

    /**
     * @return the sitiosPagos
     */
    public List<SitioPagoModeloDTO> getSitiosPagos() {
        return sitiosPagos;
    }

    /**
     * @param sitiosPagos the sitiosPagos to set
     */
    public void setSitiosPagos(List<SitioPagoModeloDTO> sitiosPagos) {
        this.sitiosPagos = sitiosPagos;
    }
}
