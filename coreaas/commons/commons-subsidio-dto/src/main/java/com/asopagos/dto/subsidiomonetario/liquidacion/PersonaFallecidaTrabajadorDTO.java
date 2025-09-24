package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO Usado al seleccionar una persona en sudsidio fallecimiento de trabajador <br/>
 * <b>Módulo:</b> Asopagos - HU-317-503 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class PersonaFallecidaTrabajadorDTO implements Serializable {

    private static final long serialVersionUID = -7402083420987091444L;
    
    /* CAMPOS ASOCIADOS A LOS DATOS DE LA PERSONA */
    
    /** Id de la persona */
    private Long idPersona;
    
    /** Tipo de identificacion de la persona */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /** Número de identificacion de la persona */
    private String numeroIdentificacion;
    
    /** Primer nombre de la persona */ 
    private String primerNombre;
    
    /** Segundo nombre de la persona */
    private String segundoNombre;
    
    /** Primer apellido de la persona */
    private String primerApellido;
    
    /** Segundo apellido de la persona */
    private String segundoApellido;
    
    /** Fecha de nacimiento de la persona */
    private Date fechaNacimiento;
    
    /** Fecha de fallecido de la persona */
    private Date fechaFallecido;
 
    /** Indicador de si la persona ha fallecido */    
    private Boolean fallecido;
    
    /** Descripción del estado del rol afiliado */
    private EstadoAfiliadoEnum estadoAfiliado;
    
    /** Descripción de la desafiliacion del afiliado */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    
    /** Descripción del canal de recepción de las solicitudes */
    private CanalRecepcionEnum canalRecepcion;
    
    /** Fecha de radicación de la solicitud */
    private Date fechaRadicacion;
    
    /** Tipo de transaccion asociada a una solicitud */
    private TipoTransaccionEnum tipoTransaccion;
    
    // Verificar liquidacion en proceso
    /** Variable para determinar si hay liqudacion en proceso */
    private Boolean liquidacionEnProceso;
    
    /* VARIABLES ASOCIADAS A LAS CONDICIONES DE VALIDACION PARA TRABAJADOR FALLECIDO */
    /** Caso 1: Afiliado activo en caja luego de fallecido */
    private Boolean activoEnCaja;
    
    /** Caso 2: El dia anterior a la fecha de defuncion estaba activo */
    private Boolean diaAnteriorFechaDefuncionActivo;
    
    /** Caso 3: Rango entre fallecimiento y registro de la solicitud es valido */
    private Boolean rangoFallecimientoSolitudValido;
    
    /** Caso 4: El motivo de desafilicion es fallecimiento y canal es presencial */
    private Boolean motivoFallecimientoCanalPresencial;
    
    /** Caso 5: Beneficiario distinto a conyuge activo al menos el dia anterior a la muerte del afiliado */
    private Boolean beneficiarioActivoAlMorirAfiliado;
    
    // LISTA DE BENEFICIARIOS
    private List<BeneficiariosAfiliadoDTO> listaBeneficiarios;
    
    /* CONDICIONES INICIALES DEL AFILIADO (HU-317-506) */

    /**
     * C1: Validar que el trabajador tenga “Estado del afiliado principal como trabajador dependiente” igual a “Activo”
     */
    private Boolean afiliadoPrincipalActivo;

    /**
     * C2: Validar que el afiliado tenga al menos un beneficiario distinto a “Cónyuge”
     */
    private Boolean tieneBeneficiarioDistintoConyuge;

    /**
     * C3: Validar que en al menos un día del periodo en el que fallece el beneficiario, el trabajador tenga
     * “Estado de afiliación como dependiente” igual a “Activo”
     */
    private Boolean trabajadorActivoAlFallecerBeneficiario;

    /**
     * C4: Si el afiliado principal tiene relacionado más de un beneficiario fallecido (distinto a cónyuge), 
     * Validar si las “Fechas de defunción” son iguales
     */
    private Boolean masDeUnBeneficiarioFallecido;

    /*
    * Variable que indica si una persona ha recibido subisidio en periodo de fallecimiento
    */
    private Boolean validacionSubsidioFallecimientoBeneficiario;

    // Contructor vacio
    public PersonaFallecidaTrabajadorDTO(){}

    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param fechaNacimiento
     * @param fechaFallecido
     * @param fallecido
     * @param estadoAfiliado
     * @param motivoDesafiliacion
     * @param canalRecepcion
     * @param fechaRadicacion
     */
    public PersonaFallecidaTrabajadorDTO(Long idPersona, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento, Date fechaFallecido,
            Boolean fallecido, EstadoAfiliadoEnum estadoAfiliado, MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion,
            CanalRecepcionEnum canalRecepcion, Date fechaRadicacion, TipoTransaccionEnum tipoTransaccion) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecido = fechaFallecido;
        this.fallecido = fallecido;
        this.estadoAfiliado = estadoAfiliado;
        this.motivoDesafiliacion = motivoDesafiliacion;
        this.canalRecepcion = canalRecepcion;
        this.fechaRadicacion = fechaRadicacion;
        this.tipoTransaccion = tipoTransaccion;
    }
    
    /**
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param fechaNacimiento
     * @param fechaFallecido
     * @param fallecido
     */
    public PersonaFallecidaTrabajadorDTO(Long idPersona, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento, Date fechaFallecido,
            Boolean fallecido) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecido = fechaFallecido;
        this.fallecido = fallecido;
    }
    
    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the fechaFallecido
     */
    public Date getFechaFallecido() {
        return fechaFallecido;
    }

    /**
     * @param fechaFallecido the fechaFallecido to set
     */
    public void setFechaFallecido(Date fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    /**
     * @return the fallecido
     */
    public Boolean getFallecido() {
        return fallecido;
    }

    /**
     * @param fallecido the fallecido to set
     */
    public void setFallecido(Boolean fallecido) {
        this.fallecido = fallecido;
    }

    /**
     * @return the estadoAfiliado
     */
    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return estadoAfiliado;
    }

    /**
     * @param estadoAfiliado the estadoAfiliado to set
     */
    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the liquidacionEnProceso
     */
    public Boolean getLiquidacionEnProceso() {
        return liquidacionEnProceso;
    }

    /**
     * @param liquidacionEnProceso the liquidacionEnProceso to set
     */
    public void setLiquidacionEnProceso(Boolean liquidacionEnProceso) {
        this.liquidacionEnProceso = liquidacionEnProceso;
    }

    /**
     * @return the activoEnCaja
     */
    public Boolean getActivoEnCaja() {
        return activoEnCaja;
    }

    /**
     * @param activoEnCaja the activoEnCaja to set
     */
    public void setActivoEnCaja(Boolean activoEnCaja) {
        this.activoEnCaja = activoEnCaja;
    }

    /**
     * @return the diaAnteriorFechaDefuncionActivo
     */
    public Boolean getDiaAnteriorFechaDefuncionActivo() {
        return diaAnteriorFechaDefuncionActivo;
    }

    /**
     * @param diaAnteriorFechaDefuncionActivo the diaAnteriorFechaDefuncionActivo to set
     */
    public void setDiaAnteriorFechaDefuncionActivo(Boolean diaAnteriorFechaDefuncionActivo) {
        this.diaAnteriorFechaDefuncionActivo = diaAnteriorFechaDefuncionActivo;
    }

    /**
     * @return the rangoFallecimientoSolitudValido
     */
    public Boolean getRangoFallecimientoSolitudValido() {
        return rangoFallecimientoSolitudValido;
    }

    /**
     * @param rangoFallecimientoSolitudValido the rangoFallecimientoSolitudValido to set
     */
    public void setRangoFallecimientoSolitudValido(Boolean rangoFallecimientoSolitudValido) {
        this.rangoFallecimientoSolitudValido = rangoFallecimientoSolitudValido;
    }

    /**
     * @return the motivoFallecimientoCanalPresencial
     */
    public Boolean getMotivoFallecimientoCanalPresencial() {
        return motivoFallecimientoCanalPresencial;
    }

    /**
     * @param motivoFallecimientoCanalPresencial the motivoFallecimientoCanalPresencial to set
     */
    public void setMotivoFallecimientoCanalPresencial(Boolean motivoFallecimientoCanalPresencial) {
        this.motivoFallecimientoCanalPresencial = motivoFallecimientoCanalPresencial;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the listaBeneficiarios
     */
    public List<BeneficiariosAfiliadoDTO> getListaBeneficiarios() {
        return listaBeneficiarios;
    }

    /**
     * @param listaBeneficiarios the listaBeneficiarios to set
     */
    public void setListaBeneficiarios(List<BeneficiariosAfiliadoDTO> listaBeneficiarios) {
        this.listaBeneficiarios = listaBeneficiarios;
    }

    /**
     * @return the beneficiarioActivoAlMorirAfiliado
     */
    public Boolean getBeneficiarioActivoAlMorirAfiliado() {
        return beneficiarioActivoAlMorirAfiliado;
    }

    /**
     * @param beneficiarioActivoAlMorirAfiliado the beneficiarioActivoAlMorirAfiliado to set
     */
    public void setBeneficiarioActivoAlMorirAfiliado(Boolean beneficiarioActivoAlMorirAfiliado) {
        this.beneficiarioActivoAlMorirAfiliado = beneficiarioActivoAlMorirAfiliado;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the afiliadoPrincipalActivo
     */
    public Boolean getAfiliadoPrincipalActivo() {
        return afiliadoPrincipalActivo;
    }

    /**
     * @param afiliadoPrincipalActivo the afiliadoPrincipalActivo to set
     */
    public void setAfiliadoPrincipalActivo(Boolean afiliadoPrincipalActivo) {
        this.afiliadoPrincipalActivo = afiliadoPrincipalActivo;
    }

    /**
     * @return the tieneBeneficiarioDistintoConyuge
     */
    public Boolean getTieneBeneficiarioDistintoConyuge() {
        return tieneBeneficiarioDistintoConyuge;
    }

    /**
     * @param tieneBeneficiarioDistintoConyuge the tieneBeneficiarioDistintoConyuge to set
     */
    public void setTieneBeneficiarioDistintoConyuge(Boolean tieneBeneficiarioDistintoConyuge) {
        this.tieneBeneficiarioDistintoConyuge = tieneBeneficiarioDistintoConyuge;
    }

    /**
     * @return the trabajadorActivoAlFallecerBeneficiario
     */
    public Boolean getTrabajadorActivoAlFallecerBeneficiario() {
        return trabajadorActivoAlFallecerBeneficiario;
    }

    /**
     * @param trabajadorActivoAlFallecerBeneficiario the trabajadorActivoAlFallecerBeneficiario to set
     */
    public void setTrabajadorActivoAlFallecerBeneficiario(Boolean trabajadorActivoAlFallecerBeneficiario) {
        this.trabajadorActivoAlFallecerBeneficiario = trabajadorActivoAlFallecerBeneficiario;
    }

    /**
     * @return the masDeUnBeneficiarioFallecido
     */
    public Boolean getMasDeUnBeneficiarioFallecido() {
        return masDeUnBeneficiarioFallecido;
    }

    /**
     * @param masDeUnBeneficiarioFallecido the masDeUnBeneficiarioFallecido to set
     */
    public void setMasDeUnBeneficiarioFallecido(Boolean masDeUnBeneficiarioFallecido) {
        this.masDeUnBeneficiarioFallecido = masDeUnBeneficiarioFallecido;
    }

    public Boolean getValidacionSubsidioFallecimientoBeneficiario() {
        return validacionSubsidioFallecimientoBeneficiario;
    }

    public void setValidacionSubsidioFallecimientoBeneficiario(Boolean validacionSubsidioFallecimientoBeneficiario) {
        this.validacionSubsidioFallecimientoBeneficiario = validacionSubsidioFallecimientoBeneficiario;
    }


}
