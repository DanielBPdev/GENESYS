/**
 *
 */
package com.asopagos.novedades.fovis.dto;

import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.fovis.MiembroHogarDTO;
import com.asopagos.enumeraciones.fovis.MedioPagoEnum;
import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.enumeraciones.fovis.MotivoDesistimientoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoEnajenacionPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoHabilitacionPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoRechazoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoRestitucionSubsidioEnum;
import com.asopagos.enumeraciones.fovis.TiempoSancionPostulacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los campos que se pueden modificar en Novedades
 * Regulares FOVIS.
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class DatosFOVISNovedadRegularDTO {

    /**
     * Tipo de Identificación de Jefe Hogar
     */
    private TipoIdentificacionEnum tipoIdJefeHogar;

    /**
     * Número de Identificación de Jefe de Hogar
     */
    private String numeroIdJefeHogar;

    /**
     * Tipo de Identificación de Integrante Hogar Actual
     */
    private TipoIdentificacionEnum tipoIdIntegrante;

    /**
     * Número de Identificación de Integrante Hogar Actual
     */
    private String numeroIdIntegrante;

    /**
     * Primer nombre Integrante Hogar Nuevo
     */
    private String primerNombreIntegrante;

    /**
     * Segundo nombre Integrante Hogar nuevo
     */
    private String segundoNombreIntegrante;

    /**
     * Primer apellido Integrante Hogar nuevo
     */
    private String primerApellidoIntegrante;

    /**
     * Segundo apellido Integrante Hogar nuevo
     */
    private String segundoApellidoIntegrante;

    /**
     * Tipo de Identificación de Integrante Hogar Nuevo
     */
    private TipoIdentificacionEnum tipoIdIntegranteNuevo;

    /**
     * Número de Identificación de Integrante Hogar Nuevo
     */
    private String numeroIdIntegranteNuevo;

    /* Novedad 10: Desistimiento Postulación */
    /**
     * Motivo de Desistimiento de la Postulación.
     */
    private MotivoDesistimientoPostulacionEnum motivoDesistimientoPostulacion;

    /* Novedad 10 - 13 - 18: Movilización Ahorro Previo para Hogar no asignado */
    /**
     * Indica si el tipo de Ahorro Programado sera Movilizado.
     */
    private Boolean ahorroProgramadoMovilizado;

    /**
     * Indica si el tipo de Ahorro Programado Contractual
     * con Evaluación crediticia favorable previa (FNA) sera Movilizado.
     */
    private Boolean ahorroEvaluacionCrediticiaMovilizado;

    /**
     * Indica si el tipo de Ahorro Cesantías sera Movilizado.
     */
    private Boolean cesantiasMovilizado;

    /* Novedad 14 Rechazo Postulación */
    /**
     * Motivo Rechazo Postulación
     */
    private MotivoRechazoPostulacionEnum motivoRechazoPostulacion;

    /**
     * Documento Soporte.
     */
    private String documentoSoporte;

    /* Novedad 15: Habilitación de Postulación rechazada. */
    /**
     * Motivo de Habilitación de la Postulación.
     */
    private MotivoHabilitacionPostulacionEnum motivoHabilitacion;

    /* Novedad 19: Prorroga */
    /**
     * Indica si la prorroga esta activa
     */
    private Boolean prorrogaActiva;

    /* Novedad 21: Restitución de Subsidio por incumplimiento. */

    /**
     * Valor a Restituir (Calculado automáticamente)
     */
    private BigDecimal valorRestituir;

    /**
     * Identifica si el hogar fue sancionado por Restitución.
     */
    private Boolean restituidoConSancion;

    /**
     * Identifica el Tiempo de sanción.
     */
    private TiempoSancionPostulacionEnum tiempoSancion;

    /**
     * Identifica el medio de Pago.
     */
    private MedioPagoEnum medioPago;

    /**
     * Tipo Identificacion.
     */
    private TipoIdentificacionEnum tipoIdQuienHaceDevolucion;

    /**
     * Valor del Numero de Identificacion
     */
    private String numeroIdQuienHaceDevolucion;

    /**
     * Valor del Numero de Identificacion
     */
    private String nombreCompleto;

    /**
     * Valor del monto de los rendimientos financieros
     */
    private BigDecimal rendimientoFinanciero;

    /**
     * Motivo de Restitución del Subsidio.
     */
    private MotivoRestitucionSubsidioEnum motivoRestitucion;

    /* Novedad 24: Autorización enajenación vivienda subsidiada. */
    /**
     * Datos de Ubicación de nueva Vivienda.
     */
    private UbicacionModeloDTO ubicacionProyecto;

    /**
     * Vive en casa propia?
     */
    private Boolean viveEnCasaPropia;

    /**
     * Reside en sector rural?
     */
    private Boolean resideEnSectorRural;

    /**
     * La enajenación fue autorizada por la CCF
     */
    private Boolean enajenacionAutorizada;

    /**
     * Motivo de Enajenación Hogar.
     */
    private MotivoEnajenacionPostulacionEnum motivoEnajenacion;

    /* Novedad 25: Conformación de nuevo Hogar */

    /**
     * Condicion para conformar de nuevo el hogar
     */
    private Boolean cumpleConformarNuevoHogar;

    /* Novedad 26: Movilización Ahorro Previo para Pago Oferente */

    /**
     * Indica que se requiere movilizacion de ahorro pago oferente
     */
    private Boolean requiereMovilizacionAhorroPagoOferente;

    /* Novedad 28: Levantar Inhabilidad o Sanción */
    /**
     * Indica que se inhabilita la persona para subsidio vivienda
     */
    private Boolean inhabilitadoSubsidioVivienda;

    /**
     * Indica si el hogar es sancionado
     */
    private Boolean hogarSancionado;

    /**
     * Valor SFV calculado asociado a la Postulación.
     */
    private BigDecimal valorAjusteIPCSFV;

    /**
     * Valor del monto del desembolso
     */
    private BigDecimal montoDesembolso;

    /**
     * Valor del monto reembolsado
     */
    private BigDecimal montoReembolsado;

    /**
     * Fecha de desembolso
     */
    private Long fechaDesembolso;

    //Novedad 31 : Ajuste y actualización valor SFV (Decreto 133 de 2018)
    /**
     * Información detallada de novedad
     */
    private DetalleNovedadFovisModeloDTO detalleNovedad;

    /**
     * Lista de miembros hogar
     */
    private List<MiembroHogarDTO> listaMiembros;

    /**
     * Lista de los ciclos vigentes en la novedad habilitacion postulación rechazada
     */
    private Long cicloAsignacionDestino;

    /**
     * @return the tipoIdJefeHogar
     */
    public TipoIdentificacionEnum getTipoIdJefeHogar() {
        return tipoIdJefeHogar;
    }

    /**
     * @param tipoIdJefeHogar
     *        the tipoIdJefeHogar to set
     */
    public void setTipoIdJefeHogar(TipoIdentificacionEnum tipoIdJefeHogar) {
        this.tipoIdJefeHogar = tipoIdJefeHogar;
    }

    /**
     * @return the numeroIdJefeHogar
     */
    public String getNumeroIdJefeHogar() {
        return numeroIdJefeHogar;
    }

    /**
     * @param numeroIdJefeHogar
     *        the numeroIdJefeHogar to set
     */
    public void setNumeroIdJefeHogar(String numeroIdJefeHogar) {
        this.numeroIdJefeHogar = numeroIdJefeHogar;
    }

    /**
     * @return the tipoIdIntegrante
     */
    public TipoIdentificacionEnum getTipoIdIntegrante() {
        return tipoIdIntegrante;
    }

    /**
     * @param tipoIdIntegrante
     *        the tipoIdIntegrante to set
     */
    public void setTipoIdIntegrante(TipoIdentificacionEnum tipoIdIntegrante) {
        this.tipoIdIntegrante = tipoIdIntegrante;
    }

    /**
     * @return the numeroIdIntegrante
     */
    public String getNumeroIdIntegrante() {
        return numeroIdIntegrante;
    }

    /**
     * @param numeroIdIntegrante
     *        the numeroIdIntegrante to set
     */
    public void setNumeroIdIntegrante(String numeroIdIntegrante) {
        this.numeroIdIntegrante = numeroIdIntegrante;
    }

    /**
     * @return the primerNombreIntegrante
     */
    public String getPrimerNombreIntegrante() {
        return primerNombreIntegrante;
    }

    /**
     * @param primerNombreIntegrante
     *        the primerNombreIntegrante to set
     */
    public void setPrimerNombreIntegrante(String primerNombreIntegrante) {
        this.primerNombreIntegrante = primerNombreIntegrante;
    }

    /**
     * @return the segundoNombreIntegrante
     */
    public String getSegundoNombreIntegrante() {
        return segundoNombreIntegrante;
    }

    /**
     * @param segundoNombreIntegrante
     *        the segundoNombreIntegrante to set
     */
    public void setSegundoNombreIntegrante(String segundoNombreIntegrante) {
        this.segundoNombreIntegrante = segundoNombreIntegrante;
    }

    /**
     * @return the primerApellidoIntegrante
     */
    public String getPrimerApellidoIntegrante() {
        return primerApellidoIntegrante;
    }

    /**
     * @param primerApellidoIntegrante
     *        the primerApellidoIntegrante to set
     */
    public void setPrimerApellidoIntegrante(String primerApellidoIntegrante) {
        this.primerApellidoIntegrante = primerApellidoIntegrante;
    }

    /**
     * @return the segundoApellidoIntegrante
     */
    public String getSegundoApellidoIntegrante() {
        return segundoApellidoIntegrante;
    }

    /**
     * @param segundoApellidoIntegrante
     *        the segundoApellidoIntegrante to set
     */
    public void setSegundoApellidoIntegrante(String segundoApellidoIntegrante) {
        this.segundoApellidoIntegrante = segundoApellidoIntegrante;
    }

    /**
     * @return the tipoIdIntegranteNuevo
     */
    public TipoIdentificacionEnum getTipoIdIntegranteNuevo() {
        return tipoIdIntegranteNuevo;
    }

    /**
     * @param tipoIdIntegranteNuevo
     *        the tipoIdIntegranteNuevo to set
     */
    public void setTipoIdIntegranteNuevo(TipoIdentificacionEnum tipoIdIntegranteNuevo) {
        this.tipoIdIntegranteNuevo = tipoIdIntegranteNuevo;
    }

    /**
     * @return the numeroIdIntegranteNuevo
     */
    public String getNumeroIdIntegranteNuevo() {
        return numeroIdIntegranteNuevo;
    }

    /**
     * @param numeroIdIntegranteNuevo
     *        the numeroIdIntegranteNuevo to set
     */
    public void setNumeroIdIntegranteNuevo(String numeroIdIntegranteNuevo) {
        this.numeroIdIntegranteNuevo = numeroIdIntegranteNuevo;
    }

    /**
     * @return the motivoDesistimientoPostulacion
     */
    public MotivoDesistimientoPostulacionEnum getMotivoDesistimientoPostulacion() {
        return motivoDesistimientoPostulacion;
    }

    /**
     * @param motivoDesistimientoPostulacion
     *        the motivoDesistimientoPostulacion to set
     */
    public void setMotivoDesistimientoPostulacion(MotivoDesistimientoPostulacionEnum motivoDesistimientoPostulacion) {
        this.motivoDesistimientoPostulacion = motivoDesistimientoPostulacion;
    }

    /**
     * @return the ahorroProgramadoMobilizado
     */
    public Boolean getAhorroProgramadoMovilizado() {
        return ahorroProgramadoMovilizado;
    }

    /**
     * @param ahorroProgramadoMobilizado
     *        the ahorroProgramadoMobilizado to set
     */
    public void setAhorroProgramadoMovilizado(Boolean ahorroProgramadoMovilizado) {
        this.ahorroProgramadoMovilizado = ahorroProgramadoMovilizado;
    }

    /**
     * @return the ahorroEvaluacionCrediticiaMobilizado
     */
    public Boolean getAhorroEvaluacionCrediticiaMovilizado() {
        return ahorroEvaluacionCrediticiaMovilizado;
    }

    /**
     * @param ahorroEvaluacionCrediticiaMobilizado
     *        the ahorroEvaluacionCrediticiaMobilizado to set
     */
    public void setAhorroEvaluacionCrediticiaMovilizado(Boolean ahorroEvaluacionCrediticiaMovilizado) {
        this.ahorroEvaluacionCrediticiaMovilizado = ahorroEvaluacionCrediticiaMovilizado;
    }

    /**
     * @return the cesantiasMovilizado
     */
    public Boolean getCesantiasMovilizado() {
        return cesantiasMovilizado;
    }

    /**
     * @param cesantiasMovilizado
     *        the cesantiasMovilizado to set
     */
    public void setCesantiasMovilizado(Boolean cesantiasMovilizado) {
        this.cesantiasMovilizado = cesantiasMovilizado;
    }

    /**
     * @return the motivoRechazoPostulacion
     */
    public MotivoRechazoPostulacionEnum getMotivoRechazoPostulacion() {
        return motivoRechazoPostulacion;
    }

    /**
     * @param motivoRechazoPostulacion
     *        the motivoRechazoPostulacion to set
     */
    public void setMotivoRechazoPostulacion(MotivoRechazoPostulacionEnum motivoRechazoPostulacion) {
        this.motivoRechazoPostulacion = motivoRechazoPostulacion;
    }

    /**
     * @return the documentoSoporte
     */
    public String getDocumentoSoporte() {
        return documentoSoporte;
    }

    /**
     * @param documentoSoporte
     *        the documentoSoporte to set
     */
    public void setDocumentoSoporte(String documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    /**
     * @return the motivoHabilitacion
     */
    public MotivoHabilitacionPostulacionEnum getMotivoHabilitacion() {
        return motivoHabilitacion;
    }

    /**
     * @param motivoHabilitacion
     *        the motivoHabilitacion to set
     */
    public void setMotivoHabilitacion(MotivoHabilitacionPostulacionEnum motivoHabilitacion) {
        this.motivoHabilitacion = motivoHabilitacion;
    }

    /**
     * @return the valorRestituir
     */
    public BigDecimal getValorRestituir() {
        return valorRestituir;
    }

    /**
     * @param valorRestituir
     *        the valorRestituir to set
     */
    public void setValorRestituir(BigDecimal valorRestituir) {
        this.valorRestituir = valorRestituir;
    }

    /**
     * @return the restituidoConSancion
     */
    public Boolean getRestituidoConSancion() {
        return restituidoConSancion;
    }

    /**
     * @param restituidoConSancion
     *        the restituidoConSancion to set
     */
    public void setRestituidoConSancion(Boolean restituidoConSancion) {
        this.restituidoConSancion = restituidoConSancion;
    }

    /**
     * @return the tiempoSancion
     */
    public TiempoSancionPostulacionEnum getTiempoSancion() {
        return tiempoSancion;
    }

    /**
     * @param tiempoSancion
     *        the tiempoSancion to set
     */
    public void setTiempoSancion(TiempoSancionPostulacionEnum tiempoSancion) {
        this.tiempoSancion = tiempoSancion;
    }

    /**
     * @return the motivoRestitucion
     */
    public MotivoRestitucionSubsidioEnum getMotivoRestitucion() {
        return motivoRestitucion;
    }

    /**
     * @param motivoRestitucion
     *        the motivoRestitucion to set
     */
    public void setMotivoRestitucion(MotivoRestitucionSubsidioEnum motivoRestitucion) {
        this.motivoRestitucion = motivoRestitucion;
    }

    /**
     * @return the ubicacionProyecto
     */
    public UbicacionModeloDTO getUbicacionProyecto() {
        return ubicacionProyecto;
    }

    /**
     * @param ubicacionProyecto
     *        the ubicacionProyecto to set
     */
    public void setUbicacionProyecto(UbicacionModeloDTO ubicacionProyecto) {
        this.ubicacionProyecto = ubicacionProyecto;
    }

    /**
     * @return the enajenacionAutorizada
     */
    public Boolean getEnajenacionAutorizada() {
        return enajenacionAutorizada;
    }

    /**
     * @param enajenacionAutorizada
     *        the enajenacionAutorizada to set
     */
    public void setEnajenacionAutorizada(Boolean enajenacionAutorizada) {
        this.enajenacionAutorizada = enajenacionAutorizada;
    }

    /**
     * @return the motivoEnajenacion
     */
    public MotivoEnajenacionPostulacionEnum getMotivoEnajenacion() {
        return motivoEnajenacion;
    }

    /**
     * @param motivoEnajenacion
     *        the motivoEnajenacion to set
     */
    public void setMotivoEnajenacion(MotivoEnajenacionPostulacionEnum motivoEnajenacion) {
        this.motivoEnajenacion = motivoEnajenacion;
    }

    /**
     * @return the cumpleConformarNuevoHogar
     */
    public Boolean getCumpleConformarNuevoHogar() {
        return cumpleConformarNuevoHogar;
    }

    /**
     * @param cumpleConformarNuevoHogar
     *        the cumpleConformarNuevoHogar to set
     */
    public void setCumpleConformarNuevoHogar(Boolean cumpleConformarNuevoHogar) {
        this.cumpleConformarNuevoHogar = cumpleConformarNuevoHogar;
    }

    /**
     * @return the requiereMovilizacionAhorroPagoOferente
     */
    public Boolean getRequiereMovilizacionAhorroPagoOferente() {
        return requiereMovilizacionAhorroPagoOferente;
    }

    /**
     * @param requiereMovilizacionAhorroPagoOferente
     *        the requiereMovilizacionAhorroPagoOferente to set
     */
    public void setRequiereMovilizacionAhorroPagoOferente(Boolean requiereMovilizacionAhorroPagoOferente) {
        this.requiereMovilizacionAhorroPagoOferente = requiereMovilizacionAhorroPagoOferente;
    }

    /**
     * @return the inhabilitadoSubsidioVivienda
     */
    public Boolean getInhabilitadoSubsidioVivienda() {
        return inhabilitadoSubsidioVivienda;
    }

    /**
     * @param inhabilitadoSubsidioVivienda
     *        the inhabilitadoSubsidioVivienda to set
     */
    public void setInhabilitadoSubsidioVivienda(Boolean inhabilitadoSubsidioVivienda) {
        this.inhabilitadoSubsidioVivienda = inhabilitadoSubsidioVivienda;
    }

    /**
     * @return the viveEnCasaPropia
     */
    public Boolean getViveEnCasaPropia() {
        return viveEnCasaPropia;
    }

    /**
     * @param viveEnCasaPropia
     *        the viveEnCasaPropia to set
     */
    public void setViveEnCasaPropia(Boolean viveEnCasaPropia) {
        this.viveEnCasaPropia = viveEnCasaPropia;
    }

    /**
     * @return the resideEnSectorRural
     */
    public Boolean getResideEnSectorRural() {
        return resideEnSectorRural;
    }

    /**
     * @param resideEnSectorRural
     *        the resideEnSectorRural to set
     */
    public void setResideEnSectorRural(Boolean resideEnSectorRural) {
        this.resideEnSectorRural = resideEnSectorRural;
    }

    /**
     * @return the hogarSancionado
     */
    public Boolean getHogarSancionado() {
        return hogarSancionado;
    }

    /**
     * @param hogarSancionado
     *        the hogarSancionado to set
     */
    public void setHogarSancionado(Boolean hogarSancionado) {
        this.hogarSancionado = hogarSancionado;
    }

    /**
     * @return the valorAjusteIPCSFV
     */
    public BigDecimal getValorAjusteIPCSFV() {
        return valorAjusteIPCSFV;
    }

    /**
     * @param valorAjusteIPCSFV
     *        the valorAjusteIPCSFV to set
     */
    public void setValorAjusteIPCSFV(BigDecimal valorAjusteIPCSFV) {
        this.valorAjusteIPCSFV = valorAjusteIPCSFV;
    }

    /**
     * @return the prorrogaActiva
     */
    public Boolean getProrrogaActiva() {
        return prorrogaActiva;
    }

    /**
     * @param prorrogaActiva
     *        the prorrogaActiva to set
     */
    public void setProrrogaActiva(Boolean prorrogaActiva) {
        this.prorrogaActiva = prorrogaActiva;
    }

    /**
     * @return the montoDesembolso
     */
    public BigDecimal getMontoDesembolso() {
        return montoDesembolso;
    }

    /**
     * @param montoDesembolso
     *        the montoDesembolso to set
     */
    public void setMontoDesembolso(BigDecimal montoDesembolso) {
        this.montoDesembolso = montoDesembolso;
    }

    /**
     * @return the montoReembolsado
     */
    public BigDecimal getMontoReembolsado() {
        return montoReembolsado;
    }

    /**
     * @param montoReembolsado
     *        the montoReembolsado to set
     */
    public void setMontoReembolsado(BigDecimal montoReembolsado) {
        this.montoReembolsado = montoReembolsado;
    }

    /**
     * @return the fechaDesembolso
     */
    public Long getFechaDesembolso() {
        return fechaDesembolso;
    }

    /**
     * @param fechaDesembolso
     *        the fechaDesembolso to set
     */
    public void setFechaDesembolso(Long fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    /**
     * @return the detalleNovedad
     */
    public DetalleNovedadFovisModeloDTO getDetalleNovedad() {
        return detalleNovedad;
    }

    /**
     * @param detalleNovedad
     *        the detalleNovedad to set
     */
    public void setDetalleNovedad(DetalleNovedadFovisModeloDTO detalleNovedad) {
        this.detalleNovedad = detalleNovedad;
    }

    /**
     * @return the listaMiembros
     */
    public List<MiembroHogarDTO> getListaMiembros() {
        return listaMiembros;
    }

    /**
     * @param listaMiembros
     *        the listaMiembros to set
     */
    public void setListaMiembros(List<MiembroHogarDTO> listaMiembros) {
        this.listaMiembros = listaMiembros;
    }

    public MedioPagoEnum getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPagoEnum medioPago) {
        this.medioPago = medioPago;
    }

    public TipoIdentificacionEnum getTipoIdQuienHaceDevolucion() {
        return tipoIdQuienHaceDevolucion;
    }

    public void setTipoIdQuienHaceDevolucion(TipoIdentificacionEnum tipoIdQuienHaceDevolucion) {
        this.tipoIdQuienHaceDevolucion = tipoIdQuienHaceDevolucion;
    }

    public String getNumeroIdQuienHaceDevolucion() {
        return numeroIdQuienHaceDevolucion;
    }

    public void setNumeroIdQuienHaceDevolucion(String numeroIdQuienHaceDevolucion) {
        this.numeroIdQuienHaceDevolucion = numeroIdQuienHaceDevolucion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public BigDecimal getRendimientoFinanciero() {
        return rendimientoFinanciero;
    }

    public void setRendimientoFinanciero(BigDecimal rendimientoFinanciero) {
        this.rendimientoFinanciero = rendimientoFinanciero;
        
    }

    public Long getCicloAsignacionDestino() {
        return cicloAsignacionDestino;
    }
    public void setCicloAsignacionDestino(Long cicloAsignacionDestino) {
        this.cicloAsignacionDestino = cicloAsignacionDestino;
    }

}