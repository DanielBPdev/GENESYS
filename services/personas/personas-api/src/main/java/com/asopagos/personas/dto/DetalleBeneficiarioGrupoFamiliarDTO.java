package com.asopagos.personas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.util.CalendarUtils;

public class DetalleBeneficiarioGrupoFamiliarDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5263599299862710958L;
    
  //comunes
    private TipoBeneficiarioEnum parentezco;
    private EstadoAfiliadoEnum estadoRespectoAfiPrincipal;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaNacimiento;
    private String fechaExpDocIdent;
    private GeneroEnum genero;
    private Integer ocupacion;
    private String fechaIngresoBeneficiario;

    //comunes conyuge - padre

    private NivelEducativoEnum nivelEducativo;


    //comunes padre - hijo
    private ClasificacionEnum clasificacion;
    private Short gradoCursado;
    private String condicionInvalidez;
    private String fechaReporteCondicionInvalidez;


    //conyuge
    private Boolean conyugeLabora;
    private Double valorIngresoMensual;

    //hijo
    private String certificadoEscolar;
    private String fechaVencimientoCertificado;
    
    private Long idBeneficiario;
    private Long idBeneficiarioDetalle;
    
    /**
     * CC Vistas 360
     */
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
    
    /**
     * CC Vistas 360
     */
    private String fechaRetiroBeneficiario;
    
    /**
     * CC Vistas 360
     */
    private String edad;
    
    /**
     * CC Vista 360
     */
    private String fechaRegistroCertificadoEscolar;
    
    /**
     * CC Vista 360
     */
    private String fechaInicioVigenciaCertificado;
    
    /**
     * CC Vista 360-2
     */
    private String fechaRecepcionDocumento;

    private String conyugeCuidador;

    private String fechaInicioConyugeCuidador;

    private String fechaFinConyugeCuidador;

    private String periodoInicioExclusion;

    private String periodoFinExclusion;
    
    /**
     * 
     */
    public DetalleBeneficiarioGrupoFamiliarDTO() {
        super();
    }

    /**
     * Constructor para resolver consultas
     * @param estadoRespectoAfiPrincipal
     *        Estado del beneficiario contra el afiliado principal
     * @param primerNombre
     *        Primer nombre
     * @param segundoNombre
     *        Segundo nombre
     * @param primerApellido
     *        Primer apellido
     * @param segundoApellido
     *        Segundo apellido
     * @param fechaNacimiento
     *        Fecha de nacimiento
     * @param fechaExpDocIdent
     *        Fecha de expedición documento
     * @param genero
     *        Descripción del genero
     * @param ocupacion
     *        Identificador de Ocupación
     * @param fechaIngresoBeneficiario
     *        Fecha de afiliación
     * @param nivelEducativo
     *        Descripción Nivel educativo
     * @param clasificacion
     *        Descripción de la clasificación del benficiario
     * @param gradoCursado
     *        Identificador grado cursado
     * @param condicionInvalidez
     *        Indica si tiene condición de invalidez
     * @param fechaReporteCondicionInvalidez
     *        Fecha en la que reporto condición de invalidez
     * @param conyugeLabora
     *        Indica si el labora aplica solo para conyuge
     * @param valorIngresoMensual
     *        Valor ingresos aplica solo para conyuge
     * @param certificadoEscolar
     *        Indica si tiene certificado escolar
     * @param fechaVencimientoCertificado
     *        Fecha de vencimiento del certificado
     * @param idBeneficiario
     *        Identificador del beneficiario
     * @param idBeneficiarioDetalle
     *        Identificador del beneficiario detalle
     */
    public DetalleBeneficiarioGrupoFamiliarDTO(String estadoRespectoAfiPrincipal, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, Date fechaNacimiento, Date fechaExpDocIdent, String genero,
            Integer ocupacion, Date fechaIngresoBeneficiario, String nivelEducativo, String clasificacion, Short gradoCursado,
            String condicionInvalidez, Date fechaReporteCondicionInvalidez, Boolean conyugeLabora, BigDecimal valorIngresoMensual,
            Boolean certificadoEscolar, Date fechaVencimientoCertificado, Long idBeneficiario, Long idBeneficiarioDetalle,
            String motivoDesafiliacion, Date fechaRetiroBeneficiario, Date fechaInicioVigenciaCertificado, Date fechaRegistroCertificadoEscolar,
            Date fechaRecepcionDocumento,String conyugeCuidador, Date fechaInicioConyugeCuidador, Date fechaFinConyugeCuidador, Date periodoInicioExclusion,
            Date periodoFinExclusion) {
        super();
        this.estadoRespectoAfiPrincipal = estadoRespectoAfiPrincipal!= null ? EstadoAfiliadoEnum.valueOf(estadoRespectoAfiPrincipal) : null;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento != null ? CalendarUtils.darFormatoYYYYMMDD(fechaNacimiento) : null;
        this.fechaExpDocIdent = fechaExpDocIdent != null ? CalendarUtils.darFormatoYYYYMMDD(fechaExpDocIdent) : null;
        this.genero = genero != null ? GeneroEnum.valueOf(genero) : null;
        this.ocupacion = ocupacion;
        this.fechaIngresoBeneficiario = fechaIngresoBeneficiario != null ? CalendarUtils.darFormatoYYYYMMDD(fechaIngresoBeneficiario) : null;
        this.nivelEducativo = nivelEducativo != null ? NivelEducativoEnum.valueOf(nivelEducativo) : null;
        if (clasificacion != null) {
            this.clasificacion = ClasificacionEnum.valueOf(clasificacion);
            this.parentezco = TipoBeneficiarioEnum.valueOf(this.clasificacion.getSujetoTramite().getName());
        }
        this.gradoCursado = gradoCursado;
        this.condicionInvalidez = condicionInvalidez;
        this.fechaReporteCondicionInvalidez = fechaReporteCondicionInvalidez != null ? CalendarUtils.darFormatoYYYYMMDD(fechaReporteCondicionInvalidez) : null;
        this.conyugeLabora = conyugeLabora;
        this.valorIngresoMensual = valorIngresoMensual != null ? valorIngresoMensual.doubleValue() : null;
        this.certificadoEscolar = certificadoEscolar != null ? certificadoEscolar.toString() : null;
        this.fechaVencimientoCertificado = fechaVencimientoCertificado != null ? CalendarUtils.darFormatoYYYYMMDD(fechaVencimientoCertificado) : null;
        this.idBeneficiario = idBeneficiario;
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
        //CC Vistas 360
        if (motivoDesafiliacion != null) {
            this.motivoDesafiliacion = MotivoDesafiliacionBeneficiarioEnum.valueOf(motivoDesafiliacion);
        }
        this.fechaRetiroBeneficiario = fechaRetiroBeneficiario != null ? CalendarUtils.darFormatoYYYYMMDD(fechaRetiroBeneficiario) : null;
        this.edad = fechaNacimiento != null ? CalendarUtils.calcularEdadAniosMesesDias(fechaNacimiento) : null;
        this.fechaInicioVigenciaCertificado = fechaInicioVigenciaCertificado != null ? CalendarUtils.darFormatoYYYYMMDD(fechaInicioVigenciaCertificado) : null;
        this.fechaRegistroCertificadoEscolar = fechaRegistroCertificadoEscolar != null ? CalendarUtils.darFormatoYYYYMMDD(fechaRegistroCertificadoEscolar) : null;
        this.fechaRecepcionDocumento = fechaRecepcionDocumento != null ? CalendarUtils.darFormatoYYYYMMDD(fechaRecepcionDocumento) : null;
        this.conyugeCuidador = conyugeCuidador;
        this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador != null ? CalendarUtils.darFormatoYYYYMMDD(fechaInicioConyugeCuidador) : null;
        this.fechaFinConyugeCuidador = fechaFinConyugeCuidador != null ? CalendarUtils.darFormatoYYYYMMDD(fechaFinConyugeCuidador) : null;
        this.periodoInicioExclusion = periodoInicioExclusion != null ? CalendarUtils.darFormatoYYYYMMDD(periodoInicioExclusion) : null;
        this.periodoFinExclusion = periodoFinExclusion != null ? CalendarUtils.darFormatoYYYYMMDD(periodoFinExclusion) : null;

    }


    /**
     * @return the parentezco
     */
    public TipoBeneficiarioEnum getParentezco() {
        return parentezco;
    }
    
    /**
     * @param parentezco the parentezco to set
     */
    public void setParentezco(TipoBeneficiarioEnum parentezco) {
        this.parentezco = parentezco;
    }
    
    /**
     * @return the estadoRespectoAfiPrincipal
     */
    public EstadoAfiliadoEnum getEstadoRespectoAfiPrincipal() {
        return estadoRespectoAfiPrincipal;
    }
    
    /**
     * @param estadoRespectoAfiPrincipal the estadoRespectoAfiPrincipal to set
     */
    public void setEstadoRespectoAfiPrincipal(EstadoAfiliadoEnum estadoRespectoAfiPrincipal) {
        this.estadoRespectoAfiPrincipal = estadoRespectoAfiPrincipal;
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
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * @return the fechaExpDocIdent
     */
    public String getFechaExpDocIdent() {
        return fechaExpDocIdent;
    }
    
    /**
     * @param fechaExpDocIdent the fechaExpDocIdent to set
     */
    public void setFechaExpDocIdent(String fechaExpDocIdent) {
        this.fechaExpDocIdent = fechaExpDocIdent;
    }
    
    /**
     * @return the genero
     */
    public GeneroEnum getGenero() {
        return genero;
    }
    
    /**
     * @param genero the genero to set
     */
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }
    
    /**
     * @return the ocupacion
     */
    public Integer getOcupacion() {
        return ocupacion;
    }
    
    /**
     * @param ocupacion the ocupacion to set
     */
    public void setOcupacion(Integer ocupacion) {
        this.ocupacion = ocupacion;
    }
    
    /**
     * @return the fechaIngresoBeneficiario
     */
    public String getFechaIngresoBeneficiario() {
        return fechaIngresoBeneficiario;
    }
    
    /**
     * @param fechaIngresoBeneficiario the fechaIngresoBeneficiario to set
     */
    public void setFechaIngresoBeneficiario(String fechaIngresoBeneficiario) {
        this.fechaIngresoBeneficiario = fechaIngresoBeneficiario;
    }
    
    /**
     * @return the nivelEducativo
     */
    public NivelEducativoEnum getNivelEducativo() {
        return nivelEducativo;
    }
    
    /**
     * @param nivelEducativo the nivelEducativo to set
     */
    public void setNivelEducativo(NivelEducativoEnum nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }
    
    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }
    
    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    /**
     * @return the gradoCursado
     */
    public Short getGradoCursado() {
        return gradoCursado;
    }
    
    /**
     * @param gradoCursado the gradoCursado to set
     */
    public void setGradoCursado(Short gradoCursado) {
        this.gradoCursado = gradoCursado;
    }
    
    /**
     * @return the condicionInvalidez
     */
    public String getCondicionInvalidez() {
        return condicionInvalidez;
    }
    
    /**
     * @param condicionInvalidez the condicionInvalidez to set
     */
    public void setCondicionInvalidez(String condicionInvalidez) {
        this.condicionInvalidez = condicionInvalidez;
    }
    
    /**
     * @return the fechaReporteCondicionInvalidez
     */
    public String getFechaReporteCondicionInvalidez() {
        return fechaReporteCondicionInvalidez;
    }
    
    /**
     * @param fechaReporteCondicionInvalidez the fechaReporteCondicionInvalidez to set
     */
    public void setFechaReporteCondicionInvalidez(String fechaReporteCondicionInvalidez) {
        this.fechaReporteCondicionInvalidez = fechaReporteCondicionInvalidez;
    }
    
    /**
     * @return the conyugeLabora
     */
    public Boolean getConyugeLabora() {
        return conyugeLabora;
    }
    
    /**
     * @param conyugeLabora the conyugeLabora to set
     */
    public void setConyugeLabora(Boolean conyugeLabora) {
        this.conyugeLabora = conyugeLabora;
    }
    
    /**
     * @return the valorIngresoMensual
     */
    public Double getValorIngresoMensual() {
        return valorIngresoMensual;
    }
    
    /**
     * @param valorIngresoMensual the valorIngresoMensual to set
     */
    public void setValorIngresoMensual(Double valorIngresoMensual) {
        this.valorIngresoMensual = valorIngresoMensual;
    }
    
    /**
     * @return the certificadoEscolar
     */
    public String getCertificadoEscolar() {
        return certificadoEscolar;
    }
    
    /**
     * @param certificadoEscolar the certificadoEscolar to set
     */
    public void setCertificadoEscolar(String certificadoEscolar) {
        this.certificadoEscolar = certificadoEscolar;
    }
    
    /**
     * @return the fechaVencimientoCertificado
     */
    public String getFechaVencimientoCertificado() {
        return fechaVencimientoCertificado;
    }
    
    /**
     * @param fechaVencimientoCertificado the fechaVencimientoCertificado to set
     */
    public void setFechaVencimientoCertificado(String fechaVencimientoCertificado) {
        this.fechaVencimientoCertificado = fechaVencimientoCertificado;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the fechaRetiroBeneficiario
     */
    public String getFechaRetiroBeneficiario() {
        return fechaRetiroBeneficiario;
    }

    /**
     * @param fechaRetiroBeneficiario the fechaRetiroBeneficiario to set
     */
    public void setFechaRetiroBeneficiario(String fechaRetiroBeneficiario) {
        this.fechaRetiroBeneficiario = fechaRetiroBeneficiario;
    }

    /**
     * @return the edadBeneficiario
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edadBeneficiario the edadBeneficiario to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the fechaUltimoCertificadoEscolar
     */
    public String getFechaRegistroCertificadoEscolar() {
        return fechaRegistroCertificadoEscolar;
    }

    /**
     * @param fechaUltimoCertificadoEscolar the fechaUltimoCertificadoEscolar to set
     */
    public void setFechaRegistroCertificadoEscolar(String fechaRegistroCertificadoEscolar) {
        this.fechaRegistroCertificadoEscolar = fechaRegistroCertificadoEscolar;
    }

    /**
     * @return the fechaInicioVigenciaCertificado
     */
    public String getFechaInicioVigenciaCertificado() {
        return fechaInicioVigenciaCertificado;
    }

    /**
     * @param fechaInicioVigenciaCertificado the fechaInicioVigenciaCertificado to set
     */
    public void setFechaInicioVigenciaCertificado(String fechaInicioVigenciaCertificado) {
        this.fechaInicioVigenciaCertificado = fechaInicioVigenciaCertificado;
    }

    /**
     * @return the fechaRecepcionDocumento
     */
    public String getFechaRecepcionDocumento() {
        return fechaRecepcionDocumento;
    }

    /**
     * @param fechaRecepcionDocumento the fechaRecepcionDocumento to set
     */
    public void setFechaRecepcionDocumento(String fechaRecepcionDocumento) {
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }
    
    public String getConyugeCuidador() {
        return this.conyugeCuidador;
    }

    public void setConyugeCuidador(String conyugeCuidador) {
        this.conyugeCuidador = conyugeCuidador;
    }

    public String getFechaInicioConyugeCuidador() {
        return this.fechaInicioConyugeCuidador;
    }

    public void setFechaInicioConyugeCuidador(String fechaInicioConyugeCuidador) {
        this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador;
    }

    public String getFechaFinConyugeCuidador() {
        return this.fechaFinConyugeCuidador;
    }

    public void setFechaFinConyugeCuidador(String fechaFinConyugeCuidador) {
        this.fechaFinConyugeCuidador = fechaFinConyugeCuidador;
    }

    public String getPeriodoInicioExclusion() {
        return this.periodoInicioExclusion;
    }

    public void setPeriodoInicioExclusion(String periodoInicioExclusion) {
        this.periodoInicioExclusion = periodoInicioExclusion;
    }
    
    public String getPeriodoFinExclusion() {
        return this.periodoFinExclusion;
    }

    public void setPeriodoFinExclusion(String periodoFinExclusion) {
        this.periodoFinExclusion = periodoFinExclusion;
    }

}
