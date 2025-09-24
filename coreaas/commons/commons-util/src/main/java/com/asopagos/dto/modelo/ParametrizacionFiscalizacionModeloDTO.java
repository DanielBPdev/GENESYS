package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.ParametrizacionFiscalizacion;

/**
 * DTO que representa el modelo de Parametrización de fiscalización
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ParametrizacionFiscalizacionModeloDTO extends ParametrizacionCarteraModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = -7628877192864330331L;

    /**
     * Alertas de validaciones en la planilla PILA.
     */
    private Boolean alertaValidacionPila;

    /**
     * Aportantes con “Estado de aportes” igual a “No OK”
     */
    private Boolean estadoNoOk;

    /**
     * Trabajadores con Salario menor al último salario reportado por el trabajador
     * para ese aportante
     */
    private Boolean salarioMenorUltimo;

    /**
     * Trabajadores con IBC menor al último IBC reportado por el trabajador para ese
     * aportante.
     */
    private Boolean ibcMenorUltimo;

    /**
     * Aportantes que reportaron una novedad de retiro “RET” para períodos retroactivos
     */
    private Boolean novedadRetiro;

    /**
     * Cantidad de periodos retroactivos
     */
    private Short periodosRetroactivos;
    
    /**
     * Número que define la cantidad de entidades que se
     * seleccionaran que cumplan con los criterios de prioridad de 0 a 500.000
     */
    private Long corteEntidades;
    
    /**
     * Alerta desde Gestión Preventiva con valor true o false
     */
    private Boolean gestionPreventiva;      

    /**
     * Método constructor
     */
    public ParametrizacionFiscalizacionModeloDTO() {

    }
    
    /**
     * Método que retorna el valor de gestionPreventiva.
     * @return valor de gestionPreventiva.
     */
    public Boolean getGestionPreventiva() {
        return gestionPreventiva;
    }

    /**
     * Método encargado de modificar el valor de gestionPreventiva.
     * @param valor para modificar gestionPreventiva.
     */
    public void setGestionPreventiva(Boolean gestionPreventiva) {
        this.gestionPreventiva = gestionPreventiva;
    }

    /**
     * @return the alertaValidacionPila
     */
    public Boolean getAlertaValidacionPila() {
        return alertaValidacionPila;
    }

    /**
     * @param alertaValidacionPila
     *        the alertaValidacionPila to set
     */
    public void setAlertaValidacionPila(Boolean alertaValidacionPila) {
        this.alertaValidacionPila = alertaValidacionPila;
    }

    /**
     * @return the estadoNoOk
     */
    public Boolean getEstadoNoOk() {
        return estadoNoOk;
    }

    /**
     * @param estadoNoOk
     *        the estadoNoOk to set
     */
    public void setEstadoNoOk(Boolean estadoNoOk) {
        this.estadoNoOk = estadoNoOk;
    }

    /**
     * @return the salarioMenorUltimo
     */
    public Boolean getSalarioMenorUltimo() {
        return salarioMenorUltimo;
    }

    /**
     * @param salarioMenorUltimo
     *        the salarioMenorUltimo to set
     */
    public void setSalarioMenorUltimo(Boolean salarioMenorUltimo) {
        this.salarioMenorUltimo = salarioMenorUltimo;
    }

    /**
     * @return the ibcMenorUltimo
     */
    public Boolean getIbcMenorUltimo() {
        return ibcMenorUltimo;
    }

    /**
     * @param ibcMenorUltimo
     *        the ibcMenorUltimo to set
     */
    public void setIbcMenorUltimo(Boolean ibcMenorUltimo) {
        this.ibcMenorUltimo = ibcMenorUltimo;
    }

    /**
     * @return the novedadRetiro
     */
    public Boolean getNovedadRetiro() {
        return novedadRetiro;
    }

    /**
     * @param novedadRetiro
     *        the novedadRetiro to set
     */
    public void setNovedadRetiro(Boolean novedadRetiro) {
        this.novedadRetiro = novedadRetiro;
    }

    /**
     * @return the periodosRetroactivos
     */
    public Short getPeriodosRetroactivos() {
        return periodosRetroactivos;
    }

    /**
     * @param periodosRetroactivos
     *        the periodosRetroactivos to set
     */
    public void setPeriodosRetroactivos(Short periodosRetroactivos) {
        this.periodosRetroactivos = periodosRetroactivos;
    }
    
    /**
     * @return the corteEntidades
     */
    public Long getCorteEntidades() {
        return corteEntidades;
    }

    /**
     * @param corteEntidades the corteEntidades to set
     */
    public void setCorteEntidades(Long corteEntidades) {
        this.corteEntidades = corteEntidades;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso ParametrizacionFiscalizacionModeloDTO
     * @param parametrizacionFiscalizacion
     *        recibe la entity
     * @return devuelve un objeto DTO ParametrizacionFiscalizacionModeloDTO
     */
    public ParametrizacionFiscalizacionModeloDTO convertToDTO(ParametrizacionFiscalizacion parametrizacionFiscalizacion) {
        super.convertToDTO(parametrizacionFiscalizacion);
        /* Se setean la información al objeto ParametrizacionFiscalizacionModeloDTO */
        this.setAlertaValidacionPila(parametrizacionFiscalizacion.getAlertaValidacionPila());
        this.setEstadoNoOk(parametrizacionFiscalizacion.getEstadoNoOk());
        this.setSalarioMenorUltimo(parametrizacionFiscalizacion.getSalarioMenorUltimo());
        this.setIbcMenorUltimo(parametrizacionFiscalizacion.getIbcMenorUltimo());
        this.setNovedadRetiro(parametrizacionFiscalizacion.getNovedadRetiro());
        this.setPeriodosRetroactivos(parametrizacionFiscalizacion.getPeriodosRetroactivos());
        this.setCorteEntidades(parametrizacionFiscalizacion.getCorteEntidades());
        this.setGestionPreventiva(parametrizacionFiscalizacion.getGestionPreventiva());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso ParametrizacionFiscalizacion
     * @return devuelve un objeto Entity de ParametrizacionFiscalizacion
     */
    public ParametrizacionFiscalizacion convertToParametrizacionFiscalizacionEntity() {
        /* Se instancia objeto ParametrizacionFiscalizacion */
        ParametrizacionFiscalizacion parametrizacionFiscalizacion = new ParametrizacionFiscalizacion();
        /* Se carga información de la clase padre */
        parametrizacionFiscalizacion = (ParametrizacionFiscalizacion) super.convertToEntity(parametrizacionFiscalizacion);
        /* Se setan los valores a parametrizacionFiscalizacion */
        parametrizacionFiscalizacion.setAlertaValidacionPila(this.getAlertaValidacionPila());
        parametrizacionFiscalizacion.setEstadoNoOk(this.getEstadoNoOk());
        parametrizacionFiscalizacion.setSalarioMenorUltimo(this.getSalarioMenorUltimo());
        parametrizacionFiscalizacion.setIbcMenorUltimo(this.getIbcMenorUltimo());
        parametrizacionFiscalizacion.setNovedadRetiro(this.getNovedadRetiro());
        parametrizacionFiscalizacion.setPeriodosRetroactivos(this.getPeriodosRetroactivos());
        parametrizacionFiscalizacion.setCorteEntidades(this.getCorteEntidades());
        parametrizacionFiscalizacion.setGestionPreventiva(this.getGestionPreventiva());
        return parametrizacionFiscalizacion;
    }

}
