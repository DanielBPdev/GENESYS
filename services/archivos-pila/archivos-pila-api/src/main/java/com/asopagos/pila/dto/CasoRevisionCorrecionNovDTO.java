package com.asopagos.pila.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;

/**
 * <b>Descripcion:</b> DTO que se emplea para la recopilación de los información para la 
 * validación de los casos de correcciones en novedades de ingreso y retiro en planillas tipo N
 * 
 * CONTROL DE CAMBIO 219141 - Anexo 2.1.1
 * <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class CasoRevisionCorrecionNovDTO extends CasoRevisionDTO{
    private static final long serialVersionUID = 1L;

    /** Tipo de cotizante */
    private TipoCotizanteEnum tipoCotizante;
    
    /** Listados de registros resumidos corrección A*/
    private List<ResumenRegistro2DTO> listaLineasA = new ArrayList<>();
    
    /** Listados de registros resumidos corrección C*/
    private List<ResumenRegistro2DTO> listaLineasC = new ArrayList<>();

    /** Sumatoria para días cotizados en registros A */
    private Integer sumatoriaDiasA = 0;

    /** Sumatoria para días cotizados en registros C */
    private Integer sumatoriaDiasC = 0;

    /** Sumatoria de IBC en registros A */
    private BigDecimal sumatoriaIbcA = BigDecimal.valueOf(0);

    /** Sumatoria de IBC en registros C */
    private BigDecimal sumatoriaIbcC = BigDecimal.valueOf(0);

    /**
     * @return the tipoCotizante
     */
    public TipoCotizanteEnum getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the listaLineasA
     */
    public List<ResumenRegistro2DTO> getListaLineasA() {
        return listaLineasA;
    }

    /**
     * @return the listaLineasC
     */
    public List<ResumenRegistro2DTO> getListaLineasC() {
        return listaLineasC;
    }
    
    /**
     * @param lineaA to add
     */
    public void addLineaA(ResumenRegistro2DTO lineaA){
        listaLineasA.add(lineaA);
    }
    
    /**
     * @param lineaC to add
     */
    public void addLineaC(ResumenRegistro2DTO lineaC){
        listaLineasC.add(lineaC);
    }

    /**
     * @return the sumatoriaDiasA
     */
    public Integer getSumatoriaDiasA() {
        return sumatoriaDiasA;
    }

    /**
     * @param sumatoriaDiasA the sumatoriaDiasA to set
     */
    public void setSumatoriaDiasA(Integer sumatoriaDiasA) {
        this.sumatoriaDiasA = sumatoriaDiasA;
    }

    /**
     * @return the sumatoriaDiasC
     */
    public Integer getSumatoriaDiasC() {
        return sumatoriaDiasC;
    }

    /**
     * @param sumatoriaDiasC the sumatoriaDiasC to set
     */
    public void setSumatoriaDiasC(Integer sumatoriaDiasC) {
        this.sumatoriaDiasC = sumatoriaDiasC;
    }

    /**
     * @return the sumatoriaIbcA
     */
    public BigDecimal getSumatoriaIbcA() {
        return sumatoriaIbcA;
    }

    /**
     * @param sumatoriaIbcA the sumatoriaIbcA to set
     */
    public void setSumatoriaIbcA(BigDecimal sumatoriaIbcA) {
        this.sumatoriaIbcA = sumatoriaIbcA;
    }

    /**
     * @return the sumatoriaIbcC
     */
    public BigDecimal getSumatoriaIbcC() {
        return sumatoriaIbcC;
    }

    /**
     * @param sumatoriaIbcC the sumatoriaIbcC to set
     */
    public void setSumatoriaIbcC(BigDecimal sumatoriaIbcC) {
        this.sumatoriaIbcC = sumatoriaIbcC;
    }
}
