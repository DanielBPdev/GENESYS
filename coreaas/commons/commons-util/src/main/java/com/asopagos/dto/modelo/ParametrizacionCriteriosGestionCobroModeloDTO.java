package com.asopagos.dto.modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.ParametrizacionCartera;
import com.asopagos.entidades.ccf.cartera.ParametrizacionCriterioGestionCobro;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionCarteraEnum;

/**
 * Entidad que contiene los datos para una parametrización de los criterios de la Gestión de Cobro
 * HU-TRA-229 Parametrizar criterios gestión cobro.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @created 19-01-2018 8:18:26 a.m.
 */
@XmlRootElement
public class ParametrizacionCriteriosGestionCobroModeloDTO extends ParametrizacionCarteraModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -380535584099272537L;
    /**
     * Atributo que indica la linea de cobro.
     */
    private TipoLineaCobroEnum lineaCobro;
    /**
     * Atributo que indica si la parametrización de la línea esta activa o no.
     */
    private Boolean activa;
    /**
     * Atributo que inidica que método se activó para la linea de cobro 1.
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * Atributo que indica si se activa la modalidad automática.
     */
    private TipoGestionCarteraEnum accion;

    /**
     * Número que define la cantidad de entidades que se
     * seleccionaran que cumplan con los criterios de prioridad de 0 a 500.000
     */
    private Long corteEntidades;
    
    /**
     * 
     */
    public ParametrizacionCriteriosGestionCobroModeloDTO() {
     
    }
    
    /**
     * Método constructor 
     */
    public ParametrizacionCriteriosGestionCobroModeloDTO(ParametrizacionCriterioGestionCobro parametrizacionGestionCobro){
        super.convertToDTO(parametrizacionGestionCobro); 
        this.setLineaCobro(parametrizacionGestionCobro.getLineaCobro());
        this.setActiva(parametrizacionGestionCobro.getActiva());
        this.setMetodo(parametrizacionGestionCobro.getMetodo());
        this.setAccion(parametrizacionGestionCobro.getAccion());
        this.setCorteEntidades(parametrizacionGestionCobro.getCorteEntidades());
    }
       
    /**
     * Método encargado de convertir un dto de ParametrizacionCriteriosGestionCobroModeloDTO a una entidad 
     */ 
    public ParametrizacionCriterioGestionCobro convertToEntity(){
        ParametrizacionCriterioGestionCobro parametrizacionGestionCobro = new ParametrizacionCriterioGestionCobro();
        parametrizacionGestionCobro = (ParametrizacionCriterioGestionCobro) super.convertToEntity(parametrizacionGestionCobro);
        if (this.getLineaCobro() != null) {
            parametrizacionGestionCobro.setLineaCobro(this.getLineaCobro());
        }
        if (this.getActiva() != null) {
            parametrizacionGestionCobro.setActiva(this.getActiva());
        }
        if (this.getMetodo() != null) {
            parametrizacionGestionCobro.setMetodo(this.getMetodo());
        }
        if (this.getAccion() != null) {
            parametrizacionGestionCobro.setAccion(this.getAccion());
        }
        if (this.getCorteEntidades() != null) {
            parametrizacionGestionCobro.setCorteEntidades(this.getCorteEntidades());
        }
        return parametrizacionGestionCobro;
    }
    
    /** 
     * Asociar los datos del DTO a la Entidad 
     * @return ParametrizacionCartera
     */ 
    public ParametrizacionCartera convertParametrizicionCarteraEntity(ParametrizacionCartera parametrizacionCartera) {
        if (TipoParametrizacionCarteraEnum.GESTION_COBRO.equals(parametrizacionCartera.getTipoParametrizacion())) {
            ParametrizacionCriterioGestionCobro parametrizacion = new ParametrizacionCriterioGestionCobro();
            if (this.getLineaCobro() != null) {
                parametrizacion.setLineaCobro(this.getLineaCobro());
            }
            if (this.getActiva() != null) {
                parametrizacion.setActiva(this.getActiva());
            }
            if (this.getMetodo() != null) {
                parametrizacion.setMetodo(this.getMetodo());
            }
            if (this.getAccion() != null) {
                parametrizacion.setAccion(this.getAccion());
            }
            if (this.getCorteEntidades() != null) {
                parametrizacion.setCorteEntidades(this.getCorteEntidades());
            }
            return parametrizacion;
        }
        return parametrizacionCartera;
    }
     
    /**
     * Método que retorna el valor de lineaCobro.
     * @return valor de lineaCobro.
     */
    public TipoLineaCobroEnum getLineaCobro() {
        return lineaCobro;
    }

    /**
     * Método que retorna el valor de activa.
     * @return valor de activa.
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Método que retorna el valor de metodo.
     * @return valor de metodo.
     */
    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    /**
     * Método que retorna el valor de accion.
     * @return valor de accion.
     */
    public TipoGestionCarteraEnum getAccion() {
        return accion;
    }

    /**
     * Método que retorna el valor de corteEntidades.
     * @return valor de corteEntidades.
     */
    public Long getCorteEntidades() {
        return corteEntidades;
    }

    /**
     * Método encargado de modificar el valor de lineaCobro.
     * @param valor
     *        para modificar lineaCobro.
     */
    public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    /**
     * Método encargado de modificar el valor de activa.
     * @param valor
     *        para modificar activa.
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * Método encargado de modificar el valor de metodo.
     * @param valor
     *        para modificar metodo.
     */
    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }

    /**
     * Método encargado de modificar el valor de accion.
     * @param valor
     *        para modificar accion.
     */
    public void setAccion(TipoGestionCarteraEnum accion) {
        this.accion = accion;
    }

    /**
     * Método encargado de modificar el valor de corteEntidades.
     * @param valor
     *        para modificar corteEntidades.
     */
    public void setCorteEntidades(Long corteEntidades) {
        this.corteEntidades = corteEntidades;
    }

}