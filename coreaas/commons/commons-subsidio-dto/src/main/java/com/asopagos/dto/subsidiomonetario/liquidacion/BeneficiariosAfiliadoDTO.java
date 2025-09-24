package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase DTO que recibe la consulta de los beneficiarios para un afiliado asociado <br/>
 * <b>Módulo:</b> Asopagos - HU 317-144<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class BeneficiariosAfiliadoDTO implements Serializable {

    private static final long serialVersionUID = 2170225283650311559L;

    /**
     * Constructor usado para persistencia de datos en PersonaLiquidacionEspecifica
     * @param idGrupoFamiliar
     * @param idBeneficiario
     * @param idAfiliadoPrincipal
     * @param idBeneficiarioDetalle
     * @param idEmpleador
     */
    public BeneficiariosAfiliadoDTO(Long idGrupoFamiliar, Long idBeneficiario, Long idAfiliadoPrincipal, Long idBeneficiarioDetalle,
            Long idEmpleador) {
        this.idGrupoFamiliar = idGrupoFamiliar;
        this.idBeneficiario = idBeneficiario;
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
        this.idEmpleador = idEmpleador;
    }

    /**
     * Constructor usado para persistencia de datos en PersonaLiquidacionEspecifica v2
     * @param idAfiliadoPrincipal
     * @param idEmpleador
     */
    public BeneficiariosAfiliadoDTO(Long idAfiliadoPrincipal, Long idEmpleador) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.idEmpleador = idEmpleador;
    }

    /**
     * Constructor usado para persistencia de datos en PersonaLiquidacionEspecifica v3
     * @param idAfiliadoPrincipal
     * @param idEmpleador
     * @param idBeneficiarioDetalle
     */
    public BeneficiariosAfiliadoDTO(Long idAfiliadoPrincipal, Long idEmpleador, Long idBeneficiarioDetalle) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.idEmpleador = idEmpleador;
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * Constructor usado en subsidio especifico por reconocimiento
     * @param tipoDocumentoBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoBeneficiario
     * @param idGrupoFamiliar
     * @param estadoBeneficiarioAfiliado
     * @param idBeneficiario
     * @param nombreCompletoBeneficiario
     */
    public BeneficiariosAfiliadoDTO(TipoIdentificacionEnum tipoDocumentoBeneficiario, String numeroIdentificacionBeneficiario,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, ClasificacionEnum tipoBeneficiario,
            Long idGrupoFamiliar, EstadoAfiliadoEnum estadoBeneficiarioAfiliado, Long idBeneficiario, Long idAfiliadoPrincipal,
            Long idBeneficiarioDetalle, Long idEmpleador) {
        this.tipoDocumentoBeneficiario = tipoDocumentoBeneficiario;
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
        this.tipoBeneficiario = tipoBeneficiario;
        this.idGrupoFamiliar = idGrupoFamiliar;
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
        this.idBeneficiario = idBeneficiario;
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
        this.idEmpleador = idEmpleador;

        // Concatenar el nombre completo
        this.nombreCompletoBeneficiario = Stream.of(primerNombre, segundoNombre, primerApellido, segundoApellido).filter(s -> s != null)
                .map(s -> s + " ").collect(Collectors.joining()).trim();
    }

    /**
     * Constructor usado en HU-503
     * @param tipoDocumentoBeneficiario
     * @param numeroIdentificacionBeneficiario
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoBeneficiario
     * @param idGrupoFamiliar
     * @param estadoBeneficiarioAfiliado
     * @param idBeneficiario
     * @param idAfiliadoPrincipal
     * @param idBeneficiarioDetalle
     * @param fechaFallecido
     */
    public BeneficiariosAfiliadoDTO(TipoIdentificacionEnum tipoDocumentoBeneficiario, String numeroIdentificacionBeneficiario,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, ClasificacionEnum tipoBeneficiario,
            Object idGrupoFamiliar, EstadoAfiliadoEnum estadoBeneficiarioAfiliado, Long idBeneficiario, Long idAfiliadoPrincipal,
            Long idBeneficiarioDetalle, Date fechaFallecido) {
        this.tipoDocumentoBeneficiario = tipoDocumentoBeneficiario;
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
        this.tipoBeneficiario = tipoBeneficiario;
        this.idGrupoFamiliar = idGrupoFamiliar instanceof Long ?(Long)idGrupoFamiliar : Long.valueOf(((Byte)idGrupoFamiliar));
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
        this.idBeneficiario = idBeneficiario;
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;

        // Concatenar el nombre completo
        this.nombreCompletoBeneficiario = Stream.of(primerNombre, segundoNombre, primerApellido, segundoApellido).filter(s -> s != null)
                .map(s -> s + " ").collect(Collectors.joining()).trim();
        //se obtiene la fecha de fallecimiento del beneficiario
        this.fechaFallecido = fechaFallecido;
    }

    /** Tipo documento del beneficiario */
    private TipoIdentificacionEnum tipoDocumentoBeneficiario;

    /** Número de identificacion del beneficiario */
    private String numeroIdentificacionBeneficiario;

    /** Primer nombre del beneficiario */
    private String primerNombre;

    /** Segundo nombre del beneficiario */
    private String segundoNombre;

    /** Primer apellido del beneficiario */
    private String primerApellido;

    /** Segundo apellido del beneficiario */
    private String segundoApellido;

    /** Tipo de beneficiario */
    private ClasificacionEnum tipoBeneficiario;

    /** Id del grupo familiar */
    private Long idGrupoFamiliar;

    /** Estado del beneficiario con respecto a la caja */
    private EstadoAfiliadoEnum estadoBeneficiarioAfiliado;

    /** Id del beneficiario */
    private Long idBeneficiario;

    /** Nombre completo del beneficiario */
    private String nombreCompletoBeneficiario;

    /** Id afiliado principal asociado al beneficiario */
    private Long idAfiliadoPrincipal;

    /** Id Beneficiario detalle asociado al beneficiario */
    private Long idBeneficiarioDetalle;

    /** Id Empleador al cual esta asociado el rol afiliado */
    private Long idEmpleador;

    /** HU-317-503 Booleano que indica si en la ultima liquidacion el beneficiario ha tenido derecho */
    private Boolean derechoUltimaLiquidacion;

    /** Fecha en la cual el beneficiario fallecio */
    private Date fechaFallecido;

    /* CONDICIONES BENEFICIARIO HU-317-506 */

    /**
     * C3: Validar que en al menos un día del periodo en el que fallece el beneficiario, el trabajador tenga
     * “Estado de afiliación como dependiente” igual a “Activo”
     */
    private Boolean trabajadorActivoAlFallecerBeneficiario;

    /**
     * C5: Validar que el beneficiario fallecido, tenga “Estado de afiliación con respecto al afiliado
     * principal” distinto a “Activo” (al momento de la consulta).
     */
    private Boolean estadoDistintoDeActivo;

    /**
     * C6: Validar que el beneficiario al menos al día anterior a la “Fecha de defunción” tenga “Estado
     * de afiliación con respecto al afiliado principal” igual a “Activo”:
     */
    private Boolean activoAntesDeMorir;

    /**
     * C7: Validar que el rango de días entre la “Fecha de defunción” y la “Fecha de registro” de la
     * novedad, sea menor o igual a los días parametrizados por la caja para poder registrar una solicitud
     * de subsidio por fallecimiento
     */
    private Boolean rangoValidoDiasParametrizacion;

    /**
     * C8: Validar que el último “Motivo de desafiliación” sea “Fallecimiento” y que el “Canal” de registro
     * de la novedad sea “Presencial”
     */
    private Boolean motivoFallecimientoCanalPresencial;

    /**
     * C9: Validar que el beneficiario por el grupo familiar al que se le evalúa tiene registrado un 
     * administrador de subsidio y un medio de pago.
     */
    private Boolean beneficiarioRegistraAdmonSubsidio;

    /*
    * Variable que indica si una persona ha recibido subisidio en periodo de fallecimiento
    */
    private Boolean validacionSubsidioFallecimientoBeneficiario;


    public Boolean isBeneficiarioRegistraAdmonSubsidio() {
        return this.beneficiarioRegistraAdmonSubsidio;
    }

    public Boolean getBeneficiarioRegistraAdmonSubsidio() {
        return this.beneficiarioRegistraAdmonSubsidio;
    }

    public void setBeneficiarioRegistraAdmonSubsidio(Boolean beneficiarioRegistraAdmonSubsidio) {
        this.beneficiarioRegistraAdmonSubsidio = beneficiarioRegistraAdmonSubsidio;
    }

    public Boolean isValidacionSubsidioFallecimientoBeneficiario() {
        return this.validacionSubsidioFallecimientoBeneficiario;
    }

    public Boolean getValidacionSubsidioFallecimientoBeneficiario() {
        return validacionSubsidioFallecimientoBeneficiario;
    }

    public void setValidacionSubsidioFallecimientoBeneficiario(Boolean validacionSubsidioFallecimientoBeneficiario) {
        this.validacionSubsidioFallecimientoBeneficiario = validacionSubsidioFallecimientoBeneficiario;
    }

    private Boolean cumpleSubsidioPeriodoFallecimiento;

    public Boolean isDerechoUltimaLiquidacion() {
        return this.derechoUltimaLiquidacion;
    }

    public Boolean isTrabajadorActivoAlFallecerBeneficiario() {
        return this.trabajadorActivoAlFallecerBeneficiario;
    }

    public Boolean isEstadoDistintoDeActivo() {
        return this.estadoDistintoDeActivo;
    }

    public Boolean isActivoAntesDeMorir() {
        return this.activoAntesDeMorir;
    }

    public Boolean isRangoValidoDiasParametrizacion() {
        return this.rangoValidoDiasParametrizacion;
    }

    public Boolean isMotivoFallecimientoCanalPresencial() {
        return this.motivoFallecimientoCanalPresencial;
    }

    public Boolean isCumpleSubsidioPeriodoFallecimiento() {
        return this.cumpleSubsidioPeriodoFallecimiento;
    }

    public Boolean getCumpleSubsidioPeriodoFallecimiento() {
        return this.cumpleSubsidioPeriodoFallecimiento;
    }

    public void setCumpleSubsidioPeriodoFallecimiento(Boolean cumpleSubsidioPeriodoFallecimiento) {
        this.cumpleSubsidioPeriodoFallecimiento = cumpleSubsidioPeriodoFallecimiento;
    }

    /**
     * @return the tipoDocumentoBeneficiario
     */
    public TipoIdentificacionEnum getTipoDocumentoBeneficiario() {
        return tipoDocumentoBeneficiario;
    }

    /**
     * @param tipoDocumentoBeneficiario
     *        the tipoDocumentoBeneficiario to set
     */
    public void setTipoDocumentoBeneficiario(TipoIdentificacionEnum tipoDocumentoBeneficiario) {
        this.tipoDocumentoBeneficiario = tipoDocumentoBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario
     *        the numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre
     *        the primerNombre to set
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
     * @param segundoNombre
     *        the segundoNombre to set
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
     * @param primerApellido
     *        the primerApellido to set
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
     * @param segundoApellido
     *        the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the tipoBeneficiario
     */
    public ClasificacionEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario
     *        the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(ClasificacionEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar
     *        the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the estadoBeneficiarioAfiliado
     */
    public EstadoAfiliadoEnum getEstadoBeneficiarioAfiliado() {
        return estadoBeneficiarioAfiliado;
    }

    /**
     * @param estadoBeneficiarioAfiliado
     *        the estadoBeneficiarioAfiliado to set
     */
    public void setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum estadoBeneficiarioAfiliado) {
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the nombreCompletoBeneficiario
     */
    public String getNombreCompletoBeneficiario() {
        return nombreCompletoBeneficiario;
    }

    /**
     * @param nombreCompletoBeneficiario
     *        the nombreCompletoBeneficiario to set
     */
    public void setNombreCompletoBeneficiario(String nombreCompletoBeneficiario) {
        this.nombreCompletoBeneficiario = nombreCompletoBeneficiario;
    }

    public BeneficiariosAfiliadoDTO() {
    }

    /**
     * @return the idAfiliadoPrincipal
     */
    public Long getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }

    /**
     * @param idAfiliadoPrincipal
     *        the idAfiliadoPrincipal to set
     */
    public void setIdAfiliadoPrincipal(Long idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle
     *        the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the derechoUltimaLiquidacion
     */
    public Boolean getDerechoUltimaLiquidacion() {
        return derechoUltimaLiquidacion;
    }

    /**
     * @param derechoUltimaLiquidacion
     *        the derechoUltimaLiquidacion to set
     */
    public void setDerechoUltimaLiquidacion(Boolean derechoUltimaLiquidacion) {
        this.derechoUltimaLiquidacion = derechoUltimaLiquidacion;
    }

    /**
     * @return the estadoDistintoDeActivo
     */
    public Boolean getEstadoDistintoDeActivo() {
        return estadoDistintoDeActivo;
    }

    /**
     * @param estadoDistintoDeActivo
     *        the estadoDistintoDeActivo to set
     */
    public void setEstadoDistintoDeActivo(Boolean estadoDistintoDeActivo) {
        this.estadoDistintoDeActivo = estadoDistintoDeActivo;
    }

    /**
     * @return the activoAntesDeMorir
     */
    public Boolean getActivoAntesDeMorir() {
        return activoAntesDeMorir;
    }

    /**
     * @param activoAntesDeMorir
     *        the activoAntesDeMorir to set
     */
    public void setActivoAntesDeMorir(Boolean activoAntesDeMorir) {
        this.activoAntesDeMorir = activoAntesDeMorir;
    }

    /**
     * @return the rangoValidoDiasParametrizacion
     */
    public Boolean getRangoValidoDiasParametrizacion() {
        return rangoValidoDiasParametrizacion;
    }

    /**
     * @param rangoValidoDiasParametrizacion
     *        the rangoValidoDiasParametrizacion to set
     */
    public void setRangoValidoDiasParametrizacion(Boolean rangoValidoDiasParametrizacion) {
        this.rangoValidoDiasParametrizacion = rangoValidoDiasParametrizacion;
    }

    /**
     * @return the motivoFallecimientoCanalPresencial
     */
    public Boolean getMotivoFallecimientoCanalPresencial() {
        return motivoFallecimientoCanalPresencial;
    }

    /**
     * @param motivoFallecimientoCanalPresencial
     *        the motivoFallecimientoCanalPresencial to set
     */
    public void setMotivoFallecimientoCanalPresencial(Boolean motivoFallecimientoCanalPresencial) {
        this.motivoFallecimientoCanalPresencial = motivoFallecimientoCanalPresencial;
    }

    /**
     * @return the fechaFallecido
     */
    public Date getFechaFallecido() {
        return fechaFallecido;
    }

    /**
     * @param fechaFallecido
     *        the fechaFallecido to set
     */
    public void setFechaFallecido(Date fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    /**
     * @return the trabajadorActivoAlFallecerBeneficiario
     */
    public Boolean getTrabajadorActivoAlFallecerBeneficiario() {
        return trabajadorActivoAlFallecerBeneficiario;
    }

    /**
     * @param trabajadorActivoAlFallecerBeneficiario
     *        the trabajadorActivoAlFallecerBeneficiario to set
     */
    public void setTrabajadorActivoAlFallecerBeneficiario(Boolean trabajadorActivoAlFallecerBeneficiario) {
        this.trabajadorActivoAlFallecerBeneficiario = trabajadorActivoAlFallecerBeneficiario;
    }
    
    /** (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return this.idBeneficiario.longValue() == ((BeneficiariosAfiliadoDTO)obj).getIdBeneficiario().longValue();
    }

}
