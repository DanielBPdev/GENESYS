package com.asopagos.pila.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro2;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.util.FuncionesValidador;

/**
 * <b>Descripcion:</b> DTO que representa a la entidad PilaArchivoIRegistro2<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PilaArchivoIRegistro2ModeloDTO implements Serializable {
    private static final long serialVersionUID = -5116632193247184132L;

    /**
     * Lista de los nombres de campos de la tabla
     */
    private List<String> nombresCampos;

    /**
     * Código identificador de llave primaria del registro tipo 2
     */
    private Long id;

    /**
     * Referencia a la entrada del índice de planilla de Operador de Información para el registro
     */
    private IndicePlanilla indicePlanilla;

    /**
     * Contenido del Registro tipo 2 campo 1: Secuencia.
     */
    private Integer secuencia;

    /**
     * Contenido del Registro tipo 2 campo 3: Tipo identificación del cotizante.
     */
    private String tipoIdCotizante;

    /**
     * Contenido del Registro tipo 2 campo 4: No. de identificación del cotizante.
     */
    private String idCotizante;

    /**
     * Contenido del Registro tipo 2 campo 5: Tipo de cotizante
     */
    private Short tipoCotizante;

    /**
     * Contenido del Registro tipo 2 campo 6: Subtipo de cotizante
     */
    private Short subTipoCotizante;

    /**
     * Contenido del Registro tipo 2 campo 7: Extranjero no obligado a cotizar a pensiones.
     */
    private String extrangeroNoObligado;

    /**
     * Contenido del Registro tipo 2 campo 8: Colombiano en el exterior.
     */
    private String colombianoExterior;

    /**
     * Contenido del Registro tipo 2 campo 9: Código del Departamento de la ubicación laboral.
     */
    private String codDepartamento;

    /**
     * Contenido del Registro tipo 2 campo 10: Código del Municipio de la ubicación laboral.
     */
    private String codMunicipio;

    /**
     * Contenido del Registro tipo 2 campo 11: Primer apellido.
     */
    private String primerApellido;

    /**
     * Contenido del Registro tipo 2 campo 12: Segundo apellido.
     */
    private String segundoApellido;

    /**
     * Contenido del Registro tipo 2 campo 13: Primer nombre.
     */
    private String primerNombre;

    /**
     * Contenido del Registro tipo 2 campo 14: Segundo nombre.
     */
    private String segundoNombre;

    /**
     * Contenido del Registro tipo 2 campo 15: Marcación de novedad ING: Ingreso.
     */
    private String novIngreso;

    /**
     * Contenido del Registro tipo 2 campo 16: Marcación de novedad RET: Retiro.
     */
    private String novRetiro;

    /**
     * Contenido del Registro tipo 2 campo 17: Marcación de novedad VSP: Variación permanente de salario.
     */
    private String novVSP;

    /**
     * Contenido del Registro tipo 2 campo 18: Marcación de novedad VST: Variación transitoria del salario.
     */
    private String novVST;

    /**
     * Contenido del Registro tipo 2 campo 19: Marcación de novedad SLN: Suspensión temporal
     * del contrato de trabajo, licencia no remunerada o comisión de servicios.
     */
    private String novSLN;

    /**
     * Contenido del Registro tipo 2 campo 20: Marcación de novedad IGE: Incapacidad Temporal
     * por Enfermedad General.
     */
    private String novIGE;

    /**
     * Contenido del Registro tipo 2 campo 21: Marcación de novedad LMA: Licencia de Maternidad o paternidad.
     */
    private String novLMA;

    /**
     * Contenido del Registro tipo 2 campo 22: Marcación de novedad VAC - LR: Vacaciones, Licencia remunerada
     */
    private String novVACLR;

    /**
     * Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
     * trabajo o enfermedad laboral
     */
    private String diasIRL;

    /**
     * Contenido del Registro tipo 2 campo 24: Días cotizados.
     */
    private Short diasCotizados;

    /**
     * Contenido del Registro tipo 2 campo 25: Salario básico.
     */
    private BigDecimal salarioBasico;

    /**
     * Contenido del Registro tipo 2 campo 26: Ingreso Base Cotización (IBC)
     */
    private BigDecimal valorIBC;

    /**
     * Contenido del Registro tipo 2 campo 27: Tarifa.
     */
    private BigDecimal tarifa;

    /**
     * Contenido del Registro tipo 2 campo 28: Aporte obligatorio.
     */
    private BigDecimal aporteObligatorio;

    /**
     * Contenido del Registro tipo 2 campo 29: Correcciones
     */
    private String correcciones;

    /**
     * Contenido del Registro tipo 2 campo 30: Salario Integral
     */
    private String salarioIntegral;

    /**
     * Contenido del Registro tipo 2 campo 31: Fecha de novedad ingreso
     */
    private Date fechaIngreso;

    /**
     * Contenido del Registro tipo 2 campo 32: Fecha de novedad retiro
     */
    private Date fechaRetiro;

    /**
     * Contenido del Registro tipo 2 campo 33: Fecha inicio novedad VSP
     */
    private Date fechaInicioVSP;

    /**
     * Contenido del Registro tipo 2 campo 34: Fecha inicio novedad SLN
     */
    private Date fechaInicioSLN;

    /**
     * Contenido del Registro tipo 2 campo 35: Fecha fin novedad SLN
     */
    private Date fechaFinSLN;

    /**
     * Contenido del Registro tipo 2 campo 36: Fecha inicio novedad IGE
     */
    private Date fechaInicioIGE;

    /**
     * Contenido del Registro tipo 2 campo 37: Fecha fin novedad IGE
     */
    private Date fechaFinIGE;

    /**
     * Contenido del Registro tipo 2 campo 38: Fecha inicio novedad LMA
     */
    private Date fechaInicioLMA;

    /**
     * Contenido del Registro tipo 2 campo 39: Fecha fin novedad LMA
     */
    private Date fechaFinLMA;

    /**
     * Contenido del Registro tipo 2 campo 40: Fecha inicio novedad VAC - LR
     */
    private Date fechaInicioVACLR;

    /**
     * Contenido del Registro tipo 2 campo 41: Fecha fin novedad VAC - LR
     */
    private Date fechaFinVACLR;

    /**
     * Contenido del Registro tipo 2 campo 42: Fecha inicio novedad VCT
     */
    private Date fechaInicioVCT;

    /**
     * Contenido del Registro tipo 2 campo 43: Fecha fin novedad VCT
     */
    private Date fechaFinVCT;

    /**
     * Contenido del Registro tipo 2 campo 44: Fecha inicio novedad IRL
     */
    private Date fechaInicioIRL;

    /**
     * Contenido del Registro tipo 2 campo 45: Fecha fin novedad IRL
     */
    private Date fechaFinIRL;

    /**
     * Contenido del Registro tipo 2 campo 46: Número de horas laboradas
     */
    private Short horasLaboradas;

    /**
     * Constructor que genera la lista de nombres de campos
     */
    public PilaArchivoIRegistro2ModeloDTO() {
        super();
        this.nombresCampos = new ArrayList<>();
        this.nombresCampos.add("pi2Id");
        this.nombresCampos.add("pi2IndicePlanilla");
        this.nombresCampos.add("pi2Secuencia");
        this.nombresCampos.add("pi2TipoIdCotizante");
        this.nombresCampos.add("pi2IdCotizante");
        this.nombresCampos.add("pi2TipoCotizante");
        this.nombresCampos.add("pi2SubTipoCotizante");
        this.nombresCampos.add("pi2ExtrangeroNoObligado");
        this.nombresCampos.add("pi2ColombianoExterior");
        this.nombresCampos.add("pi2CodDepartamento");
        this.nombresCampos.add("pi2CodMunicipio");
        this.nombresCampos.add("pi2PrimerApellido");
        this.nombresCampos.add("pi2SegundoApellido");
        this.nombresCampos.add("pi2PrimerNombre");
        this.nombresCampos.add("pi2SegundoNombre");
        this.nombresCampos.add("pi2NovIngreso");
        this.nombresCampos.add("pi2NovRetiro");
        this.nombresCampos.add("pi2NovVSP");
        this.nombresCampos.add("pi2NovVST");
        this.nombresCampos.add("pi2NovSLN");
        this.nombresCampos.add("pi2NovIGE");
        this.nombresCampos.add("pi2NovLMA");
        this.nombresCampos.add("pi2NovVACLR");
        this.nombresCampos.add("pi2DiasIRL");
        this.nombresCampos.add("pi2DiasCotizados");
        this.nombresCampos.add("pi2SalarioBasico");
        this.nombresCampos.add("pi2ValorIBC");
        this.nombresCampos.add("pi2Tarifa");
        this.nombresCampos.add("pi2AporteObligatorio");
        this.nombresCampos.add("pi2Correcciones");
        this.nombresCampos.add("pi2SalarioIntegral");
        this.nombresCampos.add("pi2FechaIngreso");
        this.nombresCampos.add("pi2FechaRetiro");
        this.nombresCampos.add("pi2FechaInicioVSP");
        this.nombresCampos.add("pi2FechaInicioSLN");
        this.nombresCampos.add("pi2FechaFinSLN");
        this.nombresCampos.add("pi2FechaInicioIGE");
        this.nombresCampos.add("pi2FechaFinIGE");
        this.nombresCampos.add("pi2FechaInicioLMA");
        this.nombresCampos.add("pi2FechaFinLMA");
        this.nombresCampos.add("pi2FechaInicioVACLR");
        this.nombresCampos.add("pi2FechaFinVACLR");
        this.nombresCampos.add("pi2FechaInicioVCT");
        this.nombresCampos.add("pi2FechaFinVCT");
        this.nombresCampos.add("pi2FechaInicioIRL");
        this.nombresCampos.add("pi2FechaFinIRL");
        this.nombresCampos.add("pi2HorasLaboradas");
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the indicePlanilla
     */
    public IndicePlanilla getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanilla indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the secuencia
     */
    public Integer getSecuencia() {
        return secuencia;
    }

    /**
     * @param secuencia
     *        the secuencia to set
     */
    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    /**
     * @return the tipoIdCotizante
     */
    public String getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante
     *        the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(String tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the idCotizante
     */
    public String getIdCotizante() {
        return idCotizante;
    }

    /**
     * @param idCotizante
     *        the idCotizante to set
     */
    public void setIdCotizante(String idCotizante) {
        this.idCotizante = idCotizante;
    }

    /**
     * @return the tipoCotizante
     */
    public Short getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante
     *        the tipoCotizante to set
     */
    public void setTipoCotizante(Short tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the subTipoCotizante
     */
    public Short getSubTipoCotizante() {
        return subTipoCotizante;
    }

    /**
     * @param subTipoCotizante
     *        the subTipoCotizante to set
     */
    public void setSubTipoCotizante(Short subTipoCotizante) {
        this.subTipoCotizante = subTipoCotizante;
    }

    /**
     * @return the extrangeroNoObligado
     */
    public String getExtrangeroNoObligado() {
        return extrangeroNoObligado;
    }

    /**
     * @param extrangeroNoObligado
     *        the extrangeroNoObligado to set
     */
    public void setExtrangeroNoObligado(String extrangeroNoObligado) {
        this.extrangeroNoObligado = extrangeroNoObligado;
    }

    /**
     * @return the colombianoExterior
     */
    public String getColombianoExterior() {
        return colombianoExterior;
    }

    /**
     * @param colombianoExterior
     *        the colombianoExterior to set
     */
    public void setColombianoExterior(String colombianoExterior) {
        this.colombianoExterior = colombianoExterior;
    }

    /**
     * @return the codDepartamento
     */
    public String getCodDepartamento() {
        return codDepartamento;
    }

    /**
     * @param codDepartamento
     *        the codDepartamento to set
     */
    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    /**
     * @return the codMunicipio
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * @param codMunicipio
     *        the codMunicipio to set
     */
    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
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
     * @return the novIngreso
     */
    public String getNovIngreso() {
        return novIngreso;
    }

    /**
     * @param novIngreso
     *        the novIngreso to set
     */
    public void setNovIngreso(String novIngreso) {
        this.novIngreso = novIngreso;
    }

    /**
     * @return the novRetiro
     */
    public String getNovRetiro() {
        return novRetiro;
    }

    /**
     * @param novRetiro
     *        the novRetiro to set
     */
    public void setNovRetiro(String novRetiro) {
        this.novRetiro = novRetiro;
    }

    /**
     * @return the novVSP
     */
    public String getNovVSP() {
        return novVSP;
    }

    /**
     * @param novVSP
     *        the novVSP to set
     */
    public void setNovVSP(String novVSP) {
        this.novVSP = novVSP;
    }

    /**
     * @return the novVST
     */
    public String getNovVST() {
        return novVST;
    }

    /**
     * @param novVST
     *        the novVST to set
     */
    public void setNovVST(String novVST) {
        this.novVST = novVST;
    }

    /**
     * @return the novSLN
     */
    public String getNovSLN() {
        return novSLN;
    }

    /**
     * @param novSLN
     *        the novSLN to set
     */
    public void setNovSLN(String novSLN) {
        this.novSLN = novSLN;
    }

    /**
     * @return the novIGE
     */
    public String getNovIGE() {
        return novIGE;
    }

    /**
     * @param novIGE
     *        the novIGE to set
     */
    public void setNovIGE(String novIGE) {
        this.novIGE = novIGE;
    }

    /**
     * @return the novLMA
     */
    public String getNovLMA() {
        return novLMA;
    }

    /**
     * @param novLMA
     *        the novLMA to set
     */
    public void setNovLMA(String novLMA) {
        this.novLMA = novLMA;
    }

    /**
     * @return the novVACLR
     */
    public String getNovVACLR() {
        return novVACLR;
    }

    /**
     * @param novVACLR
     *        the novVACLR to set
     */
    public void setNovVACLR(String novVACLR) {
        this.novVACLR = novVACLR;
    }

    /**
     * @return the diasIRL
     */
    public String getDiasIRL() {
        return diasIRL;
    }

    /**
     * @param diasIRL
     *        the diasIRL to set
     */
    public void setDiasIRL(String diasIRL) {
        this.diasIRL = diasIRL;
    }

    /**
     * @return the diasCotizados
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * @param diasCotizados
     *        the diasCotizados to set
     */
    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    /**
     * @return the salarioBasico
     */
    public BigDecimal getSalarioBasico() {
        return salarioBasico;
    }

    /**
     * @param salarioBasico
     *        the salarioBasico to set
     */
    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    /**
     * @return the valorIBC
     */
    public BigDecimal getValorIBC() {
        return valorIBC;
    }

    /**
     * @param valorIBC
     *        the valorIBC to set
     */
    public void setValorIBC(BigDecimal valorIBC) {
        this.valorIBC = valorIBC;
    }

    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa
     *        the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }

    /**
     * @param aporteObligatorio
     *        the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    /**
     * @return the correcciones
     */
    public String getCorrecciones() {
        return correcciones;
    }

    /**
     * @param correcciones
     *        the correcciones to set
     */
    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    /**
     * @return the salarioIntegral
     */
    public String getSalarioIntegral() {
        return salarioIntegral;
    }

    /**
     * @param salarioIntegral
     *        the salarioIntegral to set
     */
    public void setSalarioIntegral(String salarioIntegral) {
        this.salarioIntegral = salarioIntegral;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the fechaInicioVSP
     */
    public Date getFechaInicioVSP() {
        return fechaInicioVSP;
    }

    /**
     * @param fechaInicioVSP
     *        the fechaInicioVSP to set
     */
    public void setFechaInicioVSP(Date fechaInicioVSP) {
        this.fechaInicioVSP = fechaInicioVSP;
    }

    /**
     * @return the fechaInicioSLN
     */
    public Date getFechaInicioSLN() {
        return fechaInicioSLN;
    }

    /**
     * @param fechaInicioSLN
     *        the fechaInicioSLN to set
     */
    public void setFechaInicioSLN(Date fechaInicioSLN) {
        this.fechaInicioSLN = fechaInicioSLN;
    }

    /**
     * @return the fechaFinSLN
     */
    public Date getFechaFinSLN() {
        return fechaFinSLN;
    }

    /**
     * @param fechaFinSLN
     *        the fechaFinSLN to set
     */
    public void setFechaFinSLN(Date fechaFinSLN) {
        this.fechaFinSLN = fechaFinSLN;
    }

    /**
     * @return the fechaInicioIGE
     */
    public Date getFechaInicioIGE() {
        return fechaInicioIGE;
    }

    /**
     * @param fechaInicioIGE
     *        the fechaInicioIGE to set
     */
    public void setFechaInicioIGE(Date fechaInicioIGE) {
        this.fechaInicioIGE = fechaInicioIGE;
    }

    /**
     * @return the fechaFinIGE
     */
    public Date getFechaFinIGE() {
        return fechaFinIGE;
    }

    /**
     * @param fechaFinIGE
     *        the fechaFinIGE to set
     */
    public void setFechaFinIGE(Date fechaFinIGE) {
        this.fechaFinIGE = fechaFinIGE;
    }

    /**
     * @return the fechaInicioLMA
     */
    public Date getFechaInicioLMA() {
        return fechaInicioLMA;
    }

    /**
     * @param fechaInicioLMA
     *        the fechaInicioLMA to set
     */
    public void setFechaInicioLMA(Date fechaInicioLMA) {
        this.fechaInicioLMA = fechaInicioLMA;
    }

    /**
     * @return the fechaFinLMA
     */
    public Date getFechaFinLMA() {
        return fechaFinLMA;
    }

    /**
     * @param fechaFinLMA
     *        the fechaFinLMA to set
     */
    public void setFechaFinLMA(Date fechaFinLMA) {
        this.fechaFinLMA = fechaFinLMA;
    }

    /**
     * @return the fechaInicioVACLR
     */
    public Date getFechaInicioVACLR() {
        return fechaInicioVACLR;
    }

    /**
     * @param fechaInicioVACLR
     *        the fechaInicioVACLR to set
     */
    public void setFechaInicioVACLR(Date fechaInicioVACLR) {
        this.fechaInicioVACLR = fechaInicioVACLR;
    }

    /**
     * @return the fechaFinVACLR
     */
    public Date getFechaFinVACLR() {
        return fechaFinVACLR;
    }

    /**
     * @param fechaFinVACLR
     *        the fechaFinVACLR to set
     */
    public void setFechaFinVACLR(Date fechaFinVACLR) {
        this.fechaFinVACLR = fechaFinVACLR;
    }

    /**
     * @return the fechaInicioVCT
     */
    public Date getFechaInicioVCT() {
        return fechaInicioVCT;
    }

    /**
     * @param fechaInicioVCT
     *        the fechaInicioVCT to set
     */
    public void setFechaInicioVCT(Date fechaInicioVCT) {
        this.fechaInicioVCT = fechaInicioVCT;
    }

    /**
     * @return the fechaFinVCT
     */
    public Date getFechaFinVCT() {
        return fechaFinVCT;
    }

    /**
     * @param fechaFinVCT
     *        the fechaFinVCT to set
     */
    public void setFechaFinVCT(Date fechaFinVCT) {
        this.fechaFinVCT = fechaFinVCT;
    }

    /**
     * @return the fechaInicioIRL
     */
    public Date getFechaInicioIRL() {
        return fechaInicioIRL;
    }

    /**
     * @param fechaInicioIRL
     *        the fechaInicioIRL to set
     */
    public void setFechaInicioIRL(Date fechaInicioIRL) {
        this.fechaInicioIRL = fechaInicioIRL;
    }

    /**
     * @return the fechaFinIRL
     */
    public Date getFechaFinIRL() {
        return fechaFinIRL;
    }

    /**
     * @param fechaFinIRL
     *        the fechaFinIRL to set
     */
    public void setFechaFinIRL(Date fechaFinIRL) {
        this.fechaFinIRL = fechaFinIRL;
    }

    /**
     * @return the horasLaboradas
     */
    public Short getHorasLaboradas() {
        return horasLaboradas;
    }

    /**
     * @param horasLaboradas
     *        the horasLaboradas to set
     */
    public void setHorasLaboradas(Short horasLaboradas) {
        this.horasLaboradas = horasLaboradas;
    }

    /**
     * @return the nombresCampos
     */
    public List<String> getNombresCampos() {
        return nombresCampos;
    }

    /**
     * Método encargado de añadir un registro a manera de value para insert
     */
    public String crearLineaValues(PilaArchivoIRegistro2 registro) {
        StringBuilder sentenciaTemp = new StringBuilder();

        sentenciaTemp.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);
        //sentenciaTemp.append(QueriesConstants.NEXT_VALUE_FOR_CLAUSE + ConstantesComunesProcesamientoPILA.SEC_PILA_ARCHIVO_I_R2);
        sentenciaTemp.append(registro.getId() != null ? registro.getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIndicePlanilla() != null ? registro.getIndicePlanilla().getId() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSecuencia() != null ? registro.getSecuencia() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getTipoIdCotizante() != null ? "'" + registro.getTipoIdCotizante() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getIdCotizante() != null ? "'" + registro.getIdCotizante() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getTipoCotizante() != null ? registro.getTipoCotizante() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSubTipoCotizante() != null ? registro.getSubTipoCotizante() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getExtrangeroNoObligado() != null ? "'" + registro.getExtrangeroNoObligado() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getColombianoExterior() != null ? "'" + registro.getColombianoExterior() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodDepartamento() != null ? "'" + registro.getCodDepartamento() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCodMunicipio() != null ? "'" + registro.getCodMunicipio() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getPrimerApellido() != null ? "'" + registro.getPrimerApellido() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSegundoApellido() != null ? "'" + registro.getSegundoApellido() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getPrimerNombre() != null ? "'" + registro.getPrimerNombre() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSegundoNombre() != null ? "'" + registro.getSegundoNombre() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovIngreso() != null ? "'" + registro.getNovIngreso() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovRetiro() != null ? "'" + registro.getNovRetiro() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovVSP() != null ? "'" + registro.getNovVSP() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovVST() != null ? "'" + registro.getNovVST() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovSLN() != null ? "'" + registro.getNovSLN() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovIGE() != null ? "'" + registro.getNovIGE() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovLMA() != null ? "'" + registro.getNovLMA() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getNovVACLR() != null ? "'" + registro.getNovVACLR() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getDiasIRL() != null ? "'" + registro.getDiasIRL() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getDiasCotizados() != null ? registro.getDiasCotizados() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSalarioBasico() != null ? registro.getSalarioBasico().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getValorIBC() != null ? registro.getValorIBC().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getTarifa() != null ? registro.getTarifa().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getAporteObligatorio() != null ? registro.getAporteObligatorio().toPlainString() : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getCorrecciones() != null ? "'" + registro.getCorrecciones() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getSalarioIntegral() != null ? "'" + registro.getSalarioIntegral() + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaIngreso() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaIngreso()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaRetiro() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaRetiro()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioVSP() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioVSP()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioSLN() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioSLN()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaFinSLN() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinSLN()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioIGE() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioIGE()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaFinIGE() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinIGE()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioLMA() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioLMA()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaFinLMA() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinLMA()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getFechaInicioVACLR() != null
                ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioVACLR()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaFinVACLR() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinVACLR()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioVCT() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioVCT()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaFinVCT() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinVCT()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(
                registro.getFechaInicioIRL() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaInicioIRL()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp
                .append(registro.getFechaFinIRL() != null ? "'" + FuncionesValidador.formatoFecha(registro.getFechaFinIRL()) + "'" : null);
        sentenciaTemp.append(QueriesConstants.COMMA_SYMBOL);
        sentenciaTemp.append(registro.getHorasLaboradas() != null ? registro.getHorasLaboradas() : null);
        sentenciaTemp.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);

        return sentenciaTemp.toString();
    }
}
