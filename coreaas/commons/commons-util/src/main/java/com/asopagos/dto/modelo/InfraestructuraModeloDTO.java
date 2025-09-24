package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.ccf.core.Infraestructura;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;

/**
 * <b>Descripcion:</b> DTO que representa al Entity Infraestructura <br/>
 * <b>Módulo:</b> Asopagos - Control de Cambios 0227134 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class InfraestructuraModeloDTO implements Serializable {
    private static final long serialVersionUID = 3989285145303168837L;

    private static final Object SEPARADOR_CODIGO = "-";

    /** Identificador único del registro */
    private Long id;

    /** Código de la infraestructura definido por la CCF de acuerdo a lineamiento de la SSF (código CCF 6 dígitos) */
    private String codigoParaSSF;

    /** Código de la infraestructura definido por la CCF para uso interno (código CCF 5 dígitos) */
    private String codigoParaCCF;

    /** Consecutivo de la infraestructura */
    private Short consecutivoInfraestructura;

    /** Nombre de la infraestructura */
    private String nombre;

    /** Referencia al tipo de Infraestructura */
    private Long tipoInfraestructura;

    /** Descripción de la zona en la que se encuentra la infraestructura */
    private String zona;

    /** Dirección física de la infraestructura */
    private String direccion;

    /** Descripción del área geográfica en la que se encuentra ubicada la infraestructura */
    private AreaGeograficaEnum areaGeografica;

    /** Municipio en el que se encuentra ubicada la infraestructura */
    private Long municipio;

    /** Tipo de tenencia de la infraestructura */
    private Long tipoTenencia;

    /** Latitud geográfica de la infraestructura */
    private BigDecimal latitud;

    /** Longitud geográfica de la infraestructura */
    private BigDecimal longitud;
    
    /** Capacidad estimada para la infraestructura (de acuerdo al tipo de infraestructura seleccionado)*/
    private BigDecimal capacidadEstimada;

    /** Indicador de registro activo */
    private Boolean activo;

    /** Método para convertir de entidad a DTO */
    public void convertToDTO(Infraestructura infraestructura) {
        this.id = infraestructura.getId();
        this.codigoParaSSF = infraestructura.getCodigoParaSSF();
        this.codigoParaCCF = infraestructura.getCodigoParaCCF();
        this.consecutivoInfraestructura = infraestructura.getConsecutivoInfraestructura();
        this.nombre = infraestructura.getNombre();
        this.tipoInfraestructura = infraestructura.getTipoInfraestructura();
        this.zona = infraestructura.getZona();
        this.direccion = infraestructura.getDireccion();
        this.areaGeografica = infraestructura.getAreaGeografica();
        this.municipio = infraestructura.getMunicipio();
        this.tipoTenencia = infraestructura.getTipoTenencia();
        this.latitud = infraestructura.getLatitud();
        this.longitud = infraestructura.getLongitud();
        this.capacidadEstimada = infraestructura.getCapacidadEstimada();
        this.activo = infraestructura.getActivo();
    }

    /** Método para convertir de DTO a Entity */
    public Infraestructura convertToEntity() {
        Infraestructura infraestructura = new Infraestructura();
        infraestructura.setId(this.getId());
        infraestructura.setCodigoParaSSF(this.getCodigoParaSSF());
        infraestructura.setCodigoParaCCF(this.getCodigoParaCCF());
        infraestructura.setConsecutivoInfraestructura(this.getConsecutivoInfraestructura());
        infraestructura.setNombre(this.getNombre());
        infraestructura.setTipoInfraestructura(this.getTipoInfraestructura());
        infraestructura.setZona(this.getZona());
        infraestructura.setDireccion(this.getDireccion());
        infraestructura.setAreaGeografica(this.getAreaGeografica());
        infraestructura.setMunicipio(this.getMunicipio());
        infraestructura.setTipoTenencia(this.getTipoTenencia());
        infraestructura.setLatitud(this.getLatitud());
        infraestructura.setLongitud(this.getLongitud());
        infraestructura.setCapacidadEstimada(this.getCapacidadEstimada());
        infraestructura.setActivo(this.getActivo());
        return infraestructura;
    }

    /**
     * Método para crear el código de infraestructura de acuerdo al código de la CCF, el código del tipo de infraestructura y el siguiente
     * valor de consecutivo de infraestructura
     * @param codigoCCF
     *        Código de la CCF asignado por la SSF
     * @param codigoTipoInfraestructura
     *        Código SSF del tipo de infraestructura
     * @param consecutivo
     *        Consecutivo para identificación de infraestructuras
     */
    public void construirCodigosInfraestructura(String codigoCCF, String codigoTipoInfraestructura, String consecutivo) {
        StringBuilder codigoBuilderFinal = new StringBuilder();
        StringBuilder codigoBuilderCCF = new StringBuilder(codigoCCF);
        
        // se crean las versión del código de la CCF para reporte a SSF
        String codigoSSF = codigoCCF.replace("CCF", "CCF0");
        StringBuilder codigoBuilderSSF = new StringBuilder(codigoSSF);
        
        codigoBuilderFinal.append(SEPARADOR_CODIGO);
        codigoBuilderFinal.append(codigoTipoInfraestructura);
        codigoBuilderFinal.append(SEPARADOR_CODIGO);
        codigoBuilderFinal.append(consecutivo);
        
        codigoBuilderCCF.append(codigoBuilderFinal);
        codigoBuilderSSF.append(codigoBuilderFinal);

        this.codigoParaSSF = codigoBuilderSSF.toString();
        this.codigoParaCCF = codigoBuilderCCF.toString();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigoParaSSF
     */
    public String getCodigoParaSSF() {
        return codigoParaSSF;
    }

    /**
     * @param codigoParaSSF the codigoParaSSF to set
     */
    public void setCodigoParaSSF(String codigoParaSSF) {
        this.codigoParaSSF = codigoParaSSF;
    }

    /**
     * @return the codigoParaCCF
     */
    public String getCodigoParaCCF() {
        return codigoParaCCF;
    }

    /**
     * @param codigoParaCCF the codigoParaCCF to set
     */
    public void setCodigoParaCCF(String codigoParaCCF) {
        this.codigoParaCCF = codigoParaCCF;
    }

    /**
     * @return the consecutivoInfraestructura
     */
    public Short getConsecutivoInfraestructura() {
        return consecutivoInfraestructura;
    }

    /**
     * @param consecutivoInfraestructura the consecutivoInfraestructura to set
     */
    public void setConsecutivoInfraestructura(Short consecutivoInfraestructura) {
        this.consecutivoInfraestructura = consecutivoInfraestructura;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
     * @return the municipio
     */
    public Long getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Long municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the tipoTenencia
     */
    public Long getTipoTenencia() {
        return tipoTenencia;
    }

    /**
     * @param tipoTenencia the tipoTenencia to set
     */
    public void setTipoTenencia(Long tipoTenencia) {
        this.tipoTenencia = tipoTenencia;
    }

    /**
     * @return the latitud
     */
    public BigDecimal getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public BigDecimal getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the capacidadEstimada
     */
    public BigDecimal getCapacidadEstimada() {
        return capacidadEstimada;
    }

    /**
     * @param capacidadEstimada the capacidadEstimada to set
     */
    public void setCapacidadEstimada(BigDecimal capacidadEstimada) {
        this.capacidadEstimada = capacidadEstimada;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
