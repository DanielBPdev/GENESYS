package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de la fiscalizacion relacionada a un aportante.
 * @author Claudia Milena Marín <clmarin@heinsohn.com.co>
 * @created 29-sept.-2017 3:37:37 p.m.
 */
public class FiscalizacionAportanteDTO implements Serializable {

    /**
     * Serial de la version UID
     */
    private static final long serialVersionUID = 7822289712236335118L;
    /**
     * Nombre del analista el cual tiene asignada la fiscalización
     */
    private String analista;
    /**
     * Número único que registra para la fiscalización de un aportante
     */
    private Long numeroOperacion;
    /**
     * Tipo solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    /**
     * Tipo de identificación del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación del aportante
     */
    private String numeroIdentificacion;
    /**
     * Nombre completo o razon social del aportante
     */
    private String nombreRazonSocial;
    /**
     * Estado de la deuda para el aportante
     */
    private TipoDeudaEnum estado;
    /**
     * Estado de cartera que puede tomar los valores al día, moroso
     */
    private EstadoCarteraEnum estadoCartera;
    /**
     * Es el estado de fiscalizacion para el aportante que puede tener valores como
     * Asignado, En Proceso, Finalizada, Excluida
     */
    private EstadoFiscalizacionEnum estadoFiscalizacion;
    /**
     * El valor promedio de aportes durante los  últimos periodos
     */
    private BigDecimal valorPromedioAportes;
    /**
     * Cantidad de trabajadores activos
     */
    private Short trabajadoresActivo;
    /**
     * Representa la cantidad de veces que un aportante ha estado moroso durante los
     * ultimos periodos
     */
    private Short vecesMoroso;

    /**
     * @param analista
     * @param numeroOperacion
     * @param tipoSolicitante
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param nombreRazonSocial
     * @param estado
     * @param estadoCartera
     * @param estadoFiscalizacion
     */
    public FiscalizacionAportanteDTO(String analista, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String nombreRazonSocial) {
        super();
        this.analista = analista;
        this.tipoSolicitante = tipoSolicitante;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreRazonSocial = nombreRazonSocial;
    }

    /**
     * Método que retorna el valor de analista.
     * @return valor de analista.
     */
    public String getAnalista() {
        return analista;
    }

    /**
     * Método encargado de modificar el valor de analista.
     * @param valor
     *        para modificar analista.
     */
    public void setAnalista(String analista) {
        this.analista = analista;
    }

    /**
     * Método que retorna el valor de numeroOperacion.
     * @return valor de numeroOperacion.
     */
    public Long getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * Método encargado de modificar el valor de numeroOperacion.
     * @param valor
     *        para modificar numeroOperacion.
     */
    public void setNumeroOperacion(Long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de nombreRazonSocial.
     * @return valor de nombreRazonSocial.
     */
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    /**
     * Método encargado de modificar el valor de nombreRazonSocial.
     * @param valor
     *        para modificar nombreRazonSocial.
     */
    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    /**
     * Método que retorna el valor de estado.
     * @return valor de estado.
     */
    public TipoDeudaEnum getEstado() {
        return estado;
    }

    /**
     * Método encargado de modificar el valor de estado.
     * @param valor
     *        para modificar estado.
     */
    public void setEstado(TipoDeudaEnum estado) {
        this.estado = estado;
    }

    /**
     * Método que retorna el valor de estadoCartera.
     * @return valor de estadoCartera.
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoCartera.
     * @param valor
     *        para modificar estadoCartera.
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Método que retorna el valor de estadoFiscalizacion.
     * @return valor de estadoFiscalizacion.
     */
    public EstadoFiscalizacionEnum getEstadoFiscalizacion() {
        return estadoFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de estadoFiscalizacion.
     * @param valor
     *        para modificar estadoFiscalizacion.
     */
    public void setEstadoFiscalizacion(EstadoFiscalizacionEnum estadoFiscalizacion) {
        this.estadoFiscalizacion = estadoFiscalizacion;
    }

    /**
     * Método que retorna el valor de valorPromedioAportes.
     * @return valor de valorPromedioAportes.
     */
    public BigDecimal getValorPromedioAportes() {
        return valorPromedioAportes;
    }

    /**
     * Método encargado de modificar el valor de valorPromedioAportes.
     * @param valor
     *        para modificar valorPromedioAportes.
     */
    public void setValorPromedioAportes(BigDecimal valorPromedioAportes) {
        this.valorPromedioAportes = valorPromedioAportes;
    }

    /**
     * Método que retorna el valor de trabajadoresActivo.
     * @return valor de trabajadoresActivo.
     */
    public Short getTrabajadoresActivo() {
        return trabajadoresActivo;
    }

    /**
     * Método encargado de modificar el valor de trabajadoresActivo.
     * @param valor
     *        para modificar trabajadoresActivo.
     */
    public void setTrabajadoresActivo(Short trabajadoresActivo) {
        this.trabajadoresActivo = trabajadoresActivo;
    }

    /**
     * Método que retorna el valor de vecesMoroso.
     * @return valor de vecesMoroso.
     */
    public Short getVecesMoroso() {
        return vecesMoroso;
    }

    /**
     * Método encargado de modificar el valor de vecesMoroso.
     * @param valor
     *        para modificar vecesMoroso.
     */
    public void setVecesMoroso(Short vecesMoroso) {
        this.vecesMoroso = vecesMoroso;
    }

    public FiscalizacionAportanteDTO() {
        // TODO Auto-generated constructor stub
    }
}