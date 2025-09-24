package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.ParametrizacionCartera;
import com.asopagos.enumeraciones.cartera.CantidadTrabajadoresActivosEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.PeriodoRegularEnum;
import com.asopagos.enumeraciones.cartera.PromedioAportesEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionCarteraEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la parametrización de la cartera  <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Claudia Milena Marín<a href="mailto:clmarin@heinsohn.com.co"/> 
 */

public class ParametrizacionCarteraModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = -8773496012896847518L;
    /**
     * Atributo que especifica el id de la ParametrizacionCartera
     */
    private Long idParametrizacionCartera;

    /**
     * Atributo que indica si esta o no habilitada la parametrización preventiva de
     * mora de aportes.
     */
    private Boolean aplicar;
     /**
     * Atributo qud indica la linea de cobro LC2
     */
    private Boolean incluirLC2;
     /**
     * Atributo qud indica la linea de cobro LC3
     */
    private Boolean incluirLC3;
    
    /**
     * Atributo que indica si la parametrización incluye los tipos de aportante
     * independientes.
     */
    private Boolean incluirIndependientes;
    
    /**
     * Atributo que indica si la parametrización incluye los tipos de aportante
     * independientes.
     */
    private Boolean incluirPensionados;
    
    /**
     * Estado de cartera. 
     */
    private EstadoCarteraEnum estadoCartera;
    
    /**
     * Estado de Afiliación
     */
    private String estadoCarteraPantalla;
    
    /**
     * Atributo que indica la cantidad de periodos para calcular el promedio de los
     * aportes.
     */
    private Short cantidadPeriodos;
    
    /**
     * Enumeracion que representa el valor promedio de aportes
     */
    private PromedioAportesEnum valorPromedioAportes;
    
    /**
     * Atributo que identifica la cantidad de trabajadores activos.
     */
    private CantidadTrabajadoresActivosEnum trabajadoresActivos;
 
    /**
     * Atributo que indica los eriodos regulares en los que ha sido moroso.
     */
    private PeriodoRegularEnum periodosMorosos;
    
    /**
     * Atributo que indica aportantes con mayor valor promedio de aportes mensuales
     */
    private Short mayorValorPromedio;
    
    /**
     * Atributo que indica los aportantes con mayor cantidad de trabajadores activos
     * actualmente
     */
    private Short mayorTrabajadoresActivos;
    
    /**
     * Atributo que indica aportantes con mayor cantidad de veces en estado moroso en
     * los últimos periodos
     */
    private Short mayorVecesMoroso;
    
    /**
     * Enumeracion que representa el tipo de parametrización para cartera
     */
    private TipoParametrizacionCarteraEnum tipoParametrizacion;

    /**
     * Método que retorna el valor de aplicar.
     * @return valor de aplicar.
     */
    public Boolean getAplicar() {
        return aplicar;
    }

    /**
     * Método encargado de modificar el valor de aplicar.
     * @param valor para modificar aplicar.
     */
    public void setAplicar(Boolean aplicar) {
        this.aplicar = aplicar;
    }
     /**
     * Método que retorna el valor de aplicar.
     * @return valor de aplicar.
     */
    public Boolean getIncluirLC2() {
        return this.incluirLC2;
    }
        /**
     * Método encargado de modificar el valor de aplicar.
     * @param valor para modificar aplicar.
     */
    public void setIncluirLC2(Boolean incluirLC2) {
        this.incluirLC2 = incluirLC2;
    }
      /**
     * Método que retorna el valor de aplicar.
     * @return valor de aplicar.
     */
    public Boolean getIncluirLC3() {
        return this.incluirLC3;
    }
      /**
     * Método encargado de modificar el valor de aplicar.
     * @param valor para modificar aplicar.
     */
    public void setIncluirLC3(Boolean incluirLC3) {
        this.incluirLC3 = incluirLC3;
    }
    /**
     * Método que retorna el valor de incluirIndependientes.
     * @return valor de incluirIndependientes.
     */
    public Boolean getIncluirIndependientes() {
        return incluirIndependientes;
    }

    /**
     * Método encargado de modificar el valor de incluirIndependientes.
     * @param valor para modificar incluirIndependientes.
     */
    public void setIncluirIndependientes(Boolean incluirIndependientes) {
        this.incluirIndependientes = incluirIndependientes;
    }

    /**
     * Método que retorna el valor de incluirPensionados.
     * @return valor de incluirPensionados.
     */
    public Boolean getIncluirPensionados() {
        return incluirPensionados;
    }

    /**
     * Método encargado de modificar el valor de incluirPensionados.
     * @param valor para modificar incluirPensionados.
     */
    public void setIncluirPensionados(Boolean incluirPensionados) {
        this.incluirPensionados = incluirPensionados;
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
     * @param valor para modificar estadoCartera.
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Método que retorna el valor de cantidadPeriodos.
     * @return valor de cantidadPeriodos.
     */
    public Short getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    /**
     * Método encargado de modificar el valor de cantidadPeriodos.
     * @param valor para modificar cantidadPeriodos.
     */
    public void setCantidadPeriodos(Short cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }

    public String getEstadoCarteraPantalla() {
    	return estadoCarteraPantalla;
    }
    
    public void setEstadoCarteraPantalla(String estadoCarteraPantalla) {
    	this.estadoCarteraPantalla = estadoCarteraPantalla;
    }
    
    /**
     * Método que retorna el valor de valorPromedioAportes.
     * @return valor de valorPromedioAportes.
     */
    public PromedioAportesEnum getValorPromedioAportes() {
        return valorPromedioAportes;
    }

    /**
     * Método encargado de modificar el valor de valorPromedioAportes.
     * @param valor para modificar valorPromedioAportes.
     */
    public void setValorPromedioAportes(PromedioAportesEnum valorPromedioAportes) {
        this.valorPromedioAportes = valorPromedioAportes;
    }

    /**
     * Método que retorna el valor de trabajadoresActivos.
     * @return valor de trabajadoresActivos.
     */
    public CantidadTrabajadoresActivosEnum getTrabajadoresActivos() {
        return trabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de trabajadoresActivos.
     * @param valor para modificar trabajadoresActivos.
     */
    public void setTrabajadoresActivos(CantidadTrabajadoresActivosEnum trabajadoresActivos) {
        this.trabajadoresActivos = trabajadoresActivos;
    }

    /**
     * Método que retorna el valor de periodosMorosos.
     * @return valor de periodosMorosos.
     */
    public PeriodoRegularEnum getPeriodosMorosos() {
        return periodosMorosos;
    }

    /**
     * Método encargado de modificar el valor de periodosMorosos.
     * @param valor para modificar periodosMorosos.
     */
    public void setPeriodosMorosos(PeriodoRegularEnum periodosMorosos) {
        this.periodosMorosos = periodosMorosos;
    }

    /**
     * Método que retorna el valor de mayorValorPromedio.
     * @return valor de mayorValorPromedio.
     */
    public Short getMayorValorPromedio() {
        return mayorValorPromedio;
    }

    /**
     * Método encargado de modificar el valor de mayorValorPromedio.
     * @param valor
     *        para modificar mayorValorPromedio.
     */
    public void setMayorValorPromedio(Short mayorValorPromedio) {
        this.mayorValorPromedio = mayorValorPromedio;
    }

    /**
     * Método que retorna el valor de mayorTrabajadoresActivos.
     * @return valor de mayorTrabajadoresActivos.
     */
    public Short getMayorTrabajadoresActivos() {
        return mayorTrabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de mayorTrabajadoresActivos.
     * @param valor
     *        para modificar mayorTrabajadoresActivos.
     */
    public void setMayorTrabajadoresActivos(Short mayorTrabajadoresActivos) {
        this.mayorTrabajadoresActivos = mayorTrabajadoresActivos;
    }

    /**
     * Método que retorna el valor de mayorVecesMoroso.
     * @return valor de mayorVecesMoroso.
     */
    public Short getMayorVecesMoroso() {
        return mayorVecesMoroso;
    }

    /**
     * Método encargado de modificar el valor de mayorVecesMoroso.
     * @param valor para modificar mayorVecesMoroso.
     */
    public void setMayorVecesMoroso(Short mayorVecesMoroso) {
        this.mayorVecesMoroso = mayorVecesMoroso;
    }

    /**
     * Método que retorna el valor de tipoParametrizacion.
     * @return valor de tipoParametrizacion.
     */
    public TipoParametrizacionCarteraEnum getTipoParametrizacion() {
        return tipoParametrizacion;
    }

    /**
     * Método encargado de modificar el valor de tipoParametrizacion.
     * @param valor
     *        para modificar tipoParametrizacion.
     */
    public void setTipoParametrizacion(TipoParametrizacionCarteraEnum tipoParametrizacion) {
        this.tipoParametrizacion = tipoParametrizacion;
    }

    /**
     * Método que retorna el valor de idParametrizacionCartera.
     * @return valor de idParametrizacionCartera.
     */
    public Long getIdParametrizacionCartera() {
        return idParametrizacionCartera;
    }

    /**
     * Método encargado de modificar el valor de idParametrizacionCartera.
     * @param valor
     *        para modificar idParametrizacionCartera.
     */
    public void setIdParametrizacionCartera(Long idParametrizacionCartera) {
        this.idParametrizacionCartera = idParametrizacionCartera;
    }

    /**
     * Método encargado de convertir de Entidad a DTO
     * @param parametrizacionCartera
     *        entidad a convertir.
     * @return DTO convertido
     */
    public ParametrizacionCarteraModeloDTO convertToDTO(ParametrizacionCartera parametrizacionCartera) {
        this.setIdParametrizacionCartera(parametrizacionCartera.getIdParametrizacionCartera());
        this.setAplicar(parametrizacionCartera.getAplicar());
        this.setCantidadPeriodos(parametrizacionCartera.getCantidadPeriodos());
        this.setEstadoCartera(parametrizacionCartera.getEstadoCartera());
        this.setEstadoCarteraPantalla(parametrizacionCartera.getEstadoCarteraPantalla()); 
                  
        this.setIncluirIndependientes(parametrizacionCartera.getIncluirIndependientes());
        this.setIncluirPensionados(parametrizacionCartera.getIncluirPensionados());
        this.setIncluirLC2(parametrizacionCartera.getIncluirLC2());
        this.setIncluirLC3(parametrizacionCartera.getIncluirLC3());
        this.setMayorTrabajadoresActivos(parametrizacionCartera.getMayorTrabajadoresActivos());
        this.setMayorValorPromedio(parametrizacionCartera.getMayorValorPromedio());
        this.setMayorVecesMoroso(parametrizacionCartera.getMayorVecesMoroso());
        this.setPeriodosMorosos(parametrizacionCartera.getPeriodosMorosos());
        this.setTipoParametrizacion(parametrizacionCartera.getTipoParametrizacion());
        this.setTrabajadoresActivos(parametrizacionCartera.getTrabajadoresActivos());
        this.setValorPromedioAportes(parametrizacionCartera.getValorPromedioAportes());
        return this;
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public ParametrizacionCartera convertToEntity(ParametrizacionCartera parametrizacionCartera) {
        parametrizacionCartera.setIdParametrizacionCartera(this.getIdParametrizacionCartera());
        parametrizacionCartera.setAplicar(this.getAplicar());
        parametrizacionCartera.setCantidadPeriodos(this.getCantidadPeriodos());
        parametrizacionCartera.setEstadoCartera(this.getEstadoCartera());
        parametrizacionCartera.setIncluirLC2(this.getIncluirLC2());
        parametrizacionCartera.setIncluirLC3(this.getIncluirLC3());


        parametrizacionCartera.setEstadoCarteraPantalla(this.getEstadoCarteraPantalla());          

        parametrizacionCartera.setIncluirIndependientes(this.getIncluirIndependientes());
        parametrizacionCartera.setIncluirPensionados(this.getIncluirPensionados());
        parametrizacionCartera.setMayorTrabajadoresActivos(this.getMayorTrabajadoresActivos());
        parametrizacionCartera.setMayorValorPromedio(this.getMayorValorPromedio());
        parametrizacionCartera.setMayorVecesMoroso(this.getMayorVecesMoroso());
        parametrizacionCartera.setPeriodosMorosos(this.getPeriodosMorosos());
        parametrizacionCartera.setTipoParametrizacion(this.getTipoParametrizacion());
        parametrizacionCartera.setTrabajadoresActivos(this.getTrabajadoresActivos());
        parametrizacionCartera.setValorPromedioAportes(this.getValorPromedioAportes());
        return parametrizacionCartera;

    }

}
