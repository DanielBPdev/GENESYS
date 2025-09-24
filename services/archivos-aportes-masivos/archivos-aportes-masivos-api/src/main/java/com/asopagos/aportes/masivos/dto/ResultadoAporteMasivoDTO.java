package com.asopagos.aportes.masivos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;



@XmlRootElement
public class ResultadoAporteMasivoDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    // Es necesario asociar el ECM al dato
    private String ecmIdentificador;

    private TipoIdentificacionEnum tipoIdentificacionAportante;

    private String numeroIdentificacionAportante;

    private String razonSocialAportante;

    private Long idDepartamento;

    private Long idMunicipio;

    private String razonSocial;

    private Date periodoPago;

    private Date fechaRecepcionAporte;

    private Date fechaDePago;

    private TipoCotizanteEnum tipoCotizante;

    private TipoIdentificacionEnum tipoIdentificacionCotizante;

    private String numeroIdentificacionCotizante;

    private String numeroDocumentoCotizante;

    private String primerNombreCotizante;
    
    private String segundoNombreCotizante;

    private String primerApellidoCotizante;

    private String segundoApellidoCotizante;

    private String conceptoDePago;

    private BigDecimal ibc;

    private String ing;

    private String ret;

    private String irl;

    private String vsp;

    private String vst;

    private String sln;

    private String ige;

    private String lma;

    private String vac;

    private String sus;

    private BigDecimal salarioBasico;

    private Short diasCotizados;

    private Integer diasMora;

    private BigDecimal tarifa;

    private Integer numeroDeHorasLaboradas;

    private BigDecimal aporteObligatorio;

    private BigDecimal valorIntereses;

    private BigDecimal totalAporte;

    private TipoSolicitanteMovimientoAporteEnum tipoAportante;

    private Long linea;



    public String getEcmIdentificador() {
        return this.ecmIdentificador;
    }

    public void setEcmIdentificador(String ecmIdentificador) {
        this.ecmIdentificador = ecmIdentificador;
    }

    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return this.tipoIdentificacionAportante;
    }

    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    public String getNumeroIdentificacionAportante() {
        return this.numeroIdentificacionAportante;
    }

    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    public String getRazonSocialAportante() {
        return this.razonSocialAportante;
    }

    public void setRazonSocialAportante(String razonSocialAportante) {
        this.razonSocialAportante = razonSocialAportante;
    }

    public Long getIdDepartamento() {
        return this.idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Long getIdMunicipio() {
        return this.idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getPeriodoPago() {
        return this.periodoPago;
    }

    public void setPeriodoPago(Date periodoPago) {
        this.periodoPago = periodoPago;
    }

    public Date getFechaRecepcionAporte() {
        return this.fechaRecepcionAporte;
    }

    public void setFechaRecepcionAporte(Date fechaRecepcionAporte) {
        this.fechaRecepcionAporte = fechaRecepcionAporte;
    }

    public Date getFechaDePago() {
        return this.fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return this.tipoIdentificacionCotizante;
    }

    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    public String getNumeroIdentificacionCotizante() {
        return this.numeroIdentificacionCotizante;
    }

    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    public String getNumeroDocumentoCotizante() {
        return this.numeroDocumentoCotizante;
    }

    public void setNumeroDocumentoCotizante(String numeroDocumentoCotizante) {
        this.numeroDocumentoCotizante = numeroDocumentoCotizante;
    }

    public String getPrimerNombreCotizante() {
        return this.primerNombreCotizante;
    }

    public void setPrimerNombreCotizante(String primerNombreCotizante) {
        this.primerNombreCotizante = primerNombreCotizante;
    }

    public String getSegundoNombreCotizante() {
        return this.segundoNombreCotizante;
    }

    public void setSegundoNombreCotizante(String segundoNombreCotizante) {
        this.segundoNombreCotizante = segundoNombreCotizante;
    }

    public String getPrimerApellidoCotizante() {
        return this.primerApellidoCotizante;
    }

    public void setPrimerApellidoCotizante(String primerApellidoCotizante) {
        this.primerApellidoCotizante = primerApellidoCotizante;
    }

    public String getSegundoApellidoCotizante() {
        return this.segundoApellidoCotizante;
    }

    public void setSegundoApellidoCotizante(String segundoApellidoCotizante) {
        this.segundoApellidoCotizante = segundoApellidoCotizante;
    }

    public String getConceptoDePago() {
        return this.conceptoDePago;
    }

    public void setConceptoDePago(String conceptoDePago) {
        this.conceptoDePago = conceptoDePago;
    }

    public BigDecimal getIbc() {
        return this.ibc;
    }

    public void setIbc(BigDecimal ibc) {
        this.ibc = ibc;
    }

    public String getIng() {
        return this.ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getRet() {
        return this.ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getIrl() {
        return this.irl;
    }

    public void setIrl(String irl) {
        this.irl = irl;
    }

    public String getVsp() {
        return this.vsp;
    }

    public void setVsp(String vsp) {
        this.vsp = vsp;
    }

    public String getVst() {
        return this.vst;
    }

    public void setVst(String vst) {
        this.vst = vst;
    }

    public String getSln() {
        return this.sln;
    }

    public void setSln(String sln) {
        this.sln = sln;
    }

    public String getIge() {
        return this.ige;
    }

    public void setIge(String ige) {
        this.ige = ige;
    }

    public String getLma() {
        return this.lma;
    }

    public void setLma(String lma) {
        this.lma = lma;
    }

    public String getVac() {
        return this.vac;
    }

    public void setVac(String vac) {
        this.vac = vac;
    }

    public String getSus() {
        return this.sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public BigDecimal getSalarioBasico() {
        return this.salarioBasico;
    }

    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    public Short getDiasCotizados() {
        return this.diasCotizados;
    }

    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    public Integer getDiasMora() {
        return this.diasMora;
    }

    public void setDiasMora(Integer diasMora) {
        this.diasMora = diasMora;
    }

    public BigDecimal getTarifa() {
        return this.tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public Integer getNumeroDeHorasLaboradas() {
        return this.numeroDeHorasLaboradas;
    }

    public void setNumeroDeHorasLaboradas(Integer numeroDeHorasLaboradas) {
        this.numeroDeHorasLaboradas = numeroDeHorasLaboradas;
    }

    public BigDecimal getAporteObligatorio() {
        return this.aporteObligatorio;
    }

    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    public BigDecimal getValorIntereses() {
        return this.valorIntereses;
    }

    public void setValorIntereses(BigDecimal valorIntereses) {
        this.valorIntereses = valorIntereses;
    }

    public BigDecimal getTotalAporte() {
        return this.totalAporte;
    }

    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    public Long getLinea() {
        return this.linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public TipoCotizanteEnum getTipoCotizante() {
        return this.tipoCotizante;
    }

    public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return this.tipoAportante;
    }

    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }




    @Override
    public String toString() {
        return "{" +
            " ecmIdentificador='" + getEcmIdentificador() + "'" +
            ", tipoIdentificacionAportante='" + getTipoIdentificacionAportante() + "'" +
            ", numeroIdentificacionAportante='" + getNumeroIdentificacionAportante() + "'" +
            ", razonSocialAportante='" + getRazonSocialAportante() + "'" +
            ", idDepartamento='" + getIdDepartamento() + "'" +
            ", idMunicipio='" + getIdMunicipio() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", fechaRecepcionAporte='" + getFechaRecepcionAporte() + "'" +
            ", fechaDePago='" + getFechaDePago() + "'" +
            ", tipoCotizante='" + getTipoCotizante() + "'" +
            ", tipoIdentificacionCotizante='" + getTipoIdentificacionCotizante() + "'" +
            ", numeroIdentificacionCotizante='" + getNumeroIdentificacionCotizante() + "'" +
            ", numeroDocumentoCotizante='" + getNumeroDocumentoCotizante() + "'" +
            //", nombreCotizante='" + getNombreCotizante() + "'" +
            ", conceptoDePago='" + getConceptoDePago() + "'" +
            ", ibc='" + getIbc() + "'" +
            ", ing='" + getIng() + "'" +
            ", ret='" + getRet() + "'" +
            ", irl='" + getIrl() + "'" +
            ", vsp='" + getVsp() + "'" +
            ", vst='" + getVst() + "'" +
            ", sln='" + getSln() + "'" +
            ", ige='" + getIge() + "'" +
            ", lma='" + getLma() + "'" +
            ", vac='" + getVac() + "'" +
            ", sus='" + getSus() + "'" +
            ", salarioBasico='" + getSalarioBasico() + "'" +
            ", diasCotizados='" + getDiasCotizados() + "'" +
            ", diasMora='" + getDiasMora() + "'" +
            ", tarifa='" + getTarifa() + "'" +
            ", numeroDeHorasLaboradas='" + getNumeroDeHorasLaboradas() + "'" +
            ", aporteObligatorio='" + getAporteObligatorio() + "'" +
            ", valorIntereses='" + getValorIntereses() + "'" +
            ", totalAporte='" + getTotalAporte() + "'" +
            ", linea='" + getLinea() + "'" +
            "}";
    }

}
