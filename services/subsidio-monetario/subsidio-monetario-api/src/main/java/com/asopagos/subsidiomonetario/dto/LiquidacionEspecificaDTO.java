package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.RolAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.subsidiomonetario.liquidacion.Periodo;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionSubsidioAjusteModeloDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson Arboleda</a>
 */

public class LiquidacionEspecificaDTO implements Serializable {

    private static final long serialVersionUID = 1734396374444905974L;

    /** Representa el tipo de proceso de liquidacion de subsidio */
    private TipoProcesoLiquidacionEnum tipoLiquidacion;
    
    /** Representa los tipos de liquidación especificas del proceso de liquidación de subsidio */
    private TipoLiquidacionEspecificaEnum tipoAjuste;
    
    /** Indica si la liquidacion especifica actual corresponde a un reclamo */
    private Boolean isReclamo;
    
    /** Codigo del reclamo asociado */
    private String codigoReclamo;
    
    /** Comentarios sobre el reclamo relacionado */
    private String comentariosReclamo;
    
    /** Nivel sobre el cual se va a ejecutar la liquidacion (persona, empresa, todos) */
    private TipoTipoSolicitanteEnum nivelLiquidacion;
    
    /** Listado con las personas que han sido seleccionadas */
    private List<PersonaDTO> listaPersonas;
    
    /** Listado de beneficiarios seleccionados */
    private List<BeneficiariosAfiliadoDTO> listaAfiliados;
    
    /** Listado de periodos seleccionados */
    private List<ValorPeriodoDTO> listaPeriodos;
    
    /** Listado de empleadores seleccionados */
    private List<Long> listaEmpleadores;
    
    /** Listado de empleadores seleccionados */
    private List<RolAfiliadoDTO> listaRolAfiliado;
    
    
    /** Listado de empresas seleccionados (sin afiliación previa) */
    private List<Long> listaEmpresas;

    /** Condiciones especiales HU-317-143: Cuotas Vigentes */
    private List<ParametrizacionSubsidioAjusteModeloDTO> parametrizacionCuotas;
    
    /** Condiciones especiales HU-317-226 */
    private CondicionesEspecialesLiquidacionEspecificaDTO condicionesEspecialesReconocimiento;
    
    /** Trabajador fallecidos */
    private PersonaFallecidaTrabajadorDTO personaFallecida;
    
    /** Lista de empleadores para mostrar */
    private List<Empleador> listaEmpleadoresMostrar;
    
    /** Lista de periodos para mostrar */
    private List<Periodo> listaPeriodosMostrar;

    
    /**
     * @return the tipoLiquidacion
     */
    public TipoProcesoLiquidacionEnum getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    /**
     * @return the tipoAjuste
     */
    public TipoLiquidacionEspecificaEnum getTipoAjuste() {
        return tipoAjuste;
    }

    /**
     * @return the isReclamo
     */
    public Boolean getIsReclamo() {
        return isReclamo;
    }

    /**
     * @return the codigoReclamo
     */
    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    /**
     * @return the comentariosReclamo
     */
    public String getComentariosReclamo() {
        return comentariosReclamo;
    }

    /**
     * @return the nivelLiquidacion
     */
    public TipoTipoSolicitanteEnum getNivelLiquidacion() {
        return nivelLiquidacion;
    }

    /**
     * @return the listaPersonas
     */
    public List<PersonaDTO> getListaPersonas() {
        return listaPersonas;
    }

    /**
     * @return the listaAfiliados
     */
    public List<BeneficiariosAfiliadoDTO> getListaAfiliados() {
        return listaAfiliados;
    }

    /**
     * @return the listaPeriodos
     */
    public List<ValorPeriodoDTO> getListaPeriodos() {
        return listaPeriodos;
    }

    /**
     * @param tipoLiquidacion the tipoLiquidacion to set
     */
    public void setTipoLiquidacion(TipoProcesoLiquidacionEnum tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    /**
     * @param tipoAjuste the tipoAjuste to set
     */
    public void setTipoAjuste(TipoLiquidacionEspecificaEnum tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    /**
     * @param isReclamo the isReclamo to set
     */
    public void setIsReclamo(Boolean isReclamo) {
        this.isReclamo = isReclamo;
    }

    /**
     * @param codigoReclamo the codigoReclamo to set
     */
    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    /**
     * @param comentariosReclamo the comentariosReclamo to set
     */
    public void setComentariosReclamo(String comentariosReclamo) {
        this.comentariosReclamo = comentariosReclamo;
    }

    /**
     * @param nivelLiquidacion the nivelLiquidacion to set
     */
    public void setNivelLiquidacion(TipoTipoSolicitanteEnum nivelLiquidacion) {
        this.nivelLiquidacion = nivelLiquidacion;
    }

    /**
     * @param listaPersonas the listaPersonas to set
     */
    public void setListaPersonas(List<PersonaDTO> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    /**
     * @param listaAfiliados the listaAfiliados to set
     */
    public void setListaAfiliados(List<BeneficiariosAfiliadoDTO> listaAfiliados) {
        this.listaAfiliados = listaAfiliados;
    }

    /**
     * @param listaPeriodos the listaPeriodos to set
     */
    public void setListaPeriodos(List<ValorPeriodoDTO> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    /**
     * @return the listaEmpleadores
     */
    public List<Long> getListaEmpleadores() {
        return listaEmpleadores;
    }

    /**
     * @param listaEmpleadores the listaEmpleadores to set
     */
    public void setListaEmpleadores(List<Long> listaEmpleadores) {
        this.listaEmpleadores = listaEmpleadores;
    }

    /**
     * @return the parametrizacionCuotas
     */
    public List<ParametrizacionSubsidioAjusteModeloDTO> getParametrizacionCuotas() {
        return parametrizacionCuotas;
    }

    /**
     * @param parametrizacionCuotas the parametrizacionCuotas to set
     */
    public void setParametrizacionCuotas(List<ParametrizacionSubsidioAjusteModeloDTO> parametrizacionCuotas) {
        this.parametrizacionCuotas = parametrizacionCuotas;
    }

    /**
     * @return the condicionesEspecialesReconocimiento
     */
    public CondicionesEspecialesLiquidacionEspecificaDTO getCondicionesEspecialesReconocimiento() {
        return condicionesEspecialesReconocimiento;
    }

    /**
     * @param condicionesEspecialesReconocimiento the condicionesEspecialesReconocimiento to set
     */
    public void setCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condicionesEspecialesReconocimiento) {
        this.condicionesEspecialesReconocimiento = condicionesEspecialesReconocimiento;
    }

    /**
     * @return the personaFallecida
     */
    public PersonaFallecidaTrabajadorDTO getPersonaFallecida() {
        return personaFallecida;
    }

    /**
     * @param personaFallecida the personaFallecida to set
     */
    public void setPersonaFallecida(PersonaFallecidaTrabajadorDTO personaFallecida) {
        this.personaFallecida = personaFallecida;
    }

    /**
     * @return the listaEmpleadoresMostrar
     */
    public List<Empleador> getListaEmpleadoresMostrar() {
        return listaEmpleadoresMostrar;
    }

    /**
     * @param listaEmpleadoresMostrar the listaEmpleadoresMostrar to set
     */
    public void setListaEmpleadoresMostrar(List<Empleador> listaEmpleadoresMostrar) {
        this.listaEmpleadoresMostrar = listaEmpleadoresMostrar;
    }

    /**
     * @return the listaPeriodosMostrar
     */
    public List<Periodo> getListaPeriodosMostrar() {
        return listaPeriodosMostrar;
    }

    /**
     * @param listaPeriodosMostrar the listaPeriodosMostrar to set
     */
    public void setListaPeriodosMostrar(List<Periodo> listaPeriodosMostrar) {
        this.listaPeriodosMostrar = listaPeriodosMostrar;
    }

    /**
     * @return the listaEmpresas
     */
    public List<Long> getListaEmpresas() {
        return listaEmpresas;
    }

    /**
     * @param listaEmpresas the listaEmpresas to set
     */
    public void setListaEmpresas(List<Long> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public List<RolAfiliadoDTO> getListaRolAfiliado() {
        return listaRolAfiliado;
    }

    public void setListaRolAfiliado(List<RolAfiliadoDTO> listaRolAfiliado) {
        this.listaRolAfiliado = listaRolAfiliado;
    }
}
