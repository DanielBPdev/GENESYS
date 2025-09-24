/**
 * 
 */
package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase DTO con los datos de los filtros de la consulta.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class FiltrosParametrizacionDTO implements Serializable{


    /**
     * Serial
     */
    private static final long serialVersionUID = 5464490384657432401L;

    
    /**
     * Estado de cartera.
     */
    private List<String> estadosCartera;
    
    /**
     * Valor minimo de aportes.
     */
    private BigDecimal valorMinimoAportes;
    
    /**
     * Periodo inicio de promedio de aportespara un empleador.
     */
    private String periodoInicialAportesEmpleador;
    
    /**
     * Periodo final de promedio de aportes para un empleador.
     */
    private String periodoFinalAportesEmpleador;
    
    /**
     * Cantidad de trabajadores activos.
     */
    private Long cantidadTrabajadoresActivos;
  
    /**
     * Periodo inicio de mora para un empleador.
     */
    private Date  periodoInicialMorosoEmpleador;
    
    /**
     * Atributo que indica el estado de afialiación
     */
    private String estadoCarteraPantalla;
    
    /**
     * Periodo final de mora para un empleador.
     */
    private Date  periodoFinalMorosoEmpleador;
    
    /**
     * Periodo inicio de promedio de aportes  para un independiente o pensionado.
     */
    private String periodoInicialAportes;
    
    /**
     * Periodo final de promedio de aportes para un independiente o pensionado.
     */
    private String periodoFinalAportes;
    /**
     * Periodo inicio de mora  para un independiente o pensionado.
     */
    private Date  periodoInicialMoroso;
    
    /**
     * Periodo final de mora  para un independiente o pensionado.
     */
    private Date  periodoFinalMoroso;
    
    /**
     * Atributo que indica se deben incluir los independientes.
     */
    private Boolean incluirIndependientes;
    
    /**
     * Atributo que indica si  se deben incluir los pensionados.
     */
    private Boolean incluirPensionados;
    
    /**
     * Atributo que indica si se trata de un proceso automático o no.
     */
    private Boolean automatico;
    
    /**
     * Atributo que indica si se debe añadir filtro de morosidad.
     */
    private Boolean sinFiltroMora;
    
    /**
     * Atributo que indica si se debe añadir filtro de morosidad.
     */
    private Boolean aplicarCriterio;
    
    /**
     * Parámetros de consulta.
     */
    private Map<String,List<String>> params;
    
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
     * Atributo que indica la cantidad máxima de entidades que pueden salir.
     */
    private Long corteEntidades;
    /**
     * Atributo que indica las validaciones de pila.
     */
    private List<String> validacionesPila;
    /**
     * Periodo Inicial retroactivo para independientes y pensionados.
     */
    private Date periodoInicial;
    
    /**
     * Periodo final retroactivo para independientes y pensionados.
     */
    private Date periodoFinal;
    
    /**
     * Periodo Inicial retroactivo para empleadores.
     */
    private Date periodoInicialEmpleador;
    
    /**
     * Periodo final retroactivo para empleadores.
     */
    private Date periodoFinalEmpleador;
    
    /**
     * Alerta desde Gestión Preventiva
     */
    private Boolean gestionPreventiva;
    /**
     * Cantidad de días hábiles previos a la fecha de vencimiento del plazo de pago de
     * aportes para ejecutar este proceso.
     */
    private Short diasHabilesPrevios;

    
    private Boolean incluirLC2;
    /**
     * 	Línea de Cobro 2
     */
    private Boolean incluirLC3;
        /**
     * 	Línea de Cobro 3
     */

    /**
     * Día actual hábil
     */
    private Short diaActualHabil;
    /**
     * Método que retorna el valor de estadosCartera.
     * @return valor de estadosCartera.
     */
    public List<String> getEstadosCartera() {
        return estadosCartera;
    }

    /**
     * Método que retorna el valor de valorMinimoAportes.
     * @return valor de valorMinimoAportes.
     */
    public BigDecimal getValorMinimoAportes() {
        return valorMinimoAportes;
    }

    /**
     * Método que retorna el valor de periodoInicialAportesEmpleador.
     * @return valor de periodoInicialAportesEmpleador.
     */
    public String getPeriodoInicialAportesEmpleador() {
        return periodoInicialAportesEmpleador;
    }

    /**
     * Método que retorna el valor de periodoFinalAportesEmpleador.
     * @return valor de periodoFinalAportesEmpleador.
     */
    public String getPeriodoFinalAportesEmpleador() {
        return periodoFinalAportesEmpleador;
    }

    /**
     * Método que retorna el valor de cantidadTrabajadoresActivos.
     * @return valor de cantidadTrabajadoresActivos.
     */
    public Long getCantidadTrabajadoresActivos() {
        return cantidadTrabajadoresActivos;
    }

    /**
     * Método que retorna el valor de periodoInicialMorosoEmpleador.
     * @return valor de periodoInicialMorosoEmpleador.
     */
    public Date getPeriodoInicialMorosoEmpleador() {
        return periodoInicialMorosoEmpleador;
    }

    /**
     * Método que retorna el valor de periodoFinalMorosoEmpleador.
     * @return valor de periodoFinalMorosoEmpleador.
     */
    public Date getPeriodoFinalMorosoEmpleador() {
        return periodoFinalMorosoEmpleador;
    }

    /**
     * Método que retorna el valor de periodoInicialAportes.
     * @return valor de periodoInicialAportes.
     */
    public String getPeriodoInicialAportes() {
        return periodoInicialAportes;
    }

    /**
     * Método que retorna el valor de periodoFinalAportes.
     * @return valor de periodoFinalAportes.
     */
    public String getPeriodoFinalAportes() {
        return periodoFinalAportes;
    }

    /**
     * Método que retorna el valor de periodoInicialMoroso.
     * @return valor de periodoInicialMoroso.
     */
    public Date getPeriodoInicialMoroso() {
        return periodoInicialMoroso;
    }

    /**
     * Método que retorna el valor de periodoFinalMoroso.
     * @return valor de periodoFinalMoroso.
     */
    public Date getPeriodoFinalMoroso() {
        return periodoFinalMoroso;
    }

    /**
     * Método que retorna el valor de incluirIndependientes.
     * @return valor de incluirIndependientes.
     */
    public Boolean getIncluirIndependientes() {
        return incluirIndependientes;
    }

    /**
     * Método que retorna el valor de incluirPensionados.
     * @return valor de incluirPensionados.
     */
    public Boolean getIncluirPensionados() {
        return incluirPensionados;
    }

    /**
     * Método encargado de modificar el valor de estadosCartera.
     * @param valor para modificar estadosCartera.
     */
    public void setEstadosCartera(List<String> estadosCartera) {
        this.estadosCartera = estadosCartera;
    }

    /**
     * Método encargado de modificar el valor de valorMinimoAportes.
     * @param valor para modificar valorMinimoAportes.
     */
    public void setValorMinimoAportes(BigDecimal valorMinimoAportes) {
        this.valorMinimoAportes = valorMinimoAportes;
    }

    /**
     * Método encargado de modificar el valor de periodoInicialAportesEmpleador.
     * @param valor para modificar periodoInicialAportesEmpleador.
     */
    public void setPeriodoInicialAportesEmpleador(String periodoInicialAportesEmpleador) {
        this.periodoInicialAportesEmpleador = periodoInicialAportesEmpleador;
    }

    /**
     * Método encargado de modificar el valor de periodoFinalAportesEmpleador.
     * @param valor para modificar periodoFinalAportesEmpleador.
     */
    public void setPeriodoFinalAportesEmpleador(String periodoFinalAportesEmpleador) {
        this.periodoFinalAportesEmpleador = periodoFinalAportesEmpleador;
    }

    /**
     * Método encargado de modificar el valor de cantidadTrabajadoresActivos.
     * @param valor para modificar cantidadTrabajadoresActivos.
     */
    public void setCantidadTrabajadoresActivos(Long cantidadTrabajadoresActivos) {
        this.cantidadTrabajadoresActivos = cantidadTrabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de periodoInicialMorosoEmpleador.
     * @param valor para modificar periodoInicialMorosoEmpleador.
     */
    public void setPeriodoInicialMorosoEmpleador(Date periodoInicialMorosoEmpleador) {
        this.periodoInicialMorosoEmpleador = periodoInicialMorosoEmpleador;
    }

    /**
     * Método encargado de modificar el valor de periodoFinalMorosoEmpleador.
     * @param valor para modificar periodoFinalMorosoEmpleador.
     */
    public void setPeriodoFinalMorosoEmpleador(Date periodoFinalMorosoEmpleador) {
        this.periodoFinalMorosoEmpleador = periodoFinalMorosoEmpleador;
    }

    /**
     * Método encargado de modificar el valor de periodoInicialAportes.
     * @param valor para modificar periodoInicialAportes.
     */
    public void setPeriodoInicialAportes(String periodoInicialAportes) {
        this.periodoInicialAportes = periodoInicialAportes;
    }

    /**
     * Método encargado de modificar el valor de periodoFinalAportes.
     * @param valor para modificar periodoFinalAportes.
     */
    public void setPeriodoFinalAportes(String periodoFinalAportes) {
        this.periodoFinalAportes = periodoFinalAportes;
    }

    /**
     * Método encargado de modificar el valor de periodoInicialMoroso.
     * @param valor para modificar periodoInicialMoroso.
     */
    public void setPeriodoInicialMoroso(Date periodoInicialMoroso) {
        this.periodoInicialMoroso = periodoInicialMoroso;
    }

    /**
     * Método encargado de modificar el valor de periodoFinalMoroso.
     * @param valor para modificar periodoFinalMoroso.
     */
    public void setPeriodoFinalMoroso(Date periodoFinalMoroso) {
        this.periodoFinalMoroso = periodoFinalMoroso;
    }

    /**
     * Método encargado de modificar el valor de incluirIndependientes.
     * @param valor para modificar incluirIndependientes.
     */
    public void setIncluirIndependientes(Boolean incluirIndependientes) {
        this.incluirIndependientes = incluirIndependientes;
    }

    /**
     * Método encargado de modificar el valor de incluirPensionados.
     * @param valor para modificar incluirPensionados.
     */
    public void setIncluirPensionados(Boolean incluirPensionados) {
        this.incluirPensionados = incluirPensionados;
    }

    /**
     * Método que retorna el valor de automatico.
     * @return valor de automatico.
     */
    public Boolean getAutomatico() {
        return automatico;
    }

    /**
     * Método que retorna el valor de sinFiltroMora.
     * @return valor de sinFiltroMora.
     */
    public Boolean getSinFiltroMora() {
        return sinFiltroMora;
    }

    /**
     * Método encargado de modificar el valor de automatico.
     * @param valor para modificar automatico.
     */
    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
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
     * Método encargado de modificar el valor de sinFiltroMora.
     * @param valor para modificar sinFiltroMora.
     */
    public void setSinFiltroMora(Boolean sinFiltroMora) {
        this.sinFiltroMora = sinFiltroMora;
    }

    /**
     * Método que retorna el valor de params.
     * @return valor de params.
     */
    public Map<String, List<String>> getParams() {
        if(params == null){
            params = new HashMap<>();
        }
        return params;
    }

    /**
     * Método encargado de modificar el valor de params.
     * @param valor para modificar params.
     */
    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }

    /**
     * Método que retorna el valor de mayorValorPromedio.
     * @return valor de mayorValorPromedio.
     */
    public Short getMayorValorPromedio() {
        return mayorValorPromedio;
    }

    /**
     * Método que retorna el valor de mayorTrabajadoresActivos.
     * @return valor de mayorTrabajadoresActivos.
     */
    public Short getMayorTrabajadoresActivos() {
        return mayorTrabajadoresActivos;
    }

    /**
     * Método que retorna el valor de mayorVecesMoroso.
     * @return valor de mayorVecesMoroso.
     */
    public Short getMayorVecesMoroso() {
        return mayorVecesMoroso;
    }

    /**
     * Método encargado de modificar el valor de mayorValorPromedio.
     * @param valor para modificar mayorValorPromedio.
     */
    public void setMayorValorPromedio(Short mayorValorPromedio) {
        this.mayorValorPromedio = mayorValorPromedio;
    }

    /**
     * Método encargado de modificar el valor de mayorTrabajadoresActivos.
     * @param valor para modificar mayorTrabajadoresActivos.
     */
    public void setMayorTrabajadoresActivos(Short mayorTrabajadoresActivos) {
        this.mayorTrabajadoresActivos = mayorTrabajadoresActivos;
    }

    /**
     * Método encargado de modificar el valor de mayorVecesMoroso.
     * @param valor para modificar mayorVecesMoroso.
     */
    public void setMayorVecesMoroso(Short mayorVecesMoroso) {
        this.mayorVecesMoroso = mayorVecesMoroso;
    }

    /**
     * Método que retorna el valor de corteEntidades.
     * @return valor de corteEntidades.
     */
    public Long getCorteEntidades() {
        return corteEntidades;
    }

    /**
     * Método encargado de modificar el valor de corteEntidades.
     * @param valor para modificar corteEntidades.
     */
    public void setCorteEntidades(Long corteEntidades) {
        this.corteEntidades = corteEntidades;
    }

    /**
     * Método que retorna el valor de periodoInicial.
     * @return valor de periodoInicial.
     */
    public Date getPeriodoInicial() {
        return periodoInicial;
    }

    /**
     * Método encargado de modificar el valor de periodoInicial.
     * @param valor para modificar periodoInicial.
     */
    public void setPeriodoInicial(Date periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    /**
     * Método que retorna el valor de periodoFinal.
     * @return valor de periodoFinal.
     */
    public Date getPeriodoFinal() {
        return periodoFinal;
    }

    /**
     * Método encargado de modificar el valor de periodoFinal.
     * @param valor para modificar periodoFinal.
     */
    public void setPeriodoFinal(Date periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    /**
     * Método que retorna el valor de periodoInicialEmpleador.
     * @return valor de periodoInicialEmpleador.
     */
    public Date getPeriodoInicialEmpleador() {
        return periodoInicialEmpleador;
    }

    /**
     * Método encargado de modificar el valor de periodoInicialEmpleador.
     * @param valor para modificar periodoInicialEmpleador.
     */
    public void setPeriodoInicialEmpleador(Date periodoInicialEmpleador) {
        this.periodoInicialEmpleador = periodoInicialEmpleador;
    }

    /**
     * Método que retorna el valor de periodoFinalEmpleador.
     * @return valor de periodoFinalEmpleador.
     */
    public Date getPeriodoFinalEmpleador() {
        return periodoFinalEmpleador;
    }

    /**
     * Método encargado de modificar el valor de periodoFinalEmpleador.
     * @param valor para modificar periodoFinalEmpleador.
     */
    public void setPeriodoFinalEmpleador(Date periodoFinalEmpleador) {
        this.periodoFinalEmpleador = periodoFinalEmpleador;
    }

    /**
     * Método que retorna el valor de validacionesPila.
     * @return valor de validacionesPila.
     */
    public List<String> getValidacionesPila() {
        return validacionesPila;
    }

    /**
     * Método encargado de modificar el valor de validacionesPila.
     * @param valor para modificar validacionesPila.
     */
    public void setValidacionesPila(List<String> validacionesPila) {
        this.validacionesPila = validacionesPila;
    }

     /**
     * Método que retorna el valor de diasHabilesPrevios.
     * @return valor de diasHabilesPrevios.
     */
    public Short getDiasHabilesPrevios() {
        return diasHabilesPrevios;
    }
     /**
     * Método que retorna el valor de incluirLC2
     * @return valor deincluirLC2 .
     */
   
    public Boolean getIncluirLC2(){
        return incluirLC2;
  
      }
       /**
       * Método encargado de modificar el valor de incluirLC2
       * @param valor para modificar incluirLC2.
       */
    public void setIncluirLC2(Boolean incluirLC2){
          this.incluirLC2 = incluirLC2;
      }
      /**
       * Método que retorna el valor de incluirLC3
       * @return valor deincluirLC3 .
       */
    public Boolean getIncluirLC3(){
          return incluirLC3;
      }
      /**
       * Método encargado de modificar el valor de incluirLC2
       * @param valor para modificar incluirLC3.
       */
  
    public void setIncluirLC3(Boolean incluirLC3){
            this.incluirLC3 = incluirLC3;
     }

    /**
     * Método encargado de modificar el valor de diasHabilesPrevios.
     * @param valor para modificar diasHabilesPrevios.
     */
    public void setDiasHabilesPrevios(Short diasHabilesPrevios) {
        this.diasHabilesPrevios = diasHabilesPrevios;
    }

    /**
     * Método que retorna el valor de diaActualHabil.
     * @return valor de diaActualHabil.
     */
    public Short getDiaActualHabil() {
        return diaActualHabil;
    }

    /**
     * Método encargado de modificar el valor de diaActualHabil.
     * @param valor para modificar diaActualHabil.
     */
    public void setDiaActualHabil(Short diaActualHabil) {
        this.diaActualHabil = diaActualHabil;
    }

    /**
     * @return the aplicarCriterio
     */
    public Boolean getAplicarCriterio() {
        return aplicarCriterio;
    }

    /**
     * @param aplicarCriterio the aplicarCriterio to set
     */
    public void setAplicarCriterio(Boolean aplicarCriterio) {
        this.aplicarCriterio = aplicarCriterio;
    }
/**
 * 
 * @return
 */
	public String getEstadoCarteraPantalla() {
		return estadoCarteraPantalla;
	}

	public void setEstadoCarteraPantalla(String estadoCarteraPantalla) {
		this.estadoCarteraPantalla = estadoCarteraPantalla;
	}


}
