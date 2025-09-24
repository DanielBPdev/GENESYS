package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.cartera.ConvenioPagoDependiente;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * DTO de la entidad ConvenioPagoDependientes
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class ConvenioPagoDependienteModeloDTO {

    /**
     * Atributo que representa el id de la persona
     */
    private Long idPersona;

    /**
     * Atributo que representa el id de convenio de pago
     */
    private Long idPagoPeriodoConvenio;

    /**
     * Constructor de la clase
     */
    public ConvenioPagoDependienteModeloDTO() {
    }

    /**
     * Metodo que se encarga de convertir de entidad a DTO
     * @param convenioPagoDependientes
     *        recibe como parametro la entidad
     * @return un objeto DTO
     */
    public ConvenioPagoDependienteModeloDTO convertToDTO(ConvenioPagoDependiente convenioPagoDependientes) {
        /* Seteo de atributos con respecto a la entidad */
        this.setIdPersona(convenioPagoDependientes.getIdPersona());
        this.setIdPagoPeriodoConvenio(convenioPagoDependientes.getIdPagoPeriodoConvenio());
        return this;
    }

    /**
     * Metodo que se encarga de convertir de DTO a entidad
     * @return un objeto Entidad
     */
    public ConvenioPagoDependiente convertEntity() {
        ConvenioPagoDependiente convenioPagoDependientes = new ConvenioPagoDependiente();
        /* Seteo de atributos con respecto a al DTO */
        convenioPagoDependientes.setIdPersona(this.getIdPersona());
        convenioPagoDependientes.setIdPagoPeriodoConvenio(this.getIdPagoPeriodoConvenio());
        return convenioPagoDependientes;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de idPagoConvenio.
     * @return valor de idPagoConvenio.
     */
    public Long getIdPagoPeriodoConvenio() {
        return idPagoPeriodoConvenio;
    }

    /**
     * Método encargado de modificar el valor de idPagoConvenio.
     * @param valor
     *        para modificar idPagoConvenio.
     */
    public void setIdPagoPeriodoConvenio(Long idPagoPeriodoConvenio) {
        this.idPagoPeriodoConvenio = idPagoPeriodoConvenio;
    }

}
