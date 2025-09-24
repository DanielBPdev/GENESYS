package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;

/**
 * DTO que contiene los datos temporales usados en la parametrización de los criterios de gestion de cobro.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @updated 02-Feb.-2018 08:44:50 p.m.
 */
@XmlRootElement
public class CriteriosParametrizacionTemporalDTO implements Serializable {

    /**
     * Atributo que indica si la linea de cobro 1 habilita la parametrización manual.
     */
    private Boolean manual;
    /**
     * Atributo que indica el método elegido para la linea de cobro 1.
     */
    private MetodoAccionCobroEnum metodo;
    /**
     * Atributo que indica si la línea de cobro 2 está activa.
     */
    private Boolean lc2;
    /**
     * Atributo que indica las acciones de la linea de cobro 2.
     */
    private List<TipoGestionCarteraEnum> accionesLc2;
    /**
     * Atributo que indica si la línea de cobro 3 está activa.
     */
    private Boolean lc3;
    /**
     * Atributo que indica las acciones de la linea de cobro 3.
     */
    private List<TipoGestionCarteraEnum> accionesLc3;
    /**
     * Atributo que indica si la línea de cobro 4 está activa.
     */
    private Boolean lc4;
    /**
     * Atributo que indica las acciones de la linea de cobro 4.
     */
    private List<TipoGestionCarteraEnum> accionesLc4;
    /**
     * Atributo que indica si la línea de cobro 5 está activa.
     */
    private Boolean lc5;
    /**
     * Atributo que indica las acciones de la linea de cobro 5.
     */
    private List<TipoGestionCarteraEnum> accionesLc5;
    
    /**
     * Método de parametrización que se tenia anteriormente 
     * Solución Mantis: 0236016
     */
    private MetodoAccionCobroEnum metodoAnterior;
    /**
     * Atributo que contiene las parametrizaciones realizadas.
     */
    private List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizaciones;
    
    /**
     * Método encargado de copiar la información de un dto nuevo al que ya existe
     * @param criterioParametrizacionExistente, criterio parametrización existente 
     * @param criterioParametrizacionDTONuevo, criterio parametrización nuevo 
     */
    public CriteriosParametrizacionTemporalDTO copyDTOToDTO(CriteriosParametrizacionTemporalDTO criterioParametrizacionExistente,CriteriosParametrizacionTemporalDTO criterioParametrizacionDTONuevo){
        if (criterioParametrizacionDTONuevo.getManual() != null) {
            criterioParametrizacionExistente.setManual(criterioParametrizacionDTONuevo.getManual());
        }
        if (criterioParametrizacionDTONuevo.getMetodo() != null) {
            criterioParametrizacionExistente.setMetodo(criterioParametrizacionDTONuevo.getMetodo());
        }
        if (criterioParametrizacionDTONuevo.getMetodoAnterior() != null) {
            criterioParametrizacionExistente.setMetodoAnterior(criterioParametrizacionDTONuevo.getMetodoAnterior());
        }
        if (criterioParametrizacionDTONuevo.getLc2() != null) {
            criterioParametrizacionExistente.setLc2(criterioParametrizacionDTONuevo.getLc2());
        }
        if (criterioParametrizacionDTONuevo.getAccionesLc2() != null) {
            criterioParametrizacionExistente.setAccionesLc2(criterioParametrizacionDTONuevo.getAccionesLc2());
        }
        if (criterioParametrizacionDTONuevo.getLc3() != null) {
            criterioParametrizacionExistente.setLc3(criterioParametrizacionDTONuevo.getLc3());
        } 
        if (criterioParametrizacionDTONuevo.getAccionesLc3() != null) {
            criterioParametrizacionExistente.setAccionesLc3(criterioParametrizacionDTONuevo.getAccionesLc3());
        }
        if (criterioParametrizacionDTONuevo.getLc4() != null) {
            criterioParametrizacionExistente.setLc4(criterioParametrizacionDTONuevo.getLc4());
        }
        if (criterioParametrizacionDTONuevo.getAccionesLc4() != null) {
            criterioParametrizacionExistente.setAccionesLc4(criterioParametrizacionDTONuevo.getAccionesLc4());
        }
        if (criterioParametrizacionDTONuevo.getLc5() != null) {
            criterioParametrizacionExistente.setLc5(criterioParametrizacionDTONuevo.getLc5());
        }
        if (criterioParametrizacionDTONuevo.getAccionesLc5() != null) {
            criterioParametrizacionExistente.setAccionesLc5(criterioParametrizacionDTONuevo.getAccionesLc5());
        }
        if (criterioParametrizacionDTONuevo.getParametrizaciones() != null) {
            criterioParametrizacionExistente.setParametrizaciones(criterioParametrizacionDTONuevo.getParametrizaciones());
        }
        return criterioParametrizacionExistente;
    }
    
    /**
     * Método que retorna el valor de manual.
     * @return valor de manual.
     */
    public Boolean getManual() {
        return manual;
    }
    /**
     * Método que retorna el valor de metodo.
     * @return valor de metodo.
     */
    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }
    /**
     * Método que retorna el valor de lc2.
     * @return valor de lc2.
     */
    public Boolean getLc2() {
        return lc2;
    }
    /**
     * Método que retorna el valor de accionesLc2.
     * @return valor de accionesLc2.
     */
    public List<TipoGestionCarteraEnum> getAccionesLc2() {
        return accionesLc2;
    }
    /**
     * Método que retorna el valor de lc3.
     * @return valor de lc3.
     */
    public Boolean getLc3() {
        return lc3;
    }
    /**
     * Método que retorna el valor de accionesLc3.
     * @return valor de accionesLc3.
     */
    public List<TipoGestionCarteraEnum> getAccionesLc3() {
        return accionesLc3;
    }
    /**
     * Método que retorna el valor de lc4.
     * @return valor de lc4.
     */
    public Boolean getLc4() {
        return lc4;
    }
    /**
     * Método que retorna el valor de accionesLc4.
     * @return valor de accionesLc4.
     */
    public List<TipoGestionCarteraEnum> getAccionesLc4() {
        return accionesLc4;
    }
    /**
     * Método que retorna el valor de lc5.
     * @return valor de lc5.
     */
    public Boolean getLc5() {
        return lc5;
    }
    /**
     * Método que retorna el valor de accionesLc5.
     * @return valor de accionesLc5.
     */
    public List<TipoGestionCarteraEnum> getAccionesLc5() {
        return accionesLc5;
    }
    /**
     * Método encargado de modificar el valor de manual.
     * @param valor para modificar manual.
     */
    public void setManual(Boolean manual) {
        this.manual = manual;
    }
    /**
     * Método encargado de modificar el valor de metodo.
     * @param valor para modificar metodo.
     */
    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }
    /**
     * Método encargado de modificar el valor de lc2.
     * @param valor para modificar lc2.
     */
    public void setLc2(Boolean lc2) {
        this.lc2 = lc2;
    }
    /**
     * Método encargado de modificar el valor de accionesLc2.
     * @param valor para modificar accionesLc2.
     */
    public void setAccionesLc2(List<TipoGestionCarteraEnum> accionesLc2) {
        this.accionesLc2 = accionesLc2;
    }
    /**
     * Método encargado de modificar el valor de lc3.
     * @param valor para modificar lc3.
     */
    public void setLc3(Boolean lc3) {
        this.lc3 = lc3;
    }
    /**
     * Método encargado de modificar el valor de accionesLc3.
     * @param valor para modificar accionesLc3.
     */
    public void setAccionesLc3(List<TipoGestionCarteraEnum> accionesLc3) {
        this.accionesLc3 = accionesLc3;
    }
    /**
     * Método encargado de modificar el valor de lc4.
     * @param valor para modificar lc4.
     */
    public void setLc4(Boolean lc4) {
        this.lc4 = lc4;
    }
    /**
     * Método encargado de modificar el valor de accionesLc4.
     * @param valor para modificar accionesLc4.
     */
    public void setAccionesLc4(List<TipoGestionCarteraEnum> accionesLc4) {
        this.accionesLc4 = accionesLc4;
    }
    /**
     * Método encargado de modificar el valor de lc5.
     * @param valor para modificar lc5.
     */
    public void setLc5(Boolean lc5) {
        this.lc5 = lc5;
    }
    /**
     * Método encargado de modificar el valor de accionesLc5.
     * @param valor para modificar accionesLc5.
     */
    public void setAccionesLc5(List<TipoGestionCarteraEnum> accionesLc5) {
        this.accionesLc5 = accionesLc5;
    }
    /**
     * Método que retorna el valor de parametrizaciones.
     * @return valor de parametrizaciones.
     */
    public List<ParametrizacionCriteriosGestionCobroModeloDTO> getParametrizaciones() {
        return parametrizaciones;
    }
    /**
     * Método encargado de modificar el valor de parametrizaciones.
     * @param valor para modificar parametrizaciones.
     */
    public void setParametrizaciones(List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizaciones) {
        this.parametrizaciones = parametrizaciones;
    }

    /**
     * Método que retorna el valor de metodoAnterior.
     * @return valor de metodoAnterior.
     */
    public MetodoAccionCobroEnum getMetodoAnterior() {
        return metodoAnterior;
    }

    /**
     * Método encargado de modificar el valor de metodoAnterior.
     * @param valor para modificar metodoAnterior.
     */
    public void setMetodoAnterior(MetodoAccionCobroEnum metodoAnterior) {
        this.metodoAnterior = metodoAnterior;
    }
    
    
}