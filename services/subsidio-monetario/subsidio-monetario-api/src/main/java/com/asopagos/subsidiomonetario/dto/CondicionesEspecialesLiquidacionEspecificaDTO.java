package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene las condiciones especiales para un subsidio especifico por reconocimiento <br/>
 * <b>Módulo:</b> Asopagos - HU-317-226 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class CondicionesEspecialesLiquidacionEspecificaDTO implements Serializable {

    private static final long serialVersionUID = -769230466195768464L;

    // PESTAÑA GENERAL:

    /** Regla para evaluar condicion agricola */
    private Boolean evaluarCondicionAgricola;

    /** Regla para evaluar retroactivos por novedades */
    private Boolean evaluarRetroactivosPorNovedades;

    /** Regla para evaluar retroactivos por aportes */
    private Boolean evaluarRetroactivosPorAportes;

    /* PESTAÑA EMPRESAS */

    /** Validar condiciones del aportante regular */
    private Boolean validarCondicionesAportanteRegular;

    /** Estados válidos de los empleadores para ejecutar liquidación */
    private List<EstadoEmpleadorEnum> estadosEmpleador;

    /** Estados de afiliación del trabajador con respecto al empleador */
    private List<EstadoAfiliadoEnum> estadosAfiliacionTrabajador;

    /* PESTAÑA PERSONAS */

    /** Validar condiciones del trabajador */
    private Boolean validarCondicionesTrabajador;

    /**
     * Se evaluará la condición que el trabajador debe tener al menos un
     * beneficiario activo diferente a "Cónyuge”
     */
    private Boolean evaluarBeneficiarioDiferenteAConyuge;

    /** Se evalúa regla de causación de salarios para trabajadores con multiafiliacion */
    private Boolean evaluarTrabajadorConMultiAfiliacion;

    /** Se evalua regla en la cual el trabajador sea distinto al empleador */
    private Boolean evaluarTrabajadorDistintoAEmpleador;

    /** Estados del aporte que son válidos para aportes y novedades del trabajador */
    private List<EstadoRegistroAportesArchivoEnum> estadosAporteValidosTrabajador;

    /** Evaluar regla de días cotizados y novedades del trabajador */
    private Boolean evaluarDiasCotizadosYNovedadesTrabajador;

    /** Evaluar regla de salario del empleador */
    private Boolean evaluarSalarioTrabajador;

    /* PESTAÑA BENEFICIARIOS */

    /** Validar las condiciones del beneficiario */
    private Boolean validarCondicionesBeneficiario;

    // Beneficiario tipo padre

    /** Estado de afiliación del Beneficiario tipo Padre con respecto al afiliado principal */
    private List<EstadoAfiliadoEnum> estadosAfilPadreConAfiliadoPrincipal;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo padre es el afiliado pricipal */
    private Boolean evaluarBeneficiarioPadreEsAfiliadoPrincipal;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo padre persona natural */
    private Boolean evaluarBeneficiarioPadreEsPersonaNatural;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo padre tiene otros aportes */
    private Boolean evaluarBeneficiarioPadreTieneOtrosAportes;

    /** Evaluar si es beneficiario tipo padre de otras personas */
    private Boolean evaluarBenefiarioTipoPadreDeOtrasPersonas;
    
    /** Evaluar beneficiario tipo padre con condicion de invalidez */
    private Boolean evaluarBeneficiarioTipoPadreConCondicionInvalidez;
    
    /** Evaluar edad de beneficiario tipo padre */
    private Boolean evaluarEdadBeneficiarioTipoPadre;

    // Beneficiario tipo hijo
    
    /** Estado de afiliación del Beneficiario tipo hijo con respecto al afiliado principal */
    private List<EstadoAfiliadoEnum> estadosAfilHijoConAfiliadoPrincipal;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo hijo es el afiliado pricipal */
    private Boolean evaluarBeneficiarioHijoEsAfiliadoPrincipal;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo hijo es persona natural */
    private Boolean evaluarBeneficiarioHijoEsPersonaNatural;

    /** (Beneficiario con otros ingresos) Validar si beneficiario tipo hijo tiene otros aportes */
    private Boolean evaluarBeneficiarioHijoTieneOtrosAportes;

    /** Evaluar si es beneficiario tipo hijo de otras personas */
    private Boolean evaluarBenefiarioTipoHijoDeOtrasPersonas;
    
    /** Evaluar beneficiario tipo hijo con condicion de invalidez */
    private Boolean evaluarBeneficiarioTipoHijoConCondicionInvalidez;
    
    /** Evaluar edad de beneficiario tipo hijo */
    private Boolean evaluarEdadBeneficiarioTipoHijo;

    /**
     * @return the evaluarCondicionAgricola
     */
    public Boolean getEvaluarCondicionAgricola() {
        return evaluarCondicionAgricola;
    }

    /**
     * @param evaluarCondicionAgricola the evaluarCondicionAgricola to set
     */
    public void setEvaluarCondicionAgricola(Boolean evaluarCondicionAgricola) {
        this.evaluarCondicionAgricola = evaluarCondicionAgricola;
    }

    /**
     * @return the evaluarRetroactivosPorNovedades
     */
    public Boolean getEvaluarRetroactivosPorNovedades() {
        return evaluarRetroactivosPorNovedades;
    }

    /**
     * @param evaluarRetroactivosPorNovedades the evaluarRetroactivosPorNovedades to set
     */
    public void setEvaluarRetroactivosPorNovedades(Boolean evaluarRetroactivosPorNovedades) {
        this.evaluarRetroactivosPorNovedades = evaluarRetroactivosPorNovedades;
    }

    /**
     * @return the evaluarRetroactivosPorAportes
     */
    public Boolean getEvaluarRetroactivosPorAportes() {
        return evaluarRetroactivosPorAportes;
    }

    /**
     * @param evaluarRetroactivosPorAportes the evaluarRetroactivosPorAportes to set
     */
    public void setEvaluarRetroactivosPorAportes(Boolean evaluarRetroactivosPorAportes) {
        this.evaluarRetroactivosPorAportes = evaluarRetroactivosPorAportes;
    }

    /**
     * @return the validarCondicionesAportanteRegular
     */
    public Boolean getValidarCondicionesAportanteRegular() {
        return validarCondicionesAportanteRegular;
    }

    /**
     * @param validarCondicionesAportanteRegular the validarCondicionesAportanteRegular to set
     */
    public void setValidarCondicionesAportanteRegular(Boolean validarCondicionesAportanteRegular) {
        this.validarCondicionesAportanteRegular = validarCondicionesAportanteRegular;
    }

    /**
     * @return the estadosEmpleador
     */
    public List<EstadoEmpleadorEnum> getEstadosEmpleador() {
        return estadosEmpleador;
    }

    /**
     * @param estadosEmpleador the estadosEmpleador to set
     */
    public void setEstadosEmpleador(List<EstadoEmpleadorEnum> estadosEmpleador) {
        this.estadosEmpleador = estadosEmpleador;
    }

    /**
     * @return the estadosAfiliacionTrabajador
     */
    public List<EstadoAfiliadoEnum> getEstadosAfiliacionTrabajador() {
        return estadosAfiliacionTrabajador;
    }

    /**
     * @param estadosAfiliacionTrabajador the estadosAfiliacionTrabajador to set
     */
    public void setEstadosAfiliacionTrabajador(List<EstadoAfiliadoEnum> estadosAfiliacionTrabajador) {
        this.estadosAfiliacionTrabajador = estadosAfiliacionTrabajador;
    }

    /**
     * @return the validarCondicionesTrabajador
     */
    public Boolean getValidarCondicionesTrabajador() {
        return validarCondicionesTrabajador;
    }

    /**
     * @param validarCondicionesTrabajador the validarCondicionesTrabajador to set
     */
    public void setValidarCondicionesTrabajador(Boolean validarCondicionesTrabajador) {
        this.validarCondicionesTrabajador = validarCondicionesTrabajador;
    }

    /**
     * @return the evaluarBeneficiarioDiferenteAConyuge
     */
    public Boolean getEvaluarBeneficiarioDiferenteAConyuge() {
        return evaluarBeneficiarioDiferenteAConyuge;
    }

    /**
     * @param evaluarBeneficiarioDiferenteAConyuge the evaluarBeneficiarioDiferenteAConyuge to set
     */
    public void setEvaluarBeneficiarioDiferenteAConyuge(Boolean evaluarBeneficiarioDiferenteAConyuge) {
        this.evaluarBeneficiarioDiferenteAConyuge = evaluarBeneficiarioDiferenteAConyuge;
    }

    /**
     * @return the evaluarTrabajadorConMultiAfiliacion
     */
    public Boolean getEvaluarTrabajadorConMultiAfiliacion() {
        return evaluarTrabajadorConMultiAfiliacion;
    }

    /**
     * @param evaluarTrabajadorConMultiAfiliacion the evaluarTrabajadorConMultiAfiliacion to set
     */
    public void setEvaluarTrabajadorConMultiAfiliacion(Boolean evaluarTrabajadorConMultiAfiliacion) {
        this.evaluarTrabajadorConMultiAfiliacion = evaluarTrabajadorConMultiAfiliacion;
    }

    /**
     * @return the evaluarTrabajadorDistintoAEmpleador
     */
    public Boolean getEvaluarTrabajadorDistintoAEmpleador() {
        return evaluarTrabajadorDistintoAEmpleador;
    }

    /**
     * @param evaluarTrabajadorDistintoAEmpleador the evaluarTrabajadorDistintoAEmpleador to set
     */
    public void setEvaluarTrabajadorDistintoAEmpleador(Boolean evaluarTrabajadorDistintoAEmpleador) {
        this.evaluarTrabajadorDistintoAEmpleador = evaluarTrabajadorDistintoAEmpleador;
    }

    /**
     * @return the estadosAporteValidosTrabajador
     */
    public List<EstadoRegistroAportesArchivoEnum> getEstadosAporteValidosTrabajador() {
        return estadosAporteValidosTrabajador;
    }

    /**
     * @param estadosAporteValidosTrabajador the estadosAporteValidosTrabajador to set
     */
    public void setEstadosAporteValidosTrabajador(List<EstadoRegistroAportesArchivoEnum> estadosAporteValidosTrabajador) {
        this.estadosAporteValidosTrabajador = estadosAporteValidosTrabajador;
    }

    /**
     * @return the evaluarDiasCotizadosYNovedadesTrabajador
     */
    public Boolean getEvaluarDiasCotizadosYNovedadesTrabajador() {
        return evaluarDiasCotizadosYNovedadesTrabajador;
    }

    /**
     * @param evaluarDiasCotizadosYNovedadesTrabajador the evaluarDiasCotizadosYNovedadesTrabajador to set
     */
    public void setEvaluarDiasCotizadosYNovedadesTrabajador(Boolean evaluarDiasCotizadosYNovedadesTrabajador) {
        this.evaluarDiasCotizadosYNovedadesTrabajador = evaluarDiasCotizadosYNovedadesTrabajador;
    }

    /**
     * @return the evaluarSalarioTrabajador
     */
    public Boolean getEvaluarSalarioTrabajador() {
        return evaluarSalarioTrabajador;
    }

    /**
     * @param evaluarSalarioTrabajador the evaluarSalarioTrabajador to set
     */
    public void setEvaluarSalarioTrabajador(Boolean evaluarSalarioTrabajador) {
        this.evaluarSalarioTrabajador = evaluarSalarioTrabajador;
    }

    /**
     * @return the validarCondicionesBeneficiario
     */
    public Boolean getValidarCondicionesBeneficiario() {
        return validarCondicionesBeneficiario;
    }

    /**
     * @param validarCondicionesBeneficiario the validarCondicionesBeneficiario to set
     */
    public void setValidarCondicionesBeneficiario(Boolean validarCondicionesBeneficiario) {
        this.validarCondicionesBeneficiario = validarCondicionesBeneficiario;
    }

    /**
     * @return the estadosAfilPadreConAfiliadoPrincipal
     */
    public List<EstadoAfiliadoEnum> getEstadosAfilPadreConAfiliadoPrincipal() {
        return estadosAfilPadreConAfiliadoPrincipal;
    }

    /**
     * @param estadosAfilPadreConAfiliadoPrincipal the estadosAfilPadreConAfiliadoPrincipal to set
     */
    public void setEstadosAfilPadreConAfiliadoPrincipal(List<EstadoAfiliadoEnum> estadosAfilPadreConAfiliadoPrincipal) {
        this.estadosAfilPadreConAfiliadoPrincipal = estadosAfilPadreConAfiliadoPrincipal;
    }

    /**
     * @return the evaluarBeneficiarioPadreEsAfiliadoPrincipal
     */
    public Boolean getEvaluarBeneficiarioPadreEsAfiliadoPrincipal() {
        return evaluarBeneficiarioPadreEsAfiliadoPrincipal;
    }

    /**
     * @param evaluarBeneficiarioPadreEsAfiliadoPrincipal the evaluarBeneficiarioPadreEsAfiliadoPrincipal to set
     */
    public void setEvaluarBeneficiarioPadreEsAfiliadoPrincipal(Boolean evaluarBeneficiarioPadreEsAfiliadoPrincipal) {
        this.evaluarBeneficiarioPadreEsAfiliadoPrincipal = evaluarBeneficiarioPadreEsAfiliadoPrincipal;
    }

    /**
     * @return the evaluarBeneficiarioPadreEsPersonaNatural
     */
    public Boolean getEvaluarBeneficiarioPadreEsPersonaNatural() {
        return evaluarBeneficiarioPadreEsPersonaNatural;
    }

    /**
     * @param evaluarBeneficiarioPadreEsPersonaNatural the evaluarBeneficiarioPadreEsPersonaNatural to set
     */
    public void setEvaluarBeneficiarioPadreEsPersonaNatural(Boolean evaluarBeneficiarioPadreEsPersonaNatural) {
        this.evaluarBeneficiarioPadreEsPersonaNatural = evaluarBeneficiarioPadreEsPersonaNatural;
    }

    /**
     * @return the evaluarBeneficiarioPadreTieneOtrosAportes
     */
    public Boolean getEvaluarBeneficiarioPadreTieneOtrosAportes() {
        return evaluarBeneficiarioPadreTieneOtrosAportes;
    }

    /**
     * @param evaluarBeneficiarioPadreTieneOtrosAportes the evaluarBeneficiarioPadreTieneOtrosAportes to set
     */
    public void setEvaluarBeneficiarioPadreTieneOtrosAportes(Boolean evaluarBeneficiarioPadreTieneOtrosAportes) {
        this.evaluarBeneficiarioPadreTieneOtrosAportes = evaluarBeneficiarioPadreTieneOtrosAportes;
    }

    /**
     * @return the evaluarBenefiarioTipoPadreDeOtrasPersonas
     */
    public Boolean getEvaluarBenefiarioTipoPadreDeOtrasPersonas() {
        return evaluarBenefiarioTipoPadreDeOtrasPersonas;
    }

    /**
     * @param evaluarBenefiarioTipoPadreDeOtrasPersonas the evaluarBenefiarioTipoPadreDeOtrasPersonas to set
     */
    public void setEvaluarBenefiarioTipoPadreDeOtrasPersonas(Boolean evaluarBenefiarioTipoPadreDeOtrasPersonas) {
        this.evaluarBenefiarioTipoPadreDeOtrasPersonas = evaluarBenefiarioTipoPadreDeOtrasPersonas;
    }

    /**
     * @return the evaluarBeneficiarioTipoPadreConCondicionInvalidez
     */
    public Boolean getEvaluarBeneficiarioTipoPadreConCondicionInvalidez() {
        return evaluarBeneficiarioTipoPadreConCondicionInvalidez;
    }

    /**
     * @param evaluarBeneficiarioTipoPadreConCondicionInvalidez the evaluarBeneficiarioTipoPadreConCondicionInvalidez to set
     */
    public void setEvaluarBeneficiarioTipoPadreConCondicionInvalidez(Boolean evaluarBeneficiarioTipoPadreConCondicionInvalidez) {
        this.evaluarBeneficiarioTipoPadreConCondicionInvalidez = evaluarBeneficiarioTipoPadreConCondicionInvalidez;
    }

    /**
     * @return the evaluarEdadBeneficiarioTipoPadre
     */
    public Boolean getEvaluarEdadBeneficiarioTipoPadre() {
        return evaluarEdadBeneficiarioTipoPadre;
    }

    /**
     * @param evaluarEdadBeneficiarioTipoPadre the evaluarEdadBeneficiarioTipoPadre to set
     */
    public void setEvaluarEdadBeneficiarioTipoPadre(Boolean evaluarEdadBeneficiarioTipoPadre) {
        this.evaluarEdadBeneficiarioTipoPadre = evaluarEdadBeneficiarioTipoPadre;
    }

    /**
     * @return the estadosAfilHijoConAfiliadoPrincipal
     */
    public List<EstadoAfiliadoEnum> getEstadosAfilHijoConAfiliadoPrincipal() {
        return estadosAfilHijoConAfiliadoPrincipal;
    }

    /**
     * @param estadosAfilHijoConAfiliadoPrincipal the estadosAfilHijoConAfiliadoPrincipal to set
     */
    public void setEstadosAfilHijoConAfiliadoPrincipal(List<EstadoAfiliadoEnum> estadosAfilHijoConAfiliadoPrincipal) {
        this.estadosAfilHijoConAfiliadoPrincipal = estadosAfilHijoConAfiliadoPrincipal;
    }

    /**
     * @return the evaluarBeneficiarioHijoEsAfiliadoPrincipal
     */
    public Boolean getEvaluarBeneficiarioHijoEsAfiliadoPrincipal() {
        return evaluarBeneficiarioHijoEsAfiliadoPrincipal;
    }

    /**
     * @param evaluarBeneficiarioHijoEsAfiliadoPrincipal the evaluarBeneficiarioHijoEsAfiliadoPrincipal to set
     */
    public void setEvaluarBeneficiarioHijoEsAfiliadoPrincipal(Boolean evaluarBeneficiarioHijoEsAfiliadoPrincipal) {
        this.evaluarBeneficiarioHijoEsAfiliadoPrincipal = evaluarBeneficiarioHijoEsAfiliadoPrincipal;
    }

    /**
     * @return the evaluarBeneficiarioHijoEsPersonaNatural
     */
    public Boolean getEvaluarBeneficiarioHijoEsPersonaNatural() {
        return evaluarBeneficiarioHijoEsPersonaNatural;
    }

    /**
     * @param evaluarBeneficiarioHijoEsPersonaNatural the evaluarBeneficiarioHijoEsPersonaNatural to set
     */
    public void setEvaluarBeneficiarioHijoEsPersonaNatural(Boolean evaluarBeneficiarioHijoEsPersonaNatural) {
        this.evaluarBeneficiarioHijoEsPersonaNatural = evaluarBeneficiarioHijoEsPersonaNatural;
    }

    /**
     * @return the evaluarBeneficiarioHijoTieneOtrosAportes
     */
    public Boolean getEvaluarBeneficiarioHijoTieneOtrosAportes() {
        return evaluarBeneficiarioHijoTieneOtrosAportes;
    }

    /**
     * @param evaluarBeneficiarioHijoTieneOtrosAportes the evaluarBeneficiarioHijoTieneOtrosAportes to set
     */
    public void setEvaluarBeneficiarioHijoTieneOtrosAportes(Boolean evaluarBeneficiarioHijoTieneOtrosAportes) {
        this.evaluarBeneficiarioHijoTieneOtrosAportes = evaluarBeneficiarioHijoTieneOtrosAportes;
    }

    /**
     * @return the evaluarBenefiarioTipoHijoDeOtrasPersonas
     */
    public Boolean getEvaluarBenefiarioTipoHijoDeOtrasPersonas() {
        return evaluarBenefiarioTipoHijoDeOtrasPersonas;
    }

    /**
     * @param evaluarBenefiarioTipoHijoDeOtrasPersonas the evaluarBenefiarioTipoHijoDeOtrasPersonas to set
     */
    public void setEvaluarBenefiarioTipoHijoDeOtrasPersonas(Boolean evaluarBenefiarioTipoHijoDeOtrasPersonas) {
        this.evaluarBenefiarioTipoHijoDeOtrasPersonas = evaluarBenefiarioTipoHijoDeOtrasPersonas;
    }

    /**
     * @return the evaluarBeneficiarioTipoHijoConCondicionInvalidez
     */
    public Boolean getEvaluarBeneficiarioTipoHijoConCondicionInvalidez() {
        return evaluarBeneficiarioTipoHijoConCondicionInvalidez;
    }

    /**
     * @param evaluarBeneficiarioTipoHijoConCondicionInvalidez the evaluarBeneficiarioTipoHijoConCondicionInvalidez to set
     */
    public void setEvaluarBeneficiarioTipoHijoConCondicionInvalidez(Boolean evaluarBeneficiarioTipoHijoConCondicionInvalidez) {
        this.evaluarBeneficiarioTipoHijoConCondicionInvalidez = evaluarBeneficiarioTipoHijoConCondicionInvalidez;
    }

    /**
     * @return the evaluarEdadBeneficiarioTipoHijo
     */
    public Boolean getEvaluarEdadBeneficiarioTipoHijo() {
        return evaluarEdadBeneficiarioTipoHijo;
    }

    /**
     * @param evaluarEdadBeneficiarioTipoHijo the evaluarEdadBeneficiarioTipoHijo to set
     */
    public void setEvaluarEdadBeneficiarioTipoHijo(Boolean evaluarEdadBeneficiarioTipoHijo) {
        this.evaluarEdadBeneficiarioTipoHijo = evaluarEdadBeneficiarioTipoHijo;
    }
}
