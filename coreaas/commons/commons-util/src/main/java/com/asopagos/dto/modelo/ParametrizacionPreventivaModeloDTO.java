package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.cartera.ParametrizacionPreventiva;

/**
 * DTO que representa el modelo de Paramatrización Preventiva.
 *
 * @author Claudia Milena Marín <a href="mailto:clmarin@heinsohn.com.co">
 */
public class ParametrizacionPreventivaModeloDTO extends ParametrizacionCarteraModeloDTO implements Serializable{

    /**
     * Serial version
     */
    private static final long serialVersionUID = 3147137714300883340L;
    /**
     * Cantidad de días hábiles previos a la fecha de vencimiento del plazo de pago de
     * aportes para ejecutar este proceso.
     */
    private Short diasHabilesPrevios;
    /**
     * Hora de ejecución
     */
    private Long horaEjecucion;
    /**
     * Atributo que indica si la gestión preventiva se realiza o no de manera automática.
     */
    private Boolean ejecucionAutomatica;

    /**
     * Método encargado de convertir de Entidad a DTO
     * @param parametrizacionPreventiva
     *        entidad a convertir.
     * @return DTO convertido 
     */
    public ParametrizacionPreventivaModeloDTO convertToDTO(ParametrizacionPreventiva parametrizacionPreventiva) {
        super.convertToDTO(parametrizacionPreventiva);
        this.setDiasHabilesPrevios(parametrizacionPreventiva.getDiasHabilesPrevios());
        if(parametrizacionPreventiva.getHoraEjecucion()!=null){
            this.setHoraEjecucion(parametrizacionPreventiva.getHoraEjecucion().getTime());
        }
        this.setEjecucionAutomatica(parametrizacionPreventiva.getEjecucionAutomatica());
        return this;
    }
    
    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public ParametrizacionPreventiva convertToParametrizacionPreventivaEntity() {
        /*Se crea instancia del objeto ParametrizacionPreventiva*/
        ParametrizacionPreventiva parametrizacionPreventiva = new ParametrizacionPreventiva();
        parametrizacionPreventiva = (ParametrizacionPreventiva) super.convertToEntity(parametrizacionPreventiva);
        parametrizacionPreventiva.setDiasHabilesPrevios(this.getDiasHabilesPrevios());
        if(this.getHoraEjecucion()!=null){
            parametrizacionPreventiva.setHoraEjecucion(new Date(this.getHoraEjecucion()));
        }
        parametrizacionPreventiva.setEjecucionAutomatica(this.getEjecucionAutomatica());
        return parametrizacionPreventiva;

    }

    /**
     * Método que retorna el valor de diasHabilesPrevios.
     * @return valor de diasHabilesPrevios.
     */
    public Short getDiasHabilesPrevios() {
        return diasHabilesPrevios;
    }

    /**
     * Método encargado de modificar el valor de diasHabilesPrevios.
     * @param valor para modificar diasHabilesPrevios.
     */
    public void setDiasHabilesPrevios(Short diasHabilesPrevios) {
        this.diasHabilesPrevios = diasHabilesPrevios;
    }

    /**
     * Método que retorna el valor de horaEjecucion.
     * @return valor de horaEjecucion.
     */
    public Long getHoraEjecucion() {
        return horaEjecucion;
    }

    /**
     * Método encargado de modificar el valor de horaEjecucion.
     * @param valor para modificar horaEjecucion.
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     * Método que retorna el valor de ejecucionAutomatica.
     * @return valor de ejecucionAutomatica.
     */
    public Boolean getEjecucionAutomatica() {
        return ejecucionAutomatica;
    }

    /**
     * Método encargado de modificar el valor de ejecucionAutomatica.
     * @param valor para modificar ejecucionAutomatica.
     */
    public void setEjecucionAutomatica(Boolean ejecucionAutomatica) {
        this.ejecucionAutomatica = ejecucionAutomatica;
    }


   
}
